package com.kesd.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.libweb.dao.JDBCDao;

public class SqlPage {
	
	private int pagesize=10;
	private int totalrecords;
	private int currentpage=1;
	
	public SqlPage(int currentpage,int pagesize){
    	this.currentpage = currentpage;
    	this.pagesize = pagesize;
    }
	
	public List<Map<String, Object>> getRecord(String sql) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		try {
			rs = j_dao.executeQuery(sql);
			ResultSetMetaData rm = rs.getMetaData();
			int count = rm.getColumnCount();
			
			while(rs.next()){
				Map<String, Object> mp = new HashMap<String, Object>();
				for (int c = 1; c <= count; c++) {
					mp.put(rm.getColumnName(c), rs.getObject(c));
				}
				list.add(mp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			j_dao.closeRs(rs);
		}
		
		return list;
	}
	
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getTotalrecords() {
		return totalrecords;
	}
	
	public void setTotalrecords(String sql) {
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		try {
			rs = j_dao.executeQuery(sql);
			if(rs.next()){
				totalrecords = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			j_dao.closeRs(rs);
		}
	}
	public int getCurrentpage() {
		return currentpage;
	}
	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}
	
}
