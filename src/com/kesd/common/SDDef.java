package com.kesd.common;


public class SDDef {

	public static final String SUCCESS 			= "success";
	public static final String FAIL 			= "fail";
	public static final String SESSION_USERNAME = "username";
	public static final String BUNDLENAME 		= "kesdweb";
	
	//hzhw-begin
	public static final String TRUE 			= "true";
	public static final String FALSE 			= "false";
	//hzhw-end
	public static final String OK 				= "ok";
	public static final String ERROR 			= "error";
	public static final String EMPTY 			= "";
	public static final String EXCEL 			= "excel";
	
	public static final String GLOBAL_KE1 		= "global_ke1";
	public static final String GLOBAL_KE2		= "global_ke2";
	
	public static final String ADMIN			= "sdadmin";
	public static final String ADMIN_PWD		= "jackjack";
	
	public static final int DATE_FORMAT1 		= 1;
	public static final int DATE_FORMAT2 		= 2;
	
	//HL
	public static final String STR_AND 			= "&";			//
	public static final String STR_OR 			= "|";			//
	public static final int GETUSERINFO			= 101;			//售电查询
	//
	
	public static final String TREE_POWER		= "p";
	public static final String TREE_MANAGE		= "m";
	
	public static final int    PAGESIZE         = 10;
	
	public static final int    BDZRTUID         = 0;			//变电站 终端起始编号
	public static final int    ZBRTUID          = 10000000;		//专变 终端起始编号
	public static final int    GBRTUID          = 20000000;		//公变 终端起始编号
	public static final int    JMRTUID          = 30000000;		//居民 终端起始编号
	public static final int    NPRTUID          = 50000000;		//农排 终端起始编号
	
	//用户类型
	public static final int    APPTYPE_BDZ      = 0;
	public static final int    APPTYPE_ZB       = 1;
	public static final int    APPTYPE_GB       = 2;
	public static final int    APPTYPE_JC       = 3;
	public static final int	   APPTYPE_NP		= 5;
	
	//综合应用-数据查询
	public static final int    ZHYY_LINELOSS	= 0;			//线损查询
	public static final int    ZHYY_MOTHERLINE  = 1;			//母线查询
	public static final int    ZHYY_TOTALSEARCH = 2;			//汇总查询
	
	public static final String JSONSPLIT		= "\\|";
	public static final String SPLITCOMA		= ",";
	public static final String JSONQUOT			= "\"";
	public static final String JSONCLN			= ":";
	public static final String JSONQAC			= "\":";
	public static final String JSONQACQ			= "\":\"";	
	public static final String JSONCOMA			= ",";	
	public static final String JSONCCM			= "\",";
	public static final String JSONLBRACES		= "{";
	public static final String JSONRBRACES		= "}";
	public static final String JSONQRBCM		= "\"},";
	public static final String JSONRBCM		    = "},";
	public static final String JSONQBRRBCM		= "\"]},";
	public static final String JSONLBRACKET		= "[";
	public static final String JSONRBRACKET		= "]";
	public static final String JSONRBRBRBR		= "}]}";
	public static final String JSONROWSTITLE	= "{\"rows\":[";
	public static final String JSONTOTAL	    = "{\"total\":";
	public static final String JSONPAGEROWS     = ",\"page\":[{\"rows\":[";
	public static final String JSONSELDATA		= "{data:[";
	public static final String JSONDATA			= "\",data:[\"0\",";
	public static final String JSONNZDATA		= "\",data:[";
	public static final String JSONDATA1		= "\",data:[\"1\",";
	
	public static final String NAVIG_SPRT 		= "<font size=2px>→</font>";	//导航内容分隔符
	
	public static final String DATESEPARATOR	= "-";
	public static final String UNDERLINE    	= "_";
	
	
	public static final byte	PROOBJ_TYPE_RTU 	= 0;
	public static final byte	PROOBJ_TYPE_POINT 	= 1;
	public static final byte	PROOBJ_TYPE_ZJGROUP = 2;
	public static final byte	PROOBJ_TYPE_CONTLUN = 3;
	public static final byte	PROOBJ_TYPE_TASKNO 	= 4;
	public static final byte	PROOBJ_TYPE_ZL 		= 5;
	public static final byte	PROOBJ_TYPE_ALARMNO = 6;
	
