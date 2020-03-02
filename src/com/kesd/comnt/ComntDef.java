package com.kesd.comnt;

public class ComntDef {
	//字符长度预定义
	public static final int YD_8_STRLEN						= 8;	//8字节字符
	public static final int YD_9_STRLEN						= 9;	//9字节字符
	public static final int YD_16_STRLEN					= 16;	//16字节字符
	public static final int YD_32_STRLEN					= 32;	//32字节字符
	public static final int YD_64_STRLEN					= 64;	//64字节字符

	public static final int FALSE							= 0;
	public static final int TRUE							= 1;
	
	public static final int NO								= 0;
	public static final int YES								= 1;
	
	//用电系统规约定义
	public static final int YD_PROTOCAL_NULL				= 0;	//无效
	public static final int YD_PROTOCAL_FKGD04				= 1;	//国电负控2004规约
	public static final int YD_PROTOCAL_FKGD05				= 2;	//国电负控2005规约
	public static final int YD_PROTOCAL_GD376				= 3;	//国电376.1-2009规约
	public static final int YD_PROTOCAL_KEDYH				= 4;	//科林大用户规约
	public static final int YD_PROTOCAL_KEBDZ				= 5;	//科林变电站规约
	public static final int YD_PROTOCAL_GD376_2013			= 6;	//国电376.1-2013规约
	public static final int YD_PROTOCAL_DL719				= 10;	//变电站DL719规约
	public static final int YD_PROTOCAL_IEC102				= 11;	//变电站102规约
	public static final int YD_PROTOCAL_IECHB				= 12;	//变电站102规约河北
	public static final int YD_PROTOCAL_IECNR				= 13;	//变电站102规约南瑞
	public static final int YD_PROTOCAL_IECHL				= 14;	//变电站102规约华丽
	public static final int YD_PROTOCAL_IECTC				= 15;	//变电站102规约透传
	public static final int YD_PROTOCAL_KEJC_DFL			= 50;	//科林集抄规约单费率(晓程)
	public static final int YD_PROTOCAL_KEJC_FFL			= 51;	//科林集抄规约复费率(晓程)
	public static final int YD_PROTOCAL_HBNWJC				= 52;	//河北南网集抄规约
	public static final int YD_PROTOCAL_DFPB				= 70;	//地方配变规约
	public static final int YD_PROTOCAL_DFFK				= 90;	//地方负控规约

	
	//任务分配类型
	public static final int YD_TASKASSIGNTYPE_AUTO			= 0;	//自动任务
	public static final int YD_TASKASSIGNTYPE_MAN			= 1;	//手工任务
	
//	//任务执行错误码
	public static final int YD_TASKERR_OK					= 0;		//正确
	public static final int YD_TASKERR_PARA				    = 1;		//参数不匹配
	public static final int YD_TASKERR_MSG				    = 2;		//消息发送错误
	public static final int YD_TASKERR_WAIT_TIMEOUT		    = 3;		//等待超时
	public static final int YD_TASKERR_COMNT_TIMEOUT	    = 4;		//通讯超时
	public static final int YD_TASKERR_TASKBUF_FULL		    = 5;		//任务缓存满
	public static final int YD_TASKERR_CHAN_UNVALID		    = 6;		//通道无效
	public static final int YD_TASKERR_CHAN_COMNT		    = 7;		//通道通讯错误
	public static final int YD_TASKERR_RTU_UNVALID		    = 8;		//终端无效
	public static final int YD_TASKERR_RTU_COMNT		    = 9;		//终端通讯错误
	public static final int YD_TASKERR_MAKEFRAME		    = 10;		//组帧失败
	public static final int YD_TASKERR_RTU_NOREPLY		    = 11;		//终端无应答
	public static final int YD_TASKERR_CANCEL			    = 12;		//任务被取消
	public static final int YD_TASKERR_TASKSTATE		    = 13;		//任务状态错误
	public static final int YD_TASKERR_TASKPARA				= 14;		//任务参数错误
	public static final int YD_TASKERR_ENDBYFORCE			= 15;		//任务被强制结束
	public static final int YD_TASKERR_OTHER			    = 100;		//其他错误

	public static final String getErrCodeString(int errcode) {
		String ret_str = "未知错误";
		switch (errcode) {
		case YD_TASKERR_OK:				ret_str = "正确"; 			break;		
		case YD_TASKERR_PARA:			ret_str = "参数不匹配";		break;	
		case YD_TASKERR_MSG:			ret_str = "消息发送错误";	break;	
		case YD_TASKERR_WAIT_TIMEOUT:	ret_str = "等待超时";		break;	
		case YD_TASKERR_COMNT_TIMEOUT:	ret_str = "通讯超时";		break;	
		case YD_TASKERR_TASKBUF_FULL:	ret_str = "任务缓存满";		break;	
		case YD_TASKERR_CHAN_UNVALID:	ret_str = "通道无效";		break;	
		case YD_TASKERR_CHAN_COMNT:		ret_str = "通道通讯错误";	break;	
		case YD_TASKERR_RTU_UNVALID:	ret_str = "终端无效";		break;	
		case YD_TASKERR_RTU_COMNT:		ret_str = "终端通讯错误";	break;	
		case YD_TASKERR_MAKEFRAME:		ret_str = "组帧失败";		break;	
		case YD_TASKERR_RTU_NOREPLY:	ret_str = "终端无应答";		break;	
		case YD_TASKERR_CANCEL:			ret_str = "任务被取消";		break;	
		case YD_TASKERR_TASKSTATE:		ret_str = "任务状态错误";	break;	
		case YD_TASKERR_TASKPARA:		ret_str = "任务参数错误";	break;	
		case YD_TASKERR_ENDBYFORCE:		ret_str = "任务被强制结束";	break;	
		case YD_TASKERR_OTHER:			ret_str = "其他错误";		break;
		}		
		return ret_str;
	}
	
