package com.kesd.comntpara;

import java.sql.ResultSet;
import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.libweb.dao.JDBCDao;
import com.libweb.comnt.ComntFunc;
import com.libweb.comnt.ComntTime;
import com.libweb.comnt.ComntVector;

import com.kesd.common.CommFunc;
import com.kesd.comnt.ComntDef;

import com.kesd.comnt.ComntMsg;
import com.kesd.comnt.ComntMsgProc;
import com.kesd.comnt.ComntProtMsg;

public class ComntParaBase {

	
	public static class RTU_PARA {
		public int   rtu_id       = 0;
		public byte  prot_type    = 0;
		public short rtu_model	  = 0;
		public short timeout      = 0;
	}
	
	public static RTU_PARA loadRtuActPara(int rtu_id) {
		JDBCDao jdbc_dao = new JDBCDao();
		
		String sql = "select a.id, a.prot_type, a.rtu_model, b.timeout " + 
					" from rtupara a, chanpara b where a.id = " + rtu_id + 
					" and a.chan_main = b.id";
		
		RTU_PARA ret_para = null;
		
		try{
			ret_para = new RTU_PARA();	
			ResultSet rs = jdbc_dao.executeQuery(sql);
			
			if(rs.next()){
				ret_para.rtu_id      = rs.getInt("id");
				ret_para.prot_type   = rs.getByte("prot_type");
				ret_para.rtu_model	 = rs.getByte("rtu_model");
				ret_para.timeout     = rs.getShort("timeout");
			}
			jdbc_dao.closeRs(rs);
		}catch (Exception e) {
			ret_para = null;
			e.printStackTrace();
		}
		
		return ret_para;
	}
	
	public static int RTU_COMMON_TIMEOUT = 5 * 60;	//通讯超时时间 5min


	public static class SALEMAN_TASK_RESULT_DETAIL
	{
		public byte	taskresult	= 0;

		public int	errcode		= 0;						//父码
		public byte errproc		= 0;
		public String errhost   = "";

		//用于加密机
		public byte	errstep		= 0;						//失败步骤
		public int	errsubcode	= 0;						//错误码 子码
		public String errstr	= "";
		
		public void clear()
		{
			taskresult	= 0;

			errcode		= 0;						//父码
			errproc		= 0;
			errhost   	= "";

			//用于加密机
			errstep		= 0;						//失败步骤
			errsubcode	= 0;						//错误码 子码
			errstr		= "";
		}		
	};
	
	public static String getUserName() {			//需要从内存中读取，暂时用一个常用
		return CommFunc.getYffMan().getName();
	}
	
	public static String makeErrCode(ComntMsg.MSG_TASKRESULT task_result) {
		int errproc = (int)task_result.errproc;
		int errcode = task_result.errcode;
		//20131105修改，当为任务进程为前置机或任务分配时，解析任务进程代码
		String code_str = null, proc_str = null, host_str = null,err_code = null;
		
		if(errproc == ComntDef.YD_TASKPROC_FRONT || errproc == ComntDef.YD_TASKPROC_TASGN){
			code_str = ComntDef.getErrCodeString(errcode);
		}
		else{
			code_str = ComntDef.getYffErrCodeString(errcode);
		}
		
		err_code = " [" + errcode +  "]";
		proc_str = ComntDef.getErrProcString(errproc);
		host_str = ComntFunc.byte2String(task_result.errhost);
		
		return "" + "  描述:[" + (code_str + err_code) + "]  进程:[" + proc_str + "]  节点:[" + host_str + "]";
	}
	
