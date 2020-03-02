package com.kesd.action.oper;
import net.sf.json.JSONObject;

import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.comnt.ComntMsg;
import com.kesd.comnt.ComntMsgNp;
import com.kesd.comnt.ComntMsgProc;
import com.kesd.comnt.ComntProtMsg;
import com.kesd.comntpara.ComntOperNpFunc;
import com.kesd.comntpara.ComntParaBase;
import com.kesd.comntpara.ComntUseropDef;
import com.libweb.common.CommBase;
import com.libweb.comnt.ComntFunc;
import com.libweb.dao.HibDao;

public class ActOperNp {
	
	private static final long serialVersionUID = 3607391502741009698L;
	private String  userData1 			= null;		//AREA_ID
	private String  userData2 			= null;		//USEROP_ID
	private String 	result 				= null;		//SUCCESS/FAIL
	private String 	detailInfo 			= null;		//返回详细信息(JSON)
	private String	err_strinfo			= null;		//返回错误信息
	private String	task_resultinfo		= null;		//返回任务详细信息
	private String 	gsmflag				= null;		//是否发送短信
	private String  farmerid			= null;		//客户ID	

	private String	npOperAddres		= null;		//农排操作-开户
	private String	npOperPay			= null;		//农排操作-缴费
	private String	npOperRepair		= null;		//农排操作-补卡
	private String	npOperRewrite		= null;		//农排操作-补写卡
	private String	npOperRever			= null;		//农排操作-冲正
	private String	npOperDestory		= null;		//农排操作-销户
	private String	npOperUpdateState	= null;		//农排操作-强制状态更新
	private String	npOperGParaState	= null;		//农排操作-获得预付费参数及状态
	private String	npOperResetDoc		= null;		//农排操作-更改用户参数
	
	private String operErr				= null;		//返回错误信息描述,在js弹出框中显示
	
	/**
	 * zhp
	 * 20131128修改说明：
	 * 功能：当主站操作失败时，在操作界面弹出框中提示出错误信息的描述和代码
	 * 每个方法中增加了对task_result_detail的值的返回。
	 * 增加了参数operErr,用于向js返回信息，
	 * 主方法中使用ComntParaBase.makeOperErrCode(task_result_detail)对operErr进行显示组合;
	*/

