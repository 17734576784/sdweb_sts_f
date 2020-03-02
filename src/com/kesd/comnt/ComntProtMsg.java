package com.kesd.comnt;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.WebConfig;
import com.kesd.common.YDTable;
import com.kesd.common.YFFDef;
import com.kesd.dbpara.YffAlarmPara;
import com.kesd.dbpara.YffRatePara;
import com.kesd.util.I18N;
import com.kesd.util.OnlineRtu;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
import com.libweb.comnt.ComntFunc;
import com.libweb.comnt.ComntStream;
import com.libweb.comnt.ComntVector;


public class ComntProtMsg {
	//低压通讯-召参数类型
	public static final int	YFF_DY_CALL_PARA					= 1;			//参数信息文件
	public static final int	YFF_DY_CALL_FEI1					= 2;			//一套费率
	public static final int	YFF_DY_CALL_FEI2					= 3;			//二套费率
	
	//20131021添加begin
	public static final int YFF_DY_CALL_JTFEI1     				= 5;   			//一套阶梯费率
	public static final int YFF_DY_CALL_JTFEI2     				= 6;   			//二套阶梯费率
	//end  

	public static final int	YFF_DY_CALL_STATE					= 21;			//状态查询过esam

	//低压通讯-设置用户基本参数类型
	public static final int	YFF_DY_SET_USERID					= 1;			//户号
	public static final int	YFF_DY_SET_CT						= 2;			//CT
	public static final int	YFF_DY_SET_PT						= 3;			//PT
	public static final int	YFF_DY_SET_ALARM1					= 4;			//ALARM1
	public static final int	YFF_DY_SET_ALARM2					= 5;			//ALARM2
	public static final int	YFF_DY_SET_CHGTIME					= 6;			//分时费率切换时间
	
	//20131021添加begin
	public static final int	YFF_DY_SET_METERNO     				= 7;   			//表号
	public static final int	YFF_DY_SET_JTCHGTIME    			= 8;   			//阶梯费率切换时间
	public static final int	YFF_DY_FEE_BLOCK     				= 9;   			//费率块  二版扩展
	public static final int	YFF_DY_JTFEE_BLOCK     				= 10;   		//阶梯费率块 二版扩展
	//end


	//低压通讯-设置费率
	public static final int	YFF_DY_FEE_Z						= 20;			//总
	public static final int	YFF_DY_FEE_J						= 21;			//尖
	public static final int	YFF_DY_FEE_F						= 22;			//峰
	public static final int	YFF_DY_FEE_P						= 23;			//平
	public static final int	YFF_DY_FEE_G						= 24;			//谷

	public static final int	YFF_DY_FEE_1						= 1;			//一套费率
	public static final int	YFF_DY_FEE_2						= 2;			//二套费率

	//低压通讯-控制类型
	public static final int	YFF_DY_CTRL_CUT						= 1;			//跳闸
	public static final int	YFF_DY_CTRL_ON						= 2;			//合闸允许
	public static final int	YFF_DY_CTRL_KEEPON					= 3;			//保电
	public static final int	YFF_DY_CTRL_KEEPOFF					= 4;			//取消保电
	public static final int	YFF_DY_CTRL_ALARMON					= 5;			//报警
	public static final int	YFF_DY_CTRL_ALARMOFF				= 6;			//报警解除
	
	//20131021添加(以前没有，但不是这次添加内容)begin
	public static final int	YFF_DY_CTRL_ONDIRECT				= 7;			//直接合闸
	public static final int	YFF_DY_CLEAR_DL						= 15;			//清电量
	public static final int	YFF_DY_CLEAR_XL						= 16;			//清需量
	public static final int	YFF_DY_CLEAR_EVENT					= 17;			//清事项
	//end
	
	


	//高压通讯-召参数类型
	public static final int	YFF_GY_CALL_PARAREMAIN				= 1;			//376 05 AFN = A  F47 61召预付费
	public static final int	YFF_GY_CALL_FEE						= 2;			//召费率
	public static final int	YFF_GY_CALL_APPEND					= 3;			//召付费附加值 扩展
	public static final int	YFF_GY_CALL_KEEP					= 4;			//查看科林扩展保电

	//高压通讯-设置参数
	public static final int	YFF_GY_SET_FEI						= 2;			//设置费率
	public static final int	YFF_GY_SET_APPEND					= 3;			//设置预付费附加值 扩展
	public static final int	YFF_GY_SET_KEEP						= 4;			//保电

	//高压通讯-控制
	public static final int	YFF_GY_CTRL_KEEPON					= 1;			//科林扩展保电
	public static final int	YFF_GY_CTRL_KEEPOFF					= 2;			//取消科林扩展保电
	public static final int	YFF_GY_CTRL_CUT						= 3;			//负控限电
	public static final int	YFF_GY_CTRL_ON						= 4;			//合闸允许
	public static final int	YFF_GY_CTRL_ALARMON					= 5;			//催费告警投入
	public static final int	YFF_GY_CTRL_ALARMOFF				= 6;			//催费告警解除


	//召测数据
	//召测一类实时数据 
	public static final int	YFF_CALL_GY_REMAIN					= 1;			//剩余金额专变
	public static final int	YFF_CALL_DY_REMAIN					= 2;			//电能表购、用电信息

	public static final int	YFF_CALL_REAL_ZBD					= 18;			//实时表底正向
	public static final int	YFF_CALL_REAL_FBD					= 19;			//实时表底反向

	//预付费召测二类日数据
	public static final int	YFF_CALL_DAY_ZBD					= 20;			//日表底正向
	public static final int	YFF_CALL_DAY_FBD					= 21;			//日表底反向


	//透传数据 通过集中器 但不过加密机
	public static final int	YFF_CALL_DB_TCREMAIN				= 40;			//剩余金额
	public static final int	YFF_CALL_DB_TCOVER					= 41;			//透支金额

	public static final int	YFF_CALL_DB_STATE1					= 45;			//运行状态字1
	public static final int	YFF_CALL_DB_STATE2					= 46;			//运行状态字2
	public static final int	YFF_CALL_DB_STATE3					= 47;			//运行状态字3
	public static final int	YFF_CALL_DB_STATE4					= 48;			//运行状态字4
	public static final int	YFF_CALL_DB_STATE5					= 49;			//运行状态字5
	public static final int	YFF_CALL_DB_STATE6					= 50;			//运行状态字6
	public static final int	YFF_CALL_DB_STATE7					= 51;			//运行状态字7

	//20131021添加begin
	public static final int	YFF_CALL_KEYNUM      				= 110; 			//密钥条数
	public static final int	YFF_CALL_KEYSTATE    				= 111;   		//密钥状态字
	//public static final int YFF_CALL_REVERMONEY	    		= 112;   		//退费金额
	public static final int	YFF_CALL_CARDSTATE     				= 113;   		//插卡状态字
	public static final int	YFF_CALL_FEI1      					= 114;   		//一套费率
	public static final int	YFF_CALL_FEI2      					= 115;   		//二套费率
	public static final int	YFF_CALL_JTFEI1     				= 116;  		//一套阶梯费率
	public static final int	YFF_CALL_JTFEI2     				= 117;  		//二套阶梯费率
	//end

	//返回数据 类型
	public static final int	YFF_DATATYPE_DY_YESNO				= 1;			//控制、参数设置、缴费
	public static final int	YFF_DATATYPE_DY_CALLPARA			= 2;			//参数信息文件
	public static final int	YFF_DATATYPE_DY_CALLFEI				= 3;			//费率
	public static final int	YFF_DATATYPE_DY_CALLSTATE			= 5;			//状态查询过esam
	
	//20131021添加begin
	public static final int	YFF_DATATYPE_DY_CALLFEIJT   		= 6;   			//阶梯费率
	//end


	public static final int	YFF_DATATYPE_GY_REALREMAIN			= 16;			//总加组当前剩余电费
	public static final int	YFF_DATATYPE_DY_REALREMAIN			= 17;			//电能表购、用电信息
	public static final int	YFF_DATATYPE_REAL_ZBD				= 18;			//正向实时表底
	public static final int	YFF_DATATYPE_REAL_FBD				= 19;			//反向实时表底

	public static final int	YFF_DATATYPE_DAY_ZBD				= 20;			//正向日表底
	public static final int	YFF_DATATYPE_DAY_FBD				= 21;			//反向日表底

	public static final int	YFF_DATATYPE_DB_TCREMAIN			= 32;			//透传电表剩余金额
	public static final int	YFF_DATATYPE_DB_TCOVER				= 33;			//透传电表透支金额
	public static final int	YFF_DATATYPE_DB_TCSTATE1			= 34;			//透传电表状态1
	public static final int	YFF_DATATYPE_DB_TCSTATE2			= 35;			//透传电表状态2
	public static final int	YFF_DATATYPE_DB_TCSTATE3			= 36;			//透传电表状态3
	public static final int	YFF_DATATYPE_DB_TCSTATE4			= 37;			//透传电表状态4
	public static final int	YFF_DATATYPE_DB_TCSTATE5			= 38;			//透传电表状态5
	public static final int	YFF_DATATYPE_DB_TCSTATE6			= 39;			//透传电表状态6
	public static final int	YFF_DATATYPE_DB_TCSTATE7			= 40;			//透传电表状态7
	
	///////////////////////////3月2日注释。更新为新的值。、
	//为低压透传留出足够的空间 100 --250
	/*
	public static final int	YFF_DATATYPE_GY_YESNO				= 51;			//控制、参数设置、缴费  召测错误数据
	public static final int	YFF_DATATYPE_GY_CALLPARAREMAIN		= 52;			//召测预付费数据
	public static final int	YFF_DATATYPE_GY_CALLFEE				= 53;			//召测费率信息
	public static final int	YFF_DATATYPE_GY_CALLAPPEND			= 54;			//召测附加值
	public static final int	YFF_DATATYPE_GY_CALLKEEP			= 55;			//召测保电信息

	//业务操作返回数据类型3月2日注释。更新为新的值。
	public static final int	YFF_DATATYPE_OPER_IDX				= 100;			//预付费操作索引
	public static final int	YFF_DATATYPE_DYOPER_PARASTATE		= 101;			//低压预付费参数及状态
	public static final int	YFF_DATATYPE_GYOPER_PARASTATE		= 102;			//高压预付费参数及状态
	public static final int	YFF_DATATYPE_OPER_GETREMAIN			= 103;			//获得高低压的截至到某天的预付费

	public static final int	YFF_DATATYPE_OPER_PAUSEALARM		= 110;			//暂停预付费报警及控制
	public static final int	YFF_DATATYPE_OPER_GLOPROT			= 110;			//全局保电参数
	*/
/////////////////////////////////////
	//20131021添加begin
	public static final int  YFF_DATATYPE_DB_TCKEYNUM   		= 100;   		//密钥条数
	public static final int  YFF_DATATYPE_DB_TCKEYSTATE   		= 101;   		//密钥状态字
	//public static final int YFF_DATATYPE_DB_TCREVERMONEY  	= 102;   		//退费金额
	public static final int  YFF_DATATYPE_DB_TCCARDSTATE   		= 103;   		//插卡状态字
	public static final int  YFF_DATATYPE_DB_TCFEI1    			= 114;   		//一套费率
	public static final int  YFF_DATATYPE_DB_TCFEI2    			= 115;   		//二套费率
	public static final int  YFF_DATATYPE_DB_TCJTFEI1   		= 116;   		//一套阶梯费率
	public static final int  YFF_DATATYPE_DB_TCJTFEI2   		= 117;   		//二套阶梯费率
	//end



	public static final int YFF_DATATYPE_GY_YESNO				= 251;			//控制、参数设置、缴费  召测错误数据
	public static final int	YFF_DATATYPE_GY_CALLPARAREMAIN		= 252;			//召测预付费数据
	public static final int	YFF_DATATYPE_GY_CALLFEE				= 253;			//召测费率信息
	public static final int	YFF_DATATYPE_GY_CALLAPPEND			= 254;			//召测附加值
	public static final int	YFF_DATATYPE_GY_CALLKEEP			= 255;			//召测保电信息
	
	//业务操作返回数据类型
	public static final int	YFF_DATATYPE_OPER_IDX				= 300;			//预付费操作索引
	public static final int	YFF_DATATYPE_DYOPER_PARASTATE		= 301;			//低压预付费参数及状态
	public static final int	YFF_DATATYPE_GYOPER_PARASTATE		= 302;			//高压预付费参数及状态
	public static final int	YFF_DATATYPE_OPER_GETREMAIN			= 303;			//获得高低压的截至到某天的预付费
	public static final int	YFF_DATATYPE_OPER_GETJSBCREMAIN		= 304;			//获得高低压的结算补差的余额
	public static final int	YFF_DATATYPE_NPOPER_PARASTATE		= 305;			//农排预付费参数及状态
	
	public static final int	YFF_DATATYPE_OPER_PAUSEALARM		= 310;			//暂停预付费报警及控制
	public static final int	YFF_DATATYPE_OPER_GLOPROT			= 311;			//全局保电参数


	
	//农排通讯
	//农灌表用户用电记录
	public static final int YFF_CALL_DL645_YDJL_ALL				= 8000;
	public static final int YFF_CALL_DL645_YDJL_NUM				= 8001;
	public static final int YFF_CALL_DL645_YDJL_FIR				= 8002;
	public static final int YFF_CALL_DL645_YDJL_FIN				= 9001;

	public static final int YFF_CALL_DL645_DLJL					= 9011;

	//存储20条挂起用户记录
	public static final int YFF_CALL_DL645_GQJL_ALL				= 9020;
	public static final int YFF_CALL_DL645_GQJL_NUM				= 9021;
	public static final int YFF_CALL_DL645_GQJL_FIR				= 9022;
	public static final int YFF_CALL_DL645_GQJL_FIN				= 9041;

	//存储最近12个月的结算电量
	public static final int YFF_CALL_DL645_JSJL_ALL				= 9050;
	public static final int YFF_CALL_DL645_JSJL_FIR				= 9052;
	public static final int YFF_CALL_DL645_JSJL_FIN				= 9063;

	//存储最近50条参数设置记录
	public static final int YFF_CALL_DL645_CSJL_ALL				= 9070;
	public static final int YFF_CALL_DL645_CSJL_NUM				= 9071;
	public static final int YFF_CALL_DL645_CSJL_FIR				= 9072;
	public static final int YFF_CALL_DL645_CSJL_FIN				= 9121;

	//存储20条刷卡故障用户记录
	public static final int YFF_CALL_DL645_GZJL_ALL				= 9150;
	public static final int YFF_CALL_DL645_GZJL_NUM				= 9151;
	public static final int YFF_CALL_DL645_GZJL_FIR				= 9152;
	public static final int YFF_CALL_DL645_GZJL_FIN				= 9171;

	//存储用户的过零电量
	public static final int YFF_CALL_DL645_GLJL_ALL				= 9180;
	public static final int YFF_CALL_DL645_GLJL_NUM				= 9181;
	public static final int YFF_CALL_DL645_GLJL_FIR				= 9182;
	public static final int YFF_CALL_DL645_GLJL_FIN				= 9201;
	
	//召测费率电价
	public static final int YFF_CALL_DL645_CALL_FEE				= 9231;
	//召测区域号
	public static final int YFF_CALL_DL645_CALL_AREA			= 9233;
	//召测区变比
	public static final int YFF_CALL_DL645_CALL_RATE			= 9235;
	//召测报警金额
	public static final int YFF_CALL_DL645_CALL_ALARM			= 9237;
	//召测限定功率
	public static final int YFF_CALL_DL645_CALL_POWLINIT		= 9239;
	//召测无采样自动断电时间
	public static final int YFF_CALL_DL645_CALL_NOCYCUT			= 9241;
	//召测电表锁定状态
	public static final int YFF_CALL_DL645_CALL_LOCK			= 9243;

	//设置费率电价
	public static final int YFF_CALL_DL645_SET_FEE				= 9232;
	//设置区域号
	public static final int YFF_CALL_DL645_SET_AREA				= 9234;
	//设置区变比
	public static final int YFF_CALL_DL645_SET_RATE				= 9236;
	//设置报警金额
	public static final int YFF_CALL_DL645_SET_ALARM			= 9238;
	//设置限定功率
	public static final int YFF_CALL_DL645_SET_POWLINIT			= 9240;
	//设置无采样自动断电时间
	public static final int YFF_CALL_DL645_SET_NOCYCUT			= 9242;
	//设置电表锁定状态
	public static final int YFF_CALL_DL645_SET_LOCK				= 9244;
	
	//清空20条挂起记录
	public static final int YFF_CALL_DL645_CLEAR_GQJL_FIR		= 9252;
	public static final int YFF_CALL_DL645_CLEAR_GQJL_FIN		= 9271;
	
	//召测费率电价
	public static final int YFF_DATATYPE_NP_CALL_FEE			= 9231;
	//召测区域号
	public static final int YFF_DATATYPE_NP_CALL_AREA			= 9233;
	//召测区变比
	public static final int YFF_DATATYPE_NP_CALL_RATE			= 9235;
	//召测报警金额
	public static final int YFF_DATATYPE_NP_CALL_ALARM			= 9237;
	//召测限定功率
	public static final int YFF_DATATYPE_NP_CALL_POWLINIT		= 9239;
	//召测无采样自动断电时间
	public static final int YFF_DATATYPE_NP_CALL_NOCYCUT		= 9241;
	//召测电表锁定状态
	public static final int YFF_DATATYPE_NP_CALL_LOCK			= 9243;
	
	
	
	//20130327
	//召测/设置黑名单启停标志
	public static final int YFF_CALL_DL645_CALL_BL_FLAG			= 9300;
	public static final int YFF_CALL_DL645_SET_BL_FLAG			= 9301;
	//添加/删除黑名单
	public static final int YFF_CALL_DL645_SET_BL_ITEM			= 9310;
	//清除黑名单
	public static final int YFF_CALL_DL645_CLR_BL				= 9320;

	//召测/设置白名单启停标志
	public static final int YFF_CALL_DL645_CALL_WL_FLAG			= 9330;
	public static final int YFF_CALL_DL645_SET_WL_FLAG			= 9331;
	//添加/删除白名单
	public static final int YFF_CALL_DL645_SET_WL_ITEM			= 9340;
	//清除白名单
	public static final int YFF_CALL_DL645_CLR_WL				= 9350;

	//召测黑名单
	public static final int YFF_CALL_DL645_CALL_BL_FIR			= 9400;
	public static final int YFF_CALL_DL645_CALL_BL_FIN			= 9499;

	//召测白名单
	public static final int YFF_CALL_DL645_CALL_WL_FIR			= 9600;
	public static final int YFF_CALL_DL645_CALL_WL_FIN			= 9699;
	
	//20130327
	//召测/设置黑名单启停标志
	public static final int YFF_DATATYPE_NP_CALL_BL_FLAG		= 9300;

	//召测/设置白名单启停标志
	public static final int YFF_DATATYPE_NP_CALL_WL_FLAG		= 9330;

	//召测黑名单
	public static final int YFF_DATATYPE_NP_CALL_BL_FIR			= 9400;
	public static final int YFF_DATATYPE_NP_CALL_BL_FIN			= 9499;

	//召测白名单
	public static final int YFF_DATATYPE_NP_CALL_WL_FIR			= 9600;
	public static final int YFF_DATATYPE_NP_CALL_WL_FIN			= 9699;
	
	
	//******************************************//
	//			河南清丰农排表定义
	//				      开始
	//******************************************//
	
	//农灌表用户用电记录
//	public static final int YFF_CALL_HNQF_YDJL_FIR				= 6500501;
//	public static final int YFF_CALL_HNQF_YDJL_FIN				= 6500600;
//
//	//用电挂起记录
//	public static final int YFF_CALL_HNQF_GQJL_FIR				= 6500701;
//	public static final int YFF_CALL_HNQF_GQJL_FIN				= 6500703;
//	//刷卡故障记录
//	public static final int YFF_CALL_HNQF_GZJL_FIR				= 6500711;
//	public static final int YFF_CALL_HNQF_GZJL_FIN				= 6500713;
//	//参数设置记录
//	public static final int YFF_CALL_HNQF_CSJL_FIR				= 6500721;
//	public static final int YFF_CALL_HNQF_CSJL_FIN				= 6500725;
	
	
	//农灌表用户用电记录
//	public static final int YFF_CALL_YDJL_FIR					= 10001;       
//	public static final int YFF_CALL_YDJL_FIN					= 10100;       
//	//用电挂起记录                                                   
//	public static final int YFF_CALL_GQJL_FIR					= 10201;       
//	public static final int YFF_CALL_GQJL_FIN					= 10203;       
//	//刷卡故障记录                                                   
//	public static final int YFF_CALL_GZJL_FIR					= 10211;       
//	public static final int YFF_CALL_GZJL_FIN					= 10213;       
//	//参数设置记录                                                   
//	public static final int YFF_CALL_CSJL_FIR					= 10221;       
//	public static final int YFF_CALL_CSJL_FIN					= 10225;       
	                                                                 
	public static final int YFF_CALL_HNQF_YDJL_FIR					= 10001;       
	public static final int YFF_CALL_HNQF_YDJL_FIN					= 10100;       
	//用电挂起记录                                                   
	public static final int YFF_CALL_HNQF_GQJL_FIR					= 10201;       
	public static final int YFF_CALL_HNQF_GQJL_FIN					= 10203;       
	//刷卡故障记录                                                   
	public static final int YFF_CALL_HNQF_GZJL_FIR					= 10211;       
	public static final int YFF_CALL_HNQF_GZJL_FIN					= 10213;       
	//参数设置记录                                                   
	public static final int YFF_CALL_HNQF_CSJL_FIR					= 10221;       
	public static final int YFF_CALL_HNQF_CSJL_FIN					= 10225;
	
	public static final int YFF_DATATYPE_NP_CALL_YDJL_FIR			= 10001; 
	public static final int YFF_DATATYPE_NP_CALL_YDJL_FIN			= 10100; 
		//用电挂起记录                                                   
	public static final int YFF_DATATYPE_NP_CALL_GQJL_FIR			= 10201; 
	public static final int YFF_DATATYPE_NP_CALL_GQJL_FIN			= 10203; 
		//刷卡故障记录                                                   
	public static final int YFF_DATATYPE_NP_CALL_GZJL_FIR			= 10211; 
	public static final int YFF_DATATYPE_NP_CALL_GZJL_FIN			= 10213; 
		//参数设置记录                                                   
	public static final int YFF_DATATYPE_NP_CALL_CSJL_FIR			= 10221; 
	public static final int YFF_DATATYPE_NP_CALL_CSJL_FIN			= 10225; 

	
	//******************************************//
	//			河南清丰农排表定义
	//				     结束
	//******************************************//
	
		
	
	
	
	public static final int	YFF_DATATYPE_NULL					= 0;			//未知
	public static final int	YFF_DATATYPE_YES					= 1;			//成功
	public static final int	YFF_DATATYPE_NO						= 2;			//失败

	public static final int	YFF_ERRTYPE_SYS						= 0;			//系统定义
	public static final int	YFF_ERRTYPE_PROT					= 1;			//规约返回 
	public static final int	YFF_ERRTYPE_CRYP					= 2;			//加密机

	//通讯报文及解析
	public static final int	YFF_RAWTYPE_DATATX					= 1;			//前置发送报文
	public static final int	YFF_RAWTYPE_DATARX					= 2;			//终端返回报文
	public static final int	YFF_RAWTYPE_DATACRYP				= 3;			//加密机通讯报文
 
	//[消息相关,请不要随意改动]
	public static class YFFGYSET_PROTECT{
		public byte		bdbegin;			//保电开始
		public byte		bdend;				//保电结束
		public byte		ctrlflag;			//控制标志	376 05
		