	//20131128 zhp 增加主站操作错误时错误码组成。
	public static String makeOperErrCode(SALEMAN_TASK_RESULT_DETAIL task_result) {
		int errproc = (int)task_result.errproc;
		int errcode = task_result.errcode;
	
		String code_str = null,err_code = null;
		
		if(errproc == ComntDef.YD_TASKPROC_FRONT || errproc == ComntDef.YD_TASKPROC_TASGN){
			code_str = ComntDef.getErrCodeString(errcode);
		}
		else{
			code_str = ComntDef.getYffErrCodeString(errcode);
		}
		err_code = " [" + errcode +  "]";
		
		return "错误描述:" + (code_str + err_code);
	}
	//end
	
	public static String makeErrCode(ComntProtMsg.YFF_DATAYESNOMSG data_result, String ret_str)
	{
		return "ERR: 步骤:[" + data_result.errstep + "]  错误码:[" + data_result.errcode + ":" + data_result.errsubcode + "]  描述:[" + data_result.text + "]";
	}
	
	public static boolean get1StepTaskResult(String version, String user_name, int userData1, int userData2, RTU_PARA rtu_para,
									  byte task_apptype, byte task_assigntype, ComntVector.ByteVector task_data_vect,  
									  byte[] task_result, ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect, OpDetailInfo detail_info,
									  StringBuffer err_str_1/* = NULL*/, SALEMAN_TASK_RESULT_DETAIL task_result_detail/* = NULL*/) {
		
		return get1StepTaskResult_Retry(version, user_name, userData1, userData2, rtu_para, task_apptype, task_assigntype, task_data_vect, 0,
				task_result, ret_data_vect, detail_info, err_str_1, task_result_detail);
	}

