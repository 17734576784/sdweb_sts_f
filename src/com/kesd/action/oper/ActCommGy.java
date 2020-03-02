package com.kesd.action.oper;

import java.util.ArrayList;

import net.sf.json.JSONObject;

import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.comnt.ComntDef;
import com.kesd.comnt.ComntMsg;
import com.kesd.comnt.ComntMsgGy;
import com.kesd.comnt.ComntMsgProc;
import com.kesd.comnt.ComntProtMsg;
import com.kesd.comntpara.ComntParaBase;
import com.kesd.comntpara.ComntUseropDef;
import com.libweb.common.CommBase;
import com.libweb.comnt.ComntFunc;
import com.libweb.comnt.ComntVector;

public class ActCommGy {
	private static final long serialVersionUID = 3607391502741009698L;
	private String  userData1 			= null;		//RTU_ID
	private String  userData2 			= null;		//USEROP_ID
	private String 	result 				= null;		//SUCCESS/FAIL
	private String 	detailInfo 			= null;		//返回详细信息(JSON)
	private String	err_strinfo			= null;		//返回错误信息
	private String	task_resultinfo		= null;		//返回任务详细信息
	private String  callFlag			= null;		//是否召测-召测/设置
	private String  firstLastFlag		= null;		//用于多步执行的任务
	private String  zjgid				= null;		//总加组编号	
	
	private String	gyCommReadData		= null;		//读取数据高压
	private String	gyCommPay			= null;		//高压通讯-缴费
	private String	gyCommCallPara		= null;		//高压通讯-召参数
	private String	gyCommSetPara		= null;		//高压通讯-设参数
	private String	gyCommCtrl			= null;		//高压通讯-控制
	