	public static final byte	PRODATA_TYPE_PARA 		= 0;
	public static final byte	PRODATA_TYPE_CONT 		= 5;
	public static final byte	PRODATA_TYPE_REALDATA 	= 10;
	public static final byte	PRODATA_TYPE_MINDATA 	= 11;
	public static final byte	PRODATA_TYPE_DAYDATA 	= 12;
	public static final byte	PRODATA_TYPE_MONDATA 	= 13;
	public static final byte	PRODATA_TYPE_CBDATA 	= 16;
	public static final byte	PRODATA_TYPE_EVNT 		= 20;
	public static final byte	PRODATA_TYPE_ERRALARM 	= 21;
	public static final byte	PRODATA_TYPE_OTHER 		= 30;
	
	//包含下属
	public static final byte	HAVE_TYPE_UNKNOWN 		= -1;
	public static final byte	HAVE_TYPE_NONE 			= 0;
	public static final byte	HAVE_TYPE_STATION 		= 1;
	public static final byte	HAVE_TYPE_LINE 			= 2;
	public static final byte	HAVE_TYPE_ORG 			= 3;
	public static final byte	HAVE_TYPE_LINEFZMAN		= 4;
	public static final byte	HAVE_TYPE_CONS 			= 5;
	public static final byte	HAVE_TYPE_RTU 			= 6;
	public static final byte	HAVE_TYPE_MP 			= 7;
	public static final byte	HAVE_TYPE_METER 		= 8;
	public static final byte	HAVE_TYPE_ZJZ 			= 9;
	

	public static final byte 	MeterNum_OneGroup_DYH  = 4;		//每组数据电表个数 ----大用户
	public static final byte 	MeterNum_OneGroup_FXCC = 5;		//每组数据电表个数 ----福星晓程
	
	public static final byte	RtuNum_OneGroup_Batch  = 10;	//群操作时下发最大终端个数
	public static final byte	RtuNum_OneGroup_Onlyone= 1;		//群操作时下发个数只允许每次一个
	
	public static final byte	Oper_State_None = 0;			//未操作
	public static final byte	Oper_State_Call = 1;			//正在召测
	public static final byte	Oper_State_Set  = 2;			//正在设置
	public static final byte	Oper_State_Result = 9;			//返回结果
	
	
	public static final int DATA_MAX_ROW = 10000;				//页面显示记录的最大条数
	
	//定义表底电量数据读取类型范围
	public static final byte  DATA_BDDL_SINGLE 		= 0x01;		//单费率
	public static final byte  DATA_BDDL_MULT   		= 0x02;		//复费率
	public static final byte  DATA_BDDL_ZHENG   	= 0x04;		//正向
	public static final byte  DATA_BDDL_FAN   		= 0x08;		//反向
	
	public static final byte  DATA_SSL_V			= 0x01;		//电压
	public static final byte  DATA_SSL_I			= 0x02;		//电流
	public static final byte  DATA_SSL_PQ			= 0x04;		//功率
	public static final byte  DATA_SSL_CS			= 0x08;		//功率因数
	
	public static final byte  DATA_BEILV_ONE		= 0x01;		//一次值
	public static final byte  DATA_BEILV_TWO		= 0x02;		//二次值
	
	public static final byte  DATA_HG_TM			= 0x01;		//累计时间
	public static final byte  DATA_HG_LIMIT			= 0x02;		//极值
	public static final byte  DATA_HG_PJ			= 0x04;		//平均值
	
	public static final byte  DATA_I_TM				= 0x01;		//累计时间   电流
	public static final byte  DATA_I_LIMIT			= 0x02;		//极值
	
	public static final byte  DATA_P_LIMIT			= 0x01;		//功率极值
	public static final byte  DATA_P_ZERO			= 0x02;		//功率为零累计时间   电流
	