	//农排操作-开户
	private String NPAddRes(String user_name, ComntParaBase.RTU_PARA rtu_para, short farmerid, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		//int i = 0;
		
		boolean ret_val = false;
		
		//发送
		if (npOperAddres != null) npOperAddres = npOperAddres.trim();
		if (npOperAddres == null || npOperAddres.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(npOperAddres);
		
		//20131019
		ComntMsgNp.MSG_NPOPER_ADDRES npoper = new ComntMsgNp.MSG_NPOPER_ADDRES();

		npoper.testf = 0;
		npoper.gsmf  = gsm_flag;
		npoper.farmerid  = farmerid;
		ComntFunc.string2Byte(user_name, npoper.operman);
		
		npoper.paytype  	= (byte)CommBase.strtoi(json.getString("paytype"));
		ComntFunc.string2Byte(json.getString("cardno"), npoper.cardno);
		npoper.opresult 	= op_result;
		npoper.buynum 		= 1;

		npoper.money.pay_money   = CommBase.strtof(json.getString("pay_money"));
		npoper.money.othjs_money = CommBase.strtof(json.getString("othjs_money"));
		npoper.money.zb_money	 = CommBase.strtof(json.getString("zb_money"));
		npoper.money.all_money   = CommBase.strtof(json.getString("all_money"));
				
		npOperAddres = SDDef.EMPTY;
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
		ret_val = ComntOperNpFunc.NPAddRes(user_name, i_user_data1, i_user_data2, rtu_para, farmerid, npoper, operidx, err_str_1,task_result_detail);
		if (ret_val) {
			npOperAddres = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
		else {
			return SDDef.FAIL;
		}
	}	
	
	//农排操作-缴费
	private String NPPay(String user_name, ComntParaBase.RTU_PARA rtu_para, short farmerid, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) {
		int i_user_data1 = CommBase.strtoi(userData1);	//area_id
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;
		if (npOperPay != null) npOperPay = npOperPay.trim();
		if (npOperPay == null || npOperPay.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		JSONObject json = JSONObject.fromObject(npOperPay);
		
		//20131019
		ComntMsgNp.MSG_NPOPER_PAY npoper = new ComntMsgNp.MSG_NPOPER_PAY();

		npoper.testf = 0;
		npoper.gsmf  = gsm_flag;
		npoper.farmerid  = farmerid;
		ComntFunc.string2Byte(user_name, npoper.operman);
		npoper.paytype  	= (byte)CommBase.strtoi(json.getString("paytype"));
		if(json.has("op_result")) {
			npoper.opresult = (byte)json.getInt("op_result");
		} else {
			npoper.opresult = 0;
		}
		npoper.buynum 		= CommBase.strtoi(json.getString("buynum")) + 1;
		npoper.lsremain  	= CommBase.strtof(json.getString("lsremain"));
		npoper.money.pay_money  = CommBase.strtof(json.getString("pay_money"));
		npoper.money.othjs_money= CommBase.strtof(json.getString("othjs_money"));
		npoper.money.zb_money	= CommBase.strtof(json.getString("zb_money"));
		npoper.money.all_money  = CommBase.strtof(json.getString("all_money"));
		
		npOperPay = SDDef.EMPTY;	
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
		ret_val = ComntOperNpFunc.NPPay(user_name, i_user_data1, i_user_data2, rtu_para, farmerid, npoper, operidx, err_str_1, task_result_detail);
		
		if (ret_val) {
			npOperPay = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
		else {
			return SDDef.FAIL;
		}
		
	}	
	
	//农排操作-补卡
	private String NPRepair(String user_name, ComntParaBase.RTU_PARA rtu_para, short farmerid, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;
		if (npOperRepair != null) npOperRepair = npOperRepair.trim();
		if (npOperRepair == null || npOperRepair.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(npOperRepair);	
		
		//20131019
		ComntMsgNp.MSG_NPOPER_REPAIR npoper = new ComntMsgNp.MSG_NPOPER_REPAIR();

		npoper.testf = 0;
		npoper.gsmf  = gsm_flag;
		npoper.farmerid  = farmerid;
		ComntFunc.string2Byte(user_name, npoper.operman);
		npoper.opresult 	= op_result;
		ComntFunc.string2Byte(json.getString("cardno"), npoper.cardno);
		
		npoper.old_op_date	= CommBase.strtoi(json.getString("old_op_date"));
		npoper.old_op_time	= CommBase.strtoi(json.getString("old_op_time"));
		npoper.lsremain  	= CommBase.strtof(json.getString("lsremain"));
		npoper.money.pay_money  = CommBase.strtof(json.getString("pay_money"));
		npoper.money.othjs_money= CommBase.strtof(json.getString("othjs_money"));
		npoper.money.zb_money	= CommBase.strtof(json.getString("zb_money"));
		npoper.money.all_money  = CommBase.strtof(json.getString("all_money"));
			
		npOperRepair = SDDef.EMPTY;
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
	    ret_val = ComntOperNpFunc.NPRepair(user_name, i_user_data1, i_user_data2, rtu_para, farmerid, npoper, operidx, err_str_1, task_result_detail);
		
	    if(ret_val){
	    	npOperRepair = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
	    return SDDef.FAIL;
	}
	
	//农排操作-补写卡
	private String NPReWrite(String user_name, ComntParaBase.RTU_PARA rtu_para, short farmerid, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) {
		
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;
		
		if (npOperRewrite != null) npOperRewrite = npOperRewrite.trim();
		if (npOperRewrite == null || npOperRewrite.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		JSONObject json = JSONObject.fromObject(npOperRewrite);
		
		//20131019
		ComntMsgNp.MSG_NPOPER_REWRITE npoper = new ComntMsgNp.MSG_NPOPER_REWRITE();
	
		npoper.testf = 0;
		npoper.gsmf  = gsm_flag;
		npoper.farmerid  = farmerid;
		ComntFunc.string2Byte(user_name, npoper.operman);
		npoper.opresult 	= op_result;
		npoper.old_op_date	= CommBase.strtoi(json.getString(""));
		npoper.old_op_time	= CommBase.strtoi(json.getString(""));
		
		npOperRewrite = SDDef.EMPTY;
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
		ret_val = ComntOperNpFunc.NPReWrite(user_name, i_user_data1, i_user_data2, rtu_para, farmerid, npoper, operidx, err_str_1, task_result_detail);
		
		if(ret_val){
			npOperRewrite = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
		return SDDef.FAIL;
	}
	
	//农排操作-冲正
	private String NPRever(String user_name, ComntParaBase.RTU_PARA rtu_para, short farmerid, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID	
		
		boolean ret_val = false;

		if (npOperRever != null) npOperRever = npOperRever.trim();
		if (npOperRever == null || npOperRever.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(npOperRever);
		
		//20131019
		ComntMsgNp.MSG_NPOPER_REVER npoper = new ComntMsgNp.MSG_NPOPER_REVER();
		
		npoper.testf = 0;
		npoper.gsmf  = gsm_flag;
		npoper.farmerid  = farmerid;
		
		ComntFunc.string2Byte(user_name, npoper.operman);
		npoper.paytype		= (byte)CommBase.strtoi(json.getString("paytype"));
		if(json.has("op_result")) {
			npoper.opresult = (byte)json.getInt("op_result");
		} else {
			npoper.opresult = 0;
		}
		
		npoper.oldopdate	= CommBase.strtoi(json.getString("oldopdate"));
		npoper.oldoptime	= CommBase.strtoi(json.getString("oldoptime"));
		
		npoper.newmoney.pay_money   = CommBase.strtof(json.getString("pay_money"));
		npoper.newmoney.othjs_money = CommBase.strtof(json.getString("othjs_money"));
		npoper.newmoney.zb_money	= CommBase.strtof(json.getString("zb_money"));
		npoper.newmoney.all_money   = CommBase.strtof(json.getString("all_money"));
		npoper.lsremain				= CommBase.strtof(json.getString("lsremain"));
			
		npOperRever = SDDef.EMPTY;
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
		ret_val = ComntOperNpFunc.NPRever(user_name, i_user_data1, i_user_data2, rtu_para, farmerid, npoper, operidx, err_str_1, task_result_detail);

		if(ret_val){
			npOperRever = operidx.toJsonString();
			return SDDef.SUCCESS; 
		}else{
			return SDDef.FAIL;
		}
	}

	//农排操作-强制状态更新
	private String NPSetUpdate(String user_name, ComntParaBase.RTU_PARA rtu_para, short farmerid, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID	
		
		boolean ret_val = false;
		
		if (npOperUpdateState != null) npOperUpdateState = npOperUpdateState.trim();
		if (npOperUpdateState == null || npOperUpdateState.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		//首先获取预付费参数
		ComntProtMsg.YFF_DATA_NPOPER_PARASTATE npoper_parastate = new ComntProtMsg.YFF_DATA_NPOPER_PARASTATE(); 
		ret_val = ComntOperNpFunc.NPGetPayParaState(user_name, i_user_data1, i_user_data2, rtu_para, 
				farmerid, npoper_parastate, err_str_1, task_result_detail);
		if (ret_val == false) {
			op_detail.addTaskInfo("ERR:获取预付费参数及状态错误");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(npOperUpdateState);
		
		//20131019
		ComntMsgNp.MSG_NPOPER_UPDATE npoper = new ComntMsgNp.MSG_NPOPER_UPDATE();

		npoper.testf = 0;
		npoper.gsmf  = 0;
		npoper.farmerid = farmerid;
		
		ComntFunc.string2Byte(user_name, npoper.operman);
//		npoper.paravalidf		= (byte)CommBase.strtoi(json.getString("paravalidf"));
//		
//		npoper.state_op_validf	= (byte)CommBase.strtoi(json.getString("state_op_validf"));
//		npoper.state_js_validf	= (byte)CommBase.strtoi(json.getString("state_js_validf"));
		npoper.state_bn_validf	= (byte)1;//CommBase.strtoi(json.getString("state_bn_validf")); //需要完善
//		npoper.state_cc_validf	= (byte)CommBase.strtoi(json.getString("state_cc_validf"));
//		
//		npoper.state_cs_validf	= (byte)CommBase.strtoi(json.getString("state_cs_validf"));
//		npoper.state_yc_validf	= (byte)CommBase.strtoi(json.getString("state_yc_validf"));
//		npoper.state_ot_validf	= (byte)CommBase.strtoi(json.getString("state_ot_validf"));
//		npoper.state_df_validf	= (byte)CommBase.strtoi(json.getString("state_df_validf"));
//		
//		npoper.alarmvalidf		= (byte)CommBase.strtoi(json.getString("alarmvalidf"));
//		npoper.commdatavalidf	= (byte)CommBase.strtoi(json.getString("commdatavalidf"));
		
		npoper_parastate.state.buy_times = json.getInt("buy_times");
		npoper.state 			= npoper_parastate.state;
		
		ret_val = ComntOperNpFunc.NPSetUpdate(user_name, i_user_data1, i_user_data2, rtu_para, farmerid, npoper, err_str_1, task_result_detail);
		
		if (ret_val == false) {
			op_detail.addTaskInfo("ERR:设置预付费参数及状态错误");
			return SDDef.FAIL;			
		}
		return SDDef.SUCCESS;
	}	
	
	//获取片区档案
	private com.kesd.dbpara.AreaPara NPGetAreaPara(int area_id) {
		HibDao 		 hib_dao = new HibDao();		
		return (com.kesd.dbpara.AreaPara) hib_dao.loadById(com.kesd.dbpara.AreaPara.class, new Integer(area_id));
	}
	
	//农排操作-获得预付费参数及状态
	private String NPGetPayParaState(String user_name, ComntParaBase.RTU_PARA rtu_para, short farmerid, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) {
		
		int i_user_data1 = CommBase.strtoi(userData1);	//area ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		boolean ret_val = false;
		
		npOperGParaState = SDDef.EMPTY;
		
		ComntProtMsg.YFF_DATA_NPOPER_PARASTATE npoper_parastate = new ComntProtMsg.YFF_DATA_NPOPER_PARASTATE();
		
        ret_val = ComntOperNpFunc.NPGetPayParaState(user_name, i_user_data1, i_user_data2, rtu_para, farmerid, npoper_parastate, err_str_1, task_result_detail);

		if (ret_val) {
			com.kesd.dbpara.AreaPara area_para = NPGetAreaPara(i_user_data1);
			npOperGParaState = npoper_parastate.toJsonString(area_para);
			return SDDef.SUCCESS;
		}
		else {
			return SDDef.FAIL;
		}
	}	
	

	//农排操作-销户
	private String NPDestory(String user_name, ComntParaBase.RTU_PARA rtu_para, short farmerid, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		
		boolean ret_val = false;
		
		if (npOperDestory != null) npOperDestory = npOperDestory.trim();
		if (npOperDestory == null || npOperDestory.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(npOperDestory);
		
		//20131019
		ComntMsgNp.MSG_NPOPER_DESTORY npoper = new ComntMsgNp.MSG_NPOPER_DESTORY();

		npoper.testf = 0;
		npoper.gsmf  = gsm_flag;
		npoper.farmerid  = farmerid;
		
		ComntFunc.string2Byte(user_name, npoper.operman);
		npoper.opresult = op_result;
		npoper.lsremain  = CommBase.strtof(json.getString("lsremain"));
		
		npoper.money.pay_money  = CommBase.strtof(json.getString("pay_money"));
		npoper.money.othjs_money= CommBase.strtof(json.getString("othjs_money"));
		npoper.money.zb_money	= CommBase.strtof(json.getString("zb_money"));
		npoper.money.all_money  = CommBase.strtof(json.getString("all_money"));
		
		npOperDestory = SDDef.EMPTY;
		ComntProtMsg.YFF_DATA_OPER_IDX  operidx = new ComntProtMsg.YFF_DATA_OPER_IDX();
		
		ret_val = ComntOperNpFunc.NPDestory(user_name, i_user_data1, i_user_data2, rtu_para, farmerid, npoper, operidx, err_str_1, task_result_detail);
		
		if (ret_val) {
			npOperDestory = operidx.toJsonString();
			return SDDef.SUCCESS;
		}
		else {
			return SDDef.FAIL;
		}
	}
	
	//农排操作-更改用户参数  20130201
	private String NPResetDoc(String user_name, ComntParaBase.RTU_PARA rtu_para, short farmerid, byte gsm_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail, byte op_result) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		
		boolean ret_val = false;
		
		if (npOperResetDoc != null) npOperResetDoc = npOperResetDoc.trim();
		if (npOperResetDoc == null || npOperResetDoc.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(npOperResetDoc);
		
		//20131019
		ComntMsgNp.MSG_NPOPER_RESETDOC npoper = new ComntMsgNp.MSG_NPOPER_RESETDOC();


		npoper.testf = 0;
		npoper.gsmf  = gsm_flag;
		npoper.farmerid  = farmerid;
		
		ComntFunc.string2Byte(user_name, npoper.operman);
		npoper.chgtype  = (byte)CommBase.strtoi(json.getString("chgtype"));
		
		ComntFunc.string2Byte(json.getString("filed1"), npoper.filed1);
		ComntFunc.string2Byte(json.getString("filed2"), npoper.filed2);
		ComntFunc.string2Byte(json.getString("filed3"), npoper.filed3);
		ComntFunc.string2Byte(json.getString("filed4"), npoper.filed4);
				
		npOperResetDoc = SDDef.EMPTY;
		
		ret_val = ComntOperNpFunc.NPResetDoc(user_name, i_user_data1, i_user_data2, rtu_para, farmerid, npoper, err_str_1, task_result_detail);
		
		if (ret_val == false) {
			return SDDef.FAIL;
		}		
		
		return SDDef.SUCCESS;
	}
	
	public String taskProc() {
		ComntParaBase.OpDetailInfo op_detail = new ComntParaBase.OpDetailInfo();
		StringBuffer err_str_1 				 = new StringBuffer();
		ComntParaBase.SALEMAN_TASK_RESULT_DETAIL 	task_result_detail = new ComntParaBase.SALEMAN_TASK_RESULT_DETAIL();
		
		int area_id    = CommBase.strtoi(userData1);
		int userop_id  = CommBase.strtoi(userData2); 
		short famer_id   = (short)CommBase.strtoi(farmerid);
		byte gsm_flag  = (byte)CommBase.strtoi(gsmflag);
		byte op_result = 0;	//返回结果
		
		//农排应用中RTU_PARA不再具有终端的功能,在此作为一个片区的载体(片区的意思).
		ComntParaBase.RTU_PARA rtu_para = new ComntParaBase.RTU_PARA();//ComntParaBase.loadRtuActPara(rtu_id);
		rtu_para.rtu_id = area_id;
		
		/*
		if (rtu_para == null) {
			result = SDDef.FAIL;
			op_detail.addTaskInfo("ERR:无效的片区ID[" + area_id + "]");
			detailInfo = op_detail.toJsonString(); 
			return SDDef.SUCCESS;
		}
		*/
		
		String user_name = ComntParaBase.getUserName();
		
		try{
			String ret_val = SDDef.FAIL;
			if (userop_id == ComntUseropDef.YFF_NPOPER_ADDRES) {			//农排操作-开户
				ret_val = NPAddRes(user_name, rtu_para, famer_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_NPOPER_PAY) {			//农排操作-缴费
				ret_val = NPPay(user_name, rtu_para, famer_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_NPOPER_REPAIR) {		//农排操作-补卡
				ret_val = NPRepair(user_name, rtu_para, famer_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_NPOPER_REWRITE) {		//农排操作-补写卡
				ret_val = NPReWrite(user_name, rtu_para, famer_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_NPOPER_REVER) {		//农排操作-冲正
				ret_val = NPRever(user_name, rtu_para, famer_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_NPOPER_UDPATESTATE) {	//农排操作-强制状态更新
				ret_val = NPSetUpdate(user_name, rtu_para, famer_id, gsm_flag, op_detail, err_str_1, task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_NPOPER_GPARASTATE) {	//农排操作-获得预付费参数及状态
				ret_val = NPGetPayParaState(user_name, rtu_para, famer_id, op_detail, err_str_1, task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_NPOPER_DESTORY) {		//农排操作-销户
				ret_val = NPDestory(user_name, rtu_para, famer_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			else if (userop_id == ComntUseropDef.YFF_NPOPER_RESETDOC) {		//农排操作-更改用户参数  20130201
				ret_val = NPResetDoc(user_name, rtu_para, famer_id, gsm_flag, op_detail, err_str_1, task_result_detail, op_result);
			}
			
	
			else {
				op_detail.addTaskInfo("ERR:未知的操作类型[" + userop_id + "]");
			}
			result 			= ret_val;
			err_strinfo		= err_str_1.toString();
			detailInfo = op_detail.toJsonString();
			operErr			= ComntParaBase.makeOperErrCode(task_result_detail);
		}catch (Exception e) {

			CommFunc.err_log(e);
			result = SDDef.FAIL;
			err_strinfo = e.getMessage();
		}
//		task_resultinfo = task_result_detail;	//以后再详细描述
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
	public String getGsmflag() {
		return gsmflag;
	}
	public void setGsmflag(String gsmflag) {
		this.gsmflag = gsmflag;
	}
	public String getNpOperAddres() {
		return npOperAddres;
	}
	public void setNpOperAddres(String npOperAddres) {
		this.npOperAddres = npOperAddres;
	}
	public String getNpOperPay() {
		return npOperPay;
	}
	public void setNpOperPay(String npOperPay) {
		this.npOperPay = npOperPay;
	}
	public String getNpOperRepair() {
		return npOperRepair;
	}
	public void setNpOperRepair(String npOperRepair) {
		this.npOperRepair = npOperRepair;
	}
	public String getNpOperRewrite() {
		return npOperRewrite;
	}
	public void setNpOperRewrite(String npOperRewrite) {
		this.npOperRewrite = npOperRewrite;
	}
	public String getNpOperRever() {
		return npOperRever;
	}
	public void setNpOperRever(String npOperRever) {
		this.npOperRever = npOperRever;
	}
	public String getNpOperDestory() {
		return npOperDestory;
	}
	public void setNpOperDestory(String npOperDestory) {
		this.npOperDestory = npOperDestory;
	}
	public String getNpOperUpdateState() {
		return npOperUpdateState;
	}
	public void setNpOperUpdateState(String npOperUpdateState) {
		this.npOperUpdateState = npOperUpdateState;
	}
	public String getNpOperGParaState() {
		return npOperGParaState;
	}
	public void setNpOperGParaState(String npOperGParaState) {
		this.npOperGParaState = npOperGParaState;
	}
	public String getFarmerid() {
		return farmerid;
	}
	public void setFarmerid(String farmerid) {
		this.farmerid = farmerid;
	}

	public String getNpOperResetDoc() {
		return npOperResetDoc;
	}

	public void setNpOperResetDoc(String npOperResetDoc) {
		this.npOperResetDoc = npOperResetDoc;
	}

	public String getOperErr() {
		return operErr;
	}

	public void setOperErr(String operErr) {
		this.operErr = operErr;
	}
	
}
