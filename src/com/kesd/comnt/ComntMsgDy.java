package com.kesd.comnt;

import com.kesd.common.YFFDef;
import com.libweb.comnt.ComntStream;
import com.libweb.comnt.ComntVector;

/**
 * 低压部分消息结构
 * @author zhpeng
 * @date   20131019
 */
public class ComntMsgDy extends ComntMsg{
	
	//低压业务操作
	//低压操作-开户 CYFF_TASKMSG_DYOPER_ADDRES  
	public static class MSG_DYOPER_ADDRES implements IMSG_READWRITESTREAM {
		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	mpid			= 0;				//点号
		public short	yffalarmid		= 0;				//报警方案

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员
		public byte		paytype			= 0;				//缴费方式

		public byte		opresult		= 0;
		public int		buynum			= 0;				//购电次数
		
		//20140606 zhp 新增 
		public YFFDef.YFF_PAYBZ		bz		 = new YFFDef.YFF_PAYBZ();		//缴费表字		
		public YFFDef.YFF_PASSRAND  passrand = new YFFDef.YFF_PASSRAND();	//随机密码
		
		public YFFDef.YFF_PAYMONEY 	money	= new YFFDef.YFF_PAYMONEY();	//缴费信息
		public YFFDef.YFF_DBBD[] 	bd		= new YFFDef.YFF_DBBD[YFF_MPPAY_METERNUM];		//表底信息
		
		//20120516
		public double	jt_total_zbdl	= 0;				//阶梯追补电量
		
