var params 		= window.dialogArguments;

$(document).ready(function() {
	initText();
	if (params.prot_type == undefined) params.prot_type = ComntDef.YD_PROTOCAL_KEDYH;
	//科林大用户规约显示是否启用预付费，其他规约隐藏
	if (params.prot_type != ComntDef.YD_PROTOCAL_KEDYH){
		$("#yff_start1").hide();
		$("#start_yff_flag").hide();
	}
	$("#readRtuInfo").focus().click(function() {readRtuInfo()});
	$("#detail_info").click(function() {showOpDetailDialog()});
	//20131130增加取消任务操作
	$("#btnCancel").click(function(){cancelTask()});
});

function initText() {

	if (params.type == "bd") {
		$("#nowval_text").html("当前表底：");
		$("#totval_text").html("断电止码：");
		$("#alarm_val1_text").html("报警止码：");
		
	} else {
		$("#nowval_text").html("当前金额：");
		$("#totval_text").html("总金额：");
		$("#alarm_val1_text").html("报警金额：");
		$("#remain_info").hide();//金额类型，则隐藏剩余信息
	}
	$("#start_yff_flag").attr("disabled", true);	//是否启用预付费不可修改
	
	$("#userno").html(params.userno);
	$("#username").html(params.username);
	$("#cacl_type_desc").html(params.cacl_type_desc);
	$("#buy_times").html(params.buytimes);
	
	$(".tabinfo").css("display","block");
}

function readRtuInfo(){
	
	var param = {};
	param.rtu_id 	= params.rtuid;
	param.zjg_id 	= params.zjgid;
	param.paratype	= ComntProtMsg.YFF_GY_CALL_PARAREMAIN;
	
	var json_str = jsonString.json2String(param);
	//增加取消任务操作,loading界面范围适当改变
	loading.loading_dialog();
	//禁用读取按钮
	$("#readRtuInfo").attr("disabled",true);
	addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommGy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag 	: true,
			userData1		: param.rtu_id,
			zjgid			: param.zjg_id,
			gyCommCallPara	: json_str,
			userData2 		: ComntUseropDef.YFF_GYCOMM_CALLPARA
		},
		function(data) {//回传函数
			loading.loaded();
			addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				addStringTaskOpDetail("接收预付费服务任务成功...");
				var json = eval('(' + data.gyCommCallPara + ')');
				setValue(json);
				if (params.prot_type == ComntDef.YD_PROTOCAL_FKGD05 || params.prot_type == ComntDef.YD_PROTOCAL_GD376 
						|| params.prot_type == ComntDef.YD_PROTOCAL_GD376_2013 ) {
					
					param.mpid 		= 1;
					param.ymd 		= 0;
					param.datatype	= ComntProtMsg.YFF_CALL_GY_REMAIN;
					
					json_str = jsonString.json2String(param);
					loading.loading_dialog();
					addStringTaskOpDetail("正在向预付费服务发送信息...");
					$.post( def.basePath + "ajaxoper/actCommGy!taskProc.action", 		//后台处理程序
						{
							firstLastFlag 	: true,
							userData1		: param.rtu_id,
							zjgid			: param.zjg_id,
							gyCommReadData	: json_str,
							userData2 		: ComntUseropDef.YFF_READDATA
						},
						function(data) {//回传函数
							
							loading.loaded();
							addJsonOpDetail(data.detailInfo);
							if (data.result == "success") {
								addStringTaskOpDetail("接收预付费服务任务成功...");
								var json = eval('(' + data.gyCommReadData + ')');
								$("#nowval").html(json.cur_val);
								window.returnValue.nowval = json.cur_val;
								$("#cls_window").focus();
							}
					});
				}
			}else{
				addStringTaskOpDetail("通讯失败.");
				alert("通讯失败..");
			}
			//重新启用读取按钮
			$("#readRtuInfo").attr("disabled",false);
		});
}

function setValue(json){
	$("#nowval").html(json.nowval);
	$("#totval").html(json.totval);
	$("#alarm_val1").html(json.alarm_val1);
	
	$("#start_yff_flag").attr('checked', json.yff_flag == 1 ? true : false);
	
	setRemainDL_JE(json);
}

function setRemainDL_JE(json){
	if(params.type == "bd"){
		var bmc		 = Number(json.totval) - Number(json.nowval);
		var remainDl = bmc*params.blv;
		$("#remain_dl").html(round(remainDl, def.point));
		
		var remain_Je = remainDl*params.dj;
		$("#remain_je").html(round(remain_Je, def.point));
	}
	
	window.returnValue = {
		nowval:json.nowval,
		totval:json.totval,
		alarm_val1 : $("#alarm_val1").html(),
		remain_dl:$("#remain_dl").html(),
		remain_je:$("#remain_je").html()
	};
	
	$("#cls_window").focus();
}

//取消操作
function cancelTask(){
	cancelTasks(ComntUseropDef.YFF_GYCOMM_CALLPARA);
	cancelTasks(ComntUseropDef.YFF_READDATA);
}

function cancelTasks(cancel_oper){
	$.post(def.basePath + "ajaxoper/actCommGy!cancelTask.action", {
		userData1 : params.rtuid,
		userData2 : cancel_oper
	});
}