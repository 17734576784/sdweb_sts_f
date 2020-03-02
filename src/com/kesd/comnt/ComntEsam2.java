package com.kesd.comnt;

import com.libweb.comnt.*;

public class ComntEsam2 {
	public static final int	CALL_EASM2_IDENTITY		= 1;
	public static final int	CALL_EASM2_UPDATE		= 2;
	public static final int	CALL_EASM2_CONTROL		= 3;
	public static final int	CALL_EASM2_INCREASE		= 4;
	public static final int	CALL_EASM2_CHGFEE1		= 5;
	public static final int	CALL_EASM2_CHGFEE2		= 6;
	public static final int	CALL_EASM2_CHGIMP		= 7;
	
	public static final int CALL_EASM2_CRYPPARA		= 8;
	public static final int CALL_EASM2_ININTPURSE	= 9;

	//public static final int	CALL_EASM2_LOOK			= 19;
	//public static final int	CALL_EASM2_PROC			= 20;
	
	public static final int	CALL_EASM2_COMPARE		= 21;
	public static final int	CALL_EASM2_CHGIMPEXT	= 22;
	public static final int	CALL_EASM2_CHGCRYPEXT	= 23;
	public static final int	CALL_EASM2_REVER		= 24;
	public static final int	CALL_EASM2_CLEARXL		= 25;
	public static final int	CALL_EASM2_CLEARDL		= 26;
	public static final int	CALL_EASM2_CLEAREVE		= 27;
	public static final int	CALL_EASM2_FRAREDRAND	= 28;
	public static final int	CALL_EASM2_FRAREDAUTH	= 29;
	
	public static final int	EASM2_OK				= 0;
	public static final int	EASM2_ERR				= -1;

	public static final int	ESAM2_16_STRLEN			= 16;		//描述长度
	public static final int	ESAM2_32_STRLEN			= 32;		//描述长度
	public static final int	ESAM2_80_STRLEN			= 80;		//描述长度
	
	public static final int	ESAM2_FEERATE_NUM		= 5;		//费率数
	public static final int	ESAM2_JTSTEP_NUM		= 7;		//阶梯梯度数
	public static final int	ESAM2_JTRATE_NUM		= 8;		//阶梯费率数
	public static final int	ESAM2_JSDAY_NUM			= 5;		//结算日
	
	
	//[消息相关,请不要随意改动]
	public static class ESAM2_CRYPLINK	{
		public byte		func_id		= 0;							//程序id
		public byte		keyflag		= 0;							//密钥状态	0: 测试密钥状态；1: 正式密钥状态
		public byte[]	div 		= new byte[ESAM2_32_STRLEN];	//分散因子 0000+表号
		//身份认证返回
		public byte[] 	easm_id 	= new byte[ESAM2_32_STRLEN];	//easm 序列号
		public byte[]   rand2   	= new byte[ESAM2_16_STRLEN];	//随机数

		//下装密钥
		public byte		keysum		= 0;							//密钥总条数
		public byte		key_id		= 0;							//密钥编号
		public byte		keynum		= 0;							//密钥条数
		//end

		//购电专用
		public byte[] 	user_no 	= new byte[ESAM2_16_STRLEN];	//户号
		public int   	pay_money 	= 0;							//缴费金额 单位分
		public int	  	pay_num 	= 0;							//购电次数
		//end

		//软件比对电表使用
		public byte		cpu_id			= 0;						//cpu编号
		public int		cpdiv_bgaddr	= 0;						//比对因子起始地址
		public int		cpdata_bgaddr	= 0;						//比对数据起始地址					
		//end	
		//软件比对函数
		public byte[]	cpdiv 			= new byte[8];				//比对因子

		//控制 清需量 事项类
		public byte 	ctrl_type		= 0;						//控制类型	
		public byte 	ctrl_ver		= 0;						//控制版本
		public byte 	date_flag		= 0;						//日期标志
		public int	  	effect_date		= 0;						//有效日期
		public int	  	effect_time		= 0;						//有效时间
		public int		clear_eve_type	= 0;						//清除数据的数据类型		//先高后低
		//end

		public int  	file_type		= 0;						//文件类型
		public short 	begin_pos		= 0;						//起始位置
		public short 	para_len		= 0;						//数据长度
		public byte[] 	para_data		= new byte[ESAM2_80_STRLEN];//参数数据

		//红外认证
		public byte[]	rand1_endata 	= new byte[8];				//随机数1密文
		//end

		//电表 645通讯使用
		public byte[] 	meter_addr		= new byte[ESAM2_16_STRLEN];//表地址
		public int  	data_type		= 0;						//数据类型		//先高后低
		public int  	pass_code		= 0;						//密码等级		//先高后低
		public int  	oper_code		= 0;						//操作者代码		//先高后低
		
