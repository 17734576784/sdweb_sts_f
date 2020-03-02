/********************************************************************************************************
*                                        sdweb STS Ver2.0												*
*																										*
*                           (c) Copyright 2015~,   Kelin Electric Co.,Ltd.								*
*                                           All Rights Reserved											*
*																										*
*	FileName	:	StsProt.java																	    *
*	Description	:	Standard transfer specification														*
*					Electricity metering – Payment systems –  Part 41										*					*
*																										*
*	Author		:	ZhangXiangping																		*
*	Date		:	2015/08/01																			*
*																										*
*   No.         Date         Modifier        Description												*
*   -----------------------------------------------------------------------------------------------     *
*********************************************************************************************************/
package com.sts.common;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

import com.kesd.common.CommFunc;
import com.kesd.common.WebConfig;
import com.sts.SoftEncryptedToken;
import com.sts.rsa.PrivateKeyReader;

import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.math.*;
import java.net.URLDecoder;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.Math;
import  java.text.DecimalFormat;

public class StsAlgo
{
	private final static byte SUBSTITUTION_TABLE1[] = {		//替换表1
		0x0e,0x0a,0x07,0x09,0x0c,0x03,0x02,0x05,
	    0x0d,0x00,0x0f,0x01,0x04,0x08,0x06,0x0b
	};
	
	private final static byte SUBSTITUTION_TABLE2[] = {		//替换表2
		0x0c,0x08,0x02,0x0d,0x07,0x06,0x01,0x03,
	    0x0b,0x05,0x09,0x0f,0x00,0x04,0x0a,0x0e
	};
	
	private final static byte SUBSTITUTION_TABLE3[] = {		//替换表3
		0x0c,0x06,0x02,0x07,0x0d,0x09,0x05,0x04,
	    0x01,0x0a,0x0e,0x08,0x00,0x03,0x0f,0x0b
	};
	private final static byte SUBSTITUTION_TABLE4[] = {		//替换表4
		0x09,0x0b,0x06,0x05,0x0c,0x07,0x0e,0x02,
	    0x0d,0x03,0x01,0x0f,0x04,0x08,0x00,0x0a
	};
	
	private final static byte PERMUTATION_TABLE3[] 	= {		//加密时的置换表
		0x37,0x2a,0x0a,0x12,0x18,0x15,0x2c,0x23,
		0x02,0x16,0x38,0x2b,0x1b,0x3a,0x09,0x32,
		0x06,0x24,0x0c,0x3d,0x25,0x26,0x35,0x10,
		0x3e,0x03,0x07,0x04,0x20,0x14,0x3f,0x19,
		0x33,0x34,0x36,0x21,0x31,0x13,0x2e,0x1d,
		0x30,0x1f,0x17,0x1e,0x29,0x1c,0x0d,0x05,
		0x28,0x3c,0x27,0x0b,0x0f,0x11,0x01,0x00,
		0x39,0x22,0x3b,0x08,0x2f,0x0e,0x2d,0x1a,
	};
	private final static byte PERMUTATION_TABLE4[] 	= {		//解密时的置换表
		0x37,0x36,0x08,0x19,0x1b,0x2f,0x10,0x1a,
		0x3b,0x0e,0x02,0x33,0x12,0x2e,0x3d,0x34,
		0x17,0x35,0x03,0x25,0x1d,0x05,0x09,0x2a,
		0x04,0x1f,0x3f,0x0c,0x2d,0x27,0x2b,0x29,
		0x1c,0x23,0x39,0x07,0x11,0x14,0x15,0x32,
		0x30,0x2c,0x01,0x0b,0x06,0x3e,0x26,0x3c,
		0x28,0x24,0x0f,0x20,0x21,0x16,0x22,0x00,
		0x0a,0x38,0x0d,0x3a,0x31,0x13,0x18,0x1e,
	};
	