	public static boolean get1StepTaskResult_Retry(String version, String user_name, int userData1, int userData2, RTU_PARA rtu_para,
									  byte task_apptype, byte task_assigntype, ComntVector.ByteVector task_data_vect, int retry_times,  
									  byte[] task_result, ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect, OpDetailInfo detail_info,
									  StringBuffer err_str_1/* = NULL*/, SALEMAN_TASK_RESULT_DETAIL task_result_detail/* = NULL*/) {
		
		task_result[0] = (byte)ComntDef.YD_YFF_TASKRESULT_NULL;
		if (task_result_detail != null) task_result_detail.taskresult = task_result[0];
		
		int uuid     = ComntMsgProc.getNewUUID();
		int time_out = RTU_COMMON_TIMEOUT;
		
		ComntMsg.MSG_CLIENT_STRUCT comnt_msg = new ComntMsg.MSG_CLIENT_STRUCT();

		ComntMsg.makeTaskComntMsg(comnt_msg,								//要发送的消息 
				 uuid, 										//消息的UUID
				 time_out, 									//等待超时
				 user_name, 								//操作的用户名称,用于记录日志
				 version,									//版本信息
				 (byte)task_apptype, 						//任务的应用类型-设置参数的任务
				 (byte)task_assigntype, 					//将被分配到前置的手工任务区
				 rtu_para.rtu_id, 							//终端ID
				 (byte)rtu_para.prot_type, 					//终端的规约类型
				 (byte)retry_times, 						//重试次数,0-不重试
				 (byte)ComntDef.TRUE, 						//是否返回任务结果
				 (byte)ComntDef.YD_YFF_TASK_BACKDATA_SRC, 	//返回数据方向,返回给发送端即WEB客户端
				 (byte)ComntDef.TRUE, 						//是否返回规约报文
				 (byte)ComntDef.TRUE, 						//规约报文是否解析
				 task_data_vect);							//任务结构转化成的字节流
				
		int i_user_data1 = userData1;	//RTU ID
		int i_user_data2 = userData2;	//USEROPER ID

		//将任务放入发送队列
		boolean add_val = ComntMsgProc.addWaitSendMsg(comnt_msg.msg_head, comnt_msg.msg_body, i_user_data1, i_user_data2);
		if (!add_val) {
			detail_info.addTaskInfo("ERR:放入发送队列发生错误");
			return false;
		}

		ArrayList<ComntMsg.MSG_SERVER_STRUCT> retmsg_vect = new ArrayList<ComntMsg.MSG_SERVER_STRUCT>();
		int msg_step = ComntMsgProc.MSGSTEP_END;
		
		//等待返回结果
		int ferr_count = 0;
		do {
			ComntMsgProc.waitMsg();			
			msg_step = ComntMsgProc.getRecvMsg(comnt_msg.msg_head.uuid, 
											   true, 		//是否一次接受所有返回数据
											   retmsg_vect);
			
			if (++ferr_count >= 7200) break;
		} while (msg_step < ComntMsgProc.MSGSTEP_FINISH);

		if (++ferr_count >= 7200) {	//1h
			detail_info.addTaskInfo("ERR:等待超时");			
			return false;
		}
		
		if (msg_step == ComntMsgProc.MSGSTEP_END) {
			detail_info.addTaskInfo("ERR:任务被删除");
			if (err_str_1 != null) err_str_1.append("ERR:任务被删除");
			return false;
		}
		
		int tmp_i = 0;
		ComntMsg.MSG_SERVER_STRUCT ret_msg = null;
		
		//数据结果
		ret_data_vect.clear();
		ComntMsg.MSG_RESULT_DATA result_data = new ComntMsg.MSG_RESULT_DATA(); 		
		for (tmp_i = 0; tmp_i < retmsg_vect.size(); tmp_i++) {
			ret_msg = retmsg_vect.get(tmp_i);
			if (ret_msg.msg_head.msg_type != ComntDef.YD_YFF_MSGTYPE_DATA) continue;
			result_data.fromDataStream(ret_msg.msg_body.byte_vect, 0);
			ret_data_vect.add(result_data.clone());
		}

		//规约及解析报文
//		ComntMsg.MSG_RESULT_RAWDATA raw_data = new ComntMsg.MSG_RESULT_RAWDATA(); 
//		for (tmp_i = 0; tmp_i < retmsg_vect.size(); tmp_i++) {
//			ret_msg = retmsg_vect.get(tmp_i);
//			if (ret_msg.msg_head.msg_type == ComntDef.YD_YFF_MSGTYPE_RAWDATA) {
//				raw_data.clean();
//				raw_data.fromDataStream(ret_msg.msg_body.byte_vect, 0);
//				
//				if (raw_data.rawdatatype == ComntProtMsg.YFF_RAWTYPE_DATARX) {
//					ComntProtMsg.YFF_RAWDATARX  rx = new ComntProtMsg.YFF_RAWDATARX();
//					
//					rx.fromDataStream(raw_data.data_buf, 0);
//					detail_info.addRxInfo(ComntTime.cTime(), rx.data_buf.getaddr());
//				}
//				else if (raw_data.rawdatatype == ComntProtMsg.YFF_RAWTYPE_DATATX) {
//					ComntProtMsg.YFF_RAWDATATX tx = new ComntProtMsg.YFF_RAWDATATX();
//					
//					tx.fromDataStream(raw_data.data_buf, 0);
//					detail_info.addTxInfo(ComntTime.cTime(), tx.data_buf.getaddr());
//				}
//				else if (raw_data.rawdatatype == ComntProtMsg.YFF_RAWTYPE_DATACRYP) {
//					ComntProtMsg.YFF_RAWDATACRYP cryp = new ComntProtMsg.YFF_RAWDATACRYP();
//					
//					cryp.fromDataStream(raw_data.data_buf, 0);
//					detail_info.addCrypInfo(ComntTime.cTime(), cryp.data_buf.getaddr());			
//				}				
//			}
//		}
		
		//判断任务结果
		for (tmp_i = 0; tmp_i < retmsg_vect.size(); tmp_i++) {
			ret_msg = retmsg_vect.get(tmp_i);
			if (ret_msg.msg_head.msg_type == ComntDef.YD_YFF_MSGTYPE_TASKRESULT) break;
		}

		if (tmp_i < retmsg_vect.size()) {	//无任务结果 退出
			ComntMsg.MSG_TASKRESULT result_msg = new ComntMsg.MSG_TASKRESULT();
			result_msg.fromDataStream(ret_msg.msg_body.byte_vect, 0);
//			task_result[0] = result_msg.taskresult;
			
			if (task_result_detail != null) {
				task_result_detail.errcode	= result_msg.errcode;
				task_result_detail.errproc	= result_msg.errproc;
				task_result_detail.errhost	= ComntFunc.byte2String(result_msg.errhost);
				task_result_detail.errstep  = result_msg.errstep;
				task_result_detail.errsubcode  = result_msg.errsubcode;
				task_result_detail.errstr  = ComntFunc.byte2String(result_msg.errstr);
			}
			
			byte sec_flag = ComntDef.YD_YFF_TASKRESULT_SUCCEED;
			//任务执行失败
			if (result_msg.taskresult != ComntDef.YD_YFF_TASKRESULT_SUCCEED) {		//执行失败 退出
				sec_flag = ComntDef.YD_YFF_TASKRESULT_FAILED;
			}
			
			if (result_msg.taskresult == ComntDef.YD_YFF_TASKRESULT_SUCCEED) {
				if(result_msg.errcode == 0) {		//执行成功
					sec_flag  = ComntDef.YD_YFF_TASKRESULT_SUCCEED;
				}
				else {
					sec_flag  = ComntDef.YD_YFF_TASKRESULT_FAILED;
				}
			}
			task_result[0] = (sec_flag == ComntDef.YD_YFF_TASKRESULT_SUCCEED) ? (byte)ComntDef.YD_YFF_TASKRESULT_SUCCEED : (byte)ComntDef.YD_YFF_TASKRESULT_FAILED;
			
			if(sec_flag == ComntDef.YD_YFF_TASKRESULT_FAILED){
				String err_str = "ERR:任务执行失败." + makeErrCode(result_msg);
				detail_info.addTaskInfo(err_str);
				
				if (err_str_1 != null) {
					err_str_1.append(err_str);
				} 
				return false;
			}
		}		
		return true;
	}
	
	
	
