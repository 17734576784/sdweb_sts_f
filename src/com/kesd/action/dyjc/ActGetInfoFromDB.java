package com.kesd.action.dyjc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.YDTable;
import com.kesd.dbpara.YffRatePara;
import com.kesd.util.I18N;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;

public class ActGetInfoFromDB extends ActionSupport{

	/**
	 * 直接从数据库中查询信息
	 */
	private static final long serialVersionUID = 111626869446475243L;
	
	private String result;

	public String loadUserInformation() throws SQLException, IllegalAccessException{
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JSONObject param = JSONObject.fromObject(result);
		String rtuId = param.getString("rtuId");
		String mpId	 = param.getString("mpId");
		
		JSONObject jsonData = new JSONObject();		
		String sql = "select sts.encrypt_type,msts.sgc,sts.key_type,sts.dkga,sts.vk1,org.r_org_no,org.addr,org.telno,msts.ken,msts.ti,msts.regno, res.cons_no,res.id as res_id, res.describe, res.phone, res.address, mpstate.cus_state, meter.comm_addr, meter.factory, mppay.yffmeter_type, cons.tel_no3," +
			"mppay.cacl_type, mppay.feectrl_type, mppay.pay_type, mppay.feeproj_id,mppay.key_version, mppay.tz_val,mpstate.jy_money, mpstate.reserve1 as seqNo, mpstate.reserve2 as keyNo, mpstate.buy_times, " +	
			"mppay.prot_st as friend_s, mppay.prot_ed as friend_e, mp.reserve1 as friendDate,mp.ct_ratio as ct,mp.pt_ratio as pt,mp.rp as fmaximum_power,mp.mi as gmaximum_power,mp.bd_factor as power_start,mp.v_factor as power_end,mp.ct_denominator as power_date " +
			"from ydparaben.dbo.orgpara as org,ydparaben.dbo.conspara as cons, ydparaben.dbo.rtupara as rtu, ydparaben.dbo.mppara as mp, ydparaben.dbo.residentpara as res, " +
			"ydparaben.dbo.meterpara as meter, ydparaben.dbo.mppay_para as mppay, ydparaben.dbo.mppay_state as mpstate , ydparaben.dbo.org_stspara as sts, ydparaben.dbo.meter_stspara as msts  " +  
			"where  sts.org_id = org.id  and  cons.org_id = org.id and  cons.id = rtu.cons_id and rtu.app_type = " + SDDef.APPTYPE_JC + " and rtu.id = mp.rtu_id and rtu.id = res.rtu_id and rtu.id = meter.rtu_id and " +
			" msts.rtu_id = rtu.id and msts.mp_id = mp.id and  rtu.id = mppay.rtu_id and mp.id = meter.mp_id and mp.id = mppay.mp_id and res.id = meter.resident_id and rtu.id = mpstate.rtu_id and mp.id = mpstate.mp_id " + 
			" and rtu.id = " + rtuId + " and mp.id = " + mpId;
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		rs = j_dao.executeQuery(sql);
		while(rs.next()){
			jsonData.put("rtu_id", rtuId);		//终端编号
			jsonData.put("mp_id", mpId);		//测点编号
			jsonData.put("res_id", CommBase.CheckString(rs.getString("res_id")));						//存库时使用	
			jsonData.put("userno", CommBase.CheckString(rs.getString("cons_no")));						//客户编号
			jsonData.put("username", CommBase.CheckString(rs.getString("describe")));					//客户名称
			jsonData.put("cus_state_id", CommBase.CheckString(rs.getString("cus_state")));				//客户状态值
			jsonData.put("cus_state", Rd.getDict(Dict.DICTITEM_YFFCUSSTATE, rs.getByte("cus_state")));	//客户状态描述
			
			jsonData.put("r_org_no", CommBase.CheckString(rs.getString("r_org_no")));	//SGC对应供电所档案表的供电单位编号（orgpara.r_org_no)
			jsonData.put("addr", CommBase.CheckString(rs.getString("addr")));							//服务地址
			jsonData.put("telno", CommBase.CheckString(rs.getString("telno")));							//服务电话
			jsonData.put("tel", CommBase.CheckString(rs.getString("phone")));							//联系电话
			jsonData.put("useraddr", CommBase.CheckString(rs.getString("address")));					//客户地址
			jsonData.put("commaddr", CommBase.CheckString(rs.getString("comm_addr")));					//电表编号
			jsonData.put("yffmeter_type", CommBase.CheckString(rs.getString("yffmeter_type")));			//预付费表类型			
			jsonData.put("yffmeter_type_desc", Rd.getDict(Dict.DICTITEM_YFFMETERTYPE, rs.getByte("yffmeter_type")));//预付费表类型描述
			jsonData.put("factory", Rd.getDict(Dict.DICTITEM_FACTORY, rs.getByte("factory")));			//生产厂家
			jsonData.put("utilityid", CommBase.CheckString(rs.getString("tel_no3")));					//供电单位编号
			jsonData.put("cacl_type", CommBase.CheckString(rs.getString("cacl_type")));					//计费方式
			jsonData.put("cacl_type_desc", Rd.getDict(Dict.DICTITEM_FEETYPE, rs.getByte("cacl_type")));//计费方式描述
			
			jsonData.put("feectrl_type", CommBase.CheckString(rs.getString("feectrl_type")));			//费控方式
			jsonData.put("feectrl_type_desc", Rd.getDict(Dict.DICTITEM_PREPAYTYPE, rs.getByte("feectrl_type")));//费控方式描述
			jsonData.put("pay_type", CommBase.CheckString(rs.getString("pay_type")));					//缴费方式
			jsonData.put("pay_type_desc", Rd.getDict(Dict.DICTITEM_PAYTYPE, rs.getByte("pay_type")));	//缴费方式描述
			jsonData.put("feeproj_id", CommBase.CheckString(rs.getString("feeproj_id")));				//费率方案id
			
			Object obj = Rd.getRecord(YDTable.TABLECLASS_YFFRATEPARA, rs.getShort("feeproj_id"));
			if(obj != null){
				jsonData.put("feeproj_desc", ((YffRatePara)obj).getDescribe());							//费率方案描述
				jsonData.put("feeproj_detail", getDJLX((YffRatePara)obj));														//费率方案详细信息--Rd.getYffRateDesc((YffRatePara)obj)再定！！
				jsonData.put("feeType", ((YffRatePara)obj).getFeeType() );								//费率方案
				jsonData.put("money_limit", ((YffRatePara)obj).getMoneyLimit() == null? 0.0 : ((YffRatePara)obj).getMoneyLimit());						//囤积额度
				jsonData.put("activation_date",((YffRatePara)obj).getActivdate());					//设备激活日期
							
				jsonData.put("rateid",((YffRatePara)obj).getRateId());						//设备激活日期
				
			}			
			jsonData.put("tzval", CommBase.CheckString(rs.getString("tz_val")));						//透支金额
			
			jsonData.put("seqNo", CommBase.CheckString(rs.getString("seqNo")));			//seqNo				
			jsonData.put("keyNo", CommBase.CheckString(rs.getString("keyNo")));			//keyNo
			jsonData.put("buy_times", CommBase.CheckString(rs.getString("buy_times")).equals("")? 0:CommBase.CheckString(rs.getString("buy_times")));	//购电次数
			
			jsonData.put("friend_s", CommBase.CheckString(rs.getString("friend_s")));	//保电开始时间
			jsonData.put("friend_e", CommBase.CheckString(rs.getString("friend_e")));	//保电结束时间
			jsonData.put("friendDate", CommBase.CheckString(rs.getString("friendDate")));//每周保电日期
			
			jsonData.put("fmaximum_power", CommBase.CheckString(rs.getString("fmaximum_power")));	//峰最大功率
			jsonData.put("gmaximum_power", CommBase.CheckString(rs.getString("gmaximum_power")));	//谷最大功率
			jsonData.put("power_start", CommBase.CheckString(rs.getString("power_start")));			//最大功率开始时间
			jsonData.put("power_end", CommBase.CheckString(rs.getString("power_end")));				//最大功率结束时间	
			jsonData.put("power_date", CommBase.CheckString(rs.getString("power_date")));			//最大功率激活日期	
			
			jsonData.put("key_version", CommBase.CheckString(rs.getString("key_version")));			//KRN 对应mppay_para中的密钥版本
			
			jsonData.put("key_type", CommBase.CheckString(rs.getString("key_type")));			//KRN 对应mppay_para中的密钥版本
			jsonData.put("dkga", CommBase.CheckString(rs.getString("dkga")));			//KRN 对应mppay_para中的密钥版本
			jsonData.put("vk1", CommBase.CheckString(rs.getString("vk1")));			//KRN 对应mppay_para中的密钥版本
			jsonData.put("ken", CommBase.CheckString(rs.getString("ken")));			//meter_stspara ken字段
			
			jsonData.put("ct", CommBase.CheckString(rs.getString("ct")));						//ct mppara字段
			jsonData.put("pt", CommBase.CheckString(rs.getString("pt")));						//pt mppara字段
			jsonData.put("jy_money", CommBase.CheckString(rs.getString("jy_money")));			//jy_money mppay_state字段
			jsonData.put("ti", 		 CommBase.CheckString(rs.getString("ti")));			//ti 	meter_stspara字段
			jsonData.put("regno", CommBase.CheckString(rs.getString("regno")));			//regno meter_stspara字段
			jsonData.put("sgc", CommBase.CheckString(rs.getString("sgc")));
			
			jsonData.put("encrypt_type", CommBase.CheckString(rs.getString("encrypt_type")));	//org_stspara 软硬件 加密类型
			
			//jsonData.putAll(getJYffPrintTemplate(rtuId,mpId));
			
		}
		result = jsonData.toString();
		return SDDef.SUCCESS;
	}
	
