package com.kesd.action.docs;

/********************************************************************************************************
*                                        用电WEB Ver2.0													*
*																										*
*                           (c) Copyright 2010~,   KLD Automation Co., Ltd.								*
*                                           All Rights Reserved											*
*																										*
*	FileName	:	ActYffRatePara															    		*
*	Description	:	费率增加、修改、删除、查询														*
*	Author		:																						*
*	Date		:	2011/01/05																			*
*																										*
*   No.         Date         Modifier        Description												*
*   -----------------------------------------------------------------------------------------------     *
*********************************************************************************************************/

import java.util.Iterator;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.libweb.common.CommBase;
import com.libweb.dao.HibDao;
import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.SDOperlog;
import com.kesd.common.YDTable;
import com.kesd.dao.HibPage;
import com.kesd.dbpara.YffRatePara;
import com.kesd.util.Rd;
import com.opensymphony.xwork2.ActionSupport;


/** ******************* CLASS DESCRIPTION *******************
* <p>
* <p>NAME        : ActConsPara
* <p>
* <p>DESCRIPTION : 费率增加、修改、删除、查询 
* <p>
* <p>  No.         Date         Modifier        Description	
* <p>---------------------------------------------------------
**************************************************************/
public class ActYffRatePara extends ActionSupport
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -601568323512959063L;
	private String   		result;
	private String   		pageNo;			//页号
	private String   		pageSize;  		//每页记录数
	private YffRatePara 	yffratepara;		//通道档案
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
		boolean add = false;
		if(yffratepara.getId() == null){
			add = true;
		}
		
		if (hib_dao.saveOrUpdate(yffratepara)) {
			if(add){
				SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_ADD, "Add Tariff Project["+ yffratepara.getId() +"]");
			}
			else{
				SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_UPDATE, "Modify Tariff Project["+ yffratepara.getId() +"]");
			}
			result = SDDef.SUCCESS;
		}
		else {
			result = SDDef.FAIL;
		}
		return SDDef.SUCCESS;
	}
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : delYffRateParaById
	* <p>
	* <p>DESCRIPTION : 通过ID删除预付费记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	public String delYffRateParaById()
	{
		HibDao hib_dao = new HibDao();
		String id[]	   = result.split(SDDef.JSONSPLIT);
		Short[] ids = new Short[id.length];
		for(int i=0;i<id.length;i++) {
			ids[i] = Short.parseShort(id[i]);
		}
		if(hib_dao.delete(YffRatePara.class, ids)) {
			for(int i= 0; i < ids.length; i++){
				SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_DELETE, "Delete Tariff Project["+ ids[i] +"]");

			}
			result=SDDef.SUCCESS;
		}
		else {
			result=SDDef.FAIL;
		}
		
		return SDDef.SUCCESS;
	}
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : getYffRateParaById
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
	public String getYffRateParaById() throws Exception
	{
		if(result == null){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		StringBuffer ret_buf = new StringBuffer();
		HibDao 		 hib_dao = new HibDao();
		yffratepara             = (YffRatePara) hib_dao.loadById(YffRatePara.class, Short.parseShort(result));
		
		String titles[] = field.split(SDDef.SPLITCOMA);
		ret_buf.append(SDDef.JSONSELDATA);
		for(int i = 0; i < titles.length; i ++) {
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(CommFunc.getMethodValue(titles[i], YffRatePara.class,yffratepara)) + SDDef.JSONQUOT  + SDDef.JSONCOMA);
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
		String  hql		 = "select count(id) from " + YDTable.TABLECLASS_YFFRATEPARA + " as a";
		if(result != null && !result.isEmpty()){
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
		hql = "from " + YDTable.TABLECLASS_YFFRATEPARA + " as a";
		if(result != null){
			hql += " where a.describe like '%" + result + "%'";
		}
		
		list = hib_page.getRecord(hql);
		ret_buf.append(SDDef.JSONTOTAL + hib_page.getTotalrecords() + SDDef.JSONPAGEROWS);

		Iterator it=list.iterator();
		
		int no = pagesize * (hib_page.getCurrentpage() - 1) + 1;
		
		while(it.hasNext()){
			YffRatePara yffratepara=(YffRatePara) it.next();
			
			ret_buf.append(SDDef.JSONLBRACES); 
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + yffratepara.getId() + SDDef.JSONDATA);
			
			ret_buf.append(SDDef.JSONQUOT + (no++)                                                            + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + "[" + CommFunc.FormatAdd0(yffratepara.getId(), 2) + "]" + CommBase.CheckString(yffratepara.getDescribe())                                + SDDef.JSONCCM);
			
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRateId())                  	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(CommFunc.FormatToYMD(yffratepara.getActivdate()))                  	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_FEELVTYPE, yffratepara.getFeeType())	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getMoneyLimit())              	+ SDDef.JSONCCM);
			
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatedZ())                  	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatefF())                  	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatefP())                  	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatefG())                   + SDDef.JSONCCM);
			
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRateh1())                	+ SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRateh2())                   + SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRateh3())               	+ SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRateh4())                	+ SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatehBl1())              	+ SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatehBl2())               	+ SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatehBl3())               	+ SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatehBl4())              	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_YFF_RATEJ_TYPE, yffratepara.getRatejType())	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatejNum())                	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_METER_FEE_TYPE, yffratepara.getMeterfeeType())	+ SDDef.JSONCCM);			
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getMeterfeeR())             	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatejR1())                	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatejR2())               	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatejR3())                 	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatejR4())                	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatejR5())                	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatejR6())                	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatejR7())                	+ SDDef.JSONCCM);
		
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatejTd1())            		+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatejTd2())             	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatejTd3())     			+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatejTd4())            		+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatejTd5())             	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatejTd6())     			+ SDDef.JSONCCM);
		
			
//			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_YFF_RATEJ_TYPE, yffratepara.getRatehjType())	+ SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatehjNum())               	+ SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_METER_FEE_TYPE, yffratepara.getMeterfeeType())	+ SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getMeterfeehjR())             	+ SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatehjHr1R1())             	+ SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatehjHr1R2())            	+ SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatehjHr1R3())             	+ SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatehjHr1R4())             	+ SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatehjHr1Td1())            	+ SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatehjHr1Td2())             + SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatehjHr1Td3())          	+ SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatehjHr2())               	+ SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatehjHr3())                + SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatehjBl1())              	+ SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatehjBl2())                + SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatehjBl3())          		+ SDDef.JSONCCM);

//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getRatejTd4())                                 + SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(yffratepara.getRatejJsdate1())                             + SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(yffratepara.getRatejJsdate2())                             + SDDef.JSONCCM);

			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getInfCode1())          		+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(yffratepara.getInfCode2())          		+ SDDef.JSONCCM);
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
	
	public YffRatePara getYffratepara() {
		return yffratepara;
	}

	public void setYffratepara(YffRatePara yffratepara) {
		this.yffratepara = yffratepara;
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

	public YffRatePara getYffmandef() {
		return yffratepara;
	}

	public void setYffmandef(YffRatePara yffratepara) {
		this.yffratepara = yffratepara;
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