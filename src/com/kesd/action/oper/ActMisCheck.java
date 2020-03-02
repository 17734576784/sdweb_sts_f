package com.kesd.action.oper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.common.WebConfig;
import com.kesd.comnt.ComntMsgMis;
import com.kesd.comnt.ComntMsgMisGS;
import com.kesd.comnt.ComntMsgMisHN;
import com.kesd.comnt.ComntMsgProc;
import com.kesd.comntpara.ComntOperMis;
import com.kesd.comntpara.ComntOperMisGS;
import com.kesd.comntpara.ComntOperMisHN;
import com.kesd.comntpara.ComntParaBase;
import com.kesd.comntpara.ComntUseropDef;
import com.kesd.dbpara.YffManDef;
import com.libweb.common.CommBase;
import com.libweb.comnt.ComntFunc;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionContext;

public class ActMisCheck {
	
	private String  userData1 			= null;		//RTU_ID
	private String  userData2 			= null;		//USEROP_ID
	private String 	result 				= null;		//SUCCESS/FAIL
	private String 	detailInfo 			= null;		//返回详细信息(JSON)
	private String  misResult			= null;		//
	
	private String 	datepara 			= null;
	private String 	misOperStr			= null;		//mis对账操作字符串,这里只传递了日期
	private String 	misType				= null;		//mis省份
	
