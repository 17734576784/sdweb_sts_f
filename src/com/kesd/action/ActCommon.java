package com.kesd.action;

/********************************************************************************************************
*                                        用电WEB Ver2.0													*
*																										*
*                           (c) Copyright 2010~,   KLD Automation Co., Ltd.								*
*                                           All Rights Reserved											*
*																										*
*	FileName	:	ActCommon.java																	    *
*	Description	:	公用方法类																				*
*	Author		:																						*
*	Date		:	2010/12/28																			*
*																										*
*   No.         Date         Modifier        Description												*
*   -----------------------------------------------------------------------------------------------     *
*********************************************************************************************************/
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.json.annotations.JSON;

import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.YDTable;
import com.kesd.comnt.ComntDef;
import com.kesd.comnt.ComntMsgProc;
import com.kesd.dbpara.RtuPara;
import com.kesd.dbpara.YffManDef;
import com.kesd.service.DBOper;
import com.kesd.util.Rd;
 
import com.libweb.common.CommBase;
import com.libweb.dao.HibDao;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;

/** ******************* CLASS DESCRIPTION *******************
* <p>
* <p>NAME        : ActCommon
* <p>
* <p>DESCRIPTION : 公用方法类
* <p>
* <p>  No.         Date         Modifier        Description	
* <p>---------------------------------------------------------
**************************************************************/

public class ActCommon extends ActionSupport{
	
	private static final long serialVersionUID = -1342097050932696374L;
	
	private String tableName;
	private String value;
	private String text;
	private String appType;
	private String result;
	private String flag;
	private String ydInfoNo;
	private String repairData = SDDef.EMPTY;	//补卡时返回上次缴费记录信息 
	
	/**
	 * 根据权限获取供电所
	 */
	@JSON(serialize=false)
	public String getOrg() throws Exception
	{
		//从session中取得用户信息
		
		String sql = "select id,describe from orgpara";
		YffManDef user = CommFunc.getYffMan();
		//权限范围: 非0所内管辖终端, 0所有终端
		if(user.getRank() != 0){
			flag = "1";
			sql += " where id=" + user.getOrgId();
		}
		result = DBOper.getValueText(sql);
		return SDDef.SUCCESS;
	}
	
	@JSON(serialize=false)
	public String getOperName() throws Exception
	{
		YffManDef user = CommFunc.getYffMan();
		result = user.getName();
		return SDDef.SUCCESS;
	}	
	
