/********************************************************************************************************
*                                        STS05V API Specification  										*
*																										*
*                           (c) Copyright 2016~,   Kelin Electric Co.,Ltd.								*
*                                           All Rights Reserved											*
*																										*
*	FileName	:	StsEncryptor.java																	*
*	Description	:	TSM250 Product Introduction:																		*
*					The  security module  is  a  compact,  tamperproof  device, which  provides message *
* 					authentication and  device  authentication. The module  includes  hardware  and  	*
* 					software  support  for  the  popular DES  and  STS  encryption  algorithms, as well *
* 					as a host of key management  functions.  Typical applications of  the module are 	*
* 					within electronic  funds  transfer equipment; here  it can be used  to 				*
*					ensure authenticity of the data transferred in and out of the equipment, as well as *
*					the authenticity of the equipment itself. 											*
*																										*
*	Author		:	ZhangXiangping																		*
*	Date		:	2016/11/01																			*
*																										*
*   No.         Date         Modifier        Description												*
*   -----------------------------------------------------------------------------------------------     *
*********************************************************************************************************/

package com.sts.common;

import com.kesd.util.MSG_RESP;

import net.sf.json.JSONObject;

//1.  Set retry-count to zero. 
//2.  Flush the receive buffer. Send the [command + CRC + CR] to the security module. 
//3.  Wait for the response. If there is a response within the allowed time (TIMEOUT), go to step 4.  
//Otherwise,  increment  the  retry  count.  If  the  retries  exceeds MAX_RETRIES,  return  a  device 
//failure. Terminate interface. 
//4.  If the response CRC is incorrect, send [GL?RR + CRC + CR] to the security module, and go to 
//step 3. 
//5.  If  the  response header matches  the command header  (but with a  '!'  instead of  the  '?'),  return 
//the response. 
//6.  Increment the retry count. If the retries exceeds MAX_RETRIES, return a device failure. Go to 
//step 2. 
// 
//Notes:  The  value  for  TIMEOUT  depends  on  the  security  module  command  being  executed. 
//MAX_RETRIES should be at least two. 


public class StsEncryptor1
{
	//return codes
	public static final int RC_SUCCESSFUL				= 0;
	public static final int RC_DEVICE_FAILURE			= 1;
	public static final int RC_FORMAT_ERROR				= 2;
	public static final int RC_COMMAND_TIMED_OUT		= 3;
	public static final int RC_KEY_NUMBER_ERROR			= 4;
	public static final int RC_KEY_TYPE_ERROR			= 5;
	public static final int RC_KEY_INTEGRITY_ERROR		= 6;
	public static final int RC_KEY_PARITY_ERROR			= 7;
	public static final int RC_REDEFINITION_FAILED		= 8;
	public static final int RC_INPUT_DISPLAY_FAILED		= 9;
	public static final int RC_KEYPAD_ENTRY_CANCELLED	= 10;
	public static final int RC_INVALID_FUNCTION_KEY_MASK= 11;
	public static final int RC_INVALID_AMOUNT_FORMAT	= 12;
	public static final int RC_READER_NOT_SUPPORTED		= 13;
	public static final int RC_CARD_NOT_READ			= 14;
	public static final int RC_MSG_CYCLE_BUF_VIOLATION	= 15;
	public static final int RC_INVALID_PIN				= 16;
	public static final int RC_DEA2_PUBLIC_KEY_ERROR	= 17;
	public static final int RC_INVALID_PASSWORD			= 18;
	public static final int RC_INVALID_REPRESENTION		= 19;
	public static final int RC_CHECKSUM_ERROR			= 20;
	public static final int RC_INVALID_REQUEST_HEADER	= 21;
	public static final int RC_MAX_KEY_COMPONENTS		= 22;
	public static final int RC_INVALID_DOMAIN			= 23;
	public static final int RC_KEY_TOKEN_ERROR			= 24;
	public static final int RC_WEAK_KEY_ERROR			= 25;
	public static final int RC_RSA_DES_KEYS_NOT_CLEARED	= 26;
	public static final int RC_PASSWORD_LOCKOUT			= 27;
	public static final int RC_PASSWORD_NOT_SET_ERROR	= 28;
	public static final int RC_PASSWORD_INTEGRITY_ERROR	= 29;
	public static final int RC_INVALID_STS_TOKEN		= 30;
	public static final int RC_INSUFFICIENT_CRESIT_BALANCE 	= 31;
	public static final int RC_CREDIT_UPDATE_BLK_FMT_ERROR 	= 32;
	public static final int RC_INVALID_SECURITY_MODULE_ID	= 33;
	
