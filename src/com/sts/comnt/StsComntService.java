package com.sts.comnt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import net.sf.json.JSONObject;
import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.common.WebConfig;
import com.kesd.dbpara.OrgStsPara;
import com.kesd.dbpara.YffManDef;
import com.kesd.util.MSG_RESP;
import com.libweb.dao.HibDao;
import com.libweb.dao.JDBCDao;
import com.sts.StsTaskQueue;
import com.sts.common.STSDef;
import com.sts.common.SerialOperation;
import com.sts.common.StsEncryptor;
import com.sts.common.StsFunc;
import com.sts.common.StsOptDef;
import com.sts.model.MsgTaskTokenPay;
import com.sts.model.ToolRecord;

import freemarker.cache.StrongCacheStorage;

/**
 * @author Administrator
 */
public class StsComntService{
	/**
	 * 实现单例加载
	 * */
	private static StsComntService instance = new StsComntService();
	private StsComntService(){
		
	}
	
	public static StsComntService getInstance(){
		return instance;
	}
	
	//开户  (参数暂时定义为String类型),目前规约竟然不支持？？
	public String addNewOperToken(int payQuantity, String meterAddress, int rtuId, int mpId){
		//获取该表KMF
		Map<String, String> kmfMap = getMeterKMF(rtuId);
		
		//获取tariffIndex(费率方案yffratepara中的rateid)
		//int tariffIndex = getMeterTariffIndex(rtuId, mpId);
		//int ken = getMeterKeyExpiryNumber(rtuId, mpId);
		JSONObject meterInfo = getMeterStsInfo(rtuId, mpId);
		
		String strSend = null, strReceive = null;
		MSG_RESP resp = null;
		
		synchronized (this) {
			strSend = StsEncryptor.api_GenKeyChangeToken_Req(
					1, 	//先写死
					7, 	//先写死
					StsFunc.getPAN(meterAddress), 
					kmfMap.get("supplyGroupCode"), 
					meterInfo.getInt("tariffIndex"),//tariffIndex, 
					meterInfo.getString("keyRevision"),//kmfMap.get("keyRevisionNumber"), 
					meterInfo.getInt("ken"));//CommFunc.objectToInt(kmfMap.get("keyExpiryNumber"))
			strReceive = stsCommuniate(strSend);
	
			/*
			strSend = StsEncryptor.api_Change_Key_Req(
					StsFunc.getPAN(meterAddress),  
					CommFunc.objectToInt(kmfMap.get("keyRegisterNO")), 
					vending_key_reg_no_new, 
					kmfMap.get("supplyGroupCode"), 
					sgc_new, 
					meterInfo.getInt("tariffIndex"),//tariffIndex, 
					tariff_index_new, 
					CommFunc.objectToByte(meterInfo.getInt("keyRevision")),//CommFunc.objectToByte(kmfMap.get("keyRevisionNumber")), 
					key_revision_no_new, 
					CommFunc.objectToInt(meterInfo.getInt("ken")),//CommFunc.objectToInt(kmfMap.get("keyExpiryNumber")), 
					key_expiry_no_new);
			strReceive = stsCommuniate(strSend);
			*/
			
			//对串口返回数据进行解密,获取数据
			resp = StsEncryptor.api_GetIden_Resp(strReceive);
			//解析respm,验证一下返回的结果是否正确 
			
			//获取开户返回的token
			JSONObject tokenJson = new JSONObject(); 
			tokenJson.put("token1", "Decimal Key Token");
			tokenJson.put("token2", "Decimal Key Token");
			
			//执行缴费
//			if(payQuantity > 0){
//				tokenJson.put("PayToken", payOperToken(payQuantity, meterAddress, rtuId, mpId));
//			}
			return tokenJson.toString();
		}
	}
	
