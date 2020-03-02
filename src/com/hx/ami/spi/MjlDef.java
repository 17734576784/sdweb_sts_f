package com.hx.ami.spi;

public class MjlDef {
	
	public static final byte	MJLCARDTOKENID_CREDIT			=	1;		//getCreditToken(缴费)
	public static final byte	MJLCARDTOKENID_CHANGEKEY		=	2;		//getKeyChangeToken（更改秘钥）
	public static final byte	MJLCARDTOKENID_BALANCE			=	3;		//getClearBalanceToken(清除余额)
	public static final byte	MJLCARDTOKENID_EVENT			=	4;		//getClearEventToken(清除事件)
	public static final byte	MJLCARDTOKENID_MAXPOWLIMIT		=	5;		//getMaxPowerLimitToken(设置最大功率极)
	public static final byte	MJLCARDTOKENID_MAXPOWLIMIT2		=	6;		//getMaxPowerLimitToken(设置最大功率极)_2
	public static final byte	MJLCARDTOKENID_SINGLETARIFF		=	7;		//getSingleTariffToken(设置单费率)
	public static final byte	MJLCARDTOKENID_SINGLETARIFF2	=	8;		//getSingleTariffToken(设置单费率)_2
	public static final byte	MJLCARDTOKENID_STEPTARIFF		=	9;		//getStepTariffToken(设置阶梯费率)
	public static final byte	MJLCARDTOKENID_STEPTARIFF2		=	10;		//getStepTariffToken(设置阶梯费率)_2
	public static final byte	MJLCARDTOKENID_STEPTARIFF3		=	11;		//getStepTariffToken(设置阶梯费率)_3
	public static final byte	MJLCARDTOKENID_STEPTARIFF4		=	12;		//getStepTariffToken(设置阶梯费率)_4
	public static final byte	MJLCARDTOKENID_STEPTARIFF5		=	13;		//getStepTariffToken(设置阶梯费率)_5
	public static final byte	MJLCARDTOKENID_TOURARIFF		=	14;		//getTOUTariffToken(设置复费率)
	public static final byte	MJLCARDTOKENID_TOURARIFF2		=	15;		//getTOUTariffToken(设置复费率)_2
	public static final byte	MJLCARDTOKENID_FRIENDMODE		=	16;		//getFriendModeToken(设置友好模式)
	public static final byte	MJLCARDTOKENID_FRIENDMODE2		=	17;		//getFriendModeToken(设置友好模式)_2
	public static final byte	MJLCARDTOKENID_CHANGEMETERMODE  =	18;		//getChangeMeterModeToken（转换电表模式）
	public static final byte	MJLCARDTOKENID_CREDITOVERLIMIT	=	19;		//getSetCreditAmountLimitAndOverdrawAmountLimit设置信用额度极限和透支额度极限"
	public static final byte	MJLCARDTOKENID_GETRETURN		=	20;		//getReturnToken
	public static final byte	MJLCARDTOKENID_TEST				=	24;		//getTestToken（测试卡)

	
	
	public static final int		MJLTOKEN_ERR_SECCESS			= 	0;		//or empty success成功
	public static final int		MJLTOKEN_ERR_SEQERR				=   1;		//sequence number is not excpected 序列号不被接受
	public static final int		MJLTOKEN_ERR_TARIFFINDEX		=   2; 		//tariff Index argument error, not in scope 费率指数错误，不在范围内
	public static final int		MJLTOKEN_ERR_KEYVER				= 	3; 		//key Version argument not in scope 密钥版本，不在范围内
	public static final int		MJLTOKEN_ERR_KEYEXPTIME			=   4;		//key Expired Time not in scope(0-255) 密钥到期时间，不在范围内（0-255）
	public static final int		MJLTOKEN_ERR_KEYNO				= 	5; 		//key No exceed 65535 or < 0 密钥号 超出65535或《0
	public static final int		MJLTOKEN_ERR_METERNO			=   6;		//meter No is not excepted 电表编号不被接受
	public static final int		MJLTOKEN_ERR_CREDITERR			=   7;		//credit amount <= 0 信用金额<= 0
	public static final int		MJLTOKEN_ERR_UNKNOWN			=   8;		// unknown reason 未知原因


		
}
