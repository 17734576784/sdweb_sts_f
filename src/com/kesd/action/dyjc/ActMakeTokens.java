package com.kesd.action.dyjc;

import javassist.scopedpool.SoftValueHashMap;
import net.sf.json.JSONObject;
import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.dbpara.OrgStsPara;
import com.libweb.dao.HibDao;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;
import com.sts.SoftEncryptedToken;
import com.sts.common.STSDef;
import com.sts.common.StsAlgo;
import com.sts.common.StsFunc;
import com.sts.comnt.StsComntService;

public class ActMakeTokens extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5719538198884786432L;
	private String result;
	private String params;
	
	public String execute(){
		return SDDef.SUCCESS;
	}
	
	//生成新的meterKey
	public String GenerateDES(){
		JSONObject json = JSONObject.fromObject(result);
		byte KT = Byte.parseByte(json.getString("KT"));
		int SGC = Integer.parseInt(json.getString("SGC"));
		byte TI = Byte.parseByte(json.getString("TI"));
		byte KRN = Byte.parseByte(json.getString("KRN"));
		int IIN = Integer.parseInt(json.getString("IIN"));
		long DRN = Long.parseLong(json.getString("DRN"));
		String pb = SoftEncryptedToken.get_PANBlock(json.getString("DRN"));
		String vk = json.getString("vk1");
		
		result = StsAlgo.DES(vk,KT, SGC, TI, KRN, pb);
		

		int rtuId = Integer.parseInt(json.getString("rtuId"));
 		short mpId = Short.parseShort(json.getString("mpId"));
 		
		String sql = "update meterpara set comm_addr = '"+ json.getString("DRN") +"'  where rtu_id = "+ rtuId +"  and mp_id = "+mpId;
		JDBCDao dao = new JDBCDao();
 		//更新DRN
		if(dao.executeUpdate(sql)){
			sql = "update mppay_para set key_version = "+ KRN +"  where rtu_id = "+ rtuId +"  and mp_id = "+mpId;
			//更新KRN
			if(dao.executeUpdate(sql)){
				sql = "update orgpara set r_org_no = "+ SGC +"  where  id = (select cons.org_id  from conspara cons,rtupara rtu where rtu.cons_id = cons.id and rtu.id = "+rtuId +")";
				//更新SGC
				if(dao.executeUpdate(sql)){
					sql = " update  yffratepara set rateid = " + TI + " where id = (select feeproj_id from mppay_para where rtu_id = "+ rtuId + " and mp_id = "+mpId+")";
					//更新TI
					if(dao.executeUpdate(sql)){
						
					}
				}
			}
		}		
		return SDDef.SUCCESS;
		
	}
	
	//生成class2 token
	public String GenerateClass2Token(){
		JSONObject json_param = JSONObject.fromObject(result);
		int rtuId = json_param.optInt("rtuId");
		short mpId = CommFunc.objectToShort(json_param.optInt("mpId"));
		StsComntService stsComntService = StsComntService.getInstance();
		short orgId = stsComntService.getOrgByRtuId(rtuId);
		OrgStsPara orgStsPara = stsComntService.getKMFParam(orgId);
		//根据加密方式调用对应的加密规则
		//软件加密
		if(orgStsPara.getET() == STSDef.STS_ET_SOFT){
			result = GenerateClass2TokenSoft();
		}
		//串口加密
		else{
			result = generateClass2TokenSerial(json_param, stsComntService);
		}
		return SDDef.SUCCESS;
	}
	
	//软件加密
	public String GenerateClass2TokenSoft(){
		JSONObject json = JSONObject.fromObject(result);
		byte tokentype = Byte.parseByte(json.getString("tokentype"));
		byte subclass = Byte.parseByte(json.getString("subclass"));
  		int showvalue = Integer.parseInt(json.getString("showvalue").isEmpty() ? "0" : json.getString("showvalue"));		
 		byte RND = (byte) (Byte.parseByte(json.getString("RND").isEmpty() ? "0" : json.getString("RND"))%16);
    
 		byte newKRN = (byte) (Byte.parseByte(json.getString("newKRN").isEmpty() ? "0" : json.getString("newKRN")));
 		byte newKT = (byte) (Byte.parseByte(json.getString("newKT").isEmpty() ? "0" : json.getString("newKT")));
 		byte newTI = (byte) (Byte.parseByte(json.getString("newTI").isEmpty() ? "0" : json.getString("newTI"))); 		
 		byte res = (byte) (Byte.parseByte(json.getString("res").isEmpty() ? "0" : json.getString("res")));
 		byte ro = (byte) (Byte.parseByte(json.getString("ro").isEmpty() ? "0" : json.getString("ro")));
  
 		int newKEN = Integer.parseInt(json.getString("newKEN").isEmpty() ? "0" : json.getString("newKEN"));
 		String newmeterkey = json.getString("newmeterkey").isEmpty() ? "0" : json.getString("newmeterkey").replace(" ", ""); 
 	 	 
 		int rtuId = Integer.parseInt(json.getString("rtuId"));
 		short mpId = Short.parseShort(json.getString("mpId"));
 		String keydata = CommFunc.loadMeterKey(rtuId,mpId); 
 		String oldkeydata = CommFunc.loadOldMeterKey(rtuId,mpId); 
 		
 		String token = "";
		switch(tokentype){
		case 0 :
			 token = new SoftEncryptedToken().getToken_SetMaximumPowerLimit(subclass,RND, StsFunc.getTid(STSDef.STS_BASEDATE_2014), showvalue, StsFunc.hexStringToByte(keydata));
			break;
		case 1:
			 token = new SoftEncryptedToken().getToken_ClearCredit(subclass,RND, StsFunc.getTid(STSDef.STS_BASEDATE_2014), showvalue, StsFunc.hexStringToByte(keydata));
			break;
		case 3:
			 token = new SoftEncryptedToken().getToken_Set1stSectionDecoderKey(subclass, (byte)(newKEN >> 4),newKRN, ro, res, newKT, Long.parseLong(newmeterkey.substring(0,8),16) ,StsFunc.hexStringToByte(oldkeydata));
			 token +="  Token2 ：  "+ new SoftEncryptedToken().getToken_Set2ndSectionDecoderKey(STSDef.STS_CLS2_SUBCLS_SET_2SDK,(byte)newKEN, newTI, Long.parseLong(newmeterkey.substring(newmeterkey.length()-8, newmeterkey.length()),16), StsFunc.hexStringToByte(oldkeydata));
			 saveKEN(newKEN);
			 break;
		case 4:  
			 token = new SoftEncryptedToken().getToken_Set2ndSectionDecoderKey(subclass,(byte)newKEN, newTI, Long.parseLong(newmeterkey.substring(newmeterkey.length()-8, newmeterkey.length()),16), StsFunc.hexStringToByte(keydata));
			 saveKEN(newKEN);
			 break;
		case 5:
			 token = new SoftEncryptedToken().getToken_ClearTamperCondition(subclass,RND, StsFunc.getTid(STSDef.STS_BASEDATE_2014), showvalue, StsFunc.hexStringToByte(keydata));
			break;
		case 6:
			 token = new SoftEncryptedToken().getToken_SetMaximumPhasePowerUnbalanceLimit(subclass,RND, StsFunc.getTid(STSDef.STS_BASEDATE_2014), showvalue, StsFunc.hexStringToByte(keydata));
			break;
		case 11:
			 token = new SoftEncryptedToken().getToken_ProprietaryReserved(subclass,RND, StsFunc.getTid(STSDef.STS_BASEDATE_2014), showvalue, StsFunc.hexStringToByte(keydata));
			break;

		}
		
		String[] tokens = token.split("Token2 ：");
		String token1 = "",token2="";
		token1 = token.split("Token2 ：")[0];
		if(tokens.length > 1){			
			token2 = token.split("Token2 ：")[1];
		}
		
		String ymdhms 	= "20" + CommFunc.nowDate();	//nowDate()方法获取的是YYMMDDHHmmss
		String ymd 		= ymdhms.substring(0, 8);	//年月日
		String hms 		= ymdhms.substring(8, 14);	//时分秒 
 		params="{rtu_id:"+rtuId+",id:"+mpId+",op_date:"+ymd+",op_time:"+hms+",wasteno:'"+token1+","+token2+"'}";
 		return token;	
	}
	
	//串口进行加密
	public String generateClass2TokenSerial(JSONObject json_param, StsComntService stsComntService){
		int transferAmount = json_param.optInt("showvalue");
		int rtuId = json_param.optInt("rtuId");
		short mpId = CommFunc.objectToShort(json_param.optInt("mpId"));
		String resNo = json_param.optString("resNo"); 
		String resDesc = json_param.optString("resDesc");
		
		String meterAddress = json_param.optString("meterAddr");
		byte tokenType = CommFunc.objectToByte(json_param.optInt("tokentype"));
		
		String token = null;
		if(tokenType == STSDef.STS_TOOL_SETMAXIMUMPOWERLIMIT 
			|| tokenType == STSDef.STS_TOOL_CLEARTAMPERCONDITION 
			|| tokenType == STSDef.STS_TOOL_SETMAXIMUMPHASEPOWERUNABLANCELIMIT 
			|| tokenType == STSDef.STS_TOOL_RESERVEDPROPRIETARYUSE){
			token = stsComntService.setMaxPowerToken(transferAmount, meterAddress, rtuId, mpId, tokenType);
		}
		else if(tokenType == STSDef.STS_TOOL_CLEARCREDIT){
			token = stsComntService.setClearCreditToken(transferAmount, meterAddress, rtuId, mpId, resNo, resDesc, tokenType);
		}
		else{
			String newSGC = json_param.optString("newSGC");
			int newTI = json_param.optInt("newTI");
			byte newKRN = CommFunc.objectToByte(json_param.optString("newKRN")); 
			byte newmeterkey = CommFunc.objectToByte(json_param.optString("newmeterkey"));
			int newKEN = json_param.optInt("newKEN");	
			token = stsComntService.changeKey(meterAddress, rtuId, mpId, newmeterkey, newSGC, newTI, newKRN, newKEN);
		}
		
		return token;
	}
	
	//生成class1 token
	public String GenerateClass1Token(){
		
		JSONObject json = JSONObject.fromObject(result); 		
 		Long controlvalue = Long.parseLong(json.getString("controlvalue").isEmpty() ? "0" : json.getString("controlvalue"),16); 
 				
 		int rtuId = Integer.parseInt(json.getString("rtuId"));
 		short mpId = Short.parseShort(json.getString("mpId")); 	 
 		byte[] keydata = StsFunc.hexStringToByte(CommFunc.loadMeterKey(rtuId,mpId));
 		  
 		byte subclass1 = (byte) (Byte.parseByte(json.getString("subclass1").isEmpty() ? "0" : json.getString("subclass1")));
// 		byte mfrcode = (byte) (Byte.parseByte(json.getString("mfrcode").isEmpty() ? "0" : json.getString("mfrcode")));
 		String mfrcode = json.getString("mfrcode").isEmpty() ? "00" : json.getString("mfrcode");
 		//mfrcode厂商代码如果是4位，subclass应该为1
 		String token = new SoftEncryptedToken().getToken_InitIateMeterTestOrDisplay(subclass1, controlvalue, mfrcode, keydata);
 		
 		String ymdhms 	= "20" + CommFunc.nowDate();	//nowDate()方法获取的是YYMMDDHHmmss
		String ymd 		= ymdhms.substring(0, 8);	//年月日
		String hms 		= ymdhms.substring(8, 14);	//时分秒 
 		params="{rtu_id:"+rtuId+",id:"+mpId+",op_date:"+ymd+",op_time:"+hms+",wasteno:'"+token+"'}";
 		
 		result = token;
		return SDDef.SUCCESS;
		
	}
	
	//保存meterkey
	public String saveMeterToken(){
		
		JSONObject json = JSONObject.fromObject(result);
		int rtuId  = Integer.parseInt(json.getString("rtuId"));
		short mpId = Short.parseShort(json.getString("mpId"));
		String meterToken = json.getString("meterToken").isEmpty()?"":json.getString("meterToken");
		if(meterToken.isEmpty()){
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		
		String hql =" update MeterStsPara set oldmtKey =  meterKey ,  meterKey='"+meterToken.replace(" ", "")+"' where rtuId ="+rtuId +" and mpId = "+mpId;
		HibDao  dao = new  HibDao();
		if(!dao.updateByHql(hql)){
			result = SDDef.FAIL;
		}
		
		result = SDDef.SUCCESS;
		return SDDef.SUCCESS;
	}
	
	//保存token
	public String saveKEN(int newKEN){
		
		JSONObject json = JSONObject.fromObject(result);
		int rtuId  = Integer.parseInt(json.getString("rtuId"));
		short mpId = Short.parseShort(json.getString("mpId")); 	 
		
		String hql =" update MeterStsPara set ken='"+newKEN+"' where rtuId ="+rtuId +" and mpId = "+mpId;
		HibDao  dao = new  HibDao();
		if(!dao.updateByHql(hql)){
			result = SDDef.FAIL;
		}
		
		result = SDDef.SUCCESS;
		return SDDef.SUCCESS;
	}
	
	//保存p1 p2 token
	public String saveClass2Token(){
		
		JSONObject json = JSONObject.fromObject(result);
		int rtuId = Integer.parseInt(json.getString("rtuId"));
		short mpId = Short.parseShort(json.getString("mpId"));
		String class2Token = json.getString("class2Token").isEmpty()?"":json.getString("class2Token");
		if(class2Token.isEmpty()){
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		
		String[] tokens = class2Token.split("Token2 ：");		
		String hql =" update MeterStsPara set token1 = '"+tokens[0].trim()+"' ,  token2='"+tokens[1]+"' where rtuId ="+rtuId +" and mpId = "+mpId;
		
		HibDao  dao = new  HibDao();
		if(!dao.updateByHql(hql)){
			result = SDDef.FAIL;
		}
		
		String ymdhms 	= "20" + CommFunc.nowDate();	//nowDate()方法获取的是YYMMDDHHmmss
		String ymd 		= ymdhms.substring(0, 8);	//年月日
		String hms 		= ymdhms.substring(8, 14);	//时分秒 
 		params="{rtu_id:"+rtuId+",id:"+mpId+",op_date:"+ymd+",op_time:"+hms+",wasteno:'"+tokens[0].trim()+","+tokens[1].trim()+"'}";
 		
		result = SDDef.SUCCESS;
		return SDDef.SUCCESS;
	}
	
	//生成开户token
	public String makeOpenAccountToken(){
		JSONObject json_param = JSONObject.fromObject(result);
		int rtuId = json_param.optInt("rtuId");
		short mpId = CommFunc.objectToShort(json_param.optInt("mpId"));
		StsComntService stsComntService = StsComntService.getInstance();
		short orgId = stsComntService.getOrgByRtuId(rtuId);
		OrgStsPara orgStsPara = stsComntService.getKMFParam(orgId);
		//根据加密方式调用对应的加密规则
		//软件加密
		if(orgStsPara.getET() == STSDef.STS_ET_SOFT){
			makeOpenAccountTokenSoft(json_param);
		}
		//串口加密
		else{
			int payQuantity = json_param.optInt("alldl");
			String meterAddress = json_param.optString("DRN");
			
			String token = stsComntService.addNewOperToken(payQuantity, meterAddress, rtuId, mpId);
			if(!token.equals("")){
				JDBCDao jdbcDao = new JDBCDao();
				//修改keychange 置为0
				String sql = "update meter_stspara set keychange = 0 where rtu_id = " + rtuId + " and mp_id = " + mpId;					
				if(!jdbcDao.executeUpdate(sql)){
					result = "";
				}
			}		
			result = token;
		}
		return SDDef.SUCCESS;
	}
	
	//软件加密,生成开户token
	private void makeOpenAccountTokenSoft(JSONObject json){
		byte KT = Byte.parseByte(json.getString("KT"));
		int SGC = Integer.parseInt(json.getString("SGC"));
		byte TI = Byte.parseByte(json.getString("TI"));
		byte KRN = Byte.parseByte(json.getString("KRN"));
		int IIN = Integer.parseInt(json.getString("IIN"));
		
		//Long DRN = Long.parseLong(meteraddr); //CommFunc.objectToLong(meteraddr);
		
		//long DRN = Long.parseLong(json.getString("DRN"));	
		int rtuId = Integer.parseInt(json.getString("rtuId"));
 		short mpId = Short.parseShort(json.getString("mpId"));
 		int alldl = Integer.parseInt(json.getString("alldl").isEmpty() ? "0" :json.getString("alldl"));
 	 
 		String keydata = CommFunc.loadMeterKey(rtuId,mpId);
 		int KEN = Integer.parseInt(json.getString("KEN"));
 		String vk = json.getString("vk");

		String pb = SoftEncryptedToken.get_PANBlock(json.getString("DRN"));
 		String meterKey = StsAlgo.DES(vk,KT, SGC, TI, KRN, pb).replace(" ", "");	
 		//ro 默认为1 开户清空购电次数
 		String token1 = new SoftEncryptedToken().getToken_Set1stSectionDecoderKey((byte)3, (byte)(KEN >> 4),KRN, (byte)1,(byte)0, KT, Long.parseLong(meterKey.substring(0,8),16) ,StsFunc.hexStringToByte(keydata));
		String token2 = new SoftEncryptedToken().getToken_Set2ndSectionDecoderKey((byte)4,(byte)KEN, TI, Long.parseLong(meterKey.substring(meterKey.length()-8, meterKey.length()),16), StsFunc.hexStringToByte(keydata));
		String opentoken = "";
		if(alldl != 0){
			opentoken = new SoftEncryptedToken().getToken_TransferCredit((byte)0,(byte)1, StsFunc.getTid(STSDef.STS_BASEDATE_2014),alldl, StsFunc.hexStringToByte(meterKey));
		}
		
		String retval ="{meterKey:'"+meterKey+"',token1:'"+token1+"',token2:'"+token2+"',opentoken:'"+opentoken+"'}";		
		
		if(!token1.equals("") && !token2.equals("")){
			JDBCDao jdbcDao = new JDBCDao();
			//修改keychange 置为0
			String sql = "update meter_stspara set keychange = 0 where rtu_id = " + rtuId + " and mp_id = " + mpId;					
			if(!jdbcDao.executeUpdate(sql)){
				result = retval;
			}			
		}		
		result = retval;		
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
	
}
