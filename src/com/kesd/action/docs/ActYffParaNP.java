package com.kesd.action.docs;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import net.sf.json.JSONObject;

import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.YDTable;
import com.kesd.dao.HibPage;
import com.kesd.dao.SqlPage;
import com.kesd.dbpara.MeterExtparaNP;
import com.kesd.dbpara.MeterPara;
import com.kesd.dbpara.MpPara;
import com.kesd.dbpara.NppayPara;
import com.kesd.dbpara.YffAlarmPara;
import com.kesd.dbpara.YffRatePara;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
import com.libweb.dao.HibDao;

public class ActYffParaNP {
	private String 			result;
	private String 			pageNo;		//页号
	private String 			yffState;   //预付费使用标志
	private NppayPara		nppaypara;	//农排预付费参数
	private MpPara			mppara;
	private MeterExtparaNP	meterextparanp;
	private MeterPara		meterpara;
	private String	 		field;
	private String   		pageSize;
	private String   		pl;			//批量参数
	private int         	count=0;	
	private String			params;
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : execute
	* <p>
	* <p>DESCRIPTION : 分页获取测量点费控记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		List list 			= null;
		String describe 	= null;  //名称查询条件
		String idFromTree 	= null;  //树中获取的id
		String param2		= null;
		StringBuffer ret_buf = new StringBuffer();//查询结果组成的数据
		
		int page = Integer.parseInt(pageNo == null? "1" : pageNo);
		int pagesize = Integer.parseInt(pageSize == null? "10" : pageSize);//默认每页10条记录
		
		StringBuffer sql = new StringBuffer();
		sql.append("select n.rtuId, n.mpId, r.describe, m.describe, n.powLimit, n.nocycutFlag, n.nocycutMin, n.powerupProt, n.keyVersion, n.cryplinkId from ");
		sql.append(YDTable.TABLECLASS_RTUPARA + " as r,");
		sql.append(YDTable.TABLECLASS_METERPARA + " as m,");
		sql.append(YDTable.TABLECLASS_NPPAYPARA + " as n ");
		
		StringBuffer countsql = new StringBuffer();
		countsql.append("select count(n.mpId) from ");
		countsql.append(YDTable.TABLECLASS_RTUPARA + " as r,");
		countsql.append(YDTable.TABLECLASS_METERPARA + " as m,");
		countsql.append(YDTable.TABLECLASS_NPPAYPARA + " as n ");
		
		//根据左侧导航树不同层次进行查询条件的选择
		if(result != null && !result.isEmpty()){
			JSONObject jsonObj = JSONObject.fromObject(result);
			describe	=	jsonObj.getString("describe");
			param2	= jsonObj.getString("id");
			int pos = param2.indexOf("_");
			idFromTree = param2.substring(pos+1);
			
			if(param2.equals(SDDef.GLOBAL_KE2)){
				sql.append("," + YDTable.TABLECLASS_CONSPARA + " as b, " + YDTable.TABLECLASS_ORGPARA + " as c where r.consId=b.id and b.appType="+SDDef.APPTYPE_NP+" and b.orgId=c.id ");
				countsql.append("," + YDTable.TABLECLASS_CONSPARA + " as b, " + YDTable.TABLECLASS_ORGPARA + " as c where r.consId=b.id and b.appType="+SDDef.APPTYPE_NP+" and b.orgId=c.id ");
			}else if(param2.startsWith(YDTable.TABLECLASS_ORGPARA)){
				sql.append("," + YDTable.TABLECLASS_CONSPARA + " as b, " + YDTable.TABLECLASS_ORGPARA + " as c where r.consId=b.id and b.appType="+SDDef.APPTYPE_NP+" and b.orgId=c.id and c.id=" + idFromTree);
				countsql.append("," + YDTable.TABLECLASS_CONSPARA + " as b, " + YDTable.TABLECLASS_ORGPARA + " as c where r.consId=b.id and b.appType="+SDDef.APPTYPE_NP+" and b.orgId=c.id and c.id=" + idFromTree);
			}else if(param2.startsWith(YDTable.TABLECLASS_LINEFZMAN)){
				sql.append("," + YDTable.TABLECLASS_CONSPARA + " as b where r.consId=b.id and b.appType="+SDDef.APPTYPE_NP+" and  b.lineFzManId=" + idFromTree);
				countsql.append("," + YDTable.TABLECLASS_CONSPARA + " as b where r.consId=b.id and b.appType="+SDDef.APPTYPE_NP+" and  b.lineFzManId=" + idFromTree);
			}else if(param2.startsWith(YDTable.TABLECLASS_CONSPARA)){
				sql.append("," + YDTable.TABLECLASS_CONSPARA + " as b where r.consId=b.id and b.id=" + idFromTree);
				countsql.append("," + YDTable.TABLECLASS_CONSPARA + " as b where r.consId=b.id and b.id=" + idFromTree);
			}else{
				sql.append(" where r.id=" + idFromTree);
				countsql.append(" where r.id=" + idFromTree);
			}
		}
		