		public static void setYFFGYSET_PROTECT(YFFGYSET_PROTECT para, YFFGYSET_PROTECT setValue){
			if(setValue == null){
				para = new YFFGYSET_PROTECT();
			}else{
				para.bdbegin	= setValue.bdbegin;
				para.bdend		= setValue.bdend;
				para.ctrlflag	= setValue.ctrlflag;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, YFFGYSET_PROTECT para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.bdbegin);
			ret_len += ComntStream.writeStream(byte_vect, para.bdend);
			ret_len += ComntStream.writeStream(byte_vect, para.ctrlflag);
			
			return ret_len;
		}
		
		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFFGYSET_PROTECT para){
			
			int ret_len = len;
			
			para.bdbegin = ComntStream.readStream(byte_vect, offset + ret_len, para.bdbegin); 
			ret_len += ComntStream.getDataSize(para.bdbegin);
			
			para.bdend = ComntStream.readStream(byte_vect, offset + ret_len, para.bdend); 
			ret_len += ComntStream.getDataSize(para.bdend);
			
			para.ctrlflag = ComntStream.readStream(byte_vect, offset + ret_len, para.ctrlflag); 
			ret_len += ComntStream.getDataSize(para.ctrlflag);
			
			return ret_len;
		}
	}
	
	//[消息相关,请不要随意改动]
	public static class YFFGYCTRL_CUT{
		public int		ctrltype;			//控制类型
		public byte		ctrlroll;			//开关轮次
		public byte		limithour;			//限电小时
		public byte		limitmin;			//限电分钟
		public short	plustime;			//脉冲时长
		public byte		delaytime;			//延时时间
		
		public static void setYFFGYCTRL_CUT(YFFGYCTRL_CUT para, YFFGYCTRL_CUT setValue){
			if(setValue == null){
				para = new YFFGYCTRL_CUT();
			}else{
				para.ctrltype	= setValue.ctrltype;
				para.ctrlroll	= setValue.ctrlroll;
				para.limithour	= setValue.limithour;
				
				para.limitmin	= setValue.limitmin;
				para.plustime	= setValue.plustime;
				para.delaytime	= setValue.delaytime;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, YFFGYCTRL_CUT para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.ctrltype);
			ret_len += ComntStream.writeStream(byte_vect, para.ctrlroll);
			ret_len += ComntStream.writeStream(byte_vect, para.limithour);
			
			ret_len += ComntStream.writeStream(byte_vect, para.limitmin);
			ret_len += ComntStream.writeStream(byte_vect, para.plustime);
			ret_len += ComntStream.writeStream(byte_vect, para.delaytime);
			
			return ret_len;
		}
		
		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFFGYCTRL_CUT para){
			
			int ret_len = len;
			
			para.ctrltype = ComntStream.readStream(byte_vect, offset + ret_len, para.ctrltype); 
			ret_len += ComntStream.getDataSize(para.ctrltype);
			
			para.ctrlroll = ComntStream.readStream(byte_vect, offset + ret_len, para.ctrlroll); 
			ret_len += ComntStream.getDataSize(para.ctrlroll);
			
			para.limithour = ComntStream.readStream(byte_vect, offset + ret_len, para.limithour); 
			ret_len += ComntStream.getDataSize(para.limithour);
			
			para.limitmin = ComntStream.readStream(byte_vect, offset + ret_len, para.limitmin); 
			ret_len += ComntStream.getDataSize(para.limitmin);
			
			para.plustime = ComntStream.readStream(byte_vect, offset + ret_len, para.plustime); 
			ret_len += ComntStream.getDataSize(para.plustime);
			
			para.delaytime = ComntStream.readStream(byte_vect, offset + ret_len, para.delaytime); 
			ret_len += ComntStream.getDataSize(para.delaytime);
			
			return ret_len;
		}
	}

	
	
	//[消息相关,请不要随意改动]
	public static class YFFDY_REMAIN_MONEY	{
		public int			read_date;			//数据日期	yyyy-mm--dd
		public int			read_time;			//数据时间	hh--mm--00
	
		public int			pay_num;			//购电次数
	
		public float		remain_money;		//剩余金额
		public float		total_money;		//累计购电金额
		
		public float		remain_dl;			//剩余电量
		public float		overd_dl;			//透支电量
		public float		total_dl;			//累计购电量
		public float		tick_dl;			//赊欠门限电量
		public float		alarm_dl;			//报警电量
		public float		fail_dl;			//故障电量
		
		public static void setYFFDY_REMAIN_MONEY(YFFDY_REMAIN_MONEY para, YFFDY_REMAIN_MONEY setValue){
			if(setValue == null){
				para = new YFFDY_REMAIN_MONEY();
			}else{
				para.read_date	= setValue.read_date;
				para.read_time	= setValue.read_time;
				para.pay_num	= setValue.pay_num;
				para.remain_money	= setValue.remain_money;
				para.total_money	= setValue.total_money;
				
				para.remain_dl	= setValue.remain_dl;
				para.overd_dl	= setValue.overd_dl;
				para.total_dl	= setValue.total_dl;
				para.tick_dl	= setValue.tick_dl;
				para.alarm_dl	= setValue.alarm_dl;
				para.fail_dl	= setValue.fail_dl;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, YFFDY_REMAIN_MONEY para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.read_date);
			ret_len += ComntStream.writeStream(byte_vect, para.read_time);
			ret_len += ComntStream.writeStream(byte_vect, para.pay_num);
			ret_len += ComntStream.writeStream(byte_vect, para.remain_money);
			ret_len += ComntStream.writeStream(byte_vect, para.total_money);
			
			ret_len += ComntStream.writeStream(byte_vect, para.remain_dl);
			ret_len += ComntStream.writeStream(byte_vect, para.overd_dl);
			ret_len += ComntStream.writeStream(byte_vect, para.total_dl);
			ret_len += ComntStream.writeStream(byte_vect, para.tick_dl);
			ret_len += ComntStream.writeStream(byte_vect, para.alarm_dl);
			ret_len += ComntStream.writeStream(byte_vect, para.fail_dl);
			
			return ret_len;
		}
		
		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFFDY_REMAIN_MONEY para){
			
			int ret_len = len;
			
			para.read_date = ComntStream.readStream(byte_vect, offset + ret_len, para.read_date); 
			ret_len += ComntStream.getDataSize(para.read_date);
			
			para.read_time = ComntStream.readStream(byte_vect, offset + ret_len, para.read_time); 
			ret_len += ComntStream.getDataSize(para.read_time);
			
			para.pay_num = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_num); 
			ret_len += ComntStream.getDataSize(para.pay_num);
			
			para.remain_money = ComntStream.readStream(byte_vect, offset + ret_len, para.remain_money); 
			ret_len += ComntStream.getDataSize(para.remain_money);
			
			para.total_money = ComntStream.readStream(byte_vect, offset + ret_len, para.total_money); 
			ret_len += ComntStream.getDataSize(para.total_money);
			
			para.remain_dl = ComntStream.readStream(byte_vect, offset + ret_len, para.remain_dl); 
			ret_len += ComntStream.getDataSize(para.remain_dl);
			
			para.overd_dl = ComntStream.readStream(byte_vect, offset + ret_len, para.overd_dl); 
			ret_len += ComntStream.getDataSize(para.overd_dl);
			
			para.total_dl = ComntStream.readStream(byte_vect, offset + ret_len, para.total_dl); 
			ret_len += ComntStream.getDataSize(para.total_dl);
			
			para.tick_dl = ComntStream.readStream(byte_vect, offset + ret_len, para.tick_dl); 
			ret_len += ComntStream.getDataSize(para.tick_dl);
			
			para.alarm_dl = ComntStream.readStream(byte_vect, offset + ret_len, para.alarm_dl); 
			ret_len += ComntStream.getDataSize(para.alarm_dl);
			
			para.fail_dl = ComntStream.readStream(byte_vect, offset + ret_len, para.fail_dl); 
			ret_len += ComntStream.getDataSize(para.fail_dl);
			 
			return ret_len;
		}
	}
	
	
	
	//[消息相关,请不要随意改动]
	public static class YFFDBBD_DATA	{
		public int			ymd;				//yyyy-mm-dd
		
		public double		cur_zyz;			//当前总表底
		public double		cur_zyf;			//当前峰表底
		public double		cur_zyp;			//当前平表底
		public double		cur_zyg;			//当前谷表底
		public double		cur_zyj;			//当前尖表底
		
		public double		cur_fyz;			//当前总表底
		public double		cur_fyf;			//当前峰表底
		public double		cur_fyp;			//当前平表底
		public double		cur_fyg;			//当前谷表底
		public double		cur_fyj;			//当前尖表底
		
		//20120723
		public double		cur_zwz;			//当前正无总表底
		public double		cur_fwz;			//当前反无总表底
		
		public static void setYFFDBBD_DATA(YFFDBBD_DATA para, YFFDBBD_DATA setValue){
			if(setValue == null){
				para = new YFFDBBD_DATA();
			}else{
				para.ymd	= setValue.ymd;
				
				para.cur_zyz	= setValue.cur_zyz;
				para.cur_zyf	= setValue.cur_zyf;
				para.cur_zyp	= setValue.cur_zyp;
				para.cur_zyg	= setValue.cur_zyg;
				para.cur_zyj	= setValue.cur_zyj;
				
				para.cur_fyz	= setValue.cur_fyz;
				para.cur_fyf	= setValue.cur_fyf;
				para.cur_fyp	= setValue.cur_fyp;
				para.cur_fyg	= setValue.cur_fyg;
				para.cur_fyj	= setValue.cur_fyj;
				
				para.cur_zwz	= setValue.cur_zwz;
				para.cur_fwz	= setValue.cur_fwz;			
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, YFFDBBD_DATA para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.ymd);
			
			ret_len += ComntStream.writeStream(byte_vect, para.cur_zyz);
			ret_len += ComntStream.writeStream(byte_vect, para.cur_zyf);
			ret_len += ComntStream.writeStream(byte_vect, para.cur_zyp);
			ret_len += ComntStream.writeStream(byte_vect, para.cur_zyg);
			ret_len += ComntStream.writeStream(byte_vect, para.cur_zyj);
			
			ret_len += ComntStream.writeStream(byte_vect, para.cur_fyz);
			ret_len += ComntStream.writeStream(byte_vect, para.cur_fyf);
			ret_len += ComntStream.writeStream(byte_vect, para.cur_fyp);
			ret_len += ComntStream.writeStream(byte_vect, para.cur_fyg);
			ret_len += ComntStream.writeStream(byte_vect, para.cur_fyj);
			
			ret_len += ComntStream.writeStream(byte_vect, para.cur_zwz);
			ret_len += ComntStream.writeStream(byte_vect, para.cur_fwz);
			
			return ret_len;
		}
		
		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFFDBBD_DATA para){
			
			int ret_len = len;
			
			para.ymd = ComntStream.readStream(byte_vect, offset + ret_len, para.ymd); 
			ret_len += ComntStream.getDataSize(para.ymd);
			
			para.cur_zyz = ComntStream.readStream(byte_vect, offset + ret_len, para.cur_zyz); 
			ret_len += ComntStream.getDataSize(para.cur_zyz);
			
			para.cur_zyf = ComntStream.readStream(byte_vect, offset + ret_len, para.cur_zyf); 
			ret_len += ComntStream.getDataSize(para.cur_zyf);
			
			para.cur_zyp = ComntStream.readStream(byte_vect, offset + ret_len, para.cur_zyp); 
			ret_len += ComntStream.getDataSize(para.cur_zyp);
			
			para.cur_zyg = ComntStream.readStream(byte_vect, offset + ret_len, para.cur_zyg); 
			ret_len += ComntStream.getDataSize(para.cur_zyg);
			
			para.cur_zyj = ComntStream.readStream(byte_vect, offset + ret_len, para.cur_zyj); 
			ret_len += ComntStream.getDataSize(para.cur_zyj);

			para.cur_fyz = ComntStream.readStream(byte_vect, offset + ret_len, para.cur_fyz); 
			ret_len += ComntStream.getDataSize(para.cur_fyz);

			para.cur_fyf = ComntStream.readStream(byte_vect, offset + ret_len, para.cur_fyf); 
			ret_len += ComntStream.getDataSize(para.cur_fyf);

			para.cur_fyp = ComntStream.readStream(byte_vect, offset + ret_len, para.cur_fyp); 
			ret_len += ComntStream.getDataSize(para.cur_fyp);

			para.cur_fyg = ComntStream.readStream(byte_vect, offset + ret_len, para.cur_fyg); 
			ret_len += ComntStream.getDataSize(para.cur_fyg);

			para.cur_fyj = ComntStream.readStream(byte_vect, offset + ret_len, para.cur_fyj); 
			ret_len += ComntStream.getDataSize(para.cur_fyj);

			para.cur_zwz = ComntStream.readStream(byte_vect, offset + ret_len, para.cur_zwz); 
			ret_len += ComntStream.getDataSize(para.cur_zwz);

			para.cur_fwz = ComntStream.readStream(byte_vect, offset + ret_len, para.cur_fwz); 
			ret_len += ComntStream.getDataSize(para.cur_fwz);

			return ret_len;
		}
	}

	//[消息相关,请不要随意改动]
	public static class YFFGY_CALLREMAIN	{
		public byte		fee_type;			//预付费方式 表底 单 复费率
		public byte		cacl_type;			//金额 表底方式
		public int		buy_time;			//购电次数
		public double	nowval;				//剩余金额 或者 当前表底 
		public double	totval;				//总金额 或者 断电表底	上次
		public double	alarm_val1;			//报警金额 或者报警止码
		public double	alarm_val2;			//二次报警
		public byte		yff_flag;			//预付费启用标志
		
		public static void setYFFGY_CALLREMAIN(YFFGY_CALLREMAIN para, YFFGY_CALLREMAIN setValue){
			if(setValue == null){
				para = new YFFGY_CALLREMAIN();
			}else{
				para.fee_type	= setValue.fee_type;
				para.cacl_type	= setValue.cacl_type;
				para.buy_time	= setValue.buy_time;
				
				para.nowval	= setValue.nowval;
				para.totval	= setValue.totval;
				para.alarm_val1	= setValue.alarm_val1;
				para.alarm_val2	= setValue.alarm_val2;
				para.yff_flag	= setValue.yff_flag;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, YFFGY_CALLREMAIN para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.fee_type);
			ret_len += ComntStream.writeStream(byte_vect, para.cacl_type);
			ret_len += ComntStream.writeStream(byte_vect, para.buy_time);
			
			ret_len += ComntStream.writeStream(byte_vect, para.nowval);
			ret_len += ComntStream.writeStream(byte_vect, para.totval);
			ret_len += ComntStream.writeStream(byte_vect, para.alarm_val1);
			ret_len += ComntStream.writeStream(byte_vect, para.alarm_val2);
			ret_len += ComntStream.writeStream(byte_vect, para.yff_flag);
			
			return ret_len;
		}
		
		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFFGY_CALLREMAIN para){
			
			int ret_len = len;
			
			para.fee_type = ComntStream.readStream(byte_vect, offset + ret_len, para.fee_type); 
			ret_len += ComntStream.getDataSize(para.fee_type);
			
			para.cacl_type = ComntStream.readStream(byte_vect, offset + ret_len, para.cacl_type); 
			ret_len += ComntStream.getDataSize(para.cacl_type);
			
			para.buy_time = ComntStream.readStream(byte_vect, offset + ret_len, para.buy_time); 
			ret_len += ComntStream.getDataSize(para.buy_time);
			
			para.nowval = ComntStream.readStream(byte_vect, offset + ret_len, para.nowval); 
			ret_len += ComntStream.getDataSize(para.nowval);
			
			para.totval = ComntStream.readStream(byte_vect, offset + ret_len, para.totval); 
			ret_len += ComntStream.getDataSize(para.totval);
			
			para.alarm_val1 = ComntStream.readStream(byte_vect, offset + ret_len, para.alarm_val1); 
			ret_len += ComntStream.getDataSize(para.alarm_val1);
			
			para.alarm_val2 = ComntStream.readStream(byte_vect, offset + ret_len, para.alarm_val2); 
			ret_len += ComntStream.getDataSize(para.alarm_val2);
			
			para.yff_flag = ComntStream.readStream(byte_vect, offset + ret_len, para.yff_flag); 
			ret_len += ComntStream.getDataSize(para.yff_flag);
			
			return ret_len;
		}
	}

	//[消息相关,请不要随意改动]
	public static class YFFGY_CALLFEERATE	{
		double		rated_z;							//总费率
		double		ratef_j;							//尖费率
		double		ratef_f;							//峰费率	
		double		ratef_p;							//平费率
		double		ratef_g;							//谷费率
		
		double		pay_add1;
		double		pay_add2;
		double		pay_add3;
		
		public static void setYFFGY_CALLFEERATE(YFFGY_CALLFEERATE para, YFFGY_CALLFEERATE setValue){
			if(setValue == null){
				para = new YFFGY_CALLFEERATE();
			}else{
				para.rated_z	= setValue.rated_z;
				para.ratef_j	= setValue.ratef_j;
				para.ratef_f	= setValue.ratef_f;
				para.ratef_p	= setValue.ratef_p;
				para.ratef_g	= setValue.ratef_g;
				
				para.pay_add1	= setValue.pay_add1;
				para.pay_add2	= setValue.pay_add2;
				para.pay_add3	= setValue.pay_add3;
			}
		}
		
		public static int writeStream(ComntVector.ByteVector byte_vect, YFFGY_CALLFEERATE para){
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, para.rated_z);
			ret_len += ComntStream.writeStream(byte_vect, para.ratef_j);
			ret_len += ComntStream.writeStream(byte_vect, para.ratef_f);
			ret_len += ComntStream.writeStream(byte_vect, para.ratef_p);
			ret_len += ComntStream.writeStream(byte_vect, para.ratef_g);
			
			ret_len += ComntStream.writeStream(byte_vect, para.pay_add1);
			ret_len += ComntStream.writeStream(byte_vect, para.pay_add2);
			ret_len += ComntStream.writeStream(byte_vect, para.pay_add3);
			
			return ret_len;
		}
		
		public static int getDataSize(ComntVector.ByteVector byte_vect, int offset, int len, YFFGY_CALLFEERATE para){
			
			int ret_len = len;
			
			para.rated_z = ComntStream.readStream(byte_vect, offset + ret_len, para.rated_z); 
			ret_len += ComntStream.getDataSize(para.rated_z);
			
			para.ratef_j = ComntStream.readStream(byte_vect, offset + ret_len, para.ratef_j); 
			ret_len += ComntStream.getDataSize(para.ratef_j);
			
			para.ratef_f = ComntStream.readStream(byte_vect, offset + ret_len, para.ratef_f); 
			ret_len += ComntStream.getDataSize(para.ratef_f);
			
			para.ratef_p = ComntStream.readStream(byte_vect, offset + ret_len, para.ratef_p); 
			ret_len += ComntStream.getDataSize(para.ratef_p);
			
			para.ratef_g = ComntStream.readStream(byte_vect, offset + ret_len, para.ratef_g); 
			ret_len += ComntStream.getDataSize(para.ratef_g);
			
			para.pay_add1 = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_add1); 
			ret_len += ComntStream.getDataSize(para.pay_add1);
			
			para.pay_add2 = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_add2); 
			ret_len += ComntStream.getDataSize(para.pay_add2);
			
			para.pay_add3 = ComntStream.readStream(byte_vect, offset + ret_len, para.pay_add3); 
			ret_len += ComntStream.getDataSize(para.pay_add3);
			
			return ret_len;
		}
	}
	

	//低压通讯-设置用户基本参数类型
	public static class YFF_DYSET_USERPARA {
		
		public short mpid = 0;
		public ComntEsam.ESAM_PARAINFO parainfo = new ComntEsam.ESAM_PARAINFO();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntEsam.ESAM_PARAINFO.writeStream(byte_vect, parainfo);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid);
			ret_len += ComntStream.getDataSize(mpid);

			ret_len = ComntEsam.ESAM_PARAINFO.getDataSize(byte_vect, offset, ret_len, parainfo);
			
			return ret_len;
		}

		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("user_type"	, String.valueOf(parainfo.user_type));
			j_obj.put("bit_update"	, String.valueOf(parainfo.bit_update));
			j_obj.put("chg_date"	, String.valueOf(parainfo.chg_date));
			j_obj.put("chg_time"	, String.valueOf(parainfo.chg_time));
			j_obj.put("alarm_val1"	, String.valueOf(parainfo.alarm_val1));
			j_obj.put("alarm_val2"	, String.valueOf(parainfo.alarm_val2));
			j_obj.put("ct"		 	, String.valueOf(parainfo.ct));
			j_obj.put("pt"		 	, String.valueOf(parainfo.pt));
			j_obj.put("meterno"		, ComntFunc.byte2String(parainfo.meterno));
			j_obj.put("userno"		, ComntFunc.byte2String(parainfo.userno));
			
			return j_obj.toString();
		}
	}

	//低压通讯-设置费率	
	public static class YFF_DYSET_FEEPARA {
		public	short					mpid;							//测量点号
		public	byte 					feeno;
		public	ComntEsam.ESAM_FEERATE	feerate = new ComntEsam.ESAM_FEERATE();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, feeno);
			ret_len += ComntEsam.ESAM_FEERATE.writeStream(byte_vect, feerate);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid);
			ret_len += ComntStream.getDataSize(mpid);
			
			feeno = ComntStream.readStream(byte_vect, offset + ret_len, feeno);
			ret_len += ComntStream.getDataSize(feeno);
			
			ret_len = ComntEsam.ESAM_FEERATE.getDataSize(byte_vect, offset, ret_len, feerate);
			
			return ret_len;
		}

//		public void fromJsonString(String jstr) {
//			JSONObject j_obj = JSONObject.fromObject(jstr);	
//
//			mpid 			= (short)CommBase.strtoi(j_obj.getString("mpid"));
//			feeno 			= (byte)CommBase.strtoi(j_obj.getString("feeno"));
//			
//			feerate.rated_z = CommBase.strtoi(j_obj.getString("rated_z"));
//			feerate.ratef_j = CommBase.strtof(j_obj.getString("ratef_j"));
//			feerate.ratef_f = CommBase.strtof(j_obj.getString("ratef_f"));
//			feerate.ratef_p = CommBase.strtof(j_obj.getString("ratef_p"));
//			feerate.ratef_g = CommBase.strtof(j_obj.getString("ratef_g"));
//		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("mpid", 	String.valueOf(mpid));
			j_obj.put("feeno", 	String.valueOf(feeno));
			
			j_obj.put("rated_z", 	String.valueOf(feerate.rated_z));
			j_obj.put("ratef_j", 	String.valueOf(feerate.ratef_j));
			j_obj.put("ratef_f", 	String.valueOf(feerate.ratef_f));
			j_obj.put("ratef_p", 	String.valueOf(feerate.ratef_p));
			j_obj.put("ratef_g", 	String.valueOf(feerate.ratef_g));
			
			String tmp=I18N.getText("flfa_new",feerate.rated_z,feerate.ratef_j,feerate.ratef_f,feerate.ratef_p,feerate.ratef_g);
			j_obj.put("rate_desc", 	tmp);
			
			return j_obj.toString();
		}
	}

	//20120502 阶梯整合
	//低压通讯-设置阶梯费率		
	
	//低压通讯-设置预付费比较重要参数 二类参数 过easm 加密
	
	//20131021
	//低压通讯-设置二版阶梯费率
	public static class YFF_DYSET_JTBLOCK_FEEPARA{
		public short	mpid	= 0;
		public byte		feeno	= 0;		//费率号	第一套 第二套
		public ComntEsam2.ESAM2_FEERATE	feerate = new ComntEsam2.ESAM2_FEERATE();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntStream.writeStream(byte_vect, feeno);
			ret_len += ComntEsam2.ESAM2_FEERATE.writeStream(byte_vect, feerate);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid);
			ret_len += ComntStream.getDataSize(mpid);
			
			feeno = ComntStream.readStream(byte_vect, offset + ret_len, feeno);
			ret_len += ComntStream.getDataSize(feeno);
			
			ret_len = ComntEsam2.ESAM2_FEERATE.getDataSize(byte_vect, offset, ret_len, feerate);
			
			return ret_len;
		}
	
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			JSONArray  j_array1 = new JSONArray(),j_array2 = new JSONArray(),j_array3 = new JSONArray(),j_array4 = new JSONArray();
			
			j_obj.put("mpid", 	String.valueOf(mpid));
			j_obj.put("feeno", 	String.valueOf(feeno));
			
			if(feerate.fee_rate.length > 0){
				for(int i=0; i<feerate.fee_rate.length; i++){
					j_array1.add(feerate.fee_rate[i]);
				}
			}
			
			if(feerate.jt_step.length > 0 ){
				for(int i=0; i<feerate.jt_step.length; i++){
					j_array2.add(feerate.jt_step[i]);
				}
			}
			
			if(feerate.jt_rate.length > 0){
				for(int i=0; i<feerate.jt_rate.length; i++){
					j_array3.add(feerate.jt_rate[i]);
				}
			}
			
			if(feerate.jt_jsmdh.length > 0){
				for(int i=0; i<feerate.jt_jsmdh.length; i++){
					j_array4.add(feerate.jt_jsmdh[i]);
				}
			}
			
			j_obj.put("fee_rate", j_array1);
			j_obj.put("jt_step", j_array2);
			j_obj.put("jt_rate", j_array3);
			j_obj.put("jt_jsmdh", j_array4);
			
			j_obj.put("jt_chgymd", feerate.jt_chgymd);
			j_obj.put("jt_cghm", feerate.jt_cghm);
				
			return j_obj.toString();
		}
	}
	
	//高压通讯-设置费率
	public static class YFF_GYSET_FEEPARA {
		public	short	zjgid;							//总加组号
		public	short	feeproj_id;						//费率方案号1
		public	double	pay_add1;
		public	double	pay_add2;
		public	double	pay_add3;
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += ComntStream.writeStream(byte_vect, feeproj_id);
			ret_len += ComntStream.writeStream(byte_vect, pay_add1);
			ret_len += ComntStream.writeStream(byte_vect, pay_add2);
			ret_len += ComntStream.writeStream(byte_vect, pay_add3);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid);
			ret_len += ComntStream.getDataSize(zjgid);
			
			feeproj_id = ComntStream.readStream(byte_vect, offset + ret_len, feeproj_id);
			ret_len += ComntStream.getDataSize(feeproj_id);
			
			pay_add1 = ComntStream.readStream(byte_vect, offset + ret_len, pay_add1);
			ret_len += ComntStream.getDataSize(pay_add1);
			
			pay_add2 = ComntStream.readStream(byte_vect, offset + ret_len, pay_add2);
			ret_len += ComntStream.getDataSize(pay_add2);
			
			pay_add3 = ComntStream.readStream(byte_vect, offset + ret_len, pay_add3);
			ret_len += ComntStream.getDataSize(pay_add3);

			return ret_len;
		}

//		public void fromJsonString(String jstr) {
//			JSONObject j_obj = JSONObject.fromObject(jstr);			
//
//			zjgid 		= (short)CommBase.strtoi(j_obj.getString("zjgid"));
//			feeproj_id 	= (short)CommBase.strtoi(j_obj.getString("feeproj_id"));
//			pay_add1 	= CommBase.strtof(j_obj.getString("pay_add1"));
//			pay_add2 	= CommBase.strtof(j_obj.getString("pay_add2"));
//			pay_add3 	= CommBase.strtof(j_obj.getString("pay_add3"));
//		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("zjgid", 		String.valueOf(zjgid));
			j_obj.put("feeproj_id", String.valueOf(feeproj_id));
			j_obj.put("pay_add1", 	String.valueOf(pay_add1));
			j_obj.put("pay_add2", 	String.valueOf(pay_add2));
			j_obj.put("pay_add3", 	String.valueOf(pay_add3));
			
			return j_obj.toString();
		}
	}

	//高压设置保电
	public static class YFF_GYSET_PROTECT {
		public	short				zjgid;							//测量点号
		public	YFFGYSET_PROTECT	setpara = new YFFGYSET_PROTECT();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += YFFGYSET_PROTECT.writeStream(byte_vect, setpara);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid);
			ret_len += ComntStream.getDataSize(zjgid);
			
			ret_len = YFFGYSET_PROTECT.getDataSize(byte_vect, offset, ret_len, setpara);

			return ret_len;
		}

//		public void fromJsonString(String jstr) {
//			JSONObject j_obj = JSONObject.fromObject(jstr);	
//
//			zjgid 			 = (short)CommBase.strtoi(j_obj.getString("zjgid"));
//			
//			setpara.bdbegin = (byte)CommBase.strtoi(j_obj.getString("bdbegin"));
//			setpara.bdend 	 = (byte)CommBase.strtoi(j_obj.getString("bdend"));
//			setpara.ctrlflag= (byte)CommBase.strtoi(j_obj.getString("ctrlflag"));
//			
//		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("zjgid", 		String.valueOf(zjgid));
			
			j_obj.put("bdbegin", 	String.valueOf(setpara.bdbegin));
			j_obj.put("bdend", 		String.valueOf(setpara.bdend));
			j_obj.put("ctrlflag", 	String.valueOf(setpara.ctrlflag));
			
			return j_obj.toString();
		}
	}
	
	//高压控制保电
	public static class YFF_GYCTRL_PROTECT extends YFF_GYSET_PROTECT{

	}
	
	//负控限电
	public static class YFF_GYCTRL_CUT {
		public	short			zjgid;							//测量点号
		public	YFFGYCTRL_CUT	ctrl = new YFFGYCTRL_CUT();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += YFFGYCTRL_CUT.writeStream(byte_vect, ctrl);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid);
			ret_len += ComntStream.getDataSize(zjgid);
			
			ret_len = YFFGYCTRL_CUT.getDataSize(byte_vect, offset, ret_len, ctrl);

			return ret_len;
		}

//		public void fromJsonString(String jstr) {
//			JSONObject j_obj = JSONObject.fromObject(jstr);	
//
//			zjgid 			 	= (short)CommBase.strtoi(j_obj.getString("mpid"));
//			
//			ctrl.ctrltype 	= CommBase.strtoi(j_obj.getString("ctrltype"));
//			ctrl.ctrlroll 	= (byte)CommBase.strtoi(j_obj.getString("ctrlroll"));
//			ctrl.limithour	= (byte)CommBase.strtoi(j_obj.getString("limithour"));
//			ctrl.limitmin	= (byte)CommBase.strtoi(j_obj.getString("limitmin"));
//			ctrl.plustime	= (short)CommBase.strtoi(j_obj.getString("plustime"));
//			ctrl.delaytime	= (byte)CommBase.strtoi(j_obj.getString("delaytime"));
//			
//		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("mpid", 		String.valueOf(zjgid));
			
			j_obj.put("ctrltype", 	String.valueOf(ctrl.ctrltype));
			j_obj.put("ctrlroll", 	String.valueOf(ctrl.ctrlroll));
			j_obj.put("limithour", 	String.valueOf(ctrl.limithour));
			j_obj.put("limitmin", 	String.valueOf(ctrl.limitmin));
			j_obj.put("plustime", 	String.valueOf(ctrl.plustime));
			j_obj.put("delaytime", 	String.valueOf(ctrl.delaytime));
			
			return j_obj.toString();
		}
	}

	//返回数据结构 控制、参数设置、缴费
	public static class YFF_DATAYESNOMSG {
	  	public byte	result;
		public byte	errstep;							//失败步骤
		public int	errcode;							//错误码 父码
		public int	errsubcode;							//错误码 子码
	
		public byte[]	text = new byte[ComntDef.YD_64_STRLEN];
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, result);
			ret_len += ComntStream.writeStream(byte_vect, errstep);
			ret_len += ComntStream.writeStream(byte_vect, errcode);
			ret_len += ComntStream.writeStream(byte_vect, errsubcode);
			ret_len += ComntStream.writeStream(byte_vect, text, 0, text.length);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			result = ComntStream.readStream(byte_vect, offset + ret_len, result);
			ret_len += ComntStream.getDataSize(result);
			
			errstep = ComntStream.readStream(byte_vect, offset + ret_len, errstep);
			ret_len += ComntStream.getDataSize(errstep);
			
			errcode = ComntStream.readStream(byte_vect, offset + ret_len, errcode);
			ret_len += ComntStream.getDataSize(errcode);
			
			errsubcode = ComntStream.readStream(byte_vect, offset + ret_len, errsubcode);
			ret_len += ComntStream.getDataSize(errsubcode);

			ComntStream.readStream(byte_vect, offset + ret_len, text, 0, text.length);
			ret_len += ComntStream.getDataSize((byte)1) *  text.length;
			
			return ret_len;
		}

//		public void fromJsonString(String jstr) {
//			JSONObject j_obj = JSONObject.fromObject(jstr);	
//			
//			String tmp = "";
//			int 	i  = 0;
//			
//			result 		= (byte)CommBase.strtoi(j_obj.getString("result"));
//			errstep 	= (byte)CommBase.strtoi(j_obj.getString("errstep"));
//			errcode 	= CommBase.strtoi(j_obj.getString("errcode"));
//			errsubcode 	= CommBase.strtoi(j_obj.getString("errsubcode"));
//
//			tmp = j_obj.getString("text");
//			while(tmp.length() < text.length) {
//				tmp += " ";
//			}
//			for (i = 0; i < text.length; i++) {
//				text[i] = (byte)tmp.charAt(i);
//			}
//		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("result", 	String.valueOf(result));
			j_obj.put("errstep", 	String.valueOf(errstep));
			j_obj.put("errcode", 	String.valueOf(errcode));
			j_obj.put("errsubcode", String.valueOf(errsubcode));
			
			j_obj.put("text", 		ComntFunc.byte2String(text));
			
			return j_obj.toString();
		}
	}


	
	//参数信息文件
	public static class YFF_DATA_DYCALLUSER extends YFF_DYSET_USERPARA{

	}
	
	//费率
	public static class YFF_DATA_DYCALLFEE extends YFF_DYSET_FEEPARA{

	}

	//20131021添加	begin
	//此处应该添加部分  用时再定
	public static class YFF_DATA_DYCALLJTFEE extends YFF_DYSET_JTBLOCK_FEEPARA{} ;
	//end
	
	//状态查询过esam
	public static class YFF_DATA_DYSTATE {
		public	short						mpid;							//测量点号
		public	ComntEsam.ESAM_LOOKSTATE	lookstate = new ComntEsam.ESAM_LOOKSTATE();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntEsam.ESAM_LOOKSTATE.writeStream(byte_vect, lookstate);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid);
			ret_len += ComntStream.getDataSize(mpid);
			
			ret_len = ComntEsam.ESAM_LOOKSTATE.getDataSize(byte_vect, offset, ret_len, lookstate);

			return ret_len;
		}

