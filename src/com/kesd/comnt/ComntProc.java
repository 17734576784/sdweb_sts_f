package com.kesd.comnt;

import java.util.ArrayList;

import com.libweb.common.CommBase;
import com.libweb.comnt.ComntSocket;
import com.libweb.comnt.ComntTime;
import com.libweb.comnt.ComntVector;

public class ComntProc implements Runnable {
	ComntCfg     comnt_cfg     = null;
	ComntSocket  comnt_socket  = null;
	ComntMsgProc comnt_msgproc = null;
	
	int last_connect_ctime   = 0;
	int sock_err_count		 = 0;
	int last_rtustate_time   = 0;
	
	public ComntProc(){
		comnt_cfg     = new ComntCfg();
		comnt_socket  = new ComntSocket();
		comnt_msgproc = new ComntMsgProc();
		comnt_cfg.loadComntCfg();
	}

	public static final int SOCKET_CONNECT_INTER	  = 5;			//socket重新连接间隔5s
//	public static final int SALEMAN_HEART_SEC         = (5 * 60);	//心跳间隔
	public static final int SALEMAN_CHECK_HEART_INTER = 5;
	public static final int SALEMAN_RTUSTATE_SEC      = 10;			//终端状态时间	
	
	private boolean connectSocket() {
		if (comnt_socket == null) return false;
		
		int cur_ctime = ComntTime.cTime();
		if (!comnt_socket.isSocketValid()) {			
			if (Math.abs(cur_ctime - last_connect_ctime) < SOCKET_CONNECT_INTER) return false;			
			boolean cnn_val = comnt_socket.connect(comnt_cfg.saleman_servic_ip, comnt_cfg.saleman_servic_port);
			
			System.out.println(new java.util.Date() + "通讯线程:SOCKET 连接" + "IP:" + comnt_cfg.saleman_servic_ip  + "/" + comnt_cfg.saleman_servic_port + (cnn_val ? "成功" : "失败") + ".");
			
			last_connect_ctime = cur_ctime;
			
			if (cnn_val == false) {
				sock_err_count++;
				return false;			
			}
		}
		
		sock_err_count = 0;
		last_connect_ctime = cur_ctime;
		
		return true;
	}
	
	public static final int SOCKET_DATA_MAXLEN		= (1024 * 1024 * 1024);	//读写的SOCKET最大数据长度 1M
	
	private int readProc() {
		if (comnt_socket.getErrFlag()) return -1;
		
		int sel_val = comnt_socket.select(10);		//10ms
		if (sel_val < 0) {							//socket err
			comnt_socket.setErrFlag(true);
			return -1;
		}
		else if (sel_val == 0) {					//no data
			return 0;
		}

		ComntVector.ByteVector byte_vect = new ComntVector.ByteVector();
		
		byte_vect.resize(ComntMsg.MSG_SERVER_HEAD.size());
		int read_val = comnt_socket.read(byte_vect.getaddr(), 0, byte_vect.size(), 1000);
		
		if (read_val != byte_vect.size()) {
			comnt_socket.setErrFlag(true);
			return -1;
		}
		
		ComntMsg.MSG_SERVER_STRUCT comnt_msg = new ComntMsg.MSG_SERVER_STRUCT();
		comnt_msg.msg_head.fromDataStream(byte_vect, 0);
		
		//消息为空或太长则抛弃
		if (comnt_msg.msg_head.data_len <= 0 || comnt_msg.msg_head.data_len >= SOCKET_DATA_MAXLEN) return 1;		
		
		sel_val = comnt_socket.select(1000);		//1000ms
		if (sel_val <= 0) {							//socket err
			comnt_socket.setErrFlag(true);
			return -1;
		}

		byte_vect.resize(comnt_msg.msg_head.data_len);

		read_val = comnt_socket.read(byte_vect.getaddr(), 0, byte_vect.size(), 1000);

		if (read_val != byte_vect.size()) {
			comnt_socket.setErrFlag(true);
			return -1;
		}
		
		//20120825 发送接收确认
		if (!TestSocket3()) {
			comnt_socket.setErrFlag(true);
			return -1;
		}
		
		comnt_msg.msg_body.byte_vect.push_back(byte_vect);

		//每过5s服务器发送终端状态
		if (ComntMsgProc.isSelfRtuState(comnt_msg)) {
			ComntMsgProc.setSelfRtuState(comnt_msg);
		}
//		else if (ComntMsgProc.isSelfSysEvent(comnt_msg)) {
//			ComntMsgProc.setSelfSysEvent(comnt_msg);
//		}
		else {
			ComntMsgProc.addRecvMsg(comnt_msg);	
		}

		return 1;
	}
	
