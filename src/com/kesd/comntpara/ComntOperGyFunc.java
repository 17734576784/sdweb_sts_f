package com.kesd.comntpara;
import java.util.ArrayList;
import com.kesd.comnt.ComntDef;
import com.kesd.comnt.ComntMsg;
import com.kesd.comnt.ComntMsgGy;
import com.kesd.comnt.ComntProtMsg;
import com.kesd.comntpara.ComntParaBase.OpDetailInfo;
import com.kesd.comntpara.ComntParaBase.SALEMAN_TASK_RESULT_DETAIL;
import com.libweb.comnt.ComntFunc;
import com.libweb.comnt.ComntVector;

public class ComntOperGyFunc {
	//20131019修改内容：
	//将各方法参数中ComntMsg替换为ComntMsgGy
	
	//高压操作-开户
	public static boolean GYAddRes(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
			                       short zjg_id, ComntMsgGy.MSG_GYOPER_ADDCUS oper_req, ComntProtMsg.YFF_DATA_OPER_IDX oper_result,
			                       StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail){

		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		oper_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYOPER_ADDCUS/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX/*-----3*/) {
					oper_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//高压操作-销户
	public static boolean GYDestory(String user_name, int user_data1, int user_data2,ComntParaBase.RTU_PARA rtu_para, 
			                        short zjg_id,ComntMsgGy.MSG_GYOPER_DESTORY destoryer_req, ComntProtMsg.YFF_DATA_OPER_IDX destoryer_result,
			                        StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail){

		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		destoryer_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYOPER_DESTORY, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX/*-----3*/) {
					destoryer_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//高压操作-补卡   author ylc
	public static boolean GYRepair(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short zjg_id,
								   ComntMsgGy.MSG_GYOPER_REPAIR repair_req, ComntProtMsg.YFF_DATA_OPER_IDX pay_result, StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail){
		
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		repair_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYOPER_REPAIR, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX/*-----3*/) {
					pay_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//高压操作-高压缴费(有输入有输出)
	public static boolean GYPay(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
								short zjg_id,ComntMsgGy.MSG_GYOPER_PAY pay_req, ComntProtMsg.YFF_DATA_OPER_IDX pay_result,
								StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail){
		
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		pay_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYOPER_PAY/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX/*-----3*/) {
					pay_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//高压操作-获取余额(有输入,有输出)
	public static boolean GYGetRemain(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
											short zjg_id, ComntMsgGy.MSG_GYOPER_GETREMAIN remain_req, ComntProtMsg.YFF_DATA_OPER_GETREMAIN remain_result,
											StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)
	{
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		remain_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYOPER_GETREMAIN/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_GETREMAIN/*-----3*/) {
					remain_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//高压操作-获取结算补差余额(有输入,有输出)
	public static boolean GYGetJSBCRemain(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
											short zjg_id, ComntMsgGy.MSG_GYOPER_GETJSBCREMAIN remain_req, ComntProtMsg.YFF_DATA_OPER_GETJSBCREMAIN remain_result,
											StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)
	{
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		remain_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYOPER_GETJSBCREMAIN/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_GETJSBCREMAIN/*-----3*/) {
					remain_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//高压操作-获取预付费信息  (无输入(输入直接在里面赋值了)有输出)
	public static boolean GYGetPayParaState(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
											short zjg_id, ComntProtMsg.YFF_DATA_GYOPER_PARASTATE gyoper_parastate, StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)
	{
		//20131019修改		
		ComntMsgGy.MSG_GYOPER_GETPARASTATE parastate_tx = new ComntMsgGy.MSG_GYOPER_GETPARASTATE();
		
		parastate_tx.testf = 0;
		parastate_tx.gsmf  = 0;
		parastate_tx.zjgid = zjg_id;
		ComntFunc.string2Byte(user_name, parastate_tx.operman);
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		parastate_tx.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYOPER_GPARASTATE/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_GYOPER_PARASTATE/*-----3*/) {
					gyoper_parastate.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//高压操作-强制更新  有输入无输出(输出为true或false)
	public static boolean GYUpdateParaState(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
											short zjg_id, ComntMsgGy.MSG_GYOPER_UPDATE gyoper_update, StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception
	{
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		gyoper_update.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYOPER_UDPATESTATE, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED)) return true;
		else return false;
	}

    //高压操作-换表换倍率(有输入有输出)	
	public static boolean GYChgMeter(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short zjg_id,
									 ComntMsgGy.MSG_GYOPER_CHANGEMETER change_req, ComntProtMsg.YFF_DATA_OPER_IDX change_result,
									 StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception {
		
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		change_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYOPER_CHANGEMETER, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX/*-----3*/) {
					change_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
    
	//高压操作-换电价(有输入有输出)
	public static boolean GYChgRate(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short zjg_id,
									ComntMsgGy.MSG_GYOPER_CHANGERATE chgRate_req, ComntProtMsg.YFF_DATA_OPER_IDX chgRate_result,
									StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception {
		
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		chgRate_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYOPER_CHANGERATE, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX/*-----3*/) {
					chgRate_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}

	//高压操作-结算补差
	public static boolean GYReJs(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short zjg_id,
								 ComntMsgGy.MSG_GYOPER_REJS gyoper_rejs, ComntProtMsg.YFF_DATA_OPER_IDX pay_result,
								 StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception
	{
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		gyoper_rejs.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYOPER_REJS, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX) {
					pay_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
    
	//高压操作-暂停
	public static boolean GYPause(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short zjg_id,
			ComntMsgGy.MSG_GYOPER_PAUSE pause_req, ComntProtMsg.YFF_DATA_OPER_IDX pause_result, StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception {

		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		pause_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYOPER_PAUSE, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX/*-----3*/) {
					pause_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}

    //高压操作-恢复
	public static boolean GYRestart(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short zjg_id,
									ComntMsgGy.MSG_GYOPER_RESTART restart_req, ComntProtMsg.YFF_DATA_OPER_IDX restart_result,
									StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {

		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		restart_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYOPER_RESTART, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX/*-----3*/) {
					restart_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}

	//高压操作-换基本费
	public static boolean GYChgPayAdd(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short zjg_id,
									  ComntMsgGy.MSG_GYOPER_CHANGPAYADD payAdd_req, ComntProtMsg.YFF_DATA_OPER_IDX payAdd_result,
									  StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception {
		
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		payAdd_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYOPER_CHANGPAYADD/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX/*-----3*/) {
					payAdd_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}

    //高压操作-冲正
	public static boolean GYRever(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short zjg_id,
								  ComntMsgGy.MSG_GYOPER_REVER rever_req, ComntProtMsg.YFF_DATA_OPER_IDX rever_result,
								  StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception {
		
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		rever_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYOPER_REVER/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX/*-----3*/) {
					rever_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}

	//高压操作-保电
	public static boolean GYProtect(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short zjg_id,
									ComntMsgGy.MSG_GYOPER_PROTECT gyoper_protect, StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception
	{
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		gyoper_protect.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYOPER_PROTECT/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED)) return true;
		else return false;
	}

    //高压操作-重新计算剩余金额
	public static boolean GYReCalc(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short zjg_id,
								   ComntMsgGy.MSG_GYOPER_RECALC gyoper_recalc, StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception
	{
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		gyoper_recalc.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYOPER_RECALC/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED)) return true;
		else return false;
	}
	
	//高压操作-更改参数  预付费参数  20130201
	public static boolean GYResetDoc(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short zjg_id, 
			ComntMsgGy.MSG_GYOPER_RESETDOC gyoper_resetdoc, StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception
	{
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		gyoper_resetdoc.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYOPER_RESETDOC/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED)) return true;
		else return false;
	}
	
	
	//得到-暂停预付费报警及控制
	public static boolean GetPauseAlarm(String user_name, int user_data2, ComntMsg.MSG_GET_PAUSEALARM pausealarm_req,
										ComntProtMsg.YFF_DATA_OPER_PAUSEALARM pausealarm_result, StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception {
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		pausealarm_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		ComntParaBase.RTU_PARA rtu_para = new ComntParaBase.RTU_PARA();
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, 0, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GET_PAUSEALARM, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_PAUSEALARM) {
					pausealarm_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//设置-暂停预付费报警及控制
	public static boolean SetPauseAlarm(String user_name, int user_data2, ComntMsg.MSG_SET_PAUSEALARM pausealarm_req,
										StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception
	{
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		pausealarm_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		ComntParaBase.RTU_PARA rtu_para = new ComntParaBase.RTU_PARA();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, 0, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_SET_PAUSEALARM/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED)) return true;
		else return false;
	}
	
	//得到-全局保电
	public static boolean GetGloProt(String user_name, int user_data2, ComntMsg.MSG_GET_GLOPROT gloprot_req,
									 ComntProtMsg.YFF_DATA_OPER_GLOPROT gloprot_result, StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception {
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		gloprot_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		ComntParaBase.RTU_PARA rtu_para = new ComntParaBase.RTU_PARA();
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, 0, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GET_GLOPROT, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_GLOPROT/*-----3*/) {
					gloprot_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//设置-全局保电
	public static boolean SetGloProt(String user_name, int user_data2, ComntMsg.MSG_SET_GLOPROT gloprot_req,
									 StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception
	{
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		gloprot_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		ComntParaBase.RTU_PARA rtu_para = new ComntParaBase.RTU_PARA();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, 0, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_SET_GLOPROT/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED)) return true;
		else return false;
	}	
}

