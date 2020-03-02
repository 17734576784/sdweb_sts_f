package com.kesd.action.oper;

import net.sf.json.JSONObject;

import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.comnt.ComntMsg;
import com.kesd.comnt.ComntProtMsg;
import com.kesd.comntpara.ComntOperGyFunc;
import com.kesd.comntpara.ComntParaBase;
import com.kesd.comntpara.ComntUseropDef;
import com.kesd.dbpara.YffManDef;
import com.libweb.common.CommBase;
import com.libweb.comnt.ComntFunc;
import com.kesd.comntpara.ComntParaBase.SALEMAN_TASK_RESULT_DETAIL;

public class ActSysCtrl {
	private static final long serialVersionUID = 3607391502741009698L;
	
	private String  userData2 		= null;		//USEROP_ID
	private String 	result 			= null;		//SUCCESS/FAIL
	private String 	detailInfo 		= null;		//返回详细信息(JSON)
	private String	err_strinfo		= null;		//返回错误信息
	private String	task_resultinfo	= null;		//返回任务详细信息
	
	private String	getPauseAlarm	= null;		//得到-暂停预付费报警及控制
	private String	setpauseAlarm	= null;		//设置-暂停预付费报警及控制
	private String	getGloProt		= null;		//得到-全局保电参数
	private String	setGloProt		= null;		//设置-全局保电参数
	
	private String operErr				= null;		//返回错误信息描述,在js弹出框中显示
	
	//得到-暂停预付费报警及控制
	private String GetPauseAlarm(String user_name, StringBuffer err_str_1,SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;
		
		JSONObject json = JSONObject.fromObject(getPauseAlarm);
		ComntMsg.MSG_GET_PAUSEALARM pausealarm_req = new ComntMsg.MSG_GET_PAUSEALARM();
		ComntFunc.string2Byte(user_name, pausealarm_req.operman);
		pausealarm_req.type = json.getInt("type");
		
		ComntProtMsg.YFF_DATA_OPER_PAUSEALARM op_result = new ComntProtMsg.YFF_DATA_OPER_PAUSEALARM();
		
		ret_val = ComntOperGyFunc.GetPauseAlarm(user_name, i_user_data2, pausealarm_req, op_result, err_str_1,task_result_detail);
		
		if (ret_val) {
			getPauseAlarm = op_result.toJsonString();
			return SDDef.SUCCESS;
		}
		else {
			return SDDef.FAIL;
		}
	}
	
	//设置-暂停预付费报警及控制
	private String SetPauseAlarm(String user_name, StringBuffer err_str_1,SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID

		boolean ret_val = false;
		
		JSONObject json = JSONObject.fromObject(setpauseAlarm);
		
		ComntMsg.MSG_SET_PAUSEALARM pausealarm_req = new ComntMsg.MSG_SET_PAUSEALARM();
		pausealarm_req.type = json.getInt("type");
		ComntFunc.string2Byte(user_name, pausealarm_req.operman);
		pausealarm_req.mins = json.getInt("mins");
		
		ret_val = ComntOperGyFunc.SetPauseAlarm(user_name, i_user_data2, pausealarm_req, err_str_1,task_result_detail);
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}
	
	//得到-全局保电参数
	private String GetGloprot(String user_name, StringBuffer err_str_1,SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		boolean ret_val = false;
		
		JSONObject json = JSONObject.fromObject(getGloProt);
		ComntMsg.MSG_GET_GLOPROT gloprot_req = new ComntMsg.MSG_GET_GLOPROT();
		ComntFunc.string2Byte(user_name, gloprot_req.operman);
		gloprot_req.type = json.getInt("type");
		
		ComntProtMsg.YFF_DATA_OPER_GLOPROT op_result = new ComntProtMsg.YFF_DATA_OPER_GLOPROT();
		
		ret_val = ComntOperGyFunc.GetGloProt(user_name, i_user_data2, gloprot_req, op_result, err_str_1,task_result_detail);
		
		if (ret_val) {
			getGloProt = op_result.toJsonString();
			return SDDef.SUCCESS;
		}
		else {
			return SDDef.FAIL;
		}
	}
	
