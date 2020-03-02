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
package com.sts;

import java.util.ArrayList;
import java.util.List;

import com.kesd.common.CommFunc;
import com.sts.common.STSDef;
import com.sts.common.StsAlgo;
import com.sts.common.StsFunc;

public class SoftEncryptedToken implements TokenInterface
{
	public String getToken_TransferCredit(byte subcls, byte rnd, long tid, int val, byte[] keydata)
	{
		String retstr = "";

		List<Byte> tokenlist = new ArrayList<Byte>();

		byte [] buf = new byte[10];
		int ptr = 0, crc = 0;

		ptr = StsFunc.setIntval(buf, ptr, subcls<<4 | rnd, 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)((tid>>>16) & 0xFF), 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)((tid>>>8) & 0xFF), 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)(tid & 0xFF), 1);
		ptr = StsFunc.setAmount(buf, ptr, val, 2);
		ptr = StsFunc.addLeftClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_0);
		crc = StsAlgo.stsMakeCRC(buf, ptr);
		ptr = StsFunc.setIntval(buf, ptr, crc, 2);
		
		byte [] tmpbuf = new byte[8];
		System.arraycopy(buf, 1, tmpbuf, 0, 8);
		StsAlgo.stsEncrypt_EA07(tmpbuf, keydata);
		System.arraycopy(tmpbuf, 0, buf, 1, 8);
		
		StsFunc.transpositionClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_0);
	
		for (int i = 0; i < ptr; i++) {
			tokenlist.add(buf[i]);
		}
		
		retstr = StsFunc.hexStringToToken(StsFunc.bytes2HexString(buf, ptr));

		return CommFunc.splitToken(retstr);
	}
	
	public String getToken_InitIateMeterTestOrDisplay(byte subcls/*子类号*/, long ctrl/*控制码*/, String mfrcode/*厂商代码*/, byte[] keydata)
	{		
		String retstr = "";
		
		List<Byte> tokenlist = new ArrayList<Byte>();
		
		byte [] buf = new byte[10];
		int ptr = 0, crc = 0;
		
		if (mfrcode.length() <= 2) {
			ptr = StsFunc.setIntval(buf, ptr, (int)(subcls<<4 | ((ctrl>>>32)&0xFF)), 1);
			ptr = StsFunc.setIntval(buf, ptr, (int)((ctrl>>>24) & 0xFF), 1);
			ptr = StsFunc.setIntval(buf, ptr, (int)((ctrl>>>16) & 0xFF), 1);
			ptr = StsFunc.setIntval(buf, ptr, (int)((ctrl>>>8) & 0xFF), 1);
			ptr = StsFunc.setIntval(buf, ptr, (int)((ctrl) & 0xFF), 1);
			ptr = StsFunc.setIntval(buf, ptr, Integer.parseInt(mfrcode), 1);
		}
		else {
			ptr = StsFunc.setIntval(buf, ptr, (int)(subcls<<4 | ((ctrl>>>24)&0xFF)), 1);
			ptr = StsFunc.setIntval(buf, ptr, (int)((ctrl>>>16) & 0xFF), 1);
			ptr = StsFunc.setIntval(buf, ptr, (int)((ctrl>>>8) & 0xFF), 1);
			ptr = StsFunc.setIntval(buf, ptr, (int)((ctrl) & 0xFF), 1);
			ptr = StsFunc.setIntval(buf, ptr, Integer.parseInt(mfrcode), 2);
		}
		ptr = StsFunc.addLeftClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_1);
		crc = StsAlgo.stsMakeCRC(buf, ptr);
		ptr = StsFunc.setIntval(buf, ptr, crc, 2);
		
		//1类TOKEN是明文，无需加密
