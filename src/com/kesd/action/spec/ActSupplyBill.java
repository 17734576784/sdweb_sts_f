package com.kesd.action.spec;

import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.dao.SqlPage;
import com.kesd.dbpara.YffRatePara;
import com.kesd.util.I18N;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
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
			String khbh = j_obj.getString("khbh");
			String khmc = j_obj.getString("khmc");
			String czy = j_obj.getString("czy");
			String org = j_obj.getString("org");
			String rtu = j_obj.getString("rtu");
			ret.append("{rows:[");
			ret.append(mulbillSearch(sdate, edate, khbh, khmc, czy, org, rtu, yffrate));
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
		
		return SDDef.SUCCESS;
	}
	
	private String mulbillSearch(String sdate, String edate, String khbh,String khmc,String czy, String org, String rtu ,List<YffRatePara> yffrate) {
		StringBuffer ret = new StringBuffer();
		List<Map<String, Object>> list 	 = null;
		int 			page	 = Integer.parseInt(pageNo == null? "1" : pageNo);
		int 			pagesize = Integer.parseInt(pageSize == null? "10" : pageSize);
		SqlPage sql_page = new SqlPage(page, pagesize);
		String countSql="select count(*) " 
			  +"from ydparaben.dbo.conspara a, ydparaben.dbo.rtupara as r, yddataben.dbo.zyff"+sdate.substring(0,4)+" b ,zjgpay_para c, yffratepara rate " 
	          +"where r.cons_id = a.id  and r.id = b.rtu_id  "  +" and b.rtu_id=c.rtu_id and b.zjg_id=c.zjg_id and c.feeproj_id=rate.id "//新增表的关联关系
	          +"and b.op_date >= "+sdate+" and b.op_date<= "+edate+" and b.visible_flag=1  ";
		
		//增加 （单、复合、混合）fee_type、 总值（单对应 rated_z）、尖峰平谷（rated_j、rated_f、rated_p、rated_g）
		//增加两张表 yffratepara 和zjgpay关系对照表
		String sql=" c.feeproj_id,b.rtu_id, b.zjg_id, b.wasteno, a.busi_no as cons_no,a.describe, a.addr,b.op_type,b.op_date,b.op_time,b.op_man,b.pay_type,b.pay_money,b.othjs_money,b.zb_money,b.all_money, b.shutdown_val,b.alarm1,b.alarm2,b.buy_times,"
			      +" rate.fee_type,rate.rated_z,rate.ratef_j,rate.ratef_f,rate.ratef_p,rate.ratef_g ,c.cardmeter_type, c.feectrl_type, c.cacl_type, r.prot_type "//增加六个字段
		          +"from ydparaben.dbo.conspara a, ydparaben.dbo.rtupara as r, yddataben.dbo.zyff"+sdate.substring(0,4)+" b ,zjgpay_para c, yffratepara rate " 
		          +"where r.cons_id = a.id  and r.id = b.rtu_id  "  +" and b.rtu_id=c.rtu_id and b.zjg_id=c.zjg_id and c.feeproj_id=rate.id "//新增表的关联关系
		          +"and b.op_date >= "+sdate+" and b.op_date<= "+edate+" and b.visible_flag=1  ";
		
		if(!czy.isEmpty()){
			countSql += " and b.op_man like '%" + czy + "%'";
			sql += " and b.op_man like '%" + czy + "%'";
		}
		if(!khbh.isEmpty()){
			countSql += " and a.busi_no like '%" + khbh + "%'";
			sql += " and a.busi_no like '%" + khbh + "%'";
		}
		if(!khmc.isEmpty()){
			countSql += " and a.describe like '%" + khmc + "%'";
			sql += " and a.describe like '%" + khmc + "%'";
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

		list = sql_page.getRecord(sql);
		
		int no = pagesize * (sql_page.getCurrentpage() - 1) + 1;
		
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);	
			
			String paytype = "8";	//nodeIdx :{dycard: 1, dyremote: 2, dymain : 3, gycard1: 4,gycard3 : 5,gyycmoney : 6,gyycbd: 7,	gymain : 8},
			
			String pay_type 	= CommBase.CheckNum(map.get("pay_type"));
//			String prot_type 	= CommBase.CheckNum(map.get("prot_type"));
			String cacl_type 	= CommBase.CheckNum(map.get("cacl_type"));
//			String feectrl_type = CommBase.CheckNum(map.get("feectrl_type"));
			String cardmet_type = CommBase.CheckNum(map.get("cardmeter_type"));
			
			switch(Byte.parseByte(pay_type)){
			case SDDef.YFF_PREPAYTYPE_CARD   : 
				if(Byte.parseByte(cardmet_type) == SDDef.YFF_CARDMTYPE_KE001) 
					 paytype = "4"; 
				else if(Byte.parseByte(cardmet_type) == SDDef.YFF_CARDMTYPE_KE003) 
					 paytype = "5"; 
				break;				
			case SDDef.YFF_PREPAYTYPE_REMOTE : 
				if(Byte.parseByte(cacl_type) == SDDef.YFF_CACL_TYPE_MONEY)		
					paytype = "6";
				else if(Byte.parseByte(cacl_type) == SDDef.YFF_CACL_TYPE_BD)	
					paytype = "7";
				break;			 
			case SDDef.YFF_PREPAYTYPE_MASTER :
				//if(Byte.parseByte(feectrl_type) == SDDef.YFF_FEECTRL_TYPE_MASTER)
				paytype = "8"; break;
			}
			
			String id = map.get("rtu_id") + "_" + map.get("zjg_id") + "_" + map.get("op_type") + "_" + map.get("op_date") + "_" + map.get("op_time") + "_" + paytype;
			
			ret.append("{id:\"" + id + "\",data:[" + no++ +",");	
			ret.append("\""+map.get("wasteno")+"\",");//流水号
			ret.append("\""+map.get("cons_no")+"\",");//用户编号
			ret.append("\""+map.get("describe")+"\",");//用户名称
			ret.append("\""+CommBase.CheckString(map.get("addr"))+"\",");//用户地址
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
			ret.append("\""+map.get("buy_times")+"\",");

			String str_id = CommBase.CheckString(map.get("feeproj_id"));
			short feeproj_id = (short)CommBase.strtoi(str_id);//将int型转换为short型
			
			String avg_price = getDJLX(feeproj_id, yffrate);
			
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
		if(strDesc == null){
			strDesc=I18N.getText("invalid")+I18N.getText("flfa");	
		}
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
