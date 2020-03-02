/********************************************************************************************************
*                                        用电WEB Ver2.0													*
*																										*
*                           (c) Copyright 2010~,   KLD Automation Co., Ltd.								*
*                                           All Rights Reserved											*
*																										*
*	FileName	:	ActYffManDef															    		*
*	Description	:	预付费人员增加、修改、删除、查询														*
*	Author		:																						*
*	Date		:	2011/01/05																			*
*																										*
*   No.         Date         Modifier        Description												*
*   -----------------------------------------------------------------------------------------------     *
*********************************************************************************************************/
package com.kesd.action.docs;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.YDTable;
import com.kesd.dao.SqlPage;
import com.kesd.dbpara.YffManDef;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
import com.libweb.dao.HibDao;
import com.opensymphony.xwork2.ActionSupport;


/** ******************* CLASS DESCRIPTION *******************
* <p>
* <p>NAME        : ActYffManDef
* <p>
* <p>DESCRIPTION : 预付费人员增加、修改、删除、查询 
* <p>
* <p>  No.         Date         Modifier        Description	
* <p>---------------------------------------------------------
**************************************************************/
public class ActYffManDef extends ActionSupport
{

	private static final long serialVersionUID = -601568323512959063L;
	private String   	result;
	private String   	pageNo;			//页号
	private String   	pageSize;  		//每页记录数
	private YffManDef 	yffmandef;		//通道档案
	private String   	field;			//所需查询的数据库字段
	private int count = 0;
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : addOrEdit
	* <p>
	* <p>DESCRIPTION : 添加或修改预付费人员档案记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	
	@SuppressWarnings("unchecked")
	public String addOrEdit()throws Exception {
		HibDao hib_dao = new HibDao();
		String hql = null;
		if(yffmandef == null) {
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		if(yffmandef.getRank() == 0) {
			yffmandef.setOrgId(null);
		}
		//添加
		if(yffmandef.getId() == null){
			hql = "from " + YDTable.TABLECLASS_YFFMANDEF + " as a where a.name = '"+ yffmandef.getName()+"'";
		}
		//修改
		else{
			hql = "from " + YDTable.TABLECLASS_YFFMANDEF;
			List yffman = hib_dao.loadAll(hql);
			short admin_id = -1;
			for (int j = 0; j < yffman.size(); j++) {
				YffManDef tmp = (YffManDef) yffman.get(j);
				if(tmp.getName().trim().equals(SDDef.ADMIN)){
					admin_id = tmp.getId();
					break;
				}
			}
			
			if(admin_id == yffmandef.getId()){
				result = SDDef.FAIL;
				return SDDef.SUCCESS;
			}
			
			hql = "from " + YDTable.TABLECLASS_YFFMANDEF + " as a where a.name='"+yffmandef.getName()+"' and a.id<>" + yffmandef.getId();
		}
		List list = hib_dao.loadAll(hql);
		if(list.size() > 0){
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		if (hib_dao.saveOrUpdate(yffmandef)) {
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
	* <p>DESCRIPTION : 通过ID删除预付费记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public String delYffManDefById()
	{
		HibDao hib_dao = new HibDao();
		String id[]	   = result.split(SDDef.JSONSPLIT);
		String hql = "from " + YDTable.TABLECLASS_YFFMANDEF;
		
		List yffman = hib_dao.loadAll(hql);
		String admin_id = null;
		for (int j = 0; j < yffman.size(); j++) {
			YffManDef tmp = (YffManDef) yffman.get(j);
			if(tmp.getName().trim().equals(SDDef.ADMIN)){
				admin_id = tmp.getId().toString();
				break;
			}
		}
		
		boolean admin_flag = false;
		for (int i = 0; i < id.length; i++) {
			if(id[i].equals(admin_id)){
				id[i] = null;
				admin_flag = true;
			}
		}
		
		Short[] ids = null;
		if(admin_flag){
			ids = new Short[id.length - 1];
		}else{
			ids = new Short[id.length];
		}
		for (int i = 0; i < id.length; i++) {
			if(id[i] == null)continue;
			ids[i] = Short.parseShort(id[i]);
		}
		
		if(hib_dao.delete(YffManDef.class, ids)) {
			result=SDDef.SUCCESS;
		}else {
			result=SDDef.FAIL;
		}
		
		return SDDef.SUCCESS;
	}
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : getYffManDefById
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
	public String getYffManDefById() throws Exception
	{
		if(result == null){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		StringBuffer ret_buf = new StringBuffer();
		HibDao 		 hib_dao = new HibDao();
		yffmandef             = (YffManDef) hib_dao.loadById(YffManDef.class, Short.parseShort(result));
		
		String titles[] = field.split(SDDef.SPLITCOMA);
		ret_buf.append(SDDef.JSONSELDATA);
		for(int i = 0; i < titles.length; i ++) {
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(CommFunc.getMethodValue(titles[i], YffManDef.class,yffmandef)) + SDDef.JSONQUOT  + SDDef.JSONCOMA);
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
		int 			page	 = Integer.parseInt(pageNo == null  ? "1" : pageNo);
		int 			pagesize = Integer.parseInt(pageSize == null? "20" : pageSize);
		
		SqlPage sql_page = new SqlPage(page, pagesize);
		StringBuffer countSql=new StringBuffer();
		
		countSql.append("select count(a.id) from yffmandef as a left join orgpara as b on a.org_id=b.id left join line_fzman as c on a.fzman_id=c.id where 1=1 ");
		
		StringBuffer sqlbuf=new StringBuffer();
		String sql=new String();
		sqlbuf.append(" a.id,a.name,a.describe as yff_desc, a.apptype,a.rank,a.rese1_flag,a.rese2_flag,a.rese3_flag,b.describe as org_desc,a.viewflag,a.openflag,a.payflag,a.paraflag,a.ctrlflag, l.describe as fzm_desc ");
		sqlbuf.append("from line_fzman as l right outer join yffmandef as a on a.fzman_id = l.id left outer join orgpara as b on a.org_id=b.id where 1=1 ");
		
		YffManDef user = CommFunc.getYffMan();
		//无用户管理权限
		if(user.getRese2_flag() == null || user.getRese2_flag() != (byte)1) {
			countSql.append(" and a.name='"+user.getName()+"' ");
			sqlbuf.append  (" and a.name='"+user.getName()+"' ");
		}
		else{
			if(user.getRese1_flag() != null && user.getRese1_flag() == 1) {	//查询权限
				countSql.append(" and a.id=" + user.getId());
				sqlbuf.append  (" and a.id=" + user.getId());
			}
			
		}
		
		//子查询作为一个新表 重新命名为sub
		sqlbuf.append(" order by a.id desc) sub order by sub.id" );
		sql_page.setTotalrecords(countSql.toString());
		count = sql_page.getTotalrecords();
		sql = sqlbuf.toString();
		
		if(pagesize == 0){
			 sql="select top " + count + " * from (select top "+ count +" " + sql;
		}else{
			if(pagesize > count){
				sql = "select top "+pagesize+" * from (select top "+ count +" " + sql;
			}else{
				sql = "select top "+pagesize+" * from (select top "+(count - pagesize*(page-1)) + sql;
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
		
		Map<Integer,String> yesflag = Rd.getDict(Dict.DICTITEM_YESFLAG);
		
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map =  (Map<String, Object>) list.get(i);				
			ret_buf.append(SDDef.JSONLBRACES); 
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + map.get("id") + SDDef.JSONDATA);
			
			ret_buf.append(SDDef.JSONQUOT + (no++)                                                  + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("name"))                   + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("yff_desc"))               + SDDef.JSONCCM);
			
			//ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_YFFAPPTYPE,map.get("apptype")) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_YFFRANK, map.get("rank"))      + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("org_desc"))    		    + SDDef.JSONCCM);
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("fzm_desc"))    		    + SDDef.JSONCCM);//线路负责人
			
			Object tmp = map.get("viewflag");
			ret_buf.append(SDDef.JSONQUOT + (tmp == null?"":yesflag.get(Integer.parseInt(tmp.toString()))) + SDDef.JSONCCM);
			tmp = map.get("openflag");
			ret_buf.append(SDDef.JSONQUOT + (tmp == null?"":yesflag.get(Integer.parseInt(tmp.toString()))) + SDDef.JSONCCM);
			tmp = map.get("payflag");
			ret_buf.append(SDDef.JSONQUOT + (tmp == null?"":yesflag.get(Integer.parseInt(tmp.toString()))) + SDDef.JSONCCM);
			tmp = map.get("paraflag");
			ret_buf.append(SDDef.JSONQUOT + (tmp == null?"":yesflag.get(Integer.parseInt(tmp.toString()))) + SDDef.JSONCCM);
			tmp = map.get("ctrlflag");
			ret_buf.append(SDDef.JSONQUOT + (tmp == null?"":yesflag.get(Integer.parseInt(tmp.toString()))) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_DOCRIGHT,map.get("rese1_flag")) + SDDef.JSONCCM);
			tmp = map.get("rese2_flag");
			ret_buf.append(SDDef.JSONQUOT + (tmp == null?"":yesflag.get(Integer.parseInt(tmp.toString()))) + SDDef.JSONCCM);
			
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_DOCRIGHT,map.get("rese3_flag")) + SDDef.JSONCCM);
			
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

	public YffManDef getYffmandef() {
		return yffmandef;
	}

	public void setYffmandef(YffManDef yffmandef) {
		this.yffmandef = yffmandef;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
}