	/**
	 * DES 加解密算法
	 * @param data  加解密数据
	 * @param key   秘钥
	 * @param mode  模式
	 * @return      加解密结果
	 */
	public static byte[] desCryt(byte[] data, byte[] key, int mode){
		byte[] result = null ;
		try {
			SecureRandom sr = new SecureRandom();  
			SecretKeyFactory keyFactory;
			DESKeySpec dks = new DESKeySpec(key);
			keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretkey = keyFactory.generateSecret(dks); 
			//创建Cipher对象
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");  
			//初始化Cipher对象  
			cipher.init(mode, secretkey, sr);  
			//加解密
			result = cipher.doFinal(data); 
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} 
		
		return result;
	} 
	

	private static void stsSubstitution(byte[] blockdata, byte[] keydata)
	{
		int i, mm, nn;
		for (i = 0; i < 8; i++) {
			mm = blockdata[i] & 0x0F;
			nn = i;
			
			if ((keydata[nn] & 0x08) == 0x08) 	blockdata[i] = (byte)((blockdata[i] & 0xF0) | SUBSTITUTION_TABLE2[mm]);
			else blockdata[i] = (byte)((blockdata[i] & 0xF0) | SUBSTITUTION_TABLE1[mm]);
			
			mm = (blockdata[i] & 0xF0) >> 4;
			if ((keydata[nn] & 0x80) == 0x80)	blockdata[i] = (byte)((blockdata[i] & 0x0F) | (SUBSTITUTION_TABLE2[mm]<<4));
			else blockdata[i] = (byte)((blockdata[i] & 0x0F) | (SUBSTITUTION_TABLE1[mm]<<4));
			
		}
	}
	
	private static void stsSubstConverse(byte[] blockdata, byte[] keydata)
	{
		int i, mm, nn;
		for (i = 0; i < 8; i++) {
			mm = blockdata[i] & 0x0F;
			nn = i;
			
			if ((keydata[nn] & 0x01) == 0x01) 	blockdata[i] = (byte)((blockdata[i] & 0xF0) | SUBSTITUTION_TABLE4[mm]);
			else blockdata[i] = (byte)((blockdata[i] & 0xF0) | SUBSTITUTION_TABLE3[mm]);
			
			mm = (blockdata[i] & 0xF0) >> 4;
			if ((keydata[nn] & 0x10) == 0x10)	blockdata[i] = (byte)((blockdata[i] & 0x0F) | (SUBSTITUTION_TABLE4[mm]<<4));
			else blockdata[i] = (byte)((blockdata[i] & 0x0F) | (SUBSTITUTION_TABLE3[mm]<<4));
			
		}
	}
	
