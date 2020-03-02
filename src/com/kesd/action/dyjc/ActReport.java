package com.kesd.action.dyjc;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;
import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.opensymphony.xwork2.ActionSupport;


public class ActReport extends ActionSupport{
	
	private static final long serialVersionUID = -5150355477597234145L;
	
	private String result;
	private String hz;
	
	private int count = 1;
	private double[] total_money = {0,0,0,0,0};
	private double total_dl = 0;
	private int tt_gdcs = 0;

	private static class OpmanSum{
		public String org_desc = null;
		public int 	  org_id   = -1;
		public String oper_man = null;
		public int    salenum  = 0;
		public double pay_money = 0.0;
		public double zb_money  = 0.0;
		public double js_money  = 0.0;
		public double tot_money = 0.0;
		public double buy_dl	= 0.0;
	}

	public List<OpmanSum> opmansum = new ArrayList<OpmanSum>();	
	
	public int findOpmanSum(int org_id, String oper_man)
	{
		for (int i = 0; i < opmansum.size(); i++) 
		{
			if ((opmansum.get(i).org_id == org_id) && (opmansum.get(i).oper_man.equalsIgnoreCase(oper_man)))
				return i;	
		}
		return -1;
	}
	
	/**
	 * 购电明细表
	 */
	public String gdmxReport(){
		
		StringBuffer ret = new StringBuffer();
		
		try{
			
			JSONObject j_obj = JSONObject.fromObject(result);
			
			String sdate = j_obj.getString("sdate");
			String edate = j_obj.getString("edate");
			
			int isyear = Integer.parseInt(sdate.substring(0, 4));
			int ieyear = Integer.parseInt(edate.substring(0, 4));
			
			String isday = sdate.substring(4);
			String ieday = edate.substring(4);
			
			int year = ieyear - isyear + 1;
			
			ret.append("{rows:[");
			
			String czy = j_obj.getString("czy");
			String org = j_obj.getString("org");
			String rtu = j_obj.getString("rtu");
			
			for(int i = 0; i < year; i++){
				
				sdate = isyear + isday;
				if (i < year - 1) {
					edate = isyear + "1231";
					isday = "0101";
				}else if(i == year - 1){
					edate = ieyear + ieday;
				}
				
				ret.append(mulGdmx(sdate, edate, czy, org, rtu));
				
				isyear++;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		if(count == 1){
			result = "";
		}else{
			
			ret.deleteCharAt(ret.length() - 1);
			ret.append("]}");
			result = ret.toString();
			
			hz = "{jfje:\""+CommFunc.FormatBDDLp000(total_money[0])+"\",zbje:\""+CommFunc.FormatBDDLp000(total_money[1])+"\",jsje:\""+CommFunc.FormatBDDLp000(total_money[2])+"\",zje:\""+CommFunc.FormatBDDLp000(total_money[3])+"\",zdl:\""+CommFunc.FormatBDDLp000(total_dl)+"\"}";
		}
		return SDDef.SUCCESS;
	}
	
	private String mulGdmx(String sdate, String edate, String czy, String org, String rtu){
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;
		StringBuffer ret = new StringBuffer();
		
		try{
			
			sql = "select res.cons_no,res.describe,b.fee_type,b.describe as tp_desc," +
					" c.rtu_id,c.mp_id,c.op_man," +
					" c.op_type, c.op_date, c.op_time, c.pay_type, c.buy_times, c.pay_money, c.othjs_money, c.zb_money,c.buy_dl,mt.comm_addr,mp.pt_ratio, mp.ct_ratio " +
					" " +
					"from mppay_para a,yffratepara b,yddataben.dbo.JYff"+sdate.substring(0, 4)+" c,conspara d,rtupara r, mppara mp, meterpara mt," +
							"residentpara res where a.rtu_id=c.rtu_id and a.rtu_id=r.id and r.cons_id=d.id and a.mp_id=c.mp_id and a.rtu_id=mp.rtu_id and a.mp_id=mp.id and " +
							"a.feeproj_id=b.id and (c.op_type=1 or c.op_type=2 or c.op_type=5 or c.op_type=6 or c.op_type=9 or c.op_type=50)" +
							" and mt.mp_id=c.mp_id and mt.rtu_id=c.rtu_id and mt.rtu_id=res.rtu_id and mt.resident_id=res.id and c.op_date>="+sdate+" and c.op_date<="+edate;
			
			if(!czy.isEmpty()){
				sql += " and c.op_man like '%" + czy + "%'";
			}
			
			if(!org.equals("-1")){
				sql += " and d.org_id=" + org;
			}
			
			if(!rtu.equals("-1")){
				sql += " and r.id=" + rtu;
			}
			sql += " order by c.op_date,c.op_time";
			
			rs = j_dao.executeQuery(sql);
			double all_money = 0, tmp;
			double pt = 0, ct = 0, pay_dl=0;
			
			while(rs.next()){
				
				if(count > 10000)break;
				
				all_money = 0;
				
				ret.append("{id:\"" + count + "\",data:[" + count++ +",");
				
				ret.append("\""+CommBase.CheckString(rs.getString("cons_no"))+"\",");
				ret.append("\""+CommBase.CheckString(rs.getString("describe"))+"\",");
				ret.append("\""+CommBase.CheckString(rs.getString("comm_addr"))+"\",");

				ret.append("\""+CommBase.CheckString(rs.getString("tp_desc"))+"\",");
				
				//ret.append("\""+avg_price(rs.getInt("fee_type"),rs.getDouble("rated_z"),rs.getDouble("ratef_j"),rs.getDouble("ratef_f"),rs.getDouble("ratef_p"),rs.getDouble("ratef_g"))+"\",");
				
				
				tmp = rs.getDouble("pay_money");
				ret.append("\""+CommFunc.FormatBDDLp000(tmp)+"\",");
				all_money += tmp;
				total_money[0] += tmp;
				
				tmp = rs.getDouble("zb_money");
//				ret.append("\""+CommFunc.FormatBDDLp000(tmp)+"\",");
				all_money += tmp;
				total_money[1] += tmp;
				
				tmp = rs.getDouble("othjs_money");
//				ret.append("\""+CommFunc.FormatBDDLp000(tmp)+"\",");
				all_money += tmp;
				total_money[2] += tmp;
				
				total_money[3] += all_money;
				
				ret.append("\""+CommFunc.FormatBDDLp000(all_money)+"\",");
			
				pt = rs.getDouble("pt_ratio");
				ct = rs.getDouble("ct_ratio");
				
				pay_dl = rs.getDouble("buy_dl")*pt*ct;
				ret.append("\"" + CommFunc.FormatDouble(pay_dl>0 ? rs.getDouble("pay_money")/pay_dl : 0, 3) + "\",");	//average price
				
				ret.append("\"" + CommFunc.FormatDouble(pt, 2) + "\",");
				ret.append("\"" + CommFunc.FormatDouble(ct, 2) + "\",");

				ret.append("\"" + rs.getInt("buy_times") + "\",");		//buy_times
				
				tmp = rs.getDouble("buy_dl");
				ret.append("\""+CommFunc.FormatBDDLp000(tmp)+"\",");
				
				ret.append("\""+CommFunc.FormatBDDLp000(pay_dl)+"\",");
				
				total_dl += pay_dl;
				
				ret.append("\""+CommFunc.FormatToYMD(rs.getString("op_date")) + " " + CommFunc.FormatToHMS(rs.getString("op_time"),2) + "\",");
				ret.append("\""+CommBase.CheckString(rs.getString("op_man"))+"\",");
				
				ret.append("]},");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			System.err.println("sql语句错误：\n" + sql);
		}finally{
			j_dao.closeRs(rs);
		}
		return ret.toString();
	}
	/**
	 * 用户购电表
	 */
	public String yhgdReport(){
		
		StringBuffer ret = new StringBuffer();
		
		try{
			
			JSONObject j_obj = JSONObject.fromObject(result);
			
			String sdate = j_obj.getString("sdate");
			String edate = j_obj.getString("edate");
			
			int isyear = Integer.parseInt(sdate.substring(0, 4));
			int ieyear = Integer.parseInt(edate.substring(0, 4));
			
			String isday = sdate.substring(4);
			String ieday = edate.substring(4);
			
			int year = ieyear - isyear + 1;
			
			ret.append("{rows:[");
			
			String yhbh = j_obj.getString("yhbh");
			String org = j_obj.getString("org");
			String rtu = j_obj.getString("rtu");
			
			for(int i = 0; i < year; i++){
				
				sdate = isyear + isday;
				if (i < year - 1) {
					edate = isyear + "1231";
					isday = "0101";
				}else if(i == year - 1){
					edate = ieyear + ieday;
				}
				
				ret.append(mulYhgd(sdate, edate, yhbh, org, rtu));
				
				isyear++;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		if(count == 1){
			result = "";
		}else{
			
			ret.deleteCharAt(ret.length() - 1);
			ret.append("]}");
			result = ret.toString();
			
			hz = "{jfcs:"+tt_gdcs+",jfje:\""+CommFunc.FormatBDDLp000(total_money[0])+"\",zbje:\""+CommFunc.FormatBDDLp000(total_money[1])+"\",jsje:\""+CommFunc.FormatBDDLp000(total_money[2])+"\",zje:\""+CommFunc.FormatBDDLp000(total_money[2])+"\",zdl:\""+CommFunc.FormatBDDLp000(total_dl)+"\"}";
		}
		return SDDef.SUCCESS;
	}
	
	private String mulYhgd(String sdate, String edate, String yhbh, String org, String rtu){
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;
		StringBuffer ret = new StringBuffer();
		try{
			
			sql = "select a.*,d.comm_addr,res.cons_no,res.describe,rate.fee_type,rate.rated_z,rate.ratef_j,rate.ratef_f,rate.ratef_p,rate.ratef_g from (select cast(substring(key_id,1,8) as int) as rtu_id," +
					"cast(substring(key_id,9,6) as int) as mp_id,count(buy_times) as gdcs,sum(pay_money) as pay_money,sum(othjs_money) as js_money," +
					"sum(zb_money) as zb_money, sum(all_money) as all_money, sum(buy_dl) as buy_dl " +
					"from (select cast(rtu_id as char(8))+cast(mp_id as char(6)) as key_id,a.* from yddataben.dbo.JYff"+sdate.substring(0, 4)+" a " +
							"where (op_type=1 or op_type=2 or op_type=5 or op_type=6 or op_type=9 or op_type=50) and op_date>="+sdate+" and op_date<="+edate+") a group by key_id) a," +
									"rtupara b,conspara c,meterpara d,residentpara res,mppay_para pay,yffratepara rate where a.rtu_id=b.id and a.mp_id=d.mp_id " +
									"and b.cons_id=c.id and d.rtu_id=b.id " +
									"and d.resident_id=res.id and res.rtu_id=b.id and pay.rtu_id=b.id and pay.mp_id=a.mp_id and pay.feeproj_id=rate.id";
			
			if(!yhbh.isEmpty()){
				sql += " and res.cons_no like '%" + yhbh + "%'";
			}
			
			if(!org.equals("-1")){
				sql += " and c.org_id=" + org;
			}
			
			if(!rtu.equals("-1")){
				sql += " and b.id=" + rtu;
			}
			
			rs = j_dao.executeQuery(sql);
			
			double all_money = 0, money = 0, dl = 0, all_dl = 0;
			
			int gdcs = 0;;
			
			while(rs.next()){
				
				if(count > 10000)break;
				
				all_money = 0;
				
				ret.append("{id:\"" + count + "\",data:[" + count++ +",");
				
				ret.append("\""+CommBase.CheckString(rs.getString("cons_no"))+"\",");
				ret.append("\""+CommBase.CheckString(rs.getString("describe"))+"\",");
				ret.append("\""+CommBase.CheckString(rs.getString("comm_addr"))+"\",");
				 
				gdcs = rs.getInt("gdcs");
				ret.append("\""+gdcs+"\",");
				tt_gdcs += gdcs;
				
				money = rs.getDouble("pay_money");
				ret.append("\""+CommFunc.FormatBDDLp000(money)+"\",");
				all_money += money;
				total_money[0] += money;
				
//				money = rs.getDouble("zb_money");
//				ret.append("\""+CommFunc.FormatBDDLp000(money)+"\",");
//				all_money += money;
//				total_money[1] += money;
//				
//				money = rs.getDouble("js_money");
//				ret.append("\""+CommFunc.FormatBDDLp000(money)+"\",");
//				all_money += money;
//				total_money[2] += money;
				money = rs.getDouble("all_money");
				ret.append("\""+CommFunc.FormatBDDLp000(money)+"\",");
				total_money[1] += money;
												
				dl = rs.getDouble("buy_dl");
				ret.append("\""+CommFunc.FormatBDDLp000(dl)+"\",");
				all_dl += dl;
				total_money[2] += dl;
				
				ret.append("\""+avg_price(rs.getInt("fee_type"),rs.getDouble("rated_z"),rs.getDouble("ratef_j"),rs.getDouble("ratef_f"),rs.getDouble("ratef_p"),rs.getDouble("ratef_g"))+"\",");
				
				ret.append("]},");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			System.err.println("sql语句错误：\n" + sql);
			return "";
		}finally{
			j_dao.closeRs(rs);
		}
		return ret.toString();
	}
	/**
	 * 购电汇总表
	 */
	public String gdhzReport(){
		
		StringBuffer ret = new StringBuffer();
		
		try{
			
			JSONObject j_obj = JSONObject.fromObject(result);
			
			String sdate = j_obj.getString("sdate");
			String edate = j_obj.getString("edate");
			
			int isyear = Integer.parseInt(sdate.substring(0, 4));
			int ieyear = Integer.parseInt(edate.substring(0, 4));
			
			String isday = sdate.substring(4);
			String ieday = edate.substring(4);
			
			int year = ieyear - isyear + 1;
			
			ret.append("{rows:[");
			
			String org = j_obj.getString("org");
			String rtu = j_obj.getString("rtu");
			
			for(int i = 0; i < year; i++){
				
				sdate = isyear + isday;
				if (i < year - 1) {
					edate = isyear + "1231";
					isday = "0101";
				}else if(i == year - 1){
					edate = ieyear + ieday;
				}
				
				ret.append(mulGdhz(sdate, edate, org, rtu));
				
				isyear++;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		if(count == 1){
			result = "";
		}else{
			
			ret.deleteCharAt(ret.length() - 1);
			ret.append("]}");
			result = ret.toString();
			
			hz = "{jfcs:"+tt_gdcs+",jfje:\""+CommFunc.FormatBDDLp000(total_money[0])+"\",zje:\""+CommFunc.FormatBDDLp000(total_money[1])+"\",zdl:\""+CommFunc.FormatBDDLp000(total_money[2])+"\"}";
		}
		return SDDef.SUCCESS;
	}
	
	public String mulGdhz(String sdate, String edate, String org, String rtu){
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;
		StringBuffer ret = new StringBuffer();
		
		try{
			sql = "select b.describe as org_desc,a.*  from (select cast(substring(key_id,1,8) as int) as op_date,cast(substring(key_id,9,6) as int) as org_id," +
					"count(buy_times) as gdcs,sum(pay_money) as pay_money,sum(othjs_money) as js_money,sum(zb_money) as zb_money,sum(all_money) as all_money,sum(buy_dl) as buy_dl from (" +
					"select cast(a.op_date as char(8))+cast(c.org_id as char(6)) as key_id,a.* from yddataben.dbo.JYff"+sdate.substring(0, 4)+" a,rtupara b,conspara c,meterpara mp " +
							"where (a.op_type=1 or a.op_type=2 or a.op_type=5 or a.op_type=6 or a.op_type=9 or a.op_type=50) and a.rtu_id=b.id and b.cons_id=c.id and a.mp_id=mp.mp_id and mp.rtu_id=b.id and a.op_date>="+sdate+" and a.op_date<="+edate;
			
			if(!rtu.equals("-1")){
				sql += " and b.id=" + rtu;
			}
			
			sql += ") a group by key_id) a,orgpara b where a.org_id=b.id ";
			
			if(!org.equals("-1")){
				sql += " and b.id=" + org;
			}
			sql += " order by b.id,a.op_date";
			
			rs = j_dao.executeQuery(sql);
			double all_money = 0, money = 0;
			
			int gdcs = 0;;
			
			while(rs.next()){
				
				if(count > 10000)break;
				
				all_money = 0;
				
				ret.append("{id:\"" + count + "\",data:[" + count++ +",");
				
				ret.append("\""+CommFunc.FormatToYMD((rs.getString("op_date")))+"\",");
				ret.append("\""+CommBase.CheckString(rs.getString("org_desc"))+"\",");
				
				gdcs = rs.getInt("gdcs");
				ret.append("\""+gdcs+"\",");
				tt_gdcs += gdcs;
				
				money = rs.getDouble("pay_money");
				ret.append("\""+CommFunc.FormatBDDLp000(money)+"\",");
				all_money += money;
				total_money[0] += money;
				
//				money = rs.getDouble("zb_money");
//				ret.append("\""+CommFunc.FormatBDDLp000(money)+"\",");
//				all_money += money;
//				total_money[1] += money;
//				
//				money = rs.getDouble("js_money");
//				ret.append("\""+CommFunc.FormatBDDLp000(money)+"\",");
//				all_money += money;
//				total_money[2] += money;

				money = rs.getDouble("all_money");
				ret.append("\""+CommFunc.FormatBDDLp000(money)+"\",");
				total_money[1] += money;
				
				money = rs.getDouble("buy_dl");
				ret.append("\""+CommFunc.FormatBDDLp000(money)+"\",");
				all_money += money;
				total_money[2] += money;
//				
//				ret.append("\""+CommFunc.FormatBDDLp000(all_money)+"\",");
				
				ret.append("]},");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			System.err.println("sql语句错误：\n" + sql);
			return "";
		}finally{
			j_dao.closeRs(rs);
		}
		return ret.toString();
	}
	
	private String avg_price(int fee_type,double z, double j, double f, double p, double g){
		
		int YFF_FEETYPE_DFL = 3;
		int YFF_FEETYPE_FFL = 4;
		
		if(fee_type == YFF_FEETYPE_DFL){
			return CommFunc.FormatBDDLp000(z);
		}else if(fee_type == YFF_FEETYPE_FFL){
			return CommFunc.FormatBDDLp000((j + f + p + g) / 4);
		}else{
			return "0.000";
		}
		
	}
	
	/**
	 * 操作员购电汇总表
	 */
	public String opmanSalsumReport(){
		
		StringBuffer ret = new StringBuffer();
		
		try{
			
			JSONObject j_obj = JSONObject.fromObject(result);
			
			String sdate = j_obj.getString("sdate");
			String edate = j_obj.getString("edate");
			
			int isyear = Integer.parseInt(sdate.substring(0, 4));
			int ieyear = Integer.parseInt(edate.substring(0, 4));
			
			String isday = sdate.substring(4);
			String ieday = edate.substring(4);
			
			int year = ieyear - isyear + 1;
			
			ret.append("{rows:[");
			
			String org = j_obj.getString("org");
			
			for(int i = 0; i < year; i++){
				
				sdate = isyear + isday;
				if (i < year - 1) {
					edate = isyear + "1231";
					isday = "0101";
				}else if(i == year - 1){
					edate = ieyear + ieday;
				}
				
				OpmanSalsum(sdate, edate, org);
				
				isyear++;
			}
			for(int i = 0; i < opmansum.size(); i++){
				ret.append("{id:\"" + opmansum.get(i).org_id + "_" + count + "\",data:[" + count++ +",");	

				ret.append("\""+ opmansum.get(i).org_desc+"\",");
				ret.append("\""+ opmansum.get(i).oper_man+"\",");
				ret.append("\""+ opmansum.get(i).salenum+"\",");
				ret.append("\""+ opmansum.get(i).pay_money+"\",");
//				ret.append("\""+ opmansum.get(i).zb_money+"\",");
//				ret.append("\""+ opmansum.get(i).js_money+"\",");
				ret.append("\""+ opmansum.get(i).tot_money+"\",");
				ret.append("\""+ opmansum.get(i).buy_dl+"\",");
				ret.append("]},");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(count == 1){
			result = "";
		}else{
			
			ret.deleteCharAt(ret.length() - 1);
			ret.append("]}");
			result = ret.toString();
			
			hz = "{jfcs:"+tt_gdcs+",jfje:\""+CommFunc.FormatBDDLp000(total_money[0])+"\",zje:\""+CommFunc.FormatBDDLp000(total_money[3])+"\",zdl:\""+CommFunc.FormatBDDLp000(total_money[4])+"\"}";
		}
		return SDDef.SUCCESS;
	}
	
	public int OpmanSalsum(String sdate, String edate, String org){
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;
		
		try{
			sql = "select o.id as ogr_id, o.describe as org_desc, a.* from ( " +
			"select o.id as orgid, z.op_man, count(buy_times) as paysum, sum(pay_money) as pay_money,sum(othjs_money) as js_money,sum(zb_money) as zb_money, sum(all_money) as all_money, sum(buy_dl) as buy_dl " + 
			"from yddataben.dbo.JYff" + sdate.substring(0,4) + " as z, rtupara as r,conspara as c , orgpara as o  " + 
			"where z.rtu_id = r.id and r.cons_id = c.id  and c.org_id  = o.id " + 
	     	"and(z.op_type=1 or z.op_type=2 or z.op_type=5 or z.op_type=6 or z.op_type=9 or z.op_type=50) " +
	     	"and z.op_date>=" + sdate + " and z.op_date<=" + edate;
			if(!org.equals("-1")){
	    		sql += " and o.id =" + org;
	    	}

	     	sql +=" group by o.id, z.op_man" +
	     	" ) as a, orgpara as o where o.id = a.orgid  order by o.id";
	     	
			rs = j_dao.executeQuery(sql);

			while(rs.next()){
				
				if(count > 10000) break;
				
				OpmanSum tmpopersum = new OpmanSum();

				tmpopersum.org_id = rs.getInt("ogr_id");
				tmpopersum.org_desc  = CommBase.CheckString(rs.getString("org_desc"));
				tmpopersum.oper_man  = CommBase.CheckString(rs.getString("op_man"));
				tmpopersum.salenum   = rs.getInt("paysum");			tt_gdcs += tmpopersum.salenum ;
				tmpopersum.pay_money = rs.getDouble("pay_money");  	total_money[0] += tmpopersum.pay_money;
				tmpopersum.zb_money  = rs.getDouble("zb_money");	total_money[1] += tmpopersum.zb_money;
				tmpopersum.js_money  = rs.getDouble("js_money");    total_money[2] += tmpopersum.js_money;
				tmpopersum.tot_money = rs.getDouble("all_money");   total_money[3] += tmpopersum.tot_money;
				tmpopersum.buy_dl	 = rs.getDouble("buy_dl");   	total_money[4] += tmpopersum.buy_dl;

				opmansum.add(tmpopersum);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			System.err.println("sql语句错误：\n" + sql);
			return -1;
		}finally{
			j_dao.closeRs(rs);
		}
		return 1;
	}
	
	//20131115添加低压发行电费报表
	public String fxdfReport(){
		StringBuffer ret = new StringBuffer();
		
		try{
			JSONObject j_obj = JSONObject.fromObject(result);
			
			String sdate = j_obj.getString("sdate");
			String edate = j_obj.getString("edate");
			
			int isyear = Integer.parseInt(sdate.substring(0, 4));
			int ieyear = Integer.parseInt(edate.substring(0, 4));
			
			String isday = sdate.substring(4);
			String ieday = edate.substring(4);
			
			int year = ieyear - isyear + 1;
			
			ret.append("{rows:[");
			
			String org = j_obj.getString("org");
			String rtu = j_obj.getString("rtu");
			
			for(int i = 0; i < year; i++){
				
				sdate = isyear + isday;
				if (i < year - 1) {
					edate = isyear + "1231";
					isday = "0101";
				}else if(i == year - 1){
					edate = ieyear + ieday;
				}
				
				ret.append(mulFxdf(sdate, edate, org, rtu));
				
				isyear++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(count == 1){
			result = "";
		}else{
			
			ret.deleteCharAt(ret.length() - 1);
			ret.append("]}");
			result = ret.toString();
		}
		return SDDef.SUCCESS;
	}
	
	public String mulFxdf(String sdate, String edate, String org, String rtu){
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;
		StringBuffer ret = new StringBuffer();
		String tabName = "yddataben.dbo.MpFxMoney" + sdate.substring(0,4);
		try{
			
		sql = " select fx.*, rtu.describe as rtu_desc, res.describe as res_desc from " + 
				tabName + " as fx, rtupara as rtu,  residentpara res, mppara as mp, conspara c" +
				" where mp.rtu_id = rtu.id and res.rtu_id = rtu.id and rtu.id = fx.rtu_id and fx.mp_id = mp.id and fx.rtu_id = mp.rtu_id and res.cons_no = fx.cons_no and c.id = rtu.cons_id " + 
				" and fx.fxdf_ym >= " + sdate + " and fx.fxdf_ym <=" + edate;
		if(!rtu.equals("-1")){
			sql += " and fx.rtu_id=" + rtu;
		}
		if(!org.equals("-1")){
			sql += " and c.org_id=" + org;
		}
		
		sql += " order by fx.rtu_id, fx.mp_id, fx.fxdf_ym";
		rs = j_dao.executeQuery(sql);
		
		while(rs.next()){
			if(count > 10000)break;
			ret.append("{id:\"" + count + "\",data:[" + (count++) + ",");
			ret.append("\"" + CommBase.CheckString(rs.getString("rtu_desc")) + "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("cons_no")) + "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("res_desc")) + "\",");
			
			ret.append("\"" + CommFunc.FormatToYM(rs.getString("fxdf_ym")) + "\",");
			ret.append("\"" + CommFunc.FormatToMDHMin(rs.getString("fxdf_mdhmi")) + "\",");
			ret.append("\"" + CommFunc.FormatToYM(rs.getString("fxdf_lastym"))	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("iall_money"))	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("last_remain"))	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("now_remain"))	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("jt_total_zbdl"))	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("jt_total_dl"))	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("df_money"))	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("shutdown_val"))	+ "\",");
			
			ret.append("\"" + CommFunc.FormatToYMD(rs.getString("jsbd_ymd"))		+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("jsbd_zyz")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("jsbd_zyj")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("jsbd_zyf")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("jsbd_zyp")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("jsbd_zyg")) 	+ "\",");
			
			ret.append("\"" + CommBase.CheckString(rs.getString("jsbd1_zyz")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("jsbd1_zyj")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("jsbd1_zyf")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("jsbd1_zyp")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("jsbd1_zyg")) 	+ "\",");
			
			ret.append("\"" + CommBase.CheckString(rs.getString("jsbd2_zyz")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("jsbd2_zyj")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("jsbd2_zyf")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("jsbd2_zyp")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("jsbd2_zyg")) 	+ "\",");
			
			ret.append("\"" + CommFunc.FormatToYMD(rs.getString("calc_bdymd")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("calc_zyz"))  	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("calc_zyj")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("calc_zyf")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("calc_zyp")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("calc_zyg")) 	+ "\",");
			
			ret.append("\"" + CommBase.CheckString(rs.getString("calc1_zyz")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("calc1_zyj")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("calc1_zyf")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("calc1_zyp")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("calc1_zyg")) 	+ "\",");
			
			ret.append("\"" + CommBase.CheckString(rs.getString("calc2_zyz")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("calc2_zyj")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("calc2_zyf")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("calc2_zyp")) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("calc2_zyg")) 	+ "\",");
			
			
			ret.append("\"" + CommBase.CheckString(rs.getString("update_count")) + "\",");
			ret.append("\"" + ((CommBase.CheckString(rs.getString("all_monthf")).equals("1"))?"整月" : "") + "\",");
//			ret.append("\"" + CommBase.CheckString(rs.getString("bc_flag")) 	+ "\",");//结算补差标记
//			ret.append("\"" + CommFunc.FormatToYMD(rs.getString("bc_date")) 	+ "\","); 结算的三项目前不使用。注释。使用时去掉这里和jsp的注释即可。
//			ret.append("\"" + CommFunc.FormatToHMS(rs.getString("bc_time"),0) 	+ "\",");
			ret.append("\"" + CommBase.CheckString(rs.getString("op_man")) 		+ "\"]},");
		}
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("sql语句错误：\n" + sql);
			return "";
		}finally{
			j_dao.closeRs(rs);
		}
		return ret.toString();
	}
	
	
	class JRtuColtMoney {
		public int org_id;
		public int rtu_id;
		public String rtu_desc; // 名称
		public String org_desc; 	//供电所名称

		public int	  buytimes;		//
		public double paymoneys;	//
		public double jsmoneys;		//
		public double zbmoneys;		//
		public double totmoneys;	//
		public double buydl;
	}
	
	//Map<Integer, JRtuColtMoney> rtucol_map = new HashMap<Integer, JRtuColtMoney>();
	List<JRtuColtMoney> rtucol_map = new ArrayList<JRtuColtMoney>();

	public String rtucoltmoney()
	{
		StringBuffer ret = new StringBuffer();
		
		try{
			
			JSONObject j_obj = JSONObject.fromObject(result);
			
			String sdate = j_obj.getString("sdate");
			String edate = j_obj.getString("edate");
			
			int isyear = Integer.parseInt(sdate.substring(0, 4));
			int ieyear = Integer.parseInt(edate.substring(0, 4));
			
			String isday = sdate.substring(4);
			String ieday = edate.substring(4);
			
			int year = ieyear - isyear + 1;
			
		//	ret.append("{rows:[");
			
			String org = j_obj.getString("org");
			
			rtucol_map.clear();
			
			for(int i = 0; i < year; i++){
				
				sdate = isyear + isday;
				if (i < year - 1) {
					edate = isyear + "1231";
					isday = "0101";
				}else if(i == year - 1){
					edate = ieyear + ieday;
				}
				
				rtucoltitem(sdate, edate, org, i == 0);
				
				isyear++;
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		JSONArray j_array1 = new JSONArray();
		JSONArray j_array2 = new JSONArray();
		int index = 1;

		JRtuColtMoney temp = null;
		for (int i = 0; i < rtucol_map.size(); i++) { //
			temp = rtucol_map.get(i);
			

			json1.put("id", index);

			j_array1.add(index++); // 序号
			j_array1.add(CommFunc.CheckString(temp.org_desc)); // 营业点
			j_array1.add(CommFunc.CheckString(temp.rtu_desc)); 

			j_array1.add(CommFunc.CheckString(temp.buytimes)); //
			tt_gdcs +=temp.buytimes;
			
			j_array1.add(CommFunc.CheckString(temp.paymoneys)); 
			total_money[0] += temp.paymoneys;
			//
//			j_array1.add(CommFunc.CheckString(temp.zbmoneys)); //
			total_money[1] += temp.zbmoneys;
			
//			j_array1.add(CommFunc.CheckString(temp.jsmoneys)); //
			total_money[2] += temp.jsmoneys;
			
			j_array1.add(CommFunc.CheckString(temp.totmoneys)); //
			total_money[3] += temp.totmoneys;
			
			j_array1.add(CommFunc.CheckString(temp.buydl));
			total_money[4] += temp.buydl;
			
			
			json1.put("data", j_array1);
			j_array2.add(json1);
			j_array1.clear();
			json1.clear();
		}
		
		json2.put("rows", j_array2);
		result = json2.toString();
	
		hz = "{jfcs:"+tt_gdcs+",jfje:\""+CommFunc.FormatBDDLp000(total_money[0])+"\",zje:\""+CommFunc.FormatBDDLp000(total_money[3])+"\",zdl:\""+CommFunc.FormatBDDLp000(total_money[4])+"\"}";
	
		return SDDef.SUCCESS;
	}
	
		public int rtucoltitem(String sdate, String edate, String org, boolean first){
			
			JDBCDao j_dao = new JDBCDao();
			ResultSet rs = null;
			String sql = null;
			
			try{
				sql = "select o.describe as org_desc, r.describe as rtu_desc, a.* from ( " +
				"select rtu_id, count(*) as paysum, sum(pay_money) as pay_money,sum(othjs_money) as js_money,sum(zb_money) as zb_money, sum(all_money) as all_money, sum(buy_dl) as buy_dl " + 
				"from yddataben.dbo.JYff" + sdate.substring(0,4) +" as z, rtupara as r,conspara as c " + 
				"where z.rtu_id = r.id and r.cons_id = c.id  and (z.op_type=1 or z.op_type=2 or z.op_type=5 or z.op_type=6 or z.op_type=9 or z.op_type=50) " +
		     	"and z.op_date>=" + sdate + " and z.op_date<=" + edate;

				if(!org.equals("-1")){
		    		sql += " and c.org_id =" + org;
		    	}

		     	sql +=" group by z.rtu_id" +
		     	" ) as a, rtupara as r,conspara as c, orgpara as o where a.rtu_id = r.id and r.cons_id = c.id and c.org_id = o.id order by o.id";
		     	
				rs = j_dao.executeQuery(sql);

				
				while(rs.next()){
					
					if(count > 10000) break;
					
					OpmanSum tmpopersum = new OpmanSum();
				
					int rtu_id			 = rs.getInt("rtu_id");
				
					int i = 0;
					
					if (!first) {
						for (i = 0; i < rtucol_map.size(); i++) {
							if (rtucol_map.get(i).rtu_id == rtu_id) {
								rtucol_map.get(i).buytimes += rs.getInt("paysum");
								rtucol_map.get(i).paymoneys+= rs.getDouble("pay_money");
								rtucol_map.get(i).jsmoneys += rs.getDouble("js_money");
								rtucol_map.get(i).zbmoneys += rs.getDouble("zb_money");
								rtucol_map.get(i).totmoneys+= rs.getDouble("all_money");
								rtucol_map.get(i).buydl+= rs.getDouble("buy_dl");
								break;
							}
						}
					}
					
					if ((i >= rtucol_map.size()) || first) {
						JRtuColtMoney tempitem = new JRtuColtMoney();
						
						tempitem.rtu_id = rtu_id;
						tempitem.org_desc  = rs.getString("org_desc");
						tempitem.rtu_desc  = rs.getString("rtu_desc");
						tempitem.buytimes  = rs.getInt("paysum");
						tempitem.paymoneys = rs.getDouble("pay_money");
						tempitem.jsmoneys  = rs.getDouble("js_money");
						tempitem.zbmoneys  = rs.getDouble("zb_money");
						tempitem.totmoneys = rs.getDouble("all_money");
						tempitem.buydl 	   = rs.getDouble("buy_dl");
						rtucol_map.add(tempitem);
					}
				}
				
			}catch (Exception e) {
				e.printStackTrace();
				System.err.println("sql语句错误：\n" + sql);
				return -1;
			}finally{
				j_dao.closeRs(rs);
			}
			return 1;
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
	public void setHz(String hz) {
		this.hz = hz;
	}
	public String getHz() {
		return hz;
	}
}