	public static final int RC_INVALID_CREDIT_UPDATE_SEQUENCE_NO	= 34;
	//The sequence number in the credit update block is incorrect.
	
	public static final int RC_CREDIT_OVERFLOW				= 35;
	//The maximum STS credit balance has been exceeded.
	
	public static final int RC_TARIFF_CREDIT_INTEGRITY_ERROR	= 37;
	//The tariff / credit has become corrupt.
	
	public static final int RC_NO_TARIFF_CREDIT_DATA			= 38;
	//No tariff / credit data exists.
	
	public static final int RC_MAX_CREDIT_DOMAINS_REACHED		= 39;
	//The maximum number of credit domains allowed for the module has been reached.
	
	public static final int RC_INVALID_DATE_ERROR				= 40;
	
	public static final int RC_PAM_CREDIT_UNDERFLOW				= 41;
	
	public static final int RC_PAM_TARIFF_EXPIRED				= 42;
	
	public static final int RC_SWITCHOVER_ERROR = 39;
	//There was an error implementing the requested switchover combination.
	
	public static final int RC_KEYPAD_ENTRY_TERMINATED = 50;
	
	public static final int RC_KEYPAD_ENTRY_CLEARED		= 51;
	
	public static final int RC_KEYPAD_ENTRY_ERROR = 52;
	
	public static final int RC_INVALID_TERMINAL	= 60;
	//The terminal number is invalid.
	
	public static final int RC_RSA_BLOCK_ERROR = 61;
	//The data in the RSA block is invalid.
	
	public static final int RC_BALANCE_ERROR	= 64;
	//The balance amount is incorrect.
	
	public static final int RC_TRANS_SEQUENCE_NUMBER_ERROR = 65;
	//The transaction sequence number is incorrect.
	
	public static final int RC_INVALID_CARD_TYPE = 66;
	//The card type is incorrect.
	
	public static final int RC_INVALID_OPTION = 67;
	//The option code is not supported.
	
	public static final int RC_RANDOM_NUMBER_ERROR 	= 68;
	//The random number is incorrect.
	
	
	public static final int RC_PROTOCOL_SEQUENCE_ERROR = 69;
	//The command has been executed out of sequence.
	
	public static final int RC_REVERSAL_ERROR = 70;
	//The transaction reversal could not be performed.
	
	public static final int RC_PIN_BLOCK_ERROR = 71;
	//The PIN block data is not in the correct format.
	
	public static final int RC_KEY_REGISTER_ERROR = 72;
	//There is some problem with a key register. Usually it means the register is empty of corrupt. This error code is not used for the standard key registers.
	
	public static final int RC_INVALID_AMOUNT = 73;
	//The amount is invalid.
	
	public static final int RC_PIN_ERROR = 74;
	//The PIN is invalid.
	
	public static final int RC_TOKEN_INTERFACE_ERROR	= 75;
	//The security module cannot communicate with the smart card (or other token).
	
	public static final int RC_TOKEN_FORMAT_ERROR = 76;
	//The format of the smart card (or other token) is incorrect.
	
	public static final int RC_AUTHENTICATION_ERROR  = 77;
	//The authentication function failed.
	
	public static final int RC_CHECK_DIGIT_ERROR = 78;
	//The check digits for the key are incorrect.
	
	public static final int RC_NO_CARD_PRESENT = 79;
	//No smart card is present.
	
	public static final int RC_INVALID_TOKEN = 80;
	//The token is invalid.
	
	public static final int RC_SIGNATURE_ERROR = 81;
	//The signature was not verified.
	
	public static final int RC_KEY_PROTOCOL_ERROR = 82;
	//The key protocol rules were broken.
	
	public static final int RC_PUBLIC_KEY_ALREADY_PRESENT = 83;
	//The Public Key component is already in key storage, or key storage in use
	
