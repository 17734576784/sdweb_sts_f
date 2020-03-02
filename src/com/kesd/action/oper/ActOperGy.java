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
import com.kesd.comnt.ComntMsgGy;
import com.kesd.comnt.ComntMsgProc;
import com.kesd.comnt.ComntProtMsg;
import com.kesd.comntpara.ComntOperGyFunc;
import com.kesd.comntpara.ComntParaBase;
import com.kesd.comntpara.ComntUseropDef;
import com.kesd.dbpara.JSPara;
import com.kesd.service.DBOper;
import com.libweb.common.CommBase;
import com.libweb.comnt.ComntFunc;
import com.libweb.dao.JDBCDao;

public class ActOperGy {
	private static final long serialVersionUID = 3607391502741009698L;
	private String  userData1 			= null;		//RTU_ID
	private String  userData2 			= null;		//USEROP_ID
	private String 	result 				= null;		//SUCCESS/FAIL
	private String 	detailInfo 			= null;		//返回详细信息(JSON)
	private String	err_strinfo			= null;		//返回错误信息
	private String	task_resultinfo		= null;		//返回任务详细信息
	private String 	gsmflag				= null;		//是否发送短信
	private String  zjgid				= null;		//总加组编号
	
	private String	gyOperAddCus		= null;		//高压操作-开户
	private String	gyOperPay			= null;		//高压操作-缴费
	private String  gyOperRepair        = null;     //高压操作-补卡
	private String	gyOperRever			= null;		//高压操作-冲正
	private String	gyOperChangeMeter	= null;		//高压操作-换表换倍率
	private String	gyOperChangeRate	= null;		//高压操作-换电价
	private String	gyOperProtect		= null;		//高压操作-保电
	private String	gyOperUpdateState	= null;		//高压操作-强制状态更新
	private String	gyOperRecalc		= null;		//高压操作-重新计算剩余金额
	private String	gyOperRejs			= null;		//高压操作-重新结算
	private String	gyOperGetRemain		= null;		//高压操作-返回余额
	private String	gyOperGetJSBCRemain	= null;		//高压操作-结算补差返回余额
	private String	gyOperChanePayAdd	= null;		//高压操作-换基本费
	private String	gyOperGParaState	= null;		//高压操作-获得预付费参数及状态
	private String	gyOperReStart		= null;		//高压操作-恢复
	private String	gyOperPause			= null;		//高压操作-暂停
	private String	gyOperDestory		= null;		//高压操作-销户
	private String	gyOperResetDoc		= null;		//高压操作-更改参数  预付费参数
	
	private String operErr				= null;		//返回错误信息描述,在js弹出框中显示
	
	/**
	 * zhp
	 * 20131128修改说明：
	 * 功能：当主站操作失败时，在操作界面弹出框中提示出错误信息的描述和代码
	 * 每个方法中增加了对task_result_detail的值的返回。
	 * 增加了参数operErr,用于向js返回信息，
	 * 主方法中使用ComntParaBase.makeOperErrCode(task_result_detail)对operErr进行显示组合;
	*/
	