	public static final String YD_TASKPROC_JWEBSERVICE_NAME	= "java后台";
	
	//任务进程代码
	public static final int YD_TASKPROC_NULL				= 0;		//无
	public static final int YD_TASKPROC_FRONT				= 1;		//前置机
	public static final int YD_TASKPROC_TASGN				= 2;		//任务分配
	public static final int YD_TASKPROC_CWEBSERVICE			= 3;		//C++  WebService
	public static final int YD_TASKPROC_CRYPLINK			= 4;		//加密机
	public static final int YD_TASKPROC_SALEMANSERVICE		= 5;		//售电管理服务
	public static final int YD_TASKPROC_SALEMANAGER			= 6;		//售电管理客户端
	public static final int YD_TASKPROC_YFFCRYPSERVICE		= 7;
	public static final int YD_TASKPROC_JWEBSERVICE			= 10;		//java WebService	
	public static final int YD_TASKPROC_YFFCALCSERVICE		= 11;
	public static final int YD_TASKPROC_YFFOPERSERVICE		= 12;
	public static final int YD_TASKPROC_YFFCOMMSERVICE		= 13;		//预付费通讯服务
	public static final int YD_TASKPROC_YFFALARMSERVICE		= 14;
	public static final int YD_TASKPROC_YFFEVENTSERVICE		= 15;

	
	public static final String getErrProcString(int errproc) {
		String ret_str = "未知进程";		
		switch (errproc) {
		case YD_TASKPROC_NULL:				ret_str = "无";					break;
		case YD_TASKPROC_FRONT:				ret_str = "前置机进程";			break;
		case YD_TASKPROC_TASGN:				ret_str = "任务进程";			break;
		case YD_TASKPROC_CWEBSERVICE:		ret_str = "WEB通信进程";			break;
		case YD_TASKPROC_SALEMANSERVICE:	ret_str = "售电管理服务";		break;
		case YD_TASKPROC_SALEMANAGER:		ret_str = "售电管理客户端";		break;
		case YD_TASKPROC_YFFCRYPSERVICE:	ret_str = "加密机服务";			break;
		case YD_TASKPROC_JWEBSERVICE:		ret_str = "java后台";			break;
		case YD_TASKPROC_YFFCALCSERVICE:	ret_str = "预付费电费计算服务";	break;
		case YD_TASKPROC_YFFOPERSERVICE:	ret_str = "预付费业务处理服务";	break;
		case YD_TASKPROC_YFFCOMMSERVICE:	ret_str = "预付费通讯服务";		break;
		case YD_TASKPROC_YFFALARMSERVICE:	ret_str = "预付费报警控制服务";	break;
		case YD_TASKPROC_YFFEVENTSERVICE:	ret_str = "预付费事项服务";		break;		
		}		
		return ret_str;
	}
	
	//确认状态
	public static final byte YFF_QRFLAG_INIT	=	0;		//初始态
	public static final byte YFF_QRFLAG_WAIT	=	1;		//等待
	public static final byte YFF_QRFLAG_SUCCESS	=	2;		//成功
	public static final byte YFF_QRFLAG_FAILD	=	3;		//失败
	
	//************************yff常量信息定义***************************//
	public static final String  SALEMANAGER_VERSION					= "2.0.1";			//客户端版本信息
	//消息类型
	public static final int YD_YFF_MSGTYPE_NULL						= 0;
	public static final int YD_YFF_MSGTYPE_TASK						= 1;			//执行任务
	public static final int YD_YFF_MSGTYPE_TASKRESULT				= 2;			//执行任务结果
	public static final int YD_YFF_MSGTYPE_DATA						= 3;			//数据
	public static final int YD_YFF_MSGTYPE_RAWDATA					= 4;			//通讯报文
	public static final int YD_YFF_MSGTYPE_CANCELMANTASK			= 5;			//取消手工任务--hzhw20110324
	public static final int YD_YFF_MSGTYPE_RTUSTATE					= 6;			//终端状态
	public static final int YD_YFF_MSGTYPE_RELOADPARA				= 7;			//重载参数
	
	public static final int SALEMAN_MSGTYPE_QUERYRTUSTATE			= 99;			//请求终端状态
	public static final int YD_YFF_MSGTYPE_TEST						= 100;			//链路测试
	
	//任务应用类型
	public static final int YD_YFF_TASKAPPTYPE_NULL					= 0;			//

	//低压
	public static final int YD_YFF_TASKAPPTYPE_DYOPER_ADDRES		= 1;			//低压操作-开户
	public static final int YD_YFF_TASKAPPTYPE_DYOPER_PAY			= 2;			//低压操作-缴费
	public static final int YD_YFF_TASKAPPTYPE_DYOPER_REPAIR		= 3;			//低压操作-补卡
	public static final int YD_YFF_TASKAPPTYPE_DYOPER_REWRITE		= 4;			//低压操作-补写卡
	public static final int YD_YFF_TASKAPPTYPE_DYOPER_REVER			= 5;			//低压操作-冲正
	public static final int YD_YFF_TASKAPPTYPE_DYOPER_CHANGEMETER	= 6;			//低压操作-换表换倍率
	public static final int YD_YFF_TASKAPPTYPE_DYOPER_CHANGERATE	= 7;			//低压操作-换电价
	public static final int YD_YFF_TASKAPPTYPE_DYOPER_PROTECT		= 8;			//低压操作-保电
	public static final int YD_YFF_TASKAPPTYPE_DYOPER_UDPATESTATE	= 9;			//低压操作-强制状态更新
	public static final int YD_YFF_TASKAPPTYPE_DYOPER_RECALC		= 10;			//低压操作-重新计算剩余金额
	

