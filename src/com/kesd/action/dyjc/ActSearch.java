package com.kesd.action.dyjc;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;

import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.comnt.ComntProtMsg;
import com.kesd.dao.SqlPage;
import com.kesd.dbpara.YffManDef;
import com.kesd.util.Rd;
import com.opensymphony.xwork2.ActionSupport;

public class ActSearch extends ActionSupport{

	private static final long serialVersionUID = -9041893046091034187L;
	
	private String result;
	private String pageNo;			//页号
	private String pageSize;  		//每页记录数
	private int count = 0;

	public String zhSearch(){
		
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
			String oper_type = j_obj.getString("oper_type");
			
			ret.append("{rows:[");
			
			ret.append(mulZhSearch(sdate, edate, yhbh, yhmc, czy, org, rtu, oper_type));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(count == 0){
			result = "";
		}else{
			ret.deleteCharAt(ret.length() - 1);
			ret.append("]}");
			result = ret.toString();
			result = "{total:"+count+",page:["+result+"]}";
		}
		return SDDef.SUCCESS;
	}
	
	private String mulZhSearch(String sdate, String edate, String yhbh, String yhmc, String czy, String org, String rtu, String oper_type) throws Exception
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
			
			sql = "select count(*) from yddataben.dbo.sysobjects where name='jyff" + isyear + "'";
			rs = j_dao.executeQuery(sql);
			if(rs.next()){
				if(rs.getInt(1) == 0){
					j_dao.closeRs(rs);
					continue;
				}
			}
			j_dao.closeRs(rs);
			
			
			table += "union (select b.rtu_id,b.mp_id, b.res_id,r.describe as rtu_desc,b.wasteno,a.cons_no,a.describe," +
					"a.address,b.op_type,b.op_date,b.op_time,b.op_man,b.pay_type,b.pay_money,b.othjs_money,b.zb_money," +
					"b.all_money,b.alarm1,b.alarm2,b.shutdown_val,b.buy_times,b.buy_dl,m.comm_addr  " +
					"from ydparaben.dbo.residentpara a,yddataben.dbo.jyff" + isyear + " b," +
					"ydparaben.dbo.rtupara as r, ydparaben.dbo.conspara  as c, ydparaben.dbo.meterpara  as m" +
					" where b.rtu_id =m.rtu_id and b.mp_id = m.mp_id and a.rtu_id = b.rtu_id and a.rtu_id = r.id and r.cons_id = c.id and a.id = b.res_id and b.visible_flag=1 ";
			
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
			if(!oper_type.equals("-1")){
				table += " and b.op_type=" + oper_type;
			}
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
			sql = "select * from (" + table + ") x order by x.op_date,x.op_time";
		}else{
			if(pagesize > count){
				sql = "select top "+pagesize+" * from (select top "+ count +" * from (" + table + ")x order by x.op_date desc,op_time desc) y order by op_date asc,op_time asc";
			}else{
				sql = "select top "+pagesize+" * from (select top "+(count - pagesize*(page-1)) +" * from (" + table + ")x order by x.op_date desc,op_time desc) y order by op_date asc,op_time asc";
			}
		}
		
		if (pagesize == 0) {
			pagesize = sql_page.getTotalrecords();
			sql_page.setPagesize(pagesize);
		}
		
		list = sql_page.getRecord(sql);
		
		int no = pagesize * (sql_page.getCurrentpage() - 1) + 1;
		
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			
			ret.append("{id:\"" + no + "\",data:[" + no++ +",");
			
			ret.append("\""+map.get("rtu_desc")+"\",");
			ret.append("\""+map.get("wasteno")+"\",");
			ret.append("\""+map.get("cons_no")+"\",");
			ret.append("\""+map.get("describe")+"\",");
			ret.append("\""+map.get("address")+"\",");
			ret.append("\""+map.get("comm_addr")+"\",");
			 
			ret.append("\""+Rd.getDict(Dict.DICTITEM_YFFOPTYPE, map.get("op_type"))+"\",");
			ret.append("\""+CommFunc.FormatToYMD(map.get("op_date")) + " " + CommFunc.FormatToHMS(map.get("op_time"),1) + "\",");
			ret.append("\""+CommBase.CheckString(map.get("op_man"))+"\",");
			ret.append("\""+Rd.getDict(Dict.DICTITEM_PAYTYPE, map.get("pay_type"))+"\",");
			ret.append("\""+map.get("pay_money")+"\",");
//			ret.append("\""+map.get("othjs_money")+"\",");
//			ret.append("\""+map.get("zb_money")+"\",");
			ret.append("\""+map.get("all_money")+"\",");
