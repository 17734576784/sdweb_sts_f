package com.kesd.comnt;

import com.libweb.comnt.*;

import com.kesd.common.YFFDef;

public class ComntMsg {
	
	public static final int MSG_USERNAME_LEN	= 64;
	public static final int MSG_STR_LEN_120		= 120;
	public static final int MSG_STR_LEN_64		= 64;
	public static final int MSG_STR_LEN_60		= 60;
	public static final int MSG_STR_LEN_40		= 40;
	public static final int MSG_STR_LEN_32		= 32;
	public static final int MSG_STR_LEN_20		= 20;
	public static final int MSG_STR_LEN_16		= 16;
	public static final int MSG_STR_LEN_8		= 8;
	public static final int MSG_STR_LEN_80		= 80;
	public static final int MSG_STR_LEN_160		= 160;
	
	public static final int YFF_MPPAY_METERNUM  = 3;
	public static final int YFF_ZJGPAY_METERNUM = 3;
	
	public static class MSG_CLIENT_HEAD {
		public int  msg_type      = 0;
		public int  uuid		  = 0;
		public int  data_len      = 0;
		public int  wait_count	  = 0;
		public byte user_name[]   = new byte[MSG_USERNAME_LEN];
		public byte version[]     = new byte[MSG_USERNAME_LEN];

		public int size() {
			return MSG_USERNAME_LEN * 2 + 4 * 4;
		}

		public void clean() {
			msg_type 	  = 0;
			uuid	 	  = 0;
			data_len 	  = 0;
			wait_count	  = 0;
			ComntFunc.arraySet(user_name, (byte)0);
			ComntFunc.arraySet(version,   (byte)0);
		}

		public void clone(MSG_CLIENT_HEAD msg_head) {
			msg_type 	  = msg_head.msg_type;
			uuid	 	  = msg_head.uuid;
			data_len 	  = msg_head.data_len;
			wait_count	  = msg_head.wait_count;			
			ComntFunc.arrayCopy(user_name, msg_head.user_name);
			ComntFunc.arrayCopy(version,   msg_head.version);
		}

		public MSG_CLIENT_HEAD clone() {
			MSG_CLIENT_HEAD ret_msg = new MSG_CLIENT_HEAD();
			ret_msg.clone(this);

			return ret_msg;
		}

		public void makeMsgHead(int msg_type, int uuid, int data_len, 
								int wait_count, String user_name, String version) {
			this.msg_type 		= msg_type;
			this.uuid     		= uuid;
			this.data_len 		= data_len;
			this.wait_count	    = wait_count;
			
			ComntFunc.string2Byte(user_name, this.user_name);
			ComntFunc.string2Byte(version,   this.version);
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len = 0;
			ret_len += ComntStream.writeStream(byte_vect, msg_type);
			ret_len += ComntStream.writeStream(byte_vect, uuid);
			ret_len += ComntStream.writeStream(byte_vect, data_len);
			ret_len += ComntStream.writeStream(byte_vect, wait_count);
			ret_len += ComntStream.writeStream(byte_vect, user_name, 0, user_name.length);
			ret_len += ComntStream.writeStream(byte_vect, version,   0, version.length);
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			msg_type = ComntStream.readStream(byte_vect, offset + ret_len, msg_type);
			ret_len += ComntStream.getDataSize(msg_type);

			uuid = ComntStream.readStream(byte_vect, offset + ret_len, uuid);
			ret_len += ComntStream.getDataSize(uuid);

			data_len = ComntStream.readStream(byte_vect, offset + ret_len, data_len);
			ret_len += ComntStream.getDataSize(data_len);

			wait_count = ComntStream.readStream(byte_vect, offset + ret_len, wait_count);
			ret_len += ComntStream.getDataSize(wait_count);

			ComntStream.readStream(byte_vect, offset + ret_len, user_name, 0, user_name.length);
			ret_len += ComntStream.getDataSize((byte)1) * user_name.length;

			ComntStream.readStream(byte_vect, offset + ret_len, version, 0, version.length);
			ret_len += ComntStream.getDataSize((byte)1) * version.length;

			return ret_len;
		}
	}

	public static class MSG_SERVER_HEAD {
		public int  msg_type      = 0;
		public int  uuid		  = 0;
		public int  data_len      = 0;

		public static int size() {
			return 4 * 3;
		}

		public void clean() {
			msg_type 	  = 0;
			uuid	 	  = 0;
			data_len 	  = 0;
		}

		public void clone(MSG_SERVER_HEAD msg_head) {
			msg_type 	  = msg_head.msg_type;
			uuid	 	  = msg_head.uuid;
			data_len 	  = msg_head.data_len;
		}

		public MSG_SERVER_HEAD clone() {
			MSG_SERVER_HEAD ret_msg = new MSG_SERVER_HEAD();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public void makeMsgHead(int msg_type, int uuid, int data_len) {
			this.msg_type 		= msg_type;
			this.uuid     		= uuid;
			this.data_len 		= data_len;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len = 0;
			ret_len += ComntStream.writeStream(byte_vect, msg_type);
			ret_len += ComntStream.writeStream(byte_vect, uuid);
			ret_len += ComntStream.writeStream(byte_vect, data_len);
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			msg_type = ComntStream.readStream(byte_vect, offset + ret_len, msg_type);
			ret_len += ComntStream.getDataSize(msg_type);

			uuid = ComntStream.readStream(byte_vect, offset + ret_len, uuid);
			ret_len += ComntStream.getDataSize(uuid);

			data_len = ComntStream.readStream(byte_vect, offset + ret_len, data_len);
			ret_len += ComntStream.getDataSize(data_len);
			
			return ret_len;
		}
	}
	
	public static class MSG_BODY {
		public ComntVector.ByteVector byte_vect = new ComntVector.ByteVector();
		
		public void clean() {
			byte_vect.clear();
		}
		
		public void clone(MSG_BODY msg_body) {
			byte_vect.clone(msg_body.byte_vect);
		}

		public MSG_BODY clone() {
			MSG_BODY ret_msg = new MSG_BODY();
			ret_msg.clone(this);

			return ret_msg;
		}
		
