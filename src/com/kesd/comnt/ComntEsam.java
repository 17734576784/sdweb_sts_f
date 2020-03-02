package com.kesd.comnt;

import com.libweb.comnt.*;

public class ComntEsam {
	public static final int	CALL_EASM_IDENTITY		= 1;
	public static final int	CALL_EASM_UPDATE		= 2;
	public static final int	CALL_EASM_CONTROL		= 3;
	public static final int	CALL_EASM_INCREASE		= 4;
	public static final int	CALL_EASM_CHGFEE1		= 5;
	public static final int	CALL_EASM_CHGFEE2		= 6;
	public static final int	CALL_EASM_CHGIMP		= 7;

	public static final int	CALL_EASM_LOOK			= 19;
	public static final int	CALL_EASM_PROC			= 20;

	//智能电表下装密钥
	public static final int	YFF_DY_KEY_AUTHEN	= 1;			//身份认证
	public static final int	YFF_DY_KEY_REMOTE	= 2;			//远程控制
	public static final int	YFF_DY_KEY_PARA		= 3;			//参数更新


	public static final int	EASM_OK					= 0;
	public static final int	EASM_ERR				= -1;
	public static final int	EASM_ERR_LINK			= 200;		//连接加密机失败
	public static final int	EASM_ERR_RAND1			= 201;
	public static final int	EASM_ERR_RAND2			= 202;
	public static final int	EASM_ERR_KEYDIFF		= 203;
	public static final int	EASM_ERR_DATAENCRY		= 204;
	public static final int	EASM_ERR_GETCIPH		= 205;

	public static final int	ESAM_16_STRLEN			= 16;		//描述长度
	public static final int	ESAM_32_STRLEN			= 32;		//描述长度

	//[消息相关,请不要随意改动]
	public static class ESAM_CRYPLINK	{
		public byte		func_id			= 0;						//程序id

		public byte		key_state		= 0;						//密钥状态
		public byte		key_updatetype	= 0;						//更新方式
		public byte		key_identify	= 0;						//标识	
		public byte		key_ver			= 0;						//密钥版本
		public byte		key_id			= 0;						//密钥类型
		public byte		key_clear		= 0;						//清空标志

		public byte[]	div				= new byte[ESAM_32_STRLEN];	//分散因子
		public byte[]	meter_addr		= new byte[ESAM_16_STRLEN];	//表地址
		public byte[]	easm_id			= new byte[ESAM_32_STRLEN];	//easm 序列号
		public byte[]	rand2			= new byte[ESAM_16_STRLEN];	//随机数
		
		public byte		ctrl_type		= 0;						//控制类型	
		public byte		ctrl_ver		= 0;						//控制版本
		public byte		date_flag		= 0;						//日期标志
		public int		effect_date		= 0;						//有效日期
		public int	  	effect_time		= 0;						//有效时间

		public byte[]	user_no			= new byte[ESAM_16_STRLEN];	//户号
		public int   	pay_money		= 0;						//缴费金额 单位分
		public int	  	pay_num			= 0;						//购电次数

		public int  	file_type		= 0;						//文件类型
		public short 	begin_pos		= 0;						//起始位置
		public short 	para_len		= 0;						//数据长度
		public int  	data_type		= 0;						//数据类型
		public byte[]	para_data		= new byte[ESAM_32_STRLEN];	//参数数据
		
