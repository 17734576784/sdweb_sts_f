package com.hx.ami.spi;

public interface Token {	//

	public String getCreditToken(String meterNo, String sgc, int tarrifIndex, int keyVersion, int keyExpiredTime, long keyNo, int seqNo, int amount);
	public String getKeyChangeToken (String meterNo, String sgc, int tarrifIndex, int keyVersion, int keyExpiredTime, long keyNo, String nSgc, int nTariffIndex, int nKeyVersion,int nKeyExpiredTime, long nKeyNo);
	
	public String getClearBalanceToken (String meterNo, String Sgc, int tarrifIndex, int keyVersion, int keyExpiredTime, long keyNo, int seqNo);
	//Generate clear event Token 生成清除时间代码
	public String getClearEventToken (String meterNo, String Sgc, int tarrifIndex, int keyVersion, int keyExpiredTime, long keyNo, int seqNo);
	
	//Generate max Power Limit Mode Token 生成最大功率极限模式代码
	public String getMaxPowerLimitToken(String meterNo,String Sgc, int tarrifIndex, int keyVersion, int keyExpiredTime, long keyNo, int seqNo, int activationModel, String date,int[] maxPowerLimits, int[] hours);
	
	//Generate single Tariff Token生成单一费率代码
	public String getSingleTariffToken(String meterNo, String Sgc, int tarrifIndex,int keyVersion, int keyExpiredTime, long keyNo, int seqNo,String activatingDate,  int activatingModel, int validDate, int rate);
	//Generate step Tariff Token 生成阶梯电价代码
	public String getStepTariffToken(String meterNo, String Sgc, int tarrifIndex,int keyVersion, int keyExpiredTime, long keyNo, int seqNo,String activatingDate, int activatingModel, int validDate, int[] rates, int[] steps);
	//Generate Tou Tariff Token 生成分时电价代码
	public String getTOUTariffToken(String meterNo, String Sgc, int tarrifIndex,int keyVersion, int keyExpiredTime, long keyNo, int seqNo,String activatingDate,  int activatingModel, int validDate, int[] rates, int[] times);
	//Generate Friend Mode Token 生成友好模式代码
	public String getFriendModeToken(String meterNo, String Sgc, int tarrifIndex, int keyVersion, int keyExpiredTime, long keyNo, int seqNo, int friendMode, int[] times, int[] days);
	
	//Generate Change Meter Mode Token生成改变电表模式代码
	public String getChangeMeterModeToken(String meterNo, String Sgc, 
	int tarrifIndex, int keyVersion, int keyExpiredTime, long keyNo, int seqNo,int mode);
	
	//Generate Set Credit Amount Limit And Overdraw Amount Limit Token生成设置信用额度极限和透支极限代码
	public String getSetCreditAmountLimitOrOverdrawAmountLimitToken(
	String meterNo, String Sgc, int tarrifIndex, int keyVersion, int keyExpiredTime, long keyNo, int seqNo, int amountType, int amountLimit);
		
	//Generate Analysis of Return Credit Token according to Return Credit 根据返回信用生成返回信用分析代码
	public String getReturnToken(String meterNo, String Sgc, int tarrifIndex, int keyVersion, int keyExpiredTime, long keyNo, String Token);
		
	//4) Generate Test Token 生成测试代码
	public String getTestToken (int manufacturingID, int control);
	
}
