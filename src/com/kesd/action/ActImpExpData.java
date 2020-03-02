package com.kesd.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

import com.kesd.common.SDDef;
import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;;

public class ActImpExpData extends ActionSupport{
	private String 		filed;
	private String 		filename;
	private InputStream txtStream;
	private File 		myFile;
	private String 		msg;
	
	public String execute() throws Exception{
		JDBCDao j_dao = new JDBCDao();
		String sql ="select * from yffdoc_template where id=" + filed;
		String tpldata="";
		String desc = "";
		byte app_type = 0;
		ResultSet rs = j_dao.executeQuery(sql);
		while(rs.next()){
			desc = CommBase.CheckString(rs.getString("describe"));
			app_type = rs.getByte("app_type");
			byte tpl[] = rs.getBytes("tpldata");
			tpldata=new String(tpl);
			break;			
		}
		filed = "{describe:\"" + desc + "\",app_type:\"" + app_type + "\",tpldata:'" + tpldata + "'}";
		txtStream  = new ByteArrayInputStream(filed.getBytes());
		desc = desc.trim() +".txt";
		filename = new String(desc.getBytes(),"ISO8859_1");
		return SDDef.SUCCESS;
	}
	
	//读取上传文件内容
	public String getFileText() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		InputStream is = new FileInputStream(myFile);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuffer sbf = new StringBuffer();
		String line = "";
		while((line = reader.readLine()) != null){
			sbf.append(line);
		}
		is.close();
		String data = sbf.toString();
		JSONObject jsonObj = JSONObject.fromObject(data);
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		int id = 1;
		String getMaxId = "select max(id) id from yffdoc_template";
		
		rs = j_dao.executeQuery(getMaxId);
		while(rs.next()){
			id = rs.getInt("id");
		}

		String describe = jsonObj.getString("describe");
		String app_type = jsonObj.getString("app_type");
		String tpldata = jsonObj.getString("tpldata");
		
		String isOrder = "select count(id) num from yffdoc_template where describe = '" + describe + "'";
		rs = j_dao.executeQuery(isOrder);
		while(rs.next()){
			int num = rs.getInt("num");
			if(num > 0){
				msg = "此模板名称已经存在！";
				request.setAttribute("msg", msg);
				return SDDef.SUCCESS;
			}
		}
	
		String sql = "insert into yffdoc_template values(" + (++id) + ",'" + describe + "'," + app_type + ",'" + tpldata + "')";
		if(j_dao.executeUpdate(sql)){
			msg = "导入成功！";
		}
		else{
			msg = "导入失败！";
		}
		return SDDef.SUCCESS;
	}
	
	public String getFiled() {
		return filed;
	}

	public void setFiled(String filed) {
		this.filed = filed;
	}

	public String getFilename() throws IOException {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	public InputStream getTxtStream() {
		return txtStream;
	}
	public void setTxtStream(InputStream txtStream) {
		this.txtStream = txtStream;
	}
	public File getMyFile() {
		return myFile;
	}
	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