	//缴费
	public String payOperToken(int payQuantity, String meterAddress, int rtuId, int mpId){
		Map<String, String> kmfMap = getMeterKMF(rtuId);
		MsgTaskTokenPay msgTaskTokenPay = getMsgTaskTokenPay(payQuantity, meterAddress, rtuId, mpId, kmfMap);
		//有用否？？
		//MsgTaskToken msgTaskToken = new MsgTaskToken(MagTokenKey.TOKEN_PAY, 10, msgTaskTokenPay);	//第二个参数暂定为10，需要和平哥确定该参数是否有用 
		//根据KEN判断TID是否过期
		if (isTidExpired(CommFunc.objectToInt(msgTaskTokenPay.getKeyExpiryNumber()), CommFunc.objectToInt(msgTaskTokenPay.getTokenId()))) {
			return "STS Token Expired";
		}
		
		int step = StsOptDef.STS_OPT_PAY_STEP_1;
		String strSend = null, strReceive = null;
		MSG_RESP resp = null;
		
		synchronized (this) {
			String smId = null, sequenceNumber = null, balanceAmount = null;
			
			while (step < StsOptDef.STS_OPT_PAY_STEP_3) { 
				if (step == StsOptDef.STS_OPT_PAY_STEP_1) {
					strSend = StsEncryptor.api_GetIden_Req();
					//获取串口返回数据
					strReceive = stsCommuniate(strSend);
					//对串口返回数据进行解密,获取数据
					resp = StsEncryptor.api_GetIden_Resp(strReceive);
					System.out.println("step1接收到了串口的返回数据，即将放入应答队列:" + strReceive);
					smId = resp.getObj().optString("devId");
				}
				else if (step == StsOptDef.STS_OPT_PAY_STEP_2) {
					strSend = StsEncryptor.api_STS_Credit_Balance_Status_Req();
					strReceive = stsCommuniate(strSend);	//获取串口返回数据
					resp = StsEncryptor.api_STS_Credit_Balance_Status_Resq(strReceive);
					System.out.println("step2接收到了串口的返回数据，即将放入应答队列:" + strReceive);
					sequenceNumber = resp.getObj().optString("creditUpdateSeqNo");
					balanceAmount = resp.getObj().optString("creditBalanceAmount");	//查询出当前池子中的余额
				}
				step ++;
			}
			
			JSONObject object = resp.getObj();
			if(object == null) return null;
			
			//根据参考算法获取需要填充金额
			int remain = getPoolRemain(balanceAmount);
			int maxinum = 18201624;	//文档中提供的最大值
			
			if("0000".equals(balanceAmount) || remain < payQuantity){
				char keyType 				= 'C';
				char keyParity 				= 'S';
				char keyEncryptionMethod 	= 'T';
//				if(CommFunc.objectToString(kmfMap.get("keyType")).length()>0){
//					keyType 	= CommFunc.objectToString(kmfMap.get("keyType")).charAt(0);
//				}
				if(CommFunc.objectToString(kmfMap.get("keyParity")).length()>0){
					keyParity 	= CommFunc.objectToString(kmfMap.get("keyParity")).charAt(0);
				}
				if(CommFunc.objectToString(kmfMap.get("keyEncryptionMethod")).length()>0){
					keyEncryptionMethod 	= CommFunc.objectToString(kmfMap.get("keyEncryptionMethod")).charAt(0);
				}
				//keyType = 'C';
				//售电电量池补齐流程
				//SM?GK	--返回的数据没发现有其他地方调用 //CommFunc.objectToInt(kmfMap.get("keyRegisterNO"))
				resp = generateSingleLength(84, //固定值84 以前 CommFunc.objectToInt(kmfMap.get("keyRegisterNO"))
						'C',				//固定值'C'   以前 keyType
						keyParity,
						CommFunc.objectToInt(kmfMap.get("parentRegisterNumber")),
						keyEncryptionMethod);
				
				//获取creatUpdateBlock
				String plaintext_data_stream = StsFunc.getCreditUpdateBlock(
						1,						//定死为0 
						smId, 
						(CommFunc.objectToInt(sequenceNumber)+1)%99, 
						StsFunc.getStsAmount(maxinum - remain));		//需要补充的金额
				
				//SM?ED	返回参数用在哪里？
				resp = encryptDataStream(0, 	//暂时固定传值为0
						84, 	//CommFunc.objectToInt(kmfMap.get("keyRegisterNO"))
						0, 						//暂时固定传值为0
						0, 						//暂时固定传值为0
						'H',					//必须是大写H	 
						plaintext_data_stream);
				//经过加密的串，传参到下一步
				String ciphDataStream = resp.getObj().optString("ciphDataStream");		

				//SM?TU	CommFunc.objectToInt(kmfMap.get("keyRegisterNO"))
				resp = tu(84, ciphDataStream);
			}
			
			//进行缴费操作，总池子中剩余总量可以满足本次缴费
			resp = pay(msgTaskTokenPay);
			
			//最后返回Token到界面
			if(resp.getObj() == null || !resp.getObj().containsKey("decCreditToken")){
				return "STS Token Error";
			}
			else{
				String res = resp.getObj().getString("decCreditToken");
				return CommFunc.splitToken(res);
			}	
		}
	}
	
	//SM?GK Generate Single-Length
	public MSG_RESP generateSingleLength(int key_reg_no, char key_type, 
			char key_parity, int parent_key_reg_no, char encryption_method){
		String strSend = null, strReceive = null;
		MSG_RESP resp = null;
		strSend = StsEncryptor.api_Generate_DEA_Key_Req(key_reg_no, key_type, key_parity, parent_key_reg_no, encryption_method);
		strReceive = stsCommuniate(strSend);
		//对串口返回数据进行解密,获取数据
		resp = StsEncryptor.api_Generate_DEA_Key_Resp(strReceive);
		return resp;
	}
	