	/**
	 * 根据供电所id获取线路负责人
	 */
	@JSON(serialize=false)
	public String getLineFzByOrg()throws Exception
	{
		if(value == null || value.isEmpty()){		//value:org_id
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		String sql = "select id,describe from line_fzman where org_id=" +  value;
		result = DBOper.getValueText(sql);
		return SDDef.SUCCESS;
	}
	/**
	 * 根据供电所id获取片区
	 */
	@JSON(serialize=false)
	public String getAreaByOrg()throws Exception
	{
		if(value == null || value.isEmpty()){		//value:org_id
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		String sql = "select id,describe from areapara where org_id=" +  value;
		result = DBOper.getValueText(sql);
		return SDDef.SUCCESS;
	}
	//qjl add 20150209 start
	/**
	 * 根据供电所id获取台区
	 */
	@JSON(serialize=false)
	public String getConsByOrg()throws Exception
	{
		if(value == null || value.isEmpty()){		//value:org_id
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		String sql = "select id,describe from conspara where org_id=" +  value;
		result = DBOper.getValueText(sql);
		return SDDef.SUCCESS;
	}
	//qjl add 20150209 end	
	/**
	 * 根据供电所id获取终端
	 */
	@JSON(serialize=false)
	public String getRtuByOrg()throws Exception {

		if(value == null || value.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		String sql = "select r.id,r.describe from rtupara r,orgpara o,conspara c where c.org_id=o.id and r.cons_id=c.id and r.app_type=" + appType + " and o.id=" + value;
		result = DBOper.getValueText(sql);
		return SDDef.SUCCESS;
	}
	
	@JSON(serialize=false)
	public String getRtuByFzman()throws Exception {

		if(value == null || value.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		String sql = "select r.id,r.describe from rtupara r,line_fzman l,conspara c where c.line_fzman_id=l.id and r.cons_id=c.id and r.app_type=" + appType + " and l.id=" + value;
		result = DBOper.getValueText(sql);
		return SDDef.SUCCESS;
	}

	/**
	 * 获取指定居民区的居民
	 */
	@JSON(serialize=false)
	public String getResidentByConsid() throws Exception
	{
		String sql = "select res.rtu_id,res.id,res.describe from residentpara res,rtupara rtu where res.rtu_id=rtu.id and rtu.cons_id= "+ value;
		result = DBOper.getResidentValueText(sql);
		return SDDef.SUCCESS;
	}
	
	/**
	 * 获取费率方案列表
	 */
	@JSON(serialize=false)
	public String getTariffProject() throws Exception
	{
		String sql = "select id,describe,rated_z from yffratepara";
		result = DBOper.getTariffValueText(sql);
		return SDDef.SUCCESS;
	}
	
	/**
	 根据片区获取电表
	 */
	@JSON(serialize=false)
	public String getMeterByArea() throws Exception {
		if(value == null || value.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		String sql = "select cast(b.rtu_id as varchar(8))  +'_'+ cast(b.mp_id as varchar(8)),b.describe from areapara a,meterpara b,meter_extparanp c where a.id=c.area_id and b.mp_id=c.mp_id and b.rtu_id>=" + SDDef.NPRTUID + " and a.id=" + value;
		result = DBOper.getValueText(sql);
		
		return SDDef.SUCCESS;
	}
	
	/**
	 * 更改通知
	 */
	public String dbUpdate() {
		boolean    reload_flag = false;
		byte[]     reload_mask = new byte[32];
		String[]   reload_str  = value.split(",");
		
		for (int i = 0; i < 32; i++) {
			reload_mask[i] = (byte)CommBase.strtoi(reload_str[i]);
			if (reload_mask[i] != 0) reload_flag |= true;
		}
		
		if (reload_flag == false) {
			result = "";
			return SDDef.SUCCESS;
		}
		ComntMsgProc.sendReloadTableMsg(CommFunc.getYffMan().getName().trim(), reload_mask, ComntDef.SALEMANAGER_VERSION);
		result = SDDef.SUCCESS;
		return SDDef.SUCCESS;
	}
	
	/**
	 * 查找数据数据字典填充下拉列表 一次性返回多个
	 * @return : {rows:[table1:[{value:1,text:"text1"},{value:2,text:"text2"}],table2:[{value:1,text:"text1"},{value:2,text:"text2"}]]}
	 */
	public String retDict(){
		
		if(text == null || text.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		StringBuffer ret = new StringBuffer();
		ret.append("{rows:[");
		
		//解析text : {item:["t1","t2"]}
		JSONObject j_obj = JSONObject.fromObject(text);
		JSONArray  rows  = j_obj.getJSONArray("item");
		for(int i = 0; i < rows.size(); i++){
			String item = rows.getString(i);
			Map<Integer, String> map = Rd.getDict(item);
			
			String dict = DBOper.getDictValueText(map);
			ret.append("{" + item + ":" + dict + "},");
		}
		
		ret.deleteCharAt(ret.length() - 1);
		ret.append("]}");
		
		result = ret.toString();
		return SDDef.SUCCESS;
	}
	
	/**
	 * 查找数据库填充下拉列表 一次性返回多个
	 * @return : {rows:[table1:[{value:1,text:"text1"},{value:2,text:"text2"}],table2:[{value:1,text:"text1"},{value:2,text:"text2"}]]}
	 */
	@JSON(serialize=false)
	public String getValueAndText(){
		
		if(tableName == null || tableName.isEmpty()){
			result = "";
			return SDDef.SUCCESS;
		}
		StringBuffer ret = new StringBuffer();
		ret.append("{rows:[");
		
		//tableName : {tables:["table1","table2",{table:"table",field:["field1","field2"],value:[1,2]}]}
		JSONObject j_obj = JSONObject.fromObject(tableName);
		JSONArray  rows  = j_obj.getJSONArray("tables");
		String sql = "";
		for(int i = 0; i < rows.size(); i++){
			Object obj = rows.get(i);
			String table = "";
			//是字符串时 表示直接查询该表
			if(obj instanceof String){
				table = obj.toString();
				sql = "select id,describe from " + table.toLowerCase();
			}
			//是json对象时 需要添加对应条件
			else if(obj instanceof JSONObject){
				JSONObject tmp = (JSONObject)obj;
				
				table = tmp.getString("table");
				JSONArray fields = tmp.getJSONArray("field");	//字段
				JSONArray values = tmp.getJSONArray("value");	//值
				
				sql = "select id,describe from " + table.toLowerCase() + " where 1=1";
				//添加查询条件
				for(int j = 0; j < fields.size(); j++){
					sql += " and " + fields.getString(j) + "='" + values.getString(j)+"'";
				}
			}
			
			ret.append("{" + table + ":" + DBOper.getValueText(sql) + "},");
		}
		ret.deleteCharAt(ret.length() - 1);
		ret.append("]}");
		
		result = ret.toString();
		return SDDef.SUCCESS;
	}
	
	/**
	 * 20140615新增
	 * 查找数据库填充外接卡表下拉列表 一次性返回多个
	 * @return : {rows:[table1:[{value:1,text:"text1"},{value:2,text:"text2"}],table2:[{value:1,text:"text1"},{value:2,text:"text2"}]]}
	 */
	@JSON(serialize=false)
	public String getValueAndTextExtCard(){
		
		if(tableName == null || tableName.isEmpty()){
			result = "";
			return SDDef.SUCCESS;
		}
		StringBuffer ret = new StringBuffer();
		ret.append("{rows:[");
		
		//tableName : {tables:["table1","table2",{table:"table",field:["field1","field2"],value:[1,2]}]}
		JSONObject j_obj = JSONObject.fromObject(tableName);
		JSONArray  rows  = j_obj.getJSONArray("tables");
		String sql = "";
		for(int i = 0; i < rows.size(); i++){
			Object obj = rows.get(i);
			String table = "";
			//是字符串时 表示直接查询该表
			table = obj.toString();
			sql = "select cardtype,describe from " + table.toLowerCase();
			ret.append("{" + table + ":" + DBOper.getValueText(sql) + "},");
		}
		ret.deleteCharAt(ret.length() - 1);
		ret.append("]}");
		
		result = ret.toString();
		return SDDef.SUCCESS;
	}
	
	/**低压预付费，缴费历史记录查询*/
	@JSON(serialize=false)
	public String getDyYFFRecs()throws Exception{
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String rtu_id	= jsonObj.getString("rtu_id");
		String mp_id	= jsonObj.getString("mp_id");
		String oper_type = jsonObj.getString("oper_type");
		
		int topNum 	= 0;
		if(!jsonObj.containsKey("top_num")) {
			topNum = 5;
		} else {
			topNum 	= jsonObj.getInt("top_num");
		}
		
		StringBuffer sbfSql = new StringBuffer();
		StringBuffer ret_buf = new StringBuffer();
		
		String yyyy				= new SimpleDateFormat("yyyy").format(new Date());
		int year				= Integer.parseInt(yyyy);
		int all_num				= 0;
		JDBCDao j_dao			= new JDBCDao();
		ret_buf.append(SDDef.JSONROWSTITLE);
		
		//查询最近两年的表
		for(int i = 0; i < 4; i++, year--){
			String tabname = YDTable.TABLEDATA_JYFF + year;
			sbfSql.append("select top " + (topNum - all_num) + " b.rtu_id, b.mp_id, b.res_id, r.describe as rtu_desc, b.wasteno,a.cons_no,a.describe,");
			sbfSql.append("a.address,b.op_type,b.op_date,b.op_time,b.op_man,b.pay_type,b.pay_money,b.othjs_money,b.zb_money,");
			sbfSql.append("b.all_money, b.alarm1,b.alarm2, b.shutdown_val, b.buy_times, b.buy_dl ,c.describe conDesc,o.describe orgDesc,o.addr orgAddr,o.telno");
			sbfSql.append(" from ydparaben.dbo.residentpara a, " + tabname + " b, ydparaben.dbo.rtupara as r,ydparaben.dbo.mppay_state as d, ydparaben.dbo.conspara c, ydparaben.dbo.orgpara o");
			//将a.id = b.res_id  改为了 a.cons_no = b.res_id		//20150312 zp
			sbfSql.append(" where c.org_id = o.id and a.rtu_id = r.id and r.cons_id= c.id and a.rtu_id = b.rtu_id and a.rtu_id = r.id and a.rtu_id = d.rtu_id and a.id = b.res_id and b.mp_id = d.mp_id");
			sbfSql.append(" and b.op_date >=0 and b.op_date >= d.kh_date and b.op_date >=d.xh_date and b.visible_flag=1");
			sbfSql.append(" and b.rtu_id = " + rtu_id);
			sbfSql.append(" and b.mp_id =" + mp_id);
			sbfSql.append(" order by b.op_date desc, b.op_time desc");
			
			List<Map<String, Object>>	list = j_dao.result(sbfSql.toString());
			sbfSql.delete(0, sbfSql.length());
			if(list == null || list.size() == 0){
				continue;
			}
			
			all_num += list.size();
			
			for (int index = 0; index < list.size(); index++) {
				Map<String,Object> map = list.get(index);
				
				ret_buf.append(SDDef.JSONLBRACES); 
				ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + (year + "" + (index+1)) + SDDef.JSONNZDATA);
				ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(map.get("op_date"), "day") 	+ " " + CommFunc.FormatToHMS((map.get("op_time")),1) + SDDef.JSONCCM);//操作日期
				ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_YFFOPTYPE, map.get("op_type"))    + SDDef.JSONCCM);//操作类型
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("pay_money")) 	+ SDDef.JSONCCM);//缴费金额(元)	
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("all_money")) 	+ SDDef.JSONCCM);//总金额(元)
				//ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("wasteno")) 	+ SDDef.JSONCCM);//流水号
//				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("alarm1")) 	+ SDDef.JSONCCM);//报警值1
//				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("shutdown_val"))	+ SDDef.JSONCCM);//断电金额(元)
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("buy_times"))  + SDDef.JSONCCM);//购电次数
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("buy_dl"))  + SDDef.JSONCCM);//购电量
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("wasteno"))    + SDDef.JSONCCM);//流水号
				ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_PAYTYPE, map.get("pay_type"))  + SDDef.JSONCCM);//缴费方式
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("op_man")) 	+ SDDef.JSONCCM);//操作员
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("orgAddr")) 	+ SDDef.JSONCCM);//购电地址
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("telno")) 	+ SDDef.JSONCCM);//服务电话
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("orgDesc")) 	+ SDDef.JSONCCM);//供电所地址
				
				ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
				ret_buf.append(SDDef.JSONRBCM);
				
				//补卡操作，查询记录时，要将上次缴费记录展示到界面
				if(CommBase.strtoi(oper_type) == SDDef.YFF_OPTYPE_REPAIR 
						&& index == 0){
					JSONObject json_repairData = new JSONObject();
					json_repairData.put("pay_money", CommBase.CheckString(map.get("pay_money")));	//缴费金额(元)
					json_repairData.put("zb_money", CommBase.CheckString(map.get("zb_money")));		//追补金额(元)
					json_repairData.put("all_money", CommBase.CheckString(map.get("all_money")));	//总金额(元)
					repairData = json_repairData.toString();
				}
			}
			if(all_num >= topNum) break;
		}
		
		if(all_num == 0) {	//记录为空
			result = "";
		}
		else {
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBRACES);
			result = ret_buf.toString();
		}
		
		return SDDef.SUCCESS;
	}
	
	//20140613新增
	/**低压预付费，外接电表，缴费历史记录查询*/
	@JSON(serialize=false)
	public String getDyYFFRecsExt()throws Exception{
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String rtu_id	= jsonObj.getString("rtu_id");
		String mp_id	= jsonObj.getString("mp_id");
		int topNum 	= 0;
		if(!jsonObj.containsKey("top_num")) {
			topNum = 5;
		} else {
			topNum 	= jsonObj.getInt("top_num");
		}
		
		StringBuffer sbfSql = new StringBuffer();
		StringBuffer ret_buf = new StringBuffer();
		
		String yyyy				= new SimpleDateFormat("yyyy").format(new Date());
		int year				= Integer.parseInt(yyyy);
		int all_num				= 0;
		JDBCDao j_dao			= new JDBCDao();
		ret_buf.append(SDDef.JSONROWSTITLE);
		
		//查询最近两年的表
		for(int i = 0; i < 4; i++, year--){
			String tabname = YDTable.TABLEDATA_JYFF + year;
			sbfSql.append("select top " + (topNum - all_num) + " b.rtu_id, b.mp_id, b.res_id, r.describe as rtu_desc, b.wasteno,a.cons_no,a.describe,");
			sbfSql.append("a.address,b.op_type,b.op_date,b.op_time,b.op_man,b.pay_type,b.pay_money,b.othjs_money,b.zb_money,");
			sbfSql.append("b.all_money, b.alarm1,b.alarm2, b.shutdown_val, b.buy_times,");
			//20140614缴费记录中新增加购电量和表码差
			sbfSql.append("b.buy_dl, b.pay_bmc ");
			sbfSql.append(" from ydparaben.dbo.residentpara a, " + tabname + " b, ydparaben.dbo.rtupara as r,ydparaben.dbo.mppay_state as d ");
			sbfSql.append(" where a.rtu_id = b.rtu_id and a.rtu_id = r.id and a.rtu_id = d.rtu_id and a.id = b.res_id and b.mp_id = d.mp_id");
			sbfSql.append(" and b.op_date >=0 and b.op_date >= d.kh_date and b.op_date >=d.xh_date and b.visible_flag=1");
			sbfSql.append(" and b.rtu_id = " + rtu_id);
			sbfSql.append(" and b.mp_id =" + mp_id);
			sbfSql.append(" order by b.wasteno desc");
			
			List<Map<String, Object>>	list = j_dao.result(sbfSql.toString());
			sbfSql.delete(0, sbfSql.length());
			if(list == null || list.size() == 0){
				continue;
			}
			
			all_num += list.size();
			
			for (int index = 0; index < list.size(); index++) {
				Map<String,Object> map = list.get(index);
				
				ret_buf.append(SDDef.JSONLBRACES); 
				ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + (year + "" + (index+1)) + SDDef.JSONNZDATA);
				ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(map.get("op_date")) 	+ " " + CommFunc.FormatToHMS((map.get("op_time")),2) + SDDef.JSONCCM);//操作日期
				ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_YFFOPTYPE, map.get("op_type"))    + SDDef.JSONCCM);//操作类型
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("pay_money")) 	+ SDDef.JSONCCM);//缴费金额(元)
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("othjs_money"))+ SDDef.JSONCCM);//结算金额(元)
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("zb_money")) 	+ SDDef.JSONCCM);//追补金额(元)
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("all_money")) 	+ SDDef.JSONCCM);//总金额(元)	
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("alarm1")) 	+ SDDef.JSONCCM);//报警值1
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("shutdown_val"))	+ SDDef.JSONCCM);//断电金额(元)
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("buy_times"))  + SDDef.JSONCCM);//购电次数
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("buy_dl"))   + SDDef.JSONCCM);//购电量
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("pay_bmc"))  + SDDef.JSONCCM);//表码差
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("wasteno"))    + SDDef.JSONCCM);//流水号
				ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_PAYTYPE, map.get("pay_type"))  + SDDef.JSONCCM);//缴费方式
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("op_man")) 	+ SDDef.JSONCCM);//操作员
				
				ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
				ret_buf.append(SDDef.JSONRBCM);
			}
			if(all_num >= topNum) break;
		}
		
		if(all_num == 0) {	//记录为空
			result = "";
		}
		else {
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBRACES);
			result = ret_buf.toString();
		}
		
		return SDDef.SUCCESS;
	}
	
	/**高压预付费，缴费历史记录查询*/
	@JSON(serialize=false)
	public String getGyYFFRecs() throws Exception {
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String rtu_id	= jsonObj.getString("rtu_id");
		String zjg_id	= jsonObj.getString("zjg_id");
		String topNum 	= jsonObj.getString("top_num");
		if(rtu_id.equals("undefined") || zjg_id.equals("undefined")){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		if(topNum.equals("undefined") || Integer.parseInt(topNum) <= 0){
			 topNum	= "5";
		}
		int top_num = Integer.parseInt(topNum);
		
		JDBCDao j_dao			= new JDBCDao();
		StringBuffer ret_buf	= new StringBuffer();
		String yyyy				= new SimpleDateFormat("yyyy").format(new Date());
		int year				= Integer.parseInt(yyyy);
		int all_num				= 0;
		ret_buf.append(SDDef.JSONROWSTITLE);
		
		//查询最近两年的表
		for(int i = 0; i < 4; i++, year--){
			String tabname = YDTable.TABLEDATA_ZYFF + year;
			String sql = "select top " + (top_num - all_num) + " b.rtu_id, b.zjg_id, b.wasteno, a.busi_no as cons_no,a.describe, a.addr,"
					+ "b.op_type,b.op_date,b.op_time,b.op_man,b.pay_type,b.pay_money,b.othjs_money,b.zb_money,"
					+ "b.all_money, b.shutdown_val,b.alarm1, b.alarm2, b.buy_times "
					+ "from ydparaben.dbo.conspara a, ydparaben.dbo.rtupara as r, " + tabname + " b, ydparaben.dbo.zjgpay_state as s "
					+ "where r.cons_id = a.id  and r.id = b.rtu_id and r.id = s.rtu_id and s.zjg_id = b.zjg_id and b.op_date>= s.kh_date " 
					+ "and b.op_date>=s.xh_date and b.visible_flag=1 and b.rtu_id=" + rtu_id + " and b.zjg_id=" + zjg_id + " order by b.wasteno desc ";
			List<Map<String, Object>>	list = j_dao.result(sql);
			if(list == null || list.size() == 0){
				continue;
			}
			
			all_num += list.size();
			
			for (int index = 0; index < list.size(); index++) {
				Map<String,Object> map = list.get(index);
				
				ret_buf.append(SDDef.JSONLBRACES); 
				ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + (year + "" + (index+1)) + SDDef.JSONNZDATA);
				ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(map.get("op_date")) 	+ " " + CommFunc.FormatToHMS((map.get("op_time")),2) + SDDef.JSONCCM);//操作日期
				ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_YFFOPTYPE, map.get("op_type"))    + SDDef.JSONCCM);//操作类型
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("pay_money")) 	+ SDDef.JSONCCM);//缴费金额(元)
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("zb_money")) 	+ SDDef.JSONCCM);//追补金额(元)
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("othjs_money"))+ SDDef.JSONCCM);//结算金额(元)
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("all_money")) 	+ SDDef.JSONCCM);//总金额(元)
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("shutdown_val"))	+ SDDef.JSONCCM);//断电金额(元)
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("alarm1")) 	+ SDDef.JSONCCM);//报警值1
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("alarm2")) 	+ SDDef.JSONCCM);//报警值2
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("buy_times"))  + SDDef.JSONCCM);//购电次数
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("wasteno"))    + SDDef.JSONCCM);//流水号
				ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_PAYTYPE, map.get("pay_type"))  + SDDef.JSONCCM);//缴费方式
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("op_man")) 	+ SDDef.JSONCCM);//操作员

				ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
				ret_buf.append(SDDef.JSONRBCM);
			}
			if(all_num >= top_num) break;
		}
		
		if(all_num == 0) {	//记录为空
			result = "";
		}
		else {
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBRACES);
			result = ret_buf.toString();
		}
		return SDDef.SUCCESS;
	}
	
	/**农排预付费，缴费历史记录查询*/
	@JSON(serialize=false)
	public String getNpYFFRecs(){
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String area_id	= jsonObj.getString("area_id");
		String farmer_id	= jsonObj.getString("farmer_id");
		int topNum 	= 0;
		if(!jsonObj.containsKey("top_num")) {
			topNum = 5;
		} else {
			topNum 	= jsonObj.getInt("top_num");
		}
				
		StringBuffer sbfSql = new StringBuffer();
		StringBuffer ret_buf = new StringBuffer();
		
		String yyyy				= new SimpleDateFormat("yyyy").format(new Date());
		int year				= Integer.parseInt(yyyy);
		int all_num				= 0;
		JDBCDao j_dao			= new JDBCDao();
		ret_buf.append(SDDef.JSONROWSTITLE);
		
		//查询最近两年的表
		for(int i = 0; i < 4; i++, year--){
			String tablename = YDTable.TABLEDATA_NYFF + year;
			
			sbfSql.append("select top " + (topNum - all_num) + " n.op_date, n.op_type, n.pay_money, n.othjs_money, n.zb_money, n.all_money, n.buy_times, n.wasteno, n.pay_type, n.op_man, n.op_time");
			sbfSql.append(" from ydparaben.dbo.farmerpay_state as f left join " + tablename + " as n on");
			sbfSql.append(" n.area_id = f.area_id and n.farmer_id = f.farmer_id and n.op_date >= f.kh_date and n.op_date >= f.xh_date ");
			sbfSql.append(" where n.area_id = " + area_id );
			sbfSql.append(" and n.farmer_id = " + farmer_id);
			sbfSql.append(" and n.op_date > 0 and n.visible_flag = 1 order by n.wasteno desc ");
			
			List<Map<String, Object>>	list = j_dao.result(sbfSql.toString());
			sbfSql.delete(0, sbfSql.length());
			
			if(list == null || list.size() == 0){
				continue;
			}
			
			all_num += list.size();
			
			for (int index = 0; index < list.size(); index++) {
				Map<String,Object> map = list.get(index);
				
				ret_buf.append(SDDef.JSONLBRACES); 
				ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + (year + "" + (index+1)) + SDDef.JSONNZDATA);
				ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(map.get("op_date")) 	+ " " + CommFunc.FormatToHMS((map.get("op_time")),2) + SDDef.JSONCCM);//操作日期
				ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_YFFOPTYPE, map.get("op_type"))    + SDDef.JSONCCM);//操作类型
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("pay_money")) 	+ SDDef.JSONCCM);//缴费金额(元)
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("othjs_money"))+ SDDef.JSONCCM);//结算金额(元)
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("zb_money")) 	+ SDDef.JSONCCM);//追补金额(元)
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("all_money")) 	+ SDDef.JSONCCM);//总金额(元)	
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("buy_times"))  + SDDef.JSONCCM);//购电次数
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("wasteno"))    + SDDef.JSONCCM);//流水号
				ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_PAYTYPE, map.get("pay_type"))  + SDDef.JSONCCM);//缴费方式
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("op_man")) 	+ SDDef.JSONCCM);//操作员
				
				ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
				ret_buf.append(SDDef.JSONRBCM);
			}
			if(all_num >= topNum) break;
		}
		
		if(all_num == 0) {	//记录为空
			result = "";
		}
		else {
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBRACES);
			result = ret_buf.toString();
			ydInfoNo = String.valueOf(topNum);   //用电信息查询条数和缴费记录条数一致
		}
		return SDDef.SUCCESS;
	}
	
	/**农排用电信息记录*/
	@JSON(serialize=false)
	public String getYDInfoNP(){
		if(result == null || result.isEmpty()) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String area_id = jsonObj.getString("area_id");
		String farmer_id = jsonObj.getString("farmer_id");
		String top_Num = jsonObj.getString("top_num");
		int topNum = Integer.parseInt(top_Num);
		
		StringBuffer sbfSql = new StringBuffer();
		StringBuffer ret_buf = new StringBuffer();
		
		String yyyy				= new SimpleDateFormat("yyyy").format(new Date());
		int year				= Integer.parseInt(yyyy);
		int all_num				= 0;
		JDBCDao j_dao			= new JDBCDao();
		ret_buf.append(SDDef.JSONROWSTITLE);
		
		//查询最近两年的表
		for(int i = 0; i < 4; i++, year--){
			String tablename = YDTable.TABLEDATA_NPYD_RECORD + year;
			
			sbfSql.append("select top " + (topNum - all_num) + " r.describe as rtu_desc, m.describe as mp_desc, npyd.card_no, npyd.begin_date, npyd.begin_time, npyd.end_date, npyd.end_time, npyd.fee, npyd.use_money, npyd.remain_money, ");
			sbfSql.append("npyd.use_dl, npyd.zero_dl, npyd.farmer_state from "+ tablename + " as npyd, rtupara as r, meterpara as m ");
			sbfSql.append("where npyd.area_id="+area_id+" and npyd.farmer_id="+farmer_id+" and npyd.rtu_id = r.id and npyd.rtu_id = m.rtu_id and npyd.mp_id = m.mp_id ");
			sbfSql.append("order by npyd.begin_date, npyd.begin_time ");
			
			List<Map<String, Object>>	list = j_dao.result(sbfSql.toString());
			sbfSql.delete(0, sbfSql.length());
			
			if(list == null || list.size() == 0){
				continue;
			}
			
			all_num += list.size();
			
			Map<Integer, String> ydstate = Rd.getDict(Dict.DICTITEM_FARMER_STATE);
			
			for (int index = 0; index < list.size(); index++) {
				Map<String,Object> map = list.get(index);
				
				ret_buf.append(SDDef.JSONLBRACES); 
				ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + (index+1) + SDDef.JSONNZDATA);
				ret_buf.append(SDDef.JSONQUOT + (index+1) + SDDef.JSONCCM);												//序号
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("rtu_desc")) 		+ SDDef.JSONCCM);		//终端名称
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("mp_desc")) 		+ SDDef.JSONCCM);		//电表名称
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("card_no")) 		+ SDDef.JSONCCM);		//卡号
				ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(map.get("begin_date")) 	+ SDDef.JSONCCM);		//开始日期
				ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToHMS(map.get("begin_time"),1) 	+ SDDef.JSONCCM);		//开始时间
				ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(map.get("end_date")) 		+ SDDef.JSONCCM);		//结束日期
				ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToHMS(map.get("end_time"),1) 	+ SDDef.JSONCCM);		//结束时间
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("use_money")) 		+ SDDef.JSONCCM);		//费率
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("use_money")) 		+ SDDef.JSONCCM);		//本次用电金额
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("remain_money")) 	+ SDDef.JSONCCM);		//剩余金额
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("use_dl")) 		+ SDDef.JSONCCM);		//本次用电电量
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("zero_dl")) 		+ SDDef.JSONCCM);		//本次过零电量
				
				String tmp = CommBase.CheckString(map.get("farmer_state"));
				int itmp = CommBase.strtoi(tmp);
				ret_buf.append(SDDef.JSONQUOT + ydstate.get(itmp) + SDDef.JSONCCM);		//用电状态	0 正常 1 系统停电 2 无脉冲自动拉闸 3人为锁表
			
				ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
				ret_buf.append(SDDef.JSONRBCM);
			}
			if(all_num >= topNum) break;
		}
		if(all_num == 0) {	//记录为空
			result = "";
		}
		else {
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBRACES);
			result = ret_buf.toString();
		}
		return SDDef.SUCCESS;
	}
	
	/**低压冲正时 获取最近一条缴费记录信息*/
	public String lastPayInfoDY(){
		if(result == null || result.isEmpty()) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String rtu_id	= jsonObj.getString("rtu_id");
		String mp_id	= jsonObj.getString("mp_id");
		
		JDBCDao j_dao			= new JDBCDao();
		StringBuffer ret_buf	= new StringBuffer();
		String yyyy				= new SimpleDateFormat("yyyy").format(new Date());
		int year				= Integer.parseInt(yyyy);
		int all_num				= 0;
		
		//查找最新缴费记录（从近两年的表中查）
		for(int i = 0; i < 2; i++, year--){
			String tabname = YDTable.TABLEDATA_JYFF + year;
			String sql = "select top 1 b.rtu_id, b.mp_id, b.wasteno,"
					+ "b.op_type,b.op_date,b.op_time,b.op_man,b.pay_type,b.pay_money,b.othjs_money,b.zb_money,"
					+ "b.all_money, b.shutdown_val,b.alarm1, b.alarm2, b.buy_times "
					+ "from ydparaben.dbo.conspara a, ydparaben.dbo.rtupara as r, " + tabname + " b, ydparaben.dbo.mppay_state as s "
					+ "where r.cons_id = a.id and r.id = b.rtu_id and r.id = s.rtu_id and s.mp_id = b.mp_id and b.op_date>= s.kh_date " 
					+ "and b.op_date>=s.xh_date and b.visible_flag=1 and b.rtu_id=" + rtu_id + " and b.mp_id=" + mp_id + 
					" and (b.op_type="+SDDef.YFF_OPTYPE_ADDRES+" or b.op_type="+SDDef.YFF_OPTYPE_PAY + " or b.op_type=" + SDDef.YFF_OPTYPE_RESTART + " or b.op_type=" + SDDef.YFF_OPTYPE_CHANGEMETER + " or b.op_type=" + SDDef.YFF_OPTYPE_JSBC +")"
					+ " order by b.op_date desc, b.op_time desc";
			
			List<Map<String, Object>>	list = j_dao.result(sql);
			if(list == null || list.size() == 0){
				continue;
			}
			
			all_num += list.size();
			
			Map<String,Object> map = list.get(0);
			
			ret_buf.append("{op_date:" 		+ map.get("op_date") 		+ ",");
			ret_buf.append("op_time:" 		+ map.get("op_time") 		+ ",");
			ret_buf.append("last_wastno:\"" + map.get("wasteno") 		+ "\",");
			ret_buf.append("pay_money:" 	+ map.get("pay_money") 		+ ",");
			ret_buf.append("othjs_money:" 	+ map.get("othjs_money") 	+ ",");
			ret_buf.append("zb_money:" 		+ map.get("zb_money") 		+ ",");
			ret_buf.append("all_money:" 	+ map.get("all_money") 		+ "}");
			
			if(all_num >= 1)break;
		}
		
		if(all_num == 0) {	//记录为空
			result = "";
		}
		else {
			result = ret_buf.toString();
		}
		return SDDef.SUCCESS;
	}
	
	/**高压冲正时 获取最近一条缴费记录信息*/
	public String lastPayInfoGY(){
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String rtu_id	= jsonObj.getString("rtu_id");
		String zjg_id	= jsonObj.getString("zjg_id");
		
		JDBCDao j_dao			= new JDBCDao();
		StringBuffer ret_buf	= new StringBuffer();
		String yyyy				= new SimpleDateFormat("yyyy").format(new Date());
		int year				= Integer.parseInt(yyyy);
		int all_num				= 0;
		
		//查找最新缴费记录（从近两年的表中查）
		for(int i = 0; i < 2; i++, year--){
			String tabname = YDTable.TABLEDATA_ZYFF + year;
			String sql = "select top 1 b.rtu_id, b.zjg_id, b.wasteno, a.busi_no as cons_no,a.describe, a.addr,"
					+ "b.op_type,b.op_date,b.op_time,b.op_man,b.pay_type,b.pay_money,b.othjs_money,b.zb_money,"
					+ "b.all_money, b.shutdown_val,b.alarm1, b.alarm2, b.buy_times "
					+ "from ydparaben.dbo.conspara a, ydparaben.dbo.rtupara as r, " + tabname + " b, ydparaben.dbo.zjgpay_state as s "
					+ "where r.cons_id = a.id  and r.id = b.rtu_id and r.id = s.rtu_id and s.zjg_id = b.zjg_id and b.op_date>= s.kh_date " 
					+ "and b.op_date>=s.xh_date and b.visible_flag=1 and b.rtu_id=" + rtu_id + " and b.zjg_id=" + zjg_id + 
					" and (b.op_type="+SDDef.YFF_OPTYPE_ADDRES+" or b.op_type="+SDDef.YFF_OPTYPE_PAY + " or b.op_type=" + SDDef.YFF_OPTYPE_RESTART +" or b.op_type=" + SDDef.YFF_OPTYPE_CHANGEMETER + " or b.op_type=" + SDDef.YFF_OPTYPE_JSBC +")"
					+ " order by b.op_date desc, b.op_time desc";

			List<Map<String, Object>>	list = j_dao.result(sql);
			if(list == null || list.size() == 0){
				continue;
			}
			
			all_num += list.size();
			
			Map<String,Object> map = list.get(0);
			
			ret_buf.append("{op_date:" 		+ map.get("op_date") 		+ ",");
			ret_buf.append("op_time:" 		+ map.get("op_time") 		+ ",");
			ret_buf.append("last_wastno:\"" + map.get("wasteno") 		+ "\",");
			ret_buf.append("pay_money:" 	+ map.get("pay_money") 		+ ",");
			ret_buf.append("othjs_money:" 	+ map.get("othjs_money") 	+ ",");
			ret_buf.append("zb_money:" 		+ map.get("zb_money") 		+ ",");
			ret_buf.append("all_money:" 	+ map.get("all_money") 		+ ",");
			ret_buf.append("buy_times:" 	+ map.get("buy_times") 		+ "}");

			if(all_num >= 1)break;
		}
		
		if(all_num == 0) {	//记录为空
			result = "";
		}
		else {
			result = ret_buf.toString();
		}
		return SDDef.SUCCESS;
	}
	
	/**
	 * 权限管理中，根据用户管理权限查询(仅在预付费人员管理中使用)
	 */
	@JSON(serialize=false)
	public String getOrgByYHQX() throws Exception
	{
		//从session中取得用户信息
		YffManDef user = CommFunc.getYffMan();
		String sql = "select id,describe from orgpara";
		//权限范围: 具有用户管理权限的可以查询所有
		if(user.getRese2_flag() == 0){
			//权限范围: 非0所内管辖终端, 0所有终端
			if(user.getRank() != 0){
			flag = "1";
			sql += " where id=" + user.getOrgId();
			}
		}
		result = DBOper.getValueText(sql);
		return SDDef.SUCCESS;
	}
