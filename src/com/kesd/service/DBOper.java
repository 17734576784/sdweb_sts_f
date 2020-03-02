package com.kesd.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kesd.common.SDDef;
import com.kesd.common.YDTable;
import com.kesd.dbpara.JSPara;
import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;

public class DBOper {
	
	/**
	 * 根据sql语句查询数据库取得数据
	 * @return [{value:1,text:"text1"},{value:2,text:"text2"}]
	 */
	public static String getValueText(String sql){
		String empty = "\"\"";
		if(sql == null || sql.isEmpty())return empty;
		
		StringBuffer ret_buf = new StringBuffer();
		ret_buf.append("[");
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		boolean num_flag = false;
		try {
			//执行sql语句
			rs = j_dao.executeQuery(sql);
			//生成json字符串
			while(rs.next()){
				if(!num_flag)num_flag = true;
				ret_buf.append(SDDef.JSONLBRACES);
				ret_buf.append(SDDef.JSONQUOT + "value" + SDDef.JSONQACQ + rs.getString(1)+ SDDef.JSONCCM); 
				ret_buf.append(SDDef.JSONQUOT + "text" 	+ SDDef.JSONQACQ + CommBase.CheckString(rs.getString(2))+ SDDef.JSONQRBCM);
			}
			ret_buf.deleteCharAt(ret_buf.length() - 1);
			ret_buf.append("]");
		} catch (Exception e) {
			e.printStackTrace();
			return empty;
		}finally{
			j_dao.closeRs(rs);
		}
		if(!num_flag)return empty;
		
		return ret_buf.toString();
	}

	/**
	 * 根据sql语句查询数据库取得数据
	 * @return [{value:1,text:"text1"},{value:2,text:"text2"}]
	 */
	public static String getTariffValueText(String sql){
		String empty = "\"\"";
		if(sql == null || sql.isEmpty())return empty;
		
		StringBuffer ret_buf = new StringBuffer();
		ret_buf.append("[");
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		boolean num_flag = false;
		try {
			//执行sql语句
			rs = j_dao.executeQuery(sql);
			//生成json字符串
			while(rs.next()){
				if(!num_flag)num_flag = true;
				ret_buf.append(SDDef.JSONLBRACES);
				ret_buf.append(SDDef.JSONQUOT + "value" + SDDef.JSONQACQ + rs.getString(1)+ SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "text" 	+ SDDef.JSONQACQ + CommBase.CheckString(rs.getString(2)+"["+rs.getString(3)+"]")+ SDDef.JSONQRBCM);
			}
			ret_buf.deleteCharAt(ret_buf.length() - 1);
			ret_buf.append("]");
		} catch (Exception e) {
			e.printStackTrace();
			return empty;
		}finally{
			j_dao.closeRs(rs);
		}
		if(!num_flag)return empty;
		
		return ret_buf.toString();
	}

	/**
	 * 根据sql语句查询数据库取得数据
	 * @return [{value:1,text:"text1"},{value:2,text:"text2"}]
	 */
	public static String getResidentValueText(String sql){
		String empty = "\"\"";
		if(sql == null || sql.isEmpty())return empty;
		
		StringBuffer ret_buf = new StringBuffer();
		ret_buf.append("[");
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		boolean num_flag = false;
		try {
			//执行sql语句
			rs = j_dao.executeQuery(sql);
			//生成json字符串
			while(rs.next()){
				if(!num_flag)num_flag = true;
				ret_buf.append(SDDef.JSONLBRACES);
				ret_buf.append(SDDef.JSONQUOT + "value" + SDDef.JSONQACQ + rs.getString(1)+"_"+rs.getString(2)+ SDDef.JSONCCM); 
				ret_buf.append(SDDef.JSONQUOT + "text" 	+ SDDef.JSONQACQ + CommBase.CheckString(rs.getString(3))+ SDDef.JSONQRBCM);
			}
			ret_buf.deleteCharAt(ret_buf.length() - 1);
			ret_buf.append("]");
		} catch (Exception e) {
			e.printStackTrace();
			return empty;
		}finally{
			j_dao.closeRs(rs);
		}
		if(!num_flag)return empty;
		
		return ret_buf.toString();
	}
	
	/**获取数据字典数据
	 * @return [{value:1,text:"text1"},{value:2,text:"text2"}]
	 */
	@SuppressWarnings("unchecked")
	public static String getDictValueText(Map<Integer, String> map){
		String empty = "\"\"";
		if(map == null || map.size() == 0)return empty;
		
		StringBuffer ret_buf = new StringBuffer();
		ret_buf.append("[");
		List arrayList = new ArrayList(map.entrySet());
		sort(arrayList);
		//Collections.sort(arrayList, new TariffMapComparator());
		
		//根据map中的key value组成json字符串
		for (Iterator it = arrayList.iterator(); it.hasNext();) {
			Map.Entry en = (Map.Entry) it.next();
			ret_buf.append(SDDef.JSONLBRACES);
			ret_buf.append(SDDef.JSONQUOT + "value" + SDDef.JSONQACQ + en.getKey()   + SDDef.JSONCCM); 
			ret_buf.append(SDDef.JSONQUOT + "text" 	+ SDDef.JSONQACQ + en.getValue() + SDDef.JSONQRBCM);
		}
		
		ret_buf.deleteCharAt(ret_buf.length() - 1);
		ret_buf.append("]");
		
		return ret_buf.toString();
	}
	
