package com.kesd.action.dyjc;

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
	/**
	 * 
	 */
	private static final long serialVersionUID = 6268085290910467823L;
	
	private String 		result;
	private String		field;		//所需查询的数据库字段
	
	
//	Formatter fmt = new Formatter();
	
	
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
		//修改sql语句增加mappay_state中阶梯、替换信息 查询字段 
		String temp=" b1.jt_total_zbdl,b1.fxdf_iall_money,b1.fxdf_ym,b1.jt_total_dl,b1.fxdf_iall_money2,b1.fxdf_data_ymd,b1.jt_reset_ymd,b1.fxdf_remain,b1.fxdf_calc_mdhmi,b1.jt_reset_mdhmi,b1.fxdf_remain2,b1.js_bc_ymd ,";
		String 	sql	= " select b.rtu_id,a.describe as rtu_desc, b.mp_id,e.describe as mp_desc,c.cons_no,c.describe as cons_desc,b1.buy_times,d.meter_id as meter_no," +
		        temp+
				"b1.cs_al1_state,b1.al1_mdhmi,b1.cs_al2_state,b1.al2_mdhmi,b1.cs_fhz_state,b2.cacl_type,b2.feectrl_type,b2.yffalarm_id,b2.prot_st,b2.prot_ed,yffmeter_type, " +
				" b.qr_al1_1_state,b.qr_al1_2_state,b.qr_al1_3_state,b.qr_al2_1_state,b.qr_al2_2_state,b.qr_al2_3_state,b.qr_fhz_state,b.qr_fhz_rf1_state,b.qr_fhz_rf2_state," +
				" b.qr_fz_times,b.qr_fz_rf1_times,qr_fz_rf2_times,b.qr_al1_1_mdhmi,b.qr_al1_2_mdhmi,b.qr_al1_3_mdhmi,b.qr_al2_1_mdhmi,b.qr_al2_2_mdhmi,b.qr_al2_3_mdhmi," +
				" b.qr_fhz_mdhmi,b.qr_fhz_rf1_mdhmi,b.qr_fhz_rf2_mdhmi,b1.fhz_mdhmi,b.cg_fhz_rf1_mdhmi,b.cg_fhz_rf2_mdhmi,b.qr_al1_1_uuid,b.qr_al2_1_uuid,b.out_info" + 
				" from ydparaben.dbo.rtupara as a, ydparaben.dbo.conspara as f,ydparaben.dbo.mppara as e, ydparaben.dbo.residentpara as c,ydparaben.dbo.meterpara as d," +
				" ydparaben.dbo.mppay_almstate as b,ydparaben.dbo.mppay_state as b1,ydparaben.dbo.mppay_para as b2 " +
				" where a.app_type=3 and a.cons_id=f.id and a.id=b.rtu_id and a.id=b1.rtu_id and a.id=b2.rtu_id and a.id=c.rtu_id and a.id=d.rtu_id " +
				" and b.mp_id=d.mp_id and b1.mp_id=d.mp_id and b2.mp_id=d.mp_id and c.id=d.resident_id and d.rtu_id=e.rtu_id and d.mp_id=e.id and e.bak_flag=0 and b1.cus_state is not null and b1.cus_state<>0 ";
		
		String whsql = "";
		
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String 	orgId = jsonObj.getString("orgId");
		String 	rtuId = jsonObj.getString("rtuId");
		Integer sType = jsonObj.getInt("searchType");
		String 	sText = jsonObj.getString("searchContent");
		int     updType = jsonObj.getInt("updType"); //查询类型
		if(!rtuId.equals("-1")){
			whsql = whsql + " and a.id=" + rtuId;
		}else if(!orgId.equals("-1")){
			whsql = whsql + " and f.org_id = "+ orgId;
		}
	
		if(sText != null && !sText.equals("") && !sText.isEmpty()){//查询条件
			switch(sType){
			case 1://终端名称
				whsql = whsql + " and a.describe like '%" + sText + "%'";
				break;
			case 2://用户户号
				whsql = whsql + " and c.cons_no like '%" + sText + "%'";
				break;
			case 3://用户名称
				whsql = whsql + " and c.describe like '%" + sText + "%'";
				break;
			case 4://联系电话
				whsql = whsql + " and c.phone like '%" + sText + "%'";
				break;
			case 5://表号
				whsql = whsql + " and d.meter_id like '%" + sText + "%'";
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
			whsql = whsql + " and b.qr_al1_1_state  > 0xc0 ";
			break;
		case 2 ://一级声音告警
			whsql = whsql + " and b.qr_al1_2_state  > 0xc0 ";
			break;
		case 3 ://二次短信告警
			whsql = whsql + " and b.qr_al2_1_state  > 0xc0 ";
			break;
	}
		sql = sql + whsql;
		sql = "select a.*,b.describe yffalarm_desc from (" + sql + ") a left join yffalarmpara b on a.yffalarm_id=b.id";
		
		list = j_dao.result(sql);
		if(list.size()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		} 
		
		ret_buf.append(SDDef.JSONROWSTITLE);
		for(int index =0;index<list.size();index++){
			Map<String,Object> map=list.get(index);
		
			ret_buf.append(SDDef.JSONLBRACES); 
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + map.get("rtu_id") + "_" + map.get("mp_id") + SDDef.JSONNZDATA);
			ret_buf.append(SDDef.JSONQUOT + (index+1) + SDDef.JSONCCM);//序号
			ret_buf.append(SDDef.JSONQUOT + "[" + map.get("rtu_id") + "]" + CommBase.CheckString(map.get("rtu_desc")) + SDDef.JSONCCM);//终端名称
			ret_buf.append(SDDef.JSONQUOT + "[" + map.get("mp_id") + "]" + CommBase.CheckString(map.get("cons_desc")) + SDDef.JSONCCM);//用户名称	
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckNum(map.get("buy_times"))+ SDDef.JSONCCM);//购电次数
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("yffalarm_desc")) + SDDef.JSONCCM);//报警方案
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckNum(map.get("prot_st")) + "-" + CommBase.CheckNum(map.get("prot_ed")) + SDDef.JSONCCM);//保电时间
			ret_buf.append(SDDef.JSONQUOT + getState(map.get("cs_al1_state"),0)+ " " + getTime(map.get("al1_mdhmi")) + SDDef.JSONCCM);//1级报警状态/时间
			ret_buf.append(SDDef.JSONQUOT + getState(map.get("qr_al1_1_state"),1) + " " +  getTime(map.get("qr_al1_1_mdhmi")) + SDDef.JSONCCM);//1级短信报警状态/时间
			ret_buf.append(SDDef.JSONQUOT + getState(map.get("qr_al1_2_state"),2) +  " " + getTime(map.get("qr_al1_2_mdhmi")) + SDDef.JSONCCM);//1级声音报警状态/时间
			ret_buf.append(SDDef.JSONQUOT + getState(map.get("cs_al2_state"),3) + " " +  getTime(map.get("al2_mdhmi")) + SDDef.JSONCCM);//2级报警状态/时间
			ret_buf.append(SDDef.JSONQUOT + getState(map.get("qr_al2_1_state"),4) +  " " + getTime(map.get("qr_al2_1_mdhmi")) + SDDef.JSONCCM);//2级短信报警状态/时间
			ret_buf.append(SDDef.JSONQUOT + getState(map.get("cs_fhz_state"),6) +  " " + getTime(map.get("fhz_mdhmi")) + SDDef.JSONCCM);//分合闸状态/时间
			ret_buf.append(SDDef.JSONQUOT + getState(map.get("qr_fhz_state"),7) +  " " + getTime(map.get("qr_fhz_mdhmi")) + SDDef.JSONCCM);//分合闸确认状态/时间
			
			//增加 阶梯、替换信息中的字段 
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp00(map.get("jt_total_zbdl")) + SDDef.JSONCCM);//阶梯追补累计用电量
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp00(map.get("jt_total_dl")) + SDDef.JSONCCM);//阶梯累计用电量
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(map.get("jt_reset_ymd")) + SDDef.JSONCCM);//阶梯切换日期 阶梯上次切换日期执行日期
			ret_buf.append(SDDef.JSONQUOT + FormatToMD_SM(map.get("jt_reset_mdhmi")) + SDDef.JSONCCM);//阶梯切换执行时间
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp00(map.get("fxdf_iall_money")) + SDDef.JSONCCM);//发行电费当月总金额
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp00(map.get("fxdf_remain"))  + SDDef.JSONCCM);//发行电费当月剩余金额
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp00(map.get("fxdf_remain2")) + SDDef.JSONCCM);//发行电费当月剩余金额2
			ret_buf.append(SDDef.JSONQUOT + FormatToYM(map.get("fxdf_ym")) + SDDef.JSONCCM);//发行电费年月
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(map.get("fxdf_data_ymd")) + SDDef.JSONCCM);//发行电费数据日期
			ret_buf.append(SDDef.JSONQUOT + FormatToMD_SM(map.get("fxdf_calc_mdhmi")) + SDDef.JSONCCM);//发行电费算费时间
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(map.get("jt_bc_ymd")) + SDDef.JSONCCM);//结算补差日期
				
			//列表中不显示，单击一行再详细信息中显示的
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("cons_no")) + SDDef.JSONCCM);//户号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("meter_no")) + SDDef.JSONCCM);//表号
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_FEETYPE,CommBase.CheckString(map.get("cacl_type"))) + SDDef.JSONCCM);//算费类型
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_PREPAYTYPE,CommBase.CheckString(map.get("feectrl_type"))) + SDDef.JSONCCM);//费控方式
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_YFFMETERTYPE,CommBase.CheckString(map.get("yffmeter_type"))) + SDDef.JSONCCM);//预付费表类型
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("cons_no")) + SDDef.JSONCCM);
			//列表中不显示,新增的字段
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatBDDLp00(map.get("fxdf_iall_money2")) + SDDef.JSONCCM);//发行电费当月总金额2			
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
	/*ylc*/
	   /** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : FormatToDH
	* <p>
	* <p>DESCRIPTION : 将1003--->10日03时
	* <p>			        
	* <p>COMPLETION
	* <p>INPUT       : Object obj
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/  
	public static String FormatToDH(Object obj){
		if(obj == null){
			//return "00日00时";
			return "";
		}else {
			String strdate = obj.toString().trim();
			if(strdate.equals("0")){
				return "00日00时";
			}
			
			while(strdate.length() < 4){
				strdate = "0" + strdate;
			}
			
			strdate = strdate.substring(0,2) + "日" + strdate.substring(2,4)+"时";
			return strdate;
		}
	}
	   /** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : Forma
	* <p>
	* <p>DESCRIPTION : 将String转化成"是" 或 者  "否" 。1->是,0/null->否
	* <p>			        
	* <p>COMPLETION
	* <p>INPUT       : String obj 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/    
	public String numToFlag(Object obj){
		if(obj==null){
			return "否";
		}else{
			String num=obj.toString().trim();
			if("1".equals(num)){
				return "是";
			}else{
				return "否";
			}
		}
	}
	   /** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : FormatToYD
	* <p>
	* <p>DESCRIPTION : 0206---->02月06日
	* <p>			        
	* <p>COMPLETION
	* <p>INPUT       : String obj 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/   
	public static String FormatToYD(Object obj)
	{
		if(obj == null){
			//return "00月00日";
			return "";
		}else {
			String strdate = obj.toString().trim();
			if(strdate.equals("0")){
				return "00月00日";
			}
			
			while(strdate.length() < 4){
				strdate = "0" + strdate;
			}
			
			strdate = strdate.substring(0,2) + "月" + strdate.substring(2,4)+"日";
			return strdate;
		}
		
	}
	   /** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : FormatToYM
	* <p>
	* <p>DESCRIPTION : 1206---->12年06月
	* <p>			        
	* <p>COMPLETION
	* <p>INPUT       : String obj 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/   
	public  String FormatToYM(Object obj){
		if(obj == null){
			//return "00年00月";
			return "";
		}else {
			String strdate = obj.toString().trim();
			if(strdate.equals("0")){
				return "00年00月";
			}
			
			while(strdate.length() < 6){
				strdate = "0" + strdate;
			}
			
			strdate = strdate.substring(0,4) + "年" + strdate.substring(4,6)+"月";
			return strdate;
		}
		
	}
	   /** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : FormatToMD_SM
	* <p>
	* <p>DESCRIPTION : 7310604---->07月31日 06时04分
	* <p>			        
	* <p>COMPLETION
	* <p>INPUT       : String obj 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/   
	public  String FormatToMD_SM(Object obj){
		if(obj == null){
			//return "00年00月";
			return "";
		}else {
			String strdate = obj.toString().trim();
			if(strdate.equals("0")){
				return "00月00日"+" "+"00时00分";
			}
			
			while(strdate.length() < 8){
				strdate = "0" + strdate;
			}
			
			strdate = strdate.substring(0,2) + "月" + strdate.substring(2,4)+"日"+" "+strdate.substring(4,6)+"时"+strdate.substring(6,8)+"分";
			return strdate;
		}
		
	}
	
	
	
}