//-------------------------------------------------------------------
	/**农排冲正时 获取最近一条缴费记录信息*/
	public String lastPayInfoNP(){
		if(result == null || result.isEmpty()) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String area_id	= jsonObj.getString("area_id");
		String farmer_id	= jsonObj.getString("farmer_id");
		
		JDBCDao j_dao			= new JDBCDao();
		StringBuffer ret_buf	= new StringBuffer();
		StringBuffer sbfSql		= new StringBuffer();
		String yyyy				= new SimpleDateFormat("yyyy").format(new Date());
		int year				= Integer.parseInt(yyyy);
		int all_num				= 0;	
		
		//查找最新缴费记录（从近两年的表中查）
		for(int i = 0; i < 2; i++, year--){
			String tablename = YDTable.TABLEDATA_NYFF + year;
			
			sbfSql.append("select top 1 n.op_date, n.op_type, n.pay_money, n.othjs_money, n.zb_money, n.all_money, n.buy_times, n.wasteno, n.pay_type, n.op_man, n.op_time");
			sbfSql.append(" from ydparaben.dbo.farmerpay_state as f left join " + tablename + " as n on");
			sbfSql.append(" n.area_id = f.area_id and n.farmer_id = f.farmer_id and n.op_date >= f.kh_date and n.op_date >= f.xh_date ");
			sbfSql.append(" where n.area_id = " + area_id );
			sbfSql.append(" and n.farmer_id = " + farmer_id);
			sbfSql.append(" and n.op_date > 0 and n.visible_flag = 1 and ");
			sbfSql.append(" (n.op_type="+ SDDef.YFF_OPTYPE_ADDRES + " or n.op_type=" +SDDef.YFF_OPTYPE_PAY + " or n.op_type=" + SDDef.YFF_OPTYPE_RESTART + " or n.op_type=" + SDDef.YFF_OPTYPE_CHANGEMETER + " or n.op_type=" + SDDef.YFF_OPTYPE_REPAIR + " or n.op_type=" + SDDef.YFF_OPTYPE_JSBC + " ) ");
			sbfSql.append(" order by n.wasteno desc");
			
			List<Map<String, Object>>	list = j_dao.result(sbfSql.toString());
			sbfSql.delete(0, sbfSql.length());
			if(list == null || list.size() == 0){
				continue;
			}
			
			all_num += list.size();
			
			Map<String,Object> map = list.get(0);
			
			ret_buf.append("{op_date:" 		+ map.get("op_date") 		+ ",");
			ret_buf.append("op_time:" 		+ map.get("op_time") 		+ ",");
			ret_buf.append("last_wastno:\"" + map.get("wasteno") 		+ "\",");
			ret_buf.append("pay_money:" 	+ map.get("pay_money") 		+ ",");
			ret_buf.append("othjs_money:" 	+ map.get("othjs_money") 	+ ",");
			ret_buf.append("zb_money:" 		+ map.get("zb_money") 		+ ",");
			ret_buf.append("all_money:" 	+ map.get("all_money") 		+ "}");
			
			if(all_num >= 1)break;
		}
		
		if(all_num == 0) {	//记录为空
			result = "";
		}
		else {
			result = ret_buf.toString();
		}
		return SDDef.SUCCESS;
	}
	
	@JSON(serialize=false)
	@SuppressWarnings("unchecked")
	public String initOperman(){
		String hql = "";	
		hql = "select a.id,a.describe from " + tableName + " as a where 1=1 ";			
		
		HibDao hib_dao = new HibDao();
		StringBuffer ret_buf = new StringBuffer();
		ret_buf.append(SDDef.JSONROWSTITLE);
		List list = hib_dao.loadAll(hql);
		if(list.size() == 0){
			result = "";
			return SDDef.SUCCESS;
		}
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Object[] obj = (Object[])it.next();
			ret_buf.append(SDDef.JSONLBRACES);
			ret_buf.append(SDDef.JSONQUOT + "value" + SDDef.JSONQACQ + obj[0]                       + SDDef.JSONCCM); 
			ret_buf.append(SDDef.JSONQUOT + "text" 	+ SDDef.JSONQACQ + CommFunc.CheckString(obj[1]) + SDDef.JSONQRBCM);
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		
		result=ret_buf.toString();
		
		return SDDef.SUCCESS;
	}
	
	
	
	
	/**
	 * 根据外接表类型和计费方式获取外接表的卡类型
	 * */
	@JSON(serialize=false)
	public String getExtCardType(){
		if(result.isEmpty() || result == null){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		//计费方式
		String feectrl_type = result.split(SDDef.SPLITCOMA)[0];
		//表类型
		String fact_id = result.split(SDDef.SPLITCOMA)[1];
		
		String sql = "select cardtype, describe, id from ydparaben.dbo.ocardtype_para where feectrl_type = " + feectrl_type + " and fact_id = " + fact_id; 
		JDBCDao j_dao			= new JDBCDao();
		List<Map<String, Object>>	list = j_dao.result(sql);
		//if(list.isEmpty()){				//测试时注意是否为空
		if(list == null || list.size() == 0){	
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		}
		JSONObject json = new JSONObject();
		JSONArray  j_array = new JSONArray();
		
		Iterator it = list.iterator();
		while(it.hasNext()){
			Map<String,Object> map = (Map<String,Object>)it.next();
			json.put("id", CommFunc.CheckString(map.get("id")));
			json.put("value", CommFunc.CheckString(map.get("cardtype")));
			json.put("text", CommFunc.CheckString(map.get("describe")));
			j_array.add(json);
			json.clear();
		}
		result = j_array.toString();
		return SDDef.SUCCESS;
	}
	
	
//	public String loadRtuPara(){
//		if(tableName == null || tableName.isEmpty()){
//			result = SDDef.EMPTY;
//			return SDDef.SUCCESS;
//		}
//		
//		String sql = "select id, describe from ydparaben.dbo." + tableName;
//		JDBCDao j_dao = new JDBCDao();
//		ResultSet rs = null;
//		JSONArray json_array = new JSONArray();
//		JSONObject jsonObj = new JSONObject();
//		
//		try {
//			//执行sql语句
//			rs = j_dao.executeQuery(sql);
//			//生成json字符串
//			while(rs.next()){
//				jsonObj.put("value", rs.getString(1));
//				jsonObj.put("text", CommBase.CheckString(rs.getString(2)));
//				json_array.add(jsonObj);
//				jsonObj.clear();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return SDDef.EMPTY;
//		}finally{
//			j_dao.closeRs(rs);
//		}
//		result = json_array.toString();
//		return SDDef.SUCCESS;
//	}
	
	/**
	 * 20150306 zp 获取rtumodel表中信息 
	 * */
	public String loadRtuModel(){
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		int typemin = 0;
		int typemax = 0;
		if(result.equals("0")){
			typemin = 0;
			typemax = 50;
		}
		else if(result.equals("1") || result.equals("2")){
			typemin = 51;
			typemax = 100;
		}
		else if(result.equals("3")){
			typemin = 101;
			typemax = 150;
		}
		
		String sql = "select id, describe from ydparaben.dbo.rtumodel where id >= " + typemin + " and id <= " + typemax;
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		JSONArray json_array = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		
		try {
			//执行sql语句
			rs = j_dao.executeQuery(sql);
			//生成json字符串
			while(rs.next()){
				jsonObj.put("value", rs.getString(1));
				jsonObj.put("text", CommBase.CheckString(rs.getString(2)));
				json_array.add(jsonObj);
				jsonObj.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return SDDef.EMPTY;
		}finally{
			j_dao.closeRs(rs);
		}
		result = json_array.toString();
		return SDDef.SUCCESS;
	}
	
	/**
	 * 20150306 zp 根据表名获取id和text值，此处用来获取终端参数下拉列表
	 * */
	public String loadDBDesc(){
		if(tableName == null || tableName.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		String sql = "select id, describe from ydparaben.dbo." + tableName;
		if(result != null && !result.isEmpty()){
			sql += " where id = " + result;
		}
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		JSONArray json_array = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		
		try {
			//执行sql语句
			rs = j_dao.executeQuery(sql);
			//生成json字符串
			while(rs.next()){
				jsonObj.put("value", rs.getString(1));
				jsonObj.put("text", CommBase.CheckString(rs.getString(2)));
				json_array.add(jsonObj);
				jsonObj.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return SDDef.EMPTY;
		}finally{
			j_dao.closeRs(rs);
		}
		result = json_array.toString();
		return SDDef.SUCCESS;
	}
	
	/**获取所有低压集抄终端 */
	public String loadRtuDY(){
		YffManDef user = CommFunc.getYffMan();
		String hql = "";
		if(user.getOrgId() != null){
			hql = "select r.id,r.describe from " + YDTable.TABLECLASS_RTUPARA + " r,ConsPara c,"+ YDTable.TABLECLASS_Userrankbound +" as u where u.consId = c.id and u.userId = " + user.getId()+" and r.consId = c.id and  r.appType = " + SDDef.APPTYPE_JC;

			//权限范围: 非0所内管辖终端, 0所有终端
			if(user.getRank() != 0){
				hql += " and  c.orgId=" + user.getOrgId();
			}			
		}else{
			hql = "select r.id,r.describe from " + YDTable.TABLECLASS_RTUPARA + " r,ConsPara c where r.consId = c.id and  r.appType = " + SDDef.APPTYPE_JC;

			//权限范围: 非0所内管辖终端, 0所有终端
			if(user.getRank() != 0){
				hql += " and  c.orgId=" + user.getOrgId();
			}				
		}

		
		HibDao hib_dao = new HibDao();
		List list = hib_dao.loadAll(hql);
	
		if(list.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		Iterator<?> it = list.iterator();
 		JSONObject json = new JSONObject();
		JSONArray j_array = new JSONArray(); 
		
		while(it.hasNext()){
			Object[] obj = (Object[])it.next();
			json.put("id", obj[0]);
			json.put("desc", obj[1]);
			j_array.add(json);
			json.clear();
		}
		result = j_array.toString();
		return SDDef.SUCCESS;
	}
	
	/*get set 方法*/
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getAppType() {
		return appType;
	}

	public String getYdInfoNo() {
		return ydInfoNo;
	}

	public void setYdInfoNo(String ydInfoNo) {
		this.ydInfoNo = ydInfoNo;
	}

	public String getRepairData() {
		return repairData;
	}

	public void setRepairData(String repairData) {
		this.repairData = repairData;
	}
	
}
