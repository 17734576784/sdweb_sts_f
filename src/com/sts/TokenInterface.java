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

public interface TokenInterface {
	
	public String getToken_TransferCredit(byte subcls, byte rnd, long tid,
			int val, byte[] keydata);

	public String getToken_InitIateMeterTestOrDisplay(byte subcls, long ctrl,
			String mfrcode, byte[] keydata);

	public String getToken_SetMaximumPowerLimit(byte subcls, byte rnd,
			long tid, int val, byte[] keydata);

	public String getToken_ClearCredit(byte subcls, byte rnd, long tid,
			int val, byte[] keydata);

	public String getToken_SetTariffRate(byte subcls, byte rnd, long tid,
			int val, byte[] keydata);

	public String getToken_Set1stSectionDecoderKey(byte subcls, byte kenho,
			byte krn, byte ro, byte res, byte kt, long nkho, byte[] keydata);

	public String getToken_Set2ndSectionDecoderKey(byte subcls, byte kenlo,
			byte ti, long nklo, byte[] keydata);

	public String getToken_ClearTamperCondition(byte subcls, byte rnd,
			long tid, int val, byte[] keydata);

	public String getToken_SetMaximumPhasePowerUnbalanceLimit(byte subcls,
			byte rnd, long tid, int val, byte[] keydata);

	public String getToken_SetWaterMeterFactor(byte subcls, byte rnd, long tid,
			int val, byte[] keydata);

	public String getToken_StsReserved(byte subcls, byte rnd, long tid,
			int val, byte[] keydata);

	public String getToken_ProprietaryReserved(byte subcls, byte rnd, long tid,
			int val, byte[] keydata);
}