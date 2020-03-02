package com.hx.ami.spi;

import java.util.ArrayList;
import java.util.List;

public class CKeToken implements Token, SmartCard{
	class CardItemObj{
		String name;
		String value;
		public CardItemObj(String name, String value/*, String endname*/){
			this.name = name;
			this.value = value;
		}
		public CardItemObj(String name){
			this.name = name;
			this.value = "";
		}
	}
	
	//1)	Generate credit Token 生成信用代码
	public String getCreditToken(String meterNo, String sgc, int tarrifIndex, int keyVersion, int keyExpiredTime, long keyNo, int seqNo, int amount)
	{
		String retstr = "", tokenstr= "";

		List<String> tokenlist = new ArrayList<String>();

		byte []buf = new byte[10];
		int ptr = 0, errcode = 0;

		if (amount <= 0) errcode = MjlDef.MJLTOKEN_ERR_CREDITERR;
		else if ((keyNo <0) || (keyNo > 65535)) errcode = MjlDef.MJLTOKEN_ERR_KEYNO;
		else {
			CChgFanc.SetIntval(buf, ptr, MjlDef.MJLCARDTOKENID_CREDIT, 1);	ptr += 1;
			CChgFanc.SetIntval(buf, ptr, (int)keyNo, 2);					ptr += 2;	
			CChgFanc.SetIntval(buf, ptr, seqNo, 1);							ptr += 1;
			CChgFanc.SetIntval(buf, ptr, amount,4);							ptr += 4;
			CChgFanc.SetIntval(buf, ptr, 0, 2);								ptr += 2;//空出来
			
			tokenstr = CChgFanc.Bytes2HexString(buf, ptr);
	
			tokenlist.add(tokenstr);
		}
		
		retstr = ChgXmlStr(errcode, tokenlist);

		return retstr;
	}
	
	//2)	Generate key  change Token 生成密钥改变代码
	public String getKeyChangeToken (String meterNo, String sgc, int tarrifIndex, int keyVersion, int keyExpiredTime, long keyNo, String nSgc, int nTariffIndex, int nKeyVersion,int nKeyExpiredTime, long nKeyNo)
	{
		String retstr = "", tokenstr= "";

		List<String> tokenlist = new ArrayList<String>();

		byte []buf = new byte[10];
		int ptr = 0, errcode = 0;

		CChgFanc.SetIntval(buf, ptr, MjlDef.MJLCARDTOKENID_CHANGEKEY, 1);	ptr += 1;
		CChgFanc.SetIntval(buf, ptr, (int)keyNo, 2);					ptr += 2;	
		CChgFanc.SetIntval(buf, ptr, (int)nKeyNo, 2);					ptr += 2;
		CChgFanc.SetIntval(buf, ptr, 0, 1);								ptr += 1;//空出来
		CChgFanc.SetIntval(buf, ptr, 0, 4);								ptr += 4;//空出来
		
		tokenstr = CChgFanc.Bytes2HexString(buf, ptr);

		tokenlist.add(tokenstr);

		retstr = ChgXmlStr(errcode, tokenlist);

		return retstr;
	}
	
	//3)	Generate Management Token 生成管理代码
	public String getClearBalanceToken (String meterNo, String Sgc, int tarrifIndex, int keyVersion, int keyExpiredTime, long keyNo, int seqNo)
	{
		String retstr = "", tokenstr= "";

		List<String> tokenlist = new ArrayList<String>();

		byte []buf = new byte[10];
		int ptr = 0, errcode = 0;


		CChgFanc.SetIntval(buf, ptr, MjlDef.MJLCARDTOKENID_BALANCE, 1);	ptr += 1;
		CChgFanc.SetIntval(buf, ptr, (int)keyNo, 2);					ptr += 2;	
		CChgFanc.SetIntval(buf, ptr, seqNo, 1);							ptr += 1;
		CChgFanc.SetIntval(buf, ptr, 0, 2);								ptr += 2;//空出来
		CChgFanc.SetIntval(buf, ptr, 0, 4);								ptr += 4;//空出来
		
		tokenstr = CChgFanc.Bytes2HexString(buf, ptr);

		tokenlist.add(tokenstr);

		retstr = ChgXmlStr(errcode, tokenlist);

		return retstr;
	}
	
	//Generate clear event Token 生成清除时间代码
	public String getClearEventToken (String meterNo, String Sgc, int tarrifIndex, int keyVersion, int keyExpiredTime, long keyNo, int seqNo)
	{
		String retstr = "", tokenstr= "";

		List<String> tokenlist = new ArrayList<String>();

		byte []buf = new byte[10];
		int ptr = 0, errcode = 0;

		CChgFanc.SetIntval(buf, ptr, MjlDef.MJLCARDTOKENID_EVENT, 1);	ptr += 1;
		CChgFanc.SetIntval(buf, ptr, (int)keyNo, 2);					ptr += 2;	
		CChgFanc.SetIntval(buf, ptr, seqNo, 1);							ptr += 1;
		CChgFanc.SetIntval(buf, ptr, 0, 2);								ptr += 2;//空出来
		CChgFanc.SetIntval(buf, ptr, 0, 4);								ptr += 4;//空出来
		
		tokenstr = CChgFanc.Bytes2HexString(buf, ptr);

		tokenlist.add(tokenstr);

		retstr = ChgXmlStr(errcode, tokenlist);

		return retstr;
	}
	