//		byte [] tmpbuf = new byte[8];
//		System.arraycopy(buf, 1, tmpbuf, 0, 8);
//		StsAlgo.stsEncrypt_EA07(tmpbuf, keydata);
//		System.arraycopy(tmpbuf, 0, buf, 1, 8);
		
		StsFunc.transpositionClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_1);
		
		for (int i = 0; i < ptr; i++) {
			tokenlist.add(buf[i]);
		}
		
		retstr = StsFunc.hexStringToToken(StsFunc.bytes2HexString(buf, ptr));

		return CommFunc.splitToken(retstr);
	}
	
	public String getToken_SetMaximumPowerLimit(byte subcls, byte rnd, long tid, int val, byte[] keydata)
	{		
		String retstr = "";
		
		List<Byte> tokenlist = new ArrayList<Byte>();
		
		byte [] buf = new byte[10];
		int ptr = 0, crc = 0;
		
		ptr = StsFunc.setIntval(buf, ptr, subcls<<4 | rnd, 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)((tid>>>16) & 0xFF), 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)((tid>>>8) & 0xFF), 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)(tid & 0xFF), 1);
		
		ptr = StsFunc.setAmount(buf, ptr, val, 2);
		ptr = StsFunc.addLeftClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_2);
		crc = StsAlgo.stsMakeCRC(buf, ptr);
		ptr = StsFunc.setIntval(buf, ptr, crc, 2);
		
		byte [] tmpbuf = new byte[8];
		System.arraycopy(buf, 1, tmpbuf, 0, 8);
		StsAlgo.stsEncrypt_EA07(tmpbuf, keydata);
		System.arraycopy(tmpbuf, 0, buf, 1, 8);
		
		StsFunc.transpositionClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_2);
			
		for (int i = 0; i < ptr; i++) {
			tokenlist.add(buf[i]);
		}

		retstr = StsFunc.hexStringToToken(StsFunc.bytes2HexString(buf, ptr));

		return CommFunc.splitToken(retstr);
	}

	public String getToken_ClearCredit(byte subcls, byte rnd, long tid, int val, byte[] keydata)
	{		
		String retstr = "";
		
		List<Byte> tokenlist = new ArrayList<Byte>();
		
		byte [] buf = new byte[10];
		int ptr = 0, crc = 0;
		
		ptr = StsFunc.setIntval(buf, ptr, subcls<<4 | rnd, 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)((tid>>>16) & 0xFF), 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)((tid>>>8) & 0xFF), 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)(tid & 0xFF), 1);
		ptr = StsFunc.setAmount(buf, ptr, val, 2);
		ptr = StsFunc.addLeftClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_2);
		crc = StsAlgo.stsMakeCRC(buf, ptr);
		ptr = StsFunc.setIntval(buf, ptr, crc, 2);
		
		byte [] tmpbuf = new byte[8];
		System.arraycopy(buf, 1, tmpbuf, 0, 8);
		StsAlgo.stsEncrypt_EA07(tmpbuf, keydata);
		System.arraycopy(tmpbuf, 0, buf, 1, 8);
		
		StsFunc.transpositionClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_2);
		
		for (int i = 0; i < ptr; i++) {
			tokenlist.add(buf[i]);
		}

		retstr = StsFunc.hexStringToToken(StsFunc.bytes2HexString(buf, ptr));

		return CommFunc.splitToken(retstr);
	}

	public String getToken_SetTariffRate(byte subcls, byte rnd, long tid, int val, byte[] keydata)
	{		
		String retstr = "";
		
		List<Byte> tokenlist = new ArrayList<Byte>();
		
		byte [] buf = new byte[10];
		int ptr = 0, crc = 0;
		
		ptr = StsFunc.setIntval(buf, ptr, subcls<<4 | rnd, 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)((tid>>>16) & 0xFF), 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)((tid>>>8) & 0xFF), 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)(tid & 0xFF), 1);
		ptr = StsFunc.setAmount(buf, ptr, val, 2);
		ptr = StsFunc.addLeftClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_2);
		crc = StsAlgo.stsMakeCRC(buf, ptr);
		ptr = StsFunc.setIntval(buf, ptr, crc, 2);
		
		byte [] tmpbuf = new byte[8];
		System.arraycopy(buf, 1, tmpbuf, 0, 8);
		StsAlgo.stsEncrypt_EA07(tmpbuf, keydata);
		System.arraycopy(tmpbuf, 0, buf, 1, 8);
		
		StsFunc.transpositionClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_2);
		
		for (int i = 0; i < ptr; i++) {
			tokenlist.add(buf[i]);
		}

		retstr = StsFunc.hexStringToToken(StsFunc.bytes2HexString(buf, ptr));

		return CommFunc.splitToken(retstr);
	}

	public String getToken_Set1stSectionDecoderKey(byte subcls, byte kenho, byte krn, byte ro, byte res, byte kt, long nkho, byte[] keydata)
	{		
		String retstr = "";
		
		List<Byte> tokenlist = new ArrayList<Byte>();
		
		byte [] buf = new byte[10];
		int ptr = 0, crc = 0;
		
		ptr = StsFunc.setIntval(buf, ptr, (subcls<<4) | (kenho & 0x0F), 1);
		ptr = StsFunc.setIntval(buf, ptr, krn<<4 | ro<<3 | res<<2 | kt, 1);
		ptr = StsFunc.setLongval(buf, ptr, nkho, 4);
		ptr = StsFunc.addLeftClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_2);
		crc = StsAlgo.stsMakeCRC(buf, ptr);
		ptr = StsFunc.setIntval(buf, ptr, crc, 2);
		
		byte [] tmpbuf = new byte[8];
		System.arraycopy(buf, 1, tmpbuf, 0, 8);
		StsAlgo.stsEncrypt_EA07(tmpbuf, keydata);
		System.arraycopy(tmpbuf, 0, buf, 1, 8);
		
		StsFunc.transpositionClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_2);
		
		for (int i = 0; i < ptr; i++) {
			tokenlist.add(buf[i]);
		}

		retstr = StsFunc.hexStringToToken(StsFunc.bytes2HexString(buf, ptr));

		return CommFunc.splitToken(retstr);
	}
	
	public String getToken_Set2ndSectionDecoderKey(byte subcls, byte kenlo, byte ti, long nklo, byte[] keydata)
	{		
		String retstr = "";
		
		List<Byte> tokenlist = new ArrayList<Byte>();
		
		byte [] buf = new byte[10];
		int ptr = 0, crc = 0;
		
		ptr = StsFunc.setIntval(buf, ptr, (subcls<<4) | (kenlo & 0x0F), 1);
		ptr = StsFunc.setIntval(buf, ptr, ti, 1);
		ptr = StsFunc.setLongval(buf, ptr, nklo, 4);
		ptr = StsFunc.addLeftClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_2);
		crc = StsAlgo.stsMakeCRC(buf, ptr);
		ptr = StsFunc.setIntval(buf, ptr, crc, 2);
		
		byte [] tmpbuf = new byte[8];
		System.arraycopy(buf, 1, tmpbuf, 0, 8);
		StsAlgo.stsEncrypt_EA07(tmpbuf, keydata);
		System.arraycopy(tmpbuf, 0, buf, 1, 8);
		
		StsFunc.transpositionClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_2);
		
		for (int i = 0; i < ptr; i++) {
			tokenlist.add(buf[i]);
		}

		retstr = StsFunc.hexStringToToken(StsFunc.bytes2HexString(buf, ptr));

		return CommFunc.splitToken(retstr);
	}
	
	public  String getToken_ClearTamperCondition(byte subcls, byte rnd, long tid, int val, byte[] keydata)
	{		
		String retstr = "";		
		List<Byte> tokenlist = new ArrayList<Byte>();		
		byte [] buf = new byte[10];
		int ptr = 0, crc = 0;
		
		ptr = StsFunc.setIntval(buf, ptr, subcls<<4 | rnd, 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)((tid>>>16) & 0xFF), 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)((tid>>>8) & 0xFF), 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)(tid & 0xFF), 1);
		ptr = StsFunc.setAmount(buf, ptr, val, 2);
		ptr = StsFunc.addLeftClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_2);
		crc = StsAlgo.stsMakeCRC(buf, ptr);
		ptr = StsFunc.setIntval(buf, ptr, crc, 2);
		
		byte [] tmpbuf = new byte[8];
		System.arraycopy(buf, 1, tmpbuf, 0, 8);
		StsAlgo.stsEncrypt_EA07(tmpbuf, keydata);
		System.arraycopy(tmpbuf, 0, buf, 1, 8);
		
		StsFunc.transpositionClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_2);
		
		for (int i = 0; i < ptr; i++) {
			tokenlist.add(buf[i]);
		}

		retstr = StsFunc.hexStringToToken(StsFunc.bytes2HexString(buf, ptr));

		return CommFunc.splitToken(retstr);
	}
	
	public String getToken_SetMaximumPhasePowerUnbalanceLimit(byte subcls, byte rnd, long tid, int val, byte[] keydata)
	{		
		String retstr = "";
		
		List<Byte> tokenlist = new ArrayList<Byte>();
		
		byte [] buf = new byte[10];
		int ptr = 0, crc = 0;
		
		ptr = StsFunc.setIntval(buf, ptr, subcls<<4 | rnd, 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)((tid>>>16) & 0xFF), 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)((tid>>>8) & 0xFF), 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)(tid & 0xFF), 1);
		ptr = StsFunc.setAmount(buf, ptr, val, 2);
		ptr = StsFunc.addLeftClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_2);
		crc = StsAlgo.stsMakeCRC(buf, ptr);
		ptr = StsFunc.setIntval(buf, ptr, crc, 2);
		
		byte [] tmpbuf = new byte[8];
		System.arraycopy(buf, 1, tmpbuf, 0, 8);
		StsAlgo.stsEncrypt_EA07(tmpbuf, keydata);
		System.arraycopy(tmpbuf, 0, buf, 1, 8);
		
		StsFunc.transpositionClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_2);
		
		for (int i = 0; i < ptr; i++) {
			tokenlist.add(buf[i]);
		}

		retstr = StsFunc.hexStringToToken(StsFunc.bytes2HexString(buf, ptr));

		return CommFunc.splitToken(retstr);
	}

	public String getToken_SetWaterMeterFactor(byte subcls, byte rnd, long tid, int val, byte[] keydata)
	{		
		String retstr = "";
		
		List<Byte> tokenlist = new ArrayList<Byte>();
		
		byte [] buf = new byte[10];
		int ptr = 0, crc = 0;
		
		ptr = StsFunc.setIntval(buf, ptr, subcls<<4 | rnd, 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)((tid>>>16) & 0xFF), 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)((tid>>>8) & 0xFF), 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)(tid & 0xFF), 1);
		ptr = StsFunc.setAmount(buf, ptr, val, 2);
		ptr = StsFunc.addLeftClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_2);
		crc = StsAlgo.stsMakeCRC(buf, ptr);
		ptr = StsFunc.setIntval(buf, ptr, crc, 2);
		
		byte [] tmpbuf = new byte[8];
		System.arraycopy(buf, 1, tmpbuf, 0, 8);
		StsAlgo.stsEncrypt_EA07(tmpbuf, keydata);
		System.arraycopy(tmpbuf, 0, buf, 1, 8);
		
		StsFunc.transpositionClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_2);
		
		for (int i = 0; i < ptr; i++) {
			tokenlist.add(buf[i]);
		}

		retstr = StsFunc.hexStringToToken(StsFunc.bytes2HexString(buf, ptr));

		return CommFunc.splitToken(retstr);
	}
	
	public String getToken_StsReserved(byte subcls, byte rnd, long tid, int val, byte[] keydata)
	{
		String retstr = "";
		
		List<Byte> tokenlist = new ArrayList<Byte>();
		
		byte [] buf = new byte[10];
		int ptr = 0, crc = 0;
		
		ptr = StsFunc.setIntval(buf, ptr, subcls<<4 | rnd, 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)((tid>>>16) & 0xFF), 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)((tid>>>8) & 0xFF), 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)(tid & 0xFF), 1);
		ptr = StsFunc.setIntval(buf, ptr, val, 2);
		ptr = StsFunc.addLeftClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_2);
		crc = StsAlgo.stsMakeCRC(buf, ptr);
		ptr = StsFunc.setIntval(buf, ptr, crc, 2);
		
		byte [] tmpbuf = new byte[8];
		System.arraycopy(buf, 1, tmpbuf, 0, 8);
		StsAlgo.stsEncrypt_EA07(tmpbuf, keydata);
		System.arraycopy(tmpbuf, 0, buf, 1, 8);
		
		StsFunc.transpositionClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_2);
		
		for (int i = 0; i < ptr; i++) {
			tokenlist.add(buf[i]);
		}

		retstr = StsFunc.hexStringToToken(StsFunc.bytes2HexString(buf, ptr));

		return CommFunc.splitToken(retstr);	
	}

	public String getToken_ProprietaryReserved(byte subcls, byte rnd, long tid, int val, byte[] keydata)
	{
		String retstr = "";
		
		List<Byte> tokenlist = new ArrayList<Byte>();
		
		byte [] buf = new byte[10];
		int ptr = 0, crc = 0;
		
		ptr = StsFunc.setIntval(buf, ptr, subcls<<4 | rnd, 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)((tid>>>16) & 0xFF), 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)((tid>>>8) & 0xFF), 1);
		ptr = StsFunc.setIntval(buf, ptr, (int)(tid & 0xFF), 1);
		ptr = StsFunc.setIntval(buf, ptr, val, 2);
		ptr = StsFunc.addLeftClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_2);
		crc = StsAlgo.stsMakeCRC(buf, ptr);
		ptr = StsFunc.setIntval(buf, ptr, crc, 2);
		
		byte [] tmpbuf = new byte[8];
		System.arraycopy(buf, 1, tmpbuf, 0, 8);
		StsAlgo.stsEncrypt_EA07(tmpbuf, keydata);
		System.arraycopy(tmpbuf, 0, buf, 1, 8);
		
		StsFunc.transpositionClassBit(buf, ptr, STSDef.STS_TOKEN_CLS_2);
		
		for (int i = 0; i < ptr; i++) {
			tokenlist.add(buf[i]);
		}

		retstr = StsFunc.hexStringToToken(StsFunc.bytes2HexString(buf, ptr));

		return CommFunc.splitToken(retstr);	
	}
	
	public static String get_ControlBlock(byte KT, int SGC, byte TI, byte KRN)
	{
		StringBuffer result = new StringBuffer();
		result.append(String.format("%d%06d%02d%d%s", KT, SGC, TI, KRN, "FFFFFF"));
		return result.toString();
	}

	//获取PAN(厂商编码2位，IIN使用600727，厂商编码4位，IIN使用0000，返回16位串)
	public static String get_PANBlock(int IIN, long DRN)
	{
		StringBuffer result = new StringBuffer();
		IIN = IIN > 100000 ? IIN-(IIN/100000)*100000 : IIN;		
		result.append(String.format("%05d%011d", IIN, DRN));
		return result.toString();
		//return String.format("%05s%011s", IIN.substring(IIN.length()-5, IIN.length()-1), DRN);
	}
	
	//获取PAN(厂商编码2位，IIN使用600727，厂商编码4位，IIN使用0000，返回16位串)
	public static String get_PANBlock(String meter_addr)
	{
		StringBuffer sb = new StringBuffer();
		int m_len = meter_addr.length();
		if (m_len == 10) {
			int crc = StsFunc.getStrLuhnNum(meter_addr);
			sb.append(STSDef.STS_IIN_SHORTADDR).append(meter_addr).append((char)crc);
		}
		else if (m_len == 11) {
			sb.append(STSDef.STS_IIN_SHORTADDR).append(meter_addr);
		}
		else if (m_len == 12) {
			int crc = StsFunc.getStrLuhnNum(meter_addr);
			sb.append(STSDef.STS_IIN_LONGADDR).append(meter_addr).append((char)crc);
		}
		else if (m_len == 13) {
			sb.append(STSDef.STS_IIN_LONGADDR).append(meter_addr);
		}
		
		String pan = sb.toString();
		return pan.substring(pan.length()-16, pan.length());		//取16位长度字符串
	}
	
   public String TransferCredit_Test()
    {
    	String keydata = "0124000000000001";
    	String ret = getToken_TransferCredit((byte)0, (byte)1, 1, (int)(12345.6*1000/100), StsFunc.hexStringToByte(keydata));
    	//String ret = stsEncrypt_EA07(StsFunc.hexStringToByte(bd), StsFunc.hexStringToByte(keydata));
    	return ret;
    }
   