	//SM?ED : encrypt Data Stream
	public MSG_RESP encryptDataStream(int encryption_mode, int key_reg_no, int init_vect_reg_no, 
			int last_ciphblk_reg_no, int data_stream_representation, String plaintext_data_stream){
		String strSend = null, strReceive = null;
		MSG_RESP resp = null;
		strSend = StsEncryptor.api_Encrypt_Data_Stream_Req(encryption_mode, key_reg_no, init_vect_reg_no, last_ciphblk_reg_no, data_stream_representation, plaintext_data_stream);
		strReceive = stsCommuniate(strSend);
		//对串口返回数据进行解密,获取数据
		resp = StsEncryptor.api_Encrypt_Data_Stream_Resp(strReceive);
		return resp;
	}
	
	//SM?TU
	public MSG_RESP tu(int key_reg_no, String credit_update_block){
		String strSend = null, strReceive = null;
		MSG_RESP resp = null;
		strSend = StsEncryptor.api_STS_Update_Credit_Balance_Req(key_reg_no, credit_update_block);
		strReceive = stsCommuniate(strSend);	
		resp = StsEncryptor.api_STS_Update_Credit_Balance_Resp(strReceive);
		return resp;
	}
	
	//SM?TC
	public MSG_RESP pay(MsgTaskTokenPay msgTaskTokenPay){
		String strSend = null, strReceive = null;
		MSG_RESP resp = null;
		
		strSend = StsEncryptor.api_Encrypt_Credit_Token_Req(
				msgTaskTokenPay.getDispenserPan(), 
				CommFunc.objectToInt(msgTaskTokenPay.getKeyRegNo()), 
				msgTaskTokenPay.getSgc(), 
				msgTaskTokenPay.getTariffIndex(), 
				CommFunc.objectToInt(msgTaskTokenPay.getKeyRevisionNumber()), 
				CommFunc.objectToInt(msgTaskTokenPay.getKeyExpiryNumber()), 
				msgTaskTokenPay.getCreditType(), 
				msgTaskTokenPay.getTokenId(), 
				msgTaskTokenPay.getPayQuantity()); 
		strReceive = stsCommuniate(strSend);	//获取串口返回数据
		resp = StsEncryptor.api_Encrypt_Credit_Token_Resp(strReceive);
		return resp;
	}
	
	//设置最大功率,clearTamperCondition等
	public String setMaxPowerToken(int transfer_amount, String meterAddress, int rtuId, int mpId, byte management_type){
		//获取该表KMF
		Map<String, String> kmfMap = getMeterKMF(rtuId);
		
		//获取tariffIndex(费率方案yffratepara中的rateid)
		
		//int tariffIndex = getMeterTariffIndex(rtuId, mpId);

		JSONObject meterInfo = getMeterStsInfo(rtuId, mpId);
		
		String strSend = null, strReceive = null;
		MSG_RESP resp = null;

		//获取tokenId
		String tokenId = String.valueOf(StsFunc.getTid(WebConfig.sts_baseDate, getNowFormatTMD()));
		
		synchronized (this) {
			strSend = StsEncryptor.api_STS_Maximum_power_req(
					StsFunc.getPAN(meterAddress), 
					CommFunc.objectToInt(kmfMap.get("keyRegisterNO")),
					kmfMap.get("supplyGroupCode"), 
					meterInfo.getInt("tariffIndex"),//tariffIndex, 
					meterInfo.getInt("keyRevision"),	//CommFunc.objectToInt(kmfMap.get("keyRevisionNumber")),
					meterInfo.getInt("ken"),//CommFunc.objectToInt(kmfMap.get("keyExpiryNumber")),
					management_type,
					tokenId,	
					transfer_amount
			);
			strReceive = stsCommuniate(strSend);
			//对串口返回数据进行解密,获取数据
			resp = StsEncryptor.api_STS_Maximum_power_Resp(strReceive);
			if(resp.getObj() == null || !resp.getObj().containsKey("decCreditToken")){
				return "GET TOKEN ERROR!";
			}
			else{
				String res = resp.getObj().getString("decCreditToken");
				return CommFunc.splitToken(res);
			}
		}
	}
	
