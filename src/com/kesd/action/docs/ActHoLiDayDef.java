package com.kesd.action.docs;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;


import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.common.YDTable;
import com.kesd.dao.HibPage;
import com.kesd.dao.SqlPage;
import com.kesd.dbpara.HoLiDayDef;
import com.kesd.dbpara.YffManDef;
import com.libweb.common.CommBase;
import com.libweb.dao.HibDao;
import com.opensymphony.xwork2.ActionSupport;

public class ActHoLiDayDef extends ActionSupport{
	private static final long serialVersionUID = -601568323512959063L;
	
	private String   	result;
	private String   	pageNo;			//页号
	private String   	pageSize;  		//每页记录数
	private HoLiDayDef 	holidaydef;		//通道档案
	private String   	field;			//所需查询的数据库字段
	
	private int count = 0;
	
	@SuppressWarnings("unchecked")
	public String addOrEdit()throws Exception {
		HibDao hib_dao = new HibDao();
		String hql = null;
		if(holidaydef == null) {
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		if (hib_dao.saveOrUpdate(holidaydef)) {
			result = SDDef.SUCCESS;
		}
		else {
			result = SDDef.FAIL;
		}
		return SDDef.SUCCESS;
	}
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : delYffManDefById
	* <p>
	* <p>DESCRIPTION : 通过ID删除节假日
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public String delHoLiDayDefById()
	{
		HibDao hib_dao = new HibDao();
		String id[]	   = result.split(SDDef.JSONSPLIT);
		Short[] ids = null;
		ids = new Short[id.length];

		for (int i = 0; i < id.length; i++) {
			if(id[i] == null)continue;
			ids[i] = Short.parseShort(id[i]);
		}
		
		if(hib_dao.delete(HoLiDayDef.class, ids)) {
			result=SDDef.SUCCESS;
		}else {
			result=SDDef.FAIL;
		}
		
		return SDDef.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@JSON(serialize=false)
	public String getHoLiDayDefById() throws Exception
	{
		if(result == null){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		StringBuffer ret_buf = new StringBuffer();
		HibDao 		 hib_dao = new HibDao();
		holidaydef             = (HoLiDayDef) hib_dao.loadById(HoLiDayDef.class, Short.parseShort(result));
		
		String titles[] = field.split(SDDef.SPLITCOMA);
		ret_buf.append(SDDef.JSONSELDATA);
		for(int i = 0; i < titles.length; i ++) {
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(CommFunc.getMethodValue(titles[i], HoLiDayDef.class,holidaydef)) + SDDef.JSONQUOT  + SDDef.JSONCOMA);
		}
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();
		
		return SDDef.SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String execute(){
		List 			list 	 = null;
		StringBuffer 	ret_buf  = new StringBuffer();
		int 			page	 = Integer.parseInt(pageNo == null  ? "1" : pageNo);
		int 			pagesize = Integer.parseInt(pageSize == null? "20" : pageSize);
		
		SqlPage sql_page = new SqlPage(page, pagesize);
		StringBuffer countSql=new StringBuffer();
		String sql = "";
		
		countSql.append("select count(id) from holidaydef");
		sql_page.setTotalrecords(countSql.toString());
		count = sql_page.getTotalrecords();
		if(pagesize == 0){
			 sql="select top " + count + " * from holidaydef ";
		}else{
			if(pagesize > count){
				sql = "select top "+pagesize+" * from holidaydef ";
			}else{
				sql = "select top "+pagesize+" * from holidaydef ";
			}
		}
		if(sql_page.getTotalrecords()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		if (pagesize == 0) {
			pagesize = sql_page.getTotalrecords();
			sql_page.setPagesize(pagesize);
		}
		list = sql_page.getRecord(sql);
		int no = pagesize * (sql_page.getCurrentpage() - 1) + 1;
		ret_buf.append(SDDef.JSONTOTAL + sql_page.getTotalrecords() + SDDef.JSONPAGEROWS);
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map =  (Map<String, Object>) list.get(i);
			ret_buf.append(SDDef.JSONLBRACES); 
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + map.get("id") + SDDef.JSONDATA);
			
			ret_buf.append(SDDef.JSONQUOT + (no++)                                                  + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("describe"))                   + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("sdate"))                   + SDDef.JSONCCM);
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);
		}
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRBRBR);
		result = ret_buf.toString();
		return SDDef.SUCCESS;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public HoLiDayDef getHolidaydef() {
		return holidaydef;
	}
	public void setHolidaydef(HoLiDayDef holidaydef) {
		this.holidaydef = holidaydef;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

}
