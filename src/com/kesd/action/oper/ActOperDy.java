package com.kesd.action.oper;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JSONObject;
import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.comnt.ComntDef;
import com.kesd.comnt.ComntMsg;
import com.kesd.comnt.ComntMsgDy;
import com.kesd.comnt.ComntMsgProc;
import com.kesd.comnt.ComntProtMsg;
import com.kesd.comntpara.ComntOperDyFunc;
import com.kesd.comntpara.ComntParaBase;
import com.kesd.comntpara.ComntUseropDef;
import com.kesd.dbpara.JSPara;
import com.kesd.service.DBOper;
import com.libweb.common.CommBase;
import com.libweb.comnt.ComntFunc;
import com.libweb.dao.JDBCDao;

public class ActOperDy {
	
	private static final long serialVersionUID = 3607391502741009698L;
	private String  userData1 			= null;		//RTU_ID
	private String  userData2 			= null;		//USEROP_ID
	private String 	result 				= null;		//SUCCESS/FAIL
	private String 	detailInfo 			= null;		//返回详细信息(JSON)
	private String	err_strinfo			= null;		//返回错误信息
	private String	task_resultinfo		= null;		//返回任务详细信息
	private String 	gsmflag				= null;		//是否发送短信
	private String  mpid				= null;		//测量点编号

	private String	dyOperAddres		= null;		//低压操作-开户
	private String	dyOperPay			= null;		//低压操作-缴费
	private String	dyOperRepair		= null;		//低压操作-补卡
	private String	dyOperRewrite		= null;		//低压操作-补写卡
	private String	dyOperRever			= null;		//低压操作-冲正
	private String	dyOperChangeMeter	= null;		//低压操作-换表换倍率
	private String	dyOperChangeRate	= null;		//低压操作-换电价
	private String	dyOperProtect		= null;		//低压操作-保电
	private String	dyOperUpdateState	= null;		//低压操作-强制状态更新
	private String	dyOperRecalc		= null;		//低压操作-重新计算剩余金额
	private String	dyOperJsbc			= null;		//低压操作-结算补差
	private String	dyOperReFxdf		= null;		//低压操作-发行电费		//20120730
	private String	dyOperReJtRest		= null;		//低压操作-重新阶梯清零	//20120730
	private String	dyOperGetRemain		= null;		//低压操作-返回余额
	private String	dyOperGParaState	= null;		//低压操作-获得预付费参数及状态
	private String	dyOperReStart		= null;		//低压操作-恢复
	private String	dyOperPause			= null;		//低压操作-暂停
	private String	dyOperDestory		= null;		//低压操作-销户
	private String	dyOperResetDoc		= null;		//低压操作-更改参数  预付费参数
    
	private String dyUpdateEsameNo		= null;     //保存esam表号
	public String getDyUpdateEsameNo() {
		return dyUpdateEsameNo;
	}

	public void setDyUpdateEsameNo(String dyUpdateEsameNo) {
		this.dyUpdateEsameNo = dyUpdateEsameNo;
	}


	private String operErr				= null;		//返回错误信息描述,在js弹出框中显示
	
	/**
	 * zhp
	 * 20131128修改说明：
	 * 功能：当主站操作失败时，在操作界面弹出框中提示出错误信息的描述和代码
	 * 每个方法中增加了对task_result_detail的值的返回。
	 * 增加了参数operErr,用于向js返回信息，
	 * 主方法中使用ComntParaBase.makeOperErrCode(task_result_detail)对operErr进行显示组合;
	*/

