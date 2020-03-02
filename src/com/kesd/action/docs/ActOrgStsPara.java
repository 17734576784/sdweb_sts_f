package com.kesd.action.docs;

/********************************************************************************************************
*                                        用电WEB Ver2.0													*
*																										*
*                           (c) Copyright 2010~,   KLD Automation Co., Ltd.								*
*                                           All Rights Reserved											*
*																										*
*	FileName	:	Actorgstspara															    		*
*	Description	:	费率增加、修改、删除、查询														*
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

import com.libweb.common.CommBase;
import com.libweb.dao.HibDao;
import com.libweb.dao.JDBCDao;

import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.common.SDOperlog;
import com.kesd.common.YDTable;
import com.kesd.dao.HibPage;
import com.kesd.dbpara.OrgPara;
import com.kesd.dbpara.OrgReg;
import com.kesd.dbpara.OrgStsPara;
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
public class ActOrgStsPara extends ActionSupport
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -601568323512959063L;
	private String   		result;
	private String   		pageNo;			//页号
	private String   		pageSize;  		//每页记录数
	private String   		field;			//所需查询的数据库字段
	private OrgPara         orgpara;
	private OrgStsPara 		orgstspara;		//供电所STS参数表
	private OrgReg          orgReg;
	
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
		HibDao hib_dao  = new HibDao();
		JDBCDao jdbcDao = new JDBCDao();
		//rorgNo 唯一性
		String selectRorgNoSql = "select count(*) id from orgpara where r_org_no = '" + orgpara.getRorgNo() + "'";
		List<Map<String,Object>> listRorgNo = jdbcDao.result(selectRorgNoSql);
		
		//判断是修改还是新增
		//如果是修改,那么查库是否修改了
		if(orgpara.getId() != null){
			//修改的话不包含本记录
			if(Integer.parseInt(CommFunc.objectToString(listRorgNo.get(0).get("id")))>1){
				result = "SGC already exists.";
				return SDDef.SUCCESS;		
			}					
			
			
			String sql = "select encrypt_type,key_regno,key_type,r_org_no from org_stspara,orgpara where orgpara.id = org_stspara.org_id and org_stspara.org_id = " + orgpara.getId();			
			List<Map<String,Object>> list = jdbcDao.result(sql);
			String encryptType 	= CommFunc.objectToString(list.get(0).get("encrypt_type"));
			String keyRegno 	= CommFunc.objectToString(list.get(0).get("key_regno").toString());
			String keyType 		= CommFunc.objectToString(list.get(0).get("key_type").toString());
			String rOrgNo		= CommFunc.objectToString(list.get(0).get("r_org_no").toString());
			if(orgstspara.getET() != CommFunc.objectToInt(encryptType)){
				sql = "update meter_stspara set meter_stspara.keychange = 1" +
						" from conspara cons,org_stspara os,rtupara rtu " + 
						" where os.org_id = cons.org_id and cons.id = rtu.cons_id " +
						" and meter_stspara.rtu_id = rtu.id and os.org_id = " + orgpara.getId();
				if(!jdbcDao.executeUpdate(sql)){
					result = SDDef.FAIL;
					return SDDef.SUCCESS;
				}else{
					SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_UPDATE, "update meter_stspara encrypt_type parameter successfully.");
				}
			}
			if(orgstspara.getKR() != CommFunc.objectToInt(keyRegno)){
				sql = "update meter_stspara set meter_stspara.keychange = 1,meter_stspara.old_regno = regno,meter_stspara.regno = " + orgstspara.getKR() +
				" from conspara cons,org_stspara os,rtupara rtu " + 
				" where os.org_id = cons.org_id and cons.id = rtu.cons_id " +
				" and meter_stspara.rtu_id = rtu.id and os.org_id = " + orgpara.getId();			
				if(!jdbcDao.executeUpdate(sql)){
					result = SDDef.FAIL;
					return SDDef.SUCCESS;
				}else{
					SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_UPDATE, "update meter_stspara regno parameter successfully.");
				}
			}
			if(orgstspara.getKT() != CommFunc.objectToInt(keyType)){
				sql = "update meter_stspara set meter_stspara.keychange = 1 ,meter_stspara.old_kt = kt,meter_stspara.kt = " + orgstspara.getKT() + 
				" from conspara cons,org_stspara os,rtupara rtu " + 
				" where os.org_id = cons.org_id and cons.id = rtu.cons_id " +
				" and meter_stspara.rtu_id = rtu.id and os.org_id = " + orgpara.getId();			
				if(!jdbcDao.executeUpdate(sql)){
					result = SDDef.FAIL;
					return SDDef.SUCCESS;
				}else{
					SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_UPDATE, "update meter_stsparaorgstspara kt parameter successfully.");
				}
			}
			if(!orgpara.getRorgNo().equals(rOrgNo)){
				sql = "update meter_stspara set meter_stspara.keychange = 1 ,meter_stspara.old_sgc = sgc,meter_stspara.sgc = " + orgpara.getRorgNo() + 
				" from conspara cons,org_stspara os,rtupara rtu " + 
				" where os.org_id = cons.org_id and cons.id = rtu.cons_id " +
				" and meter_stspara.rtu_id = rtu.id and os.org_id = " + orgpara.getId();				
				if(!jdbcDao.executeUpdate(sql)){
					result = SDDef.FAIL;
					return SDDef.SUCCESS;
				}else{
					SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_UPDATE, "update meter_stspara sgc parameter successfully.");
				}
			}
			
		}else{
			if(Integer.parseInt(CommFunc.objectToString(listRorgNo.get(0).get("id")))>0){
				result = "SGC already exists.";
				return SDDef.SUCCESS;		
			}
		}
		if (hib_dao.saveOrUpdate(orgpara)) {
			orgstspara.setOrgId(orgpara.getId());
			if (hib_dao.saveOrUpdate(orgstspara)) {
				//添加一条记录
				orgReg.setOrgId(orgpara.getId());
				orgReg.setRegType(Short.parseShort("0"));
				orgReg.setRegUser(orgpara.getDescribe());
				if(hib_dao.saveOrUpdate(orgReg)){
					result = SDDef.SUCCESS;					
				}
			}
		}
		else {
			result = SDDef.FAIL;
		}
		return SDDef.SUCCESS;
	}
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : delorgstsparaById
	* <p>
	* <p>DESCRIPTION : 通过ID删除预付费记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	public String delorgstsparaById()
	{
		HibDao hib_dao = new HibDao();
		String id[]	   = result.split(SDDef.JSONSPLIT);
		Short[] orgids = new Short[id.length];
		//Integer[] ids = new Integer[id.length];
		for(int i=0;i<id.length;i++) {
			//ids[i] = Integer.parseInt(id[i]);
			orgids[i] = Short.parseShort(id[i]);
		}
		if(hib_dao.delete(OrgPara.class, orgids)) {
			if(hib_dao.delete(OrgStsPara.class, orgids)) {				
				if(hib_dao.delete(OrgReg.class, orgids)) {
					result=SDDef.SUCCESS;
				}
			}
		}
		else {
			result=SDDef.FAIL;
		}
		
		return SDDef.SUCCESS;
	}
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : getorgstsparaById
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
	public String getorgstsparaById() throws Exception
	{
		if(result == null){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		StringBuffer ret_buf = new StringBuffer();
		HibDao hib_dao = new HibDao();
		
		orgpara = (OrgPara) hib_dao.loadById(OrgPara.class, Short.parseShort(result));
		String titles[] = field.split(SDDef.SPLITCOMA);
		ret_buf.append(SDDef.JSONSELDATA);
		for(int i = 0; i < 7; i ++) {
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(CommFunc.getMethodValue(titles[i], OrgPara.class,orgpara)) + SDDef.JSONQUOT  + SDDef.JSONCOMA);
		}
		
		orgstspara  = (OrgStsPara) hib_dao.loadById(OrgStsPara.class, Short.parseShort(result));
		for(int i = 7; i < titles.length; i ++) {
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(CommFunc.getMethodValue(titles[i], OrgStsPara.class,orgstspara)) + SDDef.JSONQUOT  + SDDef.JSONCOMA);
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
		String  hql		 = "select count(a.id) from " + YDTable.TABLECLASS_ORGPARA + " as a, "+YDTable.TABLECLASS_ORGSTSPARA +" as b where  a.id = b.OrgId";;
	 
		hib_page.setTotalrecords(hql);
		if(hib_page.getTotalrecords()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		if (pagesize == 0) {
			pagesize = hib_page.getTotalrecords();
			hib_page.setPagesize(pagesize);
		}		 	
	
		hql = " select a.id,a.describe,a.rorgNo,a.addr,a.postalCode,a.linkMan,a.telNo,b.KT,b.DKGA,b.VK1,b.VK2,b.VK3,b.VK4 from " + YDTable.TABLECLASS_ORGPARA + " as a , "+YDTable.TABLECLASS_ORGSTSPARA +" as b where  a.id = b.OrgId";
 		list = hib_page.getRecord(hql);
		ret_buf.append(SDDef.JSONTOTAL + hib_page.getTotalrecords() + SDDef.JSONPAGEROWS);

		Iterator it=list.iterator();
		
		int no = pagesize * (hib_page.getCurrentpage() - 1) + 1;
		
		while(it.hasNext()){
			Object[] obj = (Object[])it.next();
			
			ret_buf.append(SDDef.JSONLBRACES); 
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + obj[0] + SDDef.JSONDATA);
			
			ret_buf.append(SDDef.JSONQUOT + (no++)                                                       + SDDef.JSONCCM);
			//describe
			ret_buf.append(SDDef.JSONQUOT + "[" + CommFunc.FormatAdd0(obj[0], 2) + "]" + CommBase.CheckString(obj[1])  + SDDef.JSONCCM);
			//r_org_no
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckString(obj[2])                                + SDDef.JSONCCM);
			//addr
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckString(obj[3])                                + SDDef.JSONCCM);
			//pstalcode
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckString(obj[4])                                + SDDef.JSONCCM);
			//linkman
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckString(obj[5])                                + SDDef.JSONCCM);
			//telno
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckString(obj[6])                                + SDDef.JSONCCM);

			
			
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(obj[7])                  	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(obj[8])                  	+ SDDef.JSONCCM);
 			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(obj[9])              	+ SDDef.JSONCCM);
			
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(obj[10])                  	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(obj[11])                  	+ SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(obj[12])                  	+ SDDef.JSONCCM);
			
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

	public OrgStsPara getOrgstspara() {
		return orgstspara;
	}

	public void setOrgstspara(OrgStsPara orgstspara) {
		this.orgstspara = orgstspara;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public OrgPara getOrgpara() {
		return orgpara;
	}

	public void setOrgpara(OrgPara orgpara) {
		this.orgpara = orgpara;
	}

	public OrgReg getOrgReg() {
		return orgReg;
	}

	public void setOrgReg(OrgReg orgReg) {
		this.orgReg = orgReg;
	}	
}