		//根据使用标志查询条件
		if(CommBase.strtoi(yffState) == 0){
			sql.append(" and r.id = m.rtuId and n.rtuId = r.id and n.mpId = m.mpId ");
			//sql.append(" and r.id = m.rtuId ");
			countsql.append(" and r.id = m.rtuId and n.rtuId = r.id and n.mpId = m.mpId ");
			//countsql.append(" and r.id = m.rtuId ");
		}else if(CommBase.strtoi(yffState) == 1){
			sql.append(" and m.prepayflag = 1 and r.id = m.rtuId and n.rtuId = r.id and n.mpId = m.mpId ");
			countsql.append(" and m.prepayflag=1 and r.id = m.rtuId and n.rtuId = r.id and n.mpId = m.mpId ");
		}else if(CommBase.strtoi(yffState) == 2){
			sql.append(" and m.prepayflag = 0 and r.id = m.rtuId and n.rtuId = r.id and n.mpId = m.mpId ");
			countsql.append(" and m.prepayflag = 0 and r.id = m.rtuId and n.rtuId = r.id and n.mpId = m.mpId ");
		}
		
		if(describe != null && !describe.isEmpty()){
			sql.append(" and m.describe like '%" + describe + "%'" );
			countsql.append(" and m.describe like '%" + describe + "%'" );
		}
		
		String hql = sql.toString();
		String counthql = countsql.toString();
		
		HibPage hib_page = new HibPage(page, pagesize);
		hib_page.setTotalrecords(counthql);
		
		if(pagesize == 0){
			pagesize = hib_page.getTotalrecords();
			hib_page.setPagesize(pagesize);
		}
		
		if(hib_page.getTotalrecords() == 0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		hql += " order by r.id";
		list = hib_page.getRecord(hql);
		
		int no = pagesize*(hib_page.getCurrentpage()-1)+1;
		ret_buf.append(SDDef.JSONTOTAL + hib_page.getTotalrecords() + SDDef.JSONPAGEROWS);
		Iterator it = list.iterator();
		
		Map<Integer, String> sdbh_flag = Rd.getDict(Dict.DICTITEM_YESFLAG);
		Map<Integer, String> wcyzddd_flag = Rd.getDict(Dict.DICTITEM_YESFLAG);
		Map<Integer, String> wcyzdddsj = Rd.getDict(Dict.DICTITEM_NOCYCUT_MIN);
		
		while(it.hasNext()){
			int i=0;
			Object[] obj = (Object[])it.next();
			ret_buf.append(SDDef.JSONLBRACES);
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + (obj[0]+"_"+obj[1]) +  SDDef.JSONNZDATA); i++;	//i+1
			ret_buf.append(SDDef.JSONQUOT + (no++)										+  SDDef.JSONCCM); i++;		//i+1
			
			ret_buf.append(SDDef.JSONQUOT +  obj[2] + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT +  "<a href='javascript:redoOnRowDblClicked(&quot;"+ obj[1]+","+obj[0]+"," + CommBase.CheckString(obj[3]) +"&quot;);'>" + CommBase.CheckString(obj[3]) + "</a>" + SDDef.JSONCCM);
			i = 4;
			
			ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(obj[i++]) + SDDef.JSONCCM);	//限定功率
			
