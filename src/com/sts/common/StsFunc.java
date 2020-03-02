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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Math;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

import com.kesd.common.CommFunc;

public class StsFunc
{
	private static final String TOKEN_STR_FORMAT = "00000000000000000000";
	public static String TOKEN_DATE_FORMAT = "yyyy-MM-dd";
	public static String TOKEN_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";



	public static String bytes2HexString(byte[] b, int len) {
		String ret = ""; 
		len = (b.length < len) ? b.length:len;
		
		for (int i = 0; i < len; i++) { 
			String hex = Integer.toHexString(b[i] & 0xFF); 
			if (hex.length() == 1) { 
				hex = '0' + hex; 
			} 
			ret += hex.toUpperCase(); 
		} 

		return ret; 
	}

	public static String bytes2HexString(byte[] b, int begin, int len) {
		String ret = "";
		
		if (b.length < begin) return ret;
		if (b.length < begin +len) len = b.length - begin;

		for (int i = 0; i < len; i++) { 
			String hex = Integer.toHexString(b[begin + i] & 0xFF); 
			if (hex.length() == 1) { 
				hex = '0' + hex; 
			} 
			ret += hex.toUpperCase(); 
		} 
		
		return ret; 
	}

	private static byte toByte(char c) {
	    byte b = (byte) "0123456789ABCDEF".indexOf(c);   
	    return b;                                        
	}                                                    