	public static final byte  DATA_DX_TIMES			= 0x01;		//断相次数
	public static final byte  DATA_DX_TM			= 0x02;		//断相累计时间
	public static final byte  DATA_DX_LAST			= 0x04;		//最近断相时刻
	
	public static final byte	LOG_LOGIN		= 0;
	public static final byte	LOG_LOGOUT		= 1;
	public static final byte	LOG_ADD			= 2;
	public static final byte	LOG_UPDATE		= 3;
	public static final byte	LOG_DELETE		= 4;
	public static final byte	LOG_SET			= 5;
	
	public static final byte	YFF_FEETYPE_FFL		= 1;//复费率
	public static final byte	YFF_FEETYPE_JTFL	= 2;//阶梯费率
	public static final byte	YFF_FEETYPE_DFL		= 3;//单费率
	public static final byte	YFF_FEETYPE_MIX		= 4;//混合费率
	public static final byte	YFF_FEETYPE_MIXJT   = 5;//混合阶梯费率
	
	//阶梯电价方案
	public static final byte	YFF_RATETYPE_YEAR	= 0;//年度方案
	public static final byte	YFF_RATETYPE_MON	= 1;//月度方案
	

	public static final byte	YFF_PREPAYTYPE_CARD		= 0;	//缴费方式-智能卡
	public static final byte	YFF_PREPAYTYPE_REMOTE	= 1;	//缴费方式-远程缴费
	public static final byte	YFF_PREPAYTYPE_MASTER	= 2;	//缴费方式-主站缴费
	
	public static final byte 	YFF_DY_CHGBUYTIME		= 0;	//更新购电次数
	public static final byte 	YFF_DY_FRMESS_ERR		= 1;	//首次短信告警确认失败
	public static final byte 	YFF_DY_FRSOUND_ERR		= 2;	//首次声音告警确认失败
	public static final byte 	YFF_DY_SECMESS_ERR		= 3;	//二次短信告警确认失败
	public static final byte 	YFF_DY_JTTOTZBDL		= 4;	//阶梯追补累计用电量
	public static final byte 	YFF_DY_JTRESETYMD		= 5;	//阶梯上次自动切换日期	自动切换执行时间
	public static final byte 	YFF_DY_FXDF_ALLMONEY	= 6;	//发行电费当月缴费总金额
	public static final byte 	YFF_DY_FXDF_REMIAN		= 7;	//发行电费当月剩余金额
	public static final byte 	YFF_DY_FXDF_YM			= 8;	//发行电费年月
	public static final byte 	YFF_DY_MAINCTRL_ERR		= 9;	//主表控制失败
	public static final byte 	YFF_DY_REL1CTRL_ERR		= 10;	//关联1控制失败
	public static final byte 	YFF_DY_REL2CTRL_ERR		= 11;	//关联2控制失败
	
	public static final byte 	YFF_DY_UPDATE_KEYVER	= 12;	//密钥版本	
	
	public static final byte 	YFF_GY_CHGBUYTIME		= 0;	//更新购电次数
	public static final byte 	YFF_GY_FRMESS_ERR		= 1;	//首次短信告警确认失败
	public static final byte 	YFF_GY_FRSOUND_ERR		= 2;	//首次声音告警确认失败
	public static final byte 	YFF_GY_SECMESS_ERR		= 3;	//二次短信告警确认失败
	//新增加的字段
	public static final byte 	YFF_GY_TOTAL_YWZBDL		= 4;	//追补累计用电量
	public static final byte 	YFF_GY_ZBELE_MONEY		= 5;	//追补电度电费
	public static final byte 	YFF_GY_ZBJBF_MONEY		= 6;	//追补基本费电费
	public static final byte 	YFF_GY_FXDF_IALL_MONEY	= 7;	//发行电费当月缴费总金额
	public static final byte 	YFF_GY_FXDF_REMAIN      = 8;	//发行电费后剩余金额
	public static final byte 	YFF_GY_FXDF_YM   		= 9;	//发行电费数据日期
	public static final byte 	YFF_GY_JSWGBD   		= 10;	//结算无功表底
	
	
	
