package com.kesd.common;

import com.libweb.comnt.ComntStream;
import com.libweb.comnt.ComntVector;

import com.kesd.comnt.ComntMsg;

public class YFFDef {
	
	/**
	 * 计费表底-[消息相关,请不要随意改动]
	 */
	public static class YFF_DBBD{
		public int			rtu_id	= 0;							//终端编号
		public short		mp_id	= 0;
		public int			date	= 0;
		public int			time	= 0;
		
		public double		bd_zy	= 0;							//正有
		public double		bd_zyj	= 0;							//尖	
		public double		bd_zyf	= 0;							//峰
		public double		bd_zyp	= 0;							//平		
		public double		bd_zyg	= 0;							//谷
		public double		bd_fy	= 0;							//反有
		public double		bd_fyj	= 0;							//尖	
		public double		bd_fyf	= 0;							//峰
		public double		bd_fyp	= 0;							//平		
		public double		bd_fyg	= 0;							//谷
		
		public double		bd_zw	= 0;							//正向无功
		public double		bd_fw	= 0;							//反向无功
		
		public static void setYFF_DBBD(YFF_DBBD para, YFF_DBBD setValue){
			if(setValue == null){
				para = new YFF_DBBD();
			}else{
				para.rtu_id		= setValue.rtu_id;
				para.mp_id		= setValue.mp_id;
				para.date		= setValue.date;
				para.time		= setValue.time;
				para.bd_zy		= setValue.bd_zy;
				para.bd_zyj		= setValue.bd_zyj;
				para.bd_zyf		= setValue.bd_zyf;
				para.bd_zyp		= setValue.bd_zyp;
				para.bd_zyg		= setValue.bd_zyg;
				para.bd_fy		= setValue.bd_fy;
				para.bd_fyj		= setValue.bd_fyj;
				para.bd_fyf		= setValue.bd_fyf;
				para.bd_fyp		= setValue.bd_fyp;
				para.bd_fyg		= setValue.bd_fyg;
				
				para.bd_zw      = setValue.bd_zw;
				para.bd_fw      = setValue.bd_fw;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, YFF_DBBD para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.rtu_id);
			ret_len += ComntStream.writeStream(byte_vect, para.mp_id);
			ret_len += ComntStream.writeStream(byte_vect, para.date);
			ret_len += ComntStream.writeStream(byte_vect, para.time);
			ret_len += ComntStream.writeStream(byte_vect, para.bd_zy);
			ret_len += ComntStream.writeStream(byte_vect, para.bd_zyj);
			ret_len += ComntStream.writeStream(byte_vect, para.bd_zyf);
			ret_len += ComntStream.writeStream(byte_vect, para.bd_zyp);
			ret_len += ComntStream.writeStream(byte_vect, para.bd_zyg);
			ret_len += ComntStream.writeStream(byte_vect, para.bd_fy);
			ret_len += ComntStream.writeStream(byte_vect, para.bd_fyj);
			ret_len += ComntStream.writeStream(byte_vect, para.bd_fyf);
			ret_len += ComntStream.writeStream(byte_vect, para.bd_fyp);
			ret_len += ComntStream.writeStream(byte_vect, para.bd_fyg);
			
			ret_len += ComntStream.writeStream(byte_vect, para.bd_zw);
			ret_len += ComntStream.writeStream(byte_vect, para.bd_fw);			
			
			return ret_len;
		}
		
		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFF_DBBD para){
			
			int ret_len = len;
			
			para.rtu_id = ComntStream.readStream(byte_vect, offset + ret_len, para.rtu_id); 
			ret_len += ComntStream.getDataSize(para.rtu_id);
			
			para.mp_id = ComntStream.readStream(byte_vect, offset + ret_len, para.mp_id); 
			ret_len += ComntStream.getDataSize(para.mp_id);
			
			para.date = ComntStream.readStream(byte_vect, offset + ret_len, para.date); 
			ret_len += ComntStream.getDataSize(para.date);
			
			para.time = ComntStream.readStream(byte_vect, offset + ret_len, para.time); 
			ret_len += ComntStream.getDataSize(para.time);
			
			para.bd_zy = ComntStream.readStream(byte_vect, offset + ret_len, para.bd_zy); 
			ret_len += ComntStream.getDataSize(para.bd_zy);
			
			para.bd_zyj = ComntStream.readStream(byte_vect, offset + ret_len, para.bd_zyj); 
			ret_len += ComntStream.getDataSize(para.bd_zyj);
			
			para.bd_zyf = ComntStream.readStream(byte_vect, offset + ret_len, para.bd_zyf); 
			ret_len += ComntStream.getDataSize(para.bd_zyf);
			
			para.bd_zyp = ComntStream.readStream(byte_vect, offset + ret_len, para.bd_zyp); 
			ret_len += ComntStream.getDataSize(para.bd_zyp);
			
			para.bd_zyg = ComntStream.readStream(byte_vect, offset + ret_len, para.bd_zyg); 
			ret_len += ComntStream.getDataSize(para.bd_zyg);
			
			para.bd_fy = ComntStream.readStream(byte_vect, offset + ret_len, para.bd_fy); 
			ret_len += ComntStream.getDataSize(para.bd_fy);
			
			para.bd_fyj = ComntStream.readStream(byte_vect, offset + ret_len, para.bd_fyj); 
			ret_len += ComntStream.getDataSize(para.bd_fyj);

			para.bd_fyf = ComntStream.readStream(byte_vect, offset + ret_len, para.bd_fyf); 
			ret_len += ComntStream.getDataSize(para.bd_fyf);

			para.bd_fyp = ComntStream.readStream(byte_vect, offset + ret_len, para.bd_fyp); 
			ret_len += ComntStream.getDataSize(para.bd_fyp);

			para.bd_fyg = ComntStream.readStream(byte_vect, offset + ret_len, para.bd_fyg); 
			ret_len += ComntStream.getDataSize(para.bd_fyg);

			para.bd_zw = ComntStream.readStream(byte_vect, offset + ret_len, para.bd_zw); 
			ret_len += ComntStream.getDataSize(para.bd_zw);

			para.bd_fw = ComntStream.readStream(byte_vect, offset + ret_len, para.bd_fw); 
			ret_len += ComntStream.getDataSize(para.bd_fw);

			return ret_len;
		}		
	}
	
	
	/**
	 * 缴费信息--[消息相关,请不要随意改动]
	 */
	public static class YFF_PAYMONEY{
		public double		pay_money 	= 0;					//缴费金额
		public double		othjs_money = 0;					//结算金额(其它系统)
		public double		zb_money 	= 0;					//追补金额	
		public double		all_money 	= 0;					//总金额
		
		public static void setYFF_PAYMONEY(YFF_PAYMONEY yff_pm, YFF_PAYMONEY setValue){
			if(setValue == null){
				yff_pm = new YFF_PAYMONEY();
			}else{
				yff_pm.pay_money	= setValue.pay_money;
				yff_pm.othjs_money 	= setValue.othjs_money;
				yff_pm.zb_money 	= setValue.zb_money;
				yff_pm.all_money 	= setValue.all_money;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, YFF_PAYMONEY para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.pay_money);
			ret_len += ComntStream.writeStream(byte_vect, para.othjs_money);
			ret_len += ComntStream.writeStream(byte_vect, para.zb_money);
			ret_len += ComntStream.writeStream(byte_vect, para.all_money);
			
			return ret_len;
		}
		
		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFF_PAYMONEY para){
			int ret_len = len;
			
			para.pay_money = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_money); 
			ret_len += ComntStream.getDataSize(para.pay_money);
			
			para.othjs_money = ComntStream.readStream(byte_vect, offset + ret_len, para.othjs_money); 
			ret_len += ComntStream.getDataSize(para.othjs_money);
			
			para.zb_money = ComntStream.readStream(byte_vect, offset + ret_len, para.zb_money); 
			ret_len += ComntStream.getDataSize(para.zb_money);
			
			para.all_money = ComntStream.readStream(byte_vect, offset + ret_len, para.all_money); 
			ret_len += ComntStream.getDataSize(para.all_money);
			
			return ret_len;
		}
		
	}
	
	
	/**
	 * 换表信息-[消息相关,请不要随意改动]
	 */
	public static class YFF_CHGMETERRATE{
		public short		id					= 0;			//编号
		public int			rtu_id				= 0;			//终端编号
		public short		mp_id				= 0;			//测量点编号
		
		public int			chg_date			= 0;			//更换日期
		public int			chg_time			= 0;			//更换时间
		public short		chg_type			= 0;			//更换类型

		public int			oldct_numerator		= 0;  			//旧CT分子
		public int			oldct_denominator	= 0; 			//旧CT分母
		public double		oldct_ratio			= 0;			//旧ct变比
		public int			newct_numerator		= 0;  			//新CT分子
		public int			newct_denominator	= 0;			//新CT分母
		public double		newct_ratio			= 0;			//新CT变比
		
		public int			oldpt_numerator		= 0;			//旧PT分子
		public int			oldpt_denominator	= 0;			//旧PT分母
		public double		oldpt_ratio			= 0;			//旧pt变比
		public int			newpt_numerator		= 0;			//新PT分子
		public int			newpt_denominator	= 0;			//新PT分母
		public double		newpt_ratio			= 0;			//新pt变比

		public double		old_zyz = 0;						//旧正向总有功表底
		public double		old_zyj = 0;						//旧正向尖有功表底
		public double		old_zyf = 0;						//旧正向峰有功表底
		public double		old_zyp = 0;						//旧正向平有功表底
		public double		old_zyg = 0;						//旧正向谷有功表底
		public double		old_fyz = 0;						//旧反向总有功表底
		public double		old_fyj = 0;						//旧反向尖有功表底
		public double		old_fyf = 0;						//旧反向峰有功表底
		public double		old_fyp = 0;						//旧反向平有功表底
		public double		old_fyg = 0;						//旧反向谷有功表底
		public double		old_zwz = 0;						//旧正向总无功表底
		public double		old_zwj = 0;						//旧正向尖无功表底
		public double		old_zwf = 0;						//旧正向峰无功表底
		public double		old_zwp = 0;						//旧正向平无功表底
		public double		old_zwg = 0;						//旧正向谷无功表底
		public double		old_fwz = 0;						//旧反向总无功表底
		public double		old_fwj = 0;						//旧反向尖无功表底
		public double		old_fwf = 0;						//旧反向峰无功表底
		public double		old_fwp = 0;						//旧反向平无功表底
		public double		old_fwg = 0;						//旧反向谷无功表底

		public double		new_zyz = 0;						//新正向总有功表底
		public double		new_zyj = 0;						//新正向尖有功表底
		public double		new_zyf = 0;						//新正向峰有功表底
		public double		new_zyp = 0;						//新正向平有功表底
		public double		new_zyg = 0;						//新正向谷有功表底
		public double		new_fyz = 0;						//新反向总有功表底
		public double		new_fyj = 0;						//新反向尖有功表底
		public double		new_fyf = 0;						//新反向峰有功表底
		public double		new_fyp = 0;						//新反向平有功表底
		public double		new_fyg = 0;						//新反向谷有功表底
		public double		new_zwz = 0;						//新正向总无功表底
		public double		new_zwj = 0;						//新正向尖无功表底
		public double		new_zwf = 0;						//新正向峰无功表底
		public double		new_zwp = 0;						//新正向平无功表底
		public double		new_zwg = 0;						//新正向谷无功表底
		public double		new_fwz = 0;						//新反向总无功表底
		public double		new_fwj = 0;						//新反向尖无功表底
		public double		new_fwf = 0;						//新反向峰无功表底
		public double		new_fwp = 0;						//新反向平无功表底
		public double		new_fwg = 0;						//新反向谷无功表底

		public double		add_zyz = 0;						//追加正向总有功电量
		public double		add_zyj = 0;						//追加正向尖有功电量
		public double		add_zyf = 0;						//追加正向峰有功电量
		public double		add_zyp = 0;						//追加正向平有功电量
		public double		add_zyg = 0;						//追加正向谷有功电量
		public double		add_fyz = 0;						//追加反向总有功电量
		public double		add_fyj = 0;						//追加反向尖有功电量
		public double		add_fyf = 0;						//追加反向峰有功电量
		public double		add_fyp = 0;						//追加反向平有功电量
		public double		add_fyg = 0;						//追加反向谷有功电量
		public double		add_zwz = 0;						//追加正向总无功电量
		public double		add_zwj = 0;						//追加正向尖无功电量
		public double		add_zwf = 0;						//追加正向峰无功电量
		public double		add_zwp = 0;						//追加正向平无功电量
		public double		add_zwg = 0;						//追加正向谷无功电量
		public double		add_fwz = 0;						//追加反向总无功电量
		public double		add_fwj = 0;						//追加反向尖无功电量
		public double		add_fwf = 0;						//追加反向峰无功电量
		public double		add_fwp = 0;						//追加反向平无功电量
		public double		add_fwg = 0;						//追加反向谷无功电量
		
		public static void setYFF_CHGMETERRATE(YFF_CHGMETERRATE para, YFF_CHGMETERRATE setValue){
			if(setValue == null){
				para = new YFF_CHGMETERRATE();
			}else{
				para.id		= setValue.id;					
				para.rtu_id = setValue.rtu_id;				
				para.mp_id	= setValue.mp_id;				
				
				para.chg_date	= setValue.chg_date;			
				para.chg_time	= setValue.chg_time;			
				para.chg_type	= setValue.chg_type;			

				para.oldct_numerator	= setValue.oldct_numerator;  	
				para.oldct_denominator	= setValue.oldct_denominator; 	
				para.oldct_ratio		= setValue.oldct_ratio;		
				para.newct_numerator	= setValue.newct_numerator;  	
				para.newct_denominator	= setValue.newct_denominator;	
				para.newct_ratio		= setValue.newct_ratio;		
				
				para.oldpt_numerator	= setValue.oldpt_numerator;	
				para.oldpt_denominator	= setValue.oldpt_denominator;	
				para.oldpt_ratio		= setValue.oldpt_ratio;		
				para.newpt_numerator	= setValue.newpt_numerator;	
				para.newpt_denominator	= setValue.newpt_denominator;	
				para.newpt_ratio		= setValue.newpt_ratio;		

				para.old_zyz = setValue.old_zyz;
				para.old_zyj = setValue.old_zyj;
				para.old_zyf = setValue.old_zyf;
				para.old_zyp = setValue.old_zyp;
				para.old_zyg = setValue.old_zyg;
				para.old_fyz = setValue.old_fyz;
				para.old_fyj = setValue.old_fyj;
				para.old_fyf = setValue.old_fyf;
				para.old_fyp = setValue.old_fyp;
				para.old_fyg = setValue.old_fyg;
				para.old_zwz = setValue.old_zwz;
				para.old_zwj = setValue.old_zwj;
				para.old_zwf = setValue.old_zwf;
				para.old_zwp = setValue.old_zwp;
				para.old_zwg = setValue.old_zwg;
				para.old_fwz = setValue.old_fwz;
				para.old_fwj = setValue.old_fwj;
				para.old_fwf = setValue.old_fwf;
				para.old_fwp = setValue.old_fwp;
				para.old_fwg = setValue.old_fwg;

				para.new_zyz = setValue.new_zyz;
				para.new_zyj = setValue.new_zyj;
				para.new_zyf = setValue.new_zyf;
				para.new_zyp = setValue.new_zyp;
				para.new_zyg = setValue.new_zyg;
				para.new_fyz = setValue.new_fyz;
				para.new_fyj = setValue.new_fyj;
				para.new_fyf = setValue.new_fyf;
				para.new_fyp = setValue.new_fyp;
				para.new_fyg = setValue.new_fyg;
				para.new_zwz = setValue.new_zwz;
				para.new_zwj = setValue.new_zwj;
				para.new_zwf = setValue.new_zwf;
				para.new_zwp = setValue.new_zwp;
				para.new_zwg = setValue.new_zwg;
				para.new_fwz = setValue.new_fwz;
				para.new_fwj = setValue.new_fwj;
				para.new_fwf = setValue.new_fwf;
				para.new_fwp = setValue.new_fwp;
				para.new_fwg = setValue.new_fwg;

				para.add_zyz = setValue.add_zyz;
				para.add_zyj = setValue.add_zyj;
				para.add_zyf = setValue.add_zyf;
				para.add_zyp = setValue.add_zyp;
				para.add_zyg = setValue.add_zyg;
				para.add_fyz = setValue.add_fyz;
				para.add_fyj = setValue.add_fyj;
				para.add_fyf = setValue.add_fyf;
				para.add_fyp = setValue.add_fyp;
				para.add_fyg = setValue.add_fyg;
				para.add_zwz = setValue.add_zwz;
				para.add_zwj = setValue.add_zwj;
				para.add_zwf = setValue.add_zwf;
				para.add_zwp = setValue.add_zwp;
				para.add_zwg = setValue.add_zwg;
				para.add_fwz = setValue.add_fwz;
				para.add_fwj = setValue.add_fwj;
				para.add_fwf = setValue.add_fwf;
				para.add_fwp = setValue.add_fwp;
				para.add_fwg = setValue.add_fwg;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, YFF_CHGMETERRATE para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.id);
			ret_len += ComntStream.writeStream(byte_vect, para.rtu_id);
			ret_len += ComntStream.writeStream(byte_vect, para.mp_id);
			ret_len += ComntStream.writeStream(byte_vect, para.chg_date);
			ret_len += ComntStream.writeStream(byte_vect, para.chg_time);
			ret_len += ComntStream.writeStream(byte_vect, para.chg_type);
			ret_len += ComntStream.writeStream(byte_vect, para.oldct_numerator);
			ret_len += ComntStream.writeStream(byte_vect, para.oldct_denominator);
			ret_len += ComntStream.writeStream(byte_vect, para.oldct_ratio);
			ret_len += ComntStream.writeStream(byte_vect, para.newct_numerator);
			ret_len += ComntStream.writeStream(byte_vect, para.newct_denominator);
			ret_len += ComntStream.writeStream(byte_vect, para.newct_ratio);
			ret_len += ComntStream.writeStream(byte_vect, para.oldpt_numerator);
			ret_len += ComntStream.writeStream(byte_vect, para.oldpt_denominator);
			ret_len += ComntStream.writeStream(byte_vect, para.oldpt_ratio);
			ret_len += ComntStream.writeStream(byte_vect, para.newpt_numerator);
			ret_len += ComntStream.writeStream(byte_vect, para.newpt_denominator);
			ret_len += ComntStream.writeStream(byte_vect, para.newpt_ratio);
			
			ret_len += ComntStream.writeStream(byte_vect, para.old_zyz);
			ret_len += ComntStream.writeStream(byte_vect, para.old_zyj);
			ret_len += ComntStream.writeStream(byte_vect, para.old_zyf);
			ret_len += ComntStream.writeStream(byte_vect, para.old_zyp);
			ret_len += ComntStream.writeStream(byte_vect, para.old_zyg);
			ret_len += ComntStream.writeStream(byte_vect, para.old_fyz);
			ret_len += ComntStream.writeStream(byte_vect, para.old_fyj);
			ret_len += ComntStream.writeStream(byte_vect, para.old_fyf);
			ret_len += ComntStream.writeStream(byte_vect, para.old_fyp);
			ret_len += ComntStream.writeStream(byte_vect, para.old_fyg);
			ret_len += ComntStream.writeStream(byte_vect, para.old_zwz);
			ret_len += ComntStream.writeStream(byte_vect, para.old_zwj);
			ret_len += ComntStream.writeStream(byte_vect, para.old_zwf);
			ret_len += ComntStream.writeStream(byte_vect, para.old_zwp);
			ret_len += ComntStream.writeStream(byte_vect, para.old_zwg);
			ret_len += ComntStream.writeStream(byte_vect, para.old_fwz);
			ret_len += ComntStream.writeStream(byte_vect, para.old_fwj);
			ret_len += ComntStream.writeStream(byte_vect, para.old_fwf);
			ret_len += ComntStream.writeStream(byte_vect, para.old_fwp);
			ret_len += ComntStream.writeStream(byte_vect, para.old_fwg);
			
			ret_len += ComntStream.writeStream(byte_vect, para.new_zyz);
			ret_len += ComntStream.writeStream(byte_vect, para.new_zyj);
			ret_len += ComntStream.writeStream(byte_vect, para.new_zyf);
			ret_len += ComntStream.writeStream(byte_vect, para.new_zyp);
			ret_len += ComntStream.writeStream(byte_vect, para.new_zyg);
			ret_len += ComntStream.writeStream(byte_vect, para.new_fyz);
			ret_len += ComntStream.writeStream(byte_vect, para.new_fyj);
			ret_len += ComntStream.writeStream(byte_vect, para.new_fyf);
			ret_len += ComntStream.writeStream(byte_vect, para.new_fyp);
			ret_len += ComntStream.writeStream(byte_vect, para.new_fyg);
			ret_len += ComntStream.writeStream(byte_vect, para.new_zwz);
			ret_len += ComntStream.writeStream(byte_vect, para.new_zwj);
			ret_len += ComntStream.writeStream(byte_vect, para.new_zwf);
			ret_len += ComntStream.writeStream(byte_vect, para.new_zwp);
			ret_len += ComntStream.writeStream(byte_vect, para.new_zwg);
			ret_len += ComntStream.writeStream(byte_vect, para.new_fwz);
			ret_len += ComntStream.writeStream(byte_vect, para.new_fwj);
			ret_len += ComntStream.writeStream(byte_vect, para.new_fwf);
			ret_len += ComntStream.writeStream(byte_vect, para.new_fwp);
			ret_len += ComntStream.writeStream(byte_vect, para.new_fwg);
			
			ret_len += ComntStream.writeStream(byte_vect, para.add_zyz);
			ret_len += ComntStream.writeStream(byte_vect, para.add_zyj);
			ret_len += ComntStream.writeStream(byte_vect, para.add_zyf);
			ret_len += ComntStream.writeStream(byte_vect, para.add_zyp);
			ret_len += ComntStream.writeStream(byte_vect, para.add_zyg);
			ret_len += ComntStream.writeStream(byte_vect, para.add_fyz);
			ret_len += ComntStream.writeStream(byte_vect, para.add_fyj);
			ret_len += ComntStream.writeStream(byte_vect, para.add_fyf);
			ret_len += ComntStream.writeStream(byte_vect, para.add_fyp);
			ret_len += ComntStream.writeStream(byte_vect, para.add_fyg);
			ret_len += ComntStream.writeStream(byte_vect, para.add_zwz);
			ret_len += ComntStream.writeStream(byte_vect, para.add_zwj);
			ret_len += ComntStream.writeStream(byte_vect, para.add_zwf);
			ret_len += ComntStream.writeStream(byte_vect, para.add_zwp);
			ret_len += ComntStream.writeStream(byte_vect, para.add_zwg);
			ret_len += ComntStream.writeStream(byte_vect, para.add_fwz);
			ret_len += ComntStream.writeStream(byte_vect, para.add_fwj);
			ret_len += ComntStream.writeStream(byte_vect, para.add_fwf);
			ret_len += ComntStream.writeStream(byte_vect, para.add_fwp);
			ret_len += ComntStream.writeStream(byte_vect, para.add_fwg);
			
			return ret_len;
		}
		
		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFF_CHGMETERRATE para){
			int ret_len = len;
			
			para.id = ComntStream.readStream(byte_vect, offset + ret_len, para.id); 
			ret_len += ComntStream.getDataSize(para.id);
			
			para.rtu_id = ComntStream.readStream(byte_vect, offset + ret_len, para.rtu_id); 
			ret_len += ComntStream.getDataSize(para.rtu_id);
			
			para.mp_id = ComntStream.readStream(byte_vect, offset + ret_len, para.mp_id); 
			ret_len += ComntStream.getDataSize(para.mp_id);
			
			para.chg_date = ComntStream.readStream(byte_vect, offset + ret_len, para.chg_date); 
			ret_len += ComntStream.getDataSize(para.chg_date);
			
			para.chg_time = ComntStream.readStream(byte_vect, offset + ret_len, para.chg_time); 
			ret_len += ComntStream.getDataSize(para.chg_time);
			
			para.chg_type = ComntStream.readStream(byte_vect, offset + ret_len, para.chg_type); 
			ret_len += ComntStream.getDataSize(para.chg_type);
			
			para.oldct_numerator = ComntStream.readStream(byte_vect, offset + ret_len, para.oldct_numerator); 
			ret_len += ComntStream.getDataSize(para.oldct_numerator);
			
			para.oldct_denominator = ComntStream.readStream(byte_vect, offset + ret_len, para.oldct_denominator); 
			ret_len += ComntStream.getDataSize(para.oldct_denominator);
			
			para.oldct_ratio = ComntStream.readStream(byte_vect, offset + ret_len, para.oldct_ratio); 
			ret_len += ComntStream.getDataSize(para.oldct_ratio);
			
			para.newct_numerator = ComntStream.readStream(byte_vect, offset + ret_len, para.newct_numerator); 
			ret_len += ComntStream.getDataSize(para.newct_numerator);
			
			para.newct_denominator = ComntStream.readStream(byte_vect, offset + ret_len, para.newct_denominator); 
			ret_len += ComntStream.getDataSize(para.newct_denominator);
			
			para.newct_ratio = ComntStream.readStream(byte_vect, offset + ret_len, para.newct_ratio); 
			ret_len += ComntStream.getDataSize(para.newct_ratio);
			
			para.oldpt_numerator = ComntStream.readStream(byte_vect, offset + ret_len, para.oldpt_numerator); 
			ret_len += ComntStream.getDataSize(para.oldpt_numerator);
			
			para.oldpt_denominator = ComntStream.readStream(byte_vect, offset + ret_len, para.oldpt_denominator); 
			ret_len += ComntStream.getDataSize(para.oldpt_denominator);
			
			para.oldpt_ratio = ComntStream.readStream(byte_vect, offset + ret_len, para.oldpt_ratio); 
			ret_len += ComntStream.getDataSize(para.oldpt_ratio);
			
			para.newpt_numerator = ComntStream.readStream(byte_vect, offset + ret_len, para.newpt_numerator); 
			ret_len += ComntStream.getDataSize(para.newpt_numerator);
			
			para.newpt_denominator = ComntStream.readStream(byte_vect, offset + ret_len, para.newpt_denominator); 
			ret_len += ComntStream.getDataSize(para.newpt_denominator);
			
			para.newpt_ratio = ComntStream.readStream(byte_vect, offset + ret_len, para.newpt_ratio); 
			ret_len += ComntStream.getDataSize(para.newpt_ratio);
			
			para.old_zyz = ComntStream.readStream(byte_vect, offset + ret_len, para.old_zyz); 
			ret_len += ComntStream.getDataSize(para.old_zyz);
			para.old_zyj = ComntStream.readStream(byte_vect, offset + ret_len, para.old_zyj); 
			ret_len += ComntStream.getDataSize(para.old_zyj);
			para.old_zyf = ComntStream.readStream(byte_vect, offset + ret_len, para.old_zyf); 
			ret_len += ComntStream.getDataSize(para.old_zyf);
			para.old_zyp = ComntStream.readStream(byte_vect, offset + ret_len, para.old_zyp); 
			ret_len += ComntStream.getDataSize(para.old_zyp);
			para.old_zyg = ComntStream.readStream(byte_vect, offset + ret_len, para.old_zyg); 
			ret_len += ComntStream.getDataSize(para.old_zyg);
			para.old_fyz = ComntStream.readStream(byte_vect, offset + ret_len, para.old_fyz); 
			ret_len += ComntStream.getDataSize(para.old_fyz);
			para.old_fyj = ComntStream.readStream(byte_vect, offset + ret_len, para.old_fyj); 
			ret_len += ComntStream.getDataSize(para.old_fyj);
			para.old_fyf = ComntStream.readStream(byte_vect, offset + ret_len, para.old_fyf); 
			ret_len += ComntStream.getDataSize(para.old_fyf);
			para.old_fyp = ComntStream.readStream(byte_vect, offset + ret_len, para.old_fyp); 
			ret_len += ComntStream.getDataSize(para.old_fyp);
			para.old_fyg = ComntStream.readStream(byte_vect, offset + ret_len, para.old_fyg); 
			ret_len += ComntStream.getDataSize(para.old_fyg);
			para.old_zwz = ComntStream.readStream(byte_vect, offset + ret_len, para.old_zwz); 
			ret_len += ComntStream.getDataSize(para.old_zwz);
			para.old_zwj = ComntStream.readStream(byte_vect, offset + ret_len, para.old_zwj); 
			ret_len += ComntStream.getDataSize(para.old_zwj);
			para.old_zwf = ComntStream.readStream(byte_vect, offset + ret_len, para.old_zwf); 
			ret_len += ComntStream.getDataSize(para.old_zwf);
			para.old_zwp = ComntStream.readStream(byte_vect, offset + ret_len, para.old_zwp); 
			ret_len += ComntStream.getDataSize(para.old_zwp);
			para.old_zwg = ComntStream.readStream(byte_vect, offset + ret_len, para.old_zwg); 
			ret_len += ComntStream.getDataSize(para.old_zwg);
			para.old_fwz = ComntStream.readStream(byte_vect, offset + ret_len, para.old_fwz); 
			ret_len += ComntStream.getDataSize(para.old_fwz);
			para.old_fwj = ComntStream.readStream(byte_vect, offset + ret_len, para.old_fwj); 
			ret_len += ComntStream.getDataSize(para.old_fwj);
			para.old_fwf = ComntStream.readStream(byte_vect, offset + ret_len, para.old_fwf); 
			ret_len += ComntStream.getDataSize(para.old_fwf);
			para.old_fwp = ComntStream.readStream(byte_vect, offset + ret_len, para.old_fwp); 
			ret_len += ComntStream.getDataSize(para.old_fwp);
			para.old_fwg = ComntStream.readStream(byte_vect, offset + ret_len, para.old_fwg); 
			ret_len += ComntStream.getDataSize(para.old_fwg);
			
			para.new_zyz = ComntStream.readStream(byte_vect, offset + ret_len, para.new_zyz); 
			ret_len += ComntStream.getDataSize(para.new_zyz);
			para.new_zyj = ComntStream.readStream(byte_vect, offset + ret_len, para.new_zyj); 
			ret_len += ComntStream.getDataSize(para.new_zyj);
			para.new_zyf = ComntStream.readStream(byte_vect, offset + ret_len, para.new_zyf); 
			ret_len += ComntStream.getDataSize(para.new_zyf);
			para.new_zyp = ComntStream.readStream(byte_vect, offset + ret_len, para.new_zyp); 
			ret_len += ComntStream.getDataSize(para.new_zyp);
			para.new_zyg = ComntStream.readStream(byte_vect, offset + ret_len, para.new_zyg); 
			ret_len += ComntStream.getDataSize(para.new_zyg);
			para.new_fyz = ComntStream.readStream(byte_vect, offset + ret_len, para.new_fyz); 
			ret_len += ComntStream.getDataSize(para.new_fyz);
			para.new_fyj = ComntStream.readStream(byte_vect, offset + ret_len, para.new_fyj); 
			ret_len += ComntStream.getDataSize(para.new_fyj);
			para.new_fyf = ComntStream.readStream(byte_vect, offset + ret_len, para.new_fyf); 
			ret_len += ComntStream.getDataSize(para.new_fyf);
			para.new_fyp = ComntStream.readStream(byte_vect, offset + ret_len, para.new_fyp); 
			ret_len += ComntStream.getDataSize(para.new_fyp);
			para.new_fyg = ComntStream.readStream(byte_vect, offset + ret_len, para.new_fyg); 
			ret_len += ComntStream.getDataSize(para.new_fyg);
			para.new_zwz = ComntStream.readStream(byte_vect, offset + ret_len, para.new_zwz); 
			ret_len += ComntStream.getDataSize(para.new_zwz);
			para.new_zwj = ComntStream.readStream(byte_vect, offset + ret_len, para.new_zwj); 
			ret_len += ComntStream.getDataSize(para.new_zwj);
			para.new_zwf = ComntStream.readStream(byte_vect, offset + ret_len, para.new_zwf); 
			ret_len += ComntStream.getDataSize(para.new_zwf);
			para.new_zwp = ComntStream.readStream(byte_vect, offset + ret_len, para.new_zwp); 
			ret_len += ComntStream.getDataSize(para.new_zwp);
			para.new_zwg = ComntStream.readStream(byte_vect, offset + ret_len, para.new_zwg); 
			ret_len += ComntStream.getDataSize(para.new_zwg);
			para.new_fwz = ComntStream.readStream(byte_vect, offset + ret_len, para.new_fwz); 
			ret_len += ComntStream.getDataSize(para.new_fwz);
			para.new_fwj = ComntStream.readStream(byte_vect, offset + ret_len, para.new_fwj); 
			ret_len += ComntStream.getDataSize(para.new_fwj);
			para.new_fwf = ComntStream.readStream(byte_vect, offset + ret_len, para.new_fwf); 
			ret_len += ComntStream.getDataSize(para.new_fwf);
			para.new_fwp = ComntStream.readStream(byte_vect, offset + ret_len, para.new_fwp); 
			ret_len += ComntStream.getDataSize(para.new_fwp);
			para.new_fwg = ComntStream.readStream(byte_vect, offset + ret_len, para.new_fwg); 
			ret_len += ComntStream.getDataSize(para.new_fwg);
			
			para.add_zyz = ComntStream.readStream(byte_vect, offset + ret_len, para.add_zyz); 
			ret_len += ComntStream.getDataSize(para.add_zyz);
			para.add_zyj = ComntStream.readStream(byte_vect, offset + ret_len, para.add_zyj); 
			ret_len += ComntStream.getDataSize(para.add_zyj);
			para.add_zyf = ComntStream.readStream(byte_vect, offset + ret_len, para.add_zyf); 
			ret_len += ComntStream.getDataSize(para.add_zyf);
			para.add_zyp = ComntStream.readStream(byte_vect, offset + ret_len, para.add_zyp); 
			ret_len += ComntStream.getDataSize(para.add_zyp);
			para.add_zyg = ComntStream.readStream(byte_vect, offset + ret_len, para.add_zyg); 
			ret_len += ComntStream.getDataSize(para.add_zyg);
			para.add_fyz = ComntStream.readStream(byte_vect, offset + ret_len, para.add_fyz); 
			ret_len += ComntStream.getDataSize(para.add_fyz);
			para.add_fyj = ComntStream.readStream(byte_vect, offset + ret_len, para.add_fyj); 
			ret_len += ComntStream.getDataSize(para.add_fyj);
			para.add_fyf = ComntStream.readStream(byte_vect, offset + ret_len, para.add_fyf); 
			ret_len += ComntStream.getDataSize(para.add_fyf);
			para.add_fyp = ComntStream.readStream(byte_vect, offset + ret_len, para.add_fyp); 
			ret_len += ComntStream.getDataSize(para.add_fyp);
			para.add_fyg = ComntStream.readStream(byte_vect, offset + ret_len, para.add_fyg); 
			ret_len += ComntStream.getDataSize(para.add_fyg);
			para.add_zwz = ComntStream.readStream(byte_vect, offset + ret_len, para.add_zwz); 
			ret_len += ComntStream.getDataSize(para.add_zwz);
			para.add_zwj = ComntStream.readStream(byte_vect, offset + ret_len, para.add_zwj); 
			ret_len += ComntStream.getDataSize(para.add_zwj);
			para.add_zwf = ComntStream.readStream(byte_vect, offset + ret_len, para.add_zwf); 
			ret_len += ComntStream.getDataSize(para.add_zwf);
			para.add_zwp = ComntStream.readStream(byte_vect, offset + ret_len, para.add_zwp); 
			ret_len += ComntStream.getDataSize(para.add_zwp);
			para.add_zwg = ComntStream.readStream(byte_vect, offset + ret_len, para.add_zwg); 
			ret_len += ComntStream.getDataSize(para.add_zwg);
			para.add_fwz = ComntStream.readStream(byte_vect, offset + ret_len, para.add_fwz); 
			ret_len += ComntStream.getDataSize(para.add_fwz);
			para.add_fwj = ComntStream.readStream(byte_vect, offset + ret_len, para.add_fwj); 
			ret_len += ComntStream.getDataSize(para.add_fwj);
			para.add_fwf = ComntStream.readStream(byte_vect, offset + ret_len, para.add_fwf); 
			ret_len += ComntStream.getDataSize(para.add_fwf);
			para.add_fwp = ComntStream.readStream(byte_vect, offset + ret_len, para.add_fwp); 
			ret_len += ComntStream.getDataSize(para.add_fwp);
			para.add_fwg = ComntStream.readStream(byte_vect, offset + ret_len, para.add_fwg); 
			ret_len += ComntStream.getDataSize(para.add_fwg);
			
			return ret_len;
		}
	}

	
	/**
	 * 缴费表字信息--[消息相关,请不要随意改动]
	 */
	public static class YFF_PAYBZ{
		public double		buy_dl			= 0;					//购电量
		public double		pay_bmc			= 0;					//表码差
		public double		shutdown_bd		= 0;					//断电止码
		
		public static void setYFF_PAYBZ(YFF_PAYBZ yff_bz, YFF_PAYBZ setValue){
			if(setValue == null){
				yff_bz = new YFF_PAYBZ();
			}else{
				yff_bz.buy_dl	= setValue.buy_dl;
				yff_bz.pay_bmc 	= setValue.pay_bmc;
				yff_bz.shutdown_bd 	= setValue.shutdown_bd;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, YFF_PAYBZ para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.buy_dl);
			ret_len += ComntStream.writeStream(byte_vect, para.pay_bmc);
			ret_len += ComntStream.writeStream(byte_vect, para.shutdown_bd);
			
			return ret_len;
		}
		
		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFF_PAYBZ para){
			int ret_len = len;
			
			para.buy_dl = ComntStream.readStream(byte_vect, offset + ret_len, para.buy_dl); 
			ret_len += ComntStream.getDataSize(para.buy_dl);
			
			para.pay_bmc = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_bmc); 
			ret_len += ComntStream.getDataSize(para.pay_bmc);
			
			para.shutdown_bd = ComntStream.readStream(byte_vect, offset + ret_len, para.shutdown_bd); 
			ret_len += ComntStream.getDataSize(para.shutdown_bd);
			
			return ret_len;
		}
	}

	//20140606 zhp 新增  缴费动态密码信息
	public static class YFF_PASSRAND{
		public int	   update_flag	=	0;									//更新标志0x01 随机数 0x02 密码
		public byte[]  card_rand  	= 	new byte[ComntMsg.MSG_STR_LEN_32];	//随机数
		public byte[]  card_pass	=	new byte[ComntMsg.MSG_STR_LEN_32];	//密码
		public byte[]  reserve1		=	new byte[ComntMsg.MSG_STR_LEN_32];	//
		public byte[]  reserve2		=	new byte[ComntMsg.MSG_STR_LEN_32];
		public int	   reserve3		=	0;
		public double  reserve4		=	0;
		
		public static void setYFF_PASSRAND(YFF_PASSRAND para, YFF_PASSRAND setValue){
			if(setValue == null){
				para = new YFF_PASSRAND();
			}else{
				para.update_flag	= setValue.update_flag;
				
				for (int i = 0; i < ComntMsg.MSG_STR_LEN_32; i++) {
					para.card_rand[i] = setValue.card_rand[i];
					para.card_pass[i] = setValue.card_pass[i];
					para.reserve1[i]  = setValue.reserve1[i];
					para.reserve2[i]  = setValue.reserve2[i];
				}
				para.reserve3 	= setValue.reserve3;
				para.reserve4 	= setValue.reserve4;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, YFF_PASSRAND para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.update_flag);
			ret_len += ComntStream.writeStream(byte_vect, para.card_rand, 0, para.card_rand.length);
			ret_len += ComntStream.writeStream(byte_vect, para.card_pass, 0, para.card_pass.length);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve1,  0, para.reserve1.length);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve2,  0, para.reserve2.length);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve3);
			ret_len += ComntStream.writeStream(byte_vect, para.reserve4);
			
			return ret_len;
		}
		
		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFF_PASSRAND para){
			int ret_len = len;
			
			para.update_flag = ComntStream.readStream(byte_vect, offset + ret_len, para.update_flag); 
			ret_len += ComntStream.getDataSize(para.update_flag);
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.card_rand, 0, para.card_rand.length);
			ret_len += ComntStream.getDataSize(para.card_rand[0]) * para.card_rand.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.card_pass, 0, para.card_pass.length);
			ret_len += ComntStream.getDataSize(para.card_pass[0]) * para.card_pass.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.reserve1, 0, para.reserve1.length);
			ret_len += ComntStream.getDataSize(para.reserve1[0]) * para.reserve1.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.reserve2, 0, para.reserve2.length);
			ret_len += ComntStream.getDataSize(para.reserve2[0]) * para.reserve2.length;
			
			para.reserve3 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve3); 
			ret_len += ComntStream.getDataSize(para.reserve3);
			
			para.reserve4 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve4); 
			ret_len += ComntStream.getDataSize(para.reserve4);
			
			return ret_len;
		}

	}
	

	/**
	 * 计量点预付费参数表--[消息相关,请不要随意改动]
	 */
	public static class YFF_MPPAYPARA
	{
		public int		rtu_id 			= 0;						//终端编号
		public short	mp_id 			= 0;						//测量点编号
		
		public byte		cacl_type		= 0;						//算费类型 0:无 1:金额计费 2:表底计费
		public byte		feectrl_type	= 0;						//费控方式 0:本地费控 1:主站费控
		public byte		pay_type 		= 0;						//缴费方式 0:卡式 1:远程 2:主站
		public byte		writecard_no[]   = new byte[ComntMsg.MSG_STR_LEN_32];	//写卡户号 
		
		public byte		yffmeter_type 	= 0;						//预付费表类型
		public short	feeproj_id 		= 0;						//费率方案号
		public short	yffalarm_id 	= 0;						//报警方案
		
		public int		fee_begindate	= 0;						//费率启用日期
		
		public byte		prot_st 		= 0;						//保电开始时间
		public byte		prot_ed 		= 0;						//保电结束时间
		public byte		ngloprot_flag 	= 0;						//不参与全局保电标志
		
		public byte		key_version 	= 0;						//密钥版本, 单用于智能表
		public byte		cryplink_id 	= 0;						//加密机ID, 单用于智能表
		public int		pay_add 		= 0;						//缴费附加值
		public int		tz_val 			= 0;						//透支值
		
		public byte		power_relaf 	= 0;						//动力关联标志
		public short	power_rela1 	= 0;						//动力关联1
		public short	power_rela2 	= 0;						//动力关联2
		public byte		power_relask1 	= 0;						//动力1可控标志
		public byte		power_relask2 	= 0;						//动力2可控标志
		
		public byte		fee_chgf 		= 0;						//费率更改标准
		public short	fee_chgid 		= 0;						//费率更改ID
		public int		fee_chgdate 	= 0;						//费率更改日期
		public int		fee_chgtime 	= 0;						//费率更改时间
		
		//20120806
		public short	jt_cycle_md		= 0;						//阶梯周期切换时间MMDD
		
		public byte		cb_cycle_type   = 0;						//抄表周期类型 0:每月抄表 1:双月抄表  2:单月抄表 3:季度抄表 4：半年抄表 5：年抄表
		public short	cb_dayhour		= 0;						//抄表日DDHH
		public byte		js_day			= 0;						//发行电费日

		public byte		fxdf_flag		= 0;						//是否发行电费
		public int		fxdf_begindate	= 0;						//发行电费起始日期

		public byte		local_maincalcf = 0;						//主站算费标志	0 不算费， 1 算费
		
		//20140606 zhp
		public int		ocardproj_id;										//外接卡表类型编号
		public byte[]	card_rand   = 	new byte[ComntMsg.MSG_STR_LEN_32];	//随机数现在是外接卡表使用
		public byte[]	card_pass	=	new byte[ComntMsg.MSG_STR_LEN_32];	//密码现在是外接卡表使用
		public byte[]	card_area	=	new byte[ComntMsg.MSG_STR_LEN_32];	//区域码现在是外接卡表使用
		//end
		
		public static void setYFF_MPPAYPARA(YFF_MPPAYPARA para, YFF_MPPAYPARA setValue){
			if(setValue == null){
				para = new YFF_MPPAYPARA();
			}else{
				para.rtu_id 		= setValue.rtu_id;
				para.mp_id 			= setValue.mp_id;
				para.cacl_type		= setValue.cacl_type;
				para.feectrl_type 	= setValue.feectrl_type;
				para.pay_type 		= setValue.pay_type;
				for (int i = 0; i < ComntMsg.MSG_STR_LEN_32; i++) {
					para.writecard_no[i] = setValue.writecard_no[i];
				}
				para.yffmeter_type 	= setValue.yffmeter_type;
				para.feeproj_id 	= setValue.feeproj_id;
				para.yffalarm_id 	= setValue.yffalarm_id;
				para.fee_begindate  = setValue.fee_begindate;
				para.prot_st 		= setValue.prot_st;
				para.prot_ed 		= setValue.prot_ed;
				para.ngloprot_flag 	= setValue.ngloprot_flag;
				para.key_version 	= setValue.key_version;
				para.cryplink_id 	= setValue.cryplink_id;
				para.pay_add 		= setValue.pay_add;
				para.tz_val 		= setValue.tz_val;
				para.power_relaf 	= setValue.power_relaf;
				para.power_rela1 	= setValue.power_rela1;
				para.power_rela2 	= setValue.power_rela2;
				para.power_relask1 	= setValue.power_relask1;
				para.power_relask2 	= setValue.power_relask2;
				para.fee_chgf 		= setValue.fee_chgf;
				para.fee_chgid 		= setValue.fee_chgid;
				para.fee_chgdate 	= setValue.fee_chgdate;
				para.fee_chgtime 	= setValue.fee_chgtime;
				
				para.jt_cycle_md	= setValue.jt_cycle_md;
				para.cb_cycle_type	= setValue.cb_cycle_type;
				para.cb_dayhour		= setValue.cb_dayhour;
				para.js_day			= setValue.js_day;
				para.fxdf_flag		= setValue.fxdf_flag;
				para.fxdf_begindate	= setValue.fxdf_begindate;
				para.local_maincalcf= setValue.local_maincalcf;
				
				para.ocardproj_id   = setValue.ocardproj_id;
				for (int i = 0; i < ComntMsg.MSG_STR_LEN_32; i++) {
					para.card_rand[i] = setValue.card_rand[i];
					para.card_pass[i] = setValue.card_pass[i];
					para.card_area[i] = setValue.card_area[i];
				}
			}
		}

		public static int writeStream(ComntVector.ByteVector byte_vect, YFF_MPPAYPARA para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.rtu_id);
			ret_len += ComntStream.writeStream(byte_vect, para.mp_id);
			ret_len += ComntStream.writeStream(byte_vect, para.cacl_type);
			ret_len += ComntStream.writeStream(byte_vect, para.feectrl_type);
			ret_len += ComntStream.writeStream(byte_vect, para.pay_type);	
			ret_len += ComntStream.writeStream(byte_vect, para.writecard_no, 0, para.writecard_no.length);
			ret_len += ComntStream.writeStream(byte_vect, para.yffmeter_type);
			ret_len += ComntStream.writeStream(byte_vect, para.feeproj_id);
			ret_len += ComntStream.writeStream(byte_vect, para.yffalarm_id);
			ret_len += ComntStream.writeStream(byte_vect, para.fee_begindate);
			ret_len += ComntStream.writeStream(byte_vect, para.prot_st);
			ret_len += ComntStream.writeStream(byte_vect, para.prot_ed);
			ret_len += ComntStream.writeStream(byte_vect, para.ngloprot_flag);
			ret_len += ComntStream.writeStream(byte_vect, para.key_version);
			ret_len += ComntStream.writeStream(byte_vect, para.cryplink_id);
			ret_len += ComntStream.writeStream(byte_vect, para.pay_add);
			ret_len += ComntStream.writeStream(byte_vect, para.tz_val);
			ret_len += ComntStream.writeStream(byte_vect, para.power_relaf);
			ret_len += ComntStream.writeStream(byte_vect, para.power_rela1);
			ret_len += ComntStream.writeStream(byte_vect, para.power_rela2);
			ret_len += ComntStream.writeStream(byte_vect, para.power_relask1);
			ret_len += ComntStream.writeStream(byte_vect, para.power_relask2);
			ret_len += ComntStream.writeStream(byte_vect, para.fee_chgf);
			ret_len += ComntStream.writeStream(byte_vect, para.fee_chgid);
			ret_len += ComntStream.writeStream(byte_vect, para.fee_chgdate);
			ret_len += ComntStream.writeStream(byte_vect, para.fee_chgtime);

			ret_len += ComntStream.writeStream(byte_vect, para.jt_cycle_md);
			ret_len += ComntStream.writeStream(byte_vect, para.cb_cycle_type);
			ret_len += ComntStream.writeStream(byte_vect, para.cb_dayhour);
			ret_len += ComntStream.writeStream(byte_vect, para.js_day);
			ret_len += ComntStream.writeStream(byte_vect, para.fxdf_flag);
			ret_len += ComntStream.writeStream(byte_vect, para.fxdf_begindate);
			ret_len += ComntStream.writeStream(byte_vect, para.local_maincalcf);
			
			ret_len += ComntStream.writeStream(byte_vect, para.ocardproj_id);
			ret_len += ComntStream.writeStream(byte_vect, para.card_rand, 0, para.card_rand.length);
			ret_len += ComntStream.writeStream(byte_vect, para.card_pass, 0, para.card_pass.length);
			ret_len += ComntStream.writeStream(byte_vect, para.card_area, 0, para.card_area.length);
			
			return ret_len;
		}
		
		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFF_MPPAYPARA para){
			int ret_len = len;
			
			para.rtu_id = ComntStream.readStream(byte_vect, offset + ret_len, para.rtu_id); 
			ret_len += ComntStream.getDataSize(para.rtu_id);
			
			para.mp_id = ComntStream.readStream(byte_vect, offset + ret_len, para.mp_id); 
			ret_len += ComntStream.getDataSize(para.mp_id);
			
			para.cacl_type = ComntStream.readStream(byte_vect, offset + ret_len, para.cacl_type); 
			ret_len += ComntStream.getDataSize(para.cacl_type);
			
			para.feectrl_type = ComntStream.readStream(byte_vect, offset + ret_len, para.feectrl_type); 
			ret_len += ComntStream.getDataSize(para.feectrl_type);
			
			para.pay_type = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_type); 
			ret_len += ComntStream.getDataSize(para.pay_type);

			ComntStream.readStream(byte_vect, offset + ret_len, para.writecard_no, 0, para.writecard_no.length);
			ret_len += ComntStream.getDataSize(para.writecard_no[0]) * para.writecard_no.length;			
		
			para.yffmeter_type = ComntStream.readStream(byte_vect, offset + ret_len, para.yffmeter_type); 
			ret_len += ComntStream.getDataSize(para.yffmeter_type);
			
			para.feeproj_id = ComntStream.readStream(byte_vect, offset + ret_len, para.feeproj_id); 
			ret_len += ComntStream.getDataSize(para.feeproj_id);
			
			para.yffalarm_id = ComntStream.readStream(byte_vect, offset + ret_len, para.yffalarm_id); 
			ret_len += ComntStream.getDataSize(para.yffalarm_id);

			para.fee_begindate = ComntStream.readStream(byte_vect, offset + ret_len, para.fee_begindate); 
			ret_len += ComntStream.getDataSize(para.fee_begindate);

			para.prot_st = ComntStream.readStream(byte_vect, offset + ret_len, para.prot_st); 
			ret_len += ComntStream.getDataSize(para.prot_st);

			para.prot_ed = ComntStream.readStream(byte_vect, offset + ret_len, para.prot_ed); 
			ret_len += ComntStream.getDataSize(para.prot_ed);
			
			para.ngloprot_flag = ComntStream.readStream(byte_vect, offset + ret_len, para.ngloprot_flag); 
			ret_len += ComntStream.getDataSize(para.ngloprot_flag);
			
			para.key_version = ComntStream.readStream(byte_vect, offset + ret_len, para.key_version); 
			ret_len += ComntStream.getDataSize(para.key_version);
			
			para.cryplink_id = ComntStream.readStream(byte_vect, offset + ret_len, para.cryplink_id); 
			ret_len += ComntStream.getDataSize(para.cryplink_id);
			
			para.pay_add = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_add); 
			ret_len += ComntStream.getDataSize(para.pay_add);
			
			para.tz_val = ComntStream.readStream(byte_vect, offset + ret_len, para.tz_val); 
			ret_len += ComntStream.getDataSize(para.tz_val);
			
			para.power_relaf = ComntStream.readStream(byte_vect, offset + ret_len, para.power_relaf); 
			ret_len += ComntStream.getDataSize(para.power_relaf);
			
			para.power_rela1 = ComntStream.readStream(byte_vect, offset + ret_len, para.power_rela1); 
			ret_len += ComntStream.getDataSize(para.power_rela1);
			
			para.power_rela2 = ComntStream.readStream(byte_vect, offset + ret_len, para.power_rela2); 
			ret_len += ComntStream.getDataSize(para.power_rela2);
			
			para.power_relask1 = ComntStream.readStream(byte_vect, offset + ret_len, para.power_relask1); 
			ret_len += ComntStream.getDataSize(para.power_relask1);
			
			para.power_relask2 = ComntStream.readStream(byte_vect, offset + ret_len, para.power_relask2); 
			ret_len += ComntStream.getDataSize(para.power_relask2);
			
			para.fee_chgf = ComntStream.readStream(byte_vect, offset + ret_len, para.fee_chgf); 
			ret_len += ComntStream.getDataSize(para.fee_chgf);
			
			para.fee_chgid = ComntStream.readStream(byte_vect, offset + ret_len, para.fee_chgid); 
			ret_len += ComntStream.getDataSize(para.fee_chgid);
			
			para.fee_chgdate = ComntStream.readStream(byte_vect, offset + ret_len, para.fee_chgdate); 
			ret_len += ComntStream.getDataSize(para.fee_chgdate);
			
			para.fee_chgtime = ComntStream.readStream(byte_vect, offset + ret_len, para.fee_chgtime); 
			ret_len += ComntStream.getDataSize(para.fee_chgtime);
			
			para.jt_cycle_md = ComntStream.readStream(byte_vect, offset + ret_len, para.jt_cycle_md); 
			ret_len += ComntStream.getDataSize(para.jt_cycle_md);			
			
			para.cb_cycle_type = ComntStream.readStream(byte_vect, offset + ret_len, para.cb_cycle_type); 
			ret_len += ComntStream.getDataSize(para.cb_cycle_type);
			
			para.cb_dayhour = ComntStream.readStream(byte_vect, offset + ret_len, para.cb_dayhour); 
			ret_len += ComntStream.getDataSize(para.cb_dayhour);
			
			para.js_day = ComntStream.readStream(byte_vect, offset + ret_len, para.js_day); 
			ret_len += ComntStream.getDataSize(para.js_day);
			
			para.fxdf_flag = ComntStream.readStream(byte_vect, offset + ret_len, para.fxdf_flag); 
			ret_len += ComntStream.getDataSize(para.fxdf_flag);
			
			para.fxdf_begindate = ComntStream.readStream(byte_vect, offset + ret_len, para.fxdf_begindate); 
			ret_len += ComntStream.getDataSize(para.fxdf_begindate);
			
			para.local_maincalcf = ComntStream.readStream(byte_vect, offset + ret_len, para.local_maincalcf); 
			ret_len += ComntStream.getDataSize(para.local_maincalcf);	
			
			para.ocardproj_id = ComntStream.readStream(byte_vect, offset + ret_len, para.ocardproj_id); 
			ret_len += ComntStream.getDataSize(para.ocardproj_id);	
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.card_rand, 0, para.card_rand.length);
			ret_len += ComntStream.getDataSize(para.card_rand[0]) * para.card_rand.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.card_pass, 0, para.card_pass.length);
			ret_len += ComntStream.getDataSize(para.card_pass[0]) * para.card_pass.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.card_area, 0, para.card_area.length);
			ret_len += ComntStream.getDataSize(para.card_area[0]) * para.card_area.length;
			
			return ret_len;
		}
	}
	
	/**
	 * 计量点预付费状态表--[消息相关,请不要随意改动]
	 */
	public static class YFF_MPPAYSTATE{
		public int		rtu_id 		= 0;						//终端编号
		public short	mp_id 		= 0;						//测量点编号
		public byte		cus_state 	= 0;						//用户状态 0 初始态, 1 正常态, 10 销户态
		public byte		op_type 	= 0;						//本次操作类型 0 初始态, 1 开户, 2 缴费, 3 补卡, 4 补写卡, 5 冲正, 6 换表, 7 换费率 10 销户 
		public int		op_date 	= 0;						//本次操作日期
		public int		op_time 	= 0;						//本次操作时间
		public double	pay_money 	= 0;						//缴费金额
		public double	othjs_money = 0;						//结算金额(其它系统)
		public double	zb_money 	= 0;						//追补金额
		public double	all_money 	= 0;						//总金额
		
		//20140606 zhp 新增
		public double	buy_dl		= 0.0;						//购电量
		public double	pay_bmc		= 0.0;						//表码差
		//end
		
		public double	shutdown_val= 0;						//断电值 金额计费时为:断电金额
		public double	shutdown_val2 = 0;						//断电值 本地模拟电表
		
		public double	jsbd_zyz[] = new double[ComntMsg.YFF_MPPAY_METERNUM];		//结算总表底
		public double	jsbd_zyj[] = new double[ComntMsg.YFF_MPPAY_METERNUM];		//结算尖表底
		public double	jsbd_zyf[] = new double[ComntMsg.YFF_MPPAY_METERNUM];		//结算峰表底
		public double	jsbd_zyp[] = new double[ComntMsg.YFF_MPPAY_METERNUM];		//结算平表底
		public double	jsbd_zyg[] = new double[ComntMsg.YFF_MPPAY_METERNUM];		//结算谷表底

		public int		jsbd_ymd   = 0;							//结算时间
		public int		buy_times  = 0;							//购电次数
		public int		calc_mdhmi = 0;							//算费时间-MMDDHHMI
		public int		calc_bdymd = 0;							//算费表底时间-YYYYMMDD
		
		public double	calc_zyz[] = new double[ComntMsg.YFF_MPPAY_METERNUM];		//算费时总表底
		public double	calc_zyj[] = new double[ComntMsg.YFF_MPPAY_METERNUM];		//算费时尖表底
		public double	calc_zyf[] = new double[ComntMsg.YFF_MPPAY_METERNUM];		//算费时峰表底
		public double	calc_zyp[] = new double[ComntMsg.YFF_MPPAY_METERNUM];		//算费时平表底
		public double	calc_zyg[] = new double[ComntMsg.YFF_MPPAY_METERNUM];		//算费时谷表底
		
		public double	now_remain		= 0;								//当前剩余
		public double	now_remain2		= 0;								//当前剩余(本地模拟电表)
		
		public double	bj_bd 			= 0;								//报警门限
		public double	tz_bd 			= 0;								//跳闸门限
		public byte		cs_al1_state 	= 0;								//报警1状态  0:正常状态 1:报警状态
		public byte		cs_al2_state 	= 0;								//报警2状态  0:正常状态 1:报警状态
		public byte		cs_fhz_state 	= 0;								//分合闸状态 0:分闸状态 1:合闸状态
		public int		al1_mdhmi 		= 0;								//报警1状态改变时间-MMDDHHMI
		public int		al2_mdhmi 		= 0;								//报警2状态改变时间-MMDDHHMI
		public int		fhz_mdhmi 		= 0;								//分合闸状态改变时间-MMDDHHMI
		public double	fz_zyz[] 		= new double[ComntMsg.YFF_MPPAY_METERNUM];			//分闸时总表底
		public int		yc_flag1 		= 0;								//异常标志1, 参数错误 
		public int		yc_flag2 		= 0;								//异常标志2(按位标志), 数据错误 0位:分闸后表字继续走 1位:表底飞走 2位:表底倒转 3位:长时间无数据 4位:长时间不缴费
		public int		yc_flag3 		= 0;								//异常标志3, 备用 
		public int		hb_date 		= 0;								//上次换表日期
		public int		hb_time 		= 0;								//上次换表时间
		public int		kh_date 		= 0;								//开户日期-YYYYMMDD
		public int		xh_date 		= 0;								//销户日期-YYYYMMDD
		public double	total_gdz 		= 0;								//累计购电值
		
		//20120516
		public double	jt_total_zbdl	= 0;								//阶梯追补累计用电量
		public double	jt_total_dl		= 0;								//阶梯累计用电量
		public int		jt_reset_ymd	= 0;								//阶梯切换日期
		public int		jt_reset_mdhmi	= 0;								//阶梯切换执行时间

		public double	fxdf_iall_money	= 0;								//发行电费当月缴费总金额
		public double	fxdf_iall_money2= 0;								//发行电费当月缴费总金额2

		public double	fxdf_remain		= 0;								//发行电费当月剩余金额
		public double	fxdf_remain2	= 0;								//发行电费当月剩余金额2-模拟电表

		public int		fxdf_ym			= 0;								//发行电费年月 YYYYMM
		public int		fxdf_data_ymd	= 0;								//发行电费数据日期 YYYYMMDDD
		public int		fxdf_calc_mdhmi	= 0;								//发行电费算费时间 -MMDDHHMI

		public int		js_bc_ymd		= 0;								//结算补差日期 YYYYMMDD
		
		public int		reserve1 		= 0;								//保留
		
		public static void setYFF_MPPAYSTATE(YFF_MPPAYSTATE para, YFF_MPPAYSTATE setValue){
			if(setValue == null){
				para = new YFF_MPPAYSTATE();
			}else{
				para.rtu_id 		= setValue.rtu_id;
				para.mp_id 			= setValue.mp_id;
				para.cus_state 		= setValue.cus_state;
				para.op_type 		= setValue.op_type;
				para.op_date 		= setValue.op_date;
				para.op_time 		= setValue.op_time;
				para.pay_money 		= setValue.pay_money;
				para.othjs_money 	= setValue.othjs_money;
				para.zb_money 		= setValue.zb_money;
				para.all_money 		= setValue.all_money;
				//20140606
				para.buy_dl			= setValue.buy_dl;						
				para.pay_bmc		= setValue.pay_bmc;
				//end
				para.shutdown_val 	= setValue.shutdown_val;
				para.shutdown_val2 	= setValue.shutdown_val2;
				
				for(int i=0;i<ComntMsg.YFF_MPPAY_METERNUM;i++){
					para.jsbd_zyz[i] = setValue.jsbd_zyz[i];
					para.jsbd_zyj[i] = setValue.jsbd_zyj[i];
					para.jsbd_zyf[i] = setValue.jsbd_zyf[i];
					para.jsbd_zyp[i] = setValue.jsbd_zyp[i];
					para.jsbd_zyg[i] = setValue.jsbd_zyg[i];
					para.calc_zyz[i] = setValue.calc_zyz[i];
					para.calc_zyj[i] = setValue.calc_zyj[i];
					para.calc_zyf[i] = setValue.calc_zyf[i];
					para.calc_zyp[i] = setValue.calc_zyp[i];
					para.calc_zyg[i] = setValue.calc_zyg[i];
					para.fz_zyz[i] = setValue.fz_zyz[i];
				}

				para.jsbd_ymd 		= setValue.jsbd_ymd;
				para.buy_times 		= setValue.buy_times;
				para.calc_mdhmi 	= setValue.calc_mdhmi;
				para.calc_bdymd 	= setValue.calc_bdymd;
				para.now_remain 	= setValue.now_remain;
				para.now_remain2 	= setValue.now_remain2;				
				para.bj_bd 			= setValue.bj_bd;
				para.tz_bd 			= setValue.tz_bd;
				para.cs_al1_state 	= setValue.cs_al1_state;
				para.cs_al2_state 	= setValue.cs_al2_state;
				para.cs_fhz_state 	= setValue.cs_fhz_state;
				para.al1_mdhmi 		= setValue.al1_mdhmi;
				para.al2_mdhmi 		= setValue.al2_mdhmi;
				para.fhz_mdhmi 		= setValue.fhz_mdhmi;
				para.yc_flag1 		= setValue.yc_flag1;
				para.yc_flag2 		= setValue.yc_flag2;
				para.yc_flag3 		= setValue.yc_flag3;
				para.hb_date 		= setValue.hb_date;
				para.hb_time 		= setValue.hb_time;
				para.kh_date 		= setValue.kh_date;
				para.xh_date 		= setValue.xh_date;
				para.total_gdz 		= setValue.total_gdz;

				para.jt_total_zbdl	= setValue.jt_total_zbdl;
				para.jt_total_dl	= setValue.jt_total_dl;
				para.jt_reset_ymd	= setValue.jt_reset_ymd;
				para.jt_reset_mdhmi	= setValue.jt_reset_mdhmi;

				para.fxdf_iall_money= setValue.fxdf_iall_money;
				para.fxdf_iall_money2= setValue.fxdf_iall_money2;

				para.fxdf_remain	= setValue.fxdf_remain;
				para.fxdf_remain2	= setValue.fxdf_remain;

				para.fxdf_ym		= setValue.fxdf_ym;
				para.fxdf_data_ymd	= setValue.fxdf_data_ymd;
				para.fxdf_calc_mdhmi= setValue.fxdf_calc_mdhmi;

				para.js_bc_ymd		= setValue.js_bc_ymd;
				
				para.reserve1 		= setValue.reserve1;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, YFF_MPPAYSTATE para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.rtu_id);
			ret_len += ComntStream.writeStream(byte_vect, para.mp_id);
			ret_len += ComntStream.writeStream(byte_vect, para.cus_state);
			ret_len += ComntStream.writeStream(byte_vect, para.op_type);
			ret_len += ComntStream.writeStream(byte_vect, para.op_date);
			ret_len += ComntStream.writeStream(byte_vect, para.op_time);
			ret_len += ComntStream.writeStream(byte_vect, para.pay_money);
			ret_len += ComntStream.writeStream(byte_vect, para.othjs_money);
			ret_len += ComntStream.writeStream(byte_vect, para.zb_money);
			ret_len += ComntStream.writeStream(byte_vect, para.all_money);
			//20140606
			ret_len += ComntStream.writeStream(byte_vect, para.buy_dl);
			ret_len += ComntStream.writeStream(byte_vect, para.pay_bmc);
			//end
			ret_len += ComntStream.writeStream(byte_vect, para.shutdown_val);
			ret_len += ComntStream.writeStream(byte_vect, para.shutdown_val2);
			for(int i = 0; i< ComntMsg.YFF_MPPAY_METERNUM ; i++){
				ret_len += ComntStream.writeStream(byte_vect, para.jsbd_zyz[i]);
				ret_len += ComntStream.writeStream(byte_vect, para.jsbd_zyj[i]);
				ret_len += ComntStream.writeStream(byte_vect, para.jsbd_zyf[i]);
				ret_len += ComntStream.writeStream(byte_vect, para.jsbd_zyp[i]);
				ret_len += ComntStream.writeStream(byte_vect, para.jsbd_zyg[i]);
			}
			ret_len += ComntStream.writeStream(byte_vect, para.jsbd_ymd);
			ret_len += ComntStream.writeStream(byte_vect, para.buy_times);
			ret_len += ComntStream.writeStream(byte_vect, para.calc_mdhmi);
			ret_len += ComntStream.writeStream(byte_vect, para.calc_bdymd);
			
			for(int i = 0; i< ComntMsg.YFF_MPPAY_METERNUM ; i++){
				ret_len += ComntStream.writeStream(byte_vect, para.calc_zyz[i]);
				ret_len += ComntStream.writeStream(byte_vect, para.calc_zyj[i]);
				ret_len += ComntStream.writeStream(byte_vect, para.calc_zyf[i]);
				ret_len += ComntStream.writeStream(byte_vect, para.calc_zyp[i]);
				ret_len += ComntStream.writeStream(byte_vect, para.calc_zyg[i]);
			}
			ret_len += ComntStream.writeStream(byte_vect, para.now_remain);
			ret_len += ComntStream.writeStream(byte_vect, para.now_remain2);
			ret_len += ComntStream.writeStream(byte_vect, para.bj_bd);
			ret_len += ComntStream.writeStream(byte_vect, para.tz_bd);
			ret_len += ComntStream.writeStream(byte_vect, para.cs_al1_state);
			ret_len += ComntStream.writeStream(byte_vect, para.cs_al2_state);
			ret_len += ComntStream.writeStream(byte_vect, para.cs_fhz_state);
			ret_len += ComntStream.writeStream(byte_vect, para.al1_mdhmi);
			ret_len += ComntStream.writeStream(byte_vect, para.al2_mdhmi);
			ret_len += ComntStream.writeStream(byte_vect, para.fhz_mdhmi);
			
			for(int i = 0; i< ComntMsg.YFF_MPPAY_METERNUM ; i++){
				ret_len += ComntStream.writeStream(byte_vect, para.fz_zyz[i]);
			}
			ret_len += ComntStream.writeStream(byte_vect, para.yc_flag1);
			ret_len += ComntStream.writeStream(byte_vect, para.yc_flag2);
			ret_len += ComntStream.writeStream(byte_vect, para.yc_flag3);
			ret_len += ComntStream.writeStream(byte_vect, para.hb_date);
			ret_len += ComntStream.writeStream(byte_vect, para.hb_time);
			ret_len += ComntStream.writeStream(byte_vect, para.kh_date);
			ret_len += ComntStream.writeStream(byte_vect, para.xh_date);
			ret_len += ComntStream.writeStream(byte_vect, para.total_gdz);
			
			ret_len += ComntStream.writeStream(byte_vect, para.jt_total_zbdl);	
			ret_len += ComntStream.writeStream(byte_vect, para.jt_total_dl);
			ret_len += ComntStream.writeStream(byte_vect, para.jt_reset_ymd);
			ret_len += ComntStream.writeStream(byte_vect, para.jt_reset_mdhmi);
			ret_len += ComntStream.writeStream(byte_vect, para.fxdf_iall_money);
			ret_len += ComntStream.writeStream(byte_vect, para.fxdf_iall_money2);
			ret_len += ComntStream.writeStream(byte_vect, para.fxdf_remain);
			ret_len += ComntStream.writeStream(byte_vect, para.fxdf_remain2);
			ret_len += ComntStream.writeStream(byte_vect, para.fxdf_ym);
			ret_len += ComntStream.writeStream(byte_vect, para.fxdf_data_ymd);
			ret_len += ComntStream.writeStream(byte_vect, para.fxdf_calc_mdhmi);
			ret_len += ComntStream.writeStream(byte_vect, para.js_bc_ymd);
			
			ret_len += ComntStream.writeStream(byte_vect, para.reserve1);
			
			return ret_len;
		}
		
		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFF_MPPAYSTATE para){
			int ret_len   = len;
			int i 		  = 0;
			
			para.rtu_id = ComntStream.readStream(byte_vect, offset + ret_len, para.rtu_id); 
			ret_len += ComntStream.getDataSize(para.rtu_id);
			
			para.mp_id = ComntStream.readStream(byte_vect, offset + ret_len, para.mp_id); 
			ret_len += ComntStream.getDataSize(para.mp_id);
			
			para.cus_state = ComntStream.readStream(byte_vect, offset + ret_len, para.cus_state); 
			ret_len += ComntStream.getDataSize(para.cus_state);
			
			para.op_type = ComntStream.readStream(byte_vect, offset + ret_len, para.op_type); 
			ret_len += ComntStream.getDataSize(para.op_type);
			
			para.op_date = ComntStream.readStream(byte_vect, offset + ret_len, para.op_date); 
			ret_len += ComntStream.getDataSize(para.op_date);
			
			para.op_time = ComntStream.readStream(byte_vect, offset + ret_len, para.op_time); 
			ret_len += ComntStream.getDataSize(para.op_time);
			
			para.pay_money = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_money); 
			ret_len += ComntStream.getDataSize(para.pay_money);
			
			para.othjs_money = ComntStream.readStream(byte_vect, offset + ret_len, para.othjs_money); 
			ret_len += ComntStream.getDataSize(para.othjs_money);
			
			para.zb_money = ComntStream.readStream(byte_vect, offset + ret_len, para.zb_money); 
			ret_len += ComntStream.getDataSize(para.zb_money);
			
			para.all_money = ComntStream.readStream(byte_vect, offset + ret_len, para.all_money); 
			ret_len += ComntStream.getDataSize(para.all_money);
			
			//20140606
			para.buy_dl = ComntStream.readStream(byte_vect, offset + ret_len, para.buy_dl); 
			ret_len += ComntStream.getDataSize(para.buy_dl);
			
			para.pay_bmc = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_bmc); 
			ret_len += ComntStream.getDataSize(para.pay_bmc);
			//end
			
			para.shutdown_val = ComntStream.readStream(byte_vect, offset + ret_len, para.shutdown_val); 
			ret_len += ComntStream.getDataSize(para.shutdown_val);

			para.shutdown_val2 = ComntStream.readStream(byte_vect, offset + ret_len, para.shutdown_val2); 
			ret_len += ComntStream.getDataSize(para.shutdown_val2);			
			
			for (i = 0;i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				para.jsbd_zyz[i] = ComntStream.readStream(byte_vect, offset + ret_len, para.jsbd_zyz[i]); 
				ret_len += ComntStream.getDataSize(para.jsbd_zyz[i]);	
				
				para.jsbd_zyj[i] = ComntStream.readStream(byte_vect, offset + ret_len, para.jsbd_zyj[i]); 
				ret_len += ComntStream.getDataSize(para.jsbd_zyj[i]);
				
				para.jsbd_zyf[i] = ComntStream.readStream(byte_vect, offset + ret_len, para.jsbd_zyf[i]); 
				ret_len += ComntStream.getDataSize(para.jsbd_zyf[i]);
				
				para.jsbd_zyp[i] = ComntStream.readStream(byte_vect, offset + ret_len, para.jsbd_zyp[i]); 
				ret_len += ComntStream.getDataSize(para.jsbd_zyp[i]);
				
				para.jsbd_zyg[i] = ComntStream.readStream(byte_vect, offset + ret_len, para.jsbd_zyg[i]); 
				ret_len += ComntStream.getDataSize(para.jsbd_zyg[i]);
			}
			
			para.jsbd_ymd = ComntStream.readStream(byte_vect, offset + ret_len, para.jsbd_ymd); 
			ret_len += ComntStream.getDataSize(para.jsbd_ymd);
			
			para.buy_times = ComntStream.readStream(byte_vect, offset + ret_len, para.buy_times); 
			ret_len += ComntStream.getDataSize(para.buy_times);
			
			para.calc_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.calc_mdhmi); 
			ret_len += ComntStream.getDataSize(para.calc_mdhmi);
			
			para.calc_bdymd = ComntStream.readStream(byte_vect, offset + ret_len, para.calc_bdymd); 
			ret_len += ComntStream.getDataSize(para.calc_bdymd);
			
			for (i = 0;i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				para.calc_zyz[i] = ComntStream.readStream(byte_vect, offset + ret_len, para.calc_zyz[i]); 
				ret_len += ComntStream.getDataSize(para.calc_zyz[i]);
				
				para.calc_zyj[i] = ComntStream.readStream(byte_vect, offset + ret_len, para.calc_zyj[i]); 
				ret_len += ComntStream.getDataSize(para.calc_zyj[i]);
				
				para.calc_zyf[i] = ComntStream.readStream(byte_vect, offset + ret_len, para.calc_zyf[i]); 
				ret_len += ComntStream.getDataSize(para.calc_zyf[i]);
				
				para.calc_zyp[i] = ComntStream.readStream(byte_vect, offset + ret_len, para.calc_zyp[i]); 
				ret_len += ComntStream.getDataSize(para.calc_zyp[i]);
				
				para.calc_zyg[i] = ComntStream.readStream(byte_vect, offset + ret_len, para.calc_zyg[i]); 
				ret_len += ComntStream.getDataSize(para.calc_zyg[i]);
			}
						
			para.now_remain = ComntStream.readStream(byte_vect, offset + ret_len, para.now_remain); 
			ret_len += ComntStream.getDataSize(para.now_remain);
			
			para.now_remain2 = ComntStream.readStream(byte_vect, offset + ret_len, para.now_remain2); 
			ret_len += ComntStream.getDataSize(para.now_remain2);
			
			para.bj_bd = ComntStream.readStream(byte_vect, offset + ret_len, para.bj_bd); 
			ret_len += ComntStream.getDataSize(para.bj_bd);
			
			para.tz_bd = ComntStream.readStream(byte_vect, offset + ret_len, para.tz_bd); 
			ret_len += ComntStream.getDataSize(para.tz_bd);
			
			para.cs_al1_state = ComntStream.readStream(byte_vect, offset + ret_len, para.cs_al1_state); 
			ret_len += ComntStream.getDataSize(para.cs_al1_state);
			
			para.cs_al2_state = ComntStream.readStream(byte_vect, offset + ret_len, para.cs_al2_state); 
			ret_len += ComntStream.getDataSize(para.cs_al2_state);
			
			para.cs_fhz_state = ComntStream.readStream(byte_vect, offset + ret_len, para.cs_fhz_state); 
			ret_len += ComntStream.getDataSize(para.cs_fhz_state);
			
			para.al1_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.al1_mdhmi); 
			ret_len += ComntStream.getDataSize(para.al1_mdhmi);
			
			para.al2_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.al2_mdhmi); 
			ret_len += ComntStream.getDataSize(para.al2_mdhmi);
			
			para.fhz_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.fhz_mdhmi); 
			ret_len += ComntStream.getDataSize(para.fhz_mdhmi);
			
			for (i = 0;i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				para.fz_zyz[i]   = ComntStream.readStream(byte_vect, offset + ret_len, para.fz_zyz[i]);
				ret_len += ComntStream.getDataSize(para.fz_zyz[i]);
			}
			
			para.yc_flag1 = ComntStream.readStream(byte_vect, offset + ret_len, para.yc_flag1); 
			ret_len += ComntStream.getDataSize(para.yc_flag1);
			
			para.yc_flag2 = ComntStream.readStream(byte_vect, offset + ret_len, para.yc_flag2); 
			ret_len += ComntStream.getDataSize(para.yc_flag2);
			
			para.yc_flag3 = ComntStream.readStream(byte_vect, offset + ret_len, para.yc_flag3); 
			ret_len += ComntStream.getDataSize(para.yc_flag3);
			
			para.hb_date = ComntStream.readStream(byte_vect, offset + ret_len, para.hb_date); 
			ret_len += ComntStream.getDataSize(para.hb_date);
			
			para.hb_time = ComntStream.readStream(byte_vect, offset + ret_len, para.hb_time); 
			ret_len += ComntStream.getDataSize(para.hb_time);
			
			para.kh_date = ComntStream.readStream(byte_vect, offset + ret_len, para.kh_date); 
			ret_len += ComntStream.getDataSize(para.kh_date);
			
			para.xh_date = ComntStream.readStream(byte_vect, offset + ret_len, para.xh_date); 
			ret_len += ComntStream.getDataSize(para.xh_date);
			
			para.total_gdz = ComntStream.readStream(byte_vect, offset + ret_len, para.total_gdz); 
			ret_len += ComntStream.getDataSize(para.total_gdz);
			
			para.jt_total_zbdl = ComntStream.readStream(byte_vect, offset + ret_len, para.jt_total_zbdl); 
			ret_len += ComntStream.getDataSize(para.jt_total_zbdl);
			
			para.jt_total_dl = ComntStream.readStream(byte_vect, offset + ret_len, para.jt_total_dl); 
			ret_len += ComntStream.getDataSize(para.jt_total_dl);
			
			para.jt_reset_ymd = ComntStream.readStream(byte_vect, offset + ret_len, para.jt_reset_ymd); 
			ret_len += ComntStream.getDataSize(para.jt_reset_ymd);

			para.jt_reset_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.jt_reset_mdhmi); 
			ret_len += ComntStream.getDataSize(para.jt_reset_mdhmi);			

			para.fxdf_iall_money = ComntStream.readStream(byte_vect, offset + ret_len, para.fxdf_iall_money); 
			ret_len += ComntStream.getDataSize(para.fxdf_iall_money);
			
			para.fxdf_iall_money2 = ComntStream.readStream(byte_vect, offset + ret_len, para.fxdf_iall_money2); 
			ret_len += ComntStream.getDataSize(para.fxdf_iall_money2);
			
			para.fxdf_remain = ComntStream.readStream(byte_vect, offset + ret_len, para.fxdf_remain); 
			ret_len += ComntStream.getDataSize(para.fxdf_remain);
			
			para.fxdf_remain2 = ComntStream.readStream(byte_vect, offset + ret_len, para.fxdf_remain2); 
			ret_len += ComntStream.getDataSize(para.fxdf_remain2);
			
			para.fxdf_ym = ComntStream.readStream(byte_vect, offset + ret_len, para.fxdf_ym); 
			ret_len += ComntStream.getDataSize(para.fxdf_ym);
			
			para.fxdf_data_ymd = ComntStream.readStream(byte_vect, offset + ret_len, para.fxdf_data_ymd); 
			ret_len += ComntStream.getDataSize(para.fxdf_data_ymd);
			
			para.fxdf_calc_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.fxdf_calc_mdhmi); 
			ret_len += ComntStream.getDataSize(para.fxdf_calc_mdhmi);
			
			para.js_bc_ymd = ComntStream.readStream(byte_vect, offset + ret_len, para.js_bc_ymd); 
			ret_len += ComntStream.getDataSize(para.js_bc_ymd);
			
			para.reserve1 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve1); 
			ret_len += ComntStream.getDataSize(para.reserve1);
			
			return ret_len;
		}
		
	}
	
	
	/**
	 * 计量点预付费报警表--[消息相关,请不要随意改动]
	 */
	public static class YFF_MPPAY_ALARMSTATE{
		public int		rtu_id = 0;								//终端编号
		public short	mp_id = 0;								//测量点编号

		public byte		qr_al1_1_state = 0;						//报警1-1确认状态(短信方式) 低6位(重试次数0~63) 高2位(0:初始态 1:等待 2:成功 3:失败)
		public byte		qr_al1_2_state = 0;						//报警1-2确认状态(声音方式)
		public byte		qr_al1_3_state = 0;						//报警1-3确认状态(备用方式)

		public byte		qr_al2_1_state = 0;						//报警2-1确认状态(短信方式)
		public byte		qr_al2_2_state = 0;						//报警2-2确认状态(声音方式)
		public byte		qr_al2_3_state = 0;						//报警2-3确认状态(备用方式)

		public byte		qr_fhz_state = 0;						//分合闸确认状态
		public byte		qr_fhz_rf1_state = 0;					//分合闸确认状态(动力关联1)
		public byte		qr_fhz_rf2_state = 0;					//分合闸确认状态(动力关联2)

		public byte		qr_fz_times = 0;						//分闸次数
		public byte		qr_fz_rf1_times = 0;					//分闸次数(动力关联1)
		public byte		qr_fz_rf2_times = 0;					//分闸次数(动力关联2)

		public int		qr_al1_1_mdhmi = 0;						//报警1-1确认状态(短信方式) 发送时间
		public int		qr_al1_2_mdhmi = 0;						//报警1-2确认状态(声音方式) 发送时间
		public int		qr_al1_3_mdhmi = 0;						//报警1-3确认状态(备用方式) 发送时间

		public int		qr_al2_1_mdhmi = 0;						//报警2-1确认状态(短信方式) 发送时间
		public int		qr_al2_2_mdhmi = 0;						//报警2-2确认状态(声音方式) 发送时间
		public int		qr_al2_3_mdhmi = 0;						//报警2-3确认状态(备用方式) 发送时间

		public int		qr_fhz_mdhmi = 0;						//分合闸确认状态 发送时间
		public int		qr_fhz_rf1_mdhmi = 0;					//分合闸确认状态 发送时间(动力关联1)
		public int		qr_fhz_rf2_mdhmi = 0;					//分合闸确认状态 发送时间(动力关联2)

		public int		cg_fhz_mdhmi = 0;						//成功分合闸时间
		public int		cg_fhz_rf1_mdhmi = 0;					//成功分合闸时间(动力关联1)
		public int		cg_fhz_rf2_mdhmi = 0;					//成功分合闸时间(动力关联2)

		public int		qr_al1_1_uuid = 0;						//报警1-1确认状态(短信方式) UUID
		public int		qr_al2_1_uuid = 0;						//报警2-1确认状态(短信方式) UUID
		public byte		out_info[] = new byte[ComntMsg.MSG_STR_LEN_64];				//信息输出
		
		public static void setYFF_MPPAY_ALARMSTATE(YFF_MPPAY_ALARMSTATE para, YFF_MPPAY_ALARMSTATE setValue){
			if(setValue == null){
				para = new YFF_MPPAY_ALARMSTATE();
			}else{
				para.rtu_id = setValue.rtu_id;					
				para.mp_id = setValue.mp_id;					

				para.qr_al1_1_state = setValue.qr_al1_1_state;				
				para.qr_al1_2_state = setValue.qr_al1_2_state;				
				para.qr_al1_3_state = setValue.qr_al1_3_state;				

				para.qr_al2_1_state = setValue.qr_al2_1_state;				
				para.qr_al2_2_state = setValue.qr_al2_2_state;				
				para.qr_al2_3_state = setValue.qr_al2_3_state;				

				para.qr_fhz_state = setValue.qr_fhz_state;				
				para.qr_fhz_rf1_state = setValue.qr_fhz_rf1_state;				
				para.qr_fhz_rf2_state = setValue.qr_fhz_rf2_state;				

				para.qr_fz_times = setValue.qr_fz_times;				
				para.qr_fz_rf1_times = setValue.qr_fz_rf1_times;				
				para.qr_fz_rf2_times = setValue.qr_fz_rf2_times;				

				para.qr_al1_1_mdhmi = setValue.qr_al1_1_mdhmi;				
				para.qr_al1_2_mdhmi = setValue.qr_al1_2_mdhmi;				
				para.qr_al1_3_mdhmi = setValue.qr_al1_3_mdhmi;				

				para.qr_al2_1_mdhmi = setValue.qr_al2_1_mdhmi;				
				para.qr_al2_2_mdhmi = setValue.qr_al2_2_mdhmi;				
				para.qr_al2_3_mdhmi = setValue.qr_al2_3_mdhmi;				

				para.qr_fhz_mdhmi = setValue.qr_fhz_mdhmi;				
				para.qr_fhz_rf1_mdhmi = setValue.qr_fhz_rf1_mdhmi;				
				para.qr_fhz_rf2_mdhmi = setValue.qr_fhz_rf2_mdhmi;				

				para.cg_fhz_mdhmi = setValue.cg_fhz_mdhmi;				
				para.cg_fhz_rf1_mdhmi = setValue.cg_fhz_rf1_mdhmi;				
				para.cg_fhz_rf2_mdhmi = setValue.cg_fhz_rf2_mdhmi;				

				para.qr_al1_1_uuid = setValue.qr_al1_1_uuid;				
				para.qr_al2_1_uuid = setValue.qr_al2_1_uuid;				
				for(int i=0;i<ComntMsg.MSG_STR_LEN_64;i++){
					para.out_info[i] = setValue.out_info[i];
				}
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, YFF_MPPAY_ALARMSTATE para){
			int ret_len   = 0;

			ret_len += ComntStream.writeStream(byte_vect, para.rtu_id);
			ret_len += ComntStream.writeStream(byte_vect, para.mp_id);	

			ret_len += ComntStream.writeStream(byte_vect, para.qr_al1_1_state);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al1_2_state);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al1_3_state);	

			ret_len += ComntStream.writeStream(byte_vect, para.qr_al2_1_state);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al2_2_state);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al2_3_state);	

			ret_len += ComntStream.writeStream(byte_vect, para.qr_fhz_state);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_fhz_rf1_state);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_fhz_rf2_state);	

			ret_len += ComntStream.writeStream(byte_vect, para.qr_fz_times);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_fz_rf1_times);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_fz_rf2_times);	
			
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al1_1_mdhmi);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al1_2_mdhmi);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al1_3_mdhmi);	

			ret_len += ComntStream.writeStream(byte_vect, para.qr_al2_1_mdhmi);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al2_2_mdhmi);
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al2_3_mdhmi);	

			ret_len += ComntStream.writeStream(byte_vect, para.qr_fhz_mdhmi);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_fhz_rf1_mdhmi);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_fhz_rf2_mdhmi);	

			ret_len += ComntStream.writeStream(byte_vect, para.cg_fhz_mdhmi);	
			ret_len += ComntStream.writeStream(byte_vect, para.cg_fhz_rf1_mdhmi);	
			ret_len += ComntStream.writeStream(byte_vect, para.cg_fhz_rf2_mdhmi);	

			ret_len += ComntStream.writeStream(byte_vect, para.qr_al1_1_uuid);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al2_1_uuid);	
			
			ret_len += ComntStream.writeStream(byte_vect, para.out_info, 0, para.out_info.length);
			
			return ret_len;
		}
		
		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFF_MPPAY_ALARMSTATE para){
			int ret_len   = len;
			
			para.rtu_id = ComntStream.readStream(byte_vect, offset + ret_len, para.rtu_id); 
			ret_len += ComntStream.getDataSize(para.rtu_id);
			
			para.mp_id = ComntStream.readStream(byte_vect, offset + ret_len, para.mp_id); 
			ret_len += ComntStream.getDataSize(para.mp_id);
			
			para.qr_al1_1_state = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al1_1_state); 
			ret_len += ComntStream.getDataSize(para.qr_al1_1_state);
			
			para.qr_al1_2_state = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al1_2_state); 
			ret_len += ComntStream.getDataSize(para.qr_al1_2_state);
			
			para.qr_al1_3_state = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al1_3_state); 
			ret_len += ComntStream.getDataSize(para.qr_al1_3_state);
			
			para.qr_al2_1_state = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al2_1_state); 
			ret_len += ComntStream.getDataSize(para.qr_al2_1_state);
			
			para.qr_al2_2_state = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al2_2_state); 
			ret_len += ComntStream.getDataSize(para.qr_al2_2_state);
			
			para.qr_al2_3_state = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al2_3_state); 
			ret_len += ComntStream.getDataSize(para.qr_al2_3_state);
			
			para.qr_fhz_state = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_fhz_state); 
			ret_len += ComntStream.getDataSize(para.qr_fhz_state);
			
			para.qr_fhz_rf1_state = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_fhz_rf1_state); 
			ret_len += ComntStream.getDataSize(para.qr_fhz_rf1_state);
			
			para.qr_fhz_rf2_state = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_fhz_rf2_state); 
			ret_len += ComntStream.getDataSize(para.qr_fhz_rf2_state);
			
			para.qr_fz_times = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_fz_times); 
			ret_len += ComntStream.getDataSize(para.qr_fz_times);
			
			para.qr_fz_rf1_times = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_fz_rf1_times); 
			ret_len += ComntStream.getDataSize(para.qr_fz_rf1_times);
			
			para.qr_fz_rf2_times = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_fz_rf2_times); 
			ret_len += ComntStream.getDataSize(para.qr_fz_rf2_times);
			
			para.qr_al1_1_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al1_1_mdhmi); 
			ret_len += ComntStream.getDataSize(para.qr_al1_1_mdhmi);
			
			para.qr_al1_2_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al1_2_mdhmi); 
			ret_len += ComntStream.getDataSize(para.qr_al1_2_mdhmi);
			
			para.qr_al1_3_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al1_3_mdhmi); 
			ret_len += ComntStream.getDataSize(para.qr_al1_3_mdhmi);
			
			para.qr_al2_1_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al2_1_mdhmi); 
			ret_len += ComntStream.getDataSize(para.qr_al2_1_mdhmi);
			
			para.qr_al2_2_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al2_2_mdhmi); 
			ret_len += ComntStream.getDataSize(para.qr_al2_2_mdhmi);
			
			para.qr_al2_3_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al2_3_mdhmi); 
			ret_len += ComntStream.getDataSize(para.qr_al2_3_mdhmi);
			
			para.qr_fhz_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_fhz_mdhmi); 
			ret_len += ComntStream.getDataSize(para.qr_fhz_mdhmi);
			
			para.qr_fhz_rf1_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_fhz_rf1_mdhmi); 
			ret_len += ComntStream.getDataSize(para.qr_fhz_rf1_mdhmi);
			
			para.qr_fhz_rf2_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_fhz_rf2_mdhmi); 
			ret_len += ComntStream.getDataSize(para.qr_fhz_rf2_mdhmi);
			
			para.cg_fhz_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.cg_fhz_mdhmi); 
			ret_len += ComntStream.getDataSize(para.cg_fhz_mdhmi);
			
			para.cg_fhz_rf1_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.cg_fhz_rf1_mdhmi); 
			ret_len += ComntStream.getDataSize(para.cg_fhz_rf1_mdhmi);
			
			para.cg_fhz_rf2_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.cg_fhz_rf2_mdhmi); 
			ret_len += ComntStream.getDataSize(para.cg_fhz_rf2_mdhmi);
			
			para.qr_al1_1_uuid = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al1_1_uuid); 
			ret_len += ComntStream.getDataSize(para.qr_al1_1_uuid);
			
			para.qr_al2_1_uuid = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al2_1_uuid); 
			ret_len += ComntStream.getDataSize(para.qr_al2_1_uuid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.out_info, 0, para.out_info.length);
			ret_len += ComntStream.getDataSize(para.out_info[0]) * para.out_info.length;
			
			return ret_len;
		}
	}
	
	
	
	/**
	 * 预付费报警临时表--[消息相关,请不要随意改动]
	 */
	public static class YFF_ALARM_COMMDATA{
		public int		tmp_al1_1_mdhmi = 0;					//报警1-1确认状态(短信方式) 发送时间
		public int		tmp_al1_2_mdhmi = 0;					//报警1-2确认状态(声音方式) 发送时间
		public int		tmp_al1_3_mdhmi = 0;					//报警1-3确认状态(备用方式) 发送时间

		public int		tmp_al2_1_mdhmi = 0;					//报警2-1确认状态(短信方式) 发送时间
		public int		tmp_al2_2_mdhmi = 0;					//报警2-2确认状态(声音方式) 发送时间
		public int		tmp_al2_3_mdhmi = 0;					//报警2-3确认状态(备用方式) 发送时间

		public int		tmp_fhz_mdhmi = 0;						//分合闸确认状态 发送时间
		public int		tmp_fhz_rf1_mdhmi = 0;					//分合闸确认状态 发送时间(居民动力1)
		public 	int		tmp_fhz_rf2_mdhmi = 0;					//分合闸确认状态 发送时间(居民动力2)

		public byte		tmp_prot_flag = 0;						//当前保电状态

		public byte		update_alarmstate = 0;					//报警状态更新数据库标志
		
		public static void setYFF_ALARM_COMMDATA(YFF_ALARM_COMMDATA para, YFF_ALARM_COMMDATA setValue){
			if(setValue == null){
				para = new YFF_ALARM_COMMDATA();
			}else{
				para.tmp_al1_1_mdhmi = setValue.tmp_al1_1_mdhmi;	
				para.tmp_al1_2_mdhmi = setValue.tmp_al1_2_mdhmi;	
				para.tmp_al1_3_mdhmi = setValue.tmp_al1_3_mdhmi;	
				
				para.tmp_al2_1_mdhmi = setValue.tmp_al2_1_mdhmi;	
				para.tmp_al2_2_mdhmi = setValue.tmp_al2_2_mdhmi;	
				para.tmp_al2_3_mdhmi = setValue.tmp_al2_3_mdhmi;	
				
				para.tmp_fhz_mdhmi = setValue.tmp_fhz_mdhmi;	
				para.tmp_fhz_rf1_mdhmi = setValue.tmp_fhz_rf1_mdhmi;
				para.tmp_fhz_rf2_mdhmi = setValue.tmp_fhz_rf2_mdhmi;
				
				para.tmp_prot_flag = setValue.tmp_prot_flag;	
				
				para.update_alarmstate = setValue.update_alarmstate;

			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, YFF_ALARM_COMMDATA para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.tmp_al1_1_mdhmi);	
			ret_len += ComntStream.writeStream(byte_vect, para.tmp_al1_2_mdhmi);	
			ret_len += ComntStream.writeStream(byte_vect, para.tmp_al1_3_mdhmi);	
			
			ret_len += ComntStream.writeStream(byte_vect, para.tmp_al2_1_mdhmi);	
			ret_len += ComntStream.writeStream(byte_vect, para.tmp_al2_2_mdhmi);	
			ret_len += ComntStream.writeStream(byte_vect, para.tmp_al2_3_mdhmi);	
			
			ret_len += ComntStream.writeStream(byte_vect, para.tmp_fhz_mdhmi);	
			ret_len += ComntStream.writeStream(byte_vect, para.tmp_fhz_rf1_mdhmi);
			ret_len += ComntStream.writeStream(byte_vect, para.tmp_fhz_rf2_mdhmi);
			
			ret_len += ComntStream.writeStream(byte_vect, para.tmp_prot_flag);	
			
			ret_len += ComntStream.writeStream(byte_vect, para.update_alarmstate);

			return ret_len;
		}
		
		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFF_ALARM_COMMDATA para){
			int ret_len   = len;
			
			para.tmp_al1_1_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.tmp_al1_1_mdhmi); 
			ret_len += ComntStream.getDataSize(para.tmp_al1_1_mdhmi);
			
			para.tmp_al1_2_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.tmp_al1_2_mdhmi); 
			ret_len += ComntStream.getDataSize(para.tmp_al1_2_mdhmi);
			
			para.tmp_al1_3_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.tmp_al1_3_mdhmi); 
			ret_len += ComntStream.getDataSize(para.tmp_al1_3_mdhmi);
			
			para.tmp_al2_1_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.tmp_al2_1_mdhmi); 
			ret_len += ComntStream.getDataSize(para.tmp_al2_1_mdhmi);
			
			para.tmp_al2_2_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.tmp_al2_2_mdhmi); 
			ret_len += ComntStream.getDataSize(para.tmp_al2_2_mdhmi);
			
			para.tmp_al2_3_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.tmp_al2_3_mdhmi); 
			ret_len += ComntStream.getDataSize(para.tmp_al2_3_mdhmi);
			
			para.tmp_fhz_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.tmp_fhz_mdhmi); 
			ret_len += ComntStream.getDataSize(para.tmp_fhz_mdhmi);
			
			para.tmp_fhz_rf1_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.tmp_fhz_rf1_mdhmi); 
			ret_len += ComntStream.getDataSize(para.tmp_fhz_rf1_mdhmi);
			
			para.tmp_fhz_rf2_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.tmp_fhz_rf2_mdhmi); 
			ret_len += ComntStream.getDataSize(para.tmp_fhz_rf2_mdhmi);
			
			para.tmp_prot_flag = ComntStream.readStream(byte_vect, offset + ret_len, para.tmp_prot_flag); 
			ret_len += ComntStream.getDataSize(para.tmp_prot_flag);
			
			para.update_alarmstate = ComntStream.readStream(byte_vect, offset + ret_len, para.update_alarmstate); 
			ret_len += ComntStream.getDataSize(para.update_alarmstate);
			
			return ret_len;
		}

	}
	
	
	/**
	 * 总加组预付费参数--[消息相关,请不要随意改动]
	 */
	public static class YFF_ZJGPAYPARA{
		public int		rtu_id			= 0;						//终端编号
		public short	zjg_id			= 0;						//总加组号
		
		public byte		cacl_type		= 0;						//计费方式 0:无 1:金额计费 2:表底计费
		public byte		feectrl_type	= 0;						//费控方式 0:本地费控 1:主站费控
		public byte		pay_type		= 0;						//缴费方式 0:卡式 1:远程 2:主站
		
		public byte		writecard_no[]   = new byte[ComntMsg.MSG_STR_LEN_32];	//写卡户号 -主要6103 智能表也可 同186
		public byte 	cardmeter_type	= 0;						//卡表类型
		
		public byte		yffctrl_type	= 0;						//预付费控制类型
		public short	feeproj_id[] 	= new short[ComntMsg.YFF_ZJGPAY_METERNUM];	//测量点1-费率方案号
		public short	yffalarm_id		= 0;						//报警方案
		
		public int		fee_begindate	= 0;						//费率启用日期
		
		public byte		prot_st			= 0;						//保电开始时间
		public byte		prot_ed			= 0;						//保电结束时间
		public byte		ngloprot_flag	= 0;						//不参与全局保电标志
		
		public byte		key_version		= 0;						//密钥版本, 单用于智能表
		public byte		cryplink_id		= 0;						//加密机ID, 单用于智能表
		
		public double	pay_add1		= 0;						//缴费附加值1(基本费)
		public double	pay_add2		= 0;						//缴费附加值2
		public double	pay_add3		= 0;						//缴费附加值3
		public int		tz_val			= 0;						//透支值
		public short	plus_time		= 0;						//负荷脉冲闭合时间
		public byte		use_gfh			= 0;						//是否启用高负荷控制
		public short	hfh_time		= 0;						//连续高负荷时间
		public int		hfh_shutdown	= 0;						//高负荷断电值
		public byte		cs_stand		= 0;						//功率因数标准
		
		public byte		powrate_flag	= 0;						//力调算费标志	0 不算费，     1 算费
		public byte		prize_flag		= 0;						//奖罚标志   		0 执行奖罚, 1 只罚不奖
		public byte		stop_flag		= 0;						//报停标志   		0 不报停，      1 报停
		public int		stop_begdate	= 0;						//报停开始日期
		public int		stop_enddate	= 0;						//报停结束日期		
		
		public byte		fee_chgf		= 0;						//费率更改标准
		public short	fee_chgid[] 	= new short[ComntMsg.YFF_ZJGPAY_METERNUM];	//测量点1-费率更改ID
		public int		fee_chgdate		= 0;						//费率更改日期
		public int		fee_chgtime		= 0;						//费率更改时间
		
		public byte		jbf_chgf		= 0;						//基本更改标准
		public double	jbf_chgval		= 0;						//基本费更改值
		public int		jbf_chgdate		= 0;						//基本更改日期
		public int		jbf_chgtime		= 0;						//基本更改时间

		//20120516
		public byte		cb_cycle_type	= 0;						//抄表周期类型 0:每月抄表 1:双月抄表  2:单月抄表 3:季度抄表 4：半年抄表 5：年抄表
		public short	cb_dayhour		= 0;						//抄表日DDHH
		public byte		js_day			= 0;						//结算日

		//20120723
		public byte		cbjs_flag		= 0;						//抄表日结算标志
		public byte		fxdf_flag		= 0;						//是否发行电费
		public int		fxdf_begindate	= 0;						//发行电费起始日期
		public byte		local_maincalcf = 0;						//主站算费标志	0 不算费， 1 算费		
		
		public static void setYFF_ZJGPAYPARA(YFF_ZJGPAYPARA para, YFF_ZJGPAYPARA setValue){
			if(setValue == null){
				para = new YFF_ZJGPAYPARA();
			}else{
				int i = 0;
				para.rtu_id			= setValue.rtu_id;
				para.zjg_id			= setValue.zjg_id;
				para.cacl_type		= setValue.cacl_type;
				para.feectrl_type	= setValue.feectrl_type;
				para.pay_type		= setValue.pay_type;
				
				//20120723
				for (i = 0; i < ComntMsg.MSG_STR_LEN_32; i++) {
					para.writecard_no[i] = setValue.writecard_no[i];
				}
				para.cardmeter_type = setValue.cardmeter_type;
				
				para.yffctrl_type	= setValue.yffctrl_type;
				
				for (i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
					para.feeproj_id[i] = setValue.feeproj_id[i];
				}
				para.yffalarm_id	= setValue.yffalarm_id;
				
				para.fee_begindate  = setValue.fee_begindate;
				
				para.prot_st		= setValue.prot_st;
				para.prot_ed		= setValue.prot_ed;
				para.ngloprot_flag	= setValue.ngloprot_flag;
				
				para.key_version	= setValue.key_version;
				para.cryplink_id	= setValue.cryplink_id;
				
				para.pay_add1		= setValue.pay_add1;
				para.pay_add2		= setValue.pay_add2;
				para.pay_add3		= setValue.pay_add3;
				para.tz_val			= setValue.tz_val;	
				para.plus_time		= setValue.plus_time;	
				para.use_gfh		= setValue.use_gfh;
				para.hfh_time		= setValue.hfh_time;
				para.hfh_shutdown	= setValue.hfh_shutdown;
				para.cs_stand		= setValue.cs_stand;
				
				para.powrate_flag	= setValue.powrate_flag;
				para.prize_flag		= setValue.prize_flag;
				para.stop_flag		= setValue.stop_flag;
				para.stop_begdate	= setValue.stop_begdate;
				para.stop_enddate	= setValue.stop_enddate;				
				
				para.fee_chgf		= setValue.fee_chgf;
				for (i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
					para.fee_chgid[i] = setValue.fee_chgid[i];
				}
				para.fee_chgdate	= setValue.fee_chgdate;
				para.fee_chgtime	= setValue.fee_chgtime;
				para.jbf_chgf		= setValue.jbf_chgf;
				para.jbf_chgval		= setValue.jbf_chgval;	
				para.jbf_chgdate	= setValue.jbf_chgdate;
				para.jbf_chgtime	= setValue.jbf_chgtime;

				//20120516
				para.cb_cycle_type	= setValue.cb_cycle_type;
				para.cb_dayhour		= setValue.cb_dayhour;
				para.js_day			= setValue.js_day;

				//20120723
				para.cbjs_flag		= setValue.cbjs_flag;
				para.fxdf_flag		= setValue.fxdf_flag;
				para.fxdf_begindate	= setValue.fxdf_begindate;
				para.local_maincalcf= setValue.local_maincalcf;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, YFF_ZJGPAYPARA para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.rtu_id);	
			ret_len += ComntStream.writeStream(byte_vect, para.zjg_id);	
			ret_len += ComntStream.writeStream(byte_vect, para.cacl_type);	
			ret_len += ComntStream.writeStream(byte_vect, para.feectrl_type);	
			ret_len += ComntStream.writeStream(byte_vect, para.pay_type);
			ret_len += ComntStream.writeStream(byte_vect, para.writecard_no, 0, para.writecard_no.length);
			ret_len += ComntStream.writeStream(byte_vect, para.cardmeter_type);
			ret_len += ComntStream.writeStream(byte_vect, para.yffctrl_type);
			ret_len += ComntStream.writeStream(byte_vect, para.feeproj_id, 0, para.feeproj_id.length);
			ret_len += ComntStream.writeStream(byte_vect, para.yffalarm_id);	
			ret_len += ComntStream.writeStream(byte_vect, para.fee_begindate);
			ret_len += ComntStream.writeStream(byte_vect, para.prot_st);
			ret_len += ComntStream.writeStream(byte_vect, para.prot_ed);
			ret_len += ComntStream.writeStream(byte_vect, para.ngloprot_flag);	
			
			ret_len += ComntStream.writeStream(byte_vect, para.key_version);
			ret_len += ComntStream.writeStream(byte_vect, para.cryplink_id);
			
			ret_len += ComntStream.writeStream(byte_vect, para.pay_add1);
			ret_len += ComntStream.writeStream(byte_vect, para.pay_add2);	
			ret_len += ComntStream.writeStream(byte_vect, para.pay_add3);
			ret_len += ComntStream.writeStream(byte_vect, para.tz_val);	
			ret_len += ComntStream.writeStream(byte_vect, para.plus_time);
			ret_len += ComntStream.writeStream(byte_vect, para.use_gfh);
			ret_len += ComntStream.writeStream(byte_vect, para.hfh_time);
			ret_len += ComntStream.writeStream(byte_vect, para.hfh_shutdown);
			ret_len += ComntStream.writeStream(byte_vect, para.cs_stand);
			
			ret_len += ComntStream.writeStream(byte_vect, para.powrate_flag);
			ret_len += ComntStream.writeStream(byte_vect, para.prize_flag);
			ret_len += ComntStream.writeStream(byte_vect, para.stop_flag);
			ret_len += ComntStream.writeStream(byte_vect, para.stop_begdate);
			ret_len += ComntStream.writeStream(byte_vect, para.stop_enddate);
			
			ret_len += ComntStream.writeStream(byte_vect, para.fee_chgf);
			ret_len += ComntStream.writeStream(byte_vect, para.fee_chgid, 0, para.fee_chgid.length);
			ret_len += ComntStream.writeStream(byte_vect, para.fee_chgdate);
			ret_len += ComntStream.writeStream(byte_vect, para.fee_chgtime);
			ret_len += ComntStream.writeStream(byte_vect, para.jbf_chgf);
			ret_len += ComntStream.writeStream(byte_vect, para.jbf_chgval);	
			ret_len += ComntStream.writeStream(byte_vect, para.jbf_chgdate);
			ret_len += ComntStream.writeStream(byte_vect, para.jbf_chgtime);

			//20120516
			ret_len += ComntStream.writeStream(byte_vect, para.cb_cycle_type);
			ret_len += ComntStream.writeStream(byte_vect, para.cb_dayhour);
			ret_len += ComntStream.writeStream(byte_vect, para.js_day);
			ret_len += ComntStream.writeStream(byte_vect, para.cbjs_flag);
			ret_len += ComntStream.writeStream(byte_vect, para.fxdf_flag);
			ret_len += ComntStream.writeStream(byte_vect, para.fxdf_begindate);
			ret_len += ComntStream.writeStream(byte_vect, para.local_maincalcf);			
			
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFF_ZJGPAYPARA para){
			int ret_len   = len;
			
			para.rtu_id = ComntStream.readStream(byte_vect, offset + ret_len, para.rtu_id); 
			ret_len += ComntStream.getDataSize(para.rtu_id);
			
			para.zjg_id = ComntStream.readStream(byte_vect, offset + ret_len, para.zjg_id); 
			ret_len += ComntStream.getDataSize(para.zjg_id);
			
			para.cacl_type = ComntStream.readStream(byte_vect, offset + ret_len, para.cacl_type); 
			ret_len += ComntStream.getDataSize(para.cacl_type);
			
			para.feectrl_type = ComntStream.readStream(byte_vect, offset + ret_len, para.feectrl_type); 
			ret_len += ComntStream.getDataSize(para.feectrl_type);
			
			para.pay_type = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_type); 
			ret_len += ComntStream.getDataSize(para.pay_type);
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.writecard_no, 0, para.writecard_no.length);
			ret_len += ComntStream.getDataSize(para.writecard_no[0]) * para.writecard_no.length;			
			
			para.cardmeter_type = ComntStream.readStream(byte_vect, offset + ret_len, para.cardmeter_type); 
			ret_len += ComntStream.getDataSize(para.cardmeter_type);
			
			para.yffctrl_type = ComntStream.readStream(byte_vect, offset + ret_len, para.yffctrl_type); 
			ret_len += ComntStream.getDataSize(para.yffctrl_type);
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.feeproj_id, 0, para.feeproj_id.length);
			ret_len += ComntStream.getDataSize(para.feeproj_id[0]) * para.feeproj_id.length;
			
			para.yffalarm_id = ComntStream.readStream(byte_vect, offset + ret_len, para.yffalarm_id); 
			ret_len += ComntStream.getDataSize(para.yffalarm_id);
			
			para.fee_begindate = ComntStream.readStream(byte_vect, offset + ret_len, para.fee_begindate); 
			ret_len += ComntStream.getDataSize(para.fee_begindate);			
			
			para.prot_st = ComntStream.readStream(byte_vect, offset + ret_len, para.prot_st); 
			ret_len += ComntStream.getDataSize(para.prot_st);
			
			para.prot_ed = ComntStream.readStream(byte_vect, offset + ret_len, para.prot_ed); 
			ret_len += ComntStream.getDataSize(para.prot_ed);
			
			para.ngloprot_flag = ComntStream.readStream(byte_vect, offset + ret_len, para.ngloprot_flag); 
			ret_len += ComntStream.getDataSize(para.ngloprot_flag);
			
			para.key_version = ComntStream.readStream(byte_vect, offset + ret_len, para.key_version); 
			ret_len += ComntStream.getDataSize(para.key_version);			
			
			para.cryplink_id = ComntStream.readStream(byte_vect, offset + ret_len, para.cryplink_id); 
			ret_len += ComntStream.getDataSize(para.cryplink_id);			
			
			para.pay_add1 = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_add1); 
			ret_len += ComntStream.getDataSize(para.pay_add1);
			
			para.pay_add2 = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_add2); 
			ret_len += ComntStream.getDataSize(para.pay_add2);
			
			para.pay_add3 = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_add3); 
			ret_len += ComntStream.getDataSize(para.pay_add3);
			
			para.tz_val = ComntStream.readStream(byte_vect, offset + ret_len, para.tz_val); 
			ret_len += ComntStream.getDataSize(para.tz_val);
			
			para.plus_time = ComntStream.readStream(byte_vect, offset + ret_len, para.plus_time); 
			ret_len += ComntStream.getDataSize(para.plus_time);
			
			para.use_gfh = ComntStream.readStream(byte_vect, offset + ret_len, para.use_gfh); 
			ret_len += ComntStream.getDataSize(para.use_gfh);
			
			para.hfh_time = ComntStream.readStream(byte_vect, offset + ret_len, para.hfh_time); 
			ret_len += ComntStream.getDataSize(para.hfh_time);
			
			para.hfh_shutdown = ComntStream.readStream(byte_vect, offset + ret_len, para.hfh_shutdown); 
			ret_len += ComntStream.getDataSize(para.hfh_shutdown);

			para.cs_stand = ComntStream.readStream(byte_vect, offset + ret_len, para.cs_stand); 
			ret_len += ComntStream.getDataSize(para.cs_stand);

			para.powrate_flag = ComntStream.readStream(byte_vect, offset + ret_len, para.powrate_flag); 
			ret_len += ComntStream.getDataSize(para.powrate_flag);

			para.prize_flag = ComntStream.readStream(byte_vect, offset + ret_len, para.prize_flag); 
			ret_len += ComntStream.getDataSize(para.prize_flag);

			para.stop_flag = ComntStream.readStream(byte_vect, offset + ret_len, para.stop_flag); 
			ret_len += ComntStream.getDataSize(para.stop_flag);

			para.stop_begdate = ComntStream.readStream(byte_vect, offset + ret_len, para.stop_begdate); 
			ret_len += ComntStream.getDataSize(para.stop_begdate);

			para.stop_enddate = ComntStream.readStream(byte_vect, offset + ret_len, para.stop_enddate); 
			ret_len += ComntStream.getDataSize(para.stop_enddate);
			
			para.fee_chgf = ComntStream.readStream(byte_vect, offset + ret_len, para.fee_chgf); 
			ret_len += ComntStream.getDataSize(para.fee_chgf);
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.fee_chgid, 0, para.fee_chgid.length);
			ret_len += ComntStream.getDataSize(para.fee_chgid[0]) * para.fee_chgid.length;
			
			para.fee_chgdate = ComntStream.readStream(byte_vect, offset + ret_len, para.fee_chgdate); 
			ret_len += ComntStream.getDataSize(para.fee_chgdate);
			
			para.fee_chgtime = ComntStream.readStream(byte_vect, offset + ret_len, para.fee_chgtime); 
			ret_len += ComntStream.getDataSize(para.fee_chgtime);
			
			para.jbf_chgf = ComntStream.readStream(byte_vect, offset + ret_len, para.jbf_chgf); 
			ret_len += ComntStream.getDataSize(para.jbf_chgf);
			
			para.jbf_chgval = ComntStream.readStream(byte_vect, offset + ret_len, para.jbf_chgval); 
			ret_len += ComntStream.getDataSize(para.jbf_chgval);
			
			para.jbf_chgdate = ComntStream.readStream(byte_vect, offset + ret_len, para.jbf_chgdate); 
			ret_len += ComntStream.getDataSize(para.jbf_chgdate);
			
			para.jbf_chgtime = ComntStream.readStream(byte_vect, offset + ret_len, para.jbf_chgtime); 
			ret_len += ComntStream.getDataSize(para.jbf_chgtime);
			
			//20120516
			para.cb_cycle_type = ComntStream.readStream(byte_vect, offset + ret_len, para.cb_cycle_type); 
			ret_len += ComntStream.getDataSize(para.cb_cycle_type);
			
			para.cb_dayhour = ComntStream.readStream(byte_vect, offset + ret_len, para.cb_dayhour); 
			ret_len += ComntStream.getDataSize(para.cb_dayhour);
			
			para.js_day = ComntStream.readStream(byte_vect, offset + ret_len, para.js_day); 
			ret_len += ComntStream.getDataSize(para.js_day);
			
			//20120723
			para.cbjs_flag = ComntStream.readStream(byte_vect, offset + ret_len, para.cbjs_flag); 
			ret_len += ComntStream.getDataSize(para.cbjs_flag);
			
			para.fxdf_flag = ComntStream.readStream(byte_vect, offset + ret_len, para.fxdf_flag); 
			ret_len += ComntStream.getDataSize(para.fxdf_flag);
			
			para.fxdf_begindate = ComntStream.readStream(byte_vect, offset + ret_len, para.fxdf_begindate); 
			ret_len += ComntStream.getDataSize(para.fxdf_begindate);			

			para.local_maincalcf = ComntStream.readStream(byte_vect, offset + ret_len, para.local_maincalcf); 
			ret_len += ComntStream.getDataSize(para.local_maincalcf);
			
			return ret_len;
		}
	}
		
	
	/**
	 * 总加组预付费状态表--[消息相关,请不要随意改动]
	 */
	public static class YFF_ZJGPAYSTATE{
		
		public int		rtu_id				= 0;						//终端编号
		public short	zjg_id				= 0;						//总加组号
		public byte		cus_state			= 0;						//用户状态 0 初始态, 1 正常态, 10 销户态
		public byte		op_type				= 0;						//本次操作类型 0 初始态, 1 开户, 2 缴费, 3 补卡, 4 补写卡, 5 冲正, 6 换表, 7 换费率 10 销户
		public int		op_date				= 0;						//本次操作日期
		public int		op_time				= 0;						//本次操作时间
		public double	pay_money			= 0;						//缴费金额	
		public double	othjs_money			= 0;						//结算金额(其它系统)
		public double	zb_money			= 0;						//追补金额	
		public double	all_money			= 0;						//总金额
		public double	buy_dl				= 0;						//购电量
		public double	pay_bmc				= 0;						//表码差
		public double	alarm_val1			= 0;						//报警值1
		public double	alarm_val2			= 0;						//报警值2
		public double	shutdown_val		= 0;						//断电值 金额计费时为:断电金额 表底计费时为:断电止码
		
		public double	jsbd_zyz[]			= new double[ComntMsg.YFF_ZJGPAY_METERNUM];		//测量点-结算总表底
		public double	jsbd_zyj[]			= new double[ComntMsg.YFF_ZJGPAY_METERNUM];		//测量点-结算尖表底	
		public double	jsbd_zyf[]			= new double[ComntMsg.YFF_ZJGPAY_METERNUM];		//测量点-结算峰表底	
		public double	jsbd_zyp[]			= new double[ComntMsg.YFF_ZJGPAY_METERNUM];		//测量点-结算平表底	
		public double	jsbd_zyg[]			= new double[ComntMsg.YFF_ZJGPAY_METERNUM];		//测量点-结算谷表底		
		//20120723
		public double	jsbd_zwz[]			= new double [ComntMsg.YFF_ZJGPAY_METERNUM];	//测量点-结算无功总表底
		
		public int		jsbd_ymd			= 0;						//结算时间
		public double	lsttw_money			= 0;						//上次调尾金额
		public double	nowtw_money			= 0;						//本次调尾金额
		public int		buy_times			= 0;						//购电次数
		public int		calc_mdhmi			= 0;						//算费时间-MMDDHHMI
		public int		calc_bdymd			= 0;						//算费表底时间-YYYYMMDD	
		
		public double	calc_zyz[]			= new double[ComntMsg.YFF_ZJGPAY_METERNUM];		//测量点-算费时总表底
		public double	calc_zyj[]			= new double[ComntMsg.YFF_ZJGPAY_METERNUM];		//测量点-算费时尖表底
		public double	calc_zyf[]			= new double[ComntMsg.YFF_ZJGPAY_METERNUM];		//测量点-算费时峰表底
		public double	calc_zyp[]			= new double[ComntMsg.YFF_ZJGPAY_METERNUM];		//测量点-算费时平表底
		public double	calc_zyg[]			= new double[ComntMsg.YFF_ZJGPAY_METERNUM];		//测量点-算费时谷表底
		
		public double	calc_zwz[]			= new double[ComntMsg.YFF_ZJGPAY_METERNUM];		//测量点-算费时无功总表底

		public double	real_powrate		= 0;						//实际功率因数
		public double	ele_money			= 0;						//电度电费
		public double	jbf_money			= 0;						//基本费电费
		public double	powrate_money		= 0;						//力调电费
		public double	other_money			= 0;						//其它电费		
		
		public double	now_remain			= 0;						//当前剩余
		public double	bj_bd				= 0;						//报警门限	--如果包含基本费则计算不准确
		public double	tz_bd				= 0;						//跳闸门限	--如果包含基本费则计算不准确
		public byte		cs_al1_state		= 0;						//报警1状态  0:正常状态 1:报警状态
		public byte		cs_al2_state		= 0;						//报警2状态  0:正常状态 1:报警状态
		public byte		cs_fhz_state		= 0;						//分合闸状态 0:分闸状态 1:合闸状态
		public int		al1_mdhmi			= 0;						//报警1状态改变时间-MMDDHHMI
		public int		al2_mdhmi			= 0;						//报警2状态改变时间-MMDDHHMI
		public int		fhz_mdhmi			= 0;						//分合闸状态改变时间-MMDDHHMI

		public double	fz_zyz[]			= new double[ComntMsg.YFF_ZJGPAY_METERNUM];		//测量点-分闸时总表底

		public int		yc_flag1			= 0;						//异常标志1, 参数错误 
		public int		yc_flag2			= 0;						//异常标志2(按位标志), 数据错误 0位:分闸后表字继续走 1位:表底飞走 2位:表底倒转 3位:长时间无数据 4位:长时间不缴费
		public int		yc_flag3			= 0;						//异常标志3, 备用 
		public int		hb_date				= 0;						//上次换表日期
		public int		hb_time				= 0;						//上次换表时间	
		public int		kh_date				= 0;						//开户日期-YYYYMMDD
		public int		xh_date				= 0;						//销户日期-YYYYMMDD
		public double	total_gdz			= 0;						//累计购电值

		public double	total_yzbdl			= 0;						//有功追补累计用电量
		public double	total_wzbdl			= 0;						//无功追补累计用电量
		public double	total_ydl			= 0;						//有功累计用电量
		public double	total_wdl			= 0;						//无功累计用电量

		public double	zbele_money			= 0;						//追补电度电费
		public double	zbjbf_money			= 0;						//追补基本费电费
		public double	zbother_money		= 0;						//追补其它电费

		public double	fxdf_iall_money		= 0;						//发行电费当月缴费总金额
		public double	fxdf_remain			= 0;						//发行电费当月剩余金额

		public int		fxdf_ym				= 0;						//发行电费年月 YYYYMM
		public int		fxdf_data_ymd		= 0;						//发行电费数据日期 YYYYMMDDD
		public int		fxdf_calc_mdhmi		= 0;						//发行电费算费时间 -MMDDHHMI

		public int		js_bc_ymd			= 0;						//结算补差日期 YYYYMMDD		
		
		public int		reserve1			= 0;						//保留
		
		public static void setYFF_ZJGPAYSTATE(YFF_ZJGPAYSTATE para, YFF_ZJGPAYSTATE setValue){
			if(setValue == null){
				para = new YFF_ZJGPAYSTATE();
			}else{
				int i = 0;
				para.rtu_id				= setValue.rtu_id;
				para.zjg_id				= setValue.zjg_id;
				para.cus_state			= setValue.cus_state;
				para.op_type			= setValue.op_type;
				para.op_date			= setValue.op_date;
				para.op_time			= setValue.op_time;
				para.pay_money			= setValue.pay_money;	
				para.othjs_money		= setValue.othjs_money;
				para.zb_money			= setValue.zb_money;
				para.all_money			= setValue.all_money;	
				para.buy_dl				= setValue.buy_dl;	
				para.pay_bmc			= setValue.pay_bmc;
				para.alarm_val1			= setValue.alarm_val1;	
				para.alarm_val2			= setValue.alarm_val2;	
				para.shutdown_val		= setValue.shutdown_val;
				for (i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
					para.jsbd_zyz[i] 	= setValue.jsbd_zyz[i];
					para.jsbd_zyj[i] 	= setValue.jsbd_zyj[i];
					para.jsbd_zyf[i] 	= setValue.jsbd_zyf[i];
					para.jsbd_zyp[i] 	= setValue.jsbd_zyp[i];
					para.jsbd_zyg[i] 	= setValue.jsbd_zyg[i];
				}
				for (i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
					para.jsbd_zwz[i] 	= setValue.jsbd_zwz[i];
				}
				
				para.jsbd_ymd			= setValue.jsbd_ymd;
				para.lsttw_money		= setValue.lsttw_money;
				para.nowtw_money		= setValue.nowtw_money;
				para.buy_times			= setValue.buy_times;
				para.calc_mdhmi			= setValue.calc_mdhmi;
				para.calc_bdymd			= setValue.calc_bdymd;
				
				for (i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
					para.calc_zyz[i] 	= setValue.calc_zyz[i];
					para.calc_zyj[i] 	= setValue.calc_zyj[i];
					para.calc_zyf[i] 	= setValue.calc_zyf[i];
					para.calc_zyp[i] 	= setValue.calc_zyp[i];
					para.calc_zyg[i] 	= setValue.calc_zyg[i];
				}
				
				for (i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
					para.calc_zwz[i] 	= setValue.calc_zwz[i];
				}				

				para.real_powrate		= setValue.real_powrate;
				para.ele_money			= setValue.ele_money;
				para.jbf_money			= setValue.jbf_money;
				para.powrate_money		= setValue.powrate_money;
				para.other_money		= setValue.other_money;

				para.now_remain			= setValue.now_remain;		
				para.bj_bd				= setValue.bj_bd;	
				para.tz_bd				= setValue.tz_bd;	
				para.cs_al1_state		= setValue.cs_al1_state;
				para.cs_al2_state		= setValue.cs_al2_state;
				para.cs_fhz_state		= setValue.cs_fhz_state;
				para.al1_mdhmi			= setValue.al1_mdhmi;	
				para.al2_mdhmi			= setValue.al2_mdhmi;	
				para.fhz_mdhmi			= setValue.fhz_mdhmi;	
				
				for (i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
					para.fz_zyz[i] 		= setValue.fz_zyz[i];
				}
				
				para.yc_flag1			= setValue.yc_flag1;
				para.yc_flag2			= setValue.yc_flag2;
				para.yc_flag3			= setValue.yc_flag3;
				para.hb_date			= setValue.hb_date;
				para.hb_time			= setValue.hb_time;
				para.kh_date			= setValue.kh_date;
				para.xh_date			= setValue.xh_date;
				para.total_gdz			= setValue.total_gdz;	

				para.total_yzbdl		= setValue.total_yzbdl;
				para.total_wzbdl		= setValue.total_wzbdl;
				para.total_ydl			= setValue.total_ydl;
				para.total_wdl			= setValue.total_wdl;
				para.zbele_money		= setValue.zbele_money;
				para.zbjbf_money		= setValue.zbjbf_money;
				para.zbother_money		= setValue.zbother_money;
				para.fxdf_iall_money	= setValue.fxdf_iall_money;
				para.fxdf_remain		= setValue.fxdf_remain;
				para.fxdf_ym			= setValue.fxdf_ym;
				para.fxdf_data_ymd		= setValue.fxdf_data_ymd;
				para.fxdf_calc_mdhmi	= setValue.fxdf_calc_mdhmi;
				para.js_bc_ymd			= setValue.js_bc_ymd;
				
				para.reserve1			= setValue.reserve1;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, YFF_ZJGPAYSTATE para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.rtu_id);	
			ret_len += ComntStream.writeStream(byte_vect, para.zjg_id);	
			ret_len += ComntStream.writeStream(byte_vect, para.cus_state);	
			ret_len += ComntStream.writeStream(byte_vect, para.op_type);	
			ret_len += ComntStream.writeStream(byte_vect, para.op_date);	
			ret_len += ComntStream.writeStream(byte_vect, para.op_time);	
			ret_len += ComntStream.writeStream(byte_vect, para.pay_money);
			ret_len += ComntStream.writeStream(byte_vect, para.othjs_money);
			ret_len += ComntStream.writeStream(byte_vect, para.zb_money);
			ret_len += ComntStream.writeStream(byte_vect, para.all_money);
			ret_len += ComntStream.writeStream(byte_vect, para.buy_dl);
			ret_len += ComntStream.writeStream(byte_vect, para.pay_bmc);
			ret_len += ComntStream.writeStream(byte_vect, para.alarm_val1);
			ret_len += ComntStream.writeStream(byte_vect, para.alarm_val2);
			ret_len += ComntStream.writeStream(byte_vect, para.shutdown_val);
			
			int i = 0;
			for (i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
				ret_len += ComntStream.writeStream(byte_vect, para.jsbd_zyz[i]);
				ret_len += ComntStream.writeStream(byte_vect, para.jsbd_zyj[i]);
				ret_len += ComntStream.writeStream(byte_vect, para.jsbd_zyf[i]);
				ret_len += ComntStream.writeStream(byte_vect, para.jsbd_zyp[i]);
				ret_len += ComntStream.writeStream(byte_vect, para.jsbd_zyg[i]);
			}
			
			for (i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
				ret_len += ComntStream.writeStream(byte_vect, para.jsbd_zwz[i]);
			}
			
			ret_len += ComntStream.writeStream(byte_vect, para.jsbd_ymd);
			ret_len += ComntStream.writeStream(byte_vect, para.lsttw_money);
			ret_len += ComntStream.writeStream(byte_vect, para.nowtw_money);	
			ret_len += ComntStream.writeStream(byte_vect, para.buy_times);
			ret_len += ComntStream.writeStream(byte_vect, para.calc_mdhmi);	
			ret_len += ComntStream.writeStream(byte_vect, para.calc_bdymd);
			
			for (i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
				ret_len += ComntStream.writeStream(byte_vect, para.calc_zyz[i]);
				ret_len += ComntStream.writeStream(byte_vect, para.calc_zyj[i]);
				ret_len += ComntStream.writeStream(byte_vect, para.calc_zyf[i]);
				ret_len += ComntStream.writeStream(byte_vect, para.calc_zyp[i]);
				ret_len += ComntStream.writeStream(byte_vect, para.calc_zyg[i]);
			}
			for (i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
				ret_len += ComntStream.writeStream(byte_vect, para.calc_zwz[i]);
			}
			
			
			ret_len += ComntStream.writeStream(byte_vect, para.real_powrate);	
			ret_len += ComntStream.writeStream(byte_vect, para.ele_money);
			ret_len += ComntStream.writeStream(byte_vect, para.jbf_money);
			ret_len += ComntStream.writeStream(byte_vect, para.powrate_money);
			ret_len += ComntStream.writeStream(byte_vect, para.other_money);
			
			ret_len += ComntStream.writeStream(byte_vect, para.now_remain);	
			ret_len += ComntStream.writeStream(byte_vect, para.bj_bd);
			ret_len += ComntStream.writeStream(byte_vect, para.tz_bd);	
			ret_len += ComntStream.writeStream(byte_vect, para.cs_al1_state);
			ret_len += ComntStream.writeStream(byte_vect, para.cs_al2_state);	
			ret_len += ComntStream.writeStream(byte_vect, para.cs_fhz_state);
			ret_len += ComntStream.writeStream(byte_vect, para.al1_mdhmi);	
			ret_len += ComntStream.writeStream(byte_vect, para.al2_mdhmi);
			ret_len += ComntStream.writeStream(byte_vect, para.fhz_mdhmi);
			
			for (i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
				ret_len += ComntStream.writeStream(byte_vect, para.fz_zyz[i]);
			}
			
			ret_len += ComntStream.writeStream(byte_vect, para.yc_flag1);
			ret_len += ComntStream.writeStream(byte_vect, para.yc_flag2);
			ret_len += ComntStream.writeStream(byte_vect, para.yc_flag3);	
			ret_len += ComntStream.writeStream(byte_vect, para.hb_date);
			ret_len += ComntStream.writeStream(byte_vect, para.hb_time);	
			ret_len += ComntStream.writeStream(byte_vect, para.kh_date);
			ret_len += ComntStream.writeStream(byte_vect, para.xh_date);
			ret_len += ComntStream.writeStream(byte_vect, para.total_gdz);
			
			ret_len += ComntStream.writeStream(byte_vect, para.total_yzbdl);
			ret_len += ComntStream.writeStream(byte_vect, para.total_wzbdl);
			ret_len += ComntStream.writeStream(byte_vect, para.total_ydl);
			ret_len += ComntStream.writeStream(byte_vect, para.total_wdl);
			ret_len += ComntStream.writeStream(byte_vect, para.zbele_money);
			ret_len += ComntStream.writeStream(byte_vect, para.zbjbf_money);
			ret_len += ComntStream.writeStream(byte_vect, para.zbother_money);
			ret_len += ComntStream.writeStream(byte_vect, para.fxdf_iall_money);
			ret_len += ComntStream.writeStream(byte_vect, para.fxdf_remain);
			ret_len += ComntStream.writeStream(byte_vect, para.fxdf_ym);
			ret_len += ComntStream.writeStream(byte_vect, para.fxdf_data_ymd);
			ret_len += ComntStream.writeStream(byte_vect, para.fxdf_calc_mdhmi);
			ret_len += ComntStream.writeStream(byte_vect, para.js_bc_ymd);
			
			ret_len += ComntStream.writeStream(byte_vect, para.reserve1);
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFF_ZJGPAYSTATE para){
			int ret_len   = len;
			int i 		  = 0;
			
			para.rtu_id = ComntStream.readStream(byte_vect, offset + ret_len, para.rtu_id); 
			ret_len += ComntStream.getDataSize(para.rtu_id);
			
			para.zjg_id = ComntStream.readStream(byte_vect, offset + ret_len, para.zjg_id); 
			ret_len += ComntStream.getDataSize(para.zjg_id);
			
			para.cus_state = ComntStream.readStream(byte_vect, offset + ret_len, para.cus_state); 
			ret_len += ComntStream.getDataSize(para.cus_state);
			
			para.op_type = ComntStream.readStream(byte_vect, offset + ret_len, para.op_type); 
			ret_len += ComntStream.getDataSize(para.op_type);
			
			para.op_date = ComntStream.readStream(byte_vect, offset + ret_len, para.op_date); 
			ret_len += ComntStream.getDataSize(para.op_date);
			
			para.op_time = ComntStream.readStream(byte_vect, offset + ret_len, para.op_time); 
			ret_len += ComntStream.getDataSize(para.op_time);
			
			para.pay_money = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_money); 
			ret_len += ComntStream.getDataSize(para.pay_money);
			
			para.othjs_money = ComntStream.readStream(byte_vect, offset + ret_len, para.othjs_money); 
			ret_len += ComntStream.getDataSize(para.othjs_money);
			
			para.zb_money = ComntStream.readStream(byte_vect, offset + ret_len, para.zb_money); 
			ret_len += ComntStream.getDataSize(para.zb_money);
			
			para.all_money = ComntStream.readStream(byte_vect, offset + ret_len, para.all_money); 
			ret_len += ComntStream.getDataSize(para.all_money);
			
			para.buy_dl = ComntStream.readStream(byte_vect, offset + ret_len, para.buy_dl); 
			ret_len += ComntStream.getDataSize(para.buy_dl);
			
			para.pay_bmc = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_bmc); 
			ret_len += ComntStream.getDataSize(para.pay_bmc);
			
			para.alarm_val1 = ComntStream.readStream(byte_vect, offset + ret_len, para.alarm_val1); 
			ret_len += ComntStream.getDataSize(para.alarm_val1);
			
			para.alarm_val2 = ComntStream.readStream(byte_vect, offset + ret_len, para.alarm_val2); 
			ret_len += ComntStream.getDataSize(para.alarm_val2);
			
			para.shutdown_val = ComntStream.readStream(byte_vect, offset + ret_len, para.shutdown_val); 
			ret_len += ComntStream.getDataSize(para.shutdown_val);
			
			for (i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
				para.jsbd_zyz[i] = ComntStream.readStream(byte_vect, offset + ret_len, para.jsbd_zyz[i]); 
				ret_len += ComntStream.getDataSize(para.jsbd_zyz[i]);
				
				para.jsbd_zyj[i] = ComntStream.readStream(byte_vect, offset + ret_len, para.jsbd_zyj[i]); 
				ret_len += ComntStream.getDataSize(para.jsbd_zyj[i]);
				
				para.jsbd_zyf[i] = ComntStream.readStream(byte_vect, offset + ret_len, para.jsbd_zyf[i]); 
				ret_len += ComntStream.getDataSize(para.jsbd_zyf[i]);
				
				para.jsbd_zyp[i] = ComntStream.readStream(byte_vect, offset + ret_len, para.jsbd_zyp[i]); 
				ret_len += ComntStream.getDataSize(para.jsbd_zyp[i]);
				
				para.jsbd_zyg[i] = ComntStream.readStream(byte_vect, offset + ret_len, para.jsbd_zyg[i]); 
				ret_len += ComntStream.getDataSize(para.jsbd_zyg[i]);
			}
			
			for (i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
				ComntStream.readStream(byte_vect, offset + ret_len, para.jsbd_zwz[i]);
				ret_len += ComntStream.getDataSize(para.jsbd_zwz[i]);
			}

			para.jsbd_ymd = ComntStream.readStream(byte_vect, offset + ret_len, para.jsbd_ymd); 
			ret_len += ComntStream.getDataSize(para.jsbd_ymd);
			
			para.lsttw_money = ComntStream.readStream(byte_vect, offset + ret_len, para.lsttw_money); 
			ret_len += ComntStream.getDataSize(para.lsttw_money);
			
			para.nowtw_money = ComntStream.readStream(byte_vect, offset + ret_len, para.nowtw_money); 
			ret_len += ComntStream.getDataSize(para.nowtw_money);
			
			para.buy_times = ComntStream.readStream(byte_vect, offset + ret_len, para.buy_times); 
			ret_len += ComntStream.getDataSize(para.buy_times);
			
			para.calc_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.calc_mdhmi); 
			ret_len += ComntStream.getDataSize(para.calc_mdhmi);
			
			para.calc_bdymd = ComntStream.readStream(byte_vect, offset + ret_len, para.calc_bdymd); 
			ret_len += ComntStream.getDataSize(para.calc_bdymd);
			
			for (i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
				para.calc_zyz[i] = ComntStream.readStream(byte_vect, offset + ret_len, para.calc_zyz[i]); 
				ret_len += ComntStream.getDataSize(para.calc_zyz[i]);
				
				para.calc_zyj[i] = ComntStream.readStream(byte_vect, offset + ret_len, para.calc_zyj[i]); 
				ret_len += ComntStream.getDataSize(para.calc_zyj[i]);
				
				para.calc_zyf[i] = ComntStream.readStream(byte_vect, offset + ret_len, para.calc_zyf[i]); 
				ret_len += ComntStream.getDataSize(para.calc_zyf[i]);
				
				para.calc_zyp[i] = ComntStream.readStream(byte_vect, offset + ret_len, para.calc_zyp[i]); 
				ret_len += ComntStream.getDataSize(para.calc_zyp[i]);
				
				para.calc_zyg[i] = ComntStream.readStream(byte_vect, offset + ret_len, para.calc_zyg[i]); 
				ret_len += ComntStream.getDataSize(para.calc_zyg[i]);
			}
			
			for (i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
				ComntStream.readStream(byte_vect, offset + ret_len, para.calc_zwz[i]);
				ret_len += ComntStream.getDataSize(para.calc_zwz[i]);
			}			

			para.real_powrate = ComntStream.readStream(byte_vect, offset + ret_len, para.real_powrate); 
			ret_len += ComntStream.getDataSize(para.real_powrate);
			
			para.ele_money = ComntStream.readStream(byte_vect, offset + ret_len, para.ele_money); 
			ret_len += ComntStream.getDataSize(para.ele_money);
			
			para.jbf_money = ComntStream.readStream(byte_vect, offset + ret_len, para.jbf_money); 
			ret_len += ComntStream.getDataSize(para.jbf_money);
			
			para.powrate_money = ComntStream.readStream(byte_vect, offset + ret_len, para.powrate_money); 
			ret_len += ComntStream.getDataSize(para.powrate_money);
			
			para.other_money = ComntStream.readStream(byte_vect, offset + ret_len, para.other_money); 
			ret_len += ComntStream.getDataSize(para.other_money);			
			
			para.now_remain = ComntStream.readStream(byte_vect, offset + ret_len, para.now_remain); 
			ret_len += ComntStream.getDataSize(para.now_remain);
			
			para.bj_bd = ComntStream.readStream(byte_vect, offset + ret_len, para.bj_bd); 
			ret_len += ComntStream.getDataSize(para.bj_bd);
			
			para.tz_bd = ComntStream.readStream(byte_vect, offset + ret_len, para.tz_bd); 
			ret_len += ComntStream.getDataSize(para.tz_bd);
			
			para.cs_al1_state = ComntStream.readStream(byte_vect, offset + ret_len, para.cs_al1_state); 
			ret_len += ComntStream.getDataSize(para.cs_al1_state);
			
			para.cs_al2_state = ComntStream.readStream(byte_vect, offset + ret_len, para.cs_al2_state); 
			ret_len += ComntStream.getDataSize(para.cs_al2_state);
			
			para.cs_fhz_state = ComntStream.readStream(byte_vect, offset + ret_len, para.cs_fhz_state); 
			ret_len += ComntStream.getDataSize(para.cs_fhz_state);
			
			para.al1_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.al1_mdhmi); 
			ret_len += ComntStream.getDataSize(para.al1_mdhmi);
			
			para.al2_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.al2_mdhmi); 
			ret_len += ComntStream.getDataSize(para.al2_mdhmi);
			
			para.fhz_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.fhz_mdhmi); 
			ret_len += ComntStream.getDataSize(para.fhz_mdhmi);
			
		//	ComntStream.readStream(byte_vect, offset + ret_len, para.fz_zyz, 0, para.fz_zyz.length);
		//	ret_len += ComntStream.getDataSize(para.fz_zyz[0]) * para.fz_zyz.length;
			for (i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
				ComntStream.readStream(byte_vect, offset + ret_len, para.fz_zyz[i]);
				ret_len += ComntStream.getDataSize(para.fz_zyz[i]);
			}			

			para.yc_flag1 = ComntStream.readStream(byte_vect, offset + ret_len, para.yc_flag1); 
			ret_len += ComntStream.getDataSize(para.yc_flag1);
			
			para.yc_flag2 = ComntStream.readStream(byte_vect, offset + ret_len, para.yc_flag2); 
			ret_len += ComntStream.getDataSize(para.yc_flag2);
			
			para.yc_flag3 = ComntStream.readStream(byte_vect, offset + ret_len, para.yc_flag3); 
			ret_len += ComntStream.getDataSize(para.yc_flag3);
			
			para.hb_date = ComntStream.readStream(byte_vect, offset + ret_len, para.hb_date); 
			ret_len += ComntStream.getDataSize(para.hb_date);
			
			para.hb_time = ComntStream.readStream(byte_vect, offset + ret_len, para.hb_time); 
			ret_len += ComntStream.getDataSize(para.hb_time);
			
			para.kh_date = ComntStream.readStream(byte_vect, offset + ret_len, para.kh_date); 
			ret_len += ComntStream.getDataSize(para.kh_date);
			
			para.xh_date = ComntStream.readStream(byte_vect, offset + ret_len, para.xh_date); 
			ret_len += ComntStream.getDataSize(para.xh_date);
			
			para.total_gdz = ComntStream.readStream(byte_vect, offset + ret_len, para.total_gdz); 
			ret_len += ComntStream.getDataSize(para.total_gdz);
			
			para.total_yzbdl = ComntStream.readStream(byte_vect, offset + ret_len, para.total_yzbdl); 
			ret_len += ComntStream.getDataSize(para.total_yzbdl);
			
			para.total_wzbdl = ComntStream.readStream(byte_vect, offset + ret_len, para.total_wzbdl); 
			ret_len += ComntStream.getDataSize(para.total_wzbdl);
			
			para.total_ydl = ComntStream.readStream(byte_vect, offset + ret_len, para.total_ydl); 
			ret_len += ComntStream.getDataSize(para.total_ydl);
			
			para.total_wdl = ComntStream.readStream(byte_vect, offset + ret_len, para.total_wdl); 
			ret_len += ComntStream.getDataSize(para.total_wdl);
			
			para.zbele_money = ComntStream.readStream(byte_vect, offset + ret_len, para.zbele_money); 
			ret_len += ComntStream.getDataSize(para.zbele_money);
			
			para.zbjbf_money = ComntStream.readStream(byte_vect, offset + ret_len, para.zbjbf_money); 
			ret_len += ComntStream.getDataSize(para.zbjbf_money);
			
			para.zbother_money = ComntStream.readStream(byte_vect, offset + ret_len, para.zbother_money); 
			ret_len += ComntStream.getDataSize(para.zbother_money);
			
			para.fxdf_iall_money = ComntStream.readStream(byte_vect, offset + ret_len, para.fxdf_iall_money); 
			ret_len += ComntStream.getDataSize(para.fxdf_iall_money);
			
			para.fxdf_remain = ComntStream.readStream(byte_vect, offset + ret_len, para.fxdf_remain); 
			ret_len += ComntStream.getDataSize(para.fxdf_remain);
			
			para.fxdf_ym = ComntStream.readStream(byte_vect, offset + ret_len, para.fxdf_ym); 
			ret_len += ComntStream.getDataSize(para.fxdf_ym);
			
			para.fxdf_data_ymd = ComntStream.readStream(byte_vect, offset + ret_len, para.fxdf_data_ymd); 
			ret_len += ComntStream.getDataSize(para.fxdf_data_ymd);
			
			para.fxdf_calc_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.fxdf_calc_mdhmi); 
			ret_len += ComntStream.getDataSize(para.fxdf_calc_mdhmi);
			
			para.js_bc_ymd = ComntStream.readStream(byte_vect, offset + ret_len, para.js_bc_ymd); 
			ret_len += ComntStream.getDataSize(para.js_bc_ymd);
			
			para.reserve1 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve1); 
			ret_len += ComntStream.getDataSize(para.reserve1);
			
			return ret_len;
		}
	}
	
	
	
	
	/**
	 * 总加组预付费报警表--[消息相关,请不要随意改动]
	 */
	public static class YFF_ZJGPAY_ALARMSTATE{
		public int		rtu_id			= 0;						//终端编号
		public short	zjg_id			= 0;						//总加组编号

		public byte		qr_al1_1_state	= 0;						//报警1-1确认状态(短信方式) 低6位(重试次数0~63) 高2位(0:初始态 1:等待 2:成功 3:失败)
		public byte		qr_al1_2_state	= 0;						//报警1-2确认状态(声音方式)
		public byte		qr_al1_3_state	= 0;						//报警1-3确认状态(备用方式)

		public byte		qr_al2_1_state	= 0;						//报警2-1确认状态(短信方式)
		public byte		qr_al2_2_state	= 0;						//报警2-2确认状态(声音方式)
		public byte		qr_al2_3_state	= 0;						//报警2-3确认状态(备用方式)

		public byte		qr_fhz_state	= 0;						//分合闸确认状态

		public byte		qr_fz_times		= 0;						//分闸次数

		public int		qr_al1_1_mdhmi	= 0;						//报警1-1确认状态(短信方式) 发送时间
		public int		qr_al1_2_mdhmi	= 0;						//报警1-2确认状态(声音方式) 发送时间
		public int		qr_al1_3_mdhmi	= 0;						//报警1-3确认状态(备用方式) 发送时间

		public int		qr_al2_1_mdhmi	= 0;						//报警2-1确认状态(短信方式) 发送时间
		public int		qr_al2_2_mdhmi	= 0;						//报警2-2确认状态(声音方式) 发送时间
		public int		qr_al2_3_mdhmi	= 0;						//报警2-3确认状态(备用方式) 发送时间

		public int		qr_fhz_mdhmi	= 0;						//分合闸确认状态 发送时间
		public int		cg_fhz_mdhmi	= 0;						//成功分合闸时间

		public int		qr_al1_1_uuid	= 0;						//报警1-1确认状态(短信方式) UUID
		public int		qr_al2_1_uuid	= 0;						//报警2-1确认状态(短信方式) UUID

		public byte		out_info[]		= new byte[ComntMsg.MSG_STR_LEN_64];	//信息输出
		
		public static void setYFF_ZJGPAY_ALARMSTATE(YFF_ZJGPAY_ALARMSTATE para, YFF_ZJGPAY_ALARMSTATE setValue){
			if(setValue == null){
				para = new YFF_ZJGPAY_ALARMSTATE();
			}else{
				para.rtu_id			= setValue.rtu_id;
				para.zjg_id			= setValue.zjg_id;

				para.qr_al1_1_state	= setValue.qr_al1_1_state;
				para.qr_al1_2_state	= setValue.qr_al1_2_state;
				para.qr_al1_3_state	= setValue.qr_al1_3_state;
				para.qr_al2_1_state	= setValue.qr_al2_1_state;
				para.qr_al2_2_state	= setValue.qr_al2_2_state;
				para.qr_al2_3_state	= setValue.qr_al2_3_state;
				para.qr_fhz_state	= setValue.qr_fhz_state;
				para.qr_fz_times	= setValue.qr_fz_times;
				para.qr_al1_1_mdhmi	= setValue.qr_al1_1_mdhmi;
				para.qr_al1_2_mdhmi	= setValue.qr_al1_2_mdhmi;
				para.qr_al1_3_mdhmi	= setValue.qr_al1_3_mdhmi;
				para.qr_al2_1_mdhmi	= setValue.qr_al2_1_mdhmi;
				para.qr_al2_2_mdhmi	= setValue.qr_al2_2_mdhmi;
				para.qr_al2_3_mdhmi	= setValue.qr_al2_3_mdhmi;
				para.qr_fhz_mdhmi	= setValue.qr_fhz_mdhmi;
				para.cg_fhz_mdhmi	= setValue.cg_fhz_mdhmi;
				para.qr_al1_1_uuid	= setValue.qr_al1_1_uuid;
				para.qr_al2_1_uuid	= setValue.qr_al2_1_uuid;

				for (int i = 0; i < ComntMsg.MSG_STR_LEN_64; i++) {
					para.out_info[i] = setValue.out_info[i];
				}
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, YFF_ZJGPAY_ALARMSTATE para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.rtu_id);	
			ret_len += ComntStream.writeStream(byte_vect, para.zjg_id);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al1_1_state);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al1_2_state);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al1_3_state);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al2_1_state);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al2_2_state);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al2_3_state);
			ret_len += ComntStream.writeStream(byte_vect, para.qr_fhz_state);
			ret_len += ComntStream.writeStream(byte_vect, para.qr_fz_times);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al1_1_mdhmi);
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al1_2_mdhmi);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al1_3_mdhmi);
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al2_1_mdhmi);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al2_2_mdhmi);
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al2_3_mdhmi);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_fhz_mdhmi);
			ret_len += ComntStream.writeStream(byte_vect, para.cg_fhz_mdhmi);	
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al1_1_uuid);
			ret_len += ComntStream.writeStream(byte_vect, para.qr_al2_1_uuid);	
			ret_len += ComntStream.writeStream(byte_vect, para.out_info, 0, para.out_info.length);
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFF_ZJGPAY_ALARMSTATE para){
			int ret_len   = len;
			
			para.rtu_id = ComntStream.readStream(byte_vect, offset + ret_len, para.rtu_id); 
			ret_len += ComntStream.getDataSize(para.rtu_id);
			
			para.zjg_id = ComntStream.readStream(byte_vect, offset + ret_len, para.zjg_id); 
			ret_len += ComntStream.getDataSize(para.zjg_id);
			
			para.qr_al1_1_state = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al1_1_state); 
			ret_len += ComntStream.getDataSize(para.qr_al1_1_state);
			
			para.qr_al1_2_state = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al1_2_state); 
			ret_len += ComntStream.getDataSize(para.qr_al1_2_state);
			
			para.qr_al1_3_state = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al1_3_state); 
			ret_len += ComntStream.getDataSize(para.qr_al1_3_state);
			
			para.qr_al2_1_state = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al2_1_state); 
			ret_len += ComntStream.getDataSize(para.qr_al2_1_state);
			
			para.qr_al2_2_state = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al2_2_state); 
			ret_len += ComntStream.getDataSize(para.qr_al2_2_state);
			
			para.qr_al2_3_state = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al2_3_state); 
			ret_len += ComntStream.getDataSize(para.qr_al2_3_state);
			
			para.qr_fhz_state = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_fhz_state); 
			ret_len += ComntStream.getDataSize(para.qr_fhz_state);
			
			para.qr_fz_times = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_fz_times); 
			ret_len += ComntStream.getDataSize(para.qr_fz_times);
			
			para.qr_al1_1_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al1_1_mdhmi); 
			ret_len += ComntStream.getDataSize(para.qr_al1_1_mdhmi);
			
			para.qr_al1_2_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al1_2_mdhmi); 
			ret_len += ComntStream.getDataSize(para.qr_al1_2_mdhmi);
			
			para.qr_al1_3_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al1_3_mdhmi); 
			ret_len += ComntStream.getDataSize(para.qr_al1_3_mdhmi);
			
			para.qr_al2_1_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al2_1_mdhmi); 
			ret_len += ComntStream.getDataSize(para.qr_al2_1_mdhmi);
			
			para.qr_al2_2_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al2_2_mdhmi); 
			ret_len += ComntStream.getDataSize(para.qr_al2_2_mdhmi);
			
			para.qr_al2_3_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al2_3_mdhmi); 
			ret_len += ComntStream.getDataSize(para.qr_al2_3_mdhmi);
			
			para.qr_fhz_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_fhz_mdhmi); 
			ret_len += ComntStream.getDataSize(para.qr_fhz_mdhmi);
			
			para.cg_fhz_mdhmi = ComntStream.readStream(byte_vect, offset + ret_len, para.cg_fhz_mdhmi); 
			ret_len += ComntStream.getDataSize(para.cg_fhz_mdhmi);
			
			para.qr_al1_1_uuid = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al1_1_uuid); 
			ret_len += ComntStream.getDataSize(para.qr_al1_1_uuid);
			
			para.qr_al2_1_uuid = ComntStream.readStream(byte_vect, offset + ret_len, para.qr_al2_1_uuid); 
			ret_len += ComntStream.getDataSize(para.qr_al2_1_uuid);
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.out_info, 0, para.out_info.length);
			ret_len += ComntStream.getDataSize(para.out_info[0]) * para.out_info.length;
			
			return ret_len;
		}
		
	}
	
	
	
	/**
	 * 预付费记录索引--[消息相关,请不要随意改动]
	 */
	public static class YFF_OPRECORD_IDX{
		public int		rtu_id	= 0;
		public short	id		= 0;
		public byte		op_type = 0;
		public int		op_date = 0;
		public int		op_time = 0;

		public byte		wasteno[] = new byte[ComntMsg.MSG_STR_LEN_32];
		
		public static void setYFF_OPRECORD_IDX(YFF_OPRECORD_IDX para, YFF_OPRECORD_IDX setValue){
			if(setValue == null){
				para = new YFF_OPRECORD_IDX();
			}else{
				para.rtu_id		= setValue.rtu_id;
				para.id			= setValue.id;
				para.op_type	= setValue.op_type;
				para.op_date	= setValue.op_date;
				para.op_time	= setValue.op_time;

				for (int i = 0; i < ComntMsg.MSG_STR_LEN_32; i++) {
					para.wasteno[i] = setValue.wasteno[i];
				}
				
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, YFF_OPRECORD_IDX para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.rtu_id);	
			ret_len += ComntStream.writeStream(byte_vect, para.id);	
			ret_len += ComntStream.writeStream(byte_vect, para.op_type);	
			ret_len += ComntStream.writeStream(byte_vect, para.op_date);	
			ret_len += ComntStream.writeStream(byte_vect, para.op_time);
			ret_len += ComntStream.writeStream(byte_vect, para.wasteno, 0, para.wasteno.length);
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFF_OPRECORD_IDX para){
			int ret_len   = len;
			
			para.rtu_id = ComntStream.readStream(byte_vect, offset + ret_len, para.rtu_id); 
			ret_len += ComntStream.getDataSize(para.rtu_id);
			
			para.id = ComntStream.readStream(byte_vect, offset + ret_len, para.id); 
			ret_len += ComntStream.getDataSize(para.id);
			
			para.op_type = ComntStream.readStream(byte_vect, offset + ret_len, para.op_type); 
			ret_len += ComntStream.getDataSize(para.op_type);
			
			para.op_date = ComntStream.readStream(byte_vect, offset + ret_len, para.op_date); 
			ret_len += ComntStream.getDataSize(para.op_date);
			
			para.op_time = ComntStream.readStream(byte_vect, offset + ret_len, para.op_time); 
			ret_len += ComntStream.getDataSize(para.op_time);
			
			ComntStream.readStream(byte_vect, offset + ret_len, para.wasteno, 0, para.wasteno.length);
			ret_len += ComntStream.getDataSize(para.wasteno[0]) * para.wasteno.length;
			
			return ret_len;
		}
	}
	

	
	
	/**
	 * 预付费-返回余额--[消息相关,请不要随意改动]
	 */
	public static class YFF_CALCREMAIN{
		public double	remain_val	= 0;
		public double	reserve1	= 0;
		public double	reserve2	= 0;
		public double	reserve3	= 0;
		
		public static void setYFF_CALCREMAIN(YFF_CALCREMAIN para, YFF_CALCREMAIN setValue){
			if(setValue == null){
				para = new YFF_CALCREMAIN();
			}else{
				para.remain_val	= setValue.remain_val;
				para.reserve1	= setValue.reserve1;
				para.reserve2	= setValue.reserve2;
				para.reserve3	= setValue.reserve3;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, YFF_CALCREMAIN para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.remain_val);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve1);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve2);	
			ret_len += ComntStream.writeStream(byte_vect, para.reserve3);	
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFF_CALCREMAIN para){
			int ret_len   = len;
			
			para.remain_val = ComntStream.readStream(byte_vect, offset + ret_len, para.remain_val); 
			ret_len += ComntStream.getDataSize(para.remain_val);
			
			para.reserve1 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve1); 
			ret_len += ComntStream.getDataSize(para.reserve1);
			
			para.reserve2 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve2); 
			ret_len += ComntStream.getDataSize(para.reserve2);
			
			para.reserve3 = ComntStream.readStream(byte_vect, offset + ret_len, para.reserve3); 
			ret_len += ComntStream.getDataSize(para.reserve3);
			
			return ret_len;
		}
		
	}

	
	/**
	 * //农排预付费状态表--[消息相关,请不要随意改动]
	 */
	public static class YFF_FARMERSTATE{
		
		public int		areaid 				= 0;						//所属片区
		public short	farmerid 			= 0;						//用户编号
		public byte		cus_state 			= 0;						//用户状态 0 初始态, 1 正常态, 10 销户态	
		
		public byte		op_type				= 0;						//本次操作类型 0 初始态, 1 开户, 2 缴费, 3 补卡, 4 补写卡, 5 冲正, 6 换表, 7 换费率 10 销户
		public int		op_date				= 0;						//本次操作日期
		public int		op_time				= 0;						//本次操作时间
		
		public double	pay_money			= 0;						//缴费金额	
		public double	othjs_money			= 0;						//结算金额(其它系统)
		public double	zb_money			= 0;						//追补金额	
		public double	all_money			= 0;						//总金额
 	
		public double 	ls_remain			= 0;						//上次剩余金额	
		public double 	cur_remain			= 0;						//当前剩余金额
		public double 	total_gdz			= 0;						//累计购电金额

		public int 		buy_times			= 0;						//购电次数
		public int 		totbuy_times		= 0;						//累计购电次数
		
		public double 	alarm1				= 0;						//报警值1		//备用
		public double 	alarm2				= 0;						//报警值2		//备用
		
		public int		kh_date				= 0;						//开户日期-YYYYMMDD
		public int		xh_date				= 0;						//销户日期-YYYYMMDD
		public int		js_bc_ymd			= 0;						//结算补差日期YYYYMMDD   //备用

		
		public static void setYFF_FARMERSTATE(YFF_FARMERSTATE para, YFF_FARMERSTATE setValue){
			if(setValue == null){
				para = new YFF_FARMERSTATE();
			}else{
				int i = 0;
				para.areaid				= setValue.areaid;
				para.farmerid			= setValue.farmerid;
				para.cus_state			= setValue.cus_state;
				
				para.op_type			= setValue.op_type;
				para.op_date			= setValue.op_date;
				para.op_time			= setValue.op_time;
				
				para.pay_money			= setValue.pay_money;	
				para.othjs_money		= setValue.othjs_money;
				para.zb_money			= setValue.zb_money;
				para.all_money			= setValue.all_money;
				
				para.ls_remain			= setValue.ls_remain;	
				para.cur_remain			= setValue.cur_remain;
				para.total_gdz			= setValue.total_gdz;	
				
				para.buy_times			= setValue.buy_times;	
				para.totbuy_times		= setValue.totbuy_times;
				
				para.alarm1				= setValue.alarm1;
				para.alarm2				= setValue.alarm2;
				
				para.kh_date			= setValue.kh_date;
				para.xh_date			= setValue.xh_date;
				para.js_bc_ymd			= setValue.js_bc_ymd;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, YFF_FARMERSTATE para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.areaid);	
			ret_len += ComntStream.writeStream(byte_vect, para.farmerid);	
			ret_len += ComntStream.writeStream(byte_vect, para.cus_state);	
			
			ret_len += ComntStream.writeStream(byte_vect, para.op_type);	
			ret_len += ComntStream.writeStream(byte_vect, para.op_date);	
			ret_len += ComntStream.writeStream(byte_vect, para.op_time);	
			
			ret_len += ComntStream.writeStream(byte_vect, para.pay_money);
			ret_len += ComntStream.writeStream(byte_vect, para.othjs_money);
			ret_len += ComntStream.writeStream(byte_vect, para.zb_money);
			ret_len += ComntStream.writeStream(byte_vect, para.all_money);
			
			ret_len += ComntStream.writeStream(byte_vect, para.ls_remain);
			ret_len += ComntStream.writeStream(byte_vect, para.cur_remain);
			ret_len += ComntStream.writeStream(byte_vect, para.total_gdz);
			
			ret_len += ComntStream.writeStream(byte_vect, para.buy_times);
			ret_len += ComntStream.writeStream(byte_vect, para.totbuy_times);	
			
			ret_len += ComntStream.writeStream(byte_vect, para.alarm1);
			ret_len += ComntStream.writeStream(byte_vect, para.alarm2);	
			
			ret_len += ComntStream.writeStream(byte_vect, para.kh_date);
			ret_len += ComntStream.writeStream(byte_vect, para.xh_date);
			ret_len += ComntStream.writeStream(byte_vect, para.js_bc_ymd);
			
			return ret_len;
		}

		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFF_FARMERSTATE para){
			int ret_len   = len;
			int i 		  = 0;
			
			para.areaid = ComntStream.readStream(byte_vect, offset + ret_len, para.areaid); 
			ret_len += ComntStream.getDataSize(para.areaid);
			
			para.farmerid = ComntStream.readStream(byte_vect, offset + ret_len, para.farmerid); 
			ret_len += ComntStream.getDataSize(para.farmerid);
			
			para.cus_state = ComntStream.readStream(byte_vect, offset + ret_len, para.cus_state); 
			ret_len += ComntStream.getDataSize(para.cus_state);
			
			para.op_type = ComntStream.readStream(byte_vect, offset + ret_len, para.op_type); 
			ret_len += ComntStream.getDataSize(para.op_type);
			
			para.op_date = ComntStream.readStream(byte_vect, offset + ret_len, para.op_date); 
			ret_len += ComntStream.getDataSize(para.op_date);
			
			para.op_time = ComntStream.readStream(byte_vect, offset + ret_len, para.op_time); 
			ret_len += ComntStream.getDataSize(para.op_time);
			
			para.pay_money = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_money); 
			ret_len += ComntStream.getDataSize(para.pay_money);
			
			para.othjs_money = ComntStream.readStream(byte_vect, offset + ret_len, para.othjs_money); 
			ret_len += ComntStream.getDataSize(para.othjs_money);
			
			para.zb_money = ComntStream.readStream(byte_vect, offset + ret_len, para.zb_money); 
			ret_len += ComntStream.getDataSize(para.zb_money);
			
			para.all_money = ComntStream.readStream(byte_vect, offset + ret_len, para.all_money); 
			ret_len += ComntStream.getDataSize(para.all_money);
			
			para.ls_remain = ComntStream.readStream(byte_vect, offset + ret_len, para.ls_remain); 
			ret_len += ComntStream.getDataSize(para.ls_remain);
			
			para.cur_remain = ComntStream.readStream(byte_vect, offset + ret_len, para.cur_remain); 
			ret_len += ComntStream.getDataSize(para.cur_remain);
			
			para.total_gdz = ComntStream.readStream(byte_vect, offset + ret_len, para.total_gdz); 
			ret_len += ComntStream.getDataSize(para.total_gdz);
			
			para.buy_times = ComntStream.readStream(byte_vect, offset + ret_len, para.buy_times); 
			ret_len += ComntStream.getDataSize(para.buy_times);
			
			para.totbuy_times = ComntStream.readStream(byte_vect, offset + ret_len, para.totbuy_times); 
			ret_len += ComntStream.getDataSize(para.totbuy_times);
			
			para.alarm1 = ComntStream.readStream(byte_vect, offset + ret_len, para.alarm1); 
			ret_len += ComntStream.getDataSize(para.alarm1);
			
			para.alarm2 = ComntStream.readStream(byte_vect, offset + ret_len, para.alarm2); 
			ret_len += ComntStream.getDataSize(para.alarm2);
			
			para.kh_date = ComntStream.readStream(byte_vect, offset + ret_len, para.kh_date); 
			ret_len += ComntStream.getDataSize(para.kh_date);
			
			para.xh_date = ComntStream.readStream(byte_vect, offset + ret_len, para.xh_date); 
			ret_len += ComntStream.getDataSize(para.xh_date);
			
			para.js_bc_ymd = ComntStream.readStream(byte_vect, offset + ret_len, para.js_bc_ymd); 
			ret_len += ComntStream.getDataSize(para.js_bc_ymd);
			
			return ret_len;
		}
	}
	
}