	//Generate max Power Limit Mode Token 生成最大功率极限模式代码
	public String getMaxPowerLimitToken(String meterNo,String Sgc, int tarrifIndex, int keyVersion, int keyExpiredTime, long keyNo, int seqNo, int activationModel, String date,int[] maxPowerLimits, int[] hours)
	{
		String retstr = "", tokenstr= "";

		List<String> tokenlist = new ArrayList<String>();

		byte []buf = new byte[10];
		int ptr = 0, errcode = 0;

		CChgFanc.SetIntval(buf, ptr, MjlDef.MJLCARDTOKENID_MAXPOWLIMIT, 1);	ptr += 1;
		CChgFanc.SetIntval(buf, ptr, (int)keyNo, 2);						ptr += 2;
		CChgFanc.SetIntval(buf, ptr, (int)seqNo, 1);						ptr += 1;
		CChgFanc.SetIntval(buf, ptr, activationModel, 1);					ptr += 1;
		
		int idate = Integer.parseInt(date.substring(0, 4)) * 10000 + 
					Integer.parseInt(date.substring(5, 7)) * 100 +
					Integer.parseInt(date.substring(8, 10));
		
		CChgFanc.SetBcdIntval(buf, ptr, idate, 3);							ptr += 3;
		CChgFanc.SetIntval(buf, ptr, 0, 2);									ptr += 2;//空出来

		tokenstr = CChgFanc.Bytes2HexString(buf, ptr);

		tokenlist.add(tokenstr);

		///////////
		ptr = 0;
		CChgFanc.SetIntval(buf, ptr, MjlDef.MJLCARDTOKENID_MAXPOWLIMIT2, 1);ptr += 1;
		CChgFanc.SetBcdIntval(buf, ptr, (int)maxPowerLimits[0], 2);			ptr += 2;	
		CChgFanc.SetBcdIntval(buf, ptr, (int)maxPowerLimits[1], 2);			ptr += 2;
		CChgFanc.SetBcdIntval(buf, ptr, hours[0], 1);						ptr += 1;
		CChgFanc.SetBcdIntval(buf, ptr, hours[1], 1);						ptr += 1;
		CChgFanc.SetIntval(buf, ptr, 0, 3);									ptr += 3;//空出来

		tokenstr = CChgFanc.Bytes2HexString(buf, ptr);

		tokenlist.add(tokenstr);

		retstr = ChgXmlStr(errcode, tokenlist);

		return retstr;
	}
	
	//Generate single Tariff Token生成单一费率代码
	public String getSingleTariffToken(String meterNo, String Sgc, int tarrifIndex,int keyVersion, int keyExpiredTime, long keyNo, int seqNo,String activatingDate,  int activatingModel, int validDate, int rate)
	{
		String retstr = "", tokenstr= "";

		List<String> tokenlist = new ArrayList<String>();

		byte []buf = new byte[10];
		int ptr = 0, errcode = 0;

		CChgFanc.SetIntval(buf, ptr, MjlDef.MJLCARDTOKENID_SINGLETARIFF, 1);	ptr += 1;
		CChgFanc.SetIntval(buf, ptr, (int)keyNo, 2);							ptr += 2;
		CChgFanc.SetIntval(buf, ptr, seqNo, 1);									ptr += 1;
	
		int idate = Integer.parseInt(activatingDate.substring(0, 4)) * 10000 + 
					Integer.parseInt(activatingDate.substring(5, 7)) * 100 +
					Integer.parseInt(activatingDate.substring(8, 10));

		CChgFanc.SetBcdIntval(buf, ptr, idate, 3);								ptr += 3;
		CChgFanc.SetIntval(buf, ptr, (int)activatingModel, 1);					ptr += 1;
		CChgFanc.SetIntval(buf, ptr, (int)validDate, 1);						ptr += 1;

		CChgFanc.SetIntval(buf, ptr, 0, 1);										ptr += 1;//空出来
		
		tokenstr = CChgFanc.Bytes2HexString(buf, ptr);

		tokenlist.add(tokenstr);

		///////////
		ptr = 0;
		CChgFanc.SetIntval(buf, ptr, MjlDef.MJLCARDTOKENID_SINGLETARIFF2, 1);ptr += 1;
		CChgFanc.SetBcdIntval(buf, ptr, rate, 2);							 ptr += 2;	
		CChgFanc.SetBcdIntval(buf, ptr, 0, 1);								 ptr += 1;//空出来
		CChgFanc.SetBcdIntval(buf, ptr, 0, 4);								 ptr += 4;//空出来
		CChgFanc.SetBcdIntval(buf, ptr, 0, 2);								 ptr += 2;//空出来
		
		tokenstr = CChgFanc.Bytes2HexString(buf, ptr);

		tokenlist.add(tokenstr);

		retstr = ChgXmlStr(errcode, tokenlist);

		return retstr;
	}