	public static final byte    YFF_FEECTRL_TYPE_RTU 	= 0;	//费控类型-终端费控
	public static final byte    YFF_FEECTRL_TYPE_MASTER	= 1;	//费控类型-主站费控
	
	public static final byte    YFF_CTRLTYPE_GY_LK		= 0;	//控制类型-轮次控制
	
	public static final byte    YFF_CACL_TYPE_NONE		= 0;	//计费方式-无
	public static final byte    YFF_CACL_TYPE_MONEY		= 1;	//计费方式-金额计费
	public static final byte    YFF_CACL_TYPE_BD		= 2;	//计费方式-表底计费
	public static final byte    YFF_CACL_TYPE_DL		= 3;	//计费方式-电量计费
	
	public static final byte    YFF_PAY_TYPE			= 0;	//缴费方式-卡式

	public static final byte    YFF_CUSSTATE_INIT		= 0;	//客户状态-初始态
	public static final byte    YFF_CUSSTATE_NORMAL		= 1;	//客户状态-正常态
	public static final byte    YFF_CUSSTATE_PAUSE		= 49;	//客户状态-暂停态
	public static final byte    YFF_CUSSTATE_DESTORY	= 50;	//客户状态-销户态
	
	//操作类型
	public static final int		YFF_OPTYPE_NULL			= 0;	//初始态
	public static final int		YFF_OPTYPE_ADDRES		= 1;	//开户
	public static final int		YFF_OPTYPE_PAY			= 2;	//缴费
	public static final int		YFF_OPTYPE_REPAIR		= 3;	//补卡
	public static final int		YFF_OPTYPE_REWRITE		= 4;	//补写卡
	public static final int		YFF_OPTYPE_REVER		= 5;	//冲正
	public static final int		YFF_OPTYPE_CHANGEMETER	= 6;	//换表
	public static final int		YFF_OPTYPE_CHANGERATE	= 7;	//换费率
	public static final int		YFF_OPTYPE_CHANGEPADD	= 8;	//换基本费
	public static final int		YFF_OPTYPE_JSBC			= 9;	//结算
	public static final int		YFF_OPTYPE_TOKENUPDATE	= 10;	//密钥更新

	public static final int		YFF_OPTYPE_RESTART		= 48;	//恢复
	public static final int		YFF_OPTYPE_PAUSE		= 49;	//暂停
	public static final int		YFF_OPTYPE_DESTORY		= 50;	//销户	
	
	//报警方式
	public static final byte  	YFF_ALARMTYPE_FIXED		= 0;	//固定值方式
	public static final byte  	YFF_ALARMTYPE_SCALE		= 1;	//比例方式
	
	//换表/换倍率
	public static final int  	YFF_CHGMETER_MT			= 0;	//换表
	public static final int  	YFF_CHGMETER_CT			= 1;	//换ct
	public static final int  	YFF_CHGMETER_PT			= 3;	//换pt
	
	public static final byte  	YFF_APPTYPE_DYQX		= 1;	//低压权限
	public static final byte  	YFF_APPTYPE_GYQX		= 2;	//高压权限
	public static final byte  	YFF_APPTYPE_NPQX		= 4;	//农排权限		---应该为3，暂时定义为2进行
	//public static final byte  	YFF_APPTYPE_DGQX		= 2;	//低高压权限
	
	//卡类型
	public static final byte  	YFF_CARDMTYPE_NULL		= 0;	//无
	public static final byte  	YFF_CARDMTYPE_KE001		= 1;	//科林自管户1
	public static final byte  	YFF_CARDMTYPE_KE002		= 2;	//科林自管户2
	public static final byte  	YFF_CARDMTYPE_KE003		= 3;	//科林6103
	public static final byte  	YFF_CARDMTYPE_KE005		= 5;	//科林农排
	//20131021新增卡类型，表类型
	public static final byte  	YFF_CARDMTYPE_KE006		= 6;	//新规约智能卡
	