//   public String NewMeterKey_Test()
//   {
//	   StringBuffer result = new StringBuffer();
//	   String keydata = "0124000000000001";
//	   result.append(getToken_Set1stSectionDecoderKey((byte)3, (byte)255, (byte)1, (byte)0, (byte)0, (byte)2, (int)0x6ca9aad4, StsFunc.hexStringToByte(keydata)));
//	   result.append("----");
//	   result.append(getToken_Set2ndSectionDecoderKey((byte)4, (byte)255, (byte)1, (int)0x270fa736, StsFunc.hexStringToByte(keydata)));
//	   return result.toString();
//   }
   
   public String LoadSwitch_Test(long ctrl)
   {
	   String keydata = "0124000000000001";
	   //long ctrl = StsFunc.getMTestControl(ctrl_type);
	   String ret = getToken_InitIateMeterTestOrDisplay((byte)0, ctrl, "00", StsFunc.hexStringToByte(keydata));
	   
	   return ret; 
   }
   
   public String MPL_Test()
   {
	   String keydata = "0124000000000001";
	   String ret = getToken_SetMaximumPowerLimit((byte)0, (byte)1, 12345L, 1256, StsFunc.hexStringToByte(keydata));
	   
	   return ret;   
   }
   
   public String PPUL_Test()
   {
	   String keydata = "0124000000000001";
	   String ret = getToken_SetMaximumPhasePowerUnbalanceLimit((byte)6, (byte)1, 12345L, 1256, StsFunc.hexStringToByte(keydata));
	   
	   return ret;
   }
	public static void main(String[] args)
	{
		
		
		String token = new SoftEncryptedToken().getToken_ClearTamperCondition((byte)5, (byte)1, 850601, 0, StsFunc.hexStringToByte("1B3F286556CA4883"));
		
		System.out.println("token  : "+ token);
		
		long lll = 0L;
		String s = "54224C0B";
		lll = Long.parseLong(s, 16);
		
		long tid = StsFunc.getTid(STSDef.STS_BASEDATE_2014);
		SoftEncryptedToken prot = new SoftEncryptedToken();
		//充值测试
//		String str = prot.TransferCredit_Test();
//		System.out.println("缴费串：" + str);
//		
//		String str = prot.MPL_Test();
//		System.out.println("SetMaximumPowerLimit TOKEN：" + str);
		
		String str = prot.LoadSwitch_Test(STSDef.STS_MTEST_BIT_DISPLAY);
		System.out.println("MeterTest TOKEN：" + str);
//		String str = prot.NewMeterKey_Test();
//		System.out.println("新1类密钥串：" + str);
//		String str1 = "0500303969d3ab95";
//		String key ="0124000000000001";
//		String ret = StsAlgo.stsEncrypt_EA07(StsFunc.hexStringToByte(str1), StsFunc.hexStringToByte(key));
//		System.out.println(ret);
	}
}