	//与MIS系统进行对账
	public String execute(){
		StringBuffer ret = new StringBuffer();
		int count = 0;
		
		//获取当前操作员,根据操作员进行数据查询。此处还可以根据权限来进行扩展，如权限高的可以查看、操作权限低的操作员
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		YffManDef yffman = (YffManDef) session.get(SDDef.SESSION_USERNAME);
//		String czybh = yffman.getName();
		String czybh = CommFunc.getYffMan().getName();
		
		if(datepara.isEmpty() || datepara == null){
			datepara = "";
			return SDDef.SUCCESS;
		}
		
		String sdate = datepara;
		int isdate = Integer.parseInt(sdate);
		
		ret.append("{rows:[");
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		
		isdate /= 10000;
		
		String sql = "", table_name = "";
		
		//分情况，甘肃jyffMisPayNew,zyffMisPayNew
		if("GS".equals(misType)){
			table_name = "yddataben.dbo.jyffMisPayNew" + isdate;
			sql = "(select m.rtu_id, m.mp_id, res.cons_no, res.describe, m.date, m.time, m.ccbh, m.dsdwbh, m.czybh, m.jylsh, m.dzpc, m.yhbh, m.error_no, " + 
			  "m.bcsj_zje, m.bcsj_qfje, m.bcsj_ysje, m.yhzwrq, m.jfbs, m.jfmx1, m.jfmx2, m.jfmx3, m.remark from ydparaben.dbo.rtupara as r," + table_name + " m, meterpara met, residentpara res "+
			  "where r.id = met.rtu_id and met.resident_id = res.id and m.rtu_id = r.id and res.rtu_id = r.id and m.mp_id = met.mp_id " +
			  " and m.check_flag = 0 and m.rever_flag = 0 and m.date = " + sdate + " and m.czybh = " + czybh + ")";
			table_name = "yddataben.dbo.zyffMisPayNew" + isdate;
			sql += "union (select m.rtu_id, m.zjg_id as mp_id, c.busi_no as cons_no, c.describe, m.date, m.time, m.ccbh,m.dsdwbh, m.czybh, m.jylsh, m.dzpc, m.yhbh, m.error_no, " + 
		 	  "m.bcsj_zje, m.bcsj_qfje, m.bcsj_ysje, m.yhzwrq, m.jfbs, m.jfmx1, m.jfmx2, m.jfmx3, m.remark from ydparaben.dbo.rtupara as r," + table_name + " m, ydparaben.dbo.conspara c " +
		 	  "where r.cons_id = c.id and r.id = m.rtu_id and m.check_flag = 0 and m.rever_flag = 0 and m.date = " + sdate + " and m.czybh = " + czybh + ")";
		
		  try {
			rs = j_dao.executeQuery(sql);
			while(rs.next()){
				if(count > 10000) break;
				ret.append("{id:\"" + rs.getInt("rtu_id") + "_" + rs.getInt("mp_id") + "_"  + count + "\",data:[" + ++count +",");
				//用户编号
				ret.append("\"" + CommBase.CheckString(rs.getString("cons_no")) + "\",");
				//用户名称
				ret.append("\"" + CommBase.CheckString(rs.getString("describe")) + "\",");
				//操作日期
				ret.append("\"" + CommFunc.FormatToYMD(rs.getInt("date")) + CommFunc.FormatToHMS(rs.getInt("time"), 2) + "\",");
				
				int error_no = rs.getInt("error_no");
				if(error_no == 1001){
					ret.append("\"" + error_no + "\",");
				}
				else{
					ret.append("\"<font color = 'red'>" + error_no + "</font>\",");
				}
				
				//本次实缴总金额
				ret.append("\"" + rs.getString("bcsj_zje") + "\",");
				//本次实缴欠费金额
				ret.append("\"" + rs.getString("bcsj_qfje") + "\",");
				//本次实缴预收金额
				ret.append("\"" + rs.getString("bcsj_ysje") + "\",");
				//出厂编号
				ret.append("\"" + rs.getString("ccbh") + "\",");
				//对账批次
				ret.append("\"" + rs.getString("dzpc") + "\",");
				//代收单位编号
				ret.append("\"" + rs.getString("dsdwbh") + "\",");
				//操作员编号
				ret.append("\"" + rs.getString("czybh") + "\",");
				//交易流水号
				ret.append("\"" + rs.getString("jylsh") + "\",");
				//缴费笔数
				ret.append("\"" + rs.getString("jfbs") + "\",");
				//银行账务日期
				ret.append("\"" + CommFunc.FormatToYMD(rs.getString("yhzwrq")) + "\",");
				//缴费明细1
				ret.append("\"" + rs.getString("jfmx1") + "\",");
				//缴费明细2
				ret.append("\"" + rs.getString("jfmx2") + "\",");
				//缴费明细3
				ret.append("\"" + rs.getString("jfmx3") + "\",");
				//备注
				ret.append("\"" + rs.getString("remark") + "\",");
				ret.append("]},");
			}
		  } catch (SQLException e) {
			e.printStackTrace();
		  }
		}
		else{
			table_name = "yddataben.dbo.jyffMisPay" + isdate;
			sql = "(select m.rtu_id, m.mp_id, res.cons_no, res.describe, m.date, m.time, m.ccbh,m.dsdwbh, m.czybh, m.jylsh, m.yhbh, m.error_no, " + 
			  "m.jfje, m.yhzwrq, m.jfbs, m.ysbs1, m.ysbs2, m.ysbs3, m.remark from ydparaben.dbo.rtupara as r," + table_name + " m, meterpara met, residentpara res "+
			  "where r.id = met.rtu_id and met.resident_id = res.id and m.rtu_id = r.id and res.rtu_id = r.id and m.mp_id = met.mp_id " +
			  " and m.check_flag = 0 and m.rever_flag = 0 and m.date = " + sdate + " and m.czybh = '" + czybh + "')";
			table_name = "yddataben.dbo.zyffMisPay" + isdate;
			sql += "union (select m.rtu_id, m.zjg_id as mp_id, c.busi_no as cons_no, c.describe, m.date, m.time, m.ccbh,m.dsdwbh, m.czybh, m.jylsh, m.yhbh, m.error_no, " + 
		 	    "m.jfje, m.yhzwrq, m.jfbs, m.ysbs1, m.ysbs2, m.ysbs3, m.remark from ydparaben.dbo.rtupara as r," + table_name + " m, ydparaben.dbo.conspara c " +
		 	    "where r.cons_id = c.id and r.id = m.rtu_id and m.check_flag = 0 and m.rever_flag = 0 and m.date = " + sdate + " and m.czybh = '" + czybh + "')";
		
		  try {
			rs = j_dao.executeQuery(sql);
			while(rs.next()){
				if(count > 10000) break;
				ret.append("{id:\"" + rs.getInt("rtu_id") + "_" + rs.getInt("mp_id") + "_"  + count + "\",data:[" + ++count +",");
				//用户编号
				ret.append("\"" + CommBase.CheckString(rs.getString("cons_no")) + "\",");
				//用户名称
				ret.append("\"" + CommBase.CheckString(rs.getString("describe")) + "\",");
				//操作日期
				ret.append("\"" + CommFunc.FormatToYMD(rs.getInt("date")) + CommFunc.FormatToHMS(rs.getInt("time"), 2) + "\",");
				
				int error_no = rs.getInt("error_no");
				if(error_no == 1001){
					ret.append("\"" + error_no + "\",");
				}
				else{
					ret.append("\"<font color = 'red'>" + error_no + "</font>\",");
				}
				
				//缴费金额
				ret.append("\"" + rs.getString("jfje") + "\",");
				//出厂编号
				ret.append("\"" + rs.getString("ccbh") + "\",");
				//代收单位编号
				ret.append("\"" + rs.getString("dsdwbh") + "\",");
				//操作员编号
				ret.append("\"" + rs.getString("czybh") + "\",");
				//交易流水号
				ret.append("\"" + rs.getString("jylsh") + "\",");
				//缴费笔数
				ret.append("\"" + rs.getString("jfbs") + "\",");
				//银行账务日期
				ret.append("\"" + CommFunc.FormatToYMD(rs.getString("yhzwrq")) + "\",");
				//应收标识1
				ret.append("\"" + rs.getString("ysbs1") + "\",");
				//应收标识2
				ret.append("\"" + rs.getString("ysbs2") + "\",");
				//应收标识3
				ret.append("\"" + rs.getString("ysbs3") + "\",");
				//备注
				ret.append("\"" + rs.getString("remark") + "\",");
				ret.append("]},");
			}
		  } catch (SQLException e) {
			e.printStackTrace();
		  }
		}
		
		if(count == 0){
			result = "";
		}else{
			ret.deleteCharAt(ret.length() - 1);
			ret.append("]}");
			result = ret.toString();
		}
		return SDDef.SUCCESS;
	}