		public void makeMsgBody(ComntVector.ByteVector byte_vect) {
			this.byte_vect.clone(byte_vect);
		}
	}
	
	public static class MSG_CLIENT_STRUCT {
		public MSG_CLIENT_HEAD msg_head = new MSG_CLIENT_HEAD();
		public MSG_BODY        msg_body = new MSG_BODY();

		public void clean() {
			msg_head.clean();
			msg_body.clean();
		}
		
		public void clone(MSG_CLIENT_STRUCT msg_struct) {
			msg_head.clone(msg_struct.msg_head);
			msg_body.clone(msg_struct.msg_body);
		}
		
		public MSG_CLIENT_STRUCT clone() {
			MSG_CLIENT_STRUCT ret_msg = new MSG_CLIENT_STRUCT();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public void makeClientMsgStruct(int msg_type, int uuid, int wait_count, 
											String user_name, String version, ComntVector.ByteVector byte_vect) {
			
			msg_head.makeMsgHead(msg_type, uuid, byte_vect.size(), wait_count, user_name, version);			
			msg_body.byte_vect.clone(byte_vect);			
		}
	}
	
	public static class MSG_SERVER_STRUCT {
		public MSG_SERVER_HEAD msg_head = new MSG_SERVER_HEAD();
		public MSG_BODY        msg_body = new MSG_BODY();

		public void clean() {
			msg_head.clean();
			msg_body.clean();
		}
		
		public void clone(MSG_SERVER_STRUCT msg_struct) {
			msg_head.clone(msg_struct.msg_head);
			msg_body.clone(msg_struct.msg_body);
		}
		
		public MSG_SERVER_STRUCT clone() {
			MSG_SERVER_STRUCT ret_msg = new MSG_SERVER_STRUCT();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public void makeServerMsgStruct(int msg_type, int uuid, ComntVector.ByteVector byte_vect) {
			msg_head.msg_type = msg_type;
			msg_head.uuid     = uuid;
			msg_head.data_len = byte_vect.size();
			
			msg_body.byte_vect.clone(byte_vect);
		}
	}
	
	public static interface IMSG_READWRITESTREAM {
		public int toDataStream(ComntVector.ByteVector byte_vect);
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset);
	}
	
	public static class MSG_TASK implements IMSG_READWRITESTREAM {
		public byte		taskapptype		= 0;
		public byte		taskassigntype	= 0;

		public int		rtuid			= -1;
		public byte		prottype		= 0;

		public byte		retrynum		= 0;

		public byte		bakresult_flag	= 0;		//返回任务执行状态
		public byte		bakdata_dict	= 0;		//返回数据方向
		public byte		bakcode_flag	= 0;		//返回通讯报文标志
		public byte		bakcodejx_flag  = 0;		//返回通讯报文解析标志

		public ComntVector.ByteVector data_vect = new ComntVector.ByteVector();
	
		
		public void clean() {
			taskapptype		= 0;
			taskassigntype	= 0;

			rtuid			= -1;
			prottype		= 0;

			retrynum		= 0;

			bakresult_flag	= 0;		//返回任务执行状态
			bakdata_dict	= 0;		//返回数据方向
			bakcode_flag	= 0;		//返回通讯报文标志
			bakcodejx_flag  = 0;		//返回通讯报文解析标志
			
			data_vect.clear();
		}
		
		public void clone(MSG_TASK msg) {
			taskapptype		= msg.taskapptype;
			taskassigntype	= msg.taskassigntype;

			rtuid			= msg.rtuid;
			prottype		= msg.prottype;

			retrynum		= msg.retrynum;

			bakresult_flag	= msg.bakresult_flag;		//返回任务执行状态
			bakdata_dict	= msg.bakdata_dict;			//返回数据方向
			bakcode_flag	= msg.bakcode_flag;			//返回通讯报文标志
			bakcodejx_flag  = msg.bakcodejx_flag;		//返回通讯报文解析标志

			data_vect.clone(msg.data_vect);
		}
		
		public MSG_TASK clone() {
			MSG_TASK ret_msg = new MSG_TASK();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public void makeMsgTask(byte  apptype, byte assigntype, int rtuid, byte prottype, byte retrynum,
								byte	bakresult_flag, byte bakdata_dict,  byte bakcode_flag, byte  bakcodejx_flag) {

			this.taskapptype	= apptype;
			this.taskassigntype	= assigntype;

			this.rtuid			= rtuid;
			this.prottype		= prottype;

			this.retrynum		= retrynum;

			this.bakresult_flag	= bakresult_flag;		//返回任务执行状态
			this.bakdata_dict	= bakdata_dict;			//返回数据方向
			this.bakcode_flag	= bakcode_flag;			//返回通讯报文标志
			this.bakcodejx_flag = bakcodejx_flag;		//返回通讯报文解析标志			
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			int vect_size = 0;

			ret_len += ComntStream.writeStream(byte_vect, taskapptype);
			ret_len += ComntStream.writeStream(byte_vect, taskassigntype);

			ret_len += ComntStream.writeStream(byte_vect, rtuid);
			ret_len += ComntStream.writeStream(byte_vect, prottype);

			ret_len += ComntStream.writeStream(byte_vect, retrynum);

			ret_len += ComntStream.writeStream(byte_vect, bakresult_flag);
			ret_len += ComntStream.writeStream(byte_vect, bakdata_dict);
			ret_len += ComntStream.writeStream(byte_vect, bakcode_flag);
			ret_len += ComntStream.writeStream(byte_vect, bakcodejx_flag);			
			
			vect_size = data_vect.size();
			ret_len += ComntStream.writeStream(byte_vect, vect_size);
			ret_len += ComntStream.writeStream(byte_vect, data_vect.getaddr(), 0, vect_size); 
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			int vect_size = 0;
			
			taskapptype = ComntStream.readStream(byte_vect, offset + ret_len, taskapptype); 
			ret_len += ComntStream.getDataSize(taskapptype);
			
			taskassigntype = ComntStream.readStream(byte_vect, offset + ret_len, taskassigntype); 
			ret_len += ComntStream.getDataSize(taskassigntype);
			
			rtuid = ComntStream.readStream(byte_vect, offset + ret_len, rtuid);
			ret_len += ComntStream.getDataSize(rtuid);
			
			prottype = ComntStream.readStream(byte_vect, offset + ret_len, prottype);
			ret_len += ComntStream.getDataSize(prottype);
			
			retrynum = ComntStream.readStream(byte_vect, offset + ret_len, retrynum);
			ret_len += ComntStream.getDataSize(retrynum);					

			bakresult_flag = ComntStream.readStream(byte_vect, offset + ret_len, bakresult_flag);
			ret_len += ComntStream.getDataSize(bakresult_flag);

			bakdata_dict = ComntStream.readStream(byte_vect, offset + ret_len, bakdata_dict);
			ret_len += ComntStream.getDataSize(bakdata_dict);

			bakcode_flag = ComntStream.readStream(byte_vect, offset + ret_len, bakcode_flag);
			ret_len += ComntStream.getDataSize(bakcode_flag);

			bakcodejx_flag = ComntStream.readStream(byte_vect, offset + ret_len, bakcodejx_flag);
			ret_len += ComntStream.getDataSize(bakcodejx_flag);

			vect_size = ComntStream.readStream(byte_vect, offset + ret_len, vect_size);
			ret_len += ComntStream.getDataSize(vect_size);
			
			data_vect.resize(vect_size);
			ComntStream.readStream(byte_vect, offset + ret_len, data_vect.getaddr(), 0, vect_size);
			ret_len += ComntStream.getDataSize((byte)1) * vect_size;
			
			return ret_len;
		}		
	}	
	