	//Generate step Tariff Token 生成阶梯电价代码
	public String getStepTariffToken(String meterNo, String Sgc, int tarrifIndex,int keyVersion, int keyExpiredTime, long keyNo, int seqNo,String activatingDate, int activatingModel, int validDate, int[] rates, int[] steps)
	{
		String retstr = "", tokenstr= "";

		List<String> tokenlist = new ArrayList<String>();

		byte []buf = new byte[10];
		int ptr = 0, errcode = 0;

		CChgFanc.SetIntval(buf, ptr, MjlDef.MJLCARDTOKENID_STEPTARIFF, 1);		ptr += 1;
		CChgFanc.SetIntval(buf, ptr, (int)keyNo, 2);							ptr += 2;
		CChgFanc.SetIntval(buf, ptr, seqNo, 1);									ptr += 1;
	
		int idate = Integer.parseInt(activatingDate.substring(0, 4)) * 10000 + 
					Integer.parseInt(activatingDate.substring(5, 7)) * 100 +
					Integer.parseInt(activatingDate.substring(8, 10));

		CChgFanc.SetBcdIntval(buf, ptr, idate, 3);								ptr += 3;
		CChgFanc.SetIntval(buf, ptr, (int)activatingModel, 1);					ptr += 1;
		CChgFanc.SetIntval(buf, ptr, (int)validDate, 1);						ptr += 1;
		CChgFanc.SetIntval(buf, ptr, 0, 1);										ptr += 1;//空出来
		
		tokenstr = CChgFanc.Bytes2HexString(buf, ptr);

		tokenlist.add(tokenstr);

		///////////
		ptr = 0;
		CChgFanc.SetIntval(buf, ptr, MjlDef.MJLCARDTOKENID_STEPTARIFF2, 1);		ptr += 1;
		CChgFanc.SetBcdIntval(buf, ptr, rates[0], 2);							ptr += 2;
		CChgFanc.SetBcdIntval(buf, ptr, rates[1], 2);							ptr += 2;
		CChgFanc.SetBcdIntval(buf, ptr, rates[2], 2);							ptr += 2;
		CChgFanc.SetBcdIntval(buf, ptr, rates[3], 2);							ptr += 2;
		CChgFanc.SetBcdIntval(buf, ptr, 0, 1);									ptr += 1;//空出来
		
		tokenstr = CChgFanc.Bytes2HexString(buf, ptr);

		tokenlist.add(tokenstr);
		
		///////////
		ptr = 0;
		CChgFanc.SetIntval(buf, ptr, MjlDef.MJLCARDTOKENID_STEPTARIFF3, 1);		ptr += 1;
		if (rates.length >= 5)	CChgFanc.SetBcdIntval(buf, ptr, rates[4], 2);
		else 					CChgFanc.SetBcdIntval(buf, ptr, 0, 2);			ptr += 2;
		if (rates.length >= 6)	CChgFanc.SetBcdIntval(buf, ptr, rates[5], 2);
		else 					CChgFanc.SetBcdIntval(buf, ptr, 0, 2);			ptr += 2;
		if (rates.length >= 7)	CChgFanc.SetBcdIntval(buf, ptr, rates[6], 2);
		else 					CChgFanc.SetBcdIntval(buf, ptr, 0, 2);			ptr += 2;
		
		CChgFanc.SetBcdIntval(buf, ptr, 0, 3);									ptr += 3;//空出来
		tokenstr = CChgFanc.Bytes2HexString(buf, ptr);

		tokenlist.add(tokenstr);

		///////////
		ptr = 0;
		CChgFanc.SetIntval(buf, ptr, MjlDef.MJLCARDTOKENID_STEPTARIFF4, 1);		ptr += 1;
		CChgFanc.SetBcdIntval(buf, ptr, steps[0], 2);							ptr += 2;
		CChgFanc.SetBcdIntval(buf, ptr, steps[1], 2);							ptr += 2;
		CChgFanc.SetBcdIntval(buf, ptr, steps[2], 2);							ptr += 2;
		CChgFanc.SetBcdIntval(buf, ptr, steps[3], 2);							ptr += 2;
		CChgFanc.SetBcdIntval(buf, ptr, 0, 1);									ptr += 1;//空出来
		
		tokenstr = CChgFanc.Bytes2HexString(buf, ptr);

		tokenlist.add(tokenstr);
		
		///////////
		ptr = 0;
		CChgFanc.SetIntval(buf, ptr, MjlDef.MJLCARDTOKENID_STEPTARIFF5, 1);		ptr += 1;
		if (steps.length >= 5)	CChgFanc.SetBcdIntval(buf, ptr, steps[4], 2);
		else 					CChgFanc.SetBcdIntval(buf, ptr, 0, 2);			ptr += 2;
		if (steps.length >= 6)	CChgFanc.SetBcdIntval(buf, ptr, steps[5], 2);
		else 					CChgFanc.SetBcdIntval(buf, ptr, 0, 2);			ptr += 2;
		
		CChgFanc.SetBcdIntval(buf, ptr, 0, 1);									ptr += 1;//空出来
		CChgFanc.SetBcdIntval(buf, ptr, 0, 4);									ptr += 4;//空出来
		
		tokenstr = CChgFanc.Bytes2HexString(buf, ptr);

		tokenlist.add(tokenstr);

		retstr = ChgXmlStr(errcode, tokenlist);

		return retstr;
	}
	
	//Generate Tou Tariff Token 生成分时电价代码
	public String getTOUTariffToken(String meterNo, String Sgc, int tarrifIndex,int keyVersion, int keyExpiredTime, long keyNo, int seqNo,String activatingDate,  int activatingModel, int validDate, int[] rates, int[] times)
	{
		String retstr = "", tokenstr= "";

		List<String> tokenlist = new ArrayList<String>();

		byte []buf = new byte[10];
		int ptr = 0, errcode = 0;

		CChgFanc.SetIntval(buf, ptr, MjlDef.MJLCARDTOKENID_TOURARIFF, 1);		ptr += 1;
		CChgFanc.SetIntval(buf, ptr, (int)keyNo, 2);							ptr += 2;
		CChgFanc.SetIntval(buf, ptr, seqNo, 1);									ptr += 1;
	
		int idate = Integer.parseInt(activatingDate.substring(0, 4)) * 10000 + 
					Integer.parseInt(activatingDate.substring(5, 7)) * 100 +
					Integer.parseInt(activatingDate.substring(8, 10));

		CChgFanc.SetBcdIntval(buf, ptr, idate, 3);								ptr += 3;
		CChgFanc.SetIntval(buf, ptr, (int)activatingModel, 1);					ptr += 1;
		CChgFanc.SetIntval(buf, ptr, (int)validDate, 1);						ptr += 1;

		CChgFanc.SetIntval(buf, ptr, 0, 1);										ptr += 1;//空出来

		tokenstr = CChgFanc.Bytes2HexString(buf, ptr);

		tokenlist.add(tokenstr);

		///////////
		ptr = 0;
		CChgFanc.SetIntval(buf, ptr, MjlDef.MJLCARDTOKENID_TOURARIFF2, 1);		ptr += 1;
		CChgFanc.SetBcdIntval(buf, ptr, rates[0], 2);							ptr += 2;
		CChgFanc.SetBcdIntval(buf, ptr, rates[1], 2);							ptr += 2;
		CChgFanc.SetBcdIntval(buf, ptr, times[0], 1);							ptr += 1;
		CChgFanc.SetBcdIntval(buf, ptr, times[1], 1);							ptr += 1;
		
		CChgFanc.SetBcdIntval(buf, ptr, 0, 3);									ptr += 3;//空出来

		tokenstr = CChgFanc.Bytes2HexString(buf, ptr);

		tokenlist.add(tokenstr);

		retstr = ChgXmlStr(errcode, tokenlist);

		return retstr;
	}
	