	private  Map<String,Object> getJYffPrintTemplate(String rtuId,String mpId) throws SQLException, IllegalAccessException{
		
		Map<String,Object> printData = new HashMap<String,Object>();
		String year 	= CommFunc.nowYMD().substring(0, 4);//年份
		
		String sql = "select top 1 op_man,op_date,op_time,wasteno,pay_money,buy_dl " +
					  " from YDDataBen.dbo.JYff"+year+
					  " WHERE rtu_id = "+rtuId+" and mp_id = "+mpId+" and op_type = "+1+" ORDER BY wasteno DESC";
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		rs = j_dao.executeQuery(sql);
		
		while(rs.next()){

			printData.put("op_man", rs.getObject("op_man"));
			printData.put("op_time", CommFunc.FormatToYMD(rs.getObject("op_date"), "day") +" "+ CommFunc.FormatToHMS((rs.getObject("op_time")),1));
			printData.put("wasteno", rs.getObject("wasteno"));
			printData.put("pay_money", rs.getObject("pay_money"));
			printData.put("buy_dl", rs.getObject("buy_dl"));
			
		}
		
		return printData;
	}
	
	private static String getDJLX(YffRatePara yffrate) throws IllegalAccessException{
		
		if(yffrate == null)return "";
		
		String strDesc = null;
		
		switch(yffrate.getFeeType()){
		case SDDef.YFF_FEETYPE_DFL: 
			//"总:%.3f 
			strDesc = String.valueOf(yffrate.getRatedZ());
			break;
		case SDDef.YFF_FEETYPE_FFL: 
			//"尖:%.3f 峰:%.3f 平:%.3f 谷:%.3f "
			String RatefF = CommFunc.CheckString(((yffrate.getRatefF()== null)?0.0: yffrate.getRatefF()));
			String RatefG = CommFunc.CheckString(((yffrate.getRatefG()== null)?0.0: yffrate.getRatefG()));
			strDesc = "Peak/Off:" +  RatefF + "/"  + RatefG;
			break;
		case SDDef.YFF_FEETYPE_JTFL:	//阶梯费率1:，阶梯费率2:，阶梯费率3:，梯度1:，梯度2:
			switch(yffrate.getRatejNum()) {
				case 1:
				case 2:
					strDesc = "0~" + CheckNull(yffrate.getRatejTd1())+ "|" + CheckNull(yffrate.getRatejR1()) + "/" + CheckNull(yffrate.getRatejR2()); 
					break;
				case 3:
					strDesc = "0~" + CheckNull(yffrate.getRatejTd1())+ "~" + CheckNull(yffrate.getRatejTd2()) + "|" + CheckNull(yffrate.getRatejR1()) + "/" + CheckNull(yffrate.getRatejR2()) + "/" + CheckNull(yffrate.getRatejR3());
					break;
				case 4:
					strDesc = "0~" + CheckNull(yffrate.getRatejTd1())+ "~" + CheckNull(yffrate.getRatejTd2()) +  "~" + CheckNull(yffrate.getRatejTd3()) + "~" +"|" 
							+ CheckNull(yffrate.getRatejR1()) + "/" + CheckNull(yffrate.getRatejR2()) + "/" + CheckNull(yffrate.getRatejR3()) + "/" + CheckNull(yffrate.getRatejR4());
					break;
				case 5:
					strDesc = "0~" + CheckNull(yffrate.getRatejTd1())+ "~" + CheckNull(yffrate.getRatejTd2()) +  "~" + CheckNull(yffrate.getRatejTd3()) + "~" + CheckNull(yffrate.getRatejTd4()) +  "|" 
							+ CheckNull(yffrate.getRatejR1()) + "/" + CheckNull(yffrate.getRatejR2()) + "/" + CheckNull(yffrate.getRatejR3()) + "/" + CheckNull(yffrate.getRatejR4()) + "/" + CheckNull(yffrate.getRatejR5());
					break;
				case 6:
					strDesc = "0~" + CheckNull(yffrate.getRatejTd1())+ "~" + CheckNull(yffrate.getRatejTd2()) +  "~" + CheckNull(yffrate.getRatejTd3()) + "~" + CheckNull(yffrate.getRatejTd4()) +  "~" + CheckNull(yffrate.getRatejTd5()) + "|" 
							+ CheckNull(yffrate.getRatejR1()) + "/" + CheckNull(yffrate.getRatejR2()) + "/" + CheckNull(yffrate.getRatejR3()) + "/" + CheckNull(yffrate.getRatejR4()) + "/" + CheckNull(yffrate.getRatejR5()) + "/" + CheckNull(yffrate.getRatejR6());
					break;
				case 7:
					strDesc = "0~" + CheckNull(yffrate.getRatejTd1())+ "~" + CheckNull(yffrate.getRatejTd2()) +  "~" + CheckNull(yffrate.getRatejTd3()) + "~" + CheckNull(yffrate.getRatejTd4()) +  "~" + CheckNull(yffrate.getRatejTd5()) + "~" + CheckNull(yffrate.getRatejTd6()) + "|" 
								+ CheckNull(yffrate.getRatejR1()) + "/" + CheckNull(yffrate.getRatejR2()) + "/" + CheckNull(yffrate.getRatejR3()) + "/" + CheckNull(yffrate.getRatejR4()) + "/" + CheckNull(yffrate.getRatejR5()) + "/" + CheckNull(yffrate.getRatejR6())+ "/" + CheckNull(yffrate.getRatejR7());
					break;
			}
			break;
		}
		
		if(strDesc == null){
			strDesc=I18N.getText("invalid")+I18N.getText("flfa");	
		}
		
		return strDesc;
	}
	
	public static String CheckNull(Object obj){		
		return CommFunc.CheckString(((obj== null)?0.0: obj));
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