	public static void makeTaskComntMsg(MSG_CLIENT_STRUCT comnt_msg, MSG_TASK task_msg, int uuid, 
										int wait_count, String user_name, String version) {		
		task_msg.toDataStream(comnt_msg.msg_body.byte_vect);
		
		comnt_msg.msg_head.makeMsgHead(ComntDef.YD_YFF_MSGTYPE_TASK, uuid, comnt_msg.msg_body.byte_vect.size(), 
									   wait_count, user_name, version);		
	}

	public static void makeTaskComntMsg(MSG_CLIENT_STRUCT comnt_msg, int uuid, int wait_count, String user_name, String version,
										byte apptype, byte assigntype, int rtuid, byte prottype, byte retrynum,
										byte bakresult_flag, byte bakdata_dict,  byte bakcode_flag, byte  bakcodejx_flag,
										ComntVector.ByteVector byte_vect) {
		MSG_TASK task_msg = new MSG_TASK();
		task_msg.makeMsgTask(apptype, assigntype, rtuid, prottype, retrynum, bakresult_flag, bakdata_dict, bakcode_flag, bakcodejx_flag);		
		task_msg.data_vect.clone(byte_vect);
		
		makeTaskComntMsg(comnt_msg, task_msg, uuid, wait_count, user_name, version);
	}	

	//发送给任务源的消息 CYFF_TASKRESULTMSG
	public static class MSG_TASKRESULT implements IMSG_READWRITESTREAM {
		public int		rtuid			= -1;
		public int 		task_uuid		= 0;
		public byte		taskresult		= 0;
		
		public int		errcode			= 0;
		public byte		errproc			= 0;
		public byte   	errhost[]		= new byte[MSG_STR_LEN_16]; 
		
		//用于加密机		
		public byte		errstep			= 0;	//失败步骤
		public int		errsubcode		= 0;	//错误码 子码
		public byte		errstr[] 		= new byte[MSG_STR_LEN_64];
		
		public int		user_data1		= 0;
		public int		user_data2		= 0;

		{
			for (int i = 0; i < MSG_STR_LEN_16; i++) errhost[i] = 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) errstr[i]  = 0;
		}
		
		public void clean() {
			rtuid			= -1;
			task_uuid		= 0;
			taskresult		= 0;
			
			errcode			= 0;
			errproc			= 0;
			for (int i = 0; i < MSG_STR_LEN_16; i++) errhost[i] = 0;
			
			errstep			= 0;	//失败步骤
			errsubcode		= 0;	//错误码 子码
			for (int i = 0; i < MSG_STR_LEN_64; i++) errstr[i] = 0;
			
			user_data1      = 0;
			user_data2      = 0;
		}
		
		public void clone(MSG_TASKRESULT task_result) {
			rtuid			= task_result.rtuid;
			task_uuid		= task_result.task_uuid;
			taskresult		= task_result.taskresult;
			
			errcode			= task_result.errcode;
			errproc			= task_result.errproc;
			for (int i = 0; i < MSG_STR_LEN_16; i++) errhost[i] = task_result.errhost[i];
			
			errstep			= task_result.errstep;
			errsubcode		= task_result.errsubcode;
			for (int i = 0; i < MSG_STR_LEN_64; i++) errstr[i] =  task_result.errstr[i];
			
			user_data1		= task_result.user_data1;
			user_data2		= task_result.user_data2;
		}

		public MSG_TASKRESULT clone() {
			MSG_TASKRESULT ret_msg = new MSG_TASKRESULT();
			ret_msg.clone(this);

			return ret_msg;
		}

