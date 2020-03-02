package com.kesd.action.docs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.json.annotations.JSON;

import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.common.YDTable;
import com.kesd.dao.SqlPage;
import com.kesd.dbpara.HoLiDayDef;
import com.kesd.dbpara.HoLiDayGroup;
import com.kesd.dbpara.HoLigItem;
import com.libweb.common.CommBase;
import com.libweb.dao.HibDao;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;

public class ActHoLiDayGroup extends ActionSupport{
private static final long serialVersionUID = -601568323512959063L;
	
	private String   		result;
	private String   		pageNo;			//页号
	private String   		pageSize;  		//每页记录数
	private HoLiDayGroup 	holidaygroup;		//通道档案
	private HoLigItem      hoLigItem;
	private String   		field;			//所需查询的数据库字段
	
	private int count = 0;
	
	@SuppressWarnings("unchecked")
	public String addOrEdit()throws Exception {
		HibDao hib_dao = new HibDao();
		String hql = null;
		if(holidaygroup == null) {
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		if (hib_dao.saveOrUpdate(holidaygroup)) {
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
	public String delHoLiDayGroupById()
	{
		HibDao hib_dao = new HibDao();
		String id[]	   = result.split(SDDef.JSONSPLIT);
		Short[] ids = null;
		ids = new Short[id.length];

		for (int i = 0; i < id.length; i++) {
			if(id[i] == null)continue;
			ids[i] = Short.parseShort(id[i]);
		}
		
		if(hib_dao.delete(HoLiDayGroup.class, ids)) {
			result=SDDef.SUCCESS;
		}else {
			result=SDDef.FAIL;
		}
		
		return SDDef.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@JSON(serialize=false)
	public String getHoLiDayGroupById() throws Exception
	{
		if(result == null){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		StringBuffer ret_buf = new StringBuffer();
		HibDao 		 hib_dao = new HibDao();
		holidaygroup             = (HoLiDayGroup) hib_dao.loadById(HoLiDayGroup.class, Short.parseShort(result));
		
		String titles[] = field.split(SDDef.SPLITCOMA);
		ret_buf.append(SDDef.JSONSELDATA);
		for(int i = 0; i < titles.length; i ++) {
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(CommFunc.getMethodValue(titles[i], HoLiDayGroup.class,holidaygroup)) + SDDef.JSONQUOT  + SDDef.JSONCOMA);
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
		
		countSql.append("select count(id) from holidaygroup");
		sql_page.setTotalrecords(countSql.toString());
		count = sql_page.getTotalrecords();
		if(pagesize == 0){
			 sql="select top " + count + " * from holidaygroup ";
		}else{
			if(pagesize > count){
				sql = "select top "+pagesize+" * from holidaygroup ";
			}else{
				sql = "select top "+pagesize+" * from holidaygroup ";
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
			ret_buf.append(SDDef.JSONQUOT + "<a href='javascript:redoOnRowDblClicked(" + map.get("id") + ")'>" + CommBase.CheckString(map.get("describe"))                   + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("grouptype"))                   + SDDef.JSONCCM);
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);
		}
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRBRBR);
		result = ret_buf.toString();
		return SDDef.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String loadItem(){
		Map<Short, Short> itemMap = new HashMap<Short, Short>();
		HibDao hib_dao = new HibDao();
		String hql = "";
		hql = "select c.groupId,c.holidayId from "+ YDTable.TABLECLASS_HOLIDAYDEF + " as a,"+ YDTable.TABLECLASS_HOLIDAYGROUP + " as b," + YDTable.TABLECLASS_HOLIGITEM +" as c where a.id = c.holidayId and b.id = groupId and b.id = " + result;
		List list = hib_dao.loadAll(hql);
		Iterator it = list.iterator();
		while(it.hasNext()){
			Object[] obj = (Object[])it.next();
			itemMap.put(Short.parseShort(obj[1].toString()), Short.parseShort(obj[0].toString()));
		}
		
		String hqldef = "from "+YDTable.TABLECLASS_HOLIDAYDEF;
		String hqlgroup = "from "+YDTable.TABLECLASS_HOLIDAYGROUP + " where id = " + result;
		HoLiDayGroup holidaygroup = (HoLiDayGroup) hib_dao.loadById(HoLiDayGroup.class, Short.parseShort(result)); 
		
		List listdef = hib_dao.loadAll(hqldef);
//		List listgroup = hib_dao.loadAll(hqlgroup);
		
		Iterator itdef = listdef.iterator();
		
		
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		JSONArray  j_array1 = new JSONArray();
		JSONArray  j_array2 = new JSONArray();
		int index =1;
		while(itdef.hasNext()){
			HoLiDayDef holidaydef = (HoLiDayDef)itdef.next();
			json1.put("id", holidaygroup.getId() +"_"+holidaydef.getId());		//id
			
			if(itemMap.containsKey(holidaydef.getId())){
				j_array1.add(1);
			}else{
				j_array1.add(0);
			}
			j_array1.add(index++);												//序号
			j_array1.add(holidaydef.getDescribe());								//节假日描述
			j_array1.add(holidaygroup.getDescribe());							//节假日组描述
			
			json1.put("data", j_array1);
			j_array2.add(json1);
			json1.clear();
			j_array1.clear();
		}
		json2.put("rows", j_array2);
		result = json2.toString();
		return SDDef.SUCCESS;
	}
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : plAddOrEdit
	* <p>
	* <p>DESCRIPTION : 批量添加或修改费控参数记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	
	public String plEdit(){
		HibDao hib_dao = new HibDao();
		JDBCDao jdbcDao = new JDBCDao();
		
//		Short groupid = Short.parseShort(item_ids[0].split("_")[0]); 
		String sql = "delete holigitem where groupid = " + result;
		if(jdbcDao.executeUpdate(sql)) {
			result=SDDef.SUCCESS;
		}else {
			result=SDDef.FAIL;
		}
		if(field == null || field.isEmpty()){
			result = SDDef.SUCCESS;
			return SDDef.SUCCESS;
		}
		String item_ids[]	   = field.split(SDDef.SPLITCOMA);
		boolean flag = true;
		for (int i = 0; i < item_ids.length; i++) {
			
			Short groupId 	= Short.parseShort(item_ids[i].split("_")[0]);
			Short holidayId	= Short.parseShort(item_ids[i].split("_")[1]);
			sql = "insert into holigitem(groupid,holidayid) values(" + groupId + "," + holidayId + ")";
			
			if (!jdbcDao.executeUpdate(sql)) {
				flag = false;
				break;
			}
		}
		
		if(flag){
			result = SDDef.SUCCESS;
		}else{
			result = SDDef.FAIL;
		}
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
	public HoLiDayGroup getHolidaygroup() {
		return holidaygroup;
	}
	public void setHolidaygroup(HoLiDayGroup holidaygroup) {
		this.holidaygroup = holidaygroup;
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
	public HoLigItem getHoLigItem() {
		return hoLigItem;
	}
	public void setHoLigItem(HoLigItem hoLigItem) {
		this.hoLigItem = hoLigItem;
	}

}