	private static void stsPermutation(byte[] blockdata, int type/*0:加密   1:解密*/)
	{
		int i,j,mm,nn,temp1;
		byte [] temp = new byte[8];
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				if (type == 0) temp1 = PERMUTATION_TABLE3[i*8+j];
				else temp1 = PERMUTATION_TABLE4[i*8+j];
				
				mm = temp1/8;
				nn = temp1%8;
				
				if ((blockdata[i] & (1<<j)) > 0) temp[mm] |= (1<<nn);
			}
		}
		
		System.arraycopy(temp, 0, blockdata, 0, 8);
	}
	
	private static void stsLeftRotate(byte[] keydata)
	{
		byte [] temp = new byte[8];
		for (int i = 0; i < 8; i++) {
			if (i == 0) temp[i] = (byte)(((keydata[i]<<1) & 0xFF) | ((keydata[7]>>>7) & 0x01));
			else temp[i] = (byte)(((keydata[i]<<1) & 0xFF) | ((keydata[i-1]>>>7) & 0x01));
		}
		
		System.arraycopy(temp, 0, keydata, 0, 8);
	}
	
	private static void stsRightRotate(byte[] keydata) 
	{
		byte [] temp = new byte[8];
		for (int i = 0; i< 8; i++) {
			if (i == 7) temp[i] = (byte)(((keydata[i]>>>1) & 0x7F) | ((keydata[0]<<7) & 0xFF));
			else temp[i] = (byte)(((keydata[i]>>>1) & 0x7F) | ((keydata[i+1]<<7) & 0xFF));
		}
		
		System.arraycopy(temp, 0, keydata, 0, 8);
	}
	
	private static void stsConversOrder(byte[] keydata)
	{
		byte b = 0;
		for (int i = 0; i < keydata.length/2; i++) {
			b = keydata[i];
			keydata[i] = keydata[keydata.length-i-1];
			keydata[keydata.length-i-1] = b;
		}			
	}
	
	//取反
	public static void stsComplement(byte[] blockdata)
	{
		for (int i = 0; i < blockdata.length; i++) {
			blockdata[i] = (byte)~blockdata[i];
		}
	}
	
	//转圈右移12位
	public static void stsRightRotate12(byte[] ar)
	{
		byte b1=0,b2=0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < ar.length; j++) {
				if (j == 0) b1 = (byte)(ar[ar.length-1] & 0x0F);
				b2 = (byte)(ar[j] & 0x0F);
				ar[j] = (byte)(((ar[j]>>4) & 0x0F) | (b1<<4));
				b1 = b2;
			}
		}
	}
	
	public static String stsEncrypt_EA07(byte[] blockdata, byte[] keydata)
	{
		StsAlgo.stsComplement(keydata);		//按位取反
		StsAlgo.stsRightRotate12(keydata);	//右移12位
		stsConversOrder(keydata);			//高低字节逆序
		stsConversOrder(blockdata);
		
		for (int i = 0; i < 16; i++) {
			stsSubstitution(blockdata, keydata);
			//stsConversOrder(blockdata);
			stsPermutation(blockdata, 0);
			stsLeftRotate(keydata);
		}
		
		stsConversOrder(blockdata);
		return StsFunc.bytes2HexString(blockdata, blockdata.length);
	}
	
	public static String stsDecrypt_EA07(byte[] blockdata, byte[] keydata)
	{
		for (int i = 0; i < 16; i++) {
			stsPermutation(blockdata, 1);
			stsSubstConverse(blockdata, keydata);
			stsRightRotate(keydata);
		}
		
		return StsFunc.bytes2HexString(blockdata, blockdata.length);
	}
	
	
	public static int stsMakeCRC(byte[] blockdata) {
		int i, j, current;
		int CRC = 0xFFFF;
		for (i = 0; i < blockdata.length; i++) {
			current = blockdata[i];
			for (j = 0; j < 8; j++) {
				if (((CRC ^ current) & 0x0001) == 0x0001) CRC = (CRC>>1) ^ 0xa001;
				else CRC >>= 1;

				current >>= 1;
			}
		}
		
		i = (CRC>>8) & 0x00FF;
		CRC = (CRC<<8) | i;
		
		return CRC & 0x0000FFFF;
	}
		
	public static int stsMakeCRC(byte[] bytes, int len) {
		int i, j, current;
		int CRC = 0xFFFF;
		for (i = 0; i < len; i++) {
			current = bytes[i];
			for (j = 0; j < 8; j++) {
				if (((CRC ^ current) & 0x0001) == 0x0001) CRC = (CRC>>1) ^ 0xa001;
				else CRC >>= 1;

				current >>= 1;
			}
		}
		
		i = (CRC>>8) & 0x00FF;
		CRC = (CRC<<8) | i;
		
		return CRC & 0x0000FFFF;
	}
	

    public   String  doubleOutPut(double  v,Integer num){ 
        if ( v == Double.valueOf(v).intValue()){ 
            return  Double.valueOf(v).intValue() +  "" ; 
        }else { 
            BigDecimal b =  new  BigDecimal(Double.toString(v)); 
            return  b.setScale(num,BigDecimal.ROUND_HALF_UP ).toString(); 
        } 
    } 
    public   String  roundNumber(double  v,int  num){ 
        String  fmtString =  "0000000000000000" ;  //16bit 
        fmtString = num>0 ?  "0."   + fmtString.substring(0,num):"0" ; 
        DecimalFormat dFormat =  new  DecimalFormat(fmtString); 
        return  dFormat.format(v); 
    } 

    public static String DES(String vk, byte KT, int SGC, byte TI, byte KRN, String PB){
		String token = "";		
		String cb = SoftEncryptedToken.get_ControlBlock(KT, SGC, TI, KRN);
		String pb = PB;//SoftEncryptedToken.get_PANBlock(IIN, DRN);

		byte [] b1 = StsFunc.hexStringToByte(cb.toUpperCase());
		byte [] b2 = StsFunc.hexStringToByte(pb.toUpperCase());
  		//加解密模式
		int mode = Cipher.ENCRYPT_MODE;			//Cipher.DECRYPT_MODE
		//被加解密byte数组16进制字符串
		String dataHexString = StsFunc.getXorBlock(b1, b2, b1.length);
		//秘钥byte数组16进制字符串
		//String keyHexString = "abababababababab";
		//		String keyHexString = Descrypt(vk);
        
		try{
			String real_basepath = new File(new File(WebConfig.class.getResource("/").getPath()).getParent()).getParent();
			try {
				real_basepath = URLDecoder.decode(real_basepath, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			String privateKeyFilePath = real_basepath + "\\WEB-INF\\classes\\privateKeyFile";
			//将vk进行解密 start
			Cipher cipher = Cipher.getInstance("RSA");        
	        //RSAPublicKey pubKey = (RSAPublicKey) PublicKeyReader.get("f:/publicKeyFile");
			
	        //RSAPrivateKey privKey = (RSAPrivateKey) PrivateKeyReader.get("f:/privateKeyFile");
			RSAPrivateKey privKey = (RSAPrivateKey) PrivateKeyReader.get(privateKeyFilePath);
	        
	        cipher.init(Cipher.DECRYPT_MODE, privKey); 
	        byte[] plainText = cipher.doFinal(StsFunc.hexStringToByte(vk));
	        //end
	        //加解密数据 start
			byte[] data = StsFunc.hexStringToByte(dataHexString.toUpperCase());
			byte[] key = StsFunc.hexStringToByte(new String(plainText).toUpperCase());
			byte[] result = desCryt(data, key, mode);
			token = StsFunc.bytes2HexString(result, result.length);
			//end
		}catch(Exception e){
			e.printStackTrace();
		}	
		return CommFunc.splitToken(token);
		
	}
//	 public static void main(String[] args)
//	 {
//		String cb = "2123456011FFFFFF";
//		String pb = "0072700001234501";
//
//		byte [] b1 = StsFunc.hexStringToByte(cb.toUpperCase());
//		byte [] b2 = StsFunc.hexStringToByte(pb.toUpperCase());
//  		//加解密模式
//		int mode = Cipher.ENCRYPT_MODE;			//Cipher.DECRYPT_MODE
//		//被加解密byte数组16进制字符串
//		String dataHexString = StsFunc.get_XorBlock(b1, b2, b1.length);
//		//秘钥byte数组16进制字符串
//		String keyHexString = "abababababababab";
//		byte[] data = StsFunc.hexStringToByte(dataHexString.toUpperCase());
//		byte[] key = StsFunc.hexStringToByte(keyHexString.toUpperCase());
//		byte[] result = desCryt(data, key, mode);
//		//打印结果
////		System.out.println("结果："+StsFunc.Bytes2HexString(result, result.length));
//		
//		String token = StsFunc.Bytes2HexString(result, result.length);
//		
//
//	}
	
	
	 public static void main(String[] args)
	 {
		 
//		String pb = StsProt.get_PANBlock("600727", "3000002");
//		System.out.println("pb  :  "+pb);
//		System.out.println("结果："+DES("2123456011FFFFFF","0072700001234501"));
//		String cb = "2123456011FFFFFF";
//		String pb = "0072700001234501";
//		byte [] b1 = StsFunc.hexStringToByte(cb.toUpperCase());
//		byte [] b2 = StsFunc.hexStringToByte(pb.toUpperCase());
//		String xordata = StsFunc.get_XorBlock(b1, b2, b1.length);
//		System.out.println("XOR Result: " + xordata);
//		//加解密模式
//		int mode = Cipher.ENCRYPT_MODE;			//Cipher.DECRYPT_MODE
//		//被加解密byte数组16进制字符串
//		String dataHexString = xordata;
//		//秘钥byte数组16进制字符串
//		String keyHexString = "abababababababab";
//		byte[] data = StsFunc.hexStringToByte(dataHexString.toUpperCase());
//		byte[] key = StsFunc.hexStringToByte(keyHexString.toUpperCase());
//		byte[] result = desCryt(data, key, mode);
//		//打印结果
//		System.out.println("结果："+StsFunc.Bytes2HexString(result, result.length));
		
		
		//STA算法测试
		byte[] datavalue = {-106,0x01,0x10,0x27,0x00,0x00,0x00,0x0a};
		byte[] keyvalue = {0x9f-256,0xaf-256,0xbf-256,0xcf-256,0xdf-256,0xef-256,0x7f,0x8f-256};
		byte[] keyvalue1 = {0x08,0x07,0x06,0x05,0x04,0x03,0x02,0x01};
		
		//加密
		String enstr = stsEncrypt_EA07(datavalue, keyvalue);
		System.out.println("加密结果字符串：" + enstr);
		
		//解密
		datavalue = StsFunc.hexStringToByte(enstr);
		String destr = stsDecrypt_EA07(datavalue, keyvalue1);
		System.out.println("解密结果字符串：" + destr);
		
		//缴费测试
		String STR_FORMAT = "00000000000000000000";



		String str1 = "2CDFEA99A02ECE643";
		//long l = Long.parseLong(str1, 16);
		BigInteger a=new BigInteger(str1,16);
		DecimalFormat df = new DecimalFormat(STR_FORMAT);
	    String abc = df.format(a);
	    System.out.println("ABC is " + abc);
	    
		String t1  = abc;
		//t1.format("%20s", "11224454545");
		System.out.println("TOKEN IS " + t1.substring(0, 4) + " " + t1.substring(4, 8) + " " + t1.substring(8, 12) + " " + t1.substring(12,16) + " " + t1.substring(16,20));

		System.out.println("a is :" + a );
//		
		BigDecimal bd = new BigDecimal(a);
		BigDecimal bmax = new BigDecimal(Long.MAX_VALUE);
		BigDecimal bmin = new BigDecimal(Long.MIN_VALUE);
		if (a.doubleValue() > 0) {
			bd.add(bmax);
			bd.subtract(bmin);
		}
		System.out.println("BD:" + bd + "BSTRING: " + bd.toString());
//		if (l > 0) 
		byte[] bbb = StsFunc.hexStringToByte(str1);
		double d = 0;
		int bb = 0;
		for (int i = 0; i < 8; i++) {
			bb = (bbb[i] &0xFF);
			d += bb*Math.pow(0x100, 8-i-1);
		} 
		StsAlgo format =  new  StsAlgo();
		System.out.println(format.doubleOutPut(12.345, 2));    
		System.out.println(format.roundNumber(12.335, 2)); 
		System.out.println(format.doubleOutPut(d, 4));

		enstr = stsEncrypt_EA07(StsFunc.hexStringToByte(str1), keyvalue);
	//	BigDecimal bd = new java.math.BigDecimal(enstr, 16);
		
		//CRC测试
		String strCrc = "00004A2D900FF2";
		byte [] ca = StsFunc.hexStringToByte(strCrc);
		int crc = stsMakeCRC(ca);
		System.out.println("String is " + strCrc + ", CRC is " + String.format("%04X", crc));
	 }
}