	private int writeProc() {
		if (comnt_socket.getErrFlag()) return -1;

		ArrayList<ComntMsg.MSG_CLIENT_STRUCT> msg_vect = ComntMsgProc.getWaitSendMsg(10);	//每次发送10个任务
		if (msg_vect == null || msg_vect.size() <= 0) return 0;
		
		//首先测试链路
//		if (TestSocket() == false) {
//			comnt_socket.setErrFlag(true);
//			return -1;
//		}

		ComntMsg.MSG_CLIENT_STRUCT msg = null;
		
		int write_val = 0;		
		
		ComntVector.ByteVector byte_vect = new ComntVector.ByteVector();		
		
		for (int i = 0; i < msg_vect.size(); i++) {
			msg = msg_vect.get(i);
			
			byte_vect.clear();
			msg.msg_head.toDataStream(byte_vect);
			
			write_val = comnt_socket.write(byte_vect.getaddr(), 0, byte_vect.size());
			if (write_val != byte_vect.size()) {
				comnt_socket.setErrFlag(true);				
				return -1;
			}
			
			byte_vect.clear();
			byte_vect.push_back(msg.msg_body.byte_vect);
			
			write_val = comnt_socket.write(byte_vect.getaddr(), 0, byte_vect.size());
			if (write_val != byte_vect.size()) {
				comnt_socket.setErrFlag(true);				
				return -1;
			}
			
			//20120825 等待 接收确认
			boolean test_f = TestSocket2();
			if (!test_f) {
				comnt_socket.setErrFlag(true);				
				return -1;
			}
			
			if (msg.msg_head.msg_type == ComntDef.YD_YFF_MSGTYPE_CANCELMANTASK) {
				ComntMsg.MSG_CANCELMANTASK cancel_msg = new ComntMsg.MSG_CANCELMANTASK();
				cancel_msg.fromDataStream(msg.msg_body.byte_vect, 0);
				ComntMsgProc.setMsgCancelRecv(cancel_msg.task_uuid);
			}
			else {
				ComntMsgProc.setMsgRecvingFlag(msg.msg_head.uuid);
			}
		}

		return 1;
	}
	
	private void checkSocket() {
		if (comnt_socket.getErrFlag()) {
			comnt_socket.setErrFlag(false);
			comnt_socket.close();
		}
	}
	
	private boolean isSocketValid() {
		if (comnt_socket == null || !comnt_socket.isSocketValid()) return false;
		else return true;
	}
	
	/*
	private boolean TestSocket() {
		if (comnt_socket.getErrFlag()) return false;
		
		int send_len = 0, recv_len = 0;
		
		ComntVector.ByteVector byte_vect = new ComntVector.ByteVector();
		int time_out = 5000;		//5s 超时
		
		ComntMsg.MSG_CLIENT_STRUCT client_msg = new ComntMsg.MSG_CLIENT_STRUCT();
		ComntMsg.MSG_TEST          msg_test   = new ComntMsg.MSG_TEST(); 
		
		msg_test.one_byte = (byte)0x99;
		msg_test.toDataStream(client_msg.msg_body.byte_vect);
		
		client_msg.msg_head.makeMsgHead(ComntDef.YD_YFF_MSGTYPE_TEST, 0, client_msg.msg_body.byte_vect.size(), 
										0, "test", ComntDef.SALEMANAGER_VERSION);
		
		byte_vect.clear();
		client_msg.msg_head.toDataStream(byte_vect);
		send_len = comnt_socket.write(byte_vect.getaddr(), 0, byte_vect.size());
		if (send_len != byte_vect.size()) return false;
		
		byte_vect.clear();
		byte_vect.push_back(client_msg.msg_body.byte_vect);

		send_len = comnt_socket.write(byte_vect.getaddr(), 0, byte_vect.size());
		if (send_len != byte_vect.size()) return false;		
		
		int sel_val = comnt_socket.select(time_out);		//5000ms
		if (sel_val <= 0) return false;
	
		byte_vect.clear();
		byte_vect.resize(ComntMsg.MSG_SERVER_HEAD.size());
		
		recv_len = comnt_socket.read(byte_vect.getaddr(), 0, byte_vect.size(), 1000);

		if (recv_len != byte_vect.size()) {
			return false;
		}
		
		ComntMsg.MSG_SERVER_STRUCT server_msg = new ComntMsg.MSG_SERVER_STRUCT();
		server_msg.msg_head.fromDataStream(byte_vect, 0);

//hzhw 20120814 等待改进
		if (server_msg.msg_head.msg_type != ComntDef.YD_YFF_MSGTYPE_TEST || server_msg.msg_head.data_len != 1) {
			return false;
		}

		sel_val = comnt_socket.select(time_out);		//5000ms
		if (sel_val <= 0) return false;
	
		byte_vect.resize(server_msg.msg_head.data_len);

		recv_len = comnt_socket.read(byte_vect.getaddr(), 0, byte_vect.size(), 1000);

		if (recv_len != byte_vect.size()) {
			return false;
		}

		return true;		
	}
	*/
	