	public static final int YD_YFF_TASKAPPTYPE_DYOPER_JSBC			= 11;			//低压操作-结算补差			//20120728				
	public static final int YD_YFF_TASKAPPTYPE_DYOPER_GETREMAIN		= 12;			//低压操作-返回余额
	public static final int YD_YFF_TASKAPPTYPE_DYOPER_REFXDF		= 13;			//低压操作-重新发行电费		//20120728
	public static final int YD_YFF_TASKAPPTYPE_DYOPER_REJTRESET		= 14;			//低压操作-重新阶梯清零		//20120728
	public static final int	YD_YFF_TASKAPPTYPE_DYOPER_RESETDOC		= 15;			//低压操作-更改用户参数		//20130201
	
	public static final int YD_YFF_TASKAPPTYPE_DYOPER_GPARASTATE	= 27;			//低压操作-获得预付费参数及状态
	public static final int YD_YFF_TASKAPPTYPE_DYOPER_RESTART		= 28;			//低压操作-恢复
	public static final int YD_YFF_TASKAPPTYPE_DYOPER_PAUSE			= 29;			//低压操作-暂停
	public static final int YD_YFF_TASKAPPTYPE_DYOPER_DESTORY		= 30;			//低压操作-销户

	
//高压
	public static final int YD_YFF_TASKAPPTYPE_GYOPER_ADDCUS		= 51;			//高压操作-开户
	public static final int YD_YFF_TASKAPPTYPE_GYOPER_PAY			= 52;			//高压操作-缴费
	public static final int YD_YFF_TASKAPPTYPE_GYOPER_REPAIR        = 53;           //高压操作-补卡
	public static final int YD_YFF_TASKAPPTYPE_GYOPER_REVER			= 55;			//高压操作-冲正
	public static final int YD_YFF_TASKAPPTYPE_GYOPER_CHANGEMETER	= 56;			//高压操作-换表换倍率
	public static final int YD_YFF_TASKAPPTYPE_GYOPER_CHANGERATE	= 57;			//高压操作-换电价
	public static final int YD_YFF_TASKAPPTYPE_GYOPER_PROTECT		= 58;			//高压操作-保电
	public static final int YD_YFF_TASKAPPTYPE_GYOPER_UDPATESTATE	= 59;			//高压操作-强制状态更新
	public static final int YD_YFF_TASKAPPTYPE_GYOPER_RECALC		= 60;			//高压操作-重新计算剩余金额
	public static final int YD_YFF_TASKAPPTYPE_GYOPER_REJS			= 61;			//高压操作-重新结算
	public static final int YD_YFF_TASKAPPTYPE_GYOPER_GETREMAIN		= 62;			//高压操作-返回余额
	public static final int YD_YFF_TASKAPPTYPE_GYOPER_CHANGPAYADD	= 63;			//高压操作-换基本费
	public static final int YD_YFF_TASKAPPTYPE_GYOPER_GETJSBCREMAIN = 65;			//高压操作-结算补差返回余额
	public static final int YD_YFF_TASKAPPTYPE_GYOPER_RESETDOC		= 66;			//高压操作-更改用户参数
	
	public static final int YD_YFF_TASKAPPTYPE_GYOPER_GPARASTATE	= 77;			//高压操作-获得预付费参数及状态
	public static final int YD_YFF_TASKAPPTYPE_GYOPER_RESTART		= 78;			//高压操作-恢复
	public static final int YD_YFF_TASKAPPTYPE_GYOPER_PAUSE			= 79;			//高压操作-暂停
	public static final int YD_YFF_TASKAPPTYPE_GYOPER_DESTORY		= 80;			//高压操作-销户
	
//农排	
	public static final int YD_YFF_TASKAPPTYPE_NPOPER_ADDRES		= 85;			//农排操作-开户
	public static final int YD_YFF_TASKAPPTYPE_NPOPER_PAY			= 86;			//农排操作-缴费
	public static final int YD_YFF_TASKAPPTYPE_NPOPER_REPAIR		= 87;			//农排操作-补卡
	public static final int YD_YFF_TASKAPPTYPE_NPOPER_REWRITE		= 88;			//农排操作-补写卡
	public static final int YD_YFF_TASKAPPTYPE_NPOPER_REVER			= 89;			//农排操作-冲正
	public static final int YD_YFF_TASKAPPTYPE_NPOPER_UDPATESTATE	= 90;			//农排操作-强制状态更新
	public static final int YD_YFF_TASKAPPTYPE_NPOPER_GPARASTATE	= 91;			//农排操作-获得预付费参数及状态
	public static final int YD_YFF_TASKAPPTYPE_NPOPER_DESTORY		= 92;			//农排操作-销户
	
