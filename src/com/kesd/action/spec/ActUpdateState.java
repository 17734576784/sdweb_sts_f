package com.kesd.action.spec;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.struts2.json.annotations.JSON;
import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;
import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.util.Rd;
import com.opensymphony.xwork2.ActionSupport;

public class ActUpdateState extends ActionSupport{
	private static final long serialVersionUID = 6268085290910467823L;
	
	private String 		result;
	private String		field;		//所需查询的数据库字段	
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>NAME        : getsList	
	* <p>DESCRIPTION : 获取记录列表--特殊修正(高压)--查询结果
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	*-----------------------------------------------------------*/
	@JSON(serialize = false)
	public String getsList(){
		List<Map<String, Object>>list = null;
		StringBuffer ret_buf = new StringBuffer();
		
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		}
		JDBCDao j_dao = new JDBCDao();
		
		StringBuffer sql=new StringBuffer();
		
		sql.append("select z.clp_num, z.describe as zjg_describe,d.rtu_id, r.describe as rtu_desc, d.zjg_id,c.org_id, c.busi_no, cacl_type,feectrl_type,pay_type,yffctrl_type,feeproj_id,feeproj1_id,feeproj2_id,yffalarm_id,prot_st,prot_ed,ngloprot_flag,pay_add1,pay_add2,pay_add3,tz_val,plus_time,use_gfh,hfh_time,hfh_shutdown,cs_stand,fee_chgf,fee_chgid,fee_chgid1,fee_chgid2,fee_chgdate,fee_chgtime,jbf_chgf,jbf_chgval,jbf_chgdate,jbf_chgtime,d.*,e.*,f.describe yffalarm_desc ").
		    append("from ydparaben.dbo.conspara as c, ydparaben.dbo.zjgpay_para as b, ydparaben.dbo.rtupara as r , ydparaben.dbo.zjgpay_state as d,ydparaben.dbo.zjgpay_almstate as e,zjgpara z,yffalarmpara f ").
		    append("where r.app_type = 1 and c.id = r.cons_id and r.id = b.rtu_id and r.id = d.rtu_id and r.id = e.rtu_id and z.rtu_id = d.rtu_id and z.zjg_id = d.zjg_id and f.id=yffalarm_id and d.cus_state is not null and d.cus_state<>0 ");
		    
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String 	orgId = jsonObj.getString("orgId");
		//String 	rtuId = jsonObj.getString("rtuId");//暂时不用
		Integer sType = jsonObj.getInt("searchType");
		String 	sText = jsonObj.getString("searchContent");
		int  updType = jsonObj.getInt("updType");
		if(!orgId.equals("-1")){
			sql.append(" and c.org_id="+ orgId);
		}

		if(sText != null && !sText.equals("") && !sText.isEmpty()){//查询条件
			switch(sType){
			case 1://营业户号
				sql.append(" and c.busi_no like '%" + sText + "%'");
				break;
			case 2://用户名称
				sql.append(" and c.describe like '%" + sText + "%'");
				break;
			case 3://联系电话
				sql.append(" and c.tel_no1 like '%"  + sText + "%'");
				break;		
			}
		}
		switch(updType){
			case 0 ://购电次数
			case 4 :
			case 5 :
			case 6 :
			case 7 :
			case 8 :
			case 9 :
				break;
			case 1 ://首次短信告警确认失败
				sql.append(" and e.qr_al1_1_state > 0xc0");
				break;
			case 2 ://一级声音告警
				sql.append(" and e.qr_al1_2_state > 0xc0");
				break;
			case 3 ://二次短信告警
				sql.append(" and e.qr_al2_1_state > 0xc0");
				break;
		}
		//String sqltmp="select a.*,b.describe yffalarm_desc from (" + sql.toString() + ") a left join yffalarmpara b on a.yffalarm_id=b.id";
		list = j_dao.result(sql.toString());
		if(list.size()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		}
		
