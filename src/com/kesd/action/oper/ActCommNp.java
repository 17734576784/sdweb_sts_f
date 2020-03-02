package com.kesd.action.oper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.comnt.ComntDef;
import com.kesd.comnt.ComntMsg;
import com.kesd.comnt.ComntMsgGy;
import com.kesd.comnt.ComntMsgProc;
import com.kesd.comnt.ComntProtMsg;
import com.kesd.comntpara.ComntParaBase;
import com.kesd.comntpara.ComntUseropDef;
import com.kesd.comntpara.ComntParaBase.OpDetailInfo;
import com.libweb.common.CommBase;
import com.libweb.comnt.ComntFunc;
import com.libweb.comnt.ComntVector;
import com.libweb.dao.JDBCDao;

public class ActCommNp {
	
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
	
	
	private String	npCommReadData		= null;		//读取数据农排
	private String	npCommCallBase		= null;		//召测基本数据农排
	private String	npCommSetBase		= null;		//设置基本数据农排
	private String  npCommCallWlPara	= null;		//召测白名单参数
	private String  npCommCallBlPara	= null;		//召测黑名单参数
	private String  npCommSetWlPara		= null;		//设置白名单参数
	private String  npCommSetBlPara		= null;		//设置黑名单参数
	
	
	public String retFarmInfo() {
		
		JDBCDao jdao = new JDBCDao();
		String sql = "select a.describe f_desc,a.card_no from farmerpara a,meter_extparanp b where a.area_id=b.area_id and b.rtu_id=" + result;
		List<Map<String,Object>> list = jdao.result(sql);
		if(list.size() <= 0) {
			result = "";
			return SDDef.SUCCESS;
		}
		
		StringBuffer ret = new StringBuffer();
		ret.append("{rows:[");
		
		for(int t = 0; t < list.size(); t++) {
			Map<String,Object> map = list.get(t);
			ret.append("{farmer_desc:\""+CommFunc.CheckString(map.get("f_desc"))+"\",card_no:\""+CommFunc.CheckString(map.get("card_no"))+"\"},");
		}
		ret.deleteCharAt(ret.length() - 1);
		ret.append("]}");
		result = ret.toString();
		return SDDef.SUCCESS;
	}
	
	
	//读取数据农排
	private String NpCommReadData(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, /*boolean first_flag, */ComntParaBase.OpDetailInfo op_detail, 
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		short read_datatype = 0;
		int i = 0;
		
		boolean ret_val = false;
		
		//发送
		if (npCommReadData != null) npCommReadData = npCommReadData.trim();
		if (npCommReadData == null || npCommReadData.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}

		JSONObject json = JSONObject.fromObject(npCommReadData);
		
		ComntMsg.MSG_READDATA npcomm = new ComntMsg.MSG_READDATA();

		npcomm.mpid  	= mp_id;
		npcomm.ymd 		= CommBase.strtoi(json.getString("ymd"));
		npcomm.datatype = (short)CommBase.strtoi(json.getString("datatype"));
		
		read_datatype = npcomm.datatype;
		
		ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
		npcomm.toDataStream(task_data_vect);


		//接收
		byte []task_result = new byte[1];	
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect = new ArrayList<ComntMsg.MSG_RESULT_DATA>();		

		ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_READDATA, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect, task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);
				
		boolean data_flag = false;
		ComntMsg.MSG_RESULT_DATA msg_result_data = null;
		
		boolean ret_val1 = false;		

