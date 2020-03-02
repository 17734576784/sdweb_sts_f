package com.kesd.action.np;

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
			String cardno = j_obj.getString("cardno");
			String yhmc = j_obj.getString("yhmc");
			String czy = j_obj.getString("czy");
			String org = j_obj.getString("org");
			String oper_type = j_obj.getString("oper_type");
			
			ret.append("{rows:[");
			
			ret.append(mulZhSearch(sdate, edate, yhbh, cardno, yhmc, czy, org, oper_type));
			
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
	
	private String mulZhSearch(String sdate, String edate, String yhbh, String cardno, String yhmc, String czy, String org, String oper_type) throws Exception
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
			
			sql = "select count(*) from yddataben.dbo.sysobjects where name='nyff" + isyear + "'";
			rs = j_dao.executeQuery(sql);
			if(rs.next()){
				if(rs.getInt(1) == 0){
					j_dao.closeRs(rs);
					continue;
				}
			}
			j_dao.closeRs(rs);
			
			table += "union (select ap.describe ap_desc,fp.describe fp_desc,fp.farmer_no,a.card_no,ap.area_code,a.op_man,a.op_type,a.op_date,a.op_time,a.pay_type,a.wasteno,a.rewasteno,a.pay_money,a.othjs_money,a.zb_money,a.all_money,a.ls_remain,a.cur_remain,a.total_gdz,a.buy_times from yddataben.dbo.nyff" + isyear + " a,areapara ap,farmerpara fp where a.area_id=ap.id and a.farmer_id=fp.id and ap.id=fp.area_id ";
			
			if(!yhbh.isEmpty()){
				table += " and fp.farmer_no like '%" + yhbh + "%'";
			}
			if(!cardno.isEmpty()){
				table += " and a.card_no like '%" + cardno + "%'";
			}
			if(!yhmc.isEmpty()){
				table += " and fp.describe like '%" + yhmc + "%'";
			}
			if(!czy.isEmpty()){
				table += " and a.op_man like '%" + czy + "%'";
			}
			if(!org.equals("-1")){
				table += " and ap.org_id=" + org;
			}
			
			if(!oper_type.equals("-1")){
				table += " and a.op_type=" + oper_type;
			}
			table += " and a.op_date >= " + sdate + " and a.op_date<=" + edate +")";
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
			
			ret.append("\""+map.get("ap_desc")+"\",");
			ret.append("\""+map.get("fp_desc")+"\",");
			ret.append("\""+map.get("farmer_no")+"\",");
			ret.append("\""+map.get("card_no")+"\",");
			ret.append("\""+map.get("area_code")+"\",");
			ret.append("\""+CommBase.CheckString(map.get("op_man"))+"\",");
			ret.append("\""+Rd.getDict(Dict.DICTITEM_YFFOPTYPE, map.get("op_type"))+"\",");
			ret.append("\""+CommFunc.FormatToYMD(map.get("op_date"))+ CommFunc.FormatToHMS(map.get("op_time"),1) + "\",");
			ret.append("\""+Rd.getDict(Dict.DICTITEM_PAYTYPE, map.get("pay_type"))+"\",");
			ret.append("\""+CommBase.CheckString(map.get("wasteno"))+"\",");
			ret.append("\""+CommBase.CheckString(map.get("rewasteno"))+"\",");
			ret.append("\""+map.get("pay_money")+"\",");
			ret.append("\""+map.get("othjs_money")+"\",");
			ret.append("\""+map.get("zb_money")+"\",");
			ret.append("\""+map.get("all_money")+"\",");
			ret.append("\""+map.get("ls_remain")+"\",");
			ret.append("\""+map.get("cur_remain")+"\",");
			ret.append("\""+map.get("total_gdz")+"\",");
			ret.append("\""+map.get("buy_times")+"\"");
			
			ret.append("]},");
		}
		
		return ret.toString();
		
	}
	
	public String yhxxSearch(){
		
		JSONObject j_obj = JSONObject.fromObject(result);
		
		StringBuffer ret = new StringBuffer();
		
		int 			page	 = Integer.parseInt(pageNo == null? "1" : pageNo);
		int 			pagesize = Integer.parseInt(pageSize == null? "10" : pageSize);
		
		SqlPage sql_page = new SqlPage(page, pagesize);
		
		ret.append("{rows:[");
		String countSql = "select count(*) from farmerpara f,areapara a where f.area_id=a.id";
		String sql = " f.area_id,f.id,a.describe as ap_desc, f.describe as fp_desc,f.farmer_no,f.card_no,f.mobile,f.phone,f.identity_no,f.village,f.address,f.post "
				+ "from  farmerpara f,areapara a where f.area_id=a.id ";
		
		String org = j_obj.getString("org");
		if(!org.equals("-1")){
			countSql += " and a.org_id=" + org;
			sql += " and a.org_id=" + org;
		}
		
		String area = j_obj.getString("area");
		if(!area.equals("-1")){
			countSql += " and a.id=" + area;
			sql += " and a.id=" + area;
		}
		
		String yhbh = j_obj.getString("yhbh");
		if(!yhbh.isEmpty()){
			countSql += " and f.farmer_no like '%" + yhbh + "%'";
			sql += " and f.farmer_no like '%" + yhbh + "%'";
		}
		
		String yhmc = j_obj.getString("yhmc");
		if(!yhmc.isEmpty()){
			countSql += " and f.describe like '%" + yhmc + "%'";
			sql += " and f.describe like '%" + yhmc + "%'";
		}
		
		String lxdh = j_obj.getString("lxdh");
		if(!lxdh.isEmpty()){
			countSql += " and f.mobile like '%" + lxdh + "%'";
			sql += " and f.mobile like '%" + lxdh + "%'";
		}
		
		String cardno = j_obj.getString("cardno");
		if(!cardno.isEmpty()){
			countSql += " and f.card_no like '%" + cardno + "%'";
			sql += " and f.card_no like '%" + cardno + "%'";
		}
		sql += " order by f.area_id desc,f.id desc) a order by a.area_id,a.id";
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
			ret.append("\""+CommFunc.CheckString(map.get("ap_desc"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("fp_desc"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("farmer_no"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("card_no"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("mobile"))+"\",");
			
			ret.append("\""+CommFunc.CheckString(map.get("phone"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("identity_no"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("village"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("address"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("post"))+"\",");
			
			ret.append("]},");
		}
		
		ret.deleteCharAt(ret.length() - 1);
		ret.append("]}");
		
		result = ret.toString();
		
		result = "{total:"+count+",page:["+result+"]}";
		
		return SDDef.SUCCESS;
	}
	
	public String yhztSearch(){
		
		JSONObject j_obj = JSONObject.fromObject(result);
		
		StringBuffer ret = new StringBuffer();
		
		int 			page	 = Integer.parseInt(pageNo == null? "1" : pageNo);
		int 			pagesize = Integer.parseInt(pageSize == null? "10" : pageSize);
		
		SqlPage sql_page = new SqlPage(page, pagesize);
		
		ret.append("{rows:[");
		String countSql = "select count(*) from farmerpay_state s,farmerpara f,areapara a where s.area_id=a.id and s.farmer_id=f.id and f.area_id=a.id ";
		String sql = " f.area_id,f.id,a.describe as ap_desc, f.describe as fp_desc,f.farmer_no,f.card_no,f.mobile,s.cus_state,s.op_type,s.op_date,s.op_time,s.pay_money,s.othjs_money,s.zb_money,s.all_money,s.ls_remain,s.cur_remain,s.total_gdz,s.buy_times,s.kh_date,s.xh_date "
				+ "from farmerpay_state s,farmerpara f,areapara a where s.area_id=a.id and s.farmer_id=f.id and f.area_id=a.id ";
		
		String org = j_obj.getString("org");
		if(!org.equals("-1")){
			countSql += " and a.org_id=" + org;
			sql += " and a.org_id=" + org;
		}
		
		String area = j_obj.getString("area");
		if(!area.equals("-1")){
			countSql += " and a.id=" + area;
			sql += " and a.id=" + area;
		}
		
		String yhbh = j_obj.getString("yhbh");
		if(!yhbh.isEmpty()){
			countSql += " and f.farmer_no like '%" + yhbh + "%'";
			sql += " and f.farmer_no like '%" + yhbh + "%'";
		}
		
		String yhmc = j_obj.getString("yhmc");
		if(!yhmc.isEmpty()){
			countSql += " and f.describe like '%" + yhmc + "%'";
			sql += " and f.describe like '%" + yhmc + "%'";
		}
		
		String lxdh = j_obj.getString("lxdh");
		if(!lxdh.isEmpty()){
			countSql += " and f.mobile like '%" + lxdh + "%'";
			sql += " and f.mobile like '%" + lxdh + "%'";
		}
		
		String cardno = j_obj.getString("cardno");
		if(!cardno.isEmpty()){
			countSql += " and f.card_no like '%" + cardno + "%'";
			sql += " and f.card_no like '%" + cardno + "%'";
		}
		sql += " order by f.area_id desc,f.id desc) a order by a.area_id,a.id";
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
			ret.append("\""+CommFunc.CheckString(map.get("ap_desc"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("fp_desc"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("farmer_no"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("card_no"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("mobile"))+"\",");
			ret.append("\""+Rd.getDict(Dict.DICTITEM_YFFCUSSTATE, map.get("cus_state"))+"\",");//用户状态			
			ret.append("\""+Rd.getDict(Dict.DICTITEM_YFFOPTYPE, map.get("op_type"))+"\",");
			ret.append("\""+CommFunc.FormatToYMD(map.get("op_date"))+ CommFunc.FormatToHMS(map.get("op_time"),1) + "\",");
			ret.append("\""+CommFunc.CheckString(map.get("pay_money"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("othjs_money"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("zb_money"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("all_money"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("ls_remain"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("cur_remain"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("total_gdz"))+"\",");
			ret.append("\""+CommFunc.CheckString(map.get("buy_times"))+"\",");
			ret.append("\""+CommFunc.FormatToYMD(map.get("kh_date")) + "\",");
			ret.append("\""+CommFunc.FormatToYMD(map.get("xh_date")) + "\",");
			
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
			
			ret.append("\""+map.get(Integer.parseInt(ret_map.get("op_type")+""))+"\",");
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