	//Generate Friend Mode Token 生成友好模式代码
	public String getFriendModeToken(String meterNo, String Sgc, int tarrifIndex, int keyVersion, int keyExpiredTime, long keyNo, int seqNo, int friendMode, int[] times, int[] days)
	{
		String retstr = "", tokenstr= "";

		List<String> tokenlist = new ArrayList<String>();

		byte []buf = new byte[10];
		int ptr = 0, errcode = 0;

		CChgFanc.SetIntval(buf, ptr, MjlDef.MJLCARDTOKENID_FRIENDMODE, 1);		ptr += 1;
		CChgFanc.SetIntval(buf, ptr, (int)keyNo, 2);							ptr += 2;
		CChgFanc.SetIntval(buf, ptr, seqNo, 1);									ptr += 1;
		CChgFanc.SetIntval(buf, ptr, (int)friendMode, 1);						ptr += 1;
		CChgFanc.SetBcdIntval(buf, ptr, (int)times[0], 1);						ptr += 1;
		CChgFanc.SetBcdIntval(buf, ptr, (int)times[1], 1);						ptr += 1;

		CChgFanc.SetIntval(buf, ptr, 0, 3);										ptr += 3;//空出来

		tokenstr = CChgFanc.Bytes2HexString(buf, ptr);

		tokenlist.add(tokenstr);

		///////////
		ptr = 0;
		CChgFanc.SetIntval(buf, ptr, MjlDef.MJLCARDTOKENID_FRIENDMODE2, 1);		ptr += 1;
		for (int i = 0; i < 7; i++) {
			CChgFanc.SetBcdIntval(buf, ptr, (int)days[i], 1);					ptr += 1;
		}

		CChgFanc.SetIntval(buf, ptr, (int)0, 2);								ptr += 2;
		tokenstr = CChgFanc.Bytes2HexString(buf, ptr);
		tokenlist.add(tokenstr);
		
		retstr = ChgXmlStr(errcode, tokenlist);

		return retstr;
	}
	
	//Generate Change Meter Mode Token生成改变电表模式代码
	public String getChangeMeterModeToken(String meterNo, String Sgc, 
	int tarrifIndex, int keyVersion, int keyExpiredTime, long keyNo, int seqNo,int mode)
	{
		String retstr = "", tokenstr= "";

		List<String> tokenlist = new ArrayList<String>();

		byte []buf = new byte[10];
		int ptr = 0, errcode = 0;

		CChgFanc.SetIntval(buf, ptr, MjlDef.MJLCARDTOKENID_CHANGEMETERMODE, 1);	ptr += 1;
		CChgFanc.SetIntval(buf, ptr, (int)keyNo, 2);							ptr += 2;
		CChgFanc.SetIntval(buf, ptr, seqNo, 1);									ptr += 1;
		CChgFanc.SetIntval(buf, ptr, (int)mode, 1);								ptr += 1;

		CChgFanc.SetIntval(buf, ptr, 0, 1);										ptr += 1;//空出来

		CChgFanc.SetIntval(buf, ptr, 0, 4);										ptr += 4;//空出来

		tokenstr = CChgFanc.Bytes2HexString(buf, ptr);

		tokenlist.add(tokenstr);
		
		retstr = ChgXmlStr(errcode, tokenlist);
		
		return retstr;
	}
	
	//Generate Set Credit Amount Limit And Overdraw Amount Limit Token生成设置信用额度极限和透支极限代码
	public String getSetCreditAmountLimitOrOverdrawAmountLimitToken(
	String meterNo, String Sgc, int tarrifIndex, int keyVersion, int keyExpiredTime, long keyNo, int seqNo, int amountType, int amountLimit)
	{
		String retstr = "", tokenstr= "";

		List<String> tokenlist = new ArrayList<String>();

		byte []buf = new byte[10];
		int ptr = 0, errcode = 0;

	//	if (amount < 0) errcode = -1;

		CChgFanc.SetIntval(buf, ptr, MjlDef.MJLCARDTOKENID_CREDITOVERLIMIT, 1);	ptr += 1;
		CChgFanc.SetIntval(buf, ptr, (int)keyNo, 2);							ptr += 2;	
		CChgFanc.SetIntval(buf, ptr, seqNo, 1);									ptr += 1;
		CChgFanc.SetIntval(buf, ptr, amountType,1);								ptr += 1;
		CChgFanc.SetBcdIntval(buf, ptr, amountLimit, 4);						ptr += 4;
		CChgFanc.SetIntval(buf, ptr, 0, 1);										ptr += 1;//空出来
		
		tokenstr = CChgFanc.Bytes2HexString(buf, ptr);

		tokenlist.add(tokenstr);

		retstr = ChgXmlStr(errcode, tokenlist);

		return retstr;
	}
	
