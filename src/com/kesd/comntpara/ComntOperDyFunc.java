package com.kesd.comntpara;
import java.util.ArrayList;
import com.kesd.comnt.ComntDef;
import com.kesd.comnt.ComntMsg;
import com.kesd.comnt.ComntMsgDy;
import com.kesd.comnt.ComntProtMsg;
import com.kesd.comntpara.ComntParaBase.OpDetailInfo;
import com.kesd.comntpara.ComntParaBase.SALEMAN_TASK_RESULT_DETAIL;
import com.libweb.comnt.ComntFunc;
import com.libweb.comnt.ComntVector;

public class ComntOperDyFunc {
	//20131019修改内容：
	//将所有方法中的ComntMsg.修改为了ComntMsgDy.
	
	
	//低压操作-开户
	public static boolean DYAddRes(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
			                       short mp_id, ComntMsgDy.MSG_DYOPER_ADDRES oper_req, ComntProtMsg.YFF_DATA_OPER_IDX oper_resul, StringBuffer err_str,
			                       SALEMAN_TASK_RESULT_DETAIL task_result_detail){
		//--------1                                      
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		oper_req.toDataStream(task_data_vect1);
		//---------1
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYOPER_ADDRES/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX/*-----3*/) {
					oper_resul.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//低压操作-销户
	public static boolean DYDestory(String user_name, int user_data1, int user_data2,ComntParaBase.RTU_PARA rtu_para, 
			                        short mp_id,ComntMsgDy.MSG_DYOPER_DESTORY destoryer_req, ComntProtMsg.YFF_DATA_OPER_IDX destoryer_resul, StringBuffer err_str,
			                        SALEMAN_TASK_RESULT_DETAIL 	task_result_detail){
		//--------1
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		destoryer_req.toDataStream(task_data_vect1);
		//---------1
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYOPER_DESTORY/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX/*-----3*/) {
					destoryer_resul.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}	
	
	//低压操作-低压缴费(有输入有输出)
	public static boolean DYPay(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short mp_id,ComntMsgDy.MSG_DYOPER_PAY pay_req,
								ComntProtMsg.YFF_DATA_OPER_IDX pay_resul, StringBuffer err_str,SALEMAN_TASK_RESULT_DETAIL task_result_detail){
		//--------1
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		pay_req.toDataStream(task_data_vect1);
		//---------1
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYOPER_PAY/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX/*-----3*/) {
					pay_resul.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//低压操作-补卡
	public static boolean DYRepair(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short mp_id,ComntMsgDy.MSG_DYOPER_REPAIR repair_req, 
									ComntProtMsg.YFF_DATA_OPER_IDX pay_resul, StringBuffer err_str,SALEMAN_TASK_RESULT_DETAIL task_result_detail){
		//--------1
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		repair_req.toDataStream(task_data_vect1);
		//---------1
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYOPER_REPAIR/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX/*-----3*/) {
					pay_resul.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//低压操作-补写卡
	public static boolean DYReWrite(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short mp_id,ComntMsgDy.MSG_DYOPER_REWRITE rewrite_req,
									ComntProtMsg.YFF_DATA_OPER_IDX pay_resul, StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL 	task_result_detail){
		//--------1
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		rewrite_req.toDataStream(task_data_vect1);
		//---------1
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYOPER_REWRITE/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX/*-----3*/) {
					pay_resul.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//低压操作-获取余额(有输入,有输出)
	public static boolean DYGetRemain(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
											short mp_id, ComntMsgDy.MSG_DYOPER_GETREMAIN remain_req, ComntProtMsg.YFF_DATA_OPER_GETREMAIN remain_resul,
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
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYOPER_GETREMAIN/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_GETREMAIN/*-----3*/) {
					remain_resul.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//低压操作-获取预付费信息  (无输入(输入直接在里面赋值了)有输出)
	public static boolean DYGetPayParaState(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
											short mp_id, ComntProtMsg.YFF_DATA_DYOPER_PARASTATE dyoper_parastate, StringBuffer err_str,
											SALEMAN_TASK_RESULT_DETAIL task_result_detail)
	{
		//20131019修改
		ComntMsgDy.MSG_DYOPER_GETPARASTATE parastate_tx = new ComntMsgDy.MSG_DYOPER_GETPARASTATE();		
		
		parastate_tx.testf = 0;
		parastate_tx.gsmf  = 0;
		parastate_tx.mpid = mp_id;
		ComntFunc.string2Byte(user_name, parastate_tx.operman);
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		parastate_tx.toDataStream(task_data_vect1);
		//---------1
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYOPER_GPARASTATE/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_DYOPER_PARASTATE/*-----3*/) {
					dyoper_parastate.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//低压操作-强制更新  有输入无输出(输出为true或false)
	public static boolean DySetUpdate(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short mp_id, 
										ComntMsgDy.MSG_DYOPER_UPDATE dyoper_update, StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception
	{	
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		dyoper_update.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYOPER_UDPATESTATE/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED)) return true;
		else return false;
	}

    //低压操作-换表换倍率(有输入有输出)	
	public static boolean DYChgMeter(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short mp_id,
									 ComntMsgDy.MSG_DYOPER_CHANGEMETER change_req, ComntProtMsg.YFF_DATA_OPER_IDX change_resul,
									 StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception {
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		change_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYOPER_CHANGEMETER/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX/*-----3*/) {
					change_resul.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
    
	//低压操作-换电价(有输入有输出)
	public static boolean DYChgRate(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short mp_id,
									ComntMsgDy.MSG_DYOPER_CHANGERATE chgRate_req, ComntProtMsg.YFF_DATA_OPER_IDX chgRate_resul, 
									StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception {
		
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		chgRate_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYOPER_CHANGERATE/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX/*-----3*/) {
					chgRate_resul.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}

	//低压操作-结算补差
	public static boolean DYJsbc(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short mp_id,
								 ComntMsgDy.MSG_DYOPER_JSBC dyoper_jsbc, ComntProtMsg.YFF_DATA_OPER_IDX pay_resul, StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception
	{
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		dyoper_jsbc.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYOPER_JSBC/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX) {
					pay_resul.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
    
	//低压操作-重新发行电费
	public static boolean DYReFxdf(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short mp_id,
								   ComntMsgDy.MSG_DYOPER_REFXDF dyoper_refxdf, StringBuffer err_str,SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception
	{
		
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		dyoper_refxdf.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYOPER_REFXDF/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED)) return true;
		else return false;
	}
	
	//低压操作-重新阶梯清零
	public static boolean DYReJtRest(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short mp_id, ComntMsgDy.MSG_DYOPER_REJTRESET dyoper_rejtreset,
									 StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception
	{
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		dyoper_rejtreset.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYOPER_REJTRESET/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED)) return true;
		else return false;
	}
	
	//低压操作-更改参数  预付费参数  20130201
	public static boolean DYResetDoc(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short mp_id,
									 ComntMsgDy.MSG_DYOPER_RESETDOC dyoper_resetdoc, StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception
	{
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		dyoper_resetdoc.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYOPER_RESETDOC/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED)) return true;
		else return false;
	}
	
	//低压操作-暂停
	public static boolean DYPause(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short mp_id,
								  ComntMsgDy.MSG_DYOPER_PAUSE pause_req, ComntProtMsg.YFF_DATA_OPER_IDX pause_resul,
								  StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception {
		
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		pause_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYOPER_PAUSE	/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX/*-----3*/) {
					pause_resul.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}

    //低压操作-恢复
	public static boolean DYRestart(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short mp_id,
									ComntMsgDy.MSG_DYOPER_RESTART restart_req, ComntProtMsg.YFF_DATA_OPER_IDX restart_resul,
									StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		restart_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYOPER_RESTART/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX/*-----3*/) {
					restart_resul.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}


    //低压操作-冲正
	public static boolean DYRever(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short mp_id,
								  ComntMsgDy.MSG_DYOPER_REVER rever_req, ComntProtMsg.YFF_DATA_OPER_IDX rever_resul, 
								  StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception {

		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		rever_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYOPER_REVER/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_OPER_IDX/*-----3*/) {
					rever_resul.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}

	//低压操作-保电
	public static boolean DYProtect(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short mp_id,
									ComntMsgDy.MSG_DYOPER_PROTECT dyoper_protect, StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception
	{
		
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		dyoper_protect.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYOPER_PROTECT/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED)) return true;
		else return false;
	}

    //低压操作-重新计算剩余金额
	public static boolean DYReCalc(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, short mp_id,
								   ComntMsgDy.MSG_DYOPER_RECALC dyoper_recalc, StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception
	{
		
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		dyoper_recalc.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYOPER_RECALC/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED)) return true;
		else return false;
	}

}