		npCommReadData = SDDef.EMPTY;
		//农灌表用户用电记录
		if (read_datatype >= ComntProtMsg.YFF_CALL_DL645_YDJL_FIR && read_datatype <= ComntProtMsg.YFF_CALL_DL645_YDJL_FIN) {
			ComntProtMsg.YFF_DATA_NPCOMM_YDJL  npydjl = new ComntProtMsg.YFF_DATA_NPCOMM_YDJL();
			if (ret_data_vect.size() > 0) {

				for (i = 0; i < ret_data_vect.size(); i++) {
					msg_result_data = ret_data_vect.get(i);
					if (msg_result_data.datatype >= ComntProtMsg.YFF_CALL_DL645_YDJL_FIR 
							&& msg_result_data.datatype <= ComntProtMsg.YFF_CALL_DL645_YDJL_FIN) {
						
						npydjl.fromDataStream(msg_result_data.data_vect, 0);
						data_flag = true;
						
						npCommReadData = npydjl.toJsonString();
						break;
					}
				}
			}
			
			if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
		}
		//存储表的总用电量和总故障电量
		else if (read_datatype == ComntProtMsg.YFF_CALL_DL645_DLJL) {
			ComntProtMsg.YFF_DATA_NPCOMM_DLJL  npdljl = new ComntProtMsg.YFF_DATA_NPCOMM_DLJL();
			if (ret_data_vect.size() > 0) {
				
				for (i = 0; i < ret_data_vect.size(); i++) {
					msg_result_data = ret_data_vect.get(i);
					if (msg_result_data.datatype == ComntProtMsg.YFF_CALL_DL645_DLJL) {
						
						npdljl.fromDataStream(msg_result_data.data_vect, 0);
						data_flag = true;
						
						npCommReadData = npdljl.toJsonString();
						break;
					}
				}
			}
			
			if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
		}
		//存储20条挂起用户记录
		else if (read_datatype >= ComntProtMsg.YFF_CALL_DL645_GQJL_FIR && read_datatype <= ComntProtMsg.YFF_CALL_DL645_GQJL_FIN) {
			ComntProtMsg.YFF_DATA_NPCOMM_GQJL  npgqjl = new ComntProtMsg.YFF_DATA_NPCOMM_GQJL();
			if (ret_data_vect.size() > 0) {
				
				for (i = 0; i < ret_data_vect.size(); i++) {
					msg_result_data = ret_data_vect.get(i);
					if (msg_result_data.datatype >= ComntProtMsg.YFF_CALL_DL645_GQJL_FIR 
							&& msg_result_data.datatype <= ComntProtMsg.YFF_CALL_DL645_GQJL_FIN) {
						
						npgqjl.fromDataStream(msg_result_data.data_vect, 0);
						data_flag = true;
						
						npCommReadData = npgqjl.toJsonString();
						break;
					}
				}
			}
			
			if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
		}
		//存储最近12个月的结算电量
		else if (read_datatype >= ComntProtMsg.YFF_CALL_DL645_JSJL_FIR && read_datatype <= ComntProtMsg.YFF_CALL_DL645_JSJL_FIN) {
			ComntProtMsg.YFF_DATA_NPCOMM_JSJL  npjsjl = new ComntProtMsg.YFF_DATA_NPCOMM_JSJL();
			if (ret_data_vect.size() > 0) {

				for (i = 0; i < ret_data_vect.size(); i++) {
					msg_result_data = ret_data_vect.get(i);
					if (msg_result_data.datatype >= ComntProtMsg.YFF_CALL_DL645_JSJL_FIR 
							&& msg_result_data.datatype <= ComntProtMsg.YFF_CALL_DL645_JSJL_FIN) {
						
						npjsjl.fromDataStream(msg_result_data.data_vect, 0);
						data_flag = true;
						
						npCommReadData = npjsjl.toJsonString();
						break;
					}
				}
			}
			
			if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
		}
		//存储最近50条参数设置记录
		else if (read_datatype >= ComntProtMsg.YFF_CALL_DL645_CSJL_FIR && read_datatype <= ComntProtMsg.YFF_CALL_DL645_CSJL_FIN) {
			ComntProtMsg.YFF_DATA_NPCOMM_CSJL  npcsjl = new ComntProtMsg.YFF_DATA_NPCOMM_CSJL();
			if (ret_data_vect.size() > 0) {

				for (i = 0; i < ret_data_vect.size(); i++) {
					msg_result_data = ret_data_vect.get(i);
					if (msg_result_data.datatype >= ComntProtMsg.YFF_CALL_DL645_CSJL_FIR 
							&& msg_result_data.datatype <= ComntProtMsg.YFF_CALL_DL645_CSJL_FIN) {
						
						npcsjl.fromDataStream(msg_result_data.data_vect, 0);
						data_flag = true;
						
						npCommReadData = npcsjl.toJsonString();
						break;
					}
				}
			}
			
			if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
		}
		//存储20条刷卡故障用户记录
		else if (read_datatype >= ComntProtMsg.YFF_CALL_DL645_GZJL_FIR && read_datatype <= ComntProtMsg.YFF_CALL_DL645_GZJL_FIN) {
			ComntProtMsg.YFF_DATA_NPCOMM_GZJL  npgzjl = new ComntProtMsg.YFF_DATA_NPCOMM_GZJL();
			if (ret_data_vect.size() > 0) {

				for (i = 0; i < ret_data_vect.size(); i++) {
					msg_result_data = ret_data_vect.get(i);
					if (msg_result_data.datatype >= ComntProtMsg.YFF_CALL_DL645_GZJL_FIR 
							&& msg_result_data.datatype <= ComntProtMsg.YFF_CALL_DL645_GZJL_FIN) {
						
						npgzjl.fromDataStream(msg_result_data.data_vect, 0);
						data_flag = true;
						
						npCommReadData = npgzjl.toJsonString();
						break;
					}
				}
			}
			
			if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
		}
		//存储用户的过零电量
		else if (read_datatype >= ComntProtMsg.YFF_CALL_DL645_GLJL_FIR && read_datatype <= ComntProtMsg.YFF_CALL_DL645_GLJL_FIN) {
			ComntProtMsg.YFF_DATA_NPCOMM_GLJL  npgljl = new ComntProtMsg.YFF_DATA_NPCOMM_GLJL();
			if (ret_data_vect.size() > 0) {

				for (i = 0; i < ret_data_vect.size(); i++) {
					msg_result_data = ret_data_vect.get(i);
					if (msg_result_data.datatype >= ComntProtMsg.YFF_CALL_DL645_GLJL_FIR 
							&& msg_result_data.datatype <= ComntProtMsg.YFF_CALL_DL645_GLJL_FIN) {
						
						npgljl.fromDataStream(msg_result_data.data_vect, 0);
						data_flag = true;
						
						npCommReadData = npgljl.toJsonString();
						break;
					}
				}
			}
			
			if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
		}
		//河南清丰农灌表用户用电记录
		else if (read_datatype >= ComntProtMsg.YFF_CALL_HNQF_YDJL_FIR && read_datatype <= ComntProtMsg.YFF_CALL_HNQF_YDJL_FIN) {
			ComntProtMsg.YFF_HNQF_NPCOMM_YDJL  npydjl = new ComntProtMsg.YFF_HNQF_NPCOMM_YDJL();
			if (ret_data_vect.size() > 0) {

				for (i = 0; i < ret_data_vect.size(); i++) {
					msg_result_data = ret_data_vect.get(i);
					if (msg_result_data.datatype >= ComntProtMsg.YFF_CALL_HNQF_YDJL_FIR 
							&& msg_result_data.datatype <= ComntProtMsg.YFF_CALL_HNQF_YDJL_FIN) {
						
						npydjl.fromDataStream(msg_result_data.data_vect, 0);
						if (npydjl.comm_ydjl == null) {
							data_flag = false;
						}
						else {
							npCommReadData = npydjl.toJsonString();
							data_flag = true;
						}
						break;
					}
				}
			}
			
			if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
		}
		else if (read_datatype >= ComntProtMsg.YFF_CALL_HNQF_GQJL_FIR && read_datatype <= ComntProtMsg.YFF_CALL_HNQF_GQJL_FIN) {
			ComntProtMsg.YFF_HNQF_NPCOMM_GQJL  npgqjl = new ComntProtMsg.YFF_HNQF_NPCOMM_GQJL();
			if (ret_data_vect.size() > 0) {
				
				for (i = 0; i < ret_data_vect.size(); i++) {
					msg_result_data = ret_data_vect.get(i);
					if (msg_result_data.datatype >= ComntProtMsg.YFF_CALL_HNQF_GQJL_FIR 
							&& msg_result_data.datatype <= ComntProtMsg.YFF_CALL_HNQF_GQJL_FIN) {
						
						npgqjl.fromDataStream(msg_result_data.data_vect, 0);
						if (npgqjl.comm_gqjl == null) {
							data_flag = false;
						}
						else {
							npCommReadData = npgqjl.toJsonString();
							data_flag = true;
						}
						break;
					}
				}
			}
			
			if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
		}
		else if (read_datatype >= ComntProtMsg.YFF_CALL_HNQF_GZJL_FIR && read_datatype <= ComntProtMsg.YFF_CALL_HNQF_GZJL_FIN) {
			ComntProtMsg.YFF_HNQF_NPCOMM_GZJL  npgzjl = new ComntProtMsg.YFF_HNQF_NPCOMM_GZJL();
			if (ret_data_vect.size() > 0) {
				
				for (i = 0; i < ret_data_vect.size(); i++) {
					msg_result_data = ret_data_vect.get(i);
					if (msg_result_data.datatype >= ComntProtMsg.YFF_CALL_HNQF_GZJL_FIR 
							&& msg_result_data.datatype <= ComntProtMsg.YFF_CALL_HNQF_GZJL_FIN) {
						npgzjl.fromDataStream(msg_result_data.data_vect, 0);
						
						if (npgzjl.comm_gzjl == null) {
							data_flag = false;
						}
						else {
							npCommReadData = npgzjl.toJsonString();
							data_flag = true;
						}
					
						break;
					}
				}
			}
			
			if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
		}
		else if (read_datatype >= ComntProtMsg.YFF_CALL_HNQF_CSJL_FIR && read_datatype <= ComntProtMsg.YFF_CALL_HNQF_CSJL_FIN) {
			ComntProtMsg.YFF_HNQF_NPCOMM_CSJL  npcsjl = new ComntProtMsg.YFF_HNQF_NPCOMM_CSJL();
			if (ret_data_vect.size() > 0) {
				
				for (i = 0; i < ret_data_vect.size(); i++) {
					msg_result_data = ret_data_vect.get(i);
					if (msg_result_data.datatype >= ComntProtMsg.YFF_CALL_HNQF_CSJL_FIR 
							&& msg_result_data.datatype <= ComntProtMsg.YFF_CALL_HNQF_CSJL_FIN) {
						
						npcsjl.fromDataStream(msg_result_data.data_vect, 0);

						if (npcsjl.comm_csjl == null) {
							data_flag = false;
						}
						else {
							data_flag = true;
						
							npCommReadData = npcsjl.toJsonString();
						}
						break;
					}
				}
			}
			
			if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
		}
		
		
		return ret_val1 ? SDDef.SUCCESS : SDDef.FAIL;
	}
	
	
	private String NpCommWriteDB(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, ComntParaBase.OpDetailInfo op_detail,
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws SQLException {
		int rtuId = CommBase.strtoi(userData1);	//RTU ID		
		
		int i = 0;
		
		boolean ret_val = false;
		
		if (npCommSetBase != null) npCommSetBase = npCommSetBase.trim();
		if (npCommSetBase == null || npCommSetBase.length() <= 0 || npCommSetBase.equals("{\"rows\":]}") ){
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}

		JSONObject json = JSONObject.fromObject(npCommSetBase);
		JSONArray  j_array = json.getJSONArray("rows");
		JSONObject j_obj2  = null;	
 	
		for (int j = 0; j < j_array.size(); j++) {
			j_obj2 = j_array.getJSONObject(j);
			
			String sdate = j_obj2.getString("begendate").substring(0,11).replace("年", "").replace("月", "").replace("日", "");
			String stime = j_obj2.getString("begendate").substring(12,21).replace("时", "").replace("分", "").replace("秒", "");
			String edate = j_obj2.getString("enddate").substring(0,11).replace("年", "").replace("月", "").replace("日", "");
			String etime = j_obj2.getString("enddate").substring(12,21).replace("时", "").replace("分", "").replace("秒", "");
			String cardno = j_obj2.getString("cardno");
			String fee  = j_obj2.getString("fee").equals("") ? "0.000" : j_obj2.getString("fee");
			String usemoney = j_obj2.getString("usemoney").equals("") ? "0.000" : j_obj2.getString("usemoney");
			String remainmoney = j_obj2.getString("remainmoney").equals("") ? "0.000" : j_obj2.getString("remainmoney");
			String usedl = j_obj2.getString("usedl").equals("") ? "0.000" : j_obj2.getString("usedl");
			String zerodl = j_obj2.getString("zerodl").equals("") ? "0.000" : j_obj2.getString("zerodl");
			String state = j_obj2.getString("state").length()==0 ? "0" : j_obj2.getString("state");
			if(state.equals("正常")){
				state = "0"; 
			}else if(state.equals("系统停电")){
				state = "1";
			}else if(state.equals("无脉冲自动拉闸")){
				state = "2";
			}else if(state.equals("人为锁表")){
				state = "3";
			}	

			JDBCDao dao = new JDBCDao();
			String checksql = "select * from yddataben.dbo.npyd_record"+Calendar.getInstance().get(Calendar.YEAR)+" where rtu_id ="+rtuId +" and mp_id ="+mpid +" and begin_date = "+sdate+" and begin_time = "+stime;
			String sql = "";
			String farmsql ="select area_id,id,farmer_no from farmerpara where card_no = '"+ cardno+"'";
			ResultSet rs =dao.executeQuery(farmsql);
			String areaId="",farmId="",farnNo="";
			if(rs.next()){
				areaId = String.valueOf(rs.getInt(1));
				farmId = String.valueOf(rs.getShort(2));
				farnNo = rs.getString(3);
			}
			
			if(dao.executeQuery(checksql).next()){
				sql = "update yddataben.dbo.npyd_record"+Calendar.getInstance().get(Calendar.YEAR) 
		 		+" set area_id = "+areaId+", farmer_id = "+farmId+", farmer_no = "+farnNo+",card_no = "+cardno+",end_date ="+edate+",end_time ="+etime+",fee = "+fee+",use_money = "+usemoney+",remain_money = "+remainmoney+",use_dl = "+usedl+",zero_dl="+zerodl+",farmer_state = "+state +" where rtu_id ="+rtuId+" and  mp_id ="+mpid  +" and begin_date = "+sdate+"  and begin_time = "+stime;
			}else{
				sql = "insert into  yddataben.dbo.npyd_record"+Calendar.getInstance().get(Calendar.YEAR) 
		 		+" (rtu_id,mp_id,area_id,farmer_id,farmer_no,card_no,begin_date,begin_time,end_date,end_time,fee,use_money,remain_money,use_dl,zero_dl,farmer_state) values("
		 		+rtuId+","+mpid+","+areaId+","+farmId+","+farnNo+","+cardno+","+sdate+","+stime+","+edate+","+etime+","+fee+","+usemoney+","+remainmoney+","+usedl+","+zerodl+","+state+")";
			}		
			dao.executeUpdate(sql);
		}
			
		return  SDDef.SUCCESS;
	}
	
	private String NpCommQGWriteDB(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, ComntParaBase.OpDetailInfo op_detail,
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) throws SQLException {
		int rtuId = CommBase.strtoi(userData1);	//RTU ID		
		
		int i = 0;
		
		boolean ret_val = false;
		
		if (npCommSetBase != null) npCommSetBase = npCommSetBase.trim();
		if (npCommSetBase == null || npCommSetBase.length() <= 0 || npCommSetBase.equals("{\"rows\":]}") ){
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}

		JSONObject json = JSONObject.fromObject(npCommSetBase);
		JSONArray  j_array = json.getJSONArray("rows");
		JSONObject j_obj2  = null;	
 	
		for (int j = 0; j < j_array.size(); j++) {
			j_obj2 = j_array.getJSONObject(j);
			
			String sdate = j_obj2.getString("begendate").substring(0,11).replace("年", "").replace("月", "").replace("日", "");
			String stime = j_obj2.getString("begendate").substring(12,21).replace("时", "").replace("分", "").replace("秒", "");
			String edate = j_obj2.getString("enddate").substring(0,11).replace("年", "").replace("月", "").replace("日", "");
			String etime = j_obj2.getString("enddate").substring(12,21).replace("时", "").replace("分", "").replace("秒", "");
			String cardno = j_obj2.getString("cardno");
			String fee  = j_obj2.getString("fee").equals("") ? "0.000" : j_obj2.getString("fee");
			String usemoney = j_obj2.getString("usemoney").equals("") ? "0.000" : j_obj2.getString("usemoney");
			String remainmoney = j_obj2.getString("remainmoney").equals("") ? "0.000" : j_obj2.getString("remainmoney");
			String usedl = j_obj2.getString("usedl").equals("") ? "0.000" : j_obj2.getString("usedl");
			String zerodl = j_obj2.getString("zerodl").equals("") ? "0.000" : j_obj2.getString("zerodl");
			String state = j_obj2.getString("state").length()==0 ? "0" : j_obj2.getString("state");
			if(state.equals("正常")){
				state = "0"; 
			}else if(state.equals("系统停电")){
				state = "1";
			}else if(state.equals("无脉冲自动拉闸")){
				state = "2";
			}else if(state.equals("人为锁表")){
				state = "3";
			}			
			
			JDBCDao dao = new JDBCDao();
			String checksql = "select * from yddataben.dbo.npgq_record"+Calendar.getInstance().get(Calendar.YEAR)+" where rtu_id ="+rtuId +" and mp_id ="+mpid +" and begin_date = "+sdate+" and begin_time = "+stime;;
			String sql = "";
			if(dao.executeQuery(checksql).next()){
				sql = "update yddataben.dbo.npgq_record"+Calendar.getInstance().get(Calendar.YEAR) 
		 		+" set card_no = "+cardno+",gq_date ="+edate+",gq_time ="+etime+",fee = "+fee+",use_money = "+usemoney+",remain_money = "+remainmoney+",use_dl = "+usedl+",zero_dl="+zerodl+",farmer_state = "+state +" where rtu_id ="+rtuId+" and  mp_id ="+mpid  +" and begin_date = "+sdate+"  and begin_time = "+stime;
			}else{
				 sql = "insert into  yddataben.dbo.npgq_record"+Calendar.getInstance().get(Calendar.YEAR) 
		 		+" (rtu_id,mp_id,card_no,begin_date,begin_time,gq_date,gq_time,fee,use_money,remain_money,use_dl,zero_dl,farmer_state) values("
		 		+rtuId+","+mpid+","+cardno+","+sdate+","+stime+","+edate+","+etime+","+fee+","+usemoney+","+remainmoney+","+usedl+","+zerodl+","+state+")";
			
			}
			
			dao.executeUpdate(sql);
		}
			
		return  SDDef.SUCCESS;
	}
	
	private String NpCommCallBase(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, ComntParaBase.OpDetailInfo op_detail,
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;
		
		boolean ret_val = false;
		
		if (npCommCallBase != null) npCommCallBase = npCommCallBase.trim();
		if (npCommCallBase == null || npCommCallBase.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}

		JSONObject json = JSONObject.fromObject(npCommCallBase);
		
		ComntMsg.MSG_READDATA npcomm = new ComntMsg.MSG_READDATA();

		npcomm.mpid  	= mp_id;
		
		Date date = new Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
		
		npcomm.ymd 		= CommBase.strtoi(sdf.format(date));
		npcomm.datatype = (short)CommBase.strtoi(json.getString("datatype"));
		
		int data_idx    = CommBase.strtoi(json.getString("data_idx"));
		
		ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
		npcomm.toDataStream(task_data_vect);

		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		//OpDetailInfo 						detail_info 		= new OpDetailInfo();
		
		ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_READDATA, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect, task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);
		

		boolean data_flag = false;
		ComntMsg.MSG_RESULT_DATA msg_result_data = null;
		
		boolean ret_val1 = false;
		
		switch (data_idx) {
		//电价
		case 0:
			{
				if (ret_data_vect.size() > 0) {
					for (i = 0; i < ret_data_vect.size(); i++) {
						msg_result_data = ret_data_vect.get(i);
						if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_NP_CALL_FEE) {
							ComntProtMsg.CALL_YFF_NP_FEE fee = new ComntProtMsg.CALL_YFF_NP_FEE();
							fee.fromDataStream(msg_result_data.data_vect, 0);
							data_flag = true;
							
							npCommCallBase = fee.toJsonString();
							break;
						}
					}
				}
				
				if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
			}
			break;
		//区域号
		case 1:
			{
				if (ret_data_vect.size() > 0) {					
					for (i = 0; i < ret_data_vect.size(); i++) {
						msg_result_data = ret_data_vect.get(i);
						if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_NP_CALL_AREA) {
							ComntProtMsg.CALL_YFF_NP_AREA area = new ComntProtMsg.CALL_YFF_NP_AREA();
							area.fromDataStream(msg_result_data.data_vect, 0);
							data_flag = true;
							
							npCommCallBase = area.toJsonString();
							break;
						}
					}
				}
				
				if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
			}
			break;
		//变比
		case 2:
			{
				if (ret_data_vect.size() > 0) {
					for (i = 0; i < ret_data_vect.size(); i++) {
						msg_result_data = ret_data_vect.get(i);
						if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_NP_CALL_RATE) {
							ComntProtMsg.CALL_YFF_NP_RATE rate = new ComntProtMsg.CALL_YFF_NP_RATE();
							rate.fromDataStream(msg_result_data.data_vect, 0);
							data_flag = true;
							
							npCommCallBase = rate.toJsonString();
							break;
						}
					}
				}
				
				if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
			}	
			break;
		//报警金额
		case 3:
			{
				if (ret_data_vect.size() > 0) {
					for (i = 0; i < ret_data_vect.size(); i++) {
						msg_result_data = ret_data_vect.get(i);
						if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_NP_CALL_ALARM) {
							ComntProtMsg.CALL_YFF_NP_ALARM alarm = new ComntProtMsg.CALL_YFF_NP_ALARM();
							alarm.fromDataStream(msg_result_data.data_vect, 0);
							data_flag = true;
							
							npCommCallBase = alarm.toJsonString();
							break;
						}
					}
				}
				
				if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
			}	
			break;
		//限定功率
		case 4:
			{
				if (ret_data_vect.size() > 0) {					
					for (i = 0; i < ret_data_vect.size(); i++) {
						msg_result_data = ret_data_vect.get(i);
						if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_NP_CALL_POWLINIT) {
							ComntProtMsg.CALL_YFF_NP_POWLIMIT limit = new ComntProtMsg.CALL_YFF_NP_POWLIMIT();
							limit.fromDataStream(msg_result_data.data_vect, 0);
							data_flag = true;
							
							npCommCallBase = limit.toJsonString();
							break;
						}
					}
				}
				
				if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
			}	
			break;
		//无采样自动断电时间
		case 5:
			{
				if (ret_data_vect.size() > 0) {					
					for (i = 0; i < ret_data_vect.size(); i++) {
						msg_result_data = ret_data_vect.get(i);
						if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_NP_CALL_NOCYCUT) {
							ComntProtMsg.CALL_YFF_NP_NOCYCUT cut = new ComntProtMsg.CALL_YFF_NP_NOCYCUT();
							cut.fromDataStream(msg_result_data.data_vect, 0);
							data_flag = true;
							
							npCommCallBase = cut.toJsonString();
							break;
						}
					}
				}
				
				if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
			}	
			break;
		//电表锁定状态
		case 6:
			{
				if (ret_data_vect.size() > 0) {
					for (i = 0; i < ret_data_vect.size(); i++) {
						msg_result_data = ret_data_vect.get(i);
						if (msg_result_data.datatype == ComntProtMsg.YFF_DATATYPE_NP_CALL_LOCK) {
							ComntProtMsg.CALL_YFF_NP_LOCK lock = new ComntProtMsg.CALL_YFF_NP_LOCK();
							lock.fromDataStream(msg_result_data.data_vect, 0);
							data_flag = true;
							
							npCommCallBase = lock.toJsonString();
							break;
						}
					}
				}
				
				if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
			}	
			break;
		}
			
		return ret_val1 ? SDDef.SUCCESS : SDDef.FAIL;
	}
	

	private String NpCommSetBase(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, ComntParaBase.OpDetailInfo op_detail,
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		if (npCommSetBase != null) npCommSetBase = npCommSetBase.trim();
		if (npCommSetBase == null || npCommSetBase.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}

		JSONObject json = JSONObject.fromObject(npCommSetBase);		
		
		//20131019
		//ComntMsg.MSG_GYCOMM_SETPARA gycomm = new ComntMsg.MSG_GYCOMM_SETPARA();
		ComntMsgGy.MSG_GYCOMM_SETPARA gycomm = new ComntMsgGy.MSG_GYCOMM_SETPARA();

		gycomm.zjgid  	= mp_id;
		ComntFunc.string2Byte(user_name, gycomm.operman);
		
		int data_idx    = CommBase.strtoi(json.getString("data_idx"));		
		
		boolean ret_val1 = true;
		
		switch (data_idx) {
		//电价
		case 0:
			{
				ComntProtMsg.YFF_NPSET_FEE fee = new ComntProtMsg.YFF_NPSET_FEE();
				String str_area = json.getString("meter_area");
				String str_pwd  = json.getString("meter_pwd");
				ComntFunc.string2Byte(str_area, fee.area);
				ComntFunc.string2Byte(str_pwd,  fee.pwd);
				fee.fee = CommBase.strtoi(json.getString("fee"));

				fee.toDataStream(gycomm.data_vect);
				
				gycomm.paratype = ComntProtMsg.YFF_CALL_DL645_SET_FEE;
			}
			break;
		//区域号
		case 1:
			{
				ComntProtMsg.YFF_NPSET_AREA area = new ComntProtMsg.YFF_NPSET_AREA();
				String str_area 	= json.getString("meter_area");
				String str_area_new = json.getString("new_area");
				String str_pwd  	= json.getString("meter_pwd");
				ComntFunc.string2Byte(str_area, 		area.area_old);
				ComntFunc.string2Byte(str_area_new,  	area.area_new);
				ComntFunc.string2Byte(str_pwd,  area.pwd);
				
				area.toDataStream(gycomm.data_vect);
				
				gycomm.paratype = ComntProtMsg.YFF_CALL_DL645_SET_AREA;
			}
			break;
		//变比
		case 2:
			{
				ComntProtMsg.YFF_NPSET_RATE rate = new ComntProtMsg.YFF_NPSET_RATE();
				String str_area 	= json.getString("meter_area");
				String str_pwd  	= json.getString("meter_pwd");
				ComntFunc.string2Byte(str_area, rate.area);
				ComntFunc.string2Byte(str_pwd,  rate.pwd);
				rate.rate = CommBase.strtoi(json.getString("rate"));
				
				rate.toDataStream(gycomm.data_vect);
				
				gycomm.paratype = ComntProtMsg.YFF_CALL_DL645_SET_RATE;
			}	
			break;
		//报警金额
		case 3:
			{
				ComntProtMsg.YFF_NPSET_ALARM alarm = new ComntProtMsg.YFF_NPSET_ALARM();
				String str_area 	= json.getString("meter_area");
				String str_pwd  	= json.getString("meter_pwd");
				ComntFunc.string2Byte(str_area, alarm.area);
				ComntFunc.string2Byte(str_pwd,  alarm.pwd);
				alarm.val = CommBase.strtoi(json.getString("val"));
				
				alarm.toDataStream(gycomm.data_vect);
				
				gycomm.paratype = ComntProtMsg.YFF_CALL_DL645_SET_ALARM;
			}
			break;
		//限定功率
		case 4:
			{
				ComntProtMsg.YFF_NPSET_POWLIMIT limit = new ComntProtMsg.YFF_NPSET_POWLIMIT();
				String str_area 	= json.getString("meter_area");
				String str_pwd  	= json.getString("meter_pwd");
				ComntFunc.string2Byte(str_area, limit.area);
				ComntFunc.string2Byte(str_pwd,  limit.pwd);
				limit.val = CommBase.strtoi(json.getString("val"));
				
				limit.toDataStream(gycomm.data_vect);
				
				gycomm.paratype = ComntProtMsg.YFF_CALL_DL645_SET_POWLINIT;
			}	
			break;
		//无采样自动断电时间
		case 5:
			{
				ComntProtMsg.YFF_NPSET_NOCYCUT cut = new ComntProtMsg.YFF_NPSET_NOCYCUT();
				String str_area 	= json.getString("meter_area");
				String str_pwd  	= json.getString("meter_pwd");
				ComntFunc.string2Byte(str_area, cut.area);
				ComntFunc.string2Byte(str_pwd,  cut.pwd);
				cut.val = (short)CommBase.strtoi(json.getString("val"));
				
				cut.toDataStream(gycomm.data_vect);
				
				gycomm.paratype = ComntProtMsg.YFF_CALL_DL645_SET_NOCYCUT;
			}	
			break;
		//电表锁定状态
		case 6:
			{
				ComntProtMsg.YFF_NPSET_LOCK lock = new ComntProtMsg.YFF_NPSET_LOCK();
				String str_area 	= json.getString("meter_area");
				String str_pwd  	= json.getString("meter_pwd");
				ComntFunc.string2Byte(str_area, lock.area);
				ComntFunc.string2Byte(str_pwd,  lock.pwd);
				lock.val = (byte)CommBase.strtoi(json.getString("val"));
				
				lock.toDataStream(gycomm.data_vect);
				
				gycomm.paratype = ComntProtMsg.YFF_CALL_DL645_SET_LOCK;
			}	
			break;
			//清除挂起记录
		case 7:
			{
				ComntProtMsg.YFF_NP_CLEAR_GQJL cleargq = new ComntProtMsg.YFF_NP_CLEAR_GQJL();
				String str_area 	= json.getString("meter_area");
				String str_pwd  	= json.getString("meter_pwd");
				ComntFunc.string2Byte(str_area, cleargq.area);
				ComntFunc.string2Byte(str_pwd,  cleargq.pwd);
				
				cleargq.toDataStream(gycomm.data_vect);
				
				gycomm.paratype = (short)(ComntProtMsg.YFF_CALL_DL645_CLEAR_GQJL_FIR + CommBase.strtoi(json.getString("val")));
			}	
			break;
		default:
			ret_val1 = false;
			break;
		}
		
		if (!ret_val1) return SDDef.FAIL;
		
		
		ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
		gycomm.toDataStream(task_data_vect);

		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		//OpDetailInfo 						detail_info 		= new OpDetailInfo();
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYCOMM_SETPARA, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect, task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED)) ret_val1 = true;
		else ret_val1 = false;
		
		return ret_val1 ? SDDef.SUCCESS : SDDef.FAIL;
	}
	
	
	//召测黑名单参数
	private String NpCommCallBlPara(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, ComntParaBase.OpDetailInfo op_detail,
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;
		
		boolean ret_val = false;
		
		if (npCommCallBlPara != null) npCommCallBlPara = npCommCallBlPara.trim();
		if (npCommCallBlPara == null || npCommCallBlPara.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}

		JSONObject json = JSONObject.fromObject(npCommCallBlPara);
		
		ComntMsg.MSG_READDATA npcomm = new ComntMsg.MSG_READDATA();

		npcomm.mpid  	= mp_id;
		
		Date date = new Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
		
		npcomm.ymd 		= CommBase.strtoi(sdf.format(date));
		npcomm.datatype = (short)CommBase.strtoi(json.getString("datatype"));
		
		int data_idx    = CommBase.strtoi(json.getString("data_idx"));
		
		ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
		npcomm.toDataStream(task_data_vect);

		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		//OpDetailInfo 						detail_info 		= new OpDetailInfo();
		
		ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_READDATA, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect, task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);
		

		boolean data_flag = false;
		ComntMsg.MSG_RESULT_DATA msg_result_data = null;
		
		boolean ret_val1 = false;
		
		switch (data_idx) {
		case 0: //黑名单启停标志
			{
				if (ret_data_vect.size() > 0) {
					for (i = 0; i < ret_data_vect.size(); i++) {
						msg_result_data = ret_data_vect.get(i);
						if (msg_result_data.datatype == ComntProtMsg.YFF_CALL_DL645_CALL_BL_FLAG) {
							ComntProtMsg.YFF_DATA_NPCOMM_BL_FLAG_U blflag = new ComntProtMsg.YFF_DATA_NPCOMM_BL_FLAG_U();
							blflag.fromDataStream(msg_result_data.data_vect, 0);
							data_flag = true;
							
							npCommCallBlPara = blflag.toJsonString();
							break;
						}
					}
				}
				
				if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
			}
			break;
		case 1:  //召测黑名单
			{
				if (ret_data_vect.size() > 0) {					
					for (i = 0; i < ret_data_vect.size(); i++) {
						msg_result_data = ret_data_vect.get(i);
						if (msg_result_data.datatype >= ComntProtMsg.YFF_CALL_DL645_CALL_BL_FIR  &&
								msg_result_data.datatype <= ComntProtMsg.YFF_CALL_DL645_CALL_BL_FIN ) {
							ComntProtMsg.YFF_DATA_NPCOMM_BL_ITEM_U bl = new ComntProtMsg.YFF_DATA_NPCOMM_BL_ITEM_U();
							bl.fromDataStream(msg_result_data.data_vect, 0);
							data_flag = true;
							
							npCommCallBlPara = bl.toJsonString();
							break;
						}
					}
				}
				
				if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
			}
			break;
		}
			
		return ret_val1 ? SDDef.SUCCESS : SDDef.FAIL;
	}
	
	//设置黑名单参数
	private String NpCommSetBlPara(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, ComntParaBase.OpDetailInfo op_detail,
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		if (npCommSetBlPara != null) npCommSetBlPara = npCommSetBlPara.trim();
		if (npCommSetBlPara == null || npCommSetBlPara.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}

		JSONObject json = JSONObject.fromObject(npCommSetBlPara);		
		
		//20131019
		//ComntMsg.MSG_GYCOMM_SETPARA gycomm = new ComntMsg.MSG_GYCOMM_SETPARA();
		ComntMsgGy.MSG_GYCOMM_SETPARA gycomm = new ComntMsgGy.MSG_GYCOMM_SETPARA();

		gycomm.zjgid  	= mp_id;
		ComntFunc.string2Byte(user_name, gycomm.operman);
		
		int data_idx    = CommBase.strtoi(json.getString("data_idx"));		
		
		boolean ret_val1 = true;
		
		switch (data_idx) {
		case 0:		//设置黑名单启停标志
			{
				ComntProtMsg.YFF_DATA_NPCOMM_BL_FLAG_D blflag = new ComntProtMsg.YFF_DATA_NPCOMM_BL_FLAG_D();
				String str_area = json.getString("meter_area");
				String str_pwd  = json.getString("meter_pwd");
				ComntFunc.string2Byte(str_area, blflag.area);
				ComntFunc.string2Byte(str_pwd,  blflag.pwd);
				blflag.useflag = (byte)CommBase.strtoi(json.getString("useflag"));

				blflag.toDataStream(gycomm.data_vect);
				
				gycomm.paratype = ComntProtMsg.YFF_CALL_DL645_SET_BL_FLAG;
			}
			break;
		case 1:		//添加删除黑名单
			{
				ComntProtMsg.YFF_DATA_NPCOMM_BL_ITEM_D bl = new ComntProtMsg.YFF_DATA_NPCOMM_BL_ITEM_D();
				String str_area 	= json.getString("meter_area");
				String str_pwd  	= json.getString("meter_pwd");
				String str_cardno  	= json.getString("card_no");
				ComntFunc.string2Byte(str_area, 		bl.area);
				ComntFunc.string2Byte(str_pwd,  		bl.pwd);
				ComntFunc.string2Byte(str_cardno,  		bl.kh);
				
				bl.addflag = (byte)CommBase.strtoi(json.getString("addflag"));
				bl.opymd = 0;
				bl.ophms = 0;
				
				bl.toDataStream(gycomm.data_vect);
				
				gycomm.paratype = ComntProtMsg.YFF_CALL_DL645_SET_BL_ITEM;
			}
			break;
		case 2:		//清除黑名单
			{
				ComntProtMsg.YFF_DATA_NPCOMM_CLR_BL_D clear = new ComntProtMsg.YFF_DATA_NPCOMM_CLR_BL_D();
				String str_area 	= json.getString("meter_area");
				String str_pwd  	= json.getString("meter_pwd");
				ComntFunc.string2Byte(str_area, clear.area);
				ComntFunc.string2Byte(str_pwd,  clear.pwd);
								
				clear.toDataStream(gycomm.data_vect);
				
				gycomm.paratype = ComntProtMsg.YFF_CALL_DL645_CLR_BL;
			}	
			break;
		default:
			ret_val1 = false;
			break;
		}
		
		if (!ret_val1) return SDDef.FAIL;
		
		
		ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
		gycomm.toDataStream(task_data_vect);

		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		//OpDetailInfo 						detail_info 		= new OpDetailInfo();
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYCOMM_SETPARA, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect, task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED)) ret_val1 = true;
		else ret_val1 = false;
		
		return ret_val1 ? SDDef.SUCCESS : SDDef.FAIL;
	}
	

	//召测白名单参数
	private String NpCommCallWlPara(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, ComntParaBase.OpDetailInfo op_detail,
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		int i = 0;
		
		boolean ret_val = false;
		
		if (npCommCallWlPara != null) npCommCallWlPara = npCommCallWlPara.trim();
		if (npCommCallWlPara == null || npCommCallWlPara.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}

		JSONObject json = JSONObject.fromObject(npCommCallWlPara);
		
		ComntMsg.MSG_READDATA npcomm = new ComntMsg.MSG_READDATA();

		npcomm.mpid  	= mp_id;
		
		Date date = new Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
		
		npcomm.ymd 		= CommBase.strtoi(sdf.format(date));
		npcomm.datatype = (short)CommBase.strtoi(json.getString("datatype"));
		
		int data_idx    = CommBase.strtoi(json.getString("data_idx"));
		
		ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
		npcomm.toDataStream(task_data_vect);

		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		//OpDetailInfo 						detail_info 		= new OpDetailInfo();
		
		ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_READDATA, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect, task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);
		

		boolean data_flag = false;
		ComntMsg.MSG_RESULT_DATA msg_result_data = null;
		
		boolean ret_val1 = false;
		
		switch (data_idx) {
		case 0: //白名单启停标志
			{
				if (ret_data_vect.size() > 0) {
					for (i = 0; i < ret_data_vect.size(); i++) {
						msg_result_data = ret_data_vect.get(i);
						if (msg_result_data.datatype == ComntProtMsg.YFF_CALL_DL645_CALL_WL_FLAG) {
							ComntProtMsg.YFF_DATA_NPCOMM_WL_FLAG_U wlflag = new ComntProtMsg.YFF_DATA_NPCOMM_WL_FLAG_U();
							wlflag.fromDataStream(msg_result_data.data_vect, 0);
							data_flag = true;
							
							npCommCallWlPara = wlflag.toJsonString();
							break;
						}
					}
				}
				
				if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
			}
			break;
		case 1:  //召测白名单
			{
				if (ret_data_vect.size() > 0) {					
					for (i = 0; i < ret_data_vect.size(); i++) {
						msg_result_data = ret_data_vect.get(i);
						if (msg_result_data.datatype >= ComntProtMsg.YFF_CALL_DL645_CALL_WL_FIR  &&
								msg_result_data.datatype <= ComntProtMsg.YFF_CALL_DL645_CALL_WL_FIN ) {
							ComntProtMsg.YFF_DATA_NPCOMM_WL_ITEM_U wl = new ComntProtMsg.YFF_DATA_NPCOMM_WL_ITEM_U();
							wl.fromDataStream(msg_result_data.data_vect, 0);
							data_flag = true;
							
							npCommCallWlPara = wl.toJsonString();
							break;
						}
					}
				}
				
				if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED) && data_flag) ret_val1 = true;
			}
			break;
		}
			
		return ret_val1 ? SDDef.SUCCESS : SDDef.FAIL;
	}
	
	//设置白名单参数
	private String NpCommSetWlPara(String user_name, ComntParaBase.RTU_PARA rtu_para, short mp_id, ComntParaBase.OpDetailInfo op_detail,
			StringBuffer err_str_1, ComntParaBase.SALEMAN_TASK_RESULT_DETAIL task_result_detail) {
		int i_user_data1 = CommBase.strtoi(userData1);	//RTU ID
		int i_user_data2 = CommBase.strtoi(userData2);	//USEROPER ID
		
		if (npCommSetWlPara != null) npCommSetWlPara = npCommSetWlPara.trim();
		if (npCommSetWlPara == null || npCommSetWlPara.length() <= 0) {
			op_detail.addTaskInfo("ERR:错误的请求参数");
			return SDDef.FAIL;
		}

		JSONObject json = JSONObject.fromObject(npCommSetWlPara);		
		
		//20131019
		//ComntMsg.MSG_GYCOMM_SETPARA gycomm = new ComntMsg.MSG_GYCOMM_SETPARA();
		ComntMsgGy.MSG_GYCOMM_SETPARA gycomm = new ComntMsgGy.MSG_GYCOMM_SETPARA();

		gycomm.zjgid  	= mp_id;
		ComntFunc.string2Byte(user_name, gycomm.operman);
		
		int data_idx    = CommBase.strtoi(json.getString("data_idx"));		
		
		boolean ret_val1 = true;
		
		switch (data_idx) {
		case 0:		//设置白名单启停标志
			{
				ComntProtMsg.YFF_DATA_NPCOMM_WL_FLAG_D wlflag = new ComntProtMsg.YFF_DATA_NPCOMM_WL_FLAG_D();
				String str_area = json.getString("meter_area");
				String str_pwd  = json.getString("meter_pwd");
				ComntFunc.string2Byte(str_area, wlflag.area);
				ComntFunc.string2Byte(str_pwd,  wlflag.pwd);
				wlflag.useflag = (byte)CommBase.strtoi(json.getString("useflag"));

				wlflag.toDataStream(gycomm.data_vect);
				
				gycomm.paratype = ComntProtMsg.YFF_CALL_DL645_SET_WL_FLAG;
			}
			break;
		case 1:		//添加删除白名单
			{
				ComntProtMsg.YFF_DATA_NPCOMM_WL_ITEM_D wl = new ComntProtMsg.YFF_DATA_NPCOMM_WL_ITEM_D();
				String str_area 	= json.getString("meter_area");
				String str_pwd  	= json.getString("meter_pwd");
				String str_cardno  	= json.getString("card_no");
				ComntFunc.string2Byte(str_area, 		wl.area);
				ComntFunc.string2Byte(str_pwd,  		wl.pwd);
				ComntFunc.string2Byte(str_cardno,  		wl.kh);
				
				wl.addflag = (byte)CommBase.strtoi(json.getString("addflag"));
				wl.opymd = 0;
				wl.ophms = 0;
				
				wl.toDataStream(gycomm.data_vect);
				
				gycomm.paratype = ComntProtMsg.YFF_CALL_DL645_SET_WL_ITEM;
			}
			break;
		case 2:		//清除白名单
			{
				ComntProtMsg.YFF_DATA_NPCOMM_CLR_WL_D clear = new ComntProtMsg.YFF_DATA_NPCOMM_CLR_WL_D();
				String str_area 	= json.getString("meter_area");
				String str_pwd  	= json.getString("meter_pwd");
				ComntFunc.string2Byte(str_area, clear.area);
				ComntFunc.string2Byte(str_pwd,  clear.pwd);
								
				clear.toDataStream(gycomm.data_vect);
				
				gycomm.paratype = ComntProtMsg.YFF_CALL_DL645_CLR_WL;
			}	
			break;
		default:
			ret_val1 = false;
			break;
		}
		
		if (!ret_val1) return SDDef.FAIL;
		
		
		ComntVector.ByteVector task_data_vect = new ComntVector.ByteVector();
		gycomm.toDataStream(task_data_vect);

		byte[] task_result = new byte[1];
		task_result[0] = ComntDef.YD_YFF_TASKRESULT_NULL;
		
		ArrayList<ComntMsg.MSG_RESULT_DATA> ret_data_vect 		= new  ArrayList<ComntMsg.MSG_RESULT_DATA>();
		//OpDetailInfo 						detail_info 		= new OpDetailInfo();
		
		boolean ret_val = ComntParaBase.get1StepTaskResult(ComntDef.SALEMANAGER_VERSION, user_name, i_user_data1, i_user_data2, rtu_para,
				(byte)ComntDef.YD_YFF_TASKAPPTYPE_GYCOMM_SETPARA, (byte)ComntDef.YD_TASKASSIGNTYPE_MAN, 
				task_data_vect, task_result, ret_data_vect, op_detail, err_str_1, task_result_detail);
		
		if (ret_val && (task_result[0] == ComntDef.YD_YFF_TASKRESULT_SUCCEED)) ret_val1 = true;
		else ret_val1 = false;
		
		return ret_val1 ? SDDef.SUCCESS : SDDef.FAIL;
	}
	
	public String taskProc() throws SQLException{
		ComntParaBase.OpDetailInfo op_detail = new ComntParaBase.OpDetailInfo();
		StringBuffer err_str_1 				 = new StringBuffer();
		ComntParaBase.SALEMAN_TASK_RESULT_DETAIL 	task_result_detail = new ComntParaBase.SALEMAN_TASK_RESULT_DETAIL();
		
		int rtu_id    = CommBase.strtoi(userData1);
		int userop_id = CommBase.strtoi(userData2); 
		short mp_id   = (short)CommBase.strtoi(mpid);

		JDBCDao jdao = new JDBCDao();
		String sql = "select top 1 mp_id from meterpara where rtu_id=" + rtu_id + " and mp_id = " + mp_id;
		List<Map<String,Object>> list = jdao.result(sql);
		if(list.size() <= 0) {
			mp_id = 1;
		}
		
//		else {		//20140912 zkz temp 
////			mp_id = (short)CommBase.strtoi(CommFunc.CheckString((list.get(0)).get("mp_id")));
//		}

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
		if (userop_id == ComntUseropDef.YFF_READDATA) {					//读取数据农排
			//String firstlast_str = firstLastFlag.trim();
			//boolean first_flag = (firstlast_str.compareToIgnoreCase(SDDef.TRUE) == 0);
			
			ret_val = NpCommReadData(user_name, rtu_para, mp_id, /*first_flag, */op_detail, err_str_1, task_result_detail);			
		}
		
		else if (userop_id == ComntUseropDef.YFF_NPOPER_CALLBASE) {		//农排操作-召测基本数据
			ret_val = NpCommCallBase(user_name, rtu_para, mp_id, op_detail, err_str_1, task_result_detail);
		}
		
		else if (userop_id == ComntUseropDef.YFF_NPOPER_SETBASE) {		//农排操作-设置节本数据
			ret_val = NpCommSetBase(user_name, rtu_para, mp_id, op_detail, err_str_1, task_result_detail);
		}
		
		else if (userop_id == ComntUseropDef.YFF_NPOPER_WriteDB) {		//农排操作-用电记录数据
			ret_val = NpCommWriteDB(user_name, rtu_para, mp_id, op_detail, err_str_1, task_result_detail);
		}
		else if (userop_id == ComntUseropDef.YFF_NPGQOPER_WriteDB) {		//农排操作-挂起用户记录
			ret_val = NpCommQGWriteDB(user_name, rtu_para, mp_id, op_detail, err_str_1, task_result_detail);
		}
		
		else if (userop_id == ComntUseropDef.YFF_NPOPER_BL_PARA) {		//农排操作-黑名单
			String callflag_str  = callFlag.trim();
			boolean call_flag  = (callflag_str.compareToIgnoreCase(SDDef.TRUE) == 0);
			
			if (call_flag) {
				ret_val = NpCommCallBlPara(user_name, rtu_para, mp_id, op_detail, err_str_1, task_result_detail);
			}
			else {
				ret_val = NpCommSetBlPara(user_name, rtu_para, mp_id, op_detail, err_str_1, task_result_detail);
			}
		}
		
		else if (userop_id == ComntUseropDef.YFF_NPOPER_WL_PARA) {		//农排操作-白名单
			String callflag_str  = callFlag.trim();
			boolean call_flag  = (callflag_str.compareToIgnoreCase(SDDef.TRUE) == 0);
			
			if (call_flag) {
				ret_val = NpCommCallWlPara(user_name, rtu_para, mp_id, op_detail, err_str_1, task_result_detail);
			}
			else {
				ret_val = NpCommSetWlPara(user_name, rtu_para, mp_id, op_detail, err_str_1, task_result_detail);
			}
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
	public String getNpCommReadData() {
		return npCommReadData;
	}
	public void setNpCommReadData(String npCommReadData) {
		this.npCommReadData = npCommReadData;
	}
	public String getNpCommCallBase() {
		return npCommCallBase;
	}
	public void setNpCommCallBase(String npCommCallBase) {
		this.npCommCallBase = npCommCallBase;
	}
	public String getNpCommSetBase() {
		return npCommSetBase;
	}
	public void setNpCommSetBase(String npCommSetBase) {
		this.npCommSetBase = npCommSetBase;
	}
	public String getNpCommCallWlPara() {
		return npCommCallWlPara;
	}
	public void setNpCommCallWlPara(String npCommCallWlPara) {
		this.npCommCallWlPara = npCommCallWlPara;
	}
	public String getNpCommCallBlPara() {
		return npCommCallBlPara;
	}
	public void setNpCommCallBlPara(String npCommCallBlPara) {
		this.npCommCallBlPara = npCommCallBlPara;
	}
	public String getNpCommSetWlPara() {
		return npCommSetWlPara;
	}
	public void setNpCommSetWlPara(String npCommSetWlPara) {
		this.npCommSetWlPara = npCommSetWlPara;
	}
	public String getNpCommSetBlPara() {
		return npCommSetBlPara;
	}
	public void setNpCommSetBlPara(String npCommSetBlPara) {
		this.npCommSetBlPara = npCommSetBlPara;
	}	
}