	//Generate Analysis of Return Credit Token according to Return Credit 根据返回信用生成返回信用分析代码
	public String getReturnToken(String meterNo, String Sgc, int tarrifIndex, int keyVersion, int keyExpiredTime, long keyNo, String Token)
	{
		String retstr = "";
		List<CardItemObj> listblock = new ArrayList<CardItemObj>();
		
		byte[]buf = CChgFanc.hexStringToByte(Token);
		if (buf.length < 10) return retstr;
		int ptr = 0;
		
		String Sdata = Integer.toString(CChgFanc.GetIntval(buf, ptr, 3));		ptr += 3;		//
		CardItemObj item = new CardItemObj("tokenSubClass", Sdata);
		listblock.add(item);
		
		Sdata = Integer.toString(CChgFanc.GetIntval(buf, ptr, 1));					ptr += 1;		//
		item = new CardItemObj("seq", Sdata);
		listblock.add(item);
				
		int MeterState = CChgFanc.GetIntval(buf, ptr, 2);							ptr += 2;		//
		Sdata = "";
		for (int i = 0; i < 10; i++) {
			Sdata += ((MeterState >>i) & 0x01) + ","; 	
		}

		Sdata += ((MeterState >>10) & 0x03) + ",";
		Sdata += ((MeterState >>11) & 0x01);
		item = new CardItemObj("meterStatus", Sdata);
		
		Sdata = Integer.toString(CChgFanc.GetBcdIntval(buf, ptr, 4));					ptr += 4;		//
		item = new CardItemObj("amount", Sdata);
		listblock.add(item);

		retstr = ReturnTokenXml(0, listblock);

		return retstr;
	}
	
	//4) Generate Test Token 生成测试代码
	public String getTestToken (int manufacturingID, int control)
	{
		String retstr = "", tokenstr= "";

		List<String> tokenlist = new ArrayList<String>();

		byte []buf = new byte[10];
		int ptr = 0, errcode = 0;

	//	if (amount < 0) errcode = -1;

		CChgFanc.SetIntval(buf, ptr, MjlDef.MJLCARDTOKENID_TEST, 1);			ptr += 1;
		CChgFanc.SetIntval(buf, ptr, manufacturingID, 2);						ptr += 2;
		CChgFanc.SetIntval(buf, ptr, control, 1);								ptr += 1;

		CChgFanc.SetIntval(buf, ptr, 0, 4);										ptr += 4;//空出来
		CChgFanc.SetIntval(buf, ptr, 0, 2);										ptr += 2;//空出来

		tokenstr = CChgFanc.Bytes2HexString(buf, ptr);

		tokenlist.add(tokenstr);

		retstr = ChgXmlStr(errcode, tokenlist);

		return retstr;
	}
	
	public String encryptSmartCardReturnData(int answerToReset, int binaryPattern, int version, String meterID, String customerID, String utilityID, long sanctionedLoad, int meterType, int lastRechargeAmount, String lastRechargeDate, String lastTransactionID, int cardType,String pwdData, int TokenTotalNumber, String[] tokens)
	{
		String tokenstr= "";

	//	List<String> tokenlist = new ArrayList<String>();

		byte []buf = new byte[1024];
		int ptr = 0;//, errcode = 0;

	//	if (meterID.length() <= 0) errcode = MjlDef.MJLTOKEN_ERR_METERNO;		return retstr;
	//	else if ((cardType != 0X0ABC)||(cardType != 0X0BAC)) errcode = MjlDef.MJLTOKEN_ERR_UNKNOWN;	//NEED ADD OTHER CHECK
		
		//明文区
		CChgFanc.SetIntval(buf, ptr, answerToReset,	4);						ptr += 4;
		CChgFanc.SetIntval(buf, ptr, binaryPattern,	2);						ptr += 2;		//0x0cf0
		CChgFanc.SetIntval(buf, ptr, version,		1);						ptr += 1;		//

		byte[] tmpbyte  = CChgFanc.hexStringToByte(meterID, 20);
		for (int i = 0; i < 10; i++) 		buf[ptr++] = tmpbyte[i];

		tmpbyte  = CChgFanc.hexStringToByte(customerID, 20);
		for (int i = 0; i < 10; i++) 		buf[ptr++] = tmpbyte[i];
				
		tmpbyte  = CChgFanc.hexStringToByte(utilityID, 12);
		for (int i = 0; i < 6; i++) 		buf[ptr++] = tmpbyte[i];
		
		CChgFanc.SetBcdIntval(buf, ptr, (int)(sanctionedLoad /1000000),3);	ptr += 3;		//
		CChgFanc.SetBcdIntval(buf, ptr, (int)sanctionedLoad%1000000,   3);	ptr += 3;		//0-65535 说明为 像hex??????????????????

		CChgFanc.SetIntval(buf, ptr, (int)meterType,	1);					ptr += 1;
		CChgFanc.SetIntval(buf, ptr, 0, 2);									ptr += 2;		//sanctionedLoad 占用六个字节 取值到65535 空出来
		
		
		
		CChgFanc.SetBcdIntval(buf, ptr, (int)lastRechargeAmount,	4);		ptr += 4;
		
		int itmpval = Integer.parseInt(lastRechargeDate);

		CChgFanc.SetBcdIntval(buf, ptr, (int)itmpval,	3);		ptr += 3;

		tmpbyte  = CChgFanc.hexStringToByte(lastTransactionID, 20);
		for (int i = 0; i < 10; i++) 		buf[ptr++] = tmpbyte[i];

	//	CChgFanc.SetIntval(buf, ptr, (int)keyNo, 2);									ptr += 2;	
	//	CChgFanc.SetIntval(buf, ptr, seqNo, 1);											ptr += 1;

		for (int i = 0; i < 41; i++) 		buf[ptr++] = (byte)0xff;					//空白区 38 + 2 + 1

		int crcbegin = ptr;
		CChgFanc.SetIntval(buf, ptr, cardType, 2);						ptr += 2;		
		CChgFanc.SetIntval(buf, ptr, 0xff, 1);								ptr += 1;		//Card Used Flag
		CChgFanc.SetIntval(buf, ptr, TokenTotalNumber, 1);				ptr += 1;
		buf[ptr++]	= 0x00;																//crc 高位
		buf[ptr++]	= CChgFanc.MakeCRC(buf, crcbegin, ptr-1);							//crc 低位

		for (int i = 0; i < 6; i++) 		buf[ptr++] = (byte)0xff;					//空白区 6

		crcbegin = ptr;
		for (int t = 0; t < TokenTotalNumber; t++) 	{
			tmpbyte  = CChgFanc.hexStringToByte(tokens[t], 20);
			for (int i = 0; i < 10; i++) 		buf[ptr++] = tmpbyte[i];
		}
		
		while (ptr < 362) {
			buf[ptr++] = (byte)0xff;		//totken 剩余空间
		}
		
		for (int i = 0; i < 6; i++) 		buf[ptr++] = (byte)0xff;					//空白区 6
		
		tmpbyte  = CChgFanc.hexStringToByte(meterID, 20);
		for (int i = 0; i < 10; i++) 		buf[ptr++] = tmpbyte[i];
		for (int i = 0; i < 10; i++) 		buf[ptr++] = (byte)0xff;					//电表编号(只用10字节，后面10个补0)

		tmpbyte  = CChgFanc.hexStringToByte(customerID, 20);
		for (int i = 0; i < 10; i++) 		buf[ptr++] = tmpbyte[i];
		for (int i = 0; i < 10; i++) 		buf[ptr++] = (byte)0xff;					//用户ID(只用10字节，后面10个补0)
		
		tmpbyte  = CChgFanc.hexStringToByte(utilityID, 12);
		for (int i = 0; i < 6; i++) 		buf[ptr++] = tmpbyte[i];

		buf[ptr++]	= 0x00;																//crc 高位
		buf[ptr++]	= CChgFanc.MakeCRC(buf, crcbegin, ptr-1);							//crc 低位
		
		for (int i = 0; i < 8; i++) 		buf[ptr++] = (byte)0xff;					//空白区 8
		
		while (ptr < 1020) {
			buf[ptr++] = (byte)0xff;		//totken 剩余空间
		}
	
		//先不加密 加密时放开
		byte[] keybyte  = new byte [16];
		
		byte[] meterbyte  = CChgFanc.hexStringToByte(meterID, 16);

		System.arraycopy(meterbyte, 0, keybyte, 0, 8);
		for (int i = 0; i < 8; i++) {
			keybyte[8+i] = (byte)((~meterbyte[i]) & 0xff);
		}

		//加密数据 begin 100 end 420  其中 424开始为反写区  加密数据为8的倍数				
		if (CCrypFanc.tea_cryproc(buf, 100, 420, keybyte, true) == false) return "";

		tokenstr = CChgFanc.Bytes2HexString(buf, 4, 1016);

		return tokenstr;
	}
	