		public static void setESAM2_CRYPLINK(ESAM2_CRYPLINK para, ESAM2_CRYPLINK setValue){
			if(setValue == null){
				para = new ESAM2_CRYPLINK();
			}else{
				para.func_id		= setValue.func_id;
				para.keyflag		= setValue.keyflag;
				para.keysum			= setValue.keysum;
				para.key_id			= setValue.key_id;
				para.keynum			= setValue.keynum;
				para.pay_money		= setValue.pay_money;
				para.pay_num		= setValue.pay_num;
				para.cpu_id			= setValue.cpu_id;
				para.cpdiv_bgaddr	= setValue.cpdiv_bgaddr;
				para.cpdata_bgaddr	= setValue.cpdata_bgaddr;
				
				for(int i=0; i < 8; i++){
					para.cpdiv[i]		 = setValue.cpdiv[i];
					para.rand1_endata[i] = setValue.rand1_endata[i];
				}
				
				for (int i = 0; i < ESAM2_80_STRLEN; i++) {
					para.para_data[i] = setValue.para_data[i];
				}
				
				
				for (int i = 0; i < ESAM2_32_STRLEN; i++) {
					para.div[i] = setValue.div[i];
					para.easm_id[i] = setValue.easm_id[i];
				}
				for (int i = 0; i < ESAM2_16_STRLEN; i++) {
					para.meter_addr[i] = setValue.meter_addr[i];
					para.rand2[i] = setValue.rand2[i];
					para.user_no[i] = setValue.user_no[i];
				}
				para.ctrl_type		= setValue.ctrl_type;
				para.ctrl_ver		= setValue.ctrl_ver;
				para.date_flag		= setValue.date_flag;
				para.effect_date	= setValue.effect_date;
				para.effect_time	= setValue.effect_time;
				para.clear_eve_type	= setValue.clear_eve_type;
				
				para.file_type		= setValue.file_type;
				para.begin_pos		= setValue.begin_pos;
				para.para_len		= setValue.para_len;
				para.data_type		= setValue.data_type;
				para.pass_code		= setValue.pass_code;
				para.oper_code		= setValue.oper_code;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, ESAM2_CRYPLINK para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.func_id);
			ret_len += ComntStream.writeStream(byte_vect, para.keyflag);
			ret_len += ComntStream.writeStream(byte_vect, para.div, 0, para.div.length);
			ret_len += ComntStream.writeStream(byte_vect, para.easm_id, 0, para.easm_id.length);
			ret_len += ComntStream.writeStream(byte_vect, para.rand2, 0, para.rand2.length);
			//下装密钥
			ret_len += ComntStream.writeStream(byte_vect, para.keysum);
			ret_len += ComntStream.writeStream(byte_vect, para.key_id);
			ret_len += ComntStream.writeStream(byte_vect, para.keynum);
			//购电专用
			ret_len += ComntStream.writeStream(byte_vect, para.user_no, 0, para.user_no.length);
			ret_len += ComntStream.writeStream(byte_vect, para.pay_money);
			ret_len += ComntStream.writeStream(byte_vect, para.pay_num);
			//软件比对电表使用
			ret_len += ComntStream.writeStream(byte_vect, para.cpu_id);
			ret_len += ComntStream.writeStream(byte_vect, para.cpdiv_bgaddr);
			ret_len += ComntStream.writeStream(byte_vect, para.cpdata_bgaddr);
			//软件比对函数
			ret_len += ComntStream.writeStream(byte_vect, para.div, 0, para.cpdiv.length);
			//控制 清需量 事项类
			ret_len += ComntStream.writeStream(byte_vect, para.ctrl_type);
			ret_len += ComntStream.writeStream(byte_vect, para.ctrl_ver);
			ret_len += ComntStream.writeStream(byte_vect, para.date_flag);
			ret_len += ComntStream.writeStream(byte_vect, para.effect_date);
			ret_len += ComntStream.writeStream(byte_vect, para.effect_time);
			ret_len += ComntStream.writeStream(byte_vect, para.clear_eve_type);
			
			ret_len += ComntStream.writeStream(byte_vect, para.file_type);
			ret_len += ComntStream.writeStream(byte_vect, para.begin_pos);
			ret_len += ComntStream.writeStream(byte_vect, para.para_len);
			ret_len += ComntStream.writeStream(byte_vect, para.para_data, 0, para.para_data.length);
			//红外认证
			ret_len += ComntStream.writeStream(byte_vect, para.rand1_endata, 0, para.rand1_endata.length);
			//电表 645通讯使用
			ret_len += ComntStream.writeStream(byte_vect, para.meter_addr, 0, para.meter_addr.length);
			ret_len += ComntStream.writeStream(byte_vect, para.data_type);
			ret_len += ComntStream.writeStream(byte_vect, para.pass_code);
			ret_len += ComntStream.writeStream(byte_vect, para.oper_code);
			return ret_len;
		}
		
		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, ESAM2_CRYPLINK para){
			
			int ret_len = len;
			
			para.func_id = ComntStream.readStream(byte_vect, offset + ret_len, para.func_id); 
			ret_len += ComntStream.getDataSize(para.func_id);
			
			para.keyflag = ComntStream.readStream(byte_vect, offset + ret_len, para.keyflag); 
			ret_len += ComntStream.getDataSize(para.keyflag);
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.div, 0, para.div.length);
			ret_len += ComntStream.getDataSize(para.div[0]) * para.div.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.easm_id, 0, para.easm_id.length);
			ret_len += ComntStream.getDataSize(para.easm_id[0]) * para.easm_id.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.rand2, 0, para.rand2.length);
			ret_len += ComntStream.getDataSize(para.rand2[0]) * para.rand2.length;
			
			para.keysum = ComntStream.readStream(byte_vect, offset + ret_len, para.keysum); 
			ret_len += ComntStream.getDataSize(para.keysum);
			
			para.key_id = ComntStream.readStream(byte_vect, offset + ret_len, para.key_id); 
			ret_len += ComntStream.getDataSize(para.key_id);
			
			para.keynum = ComntStream.readStream(byte_vect, offset + ret_len, para.keynum); 
			ret_len += ComntStream.getDataSize(para.keynum);
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.user_no, 0, para.user_no.length);
			ret_len += ComntStream.getDataSize(para.user_no[0]) * para.user_no.length;
			
			para.pay_money = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_money); 
			ret_len += ComntStream.getDataSize(para.pay_money);
			
			para.pay_num = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_num); 
			ret_len += ComntStream.getDataSize(para.pay_num);
			
			para.cpu_id = ComntStream.readStream(byte_vect, offset + ret_len, para.cpu_id); 
			ret_len += ComntStream.getDataSize(para.cpu_id);
				
			para.cpdiv_bgaddr = ComntStream.readStream(byte_vect, offset + ret_len, para.cpdiv_bgaddr); 
			ret_len += ComntStream.getDataSize(para.cpdiv_bgaddr);
				
			para.cpdata_bgaddr = ComntStream.readStream(byte_vect, offset + ret_len, para.cpdata_bgaddr); 
			ret_len += ComntStream.getDataSize(para.cpdata_bgaddr);
			 
			ComntStream.readStream(byte_vect, offset + ret_len, para.cpdiv, 0, para.cpdiv.length);
			ret_len += ComntStream.getDataSize(para.cpdiv[0]) * para.cpdiv.length;
			
			para.ctrl_type = ComntStream.readStream(byte_vect, offset + ret_len, para.ctrl_type); 
			ret_len += ComntStream.getDataSize(para.ctrl_type);
			
			para.ctrl_ver = ComntStream.readStream(byte_vect, offset + ret_len, para.ctrl_ver); 
			ret_len += ComntStream.getDataSize(para.ctrl_ver);
			
			para.date_flag = ComntStream.readStream(byte_vect, offset + ret_len, para.date_flag); 
			ret_len += ComntStream.getDataSize(para.date_flag);
			
			para.effect_date = ComntStream.readStream(byte_vect, offset + ret_len, para.effect_date); 
			ret_len += ComntStream.getDataSize(para.effect_date);
			
			para.effect_time = ComntStream.readStream(byte_vect, offset + ret_len, para.effect_time); 
			ret_len += ComntStream.getDataSize(para.effect_time);
			
			para.clear_eve_type = ComntStream.readStream(byte_vect, offset + ret_len, para.clear_eve_type); 
			ret_len += ComntStream.getDataSize(para.clear_eve_type);
			
			para.file_type = ComntStream.readStream(byte_vect, offset + ret_len, para.file_type); 
			ret_len += ComntStream.getDataSize(para.file_type);
			
			para.begin_pos = ComntStream.readStream(byte_vect, offset + ret_len, para.begin_pos); 
			ret_len += ComntStream.getDataSize(para.begin_pos);
			
			para.para_len = ComntStream.readStream(byte_vect, offset + ret_len, para.para_len); 
			ret_len += ComntStream.getDataSize(para.para_len);
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.para_data, 0, para.para_data.length);
			ret_len += ComntStream.getDataSize(para.para_data[0]) * para.para_data.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.rand1_endata, 0, para.rand1_endata.length);
			ret_len += ComntStream.getDataSize(para.rand1_endata[0]) * para.rand1_endata.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.meter_addr, 0, para.meter_addr.length);
			ret_len += ComntStream.getDataSize(para.meter_addr[0]) * para.meter_addr.length;
			
			para.data_type = ComntStream.readStream(byte_vect, offset + ret_len, para.data_type); 
			ret_len += ComntStream.getDataSize(para.data_type);
			
			para.pass_code = ComntStream.readStream(byte_vect, offset + ret_len, para.pass_code); 
			ret_len += ComntStream.getDataSize(para.pass_code);
			
			para.oper_code = ComntStream.readStream(byte_vect, offset + ret_len, para.oper_code); 
			ret_len += ComntStream.getDataSize(para.oper_code);

			return ret_len;
		}
	}
	
	//[消息相关,请不要随意改动]
	public static class ESAM2_LOOKSTATE{
		public int 		remain_money	= 0;							//剩余金额 单位分
		public int		buy_num			= 0;							//购电次数
		public int		key_state		= 0;							//密钥状态
		public byte[] 	user_no			= new byte[ESAM2_16_STRLEN];	//户号
		
		public static void setESAM2_LOOKSTATE(ESAM2_LOOKSTATE para, ESAM2_LOOKSTATE setValue){
			if(setValue == null){
				para = new ESAM2_LOOKSTATE();
			}else{
				para.remain_money	= setValue.remain_money;
				para.buy_num		= setValue.buy_num;
				para.key_state		= setValue.key_state;
				for(int i=0; i<ESAM2_16_STRLEN; i++){
					para.user_no[i] = setValue.user_no[i];
				}
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, ESAM2_LOOKSTATE para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.remain_money);	
			ret_len += ComntStream.writeStream(byte_vect, para.buy_num);	
			ret_len += ComntStream.writeStream(byte_vect, para.key_state);	
			ret_len += ComntStream.writeStream(byte_vect, para.user_no, 0, para.user_no.length);
			
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, ESAM2_LOOKSTATE para){
			int ret_len   = len;
			
			para.remain_money = ComntStream.readStream(byte_vect, offset + ret_len, para.remain_money); 
			ret_len += ComntStream.getDataSize(para.remain_money);
			
			para.buy_num = ComntStream.readStream(byte_vect, offset + ret_len, para.buy_num); 
			ret_len += ComntStream.getDataSize(para.buy_num);
			
			para.key_state = ComntStream.readStream(byte_vect, offset + ret_len, para.key_state); 
			ret_len += ComntStream.getDataSize(para.key_state);
		
			ComntStream.readStream(byte_vect, offset + ret_len, para.user_no, 0, para.user_no.length);
			ret_len += ComntStream.getDataSize(para.user_no[0]) * para.user_no.length;
			return ret_len;
		}	
	}
	
	//[消息相关,请不要随意改动]
	public static class ESAM2_PARAINFO{
		public byte		bit_update	= 0;						//参数更新标志位
		public int		chg_date	= 0;						//分时日期 YY-MM-DD
		public int		chg_time	= 0;						//分时时间  HH-MI-00
		public int		alarm_val1	= 0;						//单位为分
		public int		alarm_val2	= 0;						//单位为分
		public int		ct			= 0;
		public int		pt			= 0;
		public byte		meterno[]	= new byte[ESAM2_16_STRLEN];	//表号
		public byte		userno[]	= new byte[ESAM2_16_STRLEN];	//客户编号
		
		public static void setESAM2_PARAINFO(ESAM2_PARAINFO para, ESAM2_PARAINFO setValue){
			if(setValue == null){
				para = new ESAM2_PARAINFO();
			}else{
				para.bit_update			= setValue.bit_update;
				para.chg_date	= setValue.chg_date;
				para.chg_time	= setValue.chg_time;
				para.alarm_val1	= setValue.alarm_val1;
				para.alarm_val2	= setValue.alarm_val2;
				para.ct	= setValue.ct;
				para.pt	= setValue.pt;
				
				for (int i = 0; i < ESAM2_16_STRLEN; i++) {
					para.meterno[i] = setValue.meterno[i];
					para.userno[i] = setValue.userno[i];
				}
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, ESAM2_PARAINFO para){
			int ret_len   = 0;
		
			ret_len += ComntStream.writeStream(byte_vect, para.bit_update);	
			ret_len += ComntStream.writeStream(byte_vect, para.chg_date);	
			ret_len += ComntStream.writeStream(byte_vect, para.chg_time);	
			ret_len += ComntStream.writeStream(byte_vect, para.alarm_val1);
			ret_len += ComntStream.writeStream(byte_vect, para.alarm_val2);
			ret_len += ComntStream.writeStream(byte_vect, para.ct);
			ret_len += ComntStream.writeStream(byte_vect, para.pt);
			ret_len += ComntStream.writeStream(byte_vect, para.meterno, 0, para.meterno.length);
			ret_len += ComntStream.writeStream(byte_vect, para.userno, 0, para.userno.length);
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, ESAM2_PARAINFO para){
			int ret_len   = len;
			
			para.bit_update = ComntStream.readStream(byte_vect, offset + ret_len, para.bit_update); 
			ret_len += ComntStream.getDataSize(para.bit_update);
			
			para.chg_date = ComntStream.readStream(byte_vect, offset + ret_len, para.chg_date); 
			ret_len += ComntStream.getDataSize(para.chg_date);
			
			para.chg_time = ComntStream.readStream(byte_vect, offset + ret_len, para.chg_time); 
			ret_len += ComntStream.getDataSize(para.chg_time);
			
			para.alarm_val1 = ComntStream.readStream(byte_vect, offset + ret_len, para.alarm_val1); 
			ret_len += ComntStream.getDataSize(para.alarm_val1);
			
			para.alarm_val2 = ComntStream.readStream(byte_vect, offset + ret_len, para.alarm_val2); 
			ret_len += ComntStream.getDataSize(para.alarm_val2);
			
			para.ct = ComntStream.readStream(byte_vect, offset + ret_len, para.ct); 
			ret_len += ComntStream.getDataSize(para.ct);
			
			para.pt = ComntStream.readStream(byte_vect, offset + ret_len, para.pt); 
			ret_len += ComntStream.getDataSize(para.pt);
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.meterno, 0, para.meterno.length);
			ret_len += ComntStream.getDataSize(para.meterno[0]) * para.meterno.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.userno, 0, para.userno.length);
			ret_len += ComntStream.getDataSize(para.userno[0]) * para.userno.length;
			
			return ret_len;
		}
		
	}
	
	//费率信息
	//[消息相关,请不要随意改动]
	public static class ESAM2_FEERATE{
		public int[]	fee_rate 	= new int[ESAM2_FEERATE_NUM];		//复费率5	4位小数 0.0001
		public int[]	jt_step  	= new int[ESAM2_JTSTEP_NUM];		//阶梯值7	2位小数 0.01 
		public int[]	jt_rate  	= new int[ESAM2_JTRATE_NUM];		//阶梯费率8	4位小数 0.0001
		public int[]	jt_jsmdh 	= new int[ESAM2_JSDAY_NUM];			//阶梯年结算日4mdh
		public int		jt_chgymd 	= 0;								//阶梯切换YMD
		public int		jt_cghm   	= 0;								//阶梯切换HM
		
		public static void setESAM2_FEERATE(ESAM2_FEERATE para, ESAM2_FEERATE setValue){
			if(setValue == null){
				para = new ESAM2_FEERATE();
			}else{
				for(int i=0; i<setValue.fee_rate.length;i++){
					para.fee_rate[i] =  setValue.fee_rate[i];
				}
				for(int i=0; i<setValue.jt_step.length;i++){
					para.jt_step[i] =  setValue.jt_step[i];
				}
				for(int i=0; i<setValue.jt_rate.length;i++){
					para.jt_rate[i] =  setValue.jt_rate[i];
				}
				for(int i=0; i<setValue.jt_jsmdh.length;i++){
					para.jt_jsmdh[i] =  setValue.jt_jsmdh[i];
				}
				para.jt_chgymd	= setValue.jt_chgymd;
				para.jt_cghm	= setValue.jt_cghm;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, ESAM2_FEERATE para){
			int ret_len   = 0;
			ret_len += ComntStream.writeStream(byte_vect, para.fee_rate, 0, para.fee_rate.length);
			ret_len += ComntStream.writeStream(byte_vect, para.jt_step, 0, para.jt_step.length);
			ret_len += ComntStream.writeStream(byte_vect, para.jt_rate, 0, para.jt_rate.length);
			ret_len += ComntStream.writeStream(byte_vect, para.jt_jsmdh, 0, para.jt_jsmdh.length);
			ret_len += ComntStream.writeStream(byte_vect, para.jt_chgymd);
			ret_len += ComntStream.writeStream(byte_vect, para.jt_cghm);	
			
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, ESAM2_FEERATE para){
			int ret_len   = len;
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.fee_rate, 0, para.fee_rate.length);
			ret_len += ComntStream.getDataSize(para.fee_rate[0]) * para.fee_rate.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.jt_step, 0, para.jt_step.length);
			ret_len += ComntStream.getDataSize(para.jt_step[0]) * para.jt_step.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.jt_rate, 0, para.jt_rate.length);
			ret_len += ComntStream.getDataSize(para.jt_rate[0]) * para.jt_rate.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.jt_jsmdh, 0, para.jt_jsmdh.length);
			ret_len += ComntStream.getDataSize(para.jt_jsmdh[0]) * para.jt_jsmdh.length;
			
			para.jt_chgymd = ComntStream.readStream(byte_vect, offset + ret_len, para.jt_chgymd); 
			ret_len += ComntStream.getDataSize(para.jt_chgymd);
			
			para.jt_cghm = ComntStream.readStream(byte_vect, offset + ret_len, para.jt_cghm); 
			ret_len += ComntStream.getDataSize(para.jt_cghm);
			
			return ret_len;
		}
	}
	
	//运行信息
	public static class ESAM2_RUNSTATE{
		public byte		user_type		= 0;							//01 单费率 02 复费率 
		public int		ct				= 0;
		public int		pt				= 0;
		public byte		meter_no[]		= new byte[ESAM2_16_STRLEN];	//表号
		public byte		user_no[]		= new byte[ESAM2_16_STRLEN];	//客户编号
		public int		remain_money	= 0;							//剩余金额 单位为分
		public int		pay_num			= 0;							//购电次数
		public int		overd_money		= 0;							//透支金额
		public int		errin_num		= 0;							//非法插卡次数
		public int		reback_date		= 0;							//返写日期	YY-MM-DD
		public int		reback_time		= 0;							//HH-MI-00
		
		public static void setESAM2_RUNSTATE(ESAM2_RUNSTATE para, ESAM2_RUNSTATE setValue){
			if(setValue == null){
				para = new ESAM2_RUNSTATE();
			}else{
				para.user_type		= setValue.user_type;
				para.ct				= setValue.ct;
				para.pt	= setValue.pt;
				for (int i = 0; i < ESAM2_16_STRLEN; i++) {
					para.meter_no[i]= setValue.meter_no[i];
					para.user_no[i] = setValue.user_no[i];
				}
				para.remain_money	= setValue.remain_money;
				para.pay_num		= setValue.pay_num;
				para.overd_money	= setValue.overd_money;
				para.errin_num		= setValue.errin_num;
				para.reback_date	= setValue.reback_date;
				para.reback_time	= setValue.reback_time;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, ESAM2_RUNSTATE para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.user_type);
			ret_len += ComntStream.writeStream(byte_vect, para.ct);
			ret_len += ComntStream.writeStream(byte_vect, para.pt);
			ret_len += ComntStream.writeStream(byte_vect, para.meter_no, 0, para.meter_no.length);
			ret_len += ComntStream.writeStream(byte_vect, para.user_no, 0, para.user_no.length);
			ret_len += ComntStream.writeStream(byte_vect, para.remain_money);	
			ret_len += ComntStream.writeStream(byte_vect, para.pay_num);	
			ret_len += ComntStream.writeStream(byte_vect, para.overd_money);	
			ret_len += ComntStream.writeStream(byte_vect, para.errin_num);
			ret_len += ComntStream.writeStream(byte_vect, para.reback_date);
			ret_len += ComntStream.writeStream(byte_vect, para.reback_time);
	
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, ESAM2_RUNSTATE para){
			int ret_len   = len;
			
			para.user_type = ComntStream.readStream(byte_vect, offset + ret_len, para.user_type); 
			ret_len += ComntStream.getDataSize(para.user_type);
			
			para.ct = ComntStream.readStream(byte_vect, offset + ret_len, para.ct); 
			ret_len += ComntStream.getDataSize(para.ct);
			
			para.pt = ComntStream.readStream(byte_vect, offset + ret_len, para.pt); 
			ret_len += ComntStream.getDataSize(para.pt);
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.meter_no, 0, para.meter_no.length);
			ret_len += ComntStream.getDataSize(para.meter_no[0]) * para.meter_no.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.user_no, 0, para.user_no.length);
			ret_len += ComntStream.getDataSize(para.user_no[0]) * para.user_no.length;
			
			para.remain_money = ComntStream.readStream(byte_vect, offset + ret_len, para.remain_money); 
			ret_len += ComntStream.getDataSize(para.remain_money);
			
			para.pay_num = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_num); 
			ret_len += ComntStream.getDataSize(para.pay_num);
			
			para.overd_money = ComntStream.readStream(byte_vect, offset + ret_len, para.overd_money); 
			ret_len += ComntStream.getDataSize(para.overd_money);
			
			para.errin_num = ComntStream.readStream(byte_vect, offset + ret_len, para.errin_num); 
			ret_len += ComntStream.getDataSize(para.errin_num);
			
			para.reback_date = ComntStream.readStream(byte_vect, offset + ret_len, para.reback_date); 
			ret_len += ComntStream.getDataSize(para.reback_date);
			
			para.reback_time = ComntStream.readStream(byte_vect, offset + ret_len, para.reback_time); 
			ret_len += ComntStream.getDataSize(para.reback_time);
			
			return ret_len;
		}
		
	}
	
	//[消息相关,请不要随意改动]
	public static class SMARTDB2_STATE1{
		public byte	reserve1	= 0;						//保留
		public byte	xljsfs		= 0;							//需量积算方式
		public byte	szdc		= 0;							//时钟电池
		public byte	tdcbdc		= 0;							//停电抄表电池
		public byte	ygglfx		= 0;							//有功功率方向
		public byte	wgglfx		= 0;		 		 		 	//无功功率方向
		public byte	reserve6	= 0;
		public byte	reserve7	= 0;
		public byte	kzhlcw		= 0;							//控制回路错误
		public byte	esamcw		= 0;							//esamcw
		public byte	reserve10	= 0;
		public byte	reserve11	= 0;

		public byte	nbcxcw		= 0;							//内部程序错误
		public byte	ccqgz		= 0;							//存储器故障
		public byte	tzzt		= 0;							//透支状态
		public byte	reserve15	= 0;
		
		public static void setSMARTDB2_STATE1(SMARTDB2_STATE1 para, SMARTDB2_STATE1 setValue){
			if(setValue == null){
				para = new SMARTDB2_STATE1();
			}else{
				para.reserve1	= setValue.reserve1;
				para.xljsfs		= setValue.xljsfs;
				para.szdc		= setValue.szdc;
				para.tdcbdc		= setValue.tdcbdc;
				para.ygglfx		= setValue.ygglfx;
				para.wgglfx		= setValue.wgglfx;
				para.reserve6	= setValue.reserve6;
				para.reserve7	= setValue.reserve7;
				
				para.kzhlcw		= setValue.kzhlcw;
				para.esamcw		= setValue.esamcw;
				para.reserve10	= setValue.reserve10;
				para.reserve11	= setValue.reserve11;
				            
				para.nbcxcw		= setValue.nbcxcw;	
				para.ccqgz		= setValue.ccqgz;
				para.tzzt		= setValue.tzzt;
				para.reserve15	= setValue.reserve15;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, SMARTDB2_STATE1 para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.reserve1);	
			ret_len += ComntStream.writeStream(byte_vect, para.xljsfs);	
			ret_len += ComntStream.writeStream(byte_vect, para.szdc);	
			ret_len += ComntStream.writeStream(byte_vect, para.tdcbdc);	
			ret_len += ComntStream.writeStream(byte_vect, para.ygglfx);	
			ret_len += ComntStream.writeStream(byte_vect, para.wgglfx);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve6);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve7);
			ret_len += ComntStream.writeStream(byte_vect, para.kzhlcw);	
			ret_len += ComntStream.writeStream(byte_vect, para.esamcw);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve10);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve11);	
			ret_len += ComntStream.writeStream(byte_vect, para.nbcxcw);	
			ret_len += ComntStream.writeStream(byte_vect, para.ccqgz);	
			ret_len += ComntStream.writeStream(byte_vect, para.tzzt);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve15);
			
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, SMARTDB2_STATE1 para){
			int ret_len   = 0;
			
			para.reserve1 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve1); 
			ret_len += ComntStream.getDataSize(para.reserve1);
			
			para.xljsfs = ComntStream.readStream(byte_vect, offset + ret_len, para.xljsfs); 
			ret_len += ComntStream.getDataSize(para.xljsfs);
			
			para.szdc = ComntStream.readStream(byte_vect, offset + ret_len, para.szdc); 
			ret_len += ComntStream.getDataSize(para.szdc);
			
			para.tdcbdc = ComntStream.readStream(byte_vect, offset + ret_len, para.tdcbdc); 
			ret_len += ComntStream.getDataSize(para.tdcbdc);
			
			para.ygglfx = ComntStream.readStream(byte_vect, offset + ret_len, para.ygglfx); 
			ret_len += ComntStream.getDataSize(para.ygglfx);
			
			para.wgglfx = ComntStream.readStream(byte_vect, offset + ret_len, para.wgglfx); 
			ret_len += ComntStream.getDataSize(para.wgglfx);
			
			para.reserve6 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve6); 
			ret_len += ComntStream.getDataSize(para.reserve6);
			
			para.reserve7 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve7); 
			ret_len += ComntStream.getDataSize(para.reserve7);
			
			para.kzhlcw = ComntStream.readStream(byte_vect, offset + ret_len, para.kzhlcw); 
			ret_len += ComntStream.getDataSize(para.kzhlcw);
			
			para.esamcw = ComntStream.readStream(byte_vect, offset + ret_len, para.esamcw); 
			ret_len += ComntStream.getDataSize(para.esamcw);
			
			para.reserve10 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve10); 
			ret_len += ComntStream.getDataSize(para.reserve10);
			
			para.reserve11 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve11); 
			ret_len += ComntStream.getDataSize(para.reserve11);
			
			para.nbcxcw = ComntStream.readStream(byte_vect, offset + ret_len, para.nbcxcw); 
			ret_len += ComntStream.getDataSize(para.nbcxcw);
			
			para.ccqgz = ComntStream.readStream(byte_vect, offset + ret_len, para.ccqgz); 
			ret_len += ComntStream.getDataSize(para.ccqgz);
			
			para.tzzt = ComntStream.readStream(byte_vect, offset + ret_len, para.tzzt); 
			ret_len += ComntStream.getDataSize(para.tzzt);
			
			para.reserve15 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve15); 
			ret_len += ComntStream.getDataSize(para.reserve15);
			
			return ret_len;
		}
	}
	
	//[消息相关,请不要随意改动]
	public static class SMARTDB2_STATE2{
		public byte	aygp		= 0;							//A相有功功率
		public byte	bygp		= 0;							//B相有功功率
		public byte	cygp		= 0;							//C相有功功率
		public byte	reserve3	= 0;	
		public byte	awgp		= 0;							//A相有功功率
		public byte	bwgp		= 0; 							//B相有功功率
		public byte	cwgp		= 0;							//C相有功功率
		public byte	reserve7	= 0;
		public byte	reserve8	= 0;
		public byte	reserve9	= 0;			
		public byte	reserve10	= 0;			
		public byte	reserve11	= 0;			
		public byte	reserve12	= 0;			
		public byte	reserve13	= 0;
		public byte	reserve14	= 0;
		public byte	reserve15	= 0;
		
		public static void setSMARTDB2_STATE2(SMARTDB2_STATE2 para, SMARTDB2_STATE2 setValue){
			if(setValue == null){
				para = new SMARTDB2_STATE2();
			}else{
				para.aygp		= setValue.aygp;
				para.bygp		= setValue.bygp;
				para.cygp		= setValue.cygp;
				para.reserve3	= setValue.reserve3;
				para.awgp		= setValue.awgp;
				para.bwgp		= setValue.bwgp;
				para.cwgp		= setValue.cwgp;
				para.reserve7	= setValue.reserve7;
				para.reserve8	= setValue.reserve8;
				para.reserve9	= setValue.reserve9;
				para.reserve10	= setValue.reserve10;
				para.reserve11	= setValue.reserve11;
				para.reserve12	= setValue.reserve12;
				para.reserve13	= setValue.reserve13;
				para.reserve14	= setValue.reserve14;
				para.reserve15	= setValue.reserve15;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, SMARTDB2_STATE2 para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.aygp);	
			ret_len += ComntStream.writeStream(byte_vect, para.bygp);	
			ret_len += ComntStream.writeStream(byte_vect, para.cygp);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve3);	
			ret_len += ComntStream.writeStream(byte_vect, para.awgp);	
			ret_len += ComntStream.writeStream(byte_vect, para.bwgp);	
			ret_len += ComntStream.writeStream(byte_vect, para.cwgp);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve7);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve8);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve9);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve10);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve11);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve12);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve13);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve14);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve15);
			
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, SMARTDB2_STATE2 para){
			int ret_len   = 0;
			
			para.aygp = ComntStream.readStream(byte_vect, offset + ret_len, para.aygp); 
			ret_len += ComntStream.getDataSize(para.aygp);
			
			para.bygp = ComntStream.readStream(byte_vect, offset + ret_len, para.bygp); 
			ret_len += ComntStream.getDataSize(para.bygp);
			
			para.cygp = ComntStream.readStream(byte_vect, offset + ret_len, para.cygp); 
			ret_len += ComntStream.getDataSize(para.cygp);
			
			para.reserve3 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve3); 
			ret_len += ComntStream.getDataSize(para.reserve3);
			
			para.awgp = ComntStream.readStream(byte_vect, offset + ret_len, para.awgp); 
			ret_len += ComntStream.getDataSize(para.awgp);
			
			para.bwgp = ComntStream.readStream(byte_vect, offset + ret_len, para.bwgp); 
			ret_len += ComntStream.getDataSize(para.bwgp);
			
			para.cwgp = ComntStream.readStream(byte_vect, offset + ret_len, para.cwgp); 
			ret_len += ComntStream.getDataSize(para.cwgp);
			
			para.reserve7 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve7); 
			ret_len += ComntStream.getDataSize(para.reserve7);
			
			para.reserve8 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve8); 
			ret_len += ComntStream.getDataSize(para.reserve8);
			
			para.reserve9 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve9); 
			ret_len += ComntStream.getDataSize(para.reserve9);
			
			para.reserve10 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve10); 
			ret_len += ComntStream.getDataSize(para.reserve10);
			
			para.reserve11 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve11); 
			ret_len += ComntStream.getDataSize(para.reserve11);
			
			para.reserve12 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve12); 
			ret_len += ComntStream.getDataSize(para.reserve12);
			
			para.reserve13 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve13); 
			ret_len += ComntStream.getDataSize(para.reserve13);
			
			para.reserve14 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve14); 
			ret_len += ComntStream.getDataSize(para.reserve14);
			
			para.reserve15 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve15); 
			ret_len += ComntStream.getDataSize(para.reserve15);
			
			return ret_len;
		}
	}
	
	//[消息相关,请不要随意改动]
	public static class SMARTDB2_YFF{
		public byte		dqyxsd		= 0;					//当前运行时段
		public byte		gdfs		= 0;					//供电方式
		public byte		bcyx		= 0;					//编程允许
		public byte		jdqzt		= 0;					//继电器状态
		public byte		dqyxsq		= 0;					//当前运行时区
		public byte		jdqmlzt		= 0;					//继电器命令状态
		public byte		ytzbjzt		= 0;					//预跳闸报警状态
		public byte		dnblx		= 0;					//电能表类型
		
		public byte		reserve10 	= 0;//dqyxfldj;			//当前运行费率电价
		public byte		reserve11 	= 0;//dqjt;				//当前阶梯
		public byte		protect	  	= 0;					//保电状态
		public byte		authstate 	= 0;					//身份认证状态
		public byte		localkh   	= 0;					//本地开户状态
		public byte		remotekh  	= 0;					//远程开户状态
		
		public static void setSMARTDB2_YFF(SMARTDB2_YFF para, SMARTDB2_YFF setValue){
			if(setValue == null){
				para = new SMARTDB2_YFF();
			}else{
				para.dqyxsd		= setValue.dqyxsd;
				para.gdfs		= setValue.gdfs;
				para.bcyx		= setValue.bcyx;
				para.jdqzt		= setValue.jdqzt;
				para.dqyxsq		= setValue.dqyxsq;
				para.jdqmlzt	= setValue.jdqmlzt;
				para.ytzbjzt	= setValue.ytzbjzt;
				para.dnblx		= setValue.dnblx;
				
				para.reserve10	= setValue.reserve10;
				para.reserve11	= setValue.reserve11;
				para.protect	= setValue.protect;	 
				para.authstate	= setValue.authstate;
				para.localkh  	= setValue.localkh;  
				para.remotekh 	= setValue.remotekh; 
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, SMARTDB2_YFF para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.dqyxsd);	
			ret_len += ComntStream.writeStream(byte_vect, para.gdfs);	
			ret_len += ComntStream.writeStream(byte_vect, para.bcyx);	
			ret_len += ComntStream.writeStream(byte_vect, para.jdqzt);	
			ret_len += ComntStream.writeStream(byte_vect, para.dqyxsq);	
			ret_len += ComntStream.writeStream(byte_vect, para.jdqmlzt);	
			ret_len += ComntStream.writeStream(byte_vect, para.ytzbjzt);
			ret_len += ComntStream.writeStream(byte_vect, para.dnblx);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve10);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve11);	
			ret_len += ComntStream.writeStream(byte_vect, para.protect);
			ret_len += ComntStream.writeStream(byte_vect, para.authstate);	
			ret_len += ComntStream.writeStream(byte_vect, para.localkh);
			ret_len += ComntStream.writeStream(byte_vect, para.remotekh);
			
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, SMARTDB2_YFF para){
			int ret_len   = 0; 
			
			para.dqyxsd = ComntStream.readStream(byte_vect, offset + ret_len, para.dqyxsd); 
			ret_len += ComntStream.getDataSize(para.dqyxsd);
			
			para.gdfs = ComntStream.readStream(byte_vect, offset + ret_len, para.gdfs); 
			ret_len += ComntStream.getDataSize(para.gdfs);
			
			para.bcyx = ComntStream.readStream(byte_vect, offset + ret_len, para.bcyx); 
			ret_len += ComntStream.getDataSize(para.bcyx);
			
			para.jdqzt = ComntStream.readStream(byte_vect, offset + ret_len, para.jdqzt); 
			ret_len += ComntStream.getDataSize(para.jdqzt);
			
			para.dqyxsq = ComntStream.readStream(byte_vect, offset + ret_len, para.dqyxsq); 
			ret_len += ComntStream.getDataSize(para.dqyxsq);
			
			para.jdqmlzt = ComntStream.readStream(byte_vect, offset + ret_len, para.jdqmlzt); 
			ret_len += ComntStream.getDataSize(para.jdqmlzt);
			
			para.ytzbjzt = ComntStream.readStream(byte_vect, offset + ret_len, para.ytzbjzt); 
			ret_len += ComntStream.getDataSize(para.ytzbjzt);
			
			para.dnblx = ComntStream.readStream(byte_vect, offset + ret_len, para.dnblx); 
			ret_len += ComntStream.getDataSize(para.dnblx);
			
			para.reserve10 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve10); 
			ret_len += ComntStream.getDataSize(para.reserve10);
			
			para.reserve11 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve11); 
			ret_len += ComntStream.getDataSize(para.reserve11);
			
			para.protect = ComntStream.readStream(byte_vect, offset + ret_len, para.protect); 
			ret_len += ComntStream.getDataSize(para.protect);
			
			para.authstate = ComntStream.readStream(byte_vect, offset + ret_len, para.authstate); 
			ret_len += ComntStream.getDataSize(para.authstate);
			
			para.localkh = ComntStream.readStream(byte_vect, offset + ret_len, para.localkh); 
			ret_len += ComntStream.getDataSize(para.localkh);
			
			para.remotekh = ComntStream.readStream(byte_vect, offset + ret_len, para.remotekh); 
			ret_len += ComntStream.getDataSize(para.remotekh);
			
			return ret_len;
		}
	}
	
	//[消息相关,请不要随意改动]
	public static class SMARTDB2_FAILA{
		public byte	sy			= 0;								//失压
		public byte	qy			= 0;								//欠压
		public byte	gy			= 0;								//过压
		public byte	sl			= 0;								//失压
		public byte	ql			= 0;								//过流
		public byte	gz			= 0;								//过载
		public byte	clfx		= 0;								//潮流方向
		public byte	dx			= 0;								//断相
		public byte	dl			= 0;								//断流
		public byte	reserve9	= 0;
		public byte	reserve10	= 0;
		public byte	reserve11	= 0;
		public byte	reserve12	= 0;
		public byte	reserve13	= 0;
		public byte	reserve14	= 0;
		public byte	reserve15	= 0;
		
		public static void setSMARTDB2_FAIL(SMARTDB2_FAILA para, SMARTDB2_FAILA setValue){
			if(setValue == null){
				para = new SMARTDB2_FAILA();
			}else{
				para.sy			= setValue.sy;
				para.qy			= setValue.qy;
				para.gy			= setValue.gy;
				para.sl			= setValue.sl;
				para.ql			= setValue.ql;
				para.gz			= setValue.gz;
				para.clfx		= setValue.clfx;
				para.dx			= setValue.dx;
				para.dl			= setValue.dl;
				para.reserve9	= setValue.reserve9;
				para.reserve10	= setValue.reserve10;
				para.reserve11	= setValue.reserve11;
				para.reserve12	= setValue.reserve12;
				para.reserve13	= setValue.reserve13;
				para.reserve14	= setValue.reserve14;
				para.reserve15	= setValue.reserve15;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, SMARTDB2_FAILA para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.sy);	
			ret_len += ComntStream.writeStream(byte_vect, para.qy);	
			ret_len += ComntStream.writeStream(byte_vect, para.gy);	
			ret_len += ComntStream.writeStream(byte_vect, para.sl);	
			ret_len += ComntStream.writeStream(byte_vect, para.ql);	
			ret_len += ComntStream.writeStream(byte_vect, para.gz);	
			ret_len += ComntStream.writeStream(byte_vect, para.clfx);
			ret_len += ComntStream.writeStream(byte_vect, para.dx);	
			ret_len += ComntStream.writeStream(byte_vect, para.dl);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve9);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve10);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve11);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve12);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve13);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve14);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve15);
			
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, SMARTDB2_FAILA para){
			int ret_len   = 0;
			
			para.sy = ComntStream.readStream(byte_vect, offset + ret_len, para.sy); 
			ret_len += ComntStream.getDataSize(para.sy);
			
			para.qy = ComntStream.readStream(byte_vect, offset + ret_len, para.qy); 
			ret_len += ComntStream.getDataSize(para.qy);
			
			para.gy = ComntStream.readStream(byte_vect, offset + ret_len, para.gy); 
			ret_len += ComntStream.getDataSize(para.gy);
			
			para.sl = ComntStream.readStream(byte_vect, offset + ret_len, para.sl); 
			ret_len += ComntStream.getDataSize(para.sl);
			
			para.ql = ComntStream.readStream(byte_vect, offset + ret_len, para.ql); 
			ret_len += ComntStream.getDataSize(para.ql);
			
			para.gz = ComntStream.readStream(byte_vect, offset + ret_len, para.gz); 
			ret_len += ComntStream.getDataSize(para.gz);
			
			para.clfx = ComntStream.readStream(byte_vect, offset + ret_len, para.clfx); 
			ret_len += ComntStream.getDataSize(para.clfx);
			
			para.dx = ComntStream.readStream(byte_vect, offset + ret_len, para.dx); 
			ret_len += ComntStream.getDataSize(para.dx);
			
			para.dl = ComntStream.readStream(byte_vect, offset + ret_len, para.dl); 
			ret_len += ComntStream.getDataSize(para.dl);
			
			para.reserve9 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve9); 
			ret_len += ComntStream.getDataSize(para.reserve9);
			
			para.reserve10 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve10); 
			ret_len += ComntStream.getDataSize(para.reserve10);
			
			para.reserve11 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve11); 
			ret_len += ComntStream.getDataSize(para.reserve11);
			
			para.reserve12 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve12); 
			ret_len += ComntStream.getDataSize(para.reserve12);
			
			para.reserve13 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve13); 
			ret_len += ComntStream.getDataSize(para.reserve13);
			
			para.reserve14 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve14); 
			ret_len += ComntStream.getDataSize(para.reserve14);
			
			para.reserve15 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve15); 
			ret_len += ComntStream.getDataSize(para.reserve15);
			
			return ret_len;
		}
	}

	public static class SMARTDB2_FAILB extends SMARTDB2_FAILA{}
	
	public static class SMARTDB2_FAILC extends SMARTDB2_FAILA{}
	
	//[消息相关,请不要随意改动]
	public static class SMARTDB2_FAIL{
		public byte	dynxx		= 0;		//电压逆相序       
		public byte	dlnxx		= 0;    	//电流逆相序       
		public byte	dybph		= 0;    	//电压不平衡       
		public byte	dlbph		= 0;    	//电流不平衡       
		public byte	fzdysy		= 0;    	//辅助电源失压     
		public byte	dd			= 0;    	//掉电               
		public byte	xlcz		= 0;    	//需量超载           
		public byte	zglyscxx	= 0;    	//总功率因数超下限
		
		public byte	dlyzbph		= 0;        //电流严重不平衡   
		public byte	openmeter	= 0;    	//开表盖  
		public byte	opendn		= 0;    	//开端钮盖 	       
		public byte	reserve11	= 0;    	        
		public byte	reserve12	= 0;
		public byte	reserve13	= 0;
		public byte	reserve14	= 0;
		public byte	reserve15	= 0;
		
		public static void setSMARTDB2_FAIL(SMARTDB2_FAIL para, SMARTDB2_FAIL setValue){
			if(setValue == null){
				para = new SMARTDB2_FAIL();
			}else{
				para.dynxx		= setValue.dynxx;
				para.dlnxx		= setValue.dlnxx;
				para.dybph		= setValue.dybph;
				para.dlbph		= setValue.dlbph;
				para.fzdysy		= setValue.fzdysy;
				para.dd			= setValue.dd;
				para.xlcz		= setValue.xlcz;
				para.zglyscxx	= setValue.zglyscxx;
				para.dlyzbph	= setValue.dlyzbph;
				para.openmeter	= setValue.openmeter;
				para.opendn		= setValue.opendn;
				para.reserve11	= setValue.reserve11;
				para.reserve12	= setValue.reserve12;
				para.reserve13	= setValue.reserve13;
				para.reserve14	= setValue.reserve14;
				para.reserve15	= setValue.reserve15;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, SMARTDB2_FAIL para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.dynxx);	
			ret_len += ComntStream.writeStream(byte_vect, para.dlnxx);	
			ret_len += ComntStream.writeStream(byte_vect, para.dybph);	
			ret_len += ComntStream.writeStream(byte_vect, para.dlbph);	
			ret_len += ComntStream.writeStream(byte_vect, para.fzdysy);	
			ret_len += ComntStream.writeStream(byte_vect, para.dd);	
			ret_len += ComntStream.writeStream(byte_vect, para.xlcz);
			ret_len += ComntStream.writeStream(byte_vect, para.zglyscxx);	
			ret_len += ComntStream.writeStream(byte_vect, para.dlyzbph);	
			ret_len += ComntStream.writeStream(byte_vect, para.openmeter);	
			ret_len += ComntStream.writeStream(byte_vect, para.opendn);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve11);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve12);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve13);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve14);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve15);
			
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, SMARTDB2_FAIL para){
			int ret_len   = 0; 
			
			para.dynxx = ComntStream.readStream(byte_vect, offset + ret_len, para.dynxx); 
			ret_len += ComntStream.getDataSize(para.dynxx);
			
			para.dlnxx = ComntStream.readStream(byte_vect, offset + ret_len, para.dlnxx); 
			ret_len += ComntStream.getDataSize(para.dlnxx);
			
			para.dybph = ComntStream.readStream(byte_vect, offset + ret_len, para.dybph); 
			ret_len += ComntStream.getDataSize(para.dybph);
			
			para.dlbph = ComntStream.readStream(byte_vect, offset + ret_len, para.dlbph); 
			ret_len += ComntStream.getDataSize(para.dlbph);
			
			para.fzdysy = ComntStream.readStream(byte_vect, offset + ret_len, para.fzdysy); 
			ret_len += ComntStream.getDataSize(para.fzdysy);
			
			para.dd = ComntStream.readStream(byte_vect, offset + ret_len, para.dd); 
			ret_len += ComntStream.getDataSize(para.dd);
			
			para.xlcz = ComntStream.readStream(byte_vect, offset + ret_len, para.xlcz); 
			ret_len += ComntStream.getDataSize(para.xlcz);
			
			para.zglyscxx = ComntStream.readStream(byte_vect, offset + ret_len, para.zglyscxx); 
			ret_len += ComntStream.getDataSize(para.zglyscxx);
			
			para.dlyzbph = ComntStream.readStream(byte_vect, offset + ret_len, para.dlyzbph); 
			ret_len += ComntStream.getDataSize(para.dlyzbph);
			
			para.openmeter = ComntStream.readStream(byte_vect, offset + ret_len, para.openmeter); 
			ret_len += ComntStream.getDataSize(para.openmeter);
			
			para.opendn = ComntStream.readStream(byte_vect, offset + ret_len, para.opendn); 
			ret_len += ComntStream.getDataSize(para.opendn);
			
			para.reserve11 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve11); 
			ret_len += ComntStream.getDataSize(para.reserve11);
			
			para.reserve12 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve12); 
			ret_len += ComntStream.getDataSize(para.reserve12);
			
			para.reserve13 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve13); 
			ret_len += ComntStream.getDataSize(para.reserve13);
			
			para.reserve14 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve14); 
			ret_len += ComntStream.getDataSize(para.reserve14);
			
			para.reserve15 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve15); 
			ret_len += ComntStream.getDataSize(para.reserve15);
			
			return ret_len;
		}
	}

	//插卡状态字 ??
	//[消息相关,请不要随意改动]
	public static class SMARTDB2_CARDSTATE
	{
		public byte	cardstate	= 0;						//插卡状态
		public byte	reserve2	= 0;
		public byte	reserve3	= 0;
		public byte	reserve4	= 0;
		public byte	reserve5	= 0;
		public byte	reserve6	= 0;
		public byte	reserve7	= 0;
		public byte	reserve8	= 0;
		public byte	reserve9	= 0;			
		public byte	reserve10	= 0;			
		public byte	reserve11	= 0;			
		public byte	reserve12	= 0;			
		public byte	reserve13	= 0;
		public byte	reserve14	= 0;
		public byte	reserve15	= 0;
		
		public static void setSMARTDB2_CARDSTATE(SMARTDB2_CARDSTATE para, SMARTDB2_CARDSTATE setValue){
			if(setValue == null){
				para = new SMARTDB2_CARDSTATE();
			}else{
				para. cardstate = setValue.cardstate;
				para. reserve2	= setValue.reserve2;	
				para. reserve3	= setValue.reserve3;	
				para. reserve4	= setValue.reserve4;	
				para. reserve5	= setValue.reserve5;	
				para. reserve6	= setValue.reserve6;	
				para. reserve7	= setValue.reserve7;	
				para. reserve8	= setValue.reserve8;	
				para. reserve9	= setValue.reserve9;	
				para. reserve10	= setValue.reserve10;
				para. reserve11 = setValue.reserve11;
				para. reserve12	= setValue.reserve12;
				para. reserve13	= setValue.reserve13;
				para. reserve14	= setValue.reserve14;
				para. reserve15	= setValue.reserve15;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, SMARTDB2_CARDSTATE para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.cardstate); 
			ret_len += ComntStream.writeStream(byte_vect, para.reserve2	);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve3	);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve4	);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve5	);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve6	);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve7	);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve8	);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve9	);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve10);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve11); 
			ret_len += ComntStream.writeStream(byte_vect, para.reserve12);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve13);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve14);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve15);	
			return ret_len;
		}
		
		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, SMARTDB2_CARDSTATE para){
			int ret_len   = 0; 
			
			para.cardstate = ComntStream.readStream(byte_vect, offset + ret_len, para.cardstate); 
			ret_len += ComntStream.getDataSize(para.cardstate);
			
			para.reserve2 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve2); 
			ret_len += ComntStream.getDataSize(para.reserve2);
			
			para.reserve3 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve3); 
			ret_len += ComntStream.getDataSize(para.reserve3);
			
			para.reserve4 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve4); 
			ret_len += ComntStream.getDataSize(para.reserve4);
			
			para.reserve5 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve5); 
			ret_len += ComntStream.getDataSize(para.reserve5);
			
			para.reserve6 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve6); 
			ret_len += ComntStream.getDataSize(para.reserve6);
			
			para.reserve7 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve7); 
			ret_len += ComntStream.getDataSize(para.reserve7);
			
			para.reserve8 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve8); 
			ret_len += ComntStream.getDataSize(para.reserve8);
			
			para.reserve9 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve9); 
			ret_len += ComntStream.getDataSize(para.reserve9);
			
			para.reserve10 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve10); 
			ret_len += ComntStream.getDataSize(para.reserve10);
			
			para.reserve11 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve11); 
			ret_len += ComntStream.getDataSize(para.reserve11);
			
			para.reserve12 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve12); 
			ret_len += ComntStream.getDataSize(para.reserve12);
			
			para.reserve13 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve13); 
			ret_len += ComntStream.getDataSize(para.reserve13);
			
			para.reserve14 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve14); 
			ret_len += ComntStream.getDataSize(para.reserve14);
			
			para.reserve15 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve15); 
			ret_len += ComntStream.getDataSize(para.reserve15);
			
			return ret_len;
		}
	}

	public static class SMARTDB2_STATE{
		SMARTDB2_STATE1 dbs1 	= new SMARTDB2_STATE1();
		SMARTDB2_STATE2 dbs2 	= new SMARTDB2_STATE2();
		SMARTDB2_YFF	dbyff 	= new SMARTDB2_YFF();
		SMARTDB2_FAILA  dbfaila = new SMARTDB2_FAILA();
		SMARTDB2_FAILB  dbfailb = new SMARTDB2_FAILB();
		SMARTDB2_FAILC  dbfailc = new SMARTDB2_FAILC();
		SMARTDB2_FAIL   dbfail 	= new SMARTDB2_FAIL();
		SMARTDB2_CARDSTATE dbcardstate = new SMARTDB2_CARDSTATE();
		
		public static void setSMARTDB_STATE(SMARTDB2_STATE para, SMARTDB2_STATE setValue){
			if(setValue == null){
				SMARTDB2_STATE1.setSMARTDB2_STATE1(para.dbs1, null);
				SMARTDB2_STATE2.setSMARTDB2_STATE2(para.dbs2, null);
				SMARTDB2_YFF.setSMARTDB2_YFF(para.dbyff, null);
				SMARTDB2_FAILA.setSMARTDB2_FAIL(para.dbfaila, null);
				SMARTDB2_FAILB.setSMARTDB2_FAIL(para.dbfailb, null);
				SMARTDB2_FAILC.setSMARTDB2_FAIL(para.dbfailc, null);
				SMARTDB2_FAIL.setSMARTDB2_FAIL(para.dbfail, null);
				SMARTDB2_CARDSTATE.setSMARTDB2_CARDSTATE(para.dbcardstate, null);
			}else{
				SMARTDB2_STATE1.setSMARTDB2_STATE1(para.dbs1, setValue.dbs1);
				SMARTDB2_STATE2.setSMARTDB2_STATE2(para.dbs2, setValue.dbs2);
				SMARTDB2_YFF.setSMARTDB2_YFF(para.dbyff, setValue.dbyff);
				SMARTDB2_FAILA.setSMARTDB2_FAIL(para.dbfaila, setValue.dbfaila);
				SMARTDB2_FAILB.setSMARTDB2_FAIL(para.dbfailb, setValue.dbfailb);
				SMARTDB2_FAILC.setSMARTDB2_FAIL(para.dbfailc, setValue.dbfailc);
				SMARTDB2_FAIL.setSMARTDB2_FAIL(para.dbfail, setValue.dbfail);
				SMARTDB2_CARDSTATE.setSMARTDB2_CARDSTATE(para.dbcardstate, setValue.dbcardstate);
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, SMARTDB2_STATE para){
			int ret_len   = 0;
			
			ret_len += SMARTDB2_STATE1.writeStream(byte_vect, para.dbs1);	
			ret_len += SMARTDB2_STATE2.writeStream(byte_vect, para.dbs2);	
			ret_len += SMARTDB2_YFF.writeStream(byte_vect, para.dbyff);	
			ret_len += SMARTDB2_FAILA.writeStream(byte_vect, para.dbfaila);	
			ret_len += SMARTDB2_FAILB.writeStream(byte_vect, para.dbfailb);	
			ret_len += SMARTDB2_FAILC.writeStream(byte_vect, para.dbfailc);	
			ret_len += SMARTDB2_FAIL.writeStream(byte_vect, para.dbfail);
			ret_len += SMARTDB2_CARDSTATE.writeStream(byte_vect, para.dbcardstate);
			
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, SMARTDB2_STATE para){
			int ret_len   = len;
			
			ret_len += SMARTDB2_STATE1.getDataSize(byte_vect, ret_len, para.dbs1);
			ret_len += SMARTDB2_STATE2.getDataSize(byte_vect, ret_len, para.dbs2);
			ret_len += SMARTDB2_YFF.getDataSize(byte_vect, ret_len,  para.dbyff);
			ret_len += SMARTDB2_FAILA.getDataSize(byte_vect, ret_len,  para.dbfaila);
			ret_len += SMARTDB2_FAILB.getDataSize(byte_vect, ret_len,  para.dbfailb);
			ret_len += SMARTDB2_FAILC.getDataSize(byte_vect, ret_len,  para.dbfailc);
			ret_len += SMARTDB2_FAIL.getDataSize(byte_vect, ret_len,  para.dbfail);
			ret_len += SMARTDB2_CARDSTATE.getDataSize(byte_vect, ret_len,  para.dbcardstate);

			return ret_len;
		}
	}
	
	//电表透传返回费率
	public static class SMARTDB2_JTFEERATE extends ESAM2_FEERATE{};			

	//[消息相关,请不要随意改动]
	//预付费相关透传结构
	public static class SMARTDB2_FEIPARA				
	{
		public int		rever_money		= 0;		//退费金额 单位为分
		public int		remain_money	= 0;		//剩余金额 单位为分
		public int		overd_money		= 0;		//透支金额 单位为分
		public int		money_limit		= 0;		//囤积限值	两位小数0.01kwh

		public short	js_ddhh1		= 0;		//结算日期时间	dd--hh
		public short	js_ddhh2		= 0;		//结算日期时间	dd--hh
		public short	js_ddhh3		= 0;		//结算日期时间	dd--hh
		
		public static void setSMARTDB2_FEIPARA(SMARTDB2_FEIPARA para, SMARTDB2_FEIPARA setValue){
			if(setValue == null){
				para = new SMARTDB2_FEIPARA();
			}else{
				para.rever_money  = setValue.rever_money;
				para.remain_money = setValue.remain_money;
				para.overd_money  = setValue.overd_money;
				para.money_limit  = setValue.money_limit;
				para.js_ddhh1 	  = setValue.js_ddhh1;
				para.js_ddhh2 	  = setValue.js_ddhh2;
				para.js_ddhh3     = setValue.js_ddhh3;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, SMARTDB2_FEIPARA para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.rever_money); 
			ret_len += ComntStream.writeStream(byte_vect, para.remain_money);
			ret_len += ComntStream.writeStream(byte_vect, para.overd_money);
			ret_len += ComntStream.writeStream(byte_vect, para.money_limit);
			ret_len += ComntStream.writeStream(byte_vect, para.js_ddhh1	);
			ret_len += ComntStream.writeStream(byte_vect, para.js_ddhh2	);
			ret_len += ComntStream.writeStream(byte_vect, para.js_ddhh3	);
			
			return ret_len;
		}
		
		//是否有参数len ??
		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, SMARTDB2_FEIPARA para){
			int ret_len   = len; 
			
			para.rever_money = ComntStream.readStream(byte_vect, offset + ret_len, para.rever_money); 
			ret_len += ComntStream.getDataSize(para.rever_money);
			
			para.remain_money = ComntStream.readStream(byte_vect, offset + ret_len, para.remain_money); 
			ret_len += ComntStream.getDataSize(para.remain_money);
			
			para.overd_money = ComntStream.readStream(byte_vect, offset + ret_len, para.overd_money); 
			ret_len += ComntStream.getDataSize(para.overd_money);
			
			para.money_limit = ComntStream.readStream(byte_vect, offset + ret_len, para.money_limit); 
			ret_len += ComntStream.getDataSize(para.money_limit);
			
			para.js_ddhh1 = ComntStream.readStream(byte_vect, offset + ret_len, para.js_ddhh1); 
			ret_len += ComntStream.getDataSize(para.js_ddhh1);
			
			para.js_ddhh2 = ComntStream.readStream(byte_vect, offset + ret_len, para.js_ddhh2); 
			ret_len += ComntStream.getDataSize(para.js_ddhh2);
			
			para.js_ddhh3 = ComntStream.readStream(byte_vect, offset + ret_len, para.js_ddhh3); 
			ret_len += ComntStream.getDataSize(para.js_ddhh3);
			
			return ret_len;
		}
		
	}

	//[消息相关,请不要随意改动]
	//预付费密钥即状态字
	public static class SMARTDB2_KEYSTATE				
	{
		public byte	key_num		= 0;								//密钥总条数
		public int	key_state	= 0;								//密钥状态字

		public static void setSMARTDB2_KEYSTATE(SMARTDB2_KEYSTATE para, SMARTDB2_KEYSTATE setValue){
			if(setValue == null){
				para = new SMARTDB2_KEYSTATE();
			}else{
				para.key_num   = setValue.key_num;
				para.key_state = setValue.key_state;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, SMARTDB2_KEYSTATE para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.key_num); 
			ret_len += ComntStream.writeStream(byte_vect, para.key_state);
			
			return ret_len;
		}
		
		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, SMARTDB2_KEYSTATE para){
			int ret_len   = len; 
			
			para.key_num = ComntStream.readStream(byte_vect, offset + ret_len, para.key_num); 
			ret_len += ComntStream.getDataSize(para.key_num);
			
			para.key_state = ComntStream.readStream(byte_vect, offset + ret_len, para.key_state); 
			ret_len += ComntStream.getDataSize(para.key_state);
			
			return ret_len;
		}
		
	};
}