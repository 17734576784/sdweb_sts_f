/**
 *  dbr 2016-11-9下午02:38:48
 */
package com.sts;

import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.util.Log4jUtil;
import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;
import com.sts.common.STSDef;
import com.sts.common.StsAlgo;
import com.sts.common.StsFunc;

import net.sf.json.JSONObject;

/**
 * @author dbr
 *
 */
public class GenerateToken {

	private static int wastenoIndex = 0;
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : GenerateOpenAccountToken
	* <p>DESCRIPTION : 生成开户Token,
	* <p>RETURN      : boolean
	*-----------------------------------------------------------*/
	public boolean GenerateOpenAccountToken(JSONObject json){
		
		try {
  			byte KT  = CommFunc.objectToByte(json.getString("KT"));
			int SGC  = CommFunc.objectToInt(json.getString("SGC"));
			byte TI  = CommFunc.objectToByte(json.getString("TI"));
			byte KRN = CommFunc.objectToByte(json.getString("KRN"));
			int IIN  = CommFunc.objectToInt(json.getString("IIN"));
			long DRN = CommFunc.objectToLong(json.getString("DRN"));	
			int rtuId  = CommFunc.objectToInt(json.getString("rtuId"));
	 		short mpId = CommFunc.objectToShort(json.getString("mpId"));
	 		String vk1 = CommFunc.objectToString(json.getString("vk1"));
	 		
	 		int alldl  = CommFunc.objectToInt(json.getString("alldl").isEmpty() ? "0" :json.getString("alldl"));
	 		//获取表的meterkey
	 		String keydata = CommFunc.loadMeterKey(rtuId,mpId);
	 		int KEN = CommFunc.objectToInt(json.getString("KEN"));
	 		//生成新的meterkey
	 		String pb = SoftEncryptedToken.get_PANBlock(json.getString("DRN"));
	 		String meterKey = StsAlgo.DES(vk1,KT, SGC, TI, KRN, pb).replace(" ", "");		
	 		//ro 默认为1 开户清空购电次数
	 		String token1 = new SoftEncryptedToken().getToken_Set1stSectionDecoderKey((byte)3, (byte)(KEN >> 4),KRN, (byte)1,(byte)0, KT, Long.parseLong(meterKey.substring(0,8),16) ,StsFunc.hexStringToByte(keydata));
			String token2 = new SoftEncryptedToken().getToken_Set2ndSectionDecoderKey((byte)4,(byte)KEN, TI, Long.parseLong(meterKey.substring(meterKey.length()-8, meterKey.length()),16), StsFunc.hexStringToByte(keydata));
			
			String opentoken = "";
			if(alldl != 0){
				opentoken = new SoftEncryptedToken().getToken_TransferCredit((byte)0,(byte)1, StsFunc.getTid(STSDef.STS_BASEDATE_2014),alldl, StsFunc.hexStringToByte(meterKey));
			}
			
			json.put("token1", token1);
			json.put("token2", token2);
			json.put("token3", opentoken);
	 		json.put("cus_state", SDDef.YFF_CUSSTATE_NORMAL);
	 		json.put("op_type",   SDDef.YFF_OPTYPE_ADDRES);

			json.put("meterKey", meterKey);
			//相关存库操作 缴费记录  Token_InfTbl meter_stspara（新旧meterkey）？？？ 
			saveDB(json);
			
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean GenerateRechargeToken(JSONObject json){
		
		try {
			
			int alldl = Integer.parseInt(json.getString("alldl").isEmpty() ? "0" : json.getString("alldl"));	//缴费电量	
			byte rnd = (byte) (Byte.parseByte(json.getString("rnd").isEmpty() ? "0" : json.getString("rnd"))+1%16);

			int rtuId = Integer.parseInt(json.getString("rtuId"));
	 		short mpId = Short.parseShort(json.getString("mpId"));
	 		json.put("cus_state", SDDef.YFF_CUSSTATE_NORMAL);
	 		json.put("op_type", SDDef.YFF_OPTYPE_PAY);
	 		json.put("buy_times", CommFunc.objectToInt(json.getString("rnd")));
	 		json.put("cus_state", SDDef.YFF_CUSSTATE_NORMAL);
	 		json.put("op_type", SDDef.YFF_OPTYPE_PAY);
	 		
	 		byte[] keydata = StsFunc.hexStringToByte(CommFunc.loadMeterKey(rtuId,mpId));
			String token = new SoftEncryptedToken().getToken_TransferCredit((byte)0,rnd, StsFunc.getTid(STSDef.STS_BASEDATE_2014),alldl, keydata);
			json.put("token", token);
			//相关存库操作 缴费记录  Token_InfTbl meter_stspara（新旧meterkey）？？？ 
			saveDB(json);
			
		} catch (Exception e) {
			Log4jUtil.getLogger().logger.info("开户异常 rtuId ： "+ json.getString("rtuId") +" mpId " +json.getString("mpId"));

			return false;
		}
		return true;
	}
	
	
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : GeneratePayToken
	* <p>
	* <p>DESCRIPTION : 生成缴费token
	* <p>COMPLETION
	* <p>INPUT       :  
	* <p>OUTPUT      : 
	* <p>RETURN      : boolean
	* <p>
	*-----------------------------------------------------------*/
	public boolean GeneratePayToken(JSONObject json){
		
		try {
			
			int alldl = Integer.parseInt(json.getString("alldl").isEmpty() ? "0" : json.getString("alldl"));	//缴费电量	
			byte rnd = (byte) (Byte.parseByte(json.getString("rnd").isEmpty() ? "0" : json.getString("rnd"))%16);
	 		
			int rtuId = Integer.parseInt(json.getString("rtuId"));
	 		short mpId = Short.parseShort(json.getString("mpId"));
	 	 
	 		byte[] keydata = StsFunc.hexStringToByte(CommFunc.loadMeterKey(rtuId,mpId));
			//充值token
	 		String token = new SoftEncryptedToken().getToken_TransferCredit((byte)0,rnd, StsFunc.getTid(STSDef.STS_BASEDATE_2014),alldl, keydata);
	 		json.put("token", token);
	 		json.put("cus_state", SDDef.YFF_CUSSTATE_NORMAL);
	 		json.put("op_type", SDDef.YFF_OPTYPE_PAY);
	 		
	 		//相关存库操作 缴费记录  Token_InfTbl meter_stspara（新旧meterkey）？？？ 
			saveDB(json);
			
	 		
		} catch (Exception e) {
			Log4jUtil.getLogger().logger.info("缴费异常 rtuId ： "+ json.getString("rtuId") +" mpId " +json.getString("mpId"));
			return false;
		}
		
		
		
		
		return false;
	}
	
	public boolean GenerateDestoryAccountToken(JSONObject json){
		
		try {
			int showvalue = CommFunc.objectToInt(json.getString("showvalue").isEmpty() ? "0" : json.getString("showvalue"));		
	 		byte RND = (byte) (CommFunc.objectToByte(json.getString("rnd").isEmpty() ? "0" : json.getString("rnd"))%16);
	 		
			int  rtuId = CommFunc.objectToInt(json.getString("rtuId"));
	 		short mpId = CommFunc.objectToShort(json.getString("mpId"));
	 	 
	 		byte[] keydata = StsFunc.hexStringToByte(CommFunc.loadMeterKey(rtuId,mpId));
			String token = new SoftEncryptedToken().getToken_TransferCredit((byte)0,RND, StsFunc.getTid(STSDef.STS_BASEDATE_2014),showvalue, keydata);
			json.put("token", token);
			json.put("cus_state", SDDef.YFF_CUSSTATE_DESTORY);
			json.put("op_type", SDDef.YFF_OPTYPE_DESTORY);
			
			//相关存库操作 缴费记录  Token_InfTbl meter_stspara（新旧meterkey）？？？ 
			saveDB(json);
		} catch (Exception e) {
			Log4jUtil.getLogger().logger.info("销户异常 rtuId ： "+ json.getString("rtuId") +" mpId " +json.getString("mpId"));
			return false;
		}
		
		
		return false;
		
	}
	

	//开户操作数据存库
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : saveDB
	* <p>
	* <p>DESCRIPTION : 更新充值记录  预付费状态  meter_stspara Token_InfTbl？？
	* <p>COMPLETION
	* <p>INPUT       :  
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	public String saveDB(JSONObject json_param){
		try {
			
			String rtuId 		= json_param.getString("rtuId");
			String mpId 		= json_param.getString("mpId");
			String meterId  	= json_param.getString("meterId");
			String cus_state 	= json_param.getString("cus_state");
			String op_type 		= json_param.getString("op_type");		
			String all_money 	= json_param.getString("all_money").isEmpty()? "0" : json_param.getString("all_money");
			String alldl = "0";
			if(json_param.has("alldl")){
				 alldl	=  json_param.getString("alldl").isEmpty()? "0" : json_param.getString("alldl");
			}
			String buy_times 	= json_param.getString("buy_times");
			String paytoken ="",token1 = "",token2 = "",token3 = "",meterkey="",KEN="";
			boolean flag = false;		
			if(json_param.has("token")){
				paytoken 	= json_param.getString("token").isEmpty()? "" : json_param.getString("token");			
			}
			if(json_param.has("token1")){
				meterkey	= json_param.getString("meterKey");
				token1		= json_param.getString("token1");
				token2		= json_param.getString("token2");
				token3		= json_param.getString("token3");
				KEN 		= json_param.getString("KEN");
				flag = true;
			}else{
				token1 = paytoken;
			}
			
			String res_id 		= json_param.getString("res_id");
			String pay_type 	= json_param.getString("pay_type");
			Short visible_flag = 1;
			String seqNo = json_param.getString("seqNo");
			String keyNo = json_param.getString("keyNo");
					
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
			String op_man 	= "sysadmin";//获取当前登录用户
			String ymdhms 	= "20" + CommFunc.nowDate();	//nowDate()方法获取的是YYMMDDHHmmss
			String ymd 		= ymdhms.substring(0, 8);	//年月日
			String hms 		= ymdhms.substring(8, 14);	//时分秒 
			String year 	= CommFunc.nowDateStr().substring(0,4);//年份
			
			//添加缴费记录
			String jyffTable = "yddataben.dbo.jyff" + year;
			//更新预付费状态
			String mppayStateTable = "ydparaben.dbo.mppay_state";		
			wastenoIndex = (wastenoIndex == 10000 ? 1 : wastenoIndex + 1); 
			String wasteno = "PR" + ymdhms + wastenoIndex;			
	  			
			//具体更新列先列出这些，多去少补。
			StringBuffer sql = new StringBuffer();
			sql.append("insert into ").append(jyffTable).append("(rtu_id,mp_id,res_id,op_man,op_type,op_date,");
			sql.append("op_time,pay_type,wasteno,pay_money,all_money,buy_dl,rewasteno,buy_times,visible_flag)");
			sql.append("values(").append(rtuId).append(",").append(mpId).append(",");
			sql.append(res_id).append(",'").append(op_man).append("',").append(op_type).append(",");
			sql.append(ymd).append(",").append(hms).append(",").append(pay_type).append(",'");
			sql.append(wasteno).append("',").append(all_money).append(",").append(all_money).append(",");
			sql.append(alldl).append(",'").append(paytoken).append("',").append(buy_times);
			sql.append(",").append(visible_flag).append(")");
			
			//更新预付费状态 
			if(j_dao.executeUpdate(sql.toString())){
				sql.setLength(0);
				sql.append("update ").append(mppayStateTable).append(" set");
				sql.append(" cus_state = " ).append(cus_state);
				sql.append(" ,op_type = " ).append(op_type);
				sql.append(" ,op_date = " ).append(ymd);
				sql.append(" ,op_time = " ).append(hms);
				sql.append(" ,pay_money = " ).append(all_money);
				sql.append(" ,all_money = " ).append(all_money);
				sql.append(" ,buy_dl = " ).append(alldl);
				sql.append(" ,buy_times = " ).append(buy_times);

				sql.append(" ,reserve1 = " ).append(seqNo);
				sql.append(" ,reserve2 = " ).append(keyNo);

				//写入开户日期,销户日期置为0
				if(oper_type == SDDef.YFF_OPTYPE_ADDRES){
					sql.append(", kh_date = ").append(ymd).append(", xh_date = 0");				
				}
				
				//写入销户日期
				if(oper_type == SDDef.YFF_OPTYPE_DESTORY){
					sql.append(", xh_date = " ).append(ymd);
				}
				
				sql.append(" where rtu_id = ").append(rtuId).append(" and mp_id = ").append(mpId);

				if(j_dao.executeUpdate(sql.toString())){
					System.out.println("更新预付费状态 成功！");
//					result = SDDef.SUCCESS;	
				}
				else{
//					result = SDDef.FAIL;
				}
			}
			else{
//				result = SDDef.FAIL;
			}
			//更新meter_stspara
	 		if(flag){
	 			sql.setLength(0);
	 			sql.append("insert into meter_stspara(rtu_id,mp_id,meter_key,ken,token1,token2)");
	 			sql.append(" values (").append(rtuId).append(",").append(mpId).append(",'");
	 			sql.append(meterkey).append("',").append(KEN).append(",'").append(token1);
	 			sql.append("','").append(token2).append("')");
	 			
				if(!j_dao.executeUpdate(sql.toString())){
					sql.setLength(0);
					sql.append("update meter_stspara set oldmt_key=meter_key,meter_key ='").append(meterkey);
					sql.append("', ken = ").append(KEN).append(" , token1= '").append(token1);
					sql.append("' , token2='").append(token2).append("' where rtu_id =").append(rtuId);
					sql.append(" and mp_id =").append(mpId);
					
					if(j_dao.executeUpdate(sql.toString())){
//						result = SDDef.SUCCESS;	
					}
					else{
//						result = SDDef.FAIL;
					}				
				}		
			}		
	 		
	 		//更新Token_InfTbl
	 		sql.setLength(0);
 			sql.append("insert into Token_InfTbl(meter_id,account_id,rtu_mac,token_type,");
 			sql.append("token1,token2,token3,writ_time)");
 			sql.append(" values (").append(meterId).append(",").append(meterId).append(",'");
 			sql.append(rtuId).append("',").append(op_type).append(",'").append(token1);
 			sql.append("','").append(token2).append("','").append(token3).append("',").append("getdate())");
 			
			if(!j_dao.executeUpdate(sql.toString())){
				Log4jUtil.getLogger().logger.info("Token_InfTbl保存失败！ sql : " +sql.toString());
			}
	 		
		} catch (Exception e) {
			Log4jUtil.getLogger().logger.info("存库异常！！！");
		}
 		
 		return SDDef.SUCCESS;
	}

}
