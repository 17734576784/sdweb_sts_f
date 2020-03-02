package com.hx.ami.spi;

public interface SmartCard {
	public String encryptSmartCardReturnData(int answerToReset, int binaryPattern, int version, String meterID, String customerID, String utilityID, long sanctionedLoad, int meterType, int lastRechargeAmount, String lastRechargeDate, String lastTransactionID, int cardType,String pwdData, int TokenTotalNumber, String[] tokens);
	
	public String decryptSmartCardReturnData(String meterNo, String sgc, int tariffIndex, int keyVersion, int keyExpiredTime, long keyNo, String encryptedData);

}
