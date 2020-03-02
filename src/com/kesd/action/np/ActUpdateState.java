package com.kesd.action.np;

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


public class ActUpdateState extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6268085290910467823L;
	
	private String 		result;
	private String		field;		//所需查询的数据库字段

	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>NAME        : getsList	
	* <p>DESCRIPTION : 获取记录列表--特殊修正--查询结果
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	*-----------------------------------------------------------*/
	@JSON(serialize = false)
	public String getsList(){
		List<Map<String, Object>>	list		= null;
		StringBuffer 	ret_buf		= new StringBuffer();
		
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}

		JDBCDao j_dao = new JDBCDao();
		StringBuffer sqlstr = new StringBuffer();
		
		sqlstr.append("select ap.org_id, b.area_id, b.farmer_id, ap.describe area_desc, f.farmer_no, f.describe farmer_desc, b.cus_state, b.buy_times, b.op_type, b.op_date, b.op_time, b.pay_money, b.othjs_money, b.zb_money, b.all_money, b.ls_remain, b.cur_remain, b.total_gdz, b.totbuy_times, b.kh_date, b.xh_date ");
		sqlstr.append("from farmerpay_state b,farmerpara f,areapara ap ");
		sqlstr.append("where b.area_id=f.area_id and b.farmer_id=f.id and b.area_id=ap.id ");
		
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String 	org = jsonObj.getString("org");
		String 	area = jsonObj.getString("area");
		int sType = jsonObj.getInt("searchType");
		String 	sText = jsonObj.getString("searchContent");
		
		if(!org.equals("-1")){
			sqlstr.append(" and ap.org_id=").append(org);
		}
		
		if(!area.equals("-1")){
			sqlstr.append(" and ap.id=").append(area);
		}
		
		if(sText != null && !sText.isEmpty()){
			switch(sType){
			case 1:		//客户编号
				sqlstr.append(" and f.farmer_no like '%" + sText + "%'");
				break;
			case 2:		//客户名称
				sqlstr.append(" and f.describe like '%" + sText + "%'");
				break;
			}
		}
		
		String sql = sqlstr.toString();
		
		list = j_dao.result(sql);
		if(list.size()==0) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		ret_buf.append(SDDef.JSONROWSTITLE);
		for(int index =0;index<list.size();index++) {
			Map<String,Object> map=list.get(index);
			
			ret_buf.append(SDDef.JSONLBRACES); 
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + map.get("org_id") + "_" + map.get("area_id") + "_" + map.get("farmer_id") + SDDef.JSONNZDATA);
			ret_buf.append(SDDef.JSONQUOT + (index + 1) + SDDef.JSONCCM);//序号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("area_desc")) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("farmer_no")) + SDDef.JSONCCM);	//客户编号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("farmer_desc")) + SDDef.JSONCCM);	//客户名称
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_YFFCUSSTATE, map.get("cus_state")) + SDDef.JSONCCM);	//客户状态
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckNum(map.get("buy_times"))+ SDDef.JSONCCM);		//购电次数
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckNum(map.get("totbuy_times")) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_YFFOPTYPE, map.get("op_type")) + SDDef.JSONCCM);	//操作类型
			ret_buf.append(SDDef.JSONQUOT+CommFunc.FormatToYMD(map.get("op_date"))+ CommFunc.FormatToHMS(map.get("op_time"),2) + "\",");
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp000(map.get("pay_money")) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp000(map.get("othjs_money")) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp000(map.get("zb_money")) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp000(map.get("all_money")) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp000(map.get("ls_remain")) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp000(map.get("cur_remain")) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp000(map.get("total_gdz")) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT+CommFunc.FormatToYMD(map.get("kh_date")) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT+CommFunc.FormatToYMD(map.get("xh_date")) + SDDef.JSONCCM);
			
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