	//清空credit
	public String setClearCreditToken(int transfer_amount, String meterAddress, int rtuId, int mpId, 
			String resNo, String resDesc, byte operType){
		//获取该表KMF
		Map<String, String> kmfMap = getMeterKMF(rtuId);
		
		//获取tariffIndex(费率方案yffratepara中的rateid)
		//int tariffIndex = getMeterTariffIndex(rtuId, mpId);

		JSONObject meterInfo = getMeterStsInfo(rtuId, mpId);
		
		String strSend = null, strReceive = null;
		MSG_RESP resp = null;
		
		//获取当前系统时间
		int ymd = CommFunc.nowDateInt();
		int hms = CommFunc.nowTimeInt();
		
		//测试用例，测试完要删除
		//ymd = 20040329;
		//hms = 300;
		
		//暂时先使用getPayTokenTime，两者存库查询不一致
		String tokenTime = getClearCreditTime(rtuId, mpId, ymd, hms, operType);
		//获取tokenId
		String tokenId = String.valueOf(StsFunc.getTid(WebConfig.sts_baseDate, tokenTime));
		
		synchronized (this) {
			strSend = StsEncryptor.api_STS_ClearCredit_req(
					StsFunc.getPAN(meterAddress), 
					CommFunc.objectToInt(kmfMap.get("keyRegisterNO")),
					kmfMap.get("supplyGroupCode"), 
					meterInfo.getInt("tariffIndex"),//tariffIndex, 
					meterInfo.getInt("keyRevision"),//CommFunc.objectToInt(kmfMap.get("keyRevisionNumber")),
					meterInfo.getInt("ken"),//CommFunc.objectToInt(kmfMap.get("keyExpiryNumber")),
					operType,
					tokenId,	
					transfer_amount
			);
			strReceive = stsCommuniate(strSend);
			//对串口返回数据进行解密,获取数据
			resp = StsEncryptor.api_STS_ClearCredit_Resp(strReceive);
			if(resp.getObj() == null || !resp.getObj().containsKey("decCreditToken")){
				return "GET TOKEN ERROR!";
			}
			else{
				String res = resp.getObj().getString("decCreditToken");
				//操作信息存库
				addToolRecordOper(rtuId, CommFunc.objectToShort(mpId), resNo, resDesc, STSDef.STS_TOOL_CLEARCREDIT, res);
				return CommFunc.splitToken(res);
			}
		}
	}
	
	//change key(SM?TK)
	public String changeKey(String meterAddress, int rtuId, int mpId, int vending_key_reg_no_new, 
			String sgc_new, int tariff_index_new, byte key_revision_no_new, int key_expiry_no_new){
		//获取该表当前KMF
		Map<String, String> kmfMap = getMeterKMF(rtuId);
		
		//int tariffIndex = getMeterTariffIndex(rtuId, mpId);

		JSONObject meterInfo = getMeterStsChangeKeyInfo(rtuId, mpId);
		
		String strSend = null, strReceive = null;
		MSG_RESP resp = null;
		
		strSend = StsEncryptor.api_Change_Key_Req(
				StsFunc.getPAN(meterAddress),  
				CommFunc.objectToInt(kmfMap.get("keyRegisterNO")), 
				vending_key_reg_no_new, 
				kmfMap.get("supplyGroupCode"), 
				sgc_new, 
				meterInfo.getInt("tariffIndex"),//tariffIndex, 
				tariff_index_new, 
				CommFunc.objectToByte(meterInfo.getInt("keyRevision")),//CommFunc.objectToByte(kmfMap.get("keyRevisionNumber")), 
				key_revision_no_new, 
				CommFunc.objectToInt(meterInfo.getInt("ken")),//CommFunc.objectToInt(kmfMap.get("keyExpiryNumber")), 
				key_expiry_no_new);
		strReceive = stsCommuniate(strSend);
		//对串口返回数据进行解密,获取数据
		resp = StsEncryptor.api_Change_Key_Resp(strReceive);
		if(resp.getObj() == null){
			return "GET TOKEN ERROR!";
		}
		else{
			StringBuffer sbf = new StringBuffer();
			//前端以字符拼接形式接受数据，哥也是没办法啊，这样写吧^_^
			//sbf.append("Token1 :");
			sbf.append(CommFunc.splitToken(resp.getObj().getString("decKeyToken1")))
			   //.append(" , Token2 :")
			   .append(" ,")
			   .append(CommFunc.splitToken(resp.getObj().getString("decKeyToken2")));
			
			//更新新的Tariff Index入库
			setMeterTariffIndex(rtuId, mpId, tariff_index_new);
			//else return "save Tariff Index failed!";
			//更新新的keychange相关参数
			setMeterKeyChangeInfo(rtuId, mpId, vending_key_reg_no_new, key_revision_no_new, key_expiry_no_new, tariff_index_new, 2, meterAddress, sgc_new);
			//else return "save Key Revision Number failed!";
			
			return sbf.toString();
		}
	}
	
	/**
	 * 向串口发送数据，并读取返回数据
	 * */
	public String stsCommuniate(String msg){
		//1、向队列中添加数据
		StsTaskQueue stsTaskQueue = StsTaskQueue.getInstance();		
		stsTaskQueue.addStsRequestQueue(msg);

		//2、向串口发送数据
		SerialOperation serialOperation = SerialOperation.getInstance();
		
		byte[] send_buf = StsEncryptor.makeFrame(msg);

		System.out.println("TX: " + new String(send_buf));
		serialOperation.send(send_buf);
		
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		
		//读取
		String response = stsTaskQueue.stsResponseQueue.get(0);
		stsTaskQueue.removeStsRequestQueue(msg);
		//暂定为0，当前测试应答挺快保证了应答队列中只有一个数据。实际环境中需要重点测试。
		stsTaskQueue.removeStsResponseQueue(0);
		
		return response;
	}
	
