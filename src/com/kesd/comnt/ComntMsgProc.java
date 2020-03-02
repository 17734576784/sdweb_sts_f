package com.kesd.comnt;

import java.util.LinkedList;
import java.util.ArrayList;

import com.kesd.util.OnlineRtu;
import com.libweb.comnt.*;

public class ComntMsgProc {
	public static final int MSG_MAX_TIMEOUT		= 300;			//等待超时5min
	public static final int MSG_DEFAULT_CLIENT_WAIT_NUM	= 610;	//客户端默认等待时间单位数量500ms * 610
	
	public static final int MAX_MSGBUF_NUM 		= 1024;			//最大消息缓存
	
	//消息状态
	public static final int MSGSTEP_WAITSEND 	= 0;			//等待发送
	public static final int MSGSTEP_RECVING	 	= 1;			//正在接收
	public static final int MSGSTEP_FINISH 	 	= 2;			//完成--数据接收完毕
	public static final int MSGSTEP_END	 	 	= 3;			//结束--删除消息

	public static class ComntNetMsg {
		public int     time_cnt      = 0;
		public int     cur_step      = 0;
		public int     user_id1		 = 0;
		public int 	   user_id2		 = 0;
		
		public boolean cancel_flag	 = false;
		
		public ComntMsg.MSG_CLIENT_STRUCT 				msg_send 	 = null;
		public ArrayList<ComntMsg.MSG_SERVER_STRUCT> 	msg_recvvect = null;
	}

	private LinkedList<ComntNetMsg> msg_list = new LinkedList<ComntNetMsg>();

	public LinkedList<ComntNetMsg> getMsgList() {
		return msg_list;
	}

	private boolean isListFull() {
		if (msg_list.size() >= MAX_MSGBUF_NUM) return true;
		else return false;
	}

	private int findNetMsg(int uuid) {
		ComntNetMsg h_msg = null;
		
		for (int i = 0; i < msg_list.size(); i++) {
			h_msg = msg_list.get(i);
			if (h_msg.msg_send.msg_head.uuid == uuid) return i;
		}
		
		return -1;
	}

	private ComntNetMsg findNoFinishedNetMsg(String user_name, int user_id1, int user_id2) {
		ComntNetMsg h_msg = null;
		
		String str;
		
		for (int i = 0; i < msg_list.size(); i++) {
			h_msg = msg_list.get(i);
			if (h_msg.cur_step >= MSGSTEP_FINISH) continue;
			
			if (user_id1 != h_msg.user_id1 || user_id2 != h_msg.user_id2) continue;			
			str = ComntFunc.byte2String(h_msg.msg_send.msg_head.user_name);

			if (user_name.compareToIgnoreCase(str) == 0) return h_msg;
		}
		
		return null;
	}
	
	private ComntNetMsg findNoEndNetMsg(String user_name, int user_id1, int user_id2) {
		ComntNetMsg h_msg = null;
		
		String str;
		
		for (int i = 0; i < msg_list.size(); i++) {
			h_msg = msg_list.get(i);
			if (h_msg.cur_step >= MSGSTEP_END) continue;
			
			if (user_id1 != h_msg.user_id1 || user_id2 != h_msg.user_id2) continue;			
			str = ComntFunc.byte2String(h_msg.msg_send.msg_head.user_name);
						
			if (user_name.compareToIgnoreCase(str) == 0) return h_msg;
		}
		
		return null;
	}	
	