	//低压居民智能表类型
	public static final byte  	YFF_METERTYPE_TYZG09	= 0;	//通用自管2009版
	public static final byte  	YFF_METERTYPE_KEZG09	= 1;	//科林自管2009版,
	public static final byte  	YFF_METERTYPE_BYZG09	= 2;	//备用1自管2009版
	public static final byte  	YFF_METERTYPE_02MM09	= 3;	//02级密码透传控
	public static final byte  	YFF_METERTYPE_GWZN09	= 4;	//国网2009版智能
	public static final byte  	YFF_METERTYPE_GWZGZS	= 5;	//国网自管户正式
	public static final byte  	YFF_METERTYPE_GWZGGY	= 6;	//国网自管户公钥		
	public static final byte  	YFF_METERTYPE_XCB		= 7;	//晓程表',				

	public static final byte  	YFF_METERTYPE_TYZG13	= 50;	//通用自管2013版
	public static final byte  	YFF_METERTYPE_KEZG13	= 51;	//科林自管2013版
	public static final byte  	YFF_METERTYPE_BYZG13	= 52;	//备用2自管2013版
	public static final byte  	YFF_METERTYPE_GWZN13	= 58;	//国网2013版智能		
	public static final byte  	YFF_METERTYPE_02MM13	= 59;	//02级密码透传控2013版
	
	public static final byte  	YFF_METERTYPE_EXTKE		= 101;	//科林外接卡表
	public static final byte  	YFF_METERTYPE_EXTJJ		= 102;	//江机
	public static final byte  	YFF_METERTYPE_EXTRM		= 103;	//人民
	//表类型	end
		
	public static final String 	YFF_METER_TYPE_ZNK  	= "KE_001";	//卡类型智能表
	public static final String 	YFF_METER_TYPE_6103 	= "KE_003";	//卡类型6103
	public static final String 	YFF_METER_TYPE_NP 		= "KE_005";	//卡类型农排表
	//20131021新增卡类型
	public static final String 	YFF_METER_TYPE_ZNK2 	= "KE_006";	//卡类型2013智能表
	
		
	public static final byte  	YFF_RANK_ALL			= 0;	//全局权限
	public static final byte  	YFF_RANK_ORG			= 1;	//变电所权限
	public static final byte  	YFF_RANK_FZMAN			= 2;	//线路负责人
	
	//此处以6103为基础 智能表用到时要转换一下
	public static final int 	CARD_OPTYPE_OPEN		= 0;	//卡类型 开户
	public static final int 	CARD_OPTYPE_BUY			= 1;	//买电充值
	public static final int 	CARD_OPTYPE_REPAIR		= 2;	//补卡
	public static final int 	CARD_OPTYPE_CHECK		= 6;	//单费率检查卡 
	public static final int 	CARD_OPTYPE_RELAYTEST	= 8;	//继电器测试卡 
	public static final int 	CARD_OPTYPE_ADDDL		= 9;	//增加电费卡 
	public static final int 	CARD_OPTYPE_PREMAKE		= 10;	//预置卡
	
	
	//农排写卡操作类型
	public static final int 	NPCARD_OPTYPE_INITPARA	= 0;	//不缴费 仅初始化参数
	public static final int 	NPCARD_OPTYPE_OPEN		= 1;	//卡类型 开户
	public static final int 	NPCARD_OPTYPE_BUY		= 2;	//买电充值
	public static final int 	NPCARD_OPTYPE_REVER		= 3;	//冲正
	