	/**
	 * 组织缴费结构体MsgTaskTokenPay
	 * @param payQuantity
	 * @param meterAddress
	 * @param rtuId
	 * @param mpId
	 * @param orgId
	 * @return
	 */
	public MsgTaskTokenPay getMsgTaskTokenPay(int payQuantity, String meterAddress, int rtuId, int mpId, Map<String, String> kmfMap){		
		//获取tariffIndex(费率方案yffratepara中的rateid)
		//int tariffIndex = getMeterTariffIndex(rtuId, mpId);
		
		JSONObject meterInfo = getMeterStsInfo(rtuId, mpId);
		
		
		//获取当前系统时间
		int ymd = CommFunc.nowDateInt();
		int hms = CommFunc.nowTimeInt();
		
		//测试用例，正式上线记住删除
		//ymd = 20040329;
		//hms = 300;
		
		String tokenTime = getPayTokenTime(rtuId, mpId, ymd, hms);
		
		//获取tokenId
		String tokenId = String.valueOf(StsFunc.getTid(WebConfig.sts_baseDate, tokenTime));
		
		MsgTaskTokenPay msgTaskTokenPay = new MsgTaskTokenPay(
				StsFunc.getPAN(meterAddress), 
				kmfMap.get("keyRegisterNO"), 
				kmfMap.get("supplyGroupCode"), 
				meterInfo.getInt("tariffIndex"), 
				CommFunc.objectToString(meterInfo.getInt("keyRevision")),//kmfMap.get("keyRevisionNumber"), 
				CommFunc.objectToString(meterInfo.getInt("ken")),//kmfMap.get("keyExpiryNumber"), 
				STSDef.STS_CLS0_SUBCLS_ELEC, 
				tokenId,
				payQuantity); 
		
		return msgTaskTokenPay;
	}
	
	/**
	 * 根据终端获取其KMF信息，并解析为Map格式数据
	 * @param rtuId
	 * @return
	 */
	private Map<String, String> getMeterKMF(int rtuId){
		//根据rtuId获取orgId
		int orgId = getOrgByRtuId(rtuId);
		OrgStsPara orgStsPara =	getKMFParam(CommFunc.objectToShort(orgId));
		String kmf = CommFunc.CheckString(orgStsPara.getKMF());
		
		if(kmf == null || kmf.isEmpty()) return null;
		Map<String, String> kmfMap = parseKMF(kmf);
		return kmfMap;
	}
	
	/**
	 * 根据rtuId获取该终端所属的orgId
	 * @return
	 */
	public short getOrgByRtuId(int rtuId){
		StringBuffer sql = new StringBuffer();
		sql.append("select cons.org_id AS orgId from rtupara as rtu, conspara as cons where rtu.cons_id = cons.id and rtu.id = ").append(rtuId);
		JDBCDao jDao = new JDBCDao(); 
		List<Map<String, Object>> list = jDao.result(sql.toString());
		if(list == null || list.isEmpty()){
			return 0;
		}
		
		Map<String, Object> map = list.get(0);
		return CommFunc.objectToShort(map.get("orgId"));
	}
	
	private JSONObject getMeterStsInfo(int rtuId, int mpId)
	{
		JSONObject ret = new JSONObject();
		
		JDBCDao jDao = new JDBCDao();
		StringBuffer sbf = new StringBuffer();
//		sbf.append("SELECT r.rateid FROM yffratepara AS r, mppay_para AS p WHERE r.id = p.feeproj_id AND p.rtu_id = ").append(rtuId).append(" AND p.mp_id = ").append(mpId);
		sbf.append("select mp.key_version, mt.ti,mt.ken from mppay_para as mp, meter_stspara as mt where mt.rtu_id=mp.rtu_id and mt.mp_id=mp.mp_id and mp.rtu_id=").append(rtuId).append(" and mp.mp_id=").append(mpId);
	
		List<Map<String, Object>> list = jDao.result(sbf.toString());
		if(list == null || list.isEmpty()){
			ret.put("tariffIndex", "0");
			ret.put("ken", "0");
			ret.put("keyRevision", "0");
			return ret;
		}
		
		Map<String, Object> map = list.get(0);
		
		ret.put("tariffIndex", CommFunc.CheckString(map.get("ti")));
		ret.put("ken", CommFunc.CheckString(map.get("ken")));
		ret.put("keyRevision", CommFunc.CheckString(map.get("key_version")));
		
		return ret;
	}
	