	public static final int YD_YFF_TASKAPPTYPE_NPOPER_RESETDOC		= 95;			//农排操作-更改用户参数

//低压通信
	public static final int YD_YFF_TASKAPPTYPE_DYCOMM_ADDRES		= 101;			//低压通讯-开户
	public static final int YD_YFF_TASKAPPTYPE_DYCOMM_PAY			= 102;			//低压通讯-缴费
	public static final int YD_YFF_TASKAPPTYPE_DYCOMM_CALLPARA		= 103;			//低压通讯-召参数
	public static final int YD_YFF_TASKAPPTYPE_DYCOMM_SETPARA		= 104;			//低压通讯-设参数
	public static final int YD_YFF_TASKAPPTYPE_DYCOMM_CTRL			= 105;			//低压通讯-控制
	public static final int YD_YFF_TASKAPPTYPE_DYCOMM_CHGKEY		= 106;			//低压通讯-下装密钥
	
	//20131021新增操作类型
	public static final int YD_YFF_TASKAPPTYPE_DYCOMM_CHGKEY2  		= 107;   		//低压通讯-下装密钥2
	public static final int YD_YFF_TASKAPPTYPE_DYCOMM_REVER   		= 108;   		//低压通讯-冲正
	public static final int YD_YFF_TASKAPPTYPE_DYCOMM_ININT   		= 109;   		//低压通讯-初始化
	public static final int YD_YFF_TASKAPPTYPE_DYCOMM_CLEAR   		= 110;   		//低压通讯-清空清空 电量 需

//高压通信
	public static final int YD_YFF_TASKAPPTYPE_GYCOMM_PAY			= 152;			//高压通讯-缴费
	public static final int YD_YFF_TASKAPPTYPE_GYCOMM_CALLPARA		= 153;			//高压通讯-召参数
	public static final int YD_YFF_TASKAPPTYPE_GYCOMM_SETPARA		= 154;			//高压通讯-设参数
	public static final int YD_YFF_TASKAPPTYPE_GYCOMM_CTRL			= 155;			//高压通讯-控制

//其他通信参数
	public static final int YD_YFF_TASKAPPTYPE_READDATA				= 201;			//读取数据 不分高低压
	public static final int YD_YFF_TASKAPPTYPE_CRYPLINK				= 202;			//加密机连接请求
	public static final int YD_YFF_TASKAPPTYPE_CRYPRESULT			= 203;			//加密机连接返回结果

	public static final int YD_YFF_TASKAPPTYPE_GET_PAUSEALARM		= 220;			//得到-暂停预付费报警及控制
	public static final int YD_YFF_TASKAPPTYPE_SET_PAUSEALARM		= 221;			//设置-暂停预付费报警及控制
	public static final int YD_YFF_TASKAPPTYPE_GET_GLOPROT			= 222;			//得到-全局保电参数
	public static final int YD_YFF_TASKAPPTYPE_SET_GLOPROT			= 223;			//设置-全局保电参数


	//任务应用类型
	public static final int SALEMAN_MSGTTYPE_MIS_DYQUERYPOWER		= 231;			//231电费查询
	public static final int SALEMAN_MSGTTYPE_MIS_DYPAY				= 232;			//232电费收费
	public static final int SALEMAN_MSGTTYPE_MIS_DYREVER			= 233;			//233冲正

	public static final int SALEMAN_MSGTTYPE_MIS_GYQUERYPOWER		= 241;			//241电费查询
	public static final int SALEMAN_MSGTTYPE_MIS_GYPAY				= 242;			//242电费收费
	public static final int SALEMAN_MSGTTYPE_MIS_GYREVER			= 243;			//243冲正
	public static final int SALEMAN_MSGTTYPE_MIS_GDYCHECKPAY		= 249;			//249高低压对账
	
	

	
//任务运行结果
	public static final int YD_YFF_TASKRESULT_NULL 				= 0;			//任务结果初始态
	public static final int YD_YFF_TASKRESULT_SUCCEED			= 1;			//任务结果成功
	public static final int YD_YFF_TASKRESULT_FAILED			= 2;			//任务结果失败

//任务运行状态
	public static final int YD_YFF_TASKSTATE_STOP				= 0;			//任务状态：停止态
	public static final int YD_YFF_TASKSTATE_WAIT				= 1;			//任务状态：等待态
	public static final int YD_YFF_TASKSTATE_RUN				= 2;			//任务状态：运行态

//任务返回数据类型
	public static final int YD_YFF_TASK_BACKDATA_NO				= 0;			//不返回数据
	public static final int YD_YFF_TASK_BACKDATA_SRC			= 1;			//返回给发送端

//错误码
	public static final int YD_YFF_TASKERR_OK					= 0;			//正确
	public static final int YD_YFF_TASKERR_PARA				    = 1;			//参数不匹配
	public static final int YD_YFF_TASKERR_MSG				    = 2;			//消息发送错误
	public static final int YD_YFF_TASKERR_WAIT_TIMEOUT		    = 3;			//等待超时
	public static final int YD_YFF_TASKERR_COMNT_TIMEOUT	    = 4;			//通讯超时
	public static final int YD_YFF_TASKERR_TASKBUF_FULL		    = 5;			//任务缓存满
	public static final int YD_YFF_TASKERR_RTU_UNVALID		    = 8;			//终端无效

	public static final int YD_YFF_TASKERR_MP_UNVALID			= 9;			//电表、计量点、电表通信端口
	public static final int YD_YFF_TASKERR_ZJG_UNVALID			= 10;			//电表、计量点、总加组相关
	public static final int YD_YFF_TASKERR_RTU_APPTYPE		    = 11;			//应用类型错误
	public static final int YD_YFF_TASKERR_RTU_PROTTYPE			= 12;			//规约类型错误

