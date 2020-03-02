/********************************************************************************************************
*                                        用电WEB Ver2.0													*
*																										*
*                           (c) Copyright 2010~,   KLD Automation Co., Ltd.								*
*                                           All Rights Reserved											*
*																										*
*	FileName	:	Dict.java																			*
*	Description	:	数据字典																				*
*	Author		:																						*
*	Date		:	2011/03/08																			*
*																										*
*   No.         Date         Modifier        Description												*
*   -----------------------------------------------------------------------------------------------     *
*********************************************************************************************************/
package com.kesd.common;

public class Dict {
	
	public static final String	DICTITEM_DATATYPE 		= "数据类型_-1";
	public static final String	DICTITEM_CHANGETYPE 	= "换表换CT类型_-1";
	public static final String	DICTITEM_TASKOBJECTTYPE = "任务对象类型_-1";
	public static final String	DICTITEM_CHGMETE		= "换表类型_-1";
	public static final String	DICTITEM_METERTYPE 		= "电表类型_-1";
	public static final String	DICTITEM_TASKRECALL 	= "补召策略_1";
	public static final String	DICTITEM_TIMES 			= "次数_2";
	public static final String	DICTITEM_TASKTYPE 		= "任务类型_4";
	public static final String	DICTITEM_USEFLAG 		= "使用标志_7";
	public static final String	DICTITEM_CONTROL_TYPE	= "控制类型_9";
	public static final String	DICTITEM_MAINMETFLAG	= "主副表标志_11";
	public static final String	DICTITEM_DECNUMBER		= "小数位个数_12";
	public static final String	DICTITEM_INTNUMBER		= "整数位个数_13";
	public static final String	DICTITEM_BDZMPTYPE 		= "测点类型_14";
	public static final String	DICTITEM_INSTSITE		= "安装地点_16";
	public static final String	DICTITEM_RUNSTATUS		= "运行状态_17";
	public static final String	DICTITEM_TRMODEL		= "变压器类型_18";
	public static final String	DICTITEM_LINETYPE		= "线路类型_19";
	public static final String	DICTITEM_VOLTGRADE 		= "电压等级_20";
	public static final String	DICTITEM_POWERTYPE		= "电源类型_22";
	public static final String	DICTITEM_ENCRYPTCOMM	= "通信加密_24";
	public static final String	DICTITEM_WIRINGMODE		= "接线方式_25";
	public static final String	DICTITEM_JXFS 			= "接线方式_25";
	public static final String	DICTITEM_CHANTYPE 		= "通道类型_26";
	public static final String	DICTITEM_YESFLAG 		= "是否标志_27";
	public static final String	DICTITEM_ADDFLAG 		= "总加运算标志_28";
	public static final String	DICTITEM_ADDDIRECTION	= "总加方向_29";
	public static final String	DICTITEM_BAUDTYPE 		= "波特率_30";
	public static final String	DICTITEM_DATABITTYPE 	= "数据位_31";
	public static final String	DICTITEM_CHECKTYPE 		= "校验位_32";
	public static final String	DICTITEM_STOPTYPE 		= "停止位_33";
	public static final String	DICTITEM_FLOWCHECK 		= "流控制_34";
	public static final String	DICTITEM_RTUCHANTYPE 	= "终端通道类型_35";
	public static final String	DICTITEM_CHANPORT 		= "串口号_36";
	public static final String	DICTITEM_SIMCARDTYPE	= "SIM卡类型_38";
	public static final String	DICTITEM_TRADE 			= "所属行业_37";
	public static final String	DICTITEM_RTUFACTORY		= "终端生产厂家_39";
	public static final String	DICTITEM_FACTORY	    = "电表生产厂家_40";
	public static final String	DICTITEM_METERFACTORY 	= "电表生产厂家_40";
	public static final String	DICTITEM_METERPROT 		= "电表通讯规约_41";
	public static final String	DICTITEM_METERACCURACY  = "电表精度_42";
	public static final String	DICTITEM_PREPAYTYPE		= "费控类型_43";
	public static final String	DICTITEM_RTUUPCOMMTYPE 	= "终端上行通信模式_44";
	public static final String	DICTITEM_FLNUMBER 		= "费率个数_45";
	public static final String	DICTITEM_RASCHECKTYPE	= "RAS监测类型_46";
	public static final String	DICTITEM_FUKONGRIGHT 	= "权限范围_49";
	public static final String	DICTITEM_MPTYPE			= "计量点类型_52";
	public static final String	DICTITEM_FEETYPE		= "计费方式_53";
	public static final String	DICTITEM_YFFYCKZ		= "智能表远程控制_56";
	public static final String	DICTITEM_PROTOBJECTTYPE = "规约对象类型_60";
	public static final String	DICTITEM_PROTDATATYPE	= "规约数据类型_61";
	public static final String	DICTITEM_RESIDENTUSERTYPE = "居民用户类型_62";
	public static final String	DICTITEM_ADDRJZ			= "地址录入进制_63";
	public static final String	DICTITEM_TASKREPORT		= "任务主动上报_64";
	public static final String	DICTITEM_SUPPORTTYPE	= "支持类型_65";
	public static final String	DICTITEM_RTUATTRTYPE 	= "终端使用属性_67";
	public static final String	DICTITEM_CHANPORT376 	= "通讯端口376_69";
	public static final String	DICTITEM_REPORTTYPE 	= "日数据上报类型_70";
	public static final String	DICTITEM_NETONLINE 		= "无线网络在线方式_72";
	public static final String	DICTITEM_SETFLAG 		= "设置标志_74";
	public static final String	DICTITEM_LINEATTR 		= "线路使用属性_76";
	public static final String	DICTITEM_MONITORFLAG 	= "监测类型_77";
	public static final String	DICTITEM_PRIORITY 		= "任务优先级别_78";
	public static final String	DICTITEM_WGFLAG			= "无功标志_79";
	public static final String	DICTITEM_MAXRUNshortER	= "最大回溯时间_82";
	public static final String	DICTITEM_XSPHTYPE 		= "线损及平衡类型_83";
	public static final String	DICTITEM_ALARMZFTYPE 	= "报警转发类型_84";
	public static final String	DICTITEM_XSPHITEMTYPE 	= "母线平衡及线损分项类型_85";
	public static final String	DICTITEM_CHGMETEBEILV	= "换表换倍率类型_86";
	public static final String	DICTITEM_METERCOE		= "电表系数_87";
	public static final String	DICTITEM_PROTECTTYPE 	= "保护类型_88";
	public static final String	DICTITEM_RTUPOLLID		= "受控标志_90";
	public static final String	DICTITEM_SUBSTAMPTYPE	= "计量点方案数据类型_101";
	public static final String	DICTITEM_ZDLMP 			= "总电量计量点_102";
	public static final String	DICTITEM_FLDLMP 		= "费率电量计量点_103";
	public static final String	DICTITEM_SSLMP 			= "瞬时量计量点_104";
	public static final String	DICTITEM_XLMP 			= "需量计量点_105";
	public static final String	DICTITEM_DLMINDL		= "DL719分钟电量_106";
	public static final String	DICTITEM_DLMINQX 		= "DL719分钟曲线_107";
	public static final String	DICTITEM_DLDATATC 		= "DL719数据透传_108";
	public static final String	DICTITEM_WGTYPE 		= "无功方案_110";
	public static final String	DICTITEM_DL719BUF 		= "DL719BUF_111";
	public static final String	DICTITEM_BUFFER 		= "积分周期_112";
	public static final String	DICTITEM_STARTUPFLAG	= "启用标志_113";
	public static final String	DICTITEM_RELAYTYPE		= "中继方式_118";
	public static final String	DICTITEM_CBTIMES	 	= "晓程抄表次数_124";
	public static final String	DICTITEM_ALARMDELAYTIME = "告警延时时间_155";
	public static final String	DICTITEM_LIMITPOWERTIME = "限电时间_156";
	public static final String	DICTITEM_HOLDPOWERTIME  = "保电持续时间_157";
	public static final String	DICTITEM_NOCOMMTIME  	= "无通讯时间_158";
	public static final String	DICTITEM_CSSTAND		= "功率因数标准_161";
	public static final String	DICTITEM_POWERADDFLAG	= "功率计算参与标志_162";
	public static final String	DICTITEM_PAYTYPE 		= "缴费方式_163";
	public static final String	DICTITEM_RTUUPCOMMTYPE05FK = "终端上行通信模式05fk_164";
	public static final String	DICTITEM_KE485WORKTYPE	= "KE485工作方式_165";
	public static final String	DICTITEM_KEBDZPRROTOCOL	= "KE变电站通讯规约_166";
	public static final String	DICTITEM_MAINMPFLAG		= "主测点表标志_168";
	public static final String	DICTITEM_POWER_USETYPE  = "用电类别_170";
	public static final String	DICTITEM_EXECFLAG  		= "执行标志_171";
	public static final String	DICTITEM_PRIZEFLAG  	= "力调奖罚标志_172";
	public static final String	DICTITEM_STOPFLAG  		= "报停标志_173";
	public static final String	DICTITEM_TASKDATATYPE	= "任务数据类型_500";
	public static final String	DICTITEM_TASKEXECCYCLETYPE= "任务执行周期_501";
	public static final String	DICTITEM_TASKEXECTYPE	= "任务执行类型_502";
	public static final String	DICTITEM_ENDTIMETYPE	= "任务执行截止时间类型_505";
	public static final String	DICTITEM_TASKOBJECTAREA = "任务对象范围_508";
	public static final String	DICTITEM_EVENT_SYS   	= "系统事项_600";
	public static final String	DICTITEM_EVENT_RTU   	= "终端事项_601";
	public static final String	DICTITEM_METERTYPE_KEDYH = "科林大用户表类型_620";
	public static final String	DICTITEM_METERTYPE_KEBDZ = "科林变电站表类型_621";
	public static final String	DICTITEM_METERTYPE_XC	= "晓程电表类型 _622";
	public static final String	DICTITEM_FREEZEUINT		= "分钟冻结间隔_701";
	public static final String	DICTITEM_PERIODUINT		= "召测周期间隔_702";
	public static final String	DICTITEM_CBshortERVALS = "晓程抄表间隔_703";
	public static final String	DICTITEM_CYCshortE     = "存储周期_707";
	public static final String	DICTITEM_CBshortERVALS_GB	= "国电抄表间隔_708";
	/**************终端通讯协议**************/
	public static final String	DICTITEM_RTUPROTTYPE	= "终端通讯协议_720";
	public static final String	DICTITEM_BDZRTUPROTTYPE = "变电站终端通讯协议_721";
	public static final String	DICTITEM_ZGRTUPROTTYPE  = "专变公变终端通讯协议_722";
	public static final String	DICTITEM_JMRTUPROTTYPE  = "居民终端通讯协议_723";
	public static final String	DICTITEM_ALARMTYPE 		= "报警方式_1301";
	public static final String	DICTITEM_TJJCJG 		= "统计监测间隔_1321";
	public static final String	DICTITEM_KECALCTYPE		= "科林计费方式_1370";
	public static final String	DICTITEM_ACCOUNTUNIT	= "计算任务执行单位_550";
	public static final String	DICTITEM_ACCOUNTENDTIME	= "计算任务执行截止时间_551";
	public static final String	DICTITEM_FREEZEUshort 	= "分钟冻结间隔_701";
	public static final String	DICTITEM_PERIODUshort 	= "召测周期间隔_702";
	public static final String	DICTITEM_CBINTERVALS 	= "晓程抄表间隔_703";
	public static final String	DICTITEM_REDIALINNER 	= "重拨间隔_704";
	public static final String	DICTITEM_GPRSREDIALINNER= "gprs重连间隔_705";
	public static final String	DICTITEM_SAFEINTER		= "心跳周期_706";
	public static final String	DICTITEM_CBINTERVALS_GB	= "国电抄表间隔_708";
	public static final String	DICTITEM_STATOBJTYPE	= "统计对象类型1_1320";
	public static final String	DICTITEM_SWITCHTYPE		= "开关控制类型_1350";
	public static final String	DICTITEM_CTRLOPTYPE		= "控制操作_1340";
	public static final String	DICTITEM_SWITCH_OPERTYPE= "开关操作类型_1341";
	public static final String	DICTITEM_SWITCH_STATE	= "开关状态_1342";
	public static final String	DICTITEM_SWITCHSTATUS	= "开关状态_1342";
	public static final String	DICTITEM_SWITCH_STATE1	= "分合闸状态_1343";
	public static final String	DICTITEM_CTRLLOGIC		= "控制逻辑_1360";
	/**************sg186参数********************/
	public static final String	DICTITEM_INPUTFLAG		= "导入标志_1400";
	public static final String	DICTITEM_FUKONGMANTYPE = "预付费人员类型_1500";
	public static final String	DICTITEM_YFFRANK	 	= "预付费权限范围_1501";
	public static final String	DICTITEM_YFFAPPTYPE	 	= "预付费应用类型_1502";
	public static final String	DICTITEM_YFFMONEY		= "预付费金额_1503";
	public static final String	DICTITEM_YFFMETERTYPE	= "预付费表类型_1504";
	public static final String	DICTITEM_YFFSPECCTRLTYPE= "专变预付费控制方式_1505";
	public static final String	DICTITEM_FEELVTYPE		= "费率类型_1520";
	public static final String	DICTITEM_YFFCUSSTATE	= "用户状态_1530";
	public static final String	DICTITEM_YFFOPTYPE		= "预付费操作类型_1540";
	public static final String	DICTITEM_YFFQRZT		= "预付费确认状态_1542";
	public static final String	DICTITEM_CB_CYCLE_TYPE	= "抄表周期类型_1544";
	public static final String	DICTITEM_YFF_RATEJ_TYPE	= "阶梯电价类型_1546";
	public static final String	DICTITEM_METER_FEE_TYPE	= "阶梯电价电表费率类型_1547";
	public static final String	DICTITEM_DOCRIGHT 		= "档案权限_1548";
	public static final String	DICTITEM_CARDMETER_TYPE = "卡表类型_1549";
	
	public static final String	DICTITEM_NOCYCUT_MIN    = "无采样断电时间_2001"; 
	public static final String  DICTITEM_FARMER_STATE	= "农排表用电状态_2002";
	public static final String  DICTITEM_FAULT_CAUSE	= "农排表刷卡故障原因_2003";
	public static final String  DICTITEM_NPMETER_TYPE	= "农排表类型_2004";
	public static final String  DICTITEM_NPQFMETER_TYPE	= "清丰农排表用电状态_2005";
	

}