	private JSONObject getMeterStsChangeKeyInfo(int rtuId, int mpId)
	{
		JSONObject ret = new JSONObject();
		
		JDBCDao jDao = new JDBCDao();
		StringBuffer sbf = new StringBuffer();
//		sbf.append("SELECT r.rateid FROM yffratepara AS r, mppay_para AS p WHERE r.id = p.feeproj_id AND p.rtu_id = ").append(rtuId).append(" AND p.mp_id = ").append(mpId);
		sbf.append("select mp.key_version, mt.old_ti,mt.old_ken from mppay_para as mp, meter_stspara as mt where mt.rtu_id=mp.rtu_id and mt.mp_id=mp.mp_id and mp.rtu_id=").append(rtuId).append(" and mp.mp_id=").append(mpId);
	
		List<Map<String, Object>> list = jDao.result(sbf.toString());
		if(list == null || list.isEmpty()){
			ret.put("tariffIndex", "0");
			ret.put("ken", "0");
			ret.put("keyRevision", "0");
			return ret;
		}
		
		Map<String, Object> map = list.get(0);
		
		ret.put("tariffIndex", CommFunc.CheckString(map.get("old_ti")));
		ret.put("ken", CommFunc.CheckString(map.get("old_ken")));
		ret.put("keyRevision", CommFunc.CheckString(map.get("key_version")));
		
		return ret;
	}	
	/**
	 * 根据表通讯地址获取当前费率(赋值参数tariffIndex)
	 * @param meterAddress
	 * @return
	 */
	private int getMeterTariffIndex(int rtuId, int mpId){
		JDBCDao jDao = new JDBCDao();
		StringBuffer sbf = new StringBuffer();
//		sbf.append("SELECT r.rateid FROM yffratepara AS r, mppay_para AS p WHERE r.id = p.feeproj_id AND p.rtu_id = ").append(rtuId).append(" AND p.mp_id = ").append(mpId);
		sbf.append("select tariff_index from mppay_para as p where p.rtu_id=").append(rtuId).append(" and p.mp_id=").append(mpId);
	
		List<Map<String, Object>> list = jDao.result(sbf.toString());
		if(list == null || list.isEmpty()){
			return -1;
		}
		
		Map<String, Object> map = list.get(0);
		
		return CommFunc.objectToInt(map.get("tariff_index")); 
	}	

	/**
	 * 根据表通讯地址获取当前KEN(赋值参数KeyExpiryNumber)
	 * @param meterAddress
	 * @return
	 */
	private int getMeterKeyExpiryNumber(int rtuId, int mpId){
		JDBCDao jDao = new JDBCDao();
		StringBuffer sbf = new StringBuffer();
//		sbf.append("SELECT r.rateid FROM yffratepara AS r, mppay_para AS p WHERE r.id = p.feeproj_id AND p.rtu_id = ").append(rtuId).append(" AND p.mp_id = ").append(mpId);
		sbf.append("select ken from meter_stspara as p where p.rtu_id=").append(rtuId).append(" and p.mp_id=").append(mpId);
	
		List<Map<String, Object>> list = jDao.result(sbf.toString());
		if(list == null || list.isEmpty()){
			return -1;
		}
		
		Map<String, Object> map = list.get(0);
		
		return CommFunc.objectToInt(map.get("ken")); 
	}	
	
	/**
	 * 根据orgId获取所属厂家KMF
	 * @param orgId
	 * @return
	 */
	public OrgStsPara getKMFParam(short orgId){
		HibDao hib_dao = new HibDao();
		return (OrgStsPara) hib_dao.loadById(OrgStsPara.class, orgId);
	}
		
	/**
	 * 对KMF进行解析，获取相关厂家参数 
	 * @param kmf 厂家KMF
	 * @return
	 */
	private Map<String, String> parseKMF(String kmf){
		int minLength = 83;
		//长度最少83，83后边为补充的数据,无业务意义
		//对应字段长度
		//{4,2,2,1,1,1,2,16,16,8,6,8,6,1,3,6,15};
		if(kmf == null || kmf.isEmpty()) return null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("recordType", 			kmf.substring(0,4));
		map.put("keyFunction", 			kmf.substring(4,6));
		map.put("keyRegisterNO", 		kmf.substring(6,8));
		map.put("keyType", 				kmf.substring(8,9));
		map.put("keyParity", 			kmf.substring(9,10));
		map.put("keyEncryptionMethod", 	kmf.substring(10,11));
		map.put("parentRegisterNumber", kmf.substring(11,13));
		map.put("keyValue", 			kmf.substring(13,29));
		map.put("keyCheckDigits", 		kmf.substring(29,45));
		map.put("dateSent", 			kmf.substring(45,53));
		map.put("timeSent", 			kmf.substring(53,59));
		map.put("dateActive", 			kmf.substring(59,67));
		map.put("timeActive", 			kmf.substring(67,73));
		map.put("keyRevisionNumber", 	kmf.substring(73,74));
		map.put("keyExpiryNumber", 		kmf.substring(74,77));
		map.put("supplyGroupCode", 		kmf.substring(77,83));
		if(kmf.length() > minLength){
			map.put("supplyGroupName", 		kmf.substring(83,kmf.length()));
		}		
		return map;
	}
	
