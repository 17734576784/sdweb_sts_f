package com.kesd.action.oper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;
import net.sf.json.JSONObject;
import com.hx.ami.spi.CKeToken;
import com.kesd.action.dyjc.ActMakeTokens;
import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.WebConfig;
import com.kesd.common.YDTable;
import com.kesd.comntpara.ComntUseropDef;
import com.kesd.dbpara.OrgStsPara;
import com.kesd.dbpara.YffRatePara;
import com.kesd.util.I18N;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
import com.libweb.dao.HibDao;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;
import com.sts.SoftEncryptedToken;
import com.sts.common.STSDef;
import com.sts.common.StsEncryptor;
import com.sts.common.StsFunc;
import com.sts.comnt.StsComntService;

public class ActOperDyBengal extends ActionSupport{

	/**
	 * 业务操作，调用厂家API进行操作,开户，缴费，销户
	 */
	private static final long serialVersionUID = 9103801295518796263L;
	private String result;
	private int hasErr = 1;
	private int noErr  = 0;
	private int errState = noErr;
	private String params;
	private String operType;
	private static final String errorToken = "tokenname:";
	private static final String errorTag = "error:";
	
	//操作实现
	public String operDY() throws Exception{
		if(params == null || params.isEmpty() 
				|| operType == null || operType.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		int oper_type = CommBase.strtoi(operType);
		JSONObject json_param = JSONObject.fromObject(params);
		
		switch(oper_type){
		case SDDef.YFF_OPTYPE_ADDRES:	//开户
			result = addNewOper(json_param);
			break;
		case SDDef.YFF_OPTYPE_PAY:		//缴费	
			result = payOper(json_param);
			break;
		case SDDef.YFF_OPTYPE_REPAIR:	//补卡
			result = repCardOper(json_param);
			break;
		case SDDef.YFF_OPTYPE_DESTORY:	//销户
			result = delCusOper(json_param);
			break;	
		//工具卡操作--待定
		//测试卡操作--待定
		default:
			break;
		}
		return SDDef.SUCCESS;
	}
	
	//开户操作--软件加密
	public String addNewOper(JSONObject json_param) throws Exception{
		CKeToken ckeToken = new CKeToken();	//引入API操作类
		
		String meterNo = json_param.getString("meterNo");		//电表编号	
		String customerID = json_param.getString("customerID");	//客户编号
		String utilityID = json_param.getString("utilityID");	//单位id
		int buy_times = json_param.getInt("buy_times");	//购电次数
		//int pay_money = Integer.parseInt(json_param.getString("pay_money").isEmpty()?"0":json_param.getString("pay_money"));	//缴费金额
		double pay_money = CommBase.strtof(json_param.getString("pay_money"));	//缴费金额

		String feeproj_id = json_param.getString("feeproj_id");	//费率方案;
		
		YffRatePara ratePara = loadRateParaById(Short.parseShort(feeproj_id));
		byte feeType = ratePara.getFeeType();//获取费率类型
		
		int friend_s = Integer.parseInt(json_param.getString("friend_s").isEmpty()?"20":json_param.getString("friend_s"));
		int friend_e = Integer.parseInt(json_param.getString("friend_e").isEmpty()?"2":json_param.getString("friend_e"));
		
		//8字符串，末位代表是否使用友好模式
		//前七位每位为1或0，顺序为周六,周五，周四，周三，周二，周一，周日
		String friendDate = json_param.getString("friendDate");
		
		int tzval = json_param.getInt("tzval")*100;//透支额度 单位为分

		String toady = formatToday();
						
		//以下这些参数或许可以在java中固定定义
		String sgc = "123456";
		int tarrifIndex = 0;
		int keyVersion = 0;
		int keyExpiredTime = 0;
		long keyNo = json_param.getInt("keyNo");
		String nSgc = "234567";
		int nTariffIndex = 1;
		int nKeyVersion = 1;
		int nKeyExpiredTime = 1;
		long nKeyNo = 1;
		String errorDesc = "";
		
		List<String> tokenList = new ArrayList<String>();
		
		//1、更改秘钥
		String data_chgToken = ckeToken.getKeyChangeToken(
				meterNo, 
				sgc, 
				tarrifIndex, 
				keyVersion, 
				keyExpiredTime,
				keyNo, 
				nSgc, 
				nTariffIndex, 
				nKeyVersion, 
				nKeyExpiredTime, 
				nKeyNo
		);
		errorDesc = splitXmlByToken(data_chgToken, tokenList);
		if(errorDesc.length() > 0) {
			errState = hasErr;
			return errorToken + "'" + "getKeyChangeToken" + "'," + errorTag + errorDesc;
		}
		
		//2、缴费
		int seqNo =  json_param.getInt("seqNo");			//缴费次数,界面传值，固定为2
		int amount = (int)(pay_money * 100);				//缴费金额，界面(元)*100 单位：分

		String data_creditToken = ckeToken.getCreditToken(
				meterNo, 
				nSgc, 
				tarrifIndex, 
				nKeyVersion, 
				nKeyExpiredTime, 
				nKeyNo, 
				seqNo, 
				amount
		);
		errorDesc = splitXmlByToken(data_creditToken, tokenList);
		if(errorDesc.length() > 0) {
			errState = hasErr;
			return errorToken + "'" + "getKeyChangeToken" + "'," + errorTag + errorDesc;
		}
		
		//3、设置最大功率极
		int activationModel = 1;
		//String date = "2015-01-12";	//当前时间：格式必须为YYYY-MM-dd
		int fmaximum_power = (int) (CommBase.strtof(json_param.getString("fmaximum_power")) * 10);
		int gmaximum_power = (int) (CommBase.strtof(json_param.getString("gmaximum_power")) * 10);
		int power_start = CommBase.strtoi(json_param.getString("power_start").isEmpty()?"20":json_param.getString("power_start"));
		int power_end = CommBase.strtoi(json_param.getString("power_end").isEmpty()?"2":json_param.getString("power_end"));
		
		int[] maxPowerLimits = {fmaximum_power,gmaximum_power};//前面是最大功率峰值，后面是最大功率谷值 
		int[] hours = {power_start,power_end};
		toady = json_param.getString("power_date").isEmpty()?formatToday():CommFunc.FormatToYMD(json_param.getString("power_date"));		
		
		String data_MPLToken = ckeToken.getMaxPowerLimitToken(
				meterNo, 
				sgc, 
				tarrifIndex, 
				nKeyVersion, 
				nKeyExpiredTime, 
				nKeyNo, 
				seqNo, 
				activationModel, 
				toady, 
				maxPowerLimits, 
				hours
		);
		errorDesc = splitXmlByToken(data_MPLToken, tokenList);
		if(errorDesc.length() > 0) {
			errState = hasErr;
			return errorToken + "'" + "getKeyChangeToken" + "'," + errorTag + errorDesc;
		}
		
		
		//4、(设置阶梯费率)或其他费率,根据费率（三种，选择调用不同方法）------需仲哥再检查核对一下
		Integer activatingDateDB = ratePara.getActivdate();//费率激活日期 :月日
//		String month = activatingDateDB / 100 < 10 ? "0" + activatingDateDB / 100 : activatingDateDB / 100 + "";
//		String day = activatingDateDB % 100 < 10 ? "0" + activatingDateDB % 100 : activatingDateDB % 100 + "";
//		String year = CommFunc.nowYMD().substring(0, 4);
//		String activatingDate = year + "-" + month + "-" + day;//当前时间 
		String activatingDate = CommFunc.FormatToYMD(activatingDateDB);
		int activatingModel = 1, 
			validDate = 1;
			
		String data_STToken = "";
		//居民单费率,feeType可提出为final定值
		if(feeType == 3){
			//费率*100为分
			int rate = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatedZ()== null)?0.0: ratePara.getRatedZ()*100,0));
			data_STToken = ckeToken.getSingleTariffToken(
					meterNo, 
					sgc, 
					tarrifIndex, 
					nKeyVersion, 
					nKeyExpiredTime, 
					nKeyNo, 
					seqNo, 
					activatingDate, 
					activatingModel, 
					validDate, 
					rate);
		}
		//复费率
		else if(feeType == 4){
			int rates[] = new int[2];
			int times[] = new int[2];

			if(WebConfig.getPeakTime()){
				JSONObject peakTime = JSONObject.fromObject(WebConfig.peak_time);
				times[0] = CommBase.strtoi(peakTime.getString("time1"));
				times[1] = CommBase.strtoi(peakTime.getString("time2"));
			}else{
				times[0] = 8;
				times[1] = 17;
			}

			//复费率//费率*100为分
			//数组对应峰谷
			rates[0] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatefF()== null)?0.0: ratePara.getRatefF()*100,0));//峰
			rates[1] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatefG()== null)?0.0: ratePara.getRatefG()*100,0));//谷
			
			data_STToken = ckeToken.getTOUTariffToken(
					meterNo, 
					sgc, 
					tarrifIndex, 
					nKeyVersion, 
					nKeyExpiredTime, 
					nKeyNo, 
					seqNo, 
					activatingDate, 
					activatingModel, 
					validDate, 
					rates, 
					times
			);
		}
		//阶梯费率
		else if(feeType == 0){
			int[] rates = new int[7];
			int[] steps = new int[6];
			
//			rates[0] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejR1()== null)?0.0: ratePara.getRatejR1()*100,0));//
//			rates[1] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejR2()== null)?0.0: ratePara.getRatejR2()*100,0));//
//			rates[2] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejR3()== null)?0.0: ratePara.getRatejR3()*100,0));//
//			rates[3] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejR4()== null)?0.0: ratePara.getRatejR4()*100,0));//
//			rates[4] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejR5()== null)?0.0: ratePara.getRatejR5()*100,0));//
//			rates[5] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejR6()== null)?0.0: ratePara.getRatejR6()*100,0));//
//			rates[6] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejR7()== null)?0.0: ratePara.getRatejR7()*100,0));//
//			
//			steps[0] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejTd1()== null)?0.0: ratePara.getRatejTd1(),0));//
//			steps[1] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejTd2()== null)?0.0: ratePara.getRatejTd2(),0));//
//			steps[2] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejTd3()== null)?0.0: ratePara.getRatejTd3(),0));//
//			steps[3] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejTd4()== null)?0.0: ratePara.getRatejTd4(),0));//
//			steps[4] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejTd5()== null)?0.0: ratePara.getRatejTd5(),0));//
//			steps[5] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejTd6()== null)?0.0: ratePara.getRatejTd6(),0));//
			
			rates[0] = (int)((ratePara.getRatejR1()== null)?0.0: ratePara.getRatejR1()*100);//
			rates[1] = (int)((ratePara.getRatejR2()== null)?0.0: ratePara.getRatejR2()*100);
			rates[2] = (int)((ratePara.getRatejR3()== null)?0.0: ratePara.getRatejR3()*100);
			rates[3] = (int)((ratePara.getRatejR4()== null)?0.0: ratePara.getRatejR4()*100);
			rates[4] = (int)((ratePara.getRatejR5()== null)?0.0: ratePara.getRatejR5()*100);
			rates[5] = (int)((ratePara.getRatejR6()== null)?0.0: ratePara.getRatejR6()*100);
			rates[6] = (int)((ratePara.getRatejR7()== null)?0.0: ratePara.getRatejR7()*100);
			                                                                           
			steps[0] = (int)((ratePara.getRatejTd1()== null)?0.0: ratePara.getRatejTd1());//
			steps[1] = (int)((ratePara.getRatejTd2()== null)?0.0: ratePara.getRatejTd2());//
			steps[2] = (int)((ratePara.getRatejTd3()== null)?0.0: ratePara.getRatejTd3());//
			steps[3] = (int)((ratePara.getRatejTd4()== null)?0.0: ratePara.getRatejTd4());//
			steps[4] = (int)((ratePara.getRatejTd5()== null)?0.0: ratePara.getRatejTd5());//
			steps[5] = (int)((ratePara.getRatejTd6()== null)?0.0: ratePara.getRatejTd6());//
			
			data_STToken = ckeToken.getStepTariffToken(
					meterNo, 
					sgc, 
					tarrifIndex, 
					nKeyVersion, 
					nKeyExpiredTime, 
					nKeyNo, 
					seqNo, 
					activatingDate, 
					activatingModel, 
					validDate, 
					rates, 
					steps
			);
		}
		
		errorDesc = splitXmlByToken(data_STToken, tokenList);
		if(errorDesc.length() > 0) {
			errState = hasErr;
			return errorToken + "'" + "getKeyChangeToken" + "'," + errorTag + errorDesc;
		}
		
		
		//5、(设置友好模式)--保电
		int friendMode = 1;	//是否启用友好模式 0 启用友好模式 1 不启用友好模式
		int[] times = {friend_s,friend_e}; //友好模式起止时间
		int[] days = new int[7];			//
		
		if (friendDate.length() > 8) friendDate = friendDate.substring(0, 8);
		for(int i=0, nums=friendDate.length(); i<nums; i++){
			if(i == nums-1) {
				friendMode = CommBase.strtoi(friendDate.substring(i,i+1));	//数据库中 0 不使用 1 使用
				if(friendMode == 1) {
					friendMode = 0;
				}
				else {
					friendMode = 1;
				}
			}
			else{
				days[i] = CommBase.strtoi(friendDate.substring(i,i+1));
			}
		}
				
		String  data_FMToken = ckeToken.getFriendModeToken(
				meterNo, 
				sgc, 
				tarrifIndex, 
				nKeyVersion, 
				nKeyExpiredTime, 
				nKeyNo, 
				seqNo, 
				friendMode, 
				times, 
				days
		);
		errorDesc = splitXmlByToken(data_FMToken, tokenList);
		if(errorDesc.length() > 0) {
			errState = hasErr;
			return errorToken + "'" + "getKeyChangeToken" + "'," + errorTag + errorDesc;
		}
		
		//6、设置信用额度极限和透支额度极限，透支值(mppay中)和囤积值从费率中取！
		int  amountType = 0, //信用额度限值
			 amountLimit = CommBase.strtoi(CommFunc.roundtostr(ratePara.getMoneyLimit(),0))*100;
		String data_SCALAOAL = ckeToken.getSetCreditAmountLimitOrOverdrawAmountLimitToken(
				meterNo,
				sgc, 
				tarrifIndex, 
				nKeyVersion, 
				nKeyExpiredTime, 
				nKeyNo, 
				seqNo, 
				amountType, 
				amountLimit
		);
		errorDesc = splitXmlByToken(data_SCALAOAL, tokenList);
		if(errorDesc.length() > 0) {
			errState = hasErr;
			return errorToken + "'" + "getKeyChangeToken" + "'," + errorTag + errorDesc;
		}

		amountType = 1; //透支额度极限
		amountLimit = tzval;
		data_SCALAOAL = ckeToken.getSetCreditAmountLimitOrOverdrawAmountLimitToken(
			meterNo,
			sgc, 
			tarrifIndex, 
			nKeyVersion, 
			nKeyExpiredTime, 
			nKeyNo, 
			seqNo, 
			amountType, 
			amountLimit
	   );
		
	errorDesc = splitXmlByToken(data_SCALAOAL, tokenList);
	if(errorDesc.length() > 0) {
		errState = hasErr;
		return errorToken + "'" + "getKeyChangeToken" + "'," + errorTag + errorDesc;
	}
		
		//7、加密
		int nums = tokenList.size();
		String[] tokens = new String[nums];
		for(int i=0; i<nums; i++){
			tokens[i] = tokenList.get(i);
		}

		int answerToReset = 0;
		int binaryPattern = 0;
		int version = 0;
		long sanctionedLoad = 0;
		int meterType = 0;
		int lastRechargeAmount = 0;
		String lastRechargeDate = "000000";
		String lastTransactionID = "";
		int cardType = 0x0ABC; //用户卡   测试卡：0x0BAC
		String pwdData = "";  
		int TokenTotalNumber = tokens.length;
		
		String dataToCard = ckeToken.encryptSmartCardReturnData(
				answerToReset, 
				binaryPattern, 
				version, 
				meterNo, 
				customerID, 
				utilityID, 
				sanctionedLoad, 
				meterType, 
				lastRechargeAmount, 
				lastRechargeDate, 
				lastTransactionID, 
				cardType, 
				pwdData, 
				TokenTotalNumber, 
				tokens);
		
		return dataToCard;	//返回写卡字符串
	}
	
	//缴费操作,各项参数具体值需商议
	public String payOper(JSONObject json_param){
		//判断keyregno状态
		JDBCDao jdbcDao = new JDBCDao();
		
		int rtuId = json_param.optInt("rtuId");
		short mpId = CommFunc.objectToShort(json_param.optInt("mpId"));
		
		String sql = "select keychange from meter_stspara where rtu_id = " + rtuId + " and mp_id = " + mpId;		
		
		StsComntService stsComntService = StsComntService.getInstance();
		short orgId = stsComntService.getOrgByRtuId(rtuId);
		OrgStsPara orgStsPara = stsComntService.getKMFParam(orgId);
		
		List<Map<String,Object>>  list = jdbcDao.result(sql);
		//当加密参数修改了之后,重新开户、缴费
		Map<String,Object> map = list.get(0);
		if(!map.get("keychange").toString().equals("0")){
			//走开户操作
			try{
				if(orgStsPara.getET() == STSDef.STS_ET_SOFT){
					ActMakeTokens actMakeToken = new ActMakeTokens();
					actMakeToken.setResult(json_param.toString());
					actMakeToken.makeOpenAccountToken();
					String result = actMakeToken.getResult();
					//
					if(result != null && result != ""){
						//修改keychange 置为0
						sql = "update meter_stspara set keychange = 0 where rtu_id = " + rtuId + " and mp_id = " + mpId;					
						if(jdbcDao.executeUpdate(sql)){
							sql = "update mppay_state set xh_date = kh_date,kh_date = "+CommFunc.nowDateYmd()+" where rtu_id = " + rtuId + " and mp_id = " + mpId;
							if(!jdbcDao.executeUpdate(sql)){
								return "";
							}
						}else{
							return "";
						}
					}					
					return STSDef.STS_KEYCHANGE + STSDef.STS_ET_SOFT + result;					
				}else{
					//硬加密	
					//需要的参数
					String meterAddress = json_param.getString("meterNo");
					int    regno		= json_param.getInt("regno");
					String sgc			= json_param.getString("SGC");
					int    ti			= json_param.getInt("TI");
					byte   krn			= CommFunc.objectToByte(json_param.get("KRN"));
					int    ken          = json_param.getInt("KEN");
					String tokenDesc = stsComntService.changeKey(meterAddress,rtuId,mpId,regno,sgc,ti,krn,ken);
					if(tokenDesc != null && tokenDesc != ""){
						//修改keychange 置为0
						sql = "update meter_stspara set keychange = 0 where rtu_id = " + rtuId + " and mp_id = " + mpId;					
						if(jdbcDao.executeUpdate(sql)){
							sql = "update mppay_state set xh_date = "+CommFunc.nowDateYmd()+",kh_date = "+CommFunc.nowDateYmd()+" where rtu_id = " + rtuId + " and mp_id = " + mpId;
							if(!jdbcDao.executeUpdate(sql)){
								return "";
							}
						}else{
							return "";
						}				
					}else{
						return "";
					}
					int payQuantity = json_param.optInt("all_dl");
					String token = stsComntService.payOperToken(payQuantity, meterAddress, rtuId, mpId);
					if(token != null && token != ""){
						return STSDef.STS_KEYCHANGE + STSDef.STS_ET_HARDWARE + tokenDesc + "," + token;						
					}else{
						return STSDef.STS_KEYCHANGE + STSDef.STS_ET_HARDWARE + tokenDesc + "," + "";					
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				return "";
			}
		}
		
		//正常缴费
		//根据加密方式调用对应的加密规则
		if(orgStsPara.getET() == STSDef.STS_ET_SOFT){
			int alldl = Integer.parseInt(json_param.getString("all_dl").isEmpty() ? "0" : json_param.getString("all_dl"));	//缴费电量	
			byte rnd = (byte) (Byte.parseByte(json_param.getString("rnd").isEmpty() ? "0" : json_param.getString("rnd"))%16);
	 		byte[] keydata = StsFunc.hexStringToByte(CommFunc.loadMeterKey(rtuId,mpId));
	 		if (keydata.length < 16) {		//查不到meterkey
	 			return STSDef.STS_METERKEY_ERROR;
	 		}
			String token = new SoftEncryptedToken().getToken_TransferCredit((byte)0,rnd, StsFunc.getTid(STSDef.STS_BASEDATE_2014),alldl, keydata);
			
			if(token != null && token != ""){
				//修改keychange 置为0
				sql = "update meter_stspara set keychange = 0 where rtu_id = " + rtuId + " and mp_id = " + mpId;					
				if(!jdbcDao.executeUpdate(sql)){
					return "";
				}				
			}
			return token;
		}
		else{
			int payQuantity = json_param.optInt("all_dl");
			String meterAddress = json_param.optString("meterNo");
			String token = stsComntService.payOperToken(payQuantity, meterAddress, rtuId, mpId);
			if(token != null && token != ""){
				//修改keychange 置为0
				sql = "update meter_stspara set keychange = 0 where rtu_id = " + rtuId + " and mp_id = " + mpId;					
				if(!jdbcDao.executeUpdate(sql)){
					return "";
				}				
			}
			return token;
		}	 
	}
	
	//补卡操作
	public String repCardOper(JSONObject json_param){
		return payOper(json_param);
	}
	
	//销户操作
	public String delCusOper(JSONObject json_param) {
		
  		int showvalue = Integer.parseInt(json_param.getString("showvalue").isEmpty() ? "0" : json_param.getString("showvalue"));		
 		byte RND = (byte) (Byte.parseByte(json_param.getString("RND").isEmpty() ? "0" : json_param.getString("RND"))%16);
 		
		int  rtuId = Integer.parseInt(json_param.getString("rtuId"));
 		short mpId = Short.parseShort(json_param.getString("mpId"));
		StsComntService stsComntService = StsComntService.getInstance();
		short orgId = stsComntService.getOrgByRtuId(rtuId);
		OrgStsPara orgStsPara = stsComntService.getKMFParam(orgId);
		byte[] keydata = null;
		String token   = "";
		if(orgStsPara.getET() == STSDef.STS_ET_SOFT){
	 		keydata = StsFunc.hexStringToByte(CommFunc.loadMeterKey(rtuId,mpId));
			token 	= new SoftEncryptedToken().getToken_TransferCredit((byte)0,RND, StsFunc.getTid(STSDef.STS_BASEDATE_2014),showvalue, keydata);			
		}else{
			String meterAddress 	= json_param.getString("meterNo");
			String resNo 			= json_param.getString("userNo");
			String resDesc 			= json_param.getString("userName");
			token 	= stsComntService.setClearCreditToken(65535, meterAddress, rtuId, (int)mpId, resNo, resDesc, CommFunc.objectToByte(1));
		}
		return token;	 
	}
	
	//测试卡操作
	public String cardTestOper(JSONObject json_param){
		CKeToken ckeToken = new CKeToken();	//引入API操作类
		List<String> tokenList = new ArrayList<String>();
		
		String meterNo = json_param.getString("meterNo");		//电表编号	
		String customerID = json_param.getString("customerID");	//客户编号
		String utilityID = json_param.getString("utilityID");	//单位id
		
		int manufacturingID = 0, control = 0;
		//测试卡
		String data_testToken = ckeToken.getTestToken(manufacturingID, control);
		splitXmlByToken(data_testToken, tokenList);

		//6、加密---该方法如果参数确定，则可以提取为公共方法。
		int nums = tokenList.size();
		String[] tokens = new String[nums];
		for(int i=0; i<nums; i++){
			tokens[i] = tokenList.get(i);
		}

		
		int answerToReset = 0;
		int binaryPattern = 0;
		int version = 0;
		String meterID = meterNo;
		long sanctionedLoad = 0;
		int meterType = 0;
		int lastRechargeAmount = 0;
		String lastRechargeDate = "080313";
		String lastTransactionID = "";
		int cardType = 0;
		String pwdData = "";  
		int TokenTotalNumber = tokens.length;
		
		String dataToCard = ckeToken.encryptSmartCardReturnData(
				answerToReset, 
				binaryPattern, 
				version, 
				meterID, 
				customerID, 
				utilityID, 
				sanctionedLoad, 
				meterType, 
				lastRechargeAmount, 
				lastRechargeDate, 
				lastTransactionID, 
				cardType, 
				pwdData, 
				TokenTotalNumber, 
				tokens
		);
		return dataToCard;	//返回写卡字符串
	}
	
	//读卡解密，返回值到页面,为xml转换成的json字符串
	public String decryptDataToCard(){
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		result = xmlToJSON(decryptData(result)).toString();
		return SDDef.SUCCESS;
	}
		
	//销户后 returnToken 解析
	public String QueryDestroyCardRemain() {
		
		if(params == null || params.isEmpty() 
				|| operType == null || operType.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}

		JSONObject json_param = JSONObject.fromObject(params);
		
		CKeToken ckeToken = new CKeToken();	//引入API操作类
		List<String> tokenList = new ArrayList<String>();
		String errorDesc = "";
		
		String meterNo = json_param.getString("meterNo");
		String customerID = json_param.getString("customerID");
		String utilityID = json_param.getString("utilityID");
		String Token = json_param.getString("returnToken");	//缴费金额
		
		int seqNo = Integer.parseInt(json_param.getString("seqNo").isEmpty()?"2":json_param.getString("seqNo"));
		int keyNo = Integer.parseInt(json_param.getString("keyNo").isEmpty()?"1":json_param.getString("keyNo"));
		
		
		String sgc = "1";
		int tarrifIndex = 1;
		int keyVersion = 0;
		int keyExpiredTime = 2;

		
		String CardReturnXml = ckeToken.getReturnToken(meterNo, sgc, tarrifIndex, keyVersion, keyExpiredTime, keyNo, Token);

		result = returnXmlToJSON(CardReturnXml).toString();
		return SDDef.SUCCESS;
	}
	
	//销户返回信息解析xml-->json
	public JSONObject returnXmlToJSON(String xmlStr){
		JSONObject json = new JSONObject();
		String root = "result";
		String xmlText = getTagValue(xmlStr,root);//card标签内的值
		String[] tags = {"errorCode","tokenSubClass","seq","meterStatus","amount"};
		setChildTagToJSON(json, xmlText, tags);
		return json;
	}
		
	//读卡解密，返回值为xml格式的string串
	public String decryptData(String cardInfo){
		if(cardInfo == null || cardInfo.isEmpty()){
			return SDDef.EMPTY;
		}

		String backData = "";
		//具体参数还需商定
		CKeToken ckeToken = new CKeToken();	//引入API操作类
		String meterNo = "1234567890123456"; 
		String sgc = "123456"; 
		int tariffIndex = 1;
		int keyVersion = 1; 
		int keyExpiredTime = 1; 
		long keyNo = 123;		
		backData  = ckeToken.decryptSmartCardReturnData(
				meterNo, 
				sgc, 
				tariffIndex, 
				keyVersion, 
				keyExpiredTime, 
				keyNo, 
				cardInfo
		);	
		return backData;
	}
	
	//读卡检索,返回数据库中查出的信息
	public String loadInfoByReadCard() throws SQLException, IllegalAccessException{
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		//获取解密后的xml文件
		JSONObject json = xmlToJSON(decryptData(result));
		
		String meterId = json.getString("block1_meterID").replaceFirst("0*", "");
		JSONObject jsonData = new JSONObject();
		
		String sql = "select mp.rtu_id as rtu_id, mp.id as mp_id, res.cons_no, res.id as res_id,res.describe, res.phone, res.address, mpstate.cus_state, meter.comm_addr, meter.factory, mppay.yffmeter_type, cons.tel_no3," +
			"mppay.cacl_type, mppay.feectrl_type, mppay.pay_type, mppay.feeproj_id, mppay.tz_val, mpstate.reserve1 as seqNo, mpstate.reserve2 as keyNo, mpstate.buy_times, " +	
			"mppay.prot_st as friend_s, mppay.prot_ed as friend_e, mp.reserve1 as friendDate,mp.rp as fmaximum_power,mp.mi as gmaximum_power,mp.bd_factor as power_start,v_factor as power_end,mp.ct_denominator as power_date " +
			"from  ydparaben.dbo.conspara as cons, ydparaben.dbo.rtupara as rtu, ydparaben.dbo.mppara as mp, ydparaben.dbo.residentpara as res, " +
			"ydparaben.dbo.meterpara as meter, ydparaben.dbo.mppay_para as mppay, ydparaben.dbo.mppay_state as mpstate " + 
			"where cons.id = rtu.cons_id and rtu.app_type = " + SDDef.APPTYPE_JC + " and rtu.id = mp.rtu_id and rtu.id = res.rtu_id and rtu.id = meter.rtu_id and " +
			"rtu.id = mppay.rtu_id and mp.id = meter.mp_id and mp.id = mppay.mp_id and res.id = meter.resident_id and rtu.id = mpstate.rtu_id and mp.id = mpstate.mp_id " + 
			" and meter.comm_addr = '" + meterId + "'";
		
		switch(CommBase.strtoi(operType)){
		case ComntUseropDef.YFF_GYOPER_ADDCUS:		//低压操作-开户
			sql += " and (mpstate.cus_state is null  or mpstate.cus_state = " + SDDef.YFF_CUSSTATE_INIT + " or mpstate.cus_state = " + SDDef.YFF_CUSSTATE_DESTORY + " )";
			break;
		case ComntUseropDef.YFF_GYOPER_RESTART:		//低压操作-恢复
			sql += " and mpstate.cus_state = " + SDDef.YFF_CUSSTATE_PAUSE;
			break;
		case -1: break;//清空卡页面，不限制用户状态
		default:
			sql += " and mpstate.cus_state = " + SDDef.YFF_CUSSTATE_NORMAL;
			break;
		}
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		rs = j_dao.executeQuery(sql);
		while(rs.next()){
			jsonData.put("rtu_id", CommBase.CheckString(rs.getString("rtu_id")));	//终端编号
			jsonData.put("mp_id", CommBase.CheckString(rs.getString("mp_id")));		//测点编号
			jsonData.put("userno", CommBase.CheckString(rs.getString("cons_no")));						//客户编号界面显示
			jsonData.put("res_id", CommBase.CheckString(rs.getString("res_id")));						//存库时使用
			jsonData.put("username", CommBase.CheckString(rs.getString("describe")));					//客户名称
			jsonData.put("cus_state_id", CommBase.CheckString(rs.getString("cus_state")));				//客户状态值
			jsonData.put("cus_state", Rd.getDict(Dict.DICTITEM_YFFCUSSTATE, rs.getByte("cus_state")));	//客户状态描述
			
			jsonData.put("tel", CommBase.CheckString(rs.getString("phone")));							//联系电话
			jsonData.put("useraddr", CommBase.CheckString(rs.getString("address")));					//客户地址
			jsonData.put("commaddr", CommBase.CheckString(rs.getString("comm_addr")));					//电表编号
			jsonData.put("yffmeter_type", CommBase.CheckString(rs.getString("yffmeter_type")));			//预付费表类型			
			jsonData.put("yffmeter_type_desc", Rd.getDict(Dict.DICTITEM_YFFMETERTYPE, rs.getByte("yffmeter_type")));//预付费表类型描述
			jsonData.put("factory", Rd.getDict(Dict.DICTITEM_FACTORY, rs.getByte("factory")));			//生产厂家
			jsonData.put("utilityid", CommBase.CheckString(rs.getString("tel_no3")));					//供电单位编号
			jsonData.put("cacl_type", CommBase.CheckString(rs.getString("cacl_type")));					//计费方式
			jsonData.put("cacl_type_desc", Rd.getDict(Dict.DICTITEM_FEETYPE, rs.getByte("cacl_type")));//计费方式描述
			
			jsonData.put("feectrl_type", CommBase.CheckString(rs.getString("feectrl_type")));			//费控方式
			jsonData.put("feectrl_type_desc", Rd.getDict(Dict.DICTITEM_PREPAYTYPE, rs.getByte("feectrl_type")));//费控方式描述
			
			jsonData.put("pay_type", CommBase.CheckString(rs.getString("pay_type")));					//缴费方式
			jsonData.put("pay_type_desc", Rd.getDict(Dict.DICTITEM_PAYTYPE, rs.getByte("pay_type")));	//缴费方式描述
			
			jsonData.put("feeproj_id", CommBase.CheckString(rs.getString("feeproj_id")));				//费率方案id
			Object obj = Rd.getRecord(YDTable.TABLECLASS_YFFRATEPARA, rs.getShort("feeproj_id"));
			if(obj != null){
				jsonData.put("feeproj_desc", ((YffRatePara)obj).getDescribe());							//费率方案描述
				jsonData.put("feeproj_detail", getDJLX((YffRatePara)obj));														//费率方案详细信息--Rd.getYffRateDesc((YffRatePara)obj)再定！！
				jsonData.put("feeType", ((YffRatePara)obj).getFeeType());								//费率方案
				jsonData.put("money_limit", ((YffRatePara)obj).getMoneyLimit());						//囤积额度
			}			
			jsonData.put("tzval", CommBase.CheckString(rs.getString("tz_val")));						//透支金额
			
			jsonData.put("seqNo", CommBase.CheckString(rs.getString("seqNo")));			//seqNo				
			jsonData.put("keyNo", CommBase.CheckString(rs.getString("keyNo")));			//keyNo
			jsonData.put("buy_times", CommBase.CheckString(rs.getString("buy_times")));	//购电次数
		
			jsonData.put("friend_s", CommBase.CheckString(rs.getString("friend_s")));	//保电开始时间
			jsonData.put("friend_e", CommBase.CheckString(rs.getString("friend_e")));	//保电结束时间
			jsonData.put("friendDate", CommBase.CheckString(rs.getString("friendDate")));//每周保电日期
			
			jsonData.put("fmaximum_power", CommBase.CheckString(rs.getString("fmaximum_power")));	//峰最大功率
			jsonData.put("gmaximum_power", CommBase.CheckString(rs.getString("gmaximum_power")));	//谷最大功率
			jsonData.put("power_start", CommBase.CheckString(rs.getString("power_start")));			//最大功率开始时间
			jsonData.put("power_end", CommBase.CheckString(rs.getString("power_end")));				//最大功率结束时间
			jsonData.put("power_date", CommBase.CheckString(rs.getString("power_date")));				//最大功率结束时间
		}
		result = jsonData.toString();
		return SDDef.SUCCESS;
	}

	private static String getDJLX(YffRatePara yffrate) {
		
		if(yffrate == null)return "";
		
		String strDesc = null;
		
		switch(yffrate.getFeeType()){
		case SDDef.YFF_FEETYPE_DFL: 
			//"总:%.3f 
			strDesc = String.valueOf(yffrate.getRatedZ());
			break;
		case SDDef.YFF_FEETYPE_FFL: 
			//"尖:%.3f 峰:%.3f 平:%.3f 谷:%.3f "
			String RatefF = CommFunc.CheckString(((yffrate.getRatefF()== null)?0.0: yffrate.getRatefF()));
			String RatefG = CommFunc.CheckString(((yffrate.getRatefG()== null)?0.0: yffrate.getRatefG()));
			strDesc = "Peak/Off:" +  RatefF + "/"  + RatefG;
			break;
		case SDDef.YFF_FEETYPE_JTFL:	//阶梯费率1:，阶梯费率2:，阶梯费率3:，梯度1:，梯度2:
			switch(yffrate.getRatejNum()) {
				case 1:
				case 2:
					strDesc = "0~" + CheckNull(yffrate.getRatejTd1())+ "|" + CheckNull(yffrate.getRatejR1()) + "/" + CheckNull(yffrate.getRatejR2()); 
					break;
				case 3:
					strDesc = "0~" + CheckNull(yffrate.getRatejTd1())+ "~" + CheckNull(yffrate.getRatejTd2()) + "|" + CheckNull(yffrate.getRatejR1()) + "/" + CheckNull(yffrate.getRatejR2()) + "/" + CheckNull(yffrate.getRatejR3());
					break;
				case 4:
					strDesc = "0~" + CheckNull(yffrate.getRatejTd1())+ "~" + CheckNull(yffrate.getRatejTd2()) +  "~" + CheckNull(yffrate.getRatejTd3()) + "~" +"|" 
							+ CheckNull(yffrate.getRatejR1()) + "/" + CheckNull(yffrate.getRatejR2()) + "/" + CheckNull(yffrate.getRatejR3()) + "/" + CheckNull(yffrate.getRatejR4());
					break;
				case 5:
					strDesc = "0~" + CheckNull(yffrate.getRatejTd1())+ "~" + CheckNull(yffrate.getRatejTd2()) +  "~" + CheckNull(yffrate.getRatejTd3()) + "~" + CheckNull(yffrate.getRatejTd4()) +  "|" 
							+ CheckNull(yffrate.getRatejR1()) + "/" + CheckNull(yffrate.getRatejR2()) + "/" + CheckNull(yffrate.getRatejR3()) + "/" + CheckNull(yffrate.getRatejR4()) + "/" + CheckNull(yffrate.getRatejR5());
					break;
				case 6:
					strDesc = "0~" + CheckNull(yffrate.getRatejTd1())+ "~" + CheckNull(yffrate.getRatejTd2()) +  "~" + CheckNull(yffrate.getRatejTd3()) + "~" + CheckNull(yffrate.getRatejTd4()) +  "~" + CheckNull(yffrate.getRatejTd5()) + "|" 
							+ CheckNull(yffrate.getRatejR1()) + "/" + CheckNull(yffrate.getRatejR2()) + "/" + CheckNull(yffrate.getRatejR3()) + "/" + CheckNull(yffrate.getRatejR4()) + "/" + CheckNull(yffrate.getRatejR5()) + "/" + CheckNull(yffrate.getRatejR6());
					break;
				case 7:
					strDesc = "0~" + CheckNull(yffrate.getRatejTd1())+ "~" + CheckNull(yffrate.getRatejTd2()) +  "~" + CheckNull(yffrate.getRatejTd3()) + "~" + CheckNull(yffrate.getRatejTd4()) +  "~" + CheckNull(yffrate.getRatejTd5()) + "~" + CheckNull(yffrate.getRatejTd6()) + "|" 
								+ CheckNull(yffrate.getRatejR1()) + "/" + CheckNull(yffrate.getRatejR2()) + "/" + CheckNull(yffrate.getRatejR3()) + "/" + CheckNull(yffrate.getRatejR4()) + "/" + CheckNull(yffrate.getRatejR5()) + "/" + CheckNull(yffrate.getRatejR6())+ "/" + CheckNull(yffrate.getRatejR7());
					break;
			}
			break;
		}
		
		if(strDesc == null){
			strDesc=I18N.getText("invalid")+I18N.getText("flfa");	
		}
		
		return strDesc;
	}
	
	public static String CheckNull(Object obj){		
		return CommFunc.CheckString(((obj== null)?0.0: obj));
	}
	
	//return:json格式
	public JSONObject xmlToJSON(String xmlStr){
		String root = "card";
		
		JSONObject json = new JSONObject();
		String xmlText = getTagValue(xmlStr,root);//card标签内的值
		
		byte[] index = {1,2,3,4,6,7,8};//<card>子节点序号
		
		//block1
		String[] tags_value1 = {"block1_answerToReset","block1_version","block1_meterID","block1_customerID","block1_utilityID",
								"block1_sanctionedLoad","block1_meterType","block1_lastRechargeAmount","block1_lastRechargeDate",
								"block1_lastTransactionID"	};
		//block2
		String[] tags_value2 = {"block2_cardType","block2_cardUsedFlag","block2_tokenTotalNumber"};
		//block3
		String[] tags_value3 = {"block3_token"};
		//block4
		String[] tags_value4 = {"block4_meterID","block4_consumerID","block4_utilityID"};
		//block6
		String[] tags_value6 = {"block6_tokenReturnCode", "block6_RechargeDateTime", "block6_RechargeAmount"};
		//block7
		String[] tags_value7 = {"block7_LastMonth1","block7_LastMonth2","block7_LastMonth3","block7_LastMonth4","block7_LastMonth5","block7_LastMonth6"};
		String[] tags_value7_child = {"block7_billingDate","block7_activeEnergyImport","block7_takaRecharged","block7_takaUsed","block7_activeMaximumPower",
				"reactiveMaximumPower","block7_activeEnergyImportT1","block7_activeEnergyImportT2","block7_reactiveEnergyImportT1",
				"reactiveEnergyImportT2","block7_totalChargeT1","block7_totalChargeT2","block7_numberOfPowerFailures",
				"numberOfSanctionedLoadExceeded","block7_monthAveragePowerFactor"};
		//block8
		String[] tags_value8 = {"block8_returnToken","block8_updateFlag", "block8_keyNo", "block8_seqNo"};
		
		String tag = "", tag_xml = "";
		for(int i=0, nums = index.length; i<nums; i++){
			byte tag_index = index[i];
			tag = "block" + tag_index;
			switch(tag_index){
			case 1:
				tag_xml = getTagValue(xmlText,tag);
				setChildTagToJSON(json,tag_xml,tags_value1);
				break;
			case 2:
				tag_xml = getTagValue(xmlText,tag);
				setChildTagToJSON(json,tag_xml,tags_value2);
				break;
			case 3:
				tag_xml = getTagValue(xmlText,tag);
				setChildTagToJSON(json,tag_xml,tags_value3);
				break;
			case 4:
				tag_xml = getTagValue(xmlText,tag);
				setChildTagToJSON(json,tag_xml,tags_value4);
				break;
			case 6:
				tag_xml = getTagValue(xmlText,tag);
				setChildTagToJSON(json,tag_xml,tags_value6);
				break;
			case 8:
				tag_xml = getTagValue(xmlText,tag);
				setChildTagToJSON(json,tag_xml,tags_value8);
				break;
			case 7:
				tag_xml = getTagValue(xmlText,tag);
				String tag_c_xml = "";
				for(int j=0, c_nums = tags_value7.length; j<c_nums; j++){
					tag_c_xml = getTagValue(tag_xml,tags_value7[j]);
					JSONObject json_c = new JSONObject();
					setChildTagToJSON(json_c,tag_c_xml,tags_value7_child);
					json.put(tags_value7[j], json_c);		//当block7时，属性值为一个新的json
				}
				break;
			}
		}
		return json;
	}

	//将标签中的子标签值，转换为json对象属性
	@JSON(serialize=false)
	public void setChildTagToJSON(JSONObject json, String xmlStr, String[] tags){
		String value = "";
		for(int i=0, nums = tags.length; i<nums; i++){
			value = getTagValue(xmlStr, tags[i]);
			json.put(tags[i], value);
		}
	}
	
	//获取<tag>value</tag>中的value
	@JSON(serialize=false)
	public String getTagValue(String xmlStr, String tag){
		//拆分掉block_
		if(tag.contains("_")){
			tag = tag.split("_")[1];
		}
		
		String value = "";
		String s_tag = "<" + tag + ">";
		String e_tag = "</" + tag + ">";
		//不存在此标签则返回空
		if(xmlStr.isEmpty() || !xmlStr.contains(s_tag) || !xmlStr.contains(e_tag)){
			return SDDef.EMPTY;
		}
		
		value = xmlStr.split(s_tag)[1];
		if(value.equals(e_tag)){
			value = SDDef.EMPTY;
		}
		else{
			value = value.split(e_tag)[0];
		}
		                             
		return value;
	}	

	//辅助方法
	//将接口返回的xml字符串中的tokens标签拆分数据,添加到tokenList
	public String splitXmlByToken(String xmlStr, List<String> tokenList){
		String error_start_tag 	= "<errorCode>";
		String error_end_tag 	= "</errorCode>";
		String token_start_tag 	= "<token>";
		String token_end_tag 	= "</token>";
		String[] split1 		= null;
		String errorDesc		= "";
		
		//需要商议如果没有错误是否返回错误标签
		if(xmlStr == null || xmlStr.isEmpty() || xmlStr.indexOf(error_start_tag) < 0){
			return "Xmlfile is error!";
		}
		
		split1 = xmlStr.split(error_start_tag);
		for(int i=0, nums = split1.length; i<nums; i++){
			if(split1[i].indexOf(error_end_tag) < 0) continue;
			if(split1[i].split(error_end_tag)[0].equals("0")) continue;
			
			errorDesc += getErrorDesc(split1[i].split(error_end_tag)[0]) + "|";
		}
		

		if(errorDesc.length() > 0) {
			return errorDesc.substring(0, errorDesc.length() -1);
		}
		
		if(xmlStr == null || xmlStr.isEmpty() || xmlStr.indexOf(token_start_tag) < 0){
			return "Xmlfile is error!";
		}
		
		split1 = xmlStr.split(token_start_tag);
		for(int i=0, nums = split1.length; i<nums; i++){
			if(split1[i].indexOf(token_end_tag) < 0) continue;
			tokenList.add(split1[i].split(token_end_tag)[0]);
		}
		return "";
	}
	
	@JSON(serialize=false)
	public String getErrorDesc(String errorCode) {
		String desc = "";
		switch (CommBase.strtoi(errorCode)) {
			case 1:	//序列号不被接受
				desc = "sequence number is not excpected!";
				break;
			case 2:	//费率指数错误，不在范围内
				desc = "tariff Index argument error, not in scope!";
				break;
			case 3:	//密钥版本，不在范围内
				desc =  "key Version argument not in scope!";
				break;
			case 4: //密钥到期时间，不在范围内（0-255）
				desc = "key Expired Time not in scope(0-255)!";
				break;
			case 5: //密钥号 超出65535或《0
				desc = "key No exceed 65535 or < 0!";
				break;
			case 6: //电表编号不被接受
				desc = "meter No is not excepted!";
				break;
			case 7: //信用金额<= 0
				desc =  "credit amount <= 0!";
				break;
			case 8: //未知原因
				desc = "unknown reason!";
				break;
			default:
				desc = "unknown reason!";
				break;
		}
		return desc;
	}
	
	//将YYYYMMDD转换为YYYY-MM-DD格式
	public String formatToday(){
		String ymd = CommFunc.nowYMD();
		ymd = ymd.substring(0, 4) + "-" + ymd.substring(4, 6) + "-" + ymd.substring(6, 8);		
		return ymd;
	}
	
	//根据id加载费率信息 
	public YffRatePara loadRateParaById(short feeproj_id){
		HibDao hib_dao = new HibDao();
		YffRatePara rate = (YffRatePara)hib_dao.loadById(YffRatePara.class, feeproj_id);
		return rate;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public int getErrState() {
		return errState;
	}
	public void setErrState(int errState) {
		this.errState = errState;
	}


//	//工具卡操作
//	public String cardToolOper(JSONObject json_param){
//		CKeToken ckeToken = new CKeToken();	//引入API操作类
//		
//		String meterNo = json_param.getString("meterNo");		//电表编号	
//		String customerID = json_param.getString("customerID");	//客户编号
//		String utilityID = json_param.getString("utilityID");	//单位id
//		
//		
//		//以下这些参数或许可以在java中固定定义
//		int tarrifIndex = json_param.getInt("tarrifIndex");
//		int keyVersion = json_param.getInt("keyVersion");
//		int keyExpiredTime = json_param.getInt("keyExpiredTime");
//		long keyNo = json_param.getLong("keyNo");
//		String nSgc = json_param.getString("nSgc");
//		int nKeyVersion = json_param.getInt("tarrifIndex");
//		int nKeyExpiredTime = json_param.getInt("tarrifIndex");
//		long nKeyNo = json_param.getLong("nKeyNo");
//		
//		List<String> tokenList = new ArrayList<String>();
//		//1、设置最大功率极
//		String Sgc = json_param.getString("Sgc");
//		int seqNo =  json_param.getInt("seqNo");
//		int activationModel = json_param.getInt("activationModel");
//		String date = json_param.getString("date");
//		int[] maxPowerLimits = null;
//		int[] hours = null;
//		String data_MPLToken = ckeToken.getMaxPowerLimitToken(
//				meterNo, 
//				Sgc, 
//				tarrifIndex, 
//				nKeyVersion, 
//				nKeyExpiredTime, 
//				nKeyNo, 
//				seqNo, 
//				activationModel, 
//				date, 
//				maxPowerLimits, 
//				hours
//		);
//		splitXmlByToken(data_MPLToken, tokenList);
//		
//		//2、(设置阶梯费率)或其他费率
//		String activatingDate = "";
//		int activatingModel = 0, validDate = 0;
//		int[] rates = null, steps = null;
//		String data_STToken = ckeToken.getStepTariffToken(
//				meterNo, 
//				Sgc, 
//				tarrifIndex, 
//				nKeyVersion, 
//				nKeyExpiredTime, 
//				nKeyNo, 
//				seqNo, 
//				activatingDate, 
//				activatingModel, 
//				validDate, 
//				rates, 
//				steps
//		);
//		splitXmlByToken(data_STToken, tokenList);
//		
//		//3、(设置友好模式)
//		int friendMode = 0;
//		int[] times = null, days = null;
//		String  data_FMToken = ckeToken.getFriendModeToken(
//				meterNo, 
//				Sgc, 
//				tarrifIndex, 
//				nKeyVersion, 
//				nKeyExpiredTime, 
//				nKeyNo, 
//				seqNo, 
//				friendMode, 
//				times, 
//				days
//		);
//		splitXmlByToken(data_FMToken, tokenList);
//		
//		//4、设置信用额度极限和透支额度极限
//		int  amountType = 0, amountLimit = 0;
//		String data_SCALAOAL = ckeToken.getSetCreditAmountLimitOrOverdrawAmountLimitToken(
//				meterNo, 
//				Sgc, 
//				tarrifIndex, 
//				nKeyVersion, 
//				nKeyExpiredTime, 
//				nKeyNo, 
//				seqNo, 
//				amountType, 
//				amountLimit
//		);
//		splitXmlByToken(data_SCALAOAL, tokenList);
//		
//		//5、清除事件
//		String data_CLEToken = ckeToken.getClearEventToken(
//				meterNo, 
//				Sgc, 
//				tarrifIndex, 
//				keyVersion, 
//				keyExpiredTime, 
//				keyNo, 
//				seqNo
//		);
//		splitXmlByToken(data_CLEToken, tokenList);
//		
//		//6、加密---该方法如果参数确定，则可以提取为公共方法。
//		int nums = tokenList.size();
//		String[] tokens = new String[nums];
//		for(int i=0; i<nums; i++){
//			tokens[i] = tokenList.get(i);
//		}
//
//		
//		int answerToReset = 0;
//		int binaryPattern = 0;
//		int version = 0;
//		String meterID = meterNo;
//		long sanctionedLoad = 0;
//		int meterType = 0;
//		int lastRechargeAmount = 0;
//		String lastRechargeDate = "080313";
//		String lastTransactionID = "";
//		int cardType = 0;
//		String pwdData = "";  
//		int TokenTotalNumber = tokens.length;
//		
//		String dataToCard = ckeToken.encryptSmartCardReturnData(
//				answerToReset, 
//				binaryPattern, 
//				version, 
//				meterID, 
//				customerID, 
//				utilityID, 
//				sanctionedLoad, 
//				meterType, 
//				lastRechargeAmount, 
//				lastRechargeDate, 
//				lastTransactionID, 
//				cardType, 
//				pwdData, 
//				TokenTotalNumber, 
//				tokens
//		);
//		return dataToCard;	//返回写卡字符串
//	}
}
