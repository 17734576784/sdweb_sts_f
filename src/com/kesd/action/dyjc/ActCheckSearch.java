package com.kesd.action.dyjc;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.json.annotations.JSON;

import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;

public class ActCheckSearch extends ActionSupport{
	private static final long serialVersionUID = 8076407124333125775L;
	private String result;
	private String field;
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>NAME        : checkSearch	
	* <p>DESCRIPTION : 获取记录列表--对账查询--查询结果
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	*-----------------------------------------------------------*/
	public String checkSearch(){
		List<Map<String,Object>> list = null;
		StringBuffer ret_buf=new StringBuffer();
		if(null==result||result.isEmpty()){
			result=SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		JSONObject jsonObj=JSONObject.fromObject(result);
		String sdate = jsonObj.getString("sdate");
		String edate = jsonObj.getString("edate");
		//String rtuId = jsonObj.getString("rtuId");
		String orgId = jsonObj.getString("orgId");
		String operman = jsonObj.getString("operman");
		
		JDBCDao j_dao=new JDBCDao();
		StringBuffer sql_buf=new StringBuffer();
		sql_buf.append("select ").
		        append("b.rtu_id, b.mp_id, b.res_id, r.rtu_model as rtu_model, r.prot_type as prot_type, r.describe as rtu_desc, b.wasteno, b.rewasteno as rewasteno, a.cons_no, a.describe, a.address, b.op_type, b.op_date, b.op_time, b.op_man, b.pay_type, b.pay_money, b.othjs_money, b.zb_money, b.all_money, b.buy_times, b.sg186_ysdw, b.up186_flag, b.checkpay_flag  ").
		        append("from ").
		        append("ydparaben.dbo.residentpara a, yddataben.dbo.jyff").append(sdate.substring(0,4)).append( " b, ydparaben.dbo.rtupara as r, ydparaben.dbo.conspara as c   ").
		        append("where ").
		        append("a.rtu_id = b.rtu_id and a.rtu_id = r.id and r.cons_id = c.id and a.id = b.res_id  ").
		        append("and b.op_date >= ").append(sdate).append( "and b.op_date <= ").append(edate).
		        append(" and b.up186_flag=1 and b.checkpay_flag=0 ");
		

		
//		if(!("-1").equals(rtuId)){
//			sql_buf.append( "and a.id = "+rtuId + " ");
//		}
		if(!("-1").equals(orgId)){
			sql_buf.append ("and c.org_id = " +orgId + " ");
		}
		if(!(null==operman||operman.isEmpty())){
			sql_buf.append ("and b.op_man = '" +operman + "' ");
		}
		
		list=j_dao.result(sql_buf.toString());
		if(list.size()==0){
			result=SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		ret_buf.append(SDDef.JSONROWSTITLE);
		for(int index =0;index<list.size(); index++){
			Map<String,Object> map=list.get(index);
			ret_buf.append(SDDef.JSONLBRACES);
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + map.get("rtu_id") + "_" + map.get("mp_id")+"_"+index + SDDef.JSONNZDATA);//这个id应该没有什么用
			ret_buf.append(SDDef.JSONQUOT + (index+1) + SDDef.JSONCCM);//序号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("rtu_desc")) + SDDef.JSONCCM);//所属变压器
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("cons_no"))  + SDDef.JSONCCM);//用户编号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("describe"))  + SDDef.JSONCCM);//用户名称
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_YFFOPTYPE, map.get("op_type")) + SDDef.JSONCCM);//操作类型
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(map.get("op_date"))+" "+CommFunc.FormatToHMS(map.get("op_time"),2)+ SDDef.JSONCCM);//操作日期
			
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp00(map.get("pay_money")) + SDDef.JSONCCM);//缴费金额
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp00(map.get("othjs_money")) + SDDef.JSONCCM);//结算金额
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp00(map.get("zb_money")) + SDDef.JSONCCM);//追补金额
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp00(map.get("all_money"))+ SDDef.JSONCCM);//总金额
			
			ret_buf.append(SDDef.JSONQUOT + map.get("buy_times")+ SDDef.JSONCCM);//购电次数
			ret_buf.append(SDDef.JSONQUOT + map.get("wasteno")+ SDDef.JSONCCM);  //流水号
			ret_buf.append(SDDef.JSONQUOT + map.get("rewasteno")+ SDDef.JSONCCM); //被冲正流水号
			ret_buf.append(SDDef.JSONQUOT + map.get("op_man")+ SDDef.JSONCCM);//操作员
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_PAYTYPE,map.get("pay_type"))+ SDDef.JSONCCM);//缴费方式
			
			ret_buf.append(SDDef.JSONQUOT + map.get("address")+ SDDef.JSONCCM);//用户地址
			ret_buf.append(SDDef.JSONQUOT + map.get("up186_flag")+ SDDef.JSONCCM);//上传标志
			ret_buf.append(SDDef.JSONQUOT + map.get("checkpay_flag")+ SDDef.JSONCCM);//成功标志
			
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);	
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();	
		return SDDef.SUCCESS;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
}