	/**
	 * 获取剩余电量,将16进制值根据算法转化为十进制整数
	 * @param remainHex
	 * @return
	 */
	private int getPoolRemain(String remainHex){
		int remainInt = Integer.parseInt(remainHex, 16);
		int e = (remainInt >>> 13) & 0x03;
		int m = remainInt & 0x3FFF;
		
		int t = 0;	//最终转换出来的钱数
		if(e == 0){
			t = pow(10, e) * m;
		}
		else{
			int t0 = pow(10, e) * m;
			int t1 = 0;
			int t2 = pow(2, 14);
			for(int i=1; i<=e; i++){
				t1 += (t2 * pow(10, i-1)); 
			}	
			t = t0 + t1;
		}
		return t;
	}
	
	/**
	 * 添加工具操作类存库
	 * @param rtuId
	 * @param mpId
	 * @param resNo
	 * @param resDesc
	 * @param opType
	 * @param token
	 */
	public void addToolRecordOper(int rtuId, short mpId, String resNo, 
			String resDesc, byte opType, String token){
		ToolRecord toolRecord = new ToolRecord(); 
		toolRecord.setRtuId(rtuId);
		toolRecord.setMpId(mpId);
		toolRecord.setResNo(resNo);
		toolRecord.setResDesc(resDesc);
		toolRecord.setOpType(opType);
		
		toolRecord.setWasteno(getToolOperWasteno());
		toolRecord.setRewasteno(token);
		toolRecord.setOpDate(CommFunc.nowDateInt());
		toolRecord.setOpTime(CommFunc.nowTimeInt());		
		toolRecord.setTableName("yddataben.dbo.tool" + CommFunc.nowDateInt()/10000); 
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		YffManDef yffman = (YffManDef)session.getAttribute(SDDef.SESSION_USERNAME);
		toolRecord.setOpMan(yffman.getName());
		
		toolRecord.setOpResult((byte)1);
		toolRecord.setVisibleFlag((byte)1);
		addToolRecordDB(toolRecord);		//不是特别重要的存库
	}
	
	/**
	 * 将操作设置存库
	 * @return
	 */
	private boolean addToolRecordDB(ToolRecord toolRecord){
		StringBuffer sbf = new StringBuffer(); 
		sbf.append("insert into ").append(toolRecord.getTableName()).append(" values(")
		.append(toolRecord.getRtuId()).append(",")
		.append(toolRecord.getMpId()).append(",")
		.append("'" + toolRecord.getResNo()).append("',")
		.append("'" + toolRecord.getResDesc()).append("',")
		.append("'" + toolRecord.getOpMan()).append("',")
		.append(toolRecord.getOpType()).append(",")
		.append(toolRecord.getOpDate()).append(",")
		.append(toolRecord.getOpTime()).append(",")
		.append("'" + toolRecord.getWasteno()).append("',")
		.append("'" + toolRecord.getRewasteno()).append("',")
		.append(toolRecord.getOpResult()).append(",")
		.append(toolRecord.getVisibleFlag()).append(",")
		.append(toolRecord.getUpdateFlag())
		.append(")");
		
		JDBCDao jDao = new JDBCDao();
		return jDao.executeUpdate(sbf.toString());
	}
	
	/**
	 * 获取操作设置存库的流水号
	 * */
	private String getToolOperWasteno(){
		Random rd = new Random(); 
		StringBuffer sbf = new StringBuffer();
		sbf.append("tool")
		   .append(String.format("%08d%06d%03d", CommFunc.nowDateInt(), CommFunc.nowTimeInt(), rd.nextInt(1000)));
		return sbf.toString();
	}
	
	//该方法只限于本算法计算(整数之间进行计算)
	private int pow(int m, int n){		
		int i = 0, result = 1;
		while(i<n){
			result = result * m;
			i++;
		}
		return result;
	}
	
	//获取该表计在当前分钟内是否有缴费记录
	private int getMeterYffRecord(int rtuId, int mpId, int ymd, int hms){
		JDBCDao jDao = new JDBCDao();
		StringBuffer sbf = new StringBuffer();
		sbf.append("select rtu_id, mp_id from yddataben.dbo.jyff").append(ymd/10000)
		   .append(" where rtu_id = ").append(rtuId)
		   .append(" and mp_id = ").append(mpId)
		   .append(" and op_date = ").append(ymd)
		   .append(" and op_time/100 = ").append(hms/100);		//判断是否在同一分钟内进行的操作
		
		List<Map<String, Object>> list = jDao.result(sbf.toString());
		if(list == null || list.isEmpty()){
			return 0;
		}
		else{
			return list.size();
		}
	}
	
	//获取tokenId的当前时间
	private String getPayTokenTime(int rtuId, int mpId, int ymd, int hms){ 
		int nums = getMeterYffRecord(rtuId, mpId, ymd, hms);
		//判断hms不是在00:01分范围之内，该范围为保留时间段
		if(hms > 99 && hms < 200){
			return CommFunc.computeByMinute(ymd, hms, 1);
		}
		else if(nums > 0){
			return CommFunc.computeByMinute(ymd, hms, nums);
		}
		else{
			return getFormatTMD(ymd, hms);
		}
	}
	