	//20120825 等待 接收确认
	private boolean TestSocket2() {
		if (comnt_socket.getErrFlag()) return false;
		
		int recv_len = 0;
		
		ComntVector.ByteVector byte_vect = new ComntVector.ByteVector();
		int time_out = 10000;		//10s 超时
		
		int sel_val = comnt_socket.select(time_out);		//10,000ms
		if (sel_val <= 0) return false;
	
		byte_vect.clear();
		byte_vect.resize(ComntMsg.MSG_SERVER_HEAD.size());
		
		recv_len = comnt_socket.read(byte_vect.getaddr(), 0, byte_vect.size(), 1000);

		if (recv_len != byte_vect.size()) {
			return false;
		}
		
		ComntMsg.MSG_SERVER_STRUCT server_msg = new ComntMsg.MSG_SERVER_STRUCT();
		server_msg.msg_head.fromDataStream(byte_vect, 0);

//hzhw 20120814 等待改进
		if (server_msg.msg_head.msg_type != ComntDef.YD_YFF_MSGTYPE_TEST || server_msg.msg_head.data_len != 1) {
			return false;
		}

		sel_val = comnt_socket.select(time_out);		//10,000ms
		if (sel_val <= 0) return false;
	
		byte_vect.resize(server_msg.msg_head.data_len);

		recv_len = comnt_socket.read(byte_vect.getaddr(), 0, byte_vect.size(), 2000);

		if (recv_len != byte_vect.size()) {
			return false;
		}

		return true;		
	}	
	
	//20120825 发送 接收确认
	private boolean TestSocket3() {
		if (comnt_socket.getErrFlag()) return false;
		
		int send_len = 0;
		
		ComntVector.ByteVector byte_vect = new ComntVector.ByteVector();
		
		ComntMsg.MSG_CLIENT_STRUCT client_msg = new ComntMsg.MSG_CLIENT_STRUCT();
		ComntMsg.MSG_TEST          msg_test   = new ComntMsg.MSG_TEST(); 
		
		msg_test.one_byte = (byte)0x99;
		msg_test.toDataStream(client_msg.msg_body.byte_vect);
		
		client_msg.msg_head.makeMsgHead(ComntDef.YD_YFF_MSGTYPE_TEST, 0, client_msg.msg_body.byte_vect.size(), 
										0, "test", ComntDef.SALEMANAGER_VERSION);
		
		byte_vect.clear();
		client_msg.msg_head.toDataStream(byte_vect);
		send_len = comnt_socket.write(byte_vect.getaddr(), 0, byte_vect.size());
		if (send_len != byte_vect.size()) return false;
		
		byte_vect.clear();
		byte_vect.push_back(client_msg.msg_body.byte_vect);

		send_len = comnt_socket.write(byte_vect.getaddr(), 0, byte_vect.size());
		if (send_len != byte_vect.size()) return false;		
			
		return true;		
	}	
	
	private boolean GetRtuState()
	{
		if (comnt_socket.getErrFlag()) return false;
		
		int send_len = 0, recv_len = 0;
		
		ComntVector.ByteVector byte_vect = new ComntVector.ByteVector();
		int time_out = 5000;		//5s 超时
		
		ComntMsg.MSG_CLIENT_STRUCT client_msg = new ComntMsg.MSG_CLIENT_STRUCT();
		ComntMsg.MSG_QUERYRTUSTATE msg_query   = new ComntMsg.MSG_QUERYRTUSTATE(); 
		
		msg_query.one_byte = (byte)0x99;
		msg_query.toDataStream(client_msg.msg_body.byte_vect);
		
		client_msg.msg_head.makeMsgHead(ComntDef.SALEMAN_MSGTYPE_QUERYRTUSTATE, 0, client_msg.msg_body.byte_vect.size(), 
										0, "test", ComntDef.SALEMANAGER_VERSION);
		
		byte_vect.clear();
		client_msg.msg_head.toDataStream(byte_vect);
		send_len = comnt_socket.write(byte_vect.getaddr(), 0, byte_vect.size());
		if (send_len != byte_vect.size()) return false;
		
		byte_vect.clear();
		byte_vect.push_back(client_msg.msg_body.byte_vect);

		send_len = comnt_socket.write(byte_vect.getaddr(), 0, byte_vect.size());
		if (send_len != byte_vect.size()) return false;		
		
		int sel_val = comnt_socket.select(time_out);		//5000ms
		if (sel_val <= 0) return false;
	
		byte_vect.clear();
		byte_vect.resize(ComntMsg.MSG_SERVER_HEAD.size());
		
		recv_len = comnt_socket.read(byte_vect.getaddr(), 0, byte_vect.size(), 1000);

		if (recv_len != byte_vect.size()) {
			return false;
		}
		
		ComntMsg.MSG_SERVER_STRUCT server_msg = new ComntMsg.MSG_SERVER_STRUCT();
		server_msg.msg_head.fromDataStream(byte_vect, 0);

		//hzhw 20120814 等待改进
		if (server_msg.msg_head.msg_type != ComntDef.YD_YFF_MSGTYPE_RTUSTATE) {
			return false;
		}

		sel_val = comnt_socket.select(time_out);		//5000ms
		if (sel_val <= 0) return false;
	
		byte_vect.resize(server_msg.msg_head.data_len);

		recv_len = comnt_socket.read(byte_vect.getaddr(), 0, byte_vect.size(), 1000);

		if (recv_len != byte_vect.size()) {
			return false;
		}

		server_msg.msg_body.byte_vect.push_back(byte_vect);
		
		ComntMsgProc.setSelfRtuState(server_msg);
		
		return true;		
	}
	