	public String decryptSmartCardReturnData(String meterNo, String sgc, int tariffIndex, int keyVersion, int keyExpiredTime, long keyNo, String encryptedData)
	{
		String retstr = "";//, tokenstr= "";
		List<CardItemObj> listblock1 = new ArrayList<CardItemObj>();
		List<CardItemObj> listblock2 = new ArrayList<CardItemObj>();
		List<CardItemObj> listblock3 = new ArrayList<CardItemObj>();
		
		List<CardItemObj> listblock4 = new ArrayList<CardItemObj>();
		//List<CardItemObj> listblock5 = new ArrayList<CardItemObj>();
		List<CardItemObj> listblock6 = new ArrayList<CardItemObj>();
		List<List<CardItemObj>> listblock7 = new ArrayList<List<CardItemObj>>();
		List<CardItemObj> listblock8 = new ArrayList<CardItemObj>();
		
		byte[]buf = CChgFanc.hexStringToByte(encryptedData);
		if (buf.length < 1024) return retstr;
		int ptr = 0;

		//先不加密 加密时放开
		byte[] keybyte  = new byte [16];
		System.arraycopy(buf, 9, keybyte, 0, 8);		//低8位表号
			
		for (int i = 0; i < 8; i++) {
			keybyte[8+i] = (byte)((~keybyte[i]) & 0xff);
		}
		
		//加密数据 begin 100 end 420  其中 424开始为反写区  加密数据为8的倍数
		if (CCrypFanc.tea_cryproc(buf, 100, 420, keybyte, false) == false) return "";
		int crcbegin = 100, crcend = 104; 
		byte crc1	= CChgFanc.MakeCRC(buf, crcbegin, crcend);
		if (crc1 != buf[crcend+1]) return retstr;

		int carduserflag = CChgFanc.GetIntval(buf, 102, 1);
		if (carduserflag == 0xfa) {
			if (CCrypFanc.tea_cryproc(buf, 424, 1016, keybyte, false) == false) return "";
			else {
				crcbegin = 424; crcend = 459; 
				crc1	= CChgFanc.MakeCRC(buf, crcbegin, crcend);
				if (crc1 != buf[crcend+1]) return retstr;
			}
		}
		else if (carduserflag != 0xff)  return retstr;

		int ival = 0; long lval = 0;
		//String Sdata = Integer.toString(CChgFanc.GetIntval(buf, ptr, 4));	ptr += 4;
		
		String Sdata = Long.toString(CChgFanc.GetLongval(buf, ptr, 4));	ptr += 4;
		CardItemObj item = new CardItemObj("answerToReset", Sdata);	listblock1.add(item);

		ptr += 2;	//Binary Pattern

		Sdata = Integer.toString(CChgFanc.GetIntval(buf, ptr, 1));			ptr += 1;
		item = new CardItemObj("version", Sdata);			listblock1.add(item);

		Sdata = CChgFanc.Bytes2HexString(buf, ptr, 10);						ptr += 10;
		item = new CardItemObj("meterID", Sdata);			listblock1.add(item);

		Sdata = CChgFanc.Bytes2HexString(buf, ptr, 10);						ptr += 10;
		item = new CardItemObj("customerID", Sdata);		listblock1.add(item);

		Sdata = CChgFanc.Bytes2HexString(buf, ptr, 6);						ptr += 6;
		item = new CardItemObj("utilityID", Sdata);			listblock1.add(item);

		lval = CChgFanc.GetBcdIntval(buf, ptr, 3) * 1000000;				ptr += 3;		//
		lval += CChgFanc.GetBcdIntval(buf, ptr, 3);							ptr += 3;		//
		Sdata = Long.toString(lval);
		item = new CardItemObj("sanctionedLoad", Sdata);	listblock1.add(item);			
		
		Sdata = Integer.toString(CChgFanc.GetIntval(buf, ptr, 1));			ptr += 1;
		item = new CardItemObj("meterType", Sdata);			listblock1.add(item);

		ptr += 2;	//Sanctioned Load exceeded 

		Sdata = Integer.toString(CChgFanc.GetBcdIntval(buf, ptr, 4));		ptr += 4;		//
		item = new CardItemObj("lastRechargeAmount", Sdata);listblock1.add(item);

		Sdata = Integer.toString(CChgFanc.GetBcdIntval(buf, ptr, 3));		ptr += 3;		//
		item = new CardItemObj("lastRechargeDate", Sdata);	listblock1.add(item);
		
		Sdata = CChgFanc.Bytes2HexString(buf, ptr, 10);						ptr += 10;
		item = new CardItemObj("lastTransactionID", Sdata);	listblock1.add(item);
		/////////////////////

		ptr = 100;
		Sdata = Integer.toString(CChgFanc.GetIntval(buf, ptr, 2));			ptr += 2;
		item = new CardItemObj("cardType", Sdata);			listblock2.add(item);
		
		Sdata = Integer.toString(CChgFanc.GetIntval(buf, ptr, 1));			ptr += 1;
		item = new CardItemObj("cardUsedFlag", Sdata);		listblock2.add(item);
		
		ival = CChgFanc.GetIntval(buf, ptr, 1);								ptr += 1;
		Sdata = Integer.toString(ival);
		item = new CardItemObj("tokenTotalNumber", Sdata);	listblock2.add(item);
		/////////////////////
		
		ptr = 112;
		item = new CardItemObj("token", "");
		for (int i = 0; i < ival; i++) {
			Sdata = CChgFanc.Bytes2HexString(buf, ptr, 10);					ptr += 10;
			item.value += Sdata + ",";
		}
		if (item.value.length() > 0) {
			item.value = item.value.substring(0, item.value.length() -1);
		}
		listblock3.add(item);
		/////////////////////

		ptr = 368;
		Sdata = CChgFanc.Bytes2HexString(buf, ptr, 10);						ptr += 10;
		item = new CardItemObj("meterID", Sdata);			listblock4.add(item);
		ptr += 10;	// 空余10个
		
		Sdata = CChgFanc.Bytes2HexString(buf, ptr, 10);						ptr += 10;
		item = new CardItemObj("consumerID", Sdata);		listblock4.add(item);
		ptr += 10;	// 空余10个
		
		Sdata = CChgFanc.Bytes2HexString(buf, ptr, 6);						ptr += 6;
		item = new CardItemObj("utilityID", Sdata);			listblock4.add(item);
		/////////////////////
		
		ptr = 424;
		item = new CardItemObj("tokenReturnCode");
		for (int i = 0; i < ival; i++) {
			Sdata = Integer.toString(CChgFanc.GetIntval(buf, ptr, 1));					ptr += 1;
			item.value += Sdata + ",";
		}
		if (item.value.length() > 0) {
			item.value = item.value.substring(0, item.value.length() -1);
		}
		
		listblock6.add(item);
		
		ptr = 449;
		Sdata = CChgFanc.Bytes2HexString(buf, ptr, 6);						ptr += 6;
		item = new CardItemObj("RechargeDateTime", Sdata);			listblock6.add(item);

		Sdata = Integer.toString(CChgFanc.GetBcdIntval(buf, ptr, 4));		ptr += 4;		//
		item = new CardItemObj("RechargeAmount", Sdata);			listblock6.add(item);
		/////////////////////

		for (int i = 0; i < 6; i++ ) {
			listblock7.add(LastMonthProc(buf, i));
		}
		/////////////////////
		
		ptr = 966;
		Sdata = CChgFanc.Bytes2HexString(buf, ptr, 10);						ptr += 10;
		item = new CardItemObj("returnToken", Sdata);		listblock8.add(item);
		
		ival = CChgFanc.GetIntval(buf, ptr, 1);								ptr += 1;
		Sdata = Integer.toString(ival);
		item = new CardItemObj("updateFlag", Sdata);	listblock8.add(item);

		Sdata = Integer.toString(CChgFanc.GetIntval(buf, ptr, 2));			ptr += 2;
		item = new CardItemObj("keyNo", Sdata);			listblock8.add(item);
		
		Sdata = Integer.toString(CChgFanc.GetIntval(buf, ptr, 1));			ptr += 1;
		item = new CardItemObj("seqNo", Sdata);			listblock8.add(item);
		
		retstr = ReadCardXml(listblock1, listblock2, listblock3, listblock4, listblock6, listblock7, listblock8);

		return retstr;
	}
	