	//低压操作-开户
	private String DYAddRes(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;
		
		boolean ret_val = false;
		
		//发送
		if (dyOperAddres != null) dyOperAddres = dyOperAddres.trim();
		if (dyOperAddres == null || dyOperAddres.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(dyOperAddres);
		
		//20131019
		//ComntMsg.MSG_DYOPER_ADDRES dyoper = new ComntMsg.MSG_DYOPER_ADDRES();
		ComntMsgDy.MSG_DYOPER_ADDRES dyoper = new ComntMsgDy.MSG_DYOPER_ADDRES();
		
		dyoper.testf = 0;
		dyoper.gsmf  = gsm_flag;
		dyoper.mpid  = mp_id;
		ComntFunc.string2Byte(user_name, dyoper.operman);
		
		dyoper.yffalarmid   = (short)CommBase.strtoi(json.getString("myffalarmid"));
		dyoper.paytype  	= (byte)CommBase.strtoi(json.getString("paytype"));
		dyoper.opresult 	= op_result;
		dyoper.buynum 		= 1;

		dyoper.money.pay_money   = CommBase.strtof(json.getString("pay_money"));
		dyoper.money.othjs_money = CommBase.strtof(json.getString("othjs_money"));
		dyoper.money.zb_money	 = CommBase.strtof(json.getString("zb_money"));
		dyoper.money.all_money   = CommBase.strtof(json.getString("all_money"));
		dyoper.jt_total_zbdl 	 = CommBase.strtof(json.getString("jt_total_zbdl"));

		for(i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++){
			dyoper.bd[i].rtu_id = CommBase.strtoi(json.getString("rtu_id"));
			dyoper.bd[i].mp_id  = (short)CommBase.strtoi(json.getString("mp_id"  + i));
			dyoper.bd[i].date	= CommBase.strtoi(json.getString("date"));
			dyoper.bd[i].time	= CommBase.strtoi(json.getString("time"));
			
			dyoper.bd[i].bd_zy  = (short)CommBase.strtof(json.getString("bd_zy"  + i));
			dyoper.bd[i].bd_zyj = (short)CommBase.strtof(json.getString("bd_zyj" + i));
			dyoper.bd[i].bd_zyf = (short)CommBase.strtof(json.getString("bd_zyf" + i));
			dyoper.bd[i].bd_zyp = (short)CommBase.strtof(json.getString("bd_zyp" + i));
			dyoper.bd[i].bd_zyg = (short)CommBase.strtof(json.getString("bd_zyg" + i));
		}
		

		//jack 20140612 start
		String tmp_str = "";
		if (json.containsKey("buy_dl")) {
			dyoper.bz.buy_dl 		= CommBase.strtof(json.getString("buy_dl"));
			dyoper.bz.pay_bmc 		= CommBase.strtof(json.getString("pay_bmc"));
//			dyoper.bz.shutdown_bd 	= CommBase.strtof(json.getString("shutdown_bd"));
			
			dyoper.passrand.update_flag = CommBase.strtoi(json.getString("update_flag"));
			
			tmp_str = json.getString("card_rand");
			byte card_rand[] = ComntFunc.string2Byte(tmp_str);
			ComntFunc.arrayCopy(dyoper.passrand.card_rand, card_rand);
			
			tmp_str = json.getString("card_pass");
			byte card_pass[] = ComntFunc.string2Byte(tmp_str);
			ComntFunc.arrayCopy(dyoper.passrand.card_pass, card_pass);
			
//			tmp_str = json.getString("reserve1");
//			byte reserve1[] = ComntFunc.string2Byte(tmp_str);
//			ComntFunc.arrayCopy(dyoper.passrand.reserve1, reserve1);
//			
//			tmp_str = json.getString("reserve2");
//			byte reserve2[] = ComntFunc.string2Byte(tmp_str);
//			ComntFunc.arrayCopy(dyoper.passrand.reserve2, reserve2);			
		}
		//jack 20140612 end
		
		dyOperAddres = SDDef.EMPTY;
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
		ret_val = ComntOperDyFunc.DYAddRes(user_name, i_user_data1, i_user_data2, rtu_para, mp_id, dyoper, operidx, err_str_1,task_result_detail);
		if (ret_val) {
			dyOperAddres = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
		else {
			return SDDef.FAIL;
		}
	}	
	
	//低压操作-缴费
	private String DYPay(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;
		if (dyOperPay != null) dyOperPay = dyOperPay.trim();
		if (dyOperPay == null || dyOperPay.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		JSONObject json = JSONObject.fromObject(dyOperPay);
	
		ComntMsgDy.MSG_DYOPER_PAY dyoper = new ComntMsgDy.MSG_DYOPER_PAY();
		
		dyoper.testf = 0;
		dyoper.gsmf  = gsm_flag;
		dyoper.mpid  = mp_id;
		ComntFunc.string2Byte(user_name, dyoper.operman);
		dyoper.paytype  	= (byte)CommBase.strtoi(json.getString("paytype"));
		dyoper.opresult 	= op_result;
		dyoper.buynum 		= CommBase.strtoi(json.getString("buynum")) + 1;
		dyoper.money.pay_money  = CommBase.strtof(json.getString("pay_money"));
		dyoper.money.othjs_money= CommBase.strtof(json.getString("othjs_money"));
		dyoper.money.zb_money	= CommBase.strtof(json.getString("zb_money"));
		dyoper.money.all_money  = CommBase.strtof(json.getString("all_money"));
		
		//jack 20140612 start
		String tmp_str = "";
		if (json.containsKey("buy_dl")) {
			dyoper.bz.buy_dl 		= CommBase.strtof(json.getString("buy_dl"));
			dyoper.bz.pay_bmc 		= CommBase.strtof(json.getString("pay_bmc"));
			dyoper.bz.shutdown_bd 	= 0;
//			dyoper.bz.shutdown_bd 	= CommBase.strtof(json.getString("shutdown_bd"));

			dyoper.passrand.update_flag = CommBase.strtoi(json.getString("update_flag"));
			
			tmp_str = json.getString("card_rand");
			byte card_rand[] = ComntFunc.string2Byte(tmp_str);
			ComntFunc.arrayCopy(dyoper.passrand.card_rand, card_rand);
			
			tmp_str = json.getString("card_pass");
			byte card_pass[] = ComntFunc.string2Byte(tmp_str);
			ComntFunc.arrayCopy(dyoper.passrand.card_pass, card_pass);
			
//先不赋值 以后用到 再传即可
//			tmp_str = json.getString("reserve1");
//			byte reserve1[] = ComntFunc.string2Byte(tmp_str);
//			ComntFunc.arrayCopy(dyoper.passrand.reserve1, reserve1);
//			
//			tmp_str = json.getString("reserve2");
//			byte reserve2[] = ComntFunc.string2Byte(tmp_str);
//			ComntFunc.arrayCopy(dyoper.passrand.reserve2, reserve2);			
		}
		//jack 20140612 end
		
		
		dyOperPay = SDDef.EMPTY;	
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
		ret_val = ComntOperDyFunc.DYPay(user_name, i_user_data1, i_user_data2, rtu_para, mp_id, dyoper, operidx, err_str_1, task_result_detail);
		
		if (ret_val) {
			dyOperPay = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
		else {
			return SDDef.FAIL;
		}
		
	}	
	
	//低压操作-补卡
	private String DYRepair(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;
		if (dyOperRepair != null) dyOperRepair = dyOperRepair.trim();
		if (dyOperRepair == null || dyOperRepair.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		JSONObject json = JSONObject.fromObject(dyOperRepair);
		//String     []str_para	    = dyOperRepair.split(",");
		
		//20131019
		//ComntMsg.MSG_DYOPER_REPAIR dyoper = new ComntMsg.MSG_DYOPER_REPAIR();
		ComntMsgDy.MSG_DYOPER_REPAIR dyoper = new ComntMsgDy.MSG_DYOPER_REPAIR();
		
		dyoper.testf = 0;
		dyoper.gsmf  = gsm_flag;
		dyoper.mpid  = mp_id;
		ComntFunc.string2Byte(user_name, dyoper.operman);
		dyoper.opresult 	= op_result;
		
		dyoper.old_op_date	= CommBase.strtoi(json.getString("old_op_date"));
		dyoper.old_op_time	= CommBase.strtoi(json.getString("old_op_time"));
		
		//jack 20140612 start
		String tmp_str = "";
		if (json.containsKey("update_flag")) {
//			dyoper.bz.buy_dl 		= CommBase.strtof(json.getString("buy_dl"));
//			dyoper.bz.pay_bmc 		= CommBase.strtof(json.getString("pay_bmc"));
//			dyoper.bz.shutdown_bd 	= 0;
//			dyoper.bz.shutdown_bd 	= CommBase.strtof(json.getString("shutdown_bd"));

			dyoper.passrand.update_flag = CommBase.strtoi(json.getString("update_flag"));
			
			tmp_str = json.getString("card_rand");
			byte card_rand[] = ComntFunc.string2Byte(tmp_str);
			ComntFunc.arrayCopy(dyoper.passrand.card_rand, card_rand);
			
			tmp_str = json.getString("card_pass");
			byte card_pass[] = ComntFunc.string2Byte(tmp_str);
			ComntFunc.arrayCopy(dyoper.passrand.card_pass, card_pass);
			
//先不赋值 以后用到 再传即可
//			tmp_str = json.getString("reserve1");
//			byte reserve1[] = ComntFunc.string2Byte(tmp_str);
//			ComntFunc.arrayCopy(dyoper.passrand.reserve1, reserve1);
//			
//			tmp_str = json.getString("reserve2");
//			byte reserve2[] = ComntFunc.string2Byte(tmp_str);
//			ComntFunc.arrayCopy(dyoper.passrand.reserve2, reserve2);			
		}
		//jack 20140612 end
		
		dyOperRepair = SDDef.EMPTY;
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
	    ret_val = ComntOperDyFunc.DYRepair(user_name, i_user_data1, i_user_data2, rtu_para, mp_id, dyoper, operidx, err_str_1,task_result_detail);
		
	    if(ret_val){
			dyOperRepair = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
	    return SDDef.FAIL;
	}
	
	//低压操作-补写卡-未测试
	private String DYReWrite(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) {
		
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;
		
		if (dyOperRewrite != null) dyOperRewrite = dyOperRewrite.trim();
		if (dyOperRewrite == null || dyOperRewrite.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		JSONObject json = JSONObject.fromObject(dyOperRewrite);
		
		//20131019
		//ComntMsg.MSG_DYOPER_REWRITE dyoper = new ComntMsg.MSG_DYOPER_REWRITE();
		ComntMsgDy.MSG_DYOPER_REWRITE dyoper = new ComntMsgDy.MSG_DYOPER_REWRITE();
		
		dyoper.testf = 0;
		dyoper.gsmf  = gsm_flag;
		dyoper.mpid  = mp_id;
		ComntFunc.string2Byte(user_name, dyoper.operman);
		dyoper.opresult 	= op_result;
		dyoper.old_op_date	= CommBase.strtoi(json.getString(""));
		dyoper.old_op_time	= CommBase.strtoi(json.getString(""));
		
		dyOperRewrite = SDDef.EMPTY;
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
		ret_val = ComntOperDyFunc.DYReWrite(user_name, i_user_data1, i_user_data2, rtu_para, mp_id, dyoper, operidx, err_str_1,task_result_detail);
		
		if(ret_val){
			dyOperRewrite = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
		return SDDef.FAIL;
	}
	
	//低压操作-冲正
	private String DYRever(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID	
		
		boolean ret_val = false;

		if (dyOperRever != null) dyOperRever = dyOperRever.trim();
		if (dyOperRever == null || dyOperRever.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		JSONObject json = JSONObject.fromObject(dyOperRever);
		
		//20131019
		//ComntMsg.MSG_DYOPER_REVER dyoper = new ComntMsg.MSG_DYOPER_REVER();
		ComntMsgDy.MSG_DYOPER_REVER dyoper = new ComntMsgDy.MSG_DYOPER_REVER();
		
		dyoper.testf = 0;
		dyoper.gsmf  = gsm_flag;
		dyoper.mpid  = mp_id;
		ComntFunc.string2Byte(user_name, dyoper.operman);
		dyoper.paytype		= (byte)CommBase.strtoi(json.getString("paytype"));
		dyoper.opresult 	= op_result;
		
		dyoper.old_op_date	= CommBase.strtoi(json.getString("old_op_date"));
		dyoper.old_op_time	= CommBase.strtoi(json.getString("old_op_time"));
		
		dyoper.newmoney.pay_money   = CommBase.strtof(json.getString("pay_money"));
		dyoper.newmoney.othjs_money = CommBase.strtof(json.getString("othjs_money"));
		dyoper.newmoney.zb_money	= CommBase.strtof(json.getString("zb_money"));
		dyoper.newmoney.all_money   = CommBase.strtof(json.getString("all_money"));
			
		//jack 20140612 start
		String tmp_str = "";
		if (json.containsKey("buy_dl")) {
			dyoper.bz.buy_dl 		= CommBase.strtof(json.getString("buy_dl"));
			dyoper.bz.pay_bmc 		= CommBase.strtof(json.getString("pay_bmc"));
//			dyoper.bz.shutdown_bd 	= CommBase.strtof(json.getString("shutdown_bd"));
			
			dyoper.passrand.update_flag = CommBase.strtoi(json.getString("update_flag"));
			
			tmp_str = json.getString("card_rand");
			byte card_rand[] = ComntFunc.string2Byte(tmp_str);
			ComntFunc.arrayCopy(dyoper.passrand.card_rand, card_rand);
			
			tmp_str = json.getString("card_pass");
			byte card_pass[] = ComntFunc.string2Byte(tmp_str);
			ComntFunc.arrayCopy(dyoper.passrand.card_pass, card_pass);
			
//			tmp_str = json.getString("reserve1");
//			byte reserve1[] = ComntFunc.string2Byte(tmp_str);
//			ComntFunc.arrayCopy(dyoper.passrand.reserve1, reserve1);
//			
//			tmp_str = json.getString("reserve2");
//			byte reserve2[] = ComntFunc.string2Byte(tmp_str);
//			ComntFunc.arrayCopy(dyoper.passrand.reserve2, reserve2);			
		}
		//jack 20140612 end
		
		dyOperRever = SDDef.EMPTY;
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
		ret_val = ComntOperDyFunc.DYRever(user_name, i_user_data1, i_user_data2, rtu_para, mp_id, dyoper, operidx, err_str_1,task_result_detail);

		if(ret_val){
			dyOperRever = operidx.toJsonString();
			return SDDef.SUCCESS; 
		}else{
			return SDDef.FAIL;
		}
	}
	
	//低压操作-换表换倍率
	private String DYChgMeter(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;		
		
		boolean ret_val = false;
		
		if (dyOperChangeMeter != null) dyOperChangeMeter = dyOperChangeMeter.trim();
		if (dyOperChangeMeter == null || dyOperChangeMeter.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(dyOperChangeMeter);
		
		//20131019
		//ComntMsg.MSG_DYOPER_CHANGEMETER dyoper = new ComntMsg.MSG_DYOPER_CHANGEMETER();
		ComntMsgDy.MSG_DYOPER_CHANGEMETER dyoper = new ComntMsgDy.MSG_DYOPER_CHANGEMETER();
		
		dyoper.testf = 0;
		dyoper.gsmf  = gsm_flag;
		dyoper.mpid  = mp_id;
		ComntFunc.string2Byte(user_name, dyoper.operman);
		dyoper.opresult 	= op_result;
		dyoper.buynum 		= CommBase.strtoi(json.getString("buynum"));
		
		dyoper.money.pay_money  = CommBase.strtof(json.getString("pay_money"));
		dyoper.money.othjs_money= CommBase.strtof(json.getString("othjs_money"));
		dyoper.money.zb_money	= CommBase.strtof(json.getString("zb_money"));
		dyoper.money.all_money  = CommBase.strtof(json.getString("all_money"));
		
		for(i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++){
			dyoper.oldbd[i].rtu_id= CommBase.strtoi(json.getString("rtu_id"));
			dyoper.oldbd[i].mp_id = (short)CommBase.strtoi(json.getString("mp_id" + i));
			
			dyoper.newbd[i].rtu_id= CommBase.strtoi(json.getString("rtu_id"));
			dyoper.newbd[i].mp_id = (short)CommBase.strtoi(json.getString("mp_id" + i));
			dyoper.oldbd[i].date  = CommBase.strtoi(json.getString("o_date"));
			dyoper.oldbd[i].time  = CommBase.strtoi(json.getString("o_time"));
			dyoper.newbd[i].date  = CommBase.strtoi(json.getString("n_date"));
			dyoper.newbd[i].time  = CommBase.strtoi(json.getString("n_time"));
		
			if(json.getString("chg_mpid").equals(json.getString("mp_id" + i))){
				dyoper.oldbd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy_o"  + i));
				dyoper.oldbd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj_o" + i));
				dyoper.oldbd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf_o" + i));
				dyoper.oldbd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp_o" + i));
				dyoper.oldbd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg_o" + i));				
				dyoper.newbd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy_n"  + i));
				dyoper.newbd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj_n" + i));
				dyoper.newbd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf_n" + i));
				dyoper.newbd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp_n" + i));
				dyoper.newbd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg_n" + i));
			}else{
				dyoper.oldbd[i].bd_zy  = 0;
				dyoper.oldbd[i].bd_zyj = 0;
				dyoper.oldbd[i].bd_zyf = 0;
				dyoper.oldbd[i].bd_zyp = 0;
				dyoper.oldbd[i].bd_zyg = 0;
			
				dyoper.newbd[i].bd_zy  = 0;
				dyoper.newbd[i].bd_zyj = 0;
				dyoper.newbd[i].bd_zyf = 0;
				dyoper.newbd[i].bd_zyp = 0;
				dyoper.newbd[i].bd_zyg = 0;
			}
		}
		
		dyoper.chgmterrate.rtu_id 	= CommBase.strtoi(json.getString("rtu_id"));
		dyoper.chgmterrate.mp_id 	= (short)CommBase.strtoi(json.getString("chg_mpid"));
		dyoper.chgmterrate.chg_date = CommBase.strtoi(json.getString("chg_date"));
		dyoper.chgmterrate.chg_time = CommBase.strtoi(json.getString("chg_time"));
		dyoper.chgmterrate.chg_type = (short)CommBase.strtoi(json.getString("chg_type"));

		dyoper.chgmterrate.oldct_numerator 	= CommBase.strtoi(json.getString("oldct_n"));
		dyoper.chgmterrate.oldct_denominator= CommBase.strtoi(json.getString("oldct_d"));
		dyoper.chgmterrate.oldct_ratio 		= CommBase.strtof(json.getString("oldct_r"));
		dyoper.chgmterrate.newct_numerator 	= CommBase.strtoi(json.getString("newct_n"));
		dyoper.chgmterrate.newct_denominator= CommBase.strtoi(json.getString("newct_d"));
		dyoper.chgmterrate.newct_ratio 		= CommBase.strtof(json.getString("newct_r"));
		
		dyoper.chgmterrate.oldpt_numerator 	= CommBase.strtoi(json.getString("oldpt_n"));
		dyoper.chgmterrate.oldpt_denominator= CommBase.strtoi(json.getString("oldpt_d"));
		dyoper.chgmterrate.oldpt_ratio 		= CommBase.strtof(json.getString("oldpt_r"));
		dyoper.chgmterrate.newpt_numerator 	= CommBase.strtoi(json.getString("newpt_n"));
		dyoper.chgmterrate.newpt_denominator= CommBase.strtoi(json.getString("newpt_d"));
		dyoper.chgmterrate.newpt_ratio 		= CommBase.strtof(json.getString("newpt_r"));
		
		dyoper.chgmterrate.old_zyz 	= CommBase.strtof(json.getString("bd_zy_o"));
		dyoper.chgmterrate.old_zyj 	= CommBase.strtof(json.getString("bd_zyj_o"));
		dyoper.chgmterrate.old_zyf 	= CommBase.strtof(json.getString("bd_zyf_o"));
		dyoper.chgmterrate.old_zyp 	= CommBase.strtof(json.getString("bd_zyp_o"));
		dyoper.chgmterrate.old_zyg 	= CommBase.strtof(json.getString("bd_zyg_o"));

		dyoper.chgmterrate.new_zyz 	= CommBase.strtof(json.getString("bd_zy_n"));
		dyoper.chgmterrate.new_zyj 	= CommBase.strtof(json.getString("bd_zyj_n"));
		dyoper.chgmterrate.new_zyf 	= CommBase.strtof(json.getString("bd_zyf_n"));
		dyoper.chgmterrate.new_zyp 	= CommBase.strtof(json.getString("bd_zyp_n"));
		dyoper.chgmterrate.new_zyg 	= CommBase.strtof(json.getString("bd_zyg_n"));
			
		//jack 20140612 start
		String tmp_str = "";
		if (json.containsKey("buy_dl")) {
			dyoper.bz.buy_dl 		= CommBase.strtof(json.getString("buy_dl"));
			dyoper.bz.pay_bmc 		= CommBase.strtof(json.getString("pay_bmc"));
			dyoper.bz.shutdown_bd 	= CommBase.strtof(json.getString("shutdown_bd"));
			
			dyoper.passrand.update_flag = CommBase.strtoi(json.getString("update_flag"));
			
			tmp_str = json.getString("card_rand");
			byte card_rand[] = ComntFunc.string2Byte(tmp_str);
			ComntFunc.arrayCopy(dyoper.passrand.card_rand, card_rand);
			
			tmp_str = json.getString("card_pass");
			byte card_pass[] = ComntFunc.string2Byte(tmp_str);
			ComntFunc.arrayCopy(dyoper.passrand.card_pass, card_pass);
			
			tmp_str = json.getString("reserve1");
			byte reserve1[] = ComntFunc.string2Byte(tmp_str);
			ComntFunc.arrayCopy(dyoper.passrand.reserve1, reserve1);
			
			tmp_str = json.getString("reserve2");
			byte reserve2[] = ComntFunc.string2Byte(tmp_str);
			ComntFunc.arrayCopy(dyoper.passrand.reserve2, reserve2);			
		}
		//jack 20140612 end
		
		dyOperChangeMeter = SDDef.EMPTY;
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
	
		ret_val = ComntOperDyFunc.DYChgMeter(user_name, i_user_data1, i_user_data2, rtu_para, mp_id, dyoper, operidx, err_str_1,task_result_detail);
		
		if (ret_val) {
			dyOperChangeMeter = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
		else {
			return SDDef.FAIL;
		}
	}	
	
	//低压操作-换电价
	private String DYChgRate(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;		
		
		boolean ret_val = false;
		
		if (dyOperChangeRate != null) dyOperChangeRate = dyOperChangeRate.trim();
		if (dyOperChangeRate == null || dyOperChangeRate.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}

		JSONObject json = JSONObject.fromObject(dyOperChangeRate);
		
		//20131019
		//ComntMsg.MSG_DYOPER_CHANGERATE dyoper = new ComntMsg.MSG_DYOPER_CHANGERATE();
		ComntMsgDy.MSG_DYOPER_CHANGERATE dyoper = new ComntMsgDy.MSG_DYOPER_CHANGERATE();
		
		dyoper.testf = 0;
		dyoper.gsmf  = gsm_flag;
		dyoper.mpid  = mp_id;
		ComntFunc.string2Byte(user_name, dyoper.operman);
		dyoper.opresult 	= op_result;
		
		dyoper.chg_type 	= 1;
		dyoper.fee_chgid 	= (short)CommBase.strtoi(json.getString("chgid"));
		dyoper.fee_chgdate 	= CommBase.strtoi(json.getString("chg_date"));
		dyoper.fee_chgtime 	= CommBase.strtoi(json.getString("chg_time"));

		for(i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++){
			dyoper.bd[i].rtu_id= CommBase.strtoi(json.getString("rtu_id"));
			dyoper.bd[i].mp_id = (short)CommBase.strtoi(json.getString("mp_id" + i));
			dyoper.bd[i].date  = CommBase.strtoi(json.getString("date"));
			dyoper.bd[i].time  = CommBase.strtoi(json.getString("time"));

			dyoper.bd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy"  + i));
			dyoper.bd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj" + i));
			dyoper.bd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf" + i));
			dyoper.bd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp" + i));
			dyoper.bd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg" + i));
		}
			
		dyOperChangeRate = SDDef.EMPTY;
		
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
		ret_val = ComntOperDyFunc.DYChgRate(user_name, i_user_data1, i_user_data2, rtu_para, mp_id, dyoper, operidx, err_str_1,task_result_detail);
		
	    if(ret_val){
	    	dyOperChangeRate = operidx.toJsonString();
	    	return SDDef.SUCCESS;
	    }else{
	    	return SDDef.FAIL;
	    }
	}	
	
	//低压操作-保电
	private String DYProtect(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;
	
		if (dyOperProtect != null) dyOperProtect = dyOperProtect.trim();
		if (dyOperProtect == null || dyOperProtect.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(dyOperProtect);
		
		//20131019
		//ComntMsg.MSG_DYOPER_PROTECT dyoper = new ComntMsg.MSG_DYOPER_PROTECT();
		ComntMsgDy.MSG_DYOPER_PROTECT dyoper = new ComntMsgDy.MSG_DYOPER_PROTECT();
		
		dyoper.testf = 0;
		dyoper.gsmf  = gsm_flag;
		dyoper.mpid  = mp_id;
		ComntFunc.string2Byte(user_name, dyoper.operman);
		
		dyoper.m_prot_st 	= (short)CommBase.strtoi(json.getString("m_prot_st"));
		dyoper.m_prot_ed 	= (short)CommBase.strtoi(json.getString("m_prot_ed"));
		
		ret_val = ComntOperDyFunc.DYProtect(user_name, i_user_data1, i_user_data2, rtu_para, mp_id, dyoper, err_str_1,task_result_detail);

		if(ret_val){
			return SDDef.SUCCESS;
		}
		return SDDef.FAIL;
	}	
	
	//低压操作-强制状态更新
	private String DySetUpdate(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID	
		
		boolean ret_val = false;
		
		if (dyOperUpdateState != null) dyOperUpdateState = dyOperUpdateState.trim();
		if (dyOperUpdateState == null || dyOperUpdateState.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		//首先获取预付费参数
		ComntProtMsg.YFF_DATA_DYOPER_PARASTATE dyoper_parastate = new ComntProtMsg.YFF_DATA_DYOPER_PARASTATE(); 
		ret_val = ComntOperDyFunc.DYGetPayParaState(user_name, i_user_data1, i_user_data2, rtu_para, 
												  mp_id, dyoper_parastate, err_str_1,task_result_detail);
		if (ret_val == false) {
			op_detail.addTaskInfo("ERR:获取预付费参数及状态错误");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(dyOperUpdateState);
		
		//20131019
		//ComntMsg.MSG_DYOPER_UPDATE dyoper = new ComntMsg.MSG_DYOPER_UPDATE();
		ComntMsgDy.MSG_DYOPER_UPDATE dyoper = new ComntMsgDy.MSG_DYOPER_UPDATE();
		
		dyoper.testf = 0;
		dyoper.gsmf  = 0;
		dyoper.mpid = mp_id;
		ComntFunc.string2Byte(user_name, dyoper.operman);
		
		short updType = (short)CommBase.strtoi(json.getString("updType"));

		dyoper.para  	= dyoper_parastate.para;
		dyoper.state 	= dyoper_parastate.state;
		dyoper.alarm 	= dyoper_parastate.alarm;
		dyoper.commdata	= dyoper_parastate.commdata;
		
		switch (updType){
		case SDDef.YFF_DY_CHGBUYTIME:
			int buy_times = CommBase.strtoi(json.getString("buy_times"));
			dyoper.state.buy_times = buy_times;
			dyoper.state_bn_validf  = 1;
			break;
		case SDDef.YFF_DY_FRMESS_ERR:
			dyoper.alarm.qr_al1_1_state = (byte) ((dyoper.alarm.qr_al1_1_state & 0x3f) | (ComntDef.YFF_QRFLAG_SUCCESS << 6));
			dyoper.alarmvalidf = 1;
			break;
		case SDDef.YFF_DY_FRSOUND_ERR:
			dyoper.alarm.qr_al1_2_state = (byte) ((dyoper.alarm.qr_al1_2_state & 0x3f) | (ComntDef.YFF_QRFLAG_SUCCESS << 6));
			dyoper.alarmvalidf = 1;
			break;
		case SDDef.YFF_DY_SECMESS_ERR:////二次短信告警确认失败
			dyoper.alarm.qr_al2_1_state = (byte) ((dyoper.alarm.qr_al2_1_state & 0x3f) | (ComntDef.YFF_QRFLAG_SUCCESS << 6));
			dyoper.alarmvalidf = 1;
			break;
		case SDDef.YFF_DY_JTTOTZBDL://阶梯追补累计用电量
			double 	jt_total_zbdl 	= CommBase.strtoi(json.getString("jt_total_zbdl"));
			dyoper.state.jt_total_zbdl = jt_total_zbdl;
			dyoper.state_jt_validf = 1;
			break;
		case SDDef.YFF_DY_JTRESETYMD:
			int 	jt_reset_ymd 	= CommBase.strtoi(json.getString("jt_reset_ymd"));
			dyoper.state.jt_reset_ymd 	= jt_reset_ymd;
//			dyoper.state.jt_reset_mdhmi = jt_reset_mdhmi;
			dyoper.state_jt_validf = 1;
			break;
		case SDDef.YFF_DY_FXDF_ALLMONEY:
			double 	fxdf_iall_money = CommBase.strtoi(json.getString("fxdf_iall_money"));
			double  fxdf_iall_money2= CommBase.strtoi(json.getString("fxdf_iall_money2"));

			dyoper.state.fxdf_iall_money  = fxdf_iall_money;
			dyoper.state.fxdf_iall_money2 = fxdf_iall_money2;
			dyoper.state_df_validf = 1;
			break;
		case SDDef.YFF_DY_FXDF_REMIAN:
			double 	fxdf_remain		= CommBase.strtoi(json.getString("fxdf_remain"));
			double  fxdf_remain2	= CommBase.strtoi(json.getString("fxdf_remain2"));

			dyoper.state.fxdf_remain  = fxdf_remain;
			dyoper.state.fxdf_remain2 = fxdf_remain2;
			dyoper.state_df_validf = 1;
			break;
		case SDDef.YFF_DY_FXDF_YM:
			int 	fxdf_ym       	= CommBase.strtoi(json.getString("fxdf_ym"));
			dyoper.state.fxdf_ym 		 = fxdf_ym;
//			dyoper.state.fxdf_data_ymd 	 = fxdf_data_ymd;
//			dyoper.state.fxdf_calc_mdhmi = fxdf_calc_mdhmi;
//			dyoper.state.js_bc_ymd 		 = js_bc_ymd;
			dyoper.state_df_validf = 1;
			break;
		case SDDef.YFF_DY_UPDATE_KEYVER:
			int     key_version		= CommBase.strtoi(json.getString("key_version")) & 0xff;
			dyoper.para.key_version = (byte) key_version;
			dyoper.paravalidf		= 1;
		default:
			break;
		}

		ret_val = ComntOperDyFunc.DySetUpdate(user_name, i_user_data1, i_user_data2, rtu_para, mp_id, dyoper, err_str_1,task_result_detail);
		
		if (ret_val == false) {
			op_detail.addTaskInfo("ERR:设置预付费参数及状态错误");
			return SDDef.FAIL;			
		}
		return SDDef.SUCCESS;
	}	
	
	//低压操作-重新计算剩余金额--未使用
	private String DYReCalc(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;		
		boolean ret_val = false;
		if (dyOperRecalc != null) dyOperRecalc = dyOperRecalc.trim();
		if (dyOperRecalc == null || dyOperRecalc.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(dyOperRecalc);
		
		//20131019
		//ComntMsg.MSG_DYOPER_RECALC dyoper = new ComntMsg.MSG_DYOPER_RECALC();
		ComntMsgDy.MSG_DYOPER_RECALC dyoper = new ComntMsgDy.MSG_DYOPER_RECALC();
		
		dyoper.testf = 0;
		dyoper.gsmf  = gsm_flag;
		dyoper.mpid  = mp_id;
		ComntFunc.string2Byte(user_name, dyoper.operman);

		for(i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++){
			dyoper.bd[i].rtu_id= CommBase.strtoi(json.getString("rtu_id"));
			dyoper.bd[i].mp_id = (short)CommBase.strtoi(json.getString("mp_id" + i));
			dyoper.bd[i].date  = CommBase.strtoi(json.getString("date"));
			dyoper.bd[i].time  = CommBase.strtoi(json.getString("time"));
			dyoper.bd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy"  + i));
			dyoper.bd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj" + i));
			dyoper.bd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf" + i));
			dyoper.bd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp" + i));
			dyoper.bd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg" + i));
		}
		ret_val = ComntOperDyFunc.DYReCalc(user_name, i_user_data1, i_user_data2, rtu_para, mp_id, dyoper, err_str_1,task_result_detail);
		
		if(ret_val){
			return SDDef.SUCCESS ;
		}else{
			return SDDef.FAIL;
		}
	}
	
	//低压操作-结算补差-未测试
	private String DYJsbc(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;		
		
		boolean ret_val = false;
		
		if (dyOperJsbc != null) dyOperJsbc = dyOperJsbc.trim();
		if (dyOperJsbc == null || dyOperJsbc.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}

		JSONObject json = JSONObject.fromObject(dyOperJsbc);
		
		//20131019
		//ComntMsg.MSG_DYOPER_JSBC dyoper = new ComntMsg.MSG_DYOPER_JSBC();
		ComntMsgDy.MSG_DYOPER_JSBC dyoper = new ComntMsgDy.MSG_DYOPER_JSBC();
		
		dyoper.testf = 0;
		dyoper.gsmf  = gsm_flag;
		dyoper.mpid  = mp_id;
		dyoper.opresult = op_result;
		ComntFunc.string2Byte(user_name, dyoper.operman);
		
		dyoper.paytype  	= (byte)CommBase.strtoi(json.getString("paytype"));
		
		//20121128 zkz 购电次数由界面控制
		//dyoper.buynum		= CommBase.strtoi(json.getString("buynum")) + 1;
		dyoper.buynum		= CommBase.strtoi(json.getString("buynum"));
		
		
		dyoper.money.pay_money  = CommBase.strtof(json.getString("pay_money"));
		dyoper.money.othjs_money= CommBase.strtof(json.getString("othjs_money"));
		dyoper.money.zb_money	= CommBase.strtof(json.getString("zb_money"));
		dyoper.money.all_money  = CommBase.strtof(json.getString("all_money"));
		
		for(i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++){
			dyoper.bd[i].rtu_id= CommBase.strtoi(json.getString("rtu_id"));
			dyoper.bd[i].mp_id = (short)CommBase.strtoi(json.getString("mp_id" + i));
			dyoper.bd[i].date  = CommBase.strtoi(json.getString("date"));
			dyoper.bd[i].time  = CommBase.strtoi(json.getString("time"));

			dyoper.bd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy"  + i));
			dyoper.bd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj" + i));
			dyoper.bd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf" + i));
			dyoper.bd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp" + i));
			dyoper.bd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg" + i));
		}
		
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		ret_val = ComntOperDyFunc.DYJsbc(user_name, i_user_data1, i_user_data2, rtu_para, mp_id, dyoper, operidx, err_str_1,task_result_detail);
		
		if(!ret_val){
			return SDDef.FAIL;
		}
		
		//结算存库
		JSPara.JJSRecordPara para = new JSPara.JJSRecordPara();
		
		para.rtu_id = json.getInt("rtu_id");
		para.mp_id = (short)json.getInt("mp_id");
		para.fxdf_ym = json.getInt("fxdf_ym");
		
		Date date = new Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd-HHmmss", Locale.SIMPLIFIED_CHINESE);
		String date_time[] = sdf.format(date).split("-");
		para.op_date = CommBase.strtoi(date_time[0]);
		para.op_time = CommBase.strtoi(date_time[1]);
		para.res_id = (short)json.getInt("res_id");
		para.res_desc = json.getString("res_desc");
		para.op_man = CommFunc.getYffMan().getName();
		para.pay_type =  (byte)json.getInt("pay_type");
		
		para.wasteno = ComntFunc.byte2String(operidx.operidx.wasteno);
		para.pay_money = json.getDouble("pay_money");
		para.othjs_money = dyoper.money.othjs_money;
		para.zb_money = json.getDouble("zb_money");
		para.all_money = dyoper.money.all_money;
		para.buy_times = json.getInt("buynum");
		
		if(json.getInt("jsflag") == 1) {
			para.misjs_money = json.getDouble("misjs_money");
		} else {
			para.misjs_money = json.getDouble("othjs_money");
		}
		
		para.lastala_val1 = json.getDouble("lastala_val1");
		para.lastala_val2 = json.getDouble("lastala_val2");
		para.lastshut_val = json.getDouble("lastshut_val");
		para.newala_val1 = json.getDouble("newala_val1");
		para.newala_val2 = json.getDouble("newala_val2");
		para.newshut_val = json.getDouble("newshut_val");
		
		if(!DBOper.insertJJs(para)) {
			err_str_1.append("存库失败!");
			return SDDef.FAIL;
		}
		
		return SDDef.SUCCESS;
	}	
	
	//低压操作-发行电费--未测试
	private String DYReFxdf(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		int i = 0;		
		boolean ret_val = false;
		if (dyOperReFxdf != null) dyOperReFxdf = dyOperReFxdf.trim();
		if (dyOperReFxdf == null || dyOperReFxdf.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		JSONObject json = JSONObject.fromObject(dyOperReFxdf);
		
		//20131019
		//ComntMsg.MSG_DYOPER_REFXDF dyoper = new ComntMsg.MSG_DYOPER_REFXDF();
		ComntMsgDy.MSG_DYOPER_REFXDF dyoper = new ComntMsgDy.MSG_DYOPER_REFXDF();
		
		dyoper.testf = 0;
		dyoper.gsmf  = gsm_flag;
		dyoper.mpid  = mp_id;
		dyoper.opresult = op_result;
		ComntFunc.string2Byte(user_name, dyoper.operman);
		
		for(i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++){
			dyoper.bd[i].rtu_id= CommBase.strtoi(json.getString("rtu_id"));
			dyoper.bd[i].mp_id = (short)CommBase.strtoi(json.getString("mp_id" + i));
			dyoper.bd[i].date  = CommBase.strtoi(json.getString("date"));
			dyoper.bd[i].time  = CommBase.strtoi(json.getString("time"));

			dyoper.bd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy"  + i));
			dyoper.bd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj" + i));
			dyoper.bd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf" + i));
			dyoper.bd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp" + i));
			dyoper.bd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg" + i));
		}
		
		ret_val = ComntOperDyFunc.DYReFxdf(user_name, i_user_data1, i_user_data2, rtu_para, mp_id, dyoper, err_str_1,task_result_detail);
		
		if(ret_val){
			return SDDef.SUCCESS;
		}
		return SDDef.FAIL;
	}
	
	//低压操作-重新阶梯清零-未测试
	private String DYReJtRest(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;		
		
		boolean ret_val = false;
		

		if (dyOperReJtRest != null) dyOperReJtRest = dyOperReJtRest.trim();
		if (dyOperReJtRest == null || dyOperReJtRest.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}

		JSONObject json = JSONObject.fromObject(dyOperReJtRest);
		
		//20131019
		//ComntMsg.MSG_DYOPER_REJTRESET dyoper = new ComntMsg.MSG_DYOPER_REJTRESET();
		ComntMsgDy.MSG_DYOPER_REJTRESET dyoper = new ComntMsgDy.MSG_DYOPER_REJTRESET();
		
		dyoper.testf = 0;
		dyoper.gsmf  = gsm_flag;
		dyoper.mpid  = mp_id;
		dyoper.opresult = op_result;
		ComntFunc.string2Byte(user_name, dyoper.operman);
		
		for(i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++){
			dyoper.bd[i].rtu_id= CommBase.strtoi(json.getString("rtu_id"));
			dyoper.bd[i].mp_id = (short)CommBase.strtoi(json.getString("mp_id" + i));
			dyoper.bd[i].date  = CommBase.strtoi(json.getString("date"));
			dyoper.bd[i].time  = CommBase.strtoi(json.getString("time"));

			dyoper.bd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy"  + i));
			dyoper.bd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj" + i));
			dyoper.bd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf" + i));
			dyoper.bd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp" + i));
			dyoper.bd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg" + i));
		}
		
		ret_val = ComntOperDyFunc.DYReJtRest(user_name, i_user_data1, i_user_data2, rtu_para, mp_id, dyoper, err_str_1,task_result_detail);
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}
	
	
	//低压操作-更改参数  预付费参数  20130201
	private String DYResetDoc(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;		
		
		boolean ret_val = false;
		

		if (dyOperResetDoc != null) dyOperResetDoc = dyOperResetDoc.trim();
		if (dyOperResetDoc == null || dyOperResetDoc.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}

		JSONObject json = JSONObject.fromObject(dyOperResetDoc);
		
		//20131019
		//ComntMsg.MSG_DYOPER_RESETDOC dyoper = new ComntMsg.MSG_DYOPER_RESETDOC();
		ComntMsgDy.MSG_DYOPER_RESETDOC dyoper = new ComntMsgDy.MSG_DYOPER_RESETDOC();
		
		dyoper.testf = 0;
		dyoper.gsmf  = gsm_flag;
		dyoper.mpid  = mp_id;
		dyoper.resid = (short)CommBase.strtoi(json.getString("resid"));
		ComntFunc.string2Byte(user_name, dyoper.operman);
		
		dyoper.chgtype = (byte)CommBase.strtoi(json.getString("chgtype"));
				
		ComntFunc.string2Byte(json.getString("filed1"), dyoper.filed1);
		ComntFunc.string2Byte(json.getString("filed2"), dyoper.filed2);
		ComntFunc.string2Byte(json.getString("filed3"), dyoper.filed3);
		ComntFunc.string2Byte(json.getString("filed4"), dyoper.filed4);
		
		
		ret_val = ComntOperDyFunc.DYResetDoc(user_name, i_user_data1, i_user_data2, rtu_para, mp_id, dyoper, err_str_1,task_result_detail);
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}
	
	//低压操作-返回余额
	private String DYGetRemain(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;		
		 
		boolean ret_val = false;
		
		if (dyOperGetRemain != null) dyOperGetRemain = dyOperGetRemain.trim();
		if (dyOperGetRemain == null || dyOperGetRemain.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}

		JSONObject json = JSONObject.fromObject(dyOperGetRemain);
		
		//20131019
		//ComntMsg.MSG_DYOPER_GETREMAIN dyoper = new ComntMsg.MSG_DYOPER_GETREMAIN();
		ComntMsgDy.MSG_DYOPER_GETREMAIN dyoper = new ComntMsgDy.MSG_DYOPER_GETREMAIN();
		
		dyoper.testf = 0;
		dyoper.gsmf  = 0;
		dyoper.mpid  = mp_id;
		ComntFunc.string2Byte(user_name, dyoper.operman);

		for(i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++){
			dyoper.bd[i].rtu_id	= CommBase.strtoi(json.getString("rtu_id"));
			dyoper.bd[i].mp_id 	= (short)CommBase.strtoi(json.getString("mp_id" + i));
			dyoper.bd[i].date  	= CommBase.strtoi(json.getString("date"));
			dyoper.bd[i].time  	= CommBase.strtoi(json.getString("time"));
			dyoper.bd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy"  + i));
			dyoper.bd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj" + i));
			dyoper.bd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf" + i));
			dyoper.bd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp" + i));
			dyoper.bd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg" + i));
		}
		
		dyOperGetRemain = SDDef.EMPTY;
		
		ComntProtMsg.YFF_DATA_OPER_GETREMAIN  getremain = new ComntProtMsg.YFF_DATA_OPER_GETREMAIN();
		
		ret_val = ComntOperDyFunc.DYGetRemain(user_name, i_user_data1, i_user_data2, rtu_para, mp_id, dyoper, getremain, err_str_1,task_result_detail);
	    
		if(ret_val){
			dyOperGetRemain = getremain.toJsonString();
			return SDDef.SUCCESS;
		}else{
			return SDDef.FAIL;
		}
	}	
	
	//低压操作-获得预付费参数及状态
	private String DYGetPayParaState(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) {
		
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID5
		boolean ret_val = false;
		
		dyOperGParaState = SDDef.EMPTY;
		
		ComntProtMsg.YFF_DATA_DYOPER_PARASTATE dyoper_parastate = new ComntProtMsg.YFF_DATA_DYOPER_PARASTATE();
		
        ret_val = ComntOperDyFunc.DYGetPayParaState(user_name, i_user_data1, i_user_data2, rtu_para, mp_id, dyoper_parastate, err_str_1,task_result_detail);

		if (ret_val) {
			dyOperGParaState = dyoper_parastate.toJsonString();
			return SDDef.SUCCESS;
		}
		else {
			return SDDef.FAIL;
		}
	}	
	
	//低压操作-恢复
	private String DYRestart(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;		
		
		boolean ret_val = false;
		
		if (dyOperReStart != null) dyOperReStart = dyOperReStart.trim();
		if (dyOperReStart == null || dyOperReStart.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(dyOperReStart);
		
		//20131019
		//ComntMsg.MSG_DYOPER_RESTART dyoper = new ComntMsg.MSG_DYOPER_RESTART();
		ComntMsgDy.MSG_DYOPER_RESTART dyoper = new ComntMsgDy.MSG_DYOPER_RESTART();
		
		dyoper.testf = 0;
		dyoper.gsmf  = gsm_flag;
		dyoper.mpid  = mp_id;
		ComntFunc.string2Byte(user_name, dyoper.operman);
		dyoper.opresult = op_result;
		
		dyoper.myffalarmid  = (short)CommBase.strtoi(json.getString("myffalarmid"));
		dyoper.paytype  	= (byte)CommBase.strtoi(json.getString("paytype"));
		dyoper.opresult 	= op_result;
		dyoper.buynum 		= CommBase.strtoi(json.getString("buynum")) + 1;
		dyoper.money.pay_money  = CommBase.strtof(json.getString("pay_money"));
		dyoper.money.othjs_money= CommBase.strtof(json.getString("othjs_money"));
		dyoper.money.zb_money	= CommBase.strtof(json.getString("zb_money"));
		dyoper.money.all_money  = CommBase.strtof(json.getString("all_money"));
		dyoper.jt_total_zbdl 	 = CommBase.strtof(json.getString("jt_total_zbdl"));

		for(i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++){
			dyoper.bd[i].rtu_id= CommBase.strtoi(json.getString("rtu_id"));
			dyoper.bd[i].mp_id = (short)CommBase.strtoi(json.getString("mp_id" + i));
			dyoper.bd[i].date  = CommBase.strtoi(json.getString("date"));
			dyoper.bd[i].time  = CommBase.strtoi(json.getString("time"));
			dyoper.bd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy"  + i));
			dyoper.bd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj" + i));
			dyoper.bd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf" + i));
			dyoper.bd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp" + i));
			dyoper.bd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg" + i));
		}
			
		dyOperReStart = SDDef.EMPTY;
		
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
	  	
        ret_val = ComntOperDyFunc.DYRestart(user_name, i_user_data1, i_user_data2, rtu_para, mp_id, dyoper, operidx, err_str_1,task_result_detail);		
		
        if(ret_val){
        	dyOperReStart = operidx.toJsonString();
        	return SDDef.SUCCESS;
        }else{
        	return SDDef.FAIL;
        }
	}	
	
	//低压操作-暂停
	private String DYPause(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;
		
		boolean ret_val = false;
		
	
		if (dyOperPause != null) dyOperPause = dyOperPause.trim();
		if (dyOperPause == null || dyOperPause.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(dyOperPause);
		
		//20131019
		//ComntMsg.MSG_DYOPER_PAUSE dyoper = new ComntMsg.MSG_DYOPER_PAUSE();
		ComntMsgDy.MSG_DYOPER_PAUSE dyoper = new ComntMsgDy.MSG_DYOPER_PAUSE();
		
		dyoper.testf = 0;
		dyoper.gsmf  = 0;
		dyoper.mpid  = mp_id;
		ComntFunc.string2Byte(user_name, dyoper.operman);
		dyoper.opresult = op_result;

		for(i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++){
			dyoper.bd[i].rtu_id= CommBase.strtoi(json.getString("rtu_id"));
			dyoper.bd[i].mp_id = (short)CommBase.strtoi(json.getString("mp_id" + i));
			dyoper.bd[i].date  = CommBase.strtoi(json.getString("date"));
			dyoper.bd[i].time  = CommBase.strtoi(json.getString("time"));
			dyoper.bd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy"  + i));
			dyoper.bd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj" + i));
			dyoper.bd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf" + i));
			dyoper.bd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp" + i));
			dyoper.bd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg" + i));
		}
		dyoper.money.pay_money  = CommBase.strtof(json.getString("pay_money"));
		dyoper.money.othjs_money= CommBase.strtof(json.getString("othjs_money"));
		dyoper.money.zb_money	= CommBase.strtof(json.getString("zb_money"));
		dyoper.money.all_money  = CommBase.strtof(json.getString("all_money"));
		
		dyOperPause = SDDef.EMPTY;
		
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
		ret_val = ComntOperDyFunc.DYPause(user_name, i_user_data1, i_user_data2, rtu_para, mp_id, dyoper, operidx, err_str_1,task_result_detail);
		
		if(ret_val){
			dyOperPause = operidx.toJsonString();
			return SDDef.SUCCESS;
		}else{
			return SDDef.FAIL;
		}

	}
	
	//低压操作-销户
	private String DYDestory(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		
		boolean ret_val = false;
		
		if (dyOperDestory != null) dyOperDestory = dyOperDestory.trim();
		if (dyOperDestory == null || dyOperDestory.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(dyOperDestory);
		
		//20131019
		//ComntMsg.MSG_DYOPER_DESTORY dyoper = new ComntMsg.MSG_DYOPER_DESTORY();
		ComntMsgDy.MSG_DYOPER_DESTORY dyoper = new ComntMsgDy.MSG_DYOPER_DESTORY();
		
		dyoper.testf = 0;
		dyoper.gsmf  = gsm_flag;
		dyoper.mpid  = mp_id;
		ComntFunc.string2Byte(user_name, dyoper.operman);
		dyoper.opresult = op_result;
		
		dyoper.money.pay_money  = CommBase.strtof(json.getString("pay_money"));
		dyoper.money.othjs_money= CommBase.strtof(json.getString("othjs_money"));
		dyoper.money.zb_money	= CommBase.strtof(json.getString("zb_money"));
		dyoper.money.all_money  = CommBase.strtof(json.getString("all_money"));
		
		for(int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++){
			dyoper.bd[i].rtu_id= CommBase.strtoi(json.getString("rtu_id"));
			dyoper.bd[i].mp_id = (short)CommBase.strtoi(json.getString("mp_id" + i));
			dyoper.bd[i].date  = CommBase.strtoi(json.getString("date"));
			dyoper.bd[i].time  = CommBase.strtoi(json.getString("time"));
			dyoper.bd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy"  + i));
			dyoper.bd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj" + i));
			dyoper.bd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf" + i));
			dyoper.bd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp" + i));
			dyoper.bd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg" + i));
		}
		
		//jack 20140612 start
		String tmp_str = "";
		if (json.containsKey("buy_dl")) {
			dyoper.bz.buy_dl 		= CommBase.strtof(json.getString("buy_dl"));
			dyoper.bz.pay_bmc 		= CommBase.strtof(json.getString("pay_bmc"));
//			dyoper.bz.shutdown_bd 	= CommBase.strtof(json.getString("shutdown_bd"));
			
			dyoper.passrand.update_flag = CommBase.strtoi(json.getString("update_flag"));
			
			tmp_str = json.getString("card_rand");
			byte card_rand[] = ComntFunc.string2Byte(tmp_str);
			ComntFunc.arrayCopy(dyoper.passrand.card_rand, card_rand);
			
			tmp_str = json.getString("card_pass");
			byte card_pass[] = ComntFunc.string2Byte(tmp_str);
			ComntFunc.arrayCopy(dyoper.passrand.card_pass, card_pass);
			
//			tmp_str = json.getString("reserve1");
//			byte reserve1[] = ComntFunc.string2Byte(tmp_str);
//			ComntFunc.arrayCopy(dyoper.passrand.reserve1, reserve1);
//			
//			tmp_str = json.getString("reserve2");
//			byte reserve2[] = ComntFunc.string2Byte(tmp_str);
//			ComntFunc.arrayCopy(dyoper.passrand.reserve2, reserve2);			
		}
		//jack 20140612 end
		
		
		dyOperDestory = SDDef.EMPTY;
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
		ret_val = ComntOperDyFunc.DYDestory(user_name, i_user_data1, i_user_data2, rtu_para, mp_id, dyoper, operidx, err_str_1,task_result_detail);
		
		if (ret_val) {
			dyOperDestory = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
		else {
			return SDDef.FAIL;
		}
	}
	
	public String taskProc() {
		ComntParaBase.OpDetailInfo op_detail = new ComntParaBase.OpDetailInfo();
		StringBuffer err_str_1 				 = new StringBuffer();
		ComntParaBase.SALEMAN_TASK_RESULT_DETAIL 	task_result_detail = new ComntParaBase.SALEMAN_TASK_RESULT_DETAIL();
		
		int rtu_id    = CommBase.strtoi(userData1);
		int userop_id = CommBase.strtoi(userData2); 
		short mp_id   = (short)CommBase.strtoi(mpid);
		byte gsm_flag = (byte)CommBase.strtoi(gsmflag);
		byte op_result = 0;	//返回结果
		
		ComntParaBase.RTU_PARA rtu_para = ComntParaBase.loadRtuActPara(rtu_id);
		
		if (rtu_para == null) {
			result = SDDef.FAIL;
			op_detail.addTaskInfo("ERR:无效的终端ID[" + rtu_id + "]");
			detailInfo = op_detail.toJsonString(); 
			return SDDef.SUCCESS;
		}
		String user_name = ComntParaBase.getUserName();
		try{
			String ret_val = SDDef.FAIL;
			if (userop_id == ComntUseropDef.YFF_DYOPER_ADDRES) {			//低压操作-开户
				ret_val = DYAddRes(user_name, rtu_para, mp_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_DYOPER_PAY) {			//低压操作-缴费
				ret_val = DYPay(user_name, rtu_para, mp_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_DYOPER_REPAIR) {		//低压操作-补卡
				ret_val = DYRepair(user_name, rtu_para, mp_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_DYOPER_REWRITE) {		//低压操作-补写卡
				ret_val = DYReWrite(user_name, rtu_para, mp_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_DYOPER_REVER) {		//低压操作-冲正
				ret_val = DYRever(user_name, rtu_para, mp_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_DYOPER_CHANGEMETER) {	//低压操作-换表换倍率
				ret_val = DYChgMeter(user_name, rtu_para, mp_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_DYOPER_CHANGERATE) {	//低压操作-换电价
				ret_val = DYChgRate(user_name, rtu_para, mp_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_DYOPER_PROTECT) {		//低压操作-保电
				ret_val = DYProtect(user_name, rtu_para, mp_id, gsm_flag, op_detail, err_str_1, task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_DYOPER_UDPATESTATE) {	//低压操作-强制状态更新
				ret_val = DySetUpdate(user_name, rtu_para, mp_id, gsm_flag, op_detail, err_str_1, task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_DYOPER_RECALC) {		//低压操作-重新计算剩余金额
				ret_val = DYReCalc(user_name, rtu_para, mp_id, gsm_flag, op_detail, err_str_1, task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_DYOPER_JSBC) {			//低压操作-结算补差
				ret_val = DYJsbc(user_name, rtu_para, mp_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_DYOPER_REFXDF) {		//低压操作-发行电费
				ret_val = DYReFxdf(user_name, rtu_para, mp_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_DYOPER_REJTREST) {		//低压操作-重新阶梯清零
				ret_val = DYReJtRest(user_name, rtu_para, mp_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_DYOPER_RESETDOC) {		//低压操作-更改参数  预付费参数
				ret_val = DYResetDoc(user_name, rtu_para, mp_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_DYOPER_GETREMAIN) {	//低压操作-返回余额
				ret_val = DYGetRemain(user_name, rtu_para, mp_id, gsm_flag, op_detail, err_str_1, task_result_detail);
			}	
			else if (userop_id == ComntUseropDef.YFF_DYOPER_GPARASTATE) {	//低压操作-获得预付费参数及状态
				ret_val = DYGetPayParaState(user_name, rtu_para, mp_id, op_detail, err_str_1, task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_DYOPER_RESTART) {		//低压操作-恢复
				ret_val = DYRestart(user_name, rtu_para, mp_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_DYOPER_PAUSE) {		//低压操作-暂停
				ret_val = DYPause(user_name, rtu_para, mp_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_DYOPER_DESTORY) {		//低压操作-销户
				ret_val = DYDestory(user_name, rtu_para, mp_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
	
			else {
				op_detail.addTaskInfo("ERR:未知的操作类型[" + userop_id + "]");
			}
			result 			= ret_val;
			err_strinfo		= err_str_1.toString();
			detailInfo 		= op_detail.toJsonString();
			operErr			= ComntParaBase.makeOperErrCode(task_result_detail);
		}catch (Exception e) {

			CommFunc.err_log(e);
			result = SDDef.FAIL;
			err_strinfo = e.getMessage();
		}
//		task_resultinfo = task_result_detail;	//以后再详细描述
		return SDDef.SUCCESS;
	}
	
	//低压保电 存入历史库 author ylc 9.7
	public String dbCtrlOper(){			
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.SIMPLIFIED_CHINESE);
		String now = sdf.format(date);
		String day = now.split("-")[0];
		String time = now.split("-")[1];
		
		try {
			JSONObject json = JSONObject.fromObject(result);
			
			String rtu_id = json.getString("rtu_id");
			String mp_id = json.getString("mp_id");
			String op_type = json.getString("op_type");
			//sql = "select id from residentpara where rtu_id="+rtu_id;
			sql = "select id from residentpara a,meterpara m where a.rtu_id="+rtu_id + " and m.rtu_id= a.rtu_id and m.resident_id=a.id and m.mp_id =" + mp_id;
			int res_id = -1;
			rs = j_dao.executeQuery(sql);
			if(rs.next()){
				res_id = rs.getInt(1);
			}

			sql = "insert into yddataben.dbo.jfk"+day.substring(0,4)+"(rtu_id,mp_id,res_id,op_date,op_time,op_man,op_type,shuttime,op_result) " +
					"values("+rtu_id+","+mp_id+","+res_id+","+day+","+time+",'"+CommFunc.getYffMan().getName()+"',"+op_type+",0,0)";
			
			if(j_dao.executeUpdate(sql)){
				result = SDDef.SUCCESS;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			j_dao.closeRs(rs);
		}
		
		return SDDef.SUCCESS;
	}	
	
	// 20141113 dubr  低压-工具-参数召设  将新ESAM表号更新到库中
	public String UpdateEsameNo(){
		JDBCDao j_dao = new JDBCDao();
	
		String sql = null;
		JSONObject json = JSONObject.fromObject(dyUpdateEsameNo);
		try {
			sql = "update ydparaben.dbo.MeterPara set meter_id ="+json.getString("meterno")+ " where rtu_id = "+json.getString("rtu_id") +" and mp_id ="+json.getString("mp_id");
			if(j_dao.executeUpdate(sql)){
				result = SDDef.SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SDDef.SUCCESS;
	}	
	//end
	
	public String cancelTask() {
		String user_name = ComntParaBase.getUserName();	
		
		int i_user_data1 = CommBase.strtoi(userData1);
		int i_user_data2 = CommBase.strtoi(userData2);

		ComntMsgProc.cancelMsg(user_name, i_user_data1, i_user_data2);
		
		result = SDDef.SUCCESS;	
		return SDDef.SUCCESS;
	}
	
	
	public String getUserData1() {
		return userData1;
	}
	public void setUserData1(String userData1) {
		this.userData1 = userData1;
	}
	public String getUserData2() {
		return userData2;
	}
	public void setUserData2(String userData2) {
		this.userData2 = userData2;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getDetailInfo() {
		return detailInfo;
	}
	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}
	public String getErr_strinfo() {
		return err_strinfo;
	}
	public void setErr_strinfo(String errStrinfo) {
		err_strinfo = errStrinfo;
	}
	public String getTask_resultinfo() {
		return task_resultinfo;
	}
	public void setTask_resultinfo(String taskResultinfo) {
		task_resultinfo = taskResultinfo;
	}
	public String getDyOperAddres() {
		return dyOperAddres;
	}
	public void setDyOperAddres(String dyOperAddres) {
		this.dyOperAddres = dyOperAddres;
	}
	public String getDyOperPay() {
		return dyOperPay;
	}
	public void setDyOperPay(String dyOperPay) {
		this.dyOperPay = dyOperPay;
	}
	public String getDyOperRepair() {
		return dyOperRepair;
	}
	public void setDyOperRepair(String dyOperRepair) {
		this.dyOperRepair = dyOperRepair;
	}
	public String getDyOperRewrite() {
		return dyOperRewrite;
	}
	public void setDyOperRewrite(String dyOperRewrite) {
		this.dyOperRewrite = dyOperRewrite;
	}
	public String getDyOperRever() {
		return dyOperRever;
	}
	public void setDyOperRever(String dyOperRever) {
		this.dyOperRever = dyOperRever;
	}
	public String getDyOperChangeMeter() {
		return dyOperChangeMeter;
	}
	public void setDyOperChangeMeter(String dyOperChangeMeter) {
		this.dyOperChangeMeter = dyOperChangeMeter;
	}
	public String getDyOperChangeRate() {
		return dyOperChangeRate;
	}
	public void setDyOperChangeRate(String dyOperChangeRate) {
		this.dyOperChangeRate = dyOperChangeRate;
	}
	public String getDyOperProtect() {
		return dyOperProtect;
	}
	public void setDyOperProtect(String dyOperProtect) {
		this.dyOperProtect = dyOperProtect;
	}
	public String getDyOperUdpateState() {
		return dyOperUpdateState;
	}
	public void setDyOperUdpateState(String dyOperUdpateState) {
		this.dyOperUpdateState = dyOperUdpateState;
	}
	public String getDyOperRecalc() {
		return dyOperRecalc;
	}
	public void setDyOperRecalc(String dyOperRecalc) {
		this.dyOperRecalc = dyOperRecalc;
	}
	public String getDyOperJsbc() {
		return dyOperJsbc;
	}

	public void setDyOperJsbc(String dyOperJsbc) {
		this.dyOperJsbc = dyOperJsbc;
	}

	public String getDyOperReFxdf() {
		return dyOperReFxdf;
	}

	public void setDyOperReFxdf(String dyOperReFxdf) {
		this.dyOperReFxdf = dyOperReFxdf;
	}
	public String getDyOperReJtRest() {
		return dyOperReJtRest;
	}

	public void setDyOperReJtRest(String dyOperReJtRest) {
		this.dyOperReJtRest = dyOperReJtRest;
	}

	public String getDyOperGetRemain() {
		return dyOperGetRemain;
	}
	public void setDyOperGetRemain(String dyOperGetRemain) {
		this.dyOperGetRemain = dyOperGetRemain;
	}
	public String getDyOperGParaState() {
		return dyOperGParaState;
	}
	public void setDyOperGParaState(String dyOperGParaState) {
		this.dyOperGParaState = dyOperGParaState;
	}
	public String getDyOperReStart() {
		return dyOperReStart;
	}
	public void setDyOperReStart(String dyOperReStart) {
		this.dyOperReStart = dyOperReStart;
	}
	public String getDyOperPause() {
		return dyOperPause;
	}
	public void setDyOperPause(String dyOperPause) {
		this.dyOperPause = dyOperPause;
	}
	public String getDyOperDestory() {
		return dyOperDestory;
	}
	public void setDyOperDestory(String dyOperDestory) {
		this.dyOperDestory = dyOperDestory;
	}
	public String getDyOperUpdateState() {
		return dyOperUpdateState;
	}
	public void setDyOperUpdateState(String dyOperUpdateState) {
		this.dyOperUpdateState = dyOperUpdateState;
	}
	public String getGsmflag() {
		return gsmflag;
	}
	public void setGsmflag(String gsmflag) {
		this.gsmflag = gsmflag;
	}
	public String getMpid() {
		return mpid;
	}
	public void setMpid(String mpid) {
		this.mpid = mpid;
	}

	public String getDyOperResetDoc() {
		return dyOperResetDoc;
	}

	public void setDyOperResetDoc(String dyOperResetDoc) {
		this.dyOperResetDoc = dyOperResetDoc;
	}
	public String getOperErr() {
		return operErr;
	}
	public void setOperErr(String operErr) {
		this.operErr = operErr;
	}
	
	
}
