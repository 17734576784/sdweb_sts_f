package com.kesd.action.docs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.json.annotations.JSON;

import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.common.YDTable;
import com.kesd.dao.HibPage;
import com.kesd.dbpara.AreaPara;
import com.kesd.dbpara.YffManDef;
import com.libweb.common.CommBase;
import com.libweb.dao.HibDao;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;

public class ActAreapara  extends ActionSupport {

	private static final long serialVersionUID = -7568208524861620538L;
	
	private String   result;
	private String   pageNo;
	private String   pageSize;
	private AreaPara ap;
	private String	 field;
	
	public String edit()throws Exception
	{
		HibDao hib_dao = new HibDao();
		
		if (hib_dao.saveOrUpdate(ap)) {
			result = String.valueOf(ap.getId());
		}
		else {
			result = SDDef.FAIL;
		}
		return SDDef.SUCCESS;
	}
	
	@JSON(serialize = false)
	public String getById()
	{
		if(result == null) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		StringBuffer ret_buf = new StringBuffer();
		HibDao 		 hib_dao = new HibDao();
		
		ap = (AreaPara)hib_dao.loadById(AreaPara.class, Integer.parseInt(result));
		String fields[] = field.split(SDDef.SPLITCOMA);

		ret_buf.append(SDDef.JSONSELDATA);
		for(int i = 0; i < fields.length; i ++) {
			ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(CommFunc.getMethodValue(fields[i], AreaPara.class,ap)) + SDDef.JSONQUOT  + SDDef.JSONCOMA);
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		
		result = ret_buf.toString();
		return SDDef.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String execute() {
		
		
		String qxM = "";
		YffManDef user = CommFunc.getYffMan();
		if(user.getRank() != 0){	//权限范围 0所有终端
			qxM = " and b.id=" + user.getOrgId();
		}
		
		String order = "";
		
		List         list      = null;
		StringBuffer ret_buf   = new StringBuffer();
		int 		 page	   = Integer.parseInt(pageNo == null? "1" : pageNo);
		int 		 pagesize  = Integer.parseInt(pageSize == null? "10" : pageSize);
		
		String idFromTree = null;
		String param2     = null;
		String describe 	= null;  //名称查询条件
		
		String getRtuhql  = "select a.id,a.describe,b.describe,a.feeprojId,a.yffalarmId,a.feeBegindate,a.areaCode,a.infCode1,a.infCode2,a.infCode3 from " + YDTable.TABLECLASS_AREAPARA + " as a," + YDTable.TABLECLASS_ORGPARA + " as b where a.orgId=b.id ";
		String counthql   = "select count(a.id) from " + YDTable.TABLECLASS_AREAPARA + " as a," + YDTable.TABLECLASS_ORGPARA + " as b where a.orgId=b.id ";
		
		if(result != null && !result.isEmpty()) {
			JSONObject jsonObj  = JSONObject.fromObject(result);
			describe	=	jsonObj.getString("describe");
			param2	= jsonObj.getString("id");
			
			int pos = param2.indexOf("_");
			idFromTree = param2.substring(pos + 1);
			
			if(param2.equals(SDDef.GLOBAL_KE2)){
				
				getRtuhql += qxM;
				counthql  += qxM;
				order = " order by a.orgId,a.id";
			}
			else if (param2.startsWith(YDTable.TABLECLASS_ORGPARA)) {
				
				getRtuhql += " and a.orgId=" + idFromTree;
				counthql  += " and a.orgId=" + idFromTree;
				
				order = " order by a.id";
			} else {
				getRtuhql += " and a.id=" + idFromTree;
				counthql  += " and a.id=" + idFromTree;
				
				order = " order by a.id";
			}
		}

		if( describe != null && !describe.isEmpty()){
			getRtuhql += " and a.describe like '%" + describe + "%'";
			counthql  += " and a.describe like '%" + describe + "%'";
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
		
		String yffSql="select id,describe from yffratepara ";
		String yffAPSql="select id, describe from yffalarmpara";
		JDBCDao dao=new JDBCDao();
		
		ResultSet rs=dao.executeQuery(yffSql);
		ResultSet rsYffAP=dao.executeQuery(yffAPSql);
		
		ArrayList<String[]> yffList=new ArrayList<String[]>();
		ArrayList<String[]> yffAPList=new ArrayList<String[]>();
		
		String[] temp=new String[2];
		try {
			while(rs.next()){
			    temp=new String[2];
				temp[0]=rs.getString("id");
				temp[1]=rs.getString("describe");
				yffList.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			dao.closeRs(rs);
		}
		
		String[] tempYffAP=new String[2];
		try {
			while(rsYffAP.next()){
				tempYffAP=new String[2];
				tempYffAP[0]=rsYffAP.getString("id");
				tempYffAP[1]=rsYffAP.getString("describe");
				yffAPList.add(tempYffAP);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			dao.closeRs(rsYffAP);
		}
		
		while(it.hasNext()) {
			int i = 0;
			Object[] obj = (Object[])it.next();
			
			ret_buf.append(SDDef.JSONLBRACES);
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + obj[i++] + SDDef.JSONNZDATA);
			ret_buf.append(SDDef.JSONQUOT + (no++) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + "<a href='javascript:redoOnRowDblClicked("+ obj[0] +");'>" + CommFunc.CheckString(obj[i++]) + "</a>" + SDDef.JSONCCM);
			
			ret_buf.append(SDDef.JSONQUOT + obj[i++] + SDDef.JSONCCM);
			
			ret_buf.append(SDDef.JSONQUOT +  equalRtuAndYff(obj[i++],yffList  ) + SDDef.JSONCCM);	//费率方案
			ret_buf.append(SDDef.JSONQUOT +  equalRtuAndYff(obj[i++],yffAPList) + SDDef.JSONCCM);	//报警方案
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(obj[i++]) + SDDef.JSONCCM);
			
			ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(obj[i++]) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(obj[i++]) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(obj[i++]) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(obj[i++]) + SDDef.JSONCCM);
			
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRBRBR);
		result = ret_buf.toString();
		return SDDef.SUCCESS;
	}
	
	private String equalRtuAndYff(Object obj,ArrayList<String[]> list){
		String[] temp=new String[2];
		for(int i=0;i<list.size();i++){
			temp=list.get(i);
			if(obj==null){
				return SDDef.EMPTY;
			}
			if(obj.toString().equals(temp[0])){
				return CommBase.CheckString(temp[1]);
			}
		}
		return "";
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

	public AreaPara getAp() {
		return ap;
	}

	public void setAp(AreaPara ap) {
		this.ap = ap;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
