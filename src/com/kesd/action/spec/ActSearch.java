package com.kesd.action.spec;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.YFFDef;
import com.kesd.comnt.ComntMsg;
import com.kesd.comnt.ComntProtMsg;
import com.kesd.dao.SqlPage;
import com.kesd.dbpara.YffRatePara;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;
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
	
	private String mulZhSearch(String sdate, String edate, String yhbh, String yhmc, String czy, String org, String rtu, String oper_type) throws Exception{

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
			sql = "select count(*) from yddataben.dbo.sysobjects where name = 'zyff" + isyear + "'";
			rs = j_dao.executeQuery(sql);
			if(rs.next()){
				if(rs.getInt(1)==0){
					j_dao.closeRs(rs);
					continue;
				}
			}
			j_dao.closeRs(rs);
			
			table += "union (select b.rtu_id, b.zjg_id, b.wasteno, a.busi_no,a.describe, a.addr,b.op_type,b.op_date,b.op_time,b.op_man,b.pay_type,b.pay_money,b.othjs_money,b.zb_money,b.all_money, b.shutdown_val,b.alarm1, b.alarm2, b.buy_times from ydparaben.dbo.conspara a, ydparaben.dbo.rtupara as r, yddataben.dbo.zyff"+ isyear +" b where r.cons_id=a.id and r.id=b.rtu_id and b.visible_flag=1 and b.op_date >=" + sdate + " and b.op_date <=" + edate;
			if(!yhbh.isEmpty()){
				table += " and a.busi_no like '%" + yhbh + "%'";
			}
			
			if(!yhmc.isEmpty()){
				table += " and a.describe like '%" + yhmc + "%'";
			}
			
			if(!czy.isEmpty()){
				table += " and b.op_man like '%" + czy + "%'";
			}
			
			if(!org.equals("-1")){
				table += " and a.org_id=" + org;
			}
			
			if(!rtu.equals("-1")){
				table += " and r.id=" + rtu;
			}
			
			if(!oper_type.equals("-1")){
				table += " and b.op_type=" + oper_type;
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
			//sql = "select top "+count+" * from (select top "+ count +" " + sql;
			sql = "select * from (" + table + ") x order by x.op_date, x.op_time";
		}else{
			if(pagesize > count){
				//sql = "select top "+pagesize+" * from (select top "+ count +" " + sql;
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
			
			ret.append("{id:\"" + no + "\",data:[" + no++ +",");
			
			ret.append("\"" + map.get("wasteno")+"\",");
			ret.append("\"" + CommBase.CheckString(map.get("busi_no"))+"\",");
			ret.append("\"" + map.get("describe")+"\",");
			ret.append("\"" + CommBase.CheckString(map.get("addr"))+"\",");
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_YFFOPTYPE, map.get("op_type"))+"\",");
			ret.append("\"" + CommFunc.FormatToYMD(map.get("op_date"))+ CommFunc.FormatToHMS(map.get("op_time"),2) + "\",");
			ret.append("\"" + CommBase.CheckString(map.get("op_man"))+"\",");
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_PAYTYPE, map.get("pay_type"))+"\",");
			ret.append("\"" + map.get("pay_money")+"\",");
			ret.append("\"" + map.get("othjs_money")+"\",");
			ret.append("\"" + map.get("zb_money")+"\",");
			ret.append("\"" + map.get("all_money")+"\",");
			ret.append("\"" + map.get("alarm1")+"\",");
			ret.append("\"" + map.get("alarm2")+"\",");
			ret.append("\"" + map.get("shutdown_val")+"\",");
			ret.append("\"" + map.get("buy_times")+"\"");
			
			ret.append("]},");
		}
		
		return ret.toString();
		
		/*
		String countSql = "select count(*) from ydparaben.dbo.conspara a, ydparaben.dbo.rtupara as r, yddataben.dbo.zyff"+sdate.substring(0, 4)+" b " +
				"where r.cons_id=a.id and r.id=b.rtu_id and b.op_date >="+sdate+" and b.op_date<="+edate+" and b.visible_flag=1 ";
		String sql = " b.rtu_id, b.zjg_id, b.wasteno, a.busi_no,a.describe, a.addr,b.op_type,b.op_date,b.op_time,b.op_man,b.pay_type,b.pay_money,b.othjs_money,b.zb_money,b.all_money, b.shutdown_val,b.alarm1, b.alarm2, b.buy_times from ydparaben.dbo.conspara a, ydparaben.dbo.rtupara as r, yddataben.dbo.zyff"+sdate.substring(0, 4)+" b where r.cons_id=a.id and r.id=b.rtu_id and b.visible_flag=1 and b.op_date >=" + sdate + " and b.op_date <=" + edate;
		if(!yhbh.isEmpty()){
			countSql += " and a.busi_no like '%" + yhbh + "%'";
			sql += " and a.busi_no like '%" + yhbh + "%'";
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
			countSql += " and a.org_id=" + org;
			sql += " and a.org_id=" + org;
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
		
		*/
	}
	
	public String yhxxSearch(){
		
		if(result == null || result.isEmpty()){
			result = "";
			return SDDef.SUCCESS;
		}
		
		JSONObject j_obj = JSONObject.fromObject(result);
		
		StringBuffer ret = new StringBuffer();
		
		int 			page	 = Integer.parseInt(pageNo == null? "1" : pageNo);
		int 			pagesize = Integer.parseInt(pageSize == null? "10" : pageSize);
		
		SqlPage sql_page = new SqlPage(page, pagesize);
		
		ret.append("{rows:[");
		String countSql = "select count(*) from rtupara as a, conspara as c, zjgpara as z, orgpara as o,zjgpay_state as s where a.app_type="+SDDef.APPTYPE_ZB+" and a.use_flag=1 and a.id=z.rtu_id and a.cons_id=c.id and c.org_id=o.id and c.app_type="+SDDef.APPTYPE_ZB+" and z.use_flag=1 and z.yff_flag=1 and a.id=s.rtu_id and z.zjg_id = s.zjg_id ";
		String sql = " a.id as rtu_id,z.zjg_id as zjg_id,o.describe as org_desc,c.busi_no,c.describe as cons_desc,c.addr as cons_addr,c.tel_no1 as cons_tel,z.describe as zjg_desc "
				+ "from rtupara as a, conspara as c, zjgpara as z, orgpara as o,zjgpay_state as s where a.app_type="+SDDef.APPTYPE_ZB+" and a.use_flag=1 and a.id=z.rtu_id and a.cons_id=c.id and c.org_id=o.id and c.app_type="+SDDef.APPTYPE_ZB+" and z.use_flag=1 and z.yff_flag=1 and a.id=s.rtu_id and z.zjg_id=s.zjg_id ";
		
		String org = j_obj.getString("org");
		if(!org.equals("-1")){
			countSql += " and c.org_id=" + org;
			sql += " and c.org_id=" + org;
		}
		
		String rtu = j_obj.getString("rtu");
		if(!rtu.equals("-1")){
			countSql += " and a.id=" + rtu;
			sql += " and a.id=" + rtu;
		}
		
		String yyhh = j_obj.getString("yyhh");
		if(!yyhh.isEmpty()){
			countSql += " and c.busi_no like '%" + yyhh + "%'";
			sql += " and c.busi_no like '%" + yyhh + "%'";
		}
		
		String khmc = j_obj.getString("khmc");
		if(!khmc.isEmpty()){
			countSql += " and c.describe like '%" + khmc + "%'";
			sql += " and c.describe like '%" + khmc + "%'";
		}
		
		String lxdh = j_obj.getString("lxdh");
		if(!lxdh.isEmpty()){
			countSql += " and c.tel_no1 like '%" + lxdh + "%'";
			sql += " and c.tel_no1 like '%" + lxdh + "%'";
		}
		
		String fkdy = j_obj.getString("fkdy");
		if(!fkdy.isEmpty()){
			countSql += " and z.describe like '%" + fkdy + "%'";
			sql += " and z.describe like '%" + fkdy + "%'";
		}
		sql += " order by rtu_id desc,zjg_id desc) a order by a.rtu_id,a.zjg_id";
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
			
			ret.append("{id:\"" + no + "\",data:[" + no++ + ",");
			ret.append("\"" + map.get("org_desc") + "\",");
			ret.append("\"" + map.get("busi_no") + "\",");
			ret.append("\"" + map.get("cons_desc") + "\",");
			ret.append("\"" + CommBase.CheckString(map.get("cons_addr")) + "\",");
			ret.append("\"" + CommBase.CheckString(map.get("cons_tel")) + "\",");
			ret.append("\"" + map.get("zjg_desc") + "\"");

			ret.append("]},");
		}
		
		ret.deleteCharAt(ret.length() - 1);
		ret.append("]}");
		
		result = ret.toString();
		
		result = "{total:"+count+",page:["+result+"]}";
		
		return SDDef.SUCCESS;
	}
	
	public String yhztSearch() throws SQLException{
		
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
		
		String countSql = "select count(*) from conspara as c, zjgpay_para as b,zjgpay_state as d,zjgpay_almstate e,rtupara as r,zjgpara z "

				+ "where r.app_type="+SDDef.APPTYPE_ZB+" and c.id = r.cons_id and r.id = b.rtu_id and d.rtu_id=r.id and e.rtu_id=r.id and b.zjg_id=d.zjg_id and b.zjg_id=e.zjg_id "

				+ " and z.rtu_id=r.id and z.zjg_id=b.zjg_id and z.yff_flag=1 ";
		
		sql =   " z.describe as zjg_describe,r.describe as rtu_desc, c.busi_no,b.cacl_type,b.feectrl_type,b.pay_type,b.yffctrl_type,b.feeproj_id,b.feeproj1_id,b.feeproj2_id,b.yffalarm_id,b.prot_st,b.prot_ed,b.ngloprot_flag,b.pay_add1,b.pay_add2,b.pay_add3,b.tz_val,b.plus_time,b.use_gfh,b.hfh_time,b.hfh_shutdown,b.cs_stand,b.fee_chgf,"
				
				+ "b.fee_chgid,b.fee_chgid1,b.fee_chgid2,b.fee_chgdate,b.fee_chgtime,b.jbf_chgf,b.jbf_chgval,b.jbf_chgdate,b.jbf_chgtime,b.cbjs_flag,d.*"//zigpay_state(总加组预付费状态表)新增字段 包括在d.*里面

				+ ",e.qr_al1_1_state,e.qr_al1_2_state,e.qr_al1_3_state,e.qr_al2_1_state,e.qr_al2_2_state,e.qr_al2_3_state,e.qr_fhz_state,e.qr_fz_times,e.qr_al1_1_mdhmi,e.qr_al1_2_mdhmi,e.qr_al1_3_mdhmi,e.qr_al2_1_mdhmi,e.qr_al2_2_mdhmi,e.qr_al2_3_mdhmi,e.qr_fhz_mdhmi,e.cg_fhz_mdhmi,e.qr_al1_1_uuid,e.qr_al2_1_uuid,e.out_info, "
				
				//zjgpay_para(总加组预付费参数表)新增字段
				+ "b.powrate_flag , b.prize_flag , b.stop_flag , b.stop_begdate , b.stop_enddate, b.cb_cycle_type ,b.cb_dayhour,b.js_day,b.fxdf_flag,b.fxdf_begindate,b.local_maincalcf, "
				
				//zigpay_para(总加组预付费参数表)新增字段二:密钥版本,所属加密机,写卡户号,费率启用日期,卡表类型
				+ "b.key_version,b.cryplink_id,b.writecard_no,b.fee_begindate,b.cardmeter_type "
               
				+ " from conspara as c, zjgpay_para as b,zjgpay_state as d,zjgpay_almstate e,rtupara as r,zjgpara z"

				+ " where r.app_type="+SDDef.APPTYPE_ZB+" and c.id = r.cons_id and r.id = b.rtu_id and d.rtu_id=r.id and e.rtu_id=r.id and b.zjg_id=d.zjg_id and b.zjg_id=e.zjg_id"

				+ " and z.rtu_id=r.id and z.zjg_id=b.zjg_id and z.yff_flag=1 ";
		
        //查询cs_stand表 将字段 cs_stand转换为表cs_stand的describe字段
		String sql_csStand = "select * from cs_stand";
		JDBCDao dao_csStand=new JDBCDao();
		List<Map<String, Object>> list_csStand=dao_csStand.result(sql_csStand);
 		String rtu = j_obj.getString("rtu");
 		String rtu_id = j_obj.getString("rtu_id");
 		String zjg_id = j_obj.getString("zjg_id");
		if(!rtu.equals("-1")){
			countSql += " and r.id=" + rtu;
			sql += " and r.id=" + rtu;
		}
		//刷新查询增加的筛选条件
		if(!zjg_id.equals("-1")&&(!rtu_id.equals("-1"))){
			countSql += " and z.rtu_id = " +rtu_id +" and z.zjg_id = "+zjg_id;
			sql += " and z.rtu_id = " +rtu_id +" and z.zjg_id = "+zjg_id;
		}
		
		String yyhh = j_obj.getString("yyhh");
		if(!yyhh.isEmpty()){
			countSql += " and c.busi_no like '%" + yyhh + "%'";
			sql += " and c.busi_no like '%" + yyhh + "%'";
		}
		
		String yhmc = j_obj.getString("yhmc");
		if(!yhmc.isEmpty()){
			countSql += " and c.describe like '%" + yhmc + "%'";
			sql += " and c.describe like '%" + yhmc + "%'";
		}
		
		String org = j_obj.getString("org");
		if(!org.equals("-1")){
			countSql += " and c.org_id=" + org;
			sql += " and c.org_id=" + org;
		}
		
		String bh = j_obj.getString("lxdh");
		if(!bh.isEmpty()){
			countSql += " and c.tel_no1 like '%" + bh + "%'";
			sql += " and c.tel_no1 like '%" + bh + "%'";
		}
		
		if(j_obj.containsKey("yhzt")) {
			String yhzt = j_obj.getString("yhzt");
			if(!yhzt.isEmpty()){
				int zt = CommBase.strtoi(yhzt);
				switch(zt) {
				case 0 :		//所有
					break;
				case 1 :		//正常用户
					countSql += " and b.cacl_type<>0 and b.cacl_type is not null and d.cus_state=1";
					sql += " and b.cacl_type<>0 and b.cacl_type is not null and d.cus_state=1";
					break;
				case 2 :		//非正常用户
					countSql += " and (b.cacl_type=0 or b.cacl_type is null or d.cus_state<>1 or d.cus_state is null)";
					sql += " and (b.cacl_type=0 or b.cacl_type is null or d.cus_state<>1 or d.cus_state is null)";
					break;
				case 3 :		//余额大于1
					countSql += " and d.now_remain>1 and b.cacl_type<>0 and b.cacl_type is not null and d.cus_state=1";
					sql += " and d.now_remain>1 and b.cacl_type<>0 and b.cacl_type is not null and d.cus_state=1";
					break;
				case 4 :		//余额小于1
					countSql += " and d.now_remain<1 and b.cacl_type<>0 and b.cacl_type is not null and d.cus_state=1";
					sql += " and d.now_remain<1 and b.cacl_type<>0 and b.cacl_type is not null and d.cus_state=1";
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
					countSql += " and (e.qr_al1_1_state & 0xc0 =0xc0)";
					sql 	 += " and (e.qr_al1_1_state & 0xc0 =0xc0)";
					break;
				case 2 :		//首次声音异常
					countSql += " and (e.qr_al1_2_state & 0xc0 =0xc0)";
					sql 	 += " and (e.qr_al1_2_state & 0xc0 =0xc0)";
					break;
				case 3 :		//二次短信异常
					countSql += " and (e.qr_al2_1_state & 0xc0 =0xc0)";
					sql 	 += " and (e.qr_al2_1_state & 0xc0 =0xc0)";
					break;
				case 4 :		//报警异常
					countSql += " and ((e.qr_al1_1_state & 0xc0 =0xc0)or(e.qr_al1_2_state & 0xc0 =0xc0)or(e.qr_al2_1_state & 0xc0 =0xc0))";
					sql 	 += " and ((e.qr_al1_1_state & 0xc0 =0xc0)or(e.qr_al1_2_state & 0xc0 =0xc0)or(e.qr_al2_1_state & 0xc0 =0xc0))";
					break;
				case 5:			//首次短信正常
					countSql += " and (e.qr_al1_1_state & 0xc0 =0x80)";
					sql 	 += " and (e.qr_al1_1_state & 0xc0 =0x80)";
					break;
				case 6:			//首次声音成功
					countSql += " and (e.qr_al1_2_state & 0xc0 =0x80)";
					sql 	 += " and (e.qr_al1_2_state & 0xc0 =0x80)";
					break;
				case 7:			//二次短信成功
					countSql += " and (e.qr_al2_1_state & 0xc0 =0x80)";
					sql 	 += " and (e.qr_al2_1_state & 0xc0 =0x80)";
					break;
				case 8:			//报警成功 or and 
					countSql += " and ((e.qr_al1_1_state & 0xc0 =0x80)or(e.qr_al1_2_state & 0xc0 =0x80)or(e.qr_al2_1_state & 0xc0 =0x80))";
					sql 	 += " and ((e.qr_al1_1_state & 0xc0 =0x80)or(e.qr_al1_2_state & 0xc0 =0x80)or(e.qr_al2_1_state & 0xc0 =0x80))";
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
					countSql += " and d.cs_fhz_state = 0 and (e.qr_fhz_state & 0xc0 =0xc0)";
					sql 	 += " and d.cs_fhz_state = 0 and (e.qr_fhz_state & 0xc0 =0xc0)";
					break;
				case 2 :		//合闸异常
					countSql += " and d.cs_fhz_state = 1 and (e.qr_fhz_state & 0xc0 =0xc0)";
					sql 	 += " and d.cs_fhz_state = 1 and (e.qr_fhz_state & 0xc0 =0xc0)";
					break;
				case 3 :		//分合闸异常
					countSql += " and (e.qr_fhz_state & 0xc0 =0xc0)";
					sql 	 += " and (e.qr_fhz_state & 0xc0 =0xc0)";					
					break;
				case 4 :		//分闸成功
					countSql += " and d.cs_fhz_state = 0 and (e.qr_fhz_state & 0xc0 =0x80)";
					sql 	 += " and d.cs_fhz_state = 0 and (e.qr_fhz_state & 0xc0 =0x80)";
					break;
				case 5:			//合闸成功
					countSql += " and d.cs_fhz_state = 1 and (e.qr_fhz_state & 0xc0 =0x80)";
					sql 	 += " and d.cs_fhz_state = 1 and (e.qr_fhz_state & 0xc0 =0x80)";
					break;
				case 6:			//分合闸成功
					countSql += " and (e.qr_fhz_state & 0xc0 =0x80)";
					sql 	 += " and (e.qr_fhz_state & 0xc0 =0x80)";
					break;
				default:
					break;
				}
			}
		} 
		   
		//  20121114 add except end		
		sql += " order by r.id desc) a order by a.rtu_id";
		
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
			
			//ret.append("{id:\"" + no + "\",data:[" + no++ +",");
			
			ret.append("{id:\"" + map.get("rtu_id") + "_" + map.get("zjg_id") + "\",data:[" + no++ +",");
			ret.append("\"[" + map.get("rtu_id")+"]"+map.get("rtu_desc")+"\",");//终端名称
			ret.append("\"" + map.get("busi_no")+"\",");//营业户号
			ret.append("\"" + "["+map.get("zjg_id")+"]"+CommBase.CheckString(map.get("zjg_describe"))+"\",");//总加组编号
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_FEETYPE, map.get("cacl_type"))+"\",");//计费方式
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_PREPAYTYPE, map.get("feectrl_type"))+"\",");//费控方式
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_PAYTYPE, map.get("pay_type"))+"\",");//缴费方式
			ret.append("\"" + CommBase.CheckNum(map.get("pay_add1"))+"\",");//缴费附加值1
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_YFFSPECCTRLTYPE, map.get("yffctrl_type"))+"\",");//预付费控制类型
			ret.append("\"" + CommBase.CheckNum(map.get("tz_val"))+"\",");//透支值
			
			int j = 0;
			Object obj = map.get("feeproj_id");//费率方案号1
			if(obj == null){
				ret.append("\"\",");
			}else{
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
			obj = map.get("feeproj1_id");//费率方案号2
			if(obj == null){
				ret.append("\"\",");
			}else{
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
			obj = map.get("feeproj2_id");//费率方案号3
			if(obj == null){
				ret.append("\"\",");
			}else{
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
			obj = map.get("yffalarm_id");//报警方案
			if(obj == null){
				ret.append("\"\",");
			}else{
				for (j = 0; j < yffalarm.length; j++) {
					if(obj.toString().equals(yffalarm[j][0])){
						ret.append("\""+yffalarm[j][1]+"\",");
						break;
					}
				}
				
				if (j >= yffalarm.length) {
					ret.append("\"\",");
				}
			}
			
			ret.append("\"" + CommBase.CheckNum(map.get("prot_st"))+"时到"+CommBase.CheckNum(map.get("prot_ed"))+"时"+"\",");//保电时间
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_YESFLAG, map.get("ngloprot_flag"))+"\",");//不全局保电参数
			//zigpay_para(总加组预付费参数表)新增字段二:密钥版本,所属加密机,写卡户号,费率启用日期,卡表类型
			ret.append("\"" + CommBase.CheckNum(map.get("key_version"))+"\",");//密钥版本
			ret.append("\"" + CommBase.CheckNum(map.get("cryplink_id"))+"\",");//所属加密机
			ret.append("\"" + CommBase.CheckNum(map.get("writecard_no"))+"\",");//写卡户号
			ret.append("\"" + CommFunc.FormatToYMD(map.get("fee_begindate"))+"\",");//费率启用日期
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_CARDMETER_TYPE,map.get("cardmeter_type"))+"\",");//卡表类型
                              
			ret.append("\"" + CommBase.CheckNum(map.get("plus_time"))+"\",");//脉冲闭合时间
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_YESFLAG, map.get("use_gfh"))+"\",");//启用高负荷控制
			ret.append("\"" + CommBase.CheckNum(map.get("hfh_time"))+"\",");//连续高负荷时间
			ret.append("\"" + CommBase.CheckNum(map.get("hfh_shutdown"))+"\",");//高负荷断电值
			
			
			//将功率因数转化为相应的describe
			for(int f=0;f<list_csStand.size();f++){//功率因数标准
				Map<String, Object> map_csStand=list_csStand.get(f);
				if(map.get("cs_stand")==null || "".equals(map.get("cs_stand"))){
					ret.append("\""+CommBase.CheckString(map.get("cs_stand"))+"\",");
					break;
				}else  if(map.get("cs_stand")!=null&& map_csStand.get("id").equals(map.get("cs_stand"))){
					ret.append("\""+CommBase.CheckString(map_csStand.get("describe"))+"\",");
					break;
				}
			}
			
			
			//zjgpay_para新增加字段1:"力调算费标志","奖罚标志","报停标志",	"报停开始日期","报停结束日期"
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_YESFLAG, map.get("powrate_flag"))+"\",");//力调算费标志
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_PRIZEFLAG, map.get("prize_flag"))+"\",");//奖罚标志
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_YESFLAG, map.get("stop_flag"))+"\",");//报停标志
			ret.append("\"" + CommFunc.FormatToYMD(map.get("stop_begdate"))+"\",");//报停开始日期
			ret.append("\"" + CommFunc.FormatToYMD(map.get("stop_enddate"))+"\",");//报停结束日期
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_CB_CYCLE_TYPE, map.get("cb_cycle_type"))+"\",");//抄表周期			
			ret.append("\"" + CommFunc.FormatToDH(map.get("cb_dayhour"))+"\",");//抄表日
            ret.append("\"" + Rd.getDict(Dict.DICTITEM_YESFLAG, map.get("cbjs_flag"))+"\",");//抄表日结算标志0 不结算， 1 结算
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_YESFLAG, map.get("fxdf_flag"))+"\",");//是否发行电费
			ret.append("\"" + CommFunc.FormatToYMD(map.get("fxdf_begindate"))+"\",");//发行起始日期
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_YESFLAG, map.get("local_maincalcf"))+"\",");//主站算费标志
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_YESFLAG, map.get("fee_chgf"))+"\",");//费率更改标志
			ret.append("\"" + CommBase.CheckNum(map.get("fee_chgid"))+":"+CommBase.CheckNum(map.get("fee_chgid1"))+":"+CommBase.CheckNum(map.get("fee_chgid2"))+"-["+CommBase.CheckNum(map.get("fee_chgdate"))+"-"+CommBase.CheckNum(map.get("fee_chgtime"))+"]\",");//费率更改内容
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_YESFLAG, map.get("jbf_chgf"))+"\",");//基本费更改标志
			ret.append("\"" + CommBase.CheckNum(map.get("jbf_chgval"))+"-["+CommBase.CheckNum(map.get("jbf_chgdate"))+"-"+CommBase.CheckNum(map.get("jbf_chgtime"))+"]\",");//基本费更改内容
			
			//预付费参数结束,预付费状态开始
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_YFFCUSSTATE, map.get("cus_state"))+"\",");//用户状态
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_YFFOPTYPE, map.get("op_type"))+"\",");//操作类型
			ret.append("\"" + CommFunc.FormatToYMD(map.get("op_date"))+CommFunc.FormatToHMS(map.get("op_time"),1)+"\",");//操作时间
			ret.append("\"" + CommBase.CheckNum(map.get("pay_money")) + "+"+CommBase.CheckNum(map.get("othjs_money"))+"+"+CommBase.CheckNum(map.get("zb_money"))+"="+CommBase.CheckNum(map.get("all_money"))+"\",");//缴费金额
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("buy_dl"))+"-"+CommFunc.FormatBDDLp000(map.get("pay_bmc"))+"\",");//购电量-表码差
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("alarm_val1"))+"-"+CommFunc.FormatBDDLp000(map.get("alarm_val2"))+"\",");//报警值1&2
			ret.append("\"" + CommBase.CheckNum(map.get("shutdown_val"))+"\",");//断电金额
			ret.append("\"" + CommBase.CheckNum(map.get("buy_times"))+"\",");//购电次数
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("now_remain"))+"\",");//当前剩余
			ret.append("\"" + CommBase.CheckNum(map.get("total_gdz"))+"\",");//累计购电值
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("bj_bd"))+"\",");//报警门限
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("tz_bd"))+"\",");//跳闸门限
			ret.append("\"" + CommFunc.FormatToYMD(map.get("hb_date"))+CommFunc.FormatToHMS(map.get("hb_time"),2)+"\",");//换表时间
			ret.append("\"" + CommFunc.FormatToYMD(map.get("kh_date"))+"\",");//开户日期
			ret.append("\"" + CommFunc.FormatToYMD(map.get("xh_date"))+"\",");//销户日期
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("jsbd_zyz"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd1_zyz"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd2_zyz"))+"\",");//结算总表底
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("jsbd_zyj"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd1_zyj"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd2_zyj"))+"\",");//结算尖表底
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("jsbd_zyf"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd1_zyf"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd2_zyf"))+"\",");//结算峰表底
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("jsbd_zyp"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd1_zyp"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd2_zyp"))+"\",");//结算平表底
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("jsbd_zyg"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd1_zyg"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd2_zyg"))+"\",");//结算谷表底
			//zjgpay_para新增加字段3:结算无功表底
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("jsbd_zwz"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd1_zwz"))+"-"+CommFunc.FormatBDDLp000(map.get("jsbd2_zwz"))+"\",");//结算无功表底
			                  
			ret.append("\"" + CommFunc.FormatToYMD(map.get("jsbd_ymd"))+"\",");//结算时间
			ret.append("\"" + CommFunc.FormatToMDHMin(map.get("calc_mdhmi"))+"\",");//算费时间
			ret.append("\"" + CommFunc.FormatToYMD(map.get("calc_bdymd"))+"\",");//算费表底时间
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("calc_zyz"))+"-"+CommFunc.FormatBDDLp000(map.get("calc1_zyz"))+"-"+CommFunc.FormatBDDLp000(map.get("calc2_zyz"))+"\",");//算费总表底
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("calc_zyj"))+"-"+CommFunc.FormatBDDLp000(map.get("calc1_zyj"))+"-"+CommFunc.FormatBDDLp000(map.get("calc2_zyj"))+"\",");//算费尖表底
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("calc_zyf"))+"-"+CommFunc.FormatBDDLp000(map.get("calc1_zyf"))+"-"+CommFunc.FormatBDDLp000(map.get("calc2_zyf"))+"\",");//算费峰表底
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("calc_zyp"))+"-"+CommFunc.FormatBDDLp000(map.get("calc1_zyp"))+"-"+CommFunc.FormatBDDLp000(map.get("calc2_zyp"))+"\",");//算费平表底
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("calc_zyg"))+"-"+CommFunc.FormatBDDLp000(map.get("calc1_zyg"))+"-"+CommFunc.FormatBDDLp000(map.get("calc2_zyg"))+"\",");//算费谷表底
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("calc_zwz"))+"-"+CommFunc.FormatBDDLp000(map.get("calc1_zwz"))+"-"+CommFunc.FormatBDDLp000(map.get("calc2_zwz"))+"\",");//算费无功表底
			
			ret.append("\"" + CommFunc.FormatBDDLp000(
					+ CommBase.strtof(CommBase.CheckNum(map.get("ele_money")))
					+ CommBase.strtof(CommBase.CheckNum(map.get("jbf_money")))
					+ CommBase.strtof(CommBase.CheckNum(map.get("powrate_money")))
					+ CommBase.strtof(CommBase.CheckNum(map.get("other_money")))
					)+ "\",");	//增加总电费=电度电费+基本电费+力调电费+其他电费

			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("ele_money"))+"\",");//电度电费
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("jbf_money"))+"\",");//基本费电费
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("powrate_money"))+"\",");//力调电费
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("other_money"))+"\",");//其他电费
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("total_yzbdl"))+"\",");//有功追补电量
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("total_wzbdl"))+"\",");//无功追补电量
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("real_powrate"))+"\",");//实际功率因数
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("total_ydl"))+"\",");//有功累计电量
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("total_wdl"))+"\",");//无功累计电量
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("fxdf_iall_money"))+"\",");//发行电费当月缴费
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("zbele_money"))+"\",");//追补电度电费
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("zbjbf_money"))+"\",");//追补基本费
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("fxdf_remain"))+"\",");//发行电费剩余金额
			ret.append("\"" + CommFunc.FormatToYM(map.get("fxdf_ym"))+"\",");//发行电费年月
			ret.append("\"" + CommFunc.FormatToYMD(map.get("fxdf_data_ymd"))+"\",");//发行电费数据日期
			ret.append("\"" + CommFunc.FormatToMDHMin(map.get("fxdf_calc_mdhmi"))+"\",");//发行电费算费时间
			obj = map.get("cs_al1_state");
			ret.append("\"" + (obj == null?"":Integer.parseInt(map.get("cs_al1_state")+"") == 0 ? "正常":"报警")+"\",");//报警1状态
			obj = map.get("cs_al2_state");
			ret.append("\"" + (obj==null?"":Integer.parseInt(map.get("cs_al2_state")+"") == 0 ? "正常":"报警")+"\",");//报警2状态
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_SWITCH_STATE1, map.get("cs_fhz_state"))+"\",");//分合闸状态
			
			ret.append("\"" + CommFunc.FormatToMDHMin(map.get("al1_mdhmi"))+"\",");//报警1改变时间
			ret.append("\"" + CommFunc.FormatToMDHMin(map.get("al2_mdhmi"))+"\",");//报警2改变时间
			ret.append("\"" + CommFunc.FormatToMDHMin(map.get("fhz_mdhmi"))+"\",");//分合闸改变时间
			ret.append("\"" + CommBase.CheckString(map.get("yc_flag1"))+"\",");//异常标志1
			ret.append("\"" + CommBase.CheckString(map.get("yc_flag2"))+"\",");//异常标志2
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("fz_zyz"))+"-"+CommFunc.FormatBDDLp000(map.get("fz1_zyz"))+"-"+CommFunc.FormatBDDLp000(map.get("fz2_zyz"))+"\",");//分闸时总表底
			
			//预付费状态结束,报警及控制参数开始
			//预付费状态结束,报警及控制参数开始
			
			obj = map.get("qr_al1_1_state");//报警1短信
			if(obj == null){
				ret.append("\"\",");
			}else{
				tmp = Integer.parseInt(map.get("qr_al1_1_state")+"");
				ret.append("\""+Rd.getDict(Dict.DICTITEM_YFFQRZT, tmp>>6)+"-["+(tmp&0x3f)+"]\",");
			}
			obj = map.get("qr_al1_2_state");//报警1声音
			if(obj == null){
				ret.append("\"\",");
			}else{
				tmp = Integer.parseInt(obj.toString());
				ret.append("\""+Rd.getDict(Dict.DICTITEM_YFFQRZT, tmp>>6)+"-["+(tmp&0x3f)+"]\",");
			}
			obj = map.get("qr_fhz_state");//分合闸确认
			if(obj == null){
				ret.append("\"\",");
			}else{
				tmp = Integer.parseInt(obj.toString());
				ret.append("\""+Rd.getDict(Dict.DICTITEM_YFFQRZT, tmp>>6)+"-["+(tmp&0x3f)+"]\",");
			}
			obj = map.get("qr_al2_1_state");//报警2短信
			if(obj == null){
				ret.append("\"\",");
			}else{
				tmp = Integer.parseInt(obj.toString());
				ret.append("\""+Rd.getDict(Dict.DICTITEM_YFFQRZT, tmp>>6)+"-["+(tmp&0x3f)+"]\",");
			}
			obj = map.get("qr_al2_2_state");//报警2声音
			if(obj == null){
				ret.append("\"\",");
			}else{
				tmp = Integer.parseInt(obj.toString());
				ret.append("\""+Rd.getDict(Dict.DICTITEM_YFFQRZT, tmp>>6)+"-["+(tmp&0x3f)+"]\",");
			}
		
			
			ret.append("\"" + CommBase.CheckNum(map.get("qr_fz_times"))+"\",");//分闸次数
			ret.append("\"" + CommFunc.FormatToMDHMin(map.get("qr_al1_1_mdhmi"))+"\",");//报警1短信时间
			ret.append("\"" + CommFunc.FormatToMDHMin(map.get("qr_al1_2_mdhmi"))+"\",");//报警1声音时间
			ret.append("\"" + CommFunc.FormatToMDHMin(map.get("qr_fhz_mdhmi"))+"\",");//分合闸发送时间
			ret.append("\"" + CommFunc.FormatToMDHMin(map.get("qr_al2_1_mdhmi"))+"\",");//报警2短信时间
			ret.append("\"" + CommFunc.FormatToMDHMin(map.get("qr_al2_2_mdhmi"))+"\",");//报警2声音时间
			ret.append("\"" + CommFunc.FormatToMDHMin(map.get("cg_fhz_mdhmi"))+"\",");//成功分合闸时间
			ret.append("\"" + CommBase.CheckNum(map.get("qr_al1_1_uuid"))+"\",");//报警1短信UUID
			ret.append("\"" + CommBase.CheckNum(map.get("qr_al2_1_uuid"))+"\",");//报警2短信UUID
			ret.append("\"" + CommBase.CheckString(map.get("out_info"))+"\"");//信息输出
			
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
			sql = "select count(*) from yddataben.dbo.sysobjects where name = 'zfk" + isyear + "'";
			rs = j_dao.executeQuery(sql);
			if(rs.next()){
				if(rs.getInt(1)==0){
					j_dao.closeRs(rs);
					continue;
				}
			}
			j_dao.closeRs(rs);
		table += "union (select a.busi_no,a.describe, a.addr,b.op_date,b.op_time, b.op_type,b.op_man from conspara a,rtupara as r,yddataben.dbo.zfk"+ isyear+" b where r.cons_id=a.id and r.id=b.rtu_id and b.op_date>="+sdate+" and b.op_date<=" + edate;
		if(!yhbh.isEmpty()){
			//countSql += " and a.busi_no like '%" + yhbh + "%'";
			table += " and a.busi_no like '%" + yhbh + "%'";
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
			//countSql += " and a.org_id=" + org;
			table += " and a.org_id=" + org;
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
		
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(ComntProtMsg.YFF_GY_CTRL_KEEPON,	"科林扩展保电");
		map.put(ComntProtMsg.YFF_GY_CTRL_KEEPOFF,	"取消科林扩展保电");
		map.put(ComntProtMsg.YFF_GY_CTRL_CUT,		"负控限电");
		map.put(ComntProtMsg.YFF_GY_CTRL_ON,		"合闸允许");
		map.put(ComntProtMsg.YFF_GY_CTRL_ALARMON,	"催费告警投入");
		map.put(ComntProtMsg.YFF_GY_CTRL_ALARMOFF,	"催费告警解除");
		
		int no = pagesize * (sql_page.getCurrentpage() - 1) + 1;
		
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> ret_map = list.get(i);
			
			ret.append("{id:\"" + no + "\",data:[" + no++ +",");
			ret.append("\""+ret_map.get("busi_no")+"\",");
			ret.append("\""+ret_map.get("describe")+"\",");
			ret.append("\""+CommBase.CheckString(ret_map.get("addr"))+"\",");
		
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
		/*
		String countSql = "select count(*) from conspara a,rtupara as r,yddataben.dbo.zfk"+sdate.substring(0, 4)+" b where r.cons_id=a.id and r.id=b.rtu_id and b.op_date>="+sdate+" and b.op_date<=" + edate;
		String sql = " a.busi_no,a.describe, a.addr,b.op_date,b.op_time, b.op_type,b.op_man from conspara a,rtupara as r,yddataben.dbo.zfk"+sdate.substring(0, 4)+" b where r.cons_id=a.id and r.id=b.rtu_id and b.op_date>="+sdate+" and b.op_date<=" + edate;
		
		if(!yhbh.isEmpty()){
			countSql += " and a.busi_no like '%" + yhbh + "%'";
			sql += " and a.busi_no like '%" + yhbh + "%'";
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
			countSql += " and a.org_id=" + org;
			sql += " and a.org_id=" + org;
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
		
		return ret.toString();
	}
	

	/**
	 * 查询用户余额
	 * 以总加组为单位，查询每个总加组的结算日表底和当前表底。
	 * 表底/金额方式：（表码差*倍率）根据断电值、报警值 ，  计算剩余金额及当前状态（正常区间，报警区间，欠费）
	 * 表底/金额方式：开始日期：操作日，结束日期：今天。
	 * 主站方式：从数据库中查询各个值。
	 * 
	 * @return
	 */
	public String yhyeSearch() throws Exception {
		
		if(result == null || result.isEmpty()){
			result = "";
			return SDDef.SUCCESS;
		}
		
		JSONObject j_obj = JSONObject.fromObject(result);
		
		StringBuffer ret = new StringBuffer();
		
		int 			page	 = Integer.parseInt(pageNo == null? "1" : pageNo);
		int 			pagesize = Integer.parseInt(pageSize == null? "10" : pageSize);
		
		SqlPage sql_page = new SqlPage(page, pagesize);
		
		ret.append("{rows:[");
		String countSql = "select count(*) from rtupara as a, conspara as c, zjgpara as z, orgpara as o,zjgpay_state as s where a.app_type="+SDDef.APPTYPE_ZB+" and a.use_flag=1 and a.id=z.rtu_id and a.cons_id=c.id and c.org_id=o.id and c.app_type="+SDDef.APPTYPE_ZB+" and z.use_flag=1 and z.yff_flag=1 and a.id=s.rtu_id and z.zjg_id = s.zjg_id and  s.cus_state = " + SDDef.YFF_CUSSTATE_NORMAL;
		String sql = " z.rtu_id,z.zjg_id, o.describe as org_desc, c.describe as cons_desc,c.tel_no2 as cons_telno," +
					 " p.feectrl_type,p.cacl_type,p.feeproj_id,p.feeproj1_id,p.feeproj2_id,p.pay_add1,z.clp_num,z.clp_ids,z.zf_flag," +
					 " s.op_date,s.jsbd_ymd,s.calc_bdymd,s.buy_dl,s.alarm_val1,s.shutdown_val," +
					 " s.jsbd_zyz,s.jsbd_zyj,s.jsbd_zyf,s.jsbd_zyp,s.jsbd_zyg,s.jsbd1_zyz,s.jsbd1_zyj,s.jsbd1_zyf,s.jsbd1_zyp,s.jsbd1_zyg,s.jsbd2_zyz,s.jsbd2_zyj,s.jsbd2_zyf,s.jsbd2_zyp,s.jsbd2_zyg," +
					 " s.calc_zyz,s.calc_zyj,s.calc_zyf,s.calc_zyp,s.calc_zyg,s.calc1_zyz,s.calc1_zyj,s.calc1_zyf,s.calc1_zyp,s.calc1_zyg,s.calc2_zyz,s.calc2_zyj,s.calc2_zyf,s.calc2_zyp,s.calc2_zyg" +
					 " from rtupara as a, conspara as c, zjgpara as z, orgpara as o,zjgpay_state as s,zjgpay_para p " +  
					 " where a.app_type=1 and a.use_flag=1 and a.id=z.rtu_id and a.cons_id=c.id and c.org_id=o.id and c.app_type=1 and z.use_flag=1 and z.yff_flag=1 and a.id=s.rtu_id and a.id=p.rtu_id and z.zjg_id = s.zjg_id and z.zjg_id = p.zjg_id  and  s.cus_state = " + SDDef.YFF_CUSSTATE_NORMAL;
 
		
		String org = j_obj.getString("org");
		if(!org.equals("-1")){
			countSql += " and c.org_id=" + org;
			sql += " and c.org_id=" + org;
		}
		
		String rtu = j_obj.getString("rtu");
		if(!rtu.equals("-1")){
			countSql += " and a.id=" + rtu;
			sql += " and a.id=" + rtu;
		}
		
		String yyhh = j_obj.getString("yyhh");
		if(!yyhh.isEmpty()){
			countSql += " and c.busi_no like '%" + yyhh + "%'";
			sql += " and c.busi_no like '%" + yyhh + "%'";
		}
		
		String khmc = j_obj.getString("khmc");
		if(!khmc.isEmpty()){
			countSql += " and c.describe like '%" + khmc + "%'";
			sql += " and c.describe like '%" + khmc + "%'";
		}
		
		String lxdh = j_obj.getString("lxdh");
		if(!lxdh.isEmpty()){
			countSql += " and c.tel_no1 like '%" + lxdh + "%'";
			sql += " and c.tel_no1 like '%" + lxdh + "%'";
		}
		
		String fkdy = j_obj.getString("fkdy");
		if(!fkdy.isEmpty()){
			countSql += " and z.describe like '%" + fkdy + "%'";
			sql += " and z.describe like '%" + fkdy + "%'";
		}
		sql += " order by z.rtu_id desc,z.zjg_id desc) a order by a.rtu_id,a.zjg_id";
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
		
		List<YffRatePara> listrate = Rd.getYffRate();	
		
		int no = pagesize * (sql_page.getCurrentpage() - 1) + 1;
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			String info = "";
			
			int feectrl_type = CommBase.strtoi(CommBase.CheckString(map.get("feectrl_type")));
			int cacl_type = CommBase.strtoi(CommBase.CheckString(map.get("cacl_type")));
			ret.append("{id:\"" + no + "\",data:[" + no++ + ",");//序号,
			ret.append("\"" + map.get("org_desc") + "\",");//所属供电所,
			ret.append("\"" + map.get("cons_desc") + "\",");//费控用户,
			ret.append("\"" + map.get("cons_telno") + "\",");//用户电话,
			
			if (feectrl_type ==  SDDef.YFF_FEECTRL_TYPE_RTU){//终端费控
				double buy_dl =0;
				if(cacl_type == SDDef.YFF_CACL_TYPE_MONEY){
					buy_dl = CommBase.strtof(CommBase.CheckNum(map.get("buy_dl")));
				}
				else {
					buy_dl = CommBase.strtof(CommBase.CheckNum(map.get("shutdown_val")));
				}
				
				DateFormat 	f 		= new SimpleDateFormat("yyyyMMdd");
				String 		symd 	= CommBase.CheckString(map.get("op_date"));
				String 		eymd 	= f.format(new Date());		
				
				int 		clp_num	= CommBase.strtoi(CommBase.CheckNum(map.get("clp_num")));
				String 		clp_ids = CommBase.CheckString(map.get("clp_ids"));
				String 		zf_flag = CommBase.CheckString(map.get("zf_flag"));
				byte[]		zfFlag  = new byte[3];
				
				YFFDef.YFF_DBBD[]  bd_start	= new YFFDef.YFF_DBBD[ComntMsg.YFF_ZJGPAY_METERNUM];		//表底信息--起码
				YFFDef.YFF_DBBD[]  bd_end	= new YFFDef.YFF_DBBD[ComntMsg.YFF_ZJGPAY_METERNUM];		//表底信息--止码
				
				short[] 		feepjid ={1,1,1};
				feepjid[0] = (short) CommBase.strtoi(CommBase.CheckString(map.get("feeproj_id")));
				feepjid[1] = (short) CommBase.strtoi(CommBase.CheckString(map.get("feeproj1_id")));
				feepjid[2] = (short) CommBase.strtoi(CommBase.CheckString(map.get("feeproj2_id")));
				JSONObject 	json_bd = null;
				try {
					json_bd = readSEbd(CommBase.CheckNum(map.get("rtu_id")),CommBase.CheckNum(map.get("zjg_id")),
							symd,eymd,clp_num,clp_ids,bd_start,bd_end,zf_flag,zfFlag);
				} catch (Exception e) {
					e.printStackTrace();
					
					ret.append("\"\",");//断电金额/表底,
					
					ret.append("\"\",");
					ret.append("\"\",");
					ret.append("\"\",");
					ret.append("\"\",");
					ret.append("\"\",");
					ret.append("\"\",");
					ret.append("\"\",");
					ret.append("\"\",");
					ret.append("\"\",");
					ret.append("\"\",");
					ret.append("\"\",");
					ret.append("\"\",");
					ret.append("\"\",");
					ret.append("\"\",");
					ret.append("\"\"");
					ret.append("]},");
					continue;
				}
				
				boolean hasdata  = Boolean.parseBoolean(json_bd.get("hasdataflag").toString());	
				
				ret.append("\"" + CommFunc.FormatBDDLp000(buy_dl) + "\",");//断电金额/表底,
				
				ret.append("\"" + CommFunc.FormatToYMD(symd)+ "\",");//起始日期,
				ret.append("\"" + CommFunc.FormatBDDLp000(bd_start[0].bd_zy ) + "-"	+ CommFunc.FormatBDDLp000(bd_start[1].bd_zy ) + "-"	+ CommFunc.FormatBDDLp000(bd_start[2].bd_zy )	+ "\",");//起始总表底,
				ret.append("\"" + CommFunc.FormatBDDLp000(bd_start[0].bd_zyj) + "-"	+ CommFunc.FormatBDDLp000(bd_start[1].bd_zyj) + "-"	+ CommFunc.FormatBDDLp000(bd_start[2].bd_zyj)	+ "\",");//起始尖表底,
				ret.append("\"" + CommFunc.FormatBDDLp000(bd_start[0].bd_zyf) + "-"	+ CommFunc.FormatBDDLp000(bd_start[1].bd_zyf) + "-"	+ CommFunc.FormatBDDLp000(bd_start[2].bd_zyf)	+ "\",");//起始峰表底,
				ret.append("\"" + CommFunc.FormatBDDLp000(bd_start[0].bd_zyp) + "-"	+ CommFunc.FormatBDDLp000(bd_start[1].bd_zyp) + "-"	+ CommFunc.FormatBDDLp000(bd_start[2].bd_zyp)	+ "\",");//起始平表底,
				ret.append("\"" + CommFunc.FormatBDDLp000(bd_start[0].bd_zyg) + "-"	+ CommFunc.FormatBDDLp000(bd_start[1].bd_zyg) + "-"	+ CommFunc.FormatBDDLp000(bd_start[2].bd_zyg)	+ "\",");//起始谷表底,
				
				ret.append("\"" + CommFunc.FormatToYMD(eymd)+ "\",");//算费数据日期,
				ret.append("\"" + CommFunc.FormatBDDLp000(bd_end[0].bd_zy ) + "-" + CommFunc.FormatBDDLp000(bd_end[1].bd_zy ) + "-" + CommFunc.FormatBDDLp000(bd_end[2].bd_zy )	+ "\",");//算费总表底,
				ret.append("\"" + CommFunc.FormatBDDLp000(bd_end[0].bd_zyj) + "-" + CommFunc.FormatBDDLp000(bd_end[1].bd_zyj) + "-" + CommFunc.FormatBDDLp000(bd_end[2].bd_zyj)	+ "\",");//算费尖表底,
				ret.append("\"" + CommFunc.FormatBDDLp000(bd_end[0].bd_zyf) + "-" + CommFunc.FormatBDDLp000(bd_end[1].bd_zyf) + "-" + CommFunc.FormatBDDLp000(bd_end[2].bd_zyf)	+ "\",");//算费峰表底,
				ret.append("\"" + CommFunc.FormatBDDLp000(bd_end[0].bd_zyp) + "-" + CommFunc.FormatBDDLp000(bd_end[1].bd_zyp) + "-" + CommFunc.FormatBDDLp000(bd_end[2].bd_zyp)	+ "\",");//算费平表底,
				ret.append("\"" + CommFunc.FormatBDDLp000(bd_end[0].bd_zyg) + "-" + CommFunc.FormatBDDLp000(bd_end[1].bd_zyg) + "-" + CommFunc.FormatBDDLp000(bd_end[2].bd_zyg) 	+ "\",");//算费谷表底,
				
				double curremian = 0.0;
				double pay_add1  = CommBase.strtof(CommBase.CheckNum(map.get("pay_add1")));
				double beilv 	 = CommBase.strtof(json_bd.get("beilv").toString());
				double alarm_val1 = CommBase.strtof(CommBase.CheckNum(map.get("alarm_val1")));
				
				if(hasdata){
					if(cacl_type == SDDef.YFF_CACL_TYPE_MONEY ){					
						curremian = GetLocMoneyRemain(bd_start, bd_end, feepjid, clp_num, zfFlag, pay_add1, listrate, beilv, symd, eymd, buy_dl);
						if (curremian <= 0)		info = "已经欠费。";
						else if (curremian < alarm_val1)		info = "已经报警。";
					}
					else{
						JSONObject  j = GetLocBdRemain(bd_end, buy_dl, feepjid, clp_num, zfFlag, listrate, beilv); 
						info = CommBase.CheckString(j.get("info"));
						if(!info.isEmpty()){
							curremian = 0;							
						}else{
							curremian = CommBase.strtof(j.get("curremian").toString());
							if (curremian <= 0)		info = "已经欠费。";
							else if ((bd_end[0].bd_zy + bd_end[1].bd_zy + bd_end[2].bd_zy) > alarm_val1) info = "已经报警。 ";
						}
					}
				}else{
					info = CommBase.CheckString(json_bd.get("info"));
				}				
				
				ret.append("\"" + CommFunc.FormatBDDLp000(curremian)	+ "\",");//当前剩余,
				
			}
			else {//主站费控
				ret.append("\"" + CommFunc.FormatBDDLp000(map.get("shutdown_val")) + "\",");//断电金额/表底,
				ret.append("\"" + CommFunc.FormatToYMD(map.get("jsbd_ymd")) + "\",");//起始日期,
				ret.append("\"" + CommFunc.FormatBDDLp000(map.get("jsbd_zyz")) + "-" + CommFunc.FormatBDDLp000(map.get("jsbd_zyz1")) + "-" + CommFunc.FormatBDDLp000(map.get("jsbd_zyz2")) + "\",");//起始总表底,
				ret.append("\"" + CommFunc.FormatBDDLp000(map.get("jsbd_zyj")) + "-" + CommFunc.FormatBDDLp000(map.get("jsbd_zyj1")) + "-" + CommFunc.FormatBDDLp000(map.get("jsbd_zyj2")) + "\",");//起始尖表底,
				ret.append("\"" + CommFunc.FormatBDDLp000(map.get("jsbd_zyf")) + "-" + CommFunc.FormatBDDLp000(map.get("jsbd_zyf1")) + "-" + CommFunc.FormatBDDLp000(map.get("jsbd_zyf2")) + "\",");//起始峰表底,
				ret.append("\"" + CommFunc.FormatBDDLp000(map.get("jsbd_zyp")) + "-" + CommFunc.FormatBDDLp000(map.get("jsbd_zyp1")) + "-" + CommFunc.FormatBDDLp000(map.get("jsbd_zyp2")) + "\",");//起始平表底,
				ret.append("\"" + CommFunc.FormatBDDLp000(map.get("jsbd_zyg")) + "-" + CommFunc.FormatBDDLp000(map.get("jsbd_zyg1")) + "-" + CommFunc.FormatBDDLp000(map.get("jsbd_zyg2")) + "\",");//起始谷表底,
				ret.append("\"" + CommFunc.FormatToYMD(map.get("calc_bdymd")) + "\",");//算费数据日期,
				ret.append("\"" + CommFunc.FormatBDDLp000(map.get("calc_zyz")) + "-" + CommFunc.FormatBDDLp000(map.get("calc_zyz1")) + "-" + CommFunc.FormatBDDLp000(map.get("calc_zyz2")) + "\",");//算费总表底,
				ret.append("\"" + CommFunc.FormatBDDLp000(map.get("calc_zyj")) + "-" + CommFunc.FormatBDDLp000(map.get("calc_zyj1")) + "-" + CommFunc.FormatBDDLp000(map.get("calc_zyj2")) + "\",");//算费尖表底,
				ret.append("\"" + CommFunc.FormatBDDLp000(map.get("calc_zyf")) + "-" + CommFunc.FormatBDDLp000(map.get("calc_zyf1")) + "-" + CommFunc.FormatBDDLp000(map.get("calc_zyf2")) + "\",");//算费峰表底,
				ret.append("\"" + CommFunc.FormatBDDLp000(map.get("calc_zyp")) + "-" + CommFunc.FormatBDDLp000(map.get("calc_zyp1")) + "-" + CommFunc.FormatBDDLp000(map.get("calc_zyp2")) + "\",");//算费平表底,
				ret.append("\"" + CommFunc.FormatBDDLp000(map.get("calc_zyg")) + "-" + CommFunc.FormatBDDLp000(map.get("calc_zyg1")) + "-" + CommFunc.FormatBDDLp000(map.get("calc_zyg2")) + "\",");//算费谷表底,
				ret.append("\"" + CommFunc.FormatBDDLp000(map.get("now_remain")) + "\",");//当前剩余,
			}
			
			ret.append("\"" + CommFunc.FormatBDDLp000(map.get("alarm_val1")) + "\",");//报警金额/表底,
			ret.append("\"" + info + "\"");//信息输出
			ret.append("]},");
		}
		
		ret.deleteCharAt(ret.length() - 1);
		ret.append("]}");
		
		result = ret.toString();
		
		result = "{total:"+count+",page:["+result+"]}";
		
		return SDDef.SUCCESS;
	}
	
	//取得 测点ID、倍率、结算起止表底。
	JSONObject readSEbd(String rtu_id, String zjg_id, String symd, String eymd, int clp_num, String clp_ids, YFFDef.YFF_DBBD[]  bd_start,YFFDef.YFF_DBBD[]  bd_end,
						String zf_flag, byte[] zfFlag ) throws Exception
	{
		JSONObject 	rtn_json = new JSONObject();
		JDBCDao 	j_dao 	 = new JDBCDao();
		ResultSet 	rs 		 = null;	
		
		boolean sdataflag  = false;
		boolean edataflag  = false;
		
		for (int i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
			bd_start[i]  = new YFFDef.YFF_DBBD();
			bd_end[i] 	 = new YFFDef.YFF_DBBD();
		}
		
		double beilv    = 1;
		
		//取得测点ID
		int ret_num = 0;
		for (int i = 0; i < clp_ids.length(); i++) {
			if (clp_ids.charAt(i) == '0') continue;
			else {
				bd_start[ret_num].mp_id = (short)i;
				bd_end[ret_num].mp_id = (short)i;
				zfFlag[ret_num] = (byte) (zf_flag.charAt(i) == '0' ? 0 : 1) ;
			}
			ret_num++;
			if (ret_num >= bd_start.length ||	ret_num >= clp_num) break;
		}	
		
		try{
			//取得倍率
			if(ret_num > 0){
				String sql_cmd2 = "select mp.pt_ratio, mp.ct_ratio from mppara as mp where mp.rtu_id=" + rtu_id + " and mp.id=" + bd_start[0].mp_id;
				
				rs = j_dao.executeQuery(sql_cmd2);
				
				if (rs.next()) {
					Double pt_ratio  = rs.getDouble("pt_ratio");
					Double ct_ratio  = rs.getDouble("ct_ratio");
					
					beilv = pt_ratio * ct_ratio;
					beilv = beilv < 1 ? 1 : beilv;
				}
			}
			rtn_json.put("beilv",beilv);
			
			if(symd.equals("0")|| symd == null || symd.isEmpty() || symd.equals("")){
				rtn_json.put("info","无法跟据操作日期 " + symd + " 取得起始表码。");
				rtn_json.put("hasdataflag",false);
				return rtn_json;
			}
			//取得表底
			String sql  = "select * from yddataben.dbo.zdaybddl";
			String wSql = " where rtu_id=" + rtu_id + " and (mp_id=" + bd_start[0].mp_id + " or mp_id=" + bd_start[1].mp_id + " or mp_id=" + bd_start[2].mp_id + " ) and (date=" + symd + " or date =" + eymd + ")";
			if(symd.substring(0,6).equals(eymd.substring(0,6))){
				sql = sql + symd.substring(0,6) + wSql;
			}else{
				sql = "(" + sql + symd.substring(0,6) + wSql + ") union (" + sql + eymd.substring(0,6) + wSql + ")";
			}
			
			rs = j_dao.executeQuery(sql);
			
			while(rs.next()){
				
				if(rs.getString("date").equals(symd))
				{
					sdataflag = true;
					for(int ii = 0; ii < clp_num;ii++){
						if(bd_start[ii].mp_id == rs.getShort("mp_id")){
							bd_start[ii].bd_zy  = rs.getDouble("bd_zy");
							bd_start[ii].bd_zyj = rs.getDouble("bd_zy_fl1");
							bd_start[ii].bd_zyf = rs.getDouble("bd_zy_fl2");
							bd_start[ii].bd_zyp = rs.getDouble("bd_zy_fl3");
							bd_start[ii].bd_zyg = rs.getDouble("bd_zy_fl4");							
							bd_start[ii].bd_zw  = rs.getDouble("bd_zw");
							bd_start[ii].bd_fy  = rs.getDouble("bd_fy");
							bd_start[ii].bd_fyj = rs.getDouble("bd_fy_fl1");
							bd_start[ii].bd_fyf = rs.getDouble("bd_fy_fl2");
							bd_start[ii].bd_fyp = rs.getDouble("bd_fy_fl3");
							bd_start[ii].bd_fyg = rs.getDouble("bd_fy_fl4");							
							bd_start[ii].bd_fw  = rs.getDouble("bd_fw");
							break;
						}
					}
				}
				else
				{
					edataflag = true;
					for(int ii = 0; ii < clp_num;ii++){
						if(bd_end[ii].mp_id == rs.getShort("mp_id")){
							bd_end[ii].bd_zy  = rs.getDouble("bd_zy");
							bd_end[ii].bd_zyj = rs.getDouble("bd_zy_fl1");
							bd_end[ii].bd_zyf = rs.getDouble("bd_zy_fl2");
							bd_end[ii].bd_zyp = rs.getDouble("bd_zy_fl3");
							bd_end[ii].bd_zyg = rs.getDouble("bd_zy_fl4");							
							bd_end[ii].bd_zw  = rs.getDouble("bd_zw");
							bd_end[ii].bd_fy  = rs.getDouble("bd_fy");
							bd_end[ii].bd_fyj = rs.getDouble("bd_fy_fl1");
							bd_end[ii].bd_fyf = rs.getDouble("bd_fy_fl2");
							bd_end[ii].bd_fyp = rs.getDouble("bd_fy_fl3");
							bd_end[ii].bd_fyg = rs.getDouble("bd_fy_fl4");							
							bd_end[ii].bd_fw  = rs.getDouble("bd_fw");
							break;
						}
					}
				}
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			j_dao.closeRs(rs);
		}
		
		if(sdataflag && edataflag){
			rtn_json.put("info","");
			rtn_json.put("hasdataflag",true);
		}else{
			rtn_json.put("info","计算余额所需表码不足。");
			rtn_json.put("hasdataflag",false);
		}
					
		
		return rtn_json;
	}
	
	public String execute(){
		return SDDef.SUCCESS;
	}

	//按表底 计算余额。
	JSONObject GetLocBdRemain(YFFDef.YFF_DBBD[] cur_histdata, double shutbd, short[] feeproj_id, int clp_num, byte[] zfFlag,List<YffRatePara> listrate, double beilv)
	{
		//if (cuspara == NULL || zjgpay == NULL || cur_histdata == NULL) return FALSE;
		JSONObject json = new JSONObject();
		double rtn_money = 0;
		
		YffRatePara fl_rate = (YffRatePara)Rd.getYffRate(listrate, feeproj_id[0]);
		if(fl_rate == null){
			json.put("info","读取费率信息失败。费率ID=" +  feeproj_id[0] + " 的记录不存在。");
			return json;
		}
		if(fl_rate.getFeeType() == SDDef.YFF_FEETYPE_DFL)
		{
			rtn_money = (shutbd - cur_histdata[0].bd_zy - cur_histdata[1].bd_zy - cur_histdata[2].bd_zy) * fl_rate.getRatedZ() * beilv;	
			json.put("info","");
			json.put("curremian", rtn_money);
		}else{
			json.put("info","费率方案配置有误。按表底算费只支持单费率方案。");
			json.put("curremian", rtn_money);
		}
		
		return json;
	}
	

	//按金额-计算余额
	double GetLocMoneyRemain( YFFDef.YFF_DBBD[]	beg_histdata, YFFDef.YFF_DBBD[]	cur_histdata, short[] feeproj_id, int clp_num, byte[] zfFlag,
							 double pay_add1, List<YffRatePara> listrate, double beilv, String symd, String eymd, double buy_dl)
	{
		//if (cuspara == NULL || zjgpay == NULL || paystate == NULL || beg_histdata == NULL || cur_histdata == NULL) return FALSE;

		double tmp_money = 0, use_money = 0;
		double rtn_money;
		int i = 0;
		
		for( i = 0; i < clp_num; i++){
			tmp_money = 0;			//初始化
			if (i >= ComntMsg.YFF_ZJGPAY_METERNUM) break;
			YffRatePara fl_rate = (YffRatePara)Rd.getYffRate(listrate, feeproj_id[i]);
			
			if(fl_rate == null) break;
			
			if(fl_rate.getFeeType() == SDDef.YFF_FEETYPE_DFL){				
				tmp_money = Calc_CalcProcDFL(beg_histdata, cur_histdata, i, beilv, zfFlag[i], fl_rate);
			}
			else if(fl_rate.getFeeType() == SDDef.YFF_FEETYPE_FFL){
				tmp_money = Calc_CalcProcFFL(beg_histdata, cur_histdata, i, beilv, zfFlag[i], fl_rate);
			}

			use_money += tmp_money;
		}

		double payadd_money = 0;
		
		if(pay_add1 > 0){
			payadd_money = Calc_PayAdd(symd,eymd,pay_add1);
		}
		
		rtn_money = buy_dl - use_money - payadd_money;//剩余电量=购电量-已用电量-基本费。
		
		return rtn_money;
	}
	
	
	//计算两个时间段之间应该收取多少基本费:1日-2日=1天。
	double Calc_PayAdd(String symd,String eymd,double payadd){
		if( symd.equals("0") || eymd.equals("0") || eymd.equals(symd))return 0;
		
		double moneyadd = 0;

		int syear = CommBase.strtoi(symd.substring(0,4));
		int eyear = CommBase.strtoi(eymd.substring(0,4));
		int smonth = CommBase.strtoi(symd.substring(4,6));
		int emonth = CommBase.strtoi(eymd.substring(4,6));
		int sdate  = CommBase.strtoi(symd.substring(6,8));
		int edate  = CommBase.strtoi(eymd.substring(6,8));	
		
		int sdays = getDaysofMonth(symd);//开始日期月份天数
		int edays = getDaysofMonth(eymd);//结束日期月份天数
		long months = ((eyear - syear) * 12 + (emonth - smonth))-1;
		
		moneyadd = payadd * (sdays-sdate+1)/sdays + months * payadd + payadd * (edate-1)/edays;
		
		return moneyadd;
	}
	
	//返回 dateymd所在月份有多少天
	static int getDaysofMonth(String dateymd){
		
		int days   = 0;
		int syear  = CommBase.strtoi(dateymd.substring(0,4));
		int smonth = CommBase.strtoi(dateymd.substring(4,6));
		days = CommFunc.lastDayOfDate(syear, smonth);
		return days;
	}
	
	
	//单费率算费
	double Calc_CalcProcDFL(YFFDef.YFF_DBBD[] beg_dbbd, YFFDef.YFF_DBBD[] end_dbbd, int clp_idx, double beilv, byte zf_flag, YffRatePara yffrate_ptr)
	{
		double total_money =0.0;
		if (zf_flag == 0) {
			(total_money) = (end_dbbd[clp_idx].bd_zy - beg_dbbd[clp_idx].bd_zy) * yffrate_ptr.getRatedZ() * beilv;
		}
		else {
			(total_money) = (end_dbbd[clp_idx].bd_fy - beg_dbbd[clp_idx].bd_fy) * yffrate_ptr.getRatedZ() * beilv;
		}

		return total_money;
	}

	//复费率算费
	double Calc_CalcProcFFL(YFFDef.YFF_DBBD[] beg_dbbd, YFFDef.YFF_DBBD[] end_dbbd, int clp_idx, double beilv, byte zf_flag, YffRatePara yffrate_ptr)
	{
		double total_money =0.0;
		if (zf_flag == 0) {
			(total_money) = ((end_dbbd[clp_idx].bd_zyj - beg_dbbd[clp_idx].bd_zyj) *  yffrate_ptr.getRatefJ() +
							  (end_dbbd[clp_idx].bd_zyf - beg_dbbd[clp_idx].bd_zyf) *  yffrate_ptr.getRatefF() +
							  (end_dbbd[clp_idx].bd_zyp - beg_dbbd[clp_idx].bd_zyp) *  yffrate_ptr.getRatefP() +
							  (end_dbbd[clp_idx].bd_zyg - beg_dbbd[clp_idx].bd_zyg) *  yffrate_ptr.getRatefG())
							  * beilv;
		}
		else {
			(total_money) = ((end_dbbd[clp_idx].bd_fyj - beg_dbbd[clp_idx].bd_fyj) *  yffrate_ptr.getRatefJ() +
							  (end_dbbd[clp_idx].bd_fyf - beg_dbbd[clp_idx].bd_fyf) *  yffrate_ptr.getRatefF() +
							  (end_dbbd[clp_idx].bd_fyp - beg_dbbd[clp_idx].bd_fyp) *  yffrate_ptr.getRatefP() +
							  (end_dbbd[clp_idx].bd_fyg - beg_dbbd[clp_idx].bd_fyg) *  yffrate_ptr.getRatefG())
							  * beilv;
		}

		return total_money;
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
			sql = "select count(*) from yddataben.dbo.sysobjects where name = 'ZJsRecord" + isyear + "'";
			rs = j_dao.executeQuery(sql);
			if(rs.next()){
				if(rs.getInt(1)==0){
					j_dao.closeRs(rs);
					continue;
				}
			}
			j_dao.closeRs(rs);
		table += "union (select c.busi_no,c.describe cons_descr,js.* from rtupara r,conspara c,zjgpara z,zjgpay_para zp,yddataben.dbo.ZJsRecord"+isyear+" js where r.cons_id=c.id and r.app_type=1 and r.id=js.rtu_id and r.id=z.rtu_id and r.id=zp.rtu_id and z.zjg_id=zp.zjg_id and z.zjg_id=js.zjg_id " +
					"and js.op_date >=" + sdate + " and js.op_date <=" + edate;
		
		if(!yhbh.isEmpty()){
			//countSql += " and c.busi_no like '%" + yhbh + "%'";
			table += " and c.busi_no like '%" + yhbh + "%'";
		}
		
		if(!yhmc.isEmpty()){
			//countSql += " and c.describe like '%" + yhmc + "%'";
			table += " and c.describe like '%" + yhmc + "%'";
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
		/*
		String countSql = "select count(*) from rtupara r,conspara c,zjgpara z,zjgpay_para zp,yddataben.dbo.ZJsRecord"+sdate.substring(0, 4)+" js where r.cons_id=c.id and r.app_type=1 and r.id=js.rtu_id and r.id=z.rtu_id and r.id=zp.rtu_id and z.zjg_id=zp.zjg_id and z.zjg_id=js.zjg_id " +
					"and js.op_date >=" + sdate + " and js.op_date <=" + edate;
		String sql = " c.busi_no,c.describe cons_descr,js.* from rtupara r,conspara c,zjgpara z,zjgpay_para zp,yddataben.dbo.ZJsRecord"+sdate.substring(0, 4)+" js where r.cons_id=c.id and r.app_type=1 and r.id=js.rtu_id and r.id=z.rtu_id and r.id=zp.rtu_id and z.zjg_id=zp.zjg_id and z.zjg_id=js.zjg_id " +
					"and js.op_date >=" + sdate + " and js.op_date <=" + edate;
		
		if(!yhbh.isEmpty()){
			countSql += " and c.busi_no like '%" + yhbh + "%'";
			sql += " and c.busi_no like '%" + yhbh + "%'";
		}
		
		if(!yhmc.isEmpty()){
			countSql += " and c.describe like '%" + yhmc + "%'";
			sql += " and c.describe like '%" + yhmc + "%'";
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
		*/
		if (pagesize == 0) {
			pagesize = sql_page.getTotalrecords();
			sql_page.setPagesize(pagesize);
		}
		
		list = sql_page.getRecord(sql);
		
		int no = pagesize * (sql_page.getCurrentpage() - 1) + 1;
		
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			
			ret.append("{id:\"" + no + "\",data:[" + (no++) +",");
			ret.append("\"" + CommBase.CheckString(map.get("busi_no")) + "\",");
			ret.append("\"" + map.get("cons_descr") + "\",");
			ret.append("\"" + CommFunc.FormatToYM(map.get("fxdf_ym")) + "\",");
			ret.append("\"" + map.get("js_times") + "\",");
			ret.append("\"" + CommFunc.FormatToYMD(map.get("op_date"))+ CommFunc.FormatToHMS(map.get("op_time"),2) + "\",");
			ret.append("\"" + CommBase.CheckString(map.get("op_man"))+"\",");
			ret.append("\"" + Rd.getDict(Dict.DICTITEM_PAYTYPE, map.get("pay_type"))+"\",");
			ret.append("\"" + map.get("wasteno") + "\",");
			ret.append("\"" + map.get("othjs_money") + "\",");
			ret.append("\"" + map.get("pay_bmc") + "\",");
			ret.append("\"" + map.get("misjs_money") + "\",");
			ret.append("\"" + map.get("totjs_money") + "\",");
			ret.append("\"" + map.get("misjs_bmc") + "\",");
			ret.append("\"" + map.get("totjs_bmc") + "\",");
			ret.append("\"" + map.get("cur_bd") + "\",");
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