	/**
	 * 查询同一分钟内该终端是否存在tool操作记录
	 * */
	private int getToolOperRecord(int rtuId, int mpId, int ymd, int hms, int opType){
		JDBCDao jDao = new JDBCDao();
		StringBuffer sbf = new StringBuffer();
		sbf.append("select rtu_id, mp_id from yddataben.dbo.tool").append(ymd/10000)
		   .append(" where rtu_id = ").append(rtuId)
		   .append(" and mp_id = ").append(mpId)
		   .append(" and op_type = ").append(opType)
		   .append(" and op_date = ").append(ymd)
		   .append(" and op_time/100 = ").append(hms/100);		//判断是否在同一分钟内进行的操作
		
		List<Map<String, Object>> list = jDao.result(sbf.toString());
		if(list == null || list.isEmpty()){
			return 0;
		}
		else{
			return list.size();
		}
	}
	
	//获取tokenId的当前时间
	private String getClearCreditTime(int rtuId, int mpId, int ymd, int hms, int opType){ 
		//获取数据库中当前分钟是否存在操作记录
		int nums = getToolOperRecord(rtuId, mpId, ymd, hms, opType);
		//判断hms不是在00:01分范围之内，该范围为保留时间段
		if(hms > 99 && hms < 200){
			return CommFunc.computeByMinute(ymd, hms, 1);
		}
		else if(nums > 0){
			return CommFunc.computeByMinute(ymd, hms, nums);
		}
		else{
			return getFormatTMD(ymd, hms);
		}
	}
	
	/**
	 * 根据ymd, hms,获取标准的timestamp时间数据格式
	 * */
	private String getFormatTMD(int ymd, int hms){
		StringBuffer sbf = new StringBuffer();
		sbf.append(CommFunc.intToFormatDate(ymd, (byte)1)).append(" ")
		   .append(CommFunc.intToFormatTime(hms, (byte)1)); 
		return sbf.toString();
	}
	
	//当前系统时间,获取标准的timestamp时间数据格式
	private String getNowFormatTMD(){
		int ymd = CommFunc.nowDateInt();
		int hms = CommFunc.nowTimeInt();
		return getFormatTMD(ymd, hms);
	}
	

	/**
	 * 根据表通讯地址更新当前Tariff Index(赋值参数tariffIndex)
	 * 根据表测点编号更新当前KenChange 参数
	 * @param rtuId mpId
	 * @return
	 */
	private boolean setMeterTariffIndex(int rtuId, int mpId, int tariffIndex){
		JDBCDao jDao = new JDBCDao();
		StringBuffer sbf = new StringBuffer();
//		sbf.append("SELECT r.rateid FROM yffratepara AS r, mppay_para AS p WHERE r.id = p.feeproj_id AND p.rtu_id = ").append(rtuId).append(" AND p.mp_id = ").append(mpId);
//		sbf.append("select tariff_index from mppay_para as p where p.rtu_id=").append(rtuId).append(" and p.mp_id=").append(mpId);
		sbf.append("update mppay_para set tariff_index=").append(tariffIndex).append(" where rtu_id=").append(rtuId).append(" and mp_id=").append(mpId);
		return jDao.executeUpdate(sbf.toString());
	}	
	
	/**
	 * 根据表测点编号更新当前KenChange 参数
	 * @param rtuId mpId
	 * @return
	 */
	private boolean setMeterKeyChangeInfo(int rtuId, int mpId, int regno, int krn, int ken, int ti, int kt, String drn, String sgc){
		JDBCDao jDao = new JDBCDao();
		StringBuffer sbf = new StringBuffer();
		sbf.append("update mppay_para set key_version=").append(krn).append(", tariff_index=").append(ti).append(" where rtu_id=").append(rtuId).append(" and mp_id=").append(mpId);
		jDao.executeUpdate(sbf.toString());
		sbf.setLength(0);
		String str;
		str = "update meter_stspara set old_ken=" + ken + ", ken=" + ken + ",old_krn=" + krn + ",krn=" + krn + ",old_kt=" + kt + ",kt=" + kt +
			  ", old_ti=" + ti + ",ti=" + ti + ",old_regno=" + regno + ",regno=" + regno + ",old_drn='" + drn + "',drn=" + drn + ",old_sgc='" +sgc + "', sgc='" + sgc + "'" + " where rtu_id=" + rtuId + " and mp_id=" + mpId;
		sbf.append(str);
		jDao.executeUpdate(sbf.toString());
		return true;
	}	
	
	
	
	/**
	 * 根据KEN判断TID是否过期
	 * @param 
	 * @return
	 */
	private boolean isTidExpired(int ken, int tid)
	{
		return (tid - ken*65535) > 0;
	}
}
