package com.kesd.comnt;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import com.libweb.comnt.ComntFunc;
import com.libweb.comnt.ComntStream;
import com.libweb.comnt.ComntVector;

public class ComntMsgMisHN {
	
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
	public static class YFFMISMSG_DY_QUERYPOWER_HN implements ComntMsg.IMSG_READWRITESTREAM {
		
		public int		rtu_id = 0;												//终端编号
		public short	sub_id = 0;												//总加组编号/测量点编号

		public byte		dsdwbh[] 	= new byte[ComntMsg.MSG_STR_LEN_40];		//代收单位编号
		public int 		transymd 	= 0;										//交易yyyymmdd
		public int 		transhms 	= 0;										//交易hhmmss
		
		public byte 	jylsh[]  	= new byte[ComntMsg.MSG_STR_LEN_40];		//交易流水号
		public byte 	spot_code[] = new byte[ComntMsg.MSG_STR_LEN_40];		//网点代码
		public byte 	czybh[]  	= new byte[ComntMsg.MSG_STR_LEN_20];		//操作员编号
		public byte 	cxtj		= 0;										//查询条件
		public byte 	yhbh[]  	= new byte[ComntMsg.MSG_STR_LEN_40];		//用户编号
		public int 		dfny = 0;												//电费年月
		public int 		bank_acct_date = 0;										//非金融机构的账务日期，YYYYMMDD
		
		public byte 	ccbh[] 		= new byte[ComntMsg.MSG_STR_LEN_40];		//出厂编号
		public byte		reserve1[]	= new byte[ComntMsg.MSG_STR_LEN_64];		//备用1
		public byte		reserve2[]	= new byte[ComntMsg.MSG_STR_LEN_64];		//备用2
		
		public void clean() {
			rtu_id	= 0;
			sub_id	= 0;
			
			for (int i = 0; i < dsdwbh.length; i++) dsdwbh[i] = 0;
			transymd = 0;
			transhms = 0;
			for (int i = 0; i < jylsh.length; i++) jylsh[i] = 0;
			for (int i = 0; i < spot_code.length; i++) spot_code[i] = 0;
			for (int i = 0; i < czybh.length; i++) czybh[i] = 0;
			
			cxtj	= 0;
			for (int i = 0; i < yhbh.length; i++) yhbh[i] = 0;
			dfny = 0;
			bank_acct_date = 0;
			
			for (int i = 0; i < ccbh.length; i++) ccbh[i] = 0;
			for (int i = 0; i < reserve1.length; i++) reserve1[i] = 0;
			for (int i = 0; i < reserve2.length; i++) reserve2[i] = 0;		
		}
	
		public void clone(YFFMISMSG_DY_QUERYPOWER_HN msg) {
			rtu_id	= msg.rtu_id;
			sub_id	= msg.sub_id;
			
			for (int i = 0; i < dsdwbh.length; i++) dsdwbh[i] = 0;
			transymd = msg.transymd;
			transhms = msg.transhms;
			for (int i = 0; i < jylsh.length; i++) jylsh[i] = msg.jylsh[i];
			for (int i = 0; i < spot_code.length; i++) spot_code[i] = msg.spot_code[i];
			for (int i = 0; i < czybh.length; i++) czybh[i] = msg.czybh[i];
			
			cxtj	= msg.cxtj;
			for (int i = 0; i < yhbh.length; i++) yhbh[i] = msg.yhbh[i];
			dfny = msg.dfny;
			bank_acct_date = msg.bank_acct_date;
			
			for (int i = 0; i < ccbh.length; i++) ccbh[i] = msg.ccbh[i];
			for (int i = 0; i < reserve1.length; i++) reserve1[i] = msg.reserve1[i];
			for (int i = 0; i < reserve2.length; i++) reserve2[i] = msg.reserve2[i];
			
		}
		
		public YFFMISMSG_DY_QUERYPOWER_HN clone() {
			YFFMISMSG_DY_QUERYPOWER_HN ret_msg = new YFFMISMSG_DY_QUERYPOWER_HN();
			ret_msg.clone(this);
			
			return ret_msg;
		}
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, rtu_id);
			ret_len += ComntStream.writeStream(byte_vect, sub_id);
			
			ret_len += ComntStream.writeStream(byte_vect, dsdwbh, 0, dsdwbh.length);
			ret_len += ComntStream.writeStream(byte_vect, transymd);
			ret_len += ComntStream.writeStream(byte_vect, transhms);
			ret_len += ComntStream.writeStream(byte_vect, jylsh, 0, jylsh.length);
			ret_len += ComntStream.writeStream(byte_vect, spot_code, 0, spot_code.length);
			ret_len += ComntStream.writeStream(byte_vect, czybh, 0, czybh.length);
			ret_len += ComntStream.writeStream(byte_vect, cxtj);
			ret_len += ComntStream.writeStream(byte_vect, yhbh, 0, yhbh.length);
			ret_len += ComntStream.writeStream(byte_vect, dfny);
			ret_len += ComntStream.writeStream(byte_vect, bank_acct_date);
			
			ret_len += ComntStream.writeStream(byte_vect, ccbh, 0, ccbh.length);
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
			
			transymd = ComntStream.readStream(byte_vect, offset + ret_len, transymd); 
			ret_len += ComntStream.getDataSize(transymd);

			transhms = ComntStream.readStream(byte_vect, offset + ret_len, transhms); 
			ret_len += ComntStream.getDataSize(transhms);
			