	public static final int YD_YFF_TASKERR_BACK_ERR				= 13;			//（报文）返回错误
	public static final int YD_YFF_TASKERR_BACK_FAIL			= 14;			// 任务返回失败
	public static final int YD_YFF_TASKERR_BACK_DATAFAIL		= 15;			// 任务返回数据失败
	public static final int YD_YFF_TASKERR_BACK_SHORT			= 16;			// 任务返回缺少数据或者任务结果

	public static final int YD_YFF_TASKERR_FEE_ERR				= 20;			// 费率类型错误

	public static final int YD_YFF_TASKERR_CANCEL			    = 23;			//任务被取消
	public static final int YD_YFF_TASKERR_TASKSTATE		    = 24;			//任务状态错误
	public static final int YD_YFF_TASKERR_TASKPARA				= 25;			//任务参数错误
	public static final int YD_YFF_TASKERR_ENDBYFORCE			= 26;			//任务被强制结束

	public static final int YD_YFF_TASKERR_CALLIDERR			= 40;			//无调用动态库函数
	public static final int YD_YFF_TASKERR_CRYPERR				= 41;			//加密机返回失败

	public static final int YD_YFF_TASKERR_OTHER			    = 100;			//其他错误

	public static final int YD_YFF_TASKERR_QBQR					= 101;			//全部确认
	public static final int YD_YFF_TASKERR_QBFR					= 102;			//全部否认
	public static final int YD_YFF_TASKERR_SDQF					= 103;			//部分确认
	public static final int YD_YFF_TASKERR_PROT_OTHER			= 104;			//通讯规约中未知错误
	//20131105
	public static final int YD_YFF_TASKERR_PROT_645				= 105;			//645报文错误
	public static final int YD_YFF_TASKERR_READERROR			= 201;			//读数据失败
	public static final int YD_YFF_TASKERR_WRITEERROR			= 202;			//写数据失败
	public static final int YD_YFF_TASKERR_XLERROR				= 203;			//清需量失败
	public static final int YD_YFF_TASKERR_DLERROR				= 204;			//清电量失败
	public static final int YD_YFF_TASKERR_EVENTERROR			= 205;			//清事件失败
	public static final int YD_YFF_TASKERR_REMOTECTRL			= 206;			//远程控制失败
	
	public static final int YD_YFF_TASKERR_AUTHOTHER			= 210;			//安全认证失败其他错误
	public static final int YD_YFF_TASKERR_AUTHCFCZ				= 211;			//安全认证失败重复充值
	public static final int YD_YFF_TASKERR_AUTHESAM				= 212;			//安全认证失败ESAM验证失败
	public static final int YD_YFF_TASKERR_AUTHEN				= 213;			//安全认证失败身份认证失败
	public static final int YD_YFF_TASKERR_AUTHRESNO			= 214;			//安全认证失败客户编号不匹配
	public static final int YD_YFF_TASKERR_AUTHBUYTIMES			= 215;			//安全认证失败充值次数错误
	public static final int YD_YFF_TASKERR_AUTHMLIMIT			= 216;			//安全认证失败购电超囤积
	public static final int YD_YFF_TASKERR_AUTHADDR				= 217;			//安全认证失败地址异常
	public static final int YD_YFF_TASKERR_AUTHMETERUP			= 218;			//安全认证失败电表挂起
	public static final int YD_YFF_TASKERR_AUTHMREVER9			= 219;			//安全认证失败备用9
	public static final int YD_YFF_TASKERR_AUTHMREVER10			= 220;			//安全认证失败备用10
	public static final int YD_YFF_TASKERR_AUTHMREVER11			= 221;			//安全认证失败备用11
	public static final int YD_YFF_TASKERR_AUTHMREVER12			= 222;			//安全认证失败备用12
	public static final int YD_YFF_TASKERR_AUTHMREVER13			= 223;			//安全认证失败备用13
	public static final int YD_YFF_TASKERR_AUTHMREVER14			= 224;			//安全认证失败备用14
	public static final int YD_YFF_TASKERR_AUTHMREVER15			= 225;			//安全认证失败备用15
	public static final int YD_YFF_TASKERR_AUTHERROR			= 226;			//安全认证失败
	
	public static final int YD_YFF_TASKERR_TCCTRLERROR			= 230;			//透传返回未知功能码错误
	
	public static final int YD_YFF_TASKERR_TCZFEXECERR			= 240;			//不能执行转发
	public static final int YD_YFF_TASKERR_TCZFTIMEOUT			= 241;			//转发接收超时
	public static final int YD_YFF_TASKERR_TCZFRECEIVEERR		= 242;			//转发接收错误
	public static final int YD_YFF_TASKERR_TCZFSEC				= 243;			//转发接收确认
	public static final int YD_YFF_TASKERR_TCZFFAIL				= 244;			//转发接收否认
	public static final int YD_YFF_TASKERR_TCZFRECIEVEDATA		= 245;			//转发接收数据
	public static final int YD_YFF_TASKERR_NP645DATA			= 260;			//农排转发错误
	//20131105 end
	
