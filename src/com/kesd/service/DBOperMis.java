package com.kesd.service;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.libweb.dao.JDBCDao;

public class DBOperMis {
	
	/***专变应用***/
	/**查找yddataben.dbo.zyff2012 中记录*/
	public static Map<String, String> loadOneZYffOpRecord(int rtu_id, int zjg_id, int op_date, int op_time, 
												int op_type1, int op_type2, int op_type3, int op_type4){
		
		Map<String, String> ret = null;
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		try{
			String con = "";
			if (op_type1 >= 0) {
				con += " and (op_type = " + op_type1;
				if (op_type2 >= 0) {
					con += " or op_type=" + op_type2;
				}

				if (op_type3 >= 0) {
					con += " or op_type=" + op_type3;
				}

				if (op_type4 >= 0) {
					con += " or op_type=" + op_type4;
				}
				con += ")";
			}

			con = " rtu_id = " + rtu_id + " and zjg_id = " + zjg_id + " and op_date = " + op_date + " and op_time =" + op_time + con;
			
			String sql = "select * from yddataben.dbo.zyff" + (op_date / 10000) + " where " + con;
			
			rs = j_dao.executeQuery(sql);
			if(rs.next()) {
				ret = new HashMap<String, String>();
				ret.put("op_type", rs.getString("op_type"));
				ret.put("wasteno", rs.getString("wasteno"));
				ret.put("rewasteno", rs.getString("rewasteno"));
				ret.put("op_date", rs.getString("op_date"));
				ret.put("op_time", rs.getString("op_time"));
				ret.put("up186_flag", rs.getString("up186_flag"));
				ret.put("checkpay_flag", rs.getString("checkpay_flag"));
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			j_dao.closeRs(rs);
		}
		
		return ret;
	}
	
	/**查找yddataben.dbo.zyff2012 中记录*/
	public static Map<String, String> loadOneZYffOpRecordWasteNo(int rtu_id, int zjg_id, int date, String wasteno){
		
		Map<String, String> ret = null;
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		try{
			String con = "";
			con = " rtu_id = " + rtu_id + " and zjg_id = " + zjg_id + " and op_date = " + date + " and wasteno ='" + wasteno + "'" + con;
			
			String sql = "select * from yddataben.dbo.zyff" + (date / 10000) + " where " + con;
			
			rs = j_dao.executeQuery(sql);
			if(rs.next()) {
				ret = new HashMap<String, String>();
				ret.put("op_type", rs.getString("op_type"));
				ret.put("wasteno", rs.getString("wasteno"));
				ret.put("rewasteno", rs.getString("rewasteno"));
				ret.put("op_date", rs.getString("op_date"));
				ret.put("op_time", rs.getString("op_time"));
				ret.put("up186_flag", rs.getString("up186_flag"));
				ret.put("checkpay_flag", rs.getString("checkpay_flag"));
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			j_dao.closeRs(rs);
		}
		
		return ret;
	}	
	
	/**查找yddataben.dbo.zyffmispay2012 中记录*/
	public static Map<String, String> loadOneZYffMisPayRecord(/*int rtu_id, int zjg_id, */int date, String waste_no){
		
		Map<String, String> ret = null;
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		try{
			//String sql = "select * from yddataben.dbo.zyffmispay" + (date / 10000) + " where rtu_id=" + rtu_id + " and zjg_id=" + zjg_id + " and date=" + date + " and jylsh='" + waste_no + "'";
			String sql = "select * from yddataben.dbo.zyffmispay" + (date / 10000) + " where " + " date=" + date + " and jylsh='" + waste_no + "'";
			rs = j_dao.executeQuery(sql);
			if(rs.next()) {
				ret = new HashMap<String, String>();
				ret.put("rtu_id", rs.getString("rtu_id"));
				ret.put("zjg_id", rs.getString("zjg_id"));
				ret.put("date", rs.getString("date"));
				ret.put("time", rs.getString("time"));
				ret.put("rever_flag", rs.getString("rever_flag"));
				ret.put("check_flag", rs.getString("check_flag"));
				ret.put("error_no", rs.getString("error_no"));
				ret.put("ccbh", rs.getString("ccbh"));
				ret.put("dsdwbh ", rs.getString("dsdwbh"));
				ret.put("czybh", rs.getString("czybh"));
				ret.put("jylsh", rs.getString("jylsh"));
				ret.put("yhbh", rs.getString("yhbh"));
				ret.put("jylb", rs.getString("jylb"));
				ret.put("jffs", rs.getString("jffs"));
				ret.put("jsfs", rs.getString("jsfs"));
				ret.put("jfje", rs.getString("jfje"));
				ret.put("yhzwrq", rs.getString("yhzwrq"));
				ret.put("dyfp", rs.getString("dyfp"));
				ret.put("jfbs", rs.getString("jfbs"));
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			j_dao.closeRs(rs);
		}
		
		return ret;
	}
		
	/**查找yddataben.dbo.zyffmisrever2012中记录*/
	public static Map<String, String> loadOneZYffMisReverRecord(int date, String wasteno) {
		Map<String, String> ret = null;
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		try{
			String sql = "select * from yddataben.dbo.zyffmisrever" + (date / 10000) + " where czjylsh=" + wasteno;
			rs = j_dao.executeQuery(sql);
			if(rs.next()) {
				ret = new HashMap<String, String>();
				ret.put("czybh", rs.getString("czybh"));
				ret.put("error_no", rs.getString("error_no"));
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			j_dao.closeRs(rs);
		}
		
		return ret;
	}
	
	/**查找yddataben.dbo.ZYffMisPayNew2008 中记录*/
	public static Map<String, String> loadNewZYffMisPayRecord(int date, String waste_no){
		
		Map<String, String> ret = null;
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		try{
			String sql = "select * from yddataben.dbo.ZYffMisPayNew" + (date / 10000) + " where " + " date=" + date + " and jylsh='" + waste_no + "'";
			rs = j_dao.executeQuery(sql);
			if(rs.next()) {
				ret = new HashMap<String, String>();
				ret.put("rtu_id", 		rs.getString("rtu_id"));								
				ret.put("zjg_id", 		rs.getString("zjg_id"));                          
				ret.put("date", 		rs.getString("date"));                                        
				ret.put("time", 		rs.getString("time"));                              
				ret.put("rever_flag", 	rs.getString("rever_flag"));                  
				ret.put("check_flag", 	rs.getString("check_flag"));                            
				ret.put("error_no", 	rs.getString("error_no")); 
				ret.put("error_msg", 	rs.getString("error_msg"));
				ret.put("ccbh", 		rs.getString("ccbh"));                              
				ret.put("dsdwbh ", 		rs.getString("dsdwbh"));                                   
				ret.put("czybh", 		rs.getString("czybh"));                            
				ret.put("jylsh", 		rs.getString("jylsh"));
				ret.put("dzpc", 		rs.getString("dzpc"));
				ret.put("yhbh", 		rs.getString("yhbh"));                                        
				ret.put("jylb", 		rs.getString("jylb"));                              
				ret.put("jffs", 		rs.getString("jffs"));                                        
				ret.put("jsfs", 		rs.getString("jsfs"));                              
				ret.put("bcsj_zje", 	rs.getString("bcsj_zje"));
				ret.put("bcsj_qfje", 	rs.getString("bcsj_qfje"));
				ret.put("bcsj_ysje", 	rs.getString("bcsj_ysje"));
				ret.put("yhzwrq", 		rs.getString("yhzwrq"));                          
				ret.put("dyfp", 		rs.getString("dyfp"));                              			
				ret.put("jfbs", 		rs.getString("jfbs"));                                        
			}                                                                       
		} catch(Exception e){                                                       
			e.printStackTrace();                                                    
		} finally {                                                                 
			j_dao.closeRs(rs);                                                                
		}                                                                           	
		                                                                            	
		return ret;                                                                 	
	}                                                                                         
	
	/**查找yddataben.dbo.ZYffMisReverNew2008中记录*/
	public static Map<String, String> loadNewZYffMisReverRecord(int date, String wasteno) {
		Map<String, String> ret = null;
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		try{
			String sql = "select * from yddataben.dbo.ZYffMisReverNew" + (date / 10000) + " where czjylsh=" + wasteno;
			rs = j_dao.executeQuery(sql);
			if(rs.next()) {
				ret = new HashMap<String, String>();
				ret.put("czybh", 	rs.getString("czybh"));
				ret.put("error_no", rs.getString("error_no"));
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			j_dao.closeRs(rs);
		}
		
		return ret;
	}
	
	/**更新记录*/                                                                    
	public static boolean updateZYffPayRecord(int rtu_id, int zjg_id, int date, int time, int op_type, 
											boolean up_flag, boolean ok_flag) {

		JDBCDao j_dao = new JDBCDao();

		boolean ret = false;
		try{
			String sql = "update yddataben.dbo.zyff" + (date / 10000) + " set up186_flag = " + (up_flag ? 1 : 0) + ", checkpay_flag =" + (ok_flag ? 1 : 0) + " where rtu_id=" + rtu_id + " and zjg_id=" + zjg_id + " and op_date=" + date + " and op_time=" + time + " and op_type = " + op_type;
			ret = j_dao.executeUpdate(sql);
		} catch(Exception e){
			ret = false;
		}

		try{		
			String sql = "update yddataben.dbo.syszyfrcd" + (date / 10000) + " set up186_flag = " + (up_flag ? 1 : 0) + ", checkpay_flag =" + (ok_flag ? 1 : 0) + " where rtu_id=" + rtu_id + " and zjg_id=" + zjg_id + " and op_date=" + date + " and op_time=" + time + " and op_type = " + op_type;
			j_dao.executeUpdate(sql);		
		} catch(Exception e){
		}
		
		return ret;
	}
	
	
	/***居民应用***/
	/**查找yddataben.dbo.jyff2012 中记录*/
	public static Map<String, String> loadOneJYffOpRecord(int rtu_id, int mp_id, int op_date, int op_time, 
												int op_type1, int op_type2, int op_type3, int op_type4){
		
		Map<String, String> ret = null;
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		try{
			String con = "";
			if (op_type1 >= 0) {
				con += " and (op_type = " + op_type1;
				if (op_type2 >= 0) {
					con += " or op_type=" + op_type2;
				}

				if (op_type3 >= 0) {
					con += " or op_type=" + op_type3;
				}

				if (op_type4 >= 0) {
					con += " or op_type=" + op_type4;
				}
				con += ")";
			}

			con = " rtu_id = " + rtu_id + " and mp_id = " + mp_id + " and op_date = " + op_date + " and op_time =" + op_time + con;
			
			String sql = "select * from yddataben.dbo.jyff" + (op_date / 10000) + " where " + con;
			
			rs = j_dao.executeQuery(sql);
			if(rs.next()) {
				ret = new HashMap<String, String>();
				ret.put("op_type", rs.getString("op_type"));
				ret.put("wasteno", rs.getString("wasteno"));
				ret.put("rewasteno", rs.getString("rewasteno"));
				ret.put("op_date", rs.getString("op_date"));
				ret.put("op_time", rs.getString("op_time"));
				ret.put("up186_flag", rs.getString("up186_flag"));
				ret.put("checkpay_flag", rs.getString("checkpay_flag"));
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			j_dao.closeRs(rs);
		}
		
		return ret;
	}
	
	/**查找yddataben.dbo.jyff2012 中记录*/
	public static Map<String, String> loadOneJYffOpRecordWasteNo(int rtu_id, int mp_id, int date, String wasteno){
		
		Map<String, String> ret = null;
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		try{
			String con = "";
			con = " rtu_id = " + rtu_id + " and mp_id = " + mp_id + " and op_date = " + date + " and wasteno ='" + wasteno + "'" + con;
			
			String sql = "select * from yddataben.dbo.jyff" + (date / 10000) + " where " + con;
			
			rs = j_dao.executeQuery(sql);
			if(rs.next()) {
				ret = new HashMap<String, String>();
				ret.put("op_type", rs.getString("op_type"));
				ret.put("wasteno", rs.getString("wasteno"));
				ret.put("rewasteno", rs.getString("rewasteno"));
				ret.put("op_date", rs.getString("op_date"));
				ret.put("op_time", rs.getString("op_time"));
				ret.put("up186_flag", rs.getString("up186_flag"));
				ret.put("checkpay_flag", rs.getString("checkpay_flag"));
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			j_dao.closeRs(rs);
		}
		
		return ret;
	}	
	
	/**查找yddataben.dbo.jyffmispay2012 中记录*/
	public static Map<String, String> loadOneJYffMisPayRecord(int date, String waste_no){
		
		Map<String, String> ret = null;
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		try{
			//String sql = "select * from yddataben.dbo.zyffmispay" + (date / 10000) + " where rtu_id=" + rtu_id + " and zjg_id=" + zjg_id + " and date=" + date + " and jylsh='" + waste_no + "'";
			String sql = "select * from yddataben.dbo.jyffmispay" + (date / 10000) + " where " + " date=" + date + " and jylsh='" + waste_no + "'";
			rs = j_dao.executeQuery(sql);
			if(rs.next()) {
				ret = new HashMap<String, String>();
				ret.put("rtu_id", rs.getString("rtu_id"));
				ret.put("mp_id", rs.getString("mp_id"));
				ret.put("date", rs.getString("date"));
				ret.put("time", rs.getString("time"));
				ret.put("rever_flag", rs.getString("rever_flag"));
				ret.put("check_flag", rs.getString("check_flag"));
				ret.put("error_no", rs.getString("error_no"));
				ret.put("ccbh", rs.getString("ccbh"));
				ret.put("dsdwbh ", rs.getString("dsdwbh"));
				ret.put("czybh", rs.getString("czybh"));
				ret.put("jylsh", rs.getString("jylsh"));
				ret.put("yhbh", rs.getString("yhbh"));
				ret.put("jylb", rs.getString("jylb"));
				ret.put("jffs", rs.getString("jffs"));
				ret.put("jsfs", rs.getString("jsfs"));
				ret.put("jfje", rs.getString("jfje"));
				ret.put("yhzwrq", rs.getString("yhzwrq"));
				ret.put("dyfp", rs.getString("dyfp"));
				ret.put("jfbs", rs.getString("jfbs"));
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			j_dao.closeRs(rs);
		}
		
		return ret;
	}
		
	
	/**查找yddataben.dbo.jyffmisrever2012中记录*/
	public static Map<String, String> loadOneJYffMisReverRecord(int date, String wasteno) {
		Map<String, String> ret = null;
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		try{
			String sql = "select * from yddataben.dbo.jyffmisrever" + (date / 10000) + " where czjylsh=" + wasteno;
			rs = j_dao.executeQuery(sql);
			if(rs.next()) {
				ret = new HashMap<String, String>();
				ret.put("czybh", rs.getString("czybh"));
				ret.put("error_no", rs.getString("error_no"));
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			j_dao.closeRs(rs);
		}
		
		return ret;
	}	
	
	/**查找yddataben.dbo.JYffMisPayNew2008 中记录*/
	public static Map<String, String> loadNewJYffMisPayRecord(int date, String waste_no){
		
		Map<String, String> ret = null;
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
			
		try{
			String sql = "select * from yddataben.dbo.JYffMisPayNew" + (date / 10000) + " where " + " date=" + date + " and jylsh='" + waste_no + "'";
			rs = j_dao.executeQuery(sql);
			if(rs.next()) {
				ret = new HashMap<String, String>();
				ret.put("rtu_id", 		rs.getString("rtu_id"));										
				ret.put("mp_id", 		rs.getString("mp_id"));                                
				ret.put("date", 		rs.getString("date"));                                  
				ret.put("time", 		rs.getString("time"));                                  
				ret.put("rever_flag", 	rs.getString("rever_flag"));                      
				ret.put("check_flag", 	rs.getString("check_flag"));                      
				ret.put("error_no", 	rs.getString("error_no"));
				ret.put("error_msg", 	rs.getString("error_msg"));
				ret.put("ccbh", 		rs.getString("ccbh"));                                  	
				ret.put("dsdwbh ", 		rs.getString("dsdwbh"));                             		
				ret.put("czybh", 		rs.getString("czybh"));                                
				ret.put("jylsh", 		rs.getString("jylsh"));
				ret.put("dzpc", 		rs.getString("dzpc"));
				ret.put("yhbh", 		rs.getString("yhbh"));                                  		
				ret.put("jylb", 		rs.getString("jylb"));                                  
				ret.put("jffs", 		rs.getString("jffs"));                                  			
				ret.put("jsfs", 		rs.getString("jsfs"));                                  	
				ret.put("bcsj_zje", 	rs.getString("bcsj_zje"));                                  
				ret.put("bcsj_qfje", 	rs.getString("bcsj_qfje"));
				ret.put("bcsj_ysje", 	rs.getString("bcsj_ysje"));
				ret.put("yhzwrq", 		rs.getString("yhzwrq"));                             	
				ret.put("dyfp", 		rs.getString("dyfp"));                                 	
				ret.put("jfbs", 		rs.getString("jfbs"));                                 	
			}                                                                           
		} catch(Exception e){                                                           
			e.printStackTrace();                                                        
		} finally {                                                                     
			j_dao.closeRs(rs);
		}
		
		return ret;
	}
	
	/**查找yddataben.dbo.JYffMisReverNew2008中记录*/
	public static Map<String, String> loadNewJYffMisReverRecord(int date, String wasteno) {
		Map<String, String> ret = null;
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		try{
			String sql = "select * from yddataben.dbo.JYffMisReverNew" + (date / 10000) + " where czjylsh=" + wasteno;
			rs = j_dao.executeQuery(sql);
			if(rs.next()) {
				ret = new HashMap<String, String>();
				ret.put("czybh", 	rs.getString("czybh"));
				ret.put("error_no", rs.getString("error_no"));
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			j_dao.closeRs(rs);
		}
		return ret;
	}
	
	/**更新记录*/
	public static boolean updateJYffPayRecord(int rtu_id, int mp_id, int date, int time, int op_type, 
			boolean up_flag, boolean ok_flag) {

		JDBCDao j_dao = new JDBCDao();
		
		boolean ret = false;
		try{
			String sql = "update yddataben.dbo.jyff" + (date / 10000) + " set up186_flag = " + (up_flag ? 1 : 0) + ", checkpay_flag =" + (ok_flag ? 1 : 0) + " where rtu_id=" + rtu_id + " and mp_id=" + mp_id + " and op_date=" + date + " and op_time=" + time + " and op_type = " + op_type;
			ret = j_dao.executeUpdate(sql);
		} catch(Exception e){
			ret = false;
		}
		
		try{		
			String sql = "update yddataben.dbo.sysjyfrcd" + (date / 10000) + " set up186_flag = " + (up_flag ? 1 : 0) + ", checkpay_flag =" + (ok_flag ? 1 : 0) + " where rtu_id=" + rtu_id + " and mp_id=" + mp_id + " and op_date=" + date + " and op_time=" + time + " and op_type = " + op_type;
			j_dao.executeUpdate(sql);		
		} catch(Exception e){
		}
		
		return ret;
	}	

}
