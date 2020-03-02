package com.kesd.comntpara;


//定义任务操作中各个不同操作的ID
//用于任务的操作、通讯及其他
public class ComntUseropDef {

	//低压
	public static final int YFF_DYOPER_ADDRES		= 400000001;			//低压操作-开户
	public static final int YFF_DYOPER_PAY			= 400000002;			//低压操作-缴费
	public static final int YFF_DYOPER_REPAIR		= 400000003;			//低压操作-补卡
	public static final int YFF_DYOPER_REWRITE		= 400000004;			//低压操作-补写卡
	public static final int YFF_DYOPER_REVER		= 400000005;			//低压操作-冲正
	public static final int YFF_DYOPER_CHANGEMETER	= 400000006;			//低压操作-换表换倍率
	public static final int YFF_DYOPER_CHANGERATE	= 400000007;			//低压操作-换电价
	public static final int YFF_DYOPER_PROTECT		= 400000008;			//低压操作-保电
	public static final int YFF_DYOPER_UDPATESTATE	= 400000009;			//低压操作-强制状态更新
	public static final int YFF_DYOPER_RECALC		= 400000010;			//低压操作-重新计算剩余金额
	public static final int YFF_DYOPER_JSBC			= 400000011;			//低压操作-结算补差
	public static final int YFF_DYOPER_GETREMAIN	= 400000012;			//低压操作-返回余额
	public static final int YFF_DYOPER_REFXDF		= 400000013;			//低压操作-发行电费
	public static final int YFF_DYOPER_REJTREST		= 400000014;			//低压操作-重新阶梯清零
	public static final int YFF_DYOPER_RESETDOC		= 400000015;			//低压操作-更改参数  预付费参数
	public static final int YFF_DYOPER_GPARASTATE	= 400000027;			//低压操作-获得预付费参数及状态
	public static final int YFF_DYOPER_RESTART		= 400000028;			//低压操作-恢复
	public static final int YFF_DYOPER_PAUSE		= 400000029;			//低压操作-暂停
	public static final int YFF_DYOPER_DESTORY		= 400000030;			//低压操作-销户
	
	public static final int YFF_DYOPER_MIS_QUERYPOWER	= 400000031;		//低压操作-MIS查询缴费
	public static final int YFF_DYOPER_MIS_PAY			= 400000032;		//低压操作-MIS缴费记录上传
	public static final int YFF_DYOPER_MIS_REVER		= 400000033;		//低压操作-MIS冲正操作

	public static final int YFF_DYOPER_MIS_CHECKPAY		= 400000034;		//低压操作-MIS检查缴费
	public static final int YFF_DYOPER_MIS_CHECKREVER	= 400000035;		//低压操作-MIS检查冲正

	//高压
	public static final int YFF_GYOPER_ADDCUS		= 400000051;			//高压操作-开户
	public static final int YFF_GYOPER_PAY			= 400000052;			//高压操作-缴费
	public static final int YFF_GYOPER_REPAIR		= 400000053;			//高压操作-补卡
	public static final int YFF_GYOPER_REVER		= 400000055;			//高压操作-冲正
	public static final int YFF_GYOPER_CHANGEMETER	= 400000056;			//高压操作-换表换倍率
	public static final int YFF_GYOPER_CHANGERATE	= 400000057;			//高压操作-换电价
	public static final int YFF_GYOPER_PROTECT		= 400000058;			//高压操作-保电
	public static final int YFF_GYOPER_UDPATESTATE	= 400000059;			//高压操作-强制状态更新
	public static final int YFF_GYOPER_RECALC		= 400000060;			//高压操作-重新计算剩余金额
	public static final int YFF_GYOPER_REJS			= 400000061;			//高压操作-重新结算
	public static final int YFF_GYOPER_GETREMAIN	= 400000062;			//高压操作-返回余额
	public static final int YFF_GYOPER_GETJSBCREMAIN= 400000065;			//高压操作-结算补差返回余额
	public static final int YFF_GYOPER_CHANGPAYADD	= 400000063;			//高压操作-换基本费
	public static final int YFF_GYOPER_GPARASTATE	= 400000077;			//高压操作-获得预付费参数及状态
	public static final int YFF_GYOPER_RESTART		= 400000078;			//高压操作-恢复
	public static final int YFF_GYOPER_PAUSE		= 400000079;			//高压操作-暂停
	public static final int YFF_GYOPER_DESTORY		= 400000080;			//高压操作-销户
	public static final int YFF_GYOPER_RESETDOC		= 400000081;			//高压操作-更改参数  预付费参数
	
	//高压MIS
	public static final int YFF_GYOPER_MIS_QUERYPOWER	= 400000085;		//高压操作-MIS查询缴费
	public static final int YFF_GYOPER_MIS_PAY			= 400000086;		//高压操作-MIS缴费记录上传
	public static final int YFF_GYOPER_MIS_REVER		= 400000087;		//高压操作-MIS冲正操作

	public static final int YFF_GYOPER_MIS_CHECKPAY		= 400000088;		//高压操作-MIS检查缴费
	public static final int YFF_GYOPER_MIS_CHECKREVER	= 400000089;		//高压操作-MIS检查冲正
	