	private boolean rtuStateProc()
	{
		if (comnt_socket.getErrFlag()) return false;

		int cur_ctime = ComntTime.cTime();

		if (Math.abs(last_rtustate_time - cur_ctime) > SALEMAN_RTUSTATE_SEC) {
			last_rtustate_time = cur_ctime;
			//测试链路
			//if (TestSocket() == false) {
			//	comnt_socket.setErrFlag(true);
			//	return false;
			//}
			
			//获取终端运行状态
			if (GetRtuState() == false) {
				comnt_socket.setErrFlag(true);
				return false;
			}
		}

		return true;
	}
	
	private void threadProc() 	{
		if (!connectSocket()) return;

		int read_val  = 0;

		for (int i = 0; i < 10; i++) {
			read_val = readProc();			
			if (read_val <= 0) break;
		}

		writeProc();

		rtuStateProc();
		
		checkSocket();		
	}
  
	private static boolean glo_live_flag = true;
	private static boolean glo_exit_flag = false;
	
	
	public static final int CHECK_MSG_INTER	= 5;	//检查消息间隔5s
	
	public static final int MAX_SOCKERR_NUM	= 6;	//socket错误清理消息
	
	////////////////////////////////////////////////
	public void run() {
		int last_time = ComntTime.cTime();
		int cur_time = last_time;
		
		System.out.println("通讯线程:正常启动.");

		com.kesd.util.OnlineRtu.init();		
		
		while(glo_live_flag){
			try {
				threadProc();  //--调试临时封闭
				
				cur_time = ComntTime.cTime();
				
				//5s检查消息队列
				if (Math.abs(cur_time - last_time) >= CHECK_MSG_INTER) {
					ComntMsgProc.checkMsg();
					last_time = cur_time;			
					
					//检查终端在线状态
					com.kesd.util.OnlineRtu.checkRtuState();					
				}
				
				if (sock_err_count > MAX_SOCKERR_NUM) {						//连续打开socket错误,清楚所有未完成任务
					ComntMsgProc.endAllRunningTask(ComntDef.YD_YFF_TASKERR_COMNT_TIMEOUT);
					sock_err_count = 0;
				}				
				
				if (isSocketValid()) {
					//Thread.sleep(1);
				}
				else {
					Thread.sleep(500);
				}
				
			}catch(InterruptedException e){
				e.printStackTrace(System.err);
			}
		}
		
		comnt_socket.close();
		
		System.out.println("售电子系统通讯线程:正常退出.");
		
		glo_exit_flag = true;		
	}

	////////////////////////////////////////////////
	private static Thread	   	glo_comnt_thread = null;
	private static ComntProc 	glo_comnt_proc   = null;

	public static void startComntProc() {
		glo_comnt_proc   = new ComntProc();
		glo_comnt_thread = new Thread(glo_comnt_proc);

		glo_comnt_thread.start();
	}

	public static void stopComntProc() {
		glo_live_flag = false;

		for (int i = 0; i < 10; i++) {	//5s
			if (glo_exit_flag) break;
			CommBase.wait(500);
		}
	}	
	
	public static ComntMsgProc getMsgProc() {
		if (glo_comnt_proc == null) return null;
		else return glo_comnt_proc.comnt_msgproc;
	}
	
	public static ComntCfg getComntCfg() {
		if (glo_comnt_proc == null) return null;
		else return glo_comnt_proc.comnt_cfg;
	}
	
	public static boolean  isSocketOk()	{
		if (glo_comnt_proc == null || glo_comnt_proc.comnt_socket == null) return false;
		return glo_comnt_proc.comnt_socket.isSocketValid();
	}
}

