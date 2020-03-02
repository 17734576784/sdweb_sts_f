package com.kesd.comntpara;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.kesd.comnt.ComntDef;
import com.kesd.comnt.ComntMsg;
import com.kesd.comnt.ComntMsgMis;
import com.kesd.comntpara.ComntParaBase.OpDetailInfo;
import com.kesd.comntpara.ComntParaBase.SALEMAN_TASK_RESULT_DETAIL;
import com.libweb.comnt.ComntVector;

public class ComntOperMis {
	public static final int OPERMAN_STRLEN		= 15;		//MIS系统操作员编号长度
	
	
	//MIS获取用户信息
	public static boolean GYQueryPower(String user_name, int user_data1, int user_data2, ComntParaBase.RTU_PARA rtu_para, 
											short zjg_id, ComntMsgMis.YFFMISMSG_GY_QUERYPOWER power_req, ComntMsgMis.YFFMISMSG_GY_QUERYPOWER_RESULT power_result, 
											SALEMAN_TASK_RESULT_DETAIL task_result_detail, StringBuffer err_str)
	{
		//--------1
		ComntVector.ByteVector task_data_vect1 = new ComntVector.ByteVector();
		power_req.toDataStream(task_data_vect1);
		//---------15
		
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
				if (msg_result_data.datatype == ComntMsgMis.YFFMIS_MSGTYPE_GY_QUERYPOWER/*-----3*/) {
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
											short zjg_id, ComntMsgMis.YFFMISMSG_GY_PAY pay_req, ComntMsgMis.YFFMISMSG_GY_PAY_RESULT pay_result, 
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
				if (msg_result_data.datatype == ComntMsgMis.YFFMIS_MSGTYPE_GY_PAY/*-----3*/) {
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
											short zjg_id, ComntMsgMis.YFFMISMSG_GY_REVER power_req, ComntMsgMis.YFFMISMSG_GY_REVER_RESULT rever_result, 
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
				if (msg_result_data.datatype == ComntMsgMis.YFFMIS_MSGTYPE_GY_REVER/*-----3*/) {
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
											short mp_id, ComntMsgMis.YFFMISMSG_DY_QUERYPOWER power_req, ComntMsgMis.YFFMISMSG_DY_QUERYPOWER_RESULT power_result, 
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
				if (msg_result_data.datatype == ComntMsgMis.YFFMIS_MSGTYPE_DY_QUERYPOWER/*-----3*/) {
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
											short mp_id, ComntMsgMis.YFFMISMSG_DY_PAY pay_req, ComntMsgMis.YFFMISMSG_DY_PAY_RESULT pay_result, 
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
				if (msg_result_data.datatype == ComntMsgMis.YFFMIS_MSGTYPE_DY_PAY/*-----3*/) {
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
											short mp_id, ComntMsgMis.YFFMISMSG_DY_REVER power_req, ComntMsgMis.YFFMISMSG_DY_REVER_RESULT rever_result, 
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
				if (msg_result_data.datatype == ComntMsgMis.YFFMIS_MSGTYPE_DY_REVER/*-----3*/) {
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
			ComntMsgMis.YFFMISMSG_GDYCHECKPAY power_req, ComntMsgMis.YFFMISMSG_GDYCHECKPAY_RESULT power_result, 
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
				if (msg_result_data.datatype == ComntMsgMis.YFFMIS_MSGTYPE_GDY_CHECKPAY) {
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
		YFFMIS_ERR.put(1002,		"操作失败	无特别说明的操作失败"				);
		YFFMIS_ERR.put(1003,		"查询无记录"									);
		YFFMIS_ERR.put(1004,	 	"该用户编号不存在"							);
		YFFMIS_ERR.put(1005,	 	"无效冲正	交易流水号和用户编号不匹配"		);
		YFFMIS_ERR.put(1006,	 	"数据包格式不对	"							);
		YFFMIS_ERR.put(1007,		"总帐不平"									);
		YFFMIS_ERR.put(1008,		"明细不平"									);
		YFFMIS_ERR.put(1009,		"总帐和明细都不平"							);
		YFFMIS_ERR.put(1010,	 	"用户已办理代扣，不能再开户"					);
		YFFMIS_ERR.put(1011,	 	"该用户已销户"								);
		YFFMIS_ERR.put(1012,	 	"在开户行销户后才能在另一银行开户"			);
		YFFMIS_ERR.put(1013,		"数据库操作失败"								);
		YFFMIS_ERR.put(1014,		"用户正处于划拨状态"							);
		YFFMIS_ERR.put(1015,		"不能隔日冲正"								);
		YFFMIS_ERR.put(1016,		"有代扣在途欠费不能做纯预收"					);
		YFFMIS_ERR.put(1017,		"不允许同时对多户进行预收"					);
		YFFMIS_ERR.put(1018,		"数据文件生成失败"							);
		YFFMIS_ERR.put(1019,		"数据文件传送失败"                       	);
		YFFMIS_ERR.put(1024,		"文件名有误"	                            	);
		YFFMIS_ERR.put(1025,		"文件不存在"	                            	);
		YFFMIS_ERR.put(1026,		"文件内容有误"                           	);
		YFFMIS_ERR.put(1030,		"批扣不在规定时间内"                      	);
		YFFMIS_ERR.put(1031,		"无代扣、托收、小额数据"                  	);
		YFFMIS_ERR.put(1033,		"储蓄代扣数据生成交易未启动或已经销帐"			);
		YFFMIS_ERR.put(1034,		"销帐未结束"                             	);
		YFFMIS_ERR.put(1035,		"不允许部分销账"                         	);
		YFFMIS_ERR.put(1036,		"必须在开户行销户"	                    	);
		YFFMIS_ERR.put(1037,		"该用户存在欠费，不允许预收"              	);
		YFFMIS_ERR.put(1038,		"非本行缴费或代扣，不允许打印收据或发票"   	);
		YFFMIS_ERR.put(1039,		"该户为电卡表用户，不允许代收"              	);
		YFFMIS_ERR.put(1040,		"用户正处于走收在途状态"                    	);
		YFFMIS_ERR.put(1041,		"业务费必须按实际销账，不允许部分收费或多收"	);	
		YFFMIS_ERR.put(1042,		"本月尚未算费或电费为零"                    	);
		YFFMIS_ERR.put(1043,		"电费正在计算中"								);
		YFFMIS_ERR.put(1044,		"电费已缴清"									);
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
