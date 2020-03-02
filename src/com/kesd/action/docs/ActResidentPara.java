package com.kesd.action.docs;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.json.annotations.JSON;

import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.common.SDOperlog;
import com.kesd.common.YDTable;
import com.kesd.dao.HibPage;
import com.kesd.dao.SqlPage;
import com.kesd.dbpara.YffManDef;
import com.kesd.dbpara.rtu.ResidentPara;
import com.kesd.service.DBOper;

import com.libweb.common.CommBase;
import com.libweb.dao.HibDao;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;

public class ActResidentPara extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5826438366353153141L;
	private String 			result;
	private String 			pageNo;			//页号
	private String 			pageSize;		//每页记录数
	private ResidentPara	residentpara;	//居民区档案
	private String			field;			//所需查询的数据库字段
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : addOrEdit
	* <p>
	* <p>DESCRIPTION : 添加或修改居民用户档案记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public String addOrEdit()throws Exception
	{
		HibDao  hib_dao = new HibDao();
		Short   maxid   = 0;
		boolean add     = false;
		try{
			if(residentpara.getRtuId()==null||residentpara.getRtuId()==-1){
				result = SDDef.FAIL;
				return SDDef.SUCCESS;
			}
			if(residentpara.getId() == null){
				add = true;
				String hql = "select max(id) from "+YDTable.TABLECLASS_RESIDENTPARA+" as a where a.rtuId="+residentpara.getRtuId();
				List list = hib_dao.loadAll(hql);
				if(list.get(0) != null) {
					maxid = (Short)list.get(0);
					maxid ++;
				}
				residentpara.setId(maxid);
				
				String consNo = residentpara.getConsNo();
				if (consNo!= null){//居民户号，可以为空，但整个居民表（residentpara）内户号不能重复。
					hql = " from " + YDTable.TABLECLASS_RESIDENTPARA + " as a where a.consNo='"+residentpara.getConsNo() + 
								"' and (a.rtuId<>" + residentpara.getRtuId() + " or a.id<>" + residentpara.getId() + ")";
					list = hib_dao.loadAll(hql);
					if(list.size() > 0) {//居民户号已存在。不继续操作
						result = "exist";
						return SDDef.SUCCESS;
					}
				}
				
			}			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if (hib_dao.saveOrUpdate(residentpara)) {
			if(add) {
				
				String hql = "update "+YDTable.TABLECLASS_RTUPARA+" set residentNum=residentNum+1 where id=" + residentpara.getRtuId();
				if(!hib_dao.updateByHql(hql)) {
					result = SDDef.FAIL;
				}
				SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_ADD, "Add Terminal["+ residentpara.getRtuId() +"] ->Resident Archive["+residentpara.getId()+"]");
				
			}
			else{
				
				SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_UPDATE, "Modify Terminal["+ residentpara.getRtuId() +"] -> Resident Archive["+residentpara.getId()+"]");
				
			}
//			TableIdAndName.init(YDTable.TABLECLASS_RESIDENTPARA);
			result = SDDef.SUCCESS;
		}
		else {
			result = SDDef.FAIL;
		}
		
		return SDDef.SUCCESS;
	}
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : delResidentParaById
	* <p>
	* <p>DESCRIPTION : 根据id、rtuId删除居民用户档案记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	public String delResidentParaById()
	{
		HibDao  hib_dao = new HibDao();
		String id[]	      = result.split(SDDef.JSONSPLIT);
		byte   flag	   	  = 1;
		String currentRes = null;
		for (int i = 0; i < id.length; i++) {
			String resId = id[i].split("_")[0];
			String rtuId = id[i].split("_")[1];
			String hql = "from "+YDTable.TABLECLASS_METERPARA+" as a where a.residentId=" + resId +" and a.rtuId="+rtuId;
			if (!hib_dao.loadAll(hql).isEmpty()) {
				flag=2;
				currentRes = DBOper.getDescribeById(YDTable.TABLECLASS_RESIDENTPARA, Short.parseShort(resId));
				break;
			}
			else{
				hql = "delete from "+YDTable.TABLECLASS_RESIDENTPARA+ " as a where a.id="+resId+" and a.rtuId="+rtuId;
				SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_DELETE, "Delete Terminal["+ rtuId +"] -> Resident Archive["+resId+"]");

				String hqlNum = "update "+YDTable.TABLECLASS_RTUPARA+" set residentNum = residentNum-1 where residentNum>0 and id=" + rtuId;
				if(!hib_dao.updateByHql(hqlNum)) {
					flag=0;
					break;
				}
				
				if(!hib_dao.updateByHql(hql)){
					flag=0;
					break;
				}
			}
		}
		if (flag == 1) {
			
//			YDOperlog.operlog(CommFunc.getUser().getUserpara().getId(), SDDef.LOG_DELETE, "删除终端["+ result.split(SDDef.SPLITCOMA)[1] +"]下的居民档案[" + result.split(SDDef.SPLITCOMA)[0] + "]");
			
			result = SDDef.SUCCESS;
//			TableIdAndName.init(YDTable.TABLECLASS_RESIDENTPARA);
		}
		else if (flag == 0) {
			result = SDDef.FAIL;
		}
		else if (flag == 2) {
			result = "Resident【"+currentRes + "】have resident terminal cost control parameter management files please delete them first！";
		}
		return SDDef.SUCCESS;
	}
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : getResidentParaById
	* <p>
	* <p>DESCRIPTION : 通过ID获取居民用户档案记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@JSON(serialize = false)
	public String getResidentParaById() throws Exception
	{
		if(result == null) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		StringBuffer ret_buf  = new StringBuffer();
		HibDao       hib_dao  = new HibDao();
		String hql = "from " + YDTable.TABLECLASS_RESIDENTPARA + " as a where a.id=" + result.split(SDDef.SPLITCOMA)[0] + " and a.rtuId=" + result.split(SDDef.SPLITCOMA)[1];
		residentpara   		  = (ResidentPara)hib_dao.loadAll(hql).get(0);
		
		String titles[] = field.split(SDDef.SPLITCOMA);
		ret_buf.append(SDDef.JSONSELDATA);
		for(int i = 0; i < titles.length; i ++) {
			ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(CommFunc.getMethodValue(titles[i], ResidentPara.class,residentpara)) + SDDef.JSONQUOT  + SDDef.JSONCOMA);
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
	* <p>DESCRIPTION : 分页获取居民用户档案记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		List 			list		= null;
		StringBuffer 	ret_buf		= new StringBuffer();
		int 			page		= Integer.parseInt(pageNo);
		int  			pagesize	= Integer.parseInt(pageSize);
		String describe = null;
		String rtuid    = null;
		if(result != null && !result.isEmpty()){
			JSONObject jsonObj  = JSONObject.fromObject(result);
			describe = jsonObj.getString("describe");
			rtuid    = jsonObj.getString("rtuid");
		}
		else {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		HibPage hib_page = new HibPage(page, pagesize);
		
		YffManDef user = CommFunc.getYffMan();
		String getCounthql = "";
		String getDatahql  = "";
		if(user.getOrgId() == null){
			getCounthql = "select count(*) from " + YDTable.TABLECLASS_RESIDENTPARA 
			+ " as a ,RtuPara rtu,ConsPara c where a.rtuId = rtu.id and rtu.consId = c.id ";
			getDatahql  = "select a.rtuId,a.id,a.describe,a.consNo, a.address, a.post,a.phone, a.mobile,a.fax, a.mail,a.infCode1,a.infCode2,	a.infCode3	 from " + YDTable.TABLECLASS_RESIDENTPARA 
			+ " a ,"+YDTable.TABLECLASS_RTUPARA+" rtu,"+YDTable.TABLECLASS_CONSPARA+" c where a.rtuId = rtu.id and rtu.consId = c.id ";
			if(describe != null && !describe.isEmpty()){
				getCounthql += " and a.describe like '%" + describe + "%'";
				getDatahql  += " and a.describe like '%" + describe + "%'";
			}
			if(!rtuid.equals("-1")){
				getCounthql += " and a.rtuId = " + rtuid;
				getDatahql  += " and a.rtuId = " + rtuid;
			}

			if(user.getRank() != 0){
				getCounthql += " and c.orgId =" + user.getOrgId();
				getDatahql  += " and c.orgId =" + user.getOrgId();
	 		}			
		}else{
			getCounthql = "select count(*) from " + YDTable.TABLECLASS_RESIDENTPARA 
			+ " as a ,RtuPara rtu,ConsPara c,"+ YDTable.TABLECLASS_Userrankbound +" as u where u.consId = c.id and rtu.consId = c.id and a.rtuId = rtu.id " + " and u.userId = " + user.getId();
			getDatahql  = "select a.rtuId,a.id,a.describe,a.consNo, a.address, a.post,a.phone, a.mobile,a.fax, a.mail,a.infCode1,a.infCode2,	a.infCode3	 from " + YDTable.TABLECLASS_RESIDENTPARA 
			+ " a ,"+YDTable.TABLECLASS_RTUPARA+" rtu,"+YDTable.TABLECLASS_CONSPARA+" c, "+ YDTable.TABLECLASS_Userrankbound + " as u where u.consId = c.id and rtu.consId = c.id and a.rtuId = rtu.id " + " and u.userId = " + user.getId();
			
			if(describe != null && !describe.isEmpty()){
				getCounthql += " and a.describe like '%" + describe + "%'";
				getDatahql  += " and a.describe like '%" + describe + "%'";
			}
			if(!rtuid.equals("-1")){
				getCounthql += " and a.rtuId = " + rtuid;
				getDatahql  += " and a.rtuId = " + rtuid;
			}

			if(user.getRank() != 0){
				getCounthql += " and c.orgId =" + user.getOrgId();
				getDatahql  += " and c.orgId =" + user.getOrgId();
	 		}			
		}

		
		hib_page.setTotalrecords(getCounthql);
		if(pagesize == 0) {
			pagesize = hib_page.getTotalrecords();
			hib_page.setPagesize(pagesize);
		}
		if(hib_page.getTotalrecords() == 0) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		try {
			list = hib_page.getRecord(getDatahql);
			ret_buf.append(SDDef.JSONTOTAL + hib_page.getTotalrecords() + SDDef.JSONPAGEROWS);
			Iterator it=list.iterator();
			
			int no = pagesize * (hib_page.getCurrentpage() - 1) + 1;
			
			while(it.hasNext()){
				Object[] obj= (Object[])it.next();
				
				ret_buf.append(SDDef.JSONLBRACES); 
				ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + obj[1] + "_" + obj[0] + SDDef.JSONDATA);
				
				ret_buf.append(SDDef.JSONQUOT + (no++) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "<a href='javascript:redoOnRowDblClicked(&quot;"+  obj[1] +","+obj[0] +"&quot;);'>" + CommFunc.CheckString(obj[2]) + "</a>" + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(DBOper.getDescribeById("rtupara", obj[0]))   + SDDef.JSONCCM);
				
				ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(obj[3])   + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(obj[4])  + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(obj[5])     + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(obj[6])    + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(obj[7])   + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(obj[8])      + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(obj[9])     + SDDef.JSONCCM);
				
				ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
				ret_buf.append(SDDef.JSONRBCM);
			}
			
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBRBRBR);
			result = ret_buf.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return SDDef.SUCCESS;
	}
	
	//根据终端id获得终端名称
	@JSON(serialize = false)
	public String getResidentDescId () throws Exception{
		if(result == null || result.isEmpty()){		//value:org_id
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}		
		String sql = "select describe from rtupara where id= " + result;
		JSONObject json = new JSONObject();		
		JDBCDao j_dao = new JDBCDao();
		JSONArray j_array = new JSONArray();
		ResultSet rs = null;
		try {
			//执行sql语句
			rs = j_dao.executeQuery(sql);
			//生成json字符串
			while(rs.next()){
				j_array.add(rs.getString(1));
			}
		}catch (Exception e) {
			e.printStackTrace();
			return SDDef.EMPTY;
		}finally{
			j_dao.closeRs(rs);
		}
		json.put("data",j_array);
		result = json.toString();		
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

	public ResidentPara getResidentpara() {
		return residentpara;
	}

	public void setResidentpara(ResidentPara residentpara) {
		this.residentpara = residentpara;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
