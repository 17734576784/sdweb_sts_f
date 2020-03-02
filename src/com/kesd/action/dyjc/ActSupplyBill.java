package com.kesd.action.dyjc;

import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import net.sf.json.JSONObject;

import com.libweb.common.CommBase;

import com.kesd.action.ActPrint;
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
			String rtu   = j_obj.getString("rtu");
			ret.append("{rows:[");
			ret.append(mulbillSearch(sdate, edate, khbm, khmc, czy, org, rtu,yffrate));
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
	private String mulbillSearch(String sdate, String edate, String khbm,String khmc,String czy, String org, String rtu ,List<YffRatePara> yffrate) {
		StringBuffer ret = new StringBuffer();
		List<Map<String, Object>> list 	 = null;
		int 			page	 = Integer.parseInt(pageNo == null? "1" : pageNo);
		int 			pagesize = Integer.parseInt(pageSize == null? "10" : pageSize);
		SqlPage sql_page = new SqlPage(page, pagesize);
		
		String countSql = "select count(*) "
		 +" from ydparaben.dbo.residentpara a, yddataben.dbo.jyff"+sdate.substring(0,4)+"  b , ydparaben.dbo.rtupara as r , ydparaben.dbo.conspara  as c ,mppay_para z, yffratepara rate,YDParaBen.dbo.mppay_state as mps " 
         +" where a.rtu_id = b.rtu_id and mps.rtu_id = b.rtu_id and mps.mp_id = b.mp_id and a.rtu_id = r.id and r.cons_id = c.id and a.id = b.res_id "+" and b.rtu_id=z.rtu_id and b.mp_id=z.mp_id and z.feeproj_id=rate.id "//新增表的关联关系 
         +" and b.op_date >= "+sdate+" and b.op_date<= " +edate+ " and b.visible_flag=1 ";
		
		String sql ="  mps.jy_money jyMoney,sts.token1,sts.token2 ,b.rtu_id, b.mp_id, b.res_id, r.describe as rtu_desc, b.wasteno,b.rewasteno, a.cons_no,a.describe, a.address,b.op_type,b.op_date,b.op_time,b.op_man,b.pay_type,b.pay_money,b.othjs_money,b.zb_money,b.all_money, b.buy_dl,b.alarm1, b.alarm2, b.shutdown_val,b.buy_times, "
			       +" rate.id feeproj_id, z.pay_type as pay_type_para, z.cacl_type,o.DESCRIBE AS orgDesc,o.addr AS orgAddr,o.telno AS orgTel,mp.comm_addr AS comAddr "
			       +" from ydparaben.dbo.residentpara a, yddataben.dbo.jyff"+sdate.substring(0,4)+"  b , ydparaben.dbo.rtupara as r , ydparaben.dbo.conspara  as c ,mppay_para z,meter_stspara sts, yffratepara rate,ydparaben.dbo.orgpara o,ydparaben.dbo.meterpara mp,YDParaBen.dbo.mppay_state as mps " 
                   +" where mp.rtu_id = r.id and mps.rtu_id = b.rtu_id and mps.mp_id = b.mp_id and mp.mp_id = z.mp_id and o.id = c.org_id and a.rtu_id = b.rtu_id and a.rtu_id = r.id and r.cons_id = c.id and a.id = b.res_id "+" and b.rtu_id=z.rtu_id and b.mp_id=z.mp_id and z.feeproj_id=rate.id  and sts.rtu_id = z.rtu_id and sts.mp_id = z.mp_id "//新增表的关联关系 
                   +" and b.op_date >= "+sdate+" and b.op_date<= " +edate+ " and b.visible_flag=1 "; // and b.op_type in (1,2)
		
		if(!czy.isEmpty()){
			countSql+=" and b.op_man like '%"+czy+"%'";
			sql+=" and b.op_man like '%"+czy+"%'";
		}
		if(!khbm.isEmpty()){
			countSql+=" and a.cons_no like '%"+khbm+"%'";
			sql+=" and a.cons_no like '%"+khbm+"%'";
		}
		if(!khmc.isEmpty()){
			countSql+=" and a.describe like '%"+khmc+"%'";
			sql+=" and a.describe like '%"+khmc+"%'";
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
		//System.out.println(countSql);
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
			byte  paytype = 0;////nodeIdx :{dycard: 1, dyremote: 2, dymain : 3, gycard1: 4,gycard3 : 5,gyycmoney : 6,gyycbd: 7,	gymain : 8},
//			 switch(Byte.parseByte(CommBase.CheckNum(map.get("pay_type")))){
//				 case SDDef.YFF_PREPAYTYPE_CARD   : paytype = "1"; break;
//				 case SDDef.YFF_PREPAYTYPE_REMOTE : paytype = "2"; break;
//				 case SDDef.YFF_PREPAYTYPE_MASTER : paytype = "3"; break;
//			 }
			
//			 switch(Byte.parseByte(CommBase.CheckNum(map.get("pay_type")))){
//				 case SDDef.YFF_PREPAYTYPE_CARD   : 
//					 {
//						 if (Byte.parseByte(CommBase.CheckNum(map.get("cacl_type"))) == SDDef.YFF_CACL_TYPE_DL) {
//							 paytype = ActPrint.nodeIdx.dycarddl;
//						 }
//						 else {
//							 paytype = ActPrint.nodeIdx.dycard;
//						 }
//					 }
//					 break;
//				 case SDDef.YFF_PREPAYTYPE_REMOTE : 
//					 paytype = ActPrint.nodeIdx.dyremote;
//					 break;
//				 case SDDef.YFF_PREPAYTYPE_MASTER : 
//					 paytype = ActPrint.nodeIdx.dymain;
//					 break;
//			 }
			
			if(Byte.parseByte(CommBase.CheckNum(map.get("op_type")))==2 || Byte.parseByte(CommBase.CheckNum(map.get("op_type")))==50){
				paytype = ActPrint.nodeIdx.dycard;
			}else if(Byte.parseByte(CommBase.CheckNum(map.get("op_type")))==1){
				paytype = ActPrint.nodeIdx.dyaddcus;
			} 
				
						 
			String id = CommBase.CheckString(map.get("rtu_id")) + "|" + CommBase.CheckString(map.get("mp_id")) + "|" + CommBase.CheckString(map.get("op_type")) + "|" + CommBase.CheckString(map.get("op_date")) + "|" + CommBase.CheckString(map.get("op_time")) + "|" + CommBase.CheckString(paytype) + "|" + CommBase.CheckString(map.get("token1")) + "|" + CommBase.CheckString(map.get("token2")) + "|" + CommBase.CheckString(map.get("orgDesc")) + "|" + CommBase.CheckString(map.get("orgAddr")) + "|" + CommBase.CheckString(map.get("orgTel")) + "|" + CommBase.CheckString(map.get("rewasteno")) + "|" + CommBase.CheckString(map.get("comAddr"));
			ret.append("{id:\"" + id + "\",data:[" + no++ +",");	
			ret.append("\""+map.get("wasteno")+"\",");
			ret.append("\""+map.get("rtu_desc")+"\",");
			ret.append("\""+map.get("cons_no")+"\",");//用户编号
			ret.append("\""+map.get("describe")+"\",");//用户名称
			ret.append("\""+map.get("address")+"\",");//用户地址
			ret.append("\""+Rd.getDict(Dict.DICTITEM_YFFOPTYPE, map.get("op_type"))+"\",");//操作类型
			ret.append("\""+CommFunc.FormatToYMD(map.get("op_date"))+ " " + CommFunc.FormatToHMS(map.get("op_time"),2) + "\",");//操作日期
			ret.append("\""+CommBase.CheckString(map.get("op_man"))+"\",");//操作员
			ret.append("\""+Rd.getDict(Dict.DICTITEM_PAYTYPE, map.get("pay_type"))+"\",");//缴费方式
			ret.append("\""+CommFunc.FormatBDDLp00(map.get("pay_money"))+"\",");//缴费金额
//			ret.append("\""+CommFunc.FormatBDDLp00(map.get("othjs_money"))+"\",");
//			ret.append("\""+CommFunc.FormatBDDLp00(map.get("zb_money"))+"\",");
			ret.append("\""+CommFunc.FormatBDDLp00(map.get("all_money"))+"\",");
			ret.append("\""+CommFunc.FormatBDDLp00(map.get("jyMoney"))+"\",");
			ret.append("\""+CommFunc.FormatBDDLp00(map.get("buy_dl"))+"\",");
//			ret.append("\""+map.get("alarm1")+"\",");
//			ret.append("\""+map.get("alarm2")+"\",");
//			ret.append("\""+map.get("shutdown_val")+"\",");
			ret.append("\""+map.get("buy_times")+"\",");
			//确定是否是单电价 还是复合电价
			short feeproj_id=(short)CommBase.strtoi(CommBase.CheckString(map.get("feeproj_id")));
			//经过map类型  tinyint已经不是short类型了 ,先把Obj转换为String再转化为Int再转化为short
			String avg_price=getDJLX(feeproj_id, yffrate);
			
			ret.append("\""+avg_price+"\"");
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