	public static final int YFF_OPER_ERR_OK						= 0;			//正确
	public static final int YFF_OPER_ERR_OPER_TASKAPPTYPE		= 501;			//任务类型错误
	public static final int YFF_OPER_ERR_INPUTPARA				= 510;			//输入参数错误
	public static final int YFF_OPER_ERR_RTU					= 511;			//终端参数错误
	public static final int YFF_OPER_ERR_CUSSTATE				= 512;			//用户状态错误
	public static final int YFF_OPER_ERR_BUYNUM					= 513;			//购电次数错误
	public static final int YFF_OPER_ERR_JLPMETER				= 514;			//计量点或电表错误
	public static final int YFF_OPER_ERR_POWERREAF				= 515;			//动力关联错误
	public static final int YFF_OPER_ERR_FEERATE				= 516;			//费率方案错误
	public static final int YFF_OPER_ERR_ALARMPROJ				= 517;			//报警方案错误
	public static final int YFF_OPER_ERR_PTCT					= 518;			//PTCT错误
	public static final int YFF_OPER_ERR_FEERATECHG				= 519;			//费率切换错误
	public static final int YFF_OPER_ERR_LOADDATA				= 520;			//读取历史数据错误
	public static final int YFF_OPER_ERR_MONEYLIMIT				= 521;			//缴费金额大于囤积金额限值错误
	public static final int YFF_OPER_ERR_REVER					= 522;			//冲正错误
	public static final int YFF_OPER_ERR_PARA					= 523;			//参数错误
	public static final int YFF_OPER_ERR_WASTENO				= 524;			//流水号错误
	public static final int YFF_OPER_ERR_OPRECORD				= 525;			//生成预付费记录错误
	public static final int YFF_OPER_ERR_YFFSTATE				= 526;			//预付费状态错误
	public static final int YFF_OPER_ERR_NOVALIDRTU				= 527;			//没有有效的终端支持
	public static final int YFF_OPER_ERR_CALCTYPE				= 528;			//计费方式错误
	public static final int YFF_OPER_ERR_PAYADD					= 529;			//附加费错误
	//20120714
	public static final int YFF_OPER_ERR_POWRATE				= 530;			//力调电费错误
	//20120828
	public static final int YFF_OPER_EER_JSBCBD					= 531;			//结算补差表底比当前表底大,无法结算 
	public static final int YFF_OPER_EER_JSBCDATE				= 532;			//结算表底日期与后台结算表底日期不符
	public static final int YFF_OPER_EER_JSBCYM					= 533;			//后台正在进行结算,请稍后
	
	public static final int YFF_OPER_ERR_DBOP					= 540;			//数据库操作错误
	public static final int YFF_OPER_ERR_NORES					= 541;			//找不到用户错误
	public static final int YFF_OPER_ERR_FXDFNOREVER			= 542;			//已经发行电费不能冲正
	public static final int YFF_OPER_ERR_NOFXDFRECORD			= 543;			//无发行电费记录
	public static final int YFF_OPER_ERR_NOJTRESETRECORD		= 544;			//无阶梯清零记录
	
	public static final int YFF_OPER_ERR_AREA					= 545;			//区域参数错误
	public static final int YFF_OPER_ERR_OTHER					= 550;			//其它错误
	

	public static final int YFF_OPER_ERR_MASK_OK				= 0x00000000;	//分闸后表字继续走
	public static final int YFF_OPER_ERR_MASK_FZBZ				= 0x00000001;	//分闸后表字继续走
	public static final int YFF_OPER_ERR_MASK_BDFZ				= 0x00000002;	//表底飞走
	public static final int YFF_OPER_ERR_MASK_BDDZ				= 0x00000004;	//表底倒转
	public static final int YFF_OPER_ERR_MASK_DBDXJX			= 0x00000008;	//当前表底小于结算表底
	public static final int YFF_OPER_ERR_MASK_DBOUT				= 0x00000010;	//表底值溢出
	public static final int YFF_OPER_ERR_MASK_MONEYOUT			= 0x00000020;	//金额值溢出
	public static final int YFF_OPER_ERR_MASK_LNODATA			= 0x00000040;	//长时间无数据
	public static final int YFF_OPER_ERR_MASK_LNOPAY			= 0x00000080;	//长时间不缴费

	public static final int YFF_OPER_ERR_MASK_JTNOBEGIN		    = 0x00000400;		//阶梯算费时间未到
	public static final int YFF_OPER_ERR_MASK_JTSCHREPEAT	    = 0x00000800;		//阶梯重复切换
	public static final int YFF_OPER_ERR_MASK_JTSCHDATEERR	    = 0x00001000;		//阶梯切换表底时间异常
	public static final int YFF_OPER_ERR_MASK_JTPARAERR		    = 0x00002000;		//阶梯参数异常
	public static final int YFF_OPER_ERR_MASK_REPEATDF		    = 0x00004000;		//重复结算发行电费
	public static final int YFF_OPER_ERR_MASK_BDDATEERR		    = 0x00008000;		//结算发行电费表底时间异常
	
	public static final int YFF_OPER_ERR_MASK_ALL				= 0xFFFFFFFF;
	
