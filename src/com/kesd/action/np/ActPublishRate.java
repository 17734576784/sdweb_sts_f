package com.kesd.action.np;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import net.sf.json.JSONObject;

import org.apache.struts2.json.annotations.JSON;

import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;

import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;

import com.opensymphony.xwork2.ActionSupport;
public class ActPublishRate extends ActionSupport {
	private static final long serialVersionUID = 171468819168267635L;
	private String result;
	private String field;
	
	
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>NAME        : getsList	
	* <p>DESCRIPTION : 获取记录列表--发行电费--查询结果
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	*-----------------------------------------------------------*/
	@JSON(serialize = false)
	public String getsList(){
		List<Map<String,Object>> list = null;
		List<Map<String,Object>> list1= null;
		StringBuffer ret_buf=new StringBuffer();
		if(null==result||result.isEmpty()){
			result=SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		JDBCDao j_dao=new JDBCDao();
		StringBuffer sql_buf=new StringBuffer();
		StringBuffer sql_buf1=new StringBuffer();
		sql_buf.append("select ").
		        append("b.rtu_id, b.mp_id, a.describe as rtu_desc,c.cons_no as cons_no,d.meter_id as meter_no,c.describe as cons_desc, cacl_type,feectrl_type, pay_type, yffmeter_type, feeproj_id,yffalarm_id, fee_begindate, prot_st, prot_ed, ngloprot_flag, key_version, cryplink_id, pay_add, tz_val,power_relaf, power_rela1, power_rela2, power_relask1, power_relask2, fee_chgf,fee_chgid, fee_chgdate,fee_chgtime,jt_cycle_md, cb_cycle_type, cb_dayhour, js_day, fxdf_flag, fxdf_begindate, local_maincalcf ").
		        append("from ").
		        append("ydparaben.dbo.rtupara as a,ydparaben.dbo.mppay_para as b,ydparaben.dbo.residentpara as c,ydparaben.dbo.meterpara as d,ydparaben.dbo.mppara as e, ydparaben.dbo.conspara as f ").
		        append("where ").
		        append("a.app_type= "+SDDef.APPTYPE_JC +" ").
		        append("and a.cons_id=f.id and a.id=b.rtu_id and a.id=b.rtu_id and a.id=c.rtu_id and a.id=d.rtu_id and a.id = e.rtu_id and b.mp_id=d.mp_id and e.id = d.mp_id and c.id=d.resident_id and e.bak_flag=0  ");
		
		sql_buf1.append("select ").
		         append("b.rtu_id, b.mp_id,b.cus_state,b.op_type,b.op_date,b.op_time,b.pay_money,b.othjs_money,b.zb_money,b.all_money,b.shutdown_val,b.shutdown_val2,b.jsbd_zyz,b.jsbd_zyj,b.jsbd_zyf,b.jsbd_zyp,b.jsbd_zyg,b.jsbd1_zyz,b.jsbd1_zyj,b.jsbd1_zyf,b.jsbd1_zyp,b.jsbd1_zyg,b.jsbd2_zyz,b.jsbd2_zyj,b.jsbd2_zyf,b.jsbd2_zyp,b.jsbd2_zyg,b.jsbd_ymd,b.buy_times,b.calc_mdhmi,b.calc_bdymd,b.calc_zyz,b.calc_zyj,b.calc_zyf,b.calc_zyp,b.calc_zyg,b.calc1_zyz,b.calc1_zyj,b.calc1_zyf,b.calc1_zyp,b.calc1_zyg,b.calc2_zyz,b.calc2_zyj,b.calc2_zyf,b.calc2_zyp,b.calc2_zyg,b.now_remain,b.now_remain2,b.bj_bd,b.tz_bd,b.cs_al1_state,b.cs_al2_state,b.cs_fhz_state,b.al1_mdhmi,b.al2_mdhmi,b.fhz_mdhmi,b.fz_zyz,b.fz1_zyz,b.fz2_zyz,b.yc_flag1,b.yc_flag2,b.yc_flag3,b.hb_date,b.hb_time,b.kh_date,b.xh_date,b.total_gdz, b.jt_total_zbdl,b.jt_total_dl,b.jt_reset_ymd,b.jt_reset_mdhmi,b.fxdf_iall_money,b.fxdf_iall_money2,b.fxdf_remain,b.fxdf_remain2,b.fxdf_ym,b.fxdf_data_ymd,b.fxdf_calc_mdhmi,b.js_bc_ymd  ").
		         append("from ").
		         append("ydparaben.dbo.rtupara as a,ydparaben.dbo.mppay_state as b,ydparaben.dbo.residentpara as c, ydparaben.dbo.meterpara as d,ydparaben.dbo.mppara as e, ydparaben.dbo.conspara as f ").
		         append("where ").
		         append("a.app_type= "+SDDef.APPTYPE_JC +" ").
		         append("and a.cons_id=f.id and a.id=b.rtu_id and a.id=c.rtu_id and a.id=d.rtu_id and b.mp_id=d.mp_id and c.id=d.resident_id and d.rtu_id=e.rtu_id and d.mp_id=e.id and e.bak_flag=0  ");
				
		JSONObject jsonObj=JSONObject.fromObject(result);
		//String orgId = jsonObj.getString("orgId");
		String rtuId = jsonObj.getString("rtuId");
		Integer sType= jsonObj.getInt("searchType");
		String sText = jsonObj.getString("searchContent");
		
		if(!("-1").equals(rtuId)){
			sql_buf.append( "and a.id = "+rtuId + " ");
			sql_buf1.append("and a.id = "+rtuId + " ");
		}
//		if(!("-1").equals(orgId)){
//			sql_buf.append ("and f.org_id = " +orgId + " ");
//			sql_buf1.append("and f.org_id = " +orgId + " ");
//		}
		if(null!= sText && !("").equals(sText) && !sText.isEmpty()){
			switch(sType){
			case 1://终端名称
				sql_buf.append(" and  a.describe like '%").
				        append(sText).
				        append("%'");
				sql_buf1.append(" and a.describe like '%").
				        append(sText).
				        append("%'");
				break;
			
			case 2://用户户号
				sql_buf.append(" and c.cons_no like '%").
				        append(sText).
				        append("%'");
				sql_buf1.append(" and c.cons_no like '%").
				        append(sText).
				        append("%'");
				break;
				
			case 3://用户名称
				sql_buf.append(" and c.describe like '%").
				        append(sText).
				        append("%'");
				sql_buf1.append(" and c.describe like '%").
				        append(sText).
				        append("%'");
				break;
				
			case 4://联系电话
				sql_buf.append(" and c.phone like '%").
				        append(sText).
				        append("%'");
				sql_buf1.append(" and c.phone like '%").
				        append(sText).
				        append("%'");
				break;
				
			case 5://表号
				sql_buf.append(" and d.meter_id like '%").
				        append(sText).
				        append("%'");
				sql_buf1.append(" and d.meter_id like '%").
				        append(sText).
				        append("%'");
				break;
			}
		}
		sql_buf.append(" order by b.rtu_id, b.mp_id ;");
		sql_buf1.append(" order by b.rtu_id, b.mp_id ;");
		list=j_dao.result(sql_buf.toString());
		list1=j_dao.result(sql_buf1.toString());
		if(list.size()==0){
			result=SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		ret_buf.append(SDDef.JSONROWSTITLE);
		for(int index =0;index<list.size(); index++){
			Map<String,Object> map=list.get(index);
			Map<String,Object> map1=list1.get(index);
			
			ret_buf.append(SDDef.JSONLBRACES);
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + map.get("rtu_id") + "_" + map.get("mp_id") + SDDef.JSONNZDATA);
			ret_buf.append(SDDef.JSONQUOT + (index+1) + SDDef.JSONCCM);//序号
			ret_buf.append(SDDef.JSONQUOT + "[" +map.get("rtu_id")+ "]" + CommBase.CheckString(map.get("rtu_desc"))  + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + "[" +map.get("mp_id") + "]" + CommBase.CheckString(map.get("cons_desc")) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT +  FormatToDH(map.get("cb_dayhour"))  	+ SDDef.JSONCCM);//抄表日
			ret_buf.append(SDDef.JSONQUOT +  numToFlag(map.get("local_maincalcf")) 	+ SDDef.JSONCCM);//主站算费标志
			ret_buf.append(SDDef.JSONQUOT +  numToFlag(map.get("fxdf_flag")) 		+ SDDef.JSONCCM);//发行电费标志
			ret_buf.append(SDDef.JSONQUOT +  FormatToYD(map.get("jt_cycle_md")) 	+ SDDef.JSONCCM);//阶梯周期切换
			ret_buf.append(SDDef.JSONQUOT +  CommFunc.FormatToYMD(map.get("fxdf_begindate")) 	+ SDDef.JSONCCM);//发行电费起始日期
			ret_buf.append(SDDef.JSONQUOT +  FormatToYM(map1.get("fxdf_ym")) 					+ SDDef.JSONCCM);//发行电费年月
			ret_buf.append(SDDef.JSONQUOT +  CommFunc.FormatToYMD(map1.get("fxdf_data_ymd"))	+ SDDef.JSONCCM);//发行电费数据日期
			ret_buf.append(SDDef.JSONQUOT +  FormatToMD_SM(map1.get("fxdf_calc_mdhmi"))			+ SDDef.JSONCCM);//发行电费算费时间
			ret_buf.append(SDDef.JSONQUOT +  CommFunc.FormatToYMD(map1.get("jt_reset_ymd"))		+ SDDef.JSONCCM);//阶梯切换日期
			ret_buf.append(SDDef.JSONQUOT +  FormatToMD_SM(map1.get("jt_reset_mdhmi"))			+ SDDef.JSONCCM);//阶梯切换执行时间
			ret_buf.append(SDDef.JSONQUOT +  CommFunc.FormatToYMD(map1.get("js_bc_ymd"))		+ SDDef.JSONCCM);//结算补差日期
			
			ret_buf.append(SDDef.JSONQUOT +  map.get("cons_no")+ SDDef.JSONCCM);//居民户号
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);	
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();	
		return SDDef.SUCCESS;
	}
	
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>NAME        : getsList	
	* <p>DESCRIPTION : 获取记录列表--发行电费-弹出窗口-居民发行电费。
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	*-----------------------------------------------------------*/
	@JSON(serialize = false)
	public String getFxMoney(){
		List<Map<String,Object>> list = null;
		StringBuffer ret_buf=new StringBuffer();
		if(null==result||result.isEmpty()){
			result=SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JSONObject 	json 	= JSONObject.fromObject(result);
		String 		rtuId 	= json.getString("rtu_id");
		String 		mpId 	= json.getString("mp_id");
		String 		cbday 	= json.getString("cbday");//抄表日 5日=05,10日=10,未配置为0；
		String 		khDate 	= json.getString("khDate");//开户日期20120506;
		
		if(("-1").equals(rtuId) || null == rtuId || ("").equals(rtuId) || rtuId.isEmpty() || cbday.isEmpty()){
			result=SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JDBCDao j_dao = new JDBCDao();
		String 	sql   = getFxJtSql(CommBase.strtoi(cbday),rtuId,mpId,1,khDate);
		
		list = j_dao.result(sql);
		
		if(list.size() == 0){
			result=SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		ret_buf.append(SDDef.JSONROWSTITLE);
		
		for(int index = 0;index < list.size(); index++ ){
			Map<String,Object> map = list.get(index);

			ret_buf.append(SDDef.JSONLBRACES);
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + (index+1) + SDDef.JSONNZDATA);
			ret_buf.append(SDDef.JSONQUOT + (index+1) + SDDef.JSONCCM);//序号
			ret_buf.append(SDDef.JSONQUOT +  CommFunc.FormatToYM(map.get("fxdf_ym")) + SDDef.JSONCCM);//电费日期
			ret_buf.append(SDDef.JSONQUOT +  CommFunc.FormatToMDHMin(map.get("fxdf_mdhmi")) + SDDef.JSONCCM);//发行日期
			ret_buf.append(SDDef.JSONQUOT +  FormatToYM(map.get("fxdf_lastym")) + SDDef.JSONCCM);//上次电费年月
			ret_buf.append(SDDef.JSONQUOT +  CommFunc.FormatToYMD(map.get("calc_bdymd")) + SDDef.JSONCCM);//算费表底日期
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckString(map.get("calc_zyz")) + "-" + CommBase.CheckString(map.get("calc1_zyz")) + "-" + CommBase.CheckString(map.get("calc2_zyz")) + SDDef.JSONCCM);//算费总表底
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckString(map.get("calc_zyj")) + "-" + CommBase.CheckString(map.get("calc1_zyj")) + "-" + CommBase.CheckString(map.get("calc2_zyj")) + SDDef.JSONCCM);//算费尖表底
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckString(map.get("calc_zyf")) + "-" + CommBase.CheckString(map.get("calc1_zyf")) + "-" + CommBase.CheckString(map.get("calc2_zyf")) + SDDef.JSONCCM);//算费峰表底
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckString(map.get("calc_zyp")) + "-" + CommBase.CheckString(map.get("calc1_zyp")) + "-" + CommBase.CheckString(map.get("calc2_zyp")) + SDDef.JSONCCM);//算费平表底
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckString(map.get("calc_zyg")) + "-" + CommBase.CheckString(map.get("calc1_zyg")) + "-" + CommBase.CheckString(map.get("calc2_zyg")) + SDDef.JSONCCM);//算费谷表底
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckNum(map.get("now_remain")) + SDDef.JSONCCM);//电费余额
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckNum(map.get("df_money")) + SDDef.JSONCCM);//电费金额
			ret_buf.append(SDDef.JSONQUOT +  numToFlag(map.get("bc_flag")) + SDDef.JSONCCM);//补差标志
			ret_buf.append(SDDef.JSONQUOT +  numToFlag(map.get("all_monthf")) + SDDef.JSONQBRRBCM);//整月标志
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();	
		return SDDef.SUCCESS;
	}
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>NAME        : getsList	
	* <p>DESCRIPTION : 获取记录列表--发行电费-弹出窗口-重新阶梯清零。
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	*-----------------------------------------------------------*/
	@JSON(serialize = false)
	public String getJtClear(){
		List<Map<String,Object>> list = null;
		StringBuffer ret_buf=new StringBuffer();
		if(null==result||result.isEmpty()){
			result=SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JSONObject 	json 	= JSONObject.fromObject(result);
		String 		rtuId 	= json.getString("rtu_id");
		String 		mpId 	= json.getString("mp_id");
		String 		cbday 	= json.getString("cbday");//抄表日:5日=05,10日=10,未配置为0;
		String 		khDate 	= json.getString("khDate");//开户日期20120506;
		
		if(("-1").equals(rtuId) || null == rtuId || ("").equals(rtuId) || rtuId.isEmpty() || cbday.isEmpty()){
			result=SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JDBCDao j_dao = new JDBCDao();
		String 	sql   = getFxJtSql(CommBase.strtoi(cbday),rtuId,mpId,2,khDate);
		
		list = j_dao.result(sql);
		
		if(list.size()==0){
			result=SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		ret_buf.append(SDDef.JSONROWSTITLE);
		
		for(int index =0;index<list.size(); index++){
			Map<String,Object> map=list.get(index);

			ret_buf.append(SDDef.JSONLBRACES);
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + (index+1) + SDDef.JSONNZDATA);
			ret_buf.append(SDDef.JSONQUOT + (index+1) + SDDef.JSONCCM);//序号
			ret_buf.append(SDDef.JSONQUOT +  CommFunc.FormatToYM(map.get("date")) + SDDef.JSONCCM);//阶梯清零日期
			ret_buf.append(SDDef.JSONQUOT +  CommFunc.FormatToMDHMin(map.get("jtreset_mdhmi")) + SDDef.JSONCCM);//清零执行时间
			ret_buf.append(SDDef.JSONQUOT +  CommFunc.FormatToYMD(map.get("jtreset_lastymd")) + SDDef.JSONCCM);//上次清零日期
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckNum(map.get("jt_total_zbdl")) + SDDef.JSONCCM);//追补累计用电量
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckNum(map.get("jt_total_dl")) + SDDef.JSONCCM);//累计用电量
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckNum(map.get("shutdown_val")) + SDDef.JSONCCM);//断电金额
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckNum(map.get("shutdown_val2")) + SDDef.JSONQBRRBCM);//断电金额2
			
			
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();	
		return SDDef.SUCCESS;
	}
	
	
	/** 
	 * 	生成发行电费的sql 查询近5条记录。
	 *	optype =  1:发行电费；2 阶梯清零
	 *
	 *-----------------------------------------------------------*/
	@SuppressWarnings("deprecation")
	String getFxJtSql(int cbday, String rtuId, String mpId, int optype,String khDate){
		StringBuffer sql_buf = new StringBuffer();
		String sym = "";
		String eym = "";
		String tbName[] = {"",""};
		
		Date dt = new Date();
		DateFormat f = new SimpleDateFormat("yyyyMM");
		DateFormat f1 = new SimpleDateFormat("yyyy");
		
		if (dt.getDate() < cbday){//当前日期小于抄表日，结束日期取上月。
			dt.setMonth(dt.getMonth()-1);
		}
		
		eym = f.format(dt);
		tbName[0] = f1.format(dt);
		dt.setMonth(dt.getMonth()-4);//近5个月的记录
		
		Date dt1 = new Date();
		try {	//开户不足5个月。开始时间为开户日期
			dt1 = new java.text.SimpleDateFormat("yyyyMMdd").parse(khDate);
			if(dt.before(dt1))dt = dt1;
		} catch (ParseException e) {
			//e.printStackTrace();
		};
		
		sym = f.format(dt);
		tbName[1] = f1.format(dt);
		
		if( optype == 1 ){
			sql_buf.append(" (select rtu_id, mp_id, fxdf_ym, cons_no, fxdf_mdhmi, fxdf_lastym, iall_money, last_remain, last_remain2, now_remain, now_remain2, jt_total_zbdl, jt_total_dl, df_money, df_money2, shutdown_val, shutdown_val2, jsbd_ymd, jsbd_zyz, jsbd_zyj, jsbd_zyf, jsbd_zyp, jsbd_zyg, jsbd1_zyz, jsbd1_zyj, jsbd1_zyf, jsbd1_zyp, jsbd1_zyg, jsbd2_zyz, jsbd2_zyj, jsbd2_zyf, jsbd2_zyp, jsbd2_zyg, calc_bdymd, calc_zyz, calc_zyj, calc_zyf, calc_zyp, calc_zyg, calc1_zyz, calc1_zyj, calc1_zyf, calc1_zyp, calc1_zyg, calc2_zyz, calc2_zyj, calc2_zyf, calc2_zyp, calc2_zyg, update_count, all_monthf, bc_flag, bc_date, bc_time, op_man ");
			sql_buf.append(" from yddataben.dbo.MpFxMoney" + tbName[0] + " where rtu_id =" + rtuId + "  and mp_id = " + mpId);
			sql_buf.append(" and fxdf_ym >= " + sym+ " and fxdf_ym <= " + eym + ")");
			
			if(!tbName[1].equals(tbName[0])){
				sql_buf.append(" union (select rtu_id, mp_id, fxdf_ym, cons_no, fxdf_mdhmi, fxdf_lastym, iall_money, last_remain, last_remain2, now_remain, now_remain2, jt_total_zbdl, jt_total_dl, df_money, df_money2, shutdown_val, shutdown_val2, jsbd_ymd, jsbd_zyz, jsbd_zyj, jsbd_zyf, jsbd_zyp, jsbd_zyg, jsbd1_zyz, jsbd1_zyj, jsbd1_zyf, jsbd1_zyp, jsbd1_zyg, jsbd2_zyz, jsbd2_zyj, jsbd2_zyf, jsbd2_zyp, jsbd2_zyg, calc_bdymd, calc_zyz, calc_zyj, calc_zyf, calc_zyp, calc_zyg, calc1_zyz, calc1_zyj, calc1_zyf, calc1_zyp, calc1_zyg, calc2_zyz, calc2_zyj, calc2_zyf, calc2_zyp, calc2_zyg, update_count, all_monthf, bc_flag, bc_date, bc_time, op_man ");
				sql_buf.append(" from yddataben.dbo.MpFxMoney" + tbName[0] + " where rtu_id =" + rtuId + "  and mp_id = " + mpId);
				sql_buf.append(" and fxdf_ym >= " + sym+ " and fxdf_ym <= " + eym + ")");
			}
			
			sql_buf.append(" order by fxdf_ym desc");
		}
		else{
			sql_buf.append("(select rtu_id, mp_id, date, cons_no, jtreset_mdhmi, jtreset_lastymd, jt_total_zbdl, jt_total_dl,shutdown_val, shutdown_val2, jsbd_ymd, jsbd_zyz, jsbd_zyj, jsbd_zyf, jsbd_zyp, jsbd_zyg, jsbd1_zyz, jsbd1_zyj, jsbd1_zyf, jsbd1_zyp, jsbd1_zyg, jsbd2_zyz, jsbd2_zyj, jsbd2_zyf, jsbd2_zyp, jsbd2_zyg, calc_bdymd, calc_zyz, calc_zyj, calc_zyf, calc_zyp, calc_zyg, calc1_zyz, calc1_zyj, calc1_zyf, calc1_zyp, calc1_zyg, calc2_zyz, calc2_zyj, calc2_zyf, calc2_zyp, calc2_zyg, op_man ");
			sql_buf.append(" from yddataben.dbo.MpJtReset" + tbName[0] + " where rtu_id =" + rtuId + "  and mp_id = " + mpId);
			sql_buf.append(" and date >= " + sym+ " and date <= " + eym + ")");
			
			if(!tbName[1].equals(tbName[0])){
				sql_buf.append(" union (select rtu_id, mp_id, date, cons_no, jtreset_mdhmi, jtreset_lastymd, jt_total_zbdl, jt_total_dl,shutdown_val, shutdown_val2, jsbd_ymd, jsbd_zyz, jsbd_zyj, jsbd_zyf, jsbd_zyp, jsbd_zyg, jsbd1_zyz, jsbd1_zyj, jsbd1_zyf, jsbd1_zyp, jsbd1_zyg, jsbd2_zyz, jsbd2_zyj, jsbd2_zyf, jsbd2_zyp, jsbd2_zyg, calc_bdymd, calc_zyz, calc_zyj, calc_zyf, calc_zyp, calc_zyg, calc1_zyz, calc1_zyj, calc1_zyf, calc1_zyp, calc1_zyg, calc2_zyz, calc2_zyj, calc2_zyf, calc2_zyp, calc2_zyg, op_man ");
				sql_buf.append(" from yddataben.dbo.MpJtReset" + tbName[1] + " where rtu_id =" + rtuId + "  and mp_id = " + mpId);
				sql_buf.append(" and date >= " + sym+ " and date <= " + eym + ")");
			}
			sql_buf.append(" order by date desc");
		}

		return sql_buf.toString();
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
			return "未知";
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
	* <p>NAME        : FormatToYM
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
			//return "00月00日"+" "+"00时00分";
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
