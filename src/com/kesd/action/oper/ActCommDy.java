package com.kesd.action.oper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.libweb.comnt.*;
import com.libweb.common.CommBase;

import com.kesd.comnt.ComntDef;
import com.kesd.comnt.ComntMsg;
import com.kesd.comnt.ComntMsgDy;
import com.kesd.comnt.ComntMsgProc;
import com.kesd.comnt.ComntProtMsg;
import com.kesd.common.SDDef;
import com.kesd.comntpara.ComntParaBase;
import com.kesd.comntpara.ComntUseropDef;

public class ActCommDy {
	private static final long serialVersionUID = 3607391502741009698L;
	private String  userData1 			= null;		//RTU_ID
	private String  userData2 			= null;		//USEROP_ID
	private String 	result 				= null;		//SUCCESS/FAIL
	private String 	detailInfo 			= null;		//返回详细信息(JSON)
	private String	err_strinfo			= null;		//返回错误信息
	private String	task_resultinfo		= null;		//返回任务详细信息
	private String  callFlag			= null;		//是否召测-召测/设置
	private String  firstLastFlag		= null;		//用于多步执行的任务
	private String  mpid				= null;		//测量点编号	
	
	private String	dyCommReadData		= null;		//读取数据低压
	private String	dyCommAddres		= null;		//低压通讯-开户
	private String	dyCommPay			= null;		//低压通讯-缴费
	//20131021添加冲正参数
	private String  dyCommRever			= null;		//低压通讯-冲正
	private String  dyCommInit			= null;		//低压通讯-初始化
	
	private String	dyCommCallPara		= null;		//低压通讯-召参数
	private String	dyCommSetPara		= null;		//低压通讯-设参数
	private String	dyCommCtrl			= null;		//低压通讯-控制
	private String	dyCommChgKey		= null;		//低压通讯-下装密钥
	//20131021
	private String	dyCommClear			= null;		//低压通讯-清空电量需量事项
	private int 	paraType 			= 0;		//新老规约识别标识	0：老，1：新
		
	//20131021新增
	//清空事项所对应的键值对。key:从js传进来的事项标识值id，value:标识id对应事项的数据值
	private static final Map<Integer,Integer> clear_events = new HashMap<Integer,Integer>();
	static{
		//电压相关事项
		clear_events.put(1, 0x1000FFFF);	//失压总清
		clear_events.put(2, 0x1001FFFF);	//A相失压记录清零
		clear_events.put(3, 0x1002FFFF);	//B相失压记录清零
		clear_events.put(4, 0x1003FFFF);	//C相失压记录清零
		
		clear_events.put(5, 0x1100FFFF);	//欠压总清
		clear_events.put(6, 0x1101FFFF);	//A相欠压记录清零
		clear_events.put(7, 0x1102FFFF);	//B相欠压记录清零
		clear_events.put(8, 0x1103FFFF);	//C相欠压记录清零
		
		clear_events.put(9, 0x1200FFFF);	//过压总清
		clear_events.put(10, 0x1201FFFF);	//A相过压记录清零
		clear_events.put(11, 0x1202FFFF);	//B相过压记录清零
		clear_events.put(12, 0x1203FFFF);	//C相过压记录清零
		
		clear_events.put(13, 0x1300FFFF);	//断相总清
		clear_events.put(14, 0x1301FFFF);	//A相断相记录清零
		clear_events.put(15, 0x1302FFFF);	//B相断相记录清零
		clear_events.put(16, 0x1303FFFF);	//C相断相记录清零
		clear_events.put(17, 0x030500FF);	//全失压记录清零
		
		clear_events.put(18, 0x0310FFFF);	//总电压合格率记录清零
		clear_events.put(19, 0x031001FF);	//A相电压合格率记录清零
		clear_events.put(20, 0x031002FF);	//B相电压合格率记录清零
		clear_events.put(21, 0x031003FF);	//C相电压合格率记录清零
		
		clear_events.put(22, 0x1400FFFF);	//电压逆相序记录清零
		clear_events.put(23, 0x1600FFFF);	//电压不平衡记录清零
		
		//电流相关事项
		clear_events.put(24, 0x1800FFFF);	//失流总清
		clear_events.put(25, 0x1801FFFF);	//A相失流记录清零
		clear_events.put(26, 0x1802FFFF);	//B相失流记录清零
		clear_events.put(27, 0x1803FFFF);	//C相失流记录清零
		
		clear_events.put(28, 0x1900FFFF);	//过流总清
		clear_events.put(29, 0x1901FFFF);	//A相过流记录清零
		clear_events.put(30, 0x1902FFFF);	//B相过流记录清零
		clear_events.put(31, 0x1903FFFF);	//C相过流记录清零
		
		clear_events.put(32, 0x1A00FFFF);	//断流总清
		clear_events.put(33, 0x1A01FFFF);	//A相断流记录清零
		clear_events.put(34, 0x1A02FFFF);	//B相断流记录清零
		clear_events.put(35, 0x1A03FFFF);	//C相断流记录清零

		
		clear_events.put(36, 0x1B00FFFF);	//潮流反向总清
		clear_events.put(37, 0x1B01FFFF);	//A相潮流反向记录清零
		clear_events.put(38, 0x1B02FFFF);	//B相潮流反向记录清零
		clear_events.put(39, 0x1B03FFFF);	//C相潮流反向记录清零

		clear_events.put(40, 0x1500FFFF);	//电流逆相序记录清零
		clear_events.put(41, 0x1700FFFF);	//电流不平衡记录清零
		
		
		//其他事项
		clear_events.put(42, 0x1C00FFFF);	//过载总清
		clear_events.put(43, 0x1C01FFFF);	//A相过载反向记录清零
		clear_events.put(44, 0x1C02FFFF);	//B相过载反向记录清零
		clear_events.put(45, 0x1C03FFFF);	//C相过载反向记录清零
		
		clear_events.put(46, 0x1D00FFFF);	//跳闸记录清零
		clear_events.put(47, 0x1E00FFFF);	//合闸记录清零
		clear_events.put(48, 0x1F00FFFF);	//总功率因数超下限清零
		clear_events.put(49, 0x030600FF);	//辅助电源失电记录清零
		clear_events.put(50, 0x031100FF);	//掉电记录清零
		clear_events.put(51, 0x033000FF);	//编程记录清零
		clear_events.put(52, 0x033002FF);	//需量记录清零
		clear_events.put(53, 0x033004FF);	//校时记录清零
		clear_events.put(54, 0x033005FF);	//时段表编程记录清零
		clear_events.put(55, 0x033006FF);	//时区表编程记录清零
		clear_events.put(56, 0x033007FF);	//周休日编程记录清零
		clear_events.put(57, 0x033008FF);	//节假日编程记录清零
		clear_events.put(58, 0x033009FF);	//有功组合方式编程记录清零
		clear_events.put(59, 0x03300AFF);	//无功组合方式1编程记录清零	
		clear_events.put(60, 0x03300BFF);	//无功组合方式2编程记录清零
		clear_events.put(61, 0x03300CFF);	//结算日编程记录清零
		clear_events.put(61, 0x03300DFF);	//开表盖编程记录清零
		clear_events.put(63, 0x03300EFF);	//开端钮盒编程记录清零	
	}	
	