	//河北高低压MIS对账
	private String MIS_GDYCheck(String user_name, ComntParaBase.RTU_PARA rtu_para, ComntParaBase.OpDetailInfo op_detail,
		    StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail)throws Exception {
		misResult  = SDDef.EMPTY;

		if (misOperStr != null) misOperStr = misOperStr.trim();
		if (misOperStr == null || misOperStr.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			misResult = "错误的请求参数";
			return SDDef.FAIL;
		}
		
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int ymd = CommBase.strtoi(misOperStr);	//获取交易日期
		
		ComntMsgMis.YFFMISMSG_GDYCHECKPAY 			power_check  = new ComntMsgMis.YFFMISMSG_GDYCHECKPAY();
		ComntMsgMis.YFFMISMSG_GDYCHECKPAY_RESULT	power_result = new ComntMsgMis.YFFMISMSG_GDYCHECKPAY_RESULT();
		power_check.transymd = ymd;
		
		boolean ret_val = false;
		task_result_detail.clear(); 
		err_str_1.setLength(0);
		ret_val = ComntOperMis.GDYMisCheck(user_name, i_user_data1, i_user_data2, rtu_para, 
				power_check, power_result, task_result_detail, err_str_1);
		if(ret_val){
			//misResult = ComntOperMis.GetMisErrStr(power_result.retcode);
			misResult = "MIS对账成功!";
		}
		else{
			misResult 	= ComntOperMisHN.GetMisErrStr(task_result_detail.errcode);
		}
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}
	
	//河南高低压MIS对账
	private String MIS_GDYCheck_HN(String user_name, ComntParaBase.RTU_PARA rtu_para, ComntParaBase.OpDetailInfo op_detail,
		    StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail)throws Exception {
		misResult  = SDDef.EMPTY;

		if (misOperStr != null) misOperStr = misOperStr.trim();
		if (misOperStr == null || misOperStr.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			misResult = "错误的请求参数";
			return SDDef.FAIL;
		}
		
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int ymd = CommBase.strtoi(misOperStr);	//获取交易日期
		
		ComntMsgMisHN.YFFMISMSG_GDYCHECKPAY_HN 			power_check  = new ComntMsgMisHN.YFFMISMSG_GDYCHECKPAY_HN();
		ComntMsgMisHN.YFFMISMSG_GDYCHECKPAY_RESULT_HN	power_result = new ComntMsgMisHN.YFFMISMSG_GDYCHECKPAY_RESULT_HN();
		power_check.transymd = ymd;
		
		//--------------------------20140325----------------------------------------
		//操作员编号
		ComntFunc.string2Byte(CommFunc.getYffMan().getName(), power_check.czybh);
		ComntFunc.byteStringCutW(power_check.czybh, ComntOperMisHN.OPERMAN_STRLEN);
		
		boolean ret_val = false;
		task_result_detail.clear(); 
		err_str_1.setLength(0);
		ret_val = ComntOperMisHN.GDYMisCheck(user_name, i_user_data1, i_user_data2, rtu_para, 
				power_check, power_result, task_result_detail, err_str_1);
		if(ret_val){
			misResult = "MIS对账成功!";
		}
		else{
			misResult 	= ComntOperMisHN.GetMisErrStr(task_result_detail.errcode);
		}
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}