	private List LastMonthProc(byte[] buf, int month) {
		List<CardItemObj> LastMonth = new ArrayList<CardItemObj>();
		int ptr = 464 + month * 56;

		String Sdata = CChgFanc.Bytes2HexString(buf, ptr, 6);				ptr += 6;
		CardItemObj item = new CardItemObj("billingDate", Sdata);			LastMonth.add(item);

		Sdata = Integer.toString(CChgFanc.GetBcdIntval(buf, ptr, 4));		ptr += 4;
		item = new CardItemObj("activeEnergyImport", Sdata);				LastMonth.add(item);

		Sdata = Integer.toString(CChgFanc.GetBcdIntval(buf, ptr, 4));		ptr += 4;
		item = new CardItemObj("takaRecharged", Sdata);						LastMonth.add(item);
		
		Sdata = Integer.toString(CChgFanc.GetBcdIntval(buf, ptr, 4));		ptr += 4;
		item = new CardItemObj("takaUsed", Sdata);							LastMonth.add(item);
		
		Sdata = Integer.toString(CChgFanc.GetBcdIntval(buf, ptr, 3));		ptr += 3;
		item = new CardItemObj("activeMaximumPower", Sdata);				LastMonth.add(item);
		
		Sdata = Integer.toString(CChgFanc.GetBcdIntval(buf, ptr, 3));		ptr += 3;
		item = new CardItemObj("reactiveMaximumPower", Sdata);				LastMonth.add(item);
		
		Sdata = Integer.toString(CChgFanc.GetBcdIntval(buf, ptr, 4));		ptr += 4;
		item = new CardItemObj("activeEnergyImportT1", Sdata);				LastMonth.add(item);

		Sdata = Integer.toString(CChgFanc.GetBcdIntval(buf, ptr, 4));		ptr += 4;
		item = new CardItemObj("activeEnergyImportT2", Sdata);				LastMonth.add(item);
		
		Sdata = Integer.toString(CChgFanc.GetBcdIntval(buf, ptr, 4));		ptr += 4;
		item = new CardItemObj("reactiveEnergyImportT1", Sdata);			LastMonth.add(item);
		
		Sdata = Integer.toString(CChgFanc.GetBcdIntval(buf, ptr, 4));		ptr += 4;
		item = new CardItemObj("reactiveEnergyImportT2", Sdata);			LastMonth.add(item);

		Sdata = Integer.toString(CChgFanc.GetBcdIntval(buf, ptr, 4));		ptr += 4;
		item = new CardItemObj("totalChargeT1", Sdata);						LastMonth.add(item);
		
		Sdata = Integer.toString(CChgFanc.GetBcdIntval(buf, ptr, 4));		ptr += 4;
		item = new CardItemObj("totalChargeT2", Sdata);						LastMonth.add(item);
		

		Sdata = Integer.toString(CChgFanc.GetBcdIntval(buf, ptr, 2));		ptr += 2;
		item = new CardItemObj("numberOfPowerFailures", Sdata);				LastMonth.add(item);

		Sdata = Integer.toString(CChgFanc.GetBcdIntval(buf, ptr, 2));		ptr += 2;
		item = new CardItemObj("numberOfSanctionedLoadExceeded", Sdata);	LastMonth.add(item);
		
		Sdata = Integer.toString(CChgFanc.GetBcdIntval(buf, ptr, 2));		ptr += 2;
		item = new CardItemObj("monthAveragePowerFactor", Sdata);			LastMonth.add(item);

		return LastMonth;
	}
	
