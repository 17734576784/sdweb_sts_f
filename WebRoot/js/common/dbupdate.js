var dbUpdate = {
//	YD_DBUPDATE_TABLE_CHAN         : 0x00000001,	//通道参数表
//	YD_DBUPDATE_TABLE_RTU		   : 0x00000002,	//RTU参数表
//	YD_DBUPDATE_TABLE_RTUCOMM	   : 0x00000004,	//RTU通讯参数表
//	YD_DBUPDATE_TABLE_RTUEXT	   : 0x00000008,	//RTU扩展参数表
//	YD_DBUPDATE_TABLE_JLP		   : 0x00000010,	//计量点参数表
//	YD_DBUPDATE_TABLE_ZJG		   : 0x00000020,	//总加组参数表
//	YD_DBUPDATE_TABLE_METER		   : 0x00000040,	//电表参数表
//	YD_DBUPDATE_TABLE_PULSE		   : 0x00000080,	//脉冲量参数表
//	YD_DBUPDATE_TABLE_MNL		   : 0x00000100,	//模拟量参数表
//	YD_DBUPDATE_TABLE_TASKTMPL	   : 0x00000200,	//任务模板参数表
//	YD_DBUPDATE_TABLE_RTU_AUTOTASK : 0x00000400,	//终端自动任务参数表
//	YD_DBUPDATE_TABLE_FZCBTMPL	   : 0x00000800,	//冻结及抄表日模板参数表
//	YD_DBUPDATE_TABLE_CONS		   : 0x00001000,	//大客户参数表
//	YD_DBUPDATE_TABLE_RTUWH		   : 0x00002000,	//RTU维护参数表
//	YF_DBUPDATE_TABLE_EVENTPARA	   : 0x00004000,    //事项描述表
//	
//	YD_DBUPDATE_TABLE_RESIDENT	   : 0x00010000,	//居民用户参数表
//	YD_DBUPDATE_TABLE_RCPORT	   : 0x00020000,	//集抄端口通信参数表
//	YD_DBUPDATE_TABLE_MPPAY		   : 0x00040000,	//计量点预付费状态表	
//	YD_DBUPDATE_TABLE_ZJGPAY	   : 0x00080000,	//总加组预付费状态表
//	YD_DBUPDATE_TABLE_FEERATE	   : 0x00100000,	//费率档案表
//	YD_DBUPDATE_TABLE_YFFALARM	   : 0x00200000,	//报警方案档案表
//	YD_DBUPDATE_TABLE_YFFCTRL	   : 0x00400000,	//预付费控制表
//	YD_DBUPDATE_TABLE_GLOPROT	   : 0x00800000,	//节假日保电
//	
//	YD_DBUPDATE_TABLE_CALC	   	   : 0x01000000,	//后台计算参数表
//	YD_DBUPDATE_TABLE_ZF	   	   : 0x02000000,	//报警转发参数表
//	YD_DBUPDATE_TABLE_CSSTAND	   : 0x04000000,	//力调电费参数表
	
	YD_DBUPDATE_TABLE_CHAN					: 1,		//通道参数表
	YD_DBUPDATE_TABLE_RTU					: 2,		//RTU参数表
	YD_DBUPDATE_TABLE_RTUCOMM				: 3,		//RTU通讯参数表
	YD_DBUPDATE_TABLE_RTUEXT				: 4,		//RTU扩展参数表
	YD_DBUPDATE_TABLE_JLP					: 5,		//计量点参数表
	YD_DBUPDATE_TABLE_ZJG					: 6,		//总加组参数表
	YD_DBUPDATE_TABLE_METER					: 7,		//电表参数表
	YD_DBUPDATE_TABLE_PULSE					: 8,		//电表参数表
	YD_DBUPDATE_TABLE_MNL					: 9,		//模拟量参数表
	YD_DBUPDATE_TABLE_TASKTMPL				: 10,		//任务模板参数表
	YD_DBUPDATE_TABLE_RTU_AUTOTASK			: 11,		//终端自动任务参数表
	YD_DBUPDATE_TABLE_FZCBTMPL				: 12,		//冻结及抄表日模板参数表
	YD_DBUPDATE_TABLE_CONS					: 13,		//大客户参数表
	YD_DBUPDATE_TABLE_RTUWH					: 14,		//RTU维护参数表
	YF_DBUPDATE_TABLE_EVENTPARA				: 15,		//事项描述表

	YD_DBUPDATE_TABLE_RESIDENT				: 20,		//居民用户参数表
	YD_DBUPDATE_TABLE_RCPORT				: 21,		//集抄端口通信参数表
	YD_DBUPDATE_TABLE_MPPAY					: 22,		//计量点预付费状态表
	YD_DBUPDATE_TABLE_ZJGPAY				: 23,		//总加组预付费状态表
	YD_DBUPDATE_TABLE_FEERATE				: 24,		//费率档案表
	YD_DBUPDATE_TABLE_YFFALARM				: 25,		//报警方案档案表
	YD_DBUPDATE_TABLE_YFFCTRL				: 26,		//预付费控制表
	YD_DBUPDATE_TABLE_GLOPROT				: 27,		//节假日保电

	YD_DBUPDATE_TABLE_CALC					: 32,		//后台计算参数表
 	YD_DBUPDATE_TABLE_ZF					: 33,		//报警转发参数表
//20120723
 	YD_DBUPDATE_TABLE_CSSTAND				: 38,		//力调电费参数表

 	YD_DBUPDATE_TABLE_AREAPARA				: 41,		//农排片区参数表
 	YD_DBUPDATE_TABLE_FARMERPAY				: 42,		//农排用户预付费

	YD_DBUPDATE_TABLE_ALL					: 0xFFFFFFFF,		//所有参数
	
	sendDBUpdateMsg:function(update_mask) {

		$.post( def.basePath + "ajax/actCommon!dbUpdate.action", 		//后台处理程序
			  { value	 : update_mask  },
			function(data) {			    	//回传函数
				if(data.result == "success") {
					alert('更改通知信息已发出...');
				}
			}
		);
	}
}

