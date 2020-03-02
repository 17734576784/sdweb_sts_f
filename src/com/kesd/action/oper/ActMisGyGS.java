package com.kesd.action.oper;

import java.util.Map;
import net.sf.json.JSONObject;
import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.comnt.ComntMsgMisGS;
import com.kesd.comnt.ComntMsgProc;
import com.kesd.comntpara.ComntOperMis;
import com.kesd.comntpara.ComntOperMisGS;
import com.kesd.comntpara.ComntParaBase;
import com.kesd.comntpara.ComntUseropDef;
import com.kesd.service.DBOperMis;
import com.libweb.common.CommBase;
import com.libweb.comnt.ComntFunc;
import com.libweb.comnt.ComntTime;
import com.opensymphony.xwork2.ActionSupport;

public class ActMisGyGS extends ActionSupport{

	private static final long serialVersionUID = -3770616860256454508L;
	
	private String  userData1 			= null;		//RTU_ID
	private String  userData2 			= null;		//USEROP_ID
	private String 	result 				= null;		//SUCCESS/FAIL
	private String 	detailInfo 			= null;		//返回详细信息(JSON)
	
	private String  misUseflag			= null;		//
	private String  misOkflag			= null;		//
	private String  misResult			= null;		//
		
	private String  gyOperStr			= null;		//高压操作字符串
	
	//甘肃高压操作-查询缴费
	private String MIS_GyQueryPower(String user_name, ComntParaBase.RTU_PARA rtu_para, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		//useflag 首次初始化为 TRUE
		misUseflag = "true";
		misOkflag  = "false";
		misResult  = SDDef.EMPTY;

		if (gyOperStr != null) gyOperStr = gyOperStr.trim();
		if (gyOperStr == null || gyOperStr.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			misResult = "错误的请求参数";
			return SDDef.FAIL;
		}
		
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID

		JSONObject json = JSONObject.fromObject(gyOperStr);		
		
		int     p_rtu_id = CommBase.strtoi(json.getString("rtu_id"));
		short   p_zjg_id = (short)CommBase.strtoi(json.getString("zjg_id"));
		//用户编号
		String  p_consno = json.getString("busi_no");
		
		ComntMsgMisGS.YFFMISMSG_GY_QUERYPOWER_GS 			power_query  = new ComntMsgMisGS.YFFMISMSG_GY_QUERYPOWER_GS();
		ComntMsgMisGS.YFFMISMSG_GY_QUERYPOWER_RESULT_GS  	power_result = new ComntMsgMisGS.YFFMISMSG_GY_QUERYPOWER_RESULT_GS();
		
		power_query.rtu_id = p_rtu_id;
		power_query.sub_id = p_zjg_id;		
		//用户编号
		ComntFunc.string2Byte(p_consno, power_query.yhbh);
		
		gyOperStr = SDDef.EMPTY;
		
		boolean ret_val = false;
		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMisGS.GYQueryPower(user_name, i_user_data1, i_user_data2, rtu_para, p_zjg_id, 
											power_query, power_result, task_result_detail, err_str_1);
	
		if (ret_val) {
			misUseflag 	= "true";
			misOkflag	= "true";
			
			//使用MIS 但查询失败
			if(power_result.retcode != ComntMsgMisGS.YFFMIS_MISOP_OKNO){
				misOkflag = "false";
				//连接MIS失败
				if(power_result.retcode == 0) {
					misResult = "MIS用户查询失败, \n错误信息:" + err_str_1 + " \n不能操作...";
				}
				//MIS返回错误
				else{
					misResult = "MIS用户查询失败, \n错误信息:" + err_str_1 + " , \nMIS错误信息:" + 
								power_result.retcode + ": " +
								ComntOperMisGS.GetMisErrStr(power_result.retcode) + " \n不能操作...";
				}
			}
			else {
				gyOperStr = power_result.toJsonString();
			}
		}
		else {
			if(task_result_detail.errcode == ComntMsgMisGS.YFFMIS_ERR_MISNOUSE) {
				misUseflag = "false";
			}
			else {
				misResult = "MIS通讯错误";
			}
		}
	
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}