	public static final int RC_PUBLIC_KEY_NOT_LOADED_ERROR = 84;
	//The Public key failed to load
	
	
//	class MSG_RESP {
//		public int 	retcode = 0;
//		public JSONObject obj = new JSONObject();
//		public MSG_RESP() {
//			super();
//		}
//		
//	}
//	
//	unsigned int CRCof (const char *message, unsigned int len)
//	{
//		unsigned int i;
//		unsigned int crc;
//		unsigned char k;
//
//		crc = 0;
//
//		for (i=0; i<len; i++) { 
//			k = (unsigned char)(message[i]) ^ crc;
//			crc = (crc / 256) ^ (k*128) ^ (k*64);
//			if ((BitsSet(k) & 1) != 0)
//			crc ^= 0xC001;
//		}
//
//		return(crc);
//	}


	//CRC Computation
	public static long BitsSet(byte ch)
	{
		long n;
		n = 0; 
		while (ch != 0) {
			n += (ch & 0x0001);
			ch >>>= 1;
		}
		return n;
	}
	
	public static int CRCof(byte[] message, int len)
	{
		int i = 0;
		int crc = 0;
		byte k = 0;
		
		for (i = 0; i < len; i++) {
			k = (byte)(message[i] ^ crc);
			crc = (crc / 256)^(k*128) ^ (k*64);
			if ((BitsSet(k) & 0x0001) != 0)
				crc ^= 0xC001;
		}
		
		return (crc);
	}
	
	//GL!ER
	public static MSG_RESP api_Err_Resp(String strResp)
	{
		MSG_RESP resp = new MSG_RESP();
		resp.setRetcode(0);
		
		if (strResp.length() < 5) return resp;
		
		if (strResp.indexOf("GL!ER") >= 0) {
			//接收处理
			JSONObject obj = new JSONObject();			
			String respCode = strResp.substring(5);
			resp.setObj(obj);
			//resp.obj.put(obj);
		}
		else if (strResp.indexOf("SM!ER") >= 0) {
			return api_Err_Resp(strResp);
		}
		
		return resp;		
	}
	
	//GL?RS - Reset Device -- Resets the security module
	public static String api_ResetDevice_Req() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("GL?RS");
		return sb.toString();
	}

	public static String api_ResetDevice_Resp(String strResp)
	{
		if (strResp.contains("GL!RS"))
			return strResp.substring(strResp.indexOf("GL!RS") + 5);
		return "";
	}
	
	
	//GL?RR - Resend Last Response
	public static String api_ResendResp_Req()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("GL?RR");
		return sb.toString();
	}
	
	//GL?PS - Get Status - NOT SUPPORTED
	//GL?EC - Echo Data 
	public static String api_EchoData_Req(int timeout, String echodata)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("GL?EC").append(String.format("%02d", timeout)).append(String.format("%03d", echodata.length())).append(echodata);
		return sb.toString();
	}
	
	public static int api_EchoData_Resp(String strResp, int respCode, String data)
	{
		if (strResp.length() < 5) return 0;
		
		respCode = Integer.parseInt(strResp.substring(0, 2));
		int datalen = Integer.parseInt(strResp.substring(2, 5));
		data = strResp.substring(5);
		if (data.length() != datalen) return STSDef.STS_FAIL;
		
		return STSDef.STS_SUCC;
	}
	
	//Miscellaneous Commands
	//SM?ID - Get Identification
	public static String api_GetIden_Req()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SM?ID");
		return sb.toString();
	}
	
	public static MSG_RESP api_GetIden_Resp(String strResp)
	{
		MSG_RESP resp = new MSG_RESP();
		resp.setRetcode(0);
		
		if (strResp.length() < 5) return resp;
		
		if (strResp.indexOf("SM!ID") >= 0) {
			//接收处理
			JSONObject obj = new JSONObject();			
			String respCode = strResp.substring(5, 7);
			String devId = strResp.substring(7, 15);
			String firmVersion = strResp.substring(15, 23);
			String dakCheck = strResp.substring(23);
			obj.put("respCode", respCode);
			obj.put("devId", devId);
			obj.put("firmVersion", firmVersion);
			obj.put("dakCheck", dakCheck);
			resp.setObj(obj);
			//resp.obj.put(obj);
		}
		else if (strResp.indexOf("SM!ER") >= 0) {
			return api_Err_Resp(strResp);
		}
		//respCode = Integer.parseInt(strResp.substring(0, 2));
		//int datalen = Integer.parseInt(strResp.substring(2, 5));
		//data = strResp.substring(5);
		//if (data.length() != datalen) return STSDef.STS_FAIL;
		
		return resp;
		//return STSDef.STS_SUCC;
	}
	
