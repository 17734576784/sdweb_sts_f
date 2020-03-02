package com.kesd.comnt;

import com.kesd.common.YFFDef;
import com.libweb.comnt.ComntStream;
import com.libweb.comnt.ComntVector;

/**
 * 农排部分消息结构
 * @author zhpeng
 * @date	20131019
 *
 */
public class ComntMsgNp extends ComntMsg{
	//农排---------------------------------------------
	//农排操作-开户 CYFF_TASKMSG_NPOPER_ADDRES
	public static class MSG_NPOPER_ADDRES implements IMSG_READWRITESTREAM {
		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	farmerid		= 0;				//客户ID
		public short	myffalarmid		= 0;				//报警方案

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员
		public byte		paytype			= 0;				//缴费方式
		public byte[]	cardno			= new byte[MSG_STR_LEN_32];		//卡号
		public byte		opresult		= 0;
		public int		buynum			= 0;				//购电次数

		public YFFDef.YFF_PAYMONEY 	money	= new YFFDef.YFF_PAYMONEY();	//缴费信息
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			farmerid		= 0;
			myffalarmid		= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			
			paytype			= 0;
			for (int i = 0; i < MSG_STR_LEN_32; i++) cardno[i] = 0;
			opresult		= 0;
			buynum			= 0;
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, null);
		}
		
		public void clone(MSG_NPOPER_ADDRES msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			farmerid		= msg.farmerid;
			myffalarmid		= msg.myffalarmid;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			paytype			= msg.paytype;
			for (int i = 0; i < MSG_STR_LEN_32; i++) {
				cardno[i] = msg.cardno[i];
			}			
			opresult		= msg.opresult;
			buynum			= msg.buynum;

			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, msg.money);
		}
		
		public MSG_NPOPER_ADDRES clone() {
			MSG_NPOPER_ADDRES ret_msg = new MSG_NPOPER_ADDRES();
			ret_msg.clone(this);
			
			return ret_msg;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, farmerid);
			ret_len += ComntStream.writeStream(byte_vect, myffalarmid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, paytype);
			ret_len += ComntStream.writeStream(byte_vect, cardno, 0, cardno.length);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			ret_len += ComntStream.writeStream(byte_vect, buynum);

			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, money);

			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			farmerid = ComntStream.readStream(byte_vect, offset + ret_len, farmerid); 
			ret_len += ComntStream.getDataSize(farmerid);

			myffalarmid = ComntStream.readStream(byte_vect, offset + ret_len, myffalarmid); 
			ret_len += ComntStream.getDataSize(myffalarmid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			paytype = ComntStream.readStream(byte_vect, offset + ret_len, paytype); 
			ret_len += ComntStream.getDataSize(paytype);
			
			ComntStream.readStream(byte_vect, offset + ret_len, cardno, 0, cardno.length);
			ret_len += ComntStream.getDataSize(cardno[0]) * cardno.length;
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			buynum = ComntStream.readStream(byte_vect, offset + ret_len, buynum); 
			ret_len += ComntStream.getDataSize(buynum);

			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, money);

			return ret_len;
		}				
	}

	
	//农排操作-缴费 CYFF_TASKMSG_NPOPER_PAY
	public static class MSG_NPOPER_PAY implements IMSG_READWRITESTREAM {		
		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	farmerid			= 0;				//客户ID

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员
		public byte		paytype			= 0;				//缴费方式
		public byte		opresult		= 0;
		
		public int		buynum			= 0;				//购电次
		public double	lsremain		= 0;				//上次剩余金额
		public YFFDef.YFF_PAYMONEY 	money	= new YFFDef.YFF_PAYMONEY();	//缴费信息

		public void clean() {
			testf			= 0;
			gsmf			= 0;
			farmerid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			
			paytype			= 0;
			opresult		= 0;
			buynum			= 0;
			
			lsremain		= 0;

			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, null);
		}
		
		public void clone(MSG_NPOPER_PAY msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			farmerid			= msg.farmerid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			paytype			= msg.paytype;
			opresult		= msg.opresult;
			buynum			= msg.buynum;
			lsremain		= msg.lsremain;

			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, msg.money);
		}
		
		public MSG_NPOPER_PAY clone() {
			MSG_NPOPER_PAY ret_msg = new MSG_NPOPER_PAY();
			ret_msg.clone(this);
			
			return ret_msg;
		}
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, farmerid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, paytype);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			ret_len += ComntStream.writeStream(byte_vect, buynum);
			ret_len += ComntStream.writeStream(byte_vect, lsremain);

			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, money);

			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			farmerid = ComntStream.readStream(byte_vect, offset + ret_len, farmerid); 
			ret_len += ComntStream.getDataSize(farmerid);

			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			paytype = ComntStream.readStream(byte_vect, offset + ret_len, paytype); 
			ret_len += ComntStream.getDataSize(paytype);
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			buynum = ComntStream.readStream(byte_vect, offset + ret_len, buynum); 
			ret_len += ComntStream.getDataSize(buynum);
			
			lsremain = ComntStream.readStream(byte_vect, offset + ret_len, lsremain); 
			ret_len += ComntStream.getDataSize(lsremain);

			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, money);

			return ret_len;
		}
	}
	
	
	//农排操作-补卡 CYFF_TASKMSG_NPOPER_REPAIR
	public static class MSG_NPOPER_REPAIR implements IMSG_READWRITESTREAM {		
		public byte	 testf		= 0;						//测试标志
		public byte	 gsmf		= 0;						//是否发送缴费短信
		public short farmerid	= 0;						//客户ID

		public byte	operman[] 	= new byte[MSG_STR_LEN_64];	//操作员
		public byte	opresult	= 0;
		public byte	cardno[]	= new byte[MSG_STR_LEN_32];	//卡号
		public int	old_op_date	= 0;
		public int	old_op_time = 0;

		public double	lsremain		= 0;				//上次剩余金额
		public YFFDef.YFF_PAYMONEY 	money	= new YFFDef.YFF_PAYMONEY();	//缴费信息

		public void clean() {
			testf			= 0;
			gsmf			= 0;
			farmerid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			opresult		= 0;
			for (int i = 0; i < MSG_STR_LEN_32; i++) cardno[i] = 0;
			old_op_date		= 0;
			old_op_time 	= 0;
			lsremain		= 0;

			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, null);
		}
		
		public void clone(MSG_NPOPER_REPAIR msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			farmerid			= msg.farmerid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			opresult		= msg.opresult;
			for (int i = 0; i < MSG_STR_LEN_32; i++) {
				cardno[i] = msg.cardno[i];
			}
			old_op_date		= msg.old_op_date;
			old_op_time		= msg.old_op_time;
			
			lsremain		= msg.lsremain;
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, msg.money);
		}
		
		public MSG_NPOPER_REPAIR clone() {
			MSG_NPOPER_REPAIR ret_msg = new MSG_NPOPER_REPAIR();
			ret_msg.clone(this);
			
			return ret_msg;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, farmerid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			ret_len += ComntStream.writeStream(byte_vect, cardno, 0, cardno.length);
			ret_len += ComntStream.writeStream(byte_vect, old_op_date);
			ret_len += ComntStream.writeStream(byte_vect, old_op_time);
			ret_len += ComntStream.writeStream(byte_vect, lsremain);
			
			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, money);
	
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			farmerid = ComntStream.readStream(byte_vect, offset + ret_len, farmerid);
			ret_len += ComntStream.getDataSize(farmerid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			ComntStream.readStream(byte_vect, offset + ret_len, cardno, 0, cardno.length);
			ret_len += ComntStream.getDataSize(cardno[0]) * cardno.length;
			
			old_op_date = ComntStream.readStream(byte_vect, offset + ret_len, old_op_date); 
			ret_len += ComntStream.getDataSize(old_op_date);
			
			old_op_time = ComntStream.readStream(byte_vect, offset + ret_len, old_op_time); 
			ret_len += ComntStream.getDataSize(old_op_time);
			
			lsremain = ComntStream.readStream(byte_vect, offset + ret_len, lsremain); 
			ret_len += ComntStream.getDataSize(lsremain);

			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, money);
			
			return ret_len;
		}
		
	}
	
	
	//农排操作-补写卡  CYFF_TASKMSG_NPOPER_REWRITE
	public static class MSG_NPOPER_REWRITE implements IMSG_READWRITESTREAM {

		public byte	testf		= 0;						//测试标志
		public byte	gsmf		= 0;						//是否发送缴费短信
		public short	farmerid	= 0;						//客户ID

		public byte	operman[] 	= new byte[MSG_STR_LEN_64];	//操作员
		public byte	opresult	= 0;

		public int	old_op_date	= 0;
		public int	old_op_time = 0;

		public void clean() {
			testf			= 0;
			gsmf			= 0;
			farmerid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			opresult		= 0;
			old_op_date		= 0;
			old_op_time 	= 0;
		}
		
		public void clone(MSG_NPOPER_REWRITE msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			farmerid		= msg.farmerid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			opresult		= msg.opresult;
			old_op_date		= msg.old_op_date;
			old_op_time		= msg.old_op_time;
		}
		
		public MSG_NPOPER_REWRITE clone() {
			MSG_NPOPER_REWRITE ret_msg = new MSG_NPOPER_REWRITE();
			ret_msg.clone(this);
			
			return ret_msg;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, farmerid);
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

			farmerid = ComntStream.readStream(byte_vect, offset + ret_len, farmerid);
			ret_len += ComntStream.getDataSize(farmerid);
			
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
	
	//农排操作-冲正  CYFF_TASKMSG_NPOPER_REVER
	public static class MSG_NPOPER_REVER implements IMSG_READWRITESTREAM {

		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	farmerid			= 0;				//客户ID		

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员
		public byte		paytype			= 0;				//缴费方式
		public byte		opresult		= 0;
		
		public int		oldopdate		= 0;				//购电次数
		public int		oldoptime		= 0;				//报警方案
		public double	lsremain		= 0;				//上次剩余金额

		public YFFDef.YFF_PAYMONEY 	newmoney= new YFFDef.YFF_PAYMONEY();	//新缴费信息
		
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			farmerid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			paytype			= 0;
			opresult		= 0;
			
			oldopdate		= 0;
			oldoptime		= 0;
			lsremain		= 0;

			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(newmoney, null);
		}
		
		public void clone(MSG_NPOPER_REVER msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			farmerid			= msg.farmerid;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			paytype			= msg.paytype;
			opresult		= msg.opresult;
			
			oldopdate			= msg.oldopdate;
			oldoptime		= msg.oldoptime;
			lsremain		= msg.lsremain;

			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(newmoney, msg.newmoney);
		}
		
		public MSG_NPOPER_REVER clone() {
			MSG_NPOPER_REVER ret_msg = new MSG_NPOPER_REVER();
			ret_msg.clone(this);
			
			return ret_msg;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, farmerid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, paytype);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			ret_len += ComntStream.writeStream(byte_vect, oldopdate);
			ret_len += ComntStream.writeStream(byte_vect, oldoptime);
			ret_len += ComntStream.writeStream(byte_vect, lsremain);

			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, newmoney);
			
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			farmerid = ComntStream.readStream(byte_vect, offset + ret_len, farmerid); 
			ret_len += ComntStream.getDataSize(farmerid);
			
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
			
			lsremain = ComntStream.readStream(byte_vect, offset + ret_len, lsremain); 
			ret_len += ComntStream.getDataSize(lsremain);
			
			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, newmoney);
			
			return ret_len;
		}		
	}
	

	//农排操作-强制更新  CYFF_TASKMSG_NPOPER_UPDATE
	public static class MSG_NPOPER_UPDATE implements IMSG_READWRITESTREAM {
		public byte		testf		= 0;					//测试标志
		public byte		gsmf		= 0;					//是否发送缴费短信
		public short	farmerid		= 0;					//客户ID	

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
		
		public byte		alarmvalidf		= 0;
		public byte		commdatavalidf	= 0;
		public YFFDef.YFF_FARMERSTATE state = new YFFDef.YFF_FARMERSTATE();
		
		
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			farmerid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			paravalidf		= 0;
			
			state_op_validf = 0;
			state_js_validf	= 0;
			state_bn_validf	= 0;
			state_cc_validf	= 0;
			
			state_cs_validf	= 0;
			state_yc_validf	= 0;
			state_ot_validf	= 0;
			state_df_validf	= 0;
			
			alarmvalidf		= 0;
			commdatavalidf	= 0;
			YFFDef.YFF_FARMERSTATE.setYFF_FARMERSTATE(state, null);
		}
		
		public void clone(MSG_NPOPER_UPDATE msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			farmerid			= msg.farmerid;
			
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
			
			alarmvalidf		= msg.alarmvalidf;
			commdatavalidf	= msg.commdatavalidf;
			YFFDef.YFF_FARMERSTATE.setYFF_FARMERSTATE(state, msg.state);
		}
		
		public MSG_NPOPER_UPDATE clone() {
			MSG_NPOPER_UPDATE ret_msg = new MSG_NPOPER_UPDATE();
			ret_msg.clone(this);
			
			return ret_msg;
		}

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, farmerid);
			
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
			
			ret_len += ComntStream.writeStream(byte_vect, alarmvalidf);
			ret_len += ComntStream.writeStream(byte_vect, commdatavalidf);
			ret_len += YFFDef.YFF_FARMERSTATE.writeStream(byte_vect, state);
			
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			farmerid = ComntStream.readStream(byte_vect, offset + ret_len, farmerid);
			ret_len += ComntStream.getDataSize(farmerid);
			
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
			
			alarmvalidf = ComntStream.readStream(byte_vect, offset + ret_len, alarmvalidf); 
			ret_len += ComntStream.getDataSize(alarmvalidf);
			
			commdatavalidf = ComntStream.readStream(byte_vect, offset + ret_len, commdatavalidf); 
			ret_len += ComntStream.getDataSize(commdatavalidf);
			
			ret_len = YFFDef.YFF_FARMERSTATE.getDataSize(byte_vect, offset, ret_len, state);
			
			return ret_len;
		}
	}
	
	
	//农排操作-获得预付费参数及状态  CYFF_TASKMSG_NPOPER_GETPARASTATE
	public static class MSG_NPOPER_GETPARASTATE implements IMSG_READWRITESTREAM {
		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	farmerid			= 0;				//客户ID	

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员
		
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			farmerid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			
		}
		
		public void clone(MSG_NPOPER_GETPARASTATE msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			farmerid			= msg.farmerid;
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			
		}
		
		public MSG_NPOPER_GETPARASTATE clone() {
			MSG_NPOPER_GETPARASTATE ret_msg = new MSG_NPOPER_GETPARASTATE();
			ret_msg.clone(this);
			
			return ret_msg;
		}
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, farmerid);
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			farmerid = ComntStream.readStream(byte_vect, offset + ret_len, farmerid); 
			ret_len += ComntStream.getDataSize(farmerid);

			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			return ret_len;
		}
	}

	//农排操作-销户   CYFF_TASKMSG_NPOPER_DESTORY
	public static class MSG_NPOPER_DESTORY implements IMSG_READWRITESTREAM {
		public byte		testf			= 0;				//测试标志
		public byte		gsmf			= 0;				//是否发送缴费短信
		public short	farmerid			= 0;				//客户ID	

		public byte[]	operman			= new byte[MSG_STR_LEN_64];	//操作员
		public byte		opresult		= 0;
		public double	lsremain			= 0;			//上次剩余金额
		
		public YFFDef.YFF_PAYMONEY 	money	= new YFFDef.YFF_PAYMONEY();	//缴费信息

		public void clean() {
			testf			= 0;
			gsmf			= 0;
			farmerid			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			opresult		= 0;
			lsremain		= 0;
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, null);
		}
		
		public void clone(MSG_NPOPER_DESTORY msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			farmerid			= msg.farmerid;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) {
				operman[i] = msg.operman[i];
			}
			opresult		= msg.opresult;
			lsremain		= msg.lsremain;
			
			YFFDef.YFF_PAYMONEY.setYFF_PAYMONEY(money, msg.money);
		}
		
		public MSG_NPOPER_DESTORY clone() {
			MSG_NPOPER_DESTORY ret_msg = new MSG_NPOPER_DESTORY();
			ret_msg.clone(this);
			
			return ret_msg;
		}
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, farmerid);
			
			ret_len += ComntStream.writeStream(byte_vect, operman, 0, operman.length);
			ret_len += ComntStream.writeStream(byte_vect, opresult);
			ret_len += ComntStream.writeStream(byte_vect, lsremain);
			
			ret_len += YFFDef.YFF_PAYMONEY.writeStream(byte_vect, money);
			
			return ret_len;
		}

		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			testf = ComntStream.readStream(byte_vect, offset + ret_len, testf); 
			ret_len += ComntStream.getDataSize(testf);

			gsmf = ComntStream.readStream(byte_vect, offset + ret_len, gsmf); 
			ret_len += ComntStream.getDataSize(gsmf);			

			farmerid = ComntStream.readStream(byte_vect, offset + ret_len, farmerid); 
			ret_len += ComntStream.getDataSize(farmerid);

			ComntStream.readStream(byte_vect, offset + ret_len, operman, 0, operman.length);
			ret_len += ComntStream.getDataSize(operman[0]) * operman.length;
			
			opresult = ComntStream.readStream(byte_vect, offset + ret_len, opresult); 
			ret_len += ComntStream.getDataSize(opresult);
			
			lsremain = ComntStream.readStream(byte_vect, offset + ret_len, lsremain); 
			ret_len += ComntStream.getDataSize(lsremain);
			
			ret_len = YFFDef.YFF_PAYMONEY.getDataSize(byte_vect, offset, ret_len, money);

			return ret_len;
		}
	}
		

	//农排操作-更改用户参数  20130201
	public static class MSG_NPOPER_RESETDOC implements IMSG_READWRITESTREAM {	
	
		public byte		testf		= 0;				//测试标志
		public byte		gsmf		= 0;				//是否发送缴费短信
		public short	farmerid	= 0;				//用户id
		
		public byte[]	operman		= new byte[MSG_STR_LEN_64];	//操作员
		public byte		chgtype		= 0;				//类型   //YFF_DY_CHGPARATYPE_MOBILE1	居民手机号码 filed1有效

		public byte[]	filed1		= new byte[MSG_STR_LEN_64];	//字段1
		public byte[]	filed2		= new byte[MSG_STR_LEN_64];	//字段2
		public byte[]	filed3		= new byte[MSG_STR_LEN_64];	//字段3
		public byte[]	filed4		= new byte[MSG_STR_LEN_64];	//字段4
		
		public void clean() {
			testf			= 0;
			gsmf			= 0;
			farmerid			= 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) operman[i] = 0;
			chgtype			= 0;
			
			for (int i = 0; i < MSG_STR_LEN_64; i++) filed1[i] = 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) filed2[i] = 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) filed3[i] = 0;
			for (int i = 0; i < MSG_STR_LEN_64; i++) filed4[i] = 0;
		}
		
		public void clone(MSG_NPOPER_RESETDOC msg) {
			testf			= msg.testf;
			gsmf			= msg.gsmf;
			farmerid			= msg.farmerid;

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
		
		public MSG_NPOPER_RESETDOC clone() {
			MSG_NPOPER_RESETDOC ret_msg = new MSG_NPOPER_RESETDOC();
			ret_msg.clone(this);
			
			return ret_msg;
		}
				
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, testf);
			ret_len += ComntStream.writeStream(byte_vect, gsmf);
			ret_len += ComntStream.writeStream(byte_vect, farmerid);
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

			farmerid = ComntStream.readStream(byte_vect, offset + ret_len, farmerid); 
			ret_len += ComntStream.getDataSize(farmerid);

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

}