	//读取数据低压
	private String DyCommReadData(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, boolean first_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		short read_datatype = 0;
		int i = 0;		
		
		boolean ret_val = false;
		
		//发送
		if (first_flag) {
			if (dyCommReadData != null) dyCommReadData = dyCommReadData.trim();
			if (dyCommReadData == null || dyCommReadData.length() <= 0) {
				op_detail.addTaskInfo("ERR:错误的请求参数");
				return SDDef.FAIL;
			}

			JSONObject json = JSONObject.fromObject(dyCommReadData);
			
			ComntMsg.MSG_READDATA dycomm = new ComntMsg.MSG_READDATA();

			dycomm.mpid  	= mp_id;
			dycomm.ymd 		= CommBase.strtoi(json.getString("ymd"));
			dycomm.datatype = (short)CommBase.strtoi(json.getString("datatype"));
			
			read_datatype = dycomm.datatype;
			
			ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
			dycomm.toDataStream(task_data_vect);

			ret_val = ComntParaBase.sendNStepTask(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
					(byte)ComntDef.YD_YFF_TASKAPPTYPE_READDATA, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, task_data_vect, op_detail);
			
			if (!ret_val) {
				return SDDef.FAIL;
			}
		}

		//接收
		byte []task_result = new byte[1];	
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect = new ArrayList<ComntMsg.MSG_RESULT_DATA>();		

		ret_val = ComntParaBase.getNStepTaskResult(user_name, i_user_data1, i_user_data2, rtu_para, 
				task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);


		dyCommReadData = SDDef.EMPTY;
		if (read_datatype == ComntProtMsg.YFF_CALL_DY_REMAIN) {
			ComntProtMsg.YFF_DATA_DYREMAIN  dyremain = new ComntProtMsg.YFF_DATA_DYREMAIN();
			if (ret_data_vect.size() > 0) {
				ComntMsg.MSG_RESULT_DATA msg_result_data = null;

				for (i = 0; i < ret_data_vect.size(); i++) {
					msg_result_data = ret_data_vect.get(i);
					if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_DY_REALREMAIN) {
						dyremain.fromDataStream(msg_result_data.data_vect, 0);
						dyCommReadData = dyremain.toJsonString();
					}
				}
			}
		}
		else if (read_datatype >= ComntProtMsg.YFF_CALL_REAL_ZBD && read_datatype <= ComntProtMsg.YFF_CALL_DAY_FBD) {
			ComntProtMsg.YFF_DATA_READBD  readbd = new ComntProtMsg.YFF_DATA_READBD();
			if (ret_data_vect.size() > 0) {
				ComntMsg.MSG_RESULT_DATA msg_result_data = null;
				
				for (i = 0; i < ret_data_vect.size(); i++) {
					msg_result_data = ret_data_vect.get(i);
					if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_REAL_ZBD ||
						msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_REAL_FBD ||
						msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_DAY_ZBD  ||
						msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_DAY_FBD) {
						readbd.fromDataStream(msg_result_data.data_vect, 0);
						dyCommReadData = readbd.toJsonString();
					}
				}
			}
		}
		else if (read_datatype == ComntProtMsg.YFF_CALL_DB_TCREMAIN) {
			ComntProtMsg.YFF_DATA_DBREMAIN  dyremain = new ComntProtMsg.YFF_DATA_DBREMAIN();
			if (ret_data_vect.size() > 0) {
				ComntMsg.MSG_RESULT_DATA msg_result_data = null;

				for (i = 0; i < ret_data_vect.size(); i++) {
					msg_result_data = ret_data_vect.get(i);
 					if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_DB_TCREMAIN) {
						dyremain.fromDataStream(msg_result_data.data_vect, 0);
						dyCommReadData = dyremain.toJsonString();
					}
				}
			}
		}
		else if (read_datatype == ComntProtMsg.YFF_CALL_DB_TCOVER) {
			ComntProtMsg.YFF_DATA_DBOVER  dytcover = new ComntProtMsg.YFF_DATA_DBOVER();
			if (ret_data_vect.size() > 0) {
				ComntMsg.MSG_RESULT_DATA msg_result_data = null;

				for (i = 0; i < ret_data_vect.size(); i++) {
					msg_result_data = ret_data_vect.get(i);
					if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_DB_TCOVER) {
						dytcover.fromDataStream(msg_result_data.data_vect, 0);
						dyCommReadData = dytcover.toJsonString();
					}
				}
			}
		}
		//电表状态字不一样需要分离
		//新规约新增了插卡状态字	YFF_DATATYPE_DB_TCCARDSTATE
		else if (read_datatype >= ComntProtMsg.YFF_CALL_DB_STATE1 && read_datatype <= ComntProtMsg.YFF_CALL_DB_STATE7 || read_datatype == ComntProtMsg.YFF_CALL_CARDSTATE) {
			if(paraType == 0){	//老规约
				ComntProtMsg.YFF_DATA_DB_RUNSTATE  dbstate = new ComntProtMsg.YFF_DATA_DB_RUNSTATE();
				
				if (ret_data_vect.size() > 0) {
					ComntMsg.MSG_RESULT_DATA msg_result_data = null;
	
					for (i = 0; i < ret_data_vect.size(); i++) {
						msg_result_data = ret_data_vect.get(i);
						if (msg_result_data.datatype >= ComntProtMsg.YFF_DATATYPE_DB_TCSTATE1 && msg_result_data.datatype <= ComntProtMsg.YFF_DATATYPE_DB_TCSTATE7) {
							dbstate.fromDataStream(msg_result_data.data_vect, 0);
							dyCommReadData = dbstate.toJsonString();
						}
					}
				}
				
			}
			else{	//新规约
				ComntProtMsg.YFF_DATA_DB2_RUNSTATE  dbstate = new ComntProtMsg.YFF_DATA_DB2_RUNSTATE();
				if (ret_data_vect.size() > 0) {
					ComntMsg.MSG_RESULT_DATA msg_result_data = null;
	
					for (i = 0; i < ret_data_vect.size(); i++) {
						msg_result_data = ret_data_vect.get(i);
						if (msg_result_data.datatype >= ComntProtMsg.YFF_DATATYPE_DB_TCSTATE1 && msg_result_data.datatype <= ComntProtMsg.YFF_DATATYPE_DB_TCSTATE7 || read_datatype == ComntProtMsg.YFF_DATATYPE_DB_TCCARDSTATE) {
							dbstate.fromDataStream(msg_result_data.data_vect, 0);
							dyCommReadData = dbstate.toJsonString();
						}
					}
				}
			}
		}
		//20131021添加	begin
		//新规约电表密钥相关信息:密钥条数  ,密钥状态字 
		else if (read_datatype >= ComntProtMsg.YFF_CALL_KEYNUM && read_datatype <= ComntProtMsg.YFF_CALL_KEYSTATE) {
			ComntProtMsg.YFF_DATA_DB2_KEYSTATE dbkeystate = new ComntProtMsg.YFF_DATA_DB2_KEYSTATE();
			if (ret_data_vect.size() > 0) {
				ComntMsg.MSG_RESULT_DATA msg_result_data = null;
				
				for(i = 0; i < ret_data_vect.size(); i++){
					msg_result_data = ret_data_vect.get(i);
					if (msg_result_data.datatype >= ComntProtMsg.YFF_DATATYPE_DB_TCKEYNUM && msg_result_data.datatype <= ComntProtMsg.YFF_DATATYPE_DB_TCKEYSTATE) {
						dbkeystate.fromDataStream(msg_result_data.data_vect, 0);
						dyCommReadData = dbkeystate.toJsonString();
					}
				}
			}
		}
	
		//新规约电表费率相关信息：一套费率，二套费率
		else if (read_datatype >= ComntProtMsg.YFF_CALL_FEI1 && read_datatype <= ComntProtMsg.YFF_CALL_FEI2) {
			ComntProtMsg.YFF_DATA_DB2_DYCALLJTFEE dbfeepara = new ComntProtMsg.YFF_DATA_DB2_DYCALLJTFEE();
			if(ret_data_vect.size() > 0){
				ComntMsg.MSG_RESULT_DATA msg_result_data = null;
				
				for(i = 0; i < ret_data_vect.size(); i++){
					msg_result_data = ret_data_vect.get(i);
					if (msg_result_data.datatype >= ComntProtMsg.YFF_DATATYPE_DB_TCFEI1 && msg_result_data.datatype <= ComntProtMsg.YFF_DATATYPE_DB_TCFEI2) {
						dbfeepara.fromDataStream(msg_result_data.data_vect, 0);
						dyCommReadData = dbfeepara.toJsonString();
					}
				}
			}
		}
		// 新规约电表阶梯费率相关信息：一套阶梯费率，二套阶梯费率
		else if (read_datatype >= ComntProtMsg.YFF_CALL_JTFEI1 && read_datatype <= ComntProtMsg.YFF_CALL_JTFEI2) {
			ComntProtMsg.YFF_DATA_DB2_DYCALLJTFEE dbjtfeepara = new ComntProtMsg.YFF_DATA_DB2_DYCALLJTFEE();
			if(ret_data_vect.size() > 0){
				ComntMsg.MSG_RESULT_DATA msg_result_data = null;
				
				for(i = 0; i < ret_data_vect.size(); i++){
					msg_result_data = ret_data_vect.get(i);
					if (msg_result_data.datatype >= ComntProtMsg.YFF_DATATYPE_DB_TCJTFEI1 && msg_result_data.datatype <= ComntProtMsg.YFF_DATATYPE_DB_TCJTFEI2) {
						dbjtfeepara.fromDataStream(msg_result_data.data_vect, 0);
						dyCommReadData = dbjtfeepara.toJsonString();
					}
				}
			}
		}
		//20131021新增 end
			
		if (ret_val) {
			if (task_result[0] != ComntDef.YD_YFF_TASKRESULT_NULL) firstLastFlag = SDDef.TRUE;
			else firstLastFlag = SDDef.FALSE;
		}
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}	
	
	//低压通讯-开户
	private String DYCommAddres(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, boolean first_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID

		boolean ret_val = false;
		
		//发送
		if (first_flag) {
			if (dyCommAddres != null) dyCommAddres = dyCommAddres.trim();
			if (dyCommAddres == null || dyCommAddres.length() <= 0) {
				op_detail.addTaskInfo("ERR:错误的请求参数");
				return SDDef.FAIL;
			}
			
			JSONObject json = JSONObject.fromObject(dyCommAddres);
			
			//20131019
			//ComntMsg.MSG_DYCOMM_ADDRES dycomm = new ComntMsg.MSG_DYCOMM_ADDRES();
			ComntMsgDy.MSG_DYCOMM_ADDRES dycomm = new ComntMsgDy.MSG_DYCOMM_ADDRES();
			dycomm.mpid  = mp_id;
			ComntFunc.string2Byte(user_name, dycomm.operman);
			
			dycomm.myffalarmid  = (short)CommBase.strtoi(json.getString("myffalarmid"));
			dycomm.buynum 		= CommBase.strtoi("1");

			dycomm.money.pay_money  = CommBase.strtof(json.getString("pay_money"));
			dycomm.money.othjs_money= CommBase.strtof(json.getString("othjs_money"));
			dycomm.money.zb_money	= CommBase.strtof(json.getString("zb_money"));
			dycomm.money.all_money  = CommBase.strtof(json.getString("all_money"));

			ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
			dycomm.toDataStream(task_data_vect);

			ret_val = ComntParaBase.sendNStepTask(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
					(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYCOMM_ADDRES, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, task_data_vect, op_detail);
			
			if (!ret_val) {
				return SDDef.FAIL;
			}
		}
		
		//接收
		byte []task_result = new byte[1];	
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect = new ArrayList<ComntMsg.MSG_RESULT_DATA>();		

		ret_val = ComntParaBase.getNStepTaskResult(user_name, i_user_data1, i_user_data2, rtu_para, 
				task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);
		
		if (ret_val) {
			if (task_result[0] != ComntDef.YD_YFF_TASKRESULT_NULL) firstLastFlag = SDDef.TRUE;
			else firstLastFlag = SDDef.FALSE;
		}
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}	
	
	//低压通讯-缴费
	private String DyCommPay(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, boolean first_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID

		boolean ret_val = false;
		
		//发送
		if (first_flag) {
			if (dyCommPay != null) dyCommPay = dyCommPay.trim();
			if (dyCommPay == null || dyCommPay.length() <= 0) {
				op_detail.addTaskInfo("ERR:错误的请求参数");
				return SDDef.FAIL;
			}
			
			JSONObject json = JSONObject.fromObject(dyCommPay);
			
			//20131019
			//ComntMsg.MSG_DYCOMM_PAY dycomm = new ComntMsg.MSG_DYCOMM_PAY();
			ComntMsgDy.MSG_DYCOMM_PAY dycomm = new ComntMsgDy.MSG_DYCOMM_PAY();
			dycomm.mpid  = mp_id;
			ComntFunc.string2Byte(user_name, dycomm.operman);

			dycomm.buynum 			= CommBase.strtoi(json.getString("buynum"))+1;

			dycomm.money.pay_money  = CommBase.strtof(json.getString("pay_money"));
			dycomm.money.othjs_money= CommBase.strtof(json.getString("othjs_money"));
			dycomm.money.zb_money	= CommBase.strtof(json.getString("zb_money"));
			dycomm.money.all_money  = CommBase.strtof(json.getString("all_money"));

			ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
			dycomm.toDataStream(task_data_vect);

			ret_val = ComntParaBase.sendNStepTask(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
					(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYCOMM_PAY, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, task_data_vect, op_detail);
			
			if (!ret_val) {
				return SDDef.FAIL;
			}
		}
		
		//接收
		byte []task_result = new byte[1];	
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect = new ArrayList<ComntMsg.MSG_RESULT_DATA>();		

		ret_val = ComntParaBase.getNStepTaskResult(user_name, i_user_data1, i_user_data2, rtu_para, 
				task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);
		
		if (ret_val) {
			if (task_result[0] != ComntDef.YD_YFF_TASKRESULT_NULL) firstLastFlag = SDDef.TRUE;
			else firstLastFlag = SDDef.FALSE;
		}
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}	
	
	//20131021添加
	//低压通讯-冲正(新版电表)
	private String DYCommRever(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, boolean first_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail){
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID

		boolean ret_val = false;
		
		//发送
		if (first_flag) {
			if (dyCommRever != null) dyCommRever = dyCommRever.trim();
			if (dyCommRever == null || dyCommRever.length() <= 0) {
				op_detail.addTaskInfo("ERR:错误的请求参数");
				return SDDef.FAIL;
			}
			
			//具体值有变化。
			JSONObject json = JSONObject.fromObject(dyCommRever);
		
			ComntMsgDy.MSG_DYCOMM_REVER dycomm = new ComntMsgDy.MSG_DYCOMM_REVER();
			dycomm.mpid  = mp_id;
			ComntFunc.string2Byte(user_name, dycomm.operman);

			dycomm.buynum 			= CommBase.strtoi(json.getString("buynum"))+1;

			dycomm.money.pay_money  = CommBase.strtof(json.getString("pay_money"));
			dycomm.money.othjs_money= CommBase.strtof(json.getString("othjs_money"));
			dycomm.money.zb_money	= CommBase.strtof(json.getString("zb_money"));
			dycomm.money.all_money  = CommBase.strtof(json.getString("all_money"));

			ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
			dycomm.toDataStream(task_data_vect);

			ret_val = ComntParaBase.sendNStepTask(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
					(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYCOMM_REVER, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, task_data_vect, op_detail);
			
			if (!ret_val) {
				return SDDef.FAIL;
			}
		}
		
		//接收
		byte []task_result = new byte[1];	
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect = new ArrayList<ComntMsg.MSG_RESULT_DATA>();		

		ret_val = ComntParaBase.getNStepTaskResult(user_name, i_user_data1, i_user_data2, rtu_para, 
				task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);
		
		if (ret_val) {
			if (task_result[0] != ComntDef.YD_YFF_TASKRESULT_NULL) firstLastFlag = SDDef.TRUE;
			else firstLastFlag = SDDef.FALSE;
		}

		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}
	
	//低压通讯-召参数
	private String DyCommCallPara(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, boolean first_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		short call_paratype = 0;
		int i = 0;		
		
		boolean ret_val = false;
		
		//发送
		if (first_flag) {
			if (dyCommCallPara != null) dyCommCallPara = dyCommCallPara.trim();
			if (dyCommCallPara == null || dyCommCallPara.length() <= 0) {
				op_detail.addTaskInfo("ERR:错误的请求参数");
				return SDDef.FAIL;
			}

			JSONObject json = JSONObject.fromObject(dyCommCallPara);
			
			//20131019
			ComntMsgDy.MSG_DYCOMM_CALLPARA dycomm = new ComntMsgDy.MSG_DYCOMM_CALLPARA();
			
			dycomm.mpid  	= mp_id;
			dycomm.paratype = (short)CommBase.strtoi(json.getString("paratype"));
			ComntFunc.string2Byte(user_name, dycomm.operman);
			
			call_paratype = dycomm.paratype;
			
			ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
			dycomm.toDataStream(task_data_vect);

			ret_val = ComntParaBase.sendNStepTask(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
					(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYCOMM_CALLPARA, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, task_data_vect, op_detail);
			
			if (!ret_val) {
				return SDDef.FAIL;
			}
		}

		//接收
		byte []task_result = new byte[1];	
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect = new ArrayList<ComntMsg.MSG_RESULT_DATA>();		

		ret_val = ComntParaBase.getNStepTaskResult(user_name, i_user_data1, i_user_data2, rtu_para, 
				task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);


		dyCommCallPara = SDDef.EMPTY;
		switch(call_paratype) {
			case ComntProtMsg.YFF_DY_CALL_PARA:
				ComntProtMsg.YFF_DATA_DYCALLUSER  dypara = new ComntProtMsg.YFF_DATA_DYCALLUSER();
				if (ret_data_vect.size() > 0) {
					ComntMsg.MSG_RESULT_DATA msg_result_data = null;

					for (i = 0; i < ret_data_vect.size(); i++) {
						msg_result_data = ret_data_vect.get(i);
						if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_DY_CALLPARA) {
							dypara.fromDataStream(msg_result_data.data_vect, 0);
							dyCommCallPara = dypara.toJsonString();
						}
					}
				}
				break;
			case ComntProtMsg.YFF_DY_CALL_FEI1:
			case ComntProtMsg.YFF_DY_CALL_FEI2:
				//旧版规约,走ComntProtMsg.YFF_DATA_DYCALLFEE
				if(paraType == 0){
					ComntProtMsg.YFF_DATA_DYCALLFEE  dyfee = new ComntProtMsg.YFF_DATA_DYCALLFEE();
					if (ret_data_vect.size() > 0) {
						ComntMsg.MSG_RESULT_DATA msg_result_data = null;
	
						for (i = 0; i < ret_data_vect.size(); i++) {
							msg_result_data = ret_data_vect.get(i);
							if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_DY_CALLFEI) {
								dyfee.fromDataStream(msg_result_data.data_vect, 0);
								dyCommCallPara = dyfee.toJsonString();
							}
						}
					}
				}
				//新版规约，走ComntProtMsg.YFF_DATA_DYCALLJTFEE
				else{
					ComntProtMsg.YFF_DATA_DYCALLJTFEE  dyjtfee = new ComntProtMsg.YFF_DATA_DYCALLJTFEE();
					if (ret_data_vect.size() > 0) {
						ComntMsg.MSG_RESULT_DATA msg_result_data = null;

						for (i = 0; i < ret_data_vect.size(); i++) {
							msg_result_data = ret_data_vect.get(i);
							if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_DY_CALLFEI) {
								dyjtfee.fromDataStream(msg_result_data.data_vect, 0);
								dyCommCallPara = dyjtfee.toJsonString();
							}
						}
					}		
				}
				break;
			//20131021添加
			//查询状态要分类型
			case ComntProtMsg.YFF_DY_CALL_STATE:
				//如果是老规约表，走YFF_DATA_DYSTATE
				if(paraType == 0){
					ComntProtMsg.YFF_DATA_DYSTATE dystate = new ComntProtMsg.YFF_DATA_DYSTATE();
					if (ret_data_vect.size() > 0) {
						ComntMsg.MSG_RESULT_DATA msg_result_data = null;

						for (i = 0; i < ret_data_vect.size(); i++) {
							msg_result_data = ret_data_vect.get(i);
							if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_DY_CALLSTATE) {
								dystate.fromDataStream(msg_result_data.data_vect, 0);
								dyCommCallPara = dystate.toJsonString();
							}
						}
					}
				}
				//如果是新规约表，走YFF_DATA_DY2STATE
				else{
					ComntProtMsg.YFF_DATA_DY2STATE dystate = new ComntProtMsg.YFF_DATA_DY2STATE();
					if (ret_data_vect.size() > 0) {
						ComntMsg.MSG_RESULT_DATA msg_result_data = null;

						for (i = 0; i < ret_data_vect.size(); i++) {
							msg_result_data = ret_data_vect.get(i);
							if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_DY_CALLSTATE) {
								dystate.fromDataStream(msg_result_data.data_vect, 0);
								dyCommCallPara = dystate.toJsonString();
							}
						}
					}
				}
				break;
			//20131021添加召测阶梯费率begin
			case ComntProtMsg.YFF_DY_CALL_JTFEI1:
			case ComntProtMsg.YFF_DY_CALL_JTFEI2:
				ComntProtMsg.YFF_DATA_DYCALLJTFEE  dyjtfee = new ComntProtMsg.YFF_DATA_DYCALLJTFEE();
				if (ret_data_vect.size() > 0) {
					ComntMsg.MSG_RESULT_DATA msg_result_data = null;

					for (i = 0; i < ret_data_vect.size(); i++) {
						msg_result_data = ret_data_vect.get(i);
						if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_DY_CALLFEIJT) {
							dyjtfee.fromDataStream(msg_result_data.data_vect, 0);
							dyCommCallPara = dyjtfee.toJsonString();
						}
					}
				}
				break;
			//end
		}
		
		if (ret_val) {
			if (task_result[0] != ComntDef.YD_YFF_TASKRESULT_NULL) firstLastFlag = SDDef.TRUE;
			else firstLastFlag = SDDef.FALSE;
		}
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}	
	
	//低压通讯-设参数
	private String DyCommSet(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, boolean first_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID

		boolean ret_val = false;
		
		//发送
		if (first_flag) {
			if (dyCommSetPara != null) dyCommSetPara = dyCommSetPara.trim();
			if (dyCommSetPara == null || dyCommSetPara.length() <= 0) {
				op_detail.addTaskInfo("ERR:错误的请求参数");
				return SDDef.FAIL;
			}
			
			JSONObject json = JSONObject.fromObject(dyCommSetPara);
			
			//20131019  
			//ComntMsg.MSG_DYCOMM_SETPARA dycomm = new ComntMsg.MSG_DYCOMM_SETPARA();
			ComntMsgDy.MSG_DYCOMM_SETPARA dycomm = new ComntMsgDy.MSG_DYCOMM_SETPARA();
			
			dycomm.mpid  = mp_id;
			dycomm.paratype = (short)CommBase.strtoi(json.getString("paratype"));
			ComntFunc.string2Byte(user_name, dycomm.operman);
			
			
			//20131021更改
			//新规范中不能更改表号可以更改户号，
			//老规范中可以更改户号不能更改表号，但走的是同样的消息。在界面中控制
			if(dycomm.paratype >= ComntProtMsg.YFF_DY_SET_USERID && dycomm.paratype <= ComntProtMsg.YFF_DY_SET_METERNO) {
				ComntProtMsg.YFF_DYSET_USERPARA userpara = new ComntProtMsg.YFF_DYSET_USERPARA();
				userpara.mpid = mp_id;

				userpara.parainfo.user_type  = (byte)CommBase.strtoi(json.getString("user_type"));
				userpara.parainfo.bit_update = (byte)CommBase.strtoi(json.getString("bit_update"));
				userpara.parainfo.chg_date 	 = CommBase.strtoi(json.getString("chg_date"));
				userpara.parainfo.chg_time 	 = CommBase.strtoi(json.getString("chg_time"));
				userpara.parainfo.alarm_val1 = CommBase.strtoi(json.getString("alarm_val1"));
				userpara.parainfo.alarm_val2 = CommBase.strtoi(json.getString("alarm_val2"));
				userpara.parainfo.ct 		 = CommBase.strtoi(json.getString("ct"));
				userpara.parainfo.pt 		 = CommBase.strtoi(json.getString("pt"));
				ComntFunc.string2Byte(json.getString("meterno"), userpara.parainfo.meterno);
				ComntFunc.string2Byte(json.getString("userno"), userpara.parainfo.userno);
				
				userpara.toDataStream(dycomm.data_vect);	
			}
			else if (dycomm.paratype >= ComntProtMsg.YFF_DY_FEE_Z && dycomm.paratype <= ComntProtMsg.YFF_DY_FEE_G) {
				ComntProtMsg.YFF_DYSET_FEEPARA dyfee = new ComntProtMsg.YFF_DYSET_FEEPARA();
				dyfee.mpid = mp_id;
				dyfee.feeno = (byte)CommBase.strtoi(json.getString("feeno"));
				
				dyfee.feerate.rated_z = CommBase.strtof(json.getString("rated_z"));
				dyfee.feerate.ratef_j = CommBase.strtof(json.getString("ratef_j"));
				dyfee.feerate.ratef_f = CommBase.strtof(json.getString("ratef_f"));
				dyfee.feerate.ratef_p = CommBase.strtof(json.getString("ratef_p"));
				dyfee.feerate.ratef_g = CommBase.strtof(json.getString("ratef_g"));
				
				dyfee.toDataStream(dycomm.data_vect);
			}
			//20131021添加 begin
			//新规范中更改费率(更改费率界面操作)
			else if(dycomm.paratype >= ComntProtMsg.YFF_DY_SET_JTCHGTIME && dycomm.paratype <= ComntProtMsg.YFF_DY_JTFEE_BLOCK) {
				ComntProtMsg.YFF_DYSET_JTBLOCK_FEEPARA dyjtfee= new ComntProtMsg.YFF_DYSET_JTBLOCK_FEEPARA ();
				dyjtfee.mpid = mp_id;
				dyjtfee.feeno = (byte)CommBase.strtoi(json.getString("feeno"));
				dyjtfee.feerate.jt_chgymd = CommBase.strtoi(json.getString("jt_chgymd"));
				dyjtfee.feerate.jt_cghm = CommBase.strtoi(json.getString("jt_cghm"));
				
				String[] fee_rate = json.getString("fee_rate").split(","),
					   	 jt_step  = json.getString("jt_step").split(","),
				         jt_rate  = json.getString("jt_rate").split(","),
				         jt_jsmdh = json.getString("jt_jsmdh").split(",");
				
				//可以提炼出一个公共方法Strings2ints(把String数组转化成int数组)
				//获取复费率5
				for(int i=0,length = fee_rate.length; i<length; i++){
					dyjtfee.feerate.fee_rate[i] = CommBase.strtoi(fee_rate[i]);
				}

				//获取阶梯值7,当前为3个阶梯，后四个取阶梯的最大值
				int length = (jt_step.length >= 4) ? 4:jt_step.length;
				for(int i=0; i < length; i++){
					dyjtfee.feerate.jt_step[i] = CommBase.strtoi(jt_step[i]);
				}
				
				for(int i = length; i < jt_step.length; i++){
					dyjtfee.feerate.jt_step[i] = CommBase.strtoi(jt_step[length - 1]);
				}

				//获取阶梯费率 zkz
				length = (jt_rate.length >= 4) ? 4:jt_rate.length;
				for(int i=0; i < length; i++){
					dyjtfee.feerate.jt_rate[i] = CommBase.strtoi(jt_rate[i]);	
				}

				for(int i=length; i<jt_rate.length; i++){
					dyjtfee.feerate.jt_rate[i] = CommBase.strtoi(jt_rate[length -1]);
				}
				//end
				
				//获取阶梯年结算日4	
				for(int i=0,jtlength = jt_jsmdh.length; i<jtlength; i++){
					dyjtfee.feerate.jt_jsmdh[i] = CommBase.strtoi(jt_jsmdh[i]);
				}

				dyjtfee.toDataStream(dycomm.data_vect);	
			}
			//end
			
			ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
			dycomm.toDataStream(task_data_vect);

			ret_val = ComntParaBase.sendNStepTask(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
					(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYCOMM_SETPARA, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, task_data_vect, op_detail);
			
			if (!ret_val) {
				return SDDef.FAIL;
			}
		}
		
		//接收
		byte []task_result = new byte[1];	
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect = new ArrayList<ComntMsg.MSG_RESULT_DATA>();		

		ret_val = ComntParaBase.getNStepTaskResult(user_name, i_user_data1, i_user_data2, rtu_para, 
				task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);
		
		if (ret_val) {
			if (task_result[0] != ComntDef.YD_YFF_TASKRESULT_NULL) firstLastFlag = SDDef.TRUE;
			else firstLastFlag = SDDef.FALSE;
		}
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}	
	
	//低压通讯-控制
	private String DyCommCtrl(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, boolean first_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID

		boolean ret_val = false;
		
		//发送
		if (first_flag) {
			if (dyCommCtrl != null) dyCommCtrl = dyCommCtrl.trim();
			if (dyCommCtrl == null || dyCommCtrl.length() <= 0) {
				op_detail.addTaskInfo("ERR:错误的请求参数");
				return SDDef.FAIL;
			}
			
			JSONObject json = JSONObject.fromObject(dyCommCtrl);
			
			//20131019	
			//ComntMsg.MSG_DYCOMM_CTRL dycomm = new ComntMsg.MSG_DYCOMM_CTRL();
			ComntMsgDy.MSG_DYCOMM_CTRL dycomm = new ComntMsgDy.MSG_DYCOMM_CTRL();
			
			dycomm.mpid  = mp_id;
			ComntFunc.string2Byte(user_name, dycomm.operman);
			
			dycomm.ctrlver = (byte)CommBase.strtoi(json.getString("ctrlver"));
			dycomm.paratype = (short)CommBase.strtoi(json.getString("paratype"));

			ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
			dycomm.toDataStream(task_data_vect);

			ret_val = ComntParaBase.sendNStepTask(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
					(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYCOMM_CTRL, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, task_data_vect, op_detail);
			
			if (!ret_val) {
				return SDDef.FAIL;
			}
		}
		
		//接收
		byte []task_result = new byte[1];	
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect = new ArrayList<ComntMsg.MSG_RESULT_DATA>();		

		ret_val = ComntParaBase.getNStepTaskResult(user_name, i_user_data1, i_user_data2, rtu_para, 
				task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);
		
		if (ret_val) {
			if (task_result[0] != ComntDef.YD_YFF_TASKRESULT_NULL) firstLastFlag = SDDef.TRUE;
			else firstLastFlag = SDDef.FALSE;
		}
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}	
	
	//低压通讯-下装密钥
	private String DyCommChgKey(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, boolean first_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID

		boolean ret_val = false;
		
		//发送
		if (first_flag) {
			if (dyCommChgKey != null) dyCommChgKey = dyCommChgKey.trim();
			if (dyCommChgKey == null || dyCommChgKey.length() <= 0) {
				op_detail.addTaskInfo("ERR:错误的请求参数");
				return SDDef.FAIL;
			}
			
			JSONObject json = JSONObject.fromObject(dyCommChgKey);
			
			//20131019
			//ComntMsg.MSG_DYCOMM_CHGKEY dycomm = new ComntMsg.MSG_DYCOMM_CHGKEY();
			ComntMsgDy.MSG_DYCOMM_CHGKEY dycomm = new ComntMsgDy.MSG_DYCOMM_CHGKEY();
			
			dycomm.mpid  = mp_id;
			ComntFunc.string2Byte(user_name, dycomm.operman);

			dycomm.keytype 	= CommBase.strtoi(json.getString("keytype"));
			dycomm.clearkey = (byte)CommBase.strtoi(json.getString("clearkey"));
			dycomm.keyver 	= (byte)CommBase.strtoi(json.getString("keyver"));
			dycomm.initflag = (byte)CommBase.strtoi(json.getString("initflag"));

			ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
			dycomm.toDataStream(task_data_vect);

			ret_val = ComntParaBase.sendNStepTask(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
					(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYCOMM_CHGKEY, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, task_data_vect, op_detail);
			
			if (!ret_val) {
				return SDDef.FAIL;
			}
		}
		
		//接收
		byte []task_result = new byte[1];	
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect = new ArrayList<ComntMsg.MSG_RESULT_DATA>();		

		ret_val = ComntParaBase.getNStepTaskResult(user_name, i_user_data1, i_user_data2, rtu_para, 
				task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);
		
		if (ret_val) {
			if (task_result[0] != ComntDef.YD_YFF_TASKRESULT_NULL) firstLastFlag = SDDef.TRUE;
			else firstLastFlag = SDDef.FALSE;
		}
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}	
	
	//20131021添加
	//低压通讯-新版下装密钥
	private String DyCommChgKey2(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, boolean first_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID

		boolean ret_val = false;
		
		//发送
		if (first_flag) {
			if (dyCommChgKey != null) dyCommChgKey = dyCommChgKey.trim();
			if (dyCommChgKey == null || dyCommChgKey.length() <= 0) {
				op_detail.addTaskInfo("ERR:错误的请求参数");
				return SDDef.FAIL;
			}
			
			JSONObject json = JSONObject.fromObject(dyCommChgKey);
			
			//20131019
			ComntMsgDy.MSG_DYCOMM_CHGKEY2 dycomm = new ComntMsgDy.MSG_DYCOMM_CHGKEY2();
			
			dycomm.mpid  = mp_id;
			ComntFunc.string2Byte(user_name, dycomm.operman);

			dycomm.keysum 	= (byte)CommBase.strtoi(json.getString("keysum"));
			dycomm.key_id 	= (byte)CommBase.strtoi(json.getString("key_id"));
			dycomm.keynum 	= (byte)CommBase.strtoi(json.getString("keynum"));
			dycomm.keyflag 	= (byte)CommBase.strtoi(json.getString("keyflag"));

			ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
			dycomm.toDataStream(task_data_vect);

			ret_val = ComntParaBase.sendNStepTask(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
					(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYCOMM_CHGKEY2, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, task_data_vect, op_detail);
			
			if (!ret_val) {
				return SDDef.FAIL;
			}
		}
		
		//接收
		byte []task_result = new byte[1];	
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect = new ArrayList<ComntMsg.MSG_RESULT_DATA>();		

		ret_val = ComntParaBase.getNStepTaskResult(user_name, i_user_data1, i_user_data2, rtu_para, 
				task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);
		
		if (ret_val) {
			if (task_result[0] != ComntDef.YD_YFF_TASKRESULT_NULL) firstLastFlag = SDDef.TRUE;
			else firstLastFlag = SDDef.FALSE;
		}
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}
	
	//20131021添加
	//低压通讯-清空清空   电量 需量 事项
	private String DyCommClear(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, boolean first_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail){
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID

		boolean ret_val = false;
		
		//发送
		if (first_flag) {
			if (dyCommClear != null) dyCommClear = dyCommClear.trim();
			if (dyCommClear == null || dyCommClear.length() <= 0) {
				op_detail.addTaskInfo("ERR:错误的请求参数");
				return SDDef.FAIL;
			}
		
			JSONObject json = JSONObject.fromObject(dyCommClear);	
	
			ComntMsgDy.MSG_DYCOMM_CLEAR dycomm = new ComntMsgDy.MSG_DYCOMM_CLEAR();
			
			dycomm.mpid  = mp_id;
			ComntFunc.string2Byte(user_name, dycomm.operman);
	
			dycomm.ctrlver 	= (byte)CommBase.strtoi(json.getString("ctrlver"));
			dycomm.paratype = (short)CommBase.strtoi(json.getString("paratype"));
			
			int eve_type = CommBase.strtoi(json.getString("clear_eve_type"));
			
			//需量电量的clear_eve_type 设置为0
			if(eve_type == 0){
				dycomm.clear_eve_type = 0;
			}
			//事项则从clear_events中根据传进来的id获取真正的值
			else{
				dycomm.clear_eve_type = clear_events.get(CommBase.strtoi(json.getString("clear_eve_type")));
			}
			
			ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
			dycomm.toDataStream(task_data_vect);
	
			ret_val = ComntParaBase.sendNStepTask(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
					(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYCOMM_CLEAR, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, task_data_vect, op_detail);
			
			if (!ret_val) {
				return SDDef.FAIL;
			}
		}
	
		//接收
		byte []task_result = new byte[1];	
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect = new ArrayList<ComntMsg.MSG_RESULT_DATA>();		
	
		ret_val = ComntParaBase.getNStepTaskResult(user_name, i_user_data1, i_user_data2, rtu_para, 
				task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);
		
		if (ret_val) {
			if (task_result[0] != ComntDef.YD_YFF_TASKRESULT_NULL) firstLastFlag = SDDef.TRUE;
			else firstLastFlag = SDDef.FALSE;
		}
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}
	
	//20131021 添加
	//低压通讯-预置金额(钱包初始化)
	private String DYCommInit(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, boolean first_flag, ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail){
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID

		boolean ret_val = false;
		
		//发送
		if (first_flag) {
			if (dyCommInit != null) dyCommInit = dyCommInit.trim();
			if (dyCommInit == null || dyCommInit.length() <= 0) {
				op_detail.addTaskInfo("ERR:错误的请求参数");
				return SDDef.FAIL;
			}
			
			//具体值有变化。
			JSONObject json = JSONObject.fromObject(dyCommInit);
		
			ComntMsgDy.MSG_DYCOMM_ININT dycomm = new ComntMsgDy.MSG_DYCOMM_ININT();
			dycomm.mpid  = mp_id;
			ComntFunc.string2Byte(user_name, dycomm.operman);

			dycomm.buynum 			= CommBase.strtoi(json.getString("buynum"));

			dycomm.money.pay_money  = CommBase.strtof(json.getString("pay_money"));
			dycomm.money.othjs_money= CommBase.strtof(json.getString("othjs_money"));
			dycomm.money.zb_money	= CommBase.strtof(json.getString("zb_money"));
			dycomm.money.all_money  = CommBase.strtof(json.getString("all_money"));

			ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
			dycomm.toDataStream(task_data_vect);

			ret_val = ComntParaBase.sendNStepTask(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
					(byte)ComntDef.YD_YFF_TASKAPPTYPE_DYCOMM_ININT, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, task_data_vect, op_detail);
			
			if (!ret_val) {
				return SDDef.FAIL;
			}
		}
		
		//接收
		byte []task_result = new byte[1];	
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect = new ArrayList<ComntMsg.MSG_RESULT_DATA>();		

		ret_val = ComntParaBase.getNStepTaskResult(user_name, i_user_data1, i_user_data2, rtu_para, 
				task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);
		
		if (ret_val) {
			if (task_result[0] != ComntDef.YD_YFF_TASKRESULT_NULL) firstLastFlag = SDDef.TRUE;
			else firstLastFlag = SDDef.FALSE;
		}
		
		
		return ret_val ? SDDef.SUCCESS : SDDef.FAIL;
	}
	
	public String taskProc(){
		ComntParaBase.OpDetailInfo op_detail = new ComntParaBase.OpDetailInfo();
		StringBuffer err_str_1 				 = new StringBuffer();
		ComntParaBase.SALEMAN_TASK_RESULT_DETAIL 	task_result_detail = new ComntParaBase.SALEMAN_TASK_RESULT_DETAIL();
		
		int rtu_id    = CommBase.strtoi(userData1);
		int userop_id = CommBase.strtoi(userData2); 
		short mp_id   = (short)CommBase.strtoi(mpid);
		
		//int op_result = 0;	//返回结果
		
		ComntParaBase.RTU_PARA rtu_para = ComntParaBase.loadRtuActPara(rtu_id);
		
		if (rtu_para == null) {
			result = SDDef.FAIL;
			op_detail.addTaskInfo("ERR:无效的终端ID[" + rtu_id + "]");
			detailInfo = op_detail.toJsonString(); 
			return SDDef.SUCCESS;
		}
		String user_name = ComntParaBase.getUserName();
		
		String ret_val = SDDef.FAIL;
		if (userop_id == ComntUseropDef.YFF_READDATA) {					//读取数据低压
			String firstlast_str = firstLastFlag.trim();
			boolean first_flag = (firstlast_str.compareToIgnoreCase(SDDef.TRUE) == 0);
			
			ret_val = DyCommReadData(user_name, rtu_para, mp_id, first_flag, op_detail, err_str_1, task_result_detail);
		}
		else if (userop_id == ComntUseropDef.YFF_DYCOMM_ADDRES) {		//低压通讯-开户
			String firstlast_str = firstLastFlag.trim();
			boolean first_flag = (firstlast_str.compareToIgnoreCase(SDDef.TRUE) == 0);
			
			ret_val = DYCommAddres(user_name, rtu_para, mp_id, first_flag, op_detail, err_str_1, task_result_detail);
		}
		else if (userop_id == ComntUseropDef.YFF_DYCOMM_PAY) {			//低压通讯-缴费
			String firstlast_str = firstLastFlag.trim();
			boolean first_flag = (firstlast_str.compareToIgnoreCase(SDDef.TRUE) == 0);
			
			ret_val = DyCommPay(user_name, rtu_para, mp_id, first_flag, op_detail, err_str_1, task_result_detail);
		}
		//20131021新增冲正操作
		else if (userop_id == ComntUseropDef.YFF_DYCOMM_REVER) {			//低压通讯-冲正(远程)
			String firstlast_str = firstLastFlag.trim();
			boolean first_flag = (firstlast_str.compareToIgnoreCase(SDDef.TRUE) == 0);
			
			ret_val = DYCommRever(user_name, rtu_para, mp_id, first_flag, op_detail, err_str_1, task_result_detail);
		}
		else if (userop_id == ComntUseropDef.YFF_DYCOMM_CALLPARA) {		//低压通讯-召参数
			String firstlast_str = firstLastFlag.trim();
			boolean first_flag = (firstlast_str.compareToIgnoreCase(SDDef.TRUE) == 0);
			
			ret_val = DyCommCallPara(user_name, rtu_para, mp_id, first_flag, op_detail, err_str_1, task_result_detail);
		}
		else if (userop_id == ComntUseropDef.YFF_DYCOMM_SETPARA) {		//低压通讯-设参数
			String firstlast_str = firstLastFlag.trim();
			boolean first_flag = (firstlast_str.compareToIgnoreCase(SDDef.TRUE) == 0);
			
			ret_val = DyCommSet(user_name, rtu_para, mp_id, first_flag, op_detail, err_str_1, task_result_detail);
		}
		else if (userop_id == ComntUseropDef.YFF_DYCOMM_CTRL) {			//低压通讯-控制
			String firstlast_str = firstLastFlag.trim();
			boolean first_flag = (firstlast_str.compareToIgnoreCase(SDDef.TRUE) == 0);
			
			ret_val = DyCommCtrl(user_name, rtu_para, mp_id, first_flag, op_detail, err_str_1, task_result_detail);
		}
		else if (userop_id == ComntUseropDef.YFF_DYCOMM_CHGKEY) {		//低压通讯-下装密钥
			String firstlast_str = firstLastFlag.trim();
			boolean first_flag = (firstlast_str.compareToIgnoreCase(SDDef.TRUE) == 0);
			ret_val = DyCommChgKey(user_name, rtu_para, mp_id, first_flag, op_detail, err_str_1, task_result_detail);		
			
		}
		else if(userop_id == ComntUseropDef.YFF_DYCOMM_CHGKEY2){			//低压通讯-新版下装密钥
			String firstlast_str = firstLastFlag.trim();
			boolean first_flag = (firstlast_str.compareToIgnoreCase(SDDef.TRUE) == 0);
			ret_val = DyCommChgKey2(user_name, rtu_para, mp_id, first_flag, op_detail, err_str_1, task_result_detail);
		}
		else if(userop_id == ComntUseropDef.YFF_DYCOMM_CLEAR){			//低压通讯-清空清空   电量 需量 事项
			String firstlast_str = firstLastFlag.trim();
			boolean first_flag = (firstlast_str.compareToIgnoreCase(SDDef.TRUE) == 0);
			ret_val = DyCommClear(user_name, rtu_para, mp_id, first_flag, op_detail, err_str_1, task_result_detail);
		}
		//20131021新增初始化操作
		else if (userop_id == ComntUseropDef.YFF_DYCOMM_ININT) {		//低压通讯-初始化(设置预置金额)
			String firstlast_str = firstLastFlag.trim();
			boolean first_flag = (firstlast_str.compareToIgnoreCase(SDDef.TRUE) == 0);
			
			ret_val = DYCommInit(user_name, rtu_para, mp_id, first_flag, op_detail, err_str_1, task_result_detail);
		}
		else {
			op_detail.addTaskInfo("ERR:未知的操作类型[" + userop_id + "]");
		}
		result 			= ret_val;
		err_strinfo		= err_str_1.toString();
//		task_resultinfo = task_result_detail;	//以后再详细描述
		detailInfo = op_detail.toJsonString();
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
	
	public String getDyCommReadData() {
		return dyCommReadData;
	}
	public void setDyCommReadData(String dyCommReadData) {
		this.dyCommReadData = dyCommReadData;
	}
	public String getMpid() {
		return mpid;
	}
	public void setMpid(String mpid) {
		this.mpid = mpid;
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
	public String getErr_strinfo() {
		return err_strinfo;
	}
	public void setErr_strinfo(String errStrinfo) {
		err_strinfo = errStrinfo;
	}
	public String getTask_resultinfo() {
		return task_resultinfo;
	}
	public void setTask_resultinfo(String taskResultinfo) {
		task_resultinfo = taskResultinfo;
	}
	public String getCallFlag() {
		return callFlag;
	}
	public void setCallFlag(String callFlag) {
		this.callFlag = callFlag;
	}
	public String getFirstLastFlag() {
		return firstLastFlag;
	}
	public void setFirstLastFlag(String firstLastFlag) {
		this.firstLastFlag = firstLastFlag;
	}
	public String getDyCommAddres() {
		return dyCommAddres;
	}
	public void setDyCommAddres(String dyCommAddres) {
		this.dyCommAddres = dyCommAddres;
	}
	public String getDyCommPay() {
		return dyCommPay;
	}
	public void setDyCommPay(String dyCommPay) {
		this.dyCommPay = dyCommPay;
	}
	public String getDyCommCallPara() {
		return dyCommCallPara;
	}
	public void setDyCommCallPara(String dyCommCallPara) {
		this.dyCommCallPara = dyCommCallPara;
	}
	public String getDyCommSetPara() {
		return dyCommSetPara;
	}
	public void setDyCommSetPara(String dyCommSetPara) {
		this.dyCommSetPara = dyCommSetPara;
	}
	public String getDyCommCtrl() {
		return dyCommCtrl;
	}
	public void setDyCommCtrl(String dyCommCtrl) {
		this.dyCommCtrl = dyCommCtrl;
	}
	public String getDyCommChgKey() {
		return dyCommChgKey;
	}
	public void setDyCommChgKey(String dyCommChgKey) {
		this.dyCommChgKey = dyCommChgKey;
	}
	public String getDyCommInit() {
		return dyCommInit;
	}
	public void setDyCommInit(String dyCommInit) {
		this.dyCommInit = dyCommInit;
	}
	public static Map<Integer, Integer> getClearEvents() {
		return clear_events;
	}
	public String getDyCommRever() {
		return dyCommRever;
	}
	public void setDyCommRever(String dyCommRever) {
		this.dyCommRever = dyCommRever;
	}
	public int getParaType() {
		return paraType;
	}
	public void setParaType(int paraType) {
		this.paraType = paraType;
	}
	public String getDyCommClear() {
		return dyCommClear;
	}
	public void setDyCommClear(String dyCommClear) {
		this.dyCommClear = dyCommClear;
	}

}
