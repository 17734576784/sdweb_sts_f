package com.kesd.action.oper;

import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.common.SDOperlog;
import com.kesd.common.YDTable;
import com.kesd.dbpara.OrgStsPara;
import com.libweb.common.CommBase;
import com.libweb.dao.HibDao;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;
import com.sts.common.STSDef;
import com.sts.comnt.StsComntService;

public class ActOperDBDy extends ActionSupport{

	/**
	 * 孟加拉操作,数据存库
	 */
	private static final long serialVersionUID = -7291856994203955124L;

	private String result;
	private String params;
	private String operType;
	private String calcZdl;
	private static int wastenoIndex = 0;
	
	//数据库存库操作实现
	public String operDYSaveDB(){
		if(params == null || params.isEmpty() 
				|| operType == null || operType.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		int oper_type = CommBase.strtoi(operType);
		JSONObject json_param = JSONObject.fromObject(params);
		//try {
			switch(oper_type){
			case SDDef.YFF_OPTYPE_ADDRES:	//开户
				result = addNewOper(json_param);
				break;
			case SDDef.YFF_OPTYPE_PAY:		//缴费	
				result = payOper(json_param);
				break;
			case SDDef.YFF_OPTYPE_REPAIR:	//补卡
				result = repCardOper(json_param);
				break;
			case SDDef.YFF_OPTYPE_DESTORY:	//销户
				result = delCusOper(json_param);
				break;	
			//工具卡操作--待定
			//测试卡操作--待定
			default:
				break;
			}
		//} catch (Exception e) {
			// TODO: handle exception
		//	e.printStackTrace();
		//}
		
		return SDDef.SUCCESS;
	}
	
	public String loadPayInfo(){
		try {
			JSONObject paramsJson = JSONObject.fromObject(params);
			int rtuId = paramsJson.getInt("rtuId");
			int mpId = paramsJson.getInt("mpId");
			int feeprojId = paramsJson.getInt("feeprojId");
			double totalMoney = CommFunc.objectToDouble(paramsJson.get("totalMoney"));
			double tzdl = CommFunc.objectToDouble(paramsJson.get("tzdl"));
			double ct = CommFunc.objectToDouble(paramsJson.get("ct"));
			double pt = CommFunc.objectToDouble(paramsJson.get("pt"));
		
			JDBCDao j_dao = new JDBCDao();
			/** 查询费率方案信息 */
			String sql = "select * from yffratepara where id = " + feeprojId;
			List<Map<String, Object>> list = j_dao.result(sql);
			if (list.size() == 0) {
				result = "Rate scheme Error";
				return SDDef.SUCCESS;
			}
		
			double buyDl = 0D;
			Map<String,Object> feeproj = list.get(0);
			int feeTyope = CommFunc.objectToInt(feeproj.get("fee_type"));
			if (feeTyope == SDDef.YFF_FEETYPE_DFL) {// 单费率
				buyDl = totalMoney	/ CommFunc.objectToDouble(feeproj.get("rated_z"));
			} else if (feeTyope == SDDef.YFF_FEETYPE_JTFL) {// 阶梯费率
				int ratejNum = CommFunc.objectToInt(feeproj.get("ratej_num"));
				double ratejTd1 = CommFunc.objectToDouble(feeproj.get("ratej_td1"));
//				double ratejTd2 = CommFunc.objectToDouble(feeproj.get("ratej_td2"));
//				double ratejTd3 = CommFunc.objectToDouble(feeproj.get("ratej_td3"));
//				double ratejTd4 = CommFunc.objectToDouble(feeproj.get("ratej_td4"));
//				double ratejTd5 = CommFunc.objectToDouble(feeproj.get("ratej_td5"));
//				double ratejTd6 = CommFunc.objectToDouble(feeproj.get("ratej_td6"));
				
				double ratejR1 =CommFunc.objectToDouble(feeproj.get("ratej_r1"));
				double ratejR2 =CommFunc.objectToDouble(feeproj.get("ratej_r2"));
//				double ratejR3 =CommFunc.objectToDouble(feeproj.get("ratej_r3"));
//				double ratejR4 =CommFunc.objectToDouble(feeproj.get("ratej_r4"));
//				double ratejR5 =CommFunc.objectToDouble(feeproj.get("ratej_r5"));
//				double ratejR6 =CommFunc.objectToDouble(feeproj.get("ratej_r6"));
//				double ratejR7 =CommFunc.objectToDouble(feeproj.get("ratej_r7"));
				
				int year = CommFunc.nowDateInt() / 10000;
				int month = CommFunc.nowDateInt() / 100;			
				StringBuffer sb = new StringBuffer();
				sb.append("select SUM(buy_dl) dl from YDDataBen.dbo.JYff").append(year);
				sb.append(" where rtu_id =").append(rtuId).append(" and mp_id = ").append(mpId);
				sb.append(" and op_date/100 = ").append(month);
				
				double buyedDL = 0;// 本月已购电量
				list = j_dao.result(sb.toString());
				if (list.size() > 0) {
					buyedDL = CommFunc.objectToDouble(list.get(0).get("dl")) * pt
							* ct;
				}
				sb.setLength(0);
				sb.append("select count('X') num from  YDDataBen.dbo.JYff").append(year);
				sb.append(" where rtu_id =").append(rtuId).append(" and mp_id = ").append(mpId);
				sb.append(" and op_date/100 = ").append(month).append(" and buy_times = 1");
				list = j_dao.result(sb.toString());
				int firstBuy = 0;
				if (list.size() > 0) {
					firstBuy = CommFunc.objectToInt(list.get(0).get("num"));
				}
				
				if (firstBuy == 1) {
					buyedDL = buyedDL + tzdl * pt * ct;
				}
				
				if (buyedDL >= ratejTd1) {
					buyDl = totalMoney / ratejR2;
				} else if (buyedDL < ratejTd1) {
					double remainDl = ratejTd1 - buyedDL;
					double remainMoney = remainDl * ratejR1;
					if (remainMoney >= totalMoney) {
						buyDl = totalMoney / ratejR1;
					} else {
						buyDl = remainDl + (totalMoney - remainMoney) / ratejR2;
					}
				}
			}
			setCalcZdl(CommFunc.roundTosString(buyDl, 2));
			result = SDDef.SUCCESS;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			result = "error";
		}
		return SDDef.SUCCESS;
	}
	
	//批量开户操作数据存库
	public String plAddNewOper(){
		
		JSONObject resultJson = JSONObject.fromObject(result);
		JSONObject paramsJson = JSONObject.fromObject(params);
		
		int orgId = paramsJson.getInt("orgId");
		int consId = paramsJson.getInt("consId");
		String residentId = paramsJson.getString("residentId");
		
		String rtuId = residentId.split("_")[0];
		String resId = residentId.split("_")[1];
		
		int tariffProject = paramsJson.getInt("tariffProject");
		
		JSONArray rows =resultJson.getJSONArray("rows");
		
		JSONObject addOperParam = new JSONObject();
		
		JDBCDao j_dao = new JDBCDao();

		String sql = "select max(id) maxData from mppara where rtu_id = "+rtuId;
		sql+=" union select max(mp_id) maxData from meterpara where rtu_id ="+rtuId;
		sql+=" union select max(mp_id) maxData from meter_stspara where rtu_id ="+rtuId;
		List<Map<String, Object>>	list = j_dao.result(sql);
		int mpId = 0;
		for (Map<String, Object> map : list) {
			int temp = Integer.parseInt(map.get("maxData").toString());
			if (temp > mpId) {
				mpId = temp;
			}
		}
		
		sql = "select r_org_no sgc from orgpara where id=" + orgId;
		list = j_dao.result(sql);
		String sgc = "";
		if (list.get(0).get("sgc") != null) {
			sgc = list.get(0).get("sgc").toString();
		}
		
		sql = "select key_regno regno from org_stspara where org_id=" + orgId;
		list = j_dao.result(sql);
		String regno = "1";
		if (list.get(0).get("regno") != null) {
			regno = list.get(0).get("regno").toString();
		}
		
		StringBuffer has = new StringBuffer();
		for(int i=0;i<rows.size();i++){
			
			JSONObject param =JSONObject.fromObject(rows.get(i));
			JSONArray data  =param.getJSONArray("data");
			int rowId  =param.getInt("id");
			
			if (data.size() < 6) continue;
			
			//String num = data.getString(0);
			String AllMeterNo = data.getString(2);
			String meterNo = data.getString(3);
			String token1 = data.getString(4);
			String token2 = data.getString(5);
			String meterkey = data.size()>=7 ? data.getString(6) : "";
			String YFFmeterType = data.size()>=8 ? data.getString(7) : "1";	//默认单相
			if (YFFmeterType.isEmpty()) YFFmeterType = "1";
			Double PT = data.size()>=9 ? CommFunc.stringToDouble(data.getString(8)) : 1.0;		//PT
			Double CT = data.size()>=10 ?CommFunc.stringToDouble(data.getString(9)) : 1.0;		//CT
			if (PT <= 0.0) PT = 1.0;
			if (CT <= 0.0) CT = 1.0;
			
			mpId++;
			addOperParam.put("rtuId", rtuId);
			addOperParam.put("mpId", mpId);
			addOperParam.put("cus_state", "1");
			addOperParam.put("op_type", "1");
			addOperParam.put("all_money", "0");
			addOperParam.put("all_dl", "0");
			addOperParam.put("buy_times", "0");
			addOperParam.put("meterkey", meterkey);
			addOperParam.put("token1", token1);
			addOperParam.put("token2", token2);
			addOperParam.put("ken", "255");
			addOperParam.put("res_id", resId);
			addOperParam.put("pay_type", "0");
			addOperParam.put("seqNo", "0");
			addOperParam.put("keyNo", "0");
			addOperParam.put("keyNo", "0");
			addOperParam.put("comm_addr", meterNo);
			addOperParam.put("describe", AllMeterNo);
			addOperParam.put("tariffProject", tariffProject);
			addOperParam.put("YFFmeterType", YFFmeterType);
			addOperParam.put("PT", PT);
			addOperParam.put("CT", CT);
			addOperParam.put("sgc", sgc);
			addOperParam.put("regno", regno);
			
			//判断是否存在表地址
			String countRec = "SELECT count(comm_addr) countAddr from meterpara WHERE comm_addr = '"+meterNo+"'";
			
			List<Map<String,Object>> listResult = j_dao.result(countRec);
			
			if(Integer.parseInt(listResult.get(0).get("countAddr").toString())!=0){
				has.append(rowId+",");
				continue;
			}
			
			//添加基本档案
			if(insertMeterPara(addOperParam)){
				//添加开户信息到库中
				addMppayPara(addOperParam);
				
				//更新测点个数信息 zxp
				sql = "update "+YDTable.TABLECLASS_RTUPARA+" set jlp_num=jlp_num+1 where id=" + rtuId;
				j_dao.executeUpdate(sql);
				
			}
		}
		if(has.length() == 0){
			result = has.toString();			
		}else{
			result = has.substring(0,has.length()-1).toString();			
		}

		return SDDef.SUCCESS;
	}

	//批量添加居民
	public String plAddResident(){
		
		JSONObject resultJson = JSONObject.fromObject(result);
		JSONObject paramsJson = JSONObject.fromObject(params);
		
		String rtuId = paramsJson.getString("rtuId");
		
		JSONArray rows =resultJson.getJSONArray("rows");
		
		JSONObject addResidentParam = new JSONObject();
		
		JDBCDao j_dao = new JDBCDao();
		
		String sql = "SELECT max(id) maxData from residentpara WHERE rtu_id = "+rtuId;
		
		List<Map<String, Object>>	list = j_dao.result(sql);
		int mpId = 0;
		if (list.get(0).get("maxData")!=null){
			mpId=Integer.parseInt(list.get(0).get("maxData").toString());
		}
		StringBuffer has = new StringBuffer();
		for(int i=0;i<rows.size();i++){
			
			JSONObject param =JSONObject.fromObject(rows.get(i));
			JSONArray data  =param.getJSONArray("data");
			int rowId  =param.getInt("id");
			
			String consNo 				= "";
			String meterNo 				= "";
			String residentDesc 		= "";
			String residentAddress 		= "";
			String residentPost 		= "";
			String residentPhone 		= "";
			String residentMobile 		= "";
			String residentFax 			= "";
			String residentMail 		= "";
			if(data.size() > 1){
				consNo 		 = data.getString(1);
			}			
			if(data.size() > 2){
				meterNo		 = data.getString(2);
			}			
			if(data.size() > 3){
				residentDesc = data.getString(3);
			}			
			if(data.size() > 4){
				residentAddress = data.getString(4);
			}			
			if(data.size() > 5){
				residentPost 	= data.getString(5);
			}			
			if(data.size() > 6){
				residentPhone 	= data.getString(6);
			}			
			if(data.size() > 7){
				residentMobile 	= data.getString(7);
			}			
			if(data.size() > 8){
				residentFax 		= data.getString(8);
			}			
			if(data.size() > 9){
				residentMail 		= data.getString(9);
			}
			mpId++;
			
			if(meterNo.length() < 10){
				for(int j = meterNo.length(); j< 10; j++){
					meterNo = "0" + meterNo;
				}
			}
			
			addResidentParam.put("rtuId", 		rtuId);
			addResidentParam.put("id", 			mpId);
			addResidentParam.put("describe", 	residentDesc);
			addResidentParam.put("cons_no", 	consNo);
			addResidentParam.put("address", 	residentAddress);
			addResidentParam.put("post", 		residentPost);
			addResidentParam.put("phone", 		residentPhone);
			addResidentParam.put("mobile", 		residentMobile);
			addResidentParam.put("fax", 		residentFax);
			addResidentParam.put("mail", 		residentMail);
			addResidentParam.put("meterNo", 	meterNo);
			
			//判断是否存在表地址 表记录
			/*
			String residentRec = "SELECT * from meterpara WHERE comm_addr = '"+meterNo+"'";
			List<Map<String,Object>> listResult = j_dao.result(residentRec);
			if(listResult == null || listResult.size() > 0){
				has.append(rowId+",");
				continue;
			}*/
			//户号唯一
			String residentNoRec = "SELECT * from residentpara WHERE cons_no = '"+ consNo +"'";
			List<Map<String,Object>> listResidentNoResult = j_dao.result(residentNoRec);
			if(listResidentNoResult == null || listResidentNoResult.size() > 0){
				has.append(rowId+",");
				continue;
			}
			//添加居民档案
			if(insertResidentPara(addResidentParam)){

				//更新居民个数信息 zxp
				sql = "update "+YDTable.TABLECLASS_RTUPARA+" set resident_num=resident_num+1 where id=" + rtuId;
				j_dao.executeUpdate(sql);
			}
		}
		if(has.length() == 0){
			result = has.toString();			
		}else{
			result = has.substring(0,has.length()-1).toString();
		}
		return SDDef.SUCCESS;
	}	

	public boolean insertResidentPara(JSONObject residentParaParam){
		boolean flag = true;
		JDBCDao j_dao = new JDBCDao();
		
		String residentSql = "insert into residentpara(rtu_id,id,describe,cons_no,address,post,phone,mobile,fax,mail)values ("
								+residentParaParam.getString("rtuId")   + "," +residentParaParam.getString("id") 		+ ",'" 
								+residentParaParam.getString("describe")+"','"+residentParaParam.getString("cons_no") 	+ "','"
								+residentParaParam.getString("address") +"','" +residentParaParam.getString("post") 	+ "','"
								+residentParaParam.getString("phone") +"','" +residentParaParam.getString("mobile") 	+ "','"
								+residentParaParam.getString("fax")+"','"+residentParaParam.getString("mail")+"')";
		if(j_dao.executeUpdate(residentSql)){
			String updateMeterParam = "update meterpara set resident_id = " + residentParaParam.getString("id") + "," + 
										"rtu_id = " + residentParaParam.getString("rtuId") + " where comm_addr = '" 
										+ residentParaParam.getString("meterNo") + "'";
			if(j_dao.executeUpdate(updateMeterParam)){
				flag = true;
			}else{
				flag = false;
			}
		}else{
			flag=false;
		}
		return flag;
	}	
	
	
	public boolean addMppayPara(JSONObject meterParaParam){
		
		boolean flag = true;
		JDBCDao j_dao = new JDBCDao();
		String ymdhms 	= "20" + CommFunc.nowDate();	//nowDate()方法获取的是YYMMDDHHmmss
		String ymd 		= ymdhms.substring(0, 8);	//年月日
		String hms 		= ymdhms.substring(8, 14);	//时分秒 
			
		//开户缴费信息初始化
		String mppayparaSql = "insert into mppay_para(rtu_id,mp_id,use_flag,cacl_type,feectrl_type,pay_type,yffmeter_type,feeproj_id,prot_st,prot_ed,key_version,tz_val)values ("
				+meterParaParam.getString("rtuId") + "," +meterParaParam.getString("mpId") + "," 
				+1+","+2+","+0+","+0+","+meterParaParam.getString("YFFmeterType")+","+meterParaParam.getString("tariffProject")+","+0+","+0+","+1+","+20+")";
		if(j_dao.executeUpdate(mppayparaSql)){
			//开户缴费状态初始化
			String mppaystateSql = "insert into mppay_state(rtu_id,mp_id,cus_state,op_type,op_date,op_time,pay_money,all_money," +
						"buy_dl,buy_times,kh_date,xh_date,reserve1,reserve2)" +
						"values ("
						+meterParaParam.getString("rtuId") + "," +meterParaParam.getString("mpId") + "," 
						//+ ((!meterParaParam.containsKey("meterkey") || meterParaParam.getString("meterkey").isEmpty()) ? 0 : 1) +","+1+","+ymd+","+hms+","+0+","+0+","+0+","+0+","+ymd+","+0+","+2+","+1+")";
						//批量导入表档案默认已开户，meterkey电笔生产时下发到电表，软加密时需要导入meterkey，售电时使用，硬加密不需要该字段
						+ 1 +","+1+","+ymd+","+hms+","+0+","+0+","+0+","+0+","+ymd+","+0+","+2+","+1+")";
			flag = j_dao.executeUpdate(mppaystateSql);	
		}
		else{
			flag = false;
		}

		
		return flag;
	}
	
	public boolean insertMeterPara(JSONObject meterParaParam){
		boolean flag = true;
		JDBCDao j_dao = new JDBCDao();
		//meterpara  meter_stspara mppara 添加记录
		String meterStsSql = "insert into meter_stspara(rtu_id,mp_id,meter_key,oldmt_key,ken,old_ken,token1,token2,krn,old_krn, kt, old_kt, ti, old_ti, sgc, old_sgc, regno, old_regno)values ("
								+meterParaParam.getString("rtuId") + "," +meterParaParam.getString("mpId") + ",'" 
								+meterParaParam.getString("meterkey")+"','"+"0124000000000001"+"',"
								+meterParaParam.getString("ken")+","+ meterParaParam.getString("ken")+",'"
								+meterParaParam.getString("token1")+"','"+meterParaParam.getString("token2")+"'," + 1 + "," + 1+ "," + 2 + "," + 2+ "," + 1 + "," + 1 + ",'" 
								+meterParaParam.getString("sgc") + "','" + meterParaParam.getString("sgc") + "'," + meterParaParam.getString("regno") + "," + meterParaParam.getString("regno") + ")";
		if(j_dao.executeUpdate(meterStsSql)){
			String meterSql = "insert into meterpara(rtu_id,mp_id,describe,use_flag,resident_id,comm_addr," 
								+"made_no,factory,prepayflag"+")values ("
								+meterParaParam.getString("rtuId") + "," +meterParaParam.getString("mpId") + ",'" 
								+meterParaParam.getString("describe")+"',"+1+","
								+meterParaParam.getString("res_id")+",'"+meterParaParam.getString("comm_addr")+"','"
								+meterParaParam.getString("comm_addr")+"',"+0+","+1+")";				
			if(j_dao.executeUpdate(meterSql)){
				String mpSql = "insert into mppara(rtu_id,id,describe,use_flag,mp_type,pt_ratio,ct_ratio,bak_flag,reserve1)values ("
								+meterParaParam.getString("rtuId") + "," +meterParaParam.getString("mpId") + ",'"
								+meterParaParam.getString("describe")+"',"+1+","
								+0+","+meterParaParam.getDouble("PT")+","+meterParaParam.getDouble("CT")+","+0+",'"+"00000000"+"')";	
				flag=j_dao.executeUpdate(mpSql);
			}else{
				flag=false;
			}
		}else{
			flag=false;
		}
		
		return flag;
	}
	
	//开户操作数据存库
	public String addNewOper(JSONObject json_param){
		String rtuId 		= json_param.getString("rtuId");
		String mpId 		= json_param.getString("mpId");
		String cus_state 	= json_param.getString("cus_state");
		String op_type 		= json_param.getString("op_type");		
		String all_money 	= json_param.getString("all_money").isEmpty()? "0" : json_param.getString("all_money");
		String all_dl = "0";
		if(json_param.has("all_dl")){
			 all_dl	=  json_param.getString("all_dl").isEmpty()? "0" : json_param.getString("all_dl");
		}
		//String buy_times 	= json_param.getString("buy_times");
		String paytoken ="",token1 = "",token2 = "",meterkey="",KEN="";
		boolean flag = false;		
		if(json_param.has("token")){
			paytoken 	= json_param.getString("token").isEmpty()? "" : json_param.getString("token");			
		}
		if(json_param.has("token1")){
			if(!"".equals(json_param.getString("meterkey"))){
				meterkey	= json_param.getString("meterkey");	
			}
			token1		= json_param.getString("token1");
			token2		= json_param.getString("token2");
			KEN 		= json_param.getString("KEN");
			flag = true;
		}
		
		String res_id 		= json_param.getString("res_id");
		String pay_type 	= json_param.getString("pay_type");
		Short visible_flag = 1;
		String seqNo = json_param.getString("seqNo");
		String keyNo = json_param.getString("keyNo");
		
		String jy_money = "0.0";
		if(json_param.containsKey("jy_money")){
			jy_money = json_param.getString("jy_money");			
		}

		
		//开户时seqNo为2，keyNo为1，
		//缴费、补卡时自增1,seqNo自增1，如果大于200，置为1
		int oper_type = CommBase.strtoi(op_type);//操作类型
		if ((oper_type == SDDef.YFF_OPTYPE_PAY)||(oper_type == SDDef.YFF_OPTYPE_REPAIR)) {
			int seq_increase = CommBase.strtoi(seqNo) + 1;			
			if(seq_increase > 200) {					//如果大于200，置为1
				seqNo = "1";
				keyNo = String.valueOf(CommBase.strtoi(keyNo) + 1);	//keyNo自增	
			}
			else {
				seqNo = String.valueOf(seq_increase);		//
			}
		}
		JDBCDao j_dao = new JDBCDao();
		String op_man 	= CommFunc.getUser().getDescribe();//获取当前登录用户
		String ymdhms 	= "20" + CommFunc.nowDate();	//nowDate()方法获取的是YYMMDDHHmmss
		String ymd 		= ymdhms.substring(0, 8);	//年月日
		String hms 		= ymdhms.substring(8, 14);	//时分秒 
		String year 	= CommFunc.nowYMD().substring(0, 4);//年份
		
		//添加缴费记录
		String jyffTable = "yddataben.dbo.jyff" + year;
		//更新预付费状态
		String mppayStateTable = "ydparaben.dbo.mppay_state";		
		wastenoIndex = (wastenoIndex == 10000 ? 1 : wastenoIndex + 1); 
		String wasteno = "PR" + ymdhms + wastenoIndex;			
		String prt_params="{rtu_id:"+rtuId+",id:"+mpId+",op_date:"+ymd+",op_time:"+hms+",op_type:"+op_type+",optime:\""+CommFunc.FormatToYMD(ymd, "day") +" "+ CommFunc.FormatToHMS(hms,1)+"\",wasteno:\""+wasteno+"\",op_man:\""+op_man+"\",pay_money:"+all_money+",buy_dl:"+all_dl+"}";
 			
		//查询当前缴费次数，加一
		StringBuffer sbf = new StringBuffer();
		sbf.append("select buy_times from ").append(mppayStateTable)
			.append(" where rtu_id = " + rtuId).append(" and mp_id = " + mpId);
		List<Map<String, Object>> list = j_dao.result(sbf.toString());
		int buy_times = 0;
		if(list == null || list.isEmpty()){
			buy_times ++;
		}
		else{
			buy_times = CommFunc.objectToInt(list.get(0).get("buy_times"));
			buy_times ++;
		}
		
		//具体更新列先列出这些，多去少补。
		String sql = "insert into " + jyffTable + "(" +
		"rtu_id," 	+ 		
		"mp_id," 	+ 
		"res_id,"	+   
		"op_man," 	+
		"op_type," 	+ 
		"op_date," 	+
		"op_time," 	+
		"pay_type," +
		"wasteno," + 	//流水号	
		"pay_money," +
		"all_money," +
		"buy_dl," +
		"rewasteno," + //token		
		"buy_times," +
		"visible_flag" +
		")" 		+ 
		
		"values(" 	+
		rtuId 		+ "," 
		+mpId 		+ "," 
		+res_id 	+ ","
		+"'" + op_man + "'," 
		+op_type 	+ "," 
		+ymd 		+ "," 
		+hms 		+ "," 
		+pay_type 	+ "," 
		+"'" + wasteno +  "'," 
		+all_money   + "," 
		+all_money   + "," 
		+all_dl   + "," 	
		+"'"+paytoken   + "'," 		
		+buy_times 	+ "," 
		+visible_flag +")";

		
		//更新预付费状态 
		if(j_dao.executeUpdate(sql)){
			sql = "update " + mppayStateTable + " set "
				+ " cus_state = " 	+ cus_state 
				+ ",op_type = " 	+ op_type
				+ ",op_date = " 	+ ymd
				+ ",op_time = " 	+ hms	
				+ ",pay_money = " 	+ all_money				
				+ ",all_money = " 	+ all_money
				+ ",buy_dl = " 	    + all_dl
				+ ",buy_times = " 	+ buy_times
				+ ",reserve1 = " 	+ seqNo
				+ ",jy_money = " 	+ jy_money
				+ ",reserve2 = '" 	+ keyNo +"'";
				
			//写入开户日期,销户日期置为0
			if(oper_type == SDDef.YFF_OPTYPE_ADDRES){
				sql += ", kh_date = " + ymd + ", xh_date = 0";				
			}
			
			//写入销户日期
			if(oper_type == SDDef.YFF_OPTYPE_DESTORY){
				sql += ", xh_date = " + ymd;
			}
			
			sql += " where rtu_id = " + rtuId + " and mp_id = " 	+ mpId;

			if(j_dao.executeUpdate(sql)){
				result = SDDef.SUCCESS;
				
				if(oper_type == SDDef.YFF_OPTYPE_ADDRES){
					SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_ADD, "Open An Account Successfully");
				}else if(oper_type == SDDef.YFF_OPTYPE_PAY){
					SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_ADD, "Pay Money Successfully:" + all_money +","+rtuId+"["+ mpId +"]");					
				}else if(oper_type == SDDef.YFF_OPTYPE_DESTORY){
					SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_ADD, "Close An Account Successfully");
				}
			}
			else{
				result = SDDef.FAIL;
				if(oper_type == SDDef.YFF_OPTYPE_ADDRES){
					SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_ADD, "Open An Account fail");
				}else if(oper_type == SDDef.YFF_OPTYPE_PAY){
					SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_ADD, "Pay Money fail:" + all_money +","+rtuId+"["+ mpId +"]");					
				}else if(oper_type == SDDef.YFF_OPTYPE_DESTORY){
					SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_ADD, "Close An Account fail");
				}
			}
		}
		else{
			result = SDDef.FAIL;
			if(oper_type == SDDef.YFF_OPTYPE_ADDRES){
				SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_ADD, "Open An Account fail");
			}else if(oper_type == SDDef.YFF_OPTYPE_PAY){
				SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_ADD, "Pay Money fail:" + all_money +","+rtuId+"["+ mpId +"]");					
			}else if(oper_type == SDDef.YFF_OPTYPE_DESTORY){
				SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_ADD, "Close An Account fail");
			}
		}
		
 		if(flag){
			sql = "insert into meter_stspara(rtu_id,mp_id,meter_key,ken,token1,token2)values ("
				+rtuId + "," +mpId 	+ ",'" +meterkey+"',"+KEN+",'"+token1+"','"+token2+"')";			
			if(!j_dao.executeUpdate(sql)){
				
				sql = "update meter_stspara set oldmt_key=meter_key";
						if(!meterkey.equals("")){
							sql = sql + ",meter_key ='" + meterkey + "'";							
						}
						sql = sql + ", ken = "+ KEN+" , token1= '"+token1 +"' , token2='"+token2 +"' where rtu_id ="+rtuId + " and mp_id =" +mpId;			
				if(j_dao.executeUpdate(sql)){
					result = SDDef.SUCCESS;
					SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_UPDATE, "Update Meter Key And Token Successfully");
				}
				else{
					result = SDDef.FAIL;
					SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_UPDATE, "Update Meter Key And Token fail");
				}				
			}		
		}
		params = prt_params;
		return SDDef.SUCCESS;
	}
	
	//缴费
	public String payOper(JSONObject json_param){
		return addNewOper(json_param);
	}

	//补卡
	public String repCardOper(JSONObject json_param){
		return addNewOper(json_param);
	}
	
	//销户
	public String delCusOper(JSONObject json_param){
		
		JSONObject json = JSONObject.fromObject(json_param);
		int rtuId = Integer.parseInt(json.getString("rtuId"));
		short mpId = Short.parseShort(json.getString("mpId"));
		double js_money = Double.parseDouble(json.getString("pay_money"));//负数表示结算时退款金额
		
		String hql =" update MeterStsPara set oldmtKey = '0124000000000001',  meterKey='0124000000000001' where rtuId ="+rtuId +" and mpId = "+mpId;
		HibDao  dao = new  HibDao();
		if(!dao.updateByHql(hql)){
			result = SDDef.FAIL;
		}
		//如果是硬加密，需要是开户状态
		StsComntService stsComntService = StsComntService.getInstance();
		short orgId = stsComntService.getOrgByRtuId(rtuId);
		OrgStsPara orgStsPara = stsComntService.getKMFParam(orgId);
		if(orgStsPara.getET() == STSDef.STS_ET_HARDWARE){
			JDBCDao jdbcDao = new JDBCDao();
			String sql = "update mppay_state set pay_money=0, buy_times = 0,cus_state = 50, now_remain=0.0, kh_date = "+CommFunc.nowDateYmd()+",xh_date = " + CommFunc.nowDateYmd() + ", othjs_money=" + js_money + " where rtu_id = " + rtuId + " and mp_id = " + mpId;
			if(!jdbcDao.executeUpdate(sql)){
				return "";
			}else{
				//硬加密 keychange步骤
				sql = "update meter_stspara set keychange = 1 " + " where rtu_id = " + rtuId + " and mp_id = " + mpId;
				if(!jdbcDao.executeUpdate(sql)){
					return "";
				}else{
					sql = "update mppay_para set tz_val = 0 " + " where rtu_id = " + rtuId + " and mp_id = " + mpId;
					if(!jdbcDao.executeUpdate(sql)){
						return "";
					}
				}
			}
			String wasteno = "PR" + CommFunc.nowDateYmd() + CommFunc.nowTime() + wastenoIndex;	
			String returnStr = "{rtu_id:"+rtuId+",id:"+mpId+",op_date:"+CommFunc.nowDateYmd()+",op_time:"+CommFunc.nowTime()+",op_type:"+1+",optime:\""+CommFunc.FormatToYMD(CommFunc.nowDateYmd(), "day") +" "+ CommFunc.FormatToHMS(CommFunc.nowTime(),1)+"\",wasteno:\""+wasteno+"\",op_man:\""+CommFunc.getUser().getName()+"\",pay_money:"+0+",buy_dl:"+0+"}";
			params = returnStr;
			//日志
			SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_DELETE, "[STS_ET_HARDWARE] delete operation successfully");			
		}else{
			return addNewOper(json_param);
		}
		return SDDef.SUCCESS;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	
	public String getCalcZdl() {
		return calcZdl;
	}

	public void setCalcZdl(String calcZdl) {
		this.calcZdl = calcZdl;
	}
}
