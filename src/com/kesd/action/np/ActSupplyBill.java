package com.kesd.action.np;

import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import net.sf.json.JSONObject;

import com.libweb.common.CommBase;

import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.dao.SqlPage;
import com.kesd.dbpara.YffRatePara;
import com.kesd.util.Rd;
import com.opensymphony.xwork2.ActionSupport;

public class ActSupplyBill extends ActionSupport{
	private static final long serialVersionUID = -489709042006138964L;
	private String result;
	private String filed;
	private String pageNo;			//页号
	private String pageSize;  		//每页记录数
	private int count = 0;
	
	public String billSearch(){
		StringBuffer ret = new StringBuffer();
		try {
			List<YffRatePara> yffrate = Rd.getYffRate();
			
			JSONObject j_obj = JSONObject.fromObject(result);
			String sdate = j_obj.getString("sdate"); 
			String edate = j_obj.getString("edate");
			String khbm  = j_obj.getString("khbh");
			String khmc  = j_obj.getString("khmc");
			String czy   = j_obj.getString("czy");
			String org   = j_obj.getString("org");
			String area   = j_obj.getString("area");
			ret.append("{rows:[");
			ret.append(mulbillSearch(sdate, edate, khbm, khmc, czy, org, area,yffrate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == 0) {
			result = "";
		} else {
			ret.deleteCharAt(ret.length() - 1);
			ret.append("]}");
			result = ret.toString();
			result = "{total:" + count + ",page:[" + result + "]}";
		}
		//System.out.println(result);
		return SDDef.SUCCESS;
	}
	private String mulbillSearch(String sdate, String edate, String khbm,String khmc,String czy,String org , String area, List<YffRatePara> yffrate) {
		StringBuffer ret = new StringBuffer();
		List<Map<String, Object>> list 	 = null;
		int 			page	 = Integer.parseInt(pageNo == null? "1" : pageNo);
		int 			pagesize = Integer.parseInt(pageSize == null? "10" : pageSize);
		SqlPage sql_page = new SqlPage(page, pagesize);
		
		String countSql = "select count(*) "+
					"from yddataben.dbo.nyff" + sdate.substring(0,4) + " n,areapara a,farmerpara f " +
					" where n.area_id=f.area_id and n.farmer_id=f.id and a.id=f.area_id " +
					" and n.op_date >= " + sdate + " and n.op_date<= " + edate + " and n.visible_flag=1 ";
		String sql = " n.area_id,n.farmer_id,n.wasteno,a.describe area_desc,f.farmer_no,f.describe farmer_desc,f.address,f.card_no," +
					" n.op_type,n.op_date,n.op_time,n.op_man,n.pay_type,n.pay_money,n.othjs_money,n.zb_money,n.all_money,n.alarm1,n.alarm2,n.total_gdz,n.buy_times " +
					" from yddataben.dbo.nyff" + sdate.substring(0,4) + " n,areapara a,farmerpara f " +
					" where n.area_id=f.area_id and n.farmer_id=f.id and a.id=f.area_id " +
					" and n.op_date >= " + sdate + " and n.op_date<= " + edate + " and n.visible_flag=1 ";
	
		if(!czy.isEmpty()){
			countSql+=" and n.op_man like '%"+czy+"%'";
			sql+=" and n.op_man like '%"+czy+"%'";
		}
		if(!khbm.isEmpty()){
			countSql+=" and f.farmer_no like '%"+khbm+"%'";
			sql+=" and f.farmer_no like '%"+khbm+"%'";
		}
		if(!khmc.isEmpty()){
			countSql+=" and f.describe like '%"+khmc+"%'";
			sql+=" and f.describe like '%"+khmc+"%'";
		}
		if(!org.equals("-1")){
			countSql += " and a.org_id=" + org;
			sql += " and a.org_id=" + org;
		}
		if(!area.equals("-1")){
			countSql += " and n.area_id=" + area;
			sql += " and n.area_id=" + area;
		}	
		
		sql += " order by n.op_date desc,n.op_time desc) a order by a.op_date,a.op_time";
		System.out.println(countSql);
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
		//System.out.println("sql 语句：  "+sql);
		list = sql_page.getRecord(sql);
		
		int no = pagesize * (sql_page.getCurrentpage() - 1) + 1;
		
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);	
			String  paytype = "9";////nodeIdx :{dycard: 1, dyremote: 2, dymain : 3, gycard1: 4,gycard3 : 5,gyycmoney : 6,gyycbd: 7,	gymain : 8,	npcard : 9},

			String id = map.get("area_id") + "_" + map.get("farmer_id") + "_" + map.get("op_type") + "_" + map.get("op_date") + "_" + map.get("op_time") + "_" + paytype;
			ret.append("{id:\"" + id + "\",data:[" + no++ +",");	
			ret.append("\""+map.get("wasteno")+"\",");//流水号
			ret.append("\""+map.get("area_desc")+"\",");//所属片区
			ret.append("\""+map.get("farmer_no")+"\",");//用户编号
			ret.append("\""+map.get("farmer_desc")+"\",");//用户名称
			ret.append("\""+map.get("address")+"\",");//用户地址
			ret.append("\""+map.get("card_no")+"\",");//卡号
			ret.append("\""+Rd.getDict(Dict.DICTITEM_YFFOPTYPE, map.get("op_type"))+"\",");//操作类型
			ret.append("\""+CommFunc.FormatToYMD(map.get("op_date"))+ CommFunc.FormatToHMS(map.get("op_time"),2) + "\",");//操作日期
			ret.append("\""+CommBase.CheckString(map.get("op_man"))+"\",");//操作员
			ret.append("\""+Rd.getDict(Dict.DICTITEM_PAYTYPE, map.get("pay_type"))+"\",");//缴费方式
			ret.append("\""+CommFunc.FormatBDDLp00(map.get("pay_money"))+"\",");//缴费金额
			ret.append("\""+CommFunc.FormatBDDLp00(map.get("othjs_money"))+"\",");
			ret.append("\""+CommFunc.FormatBDDLp00(map.get("zb_money"))+"\",");
			ret.append("\""+CommFunc.FormatBDDLp00(map.get("all_money"))+"\",");
			ret.append("\""+map.get("alarm1")+"\",");
			ret.append("\""+map.get("alarm2")+"\",");
			ret.append("\""+map.get("total_gdz")+"\",");
			ret.append("\""+map.get("buy_times")+"\"");
			ret.append("]},");
		}
		return ret.toString();
	}
	public String getDJLX(short id, List<YffRatePara> list) {
		if(list == null || list.size() == 0) return "";
		String strDesc = null;
		YffRatePara yffrate = Rd.getYffRate(list, id);
		strDesc = Rd.getYffRateDesc(yffrate);
		
		return strDesc;
	}
	
	
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getFiled() {
		return filed;
	}
	public void setFiled(String filed) {
		this.filed = filed;
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
    }
}
