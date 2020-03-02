package com.kesd.comnt;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import com.libweb.comnt.ComntFunc;
import com.libweb.comnt.ComntStream;
import com.libweb.comnt.ComntVector;

public class ComntMsgMisGS {
	
	public static final Map<Integer, String> YFFMIS_YHFL = new HashMap<Integer, String>();
	static{
		YFFMIS_YHFL.put(1, "高压");
		YFFMIS_YHFL.put(2, "低压非居民");
		YFFMIS_YHFL.put(3, "低压居民");
	}
	
	public static final int YFFMIS_MSGTYPE_NULL							= 0;		//NULL
	public static final int YFFMIS_MSGTYPE_DY_QUERYPOWER				= 1;		//低压-电费查询
	public static final int YFFMIS_MSGTYPE_DY_PAY						= 2;		//低压-电费收费
	public static final int YFFMIS_MSGTYPE_DY_REVER						= 3;		//低压-冲正

	public static final int YFFMIS_MSGTYPE_GY_QUERYPOWER				= 11;		//高压-电费查询
	public static final int YFFMIS_MSGTYPE_GY_PAY						= 12;		//高压-电费收费
	public static final int YFFMIS_MSGTYPE_GY_REVER						= 13;		//高压-冲正

	public static final int YFFMIS_MSGTYPE_GDY_CHECKPAY					= 90;		//高低压对账
	public static final int YFFMIS_MSGTYPE_TASKRESULT					= 100;		//执行任务结果

	//错误码
	public static final int YFFMIS_ERR_SUECCED							= 0;		//成功
	public static final int YFFMIS_ERR_MISNOUSE							= 701;		//终端未使用MIS接口
	public static final int YFFMIS_ERR_DATABASE							= 702;		//数据库操作错误
	public static final int YFFMIS_ERR_CHECKPAYING						= 703;		//正在对账,请稍等
	public static final int YFFMIS_ERR_NOREVERCORD						= 704;		//无冲正记录
	public static final int YFFMIS_ERR_AUTOCHECK						= 705;		//自动对账模式            
	public static final int YFFMIS_ERR_HASCHECK							= 706;		//不能重复对账            
	public static final int YFFMIS_ERR_CHECKHIST						= 707;		//仅能对历史记录对账      
	public static final int YFFMIS_ERR_DBFILE							= 708;		//读取历史记录存本地文件错误
	public static final int YFFMIS_ERR_FTP								= 709;		//FTP文件错误      
	public static final int YFFMIS_ERR_MISQUERY							= 711;		//连接MIS请求错误

	//MIS操作返回码
	public static final int YFFMIS_MISOP_OKNO							= 1001;		//MIS调用正确标志
	public static final int YFFMIS_MISOP_HN_PRINTNODATA					= 1099;		//打印返回无数据
	
	//预付费接口消息
	public static class YFFMIS_MSG implements ComntMsg.IMSG_READWRITESTREAM {
		
		public 	int		msgtype			= 0;
		public 	short	src_msg_chanidx	= 0;
		public	short	src_msg_procidx	= 0;

		public	int		task_uuid		= 0;
		public ComntVector.ByteVector data_vect = new ComntVector.ByteVector();
		
		public void clean() {
			msgtype			= 0;
			src_msg_chanidx	= 0;
			src_msg_procidx	= 0;
			task_uuid		= 0;
			data_vect.clear();
		}
		
		public void clone(YFFMIS_MSG msg) {
			msgtype			= msg.msgtype;
			src_msg_chanidx	= msg.src_msg_chanidx;
			src_msg_procidx	= msg.src_msg_procidx;
			task_uuid		= msg.task_uuid;
			data_vect.clone(msg.data_vect);
		}
		
		public YFFMIS_MSG clone() {
			YFFMIS_MSG ret_msg = new YFFMIS_MSG();
			ret_msg.clone(this);
			
			return ret_msg;
		}
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			int vect_size = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, msgtype);
			ret_len += ComntStream.writeStream(byte_vect, src_msg_chanidx);
			ret_len += ComntStream.writeStream(byte_vect, src_msg_procidx);
			ret_len += ComntStream.writeStream(byte_vect, task_uuid);
			
			vect_size = data_vect.size();
			ret_len += ComntStream.writeStream(byte_vect, vect_size);
			ret_len += ComntStream.writeStream(byte_vect, data_vect.getaddr(), 0, vect_size); 
						
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len 	= 0;
			int vect_size 	= 0;
			
			msgtype = ComntStream.readStream(byte_vect, offset + ret_len, msgtype); 
			ret_len += ComntStream.getDataSize(msgtype);

			src_msg_chanidx = ComntStream.readStream(byte_vect, offset + ret_len, src_msg_chanidx); 
			ret_len += ComntStream.getDataSize(src_msg_chanidx);			

			src_msg_procidx = ComntStream.readStream(byte_vect, offset + ret_len, src_msg_procidx); 
			ret_len += ComntStream.getDataSize(src_msg_procidx);

			task_uuid = ComntStream.readStream(byte_vect, offset + ret_len, task_uuid); 
			ret_len += ComntStream.getDataSize(task_uuid);
			
			data_vect.resize(vect_size);
			ComntStream.readStream(byte_vect, offset + ret_len, data_vect.getaddr(), 0, vect_size);
			ret_len += ComntStream.getDataSize((byte)1) * vect_size;
			