		public void makeMsgTaskResult(int rtuid, int task_uuid, byte taskresult, 
									  byte errcode, byte errproc, String errhost, byte errstep, int errsubcode, String errstr, int user_data1, int user_data2) {
			this.rtuid			= rtuid;
			this.task_uuid		= task_uuid;
			this.taskresult		= taskresult;
			
			this.errcode		= errcode;
			this.errproc		= errproc;
			
			byte t_b[] = ComntFunc.string2Byte(errhost);
			
			for (int i = 0; i < 15; i++) {
				if (t_b != null && i < t_b.length) this.errhost[i] = t_b[i];
				else this.errhost[i] = 0;
			}
			this.errhost[15] = 0;
			
			this.errstep		= errstep;
			this.errsubcode		= errsubcode;
			
			byte t_c[] = ComntFunc.string2Byte(errstr);
			
			for (int i = 0; i < 63; i++) {
				if (t_c != null && i < t_c.length) this.errstr[i] = t_c[i];
				else this.errstr[i] = 0;
			}
			this.errstr[63] = 0;
			
			this.user_data1     = user_data1;
			this.user_data2     = user_data1;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;

			ret_len += ComntStream.writeStream(byte_vect, rtuid);
			ret_len += ComntStream.writeStream(byte_vect, task_uuid);
			ret_len += ComntStream.writeStream(byte_vect, taskresult);
			
			ret_len += ComntStream.writeStream(byte_vect, errcode); 
			ret_len += ComntStream.writeStream(byte_vect, errproc);
			ret_len += ComntStream.writeStream(byte_vect, errhost, 0, errhost.length);
			
			ret_len += ComntStream.writeStream(byte_vect, errstep); 
			ret_len += ComntStream.writeStream(byte_vect, errsubcode);
			ret_len += ComntStream.writeStream(byte_vect, errstr,  0, errstr.length);
			
			ret_len += ComntStream.writeStream(byte_vect, user_data1); 
			ret_len += ComntStream.writeStream(byte_vect, user_data2); 
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			rtuid = ComntStream.readStream(byte_vect, offset + ret_len, rtuid);
			ret_len += ComntStream.getDataSize(rtuid);

			task_uuid = ComntStream.readStream(byte_vect, offset + ret_len, task_uuid);
			ret_len += ComntStream.getDataSize(task_uuid);

			taskresult = ComntStream.readStream(byte_vect, offset + ret_len, taskresult);
			ret_len += ComntStream.getDataSize(taskresult);

			errcode = ComntStream.readStream(byte_vect, offset + ret_len, errcode);
			ret_len += ComntStream.getDataSize(errcode);

			errproc = ComntStream.readStream(byte_vect, offset + ret_len, errproc);
			ret_len += ComntStream.getDataSize(errproc);			
			
			ComntStream.readStream(byte_vect, offset + ret_len, errhost, 0, errhost.length);
			ret_len += ComntStream.getDataSize(errhost[0]) * errhost.length;						
			
			errstep = ComntStream.readStream(byte_vect, offset + ret_len, errstep);
			ret_len += ComntStream.getDataSize(errstep);

			errsubcode = ComntStream.readStream(byte_vect, offset + ret_len, errsubcode);
			ret_len += ComntStream.getDataSize(errsubcode);			
			
			ComntStream.readStream(byte_vect, offset + ret_len, errstr, 0, errstr.length);
			ret_len += ComntStream.getDataSize(errstr[0]) * errstr.length;						
			
			
			user_data1 = ComntStream.readStream(byte_vect, offset + ret_len, user_data1);
			ret_len += ComntStream.getDataSize(user_data1);

			user_data2 = ComntStream.readStream(byte_vect, offset + ret_len, user_data2);
			ret_len += ComntStream.getDataSize(user_data2);

			return ret_len;
		}
	}
	
	public static void makeTaskResultComntMsg(MSG_SERVER_STRUCT comnt_msg, MSG_TASKRESULT taskresult_msg, int uuid) {
		comnt_msg.msg_head.msg_type = ComntDef.YD_YFF_MSGTYPE_TASKRESULT;
		comnt_msg.msg_head.uuid     = uuid;
		
		taskresult_msg.toDataStream(comnt_msg.msg_body.byte_vect);
		
		comnt_msg.msg_head.data_len = comnt_msg.msg_body.byte_vect.size();		
	}
	
	public static void makeTaskResultComntMsg(MSG_SERVER_STRUCT comnt_msg, int uuid, int rtuid, byte task_uuid, byte taskresult, 
											  byte errcode, byte errproc, String errhost, byte errstep, int errsubcode, String errstr, int user_data1, int user_data2) {
		MSG_TASKRESULT taskresult_msg = new MSG_TASKRESULT();
		taskresult_msg.makeMsgTaskResult(rtuid, task_uuid, taskresult, errcode, errproc, errhost, errstep, errsubcode, errstr, user_data1, user_data2);
		
		makeTaskResultComntMsg(comnt_msg, taskresult_msg, uuid);
	}
	
	public static void makeTaskResultComntMsg(MSG_SERVER_STRUCT comnt_msg, MSG_CLIENT_STRUCT src_comnt_msg, 
												byte taskresult, int task_uuid, byte errcode, byte errproc, String errhost, byte errstep, int errsubcode, String errstr) {		
		MSG_TASK task_msg = new MSG_TASK();
		task_msg.fromDataStream(src_comnt_msg.msg_body.byte_vect, 0);
		
		MSG_TASKRESULT taskresult_msg = new MSG_TASKRESULT(); 
		
		if (src_comnt_msg.msg_head.msg_type == ComntDef.YD_YFF_MSGTYPE_TASK) {	
			taskresult_msg.makeMsgTaskResult(task_msg.rtuid,  task_uuid, taskresult, errcode, errproc, errhost, errstep, errsubcode, errstr, 0, 0);
		}
		else {
			taskresult_msg.makeMsgTaskResult((int)-1, task_uuid, taskresult, errcode, errproc, errhost, errstep, errsubcode, errstr, 0, 0);			
		}
		
		makeTaskResultComntMsg(comnt_msg, taskresult_msg, src_comnt_msg.msg_head.uuid);
	}

	public static class MSG_RESULT_DATA implements IMSG_READWRITESTREAM {
		public short	datatype	= 0;
		public int 		task_uuid	= 0;
		public ComntVector.ByteVector data_vect = new ComntVector.ByteVector();
		
		public void clean() {
			datatype	= 0;
			task_uuid	= 0;
			data_vect.clear();
		}
		
		public void clone(MSG_RESULT_DATA msg) {
			datatype	= msg.datatype;
			task_uuid	= msg.task_uuid;

			data_vect.clone(msg.data_vect);				
		}
		
		public MSG_RESULT_DATA clone() {
			MSG_RESULT_DATA ret_msg = new MSG_RESULT_DATA();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			int vect_size = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, datatype);
			ret_len += ComntStream.writeStream(byte_vect, task_uuid);
			
