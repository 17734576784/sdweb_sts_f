package com.kesd.action.dyjc;


import net.sf.json.JSONObject;

import com.hx.ami.spi.CKeToken;

import java.util.ArrayList;
import java.util.List;

import com.kesd.action.oper.ActOperDyBengal;
import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.common.WebConfig;
import com.kesd.dbpara.YffRatePara;
import com.kesd.util.I18N;
import com.libweb.common.CommBase;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ActMakeTools extends ActionSupport{
	private String result;

	private int hasErr = 1;
	private int noErr  = 0;
	private int errState = noErr;
	private static final String errorToken = "tokenname:";
	private static final String errorTag = "error:";
	

	public String execute(){
	
		String filed = result.substring(0,result.indexOf(SDDef.DATESEPARATOR));
		String[] tools = filed.split(SDDef.SPLITCOMA);
		String json1=result.split(SDDef.DATESEPARATOR)[1];
		JSONObject json = JSONObject.fromObject(json1);

//		feilv(feilv);
		try {
			result = Other(tools,json);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SDDef.SUCCESS;
	}
	public String Other(String other[],JSONObject json) throws IllegalAccessException{
		
		List<String> tokenList = new ArrayList<String>();
		ActOperDyBengal actoperdybengal = new ActOperDyBengal();
		CKeToken ckeToken = new CKeToken();
		
		String feeprojId = json.getString("feeprojId");
		
		String meterNo = json.getString("meterNo");				//电表编号
		String customerID = json.getString("customerID");		//客户编号
		String utilityID = json.getString("utilityID");			//单位id
//		int buy_times = CommBase.strtoi(json.getString("buy_times"));				//购电次数
		int seqNo =  CommBase.strtoi(json.getString("seqNo"));
		long nKeyNo = Long.parseLong(json.getString("keyNo"));
		
		YffRatePara ratePara = actoperdybengal.loadRateParaById(Short.parseShort(feeprojId));
		byte feeType = ratePara.getFeeType();										//获取费率类型		
//		String date = ratePara.getActivdate().toString();
//		String today = formatToday(date);
		String sgc = "123456";
		int tarrifIndex = 0;
		int nKeyVersion = 1;
		int nKeyExpiredTime = 1;
//		long nKeyNo = 1;
		String errorDesc = "";

		for(int i =0;i < other.length;i++ ) {
			if(other[i].equals("0")){
				continue;
			}
			
			if(other[i].equals("1")){  													//功率
//				String json1 = json.getString("fmaximum_power");
				int fmaximum_power = (int) (CommBase.strtof(json.getString("fmaximum_power")) * 10);
				int gmaximum_power = (int) (CommBase.strtof(json.getString("gmaximum_power")) * 10);
//				int power_start = CommBase.strtoi(json.getString("power_start"));
//				int power_end = CommBase.strtoi(json.getString("power_end"));
				int power_start = CommBase.strtoi(json.getString("power_start").isEmpty()?"20":json.getString("power_start"));
				int power_end = CommBase.strtoi(json.getString("power_end").isEmpty()?"2":json.getString("power_end"));
				
//				String date = json.getString("power_date");
//				String today = formatToday(date);
				String today = json.getString("power_date").isEmpty()?formatToday():CommFunc.FormatToYMD(json.getString("power_date"));
				int activationModel = 1;
//				String date = "2015-01-12";	//时间格式不能出错
				int[] maxPowerLimits = {fmaximum_power,gmaximum_power};
				int[] hours = {power_start,power_end};
				String data_MPLToken = ckeToken.getMaxPowerLimitToken(
						meterNo, 
						sgc, 
						tarrifIndex, 
						nKeyVersion, 
						nKeyExpiredTime, 
						nKeyNo, 
						seqNo, 
						activationModel, 
						today, 
						maxPowerLimits, 
						hours
				);
				errorDesc = actoperdybengal.splitXmlByToken(data_MPLToken, tokenList);
				continue;				
			}
			if(other[i].equals("2")){															//友好模式
				int friend_s = Integer.parseInt(json.getString("friend_s").isEmpty()?"20":json.getString("friend_s"));
				int friend_e = Integer.parseInt(json.getString("friend_e").isEmpty()?"2":json.getString("friend_e"));
				String friendDate = json.getString("friendDate");
				int friendMode = 1;	//是否启用友好模式 0 启用友好模式 1 不启用友好模式
				int[] times = {friend_s,friend_e}; //友好模式起止时间
				int[] days = new int[7];			//
				
				if (friendDate.length() > 8) friendDate = friendDate.substring(0, 8);
				for(int j=0, nums=friendDate.length(); j<nums; j++){
					if(j == nums-1) {
						friendMode = CommBase.strtoi(friendDate.substring(j,j+1)); //数据库中 0 不使用 1 使用
						if(friendMode == 1) {
							friendMode = 0;
						}
						else {
							friendMode = 1;
						}
					}
					else{
						days[j] = CommBase.strtoi(friendDate.substring(j,j+1));
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
				errorDesc = actoperdybengal.splitXmlByToken(data_FMToken, tokenList);
				if(errorDesc.length() > 0) {
					errState = hasErr;
					return errorToken + "'" + "getKeyChangeToken" + "'," + errorTag + errorDesc;
				}
				continue;
			}
			if(other[i].equals("3")){															//囤积值
				double money_limit = Double.parseDouble(json.getString("money_limit"));
				int  amountType = 0, //信用额度限值
				amountLimit = CommBase.strtoi(CommFunc.roundtostr(money_limit,0))*100;
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
			errorDesc = actoperdybengal.splitXmlByToken(data_SCALAOAL, tokenList);
			if(errorDesc.length() > 0) {
				errState = hasErr;
				return errorToken + "'" + "getKeyChangeToken" + "'," + errorTag + errorDesc;
			}
				
				continue;
			}
			if(other[i].equals("4")){															//透支额度
				int tzval = CommBase.strtoi(json.getString("tzval"))*100;
				
				int amountType = 1; //透支额度极限
				int amountLimit = tzval;
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
				
			errorDesc = actoperdybengal.splitXmlByToken(data_SCALAOAL, tokenList);
			if(errorDesc.length() > 0) {
				errState = hasErr;
				return errorToken + "'" + "getKeyChangeToken" + "'," + errorTag + errorDesc;
			}
				
				continue;
			}
			//消除事件
			if(other[i].equals("5")){
				String  clearSgc = "";
				int cleartarrifIndex = 0, clearkeyVersion = 0, clearkeyExpiredTime = 0; //clearseqNo = 0;
//				long keyNo = 0; 
				
				//1、清除事件
				String data_CLEToken = ckeToken.getClearEventToken(
						meterNo, 
						clearSgc, 
						cleartarrifIndex, 
						clearkeyVersion, 
						clearkeyExpiredTime, 
						nKeyNo, 
						seqNo
				);
				errorDesc = actoperdybengal.splitXmlByToken(data_CLEToken, tokenList);
				if(errorDesc.length() > 0) {
					errState = hasErr;
					return errorToken + "'" + "getKeyChangeToken" + "'," + errorTag + errorDesc;
				}
				continue;
			}
			//费率
			if(other[i].equals("6")){	
				
				//6、(设置阶梯费率)或其他费率,根据费率（三种，选择调用不同方法）------需仲哥再检查核对一下
				Integer activatingDateDB = ratePara.getActivdate();//费率激活日期 :月日
//				String month = activatingDateDB / 100 < 10 ? "0" + activatingDateDB / 100 : activatingDateDB / 100 + "";
//				String day = activatingDateDB % 100 < 10 ? "0" + activatingDateDB % 100 : activatingDateDB % 100 + "";
//				String year = CommFunc.nowYMD().substring(0, 4);
//				String activatingDate = year + "-" + month + "-" + day;//当前时间 
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
					
//					rates[0] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejR1()== null)?0.0: ratePara.getRatejR1()*100,0));//
//					rates[1] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejR2()== null)?0.0: ratePara.getRatejR2()*100,0));//
//					rates[2] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejR3()== null)?0.0: ratePara.getRatejR3()*100,0));//
//					rates[3] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejR4()== null)?0.0: ratePara.getRatejR4()*100,0));//
//					rates[4] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejR5()== null)?0.0: ratePara.getRatejR5()*100,0));//
//					rates[5] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejR6()== null)?0.0: ratePara.getRatejR6()*100,0));//
//					rates[6] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejR7()== null)?0.0: ratePara.getRatejR7()*100,0));//
//					
//					steps[0] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejTd1()== null)?0.0: ratePara.getRatejTd1(),0));//
//					steps[1] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejTd2()== null)?0.0: ratePara.getRatejTd2(),0));//
//					steps[2] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejTd3()== null)?0.0: ratePara.getRatejTd3(),0));//
//					steps[3] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejTd4()== null)?0.0: ratePara.getRatejTd4(),0));//
//					steps[4] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejTd5()== null)?0.0: ratePara.getRatejTd5(),0));//
//					steps[5] = CommBase.strtoi(CommFunc.roundtostr((ratePara.getRatejTd6()== null)?0.0: ratePara.getRatejTd6(),0));//
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
				
				errorDesc = actoperdybengal.splitXmlByToken(data_STToken, tokenList);
				if(errorDesc.length() > 0) {
					errState = hasErr;
					return errorToken + "'" + "getKeyChangeToken" + "'," + errorTag + errorDesc;
				}
				continue;
			}
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
	public String typeCard(){

		CKeToken ckeToken = new CKeToken();	//引入API操作类

		String errorDesc = "";
		int manufacturingID = 0x1234;
		int control = Integer.parseInt(result);

		String data_chgToken = ckeToken.getTestToken (manufacturingID, control);

		List<String> tokenList = new ArrayList<String>();
		ActOperDyBengal actoperbgl = new ActOperDyBengal(); 
		 
		errorDesc = actoperbgl.splitXmlByToken(data_chgToken, tokenList);
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
		String lastRechargeDate = "080313";
		String lastTransactionID = "";
		int cardType = 0x0BAC;
		String pwdData = "";  
		int TokenTotalNumber = tokens.length;
		String meterNo 		= "11111111"; 
		String customerID 	= "11111111"; 
		String utilityID	= "111111"; 

		result = ckeToken.encryptSmartCardReturnData(
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
	
		return SDDef.SUCCESS;
	}
//	public String formatToday(String date){
//		String ymd = CommFunc.nowYMD();
//		if(date == null){
//			ymd = ymd.substring(0, 4) + "-" + ymd.substring(4, 6) + "-" + ymd.substring(6,8);
//		}
//		ymd = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);		
//		return ymd;
//	}
	public String formatToday(){
		String ymd = CommFunc.nowYMD();
		ymd = ymd.substring(0, 4) + "-" + ymd.substring(4, 6) + "-" + ymd.substring(6, 8);		
		return ymd;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