//	Initial Key Management 
	public static String api_InitialKey_Req(int key_reg_num, int key_type, int key_parity, String key_component)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SM?IK")
		.append(String.format("%02d", key_reg_num))
		.append(String.format("%c", key_type))
		.append(String.format("%c", key_parity))
		.append(key_component);
		
		return sb.toString();
	}
	
	//SM?LK - Load Single-Length DEA-1 Key
	public static String api_LoadKey_Req(int key_reg_num, char key_type, char key_parity, int parent_key_reg_num, char encryption_method, String encrypted_key)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SM?LK")
		.append(String.format("%02d", key_reg_num))
		.append(String.format("%c", key_type))
		.append(String.format("%c", key_parity))
		.append(String.format("%02d", parent_key_reg_num))
		.append(String.format("%c", encryption_method))
		.append(encrypted_key);
		
		return sb.toString();
	}
	
	//SM?GT – Generate Key Change Token 
	public static String api_GenKeyChangeToken_Req(int vending_key_reg_no, int dispenser_key_reg_no, String dispenser_pan, String supply_group_code, int tariff_index, char key_rev_no, int key_expiry_no)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SM?GT")
		.append(String.format("%02d", vending_key_reg_no))
		.append(String.format("%02d", dispenser_key_reg_no))
		.append(dispenser_pan)
		.append(supply_group_code)
		.append(String.format("%02d", tariff_index))
		.append(String.valueOf(key_rev_no))
		.append(String.format("%02X", key_expiry_no));
		
		return sb.toString();
	}
	
	public static MSG_RESP api_GenKeyChangeToken_Resp(String strResp)
	{
		MSG_RESP resp = new MSG_RESP();
		resp.setRetcode(STSDef.STS_FAIL);
		
		if (strResp.length() < 5) return resp;
		
		if (strResp.indexOf("SM!GT") >= 0) {
			//接收处理
			JSONObject obj = new JSONObject();			
			String respCode = strResp.substring(5, 7);
			String token1 = strResp.substring(41, 61);
			String token2 = strResp.substring(61, 81);

			obj.put("respCode", respCode);
			obj.put("token1", token1);
			obj.put("token2", token2);
			resp.setObj(obj);
			//resp.obj.put(obj);
		}
		else if (strResp.indexOf("SM!ER") >= 0) {
			return api_Err_Resp(strResp);
		}
		
		resp.setRetcode(STSDef.STS_SUCC);
		return resp;
	}
	
	//SM?TK - STS Change Key 
	public static String api_Change_Key_Req(String dispenser_pan, int vending_key_reg_no_current, int vending_key_reg_no_new, String sgc_current, String sgc_new, int tariff_index_current, int tariff_index_new, byte key_revision_no_current, byte key_revision_no_new, int key_expiry_no_current, int key_expiry_no_new)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SM?TK")
		.append(dispenser_pan)
		.append(String.format("%02d", vending_key_reg_no_current))
		.append(String.format("%02d", vending_key_reg_no_new))
		.append(sgc_current)
		.append(sgc_new)
		.append(String.format("%02d", tariff_index_current))
		.append(String.format("%02d", tariff_index_new))
		.append(String.format("%d", key_revision_no_current))
		.append(String.format("%d", key_revision_no_new))
		.append(String.format("%02d", key_expiry_no_current))
		.append(String.format("%02d", key_expiry_no_new));
		
		return sb.toString();
	}
	
	
	//SM?TC – Encrypt Credit Function Token
	public static String api_Encrypt_Credit_Token_Req(String dispenser_pan, byte vending_supply_key_reg_no, String sgc, int tariff_index, byte key_revision_number, int key_expiry_number, int credit_type, String token_id, String transfer_amount/*, int algorithm_type, int token_tech*/)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SM?TC")
		.append(dispenser_pan)
		.append(String.format("%02d", vending_supply_key_reg_no))
		.append(sgc)
		.append(String.format("%02d", tariff_index))
		.append(String.format("%d", key_revision_number))
		.append(String.format("%02d", key_expiry_number))
		.append(String.format("%02d", credit_type))
		.append(token_id)
		.append(transfer_amount);
		
		return sb.toString();
	}
}