			vect_size = data_vect.size();
			ret_len += ComntStream.writeStream(byte_vect, vect_size);
			ret_len += ComntStream.writeStream(byte_vect, data_vect.getaddr(), 0, vect_size); 
			
			return ret_len;
		}		
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			int vect_size = 0;
			
			datatype = ComntStream.readStream(byte_vect, offset + ret_len, datatype); 
			ret_len += ComntStream.getDataSize(datatype);
			
			task_uuid = ComntStream.readStream(byte_vect, offset + ret_len, task_uuid); 
			ret_len += ComntStream.getDataSize(task_uuid);
			
			vect_size = ComntStream.readStream(byte_vect, offset + ret_len, vect_size);
			ret_len += ComntStream.getDataSize(vect_size);
			
			data_vect.resize(vect_size);
			ComntStream.readStream(byte_vect, offset + ret_len, data_vect.getaddr(), 0, vect_size);
			ret_len += ComntStream.getDataSize((byte)1) * vect_size;
			
			return ret_len;
		}						
	}
	//MakeRawDataMsg 函数没有写
	//通讯报文 CYFF_RAWDATAMSG  
	public static class MSG_RESULT_RAWDATA implements IMSG_READWRITESTREAM {
		public int		rtuid			= 0;
		public short	subid			= 0;
		public int		task_uuid		= 0;

		public byte		rawdatatype		= 0;
		
		public ComntVector.ByteVector data_buf		= new ComntVector.ByteVector();
		
		public void clean() {
			rtuid			= 0;
			subid			= 0;
			task_uuid		= 0;

			rawdatatype		= 0;
			
			data_buf.clear();
		}
		
		public void clone(MSG_RESULT_RAWDATA msg) {
			rtuid			= msg.rtuid;
			subid			= msg.subid;

			task_uuid		= msg.task_uuid;
			rawdatatype		= msg.rawdatatype;
			
			data_buf.clone(msg.data_buf);
		}
		
		public MSG_RESULT_RAWDATA clone() {
			MSG_RESULT_RAWDATA ret_msg = new MSG_RESULT_RAWDATA();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			int vect_size = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, rtuid);
			ret_len += ComntStream.writeStream(byte_vect, subid);
			ret_len += ComntStream.writeStream(byte_vect, task_uuid);
			ret_len += ComntStream.writeStream(byte_vect, rawdatatype);
	
			vect_size = data_buf.size();
			ret_len += ComntStream.writeStream(byte_vect, vect_size);
			ret_len += ComntStream.writeStream(byte_vect, data_buf.getaddr(), 0, vect_size); 

			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			int vect_size = 0;

			rtuid = ComntStream.readStream(byte_vect, offset + ret_len, rtuid); 
			ret_len += ComntStream.getDataSize(rtuid);

			subid = ComntStream.readStream(byte_vect, offset + ret_len, subid); 
			ret_len += ComntStream.getDataSize(subid);			

			task_uuid = ComntStream.readStream(byte_vect, offset + ret_len, task_uuid); 
			ret_len += ComntStream.getDataSize(task_uuid);

			rawdatatype = ComntStream.readStream(byte_vect, offset + ret_len, rawdatatype); 
			ret_len += ComntStream.getDataSize(rawdatatype);

			vect_size = ComntStream.readStream(byte_vect, offset + ret_len, vect_size);
			ret_len += ComntStream.getDataSize(vect_size);

			data_buf.resize(vect_size);
			ComntStream.readStream(byte_vect, offset + ret_len, data_buf.getaddr(), 0, vect_size);
			ret_len += ComntStream.getDataSize((byte)1) * vect_size;