	//甘肃高压操作-缴费记录上传	
	private String MIS_GyPay(String user_name, ComntParaBase.RTU_PARA rtu_para, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		misResult  = SDDef.EMPTY;
		
		if (gyOperStr != null) gyOperStr = gyOperStr.trim();
		if (gyOperStr == null || gyOperStr.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			misResult = "错误的请求参数";			
			return SDDef.FAIL;
		}
		
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID

		boolean ret_val = false;		
		JSONObject json = JSONObject.fromObject(gyOperStr);		
		
		int 	p_rtu_id 	= CommBase.strtoi(json.getString("rtu_id"));
		short 	p_zjg_id 	= (short)CommBase.strtoi(json.getString("zjg_id"));
		int     p_date   	= CommBase.strtoi(json.getString("date"));
		int     p_time   	= CommBase.strtoi(json.getString("time"));
		byte    p_optype	= (byte)CommBase.strtoi(json.getString("op_type"));
		//用户编号
		String  p_consno 	= json.getString("yhbh");
		//交易流水号
		String  p_wasteno 	= json.getString("jylsh");
		//对账批次
		String  p_dzpc		= json.getString("dzpc");
		//缴费金额
		double  p_paymoney  = CommBase.strtof(json.getString("jfje"));
		
		//没有缴费,不用于MIS通讯
		if (p_paymoney <= 0.01) {
			misResult = "没有缴费,不用传MIS缴费记录...";
			return SDDef.SUCCESS;
		}
		
		//发送
		ComntMsgMisGS.YFFMISMSG_GY_PAY_GS pay_req = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS();
		ComntMsgMisGS.YFFMISMSG_GY_PAY_RESULT_GS  pay_result = new ComntMsgMisGS.YFFMISMSG_GY_PAY_RESULT_GS();
		
		pay_req.rtu_id 	= p_rtu_id;
		pay_req.sub_id 	= p_zjg_id;
		pay_req.date 	= p_date;
		pay_req.time 	= p_time;
		
		//更新标志
		pay_req.updateflag = 0;
		//操作员编号
		ComntFunc.string2Byte(CommFunc.getYffMan().getName(), pay_req.czybh);
		ComntFunc.byteStringCutW(pay_req.czybh, ComntOperMisGS.OPERMAN_STRLEN);
		//交易流水号
		ComntFunc.string2Byte(p_wasteno, pay_req.jylsh);
		//对账批次
		ComntFunc.string2Byte(p_dzpc, pay_req.dzpc);
		//用户编号
		ComntFunc.string2Byte(p_consno, pay_req.yhbh);
		//本次实缴总金额
		pay_req.jfje = p_paymoney;
		//银行账务日期
		pay_req.yhzwrq = CommBase.strtoi(json.getString("yhzwrq"));
		//缴费笔数
		pay_req.jfbs = CommBase.strtoi(json.getString("jfbs"));
		//预留字段
		ComntFunc.string2Byte("", pay_req.reserve);
		//备注
		ComntFunc.string2Byte("", pay_req.remark);	
		
		int len = CommBase.strtoi(json.getString("vlength"));
		pay_req.details_vect = null;
		if (len != pay_req.jfbs) {
			op_detail.addTaskInfo("ERR:缴费笔数与实际电费数不一致...");
			misResult = "缴费笔数与实际电费数不一致";
			return SDDef.FAIL;
		}	
		int j = 0, k = 0;
		double bcj_zje = p_paymoney,	//总金额 
			   bcj_qf = 0,				//实缴欠费 
			   bcj_ys = 0;				//实缴预收
		ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL []t_details_vect = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL[len];
		for(j = 0; j < len; j++){
			t_details_vect[j] = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL();
			
			ComntFunc.string2Byte(json.getString("yhbh" + j), t_details_vect[j].yhbh);	//用户编号
			ComntFunc.string2Byte(json.getString("ysbs" + j), t_details_vect[j].ysbs);	//应收标识
			
			t_details_vect[j].jfje  = CommBase.strtof(json.getString("jfje" + j));		//应缴费金额
			t_details_vect[j].dfje  = CommBase.strtof(json.getString("dfje" + j));		//电费金额
			t_details_vect[j].wyjje = CommBase.strtof(json.getString("wyjje" + j));		//违约金金额
			t_details_vect[j].sctw  = CommBase.strtof(json.getString("sctw" + j));		//上次调尾
			t_details_vect[j].bctw  = CommBase.strtof(json.getString("bctw" + j));		//本次调尾
			
			if ((bcj_zje - t_details_vect[j].jfje) < 0) {
				t_details_vect[j].jfje = bcj_zje;
				
				bcj_qf += t_details_vect[j].jfje;
				
				break;
			}
			else {
				bcj_zje -= t_details_vect[j].jfje;
				
				bcj_qf += t_details_vect[j].jfje;
			}
		}
		//未补齐以前电费
		if (j < len) {
			pay_req.details_vect = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL[j + 1];
			for (k = 0; k < (j + 1); k++) {
				pay_req.details_vect[k] = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL();
				pay_req.details_vect[k].clone(t_details_vect[k]);
			}
			//修改缴费笔数
			pay_req.jfbs = j + 1;
		}
		//已补齐以前电费
		else {
			if (bcj_zje > 0.01) {
				pay_req.details_vect = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL[len + 1];
				for (k = 0; k < len; k++) {
					pay_req.details_vect[k] = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL();
					pay_req.details_vect[k].clone(t_details_vect[k]);
				}
				
				//预收的
				pay_req.details_vect[k] = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL();
				
				ComntFunc.string2Byte(p_consno, pay_req.details_vect[k].yhbh);	//用户编号
				ComntFunc.string2Byte("",       pay_req.details_vect[k].ysbs);	//应收标识
				pay_req.details_vect[k].jfje  = bcj_zje;						//缴费金额
				pay_req.details_vect[k].dfje  = 0;								//电费金额
				pay_req.details_vect[k].wyjje = 0;								//违约金金额
				
				pay_req.details_vect[k].sctw  = 0;								//上次调尾
				pay_req.details_vect[k].bctw  = 0;								//本次调尾			
				
				//修改缴费笔数
				pay_req.jfbs ++;
				
				bcj_ys = bcj_zje;
			}
			else {
				pay_req.details_vect = t_details_vect;
			}
		}
		
		pay_req.sjqfje = bcj_qf;	//本次实缴欠费金额
		pay_req.sjysje = bcj_ys;	//本次实缴预收金额
		
		gyOperStr = SDDef.EMPTY;
		
		//上传缴费记录
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMisGS.GYPay(user_name, i_user_data1, i_user_data2, rtu_para, p_zjg_id, 
											pay_req, pay_result, task_result_detail, err_str_1);

		boolean payok_flag = false;
		
		if (ret_val) {
			//上传成功
			if(pay_result.retcode == ComntMsgMisGS.YFFMIS_MISOP_OKNO) {
				payok_flag = true;
				
				gyOperStr = pay_result.toJsonString();
			}
		}
		//返回错误 读数据库
		else {
			//容错处理
			Map<String, String> map = DBOperMis.loadNewZYffMisPayRecord(p_date, p_wasteno);
			if ((map != null) && 
				(CommBase.strtoi(map.get("error_no")) == ComntMsgMisGS.YFFMIS_MISOP_OKNO)) {
				payok_flag = true;
			}
		}
		
		//更新本地数据库
		DBOperMis.updateZYffPayRecord(p_rtu_id, p_zjg_id, p_date, p_time, p_optype, 
								  true, payok_flag);
		
		if(payok_flag == false) {
			//连接MIS失败
			if(pay_result.retcode == 0) {
				misResult = "上传缴费记录失败, \n错误信息:" + err_str_1 + " \n不能操作...";
			}
			//MIS返回错误
			else{
				misResult = "上传缴费记录失败, \n错误信息:" + err_str_1 + " , \nMIS错误信息:" + 
							pay_result.retcode + ": " +
							ComntOperMisGS.GetMisErrStr(pay_result.retcode) + " \n请转入【补MIS缴费记录】页面...";
			}
		}
		else {
			misResult = "上传缴费记录成功。";
		}
		
		misOkflag = payok_flag ? "true" : "false";
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}
		
