package com.kesd.action.np;

import java.util.ArrayList;

import net.sf.json.JSONObject;

import com.libweb.common.CommBase;
import com.libweb.comnt.ComntFunc;
import com.libweb.comnt.ComntVector;

import com.kesd.common.CommCardWrt;
import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.comnt.ComntDef;
import com.kesd.comnt.ComntMsg;
import com.kesd.comnt.ComntMsgDy;
import com.kesd.comnt.ComntProtMsg;
import com.kesd.comntpara.ComntParaBase;
import com.kesd.comntpara.ComntUseropDef;
import com.kesd.util.Rd;

public class ActCardReadInfo {

	private static final long serialVersionUID = 3607391502741009698L;
	private String  userData1 			= null;		//RTU_ID
	private String  userData2 			= null;		//USEROP_ID
	private String 	result 				= null;		//SUCCESS/FAIL
	private String	detailInfo			= null;
	private String	userInfo			= null;
	private String	firstLastFlag		= null;
	private String	err_strinfo			= null;
	private String 	mpid 				= null;		
	private String 	gsmflag				= null;		
	private String	dyOperGParaState	= null;		//低压操作-获得预付费参数及状态
	
	public String taskProc(){
		ComntParaBase.OpDetailInfo op_detail = new ComntParaBase.OpDetailInfo();
		StringBuffer err_str_1 				 = new StringBuffer();
		ComntParaBase.SALEMAN_TASK_RESULT_DETAIL 	task_result_detail = new ComntParaBase.SALEMAN_TASK_RESULT_DETAIL();
		if (result == null) {
			
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		
		CommCardWrt.MET_RES_MP met_res_mp = CommCardWrt.loadMetResMpPara(result);
		
		JSONObject j_obj = new JSONObject();
		
		j_obj.put("cons_no", 		String.valueOf(result));
		j_obj.put("meter_no", 		String.valueOf(met_res_mp==null?"":met_res_mp.meter_no));
		j_obj.put("cons_desc", 		String.valueOf(met_res_mp==null?"":met_res_mp.cons_desc));
		j_obj.put("cons_addr", 		String.valueOf(met_res_mp==null?"":met_res_mp.cons_addr));
		j_obj.put("cons_telno", 	String.valueOf(met_res_mp==null?"":met_res_mp.cons_telno));
		j_obj.put("pt_n", 			met_res_mp==null?"":met_res_mp.pt_n);
		j_obj.put("pt_d", 			met_res_mp==null?"":met_res_mp.pt_d);
		j_obj.put("pt", 			met_res_mp==null?"":met_res_mp.pt);
		
		j_obj.put("ct_n", 			met_res_mp==null?"":met_res_mp.ct_n);
		j_obj.put("ct_d", 			met_res_mp==null?"":met_res_mp.ct_d);
		j_obj.put("ct", 			met_res_mp==null?"":met_res_mp.ct);
		j_obj.put("rtu_id", 		met_res_mp==null?"":met_res_mp.rtu_id);
		j_obj.put("mp_id", 			met_res_mp==null?"":met_res_mp.mp_id);
		
		if(met_res_mp == null){
			userInfo		= j_obj.toString();
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		
		int rtu_id    = met_res_mp.rtu_id;
		short mp_id   = met_res_mp.mp_id;
		int userop_id = CommBase.strtoi(userData2); 
		
		ComntParaBase.RTU_PARA rtu_para = ComntParaBase.loadRtuActPara(rtu_id);
		
		if (rtu_para == null) {
			result = SDDef.FAIL;
			op_detail.addTaskInfo("ERR:无效的终端ID[" + rtu_id + "]");
			detailInfo = op_detail.toJsonString(); 
			return SDDef.SUCCESS;
		}
		String user_name = ComntParaBase.getUserName();
		
		String ret_val = SDDef.FAIL;

		if (userop_id == ComntUseropDef.YFF_DYOPER_GPARASTATE) {	//低压操作-获得预付费参数及状态
			String firstlast_str = firstLastFlag.trim();
			boolean first_flag = (firstlast_str.compareToIgnoreCase(SDDef.TRUE) == 0);
			
			ret_val = DYGetPayParaState(user_name, rtu_para, mp_id, first_flag, op_detail, err_str_1, task_result_detail);
		}

		else {
			op_detail.addTaskInfo("ERR:未知的操作类型[" + userop_id + "]");
		}
		result 			= ret_val;
		userInfo		= j_obj.toString();
		err_strinfo		= err_str_1.toString();
		detailInfo 		= op_detail.toJsonString();
		return SDDef.SUCCESS;
	}
	
	
	//低压操作-获得预付费参数及状态
	private String DYGetPayParaState(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, boolean first_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;		
		
		boolean ret_val = false;
		
		//发送
		if (first_flag) {
			//20131019
			//ComntMsg.MSG_DYOPER_GETPARASTATE dyoper = new ComntMsg.MSG_DYOPER_GETPARASTATE();
			ComntMsgDy.MSG_DYOPER_GETPARASTATE dyoper = new ComntMsgDy.MSG_DYOPER_GETPARASTATE();
			dyoper.testf = 0;
			dyoper.gsmf  = 0;
			dyoper.mpid  = mp_id;
			ComntFunc.string2Byte(user_name, dyoper.operman);

			ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
			dyoper.toDataStream(task_data_vect);

			ret_val = ComntParaBase.sendNStepTask(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
					(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYOPER_GPARASTATE, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, task_data_vect, op_detail);
			
			if (!ret_val) {
				return SDDef.FAIL;
			}
		}

		//接受
		byte []task_result = new byte[1];	
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect = new ArrayList<ComntMsg.MSG_RESULT_DATA>();		

		ret_val = ComntParaBase.getNStepTaskResult(user_name, i_user_data1, i_user_data2, rtu_para, 
				task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);

		dyOperGParaState = SDDef.EMPTY;

		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			ComntProtMsg.YFF_DATA_DYOPER_PARASTATE dyoper_parastate = new ComntProtMsg.YFF_DATA_DYOPER_PARASTATE();
			
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_DYOPER_PARASTATE) {
					dyoper_parastate.fromDataStream(msg_result_data.data_vect, 0);
					dyOperGParaState = dyoper_parastate.toJsonString();

				}
			}
		}
		
		if (ret_val) {
			if (task_result[0] != ComntDef.YD_YFF_TASKRESULT_NULL) firstLastFlag = SDDef.TRUE;
			else firstLastFlag = SDDef.FALSE;
		}
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
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
	public String getFirstLastFlag() {
		return firstLastFlag;
	}
	public void setFirstLastFlag(String firstLastFlag) {
		this.firstLastFlag = firstLastFlag;
	}
	public String getErr_strinfo() {
		return err_strinfo;
	}
	public void setErr_strinfo(String errStrinfo) {
		err_strinfo = errStrinfo;
	}
	public String getMpid() {
		return mpid;
	}
	public void setMpid(String mpid) {
		this.mpid = mpid;
	}
	public String getGsmflag() {
		return gsmflag;
	}
	public void setGsmflag(String gsmflag) {
		this.gsmflag = gsmflag;
	}
	public String getDyOperGParaState() {
		return dyOperGParaState;
	}
	public void setDyOperGParaState(String dyOperGParaState) {
		this.dyOperGParaState = dyOperGParaState;
	}
	public String getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}	
}
