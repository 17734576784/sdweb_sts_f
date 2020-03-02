package com.kesd.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.libweb.dao.JDBCDao;

 

public class SDOperlog {
	public static int Log_index = 1;
	
	public static void operlog(short id,byte opertype,String oper_info){
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss",Locale.SIMPLIFIED_CHINESE);
		String sdate = sdf.format(date);
		String sdates[] = sdate.split("-");
		
		JDBCDao j_dao = new JDBCDao();
		if (opertype != SDDef.LOG_LOGOUT) {		
			oper_info += getIPAddr();
		}
		
		
		String sql = "insert into yddataben.dbo.operlog" + sdates[0].substring(0,4) + " values(" + id + "," + sdates[0] + "," + sdates[1] + ","+opertype+",'"+oper_info+"',null,null,null)";
		j_dao.executeUpdate(sql);
		Log_index %= 0xfffffff;
	}
	
	public static String getIPAddr(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		if(request == null){
			return "";
		}
		
		return " ——" + request.getRemoteAddr();
	}
}