			ComntStream.readStream(byte_vect, offset + ret_len, jylsh, 0, jylsh.length);
			ret_len += ComntStream.getDataSize(jylsh[0]) * jylsh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, spot_code, 0, spot_code.length);
			ret_len += ComntStream.getDataSize(spot_code[0]) * spot_code.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, czybh, 0, czybh.length);
			ret_len += ComntStream.getDataSize(czybh[0]) * czybh.length;
			
			cxtj = ComntStream.readStream(byte_vect, offset + ret_len, cxtj); 
			ret_len += ComntStream.getDataSize(cxtj);
			
			ComntStream.readStream(byte_vect, offset + ret_len, yhbh, 0, yhbh.length);
			ret_len += ComntStream.getDataSize(yhbh[0]) * yhbh.length;
			
			dfny = ComntStream.readStream(byte_vect, offset + ret_len, dfny); 
			ret_len += ComntStream.getDataSize(dfny);
			
			bank_acct_date = ComntStream.readStream(byte_vect, offset + ret_len, bank_acct_date); 
			ret_len += ComntStream.getDataSize(bank_acct_date);
			
			ComntStream.readStream(byte_vect, offset + ret_len, ccbh, 0, ccbh.length);
			ret_len += ComntStream.getDataSize(ccbh[0]) * ccbh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve1, 0, reserve1.length);
			ret_len += ComntStream.getDataSize(reserve1[0]) * reserve1.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve2, 0, reserve2.length);
			ret_len += ComntStream.getDataSize(reserve2[0]) * reserve2.length;
			
			return ret_len;
		}			
	}	
	
	//低压查询缴费结果--
	public static class YFFMISMSG_DY_QUERYPOWER_RESULT_HN implements ComntMsg.IMSG_READWRITESTREAM {
		
		public static class QUERYPOWER_RESULT_DETAIL {
			public byte		ysbs[]	= new byte[ComntMsg.MSG_STR_LEN_20];	//应收标识
			public int		dfny 	= 0;									//电费年月
			public double	dfje	= 0;									//电费金额
			public double	wyjje	= 0;									//违约金金额
			//河南add
			public byte		remark[]= new byte[ComntMsg.MSG_STR_LEN_80];    //描述本条欠费的其它信息(可以为空)
		}
		
		public int  		retcode = 0;											//返回代码
		public byte 		return_msg[] = new byte[ComntMsg.MSG_STR_LEN_160];		//结果描述
		public byte 		batch_no[] 	 = new byte[ComntMsg.MSG_STR_LEN_40];		//电力资金编号（终端批次号）
		public byte 		hsdwbh[] 	 = new byte[ComntMsg.MSG_STR_LEN_20];		//电力资金结算单位编号
		public byte 		yhbh[]		 = new byte[ComntMsg.MSG_STR_LEN_40];		//用户编号
		public byte 		yhmc[]		 = new byte[ComntMsg.MSG_STR_LEN_80];		//用户名称
		public byte 		yddz[]		 = new byte[ComntMsg.MSG_STR_LEN_160];		//用电地址
		public byte 		hsdwname[]	 = new byte[ComntMsg.MSG_STR_LEN_80];		//用户供电单位名称
		public double 		hjje		 = 0;										//合计金额
		public double 		hjys		 = 0;										//合计预收
		public int			jezbs		 = 0;										//金额总笔数

		public QUERYPOWER_RESULT_DETAIL details_vect[] = null;						//欠费记录
		
	
		public void clean() {
			retcode	= 0;
			for (int i = 0; i < return_msg.length; i++) 	return_msg[i] = 0;
			for (int i = 0; i < batch_no.length; i++) 		batch_no[i] = 0;
			for (int i = 0; i < hsdwbh.length; i++) 		hsdwbh[i] = 0;
			for (int i = 0; i < yhbh.length; i++) 			yhbh[i] = 0;
			for (int i = 0; i < yhmc.length; i++) 			yhmc[i] = 0;
			for (int i = 0; i < yddz.length; i++) 			yddz[i] = 0;
			for (int i = 0; i < hsdwname.length; i++) 		hsdwname[i] = 0;
			hjje	 = 0;
			hjys	 = 0;
			jezbs	 = 0;
		
			for (int i = 0; i < details_vect.length; i++) {
				for (int j = 0; i < details_vect[i].ysbs.length; j++) details_vect[i].ysbs[j] = 0;
				details_vect[i].dfny	= 0;
				details_vect[i].dfje	= 0;
				details_vect[i].wyjje	= 0;
				for (int j = 0; i < details_vect[i].remark.length; j++) details_vect[i].remark[j] = 0;
			}
		}
		
		public void clone(YFFMISMSG_DY_QUERYPOWER_RESULT_HN msg) {
			retcode	= msg.retcode;
			for (int i = 0; i < return_msg.length; i++) return_msg[i] 	= msg.return_msg[i];
			for (int i = 0; i < batch_no.length; i++) 	batch_no[i] 	= msg.batch_no[i];
			for (int i = 0; i < hsdwbh.length; i++) 	hsdwbh[i] 		= msg.hsdwbh[i];
			for (int i = 0; i < yhbh.length; i++) 		yhbh[i] 		= msg.yhbh[i];
			for (int i = 0; i < yhmc.length; i++) 		yhmc[i] 		= msg.yhmc[i];
			for (int i = 0; i < yddz.length; i++) 		yddz[i] 		= msg.yddz[i];
			for (int i = 0; i < hsdwname.length; i++) 	hsdwname[i] 	= msg.hsdwname[i];
			hjje	 = msg.hjje;
			hjys	 = msg.hjys;
			jezbs	 = msg.jezbs;
			
			//需要初始化数组长度
			details_vect = new QUERYPOWER_RESULT_DETAIL[msg.details_vect.length];
			
			for (int i = 0; i < msg.details_vect.length; i++) {
				for (int j = 0; i < details_vect[i].ysbs.length; j++) details_vect[i].ysbs[j] = msg.details_vect[i].ysbs[j];
				
				details_vect[i].dfny	= msg.details_vect[i].dfny;
				details_vect[i].dfje	= msg.details_vect[i].dfje;
				details_vect[i].wyjje	= msg.details_vect[i].wyjje;
				for (int j = 0; i < details_vect[i].remark.length; j++) details_vect[i].remark[j] = msg.details_vect[i].remark[j];
			}
		}
		
		public YFFMISMSG_DY_QUERYPOWER_RESULT_HN clone() {
			YFFMISMSG_DY_QUERYPOWER_RESULT_HN ret_msg = new YFFMISMSG_DY_QUERYPOWER_RESULT_HN();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, retcode);
			ret_len += ComntStream.writeStream(byte_vect, return_msg, 0, return_msg.length);
			ret_len += ComntStream.writeStream(byte_vect, batch_no, 0, batch_no.length);
			ret_len += ComntStream.writeStream(byte_vect, hsdwbh, 0, hsdwbh.length);
			ret_len += ComntStream.writeStream(byte_vect, yhbh, 0, yhbh.length);
			ret_len += ComntStream.writeStream(byte_vect, yhmc, 0, yhmc.length);
			ret_len += ComntStream.writeStream(byte_vect, yddz, 0, yddz.length);
			ret_len += ComntStream.writeStream(byte_vect, hsdwname, 0, hsdwname.length);
			ret_len += ComntStream.writeStream(byte_vect, hjje);
			ret_len += ComntStream.writeStream(byte_vect, hjys);
			ret_len += ComntStream.writeStream(byte_vect, jezbs);
			ret_len += ComntStream.writeStream(byte_vect, (int)details_vect.length);
			
			for(int i = 0; i< details_vect.length ; i++){	
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].ysbs, 0, details_vect[i].ysbs.length);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].dfny);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].dfje);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].wyjje);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].remark, 0, details_vect[i].remark.length);
			}
						
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len 	= 0;
			int vect_size	= 0;
			
			retcode = ComntStream.readStream(byte_vect, offset + ret_len, retcode); 
			ret_len += ComntStream.getDataSize(retcode);
			
			ComntStream.readStream(byte_vect, offset + ret_len, return_msg, 0, return_msg.length);
			ret_len += ComntStream.getDataSize(return_msg[0]) * return_msg.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, batch_no, 0, batch_no.length);
			ret_len += ComntStream.getDataSize(batch_no[0]) * batch_no.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, hsdwbh, 0, hsdwbh.length);
			ret_len += ComntStream.getDataSize(hsdwbh[0]) * hsdwbh.length;
			
			//
			ComntStream.readStream(byte_vect, offset + ret_len, yhbh, 0, yhbh.length);
			ret_len += ComntStream.getDataSize(yhbh[0]) * yhbh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, yhmc, 0, yhmc.length);
			ret_len += ComntStream.getDataSize(yhmc[0]) * yhmc.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, yddz, 0, yddz.length);
			ret_len += ComntStream.getDataSize(yddz[0]) * yddz.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, hsdwname, 0, hsdwname.length);
			ret_len += ComntStream.getDataSize(hsdwname[0]) * hsdwname.length;
		
			hjje = ComntStream.readStream(byte_vect, offset + ret_len, hjje); 
			ret_len += ComntStream.getDataSize(hjje);

			hjys = ComntStream.readStream(byte_vect, offset + ret_len, hjys);
			ret_len += ComntStream.getDataSize(hjys);
			
			jezbs = ComntStream.readStream(byte_vect, offset + ret_len, jezbs); 
			ret_len += ComntStream.getDataSize(jezbs);
			
			vect_size = ComntStream.readStream(byte_vect, offset + ret_len, vect_size); 
			ret_len += ComntStream.getDataSize(vect_size);
			
			details_vect = new QUERYPOWER_RESULT_DETAIL[vect_size];
			
			for(int i = 0; i < details_vect.length ; i++){
				
				details_vect[i] = new QUERYPOWER_RESULT_DETAIL();
				
				ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].ysbs, 0, details_vect[i].ysbs.length);
				ret_len += ComntStream.getDataSize(details_vect[i].ysbs[0]) * details_vect[i].ysbs.length;
				
				details_vect[i].dfny = ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].dfny); 
				ret_len += ComntStream.getDataSize(details_vect[i].dfny);
				
				details_vect[i].dfje = ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].dfje); 
				ret_len += ComntStream.getDataSize(details_vect[i].dfje);
				
				details_vect[i].wyjje = ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].wyjje); 
				ret_len += ComntStream.getDataSize(details_vect[i].wyjje);
				
				ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].remark, 0, details_vect[i].remark.length);
				ret_len += ComntStream.getDataSize(details_vect[i].remark[0]) * details_vect[i].remark.length;
				
			}
			
			return ret_len;
		}		
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("retcode", 	String.valueOf(retcode));
			j_obj.put("yhbh", 		ComntFunc.byte2String(yhbh));
			j_obj.put("yhmc", 		ComntFunc.byte2String(yhmc));
			j_obj.put("yddz", 		ComntFunc.byte2String(yddz));
			j_obj.put("hjje", 		String.valueOf(hjje));
			j_obj.put("hjys", 		String.valueOf(hjys));
			j_obj.put("jezbs", 		String.valueOf(jezbs));
		
			//add
			j_obj.put("return_msg", 	ComntFunc.byte2String(return_msg));
			j_obj.put("batch_no", 		ComntFunc.byte2String(batch_no));
			j_obj.put("hsdwbh", 		ComntFunc.byte2String(hsdwbh));
			j_obj.put("hsdwname", 		ComntFunc.byte2String(hsdwname));
			
			
			JSONObject[] rows = new JSONObject[details_vect.length];
			for(int i = 0; i < details_vect.length; i++){
				JSONObject detail = new JSONObject();
			
				detail.put("ysbs", ComntFunc.byte2String(details_vect[i].ysbs));
				detail.put("dfny", String.valueOf(details_vect[i].dfny));
				detail.put("dfje", String.valueOf(details_vect[i].dfje));
				detail.put("wyjje", String.valueOf(details_vect[i].wyjje));
				detail.put("remark", ComntFunc.byte2String(details_vect[i].remark));
				
				rows[i] = detail;
			}
			
			j_obj.put("detail", rows);
			
			return j_obj.toString();
		}
	}
	
	//低压缴费记录上传
	public static class YFFMISMSG_DY_PAY_HN implements ComntMsg.IMSG_READWRITESTREAM {
		
		public static class POWERPAY_DETAIL{
			//public byte		yhbh[]	= new byte[ComntMsg.MSG_STR_LEN_20];	//用户编号
			public byte		ysbs[]	= new byte[ComntMsg.MSG_STR_LEN_20];	//应收标识
			//河南add
			public int		dfny = 0;								//电费年月
			public double	dfje = 0;								//电费金额
			public double	wyjje = 0;								//违约金金额
			public double	bcssje = 0;								//本次实收金额
			//end
		}
		
		public int		rtu_id		= 0;										//终端编号
		public short	sub_id		= 0;										//总加组编号/测量点编号
		public int		date		= 0;										//缴费日期
		public int		time		= 0;										//缴费时间
		public byte		updateflag	= 0;										//更新标志
		
		public byte 	dsdwbh[]		= new byte[ComntMsg.MSG_STR_LEN_40];    //代收单位编号
		public int		transymd		= 0;									//交易yyyymmdd
		public int      transhms		= 0;									//交易hhmmss
		public byte 	jylsh[]			= new byte[ComntMsg.MSG_STR_LEN_40];    //交易流水号
		public byte 	spot_code[]		= new byte[ComntMsg.MSG_STR_LEN_40];    //网点代码 	
		public byte 	czybh[]			= new byte[ComntMsg.MSG_STR_LEN_20];   	//操作员编号 
		public byte 	yhbh[]			= new byte[ComntMsg.MSG_STR_LEN_40];    //用户编号
		public byte 	hsdwbh[]		= new byte[ComntMsg.MSG_STR_LEN_20];    //核算单位
		public byte 	batch_no[]		= new byte[ComntMsg.MSG_STR_LEN_40];    //电力资金编号（终端批次号）
		public byte 	order_no[]		= new byte[ComntMsg.MSG_STR_LEN_40];    //订单编号(银电联网订单可为空，结果集不能为空)
		public byte 	jffs[]			= new byte[ComntMsg.MSG_STR_LEN_8];    	//缴费方式
		public byte 	zffs[]			= new byte[ComntMsg.MSG_STR_LEN_8];    	//支付方式(09转账)
		public int 		bank_pay_mode	= 0;									//非金融机构缴费方式(01柜台、02 POS、03网银、04电话非金融机构)
		public double 	jfje			= 0;									//缴费金额
		public int 		yhzwrq			= 0;									//银行账务日期
		public int 		jfbs			= 0;									//缴费笔数
		
		public byte		ccbh[] 			= new byte[ComntMsg.MSG_STR_LEN_40];	//出厂编号
		public byte		reserve1[] 		= new byte[ComntMsg.MSG_STR_LEN_64];	//备用1
		public byte		reserve2[] 		= new byte[ComntMsg.MSG_STR_LEN_64];	//备用2

		public POWERPAY_DETAIL[] details_vect = null;		//欠费信息
		
	
		public void clean() {
			rtu_id		= 0;
			sub_id		= 0;
			date		= 0;
			time		= 0;
			updateflag	= 0;
			
			for (int i = 0; i < dsdwbh.length; i++) 	dsdwbh[i] = 0;
			for (int i = 0; i < czybh.length; i++) 		czybh[i] = 0;
			for (int i = 0; i < jylsh.length; i++) 		jylsh[i] = 0;
			for (int i = 0; i < yhbh.length; i++) 		yhbh[i] = 0;
			for (int i = 0; i < jffs.length; i++) 		jffs[i] = 0;
			jfje		= 0;									
			yhzwrq		= 0;	
			jfbs		= 0;
			
			transymd		= 0;									
			transhms		= 0;
			for (int i = 0; i < spot_code.length; i++) 	spot_code[i] = 0;
			for (int i = 0; i < hsdwbh.length; i++) 	hsdwbh[i] = 0;
			for (int i = 0; i < batch_no.length; i++) 	batch_no[i] = 0;
			for (int i = 0; i < order_no.length; i++) 	order_no[i] = 0;
			
			for (int i = 0; i < zffs.length; i++) 		zffs[i] = 0;
			bank_pay_mode	= 0;									
											
			for (int i = 0; i < ccbh.length; i++) 		ccbh[i] = 0;
			for (int i = 0; i < reserve1.length; i++) 	reserve1[i] = 0;
			for (int i = 0; i < reserve2.length; i++) 	reserve2[i] = 0;
			
			for (int i = 0; i < details_vect.length; i++) {
				for (int j = 0; i < details_vect[i].ysbs.length; j++) details_vect[i].ysbs[j] = 0;
				details_vect[i].dfny = 0;								
				details_vect[i].dfje = 0;								
				details_vect[i].wyjje = 0;								
				details_vect[i].bcssje = 0;								
			}
		}
		
		public void clone(YFFMISMSG_DY_PAY_HN msg) {
			rtu_id		= msg.rtu_id;
			sub_id		= msg.sub_id;
			date		= msg.date;
			time		= msg.time;
			updateflag	= msg.updateflag;
			
			for (int i = 0; i < dsdwbh.length; i++) 	dsdwbh[i] = msg.dsdwbh[i];
			for (int i = 0; i < czybh.length; i++) 		czybh[i] = msg.czybh[i];
			for (int i = 0; i < jylsh.length; i++) 		jylsh[i] = msg.jylsh[i];
			for (int i = 0; i < yhbh.length; i++) 		yhbh[i] = msg.yhbh[i];
			for (int i = 0; i < jffs.length; i++) 		jffs[i] = msg.jffs[i];
			jfje		= msg.jfje;									
			yhzwrq		= msg.yhzwrq;	
			jfbs		= msg.jfbs;
			
			transymd		= msg.transymd;									
			transhms		= msg.transhms;
			for (int i = 0; i < spot_code.length; i++) 	spot_code[i] = msg.spot_code[i];
			for (int i = 0; i < hsdwbh.length; i++) 	hsdwbh[i] = msg.hsdwbh[i];
			for (int i = 0; i < batch_no.length; i++) 	batch_no[i] = msg.batch_no[i];
			for (int i = 0; i < order_no.length; i++) 	order_no[i] = msg.order_no[i];
			
			for (int i = 0; i < zffs.length; i++) 		zffs[i] = msg.zffs[i];
			bank_pay_mode	= msg.bank_pay_mode;									
											
			for (int i = 0; i < ccbh.length; i++) 		ccbh[i] = msg.ccbh[i];
			for (int i = 0; i < reserve1.length; i++) 	reserve1[i] = msg.reserve1[i];
			for (int i = 0; i < reserve2.length; i++) 	reserve2[i] = msg.reserve2[i];
			
			//需要初始化数组长度
			details_vect = new POWERPAY_DETAIL[msg.details_vect.length];
			for (int i = 0; i < details_vect.length; i++) {
				for (int j = 0; i < details_vect[i].ysbs.length; j++) details_vect[i].ysbs[j] = msg.details_vect[i].ysbs[j];
				details_vect[i].dfny = msg.details_vect[i].dfny;								
				details_vect[i].dfje = msg.details_vect[i].dfje;								
				details_vect[i].wyjje = msg.details_vect[i].wyjje;								
				details_vect[i].bcssje = msg.details_vect[i].bcssje;								
			}
		}
		
		public YFFMISMSG_DY_PAY_HN clone() {
			YFFMISMSG_DY_PAY_HN ret_msg = new YFFMISMSG_DY_PAY_HN();
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
			ret_len += ComntStream.writeStream(byte_vect, transymd);
			ret_len += ComntStream.writeStream(byte_vect, transhms);
			ret_len += ComntStream.writeStream(byte_vect, jylsh,  0, jylsh.length);
			ret_len += ComntStream.writeStream(byte_vect, spot_code, 0, spot_code.length);
			ret_len += ComntStream.writeStream(byte_vect, czybh,  0, czybh.length);
			ret_len += ComntStream.writeStream(byte_vect, yhbh,   0, yhbh.length);
			ret_len += ComntStream.writeStream(byte_vect, hsdwbh, 0, hsdwbh.length);
			ret_len += ComntStream.writeStream(byte_vect, batch_no, 0, batch_no.length);
			ret_len += ComntStream.writeStream(byte_vect, order_no, 0, order_no.length);
			ret_len += ComntStream.writeStream(byte_vect, jffs,   0, jffs.length);
			ret_len += ComntStream.writeStream(byte_vect, zffs, 0, zffs.length);
			ret_len += ComntStream.writeStream(byte_vect, bank_pay_mode);
			ret_len += ComntStream.writeStream(byte_vect, jfje);
			ret_len += ComntStream.writeStream(byte_vect, yhzwrq);
			ret_len += ComntStream.writeStream(byte_vect, jfbs);
			ret_len += ComntStream.writeStream(byte_vect, ccbh, 0, ccbh.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve1, 0, reserve1.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve2, 0, reserve2.length);
			
			ret_len += ComntStream.writeStream(byte_vect, (int)details_vect.length);
			
			for(int i = 0; i< details_vect.length ; i++){
				//ret_len += ComntStream.writeStream(byte_vect, details_vect[i].yhbh, 0, details_vect[i].yhbh.length);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].ysbs, 0, details_vect[i].ysbs.length);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].dfny);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].dfje);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].wyjje);
				ret_len += ComntStream.writeStream(byte_vect, details_vect[i].bcssje);
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
			
			transymd = ComntStream.readStream(byte_vect, offset + ret_len, transymd); 
			ret_len += ComntStream.getDataSize(transymd);
			
			transhms = ComntStream.readStream(byte_vect, offset + ret_len, transhms); 
			ret_len += ComntStream.getDataSize(transhms);
			
			ComntStream.readStream(byte_vect, offset + ret_len, jylsh, 0, jylsh.length);
			ret_len += ComntStream.getDataSize(jylsh[0]) * jylsh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, spot_code, 0, spot_code.length);
			ret_len += ComntStream.getDataSize(spot_code[0]) * spot_code.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, czybh, 0, czybh.length);
			ret_len += ComntStream.getDataSize(czybh[0]) * czybh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, yhbh, 0, yhbh.length);
			ret_len += ComntStream.getDataSize(yhbh[0]) * yhbh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, hsdwbh, 0, hsdwbh.length);
			ret_len += ComntStream.getDataSize(hsdwbh[0]) * hsdwbh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, batch_no, 0, batch_no.length);
			ret_len += ComntStream.getDataSize(batch_no[0]) * batch_no.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, order_no, 0, order_no.length);
			ret_len += ComntStream.getDataSize(order_no[0]) * order_no.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, jffs, 0, jffs.length);
			ret_len += ComntStream.getDataSize(jffs[0]) * jffs.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, zffs, 0, zffs.length);
			ret_len += ComntStream.getDataSize(zffs[0]) * zffs.length;
			
			bank_pay_mode = ComntStream.readStream(byte_vect, offset + ret_len, bank_pay_mode); 
			ret_len += ComntStream.getDataSize(bank_pay_mode);
			
			jfje = ComntStream.readStream(byte_vect, offset + ret_len, jfje); 
			ret_len += ComntStream.getDataSize(jfje);
			
			yhzwrq = ComntStream.readStream(byte_vect, offset + ret_len, yhzwrq); 
			ret_len += ComntStream.getDataSize(yhzwrq);
			
			jfbs = ComntStream.readStream(byte_vect, offset + ret_len, jfbs); 
			ret_len += ComntStream.getDataSize(jfbs);
			
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
				
