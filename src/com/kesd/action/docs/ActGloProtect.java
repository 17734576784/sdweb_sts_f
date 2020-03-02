/********************************************************************************************************
*                                        用电WEB Ver2.0													*
*																										*
*                           (c) Copyright 2010~,   KLD Automation Co., Ltd.								*
*                                           All Rights Reserved											*
*																										*
*	FileName	:	ActInstallMan.java																    *
*	Description	:	安装人员档案增加、修改、删除、查询															*
*	Author		:																						*
*	Date		:	2010/12/29																			*
*																										*
*   No.         Date         Modifier        Description												*
*   -----------------------------------------------------------------------------------------------     *
*********************************************************************************************************/
package com.kesd.action.docs;

import java.util.Iterator;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.YDTable;
import com.kesd.dao.HibPage;
import com.kesd.dbpara.GloProtect;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
import com.libweb.dao.HibDao;
import com.opensymphony.xwork2.ActionSupport;

public class ActGloProtect extends ActionSupport
{
	
	private static final long serialVersionUID = -7095172778548119531L;
	
	private String   	result;
	private String   	pageNo;			//页号
	private String   	pageSize;  		//每页记录数
	private GloProtect  gloprotect;

	private String   	field;			//所需查询的数据库字段
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : addOrEdit
	* <p>
	* <p>DESCRIPTION : 添加或修改节假日保电档案记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	
	public String addOrEdit()throws Exception
	{
		HibDao hib_dao = new HibDao();
		if (hib_dao.saveOrUpdate(gloprotect)) {
			result = SDDef.SUCCESS;
		}
		else {
			result = SDDef.FAIL;
		}
		return SDDef.SUCCESS;
	}
	

	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : getGloProtectById
	* <p>
	* <p>DESCRIPTION :  
	* <p>
	* <p>COMPLETION
	* <p>INPUT       :  
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@JSON(serialize=false)
	public String getGloProtectById() throws Exception
	{
		if(result==null){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		StringBuffer ret_buf = new StringBuffer();
		HibDao 		 hib_dao = new HibDao();
		
		gloprotect            = (GloProtect) hib_dao.loadById(GloProtect.class, Short.parseShort(result)); 
		
		String fields[] = field.split(SDDef.SPLITCOMA);
		
		ret_buf.append(SDDef.JSONSELDATA);
		
		for(int i = 0; i < fields.length; i ++) {
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(CommFunc.getMethodValue(fields[i], GloProtect.class, gloprotect)) + SDDef.JSONQUOT  + SDDef.JSONCOMA);
		}
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();
		
		return SDDef.SUCCESS;
	}
		
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : execute
	* <p>
	* <p>DESCRIPTION : 获取力调电费参数表记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/		
	@SuppressWarnings("unchecked")
	public String execute() throws Exception
	{
		List 			list 	 = null;
		StringBuffer 	ret_buf  = new StringBuffer();
		int 			page	 = Integer.parseInt(pageNo == null? "1" : pageNo);
		int 			pagesize = Integer.parseInt(pageSize == null? "10" : pageSize);
		HibPage hib_page = new HibPage(page,pagesize);
		String  hql		 = "select count(id) from " + YDTable.TABLECLASS_GLOPROTECT + " as a";
		if(result != null&&!result.isEmpty()) {
			result = result.replace("'", "").replace(" ", "");
		}
		hib_page.setTotalrecords(hql);
		if(hib_page.getTotalrecords()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		if (pagesize == 0) {
			pagesize = hib_page.getTotalrecords();
			hib_page.setPagesize(pagesize);
		}
			
		hql = "from " + YDTable.TABLECLASS_GLOPROTECT + " as a";

		list = hib_page.getRecord(hql);
		ret_buf.append(SDDef.JSONTOTAL + hib_page.getTotalrecords() + SDDef.JSONPAGEROWS);
		int no = pagesize*(hib_page.getCurrentpage()-1)+1;
		Iterator it=list.iterator();
		
		while(it.hasNext()){
			GloProtect glopro=(GloProtect) it.next();
			
			ret_buf.append(SDDef.JSONLBRACES); 
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + glopro.getId() + SDDef.JSONDATA);
			
			ret_buf.append(SDDef.JSONQUOT + (no++)                                              + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_YFFAPPTYPE, glopro.getAppType())	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_USEFLAG, glopro.getUseFlag())  + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(glopro.getBgDate())            + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToHMS(glopro.getBgTime(),2)          + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(glopro.getEdDate())            + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToHMS(glopro.getEdTime(),2)          + SDDef.JSONCCM);
			
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


	public GloProtect getGloprotect() {
		return gloprotect;
	}


	public void setGloprotect(GloProtect gloprotect) {
		this.gloprotect = gloprotect;
	}


	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
}