			return ret_len;
		}
	}
	
	//发送给任务源的消息
	public static class YFFMISMSG_TASKRESULT implements ComntMsg.IMSG_READWRITESTREAM {
		
		public 	int		rtuid		= 0;
		public  byte	taskresult	= 0;

		public 	int		errcode		= 0;
		public	byte	errproc		= 0;
		public 	byte[]	errhost		= new byte[ComntMsg.MSG_STR_LEN_16];
		
		public	int		userdata1	= 0;
		public	int		userdata2	= 0;

		
		public void clean() {
			rtuid		= 0;
			taskresult	= 0;
			errcode		= 0;
			errproc		= 0;
			
			for (int i = 0; i < ComntMsg.MSG_STR_LEN_16; i++) errhost[i] = 0;
			
			userdata1	= 0;
			userdata2	= 0;
		}
		
		public void clone(YFFMISMSG_TASKRESULT msg) {
			rtuid		= msg.rtuid;
			taskresult	= msg.taskresult;
			errcode		= msg.errcode;
			errproc		= msg.errproc;
			
			for (int i = 0; i < ComntMsg.MSG_STR_LEN_16; i++) {
				errhost[i] = msg.errhost[i];
			}
			
			userdata1	= msg.userdata1;
			userdata2	= msg.userdata2;
			
		}
		
		public YFFMISMSG_TASKRESULT clone() {
			YFFMISMSG_TASKRESULT ret_msg = new YFFMISMSG_TASKRESULT();
			ret_msg.clone(this);
			
			return ret_msg;
		}
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, rtuid);
			ret_len += ComntStream.writeStream(byte_vect, taskresult);
			ret_len += ComntStream.writeStream(byte_vect, errcode);
			ret_len += ComntStream.writeStream(byte_vect, errproc);
			
			ret_len += ComntStream.writeStream(byte_vect, errhost, 0, errhost.length);
			
			ret_len += ComntStream.writeStream(byte_vect, userdata1);
			ret_len += ComntStream.writeStream(byte_vect, userdata2); 
						
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len 	= 0;

			rtuid = ComntStream.readStream(byte_vect, offset + ret_len, rtuid); 
			ret_len += ComntStream.getDataSize(rtuid);

			taskresult = ComntStream.readStream(byte_vect, offset + ret_len, taskresult); 
			ret_len += ComntStream.getDataSize(taskresult);			

			errcode = ComntStream.readStream(byte_vect, offset + ret_len, errcode); 
			ret_len += ComntStream.getDataSize(errcode);

			errproc = ComntStream.readStream(byte_vect, offset + ret_len, errproc); 
			ret_len += ComntStream.getDataSize(errproc);
			
			ComntStream.readStream(byte_vect, offset + ret_len, errhost, 0, errhost.length);
			ret_len += ComntStream.getDataSize(errhost[0]) * errhost.length;
			
			userdata1 = ComntStream.readStream(byte_vect, offset + ret_len, userdata1); 
			ret_len += ComntStream.getDataSize(userdata1);
			
			userdata2 = ComntStream.readStream(byte_vect, offset + ret_len, userdata2); 
			ret_len += ComntStream.getDataSize(userdata2);
			
			return ret_len;
		}		
	}

	//低压查询缴费
	public static class YFFMISMSG_DY_QUERYPOWER_GS implements ComntMsg.IMSG_READWRITESTREAM {
		public int		rtu_id 	= 0;									//终端编号
		public short	sub_id 	= 0;									//总加组编号/测量点编号

		public byte[]	dsdwbh 	= new byte[ComntMsg.MSG_STR_LEN_20];	//代收单位编号
		public byte[]	czybh  	= new byte[ComntMsg.MSG_STR_LEN_20];	//操作员编号
		public byte[]	yhbh	 	= new byte[ComntMsg.MSG_STR_LEN_20];	//用户编号
		public byte		cxtj	 	= 0;									//查询条件
		public byte		cxlb	 	= 0;									//查询类别

		public byte[]	ccbh	 	= new byte[ComntMsg.MSG_STR_LEN_40];	//出厂编号
		public byte[]	reserve1 	= new byte[ComntMsg.MSG_STR_LEN_64];	//备用1
		public byte[]	reserve2 	= new byte[ComntMsg.MSG_STR_LEN_64];	//备用2
		
		public void clean() {
			rtu_id	= 0;
			sub_id	= 0;
			
			for (int i = 0; i < dsdwbh.length; i++) 	dsdwbh[i] = 0;
			for (int i = 0; i < czybh.length; i++) 		czybh[i] = 0;
			for (int i = 0; i < yhbh.length; i++) 		yhbh[i] = 0;
			
			cxtj	 	= 0;
			cxlb	 	= 0;
			
			for (int i = 0; i < ccbh.length; i++) 	ccbh[i] = 0;
			for (int i = 0; i < reserve1.length; i++) reserve1[i] = 0;
			for (int i = 0; i < reserve2.length; i++) reserve2[i] = 0;		
		}
		
		public void clone(YFFMISMSG_DY_QUERYPOWER_GS msg) {
			rtu_id	= 	msg.rtu_id;
			sub_id	= 	msg.sub_id;
			
			for (int i = 0; i < dsdwbh.length; i++) 	dsdwbh[i] = msg.dsdwbh[i];
			for (int i = 0; i < czybh.length; i++) 		czybh[i] 	= msg.czybh[i];
			for (int i = 0; i < yhbh.length; i++) 		yhbh[i] 	= msg.yhbh[i];
			
			cxtj	= msg.cxtj;
			cxlb	= msg.cxlb;
			
			for (int i = 0; i < ccbh.length; i++) 		ccbh[i] 	  	= msg.ccbh[i];
			for (int i = 0; i < reserve1.length; i++) 	reserve1[i] 	= msg.reserve1[i];
			for (int i = 0; i < reserve2.length; i++) 	reserve2[i] 	= msg.reserve2[i];
		}
		
		public YFFMISMSG_DY_QUERYPOWER_GS clone() {
			YFFMISMSG_DY_QUERYPOWER_GS ret_msg = new YFFMISMSG_DY_QUERYPOWER_GS();
			ret_msg.clone(this);
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, rtu_id);
			ret_len += ComntStream.writeStream(byte_vect, sub_id);
			
			ret_len += ComntStream.writeStream(byte_vect, dsdwbh, 0, dsdwbh.length);
			ret_len += ComntStream.writeStream(byte_vect, czybh, 	0, czybh.length);
			ret_len += ComntStream.writeStream(byte_vect, yhbh, 	0, yhbh.length);
			
			ret_len += ComntStream.writeStream(byte_vect, cxtj);
			ret_len += ComntStream.writeStream(byte_vect, cxlb);
			
			ret_len += ComntStream.writeStream(byte_vect, ccbh, 	  0, ccbh.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve1, 0, reserve1.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve2, 0, reserve2.length);
					
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len 	= 0;

			rtu_id = ComntStream.readStream(byte_vect, offset + ret_len, rtu_id); 
			ret_len += ComntStream.getDataSize(rtu_id);

			sub_id = ComntStream.readStream(byte_vect, offset + ret_len, sub_id); 
			ret_len += ComntStream.getDataSize(sub_id);			

			ComntStream.readStream(byte_vect, offset + ret_len, dsdwbh, 0, dsdwbh.length);
			ret_len += ComntStream.getDataSize(dsdwbh[0]) * dsdwbh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, czybh, 0, czybh.length);
			ret_len += ComntStream.getDataSize(czybh[0]) * czybh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, yhbh, 0, yhbh.length);
			ret_len += ComntStream.getDataSize(yhbh[0]) * yhbh.length;
			
			cxtj = ComntStream.readStream(byte_vect, offset + ret_len, cxtj); 
			ret_len += ComntStream.getDataSize(cxtj);

			cxlb = ComntStream.readStream(byte_vect, offset + ret_len, cxlb); 
			ret_len += ComntStream.getDataSize(cxlb);
			
			ComntStream.readStream(byte_vect, offset + ret_len, ccbh, 0, ccbh.length);
			ret_len += ComntStream.getDataSize(ccbh[0]) * ccbh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve1, 0, reserve1.length);
			ret_len += ComntStream.getDataSize(reserve1[0]) * reserve1.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve2, 0, reserve2.length);
			ret_len += ComntStream.getDataSize(reserve2[0]) * reserve2.length;
			
			return ret_len;
		}	
	}
	
	
	//低压查询缴费结果
	public static class YFFMISMSG_DY_QUERYPOWER_RESULT_GS implements ComntMsg.IMSG_READWRITESTREAM {
		public static class QUERYPOWER_RESULT_DETAIL {
			public  byte[]  yhbh  = new byte[ComntMsg.MSG_STR_LEN_20];				//用户编号
			public 	byte[]  ysbs  = new byte[ComntMsg.MSG_STR_LEN_20];				//应收标识
			public  int		dfny  = 0;												//电费年月

			public  double	yjje  = 0.0	;											//应缴金额
			public  double	dfje  = 0.0;											//电费金额
			public  double	wyjje = 0.0;											//违约金金额
			
			public  double	sctw  = 0.0;											//上次调尾
			public  double	bctw  = 0.0;											//本次调尾

			public  byte[]  yszt  	  =   new byte[ComntMsg.MSG_STR_LEN_8];			//*应收状态
			public  byte[]  xzsx  	  =   new byte[ComntMsg.MSG_STR_LEN_8];			//销账顺序
			public  byte[]  reserve	  =   new byte[ComntMsg.MSG_STR_LEN_80];		//备用
		}
		
		public int  	retcode 	=	0;											//返回代码
		public byte[] 	retmsg  	=	new byte[ComntMsg.MSG_STR_LEN_60];			//返回消息
		public int		jezbs	  	= 	0;											//金额总笔数
		public byte[] 	dzpc	  	=	new byte[ComntMsg.MSG_STR_LEN_20];			//对账批次
		public byte[] 	hsdw    	= 	new byte[ComntMsg.MSG_STR_LEN_20];			//核算单位

		public byte[] 	yhbh		= 	new byte[ComntMsg.MSG_STR_LEN_20];			//用户编号
		public byte[] 	yhmc  		= 	new byte[ComntMsg.MSG_STR_LEN_60];			//用户名称
		public byte[] 	yddz  		= 	new byte[ComntMsg.MSG_STR_LEN_120];			//用电地址
		public double 	hjys  		= 	0.0;										//合计预收[预收余额]
		public double 	hjje  		= 	0.0;										//合计金额[应缴电费总金额]
		public byte[]	reserve 	= 	new byte[ComntMsg.MSG_STR_LEN_80];			//*预留字段
		
		public QUERYPOWER_RESULT_DETAIL details_vect[] = null;	
	
	
		public void clean() {
			retcode	= 0;
			for (int i = 0; i < retmsg.length; i++) 	retmsg[i] = 0;
			jezbs   = 0;
			for (int i = 0; i < dzpc.length; i++) 		dzpc[i] = 0;
		
			for (int i = 0; i < hsdw.length; i++) 		hsdw[i] = 0;
			for (int i = 0; i < yhbh.length; i++) 		yhbh[i] = 0;
			for (int i = 0; i < yhmc.length; i++) 		yhmc[i] = 0;
			for (int i = 0; i < yddz.length; i++) 		yddz[i] = 0;
			hjys	 = 0.0;
			hjje	 = 0.0;
			for (int i = 0; i < reserve.length; i++) 		reserve[i] = 0;
			
			for (int i = 0; i < details_vect.length; i++) {
				for (int j = 0; i < details_vect[i].yhbh.length; j++) 	details_vect[i].yhbh[j] = 0;
				for (int j = 0; i < details_vect[i].ysbs.length; j++) 	details_vect[i].ysbs[j] = 0;
				details_vect[i].dfny	= 0;
				
				details_vect[i].yjje	= 0.0;
				details_vect[i].dfje	= 0.0;
				details_vect[i].wyjje	= 0.0;
				details_vect[i].sctw  	= 0.0;
				details_vect[i].bctw  	= 0.0;
				
				for (int j = 0; i < details_vect[i].yszt.length; j++) 		details_vect[i].yszt[j] = 0;
				for (int j = 0; i < details_vect[i].xzsx.length; j++) 		details_vect[i].xzsx[j] = 0;
				for (int j = 0; i < details_vect[i].reserve.length; j++) 	details_vect[i].reserve[j] = 0;
			}
		}
		
		public void clone(YFFMISMSG_DY_QUERYPOWER_RESULT_GS msg) {
			retcode	= msg.retcode;
			for (int i = 0; i < retmsg.length; i++) 	retmsg[i] 	= msg.retmsg[i];
			jezbs   = msg.jezbs;
			for (int i = 0; i < dzpc.length; i++) 	dzpc[i] 		= msg.dzpc[i];
			
			for (int i = 0; i < hsdw.length; i++) 	hsdw[i] 		= msg.hsdw[i];
			for (int i = 0; i < yhbh.length; i++) 	yhbh[i] 		= msg.yhbh[i];
			for (int i = 0; i < yhmc.length; i++) 	yhmc[i] 		= msg.yhmc[i];
			for (int i = 0; i < yddz.length; i++) 	yddz[i] 		= msg.yddz[i];
			
			hjys	 = msg.hjys;
			hjje	 = msg.hjje;
			for (int i = 0; i < reserve.length; i++) 	reserve[i] 	= msg.reserve[i];
			
			
			//需要初始化数组长度
			details_vect = new QUERYPOWER_RESULT_DETAIL[msg.details_vect.length];
			
			for (int i = 0; i < msg.details_vect.length; i++) {
				for (int j = 0; i < details_vect[i].ysbs.length; j++) details_vect[i].yhbh[j] = msg.details_vect[i].yhbh[j];
				for (int j = 0; i < details_vect[i].ysbs.length; j++) details_vect[i].ysbs[j] = msg.details_vect[i].ysbs[j];
				
				details_vect[i].dfny	= msg.details_vect[i].dfny;
				details_vect[i].yjje	= msg.details_vect[i].yjje;
				details_vect[i].dfje	= msg.details_vect[i].dfje;
				details_vect[i].wyjje	= msg.details_vect[i].wyjje;
				details_vect[i].sctw 	= msg.details_vect[i].sctw;
				details_vect[i].bctw 	= msg.details_vect[i].bctw;
				
				for (int j = 0; i < details_vect[i].yszt.length; j++) details_vect[i].yszt[j] = msg.details_vect[i].yszt[j];
				for (int j = 0; i < details_vect[i].xzsx.length; j++) details_vect[i].xzsx[j] = msg.details_vect[i].xzsx[j];
				for (int j = 0; i < details_vect[i].reserve.length; j++) details_vect[i].reserve[j] = msg.details_vect[i].reserve[j];
			}
		}
		
		public YFFMISMSG_DY_QUERYPOWER_RESULT_GS clone() {
			YFFMISMSG_DY_QUERYPOWER_RESULT_GS ret_msg = new YFFMISMSG_DY_QUERYPOWER_RESULT_GS();
			ret_msg.clone(this);
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, retcode);
			ret_len += ComntStream.writeStream(byte_vect, retmsg, 0, retmsg.length);
			ret_len += ComntStream.writeStream(byte_vect, jezbs);
			ret_len += ComntStream.writeStream(byte_vect, dzpc, 0, dzpc.length);
			ret_len += ComntStream.writeStream(byte_vect, hsdw, 0, hsdw.length);
			ret_len += ComntStream.writeStream(byte_vect, yhbh, 0, yhbh.length);
			ret_len += ComntStream.writeStream(byte_vect, yhmc, 0, yhmc.length);
			ret_len += ComntStream.writeStream(byte_vect, yddz, 0, yddz.length);
			
			ret_len += ComntStream.writeStream(byte_vect, hjys);
			ret_len += ComntStream.writeStream(byte_vect, hjje);
			ret_len += ComntStream.writeStream(byte_vect, reserve, 0, reserve.length);
			ret_len += ComntStream.writeStream(byte_vect, (int)details_vect.length);
			
			for(int i = 0; i< details_vect.length ; i++){
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].yhbh, 0, details_vect[i].yhbh.length);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].ysbs, 0, details_vect[i].ysbs.length);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].dfny);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].yjje);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].dfje);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].wyjje);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].sctw);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].bctw);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].yszt, 0, details_vect[i].yszt.length);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].xzsx, 0, details_vect[i].xzsx.length);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].reserve, 0, details_vect[i].reserve.length);
			}			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len 	= 0;
			int vect_size	= 0;
			
			retcode = ComntStream.readStream(byte_vect, offset + ret_len, retcode); 
			ret_len += ComntStream.getDataSize(retcode);
			
			ComntStream.readStream(byte_vect, offset + ret_len, retmsg, 0, retmsg.length);
			ret_len += ComntStream.getDataSize(retmsg[0]) * retmsg.length;
			
			jezbs = ComntStream.readStream(byte_vect, offset + ret_len, jezbs); 
			ret_len += ComntStream.getDataSize(jezbs);
			
			ComntStream.readStream(byte_vect, offset + ret_len, dzpc, 0, dzpc.length);
			ret_len += ComntStream.getDataSize(dzpc[0]) * dzpc.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, hsdw, 0, hsdw.length);
			ret_len += ComntStream.getDataSize(hsdw[0]) * hsdw.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, yhbh, 0, yhbh.length);
			ret_len += ComntStream.getDataSize(yhbh[0]) * yhbh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, yhmc, 0, yhmc.length);
			ret_len += ComntStream.getDataSize(yhmc[0]) * yhmc.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, yddz, 0, yddz.length);
			ret_len += ComntStream.getDataSize(yddz[0]) * yddz.length;
			
			hjys = ComntStream.readStream(byte_vect, offset + ret_len, hjys); 
			ret_len += ComntStream.getDataSize(hjys);

			hjje = ComntStream.readStream(byte_vect, offset + ret_len, hjje);
			ret_len += ComntStream.getDataSize(hjje);
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve, 0, reserve.length);
			ret_len += ComntStream.getDataSize(reserve[0]) * reserve.length;
		
			vect_size = ComntStream.readStream(byte_vect, offset + ret_len, vect_size); 
			ret_len += ComntStream.getDataSize(vect_size);
			
			details_vect = new QUERYPOWER_RESULT_DETAIL[vect_size];
			
			for(int i = 0; i < details_vect.length ; i++){
				
				details_vect[i] = new QUERYPOWER_RESULT_DETAIL();
				
				ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].yhbh, 0, details_vect[i].yhbh.length);
				ret_len += ComntStream.getDataSize(details_vect[i].yhbh[0]) * details_vect[i].yhbh.length;
				
				ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].ysbs, 0, details_vect[i].ysbs.length);
				ret_len += ComntStream.getDataSize(details_vect[i].ysbs[0]) * details_vect[i].ysbs.length;
				
				details_vect[i].dfny = ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].dfny); 
				ret_len += ComntStream.getDataSize(details_vect[i].dfny);
				
				//details_vect[i].dfny = ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].dfny); 
				//ret_len += ComntStream.getDataSize(details_vect[i].dfny);
				
				details_vect[i].yjje = ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].yjje); 
				ret_len += ComntStream.getDataSize(details_vect[i].yjje);
				
				details_vect[i].dfje = ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].dfje); 
				ret_len += ComntStream.getDataSize(details_vect[i].dfje);
				
				details_vect[i].wyjje = ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].wyjje); 
				ret_len += ComntStream.getDataSize(details_vect[i].wyjje);
				
				details_vect[i].sctw = ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].sctw); 
				ret_len += ComntStream.getDataSize(details_vect[i].sctw);
				
				details_vect[i].bctw = ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].bctw); 
				ret_len += ComntStream.getDataSize(details_vect[i].bctw);
				
				ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].yszt, 0, details_vect[i].yszt.length);
				ret_len += ComntStream.getDataSize(details_vect[i].yszt[0]) * details_vect[i].yszt.length;
				
				ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].xzsx, 0, details_vect[i].xzsx.length);
				ret_len += ComntStream.getDataSize(details_vect[i].xzsx[0]) * details_vect[i].xzsx.length;
				
				ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].reserve, 0, details_vect[i].reserve.length);
				ret_len += ComntStream.getDataSize(details_vect[i].reserve[0]) * details_vect[i].reserve.length;
				
			}
			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("retcode"	, 	String.valueOf(retcode));
			j_obj.put("retmsg"	, 	ComntFunc.byte2String(retmsg));
			j_obj.put("jezbs"		, 	String.valueOf(jezbs));
			j_obj.put("dzpc"		, 	ComntFunc.byte2String(dzpc));
			j_obj.put("hsdw"		, 	ComntFunc.byte2String(hsdw));
			
			j_obj.put("yhbh"		, 	ComntFunc.byte2String(yhbh));
			j_obj.put("yhmc"		, 	ComntFunc.byte2String(yhmc));
			j_obj.put("yddz"		, 	ComntFunc.byte2String(yddz));
			
			j_obj.put("hjys"		, 	String.valueOf(hjys));
			j_obj.put("hjje"		, 	String.valueOf(hjje));
			j_obj.put("reserve"	, 	ComntFunc.byte2String(reserve));
			
			
			JSONObject[] rows = new JSONObject[details_vect.length];
			for(int i = 0; i < details_vect.length; i++){
				JSONObject detail = new JSONObject();
			
				detail.put("yhbh", 	ComntFunc.byte2String(details_vect[i].yhbh));
				detail.put("ysbs", 	ComntFunc.byte2String(details_vect[i].ysbs));
				detail.put("dfny", 	String.valueOf(details_vect[i].dfny));
				detail.put("yjje", 	String.valueOf(details_vect[i].yjje));
				detail.put("dfje", 	String.valueOf(details_vect[i].dfje));
				detail.put("wyjje",	String.valueOf(details_vect[i].wyjje));
				detail.put("sctw", 	String.valueOf(details_vect[i].sctw));
				detail.put("bctw", 	String.valueOf(details_vect[i].bctw));
				detail.put("yszt", 	ComntFunc.byte2String(details_vect[i].yszt));
				detail.put("xzsx", 	ComntFunc.byte2String(details_vect[i].xzsx));
				detail.put("reserve", ComntFunc.byte2String(details_vect[i].reserve));	 
				rows[i] = detail;
			}
			j_obj.put("detail", rows);
			return j_obj.toString();
		}
	}
		
	//低压缴费记录上传
	public static class YFFMISMSG_DY_PAY_GS implements ComntMsg.IMSG_READWRITESTREAM {
		
		public static class POWERPAY_DETAIL{
			public byte[]	yhbh	= new byte[ComntMsg.MSG_STR_LEN_20];				//用户编号
			public byte[]	ysbs	= new byte[ComntMsg.MSG_STR_LEN_20];				//应收标识

			public double	jfje;														//缴费金额
			public double	dfje;														//电费金额
			public double	wyjje;														//违约金金额
			
			public double	sctw;														//上次调尾
			public double	bctw;														//本次调尾
			
			public void clone(POWERPAY_DETAIL detail) {
				ComntFunc.arrayCopy(yhbh, detail.yhbh);
				ComntFunc.arrayCopy(ysbs, detail.ysbs);
				
				jfje  = detail.jfje;
				dfje  = detail.dfje;
				wyjje = detail.wyjje;
				
				sctw  = detail.sctw;
				bctw  = detail.bctw;
			}
		}
		
		public int			rtu_id		= 0;										//终端编号
		public short		sub_id		= 0;										//总加组编号/测量点编号

		public int			date			= 0;										//缴费日期
		public int			time			= 0;										//缴费时间
		public byte			updateflag	= 0;										//更新标志

		public byte[]		dsdwbh 	=	new byte[ComntMsg.MSG_STR_LEN_20];			//代收单位编号
		public byte[]		czybh 	= 	new byte[ComntMsg.MSG_STR_LEN_20];			//操作员编号
		public byte[]		jylsh 	= 	new byte[ComntMsg.MSG_STR_LEN_40];			//交易流水号
		public byte[] 		dzpc 		= 	new byte[ComntMsg.MSG_STR_LEN_20];			//对账批次

		public byte[]		yhbh 		= 	new byte[ComntMsg.MSG_STR_LEN_20];			//用户编号
		byte				jylb		=	0;											//交易类别
		public byte[]		jffs 		= 	new byte[ComntMsg.MSG_STR_LEN_16];			//缴费方式
		public byte[]		jsfs 		= 	new byte[ComntMsg.MSG_STR_LEN_16];			//结算方式

		public double		jfje		= 	0;											//缴费金额
		public double		sjqfje 	= 	0;												//缴欠费金额
		public double		sjysje 	= 	0;												//缴预收金额

		public int			yhzwrq 	= 	0;											//银行账务日期
		public byte			dyfp 		= 	0;											//是否打印发票
		public int			jfbs 		= 	0;											//缴费笔数
		public byte[]		reserve 	= 	new byte[ComntMsg.MSG_STR_LEN_80];			//*预留字段
		public byte[]		remark 	= 	new byte[ComntMsg.MSG_STR_LEN_120];			//备注

		public byte[]		ccbh 		=	new byte[ComntMsg.MSG_STR_LEN_40];			//出厂编号
		public byte[]		reserve1 	= 	new byte[ComntMsg.MSG_STR_LEN_64];			//备用1
		public byte[]		reserve2 	= 	new byte[ComntMsg.MSG_STR_LEN_64];			//备用2
		
		public POWERPAY_DETAIL[] details_vect = null;									//欠费信息
		
		public void clean() {
			rtu_id		= 0;
			sub_id		= 0;
			date			= 0;
			time			= 0;
			updateflag	= 0;
			for (int i = 0; i < dsdwbh.length; i++) 		dsdwbh[i] = 0;
			for (int i = 0; i < czybh.length; i++) 		czybh[i] 	= 0;
			for (int i = 0; i < jylsh.length; i++) 		jylsh[i] 	= 0;
			
			
			for (int i = 0; i < dzpc.length; i++) 		dzpc[i] = 0;
			for (int i = 0; i < yhbh.length; i++) 		yhbh[i] = 0;
			
			jylb		= 0;
			
			for (int i = 0; i < jffs.length; i++) 		jffs[i] = 0;
			for (int i = 0; i < jsfs.length; i++) 		jsfs[i] = 0;
			
			jfje		= 	0;
			sjqfje 	= 	0;
			sjysje 	= 	0;                
			yhzwrq 	= 	0;
			dyfp 		= 	0;
			jfbs 		= 	0;
			
			for (int i = 0; i < reserve.length; i++) 		reserve[i] 	= 0;
			for (int i = 0; i < remark.length; i++) 		remark[i] 	= 0;
			for (int i = 0; i < ccbh.length; i++) 		ccbh[i] 		= 0;
			for (int i = 0; i < reserve1.length; i++) 	reserve1[i] 	= 0;
			for (int i = 0; i < reserve2.length; i++) 	reserve2[i] 	= 0;
			
			for (int i = 0; i < details_vect.length; i++) {
				for (int j = 0; i < details_vect[i].yhbh.length; j++) details_vect[i].yhbh[j] = 0;
				for (int j = 0; i < details_vect[i].ysbs.length; j++) details_vect[i].ysbs[j] = 0;
				details_vect[i].jfje = 0;								
				details_vect[i].dfje = 0;								
				details_vect[i].wyjje = 0;								
				details_vect[i].sctw = 0;
				details_vect[i].bctw = 0;
			}
		}
		
		public void clone(YFFMISMSG_DY_PAY_GS msg) {
			rtu_id		= msg.rtu_id;
			sub_id		= msg.sub_id;
			date			= msg.date;
			time			= msg.time;
			updateflag	= msg.updateflag;
			
			for (int i = 0; i < dsdwbh.length; i++) 		dsdwbh[i] = msg.dsdwbh[i];
			for (int i = 0; i < czybh.length; i++) 		czybh[i] 	= msg.czybh[i];
			for (int i = 0; i < jylsh.length; i++) 		jylsh[i] 	= msg.jylsh[i];
			for (int i = 0; i < dzpc.length; i++) 		dzpc[i] 	= msg.dzpc[i];
			for (int i = 0; i < yhbh.length; i++) 		yhbh[i] 	= msg.yhbh[i];
			jylb	   = msg.jylb;									
			
			for (int i = 0; i < jffs.length; i++) 		jffs[i] = msg.jffs[i];
			for (int i = 0; i < jsfs.length; i++) 		jsfs[i] = msg.jsfs[i];
			
			jfje		= 	msg.jfje;
			sjqfje 	= 	msg.sjqfje;
			sjysje 	= 	msg.sjysje;                
			yhzwrq 	= 	msg.yhzwrq;
			dyfp 		= 	msg.dyfp;
			jfbs 		= 	msg.jfbs;
		
			for (int i = 0; i < reserve.length; i++) 		reserve[i]  = msg.reserve[i];
			for (int i = 0; i < remark.length; i++) 		remark[i]   = msg.remark[i];
			for (int i = 0; i < ccbh.length; i++) 		ccbh[i] 	  = msg.ccbh[i];
			for (int i = 0; i < reserve1.length; i++) 	reserve1[i] = msg.reserve1[i];
			for (int i = 0; i < reserve2.length; i++) 	reserve2[i] = msg.reserve2[i];
			
			//需要初始化数组长度
			details_vect = new POWERPAY_DETAIL[msg.details_vect.length];
			for (int i = 0; i < details_vect.length; i++) {
				for (int j = 0; i < details_vect[i].yhbh.length; j++) details_vect[i].yhbh[j] = msg.details_vect[i].yhbh[j];
				for (int j = 0; i < details_vect[i].ysbs.length; j++) details_vect[i].ysbs[j] = msg.details_vect[i].ysbs[j];
				details_vect[i].jfje  = msg.details_vect[i].jfje ;
				details_vect[i].dfje  = msg.details_vect[i].dfje ;
				details_vect[i].wyjje = msg.details_vect[i].wyjje;
				details_vect[i].sctw  = msg.details_vect[i].sctw ;								
				details_vect[i].bctw  = msg.details_vect[i].bctw ;
			}   
		}
		
		public YFFMISMSG_DY_PAY_GS clone() {
			YFFMISMSG_DY_PAY_GS ret_msg = new YFFMISMSG_DY_PAY_GS();
			ret_msg.clone(this);
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, rtu_id);
			ret_len += ComntStream.writeStream(byte_vect, sub_id);
			ret_len += ComntStream.writeStream(byte_vect, date);
			ret_len += ComntStream.writeStream(byte_vect, time);
			ret_len += ComntStream.writeStream(byte_vect, updateflag);
			
			ret_len += ComntStream.writeStream(byte_vect, dsdwbh, 0, dsdwbh.length);
			ret_len += ComntStream.writeStream(byte_vect, czybh , 0, czybh.length);
			ret_len += ComntStream.writeStream(byte_vect, jylsh , 0, jylsh.length);
			ret_len += ComntStream.writeStream(byte_vect, dzpc  , 0, dzpc .length);
			ret_len += ComntStream.writeStream(byte_vect, yhbh  , 0, yhbh .length);
			ret_len += ComntStream.writeStream(byte_vect, jylb);
			ret_len += ComntStream.writeStream(byte_vect, jffs,  0, jffs.length);
			ret_len += ComntStream.writeStream(byte_vect, jsfs, 0, jsfs.length);
			
			ret_len += ComntStream.writeStream(byte_vect, jfje	);
			ret_len += ComntStream.writeStream(byte_vect, sjqfje  );
			ret_len += ComntStream.writeStream(byte_vect, sjysje  );
			ret_len += ComntStream.writeStream(byte_vect, yhzwrq  );
			ret_len += ComntStream.writeStream(byte_vect, dyfp 	);
			ret_len += ComntStream.writeStream(byte_vect, jfbs 	);
			
			ret_len += ComntStream.writeStream(byte_vect, reserve,  0, reserve.length);
			ret_len += ComntStream.writeStream(byte_vect, remark,   0, remark.length);
			ret_len += ComntStream.writeStream(byte_vect, ccbh,     0, ccbh.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve1, 0, reserve1 .length);
			ret_len += ComntStream.writeStream(byte_vect, reserve2, 0, reserve2 .length);
			
			ret_len += ComntStream.writeStream(byte_vect, (int)details_vect.length);
			
			for(int i = 0; i< details_vect.length ; i++){
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].yhbh, 0, details_vect[i].yhbh.length);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].ysbs, 0, details_vect[i].ysbs.length);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].jfje );
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].dfje );
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].wyjje);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].sctw );
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].bctw );
			}                                                                  				
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len 	= 0;
			int vect_size	= 0;

			rtu_id = ComntStream.readStream(byte_vect, offset + ret_len, rtu_id); 
			ret_len += ComntStream.getDataSize(rtu_id);

			sub_id = ComntStream.readStream(byte_vect, offset + ret_len, sub_id); 
			ret_len += ComntStream.getDataSize(sub_id);
			
			date = ComntStream.readStream(byte_vect, offset + ret_len, date); 
			ret_len += ComntStream.getDataSize(date);

			time = ComntStream.readStream(byte_vect, offset + ret_len, time); 
			ret_len += ComntStream.getDataSize(time);
			
			updateflag = ComntStream.readStream(byte_vect, offset + ret_len, updateflag); 
			ret_len += ComntStream.getDataSize(updateflag);

			ComntStream.readStream(byte_vect, offset + ret_len, dsdwbh, 0, dsdwbh.length);
			ret_len += ComntStream.getDataSize(dsdwbh[0]) * dsdwbh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, czybh, 0, czybh.length);
			ret_len += ComntStream.getDataSize(czybh[0]) * czybh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, jylsh, 0, jylsh.length);
			ret_len += ComntStream.getDataSize(jylsh[0]) * jylsh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, dzpc, 0, dzpc.length);
			ret_len += ComntStream.getDataSize(dzpc[0]) * dzpc.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, yhbh, 0, yhbh.length);
			ret_len += ComntStream.getDataSize(yhbh[0]) * yhbh.length;
			
			jylb = ComntStream.readStream(byte_vect, offset + ret_len, jylb); 
			ret_len += ComntStream.getDataSize(jylb);
			
			ComntStream.readStream(byte_vect, offset + ret_len, jffs, 0, jffs.length);
			ret_len += ComntStream.getDataSize(jffs[0]) * jffs.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, jsfs, 0, jsfs.length);
			ret_len += ComntStream.getDataSize(jsfs[0]) * jsfs.length;
			
			jfje = ComntStream.readStream(byte_vect, offset + ret_len, jfje); 
			ret_len += ComntStream.getDataSize(jfje);
			
			sjqfje = ComntStream.readStream(byte_vect, offset + ret_len, sjqfje); 
			ret_len += ComntStream.getDataSize(sjqfje);
			
			sjysje = ComntStream.readStream(byte_vect, offset + ret_len, sjysje); 
			ret_len += ComntStream.getDataSize(sjysje);
			
			yhzwrq = ComntStream.readStream(byte_vect, offset + ret_len, yhzwrq); 
			ret_len += ComntStream.getDataSize(yhzwrq);
			
			dyfp = ComntStream.readStream(byte_vect, offset + ret_len, dyfp); 
			ret_len += ComntStream.getDataSize(dyfp);
			
			jfbs = ComntStream.readStream(byte_vect, offset + ret_len, jfbs); 
			ret_len += ComntStream.getDataSize(jfbs);
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve, 0, reserve.length);
			ret_len += ComntStream.getDataSize(reserve[0]) * reserve.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, remark, 0, remark.length);
			ret_len += ComntStream.getDataSize(remark[0]) * remark.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, ccbh, 0, ccbh.length);
			ret_len += ComntStream.getDataSize(ccbh[0]) * ccbh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve1, 0, reserve1.length);
			ret_len += ComntStream.getDataSize(reserve1[0]) * reserve1.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve2, 0, reserve2.length);
			ret_len += ComntStream.getDataSize(reserve2[0]) * reserve2.length;
			
			
			vect_size = ComntStream.readStream(byte_vect, offset + ret_len, vect_size); 
			ret_len += ComntStream.getDataSize(vect_size);
			
			details_vect = new POWERPAY_DETAIL[vect_size];
			
			for(int i = 0; i< details_vect.length ; i++){
				details_vect[i] = new POWERPAY_DETAIL();
				
				ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].yhbh, 0, details_vect[i].yhbh.length);
				ret_len += ComntStream.getDataSize(details_vect[i].yhbh[0]) * details_vect[i].yhbh.length;
				
				ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].ysbs, 0, details_vect[i].ysbs.length);
				ret_len += ComntStream.getDataSize(details_vect[i].ysbs[0]) * details_vect[i].ysbs.length;
				
				details_vect[i].jfje = ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].jfje); 
				ret_len += ComntStream.getDataSize(details_vect[i].jfje);
				
				details_vect[i].dfje = ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].dfje); 
				ret_len += ComntStream.getDataSize(details_vect[i].dfje);
				
				details_vect[i].wyjje = ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].wyjje); 
				ret_len += ComntStream.getDataSize(details_vect[i].wyjje);
				
				details_vect[i].sctw = ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].sctw); 
				ret_len += ComntStream.getDataSize(details_vect[i].sctw);
				
				details_vect[i].bctw = ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].bctw); 
				ret_len += ComntStream.getDataSize(details_vect[i].bctw);
				
			}
			return ret_len;
		}

	}

	//低压缴费返回结果
	public static class YFFMISMSG_DY_PAY_RESULT_GS implements ComntMsg.IMSG_READWRITESTREAM {
//		public static class PAYRESULTDETAIL_BASE{
//			public byte[]	ysbs 	= 	new byte[ComntMsg.MSG_STR_LEN_20];			//*应收标识
//			public byte[]	xmdm 	= 	new byte[ComntMsg.MSG_STR_LEN_8];			//*项目代码
//			public byte[]	xmval 	= 	new byte[ComntMsg.MSG_STR_LEN_60];		//*项目值
//		}
		
		public int  		retcode	= 0;											//返回代码
		public byte[] 		retmsg    = new byte[ComntMsg.MSG_STR_LEN_60];			//返回消息
		public int  		fp_num	= 0;											//发票数
		public byte[] 		hsdw		= new byte[ComntMsg.MSG_STR_LEN_20];			//核算单位
		public byte[] 		reserve	= new byte[ComntMsg.MSG_STR_LEN_80];			//预留字段
		
		public void clean() {
			retcode  = 0;
			for (int i = 0; i < retmsg.length; i++) retmsg[i] = 0;
			fp_num   = 0;
			for (int i = 0; i < hsdw.length; i++) hsdw[i] = 0;
			for (int i = 0; i < reserve.length; i++) reserve[i] = 0;
		}
		
		public void clone(YFFMISMSG_DY_PAY_RESULT_GS msg) {
			retcode  = msg.retcode;
			for (int i = 0; i < retmsg.length; i++) 	retmsg[i]  = msg.retmsg[i];
			fp_num   = msg.fp_num;
			for (int i = 0; i < hsdw.length; i++) 	hsdw[i] 	 = msg.hsdw[i];
			for (int i = 0; i < reserve.length; i++) 	reserve[i] = msg.reserve[i];
		}
		
		public YFFMISMSG_DY_PAY_RESULT_GS clone() {
			YFFMISMSG_DY_PAY_RESULT_GS ret_msg = new YFFMISMSG_DY_PAY_RESULT_GS();
			ret_msg.clone(this);
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, retcode);
			ret_len += ComntStream.writeStream(byte_vect, retmsg, 0, retmsg.length);
			ret_len += ComntStream.writeStream(byte_vect, fp_num);
			ret_len += ComntStream.writeStream(byte_vect, hsdw, 0, hsdw.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve, 0, reserve.length);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len 	= 0;

			retcode = ComntStream.readStream(byte_vect, offset + ret_len, retcode); 
			ret_len += ComntStream.getDataSize(retcode);
			
			ComntStream.readStream(byte_vect, offset + ret_len, retmsg, 0, retmsg.length);
			ret_len += ComntStream.getDataSize(retmsg[0]) * retmsg.length;
			
			fp_num = ComntStream.readStream(byte_vect, offset + ret_len, fp_num); 
			ret_len += ComntStream.getDataSize(fp_num);
			
			ComntStream.readStream(byte_vect, offset + ret_len, hsdw, 0, hsdw.length);
			ret_len += ComntStream.getDataSize(hsdw[0]) * hsdw.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve, 0, reserve.length);
			ret_len += ComntStream.getDataSize(reserve[0]) * reserve.length;

			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("retcode", 	String.valueOf(retcode));
			j_obj.put("retmsg" , 	ComntFunc.byte2String(retmsg));
			j_obj.put("fp_num" , 	String.valueOf(fp_num));
			j_obj.put("hsdw"   , 	ComntFunc.byte2String(hsdw));
			j_obj.put("reserve", 	ComntFunc.byte2String(reserve));
			
			return j_obj.toString();
		}
		
	}

	//低压冲正操作
	public static class YFFMISMSG_DY_REVER_GS implements ComntMsg.IMSG_READWRITESTREAM {
		public int		rtu_id		= 0;										//终端编号
		public short	sub_id		= 0;										//总加组编号/测量点编号
		public int		date			= 0;
		public int		time			= 0;
		public int		last_date 	= 0;										//被冲正记录日期
		public int		last_time 	= 0;										//被冲正记录时间
		public byte		updateflag 	= 0;										//更新标志

		public byte[]	dsdwbh   		= new byte[ComntMsg.MSG_STR_LEN_20];		//代收单位编号
		public byte[]	czybh  	 	= new byte[ComntMsg.MSG_STR_LEN_20];		//操作员编号
		public byte[]	czjylsh  		= new byte[ComntMsg.MSG_STR_LEN_40];		//冲正交易流水号
		public byte[] 	dzpc  	 	= new byte[ComntMsg.MSG_STR_LEN_20];		//对账批次
                                   
		public byte[]	yjylsh   		= new byte[ComntMsg.MSG_STR_LEN_40];		//原交易流水号
		public int		yhzwrq 	 	= 0;										//银行账务日期
                                   
		public byte[]	ccbh 		 	= new byte[ComntMsg.MSG_STR_LEN_40];		//出厂编号
		public byte[]	reserve1 		= new byte[ComntMsg.MSG_STR_LEN_64];		//备用1
		public byte[]	reserve2 		= new byte[ComntMsg.MSG_STR_LEN_64];		//备用2
	
		public void clean() {
			rtu_id	  =  0;
			sub_id	  =  0;
			date		  =  0;
			time		  =  0;
			last_date   =  0;
			last_time   =  0;
			updateflag  =  0;
			for (int i = 0; i < dsdwbh.length; i++) 		dsdwbh[i] = 0;								
			for (int i = 0; i < czybh.length; i++) 		czybh[i] = 0;
			for (int i = 0; i < czjylsh.length; i++) 		czjylsh[i] = 0;
			for (int i = 0; i < dzpc.length; i++) 		dzpc[i] = 0;
			for (int i = 0; i < yjylsh.length; i++) 		yjylsh[i] = 0;
			yhzwrq		= 0;
			for (int i = 0; i < ccbh.length; i++) 		ccbh[i] = 0;
			for (int i = 0; i < reserve1.length; i++) 	reserve1[i] = 0;
			for (int i = 0; i < reserve2.length; i++) 	reserve2[i] = 0;
		}
		
		public void clone(YFFMISMSG_DY_REVER_GS msg) {
			rtu_id	   =   msg.rtu_id	;
			sub_id	   =   msg.sub_id	;
			date		   =   msg.date	;
			time		   =   msg.time	;
			last_date    =   msg.last_date; 
			last_time    =   msg.last_time; 
			updateflag   =   msg.updateflag;
			
			for (int i = 0; i < dsdwbh.length; i++) dsdwbh[i]		= msg.dsdwbh[i];
			for (int i = 0; i < czybh.length; i++) czybh[i] 		= msg.czybh[i];
			for (int i = 0; i < czjylsh.length; i++) czjylsh[i] 	= msg.czjylsh[i];
			for (int i = 0; i < dzpc.length; i++) dzpc[i] 			= msg.dzpc[i];
			for (int i = 0; i < yjylsh.length; i++) yjylsh[i] 		= msg.yjylsh[i];
			yhzwrq		= msg.yhzwrq;
			for (int i = 0; i < ccbh.length; i++) ccbh[i] 			= msg.ccbh[i];
			for (int i = 0; i < reserve1.length; i++) reserve1[i]	= msg.reserve1[i];
			for (int i = 0; i < reserve2.length; i++) reserve2[i]	= msg.reserve2[i];
		}
		
		public YFFMISMSG_DY_REVER_GS clone() {
			YFFMISMSG_DY_REVER_GS ret_msg = new YFFMISMSG_DY_REVER_GS();
			ret_msg.clone(this);
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, rtu_id);
			ret_len += ComntStream.writeStream(byte_vect, sub_id);
			ret_len += ComntStream.writeStream(byte_vect, date);
			ret_len += ComntStream.writeStream(byte_vect, time);
			ret_len += ComntStream.writeStream(byte_vect, last_date);
			ret_len += ComntStream.writeStream(byte_vect, last_time);
			ret_len += ComntStream.writeStream(byte_vect, updateflag);
			
			ret_len += ComntStream.writeStream(byte_vect, dsdwbh, 	0, dsdwbh.length);
			ret_len += ComntStream.writeStream(byte_vect, czybh, 		0, czybh.length);
			ret_len += ComntStream.writeStream(byte_vect, czjylsh, 	0, czjylsh.length);
			ret_len += ComntStream.writeStream(byte_vect, dzpc, 		0, dzpc.length);
			ret_len += ComntStream.writeStream(byte_vect, yjylsh, 	0, yjylsh.length);
			ret_len += ComntStream.writeStream(byte_vect, yhzwrq);
			ret_len += ComntStream.writeStream(byte_vect, ccbh, 		0, ccbh.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve1, 	0, reserve1.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve2, 	0, reserve2.length);
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len 	= 0;

			rtu_id = ComntStream.readStream(byte_vect, offset + ret_len, rtu_id); 
			ret_len += ComntStream.getDataSize(rtu_id);

			sub_id = ComntStream.readStream(byte_vect, offset + ret_len, sub_id); 
			ret_len += ComntStream.getDataSize(sub_id);
			
			date = ComntStream.readStream(byte_vect, offset + ret_len, date); 
			ret_len += ComntStream.getDataSize(date);

			time = ComntStream.readStream(byte_vect, offset + ret_len, time); 
			ret_len += ComntStream.getDataSize(time);
			
			last_date = ComntStream.readStream(byte_vect, offset + ret_len, last_date); 
			ret_len += ComntStream.getDataSize(last_date);

			last_time = ComntStream.readStream(byte_vect, offset + ret_len, last_time); 
			ret_len += ComntStream.getDataSize(last_time);
			
			updateflag = ComntStream.readStream(byte_vect, offset + ret_len, updateflag); 
			ret_len += ComntStream.getDataSize(updateflag);
			
			ComntStream.readStream(byte_vect, offset + ret_len, dsdwbh, 0, dsdwbh.length);
			ret_len += ComntStream.getDataSize(dsdwbh[0]) * dsdwbh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, czybh, 0, czybh.length);
			ret_len += ComntStream.getDataSize(czybh[0]) * czybh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, czjylsh, 0, czjylsh.length);
			ret_len += ComntStream.getDataSize(czjylsh[0]) * czjylsh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, dzpc, 0, dzpc.length);
			ret_len += ComntStream.getDataSize(dzpc[0]) * dzpc.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, yjylsh, 0, yjylsh.length);
			ret_len += ComntStream.getDataSize(yjylsh[0]) * yjylsh.length;
			
			yhzwrq = ComntStream.readStream(byte_vect, offset + ret_len, yhzwrq); 
			ret_len += ComntStream.getDataSize(yhzwrq);

			ComntStream.readStream(byte_vect, offset + ret_len, ccbh, 0, ccbh.length);
			ret_len += ComntStream.getDataSize(ccbh[0]) * ccbh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve1, 0, reserve1.length);
			ret_len += ComntStream.getDataSize(reserve1[0]) * reserve1.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve2, 0, reserve2.length);
			ret_len += ComntStream.getDataSize(reserve2[0]) * reserve2.length;

			return ret_len;
		}
	}
	
	//低压冲正返回结果
	public static class YFFMISMSG_DY_REVER_RESULT_GS implements ComntMsg.IMSG_READWRITESTREAM {
		public int		retcode;												//返回代码
		public byte[] 	retmsg  = new byte[ComntMsg.MSG_STR_LEN_60];			//返回消息
		
		public void clean() {
			retcode  = 0;
			for (int i = 0; i < retmsg.length; i++) retmsg[i] = 0;
		}
		
		public void clone(YFFMISMSG_DY_REVER_RESULT_GS msg) {
			retcode  = msg.retcode;
			for (int i = 0; i < retmsg.length; i++) retmsg[i] = msg.retmsg[i];
		}
		
		public YFFMISMSG_DY_REVER_RESULT_GS clone() {
			YFFMISMSG_DY_REVER_RESULT_GS ret_msg = new YFFMISMSG_DY_REVER_RESULT_GS();
			ret_msg.clone(this);
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			ret_len += ComntStream.writeStream(byte_vect, retcode);
			ret_len += ComntStream.writeStream(byte_vect, retmsg, 0, retmsg.length);
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len 	= 0;

			retcode = ComntStream.readStream(byte_vect, offset + ret_len, retcode); 
			ret_len += ComntStream.getDataSize(retcode);
			
			ComntStream.readStream(byte_vect, offset + ret_len, retmsg, 0, retmsg.length);
			ret_len += ComntStream.getDataSize(retmsg[0]) * retmsg.length;

			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			j_obj.put("retcode", 	String.valueOf(retcode));
			j_obj.put("retmsg", 	ComntFunc.byte2String(retmsg));
			return j_obj.toString();
		}
	}
	
	//高压查询缴费
	public static class YFFMISMSG_GY_QUERYPOWER_GS extends YFFMISMSG_DY_QUERYPOWER_GS{}
	//高压查询缴费结果
	public static class YFFMISMSG_GY_QUERYPOWER_RESULT_GS extends YFFMISMSG_DY_QUERYPOWER_RESULT_GS{}

	//高压缴费记录上传
	public static class YFFMISMSG_GY_PAY_GS extends YFFMISMSG_DY_PAY_GS{}
	//高压缴费返回结果
	public static class YFFMISMSG_GY_PAY_RESULT_GS extends YFFMISMSG_DY_PAY_RESULT_GS{}
	//高压冲正操作
	public static class YFFMISMSG_GY_REVER_GS extends YFFMISMSG_DY_REVER_GS{}					
	//高压冲正返回结果
	public static class YFFMISMSG_GY_REVER_RESULT_GS extends YFFMISMSG_DY_REVER_RESULT_GS{}
	
	//对账操作
	public static class YFFMISMSG_CHECK_GS implements ComntMsg.IMSG_READWRITESTREAM {
		public byte[] 	dsdwbh = new byte[ComntMsg.MSG_STR_LEN_20];			//代收单位编号
		public byte[]	hsdw = new byte[ComntMsg.MSG_STR_LEN_20];			//核算单位
		public byte[]	czybh = new byte[ComntMsg.MSG_STR_LEN_20];			//*操作员编号
		public byte[] 	dzpc = new byte[ComntMsg.MSG_STR_LEN_20];			//*对账批次

		public byte[]	dzwjm = new byte[ComntMsg.MSG_STR_LEN_60];			//对账文件名
		public int		jybs = 0;								//交易笔数
		public double	jyje = 0;								//交易金额
		public byte		dzlb = 0;								//对账类别

		public byte[]	reserve1 = new byte[ComntMsg.MSG_STR_LEN_64];		//备用1
		public byte[]	reserve2 = new byte[ComntMsg.MSG_STR_LEN_64];		//备用2
		
		public void clean() {
			for (int i = 0; i < dsdwbh.length; i++) 	dsdwbh[i] = 0;
			for (int i = 0; i < hsdw.length; i++) 	hsdw[i] = 0;
			for (int i = 0; i < czybh.length; i++) 	czybh[i] = 0;
			for (int i = 0; i < dzpc.length; i++) 	dzpc[i] = 0;
			for (int i = 0; i < dzwjm.length; i++) 	dzwjm[i] = 0;
			
			jybs	= 0;
			jyje	= 0;
			dzlb	= 0;
			
			for (int i = 0; i < reserve1.length; i++) reserve1[i] = 0;
			for (int i = 0; i < reserve2.length; i++) reserve2[i] = 0;
		}
		
		public void clone(YFFMISMSG_CHECK_GS msg) {
			for (int i = 0; i < dsdwbh.length; i++) 	dsdwbh[i]	= msg.dsdwbh[i];
			for (int i = 0; i < hsdw.length; i++) 	hsdw[i] 	= msg.hsdw[i];
			for (int i = 0; i < czybh.length; i++) 	czybh[i] 	= msg.czybh[i];
			for (int i = 0; i < dzpc.length; i++) 	dzpc[i] 	= msg.dzpc[i];
			for (int i = 0; i < dzwjm.length; i++) 	dzwjm[i] 	= msg.dzwjm[i];
			
			jybs	= msg.jybs;
			jyje	= msg.jyje;
			dzlb 	= msg.dzlb;
			
			for (int i = 0; i < reserve1.length; i++) reserve1[i]	= msg.reserve1[i];
			for (int i = 0; i < reserve2.length; i++) reserve2[i]	= msg.reserve2[i];
		}
		
		public YFFMISMSG_CHECK_GS clone() {
			YFFMISMSG_CHECK_GS ret_msg = new YFFMISMSG_CHECK_GS();
			ret_msg.clone(this);
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			ret_len += ComntStream.writeStream(byte_vect, dsdwbh, 	0, dsdwbh.length);
			ret_len += ComntStream.writeStream(byte_vect, hsdw, 		0, hsdw.length);
			ret_len += ComntStream.writeStream(byte_vect, czybh, 		0, czybh.length);
			ret_len += ComntStream.writeStream(byte_vect, dzpc, 		0, dzpc.length);
			ret_len += ComntStream.writeStream(byte_vect, dzwjm, 		0, dzwjm.length);
			ret_len += ComntStream.writeStream(byte_vect, jybs);
			ret_len += ComntStream.writeStream(byte_vect, jyje);
			ret_len += ComntStream.writeStream(byte_vect, dzlb);
			ret_len += ComntStream.writeStream(byte_vect, reserve1, 	0, reserve1.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve2, 	0, reserve2.length);
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len 	= 0;
			
			ComntStream.readStream(byte_vect, offset + ret_len, dsdwbh, 0, dsdwbh.length);
			ret_len += ComntStream.getDataSize(dsdwbh[0]) * dsdwbh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, hsdw, 0, hsdw.length);
			ret_len += ComntStream.getDataSize(hsdw[0]) * hsdw.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, czybh, 0, czybh.length);
			ret_len += ComntStream.getDataSize(czybh[0]) * czybh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, dzpc, 0, dzpc.length);
			ret_len += ComntStream.getDataSize(dzpc[0]) * dzpc.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, dzwjm, 0, dzwjm.length);
			ret_len += ComntStream.getDataSize(dzwjm[0]) * dzwjm.length;
			
			jybs = ComntStream.readStream(byte_vect, offset + ret_len, jybs); 
			ret_len += ComntStream.getDataSize(jybs);
			
			jyje = ComntStream.readStream(byte_vect, offset + ret_len, jyje); 
			ret_len += ComntStream.getDataSize(jyje);
			
			dzlb = ComntStream.readStream(byte_vect, offset + ret_len, dzlb); 
			ret_len += ComntStream.getDataSize(dzlb);
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve1, 0, reserve1.length);
			ret_len += ComntStream.getDataSize(reserve1[0]) * reserve1.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve2, 0, reserve2.length);
			ret_len += ComntStream.getDataSize(reserve2[0]) * reserve2.length;

			return ret_len;
		}
	}
	
	//对账操作结果
	public static class YFFMISMSG_CHECK_RESULT_GS implements ComntMsg.IMSG_READWRITESTREAM {
		public int		retcode = 0;												//返回代码
		public byte[] 	retmsg  = new byte[ComntMsg.MSG_STR_LEN_60];				//*返回消息
		public byte[] 	xdzpc   = new byte[ComntMsg.MSG_STR_LEN_20];				//*新对账批次
		public byte[]	dbzmc   = new byte[ComntMsg.MSG_STR_LEN_120];				//单边账文件名称
		
		public void clean() {
			retcode  = 0;
			for (int i = 0; i < retmsg.length; i++) 	retmsg[i] = 0;
			for (int i = 0; i < xdzpc.length; i++) 	xdzpc[i] 	= 0;
			for (int i = 0; i < dbzmc.length; i++) 	dbzmc[i] 	= 0;
		}
		
		public void clone(YFFMISMSG_CHECK_RESULT_GS msg) {
			retcode  = msg.retcode;
			for (int i = 0; i < retmsg.length; i++) 	retmsg[i]	= msg.retmsg[i];
			for (int i = 0; i < xdzpc.length; i++) 	xdzpc[i]	= msg.xdzpc[i];
			for (int i = 0; i < dbzmc.length; i++) 	dbzmc[i]	= msg.dbzmc[i];
		}
		
		public YFFMISMSG_CHECK_RESULT_GS clone() {
			YFFMISMSG_CHECK_RESULT_GS ret_msg = new YFFMISMSG_CHECK_RESULT_GS();
			ret_msg.clone(this);
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			ret_len += ComntStream.writeStream(byte_vect, retcode);
			ret_len += ComntStream.writeStream(byte_vect, retmsg, 0, retmsg.length);
			ret_len += ComntStream.writeStream(byte_vect, xdzpc, 	0, xdzpc.length);
			ret_len += ComntStream.writeStream(byte_vect, dbzmc, 	0, dbzmc.length);
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len 	= 0;

			retcode = ComntStream.readStream(byte_vect, offset + ret_len, retcode); 
			ret_len += ComntStream.getDataSize(retcode);
			
			ComntStream.readStream(byte_vect, offset + ret_len, retmsg, 0, retmsg.length);
			ret_len += ComntStream.getDataSize(retmsg[0]) * retmsg.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, xdzpc, 0, xdzpc.length);
			ret_len += ComntStream.getDataSize(xdzpc[0]) * xdzpc.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, dbzmc, 0, dbzmc.length);
			ret_len += ComntStream.getDataSize(dbzmc[0]) * dbzmc.length;
			
			return ret_len;
		}
	}

	//20131203  zhp
	//与MIS系统高低压对账
	public static class YFFMISMSG_GDYCHECKPAY_GS implements ComntMsg.IMSG_READWRITESTREAM {
		public byte dsdwbh[]	= new byte[ComntMsg.MSG_STR_LEN_40];		//代收单位编号   渠道商编号
		public byte hsdw[]		= new byte[ComntMsg.MSG_STR_LEN_20];		//核算单位
		public int	transymd	= 0;										//交易yyyymmdd
		public int	transhms	= 0;										//交易hhmmss
		public byte czybh[] 	= new byte[ComntMsg.MSG_STR_LEN_20];		//操作员编号
		public byte reserve1[]	= new byte[ComntMsg.MSG_STR_LEN_64];		//备用1
		public byte reserve2[]	= new byte[ComntMsg.MSG_STR_LEN_64];		//备用2
		
		public void clean(){
			for (int i = 0; i < dsdwbh.length; i++)   dsdwbh[i] = 0;
			for (int i = 0; i < hsdw.length; i++)     hsdw[i] = 0;
			transymd	= 0;										
			transhms	= 0;
			for (int i = 0; i < czybh.length; i++)    czybh[i] = 0;
			for (int i = 0; i < reserve1.length; i++) reserve1[i] = 0;
			for (int i = 0; i < reserve2.length; i++) reserve2[i] = 0;
		}
		
		public void clone(YFFMISMSG_GDYCHECKPAY_GS msg) {
			for (int i = 0; i < dsdwbh.length; i++)   dsdwbh[i] = msg.dsdwbh[i];
			for (int i = 0; i < hsdw.length; i++)     hsdw[i] = msg.hsdw[i];
			transymd	= msg.transymd;										
			transhms	= msg.transhms;
			for (int i = 0; i < czybh.length; i++)    czybh[i] = msg.czybh[i];
			for (int i = 0; i < reserve1.length; i++) reserve1[i] = msg.reserve1[i];
			for (int i = 0; i < reserve2.length; i++) reserve2[i] = msg.reserve2[i];
		}
		
		public YFFMISMSG_GDYCHECKPAY_GS clone(){
			YFFMISMSG_GDYCHECKPAY_GS ret_msg = new YFFMISMSG_GDYCHECKPAY_GS();
			ret_msg.clone(this);
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			ret_len += ComntStream.writeStream(byte_vect, dsdwbh, 0, dsdwbh.length);
			ret_len += ComntStream.writeStream(byte_vect, hsdw, 0, hsdw.length);
			ret_len += ComntStream.writeStream(byte_vect, transymd);
			ret_len += ComntStream.writeStream(byte_vect, transhms);
			ret_len += ComntStream.writeStream(byte_vect, czybh, 0, czybh.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve1, 0, reserve1.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve2, 0, reserve2.length);
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len 	= 0;

			ComntStream.readStream(byte_vect, offset + ret_len, dsdwbh, 0, dsdwbh.length);
			ret_len += ComntStream.getDataSize(dsdwbh[0]) * dsdwbh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, hsdw, 0, hsdw.length);
			ret_len += ComntStream.getDataSize(hsdw[0]) * hsdw.length;
			
			transymd = ComntStream.readStream(byte_vect, offset + ret_len, transymd); 
			ret_len += ComntStream.getDataSize(transymd);
			
			transhms = ComntStream.readStream(byte_vect, offset + ret_len, transhms); 
			ret_len += ComntStream.getDataSize(transhms);
			
			ComntStream.readStream(byte_vect, offset + ret_len, czybh, 0, czybh.length);
			ret_len += ComntStream.getDataSize(czybh[0]) * czybh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve1, 0, reserve1.length);
			ret_len += ComntStream.getDataSize(reserve1[0]) * reserve1.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve2, 0, reserve2.length);
			ret_len += ComntStream.getDataSize(reserve2[0]) * reserve2.length;

			return ret_len;
		} 
	}
	
	//与MIS系统高低压对账操作结果
	public static class YFFMISMSG_GDYCHECKPAY_RESULT_GS implements ComntMsg.IMSG_READWRITESTREAM{
		public int		retcode	= 0;									//返回代码
		public byte		dbzmc[]	= new byte[ComntMsg.MSG_STR_LEN_60];	//单边账文件名称
		
		public void clean() {
			retcode	= 0;
			for (int i = 0; i < dbzmc.length; i++) dbzmc[i] = 0;
		}
		
		public void clone(YFFMISMSG_GDYCHECKPAY_RESULT_GS msg) {
			retcode		= msg.retcode;
			for (int i = 0; i < dbzmc.length; i++) dbzmc[i]	= msg.dbzmc[i];
			
		}
		
		public YFFMISMSG_GDYCHECKPAY_RESULT_GS clone() {
			YFFMISMSG_GDYCHECKPAY_RESULT_GS ret_msg = new YFFMISMSG_GDYCHECKPAY_RESULT_GS();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			ret_len += ComntStream.writeStream(byte_vect, retcode);
			ret_len += ComntStream.writeStream(byte_vect, dbzmc, 0, dbzmc.length);
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len 	= 0;

			retcode = ComntStream.readStream(byte_vect, offset + ret_len, retcode); 
			ret_len += ComntStream.getDataSize(retcode);
			
			ComntStream.readStream(byte_vect, offset + ret_len, dbzmc, 0, dbzmc.length);
			ret_len += ComntStream.getDataSize(dbzmc[0]) * dbzmc.length;
			
			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			j_obj.put("retcode", 	String.valueOf(retcode));
			j_obj.put("dbzmc", 		ComntFunc.byte2String(dbzmc));
			
			return j_obj.toString();
		}
	}	
}


