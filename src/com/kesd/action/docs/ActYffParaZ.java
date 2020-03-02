package com.kesd.action.docs;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.json.annotations.JSON;

import com.libweb.common.CommBase;
import com.libweb.dao.HibDao;

import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.YDTable;
import com.kesd.dao.SqlPage;
import com.kesd.dbpara.RtuPayCtrl;
import com.kesd.dbpara.ZjgPaypara;
import com.kesd.util.Rd;


public class ActYffParaZ {
	
	private String 		result;
	private String 		pageNo;		//页号
	private ZjgPaypara 	zjgpaypara;	//总加组档案
	private RtuPayCtrl	rtuPayCtrl;	//预付费时间，快速建档时使用
	
	private String	 	field;
	private String   	pageSize;
	private String   	pl;
	private int         count=0;

	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : addOrEdit
	* <p>
	* <p>DESCRIPTION : 添加或修改测点档案记录
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
			if(zjgpaypara.getRtuId() == null || zjgpaypara.getRtuId() == -1) {
				result = SDDef.FAIL;
				return SDDef.SUCCESS;
			}
			
			if(zjgpaypara.getZjgId() == null) {
				result = SDDef.FAIL;
				return SDDef.SUCCESS;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		if (hib_dao.saveOrUpdate(zjgpaypara)) {
			result = SDDef.SUCCESS;
		}else{
			result = SDDef.FAIL;
		}
		return SDDef.SUCCESS;
	}	
	
