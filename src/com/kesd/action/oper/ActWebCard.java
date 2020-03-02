package com.kesd.action.oper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.common.YDTable;
import com.kesd.comntpara.ComntUseropDef;
import com.kesd.dbpara.YffManDef;
import com.kesd.dbpara.YffRatePara;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;

/**
 * 读写卡用共通Action
 * @author Administrator
 *
 */
public class ActWebCard {

	private String 			result;
	private String			card_no_np;
	
	private String 			STR_NULL = "";	//空字符串
	class NPCARD_KEBLOCK_PARA
	{     
		String 	card_type 	= "81";			//自定义卡类型 (黑名单或者其他)
		byte	kecard_type = 0;			//操作类型 NPCARD_KEEXPAN_BLACK   取值：1-5
		Integer	sub_gno		= 0;			//子组号（抄收有效）
		Integer	block_no	= 0;			//黑名单数
		String	cardno		= "";			//本卡卡号
		String	areano		= "";			//区域号
		String	meterno		= "";			//表号
		String	blkcardno   = "|||||||||";	//黑名单卡号
	};
	
	@JSON(serialize = false)
	public String getcardClear() {
		if(result == null || result == STR_NULL){
			
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JSONObject json = JSONObject.fromObject(result);
		int metertype = json.getInt("meter_type");//卡类型
		
		if(metertype == SDDef.YFF_CARDMTYPE_KE001){
			result = SDDef.YFF_METER_TYPE_ZNK + "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0";//24个参数
		}else if(metertype == SDDef.YFF_CARDMTYPE_KE003){
			result = SDDef.YFF_METER_TYPE_6103 + "|0|0|0|0|0|0|0|0|0|0|0";	//12个参数
		}else if(metertype == SDDef.YFF_CARDMTYPE_KE005){
			int now_remain = (int)(json.getDouble("now_remain") * 100);
			
			int type = now_remain == 0 ? SDDef.NPCARD_OPTYPE_INITPARA : SDDef.NPCARD_OPTYPE_REVER;
			
			result = SDDef.YFF_METER_TYPE_NP + "|0|" + card_no_np + "|" + now_remain + "|0|0|0|0|0|1|" + type; //添加信息卡内记录数据条数----------------------------------------------------------
		}
		//20131020添加新规约智能卡
		else if(metertype == SDDef.YFF_CARDMTYPE_KE006){
			result = SDDef.YFF_METER_TYPE_ZNK2 + "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0";//67个参数
		}

		return SDDef.SUCCESS;
	}
	
	@JSON(serialize = false)
	public String getExtcardClear() {
		if(result == null || result == STR_NULL){
			
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JSONObject json = JSONObject.fromObject(result);
		String cardtype = json.getString("cardtype");			//卡类型
		String card_pass = json.getString("card_pass");			
		
		result = "||"+ cardtype + "|||00||" + card_pass +"||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||";

//			
//		if(metertype == SDDef.YFF_CARDMTYPE_KE001){
//			result = SDDef.YFF_METER_TYPE_ZNK + "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0";//24个参数
//		}else if(metertype == SDDef.YFF_CARDMTYPE_KE003){
//			result = SDDef.YFF_METER_TYPE_6103 + "|0|0|0|0|0|0|0|0|0|0|0";	//12个参数
//		}else if(metertype == SDDef.YFF_CARDMTYPE_KE005){
//			int now_remain = (int)(json.getDouble("now_remain") * 100);
//			
//			int type = now_remain == 0 ? SDDef.NPCARD_OPTYPE_INITPARA : SDDef.NPCARD_OPTYPE_REVER;
//			
//			result = SDDef.YFF_METER_TYPE_NP + "|0|" + card_no_np + "|" + now_remain + "|0|0|0|0|0|1|" + type; //添加信息卡内记录数据条数----------------------------------------------------------
//		}
//		//20131020添加新规约智能卡
//		else if(metertype == SDDef.YFF_CARDMTYPE_KE006){
//			result = SDDef.YFF_METER_TYPE_ZNK2 + "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0";//67个参数
//		}

		return SDDef.SUCCESS;
	}
	
	/**
	 * 写卡页面js调用。返回写卡字符串。
	 * 
	 * @return
	 */
	@JSON(serialize = false)
	public String getcardWrtInfo(){
		
		if(result == null || result == STR_NULL){
			
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JSONObject json = JSONObject.fromObject(result);
		int metertype = json.getInt("meter_type");//卡类型
		
		if(metertype == SDDef.YFF_CARDMTYPE_KE001){
			result = getWriteCardStr(json);
		}else if(metertype == SDDef.YFF_CARDMTYPE_KE003){
			result = getWriteCardStr6103(json);
		}
		else if (metertype == SDDef.YFF_CARDMTYPE_KE005){
			result = getWriteCardStrNp(json);
		}
		else if (metertype == SDDef.YFF_CARDMTYPE_KE006){
			result = getWriteCardStr2(json);
		}
		
		return SDDef.SUCCESS;
		
	}
	
	//取得写卡字符串-智能卡
	String getWriteCardStr(JSONObject json){	
		String	CARD_NO  	= json.getString("writecard_no");//户号--20130604写卡时传写卡户号，写完卡存库的时候户号用的还是原来的用户户号。
		String	METER_NO 	= json.getString("meterno");	//表号
		int	CARD_TYPE 		= json.getInt("card_type") + 1;	//转成 开户 1， 缴费2， 补卡3  
		String	BUYP_VAL	= CommFunc.FormatPayMoney(json.getDouble("all_money") * 100);//单位：分
		int	BUY_TIMES		= json.getInt("buynum");
		String 	PT 			= json.getString("pt");
		String 	CT 			= json.getString("ct");
		short feeproj_id 	= Short.parseShort(json.getString("feeproj_id"));
	
		String	CHG_DATE 	= "20990102"; 
		String	CHG_TIME 	= "112300";
		int USER_TYPE 		= 1;	//01 单费率，02 复费率

		Double  tempval		= json.getDouble("alarm_val1"); 
		int ALARM_VAL1		= tempval.intValue();
			    tempval		= json.getDouble("alarm_val2");
		int ALARM_VAL2		= tempval.intValue();

		//费率方案1
		YffRatePara yff_rate_para = (YffRatePara)Rd.getRecord(YDTable.TABLECLASS_YFFRATEPARA,feeproj_id);
		
		if(yff_rate_para.getFeeType() == SDDef.YFF_FEETYPE_FFL) {
			USER_TYPE = 2;				//复费率
		}
		else if (yff_rate_para.getFeeType() == SDDef.YFF_FEETYPE_JTFL) {
			if (yff_rate_para.getMeterfeeType() == SDDef.YFF_FEETYPE_FFL){
				USER_TYPE = 2;			//复费率
			}else {
				USER_TYPE = 1;			//单费率
			}
		}
		else if (yff_rate_para.getFeeType() == SDDef.YFF_FEETYPE_MIXJT) {
			if (yff_rate_para.getMeterfeeType() == SDDef.YFF_FEETYPE_FFL){
				USER_TYPE = 2;			//复费率
			}else {
				USER_TYPE = 1;			//单费率
			}
		}
		else {
			USER_TYPE = 1;				//单费率
		}
		double[] FEE1 = {0.0,0.0,0.0,0.0,0.0};//尖峰平谷
		double[] FEE2 = {0.0,0.0,0.0,0.0,0.0};//尖峰平谷
		
		if ( yff_rate_para.getFeeType() == SDDef.YFF_FEETYPE_FFL){	
			FEE1[1] = yff_rate_para.getRatefJ();
			FEE1[2] = yff_rate_para.getRatefF();
			FEE1[3] = yff_rate_para.getRatefP();
			FEE1[4] = yff_rate_para.getRatefG();
		}
		else if ( yff_rate_para.getFeeType() == SDDef.YFF_FEETYPE_JTFL){//卡式 写第一阶梯电价
			double cardfee = yff_rate_para.getMeterfeeR();		
			if (cardfee < 0.001)	cardfee = yff_rate_para.getRatejR1();
			double[] tFEE1 = {cardfee,cardfee,cardfee,cardfee,cardfee};
			FEE1 = tFEE1;
		}
		else if ( yff_rate_para.getFeeType() == SDDef.YFF_FEETYPE_MIXJT){//卡式 写第一阶梯电价
			double cardfee = yff_rate_para.getMeterfeehjR();		
			if (cardfee < 0.001)	cardfee = yff_rate_para.getRatehjHr1R1();
			double[] tFEE1 = {cardfee,cardfee,cardfee,cardfee,cardfee};
			FEE1 = tFEE1;
		}
		else {	//单费率四个值传相同
			double cardfee = yff_rate_para.getRatedZ();
			double[] tFEE1 = {cardfee,cardfee,cardfee,cardfee,cardfee};
			FEE1 = tFEE1;			
		}
		FEE2 = FEE1;

		//组写卡字符串
		StringBuffer rtn_buf = new StringBuffer();
		rtn_buf.append(SDDef.YFF_METER_TYPE_ZNK + SDDef.STR_OR);//0//卡表类型
		rtn_buf.append(USER_TYPE 	+ SDDef.STR_OR);//1//01 单费率 02 复费率
		rtn_buf.append(0x83			+ SDDef.STR_OR);//2//参数更新标志位--
		rtn_buf.append(CHG_DATE 	+ SDDef.STR_OR);//3//分时日期--
		rtn_buf.append(CHG_TIME		+ SDDef.STR_OR);//4//分时时间--
		rtn_buf.append(ALARM_VAL1 	+ SDDef.STR_OR);//5//报警值1
		rtn_buf.append(ALARM_VAL2 	+ SDDef.STR_OR);//6//报警值2
		rtn_buf.append(CT		 	+ SDDef.STR_OR);//7//CT
		rtn_buf.append(PT		 	+ SDDef.STR_OR);//8//PT
		rtn_buf.append(METER_NO 	+ SDDef.STR_OR);//9//表号--
		rtn_buf.append(CARD_NO 		+ SDDef.STR_OR);//10//客户编号
		rtn_buf.append(CARD_TYPE 	+ SDDef.STR_OR);//11//1 开户 2 购电 3 补卡
		rtn_buf.append(BUYP_VAL		+ SDDef.STR_OR);//12//购电金额-分
		rtn_buf.append(BUY_TIMES 	+ SDDef.STR_OR);//13//购电次数
		rtn_buf.append(FEE1[0] 		+ SDDef.STR_OR);//14//费率信息1总费率
		rtn_buf.append(FEE1[1] 		+ SDDef.STR_OR);//15//尖费率
		rtn_buf.append(FEE1[2] 		+ SDDef.STR_OR);//16//峰费率
		rtn_buf.append(FEE1[3] 		+ SDDef.STR_OR);//17//平费率
		rtn_buf.append(FEE1[4] 		+ SDDef.STR_OR);//18//谷费率
		rtn_buf.append(FEE2[0] 		+ SDDef.STR_OR);//19//费率信息2总费率
		rtn_buf.append(FEE2[1] 		+ SDDef.STR_OR);//20//尖费率
		rtn_buf.append(FEE2[2] 		+ SDDef.STR_OR);//21//峰费率
		rtn_buf.append(FEE2[3] 		+ SDDef.STR_OR);//22//平费率
		rtn_buf.append(FEE2[4]);					//23//谷费率
		
		
		return rtn_buf.toString(); 
	}
	
	//取得写卡字符串-6103
	String getWriteCardStr6103(JSONObject json){
		
		String	CARD_NO  	= json.getString("writecard_no");			//户号--zjgpay_para的 写卡户号		
		Integer	CARD_TYPE 	= json.getInt("card_type");  				//card_type：开户/缴费/补卡  --  6103：0/1/2 ------  智能卡 1/2/3 。
		String	BUY_TIMES	= json.getString("buynum");
		int		PtCt		= json.getInt("pt") * json.getInt("ct");
	//	String 	PT 			= json.getString("pt");
	//	String 	CT 			= json.getString("ct");

		Double tempval		= (json.getString("pay_bmc").isEmpty() ? 0 : json.getDouble("pay_bmc")) * 100; 
		int    PAY_BMC		= tempval.intValue();							//表码差
			   tempval 		= (json.getString("alarm_val1").isEmpty() ? 0 : json.getDouble("alarm_val1")) * 100;
		int	   ALARM_VAL1	= tempval.intValue();							//报警电量1
			   tempval 		= (json.getString("alarm_val2").isEmpty() ? 0 : json.getDouble("alarm_val2")) * 100;
		int	   ALARM_VAL2	= tempval.intValue();							//报警电量1
			   tempval 		= (json.getString("limit_dl").isEmpty() ? 0 : json.getDouble("limit_dl")) * 100;
		int	   LIMIT_DL		= tempval.intValue();							//囤积限值	

		//组写卡字符串
		StringBuffer rtn_buf = new StringBuffer();
		rtn_buf.append(SDDef.YFF_METER_TYPE_6103 + SDDef.STR_OR);//卡表类型
		rtn_buf.append(CARD_TYPE 	+ SDDef.STR_OR);//1//0 开户 1 购电2 补卡
		rtn_buf.append(CARD_NO		+ SDDef.STR_OR);//2//客户编号
		rtn_buf.append(ALARM_VAL1 	+ SDDef.STR_OR);//3//报警电量1 ：0.01kwh
		rtn_buf.append(ALARM_VAL2 	+ SDDef.STR_OR);//4//报警电量2 ：0.01kwh
		rtn_buf.append(PAY_BMC		+ SDDef.STR_OR);//5//购电量	: 0.01kwh	
		rtn_buf.append(BUY_TIMES 	+ SDDef.STR_OR);//6//购电次数
		rtn_buf.append(LIMIT_DL 	+ SDDef.STR_OR);//7//囤积电量  0.01kwh
		rtn_buf.append(PtCt		 	+ SDDef.STR_OR);//8//CT
	//	rtn_buf.append(PT		 	+ SDDef.STR_OR);//9//PT
		rtn_buf.append(0 			+ SDDef.STR_OR);//9//reserve1
		rtn_buf.append(0 			+ SDDef.STR_OR);//10//reserve2
		rtn_buf.append(0 					 	  );//11//reserve3

		
		return rtn_buf.toString(); 
	}
	
	//取得写卡字符串-农排卡
	String getWriteCardStrNp(JSONObject json){
		String  AREA_NO		= json.getString("areano");
		String	CARD_NO  	= json.getString("cardno");		//卡号
		int		CARD_TYPE 	= json.getInt("card_type");		//转成  始化参数:0, 开户 :1, 缴费:2, 冲正:3  
		String	BUYP_VAL	= CommFunc.FormatPayMoney((json.getDouble("all_money") * 100));//单位：分
		int		BUY_TIMES	= json.getInt("buynum");

		int SETPARA_FLAG = 0;
		if (CARD_TYPE == SDDef.NPCARD_OPTYPE_INITPARA || 
			CARD_TYPE == SDDef.NPCARD_OPTYPE_OPEN) {
			SETPARA_FLAG = 1;
		}
		
        Date date = new Date();
        Calendar tmpCalen = new GregorianCalendar();
        tmpCalen.setTime(date);
        
        int YMD = tmpCalen.get(Calendar.YEAR) * 10000 + tmpCalen.get(Calendar.MONTH) * 100 + tmpCalen.get(Calendar.DAY_OF_MONTH);
        int HMS = tmpCalen.get(Calendar.HOUR) * 10000 + tmpCalen.get(Calendar.MINUTE) * 100 + tmpCalen.get(Calendar.SECOND);
		
		//组写卡字符串
		StringBuffer rtn_buf = new StringBuffer();		
		rtn_buf.append(SDDef.YFF_METER_TYPE_NP + SDDef.STR_OR);//卡表类型		
		rtn_buf.append(AREA_NO 	 	+ SDDef.STR_OR);	//区域号
		rtn_buf.append(CARD_NO 	 	+ SDDef.STR_OR);	//卡号
		rtn_buf.append(BUYP_VAL	 	+ SDDef.STR_OR);	//金额
		rtn_buf.append(BUY_TIMES 	+ SDDef.STR_OR);	//购电次数
		rtn_buf.append(YMD 		 	+ SDDef.STR_OR);	//日期
		rtn_buf.append(HMS 		 	+ SDDef.STR_OR);	//时间
		rtn_buf.append(0 		 	+ SDDef.STR_OR);	//表号
		rtn_buf.append(0 		 	+ SDDef.STR_OR);	//客户编号
		rtn_buf.append(SETPARA_FLAG	+ SDDef.STR_OR);	//写参数标志
		rtn_buf.append(CARD_TYPE);					//1//0 开户 1 购电2 补卡
		
		return rtn_buf.toString(); 
	}
	
	//取得写卡字符串-外接卡表
	@JSON(serialize = false)
	public String getWriteCardExt(){
		if(result == null || result == STR_NULL){
			
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JSONObject json = JSONObject.fromObject(result);
		
		
		String	user_no  	= json.getString("writecard_no");//户号--20130604写卡时传写卡户号，写完卡存库的时候户号用的还是原来的用户户号。
		String	meter_no 	= json.getString("meterno");	//表号
		int	card_type 		= json.getInt("card_type") + 1;	//转成 开户 1， 缴费2， 补卡3
				
		String	all_money	= CommFunc.FormatPayMoney(json.getDouble("all_money"));//单位：元
		int	buy_num			= json.getInt("buynum");
		Double 	pt 			= json.getDouble("pt");
		Double 	ct 			= json.getDouble("ct");

		short feeproj_id 	= Short.parseShort(json.getString("feeproj_id"));//费率类型

		Double  tempval		= json.getDouble("alarm_val1"); 
		int alarm_val1		= tempval.intValue();
			    tempval		= json.getDouble("alarm_val2");
		int alarm_val2		= tempval.intValue();
		
		int tz_val   		= json.getInt("tz_val");			//赊欠门限值
		String cardtype 	= json.getString("cardtype");		//预付费电表类型
		String card_area 	= json.getString("card_area");		//区域码
		String card_rand 	= json.getString("card_rand");		//随机数
		String card_pass 	= json.getString("card_pass");		//密码
		
		Double buy_dl		 = json.getDouble("buy_dl");		//写卡电量	
		Double pay_bmc		 = json.getDouble("pay_bmc");		//表码差
	//	int pay_bmc		 	 = json.getInt("pay_bmc");		//表码差
		
		int cacl_type 	 = json.getInt("cacl_type"); //预付费类型FK,LK
		
		int USER_TYPE 		= 1;	//01 单费率，02 复费率
	
		//费率方案1
		YffRatePara yff_rate_para = (YffRatePara)Rd.getRecord(YDTable.TABLECLASS_YFFRATEPARA,feeproj_id);
		
		//囤积限额
		String moneyLimit = yff_rate_para.getMoneyLimit().toString();
		
		if(yff_rate_para.getFeeType() == SDDef.YFF_FEETYPE_FFL) {
			USER_TYPE = 2;				//复费率
		}
		else if (yff_rate_para.getFeeType() == SDDef.YFF_FEETYPE_JTFL) {
			if (yff_rate_para.getMeterfeeType() == SDDef.YFF_FEETYPE_FFL){
				USER_TYPE = 2;			//复费率
			}else {
				USER_TYPE = 1;			//单费率
			}
		}
		else if (yff_rate_para.getFeeType() == SDDef.YFF_FEETYPE_MIXJT) {
			if (yff_rate_para.getMeterfeeType() == SDDef.YFF_FEETYPE_FFL){
				USER_TYPE = 2;			//复费率
			}else {
				USER_TYPE = 1;			//单费率
			}
		}
		else {
			USER_TYPE = 1;				//单费率
		}
		double[] FEE1 = {0.0,0.0,0.0,0.0,0.0};//尖峰平谷
		double[] FEE2 = {0.0,0.0,0.0,0.0,0.0};//尖峰平谷
		
		if ( yff_rate_para.getFeeType() == SDDef.YFF_FEETYPE_FFL){	
			FEE1[1] = yff_rate_para.getRatefJ();
			FEE1[2] = yff_rate_para.getRatefF();
			FEE1[3] = yff_rate_para.getRatefP();
			FEE1[4] = yff_rate_para.getRatefG();
		}
		else if ( yff_rate_para.getFeeType() == SDDef.YFF_FEETYPE_JTFL){//卡式 写第一阶梯电价
			double cardfee = yff_rate_para.getMeterfeeR();		
			if (cardfee < 0.001)	cardfee = yff_rate_para.getRatejR1();
			double[] tFEE1 = {cardfee,cardfee,cardfee,cardfee,cardfee};
			FEE1 = tFEE1;
		}
		else if ( yff_rate_para.getFeeType() == SDDef.YFF_FEETYPE_MIXJT){//卡式 写第一阶梯电价
			double cardfee = yff_rate_para.getMeterfeehjR();		
			if (cardfee < 0.001)	cardfee = yff_rate_para.getRatehjHr1R1();
			double[] tFEE1 = {cardfee,cardfee,cardfee,cardfee,cardfee};
			FEE1 = tFEE1;
		}
		else {	//单费率四个值传相同
			double cardfee = yff_rate_para.getRatedZ();
			double[] tFEE1 = {cardfee,cardfee,cardfee,cardfee,cardfee};
			FEE1 = tFEE1;			
		}
		FEE2 = FEE1;

		//组写卡字符串
		StringBuffer rtn_buf = new StringBuffer();
		
		YffManDef yffman = CommFunc.getYffMan();
		String operName = "";	//当前操作员
		if(yffman != null){
			operName = CommFunc.getYffMan().getName();
		}
		
		String operTime = CommFunc.nowDate();	//当前时间
		
		rtn_buf.append(user_no 		+ SDDef.STR_OR);//1//用户编号
		rtn_buf.append(meter_no 	+ SDDef.STR_OR);//2//表号
		rtn_buf.append(cardtype 	+ SDDef.STR_OR);//3//电表类型
		rtn_buf.append(card_area 	+ SDDef.STR_OR);//4//区域码
		
		if (cacl_type  != SDDef.YFF_CACL_TYPE_MONEY) {
			rtn_buf.append("LK" + SDDef.STR_OR);	//5//预付费类型
		}
		else {
			rtn_buf.append("FK" + SDDef.STR_OR);	//5//预付费类型
		}
		 
		rtn_buf.append(CommFunc.FormatAdd0(card_type,2)+ SDDef.STR_OR);//6//电卡类型:开户、购电
		rtn_buf.append("" 			+ SDDef.STR_OR);//7//参数更新标志位
		rtn_buf.append(card_pass	+ SDDef.STR_OR);//8//写卡密钥
		rtn_buf.append(card_rand 	+ SDDef.STR_OR);//9//随机数
		
		if (cacl_type  != SDDef.YFF_CACL_TYPE_MONEY) {
			rtn_buf.append( pay_bmc 	+ SDDef.STR_OR);	//10//购电值
		}
		else {
			rtn_buf.append(all_money 	+ SDDef.STR_OR);//10//购电值
		}
		
		String moneyLimt = CommFunc.CheckString(yff_rate_para.getMoneyLimit());
		
		rtn_buf.append("" 			+ SDDef.STR_OR);//11//累计购电值
		rtn_buf.append("" 			+ SDDef.STR_OR);//12//补卡次数
		rtn_buf.append(buy_num 		+ SDDef.STR_OR);//13//购电次数
		rtn_buf.append(operName		+ SDDef.STR_OR);//14//售电操作员
		rtn_buf.append(operTime 	+ SDDef.STR_OR);//15//售电时间
		rtn_buf.append(ct 			+ SDDef.STR_OR);//16//电流互感器变比
		rtn_buf.append(pt 			+ SDDef.STR_OR);//17//电压互感器变比
		rtn_buf.append(pt*ct		+ SDDef.STR_OR);//18//综合倍率
		rtn_buf.append("" 			+ SDDef.STR_OR);//19//电表常数
		rtn_buf.append("" 			+ SDDef.STR_OR);//20//限容功率
		rtn_buf.append("" 			+ SDDef.STR_OR);//21//报警方式
		rtn_buf.append(alarm_val1 	+ SDDef.STR_OR);//22//报警值1
		rtn_buf.append(alarm_val2 	+ SDDef.STR_OR);//23//报警值2
		rtn_buf.append(moneyLimt    + SDDef.STR_OR);//24//囤积限额--从费率方案中取
		rtn_buf.append(tz_val 		+ SDDef.STR_OR);//25//赊欠门限值----暂时赋值为0，不能透支。
		rtn_buf.append("" 			+ SDDef.STR_OR);//26//冻结日时1
		rtn_buf.append("" 			+ SDDef.STR_OR);//27//冻结日时2
		rtn_buf.append("" 			+ SDDef.STR_OR);//28//冻结日时3
		rtn_buf.append("" 			+ SDDef.STR_OR);//29//冻结日时4
		rtn_buf.append("" 			+ SDDef.STR_OR);//30//新费率表启动日期
		rtn_buf.append("" 			+ SDDef.STR_OR);//31//年时区1启动日期及对应的日时段表1
		rtn_buf.append("" 			+ SDDef.STR_OR);//32//年时区2启用日期及对应的日时段表2
		rtn_buf.append("" 			+ SDDef.STR_OR);//33//第一时段表第1个时段的费率号和起始时间
		rtn_buf.append("" 			+ SDDef.STR_OR);//34//第一时段表第2个时段的费率号和起始时间
		rtn_buf.append("" 			+ SDDef.STR_OR);//35//第一时段表第3个时段的费率号和起始时间
		rtn_buf.append("" 			+ SDDef.STR_OR);//36//第一时段表第4个时段的费率号和起始时间
		rtn_buf.append("" 			+ SDDef.STR_OR);//37//第一时段表第5个时段的费率号和起始时间
		rtn_buf.append("" 			+ SDDef.STR_OR);//38//第一时段表第6个时段的费率号和起始时间
		rtn_buf.append("" 			+ SDDef.STR_OR);//39//第一时段表第7个时段的费率号和起始时间
		rtn_buf.append("" 			+ SDDef.STR_OR);//40//第一时段表第8个时段的费率号和起始时间
		rtn_buf.append("" 			+ SDDef.STR_OR);//41//第一时段表第9个时段的费率号和起始时间
		rtn_buf.append("" 			+ SDDef.STR_OR);//42//第一时段表第10个时段的费率号和起始时间
		rtn_buf.append("" 			+ SDDef.STR_OR);//43//第二时段表第1个时段的费率号和起始时间
		rtn_buf.append("" 			+ SDDef.STR_OR);//44//第二时段表第2个时段的费率号和起始时间
		rtn_buf.append("" 			+ SDDef.STR_OR);//45//第二时段表第3个时段的费率号和起始时间
		rtn_buf.append("" 			+ SDDef.STR_OR);//46//第二时段表第4个时段的费率号和起始时间
		rtn_buf.append("" 			+ SDDef.STR_OR);//47//第二时段表第5个时段的费率号和起始时间
		rtn_buf.append("" 			+ SDDef.STR_OR);//48//第二时段表第6个时段的费率号和起始时间
		rtn_buf.append("" 			+ SDDef.STR_OR);//49//第二时段表第7个时段的费率号和起始时间
		rtn_buf.append("" 			+ SDDef.STR_OR);//50//第二时段表第8个时段的费率号和起始时间
		rtn_buf.append("" 			+ SDDef.STR_OR);//51//第二时段表第9个时段的费率号和起始时间
		rtn_buf.append("" 			+ SDDef.STR_OR);//52//第二时段表第10个时段的费率号和起始时间
		
		rtn_buf.append("" 			+ SDDef.STR_OR);//53//第一套费率阶梯数
		rtn_buf.append("" 			+ SDDef.STR_OR);//54//第一套费率阶梯1
		rtn_buf.append("" 			+ SDDef.STR_OR);//55//第一套费率阶梯2
		rtn_buf.append("" 			+ SDDef.STR_OR);//56//第一套费率阶梯3
		rtn_buf.append("" 			+ SDDef.STR_OR);//57//第一套费率阶梯4
		rtn_buf.append("" 			+ SDDef.STR_OR);//58//第一套费率阶梯5
		rtn_buf.append("" 			+ SDDef.STR_OR);//59//第一套费率阶梯6
		rtn_buf.append("" 			+ SDDef.STR_OR);//60//第一套费率阶梯7
		rtn_buf.append("" 			+ SDDef.STR_OR);//61//第一套费率阶梯8
		
		rtn_buf.append(FEE1[1] 			+ SDDef.STR_OR);//62//第一套费率费率1
		rtn_buf.append(FEE1[2] 			+ SDDef.STR_OR);//63//第一套费率费率2
		rtn_buf.append(FEE1[3] 			+ SDDef.STR_OR);//64//第一套费率费率3
		rtn_buf.append(FEE1[4] 			+ SDDef.STR_OR);//65//第一套费率费率4
		rtn_buf.append("" 			+ SDDef.STR_OR);//66//第一套费率费率5
		rtn_buf.append("" 			+ SDDef.STR_OR);//67//第一套费率费率6
		rtn_buf.append("" 			+ SDDef.STR_OR);//68//第一套费率费率7
		rtn_buf.append("" 			+ SDDef.STR_OR);//69//第一套费率费率8
		
		rtn_buf.append("" 			+ SDDef.STR_OR);//70//第二套费率阶梯数
		rtn_buf.append("" 			+ SDDef.STR_OR);//71//第二套费率阶梯1
		rtn_buf.append("" 			+ SDDef.STR_OR);//72//第二套费率阶梯2
		rtn_buf.append("" 			+ SDDef.STR_OR);//73//第二套费率阶梯3
		rtn_buf.append("" 			+ SDDef.STR_OR);//74//第二套费率阶梯4
		rtn_buf.append("" 			+ SDDef.STR_OR);//75//第二套费率阶梯5
		rtn_buf.append("" 			+ SDDef.STR_OR);//76//第二套费率阶梯6
		rtn_buf.append("" 			+ SDDef.STR_OR);//77//第二套费率阶梯7
		rtn_buf.append("" 			+ SDDef.STR_OR);//78//第二套费率阶梯8
		
		rtn_buf.append(FEE1[1] 			+ SDDef.STR_OR);//79//第二套费率费率1
		rtn_buf.append(FEE1[2] 			+ SDDef.STR_OR);//80//第二套费率费率2
		rtn_buf.append(FEE1[3]			+ SDDef.STR_OR);//81//第二套费率费率3
		rtn_buf.append(FEE1[4] 			+ SDDef.STR_OR);//82//第二套费率费率4
		rtn_buf.append("" 			+ SDDef.STR_OR);//83//第二套费率费率5
		rtn_buf.append("" 			+ SDDef.STR_OR);//84//第二套费率费率6
		rtn_buf.append("" 			+ SDDef.STR_OR);//85//第二套费率费率7
		rtn_buf.append("");							//86//第二套费率费率8
		
		result = rtn_buf.toString();
		return SDDef.SUCCESS;
	}
		
	/**
	 * 取得写卡字符串-农排扩展卡
	 * 工具 
	 * @param json
	 * @return
	 */
	@JSON(serialize = false)
	public String getWriteCardStrNpExt(){
		if(result == null || result == STR_NULL){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		NPCARD_KEBLOCK_PARA cardpara = new NPCARD_KEBLOCK_PARA(); 
		
		JSONObject json = JSONObject.fromObject(result);
		cardpara.kecard_type = Byte.parseByte(json.getString("op_type"));//操作类型
		cardpara.areano		= json.getString("areano");		//区域号
		cardpara.cardno		= json.getString("cardno");		//本卡卡号
		
		switch(cardpara.kecard_type){
		case SDDef.NPCARD_KEEXPAN_BLACK_ADD://增加黑名单
		case SDDef.NPCARD_KEEXPAN_BLACK_DEL://删除黑名单
			cardpara.block_no =	json.getInt("block_no");	
			cardpara.blkcardno=json.getString("blkcardno"); //黑名单卡号： 添加/删除的时候用旧卡号，小于10的都给空值
			break;			
		case SDDef.NPCARD_KEEXPAN_BLACK_CLEAR://清空黑名单
			break;
		case SDDef.NPCARD_KEEXPAN_BLACK_READ://抄收黑名单-制回抄卡
			cardpara.sub_gno = json.getInt("sub_gno");							
			break;
		case SDDef.NPCARD_KEEXPAN_OTHER: //其他卡类型-启停标志
			cardpara.sub_gno = 	json.getInt("usedflag");
			break;
		}
		
		
		//组写卡字符串
		StringBuffer rtn_buf = new StringBuffer();		
		rtn_buf.append(SDDef.YFF_METER_TYPE_NP	+ SDDef.STR_OR);//卡表类型		
		rtn_buf.append(cardpara.card_type		+ SDDef.STR_OR);//自定义卡类型 (黑名单或者其他)                          
		rtn_buf.append(cardpara.kecard_type 	+ SDDef.STR_OR);//操作类型 NPCARD_KEEXPAN_BLACK   取值：1-5 ; =5表示启停标志    
		rtn_buf.append(cardpara.sub_gno			+ SDDef.STR_OR);//子组号（抄收有效） 0-4组; 其他传0 ; 启停标志时：0不启用，1启用。                                
		rtn_buf.append(cardpara.block_no		+ SDDef.STR_OR);//黑名单数   :添加删除黑名单时用。：其他传0
		rtn_buf.append(cardpara.cardno 		 	+ SDDef.STR_OR);//本卡卡号                                      
		rtn_buf.append(cardpara.areano 		 	+ SDDef.STR_OR);//区域号                                       
		rtn_buf.append(cardpara.meterno		 	+ SDDef.STR_OR);//表号           =空值。                         	    
		rtn_buf.append(cardpara.blkcardno					  );//黑名单卡号： 添加/删除的时候用旧卡号，小于10的都给空值
		
		result = rtn_buf.toString();
		return SDDef.SUCCESS;
	}
	
	/**
	 * 读卡返回json-农排扩展卡-黑名单页面用
	 * 根据读卡字符串的手机号查询客户名称
	 * @return
	 */
	@JSON(serialize = false)
	public String getNpExtConsInfo(){
		StringBuffer ret_buf = new StringBuffer(); 
		
		if(result == null || result == STR_NULL){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JSONObject json = JSONObject.fromObject(result);
		String strinfo = json.getString("strarr");
		strinfo =strinfo.substring(2, strinfo.length()-2);
		strinfo = strinfo.replaceAll("\",\"", ",");
		String[] arrinfo = strinfo.split(",");
		
		String[] phone= new String[10];
		String[] datetime= new String[10];
		String[] cons= new String[10];
		String phonearr="";
		byte kecard_type = Byte.parseByte(arrinfo[19]);//自定义卡类型
		if(kecard_type != SDDef.NPCARD_KEEXPAN_BLACK_READ){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;			
		}
		Integer block_no = Integer.parseInt(arrinfo[21]);//黑名单个数
		if(block_no==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		for(int i=0;i<block_no ;i++){
			phone[i]   = arrinfo[25+(i*2)];
			datetime[i]= arrinfo[26+(i*2)];
			phonearr+=",'" + phone[i] + "'";			
		}
		phonearr = phonearr.substring(1);
		String sql= " select  f.describe farmer_desc,card_no_old from changecardnp chg, areapara a, meter_extparanp me,farmerpara f "
				  + " where me.area_id = a.id and chg.area_id = a.id and chg.area_id = f.area_id and chg.farmer_id = f.id and chg.card_no_old != chg.card_no_new " 
				  +	" and card_no_old in(" + phonearr + ")";
		
		JDBCDao j_dao = new JDBCDao();
		List<Map<String, Object>> list = j_dao.result(sql);
		if(list.size()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		}

		for(int index =0;index<list.size();index++){
			Map<String,Object> map=list.get(index);
				
			for(int i=0;i<block_no ;i++){
				if(phone[i].equals(map.get("card_no_old"))){
					cons[i] = CommBase.CheckString(map.get("farmer_desc"));
					break;
				}				
			}			
		}
		
		ret_buf.append(SDDef.JSONROWSTITLE);
		for(int i=0;i<block_no ;i++){
			ret_buf.append(SDDef.JSONLBRACES);		
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + i + SDDef.JSONNZDATA);
			ret_buf.append(SDDef.JSONQUOT + (i+1) + SDDef.JSONCCM);//序号
			ret_buf.append(SDDef.JSONQUOT + cons[i]	+ SDDef.JSONCCM);		//客户名称
			ret_buf.append(SDDef.JSONQUOT + phone[i]	+ SDDef.JSONCCM);		//卡号
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD("20"+datetime[i].substring(0,6))+	CommFunc.FormatToHMS(datetime[i].substring(6),0)+ SDDef.JSONQBRRBCM);		//设置时间
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		
		result = ret_buf.toString();
		return SDDef.SUCCESS;
	}
	
	//20131021添加新规约智能卡
	//取得写卡字符串-智能卡2013
	String getWriteCardStr2(JSONObject json){
		String	CARD_NO  	= json.getString("writecard_no");//户号
		String	METER_NO 	= json.getString("meterno");	//表号
		int		CARD_TYPE 	= json.getInt("card_type") + 1;	//转成 开户 1， 缴费2， 补卡3  
		String	BUYP_VAL	= CommFunc.FormatPayMoney(json.getDouble("all_money") * 100);//单位：分
		int		BUY_TIMES	= json.getInt("buynum");
		String 	PT 			= json.getString("pt");
		String 	CT 			= json.getString("ct");
		String	CHG_DATE	="";
		String	CHG_TIME 	="";
		String 	JT_CYCLE_MDH = "";
		String 	JT_CHGYMD, JT_CGHM;
		
		CHG_DATE 	 = 	dataFromJson(json,"chg_date",	"20990101");				//分时日期
		CHG_TIME 	 = 	dataFromJson(json,"chg_time",	"112359"); 				//分时时间
		JT_CYCLE_MDH = 	dataFromJson(json,"jt_cycle_md","1231") + "00";			//阶梯年结算日MMDDHH,从界面传过来的只有MMDD
		JT_CHGYMD 	 = 	dataFromJson(json,"jt_chgymd",	"990101");				//阶梯切换YMD
		JT_CGHM 	 = 	dataFromJson(json,"jt_cghm",	"0000");					//阶梯切换HM
				
		Double  tempval		= json.getDouble("alarm_val1"); 
		int ALARM_VAL1		= tempval.intValue();
			    tempval		= json.getDouble("alarm_val2");
		int ALARM_VAL2		= tempval.intValue();

		//费率方案1
		short feeproj_id 	= Short.parseShort(json.getString("feeproj_id"));
		YffRatePara yff_rate_para = (YffRatePara)Rd.getRecord(YDTable.TABLECLASS_YFFRATEPARA,feeproj_id);
	    
		double[] FEE1 = new double[25];
		double[] FEE2 = new double[25];
		//初始化：0.0
		for(int i=0; i<25; i++){
			FEE1[i] = 0.0;
			FEE2[i] = 0.0;
		}
		
		//复费率
		if ( yff_rate_para.getFeeType() == SDDef.YFF_FEETYPE_FFL){
			FEE1[0] = yff_rate_para.getRatefJ()*10000;
			FEE1[1] = yff_rate_para.getRatefF()*10000;
			FEE1[2] = yff_rate_para.getRatefP()*10000;
			FEE1[3] = yff_rate_para.getRatefG()*10000;
			
			for(int i= 4; i<20; i++){
				FEE1[i] = 0.0;
			}
			//阶梯年结算日  dubr 20140116  将阶梯年结算日设为9 表示该值无效
			for(int i=20; i<25; i++){
				FEE1[i] = 999999;
			}
			//end
		}
		//阶梯费率  dubr 20140123
		else if ( yff_rate_para.getFeeType() == SDDef.YFF_FEETYPE_JTFL){//卡式 写第一阶梯电价
		    //单费率 复费率
			if (yff_rate_para.getMeterfeeType() == SDDef.YFF_FEETYPE_DFL || yff_rate_para.getMeterfeeType() == SDDef.YFF_FEETYPE_FFL){
		    	for(int i=0; i<5; i++){
				   FEE1[i] = yff_rate_para.getMeterfeeR()*10000;
				}					
				for(int i= 5; i<20; i++){
					FEE1[i] = 0.0;
				}
				//阶梯年结算日
				for(int i=20; i<25; i++){
					FEE1[i] = 999999;  //赋值为9  表示阶梯年结算日无效
				}
			
			}else if(yff_rate_para.getMeterfeeType() == SDDef.YFF_FEETYPE_JTFL){//阶梯费率
				for(int i=0; i<5; i++){
					FEE1[i] = 0;
				}
				//dubr 根据阶梯电价类型 设置梯度值
				double month  = yff_rate_para.getRatejType() == 0 ? 12:1;//年度方案 month等于12  其他方案 month等于 1
				double pt 	  = Double.parseDouble(PT);
				double ct     = Double.parseDouble(CT);
				double mul 	  = month/pt/ct;
				
				//阶梯梯度值赋值
				FEE1[5] = Math.round(yff_rate_para.getRatejTd1()*100*mul);
				FEE1[6] = Math.round(yff_rate_para.getRatejTd2()*100*mul);
				FEE1[7] = Math.round(yff_rate_para.getRatejTd3()*100*mul);
				for(int i=8; i<12; i++){
					FEE1[i] = FEE1[7];
				}
				//阶梯费率赋值
				FEE1[12] = yff_rate_para.getRatejR1()*10000;
				FEE1[13] = yff_rate_para.getRatejR2()*10000;
				FEE1[14] = yff_rate_para.getRatejR3()*10000;
				FEE1[15] = yff_rate_para.getRatejR4()*10000;
				for(int i=16; i<20; i++){
					FEE1[i] = 0;
				}
				//阶梯年结算日   年度方案   从mppay_para表中查询出jt_cycle_md字段 ，其他方案 设为999999  表示无效  dubr
				if(yff_rate_para.getRatejType() == 0){
					for(int i=20; i<25; i++){
						FEE1[i] = Integer.parseInt(JT_CYCLE_MDH);
					}
				}else{
					for(int i=20; i<25; i++){
						FEE1[i] = 999999;
					}
				}
			}
		}
		//混合阶梯费率 dubr 20140123
		else if (yff_rate_para.getFeeType() == SDDef.YFF_FEETYPE_MIXJT){//卡式 写复合阶梯电价
			//单费率 复费率
			if(yff_rate_para.getMeterfeehjType() == SDDef.YFF_FEETYPE_DFL || yff_rate_para.getMeterfeehjType() == SDDef.YFF_FEETYPE_FFL){
				for(int i=0; i<5; i++){
					FEE1[i] = yff_rate_para.getMeterfeehjR()*10000;
					}					
				for(int i= 5; i<20; i++){
					FEE1[i] = 0.0;
					}
					//阶梯年结算日
				for(int i=20; i<25; i++){
					FEE1[i] = 999999;//赋值为9  表示阶梯年结算日无效
					}
			}else if(yff_rate_para.getMeterfeehjType() == SDDef.YFF_FEETYPE_JTFL){
			    //阶梯费率
				for(int i=0; i<5; i++){
					FEE1[i] = 0;
				}
				//dubr 根据阶梯电价类型 设置梯度值
				double month  = yff_rate_para.getRatehjType() == 0 ? 12:1;//dubr 年度方案 month等于12 月度方案 month等于 1
				double pt 	  = Double.parseDouble(PT);
				double ct     = Double.parseDouble(CT);
				double mul    = month/pt/ct;		
				
				
				//混合阶梯-阶梯梯度值赋值
				FEE1[5] = Math.round(yff_rate_para.getRatehjHr1Td1()*100*mul);
				FEE1[6] = Math.round(yff_rate_para.getRatehjHr1Td2()*100*mul);
				FEE1[7] = Math.round(yff_rate_para.getRatehjHr1Td3()*100*mul);
				for(int i=8; i<12; i++){
					FEE1[i] = FEE1[7];
				}
				//end
				
				//混合阶梯-阶梯费率赋值
				//阶梯费率=	第1比例阶梯费率n * 混合比例1 + 第2比例电价 * 混合比例2 + 第3比例电价 * 混合比例3
				//第二比例电价
				double feePart2 = yff_rate_para.getRatehjHr2()* yff_rate_para.getRatehjBl2()/100;	//比例在库中没有取百分比
				//第三比例电价
				double feePart3 = yff_rate_para.getRatehjHr3()* yff_rate_para.getRatehjBl3()/100;
				
				//第一比例电价(阶梯)
				FEE1[12] = (yff_rate_para.getRatehjHr1R1()*yff_rate_para.getRatehjBl1()/100+ feePart2 + feePart3)*10000;
				FEE1[13] = (yff_rate_para.getRatehjHr1R2()*yff_rate_para.getRatehjBl1()/100+ feePart2 + feePart3)*10000;
				FEE1[14] = (yff_rate_para.getRatehjHr1R3()*yff_rate_para.getRatehjBl1()/100+ feePart2 + feePart3)*10000;
				FEE1[15] = (yff_rate_para.getRatehjHr1R4()*yff_rate_para.getRatehjBl1()/100+ feePart2 + feePart3)*10000;
				for(int i=16; i<20; i++){
					FEE1[i] = 0;
				}
				//阶梯年结算日   dubr 年度方案   从mppay_para表中查询出jt_cycle_md字段   月度方案 设为999999  表示无效
				if(yff_rate_para.getRatehjType() == 0){
					for(int i=20; i<25; i++){
						FEE1[i] = Integer.parseInt(JT_CYCLE_MDH);
					}
				}else{
					for(int i=20; i<25; i++){
						FEE1[i] = 999999; 
					}
				}			
			}			
		}else if(yff_rate_para.getFeeType() == SDDef.YFF_FEETYPE_DFL){	//单费率四个值传相同
			double cardfee = yff_rate_para.getRatedZ()*10000;
			for(int i=0; i<5; i++){
				FEE1[i] = cardfee;
			}
			for(int i= 5; i<20; i++){
				FEE1[i] = 0.0;
			}
			//阶梯年结算日
			for(int i=20; i<25; i++){
				FEE1[i] = 999999;//赋值为9  表示阶梯年结算日无效
			}
		
		}else{//混合费率,四个值相同
			double fee1 = yff_rate_para.getRateh1() * 10000 * yff_rate_para.getRatehBl1()/100;//混合费率*混合比例/百分比
			double fee2 = yff_rate_para.getRateh2() * 10000 * yff_rate_para.getRatehBl2()/100;
			double fee3 = yff_rate_para.getRateh3() * 10000 * yff_rate_para.getRatehBl3()/100;
			double fee4 = yff_rate_para.getRateh4() * 10000 * yff_rate_para.getRatehBl4()/100;
			double cardfee = fee1 + fee2 + fee3 + fee4;
			for(int i=0; i<5; i++){
				FEE1[i] = cardfee;
			}		
			for(int i= 5; i<20; i++){
				FEE1[i] = 0.0;
			}
			//阶梯年结算日
			for(int i=20; i<25; i++){
				FEE1[i] = 999999;//赋值为9  表示阶梯年结算日无效
			}
		}
		
		FEE2 = FEE1;		
		//组写卡字符串
		StringBuffer rtn_buf = new StringBuffer();
		rtn_buf.append(SDDef.YFF_METER_TYPE_ZNK2 + SDDef.STR_OR);//0//卡表类型
		
		int flag = getChangeFlag(json.getInt("operType"));
		rtn_buf.append(flag			+ SDDef.STR_OR);//1//参数更新标志位--	开户、换表:0x8f；缴费、冲正、补卡为0，写卡更改费率:0x0a;
		
		rtn_buf.append(CHG_DATE 	+ SDDef.STR_OR);//2//分时日期--
		rtn_buf.append(CHG_TIME		+ SDDef.STR_OR);//3//分时时间--
		rtn_buf.append(ALARM_VAL1 	+ SDDef.STR_OR);//4//报警值1
		rtn_buf.append(ALARM_VAL2 	+ SDDef.STR_OR);//5//报警值2
		rtn_buf.append(CT		 	+ SDDef.STR_OR);//6//CT
		rtn_buf.append(PT		 	+ SDDef.STR_OR);//7//PT
		rtn_buf.append(METER_NO 	+ SDDef.STR_OR);//8//表号--
		rtn_buf.append(CARD_NO 		+ SDDef.STR_OR);//9//客户编号
		rtn_buf.append(CARD_TYPE 	+ SDDef.STR_OR);//10//1 开户 2 购电 3 补卡
		rtn_buf.append(BUYP_VAL		+ SDDef.STR_OR);//11//购电金额-分
		rtn_buf.append(BUY_TIMES 	+ SDDef.STR_OR);//12//购电次数
		
		rtn_buf.append(FEE1[0] 		+ SDDef.STR_OR);//13//总费率
		rtn_buf.append(FEE1[1] 		+ SDDef.STR_OR);//14//尖费率
		rtn_buf.append(FEE1[2] 		+ SDDef.STR_OR);//15//峰费率
		rtn_buf.append(FEE1[3] 		+ SDDef.STR_OR);//16//平费率
		rtn_buf.append(FEE1[4] 		+ SDDef.STR_OR);//17//谷费率
		rtn_buf.append(FEE1[5] 		+ SDDef.STR_OR);//18//阶梯值1
		rtn_buf.append(FEE1[6] 		+ SDDef.STR_OR);//19//阶梯值2
		rtn_buf.append(FEE1[7] 		+ SDDef.STR_OR);//20//阶梯值3
		rtn_buf.append(FEE1[8] 		+ SDDef.STR_OR);//21//阶梯值4
		rtn_buf.append(FEE1[9] 		+ SDDef.STR_OR);//22//阶梯值5
		rtn_buf.append(FEE1[10] 	+ SDDef.STR_OR);//23//阶梯值6
		rtn_buf.append(FEE1[11] 	+ SDDef.STR_OR);//24//阶梯值7
		rtn_buf.append(FEE1[12] 	+ SDDef.STR_OR);//25//阶梯费率1
		rtn_buf.append(FEE1[13] 	+ SDDef.STR_OR);//26//阶梯费率2
		rtn_buf.append(FEE1[14] 	+ SDDef.STR_OR);//27//阶梯费率3
		rtn_buf.append(FEE1[15] 	+ SDDef.STR_OR);//28//阶梯费率4
		rtn_buf.append(FEE1[16] 	+ SDDef.STR_OR);//29//阶梯费率5
		rtn_buf.append(FEE1[17] 	+ SDDef.STR_OR);//30//阶梯费率6
		rtn_buf.append(FEE1[18] 	+ SDDef.STR_OR);//31//阶梯费率7
		rtn_buf.append(FEE1[19] 	+ SDDef.STR_OR);//32//阶梯费率8
		rtn_buf.append(FEE1[20] 	+ SDDef.STR_OR);//33//阶梯年结算日1
		rtn_buf.append(FEE1[21] 	+ SDDef.STR_OR);//34//阶梯年结算日2
		rtn_buf.append(FEE1[22] 	+ SDDef.STR_OR);//35//阶梯年结算日3
		rtn_buf.append(FEE1[23] 	+ SDDef.STR_OR);//36//阶梯年结算日4
		rtn_buf.append(FEE1[24] 	+ SDDef.STR_OR);//37//阶梯年结算日5
		rtn_buf.append(JT_CHGYMD 	+ SDDef.STR_OR);//38//阶梯切换YMD
		rtn_buf.append(JT_CGHM		+ SDDef.STR_OR);//39//阶梯切换HM
		
		rtn_buf.append(FEE2[0] 		+ SDDef.STR_OR);//40//总费率
		rtn_buf.append(FEE2[1] 		+ SDDef.STR_OR);//41//尖费率
		rtn_buf.append(FEE2[2] 		+ SDDef.STR_OR);//42//峰费率
		rtn_buf.append(FEE2[3] 		+ SDDef.STR_OR);//43//平费率
		rtn_buf.append(FEE2[4] 		+ SDDef.STR_OR);//44//谷费率
		rtn_buf.append(FEE2[5] 		+ SDDef.STR_OR);//45//阶梯值1   
		rtn_buf.append(FEE2[6] 		+ SDDef.STR_OR);//46//阶梯值2   
		rtn_buf.append(FEE2[7] 		+ SDDef.STR_OR);//47//阶梯值3   
		rtn_buf.append(FEE2[8] 		+ SDDef.STR_OR);//48//阶梯值4   
		rtn_buf.append(FEE2[9] 		+ SDDef.STR_OR);//49//阶梯值5   
		rtn_buf.append(FEE2[10] 	+ SDDef.STR_OR);//50//阶梯值6   
		rtn_buf.append(FEE2[11] 	+ SDDef.STR_OR);//51//阶梯值7   
		rtn_buf.append(FEE2[12] 	+ SDDef.STR_OR);//52//阶梯费率1  
		rtn_buf.append(FEE2[13] 	+ SDDef.STR_OR);//53//阶梯费率2  
		rtn_buf.append(FEE2[14] 	+ SDDef.STR_OR);//54//阶梯费率3  
		rtn_buf.append(FEE2[15] 	+ SDDef.STR_OR);//55//阶梯费率4  
		rtn_buf.append(FEE2[16] 	+ SDDef.STR_OR);//56//阶梯费率5  
		rtn_buf.append(FEE2[17] 	+ SDDef.STR_OR);//57//阶梯费率6  
		rtn_buf.append(FEE2[18] 	+ SDDef.STR_OR);//58//阶梯费率7  
		rtn_buf.append(FEE2[19] 	+ SDDef.STR_OR);//59//阶梯费率8  
		rtn_buf.append(FEE2[20] 	+ SDDef.STR_OR);//60//阶梯年结算日1
		rtn_buf.append(FEE2[21] 	+ SDDef.STR_OR);//61//阶梯年结算日2
		rtn_buf.append(FEE2[22] 	+ SDDef.STR_OR);//62//阶梯年结算日3
		rtn_buf.append(FEE2[23] 	+ SDDef.STR_OR);//63//阶梯年结算日4
		rtn_buf.append(FEE2[24] 	+ SDDef.STR_OR);//64//阶梯年结算日5
		rtn_buf.append(JT_CHGYMD 	+ SDDef.STR_OR);//65//
		rtn_buf.append(JT_CGHM);					//66//
		return rtn_buf.toString(); 
	}
	
	//智能卡2013根据操作类型，获取更新标识
	public int getChangeFlag(int operType){
		int flag = 0;
		if(operType == ComntUseropDef.YFF_DYOPER_ADDRES || operType == ComntUseropDef.YFF_DYOPER_CHANGEMETER 
			|| operType == ComntUseropDef.YFF_GYOPER_ADDCUS || operType == ComntUseropDef.YFF_GYOPER_CHANGEMETER	){
			flag = 0x8f;
		}
		else if(operType == ComntUseropDef.YFF_DYOPER_PAY || operType == ComntUseropDef.YFF_DYOPER_REPAIR 
				|| operType == ComntUseropDef.YFF_DYOPER_REVER || operType == ComntUseropDef.YFF_GYOPER_PAY
				|| operType == ComntUseropDef.YFF_GYOPER_REPAIR || operType == ComntUseropDef.YFF_GYOPER_REVER){
			flag = 0x00;  
		}else{
			flag = 0x0a;
		}
		return flag;
	}

	//20131216 zhp
	//获取从界面json中传过来的值
	//当json中没有jsonKey时，返回默认值defaultData
	public String dataFromJson(JSONObject json, String jsonKey, String defaultData){
		if(json.has(jsonKey)){
			return json.getString(jsonKey);
		}
		else{
			return defaultData;
		}
	}
	
	//get/set方法
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCard_no_np() {
		return card_no_np;
	}

	public void setCard_no_np(String cardNoNp) {
		card_no_np = cardNoNp;
	}
}