	/**按key从小到大排序*/
	@SuppressWarnings("unchecked")
	private static void sort(List list) {
		if (list == null || list.size() == 0) return;
		
		Map.Entry obj1, obj2;
		int n1, n2;
		int n = list.size();
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < n - i; j++) {
				obj1 = (Map.Entry) list.get(j);
				obj2 = (Map.Entry) list.get(j + 1);
				n1 = CommBase.strtoi(obj1.toString());
				n2 = CommBase.strtoi(obj2.toString());
				if (n1 > n2) {
					Map.Entry tmp = obj1;
					list.set(j, obj2);
					list.set(j + 1, tmp);
				}
			}
		}
	}
	/*
	//排序
	public static class TariffMapComparator implements Comparator {
		public int compare(Object o1, Object o2) {
			Map.Entry obj1 = (Map.Entry) o1;
			Map.Entry obj2 = (Map.Entry) o2;
			return obj1.getKey().toString().compareTo(obj2.getKey().toString());
		}
		
	}
	*/
	
	/**高压 总加组结算记录表*/
	public static boolean insertZJs(JSPara.ZJSRecordPara para) {
		if(para == null) {
			return false;
		}
		
		String table = "yddataben.dbo.ZJsRecord" + String.valueOf(para.op_date).substring(0, 4);
		String sql = null;
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		try{
			sql = "select top 1 js_times,totjs_money,totjs_bmc from " + table + " where rtu_id="+para.rtu_id+" and zjg_id="+para.zjg_id+" and fxdf_ym="+para.fxdf_ym + " order by js_times desc";
			rs = j_dao.executeQuery(sql);
			
			double totjs_money = 0, totjs_bmc = 0;
			int num = 0;
			if(rs.next()) {
				num = rs.getInt("js_times");
				totjs_money = rs.getDouble("totjs_money");
				totjs_bmc = rs.getDouble("totjs_bmc");
			}
			para.js_times = num + 1;
			para.totjs_money = para.othjs_money + totjs_money;
			para.totjs_bmc = para.pay_bmc + totjs_bmc;
			
			sql = "insert into "
					+ table
					+ "(rtu_id,zjg_id,fxdf_ym,js_times,op_date,op_time,cons_id,cons_desc,op_man,pay_type,wasteno,pay_money,othjs_money,zb_money,all_money,buy_dl,pay_bmc,buy_times,misjs_money,totjs_money,misjs_bmc,totjs_bmc,cur_bd,lastala_val1,lastala_val2,lastshut_val,newala_val1,newala_val2,newshut_val) "
					+ "values(" + para.rtu_id + "," + para.zjg_id + ","
					+ para.fxdf_ym + "," + para.js_times + "," + para.op_date
					+ "," + para.op_time + "," + para.cons_id + ",'"
					+ para.cons_desc + "','" + para.op_man + "',"
					+ para.pay_type + ",'" + para.wasteno + "',"
					+ para.pay_money + "," + para.othjs_money + ","
					+ para.zb_money + "," + para.all_money + "," + para.buy_dl
					+ "," + para.pay_bmc + "," + para.buy_times + ","
					+ para.misjs_money + "," + para.totjs_money + ","
					+ para.misjs_bmc + "," + para.totjs_bmc + "," + para.cur_bd
					+ "," + para.lastala_val1 + "," + para.lastala_val2 + ","
					+ para.lastshut_val + "," + para.newala_val1 + ","
					+ para.newala_val2 + "," + para.newshut_val + ")";
			
			return j_dao.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			j_dao.closeRs(rs);
		}
	}
	
	/**低压 居民结算记录表*/
	public static boolean insertJJs(JSPara.JJSRecordPara para) {
		if(para == null) {
			return false;
		}
		
		String table = "yddataben.dbo.JJsRecord" + String.valueOf(para.op_date).substring(0, 4);
		String sql = null;
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		try{
			
			sql = "select top 1 js_times,totjs_money from " + table + " where rtu_id="+para.rtu_id+" and mp_id="+para.mp_id+" and fxdf_ym="+para.fxdf_ym + " order by js_times desc";
			rs = j_dao.executeQuery(sql);
			
			double totjs_money = 0;
			int num = 0;
			if(rs.next()) {
				num = rs.getInt("js_times");
				totjs_money = rs.getDouble("totjs_money");
			}
			para.js_times = num + 1;
			para.totjs_money = para.othjs_money + totjs_money;
			
			sql = "insert into "
					+ table
					+ "(rtu_id,mp_id,fxdf_ym,js_times,op_date,op_time,res_id,res_desc,op_man,pay_type,wasteno,pay_money,othjs_money,zb_money,all_money,buy_times,misjs_money,totjs_money,lastala_val1,lastala_val2,lastshut_val,newala_val1,newala_val2,newshut_val) "
					+ "values(" + para.rtu_id + "," + para.mp_id + ","
					+ para.fxdf_ym + "," + para.js_times + "," + para.op_date
					+ "," + para.op_time + "," + para.res_id + ",'"
					+ para.res_desc + "','" + para.op_man + "',"
					+ para.pay_type + ",'" + para.wasteno + "',"
					+ para.pay_money + "," + para.othjs_money + ","
					+ para.zb_money + "," + para.all_money + ","
					+ para.buy_times + "," + para.misjs_money + ","
					+ para.totjs_money + "," + para.lastala_val1 + ","
					+ para.lastala_val2 + "," + para.lastshut_val + ","
					+ para.newala_val1 + "," + para.newala_val2 + ","
					+ para.newshut_val + ")";
			
			return j_dao.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			j_dao.closeRs(rs);
		}
	}
	
	//sql查询,基本表中根据id查询出describe,临时这么写--zp
	public static String getDescribeById(String tablename, Object id){
		String sql = "select describe from " + tablename + " where id= " + id;
		String desc = "";
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		try {
			//执行sql语句
			rs = j_dao.executeQuery(sql);
			//生成json字符串
			while(rs.next()){
				desc = rs.getString(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return SDDef.EMPTY;
		}finally{
			j_dao.closeRs(rs);
		} 
		return desc;
	}
}