	public static boolean sendNStepTask(String version, String user_name, int userData1, int userData2, RTU_PARA rtu_para,
			  							byte task_apptype, byte task_assigntype, ComntVector.ByteVector task_data_vect, OpDetailInfo detail_info) {
		
		return sendNStepTask_Retry(version, user_name, userData1, userData2, rtu_para,
			  						task_apptype, task_assigntype, task_data_vect, 0, detail_info);
	}
	

	public static boolean sendNStepTask_Retry(String version, String user_name, int userData1, int userData2, RTU_PARA rtu_para,
			  							byte task_apptype, byte task_assigntype, ComntVector.ByteVector task_data_vect, int retry_times, OpDetailInfo detail_info) {
		
		int uuid     = ComntMsgProc.getNewUUID();
		int time_out = RTU_COMMON_TIMEOUT;
		
		ComntMsg.MSG_CLIENT_STRUCT comnt_msg = new ComntMsg.MSG_CLIENT_STRUCT();

		ComntMsg.makeTaskComntMsg(comnt_msg,				//要发送的消息 
				 uuid, 										//消息的UUID
				 time_out, 									//等待超时
				 user_name, 								//操作的用户名称,用于记录日志
				 version,									//版本信息
				 (byte)task_apptype, 						//任务的应用类型-设置参数的任务
				 (byte)task_assigntype, 					//将被分配到前置的手工任务区
				 rtu_para.rtu_id, 							//终端ID
				 (byte)rtu_para.prot_type, 					//终端的规约类型
				 (byte)retry_times, 						//重试次数,0-不重试
				 (byte)ComntDef.TRUE, 						//是否返回任务结果
				 (byte)ComntDef.YD_YFF_TASK_BACKDATA_SRC, 	//返回数据方向,返回给发送端即WEB客户端
				 (byte)ComntDef.TRUE, 						//是否返回规约报文
				 (byte)ComntDef.TRUE, 						//规约报文是否解析
				 task_data_vect);							//任务结构转化成的字节流
				
		int i_user_data1 = userData1;	//RTU ID
		int i_user_data2 = userData2;	//USEROPER ID

		//将任务放入发送队列
		boolean add_val = ComntMsgProc.addWaitSendMsg(comnt_msg.msg_head, comnt_msg.msg_body, i_user_data1, i_user_data2);
		if (!add_val) {
			detail_info.addTaskInfo("ERR:放入发送队列发生错误");
			return false;
		}
		else return true;
	}

	
	public static boolean getNStepTaskResult(String user_name, int userData1, int userData2, RTU_PARA rtu_para,  
									  		byte[] task_result, ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect, OpDetailInfo detail_info,
									  		StringBuffer err_str_1/* = NULL*/, SALEMAN_TASK_RESULT_DETAIL task_result_detail/* = NULL*/) {
		
		task_result[0] = (byte)ComntDef.YD_YFF_TASKRESULT_NULL;
		if (task_result_detail != null) task_result_detail.taskresult = task_result[0];
		
		ArrayList<ComntMsg.MSG_SERVER_STRUCT> retmsg_vect = new ArrayList<ComntMsg.MSG_SERVER_STRUCT>();
		int msg_step = ComntMsgProc.MSGSTEP_END;
		
		//等待返回结果
		int ferr_count = 0;
		do {
			ComntMsgProc.waitMsg();			
			msg_step = ComntMsgProc.getRecvMsg(user_name, userData1, userData2,
											   false, 		//是否一次接受所有返回数据
											   retmsg_vect);
			
			if (msg_step >= ComntMsgProc.MSGSTEP_FINISH) break;
			if (retmsg_vect.size() > 0) break;
			
			if (++ferr_count >= 7200) break;
		} while (true);

		if (++ferr_count >= 7200) {	//1h
			detail_info.addTaskInfo("ERR:等待超时");		
			if (err_str_1 != null) err_str_1.append("ERR:等待超时");
			return false;
		}
		
		if (msg_step == ComntMsgProc.MSGSTEP_END) {
			detail_info.addTaskInfo("ERR:任务被删除");
			if (err_str_1 != null) err_str_1.append("ERR:任务被删除");
			return false;
		}
		
		int tmp_i = 0;
		ComntMsg.MSG_SERVER_STRUCT ret_msg = null;
		
		//数据结果
		ret_data_vect.clear();
		ComntMsg.MSG_RESULT_DATA result_data = new ComntMsg.MSG_RESULT_DATA(); 		
		for (tmp_i = 0; tmp_i < retmsg_vect.size(); tmp_i++) {
			ret_msg = retmsg_vect.get(tmp_i);
			if (ret_msg.msg_head.msg_type != ComntDef.YD_YFF_MSGTYPE_DATA) continue;
			result_data.fromDataStream(ret_msg.msg_body.byte_vect, 0);
			ret_data_vect.add(result_data.clone());
		}

		//规约及解析报文
		ComntMsg.MSG_RESULT_RAWDATA raw_data = new ComntMsg.MSG_RESULT_RAWDATA(); 
		for (tmp_i = 0; tmp_i < retmsg_vect.size(); tmp_i++) {
			ret_msg = retmsg_vect.get(tmp_i);
			if (ret_msg.msg_head.msg_type == ComntDef.YD_YFF_MSGTYPE_RAWDATA) {
				raw_data.clean();
				raw_data.fromDataStream(ret_msg.msg_body.byte_vect, 0);
				
				if (raw_data.rawdatatype == ComntProtMsg.YFF_RAWTYPE_DATARX) {
					ComntProtMsg.YFF_RAWDATARX  rx = new ComntProtMsg.YFF_RAWDATARX();
					
					rx.fromDataStream(raw_data.data_buf, 0);
					detail_info.addRxInfo(ComntTime.cTime(), rx.data_buf.getaddr());
				}
				else if (raw_data.rawdatatype == ComntProtMsg.YFF_RAWTYPE_DATATX) {
					ComntProtMsg.YFF_RAWDATATX tx = new ComntProtMsg.YFF_RAWDATATX();
					
					tx.fromDataStream(raw_data.data_buf, 0);
					detail_info.addTxInfo(ComntTime.cTime(), tx.data_buf.getaddr());
				}
				else if (raw_data.rawdatatype == ComntProtMsg.YFF_RAWTYPE_DATACRYP) {
					ComntProtMsg.YFF_RAWDATACRYP cryp = new ComntProtMsg.YFF_RAWDATACRYP();
					
					cryp.fromDataStream(raw_data.data_buf, 0);
					detail_info.addCrypInfo(ComntTime.cTime(), cryp.data_buf.getaddr());			
				}				
			}
		}
		
		//判断任务结果
		for (tmp_i = 0; tmp_i < retmsg_vect.size(); tmp_i++) {
			ret_msg = retmsg_vect.get(tmp_i);
			if (ret_msg.msg_head.msg_type == ComntDef.YD_YFF_MSGTYPE_TASKRESULT) break;
		}

		if (tmp_i < retmsg_vect.size()) {	//无任务结果 退出
			ComntMsg.MSG_TASKRESULT result_msg = new ComntMsg.MSG_TASKRESULT();
			result_msg.fromDataStream(ret_msg.msg_body.byte_vect, 0);
//			task_result[0] = result_msg.taskresult;
			
			if (task_result_detail != null) {
				task_result_detail.errcode	= result_msg.errcode;
				task_result_detail.errproc	= result_msg.errproc;
				task_result_detail.errhost	= ComntFunc.byte2String(result_msg.errhost);
				task_result_detail.errstep  = result_msg.errstep;
				task_result_detail.errsubcode  = result_msg.errsubcode;
				task_result_detail.errstr  = ComntFunc.byte2String(result_msg.errstr);
			}
			
			byte sec_flag = ComntDef.YD_YFF_TASKRESULT_SUCCEED;
			//任务执行失败
			if (result_msg.taskresult != ComntDef.YD_YFF_TASKRESULT_SUCCEED) {		//执行失败 退出
				sec_flag = ComntDef.YD_YFF_TASKRESULT_FAILED;
			}
			
			if (result_msg.taskresult == ComntDef.YD_YFF_TASKRESULT_SUCCEED) {
				if(result_msg.errcode == 0) {		//执行成功
					sec_flag  = ComntDef.YD_YFF_TASKRESULT_SUCCEED;
				}
				else {
					sec_flag  = ComntDef.YD_YFF_TASKRESULT_FAILED;
				}
			}
			task_result[0] = (sec_flag == ComntDef.YD_YFF_TASKRESULT_SUCCEED) ? (byte)ComntDef.YD_YFF_TASKRESULT_SUCCEED : (byte)ComntDef.YD_YFF_TASKRESULT_FAILED;
			
			if(sec_flag == ComntDef.YD_YFF_TASKRESULT_FAILED){
				String err_str = "ERR:任务执行失败." + makeErrCode(result_msg);
				detail_info.addTaskInfo(err_str);
				
				if (err_str_1 != null) {
					err_str_1.append(err_str);
				} 
				return false;
			}
		}		
		return true;
	}
	