		public static void setESAM_CRYPLINK(ESAM_CRYPLINK para, ESAM_CRYPLINK setValue){
			if(setValue == null){
				para = new ESAM_CRYPLINK();
			}else{
				para.func_id		= setValue.func_id;
				para.key_state		= setValue.key_state;
				para.key_updatetype	= setValue.key_updatetype;
				para.key_identify	= setValue.key_identify;
				para.key_ver		= setValue.key_ver;
				para.key_id			= setValue.key_id;
				para.key_clear		= setValue.key_clear;
				for (int i = 0; i < ESAM_32_STRLEN; i++) {
					para.div[i] = setValue.div[i];
					para.easm_id[i] = setValue.easm_id[i];
					para.para_data[i] = setValue.para_data[i];
				}
				for (int i = 0; i < ESAM_16_STRLEN; i++) {
					para.meter_addr[i] = setValue.meter_addr[i];
					para.rand2[i] = setValue.rand2[i];
					para.user_no[i] = setValue.user_no[i];
				}
				para.ctrl_type		= setValue.ctrl_type;
				para.ctrl_ver		= setValue.ctrl_ver;
				para.date_flag		= setValue.date_flag;
				para.effect_date	= setValue.effect_date;
				para.effect_time	= setValue.effect_time;
				para.pay_num		= setValue.pay_num;
				para.file_type		= setValue.file_type;
				para.begin_pos		= setValue.begin_pos;
				para.para_len		= setValue.para_len;
				para.data_type		= setValue.data_type;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, ESAM_CRYPLINK para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.func_id);
			ret_len += ComntStream.writeStream(byte_vect, para.key_state);
			ret_len += ComntStream.writeStream(byte_vect, para.key_updatetype);
			ret_len += ComntStream.writeStream(byte_vect, para.key_identify);
			ret_len += ComntStream.writeStream(byte_vect, para.key_ver);
			ret_len += ComntStream.writeStream(byte_vect, para.key_id);
			ret_len += ComntStream.writeStream(byte_vect, para.key_clear);
			ret_len += ComntStream.writeStream(byte_vect, para.div, 0, para.div.length);
			ret_len += ComntStream.writeStream(byte_vect, para.meter_addr, 0, para.meter_addr.length);
			ret_len += ComntStream.writeStream(byte_vect, para.easm_id, 0, para.easm_id.length);
			ret_len += ComntStream.writeStream(byte_vect, para.rand2, 0, para.rand2.length);
			ret_len += ComntStream.writeStream(byte_vect, para.ctrl_type);
			ret_len += ComntStream.writeStream(byte_vect, para.ctrl_ver);
			ret_len += ComntStream.writeStream(byte_vect, para.date_flag);
			ret_len += ComntStream.writeStream(byte_vect, para.effect_date);
			ret_len += ComntStream.writeStream(byte_vect, para.effect_time);
			ret_len += ComntStream.writeStream(byte_vect, para.user_no, 0, para.user_no.length);
			ret_len += ComntStream.writeStream(byte_vect, para.pay_money);
			ret_len += ComntStream.writeStream(byte_vect, para.pay_num);
			ret_len += ComntStream.writeStream(byte_vect, para.file_type);
			ret_len += ComntStream.writeStream(byte_vect, para.begin_pos);
			ret_len += ComntStream.writeStream(byte_vect, para.para_len);
			ret_len += ComntStream.writeStream(byte_vect, para.data_type);
			ret_len += ComntStream.writeStream(byte_vect, para.para_data, 0, para.para_data.length);
			
			return ret_len;
		}
		
		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, ESAM_CRYPLINK para){
			
			int ret_len = len;
			
			para.func_id = ComntStream.readStream(byte_vect, offset + ret_len, para.func_id); 
			ret_len += ComntStream.getDataSize(para.func_id);
			
			para.key_state = ComntStream.readStream(byte_vect, offset + ret_len, para.key_state); 
			ret_len += ComntStream.getDataSize(para.key_state);
			
			para.key_updatetype = ComntStream.readStream(byte_vect, offset + ret_len, para.key_updatetype); 
			ret_len += ComntStream.getDataSize(para.key_updatetype);
			
			para.key_identify = ComntStream.readStream(byte_vect, offset + ret_len, para.key_identify); 
			ret_len += ComntStream.getDataSize(para.key_identify);
			
			para.key_ver = ComntStream.readStream(byte_vect, offset + ret_len, para.key_ver); 
			ret_len += ComntStream.getDataSize(para.key_ver);
			
			para.key_id = ComntStream.readStream(byte_vect, offset + ret_len, para.key_id); 
			ret_len += ComntStream.getDataSize(para.key_id);
			
			para.key_clear = ComntStream.readStream(byte_vect, offset + ret_len, para.key_clear); 
			ret_len += ComntStream.getDataSize(para.key_clear);
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.div, 0, para.div.length);
			ret_len += ComntStream.getDataSize(para.div[0]) * para.div.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.meter_addr, 0, para.meter_addr.length);
			ret_len += ComntStream.getDataSize(para.meter_addr[0]) * para.meter_addr.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.easm_id, 0, para.easm_id.length);
			ret_len += ComntStream.getDataSize(para.easm_id[0]) * para.easm_id.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.rand2, 0, para.rand2.length);
			ret_len += ComntStream.getDataSize(para.rand2[0]) * para.rand2.length;
			
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
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.user_no, 0, para.user_no.length);
			ret_len += ComntStream.getDataSize(para.user_no[0]) * para.user_no.length;
			
			para.pay_money = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_money); 
			ret_len += ComntStream.getDataSize(para.pay_money);
			
			para.pay_num = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_num); 
			ret_len += ComntStream.getDataSize(para.pay_num);
			
			para.file_type = ComntStream.readStream(byte_vect, offset + ret_len, para.file_type); 
			ret_len += ComntStream.getDataSize(para.file_type);
			
			para.begin_pos = ComntStream.readStream(byte_vect, offset + ret_len, para.begin_pos); 
			ret_len += ComntStream.getDataSize(para.begin_pos);
			
			para.para_len = ComntStream.readStream(byte_vect, offset + ret_len, para.para_len); 
			ret_len += ComntStream.getDataSize(para.para_len);
			
			para.data_type = ComntStream.readStream(byte_vect, offset + ret_len, para.data_type); 
			ret_len += ComntStream.getDataSize(para.data_type);
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.para_data, 0, para.para_data.length);
			ret_len += ComntStream.getDataSize(para.para_data[0]) * para.para_data.length;
			
			return ret_len;
		}
	}
	
	

	//[消息相关,请不要随意改动]
	
	public static class ESAM_LOOKSTATE{
		public int 		remain_money	= 0;					//剩余金额 单位分
		public int		buy_num			= 0;					//购电次数
		public byte		key_state		= 0;					//密钥状态
		public byte		key_updatetype	= 0;					//更新方式
		public byte		key_chgno		= 0;					//更新条数
		public byte		key_ver			= 0;					//密钥版本
		
		public static void setESAM_LOOKSTATE(ESAM_LOOKSTATE para, ESAM_LOOKSTATE setValue){
			if(setValue == null){
				para = new ESAM_LOOKSTATE();
			}else{
				para.remain_money	= setValue.remain_money;
				para.buy_num		= setValue.buy_num;
				para.key_state		= setValue.key_state;
				para.key_updatetype	= setValue.key_updatetype;
				para.key_chgno		= setValue.key_chgno;
				para.key_ver		= setValue.key_ver;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, ESAM_LOOKSTATE para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.remain_money);	
			ret_len += ComntStream.writeStream(byte_vect, para.buy_num);	
			ret_len += ComntStream.writeStream(byte_vect, para.key_state);	
			ret_len += ComntStream.writeStream(byte_vect, para.key_updatetype);
			ret_len += ComntStream.writeStream(byte_vect, para.key_chgno);	
			ret_len += ComntStream.writeStream(byte_vect, para.key_ver);
			
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, ESAM_LOOKSTATE para){
			int ret_len   = len;
			
			para.remain_money = ComntStream.readStream(byte_vect, offset + ret_len, para.remain_money); 
			ret_len += ComntStream.getDataSize(para.remain_money);
			
			para.buy_num = ComntStream.readStream(byte_vect, offset + ret_len, para.buy_num); 
			ret_len += ComntStream.getDataSize(para.buy_num);
			
			para.key_state = ComntStream.readStream(byte_vect, offset + ret_len, para.key_state); 
			ret_len += ComntStream.getDataSize(para.key_state);
			
			para.key_updatetype = ComntStream.readStream(byte_vect, offset + ret_len, para.key_updatetype); 
			ret_len += ComntStream.getDataSize(para.key_updatetype);
			
			para.key_chgno = ComntStream.readStream(byte_vect, offset + ret_len, para.key_chgno); 
			ret_len += ComntStream.getDataSize(para.key_chgno);
			
			para.key_ver = ComntStream.readStream(byte_vect, offset + ret_len, para.key_ver); 
			ret_len += ComntStream.getDataSize(para.key_ver);
			
			return ret_len;
		}
		
	}
	
	
	//[消息相关,请不要随意改动]
	public static class ESAM_PARAINFO{
		public byte		user_type	= 0;						//01 单费率 02 复费率
		public byte		bit_update	= 0;						//参数更新标志位
		public int		chg_date	= 0;						//分时日期 YY-MM-DD
		public int		chg_time	= 0;						//分时时间  HH-MI-00
		public int		alarm_val1	= 0;						//单位为分
		public int		alarm_val2	= 0;						//单位为分
		public int		ct			= 0;
		public int		pt			= 0;
		public byte		meterno[]	= new byte[ESAM_16_STRLEN];	//表号
		public byte		userno[]	= new byte[ESAM_16_STRLEN];	//客户编号
		
		public static void setESAM_PARAINFO(ESAM_PARAINFO para, ESAM_PARAINFO setValue){
			if(setValue == null){
				para = new ESAM_PARAINFO();
			}else{
				para.user_type		= setValue.user_type;
				para.bit_update			= setValue.bit_update;
				para.chg_date	= setValue.chg_date;
				para.chg_time	= setValue.chg_time;
				para.alarm_val1	= setValue.alarm_val1;
				para.alarm_val2	= setValue.alarm_val2;
				para.ct	= setValue.ct;
				para.pt	= setValue.pt;
				
				for (int i = 0; i < ESAM_16_STRLEN; i++) {
					para.meterno[i] = setValue.meterno[i];
					para.userno[i] = setValue.userno[i];
				}
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, ESAM_PARAINFO para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.user_type);	
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

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, ESAM_PARAINFO para){
			int ret_len   = len;
			
			para.user_type = ComntStream.readStream(byte_vect, offset + ret_len, para.user_type); 
			ret_len += ComntStream.getDataSize(para.user_type);
			
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
	public static class ESAM_FEERATE{
		public double	rated_z = 0;							//总费率
		public double	ratef_j = 0;							//尖费率
		public double	ratef_f = 0;							//峰费率	
		public double	ratef_p = 0;							//平费率
		public double	ratef_g = 0;							//谷费率	
		
		public static void setESAM_FEERATE(ESAM_FEERATE para, ESAM_FEERATE setValue){
			if(setValue == null){
				para = new ESAM_FEERATE();
			}else{
				para.rated_z	= setValue.rated_z;
				para.ratef_j	= setValue.ratef_j;
				para.ratef_f	= setValue.ratef_f;
				para.ratef_p	= setValue.ratef_p;
				para.ratef_g	= setValue.ratef_g;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, ESAM_FEERATE para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.rated_z);	
			ret_len += ComntStream.writeStream(byte_vect, para.ratef_j);	
			ret_len += ComntStream.writeStream(byte_vect, para.ratef_f);	
			ret_len += ComntStream.writeStream(byte_vect, para.ratef_p);
			ret_len += ComntStream.writeStream(byte_vect, para.ratef_g);	
			
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, ESAM_FEERATE para){
			int ret_len   = len;
			
			para.rated_z = ComntStream.readStream(byte_vect, offset + ret_len, para.rated_z); 
			ret_len += ComntStream.getDataSize(para.rated_z);
			
			para.ratef_j = ComntStream.readStream(byte_vect, offset + ret_len, para.ratef_j); 
			ret_len += ComntStream.getDataSize(para.ratef_j);
			
			para.ratef_f = ComntStream.readStream(byte_vect, offset + ret_len, para.ratef_f); 
			ret_len += ComntStream.getDataSize(para.ratef_f);
			
			para.ratef_p = ComntStream.readStream(byte_vect, offset + ret_len, para.ratef_p); 
			ret_len += ComntStream.getDataSize(para.ratef_p);
			
			para.ratef_g = ComntStream.readStream(byte_vect, offset + ret_len, para.ratef_g); 
			ret_len += ComntStream.getDataSize(para.ratef_g);
			
			return ret_len;
		}
	}
	
	
	
	
	//运行信息
	public static class ESAM_RUNSTATE{
		public byte		user_type		= 0;						//01 单费率 02 复费率 
		public int		ct				= 0;
		public int		pt				= 0;
		public byte		meter_no[]		= new byte[ESAM_16_STRLEN];	//表号
		public byte		user_no[]		= new byte[ESAM_16_STRLEN];	//客户编号
		public int		remain_money	= 0;						//剩余金额 单位为分
		public int		pay_num			= 0;						//购电次数
		public int		overd_money		= 0;						//透支金额
		public byte		key_state		= 0;						//密钥状态
		public byte		key_updatetype	= 0;						//更新方式
		public byte		key_identify	= 0;						//标识	
		public byte		key_ver			= 0;						//密钥版本
		public int		errin_num		= 0;						//非法插卡次数
		public int		reback_date		= 0;						//返写日期	YY-MM-DD
		public int		reback_time		= 0;						//HH-MI-00
		
		public static void setESAM_RUNSTATE(ESAM_RUNSTATE para, ESAM_RUNSTATE setValue){
			if(setValue == null){
				para = new ESAM_RUNSTATE();
			}else{
				para.user_type		= setValue.user_type;
				para.ct				= setValue.ct;
				para.pt	= setValue.pt;
				for (int i = 0; i < ESAM_16_STRLEN; i++) {
					para.meter_no[i]= setValue.meter_no[i];
					para.user_no[i] = setValue.user_no[i];
				}
				para.remain_money	= setValue.remain_money;
				para.pay_num		= setValue.pay_num;
				para.overd_money	= setValue.overd_money;
				para.key_state		= setValue.key_state;
				para.key_updatetype	= setValue.key_updatetype;
				para.key_identify	= setValue.key_identify;
				para.key_ver		= setValue.key_ver;
				para.errin_num		= setValue.errin_num;
				para.reback_date	= setValue.reback_date;
				para.reback_time	= setValue.reback_time;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, ESAM_RUNSTATE para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.user_type);
			ret_len += ComntStream.writeStream(byte_vect, para.ct);
			ret_len += ComntStream.writeStream(byte_vect, para.pt);
			ret_len += ComntStream.writeStream(byte_vect, para.meter_no, 0, para.meter_no.length);
			ret_len += ComntStream.writeStream(byte_vect, para.user_no, 0, para.user_no.length);
			ret_len += ComntStream.writeStream(byte_vect, para.remain_money);	
			ret_len += ComntStream.writeStream(byte_vect, para.pay_num);	
			ret_len += ComntStream.writeStream(byte_vect, para.overd_money);	
			ret_len += ComntStream.writeStream(byte_vect, para.key_state);
			ret_len += ComntStream.writeStream(byte_vect, para.key_updatetype);
			ret_len += ComntStream.writeStream(byte_vect, para.key_identify);
			ret_len += ComntStream.writeStream(byte_vect, para.key_ver);
			ret_len += ComntStream.writeStream(byte_vect, para.errin_num);
			ret_len += ComntStream.writeStream(byte_vect, para.reback_date);
			ret_len += ComntStream.writeStream(byte_vect, para.reback_time);
			
			
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, ESAM_RUNSTATE para){
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
			
			para.key_state = ComntStream.readStream(byte_vect, offset + ret_len, para.key_state); 
			ret_len += ComntStream.getDataSize(para.key_state);
			
			para.key_updatetype = ComntStream.readStream(byte_vect, offset + ret_len, para.key_updatetype); 
			ret_len += ComntStream.getDataSize(para.key_updatetype);
			
			para.key_identify = ComntStream.readStream(byte_vect, offset + ret_len, para.key_identify); 
			ret_len += ComntStream.getDataSize(para.key_identify);
			
			para.key_ver = ComntStream.readStream(byte_vect, offset + ret_len, para.key_ver); 
			ret_len += ComntStream.getDataSize(para.key_ver);
			
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
	public static class SMARTDB_STATE1{
		public byte	reserve1	= 0;						//保留
		public byte	xljsfs		= 0;							//需量积算方式
		public byte	szdc		= 0;							//时钟电池
		public byte	tdcbdc		= 0;							//停电抄表电池
		public byte	ygglfx		= 0;							//有功功率方向
		public byte	wgglfx		= 0;		 		 		 	//无功功率方向
		public byte	reserve6	= 0;
		public byte	reserve7	= 0;
		public byte	reserve8	= 0;
		public byte	myzt		= 0;							//密钥状态
		public byte	sydl		= 0;							//剩余电量
		public byte	esamzt		= 0;							//esam状态
		public byte	iccard		= 0;							//IC卡座	
		public byte	reserve13	= 0;
		public byte	reserve14	= 0;
		public byte	reserve15	= 0;
		
		public static void setSMARTDB_STATE1(SMARTDB_STATE1 para, SMARTDB_STATE1 setValue){
			if(setValue == null){
				para = new SMARTDB_STATE1();
			}else{
				para.reserve1	= setValue.reserve1;
				para.xljsfs		= setValue.xljsfs;
				para.szdc		= setValue.szdc;
				para.tdcbdc		= setValue.tdcbdc;
				para.ygglfx		= setValue.ygglfx;
				para.wgglfx		= setValue.wgglfx;
				para.reserve6	= setValue.reserve6;
				para.reserve7	= setValue.reserve7;
				para.reserve8	= setValue.reserve8;
				para.myzt		= setValue.myzt;
				para.sydl		= setValue.sydl;
				para.esamzt		= setValue.esamzt;
				para.iccard		= setValue.iccard;
				para.reserve13	= setValue.reserve13;
				para.reserve14	= setValue.reserve14;
				para.reserve15	= setValue.reserve15;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, SMARTDB_STATE1 para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.reserve1);	
			ret_len += ComntStream.writeStream(byte_vect, para.xljsfs);	
			ret_len += ComntStream.writeStream(byte_vect, para.szdc);	
			ret_len += ComntStream.writeStream(byte_vect, para.tdcbdc);	
			ret_len += ComntStream.writeStream(byte_vect, para.ygglfx);	
			ret_len += ComntStream.writeStream(byte_vect, para.wgglfx);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve6);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve7);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve8);	
			ret_len += ComntStream.writeStream(byte_vect, para.myzt);	
			ret_len += ComntStream.writeStream(byte_vect, para.sydl);
			ret_len += ComntStream.writeStream(byte_vect, para.esamzt);	
			ret_len += ComntStream.writeStream(byte_vect, para.iccard);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve13);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve14);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve15);
			
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, SMARTDB_STATE1 para){
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
			
			para.reserve8 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve8); 
			ret_len += ComntStream.getDataSize(para.reserve8);
			
			para.myzt = ComntStream.readStream(byte_vect, offset + ret_len, para.myzt); 
			ret_len += ComntStream.getDataSize(para.myzt);
			
			para.sydl = ComntStream.readStream(byte_vect, offset + ret_len, para.sydl); 
			ret_len += ComntStream.getDataSize(para.sydl);
			
			para.esamzt = ComntStream.readStream(byte_vect, offset + ret_len, para.esamzt); 
			ret_len += ComntStream.getDataSize(para.esamzt);
			
			para.iccard = ComntStream.readStream(byte_vect, offset + ret_len, para.iccard); 
			ret_len += ComntStream.getDataSize(para.iccard);
			
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
	public static class SMARTDB_STATE2{
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
		
		public static void setSMARTDB_STATE2(SMARTDB_STATE2 para, SMARTDB_STATE2 setValue){
			if(setValue == null){
				para = new SMARTDB_STATE2();
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
		
		public static int writeStream(ComntVector.ByteVector byte_vect, SMARTDB_STATE2 para){
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

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, SMARTDB_STATE2 para){
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

	public static class SMARTDB_YFF{
		public byte		dqyxsd	= 0;							//当前运行时段
		public byte		gdfs	= 0;							//供电方式
		public byte		bcyx	= 0;							//编程允许
		public byte		jdqzt	= 0;							//继电器状态
		public byte		dqyxsq	= 0;							//当前运行时区
		public byte		jdqmlzt	= 0;						//继电器命令状态
		public byte		ytzbjzt	= 0;						//预跳闸报警状态
		public byte		dnblx	= 0;							//电能表类型
		public byte		dqyxfldj= 0;						//当前运行费率电价
		public byte		dqjt	= 0;							//当前阶梯
		//byte		reserve12;
		public byte		protect	= 0;						//保电状态 扩展
		public byte		reserve13= 0;
		public byte		reserve14= 0;
		//byte		reserve15;
		public byte		keytype;						//密钥状态
		
		public static void setSMARTDB_YFF(SMARTDB_YFF para, SMARTDB_YFF setValue){
			if(setValue == null){
				para = new SMARTDB_YFF();
			}else{
				para.dqyxsd		= setValue.dqyxsd;
				para.gdfs		= setValue.gdfs;
				para.bcyx		= setValue.bcyx;
				para.jdqzt		= setValue.jdqzt;
				para.dqyxsq		= setValue.dqyxsq;
				para.jdqmlzt	= setValue.jdqmlzt;
				para.ytzbjzt	= setValue.ytzbjzt;
				para.dnblx		= setValue.dnblx;
				para.dqyxfldj	= setValue.dqyxfldj;
				para.dqjt		= setValue.dqjt;
				para.protect	= setValue.protect;
				para.reserve13	= setValue.reserve13;
				para.reserve14	= setValue.reserve14;
				para.keytype	= setValue.keytype;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, SMARTDB_YFF para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.dqyxsd);	
			ret_len += ComntStream.writeStream(byte_vect, para.gdfs);	
			ret_len += ComntStream.writeStream(byte_vect, para.bcyx);	
			ret_len += ComntStream.writeStream(byte_vect, para.jdqzt);	
			ret_len += ComntStream.writeStream(byte_vect, para.dqyxsq);	
			ret_len += ComntStream.writeStream(byte_vect, para.jdqmlzt);	
			ret_len += ComntStream.writeStream(byte_vect, para.ytzbjzt);
			ret_len += ComntStream.writeStream(byte_vect, para.dnblx);	
			ret_len += ComntStream.writeStream(byte_vect, para.dqyxfldj);	
			ret_len += ComntStream.writeStream(byte_vect, para.dqjt);	
			ret_len += ComntStream.writeStream(byte_vect, para.protect);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve13);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve14);
			ret_len += ComntStream.writeStream(byte_vect, para.keytype);
			
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, SMARTDB_YFF para){
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
			
			para.dqyxfldj = ComntStream.readStream(byte_vect, offset + ret_len, para.dqyxfldj); 
			ret_len += ComntStream.getDataSize(para.dqyxfldj);
			
			para.dqjt = ComntStream.readStream(byte_vect, offset + ret_len, para.dqjt); 
			ret_len += ComntStream.getDataSize(para.dqjt);
			
			para.protect = ComntStream.readStream(byte_vect, offset + ret_len, para.protect); 
			ret_len += ComntStream.getDataSize(para.protect);
			
			para.reserve13 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve13); 
			ret_len += ComntStream.getDataSize(para.reserve13);
			
			para.reserve14 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve14); 
			ret_len += ComntStream.getDataSize(para.reserve14);
			
			para.keytype = ComntStream.readStream(byte_vect, offset + ret_len, para.keytype); 
			ret_len += ComntStream.getDataSize(para.keytype);
			
			return ret_len;
		}
	}
	
	
	
	
	