	//设置-全局保电参数
	private String SetGloprot(String user_name, StringBuffer err_str_1,SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID

		boolean ret_val = false;
		
		JSONObject json = JSONObject.fromObject(setGloProt);
		
		ComntMsg.MSG_SET_GLOPROT gloprot_req = new ComntMsg.MSG_SET_GLOPROT();
		gloprot_req.type = json.getInt("type");
		ComntFunc.string2Byte(user_name, gloprot_req.operman);
		gloprot_req.app_type = (byte)json.getInt("app_type");
		gloprot_req.use_flag = (byte)json.getInt("use_flag");
		gloprot_req.bg_date = json.getInt("bg_date");
		gloprot_req.bg_time = json.getInt("bg_time");
		gloprot_req.ed_date = json.getInt("ed_date");
		gloprot_req.ed_time = json.getInt("ed_time");
		
		ret_val = ComntOperGyFunc.SetGloProt(user_name, i_user_data2, gloprot_req, err_str_1,task_result_detail);
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}
	
	public String taskProc(){
		try {
			ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail = new ComntParaBase.SALEMAN_TASK_RESULT_DETAIL();
			ComntParaBase.OpDetailInfo op_detail = new ComntParaBase.OpDetailInfo();
			StringBuffer err_str_1 				 = new StringBuffer();
			
			int userop_id = CommBase.strtoi(userData2); 
			YffManDef user = CommFunc.getYffMan();
			if(user.getOpenflag() == 0  && user.getPayflag() == 0) {
				result = SDDef.FAIL;
				err_strinfo = "No Permission";
				return SDDef.SUCCESS;
			}
			String user_name = user.getName().trim();
			
			String ret_val = SDDef.FAIL;
	
			if (userop_id == ComntUseropDef.YFF_GET_PAUSEALARM) {			//得到-暂停预付费报警及控制
				ret_val = GetPauseAlarm(user_name, err_str_1,task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_SET_PAUSEALARM) {		//设置-暂停预付费报警及控制
				ret_val = SetPauseAlarm(user_name, err_str_1,task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_GET_GLOPROT) {			//得到-全局保电参数
				ret_val = GetGloprot(user_name, err_str_1,task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_SET_GLOPROT) {			//设置-全局保电参数
				ret_val = SetGloprot(user_name, err_str_1,task_result_detail);
			}
			else {
				op_detail.addTaskInfo("ERR:未知的操作类型[" + userop_id + "]");
			}
			result 			= ret_val;
			err_strinfo		= err_str_1.toString();
	//		task_resultinfo = task_result_detail;	//以后再详细描述
			detailInfo = op_detail.toJsonString();
			operErr			= ComntParaBase.makeOperErrCode(task_result_detail);
		} catch (Exception e) {
			CommFunc.err_log(e);
			err_strinfo = e.getMessage();
		}
		return SDDef.SUCCESS;
	}
	
	public String cancelTask() {
		/*
		String user_name = ComntParaBase.getUserName();	
		int i_user_data2 = CommBase.strtoi(userData2);
		ComntMsgProc.cancelMsg(user_name, i_user_data1, i_user_data2);
		result = SDDef.SUCCESS;
		*/	
		return SDDef.SUCCESS;
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

	public String getGetPauseAlarm() {
		return getPauseAlarm;
	}

	public void setGetPauseAlarm(String getPauseAlarm) {
		this.getPauseAlarm = getPauseAlarm;
	}

	public String getSetpauseAlarm() {
		return setpauseAlarm;
	}

	public void setSetpauseAlarm(String setpauseAlarm) {
		this.setpauseAlarm = setpauseAlarm;
	}

	public String getGetGloProt() {
		return getGloProt;
	}

	public void setGetGloProt(String getGloProt) {
		this.getGloProt = getGloProt;
	}

	public String getSetGloProt() {
		return setGloProt;
	}

	public void setSetGloProt(String setGloProt) {
		this.setGloProt = setGloProt;
	}

	public String getOperErr() {
		return operErr;
	}

	public void setOperErr(String operErr) {
		this.operErr = operErr;
	}
	
}