	//高低压MIS对账
	public static final int YFF_GDYOPER_MIS_CHECKPAY	= 400000090;		//高低压操作-MIS对账
	

	//低压通信
	public static final int YFF_DYCOMM_ADDRES		= 400000101;			//低压通讯-开户
	public static final int YFF_DYCOMM_PAY			= 400000102;			//低压通讯-缴费
	public static final int YFF_DYCOMM_CALLPARA		= 400000103;			//低压通讯-召参数
	public static final int YFF_DYCOMM_SETPARA		= 400000104;			//低压通讯-设参数
	public static final int YFF_DYCOMM_CTRL			= 400000105;			//低压通讯-控制
	public static final int YFF_DYCOMM_CHGKEY		= 400000106;			//低压通讯-下装密钥
	
	//20131021添加
	public static final int YFF_DYCOMM_CHGKEY2  	= 400000107;   			//低压通讯-下装密钥2
	public static final int YFF_DYCOMM_REVER   		= 400000108;   			//低压通讯-冲正
	public static final int YFF_DYCOMM_ININT   		= 400000109;   			//低压通讯-初始化
	public static final int YFF_DYCOMM_CLEAR   		= 400000110;   			//低压通讯-清空清空   电量 需量 事项
	

	//高压通信
	public static final int YFF_GYCOMM_PAY			= 400000152;			//高压通讯-缴费
	public static final int YFF_GYCOMM_CALLPARA		= 400000153;			//高压通讯-召参数
	public static final int YFF_GYCOMM_SETPARA		= 400000154;			//高压通讯-设参数
	public static final int YFF_GYCOMM_CTRL			= 400000155;			//高压通讯-控制

	//其他通信参数
	public static final int YFF_READDATA			= 400000201;			//读取数据 不分高低压
	public static final int YFF_CRYPLINK			= 400000202;			//加密机连接请求
	public static final int YFF_CRYPRESULT			= 400000203;			//加密机连接返回结果

	public static final int YFF_GET_PAUSEALARM		= 400000220;			//得到-暂停预付费报警及控制
	public static final int YFF_SET_PAUSEALARM		= 400000221;			//设置-暂停预付费报警及控制
	public static final int YFF_GET_GLOPROT			= 400000222;			//得到-全局保电参数
	public static final int YFF_SET_GLOPROT			= 400000223;			//设置-全局保电参数
	
	//农排
	public static final int YFF_NPOPER_ADDRES		= 400000301;			//农排操作-开户
	public static final int YFF_NPOPER_PAY			= 400000302;			//农排操作-缴费
	public static final int YFF_NPOPER_REPAIR		= 400000303;			//农排操作-补卡
	public static final int YFF_NPOPER_REWRITE		= 400000304;			//农排操作-补写卡
	public static final int YFF_NPOPER_REVER		= 400000305;			//农排操作-冲正
	public static final int YFF_NPOPER_UDPATESTATE	= 400000306;			//农排操作-强制状态更新
	public static final int YFF_NPOPER_GPARASTATE	= 400000307;			//农排操作-获得预付费参数及状态
	public static final int YFF_NPOPER_DESTORY		= 400000308;			//农排操作-销户
	public static final int YFF_NPOPER_RESETDOC		= 400000320;			//农排操作-更改参数  预付费参数
	public static final int YFF_NPOPER_CALLBASE		= 400000321;			//农排操作-召测基本数据
	public static final int YFF_NPOPER_SETBASE		= 400000322;			//农排操作-设置节本数据
	public static final int YFF_NPOPER_BL_PARA		= 400000323;			//农排操作-黑名单
	public static final int YFF_NPOPER_WL_PARA		= 400000324;			//农排操作-白名单
	
	public static final int YFF_NPOPER_WriteDB		= 400000325;			//农排操作-用电记录存库
	public static final int YFF_NPGQOPER_WriteDB		= 400000326;			//农排操作-挂起用户记录存库

	
	public static class OP_DESC {
		public String str;
		public int    id;
		
		public OP_DESC(String str, int id) {
			this.str = str;
			this.id  = id;
		}
	}
	
