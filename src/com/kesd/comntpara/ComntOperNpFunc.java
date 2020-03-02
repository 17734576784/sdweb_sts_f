package com.kesd.comntpara;
import java.util.ArrayList;
import com.kesd.comnt.ComntDef;
import com.kesd.comnt.ComntMsg;
import com.kesd.comnt.ComntMsgNp;
import com.kesd.comnt.ComntProtMsg;
import com.kesd.comntpara.ComntParaBase.OpDetailInfo;
import com.kesd.comntpara.ComntParaBase.SALEMAN_TASK_RESULT_DETAIL;
import com.libweb.comnt.ComntFunc;
import com.libweb.comnt.ComntVector;

public class ComntOperNpFunc {
	
	//20131019修改内容：
	//将各方法参数中的ComntMsg替换为ComntMsgNp
	
	//农排操作-开户
	public static boolean NPAddRes(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
			                       short farmerid, ComntMsgNp.MSG_NPOPER_ADDRES oper_req, ComntProtMsg.YFF_DATA_OPER_IDX oper_resul,
			                       StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail){
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
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_NPOPER_ADDRES/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
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
	

	//农排操作-缴费(有输入有输出)
	public static boolean NPPay(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
								short farmerid,ComntMsgNp.MSG_NPOPER_PAY pay_req, ComntProtMsg.YFF_DATA_OPER_IDX pay_resul,
								StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail){
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
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_NPOPER_PAY/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
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


	//农排操作-补卡
	public static boolean NPRepair(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
								short farmerid,ComntMsgNp.MSG_NPOPER_REPAIR repair_req, ComntProtMsg.YFF_DATA_OPER_IDX pay_resul,
								StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail){
		//--------1
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		repair_req.toDataStream(task_data_vect1);
		//---------1
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_NPOPER_REPAIR/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
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

	//农排操作-补写卡
	public static boolean NPReWrite(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
								short farmerid,ComntMsgNp.MSG_NPOPER_REWRITE rewrite_req, ComntProtMsg.YFF_DATA_OPER_IDX pay_resul,
								StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail){
		//--------1
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		rewrite_req.toDataStream(task_data_vect1);
		//---------1
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_NPOPER_REWRITE/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
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
	

	//农排操作-冲正
	public static boolean NPRever(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
								short farmerid,ComntMsgNp.MSG_NPOPER_REVER rever_req, ComntProtMsg.YFF_DATA_OPER_IDX rever_resul,
								StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception {
		//--------1
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		rever_req.toDataStream(task_data_vect1);
		//---------1
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_NPOPER_REVER/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
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

	
	//农排操作-强制状态更新  有输入无输出(输出为true或false)
	public static boolean NPSetUpdate(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
									short farmerid, ComntMsgNp.MSG_NPOPER_UPDATE npoper_update, StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)  throws Exception
	{
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		npoper_update.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_NPOPER_UDPATESTATE/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED)) return true;
		else return false;
	}
	
	//农排操作-获得预付费参数及状态  (无输入(输入直接在里面赋值了)有输出)
	public static boolean NPGetPayParaState(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
											short farmerid, ComntProtMsg.YFF_DATA_NPOPER_PARASTATE npoper_parastate,
											StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail)
	{
		//20131019
		ComntMsgNp.MSG_NPOPER_GETPARASTATE parastate_tx = new ComntMsgNp.MSG_NPOPER_GETPARASTATE();		
		parastate_tx.testf = 0;
		parastate_tx.gsmf  = 0;
		parastate_tx.farmerid = farmerid;
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
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_NPOPER_GPARASTATE/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_NPOPER_PARASTATE/*-----3*/) {
					npoper_parastate.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//农排操作-销户
	public static boolean NPDestory(String user_name, int user_data1, int user_data2,ComntParaBase.RTU_PARA rtu_para, 
			                        short farmerid, ComntMsgNp.MSG_NPOPER_DESTORY destoryer_req, ComntProtMsg.YFF_DATA_OPER_IDX destoryer_resul,
			                        StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail){
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		destoryer_req.toDataStream(task_data_vect1);
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_NPOPER_DESTORY/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
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
	
	//农排操作-更改用户参数  20130201
	public static boolean NPResetDoc(String user_name, int user_data1, int user_data2,ComntParaBase.RTU_PARA rtu_para, 
			                        short farmerid, ComntMsgNp.MSG_NPOPER_RESETDOC resetdoc_req, StringBuffer err_str, SALEMAN_TASK_RESULT_DETAIL task_result_detail){
		//--------1
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		resetdoc_req.toDataStream(task_data_vect1);
		//---------1
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 			task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
			
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_NPOPER_RESETDOC/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED)) return true;
		else return false;
	}	


}