	public static class OpDetailInfo {
		public final static int TYPE_TASK = 0;	//任务执行信息
		public final static int TYPE_RX	  = 1;	//接收的报文
		public final static int TYPE_TX	  = 2;	//发送的报文
		public final static int TYPE_RXJX = 3;	//接收报文的解析
		public final static int TYPE_TXJX = 4;	//发送报文的解析
		public final static int TYPE_DB   = 5;	//数据库信息
		public final static int TYPE_USER = 6;	//用户信息
		public final static int TYPE_OTHER= 7;	//其他信息
		public final static int TYPE_CRYP =	8;	//加密机报文
		
		public static class ITEM {
			public int    type = 0;
			public int    time = 0;
			public int    data_len = 0;
			public String data = null;
		}
		
		public ArrayList<ITEM> item_list = new ArrayList<ITEM>();
		
		public void addTaskInfo(String str) {
			ITEM item = new ITEM();
			item.type = TYPE_TASK;
			item.time = ComntTime.cTime();
			item.data_len = 0;
			item.data = str;
			item_list.add(item);
		}
		
		public void addCrypInfo(int data_time, byte[] datas)
		{
			ITEM item = new ITEM();
			item.type = TYPE_CRYP;
			item.time = data_time;
			item.data_len = datas==null ? 0 : datas.length;
			item.data = ComntFunc.printProtalData(datas);
			item_list.add(item);
		}
		
		
		public void addRxInfo(int data_time, byte[] datas) {
			ITEM item = new ITEM();
			item.type = TYPE_RX;
			item.time = data_time;
			item.data_len = datas==null ? 0 : datas.length; //datas.length;
			item.data = ComntFunc.printProtalData(datas);
			item_list.add(item);
		}
		