//		public void fromJsonString(String jstr) {
//			JSONObject j_obj = JSONObject.fromObject(jstr);	
//
//			mpid 			 		= (short)CommBase.strtoi(j_obj.getString("mpid"));
//			
//			lookstate.remain_money 	= CommBase.strtoi(j_obj.getString("remain_money"));
//			lookstate.buy_num 		= (byte)CommBase.strtoi(j_obj.getString("buy_num"));
//			lookstate.key_state 		= (byte)CommBase.strtoi(j_obj.getString("key_state"));
//			lookstate.key_updatetype = (byte)CommBase.strtoi(j_obj.getString("key_updatetype"));
//			lookstate.key_chgno 		= (byte)CommBase.strtoi(j_obj.getString("key_chgno"));
//			lookstate.key_ver 		= (byte)CommBase.strtoi(j_obj.getString("key_ver"));
//		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("mpid", 			String.valueOf(mpid));
			
			j_obj.put("remain_money", 	String.valueOf(lookstate.remain_money));
			j_obj.put("buy_num", 		String.valueOf(lookstate.buy_num));
			j_obj.put("key_state", 		String.valueOf(lookstate.key_state));
			j_obj.put("key_updatetype", String.valueOf(lookstate.key_updatetype));
			j_obj.put("key_chgno", 		String.valueOf(lookstate.key_chgno));
			j_obj.put("key_ver", 		String.valueOf(lookstate.key_ver));
			
			return j_obj.toString();
		}
	}
	
	//20131021添加	begin
	//2版状态查询过esam
	public static class YFF_DATA_DY2STATE{
		public	short						mpid;							//测量点号
		public	ComntEsam2.ESAM2_LOOKSTATE	lookstate = new ComntEsam2.ESAM2_LOOKSTATE();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntEsam2.ESAM2_LOOKSTATE.writeStream(byte_vect, lookstate);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid);
			ret_len += ComntStream.getDataSize(mpid);
			
			ret_len = ComntEsam2.ESAM2_LOOKSTATE.getDataSize(byte_vect, offset, ret_len, lookstate);

			return ret_len;
		}
	
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			JSONArray  j_array = new JSONArray();
			
			j_obj.put("mpid", 			String.valueOf(mpid));
			
			j_obj.put("remain_money", 	String.valueOf(lookstate.remain_money));
			j_obj.put("buy_num", 		String.valueOf(lookstate.buy_num));
			j_obj.put("key_state", 		String.valueOf(lookstate.key_state));
			
			if(lookstate.user_no.length > 0){
				for(int i=0; i<lookstate.user_no.length; i++){
					j_array.add(lookstate.user_no[i]);
				}
			}
			j_obj.put("user_no", j_array);
			
			return j_obj.toString();
		}
	}
	//end


	//总加组当前剩余电费
	public static class YFF_DATA_GYREMAIN {
		public double	cur_val;
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, cur_val);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			cur_val = ComntStream.readStream(byte_vect, offset + ret_len, cur_val);
			ret_len += ComntStream.getDataSize(cur_val);
			
			return ret_len;
		}

//		public void fromJsonString(String jstr) {
//			JSONObject j_obj = JSONObject.fromObject(jstr);	
//
//			cur_val 	= CommBase.strtof(j_obj.getString("cur_val"));
//		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("cur_val", 	String.valueOf(cur_val));
			
			return j_obj.toString();
		}
	}

	
	//电能表购、用电信息
	public static class YFF_DATA_DYREMAIN {
		public	short				mpid;							//测量点号
		public	YFFDY_REMAIN_MONEY	readdata = new YFFDY_REMAIN_MONEY();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += YFFDY_REMAIN_MONEY.writeStream(byte_vect, readdata);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid);
			ret_len += ComntStream.getDataSize(mpid);
			
			ret_len = YFFDY_REMAIN_MONEY.getDataSize(byte_vect, offset, ret_len, readdata);

			return ret_len;
		}

//		public void fromJsonString(String jstr) {
//			JSONObject j_obj = JSONObject.fromObject(jstr);	
//
//			mpid 			 = (short)CommBase.strtoi(j_obj.getString("mpid"));
//			
//			readdata.read_date 	 = CommBase.strtoi(j_obj.getString("read_date"));
//			readdata.read_time 	 = CommBase.strtoi(j_obj.getString("read_time"));
//			readdata.pay_num 	 = CommBase.strtoi(j_obj.getString("pay_num"));
//			readdata.remain_money = (float)CommBase.strtof(j_obj.getString("remain_money"));
//			readdata.total_money =  (float)CommBase.strtof(j_obj.getString("total_money"));
//			readdata.remain_dl =  (float)CommBase.strtof(j_obj.getString("remain_dl"));
//			readdata.overd_dl =  (float)CommBase.strtof(j_obj.getString("overd_dl"));
//			readdata.total_dl =  (float)CommBase.strtof(j_obj.getString("total_dl"));
//			readdata.tick_dl =  (float)CommBase.strtof(j_obj.getString("tick_dl"));
//			readdata.alarm_dl =  (float)CommBase.strtof(j_obj.getString("alarm_dl"));
//			readdata.fail_dl =  (float)CommBase.strtof(j_obj.getString("fail_dl"));
//		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("mpid", 			String.valueOf(mpid));
			
			j_obj.put("read_date", 		String.valueOf(readdata.read_date));
			j_obj.put("read_time", 		String.valueOf(readdata.read_time));
			j_obj.put("pay_num", 		String.valueOf(readdata.pay_num));
			j_obj.put("remain_money", 	String.valueOf(readdata.remain_money));
			j_obj.put("total_money", 	String.valueOf(readdata.total_money));
			j_obj.put("remain_dl", 		String.valueOf(readdata.remain_dl));
			j_obj.put("overd_dl", 		String.valueOf(readdata.overd_dl));
			j_obj.put("total_dl", 		String.valueOf(readdata.total_dl));
			j_obj.put("tick_dl", 		String.valueOf(readdata.tick_dl));
			j_obj.put("alarm_dl", 		String.valueOf(readdata.alarm_dl));
			j_obj.put("fail_dl", 		String.valueOf(readdata.fail_dl));
			
			return j_obj.toString();
		}
	}
	

	//表底	实时 日 正反向
	public static class YFF_DATA_READBD {
		public	short			mpid;							//测量点号
		public	YFFDBBD_DATA	dbbd = new YFFDBBD_DATA();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += YFFDBBD_DATA.writeStream(byte_vect, dbbd);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid);
			ret_len += ComntStream.getDataSize(mpid);
			
			ret_len = YFFDBBD_DATA.getDataSize(byte_vect, offset, ret_len, dbbd);

			return ret_len;
		}

//		public void fromJsonString(String jstr) {
//			JSONObject j_obj = JSONObject.fromObject(jstr);	
//
//			mpid 			 = (short)CommBase.strtoi(j_obj.getString("mpid"));
//			
//			dbbd.ymd 	 = CommBase.strtoi(j_obj.getString("ymd"));
//			
//			dbbd.cur_zyz = CommBase.strtof(j_obj.getString("cur_zyz"));
//			dbbd.cur_zyf = CommBase.strtof(j_obj.getString("cur_zyf"));
//			dbbd.cur_zyp = CommBase.strtof(j_obj.getString("cur_zyp"));
//			dbbd.cur_zyg = CommBase.strtof(j_obj.getString("cur_zyg"));
//			dbbd.cur_zyj = CommBase.strtof(j_obj.getString("cur_zyj"));
//			
//			dbbd.cur_fyz = CommBase.strtof(j_obj.getString("cur_fyz"));
//			dbbd.cur_fyf = CommBase.strtof(j_obj.getString("cur_fyf"));
//			dbbd.cur_fyp = CommBase.strtof(j_obj.getString("cur_fyp"));
//			dbbd.cur_fyg = CommBase.strtof(j_obj.getString("cur_fyg"));
//			dbbd.cur_fyj = CommBase.strtof(j_obj.getString("cur_fyj"));
//			
//			dbbd.cur_zwz = CommBase.strtof(j_obj.getString("cur_zwz"));
//			dbbd.cur_fwz = CommBase.strtof(j_obj.getString("cur_fwz"));			
//		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("mpid", 	String.valueOf(mpid));
			
			j_obj.put("ymd", 	String.valueOf(dbbd.ymd));
			
			j_obj.put("cur_zyz", 	String.valueOf(dbbd.cur_zyz));
			j_obj.put("cur_zyf", 	String.valueOf(dbbd.cur_zyf));
			j_obj.put("cur_zyp", 	String.valueOf(dbbd.cur_zyp));
			j_obj.put("cur_zyg", 	String.valueOf(dbbd.cur_zyg));
			j_obj.put("cur_zyj", 	String.valueOf(dbbd.cur_zyj));
			
			j_obj.put("cur_fyz", 	String.valueOf(dbbd.cur_fyz));
			j_obj.put("cur_fyf", 	String.valueOf(dbbd.cur_fyf));
			j_obj.put("cur_fyp", 	String.valueOf(dbbd.cur_fyp));
			j_obj.put("cur_fyg", 	String.valueOf(dbbd.cur_fyg));
			j_obj.put("cur_fyj", 	String.valueOf(dbbd.cur_fyj));
			
			j_obj.put("cur_zwz", 	String.valueOf(dbbd.cur_zwz));
			j_obj.put("cur_fwz", 	String.valueOf(dbbd.cur_fwz));
			
			return j_obj.toString();
		}
	}
		
	//透传剩余金额
	public static class YFF_DATA_DBREMAIN extends YFF_DATA_GYREMAIN{

	}
		
	//透传透支金额
	public static class YFF_DATA_DBOVER extends YFF_DATA_DBREMAIN{

	}

	
	//电表运行状态字
	public static class YFF_DATA_DB_RUNSTATE {
		
		public	short					mpid;							//测量点号
		public	ComntEsam.SMARTDB_STATE	dbstate = new ComntEsam.SMARTDB_STATE();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntEsam.SMARTDB_STATE.writeStream(byte_vect, dbstate);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid);
			ret_len += ComntStream.getDataSize(mpid);
			
			ret_len = ComntEsam.SMARTDB_STATE.getDataSize(byte_vect, offset, ret_len, dbstate);

			return ret_len;
		}