	//甘肃高压操作-冲正
	private String MIS_GYRever(String user_name, ComntParaBase.RTU_PARA rtu_para, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;
		
		misResult = SDDef.EMPTY;
		
		if (gyOperStr != null) gyOperStr = gyOperStr.trim();
		if (gyOperStr == null || gyOperStr.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			misResult = "错误的请求参数";
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(gyOperStr);
		
		int 	p_rtu_id 	= CommBase.strtoi(json.getString("rtu_id"));
		short 	p_zjg_id 	= (short)CommBase.strtoi(json.getString("zjg_id"));
		int     p_last_date = CommBase.strtoi(json.getString("last_date"));
		int     p_last_time = CommBase.strtoi(json.getString("last_time"));
		int     p_date   	= CommBase.strtoi(json.getString("date"));
		int     p_time   	= CommBase.strtoi(json.getString("time"));
		//用户编号
		String  p_consno    = json.getString("busi_no");
		//缴费金额
		double  p_paymoney  = CommBase.strtof(json.getString("pay_money"));
		//冲正交易流水号
		String  p_wasteno   = json.getString("czjylsh");
		//对账批次
		String  p_dzpc		= json.getString("dzpc");
		//原交易流水号
		String  p_rewasteno = json.getString("yjylsh");
		
		//读取被冲正的缴费记录
		Map<String, String> record_lastpay = DBOperMis.loadOneZYffOpRecord(p_rtu_id, p_zjg_id, p_last_date, p_last_time, 
				SDDef.YFF_OPTYPE_ADDRES , SDDef.YFF_OPTYPE_RESTART , SDDef.YFF_OPTYPE_PAY , SDDef.YFF_OPTYPE_CHANGEMETER );
		
		if(record_lastpay == null) {
			misResult = "MIS冲正, 读取被冲正记录失败, 请手工在MIS中冲正...";
			return SDDef.FAIL;
		}
		
		//读取冲正记录
		Map<String, String> record_rever = DBOperMis.loadOneZYffOpRecord(p_rtu_id, p_zjg_id, p_date, p_time, 
											SDDef.YFF_OPTYPE_REVER, -1, -1, -1);
		if(record_rever == null) {
			misResult = "MIS冲正, 读取被冲正记录失败, 请手工在MIS中冲正...";
			return SDDef.FAIL;
		}
		
		//读取新缴费记录
		Map<String, String> record_thispay = DBOperMis.loadOneZYffOpRecord(p_rtu_id, p_zjg_id, p_date, p_time, 
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
		DBOperMis.updateZYffPayRecord(p_rtu_id, p_zjg_id, p_last_date, p_last_time, 
								 CommBase.strtoi(record_lastpay.get("op_type")), 
								 false, false);
		
		//把缴费数据库记录的MIS置上
		if (p_paymoney > 0.01) {
			DBOperMis.updateZYffPayRecord(p_rtu_id, p_zjg_id, p_date, p_time, SDDef.YFF_OPTYPE_PAY, true, false);
		}

		//上传冲正记录
		ComntMsgMisGS.YFFMISMSG_GY_REVER_GS 		   rever_req    = new ComntMsgMisGS.YFFMISMSG_GY_REVER_GS();
		ComntMsgMisGS.YFFMISMSG_GY_REVER_RESULT_GS  rever_result = new ComntMsgMisGS.YFFMISMSG_GY_REVER_RESULT_GS();
		
		rever_req.rtu_id = p_rtu_id;
		rever_req.sub_id = p_zjg_id;
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
		//对账批次
		ComntFunc.string2Byte(p_dzpc, rever_req.dzpc);
		//冲正交易流水号
		//ComntFunc.string2Byte(p_wasteno, rever_req.czjylsh);
		ComntFunc.string2Byte(record_rever.get("wasteno"), rever_req.czjylsh);
		//原交易流水号
		ComntFunc.string2Byte(p_rewasteno, rever_req.yjylsh);
		//银行账务日期
		rever_req.yhzwrq = CommBase.strtoi(json.getString("yhzwrq"));
		
		gyOperStr = SDDef.EMPTY;
		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMisGS.GYRever(user_name, i_user_data1, i_user_data2, rtu_para, p_zjg_id, 
									rever_req, rever_result, task_result_detail, err_str_1);			

		boolean rev_ok_flag = false;
		if (ret_val) {
			if(rever_result.retcode == ComntMsgMisGS.YFFMIS_MISOP_OKNO) {
				rev_ok_flag = true;
			}
		}
		else {
			Map<String, String> map_rev_record = DBOperMis.loadNewZYffMisReverRecord(p_date, p_rewasteno);
			if ((map_rev_record != null) && 
				(CommBase.strtoi(map_rev_record.get("error_no")) == ComntMsgMisGS.YFFMIS_MISOP_OKNO)) {
				rev_ok_flag = true;
			}
		}

		//更新本地数据库
		DBOperMis.updateZYffPayRecord(p_rtu_id, p_zjg_id, p_date, p_time, 
								 SDDef.YFF_OPTYPE_REVER, true, rev_ok_flag);
		
		if (!rev_ok_flag) {
			//连接MIS失败
			if(rever_result.retcode == 0) {
				misResult = "上传冲正记录失败, 错误信息:" + err_str_1 + " \n请转入【补MIS冲正记录】页面...";
			}
			//MIS返回错误
			else{
				misResult = "上传冲正记录失败, \n错误信息:" + err_str_1 + " , \nMIS错误信息:" + 
							rever_result.retcode + ":" +
							ComntOperMisGS.GetMisErrStr(rever_result.retcode) + " \n请转入【补MIS冲正记录】页面...";
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
		ComntMsgMisGS.YFFMISMSG_GY_QUERYPOWER_GS power_req = new ComntMsgMisGS.YFFMISMSG_GY_QUERYPOWER_GS();
		
		power_req.rtu_id = p_rtu_id;
		power_req.sub_id = p_zjg_id;
		//用户编号
		ComntFunc.string2Byte(p_consno, power_req.yhbh);

		ComntMsgMisGS.YFFMISMSG_GY_QUERYPOWER_RESULT_GS  power_result = new ComntMsgMisGS.YFFMISMSG_GY_QUERYPOWER_RESULT_GS();		
		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMisGS.GYQueryPower(user_name, i_user_data1, i_user_data2, rtu_para, p_zjg_id, 
				power_req, power_result, task_result_detail, err_str_1);
		
		if (ret_val == false || power_result.retcode != ComntMsgMisGS.YFFMIS_MISOP_OKNO) {
			if (task_result_detail.errcode == 0) {
				misResult = "MIS用户查询失败, \n错误信息:" + err_str_1 + " \n请转入【补MIS缴费记录】页面...";
			}
			else {
				misResult = "MIS用户查询失败, \n错误信息:" + err_str_1 + ", \nMIS错误信息:" + 
							task_result_detail.errcode + ": " +
							ComntOperMis.GetMisErrStr(task_result_detail.errcode) + " \n请转入【补MIS缴费记录】页面...";
			}

			//更新缴费本地数据库
			DBOperMis.updateZYffPayRecord(p_rtu_id, p_zjg_id, 
										CommBase.strtoi(record_thispay.get("op_date")), 
										CommBase.strtoi(record_thispay.get("op_time")), 
									   SDDef.YFF_OPTYPE_PAY, true, false);

			return SDDef.FAIL;
		}
		
		//暂停1s
		CommFunc.waitMsg(1000);
		
		//缴费
		ComntMsgMisGS.YFFMISMSG_GY_PAY_GS pay_req = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS();
		
		pay_req.rtu_id = p_rtu_id;
		pay_req.sub_id = p_zjg_id;
		pay_req.date = CommBase.strtoi(record_thispay.get("op_date"));
		pay_req.time = CommBase.strtoi(record_thispay.get("op_time"));
		pay_req.updateflag = 0;
		//操作员编号
		ComntFunc.string2Byte(CommFunc.getYffMan().getName(), pay_req.czybh);
		ComntFunc.byteStringCutW(pay_req.czybh, ComntOperMis.OPERMAN_STRLEN);
		
		//交易流水号
		ComntFunc.string2Byte(record_thispay.get("wasteno"), pay_req.jylsh);
		//对账批次
		ComntFunc.arrayCopy(power_result.dzpc, pay_req.dzpc);
		//用户编号
		ComntFunc.string2Byte(p_consno, pay_req.yhbh);
		//缴费金额
		pay_req.jfje = p_paymoney;
		//银行账务日期
		pay_req.yhzwrq = CommBase.strtoi(record_thispay.get("op_date"));
		//缴费笔数
		pay_req.jfbs = power_result.jezbs;
		//预留字段
		ComntFunc.string2Byte("", pay_req.reserve);
		//备注
		ComntFunc.string2Byte("", pay_req.remark);		
		//欠费信息
		int j = 0, k = 0;
		double bcj_zje = p_paymoney,	//总金额 
			   bcj_qf = 0,				//实缴欠费 
			   bcj_ys = 0;				//实缴预收
		
		ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL []t_details_vect = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL[power_result.jezbs];
		
		for(j = 0; j < power_result.jezbs; j++){
			t_details_vect[j] = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL();
	
			ComntFunc.arrayCopy(t_details_vect[j].yhbh, power_result.details_vect[j].yhbh);	//用户编号
			ComntFunc.arrayCopy(t_details_vect[j].ysbs, power_result.details_vect[j].ysbs);	//用户编号
			
			t_details_vect[j].jfje  = power_result.details_vect[j].yjje;		//应缴费金额
			t_details_vect[j].dfje  = power_result.details_vect[j].dfje;		//电费金额
			t_details_vect[j].wyjje = power_result.details_vect[j].wyjje;		//违约金金额
			t_details_vect[j].sctw  = power_result.details_vect[j].sctw;		//上次调尾
			t_details_vect[j].bctw  = power_result.details_vect[j].bctw;		//本次调尾
			
			if ((bcj_zje - t_details_vect[j].jfje) < 0) {
				t_details_vect[j].jfje = bcj_zje;
				
				bcj_qf += t_details_vect[j].jfje;
				
				break;
			}
			else {
				bcj_zje -= t_details_vect[j].jfje;
				
				bcj_qf += t_details_vect[j].jfje;
			}
		}
		
		//未补齐以前电费
		if (j < power_result.jezbs) {
			pay_req.details_vect = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL[j + 1];
			for (k = 0; k < (j + 1); k++) {
				pay_req.details_vect[k] = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL();
				pay_req.details_vect[k].clone(t_details_vect[k]);
			}
			//修改缴费笔数
			pay_req.jfbs = j + 1;
		}
		//已补齐以前电费
		else {
			if (bcj_zje > 0.01) {
				pay_req.details_vect = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL[power_result.jezbs + 1];
				for (k = 0; k < power_result.jezbs; k++) {
					pay_req.details_vect[k] = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL();
					pay_req.details_vect[k].clone(t_details_vect[k]);
				}
				
				//预收的
				pay_req.details_vect[k] = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL();
				
				ComntFunc.string2Byte(p_consno, pay_req.details_vect[k].yhbh);	//用户编号
				ComntFunc.string2Byte("",       pay_req.details_vect[k].ysbs);	//应收标识
				pay_req.details_vect[k].jfje  = bcj_zje;						//缴费金额
				pay_req.details_vect[k].dfje  = 0;								//电费金额
				pay_req.details_vect[k].wyjje = 0;								//违约金金额
				
				pay_req.details_vect[k].sctw  = 0;								//上次调尾
				pay_req.details_vect[k].bctw  = 0;								//本次调尾			
				
				//修改缴费笔数
				pay_req.jfbs ++;
				
				bcj_ys = bcj_zje;
			}
			else {
				pay_req.details_vect = t_details_vect;
			}
		}

		pay_req.sjqfje = bcj_qf;	//本次实缴欠费金额
		pay_req.sjysje = bcj_ys;	//本次实缴预收金额		
		
		//上传缴费记录
		ComntMsgMisGS.YFFMISMSG_GY_PAY_RESULT_GS pay_result = new ComntMsgMisGS.YFFMISMSG_GY_PAY_RESULT_GS();
		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMisGS.GYPay(user_name, i_user_data1, i_user_data2, rtu_para, p_zjg_id, 
									pay_req, pay_result, task_result_detail, err_str_1);
		
		boolean pay_okflag = false;
		if (ret_val == false) {
			Map<String, String> map = DBOperMis.loadOneZYffMisPayRecord(/*p_rtu_id, p_zjg_id,*/ 
									  CommBase.strtoi(record_thispay.get("op_date")), 
									  record_thispay.get("wasteno"));
			if ((map != null) && 
				(CommBase.strtoi(map.get("error_no")) == ComntMsgMisGS.YFFMIS_MISOP_OKNO)) {
				pay_okflag = true;
			}
		}
		//返回错误 读数据库
		else {
			//上传成功
			if(pay_result.retcode == ComntMsgMisGS.YFFMIS_MISOP_OKNO) {
				pay_okflag = true;
			}
		}
		
		//更新本地数据库
		DBOperMis.updateZYffPayRecord(p_rtu_id, p_zjg_id, 
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
							ComntOperMisGS.GetMisErrStr(pay_result.retcode) + 
							" \n请转入【补MIS缴费记录】页面 "; 
			}			
		}
		else {
			misResult = "MIS冲正成功...";
		}
		
		return pay_okflag ? SDDef.SUCCESS : SDDef.FAIL;
	}
	
	//甘肃高压操作-检查缴费
	private String MIS_GYCheckPay(String user_name, ComntParaBase.RTU_PARA rtu_para, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;
		
		misResult = SDDef.EMPTY;
		
		if (gyOperStr != null) gyOperStr = gyOperStr.trim();
		if (gyOperStr == null || gyOperStr.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			misResult = "错误的请求参数";
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(gyOperStr);

		int 	p_rtu_id 	= CommBase.strtoi(json.getString("rtu_id"));
		short 	p_zjg_id 	= (short)CommBase.strtoi(json.getString("zjg_id"));
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
		Map<String, String> record_pay = DBOperMis.loadOneZYffOpRecord(p_rtu_id, p_zjg_id, p_opdate, p_optime, 
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
		Map<String, String> record_rever = DBOperMis.loadOneZYffOpRecord(p_rtu_id, p_zjg_id, p_opdate, p_optime, 
											SDDef.YFF_OPTYPE_REVER, -1, -1, -1);
		
		if (record_rever != null && 
		    CommBase.strtoi(record_rever.get("up186_flag")) != 0 && 
		    CommBase.strtoi(record_rever.get("checkpay_flag")) == 0) {
			misResult = "此用户存在未提交的冲正记录, 请先冲正";
			return SDDef.FAIL;
		}
		
		byte update_flag = 0;
		
		//读取MIS缴费操作记录
		Map<String, String> mis_payrcd = DBOperMis.loadNewZYffMisPayRecord(/*p_rtu_id, p_zjg_id, */p_opdate, p_wasteno);
		if ((mis_payrcd != null) && 
			(ComntMsgMisGS.YFFMIS_MISOP_OKNO == CommBase.strtoi(mis_payrcd.get("error_no")))) {
			//更新本地数据库记录
			DBOperMis.updateZYffPayRecord(p_rtu_id, p_zjg_id, p_opdate, p_optime, p_optype, true, true);
			return SDDef.SUCCESS;
		}
		
		if (mis_payrcd != null) {
			update_flag = 1;
		}
		
		//MIS查询
		ComntMsgMisGS.YFFMISMSG_GY_QUERYPOWER_GS power_req = new ComntMsgMisGS.YFFMISMSG_GY_QUERYPOWER_GS();
		ComntMsgMisGS.YFFMISMSG_GY_QUERYPOWER_RESULT_GS  power_result = new ComntMsgMisGS.YFFMISMSG_GY_QUERYPOWER_RESULT_GS();
		
		power_req.rtu_id = p_rtu_id;
		power_req.sub_id = p_zjg_id;
		//用户编号
		ComntFunc.string2Byte(p_consno, power_req.yhbh);
		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMisGS.GYQueryPower(user_name, i_user_data1, i_user_data2, rtu_para, p_zjg_id, 
				power_req, power_result, task_result_detail, err_str_1);

		if (ret_val == false || power_result.retcode != ComntMsgMisGS.YFFMIS_MISOP_OKNO) {
			if (task_result_detail.errcode == 0) {
				misResult = "MIS用户查询失败, \n错误信息:" + err_str_1 + " ...";
			}
			else {
				misResult = "MIS用户查询失败, \n错误信息:" + err_str_1 + ", \nMIS错误信息:" + 
							task_result_detail.errcode + ": " +
							ComntOperMisGS.GetMisErrStr(task_result_detail.errcode) + " ...";
			}

			return SDDef.FAIL;
		}
		
		//暂停1s
		CommFunc.waitMsg(1000);
		
		//缴费
		ComntMsgMisGS.YFFMISMSG_GY_PAY_GS pay_req = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS();
		ComntMsgMisGS.YFFMISMSG_GY_PAY_RESULT_GS pay_result = new ComntMsgMisGS.YFFMISMSG_GY_PAY_RESULT_GS();
		
		pay_req.rtu_id 	= p_rtu_id;
		pay_req.sub_id 	= p_zjg_id;
		pay_req.date 	= p_opdate;
		pay_req.time 	= p_optime;
		pay_req.updateflag = update_flag;
		//操作员编号
		ComntFunc.string2Byte(CommFunc.getYffMan().getName(), pay_req.czybh);
		ComntFunc.byteStringCutW(pay_req.czybh, ComntOperMis.OPERMAN_STRLEN);
		
		//交易流水号
		ComntFunc.string2Byte(p_wasteno, pay_req.jylsh);
		//对账批次
		ComntFunc.arrayCopy(pay_req.dzpc, power_result.dzpc);
		//用户编号
		ComntFunc.string2Byte(p_consno, pay_req.yhbh);
		//缴费金额
		pay_req.jfje = p_paymoney;
		//银行账务日期
		pay_req.yhzwrq = p_opdate;
		//缴费笔数
		pay_req.jfbs = power_result.jezbs;
		//预留字段
		ComntFunc.string2Byte("", pay_req.reserve);
		//备注
		ComntFunc.string2Byte("", pay_req.remark);
		//欠费信息
		int j = 0, k = 0;
		double bcj_zje = p_paymoney,	//总金额 
			   bcj_qf = 0,				//实缴欠费 
			   bcj_ys = 0;				//实缴预收
		
		ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL []t_details_vect = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL[power_result.jezbs];
		
		for(j = 0; j < power_result.jezbs; j++){
			t_details_vect[j] = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL();
			
			ComntFunc.arrayCopy(t_details_vect[j].yhbh, power_result.details_vect[j].yhbh);	//用户编号
			ComntFunc.arrayCopy(t_details_vect[j].ysbs, power_result.details_vect[j].ysbs);	//用户编号
			
			t_details_vect[j].jfje  = power_result.details_vect[j].yjje;		//应缴费金额
			t_details_vect[j].dfje  = power_result.details_vect[j].dfje;		//电费金额
			t_details_vect[j].wyjje = power_result.details_vect[j].wyjje;		//违约金金额
			t_details_vect[j].sctw  = power_result.details_vect[j].sctw;		//上次调尾
			t_details_vect[j].bctw  = power_result.details_vect[j].bctw;		//本次调尾
			
			if ((bcj_zje - t_details_vect[j].jfje) < 0) {
				t_details_vect[j].jfje = bcj_zje;
				
				bcj_qf += t_details_vect[j].jfje;
				
				break;
			}
			else {
				bcj_zje -= t_details_vect[j].jfje;
				
				bcj_qf += t_details_vect[j].jfje;
			}
		}
		
		//未补齐以前电费
		if (j < power_result.jezbs) {
			pay_req.details_vect = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL[j + 1];
			for (k = 0; k < (j + 1); k++) {
				pay_req.details_vect[k] = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL();
				pay_req.details_vect[k].clone(t_details_vect[k]);
			}
			
			//修改缴费笔数
			pay_req.jfbs = j + 1;
		}
		//已补齐以前电费
		else {
			if (bcj_zje > 0.01) {
				pay_req.details_vect = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL[power_result.jezbs + 1];
				for (k = 0; k < power_result.jezbs; k++) {
					pay_req.details_vect[k] = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL();
					pay_req.details_vect[k].clone(t_details_vect[k]);
				}
				
				//预收的
				pay_req.details_vect[k] = new ComntMsgMisGS.YFFMISMSG_GY_PAY_GS.POWERPAY_DETAIL();
				
				ComntFunc.string2Byte(p_consno, pay_req.details_vect[k].yhbh);	//用户编号
				ComntFunc.string2Byte("",       pay_req.details_vect[k].ysbs);	//应收标识
				pay_req.details_vect[k].jfje  = bcj_zje;						//缴费金额
				pay_req.details_vect[k].dfje  = 0;								//电费金额
				pay_req.details_vect[k].wyjje = 0;								//违约金金额
				
				pay_req.details_vect[k].sctw  = 0;								//上次调尾
				pay_req.details_vect[k].bctw  = 0;								//本次调尾			
				
				//修改缴费笔数
				pay_req.jfbs ++;
				
				bcj_ys = bcj_zje;
			}
			else {
				pay_req.details_vect = t_details_vect;
			}
		}

		pay_req.sjqfje = bcj_qf;	//本次实缴欠费金额
		pay_req.sjysje = bcj_ys;	//本次实缴预收金额	

		//上传缴费记录		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMisGS.GYPay(user_name, i_user_data1, i_user_data2, rtu_para, p_zjg_id, 
									pay_req, pay_result, task_result_detail, err_str_1);
		
		boolean pay_okflag = false;
		if (ret_val == false) {
			Map<String, String> map = DBOperMis.loadOneZYffMisPayRecord(/*p_rtu_id, p_zjg_id,*/ 
									  p_opdate, p_wasteno);
			if ((map != null) && 
				(CommBase.strtoi(map.get("error_no")) == ComntMsgMisGS.YFFMIS_MISOP_OKNO)) {
				pay_okflag = true;
			}
		}
		//返回错误 读数据库
		else {
			//上传成功
			if(pay_result.retcode == ComntMsgMisGS.YFFMIS_MISOP_OKNO) {
				pay_okflag = true;
			}
		}
		
		//更新本地数据库
		if (pay_okflag) {
			DBOperMis.updateZYffPayRecord(p_rtu_id, p_zjg_id, p_opdate, p_optime,
									  SDDef.YFF_OPTYPE_PAY, true, true);
		}
		
		if (pay_okflag == false) {
			if (pay_result.retcode == 0) {
				misResult = "上传缴费记录失败, 错误信息:" + err_str_1;
			}
			else {
				misResult = "上传缴费记录失败, 错误信息:" + err_str_1 + " \nMIS错误信息:" + 
							pay_result.retcode + ": " +
							ComntOperMisGS.GetMisErrStr(pay_result.retcode); 
			}
		}
		else {
			misResult = "MIS缴费成功";
		}
		
		return pay_okflag ? SDDef.SUCCESS : SDDef.FAIL;				
	}	
	
	//甘肃高压操作-检查冲正
	private String MIS_GYCheckRever(String user_name, ComntParaBase.RTU_PARA rtu_para, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws Exception {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		boolean ret_val = false;		
		misResult = SDDef.EMPTY;		
		
		if (gyOperStr != null) gyOperStr = gyOperStr.trim();
		if (gyOperStr == null || gyOperStr.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			misResult = "错误的请求参数";
			return SDDef.FAIL;
		}
		
		JSONObject json = JSONObject.fromObject(gyOperStr);
	
		int 	p_rtu_id 	= CommBase.strtoi(json.getString("rtu_id"));
		short 	p_zjg_id 	= (short)CommBase.strtoi(json.getString("zjg_id"));
		int     p_opdate   	= CommBase.strtoi(json.getString("op_date"));
		int     p_optime   	= CommBase.strtoi(json.getString("op_time"));
		byte    p_optype   	= (byte)CommBase.strtoi(json.getString("op_type"));
		
		//用户编号
		String  p_consno    = json.getString("yhbh");
		
		/////////////////////////////////////////////////
		//MIS查询
		ComntMsgMisGS.YFFMISMSG_GY_QUERYPOWER_GS power_req = new ComntMsgMisGS.YFFMISMSG_GY_QUERYPOWER_GS();
		ComntMsgMisGS.YFFMISMSG_GY_QUERYPOWER_RESULT_GS  power_result = new ComntMsgMisGS.YFFMISMSG_GY_QUERYPOWER_RESULT_GS();
		
		power_req.rtu_id = p_rtu_id;
		power_req.sub_id = p_zjg_id;
		//用户编号
		ComntFunc.string2Byte(p_consno, power_req.yhbh);
		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMisGS.GYQueryPower(user_name, i_user_data1, i_user_data2, rtu_para, p_zjg_id, 
				power_req, power_result, task_result_detail, err_str_1);

		if (ret_val == false || power_result.retcode != ComntMsgMisGS.YFFMIS_MISOP_OKNO) {
			if (task_result_detail.errcode == 0) {
				misResult = "MIS用户查询失败, \n错误信息:" + err_str_1 + " ...";
			}
			else {
				misResult = "MIS用户查询失败, \n错误信息:" + err_str_1 + ", \nMIS错误信息:" + 
				task_result_detail.errcode + ": " +
				ComntOperMisGS.GetMisErrStr(task_result_detail.errcode) + " ...";
			}

			return SDDef.FAIL;
		}		
		/////////////////////////////////////////////////
		
		//对账批次
		String p_dzpc       = ComntFunc.byte2String(power_result.dzpc);	
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
		Map<String, String> record_rever = DBOperMis.loadOneZYffOpRecord(p_rtu_id, p_zjg_id, p_opdate, p_optime, 
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
		Map<String, String> record_lastrever = DBOperMis.loadOneZYffOpRecordWasteNo(p_rtu_id, p_zjg_id, p_opdate, p_rewasteno);
		if (record_lastrever == null) {
			misResult = "读取本地被冲正记录失败.";
			return SDDef.FAIL;
		}
		
		//读取MIS冲正操作记录
		Map<String, String> mis_reverrcd = DBOperMis.loadNewZYffMisReverRecord(/*p_rtu_id, p_zjg_id, */p_opdate, p_wasteno);
		if ((mis_reverrcd != null) && 
			(ComntMsgMisGS.YFFMIS_MISOP_OKNO == CommBase.strtoi(mis_reverrcd.get("error_no")))) {
			//更新本地数据库记录
			DBOperMis.updateZYffPayRecord(p_rtu_id, p_zjg_id, p_opdate, p_optime, p_optype, true, true);
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
		Map<String, String> mis_payrcd = DBOperMis.loadOneZYffMisPayRecord(/*p_rtu_id, p_zjg_id, */p_opdate, p_rewasteno);
		//无记录不用再冲正
		if (mis_payrcd == null) {
			//更新本地数据库记录
			DBOperMis.updateZYffPayRecord(p_rtu_id, p_zjg_id, p_opdate, p_optime, p_optype, true, true);
			misResult = "冲正成功.";
			return SDDef.SUCCESS;
		}
		
//		if (CommBase.strtoi(record_lastrever.get("up186_flag")) == 0) {
//			//更新本地数据库记录
//			DBOperMis.updateZYffPayRecord(p_rtu_id, p_zjg_id, p_opdate, p_optime, p_optype, true, true);
//			misResult = "冲正成功.";
//			return SDDef.SUCCESS;
//		}
		//20120421 end this 可能还会有点问题？？
		
		//上传冲正记录
		ComntMsgMisGS.YFFMISMSG_GY_REVER_GS 			rever_req = new ComntMsgMisGS.YFFMISMSG_GY_REVER_GS();
		ComntMsgMisGS.YFFMISMSG_GY_REVER_RESULT_GS  	rever_result = new ComntMsgMisGS.YFFMISMSG_GY_REVER_RESULT_GS();
		
		rever_req.rtu_id = p_rtu_id;
		rever_req.sub_id = p_zjg_id;
		rever_req.date 	 = p_opdate;
		rever_req.time 	 = p_optime;
		
		//被冲正记录日期
		rever_req.last_date = CommBase.strtoi(record_lastrever.get("op_date"));
		//被冲正记录时间
		rever_req.last_time = CommBase.strtoi(record_lastrever.get("op_time"));
		//更新标志
		rever_req.updateflag = update_flag;
		//对账批次
		ComntFunc.string2Byte(p_dzpc, rever_req.dzpc);
		//操作员编号
		ComntFunc.string2Byte(CommFunc.getYffMan().getName(), rever_req.czybh);
		ComntFunc.byteStringCutW(rever_req.czybh, ComntOperMis.OPERMAN_STRLEN);
		//冲正交易流水号
		ComntFunc.string2Byte(record_rever.get("wasteno"), rever_req.czjylsh);
		//原交易流水号
		ComntFunc.string2Byte(record_rever.get("rewasteno"), rever_req.yjylsh);
		//银行账务日期
		rever_req.yhzwrq = CommBase.strtoi(record_rever.get("op_date"));
		
		gyOperStr = SDDef.EMPTY;
		
		task_result_detail.clear(); err_str_1.setLength(0);
		ret_val = ComntOperMisGS.GYRever(user_name, i_user_data1, i_user_data2, rtu_para, p_zjg_id, 
									rever_req, rever_result, task_result_detail, err_str_1);		

		boolean rev_ok_flag = false;
		if (ret_val) {
			if(rever_result.retcode == ComntMsgMisGS.YFFMIS_MISOP_OKNO) {
				rev_ok_flag = true;
			}
		}
		else {
			Map<String, String> map_rev_record = DBOperMis.loadNewZYffMisReverRecord(p_opdate, p_wasteno);
			if ((map_rev_record != null) && 
				(CommBase.strtoi(map_rev_record.get("error_no")) == ComntMsgMisGS.YFFMIS_MISOP_OKNO)) {
				rev_ok_flag = true;
			}
		}

		if (rev_ok_flag) {
			//更新本地数据库
			DBOperMis.updateZYffPayRecord(p_rtu_id, p_zjg_id, p_opdate, p_optime, 
									 SDDef.YFF_OPTYPE_REVER, true, rev_ok_flag);
		}

		if (rev_ok_flag == false) {
			if (rever_result.retcode == 0) {
				misResult = "上传冲正记录失败, 错误信息:" + err_str_1;
			}
			else {
				misResult = "上传冲正记录失败, 错误信息:" + err_str_1 + " \nMIS错误信息:" + 
							rever_result.retcode + ": " +
							ComntOperMisGS.GetMisErrStr(rever_result.retcode); 
			}
		}
		else {
			misResult = "MIS冲正成功";
		}
		
		return rev_ok_flag ? SDDef.SUCCESS : SDDef.FAIL;	
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
			if (userop_id == ComntUseropDef.YFF_GYOPER_MIS_QUERYPOWER) {			//高压操作-MIS查询缴费
				
				ret_val = MIS_GyQueryPower(user_name, rtu_para, op_detail, err_str_1, task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_MIS_PAY) {				//高压操作-MIS缴费记录上传
				
				ret_val = MIS_GyPay(user_name, rtu_para, op_detail, err_str_1, task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_MIS_REVER) {			//高压操作-MIS冲正操作
				
				ret_val = MIS_GYRever(user_name, rtu_para, op_detail, err_str_1, task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_MIS_CHECKPAY) {			//高压操作-MIS检查缴费
				
				ret_val = MIS_GYCheckPay(user_name, rtu_para, op_detail, err_str_1, task_result_detail);
			}
			else if (userop_id == ComntUseropDef.YFF_GYOPER_MIS_CHECKREVER) {		//高压操作-MIS检查冲正
				
				ret_val = MIS_GYCheckRever(user_name, rtu_para, op_detail, err_str_1, task_result_detail);			
			}
			else {
				op_detail.addTaskInfo("ERR:未知的操作类型[" + userop_id + "]");
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

	public String getGyOperStr() {
		return gyOperStr;
	}

	public void setGyOperStr(String gyOperStr) {
		this.gyOperStr = gyOperStr;
	}

}
