package com.kesd.comnt;

import com.kesd.common.YFFDef;
import com.libweb.comnt.ComntStream;
import com.libweb.comnt.ComntVector;

/**
 * 高压部分消息结构
 * @author zhpeng
 * @date	20131019
 *
 */
public class ComntMsgGy extends ComntMsg{
	
	//高压操作-开户 CYFF_TASKMSG_GYOPER_ADDCUS
	public static class MSG_GYOPER_ADDCUS implements IMSG_READWRITESTREAM {
		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	zjgid			= 0;				//点号
		public short	myffalarmid		= 0;				//报警方案

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员
		public byte		paytype			= 0;				//缴费方式

		public byte		opresult		= 0;
		public int		buynum			= 0;				//购电次数
		
		public double	alarm_val1		= 0;				//报警值1
		public double	alarm_val2		= 0;				//报警值2
		
		//20120723
		public double	total_yzbdl		= 0;				//有功追补电量
		public double	total_wzbdl		= 0;				//无功追补电量
		
		public YFFDef.YFF_PAYBZ		bz		= new YFFDef.YFF_PAYBZ();
		public YFFDef.YFF_PAYMONEY 	money	= new YFFDef.YFF_PAYMONEY();	//缴费信息
		public YFFDef.YFF_DBBD[] 	bd		= new YFFDef.YFF_DBBD[YFF_ZJGPAY_METERNUM];		//表底信息
		{
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			zjgid			= 0;
			myffalarmid		= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			
			paytype			= 0;
			opresult		= 0;
			buynum			= 0;
			
			alarm_val1		= 0;
			alarm_val2		= 0;
			
			total_yzbdl		= 0;
			total_wzbdl		= 0;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, null);
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, null);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
		}
		
		public void clone(MSG_GYOPER_ADDCUS msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			zjgid			= msg.zjgid;
			myffalarmid		= msg.myffalarmid;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			paytype			= msg.paytype;
			opresult		= msg.opresult;
			buynum			= msg.buynum;
			alarm_val1		= msg.alarm_val1;
			alarm_val2		= msg.alarm_val2;
			
			total_yzbdl		= msg.total_yzbdl;
			total_wzbdl		= msg.total_wzbdl;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, msg.bz);
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, msg.money);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
		}
		
		public MSG_GYOPER_ADDCUS clone() {
			MSG_GYOPER_ADDCUS ret_msg = new MSG_GYOPER_ADDCUS();
			ret_msg.clone(this);
			
			return ret_msg;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += ComntStream.writeStream(byte_vect, myffalarmid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, paytype);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			ret_len += ComntStream.writeStream(byte_vect, buynum);
			ret_len += ComntStream.writeStream(byte_vect, alarm_val1);
			ret_len += ComntStream.writeStream(byte_vect, alarm_val2);
	
			ret_len += ComntStream.writeStream(byte_vect, total_yzbdl);
			ret_len += ComntStream.writeStream(byte_vect, total_wzbdl);			
			
			ret_len += YFFDef.YFF_PAYBZ.writeStream(byte_vect, bz);
			
			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, money);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
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

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid); 
			ret_len += ComntStream.getDataSize(zjgid);

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
			
			alarm_val1 = ComntStream.readStream(byte_vect, offset + ret_len, alarm_val1); 
			ret_len += ComntStream.getDataSize(alarm_val1);
			
			alarm_val2 = ComntStream.readStream(byte_vect, offset + ret_len, alarm_val2); 
			ret_len += ComntStream.getDataSize(alarm_val2);
			
			total_yzbdl = ComntStream.readStream(byte_vect, offset + ret_len, total_yzbdl); 
			ret_len += ComntStream.getDataSize(total_yzbdl);
			
			total_wzbdl = ComntStream.readStream(byte_vect, offset + ret_len, total_wzbdl); 
			ret_len += ComntStream.getDataSize(total_wzbdl);
			
			ret_len = YFFDef.YFF_PAYBZ.getDataSize(byte_vect, offset, ret_len, bz);
			
			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, money);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			return ret_len;
		}		
	}
	
	//高压操作-缴费
	public static class MSG_GYOPER_PAY implements IMSG_READWRITESTREAM {
		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	zjgid			= 0;				//点号

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员
		public byte		paytype			= 0;				//缴费方式

		public byte		opresult		= 0;
		public int		buynum			= 0;				//购电次数
		
		public double	alarm_val1		= 0;				//报警值1
		public double	alarm_val2		= 0;				//报警值2
		
		public YFFDef.YFF_PAYBZ		bz		= new YFFDef.YFF_PAYBZ();
		public YFFDef.YFF_PAYMONEY 	money	= new YFFDef.YFF_PAYMONEY();	//缴费信息
		public YFFDef.YFF_DBBD[] 	bd		= new YFFDef.YFF_DBBD[YFF_ZJGPAY_METERNUM];		//表底信息
		{
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			zjgid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			
			paytype			= 0;
			opresult		= 0;
			buynum			= 0;
			
			alarm_val1		= 0;
			alarm_val2		= 0;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, null);
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, null);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
		}
		
		public void clone(MSG_GYOPER_PAY msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			zjgid			= msg.zjgid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			paytype			= msg.paytype;
			opresult		= msg.opresult;
			buynum			= msg.buynum;
			alarm_val1		= msg.alarm_val1;
			alarm_val2		= msg.alarm_val2;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, msg.bz);
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, msg.money);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
			
		}
		
		public MSG_GYOPER_PAY clone() {
			MSG_GYOPER_PAY ret_msg = new MSG_GYOPER_PAY();
			ret_msg.clone(this);
			
			return ret_msg;
		}
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, paytype);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			ret_len += ComntStream.writeStream(byte_vect, buynum);
			ret_len += ComntStream.writeStream(byte_vect, alarm_val1);
			ret_len += ComntStream.writeStream(byte_vect, alarm_val2);
	
			ret_len += YFFDef.YFF_PAYBZ.writeStream(byte_vect, bz);
			
			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, money);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
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

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid); 
			ret_len += ComntStream.getDataSize(zjgid);

			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			paytype = ComntStream.readStream(byte_vect, offset + ret_len, paytype); 
			ret_len += ComntStream.getDataSize(paytype);
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			buynum = ComntStream.readStream(byte_vect, offset + ret_len, buynum); 
			ret_len += ComntStream.getDataSize(buynum);
			
			alarm_val1 = ComntStream.readStream(byte_vect, offset + ret_len, alarm_val1); 
			ret_len += ComntStream.getDataSize(alarm_val1);
			
			alarm_val2 = ComntStream.readStream(byte_vect, offset + ret_len, alarm_val2); 
			ret_len += ComntStream.getDataSize(alarm_val2);
			
			
			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, money);
			
			ret_len = YFFDef.YFF_PAYBZ.getDataSize(byte_vect, offset, ret_len, bz);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			return ret_len;
		}
	}
	
	//高压操作-补卡
	public static class MSG_GYOPER_REPAIR implements IMSG_READWRITESTREAM {
		public byte	 testf		= 0;						//测试标志
		public byte	 gsmf		= 0;						//是否发送缴费短信
		public short zjgid		= 0;						//点号

		public byte	operman[] 	= new byte[MSG_STR_LEN_64];	//操作员
		public byte	opresult	= 0;

		public int	old_op_date	= 0;
		public int	old_op_time = 0;

		public void clean() {
			testf			= 0;
			gsmf			= 0;
			zjgid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			opresult		= 0;
			old_op_date		= 0;
			old_op_time 	= 0;
		}
		
		public void clone(MSG_GYOPER_REPAIR msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			zjgid			= msg.zjgid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			opresult		= msg.opresult;
			old_op_date		= msg.old_op_date;
			old_op_time		= msg.old_op_time;
		}
		
		public MSG_GYOPER_REPAIR clone() {
			MSG_GYOPER_REPAIR ret_msg = new MSG_GYOPER_REPAIR();
			ret_msg.clone(this);
			
			return ret_msg;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
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

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid);
			ret_len += ComntStream.getDataSize(zjgid);
			
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
	
	//高压操作-冲正
	public static class MSG_GYOPER_REVER implements IMSG_READWRITESTREAM {
		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	zjgid			= 0;				//点号
		

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员
		public byte		paytype			= 0;				//缴费方式
		public byte		opresult		= 0;
		
		public int		oldopdate		= 0;				//购电次数
		public int		oldoptime		= 0;				//报警方案
		
		public double	alarm_val1		= 0;				//报警值1
		public double	alarm_val2		= 0;				//报警值2
		
		public YFFDef.YFF_PAYBZ		bz		= new YFFDef.YFF_PAYBZ();
		public YFFDef.YFF_PAYMONEY 	newmoney= new YFFDef.YFF_PAYMONEY();	//新缴费信息
		
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			zjgid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			
			paytype			= 0;
			opresult		= 0;
			oldopdate		= 0;
			oldoptime		= 0;
			
			alarm_val1		= 0;
			alarm_val2		= 0;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, null);
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(newmoney, null);
		}
		
		public void clone(MSG_GYOPER_REVER msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			zjgid			= msg.zjgid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			paytype			= msg.paytype;
			opresult		= msg.opresult;
			oldopdate			= msg.oldopdate;
			oldoptime		= msg.oldoptime;
			alarm_val1		= msg.alarm_val1;
			alarm_val2		= msg.alarm_val2;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, msg.bz);
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(newmoney, msg.newmoney);
		}
		
		public MSG_GYOPER_REVER clone() {
			MSG_GYOPER_REVER ret_msg = new MSG_GYOPER_REVER();
			ret_msg.clone(this);
			
			return ret_msg;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, paytype);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			ret_len += ComntStream.writeStream(byte_vect, oldopdate);
			ret_len += ComntStream.writeStream(byte_vect, oldoptime);
			ret_len += ComntStream.writeStream(byte_vect, alarm_val1);
			ret_len += ComntStream.writeStream(byte_vect, alarm_val2);
	
			ret_len += YFFDef.YFF_PAYBZ.writeStream(byte_vect, bz);
			
			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, newmoney);
			
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid); 
			ret_len += ComntStream.getDataSize(zjgid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			paytype = ComntStream.readStream(byte_vect, offset + ret_len, paytype); 
			ret_len += ComntStream.getDataSize(paytype);
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			oldopdate = ComntStream.readStream(byte_vect, offset + ret_len, oldopdate); 
			ret_len += ComntStream.getDataSize(oldopdate);
			
			oldoptime = ComntStream.readStream(byte_vect, offset + ret_len, oldoptime); 
			ret_len += ComntStream.getDataSize(oldoptime);
			
			alarm_val1 = ComntStream.readStream(byte_vect, offset + ret_len, alarm_val1); 
			ret_len += ComntStream.getDataSize(alarm_val1);
			
			alarm_val2 = ComntStream.readStream(byte_vect, offset + ret_len, alarm_val2); 
			ret_len += ComntStream.getDataSize(alarm_val2);
			
			ret_len = YFFDef.YFF_PAYBZ.getDataSize(byte_vect, offset, ret_len, bz);
			
			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, newmoney);
			
			return ret_len;
		}		
	}
	
	//高压操作-换表换倍率
	public static class MSG_GYOPER_CHANGEMETER implements IMSG_READWRITESTREAM {
		public byte				testf 		= 0;								//测试标志
		public byte				gsmf 		= 0;								//是否发送缴费短信
		public short			zjgid 		= 0;								//点号

		public byte				operman[] 	= new byte[MSG_USERNAME_LEN];		//操作员
		public byte				opresult 	= 0;
		public int				buynum 		= 0;

		public YFFDef.YFF_DBBD			oldbd[] 	= new YFFDef.YFF_DBBD[YFF_ZJGPAY_METERNUM];	//旧表底信息
		public YFFDef.YFF_DBBD			newbd[] 	= new YFFDef.YFF_DBBD[YFF_ZJGPAY_METERNUM];	//新表底信息
		{
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				oldbd[i] = new YFFDef.YFF_DBBD();
				newbd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public double			alarm_val1 	= 0;
		public double			alarm_val2 	= 0;
		
		public YFFDef.YFF_PAYBZ			bz			= new YFFDef.YFF_PAYBZ();
		public YFFDef.YFF_PAYMONEY		newmoney 	= new YFFDef.YFF_PAYMONEY()	;				//新缴费信息
		public YFFDef.YFF_CHGMETERRATE	chgmterrate = new YFFDef.YFF_CHGMETERRATE();			//换表信息
		
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			zjgid			= 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			opresult		= 0;
			buynum			= 0;
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(oldbd[i], null);
				YFFDef.YFF_DBBD.setYFF_DBBD(newbd[i], null);
			}
			
			alarm_val1		= 0;
			alarm_val2		= 0;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, null);
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(newmoney, null);
			YFFDef.YFF_CHGMETERRATE.setYFF_CHGMETERRATE(chgmterrate, null);
		}
		
		public void clone(MSG_GYOPER_CHANGEMETER msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			zjgid			= msg.zjgid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			opresult		= msg.opresult;
			buynum			= msg.buynum;
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(oldbd[i], msg.oldbd[i]);
				YFFDef.YFF_DBBD.setYFF_DBBD(newbd[i], msg.newbd[i]);
			}
			alarm_val1		= msg.alarm_val1;
			alarm_val2		= msg.alarm_val2;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, msg.bz);
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(newmoney, msg.newmoney);
			YFFDef.YFF_CHGMETERRATE.setYFF_CHGMETERRATE(chgmterrate, msg.chgmterrate);
		}
		
		public MSG_GYOPER_CHANGEMETER clone() {
			MSG_GYOPER_CHANGEMETER ret_msg = new MSG_GYOPER_CHANGEMETER();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			ret_len += ComntStream.writeStream(byte_vect, buynum);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				ret_len += YFFDef.YFF_DBBD.writeStream(byte_vect, oldbd[i]);
			}
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				ret_len += YFFDef.YFF_DBBD.writeStream(byte_vect, newbd[i]);
			}
			
			ret_len += ComntStream.writeStream(byte_vect, alarm_val1);
			ret_len += ComntStream.writeStream(byte_vect, alarm_val2);
			
			ret_len += YFFDef.YFF_PAYBZ.writeStream(byte_vect, bz);
			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, newmoney);
			ret_len += YFFDef.YFF_CHGMETERRATE.writeStream(byte_vect, chgmterrate);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid); 
			ret_len += ComntStream.getDataSize(zjgid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			buynum = ComntStream.readStream(byte_vect, offset + ret_len, buynum); 
			ret_len += ComntStream.getDataSize(buynum);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, oldbd[i]);
			}
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, newbd[i]);
			}
			
			alarm_val1 = ComntStream.readStream(byte_vect, offset + ret_len, alarm_val1); 
			ret_len += ComntStream.getDataSize(alarm_val1);
			
			alarm_val2 = ComntStream.readStream(byte_vect, offset + ret_len, alarm_val2); 
			ret_len += ComntStream.getDataSize(alarm_val2);
			
			ret_len = YFFDef.YFF_PAYBZ.getDataSize(byte_vect, offset, ret_len, bz);
			
			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, newmoney);
			
			ret_len = YFFDef.YFF_CHGMETERRATE.getDataSize(byte_vect, offset, ret_len, chgmterrate);
			
			return ret_len;
		}
	}
	
	//高压操作-换电价
	public static class MSG_GYOPER_CHANGERATE implements IMSG_READWRITESTREAM {
		public byte				testf 		= 0;								//测试标志
		public byte				gsmf 		= 0;								//是否发送缴费短信
		public short			zjgid 		= 0;								//点号

		public byte				operman[] 	= new byte[MSG_USERNAME_LEN];		//操作员
		public byte				opresult 	= 0;
		public byte				chg_type	= 0;								//类型: 0-自动更改 1-手动更改
		public short			fee_chgid[] = new short[YFF_ZJGPAY_METERNUM];	//费率更改ID
		public int				fee_chgdate = 0;								//费率更改日期
		public int				fee_chgtime = 0;								//费率更改时间
		
		public YFFDef.YFF_DBBD	bd[] 		= new YFFDef.YFF_DBBD[YFF_ZJGPAY_METERNUM];	//表底信息
		{
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			zjgid			= 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			opresult		= 0;
			chg_type		= 0;
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) fee_chgid[i] = 0;
			fee_chgdate 	= 0;
			fee_chgtime 	= 0;
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
		}
		
		public void clone(MSG_GYOPER_CHANGERATE msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			zjgid			= msg.zjgid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			opresult		= msg.opresult;
			chg_type		= msg.chg_type;
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) fee_chgid[i] = msg.fee_chgid[i];
			fee_chgdate		= msg.fee_chgdate;
			fee_chgtime		= msg.fee_chgtime;
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
		}
		
		public MSG_GYOPER_CHANGERATE clone() {
			MSG_GYOPER_CHANGERATE ret_msg = new MSG_GYOPER_CHANGERATE();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			int	i		  = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			ret_len += ComntStream.writeStream(byte_vect, chg_type);
			for (i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				ret_len += ComntStream.writeStream(byte_vect, fee_chgid[i]);
			}
			ret_len += ComntStream.writeStream(byte_vect, fee_chgdate);
			ret_len += ComntStream.writeStream(byte_vect, fee_chgtime);
	
			for (i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
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

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid); 
			ret_len += ComntStream.getDataSize(zjgid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			chg_type = ComntStream.readStream(byte_vect, offset + ret_len, chg_type); 
			ret_len += ComntStream.getDataSize(chg_type);
			
			ComntStream.readStream(byte_vect, offset + ret_len, fee_chgid, 0, fee_chgid.length);
			ret_len += ComntStream.getDataSize(fee_chgid[0]) * fee_chgid.length;
			
			fee_chgdate = ComntStream.readStream(byte_vect, offset + ret_len, fee_chgdate); 
			ret_len += ComntStream.getDataSize(fee_chgdate);
			
			fee_chgtime = ComntStream.readStream(byte_vect, offset + ret_len, fee_chgtime); 
			ret_len += ComntStream.getDataSize(fee_chgtime);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			
			return ret_len;
		}
	}
	
	//高压操作-换基本费
	public static class MSG_GYOPER_CHANGPAYADD implements IMSG_READWRITESTREAM {
		public byte				testf 		= 0;								//测试标志
		public byte				gsmf 		= 0;								//是否发送缴费短信
		public short			zjgid 		= 0;								//点号

		public byte				operman[] 	= new byte[MSG_USERNAME_LEN];		//操作员
		public byte				opresult 	= 0;
		public byte				chg_type	= 0;								//类型: 0-自动更改 1-手动更改
		public double			jbf_chgval	= 0;								//基本费更改值
		public int				fee_chgdate = 0;								//费率更改日期
		public int				fee_chgtime = 0;								//费率更改时间
		
		public YFFDef.YFF_DBBD			bd[] 	= new YFFDef.YFF_DBBD[YFF_ZJGPAY_METERNUM];	//表底信息
		{
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			zjgid			= 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			opresult		= 0;
			chg_type		= 0;
			jbf_chgval	= 0;
			fee_chgdate = 0;
			fee_chgtime = 0;
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
		}
		
		public void clone(MSG_GYOPER_CHANGPAYADD msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			zjgid			= msg.zjgid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			opresult		= msg.opresult;
			chg_type		= msg.chg_type;
			jbf_chgval		= msg.jbf_chgval;
			fee_chgdate		= msg.fee_chgdate;
			fee_chgtime		= msg.fee_chgtime;
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
		}
		
		public MSG_GYOPER_CHANGPAYADD clone() {
			MSG_GYOPER_CHANGPAYADD ret_msg = new MSG_GYOPER_CHANGPAYADD();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			ret_len += ComntStream.writeStream(byte_vect, chg_type);
			ret_len += ComntStream.writeStream(byte_vect, jbf_chgval);
			ret_len += ComntStream.writeStream(byte_vect, fee_chgdate);
			ret_len += ComntStream.writeStream(byte_vect, fee_chgtime);
	
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
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

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid); 
			ret_len += ComntStream.getDataSize(zjgid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			chg_type = ComntStream.readStream(byte_vect, offset + ret_len, chg_type); 
			ret_len += ComntStream.getDataSize(chg_type);
			
			jbf_chgval = ComntStream.readStream(byte_vect, offset + ret_len, jbf_chgval); 
			ret_len += ComntStream.getDataSize(jbf_chgval);
			
			fee_chgdate = ComntStream.readStream(byte_vect, offset + ret_len, fee_chgdate); 
			ret_len += ComntStream.getDataSize(fee_chgdate);
			fee_chgtime = ComntStream.readStream(byte_vect, offset + ret_len, fee_chgtime); 
			ret_len += ComntStream.getDataSize(fee_chgtime);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			
			return ret_len;
		}
	}
	
	//高压操作-销户
	public static class MSG_GYOPER_DESTORY implements IMSG_READWRITESTREAM {
		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	zjgid			= 0;				//点号

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员

		public byte		opresult		= 0;
		
		public YFFDef.YFF_PAYBZ		bz		= new YFFDef.YFF_PAYBZ();
		public YFFDef.YFF_PAYMONEY 	money	= new YFFDef.YFF_PAYMONEY();	//缴费信息
		public YFFDef.YFF_DBBD[] 	bd		= new YFFDef.YFF_DBBD[YFF_ZJGPAY_METERNUM];		//表底信息
		{
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			zjgid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			
			opresult		= 0;
			
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, null);
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, null);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
		}
		
		public void clone(MSG_GYOPER_DESTORY msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			zjgid			= msg.zjgid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			opresult		= msg.opresult;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, msg.bz);
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, msg.money);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
			
		}
		
		public MSG_GYOPER_DESTORY clone() {
			MSG_GYOPER_DESTORY ret_msg = new MSG_GYOPER_DESTORY();
			ret_msg.clone(this);
			
			return ret_msg;
		}
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, opresult);

			ret_len += YFFDef.YFF_PAYBZ.writeStream(byte_vect, bz);
			
			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, money);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
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

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid); 
			ret_len += ComntStream.getDataSize(zjgid);

			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			ret_len = YFFDef.YFF_PAYBZ.getDataSize(byte_vect, offset, ret_len, bz);
			
			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, money);

			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			return ret_len;
		}
	}
	
	//高压操作-暂停
	public static class MSG_GYOPER_PAUSE implements IMSG_READWRITESTREAM {
		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	zjgid			= 0;				//点号

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员

		public byte		opresult		= 0;

		public YFFDef.YFF_PAYBZ		bz		= new YFFDef.YFF_PAYBZ();
		public YFFDef.YFF_PAYMONEY 	money	= new YFFDef.YFF_PAYMONEY();	//缴费信息
		public YFFDef.YFF_DBBD[] 	bd		= new YFFDef.YFF_DBBD[YFF_ZJGPAY_METERNUM];		//表底信息
		{
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			zjgid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			
			opresult		= 0;
			
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, null);
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, null);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
		}
		
		public void clone(MSG_GYOPER_PAUSE msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			zjgid			= msg.zjgid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			opresult		= msg.opresult;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, msg.bz);
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, msg.money);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
			
		}
		
		public MSG_GYOPER_PAUSE clone() {
			MSG_GYOPER_PAUSE ret_msg = new MSG_GYOPER_PAUSE();
			ret_msg.clone(this);
			
			return ret_msg;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			
			ret_len += YFFDef.YFF_PAYBZ.writeStream(byte_vect, bz);
			
			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, money);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
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

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid); 
			ret_len += ComntStream.getDataSize(zjgid);

			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			ret_len = YFFDef.YFF_PAYBZ.getDataSize(byte_vect, offset, ret_len, bz);
			
			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, money);

			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			return ret_len;
		}		
	}
	
	//高压操作-恢复
	public static class MSG_GYOPER_RESTART implements IMSG_READWRITESTREAM {
		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	zjgid			= 0;				//点号
		public short	myffalarmid		= 0;				//报警方案

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员
		public byte		paytype			= 0;				//缴费方式

		public byte		opresult		= 0;
		public int		buynum			= 0;				//购电次数
		
		public double	alarm_val1		= 0;				//报警值1
		public double	alarm_val2		= 0;				//报警值2
		
		//20120723
		public double	total_yzbdl		= 0;				//有功追补电量
		public double	total_wzbdl		= 0;				//无功追补电量		
		
		public YFFDef.YFF_PAYMONEY 	money	= new YFFDef.YFF_PAYMONEY();	//缴费信息
		public YFFDef.YFF_PAYBZ		bz		= new YFFDef.YFF_PAYBZ();		
		public YFFDef.YFF_DBBD[] 	bd		= new YFFDef.YFF_DBBD[YFF_ZJGPAY_METERNUM];		//表底信息
		{
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			zjgid			= 0;
			myffalarmid		= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			
			paytype			= 0;
			opresult		= 0;
			buynum			= 0;
			
			alarm_val1		= 0;
			alarm_val2		= 0;
			
			total_yzbdl		= 0;
			total_wzbdl		= 0;
						
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, null);

			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, null);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
		}
		
		public void clone(MSG_GYOPER_RESTART msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			zjgid			= msg.zjgid;
			myffalarmid		= msg.myffalarmid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			paytype			= msg.paytype;
			opresult		= msg.opresult;
			buynum			= msg.buynum;
			alarm_val1		= msg.alarm_val1;
			alarm_val2		= msg.alarm_val2;
			
			total_yzbdl		= msg.total_yzbdl;
			total_wzbdl		= msg.total_wzbdl;
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, msg.money);

			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, msg.bz);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
			
		}
		
		public MSG_GYOPER_RESTART clone() {
			MSG_GYOPER_RESTART ret_msg = new MSG_GYOPER_RESTART();
			ret_msg.clone(this);
			
			return ret_msg;
		}
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += ComntStream.writeStream(byte_vect, myffalarmid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, paytype);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			ret_len += ComntStream.writeStream(byte_vect, buynum);
			ret_len += ComntStream.writeStream(byte_vect, alarm_val1);
			ret_len += ComntStream.writeStream(byte_vect, alarm_val2);
	
			ret_len += ComntStream.writeStream(byte_vect, total_yzbdl);
			ret_len += ComntStream.writeStream(byte_vect, total_wzbdl);			
			
			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, money);

			ret_len += YFFDef.YFF_PAYBZ.writeStream(byte_vect, bz);			
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
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

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid); 
			ret_len += ComntStream.getDataSize(zjgid);

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
			
			alarm_val1 = ComntStream.readStream(byte_vect, offset + ret_len, alarm_val1); 
			ret_len += ComntStream.getDataSize(alarm_val1);
			
			alarm_val2 = ComntStream.readStream(byte_vect, offset + ret_len, alarm_val2); 
			ret_len += ComntStream.getDataSize(alarm_val2);
			
			total_yzbdl = ComntStream.readStream(byte_vect, offset + ret_len, total_yzbdl); 
			ret_len += ComntStream.getDataSize(total_yzbdl);
			
			total_wzbdl = ComntStream.readStream(byte_vect, offset + ret_len, total_wzbdl); 
			ret_len += ComntStream.getDataSize(total_wzbdl);
			
			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, money);
			
			ret_len = YFFDef.YFF_PAYBZ.getDataSize(byte_vect, offset, ret_len, bz);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			return ret_len;
		}
	}
	
	//高压操作-保电
	public static class MSG_GYOPER_PROTECT implements IMSG_READWRITESTREAM {
		public byte	testf		= 0;						//测试标志
		public byte	gsmf		= 0;						//是否发送缴费短信
		public short	zjgid	= 0;						//点号

		public byte	operman[] 	= new byte[MSG_STR_LEN_64];	//操作员

		public byte	m_prot_st	= 0;
		public byte	m_prot_ed	= 0;

		public void clean() {
			testf			= 0;
			gsmf			= 0;
			zjgid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			m_prot_st		= 0;
			m_prot_ed 	= 0;
		}
		
		public void clone(MSG_GYOPER_PROTECT msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			zjgid			= msg.zjgid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			m_prot_st		= msg.m_prot_st;
			m_prot_ed		= msg.m_prot_ed;
		}
		
		public MSG_GYOPER_PROTECT clone() {
			MSG_GYOPER_PROTECT ret_msg = new MSG_GYOPER_PROTECT();
			ret_msg.clone(this);
			
			return ret_msg;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
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

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid);
			ret_len += ComntStream.getDataSize(zjgid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			m_prot_st = ComntStream.readStream(byte_vect, offset + ret_len, m_prot_st); 
			ret_len += ComntStream.getDataSize(m_prot_st);
			
			m_prot_ed = ComntStream.readStream(byte_vect, offset + ret_len, m_prot_ed); 
			ret_len += ComntStream.getDataSize(m_prot_ed);
			
			return ret_len;
		}
	}
	
	//高压操作-返回余额
	public static class MSG_GYOPER_GETREMAIN implements IMSG_READWRITESTREAM {

		public byte				testf 		= 0;								//测试标志
		public byte				gsmf 		= 0;								//是否发送缴费短信
		public short			zjgid 		= 0;								//点号

		public byte				operman[] 	= new byte[MSG_USERNAME_LEN];		//操作员
		public YFFDef.YFF_DBBD	bd[] 		= new YFFDef.YFF_DBBD[YFF_ZJGPAY_METERNUM];	//表底信息
		
		{
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			zjgid			= 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
			
		}
		
		public void clone(MSG_GYOPER_GETREMAIN msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			zjgid			= msg.zjgid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}

			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
		}
		
		public MSG_GYOPER_GETREMAIN clone() {
			MSG_GYOPER_GETREMAIN ret_msg = new MSG_GYOPER_GETREMAIN();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
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

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid); 
			ret_len += ComntStream.getDataSize(zjgid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			
			return ret_len;
		}
	
		
	}
	//高压操作-返回结算补差余额
	public static class MSG_GYOPER_GETJSBCREMAIN implements IMSG_READWRITESTREAM {

		public byte				testf 		= 0;								//测试标志
		public byte				gsmf 		= 0;								//是否发送缴费短信
		public short			zjgid 		= 0;								//点号

		public byte				operman[] 	= new byte[MSG_USERNAME_LEN];		//操作员
		public YFFDef.YFF_DBBD	bd[] 		= new YFFDef.YFF_DBBD[YFF_ZJGPAY_METERNUM];	//表底信息
		
		{
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			zjgid			= 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
			
		}
		
		public void clone(MSG_GYOPER_GETJSBCREMAIN msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			zjgid			= msg.zjgid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}

			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
		}
		
		public MSG_GYOPER_GETJSBCREMAIN clone() {
			MSG_GYOPER_GETJSBCREMAIN ret_msg = new MSG_GYOPER_GETJSBCREMAIN();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
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

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid); 
			ret_len += ComntStream.getDataSize(zjgid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			
			return ret_len;
		}
	
	}
	
	//高压操作-重新计算剩余金额
	public static class MSG_GYOPER_RECALC implements IMSG_READWRITESTREAM {

		public byte				testf 		= 0;								//测试标志
		public byte				gsmf 		= 0;								//是否发送缴费短信
		public short			zjgid 		= 0;								//点号

		public byte				operman[] 	= new byte[MSG_USERNAME_LEN];		//操作员
		
		public YFFDef.YFF_DBBD		bd[] 	= new YFFDef.YFF_DBBD[YFF_ZJGPAY_METERNUM];	//表底信息
		{
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			zjgid			= 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
			
		}
		
		public void clone(MSG_GYOPER_RECALC msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			zjgid			= msg.zjgid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}
		}
		
		public MSG_GYOPER_RECALC clone() {
			MSG_GYOPER_RECALC ret_msg = new MSG_GYOPER_RECALC();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
	
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
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

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid); 
			ret_len += ComntStream.getDataSize(zjgid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			
			return ret_len;
		}
	}

	//高压操作-重新结算
	public static class MSG_GYOPER_REJS implements IMSG_READWRITESTREAM {
		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	zjgid			= 0;				//点号

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员
		public byte		paytype			= 0;				//缴费方式
		public byte		opresult		= 0;
		
		public int		buynum			= 0;
		
		public double	alarm_val1		= 0;				//报警值1
		public double	alarm_val2		= 0;				//报警值2
		
		public YFFDef.YFF_PAYBZ		bz		= new YFFDef.YFF_PAYBZ();
		public YFFDef.YFF_PAYMONEY 	money	= new YFFDef.YFF_PAYMONEY();	//缴费信息
		public YFFDef.YFF_DBBD[] 	bd		= new YFFDef.YFF_DBBD[YFF_ZJGPAY_METERNUM];		//表底信息
		{
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			zjgid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			
			paytype			= 0;
			opresult		= 0;
			
			buynum			= 0;
			
			alarm_val1		= 0;
			alarm_val2		= 0;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, null);
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, null);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
		}
		
		public void clone(MSG_GYOPER_REJS msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			zjgid			= msg.zjgid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			
			paytype			= msg.paytype;			
			opresult		= msg.opresult;
			
			buynum			= msg.buynum;
			
			alarm_val1		= msg.alarm_val1;
			alarm_val2		= msg.alarm_val2;
			
			YFFDef.YFF_PAYBZ.setYFF_PAYBZ(bz, msg.bz);
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, msg.money);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}			
		}
		
		public MSG_GYOPER_REJS clone() {
			MSG_GYOPER_REJS ret_msg = new MSG_GYOPER_REJS();
			ret_msg.clone(this);
			
			return ret_msg;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);			
			ret_len += ComntStream.writeStream(byte_vect, paytype);			
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			
			ret_len += ComntStream.writeStream(byte_vect, buynum);			
			
			ret_len += ComntStream.writeStream(byte_vect, alarm_val1);
			ret_len += ComntStream.writeStream(byte_vect, alarm_val2);
	
			ret_len += YFFDef.YFF_PAYBZ.writeStream(byte_vect, bz);
			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, money);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
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

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid); 
			ret_len += ComntStream.getDataSize(zjgid);

			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			paytype = ComntStream.readStream(byte_vect, offset + ret_len, paytype); 
			ret_len += ComntStream.getDataSize(paytype);
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);

			buynum = ComntStream.readStream(byte_vect, offset + ret_len, buynum); 
			ret_len += ComntStream.getDataSize(buynum);
			
			alarm_val1 = ComntStream.readStream(byte_vect, offset + ret_len, alarm_val1); 
			ret_len += ComntStream.getDataSize(alarm_val1);
			
			alarm_val2 = ComntStream.readStream(byte_vect, offset + ret_len, alarm_val2); 
			ret_len += ComntStream.getDataSize(alarm_val2);
			
			ret_len = YFFDef.YFF_PAYBZ.getDataSize(byte_vect, offset, ret_len, bz);
			
			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, money);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			return ret_len;
		}		
	}
	
	//高压操作-重新发行电费
	public static class MSG_GYOPER_REFXDF implements IMSG_READWRITESTREAM {	
		public byte		testf 	= 0;					//测试标志
		public byte		gsmf  	= 0;					//是否发送缴费短信

		public short	zjgid 	= 0;					//点号

		public byte		operman[] = new byte[MSG_STR_LEN_64];	//操作员
		public byte		opresult  = 0;

		//表底信息
		public YFFDef.YFF_DBBD[] 	bd		= new YFFDef.YFF_DBBD[YFF_ZJGPAY_METERNUM];		//表底信息		
		{
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				bd[i] = new YFFDef.YFF_DBBD();
			}
		}
		
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			zjgid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			opresult		= 0;
		
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], null);
			}
		}
		
		public void clone(MSG_GYOPER_REFXDF msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			zjgid			= msg.zjgid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			opresult		= msg.opresult;
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				YFFDef.YFF_DBBD.setYFF_DBBD(bd[i], msg.bd[i]);
			}			
		}
		
		public MSG_GYOPER_REFXDF clone() {
			MSG_GYOPER_REFXDF ret_msg = new MSG_GYOPER_REFXDF();
			ret_msg.clone(this);
			
			return ret_msg;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
		
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
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

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid); 
			ret_len += ComntStream.getDataSize(zjgid);

			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			for (int i = 0; i < YFF_ZJGPAY_METERNUM; i++) {
				ret_len = YFFDef.YFF_DBBD.getDataSize(byte_vect, offset, ret_len, bd[i]);
			}
			return ret_len;
		}		
	}

	
	//高压操作-更改参数  预付费参数  20130201
	public static class MSG_GYOPER_RESETDOC implements IMSG_READWRITESTREAM {	
		
		public byte		testf		= 0;				//测试标志
		public byte		gsmf		= 0;				//是否发送缴费短信
		public short	zjgid		= 0;				//点号
		
		public byte[]	operman		= new byte[MSG_STR_LEN_64];	//操作员
		public byte		chgtype		= 0;				//类型   //YFF_DY_CHGPARATYPE_MOBILE1	居民手机号码 filed1有效

		public byte[]	filed1		= new byte[MSG_STR_LEN_64];	//字段1
		public byte[]	filed2		= new byte[MSG_STR_LEN_64];	//字段2
		public byte[]	filed3		= new byte[MSG_STR_LEN_64];	//字段3
		public byte[]	filed4		= new byte[MSG_STR_LEN_64];	//字段4
		
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			zjgid			= 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			chgtype			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) filed1[i] = 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) filed2[i] = 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) filed3[i] = 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) filed4[i] = 0;
		}
		
		public void clone(MSG_GYOPER_RESETDOC msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			zjgid			= msg.zjgid;

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
		
		public MSG_GYOPER_RESETDOC clone() {
			MSG_GYOPER_RESETDOC ret_msg = new MSG_GYOPER_RESETDOC();
			ret_msg.clone(this);
			
			return ret_msg;
		}
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
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

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid); 
			ret_len += ComntStream.getDataSize(zjgid);

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
	
	//高压操作-获得预付费参数及状态
	public static class MSG_GYOPER_GETPARASTATE implements IMSG_READWRITESTREAM {
		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	zjgid			= 0;				//点号

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员
		
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			zjgid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			
		}
		
		public void clone(MSG_GYOPER_GETPARASTATE msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			zjgid			= msg.zjgid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			
		}
		
		public MSG_GYOPER_GETPARASTATE clone() {
			MSG_GYOPER_GETPARASTATE ret_msg = new MSG_GYOPER_GETPARASTATE();
			ret_msg.clone(this);
			
			return ret_msg;
		}
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid); 
			ret_len += ComntStream.getDataSize(zjgid);

			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			return ret_len;
		}
	}
	
	//高压操作-强制更新
	public static class MSG_GYOPER_UPDATE implements IMSG_READWRITESTREAM {

		public byte		testf		= 0;						//测试标志
		public byte		gsmf		= 0;						//是否发送缴费短信
		public short	zjgid	= 0;						//点号

		public byte		operman[] 	= new byte[MSG_STR_LEN_64];	//操作员

		public byte		paravalidf		= 0;
		
		public byte		state_op_validf	= 0;
		public byte		state_js_validf	= 0;
		public byte		state_bn_validf	= 0;
		public byte		state_cc_validf	= 0;
		public byte		state_cs_validf	= 0;
		public byte		state_yc_validf	= 0;
		public byte		state_ot_validf	= 0;

		public byte		state_df_validf	= 0;
		public byte		state_ff_validf	= 0;
		
		public byte		alarmvalidf		= 0;
		public byte		commdatavalidf	= 0;
		
		public YFFDef.YFF_ZJGPAYPARA		para = new YFFDef.YFF_ZJGPAYPARA();
		public YFFDef.YFF_ZJGPAYSTATE		state = new YFFDef.YFF_ZJGPAYSTATE();
		public YFFDef.YFF_ZJGPAY_ALARMSTATE	alarm = new YFFDef.YFF_ZJGPAY_ALARMSTATE();
		public YFFDef.YFF_ALARM_COMMDATA	commdata = new YFFDef.YFF_ALARM_COMMDATA();
		
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			zjgid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			paravalidf		= 0;
			state_op_validf 	= 0;
			state_js_validf	= 0;
			state_bn_validf	= 0;
			state_cc_validf	= 0;
			state_cs_validf	= 0;
			state_yc_validf	= 0;
			state_ot_validf	= 0;
			
			state_df_validf	= 0;
			state_ff_validf	= 0;
			
			alarmvalidf		= 0;
			commdatavalidf	= 0;
			
			YFFDef.YFF_ZJGPAYPARA.setYFF_ZJGPAYPARA(para, null);
			YFFDef.YFF_ZJGPAYSTATE.setYFF_ZJGPAYSTATE(state, null);
			YFFDef.YFF_ZJGPAY_ALARMSTATE.setYFF_ZJGPAY_ALARMSTATE(alarm, null);
			YFFDef.YFF_ALARM_COMMDATA.setYFF_ALARM_COMMDATA(commdata, null);
			
		}
		
		public void clone(MSG_GYOPER_UPDATE msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			zjgid			= msg.zjgid;
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
			
			state_df_validf	= msg.state_df_validf;
			state_ff_validf	= msg.state_ff_validf;			
			
			alarmvalidf		= msg.alarmvalidf;
			commdatavalidf	= msg.commdatavalidf;
			
			YFFDef.YFF_ZJGPAYPARA.setYFF_ZJGPAYPARA(para, msg.para);
			YFFDef.YFF_ZJGPAYSTATE.setYFF_ZJGPAYSTATE(state, msg.state);
			YFFDef.YFF_ZJGPAY_ALARMSTATE.setYFF_ZJGPAY_ALARMSTATE(alarm, msg.alarm);
			YFFDef.YFF_ALARM_COMMDATA.setYFF_ALARM_COMMDATA(commdata, msg.commdata);
		}
		
		public MSG_GYOPER_UPDATE clone() {
			MSG_GYOPER_UPDATE ret_msg = new MSG_GYOPER_UPDATE();
			ret_msg.clone(this);
			
			return ret_msg;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, paravalidf);
			ret_len += ComntStream.writeStream(byte_vect, state_op_validf);
			ret_len += ComntStream.writeStream(byte_vect, state_js_validf);
			ret_len += ComntStream.writeStream(byte_vect, state_bn_validf);
			ret_len += ComntStream.writeStream(byte_vect, state_cc_validf);
			ret_len += ComntStream.writeStream(byte_vect, state_cs_validf);
			ret_len += ComntStream.writeStream(byte_vect, state_yc_validf);
			ret_len += ComntStream.writeStream(byte_vect, state_ot_validf);

			ret_len += ComntStream.writeStream(byte_vect, state_df_validf);
			ret_len += ComntStream.writeStream(byte_vect, state_ff_validf);			
			
			ret_len += ComntStream.writeStream(byte_vect, alarmvalidf);
			ret_len += ComntStream.writeStream(byte_vect, commdatavalidf);
			
			ret_len += YFFDef.YFF_ZJGPAYPARA.writeStream(byte_vect, para);
			ret_len += YFFDef.YFF_ZJGPAYSTATE.writeStream(byte_vect, state);
			ret_len += YFFDef.YFF_ZJGPAY_ALARMSTATE.writeStream(byte_vect, alarm);
			ret_len += YFFDef.YFF_ALARM_COMMDATA.writeStream(byte_vect, commdata);
			
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid);
			ret_len += ComntStream.getDataSize(zjgid);
			
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

			state_df_validf = ComntStream.readStream(byte_vect, offset + ret_len, state_df_validf); 
			ret_len += ComntStream.getDataSize(state_df_validf);
			
			state_ff_validf = ComntStream.readStream(byte_vect, offset + ret_len, state_ff_validf); 
			ret_len += ComntStream.getDataSize(state_ff_validf);
			
			alarmvalidf = ComntStream.readStream(byte_vect, offset + ret_len, alarmvalidf); 
			ret_len += ComntStream.getDataSize(alarmvalidf);
			
			commdatavalidf = ComntStream.readStream(byte_vect, offset + ret_len, commdatavalidf); 
			ret_len += ComntStream.getDataSize(commdatavalidf);
			
			ret_len = YFFDef.YFF_ZJGPAYPARA.getDataSize(byte_vect, offset, ret_len, para);
			ret_len = YFFDef.YFF_ZJGPAYSTATE.getDataSize(byte_vect, offset, ret_len, state);
			ret_len = YFFDef.YFF_ZJGPAY_ALARMSTATE.getDataSize(byte_vect, offset, ret_len, alarm);
			ret_len = YFFDef.YFF_ALARM_COMMDATA.getDataSize(byte_vect, offset, ret_len, commdata);
			
			return ret_len;
		}
	}
	
	//高压通信-缴费
	public static class MSG_GYCOMM_PAY implements IMSG_READWRITESTREAM {
		public short	zjgid			= 0;
		public short	yffalarm_id		= 0;
		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员
		public int		buynum			= 0;						//购电次数
		public YFFDef.YFF_PAYMONEY 	money	= new YFFDef.YFF_PAYMONEY();	//缴费信息
		public double	alarmmoney		= 0;
		public double	buydl			= 0;						//缴费方式
		public double	paybmc			= 0;
		
		public double	shutbd			= 0;						//断电表底
		public double	alarmbd			= 0;						//报警表底
		
		//61专用
		public byte		yffflag			= 0;						//预付费启用标志
		public short	plustime		= 0;						//脉冲宽度
		
		public void clean() {
			zjgid			= 0;
			yffalarm_id		= 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			shutbd	= 0;
			alarmmoney		= 0;
			alarmbd	= 0;
			buydl			= 0;
			yffflag	= 0;
			paybmc			= 0;
			plustime	= 0;
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, null);
			
		}
		
		public void clone(MSG_GYCOMM_PAY msg) {
			zjgid		= msg.zjgid;
			yffalarm_id	= msg.yffalarm_id;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			buynum		= msg.buynum;
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, msg.money);
			alarmmoney	= msg.alarmmoney;
			buydl		= msg.buydl;
			paybmc		= msg.paybmc;
			shutbd		= msg.shutbd;
			alarmbd		= msg.alarmbd;
			yffflag		= msg.yffflag;
			plustime	= msg.plustime;
		}
		
		public MSG_GYCOMM_PAY clone() {
			MSG_GYCOMM_PAY ret_msg = new MSG_GYCOMM_PAY();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += ComntStream.writeStream(byte_vect, yffalarm_id);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, buynum);
			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, money);
			ret_len += ComntStream.writeStream(byte_vect, alarmmoney);
			ret_len += ComntStream.writeStream(byte_vect, buydl);
			ret_len += ComntStream.writeStream(byte_vect, paybmc);
			ret_len += ComntStream.writeStream(byte_vect, shutbd);
			ret_len += ComntStream.writeStream(byte_vect, alarmbd);
			ret_len += ComntStream.writeStream(byte_vect, yffflag);
			ret_len += ComntStream.writeStream(byte_vect, plustime);
			
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid); 
			ret_len += ComntStream.getDataSize(zjgid);
			
			yffalarm_id = ComntStream.readStream(byte_vect, offset + ret_len, yffalarm_id); 
			ret_len += ComntStream.getDataSize(yffalarm_id);

			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			buynum = ComntStream.readStream(byte_vect, offset + ret_len, buynum); 
			ret_len += ComntStream.getDataSize(buynum);
			
			alarmmoney = ComntStream.readStream(byte_vect, offset + ret_len, alarmmoney); 
			ret_len += ComntStream.getDataSize(alarmmoney);			
			
			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, money);
			
			buydl = ComntStream.readStream(byte_vect, offset + ret_len, buydl); 
			ret_len += ComntStream.getDataSize(buydl);
			
			paybmc = ComntStream.readStream(byte_vect, offset + ret_len, paybmc); 
			ret_len += ComntStream.getDataSize(paybmc);
			
			shutbd = ComntStream.readStream(byte_vect, offset + ret_len, shutbd); 
			ret_len += ComntStream.getDataSize(shutbd);
			
			alarmbd = ComntStream.readStream(byte_vect, offset + ret_len, alarmbd); 
			ret_len += ComntStream.getDataSize(alarmbd);
			yffflag = ComntStream.readStream(byte_vect, offset + ret_len, yffflag); 
			ret_len += ComntStream.getDataSize(yffflag);
			
			plustime = ComntStream.readStream(byte_vect, offset + ret_len, plustime); 
			ret_len += ComntStream.getDataSize(plustime);
			
			return ret_len;
		}
	}
	
	//高压通信-召参数
	public static class MSG_GYCOMM_CALLPARA implements IMSG_READWRITESTREAM {

		public short	zjgid		= 0;
		public byte[]	operman		= new byte[MSG_STR_LEN_64];
		public short	paratype	= 0;

		public ComntVector.ByteVector data_vect = new ComntVector.ByteVector();
		
		public void clean() {
			zjgid		= 0;
			paratype	= 0;

			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = 0;
			}
			data_vect.clear();
		}
		
		public void clone(MSG_GYCOMM_CALLPARA msg) {
			zjgid		= msg.zjgid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			paratype	= msg.paratype;

			data_vect.clone(msg.data_vect);
		}
		
		public MSG_GYCOMM_CALLPARA clone() {
			MSG_GYCOMM_CALLPARA ret_msg = new MSG_GYCOMM_CALLPARA();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			int vect_size = 0;

			ret_len += ComntStream.writeStream(byte_vect, zjgid);
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
			
			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid); 
			ret_len += ComntStream.getDataSize(zjgid);
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
	
	//高压通信-设参数
	public static class MSG_GYCOMM_SETPARA implements IMSG_READWRITESTREAM {

		public short	zjgid		= 0;
		public byte[]	operman		= new byte[MSG_STR_LEN_64];
		public short	paratype	= 0;

		public ComntVector.ByteVector data_vect = new ComntVector.ByteVector();
		
		public void clean() {
			zjgid		= 0;
			paratype	= 0;

			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = 0;
			}
			data_vect.clear();
		}
		
		public void clone(MSG_GYCOMM_SETPARA msg) {
			zjgid		= msg.zjgid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			paratype	= msg.paratype;

			data_vect.clone(msg.data_vect);
		}
		
		public MSG_GYCOMM_SETPARA clone() {
			MSG_GYCOMM_SETPARA ret_msg = new MSG_GYCOMM_SETPARA();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			int vect_size = 0;

			ret_len += ComntStream.writeStream(byte_vect, zjgid);
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
			
			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid); 
			ret_len += ComntStream.getDataSize(zjgid);
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
	
	//高压通信-控制
	public static class MSG_GYCOMM_CTRL implements IMSG_READWRITESTREAM {

		public short	zjgid		= 0;
		public byte[]	operman		= new byte[MSG_STR_LEN_64];
		public short	paratype	= 0;

		public ComntVector.ByteVector data_vect = new ComntVector.ByteVector();
		
		public void clean() {
			zjgid		= 0;
			paratype	= 0;

			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = 0;
			}
			data_vect.clear();
		}
		
		public void clone(MSG_GYCOMM_CTRL msg) {
			zjgid		= msg.zjgid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			paratype	= msg.paratype;

			data_vect.clone(msg.data_vect);
		}
		
		public MSG_GYCOMM_CTRL clone() {
			MSG_GYCOMM_CTRL ret_msg = new MSG_GYCOMM_CTRL();
			ret_msg.clone(this);
			
			return ret_msg;
		}
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			int vect_size = 0;

			ret_len += ComntStream.writeStream(byte_vect, zjgid);
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
			
			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid); 
			ret_len += ComntStream.getDataSize(zjgid);
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
	
}