		public void addTxInfo(int data_time, byte[] datas) {
			ITEM item = new ITEM();
			item.type = TYPE_TX;
			item.time = data_time;
			item.data_len =  datas==null ? 0 : datas.length;//datas.length;
			item.data = ComntFunc.printProtalData(datas);
			item_list.add(item);
		}
		
		public void addRxJxInfo(int data_time, String str) {
			ITEM item = new ITEM();
			item.type = TYPE_RXJX;
			item.time = data_time;
			item.data_len = 0;
			item.data = str;
			item_list.add(item);
		}		
		
		public void addTxJxInfo(int data_time, String str) {
			ITEM item = new ITEM();
			item.type = TYPE_TXJX;
			item.time = data_time;
			item.data_len = 0;
			item.data = str;
			item_list.add(item);
		}
		
		public void addDbInfo(String str) {
			ITEM item = new ITEM();
			item.type = TYPE_DB;
			item.time = ComntTime.cTime();
			item.data_len = 0;
			item.data = str;
			item_list.add(item);
		}
		
		public void addInfo(String str) {
			ITEM item = new ITEM();
			item.type = TYPE_USER;
			item.time = ComntTime.cTime();
			item.data_len = 0;
			item.data = str;
			item_list.add(item);
		}
		
		public void addOtherInfo(String str) {
			ITEM item = new ITEM();
			item.type = TYPE_OTHER;
			item.time = ComntTime.cTime();
			item.data_len = 0;
			item.data = str;
			item_list.add(item);
		}
		
		public String toJsonString() {
			JSONArray j_obj_array = new JSONArray();			
			JSONObject j_obj = null;

			ITEM item = null;
			
			for (int i = 0; i < item_list.size(); i++) {
				item = item_list.get(i);
				
				j_obj = new JSONObject();
				
				j_obj.put("TYPE",    String.valueOf(item.type));
				
				ComntTime.TM data_time = ComntTime.localCTime(item.time);				
				String str_time = String.format("%02d/%02d %02d:%02d:%02d", 
												(int)data_time.month + 1, (int)data_time.mday, 
												(int)data_time.hour, (int)data_time.minute, (int)data_time.secend);

				j_obj.put("TIME",    str_time);
				j_obj.put("DATALEN", String.valueOf(item.data_len));
				j_obj.put("DATA",    item.data);

				j_obj_array.add(j_obj);				
			}

			j_obj = new JSONObject();
			j_obj.put("OPDETAIL", j_obj_array);

			return j_obj.toString();
		}
	}
	
}
