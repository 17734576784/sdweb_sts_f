package com.kesd.common;

import java.sql.ResultSet;

import com.kesd.dbpara.YffRatePara;
import com.libweb.dao.JDBCDao;


public class CommCardWrt {
	
	public static class MPPAY_PARA {
		public int    	rtu_id      	= 0;
		public short  	mp_id    	  	= 0;
		public byte 	yffmeter_type	= 0;
		public short	feeproj_id		= 0;
		public short	yffalarm_id		= 0;
		
	}
	
	public static MPPAY_PARA loadMpPayPara(int rtu_id, short mp_id) {
		JDBCDao jdbc_dao = new JDBCDao();
		
		String sql = "select a.rtu_id, a.mp_id, a.yffmeter_type, a.feeproj_id, a.yffalarm_id " + 
					" from mppay_para a where a.rtu_id = " + rtu_id + " and a.mp_id = " + mp_id;
		
		MPPAY_PARA ret_para = null;
		
		try{
			ret_para = new MPPAY_PARA();
			ResultSet rs = jdbc_dao.executeQuery(sql);
			
			if(rs.next()){
				ret_para.rtu_id      	= rs.getInt("rtu_id");
				ret_para.mp_id		 	= rs.getShort("mp_id");
				ret_para.yffmeter_type	= rs.getByte("yffmeter_type");
				ret_para.feeproj_id	 	= rs.getShort("feeproj_id");
				ret_para.yffalarm_id 	= rs.getShort("yffalarm_id");
			}else{
				
				ret_para = null;
			}
			jdbc_dao.closeRs(rs);
		}catch (Exception e) {
			ret_para = null;
			e.printStackTrace();
		}
		
		return ret_para;
	}
	
	
	public static class YFF_ALARM_PARA {
		public short	id			= 0;
		public int		alarm1		= 0;
		public int		alarm2 		= 0;
	}
	
	public static YFF_ALARM_PARA loadYffAlarmPara(short id) {
		JDBCDao jdbc_dao = new JDBCDao();
		
		String sql = "select a.id, a.alarm1, a.alarm2 " + 
					" from yffalarmpara a where a.id = " + id;
		
		YFF_ALARM_PARA ret_para = null;
		
		try{
			ret_para = new YFF_ALARM_PARA();
			ResultSet rs = jdbc_dao.executeQuery(sql);
			
			if(rs.next()){
				ret_para.id      		= rs.getShort("id");
				ret_para.alarm1			= rs.getInt("alarm1");
				ret_para.alarm2			= rs.getInt("alarm2");
			}else{
				
				ret_para = null;
			}
			jdbc_dao.closeRs(rs);
		}catch (Exception e) {
			ret_para = null;
			e.printStackTrace();
		}
		
		return ret_para;
	}
	
	
//	public static class YFF_RATE_PARA {
//		public short	id			= 0;
//		public byte		fee_type	= 1;
//		
//		public double	rated_z		= 0.0;
//		public double	ratef_j 	= 0.0;
//		public double	ratef_f 	= 0.0;
//		public double	ratef_p 	= 0.0;
//		public double	ratef_g 	= 0.0;
//		
//		public double	rateh_1 	= 0.0;
//		public double	rateh_2 	= 0.0;
//		public double	rateh_3 	= 0.0;
//		public double	rateh_4 	= 0.0;
//		
//		public double	ratej_1 	= 0.0;
//		public double	ratej_2 	= 0.0;
//		public double	ratej_3 	= 0.0;
//		public double	ratej_4 	= 0.0;
//		
//		public double	money_limit = 0.0;
//	}
	
//	public static YffRatePara loadYffRatePara(short id) {
//		JDBCDao jdbc_dao = new JDBCDao();
//		
//		String sql = "select a.id, a.fee_type, a.rated_z, a.ratef_j, a.ratef_f, a.ratef_p, a.ratef_g,"+
//					"a.rateh_1, a.rateh_2, a.rateh_3, a.rateh_4, a.ratej_1, a.ratej_2, a.ratej_3, a.ratej_4, a.money_limit "+
//					" from yffratepara a where a.id = " + id;
//		
//		YFF_RATE_PARA ret_para = null;
//		
//		try{
//			ret_para = new YFF_RATE_PARA();
//			ResultSet rs = jdbc_dao.executeQuery(sql);
//			
//			if(rs.next()){
//				ret_para.id      		= rs.getShort("id");
//				ret_para.fee_type		= rs.getByte("fee_type");
//				
//				ret_para.rated_z		= rs.getDouble("rated_z");
//				ret_para.ratef_j		= rs.getDouble("ratef_j");
//				ret_para.ratef_f		= rs.getDouble("ratef_f");
//				ret_para.ratef_p		= rs.getDouble("ratef_p");
//				ret_para.ratef_g		= rs.getDouble("ratef_g");
//				
//				ret_para.rateh_1		= rs.getDouble("rateh_1");
//				ret_para.rateh_2		= rs.getDouble("rateh_2");
//				ret_para.rateh_3		= rs.getDouble("rateh_3");
//				ret_para.rateh_4		= rs.getDouble("rateh_4");
//				
//				ret_para.ratej_1		= rs.getDouble("ratej_1");
//				ret_para.ratej_2		= rs.getDouble("ratej_2");
//				ret_para.ratej_3		= rs.getDouble("ratej_3");
//				ret_para.ratej_4		= rs.getDouble("ratej_4");
//				ret_para.money_limit	= rs.getDouble("money_limit");
//				
//			}else{
//				
//				ret_para = null;
//			}
//			jdbc_dao.closeRs(rs);
//		}catch (Exception e) {
//			ret_para = null;
//			e.printStackTrace();
//		}
//		
//		return ret_para;
//	}
//	
//	
	public static class MET_RES_MP {
		public String	cons_no		= "";
		public String	meter_no	= "";
		
