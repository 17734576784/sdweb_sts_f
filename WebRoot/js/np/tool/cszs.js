var rtnValue;
$(document).ready(function(){

	$("#btnSearch").focus().click(function(){selcons()});
	$("#btnCall").click(function(){callPara()});
	$("#btnSet").click(function(){setPara()});
});
function selcons(){//检索
	var rtnValue1 = doSearch("",ComntUseropDef.YFF_DYCOMM_SETPARA,rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
	$("#db_userno").html(rtnValue.userno);
	$("#username").html(rtnValue.username);
	$("#esamno").html(rtnValue.esamno);
	$("#db_pt_ratio").html(rtnValue.pt);
	$("#db_ct_ratio").html(rtnValue.ct);
	$("#db_alarm1").html(rtnValue.yffalarm_alarm1);
	$("#db_alarm2").html(rtnValue.yffalarm_alarm2);
	
	$("#feeproj_id").val(rtnValue.feeproj_id);
	$("#feeproj_desc").html(rtnValue.feeproj_desc);
	$("#feeproj_detail").html(rtnValue.feeproj_detail);
	
	$("#yffalarm_id").val(rtnValue.yffalarm_id);
	$("#yffalarm_desc").html(rtnValue.yffalarm_desc);
	$("#yffalarm_detail").html(rtnValue.yffalarm_detail);

	$("#rtuonline_img").attr("src",rtnValue.onlinesrc).attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
	
	$("#btnCall").attr("disabled",false);
	$("#btnSet").attr("disabled",false);
}

function setPara(){//设置参数
	
	if(rtnValue == undefined || rtnValue == null)return;
	var params = {};
	params.rtu_id = rtnValue.rtu_id;
	params.mp_id = rtnValue.mp_id;
	params.paratype = $("#op_type").val();
	params.user_type = 1;
	params.bit_update = 0;
	params.chg_date = 0;
	params.chg_time = 0;
	params.alarm_val1 = rtnValue.yffalarm_alarm1;
	params.alarm_val2 = rtnValue.yffalarm_alarm2;
	params.pt = rtnValue.pt;
	params.ct = rtnValue.ct;
	params.userno = rtnValue.userno;
	params.meterno = rtnValue.esamno;
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag 	: true,
			userData1		: params.rtu_id,
			mpid 			: params.mp_id,
			dyCommSetPara 	: json_str,
			userData2 		: ComntUseropDef.YFF_DYCOMM_SETPARA
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("设置参数成功!");
				}else{
					window.top.addStringTaskOpDetail("设置参数成功!");
				}
			}
			else {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("设置参数失败!");
				}else{
					window.top.addStringTaskOpDetail("设置参数失败!");
				}
			}
		}		
	);
	
}

function callPara(){//读表参数
	
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	var params = {};
	params.paratype = 1;
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag 	: true,	
			userData1		: rtnValue.rtu_id,
			mpid 			: rtnValue.mp_id,
			dyCommCallPara  : json_str,
			userData2 		: ComntUseropDef.YFF_DYCOMM_CALLPARA
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);			
			if (data.result == "success") {
				var json = eval("(" + data.dyCommCallPara + ")");
				$("#mp_alarm1").html(parseFloat(json.alarm_val1) /100);
				$("#mp_alarm2").html(parseFloat(json.alarm_val2) /100);
				$("#mp_pt_ratio").html(json.pt);
				$("#mp_ct_ratio").html(json.ct);
				$("#mp_userno").html(json.userno);
				
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("接收预付费服务任务成功.");
				}else{
					window.top.addStringTaskOpDetail("接收预付费服务任务成功...");
				}
			}else {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("接收预付费服务任务失败.");
				}else{
					window.top.addStringTaskOpDetail("接收预付费服务任务失败...");
				}
			}
		}
	);
	
}