//			ret.append("\""+map.get("alarm1")+"\",");
//			ret.append("\""+map.get("alarm2")+"\",");
//			ret.append("\""+map.get("shutdown_val")+"\",");
			ret.append("\""+map.get("buy_dl")+"\",");
			ret.append("\""+map.get("buy_times")+"\"");
			
			ret.append("]},");
		}
		
		return ret.toString();
		
		
		
		/**
		String countSql = "select count(*) from ydparaben.dbo.residentpara a, yddataben.dbo.jyff"+sdate.substring(0, 4)+" b , ydparaben.dbo.rtupara as r, ydparaben.dbo.conspara  as c where a.rtu_id = b.rtu_id and a.rtu_id = r.id and r.cons_id = c.id and a.id = b.res_id and b.visible_flag=1 and b.op_date >=" + sdate + " and b.op_date <=" + edate;
		sql = " b.rtu_id, b.mp_id, b.res_id, r.describe as rtu_desc, b.wasteno, a.cons_no,a.describe, a.address,b.op_type,b.op_date,b.op_time,b.op_man,b.pay_type,b.pay_money,b.othjs_money,b.zb_money, b.all_money, b.alarm1, b.alarm2, b.shutdown_val,b.buy_times from ydparaben.dbo.residentpara a, yddataben.dbo.jyff"+sdate.substring(0, 4)+" b , ydparaben.dbo.rtupara as r, ydparaben.dbo.conspara  as c where a.rtu_id = b.rtu_id and a.rtu_id = r.id and r.cons_id = c.id and a.id = b.res_id and b.visible_flag=1 and b.op_date >=" + sdate + " and b.op_date <=" + edate;
		if(!yhbh.isEmpty()){
			countSql += " and a.cons_no like '%" + yhbh + "%'";
			sql += " and a.cons_no like '%" + yhbh + "%'";
		}
		
		if(!yhmc.isEmpty()){
			countSql += " and a.describe like '%" + yhmc + "%'";
			sql += " and a.describe like '%" + yhmc + "%'";
		}
		
		if(!czy.isEmpty()){
			countSql += " and b.op_man like '%" + czy + "%'";
			sql += " and b.op_man like '%" + czy + "%'";
		}
		
		if(!org.equals("-1")){
			countSql += " and c.org_id=" + org;
			sql += " and c.org_id=" + org;
		}
		
		if(!rtu.equals("-1")){
			countSql += " and r.id=" + rtu;
			sql += " and r.id=" + rtu;
		}
		
		if(!oper_type.equals("-1")){
			countSql += " and b.op_type=" + oper_type;
			sql += " and b.op_type=" + oper_type;
		}
		
		sql += " order by b.op_date desc,b.op_time desc) a order by a.op_date,a.op_time";
		
		sql_page.setTotalrecords(countSql);
		
		count = sql_page.getTotalrecords();
		if(pagesize == 0){
			sql = "select top "+count+" * from (select top "+ count +" " + sql;
		}else{
			if(pagesize > count){
				sql = "select top "+pagesize+" * from (select top "+ count +" " + sql;
			}else{
				sql = "select top "+pagesize+" * from (select top "+(count - pagesize*(page-1)) + sql;
			}
		}
		*/
		
		
		
	}
	
	public String yhxxSearch(){
		
		JSONObject j_obj = JSONObject.fromObject(result);
		
		StringBuffer ret = new StringBuffer();
		
		int 			page	 = Integer.parseInt(pageNo == null? "1" : pageNo);
		int 			pagesize = Integer.parseInt(pageSize == null? "10" : pageSize);
		
		SqlPage sql_page = new SqlPage(page, pagesize);
		
		ret.append("{rows:[");
		String countSql = "select count(*) from  ydparaben.dbo.conspara as q, ydparaben.dbo.rtupara as a, ydparaben.dbo.mppara as b, ydparaben.dbo.residentpara as c, ydparaben.dbo.meterpara as d, ydparaben.dbo.mppay_para as e, ydparaben.dbo.mppay_state as s "
				+ "where q.id = a.cons_id and a.app_type="+SDDef.APPTYPE_JC+" and a.id = b.rtu_id and a.id = c.rtu_id and a.id = d.rtu_id and "
				+ "a.id = e.rtu_id and b.id = d.mp_id and b.id = e.mp_id and c.id = d.resident_id and a.id = s.rtu_id and b.id = s.mp_id ";
//		String sql = " b.rtu_id as rtu_id, a.describe as rtu_desc, b.id as mp_id, a.rtu_model as rtu_model, a.prot_type as prot_type, c.id as cons_id, c.cons_no as cons_no, d.meter_id as meter_no, c.describe as cons_desc, c.address as cons_addr, c.phone as cons_telno, c.mobile as cons_mobile, b.pt_ratio*b.ct_ratio as ratio, d.made_no as meter_madeno, e.yffmeter_type as yffmeter_type, b.wiring_mode as meter_wiringmode, d.factory as meter_factory "
		String sql = " b.rtu_id as rtu_id, a.describe as rtu_desc, b.id as mp_id, a.rtu_model as rtu_model, a.prot_type as prot_type, " +
				"c.id as cons_id, c.cons_no as cons_no, d.comm_addr as meter_no, c.describe as cons_desc, c.address as cons_addr, " +
				"c.phone as cons_telno, c.mobile as cons_mobile, b.pt_ratio*b.ct_ratio as ratio, d.made_no as meter_madeno, " +
				"e.yffmeter_type as yffmeter_type, b.wiring_mode as meter_wiringmode, d.factory as meter_factory ,d.comm_addr "
				+ "from  ydparaben.dbo.conspara as q, ydparaben.dbo.rtupara as a, ydparaben.dbo.mppara as b," +
						" ydparaben.dbo.residentpara as c, ydparaben.dbo.meterpara as d, ydparaben.dbo.mppay_para as e, ydparaben.dbo.mppay_state as s "
				+ "where q.id = a.cons_id and a.app_type="+SDDef.APPTYPE_JC+" and a.id = b.rtu_id and a.id = c.rtu_id and a.id = d.rtu_id and "
				+ "a.id = e.rtu_id and b.id = d.mp_id and b.id = e.mp_id and c.id = d.resident_id and a.id = s.rtu_id and b.id = s.mp_id ";
		
		String org = j_obj.getString("org");
		if(!org.equals("-1")){
			countSql += " and q.org_id=" + org;
			sql += " and q.org_id=" + org;
		}
		
		String rtu = j_obj.getString("rtu");
		if(!rtu.equals("-1")){
			countSql += " and a.id=" + rtu;
			sql += " and a.id=" + rtu;
		}
		
		String yhbh = j_obj.getString("yhbh");
		if(!yhbh.isEmpty()){
			countSql += " and c.cons_no like '%" + yhbh + "%'";
			sql += " and c.cons_no like '%" + yhbh + "%'";
		}
		
		String yhmc = j_obj.getString("yhmc");
		if(!yhmc.isEmpty()){
			countSql += " and c.describe like '%" + yhmc + "%'";
			sql += " and c.describe like '%" + yhmc + "%'";
		}
		
		String lxdh = j_obj.getString("lxdh");
		if(!lxdh.isEmpty()){
			countSql += " and c.phone like '%" + lxdh + "%'";
			sql += " and c.phone like '%" + lxdh + "%'";
		}
		
		String bh = j_obj.getString("bh");
		if(!bh.isEmpty()){
			countSql += " and d.comm_addr like '%" + bh + "%'";
			sql += " and d.comm_addr like '%" + bh + "%'";
		}
		sql += " order by rtu_id desc,mp_id desc) a order by a.rtu_id,a.mp_id";
		sql_page.setTotalrecords(countSql);
		
		count = sql_page.getTotalrecords();
		if(pagesize == 0){
			sql = "select top "+count+" * from (select top "+ count +" " + sql;
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
		
		List<Map<String, Object>> list = sql_page.getRecord(sql);
		
		int no = pagesize * (sql_page.getCurrentpage() - 1) + 1;
		
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			
			ret.append("{id:\"" + no + "\",data:[" + no++ +",");
			ret.append("\""+map.get("rtu_desc")+"\",");
			ret.append("\""+map.get("cons_no")+"\",");
			ret.append("\""+map.get("meter_no")+"\",");
			ret.append("\""+map.get("cons_desc")+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("cons_addr"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("comm_addr"))+"\",");

			
			ret.append("\""+CommFunc.CheckString(map.get("cons_telno"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("cons_mobile"))+"\",");
			ret.append("\""+CommFunc.FormatBDDLp00(map.get("ratio"))+"\",");
			ret.append("\""+CommBase.CheckString(map.get("meter_madeno"))+"\",");
			
			ret.append("\""+Rd.getDict(Dict.DICTITEM_YFFMETERTYPE, map.get("yffmeter_type"))+"\",");
			ret.append("\""+Rd.getDict(Dict.DICTITEM_JXFS, map.get("meter_wiringmode"))+"\",");
			ret.append("\""+Rd.getDict(Dict.DICTITEM_METERFACTORY, map.get("meter_factory"))+"\"");
			
			ret.append("]},");
		}
		
		ret.deleteCharAt(ret.length() - 1);
		ret.append("]}");
		
		result = ret.toString();
		
		result = "{total:"+count+",page:["+result+"]}";
		
		return SDDef.SUCCESS;
	}
	
	public String yhztSearch(){
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;
		
		String yffrate[][] = null;
		String yffalarm[][] = null;
		try{
			sql = "select id,describe from yffratepara";
			rs = j_dao.executeQuery(sql);
			rs.last();
			yffrate = new String[rs.getRow()][2];
			rs.beforeFirst();
			int i = 0;
			while(rs.next()){
				yffrate[i] = new String[2];
				yffrate[i][0] = rs.getString("id");
				yffrate[i][1] = rs.getString("describe");
				i++;
			}
			j_dao.closeRs(rs);
			
			sql = "select id,describe from yffalarmpara";
			rs = j_dao.executeQuery(sql);
			rs.last();
			yffalarm = new String[rs.getRow()][2];
			rs.beforeFirst();
			i = 0;
			while(rs.next()){
				yffalarm[i] = new String[2];
				yffalarm[i][0] = rs.getString("id");
				yffalarm[i][1] = rs.getString("describe");
				i++;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			j_dao.closeRs(rs);
		}
		
		int 			page	 = Integer.parseInt(pageNo == null? "1" : pageNo);
		int 			pagesize = Integer.parseInt(pageSize == null? "10" : pageSize);
		
		SqlPage sql_page = new SqlPage(page, pagesize);
		
		JSONObject j_obj = JSONObject.fromObject(result);
		
		StringBuffer ret = new StringBuffer();
		ret.append("{rows:[");
		
		String countSql = "select count(*) from ydparaben.dbo.rtupara as a, ydparaben.dbo.mppay_almstate as b, ydparaben.dbo.residentpara as c, ydparaben.dbo.meterpara as d,ydparaben.dbo.mppara as e,ydparaben.dbo.mppay_para f,ydparaben.dbo.mppay_state g,conspara cons "
			+ "where a.app_type=" + SDDef.APPTYPE_JC + " and a.id=b.rtu_id and a.id=c.rtu_id and a.id=d.rtu_id and b.mp_id=d.mp_id and a.id=f.rtu_id and a.id=g.rtu_id and d.mp_id=f.mp_id and d.mp_id=g.mp_id and "
			+ "c.id=d.resident_id and d.rtu_id=e.rtu_id and d.mp_id=e.id and e.bak_flag=0 and a.cons_id=cons.id";
		
		sql = " a.describe rtu_desc,d.meter_id,d.comm_addr,c.describe cons_desc,c.cons_no," +
				"f.use_flag,f.cacl_type,f.feectrl_type,f.pay_type,f.yffmeter_type,f.feeproj_id,f.yffalarm_id,f.prot_st,f.prot_ed,f.ngloprot_flag,f.key_version,f.cryplink_id,f.pay_add,f.tz_val,f.power_relaf,f.power_rela1,f.power_rela2,f.power_relask1,f.power_relask2,f.fee_chgf,f.fee_chgid,f.fee_chgdate,f.fee_chgtime, f.fee_begindate," +
				"f.jt_cycle_md ,f.cb_cycle_type ,f.cb_dayhour ,f.js_day, f.fxdf_flag, f.fxdf_begindate , f.local_maincalcf ," +
				"g.*,e.describe as mp_describe ," +
				"b.qr_al1_1_state,b.qr_al1_2_state,b.qr_al1_3_state,b.qr_al2_1_state,b.qr_al2_2_state,b.qr_al2_3_state,b.qr_fhz_state,b.qr_fhz_rf1_state,b.qr_fhz_rf2_state,b.qr_fz_times,b.qr_fz_rf1_times,b.qr_fz_rf2_times,b.qr_al1_1_mdhmi,b.qr_al1_2_mdhmi,b.qr_al1_3_mdhmi,b.qr_al2_1_mdhmi,b.qr_al2_2_mdhmi,b.qr_al2_3_mdhmi,b.qr_fhz_mdhmi,b.qr_fhz_rf1_mdhmi,b.qr_fhz_rf2_mdhmi,b.cg_fhz_mdhmi,b.cg_fhz_rf1_mdhmi,b.cg_fhz_rf2_mdhmi,b.qr_al1_1_uuid,b.qr_al2_1_uuid,b.out_info "
				+ " from ydparaben.dbo.rtupara as a, ydparaben.dbo.mppay_almstate as b, ydparaben.dbo.residentpara as c, ydparaben.dbo.meterpara as d,ydparaben.dbo.mppara as e,ydparaben.dbo.mppay_para f,ydparaben.dbo.mppay_state g,conspara cons "
				+ "where a.app_type=" + SDDef.APPTYPE_JC + " and a.id=b.rtu_id and a.id=c.rtu_id and a.id=d.rtu_id and b.mp_id=d.mp_id and a.id=f.rtu_id and a.id=g.rtu_id and d.mp_id=f.mp_id and d.mp_id=g.mp_id and "
				+ "c.id=d.resident_id and d.rtu_id=e.rtu_id and d.mp_id=e.id and e.bak_flag=0 and a.cons_id=cons.id ";
		
		String rtu = j_obj.getString("rtu");
		if(!rtu.equals("-1")){
			countSql += " and a.id=" + rtu;
			sql += " and a.id=" + rtu;
		}
		
		String yhhh = j_obj.getString("yhhh");
		if(!yhhh.isEmpty()){
			countSql += " and c.cons_no like '%" + yhhh + "%'";
			sql += " and c.cons_no like '%" + yhhh + "%'";
		}
		
		String yhmc = j_obj.getString("yhmc");
		if(!yhmc.isEmpty()){
			countSql += " and c.describe like '%" + yhmc + "%'";
			sql += " and c.describe like '%" + yhmc + "%'";
		}
		
		String org = j_obj.getString("org");
		if(!org.equals("-1")){
			countSql += " and cons.org_id=" + org;
			sql += " and cons.org_id=" + org;
		}
		
		String bh = j_obj.getString("bh");
		if(!bh.isEmpty()){
			countSql += " and d.comm_addr like '%" + bh + "%'";
			sql += " and d.comm_addr like '%" + bh + "%'";
		}
		
		if(j_obj.containsKey("mp_id")){
			String mp_id = j_obj.getString("mp_id");
			if(!mp_id.isEmpty()){
				countSql += " and e.id ='"+mp_id+"' ";
				sql += " and e.id = '"+mp_id+"' ";
			}
		}
		
		if(j_obj.containsKey("yhzt")) {
			String yhzt = j_obj.getString("yhzt");
			if(!yhzt.isEmpty()){
				int zt = CommBase.strtoi(yhzt);
				switch(zt) {
				case 0 :		//所有
					break;
				case 1 :		//正常用户
					countSql += " and f.cacl_type<>0 and f.cacl_type is not null and g.cus_state=1";
					sql 	 += " and f.cacl_type<>0 and f.cacl_type is not null and g.cus_state=1";
					break;
				case 2 :		//非正常用户
					countSql += " and (f.cacl_type=0 or f.cacl_type is null or g.cus_state<>1 or g.cus_state is null)";
					sql += "      and (f.cacl_type=0 or f.cacl_type is null or g.cus_state<>1 or g.cus_state is null)";
					break;
				case 3 :		//余额大于1
					countSql += " and g.now_remain>1 and f.cacl_type<>0 and f.cacl_type is not null and g.cus_state=1";
					sql      += " and g.now_remain>1 and f.cacl_type<>0 and f.cacl_type is not null and g.cus_state=1";
					break;
				case 4 :		//余额小于1
					countSql += " and g.now_remain<1 and f.cacl_type<>0 and f.cacl_type is not null and g.cus_state=1";
					sql      += " and g.now_remain<1 and f.cacl_type<>0 and f.cacl_type is not null and g.cus_state=1";
					break;
				}
			}
		}
		//  20121114 add except begin
		if(j_obj.containsKey("alarm_type")) {
			String alarmtype = j_obj.getString("alarm_type");
			if(!alarmtype.isEmpty()){
				int zt = CommBase.strtoi(alarmtype);
				switch(zt) {
				case 0 :		//所有
					break;
				case 1 :		//首次短信异常
					countSql += " and (b.qr_al1_1_state & 0xc0 =0xc0)";
					sql 	 += " and (b.qr_al1_1_state & 0xc0 =0xc0)";
					break;
				case 2 :		//首次声音异常
					countSql += " and (b.qr_al1_2_state & 0xc0 =0xc0)";
					sql 	 += " and (b.qr_al1_2_state & 0xc0 =0xc0)";
					break;
				case 3 :		//二次短信异常
					countSql += " and (b.qr_al2_1_state & 0xc0 =0xc0)";
					sql 	 += " and (b.qr_al2_1_state & 0xc0 =0xc0)";
					break;
				case 4 :		//报警异常
					countSql += " and ((b.qr_al1_1_state & 0xc0 =0xc0)or(b.qr_al1_2_state & 0xc0 =0xc0)or(b.qr_al2_1_state & 0xc0 =0xc0))";
					sql 	 += " and ((b.qr_al1_1_state & 0xc0 =0xc0)or(b.qr_al1_2_state & 0xc0 =0xc0)or(b.qr_al2_1_state & 0xc0 =0xc0))";
					break;
				case 5:			//首次短信正常
					countSql += " and (b.qr_al1_1_state & 0xc0 =0x80)";
					sql 	 += " and (b.qr_al1_1_state & 0xc0 =0x80)";
					break;
				case 6:			//首次声音成功
					countSql += " and (b.qr_al1_2_state & 0xc0 =0x80)";
					sql 	 += " and (b.qr_al1_2_state & 0xc0 =0x80)";
					break;
				case 7:			//二次短信成功
					countSql += " and (b.qr_al2_1_state & 0xc0 =0x80)";
					sql 	 += " and (b.qr_al2_1_state & 0xc0 =0x80)";
					break;
				case 8:			//报警成功 or and 
					countSql += " and ((b.qr_al1_1_state & 0xc0 =0x80)or(b.qr_al1_2_state & 0xc0 =0x80)or(b.qr_al2_1_state & 0xc0 =0x80))";
					sql 	 += " and ((b.qr_al1_1_state & 0xc0 =0x80)or(b.qr_al1_2_state & 0xc0 =0x80)or(b.qr_al2_1_state & 0xc0 =0x80))";
					break;
				default:
					break;	
				}
			}
		}
		
		if(j_obj.containsKey("ctrl_type")) {
			String ctrltype = j_obj.getString("ctrl_type");
			if(!ctrltype.isEmpty()){
				int zt = CommBase.strtoi(ctrltype);
				switch(zt) {
				case 0 :		//所有
					break;
				case 1 :		//分闸异常
					countSql += " and cs_fhz_state = 0 and (b.qr_fhz_state & 0xc0 =0xc0)";
					sql 	 += " and cs_fhz_state = 0 and (b.qr_fhz_state & 0xc0 =0xc0)";
					break;
				case 2 :		//合闸异常
					countSql += " and g.cs_fhz_state = 1 and (b.qr_fhz_state & 0xc0 =0xc0)";
					sql 	 += " and g.cs_fhz_state = 1 and (b.qr_fhz_state & 0xc0 =0xc0)";
					break;
				case 3 :		//分合闸异常
					countSql += " and (b.qr_fhz_state & 0xc0 =0xc0)";
					sql 	 += " and (b.qr_fhz_state & 0xc0 =0xc0)";					
					break;
				case 4 :		//分闸成功
					countSql += " and g.cs_fhz_state = 0 and (b.qr_fhz_state & 0xc0 =0x80)";
					sql 	 += " and g.cs_fhz_state = 0 and (b.qr_fhz_state & 0xc0 =0x80)";
					break;
				case 5:			//合闸成功
					countSql += " and g.cs_fhz_state = 1 and (b.qr_fhz_state & 0xc0 =0x80)";
					sql 	 += " and g.cs_fhz_state = 1 and (b.qr_fhz_state & 0xc0 =0x80)";
					break;
				case 6:			//分合闸成功
					countSql += " and (b.qr_fhz_state & 0xc0 =0x80)";
					sql 	 += " and (b.qr_fhz_state & 0xc0 =0x80)";
					break;
				default:
					break;
				}
			}
		} 		
		   
		//  20121114 add except end
		
		sql += " order by g.rtu_id desc,g.mp_id desc) a order by a.rtu_id,a.mp_id";
		
		sql_page.setTotalrecords(countSql);
		
		count = sql_page.getTotalrecords();
		
		if(count == 0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		if(pagesize == 0){
			sql = "select top "+count+" * from (select top "+ count +" " + sql;
		}else{
			if(pagesize > count){
				sql = "select top "+pagesize+" * from (select top "+ count +" " + sql;
			}else{
				sql = "select top "+pagesize+" * from (select top "+(count - pagesize*(page-1)) + sql;
			}
		}
		
		if (pagesize == 0) {
			pagesize = sql_page.getTotalrecords();
			sql_page.setPagesize(pagesize);
		}
		
		List<Map<String, Object>> list = sql_page.getRecord(sql);
		
		int no = pagesize * (sql_page.getCurrentpage() - 1) + 1;
		
		int tmp = 0;
		
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			
			ret.append("{id:\"" +  map.get("rtu_id") + "_" + map.get("mp_id") + "\",data:[" + no++ +",");//id
			ret.append("\"["+map.get("rtu_id")+"]"+map.get("rtu_desc")+"\",");//终端名称
			ret.append("\""+map.get("cons_no")+"\",");//居民户号
			ret.append("\"["+map.get("mp_id")+"]"+CommBase.CheckString(map.get("mp_describe"))+"\",");//测量点编号
//			ret.append("\""+map.get("meter_id")+"\",");//表号
			ret.append("\""+map.get("comm_addr")+"\",");//表通讯地址
			ret.append("\""+map.get("cons_desc")+"\",");//用户名称
			
			ret.append("\""+Rd.getDict(Dict.DICTITEM_FEETYPE, map.get("cacl_type"))+"\",");//计费方式
			ret.append("\""+Rd.getDict(Dict.DICTITEM_PREPAYTYPE, map.get("feectrl_type"))+"\",");//费控方式
			ret.append("\""+Rd.getDict(Dict.DICTITEM_PAYTYPE, map.get("pay_type"))+"\",");//缴费方式
//			ret.append("\""+Rd.getDict(Dict.DICTITEM_YFFMETERTYPE, map.get("yffmeter_type"))+"\",");//预付费表类型
//			ret.append("\""+CommBase.CheckNum(map.get("tz_val"))+"\",");//透支值
			
			int j = 0;
			Object obj = map.get("feeproj_id");//费率方案号
			if(obj == null) {
				ret.append("\"\",");
			}
			else{
				//20121203 zkz
				for (j = 0; j < yffrate.length; j++) {
					if(obj.toString().equals(yffrate[j][0])){
						ret.append("\""+yffrate[j][1]+"\",");
						break;
					}
				}
				if (j >= yffrate.length) {
					ret.append("\"\",");
				}

			}
//			obj = map.get("yffalarm_id");//报警方案
//			if (obj == null) {
//				ret.append("\"\",");
//			}
//			else {//20121203 zkz
//				for (j = 0; j < yffalarm.length; j++) {
//					if(obj.toString().equals(yffalarm[j][0])){
//						ret.append("\""+yffalarm[j][1]+"\",");
//						break;
//					}
//				}
//				if (j >= yffalarm.length) {
//					ret.append("\"\",");
//				}
//			}
			
//			ret.append("\""+CommBase.CheckNum(map.get("prot_st"))+"-"+CommBase.CheckNum(map.get("prot_ed"))+"\",");//保电时间
//			ret.append("\""+Rd.getDict(Dict.DICTITEM_YESFLAG, map.get("ngloprot_flag"))+"\",");//不全局保电
			ret.append("\""+CommBase.CheckNum(map.get("key_version"))+"\",");//密钥版本
//			ret.append("\""+CommBase.CheckNum(map.get("cryplink_id"))+"\",");//加密机ID
//			ret.append("\""+CommFunc.FormatToYMD(map.get("fee_begindate"))+"\",");//费率启用日期
//			ret.append("\""+CommBase.CheckNum(map.get("power_relaf"))+"-["+CommBase.CheckNum(map.get("power_rela1"))+"-"+CommBase.CheckNum(map.get("power_relask1"))+"]-["+CommBase.CheckNum(map.get("power_rela2"))+"-"+CommBase.CheckNum(map.get("power_relask2"))+"]\",");//动力关联
//			
//			ret.append("\""+CommFunc.FormatToDH(map.get("cb_dayhour"))+"\",");//抄表日
//			ret.append("\""+Rd.getDict(Dict.DICTITEM_CB_CYCLE_TYPE, map.get("cb_cycle_type"))+"\",");//抄表周期			
//			ret.append("\""+Rd.getDict(Dict.DICTITEM_YESFLAG, map.get("local_maincalcf"))+"\",");//主站算费标志
//			ret.append("\""+Rd.getDict(Dict.DICTITEM_YESFLAG, map.get("fxdf_flag"))+"\",");//是否发行电费
//			ret.append("\""+CommFunc.FormatToYMD(map.get("fxdf_begindate"))+"\",");//发行起始日期
//			ret.append("\""+CommFunc.FormatToMD(map.get("jt_cycle_md"))+"\",");//阶梯切换月日
//			ret.append("\""+Rd.getDict(Dict.DICTITEM_YESFLAG, map.get("fee_chgf"))+"\",");//费率更改标志
//			ret.append("\""+CommBase.CheckNum(map.get("fee_chgid"))+"-["+CommBase.CheckNum(map.get("fee_chgdate"))+"-"+CommBase.CheckNum(map.get("fee_chgtime"))+"]\",");//费率更改内容
//			
			ret.append("\""+Rd.getDict(Dict.DICTITEM_YFFCUSSTATE, map.get("cus_state"))+"\",");//用户状态
			ret.append("\""+Rd.getDict(Dict.DICTITEM_YFFOPTYPE, map.get("op_type"))+"\",");//操作类型
			ret.append("\""+CommFunc.FormatToYMD(map.get("op_date"))+" "+ CommFunc.FormatToHMS(map.get("op_time"),1)+"\",");//操作时间
//			ret.append("\""+CommBase.CheckNum(map.get("pay_money")) + "+"+CommBase.CheckNum(map.get("othjs_money"))+"+"+CommBase.CheckNum(map.get("zb_money"))+"="+CommBase.CheckNum(map.get("all_money"))+"\",");//缴费金额
			ret.append("\""+CommFunc.FormatBDDLp000(map.get("pay_money"))+"\",");//缴费金额
			ret.append("\""+CommFunc.FormatBDDLp000(map.get("buy_dl"))+"\",");//购买电量
//			ret.append("\""+CommBase.CheckNum(map.get("shutdown_val"))+"\",");//断电金额
//			ret.append("\""+CommBase.CheckNum(map.get("shutdown_val2"))+"\",");//断电金额2
			ret.append("\""+CommBase.CheckNum(map.get("buy_times"))+"\",");//购电次数
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("now_remain"))+"\",");//当前剩余
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("now_remain2"))+"\",");//剩余金额2
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("bj_bd"))+"\",");//报警门限
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("tz_bd"))+"\",");//跳闸门限
//			ret.append("\""+CommBase.CheckNum(map.get("total_gdz"))+"\","); //累计购电值
			ret.append("\""+CommFunc.FormatToYMD(map.get("kh_date"))+"\",");//开户日期
			
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("jsbd_zyz"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd1_zyz"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd2_zyz"))+"\",");//结算总表底
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("jsbd_zyj"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd1_zyj"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd2_zyj"))+"\",");//结算尖表底
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("jsbd_zyf"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd1_zyf"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd2_zyf"))+"\",");//结算峰表底
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("jsbd_zyp"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd1_zyp"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd2_zyp"))+"\",");//结算平表底
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("jsbd_zyg"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd1_zyg"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd2_zyg"))+"\",");//结算谷表底
//			ret.append("\""+CommFunc.FormatToYMD(map.get("jsbd_ymd"))+"\",");//结算时间
//			ret.append("\""+CommFunc.FormatToMDHMin(map.get("calc_mdhmi"))+"\",");//算费时间
//			ret.append("\""+CommFunc.FormatToYMD(map.get("calc_bdymd"))+"\",");//算费表底时间
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("calc_zyz"))+"-"+CommFunc.FormatBDDLp000(map.get("calc1_zyz"))+"-"+CommFunc.FormatBDDLp000(map.get("calc2_zyz"))+"\",");//算费总表底
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("calc_zyj"))+"-"+CommFunc.FormatBDDLp000(map.get("calc1_zyj"))+"-"+CommFunc.FormatBDDLp000(map.get("calc2_zyj"))+"\",");//算费尖表底
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("calc_zyf"))+"-"+CommFunc.FormatBDDLp000(map.get("calc1_zyf"))+"-"+CommFunc.FormatBDDLp000(map.get("calc2_zyf"))+"\",");//算费峰表底
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("calc_zyp"))+"-"+CommFunc.FormatBDDLp000(map.get("calc1_zyp"))+"-"+CommFunc.FormatBDDLp000(map.get("calc2_zyp"))+"\",");//算费平表底
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("calc_zyg"))+"-"+CommFunc.FormatBDDLp000(map.get("calc1_zyg"))+"-"+CommFunc.FormatBDDLp000(map.get("calc2_zyg"))+"\",");//算费谷表底
//			
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("jt_total_zbdl"))+"\",");//阶梯追补电量
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("jt_total_dl"))+"\",");//阶梯累计用电量
//			ret.append("\""+CommFunc.FormatToYMD(map.get("hb_date"))+CommFunc.FormatToHMS(map.get("hb_time"),1)+"\",");//换表时间
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("fxdf_iall_money"))+"\",");//发行电费当月缴费
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("fxdf_iall_money2"))+"\",");//发行电费当月缴费2
//			ret.append("\""+CommFunc.FormatToYMD(map.get("jt_reset_ymd"))+"\",");//阶梯切换日期(时间)
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("fxdf_remain"))+"\",");//发行电费剩余金额
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("fxdf_remain2"))+"\",");//发行电费剩余金额2
//			ret.append("\""+CommFunc.FormatToMDHMin(map.get("jt_reset_mdhmi"))+"\",");//阶梯切换执行时间
//			ret.append("\""+CommFunc.FormatToYM(map.get("fxdf_ym"))+"\",");//发行电费年月
//			ret.append("\""+CommFunc.FormatToYMD(map.get("fxdf_data_ymd"))+"\",");//发行电费数据日期
//			ret.append("\""+CommFunc.FormatToMDHMin(map.get("fxdf_calc_mdhmi"))+"\",");//发行电费算费时间	
//			
//			ret.append("\""+(Integer.parseInt(CommBase.CheckNum(map.get("cs_al1_state"))) == 0 ? "正常":"报警")+"\",");//报警1状态
//			ret.append("\""+(Integer.parseInt(CommBase.CheckNum(map.get("cs_al2_state"))) == 0 ? "正常":"报警")+"\",");//报警2状态
//			ret.append("\""+Rd.getDict(Dict.DICTITEM_SWITCH_STATE1, map.get("cs_fhz_state"))+"\",");//分合闸状态
//			ret.append("\""+CommFunc.FormatToMDHMin(map.get("al1_mdhmi"))+"\",");//报警1改变时间
//			ret.append("\""+CommFunc.FormatToMDHMin(map.get("al2_mdhmi"))+"\",");//报警2改变时间
//			ret.append("\""+CommFunc.FormatToMDHMin(map.get("fhz_mdhmi"))+"\",");//分合闸改变时间
//			ret.append("\""+CommBase.CheckString(map.get("yc_flag1"))+"\",");//异常标志1
//			ret.append("\""+CommBase.CheckString(map.get("yc_flag2"))+"\",");//异常标志2
//			ret.append("\""+CommFunc.FormatBDDLp000(map.get("fz_zyz"))+"-"+CommFunc.FormatBDDLp000(map.get("fz1_zyz"))+"-"+CommFunc.FormatBDDLp000(map.get("fz2_zyz"))+"\",");//分闸时总表底
//			
//			tmp = Integer.parseInt(CommBase.CheckNum(map.get("qr_al1_1_state")));//报警1短信
//			ret.append("\""+Rd.getDict(Dict.DICTITEM_YFFQRZT, tmp>>6)+"-["+(tmp&0x3f)+"]\",");
//			tmp = Integer.parseInt(CommBase.CheckNum(map.get("qr_al1_2_state")));//报警1声音
//			ret.append("\""+Rd.getDict(Dict.DICTITEM_YFFQRZT, tmp>>6)+"-["+(tmp&0x3f)+"]\",");
//			tmp = Integer.parseInt(CommBase.CheckNum(map.get("qr_fhz_state")));//分合闸确认
//			ret.append("\""+Rd.getDict(Dict.DICTITEM_YFFQRZT, tmp>>6)+"-["+(tmp&0x3f)+"],");
//			tmp = Integer.parseInt(CommBase.CheckNum(map.get("qr_fhz_rf1_state")));
//			ret.append(Rd.getDict(Dict.DICTITEM_YFFQRZT, tmp>>6)+"-["+(tmp&0x3f)+"],");
//			tmp = Integer.parseInt(CommBase.CheckNum(map.get("qr_fhz_rf2_state")));
//			ret.append(Rd.getDict(Dict.DICTITEM_YFFQRZT, tmp>>6)+"-["+(tmp&0x3f)+"]\",");
//			tmp = Integer.parseInt(CommBase.CheckNum(map.get("qr_al2_1_state")));//报警2短信
//			ret.append("\""+Rd.getDict(Dict.DICTITEM_YFFQRZT, tmp>>6)+"-["+(tmp&0x3f)+"]\",");
//			tmp = Integer.parseInt(CommBase.CheckNum(map.get("qr_al2_2_state")));//报警2声音
//			ret.append("\""+Rd.getDict(Dict.DICTITEM_YFFQRZT, tmp>>6)+"-["+(tmp&0x3f)+"]\",");
//			ret.append("\""+CommBase.CheckNum(map.get("qr_fz_times"))+"-"+CommBase.CheckNum(map.get("qr_fz_rf1_times"))+"-"+CommBase.CheckNum(map.get("qr_fz_rf2_times"))+"\",");//分合闸次数
//			ret.append("\""+CommFunc.FormatToMDHMin(map.get("qr_al1_1_mdhmi"))+"\",");//报警1短信时间
//			ret.append("\""+CommFunc.FormatToMDHMin(map.get("qr_al1_2_mdhmi"))+"\",");//报警1声音时间
//			ret.append("\""+CommFunc.FormatToMDHMin(map.get("qr_fhz_mdhmi"))+"-"+CommFunc.FormatToMDHMin(map.get("qr_fhz_rf1_mdhmi"))+"-"+CommFunc.FormatToMDHMin(map.get("qr_fhz_rf2_mdhmi"))+"\",");//分合闸发送时间
//			ret.append("\""+CommFunc.FormatToMDHMin(map.get("qr_al2_1_mdhmi"))+"\",");//报警2短信时间
//			ret.append("\""+CommFunc.FormatToMDHMin(map.get("qr_al2_2_mdhmi"))+"\",");//报警2声音时间
//			ret.append("\""+CommFunc.FormatToMDHMin(map.get("cg_fhz_mdhmi"))+"-"+CommFunc.FormatToMDHMin(map.get("cg_fhz_rf1_mdhmi"))+"-"+CommFunc.FormatToMDHMin(map.get("cg_fhz_rf2_mdhmi"))+"\",");//成功分合闸时间
//			ret.append("\""+CommBase.CheckNum(map.get("qr_al1_1_uuid"))+"\",");//报警1短信UUID
//			ret.append("\""+CommBase.CheckNum(map.get("qr_al2_1_uuid"))+"\",");//报警2短信UUID
			ret.append("\""+CommBase.CheckString(map.get("out_info"))+"\"");//信息输出
			
			ret.append("]},");
		}
		
		ret.deleteCharAt(ret.length() - 1);
		ret.append("]}");
		
		result = ret.toString();
		result = "{total:"+count+",page:["+result+"]}";
		return SDDef.SUCCESS;
	}
	
	public String kzxxSearch(){
		
		StringBuffer ret = new StringBuffer();
		
		try{
			
			JSONObject j_obj = JSONObject.fromObject(result);
			
			String sdate = j_obj.getString("sdate");
			String edate = j_obj.getString("edate");
			
			ret.append("{rows:[");
			
			String yhbh = j_obj.getString("yhbh");
			String yhmc = j_obj.getString("yhmc");
			String czy = j_obj.getString("czy");
			String org = j_obj.getString("org");
			String rtu = j_obj.getString("rtu");
			
			ret.append(mulKzxx(sdate, edate, yhbh, yhmc, czy, org, rtu));
			
			/*
			int isyear = Integer.parseInt(sdate.substring(0, 4));
			int ieyear = Integer.parseInt(edate.substring(0, 4));
			
			String isday = sdate.substring(4);
			String ieday = edate.substring(4);
			
			int year = ieyear - isyear + 1;
			
			
			
			for(int i = 0; i < year; i++){
				
				sdate = isyear + isday;
				if (i < year - 1) {
					edate = isyear + "1231";
					isday = "0101";
				}else if(i == year - 1){
					edate = ieyear + ieday;
				}
				
				ret.append(mulKzxx(sdate, edate, yhbh, yhmc, czy));
				
				isyear++;
			}
			*/
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(count == 0){
			result = "";
		}else{
			ret.deleteCharAt(ret.length() - 1);
			ret.append("]}");
			result = ret.toString();
			result = "{total:"+count+",page:["+result+"]}";
		}
		
		return SDDef.SUCCESS;
		
	}
	
	public String mulKzxx(String sdate, String edate, String yhbh, String yhmc, String czy, String org, String rtu)throws Exception{
		
		StringBuffer ret = new StringBuffer();
		List<Map<String, Object>> list 	 = null;
		
		int 			page	 = Integer.parseInt(pageNo == null? "1" : pageNo);
		int 			pagesize = Integer.parseInt(pageSize == null? "10" : pageSize);
		
		int isyear = Integer.parseInt(sdate.substring(0,4));
		int ieyear = Integer.parseInt(edate.substring(0,4));
		if(ieyear - isyear > 3){
			result = "";
			return null;
		}
		
		int year = ieyear - isyear + 1;
		String table = "", sql = null;
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		for (int i = 0; i < year; i++, isyear++) {
			sql = "select count(*) from yddataben.dbo.sysobjects where name = 'jfk" + isyear + "'";
			rs = j_dao.executeQuery(sql);
			if(rs.next()){
				if(rs.getInt(1)==0){
					j_dao.closeRs(rs);
					continue;
				}
			}
			j_dao.closeRs(rs);
		table += "union (select a.cons_no,a.describe, a.address,b.* from ydparaben.dbo.residentpara a, yddataben.dbo.jfk"+isyear+" b,conspara c,rtupara r where a.rtu_id = b.rtu_id and a.id=b.res_id and a.rtu_id=r.id and r.cons_id=c.id and b.op_date >=" + sdate + " and b.op_date <=" + edate;	
		if(!yhbh.isEmpty()){
			//countSql += " and a.cons_no like '%" + yhbh + "%'";
			table += " and a.cons_no like '%" + yhbh + "%'";
		}
		
		if(!yhmc.isEmpty()){
			//countSql += " and a.describe like '%" + yhmc + "%'";
			table += " and a.describe like '%" + yhmc + "%'";
		}
		
		if(!czy.isEmpty()){
			//countSql += " and b.op_man like '%" + czy + "%'";
			table += " and b.op_man like '%" + czy + "%'";
		}
		if(!org.equals("-1")){
			//countSql += " and c.org_id=" + org;
			table += " and c.org_id=" + org;
		}
		if(!rtu.equals("-1")){
			//countSql += " and r.id=" + rtu;
			table += " and r.id=" + rtu;
		}	
		table += ")";	
			
	}
		if(table.startsWith("union")){
			table = table.substring(6);
		}else{
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
			sql = "select * from (" + table + ") x order by x.op_date, x.op_time";
		}else{
			if(pagesize > count){
				sql = "select top "+pagesize+" * from (select top "+ count +" * from (" + table + ")x order by x.op_date desc, x.op_time desc) y order by op_date asc, op_time asc";
			}else{
				sql = "select top "+pagesize+" * from (select top "+(count - pagesize*(page-1)) +" * from (" + table + ")x order by x.op_date desc,op_time desc) y order by op_date asc,op_time asc";
			}
		}
		
		/*
		String countSql = "select count(*) from ydparaben.dbo.residentpara a, yddataben.dbo.jfk"+sdate.substring(0, 4)+" b,conspara c,rtupara r where a.rtu_id = b.rtu_id and a.id = b.res_id and a.rtu_id=r.id and r.cons_id=c.id and b.op_date >=" + sdate + " and b.op_date <=" + edate;
		String sql = " a.cons_no,a.describe, a.address,b.* from ydparaben.dbo.residentpara a, yddataben.dbo.jfk"+sdate.substring(0, 4)+" b,conspara c,rtupara r where a.rtu_id = b.rtu_id and a.id=b.res_id and a.rtu_id=r.id and r.cons_id=c.id and b.op_date >=" + sdate + " and b.op_date <=" + edate;
		
		if(!yhbh.isEmpty()){
			countSql += " and a.cons_no like '%" + yhbh + "%'";
			sql += " and a.cons_no like '%" + yhbh + "%'";
		}
		
		if(!yhmc.isEmpty()){
			countSql += " and a.describe like '%" + yhmc + "%'";
			sql += " and a.describe like '%" + yhmc + "%'";
		}
		
		if(!czy.isEmpty()){
			countSql += " and b.op_man like '%" + czy + "%'";
			sql += " and b.op_man like '%" + czy + "%'";
		}
		if(!org.equals("-1")){
			countSql += " and c.org_id=" + org;
			sql += " and c.org_id=" + org;
		}
		if(!rtu.equals("-1")){
			countSql += " and r.id=" + rtu;
			sql += " and r.id=" + rtu;
		}
		sql += " order by b.op_date desc,b.op_time desc) a order by a.op_date,a.op_time";
		
		sql_page.setTotalrecords(countSql);
		
		count = sql_page.getTotalrecords();
		if(count == 0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		if(pagesize == 0){
			sql = "select top "+count+" * from (select top "+ count +" " + sql;
		}else{
			if(pagesize > count){
				sql = "select top "+pagesize+" * from (select top "+ count +" " + sql;
			}else{
				sql = "select top "+pagesize+" * from (select top "+(count - pagesize*(page-1)) + sql;
			}
		}
		*/
		
		if (pagesize == 0) {
			pagesize = sql_page.getTotalrecords();
			sql_page.setPagesize(pagesize);
		}
		
		list = sql_page.getRecord(sql);
		
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(ComntProtMsg.YFF_DY_CTRL_CUT, "跳闸");
		map.put(ComntProtMsg.YFF_DY_CTRL_ON, "合闸允许");
		map.put(ComntProtMsg.YFF_DY_CTRL_KEEPON, "保电");
		map.put(ComntProtMsg.YFF_DY_CTRL_KEEPOFF, "取消保电");
		map.put(ComntProtMsg.YFF_DY_CTRL_ALARMON, "报警");
		map.put(ComntProtMsg.YFF_DY_CTRL_ALARMOFF, "报警解除");
		
		int no = pagesize * (sql_page.getCurrentpage() - 1) + 1;
		
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> ret_map = list.get(i);
			
			ret.append("{id:\"" + no + "\",data:[" + no++ +",");
			ret.append("\""+ret_map.get("cons_no")+"\",");
			ret.append("\""+ret_map.get("describe")+"\",");
			ret.append("\""+ret_map.get("address")+"\",");
					
			if(ret_map.get("op_man").equals("yffalarm_service")){				
				if(Integer.parseInt(ret_map.get("op_type")+"")==ComntProtMsg.YFF_DY_CTRL_CUT)
				{	ret.append("\"欠费跳闸\",");} 
				else if		(Integer.parseInt(ret_map.get("op_type")+"")==ComntProtMsg.YFF_DY_CTRL_ON) 
					{ret.append("\"缴费合闸\",");	}					
				else{
					ret.append("\""+map.get(Integer.parseInt(ret_map.get("op_type")+""))+"\",");
				}				
			}else{
				ret.append("\""+map.get(Integer.parseInt(ret_map.get("op_type")+""))+"\",");
			}
			
			ret.append("\""+CommFunc.FormatToYMD(ret_map.get("op_date"))+ CommFunc.FormatToHMS(ret_map.get("op_time"),2) + "\",");
			ret.append("\""+ret_map.get("op_man")+"\"");
			
			ret.append("]},");
		}
		
		if(ret.toString().isEmpty()){
			return "";
		}
		
		return ret.toString();
	}
	
	public String jsbcSearch() {
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
			
			ret.append("{rows:[");
			
			ret.append(mulJsbcSearch(sdate, edate, yhbh, yhmc, czy, org, rtu));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(count == 0){
			result = "";
		}else{
			ret.deleteCharAt(ret.length() - 1);
			ret.append("]}");
			result = ret.toString();
			result = "{total:"+count+",page:["+result+"]}";
		}
		return SDDef.SUCCESS;
	}

	private String mulJsbcSearch(String sdate, String edate, String yhbh, String yhmc, String czy, String org, String rtu) throws Exception{
		StringBuffer ret = new StringBuffer();
		List<Map<String, Object>> list 	 = null;
		
		int 			page	 = Integer.parseInt(pageNo == null? "1" : pageNo);
		int 			pagesize = Integer.parseInt(pageSize == null? "10" : pageSize);
		
		
		int isyear = Integer.parseInt(sdate.substring(0,4));
		int ieyear = Integer.parseInt(edate.substring(0,4));
		if(ieyear - isyear > 3){
			result = "";
			return null;
		}
		
		int year = ieyear - isyear + 1;
		String table = "", sql = null;
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		for (int i = 0; i < year; i++, isyear++) {
			sql = "select count(*) from yddataben.dbo.sysobjects where name = 'JJsRecord" + isyear + "'";
			rs = j_dao.executeQuery(sql);
			if(rs.next()){
				if(rs.getInt(1)==0){
					j_dao.closeRs(rs);
					continue;
				}
			}
			j_dao.closeRs(rs);
			
		table += "union ( select res.cons_no,res.describe cons_desc,js.* from rtupara r,residentpara res,meterpara mt,conspara c,yddataben.dbo.JJsRecord"+isyear+" js where r.app_type=3 and r.id=res.rtu_id and r.id=mt.rtu_id and r.cons_id=c.id and r.id=js.rtu_id and mt.mp_id=js.mp_id and mt.resident_id=res.id " +
					"and js.op_date >=" + sdate + " and js.op_date <=" + edate;	
		if(!yhbh.isEmpty()){
			//countSql += " and res.cons_no like '%" + yhbh + "%'";
			table += " and res.cons_no like '%" + yhbh + "%'";
		}
		
		if(!yhmc.isEmpty()){
			//countSql += " and res.describe like '%" + yhmc + "%'";
			table += " and res.describe like '%" + yhmc + "%'";
		}
		
		if(!czy.isEmpty()){
			//countSql += " and js.op_man like '%" + czy + "%'";
			table += " and js.op_man like '%" + czy + "%'";
		}
		
		if(!org.equals("-1")){
			//countSql += " and c.org_id=" + org;
			table += " and c.org_id=" + org;
		}
		
		if(!rtu.equals("-1")){
			//countSql += " and r.id=" + rtu;
			table += " and r.id=" + rtu;
		}
		table += ")";
	}
		if(table.startsWith("union")){
			table = table.substring(6);
		}else{
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
			sql = "select * from (" + table + ") x order by x.op_date, x.op_time";
		}else{
			if(pagesize > count){
				sql = "select top "+pagesize+" * from (select top "+ count +" * from (" + table + ")x order by x.op_date desc, x.op_time desc) y order by op_date asc, op_time asc";
			}else{
				sql = "select top "+pagesize+" * from (select top "+(count - pagesize*(page-1)) +" * from (" + table + ")x order by x.op_date desc,op_time desc) y order by op_date asc,op_time asc";
			}
		}

		if (pagesize == 0) {
			pagesize = sql_page.getTotalrecords();
			sql_page.setPagesize(pagesize);
		}
		
		
		list = sql_page.getRecord(sql);
		
		int no = pagesize * (sql_page.getCurrentpage() - 1) + 1;
		
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			
			ret.append("{id:\"" + no + "\",data:[" + (no++) +",");
			ret.append("\"" + CommBase.CheckString(map.get("cons_no")) + "\",");
			ret.append("\"" + map.get("cons_desc") + "\",");
			ret.append("\"" + CommFunc.FormatToYM(map.get("fxdf_ym")) + "\",");
			ret.append("\"" + map.get("js_times") + "\",");
			ret.append("\"" + CommFunc.FormatToYMD(map.get("op_date"))+ CommFunc.FormatToHMS(map.get("op_time"),2) + "\",");
			ret.append("\"" + CommBase.CheckString(map.get("op_man"))+"\",");
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_PAYTYPE, map.get("pay_type"))+"\",");
			ret.append("\"" + map.get("wasteno") + "\",");
			ret.append("\"" + map.get("othjs_money") + "\",");
			ret.append("\"" + map.get("misjs_money") + "\",");
			ret.append("\"" + map.get("totjs_money") + "\",");
			ret.append("\"" + map.get("lastala_val1") + "\",");
			ret.append("\"" + map.get("lastala_val2") + "\",");
			ret.append("\"" + map.get("lastshut_val") + "\",");
			ret.append("\"" + map.get("newala_val1") + "\",");
			ret.append("\"" + map.get("newala_val2") + "\",");
			ret.append("\"" + map.get("newshut_val") + "\",");
			ret.append("\"" + map.get("buy_times") + "\"");
			ret.append("]},");
		}
		
		return ret.toString();
		/*
		String countSql = "select count(*) from rtupara r,residentpara res,meterpara mt,conspara c,yddataben.dbo.JJsRecord"+sdate.substring(0, 4)+" js where r.app_type=3 and r.id=res.rtu_id and r.id=mt.rtu_id and r.cons_id=c.id and r.id=js.rtu_id and mt.mp_id=js.mp_id and mt.resident_id=res.id " +
					"and js.op_date >=" + sdate + " and js.op_date <=" + edate;
		String sql = " res.cons_no,res.describe cons_desc,js.* from rtupara r,residentpara res,meterpara mt,conspara c,yddataben.dbo.JJsRecord"+sdate.substring(0, 4)+" js where r.app_type=3 and r.id=res.rtu_id and r.id=mt.rtu_id and r.cons_id=c.id and r.id=js.rtu_id and mt.mp_id=js.mp_id and mt.resident_id=res.id " +
					"and js.op_date >=" + sdate + " and js.op_date <=" + edate;
		
		if(!yhbh.isEmpty()){
			countSql += " and res.cons_no like '%" + yhbh + "%'";
			sql += " and res.cons_no like '%" + yhbh + "%'";
		}
		
		if(!yhmc.isEmpty()){
			countSql += " and res.describe like '%" + yhmc + "%'";
			sql += " and res.describe like '%" + yhmc + "%'";
		}
		
		if(!czy.isEmpty()){
			countSql += " and js.op_man like '%" + czy + "%'";
			sql += " and js.op_man like '%" + czy + "%'";
		}
		
		if(!org.equals("-1")){
			countSql += " and c.org_id=" + org;
			sql += " and c.org_id=" + org;
		}
		
		if(!rtu.equals("-1")){
			countSql += " and r.id=" + rtu;
			sql += " and r.id=" + rtu;
		}
		
		sql += " order by js.op_date desc,js.op_time desc) a order by a.op_date,a.op_time";
		
		sql_page.setTotalrecords(countSql);
		
		count = sql_page.getTotalrecords();
		if(pagesize == 0){
			sql = "select top "+count+" * from (select top "+ count +" " + sql;
		}else{
			if(pagesize > count){
				sql = "select top "+pagesize+" * from (select top "+ count +" " + sql;
			}else{
				sql = "select top "+pagesize+" * from (select top "+(count - pagesize*(page-1)) + sql;
			}
		}
		if(sql_page.getTotalrecords() == 0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		if (pagesize == 0) {
			pagesize = sql_page.getTotalrecords();
			sql_page.setPagesize(pagesize);
		}
		*/
	}
	
	public String execute(){
		
		return SDDef.SUCCESS;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageSize() {
		return pageSize;
	}
}
