package com.kesd.action.spec;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
	private double[] total_money = {0,0,0,0};
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
			
			hz = "{jfje:\""+CommFunc.FormatBDDLp000(total_money[0])+"\",zbje:\""+CommFunc.FormatBDDLp000(total_money[1])+"\",jsje:\""+CommFunc.FormatBDDLp000(total_money[2])+"\",zje:\""+CommFunc.FormatBDDLp000(total_money[3])+"\"}";
		}
		return SDDef.SUCCESS;
	}
	
	private String mulGdmx(String sdate, String edate, String czy, String org, String rtu){
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;
		StringBuffer ret = new StringBuffer();
		
		try{
			sql = "select r.describe,a.pay_money,a.zb_money,a.othjs_money,a.all_money,a.op_date,a.op_time,a.op_man,rate.fee_type,rate.rated_z,rate.ratef_j,rate.ratef_f,rate.ratef_p,rate.ratef_g " +
					" from yddataben.dbo.ZYff"+sdate.substring(0, 4)+" a,zjgpay_para b,conspara c,rtupara r,yffratepara rate where (a.op_type=1 or a.op_type=2 or a.op_type=5 or a.op_type=6 or a.op_type=9 or a.op_type=50) " +
					" and a.rtu_id=b.rtu_id and a.zjg_id=b.zjg_id and a.rtu_id=r.id and r.cons_id=c.id and b.feeproj_id=rate.id and a.op_date>="+sdate+" and a.op_date<="+edate;
			
			if(!czy.isEmpty()){
				sql += " and a.op_man like '%" + czy + "%'";
			}
			
			if(!org.equals("-1")){
				sql += " and c.org_id=" + org;
			}
			
			if(!rtu.equals("-1")){
				sql += " and r.id=" + rtu;
			}
			sql += " order by a.op_date,a.op_time";
			
			rs = j_dao.executeQuery(sql);
			double all_money = 0, tmp;
			
			
			while(rs.next()){
				
				if(count > 10000)break;
				
				all_money = 0;
				
				ret.append("{id:\"" + count + "\",data:[" + count++ +",");
				
				ret.append("\""+CommBase.CheckString(rs.getString("describe"))+"\",");
				
				tmp = rs.getDouble("pay_money");
				ret.append("\""+CommFunc.FormatBDDLp000(tmp)+"\",");
				all_money += tmp;
				total_money[0] += tmp;
				
				tmp = rs.getDouble("zb_money");
				ret.append("\""+CommFunc.FormatBDDLp000(tmp)+"\",");
				all_money += tmp;
				total_money[1] += tmp;
				
				tmp = rs.getDouble("othjs_money");
				ret.append("\""+CommFunc.FormatBDDLp000(tmp)+"\",");
				all_money += tmp;
				total_money[2] += tmp;
				
				total_money[3] += all_money;
				
				ret.append("\""+CommFunc.FormatBDDLp000(all_money)+"\",");
				
				ret.append("\""+avg_price(rs.getInt("fee_type"),rs.getDouble("rated_z"),rs.getDouble("ratef_j"),rs.getDouble("ratef_f"),rs.getDouble("ratef_p"),rs.getDouble("ratef_g"))+"\",");
				ret.append("\""+CommFunc.FormatToYMD(rs.getString("op_date"))+ CommFunc.FormatToHMS(rs.getString("op_time"),2) + "\",");
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
				
				ret.append(mulYhgd(sdate, edate, org, rtu));
				
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
			
			hz = "{jfcs:"+tt_gdcs+",jfje:\""+CommFunc.FormatBDDLp000(total_money[0])+"\",zbje:\""+CommFunc.FormatBDDLp000(total_money[1])+"\",jsje:\""+CommFunc.FormatBDDLp000(total_money[2])+"\",zje:\""+CommFunc.FormatBDDLp000(total_money[3])+"\"}";
		}
		return SDDef.SUCCESS;
	}
	
	private String mulYhgd(String sdate, String edate, String org, String rtu){
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;
		StringBuffer ret = new StringBuffer();
		try{
			
			sql = "select a.*,b.describe,rate.fee_type,rate.rated_z,rate.ratef_j,rate.ratef_f,rate.ratef_p,rate.ratef_g from(" +
					"select cast(substring(key_id,1,8) as int) as rtu_id,cast(substring(key_id,9,6) as int) as zjg_id,count(buy_times) as gdcs,sum(pay_money) as pay_money,sum(othjs_money) as js_money,sum(zb_money) as zb_money " +
					"from (select cast(rtu_id as char(8))+cast(zjg_id as char(6)) as key_id,a.* from yddataben.dbo.ZYff"+sdate.substring(0, 4)+" a where (op_type=1 or op_type=2 or op_type=5 or op_type=6 or op_type=9 or op_type=50) " +
					"and op_date>="+sdate+" and op_date<="+edate+") a group by key_id) a,rtupara b,conspara c,zjgpay_para d,yffratepara rate where a.rtu_id=b.id and a.zjg_id=d.zjg_id and a.rtu_id=d.rtu_id and b.cons_id=c.id and d.feeproj_id=rate.id";
			
			
			if(!org.equals("-1")){
				sql += " and c.org_id=" + org;
			}
			
			if(!rtu.equals("-1")){
				sql += " and b.id=" + rtu;
			}
			
			rs = j_dao.executeQuery(sql);
			
			double all_money = 0, money = 0;
			
			int gdcs = 0;;
			
			while(rs.next()){
				
				if(count > 10000)break;
				
				all_money = 0;
				
				ret.append("{id:\"" + count + "\",data:[" + count++ +",");
				
				ret.append("\""+CommBase.CheckString(rs.getString("describe"))+"\",");
				
				gdcs = rs.getInt("gdcs");
				ret.append("\""+gdcs+"\",");
				tt_gdcs += gdcs;
				
				money = rs.getDouble("pay_money");
				ret.append("\""+CommFunc.FormatBDDLp000(money)+"\",");
				all_money += money;
				total_money[0] += money;
				
				money = rs.getDouble("zb_money");
				ret.append("\""+CommFunc.FormatBDDLp000(money)+"\",");
				all_money += money;
				total_money[1] += money;
				
				money = rs.getDouble("js_money");
				ret.append("\""+CommFunc.FormatBDDLp000(money)+"\",");
				all_money += money;
				total_money[2] += money;
				
				total_money[3] += all_money;
				
				ret.append("\""+CommFunc.FormatBDDLp000(all_money)+"\",");
				
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
			
			hz = "{jfcs:"+tt_gdcs+",jfje:\""+CommFunc.FormatBDDLp000(total_money[0])+"\",zbje:\""+CommFunc.FormatBDDLp000(total_money[1])+"\",jsje:\""+CommFunc.FormatBDDLp000(total_money[2])+"\",zje:\""+CommFunc.FormatBDDLp000(total_money[3])+"\"}";
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
					"count(buy_times) as gdcs,sum(pay_money) as pay_money,sum(othjs_money) as js_money,sum(zb_money) as zb_money from (" +
					"select cast(a.op_date as char(8))+cast(c.org_id as char(6)) as key_id,a.* from yddataben.dbo.ZYff"+sdate.substring(0, 4)+" a,rtupara b,conspara c " +
							"where (a.op_type=1 or a.op_type=2 or a.op_type=5 or a.op_type=6 or a.op_type=9 or a.op_type=50) and a.rtu_id=b.id and b.cons_id=c.id and a.op_date>="+sdate+" and a.op_date<="+edate;
			
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
				
				money = rs.getDouble("zb_money");
				ret.append("\""+CommFunc.FormatBDDLp000(money)+"\",");
				all_money += money;
				total_money[1] += money;
				
				money = rs.getDouble("js_money");
				ret.append("\""+CommFunc.FormatBDDLp000(money)+"\",");
				all_money += money;
				total_money[2] += money;
				
				total_money[3] += all_money;
				
				ret.append("\""+CommFunc.FormatBDDLp000(all_money)+"\",");
				
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
		
		int YFF_FEETYPE_DFL = 0;
		int YFF_FEETYPE_FFL = 1;
		
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
					ret.append("\""+ opmansum.get(i).zb_money+"\",");
					ret.append("\""+ opmansum.get(i).js_money+"\",");
					ret.append("\""+ opmansum.get(i).tot_money+"\",");
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
			
			hz = "{jfcs:"+tt_gdcs+",jfje:\""+CommFunc.FormatBDDLp000(total_money[0])+"\",zbje:\""+CommFunc.FormatBDDLp000(total_money[1])+"\",jsje:\""+CommFunc.FormatBDDLp000(total_money[2])+"\",zje:\""+CommFunc.FormatBDDLp000(total_money[3])+"\"}";
		}
		return SDDef.SUCCESS;
	}
	
	public int OpmanSalsum(String sdate, String edate, String org){
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;
		try{
			sql = "select o.id as ogr_id, o.describe as org_desc, a.* from ( " +
			"select o.id as orgid, z.op_man, count(buy_times) as paysum, sum(pay_money) as pay_money,sum(othjs_money) as js_money,sum(zb_money) as zb_money, sum(all_money) as all_money " + 
			"from yddataben.dbo.ZYff2013  as z, rtupara as r,conspara as c , orgpara as o  " + 
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

				if(count > 10000)break;

				OpmanSum tmpopersum = new OpmanSum();

				tmpopersum.org_id = rs.getInt("ogr_id");
				tmpopersum.org_desc  = CommBase.CheckString(rs.getString("org_desc"));
				tmpopersum.oper_man  = CommBase.CheckString(rs.getString("op_man"));
				tmpopersum.salenum   = rs.getInt("paysum");			tt_gdcs += tmpopersum.salenum ;
				tmpopersum.pay_money = rs.getDouble("pay_money");  	total_money[0] += tmpopersum.pay_money;
				tmpopersum.zb_money  = rs.getDouble("zb_money");	total_money[1] += tmpopersum.zb_money;
				tmpopersum.js_money  = rs.getDouble("js_money");    total_money[2] += tmpopersum.js_money;
				tmpopersum.tot_money = rs.getDouble("all_money");   total_money[3] += tmpopersum.tot_money;

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
	/**
	 * 发行电费
	 */
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
			
			//hz = "{jfcs:"+tt_gdcs+",jfje:\""+CommFunc.FormatBDDLp000(total_money[0])+"\",zbje:\""+CommFunc.FormatBDDLp000(total_money[1])+"\",jsje:\""+CommFunc.FormatBDDLp000(total_money[2])+"\",zje:\""+CommFunc.FormatBDDLp000(total_money[3])+"\"}";
		}
		return SDDef.SUCCESS;
	}
	
	public String mulFxdf(String sdate, String edate, String org, String rtu){
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;
		StringBuffer ret = new StringBuffer();
		String tabNm = "yddataben.dbo.ZjgFxMoney" + sdate.substring(0, 4);
		try{

			sql = " select a.*,r.describe as rtu_desc,z.describe as zjg_desc from " + tabNm + " a,rtupara r,zjgpara z, conspara c" +
				  " where a.fxdf_ym>=" + sdate + " and a.fxdf_ym<=" + edate +
				  " and a.rtu_id = r.id  and a.rtu_id = z.rtu_id and a.zjg_id = z.zjg_id and a.bus_no=c.busi_no and r.cons_id=c.id ";	

			if(!rtu.equals("-1")){
				sql += " and a.rtu_id=" + rtu;
			}
			if(!org.equals("-1")){
				sql += " and c.org_id=" + org; //and a.bus_no in(select busi_no from conspara where org_id=" + org + ")"; 
			}
			sql += " order by a.rtu_id,a.fxdf_ym";
			 System.out.println("***************************************************");
			 System.out.println("sql :"+sql);
			 System.out.println("***************************************************");
			rs = j_dao.executeQuery(sql);
			
			while(rs.next()){
				
				if(count > 10000)break;
				
				ret.append("{id:\"" + count + "\",data:[" + (count++) + ",");
				ret.append("\"" + "[" + CommBase.CheckString(rs.getString("rtu_id")) + "]" + 
								  CommBase.CheckString(rs.getString("rtu_desc")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("bus_no")) 		+ "\",");
				ret.append("\"" + "[" +  CommBase.CheckString(rs.getString("zjg_id")) + "]" + 
								  CommBase.CheckString(rs.getString("zjg_desc")) 	+ "\",");
				ret.append("\"" + CommFunc.FormatToYM(rs.getString("fxdf_ym")) 		+ "\",");
				ret.append("\"" + CommFunc.FormatToMDHMin(rs.getString("fxdf_mdhmi")) 	+ "\",");
				ret.append("\"" + CommFunc.FormatToYM(rs.getString("fxdf_lastym"))	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("iall_money"))	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("last_remain"))	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("now_remain"))	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("df_money"))	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("real_powrate"))+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("ele_money"))	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("jbf_money"))	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("powrate_money"))+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("other_money"))	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("total_yzbdl"))	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("total_wzbdl"))	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("total_ydl"))	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("total_wdl"))	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("zbele_money"))	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("zbjbf_money"))	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("zbother_money"))+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("shutdown_val"))+ "\",");
				ret.append("\"" + CommFunc.FormatToYMD(rs.getString("jsbd_ymd"))		+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("jsbd_zyz")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("jsbd_zyj")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("jsbd_zyf")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("jsbd_zyp")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("jsbd_zyg")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("jsbd_zwz")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("jsbd1_zyz")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("jsbd1_zyj")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("jsbd1_zyf"))	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("jsbd1_zyp"))	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("jsbd1_zyg")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("jsbd1_zwz")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("jsbd2_zyz")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("jsbd2_zyj")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("jsbd2_zyf")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("jsbd2_zyp")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("jsbd2_zyg")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("jsbd2_zwz")) 	+ "\",");
				ret.append("\"" + CommFunc.FormatToYMD(rs.getString("calc_bdymd")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("calc_zyz"))  	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("calc_zyj")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("calc_zyf")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("calc_zyp")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("calc_zyg")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("calc_zwz")) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("calc1_zyz"))   + "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("calc1_zyj"))   + "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("calc1_zyf"))   + "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("calc1_zyp"))   + "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("calc1_zyg"))   + "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("calc1_zwz"))   + "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("calc2_zyz"))   + "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("calc2_zyj"))   + "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("calc2_zyf"))   + "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("calc2_zyp"))   + "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("calc2_zyg"))   + "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("calc2_zwz"))   + "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("update_count")) + "\",");
				ret.append("\"" + ((CommBase.CheckString(rs.getString("all_monthf")).equals("1"))?"整月" : "") + "\",");
//				ret.append("\"" + CommBase.CheckString(rs.getString("bc_flag")) 	+ "\",");//结算补差标记
//				ret.append("\"" + CommFunc.FormatToYMD(rs.getString("bc_date")) 	+ "\","); 结算的三项目前不使用。注释。使用时去掉这里和jsp的注释即可。
//				ret.append("\"" + CommFunc.FormatToHMS(rs.getString("bc_time"),0) 	+ "\",");
				ret.append("\"" + CommBase.CheckString(rs.getString("op_man")) 		+ "\"]},");
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