		ret_buf.append(SDDef.JSONROWSTITLE);
		for(int index =0;index<list.size();index++){
			Map<String,Object> map=list.get(index);
			ret_buf.append(SDDef.JSONLBRACES);
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + map.get("rtu_id") + "_" + map.get("zjg_id") + "_" + map.get("clp_num") + SDDef.JSONNZDATA);
			ret_buf.append(SDDef.JSONQUOT + (index+1) + SDDef.JSONCCM);//序号
			ret_buf.append(SDDef.JSONQUOT + "[" + map.get("rtu_id") + "]" + CommBase.CheckString(map.get("rtu_desc")) + SDDef.JSONCCM);//终端名称
			ret_buf.append(SDDef.JSONQUOT + "[" + map.get("zjg_id") + "]" + CommBase.CheckString(map.get("zjg_describe")) + SDDef.JSONCCM);//总加组编号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("busi_no")) + SDDef.JSONCCM);//营业户号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckNum(map.get("buy_times")) + SDDef.JSONCCM);//购电次数
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("yffalarm_desc")) + SDDef.JSONCCM);//报警方案
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FomatProtTime(map.get("prot_st"), map.get("prot_ed"))+ SDDef.JSONCCM);//保电时间
			ret_buf.append(SDDef.JSONQUOT + getState(map.get("cs_al1_state"),0) + " " +getTime(map.get("al1_mdhmi"))+ SDDef.JSONCCM);//1级报警状态/时间
			
			ret_buf.append(SDDef.JSONQUOT + getState(map.get("qr_al1_1_state"),1) + "-" +getTime(map.get("qr_al1_1_mdhmi")) + SDDef.JSONCCM);//1级短信报警状态/时间
			ret_buf.append(SDDef.JSONQUOT + getState(map.get("qr_al1_2_state"),2) + "-" +getTime(map.get("qr_al1_2_mdhmi")) + SDDef.JSONCCM);//1级声音报警状态/时间
 
			ret_buf.append(SDDef.JSONQUOT + getState(map.get("cs_al2_state"),3) + " " +getTime(map.get("al2_mdhmi"))+ SDDef.JSONCCM);//2级报警状态/时间
			ret_buf.append(SDDef.JSONQUOT + getState(map.get("qr_al2_1_state"),4)+"-" +getTime(map.get("qr_al2_1_mdhmi")) + SDDef.JSONCCM);//2级短信报警状态/时间
			ret_buf.append(SDDef.JSONQUOT + getState(map.get("cs_fhz_state"),6) + " " +getTime(map.get("fhz_mdhmi"))+ SDDef.JSONCCM);//分合闸状态/时间
			ret_buf.append(SDDef.JSONQUOT + getState(map.get("qr_fhz_state"),7) +"-"+getTime(map.get("qr_fhz_mdhmi"))+ SDDef.JSONCCM);//分合闸确认状态时间状态/时间
			
			//grid不用但是table表格需要的数据
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_FEETYPE,CommBase.CheckString(map.get("cacl_type"))) + SDDef.JSONCCM);//算费类型
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_YFFSPECCTRLTYPE,CommBase.CheckString(map.get("yffctrl_type"))) + SDDef.JSONCCM);//预付费表类型
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_PREPAYTYPE,CommBase.CheckString(map.get("feectrl_type"))) + SDDef.JSONCCM);//费控方式

			//8.28特殊修正  新增加的字段
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp000(map.get("total_yzbdl"))+ SDDef.JSONCCM);//有功追补累计用电量
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp000(map.get("total_wzbdl"))+ SDDef.JSONCCM);//无功追补累计用电量
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp000(map.get("total_ydl"))+ SDDef.JSONCCM);//有功累计用电量
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp000(map.get("total_wdl"))+ SDDef.JSONCCM);//无功累计用电量
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp000(map.get("zbele_money"))+ SDDef.JSONCCM);//追补电度电费
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp000(map.get("zbjbf_money"))+ SDDef.JSONCCM);//追补基本费电费
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp000(map.get("real_powrate"))+ SDDef.JSONCCM);//实际功率因数
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp000(map.get("ele_money"))+ SDDef.JSONCCM);//电度电费
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp000(map.get("jbf_money"))+ SDDef.JSONCCM);//基本费电费
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp000(map.get("powrate_money"))+ SDDef.JSONCCM);//力调电费
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp000(map.get("fxdf_iall_money"))+ SDDef.JSONCCM);//发行电费当月缴费总金额
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp000(map.get("fxdf_remain")) + SDDef.JSONCCM);//发行电费后剩余金额
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYM(map.get("fxdf_ym"))+ SDDef.JSONCCM);//发行电费数据日期(YYYYMM)
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(map.get("fxdf_data_ymd"))+ SDDef.JSONCCM);//发行电费数据日期YYYYMMDD
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToMDHMin(map.get("fxdf_calc_mdhmi"))+ SDDef.JSONCCM);//发行电费算费时间-MMDDHHMI
			String jswgbd1 = CommFunc.FormatBDDLp000(map.get("jsbd_zwz"));
			String jswgbd2 = CommFunc.FormatBDDLp000(map.get("jsbd1_zwz"));
			String jswgbd3 = CommFunc.FormatBDDLp000(map.get("jsbd2_zwz"));
			
			ret_buf.append(SDDef.JSONQUOT + (jswgbd1 + "-" + jswgbd2 + "-" + jswgbd3)+ SDDef.JSONCCM);//发行电费算费时间-MMDDHHMI
			
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);	
		}		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();
		
		return SDDef.SUCCESS;
	}
	
	private String getState(Object state,Integer level){
		String rtn_str = "成功";
		
		switch(level){
		case 0://1级报警
		case 3://2级报警
			if(state == null)
				rtn_str="[正常]";
			else
				rtn_str = (Short.parseShort(state.toString())==0 ? "[正常]" : "[报警]");
			break;
		case 1://1级短信报警
		case 2://1级声音报警			
		case 4://2级短信报警
		case 5://2级声音报警
		case 7://分合闸确认
			Integer ist = state == null ? 0 : Integer.parseInt(state.toString());
			Integer num = ist  & 0x3F ;
			Integer flag = (ist >> 6) & 0x03;
			flag = flag > 4 ? 4 : flag;
			String[] qr_stat= {"初始态","等待","成功","失败","未知"};//0:初始态 1:成功 2:失败
				rtn_str = "[" + qr_stat[flag]+ "] [" + num + "]";;
			break;
		case 6://分合闸
			if(state == null)
				rtn_str="[分闸]";
			else
				rtn_str = (state.equals(0) ? "[分闸]" : "[合闸]");
			break;
		
		}
		return rtn_str;		
	}
	
	private String getTime(Object time){
		String rtn_str = "";
		try{
			String tms = "00000000" + time.toString();
			String tm = tms.substring(tms.length()-8,tms.length());
			rtn_str = "[" +  tm.substring(0,2) + "-" + tm.substring(2,4) + " " + tm.substring(4,6)  + ":" +  tm.substring(6,8) + "]";

			//Integer tm = Integer.parseInt(time.toString());
			//rtn_str = "[" +  tm/1000000 + "-" + tm%1000000/10000 + " " + tm%10000/100  + ":" +  tm%100 + "]";
		}catch(Exception e){
			rtn_str ="[00-00 00:00]";
		}
		return rtn_str;
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