		{
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			mpid			= 0;
			yffalarmid		= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			
			paytype			= 0;
			
			opresult		= 0;
			buynum			= 0;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, null);
			YFFDef.YFF_PASSRAND.setYFF_PASSRAND(passrand, null);
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, null);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
			jt_total_zbdl   = 0;
		}
		
		public void clone(MSG_DYOPER_ADDRES msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			mpid			= msg.mpid;
			yffalarmid		= msg.yffalarmid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			paytype			= msg.paytype;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, msg.bz);
			YFFDef.YFF_PASSRAND.setYFF_PASSRAND(passrand, msg.passrand);
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, msg.money);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
			opresult		= msg.opresult;
			buynum			= msg.buynum;
			jt_total_zbdl	= msg.jt_total_zbdl;
		}
		
		public MSG_DYOPER_ADDRES clone() {
			MSG_DYOPER_ADDRES ret_msg = new MSG_DYOPER_ADDRES();
			ret_msg.clone(this);
			
			return ret_msg;
		}
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, yffalarmid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, paytype);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			ret_len += ComntStream.writeStream(byte_vect, buynum);
	
			ret_len += YFFDef.YFF_PAYBZ.writeStream(byte_vect, bz);
			ret_len += YFFDef.YFF_PASSRAND.writeStream(byte_vect, passrand);
			
			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, money);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len += YFFDef.YFF_DBBD.writeStream(byte_vect, bd[i]);
			}
			ret_len += ComntStream.writeStream(byte_vect, jt_total_zbdl);
			
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid); 
			ret_len += ComntStream.getDataSize(mpid);

			yffalarmid = ComntStream.readStream(byte_vect, offset + ret_len, yffalarmid); 
			ret_len += ComntStream.getDataSize(yffalarmid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			paytype = ComntStream.readStream(byte_vect, offset + ret_len, paytype); 
			ret_len += ComntStream.getDataSize(paytype);
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			buynum = ComntStream.readStream(byte_vect, offset + ret_len, buynum); 
			ret_len += ComntStream.getDataSize(buynum);
			
			ret_len = YFFDef.YFF_PAYBZ.getDataSize(byte_vect, offset, ret_len, bz);
			ret_len = YFFDef.YFF_PASSRAND.getDataSize(byte_vect, offset, ret_len, passrand);
			
			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, money);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			
			jt_total_zbdl = ComntStream.readStream(byte_vect, offset + ret_len, jt_total_zbdl); 
			ret_len += ComntStream.getDataSize(jt_total_zbdl);
			
			return ret_len;
		}		
	}
	
	//低压操作-缴费
	public static class MSG_DYOPER_PAY implements IMSG_READWRITESTREAM {
		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	mpid			= 0;				//点号

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员
		public byte		paytype			= 0;					//缴费方式

		public byte		opresult		= 0;
		public int		buynum			= 0;					//购电次数
		
		//20140606 zhp 新增 
		public YFFDef.YFF_PAYBZ		bz		 = new YFFDef.YFF_PAYBZ();		//缴费表字		
		public YFFDef.YFF_PASSRAND  passrand = new YFFDef.YFF_PASSRAND();	//随机密码

		public YFFDef.YFF_PAYMONEY 	money	= new YFFDef.YFF_PAYMONEY();	//缴费信息
		public YFFDef.YFF_DBBD[] 	bd		= new YFFDef.YFF_DBBD[YFF_MPPAY_METERNUM];		//表底信息
		{
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			mpid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			
			paytype			= 0;
			
			opresult		= 0;
			buynum			= 0;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, null);
			YFFDef.YFF_PASSRAND.setYFF_PASSRAND(passrand, null);
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, null);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
				
			}
		}
		
		public void clone(MSG_DYOPER_PAY msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			mpid			= msg.mpid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			paytype			= msg.paytype;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, msg.bz);
			YFFDef.YFF_PASSRAND.setYFF_PASSRAND(passrand, msg.passrand);
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, msg.money);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
			opresult		= msg.opresult;
			buynum			= msg.buynum;
		}
		
		public MSG_DYOPER_PAY clone() {
			MSG_DYOPER_PAY ret_msg = new MSG_DYOPER_PAY();
			ret_msg.clone(this);
			
			return ret_msg;
		}
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, paytype);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			ret_len += ComntStream.writeStream(byte_vect, buynum);
	
			ret_len += YFFDef.YFF_PAYBZ.writeStream(byte_vect, bz);
			ret_len += YFFDef.YFF_PASSRAND.writeStream(byte_vect, passrand);
			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, money);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len += YFFDef.YFF_DBBD.writeStream(byte_vect, bd[i]);
			}

			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid); 
			ret_len += ComntStream.getDataSize(mpid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			paytype = ComntStream.readStream(byte_vect, offset + ret_len, paytype); 
			ret_len += ComntStream.getDataSize(paytype);
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			buynum = ComntStream.readStream(byte_vect, offset + ret_len, buynum); 
			ret_len += ComntStream.getDataSize(buynum);
			
			ret_len = YFFDef.YFF_PAYBZ.getDataSize(byte_vect, offset, ret_len, bz);
			ret_len = YFFDef.YFF_PASSRAND.getDataSize(byte_vect, offset, ret_len, passrand);
			
			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, money);

			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			return ret_len;
		}
	}
	
	//低压操作-补卡
	public static class MSG_DYOPER_REPAIR implements IMSG_READWRITESTREAM {
		public byte	 testf		= 0;						//测试标志
		public byte	 gsmf		= 0;						//是否发送缴费短信
		public short mpid		= 0;						//点号

		public byte	operman[] 	= new byte[MSG_STR_LEN_64];	//操作员
		public byte	opresult	= 0;

		public int	old_op_date	= 0;
		public int	old_op_time = 0;
		public YFFDef.YFF_PASSRAND  passrand = new YFFDef.YFF_PASSRAND();	//随机密码
		
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			mpid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			opresult		= 0;
			old_op_date		= 0;
			old_op_time 	= 0;
			
			YFFDef.YFF_PASSRAND.setYFF_PASSRAND(passrand, null);
		}
		
		public void clone(MSG_DYOPER_REPAIR msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			mpid			= msg.mpid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			opresult		= msg.opresult;
			old_op_date		= msg.old_op_date;
			old_op_time		= msg.old_op_time;
			
			YFFDef.YFF_PASSRAND.setYFF_PASSRAND(passrand, msg.passrand);	
		}
		
		public MSG_DYOPER_REPAIR clone() {
			MSG_DYOPER_REPAIR ret_msg = new MSG_DYOPER_REPAIR();
			ret_msg.clone(this);
			
			return ret_msg;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			ret_len += ComntStream.writeStream(byte_vect, old_op_date);
			ret_len += ComntStream.writeStream(byte_vect, old_op_time);
			ret_len += YFFDef.YFF_PASSRAND.writeStream(byte_vect, passrand);
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid);
			ret_len += ComntStream.getDataSize(mpid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			old_op_date = ComntStream.readStream(byte_vect, offset + ret_len, old_op_date); 
			ret_len += ComntStream.getDataSize(old_op_date);
			
			old_op_time = ComntStream.readStream(byte_vect, offset + ret_len, old_op_time); 
			ret_len += ComntStream.getDataSize(old_op_time);
			
			ret_len = YFFDef.YFF_PASSRAND.getDataSize(byte_vect, offset, ret_len, passrand);
			return ret_len;
		}
		
	}
	
	//低压操作-补写卡
	public static class MSG_DYOPER_REWRITE implements IMSG_READWRITESTREAM {

		public byte	testf		= 0;						//测试标志
		public byte	gsmf		= 0;						//是否发送缴费短信
		public short	mpid	= 0;						//点号

		public byte	operman[] 	= new byte[MSG_STR_LEN_64];	//操作员
		public byte	opresult	= 0;

		public int	old_op_date	= 0;
		public int	old_op_time = 0;

		public void clean() {
			testf			= 0;
			gsmf			= 0;
			mpid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			opresult		= 0;
			old_op_date		= 0;
			old_op_time 	= 0;
		}
		
		public void clone(MSG_DYOPER_REWRITE msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			mpid			= msg.mpid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			opresult		= msg.opresult;
			old_op_date		= msg.old_op_date;
			old_op_time		= msg.old_op_time;
		}
		
		public MSG_DYOPER_REWRITE clone() {
			MSG_DYOPER_REWRITE ret_msg = new MSG_DYOPER_REWRITE();
			ret_msg.clone(this);
			
			return ret_msg;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			ret_len += ComntStream.writeStream(byte_vect, old_op_date);
			ret_len += ComntStream.writeStream(byte_vect, old_op_time);
	
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid);
			ret_len += ComntStream.getDataSize(mpid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			old_op_date = ComntStream.readStream(byte_vect, offset + ret_len, old_op_date); 
			ret_len += ComntStream.getDataSize(old_op_date);
			
			old_op_time = ComntStream.readStream(byte_vect, offset + ret_len, old_op_time); 
			ret_len += ComntStream.getDataSize(old_op_time);
			
			return ret_len;
		}
		
	
	}
	
	//低压操作-冲正
	public static class MSG_DYOPER_REVER implements IMSG_READWRITESTREAM {

		public byte	testf		= 0;						//测试标志
		public byte	gsmf		= 0;						//是否发送缴费短信
		public short	mpid	= 0;						//点号

		public byte	operman[] 	= new byte[MSG_STR_LEN_64];	//操作员
		public byte	paytype		= 0;
		public byte	opresult	= 0;

		public int	old_op_date	= 0;
		public int	old_op_time = 0;
		
		//20140606 zhp 新增 
		public YFFDef.YFF_PAYBZ		bz		 = new YFFDef.YFF_PAYBZ();		//缴费表字		
		public YFFDef.YFF_PASSRAND  passrand = new YFFDef.YFF_PASSRAND();	//随机密码
		
		public YFFDef.YFF_PAYMONEY 	newmoney	= new YFFDef.YFF_PAYMONEY();	//新缴费信息
		
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			mpid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			paytype			= 0;
			opresult		= 0;
			old_op_date		= 0;
			old_op_time 	= 0;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, null);
			YFFDef.YFF_PASSRAND.setYFF_PASSRAND(passrand, null);
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(newmoney, null);
		}
		
		public void clone(MSG_DYOPER_REVER msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			mpid			= msg.mpid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			paytype			= msg.paytype;
			opresult		= msg.opresult;
			old_op_date		= msg.old_op_date;
			old_op_time		= msg.old_op_time;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, msg.bz);
			YFFDef.YFF_PASSRAND.setYFF_PASSRAND(passrand, msg.passrand);
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(newmoney, msg.newmoney);
		}
		
		public MSG_DYOPER_REVER clone() {
			MSG_DYOPER_REVER ret_msg = new MSG_DYOPER_REVER();
			ret_msg.clone(this);
			
			return ret_msg;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, paytype);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			ret_len += ComntStream.writeStream(byte_vect, old_op_date);
			ret_len += ComntStream.writeStream(byte_vect, old_op_time);
			
			ret_len += YFFDef.YFF_PAYBZ.writeStream(byte_vect, bz);
			ret_len += YFFDef.YFF_PASSRAND.writeStream(byte_vect, passrand);
			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, newmoney);
			
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid);
			ret_len += ComntStream.getDataSize(mpid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			paytype = ComntStream.readStream(byte_vect, offset + ret_len, paytype); 
			ret_len += ComntStream.getDataSize(paytype);
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			old_op_date = ComntStream.readStream(byte_vect, offset + ret_len, old_op_date); 
			ret_len += ComntStream.getDataSize(old_op_date);
			
			old_op_time = ComntStream.readStream(byte_vect, offset + ret_len, old_op_time); 
			ret_len += ComntStream.getDataSize(old_op_time);
			
			ret_len = YFFDef.YFF_PAYBZ.getDataSize(byte_vect, offset, ret_len, bz);
			ret_len = YFFDef.YFF_PASSRAND.getDataSize(byte_vect, offset, ret_len, passrand);
			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, newmoney);
			
			return ret_len;
		}
	}
	
	//低压操作-换表换倍率
	public static class MSG_DYOPER_CHANGEMETER implements IMSG_READWRITESTREAM {
		public byte				testf 		= 0;								//测试标志
		public byte				gsmf 		= 0;								//是否发送缴费短信
		public short			mpid 		= 0;								//点号

		public byte				operman[] 	= new byte[MSG_USERNAME_LEN];		//操作员
		public byte				opresult 	= 0;
		public int				buynum 		= 0;
		
		//20140606 zhp 新增 
		public YFFDef.YFF_PAYBZ		bz		 = new YFFDef.YFF_PAYBZ();		//缴费表字		
		public YFFDef.YFF_PASSRAND  passrand = new YFFDef.YFF_PASSRAND();	//随机密码

		public YFFDef.YFF_DBBD			oldbd[] 	= new YFFDef.YFF_DBBD[YFF_MPPAY_METERNUM];	//旧表底信息
		public YFFDef.YFF_DBBD			newbd[] 	= new YFFDef.YFF_DBBD[YFF_MPPAY_METERNUM];	//新表底信息
		{
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				oldbd[i] = new YFFDef.YFF_DBBD();
				newbd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public YFFDef.YFF_PAYMONEY		money		= new YFFDef.YFF_PAYMONEY();				//缴费信息--用于表计费时,写卡或远程设置的值
		public YFFDef.YFF_CHGMETERRATE	chgmterrate = new YFFDef.YFF_CHGMETERRATE();			//换表信息
		
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			mpid			= 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			opresult		= 0;
			buynum			= 0;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, null);
			YFFDef.YFF_PASSRAND.setYFF_PASSRAND(passrand, null);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(oldbd[i], null);
				YFFDef.YFF_DBBD.setYFF_DBBD(newbd[i], null);
			}
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, null);
			YFFDef.YFF_CHGMETERRATE.setYFF_CHGMETERRATE(chgmterrate, null);
		}
		
		public void clone(MSG_DYOPER_CHANGEMETER msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			mpid			= msg.mpid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			opresult		= msg.opresult;
			buynum			= msg.buynum;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, msg.bz);
			YFFDef.YFF_PASSRAND.setYFF_PASSRAND(passrand, msg.passrand);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(oldbd[i], msg.oldbd[i]);
				YFFDef.YFF_DBBD.setYFF_DBBD(newbd[i], msg.newbd[i]);
			}

			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, msg.money);
			YFFDef.YFF_CHGMETERRATE.setYFF_CHGMETERRATE(chgmterrate, msg.chgmterrate);
		}
		
		public MSG_DYOPER_CHANGEMETER clone() {
			MSG_DYOPER_CHANGEMETER ret_msg = new MSG_DYOPER_CHANGEMETER();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			ret_len += ComntStream.writeStream(byte_vect, buynum);
	
			ret_len += YFFDef.YFF_PAYBZ.writeStream(byte_vect, bz);
			ret_len += YFFDef.YFF_PASSRAND.writeStream(byte_vect, passrand);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len += YFFDef.YFF_DBBD.writeStream(byte_vect, oldbd[i]);
			}
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len += YFFDef.YFF_DBBD.writeStream(byte_vect, newbd[i]);
			}
			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, money);
			
			ret_len += YFFDef.YFF_CHGMETERRATE.writeStream(byte_vect, chgmterrate);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid); 
			ret_len += ComntStream.getDataSize(mpid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			buynum = ComntStream.readStream(byte_vect, offset + ret_len, buynum); 
			ret_len += ComntStream.getDataSize(buynum);

			ret_len = YFFDef.YFF_PAYBZ.getDataSize(byte_vect, offset, ret_len, bz);
			ret_len = YFFDef.YFF_PASSRAND.getDataSize(byte_vect, offset, ret_len, passrand);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, oldbd[i]);
			}
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, newbd[i]);
			}

			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, money);
			
			ret_len = YFFDef.YFF_CHGMETERRATE.getDataSize(byte_vect, offset, ret_len, chgmterrate);
			
			return ret_len;
		}
	}
	
	//低压操作-换电价
	public static class MSG_DYOPER_CHANGERATE implements IMSG_READWRITESTREAM {
		public byte				testf 		= 0;								//测试标志
		public byte				gsmf 		= 0;								//是否发送缴费短信
		public short			mpid 		= 0;								//点号

		public byte				operman[] 	= new byte[MSG_USERNAME_LEN];		//操作员
		public byte				opresult 	= 0;
		public byte				chg_type	= 0;								//类型: 0-自动更改 1-手动更改
		public short			fee_chgid 	= 0;								//费率更改ID
		public int				fee_chgdate = 0;								//费率更改日期
		public int				fee_chgtime = 0;								//费率更改时间
		
		public YFFDef.YFF_DBBD			bd[] 	= new YFFDef.YFF_DBBD[YFF_MPPAY_METERNUM];	//表底信息
		{
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			mpid			= 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			opresult		= 0;
			chg_type		= 0;
			fee_chgid 	= 0;
			fee_chgdate = 0;
			fee_chgtime = 0;
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
		}
		
		public void clone(MSG_DYOPER_CHANGERATE msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			mpid			= msg.mpid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			opresult		= msg.opresult;
			chg_type		= msg.chg_type;
			fee_chgid		= msg.fee_chgid;
			fee_chgdate		= msg.fee_chgdate;
			fee_chgtime		= msg.fee_chgtime;
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
		}
		
		public MSG_DYOPER_CHANGERATE clone() {
			MSG_DYOPER_CHANGERATE ret_msg = new MSG_DYOPER_CHANGERATE();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			ret_len += ComntStream.writeStream(byte_vect, chg_type);
			ret_len += ComntStream.writeStream(byte_vect, fee_chgid);
			ret_len += ComntStream.writeStream(byte_vect, fee_chgdate);
			ret_len += ComntStream.writeStream(byte_vect, fee_chgtime);
	
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len += YFFDef.YFF_DBBD.writeStream(byte_vect, bd[i]);
			}
			

			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid); 
			ret_len += ComntStream.getDataSize(mpid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			chg_type = ComntStream.readStream(byte_vect, offset + ret_len, chg_type); 
			ret_len += ComntStream.getDataSize(chg_type);
			
			fee_chgid = ComntStream.readStream(byte_vect, offset + ret_len, fee_chgid); 
			ret_len += ComntStream.getDataSize(fee_chgid);
			
			fee_chgdate = ComntStream.readStream(byte_vect, offset + ret_len, fee_chgdate); 
			ret_len += ComntStream.getDataSize(fee_chgdate);
			
			fee_chgtime = ComntStream.readStream(byte_vect, offset + ret_len, fee_chgtime); 
			ret_len += ComntStream.getDataSize(fee_chgtime);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			
			return ret_len;
		}
	}
	
	//低压操作-销户
	public static class MSG_DYOPER_DESTORY implements IMSG_READWRITESTREAM {
		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	mpid			= 0;				//点号

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员

		public byte		opresult		= 0;

		//20140606 zhp 新增 
		public YFFDef.YFF_PAYBZ		bz		 = new YFFDef.YFF_PAYBZ();		//缴费表字		
		public YFFDef.YFF_PASSRAND  passrand = new YFFDef.YFF_PASSRAND();	//随机密码
		
		public YFFDef.YFF_PAYMONEY 	money	= new YFFDef.YFF_PAYMONEY();	//缴费信息
		public YFFDef.YFF_DBBD[] 	bd		= new YFFDef.YFF_DBBD[YFF_MPPAY_METERNUM];		//表底信息
		{
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			mpid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			opresult		= 0;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, null);
			YFFDef.YFF_PASSRAND.setYFF_PASSRAND(passrand, null);
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, null);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
		}
		public void clone(MSG_DYOPER_DESTORY msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			mpid			= msg.mpid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			opresult		= msg.opresult;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, msg.bz);
			YFFDef.YFF_PASSRAND.setYFF_PASSRAND(passrand, msg.passrand);	
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, msg.money);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
		}
		
		public MSG_DYOPER_DESTORY clone() {
			MSG_DYOPER_DESTORY ret_msg = new MSG_DYOPER_DESTORY();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
	
			ret_len += YFFDef.YFF_PAYBZ.writeStream(byte_vect, bz);
			ret_len += YFFDef.YFF_PASSRAND.writeStream(byte_vect, passrand);
			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, money);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len += YFFDef.YFF_DBBD.writeStream(byte_vect, bd[i]);
			}

			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid); 
			ret_len += ComntStream.getDataSize(mpid);

			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			ret_len = YFFDef.YFF_PAYBZ.getDataSize(byte_vect, offset, ret_len, bz);
			ret_len = YFFDef.YFF_PASSRAND.getDataSize(byte_vect, offset, ret_len, passrand);
			
			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, money);

			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			return ret_len;
		}
	}
	
	//低压操作-暂停
	public static class MSG_DYOPER_PAUSE implements IMSG_READWRITESTREAM {
		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	mpid			= 0;				//点号

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员

		public byte		opresult		= 0;

		public YFFDef.YFF_PAYMONEY 	money	= new YFFDef.YFF_PAYMONEY();	//缴费信息
		public YFFDef.YFF_DBBD[] 	bd		= new YFFDef.YFF_DBBD[YFF_MPPAY_METERNUM];		//表底信息
		{
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			mpid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			opresult		= 0;
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, null);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
		}
		
		public void clone(MSG_DYOPER_PAUSE msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			mpid			= msg.mpid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			opresult		= msg.opresult;
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, msg.money);
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
		}
		
		public MSG_DYOPER_PAUSE clone() {
			MSG_DYOPER_PAUSE ret_msg = new MSG_DYOPER_PAUSE();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
	
			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, money);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len += YFFDef.YFF_DBBD.writeStream(byte_vect, bd[i]);
			}

			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid); 
			ret_len += ComntStream.getDataSize(mpid);

			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, money);

			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			return ret_len;
		}		
	}
	
	//低压操作-恢复
	public static class MSG_DYOPER_RESTART implements IMSG_READWRITESTREAM {
		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	mpid			= 0;				//点号
		public short	myffalarmid		= 0;				//报警方案

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员
		public byte		paytype			= 0;					//缴费方式

		public byte		opresult		= 0;
		public int		buynum			= 0;					//购电次数

		public YFFDef.YFF_PAYMONEY 	money	= new YFFDef.YFF_PAYMONEY();	//缴费信息
		public YFFDef.YFF_DBBD[] 	bd		= new YFFDef.YFF_DBBD[YFF_MPPAY_METERNUM];		//表底信息
		
		//20120516
		public double	jt_total_zbdl	= 0;					//阶梯追补电量
		{
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			mpid			= 0;
			myffalarmid		= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			
			paytype			= 0;
			
			opresult		= 0;
			buynum			= 0;
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, null);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
			
			jt_total_zbdl	= 0;
		}
		
		public void clone(MSG_DYOPER_RESTART msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			mpid			= msg.mpid;
			myffalarmid		= msg.myffalarmid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			paytype			= msg.paytype;
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, msg.money);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
			opresult		= msg.opresult;
			buynum			= msg.buynum;
			jt_total_zbdl	= msg.jt_total_zbdl;
		}
		
		public MSG_DYOPER_RESTART clone() {
			MSG_DYOPER_RESTART ret_msg = new MSG_DYOPER_RESTART();
			ret_msg.clone(this);
			
			return ret_msg;
		}
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, myffalarmid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, paytype);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			ret_len += ComntStream.writeStream(byte_vect, buynum);
	
			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, money);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len += YFFDef.YFF_DBBD.writeStream(byte_vect, bd[i]);
			}
			ret_len += ComntStream.writeStream(byte_vect, jt_total_zbdl);
			
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid); 
			ret_len += ComntStream.getDataSize(mpid);

			myffalarmid = ComntStream.readStream(byte_vect, offset + ret_len, myffalarmid); 
			ret_len += ComntStream.getDataSize(myffalarmid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			paytype = ComntStream.readStream(byte_vect, offset + ret_len, paytype); 
			ret_len += ComntStream.getDataSize(paytype);
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			buynum = ComntStream.readStream(byte_vect, offset + ret_len, buynum); 
			ret_len += ComntStream.getDataSize(buynum);
			
			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, money);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			
			jt_total_zbdl = ComntStream.readStream(byte_vect, offset + ret_len, jt_total_zbdl); 
			ret_len += ComntStream.getDataSize(jt_total_zbdl);
			
			return ret_len;
		}
	}
	
	//低压操作-保电
	public static class MSG_DYOPER_PROTECT implements IMSG_READWRITESTREAM {
		public byte	testf		= 0;						//测试标志
		public byte	gsmf		= 0;						//是否发送缴费短信
		public short	mpid	= 0;						//点号

		public byte	operman[] 	= new byte[MSG_STR_LEN_64];	//操作员

		public short	m_prot_st	= 0;
		public short	m_prot_ed	= 0;

		public void clean() {
			testf			= 0;
			gsmf			= 0;
			mpid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			m_prot_st		= 0;
			m_prot_ed 	= 0;
		}
		
		public void clone(MSG_DYOPER_PROTECT msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			mpid			= msg.mpid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			m_prot_st		= msg.m_prot_st;
			m_prot_ed		= msg.m_prot_ed;
		}
		
		public MSG_DYOPER_PROTECT clone() {
			MSG_DYOPER_PROTECT ret_msg = new MSG_DYOPER_PROTECT();
			ret_msg.clone(this);
			
			return ret_msg;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, m_prot_st);
			ret_len += ComntStream.writeStream(byte_vect, m_prot_ed);
	
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid);
			ret_len += ComntStream.getDataSize(mpid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			m_prot_st = ComntStream.readStream(byte_vect, offset + ret_len, m_prot_st); 
			ret_len += ComntStream.getDataSize(m_prot_st);
			
			m_prot_ed = ComntStream.readStream(byte_vect, offset + ret_len, m_prot_ed); 
			ret_len += ComntStream.getDataSize(m_prot_ed);
			
			return ret_len;
		}
		
	}
	
	//低压操作-返回余额
	public static class MSG_DYOPER_GETREMAIN implements IMSG_READWRITESTREAM {

		public byte				testf 		= 0;								//测试标志
		public byte				gsmf 		= 0;								//是否发送缴费短信
		public short			mpid 		= 0;								//点号

		public byte				operman[] 	= new byte[MSG_USERNAME_LEN];		//操作员
		
		public byte				xhflag		= 0;								//销户标志
		
		public YFFDef.YFF_DBBD			bd[] 	= new YFFDef.YFF_DBBD[YFF_MPPAY_METERNUM];	//表底信息
		{
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			mpid			= 0;
			xhflag			= 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
			
		}
		
		public void clone(MSG_DYOPER_GETREMAIN msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			mpid			= msg.mpid;
			xhflag			= msg.xhflag;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
		}
		
		public MSG_DYOPER_GETREMAIN clone() {
			MSG_DYOPER_GETREMAIN ret_msg = new MSG_DYOPER_GETREMAIN();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, xhflag);	
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len += YFFDef.YFF_DBBD.writeStream(byte_vect, bd[i]);
			}
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid); 
			ret_len += ComntStream.getDataSize(mpid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			xhflag = ComntStream.readStream(byte_vect, offset + ret_len, xhflag); 
			ret_len += ComntStream.getDataSize(xhflag);			
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			
			return ret_len;
		}
	
	}
	
	//低压操作-重新计算剩余金额
	public static class MSG_DYOPER_RECALC implements IMSG_READWRITESTREAM {

		public byte				testf 		= 0;								//测试标志
		public byte				gsmf 		= 0;								//是否发送缴费短信
		public short			mpid 		= 0;								//点号

		public byte				operman[] 	= new byte[MSG_USERNAME_LEN];		//操作员
		
		public YFFDef.YFF_DBBD			bd[] 	= new YFFDef.YFF_DBBD[YFF_MPPAY_METERNUM];	//表底信息
		{
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			mpid			= 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
			
		}
		
		public void clone(MSG_DYOPER_RECALC msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			mpid			= msg.mpid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
		}
		
		public MSG_DYOPER_RECALC clone() {
			MSG_DYOPER_RECALC ret_msg = new MSG_DYOPER_RECALC();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
	
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len += YFFDef.YFF_DBBD.writeStream(byte_vect, bd[i]);
			}
			

			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid); 
			ret_len += ComntStream.getDataSize(mpid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			
			return ret_len;
		}
	}
	
	//低压操作-结算补差
	public static class MSG_DYOPER_JSBC implements IMSG_READWRITESTREAM {

		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	mpid			= 0;				//点号

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员
		public byte		paytype			= 0;				//缴费方式
		public byte		opresult		= 0;

		public int		buynum			= 0;				//购电次数
		
		public YFFDef.YFF_PAYMONEY 	money	= new YFFDef.YFF_PAYMONEY();	//缴费信息
		public YFFDef.YFF_DBBD[] 	bd		= new YFFDef.YFF_DBBD[YFF_MPPAY_METERNUM];		//表底信息
		{
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			mpid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			paytype			= 0;
			opresult		= 0;
			
			buynum			= 0;
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, null);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
		}
		
		public void clone(MSG_DYOPER_JSBC msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			mpid			= msg.mpid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			paytype			= msg.paytype;
			opresult		= msg.opresult;
			
			buynum			= msg.buynum;
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, msg.money);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
		}
		
		public MSG_DYOPER_JSBC clone() {
			MSG_DYOPER_JSBC ret_msg = new MSG_DYOPER_JSBC();
			ret_msg.clone(this);
			
			return ret_msg;
		}
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, paytype);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
	
			ret_len += ComntStream.writeStream(byte_vect, buynum);
			
			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, money);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len += YFFDef.YFF_DBBD.writeStream(byte_vect, bd[i]);
			}

			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid); 
			ret_len += ComntStream.getDataSize(mpid);

			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			paytype = ComntStream.readStream(byte_vect, offset + ret_len, paytype); 
			ret_len += ComntStream.getDataSize(paytype);
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			buynum = ComntStream.readStream(byte_vect, offset + ret_len, buynum); 
			ret_len += ComntStream.getDataSize(buynum); 
			
			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, money);

			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			return ret_len;
		}		
	
	}
	
	//低压操作-发行电费
	public static class MSG_DYOPER_REFXDF implements IMSG_READWRITESTREAM {

		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	mpid			= 0;				//点号

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员
		public byte		opresult		= 0;

		public YFFDef.YFF_DBBD[] 	bd		= new YFFDef.YFF_DBBD[YFF_MPPAY_METERNUM];		//表底信息
		{
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			mpid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			opresult		= 0;
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
		}
		
		public void clone(MSG_DYOPER_REFXDF msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			mpid			= msg.mpid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			opresult		= msg.opresult;
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
		}
		
		public MSG_DYOPER_REFXDF clone() {
			MSG_DYOPER_REFXDF ret_msg = new MSG_DYOPER_REFXDF();
			ret_msg.clone(this);
			
			return ret_msg;
		}
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
	
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len += YFFDef.YFF_DBBD.writeStream(byte_vect, bd[i]);
			}

			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid); 
			ret_len += ComntStream.getDataSize(mpid);

			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			return ret_len;
		}		
	}
	
	//低压操作-重新阶梯清零
	public static class MSG_DYOPER_REJTRESET implements IMSG_READWRITESTREAM {

		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	mpid			= 0;				//点号

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员
		public byte		opresult		= 0;

		public YFFDef.YFF_DBBD[] 	bd		= new YFFDef.YFF_DBBD[YFF_MPPAY_METERNUM];		//表底信息
		{
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			mpid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			opresult		= 0;
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
		}
		
		public void clone(MSG_DYOPER_REJTRESET msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			mpid			= msg.mpid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			opresult		= msg.opresult;
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
		}
		
		public MSG_DYOPER_REJTRESET clone() {
			MSG_DYOPER_REJTRESET ret_msg = new MSG_DYOPER_REJTRESET();
			ret_msg.clone(this);
			
			return ret_msg;
		}
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
	
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len += YFFDef.YFF_DBBD.writeStream(byte_vect, bd[i]);
			}

			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid); 
			ret_len += ComntStream.getDataSize(mpid);

			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			for (int i = 0; i < YFF_MPPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			return ret_len;
		}		
	
	}	

	//低压操作-更改参数  预付费参数  20130201
	public static class MSG_DYOPER_RESETDOC implements IMSG_READWRITESTREAM {

		public byte		testf		= 0;				//测试标志
		public byte		gsmf		= 0;				//是否发送缴费短信
		public short	mpid		= 0;				//点号
		public short	resid		= 0;				//居民id
		
		public byte[]	operman		= new byte[MSG_STR_LEN_64];	//操作员
		public byte		chgtype		= 0;				//类型   //YFF_DY_CHGPARATYPE_MOBILE1	居民手机号码 filed1有效

		public byte[]	filed1		= new byte[MSG_STR_LEN_64];	//字段1
		public byte[]	filed2		= new byte[MSG_STR_LEN_64];	//字段2
		public byte[]	filed3		= new byte[MSG_STR_LEN_64];	//字段3
		public byte[]	filed4		= new byte[MSG_STR_LEN_64];	//字段4
		
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			mpid			= 0;
			resid			= 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			chgtype			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) filed1[i] = 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) filed2[i] = 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) filed3[i] = 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) filed4[i] = 0;
		}
		
		public void clone(MSG_DYOPER_RESETDOC msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			mpid			= msg.mpid;
			resid			= msg.resid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			chgtype		= msg.chgtype;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				filed1[i] = msg.filed1[i];
				filed2[i] = msg.filed2[i];
				filed3[i] = msg.filed3[i];
				filed4[i] = msg.filed4[i];
			}
		}
		
		public MSG_DYOPER_RESETDOC clone() {
			MSG_DYOPER_RESETDOC ret_msg = new MSG_DYOPER_RESETDOC();
			ret_msg.clone(this);
			
			return ret_msg;
		}
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, resid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, chgtype);
			
			ret_len += ComntStream.writeStream(byte_vect, filed1, 0, filed1.length);
			ret_len += ComntStream.writeStream(byte_vect, filed2, 0, filed2.length);
			ret_len += ComntStream.writeStream(byte_vect, filed3, 0, filed3.length);
			ret_len += ComntStream.writeStream(byte_vect, filed4, 0, filed4.length);

			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid); 
			ret_len += ComntStream.getDataSize(mpid);
			
			resid = ComntStream.readStream(byte_vect, offset + ret_len, resid); 
			ret_len += ComntStream.getDataSize(resid);

			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			chgtype = ComntStream.readStream(byte_vect, offset + ret_len, chgtype); 
			ret_len += ComntStream.getDataSize(chgtype);
			
			ComntStream.readStream(byte_vect, offset + ret_len, filed1, 0, filed1.length);
			ret_len += ComntStream.getDataSize(filed1[0]) * filed1.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, filed2, 0, filed2.length);
			ret_len += ComntStream.getDataSize(filed2[0]) * filed2.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, filed3, 0, filed3.length);
			ret_len += ComntStream.getDataSize(filed3[0]) * filed3.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, filed4, 0, filed4.length);
			ret_len += ComntStream.getDataSize(filed4[0]) * filed4.length;
			
			return ret_len;
		}		
	
	}
	
	//低压操作-获得预付费参数及状态
	public static class MSG_DYOPER_GETPARASTATE implements IMSG_READWRITESTREAM {

		public byte				testf 		= 0;								//测试标志
		public byte				gsmf 		= 0;								//是否发送缴费短信
		public short			mpid 		= 0;								//点号

		public byte				operman[] 	= new byte[MSG_USERNAME_LEN];		//操作员

		public void clean() {
			testf			= 0;
			gsmf			= 0;
			mpid			= 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
		}
		
		public void clone(MSG_DYOPER_GETPARASTATE msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			mpid			= msg.mpid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
		}
		
		public MSG_DYOPER_GETPARASTATE clone() {
			MSG_DYOPER_GETPARASTATE ret_msg = new MSG_DYOPER_GETPARASTATE();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
	
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid); 
			ret_len += ComntStream.getDataSize(mpid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			return ret_len;
		}
	
	}
	
	//低压操作-强制更新
	public static class MSG_DYOPER_UPDATE implements IMSG_READWRITESTREAM {

		public byte		testf			= 0;						//测试标志
		public byte		gsmf			= 0;						//是否发送缴费短信
		public short	mpid			= 0;						//点号

		public byte		operman[] 		= new byte[MSG_STR_LEN_64];	//操作员

		public byte		paravalidf		= 0;

		public byte		state_op_validf	= 0;
		public byte		state_js_validf	= 0;
		public byte		state_bn_validf	= 0;
		public byte		state_cc_validf	= 0;
		public byte		state_cs_validf	= 0;
		public byte		state_yc_validf	= 0;
		public byte		state_ot_validf	= 0;
		
		//20120516
		public byte		state_jt_validf	= 0;
		public byte		state_df_validf = 0;
		
		public byte	    alarmvalidf		= 0;
		public byte	    commdatavalidf	= 0;

		public YFFDef.YFF_MPPAYPARA			para = new YFFDef.YFF_MPPAYPARA();
		public YFFDef.YFF_MPPAYSTATE		state = new YFFDef.YFF_MPPAYSTATE();
		public YFFDef.YFF_MPPAY_ALARMSTATE	alarm = new YFFDef.YFF_MPPAY_ALARMSTATE();
		public YFFDef.YFF_ALARM_COMMDATA	commdata = new YFFDef.YFF_ALARM_COMMDATA();
		
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			mpid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			paravalidf		= 0;
			state_op_validf 	= 0;
			state_js_validf	= 0;
			state_bn_validf	= 0;
			state_cc_validf	= 0;
			state_cs_validf	= 0;
			state_yc_validf	= 0;
			state_ot_validf	= 0;
			
			state_jt_validf	= 0;
			state_df_validf = 0;
			
			alarmvalidf		= 0;
			commdatavalidf	= 0;
			
			YFFDef.YFF_MPPAYPARA.setYFF_MPPAYPARA(para, null);
			YFFDef.YFF_MPPAYSTATE.setYFF_MPPAYSTATE(state, null);
			YFFDef.YFF_MPPAY_ALARMSTATE.setYFF_MPPAY_ALARMSTATE(alarm, null);
			YFFDef.YFF_ALARM_COMMDATA.setYFF_ALARM_COMMDATA(commdata, null);
			
		}
		
		public void clone(MSG_DYOPER_UPDATE msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			mpid			= msg.mpid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			paravalidf		= msg.paravalidf;
			state_op_validf	= msg.state_op_validf;
			state_js_validf	= msg.state_js_validf;
			state_bn_validf	= msg.state_bn_validf;
			state_cc_validf	= msg.state_cc_validf;
			state_cs_validf	= msg.state_cs_validf;
			state_yc_validf	= msg.state_yc_validf;
			state_ot_validf	= msg.state_ot_validf;
			
			state_jt_validf	= msg.state_jt_validf;
			state_df_validf = msg.state_df_validf;						
			
			alarmvalidf		= msg.alarmvalidf;
			commdatavalidf	= msg.commdatavalidf;
					
			YFFDef.YFF_MPPAYPARA.setYFF_MPPAYPARA(para, msg.para);
			YFFDef.YFF_MPPAYSTATE.setYFF_MPPAYSTATE(state, msg.state);
			YFFDef.YFF_MPPAY_ALARMSTATE.setYFF_MPPAY_ALARMSTATE(alarm, msg.alarm);
			YFFDef.YFF_ALARM_COMMDATA.setYFF_ALARM_COMMDATA(commdata, msg.commdata);
		}
		
		public MSG_DYOPER_UPDATE clone() {
			MSG_DYOPER_UPDATE ret_msg = new MSG_DYOPER_UPDATE();
			ret_msg.clone(this);
			
			return ret_msg;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, paravalidf);
			ret_len += ComntStream.writeStream(byte_vect, state_op_validf);
			ret_len += ComntStream.writeStream(byte_vect, state_js_validf);
			ret_len += ComntStream.writeStream(byte_vect, state_bn_validf);
			ret_len += ComntStream.writeStream(byte_vect, state_cc_validf);
			ret_len += ComntStream.writeStream(byte_vect, state_cs_validf);
			ret_len += ComntStream.writeStream(byte_vect, state_yc_validf);
			ret_len += ComntStream.writeStream(byte_vect, state_ot_validf);
			
			ret_len += ComntStream.writeStream(byte_vect, state_jt_validf);
			ret_len += ComntStream.writeStream(byte_vect, state_df_validf);
			
			ret_len += ComntStream.writeStream(byte_vect, alarmvalidf);
			ret_len += ComntStream.writeStream(byte_vect, commdatavalidf);	
			
			ret_len += YFFDef.YFF_MPPAYPARA.writeStream(byte_vect, para);
			ret_len += YFFDef.YFF_MPPAYSTATE.writeStream(byte_vect, state);
			ret_len += YFFDef.YFF_MPPAY_ALARMSTATE.writeStream(byte_vect, alarm);
			ret_len += YFFDef.YFF_ALARM_COMMDATA.writeStream(byte_vect, commdata);
			
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid);
			ret_len += ComntStream.getDataSize(mpid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			paravalidf = ComntStream.readStream(byte_vect, offset + ret_len, paravalidf); 
			ret_len += ComntStream.getDataSize(paravalidf);
			
			state_op_validf = ComntStream.readStream(byte_vect, offset + ret_len, state_op_validf); 
			ret_len += ComntStream.getDataSize(state_op_validf);
			
			state_js_validf = ComntStream.readStream(byte_vect, offset + ret_len, state_js_validf); 
			ret_len += ComntStream.getDataSize(state_js_validf);
			
			state_bn_validf = ComntStream.readStream(byte_vect, offset + ret_len, state_bn_validf); 
			ret_len += ComntStream.getDataSize(state_bn_validf);
			
			state_cc_validf = ComntStream.readStream(byte_vect, offset + ret_len, state_cc_validf); 
			ret_len += ComntStream.getDataSize(state_cc_validf);
			
			state_cs_validf = ComntStream.readStream(byte_vect, offset + ret_len, state_cs_validf); 
			ret_len += ComntStream.getDataSize(state_cs_validf);
			
			state_yc_validf = ComntStream.readStream(byte_vect, offset + ret_len, state_yc_validf); 
			ret_len += ComntStream.getDataSize(state_yc_validf);
			
			state_ot_validf = ComntStream.readStream(byte_vect, offset + ret_len, state_ot_validf); 
			ret_len += ComntStream.getDataSize(state_ot_validf);
			
			state_jt_validf = ComntStream.readStream(byte_vect, offset + ret_len, state_jt_validf); 
			ret_len += ComntStream.getDataSize(state_jt_validf);
			
			state_df_validf = ComntStream.readStream(byte_vect, offset + ret_len, state_df_validf); 
			ret_len += ComntStream.getDataSize(state_df_validf);
			
			alarmvalidf = ComntStream.readStream(byte_vect, offset + ret_len, alarmvalidf); 
			ret_len += ComntStream.getDataSize(alarmvalidf);
			
			commdatavalidf = ComntStream.readStream(byte_vect, offset + ret_len, commdatavalidf); 
			ret_len += ComntStream.getDataSize(commdatavalidf);			
			
			ret_len = YFFDef.YFF_MPPAYPARA.getDataSize(byte_vect, offset, ret_len, para);
			ret_len = YFFDef.YFF_MPPAYSTATE.getDataSize(byte_vect, offset, ret_len, state);
			ret_len = YFFDef.YFF_MPPAY_ALARMSTATE.getDataSize(byte_vect, offset, ret_len, alarm);
			ret_len = YFFDef.YFF_ALARM_COMMDATA.getDataSize(byte_vect, offset, ret_len, commdata);
			
			return ret_len;
		}
	}
	

	//------------------------通信部分-----------------------
	//低压通信-开户
	public static class MSG_DYCOMM_ADDRES implements IMSG_READWRITESTREAM {
		
		public short	mpid			= 0;				//点号
		public short	myffalarmid		= 0;				//报警方案

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员
		public int		buynum			= 0;					//购电次数

		public YFFDef.YFF_PAYMONEY 	money	= new YFFDef.YFF_PAYMONEY();	//缴费信息
		
		public void clean() {
			mpid			= 0;
			myffalarmid		= 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			buynum			= 0;
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, null);
		}
		
		public void clone(MSG_DYCOMM_ADDRES msg) {
			mpid			= msg.mpid;
			myffalarmid		= msg.myffalarmid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			buynum			= msg.buynum;
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, msg.money);
		}
		
		public MSG_DYCOMM_ADDRES clone() {
			MSG_DYCOMM_ADDRES ret_msg = new MSG_DYCOMM_ADDRES();
			ret_msg.clone(this);
			
			return ret_msg;
		}
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, myffalarmid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, buynum);
	
			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, money);
			
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid); 
			ret_len += ComntStream.getDataSize(mpid);

			myffalarmid = ComntStream.readStream(byte_vect, offset + ret_len, myffalarmid); 
			ret_len += ComntStream.getDataSize(myffalarmid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			buynum = ComntStream.readStream(byte_vect, offset + ret_len, buynum); 
			ret_len += ComntStream.getDataSize(buynum);
			
			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, money);

			return ret_len;
		}		
	}
	
	//低压通信-缴费
	public static class MSG_DYCOMM_PAY extends MSG_DYCOMM_ADDRES implements IMSG_READWRITESTREAM {
		
	}
	
	//20131019添加新规约增加远程表冲正
	//低压通信-冲正
	public static class MSG_DYCOMM_REVER extends MSG_DYCOMM_ADDRES implements IMSG_READWRITESTREAM {
		
	}

	//低压通信-预制金额(初始化)
	public static class MSG_DYCOMM_ININT extends MSG_DYCOMM_ADDRES implements IMSG_READWRITESTREAM {
		
	}
	//end
	
	
	//低压通信-召参数
	public static class MSG_DYCOMM_CALLPARA implements IMSG_READWRITESTREAM {

		public short	mpid		= 0;
		public byte[]	operman		= new byte[MSG_STR_LEN_64];
		public short	paratype	= 0;

		public ComntVector.ByteVector data_vect = new ComntVector.ByteVector();
		
		public void clean() {
			mpid		= 0;
			paratype	= 0;

			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = 0;
			}
			data_vect.clear();
		}
		
		public void clone(MSG_DYCOMM_CALLPARA msg) {
			mpid		= msg.mpid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			paratype	= msg.paratype;

			data_vect.clone(msg.data_vect);
		}
		
		public MSG_DYCOMM_CALLPARA clone() {
			MSG_DYCOMM_CALLPARA ret_msg = new MSG_DYCOMM_CALLPARA();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			int vect_size = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);	
			ret_len += ComntStream.writeStream(byte_vect, paratype);
			
			vect_size = data_vect.size();
			ret_len += ComntStream.writeStream(byte_vect, vect_size);
			ret_len += ComntStream.writeStream(byte_vect, data_vect.getaddr(), 0, vect_size); 
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			int vect_size = 0;
			
			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid); 
			ret_len += ComntStream.getDataSize(mpid);
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			paratype = ComntStream.readStream(byte_vect, offset + ret_len, paratype); 
			ret_len += ComntStream.getDataSize(paratype);
			
			
			vect_size = ComntStream.readStream(byte_vect, offset + ret_len, vect_size);
			ret_len += ComntStream.getDataSize(vect_size);
			
			data_vect.resize(vect_size);
			ComntStream.readStream(byte_vect, offset + ret_len, data_vect.getaddr(), 0, vect_size);
			ret_len += ComntStream.getDataSize((byte)1) * vect_size;
			
			return ret_len;
		}
	
	}
	
	//低压通信-设参数
	public static class MSG_DYCOMM_SETPARA implements IMSG_READWRITESTREAM {

		public short	mpid		= 0;
		public byte[]	operman		= new byte[MSG_STR_LEN_64];
		public short	paratype	= 0;

		public ComntVector.ByteVector data_vect = new ComntVector.ByteVector();
	
		
		public void clean() {
			mpid		= 0;
			paratype	= 0;

			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = 0;
			}
			data_vect.clear();
		}
		
		public void clone(MSG_DYCOMM_SETPARA msg) {
			mpid		= msg.mpid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			paratype	= msg.paratype;

			data_vect.clone(msg.data_vect);
		}
		
		public MSG_DYCOMM_SETPARA clone() {
			MSG_DYCOMM_SETPARA ret_msg = new MSG_DYCOMM_SETPARA();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			int vect_size = 0;

			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);		
			ret_len += ComntStream.writeStream(byte_vect, paratype);

			vect_size = data_vect.size();
			ret_len += ComntStream.writeStream(byte_vect, vect_size);
			ret_len += ComntStream.writeStream(byte_vect, data_vect.getaddr(), 0, vect_size); 
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			int vect_size = 0;
			
			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid); 
			ret_len += ComntStream.getDataSize(mpid);
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			paratype = ComntStream.readStream(byte_vect, offset + ret_len, paratype); 
			ret_len += ComntStream.getDataSize(paratype);
			
			
			vect_size = ComntStream.readStream(byte_vect, offset + ret_len, vect_size);
			ret_len += ComntStream.getDataSize(vect_size);
			
			data_vect.resize(vect_size);
			ComntStream.readStream(byte_vect, offset + ret_len, data_vect.getaddr(), 0, vect_size);
			ret_len += ComntStream.getDataSize((byte)1) * vect_size;
			
			return ret_len;
		}
	
	}
	
	//低压通信-控制
	public static class MSG_DYCOMM_CTRL implements IMSG_READWRITESTREAM {

		public short	mpid		= 0;
		public byte[]	operman		= new byte[MSG_STR_LEN_64];
		public byte		ctrlver		= 0;							//控制版本 自动合闸勾选为1，不勾为0。
		public short	paratype	= 0;

		public ComntVector.ByteVector data_vect = new ComntVector.ByteVector();
	
		
		public void clean() {
			mpid		= 0;
			ctrlver		= 0;
			paratype	= 0;

			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = 0;
			}
			data_vect.clear();
		}
		
		public void clone(MSG_DYCOMM_CTRL msg) {
			mpid		= msg.mpid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			paratype	= msg.paratype;
			ctrlver		= msg.ctrlver;
			data_vect.clone(msg.data_vect);
		}
		
		public MSG_DYCOMM_CTRL clone() {
			MSG_DYCOMM_CTRL ret_msg = new MSG_DYCOMM_CTRL();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			int vect_size = 0;

			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);		
			ret_len += ComntStream.writeStream(byte_vect, ctrlver);
			ret_len += ComntStream.writeStream(byte_vect, paratype);
			
			vect_size = data_vect.size();
			ret_len += ComntStream.writeStream(byte_vect, vect_size);
			ret_len += ComntStream.writeStream(byte_vect, data_vect.getaddr(), 0, vect_size); 
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			int vect_size = 0;
			
			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid); 
			ret_len += ComntStream.getDataSize(mpid);
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			ctrlver = ComntStream.readStream(byte_vect, offset + ret_len, ctrlver); 
			ret_len += ComntStream.getDataSize(ctrlver);
			paratype = ComntStream.readStream(byte_vect, offset + ret_len, paratype); 
			ret_len += ComntStream.getDataSize(paratype);
			
			vect_size = ComntStream.readStream(byte_vect, offset + ret_len, vect_size);
			ret_len += ComntStream.getDataSize(vect_size);
			
			data_vect.resize(vect_size);
			ComntStream.readStream(byte_vect, offset + ret_len, data_vect.getaddr(), 0, vect_size);
			ret_len += ComntStream.getDataSize((byte)1) * vect_size;
			
			return ret_len;
		}
	}

	//20131019添加	新规约增加清空 电量 需量 事项
	//低压通信-清空 电量 需量 事项
	public static class MSG_DYCOMM_CLEAR implements IMSG_READWRITESTREAM{
		public short	mpid 			= 0;							//点号
		public byte[]	operman			= new byte[MSG_STR_LEN_64];		//操作员
		public byte		ctrlver			= 0;							//控制版本
		public short	paratype		= 0;							//参数类型
		public int		clear_eve_type	= 0;							//清除数据的数据类型		//先高后低

		public ComntVector.ByteVector data_vect = new ComntVector.ByteVector();//参数
		
		public void clean(){
			mpid 			= 0;
			ctrlver 		= 0;
			paratype 		= 0;
			clear_eve_type 	= 0;
			
			for(int i=0; i < MSG_STR_LEN_64; i++){
				operman[i] = 0;
			}
		}
		
		public void clone(MSG_DYCOMM_CLEAR msg){
			mpid 			= 	msg.mpid;
			ctrlver 		= 	msg.ctrlver;
			paratype 		= 	msg.paratype;
			clear_eve_type 	= 	msg.clear_eve_type;
			
			for(int i=0; i < MSG_STR_LEN_64; i++){
				operman[i] = msg.operman[i];
			}
		}
		
		public MSG_DYCOMM_CLEAR clone() {
			MSG_DYCOMM_CLEAR ret_msg = new MSG_DYCOMM_CLEAR();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect){
			int ret_len   = 0;
			int vect_size = 0;

			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);		
			ret_len += ComntStream.writeStream(byte_vect, ctrlver);
			ret_len += ComntStream.writeStream(byte_vect, paratype);
			ret_len += ComntStream.writeStream(byte_vect, clear_eve_type);
			
			vect_size = data_vect.size();
			ret_len += ComntStream.writeStream(byte_vect, vect_size);
			ret_len += ComntStream.writeStream(byte_vect, data_vect.getaddr(), 0, vect_size); 
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			int vect_size = 0;
			
			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid); 
			ret_len += ComntStream.getDataSize(mpid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			ctrlver = ComntStream.readStream(byte_vect, offset + ret_len, ctrlver); 
			ret_len += ComntStream.getDataSize(ctrlver);
			
			paratype = ComntStream.readStream(byte_vect, offset + ret_len, paratype); 
			ret_len += ComntStream.getDataSize(paratype);
			
			clear_eve_type = ComntStream.readStream(byte_vect, offset + ret_len, clear_eve_type); 
			ret_len += ComntStream.getDataSize(clear_eve_type);
			
			vect_size = ComntStream.readStream(byte_vect, offset + ret_len, vect_size);
			ret_len += ComntStream.getDataSize(vect_size);
			
			data_vect.resize(vect_size);
			ComntStream.readStream(byte_vect, offset + ret_len, data_vect.getaddr(), 0, vect_size);
			ret_len += ComntStream.getDataSize((byte)1) * vect_size;
			
			return ret_len;
		}	
	}
	
	//低压通信-下装密钥
	public static class MSG_DYCOMM_CHGKEY implements IMSG_READWRITESTREAM {

		public short	mpid	= 0;						//点号

		public byte	operman[] 	= new byte[MSG_STR_LEN_64];	//操作员

		public int	keytype		= 0;
		public byte	clearkey	= 0;
		public byte	keyver		= 0;
		public byte	initflag	= 0;
		
		public void clean() {
			mpid			= 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			keytype		= 0;
			clearkey 	= 0;
			keyver	= 0;
			initflag	= 0;
		}
		
		public void clone(MSG_DYCOMM_CHGKEY msg) {
			mpid			= msg.mpid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			keytype		= msg.keytype;
			clearkey	= msg.clearkey;
			keyver	= msg.keyver;
			initflag	= msg.initflag;
		}
		
		public MSG_DYCOMM_CHGKEY clone() {
			MSG_DYCOMM_CHGKEY ret_msg = new MSG_DYCOMM_CHGKEY();
			ret_msg.clone(this);
			
			return ret_msg;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, keytype);
			ret_len += ComntStream.writeStream(byte_vect, clearkey);
			ret_len += ComntStream.writeStream(byte_vect, keyver);
			ret_len += ComntStream.writeStream(byte_vect, initflag);
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid);
			ret_len += ComntStream.getDataSize(mpid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			keytype = ComntStream.readStream(byte_vect, offset + ret_len, keytype); 
			ret_len += ComntStream.getDataSize(keytype);
			
			clearkey = ComntStream.readStream(byte_vect, offset + ret_len, clearkey); 
			ret_len += ComntStream.getDataSize(clearkey);
			
			keyver = ComntStream.readStream(byte_vect, offset + ret_len, keyver); 
			ret_len += ComntStream.getDataSize(keyver);
			
			initflag = ComntStream.readStream(byte_vect, offset + ret_len, initflag); 
			ret_len += ComntStream.getDataSize(initflag);
			
			return ret_len;
		}
	}
	
	//20131019	:	新版本改变下装密钥内容
	//低压通信-下装密钥
	public static class MSG_DYCOMM_CHGKEY2 implements IMSG_READWRITESTREAM {

		public short mpid	= 0;						//点号

		public byte	operman[] 	= new byte[MSG_STR_LEN_64];	//操作员
		
		public byte	keysum	= 0;						//密钥总条数
		public byte key_id	= 0;						//密钥编号
		public byte keynum	= 0;						//密钥条数
		public byte keyflag	= 0;						//密钥状态		cpp
		
		public void clean() {
			mpid	= 0;
			keysum	= 0;
			key_id	= 0;
			keynum	= 0;
			keyflag	= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
		}
		
		public void clone(MSG_DYCOMM_CHGKEY2 msg) {
			mpid		= 	msg.mpid;
			keysum		= 	msg.keysum;
			key_id		= 	msg.key_id;
			keynum		= 	msg.keynum;
			keyflag		= 	msg.keyflag;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
		}
		
		public MSG_DYCOMM_CHGKEY2 clone() {
			MSG_DYCOMM_CHGKEY2 ret_msg = new MSG_DYCOMM_CHGKEY2();
			ret_msg.clone(this);
			
			return ret_msg;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, keysum);
			ret_len += ComntStream.writeStream(byte_vect, key_id);
			ret_len += ComntStream.writeStream(byte_vect, keynum);
			ret_len += ComntStream.writeStream(byte_vect, keyflag);
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid);
			ret_len += ComntStream.getDataSize(mpid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			keysum = ComntStream.readStream(byte_vect, offset + ret_len, keysum); 
			ret_len += ComntStream.getDataSize(keysum);
			
			key_id = ComntStream.readStream(byte_vect, offset + ret_len, key_id); 
			ret_len += ComntStream.getDataSize(key_id);
			
			keynum = ComntStream.readStream(byte_vect, offset + ret_len, keynum); 
			ret_len += ComntStream.getDataSize(keynum);
			
			keyflag = ComntStream.readStream(byte_vect, offset + ret_len, keyflag); 
			ret_len += ComntStream.getDataSize(keyflag);
			
			return ret_len;
		}
	}

}