	public static final OP_DESC [] op_desc = {
		//低压
		new OP_DESC("YFF_DYOPER_ADDRES",		YFF_DYOPER_ADDRES),
		new OP_DESC("YFF_DYOPER_PAY",			YFF_DYOPER_PAY),
		new OP_DESC("YFF_DYOPER_REPAIR",		YFF_DYOPER_REPAIR),
		new OP_DESC("YFF_DYOPER_REWRITE",		YFF_DYOPER_REWRITE),
		new OP_DESC("YFF_DYOPER_REVER",			YFF_DYOPER_REVER),
		new OP_DESC("YFF_DYOPER_CHANGEMETER",	YFF_DYOPER_CHANGEMETER),
		new OP_DESC("YFF_DYOPER_CHANGERATE",	YFF_DYOPER_CHANGERATE),
		new OP_DESC("YFF_DYOPER_PROTECT",		YFF_DYOPER_PROTECT),
		new OP_DESC("YFF_DYOPER_UDPATESTATE",	YFF_DYOPER_UDPATESTATE),
		new OP_DESC("YFF_DYOPER_RECALC",		YFF_DYOPER_RECALC),
		new OP_DESC("YFF_DYOPER_JSBC",			YFF_DYOPER_JSBC),
		new OP_DESC("YFF_DYOPER_GETREMAIN",		YFF_DYOPER_GETREMAIN),
		new OP_DESC("YFF_DYOPER_GPARASTATE",	YFF_DYOPER_GPARASTATE),
		new OP_DESC("YFF_DYOPER_RESTART",		YFF_DYOPER_RESTART),
		new OP_DESC("YFF_DYOPER_PAUSE",			YFF_DYOPER_PAUSE),
		new OP_DESC("YFF_DYOPER_DESTORY",		YFF_DYOPER_DESTORY),

		//高压
		new OP_DESC("YFF_GYOPER_ADDCUS",		YFF_GYOPER_ADDCUS),		
		new OP_DESC("YFF_GYOPER_PAY",			YFF_GYOPER_PAY),			
		new OP_DESC("YFF_GYOPER_REVER",			YFF_GYOPER_REVER),		
		new OP_DESC("YFF_GYOPER_CHANGEMETER",	YFF_GYOPER_CHANGEMETER),	
		new OP_DESC("YFF_GYOPER_CHANGERATE",	YFF_GYOPER_CHANGERATE),	
		new OP_DESC("YFF_GYOPER_PROTECT",		YFF_GYOPER_PROTECT),		
		new OP_DESC("YFF_GYOPER_UDPATESTATE",	YFF_GYOPER_UDPATESTATE),	
		new OP_DESC("YFF_GYOPER_RECALC",		YFF_GYOPER_RECALC),		
		new OP_DESC("YFF_GYOPER_REJS",			YFF_GYOPER_REJS),			
		new OP_DESC("YFF_GYOPER_GETREMAIN",		YFF_GYOPER_GETREMAIN),	
		new OP_DESC("YFF_GYOPER_GETJSBCREMAIN",	YFF_GYOPER_GETJSBCREMAIN),	
		new OP_DESC("YFF_GYOPER_CHANGPAYADD",	YFF_GYOPER_CHANGPAYADD),	
		new OP_DESC("YFF_GYOPER_GPARASTATE",	YFF_GYOPER_GPARASTATE),	
		new OP_DESC("YFF_GYOPER_RESTART",		YFF_GYOPER_RESTART),		
		new OP_DESC("YFF_GYOPER_PAUSE",			YFF_GYOPER_PAUSE),		
		new OP_DESC("YFF_GYOPER_DESTORY",	   	YFF_GYOPER_DESTORY),	 

		//低压通信
		new OP_DESC("YFF_DYCOMM_ADDRES",		YFF_DYCOMM_ADDRES),
		new OP_DESC("YFF_DYCOMM_PAY",			YFF_DYCOMM_PAY),	
		new OP_DESC("YFF_DYCOMM_CALLPARA",		YFF_DYCOMM_CALLPARA),
		new OP_DESC("YFF_DYCOMM_SETPARA",		YFF_DYCOMM_SETPARA),
		new OP_DESC("YFF_DYCOMM_CTRL",			YFF_DYCOMM_CTRL),	
		new OP_DESC("YFF_DYCOMM_CHGKEY",		YFF_DYCOMM_CHGKEY),
		
		//20131021添加
		new OP_DESC("YFF_DYCOMM_CHGKEY2",		YFF_DYCOMM_CHGKEY2),
		new OP_DESC("YFF_DYCOMM_REVER",			YFF_DYCOMM_REVER),
		new OP_DESC("YFF_DYCOMM_ININT",			YFF_DYCOMM_ININT),
		new OP_DESC("YFF_DYCOMM_CLEAR",			YFF_DYCOMM_CLEAR),
		//end

		//高压通信
		new OP_DESC("YFF_GYCOMM_PAY",			YFF_GYCOMM_PAY),	
		new OP_DESC("YFF_GYCOMM_CALLPARA",		YFF_GYCOMM_CALLPARA),
		new OP_DESC("YFF_GYCOMM_SETPARA",		YFF_GYCOMM_SETPARA),
		new OP_DESC("YFF_GYCOMM_CTRL",			YFF_GYCOMM_CTRL),	

		//其他通信参数
		new OP_DESC("YFF_READDATA",				YFF_READDATA),	
		new OP_DESC("YFF_CRYPLINK",				YFF_CRYPLINK),	
		new OP_DESC("YFF_CRYPRESULT",			YFF_CRYPRESULT),	

		new OP_DESC("YFF_GET_PAUSEALARM",		YFF_GET_PAUSEALARM),
		new OP_DESC("YFF_SET_PAUSEALARM",		YFF_SET_PAUSEALARM),
		new OP_DESC("YFF_GET_GLOPROT",			YFF_GET_GLOPROT),	
		new OP_DESC("YFF_SET_GLOPROT",	   		YFF_SET_GLOPROT),	   
	};
}