//				ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].yhbh, 0, details_vect[i].yhbh.length);
//				ret_len += ComntStream.getDataSize(details_vect[i].yhbh[0]) * details_vect[i].yhbh.length;
				
				ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].ysbs, 0, details_vect[i].ysbs.length);
				ret_len += ComntStream.getDataSize(details_vect[i].ysbs[0]) * details_vect[i].ysbs.length;
				
				details_vect[i].dfny = ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].dfny); 
				ret_len += ComntStream.getDataSize(details_vect[i].dfny);
				
				details_vect[i].dfje = ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].dfje); 
				ret_len += ComntStream.getDataSize(details_vect[i].dfje);
				
				details_vect[i].wyjje = ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].wyjje); 
				ret_len += ComntStream.getDataSize(details_vect[i].wyjje);
				
				details_vect[i].bcssje = ComntStream.readStream(byte_vect, offset + ret_len, details_vect[i].bcssje); 
				ret_len += ComntStream.getDataSize(details_vect[i].bcssje);
				
			}
			
			return ret_len;
		}		
	}

	
	//低压缴费返回结果
	public static class YFFMISMSG_DY_PAY_RESULT_HN implements ComntMsg.IMSG_READWRITESTREAM {
		/*public static class PAYRESULTDETAIL_BASE{
			public byte 	fpbh[]	= new byte[ComntMsg.MSG_STR_LEN_20];		//发票编号
			public byte 	yhbh[]	= new byte[ComntMsg.MSG_STR_LEN_20];		//用户编号
			public byte 	yhmc[]	= new byte[ComntMsg.MSG_STR_LEN_60];		//用户名称
			public byte 	yddz[]	= new byte[ComntMsg.MSG_STR_LEN_120];		//用电地址
			public byte 	yhfl[]	= new byte[ComntMsg.MSG_STR_LEN_20];		//用户分类
			public byte 	cbdbh[]	= new byte[ComntMsg.MSG_STR_LEN_20];		//抄表段编号
			public byte 	csy[]	= new byte[ComntMsg.MSG_STR_LEN_20];		//抄算员
			public byte 	khyh[]	= new byte[ComntMsg.MSG_STR_LEN_20];		//开户银行
			public byte 	yhzh[]	= new byte[ComntMsg.MSG_STR_LEN_40];		//银行账户
			public int  	dfny;												//电费年月
			public int  	sfrq;												//收费日期yyyymmdd
			public double	wyjje;												//违约金金额
			public double	scye;												//上次余额
			public double	bcye;												//本次余额
			public double	sctw;												//上次调尾
			public double	bctw;												//本次调尾
			public double	yshj;												//应收合计
			public byte		yshj_dx[]	= new byte[ComntMsg.MSG_STR_LEN_60];	//应收合计(大写)
			public double	jfje;												//缴费金额
			public byte		jfje_dx[]	= new byte[ComntMsg.MSG_STR_LEN_60];	//缴费金额(大写)
		}
		public static class PAYRESULTDETAIL_DL{
			public byte		fpbh[]	= new byte[ComntMsg.MSG_STR_LEN_20];	//发票编号
			public byte		zcbh[]	= new byte[ComntMsg.MSG_STR_LEN_40];	//资产编号
			public byte		sslx[]	= new byte[ComntMsg.MSG_STR_LEN_20];	//示数类型
			public double	scss;											//上次示数
			public double	bcss;											//本次示数
			public double	zhbl;											//综合倍率
			public double	cjdl;											//抄见电量
			public double	tbdl;											//退补电量
		}
		public static class PAYRESULDETAIL_DJ{
			public byte		fpbh[]	= new byte[ComntMsg.MSG_STR_LEN_20];	//发票编号
			public byte		djmc[]	= new byte[ComntMsg.MSG_STR_LEN_40];	//电价名称
			public double	dj;												//单价
			public double	jfdl;											//计费电量
			public double	fxje;											//分项金额
		}
		*/
		
		public int  		retcode = 0;									//返回代码
		//河南add
		public byte 		return_msg[] = new byte[ComntMsg.MSG_STR_LEN_160];  //结果描述
		
		public void clean() {
			retcode		= 0;
			for (int i = 0; i < return_msg.length; i++) return_msg[i] = 0;
		}
		
		public void clone(YFFMISMSG_DY_PAY_RESULT_HN msg) {
			retcode		= msg.retcode;
			for (int i = 0; i < return_msg.length; i++) return_msg[i] = msg.return_msg[i];
			
		}
		
		public YFFMISMSG_DY_PAY_RESULT_HN clone() {
			YFFMISMSG_DY_PAY_RESULT_HN ret_msg = new YFFMISMSG_DY_PAY_RESULT_HN();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, retcode);
			ret_len += ComntStream.writeStream(byte_vect, return_msg, 0, return_msg.length);
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len 	= 0;

			retcode = ComntStream.readStream(byte_vect, offset + ret_len, retcode); 
			ret_len += ComntStream.getDataSize(retcode);
			
			ComntStream.readStream(byte_vect, offset + ret_len, return_msg, 0, return_msg.length);
			ret_len += ComntStream.getDataSize(return_msg[0]) * return_msg.length;

			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("retcode", 	String.valueOf(retcode));
			j_obj.put("return_msg", ComntFunc.byte2String(return_msg));
			
			return j_obj.toString();
		}
		
	}
	
	//低压冲正操作
	public static class YFFMISMSG_DY_REVER_HN implements ComntMsg.IMSG_READWRITESTREAM {

		public int		rtu_id		= 0;									//终端编号
		public short	sub_id		= 0;									//总加组编号/测量点编号
		public int		date		= 0;
		public int		time		= 0;
		public int		last_date	= 0;									//被冲正记录日期
		public int		last_time	= 0;									//被冲正记录时间
		public byte		updateflag	= 0;									//更新标志
		
		//chg
		public byte		dsdwbh[]	= new byte[ComntMsg.MSG_STR_LEN_40];	//代收单位编号
		public int 		transymd	= 0;									//交易yyyymmdd
		public int 		transhms	= 0;									//交	易hhmmss
		public byte		czjylsh[]	= new byte[ComntMsg.MSG_STR_LEN_40];	//冲正交易流水号
		public byte		spot_code[]	= new byte[ComntMsg.MSG_STR_LEN_40];	//网点代码
		public byte		czybh[]		= new byte[ComntMsg.MSG_STR_LEN_20];	//操作员编号
		public byte		yhbh[]	    = new byte[ComntMsg.MSG_STR_LEN_40];	//用户编号
		public byte		yjylsh[]	= new byte[ComntMsg.MSG_STR_LEN_40];	//原交易流水号
		public int		yhzwrq		= 0;									//银行账务日期
		
		public byte		ccbh[]		= new byte[ComntMsg.MSG_STR_LEN_40];	//出厂编号
		public byte		reserve1[]	= new byte[ComntMsg.MSG_STR_LEN_64];	//备用1
		public byte		reserve2[]	= new byte[ComntMsg.MSG_STR_LEN_64];	//备用2
		
		public void clean() {
			rtu_id		= 0;
			sub_id		= 0;
			date		= 0;
			time		= 0;
			last_date	= 0;
			last_time	= 0;
			updateflag	= 0;
			for (int i = 0; i < dsdwbh.length; i++) 	dsdwbh[i] = 0;
			transymd	= 0;									
			transhms	= 0;									
				
			for (int i = 0; i < czjylsh.length; i++) 	czjylsh[i] = 0;
			for (int i = 0; i < spot_code.length; i++) 	spot_code[i] = 0;
			for (int i = 0; i < czybh.length; i++) 		czybh[i] = 0;
			for (int i = 0; i < yhbh.length; i++) 		yhbh[i] = 0;
			for (int i = 0; i < yjylsh.length; i++) 	yjylsh[i] = 0;
			yhzwrq		= 0;
			for (int i = 0; i < ccbh.length; i++) 		ccbh[i] = 0;
			for (int i = 0; i < reserve1.length; i++) 	reserve1[i] = 0;
			for (int i = 0; i < reserve2.length; i++) 	reserve2[i] = 0;
		}
		
		public void clone(YFFMISMSG_DY_REVER_HN msg) {
			rtu_id		= msg.rtu_id;
			sub_id		= msg.sub_id;
			date		= msg.date;
			time		= msg.time;
			last_date	= msg.last_date;
			last_time	= msg.last_time;
			updateflag	= msg.updateflag;
			
			for (int i = 0; i < dsdwbh.length; i++) dsdwbh[i]		= msg.dsdwbh[i];
			
			transymd	= msg.transymd;									
			transhms	= msg.transhms;

			for (int i = 0; i < czjylsh.length; i++) czjylsh[i] 	= msg.czjylsh[i];
			for (int i = 0; i < spot_code.length; i++) spot_code[i] = msg.spot_code[i];
			for (int i = 0; i < czybh.length; i++) czybh[i] 		= msg.czybh[i];
			for (int i = 0; i < yhbh.length; i++) yhbh[i] 			= msg.yhbh[i];
			for (int i = 0; i < yjylsh.length; i++) yjylsh[i]		= msg.yjylsh[i];
			yhzwrq		= msg.yhzwrq;
			for (int i = 0; i < ccbh.length; i++) ccbh[i] 			= msg.ccbh[i];
			for (int i = 0; i < reserve1.length; i++) reserve1[i]	= msg.reserve1[i];
			for (int i = 0; i < reserve2.length; i++) reserve2[i]	= msg.reserve2[i];
			
		}
		
		public YFFMISMSG_DY_REVER_HN clone() {
			YFFMISMSG_DY_REVER_HN ret_msg = new YFFMISMSG_DY_REVER_HN();
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
			ret_len += ComntStream.writeStream(byte_vect, dsdwbh, 0, dsdwbh.length);
			ret_len += ComntStream.writeStream(byte_vect, transymd);
			ret_len += ComntStream.writeStream(byte_vect, transhms);
			ret_len += ComntStream.writeStream(byte_vect, czjylsh, 0, czjylsh.length);
			ret_len += ComntStream.writeStream(byte_vect, spot_code, 0, spot_code.length);
			ret_len += ComntStream.writeStream(byte_vect, czybh, 0, czybh.length);
			ret_len += ComntStream.writeStream(byte_vect, yhbh, 0, yhbh.length);
			ret_len += ComntStream.writeStream(byte_vect, yjylsh, 0, yjylsh.length);
			ret_len += ComntStream.writeStream(byte_vect, yhzwrq);
			ret_len += ComntStream.writeStream(byte_vect, ccbh, 0, ccbh.length);
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
			
			transymd = ComntStream.readStream(byte_vect, offset + ret_len, transymd); 
			ret_len += ComntStream.getDataSize(transymd);

			transhms = ComntStream.readStream(byte_vect, offset + ret_len, transhms); 
			ret_len += ComntStream.getDataSize(transhms);
			
			ComntStream.readStream(byte_vect, offset + ret_len, czjylsh, 0, czjylsh.length);
			ret_len += ComntStream.getDataSize(czjylsh[0]) * czjylsh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, spot_code, 0, spot_code.length);
			ret_len += ComntStream.getDataSize(spot_code[0]) * spot_code.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, czybh, 0, czybh.length);
			ret_len += ComntStream.getDataSize(czybh[0]) * czybh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, yhbh, 0, yhbh.length);
			ret_len += ComntStream.getDataSize(yhbh[0]) * yhbh.length;
			
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
	public static class YFFMISMSG_DY_REVER_RESULT_HN implements ComntMsg.IMSG_READWRITESTREAM {

		public int		retcode		 = 0;									    //返回代码
		public byte     return_msg[] = new byte[ComntMsg.MSG_STR_LEN_160];		//结果描述
		
		public void clean() {
			retcode		= 0;
			for (int i = 0; i < return_msg.length; i++) return_msg[i] = 0;
		}
		
		public void clone(YFFMISMSG_DY_REVER_RESULT_HN msg) {
			retcode		= msg.retcode;
			for (int i = 0; i < return_msg.length; i++) return_msg[i] = msg.return_msg[i];
		}
		
		public YFFMISMSG_DY_REVER_RESULT_HN clone() {
			YFFMISMSG_DY_REVER_RESULT_HN ret_msg = new YFFMISMSG_DY_REVER_RESULT_HN();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, retcode);
			ret_len += ComntStream.writeStream(byte_vect, return_msg, 0, return_msg.length);
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len 	= 0;

			retcode = ComntStream.readStream(byte_vect, offset + ret_len, retcode); 
			ret_len += ComntStream.getDataSize(retcode);
			
			ComntStream.readStream(byte_vect, offset + ret_len, return_msg, 0, return_msg.length);
			ret_len += ComntStream.getDataSize(return_msg[0]) * return_msg.length;

			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("retcode", 	String.valueOf(retcode));
			j_obj.put("return_msg", ComntFunc.byte2String(return_msg));
			return j_obj.toString();
		}
	}
	
	//高压查询缴费
	public static class YFFMISMSG_GY_QUERYPOWER_HN extends YFFMISMSG_DY_QUERYPOWER_HN{}
	//高压查询缴费结果
	public static class YFFMISMSG_GY_QUERYPOWER_RESULT_HN extends YFFMISMSG_DY_QUERYPOWER_RESULT_HN{}

	//高压缴费记录上传
	public static class YFFMISMSG_GY_PAY_HN extends YFFMISMSG_DY_PAY_HN{}
	//高压缴费返回结果
	public static class YFFMISMSG_GY_PAY_RESULT_HN extends YFFMISMSG_DY_PAY_RESULT_HN{}
	//高压冲正操作
	public static class YFFMISMSG_GY_REVER_HN extends YFFMISMSG_DY_REVER_HN{}					
	//高压冲正返回结果
	public static class YFFMISMSG_GY_REVER_RESULT_HN extends YFFMISMSG_DY_REVER_RESULT_HN{}
	
	//对账操作
	public static class YFFMISMSG_CHECK_HN implements ComntMsg.IMSG_READWRITESTREAM {

		public byte		dsdwbh[]	= new byte[ComntMsg.MSG_STR_LEN_40];	//代收单位编号
		public int		transymd	= 0;									//交易yyyymmdd
		public int		transhms	= 0;									//交易hhmmss
		public byte		jylsh[]   	= new byte[ComntMsg.MSG_STR_LEN_40];	//交易流水号
		public byte		spot_code[] = new byte[ComntMsg.MSG_STR_LEN_40];	//网点代码
		public byte		czybh[]		= new byte[ComntMsg.MSG_STR_LEN_20];	//操作员编号
		public int		pay_classify= 0;									//缴费分类（01：后付费、02 预付费）
		public byte		hsdw[]		= new byte[ComntMsg.MSG_STR_LEN_20];	//核算单位
		public byte		dzwjm[]		= new byte[ComntMsg.MSG_STR_LEN_60];	//对账文件名
		public byte		reserve1[]	= new byte[ComntMsg.MSG_STR_LEN_64];	//备用1
		public byte		reserve2[]	= new byte[ComntMsg.MSG_STR_LEN_64];	//备用2
		
		public void clean() {
			for (int i = 0; i < dsdwbh.length; i++) dsdwbh[i] = 0;
			transymd	= 0;
			transhms	= 0;
			for (int i = 0; i < jylsh.length; i++) jylsh[i] = 0;
			for (int i = 0; i < spot_code.length; i++) spot_code[i] = 0;
			for (int i = 0; i < czybh.length; i++) czybh[i] = 0;
			pay_classify= 0;
			for (int i = 0; i < hsdw.length; i++) hsdw[i] = 0;
			for (int i = 0; i < dzwjm.length; i++) dzwjm[i] = 0;
			for (int i = 0; i < reserve1.length; i++) reserve1[i] = 0;
			for (int i = 0; i < reserve2.length; i++) reserve2[i] = 0;
		}
		
		public void clone(YFFMISMSG_CHECK_HN msg) {
			for (int i = 0; i < dsdwbh.length; i++) dsdwbh[i]		= msg.dsdwbh[i];
			transymd	= msg.transymd;
			transhms	= msg.transhms;
			for (int i = 0; i < jylsh.length; i++) jylsh[i] = msg.jylsh[i];
			for (int i = 0; i < spot_code.length; i++) spot_code[i] = msg.spot_code[i];
			for (int i = 0; i < czybh.length; i++) czybh[i] = msg.czybh[i];
			pay_classify = msg.pay_classify;
			for (int i = 0; i < dzwjm.length; i++) dzwjm[i] 		= msg.dzwjm[i];
			//貌似少一个
			for (int i = 0; i < reserve1.length; i++) reserve1[i]	= msg.reserve1[i];
			for (int i = 0; i < reserve2.length; i++) reserve2[i]	= msg.reserve2[i];
			
		}
		
		public YFFMISMSG_CHECK_HN clone() {
			YFFMISMSG_CHECK_HN ret_msg = new YFFMISMSG_CHECK_HN();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			ret_len += ComntStream.writeStream(byte_vect, dsdwbh, 0, dsdwbh.length);
			
			ret_len += ComntStream.writeStream(byte_vect, transymd);
			ret_len += ComntStream.writeStream(byte_vect, transhms);
			ret_len += ComntStream.writeStream(byte_vect, jylsh, 0, jylsh.length);
			ret_len += ComntStream.writeStream(byte_vect, spot_code, 0, spot_code.length);
			ret_len += ComntStream.writeStream(byte_vect, czybh, 0, czybh.length);
			ret_len += ComntStream.writeStream(byte_vect, pay_classify);
			ret_len += ComntStream.writeStream(byte_vect, dzwjm, 0, dzwjm.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve1, 0, reserve1.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve2, 0, reserve2.length);
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len 	= 0;
			int vect_size 	= 0;
			
			ComntStream.readStream(byte_vect, offset + ret_len, dsdwbh, 0, dsdwbh.length);
			ret_len += ComntStream.getDataSize(dsdwbh[0]) * dsdwbh.length;
			
			transymd = ComntStream.readStream(byte_vect, offset + ret_len, transymd); 
			ret_len += ComntStream.getDataSize(transymd);
			
			transhms = ComntStream.readStream(byte_vect, offset + ret_len, transhms); 
			ret_len += ComntStream.getDataSize(transhms);
			
			ComntStream.readStream(byte_vect, offset + ret_len, jylsh, 0, jylsh.length);
			ret_len += ComntStream.getDataSize(jylsh[0]) * jylsh.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, spot_code, 0, spot_code.length);
			ret_len += ComntStream.getDataSize(spot_code[0]) * spot_code.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, czybh, 0, czybh.length);
			ret_len += ComntStream.getDataSize(czybh[0]) * czybh.length;
			
			pay_classify = ComntStream.readStream(byte_vect, offset + ret_len, pay_classify); 
			ret_len += ComntStream.getDataSize(pay_classify);
			
			ComntStream.readStream(byte_vect, offset + ret_len, dzwjm, 0, dzwjm.length);
			ret_len += ComntStream.getDataSize(dzwjm[0]) * dzwjm.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve1, 0, reserve1.length);
			ret_len += ComntStream.getDataSize(reserve1[0]) * reserve1.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve2, 0, reserve2.length);
			ret_len += ComntStream.getDataSize(reserve2[0]) * reserve2.length;

			return ret_len;
		}
	}
	
	//对账操作结果
	public static class YFFMISMSG_CHECK_RESULT_HN implements ComntMsg.IMSG_READWRITESTREAM {

		public int		retcode	= 0;									//返回代码
		//河南add
		public byte    return_msg[] = new byte[ComntMsg.MSG_STR_LEN_160];		//结果描述
		
		public void clean() {
			retcode	= 0;
			for (int i = 0; i < return_msg.length; i++) return_msg[i] = 0;
		}
		
		public void clone(YFFMISMSG_CHECK_RESULT_HN msg) {
			retcode		= msg.retcode;
			for (int i = 0; i < return_msg.length; i++) return_msg[i]	= msg.return_msg[i];
			
		}
		
		public YFFMISMSG_CHECK_RESULT_HN clone() {
			YFFMISMSG_CHECK_RESULT_HN ret_msg = new YFFMISMSG_CHECK_RESULT_HN();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			ret_len += ComntStream.writeStream(byte_vect, retcode);
			ret_len += ComntStream.writeStream(byte_vect, return_msg, 0, return_msg.length);
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len 	= 0;

			retcode = ComntStream.readStream(byte_vect, offset + ret_len, retcode); 
			ret_len += ComntStream.getDataSize(retcode);
			
			ComntStream.readStream(byte_vect, offset + ret_len, return_msg, 0, return_msg.length);
			ret_len += ComntStream.getDataSize(return_msg[0]) * return_msg.length;
			
			return ret_len;
		}
	}
	
	//20131203  zhp
	//与MIS系统高低压对账
	public static class YFFMISMSG_GDYCHECKPAY_HN implements ComntMsg.IMSG_READWRITESTREAM {
		public byte dsdwbh[]	= new byte[ComntMsg.MSG_STR_LEN_40];		//代收单位编号   渠道商编号
		public int	transymd	= 0;										//交易yyyymmdd
		public int	transhms	= 0;										//交易hhmmss
		
		public byte spot_code[] = new byte[ComntMsg.MSG_STR_LEN_40];		//网点代码
		public byte czybh[] 	= new byte[ComntMsg.MSG_STR_LEN_20];		//操作员编号
		public byte reserve1[]	= new byte[ComntMsg.MSG_STR_LEN_64];		//备用1
		public byte reserve2[]	= new byte[ComntMsg.MSG_STR_LEN_64];		//备用2
		
		public void clean(){
			for (int i = 0; i < dsdwbh.length; i++)   	dsdwbh[i] = 0;
			transymd	= 0;										
			transhms	= 0;
			for (int i = 0; i < spot_code.length; i++)	spot_code[i] = 0;
			for (int i = 0; i < czybh.length; i++)    	czybh[i] = 0;
			for (int i = 0; i < reserve1.length; i++) 	reserve1[i] = 0;
			for (int i = 0; i < reserve2.length; i++) 	reserve2[i] = 0;
		}
		
		public void clone(YFFMISMSG_GDYCHECKPAY_HN msg) {
			for (int i = 0; i < dsdwbh.length; i++)   	dsdwbh[i] = msg.dsdwbh[i];
			transymd	= msg.transymd;										
			transhms	= msg.transhms;
			for (int i = 0; i < spot_code.length; i++)  spot_code[i] = msg.spot_code[i];
			for (int i = 0; i < czybh.length; i++)    	czybh[i] = msg.czybh[i];
			for (int i = 0; i < reserve1.length; i++) 	reserve1[i] = msg.reserve1[i];
			for (int i = 0; i < reserve2.length; i++) 	reserve2[i] = msg.reserve2[i];
		}
		
		public YFFMISMSG_GDYCHECKPAY_HN clone(){
			YFFMISMSG_GDYCHECKPAY_HN ret_msg = new YFFMISMSG_GDYCHECKPAY_HN();
			ret_msg.clone(this);
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			ret_len += ComntStream.writeStream(byte_vect, dsdwbh, 0, dsdwbh.length);
			ret_len += ComntStream.writeStream(byte_vect, transymd);
			ret_len += ComntStream.writeStream(byte_vect, transhms);
			ret_len += ComntStream.writeStream(byte_vect, spot_code, 0, spot_code.length);
			ret_len += ComntStream.writeStream(byte_vect, czybh, 0, czybh.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve1, 0, reserve1.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve2, 0, reserve2.length);
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len 	= 0;

			ComntStream.readStream(byte_vect, offset + ret_len, dsdwbh, 0, dsdwbh.length);
			ret_len += ComntStream.getDataSize(dsdwbh[0]) * dsdwbh.length;

			transymd = ComntStream.readStream(byte_vect, offset + ret_len, transymd); 
			ret_len += ComntStream.getDataSize(transymd);
			
			transhms = ComntStream.readStream(byte_vect, offset + ret_len, transhms); 
			ret_len += ComntStream.getDataSize(transhms);
			
			ComntStream.readStream(byte_vect, offset + ret_len, spot_code, 0, spot_code.length);
			ret_len += ComntStream.getDataSize(spot_code[0]) * spot_code.length;
			
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
	public static class YFFMISMSG_GDYCHECKPAY_RESULT_HN implements ComntMsg.IMSG_READWRITESTREAM{
		public int		retcode	= 0;									//返回代码
		public byte		return_msg[]	= new byte[ComntMsg.MSG_STR_LEN_160];	//结果描述
		
		public void clean() {
			retcode	= 0;
			for (int i = 0; i < return_msg.length; i++) return_msg[i] = 0;
		}
		
		public void clone(YFFMISMSG_GDYCHECKPAY_RESULT_HN msg) {
			retcode		= msg.retcode;
			for (int i = 0; i < return_msg.length; i++) return_msg[i]	= msg.return_msg[i];
			
		}
		
		public YFFMISMSG_GDYCHECKPAY_RESULT_HN clone() {
			YFFMISMSG_GDYCHECKPAY_RESULT_HN ret_msg = new YFFMISMSG_GDYCHECKPAY_RESULT_HN();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			ret_len += ComntStream.writeStream(byte_vect, retcode);
			ret_len += ComntStream.writeStream(byte_vect, return_msg, 0, return_msg.length);
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len 	= 0;

			retcode = ComntStream.readStream(byte_vect, offset + ret_len, retcode); 
			ret_len += ComntStream.getDataSize(retcode);
			
			ComntStream.readStream(byte_vect, offset + ret_len, return_msg, 0, return_msg.length);
			ret_len += ComntStream.getDataSize(return_msg[0]) * return_msg.length;
			
			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			j_obj.put("retcode", 	String.valueOf(retcode));
			j_obj.put("return_msg", 		ComntFunc.byte2String(return_msg));
			
			return j_obj.toString();
		}
	}
	
}


