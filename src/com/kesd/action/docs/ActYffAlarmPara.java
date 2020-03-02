package com.kesd.action.docs;

/********************************************************************************************************
*                                        用电WEB Ver2.0													*
*																										*
*                           (c) Copyright 2010~,   KLD Automation Co., Ltd.								*
*                                           All Rights Reserved											*
*																										*
*	FileName	:	ActYffAlarmPara															    		*
*	Description	:	预付费报警方案增加、修改、删除、查询														*
*	Author		:																						*
*	Date		:	2011/01/05																			*
*																										*
*   No.         Date         Modifier        Description												*
*   -----------------------------------------------------------------------------------------------     *
*********************************************************************************************************/

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.YDTable;
import com.kesd.dao.HibPage;
import com.kesd.dbpara.YffAlarmPara;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
import com.libweb.dao.HibDao;
import com.opensymphony.xwork2.ActionSupport;


/** ******************* CLASS DESCRIPTION *******************
* <p>
* <p>NAME        : ActYffAlarmPara
* <p>
* <p>DESCRIPTION : 预付费报警方案增加、修改、删除、查询 
* <p>
* <p>  No.         Date         Modifier        Description	
* <p>---------------------------------------------------------
**************************************************************/
public class ActYffAlarmPara extends ActionSupport
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -601568323512959063L;
	private String   		result;
	private String   		pageNo;			//页号
	private String   		pageSize;  		//每页记录数
	private YffAlarmPara 	yffalarmpara;	//报警档案
	private String   		field;			//所需查询的数据库字段
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : addOrEdit
	* <p>
	* <p>DESCRIPTION : 添加或修改费率记录
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
		
		if (hib_dao.saveOrUpdate(yffalarmpara)) {
			result = SDDef.SUCCESS;
		}
		else {
			result = SDDef.FAIL;
		}
		return SDDef.SUCCESS;
	}
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : delYffAlarmParaById
	* <p>
	* <p>DESCRIPTION : 通过ID删除预付费记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	public String delYffAlarmParaById()
	{
		HibDao hib_dao = new HibDao();
		String id[]	   = result.split(SDDef.JSONSPLIT);
		Short[] ids = new Short[id.length];
		for(int i=0;i<id.length;i++) {
			ids[i] = Short.parseShort(id[i]);
		}
		if(hib_dao.delete(YffAlarmPara.class, ids)) {
			result=SDDef.SUCCESS;
		}
		else {
			result=SDDef.FAIL;
		}
		
		return SDDef.SUCCESS;
	}
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : getYffAlarmParaById
	* <p>
	* <p>DESCRIPTION : 通过ID获取预付费人员记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	
	@JSON(serialize=false)
	public String getYffAlarmParaById() throws Exception
	{
		if(result == null){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		StringBuffer ret_buf = new StringBuffer();
		HibDao 		 hib_dao = new HibDao();
		yffalarmpara             = (YffAlarmPara) hib_dao.loadById(YffAlarmPara.class, Short.parseShort(result));
		
		String titles[] = field.split(SDDef.SPLITCOMA);
		ret_buf.append(SDDef.JSONSELDATA);
		for(int i = 0; i < titles.length; i ++) {
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(CommFunc.getMethodValue(titles[i], YffAlarmPara.class,yffalarmpara)) + SDDef.JSONQUOT  + SDDef.JSONCOMA);
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
	* <p>DESCRIPTION : 分页获取预付费人员记录
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
		String  hql		 = "select count(id) from " + YDTable.TABLECLASS_YFFALARMPARA + " as a";
		if(result != null){
			result = result.replace("'", "").replace(" ", "");
			hql   += " where a.describe like '%" + result + "%'";
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
		hql = "from " + YDTable.TABLECLASS_YFFALARMPARA + " as a";
		if(result != null){
			hql += " where a.describe like '%" + result + "%'";
		}
		
		list = hib_page.getRecord(hql);
		ret_buf.append(SDDef.JSONTOTAL + hib_page.getTotalrecords() + SDDef.JSONPAGEROWS);
				
		Iterator it=list.iterator();
		
		int no = pagesize * (hib_page.getCurrentpage() - 1) + 1;
		
		Map<Integer,String> yesflag = Rd.getDict(Dict.DICTITEM_YESFLAG);
		
		while(it.hasNext()){
			YffAlarmPara yffalarmpara=(YffAlarmPara) it.next();
			
			ret_buf.append(SDDef.JSONLBRACES); 
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + yffalarmpara.getId() + SDDef.JSONDATA);
			ret_buf.append(SDDef.JSONQUOT + (no++)                                          + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffalarmpara.getDescribe())+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_ALARMTYPE, yffalarmpara.getType())  + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffalarmpara.getAlarm1())  + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffalarmpara.getAlarm2())  + SDDef.JSONCCM);
			
			Object tmp = yffalarmpara.getPayalmFlag();
			ret_buf.append(SDDef.JSONQUOT + (tmp == null?"":yesflag.get(Integer.parseInt(tmp.toString()))) + SDDef.JSONCCM);
			tmp = yffalarmpara.getHzalmFlag();
			ret_buf.append(SDDef.JSONQUOT + (tmp == null?"":yesflag.get(Integer.parseInt(tmp.toString()))) + SDDef.JSONCCM);
			tmp = yffalarmpara.getTzalmFlag();
			ret_buf.append(SDDef.JSONQUOT + (tmp == null?"":yesflag.get(Integer.parseInt(tmp.toString()))) + SDDef.JSONCCM);
			tmp = yffalarmpara.getDxalmFlag();
			ret_buf.append(SDDef.JSONQUOT + (tmp == null?"":yesflag.get(Integer.parseInt(tmp.toString()))) + SDDef.JSONCCM);
			tmp = yffalarmpara.getSyalmFlag();
			ret_buf.append(SDDef.JSONQUOT + (tmp == null?"":yesflag.get(Integer.parseInt(tmp.toString()))) + SDDef.JSONCCM);
			tmp = yffalarmpara.getDxalmcgkFlag();
			ret_buf.append(SDDef.JSONQUOT + (tmp == null?"":yesflag.get(Integer.parseInt(tmp.toString()))) + SDDef.JSONCCM);
			tmp = yffalarmpara.getSyalmcgkFlag();
			ret_buf.append(SDDef.JSONQUOT + (tmp == null?"":yesflag.get(Integer.parseInt(tmp.toString()))) + SDDef.JSONCCM);
			
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
	
	public YffAlarmPara getYffratepara() {
		return yffalarmpara;
	}

	public void setYffratepara(YffAlarmPara yffalarmpara) {
		this.yffalarmpara = yffalarmpara;
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

	public YffAlarmPara getYffmandef() {
		return yffalarmpara;
	}

	public void setYffmandef(YffAlarmPara yffalarmpara) {
		this.yffalarmpara = yffalarmpara;
	}

	public YffAlarmPara getYffalarmpara() {
		return yffalarmpara;
	}

	public void setYffalarmpara(YffAlarmPara yffalarmpara) {
		this.yffalarmpara = yffalarmpara;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
}