		public int		rtu_id		= 0;
		public short	mp_id 		= 0;
		
		public String	cons_desc	= "";
		public String	cons_addr	= "";
		public String	cons_telno	= "";
		public double	pt			= 0.0;
		public double	ct			= 0.0;
		
		public int		pt_n		= 0;
		public int		pt_d		= 0;
		
		public int		ct_n		= 0;
		public int		ct_d		= 0;
	}
	
	public static MET_RES_MP loadMetResMpPara(String consNo) {
		JDBCDao jdbc_dao = new JDBCDao();
		
		String sql = "select a.rtu_id as rtu_id, a.id as mp_id,b.id as cons_id,"+
					"b.cons_no as cons_no, c.meter_id as meter_no, "+
					"b.describe as cons_desc,b.address as cons_addr, "+
					"b.mobile as cons_telno,a.pt_numerator as pt_n,a.pt_denominator as pt_d,a.pt_ratio as pt,"+
					"a.ct_numerator as ct_n,a.ct_denominator as ct_d,a.ct_ratio as ct "+
					
					" from ydparaben.dbo.mppara as a, ydparaben.dbo.residentpara as b,"+
					" ydparaben.dbo.meterpara as c where a.rtu_id = c.rtu_id and "+
					" a.id = c.mp_id and c.resident_id = b.id and "+
					" c.rtu_id = b.rtu_id and b.cons_no=" + consNo;
		
		MET_RES_MP ret_para = null;
		
		try{
			ret_para = new MET_RES_MP();
			ResultSet rs = jdbc_dao.executeQuery(sql);
			
			if(rs.next()){
				ret_para.cons_no      	= rs.getString("cons_no");
				ret_para.meter_no		= rs.getString("meter_no");
				
				ret_para.rtu_id			= rs.getInt("rtu_id");
				ret_para.mp_id			= rs.getShort("mp_id");
				
				ret_para.cons_desc		= rs.getString("cons_desc");
				ret_para.cons_addr		= rs.getString("cons_addr");
				ret_para.cons_telno		= rs.getString("cons_telno");
				
				ret_para.pt_n			= rs.getInt("pt_n");
				ret_para.pt_d			= rs.getInt("pt_d");
				
				ret_para.pt				= rs.getDouble("pt");
				ret_para.ct				= rs.getDouble("ct");
				
				ret_para.ct_n			= rs.getInt("ct_n");
				ret_para.ct_d			= rs.getInt("ct_d");
			}else{
				
				ret_para = null;
			}
			jdbc_dao.closeRs(rs);
		}catch (Exception e) {
			ret_para = null;
			e.printStackTrace();
		}
		
		return ret_para;
	}
	
	public static class ZJGPAY_PARA {
		public int    	rtu_id      	= 0;
		public short  	zjg_id    	  	= 0;
		public byte 	cardmeter_type	= 0;
		public short	feeproj_id		= 0;
		public short	yffalarm_id		= 0;
		
	}
	
	public static ZJGPAY_PARA loadZjgPayPara(int rtu_id, short zjg_id) {
		JDBCDao jdbc_dao = new JDBCDao();
		
		String sql = "select a.rtu_id, a.zjg_id, a.cardmeter_type, a.feeproj_id, a.yffalarm_id  " + 
		 "from zjgpay_para a where a.rtu_id ='" + rtu_id + "' and a.zjg_id = " + zjg_id;		
		
		ZJGPAY_PARA ret_para = null;
		
		try{
			ret_para = new ZJGPAY_PARA();
			ResultSet rs = jdbc_dao.executeQuery(sql);
			
			if(rs.next()){
				ret_para.rtu_id      	= rs.getInt("rtu_id");
				ret_para.zjg_id		 	= rs.getShort("zjg_id");
				ret_para.cardmeter_type	= rs.getByte("cardmeter_type");
				ret_para.feeproj_id	 	= rs.getShort("feeproj_id");
				ret_para.yffalarm_id 	= rs.getShort("yffalarm_id");
			}else{
				
				ret_para = null;
			}
			jdbc_dao.closeRs(rs);
		}catch (Exception e) {
			ret_para = null;
			e.printStackTrace();
		}
		
		return ret_para;
	}




}