	private String ChgXmlStr(int errcode, List<String> tokens) {
		
		String retstr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><result><errorCode>" + errcode + "</errorCode><tokens>";		

		for (int i = 0; i < tokens.size(); i ++) {
			retstr += "<token>" + tokens.get(i) + "</token>";  
		}
		
		retstr += "</tokens></result>";
		return retstr;
	}

	private String ReturnTokenXml(int errcode, List<CardItemObj> block1) {
		
		String retstr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><result><errorCode>" + errcode + "</errorCode><tokenData>";		

		for (int i = 0; i < block1.size(); i ++) {
			CardItemObj item = (CardItemObj)block1.get(i);
			retstr += "<" + item.name + ">" + item.value + "</" +item.name + ">";
		}
		
		retstr += "</tokenData></result>";
		return retstr;
	}
	
	private String ReadCardXml(List<CardItemObj> block1, List<CardItemObj> block2, List<CardItemObj> block3, List<CardItemObj> block4, List<CardItemObj> block6, List<List<CardItemObj>> block7, List<CardItemObj> block8 ) {
		
		String retstr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><card>";
			
		retstr += "<block1>";
		for (int i = 0; i < block1.size(); i ++) {
			CardItemObj item = (CardItemObj)block1.get(i);
			retstr += "<" + item.name + ">" + item.value + "</" +item.name + ">";
		}
		retstr += "</block1>";
		////////////////////
		
		retstr += "<block2>";
		for (int i = 0; i < block2.size(); i ++) {
			CardItemObj item = (CardItemObj)block2.get(i);
			retstr += "<" + item.name + ">" + item.value + "</" +item.name + ">";
		}
		retstr += "</block2>";
		////////////////////

		retstr += "<block3>";
		for (int i = 0; i < block3.size(); i ++) {
			CardItemObj item = (CardItemObj)block3.get(i);
			retstr += "<" + item.name + ">" + item.value + "</" +item.name + ">";
		}
		retstr += "</block3>";
		////////////////////
		
		retstr += "<block4>";
		for (int i = 0; i < block4.size(); i ++) {
			CardItemObj item = (CardItemObj)block4.get(i);
			retstr += "<" + item.name + ">" + item.value + "</" +item.name + ">";
		}
		retstr += "</block4>";
		////////////////////
		
		retstr += "<block6>";
		for (int i = 0; i < block6.size(); i ++) {
			CardItemObj item = (CardItemObj)block6.get(i);
			retstr += "<" + item.name + ">" + item.value + "</" +item.name + ">";
		}
		retstr += "</block6>";
		////////////////////		
		
		retstr += "<block7>";
		for (int j = 1; j <= block7.size(); j ++) {
			List<CardItemObj> LastMonth = (List<CardItemObj>)block7.get(j -1);
			retstr += "<LastMonth" + j + ">";
			for (int i = 0; i < LastMonth.size(); i ++) {
				CardItemObj item = (CardItemObj)LastMonth.get(i);
				retstr += "<" + item.name + ">" + item.value + "</" +item.name + ">";	
			}
			retstr += "</LastMonth" + j + ">";
		}
		retstr += "</block7>";
		////////////////////
		
		retstr += "<block8>";
		for (int i = 0; i < block8.size(); i ++) {
			CardItemObj item = (CardItemObj)block8.get(i);
			retstr += "<" + item.name + ">" + item.value + "</" +item.name + ">";
		}
		retstr += "</block8>";
		////////////////////
		retstr += "</card>";
		return retstr;
	}
}
