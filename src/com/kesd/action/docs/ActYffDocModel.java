package com.kesd.action.docs;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.struts2.json.annotations.JSON;

import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;

public class ActYffDocModel extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2075299843013707216L;
	private String filed;
	private String describe;
	private String apptype;
	private String mid;
	private String flag;
	
	//增加新模板
	public String add() throws SQLException{
		if(filed == null || filed.isEmpty()){
			flag = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		//查询此模板名称是否存在
		String getNameSql = "select count(id) num from yffdoc_template where describe = '" + describe + "'"; 
		rs = j_dao.executeQuery(getNameSql);
		//如果存在，返回 信息为samename
		while(rs.next()){
			int num = rs.getInt("num");
			if(num > 0){
				flag = "samename";
				return SDDef.SUCCESS;
			}
		}
		
		int id = 1;
		String getMaxId = "select max(id) id from yffdoc_template";
		
		 rs = j_dao.executeQuery(getMaxId);
		while(rs.next()){
			id = rs.getInt("id");
		}
		
		String sql = "insert into yffdoc_template values('" + (++id) + "','" + describe + "'," + apptype + ",'" + filed + "')";
		if(j_dao.executeUpdate(sql)){
			flag = SDDef.SUCCESS;
			mid = id + "";
		}
		else{
			flag = SDDef.FAIL;
		}
		return SDDef.SUCCESS;
	}
	
	//编辑修改模板
	public String editById() throws SQLException{
		//describe,mid已经在js中判断过,这里不重复判断
		if(filed == null || filed.isEmpty()){
			flag = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		JDBCDao j_dao = new JDBCDao();
		
		//查询此模板名称是否存在
		String getNameSql = "select count(id) num from yffdoc_template where describe = '" + describe + "' and id <>" + mid; 
		ResultSet rs = j_dao.executeQuery(getNameSql);
		//如果存在，返回 信息为samename
		while(rs.next()){
			int num = rs.getInt("num");
			if(num > 0){
				flag = "samename";
				return SDDef.SUCCESS;
			}
		}
		
		String sql = "update yffdoc_template set describe = \'" + describe + "\', tpldata = \'" + filed + "\' where id = " + mid;
		if(j_dao.executeUpdate(sql)){
			flag = SDDef.SUCCESS;
		}
		else{
			flag = SDDef.FAIL;
		}
		return SDDef.SUCCESS;
	}
	
	//删除模板
	public String delById(){
		//mid已经在js中判断过,这里不重复判断
		JDBCDao j_dao = new JDBCDao();
		String sql = "delete from yffdoc_template where id = " + mid;
		if(j_dao.executeUpdate(sql)){
			flag = SDDef.SUCCESS;
		}
		else{
			flag = SDDef.FAIL;
		}
		return SDDef.SUCCESS;
	}
	
	//根据id获取模板内容
	@JSON(serialize = false)
	public String getModelById(){
		if(mid == null || mid.isEmpty()){
			flag = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		JDBCDao j_dao = new JDBCDao();
		String sql = "select * from yffdoc_template where id = " + mid;
		try{
			ResultSet rs = j_dao.executeQuery(sql);
			while(rs.next()){
				describe = rs.getString("describe");
				byte tpl[] = rs.getBytes("tpldata");
				filed = new String(tpl);
				flag = SDDef.SUCCESS;
			}
			}catch(SQLException e){
				e.printStackTrace();
			}
		return SDDef.SUCCESS;
	}
	
	//初始加载显示模板id列表
	@JSON(serialize = false)
	public String getModelIdList(){
		if(apptype == null || apptype.isEmpty()){
			filed = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		StringBuffer ref = new StringBuffer();
		ref.append(SDDef.JSONROWSTITLE);
		JDBCDao j_dao = new JDBCDao();
		String sql = "select id,describe from yffdoc_template where app_type = " + apptype;
		try{
			ResultSet rs = j_dao.executeQuery(sql);
			while(rs.next()){
				ref.append(SDDef.JSONLBRACES);
				ref.append(SDDef.JSONQUOT + "value" + SDDef.JSONQACQ + CommFunc.CheckString(rs.getString("id")) + SDDef.JSONCCM); 
				ref.append(SDDef.JSONQUOT + "text" 	+ SDDef.JSONQACQ + CommFunc.CheckString(rs.getString("describe")) + SDDef.JSONQRBCM);
			}
			
			if(ref.toString().equals(SDDef.JSONROWSTITLE)){
				filed = "";
				return SDDef.SUCCESS;
			}		
			
			ref.setCharAt(ref.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ref.append(SDDef.JSONRBRACES);
			
			filed=ref.toString();
			}catch(SQLException e){
				e.printStackTrace();
			}
		return SDDef.SUCCESS;
	}

	public String getFiled() {
		return filed;
	}
	public void setFiled(String filed) {
		this.filed = filed;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getApptype() {
		return apptype;
	}
	public void setApptype(String apptype) {
		this.apptype = apptype;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
