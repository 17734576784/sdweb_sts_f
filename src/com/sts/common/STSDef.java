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

public class STSDef {
	public static final int  STS_SUCC					= 1;
	public static final int  STS_FAIL					= 0;
	
	public static final byte STS_TOKEN_CLS_0			= 0;
	public static final byte STS_TOKEN_CLS_1			= 1;
	public static final byte STS_TOKEN_CLS_2			= 2;
	public static final byte STS_TOKEN_CLS_3			= 3;
	
	public static final byte STS_CLS0_SUBCLS_ELEC		= 0;
	public static final byte STS_CLS0_SUBCLS_WATER		= 1;
	public static final byte STS_CLS0_SUBCLS_GAS		= 2;
	public static final byte STS_CLS0_SUBCLS_TIME		= 3;
	public static final byte STS_CLS0_SUBCLS_CURRENCY	= 4;
	
	public static final byte STS_CLS1_SUBCLS_TEST		= 0;
	
	public static final byte STS_CLS2_SUBCLS_SET_MPL	= 0;
	public static final byte STS_CLS2_SUBCLS_CLEARCREDIT= 1;
	public static final byte STS_CLS2_SUBCLS_SET_TR		= 2;
	public static final byte STS_CLS2_SUBCLS_SET_1SDK	= 3;
	public static final byte STS_CLS2_SUBCLS_SET_2SDK	= 4;
	public static final byte STS_CLS2_SUBCLS_CLR_TAMPER	= 5;
	public static final byte STS_CLS2_SUBCLS_SET_MPPUL	= 6;
	public static final byte STS_CLS2_SUBCLS_SET_WMFACTOR	= 7;
	public static final byte STS_CLS2_SUBCLS_STS_RESERVED	= 8;
	public static final byte STS_CLS2_SUBCLS_OTH_RESERVED	= 11;
	
	public static final int  STS_REGCLEAR_ELEC			= 0;
	public static final int  STS_REGCLEAR_WATER			= 1;
	public static final int  STS_REGCLEAR_GAS			= 2;
	public static final int  STS_REGCLEAR_TIME			= 3;
	public static final int  STS_REGCLEAR_CURRENCY		= 4;
	public static final int  STS_REGCLEAR_ALL			= 0xFFFF;
	
	//Control: InitiateMeterTest/DisplayControlField
	//Control域值定义
	public static final int  STS_MTEST_BIT_ALLTEST		= 0;		// 所有BIT位置1
	public static final int  STS_MTEST_BIT_LOADSWITCH	= 1;
	public static final int  STS_MTEST_BIT_DISPLAY		= 2;
	public static final int  STS_MTEST_BIT_CUMULATIVE	= 3;
	public static final int  STS_MTEST_BIT_KRN			= 4;
	public static final int  STS_MTEST_BIT_TI			= 5;
	public static final int  STS_MTEST_BIT_TOKENREADER	= 6;
	public static final int  STS_MTEST_BIT_MAXPOWLIMIT	= 7;
	public static final int  STS_MTEST_BIT_TAMPERSTATUS	= 8;
	public static final int  STS_MTEST_BIT_POWCONS		= 9;
	public static final int  STS_MTEST_BIT_SOFTWAREVER	= 10;
	public static final int  STS_MTEST_BIT_PHASEPOWIMB	= 11;
	public static final int  STS_MTEST_BIT_WATERFACTOR	= 12;
	public static final int  STS_MTEST_BIT_TARIFFRATE	= 13;
	public static final int  STS_MTEST_BIT_RESERVED		= 14;
	
	public static final int  STS_BASEDATE_1993			= 1;
	public static final int  STS_BASEDATE_2014			= 2;
	public static final int  STS_BASEDATE_2035			= 3;
	
	//Token加密方式
	public static final byte STS_ET_SOFT				= 0;	//软件加密
	public static final byte STS_ET_HARDWARE			= 1;	//串口加密
	
	public static final String STS_IIN_SHORTADDR		= "600727";
	public static final String STS_IIN_LONGADDR			= "0000";
	
	public static final String STS_STR_EMPTY			= "";
	
	//sts操作类型
	public static final byte STS_TOOL_SETMAXIMUMPOWERLIMIT = 0;
	public static final byte STS_TOOL_CLEARCREDIT = 1;
	public static final byte STS_TOOL_SETTARIFFRATE = 2;
	public static final byte STS_TOOL_SET1STSECTIONDECODERKey = 3;
	public static final byte STS_TOOL_SET2STSECTIONDECODERKey = 4;
	public static final byte STS_TOOL_CLEARTAMPERCONDITION = 5;
	public static final byte STS_TOOL_SETMAXIMUMPHASEPOWERUNABLANCELIMIT = 6;
	public static final byte STS_TOOL_SETWATERMETERFACTOR = 7;
	public static final byte STS_TOOL_RESERVEDSTSUSE = 8;
	public static final byte STS_TOOL_RESERVEDPROPRIETARYUSE = 11;
	
	//token 返回类型
	public static final String STS_TOKEN_EXPIRED		= "STS Token Expired";
	public static final String STS_TOKEN_ERROR			= "STS Token Error";
	public static final String STS_KEYCHANGE			= "keychange";			//密钥参数修改标志
	public static final String STS_METERKEY_ERROR		= "MeterKey Error";
	
}