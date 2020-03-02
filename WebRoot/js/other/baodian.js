
function show() {
	$("#chkUseflag").attr("checked", false);
	$("#sdate").val("");
	$("#edate").val("");
	
	var param = "{type:" + $("#appType").val() + "}";
	loading.loading();
	$.post( def.basePath + "ajaxoper/actSysCtrl!taskProc.action", 		//后台处理程序
		{
		userData2 	: ComntUseropDef.YFF_GET_GLOPROT,
		getGloProt 	: param
		},
	function(data) {
		loading.loaded();
		if(data.result == "success") {
			
			var json = eval("(" + data.getGloProt + ")");
			
			$("#chkUseflag").attr("checked", json.use_flag == "1" ? true : false);
			$("#sdate").val(json.bg_date + json.bg_time);
			$("#edate").val(json.ed_date + json.ed_time);
		}
	});
}

function set() {
	var sdate = $("#sdate").val();
	var edate = $("#edate").val();
	if($.trim(sdate) == "" || $.trim(edate) == "") {
		alert("请填写完整!");
		return;
	}
	var useflag = $("#chkUseflag").attr("checked") ? 1 : 0;
	var bg_date = sdate.substring(0, 4) + sdate.substring(5, 7) + sdate.substring(8, 10);
	var bg_time = sdate.substring(11, 13) + sdate.substring(14, 16) + "00";
	var ed_date = edate.substring(0, 4) + edate.substring(5, 7) + edate.substring(8, 10);
	var ed_time = edate.substring(11, 13) + edate.substring(14, 16) + "00";
	
	if(parseInt(bg_date + bg_time) > parseInt(ed_date + ed_time)){
		alert("开始时间不能大于结束时间!");
		return;
	}
	
	var param = {};
	param.type = $("#appType").val();
	param.app_type = param.type;
	param.use_flag = useflag;
	param.bg_date = bg_date;
	param.bg_time = bg_time;
	param.ed_date = ed_date;
	param.ed_time = ed_time;
	
	loading.loading();
	$.post( def.basePath + "ajaxoper/actSysCtrl!taskProc.action", 		//后台处理程序
		{
		userData2 	: ComntUseropDef.YFF_SET_GLOPROT,
		setGloProt 	: jsonString.json2String(param)
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