//		public void fromJsonString(String jstr) {
//			JSONObject j_obj = JSONObject.fromObject(jstr);	
//
//			mpid 			 = (short)CommBase.strtoi(j_obj.getString("mpid"));
//			
//			dbstate.dbs1.reserve1		= (byte)CommBase.strtoi(j_obj.getString("dbs1_reserve1"));
//			dbstate.dbs1.xljsfs			= (byte)CommBase.strtoi(j_obj.getString("dbs1_xljsfs"));
//			dbstate.dbs1.tdcbdc			= (byte)CommBase.strtoi(j_obj.getString("dbs1_tdcbdc"));
//			dbstate.dbs1.ygglfx			= (byte)CommBase.strtoi(j_obj.getString("dbs1_ygglfx"));
//			dbstate.dbs1.wgglfx			= (byte)CommBase.strtoi(j_obj.getString("dbs1_wgglfx"));
//			dbstate.dbs1.reserve6		= (byte)CommBase.strtoi(j_obj.getString("dbs1_reserve6"));
//			dbstate.dbs1.reserve7		= (byte)CommBase.strtoi(j_obj.getString("dbs1_reserve7"));
//			dbstate.dbs1.reserve8		= (byte)CommBase.strtoi(j_obj.getString("dbs1_reserve8"));
//			dbstate.dbs1.myzt			= (byte)CommBase.strtoi(j_obj.getString("dbs1_myzt"));
//			dbstate.dbs1.sydl			= (byte)CommBase.strtoi(j_obj.getString("dbs1_sydl"));
//			dbstate.dbs1.esamzt			= (byte)CommBase.strtoi(j_obj.getString("dbs1_esamzt"));
//			dbstate.dbs1.iccard			= (byte)CommBase.strtoi(j_obj.getString("dbs1_iccard"));
//			dbstate.dbs1.reserve13		= (byte)CommBase.strtoi(j_obj.getString("dbs1_reserve13"));
//			dbstate.dbs1.reserve14		= (byte)CommBase.strtoi(j_obj.getString("dbs1_reserve14"));
//			dbstate.dbs1.reserve15		= (byte)CommBase.strtoi(j_obj.getString("dbs1_reserve15"));
//			
//			dbstate.dbs2.aygp			= (byte)CommBase.strtoi(j_obj.getString("dbs2_aygp"));
//			dbstate.dbs2.bygp			= (byte)CommBase.strtoi(j_obj.getString("dbs2_bygp"));
//			dbstate.dbs2.cygp			= (byte)CommBase.strtoi(j_obj.getString("dbs2_cygp"));
//			dbstate.dbs2.reserve3		= (byte)CommBase.strtoi(j_obj.getString("dbs2_reserve3"));
//			dbstate.dbs2.awgp			= (byte)CommBase.strtoi(j_obj.getString("dbs2_awgp"));
//			dbstate.dbs2.bwgp			= (byte)CommBase.strtoi(j_obj.getString("dbs2_bwgp"));
//			dbstate.dbs2.cwgp			= (byte)CommBase.strtoi(j_obj.getString("dbs2_cwgp"));
//			dbstate.dbs2.reserve7		= (byte)CommBase.strtoi(j_obj.getString("dbs2_reserve7"));
//			dbstate.dbs2.reserve8		= (byte)CommBase.strtoi(j_obj.getString("dbs2_reserve8"));
//			dbstate.dbs2.reserve9		= (byte)CommBase.strtoi(j_obj.getString("dbs2_reserve9"));
//			dbstate.dbs2.reserve10		= (byte)CommBase.strtoi(j_obj.getString("dbs2_reserve10"));
//			dbstate.dbs2.reserve11		= (byte)CommBase.strtoi(j_obj.getString("dbs2_reserve11"));
//			dbstate.dbs2.reserve13		= (byte)CommBase.strtoi(j_obj.getString("dbs2_reserve13"));
//			dbstate.dbs2.reserve14		= (byte)CommBase.strtoi(j_obj.getString("dbs2_reserve14"));
//			dbstate.dbs2.reserve15		= (byte)CommBase.strtoi(j_obj.getString("dbs2_reserve15"));
//			
//			dbstate.dbyff.dqyxsd		= (byte)CommBase.strtoi(j_obj.getString("dbyff_dqyxsd"));
//			dbstate.dbyff.gdfs			= (byte)CommBase.strtoi(j_obj.getString("dbyff_gdfs"));
//			dbstate.dbyff.bcyx			= (byte)CommBase.strtoi(j_obj.getString("dbyff_bcyx"));
//			dbstate.dbyff.jdqzt			= (byte)CommBase.strtoi(j_obj.getString("dbyff_jdqzt"));
//			dbstate.dbyff.dqyxsq		= (byte)CommBase.strtoi(j_obj.getString("dbyff_dqyxsq"));
//			dbstate.dbyff.jdqmlzt		= (byte)CommBase.strtoi(j_obj.getString("dbyff_jdqmlzt"));
//			dbstate.dbyff.ytzbjzt		= (byte)CommBase.strtoi(j_obj.getString("dbyff_ytzbjzt"));
//			dbstate.dbyff.dnblx			= (byte)CommBase.strtoi(j_obj.getString("dbyff_dnblx"));
//			dbstate.dbyff.dqyxfldj		= (byte)CommBase.strtoi(j_obj.getString("dbyff_dqyxfldj"));
//			dbstate.dbyff.dqjt			= (byte)CommBase.strtoi(j_obj.getString("dbyff_dqjt"));
//			dbstate.dbyff.protect		= (byte)CommBase.strtoi(j_obj.getString("dbyff_protect"));
//			dbstate.dbyff.reserve13		= (byte)CommBase.strtoi(j_obj.getString("dbyff_reserve13"));
//			dbstate.dbyff.reserve14		= (byte)CommBase.strtoi(j_obj.getString("dbyff_reserve14"));
//			dbstate.dbyff.keytype		= (byte)CommBase.strtoi(j_obj.getString("dbyff_keytype"));
//			
//			dbstate.dbfaila.sy			= (byte)CommBase.strtoi(j_obj.getString("dbfaila_sy"));
//			dbstate.dbfaila.qy			= (byte)CommBase.strtoi(j_obj.getString("dbfaila_qy"));
//			dbstate.dbfaila.gy			= (byte)CommBase.strtoi(j_obj.getString("dbfaila_gy"));
//			dbstate.dbfaila.sl			= (byte)CommBase.strtoi(j_obj.getString("dbfaila_sl"));
//			dbstate.dbfaila.ql			= (byte)CommBase.strtoi(j_obj.getString("dbfaila_ql"));
//			dbstate.dbfaila.gz			= (byte)CommBase.strtoi(j_obj.getString("dbfaila_gz"));
//			dbstate.dbfaila.clfx		= (byte)CommBase.strtoi(j_obj.getString("dbfaila_clfx"));
//			dbstate.dbfaila.dx			= (byte)CommBase.strtoi(j_obj.getString("dbfaila_dx"));
//			dbstate.dbfaila.dl			= (byte)CommBase.strtoi(j_obj.getString("dbfaila_dl"));
//			dbstate.dbfaila.reserve9	= (byte)CommBase.strtoi(j_obj.getString("dbfaila_reserve9"));
//			dbstate.dbfaila.reserve10	= (byte)CommBase.strtoi(j_obj.getString("dbfaila_reserve10"));
//			dbstate.dbfaila.reserve11	= (byte)CommBase.strtoi(j_obj.getString("dbfaila_reserve11"));
//			dbstate.dbfaila.reserve12	= (byte)CommBase.strtoi(j_obj.getString("dbfaila_reserve12"));
//			dbstate.dbfaila.reserve13	= (byte)CommBase.strtoi(j_obj.getString("dbfaila_reserve13"));
//			dbstate.dbfaila.reserve14	= (byte)CommBase.strtoi(j_obj.getString("dbfaila_reserve14"));
//			dbstate.dbfaila.reserve15	= (byte)CommBase.strtoi(j_obj.getString("dbfaila_reserve15"));
//			
//			dbstate.dbfailb.sy			= (byte)CommBase.strtoi(j_obj.getString("dbfailb_sy"));
//			dbstate.dbfailb.qy			= (byte)CommBase.strtoi(j_obj.getString("dbfailb_qy"));
//			dbstate.dbfailb.gy			= (byte)CommBase.strtoi(j_obj.getString("dbfailb_gy"));
//			dbstate.dbfailb.sl			= (byte)CommBase.strtoi(j_obj.getString("dbfailb_sl"));
//			dbstate.dbfailb.ql			= (byte)CommBase.strtoi(j_obj.getString("dbfailb_ql"));
//			dbstate.dbfailb.gz			= (byte)CommBase.strtoi(j_obj.getString("dbfailb_gz"));
//			dbstate.dbfailb.clfx		= (byte)CommBase.strtoi(j_obj.getString("dbfailb_clfx"));
//			dbstate.dbfailb.dx			= (byte)CommBase.strtoi(j_obj.getString("dbfailb_dx"));
//			dbstate.dbfailb.dl			= (byte)CommBase.strtoi(j_obj.getString("dbfailb_dl"));
//			dbstate.dbfailb.reserve9	= (byte)CommBase.strtoi(j_obj.getString("dbfailb_reserve9"));
//			dbstate.dbfailb.reserve10	= (byte)CommBase.strtoi(j_obj.getString("dbfailb_reserve10"));
//			dbstate.dbfailb.reserve11	= (byte)CommBase.strtoi(j_obj.getString("dbfailb_reserve11"));
//			dbstate.dbfailb.reserve12	= (byte)CommBase.strtoi(j_obj.getString("dbfailb_reserve12"));
//			dbstate.dbfailb.reserve13	= (byte)CommBase.strtoi(j_obj.getString("dbfailb_reserve13"));
//			dbstate.dbfailb.reserve14	= (byte)CommBase.strtoi(j_obj.getString("dbfailb_reserve14"));
//			dbstate.dbfailb.reserve15	= (byte)CommBase.strtoi(j_obj.getString("dbfailb_reserve15"));
//			
//			dbstate.dbfailc.sy			= (byte)CommBase.strtoi(j_obj.getString("dbfailc_sy"));
//			dbstate.dbfailc.qy			= (byte)CommBase.strtoi(j_obj.getString("dbfailc_qy"));
//			dbstate.dbfailc.gy			= (byte)CommBase.strtoi(j_obj.getString("dbfailc_gy"));
//			dbstate.dbfailc.sl			= (byte)CommBase.strtoi(j_obj.getString("dbfailc_sl"));
//			dbstate.dbfailc.ql			= (byte)CommBase.strtoi(j_obj.getString("dbfailc_ql"));
//			dbstate.dbfailc.gz			= (byte)CommBase.strtoi(j_obj.getString("dbfailc_gz"));
//			dbstate.dbfailc.clfx		= (byte)CommBase.strtoi(j_obj.getString("dbfailc_clfx"));
//			dbstate.dbfailc.dx			= (byte)CommBase.strtoi(j_obj.getString("dbfailc_dx"));
//			dbstate.dbfailc.dl			= (byte)CommBase.strtoi(j_obj.getString("dbfailc_dl"));
//			dbstate.dbfailc.reserve9	= (byte)CommBase.strtoi(j_obj.getString("dbfailc_reserve9"));
//			dbstate.dbfailc.reserve10	= (byte)CommBase.strtoi(j_obj.getString("dbfailc_reserve10"));
//			dbstate.dbfailc.reserve11	= (byte)CommBase.strtoi(j_obj.getString("dbfailc_reserve11"));
//			dbstate.dbfailc.reserve12	= (byte)CommBase.strtoi(j_obj.getString("dbfailc_reserve12"));
//			dbstate.dbfailc.reserve13	= (byte)CommBase.strtoi(j_obj.getString("dbfailc_reserve13"));
//			dbstate.dbfailc.reserve14	= (byte)CommBase.strtoi(j_obj.getString("dbfailc_reserve14"));
//			dbstate.dbfailc.reserve15	= (byte)CommBase.strtoi(j_obj.getString("dbfailc_reserve15"));
//			
//			dbstate.dbfail.dynxx	= (byte)CommBase.strtoi(j_obj.getString("dbfail_dynxx"));
//			dbstate.dbfail.dlnxx	= (byte)CommBase.strtoi(j_obj.getString("dbfail_dlnxx"));
//			dbstate.dbfail.dybph	= (byte)CommBase.strtoi(j_obj.getString("dbfail_dybph"));
//			dbstate.dbfail.dlbph	= (byte)CommBase.strtoi(j_obj.getString("dbfail_dlbph"));
//			dbstate.dbfail.fzdysy	= (byte)CommBase.strtoi(j_obj.getString("dbfail_fzdysy"));
//			dbstate.dbfail.dd	= (byte)CommBase.strtoi(j_obj.getString("dbfail_dd"));
//			dbstate.dbfail.xlcz	= (byte)CommBase.strtoi(j_obj.getString("dbfail_xlcz"));
//			dbstate.dbfail.zglyscxx	= (byte)CommBase.strtoi(j_obj.getString("dbfail_zglyscxx"));
//			dbstate.dbfail.dlyzbph	= (byte)CommBase.strtoi(j_obj.getString("dbfail_dlyzbph"));
//			dbstate.dbfail.reserve9	= (byte)CommBase.strtoi(j_obj.getString("dbfail_reserve9"));
//			dbstate.dbfail.reserve10	= (byte)CommBase.strtoi(j_obj.getString("dbfail_reserve10"));
//			dbstate.dbfail.reserve11	= (byte)CommBase.strtoi(j_obj.getString("dbfail_reserve11"));
//			dbstate.dbfail.reserve12	= (byte)CommBase.strtoi(j_obj.getString("dbfail_reserve12"));
//			dbstate.dbfail.reserve13	= (byte)CommBase.strtoi(j_obj.getString("dbfail_reserve13"));
//			dbstate.dbfail.reserve14	= (byte)CommBase.strtoi(j_obj.getString("dbfail_reserve14"));
//			dbstate.dbfail.reserve15	= (byte)CommBase.strtoi(j_obj.getString("dbfail_reserve15"));
//		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("mpid", 			String.valueOf(mpid));
			
			j_obj.put("dbs1_reserve1", 	String.valueOf(dbstate.dbs1.reserve1));
			j_obj.put("dbs1_xljsfs", 	String.valueOf(dbstate.dbs1.xljsfs));
			j_obj.put("dbs1_szdc", 		String.valueOf(dbstate.dbs1.szdc));
			j_obj.put("dbs1_tdcbdc", 	String.valueOf(dbstate.dbs1.tdcbdc));
			j_obj.put("dbs1_ygglfx", 	String.valueOf(dbstate.dbs1.ygglfx));
			j_obj.put("dbs1_wgglfx", 	String.valueOf(dbstate.dbs1.wgglfx));
			j_obj.put("dbs1_reserve6", 	String.valueOf(dbstate.dbs1.reserve6));
			j_obj.put("dbs1_reserve7", 	String.valueOf(dbstate.dbs1.reserve7));
			j_obj.put("dbs1_reserve8", 	String.valueOf(dbstate.dbs1.reserve8));
			j_obj.put("dbs1_myzt", 		String.valueOf(dbstate.dbs1.myzt));
			j_obj.put("dbs1_sydl", 		String.valueOf(dbstate.dbs1.sydl));
			j_obj.put("dbs1_esamzt", 	String.valueOf(dbstate.dbs1.esamzt));
			j_obj.put("dbs1_iccard", 	String.valueOf(dbstate.dbs1.iccard));
			j_obj.put("dbs1_reserve13", String.valueOf(dbstate.dbs1.reserve13));
			j_obj.put("dbs1_reserve14", String.valueOf(dbstate.dbs1.reserve14));
			j_obj.put("dbs1_reserve15", String.valueOf(dbstate.dbs1.reserve15));
			
			j_obj.put("dbs2_aygp", 		String.valueOf(dbstate.dbs2.aygp));
			j_obj.put("dbs2_bygp", 		String.valueOf(dbstate.dbs2.bygp));
			j_obj.put("dbs2_cygp", 		String.valueOf(dbstate.dbs2.cygp));
			j_obj.put("dbs2_reserve3", 	String.valueOf(dbstate.dbs2.reserve3));
			j_obj.put("dbs2_awgp", 		String.valueOf(dbstate.dbs2.awgp));
			j_obj.put("dbs2_bwgp", 		String.valueOf(dbstate.dbs2.bwgp));
			j_obj.put("dbs2_cwgp", 		String.valueOf(dbstate.dbs2.cwgp));
			j_obj.put("dbs2_reserve7", 	String.valueOf(dbstate.dbs2.reserve7));
			j_obj.put("dbs2_reserve8", 	String.valueOf(dbstate.dbs2.reserve8));
			j_obj.put("dbs2_reserve9", 	String.valueOf(dbstate.dbs2.reserve9));
			j_obj.put("dbs2_reserve10", String.valueOf(dbstate.dbs2.reserve10));
			j_obj.put("dbs2_reserve11", String.valueOf(dbstate.dbs2.reserve11));
			j_obj.put("dbs2_reserve12", String.valueOf(dbstate.dbs2.reserve12));
			j_obj.put("dbs2_reserve13", String.valueOf(dbstate.dbs2.reserve13));
			j_obj.put("dbs2_reserve14", String.valueOf(dbstate.dbs2.reserve14));
			j_obj.put("dbs2_reserve15", String.valueOf(dbstate.dbs2.reserve15));
			
			j_obj.put("dbyff_dqyxsd", 	String.valueOf(dbstate.dbyff.dqyxsd));
			j_obj.put("dbyff_gdfs", 	String.valueOf(dbstate.dbyff.gdfs));
			j_obj.put("dbyff_bcyx", 	String.valueOf(dbstate.dbyff.bcyx));
			j_obj.put("dbyff_jdqzt", 	String.valueOf(dbstate.dbyff.jdqzt));
			j_obj.put("dbyff_dqyxsq", 	String.valueOf(dbstate.dbyff.dqyxsq));
			j_obj.put("dbyff_jdqmlzt", 	String.valueOf(dbstate.dbyff.jdqmlzt));
			j_obj.put("dbyff_ytzbjzt", 	String.valueOf(dbstate.dbyff.ytzbjzt));
			j_obj.put("dbyff_dnblx", 	String.valueOf(dbstate.dbyff.dnblx));
			j_obj.put("dbyff_dqyxfldj", String.valueOf(dbstate.dbyff.dqyxfldj));
			j_obj.put("dbyff_dqjt", 	String.valueOf(dbstate.dbyff.dqjt));
			j_obj.put("dbyff_protect", 	String.valueOf(dbstate.dbyff.protect));
			j_obj.put("dbyff_reserve13",String.valueOf(dbstate.dbyff.reserve13));
			j_obj.put("dbyff_reserve14",String.valueOf(dbstate.dbyff.reserve14));
			j_obj.put("dbyff_keytype", 	String.valueOf(dbstate.dbyff.keytype));
			
			j_obj.put("dbyff_dqyxsd_desc", 	I18N.getText("MRState_1t2t_" + String.valueOf(dbstate.dbyff.dqyxsd)));
			j_obj.put("dbyff_gdfs_desc", 	I18N.getText("MRState_gdfs_" + String.valueOf(dbstate.dbyff.gdfs)));
			j_obj.put("dbyff_bcyx_desc", 	I18N.getText("MRState_OKNG_" + String.valueOf(dbstate.dbyff.bcyx)));
			j_obj.put("dbyff_jdqzt_desc", 	I18N.getText("MRState_TD_" + String.valueOf(dbstate.dbyff.jdqzt)));
			j_obj.put("dbyff_dqyxsq_desc", 	I18N.getText("MRState_1t2t_" + String.valueOf(dbstate.dbyff.dqyxsq)));
			j_obj.put("dbyff_jdqmlzt_desc", I18N.getText("MRState_TD_" + String.valueOf(dbstate.dbyff.jdqmlzt)));
			j_obj.put("dbyff_ytzbjzt_desc", I18N.getText("MRState_WY_" + String.valueOf(dbstate.dbyff.ytzbjzt)));
			j_obj.put("dbyff_dnblx_desc", 	I18N.getText("MRState_dnblx_" + String.valueOf(dbstate.dbyff.dnblx)));
			j_obj.put("dbyff_dqyxfldj_desc",I18N.getText("MRState_1t2t_" + String.valueOf(dbstate.dbyff.dqyxfldj)));
			j_obj.put("dbyff_dqjt_desc", 	I18N.getText("MRState_1t2t_" + String.valueOf(dbstate.dbyff.dqjt)));
			j_obj.put("dbyff_protect_desc", I18N.getText("MRState_baodian_" + String.valueOf(dbstate.dbyff.protect)));
			j_obj.put("dbyff_reserve13_desc",String.valueOf(dbstate.dbyff.reserve13));
			j_obj.put("dbyff_reserve14_desc",String.valueOf(dbstate.dbyff.reserve14));
			j_obj.put("dbyff_keytype_desc", I18N.getText("MRState_ycmy_" + String.valueOf(dbstate.dbyff.keytype)));
			
			j_obj.put("dbfaila_sy", 	String.valueOf(dbstate.dbfaila.sy));
			j_obj.put("dbfaila_qy", 	String.valueOf(dbstate.dbfaila.qy));
			j_obj.put("dbfaila_gy", 	String.valueOf(dbstate.dbfaila.gy));
			j_obj.put("dbfaila_sl", 	String.valueOf(dbstate.dbfaila.sl));
			j_obj.put("dbfaila_ql", 	String.valueOf(dbstate.dbfaila.ql));
			j_obj.put("dbfaila_gz", 	String.valueOf(dbstate.dbfaila.gz));
			j_obj.put("dbfaila_clfx", 	String.valueOf(dbstate.dbfaila.clfx));
			j_obj.put("dbfaila_dx", 	String.valueOf(dbstate.dbfaila.dx));
			j_obj.put("dbfaila_dl", 	String.valueOf(dbstate.dbfaila.dl));
			j_obj.put("dbfaila_reserve9", 	String.valueOf(dbstate.dbfaila.reserve9));
			j_obj.put("dbfaila_reserve10", 	String.valueOf(dbstate.dbfaila.reserve10));
			j_obj.put("dbfaila_reserve11", 	String.valueOf(dbstate.dbfaila.reserve11));
			j_obj.put("dbfaila_reserve12", 	String.valueOf(dbstate.dbfaila.reserve12));
			j_obj.put("dbfaila_reserve13", 	String.valueOf(dbstate.dbfaila.reserve13));
			j_obj.put("dbfaila_reserve14", 	String.valueOf(dbstate.dbfaila.reserve14));
			j_obj.put("dbfaila_reserve15", 	String.valueOf(dbstate.dbfaila.reserve15));
			
			j_obj.put("dbfailb_sy", 	String.valueOf(dbstate.dbfailb.sy));
			j_obj.put("dbfailb_qy", 	String.valueOf(dbstate.dbfailb.qy));
			j_obj.put("dbfailb_gy", 	String.valueOf(dbstate.dbfailb.gy));
			j_obj.put("dbfailb_sl", 	String.valueOf(dbstate.dbfailb.sl));
			j_obj.put("dbfailb_ql", 	String.valueOf(dbstate.dbfailb.ql));
			j_obj.put("dbfailb_gz", 	String.valueOf(dbstate.dbfailb.gz));
			j_obj.put("dbfailb_clfx", 	String.valueOf(dbstate.dbfailb.clfx));
			j_obj.put("dbfailb_dx", 	String.valueOf(dbstate.dbfailb.dx));
			j_obj.put("dbfailb_dl", 	String.valueOf(dbstate.dbfailb.dl));
			j_obj.put("dbfailb_reserve9", 	String.valueOf(dbstate.dbfailb.reserve9));
			j_obj.put("dbfailb_reserve10", 	String.valueOf(dbstate.dbfailb.reserve10));
			j_obj.put("dbfailb_reserve11", 	String.valueOf(dbstate.dbfailb.reserve11));
			j_obj.put("dbfailb_reserve12", 	String.valueOf(dbstate.dbfailb.reserve12));
			j_obj.put("dbfailb_reserve13", 	String.valueOf(dbstate.dbfailb.reserve13));
			j_obj.put("dbfailb_reserve14", 	String.valueOf(dbstate.dbfailb.reserve14));
			j_obj.put("dbfailb_reserve15", 	String.valueOf(dbstate.dbfailb.reserve15));
			
			j_obj.put("dbfailc_sy", 	String.valueOf(dbstate.dbfailc.sy));
			j_obj.put("dbfailc_qy", 	String.valueOf(dbstate.dbfailc.qy));
			j_obj.put("dbfailc_gy", 	String.valueOf(dbstate.dbfailc.gy));
			j_obj.put("dbfailc_sl", 	String.valueOf(dbstate.dbfailc.sl));
			j_obj.put("dbfailc_ql", 	String.valueOf(dbstate.dbfailc.ql));
			j_obj.put("dbfailc_gz", 	String.valueOf(dbstate.dbfailc.gz));
			j_obj.put("dbfailc_clfx", 	String.valueOf(dbstate.dbfailc.clfx));
			j_obj.put("dbfailc_dx", 	String.valueOf(dbstate.dbfailc.dx));
			j_obj.put("dbfailc_dl", 	String.valueOf(dbstate.dbfailc.dl));
			j_obj.put("dbfailc_reserve9", 	String.valueOf(dbstate.dbfailc.reserve9));
			j_obj.put("dbfailc_reserve10", 	String.valueOf(dbstate.dbfailc.reserve10));
			j_obj.put("dbfailc_reserve11", 	String.valueOf(dbstate.dbfailc.reserve11));
			j_obj.put("dbfailc_reserve12", 	String.valueOf(dbstate.dbfailc.reserve12));
			j_obj.put("dbfailc_reserve13", 	String.valueOf(dbstate.dbfailc.reserve13));
			j_obj.put("dbfailc_reserve14", 	String.valueOf(dbstate.dbfailc.reserve14));
			j_obj.put("dbfailc_reserve15", 	String.valueOf(dbstate.dbfailc.reserve15));
			
			j_obj.put("dbfail_dynxx", 	String.valueOf(dbstate.dbfail.dynxx));
			j_obj.put("dbfail_dlnxx", 	String.valueOf(dbstate.dbfail.dlnxx));
			j_obj.put("dbfail_dybph", 	String.valueOf(dbstate.dbfail.dybph));
			j_obj.put("dbfail_dlbph", 	String.valueOf(dbstate.dbfail.dlbph));
			j_obj.put("dbfail_fzdysy", 	String.valueOf(dbstate.dbfail.fzdysy));
			j_obj.put("dbfail_dd", 		String.valueOf(dbstate.dbfail.dd));
			j_obj.put("dbfail_xlcz", 	String.valueOf(dbstate.dbfail.xlcz));
			j_obj.put("dbfail_zglyscxx",String.valueOf(dbstate.dbfail.zglyscxx));
			j_obj.put("dbfail_dlyzbph", String.valueOf(dbstate.dbfail.dlyzbph));
			j_obj.put("dbfail_reserve9", 	String.valueOf(dbstate.dbfail.reserve9));
			j_obj.put("dbfail_reserve10", 	String.valueOf(dbstate.dbfail.reserve10));
			j_obj.put("dbfail_reserve11", 	String.valueOf(dbstate.dbfail.reserve11));
			j_obj.put("dbfail_reserve12", 	String.valueOf(dbstate.dbfail.reserve12));
			j_obj.put("dbfail_reserve13", 	String.valueOf(dbstate.dbfail.reserve13));
			j_obj.put("dbfail_reserve14", 	String.valueOf(dbstate.dbfail.reserve14));
			j_obj.put("dbfail_reserve15", 	String.valueOf(dbstate.dbfail.reserve15));
			
			return j_obj.toString();
		}
	}
	
	//20131021  begin
	//2版电表运行状态字
	public static class YFF_DATA_DB2_RUNSTATE{
		public	short	mpid;							//测量点号
		public	ComntEsam2.SMARTDB2_STATE	dbstate = new ComntEsam2.SMARTDB2_STATE();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntEsam2.SMARTDB2_STATE.writeStream(byte_vect, dbstate);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid);
			ret_len += ComntStream.getDataSize(mpid);
			
			ret_len = ComntEsam2.SMARTDB2_STATE.getDataSize(byte_vect, offset, ret_len, dbstate);

			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("mpid", 			String.valueOf(mpid));
			
			j_obj.put("dbs1_reserve1", 	String.valueOf(dbstate.dbs1.reserve1));
			j_obj.put("dbs1_xljsfs", 	String.valueOf(dbstate.dbs1.xljsfs));
			j_obj.put("dbs1_szdc", 		String.valueOf(dbstate.dbs1.szdc));
			j_obj.put("dbs1_tdcbdc", 	String.valueOf(dbstate.dbs1.tdcbdc));
			j_obj.put("dbs1_ygglfx", 	String.valueOf(dbstate.dbs1.ygglfx));
			j_obj.put("dbs1_wgglfx", 	String.valueOf(dbstate.dbs1.wgglfx));
			j_obj.put("dbs1_reserve6", 	String.valueOf(dbstate.dbs1.reserve6));
			j_obj.put("dbs1_reserve7", 	String.valueOf(dbstate.dbs1.reserve7));
			j_obj.put("dbs1_kzhlcw", 	String.valueOf(dbstate.dbs1.kzhlcw));
			j_obj.put("dbs1_esamcw", 	String.valueOf(dbstate.dbs1.esamcw));
			j_obj.put("dbs1_reserve10", String.valueOf(dbstate.dbs1.reserve10));
			j_obj.put("dbs1_reserve11", String.valueOf(dbstate.dbs1.reserve11));
			j_obj.put("dbs1_nbcxcw", 	String.valueOf(dbstate.dbs1.nbcxcw));
			j_obj.put("dbs1_ccqgz", 	String.valueOf(dbstate.dbs1.ccqgz));
			j_obj.put("dbs1_tzzt", 		String.valueOf(dbstate.dbs1.tzzt));
			j_obj.put("dbs1_reserve15", String.valueOf(dbstate.dbs1.reserve15));
			                            
			j_obj.put("dbs2_aygp", 		String.valueOf(dbstate.dbs2.aygp));
			j_obj.put("dbs2_bygp", 		String.valueOf(dbstate.dbs2.bygp));
			j_obj.put("dbs2_cygp", 		String.valueOf(dbstate.dbs2.cygp));
			j_obj.put("dbs2_reserve3", 	String.valueOf(dbstate.dbs2.reserve3));
			j_obj.put("dbs2_awgp", 		String.valueOf(dbstate.dbs2.awgp));
			j_obj.put("dbs2_bwgp", 		String.valueOf(dbstate.dbs2.bwgp));
			j_obj.put("dbs2_cwgp", 		String.valueOf(dbstate.dbs2.cwgp));
			j_obj.put("dbs2_reserve7", 	String.valueOf(dbstate.dbs2.reserve7) );
			j_obj.put("dbs2_reserve8", 	String.valueOf(dbstate.dbs2.reserve8) );
			j_obj.put("dbs2_reserve9", 	String.valueOf(dbstate.dbs2.reserve9) );
			j_obj.put("dbs2_reserve10", String.valueOf(dbstate.dbs2.reserve10));
			j_obj.put("dbs2_reserve11", String.valueOf(dbstate.dbs2.reserve11));
			j_obj.put("dbs2_reserve12", String.valueOf(dbstate.dbs2.reserve12));
			j_obj.put("dbs2_reserve13", String.valueOf(dbstate.dbs2.reserve13));
			j_obj.put("dbs2_reserve14", String.valueOf(dbstate.dbs2.reserve14));
			j_obj.put("dbs2_reserve15", String.valueOf(dbstate.dbs2.reserve15));
			
			j_obj.put("dbyff_dqyxsd_desc", 		I18N.getText("MRState_1t2t_" + String.valueOf(dbstate.dbyff.dqyxsd)));
			j_obj.put("dbyff_gdfs_desc", 		I18N.getText("MRState_gdfs_" + String.valueOf(dbstate.dbyff.gdfs)));
			j_obj.put("dbyff_bcyx_desc", 		I18N.getText("MRState_OKNG_" + String.valueOf(dbstate.dbyff.bcyx)));
			j_obj.put("dbyff_jdqzt_desc", 		I18N.getText("MRState_TD_" + String.valueOf(dbstate.dbyff.jdqzt)));
			j_obj.put("dbyff_dqyxsq_desc", 		I18N.getText("MRState_1t2t_" + String.valueOf(dbstate.dbyff.dqyxsq)));
			j_obj.put("dbyff_jdqmlzt_desc", 	I18N.getText("MRState_TD_" + String.valueOf(dbstate.dbyff.jdqmlzt)));
			j_obj.put("dbyff_ytzbjzt_desc", 	I18N.getText("MRState_WY_" + String.valueOf(dbstate.dbyff.ytzbjzt)));
			j_obj.put("dbyff_dnblx_desc", 		I18N.getText("MRState_dnblx_" + String.valueOf(dbstate.dbyff.dnblx)));
			j_obj.put("dbyff_dqyxfldj_desc",	String.valueOf(dbstate.dbyff.reserve10));
			j_obj.put("dbyff_dqjt_desc",		String.valueOf(dbstate.dbyff.reserve11));
			j_obj.put("dbyff_protect_desc", 	I18N.getText("MRState_baodian_" + String.valueOf(dbstate.dbyff.protect)));
			j_obj.put("dbyff_authstate_desc",	I18N.getText("MRState_sfrz_" + String.valueOf(dbstate.dbyff.authstate)));
			j_obj.put("dbyff_localkh_desc", 	I18N.getText("MRState_localkh_" + String.valueOf(dbstate.dbyff.localkh)));
			j_obj.put("dbyff_remotekh_desc", 	I18N.getText("MRState_remotekh_" + String.valueOf(dbstate.dbyff.remotekh)));
			
			j_obj.put("dbfaila_sy", 		String.valueOf(dbstate.dbfaila.sy));
			j_obj.put("dbfaila_qy", 		String.valueOf(dbstate.dbfaila.qy));
			j_obj.put("dbfaila_gy", 		String.valueOf(dbstate.dbfaila.gy));
			j_obj.put("dbfaila_sl", 		String.valueOf(dbstate.dbfaila.sl));
			j_obj.put("dbfaila_ql", 		String.valueOf(dbstate.dbfaila.ql));
			j_obj.put("dbfaila_gz", 		String.valueOf(dbstate.dbfaila.gz));
			j_obj.put("dbfaila_clfx", 		String.valueOf(dbstate.dbfaila.clfx));
			j_obj.put("dbfaila_dx", 		String.valueOf(dbstate.dbfaila.dx));
			j_obj.put("dbfaila_dl", 		String.valueOf(dbstate.dbfaila.dl));
			j_obj.put("dbfaila_reserve9", 	String.valueOf(dbstate.dbfaila.reserve9) );
			j_obj.put("dbfaila_reserve10",  String.valueOf(dbstate.dbfaila.reserve10));
			j_obj.put("dbfaila_reserve11",  String.valueOf(dbstate.dbfaila.reserve11));
			j_obj.put("dbfaila_reserve12",  String.valueOf(dbstate.dbfaila.reserve12));
			j_obj.put("dbfaila_reserve13",  String.valueOf(dbstate.dbfaila.reserve13));
			j_obj.put("dbfaila_reserve14",  String.valueOf(dbstate.dbfaila.reserve14));
			j_obj.put("dbfaila_reserve15",  String.valueOf(dbstate.dbfaila.reserve15));
			
			
			j_obj.put("dbfailb_sy", 		String.valueOf(dbstate.dbfailb.sy));
			j_obj.put("dbfailb_qy", 		String.valueOf(dbstate.dbfailb.qy));
			j_obj.put("dbfailb_gy", 		String.valueOf(dbstate.dbfailb.gy));
			j_obj.put("dbfailb_sl", 		String.valueOf(dbstate.dbfailb.sl));
			j_obj.put("dbfailb_ql", 		String.valueOf(dbstate.dbfailb.ql));
			j_obj.put("dbfailb_gz", 		String.valueOf(dbstate.dbfailb.gz));
			j_obj.put("dbfailb_clfx", 		String.valueOf(dbstate.dbfailb.clfx));
			j_obj.put("dbfailb_dx", 		String.valueOf(dbstate.dbfailb.dx));
			j_obj.put("dbfailb_dl", 		String.valueOf(dbstate.dbfailb.dl));
			j_obj.put("dbfailb_reserve9", 	String.valueOf(dbstate.dbfailb.reserve9) );
			j_obj.put("dbfailb_reserve10",  String.valueOf(dbstate.dbfailb.reserve10));
			j_obj.put("dbfailb_reserve11",  String.valueOf(dbstate.dbfailb.reserve11));
			j_obj.put("dbfailb_reserve12",  String.valueOf(dbstate.dbfailb.reserve12));
			j_obj.put("dbfailb_reserve13",  String.valueOf(dbstate.dbfailb.reserve13));
			j_obj.put("dbfailb_reserve14",  String.valueOf(dbstate.dbfailb.reserve14));
			j_obj.put("dbfailb_reserve15",  String.valueOf(dbstate.dbfailb.reserve15));
			
			j_obj.put("dbfailc_sy", 		String.valueOf(dbstate.dbfailc.sy));
			j_obj.put("dbfailc_qy", 		String.valueOf(dbstate.dbfailc.qy));
			j_obj.put("dbfailc_gy", 		String.valueOf(dbstate.dbfailc.gy));
			j_obj.put("dbfailc_sl", 		String.valueOf(dbstate.dbfailc.sl));
			j_obj.put("dbfailc_ql", 		String.valueOf(dbstate.dbfailc.ql));
			j_obj.put("dbfailc_gz", 		String.valueOf(dbstate.dbfailc.gz));
			j_obj.put("dbfailc_clfx", 		String.valueOf(dbstate.dbfailc.clfx));
			j_obj.put("dbfailc_dx", 		String.valueOf(dbstate.dbfailc.dx));
			j_obj.put("dbfailc_dl", 		String.valueOf(dbstate.dbfailc.dl));
			j_obj.put("dbfailc_reserve9", 	String.valueOf(dbstate.dbfailc.reserve9));
			j_obj.put("dbfailc_reserve10",  String.valueOf(dbstate.dbfailc.reserve10));
			j_obj.put("dbfailc_reserve11",  String.valueOf(dbstate.dbfailc.reserve11));
			j_obj.put("dbfailc_reserve12",  String.valueOf(dbstate.dbfailc.reserve12));
			j_obj.put("dbfailc_reserve13",  String.valueOf(dbstate.dbfailc.reserve13));
			j_obj.put("dbfailc_reserve14",  String.valueOf(dbstate.dbfailc.reserve14));
			j_obj.put("dbfailc_reserve15",  String.valueOf(dbstate.dbfailc.reserve15));
			
			j_obj.put("dbfail_dynxx", 		String.valueOf(dbstate.dbfail.dynxx));
			j_obj.put("dbfail_dlnxx", 		String.valueOf(dbstate.dbfail.dlnxx));
			j_obj.put("dbfail_dybph", 		String.valueOf(dbstate.dbfail.dybph));
			j_obj.put("dbfail_dlbph", 		String.valueOf(dbstate.dbfail.dlbph));
			j_obj.put("dbfail_fzdysy", 		String.valueOf(dbstate.dbfail.fzdysy));
			j_obj.put("dbfail_dd", 			String.valueOf(dbstate.dbfail.dd));
			j_obj.put("dbfail_xlcz", 		String.valueOf(dbstate.dbfail.xlcz));
			j_obj.put("dbfail_zglyscxx", 	String.valueOf(dbstate.dbfail.zglyscxx));
			j_obj.put("dbfail_dlyzbph", 	String.valueOf(dbstate.dbfail.dlyzbph));
			j_obj.put("dbfail_openmeter", 	String.valueOf(dbstate.dbfail.openmeter));
			j_obj.put("dbfail_syopendn", 	String.valueOf(dbstate.dbfail.opendn));
			j_obj.put("dbfail_reserve11", 	String.valueOf(dbstate.dbfail.reserve11));
			j_obj.put("dbfail_reserve12", 	String.valueOf(dbstate.dbfail.reserve12));
			j_obj.put("dbfail_reserve13", 	String.valueOf(dbstate.dbfail.reserve13));
			j_obj.put("dbfail_reserve14", 	String.valueOf(dbstate.dbfail.reserve14));
			j_obj.put("dbfail_reserve15", 	String.valueOf(dbstate.dbfail.reserve15));
			
			//插卡状态字
			j_obj.put("dbcard_cardstate", 	String.valueOf(dbstate.dbcardstate.cardstate));
			j_obj.put("dbcard_reserve2",  	String.valueOf(dbstate.dbcardstate.reserve2));
			j_obj.put("dbcard_reserve3",  	String.valueOf(dbstate.dbcardstate.reserve3));
			j_obj.put("dbcard_reserve4",  	String.valueOf(dbstate.dbcardstate.reserve4));
			j_obj.put("dbcard_reserve5",  	String.valueOf(dbstate.dbcardstate.reserve5));
			j_obj.put("dbcard_reserve6",  	String.valueOf(dbstate.dbcardstate.reserve6));
			j_obj.put("dbcard_reserve7",  	String.valueOf(dbstate.dbcardstate.reserve7));
			j_obj.put("dbcard_reserve8",  	String.valueOf(dbstate.dbcardstate.reserve8));
			j_obj.put("dbcard_reserve9",  	String.valueOf(dbstate.dbcardstate.reserve9));
			j_obj.put("dbcard_reserve10", 	String.valueOf(dbstate.dbcardstate.reserve10));
			j_obj.put("dbcard_reserve11", 	String.valueOf(dbstate.dbcardstate.reserve11));
			j_obj.put("dbcard_reserve12", 	String.valueOf(dbstate.dbcardstate.reserve12));
			j_obj.put("dbcard_reserve13", 	String.valueOf(dbstate.dbcardstate.reserve13));
			j_obj.put("dbcard_reserve14", 	String.valueOf(dbstate.dbcardstate.reserve14));
			j_obj.put("dbcard_reserve15", 	String.valueOf(dbstate.dbcardstate.reserve15));
			
			
			return j_obj.toString();
		}
	}

	//电表费率相关重要参数 数值
	public static class YFF_DATA_DB2_FEIPARA{
		public short mpid;
		public ComntEsam2.SMARTDB2_FEIPARA	dbfeipara = new ComntEsam2.SMARTDB2_FEIPARA();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntEsam2.SMARTDB2_FEIPARA.writeStream(byte_vect, dbfeipara);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid);
			ret_len += ComntStream.getDataSize(mpid);
			
			//此处是否有len这个参数，再问问???
			ret_len = ComntEsam2.SMARTDB2_FEIPARA.getDataSize(byte_vect, offset, ret_len, dbfeipara);

			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("mpid", 			String.valueOf(mpid));
			j_obj.put("rever_money", 	String.valueOf(dbfeipara.rever_money));
			j_obj.put("remain_money", 	String.valueOf(dbfeipara.remain_money));
			j_obj.put("overd_money", 	String.valueOf(dbfeipara.overd_money));
			j_obj.put("money_limit", 	String.valueOf(dbfeipara.money_limit));
			
			j_obj.put("js_ddhh1", 		String.valueOf(dbfeipara.js_ddhh1));
			j_obj.put("js_ddhh2", 		String.valueOf(dbfeipara.js_ddhh2));
			j_obj.put("js_ddhh3", 		String.valueOf(dbfeipara.js_ddhh3));
			
			return j_obj.toString();
		}
		
	}
	
	//电表密钥相关重要参数 数值
	public static class YFF_DATA_DB2_KEYSTATE{
		public short mpid;
		public ComntEsam2.SMARTDB2_KEYSTATE	dbkeystate = new ComntEsam2.SMARTDB2_KEYSTATE();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, mpid);
			ret_len += ComntEsam2.SMARTDB2_KEYSTATE.writeStream(byte_vect, dbkeystate);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			mpid = ComntStream.readStream(byte_vect, offset + ret_len, mpid);
			ret_len += ComntStream.getDataSize(mpid);
			//此处是否有len这个参数，再问问???
			ret_len = ComntEsam2.SMARTDB2_KEYSTATE.getDataSize(byte_vect, offset, ret_len, dbkeystate);

			return ret_len;
		}
		
		public String toJsonString(){
			JSONObject j_obj = new JSONObject();
			j_obj.put("mpid", mpid);
			j_obj.put("key_num", dbkeystate.key_num);
			j_obj.put("key_state", dbkeystate.key_state);
			
			return j_obj.toString();
		}
	}
	
	//电表阶梯费率相关重要参数
	public static class YFF_DATA_DB2_DYCALLJTFEE extends YFF_DATA_DYCALLJTFEE{};
	//end
	
	//高压召预付费
	public static class YFF_DATA_GY_CALLREMAIN {
		public	short				zjgid;
		public	YFFGY_CALLREMAIN	readpay = new YFFGY_CALLREMAIN();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += YFFGY_CALLREMAIN.writeStream(byte_vect, readpay);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid);
			ret_len += ComntStream.getDataSize(zjgid);
			
			ret_len = YFFGY_CALLREMAIN.getDataSize(byte_vect, offset, ret_len, readpay);

			return ret_len;
		}

//		public void fromJsonString(String jstr) {
//			JSONObject j_obj = JSONObject.fromObject(jstr);	
//
//			zjgid 			 = (short)CommBase.strtoi(j_obj.getString("mpid"));
//			
//			readpay.fee_type 	= (byte)CommBase.strtoi(j_obj.getString("fee_type"));
//			readpay.cacl_type 	= (byte)CommBase.strtoi(j_obj.getString("cacl_type"));
//			readpay.buy_time 	= CommBase.strtoi(j_obj.getString("buy_time"));
//			readpay.nowval 		= CommBase.strtof(j_obj.getString("nowval"));
//			readpay.totval 		= CommBase.strtof(j_obj.getString("totval"));
//			readpay.alarm_val1	= CommBase.strtof(j_obj.getString("alarm_val1"));
//			readpay.alarm_val2	= CommBase.strtof(j_obj.getString("alarm_val2"));
//			readpay.yff_flag	= (byte)CommBase.strtoi(j_obj.getString("yff_flag"));
//		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("zjgid", 	String.valueOf(zjgid));
			
			j_obj.put("fee_type", 	String.valueOf(readpay.fee_type));
			j_obj.put("cacl_type", 	String.valueOf(readpay.cacl_type));
			j_obj.put("buy_time", 	String.valueOf(readpay.buy_time));
			j_obj.put("nowval", 	String.valueOf(readpay.nowval));
			j_obj.put("totval", 	String.valueOf(readpay.totval));
			j_obj.put("alarm_val1", String.valueOf(readpay.alarm_val1));
			j_obj.put("alarm_val2", String.valueOf(readpay.alarm_val2));
			j_obj.put("yff_flag", 	String.valueOf(readpay.yff_flag));
			
			return j_obj.toString();
		}
	}
	


	//高压召费率参数
	public static class YFF_DATA_GY_CALLFEERATE {
		public	short				zjgid;
		public	YFFGY_CALLFEERATE	callfeerate = new YFFGY_CALLFEERATE();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, zjgid);
			ret_len += YFFGY_CALLFEERATE.writeStream(byte_vect, callfeerate);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			zjgid = ComntStream.readStream(byte_vect, offset + ret_len, zjgid);
			ret_len += ComntStream.getDataSize(zjgid);
			
			ret_len = YFFGY_CALLFEERATE.getDataSize(byte_vect, offset, ret_len, callfeerate);

			return ret_len;
		}