	private static boolean isMsgRecvFinish(int msg_type) {
		if ((msg_type == ComntDef.YD_YFF_MSGTYPE_TASKRESULT) ||
			(msg_type == ComntDef.YD_YFF_MSGTYPE_RTUSTATE)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private static boolean isASendMsg(int msg_type) {
		if ((msg_type == ComntDef.YD_YFF_MSGTYPE_TASK) ||
//			(msg_type == ComntDef.YD_YFF_MSGTYPE_QUERYRTUSTATE) ||
			(msg_type == ComntDef.YD_YFF_MSGTYPE_RELOADPARA)) {
			return true;
		}
		else return false;		
	}
	
	private static boolean isARecvMsg(int msg_type) {
		if ((msg_type == ComntDef.YD_YFF_MSGTYPE_TASKRESULT) ||
			(msg_type == ComntDef.YD_YFF_MSGTYPE_DATA) ||
			(msg_type == ComntDef.YD_YFF_MSGTYPE_RAWDATA) ||
			(msg_type == ComntDef.YD_YFF_MSGTYPE_RTUSTATE)) {
				return true;
			}
			else return false;
	}
	
	private static boolean isAOnlySendMsg(int msg_type) {
		if (msg_type == ComntDef.YD_YFF_MSGTYPE_RELOADPARA) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean endAlreadyInBufferMsg(int msg_type, byte []user_name, int user_id1, int user_id2) {
		if (msg_type != ComntDef.YD_YFF_MSGTYPE_TASK) return true;
		
		String str = ComntFunc.byte2String(user_name);
		
		for (int i = 0; i< 10; i++) { //容错
			ComntNetMsg h_msg = findNoEndNetMsg(str, user_id1, user_id2);
			
			if (h_msg == null) return true;
			else if (h_msg.cur_step < MSGSTEP_FINISH) return false;
			else if (h_msg.cur_step == MSGSTEP_FINISH) {
				h_msg.cur_step = MSGSTEP_END;	//结束消息
			}
		}
		
		return true;
	}
	
	public synchronized static boolean addWaitSendMsg(ComntMsg.MSG_CLIENT_HEAD msg_head, ComntMsg.MSG_BODY msg_body, int user_id1, int user_id2) {
		
		//if (!ComntProc.isSocketOk()) {
		//	System.out.print("\n\n-------ComntProc.isSocketOk----------\n\n");
		//	return false;
		//}
		
		if (msg_head.data_len != msg_body.byte_vect.size()) return false;		
		if (!isASendMsg(msg_head.msg_type)) 				return false;

//		if (isAOnlySendMsg(msg_head.msg_type)) msg_head.onlysend_flag = 1;		

		ComntMsgProc msgproc = ComntProc.getMsgProc();
		if (msgproc == null) 		return false;
		if (msgproc.isListFull()) 	return false;
		if (!msgproc.endAlreadyInBufferMsg(msg_head.msg_type, msg_head.user_name, user_id1, user_id2)) return false;
		
		LinkedList<ComntNetMsg> msg_list = msgproc.getMsgList();

		ComntNetMsg net_msg = new ComntNetMsg();

		net_msg.time_cnt  	  = ComntTime.cTime();
		net_msg.cur_step 	  = MSGSTEP_WAITSEND;

		net_msg.user_id1      = user_id1;
		net_msg.user_id2      = user_id2;
		
		net_msg.msg_send      = new ComntMsg.MSG_CLIENT_STRUCT();
		net_msg.msg_send.msg_head.clone(msg_head);
		net_msg.msg_send.msg_body.clone(msg_body);

		net_msg.msg_recvvect  = new ArrayList<ComntMsg.MSG_SERVER_STRUCT>();

		msg_list.add(net_msg);
		
		return true;
	}
	
	public synchronized static ArrayList<ComntMsg.MSG_CLIENT_STRUCT> getWaitSendMsg(int max_num) {
		ComntMsgProc msgproc = ComntProc.getMsgProc();
		if (msgproc == null) return null;

		LinkedList<ComntNetMsg> 				msg_list = msgproc.getMsgList();
		ArrayList<ComntMsg.MSG_CLIENT_STRUCT> 	ret_vect = new ArrayList<ComntMsg.MSG_CLIENT_STRUCT>();

		int i = 0, msg_num = 0;
		ComntNetMsg net_msg = null;

		//任务取消,高优先级
		ComntMsg.MSG_CLIENT_STRUCT comnt_msg = null; 

		for (i = 0; i < msg_list.size(); i++) {
			net_msg = msg_list.get(i);
			if (net_msg.cur_step != MSGSTEP_RECVING) continue;
			if (isAOnlySendMsg(net_msg.msg_send.msg_head.msg_type) ||
					/*net_msg.msg_send.msg_head.onlysend_flag != 0 || */!net_msg.cancel_flag) continue;

			//生成任务取消消息
			comnt_msg = new ComntMsg.MSG_CLIENT_STRUCT();
			ComntMsg.makeCancelManComntMsg(comnt_msg, 0, ComntFunc.byte2String(net_msg.msg_send.msg_head.user_name), net_msg.msg_send.msg_head.uuid);

			ret_vect.add(comnt_msg);

			msg_num++;
			if (msg_num >= max_num) break;
		}

		if (msg_num >= max_num) return ret_vect;

		//再发送任务数据
		for (i = 0; i < msg_list.size(); i++) {
			net_msg = msg_list.get(i);
			if (net_msg.cur_step != MSGSTEP_WAITSEND) continue;
			if (net_msg.cancel_flag) continue;
			
			ret_vect.add(net_msg.msg_send);
			
			msg_num++;
			if (msg_num >= max_num) break;
		}
		
		if (ret_vect.size() <= 0) return null;
		else return ret_vect;
	}
	
	public synchronized static boolean setMsgRecvingFlag(int uuid) {
		ComntMsgProc msgproc = ComntProc.getMsgProc();
		if (msgproc == null) 	return false;

		int msg_idx = msgproc.findNetMsg(uuid);
		if (msg_idx < 0) 		return false;

		LinkedList<ComntNetMsg> msg_list 	= msgproc.getMsgList();		
		ComntNetMsg 			net_msg 	= msg_list.get(msg_idx);

		if (isAOnlySendMsg(net_msg.msg_send.msg_head.msg_type)
			/*net_msg.msg_send.msg_head.onlysend_flag != 0*/) {
			net_msg.cur_step = MSGSTEP_END;
		}
		else if (net_msg.cur_step < MSGSTEP_RECVING) {//防止复写cancel
			net_msg.cur_step = MSGSTEP_RECVING;
		}
		
		return true;
	}
	
	public synchronized static boolean setMsgCancelRecv(int uuid) {
		ComntMsgProc msgproc = ComntProc.getMsgProc();
		if (msgproc == null) return false;

		int msg_idx = msgproc.findNetMsg(uuid);
		if (msg_idx < 0) 	 return false;

		LinkedList<ComntNetMsg> msg_list = msgproc.getMsgList();		
		ComntNetMsg 			net_msg  = msg_list.get(msg_idx);

		if (!net_msg.cancel_flag) return false;

		//组织任务失败结果
		ComntMsg.MSG_SERVER_STRUCT comnt_msg = new ComntMsg.MSG_SERVER_STRUCT();
		ComntMsg.makeTaskResultComntMsg(comnt_msg, net_msg.msg_send, (byte)ComntDef.YD_YFF_TASKRESULT_FAILED, uuid, (byte)ComntDef.YD_YFF_TASKERR_CANCEL, (byte)ComntDef.YD_TASKPROC_SALEMANAGER, ComntDef.YD_TASKPROC_JWEBSERVICE_NAME, (byte)0, 0, "");

		net_msg.msg_recvvect.add(comnt_msg);

		net_msg.cur_step = MSGSTEP_FINISH;

		return true;
	}
	
	public synchronized static boolean addRecvMsg(ComntMsg.MSG_SERVER_STRUCT msg_struct) {
		ComntMsgProc msgproc = ComntProc.getMsgProc();
		if (msgproc == null) return false;

		if (!isARecvMsg(msg_struct.msg_head.msg_type)) return false;

		int msg_idx = msgproc.findNetMsg(msg_struct.msg_head.uuid);
		if (msg_idx < 0) return false;

		LinkedList<ComntNetMsg> msg_list = msgproc.getMsgList();

		ComntNetMsg net_msg = msg_list.get(msg_idx);
		if (net_msg.cur_step <= MSGSTEP_WAITSEND || net_msg.cur_step >= MSGSTEP_FINISH) return false;
		if (isAOnlySendMsg(net_msg.msg_send.msg_head.msg_type) || 
				/*net_msg.msg_send.msg_head.onlysend_flag != 0 || */net_msg.cancel_flag) return false;		
		
		net_msg.time_cnt = ComntTime.cTime();
		net_msg.msg_recvvect.add(msg_struct.clone());

		boolean end_flag = isMsgRecvFinish(msg_struct.msg_head.msg_type);

		if (end_flag) {
			net_msg.cur_step = MSGSTEP_FINISH;
		}

		return true;
	}
	
	public static int getRecvMsgI(int uuid, boolean finish_flag, ArrayList<ComntMsg.MSG_SERVER_STRUCT> ret_vect) {
		ret_vect.clear();
		
		ComntMsgProc msgproc = ComntProc.getMsgProc();
		if (msgproc == null) return MSGSTEP_END;
		
		int msg_idx = msgproc.findNetMsg(uuid);
		if (msg_idx < 0) return MSGSTEP_END;

		LinkedList<ComntNetMsg> msg_list = msgproc.getMsgList();

		ComntNetMsg net_msg = msg_list.get(msg_idx);
		int ret_step = net_msg.cur_step;

		if ((net_msg.cur_step != MSGSTEP_RECVING) && (net_msg.cur_step != MSGSTEP_FINISH)) return ret_step;		
		if (finish_flag && (net_msg.cur_step != MSGSTEP_FINISH)) return ret_step;

		int i = 0;
		ComntMsg.MSG_SERVER_STRUCT t_comnt_msg1, t_comnt_msg2;

		if (!finish_flag && (net_msg.cur_step == MSGSTEP_RECVING)) {
			for (i = 0; i < net_msg.msg_recvvect.size(); i++) {
				t_comnt_msg1 = net_msg.msg_recvvect.get(i);
				if ((t_comnt_msg1.msg_head.msg_type == ComntDef.YD_YFF_MSGTYPE_DATA) ||
					(t_comnt_msg1.msg_head.msg_type == ComntDef.YD_YFF_MSGTYPE_TASKRESULT)) break;
			}
			
			if (i >= net_msg.msg_recvvect.size()) return ret_step;
		}
		
		for (i = 0; i < net_msg.msg_recvvect.size(); i++) {
			t_comnt_msg1 = net_msg.msg_recvvect.get(i);
			t_comnt_msg2 = t_comnt_msg1.clone();

			ret_vect.add(t_comnt_msg2);
		}

		net_msg.msg_recvvect.clear();
		
		if (net_msg.cur_step == MSGSTEP_FINISH) {
			net_msg.cur_step = MSGSTEP_END;
		}

		return ret_step;
	}
	
	public synchronized static int getRecvMsg(int uuid, boolean finish_flag, ArrayList<ComntMsg.MSG_SERVER_STRUCT> ret_vect) {
		return getRecvMsgI(uuid, finish_flag, ret_vect);
	}
	
	public synchronized static int getRecvMsg(String user_name, int user_id1, int user_id2, 
											  boolean finish_flag, ArrayList<ComntMsg.MSG_SERVER_STRUCT> ret_vect) {
		ComntMsgProc msgproc = ComntProc.getMsgProc();
		if (msgproc == null) 	return MSGSTEP_END;
			
		ComntNetMsg 			net_msg  = msgproc.findNoEndNetMsg(user_name, user_id1, user_id2);
		if (net_msg == null) 	return MSGSTEP_END;
		
		return getRecvMsgI(net_msg.msg_send.msg_head.uuid, finish_flag, ret_vect);
	}	
	
	public synchronized static int getRecvMsgTimeOut(int uuid) {
		ComntMsgProc msgproc = ComntProc.getMsgProc();
		if (msgproc == null) return -100;
		
		int msg_idx = msgproc.findNetMsg(uuid);
		if (msg_idx < 0) return -100;
					
		LinkedList<ComntNetMsg> msg_list = msgproc.getMsgList();
		
		ComntNetMsg net_msg = msg_list.get(msg_idx);		
		if (net_msg.cur_step == MSGSTEP_END) return -100;
		
		int max_timeout = 0;
		max_timeout = Math.max(net_msg.msg_send.msg_head.wait_count, MSG_MAX_TIMEOUT);
		
		int cur_time = ComntTime.cTime();
		
		return max_timeout - Math.abs(cur_time - net_msg.time_cnt);		
	}
	
	private boolean cancelMsg(ComntNetMsg net_msg) {
		if (net_msg == null) return false;
		if (isAOnlySendMsg(net_msg.msg_send.msg_head.msg_type) || 
				/*net_msg.msg_send.msg_head.onlysend_flag != 0 || */net_msg.cancel_flag) 	return false;
		if (net_msg.cur_step > MSGSTEP_RECVING) 			return false;
		
		net_msg.cancel_flag = true;
		
		if (net_msg.cur_step == MSGSTEP_WAITSEND) {
			//组织任务失败结果
			ComntMsg.MSG_SERVER_STRUCT comnt_msg = new ComntMsg.MSG_SERVER_STRUCT();
			ComntMsg.makeTaskResultComntMsg(comnt_msg, net_msg.msg_send, (byte)ComntDef.YD_YFF_TASKRESULT_FAILED, net_msg.msg_send.msg_head.uuid, (byte)ComntDef.YD_YFF_TASKERR_CANCEL, (byte)ComntDef.YD_TASKPROC_SALEMANAGER, ComntDef.YD_TASKPROC_JWEBSERVICE_NAME, (byte)0, 0, "");			
			net_msg.msg_recvvect.add(comnt_msg);
			
			net_msg.cur_step = MSGSTEP_FINISH;
		}
		
		return true;
	}
	
	public synchronized static boolean cancelMsg(int uuid) {
		ComntMsgProc msgproc = ComntProc.getMsgProc();
		if (msgproc == null) 	return false;
		
		int msg_idx = msgproc.findNetMsg(uuid);
		if (msg_idx < 0) 		return false;
		
		LinkedList<ComntNetMsg> msg_list = msgproc.getMsgList();		
		ComntNetMsg 			net_msg  = msg_list.get(msg_idx);
		
		return msgproc.cancelMsg(net_msg);
	}
	
	public synchronized static boolean cancelMsg(String user_name, int user_id1, int user_id2) {
		ComntMsgProc msgproc = ComntProc.getMsgProc();
		if (msgproc == null) 	return false;
			
		ComntNetMsg 			net_msg  = msgproc.findNoFinishedNetMsg(user_name, user_id1, user_id2);
		if (net_msg == null) 	return false;
		
		return msgproc.cancelMsg(net_msg);		
	}
	
	public synchronized static void endAllRunningTask(int err_code) {
		ComntMsgProc msgproc = ComntProc.getMsgProc();
		if (msgproc == null) return;
		
		LinkedList<ComntNetMsg> msg_list = msgproc.getMsgList();
		
		int i = 0;
		ComntNetMsg 				net_msg 	= null;		
		ComntMsg.MSG_SERVER_STRUCT 	comnt_msg 	= null;
		
		for (i = 0; i < msg_list.size(); i++) {
			net_msg = msg_list.get(i);
			if (net_msg.cur_step >= MSGSTEP_FINISH)  continue;

			net_msg.cur_step = MSGSTEP_FINISH;
			if (net_msg.cancel_flag || isAOnlySendMsg(net_msg.msg_send.msg_head.msg_type) 
					/*|| net_msg.msg_send.msg_head.onlysend_flag != 0*/) continue;

			//组织任务失败结果
			comnt_msg = new ComntMsg.MSG_SERVER_STRUCT();
//			ComntMsg.makeTaskResultComntMsg(comnt_msg, net_msg.msg_send, (byte)ComntDef.YD_TASKRESULT_FAILED, (byte)err_code, (byte)ComntDef.YD_TASKPROC_JWEBSERVICE, ComntDef.YD_TASKPROC_JWEBSERVICE_NAME);
			ComntMsg.makeTaskResultComntMsg(comnt_msg, net_msg.msg_send, (byte)ComntDef.YD_YFF_TASKRESULT_FAILED, net_msg.msg_send.msg_head.uuid, (byte)ComntDef.YD_YFF_TASKERR_CANCEL, (byte)ComntDef.YD_TASKPROC_SALEMANAGER, ComntDef.YD_TASKPROC_JWEBSERVICE_NAME, (byte)0, 0, "");
			
			net_msg.msg_recvvect.add(comnt_msg);
		}
	}
	
	public synchronized static void checkMsg() {
		ComntMsgProc msgproc = ComntProc.getMsgProc();
		if (msgproc == null) return;
		
		LinkedList<ComntNetMsg> msg_list = msgproc.getMsgList();
		
		int cur_time = ComntTime.cTime();
		
		int i = 0;
		ComntNetMsg net_msg = null;

		int max_timeout = 0;
		
		for (i = msg_list.size() - 1; i >= 0; i--) {
			net_msg = msg_list.get(i);
			if (net_msg.cur_step == MSGSTEP_END) {
				msg_list.remove(i);
				continue;
			}

			max_timeout = Math.max(net_msg.msg_send.msg_head.wait_count, MSG_MAX_TIMEOUT);
			
			if (Math.abs(net_msg.time_cnt - cur_time) >= max_timeout) {
				if ((net_msg.cur_step <= MSGSTEP_RECVING) && (!net_msg.cancel_flag) 
						&& isAOnlySendMsg(net_msg.msg_send.msg_head.msg_type)						
					//(net_msg.msg_send.msg_head.onlysend_flag != 0)
					) {
					net_msg.cancel_flag = true;
					net_msg.time_cnt    = cur_time;
				}
				else {
					msg_list.remove(i);
				}
				continue;
			}
		}
	}
	
	public static boolean isSelfRtuState(ComntMsg.MSG_SERVER_STRUCT comnt_msg) {
		if (comnt_msg.msg_head.msg_type != ComntDef.YD_YFF_MSGTYPE_RTUSTATE) return false;
		if (comnt_msg.msg_head.uuid != -1) return false;
		
		return true;
	}
	
	public static void setSelfRtuState(ComntMsg.MSG_SERVER_STRUCT comnt_msg) {
		if (comnt_msg.msg_head.msg_type != ComntDef.YD_YFF_MSGTYPE_RTUSTATE) return;
		
		ComntMsg.MSG_RTUSTATE rtustate_msg = new ComntMsg.MSG_RTUSTATE();
		rtustate_msg.fromDataStream(comnt_msg.msg_body.byte_vect, 0);
		
		OnlineRtu.setRtuState(rtustate_msg);		
	}
	
	public static boolean isSelfSysEvent(ComntMsg.MSG_SERVER_STRUCT comnt_msg) {
//		if (comnt_msg.msg_head.msg_type != ComntDef.YD_WEBMSGTYPE_SYSEVENT) return false;
		//if (comnt_msg.msg_head.uuid != -1) return false;
		
		return false;
	}
	
	public static void setSelfSysEvent(ComntMsg.MSG_SERVER_STRUCT comnt_msg) {
//		if (comnt_msg.msg_head.msg_type != ComntDef.YD_WEBMSGTYPE_SYSEVENT) return;
		
//		ComntMsg.MSG_SYSEVENT sysevent_msg = new ComntMsg.MSG_SYSEVENT();
//		sysevent_msg.fromDataStream(comnt_msg.msg_body.byte_vect, 0);		
		
	}
	
	public static void waitMsg() {
		try {
			Thread.sleep(500);
		}catch(InterruptedException e){
			e.printStackTrace(System.err);
		}
	}
	
	public static void sendReloadTableMsg(String user_name, byte[] reload_mask, String version) {
		ComntMsg.MSG_CLIENT_STRUCT comnt_msg = new ComntMsg.MSG_CLIENT_STRUCT();
		int uuid = getNewUUID();
		ComntMsg.makeReloadParaComntMsg(comnt_msg, uuid, user_name, reload_mask, version);
		
		addWaitSendMsg(comnt_msg.msg_head, comnt_msg.msg_body, 0, 0);
	}
	
    private static int glo_uuid = 1;
    public synchronized static int getNewUUID() {
    	int uuid = glo_uuid ++;
    	
    	if (glo_uuid >= ((int)0x7FFFFFF0)) glo_uuid = 1;
    	
    	return uuid;
    }	
}
