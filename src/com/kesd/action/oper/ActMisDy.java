package com.kesd.action.oper;

import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Map;

import net.sf.json.JSONObject;

import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.WebConfig;
import com.kesd.comnt.ComntMsgMis;
import com.kesd.comnt.ComntMsgMisHN;
import com.kesd.comnt.ComntMsgProc;
import com.kesd.comnt.ComntMsgMis.YFFMISMSG_DY_PAY;
import com.kesd.comntpara.ComntOperMis;
import com.kesd.comntpara.ComntOperMisHN;
import com.kesd.comntpara.ComntParaBase;
import com.kesd.comntpara.ComntUseropDef;
import com.kesd.service.DBOperMis;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
import com.libweb.comnt.ComntFunc;
import com.libweb.comnt.ComntTime;
import com.libweb.dao.JDBCDao;

public class ActMisDy {

	private static final long serialVersionUID = -3770616860256454508L;
	
	private String  userData1 			= null;		//RTU_ID
	private String  userData2 			= null;		//USEROP_ID
	private String 	result 				= null;		//SUCCESS/FAIL
	private String 	detailInfo 			= null;		//返回详细信息(JSON)
	
	private String  misUseflag			= null;		//
	private String  misOkflag			= null;		//
	private String  misResult			= null;		//
	
	private String  dyOperStr			= null;		//低压操作字符串
	
	//*********************************************河北MIS开始*************************************************
	//河北低压操作-查询缴费
	private String MIS_DyQueryPower(String user_name, ComntParaBase.RTU_PARA rtu_para, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {

		//useflag 首次初始化为 TRUE
		misUseflag = "true";
		misOkflag  = "false";
		misResult  = SDDef.EMPTY;

		if (dyOperStr != null) dyOperStr = dyOperStr.trim();
		if (dyOperStr == null || dyOperStr.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			misResult = "错误的请求参数";
			return SDDef.FAIL;
		}
		
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID

		JSONObject json = JSONObject.fromObject(dyOperStr);		
		
		int     p_rtu_id = CommBase.strtoi(json.getString("rtu_id"));
		short   p_mp_id = (short)CommBase.strtoi(json.getString("mp_id"));
		//用户编号
		String  p_consno = json.getString("yhbh");
		
		ComntMsgMis.YFFMISMSG_DY_QUERYPOWER 		power_query  = new ComntMsgMis.YFFMISMSG_DY_QUERYPOWER();
		ComntMsgMis.YFFMISMSG_DY_QUERYPOWER_RESULT  power_result = new ComntMsgMis.YFFMISMSG_DY_QUERYPOWER_RESULT();
		
		power_query.rtu_id = p_rtu_id;
		power_query.sub_id = p_mp_id;
		//用户编号
		ComntFunc.string2Byte(p_consno, power_query.yhbh);
		
		dyOperStr = SDDef.EMPTY;
		
		boolean ret_val = false;
		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMis.DYQueryPower(user_name, i_user_data1, i_user_data2, rtu_para, p_mp_id, 
											power_query, power_result, task_result_detail, err_str_1);
	
		if (ret_val) {
			misUseflag 	= "true";
			misOkflag	= "true";
			
			//使用MIS 但查询失败
			if(power_result.retcode != ComntMsgMis.YFFMIS_MISOP_OKNO){
				misOkflag = "false";
				//连接MIS失败
				if(power_result.retcode == 0) {
					misResult = "MIS用户查询失败, \n错误信息:" + err_str_1 + " \n不能操作...";
				}
				//MIS返回错误
				else{
					misResult = "MIS用户查询失败, \n错误信息:" + err_str_1 + " , \nMIS错误信息:" + 
					power_result.retcode + ": " + ComntOperMis.GetMisErrStr(power_result.retcode) + " \n不能操作...";
				}
			}
			else {
				dyOperStr = power_result.toJsonString();
			}
		}
		else {
			if(task_result_detail.errcode == ComntMsgMis.YFFMIS_ERR_MISNOUSE) {
				misUseflag = "false";
			}
			else {
				misResult = "MIS通讯错误";
			}
		}
	
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;		
	}

	//河北低压操作-缴费记录上传
	private String MIS_DyPay(String user_name, ComntParaBase.RTU_PARA rtu_para, 
						  ComntParaBase.OpDetailInfo op_detail, StringBuffer err_str_1, 
						  ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		
		misResult  = SDDef.EMPTY;
		
		if (dyOperStr != null) dyOperStr = dyOperStr.trim();
		if (dyOperStr == null || dyOperStr.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			misResult = "错误的请求参数";			
			return SDDef.FAIL;
		}
		
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID

		boolean ret_val = false;		
		JSONObject json = JSONObject.fromObject(dyOperStr);		
		
		int 	p_rtu_id 	= CommBase.strtoi(json.getString("rtu_id"));
		short 	p_mp_id 	= (short)CommBase.strtoi(json.getString("mp_id"));
		int     p_date   	= CommBase.strtoi(json.getString("date"));
		int     p_time   	= CommBase.strtoi(json.getString("time"));
		byte    p_optype	= (byte)CommBase.strtoi(json.getString("op_type"));
		//用户编号
		String  p_consno 	= json.getString("yhbh");
		//交易流水号
		String  p_wasteno 	= json.getString("jylsh");
		//缴费金额
		double  p_paymoney  = CommBase.strtof(json.getString("jfje"));
		
		//没有缴费,不用于MIS通讯
		if (p_paymoney <= 0.01) {
			misResult = "没有缴费,不用传MIS缴费记录...";
			return SDDef.SUCCESS;
		}
		
		//发送
		ComntMsgMis.YFFMISMSG_DY_PAY pay_req = new ComntMsgMis.YFFMISMSG_DY_PAY();
		ComntMsgMis.YFFMISMSG_DY_PAY_RESULT  pay_result = new ComntMsgMis.YFFMISMSG_DY_PAY_RESULT();
		
		pay_req.rtu_id 	= p_rtu_id;
		pay_req.sub_id 	= p_mp_id;
		pay_req.date 	= p_date;
		pay_req.time 	= p_time;
		
		//更新标志
		pay_req.updateflag = 0;
		//操作员编号
		ComntFunc.string2Byte(CommFunc.getYffMan().getName(), pay_req.czybh);
		ComntFunc.byteStringCutW(pay_req.czybh, ComntOperMis.OPERMAN_STRLEN);
		//交易流水号
		ComntFunc.string2Byte(p_wasteno, pay_req.jylsh);
		//用户编号
		ComntFunc.string2Byte(p_consno, pay_req.yhbh);
		//缴费金额
		pay_req.jfje = p_paymoney;
		//银行账务日期
		pay_req.yhzwrq = CommBase.strtoi(json.getString("yhzwrq"));
		//缴费笔数
		pay_req.jfbs = CommBase.strtoi(json.getString("jfbs"));
		
		int len = CommBase.strtoi(json.getString("vlength"));
		pay_req.details_vect = new YFFMISMSG_DY_PAY.POWERPAY_DETAIL[len];
		for(int j = 0; j < len; j++){
			pay_req.details_vect[j] = new YFFMISMSG_DY_PAY.POWERPAY_DETAIL();
			
			ComntFunc.string2Byte(json.getString("yhbh" + j), pay_req.details_vect[j].yhbh);
			ComntFunc.string2Byte(json.getString("ysbs" + j), pay_req.details_vect[j].ysbs);
		}

		dyOperStr = SDDef.EMPTY;
		
		//上传缴费记录
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMis.DYPay(user_name, i_user_data1, i_user_data2, rtu_para, p_mp_id, 
											pay_req, pay_result, task_result_detail, err_str_1);

		boolean payok_flag = false;
		
		if (ret_val) {
			//上传成功
			if(pay_result.retcode == ComntMsgMis.YFFMIS_MISOP_OKNO) {
				payok_flag = true;
				
				dyOperStr = pay_result.toJsonString();
			}
		}
		//返回错误 读数据库
		else {
			//容错处理
			Map<String, String> map = DBOperMis.loadOneJYffMisPayRecord(/*p_rtu_id, p_mp_id, */p_date, p_wasteno);
			if ((map != null) && 
				(CommBase.strtoi(map.get("error_no")) == ComntMsgMis.YFFMIS_MISOP_OKNO)) {
				payok_flag = true;
			}
		}
		
		//更新本地数据库
		DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, p_date, p_time, p_optype, 
								  true, payok_flag);
		
		if(payok_flag == false) {
			//连接MIS失败
			if(pay_result.retcode == 0) {
				misResult = "上传缴费记录失败, \n错误信息:" + err_str_1 + " \n不能操作...";
			}
			//MIS返回错误
			else{
				misResult = "上传缴费记录失败, \n错误信息:" + err_str_1 + " , \nMIS错误信息:" + 
				pay_result.retcode + ": " + ComntOperMis.GetMisErrStr(pay_result.retcode) + " \n请转入【补MIS缴费记录】页面...";
			}
		}
		else {
			misResult = "上传缴费记录成功。";
		}
		
		misOkflag = payok_flag ? "true" : "false";
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}
		
