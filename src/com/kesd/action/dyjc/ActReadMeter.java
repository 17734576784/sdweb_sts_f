package com.kesd.action.dyjc;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.json.annotations.JSON;

import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.YDTable;
import com.kesd.comntpara.ComntUseropDef;
import com.kesd.dao.SqlPage;
import com.kesd.dbpara.YffManDef;
import com.kesd.dbpara.YffRatePara;
import com.kesd.util.OnlineRtu;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
import com.libweb.dao.HibDao;
import com.libweb.dao.JDBCDao;

public class ActReadMeter {
	private String 			result;
	private String 			operErr				= null;		//返回错误信息描述,在js弹出框中显示
	private String 			rmback;
	private String 			field;
	
	private String 			STR_NULL = "";	//空字符串
	private String 			pageNo;			//页号
	private String 			pageSize;  		//每页记录数
	private int 			count = 0;
	
	public class SRmSearch{
		private int 		rtuid;
		private int			mp_id;
		private int			res_id;
		public String 		consNo   	= null;				//户号
		public String 		consName 	= null;				//用户名
	}
	
	@JSON(serialize = false)
	public String ReadRMCard() {
		if(result == null || result == STR_NULL){
			result = SDDef.FAIL;
			operErr = "抄表卡字符串为空！";
			return SDDef.SUCCESS;
		}
		
		String[] srm =  result.split(SDDef.JSONSPLIT);
		
		int ONERMLEN = 33;
		int HEADERLEN = 4;
		Integer rmnum = Integer.parseInt(srm[3]);
		
		if (rmnum > (srm.length - HEADERLEN)/ONERMLEN) {
			rmnum = (srm.length - HEADERLEN)/ONERMLEN;
		} 

		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		JSONArray  j_array1 = new JSONArray();
		JSONArray  j_array2 = new JSONArray();

		int sorg_id = CommBase.strtoi(srm[srm.length - 3]);
		int fzman_id = CommBase.strtoi(srm[srm.length - 2]);
		int srtu_id = CommBase.strtoi(srm[srm.length - 1]);
		
		int col = 0;
		for (int row = 0;  row < rmnum; row++)
		{
			
			json1.put("id", row+1);

			
			SRmSearch sserch = new SRmSearch();
			int ret = getRmSerchbyConsId(srm[HEADERLEN + row * ONERMLEN + 0], srm[HEADERLEN + row * ONERMLEN + 9], sorg_id, fzman_id, srtu_id, sserch);

			if (ret == 1) {
				j_array1.add(1);
				j_array1.add("" + (row+1) + "");	//qjl add 20150212在界面grid显示增加序号一列
				j_array1.add(sserch.consName);
				j_array1.add(sserch.consNo);
			}
			else {
				j_array1.add(0);
				j_array1.add("" + (row+1) + "");	//qjl add 20150212在界面grid显示增加序号一列
				j_array1.add("未找到用户");
				j_array1.add("未知户号");
			}
			
			for (col = 0; col < ONERMLEN; col ++) {
				if (col == 1) {
					String jftype = srm[HEADERLEN + row * ONERMLEN + col];
					if (( jftype == null) || ( jftype.length()== 0)||(jftype.equals("0"))) {
						j_array1.add("电量费控");	//jftype = "0";
					}
					else {
						j_array1.add("金额费控");	//jftype = "1";
					}
				}
				else {
					j_array1.add(CommFunc.CheckString(srm[HEADERLEN + row * ONERMLEN + col]));
				}
			}
			
			if (ret == 1) {
				j_array1.add(sserch.rtuid);
				j_array1.add(sserch.mp_id);
				j_array1.add(sserch.res_id);
			}
			else {
				j_array1.add("-1");
				j_array1.add("-1");
				j_array1.add("-1");
			}
			
			json1.put("data", j_array1);
			j_array2.add(json1);
			json1.clear();
			j_array1.clear();	
		}
		
		json2.put("rows", j_array2);
		
		rmback = json2.toString();
		result = SDDef.SUCCESS;
		
		return SDDef.SUCCESS;
	}

