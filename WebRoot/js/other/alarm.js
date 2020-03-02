
function show() {
	$("#pause_period").val("");
	
	var param = "{type:" + $("#appType").val() + "}";
	loading.loading();
	$.post( def.basePath + "ajaxoper/actSysCtrl!taskProc.action", 		//后台处理程序
		{
		userData2 		: ComntUseropDef.YFF_GET_PAUSEALARM,
		getPauseAlarm 	: param
		},
	function(data) {
		loading.loaded();
		if(data.result == "success") {
			var json = eval("(" + data.getPauseAlarm + ")");
			$("#pause_period").val(json.mins);
		}
	});
}

function set() {
	var mins = $("#pause_period").val();
	if($.trim(mins) == "") {
		alert("暂停时间不能为空!");
		return;
	}
	if(isNaN(mins)) {
		alert("请填写正整数!");
		return;
	}
	
	mins = parseInt(mins);
	
	var param = "{type:" + $("#appType").val() + ",mins:" + mins + "}";
	loading.loading();
	$.post( def.basePath + "ajaxoper/actSysCtrl!taskProc.action", 		//后台处理程序
		{
		userData2 		: ComntUseropDef.YFF_SET_PAUSEALARM,
		setpauseAlarm 	: param
		},
	function(data) {
		loading.loaded();
		if(data.result == "success") {
			alert("设置成功!");
		}
		else {
			alert("设置失败!");
		}
	});
}