	//读取数据高压
	private String GyCommReadData(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, boolean first_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		short read_datatype = 0;
		int i = 0;
		
		boolean ret_val = false;
		
		//发送
		if (first_flag) {
			if (gyCommReadData != null) gyCommReadData = gyCommReadData.trim();
			if (gyCommReadData == null || gyCommReadData.length() <= 0) {
				op_detail.addTaskInfo("ERR:错误的请求参数");
				return SDDef.FAIL;
			}

			JSONObject json = JSONObject.fromObject(gyCommReadData);
			
			ComntMsg.MSG_READDATA gycomm = new ComntMsg.MSG_READDATA();

			gycomm.datatype = (short)CommBase.strtoi(json.getString("datatype"));
			
			read_datatype = gycomm.datatype;
			
			if (read_datatype == ComntProtMsg.YFF_CALL_GY_REMAIN) {
				gycomm.mpid  	= zjg_id;
			}
			else if (read_datatype >= ComntProtMsg.YFF_CALL_REAL_ZBD && read_datatype <= ComntProtMsg.YFF_CALL_REAL_FBD) {
				gycomm.mpid 	= zjg_id;
			}
			else if (read_datatype >= ComntProtMsg.YFF_CALL_DAY_ZBD && read_datatype <= ComntProtMsg.YFF_CALL_DAY_FBD) {
				gycomm.mpid 	= zjg_id;
				gycomm.ymd 		= CommBase.strtoi(json.getString("ymd"));
			}
			
			ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
			gycomm.toDataStream(task_data_vect);

			ret_val = ComntParaBase.sendNStepTask(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
					(byte)ComntDef.YD_YFF_TASKAPPTYPE_READDATA, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, task_data_vect, op_detail);
			
			if (!ret_val) {
				return SDDef.FAIL;
			}
		}

		//接收
		byte []task_result = new byte[1];	
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect = new ArrayList<ComntMsg.MSG_RESULT_DATA>();		

		ret_val = ComntParaBase.getNStepTaskResult(user_name, i_user_data1, i_user_data2, rtu_para, 
				task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);


		gyCommReadData = SDDef.EMPTY;
		if (read_datatype == ComntProtMsg.YFF_CALL_GY_REMAIN) {		//F21
			ComntProtMsg.YFF_DATA_GYREMAIN  gyremain = new ComntProtMsg.YFF_DATA_GYREMAIN();
			if (ret_data_vect.size() > 0) {
				ComntMsg.MSG_RESULT_DATA msg_result_data = null;

				for (i = 0; i < ret_data_vect.size(); i++) {
					msg_result_data = ret_data_vect.get(i);
					if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_GY_REALREMAIN) {
						gyremain.fromDataStream(msg_result_data.data_vect, 0);
						gyCommReadData = gyremain.toJsonString();
					}
				}
			}
		}
		else if (read_datatype >= ComntProtMsg.YFF_CALL_REAL_ZBD && read_datatype <= ComntProtMsg.YFF_CALL_DAY_FBD) {
			ComntProtMsg.YFF_DATA_READBD  readbd = new ComntProtMsg.YFF_DATA_READBD();
			if (ret_data_vect.size() > 0) {
				ComntMsg.MSG_RESULT_DATA msg_result_data = null;
				
				for (i = 0; i < ret_data_vect.size(); i++) {
					msg_result_data = ret_data_vect.get(i);
					if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_REAL_ZBD ||
						msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_REAL_FBD ||
						msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_DAY_ZBD  ||
						msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_DAY_FBD) {
						
						readbd.fromDataStream(msg_result_data.data_vect, 0);
						gyCommReadData = readbd.toJsonString();
						
					}
				}
			}
		}
		
		
		if (ret_val) {
			if (task_result[0] != ComntDef.YD_YFF_TASKRESULT_NULL) firstLastFlag = SDDef.TRUE;
			else firstLastFlag = SDDef.FALSE;
		}
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}
	
	//高压通讯-缴费
	private String GyCommPay(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, boolean first_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID

		boolean ret_val = false;
		
		//发送
		if (first_flag) {
			if (gyCommPay != null) gyCommPay = gyCommPay.trim();
			if (gyCommPay == null || gyCommPay.length() <= 0) {
				op_detail.addTaskInfo("ERR:错误的请求参数");
				return SDDef.FAIL;
			}
			
			JSONObject json = JSONObject.fromObject(gyCommPay);
			
			//20131019
			//ComntMsg.MSG_GYCOMM_PAY gycomm = new ComntMsg.MSG_GYCOMM_PAY();
			ComntMsgGy.MSG_GYCOMM_PAY gycomm = new ComntMsgGy.MSG_GYCOMM_PAY();
			
			gycomm.zjgid  = zjg_id;
			
			ComntFunc.string2Byte(user_name, gycomm.operman);

			gycomm.yffalarm_id 		= (short)CommBase.strtoi(json.getString("yffalarm_id"));
			gycomm.buynum 			= CommBase.strtoi(json.getString("buynum")) + 1;

			gycomm.money.pay_money  = CommBase.strtof(json.getString("pay_money"));
			gycomm.money.othjs_money= CommBase.strtof(json.getString("othjs_money"));
			gycomm.money.zb_money	= CommBase.strtof(json.getString("zb_money"));
			gycomm.money.all_money  = CommBase.strtof(json.getString("all_money"));
			
			gycomm.alarmmoney  		= CommBase.strtof(json.getString("alarmmoney"));
			gycomm.buydl  			= CommBase.strtof(json.getString("buydl"));
			gycomm.paybmc  			= CommBase.strtof(json.getString("paybmc"));
			gycomm.shutbd  			= CommBase.strtof(json.getString("shutbd"));
			gycomm.alarmbd  		= CommBase.strtof(json.getString("alarmbd"));
			gycomm.yffflag  		= (byte)CommBase.strtof(json.getString("yffflag"));
			gycomm.plustime  		= (short)CommBase.strtof(json.getString("plustime"));
			
			ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
			gycomm.toDataStream(task_data_vect);

			ret_val = ComntParaBase.sendNStepTask(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
					(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYCOMM_PAY, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, task_data_vect, op_detail);

			if (!ret_val) {
				return SDDef.FAIL;
			}
		}
		
		//接收
		byte []task_result = new byte[1];	
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect = new ArrayList<ComntMsg.MSG_RESULT_DATA>();		

		ret_val = ComntParaBase.getNStepTaskResult(user_name, i_user_data1, i_user_data2, rtu_para, 
				task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);
		
		if (ret_val) {
			if (task_result[0] != ComntDef.YD_YFF_TASKRESULT_NULL) firstLastFlag = SDDef.TRUE;
			else firstLastFlag = SDDef.FALSE;
		}
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}
	
	//高压通讯-召参数
	private String GyCommCallPara(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, boolean first_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		short call_paratype = 0;
		int i = 0;
		
		boolean ret_val = false;
		
		//发送
		if (first_flag) {
			if (gyCommCallPara != null) gyCommCallPara = gyCommCallPara.trim();
			if (gyCommCallPara == null || gyCommCallPara.length() <= 0) {
				op_detail.addTaskInfo("ERR:错误的请求参数");
				return SDDef.FAIL;
			}

			JSONObject json = JSONObject.fromObject(gyCommCallPara);
			
			//20131019
			//ComntMsg.MSG_GYCOMM_CALLPARA gycomm = new ComntMsg.MSG_GYCOMM_CALLPARA();
			ComntMsgGy.MSG_GYCOMM_CALLPARA gycomm = new ComntMsgGy.MSG_GYCOMM_CALLPARA();
			
			gycomm.zjgid  	= zjg_id;
			gycomm.paratype = (short)CommBase.strtoi(json.getString("paratype"));
			ComntFunc.string2Byte(user_name, gycomm.operman);
			
			call_paratype = gycomm.paratype;
			
			ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
			gycomm.toDataStream(task_data_vect);

			ret_val = ComntParaBase.sendNStepTask(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
					(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYCOMM_CALLPARA, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, task_data_vect, op_detail);
			
			if (!ret_val) {
				return SDDef.FAIL;
			}
		}

		//接收
		byte []task_result = new byte[1];	
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect = new ArrayList<ComntMsg.MSG_RESULT_DATA>();		

		ret_val = ComntParaBase.getNStepTaskResult(user_name, i_user_data1, i_user_data2, rtu_para, 
				task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);

		gyCommCallPara = SDDef.EMPTY;
		switch(call_paratype) {
			case ComntProtMsg.YFF_GY_CALL_PARAREMAIN: //F47
				ComntProtMsg.YFF_DATA_GY_CALLREMAIN  gypara = new ComntProtMsg.YFF_DATA_GY_CALLREMAIN();
				if (ret_data_vect.size() > 0) {
					ComntMsg.MSG_RESULT_DATA msg_result_data = null;

					for (i = 0; i < ret_data_vect.size(); i++) {
						msg_result_data = ret_data_vect.get(i);
						if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_GY_CALLPARAREMAIN) {
							gypara.fromDataStream(msg_result_data.data_vect, 0);
							gyCommCallPara = gypara.toJsonString();
						}
					}
				}
				break;
			case ComntProtMsg.YFF_GY_CALL_FEE:
				ComntProtMsg.YFF_DATA_GY_CALLFEERATE  gyfee = new ComntProtMsg.YFF_DATA_GY_CALLFEERATE();
				if (ret_data_vect.size() > 0) {
					ComntMsg.MSG_RESULT_DATA msg_result_data = null;

					for (i = 0; i < ret_data_vect.size(); i++) {
						msg_result_data = ret_data_vect.get(i);
						if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_GY_CALLFEE) {
							gyfee.fromDataStream(msg_result_data.data_vect, 0);
							gyCommCallPara = gyfee.toJsonString();
						}
					}
				}
				break;
			case ComntProtMsg.YFF_GY_CALL_APPEND:
				ComntProtMsg.YFF_DATA_GY_CALLFEERATE gyappend = new ComntProtMsg.YFF_DATA_GY_CALLFEERATE();
				if (ret_data_vect.size() > 0) {
					ComntMsg.MSG_RESULT_DATA msg_result_data = null;

					for (i = 0; i < ret_data_vect.size(); i++) {
						msg_result_data = ret_data_vect.get(i);
						if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_GY_CALLAPPEND) {
							gyappend.fromDataStream(msg_result_data.data_vect, 0);
							gyCommCallPara = gyappend.toJsonString();
						}
					}
				}
				break;
			case ComntProtMsg.YFF_GY_CALL_KEEP:
				ComntProtMsg.YFF_DATA_GY_CALLPROTECT gykeep = new ComntProtMsg.YFF_DATA_GY_CALLPROTECT();
				if (ret_data_vect.size() > 0) {
					ComntMsg.MSG_RESULT_DATA msg_result_data = null;

					for (i = 0; i < ret_data_vect.size(); i++) {
						msg_result_data = ret_data_vect.get(i);
						if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_GY_CALLKEEP) {
							gykeep.fromDataStream(msg_result_data.data_vect, 0);
							gyCommCallPara = gykeep.toJsonString();
						}
					}
				}
				break;
		}
		
		if (ret_val) {
			if (task_result[0] != ComntDef.YD_YFF_TASKRESULT_NULL) firstLastFlag = SDDef.TRUE;
			else firstLastFlag = SDDef.FALSE;
		}
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}	
	
	//高压通讯-设参数
	private String GyCommSet(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, boolean first_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID

		boolean ret_val = false;
		
		//发送
		if (first_flag) {
			if (gyCommSetPara != null) gyCommSetPara = gyCommSetPara.trim();
			if (gyCommSetPara == null || gyCommSetPara.length() <= 0) {
				op_detail.addTaskInfo("ERR:错误的请求参数");
				return SDDef.FAIL;
			}
			
			JSONObject json = JSONObject.fromObject(gyCommSetPara);
			
			//20131019
			//ComntMsg.MSG_GYCOMM_SETPARA gycomm = new ComntMsg.MSG_GYCOMM_SETPARA();
			ComntMsgGy.MSG_GYCOMM_SETPARA gycomm = new ComntMsgGy.MSG_GYCOMM_SETPARA();

			gycomm.zjgid  	= zjg_id;
			gycomm.paratype = (short)CommBase.strtoi(json.getString("paratype"));
			ComntFunc.string2Byte(user_name, gycomm.operman);
			
			if(gycomm.paratype >= ComntProtMsg.YFF_GY_SET_FEI && gycomm.paratype <= ComntProtMsg.YFF_GY_SET_APPEND) {
				ComntProtMsg.YFF_GYSET_FEEPARA setfee = new ComntProtMsg.YFF_GYSET_FEEPARA();

				setfee.zjgid = zjg_id;

				setfee.feeproj_id  = (short)CommBase.strtoi(json.getString("feeproj_id"));
				setfee.pay_add1    = CommBase.strtof(json.getString("pay_add1"));
				setfee.pay_add2    = CommBase.strtof(json.getString("pay_add2"));
				setfee.pay_add3    = CommBase.strtof(json.getString("pay_add3"));
				
				setfee.toDataStream(gycomm.data_vect);	
			}
			else if (gycomm.paratype == ComntProtMsg.YFF_GY_SET_KEEP) {
				ComntProtMsg.YFF_GYSET_PROTECT gykeep = new ComntProtMsg.YFF_GYSET_PROTECT();
				
				gykeep.zjgid = zjg_id;
				gykeep.setpara.bdbegin 	= (byte)CommBase.strtoi(json.getString("bdbegin"));
				gykeep.setpara.bdend 	= (byte)CommBase.strtoi(json.getString("bdend"));
				gykeep.setpara.ctrlflag = (byte)CommBase.strtoi(json.getString("ctrlflag"));
				
				gykeep.toDataStream(gycomm.data_vect);
			}

			ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
			gycomm.toDataStream(task_data_vect);

			ret_val = ComntParaBase.sendNStepTask(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
					(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYCOMM_SETPARA, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, task_data_vect, op_detail);
			
			if (!ret_val) {
				return SDDef.FAIL;
			}
		}
		
		//接收
		byte []task_result = new byte[1];	
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect = new ArrayList<ComntMsg.MSG_RESULT_DATA>();		

		ret_val = ComntParaBase.getNStepTaskResult(user_name, i_user_data1, i_user_data2, rtu_para, 
				task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);
		
		if (ret_val) {
			if (task_result[0] != ComntDef.YD_YFF_TASKRESULT_NULL) firstLastFlag = SDDef.TRUE;
			else firstLastFlag = SDDef.FALSE;
		}
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}	
	
	//高压通讯-控制
	private String GyCommCtrl(String user_name, ComntParaBase.RTU_PARA rtu_para, short zjg_id, boolean first_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID

		boolean ret_val = false;
		
		//发送
		if (first_flag) {
			if (gyCommCtrl != null) gyCommCtrl = gyCommCtrl.trim();
			if (gyCommCtrl == null || gyCommCtrl.length() <= 0) {
				op_detail.addTaskInfo("ERR:错误的请求参数");
				return SDDef.FAIL;
			}
			
			JSONObject json = JSONObject.fromObject(gyCommCtrl);
			
			//20131019
			//ComntMsg.MSG_GYCOMM_CTRL gycomm = new ComntMsg.MSG_GYCOMM_CTRL();
			ComntMsgGy.MSG_GYCOMM_CTRL gycomm = new ComntMsgGy.MSG_GYCOMM_CTRL();

			gycomm.zjgid  = zjg_id;
			ComntFunc.string2Byte(user_name, gycomm.operman);
			gycomm.paratype = (short)CommBase.strtoi(json.getString("paratype"));
			
			if (gycomm.paratype == ComntProtMsg.YFF_GY_CTRL_CUT) {
				ComntProtMsg.YFF_GYCTRL_CUT gycut = new ComntProtMsg.YFF_GYCTRL_CUT();
				
				gycut.zjgid = zjg_id;
				gycut.ctrl.ctrltype = CommBase.strtoi(json.getString("ctrltype"));
				gycut.ctrl.ctrlroll = (byte)CommBase.strtoi(json.getString("ctrlroll"));
				
				int xdsc = CommBase.strtoi(json.getString("xdsc"));
				
				gycut.ctrl.limithour = (byte)(xdsc / 60);
				gycut.ctrl.limitmin = (byte)(xdsc % 60);
				
				gycut.ctrl.plustime = (short)CommBase.strtoi(json.getString("plustime"));
				gycut.ctrl.delaytime = (byte)CommBase.strtoi(json.getString("delaytime"));
				
				gycut.toDataStream(gycomm.data_vect);
			}else if (gycomm.paratype == ComntProtMsg.YFF_GY_CTRL_ON) {
				
				ComntProtMsg.YFF_GYCTRL_CUT gycut = new ComntProtMsg.YFF_GYCTRL_CUT();
				
				gycut.zjgid = zjg_id;
				gycut.ctrl.ctrlroll = (byte)CommBase.strtoi(json.getString("ctrlroll"));
				
				gycut.toDataStream(gycomm.data_vect);
			}
			
			ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
			gycomm.toDataStream(task_data_vect);

			ret_val = ComntParaBase.sendNStepTask(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
					(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYCOMM_CTRL, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, task_data_vect, op_detail);
			
			if (!ret_val) {
				return SDDef.FAIL;
			}
		}
		
		//接收
		byte []task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect = new ArrayList<ComntMsg.MSG_RESULT_DATA>();		

		ret_val = ComntParaBase.getNStepTaskResult(user_name, i_user_data1, i_user_data2, rtu_para, 
				task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);
		
		if (ret_val) {
			if (task_result[0] != ComntDef.YD_YFF_TASKRESULT_NULL) firstLastFlag = SDDef.TRUE;
			else firstLastFlag = SDDef.FALSE;
		}
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}	
	
	
	public String taskProc(){
		ComntParaBase.OpDetailInfo op_detail = new ComntParaBase.OpDetailInfo();
		StringBuffer err_str_1 				 = new StringBuffer();
		ComntParaBase.SALEMAN_TASK_RESULT_DETAIL 	task_result_detail = new ComntParaBase.SALEMAN_TASK_RESULT_DETAIL();
		
		int rtu_id    = CommBase.strtoi(userData1);
		int userop_id = CommBase.strtoi(userData2); 
		short zjg_id  = (short)CommBase.strtoi(zjgid);
		
		//int op_result = 0;	//返回结果
		
		ComntParaBase.RTU_PARA rtu_para = ComntParaBase.loadRtuActPara(rtu_id);
		
		if (rtu_para == null) {
			result = SDDef.FAIL;
			op_detail.addTaskInfo("ERR:无效的终端ID[" + rtu_id + "]");
			detailInfo = op_detail.toJsonString(); 
			return SDDef.SUCCESS;
		}
		String user_name = ComntParaBase.getUserName();
		
		try {
			
			String ret_val = SDDef.FAIL;
			if (userop_id == ComntUseropDef.YFF_READDATA) {					//读取数据高压
				String firstlast_str = firstLastFlag.trim();
				boolean first_flag = (firstlast_str.compareToIgnoreCase(SDDef.TRUE) == 0);
				
				ret_val = GyCommReadData(user_name, rtu_para, zjg_id, first_flag, op_detail, err_str_1, task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_GYCOMM_PAY) {			//高压通讯-缴费
				String firstlast_str = firstLastFlag.trim();
				boolean first_flag = (firstlast_str.compareToIgnoreCase(SDDef.TRUE) == 0);
				
				ret_val = GyCommPay(user_name, rtu_para, zjg_id, first_flag, op_detail, err_str_1, task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_GYCOMM_CALLPARA) {		//高压通讯-召参数
				String firstlast_str = firstLastFlag.trim();
				boolean first_flag = (firstlast_str.compareToIgnoreCase(SDDef.TRUE) == 0);
				
				ret_val = GyCommCallPara(user_name, rtu_para, zjg_id, first_flag, op_detail, err_str_1, task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_GYCOMM_SETPARA) {		//高压通讯-设参数
				String firstlast_str = firstLastFlag.trim();
				boolean first_flag = (firstlast_str.compareToIgnoreCase(SDDef.TRUE) == 0);
				
				ret_val = GyCommSet(user_name, rtu_para, zjg_id, first_flag, op_detail, err_str_1, task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_GYCOMM_CTRL) {			//高压通讯-控制
				String firstlast_str = firstLastFlag.trim();
				boolean first_flag = (firstlast_str.compareToIgnoreCase(SDDef.TRUE) == 0);
				
				ret_val = GyCommCtrl(user_name, rtu_para, zjg_id, first_flag, op_detail, err_str_1, task_result_detail);
			}
	//		else if (userop_id == ComntUseropDef.YFF_GYCOMM_CHGKEY) {		//低压通讯-下装密钥
	//			String firstlast_str = firstLastFlag.trim();
	//			boolean first_flag = (firstlast_str.compareToIgnoreCase(SDDef.TRUE) == 0);
	//			
	//			ret_val = DyCommChgKey(user_name, rtu_para, mp_id, first_flag, op_detail, err_str_1, task_result_detail);
	//		}
	
			else {
				op_detail.addTaskInfo("ERR:未知的操作类型[" + userop_id + "]");
			}
			result 			= ret_val;
			err_strinfo		= err_str_1.toString();
	//		task_resultinfo = task_result_detail;	//以后再详细描述
			detailInfo = op_detail.toJsonString();
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
	
	
	public String getGyCommReadData() {
		return gyCommReadData;
	}
	public void setGyCommReadData(String gyCommReadData) {
		this.gyCommReadData = gyCommReadData;
	}
	public String getZjgid() {
		return zjgid;
	}
	public void setZjgid(String zjgid) {
		this.zjgid = zjgid;
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
	public String getCallFlag() {
		return callFlag;
	}
	public void setCallFlag(String callFlag) {
		this.callFlag = callFlag;
	}
	public String getFirstLastFlag() {
		return firstLastFlag;
	}
	public void setFirstLastFlag(String firstLastFlag) {
		this.firstLastFlag = firstLastFlag;
	}
	public String getGyCommPay() {
		return gyCommPay;
	}
	public void setGyCommPay(String gyCommPay) {
		this.gyCommPay = gyCommPay;
	}
	public String getGyCommCallPara() {
		return gyCommCallPara;
	}
	public void setGyCommCallPara(String gyCommCallPara) {
		this.gyCommCallPara = gyCommCallPara;
	}
	public String getGyCommSetPara() {
		return gyCommSetPara;
	}
	public void setGyCommSetPara(String gyCommSetPara) {
		this.gyCommSetPara = gyCommSetPara;
	}
	public String getGyCommCtrl() {
		return gyCommCtrl;
	}
	public void setGyCommCtrl(String gyCommCtrl) {
		this.gyCommCtrl = gyCommCtrl;
	}
}