	//甘肃高低压MIS对账
	private String MIS_GDYCheck_GS(String user_name, ComntParaBase.RTU_PARA rtu_para, ComntParaBase.OpDetailInfo op_detail,
		    StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail)throws Exception {
		misResult  = SDDef.EMPTY;

		if (misOperStr != null) misOperStr = misOperStr.trim();
		if (misOperStr == null || misOperStr.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			misResult = "错误的请求参数";
			return SDDef.FAIL;
		}
		
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int ymd = CommBase.strtoi(misOperStr);	//获取交易日期
		
		ComntMsgMisGS.YFFMISMSG_GDYCHECKPAY_GS 			power_check  = new ComntMsgMisGS.YFFMISMSG_GDYCHECKPAY_GS();
		ComntMsgMisGS.YFFMISMSG_GDYCHECKPAY_RESULT_GS	power_result = new ComntMsgMisGS.YFFMISMSG_GDYCHECKPAY_RESULT_GS();
		power_check.transymd = ymd;
		
		boolean ret_val = false;
		task_result_detail.clear(); 
		err_str_1.setLength(0);
		ret_val = ComntOperMisGS.GDYMisCheck(user_name, i_user_data1, i_user_data2, rtu_para, 
				power_check, power_result, task_result_detail, err_str_1);
		if(ret_val){
			//misResult = ComntOperMisGS.GetMisErrStr(power_result.retcode);
			misResult = "MIS对账成功!";
		}
		else{
			misResult 	= ComntOperMisHN.GetMisErrStr(task_result_detail.errcode);
		}
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}
	
	public String taskProc(){
		ComntParaBase.OpDetailInfo op_detail = new ComntParaBase.OpDetailInfo();
		StringBuffer err_str_1 				 = new StringBuffer();
		ComntParaBase.SALEMAN_TASK_RESULT_DETAIL 	task_result_detail = new ComntParaBase.SALEMAN_TASK_RESULT_DETAIL();
		int rtu_id    = CommBase.strtoi(userData1);
		int userop_id = CommBase.strtoi(userData2); 
		
		ComntParaBase.RTU_PARA rtu_para = ComntParaBase.loadRtuActPara(rtu_id);
		
		if (rtu_para == null) {
			result = SDDef.FAIL;
			op_detail.addTaskInfo("ERR:无效的终端ID[" + rtu_id + "]");
			detailInfo = op_detail.toJsonString(); 
			return SDDef.SUCCESS;
		}
		String user_name = ComntParaBase.getUserName();
		String ret_val = SDDef.FAIL;
		
		try {
			if("HB".equals(WebConfig.provinceMisFlag)){
				if (userop_id == ComntUseropDef.YFF_GDYOPER_MIS_CHECKPAY) {			
					ret_val = MIS_GDYCheck(user_name, rtu_para, op_detail, err_str_1, task_result_detail);
				}
			}
			//河南MIS
			else if("HN".equals(WebConfig.provinceMisFlag)){
				if (userop_id == ComntUseropDef.YFF_GDYOPER_MIS_CHECKPAY) {
					ret_val = MIS_GDYCheck_HN(user_name, rtu_para, op_detail, err_str_1, task_result_detail);
				}
			}
			//甘肃MIS
			else if("GS".equals(WebConfig.provinceMisFlag)){
				if (userop_id == ComntUseropDef.YFF_GDYOPER_MIS_CHECKPAY) {
					ret_val = MIS_GDYCheck_GS(user_name, rtu_para, op_detail, err_str_1, task_result_detail);
				}
			}
			else{
				result = SDDef.FAIL;
				op_detail.addTaskInfo("ERR:不支持该省份的MIS");
			}
			result 	   = ret_val;
			detailInfo = op_detail.toJsonString();
		} catch (Exception e) {
			
			CommFunc.err_log(e);
			result = SDDef.FAIL;
			misResult = e.getMessage();
		}
		return SDDef.SUCCESS;
	}
	
	//取消任务
	public String cancelTask() {
		String user_name = ComntParaBase.getUserName();	
		
		int i_user_data1 = CommBase.strtoi(userData1);
		int i_user_data2 = CommBase.strtoi(userData2);

		ComntMsgProc.cancelMsg(user_name, i_user_data1, i_user_data2);
		
		result = SDDef.SUCCESS;	
		return SDDef.SUCCESS;
	}
	
	//get/set方法
	public String getResult() {
		return result;
	}

	public String getUserData1() {
		return userData1;
	}

	public void setUserData1(String userData1) {
		this.userData1 = userData1;
	}

	public String getUserData2() {
		return userData2;
	}

	public void setUserData2(String userData2) {
		this.userData2 = userData2;
	}

	public String getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}

	public String getMisResult() {
		return misResult;
	}

	public void setMisResult(String misResult) {
		this.misResult = misResult;
	}

	public String getMisOperStr() {
		return misOperStr;
	}

	public void setMisOperStr(String misOperStr) {
		this.misOperStr = misOperStr;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDatepara() {
		return datepara;
	}

	public void setDatepara(String datepara) {
		this.datepara = datepara;
	}

	public String getMisType() {
		return misType;
	}

	public void setMisType(String misType) {
		this.misType = misType;
	}
	
	
}
