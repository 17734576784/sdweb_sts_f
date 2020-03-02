/********************************************************************************************************
*                                        用电WEB Ver2.0													*
*																										*
*                           (c) Copyright 2010~,   KLD Automation Co., Ltd.								*
*                                           All Rights Reserved											*
*																										*
*	FileName	:	ActRtuPara.java																		*
*	Description	:	终端档案 业务处理类																	*
*	Author		:																						*
*	Date		:	2010/12/29																			*
*																										*
*   No.         Date         Modifier        Description												*
*   -----------------------------------------------------------------------------------------------     *
*********************************************************************************************************/
package com.kesd.action.docs;
import java.util.Iterator;
import java.util.List;
import net.sf.json.JSONObject;
import org.apache.struts2.json.annotations.JSON;

import com.libweb.common.CommBase;
import com.libweb.dao.HibDao; 
import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.SDOperlog;
import com.kesd.common.YDTable;
import com.kesd.dao.HibPage;
import com.kesd.dbpara.RtuPara;
import com.kesd.dbpara.RtuPayCtrl;
import com.kesd.util.Rd;
import com.opensymphony.xwork2.ActionSupport;

/** ******************* CLASS DESCRIPTION *******************
* <p>
* <p>NAME        : ActRtuPara
* <p>
* <p>DESCRIPTION : 终端档案 业务处理类 
* <p>
* <p>  No.         Date         Modifier        Description	
* <p>---------------------------------------------------------
**************************************************************/
public class ActRtuPayCtrlPara extends ActionSupport
{
	private static final long serialVersionUID = -4855371030068681477L;
	private String   		result;
	private String   		pageNo;
	private String   		pageSize;
	private RtuPara  		rtuPara;
	private RtuPayCtrl		rtuPayCtrl;
	private int				apptype;
	private String	 		field;

	
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : getRtuPayCtrl
	* <p>
	* <p>DESCRIPTION : 分页获取终端预付费控制表
	* <p>
	* <p>COMPLETION
	* <p>INPUT       :  
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	@JSON(serialize = false)
	public String getRtuPayCtrlPara()
	{
		List         list      = null;
		StringBuffer ret_buf   = new StringBuffer();
		int 		 page	   = Integer.parseInt(pageNo == null? "1" : pageNo);
		int 		 pagesize  = Integer.parseInt(pageSize == null? "10" : pageSize);
		
		String idFromTree = null;
		String param2     = null;
		
		String getRtuhql  = "select a.id,a.describe,p.useFlag,p.yffbgDate,p.yffbgTime,p.sg186bgDate,p.sg186bgTime from " + YDTable.TABLECLASS_RTUPARA + " as a," + YDTable.TABLECLASS_RTUPAYCTRL + " as p ";
		String counthql   = "select count(a.id) from " + YDTable.TABLECLASS_RTUPARA + " as a," + YDTable.TABLECLASS_RTUPAYCTRL + " as p ";
		
		String order = " order by b.substId,b.lineId,b.id";
		
		if(result != null && !result.isEmpty()) {
			JSONObject jsonObj  = JSONObject.fromObject(result);
			param2     = jsonObj.getString("id");
			String name     = jsonObj.getString("describe");
			int pos = param2.indexOf("_");
			idFromTree = param2.substring(pos + 1);
			
			if(param2.equals(SDDef.GLOBAL_KE2)){
				getRtuhql += "," + YDTable.TABLECLASS_CONSPARA + " as b where a.id = p.rtuId and a.consId=b.id and b.appType="+apptype;
				counthql  += "," + YDTable.TABLECLASS_CONSPARA + " as b where a.id = p.rtuId and a.consId=b.id and b.appType="+apptype;
				
				if(!name.isEmpty()){
					getRtuhql += " and a.describe like '%"+name+"%'";
					counthql  += " and a.describe like '%"+name+"%'";
				}
				
				order = " order by b.orgId,b.lineFzManId,b.id";
			} else if (param2.startsWith(YDTable.TABLECLASS_ORGPARA)) {
				getRtuhql += "," + YDTable.TABLECLASS_CONSPARA + " as b, " + YDTable.TABLECLASS_ORGPARA + " as c where a.id = p.rtuId and a.consId=b.id and b.appType="+apptype+" and b.orgId=c.id and c.id=" + idFromTree ;
				counthql  += "," + YDTable.TABLECLASS_CONSPARA + " as b, " + YDTable.TABLECLASS_ORGPARA + " as c where a.id = p.rtuId and a.consId=b.id and b.appType="+apptype+" and b.orgId=c.id and c.id=" + idFromTree ;
				
				if(!name.isEmpty()){
					getRtuhql += " and a.describe like '%"+name+"%'";
					counthql  += " and a.describe like '%"+name+"%'";
				}
				
				order = " order by b.orgId,b.lineFzManId,b.id";
			} else if (param2.startsWith(YDTable.TABLECLASS_LINEFZMAN)) {
				getRtuhql += "," + YDTable.TABLECLASS_CONSPARA + " as b where a.id = p.rtuId and a.consId=b.id and b.appType="+apptype+" and  b.lineFzManId=" + idFromTree ;
				counthql  += "," + YDTable.TABLECLASS_CONSPARA + " as b where a.id = p.rtuId and a.consId=b.id and b.appType="+apptype+" and  b.lineFzManId=" + idFromTree ;
				
				if(!name.isEmpty()){
					getRtuhql += " and a.describe like '%"+name+"%'";
					counthql  += " and a.describe like '%"+name+"%'";
				}
				
				order = " order by b.orgId,b.lineFzManId,b.id";
			} else if (param2.startsWith(YDTable.TABLECLASS_CONSPARA)){
				getRtuhql += "," + YDTable.TABLECLASS_CONSPARA + " as b where a.id = p.rtuId and a.consId=b.id and b.id=" + idFromTree ;
				counthql  += "," + YDTable.TABLECLASS_CONSPARA + " as b where a.id = p.rtuId and a.consId=b.id and b.id=" + idFromTree ;
				if(!name.isEmpty()){
					getRtuhql += " and a.describe like '%"+name+"%'";
					counthql  += " and a.describe like '%"+name+"%'";
				}
				order = "";
			}
			
			else {
				getRtuhql += " where a.id = p.rtuId and a.id=" + idFromTree ;
				counthql  += " where a.id = p.rtuId and a.id=" + idFromTree ;
				order = "";
			}
		}
		
		HibPage hib_page = new HibPage(page, pagesize);
		hib_page.setTotalrecords(counthql);
		
		if (pagesize == 0) {
			pagesize = hib_page.getTotalrecords();
			hib_page.setPagesize(pagesize);
		} 
		if(hib_page.getTotalrecords()== 0) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		ret_buf.append(SDDef.JSONTOTAL + hib_page.getTotalrecords() + SDDef.JSONPAGEROWS);
		getRtuhql += order;
		list = hib_page.getRecord(getRtuhql);
		Iterator it = list.iterator();
		
		int no = pagesize * (hib_page.getCurrentpage() - 1) + 1;
		
		while(it.hasNext()) {
			int i = 0;
			Object[] obj = (Object[])it.next();

			ret_buf.append(SDDef.JSONLBRACES);
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + obj[i] + SDDef.JSONNZDATA); i++;	//i+1 
			ret_buf.append(SDDef.JSONQUOT + (no++) +  SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + "<a href='javascript:redoOnRowDblClicked("+ obj[0] +");'>" + CommBase.CheckString(obj[i++]) + "</a>" + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_USEFLAG, obj[i++])  + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(obj[i++]) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToHMS(obj[i++],0) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(obj[i++]) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToHMS(obj[i++],0) + SDDef.JSONCCM);
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRBRBR);
		result = ret_buf.toString();
		return SDDef.SUCCESS;
	}
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : getRtuPayCtrlById
	* <p>
	* <p>DESCRIPTION : 根据ID取得终端预付费控制信息 
	* <p>
	* <p>COMPLETION
	* <p>INPUT       :  
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	@JSON(serialize = false)
	public String getRtuPayCtrlById()
	{
		if (result == null) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		StringBuffer ret_buf = new StringBuffer();
		HibDao 		 hib_dao = new HibDao();
		ret_buf.append(SDDef.JSONSELDATA);
		
		String titles[] = field.split(SDDef.SPLITCOMA);
		String col = "";
		int i = 0;
		
		for(i = 0; i < 2; i++ ) {
			col+=",a."+titles[i];
		}
		
		for(; i < titles.length; i++ ) {
			col+=",b."+titles[i];
		}
		
		col = col.substring(1);
		String hql = "select " + col + " from " + YDTable.TABLECLASS_RTUPARA + " as a," + YDTable.TABLECLASS_RTUPAYCTRL + " as b where a.id = b.rtuId and a.id=" + result;
		List list = hib_dao.loadAll(hql);
		if(list.size() == 0){
			result = "";
			return SDDef.SUCCESS;
		}
		Iterator it = list.iterator();
		while(it.hasNext()){
			Object[] obj = (Object[])it.next();
			for(i=0;i<titles.length;i++){
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(obj[i]) + SDDef.JSONQUOT  + SDDef.JSONCOMA);
			}
		}
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();
		
		return SDDef.SUCCESS;
	}
	

	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : addOrEdit
	* <p>
	* <p>DESCRIPTION : 修改终端预付费控制记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	public String addOrEdit()
	{
		HibDao  hib_dao = new HibDao();
		try {
			if(rtuPayCtrl.getRtuId() == null || rtuPayCtrl.getRtuId() == -1) {
				result = SDDef.FAIL;
				return SDDef.SUCCESS;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		if (hib_dao.saveOrUpdate(rtuPayCtrl)) {
			
			SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_UPDATE, "Modify Terminal["+ rtuPayCtrl.getRtuId() +"]‘s Prepaid Control Archive");
			
			result = SDDef.SUCCESS;
		}else{
			result = SDDef.FAIL;
		}
		return SDDef.SUCCESS;
	}	
	
	public String getResult() 
	{
		return result;
	}
	
	public void setResult(String result) 
	{
		this.result = result;
	}
	
	public String getPageNo() 
	{
		return pageNo;
	}
	
	public void setPageNo(String pageNo) 
	{
		this.pageNo = pageNo;
	}
	
	public String getPageSize() 
	{
		return pageSize;
	}
	
	public void setPageSize(String pageSize) 
	{
		this.pageSize = pageSize;
	}
	
	public RtuPara getRtuPara() {
		return rtuPara;
	}

	public void setRtuPara(RtuPara rtuPara) {
		this.rtuPara = rtuPara;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
	public RtuPayCtrl getRtuPayCtrl() {
		return rtuPayCtrl;
	}
	
	public void setRtuPayCtrl(RtuPayCtrl rtuPayCtrl) {
		this.rtuPayCtrl = rtuPayCtrl;
	}

	public int getApptype() {
		return apptype;
	}

	public void setApptype(int apptype) {
		this.apptype = apptype;
	}

}