	//高压操作-开户
	private String GYAddRes(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception {

		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID	
		
		boolean ret_val = false;
		
		if (gyOperAddCus != null) gyOperAddCus = gyOperAddCus.trim();
		if (gyOperAddCus == null || gyOperAddCus.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(gyOperAddCus);
		//20131019将ComntMsg修应该为ComntMsgGy 
		ComntMsgGy.MSG_GYOPER_ADDCUS gyoper = new ComntMsgGy.MSG_GYOPER_ADDCUS();
		
		gyoper.testf = 0;
		gyoper.gsmf  = gsm_flag;
		gyoper.zjgid = zjg_id;
		ComntFunc.string2Byte(user_name, gyoper.operman);
		
		
		gyoper.myffalarmid      = (short)CommBase.strtoi(json.getString("myffalarmid"));
		gyoper.paytype  	    = (byte)CommBase.strtoi(json.getString("paytype"));
		gyoper.opresult 	    = op_result;
		gyoper.buynum 			= 1;
		
		gyoper.alarm_val1 		= CommBase.strtof(json.getString("alarm_val1"));
		gyoper.alarm_val2 		= CommBase.strtof(json.getString("alarm_val2"));
		
		gyoper.bz.buy_dl  		= CommBase.strtof(json.getString("buy_dl"));
		gyoper.bz.pay_bmc		= CommBase.strtof(json.getString("pay_bmc"));
		gyoper.bz.shutdown_bd 	= CommBase.strtof(json.getString("shutdown_bd"));
		
		gyoper.money.pay_money  = CommBase.strtof(json.getString("pay_money"));
		gyoper.money.zb_money	= CommBase.strtof(json.getString("zb_money"));
		gyoper.money.othjs_money= CommBase.strtof(json.getString("othjs_money"));
		gyoper.money.all_money  = CommBase.strtof(json.getString("all_money"));

		for(int i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
			gyoper.bd[i].rtu_id = CommBase.strtoi(json.getString("rtu_id"));
			gyoper.bd[i].mp_id  = (short)CommBase.strtoi(json.getString("mp_id"+i));
			gyoper.bd[i].date   = CommBase.strtoi(json.getString("date"));
			gyoper.bd[i].time   = CommBase.strtoi(json.getString("time"));

			gyoper.bd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy"+i));
			gyoper.bd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj"+i));
			gyoper.bd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf"+i));
			gyoper.bd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp"+i));
			gyoper.bd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg"+i));
			
			gyoper.bd[i].bd_fy  = CommBase.strtof(json.getString("bd_fy"+i));
			gyoper.bd[i].bd_fyj = CommBase.strtof(json.getString("bd_fyj"+i));
			gyoper.bd[i].bd_fyf = CommBase.strtof(json.getString("bd_fyf"+i));
			gyoper.bd[i].bd_fyp = CommBase.strtof(json.getString("bd_fyp"+i));
			gyoper.bd[i].bd_fyg = CommBase.strtof(json.getString("bd_fyg"+i));
			
			gyoper.bd[i].bd_zw	= CommBase.strtof(json.getString("bd_zw"+i));
			gyoper.bd[i].bd_fw	= CommBase.strtof(json.getString("bd_fw"+i));
		}
		
		//sjh 201208013
		gyoper.total_yzbdl = CommBase.strtof(json.getString("total_yzbdl"));
		gyoper.total_wzbdl = CommBase.strtof(json.getString("total_wzbdl"));
		
		gyOperAddCus = SDDef.EMPTY;
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
		ret_val = ComntOperGyFunc.GYAddRes(user_name, i_user_data1, i_user_data2, rtu_para, zjg_id, 
				                         gyoper, operidx, err_str_1, task_result_detail);
		
		if (ret_val) {
			gyOperAddCus = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
		else {
			return SDDef.FAIL;
		}
	}
	
	//高压操作-缴费
	private String GYPay(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception {
		
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;		
		
		if (gyOperPay != null) gyOperPay = gyOperPay.trim();
		if (gyOperPay == null || gyOperPay.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = null;
		try{
			json = JSONObject.fromObject(gyOperPay);
		}catch (Exception e) {
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		
		//20131019将ComntMsg修应该为ComntMsgGy
		ComntMsgGy.MSG_GYOPER_PAY gyoper = new ComntMsgGy.MSG_GYOPER_PAY();
		
		gyoper.testf = 0;
		gyoper.gsmf  = gsm_flag;
		gyoper.zjgid = zjg_id;
		ComntFunc.string2Byte(user_name, gyoper.operman);
		
		gyoper.paytype  	= (byte)CommBase.strtoi(json.getString("paytype"));
		gyoper.opresult 	= op_result;
		gyoper.buynum 		= CommBase.strtoi(json.getString("buynum")) + 1;
		
		gyoper.alarm_val1 	= CommBase.strtof(json.getString("alarm_val1"));
		gyoper.alarm_val2 	= CommBase.strtof(json.getString("alarm_val2"));
		
		gyoper.bz.buy_dl		= CommBase.strtof(json.getString("buy_dl"));
		gyoper.bz.pay_bmc		= CommBase.strtof(json.getString("pay_bmc"));
		gyoper.bz.shutdown_bd	= CommBase.strtof(json.getString("shutdown_bd"));
		
		gyoper.money.pay_money  = CommBase.strtof(json.getString("pay_money"));
		gyoper.money.othjs_money= CommBase.strtof(json.getString("othjs_money"));
		gyoper.money.zb_money	= CommBase.strtof(json.getString("zb_money"));
		gyoper.money.all_money  = CommBase.strtof(json.getString("all_money"));
		
		//远程金额、远程表底缴费要加表底信息
		if(!json.getString("type").isEmpty() && json.getString("type")!= null){
		if("localmoney".equals(json.getString("type")) || "localbd".equals(json.getString("type"))){
			for(int i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
				gyoper.bd[i].rtu_id = CommBase.strtoi(json.getString("rtu_id"));
				gyoper.bd[i].mp_id  = (short)CommBase.strtoi(json.getString("mp_id"+i));
				gyoper.bd[i].date   = CommBase.strtoi(json.getString("date"+i));
				gyoper.bd[i].time   = CommBase.strtoi(json.getString("time"+i));

				gyoper.bd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy"+i));
				gyoper.bd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj"+i));
				gyoper.bd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf"+i));
				gyoper.bd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp"+i));
				gyoper.bd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg"+i));
				
				gyoper.bd[i].bd_fy  = CommBase.strtof(json.getString("bd_fy"+i));
				gyoper.bd[i].bd_fyj = CommBase.strtof(json.getString("bd_fyj"+i));
				gyoper.bd[i].bd_fyf = CommBase.strtof(json.getString("bd_fyf"+i));
				gyoper.bd[i].bd_fyp = CommBase.strtof(json.getString("bd_fyp"+i));
				gyoper.bd[i].bd_fyg = CommBase.strtof(json.getString("bd_fyg"+i));
				
				gyoper.bd[i].bd_zw	= CommBase.strtof(json.getString("bd_zw"+i));
				gyoper.bd[i].bd_fw	= CommBase.strtof(json.getString("bd_fw"+i));
			}
		  }
		}
		
		gyOperPay = SDDef.EMPTY;
		
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
		ret_val = ComntOperGyFunc.GYPay(user_name, i_user_data1, i_user_data2, rtu_para, 
									  zjg_id, gyoper, operidx, err_str_1, task_result_detail);
		
		if (ret_val) {
			gyOperPay = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
		else {
			return SDDef.FAIL;
		}
	}	
	
	//高压操作-补卡 author ylc
	private String GYRepair(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;
		if (gyOperRepair != null) gyOperRepair = gyOperRepair.trim();
		if (gyOperRepair == null || gyOperRepair.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		JSONObject json = JSONObject.fromObject(gyOperRepair);
		//String     []str_para	    = dyOperRepair.split(",");
		
		//20131019将ComntMsg修应该为ComntMsgGy
		ComntMsgGy.MSG_GYOPER_REPAIR gyoper = new ComntMsgGy.MSG_GYOPER_REPAIR();
		
		gyoper.testf = 0;
		gyoper.gsmf  = gsm_flag;
		gyoper.zjgid  = zjg_id;
		ComntFunc.string2Byte(user_name, gyoper.operman);
		gyoper.opresult 	= op_result;
		
		gyoper.old_op_date	= CommBase.strtoi(json.getString("old_op_date"));
		gyoper.old_op_time	= CommBase.strtoi(json.getString("old_op_time"));
			
		gyOperRepair = SDDef.EMPTY;
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
	    ret_val = ComntOperGyFunc.GYRepair(user_name, i_user_data1, i_user_data2, rtu_para, zjg_id, gyoper, operidx, err_str_1, task_result_detail);
		
	    if(ret_val){
			gyOperRepair = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
	    return SDDef.FAIL;
	}
	
	
	//高压操作-冲正
	private String GYRever(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception{

		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;
		
		if (gyOperRever != null) gyOperRever = gyOperRever.trim();
		if (gyOperRever == null || gyOperRever.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(gyOperRever);
		
		//20131019将ComntMsg修应该为ComntMsgGy
		ComntMsgGy.MSG_GYOPER_REVER gyoper = new ComntMsgGy.MSG_GYOPER_REVER();
		
		
		gyoper.testf = 0;
		gyoper.gsmf  = gsm_flag;
		gyoper.zjgid = zjg_id;
		ComntFunc.string2Byte(user_name, gyoper.operman);
		gyoper.paytype		= (byte)CommBase.strtoi(json.getString("paytype"));
		gyoper.opresult 	= op_result;
		
		gyoper.oldopdate	= CommBase.strtoi(json.getString("oldopdate"));
		gyoper.oldoptime	= CommBase.strtoi(json.getString("oldoptime"));
		
		gyoper.alarm_val1	= CommBase.strtof(json.getString("alarm_val1"));
		gyoper.alarm_val2	= CommBase.strtof(json.getString("alarm_val2"));

		gyoper.bz.buy_dl 			= CommBase.strtof(json.getString("buy_dl"));
		gyoper.bz.pay_bmc 			= CommBase.strtof(json.getString("pay_bmc"));
		gyoper.bz.shutdown_bd 		= CommBase.strtof(json.getString("shutdown_bd"));
		
		gyoper.newmoney.pay_money   = CommBase.strtof(json.getString("pay_money"));
		gyoper.newmoney.othjs_money = CommBase.strtof(json.getString("othjs_money"));
		gyoper.newmoney.zb_money	= CommBase.strtof(json.getString("zb_money"));
		gyoper.newmoney.all_money   = CommBase.strtof(json.getString("all_money"));
		
		boolean misUseFlag = Boolean.parseBoolean(json.getString("misUseflag"));
		//如果使用MIS，不能隔天冲正
		if(misUseFlag) {
			Date date = new Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
			int today = Integer.parseInt(sdf.format(date));
			if(today != gyoper.oldopdate) {
				return SDDef.FAIL;
			}
		}
		
		gyOperRever = SDDef.EMPTY;
		
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
		ret_val = ComntOperGyFunc.GYRever(user_name, i_user_data1, i_user_data2, rtu_para, zjg_id, gyoper, operidx, err_str_1, task_result_detail);
		
		if(ret_val){
			gyOperRever = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
		
		return SDDef.FAIL;
	}
	
	//高压操作-换表换倍率
	private String GYChgMeter(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception {
        
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		if (gyOperChangeMeter != null) gyOperChangeMeter = gyOperChangeMeter.trim();
		if (gyOperChangeMeter == null || gyOperChangeMeter.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		boolean ret_val = false;
		
		JSONObject json = JSONObject.fromObject(gyOperChangeMeter);
		
		////20131019将ComntMsg修应该为ComntMsgGy
		ComntMsgGy.MSG_GYOPER_CHANGEMETER gyoper = new ComntMsgGy.MSG_GYOPER_CHANGEMETER();
		
		gyoper.testf = 0;
		gyoper.gsmf  = gsm_flag;
		gyoper.zjgid = zjg_id;
		ComntFunc.string2Byte(user_name, gyoper.operman);
		gyoper.opresult 	= op_result;
		gyoper.buynum 		= CommBase.strtoi(json.getString("buynum"));
		
		gyoper.alarm_val1	= CommBase.strtof(json.getString("alarm_val1"));
		gyoper.alarm_val2	= CommBase.strtof(json.getString("alarm_val2"));
		
		gyoper.newmoney.pay_money  = CommBase.strtof(json.getString("pay_money"));
		gyoper.newmoney.othjs_money= CommBase.strtof(json.getString("othjs_money"));
		gyoper.newmoney.zb_money	= CommBase.strtof(json.getString("zb_money"));
		gyoper.newmoney.all_money  = CommBase.strtof(json.getString("all_money"));
		
		gyoper.bz.buy_dl 			= CommBase.strtof(json.getString("buydl"));
		gyoper.bz.pay_bmc 			= CommBase.strtof(json.getString("paybmc"));
		gyoper.bz.shutdown_bd 		= CommBase.strtof(json.getString("shutdown_bd"));
		
		int i=0;
		
		for(i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++){
			
			gyoper.oldbd[i].rtu_id= CommBase.strtoi(json.getString("rtu_id"));
			gyoper.oldbd[i].mp_id = (short)CommBase.strtoi(json.getString("mp_id" + i));
			
			gyoper.newbd[i].rtu_id= CommBase.strtoi(json.getString("rtu_id"));
			gyoper.newbd[i].mp_id = (short)CommBase.strtoi(json.getString("mp_id" + i));
			
			gyoper.oldbd[i].date  = CommBase.strtoi(json.getString("o_date"));
			gyoper.oldbd[i].time  = CommBase.strtoi(json.getString("o_time"));
			
			gyoper.newbd[i].date  = CommBase.strtoi(json.getString("n_date"));
			gyoper.newbd[i].time  = CommBase.strtoi(json.getString("n_time"));
			
			gyoper.oldbd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy_o"  + i));
			gyoper.oldbd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj_o" + i));
			gyoper.oldbd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf_o" + i));
			gyoper.oldbd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp_o" + i));
			gyoper.oldbd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg_o" + i));
			gyoper.oldbd[i].bd_zw = CommBase.strtof(json.getString("bd_zw_o" + i));
			
			gyoper.oldbd[i].bd_fy  = CommBase.strtof(json.getString("bd_fy_o"  + i));
			gyoper.oldbd[i].bd_fyj = CommBase.strtof(json.getString("bd_fyj_o" + i));
			gyoper.oldbd[i].bd_fyf = CommBase.strtof(json.getString("bd_fyf_o" + i));
			gyoper.oldbd[i].bd_fyp = CommBase.strtof(json.getString("bd_fyp_o" + i));
			gyoper.oldbd[i].bd_fyg = CommBase.strtof(json.getString("bd_fyg_o" + i));
			gyoper.oldbd[i].bd_fw  = CommBase.strtof(json.getString("bd_fw_o"  + i));
			
			gyoper.newbd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy_n"  + i));
			gyoper.newbd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj_n" + i));
			gyoper.newbd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf_n" + i));
			gyoper.newbd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp_n" + i));
			gyoper.newbd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg_n" + i));
			gyoper.newbd[i].bd_zw  = CommBase.strtof(json.getString("bd_zw_n"  + i));
			
			gyoper.newbd[i].bd_fy  = CommBase.strtof(json.getString("bd_fy_n"  + i));
			gyoper.newbd[i].bd_fyj = CommBase.strtof(json.getString("bd_fyj_n" + i));
			gyoper.newbd[i].bd_fyf = CommBase.strtof(json.getString("bd_fyf_n" + i));
			gyoper.newbd[i].bd_fyp = CommBase.strtof(json.getString("bd_fyp_n" + i));
			gyoper.newbd[i].bd_fyg = CommBase.strtof(json.getString("bd_fyg_n" + i));
			gyoper.newbd[i].bd_fw  = CommBase.strtof(json.getString("bd_fw_n"  + i));
			
		}
		
		gyoper.chgmterrate.rtu_id 	= CommBase.strtoi(json.getString("rtu_id"));
		gyoper.chgmterrate.mp_id 	= (short)CommBase.strtoi(json.getString("chg_mpid"));
		gyoper.chgmterrate.chg_date = CommBase.strtoi(json.getString("chg_date"));
		gyoper.chgmterrate.chg_time = CommBase.strtoi(json.getString("chg_time"));
		gyoper.chgmterrate.chg_type = (short)CommBase.strtoi(json.getString("chg_type"));

		gyoper.chgmterrate.oldct_numerator 	= CommBase.strtoi(json.getString("oldct_n"));
		gyoper.chgmterrate.oldct_denominator= CommBase.strtoi(json.getString("oldct_d"));
		gyoper.chgmterrate.oldct_ratio 		= CommBase.strtof(json.getString("oldct_r"));
		gyoper.chgmterrate.newct_numerator 	= CommBase.strtoi(json.getString("newct_n"));
		gyoper.chgmterrate.newct_denominator= CommBase.strtoi(json.getString("newct_d"));
		gyoper.chgmterrate.newct_ratio 		= CommBase.strtof(json.getString("newct_r"));
		
		gyoper.chgmterrate.oldpt_numerator 	= CommBase.strtoi(json.getString("oldpt_n"));
		gyoper.chgmterrate.oldpt_denominator= CommBase.strtoi(json.getString("oldpt_d"));
		gyoper.chgmterrate.oldpt_ratio 		= CommBase.strtof(json.getString("oldpt_r"));
		gyoper.chgmterrate.newpt_numerator 	= CommBase.strtoi(json.getString("newpt_n"));
		gyoper.chgmterrate.newpt_denominator= CommBase.strtoi(json.getString("newpt_d"));
		gyoper.chgmterrate.newpt_ratio 		= CommBase.strtof(json.getString("newpt_r"));

		gyoper.chgmterrate.old_zyz 	= CommBase.strtof(json.getString("bd_zy_o"));
		gyoper.chgmterrate.old_zyj 	= CommBase.strtof(json.getString("bd_zyj_o"));
		gyoper.chgmterrate.old_zyf 	= CommBase.strtof(json.getString("bd_zyf_o"));
		gyoper.chgmterrate.old_zyp 	= CommBase.strtof(json.getString("bd_zyp_o"));
		gyoper.chgmterrate.old_zyg 	= CommBase.strtof(json.getString("bd_zyg_o"));
		gyoper.chgmterrate.old_zwz 	= CommBase.strtof(json.getString("bd_zw_o"));
		
		gyoper.chgmterrate.old_fyz 	= CommBase.strtof(json.getString("bd_fy_o"));
		gyoper.chgmterrate.old_fyj 	= CommBase.strtof(json.getString("bd_fyj_o"));
		gyoper.chgmterrate.old_fyf 	= CommBase.strtof(json.getString("bd_fyf_o"));
		gyoper.chgmterrate.old_fyp 	= CommBase.strtof(json.getString("bd_fyp_o"));
		gyoper.chgmterrate.old_fyg 	= CommBase.strtof(json.getString("bd_fyg_o"));
		gyoper.chgmterrate.old_fwz 	= CommBase.strtof(json.getString("bd_fw_o"));
		
		gyoper.chgmterrate.new_zyz 	= CommBase.strtof(json.getString("bd_zy_n"));
		gyoper.chgmterrate.new_zyj 	= CommBase.strtof(json.getString("bd_zyj_n"));
		gyoper.chgmterrate.new_zyf 	= CommBase.strtof(json.getString("bd_zyf_n"));
		gyoper.chgmterrate.new_zyp 	= CommBase.strtof(json.getString("bd_zyp_n"));
		gyoper.chgmterrate.new_zyg 	= CommBase.strtof(json.getString("bd_zyg_n"));
		gyoper.chgmterrate.new_zwz 	= CommBase.strtof(json.getString("bd_zw_n"));
		
		gyoper.chgmterrate.new_fyz 	= CommBase.strtof(json.getString("bd_fy_n"));
		gyoper.chgmterrate.new_fyj 	= CommBase.strtof(json.getString("bd_fyj_n"));
		gyoper.chgmterrate.new_fyf 	= CommBase.strtof(json.getString("bd_fyf_n"));
		gyoper.chgmterrate.new_fyp 	= CommBase.strtof(json.getString("bd_fyp_n"));
		gyoper.chgmterrate.new_fyg 	= CommBase.strtof(json.getString("bd_fyg_n"));
		gyoper.chgmterrate.new_fwz 	= CommBase.strtof(json.getString("bd_fw_n"));
		
		gyOperChangeMeter = SDDef.EMPTY;
		
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
		ret_val = ComntOperGyFunc.GYChgMeter(user_name, i_user_data1, i_user_data2, rtu_para, zjg_id, gyoper, operidx, err_str_1, task_result_detail);
		
		if (ret_val) {
			gyOperChangeMeter = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
		else {
			return SDDef.FAIL;
		}
	}	
	
	//高压操作-换电价
	private String GYChgRate(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception {
        
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;		
		
		boolean ret_val = false;
		
		if (gyOperChangeRate != null) gyOperChangeRate = gyOperChangeRate.trim();
		if (gyOperChangeRate == null || gyOperChangeRate.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(gyOperChangeRate);
		
		//20131019将ComntMsg修应该为ComntMsgGy
		ComntMsgGy.MSG_GYOPER_CHANGERATE gyoper = new ComntMsgGy.MSG_GYOPER_CHANGERATE();
		
		gyoper.testf = 0;
		gyoper.gsmf  = gsm_flag;
		gyoper.zjgid = zjg_id;
		ComntFunc.string2Byte(user_name, gyoper.operman);
		gyoper.opresult 	= op_result;
		
		gyoper.chg_type 	= 1;
		for (i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
			gyoper.fee_chgid[i] 	= (short)CommBase.strtoi(json.getString("chgid" + i));
		}
		
		gyoper.fee_chgdate 	= CommBase.strtoi(json.getString("chg_date"));
		gyoper.fee_chgtime 	= CommBase.strtoi(json.getString("chg_time"));

		for(i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++){
			gyoper.bd[i].rtu_id= CommBase.strtoi(json.getString("rtu_id"));
			gyoper.bd[i].mp_id = (short)CommBase.strtoi(json.getString("mp_id" + i));
			gyoper.bd[i].date  = CommBase.strtoi(json.getString("date"));
			gyoper.bd[i].time  = CommBase.strtoi(json.getString("time"));

			gyoper.bd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy"  + i));
			gyoper.bd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj" + i));
			gyoper.bd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf" + i));
			gyoper.bd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp" + i));
			gyoper.bd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg" + i));

			gyoper.bd[i].bd_fy  = CommBase.strtof(json.getString("bd_fy"+i));
			gyoper.bd[i].bd_fyj = CommBase.strtof(json.getString("bd_fyj"+i));
			gyoper.bd[i].bd_fyf = CommBase.strtof(json.getString("bd_fyf"+i));
			gyoper.bd[i].bd_fyp = CommBase.strtof(json.getString("bd_fyp"+i));
			gyoper.bd[i].bd_fyg = CommBase.strtof(json.getString("bd_fyg"+i));

			gyoper.bd[i].bd_zw	= CommBase.strtof(json.getString("bd_zw"+i));
			gyoper.bd[i].bd_fw	= CommBase.strtof(json.getString("bd_fw"+i));				
		}

		gyOperChangeRate = SDDef.EMPTY;
		
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
		ret_val = ComntOperGyFunc.GYChgRate(user_name, i_user_data1, i_user_data2, rtu_para, zjg_id, gyoper, operidx, err_str_1, task_result_detail);
		
		if (ret_val) {
			gyOperChangeRate = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
		else {
			return SDDef.FAIL;
		}
	}
	
	//高压操作-保电
	private String GYProtect(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {

		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;
		
		if (gyOperProtect != null) gyOperProtect = gyOperProtect.trim();
		if (gyOperProtect == null || gyOperProtect.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(gyOperProtect);
		
		//20131019将ComntMsg修应该为ComntMsgGy
		ComntMsgGy.MSG_GYOPER_PROTECT gyoper = new ComntMsgGy.MSG_GYOPER_PROTECT();
		
		gyoper.testf = 0;
		gyoper.gsmf  = gsm_flag;
		gyoper.zjgid = zjg_id;
		ComntFunc.string2Byte(user_name, gyoper.operman);
		
		
		gyoper.m_prot_st 	= (byte)CommBase.strtoi(json.getString("m_prot_st"));
		gyoper.m_prot_ed 	= (byte)CommBase.strtoi(json.getString("m_prot_ed"));
		
		ret_val = ComntOperGyFunc.GYProtect(user_name, i_user_data1, i_user_data2, rtu_para, zjg_id, gyoper, err_str_1, task_result_detail);
		
		if(ret_val){
			return SDDef.SUCCESS;
		}
		
		return SDDef.FAIL;
	}	
	
	//高压操作-强制状态更新
	private String GySetUpdate(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, byte gsm_flag,  ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;
		
		if (gyOperUpdateState != null) gyOperUpdateState = gyOperUpdateState.trim();
		if (gyOperUpdateState == null || gyOperUpdateState.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		ComntProtMsg.YFF_DATA_GYOPER_PARASTATE gyoper_parastate = new ComntProtMsg.YFF_DATA_GYOPER_PARASTATE(); 
		ret_val = ComntOperGyFunc.GYGetPayParaState(user_name, i_user_data1, i_user_data2, rtu_para, 
												  zjg_id, gyoper_parastate, err_str_1, task_result_detail);
		
		if (ret_val == false) {
			op_detail.addTaskInfo("ERR:获取预付费参数及状态错误");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(gyOperUpdateState);
		
		//20131019将ComntMsg修应该为ComntMsgGy
		ComntMsgGy.MSG_GYOPER_UPDATE gyoper = new ComntMsgGy.MSG_GYOPER_UPDATE();
		
		gyoper.testf = 0;
		gyoper.gsmf  = 0;
		gyoper.zjgid = zjg_id;
		ComntFunc.string2Byte(user_name, gyoper.operman);
		
		gyoper.para  	= gyoper_parastate.para;
		gyoper.state 	= gyoper_parastate.state;
		gyoper.alarm 	= gyoper_parastate.alarm;
		gyoper.commdata	= gyoper_parastate.commdata;		
	
		short updType = (short)CommBase.strtoi(json.getString("updType"));
		
		switch (updType){
		case SDDef.YFF_GY_CHGBUYTIME:
			int buy_times = CommBase.strtoi(json.getString("buy_times"));
			gyoper.state.buy_times = buy_times;
			gyoper.state_bn_validf  = 1;
			break;
		case SDDef.YFF_GY_FRMESS_ERR:
			gyoper.alarm.qr_al1_1_state =(byte) ((gyoper.alarm.qr_al1_1_state & 0x3f) | (ComntDef.YFF_QRFLAG_SUCCESS <<6));
			gyoper.alarmvalidf = 1;
			break;
		case SDDef.YFF_GY_FRSOUND_ERR:
			gyoper.alarm.qr_al1_2_state = (byte) ((gyoper.alarm.qr_al1_2_state & 0x3f) | (ComntDef.YFF_QRFLAG_SUCCESS <<6));
			gyoper.alarmvalidf = 1;
			break;
		case SDDef.YFF_GY_SECMESS_ERR:
			gyoper.alarm.qr_al2_1_state = (byte) ((gyoper.alarm.qr_al2_1_state & 0x3f) | (ComntDef.YFF_QRFLAG_SUCCESS <<6));
			gyoper.alarmvalidf = 1;
			break;
		case SDDef.YFF_GY_TOTAL_YWZBDL://追补累计用电量
			
			gyoper.state.total_yzbdl = CommBase.strtof(json.getString("total_yzbdl"));
			gyoper.state.total_wzbdl = CommBase.strtof(json.getString("total_wzbdl"));
			gyoper.state_df_validf  = 1;
			break;
		case SDDef.YFF_GY_ZBELE_MONEY://追补电度电费
			gyoper.state.zbele_money =CommBase.strtof(json.getString("zbele_money"));
			gyoper.state_df_validf  = 1;
			break;
		case SDDef.YFF_GY_ZBJBF_MONEY://追补基本费电费
			gyoper.state.zbjbf_money =CommBase.strtof(json.getString("zbjbf_money"));
			gyoper.state_df_validf  = 1;
			break;
		case SDDef.YFF_GY_FXDF_IALL_MONEY://发行电费当月缴费总金额
			gyoper.state.fxdf_iall_money =CommBase.strtof(json.getString("fxdf_iall_money"));
			gyoper.state_ff_validf  = 1;
			break;
		case SDDef.YFF_GY_FXDF_REMAIN://发行电费后剩余金额
			gyoper.state.fxdf_remain =CommBase.strtof(json.getString("fxdf_remain"));
			gyoper.state_ff_validf  = 1;
			break;
		case SDDef.YFF_GY_FXDF_YM://发行电费数据日期
			gyoper.state.fxdf_ym =CommBase.strtoi(json.getString("fxdf_ym"));
			gyoper.state_ff_validf  = 1;
			break;
		case SDDef.YFF_GY_JSWGBD :	//结算无功表底
			double zwz1 = json.getDouble("jswgbd1");
			double zwz2 = json.getDouble("jswgbd2");
			double zwz3 = json.getDouble("jswgbd3");
			
			gyoper.state.jsbd_zwz[0] = zwz1;
			gyoper.state.jsbd_zwz[1] = zwz2;
			gyoper.state.jsbd_zwz[2] = zwz3;
			
			gyoper.state_js_validf   = 1;
			break;
		default:
			break;
		}
		
		ret_val = ComntOperGyFunc.GYUpdateParaState(user_name, i_user_data1, i_user_data2, rtu_para, 
												  zjg_id, gyoper, err_str_1, task_result_detail);
		
		if (ret_val == false) {
			op_detail.addTaskInfo("ERR:设置预付费参数及状态错误");
			return SDDef.FAIL;			
		}
		
		return SDDef.SUCCESS;
	}	
	
	//高压操作-重新计算剩余金额
	private String GYReCalc(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {

		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;		
		
		boolean ret_val = false;
        
		if (gyOperRecalc != null) gyOperRecalc = gyOperRecalc.trim();
		if (gyOperRecalc == null || gyOperRecalc.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(gyOperRecalc);
		
		//20131019将ComntMsg修应该为ComntMsgGy
		ComntMsgGy.MSG_GYOPER_RECALC gyoper = new ComntMsgGy.MSG_GYOPER_RECALC();
		
		gyoper.testf = 0;
		gyoper.gsmf  = gsm_flag;
		gyoper.zjgid = zjg_id;
		ComntFunc.string2Byte(user_name, gyoper.operman);

		for(i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++){
			gyoper.bd[i].rtu_id = CommBase.strtoi(json.getString("rtu_id"));
			gyoper.bd[i].mp_id  = (short)CommBase.strtoi(json.getString("mp_id" + i));
			gyoper.bd[i].date   = CommBase.strtoi(json.getString("date"));
			gyoper.bd[i].time   = CommBase.strtoi(json.getString("time"));
			
			gyoper.bd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy"  + i));
			gyoper.bd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj" + i));
			gyoper.bd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf" + i));
			gyoper.bd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp" + i));
			gyoper.bd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg" + i));
			
			gyoper.bd[i].bd_fy  = CommBase.strtof(json.getString("bd_fy"  + i));
			gyoper.bd[i].bd_fyj = CommBase.strtof(json.getString("bd_fyj" + i));
			gyoper.bd[i].bd_fyf = CommBase.strtof(json.getString("bd_fyf" + i));
			gyoper.bd[i].bd_fyp = CommBase.strtof(json.getString("bd_fyp" + i));
			gyoper.bd[i].bd_fyg = CommBase.strtof(json.getString("bd_fyg" + i));
			
			gyoper.bd[i].bd_zw	= CommBase.strtof(json.getString("bd_zw"  + i));
			gyoper.bd[i].bd_fw	= CommBase.strtof(json.getString("bd_fw"  + i));
		}
		
		ret_val = ComntOperGyFunc.GYReCalc(user_name, i_user_data1, i_user_data2, rtu_para, zjg_id, gyoper, err_str_1, task_result_detail);
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}
	
	//高压操作-结算补差
	private String GYReJs(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception {

		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;	
		
		boolean ret_val = false;
		
		if (gyOperRejs != null) gyOperRejs = gyOperRejs.trim();
		if (gyOperRejs == null || gyOperRejs.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}

		JSONObject json = JSONObject.fromObject(gyOperRejs);
		
		//20131019将ComntMsg修应该为ComntMsgGy
		ComntMsgGy.MSG_GYOPER_REJS gyoper = new ComntMsgGy.MSG_GYOPER_REJS();
		
		gyoper.testf = 0;
		gyoper.gsmf  = gsm_flag;
		gyoper.zjgid = zjg_id;
		
		gyoper.paytype  	= (byte)CommBase.strtoi(json.getString("paytype"));
		gyoper.opresult 	= op_result;
		gyoper.buynum 		= CommBase.strtoi(json.getString("buynum")) + 1;
		
		gyoper.alarm_val1 = json.getDouble("newala_val1");
		gyoper.alarm_val2 = json.getDouble("newala_val2");
		
		ComntFunc.string2Byte(user_name, gyoper.operman);

		gyoper.money.pay_money  = CommBase.strtof(json.getString("pay_money"));
		gyoper.money.othjs_money = CommBase.strtof(json.getString("othjs_money"));	//应结算金额
		gyoper.money.zb_money	= CommBase.strtof(json.getString("zb_money"));
		gyoper.money.all_money  = gyoper.money.pay_money + gyoper.money.othjs_money + gyoper.money.zb_money;
		
		for(i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++){
			gyoper.bd[i].rtu_id= CommBase.strtoi(json.getString("rtu_id"));
			gyoper.bd[i].mp_id = (short)CommBase.strtoi(json.getString("mp_id" + i));
			gyoper.bd[i].date  = CommBase.strtoi(json.getString("date"));
			gyoper.bd[i].time  = CommBase.strtoi(json.getString("time"));

			gyoper.bd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy"  + i));
			gyoper.bd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj" + i));
			gyoper.bd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf" + i));
			gyoper.bd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp" + i));
			gyoper.bd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg" + i));
			
			gyoper.bd[i].bd_fy  = CommBase.strtof(json.getString("bd_fy"  + i));
			gyoper.bd[i].bd_fyj = CommBase.strtof(json.getString("bd_fyj" + i));
			gyoper.bd[i].bd_fyf = CommBase.strtof(json.getString("bd_fyf" + i));
			gyoper.bd[i].bd_fyp = CommBase.strtof(json.getString("bd_fyp" + i));
			gyoper.bd[i].bd_fyg = CommBase.strtof(json.getString("bd_fyg" + i));
			
			gyoper.bd[i].bd_zw	= CommBase.strtof(json.getString("bd_zw"  + i));
			gyoper.bd[i].bd_fw	= CommBase.strtof(json.getString("bd_fw"  + i));				
		}
		
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		ret_val = ComntOperGyFunc.GYReJs(user_name, i_user_data1, i_user_data2, rtu_para, zjg_id, gyoper, operidx, err_str_1, task_result_detail);
		
		if (ret_val == false) {
			return SDDef.FAIL;
		}
		
		//结算存库
		JSPara.ZJSRecordPara para = new JSPara.ZJSRecordPara();
		
		para.rtu_id = json.getInt("rtu_id");
		para.zjg_id = (short)json.getInt("zjg_id");
		para.fxdf_ym = json.getInt("fxdf_ym");
		
		Date date = new Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd-HHmmss", Locale.SIMPLIFIED_CHINESE);
		String date_time[] = sdf.format(date).split("-");
		para.op_date = CommBase.strtoi(date_time[0]);
		para.op_time = CommBase.strtoi(date_time[1]);
		para.cons_id = (short)json.getInt("cons_id");
		para.cons_desc = json.getString("cons_desc");
		para.op_man = CommFunc.getYffMan().getName();
		para.pay_type =  (byte)json.getInt("pay_type");
		
		para.wasteno = ComntFunc.byte2String(operidx.operidx.wasteno);
		para.pay_money = json.getDouble("pay_money");
		para.othjs_money = gyoper.money.othjs_money;
		para.zb_money = json.getDouble("zb_money");
		para.all_money = gyoper.money.all_money;
		para.buy_dl = json.getDouble("buy_dl");
		para.pay_bmc = json.getDouble("pay_bmc");
		para.buy_times = json.getInt("buynum") + 1;
		
		if(json.getInt("jsflag") == 1) {
			para.misjs_money = json.getDouble("misjs_money");
			para.misjs_bmc = json.getDouble("misjs_bmc");
		} else {
			para.misjs_money = json.getDouble("othjs_money");
			para.misjs_bmc = para.pay_bmc;
		}
		
		para.cur_bd = json.getDouble("cur_bd");
		para.lastala_val1 = json.getDouble("lastala_val1");
		para.lastala_val2 = json.getDouble("lastala_val2");
		para.lastshut_val = json.getDouble("lastshut_val");
		para.newala_val1 = json.getDouble("newala_val1");
		para.newala_val2 = json.getDouble("newala_val2");
		para.newshut_val = json.getDouble("newshut_val");
		
		if(!DBOper.insertZJs(para)) {
			err_str_1.append("存库失败!");
			return SDDef.FAIL;
		}
		
		return SDDef.SUCCESS;
	}
	
	//高压操作-返回余额
	private String GYGetRemain(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;		
		
		boolean ret_val = false;
		
		//发送
		if (gyOperGetRemain != null) gyOperGetRemain = gyOperGetRemain.trim();
		if (gyOperGetRemain == null || gyOperGetRemain.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}

		JSONObject json = JSONObject.fromObject(gyOperGetRemain);
		
		//20131019将ComntMsg修应该为ComntMsgGy
		ComntMsgGy.MSG_GYOPER_GETREMAIN gyoper = new ComntMsgGy.MSG_GYOPER_GETREMAIN();
		
		gyoper.testf = 0;
		gyoper.gsmf  = gsm_flag;
		gyoper.zjgid = zjg_id;
		ComntFunc.string2Byte(user_name, gyoper.operman);

		for(i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++){
			gyoper.bd[i].rtu_id= CommBase.strtoi(json.getString("rtu_id"));
			gyoper.bd[i].mp_id = (short)CommBase.strtoi(json.getString("mp_id"+ i));
			gyoper.bd[i].date  = CommBase.strtoi(json.getString("date"));
			gyoper.bd[i].time  = CommBase.strtoi(json.getString("time"));
			
			gyoper.bd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy"+ i));
			gyoper.bd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj"+ i));
			gyoper.bd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf"+ i));
			gyoper.bd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp"+ i));
			gyoper.bd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg"+ i));
			
			gyoper.bd[i].bd_fy  = CommBase.strtof(json.getString("bd_fy"  + i));
			gyoper.bd[i].bd_fyj = CommBase.strtof(json.getString("bd_fyj" + i));
			gyoper.bd[i].bd_fyf = CommBase.strtof(json.getString("bd_fyf" + i));
			gyoper.bd[i].bd_fyp = CommBase.strtof(json.getString("bd_fyp" + i));
			gyoper.bd[i].bd_fyg = CommBase.strtof(json.getString("bd_fyg" + i));
			
			gyoper.bd[i].bd_zw	= CommBase.strtof(json.getString("bd_zw"  + i));
			gyoper.bd[i].bd_fw	= CommBase.strtof(json.getString("bd_fw"  + i));				
		}

		ComntProtMsg.YFF_DATA_OPER_GETREMAIN  getremain_result = new ComntProtMsg.YFF_DATA_OPER_GETREMAIN();		
		ret_val = ComntOperGyFunc.GYGetRemain(user_name, i_user_data1, i_user_data2, rtu_para, 
				  									zjg_id, gyoper, getremain_result, err_str_1, task_result_detail);
		
		gyOperGetRemain = SDDef.EMPTY;
		
		if (ret_val) {
			gyOperGetRemain = getremain_result.toJsonString();
			return SDDef.SUCCESS;
		}
		else {
			return SDDef.FAIL;
		}
	}	
	
	
	//高压操作-结算补差返回余额
	private String GYGetJSBCRemain(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;		
		
		boolean ret_val = false;
		
		//发送
		if (gyOperGetJSBCRemain != null) gyOperGetJSBCRemain = gyOperGetJSBCRemain.trim();
		if (gyOperGetJSBCRemain == null || gyOperGetJSBCRemain.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}

		JSONObject json = JSONObject.fromObject(gyOperGetJSBCRemain);
		
		//20131019将ComntMsg修应该为ComntMsgGy
		ComntMsgGy.MSG_GYOPER_GETJSBCREMAIN gyoper = new ComntMsgGy.MSG_GYOPER_GETJSBCREMAIN();
		
		gyoper.testf = 0;
		gyoper.gsmf  = gsm_flag;
		gyoper.zjgid = zjg_id;
		ComntFunc.string2Byte(user_name, gyoper.operman);

		for(i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++){
			gyoper.bd[i].rtu_id= CommBase.strtoi(json.getString("rtu_id"));
			gyoper.bd[i].mp_id = (short)CommBase.strtoi(json.getString("mp_id"+ i));
			gyoper.bd[i].date  = CommBase.strtoi(json.getString("date"));
			gyoper.bd[i].time  = CommBase.strtoi(json.getString("time"));
			
			gyoper.bd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy"+ i));
			gyoper.bd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj"+ i));
			gyoper.bd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf"+ i));
			gyoper.bd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp"+ i));
			gyoper.bd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg"+ i));
			
			gyoper.bd[i].bd_fy  = CommBase.strtof(json.getString("bd_fy"  + i));
			gyoper.bd[i].bd_fyj = CommBase.strtof(json.getString("bd_fyj" + i));
			gyoper.bd[i].bd_fyf = CommBase.strtof(json.getString("bd_fyf" + i));
			gyoper.bd[i].bd_fyp = CommBase.strtof(json.getString("bd_fyp" + i));
			gyoper.bd[i].bd_fyg = CommBase.strtof(json.getString("bd_fyg" + i));
			
			gyoper.bd[i].bd_zw	= CommBase.strtof(json.getString("bd_zw"  + i));
			gyoper.bd[i].bd_fw	= CommBase.strtof(json.getString("bd_fw"  + i));				
		}

		ComntProtMsg.YFF_DATA_OPER_GETJSBCREMAIN  getremain_result = new ComntProtMsg.YFF_DATA_OPER_GETJSBCREMAIN();		
		ret_val = ComntOperGyFunc.GYGetJSBCRemain(user_name, i_user_data1, i_user_data2, rtu_para, 
				  									zjg_id, gyoper, getremain_result, err_str_1, task_result_detail);
		
		gyOperGetJSBCRemain = SDDef.EMPTY;
		
		if (ret_val) {
			gyOperGetJSBCRemain = getremain_result.toJsonString();
			return SDDef.SUCCESS;
		}
		else {
			return SDDef.FAIL;
		}
	}
	
	//高压操作-换基本费
	private String GYChgPayAdd(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception {

		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;
		
		boolean ret_val = false;
		
		if (gyOperChanePayAdd != null) gyOperChanePayAdd = gyOperChanePayAdd.trim();
		if (gyOperChanePayAdd == null || gyOperChanePayAdd.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(gyOperChanePayAdd);
		
		//20131019将ComntMsg修应该为ComntMsgGy
		ComntMsgGy.MSG_GYOPER_CHANGPAYADD gyoper = new ComntMsgGy.MSG_GYOPER_CHANGPAYADD();
		
		gyoper.testf = 0;
		gyoper.gsmf  = gsm_flag;
		gyoper.zjgid = zjg_id;
		ComntFunc.string2Byte(user_name, gyoper.operman);
		gyoper.opresult 	= op_result;
	
		gyoper.chg_type 	= 1;
		gyoper.jbf_chgval 	= CommBase.strtof(json.getString("jbf_chgval"));

		gyoper.fee_chgdate 	= CommBase.strtoi(json.getString("chg_date"));
		gyoper.fee_chgtime 	= CommBase.strtoi(json.getString("chg_time"));

		for(i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++){
			gyoper.bd[i].rtu_id= CommBase.strtoi(json.getString("rtu_id"));
			gyoper.bd[i].mp_id = (short)CommBase.strtoi(json.getString("mp_id" + i));
			gyoper.bd[i].date  = CommBase.strtoi(json.getString("date"));
			gyoper.bd[i].time  = CommBase.strtoi(json.getString("time"));

			gyoper.bd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy"  + i));
			gyoper.bd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj" + i));
			gyoper.bd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf" + i));
			gyoper.bd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp" + i));
			gyoper.bd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg" + i));
			
			gyoper.bd[i].bd_fy  = CommBase.strtof(json.getString("bd_fy"  + i));
			gyoper.bd[i].bd_fyj = CommBase.strtof(json.getString("bd_fyj" + i));
			gyoper.bd[i].bd_fyf = CommBase.strtof(json.getString("bd_fyf" + i));
			gyoper.bd[i].bd_fyp = CommBase.strtof(json.getString("bd_fyp" + i));
			gyoper.bd[i].bd_fyg = CommBase.strtof(json.getString("bd_fyg" + i));
			
			gyoper.bd[i].bd_zw	= CommBase.strtof(json.getString("bd_zw"  + i));
			gyoper.bd[i].bd_fw	= CommBase.strtof(json.getString("bd_fw"  + i));				
		}
		
		gyOperChanePayAdd = SDDef.EMPTY;
		
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
		ret_val = ComntOperGyFunc.GYChgPayAdd(user_name, i_user_data1, i_user_data2, rtu_para, zjg_id, gyoper, operidx, err_str_1, task_result_detail);
		
		if(ret_val){
			gyOperChanePayAdd = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
		return SDDef.FAIL;
	}	
	
	//高压操作-更改参数  预付费参数
	private String GYResetDoc(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception {

		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;	
		
		boolean ret_val = false;
		
		if (gyOperResetDoc != null) gyOperResetDoc = gyOperResetDoc.trim();
		if (gyOperResetDoc == null || gyOperResetDoc.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}

		JSONObject json = JSONObject.fromObject(gyOperResetDoc);
		
		//20131019将ComntMsg修应该为ComntMsgGy
		ComntMsgGy.MSG_GYOPER_RESETDOC gyoper = new ComntMsgGy.MSG_GYOPER_RESETDOC();
		
		gyoper.testf = 0;
		gyoper.gsmf  = gsm_flag;
		gyoper.zjgid = zjg_id;
	
		gyoper.chgtype  	= (byte)CommBase.strtoi(json.getString("chgtype"));
		ComntFunc.string2Byte(user_name, gyoper.operman);
		
		ComntFunc.string2Byte(json.getString("filed1"), gyoper.filed1);
		ComntFunc.string2Byte(json.getString("filed2"), gyoper.filed2);
		ComntFunc.string2Byte(json.getString("filed3"), gyoper.filed3);
		ComntFunc.string2Byte(json.getString("filed4"), gyoper.filed4);
		
		ret_val = ComntOperGyFunc.GYResetDoc(user_name, i_user_data1, i_user_data2, rtu_para, zjg_id, gyoper, err_str_1, task_result_detail);
		
		if (ret_val == false) {
			return SDDef.FAIL;
		}		
		
		return SDDef.SUCCESS;
	}
	
	//高压操作-获得预付费参数及状态
	private String GYGetPayParaState(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, byte gsm_flag,ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
       
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		boolean ret_val = false;
		
		gyOperGParaState = SDDef.EMPTY;
		
		ComntProtMsg.YFF_DATA_GYOPER_PARASTATE gyoper_parastate = new ComntProtMsg.YFF_DATA_GYOPER_PARASTATE();
		
		ret_val =ComntOperGyFunc.GYGetPayParaState(user_name, i_user_data1, i_user_data2, rtu_para, zjg_id, gyoper_parastate, err_str_1, task_result_detail);
		
		if (ret_val) {
			gyOperGParaState = gyoper_parastate.toJsonString();
			return SDDef.SUCCESS;
		}
		else {
			return SDDef.FAIL;
		}
	}	
	
	//高压操作-恢复
	private String GYRestart(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception {

		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;		
		
		boolean ret_val = false;
		
		if (gyOperReStart != null) gyOperReStart = gyOperReStart.trim();
		if (gyOperReStart == null || gyOperReStart.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}	
		
		JSONObject json = JSONObject.fromObject(gyOperReStart);
		
		//20131019将ComntMsg修应该为ComntMsgGy
		ComntMsgGy.MSG_GYOPER_RESTART gyoper = new ComntMsgGy.MSG_GYOPER_RESTART();
		
		gyoper.testf = 0;
		gyoper.gsmf  = gsm_flag;
		gyoper.zjgid = zjg_id;
		ComntFunc.string2Byte(user_name, gyoper.operman);
		gyoper.opresult = op_result;
		
		gyoper.myffalarmid  = (short)CommBase.strtoi(json.getString("myffalarmid"));
		gyoper.paytype  	= (byte)CommBase.strtoi(json.getString("paytype"));
		gyoper.buynum 		= CommBase.strtoi(json.getString("buynum"));
		
		gyoper.alarm_val1 	= CommBase.strtof(json.getString("alarm_val1"));
		gyoper.alarm_val2 	= CommBase.strtof(json.getString("alarm_val2"));

		gyoper.bz.buy_dl		= CommBase.strtof(json.getString("buy_dl"));
		gyoper.bz.pay_bmc		= CommBase.strtof(json.getString("pay_bmc"));
		gyoper.bz.shutdown_bd	= CommBase.strtof(json.getString("shutdown_bd"));

		gyoper.money.pay_money	= CommBase.strtof(json.getString("pay_money"));
		gyoper.money.othjs_money= CommBase.strtof(json.getString("othjs_money"));
		gyoper.money.zb_money	= CommBase.strtof(json.getString("zb_money"));
		gyoper.money.all_money	= CommBase.strtof(json.getString("all_money"));

		for(i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++){
			gyoper.bd[i].rtu_id= CommBase.strtoi(json.getString("rtu_id"));
			gyoper.bd[i].mp_id = (short)CommBase.strtoi(json.getString("mp_id" + i));
			gyoper.bd[i].date  = CommBase.strtoi(json.getString("date"));
			gyoper.bd[i].time  = CommBase.strtoi(json.getString("time"));
			
			gyoper.bd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy"  + i));
			gyoper.bd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj" + i));
			gyoper.bd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf" + i));
			gyoper.bd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp" + i));
			gyoper.bd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg" + i));
			
			gyoper.bd[i].bd_fy  = CommBase.strtof(json.getString("bd_fy"  + i));
			gyoper.bd[i].bd_fyj = CommBase.strtof(json.getString("bd_fyj" + i));
			gyoper.bd[i].bd_fyf = CommBase.strtof(json.getString("bd_fyf" + i));
			gyoper.bd[i].bd_fyp = CommBase.strtof(json.getString("bd_fyp" + i));
			gyoper.bd[i].bd_fyg = CommBase.strtof(json.getString("bd_fyg" + i));
			
			gyoper.bd[i].bd_zw	= CommBase.strtof(json.getString("bd_zw"  + i));
			gyoper.bd[i].bd_fw	= CommBase.strtof(json.getString("bd_fw"  + i));				
		}
		
		//sjh 201208013
		gyoper.total_yzbdl = CommBase.strtof(json.getString("total_yzbdl"));
		gyoper.total_wzbdl = CommBase.strtof(json.getString("total_wzbdl"));			
		
		gyOperReStart = SDDef.EMPTY;
		
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
		ret_val = ComntOperGyFunc.GYRestart(user_name, i_user_data1, i_user_data2, rtu_para, zjg_id, gyoper, operidx, err_str_1, task_result_detail);
		
		if(ret_val){
			gyOperReStart = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
		return SDDef.FAIL;
	}
	
	//高压操作-暂停
	private String GYPause(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, byte gsm_flag,/* boolean first_flag, */ ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception {
       
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;
		
		boolean ret_val = false;
		
		if (gyOperPause != null) gyOperPause = gyOperPause.trim();
		if (gyOperPause == null || gyOperPause.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(gyOperPause);
		
		//20131019将ComntMsg修应该为ComntMsgGy
		ComntMsgGy.MSG_GYOPER_PAUSE gyoper = new ComntMsgGy.MSG_GYOPER_PAUSE();
		
		gyoper.testf = 0;
		gyoper.gsmf  = gsm_flag;
		gyoper.zjgid = zjg_id;
		ComntFunc.string2Byte(user_name, gyoper.operman);
		gyoper.opresult = op_result;

		gyoper.bz.buy_dl		= CommBase.strtof(json.getString("buy_dl"));
		gyoper.bz.pay_bmc		= CommBase.strtof(json.getString("pay_bmc"));
		gyoper.bz.shutdown_bd	= CommBase.strtof(json.getString("shutdown_bd"));

		gyoper.money.pay_money	= CommBase.strtof(json.getString("pay_money"));
		gyoper.money.othjs_money= CommBase.strtof(json.getString("othjs_money"));
		gyoper.money.zb_money	= CommBase.strtof(json.getString("zb_money"));
		gyoper.money.all_money	= CommBase.strtof(json.getString("all_money"));

		for(i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++){
			gyoper.bd[i].rtu_id= CommBase.strtoi(json.getString("rtu_id"));
			gyoper.bd[i].mp_id = (short)CommBase.strtoi(json.getString("mp_id" + i));
			gyoper.bd[i].date  = CommBase.strtoi(json.getString("date"));
			gyoper.bd[i].time  = CommBase.strtoi(json.getString("time"));
			
			gyoper.bd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy"  + i));
			gyoper.bd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj" + i));
			gyoper.bd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf" + i));
			gyoper.bd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp" + i));
			gyoper.bd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg" + i));
			
			gyoper.bd[i].bd_fy  = CommBase.strtof(json.getString("bd_fy"  + i));
			gyoper.bd[i].bd_fyj = CommBase.strtof(json.getString("bd_fyj" + i));
			gyoper.bd[i].bd_fyf = CommBase.strtof(json.getString("bd_fyf" + i));
			gyoper.bd[i].bd_fyp = CommBase.strtof(json.getString("bd_fyp" + i));
			gyoper.bd[i].bd_fyg = CommBase.strtof(json.getString("bd_fyg" + i));
			
			gyoper.bd[i].bd_zw	= CommBase.strtof(json.getString("bd_zw"  + i));
			gyoper.bd[i].bd_fw	= CommBase.strtof(json.getString("bd_fw"  + i));				
		}
		
		gyOperPause = SDDef.EMPTY;
		
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
        ret_val = ComntOperGyFunc.GYPause(user_name, i_user_data1, i_user_data2, rtu_para, zjg_id, gyoper, operidx, err_str_1, task_result_detail);
        
		if (ret_val) {
			gyOperPause = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
		else {
			return SDDef.FAIL;
		}
	}
	
	//高压操作-销户
	private String GYDestory(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception {

		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID	
		
		boolean ret_val = false;
		
		if (gyOperDestory != null) gyOperDestory = gyOperDestory.trim();
		if (gyOperDestory == null || gyOperDestory.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(gyOperDestory);
		
		//20131019将ComntMsg修应该为ComntMsgGy
		ComntMsgGy.MSG_GYOPER_DESTORY gyoper = new ComntMsgGy.MSG_GYOPER_DESTORY();
		
		gyoper.testf = 0;
		gyoper.gsmf  = gsm_flag;
		gyoper.zjgid = zjg_id;
		ComntFunc.string2Byte(user_name, gyoper.operman);
		gyoper.opresult = op_result;
		/*
		gyoper.bz.buy_dl		= CommBase.strtof(json.getString("buy_dl"));
		gyoper.bz.pay_bmc		= CommBase.strtof(json.getString("pay_bmc"));
		gyoper.bz.shutdown_bd	= CommBase.strtof(json.getString("shutdown_bd"));
		*/
		gyoper.money.pay_money  = CommBase.strtof(json.getString("pay_money"));
		gyoper.money.othjs_money= CommBase.strtof(json.getString("othjs_money"));
		gyoper.money.zb_money	= CommBase.strtof(json.getString("zb_money"));
		gyoper.money.all_money  = CommBase.strtof(json.getString("all_money"));
		
		
		for(int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++){
			gyoper.bd[i].rtu_id	= CommBase.strtoi(json.getString("rtu_id"));
			gyoper.bd[i].mp_id 	= (short)CommBase.strtoi(json.getString("mp_id" + i));
			gyoper.bd[i].date  	= CommBase.strtoi(json.getString("date"));
			gyoper.bd[i].time  	= CommBase.strtoi(json.getString("time"));
			
			gyoper.bd[i].bd_zy  = CommBase.strtof(json.getString("bd_zy"  + i));
			gyoper.bd[i].bd_zyj = CommBase.strtof(json.getString("bd_zyj" + i));
			gyoper.bd[i].bd_zyf = CommBase.strtof(json.getString("bd_zyf" + i));
			gyoper.bd[i].bd_zyp = CommBase.strtof(json.getString("bd_zyp" + i));
			gyoper.bd[i].bd_zyg = CommBase.strtof(json.getString("bd_zyg" + i));
			
			gyoper.bd[i].bd_fy  = CommBase.strtof(json.getString("bd_fy"  + i));
			gyoper.bd[i].bd_fyj = CommBase.strtof(json.getString("bd_fyj" + i));
			gyoper.bd[i].bd_fyf = CommBase.strtof(json.getString("bd_fyf" + i));
			gyoper.bd[i].bd_fyp = CommBase.strtof(json.getString("bd_fyp" + i));
			gyoper.bd[i].bd_fyg = CommBase.strtof(json.getString("bd_fyg" + i));
			
			gyoper.bd[i].bd_zw	= CommBase.strtof(json.getString("bd_zw"  + i));
			gyoper.bd[i].bd_fw	= CommBase.strtof(json.getString("bd_fw"  + i));				
			
		}
		
		gyOperDestory = SDDef.EMPTY;
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		ret_val = ComntOperGyFunc.GYDestory(user_name, i_user_data1, i_user_data2, rtu_para, zjg_id, gyoper, operidx, err_str_1, task_result_detail);
				
		if (ret_val) {
			gyOperDestory = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
		else {
			return SDDef.FAIL;
		}
	}
	
	
	public String taskProc(){
		ComntParaBase.OpDetailInfo op_detail = new ComntParaBase.OpDetailInfo();
		StringBuffer err_str_1 				 = new StringBuffer();
		ComntParaBase.SALEMAN_TASK_RESULT_DETAIL 	task_result_detail = new ComntParaBase.SALEMAN_TASK_RESULT_DETAIL();
		int rtu_id    = CommBase.strtoi(userData1);
		int userop_id = CommBase.strtoi(userData2); 
		short zjg_id  = (short)CommBase.strtoi(zjgid);
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
			
			if (userop_id == ComntUseropDef.YFF_GYOPER_ADDCUS) {			//高压操作-开户
				ret_val = GYAddRes(user_name, rtu_para, zjg_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_PAY) {			//高压操作-缴费
				ret_val = GYPay(user_name, rtu_para, zjg_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_REPAIR) {			//高压操作-缴费
				ret_val = GYRepair(user_name, rtu_para, zjg_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_REVER) {		//高压操作-冲正
				ret_val = GYRever(user_name, rtu_para, zjg_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_CHANGEMETER) {	//高压操作-换表换倍率
				ret_val = GYChgMeter(user_name, rtu_para, zjg_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_CHANGERATE) {	//高压操作-换电价
				ret_val = GYChgRate(user_name, rtu_para, zjg_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_PROTECT) {		//高压操作-保电
				ret_val = GYProtect(user_name, rtu_para, zjg_id, gsm_flag, op_detail, err_str_1, task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_UDPATESTATE) {	//高压操作-强制状态更新
				ret_val = GySetUpdate(user_name, rtu_para, zjg_id, gsm_flag, op_detail, err_str_1, task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_RECALC) {		//高压操作-重新计算剩余金额
				ret_val = GYReCalc(user_name, rtu_para, zjg_id, gsm_flag, op_detail, err_str_1, task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_REJS) {			//高压操作-重新结算
				ret_val = GYReJs(user_name, rtu_para, zjg_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_GETREMAIN) {	//高压操作-返回余额
				ret_val = GYGetRemain(user_name, rtu_para, zjg_id, gsm_flag, op_detail, err_str_1, task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_GETJSBCREMAIN) {	//高压操作-结算补差返回余额
				ret_val = GYGetJSBCRemain(user_name, rtu_para, zjg_id, gsm_flag, op_detail, err_str_1, task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_CHANGPAYADD) {	//高压操作-换基本费
				ret_val = GYChgPayAdd(user_name, rtu_para, zjg_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_GPARASTATE) {	//高压操作-获得预付费参数及状态
				ret_val = GYGetPayParaState(user_name, rtu_para, zjg_id, gsm_flag, op_detail, err_str_1, task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_RESTART) {		//高压操作-恢复
				ret_val = GYRestart(user_name, rtu_para, zjg_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_PAUSE) {		//高压操作-暂停
				ret_val = GYPause(user_name, rtu_para, zjg_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_DESTORY) {		//高压操作-销户
				ret_val = GYDestory(user_name, rtu_para, zjg_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_RESETDOC) {		//高压操作-更改参数  预付费参数
				ret_val = GYResetDoc(user_name, rtu_para, zjg_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			
			else {
				op_detail.addTaskInfo("ERR:未知的操作类型[" + userop_id + "]");
			}
			result 			= ret_val;
			err_strinfo		= err_str_1.toString();
	//		task_resultinfo = task_result_detail;	//以后再详细描述
			detailInfo = op_detail.toJsonString();
			operErr			= ComntParaBase.makeOperErrCode(task_result_detail);
		}catch (Exception e) {
			
			CommFunc.err_log(e);
			
			result = SDDef.FAIL;
			err_strinfo = e.getMessage();
		}
		
		return SDDef.SUCCESS;
	}
	
	public String cancelTask() {
		String user_name = ComntParaBase.getUserName();	
		
		int i_user_data1 = CommBase.strtoi(userData1);
		int i_user_data2 = CommBase.strtoi(userData2);

		ComntMsgProc.cancelMsg(user_name, i_user_data1, i_user_data2);
		
		result = SDDef.SUCCESS;	
		return SDDef.SUCCESS;
	}
	
	public String dbCtrlOper(){			//控制信息入库
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
			String zjg_id = json.getString("zjg_id");
			String op_type = json.getString("op_type");
			sql = "select cons_id from rtupara where id="+rtu_id;
			int cons_id = -1;
			rs = j_dao.executeQuery(sql);
			if(rs.next()){
				cons_id = rs.getInt(1);
			}
			j_dao.closeRs(rs);

			sql = "insert into yddataben.dbo.zfk"+day.substring(0,4)+"(rtu_id,zjg_id,cons_id,op_date,op_time,op_man,op_type,shuttime,op_result) " +
					"values("+rtu_id+","+zjg_id+","+cons_id+","+day+","+time+",'"+CommFunc.getYffMan().getName()+"',"+op_type+",0,0)";
			
			if(j_dao.executeUpdate(sql)){
				result = SDDef.SUCCESS;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
	public String getGyOperAddCus() {
		return gyOperAddCus;
	}
	public void setGyOperAddCus(String gyOperAddCus) {
		this.gyOperAddCus = gyOperAddCus;
	}
	public String getGyOperPay() {
		return gyOperPay;
	}
	public void setGyOperPay(String gyOperPay) {
		this.gyOperPay = gyOperPay;
	}
	public String getGyOperRever() {
		return gyOperRever;
	}
	public void setGyOperRever(String gyOperRever) {
		this.gyOperRever = gyOperRever;
	}
	public String getGyOperChangeMeter() {
		return gyOperChangeMeter;
	}
	public void setGyOperChangeMeter(String gyOperChangeMeter) {
		this.gyOperChangeMeter = gyOperChangeMeter;
	}
	public String getGyOperChangeRate() {
		return gyOperChangeRate;
	}
	public void setGyOperChangeRate(String gyOperChangeRate) {
		this.gyOperChangeRate = gyOperChangeRate;
	}
	public String getGyOperProtect() {
		return gyOperProtect;
	}
	public void setGyOperProtect(String gyOperProtect) {
		this.gyOperProtect = gyOperProtect;
	}
	public String getGyOperUpdateState() {
		return gyOperUpdateState;
	}
	public void setGyOperUpdateState(String gyOperUpdateState) {
		this.gyOperUpdateState = gyOperUpdateState;
	}
	public String getGyOperRecalc() {
		return gyOperRecalc;
	}
	public void setGyOperRecalc(String gyOperRecalc) {
		this.gyOperRecalc = gyOperRecalc;
	}
	public String getGyOperRejs() {
		return gyOperRejs;
	}
	public void setGyOperRejs(String gyOperRejs) {
		this.gyOperRejs = gyOperRejs;
	}
	public String getGyOperGetRemain() {
		return gyOperGetRemain;
	}
	public void setGyOperGetRemain(String gyOperGetRemain) {
		this.gyOperGetRemain = gyOperGetRemain;
	}
	
	public String getGyOperGetJSBCRemain() {
		return gyOperGetJSBCRemain;
	}

	public void setGyOperGetJSBCRemain(String gyOperGetJSBCRemain) {
		this.gyOperGetJSBCRemain = gyOperGetJSBCRemain;
	}

	public String getGyOperChanePayAdd() {
		return gyOperChanePayAdd;
	}
	public void setGyOperChanePayAdd(String gyOperChanePayAdd) {
		this.gyOperChanePayAdd = gyOperChanePayAdd;
	}
	public String getGyOperGParaState() {
		return gyOperGParaState;
	}
	public void setGyOperGParaState(String gyOperGParaState) {
		this.gyOperGParaState = gyOperGParaState;
	}
	public String getGyOperReStart() {
		return gyOperReStart;
	}
	public void setGyOperReStart(String gyOperReStart) {
		this.gyOperReStart = gyOperReStart;
	}
	public String getGyOperPause() {
		return gyOperPause;
	}
	public void setGyOperPause(String gyOperPause) {
		this.gyOperPause = gyOperPause;
	}
	public String getGyOperDestory() {
		return gyOperDestory;
	}
	public void setGyOperDestory(String gyOperDestory) {
		this.gyOperDestory = gyOperDestory;
	}
	public String getGsmflag() {
		return gsmflag;
	}
	public void setGsmflag(String gsmflag) {
		this.gsmflag = gsmflag;
	}
	public String getZjgid() {
		return zjgid;
	}
	public void setZjgid(String zjgid) {
		this.zjgid = zjgid;
	}

	public String getGyOperRepair() {
		return gyOperRepair;
	}

	public void setGyOperRepair(String gyOperRepair) {
		this.gyOperRepair = gyOperRepair;
	}

	public String getGyOperResetDoc() {
		return gyOperResetDoc;
	}

	public void setGyOperResetDoc(String gyOperResetDoc) {
		this.gyOperResetDoc = gyOperResetDoc;
	}

	public String getOperErr() {
		return operErr;
	}

	public void setOperErr(String operErr) {
		this.operErr = operErr;
	}

	
}