	public static byte[] hexStringToByte(String hex) {
	    int len = (hex.length() / 2);                                             
	    byte[] result = new byte[len];                                            
	    char[] achar = hex.toUpperCase().toCharArray();                                         
	    for (int i = 0; i < len; i++) {                                           
	     int pos = i * 2;                                                         
	     result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));   
	    }                                                                         
	    return result;                                                            
	}
	
	public static byte[] hexStringToByte(String hex, int formlen) {
		if (hex.length() % 2 != 0)  hex = '0' + hex;		//
		
		if (hex.length() > formlen) 	hex = hex.toUpperCase().substring(hex.length() - formlen, formlen);
		else if (hex.length() < formlen) {
			while (hex.length() < formlen) {
				hex = "00" + hex; 
			}
		}
			
	    int len = (hex.length() / 2);                                             
	    byte[] result = new byte[len];                                            
	    char[] achar = hex.toCharArray();                                         
	    for (int i = 0; i < len; i++) {                                           
	     int pos = i * 2;                                                         
	     result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));   
	    }                                                                         
	    return result;                                                            
	}
	
	public static String hexStringToToken(String hex)
	{
		BigInteger a=new BigInteger(hex,16);
		DecimalFormat df = new DecimalFormat(TOKEN_STR_FORMAT);

		StringBuffer sb = new StringBuffer();
		sb.append(df.format(a));
		
		return sb.toString();		
	    //System.out.println("TOKEN IS " + sb.substring(0, 4) + " " + sb.substring(4, 8) + " " + sb.substring(8, 12) + " " + sb.substring(12,16) + " " + sb.substring(16,20));	
	}
	

	
	public static int setIntval(byte[] para, int plen, int val, int len)
	{
		if (len == 3) {
			para[plen++] = (byte)((val>>16)& 0xff);
			para[plen++] = (byte)((val>>8) & 0xff);
			para[plen++] = (byte)(val	   & 0xff);
		}
		else if (len == 2) {
			para[plen++] = (byte)((val>>8) & 0xff);
			para[plen++] = (byte)(val	   & 0xff);

		}
		else if (len == 1){	
			para[plen++] = (byte)(val	   & 0xff);
		}
		else {
			para[plen++] = (byte)((val>>24)& 0xff);
			para[plen++] = (byte)((val>>16)& 0xff);
			para[plen++] = (byte)((val>>8) & 0xff);
			para[plen++] = (byte)(val	   & 0xff);
		}

		return plen;
	}

	public static int setLongval(byte[] para, int plen, long val, int len)
	{
		if (len <=0 ) return plen;
		
		for (int i = 0; i < len; i++) {
			para[plen++] = (byte)((val>>(len-1-i)*8) & 0xFF);
		}
		return plen;
	}
	
	public static int setAmount(byte[] para, int plen, int val, int len)
	{
		if (len != 2) return 0;
		int e = 0, rate = val;
		
		if (rate >= 0 && rate <= 16383) 				e = 0;
		else if (rate >= 16384 && rate <= 180214)		e = 1;
		else if (rate > 180214 && rate < 180224)		e = 2;//误差
		else if (rate >= 180224 && rate <= 1818524)		e = 2;
		else if (rate > 1818524 && rate < 1818624)		e = 3;//误差
		else if (rate >= 1818624 && rate <= 18201624)	e = 3;
		
		int m = 0;
		if (e == 0) m = rate;
		
		int v = 0;
		for (int n = 1; n <= e; n++) {
			v += Math.pow(2, 14) *  Math.pow(10,n-1);
		}
		
		m = Math.min((int)Math.pow(2, 14)-1, ((int)Math.ceil((rate-v)/Math.pow(10,e))));
		
		para[plen++] = (byte)((e<<6) | m>>>8);
		para[plen++] = (byte)(m & 0xff);
		
		return plen;
	}
	
	public static int addLeftClassBit(byte[] para, int plen, byte cls)
	{
		byte [] buf = new byte[plen+1];
		buf[0] = cls;
		
		System.arraycopy(para, 0, buf, 1, plen);
		System.arraycopy(buf, 0, para, 0, buf.length);
		
		return buf.length;
	}
	
	public static void transpositionClassBit(byte[] para, int plen, byte cls)
	{
		byte bit_27_28 = (byte)((para[plen- 27/8 - 1]>>(27%8)) & 0x03);
		para[plen - 27/8 - 1] = (byte)(para[plen - 27/8 - 1] & 0xE7 | (cls<<(27%8)));
		para[0] = bit_27_28;
	}
	

	public static String getXorBlock(byte[] a1, byte[] a2, int len)
	{
		StringBuffer result = new StringBuffer();

		String str;
		for (int i = 0; i < len; i++) {
			str = Integer.toHexString((a1[i] ^ a2[i]) & 0xFF);
			if (str.length()==1)	{
				result.append('0');
				result.append(str.toUpperCase());
			}
			else result.append(str.toUpperCase());
		}
		
		return result.toString();
	}
	
	public static long getMTestControl(int ctl_bit)
	{
		if (ctl_bit > 35) return 0L;
		
		long ret = 0L;
		switch (ctl_bit) {
		case STSDef.STS_MTEST_BIT_ALLTEST:
			ret = 0x0FFFFFFFFFL;
			break;
		default:
			ret = (1L<<(ctl_bit-1));
			break;
		}

		return ret;
	}
	
	public static long convert2long(String date, String format)
	{
		try {
			if (StringUtils.isNotBlank(date)) {
				if (StringUtils.isBlank(format)) 
					format = TOKEN_TIME_FORMAT;
				SimpleDateFormat sf = new SimpleDateFormat(format);
				return sf.parse(date).getTime();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return 0L;
	}
	
	public static String convert2String(long time, String format)
	{
		if (time > 0L) {
			if (StringUtils.isBlank(format))
				format = TOKEN_TIME_FORMAT;
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date date = new Date(time);		
			return sf.format(date);
		}
		
		return "";
	}
	
	//The TID field is derived from the date and time of issue and indicates the number of minutes 
	//elapsed from an STS base date and time
	//返回TokenIdentifier分钟数
	public static long getTid(int date_base)
	{
		long old = System.currentTimeMillis();
		switch (date_base) {
		case STSDef.STS_BASEDATE_1993:
			old = convert2long("1993-01-01 00:00:00", TOKEN_DATE_FORMAT);
			break;
		case STSDef.STS_BASEDATE_2014:
			old = convert2long("2014-01-01 00:00:00", TOKEN_DATE_FORMAT);
			break;
		case STSDef.STS_BASEDATE_2035:
			old = convert2long("2035-01-01 00:00:00", TOKEN_DATE_FORMAT);
			break;		
		}

		return (System.currentTimeMillis() - old) / (1000 * 60);
	}	
	
	//datel为参考时间
	public static long getTid(int date_base, String date_now)
	{
		long old = System.currentTimeMillis();
		long date_nowL = convert2long(date_now, TOKEN_TIME_FORMAT);
		
		switch (date_base) {
		case STSDef.STS_BASEDATE_1993:
			old = convert2long("1993-01-01 00:00:00", TOKEN_DATE_FORMAT);
			break;
		case STSDef.STS_BASEDATE_2014:
			old = convert2long("2014-01-01 00:00:00", TOKEN_DATE_FORMAT);
			break;
		case STSDef.STS_BASEDATE_2035:
			old = convert2long("2035-01-01 00:00:00", TOKEN_DATE_FORMAT);
			break;		
		}

		return (date_nowL - old) / (1000 * 60);
	}	
	
	//LuhnMod	
	public static int getLuhnMod(byte[] meter_number, int size)
	{
		final int[][] table = { { 0 , 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 },
			{ 0 , 2 , 4 , 6 , 8 , 1 , 3 , 5 , 7 , 9 } }; 

		int i, odd, sum;

		odd = sum = 0;

		for (i = size - 1; i >= 0; i --)  {
			odd = 1 - odd;
			sum += table [odd] [ meter_number [i]] ;  
		}

		sum %= 10 ;  
		return ( sum > 0 ? 10 - sum : 0 ) ;
	}

	//获取转换后的creditamount
	public static int getShrinkAmount(int val)
	{
		int e = 0, rate = val;
			
		if (rate >= 0 && rate <= 16383) 				e = 0;
		else if (rate >= 16384 && rate <= 180214)		e = 1;
		else if (rate > 180214 && rate < 180224)		e = 2;//误差
		else if (rate >= 180224 && rate <= 1818524)		e = 2;
		else if (rate > 1818524 && rate < 1818624)		e = 3;//误差
		else if (rate >= 1818624 && rate <= 18201624)	e = 3;
		
		int m = 0;
		if (e == 0) m = rate;
		
		int v = 0;
		for (int n = 1; n <= e; n++) {
			v += Math.pow(2, 14) *  Math.pow(10,n-1);
		}
		
		m = Math.min((int)Math.pow(2, 14)-1, ((int)Math.ceil((rate-v)/Math.pow(10,e))));
		
		byte b1 = (byte)((e<<6) | m>>>8);
		byte b2 = (byte)(m & 0xff);
	
//		return ((b1>0) ? b1 * 256 : (256 + b1) * 256) + ((b2>0) ? b2 : (256 + b2));
		return (b1 & 0xFF) * 256 + (b2 & 0xFF);
	}
	//检查creditamount是否合法
	public static boolean isValid_ShrinkAmount(int amount)
	{
		int shrink_value = getShrinkAmount(amount);
		return (shrink_value <= 0xFFFF);
	}
	//获取Credit update block
	public static String getCreditUpdateBlock(int func_id, String sm_id, int sequence_number, int balance_amount)
	{
		StringBuffer sb = new StringBuffer();
		sb.append('F').append(CommFunc.objectToString(func_id));
		sb.append(sm_id).append(String.format("%02d", sequence_number));
		sb.append(String.format("%04X", balance_amount));
		
		return sb.toString();
	}
	//转换表地址
	public static int getStrLuhnNum(String str)
	{
		int i, len, ret;
		byte[] tmp_buf = new byte[32];

		
		len = str.length();
		
		for (i = 0; i < len; i++) {
			tmp_buf[i] = (byte)(str.charAt(i) - '0');
		}

		ret = getLuhnMod(tmp_buf, len);

		return ret + '0';
	}
	
	//将表地址转换为byte[]
	public static byte[] getMeterAddrByte(String meterAddr){
		byte[] tmp_buf = new byte[32];
		for (int i = 0, len = meterAddr.length(); i < len; i++) {
			tmp_buf[i] = (byte)(meterAddr.charAt(i) - '0');
		}
		return tmp_buf;
	}
	

	//获取PAN(对meter_addr进行处理，获取19位数字)
	public static String getPAN(String meter_addr)
	{
		StringBuffer sb = new StringBuffer();
		int m_len = meter_addr.length();
		if (m_len == 10) {
			int crc = getStrLuhnNum(meter_addr);
			sb.append(STSDef.STS_IIN_SHORTADDR).append(meter_addr).append((char)crc);
			crc = getStrLuhnNum(sb.toString());
			sb.append((char)crc);
		}
		else if (m_len == 11) {
			sb.append(STSDef.STS_IIN_SHORTADDR).append(meter_addr);
			int crc = getStrLuhnNum(sb.toString());
			sb.append((char)crc);
		}
		else if (m_len == 12) {
			int crc = getStrLuhnNum(meter_addr);
			sb.append(STSDef.STS_IIN_LONGADDR).append(meter_addr).append((char)crc);
			crc = getStrLuhnNum(sb.toString());
			sb.append((char)crc);
		}
		else if (m_len == 13) {
			sb.append(STSDef.STS_IIN_LONGADDR).append(meter_addr);
			int crc = getStrLuhnNum(sb.toString());
			sb.append((char)crc);
		}
		
		return sb.append(" ").toString();	//需要补齐为19位
	}


	public static int getStsAmount(int val)
	{
		int e = 0, rate = val;
		
		if (rate >= 0 && rate <= 16383) 				e = 0;
		else if (rate >= 16384 && rate <= 180214)		e = 1;
		else if (rate > 180214 && rate < 180224)		e = 2;//误差
		else if (rate >= 180224 && rate <= 1818524)		e = 2;
		else if (rate > 1818524 && rate < 1818624)		e = 3;//误差
		else if (rate >= 1818624 && rate <= 18201624)	e = 3;
		
		int m = 0;
		if (e == 0) m = rate;
		
		int v = 0;
		for (int n = 1; n <= e; n++) {
			v += Math.pow(2, 14) *  Math.pow(10,n-1);
		}
		
		m = Math.min((int)Math.pow(2, 14)-1, ((int)Math.ceil((rate-v)/Math.pow(10,e))));
		
		//[plen++] = (byte)((e<<6) | m>>>8);
		//para[plen++] = (byte)(m & 0xff);
		int i1 = (byte)((e<<6) | m>>>8);
		int i2 = (byte)(m & 0xff);
		
		i1 = (i1<0 ? 256+i1 : i1);
		i2 = (i2<0 ? 256+i2 : i2);
		
		return i1*256 + i2;
	}
	
	
	//使用IO的inputstream流将byte[]转换为object
	public static Object byteToObject(byte[] bytes){
		Object obj = null;
		ByteArrayInputStream bi = new ByteArrayInputStream(bytes); 
		ObjectInputStream oi = null;
		
		try {
			oi = new ObjectInputStream(bi);
			obj = oi.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				bi.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				oi.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return obj;
	}
	
	/**
	 * 使用IO的outputstream流将object转换为byte[]
	 * @param obj
	 * @return
	 */
	public static byte[] objectToByte(Object obj){
		byte[] bytes = null;
		ByteArrayOutputStream bo = new ByteArrayOutputStream(); 
		ObjectOutputStream oo = null;
		
		try {
			oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);
			bytes = bo.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				bo.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				oo.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		return bytes;
	}
}