	public String plEdit(){
		if(pl == null || pl.isEmpty()){
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		HibDao  hib_dao = new HibDao();
		String rtu_zjg[] = pl.split("_");
		String con = "";
		boolean flag = true;
		for (int i = 0; i < rtu_zjg.length; i++) {
			con = " rtuId=" + rtu_zjg[i].split(",")[0] + " and zjgId=" + rtu_zjg[i].split(",")[1];
			String hql = "update " + YDTable.TABLECLASS_ZJGPAYPARA + " set " + result + " where " + con;
			if (!hib_dao.updateByHql(hql)) {
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
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : delZjgParaById
	* <p>
	* <p>DESCRIPTION : 按mp_id从大到小删除多个数量的计量点档案记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>备注 此处删除N个最大计量点，并且删除与之相关联的计量点 
	* <p>zkz 20110120 2230
	*-----------------------------------------------------------*/
	public String delZjgParaById()
	{
		HibDao  hib_dao = new HibDao();
		if(result==null){
			result=SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		int len = 2;
		String[] tmp = result.split(SDDef.SPLITCOMA);
		String[] id = tmp[0].split(SDDef.JSONSPLIT);
		String[] hql = new String[len*id.length];
		for(int i=0;i<id.length;i++){
			hql[i*len] = "delete from "+YDTable.TABLECLASS_ZJGPAYPARA +" as a where a.zjgId="+id[i]+" and a.rtuId="+tmp[1];
			hql[i*len+1] = "update "+YDTable.TABLECLASS_RTUPARA+" set zjgNum=zjgNum-1 where id=" + tmp[1];
		}
		if(hib_dao.updateByHql(hql)){
			result = SDDef.SUCCESS;
		}else{
			result = SDDef.FAIL;
		}
		return SDDef.SUCCESS;
	}
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : getZjgParaById
	* <p>
	* <p>DESCRIPTION : 通过ID获取总加组档案记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@JSON(serialize = false)
	@SuppressWarnings("unchecked")
	public String getYffParaById() throws Exception
	{
		StringBuffer ret_buf  = new StringBuffer();
		HibDao       hib_dao  = new HibDao();
		String hql = "select rtuId,zjgId,caclType,feectrlType,payType,yffalarmId,feeBegindate,protSt,protEd,ngloprotFlag,keyVersion,cryplinkId,tzVal,feeprojId,feeproj1Id,feeproj2Id,useGfh,hfhTime,hfhShutdown,plusTime,csStand,powrateFlag,prizeFlag,stopFlag,stopBegdate,stopEnddate,payAdd1,payAdd2,payAdd3,yffctrlType,feeChgf,feeChgid,feeChgid1,feeChgid2,feeChgdate,feeChgtime,jbfChgf,jbfChgval,jbfChgdate,jbfChgtime,cbCycleType,cbDayhour,jsDay,fxdfFlag,fxdfBegindate,localMaincalcf,writecardNo,cbjsFlag,cardmeterType from " 
			 + YDTable.TABLECLASS_ZJGPAYPARA + " where zjgId=" + result.split(",")[1] + " and rtuId = "+result.split(",")[0];
		List   list = hib_dao.loadAll(hql);
		Object[] object = (Object[])list.get(0);
		ret_buf.append(SDDef.JSONSELDATA);
		for(int i = 0; i<object.length; i ++) {
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(object[i]) + SDDef.JSONQUOT  + SDDef.JSONCOMA);
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();
		
		if (result == null) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		result = ret_buf.toString();	
		
		return SDDef.SUCCESS;
	}
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : getZjgModelParaById
	* <p>
	* <p>DESCRIPTION : 通过ID获取快速建档页面总加组档案记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@JSON(serialize = false)
	@SuppressWarnings("unchecked")
	public String getZjgModelParaById() throws Exception
	{
		StringBuffer ret_buf  = new StringBuffer();
		HibDao       hib_dao  = new HibDao();
		String hql = "select a.caclType,a.feectrlType,a.payType,a.yffalarmId,a.feeBegindate,a.protSt,a.protEd,a.ngloprotFlag,a.keyVersion,a.cryplinkId,a.tzVal,a.feeprojId,a.feeproj1Id,a.feeproj2Id,a.useGfh,a.hfhTime,a.hfhShutdown,a.plusTime,a.csStand,a.powrateFlag,a.prizeFlag,a.stopFlag,a.stopBegdate,a.stopEnddate,a.payAdd1,a.payAdd2,a.payAdd3,a.yffctrlType,a.feeChgf,a.feeChgid,a.feeChgid1,a.feeChgid2,a.feeChgdate,a.feeChgtime,a.jbfChgf,a.jbfChgval,a.jbfChgdate,a.jbfChgtime,a.cbCycleType,a.cbDayhour,a.jsDay,a.fxdfFlag,a.fxdfBegindate,a.localMaincalcf,a.writecardNo,a.cbjsFlag,a.cardmeterType , b.useFlag, b.yffbgDate, b.yffbgTime, b.sg186bgDate, b.sg186bgTime from " 
			 + YDTable.TABLECLASS_ZJGPAYPARA + " a ," + YDTable.TABLECLASS_RTUPAYCTRL + " b where a.zjgId=" + result.split(",")[0] + " and a.rtuId = "+result.split(",")[1] + " and b.rtuId = " + result.split(",")[1];
		List   list = hib_dao.loadAll(hql);
		Object[] object = (Object[])list.get(0);
		ret_buf.append(SDDef.JSONSELDATA);
		for(int i = 0; i<object.length; i ++) {
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(object[i]) + SDDef.JSONQUOT  + SDDef.JSONCOMA);
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();
		
		if (result == null) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		result = ret_buf.toString();	
		
		return SDDef.SUCCESS;
	}

	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : execute
	* <p>
	* <p>DESCRIPTION : 分页获取总加组档案记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		List         list      = null;
		StringBuffer ret_buf   = new StringBuffer();
		int 		 page	   = Integer.parseInt(pageNo == null? "1" : pageNo);
		int 		 pagesize  = Integer.parseInt(pageSize == null? "10" : pageSize);
		
		String describe   = null;
		String idFromTree = null;
		String param2     = null;
		
		StringBuffer getRtuSql  = new StringBuffer();
		String sql=new String();
		getRtuSql.append(" e.rtu_id zigpayPara_rtuId,e.zjg_id zigpayPara_zigId,d.describe zigpara_desc,e.cacl_type,e.feectrl_type, e.pay_type,e.writecard_no,e.yffalarm_id,alarm.describe alarm_desc, e.prot_st, e.prot_ed,  e.ngloprot_flag, e.pay_add1, e.pay_add2,e.pay_add3,b.describe cons_desc,b.busi_no,b.cont_cap ").
		          append("from ").
		          append("zjgpay_para as e left outer join yffalarmpara alarm on e.yffalarm_id=alarm.id ,zjgpara as d,  rtupara as a ");
		
		StringBuffer countSql   = new StringBuffer();
		countSql.append("select ").
                 append("count(e.zjg_id) ").
                 append("from ").
                 append("zjgpay_para as e left outer join yffalarmpara alarm on e.yffalarm_id=alarm.id ,zjgpara as d,  rtupara as a ");
		
		if(result != null && !result.isEmpty()) {
			JSONObject jsonObj  = JSONObject.fromObject(result);
			describe   = jsonObj.getString("describe");
			param2     = jsonObj.getString("id");
			int pos = param2.indexOf("_");
			idFromTree = param2.substring(pos + 1);
			
			if (param2.equals(SDDef.GLOBAL_KE2)) {
				getRtuSql.append( ", conspara as b, orgpara as c where a.cons_id=b.id and b.app_type="+SDDef.APPTYPE_ZB+" and b.org_id=c.id " );
				countSql.append ( ", conspara as b, orgpara as c where a.cons_id=b.id and b.app_type="+SDDef.APPTYPE_ZB+" and b.org_id=c.id ") ;
				
			} else if (param2.startsWith(YDTable.TABLECLASS_ORGPARA)) {
				
				getRtuSql.append( ", conspara as b, orgpara as c where a.cons_Id=b.id and b.app_type=" +SDDef.APPTYPE_ZB+" and b.org_Id=c.id and c.id=" + idFromTree );
				countSql.append ( ", conspara as b, orgpara as c where a.cons_Id=b.id and b.app_type=" +SDDef.APPTYPE_ZB+" and b.org_Id=c.id and c.id=" + idFromTree);
				
			} else if (param2.startsWith(YDTable.TABLECLASS_LINEFZMAN)) {
				
				getRtuSql.append( ", conspara as b where a.cons_Id=b.id and b.app_type="+SDDef.APPTYPE_ZB+" and  b.line_FzMan_Id=" + idFromTree );
				countSql.append ( ",  conspara as b where a.cons_Id=b.id and b.app_type="+SDDef.APPTYPE_ZB+" and  b.line_FzMan_Id=" + idFromTree ) ;
				
			} else if (param2.startsWith(YDTable.TABLECLASS_CONSPARA)){
				
				getRtuSql.append( ", conspara as b where a.cons_Id=b.id and b.id=" + idFromTree );
				countSql.append ( ", conspara as b where a.cons_Id=b.id and b.id=" + idFromTree );
			
			} else {
				
				getRtuSql.append( ", conspara as b  where a.cons_id=b.id and a.id=" + idFromTree );
			    countSql.append ( ", conspara as b  where a.cons_id=b.id and a.id=" + idFromTree );
				
			}
		}
		
		getRtuSql.append(" and d.yff_Flag = 1 and d.use_Flag = 1 and  e.rtu_Id = d.rtu_Id and e.rtu_Id = a.id and e.zjg_Id = d.zjg_Id  ");
		countSql.append( " and d.yff_Flag = 1 and d.use_Flag = 1 and  e.rtu_Id = d.rtu_Id and e.rtu_Id = a.id and e.zjg_Id = d.zjg_Id  ");
		
		if(describe != null && !describe.isEmpty()){
			getRtuSql.append( " and d.describe like '%" + describe + "%'");
			countSql.append ( " and d.describe like '%" + describe + "%'");
		}
		getRtuSql.append(" order by e.rtu_id desc ) x order by x.zigpayPara_rtuId" );
		SqlPage sqlPage = new SqlPage(page, pagesize);
		sqlPage.setTotalrecords(countSql.toString());
		
		count = sqlPage.getTotalrecords();
		sql=getRtuSql.toString();
		
		if(pagesize == 0){
			 sql="select top "+count+" * from (select top "+ count +" " + sql;
		}else{
			if(pagesize > count){
				sql = "select top "+pagesize+" * from (select top "+ count +" " + sql;
			}else{
				sql = "select top "+pagesize+" * from (select top "+(count - pagesize*(page-1)) + sql;
			}
		}
		if(sqlPage.getTotalrecords()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		if (pagesize == 0) {
			pagesize = sqlPage.getTotalrecords();
			sqlPage.setPagesize(pagesize);
		}
		ret_buf.append(SDDef.JSONTOTAL + sqlPage.getTotalrecords() + SDDef.JSONPAGEROWS);
		
		list = sqlPage.getRecord(sql);
		int no = pagesize*(sqlPage.getCurrentpage()-1)+1;
		
		Map<Integer, String> cacl_type = Rd.getDict(Dict.DICTITEM_FEETYPE);
		Map<Integer, String> feectrl_type = Rd.getDict(Dict.DICTITEM_PREPAYTYPE);
		Map<Integer, String> pay_type = Rd.getDict(Dict.DICTITEM_PAYTYPE);
		Map<Integer, String> ngloprot_flag = Rd.getDict(Dict.DICTITEM_YESFLAG);
		
		for (int i = 0; i < list.size(); i++) {
			ret_buf.append(SDDef.JSONLBRACES);
			Map<String, Object> map =  (Map<String, Object>) list.get(i);
			if(pl == null){
				String id = map.get("zigpayPara_rtuId") + "," + map.get("zigpayPara_zigId") + "," + map.get("cons_desc") + "," + CommBase.CheckString(map.get("busi_no")) + "," + CommBase.CheckString(map.get("cont_cap"));
				ret_buf.append(SDDef.JSONQUOT + "id" +   SDDef.JSONQACQ + id +  SDDef.JSONNZDATA); 	//i++;i=2
				ret_buf.append(SDDef.JSONQUOT + (no++) +  SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT +  "<a href='javascript:redoOnRowDblClicked();'>" + CommBase.CheckString(map.get("zigpara_desc")) + "</a>" + SDDef.JSONCCM);
			}else{
				ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + map.get("zigpayPara_rtuId") + "," +  map.get("zigpayPara_zigId") +  SDDef.JSONDATA);//i++;i=2
				ret_buf.append(SDDef.JSONQUOT + (no++) +  SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("zigpara_desc")) + SDDef.JSONCCM);
			}
			String tmp = CommBase.CheckString(map.get("cacl_type"));
			int itmp = CommBase.strtoi(tmp);
			ret_buf.append(SDDef.JSONQUOT + cacl_type.get(itmp)  + SDDef.JSONCCM);//i=4
			
			tmp = CommBase.CheckString(map.get("feectrl_type"));
			itmp = CommBase.strtoi(tmp);
			ret_buf.append(SDDef.JSONQUOT + feectrl_type.get(itmp)  + SDDef.JSONCCM);//i=5
			
			tmp = CommBase.CheckString(map.get("pay_type"));
			itmp = CommBase.strtoi(tmp);
			ret_buf.append(SDDef.JSONQUOT + pay_type.get(itmp)  + SDDef.JSONCCM);//i=6
			
			if(pl == null || pl.isEmpty()){
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("writecard_no")) + SDDef.JSONCCM);
			}
			
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("alarm_desc")) + SDDef.JSONCCM);
			
			if(map.get("prot_st") == null){//此时i=7
				ret_buf.append(SDDef.JSONQUOT + "" + SDDef.JSONCCM);	//i=8 i++;
			}
			else {
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("prot_st"))+"点" + SDDef.JSONCCM);//i=8
			}
			if(map.get("prot_ed") == null){//i=8
				ret_buf.append(SDDef.JSONQUOT + "" + SDDef.JSONCCM);//i=9	
			}
			else {
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("prot_ed"))+"点" + SDDef.JSONCCM);//i=9
			}
			
			tmp = CommBase.CheckString(map.get("ngloprot_flag"));
			itmp = CommBase.strtoi(tmp);
			ret_buf.append(SDDef.JSONQUOT + ngloprot_flag.get(itmp)  + SDDef.JSONCCM);//i=10
			
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("pay_add1")) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("pay_add2")) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("pay_add3")) + SDDef.JSONCCM);
			
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRBRBR);
		result = ret_buf.toString();
		
		return SDDef.SUCCESS;
	}
	
	//快速建档添加
	public String fastAddDoc(){
		HibDao  hib_dao = new HibDao();
		try {
			if(zjgpaypara.getRtuId() == null || zjgpaypara.getRtuId() == -1 || rtuPayCtrl.getRtuId() == null || rtuPayCtrl.getRtuId() == -1) {
				result = SDDef.FAIL;
				return SDDef.SUCCESS;
			}
			
			if(zjgpaypara.getZjgId() == null) {
				result = SDDef.FAIL;
				return SDDef.SUCCESS;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		Object [] obj = {zjgpaypara,rtuPayCtrl};
		if (hib_dao.saveOrUpdate(obj)) {
			result = SDDef.SUCCESS;
		}else{
			result = SDDef.FAIL;
		}
		return SDDef.SUCCESS;
	}	
	
	//----------------------------get/set-----------------------------
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
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

	public ZjgPaypara getZjgpaypara() {
		return zjgpaypara;
	}

	public void setZjgpaypara(ZjgPaypara zjgpaypara) {
		this.zjgpaypara = zjgpaypara;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public void setPl(String pl) {
		this.pl = pl;
	}

	public String getPl() {
		return pl;
	}

	public RtuPayCtrl getRtuPayCtrl() {
		return rtuPayCtrl;
	}

	public void setRtuPayCtrl(RtuPayCtrl rtuPayCtrl) {
		this.rtuPayCtrl = rtuPayCtrl;
	}
	
}