//		public void fromJsonString(String jstr) {
//			JSONObject j_obj = JSONObject.fromObject(jstr);	
//
//			zjgid 			 = (short)CommBase.strtoi(j_obj.getString("mpid"));
//			
//			callfeerate.rated_z = CommBase.strtof(j_obj.getString("rated_z"));
//			callfeerate.ratef_j = CommBase.strtof(j_obj.getString("ratef_j"));
//			callfeerate.ratef_f = CommBase.strtof(j_obj.getString("ratef_f"));
//			callfeerate.ratef_p = CommBase.strtof(j_obj.getString("ratef_p"));
//			callfeerate.ratef_g = CommBase.strtof(j_obj.getString("ratef_g"));
//			callfeerate.pay_add1 = CommBase.strtof(j_obj.getString("pay_add1"));
//			callfeerate.pay_add2 = CommBase.strtof(j_obj.getString("pay_add2"));
//			callfeerate.pay_add3 = CommBase.strtof(j_obj.getString("pay_add3"));
//		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("zjgid", 	String.valueOf(zjgid));
			
			j_obj.put("rated_z", 	String.valueOf(callfeerate.rated_z));
			j_obj.put("ratef_j", 	String.valueOf(callfeerate.ratef_j));
			j_obj.put("ratef_f", 	String.valueOf(callfeerate.ratef_f));
			j_obj.put("ratef_p", 	String.valueOf(callfeerate.ratef_p));
			j_obj.put("ratef_g", 	String.valueOf(callfeerate.ratef_g));
			j_obj.put("pay_add1", 	String.valueOf(callfeerate.pay_add1));
			j_obj.put("pay_add2", 	String.valueOf(callfeerate.pay_add2));
			j_obj.put("pay_add3", 	String.valueOf(callfeerate.pay_add3));
			
			return j_obj.toString();
		}
	}
	

	//高压召保电参数
	public static class YFF_DATA_GY_CALLPROTECT extends YFF_GYSET_PROTECT{

	}
	
	//发送报文
	public static class YFF_RAWDATATX{
		
		public ComntVector.ByteVector data_buf = new ComntVector.ByteVector();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			int vect_size = 0;
			
			vect_size = data_buf.size();
			ret_len += ComntStream.writeStream(byte_vect, vect_size);
			ret_len += ComntStream.writeStream(byte_vect, data_buf.getaddr(), 0, vect_size); 
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			int vect_size = 0;

			vect_size = ComntStream.readStream(byte_vect, offset + ret_len, vect_size);
			ret_len += ComntStream.getDataSize(vect_size);

			data_buf.resize(vect_size);
			ComntStream.readStream(byte_vect, offset + ret_len, data_buf.getaddr(), 0, vect_size);
			ret_len += ComntStream.getDataSize((byte)1) * vect_size;

			return ret_len;
		}
	}
	
				
	//接收报文
	public static class YFF_RAWDATARX extends YFF_RAWDATATX{

	}
					
	//加密机报文
	public static class YFF_RAWDATACRYP extends YFF_RAWDATATX{

	}

	
	
	//返回的预付费操作索引
	public static class YFF_DATA_OPER_IDX {
		
		public	YFFDef.YFF_OPRECORD_IDX	operidx = new YFFDef.YFF_OPRECORD_IDX();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += YFFDef.YFF_OPRECORD_IDX.writeStream(byte_vect, operidx);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ret_len = YFFDef.YFF_OPRECORD_IDX.getDataSize(byte_vect, offset, ret_len, operidx);

			return ret_len;
		}

		public void fromJsonString(String jstr) {
			JSONObject j_obj = JSONObject.fromObject(jstr);	

			operidx.rtu_id 	= CommBase.strtoi(j_obj.getString("rtu_id"));
			operidx.id 	 	= (short)CommBase.strtoi(j_obj.getString("id"));
			operidx.op_type = (byte)CommBase.strtoi(j_obj.getString("op_type"));
			operidx.op_date = CommBase.strtoi(j_obj.getString("op_date"));
			operidx.op_time = CommBase.strtoi(j_obj.getString("op_time"));
			
			String tmp = j_obj.getString("wasteno");
			while(tmp.length() < operidx.wasteno.length) {
				tmp += " ";
			}
			for (int i = 0; i < operidx.wasteno.length; i++) {
				operidx.wasteno[i] = (byte)tmp.charAt(i);
			}
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("rtu_id", 	String.valueOf(operidx.rtu_id));
			j_obj.put("id", 		String.valueOf(operidx.id));
			j_obj.put("op_type", 	String.valueOf(operidx.op_type));
			j_obj.put("op_date", 	String.valueOf(operidx.op_date));
			j_obj.put("op_time", 	String.valueOf(operidx.op_time));
			j_obj.put("wasteno", 	ComntFunc.byte2String(operidx.wasteno));
			return j_obj.toString();
		}
	}
	

	//低压操作-返回的预付费参数及状态
	public static class YFF_DATA_DYOPER_PARASTATE {
		
		public	YFFDef.YFF_MPPAYPARA		para 	 = new YFFDef.YFF_MPPAYPARA();
		public	YFFDef.YFF_MPPAYSTATE		state 	 = new YFFDef.YFF_MPPAYSTATE();
		public	YFFDef.YFF_MPPAY_ALARMSTATE	alarm 	 = new YFFDef.YFF_MPPAY_ALARMSTATE();
		public	YFFDef.YFF_ALARM_COMMDATA	commdata = new YFFDef.YFF_ALARM_COMMDATA();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += YFFDef.YFF_MPPAYPARA.writeStream(byte_vect, para);
			ret_len += YFFDef.YFF_MPPAYSTATE.writeStream(byte_vect, state);
			ret_len += YFFDef.YFF_MPPAY_ALARMSTATE.writeStream(byte_vect, alarm);
			ret_len += YFFDef.YFF_ALARM_COMMDATA.writeStream(byte_vect, commdata);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ret_len = YFFDef.YFF_MPPAYPARA.getDataSize(byte_vect, offset, ret_len, para);
			
			ret_len = YFFDef.YFF_MPPAYSTATE.getDataSize(byte_vect, offset, ret_len, state);
			
			ret_len = YFFDef.YFF_MPPAY_ALARMSTATE.getDataSize(byte_vect, offset, ret_len, alarm);
			
			ret_len = YFFDef.YFF_ALARM_COMMDATA.getDataSize(byte_vect, offset, ret_len, commdata);
			
			return ret_len;
		}