//	//[消息相关,请不要随意改动]
	public static class SMARTDB_FAILA{
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
		
		public static void setSMARTDB_FAILA(SMARTDB_FAILA para, SMARTDB_FAILA setValue){
			if(setValue == null){
				para = new SMARTDB_FAILA();
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
		
		public static int writeStream(ComntVector.ByteVector byte_vect, SMARTDB_FAILA para){
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

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, SMARTDB_FAILA para){
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

	
	
	
	//[消息相关,请不要随意改动]
	public static class SMARTDB_FAIL{
		public byte	dynxx		= 0;
		public byte	dlnxx		= 0;
		public byte	dybph		= 0;
		public byte	dlbph		= 0;
		public byte	fzdysy		= 0;
		public byte	dd			= 0;
		public byte	xlcz		= 0;
		public byte	zglyscxx	= 0;
		public byte	dlyzbph		= 0;
		public byte	reserve9	= 0;
		public byte	reserve10	= 0;
		public byte	reserve11	= 0;
		public byte	reserve12	= 0;
		public byte	reserve13	= 0;
		public byte	reserve14	= 0;
		public byte	reserve15	= 0;
		
		public static void setSMARTDB_FAIL(SMARTDB_FAIL para, SMARTDB_FAIL setValue){
			if(setValue == null){
				para = new SMARTDB_FAIL();
			}else{
				para.dynxx			= setValue.dynxx;
				para.dlnxx			= setValue.dlnxx;
				para.dybph			= setValue.dybph;
				para.dlbph			= setValue.dlbph;
				para.fzdysy			= setValue.fzdysy;
				para.dd			= setValue.dd;
				para.xlcz		= setValue.xlcz;
				para.zglyscxx			= setValue.zglyscxx;
				para.dlyzbph			= setValue.dlyzbph;
				para.reserve9	= setValue.reserve9;
				para.reserve10	= setValue.reserve10;
				para.reserve11	= setValue.reserve11;
				para.reserve12	= setValue.reserve12;
				para.reserve13	= setValue.reserve13;
				para.reserve14	= setValue.reserve14;
				para.reserve15	= setValue.reserve15;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, SMARTDB_FAIL para){
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
			ret_len += ComntStream.writeStream(byte_vect, para.reserve9);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve10);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve11);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve12);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve13);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve14);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve15);
			
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, SMARTDB_FAIL para){
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

	public static class SMARTDB_FAILB extends SMARTDB_FAILA{}
	
	public static class SMARTDB_FAILC extends SMARTDB_FAILA{}
	
	
	public static class SMARTDB_STATE{
		SMARTDB_STATE1 dbs1 	= new SMARTDB_STATE1();
		SMARTDB_STATE2 dbs2 	= new SMARTDB_STATE2();
		SMARTDB_YFF	   dbyff 	= new SMARTDB_YFF();
		SMARTDB_FAILA  dbfaila 	= new SMARTDB_FAILA();
		SMARTDB_FAILB  dbfailb 	= new SMARTDB_FAILB();
		SMARTDB_FAILC  dbfailc 	= new SMARTDB_FAILC();
		SMARTDB_FAIL   dbfail 	= new SMARTDB_FAIL();
		
		public static void setSMARTDB_STATE(SMARTDB_STATE para, SMARTDB_STATE setValue){
			if(setValue == null){
				SMARTDB_STATE1.setSMARTDB_STATE1(para.dbs1, null);
				SMARTDB_STATE2.setSMARTDB_STATE2(para.dbs2, null);
				SMARTDB_YFF.setSMARTDB_YFF(para.dbyff, null);
				SMARTDB_FAILA.setSMARTDB_FAILA(para.dbfaila, null);
				SMARTDB_FAILB.setSMARTDB_FAILA(para.dbfailb, null);
				SMARTDB_FAILC.setSMARTDB_FAILA(para.dbfailc, null);
				SMARTDB_FAIL.setSMARTDB_FAIL(para.dbfail, null);
			}else{
				SMARTDB_STATE1.setSMARTDB_STATE1(para.dbs1, setValue.dbs1);
				SMARTDB_STATE2.setSMARTDB_STATE2(para.dbs2, setValue.dbs2);
				SMARTDB_YFF.setSMARTDB_YFF(para.dbyff, setValue.dbyff);
				SMARTDB_FAILA.setSMARTDB_FAILA(para.dbfaila, setValue.dbfaila);
				SMARTDB_FAILB.setSMARTDB_FAILA(para.dbfailb, setValue.dbfailb);
				SMARTDB_FAILC.setSMARTDB_FAILA(para.dbfailc, setValue.dbfailc);
				SMARTDB_FAIL.setSMARTDB_FAIL(para.dbfail, setValue.dbfail);
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, SMARTDB_STATE para){
			int ret_len   = 0;
			
			ret_len += SMARTDB_STATE1.writeStream(byte_vect, para.dbs1);	
			ret_len += SMARTDB_STATE2.writeStream(byte_vect, para.dbs2);	
			ret_len += SMARTDB_YFF.writeStream(byte_vect, para.dbyff);	
			ret_len += SMARTDB_FAILA.writeStream(byte_vect, para.dbfaila);	
			ret_len += SMARTDB_FAILB.writeStream(byte_vect, para.dbfailb);	
			ret_len += SMARTDB_FAILC.writeStream(byte_vect, para.dbfailc);	
			ret_len += SMARTDB_FAIL.writeStream(byte_vect, para.dbfail);
			
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, SMARTDB_STATE para){
			int ret_len   = len;
			
			ret_len += SMARTDB_STATE1.getDataSize(byte_vect, ret_len, para.dbs1);
			ret_len += SMARTDB_STATE2.getDataSize(byte_vect, ret_len, para.dbs2);
			ret_len += SMARTDB_YFF.getDataSize(byte_vect, ret_len,  para.dbyff);
			ret_len += SMARTDB_FAILA.getDataSize(byte_vect, ret_len,  para.dbfaila);
			ret_len += SMARTDB_FAILB.getDataSize(byte_vect, ret_len,  para.dbfailb);
			ret_len += SMARTDB_FAILC.getDataSize(byte_vect, ret_len,  para.dbfailc);
			ret_len += SMARTDB_FAIL.getDataSize(byte_vect, ret_len,  para.dbfail);

			
			return ret_len;
		}
	}
	

}