	@JSON(serialize = false)
	public String ImportRMcard()
	{
		JSONObject json_obj = JSONObject.fromObject(result);//将页面数据转化为对象
		JSONArray  rows    = json_obj.getJSONArray("rows");	
		HibDao     hib_dao  = new HibDao();

	//	RateInfo[] rateInfo = new RateInfo[rows.size()];
		field = "";
		int ret = -1, errnum = 0;
		for(int i=0; i<rows.size(); i++){
			ret = Import2DB(JSONObject.fromObject(rows.get(i)));
			if (ret <= 0){
				errnum ++;
			}else{
				JSONObject tmp_json = JSONObject.fromObject(rows.get(i));	//qjl add 20150213 添加导入成功的行清除check的操作
				field += "_" + tmp_json.getString("row_index");				//qjl add 20150213 添加导入成功的行清除check的操作
			}
		}
	
		if (errnum == 0) {
			result = SDDef.SUCCESS;
		}
		else {
			result = SDDef.FAIL;
			operErr = "存储数据 总数["+ rows.size() +"]失败数[" + errnum + "]";
		}
			
		return SDDef.SUCCESS;
	}

	public int Import2DB(JSONObject json_obj) {
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;
		int ret = 0;

		try {

			int calctye = json_obj.getString("cacl_type")=="金额费控"?SDDef.YFF_CACL_TYPE_MONEY:SDDef.YFF_CACL_TYPE_DL;
			
			sql = "insert into yddataben.dbo.JRM"+CommBase.strtoi((json_obj.getString("op_date")))/10000+
			"(rtu_id,mp_id,op_date,res_id,res_desc,cons_no,op_man,writecard_no,cacl_type,pay_val,buy_times,limit_val,"+
			"alarm_val,ztlimit_val,pt,ct,meter_id,rm_date,cur_fei,cur_jtfei,errin_num,break_num,dbstate1,dbstate2,bd_zzyz,"+
			"bd_zyz,bd_zyj,bd_zyf,bd_zyp,bd_zyg,bd_fyz,bd_fyj,bd_fyf,bd_fyp,bd_fyg,remain_val,tz_val,totbuy_val,lsmonzyz,lssmonzyz)" +
			"values("+CommBase.strtoi((json_obj.getString("rtu_id")))+","+CommBase.strtoi((json_obj.getString("mp_id")))+","+
			CommBase.strtoi((json_obj.getString("op_date")))+","+CommBase.strtoi((json_obj.getString("res_id")))+",'"+
			(json_obj.getString("res_desc"))+"','"+(json_obj.getString("cons_no"))+"','"+
			CommFunc.getYffMan().getName()+"','"+(json_obj.getString("writecard_no"))+"',"+
			calctye+","+CommBase.strtof((json_obj.getString("pay_val")))+","+
			CommBase.strtoi((json_obj.getString("buy_times")))+","+CommBase.strtof((json_obj.getString("limit_val")))+","+
			CommBase.strtoi((json_obj.getString("alarm_val")))+","+CommBase.strtoi((json_obj.getString("ztlimit_val")))+","+
			CommBase.strtoi((json_obj.getString("pt")))+","+CommBase.strtof((json_obj.getString("ct")))+",'"+
			(json_obj.getString("meter_id"))+"',"+CommBase.strtoi((json_obj.getString("rm_date")))+","+
			CommBase.strtof((json_obj.getString("cur_fei")))/10000+","+CommBase.strtof((json_obj.getString("cur_jtfei")))/10000+","+
			CommBase.strtoi((json_obj.getString("errin_num")))+","+CommBase.strtoi((json_obj.getString("break_num")))+","+
			CommBase.strtoi((json_obj.getString("dbstate1")))+","+CommBase.strtoi((json_obj.getString("dbstate2")))+","+
			CommBase.strtof((json_obj.getString("bd_zzyz")))+","+CommBase.strtof((json_obj.getString("bd_zyz")))+","+
			CommBase.strtof((json_obj.getString("bd_zyj")))+","+CommBase.strtof((json_obj.getString("bd_zyf")))+","+
			CommBase.strtof((json_obj.getString("bd_zyp")))+","+CommBase.strtof((json_obj.getString("bd_zyg")))+","+
			CommBase.strtof((json_obj.getString("bd_fyz")))+","+CommBase.strtof((json_obj.getString("bd_fyj")))+","+
			CommBase.strtof((json_obj.getString("bd_fyf")))+","+CommBase.strtof((json_obj.getString("bd_fyp")))+","+
			CommBase.strtof((json_obj.getString("bd_fyg")))+","+CommBase.strtof((json_obj.getString("remain_val")))+","+
			CommBase.strtof((json_obj.getString("tz_val")))+","+CommBase.strtof((json_obj.getString("totbuy_val")))+","+
			CommBase.strtof((json_obj.getString("lsmonzyz")))+","+CommBase.strtof((json_obj.getString("lssmonzyz")))+")";

			if(j_dao.executeUpdate(sql)){
				ret |= 1;
			}
			
			sql = "insert into yddataben.dbo.JDayBdDl"+CommBase.strtoi((json_obj.getString("op_date")))/100+
			"(rtu_id,mp_id,date,time,bd_zy,bd_zy_fl1,bd_zy_fl2,bd_zy_fl3,bd_zy_fl4,bd_fy,bd_fy_fl1,bd_fy_fl2,bd_fy_fl3,bd_fy_fl4) " +
			"values("+CommBase.strtoi((json_obj.getString("rtu_id")))+","+CommBase.strtoi((json_obj.getString("mp_id")))+","+
			CommBase.strtoi((json_obj.getString("op_date")))+",0," + CommBase.strtof((json_obj.getString("bd_zyz")))+","+
			CommBase.strtof((json_obj.getString("bd_zyj")))+","+CommBase.strtof((json_obj.getString("bd_zyf")))+","+
			CommBase.strtof((json_obj.getString("bd_zyp")))+","+CommBase.strtof((json_obj.getString("bd_zyg")))+","+
			CommBase.strtof((json_obj.getString("bd_fyz")))+","+CommBase.strtof((json_obj.getString("bd_fyj")))+","+
			CommBase.strtof((json_obj.getString("bd_fyf")))+","+CommBase.strtof((json_obj.getString("bd_fyp")))+","+
			CommBase.strtof((json_obj.getString("bd_fyg")))+")";
			
			if(j_dao.executeUpdate(sql)){
				ret |= 2;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			j_dao.closeRs(rs);
		}
		
		return ret;
	}

	/**
	 * 读抄表卡卡检索
	 * @param wc_consNo 写卡户号 rtu_id 终端id
	 * @return sql 语句
	 */
	private String getDySearchRMCardSql(String wc_consNo, String meterNo, int org_id, int fzman_id, int rtu_id) {
		//权限相关
		YffManDef yffman = CommFunc.getYffMan();
	    if ((yffman == null) || (yffman.getApptype() & SDDef.YFF_APPTYPE_DYQX )==0 ) {
			return SDDef.EMPTY;
	    }

	    StringBuffer 	sbfSql 	= new StringBuffer();

	    sbfSql.append("select p.rtu_id,p.mp_id, mt.resident_id, res.describe,res.cons_no,mt.meter_id ");
		sbfSql.append("from meterpara mt,residentpara res,mppay_para p,ydparaben.dbo.rtupara as r, ydparaben.dbo.conspara  as c ");
		sbfSql.append("where mt.rtu_id=p.rtu_id and mt.rtu_id=res.rtu_id and p.mp_id=mt.mp_id and mt.resident_id=res.id and mt.rtu_id = r.id and r.cons_id = c.id");
		
		if (org_id >= 0) {
			sbfSql.append(" and c.org_id=" + org_id);
		}
		
		//20141205
		if (fzman_id >= 0) {
			sbfSql.append(" and c.line_fzman_id=" + fzman_id);
		}
		
		if (rtu_id > 0) {
			sbfSql.append(" and p.rtu_id=" + rtu_id);
		}

		if (wc_consNo.replaceFirst("0*", "").length() < 1) {
			sbfSql.append(" and mt.meter_id like '%" + meterNo.replaceFirst("0*", "")  + "'");
		}
		else {
			sbfSql.append(" and p.writecard_no like '%" + wc_consNo.replaceFirst("0*", "")  + "'");
		}
		
		return sbfSql.toString();
	}
	
	@JSON(serialize = false)
	public int getRmSerchbyConsId(String wc_consNo, String meterNo, int sorg_id, int sfzman_id, int srtu_id, SRmSearch srmSearch) {
		
		int ret = -1;
		List<Map<String, Object>>	list		= null;
		JDBCDao j_dao = new JDBCDao();
		String sql = getDySearchRMCardSql(wc_consNo, meterNo, sorg_id, sfzman_id, srtu_id);
		if(sql.isEmpty()){
			return ret;
		}
		
		JSONObject i_rows = new JSONObject();

		list = j_dao.result(sql);
		if(list == null || list.size()==0){
			return ret;
		}

		int i = 0;
		if(list.size() > 1) {
			
			String consNocard = wc_consNo;
			consNocard = consNocard.replaceFirst("0*", "");
			
			
			String Nodb = "";
			Map<String,Object> tmp = null;
 
			String no0_meterno =  meterNo;
			if (consNocard.length() < 1) {
				no0_meterno = meterNo.replaceFirst("0*", "");
			}
			
			for(i = 0; i < list.size(); i++) {
				tmp = list.get(i);
				
				if (consNocard.length() < 1) {
					Nodb = CommBase.CheckString(tmp.get("meter_id"));
					Nodb = Nodb.replaceFirst("0*", "");
					if(Nodb.equals(no0_meterno)) {
						break;
					}
				}
				else {
					Nodb = CommBase.CheckString(tmp.get("writecard_no"));
					Nodb = Nodb.replaceFirst("0*", "");
					if(Nodb.equals(consNocard)) {
						break;
					}
				}
			}
		}
		
		if (i < list.size()) {
			Map<String,Object> map=list.get(i);	
			
			srmSearch.rtuid = CommBase.strtoi(map.get("rtu_id").toString());
			srmSearch.mp_id = CommBase.strtoi(map.get("mp_id").toString());
			srmSearch.res_id= CommBase.strtoi(map.get("resident_id").toString());
			
			srmSearch.consNo = CommBase.CheckString(map.get("cons_no")); 	//户号
			srmSearch.consName= CommBase.CheckString(map.get("describe"));
			ret =1;
		}
		else {
			
		}
		return ret;
	}
	
	/*
	 * 查询抄表卡记录
	 */
	public String SearchRmRcd(){
		
		StringBuffer ret = new StringBuffer();
		
		try{
			
			JSONObject j_obj = JSONObject.fromObject(result);
			
			String sdate = j_obj.getString("sdate");
			String edate = j_obj.getString("edate");
			
			String yhbh = j_obj.getString("yhbh");
			String yhmc = j_obj.getString("yhmc");
			String czy = j_obj.getString("czy");
			String org = j_obj.getString("org");
			String rtu = j_obj.getString("rtu");
	//		String oper_type = j_obj.getString("oper_type");
			
	//		ret.append("{rows:[");
			
			ret.append(mulZhSearch(sdate, edate, yhbh, yhmc, czy, org, rtu));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(count == 0){
			result = "";
		}else{
		//	ret.deleteCharAt(ret.length() - 1);
		//	ret.append("]}");
		//	result = ret.toString();
			result = "{total:"+count+",page:["+ret.toString()+"]}";
		}
		
		return SDDef.SUCCESS;
	}
	
	private String mulZhSearch(String sdate, String edate, String yhbh, String yhmc, String czy, String org, String rtu) throws Exception
	{

	StringBuffer ret = new StringBuffer();
		List<Map<String, Object>> list 	 = null;
		
		int 			page	 = Integer.parseInt(pageNo == null? "1" : pageNo);
		int 			pagesize = Integer.parseInt(pageSize == null? "10" : pageSize);
		
		
		int isyear = Integer.parseInt(sdate.substring(0, 4));
		int ieyear = Integer.parseInt(edate.substring(0, 4));
		if(ieyear - isyear > 3){
			result = "";
			return null;
		}
		
		int year = ieyear - isyear + 1;
		String table = "", sql = null;
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		for(int i = 0; i < year; i++, isyear++){
			
			sql = "select count(*) from yddataben.dbo.sysobjects where name='JRM" + isyear + "'";
			rs = j_dao.executeQuery(sql);
			if(rs.next()){
				if(rs.getInt(1) == 0){
					j_dao.closeRs(rs);
					continue;
				}
			}
			j_dao.closeRs(rs);
			
			
			table += "union (select r.describe as rtu_desc,a.cons_no as rescons_no,a.describe as resdesc,a.address,b.* from ydparaben.dbo.residentpara a,yddataben.dbo.jrm" + isyear + " b,ydparaben.dbo.rtupara as r, ydparaben.dbo.conspara  as c where a.rtu_id = b.rtu_id and a.rtu_id = r.id and r.cons_id = c.id and a.id = b.res_id ";
			 
			if(!yhbh.isEmpty()){
				table += " and a.cons_no like '%" + yhbh + "%'";
			}
			if(!yhmc.isEmpty()){
				table += " and a.describe like '%" + yhmc + "%'";
			}
			if(!czy.isEmpty()){
				table += " and b.op_man like '%" + czy + "%'";
			}
			if(!org.equals("-1")){
				table += " and c.org_id=" + org;
			}
			if(!rtu.equals("-1")){
				table += " and r.id=" + rtu;
			}
//			if(!oper_type.equals("-1")){
//				table += " and b.op_type=" + oper_type;
//			}
			
			table += " and b.op_date >= " + sdate + " and b.op_date<=" + edate +")";
		}
		if(table.startsWith("union")) {
			table = table.substring(6);
		}else {
			return null;
		}
		
		SqlPage sql_page = new SqlPage(page, pagesize);
		
		String countSql = "select count(*) from (" + table + ") x";
		sql_page.setTotalrecords(countSql);
		count = sql_page.getTotalrecords();
		
		if(sql_page.getTotalrecords()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		if(pagesize == 0){
			sql = "select * from (" + table + ") x order by x.op_date";
		}else{
			if(pagesize > count){
				sql = "select top "+pagesize+" * from (select top "+ count +" * from (" + table + ")x order by x.op_date desc) y order by op_date asc";
			}else{
				sql = "select top "+pagesize+" * from (select top "+(count - pagesize*(page-1)) +" * from (" + table + ")x order by x.op_date desc) y order by op_date asc";
			}
		}
		
		if (pagesize == 0) {
			pagesize = sql_page.getTotalrecords();
			sql_page.setPagesize(pagesize);
		}
		
		list = sql_page.getRecord(sql);
		
		int no = pagesize * (sql_page.getCurrentpage() - 1) + 1;
		
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		JSONArray  j_array1 = new JSONArray();
		JSONArray  j_array2 = new JSONArray();
	
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			//	ret.append("{id:\"" +  map.get("rtu_id") + "_" + map.get("mp_id") + "\",data:[" + no++ +",");//id

			json1.put("id", map.get("rtu_id") + "_" + map.get("mp_id"));
			j_array1.add(no++);

			j_array1.add(CommFunc.CheckString(map.get("rtu_desc")));
			j_array1.add(CommBase.CheckString(map.get("rescons_no")));
			j_array1.add(CommFunc.CheckString(map.get("meter_id")));
			j_array1.add(CommBase.CheckString(map.get("resdesc")));
			j_array1.add(CommBase.CheckString(map.get("address")));
			j_array1.add(CommBase.CheckString(map.get("writecard_no")));
			j_array1.add(CommBase.CheckString(map.get("op_man")));
			j_array1.add(CommFunc.FormatToYMD(map.get("op_date")));

			j_array1.add(CommFunc.CheckString(map.get("rm_date")));
			j_array1.add(CommFunc.CheckString(map.get("pay_val")));
			j_array1.add(CommFunc.CheckString(map.get("buy_times")));
			j_array1.add(Rd.getDict(Dict.DICTITEM_FEETYPE, map.get("cacl_type")));
			j_array1.add(CommFunc.CheckString(map.get("limit_val")));
			j_array1.add(CommFunc.CheckString(map.get("alarm_val")));
			j_array1.add(CommFunc.CheckString(map.get("ztlimit_val")));
			j_array1.add(CommFunc.CheckString(map.get("pt")));
			j_array1.add(CommFunc.CheckString(map.get("ct")));
			j_array1.add(CommFunc.CheckString(map.get("cur_fei")));
			j_array1.add(CommFunc.CheckString(map.get("cur_jtfei")));
			j_array1.add(CommFunc.CheckString(map.get("errin_num")));
			j_array1.add(CommFunc.CheckString(map.get("break_num")));
			j_array1.add(CommFunc.CheckString(map.get("dbstate1")));
			j_array1.add(CommFunc.CheckString(map.get("dbstate2")));
			j_array1.add(CommFunc.CheckString(map.get("remain_val")));
			j_array1.add(CommFunc.CheckString(map.get("tz_val")));
			j_array1.add(CommFunc.CheckString(map.get("totbuy_val")));
			
			j_array1.add(CommFunc.CheckString(map.get("bd_zzyz")));
			j_array1.add(CommFunc.CheckString(map.get("bd_zyz")));
			j_array1.add(CommFunc.CheckString(map.get("bd_zyj")));
			j_array1.add(CommFunc.CheckString(map.get("bd_zyf")));
			j_array1.add(CommFunc.CheckString(map.get("bd_zyp")));
			j_array1.add(CommFunc.CheckString(map.get("bd_zyg")));
			j_array1.add(CommFunc.CheckString(map.get("bd_fyz")));
			j_array1.add(CommFunc.CheckString(map.get("bd_fyj")));
			j_array1.add(CommFunc.CheckString(map.get("bd_fyf")));
			j_array1.add(CommFunc.CheckString(map.get("bd_fyp")));
			j_array1.add(CommFunc.CheckString(map.get("bd_fyg")));
			j_array1.add(CommFunc.CheckString(map.get("lsmonzyz")));
			j_array1.add(CommFunc.CheckString(map.get("lssmonzyz")));
			
//			j_array1.add(map.get("ztlimit_val"));
//			j_array1.add(map.get("pt"));
//			j_array1.add(map.get("ct"));
//			j_array1.add(map.get("meter_id"));
//			j_array1.add(map.get("rm_date"));
//			j_array1.add(map.get("cur_fei"));
//			j_array1.add(map.get("cur_jtfei"));
//			j_array1.add(map.get("errin_num"));
//			j_array1.add(map.get("break_num"));
//			j_array1.add(map.get("dbstate1"));
//			j_array1.add(map.get("dbstate2"));
//			j_array1.add(map.get("bd_zzyz"));
//			j_array1.add(map.get("bd_zyz"));
//			j_array1.add(map.get("bd_zyj"));
//			j_array1.add(map.get("bd_zyf"));
//			j_array1.add(map.get("bd_zyp"));
//			j_array1.add(map.get("bd_zyg"));
//			j_array1.add(map.get("bd_fyz"));
//			j_array1.add(map.get("bd_fyj"));
//			j_array1.add(map.get("bd_fyf"));
//			j_array1.add(map.get("bd_fyp"));
//			j_array1.add(map.get("bd_fyg"));
//			j_array1.add(map.get("remain_val"));
//			j_array1.add(map.get("tz_val"));
//			j_array1.add(map.get("totbuy_val"));
//			j_array1.add(map.get("lsmonzyz"));
//			j_array1.add(map.get("lssmonzyz"));

			json1.put("data", j_array1);
			j_array2.add(json1);
			json1.clear();
			j_array1.clear();	
		}

		json2.put("rows", j_array2);
		return json2.toString();
	}
//
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOperErr() {
		return operErr;
	}

	public void setOperErr(String operErr) {
		this.operErr = operErr;
	}

	public String getRmback() {
		return rmback;
	}

	public void setRmback(String rmback) {
		this.rmback = rmback;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
