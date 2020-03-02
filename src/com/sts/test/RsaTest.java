package com.sts.test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.interfaces.RSAPublicKey;
import java.security.interfaces.RSAPrivateKey;

import javax.crypto.Cipher;

import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.common.WebConfig;
import com.sts.rsa.PrivateKeyReader;
import com.sts.rsa.PublicKeyReader;

public class RsaTest {

	//rsa 公钥加密成密文
	public static String encryptByPublicKey(String data, String pubkeyFile) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA");
		try {
			RSAPublicKey pubKey = (RSAPublicKey) PublicKeyReader.get(pubkeyFile);
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] plainText = cipher.doFinal(data.getBytes());
			return new String(CommFunc.bytesToHexString(plainText));
		} catch (Exception e) {
			return "";
		}
	}
	
	//rsa 私钥解密成明文
	public static String decryptByPrivateKey(String data, String privateKeyFile) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");	
		try{		
			RSAPrivateKey privKey = (RSAPrivateKey) PrivateKeyReader.get(privateKeyFile);
			cipher.init(Cipher.DECRYPT_MODE, privKey); 
			byte[] plainText = cipher.doFinal(CommFunc.hexStringToBytes(data));
			return new String(plainText);
		}catch(Exception e){
			return "";
		}
	}
	
	public static void main(String[] args)throws Exception  {
		String real_basepath = new File(new File(WebConfig.class.getResource(
		"/").getPath()).getParent()).getParent();

		String rtnCode = "";
		try{
			try {
				real_basepath = URLDecoder.decode(real_basepath, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			String publicKeyFilePath  = real_basepath
					+ "\\WEB-INF\\classes\\publicRegSubCodeFile";
			
			String privateKeyFilePath = real_basepath
					+ "\\WEB-INF\\classes\\privateRegSubCodeFile";
			
			String str = "SBEEC_2020-12-31";
			str = "NigeriaGroup_2020-12-31";
			str = "Emcompassing_2025-12-31";
			str = "SBEEC_2030-12-31";
			str = "Lagos_2020-12-31";
			str = "Oniru Estate_2020-12-01";
			
			rtnCode = encryptByPublicKey(str, publicKeyFilePath);
			rtnCode = decryptByPrivateKey(rtnCode, privateKeyFilePath);
			
			str = "abababababababab";
			//rtnCode = "462522a90a42da4b0625765ca28d32441b01fe2067dcc9c0124540f4a821e152a352b56bb6453d9be31773a93b5925598f1c9528b05835a668334a27b1575ad908249cfda26be4360939124b3d4ba7b0829364a75dceb45b9c2743ad36dc952a54334a4eabfc753955c6a9c111e9504d460cea2cbf40f638e76b7110fa5f94fc";
			//str = "ABCDEF9659000100";
			str = "EC2019ABCD000001";
			str = "F0A424B510CD1249";
			publicKeyFilePath  = real_basepath
			+ "\\WEB-INF\\classes\\publicKeyFile";
	
			privateKeyFilePath = real_basepath
			+ "\\WEB-INF\\classes\\privateKeyFile";
			rtnCode = encryptByPublicKey(str, publicKeyFilePath);
			rtnCode = decryptByPrivateKey(rtnCode, privateKeyFilePath);
		}catch(Exception e){
			return;
		}
		
		return;
	}
}