			return ret_len;
		}		
	}
	
	public static class MSG_RTUSTATE_ITEM {
		public int		rtu_id			= -1;
		public byte		comm_state		= 0;	//RTU通讯状态
		public short    frame_ok_cnt	= 0;	//正确侦记数
		public short    frame_err_cnt	= 0;	//错误侦记数
		public int		last_time		= 0;	//RTU最后通讯时间
		public int		ip_addr			= 0;	//当前IP地址
		public byte		run_state		= 0;	//当前运行状态
		public byte		autotask_num	= 0;	//自动任务数量
		public byte		mantask_num		= 0;	//手工任务数量		
		public short    rx_cnt			= 0;	//接收数据量
		public short    tx_cnt			= 0;	//发送数据量
		
		public void clean() {
			rtu_id			= -1;
			comm_state		= 0;		//RTU通讯状态
			frame_ok_cnt	= 0;		//正确侦记数
			frame_err_cnt	= 0;		//错误侦记数		
			last_time		= 0;		//RTU最后通讯时间
			ip_addr			= 0;		//当前IP地址
			run_state		= 0;		//当前运行状态
			autotask_num	= 0;		//自动任务数量
			mantask_num		= 0;		//手工任务数量
			rx_cnt			= 0;		//接收数据量
			tx_cnt			= 0;		//发送数据量
		}
		
		public void clone(MSG_RTUSTATE_ITEM msg) {
			rtu_id			= msg.rtu_id;
			comm_state		= msg.comm_state;	//RTU通讯状态
			frame_ok_cnt	= msg.frame_ok_cnt;	 //正确侦记数
			frame_err_cnt	= msg.frame_err_cnt; //错误侦记数				
			last_time		= msg.last_time;	//RTU最后通讯时间
			ip_addr			= msg.ip_addr;		//当前IP地址
			run_state		= msg.run_state;	//当前运行状态
			autotask_num	= msg.autotask_num;	//自动任务数量
			mantask_num		= msg.mantask_num;	//手工任务数量
			rx_cnt			= msg.rx_cnt;		//接收数据量
			tx_cnt			= msg.tx_cnt;		//发送数据量
		}
		
		public MSG_RTUSTATE_ITEM clone() {
			MSG_RTUSTATE_ITEM ret_msg = new MSG_RTUSTATE_ITEM();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, rtu_id);
			ret_len += ComntStream.writeStream(byte_vect, comm_state);
			ret_len += ComntStream.writeStream(byte_vect, frame_ok_cnt);
			ret_len += ComntStream.writeStream(byte_vect, frame_err_cnt);			
			ret_len += ComntStream.writeStream(byte_vect, last_time);
			ret_len += ComntStream.writeStream(byte_vect, ip_addr);
			ret_len += ComntStream.writeStream(byte_vect, run_state);
			ret_len += ComntStream.writeStream(byte_vect, autotask_num);
			ret_len += ComntStream.writeStream(byte_vect, rx_cnt);
			ret_len += ComntStream.writeStream(byte_vect, tx_cnt);			
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			rtu_id = ComntStream.readStream(byte_vect, offset + ret_len, rtu_id); 
			ret_len += ComntStream.getDataSize(rtu_id);
			
			comm_state = ComntStream.readStream(byte_vect, offset + ret_len, comm_state); 
			ret_len += ComntStream.getDataSize(comm_state);
	
			frame_ok_cnt = ComntStream.readStream(byte_vect, offset + ret_len, frame_ok_cnt);
			ret_len += ComntStream.getDataSize(frame_ok_cnt);			
			
			frame_err_cnt = ComntStream.readStream(byte_vect, offset + ret_len, frame_err_cnt);
			ret_len += ComntStream.getDataSize(frame_err_cnt);			
			
			last_time = ComntStream.readStream(byte_vect, offset + ret_len, last_time); 
			ret_len += ComntStream.getDataSize(last_time);
			
			ip_addr = ComntStream.readStream(byte_vect, offset + ret_len, ip_addr); 
			ret_len += ComntStream.getDataSize(ip_addr);
			
			run_state = ComntStream.readStream(byte_vect, offset + ret_len, run_state); 
			ret_len += ComntStream.getDataSize(run_state);
			
			autotask_num = ComntStream.readStream(byte_vect, offset + ret_len, autotask_num); 
			ret_len += ComntStream.getDataSize(autotask_num);

			mantask_num = ComntStream.readStream(byte_vect, offset + ret_len, mantask_num); 
			ret_len += ComntStream.getDataSize(mantask_num);			
			
			rx_cnt = ComntStream.readStream(byte_vect, offset + ret_len, rx_cnt); 
			ret_len += ComntStream.getDataSize(rx_cnt);
			
			tx_cnt = ComntStream.readStream(byte_vect, offset + ret_len, tx_cnt); 
			ret_len += ComntStream.getDataSize(tx_cnt);
			
			return ret_len;
		}				
	}
	
	public static class MSG_RTUSTATE implements IMSG_READWRITESTREAM  {
		public MSG_RTUSTATE_ITEM[] rtuitem_vect = null;
		
		public void clean() {
			rtuitem_vect = null;
		}
		
		public void clone(MSG_RTUSTATE msg) {
			if (msg.rtuitem_vect == null) {
				msg.rtuitem_vect = null;
			}
			else {
				if (rtuitem_vect == null || rtuitem_vect.length != msg.rtuitem_vect.length)  rtuitem_vect = new MSG_RTUSTATE_ITEM [msg.rtuitem_vect.length];				
				for (int i = 0; i < rtuitem_vect.length; i++)  rtuitem_vect[i] = msg.rtuitem_vect[i].clone();				
			}						
		}
		
		public MSG_RTUSTATE clone() {
			MSG_RTUSTATE ret_msg = new MSG_RTUSTATE();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			int i = 0, vect_size = 0;
			
			vect_size = rtuitem_vect.length;
			ret_len += ComntStream.writeStream(byte_vect, vect_size);
			
			for (i = 0; i < vect_size; i++) {
				ret_len += rtuitem_vect[i].toDataStream(byte_vect);
			}
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			int i = 0, vect_size = 0;

			vect_size = ComntStream.readStream(byte_vect, offset + ret_len, vect_size); 
			ret_len += ComntStream.getDataSize(vect_size);	
			
			rtuitem_vect = new MSG_RTUSTATE_ITEM[vect_size];
			for (i = 0; i < vect_size; i++) {
				rtuitem_vect[i] = new MSG_RTUSTATE_ITEM();
				ret_len += rtuitem_vect[i].fromDataStream(byte_vect, offset + ret_len);
			}
			
			return ret_len;
		}						
	}
	
	public static class MSG_RELOADPARA implements IMSG_READWRITESTREAM  {
		public byte[]     reload_mask = new byte[MSG_STR_LEN_32];
		
		public void clean() {
			for (int i = 0; i < MSG_STR_LEN_32; i++) reload_mask[i] = 0;
		}
		
		public void clone(MSG_RELOADPARA msg) {
			for (int i = 0; i < MSG_STR_LEN_32; i++) {
				reload_mask[i] = msg.reload_mask[i];
			}
		}
		
		public MSG_RELOADPARA clone() {
			MSG_RELOADPARA ret_msg = new MSG_RELOADPARA();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public void makeCancelManTaskMsg(byte[] reload_code) {
			for (int i = 0; i < MSG_STR_LEN_32; i++) {
				this.reload_mask[i] = reload_code[i];
			}
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, reload_mask, 0, reload_mask.length);
			
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ComntStream.readStream(byte_vect, offset + ret_len, reload_mask, 0, reload_mask.length);
			ret_len += ComntStream.getDataSize(reload_mask[0]) * reload_mask.length;

			return ret_len;
		}
	}
	
	public static void makeReloadParaComntMsg(MSG_CLIENT_STRUCT comnt_msg, MSG_RELOADPARA reload_msg, int uuid, String user_name, String version) {		
		reload_msg.toDataStream(comnt_msg.msg_body.byte_vect);
		comnt_msg.msg_head.makeMsgHead(ComntDef.YD_YFF_MSGTYPE_RELOADPARA, uuid, comnt_msg.msg_body.byte_vect.size(), 0, user_name, version);
	}

	public static void makeReloadParaComntMsg(MSG_CLIENT_STRUCT comnt_msg, int uuid, String user_name, byte[] reload_mask, String version) {
		MSG_RELOADPARA reload_msg = new MSG_RELOADPARA();
		reload_msg.makeCancelManTaskMsg(reload_mask);
		
		makeReloadParaComntMsg(comnt_msg, reload_msg, uuid, user_name, version);
	}	
	
	
	//读一类 二类数据
	public static class MSG_READDATA implements IMSG_READWRITESTREAM {

		public short	mpid		= 0;
		public int		ymd			= 0;
		public short	datatype	= 0;

		public void clean() {
			mpid		= 0;
			ymd			= 0;
			datatype	= 0;
		}
		
		public void clone(MSG_READDATA msg) {
			mpid		= msg.mpid;
			ymd			= msg.ymd;
			datatype	= msg.datatype;
		}
		
		public MSG_READDATA clone() {
			MSG_READDATA ret_msg = new MSG_READDATA();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;

			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, ymd);
			ret_len += ComntStream.writeStream(byte_vect, datatype);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid); 
			ret_len += ComntStream.getDataSize(mpid);
			ymd = ComntStream.readStream(byte_vect, offset + ret_len, ymd); 
			ret_len += ComntStream.getDataSize(ymd);
			datatype = ComntStream.readStream(byte_vect, offset + ret_len, datatype); 
			ret_len += ComntStream.getDataSize(datatype);
			
			return ret_len;
		}
	
	}
	
	//
	public static class MSG_CANCELMANTASK implements IMSG_READWRITESTREAM {

		public short	bak_msg_chanidx	= 0;
		public short	bak_msg_procidx	= 0;
		public int		task_uuid		= 0;

		public void clean() {
			bak_msg_chanidx		= 0;
			bak_msg_procidx			= 0;
			task_uuid	= 0;
		}
		
		public void clone(MSG_CANCELMANTASK msg) {
			bak_msg_chanidx		= msg.bak_msg_chanidx;
			bak_msg_procidx			= msg.bak_msg_procidx;
			task_uuid	= msg.task_uuid;
		}
		
		public MSG_CANCELMANTASK clone() {
			MSG_CANCELMANTASK ret_msg = new MSG_CANCELMANTASK();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;

			ret_len += ComntStream.writeStream(byte_vect, bak_msg_chanidx);
			ret_len += ComntStream.writeStream(byte_vect, bak_msg_procidx);
			ret_len += ComntStream.writeStream(byte_vect, task_uuid);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			bak_msg_chanidx = ComntStream.readStream(byte_vect, offset + ret_len, bak_msg_chanidx); 
			ret_len += ComntStream.getDataSize(bak_msg_chanidx);
			bak_msg_procidx = ComntStream.readStream(byte_vect, offset + ret_len, bak_msg_procidx); 
			ret_len += ComntStream.getDataSize(bak_msg_procidx);
			task_uuid = ComntStream.readStream(byte_vect, offset + ret_len, task_uuid); 
			ret_len += ComntStream.getDataSize(task_uuid);
			
			return ret_len;
		}	
		
		public void makeCancelManTaskMsg(int uuid) {
			this.task_uuid = uuid;
		}		
	}
	
	public static void makeCancelManComntMsg(MSG_CLIENT_STRUCT comnt_msg, MSG_CANCELMANTASK cancel_msg, int uuid, String user_name) {
		cancel_msg.toDataStream(comnt_msg.msg_body.byte_vect);
		
		comnt_msg.msg_head.makeMsgHead(ComntDef.YD_YFF_MSGTYPE_CANCELMANTASK, uuid, comnt_msg.msg_body.byte_vect.size(), 0, user_name, ComntDef.SALEMANAGER_VERSION);		
	}

	public static void makeCancelManComntMsg(MSG_CLIENT_STRUCT comnt_msg, int uuid, String user_name,  int cancel_uuid) {
		MSG_CANCELMANTASK cancel_msg = new MSG_CANCELMANTASK();
		cancel_msg.makeCancelManTaskMsg(cancel_uuid);
		
		makeCancelManComntMsg(comnt_msg, cancel_msg, uuid, user_name);
	}
	

	//得到-暂停预付费报警及控制
	public static class MSG_GET_PAUSEALARM implements IMSG_READWRITESTREAM {

		public byte[]	operman	= new byte[MSG_STR_LEN_64];
		public int		type	= 0;						//类型 0-低压 1-高压
		
		public void clean() {
			type	= 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = 0;
			}
		}
		
		public void clone(MSG_GET_PAUSEALARM msg) {
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			type	= msg.type;
		}
		
		public MSG_GET_PAUSEALARM clone() {
			MSG_GET_PAUSEALARM ret_msg = new MSG_GET_PAUSEALARM();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;

			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, type);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			type = ComntStream.readStream(byte_vect, offset + ret_len, type); 
			ret_len += ComntStream.getDataSize(type);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			return ret_len;
		}
	}
	
	//设置-暂停预付费报警及控制
	public static class MSG_SET_PAUSEALARM implements IMSG_READWRITESTREAM {

		public byte[]	operman	= new byte[MSG_STR_LEN_64];
		public int		type	= 0;
		public int		mins	= 0;

		public void clean() {
			type		= 0;
			mins	= 0;

			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = 0;
			}
		}
		
		public void clone(MSG_SET_PAUSEALARM msg) {
			type		= msg.type;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			mins	= msg.mins;

		}
		
		public MSG_SET_PAUSEALARM clone() {
			MSG_SET_PAUSEALARM ret_msg = new MSG_SET_PAUSEALARM();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;

			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, type);
			ret_len += ComntStream.writeStream(byte_vect, mins);					
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			type = ComntStream.readStream(byte_vect, offset + ret_len, type); 
			ret_len += ComntStream.getDataSize(type);
			mins = ComntStream.readStream(byte_vect, offset + ret_len, mins); 
			ret_len += ComntStream.getDataSize(mins);
			
			return ret_len;
		}
	}
	
	//得到-全局保电参数
	public static class MSG_GET_GLOPROT implements IMSG_READWRITESTREAM {

		public byte[]	operman	= new byte[MSG_STR_LEN_64];
		public int		type	= 0;						//类型 0-低压 1-高压
		
		public void clean() {
			type	= 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = 0;
			}
		}
		
		public void clone(MSG_GET_GLOPROT msg) {
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			type	= msg.type;
		}
		
		public MSG_GET_GLOPROT clone() {
			MSG_GET_GLOPROT ret_msg = new MSG_GET_GLOPROT();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;

			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, type);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			type = ComntStream.readStream(byte_vect, offset + ret_len, type); 
			ret_len += ComntStream.getDataSize(type);
			
			return ret_len;
		}
	}
	
	//设置-全局保电参数
	public static class MSG_SET_GLOPROT implements IMSG_READWRITESTREAM {

		public byte[]	operman		= new byte[MSG_STR_LEN_64];
		public int		type		= 0;
		public byte		app_type	= 0;
		
		public byte		use_flag	= 0;					//使用标志
		public int		bg_date		= 0;					//启用日期-yyyymmdd
		public int		bg_time		= 0;					//启用时间-hhmiss
		public int		ed_date		= 0;					//结束日期-yyyymmdd
		public int		ed_time		= 0;					//结束时间-hhmiss
		
		public byte	reserve1[] = new byte[MSG_STR_LEN_64];	//扩展字段1
		public byte	reserve2[] = new byte[MSG_STR_LEN_64];	//扩展字段2
		public byte reserve3[] = new byte[MSG_STR_LEN_64];	//扩展字段3
		public byte	reserve4[] = new byte[MSG_STR_LEN_64];	//扩展字段4
		
		public void clean() {
			type		= 0;
			app_type	= 0;

			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = 0;
				reserve1[i] = 0;
				reserve2[i] = 0;
				reserve3[i] = 0;
				reserve4[i] = 0;
			}
			
			use_flag	= 0;
			bg_date		= 0;
			bg_time		= 0;
			ed_date		= 0;
			ed_time		= 0;
		}
		
		public void clone(MSG_SET_GLOPROT msg) {
			type		= msg.type;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
				reserve1[i] = msg.reserve1[i];
				reserve2[i] = msg.reserve2[i];
				reserve3[i] = msg.reserve3[i];
				reserve4[i] = msg.reserve4[i];
			}
			app_type	= msg.app_type;
			
			use_flag	= msg.use_flag;
			bg_date		= msg.bg_date;
			bg_time		= msg.bg_time;
			ed_date		= msg.ed_date;
			ed_time		= msg.ed_time;
		}
		
		public MSG_SET_GLOPROT clone() {
			MSG_SET_GLOPROT ret_msg = new MSG_SET_GLOPROT();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;

			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, type);
			ret_len += ComntStream.writeStream(byte_vect, app_type);

			ret_len += ComntStream.writeStream(byte_vect, use_flag);
			ret_len += ComntStream.writeStream(byte_vect, bg_date);
			ret_len += ComntStream.writeStream(byte_vect, bg_time);
			ret_len += ComntStream.writeStream(byte_vect, ed_date);
			ret_len += ComntStream.writeStream(byte_vect, ed_time);
			
			ret_len += ComntStream.writeStream(byte_vect, reserve1, 0, reserve1.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve2, 0, reserve2.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve3, 0, reserve3.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve4, 0, reserve4.length);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			type = ComntStream.readStream(byte_vect, offset + ret_len, type); 
			ret_len += ComntStream.getDataSize(type);
			
			app_type = ComntStream.readStream(byte_vect, offset + ret_len, app_type); 
			ret_len += ComntStream.getDataSize(app_type);
			
			use_flag = ComntStream.readStream(byte_vect, offset + ret_len, use_flag); 
			ret_len += ComntStream.getDataSize(use_flag);
			
			bg_date = ComntStream.readStream(byte_vect, offset + ret_len, bg_date); 
			ret_len += ComntStream.getDataSize(bg_date);
			
			bg_time = ComntStream.readStream(byte_vect, offset + ret_len, bg_time); 
			ret_len += ComntStream.getDataSize(bg_time);
			
			ed_date = ComntStream.readStream(byte_vect, offset + ret_len, ed_date); 
			ret_len += ComntStream.getDataSize(ed_date);
			
			ed_time = ComntStream.readStream(byte_vect, offset + ret_len, ed_time); 
			ret_len += ComntStream.getDataSize(ed_time);
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve1, 0, reserve1.length);
			ret_len += ComntStream.getDataSize(reserve1[0]) * reserve1.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve2, 0, reserve2.length);
			ret_len += ComntStream.getDataSize(reserve2[0]) * reserve2.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve3, 0, reserve3.length);
			ret_len += ComntStream.getDataSize(reserve3[0]) * reserve3.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve4, 0, reserve4.length);
			ret_len += ComntStream.getDataSize(reserve4[0]) * reserve4.length;
			return ret_len;
		}
	}

	//设置-链路测试
	public static class MSG_TEST implements IMSG_READWRITESTREAM {
		public byte		one_byte	= 0;
		
		public void clean() {
			one_byte	= 0;
		}
		
		public void clone(MSG_TEST msg) {
			one_byte	= msg.one_byte;
		}
		
		public MSG_TEST clone() {
			MSG_TEST ret_msg = new MSG_TEST();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			ret_len += ComntStream.writeStream(byte_vect, one_byte);			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			one_byte = ComntStream.readStream(byte_vect, offset + ret_len, one_byte); 
			ret_len += ComntStream.getDataSize(one_byte);
			
			return ret_len;
		}
	}
	
	//设置-链路测试
	public static class MSG_QUERYRTUSTATE implements IMSG_READWRITESTREAM {
		public byte		one_byte	= 0;
		
		public void clean() {
			one_byte	= 0;
		}
		
		public void clone(MSG_QUERYRTUSTATE msg) {
			one_byte	= msg.one_byte;
		}
		
		public MSG_QUERYRTUSTATE clone() {
			MSG_QUERYRTUSTATE ret_msg = new MSG_QUERYRTUSTATE();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			ret_len += ComntStream.writeStream(byte_vect, one_byte);			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			one_byte = ComntStream.readStream(byte_vect, offset + ret_len, one_byte); 
			ret_len += ComntStream.getDataSize(one_byte);
			
			return ret_len;
		}
	}
	
}