	//河北低压操作-冲正
	private String MIS_DYRever(String user_name, ComntParaBase.RTU_PARA rtu_para, 
								ComntParaBase.OpDetailInfo op_detail, StringBuffer err_str_1, 
								ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {

		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;
		
		misResult = SDDef.EMPTY;
		
		if (dyOperStr != null) dyOperStr = dyOperStr.trim();
		if (dyOperStr == null || dyOperStr.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			misResult = "错误的请求参数";
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(dyOperStr);
		
		int 	p_rtu_id 	= CommBase.strtoi(json.getString("rtu_id"));
		short 	p_mp_id 	= (short)CommBase.strtoi(json.getString("mp_id"));
		int     p_last_date = CommBase.strtoi(json.getString("last_date"));
		int     p_last_time = CommBase.strtoi(json.getString("last_time"));
		int     p_date   	= CommBase.strtoi(json.getString("date"));
		int     p_time   	= CommBase.strtoi(json.getString("time"));
		//用户编号
		String  p_consno    = json.getString("yhbh");
		//缴费金额
		double  p_paymoney  = CommBase.strtof(json.getString("pay_money"));
		//冲正交易流水号
		String  p_wasteno   = json.getString("czjylsh");
		//原交易流水号
		String  p_rewasteno = json.getString("yjylsh");
		
		//读取被冲正的缴费记录
		Map<String, String> record_lastpay = DBOperMis.loadOneJYffOpRecord(p_rtu_id, p_mp_id, p_last_date, p_last_time, 
				SDDef.YFF_OPTYPE_ADDRES , SDDef.YFF_OPTYPE_RESTART , SDDef.YFF_OPTYPE_PAY , SDDef.YFF_OPTYPE_CHANGEMETER );
		
		if(record_lastpay == null) {
			misResult = "MIS冲正, 读取被冲正记录失败, 请手工在MIS中冲正...";
			return SDDef.FAIL;
		}
		
		//读取冲正记录
		Map<String, String> record_rever = DBOperMis.loadOneJYffOpRecord(p_rtu_id, p_mp_id, p_date, p_time, 
											SDDef.YFF_OPTYPE_REVER, -1, -1, -1);
		if(record_rever == null) {
			misResult = "MIS冲正, 读取被冲正记录失败, 请手工在MIS中冲正...";
			return SDDef.FAIL;
		}
		
		//读取新缴费记录
		Map<String, String> record_thispay = DBOperMis.loadOneJYffOpRecord(p_rtu_id, p_mp_id, p_date, p_time, 
											SDDef.YFF_OPTYPE_PAY, -1, -1, -1);
		if(record_thispay == null) {
			misResult = "MIS冲正, 读取被冲正记录失败, 请手工在MIS中冲正...";
			return SDDef.FAIL;
		}
		
		if(!record_lastpay.get("wasteno").equals(record_rever.get("rewasteno"))){
			misResult = "MIS冲正, 流水号匹配错误, 请手工在MIS中操作冲正...";
			return SDDef.FAIL;
		}
		
		//把被冲正的缴费记录的MIS标志清掉
		DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, p_last_date, p_last_time, 
								 CommBase.strtoi(record_lastpay.get("op_type")), 
								 false, false);
		
		//把缴费数据库记录的MIS置上
		if (p_paymoney > 0.01) {
			DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, p_date, p_time, SDDef.YFF_OPTYPE_PAY, true, false);
		}

		//上传冲正记录
		ComntMsgMis.YFFMISMSG_DY_REVER 		   rever_req    = new ComntMsgMis.YFFMISMSG_DY_REVER();
		ComntMsgMis.YFFMISMSG_DY_REVER_RESULT  rever_result = new ComntMsgMis.YFFMISMSG_DY_REVER_RESULT();
		
		rever_req.rtu_id = p_rtu_id;
		rever_req.sub_id = p_mp_id;
		rever_req.date 	 = p_date;
		rever_req.time 	 = p_time;
		
		//被冲正记录日期
		rever_req.last_date = p_last_date;
		//被冲正记录时间
		rever_req.last_time = p_last_time;
		//更新标志
		rever_req.updateflag = 0;
		//操作员编号
		ComntFunc.string2Byte(CommFunc.getYffMan().getName(), rever_req.czybh);
		ComntFunc.byteStringCutW(rever_req.czybh, ComntOperMis.OPERMAN_STRLEN);
		//冲正交易流水号
		//ComntFunc.string2Byte(p_wasteno, rever_req.czjylsh);
		ComntFunc.string2Byte(record_rever.get("wasteno"), rever_req.czjylsh);
		//原交易流水号
		ComntFunc.string2Byte(p_rewasteno, rever_req.yjylsh);
		//银行账务日期
		rever_req.yhzwrq = CommBase.strtoi(json.getString("yhzwrq"));
		
		dyOperStr = SDDef.EMPTY;
		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMis.DYRever(user_name, i_user_data1, i_user_data2, rtu_para, p_mp_id, 
									rever_req, rever_result, task_result_detail, err_str_1); 			

		boolean rev_ok_flag = false;
		if (ret_val) {
			if(rever_result.retcode == ComntMsgMis.YFFMIS_MISOP_OKNO) {
				rev_ok_flag = true;
			}
		}
		else {
			Map<String, String> map_rev_record = DBOperMis.loadOneJYffMisReverRecord(p_date, p_rewasteno);
			if ((map_rev_record != null) && 
				(CommBase.strtoi(map_rev_record.get("error_no")) == ComntMsgMis.YFFMIS_MISOP_OKNO)) {
				rev_ok_flag = true;
			}
		}

		//更新本地数据库
		DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, p_date, p_time, 
								 SDDef.YFF_OPTYPE_REVER, true, rev_ok_flag);
		
		if (!rev_ok_flag) {
			//连接MIS失败
			if(rever_result.retcode == 0) {
				misResult = "上传冲正记录失败, 错误信息:" + err_str_1 + " \n请转入【补MIS冲正记录】页面...";
			}
			//MIS返回错误
			else{
				misResult = "上传冲正记录失败, \n错误信息:" + err_str_1 + " , \nMIS错误信息:" + 
				rever_result.retcode + ": " + ComntOperMis.GetMisErrStr(rever_result.retcode) + " \n请转入【补MIS冲正记录】页面...";
			}
			return SDDef.FAIL;
		}
		
		//未缴费
		if(p_paymoney < 0.01) {
			misResult = "冲正成功...";
			return rev_ok_flag ? SDDef.SUCCESS : SDDef.FAIL;
		}
		
		//暂停1s
		CommFunc.waitMsg(1000);
		
		//MIS查询
		ComntMsgMis.YFFMISMSG_DY_QUERYPOWER power_req = new ComntMsgMis.YFFMISMSG_DY_QUERYPOWER();
		
		power_req.rtu_id = p_rtu_id;
		power_req.sub_id = p_mp_id;
		//用户编号
		ComntFunc.string2Byte(p_consno, power_req.yhbh);

		ComntMsgMis.YFFMISMSG_DY_QUERYPOWER_RESULT  power_result = new ComntMsgMis.YFFMISMSG_DY_QUERYPOWER_RESULT();		
		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMis.DYQueryPower(user_name, i_user_data1, i_user_data2, rtu_para, p_mp_id, 
				power_req, power_result, task_result_detail, err_str_1);
		
		if (ret_val == false || power_result.retcode != ComntMsgMis.YFFMIS_MISOP_OKNO) {
			if (task_result_detail.errcode == 0) {
				misResult = "MIS用户查询失败, \n错误信息:" + err_str_1 + " \n请转入【补MIS缴费记录】页面...";
			}
			else {
				misResult = "MIS用户查询失败, \n错误信息:" + err_str_1 + ", \nMIS错误信息:" + 
				task_result_detail.errcode + ": " + ComntOperMis.GetMisErrStr(task_result_detail.errcode) + " \n请转入【补MIS缴费记录】页面...";
			}

			//更新缴费本地数据库
			DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, 
										CommBase.strtoi(record_thispay.get("op_date")), 
										CommBase.strtoi(record_thispay.get("op_time")), 
									   SDDef.YFF_OPTYPE_PAY, true, false);