	//农排卡类型
	public static final String NPCARD_OPTYPE_USER 		= "01";		//用户卡
	public static final String NPCARD_OPTYPE_TEST 		= "11";		//测试卡
	public static final String NPCARD_OPTYPE_LOCK 		= "12";		//锁定/解锁卡
	public static final String NPCARD_OPTYPE_SETPARA 	= "21";		//参数设置卡
	public static final String NPCARD_OPTYPE_PTCT 		= "22";		//变比卡
	public static final String NPCARD_OPTYPE_AREA 		= "23";		//区域修改卡
	public static final String NPCARD_OPTYPE_FEE 		= "24";		//电价卡
	public static final String NPCARD_OPTYPE_QUERY 		= "31";		//查询卡
	public static final String NPCARD_OPTYPE_RESET 		= "32";		//复位卡
	public static final String NPCARD_OPTYPE_CLEAR 		= "33";		//清零卡
	public static final String NPCARD_OPTYPE_FACPARA 	= "41";		//出厂设置卡
	public static final String NPCARD_OPTYPE_TMPRCDREAD = "51";		//临时记录抄收
	public static final String NPCARD_OPTYPE_TMPRCDCLEAR= "52";		//临时记录清除
	public static final String NPCARD_OPTYPE_INCRYP 	= "88";		//修改秘钥卡
	public static final String NPCARD_OPTYPE_DECRYP 	= "89";		//恢复秘钥卡
	public static final String NPCARD_OPTYPE_GRAYLOCK 	= "90";		//灰锁
	public static final String NPCARD_OPTYPE_GRAYUNLOCK = "91";		//解锁
	public static final String NPCARD_OPTYPE_READCARDTYPE = "fe";	//读取卡类型 特殊处理
	//NPCARD_OPTYPE_EXPANSION		0x81									//ke扩展卡子类型
	public static final byte   NPCARD_KEEXPAN_BLACK_ADD		= 0x01;			//增加黑名单
	public static final byte   NPCARD_KEEXPAN_BLACK_DEL		= 0x02;			//删除黑名单
	public static final byte   NPCARD_KEEXPAN_BLACK_CLEAR	= 0x03;			//清空黑名单
	public static final byte   NPCARD_KEEXPAN_BLACK_READ	= 0x04;			//抄收黑名单
	public static final byte   NPCARD_KEEXPAN_OTHER			= 0x05;			//其他卡类型

	//更改手机号
	public static final byte	YFF_DY_CHGPARATYPE_MOBILE		=	1;		//居民手机号码 filed1有效
	public static final byte	YFF_DY_CHGPARATYPE_OTHER		=	2;			

	public static final byte	YFF_GY_CHGPARATYPE_MOBILE		=	1;		//专变客户手机号码 filed1有效
	public static final byte	YFF_GY_CHGPARATYPE_OTHER		=	2;			

	public static final byte	YFF_NP_CHGPARATYPE_MOBILE		=	1;		//农排客户手机号码 filed1有效
	public static final byte	YFF_NP_CHGPARATYPE_OTHER		=	2;		
	
	//农排表类型
	public static final byte	YFF_NP_CARDTYPE_KE2012			=	0;		//科林农排表2012版
	public static final byte	YFF_NP_CARDTYPE_HNTY			=	20;		//河南公用农排表
	
	//售电预付费表类型--低压
//	public static final byte YFF_METERTYPE_DY_ZGZNB_TY  	= 0;  		//通用自管智能表
//	public static final byte YFF_METERTYPE_DY_ZGZNB_KE  	= 1;    	//科林自管智能表
//	public static final byte YFF_METERTYPE_DY_ZGZNB_BY1 	= 2;    	//备用1自管智能表
//	public static final byte YFF_METERTYPE_DY_ZGZNB_TCCTRL 	= 3;    	//02级密码透传控制智能表
//	public static final byte YFF_METERTYPE_DY_GWZNB   		= 4;    	//国网智能表
//	public static final byte YFF_METERTYPE_DY_GWZNB_ZGH  	= 5;    	//国网自管户正式版
//	public static final byte YFF_METERTYPE_DY_GWZNB_ZGHP  	= 6;    	//国网自管户公钥版P PUBLIC
//	public static final byte YFF_METERTYPE_DY_XCB   		= 7;    	//晓程表
//	//20130805
//	public static final byte YFF_METERTYPE_DY_ZGZNB_TY2  	= 10;    	//通用自管智能表2
//	public static final byte YFF_METERTYPE_DY_ZGZNB_KE2  	= 11;    	//科林自管智能表2
//	public static final byte YFF_METERTYPE_DY_ZGZNB_BY2  	= 12;    	//备用2自管智能表2    //下面留着其他的使用
//	public static final byte YFF_METERTYPE_DY_GWZNB2   		= 18;    	//国网智能表2
//	public static final byte YFF_METERTYPE_DY_ZGZNB_TCCTRL2 = 19;    	//02级密码透传控制智能表2

	
}
