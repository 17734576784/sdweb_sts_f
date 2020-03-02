package com.kesd.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.libweb.dao.HibSessionFactory;

public class TestConnectionPool {
	public static void main(String[] args) {
		HibSessionFactory.url = "jdbc:sqlserver://10.1.1.7:1433;SelectMethod=cursor;DatabaseName=YdParaben";
		HibSessionFactory.username = "sa";
		HibSessionFactory.password = "";
		HibSessionFactory.driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		Connection con = ConnetionPool.getConnection();
		Connection con1 = ConnetionPool.getConnection();
		Connection con2 = ConnetionPool.getConnection(); // do something with con...
		
		try {
			ResultSet rs = null;
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery("select id,describe from rtupara");
			while(rs.next()){
				System.out.println("id:"+rs.getInt("id")+",describe:"+rs.getString("describe"));
			}
			stmt.close();
			rs.close();
			con.close();
		} catch (Exception e) {}
		
		try {
			con1.close();
		} catch (Exception e) {}
		
		try {
			con2.close();
		} catch (Exception e) {}
		
		con = ConnetionPool.getConnection();
		con1 = ConnetionPool.getConnection();
		con2 = ConnetionPool.getConnection();
		
		try {
			con.close();
			con1.close();
			con2.close();
		} catch (Exception e) {}
		
		ConnetionPool.printDebugMsg();
		
	}
}