			return SDDef.FAIL;
		}
		
		//暂停1s
		CommFunc.waitMsg(1000);
		
		//缴费
		ComntMsgMis.YFFMISMSG_DY_PAY pay_req = new ComntMsgMis.YFFMISMSG_DY_PAY();
		
		pay_req.rtu_id = p_rtu_id;
		pay_req.sub_id = p_mp_id;
		pay_req.date = CommBase.strtoi(record_thispay.get("op_date"));
		pay_req.time = CommBase.strtoi(record_thispay.get("op_time"));
		pay_req.updateflag = 0;
		//操作员编号
		ComntFunc.string2Byte(CommFunc.getYffMan().getName(), pay_req.czybh);
		ComntFunc.byteStringCutW(pay_req.czybh, ComntOperMis.OPERMAN_STRLEN);
		
		//交易流水号
		ComntFunc.string2Byte(record_thispay.get("wasteno"), pay_req.jylsh);
		//用户编号
		ComntFunc.string2Byte(p_consno, pay_req.yhbh);
		//缴费金额
		pay_req.jfje = p_paymoney;
		//银行账务日期
		pay_req.yhzwrq = CommBase.strtoi(record_thispay.get("op_date"));
		//缴费笔数
		pay_req.jfbs = power_result.jezbs;
		//欠费信息
		pay_req.details_vect = new YFFMISMSG_DY_PAY.POWERPAY_DETAIL[pay_req.jfbs];
		for(int j = 0; j < pay_req.jfbs; j++){
			pay_req.details_vect[j] = new YFFMISMSG_DY_PAY.POWERPAY_DETAIL();
			
			ComntFunc.arrayCopy(pay_req.details_vect[j].yhbh, power_result.details_vect[j].yhbh);
			ComntFunc.arrayCopy(pay_req.details_vect[j].ysbs, power_result.details_vect[j].ysbs);
		}

		//上传缴费记录
		ComntMsgMis.YFFMISMSG_DY_PAY_RESULT pay_result = new ComntMsgMis.YFFMISMSG_DY_PAY_RESULT();
		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMis.DYPay(user_name, i_user_data1, i_user_data2, rtu_para, p_mp_id, 
									pay_req, pay_result, task_result_detail, err_str_1);
		
		boolean pay_okflag = false;
		if (ret_val == false) {
			Map<String, String> map = DBOperMis.loadOneJYffMisPayRecord(/*p_rtu_id, p_mp_id,*/ 
									  CommBase.strtoi(record_thispay.get("op_date")), 
									  record_thispay.get("wasteno"));
			if ((map != null) && 
				(CommBase.strtoi(map.get("error_no")) == ComntMsgMis.YFFMIS_MISOP_OKNO)) {
				pay_okflag = true;
			}
		}
		//返回错误 读数据库
		else {
			//上传成功
			if(pay_result.retcode == ComntMsgMis.YFFMIS_MISOP_OKNO) {
				pay_okflag = true;
			}
		}
		
		//更新本地数据库
		DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, 
								  CommBase.strtoi(record_thispay.get("op_date")),
								  CommBase.strtoi(record_thispay.get("op_time")),
								  SDDef.YFF_OPTYPE_PAY, true, pay_okflag);
		
		if (!pay_okflag) {
			if (pay_result.retcode == 0) {
				misResult = "上传缴费记录失败, 错误信息:" + err_str_1 + " \n请转入【补MIS缴费记录】页面";
			}
			else {
				misResult = "上传缴费记录失败, 错误信息:" + err_str_1 + " \nMIS错误信息:" + 
							pay_result.retcode + ": " +
							ComntOperMis.GetMisErrStr(pay_result.retcode) + 
							" \n请转入【补MIS缴费记录】页面 "; 
			}			
		}
		else {
			misResult = "MIS冲正成功...";
		}
		
		return pay_okflag ? SDDef.SUCCESS : SDDef.FAIL;		
	}
	
	//河北低压操作-检查缴费
	private String MIS_DYCheckPay(String user_name, ComntParaBase.RTU_PARA rtu_para, 
								ComntParaBase.OpDetailInfo op_detail, StringBuffer err_str_1, 
								ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {

		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;
		
		misResult = SDDef.EMPTY;
		
		if (dyOperStr != null) dyOperStr = dyOperStr.trim();
		if (dyOperStr == null || dyOperStr.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			misResult = "错误的请求参数";
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(dyOperStr);

		int 	p_rtu_id 	= CommBase.strtoi(json.getString("rtu_id"));
		short 	p_mp_id 	= (short)CommBase.strtoi(json.getString("mp_id"));
		int     p_opdate   	= CommBase.strtoi(json.getString("op_date"));
		int     p_optime   	= CommBase.strtoi(json.getString("op_time"));
		byte    p_optype   	= (byte)CommBase.strtoi(json.getString("op_type"));
		//用户编号
		String  p_consno    = json.getString("yhbh");
		double  p_paymoney  = CommBase.strtof(json.getString("pay_money"));
		//流水号
		String  p_wasteno	= json.getString("lsh");
		
		//当前年月日
		ComntTime.TM cur_tm = ComntTime.localCTime(ComntTime.cTime());
		int cur_ymd = (cur_tm.year + 1900) * 10000 + (cur_tm.month + 1) * 100 + cur_tm.mday;
		
		if (p_opdate != cur_ymd) {
			misResult = "不能补交非今天的记录!";
			return SDDef.FAIL;
		}
		
		//读取本地缴费操作记录
		Map<String, String> record_pay = DBOperMis.loadOneJYffOpRecord(p_rtu_id, p_mp_id, p_opdate, p_optime, 
				p_optype , -1, -1, -1);
		
		if(record_pay == null) {
			misResult = "读取本地记录失败";
			return SDDef.FAIL;
		}
		if(CommBase.strtoi(record_pay.get("up186_flag")) == 0) {
			misResult = "此记录不用上传MIS";
			return SDDef.FAIL;
		}
		if(CommBase.strtoi(record_pay.get("checkpay_flag")) != 0) {
			misResult = "此记录已成功上传MIS";
			return SDDef.FAIL;
		}
		
		//读取本地冲正操作记录
		Map<String, String> record_rever = DBOperMis.loadOneJYffOpRecord(p_rtu_id, p_mp_id, p_opdate, p_optime, 
											SDDef.YFF_OPTYPE_REVER, -1, -1, -1);
		
		if (record_rever != null && 
		    CommBase.strtoi(record_rever.get("up186_flag")) != 0 && 
		    CommBase.strtoi(record_rever.get("checkpay_flag")) == 0) {
			misResult = "此用户存在未提交的冲正记录, 请先冲正";
			return SDDef.FAIL;
		}
		
		byte update_flag = 0;
		
		//读取MIS缴费操作记录
		Map<String, String> mis_payrcd = DBOperMis.loadOneJYffMisPayRecord(/*p_rtu_id, p_mp_id, */p_opdate, p_wasteno);
		if ((mis_payrcd != null) && 
			(ComntMsgMis.YFFMIS_MISOP_OKNO == CommBase.strtoi(mis_payrcd.get("error_no")))) {
			//更新本地数据库记录
			DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, p_opdate, p_optime, p_optype, true, true);
			return SDDef.SUCCESS;
		}
		
		if (mis_payrcd != null) {
			update_flag = 1;
		}
		
		//MIS查询
		ComntMsgMis.YFFMISMSG_DY_QUERYPOWER power_req = new ComntMsgMis.YFFMISMSG_DY_QUERYPOWER();
		ComntMsgMis.YFFMISMSG_DY_QUERYPOWER_RESULT  power_result = new ComntMsgMis.YFFMISMSG_DY_QUERYPOWER_RESULT();
		
		power_req.rtu_id = p_rtu_id;
		power_req.sub_id = p_mp_id;
		//用户编号
		ComntFunc.string2Byte(p_consno, power_req.yhbh);
		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMis.DYQueryPower(user_name, i_user_data1, i_user_data2, rtu_para, p_mp_id, 
				power_req, power_result, task_result_detail, err_str_1);

		if (ret_val == false || power_result.retcode != ComntMsgMis.YFFMIS_MISOP_OKNO) {
			if (task_result_detail.errcode == 0) {
				misResult = "MIS用户查询失败, \n错误信息:" + err_str_1 + " ...";
			}
			else {
				misResult = "MIS用户查询失败, \n错误信息:" + err_str_1 + ", \nMIS错误信息:" + 
				task_result_detail.errcode + ": " +
				ComntOperMis.GetMisErrStr(task_result_detail.errcode) + " ...";
			}

			return SDDef.FAIL;
		}
		
		//暂停1s
		CommFunc.waitMsg(1000);
		
		//缴费
		ComntMsgMis.YFFMISMSG_DY_PAY pay_req = new ComntMsgMis.YFFMISMSG_DY_PAY();
		ComntMsgMis.YFFMISMSG_DY_PAY_RESULT pay_result = new ComntMsgMis.YFFMISMSG_DY_PAY_RESULT();
		
		pay_req.rtu_id 	= p_rtu_id;
		pay_req.sub_id 	= p_mp_id;
		pay_req.date 	= p_opdate;
		pay_req.time 	= p_optime;
		pay_req.updateflag = update_flag;
		//操作员编号
		ComntFunc.string2Byte(CommFunc.getYffMan().getName(), pay_req.czybh);
		ComntFunc.byteStringCutW(pay_req.czybh, ComntOperMis.OPERMAN_STRLEN);
		
		//交易流水号
		ComntFunc.string2Byte(p_wasteno, pay_req.jylsh);
		//用户编号
		ComntFunc.string2Byte(p_consno, pay_req.yhbh);
		//缴费金额
		pay_req.jfje = p_paymoney;
		//银行账务日期
		pay_req.yhzwrq = p_opdate;
		//缴费笔数
		pay_req.jfbs = power_result.jezbs;
		//欠费信息
		pay_req.details_vect = new YFFMISMSG_DY_PAY.POWERPAY_DETAIL[pay_req.jfbs];
		for(int j = 0; j < pay_req.jfbs; j++){
			pay_req.details_vect[j] = new YFFMISMSG_DY_PAY.POWERPAY_DETAIL();
			
			ComntFunc.arrayCopy(pay_req.details_vect[j].yhbh, power_result.details_vect[j].yhbh);
			ComntFunc.arrayCopy(pay_req.details_vect[j].ysbs, power_result.details_vect[j].ysbs);
		}

		//上传缴费记录		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMis.DYPay(user_name, i_user_data1, i_user_data2, rtu_para, p_mp_id, 
									pay_req, pay_result, task_result_detail, err_str_1);
		
		boolean pay_okflag = false;
		if (ret_val == false) {
			Map<String, String> map = DBOperMis.loadOneZYffMisPayRecord(/*p_rtu_id, p_mp_id,*/ 
									  p_opdate, p_wasteno);
			if ((map != null) && 
				(CommBase.strtoi(map.get("error_no")) == ComntMsgMis.YFFMIS_MISOP_OKNO)) {
				pay_okflag = true;
			}
		}
		//返回错误 读数据库
		else {
			//上传成功
			if(pay_result.retcode == ComntMsgMis.YFFMIS_MISOP_OKNO) {
				pay_okflag = true;
			}
		}
		
		//更新本地数据库
		if (pay_okflag) {
			DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, p_opdate, p_optime,
									  SDDef.YFF_OPTYPE_PAY, true, true);
		}
		
		if (pay_okflag == false) {
			if (pay_result.retcode == 0) {
				misResult = "上传缴费记录失败, 错误信息:" + err_str_1;
			}
			else {
				misResult = "上传缴费记录失败, 错误信息:" + err_str_1 + " \nMIS错误信息:" +
							pay_result.retcode + ": " + 
							ComntOperMis.GetMisErrStr(pay_result.retcode); 
			}
		}
		else {
			misResult = "MIS缴费成功";
		}
		
		return pay_okflag ? SDDef.SUCCESS : SDDef.FAIL;				
	}	
	
	//河北低压操作-检查冲正
	private String MIS_DYCheckRever(String user_name, ComntParaBase.RTU_PARA rtu_para, 
								ComntParaBase.OpDetailInfo op_detail, StringBuffer err_str_1, 
								ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {

		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;		
		misResult = SDDef.EMPTY;		
		
		if (dyOperStr != null) dyOperStr = dyOperStr.trim();
		if (dyOperStr == null || dyOperStr.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			misResult = "错误的请求参数";
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(dyOperStr);
	
		int 	p_rtu_id 	= CommBase.strtoi(json.getString("rtu_id"));
		short 	p_mp_id 	= (short)CommBase.strtoi(json.getString("mp_id"));
		int     p_opdate   	= CommBase.strtoi(json.getString("op_date"));
		int     p_optime   	= CommBase.strtoi(json.getString("op_time"));
		byte    p_optype   	= (byte)CommBase.strtoi(json.getString("op_type"));
		//用户编号
		//String  p_consno    = json.getString("yhbh");
		//double  p_paymoney  = CommBase.strtoi(json.getString("pay_money"));
		//流水号
		String  p_wasteno	= json.getString("lsh");
		//被冲正流水号
		String  p_rewasteno = json.getString("bczlsh");
		
		//当前年月日
		ComntTime.TM cur_tm = ComntTime.localCTime(ComntTime.cTime());
		int cur_ymd = (cur_tm.year + 1900) * 10000 + (cur_tm.month + 1) * 100 + cur_tm.mday;
		
		if (p_opdate != cur_ymd) {
			misResult = "不能冲正非今天的记录!";
			return SDDef.FAIL;
		}
	
		//读取本地冲正操作记录
		Map<String, String> record_rever = DBOperMis.loadOneJYffOpRecord(p_rtu_id, p_mp_id, p_opdate, p_optime, 
				SDDef.YFF_OPTYPE_REVER , -1, -1, -1);
		
		if(record_rever == null) {
			misResult = "读取本地记录失败";
			return SDDef.FAIL;
		}
		if(CommBase.strtoi(record_rever.get("up186_flag")) == 0) {
			misResult = "此记录不用上传MIS";
			return SDDef.FAIL;
		}
		if(CommBase.strtoi(record_rever.get("checkpay_flag")) != 0) {
			misResult = "此记录已成功上传MIS";
			return SDDef.FAIL;
		}
		
		//读取本地被冲正操作记录
		Map<String, String> record_lastrever = DBOperMis.loadOneJYffOpRecordWasteNo(p_rtu_id, p_mp_id, p_opdate, p_rewasteno);
		if (record_lastrever == null) {
			misResult = "读取本地被冲正记录失败.";
			return SDDef.FAIL;
		}
		
		//读取MIS冲正操作记录
		Map<String, String> mis_reverrcd = DBOperMis.loadOneJYffMisReverRecord(/*p_rtu_id, p_mp_id, */p_opdate, p_wasteno);
		if ((mis_reverrcd != null) && 
			(ComntMsgMis.YFFMIS_MISOP_OKNO == CommBase.strtoi(mis_reverrcd.get("error_no")))) {
			//更新本地数据库记录
			DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, p_opdate, p_optime, p_optype, true, true);
			misResult = "冲正成功.";
			return SDDef.SUCCESS;
		}
		
		byte update_flag = 0;		
		if (mis_reverrcd != null) {
			update_flag = 1;
		}
		
		//容错处理 20120421
		//misrever 存在记录 
		//mispay无记录 说明被冲正记录未向186传过 故 也不用走冲正 
		//mispay 未成功 服务器端处理不合适
		//读取MIS缴费操作记录
		Map<String, String> mis_payrcd = DBOperMis.loadOneJYffMisPayRecord(/*p_rtu_id, p_mp_id, */p_opdate, p_rewasteno);
		//无记录不用再冲正
		if (mis_payrcd == null) {
			//更新本地数据库记录
			DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, p_opdate, p_optime, p_optype, true, true);
			misResult = "冲正成功.";
			return SDDef.SUCCESS;
		}
		
		//hzhw 20130822
		//if (CommBase.strtoi(record_lastrever.get("up186_flag")) == 0) {
			//更新本地数据库记录
		//	DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, p_opdate, p_optime, p_optype, true, true);
		//	misResult = "冲正成功.";
		//	return SDDef.SUCCESS;
		//}
		//20120421 end this 可能还会有点问题？？
		
		//上传冲正记录
		ComntMsgMis.YFFMISMSG_DY_REVER 			rever_req = new ComntMsgMis.YFFMISMSG_DY_REVER();
		ComntMsgMis.YFFMISMSG_DY_REVER_RESULT  	rever_result = new ComntMsgMis.YFFMISMSG_DY_REVER_RESULT();
		
		rever_req.rtu_id = p_rtu_id;
		rever_req.sub_id = p_mp_id;
		rever_req.date 	 = p_opdate;
		rever_req.time 	 = p_optime;
		
		//被冲正记录日期
		rever_req.last_date = CommBase.strtoi(record_lastrever.get("op_date"));
		//被冲正记录时间
		rever_req.last_time = CommBase.strtoi(record_lastrever.get("op_time"));
		//更新标志
		rever_req.updateflag = update_flag;
		//操作员编号
		ComntFunc.string2Byte(CommFunc.getYffMan().getName(), rever_req.czybh);
		ComntFunc.byteStringCutW(rever_req.czybh, ComntOperMis.OPERMAN_STRLEN);
		//冲正交易流水号
		ComntFunc.string2Byte(record_rever.get("wasteno"), rever_req.czjylsh);
		//原交易流水号
		ComntFunc.string2Byte(record_rever.get("rewasteno"), rever_req.yjylsh);
		//银行账务日期
		rever_req.yhzwrq = CommBase.strtoi(record_rever.get("op_date"));
		
		dyOperStr = SDDef.EMPTY;
		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMis.DYRever(user_name, i_user_data1, i_user_data2, rtu_para, p_mp_id, 
									rever_req, rever_result, task_result_detail, err_str_1);		

		boolean rev_ok_flag = false;
		if (ret_val) {
			if(rever_result.retcode == ComntMsgMis.YFFMIS_MISOP_OKNO) {
				rev_ok_flag = true;
			}
		}
		else {
			Map<String, String> map_rev_record = DBOperMis.loadOneJYffMisReverRecord(p_opdate, p_wasteno);
			if ((map_rev_record != null) && 
				(CommBase.strtoi(map_rev_record.get("error_no")) == ComntMsgMis.YFFMIS_MISOP_OKNO)) {
				rev_ok_flag = true;
			}
		}

		if (rev_ok_flag) {
			//更新本地数据库
			DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, p_opdate, p_optime, 
									 SDDef.YFF_OPTYPE_REVER, true, rev_ok_flag);
		}

		if (rev_ok_flag == false) {
			if (rever_result.retcode == 0) {
				misResult = "上传冲正记录失败, 错误信息:" + err_str_1;
			}
			else {
				misResult = "上传冲正记录失败, 错误信息:" + err_str_1 + " \nMIS错误信息:" + 
							rever_result.retcode + ": " +
							ComntOperMis.GetMisErrStr(rever_result.retcode); 
			}
		}
		else {
			misResult = "MIS冲正成功";
		}
		
		return rev_ok_flag ? SDDef.SUCCESS : SDDef.FAIL;		
	}

	//*********************************************河北MIS结束*************************************************
	
	//20130411 zhp
	//*********************************************河南MIS开始*************************************************

	//河南低压操作-查询缴费
	private String MIS_DyQueryPower_HN(String user_name, ComntParaBase.RTU_PARA rtu_para, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {

		//useflag 首次初始化为 TRUE
		misUseflag = "true";
		misOkflag  = "false";
		misResult  = SDDef.EMPTY;

		if (dyOperStr != null) dyOperStr = dyOperStr.trim();
		if (dyOperStr == null || dyOperStr.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			misResult = "错误的请求参数";
			return SDDef.FAIL;
		}
		
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID

		JSONObject json = JSONObject.fromObject(dyOperStr);		
		
		int     p_rtu_id = CommBase.strtoi(json.getString("rtu_id"));
		short   p_mp_id = (short)CommBase.strtoi(json.getString("mp_id"));
		//用户编号
		String  p_consno = json.getString("yhbh");
		
		ComntMsgMisHN.YFFMISMSG_DY_QUERYPOWER_HN 		 power_query  = new ComntMsgMisHN.YFFMISMSG_DY_QUERYPOWER_HN();
		ComntMsgMisHN.YFFMISMSG_DY_QUERYPOWER_RESULT_HN  power_result = new ComntMsgMisHN.YFFMISMSG_DY_QUERYPOWER_RESULT_HN();
		
		power_query.rtu_id = p_rtu_id;
		power_query.sub_id = p_mp_id;
		
		//用户编号
		ComntFunc.string2Byte(p_consno, power_query.yhbh);
		//---------------------------20140325----------------------------------------
		//操作员编号
		ComntFunc.string2Byte(CommFunc.getYffMan().getName(), power_query.czybh);
		ComntFunc.byteStringCutW(power_query.czybh, ComntOperMisHN.OPERMAN_STRLEN);
		
		//2013-08-15增加 非金融机构账务日期
		power_query.bank_acct_date = nowDate();
		
		dyOperStr = SDDef.EMPTY;
		
		boolean ret_val = false;
		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMisHN.DYQueryPower(user_name, i_user_data1, i_user_data2, rtu_para, p_mp_id, 
											power_query, power_result, task_result_detail, err_str_1);
	
		if (ret_val) {
			misUseflag 	= "true";
			misOkflag	= "true";
			
			//使用MIS 但查询失败
			if(power_result.retcode != ComntMsgMisHN.YFFMIS_MISOP_OKNO){
				misOkflag = "false";
				//连接MIS失败
				if(power_result.retcode == 0) {
					misResult = "MIS用户查询失败, \n错误信息:" + err_str_1 + " \n不能操作...";
				}
				//MIS返回错误
				else{
					misResult = "MIS用户查询失败, \n错误信息:" + err_str_1 + " , \nMIS错误信息:" + 
								ComntOperMisHN.GetMisErrStr(power_result.retcode) + " \n不能操作...";
				}
			}
			else {
				dyOperStr = power_result.toJsonString();
			}
		}
		else {
			if(task_result_detail.errcode == ComntMsgMisHN.YFFMIS_ERR_MISNOUSE) {
				misUseflag = "false";
			}
			else {
				misResult = "MIS通讯错误";
			}
		}
	
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;		
	}

	//河南低压操作-缴费记录上传
	private String MIS_DyPay_HN(String user_name, ComntParaBase.RTU_PARA rtu_para, 
						  ComntParaBase.OpDetailInfo op_detail, StringBuffer err_str_1, 
						  ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		
		misResult  = SDDef.EMPTY;
		
		if (dyOperStr != null) dyOperStr = dyOperStr.trim();
		if (dyOperStr == null || dyOperStr.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			misResult = "错误的请求参数";			
			return SDDef.FAIL;
		}
		
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID

		boolean ret_val = false;		
		JSONObject json = JSONObject.fromObject(dyOperStr);		
		
		int 	p_rtu_id 	= CommBase.strtoi(json.getString("rtu_id"));
		short 	p_mp_id 	= (short)CommBase.strtoi(json.getString("mp_id"));
		int     p_date   	= CommBase.strtoi(json.getString("date"));
		int     p_time   	= CommBase.strtoi(json.getString("time"));
		byte    p_optype	= (byte)CommBase.strtoi(json.getString("op_type"));
		//用户编号
		String  p_consno 	= json.getString("yhbh");
		//交易流水号
		String  p_wasteno 	= json.getString("jylsh");
		//缴费金额
		double  p_paymoney  = CommBase.strtof(json.getString("jfje"));
		//核算单位
		String hsdwbh		= json.getString("hsdwbh");
		//电力资金编号（终端批次号）
		String batch_no     = json.getString("batch_no");
		
		//没有缴费,不用于MIS通讯
		if (p_paymoney <= 0.01) {
			misResult = "没有缴费,不用传MIS缴费记录...";
			return SDDef.SUCCESS;
		}
		
		//发送
		ComntMsgMisHN.YFFMISMSG_DY_PAY_HN pay_req = new ComntMsgMisHN.YFFMISMSG_DY_PAY_HN();
		ComntMsgMisHN.YFFMISMSG_DY_PAY_RESULT_HN  pay_result = new ComntMsgMisHN.YFFMISMSG_DY_PAY_RESULT_HN();
		
		pay_req.rtu_id 	= p_rtu_id;
		pay_req.sub_id 	= p_mp_id;
		pay_req.date 	= p_date;
		pay_req.time 	= p_time;
		
		//更新标志
		pay_req.updateflag = 0;
		//操作员编号
		ComntFunc.string2Byte(CommFunc.getYffMan().getName(), pay_req.czybh);
		ComntFunc.byteStringCutW(pay_req.czybh, ComntOperMisHN.OPERMAN_STRLEN);
		//交易流水号
		ComntFunc.string2Byte(p_wasteno, pay_req.jylsh);
		//用户编号
		ComntFunc.string2Byte(p_consno, pay_req.yhbh);
		//缴费金额
		pay_req.jfje = p_paymoney;
		//银行账务日期
		pay_req.yhzwrq = CommBase.strtoi(json.getString("yhzwrq"));
		//缴费笔数
		pay_req.jfbs = CommBase.strtoi(json.getString("jfbs"));
		//核算单位
		ComntFunc.string2Byte(hsdwbh, pay_req.hsdwbh);
		//电力资金编号（终端批次号）
		ComntFunc.string2Byte(batch_no, pay_req.batch_no);
		
		int len = CommBase.strtoi(json.getString("vlength"));
		pay_req.details_vect = new ComntMsgMisHN.YFFMISMSG_DY_PAY_HN.POWERPAY_DETAIL[len];	
		for(int j = 0; j < len; j++){
			pay_req.details_vect[j] = new ComntMsgMisHN.YFFMISMSG_DY_PAY_HN.POWERPAY_DETAIL();	
			
			//ComntFunc.string2Byte(json.getString("yhbh" + j), pay_req.details_vect[j].yhbh);
			ComntFunc.string2Byte(json.getString("ysbs" + j), pay_req.details_vect[j].ysbs);
			pay_req.details_vect[j].dfny = Integer.parseInt(json.getString("dfny" + j));
			pay_req.details_vect[j].dfje = Double.parseDouble(json.getString("dfje" + j));
			pay_req.details_vect[j].wyjje = Double.parseDouble(json.getString("wyjje" + j));
			pay_req.details_vect[j].bcssje = Double.parseDouble(json.getString("bcssje" + j));
			
		}

		dyOperStr = SDDef.EMPTY;
		
		//上传缴费记录
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMisHN.DYPay(user_name, i_user_data1, i_user_data2, rtu_para, p_mp_id, 
											pay_req, pay_result, task_result_detail, err_str_1);

		boolean payok_flag = false;
		
		if (ret_val) {
			//上传成功
			if(pay_result.retcode == ComntMsgMisHN.YFFMIS_MISOP_OKNO) {
				payok_flag = true;
				
				dyOperStr = pay_result.toJsonString();
			}
		}
		//返回错误 读数据库
		else {
			//容错处理
			Map<String, String> map = DBOperMis.loadOneJYffMisPayRecord(/*p_rtu_id, p_mp_id, */p_date, p_wasteno);
			if ((map != null) && 
				(CommBase.strtoi(map.get("error_no")) == ComntMsgMisHN.YFFMIS_MISOP_OKNO)) {
				payok_flag = true;
			}
		}
		
		//更新本地数据库
		DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, p_date, p_time, p_optype, 
								  true, payok_flag);
		
		if(payok_flag == false) {
			//连接MIS失败
			if(pay_result.retcode == 0) {
				misResult = "上传缴费记录失败, \n错误信息:" + err_str_1 + " \n不能操作...";
			}
			//MIS返回错误
			else{
				misResult = "上传缴费记录失败, \n错误信息:" + err_str_1 + " , \nMIS错误信息:" + 
				pay_result.retcode + ": " + ComntOperMisHN.GetMisErrStr(pay_result.retcode) + " \n请转入【补MIS缴费记录】页面...";
			}
		}
		else {
			misResult = "上传缴费记录成功。";
		}
		
		misOkflag = payok_flag ? "true" : "false";
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}
		
	//河南低压操作-冲正
	private String MIS_DYRever_HN(String user_name, ComntParaBase.RTU_PARA rtu_para, 
								ComntParaBase.OpDetailInfo op_detail, StringBuffer err_str_1, 
								ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {

		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;
		
		misResult = SDDef.EMPTY;
		
		if (dyOperStr != null) dyOperStr = dyOperStr.trim();
		if (dyOperStr == null || dyOperStr.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			misResult = "错误的请求参数";
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(dyOperStr);
		
		int 	p_rtu_id 	= CommBase.strtoi(json.getString("rtu_id"));
		short 	p_mp_id 	= (short)CommBase.strtoi(json.getString("mp_id"));
		int     p_last_date = CommBase.strtoi(json.getString("last_date"));
		int     p_last_time = CommBase.strtoi(json.getString("last_time"));
		int     p_date   	= CommBase.strtoi(json.getString("date"));
		int     p_time   	= CommBase.strtoi(json.getString("time"));
		//用户编号
		String  p_consno    = json.getString("yhbh");
		//缴费金额
		double  p_paymoney  = CommBase.strtof(json.getString("pay_money"));
		//冲正交易流水号
		String  p_wasteno   = json.getString("czjylsh");
		//原交易流水号
		String  p_rewasteno = json.getString("yjylsh");
		
		//读取被冲正的缴费记录
		Map<String, String> record_lastpay = DBOperMis.loadOneJYffOpRecord(p_rtu_id, p_mp_id, p_last_date, p_last_time, 
				SDDef.YFF_OPTYPE_ADDRES , SDDef.YFF_OPTYPE_RESTART , SDDef.YFF_OPTYPE_PAY , SDDef.YFF_OPTYPE_CHANGEMETER );
		
		if(record_lastpay == null) {
			misResult = "MIS冲正, 读取被冲正记录失败, 请手工在MIS中冲正...";
			return SDDef.FAIL;
		}
		
		//读取冲正记录
		Map<String, String> record_rever = DBOperMis.loadOneJYffOpRecord(p_rtu_id, p_mp_id, p_date, p_time, 
											SDDef.YFF_OPTYPE_REVER, -1, -1, -1);
		if(record_rever == null) {
			misResult = "MIS冲正, 读取被冲正记录失败, 请手工在MIS中冲正...";
			return SDDef.FAIL;
		}
		
		//读取新缴费记录
		Map<String, String> record_thispay = DBOperMis.loadOneJYffOpRecord(p_rtu_id, p_mp_id, p_date, p_time, 
											SDDef.YFF_OPTYPE_PAY, -1, -1, -1);
		if(record_thispay == null) {
			misResult = "MIS冲正, 读取被冲正记录失败, 请手工在MIS中冲正...";
			return SDDef.FAIL;
		}
		
		if(!record_lastpay.get("wasteno").equals(record_rever.get("rewasteno"))){
			misResult = "MIS冲正, 流水号匹配错误, 请手工在MIS中操作冲正...";
			return SDDef.FAIL;
		}
		
		//把被冲正的缴费记录的MIS标志清掉
		DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, p_last_date, p_last_time, 
								 CommBase.strtoi(record_lastpay.get("op_type")), 
								 false, false);
		
		//把缴费数据库记录的MIS置上
		if (p_paymoney > 0.01) {
			DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, p_date, p_time, SDDef.YFF_OPTYPE_PAY, true, false);
		}

		//上传冲正记录
		ComntMsgMisHN.YFFMISMSG_DY_REVER_HN 		   rever_req    = new ComntMsgMisHN.YFFMISMSG_DY_REVER_HN();
		ComntMsgMisHN.YFFMISMSG_DY_REVER_RESULT_HN  rever_result = new ComntMsgMisHN.YFFMISMSG_DY_REVER_RESULT_HN();
		
		rever_req.rtu_id = p_rtu_id;
		rever_req.sub_id = p_mp_id;
		rever_req.date 	 = p_date;
		rever_req.time 	 = p_time;
		
		//被冲正记录日期
		rever_req.last_date = p_last_date;
		//被冲正记录时间
		rever_req.last_time = p_last_time;
		//更新标志
		rever_req.updateflag = 0;
		//操作员编号
		ComntFunc.string2Byte(CommFunc.getYffMan().getName(), rever_req.czybh);
		ComntFunc.byteStringCutW(rever_req.czybh, ComntOperMisHN.OPERMAN_STRLEN);
		//冲正交易流水号
		//ComntFunc.string2Byte(p_wasteno, rever_req.czjylsh);//从库中取
		ComntFunc.string2Byte(record_rever.get("wasteno"), rever_req.czjylsh);
		//原交易流水号
		ComntFunc.string2Byte(p_rewasteno, rever_req.yjylsh);
		//银行账务日期
		rever_req.yhzwrq = CommBase.strtoi(json.getString("yhzwrq"));
		
		//用户编号
		ComntFunc.string2Byte(p_consno, rever_req.yhbh);

		dyOperStr = SDDef.EMPTY;
		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMisHN.DYRever(user_name, i_user_data1, i_user_data2, rtu_para, p_mp_id, 
									rever_req, rever_result, task_result_detail, err_str_1);// 			

		boolean rev_ok_flag = false;
		if (ret_val) {
			if(rever_result.retcode == ComntMsgMisHN.YFFMIS_MISOP_OKNO) {
				rev_ok_flag = true;
			}
		}
		else {
			Map<String, String> map_rev_record = DBOperMis.loadOneJYffMisReverRecord(p_date, p_rewasteno);
			if ((map_rev_record != null) && 
				(CommBase.strtoi(map_rev_record.get("error_no")) == ComntMsgMisHN.YFFMIS_MISOP_OKNO)) {
				rev_ok_flag = true;
			}
		}

		//更新本地数据库
		DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, p_date, p_time, 
								 SDDef.YFF_OPTYPE_REVER, true, rev_ok_flag);
		
		if (!rev_ok_flag) {
			//连接MIS失败
			if(rever_result.retcode == 0) {
				misResult = "上传冲正记录失败, 错误信息:" + err_str_1 + " \n请转入【补MIS冲正记录】页面...";
			}
			//MIS返回错误
			else{
				misResult = "上传冲正记录失败, \n错误信息:" + err_str_1 + " , \nMIS错误信息:" + 
				rever_result.retcode + ": " + ComntOperMisHN.GetMisErrStr(rever_result.retcode) + " \n请转入【补MIS冲正记录】页面...";
			}
			return SDDef.FAIL;
		}
		
		//未缴费
		if(p_paymoney < 0.01) {
			misResult = "冲正成功...";
			return rev_ok_flag ? SDDef.SUCCESS : SDDef.FAIL;
		}
		
		//暂停1s
		CommFunc.waitMsg(1000);
		
		//MIS查询
		ComntMsgMisHN.YFFMISMSG_DY_QUERYPOWER_HN power_req = new ComntMsgMisHN.YFFMISMSG_DY_QUERYPOWER_HN();
		
		power_req.rtu_id = p_rtu_id;
		power_req.sub_id = p_mp_id;
		//用户编号
		ComntFunc.string2Byte(p_consno, power_req.yhbh);
		
		//---------------------------20140325----------------------------------------
		//操作员编号
		ComntFunc.string2Byte(CommFunc.getYffMan().getName(), power_req.czybh);
		ComntFunc.byteStringCutW(power_req.czybh, ComntOperMisHN.OPERMAN_STRLEN);

		ComntMsgMisHN.YFFMISMSG_DY_QUERYPOWER_RESULT_HN  power_result = new ComntMsgMisHN.YFFMISMSG_DY_QUERYPOWER_RESULT_HN();		
		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMisHN.DYQueryPower(user_name, i_user_data1, i_user_data2, rtu_para, p_mp_id, 
				power_req, power_result, task_result_detail, err_str_1);
		
		if (ret_val == false || power_result.retcode != ComntMsgMisHN.YFFMIS_MISOP_OKNO) {
			if (task_result_detail.errcode == 0) {
				misResult = "MIS用户查询失败, \n错误信息:" + err_str_1 + " \n请转入【补MIS缴费记录】页面...";
			}
			else {
				misResult = "MIS用户查询失败, \n错误信息:" + err_str_1 + ", \nMIS错误信息:" + 
				task_result_detail.errcode + ": " + ComntOperMisHN.GetMisErrStr(task_result_detail.errcode) + " \n请转入【补MIS缴费记录】页面...";
			}

			//更新缴费本地数据库
			DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, 
										CommBase.strtoi(record_thispay.get("op_date")), 
										CommBase.strtoi(record_thispay.get("op_time")), 
									   SDDef.YFF_OPTYPE_PAY, true, false);

			return SDDef.FAIL;
		}
		
		//暂停1s
		CommFunc.waitMsg(1000);
		
		//缴费
		ComntMsgMisHN.YFFMISMSG_DY_PAY_HN pay_req = new ComntMsgMisHN.YFFMISMSG_DY_PAY_HN();
		
		pay_req.rtu_id = p_rtu_id;
		pay_req.sub_id = p_mp_id;
		pay_req.date = CommBase.strtoi(record_thispay.get("op_date"));
		pay_req.time = CommBase.strtoi(record_thispay.get("op_time"));
		pay_req.updateflag = 0;
		//操作员编号
		ComntFunc.string2Byte(CommFunc.getYffMan().getName(), pay_req.czybh);
		ComntFunc.byteStringCutW(pay_req.czybh, ComntOperMisHN.OPERMAN_STRLEN);
		
		//交易流水号
		ComntFunc.string2Byte(record_thispay.get("wasteno"), pay_req.jylsh);
		//用户编号
		ComntFunc.string2Byte(p_consno, pay_req.yhbh);
		//缴费金额
		pay_req.jfje = p_paymoney;
		//银行账务日期
		pay_req.yhzwrq = CommBase.strtoi(record_thispay.get("op_date"));
		//缴费笔数
		pay_req.jfbs = power_result.jezbs;
		//核算单位
		String hsdwbh		= json.getString("hsdwbh");
		ComntFunc.string2Byte(hsdwbh, pay_req.hsdwbh);
		//电力资金编号（终端批次号）
		String batch_no     = json.getString("batch_no");
		ComntFunc.string2Byte(batch_no, pay_req.batch_no);
		
		//欠费信息
		pay_req.details_vect = new ComntMsgMisHN.YFFMISMSG_DY_PAY_HN.POWERPAY_DETAIL[pay_req.jfbs];
		for(int j = 0; j < pay_req.jfbs; j++){
			pay_req.details_vect[j] = new ComntMsgMisHN.YFFMISMSG_DY_PAY_HN.POWERPAY_DETAIL();
			ComntFunc.arrayCopy(pay_req.details_vect[j].ysbs, power_result.details_vect[j].ysbs);
		}

		//上传缴费记录
		ComntMsgMisHN.YFFMISMSG_DY_PAY_RESULT_HN pay_result = new ComntMsgMisHN.YFFMISMSG_DY_PAY_RESULT_HN();
		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMisHN.DYPay(user_name, i_user_data1, i_user_data2, rtu_para, p_mp_id, 
									pay_req, pay_result, task_result_detail, err_str_1);
		
		boolean pay_okflag = false;
		if (ret_val == false) {
			Map<String, String> map = DBOperMis.loadOneJYffMisPayRecord(/*p_rtu_id, p_mp_id,*/ 
									  CommBase.strtoi(record_thispay.get("op_date")), 
									  record_thispay.get("wasteno"));
			if ((map != null) && 
				(CommBase.strtoi(map.get("error_no")) == ComntMsgMisHN.YFFMIS_MISOP_OKNO)) {
				pay_okflag = true;
			}
		}
		//返回错误 读数据库
		else {
			//上传成功
			if(pay_result.retcode == ComntMsgMisHN.YFFMIS_MISOP_OKNO) {
				pay_okflag = true;
			}
		}
		
		//更新本地数据库
		DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, 
								  CommBase.strtoi(record_thispay.get("op_date")),
								  CommBase.strtoi(record_thispay.get("op_time")),
								  SDDef.YFF_OPTYPE_PAY, true, pay_okflag);
		
		if (!pay_okflag) {
			if (pay_result.retcode == 0) {
				misResult = "上传缴费记录失败, 错误信息:" + err_str_1 + " \n请转入【补MIS缴费记录】页面";
			}
			else {
				misResult = "上传缴费记录失败, 错误信息:" + err_str_1 + " \nMIS错误信息:" + 
				pay_result.retcode + ": " + ComntOperMisHN.GetMisErrStr(pay_result.retcode) + 
							" \n请转入【补MIS缴费记录】页面 "; 
			}			
		}
		else {
			misResult = "MIS冲正成功...";
		}
		
		return pay_okflag ? SDDef.SUCCESS : SDDef.FAIL;		
	}
	
	//河南低压操作-检查缴费
	private String MIS_DYCheckPay_HN(String user_name, ComntParaBase.RTU_PARA rtu_para, 
								ComntParaBase.OpDetailInfo op_detail, StringBuffer err_str_1, 
								ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {

		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;
		
		misResult = SDDef.EMPTY;
		
		if (dyOperStr != null) dyOperStr = dyOperStr.trim();
		if (dyOperStr == null || dyOperStr.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			misResult = "错误的请求参数";
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(dyOperStr);

		int 	p_rtu_id 	= CommBase.strtoi(json.getString("rtu_id"));
		short 	p_mp_id 	= (short)CommBase.strtoi(json.getString("mp_id"));
		int     p_opdate   	= CommBase.strtoi(json.getString("op_date"));
		int     p_optime   	= CommBase.strtoi(json.getString("op_time"));
		byte    p_optype   	= (byte)CommBase.strtoi(json.getString("op_type"));
		//用户编号
		String  p_consno    = json.getString("yhbh");
		double  p_paymoney  = CommBase.strtof(json.getString("pay_money"));
		//流水号
		String  p_wasteno	= json.getString("lsh");
		
		//当前年月日
		ComntTime.TM cur_tm = ComntTime.localCTime(ComntTime.cTime());
		int cur_ymd = (cur_tm.year + 1900) * 10000 + (cur_tm.month + 1) * 100 + cur_tm.mday;
		
		if (p_opdate != cur_ymd) {
			misResult = "不能补交非今天的记录!";
			return SDDef.FAIL;
		}
		
		//读取本地缴费操作记录
		Map<String, String> record_pay = DBOperMis.loadOneJYffOpRecord(p_rtu_id, p_mp_id, p_opdate, p_optime, 
				p_optype , -1, -1, -1);
		
		if(record_pay == null) {
			misResult = "读取本地记录失败";
			return SDDef.FAIL;
		}
		if(CommBase.strtoi(record_pay.get("up186_flag")) == 0) {
			misResult = "此记录不用上传MIS";
			return SDDef.FAIL;
		}
		if(CommBase.strtoi(record_pay.get("checkpay_flag")) != 0) {
			misResult = "此记录已成功上传MIS";
			return SDDef.FAIL;
		}
		
		//读取本地冲正操作记录
		Map<String, String> record_rever = DBOperMis.loadOneJYffOpRecord(p_rtu_id, p_mp_id, p_opdate, p_optime, 
											SDDef.YFF_OPTYPE_REVER, -1, -1, -1);
		
		if (record_rever != null && 
		    CommBase.strtoi(record_rever.get("up186_flag")) != 0 && 
		    CommBase.strtoi(record_rever.get("checkpay_flag")) == 0) {
			misResult = "此用户存在未提交的冲正记录, 请先冲正";
			return SDDef.FAIL;
		}
		
		byte update_flag = 0;
		
		//读取MIS缴费操作记录
		Map<String, String> mis_payrcd = DBOperMis.loadOneJYffMisPayRecord(/*p_rtu_id, p_mp_id, */p_opdate, p_wasteno);
		if ((mis_payrcd != null) && 
			(ComntMsgMisHN.YFFMIS_MISOP_OKNO == CommBase.strtoi(mis_payrcd.get("error_no")))) {
			//更新本地数据库记录
			DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, p_opdate, p_optime, p_optype, true, true);
			return SDDef.SUCCESS;
		}
		
		if (mis_payrcd != null) {
			update_flag = 1;
		}
		
		//MIS查询
		ComntMsgMisHN.YFFMISMSG_DY_QUERYPOWER_HN power_req = new ComntMsgMisHN.YFFMISMSG_DY_QUERYPOWER_HN();
		ComntMsgMisHN.YFFMISMSG_DY_QUERYPOWER_RESULT_HN  power_result = new ComntMsgMisHN.YFFMISMSG_DY_QUERYPOWER_RESULT_HN();
		
		power_req.rtu_id = p_rtu_id;
		power_req.sub_id = p_mp_id;
		//用户编号
		ComntFunc.string2Byte(p_consno, power_req.yhbh);
		
		//---------------------------20140325----------------------------------------
		//操作员编号
		ComntFunc.string2Byte(CommFunc.getYffMan().getName(), power_req.czybh);
		ComntFunc.byteStringCutW(power_req.czybh, ComntOperMisHN.OPERMAN_STRLEN);
		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMisHN.DYQueryPower(user_name, i_user_data1, i_user_data2, rtu_para, p_mp_id, 
				power_req, power_result, task_result_detail, err_str_1);

		if (ret_val == false || power_result.retcode != ComntMsgMisHN.YFFMIS_MISOP_OKNO) {
			if (task_result_detail.errcode == 0) {
				misResult = "MIS用户查询失败, \n错误信息:" + err_str_1 + " ...";
			}
			else {
				misResult = "MIS用户查询失败, \n错误信息:" + err_str_1 + ", \nMIS错误信息:" + 
				task_result_detail.errcode + ": " + ComntOperMisHN.GetMisErrStr(task_result_detail.errcode) + " ...";
			}

			return SDDef.FAIL;
		}
		
		//暂停1s
		CommFunc.waitMsg(1000);
		
		//缴费
		ComntMsgMisHN.YFFMISMSG_DY_PAY_HN pay_req = new ComntMsgMisHN.YFFMISMSG_DY_PAY_HN();
		ComntMsgMisHN.YFFMISMSG_DY_PAY_RESULT_HN pay_result = new ComntMsgMisHN.YFFMISMSG_DY_PAY_RESULT_HN();
		
		pay_req.rtu_id 	= p_rtu_id;
		pay_req.sub_id 	= p_mp_id;
		pay_req.date 	= p_opdate;
		pay_req.time 	= p_optime;
		pay_req.updateflag = update_flag;
		//操作员编号
		ComntFunc.string2Byte(CommFunc.getYffMan().getName(), pay_req.czybh);
		ComntFunc.byteStringCutW(pay_req.czybh, ComntOperMisHN.OPERMAN_STRLEN);
		
		//交易流水号
		ComntFunc.string2Byte(p_wasteno, pay_req.jylsh);
		//用户编号
		ComntFunc.string2Byte(p_consno, pay_req.yhbh);
		//缴费金额
		pay_req.jfje = p_paymoney;
		//银行账务日期
		pay_req.yhzwrq = p_opdate;
		//缴费笔数
		pay_req.jfbs = power_result.jezbs;
		//电力资金编号（终端批次号）
		pay_req.batch_no = power_result.batch_no;
		 //核算单位
		pay_req.hsdwbh = power_result.hsdwbh;
		//欠费信息
		pay_req.details_vect = new ComntMsgMisHN.YFFMISMSG_DY_PAY_HN.POWERPAY_DETAIL[pay_req.jfbs];
		for(int j = 0; j < pay_req.jfbs; j++){
			pay_req.details_vect[j] = new ComntMsgMisHN.YFFMISMSG_DY_PAY_HN.POWERPAY_DETAIL();
			
			//ComntFunc.arrayCopy(pay_req.details_vect[j].yhbh, power_result.details_vect[j].yhbh);
			ComntFunc.arrayCopy(pay_req.details_vect[j].ysbs, power_result.details_vect[j].ysbs);
		}

		//上传缴费记录		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMisHN.DYPay(user_name, i_user_data1, i_user_data2, rtu_para, p_mp_id, 
									pay_req, pay_result, task_result_detail, err_str_1);
		
		boolean pay_okflag = false;
		if (ret_val == false) {
			Map<String, String> map = DBOperMis.loadOneZYffMisPayRecord(/*p_rtu_id, p_mp_id,*/ 
									  p_opdate, p_wasteno);
			if ((map != null) && 
				(CommBase.strtoi(map.get("error_no")) == ComntMsgMisHN.YFFMIS_MISOP_OKNO)) {
				pay_okflag = true;
			}
		}
		//返回错误 读数据库
		else {
			//上传成功
			if(pay_result.retcode == ComntMsgMisHN.YFFMIS_MISOP_OKNO) {
				pay_okflag = true;
			}
		}
		
		//更新本地数据库
		if (pay_okflag) {
			DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, p_opdate, p_optime,
									  SDDef.YFF_OPTYPE_PAY, true, true);
		}
		
		if (pay_okflag == false) {
			if (pay_result.retcode == 0) {
				misResult = "上传缴费记录失败, 错误信息:" + err_str_1;
			}
			else {
				misResult = "上传缴费记录失败, 错误信息:" + err_str_1 + " \nMIS错误信息:" + 
				pay_result.retcode + ": " +ComntOperMisHN.GetMisErrStr(pay_result.retcode); 
			}
		}
		else {
			misResult = "MIS缴费成功";
		}
		
		return pay_okflag ? SDDef.SUCCESS : SDDef.FAIL;				
	}	
	
	//河南低压操作-检查冲正
	private String MIS_DYCheckRever_HN(String user_name, ComntParaBase.RTU_PARA rtu_para, 
								ComntParaBase.OpDetailInfo op_detail, StringBuffer err_str_1, 
								ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {

		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;		
		misResult = SDDef.EMPTY;		
		
		if (dyOperStr != null) dyOperStr = dyOperStr.trim();
		if (dyOperStr == null || dyOperStr.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			misResult = "错误的请求参数";
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(dyOperStr);
	
		int 	p_rtu_id 	= CommBase.strtoi(json.getString("rtu_id"));
		short 	p_mp_id 	= (short)CommBase.strtoi(json.getString("mp_id"));
		int     p_opdate   	= CommBase.strtoi(json.getString("op_date"));
		int     p_optime   	= CommBase.strtoi(json.getString("op_time"));
		byte    p_optype   	= (byte)CommBase.strtoi(json.getString("op_type"));
		//用户编号
		//String  p_consno    = json.getString("yhbh");
		//double  p_paymoney  = CommBase.strtoi(json.getString("pay_money"));
		//流水号
		String  p_wasteno	= json.getString("lsh");
		//被冲正流水号
		String  p_rewasteno = json.getString("bczlsh");
		
		//当前年月日
		ComntTime.TM cur_tm = ComntTime.localCTime(ComntTime.cTime());
		int cur_ymd = (cur_tm.year + 1900) * 10000 + (cur_tm.month + 1) * 100 + cur_tm.mday;
		
		if (p_opdate != cur_ymd) {
			misResult = "不能冲正非今天的记录!";
			return SDDef.FAIL;
		}
	
		//读取本地冲正操作记录
		Map<String, String> record_rever = DBOperMis.loadOneJYffOpRecord(p_rtu_id, p_mp_id, p_opdate, p_optime, 
				SDDef.YFF_OPTYPE_REVER , -1, -1, -1);
		
		if(record_rever == null) {
			misResult = "读取本地记录失败";
			return SDDef.FAIL;
		}
		if(CommBase.strtoi(record_rever.get("up186_flag")) == 0) {
			misResult = "此记录不用上传MIS";
			return SDDef.FAIL;
		}
		if(CommBase.strtoi(record_rever.get("checkpay_flag")) != 0) {
			misResult = "此记录已成功上传MIS";
			return SDDef.FAIL;
		}
		
		//读取本地被冲正操作记录
		Map<String, String> record_lastrever = DBOperMis.loadOneJYffOpRecordWasteNo(p_rtu_id, p_mp_id, p_opdate, p_rewasteno);
		if (record_lastrever == null) {
			misResult = "读取本地被冲正记录失败.";
			return SDDef.FAIL;
		}
		
		//读取MIS冲正操作记录
		Map<String, String> mis_reverrcd = DBOperMis.loadOneJYffMisReverRecord(/*p_rtu_id, p_mp_id, */p_opdate, p_wasteno);
		if ((mis_reverrcd != null) && 
			(ComntMsgMisHN.YFFMIS_MISOP_OKNO == CommBase.strtoi(mis_reverrcd.get("error_no")))) {
			//更新本地数据库记录
			DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, p_opdate, p_optime, p_optype, true, true);
			misResult = "冲正成功.";
			return SDDef.SUCCESS;
		}
		
		byte update_flag = 0;		
		if (mis_reverrcd != null) {
			update_flag = 1;
		}
		
		//容错处理 20120421
		//misrever 存在记录 
		//mispay无记录 说明被冲正记录未向186传过 故 也不用走冲正 
		//mispay 未成功 服务器端处理不合适
		//读取MIS缴费操作记录
		Map<String, String> mis_payrcd = DBOperMis.loadOneJYffMisPayRecord(/*p_rtu_id, p_mp_id, */p_opdate, p_rewasteno);
		//无记录不用再冲正
		if (mis_payrcd == null) {
			//更新本地数据库记录
			DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, p_opdate, p_optime, p_optype, true, true);
			misResult = "冲正成功.";
			return SDDef.SUCCESS;
		}
		
		//hzhw 20130822
		//if (CommBase.strtoi(record_lastrever.get("up186_flag")) == 0) {
			//更新本地数据库记录
		//	DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, p_opdate, p_optime, p_optype, true, true);
		//	misResult = "冲正成功.";
		//	return SDDef.SUCCESS;
		//}
		//20120421 end this 可能还会有点问题？？
		
		//上传冲正记录
		ComntMsgMisHN.YFFMISMSG_DY_REVER_HN 			rever_req = new ComntMsgMisHN.YFFMISMSG_DY_REVER_HN();
		ComntMsgMisHN.YFFMISMSG_DY_REVER_RESULT_HN  	rever_result = new ComntMsgMisHN.YFFMISMSG_DY_REVER_RESULT_HN();
		
		rever_req.rtu_id = p_rtu_id;
		rever_req.sub_id = p_mp_id;
		rever_req.date 	 = p_opdate;
		rever_req.time 	 = p_optime;
		
		//被冲正记录日期
		rever_req.last_date = CommBase.strtoi(record_lastrever.get("op_date"));
		//被冲正记录时间
		rever_req.last_time = CommBase.strtoi(record_lastrever.get("op_time"));
		//更新标志
		rever_req.updateflag = update_flag;
		//操作员编号
		ComntFunc.string2Byte(CommFunc.getYffMan().getName(), rever_req.czybh);
		ComntFunc.byteStringCutW(rever_req.czybh, ComntOperMisHN.OPERMAN_STRLEN);
		//冲正交易流水号
		ComntFunc.string2Byte(record_rever.get("wasteno"), rever_req.czjylsh);
		//原交易流水号
		ComntFunc.string2Byte(record_rever.get("rewasteno"), rever_req.yjylsh);
		//银行账务日期
		rever_req.yhzwrq = CommBase.strtoi(record_rever.get("op_date"));
		
		dyOperStr = SDDef.EMPTY;
		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMisHN.DYRever(user_name, i_user_data1, i_user_data2, rtu_para, p_mp_id, 
									rever_req, rever_result, task_result_detail, err_str_1);		

		boolean rev_ok_flag = false;
		if (ret_val) {
			if(rever_result.retcode == ComntMsgMisHN.YFFMIS_MISOP_OKNO) {
				rev_ok_flag = true;
			}
		}
		else {
			Map<String, String> map_rev_record = DBOperMis.loadOneJYffMisReverRecord(p_opdate, p_wasteno);
			if ((map_rev_record != null) && 
				(CommBase.strtoi(map_rev_record.get("error_no")) == ComntMsgMisHN.YFFMIS_MISOP_OKNO)) {
				rev_ok_flag = true;
			}
		}

		if (rev_ok_flag) {
			//更新本地数据库
			DBOperMis.updateJYffPayRecord(p_rtu_id, p_mp_id, p_opdate, p_optime, 
									 SDDef.YFF_OPTYPE_REVER, true, rev_ok_flag);
		}

		if (rev_ok_flag == false) {
			if (rever_result.retcode == 0) {
				misResult = "上传冲正记录失败, 错误信息:" + err_str_1;
			}
			else {
				misResult = "上传冲正记录失败, 错误信息:" + err_str_1 + " \nMIS错误信息:" + 
				rever_result.retcode + ": " + ComntOperMisHN.GetMisErrStr(rever_result.retcode); 
			}
		}
		else {
			misResult = "MIS冲正成功";
		}
		
		return rev_ok_flag ? SDDef.SUCCESS : SDDef.FAIL;		
	}
	
	//*********************************************河南MIS结束*************************************************
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
				
				if (userop_id == ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER) {			//低压操作-MIS查询缴费
					
					ret_val = MIS_DyQueryPower(user_name, rtu_para, op_detail, err_str_1, task_result_detail);
				}
				else if (userop_id == ComntUseropDef.YFF_DYOPER_MIS_PAY) {				//低压操作-MIS缴费记录上传
					
					ret_val = MIS_DyPay(user_name, rtu_para, op_detail, err_str_1, task_result_detail);
				}
				else if (userop_id == ComntUseropDef.YFF_DYOPER_MIS_REVER) {			//低压操作-MIS冲正操作
					
					ret_val = MIS_DYRever(user_name, rtu_para, op_detail, err_str_1, task_result_detail);
				}
				else if (userop_id == ComntUseropDef.YFF_DYOPER_MIS_CHECKPAY) {			//低压操作-MIS检查缴费
					
					ret_val = MIS_DYCheckPay(user_name, rtu_para, op_detail, err_str_1, task_result_detail);
				}
				else if (userop_id == ComntUseropDef.YFF_DYOPER_MIS_CHECKREVER) {		//低压操作-MIS检查冲正
					
					ret_val = MIS_DYCheckRever(user_name, rtu_para, op_detail, err_str_1, task_result_detail);			
				}
				else {
					op_detail.addTaskInfo("ERR:未知的操作类型[" + userop_id + "]");
				}
			}
			//河南MIS add----------
			else if("HN".equals(WebConfig.provinceMisFlag)){
				if (userop_id == ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER) {			//低压操作-MIS查询缴费
					
					ret_val = MIS_DyQueryPower_HN(user_name, rtu_para, op_detail, err_str_1, task_result_detail);
				}
				else if (userop_id == ComntUseropDef.YFF_DYOPER_MIS_PAY) {				//低压操作-MIS缴费记录上传
					
					ret_val = MIS_DyPay_HN(user_name, rtu_para, op_detail, err_str_1, task_result_detail);
				}
				else if (userop_id == ComntUseropDef.YFF_DYOPER_MIS_REVER) {			//低压操作-MIS冲正操作
					
					ret_val = MIS_DYRever_HN(user_name, rtu_para, op_detail, err_str_1, task_result_detail);
				}
				else if (userop_id == ComntUseropDef.YFF_DYOPER_MIS_CHECKPAY) {			//低压操作-MIS检查缴费
					
					ret_val = MIS_DYCheckPay_HN(user_name, rtu_para, op_detail, err_str_1, task_result_detail);
				}
				else if (userop_id == ComntUseropDef.YFF_DYOPER_MIS_CHECKREVER) {		//低压操作-MIS检查冲正
					
					ret_val = MIS_DYCheckRever_HN(user_name, rtu_para, op_detail, err_str_1, task_result_detail);			
				}
				else {
					op_detail.addTaskInfo("ERR:未知的操作类型[" + userop_id + "]");
				}
			}
			else{
				result = SDDef.FAIL;
				op_detail.addTaskInfo("ERR:不支持该省份的MIS");
			}
			result 			= ret_val;
			//err_strinfo		= err_str_1.toString();
			//task_resultinfo = task_result_detail.toJsonString();	//以后再详细描述
			detailInfo = op_detail.toJsonString();
		} catch (Exception e) {
			
			CommFunc.err_log(e);
			
			result = SDDef.FAIL;
			misResult = e.getMessage();
		}
		return SDDef.SUCCESS;
	}
	
	public String cancelTask() {
		String user_name = ComntParaBase.getUserName();	
		
		int i_user_data1 = CommBase.strtoi(userData1);
		int i_user_data2 = CommBase.strtoi(userData2);

		ComntMsgProc.cancelMsg(user_name, i_user_data1, i_user_data2);
		
		result = SDDef.SUCCESS;	
		return SDDef.SUCCESS;
	}
	
	/**对账错误查询*/
	public String misErrSearch(){
		
		StringBuffer ret = new StringBuffer();
		int count = 0;
		try{
			
			JSONObject j_obj = JSONObject.fromObject(result);
			
			String sdate = j_obj.getString("sdate");
			String edate = j_obj.getString("edate");
			String czy = j_obj.getString("czy");
			String org = j_obj.getString("org");
			String err_type = j_obj.getString("err_type");
			
			int isdate = Integer.parseInt(sdate);
			int iedate = Integer.parseInt(edate);
			if(isdate > iedate) {	//终止日期比起始日期小 返回
				result = "";
				return SDDef.SUCCESS;
			}
			
			ret.append("{rows:[");
			JDBCDao j_dao = new JDBCDao();
			ResultSet rs = null;
			
			isdate /= 10000;
			iedate /= 10000;
			int year_num = iedate - isdate + 1;
			
			for (int i = 0; i < year_num; i++, isdate++) {
				String table_name = "yddataben.dbo.jyff" + isdate;
				String sql = "";
				if(err_type.equals("0")) {		//所有失败记录
					sql = "select b.rtu_id, b.mp_id, r.prot_type as prot_type, b.wasteno, b.rewasteno, "
						+ "res.cons_no,res.describe,res.address,b.op_type,b.op_date,b.op_time,b.op_man,b.pay_type,b.pay_money, "
						+ "b.othjs_money,b.zb_money,b.all_money, b.shutdown_val,b.buy_times, b.sg186_ysdw, b.up186_flag, b.checkpay_flag "
						+ "from ydparaben.dbo.conspara a, ydparaben.dbo.rtupara as r, " + table_name + " b,residentpara res,meterpara mt where r.cons_id = a.id  and r.id = b.rtu_id "
						+ "and r.id=res.rtu_id and r.id=mt.rtu_id and b.mp_id=mt.mp_id and mt.resident_id=res.id and b.op_date>="+sdate+" and b.op_date<="+edate+" and "
						+ "b.visible_flag=1 and b.up186_flag=1 and b.checkpay_flag=0";
				}
				else if(err_type.equals("1")) {	//上传失败记录
					sql = "select b.rtu_id, b.mp_id, r.prot_type as prot_type, b.wasteno, b.rewasteno, "
						+ "res.cons_no,res.describe,res.address,b.op_type,b.op_date,b.op_time,b.op_man,b.pay_type,b.pay_money, "
						+ "b.othjs_money,b.zb_money,b.all_money, b.shutdown_val,b.buy_times, b.sg186_ysdw, b.up186_flag, b.checkpay_flag "
						+ "from ydparaben.dbo.conspara a, ydparaben.dbo.rtupara as r, " + table_name + " b,residentpara res,meterpara mt where r.cons_id = a.id  and r.id = b.rtu_id "
						+ "and r.id=res.rtu_id and r.id=mt.rtu_id and b.mp_id=mt.mp_id and mt.resident_id=res.id and b.op_date>="+sdate+" and b.op_date<="+edate+" and "
						+ "b.visible_flag=1 and b.op_type <> " + SDDef.YFF_OPTYPE_REVER + " and  b.up186_flag=1 and b.checkpay_flag=0";
				}
				else if(err_type.equals("2")) {	//冲正失败记录
					sql = "select b.rtu_id, b.mp_id, r.prot_type as prot_type, b.wasteno, b.rewasteno, "
						+ "res.cons_no,res.describe,res.address,b.op_type,b.op_date,b.op_time,b.op_man,b.pay_type,b.pay_money, "
						+ "b.othjs_money,b.zb_money,b.all_money, b.shutdown_val,b.buy_times, b.sg186_ysdw, b.up186_flag, b.checkpay_flag "
						+ "from ydparaben.dbo.conspara a, ydparaben.dbo.rtupara as r, " + table_name + " b,residentpara res,meterpara mt where r.cons_id = a.id  and r.id = b.rtu_id "
						+ "and r.id=res.rtu_id and r.id=mt.rtu_id and b.mp_id=mt.mp_id and mt.resident_id=res.id and b.op_date>="+sdate+" and b.op_date<="+edate+" and "
						+ "b.visible_flag=1 and b.op_type = " + SDDef.YFF_OPTYPE_REVER + " and  b.up186_flag=1 and b.checkpay_flag=0";
				}
				
				if(!czy.isEmpty()) {
					sql += " and b.op_man like '%" + czy + "%'";
				}
				
				if(!org.equals("-1")){
					sql += " and a.org_id=" + org;
				}
				
				try{
					rs = j_dao.executeQuery(sql);
					while(rs.next()) {
						if(count > 10000)break;
						ret.append("{id:\"" + rs.getInt("rtu_id")+"_"+rs.getInt("mp_id") + "_" + count + "\",data:[" + ++count +",");
						//用户编号
						ret.append("\"" + CommBase.CheckString(rs.getString("cons_no")) + "\",");
						//用户名称
						ret.append("\"" + CommBase.CheckString(rs.getString("describe")) + "\",");
						//操作类型
						ret.append("\"" + rs.getInt("op_type") + "\",");
						//操作日期
						ret.append("\"" + CommFunc.FormatToYMD(rs.getInt("op_date")) + CommFunc.FormatToHMS(rs.getInt("op_time"), 2) + "\",");
						//缴费金额
						ret.append("\"" + rs.getString("pay_money") + "\",");
						//结算金额
						ret.append("\"" + rs.getString("othjs_money") + "\",");
						//追补金额
						ret.append("\"" + rs.getString("zb_money") + "\",");
						//总金额
						ret.append("\"" + rs.getString("all_money") + "\",");
						//购电次数
						ret.append("\"" + rs.getString("buy_times") + "\",");
						//流水号
						ret.append("\"" + rs.getString("wasteno") + "\",");
						//被冲正流水号
						ret.append("\"" + rs.getString("rewasteno") + "\",");
						//操作员
						ret.append("\"" + rs.getString("op_man") + "\",");
						//缴费方式
						ret.append("\"" + Rd.getDict(Dict.DICTITEM_PAYTYPE, rs.getInt("pay_type"))+"\",");
						//用户地址
						ret.append("\"" + CommBase.CheckString(rs.getString("address")) + "\",");
						//上传标志
						ret.append("\"" + (rs.getInt("up186_flag") == 0 ? "否" : "是") + "\",");
						//成功标志
						ret.append("\"" + (rs.getInt("checkpay_flag") == 0 ? "否" : "是") + "\",");
						
						ret.append("]},");
					}
					
					j_dao.closeRs(rs);
				}catch (Exception e) {
					continue;
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
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

	//20130815增加获取当前日期(yymmdd形式)方法,以后尽量写在一个公共类中
	public int nowDate(){
		Calendar cal = Calendar.getInstance();
		String year = cal.get(cal.YEAR)+ "";
		int month = cal.get(cal.MONTH) + 1;
		int day = cal.get(cal.DATE);
		String mm = month < 10 ? ("0"+ month) : (month + "");
		String dd = day < 10 ? ("0" + day) : (day + "");
		int date = Integer.parseInt(year + mm + dd);
		return date;
	}
	
	/**get set 方法 自动生成*/
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}

	public String getMisUseflag() {
		return misUseflag;
	}

	public void setMisUseflag(String misUseflag) {
		this.misUseflag = misUseflag;
	}

	public String getMisOkflag() {
		return misOkflag;
	}

	public void setMisOkflag(String misOkflag) {
		this.misOkflag = misOkflag;
	}

	public String getMisResult() {
		return misResult;
	}

	public void setMisResult(String misResult) {
		this.misResult = misResult;
	}

	public String getDyOperStr() {
		return dyOperStr;
	}

	public void setDyOperStr(String dyOperStr) {
		this.dyOperStr = dyOperStr;
	}


}