			String tmp = CommBase.CheckString(obj[i++]);
			int itmp = CommBase.strtoi(tmp);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(wcyzddd_flag.get(itmp)) + SDDef.JSONCCM);	//无采样自动断电功能  
			
			tmp = CommBase.CheckString(obj[i++]);
			itmp = CommBase.strtoi(tmp);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(wcyzdddsj.get(itmp)) + SDDef.JSONCCM);	//无采样自动断电时间
			
			tmp = CommBase.CheckString(obj[i++]);
			itmp = CommBase.strtoi(tmp);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(sdbh_flag.get(itmp)) + SDDef.JSONCCM);	//上电保护功能
			
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(obj[i++]) + SDDef.JSONCCM);	//密钥版本
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(obj[i++]) + SDDef.JSONCCM);	//所属加密机ID
			
//			ret_buf.append(SDDef.JSONQUOT + obj[i++] + SDDef.JSONCCM);  //费率方案  
//			ret_buf.append(SDDef.JSONQUOT + obj[i++] + SDDef.JSONCCM);	//报警方案
//			ret_buf.append(SDDef.JSONQUOT + formtYMD(obj[i++].toString()) + SDDef.JSONCCM);	//费率启用日期
			
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);
		}
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRBRBR);
		result = ret_buf.toString();
		return "success";
	}
	
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : getyffparapl
	* <p>
	* <p>DESCRIPTION : 分页获取测量点费控记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@JSON(serialize = false)
	@SuppressWarnings("unchecked")
	public String getyffparapl(){
		List list 			= null;
		String describe 	= null;  //名称查询条件
		String idFromTree 	= null;  //树中获取的id
		String param2		= null;
		StringBuffer ret_buf = new StringBuffer();//查询结果组成的数据
		
		int page = Integer.parseInt(pageNo == null? "1" : pageNo);
		int pagesize = Integer.parseInt(pageSize == null? "10" : pageSize);//默认每页10条记录
		
		StringBuffer sql = new StringBuffer();
		sql.append("select n.rtu_id, n.mp_id, r.describe as r_desc, m.describe as m_desc, y.describe as y_desc, a.describe as a_desc, n.fee_begindate, n.pow_limit, n.nocycut_flag, n.nocycut_min, n.powerup_prot, n.key_version, n.cryplink_id from ");
		sql.append(" rtupara as r,");
		sql.append(" meterpara as m,");
		sql.append(" yffratepara as y right join ");
		sql.append(" nppay_para as n on y.id = n.feeproj_id left join ");
		sql.append(" yffalarmpara as a on a.id = n.yffalarm_id ");
		
		StringBuffer countsql = new StringBuffer();
		countsql.append("select count(n.mp_id) from ");
		countsql.append(" rtupara as r,");
		countsql.append(" meterpara as m,");
		countsql.append(" yffratepara as y right join ");
		countsql.append(" nppay_para as n on y.id = n.feeproj_id left join ");
		countsql.append(" yffalarmpara as a on a.id = n.yffalarm_id ");
		
		//根据左侧导航树不同层次进行查询条件的选择
		if(result != null && !result.isEmpty()){
			JSONObject jsonObj = JSONObject.fromObject(result);
			describe	=	jsonObj.getString("describe");
			param2	= jsonObj.getString("id");
			int pos = param2.indexOf("_");
			idFromTree = param2.substring(pos+1);
			
			if(param2.equals(SDDef.GLOBAL_KE2)){
				sql.append(", conspara as b,  orgpara as c where r.cons_id=b.id and b.app_type =" +SDDef.APPTYPE_NP + " and b.org_id=c.id ");
				countsql.append(",conspara as b, orgpara  as c where r.cons_id=b.id and b.app_type =" +SDDef.APPTYPE_NP + " and b.org_id=c.id ");
			}else if(param2.startsWith(YDTable.TABLECLASS_ORGPARA)){
				sql.append(", conspara as b, orgpara as c where r.cons_id=b.id and b.app_type="+SDDef.APPTYPE_NP+" and b.org_id=c.id and c.id=" + idFromTree);
				countsql.append(",conspara as b, orgpara as c where r.cons_id=b.id and b.app_type="+SDDef.APPTYPE_NP+" and b.org_id=c.id and c.id=" + idFromTree);
			}else if(param2.startsWith(YDTable.TABLECLASS_LINEFZMAN)){
				sql.append(",conspara as b where r.cons_id=b.id and b.app_type="+SDDef.APPTYPE_NP+" and  b.line_fzman_id=" + idFromTree);
				countsql.append(",conspara as b where r.cons_id=b.id and b.app_type="+SDDef.APPTYPE_NP+" and  b.line_fzman_id=" + idFromTree);
			}else if(param2.startsWith(YDTable.TABLECLASS_CONSPARA)){
				sql.append(",conspara as b where r.cons_id=b.id and b.id=" + idFromTree);
				countsql.append(",conspara as b where r.cons_id=b.id and b.id=" + idFromTree);
			}else{
				sql.append(" where r.id=" + idFromTree);
				countsql.append(" where r.id=" + idFromTree);
			}
		}
		
		//m.prepayflag = 1 查询出使用预付费的表
		sql.append(" and m.prepayflag = 1 and r.id = m.rtu_id and n.rtu_id = r.id and n.mp_id = m.mp_id ");
		countsql.append(" and m.prepayflag = 1 and r.id = m.rtu_id and n.rtu_id = r.id and n.mp_id = m.mp_id ");
		
		if(describe != null && !describe.isEmpty()){
			sql.append(" and m.describe like '%" + describe + "%'" );
			countsql.append(" and m.describe like '%" + describe + "%'" );
		}
		
		String hql = sql.toString();
		String counthql = countsql.toString();
		
		SqlPage sqlPage = new SqlPage(page, pagesize);
		sqlPage.setTotalrecords(counthql);
		
		if(pagesize == 0){
			pagesize = sqlPage.getTotalrecords();
			sqlPage.setPagesize(pagesize);
		}
		
		if(sqlPage.getTotalrecords() == 0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		hql += " order by r.id";
		list = sqlPage.getRecord(hql);
		
		int no = pagesize*(sqlPage.getCurrentpage()-1)+1;
		ret_buf.append(SDDef.JSONTOTAL + sqlPage.getTotalrecords() + SDDef.JSONPAGEROWS);
		
		Map<Integer, String> sdbh_flag 		= Rd.getDict(Dict.DICTITEM_YESFLAG);
		Map<Integer, String> wcyzddd_flag 	= Rd.getDict(Dict.DICTITEM_YESFLAG);
		Map<Integer, String> wcyzdddsj 		= Rd.getDict(Dict.DICTITEM_NOCYCUT_MIN);
		
		//数据字典中的映射值
		for(int i=0; i<list.size(); i++){
			Map<String, Object> map =  (Map<String, Object>) list.get(i);
			ret_buf.append(SDDef.JSONLBRACES);
			
			String id = map.get("rtu_id") + "_" + map.get("mp_id");
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + id +  SDDef.JSONDATA); 
			ret_buf.append(SDDef.JSONQUOT + (no++)									+  SDDef.JSONCCM); 	//序号
			
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckString(map.get("r_desc"))  + SDDef.JSONCCM); //终端名称
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckString(map.get("m_desc"))  + SDDef.JSONCCM); //农排表名称
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckString(map.get("y_desc"))  + SDDef.JSONCCM); //费率方案
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckString(map.get("a_desc"))  + SDDef.JSONCCM); //报警方案			
		
			ret_buf.append(SDDef.JSONQUOT +  CommFunc.FormatToYMD(CommBase.CheckString(map.get("fee_begindate")))  + SDDef.JSONCCM);//费率启用日期
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckString(map.get("pow_limit"))  + SDDef.JSONCCM); //限定功率
			
			String tmp = CommBase.CheckString(map.get("nocycut_flag"));
			int itmp = CommBase.strtoi(tmp);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(wcyzddd_flag.get(itmp)) + SDDef.JSONCCM);	//无采样自动断电功能
			
			tmp = CommBase.CheckString(map.get("nocycut_min"));
			itmp = CommBase.strtoi(tmp);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(wcyzdddsj.get(itmp)) + SDDef.JSONCCM);		//无采样自动断电时间
			
			tmp = CommBase.CheckString(map.get("powerup_prot"));
			itmp = CommBase.strtoi(tmp);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(sdbh_flag.get(itmp)) + SDDef.JSONCCM);		//上电保护功能

			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("key_version")) + SDDef.JSONCCM);	//密钥版本
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("cryplink_id")) + SDDef.JSONCCM);	//所属加密机ID
			
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);
		}
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRBRBR);
		result = ret_buf.toString();
		return "success";
	}
	/*
	 * 根据ID获取费控参数信息(非批量)
	 * */
	@JSON(serialize = false)
	@SuppressWarnings("unchecked")
	public String getYffParaById() throws Exception{
		if (result == null) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		StringBuffer ret_buf = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		HibDao hib_dao = new HibDao();
	
		String paras[] = result.split(SDDef.JSONCOMA);//paras[0]:mpid, paras[1]:mpid
		//area.describe, m.commAddr, m.meterId, mp.ctRatio, m.comm_pwd 
		sql.append("select m_ext.areaId, a.describe, m.commAddr, m.meterId, mp.ctRatio, mp.ctNumerator,mp.ctDenominator,m.commPwd, n.powLimit,n.keyVersion,n.nocycutFlag,n.powerupProt,n.cryplinkId,n.yffmeterType, n.nocycutMin, n.feeprojId, n.yffalarmId, n.feeBegindate from ");
		sql.append(YDTable.TABLECLASS_NPPAYPARA + " as n, ");
		sql.append(YDTable.TABLECLASS_MPPARA + " as mp, ");
		sql.append(YDTable.TABLECLASS_AREAPARA + " as a, ");
		sql.append(YDTable.TABLECLASS_METER_EXTPARANP + " as m_ext, ");
		sql.append(YDTable.TABLECLASS_METERPARA + " as m ");
		//m_ext.rtu_id = n.rtu_id and m_ext.mp_id = n.mp_id and m_ext.area_id = a.id and 
		//m.rtu_id = n.rtu_id and m.mp_id = n.mp_id and mp.rtu_id = m.rtu_id and mp.id = m.mp_id
		sql.append(" where n.rtuId= ");
		sql.append(paras[1]);
		sql.append(" and n.mpId= ");
		sql.append(paras[0]);
		sql.append(" and m_ext.rtuId = n.rtuId and m_ext.mpId = n.mpId and m_ext.areaId = a.id and ");
		sql.append("m.rtuId = n.rtuId and m.mpId = n.mpId and mp.rtuId = m.rtuId and mp.id = m.mpId");
		
		String hql = sql.toString();
		List   list = hib_dao.loadAll(hql);
		Object[] object = (Object[])list.get(0);
		
		ret_buf.append(SDDef.JSONSELDATA);
		for(int i = 0; i<object.length-1; i ++) {
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(object[i]) + SDDef.JSONQUOT  + SDDef.JSONCOMA);
		}
		
		String feeprojId = CommBase.CheckString(object[object.length - 3]);
		String yffalarmId = CommBase.CheckString(object[object.length - 2]);
		String feeprojDesc = null;
		String yffalarmDesc = null;
		
		if (feeprojId != "" && !"0".equals(feeprojId)) {
			Object obj1 = Rd.getRecord(YDTable.TABLECLASS_YFFRATEPARA, Short.parseShort(feeprojId));
			if(obj1 != null) feeprojDesc = Rd.getYffRateDesc((YffRatePara)obj1);
		}
		else {
			//默认选中第一个,与js下拉列表位置要匹配
			feeprojDesc = "未知费率类型";
		}
		if (yffalarmId != "" && !"0".equals(yffalarmId)) {
			Object obj2 = Rd.getRecord(YDTable.TABLECLASS_YFFALARMPARA, Short.parseShort(yffalarmId));
			if(obj2 != null) yffalarmDesc = Rd.getYffAlarmDesc((YffAlarmPara)obj2);
		}
		else {
			yffalarmDesc = "未知报警类型";
		}
		ret_buf.append(SDDef.JSONQUOT + feeprojDesc + SDDef.JSONQUOT  + SDDef.JSONCOMA);
		ret_buf.append(SDDef.JSONQUOT + yffalarmDesc + SDDef.JSONQUOT  + SDDef.JSONCOMA);	
		ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(object[object.length-1]) + SDDef.JSONQUOT  + SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
	
		result = ret_buf.toString();
		return SDDef.SUCCESS;
	}
	
	/*
	 * 根据ID获取费控参数信息(批量查询时使用)
	 * */
	@JSON(serialize = false)
	@SuppressWarnings("unchecked")
	public String getYffParaByPlId() throws Exception{
		if (result == null) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		StringBuffer ret_buf = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		HibDao hib_dao = new HibDao();
	
		String paras[] = result.split(SDDef.JSONCOMA);//paras[0]:mpid, paras[1]:mpid
		sql.append("select m_ext.areaId, a.describe,mp.ctRatio, mp.ctNumerator,mp.ctDenominator,m.commPwd, n.feeprojId,n.yffalarmId,n.feeBegindate,n.powLimit,n.keyVersion,n.nocycutFlag,n.powerupProt,n.cryplinkId,n.nocycutMin from ");
		sql.append(YDTable.TABLECLASS_NPPAYPARA + " as n, ");
		sql.append(YDTable.TABLECLASS_MPPARA + " as mp, ");
		sql.append(YDTable.TABLECLASS_AREAPARA + " as a, ");
		sql.append(YDTable.TABLECLASS_METER_EXTPARANP + " as m_ext, ");
		sql.append(YDTable.TABLECLASS_METERPARA + " as m ");
		sql.append(" where n.rtuId= ");
		sql.append(paras[1]);
		sql.append(" and n.mpId= ");
		sql.append(paras[0]);
		sql.append(" and m_ext.rtuId = n.rtuId and m_ext.mpId = n.mpId and m_ext.areaId = a.id and ");
		sql.append("m.rtuId = n.rtuId and m.mpId = n.mpId and mp.rtuId = m.rtuId and mp.id = m.mpId");
		
		String hql = sql.toString();
		List   list = hib_dao.loadAll(hql);
		Object[] object = (Object[])list.get(0);
		
		ret_buf.append(SDDef.JSONSELDATA);
		for(int i = 0; i<object.length-1; i ++) {
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(object[i]) + SDDef.JSONQUOT  + SDDef.JSONCOMA);
		}
		
		String feeprojId = CommBase.CheckString(object[6]);//写死了，不好
		String yffalarmId = CommBase.CheckString(object[7]);
		String feeprojDesc = null;
		String yffalarmDesc = null;
		
		if(feeprojId != "" && !"0".equals(feeprojId)){
			Object obj1 = Rd.getRecord(YDTable.TABLECLASS_YFFRATEPARA, Short.parseShort(feeprojId));
			if(obj1 != null)	feeprojDesc = Rd.getYffRateDesc((YffRatePara)obj1);
		}
		else{
			feeprojDesc = "未知费率类型";
		}
		
		if (yffalarmId != "" && !"0".equals(yffalarmId)) {
			Object obj2 = Rd.getRecord(YDTable.TABLECLASS_YFFALARMPARA, Short.parseShort(yffalarmId));
			if(obj2 != null)  yffalarmDesc = Rd.getYffAlarmDesc((YffAlarmPara)obj2);
		}
		else {
			yffalarmDesc = "未知报警类型";
		}
		
		ret_buf.append(SDDef.JSONQUOT + feeprojDesc + SDDef.JSONQUOT  + SDDef.JSONCOMA);
		ret_buf.append(SDDef.JSONQUOT + yffalarmDesc + SDDef.JSONQUOT  + SDDef.JSONCOMA);	
		ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(object[object.length-1]) + SDDef.JSONQUOT  + SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
	
		result = ret_buf.toString();
		return SDDef.SUCCESS;
	}
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : Edit
	* <p>
	* <p>DESCRIPTION :修改测点档案记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	public String edit(){
		HibDao hib_dao = new HibDao();
		Integer rtuId = nppaypara.getRtuId();
		Short	mpId  = nppaypara.getMpId(); 
		if(rtuId == null || rtuId == -1){
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		
		if(mpId == null){
			result = SDDef.FAIL;
			return SDDef.SUCCESS; 
		}
		
		String[] hql = new String[4];
		
		hql[0] = "update " + YDTable.TABLECLASS_NPPAYPARA + " set powLimit = " + nppaypara.getPowLimit() + 
				 ",keyVersion = " + nppaypara.getKeyVersion() + ",nocycutFlag = " + 
				 nppaypara.getNocycutFlag() + ",powerupProt = " + nppaypara.getPowerupProt() + ",cryplinkId = " + 
				 nppaypara.getCryplinkId() + ",nocycutMin = " + nppaypara.getNocycutMin() + ",feeprojId = " + nppaypara.getFeeprojId()+ ",yffmeterType="+nppaypara.getYffmeterType() + 
				 ",yffalarmId =" + nppaypara.getYffalarmId() + ",feeBegindate = "+ nppaypara.getFeeBegindate() + " where rtuId = " + 
				 rtuId + " and mpId=" + mpId;
		hql[1] = "update " + YDTable.TABLECLASS_METER_EXTPARANP + " set areaId = " + meterextparanp.getAreaId() + 
				" where rtuId = " + rtuId + " and mpId=" + mpId;
		hql[2] = "update " + YDTable.TABLECLASS_MPPARA + " set ctRatio = " + mppara.getCtRatio() + 
				 ",ctNumerator = " + mppara.getCtNumerator()+ ",ctDenominator = " + mppara.getCtDenominator() + 
				 " where rtuId = " + rtuId + " and id = " + mpId;
		hql[3] = "update " + YDTable.TABLECLASS_METERPARA + " set commAddr = '" + meterpara.getCommAddr() + 
				 "',meterId = '"+ meterpara.getMeterId() +  "',commPwd = '" + meterpara.getCommPwd() + 
				 "' where rtuId = " + rtuId + " and mpId=" + mpId;
		
		if (hib_dao.updateByHql(hql)) {
			result = SDDef.SUCCESS;
		}else{
			result = SDDef.FAIL;
		}
		return SDDef.SUCCESS;
	}
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : plEdit
	* <p>
	* <p>DESCRIPTION : 批量修改农排费控参数记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	public String plEdit(){
		
		if(pl.isEmpty() || pl == null){
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		HibDao hib_dao = new HibDao();
		String rtu_mp_id[] = pl.split(SDDef.JSONCOMA);//存储每一个rtuId_mpId组合
		String[] hql = new String[4];
		
		String areaId = "";
		String ctNumerator = ""; 
		String ctDenominator = "";
		String ctRatio = "";
		String commPwd = "";
		
		String[] temppara = params.split(",");
			if(temppara.length != 0){
				areaId = temppara[0];
				ctNumerator = temppara[1];
				ctDenominator = temppara[2];
				ctRatio = temppara[3];
				commPwd = temppara[4];
			}
		boolean flag = true;
		for(int i=0; i<rtu_mp_id.length; i++){
			String con = "rtuId = " + rtu_mp_id[i].split("_")[0] + " and mpId = " + rtu_mp_id[i].split("_")[1];
			int tmp = 0;
			
			if(result.isEmpty()){
				hql[0] = null;
			}else{
				StringBuffer sql = new StringBuffer();
				sql.append("update ");
				sql.append(YDTable.TABLECLASS_NPPAYPARA);
				sql.append(" set ");
				sql.append(result);
				sql.append(" where ");
				sql.append(con);
				hql[0] = sql.toString();
				tmp++;
			}
			
				hql[1] = "update " + YDTable.TABLECLASS_METER_EXTPARANP + " set areaId = " + areaId + " where " + con;
				tmp++;
			
				hql[2] = "update " + YDTable.TABLECLASS_METERPARA + " set commPwd = '" + commPwd + "' where " + con;
				tmp++;
			
				hql[3] = "update " + YDTable.TABLECLASS_MPPARA + " set ctRatio = " + ctRatio + ",ctNumerator = " + ctNumerator + 
				 		 ",ctDenominator = " + ctDenominator + " where rtuId = " + rtu_mp_id[i].split("_")[0] + " and id = " + 
				 		 rtu_mp_id[i].split("_")[1];
				tmp++;
			
			//将hql1中的非空项复制到hql2
			String[] hql2 = new String[tmp];
			for( int j=0; j<hql.length; j++){
				if(hql[j] != null){
					hql2[--tmp] = hql[j];
				}
			}
			
			if(!hib_dao.updateByHql(hql2)){
				flag = false;
				break;
			}
		}
		
		if(flag == true){
			result = SDDef.SUCCESS;
		}else{
			result = SDDef.FAIL;
		}
		return SDDef.SUCCESS;
	}
	
	/**
	 * 根据feeprojId显示方案描述
	 * @return
	 */
	@JSON(serialize = false)
	public String getFeeprojDesc(){
		if (result == null || result.isEmpty()) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		String feeprojId = result;
		Object obj = Rd.getRecord(YDTable.TABLECLASS_YFFRATEPARA, Short.parseShort(feeprojId));
		if(obj != null){
			result = Rd.getYffRateDesc((YffRatePara)obj);
		}
		else{
			result = "未知费率类型";
		}
		return SDDef.SUCCESS;
	}
	
	/**
	 * 根据yffalarmId显示方案描述
	 * @return
	 */
	@JSON(serialize = false)
	public String getAlarmDesc(){
		if (result == null || result.isEmpty()) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		String yffalarmId = result;
		Object obj = Rd.getRecord(YDTable.TABLECLASS_YFFALARMPARA, Short.parseShort(yffalarmId));
		if(obj != null){
			result = Rd.getYffAlarmDesc((YffAlarmPara)obj);
		}
		else{
			result = "未知报警类型";
		}
		return SDDef.SUCCESS;
	}
	
	//get,set方法
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
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getPl() {
		return pl;
	}
	public void setPl(String pl) {
		this.pl = pl;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	public String getYffState() {
		return yffState;
	}

	public void setYffState(String yffState) {
		this.yffState = yffState;
	}

	public NppayPara getNppaypara() {
		return nppaypara;
	}

	public void setNppaypara(NppayPara nppaypara) {
		this.nppaypara = nppaypara;
	}
	public MpPara getMppara() {
		return mppara;
	}
	public void setMppara(MpPara mppara) {
		this.mppara = mppara;
	}
	public MeterExtparaNP getMeterextparanp() {
		return meterextparanp;
	}
	public void setMeterextparanp(MeterExtparaNP meterextparanp) {
		this.meterextparanp = meterextparanp;
	}
	public MeterPara getMeterpara() {
		return meterpara;
	}
	public void setMeterpara(MeterPara meterpara) {
		this.meterpara = meterpara;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	
}
