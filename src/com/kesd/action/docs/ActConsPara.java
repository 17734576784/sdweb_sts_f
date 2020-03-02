package com.kesd.action.docs;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.json.annotations.JSON;

import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.SDOperlog;
import com.kesd.common.YDTable;
import com.kesd.dao.HibPage;
import com.kesd.dbpara.ConsPara;
import com.kesd.dbpara.YffManDef;
import com.kesd.service.DBOper;
import com.kesd.util.Rd;
import com.libweb.dao.HibDao;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;

public class ActConsPara extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5412152413195831360L;
	private String 		result;
	private String 		pageNo;		//页号
	private String 		pageSize;	//每页记录数
	private ConsPara 	conspara;   //居民区档案
	private String		field;		//所需查询的数据库字段
	
	//添加或修改居民区档案记录
	public String addOrEdit()throws Exception
	{
		HibDao hib_dao = new HibDao();
		boolean addflag = false;
		if(conspara.getId() == null){
			addflag = true;
			conspara.setTelNo3(CommFunc.objectToString(getUtilityId()));
		}
		if (hib_dao.saveOrUpdate(conspara)) {
			if(addflag){
				SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_ADD, "Add Residential Area Archive["+ conspara.getId() +"]");
			}else{
				SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_UPDATE, "Modify Residential Area Archive["+ conspara.getId() +"]");
			}
			result = SDDef.SUCCESS;
		}
		else {
			result = SDDef.FAIL;
		}
		
		return SDDef.SUCCESS;
	}
	
	//UtilityId 库中最大值加1
	private int getUtilityId(){
		int utilityId = 1;
		JDBCDao jdbc = new JDBCDao();
		String sql = "select max(tel_no3) id from conspara";
		List<Map<String, Object>> list = jdbc.result(sql);
		if(list.size() == 0){
			return utilityId;
		}else{
			utilityId = CommFunc.objectToInt(list.get(0).get("id")) + 1;
		}
		return utilityId;
	}
	
	//通过ID删除居民区档案记录
	public String delConsParaById()
	{
		HibDao  hib_dao = new HibDao();
		String  id[]    = result.split(SDDef.JSONSPLIT);
		
		byte   flag	   	   = 1;
		String currentCons = null;
		
		for(int i = 0; i < id.length; i++) {
			
			if (!hib_dao.loadAll("from " + YDTable.TABLECLASS_RTUPARA + " as a where a.consId = " + id[i]).isEmpty()) {
				flag=2;
				currentCons = DBOper.getDescribeById("ydparaben.dbo.conspara", Short.parseShort(id[i]));
				break;//有终端档案返回提示信息
			}
			else {
				if (!hib_dao.delete(ConsPara.class, Short.parseShort(id[i]))) {
					flag=0;
					break;
				}else{
					SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_DELETE, "Delete Residential Area Archive["+ id[i] +"]");

				}
			}
		}
		
		if (flag == 1) {	
			

			result = SDDef.SUCCESS;
		}
		else if (flag == 0) {
			result = SDDef.FAIL;
		}
		else if (flag == 2) {
			result = "Community【" + currentCons + "】 have terminal files please delete them first！";
		}
		
		return SDDef.SUCCESS;
	}
	
	//通过ID获取居民区档案记录
	@JSON(serialize = false)
	public String getConsParaById() throws Exception
	{
		if(result == null) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		StringBuffer ret_buf  = new StringBuffer();
		HibDao       hib_dao  = new HibDao();
		conspara      		  = (ConsPara)hib_dao.loadById(ConsPara.class, Short.parseShort(result));
		
		String titles[] = field.split(SDDef.SPLITCOMA);
		ret_buf.append(SDDef.JSONSELDATA);
		for(int i = 0; i < titles.length; i ++) {
			ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(CommFunc.getMethodValue(titles[i], ConsPara.class,conspara)) + SDDef.JSONQUOT  + SDDef.JSONCOMA);
		}
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();
		
		return SDDef.SUCCESS;
	}
	
	//分页获取居民区档案记录
	@SuppressWarnings("unchecked")
 	public String execute() {
		List 			list		= null;
		StringBuffer 	ret_buf		= new StringBuffer();
		int 			page		= Integer.parseInt(pageNo);
		int  			pagesize	= Integer.parseInt(pageSize);

		YffManDef nowUser = CommFunc.getYffMan();
		
		String describe   = null;
		String order = " order by a.substId,a.lineId,a.id";
		
		if(result != null && !result.isEmpty()){
			JSONObject jsonObj  = JSONObject.fromObject(result);
			describe   = jsonObj.getString("describe");
		}
		
		HibPage hib_page = new HibPage(page, pagesize);
		String hql = "";
		//判断是一个权限还是全局
		if(nowUser.getRank() == 0){
			hql		 = "select count(id) from " + YDTable.TABLECLASS_CONSPARA 
			+ " as a where a.appType="+SDDef.APPTYPE_JC;
			if(result != null && !result.isEmpty()){
				if(!describe.isEmpty()){
					hql += " and a.describe like '%" + describe + "%'";
				}
			}
			
			if (nowUser.getRank() != 0) {
				hql += " and a.orgId =" + nowUser.getOrgId();
			}
			
			hib_page.setTotalrecords(hql);
			if(pagesize==0){
				pagesize = hib_page.getTotalrecords();
				hib_page.setPagesize(pagesize);
			}
			if(hib_page.getTotalrecords()== 0) {
				result = SDDef.EMPTY;
				return SDDef.SUCCESS;
			}
			
			hql="from " + YDTable.TABLECLASS_CONSPARA + " as a where a.appType="+SDDef.APPTYPE_JC;
			if(result != null && !result.isEmpty()){
				if(!describe.isEmpty()){
					hql += " and a.describe like '%" + describe + "%'";
				}
			}
			
			if (nowUser.getRank() != 0) {
				hql += " and a.orgId =" + nowUser.getOrgId();
			}
			
			hql += order;			
		}else{
			hql		 = "select count(a.id) from " + YDTable.TABLECLASS_CONSPARA 
			+ " as a,"+ YDTable.TABLECLASS_Userrankbound +" as u where a.appType="+SDDef.APPTYPE_JC;
			if(result != null && !result.isEmpty()){
				if(!describe.isEmpty()){
					hql += " and a.describe like '%" + describe + "%'";
				}
			}
			
			if (nowUser.getRank() != 0) {
				hql += " and a.orgId =" + nowUser.getOrgId();
			}
			hql += " and u.userId = " + nowUser.getId();
				
			hql += " and u.consId = a.id ";
			
			hib_page.setTotalrecords(hql);
			if(pagesize==0){
				pagesize = hib_page.getTotalrecords();
				hib_page.setPagesize(pagesize);
			}
			if(hib_page.getTotalrecords()== 0) {
				result = SDDef.EMPTY;
				return SDDef.SUCCESS;
			}
			
			hql="from " + YDTable.TABLECLASS_CONSPARA + " as a,"+ YDTable.TABLECLASS_Userrankbound +" as u where a.appType="+SDDef.APPTYPE_JC;
			if(result != null && !result.isEmpty()){
				if(!describe.isEmpty()){
					hql += " and a.describe like '%" + describe + "%'";
				}
			}
			
			if (nowUser.getRank() != 0) {
				hql += " and a.orgId =" + nowUser.getOrgId();
			}
			
			hql += " and u.userId = " + nowUser.getId();
			
			hql += " and u.consId = a.id ";				
			
			hql += order;		
		}
		list = hib_page.getRecord(hql);
		ret_buf.append(SDDef.JSONTOTAL + hib_page.getTotalrecords() + SDDef.JSONPAGEROWS);
		int no = pagesize*(hib_page.getCurrentpage()-1)+1;
		Iterator it=list.iterator();
		
		Map<Integer,String> map_powertype = Rd.getDict(Dict.DICTITEM_POWERTYPE);    
		Map<Integer,String> map_vgrade = Rd.getDict(Dict.DICTITEM_VOLTGRADE);
		
		while(it.hasNext()){
			ConsPara conspara = null;
			if(nowUser.getRank() != 0){
				Object obj[] = (Object[])it.next();
				conspara	=	(ConsPara)obj[0];				
			}else{
				conspara	=	(ConsPara)it.next();
			}

			ret_buf.append(SDDef.JSONLBRACES); 
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + conspara.getId() + SDDef.JSONDATA);
			
			ret_buf.append(SDDef.JSONQUOT + (no++)			                                                            	 + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + "<a href='javascript:openModalDialog("+ conspara.getId() +");'>" + CommFunc.CheckString(conspara.getDescribe()) + "</a>" + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(conspara.getFzMan())          	        			      	 + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(conspara.getTelNo1())           			    			 + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(conspara.getTelNo2())           			                 + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + getDictByKey(map_powertype, conspara.getPowerType())   + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + getDictByKey(map_vgrade, conspara.getVoltGrade())   + SDDef.JSONCCM);
			
			ret_buf.append(SDDef.JSONQUOT + DBOper.getDescribeById("ydparaben.dbo.orgpara", conspara.getOrgId()) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + DBOper.getDescribeById("ydparaben.dbo.line_fzman", conspara.getLineFzManId()) + SDDef.JSONCCM);
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRBRBR);
		result = ret_buf.toString();
		
		return SDDef.SUCCESS;
	}
	
	private String getDictByKey(Map<Integer,String> map,Object key){
		if(key == null)return "";
		int key1 = Integer.parseInt(key.toString());
		String value = map.get(key1);
		if(value == null)return "";
		
		return value;
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

	public ConsPara getConspara() {
		return conspara;
	}

	public void setConspara(ConsPara conspara) {
		this.conspara = conspara;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