	public static final int YFFMIS_ERR_SUECCED					= 0;			//成功
	public static final int YFFMIS_ERR_MISNOUSE					= 701;			//终端未使用MIS接口
	public static final int YFFMIS_ERR_DATABASE					= 702;			//数据库操作错误
	public static final int YFFMIS_ERR_CHECKPAYING				= 703;			//正在对账,请稍等
	public static final int YFFMIS_ERR_NOREVERCORD				= 704;			//无冲正记录
	public static final int YFFMIS_ERR_MISQUERY					= 711;			//连接MIS请求错误
	
	
	public static final String getYffErrCodeString(int errcode) {
		String ret_str = "未知错误";
		switch (errcode) {
		case YD_YFF_TASKERR_OK:					ret_str = "正确"; 						break;
		case YD_YFF_TASKERR_PARA:				ret_str = "参数不匹配"; 					break;
		case YD_YFF_TASKERR_MSG:			    ret_str = "消息发送错误"; 				break;
		case YD_YFF_TASKERR_WAIT_TIMEOUT:		ret_str = "等待超时"; 					break;
		case YD_YFF_TASKERR_COMNT_TIMEOUT:	    ret_str = "通讯超时"; 					break;
		case YD_YFF_TASKERR_TASKBUF_FULL:		ret_str = "任务缓存满"; 					break;
		case YD_YFF_TASKERR_RTU_UNVALID:		ret_str = "终端无效"; 					break;

		case YD_YFF_TASKERR_MP_UNVALID:			ret_str = "电表、计量点、电表通信端口";	break;
		case YD_YFF_TASKERR_ZJG_UNVALID:		ret_str = "电表、计量点、总加组相关"; 	break;
		case YD_YFF_TASKERR_RTU_APPTYPE:		ret_str = "应用类型错误"; 				break;
		case YD_YFF_TASKERR_RTU_PROTTYPE:		ret_str = "规约类型错误"; 				break;

		case YD_YFF_TASKERR_BACK_ERR:			ret_str = "（报文）返回错误"; 			break;
		case YD_YFF_TASKERR_BACK_FAIL:			ret_str = "任务返回失败"; 				break;
		case YD_YFF_TASKERR_BACK_DATAFAIL:		ret_str = "任务返回数据失败"; 			break;
		case YD_YFF_TASKERR_BACK_SHORT:			ret_str = "任务返回缺少数据或者任务结果"; break;

		case YD_YFF_TASKERR_FEE_ERR:			ret_str = "费率类型错误"; 				break;

		case YD_YFF_TASKERR_CANCEL:			    ret_str = "任务被取消"; 					break;
		case YD_YFF_TASKERR_TASKSTATE:		    ret_str = "任务状态错误"; 				break;
		case YD_YFF_TASKERR_TASKPARA:			ret_str = "任务参数错误"; 				break;
		case YD_YFF_TASKERR_ENDBYFORCE:			ret_str = "任务被强制结束"; 				break;

		case YD_YFF_TASKERR_CALLIDERR:			ret_str = "无调用动态库函数"; 			break;
		case YD_YFF_TASKERR_CRYPERR:			ret_str = "加密机返回失败"; 				break;

		case YD_YFF_TASKERR_OTHER:			    ret_str = "其他错误"; 					break;

		case YD_YFF_TASKERR_QBQR:				ret_str = "全部确认"; 					break;
		case YD_YFF_TASKERR_QBFR:				ret_str = "全部否认"; 					break;
		case YD_YFF_TASKERR_SDQF:				ret_str = "部分确认"; 					break;
		case YD_YFF_TASKERR_PROT_OTHER:			ret_str = "通讯规约中未知错误"; 			break;

		case YD_YFF_TASKERR_PROT_645		:	ret_str = " 645报文错误 ";          		break;
		case YD_YFF_TASKERR_READERROR		:   ret_str = " 读数据失败";                 break;
		case YD_YFF_TASKERR_WRITEERROR		:   ret_str = " 写数据失败";                 break;
		case YD_YFF_TASKERR_XLERROR			:   ret_str = " 清需量失败";                 break;
		case YD_YFF_TASKERR_DLERROR			:   ret_str = " 清电量失败";                 break;
		case YD_YFF_TASKERR_EVENTERROR		:   ret_str = " 清事件失败";                 break;
		case YD_YFF_TASKERR_REMOTECTRL		:   ret_str = " 远程控制失败";                break;
		case YD_YFF_TASKERR_AUTHOTHER		:   ret_str = " 安全认证失败其他错误";        break;
		case YD_YFF_TASKERR_AUTHCFCZ		:	ret_str = " 安全认证失败重复充值";        break;
		case YD_YFF_TASKERR_AUTHESAM		:	ret_str = " 安全认证失败ESAM验证失败";	 break;
		case YD_YFF_TASKERR_AUTHEN			:   ret_str = " 安全认证失败身份认证失败";     break;
		case YD_YFF_TASKERR_AUTHRESNO		:   ret_str = " 安全认证失败客户编号不匹配";   break;
		case YD_YFF_TASKERR_AUTHBUYTIMES	:	ret_str = " 安全认证失败充值次数错误";     break;
		case YD_YFF_TASKERR_AUTHMLIMIT		:   ret_str = " 安全认证失败购电超囤积";       break;
		case YD_YFF_TASKERR_AUTHADDR		:	ret_str = " 安全认证失败地址异常";         break;
		case YD_YFF_TASKERR_AUTHMETERUP		:   ret_str = " 安全认证失败电表挂起";         break;
		case YD_YFF_TASKERR_AUTHMREVER9		:   ret_str = " 安全认证失败备用9";            break;
		case YD_YFF_TASKERR_AUTHMREVER10	:	ret_str = " 安全认证失败备用10";           break;
		case YD_YFF_TASKERR_AUTHMREVER11	:	ret_str = " 安全认证失败备用11";           break;
		case YD_YFF_TASKERR_AUTHMREVER12	:	ret_str = " 安全认证失败备用12";           break;
		case YD_YFF_TASKERR_AUTHMREVER13	:	ret_str = " 安全认证失败备用13";           break;
		case YD_YFF_TASKERR_AUTHMREVER14	:	ret_str = " 安全认证失败备用14";           break;
		case YD_YFF_TASKERR_AUTHMREVER15	:	ret_str = " 安全认证失败备用15";           break;
		case YD_YFF_TASKERR_AUTHERROR		:   ret_str = " 安全认证失败";          		  break;
		case YD_YFF_TASKERR_TCCTRLERROR		:   ret_str = " 透传返回未知功能码错误";        break;
		case YD_YFF_TASKERR_TCZFEXECERR		:   ret_str = " 不能执行转发 ";          		break;
		case YD_YFF_TASKERR_TCZFTIMEOUT		:   ret_str = " 转发接收超时 ";          		break;
		case YD_YFF_TASKERR_TCZFRECEIVEERR	:   ret_str = " 转发接收错误 ";          		break;
		case YD_YFF_TASKERR_TCZFSEC			:   ret_str = " 转发接收确认 ";          		break;
		case YD_YFF_TASKERR_TCZFFAIL		:	ret_str = " 转发接收否认 ";          		break;
		case YD_YFF_TASKERR_TCZFRECIEVEDATA	:   ret_str = " 转发接收数据 ";          		break;
		                                                                 

		case YD_YFF_TASKERR_NP645DATA:			ret_str = "农排转发错误"; 				break;
		
		case YFF_OPER_ERR_OPER_TASKAPPTYPE:		ret_str = "任务类型错误";				break;
		case YFF_OPER_ERR_INPUTPARA:			ret_str = "输入参数错误";				break;
		case YFF_OPER_ERR_RTU:					ret_str = "终端参数错误";				break;
		case YFF_OPER_ERR_CUSSTATE:				ret_str = "用户状态错误";				break;
		case YFF_OPER_ERR_BUYNUM:				ret_str = "购电次数错误";				break;
		case YFF_OPER_ERR_JLPMETER:				ret_str = "计量点或电表错误";			break;
		case YFF_OPER_ERR_POWERREAF:			ret_str = "动力关联错误";				break;
		case YFF_OPER_ERR_FEERATE:				ret_str = "费率方案错误";				break;
		case YFF_OPER_ERR_ALARMPROJ:			ret_str = "报警方案错误";				break;
		case YFF_OPER_ERR_PTCT:					ret_str = "PTCT错误";					break;				
		case YFF_OPER_ERR_FEERATECHG:			ret_str = "费率切换错误";				break;			
		case YFF_OPER_ERR_LOADDATA:				ret_str = "读取历史数据错误";			break;				
		case YFF_OPER_ERR_MONEYLIMIT:			ret_str = "缴费金额大于囤积金额限值错误";	break;			
		case YFF_OPER_ERR_REVER:				ret_str = "冲正错误";					break;
		case YFF_OPER_ERR_PARA:					ret_str = "参数错误";					break;
		case YFF_OPER_ERR_WASTENO:				ret_str = "流水号错误";					break;
		case YFF_OPER_ERR_OPRECORD:				ret_str = "生成预付费记录错误";			break;		
		case YFF_OPER_ERR_YFFSTATE:				ret_str = "预付费状态错误";				break;		
		case YFF_OPER_ERR_NOVALIDRTU:			ret_str = "没有有效的终端支持";			break;		
		case YFF_OPER_ERR_CALCTYPE:				ret_str = "计费方式错误";				break;
		case YFF_OPER_ERR_PAYADD:				ret_str = "附加费错误";					break;
		case YFF_OPER_ERR_POWRATE:				ret_str = "力调电费错误";				break;								
		case YFF_OPER_EER_JSBCBD:				ret_str = "结算补差表底比当前表底大,无法结算,请检查通讯或换表记录。";break;		
		case YFF_OPER_EER_JSBCDATE:				ret_str = "结算表底日期与后台结算表底日期不符"	;break;
		case YFF_OPER_EER_JSBCYM:				ret_str = "后台正在进行结算,请稍后";		break;						
		case YFF_OPER_ERR_DBOP:					ret_str = "数据库操作错误";				break;
		case YFF_OPER_ERR_NORES:				ret_str = "找不到用户错误";				break;
		case YFF_OPER_ERR_FXDFNOREVER:	        ret_str = "已经发行电费不能冲正";        break;
		case YFF_OPER_ERR_NOFXDFRECORD:	        ret_str = "无发行电费记录";              break;
		case YFF_OPER_ERR_NOJTRESETRECORD:      ret_str = "无阶梯清零记录";              break;
		case YFF_OPER_ERR_AREA:					ret_str = "区域参数错误";				break;
		
		case YFF_OPER_ERR_OTHER:				ret_str = "其它错误";					break;
		
		case YFF_OPER_ERR_MASK_JTNOBEGIN:		ret_str = "阶梯算费时间未到";			break;
		case YFF_OPER_ERR_MASK_JTSCHREPEAT:		ret_str = "阶梯重复切换";				break;
		case YFF_OPER_ERR_MASK_JTSCHDATEERR:	ret_str = "阶梯切换表底时间异常";			break;
		case YFF_OPER_ERR_MASK_JTPARAERR:		ret_str = "阶梯参数异常";			break;
		case YFF_OPER_ERR_MASK_REPEATDF:		ret_str = "重复结算发行电费";			break;
		case YFF_OPER_ERR_MASK_BDDATEERR:		ret_str = "结算发行电费表底时间异常";		break;

		//MIS相关
		case YFFMIS_ERR_MISNOUSE:				ret_str = "终端未使用MIS接口";			break;
		case YFFMIS_ERR_DATABASE:				ret_str = "数据库操作错误";				break;
		case YFFMIS_ERR_CHECKPAYING:			ret_str = "正在对账,请稍等";				break;
		case YFFMIS_ERR_NOREVERCORD:			ret_str = "无冲正记录";					break;
		case YFFMIS_ERR_MISQUERY:				ret_str = "连接MIS请求错误";				break;
		}		
		return ret_str;
	}
}