//		public void fromJsonString(String jstr) {
//			JSONObject j_obj	= JSONObject.fromObject(jstr);
//			para.rtu_id			= CommBase.strtoi(j_obj.getString("rtu_id"));
//			para.mp_id			= (short)CommBase.strtoi(j_obj.getString("mp_id"));
//			para.feectrl_type 	= (byte)CommBase.strtoi(j_obj.getString("feectrl_type"));
//			para.pay_type 		= (byte)CommBase.strtoi(j_obj.getString("pay_type"));
//			para.yffmeter_type 	= (byte)CommBase.strtoi(j_obj.getString("yffmeter_type"));
//			para.feeproj_id 	= (short)CommBase.strtoi(j_obj.getString("feeproj_id"));
//			para.yffalarm_id 	= (short)CommBase.strtoi(j_obj.getString("yffalarm_id"));
//			para.prot_st 		= (byte)CommBase.strtoi(j_obj.getString("prot_st"));
//			para.prot_ed 		= (byte)CommBase.strtoi(j_obj.getString("prot_ed"));
//			para.ngloprot_flag 	= (byte)CommBase.strtoi(j_obj.getString("ngloprot_flag"));
//			para.key_version 	= (byte)CommBase.strtoi(j_obj.getString("key_version"));
//			para.cryplink_id 	= (byte)CommBase.strtoi(j_obj.getString("cryplink_id"));
//			para.pay_add 		= CommBase.strtoi(j_obj.getString("pay_add"));
//			para.tz_val 		= CommBase.strtoi(j_obj.getString("tz_val"));
//			para.power_relaf 	= (byte)CommBase.strtoi(j_obj.getString("power_relaf"));
//			para.power_rela1 	= (short)CommBase.strtoi(j_obj.getString("power_rela1"));
//			para.power_rela2 	= (short)CommBase.strtoi(j_obj.getString("power_rela2"));
//			para.power_relask1 	= (byte)CommBase.strtoi(j_obj.getString("power_relask1"));
//			para.power_relask2 	= (byte)CommBase.strtoi(j_obj.getString("power_relask2"));
//			para.fee_chgf 		= (byte)CommBase.strtoi(j_obj.getString("fee_chgf"));
//			para.fee_chgid 		= (byte)CommBase.strtoi(j_obj.getString("fee_chgid"));
//			para.fee_chgdate 	= CommBase.strtoi(j_obj.getString("fee_chgdate"));
//			para.fee_chgtime 	= CommBase.strtoi(j_obj.getString("fee_chgtime"));
//			
//			state.rtu_id		= CommBase.strtoi(j_obj.getString("rtu_id"));
//			state.mp_id			= (short)CommBase.strtoi(j_obj.getString("mp_id"));
//			state.cus_state		= (byte)CommBase.strtoi(j_obj.getString("cus_state"));
//			state.op_type		= (byte)CommBase.strtoi(j_obj.getString("op_type"));
//			state.op_date		= CommBase.strtoi(j_obj.getString("op_date"));
//			state.op_time		= CommBase.strtoi(j_obj.getString("op_time"));
//			state.pay_money		= CommBase.strtof(j_obj.getString("pay_money"));
//			state.othjs_money	= CommBase.strtof(j_obj.getString("othjs_money"));
//			state.zb_money		= CommBase.strtof(j_obj.getString("zb_money"));
//			state.all_money		= CommBase.strtof(j_obj.getString("all_money"));
//			state.shutdown_val	= CommBase.strtof(j_obj.getString("shutdown_val"));
//			
//			String tmp = j_obj.getString("jsbd_zyz");
//			String tmps[] = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.jsbd_zyz[i] = CommBase.strtof(tmps[i]);
//			}
//			tmp = j_obj.getString("jsbd_zyj");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.jsbd_zyj[i] = CommBase.strtof(tmps[i]);
//			}
//			tmp = j_obj.getString("jsbd_zyf");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.jsbd_zyf[i] = CommBase.strtof(tmps[i]);
//			}
//			tmp = j_obj.getString("jsbd_zyp");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.jsbd_zyp[i] = CommBase.strtof(tmps[i]);
//			}
//			tmp = j_obj.getString("jsbd_zyg");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.jsbd_zyg[i] = CommBase.strtof(tmps[i]);
//			}
//			
//			state.jsbd_ymd		= CommBase.strtoi(j_obj.getString("jsbd_ymd"));
//			state.buy_times		= CommBase.strtoi(j_obj.getString("buy_times"));
//			state.calc_mdhmi	= CommBase.strtoi(j_obj.getString("calc_mdhmi"));
//			state.calc_bdymd	= CommBase.strtoi(j_obj.getString("calc_bdymd"));
//			
//			tmp = j_obj.getString("calc_zyz");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.calc_zyz[i] = CommBase.strtof(tmps[i]);
//			}
//			tmp = j_obj.getString("calc_zyj");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.calc_zyj[i] = CommBase.strtof(tmps[i]);
//			}
//			tmp = j_obj.getString("calc_zyf");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.calc_zyf[i] = CommBase.strtof(tmps[i]);
//			}
//			tmp = j_obj.getString("calc_zyp");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.calc_zyp[i] = CommBase.strtof(tmps[i]);
//			}
//			tmp = j_obj.getString("calc_zyg");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.calc_zyg[i] = CommBase.strtof(tmps[i]);
//			}
//			
//			state.now_remain	= CommBase.strtof(j_obj.getString("now_remain"));
//			state.bj_bd			= CommBase.strtof(j_obj.getString("bj_bd"));
//			state.tz_bd			= CommBase.strtof(j_obj.getString("tz_bd"));
//			state.cs_al1_state	= (byte)CommBase.strtoi(j_obj.getString("cs_al1_state"));
//			state.cs_al2_state	= (byte)CommBase.strtoi(j_obj.getString("cs_al2_state"));
//			state.cs_fhz_state	= (byte)CommBase.strtoi(j_obj.getString("cs_fhz_state"));
//			state.al1_mdhmi		= CommBase.strtoi(j_obj.getString("al1_mdhmi"));
//			state.al2_mdhmi		= CommBase.strtoi(j_obj.getString("al2_mdhmi"));
//			state.fhz_mdhmi		= CommBase.strtoi(j_obj.getString("fhz_mdhmi"));
//			
//			tmp = j_obj.getString("fz_zyz");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.fz_zyz[i] = CommBase.strtof(tmps[i]);
//			}
//			
//			state.yc_flag1	= CommBase.strtoi(j_obj.getString("yc_flag1"));
//			state.yc_flag2	= CommBase.strtoi(j_obj.getString("yc_flag2"));
//			state.yc_flag3	= CommBase.strtoi(j_obj.getString("yc_flag3"));
//			state.hb_date	= CommBase.strtoi(j_obj.getString("hb_date"));
//			state.hb_time	= CommBase.strtoi(j_obj.getString("hb_time"));
//			state.kh_date	= CommBase.strtoi(j_obj.getString("kh_date"));
//			state.xh_date	= CommBase.strtoi(j_obj.getString("xh_date"));
//			state.total_gdz	= CommBase.strtof(j_obj.getString("total_gdz"));
//			state.reserve1	= CommBase.strtoi(j_obj.getString("reserve1"));
//			
//			alarm.rtu_id			= CommBase.strtoi(j_obj.getString("rtu_id"));
//			alarm.mp_id				= (short)CommBase.strtoi(j_obj.getString("mp_id"));
//			alarm.qr_al1_1_state	= (byte)CommBase.strtoi(j_obj.getString("qr_al1_1_state"));
//			alarm.qr_al1_2_state	= (byte)CommBase.strtoi(j_obj.getString("qr_al1_2_state"));
//			alarm.qr_al1_3_state	= (byte)CommBase.strtoi(j_obj.getString("qr_al1_3_state"));
//			alarm.qr_al2_1_state	= (byte)CommBase.strtoi(j_obj.getString("qr_al2_1_state"));
//			alarm.qr_al2_2_state	= (byte)CommBase.strtoi(j_obj.getString("qr_al2_2_state"));
//			alarm.qr_al2_3_state	= (byte)CommBase.strtoi(j_obj.getString("qr_al2_3_state"));
//			alarm.qr_fhz_state		= (byte)CommBase.strtoi(j_obj.getString("qr_fhz_state"));
//			alarm.qr_fhz_rf1_state	= (byte)CommBase.strtoi(j_obj.getString("qr_fhz_rf1_state"));
//			alarm.qr_fhz_rf2_state	= (byte)CommBase.strtoi(j_obj.getString("qr_fhz_rf2_state"));
//			alarm.qr_fz_times		= (byte)CommBase.strtoi(j_obj.getString("qr_fz_times"));
//			alarm.qr_fz_rf1_times	= (byte)CommBase.strtoi(j_obj.getString("qr_fz_rf1_times"));
//			alarm.qr_fz_rf2_times	= (byte)CommBase.strtoi(j_obj.getString("qr_fz_rf2_times"));
//			alarm.qr_al1_1_mdhmi	= CommBase.strtoi(j_obj.getString("qr_al1_1_mdhmi"));
//			alarm.qr_al1_2_mdhmi	= CommBase.strtoi(j_obj.getString("qr_al1_2_mdhmi"));
//			alarm.qr_al1_3_mdhmi	= CommBase.strtoi(j_obj.getString("qr_al1_3_mdhmi"));
//			alarm.qr_al2_1_mdhmi	= CommBase.strtoi(j_obj.getString("qr_al2_1_mdhmi"));
//			alarm.qr_al2_2_mdhmi	= CommBase.strtoi(j_obj.getString("qr_al2_2_mdhmi"));
//			alarm.qr_al2_3_mdhmi	= CommBase.strtoi(j_obj.getString("qr_al2_3_mdhmi"));
//			alarm.qr_fhz_mdhmi		= CommBase.strtoi(j_obj.getString("qr_fhz_mdhmi"));
//			alarm.qr_fhz_rf1_mdhmi	= CommBase.strtoi(j_obj.getString("qr_fhz_rf1_mdhmi"));
//			alarm.qr_fhz_rf2_mdhmi	= CommBase.strtoi(j_obj.getString("qr_fhz_rf2_mdhmi"));
//			alarm.cg_fhz_mdhmi		= CommBase.strtoi(j_obj.getString("cg_fhz_mdhmi"));
//			alarm.cg_fhz_rf1_mdhmi	= CommBase.strtoi(j_obj.getString("cg_fhz_rf1_mdhmi"));
//			alarm.cg_fhz_rf2_mdhmi	= CommBase.strtoi(j_obj.getString("cg_fhz_rf2_mdhmi"));
//			alarm.qr_al1_1_uuid		= CommBase.strtoi(j_obj.getString("qr_al1_1_uuid"));
//			alarm.qr_al2_1_uuid		= CommBase.strtoi(j_obj.getString("qr_al2_1_uuid"));
//			
//			tmp = j_obj.getString("out_info");
//			while(tmp.length() < alarm.out_info.length) {
//				tmp += " ";
//			}
//			for (int i = 0; i < alarm.out_info.length; i++) {
//				alarm.out_info[i] = (byte)tmp.charAt(i);
//			}
//			
//			commdata.tmp_al1_1_mdhmi	= CommBase.strtoi(j_obj.getString("tmp_al1_1_mdhmi"));
//			commdata.tmp_al1_2_mdhmi	= CommBase.strtoi(j_obj.getString("tmp_al1_2_mdhmi"));
//			commdata.tmp_al1_3_mdhmi	= CommBase.strtoi(j_obj.getString("tmp_al1_3_mdhmi"));
//			commdata.tmp_al2_1_mdhmi	= CommBase.strtoi(j_obj.getString("tmp_al2_1_mdhmi"));
//			commdata.tmp_al2_2_mdhmi	= CommBase.strtoi(j_obj.getString("tmp_al2_2_mdhmi"));
//			commdata.tmp_al2_3_mdhmi	= CommBase.strtoi(j_obj.getString("tmp_al2_3_mdhmi"));
//			commdata.tmp_fhz_mdhmi		= CommBase.strtoi(j_obj.getString("tmp_fhz_mdhmi"));
//			commdata.tmp_fhz_rf1_mdhmi	= CommBase.strtoi(j_obj.getString("tmp_fhz_rf1_mdhmi"));
//			commdata.tmp_fhz_rf2_mdhmi	= CommBase.strtoi(j_obj.getString("tmp_fhz_rf2_mdhmi"));
//			commdata.tmp_prot_flag		= (byte)CommBase.strtoi(j_obj.getString("tmp_prot_flag"));
//			commdata.update_alarmstate	= (byte)CommBase.strtoi(j_obj.getString("update_alarmstate"));
//		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("rtu_id", 		String.valueOf(para.rtu_id));
			j_obj.put("mp_id", 			String.valueOf(para.mp_id));
			j_obj.put("cacl_type", 		String.valueOf(para.cacl_type));
			j_obj.put("cacl_type_desc", Rd.getDict(Dict.DICTITEM_FEETYPE, para.cacl_type));
			j_obj.put("feectrl_type", 	Rd.getDict(Dict.DICTITEM_PREPAYTYPE, para.feectrl_type));
			j_obj.put("pay_type", 		para.pay_type);
			j_obj.put("pay_type_desc", 	Rd.getDict(Dict.DICTITEM_PAYTYPE, para.pay_type));
			j_obj.put("writecard_no", 	ComntFunc.byte2String(para.writecard_no));
			j_obj.put("yffmeter_type", 	para.yffmeter_type);
			j_obj.put("yffmeter_type_desc", 	Rd.getDict(Dict.DICTITEM_YFFMETERTYPE, para.yffmeter_type));
			
			//费率方案
			j_obj.put("feeproj_id", para.feeproj_id);
			
			Object obj = Rd.getRecord(YDTable.TABLECLASS_YFFRATEPARA, para.feeproj_id);
			if(obj != null){
				j_obj.put("feeproj_desc", ((YffRatePara)obj).getDescribe());
				j_obj.put("moneyLimit", ((YffRatePara)obj).getMoneyLimit());
				
				//费率方案
				j_obj.put("feeType", ((YffRatePara)obj).getFeeType());
			}

			j_obj.put("feeproj_detail", Rd.getYffRateDesc((YffRatePara)obj));
			j_obj.put("feeproj_reteval", Rd.getYffRateVal((YffRatePara)obj));
			
			//报警方案
			j_obj.put("yffalarm_id", para.yffalarm_id);
			obj = Rd.getRecord(YDTable.TABLECLASS_YFFALARMPARA, para.yffalarm_id);
			if(obj != null){
				j_obj.put("yffalarm_desc", ((YffAlarmPara)obj).getDescribe());
				j_obj.put("yffalarm_alarm1", ((YffAlarmPara)obj).getAlarm1());
				j_obj.put("yffalarm_alarm2", ((YffAlarmPara)obj).getAlarm2());
			}else{
				j_obj.put("yffalarm_desc", "");
				j_obj.put("yffalarm_alarm1", "");
				j_obj.put("yffalarm_alarm2", "");
			}
			j_obj.put("yffalarm_detail", Rd.getYffAlarmDesc((YffAlarmPara)obj));
			
			j_obj.put("prot_st", 		String.valueOf(para.prot_st));
			j_obj.put("prot_ed", 		String.valueOf(para.prot_ed));
			j_obj.put("ngloprot_flag", 	String.valueOf(para.ngloprot_flag));
			j_obj.put("key_version", 	String.valueOf(para.key_version));
			j_obj.put("cryplink_id", 	String.valueOf(para.cryplink_id));
			j_obj.put("pay_add", 		String.valueOf(para.pay_add));
			j_obj.put("tz_val", 		String.valueOf(para.tz_val));
			j_obj.put("power_relaf", 	String.valueOf(para.power_relaf));
			j_obj.put("power_rela1", 	String.valueOf(para.power_rela1));
			j_obj.put("power_rela2", 	String.valueOf(para.power_rela2));
			j_obj.put("power_relask1", 	String.valueOf(para.power_relask1));
			j_obj.put("power_relask2", 	String.valueOf(para.power_relask2));
			j_obj.put("fee_chgf", 		String.valueOf(para.fee_chgf));
			j_obj.put("fee_chgid", 		String.valueOf(para.fee_chgid));
			j_obj.put("fee_chgdate", 	String.valueOf(para.fee_chgdate));
			j_obj.put("fee_chgtime", 	String.valueOf(para.fee_chgtime));
			
			j_obj.put("cus_state", 	Rd.getDict(Dict.DICTITEM_YFFCUSSTATE, state.cus_state));
			j_obj.put("cus_state_id", 	state.cus_state);
			
			j_obj.put("op_type", 		String.valueOf(state.op_type));
			j_obj.put("op_date", 		String.valueOf(state.op_date));
			j_obj.put("op_time", 		String.valueOf(state.op_time));
			j_obj.put("pay_money", 		String.valueOf(state.pay_money));
			j_obj.put("othjs_money",	String.valueOf(state.othjs_money));
			j_obj.put("zb_money", 		String.valueOf(state.zb_money));
			j_obj.put("all_money", 		String.valueOf(state.all_money));
			j_obj.put("shutdown_val", 	String.valueOf(state.shutdown_val));
			
			//20140731
			j_obj.put("buy_dl", 	String.valueOf(state.buy_dl));
			j_obj.put("pay_bmc", 	String.valueOf(state.pay_bmc));
			//end

			String tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.jsbd_zyz[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("jsbd_zyz", 	String.valueOf(tmp));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.jsbd_zyj[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("jsbd_zyj", 	String.valueOf(tmp));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.jsbd_zyf[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("jsbd_zyf", 	String.valueOf(tmp));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.jsbd_zyp[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("jsbd_zyp", 	String.valueOf(tmp));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.jsbd_zyg[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("jsbd_zyg", 	String.valueOf(tmp));
			
			j_obj.put("jsbd_ymd", 	String.valueOf(state.jsbd_ymd));
			j_obj.put("buy_times", 	String.valueOf(state.buy_times));
			j_obj.put("calc_mdhmi", String.valueOf(state.calc_mdhmi));
			j_obj.put("calc_bdymd", CommFunc.FormatToYMD(state.calc_bdymd));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.calc_zyz[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("calc_zyz", 	String.valueOf(tmp));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.calc_zyj[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("calc_zyj", 	String.valueOf(tmp));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.calc_zyf[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("calc_zyf", 	String.valueOf(tmp));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.calc_zyp[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("calc_zyp", 	String.valueOf(tmp));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.calc_zyg[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("calc_zyg", 		String.valueOf(tmp));
			
			j_obj.put("now_remain", 	String.valueOf(state.now_remain));
			j_obj.put("now_remain2", 	String.valueOf(state.now_remain2));
			j_obj.put("bj_bd", 			String.valueOf(state.bj_bd));
			j_obj.put("tz_bd", 			String.valueOf(state.tz_bd));
			j_obj.put("cs_al1_state", 	String.valueOf(state.cs_al1_state));
			j_obj.put("cs_al2_state", 	String.valueOf(state.cs_al2_state));
			j_obj.put("cs_fhz_state", 	String.valueOf(state.cs_fhz_state));
			j_obj.put("al1_mdhmi", 		String.valueOf(state.al1_mdhmi));
			j_obj.put("al2_mdhmi", 		String.valueOf(state.al2_mdhmi));
			j_obj.put("fhz_mdhmi", 		String.valueOf(state.fhz_mdhmi));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.fz_zyz[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("fz_zyz", 	String.valueOf(tmp));
			j_obj.put("yc_flag1", 	String.valueOf(state.yc_flag1));
			j_obj.put("yc_flag2", 	String.valueOf(state.yc_flag2));
			j_obj.put("yc_flag3", 	String.valueOf(state.yc_flag3));
			j_obj.put("hb_date", 	String.valueOf(state.hb_date));
			j_obj.put("hb_time", 	String.valueOf(state.hb_time));
			j_obj.put("kh_date", 	String.valueOf(state.kh_date));
			j_obj.put("xh_date", 	String.valueOf(state.xh_date));
			j_obj.put("total_gdz", 	String.valueOf(state.total_gdz));
			j_obj.put("reserve1", 	String.valueOf(state.reserve1));
			
			//增加 阶梯累计用电量 jt_total_dl;低压 远程/卡式   费率方案为阶梯时 用到此方法    ylc
			j_obj.put("jt_total_dl",String.valueOf(state.jt_total_dl));
			
			
			//添加终端在线状态
			boolean onlineflag = false;
			OnlineRtu.RTUSTATE_ITEM rtustate_item = OnlineRtu.getOneRtuState(para.rtu_id);
			if ((rtustate_item != null) && (rtustate_item.comm_state == 1)) onlineflag = true;

			String t_str = onlineflag ? "在线" : "离线";
			j_obj.put("onlineflag", t_str);
			
			
			j_obj.put("qr_al1_1_state", 	String.valueOf(alarm.qr_al1_1_state));
			j_obj.put("qr_al1_2_state", 	String.valueOf(alarm.qr_al1_2_state));
			j_obj.put("qr_al1_3_state", 	String.valueOf(alarm.qr_al1_3_state));
			j_obj.put("qr_al2_1_state", 	String.valueOf(alarm.qr_al2_1_state));
			j_obj.put("qr_al2_2_state", 	String.valueOf(alarm.qr_al2_2_state));
			j_obj.put("qr_al2_3_state", 	String.valueOf(alarm.qr_al2_3_state));
			j_obj.put("qr_fhz_state", 		String.valueOf(alarm.qr_fhz_state));
			j_obj.put("qr_fhz_rf1_state", 	String.valueOf(alarm.qr_fhz_rf1_state));
			j_obj.put("qr_fhz_rf2_state", 	String.valueOf(alarm.qr_fhz_rf2_state));
			j_obj.put("qr_fz_times", 		String.valueOf(alarm.qr_fz_times));
			j_obj.put("qr_fz_rf1_times", 	String.valueOf(alarm.qr_fz_rf1_times));
			j_obj.put("qr_fz_rf2_times", 	String.valueOf(alarm.qr_fz_rf2_times));
			j_obj.put("qr_al1_1_mdhmi", 	String.valueOf(alarm.qr_al1_1_mdhmi));
			j_obj.put("qr_al1_2_mdhmi", 	String.valueOf(alarm.qr_al1_2_mdhmi));
			j_obj.put("qr_al1_3_mdhmi", 	String.valueOf(alarm.qr_al1_3_mdhmi));
			j_obj.put("qr_al2_1_mdhmi", 	String.valueOf(alarm.qr_al2_1_mdhmi));
			j_obj.put("qr_al2_2_mdhmi", 	String.valueOf(alarm.qr_al2_2_mdhmi));
			j_obj.put("qr_al2_3_mdhmi", 	String.valueOf(alarm.qr_al2_3_mdhmi));
			j_obj.put("qr_fhz_mdhmi", 		String.valueOf(alarm.qr_fhz_mdhmi));
			j_obj.put("qr_fhz_rf1_mdhmi", 	String.valueOf(alarm.qr_fhz_rf1_mdhmi));
			j_obj.put("qr_fhz_rf2_mdhmi", 	String.valueOf(alarm.qr_fhz_rf2_mdhmi));
			j_obj.put("cg_fhz_mdhmi", 		String.valueOf(alarm.cg_fhz_mdhmi));
			j_obj.put("cg_fhz_rf1_mdhmi", 	String.valueOf(alarm.cg_fhz_rf1_mdhmi));
			j_obj.put("cg_fhz_rf2_mdhmi", 	String.valueOf(alarm.cg_fhz_rf2_mdhmi));
			j_obj.put("qr_al1_1_uuid", 		String.valueOf(alarm.qr_al1_1_uuid));
			j_obj.put("qr_al2_1_uuid", 		String.valueOf(alarm.qr_al2_1_uuid));
			
			tmp = ComntFunc.byte2String(alarm.out_info);
			
			j_obj.put("tmp_al1_1_mdhmi", 	String.valueOf(commdata.tmp_al1_1_mdhmi));
			j_obj.put("tmp_al1_2_mdhmi", 	String.valueOf(commdata.tmp_al1_2_mdhmi));
			j_obj.put("tmp_al1_3_mdhmi", 	String.valueOf(commdata.tmp_al1_3_mdhmi));
			j_obj.put("tmp_al2_1_mdhmi", 	String.valueOf(commdata.tmp_al2_1_mdhmi));
			j_obj.put("tmp_al2_2_mdhmi", 	String.valueOf(commdata.tmp_al2_2_mdhmi));
			j_obj.put("tmp_al2_3_mdhmi", 	String.valueOf(commdata.tmp_al2_3_mdhmi));
			j_obj.put("tmp_fhz_mdhmi", 		String.valueOf(commdata.tmp_fhz_mdhmi));
			j_obj.put("tmp_fhz_rf1_mdhmi", 	String.valueOf(commdata.tmp_fhz_rf1_mdhmi));
			j_obj.put("tmp_fhz_rf2_mdhmi", 	String.valueOf(commdata.tmp_fhz_rf2_mdhmi));
			j_obj.put("tmp_prot_flag", 		Rd.getDict(Dict.DICTITEM_YESFLAG, commdata.tmp_prot_flag));
			j_obj.put("update_alarmstate", 	String.valueOf(commdata.update_alarmstate));
			j_obj.put("out_info", 	String.valueOf(tmp));
			
			return j_obj.toString();
		}
	}

	//高压操作-返回的预付费参数及状态
	public static class YFF_DATA_GYOPER_PARASTATE {
		
		public  YFFDef.YFF_ZJGPAYPARA		para	= new YFFDef.YFF_ZJGPAYPARA();
		public  YFFDef.YFF_ZJGPAYSTATE		state 	= new YFFDef.YFF_ZJGPAYSTATE();
		public  YFFDef.YFF_ZJGPAY_ALARMSTATE alarm	= new YFFDef.YFF_ZJGPAY_ALARMSTATE();
		public	YFFDef.YFF_ALARM_COMMDATA	commdata = new YFFDef.YFF_ALARM_COMMDATA();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += YFFDef.YFF_ZJGPAYPARA.writeStream(byte_vect, para);
			ret_len += YFFDef.YFF_ZJGPAYSTATE.writeStream(byte_vect, state);
			ret_len += YFFDef.YFF_ZJGPAY_ALARMSTATE.writeStream(byte_vect, alarm);
			ret_len += YFFDef.YFF_ALARM_COMMDATA.writeStream(byte_vect, commdata);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ret_len = YFFDef.YFF_ZJGPAYPARA.getDataSize(byte_vect, offset, ret_len, para);
			ret_len = YFFDef.YFF_ZJGPAYSTATE.getDataSize(byte_vect, offset, ret_len, state);
			ret_len = YFFDef.YFF_ZJGPAY_ALARMSTATE.getDataSize(byte_vect, offset, ret_len, alarm);
			ret_len = YFFDef.YFF_ALARM_COMMDATA.getDataSize(byte_vect, offset, ret_len, commdata);
			
			return ret_len;
		}

//		public void fromJsonString(String jstr) {
//			JSONObject j_obj	= JSONObject.fromObject(jstr);
//
//			para.rtu_id			= CommBase.strtoi(j_obj.getString("rtu_id"));
//			para.zjg_id			= (short)CommBase.strtoi(j_obj.getString("zjg_id"));
//			para.cacl_type		= (byte)CommBase.strtoi(j_obj.getString("cacl_type"));
//			para.feectrl_type 	= (byte)CommBase.strtoi(j_obj.getString("feectrl_type"));
//			para.pay_type 		= (byte)CommBase.strtoi(j_obj.getString("pay_type"));
//
//			para.yffctrl_type 	= (byte)CommBase.strtoi(j_obj.getString("yffctrl_type"));
//			
//			String tmp = j_obj.getString("feeproj_id");
//			String tmps[] = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				para.feeproj_id[i] = (short)CommBase.strtof(tmps[i]);
//			}
//			
//			para.yffalarm_id 	= (short)CommBase.strtoi(j_obj.getString("yffalarm_id"));
//			para.prot_st 		= (byte)CommBase.strtoi(j_obj.getString("prot_st"));
//			para.prot_ed 		= (byte)CommBase.strtoi(j_obj.getString("prot_ed"));
//			para.ngloprot_flag 	= (byte)CommBase.strtoi(j_obj.getString("ngloprot_flag"));
//			para.pay_add1 		= CommBase.strtof(j_obj.getString("pay_add1"));
//			para.pay_add2 		= CommBase.strtof(j_obj.getString("pay_add2"));
//			para.pay_add3 		= CommBase.strtof(j_obj.getString("pay_add3"));
//			
//			para.tz_val 		= CommBase.strtoi(j_obj.getString("tz_val"));
//			para.plus_time 		= (short)CommBase.strtoi(j_obj.getString("plus_time"));
//			para.use_gfh 		= (byte)CommBase.strtoi(j_obj.getString("use_gfh"));
//			para.hfh_time 		= (short)CommBase.strtoi(j_obj.getString("hfh_time"));
//			para.hfh_shutdown 	= CommBase.strtoi(j_obj.getString("hfh_shutdown"));
//			para.cs_stand 		= (byte)CommBase.strtoi(j_obj.getString("cs_stand"));
//			
//			para.fee_chgf 		= (byte)CommBase.strtoi(j_obj.getString("fee_chgf"));
//			
//			tmp = j_obj.getString("fee_chgid");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				para.fee_chgid[i] = (short)CommBase.strtof(tmps[i]);
//			}
//			
//			para.fee_chgdate 	= CommBase.strtoi(j_obj.getString("fee_chgdate"));
//			para.fee_chgtime 	= CommBase.strtoi(j_obj.getString("fee_chgtime"));
//			
//			para.jbf_chgval 	= CommBase.strtof(j_obj.getString("jbf_chgval"));
//			para.jbf_chgdate 	= CommBase.strtoi(j_obj.getString("jbf_chgdate"));
//			para.jbf_chgtime 	= CommBase.strtoi(j_obj.getString("jbf_chgtime"));
//			
//			state.rtu_id		= CommBase.strtoi(j_obj.getString("rtu_id"));
//			state.zjg_id		= (short)CommBase.strtoi(j_obj.getString("zjg_id"));
//			state.cus_state		= (byte)CommBase.strtoi(j_obj.getString("cus_state"));
//			state.op_type		= (byte)CommBase.strtoi(j_obj.getString("op_type"));
//			state.op_date		= CommBase.strtoi(j_obj.getString("op_date"));
//			state.op_time		= CommBase.strtoi(j_obj.getString("op_time"));
//			state.pay_money		= CommBase.strtof(j_obj.getString("pay_money"));
//			state.othjs_money	= CommBase.strtof(j_obj.getString("othjs_money"));
//			state.zb_money		= CommBase.strtof(j_obj.getString("zb_money"));
//			state.all_money		= CommBase.strtof(j_obj.getString("all_money"));
//			state.buy_dl		= CommBase.strtof(j_obj.getString("buy_dl"));
//			state.pay_bmc		= CommBase.strtof(j_obj.getString("pay_bmc"));
//			state.alarm_val1	= CommBase.strtof(j_obj.getString("alarm_val1"));
//			state.alarm_val2	= CommBase.strtof(j_obj.getString("alarm_val2"));
//			state.shutdown_val	= CommBase.strtof(j_obj.getString("shutdown_val"));
//			
//			tmp = j_obj.getString("jsbd_zyz");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.jsbd_zyz[i] = CommBase.strtof(tmps[i]);
//			}
//			tmp = j_obj.getString("jsbd_zyj");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.jsbd_zyj[i] = CommBase.strtof(tmps[i]);
//			}
//			tmp = j_obj.getString("jsbd_zyf");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.jsbd_zyf[i] = CommBase.strtof(tmps[i]);
//			}
//			tmp = j_obj.getString("jsbd_zyp");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.jsbd_zyp[i] = CommBase.strtof(tmps[i]);
//			}
//			tmp = j_obj.getString("jsbd_zyg");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.jsbd_zyg[i] = CommBase.strtof(tmps[i]);
//			}
//			
//			state.jsbd_ymd		= CommBase.strtoi(j_obj.getString("jsbd_ymd"));
//			state.lsttw_money	= CommBase.strtof(j_obj.getString("lsttw_money"));
//			state.nowtw_money	= CommBase.strtof(j_obj.getString("nowtw_money"));
//			state.buy_times		= CommBase.strtoi(j_obj.getString("buy_times"));
//			state.calc_mdhmi	= CommBase.strtoi(j_obj.getString("calc_mdhmi"));
//			state.calc_bdymd	= CommBase.strtoi(j_obj.getString("calc_bdymd"));	
//			
//			tmp = j_obj.getString("calc_zyz");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.calc_zyz[i] = CommBase.strtof(tmps[i]);
//			}
//			tmp = j_obj.getString("calc_zyj");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.calc_zyj[i] = CommBase.strtof(tmps[i]);
//			}
//			tmp = j_obj.getString("calc_zyf");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.calc_zyf[i] = CommBase.strtof(tmps[i]);
//			}
//			tmp = j_obj.getString("calc_zyp");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.calc_zyp[i] = CommBase.strtof(tmps[i]);
//			}
//			tmp = j_obj.getString("calc_zyg");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.calc_zyg[i] = CommBase.strtof(tmps[i]);
//			}
//
//			state.now_remain	= CommBase.strtof(j_obj.getString("now_remain"));
//			state.bj_bd			= CommBase.strtof(j_obj.getString("bj_bd"));
//			state.tz_bd			= CommBase.strtof(j_obj.getString("tz_bd"));
//			
//			state.cs_al1_state	= (byte)CommBase.strtoi(j_obj.getString("cs_al1_state"));
//			state.cs_al2_state	= (byte)CommBase.strtoi(j_obj.getString("cs_al2_state"));
//			state.cs_fhz_state	= (byte)CommBase.strtoi(j_obj.getString("cs_fhz_state"));
//			
//			state.al1_mdhmi		= CommBase.strtoi(j_obj.getString("al1_mdhmi"));
//			state.al2_mdhmi		= CommBase.strtoi(j_obj.getString("al2_mdhmi"));
//			state.fhz_mdhmi		= CommBase.strtoi(j_obj.getString("fhz_mdhmi"));
//			
//			tmp = j_obj.getString("fz_zyz");
//			tmps = tmp.split(",");
//			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
//				state.fz_zyz[i] = CommBase.strtof(tmps[i]);
//			}
//			
//			state.yc_flag1		= CommBase.strtoi(j_obj.getString("yc_flag1"));
//			state.yc_flag2		= CommBase.strtoi(j_obj.getString("yc_flag2"));
//			state.yc_flag3		= CommBase.strtoi(j_obj.getString("yc_flag3"));
//			state.hb_date		= CommBase.strtoi(j_obj.getString("hb_date"));
//			state.hb_time		= CommBase.strtoi(j_obj.getString("hb_time"));
//			state.kh_date		= CommBase.strtoi(j_obj.getString("kh_date"));
//			state.xh_date		= CommBase.strtoi(j_obj.getString("xh_date"));
//			state.total_gdz		= CommBase.strtof(j_obj.getString("total_gdz"));
//			
//			state.reserve1		= CommBase.strtoi(j_obj.getString("reserve1"));
//
//			alarm.rtu_id			= CommBase.strtoi(j_obj.getString("rtu_id"));
//			alarm.zjg_id			= (short)CommBase.strtoi(j_obj.getString("zjg_id"));
//			alarm.qr_al1_1_state	= (byte)CommBase.strtoi(j_obj.getString("qr_al1_1_state"));
//			alarm.qr_al1_2_state	= (byte)CommBase.strtoi(j_obj.getString("qr_al1_2_state"));
//			alarm.qr_al1_3_state	= (byte)CommBase.strtoi(j_obj.getString("qr_al1_3_state"));
//			alarm.qr_al2_1_state	= (byte)CommBase.strtoi(j_obj.getString("qr_al2_1_state"));
//			alarm.qr_al2_2_state	= (byte)CommBase.strtoi(j_obj.getString("qr_al2_2_state"));
//			alarm.qr_al2_3_state	= (byte)CommBase.strtoi(j_obj.getString("qr_al2_3_state"));
//			alarm.qr_fhz_state		= (byte)CommBase.strtoi(j_obj.getString("qr_fhz_state"));
//
//			alarm.qr_fz_times		= (byte)CommBase.strtoi(j_obj.getString("qr_fz_times"));
//			alarm.qr_al1_1_mdhmi	= CommBase.strtoi(j_obj.getString("qr_al1_1_mdhmi"));
//			alarm.qr_al1_2_mdhmi	= CommBase.strtoi(j_obj.getString("qr_al1_2_mdhmi"));
//			alarm.qr_al1_3_mdhmi	= CommBase.strtoi(j_obj.getString("qr_al1_3_mdhmi"));
//			alarm.qr_al2_1_mdhmi	= CommBase.strtoi(j_obj.getString("qr_al2_1_mdhmi"));
//			alarm.qr_al2_2_mdhmi	= CommBase.strtoi(j_obj.getString("qr_al2_2_mdhmi"));
//			alarm.qr_al2_3_mdhmi	= CommBase.strtoi(j_obj.getString("qr_al2_3_mdhmi"));
//			alarm.qr_fhz_mdhmi		= CommBase.strtoi(j_obj.getString("qr_fhz_mdhmi"));
//			alarm.cg_fhz_mdhmi		= CommBase.strtoi(j_obj.getString("cg_fhz_mdhmi"));
//			alarm.qr_al1_1_uuid		= CommBase.strtoi(j_obj.getString("qr_al1_1_uuid"));
//			alarm.qr_al2_1_uuid		= CommBase.strtoi(j_obj.getString("qr_al2_1_uuid"));
//			
//			tmp = j_obj.getString("out_info");
//			while(tmp.length() < alarm.out_info.length) {
//				tmp += " ";
//			}
//			for (int i = 0; i < alarm.out_info.length; i++) {
//				alarm.out_info[i] = (byte)tmp.charAt(i);
//			}
//			
//			commdata.tmp_al1_1_mdhmi	= CommBase.strtoi(j_obj.getString("tmp_al1_1_mdhmi"));
//			commdata.tmp_al1_2_mdhmi	= CommBase.strtoi(j_obj.getString("tmp_al1_2_mdhmi"));
//			commdata.tmp_al1_3_mdhmi	= CommBase.strtoi(j_obj.getString("tmp_al1_3_mdhmi"));
//			commdata.tmp_al2_1_mdhmi	= CommBase.strtoi(j_obj.getString("tmp_al2_1_mdhmi"));
//			commdata.tmp_al2_2_mdhmi	= CommBase.strtoi(j_obj.getString("tmp_al2_2_mdhmi"));
//			commdata.tmp_al2_3_mdhmi	= CommBase.strtoi(j_obj.getString("tmp_al2_3_mdhmi"));
//			commdata.tmp_fhz_mdhmi		= CommBase.strtoi(j_obj.getString("tmp_fhz_mdhmi"));
//			commdata.tmp_fhz_rf1_mdhmi	= CommBase.strtoi(j_obj.getString("tmp_fhz_rf1_mdhmi"));
//			commdata.tmp_fhz_rf2_mdhmi	= CommBase.strtoi(j_obj.getString("tmp_fhz_rf2_mdhmi"));
//			commdata.tmp_prot_flag		= (byte)CommBase.strtoi(j_obj.getString("tmp_prot_flag"));
//			commdata.update_alarmstate	= (byte)CommBase.strtoi(j_obj.getString("update_alarmstate"));
//		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();

			
			j_obj.put("rtu_id", 			String.valueOf(para.rtu_id));
			j_obj.put("zjg_id", 			String.valueOf(para.zjg_id));
			j_obj.put("cacl_type", 			String.valueOf(para.cacl_type));
			j_obj.put("cacl_type_desc", 	Rd.getDict(Dict.DICTITEM_FEETYPE, para.cacl_type));
			j_obj.put("feectrl_type", 		Rd.getDict(Dict.DICTITEM_PREPAYTYPE, para.feectrl_type));
			j_obj.put("pay_type", 			String.valueOf(para.pay_type));
			j_obj.put("pay_type_desc", 		Rd.getDict(Dict.DICTITEM_PAYTYPE, para.pay_type));
//卡号和卡表类型以后添加
			
			j_obj.put("yffctrl_type", 		String.valueOf(para.yffctrl_type));
//			j_obj.put("yffctrl_type_desc", 	Rd.getDict(Dict.DICTITEM_YFFMETERTYPE, para.yffmeter_type));

			//费率方案
			List<YffRatePara> yffrate = Rd.getYffRate();
			
			String tmp = "";
			for (int i = 0; i < ComntMsg.YFF_ZJGPAY_METERNUM; i++) {
				String ii = String.valueOf(i);
				if(i == 0){
					ii = "";
				}
				
				j_obj.put("feeproj_id" + ii, para.feeproj_id[i]);
				
				YffRatePara tmp_rate = Rd.getYffRate(yffrate, para.feeproj_id[i]);
				if(tmp_rate == null) continue;
				
				j_obj.put("feeproj_type" + ii, ((YffRatePara)tmp_rate).getFeeType());
				j_obj.put("feeproj_desc" + ii, ((YffRatePara)tmp_rate).getDescribe());
				j_obj.put("moneyLimit" + ii, ((YffRatePara)tmp_rate).getMoneyLimit());
				j_obj.put("feeproj_detail" + ii, Rd.getYffRateDesc((YffRatePara)tmp_rate));
				j_obj.put("feeproj_reteval" + ii, Rd.getYffRateVal((YffRatePara)tmp_rate));
				
			}
			
			//报警方案
			j_obj.put("yffalarm_id", para.yffalarm_id);
			
			Object obj = Rd.getRecord(YDTable.TABLECLASS_YFFALARMPARA, para.yffalarm_id);
			if(obj != null){
				j_obj.put("yffalarm_desc", 	 ((YffAlarmPara)obj).getDescribe());
				j_obj.put("yffalarm_alarm1", ((YffAlarmPara)obj).getAlarm1());
				j_obj.put("yffalarm_alarm2", ((YffAlarmPara)obj).getAlarm2());
				j_obj.put("type", 			 ((YffAlarmPara)obj).getType());
				j_obj.put("yffalarm_detail", Rd.getYffAlarmDesc((YffAlarmPara)obj));
			}else{
				j_obj.put("yffalarm_desc", 	 "");
				j_obj.put("yffalarm_alarm1", "");
				j_obj.put("yffalarm_alarm2", "");
				j_obj.put("type", 			 "");
				j_obj.put("yffalarm_detail", "");
			}
			
			j_obj.put("prot_st", 		String.valueOf(para.prot_st));
			j_obj.put("prot_ed", 		String.valueOf(para.prot_ed));
			j_obj.put("ngloprot_flag", 	String.valueOf(para.ngloprot_flag));
//			j_obj.put("key_version", 	String.valueOf(para.key_version));
//			j_obj.put("cryplink_id", 	String.valueOf(para.cryplink_id));
			
			j_obj.put("pay_add1", 		String.valueOf(para.pay_add1));
			j_obj.put("pay_add2", 		String.valueOf(para.pay_add2));
			j_obj.put("pay_add3", 		String.valueOf(para.pay_add3));
			j_obj.put("tz_val", 		String.valueOf(para.tz_val));
			j_obj.put("plus_time", 		String.valueOf(para.plus_time));
			j_obj.put("use_gfh", 		String.valueOf(para.use_gfh));
			j_obj.put("hfh_time", 		String.valueOf(para.hfh_time));
			j_obj.put("hfh_shutdown", 	String.valueOf(para.hfh_shutdown));
			j_obj.put("cs_stand", 		String.valueOf(para.cs_stand));
			j_obj.put("fee_chgf", 		String.valueOf(para.fee_chgf));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + para.fee_chgid[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("fee_chgid", tmp);

			j_obj.put("fee_chgdate", 	String.valueOf(para.fee_chgdate));
			j_obj.put("fee_chgtime", 	String.valueOf(para.fee_chgtime));
			
			j_obj.put("fee_chgf", 		String.valueOf(para.fee_chgf));
			j_obj.put("jbf_chgval", 	String.valueOf(para.jbf_chgval));
			j_obj.put("jbf_chgdate", 	String.valueOf(para.jbf_chgdate));
			j_obj.put("jbf_chgtime", 	String.valueOf(para.jbf_chgtime));
			
			j_obj.put("cus_state", 		Rd.getDict(Dict.DICTITEM_YFFCUSSTATE, state.cus_state));
			j_obj.put("cus_state_val", 	String.valueOf(state.cus_state));
			j_obj.put("op_type", 		String.valueOf(state.op_type));
			j_obj.put("op_date", 		String.valueOf(state.op_date));
			j_obj.put("op_time", 		String.valueOf(state.op_time));
			j_obj.put("pay_money", 		String.valueOf(state.pay_money));
			j_obj.put("othjs_money",	String.valueOf(state.othjs_money));
			j_obj.put("zb_money", 		String.valueOf(state.zb_money));
			j_obj.put("all_money", 		String.valueOf(state.all_money));
			j_obj.put("buy_dl", 		String.valueOf(state.buy_dl));
			j_obj.put("pay_bmc", 		String.valueOf(state.pay_bmc));
			j_obj.put("alarm_val1", 	String.valueOf(state.alarm_val1));
			j_obj.put("alarm_val2", 	String.valueOf(state.alarm_val2));
			j_obj.put("shutdown_val", 	String.valueOf(state.shutdown_val));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.jsbd_zyz[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("jsbd_zyz", 	String.valueOf(tmp));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.jsbd_zyj[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("jsbd_zyj", 	String.valueOf(tmp));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.jsbd_zyf[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("jsbd_zyf", 	String.valueOf(tmp));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.jsbd_zyp[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("jsbd_zyp", 	String.valueOf(tmp));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.jsbd_zyg[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("jsbd_zyg", 	String.valueOf(tmp));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.jsbd_zwz[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("jsbd_zwz", 	String.valueOf(tmp));
			
			j_obj.put("jsbd_ymd", 	String.valueOf(state.jsbd_ymd));
			j_obj.put("buy_times", 	String.valueOf(state.buy_times));
			j_obj.put("calc_mdhmi", String.valueOf(state.calc_mdhmi));
			j_obj.put("calc_bdymd", CommFunc.FormatToYMD(state.calc_bdymd));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.calc_zyz[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("calc_zyz", 	String.valueOf(tmp));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.calc_zyj[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("calc_zyj", 	String.valueOf(tmp));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.calc_zyf[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("calc_zyf", 	String.valueOf(tmp));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.calc_zyp[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("calc_zyp", 	String.valueOf(tmp));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.calc_zyg[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("calc_zyg", 	String.valueOf(tmp));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.calc_zwz[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("calc_zwz", 	String.valueOf(tmp));
			
			j_obj.put("now_remain", 	String.valueOf(state.now_remain));
			j_obj.put("bj_bd", 			String.valueOf(state.bj_bd));
			j_obj.put("tz_bd", 			String.valueOf(state.tz_bd));
			j_obj.put("cs_al1_state", 	String.valueOf(state.cs_al1_state));
			j_obj.put("cs_al2_state", 	String.valueOf(state.cs_al2_state));
			j_obj.put("cs_fhz_state", 	String.valueOf(state.cs_fhz_state));
			j_obj.put("al1_mdhmi", 		String.valueOf(state.al1_mdhmi));
			j_obj.put("al2_mdhmi", 		String.valueOf(state.al2_mdhmi));
			j_obj.put("fhz_mdhmi", 		String.valueOf(state.fhz_mdhmi));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.YFF_MPPAY_METERNUM; i++) {
				tmp += "," + state.fz_zyz[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("fz_zyz", 	String.valueOf(tmp));
			
			j_obj.put("yc_flag1", 	String.valueOf(state.yc_flag1));
			j_obj.put("yc_flag2", 	String.valueOf(state.yc_flag2));
			j_obj.put("yc_flag3", 	String.valueOf(state.yc_flag3));
			j_obj.put("hb_date", 	String.valueOf(state.hb_date));
			j_obj.put("hb_time", 	String.valueOf(state.hb_time));
			j_obj.put("kh_date", 	String.valueOf(state.kh_date));
			j_obj.put("xh_date", 	String.valueOf(state.xh_date));
			j_obj.put("total_gdz", 	String.valueOf(state.total_gdz));
			j_obj.put("reserve1", 	String.valueOf(state.reserve1));
			
			j_obj.put("qr_al1_1_state", 	String.valueOf(alarm.qr_al1_1_state));
			j_obj.put("qr_al1_2_state", 	String.valueOf(alarm.qr_al1_2_state));
			j_obj.put("qr_al1_3_state", 	String.valueOf(alarm.qr_al1_3_state));
			j_obj.put("qr_al2_1_state", 	String.valueOf(alarm.qr_al2_1_state));
			j_obj.put("qr_al2_2_state", 	String.valueOf(alarm.qr_al2_2_state));
			j_obj.put("qr_al2_3_state", 	String.valueOf(alarm.qr_al2_3_state));
			j_obj.put("qr_fhz_state", 		String.valueOf(alarm.qr_fhz_state));
			j_obj.put("qr_fz_times", 		String.valueOf(alarm.qr_fz_times));
			j_obj.put("qr_al1_1_mdhmi", 	String.valueOf(alarm.qr_al1_1_mdhmi));
			j_obj.put("qr_al1_2_mdhmi", 	String.valueOf(alarm.qr_al1_2_mdhmi));
			j_obj.put("qr_al1_3_mdhmi", 	String.valueOf(alarm.qr_al1_3_mdhmi));
			j_obj.put("qr_al2_1_mdhmi", 	String.valueOf(alarm.qr_al2_1_mdhmi));
			j_obj.put("qr_al2_2_mdhmi", 	String.valueOf(alarm.qr_al2_2_mdhmi));
			j_obj.put("qr_al2_3_mdhmi", 	String.valueOf(alarm.qr_al2_3_mdhmi));
			j_obj.put("qr_fhz_mdhmi", 		String.valueOf(alarm.qr_fhz_mdhmi));
			j_obj.put("cg_fhz_mdhmi", 		String.valueOf(alarm.cg_fhz_mdhmi));
			j_obj.put("qr_al1_1_uuid", 		String.valueOf(alarm.qr_al1_1_uuid));
			j_obj.put("qr_al2_1_uuid", 		String.valueOf(alarm.qr_al2_1_uuid));
			
			tmp = "";
			for (int i = 0; i < ComntMsg.MSG_STR_LEN_64; i++) {
				tmp += "," + alarm.out_info[i];
			}
			tmp = tmp.substring(1);
			j_obj.put("out_info", 	String.valueOf(tmp));
			
			j_obj.put("tmp_al1_1_mdhmi", 		String.valueOf(commdata.tmp_al1_1_mdhmi));
			j_obj.put("tmp_al1_2_mdhmi", 		String.valueOf(commdata.tmp_al1_2_mdhmi));
			j_obj.put("tmp_al1_3_mdhmi", 		String.valueOf(commdata.tmp_al1_3_mdhmi));
			
			j_obj.put("tmp_al2_1_mdhmi", 		String.valueOf(commdata.tmp_al2_1_mdhmi));
			j_obj.put("tmp_al2_2_mdhmi", 		String.valueOf(commdata.tmp_al2_2_mdhmi));
			j_obj.put("tmp_al2_3_mdhmi", 		String.valueOf(commdata.tmp_al2_3_mdhmi));
			
			j_obj.put("tmp_fhz_mdhmi", 			String.valueOf(commdata.tmp_fhz_mdhmi));
			j_obj.put("tmp_fhz_rf1_mdhmi", 		String.valueOf(commdata.tmp_fhz_rf1_mdhmi));
			j_obj.put("tmp_fhz_rf2_mdhmi", 		String.valueOf(commdata.tmp_fhz_rf2_mdhmi));
			
			j_obj.put("tmp_prot_flag", 			String.valueOf(commdata.tmp_prot_flag));
			j_obj.put("update_alarmstate", 		String.valueOf(commdata.update_alarmstate));
			
			return j_obj.toString();
		}
	}

	
	//返回余额
	public static class YFF_DATA_OPER_GETREMAIN {
		
		public	YFFDef.YFF_CALCREMAIN	remain = new YFFDef.YFF_CALCREMAIN();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += YFFDef.YFF_CALCREMAIN.writeStream(byte_vect, remain);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ret_len = YFFDef.YFF_CALCREMAIN.getDataSize(byte_vect, offset, ret_len, remain);

			return ret_len;
		}

//		public void fromJsonString(String jstr) {
//			JSONObject j_obj = JSONObject.fromObject(jstr);	
//
//			remain.remain_val 	= CommBase.strtof(j_obj.getString("remain_val"));
//			remain.reserve1 	= CommBase.strtof(j_obj.getString("reserve1"));
//			remain.reserve2 	= CommBase.strtof(j_obj.getString("reserve2"));
//			remain.reserve3 	= CommBase.strtof(j_obj.getString("reserve3"));
//		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("remain_val", String.valueOf(remain.remain_val));
			j_obj.put("reserve1", 	String.valueOf(remain.reserve1));
			j_obj.put("reserve2", 	String.valueOf(remain.reserve2));
			j_obj.put("reserve3", 	String.valueOf(remain.reserve3));
			
			return j_obj.toString();
		}
	}
	
	//返回结算补差余额
	public static class YFF_DATA_OPER_GETJSBCREMAIN {
		
		public	YFFDef.YFF_CALCREMAIN	remain = new YFFDef.YFF_CALCREMAIN();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += YFFDef.YFF_CALCREMAIN.writeStream(byte_vect, remain);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ret_len = YFFDef.YFF_CALCREMAIN.getDataSize(byte_vect, offset, ret_len, remain);

			return ret_len;
		}

//		public void fromJsonString(String jstr) {
//			JSONObject j_obj = JSONObject.fromObject(jstr);	
//
//			remain.remain_val 	= CommBase.strtof(j_obj.getString("remain_val"));
//			remain.reserve1 	= CommBase.strtof(j_obj.getString("reserve1"));
//			remain.reserve2 	= CommBase.strtof(j_obj.getString("reserve2"));
//			remain.reserve3 	= CommBase.strtof(j_obj.getString("reserve3"));
//		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("remain_val", String.valueOf(remain.remain_val));
			j_obj.put("reserve1", 	String.valueOf(remain.reserve1));
			j_obj.put("reserve2", 	String.valueOf(remain.reserve2));
			j_obj.put("reserve3", 	String.valueOf(remain.reserve3));
			
			return j_obj.toString();
		}
	}
	

	//暂停预付费报警及控制参数
	public static class YFF_DATA_OPER_PAUSEALARM {
		public int  mins;
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, mins);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			mins = ComntStream.readStream(byte_vect, offset + ret_len, mins);
			ret_len += ComntStream.getDataSize(mins);
			
			return ret_len;
		}

//		public void fromJsonString(String jstr) {
//			JSONObject j_obj = JSONObject.fromObject(jstr);	
//
//			mins 	= CommBase.strtoi(j_obj.getString("mins"));
//		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("mins", 	String.valueOf(mins));
			
			return j_obj.toString();
		}
	}

	
	//全局保电参数
	public static class YFF_DATA_OPER_GLOPROT {
		public byte		app_type;					//预付费应用类型
		
		public byte		use_flag;					//使用标志
		
		public int		bg_date;					//启用日期-yyyymmdd
		public int		bg_time;					//启用时间-hhmiss
		
		public int		ed_date;					//结束日期-yyyymmdd
		public int		ed_time;					//结束时间-hhmiss
		
		public byte[]	reserve1 = new byte[ComntDef.YD_64_STRLEN];	//扩展字段1
		public byte[]	reserve2 = new byte[ComntDef.YD_64_STRLEN];	//扩展字段2
		public byte[]	reserve3 = new byte[ComntDef.YD_64_STRLEN];	//扩展字段3
		public byte[]	reserve4 = new byte[ComntDef.YD_64_STRLEN];	//扩展字段4
		
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, app_type);
			ret_len += ComntStream.writeStream(byte_vect, use_flag);
			ret_len += ComntStream.writeStream(byte_vect, bg_date);
			ret_len += ComntStream.writeStream(byte_vect, bg_time);
			ret_len += ComntStream.writeStream(byte_vect, ed_date);
			ret_len += ComntStream.writeStream(byte_vect, ed_time);
			
			ret_len += ComntStream.writeStream(byte_vect, reserve1, 0, reserve1.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve1, 0, reserve2.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve1, 0, reserve3.length);
			ret_len += ComntStream.writeStream(byte_vect, reserve1, 0, reserve4.length);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			app_type = ComntStream.readStream(byte_vect, offset + ret_len, app_type);
			ret_len += ComntStream.getDataSize(app_type);
			
			use_flag = ComntStream.readStream(byte_vect, offset + ret_len, use_flag);
			ret_len += ComntStream.getDataSize(use_flag);
			
			bg_date = ComntStream.readStream(byte_vect, offset + ret_len, bg_date);
			ret_len += ComntStream.getDataSize(bg_date);
			
			bg_time = ComntStream.readStream(byte_vect, offset + ret_len, bg_time);
			ret_len += ComntStream.getDataSize(bg_time);
			
			ed_date = ComntStream.readStream(byte_vect, offset + ret_len, ed_date);
			ret_len += ComntStream.getDataSize(ed_date);
			
			ed_time = ComntStream.readStream(byte_vect, offset + ret_len, ed_time);
			ret_len += ComntStream.getDataSize(ed_time);

			ComntStream.readStream(byte_vect, offset + ret_len, reserve1, 0, reserve1.length);
			ret_len += ComntStream.getDataSize((byte)1) *  reserve1.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve2, 0, reserve2.length);
			ret_len += ComntStream.getDataSize((byte)1) *  reserve2.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve3, 0, reserve3.length);
			ret_len += ComntStream.getDataSize((byte)1) *  reserve3.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, reserve4, 0, reserve4.length);
			ret_len += ComntStream.getDataSize((byte)1) *  reserve4.length;
			
			return ret_len;
		}

		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("app_type", 	String.valueOf(app_type));
			j_obj.put("use_flag", 	String.valueOf(use_flag));
			j_obj.put("bg_date", 	CommFunc.FormatToYMD(bg_date));
			j_obj.put("bg_time", 	CommFunc.FormatToHMS(bg_time, 1));
			j_obj.put("ed_date", 	CommFunc.FormatToYMD(ed_date));
			j_obj.put("ed_time", 	CommFunc.FormatToHMS(ed_time, 1));
			
			j_obj.put("reserve1", 	ComntFunc.byte2String(reserve1));
			j_obj.put("reserve2", 	ComntFunc.byte2String(reserve2));
			j_obj.put("reserve3", 	ComntFunc.byte2String(reserve3));
			j_obj.put("reserve4", 	ComntFunc.byte2String(reserve4));
			
			return j_obj.toString();
		}
	}

	//农排操作-获得预付费参数及状态
	public static class YFF_DATA_NPOPER_PARASTATE {
		public	YFFDef.YFF_FARMERSTATE		state 	 = new YFFDef.YFF_FARMERSTATE();
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += YFFDef.YFF_FARMERSTATE.writeStream(byte_vect, state);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ret_len = YFFDef.YFF_FARMERSTATE.getDataSize(byte_vect, offset, ret_len, state);
			
			return ret_len;
		}

		public String toJsonString(com.kesd.dbpara.AreaPara area_para) {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("areaid", 		String.valueOf(state.areaid));
			j_obj.put("farmerid", 		String.valueOf(state.farmerid));
			j_obj.put("cus_state", 		Rd.getDict(Dict.DICTITEM_YFFCUSSTATE, state.cus_state));
			j_obj.put("cus_state_id", 	state.cus_state);
			
			j_obj.put("op_type", 		String.valueOf(state.op_type));
			j_obj.put("op_date", 		String.valueOf(state.op_date));
			j_obj.put("op_time", 		String.valueOf(state.op_time));
			
			j_obj.put("pay_money", 		String.valueOf(state.pay_money));
			j_obj.put("othjs_money",	String.valueOf(state.othjs_money));
			j_obj.put("zb_money", 		String.valueOf(state.zb_money));
			j_obj.put("all_money", 		String.valueOf(state.all_money));
			
			j_obj.put("ls_remain", 		String.valueOf(state.ls_remain));
			j_obj.put("cur_remain", 	String.valueOf(state.cur_remain));
			j_obj.put("total_gdz", 		String.valueOf(state.total_gdz));
			
			j_obj.put("buy_times", 		String.valueOf(state.buy_times));
			j_obj.put("totbuy_times", 	String.valueOf(state.totbuy_times));
			
			j_obj.put("alarm1", 		String.valueOf(state.alarm1));
			j_obj.put("alarm2", 		String.valueOf(state.alarm2));
			
			j_obj.put("kh_date", 		String.valueOf(state.kh_date));
			j_obj.put("xh_date", 		String.valueOf(state.xh_date));
			j_obj.put("js_bc_ymd", 		String.valueOf(state.js_bc_ymd));

			j_obj.put("moneyLimit", 	String.valueOf(WebConfig.npMoneyLimit));
			
			return j_obj.toString();
		}
	}
	

	//农灌表用户用电记录  CYFF_NP_YDJL
	public static class YFF_DATA_NPCOMM_YDJL {	
		public byte[]		kh	  = new byte[ComntDef.YD_32_STRLEN];	//卡号
		public int			bgymd = 0;		//开始用电日期
		public int			bghms = 0;		//开始用电时间
		public int			edymd = 0;		//结束用电日期
		public int			edhms = 0;		//结束用电时间

		public double		ydje  = 0;		//本次用电金额
		public double		syje  = 0;		//剩余金额
		public double		yddl  = 0;		//本次用电电量
		public double		gldl  = 0;		//过零电量
		public double		fee   = 0;		//单价
		public byte			state = 0;		//用电状态			0 正常 1 系统停电 2 无脉冲自动拉闸 3人为锁表
				
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ComntStream.readStream(byte_vect, offset + ret_len, kh, 0, kh.length);
			ret_len += ComntStream.getDataSize((byte)1) *  kh.length;
			
			bgymd = ComntStream.readStream(byte_vect, offset + ret_len, bgymd);
			ret_len += ComntStream.getDataSize(bgymd);
			
			bghms = ComntStream.readStream(byte_vect, offset + ret_len, bghms);
			ret_len += ComntStream.getDataSize(bghms);
			
			edymd = ComntStream.readStream(byte_vect, offset + ret_len, edymd);
			ret_len += ComntStream.getDataSize(edymd);
			
			edhms = ComntStream.readStream(byte_vect, offset + ret_len, edhms);
			ret_len += ComntStream.getDataSize(edhms);

			ydje = ComntStream.readStream(byte_vect, offset + ret_len, ydje);
			ret_len += ComntStream.getDataSize(ydje);
			
			syje = ComntStream.readStream(byte_vect, offset + ret_len, syje);
			ret_len += ComntStream.getDataSize(syje);
			
			yddl = ComntStream.readStream(byte_vect, offset + ret_len, yddl);
			ret_len += ComntStream.getDataSize(yddl);
			
			gldl = ComntStream.readStream(byte_vect, offset + ret_len, gldl);
			ret_len += ComntStream.getDataSize(gldl);
			
			fee = ComntStream.readStream(byte_vect, offset + ret_len, fee);
			ret_len += ComntStream.getDataSize(fee);
			
			state = ComntStream.readStream(byte_vect, offset + ret_len, state);
			ret_len += ComntStream.getDataSize(state);
			
			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("kh", 		ComntFunc.byte2String(kh));
			
			/*
			j_obj.put("bgymd", 		String.valueOf(bgymd));
			j_obj.put("bghms", 		String.valueOf(bghms));
			j_obj.put("edymd", 		String.valueOf(edymd));
			j_obj.put("edhms", 		String.valueOf(edhms));
			*/
			
			j_obj.put("bgtime", 	CommFunc.FormatToYMD(bgymd) + " " + CommFunc.FormatToHMS(bghms, 2));
			j_obj.put("endtime", 	CommFunc.FormatToYMD(edymd) + " " + CommFunc.FormatToHMS(edhms, 2));
			
			j_obj.put("ydje", 		String.format("%.4f", ydje));
			j_obj.put("syje", 		String.format("%.4f", syje));
			j_obj.put("yddl", 		String.format("%.4f", yddl));
			j_obj.put("gldl", 		String.format("%.4f", gldl));
			j_obj.put("fee", 		String.format("%.4f", fee));
			j_obj.put("state", 		Rd.getDict(Dict.DICTITEM_FARMER_STATE, state));
			
			return j_obj.toString();
		}
	}
	

	//存储表的总用电量和总故障电量  CYFF_NP_DLJL
	public static class YFF_DATA_NPCOMM_DLJL {	

		public double		tot_dl  = 0;	//总用电量		总用电量=正常用电量+总故障电量
		public double		err_dl  = 0;	//总故障电量
				
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			tot_dl = ComntStream.readStream(byte_vect, offset + ret_len, tot_dl);
			ret_len += ComntStream.getDataSize(tot_dl);
			
			err_dl = ComntStream.readStream(byte_vect, offset + ret_len, err_dl);
			ret_len += ComntStream.getDataSize(err_dl);
						
			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("tot_dl", 	String.valueOf(tot_dl));
			j_obj.put("err_dl", 	String.valueOf(err_dl));
			
			return j_obj.toString();
		}
	}
	
	//存储20条挂起用户记录  CYFF_NP_GQJL
	public static class YFF_DATA_NPCOMM_GQJL {	
		
		public byte[]		kh	  = new byte[ComntDef.YD_32_STRLEN];	//卡号
		public int			bgymd = 0;		//开始用电日期
		public int			bghms = 0;		//开始用电时间
		public int			edymd = 0;		//结束用电日期
		public int			edhms = 0;		//结束用电时间

		public double		ydje  = 0;		//本次用电金额
		public double		syje  = 0;		//剩余金额
		public double		yddl  = 0;		//本次用电电量
		public double		gldl  = 0;		//过零电量
		public double		fee   = 0;		//单价
		public byte			state = 0;		//用电状态
		public byte			reserve = 0;	//保留
				
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ComntStream.readStream(byte_vect, offset + ret_len, kh, 0, kh.length);
			ret_len += ComntStream.getDataSize((byte)1) *  kh.length;
			
			bgymd = ComntStream.readStream(byte_vect, offset + ret_len, bgymd);
			ret_len += ComntStream.getDataSize(bgymd);
			
			bghms = ComntStream.readStream(byte_vect, offset + ret_len, bghms);
			ret_len += ComntStream.getDataSize(bghms);
			
			edymd = ComntStream.readStream(byte_vect, offset + ret_len, edymd);
			ret_len += ComntStream.getDataSize(edymd);
			
			edhms = ComntStream.readStream(byte_vect, offset + ret_len, edhms);
			ret_len += ComntStream.getDataSize(edhms);

			ydje = ComntStream.readStream(byte_vect, offset + ret_len, ydje);
			ret_len += ComntStream.getDataSize(ydje);
			
			syje = ComntStream.readStream(byte_vect, offset + ret_len, syje);
			ret_len += ComntStream.getDataSize(syje);
			
			yddl = ComntStream.readStream(byte_vect, offset + ret_len, yddl);
			ret_len += ComntStream.getDataSize(yddl);
			
			gldl = ComntStream.readStream(byte_vect, offset + ret_len, gldl);
			ret_len += ComntStream.getDataSize(gldl);
			
			fee = ComntStream.readStream(byte_vect, offset + ret_len, fee);
			ret_len += ComntStream.getDataSize(fee);
			
			state = ComntStream.readStream(byte_vect, offset + ret_len, state);
			ret_len += ComntStream.getDataSize(state);
			
			reserve = ComntStream.readStream(byte_vect, offset + ret_len, state);
			ret_len += ComntStream.getDataSize(reserve);
			
			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("kh", 		ComntFunc.byte2String(kh));
			j_obj.put("bgtime", 	CommFunc.FormatToYMD(bgymd) + " " + CommFunc.FormatToHMS(bghms, 2));
			j_obj.put("endtime", 	CommFunc.FormatToYMD(edymd) + " " + CommFunc.FormatToHMS(edhms, 2));
			
			j_obj.put("ydje", 		String.valueOf(ydje));
			j_obj.put("syje", 		String.valueOf(syje));
			j_obj.put("yddl", 		String.valueOf(yddl));
			j_obj.put("gldl", 		String.valueOf(gldl));
			j_obj.put("fee", 		String.format("%.4f", fee));
			j_obj.put("state", 		Rd.getDict(Dict.DICTITEM_FARMER_STATE, state));
			j_obj.put("reserve", 	String.valueOf(reserve));
			
			return j_obj.toString();
		}
	}
	
	//存储最近12个月的结算电量  CYFF_NP_JSJL
	public static class YFF_DATA_NPCOMM_JSJL {		
		public int			yymm  = 0;	//时间月份
		public double		dl  = 0;	//用电量
				
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			yymm = ComntStream.readStream(byte_vect, offset + ret_len, yymm);
			ret_len += ComntStream.getDataSize(yymm);
			
			dl = ComntStream.readStream(byte_vect, offset + ret_len, dl);
			ret_len += ComntStream.getDataSize(dl);
						
			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("yymm", 	String.valueOf(yymm));
			j_obj.put("dl", 	String.valueOf(dl));
			
			return j_obj.toString();
		}
	}
	
	//存储最近50条参数设置记录  CYFF_NP_CSJL
	public static class YFF_DATA_NPCOMM_CSJL {		
		public byte[]		val1	  = new byte[ComntDef.YD_9_STRLEN];	//修改前值
		public byte[]		val2	  = new byte[ComntDef.YD_9_STRLEN];	//修改后值
		public int			ymd  = 0;	//开始日期
		public int			hms  = 0;	//开始日期
		public int			di   = 0;	//类别DI
				
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			ComntStream.readStream(byte_vect, offset + ret_len, val1, 0, val1.length);
			ret_len += ComntStream.getDataSize((byte)1) *  val1.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, val2, 0, val2.length);
			ret_len += ComntStream.getDataSize((byte)1) *  val2.length;
			
			ymd = ComntStream.readStream(byte_vect, offset + ret_len, ymd);
			ret_len += ComntStream.getDataSize(ymd);
			
			hms = ComntStream.readStream(byte_vect, offset + ret_len, hms);
			ret_len += ComntStream.getDataSize(hms);
			
			di = ComntStream.readStream(byte_vect, offset + ret_len, di);
			ret_len += ComntStream.getDataSize(di);
						
			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("val1", 	ComntFunc.byte2String(val1));
			j_obj.put("val2", 	ComntFunc.byte2String(val2));
			j_obj.put("ymd", 	String.valueOf(ymd));
			j_obj.put("hms", 	String.valueOf(hms));
			j_obj.put("di", 	String.valueOf("["+di+"]"+getSetType(Integer.parseInt(String.valueOf(di)))) );
			
			return j_obj.toString();
		}
	}
	
	public static String getSetType(int typeid){
		String typeName = "";
		switch(typeid){
			case 1: 
				typeName = "区域号";		break;
			case 2: 
				typeName = "电价";		break;
			case 3: 
				typeName = "倍率";		break;
			case 4: 
				typeName = "限定功率";	break;
			case 5: 
				typeName = "报警金额";	break;
			case 6: 
				typeName = "结算日期";	break;
			case 7: 
				typeName = "最大屯积金额(预留)";		break;
			case 8: 
				typeName = "无采样自动断电时间";		break;
			case 9: 
				typeName = "上电保护开关";		break;
		
		}
		
		return typeName;
		
	}
	
	//存储20条刷卡故障用户记录  CYFF_NP_GZJL
	public static class YFF_DATA_NPCOMM_GZJL {
		public byte[]		kh	  = new byte[ComntDef.YD_32_STRLEN];	//卡号
		public int			bgymd = 0;		//开始用电日期
		public int			bghms = 0;		//开始用电时间
		public int			edymd = 0;		//结束用电日期
		public int			edhms = 0;		//结束用电时间

		public double		syje  = 0;		//剩余金额
		public byte			cause = 0;		//故障原因

				
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ComntStream.readStream(byte_vect, offset + ret_len, kh, 0, kh.length);
			ret_len += ComntStream.getDataSize((byte)1) *  kh.length;
			
			bgymd = ComntStream.readStream(byte_vect, offset + ret_len, bgymd);
			ret_len += ComntStream.getDataSize(bgymd);
			
			bghms = ComntStream.readStream(byte_vect, offset + ret_len, bghms);
			ret_len += ComntStream.getDataSize(bghms);
			
			edymd = ComntStream.readStream(byte_vect, offset + ret_len, edymd);
			ret_len += ComntStream.getDataSize(edymd);
			
			edhms = ComntStream.readStream(byte_vect, offset + ret_len, edhms);
			ret_len += ComntStream.getDataSize(edhms);
			
			syje = ComntStream.readStream(byte_vect, offset + ret_len, syje);
			ret_len += ComntStream.getDataSize(syje);
					
			cause = ComntStream.readStream(byte_vect, offset + ret_len, cause);
			ret_len += ComntStream.getDataSize(cause);
			
			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("kh", 		ComntFunc.byte2String(kh));
			j_obj.put("bgtime", 	CommFunc.FormatToYMD(bgymd) + " " + CommFunc.FormatToHMS(bghms, 2));
			j_obj.put("endtime", 	CommFunc.FormatToYMD(edymd) + " " + CommFunc.FormatToHMS(edhms, 2));
			
			j_obj.put("syje", 		String.valueOf(syje));
			j_obj.put("cause", 		Rd.getDict(Dict.DICTITEM_FAULT_CAUSE, cause));
			
			return j_obj.toString();
		}
	}
	
	//召测费率电价
	public static class CALL_YFF_NP_FEE {
		public int fee;			//电价(0.01分)
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, fee);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			fee = ComntStream.readStream(byte_vect, offset + ret_len, fee);
			ret_len += ComntStream.getDataSize(fee);
			
			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			j_obj.put("val", 		String.valueOf(fee));
			return j_obj.toString();
		}
	}
	
	//召测区域号
	public static class CALL_YFF_NP_AREA {
		
		public byte[]		area	  = new byte[ComntDef.YD_32_STRLEN];	//新区域号

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			ret_len += ComntStream.writeStream(byte_vect, area, 0, area.length);
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ComntStream.readStream(byte_vect, offset + ret_len, area, 0, area.length);
			ret_len += ComntStream.getDataSize((byte)1) *  area.length;
			
			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			j_obj.put("val", 		ComntFunc.byte2String(area));
			return j_obj.toString();
		}
	}
	
	//召测变比
	public static class CALL_YFF_NP_RATE {
		public short rate;			//电价(0.01分)
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, rate);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			rate = ComntStream.readStream(byte_vect, offset + ret_len, rate);
			ret_len += ComntStream.getDataSize(rate);
			
			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			j_obj.put("val", 		String.valueOf(rate));
			return j_obj.toString();
		}
	}
	
	//召测报警金额
	public static class CALL_YFF_NP_ALARM {
		public int val;			//报警金额(分)
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, val);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			val = ComntStream.readStream(byte_vect, offset + ret_len, val);
			ret_len += ComntStream.getDataSize(val);
			
			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			j_obj.put("val", 		String.valueOf(val));
			return j_obj.toString();
		}
	}
	
	//召测限定功率
	public static class CALL_YFF_NP_POWLIMIT {
		public int val;			//限定功率(0.1W)

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, val);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			val = ComntStream.readStream(byte_vect, offset + ret_len, val);
			ret_len += ComntStream.getDataSize(val);
			
			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			j_obj.put("val", 		String.valueOf(val));
			return j_obj.toString();
		}
	}
	
	//召测无采样自动断电时间
	public static class CALL_YFF_NP_NOCYCUT {
		public short val;			//无采样自动断电时间(秒)

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, val);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			val = ComntStream.readStream(byte_vect, offset + ret_len, val);
			ret_len += ComntStream.getDataSize(val);
			
			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			j_obj.put("val", 		String.valueOf(val));
			return j_obj.toString();
		}
	}
	
	//召测电表锁定状态
	public static class CALL_YFF_NP_LOCK {
		public byte val;			//电表锁定状态

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, val);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			val = ComntStream.readStream(byte_vect, offset + ret_len, val);
			ret_len += ComntStream.getDataSize(val);
			
			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			j_obj.put("val", 		String.valueOf(val));
			return j_obj.toString();
		}
	}
	
	//存储用户的过零电量  CYFF_NP_GLJL
	public static class YFF_DATA_NPCOMM_GLJL {	
		public byte[]		kh	  = new byte[ComntDef.YD_32_STRLEN];	//卡号
		public double		gldl  = 0;		//过零电量


				
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ComntStream.readStream(byte_vect, offset + ret_len, kh, 0, kh.length);
			ret_len += ComntStream.getDataSize((byte)1) *  kh.length;
			
			gldl = ComntStream.readStream(byte_vect, offset + ret_len, gldl);
			ret_len += ComntStream.getDataSize(gldl);
			
			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("kh", 		ComntFunc.byte2String(kh));
			j_obj.put("gldl", 		String.valueOf(gldl));
			
			return j_obj.toString();
		}
	}
	
	//设置农排费率电价
	public static class YFF_NPSET_FEE {
		public byte	pwd[]  = new byte[ComntDef.YD_16_STRLEN];		//通讯密码
		public byte area[] = new byte[ComntDef.YD_32_STRLEN];		//区域号
		public int	fee;											//电价(0.01分)

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, pwd, 0, pwd.length);
			ret_len += ComntStream.writeStream(byte_vect, area, 0, area.length);
			ret_len += ComntStream.writeStream(byte_vect, fee);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ComntStream.readStream(byte_vect, offset + ret_len, pwd, 0, pwd.length);
			ret_len += ComntStream.getDataSize((byte)1) *  pwd.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, area, 0, area.length);
			ret_len += ComntStream.getDataSize((byte)1) *  area.length;
			
			fee = ComntStream.readStream(byte_vect, offset + ret_len, fee);
			ret_len += ComntStream.getDataSize(fee);
			
			return ret_len;
		}
	}
	
	//设置农排区域号
	public static class YFF_NPSET_AREA {
		public byte pwd[] = new byte[ComntDef.YD_16_STRLEN];			//通讯密码
		public byte area_old[] = new byte[ComntDef.YD_32_STRLEN];		//旧区域号
		public byte area_new[] = new byte[ComntDef.YD_32_STRLEN];		//新区域号

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, pwd, 0, pwd.length);
			ret_len += ComntStream.writeStream(byte_vect, area_old, 0, area_old.length);
			ret_len += ComntStream.writeStream(byte_vect, area_new, 0, area_new.length);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ComntStream.readStream(byte_vect, offset + ret_len, pwd, 0, pwd.length);
			ret_len += ComntStream.getDataSize((byte)1) *  pwd.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, area_old, 0, area_old.length);
			ret_len += ComntStream.getDataSize((byte)1) *  area_old.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, area_new, 0, area_new.length);
			ret_len += ComntStream.getDataSize((byte)1) *  area_new.length;
			
			return ret_len;
		}
	}
	
	//设置农排变比
	public static class YFF_NPSET_RATE {
		public byte	pwd[]  = new byte[ComntDef.YD_16_STRLEN];		//通讯密码
		public byte area[] = new byte[ComntDef.YD_32_STRLEN];		//区域号
		public int	rate;											//

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, pwd, 0, pwd.length);
			ret_len += ComntStream.writeStream(byte_vect, area, 0, area.length);
			ret_len += ComntStream.writeStream(byte_vect, rate);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ComntStream.readStream(byte_vect, offset + ret_len, pwd, 0, pwd.length);
			ret_len += ComntStream.getDataSize((byte)1) *  pwd.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, area, 0, area.length);
			ret_len += ComntStream.getDataSize((byte)1) *  area.length;
			
			rate = ComntStream.readStream(byte_vect, offset + ret_len, rate);
			ret_len += ComntStream.getDataSize(rate);
			
			return ret_len;
		}
	}
	
	//设置农排报警金额
	public static class YFF_NPSET_ALARM {
		public byte	pwd[]  = new byte[ComntDef.YD_16_STRLEN];		//通讯密码
		public byte area[] = new byte[ComntDef.YD_32_STRLEN];		//区域号
		public int	val;											//报警金额(分)

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, pwd, 0, pwd.length);
			ret_len += ComntStream.writeStream(byte_vect, area, 0, area.length);
			ret_len += ComntStream.writeStream(byte_vect, val);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ComntStream.readStream(byte_vect, offset + ret_len, pwd, 0, pwd.length);
			ret_len += ComntStream.getDataSize((byte)1) *  pwd.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, area, 0, area.length);
			ret_len += ComntStream.getDataSize((byte)1) *  area.length;
			
			val = ComntStream.readStream(byte_vect, offset + ret_len, val);
			ret_len += ComntStream.getDataSize(val);
			
			return ret_len;
		}
	}
	
	//设置农排限定功率
	public static class YFF_NPSET_POWLIMIT {
		public byte	pwd[]  = new byte[ComntDef.YD_16_STRLEN];		//通讯密码
		public byte area[] = new byte[ComntDef.YD_32_STRLEN];		//区域号
		public int	val;											//限定功率(0.1W)

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, pwd, 0, pwd.length);
			ret_len += ComntStream.writeStream(byte_vect, area, 0, area.length);
			ret_len += ComntStream.writeStream(byte_vect, val);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ComntStream.readStream(byte_vect, offset + ret_len, pwd, 0, pwd.length);
			ret_len += ComntStream.getDataSize((byte)1) *  pwd.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, area, 0, area.length);
			ret_len += ComntStream.getDataSize((byte)1) *  area.length;
			
			val = ComntStream.readStream(byte_vect, offset + ret_len, val);
			ret_len += ComntStream.getDataSize(val);
			
			return ret_len;
		}
	}
	
	//设置农排无采样自动断电时间
	public static class YFF_NPSET_NOCYCUT {
		public byte	pwd[]  = new byte[ComntDef.YD_16_STRLEN];		//通讯密码
		public byte area[] = new byte[ComntDef.YD_32_STRLEN];		//区域号
		public short val;											//限定功率(0.1W)

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, pwd, 0, pwd.length);
			ret_len += ComntStream.writeStream(byte_vect, area, 0, area.length);
			ret_len += ComntStream.writeStream(byte_vect, val);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ComntStream.readStream(byte_vect, offset + ret_len, pwd, 0, pwd.length);
			ret_len += ComntStream.getDataSize((byte)1) *  pwd.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, area, 0, area.length);
			ret_len += ComntStream.getDataSize((byte)1) *  area.length;
			
			val = ComntStream.readStream(byte_vect, offset + ret_len, val);
			ret_len += ComntStream.getDataSize(val);
			
			return ret_len;
		}
	}
	
	//设置农排无采样自动断电时间
	public static class YFF_NPSET_LOCK {
		public byte	pwd[]  = new byte[ComntDef.YD_16_STRLEN];		//通讯密码
		public byte area[] = new byte[ComntDef.YD_32_STRLEN];		//区域号
		public byte val;											//限定功率(0.1W)

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, pwd, 0, pwd.length);
			ret_len += ComntStream.writeStream(byte_vect, area, 0, area.length);
			ret_len += ComntStream.writeStream(byte_vect, val);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ComntStream.readStream(byte_vect, offset + ret_len, pwd, 0, pwd.length);
			ret_len += ComntStream.getDataSize((byte)1) *  pwd.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, area, 0, area.length);
			ret_len += ComntStream.getDataSize((byte)1) *  area.length;
			
			val = ComntStream.readStream(byte_vect, offset + ret_len, val);
			ret_len += ComntStream.getDataSize(val);
			
			return ret_len;
		}
		
	}
	
	//清20条挂起记录
	public static class YFF_NP_CLEAR_GQJL {
		public byte	pwd[]  = new byte[ComntDef.YD_16_STRLEN];		//通讯密码
		public byte area[] = new byte[ComntDef.YD_32_STRLEN];		//区域号

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, pwd, 0, pwd.length);
			ret_len += ComntStream.writeStream(byte_vect, area, 0, area.length);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ComntStream.readStream(byte_vect, offset + ret_len, pwd, 0, pwd.length);
			ret_len += ComntStream.getDataSize((byte)1) *  pwd.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, area, 0, area.length);
			ret_len += ComntStream.getDataSize((byte)1) *  area.length;
			
			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			j_obj.put("pwd", 		ComntFunc.byte2String(pwd));
			j_obj.put("area", 		ComntFunc.byte2String(area));
			return j_obj.toString();
		}
	}
	
	//设置黑名单启停标志-下行 CYFF_NP_BL_FLAG_D
	public static class YFF_DATA_NPCOMM_BL_FLAG_D {	
		public byte	pwd[]  	= new byte[ComntDef.YD_16_STRLEN];		//通讯密码
		public byte area[] 	= new byte[ComntDef.YD_32_STRLEN];		//区域号
		public byte	useflag	= 0;									//0:不使用	1:使用


		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, pwd, 0, pwd.length);
			ret_len += ComntStream.writeStream(byte_vect, area, 0, area.length);
			ret_len += ComntStream.writeStream(byte_vect, useflag);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ComntStream.readStream(byte_vect, offset + ret_len, pwd, 0, pwd.length);
			ret_len += ComntStream.getDataSize((byte)1) *  pwd.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, area, 0, area.length);
			ret_len += ComntStream.getDataSize((byte)1) *  area.length;
			
			useflag = ComntStream.readStream(byte_vect, offset + ret_len, useflag);
			ret_len += ComntStream.getDataSize(useflag);
			
			return ret_len;
		}
	}
	
	//添加删除黑名单-下行 CYFF_NP_BL_ITEM_D
	public static class YFF_DATA_NPCOMM_BL_ITEM_D {	
		public byte	pwd[]  	= new byte[ComntDef.YD_16_STRLEN];		//通讯密码
		public byte area[] 	= new byte[ComntDef.YD_32_STRLEN];		//区域号
		public byte	addflag	= 0;									//0:删除	1:增加
		public byte kh[] 	= new byte[ComntDef.YD_32_STRLEN];		//卡号
		public int	opymd	= 0;									//设置日期
		public int	ophms	= 0;									//设置时间	

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, pwd, 0, pwd.length);
			ret_len += ComntStream.writeStream(byte_vect, area, 0, area.length);
			ret_len += ComntStream.writeStream(byte_vect, addflag);
			ret_len += ComntStream.writeStream(byte_vect, kh, 0, kh.length);
			ret_len += ComntStream.writeStream(byte_vect, opymd);
			ret_len += ComntStream.writeStream(byte_vect, ophms);
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ComntStream.readStream(byte_vect, offset + ret_len, pwd, 0, pwd.length);
			ret_len += ComntStream.getDataSize((byte)1) *  pwd.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, area, 0, area.length);
			ret_len += ComntStream.getDataSize((byte)1) *  area.length;
			
			addflag = ComntStream.readStream(byte_vect, offset + ret_len, addflag);
			ret_len += ComntStream.getDataSize(addflag);
			
			ComntStream.readStream(byte_vect, offset + ret_len, kh, 0, kh.length);
			ret_len += ComntStream.getDataSize((byte)1) *  kh.length;
			
			opymd = ComntStream.readStream(byte_vect, offset + ret_len, opymd);
			ret_len += ComntStream.getDataSize(opymd);
			
			ophms = ComntStream.readStream(byte_vect, offset + ret_len, ophms);
			ret_len += ComntStream.getDataSize(ophms);
			
			return ret_len;
		}
	}
	
	//清除黑名单-下行 CYFF_NP_CLR_BL_D
	public static class YFF_DATA_NPCOMM_CLR_BL_D {	
		public byte	pwd[]  	= new byte[ComntDef.YD_16_STRLEN];		//通讯密码
		public byte area[] 	= new byte[ComntDef.YD_32_STRLEN];		//区域号	

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, pwd, 0, pwd.length);
			ret_len += ComntStream.writeStream(byte_vect, area, 0, area.length);
						
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ComntStream.readStream(byte_vect, offset + ret_len, pwd, 0, pwd.length);
			ret_len += ComntStream.getDataSize((byte)1) *  pwd.length;
			
			ComntStream.readStream(byte_vect, offset + ret_len, area, 0, area.length);
			ret_len += ComntStream.getDataSize((byte)1) *  area.length;
			
			return ret_len;
		}
	}
	
	//召测黑名单-上行 CYFF_NP_BL_ITEM_U
	public static class YFF_DATA_NPCOMM_BL_ITEM_U {	
		
		public byte kh[] 	= new byte[ComntDef.YD_32_STRLEN];		//卡号
		public int	opymd	= 0;									//设置日期
		public int	ophms	= 0;									//设置时间

		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, kh, 0, kh.length);
			ret_len += ComntStream.writeStream(byte_vect, opymd);
			ret_len += ComntStream.writeStream(byte_vect, ophms);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			ComntStream.readStream(byte_vect, offset + ret_len, kh, 0, kh.length);
			ret_len += ComntStream.getDataSize((byte)1) *  kh.length;
			
			opymd = ComntStream.readStream(byte_vect, offset + ret_len, opymd);
			ret_len += ComntStream.getDataSize(opymd);
			
			ophms = ComntStream.readStream(byte_vect, offset + ret_len, ophms);
			ret_len += ComntStream.getDataSize(ophms);
			
			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			j_obj.put("kh", 		ComntFunc.byte2String(kh));
			j_obj.put("opymd", 		CommFunc.FormatToYMD(opymd));
			j_obj.put("ophms", 		CommFunc.FormatToHMS(ophms,1));
			return j_obj.toString();
		}
	}
	
	//召测黑名单状态-上行 CYFF_NP_BL_FLAG_U
	public static class YFF_DATA_NPCOMM_BL_FLAG_U{	
		public byte	useflag	= 0;									//0:不使用	1:使用
		
		public int toDataStream(ComntVector.ByteVector byte_vect) {
			int ret_len   = 0;
			
			ret_len += ComntStream.writeStream(byte_vect, useflag);
			
			return ret_len;
		}
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;
			
			useflag = ComntStream.readStream(byte_vect, offset + ret_len, useflag);
			ret_len += ComntStream.getDataSize(useflag);
			
			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			
			j_obj.put("useflag", 		useflag);
			
			return j_obj.toString();
		}
	}
	
	
	
	//设置白名单启停标志-下行 CYFF_NP_WL_FLAG_D
	public static class YFF_DATA_NPCOMM_WL_FLAG_D extends YFF_DATA_NPCOMM_BL_FLAG_D {	
		
	}
	
	//添加删除白名单-下行 CYFF_NP_WL_ITEM_D
	public static class YFF_DATA_NPCOMM_WL_ITEM_D extends YFF_DATA_NPCOMM_BL_ITEM_D {	
		
	}

	//清除白名单 CYFF_NP_CLR_WL_D
	public static class YFF_DATA_NPCOMM_CLR_WL_D extends YFF_DATA_NPCOMM_CLR_BL_D {	
		
	}
	
	//召测白名单-上行 CYFF_NP_WL_ITEM_U
	public static class YFF_DATA_NPCOMM_WL_ITEM_U extends YFF_DATA_NPCOMM_BL_ITEM_U {	
		
	}	
	
	//召测白名单启停标志-上行 CYFF_NP_WL_FLAG_U
	public static class YFF_DATA_NPCOMM_WL_FLAG_U extends YFF_DATA_NPCOMM_BL_FLAG_U {	
		
	}
	
	
	//******************************************//
	//			河南清丰农排表通讯规约
	//				      开始
	//******************************************//

	//农灌表用户用电记录 
	public static class YFF_HNQF_NPCOMM_YDJL {
		class YFF_NPCOMM_YDJL {
			public byte[]		kh	  = new byte[ComntDef.YD_32_STRLEN];	//卡号
			public int			bgymd = 0;		//开始用电日期
			public int			bghms = 0;		//开始用电时间
			public int			edymd = 0;		//结束用电日期
			public int			edhms = 0;		//结束用电时间
	
			public double		ydje  = 0;		//本次用电金额
			public double		syje  = 0;		//剩余金额
		//	public double		yddl  = 0;		//本次用电电量
		//	public double		gldl  = 0;		//过零电量
		//	public double		fee   = 0;		//单价
			public byte			state = 0;		//用电状态			0 正常 1 系统停电 2 无脉冲自动拉闸 3人为锁表
		}
		public YFF_NPCOMM_YDJL[] comm_ydjl;
		
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			int vect_size = 0;
//			
			vect_size = ComntStream.readStream(byte_vect, offset + ret_len, vect_size);
			ret_len += ComntStream.getDataSize(vect_size);			
			
			if (vect_size <= 0) return ret_len;
			this.comm_ydjl = new YFF_NPCOMM_YDJL[vect_size]; 
			
			for (int i = 0;  i < vect_size; i++) {
				comm_ydjl[i] = new  YFF_NPCOMM_YDJL();
				ComntStream.readStream(byte_vect, offset + ret_len, comm_ydjl[i].kh, 0, comm_ydjl[i].kh.length);
				ret_len += ComntStream.getDataSize((byte)1) *  comm_ydjl[i].kh.length;
				
				comm_ydjl[i].bgymd = ComntStream.readStream(byte_vect, offset + ret_len, comm_ydjl[i].bgymd);
				ret_len += ComntStream.getDataSize(comm_ydjl[i].bgymd);
				
				comm_ydjl[i].bghms = ComntStream.readStream(byte_vect, offset + ret_len, comm_ydjl[i].bghms);
				ret_len += ComntStream.getDataSize(comm_ydjl[i].bghms);
				
				comm_ydjl[i].edymd = ComntStream.readStream(byte_vect, offset + ret_len, comm_ydjl[i].edymd);
				ret_len += ComntStream.getDataSize(comm_ydjl[i].edymd);
				
				comm_ydjl[i].edhms = ComntStream.readStream(byte_vect, offset + ret_len, comm_ydjl[i].edhms);
				ret_len += ComntStream.getDataSize(comm_ydjl[i].edhms);

				comm_ydjl[i].ydje = ComntStream.readStream(byte_vect, offset + ret_len, comm_ydjl[i].ydje);
				ret_len += ComntStream.getDataSize(comm_ydjl[i].ydje);
				
				comm_ydjl[i].syje = ComntStream.readStream(byte_vect, offset + ret_len, comm_ydjl[i].syje);
				ret_len += ComntStream.getDataSize(comm_ydjl[i].syje);
				
//				ComntStream.readStream(byte_vect, offset + ret_len, comm_ydjl[i].yddl);
//				ret_len += ComntStream.getDataSize(comm_ydjl[i].yddl);
				
//				gldl = ComntStream.readStream(byte_vect, offset + ret_len, gldl);
//				ret_len += ComntStream.getDataSize(gldl);
//				
//				fee = ComntStream.readStream(byte_vect, offset + ret_len, fee);
//				ret_len += ComntStream.getDataSize(fee);
				
				comm_ydjl[i].state = ComntStream.readStream(byte_vect, offset + ret_len, comm_ydjl[i].state);
				ret_len += ComntStream.getDataSize(comm_ydjl[i].state);
				
			}

			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			JSONObject j_obj1 = new JSONObject();
			JSONArray j_array1 = new JSONArray();
			//JSONArray j_array2 = new JSONArray();
			
			int nums = this.comm_ydjl.length;
			for(int i=0; i<nums; i++){
				j_obj.put("kh", 		ComntFunc.byte2String(comm_ydjl[i].kh));
				
				j_obj.put("bgtime", 	CommFunc.FormatToYMD(comm_ydjl[i].bgymd) + " " + CommFunc.FormatToHMS(comm_ydjl[i].bghms, 2));
				j_obj.put("endtime", 	CommFunc.FormatToYMD(comm_ydjl[i].edymd) + " " + CommFunc.FormatToHMS(comm_ydjl[i].edhms, 2));
				
				j_obj.put("ydje", 		String.format("%.4f", comm_ydjl[i].ydje));
				j_obj.put("syje", 		String.format("%.4f", comm_ydjl[i].syje));
				j_obj.put("yddl", 		String.format("%.4f", 0.0));
				j_obj.put("gldl", 		String.format("%.4f", 0.0));
				j_obj.put("fee", 		String.format("%.4f", 0.0));
				j_obj.put("state", 		Rd.getDict(Dict.DICTITEM_NPQFMETER_TYPE, comm_ydjl[i].state));
				//j_obj.put("state", 		Rd.getDict(Dict.DICTITEM_NPQFMETER_TYPE, 0));
				j_array1.add(j_obj);
			}
			j_obj1.put("data", j_array1);
			return j_obj1.toString();
		}
	}

	//存储20条挂起用户记录  CYFF_NP_GQJL
	public static class YFF_HNQF_NPCOMM_GQJL {	
		class YFF_NPCOMM_GQJL {
			public byte[]		kh	  = new byte[ComntDef.YD_32_STRLEN];	//卡号
			public int			bgymd = 0;		//开始用电日期
			public int			bghms = 0;		//开始用电时间
			public int			edymd = 0;		//结束用电日期
			public int			edhms = 0;		//结束用电时间
	
			public double		ydje  = 0;		//本次用电金额
			public double		syje  = 0;		//剩余金额
//			public double		yddl  = 0;		//本次用电电量
//			public double		gldl  = 0;		//过零电量
//			public double		fee   = 0;		//单价
			public byte			state = 0;		//用电状态
//			public byte			reserve = 0;	//保留
		}

		public YFF_NPCOMM_GQJL[] comm_gqjl;
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			int vect_size = 0;
//			
			vect_size = ComntStream.readStream(byte_vect, offset + ret_len, vect_size);
			ret_len += ComntStream.getDataSize(vect_size);			
			
			if (vect_size <= 0) return ret_len;
			this.comm_gqjl = new YFF_NPCOMM_GQJL[vect_size]; 
			
			for (int i = 0;  i < vect_size; i++) {
				comm_gqjl[i] = new  YFF_NPCOMM_GQJL();
				ComntStream.readStream(byte_vect, offset + ret_len, comm_gqjl[i].kh, 0, comm_gqjl[i].kh.length);
				ret_len += ComntStream.getDataSize((byte)1) *  comm_gqjl[i].kh.length;
				
				comm_gqjl[i].bgymd = ComntStream.readStream(byte_vect, offset + ret_len, comm_gqjl[i].bgymd);
				ret_len += ComntStream.getDataSize(comm_gqjl[i].bgymd);
				
				comm_gqjl[i].bghms = ComntStream.readStream(byte_vect, offset + ret_len, comm_gqjl[i].bghms);
				ret_len += ComntStream.getDataSize(comm_gqjl[i].bghms);
				
				comm_gqjl[i].edymd = ComntStream.readStream(byte_vect, offset + ret_len, comm_gqjl[i].edymd);
				ret_len += ComntStream.getDataSize(comm_gqjl[i].edymd);
				
				comm_gqjl[i].edhms = ComntStream.readStream(byte_vect, offset + ret_len, comm_gqjl[i].edhms);
				ret_len += ComntStream.getDataSize(comm_gqjl[i].edhms);

				comm_gqjl[i].ydje = ComntStream.readStream(byte_vect, offset + ret_len, comm_gqjl[i].ydje);
				ret_len += ComntStream.getDataSize(comm_gqjl[i].ydje);
				
				comm_gqjl[i].syje = ComntStream.readStream(byte_vect, offset + ret_len, comm_gqjl[i].syje);
				ret_len += ComntStream.getDataSize(comm_gqjl[i].syje);
				
//				ComntStream.readStream(byte_vect, offset + ret_len, comm_gqjl[i].yddl);
//				ret_len += ComntStream.getDataSize(comm_gqjl[i].yddl);
				
//				gldl = ComntStream.readStream(byte_vect, offset + ret_len, gldl);
//				ret_len += ComntStream.getDataSize(gldl);
//				
//				fee = ComntStream.readStream(byte_vect, offset + ret_len, fee);
//				ret_len += ComntStream.getDataSize(fee);
				
				comm_gqjl[i].state = ComntStream.readStream(byte_vect, offset + ret_len, comm_gqjl[i].state);
				ret_len += ComntStream.getDataSize(comm_gqjl[i].state);
				
			}

			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			JSONObject j_obj1 = new JSONObject();
			JSONArray j_array1 = new JSONArray();
			//JSONArray j_array2 = new JSONArray();
			
			int nums = this.comm_gqjl.length;
			for(int i=0; i<nums; i++){
				j_obj.put("kh", 		ComntFunc.byte2String(comm_gqjl[i].kh));
				
				j_obj.put("bgtime", 	CommFunc.FormatToYMD(comm_gqjl[i].bgymd) + " " + CommFunc.FormatToHMS(comm_gqjl[i].bghms, 2));
				j_obj.put("endtime", 	CommFunc.FormatToYMD(comm_gqjl[i].edymd) + " " + CommFunc.FormatToHMS(comm_gqjl[i].edhms, 2));
				
				j_obj.put("ydje", 		String.format("%.4f", comm_gqjl[i].ydje));
				j_obj.put("syje", 		String.format("%.4f", comm_gqjl[i].syje));
				j_obj.put("yddl", 		String.format("%.4f", 0.0));
				j_obj.put("gldl", 		String.format("%.4f", 0.0));
				j_obj.put("fee", 		String.format("%.4f", 0.0));
				j_obj.put("state", 		Rd.getDict(Dict.DICTITEM_FARMER_STATE, comm_gqjl[i].state));
				j_array1.add(j_obj);
			}
			j_obj1.put("data", j_array1);
			return j_obj1.toString();
		}
	}
	
	
	//存储20条刷卡故障用户记录
	public static class YFF_HNQF_NPCOMM_GZJL {

		class YFF_NPCOMM_GZJL {
			public byte[]		kh	  = new byte[ComntDef.YD_32_STRLEN];	//卡号
			
			public 	byte[]		rand1 = new byte[ComntDef.YD_32_STRLEN];	//随机数1
			public 	byte[]		tjxh  = new byte[ComntDef.YD_8_STRLEN];	//脱机交易序号
			public 	double		jsye;		//加锁余额
			public 	byte[]		rtu_rand = new byte[ComntDef.YD_32_STRLEN];	//终端随机数
			public  byte[]		rand2 = new byte[ComntDef.YD_32_STRLEN];	//随机数2
			public  byte[]		ljxh  = new byte[ComntDef.YD_8_STRLEN];	//联机交易序号
			public  double		jsje  = 0;		//解锁金额
			public  double		jyje  = 0;		//交易金额
			public  int			jyrq  = 0;		//交易日期
			public  int			jysj  = 0;		//交易时间
			public  double		syje  = 0;		//表内剩余金额
		}
		
		public YFF_NPCOMM_GZJL[] comm_gzjl;
				
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			int vect_size = 0;
//			
			vect_size = ComntStream.readStream(byte_vect, offset + ret_len, vect_size);
			ret_len += ComntStream.getDataSize(vect_size);			
			
			if (vect_size <= 0) return ret_len;
			this.comm_gzjl = new YFF_NPCOMM_GZJL[vect_size]; 
			
			for (int i = 0;  i < vect_size; i++) {
				comm_gzjl[i] = new  YFF_NPCOMM_GZJL();
				ComntStream.readStream(byte_vect, offset + ret_len, comm_gzjl[i].kh, 0, comm_gzjl[i].kh.length);
				ret_len += ComntStream.getDataSize((byte)1) *  comm_gzjl[i].kh.length;
				
				ComntStream.readStream(byte_vect, offset + ret_len, comm_gzjl[i].rand1, 0, comm_gzjl[i].rand1.length);
				ret_len += ComntStream.getDataSize((byte)1) *  comm_gzjl[i].rand1.length;
				
				ComntStream.readStream(byte_vect, offset + ret_len, comm_gzjl[i].tjxh, 0, comm_gzjl[i].tjxh.length);
				ret_len += ComntStream.getDataSize((byte)1) *  comm_gzjl[i].tjxh.length;
				
				comm_gzjl[i].jsye = ComntStream.readStream(byte_vect, offset + ret_len, comm_gzjl[i].jsye);
				ret_len += ComntStream.getDataSize(comm_gzjl[i].jsye);
				
				
				ComntStream.readStream(byte_vect, offset + ret_len, comm_gzjl[i].rtu_rand, 0, comm_gzjl[i].rtu_rand.length);
				ret_len += ComntStream.getDataSize((byte)1) *  comm_gzjl[i].rtu_rand.length;
				
				ComntStream.readStream(byte_vect, offset + ret_len, comm_gzjl[i].rand2, 0, comm_gzjl[i].rand2.length);
				ret_len += ComntStream.getDataSize((byte)1) *  comm_gzjl[i].rand2.length;
				
				ComntStream.readStream(byte_vect, offset + ret_len, comm_gzjl[i].ljxh, 0, comm_gzjl[i].ljxh.length);
				ret_len += ComntStream.getDataSize((byte)1) *  comm_gzjl[i].ljxh.length;

				comm_gzjl[i].jsje = ComntStream.readStream(byte_vect, offset + ret_len, comm_gzjl[i].jsje);
				ret_len += ComntStream.getDataSize(comm_gzjl[i].jsje);
				
				comm_gzjl[i].jyje = ComntStream.readStream(byte_vect, offset + ret_len, comm_gzjl[i].jyje);
				ret_len += ComntStream.getDataSize(comm_gzjl[i].jyje);
				
				comm_gzjl[i].jyrq = ComntStream.readStream(byte_vect, offset + ret_len, comm_gzjl[i].jyrq);
				ret_len += ComntStream.getDataSize(comm_gzjl[i].jyrq);

				comm_gzjl[i].jysj = ComntStream.readStream(byte_vect, offset + ret_len, comm_gzjl[i].jysj);
				ret_len += ComntStream.getDataSize(comm_gzjl[i].jysj);
				
				comm_gzjl[i].syje = ComntStream.readStream(byte_vect, offset + ret_len, comm_gzjl[i].syje);
				ret_len += ComntStream.getDataSize(comm_gzjl[i].syje);
			}

			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			JSONObject j_obj1 = new JSONObject();
			JSONArray j_array1 = new JSONArray();
			//JSONArray j_array2 = new JSONArray();
			
			int nums = this.comm_gzjl.length;
			for(int i=0; i<nums; i++){
				j_obj.put("kh", 		ComntFunc.byte2String(comm_gzjl[i].kh));
//				public 	byte[]		rand1 = new byte[ComntDef.YD_32_STRLEN];	//随机数1
//				public 	byte[]		tjxh  = new byte[ComntDef.YD_8_STRLEN];	//脱机交易序号
				j_obj.put("jsye", 		String.format("%.4f", comm_gzjl[i].jsye));
//				public 	byte[]		rtu_rand = new byte[ComntDef.YD_32_STRLEN];	//终端随机数
//				public  byte[]		rand2 = new byte[ComntDef.YD_32_STRLEN];	//随机数2
//				public  byte[]		ljxh  = new byte[ComntDef.YD_8_STRLEN];	//联机交易序号
				j_obj.put("jsje", 		String.format("%.4f", comm_gzjl[i].jsje));
				j_obj.put("jyje", 		String.format("%.4f", comm_gzjl[i].jyje));
				j_obj.put("jyrq", 	CommFunc.FormatToYMD(comm_gzjl[i].jyrq) + " " + CommFunc.FormatToHMS(comm_gzjl[i].jysj, 2));
				j_obj.put("syje", 		String.format("%.4f", comm_gzjl[i].syje));

				j_array1.add(j_obj);
			}
			j_obj1.put("data", j_array1);
			return j_obj1.toString();
		}
	}
	
	public static class YFF_HNQF_NPCOMM_CSJL {
		
		class YFF_NPCOMM_CSJL {
			public int			ymd  = 0;	//开始日期
			public int			hms  = 0;	//开始日期
			public byte			di   = 0;	//类别DI
			
			public byte[]		val1	  = new byte[ComntDef.YD_32_STRLEN];	//修改前值
			public byte[]		val2	  = new byte[ComntDef.YD_32_STRLEN];	//修改后值
			
		}
		public YFF_NPCOMM_CSJL[] comm_csjl;
		public int fromDataStream(ComntVector.ByteVector byte_vect, int offset) {
			int ret_len = 0;

			int vect_size = 0;
//			
			vect_size = ComntStream.readStream(byte_vect, offset + ret_len, vect_size);
			ret_len += ComntStream.getDataSize(vect_size);			
			
			if (vect_size <= 0) return ret_len;
			this.comm_csjl = new YFF_NPCOMM_CSJL[vect_size]; 
			
			for (int i = 0;  i < vect_size; i++) {
				comm_csjl[i] = new  YFF_NPCOMM_CSJL();
				
				comm_csjl[i].ymd = ComntStream.readStream(byte_vect, offset + ret_len, comm_csjl[i].ymd);
				ret_len += ComntStream.getDataSize(comm_csjl[i].ymd);
				
				comm_csjl[i].hms = ComntStream.readStream(byte_vect, offset + ret_len, comm_csjl[i].hms);
				ret_len += ComntStream.getDataSize(comm_csjl[i].hms);
				
				comm_csjl[i].di = ComntStream.readStream(byte_vect, offset + ret_len,  comm_csjl[i].di);
				ret_len += ComntStream.getDataSize(comm_csjl[i].di);
				
				ComntStream.readStream(byte_vect, offset + ret_len, comm_csjl[i].val1, 0, comm_csjl[i].val1.length);
				ret_len += ComntStream.getDataSize((byte)1) *  comm_csjl[i].val1.length;
				
				ComntStream.readStream(byte_vect, offset + ret_len, comm_csjl[i].val2, 0, comm_csjl[i].val2.length);
				ret_len += ComntStream.getDataSize((byte)1) *  comm_csjl[i].val2.length;
				
			}

			return ret_len;
		}
		
		public String toJsonString() {
			JSONObject j_obj = new JSONObject();
			JSONObject j_obj1 = new JSONObject();
			JSONArray j_array1 = new JSONArray();
		
			int nums = this.comm_csjl.length;
			for(int i=0; i<nums; i++){
				
				j_obj.put("bgtime", 	CommFunc.FormatToYMD(comm_csjl[i].ymd) + " " + CommFunc.FormatToHMS(comm_csjl[i].hms, 2));
				j_obj.put("di", 		comm_csjl[i].di);
				j_obj.put("val1", 		ComntFunc.byte2String(comm_csjl[i].val1));
				j_obj.put("val2", 		ComntFunc.byte2String(comm_csjl[i].val2));

				j_array1.add(j_obj);
			}
			
			j_obj1.put("data", j_array1);
			return j_obj1.toString();
		}
	}
	//******************************************//
	//			河南清丰农排表通讯规约
	//				     结束
	//******************************************//

}