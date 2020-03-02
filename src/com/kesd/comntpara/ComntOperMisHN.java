package com.kesd.comntpara;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.kesd.comnt.ComntDef;
import com.kesd.comnt.ComntMsg;
import com.kesd.comnt.ComntMsgMis;
import com.kesd.comnt.ComntMsgMisHN;
import com.kesd.comntpara.ComntParaBase.OpDetailInfo;
import com.kesd.comntpara.ComntParaBase.SALEMAN_TASK_RESULT_DETAIL;
import com.libweb.comnt.ComntVector;

public class ComntOperMisHN {
	public static final int OPERMAN_STRLEN		= 15;		//MIS系统操作员编号长度
	
	
	//MIS获取用户信息
	public static boolean GYQueryPower(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
											short zjg_id, ComntMsgMisHN.YFFMISMSG_GY_QUERYPOWER_HN power_req, ComntMsgMisHN.YFFMISMSG_GY_QUERYPOWER_RESULT_HN power_result, 
											SALEMAN_TASK_RESULT_DETAIL task_result_detail, StringBuffer err_str)
	{
		//--------1
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		power_req.toDataStream(task_data_vect1);
		//---------1
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 		task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.SALEMAN_MSGTTYPE_MIS_GYQUERYPOWER/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntMsgMisHN.YFFMIS_MSGTYPE_GY_QUERYPOWER/*-----3*/) {
					power_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//MIS缴费
	public static boolean GYPay(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
											short zjg_id, ComntMsgMisHN.YFFMISMSG_GY_PAY_HN pay_req, ComntMsgMisHN.YFFMISMSG_GY_PAY_RESULT_HN pay_result, 
											SALEMAN_TASK_RESULT_DETAIL task_result_detail, StringBuffer err_str)
	{
		//--------1
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		pay_req.toDataStream(task_data_vect1);
		//---------1
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 		task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.SALEMAN_MSGTTYPE_MIS_GYPAY/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntMsgMisHN.YFFMIS_MSGTYPE_GY_PAY/*-----3*/) {
					pay_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//MIS冲正
	public static boolean GYRever(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
											short zjg_id, ComntMsgMisHN.YFFMISMSG_GY_REVER_HN power_req, ComntMsgMisHN.YFFMISMSG_GY_REVER_RESULT_HN rever_result, 
											SALEMAN_TASK_RESULT_DETAIL task_result_detail, StringBuffer err_str)
	{
		//--------1
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		power_req.toDataStream(task_data_vect1);
		//---------1
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 		task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.SALEMAN_MSGTTYPE_MIS_GYREVER/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntMsgMisHN.YFFMIS_MSGTYPE_GY_REVER/*-----3*/) {
					rever_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	

/***********低压居民应用************/
	
	//MIS获取用户信息
	public static boolean DYQueryPower(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
											short mp_id, ComntMsgMisHN.YFFMISMSG_DY_QUERYPOWER_HN power_req, ComntMsgMisHN.YFFMISMSG_DY_QUERYPOWER_RESULT_HN power_result, 
											SALEMAN_TASK_RESULT_DETAIL task_result_detail, StringBuffer err_str)
	{
		//--------1
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		power_req.toDataStream(task_data_vect1);
		//---------1
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 		task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.SALEMAN_MSGTTYPE_MIS_DYQUERYPOWER/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntMsgMisHN.YFFMIS_MSGTYPE_DY_QUERYPOWER/*-----3*/) {
					power_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//MIS缴费
	public static boolean DYPay(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
											short mp_id, ComntMsgMisHN.YFFMISMSG_DY_PAY_HN pay_req, ComntMsgMisHN.YFFMISMSG_DY_PAY_RESULT_HN pay_result, 
											SALEMAN_TASK_RESULT_DETAIL task_result_detail, StringBuffer err_str)
	{
		//--------1
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		pay_req.toDataStream(task_data_vect1);
		//---------1
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 		task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.SALEMAN_MSGTTYPE_MIS_DYPAY/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntMsgMisHN.YFFMIS_MSGTYPE_DY_PAY/*-----3*/) {
					pay_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//MIS冲正
	public static boolean DYRever(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
											short mp_id, ComntMsgMisHN.YFFMISMSG_DY_REVER_HN power_req, ComntMsgMisHN.YFFMISMSG_DY_REVER_RESULT_HN rever_result, 
											SALEMAN_TASK_RESULT_DETAIL task_result_detail, StringBuffer err_str)
	{
		//--------1
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		power_req.toDataStream(task_data_vect1);
		//---------1
		
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();
		//SALEMAN_TASK_RESULT_DETAIL 		task_result_detail 	= new SALEMAN_TASK_RESULT_DETAIL();		
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.SALEMAN_MSGTTYPE_MIS_DYREVER/*---2*/, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
		
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
			
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntMsgMisHN.YFFMIS_MSGTYPE_DY_REVER/*-----3*/) {
					rever_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
/************低压居民结束***********/
	
	//高低压MIS对账操作
	public static boolean GDYMisCheck(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
			ComntMsgMisHN.YFFMISMSG_GDYCHECKPAY_HN power_req, ComntMsgMisHN.YFFMISMSG_GDYCHECKPAY_RESULT_HN power_result, 
			SALEMAN_TASK_RESULT_DETAIL task_result_detail, StringBuffer err_str)
	{
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		power_req.toDataStream(task_data_vect1);
	
		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
	
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		OpDetailInfo 						detail_info 		= new OpDetailInfo();		
	
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, user_data1, user_data2, rtu_para, 
				(byte)ComntDef.SALEMAN_MSGTTYPE_MIS_GDYCHECKPAY, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect1, task_result, ret_data_vect, detail_info, err_str, task_result_detail);
	
		boolean data_flag = false;
		if (ret_data_vect.size() > 0) {
			ComntMsg.MSG_RESULT_DATA msg_result_data = null;
	
			int i = 0;
			for (i = 0; i < ret_data_vect.size(); i++) {
				msg_result_data = ret_data_vect.get(i);
				if (msg_result_data.datatype == ComntMsgMisHN.YFFMIS_MSGTYPE_GDY_CHECKPAY) {
					power_result.fromDataStream(msg_result_data.data_vect, 0);
					data_flag = true;
					break;
				}
			}
		}
	
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) return true;
		else return false;
	}
	
	//MIS错误代码
	public static final Map<Integer, String> YFFMIS_ERR = new HashMap<Integer, String>();
	static{
		  YFFMIS_ERR.put(1001,		"正确返回,操作成功"							);
		  YFFMIS_ERR.put(11001, "无用户信息！"									);
		  YFFMIS_ERR.put(11002, "该渠道商无权限进行此操作！"						);                              
		  YFFMIS_ERR.put(11003, "资金编号错误！ "									);
		  YFFMIS_ERR.put(11004, "流水号重复！"									);
		  YFFMIS_ERR.put(11005, "超过最大缴费限额！"								);
		  YFFMIS_ERR.put(11006, "冲正与缴费人员不一致！"							);
		  YFFMIS_ERR.put(11007, "缴费记录已被冲正！"								);
		  YFFMIS_ERR.put(11008, "冲正金额与缴费金额不一致！"						);
		  YFFMIS_ERR.put(11009, "无对应的缴费记录！"								);
		  YFFMIS_ERR.put(11010, "终端信息查询失败！"								);
		  YFFMIS_ERR.put(11011, "资金编号获取失败！"								);
		  YFFMIS_ERR.put(11012, "用户验证失败！"									);
		  YFFMIS_ERR.put(11013, "用户无欠费信息！"								);
		  YFFMIS_ERR.put(11014, "无公共服务信息！"								);
		  YFFMIS_ERR.put(11015, "文件已处理！"									);
		  YFFMIS_ERR.put(11016, "文件不存在！"									);
		  YFFMIS_ERR.put(11017, "必填项为空！"									);
		  YFFMIS_ERR.put(11018, "无渠道商调试相关信息！"							);
		  YFFMIS_ERR.put(11019, "配置文件错误！"									);
		  YFFMIS_ERR.put(11020, "SOCKET通讯异常！"								);
		  YFFMIS_ERR.put(11021, "更新失败！"										);
		  YFFMIS_ERR.put(11022, "订阅/退订失败！"								);
		  YFFMIS_ERR.put(11023, "缴费失败！"										);
		  YFFMIS_ERR.put(11024, "操作员身份认证失败！"							);
		  YFFMIS_ERR.put(11025, "用户号重复！"									);
		  YFFMIS_ERR.put(11026, "该户存在其他协议！"								);
		  YFFMIS_ERR.put(11027, "其他错误！"										);
		  YFFMIS_ERR.put(11028, "银行信息未维护！"								);
		  YFFMIS_ERR.put(11029, "该用户无代扣协议！"								);
		  YFFMIS_ERR.put(11030, "无渠道商信息！"									);
		  YFFMIS_ERR.put(11031, "无订阅信息，不能退订！"							);
		  YFFMIS_ERR.put(11032, "已订阅，不能重复订阅！"							);
		  YFFMIS_ERR.put(11033, "营销系统处理异常！"								);
		  YFFMIS_ERR.put(11034, "报文解析异常！"									);
		  YFFMIS_ERR.put(11035, "操作员未登录！"									);
		  YFFMIS_ERR.put(11036, "编号类别暂只支持用户编号！"						);
		  YFFMIS_ERR.put(11037, "结算方式暂只支持现金！"							);
		  YFFMIS_ERR.put(11038, "缴费方式错误！"									);
		  YFFMIS_ERR.put(11039, "该费用已被收取，不允许重复收取！"				);
		  YFFMIS_ERR.put(11040, "插入数据失败,请检查传入数据的完整性！"			);
		  YFFMIS_ERR.put(11041, "预收收取失败！"									);
		  YFFMIS_ERR.put(11042, "插入代收交易纪录表失败！"						);
		  YFFMIS_ERR.put(11043, "记账处理失败！"									);
		  YFFMIS_ERR.put(11044, "该渠道商信息不完整！"							);
		  YFFMIS_ERR.put(11045, "该费用已被收取，不允许重复收取！"				);
		  YFFMIS_ERR.put(11046, "冲正失败！"										);
		  YFFMIS_ERR.put(11047, "该资金编号无解款记录！"							);
		  YFFMIS_ERR.put(11048, "该对账文件已经被记录！"							);
		  YFFMIS_ERR.put(11049, "终端多收时取出数据失败！"						);
		  YFFMIS_ERR.put(11050, "单边账缴费失败！"								);
		  YFFMIS_ERR.put(11051, "营销多收时取出数据失败！"						);
		  YFFMIS_ERR.put(11052, "指定的时间内没有电费记录！"						);
		  YFFMIS_ERR.put(11053, "指定的时间内没有抄表记录！"						);
		  YFFMIS_ERR.put(11054, "没查找到用户的订阅信息！"						);
		  YFFMIS_ERR.put(11055, "密码不正确！"									);
		  YFFMIS_ERR.put(11056, "不足额消欠！"									);
		  YFFMIS_ERR.put(11057, "不允许跨区缴费！"								);
		  YFFMIS_ERR.put(11058, "档案数据重复！"									);
		  YFFMIS_ERR.put(11059, "档案数据存在问题！"								);
		  YFFMIS_ERR.put(11060, "该笔收费记录已解款！"							);
		  YFFMIS_ERR.put(11061, "解款之后不允许冲正！"							);
		  YFFMIS_ERR.put(11062, "预收款被使用后不允许冲正！"						);
		  YFFMIS_ERR.put(11063, "渠道商未与该户单位签订缴费协议!"					);
		  YFFMIS_ERR.put(11064, "报文的票据标示和交易流水号对应的票据标示不符!"	);
		  YFFMIS_ERR.put(11065, "发票已经打印，不允许重复打印!"					);
		  YFFMIS_ERR.put(11066, "发票文件格式错误!"								);
		  YFFMIS_ERR.put(11077, "非金融机构不允许跨核算单位缴费！"				);
		  YFFMIS_ERR.put(11078, "对账失败，汇总记录的总笔数与明细不一致！"			);
		  YFFMIS_ERR.put(11079, "对账失败，汇总记录的总金额与明细不一致！"			);
		  YFFMIS_ERR.put(11201, "调用加密服务失败，请进入补写卡功能进行补写卡操作！");
		  YFFMIS_ERR.put(11202, "请求的流水号重复，请重新读卡更换流水号！"			);
		  YFFMIS_ERR.put(11203, "请求的流水号已购电！"							);
		  YFFMIS_ERR.put(11204, "请求的流水号未写卡！"							);
		  YFFMIS_ERR.put(11205, "存在多条卡表用户信息！"							);
		  YFFMIS_ERR.put(11206, "用户电价信息错误，需要限制购电！"				);
		  YFFMIS_ERR.put(11207, "存在多条卡表用户信息！"							);
		  YFFMIS_ERR.put(11208, "购电卡为空卡，不能购电！"						);
		  YFFMIS_ERR.put(11209, "购电卡未插表，不能购电！"						);
		  YFFMIS_ERR.put(11210, "该用户处于流程中，需要限制购电！"				);
		  YFFMIS_ERR.put(11211, "购电卡购电次数与系统中用户购电次数不一致，需要限制购电！"	);
		  YFFMIS_ERR.put(11212, "购电卡未插插表，需要限制购电！"					);
		  YFFMIS_ERR.put(11213, "该用户为定量用户，需要限制购电！"				);
		  YFFMIS_ERR.put(11214, "该用户存在欠费信息，请先结清欠费！"				);
		  YFFMIS_ERR.put(11215, "购电卡已写卡成功，不允许补写卡！"				);
		  YFFMIS_ERR.put(11216, "购电卡购电次数与系统中用户购电次数不一致，不允许补写卡！"	);
		  YFFMIS_ERR.put(11217, "用户电价信息错误，需要限制购电！"						);
		  YFFMIS_ERR.put(11218, "用户卡表档案错误！"										);
		  YFFMIS_ERR.put(11219, "使用的购购电卡为补卡后的旧卡，请使用新卡进行购电！"		);
		  YFFMIS_ERR.put(11220, "网络繁忙，请稍候再试！                                  "						);
		  YFFMIS_ERR.put(11221, "调用加密服务失败，请进入补写卡功能进行补写卡操作！ "		);
		  YFFMIS_ERR.put(11222, "不存在用户购电记录！"									);
		  YFFMIS_ERR.put(11223, "存在购电次数相同的两条购电记录！"						);
		  YFFMIS_ERR.put(11224, "卡内购电次数与最后一次购电次数不同，不允许补写卡！"		);
		  YFFMIS_ERR.put(11225, "请求的流水号重复，请重新读卡更换流水号！"					);
		  YFFMIS_ERR.put(11226, "请求的流水号已购电！"									);
		  YFFMIS_ERR.put(11227, "营销后台过程返回失败！"									);
		  YFFMIS_ERR.put(11228, "调用营销核心查询过程失败！"								);
		  YFFMIS_ERR.put(11229, "调用营销核心购电过程失败！"								);
		  YFFMIS_ERR.put(11230, "调用营销核心补写卡过程失败！"							);
                           
		  //20131205添加
		  YFFMIS_ERR.put(701,		"终端未使用MIS接口"         );
		  YFFMIS_ERR.put(702,		"数据库操作错误"            );
		  YFFMIS_ERR.put(703,		"正在对账,请稍等"           );
		  YFFMIS_ERR.put(704,		"无冲正记录"                );
		  YFFMIS_ERR.put(705,		"系统为自动对账模式"              );
		  YFFMIS_ERR.put(706,		"不能重复对账"              );
		  YFFMIS_ERR.put(707,		"仅能对历史记录对账"         );
		  YFFMIS_ERR.put(708,		"读取历史记录存本地文件错误" );
		  YFFMIS_ERR.put(709,		"FTP文件错误"               );
		  YFFMIS_ERR.put(711,		"连接MIS请求错误"           );
		
	}
	public static String GetMisErrStr(int err_code)
	{
		String ret_val = YFFMIS_ERR.get(err_code);
		if (ret_val == null) {
			ret_val = "[" + err_code + "]" + "未知错误码";
		}
		
		return ret_val;
	}
}
