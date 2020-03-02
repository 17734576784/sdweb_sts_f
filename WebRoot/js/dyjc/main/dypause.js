var rtnValue;
var rtnBD;
$(document).ready(function(){
	initGrid();//初始化grid
	
	$("#btnImportBD").click(function(){Import()});
	$("#btnSearch").click(function(){selcons()});
	$("#btnCompute").click(function(){compute()});
	$("#btnPause").click(function(){if(checkBDZ("")){pause()}});
	$("#btnPrint").click(function(){printZt()});
	setDisabled(true)
    $("#btnSearch").focus();	
});

function selcons(){//检索
	var rtnValue1 = doSearch("zz",ComntUseropDef.YFF_DYOPER_PAUSE,rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
	setDisabled(false)
	setConsValue(rtnValue);
	$("#syje").val(round(rtnValue.now_remain,def.point));
	
	getRecord($("#rtu_id").val(), $("#mp_id").val());//grid赋值
	$("#btnCompute").attr("disabled",true);
	$("#btnPause").attr("disabled",true);
	$("#btnPrint").attr("disabled",true);
}

function pause(){
	if(!check()) return;
	
	if(!confirm("确认要暂停吗？")){
		return;
	}
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	var params = {};
	params.rtu_id = rtu_id;
	
	params.date = rtnBD.date;
	params.time = 0;
		
    for(var i = 0; i < 3; i++){
		eval("params.mp_id"  + i + "=rtnBD.mp_id" + i);
		eval("params.bd_zy"  + i + "=rtnBD.zbd" + i);
		eval("params.bd_zyj" + i + "=rtnBD.jbd" + i);
		eval("params.bd_zyf" + i + "=rtnBD.fbd" + i);
		eval("params.bd_zyp" + i + "=rtnBD.pbd" + i);
		eval("params.bd_zyg" + i + "=rtnBD.gbd" + i);
	}
    params.pay_money	= $("#syje").val() * (-1);//这里四个值都是去负值
    params.othjs_money  = 0;
    params.zb_money     = 0;
    params.all_money    = params.pay_money;
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag	: true,
			userData1		: rtu_id,
			mpid			: mp_id,
			gsmflag			: 0,
			dyOperPause		: json_str,
			userData2		: ComntUseropDef.YFF_DYOPER_PAUSE
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				alert("暂停成功!");
				getRecord($("#rtu_id").val(), $("#mp_id").val());
				setDisabled(true);
				
				$("#btnPrint").attr("disabled",false);
				window.top.WebPrint.setYffDataOperIdx2params(data.dyOperPause,window.top.WebPrint.nodeIdx.dymain);//打印用的参数
			}
			else {
				alert("暂停失败!" + (data.operErr ? data.operErr : ''));
			}
		}
	);
}

function check() {
	if(rtnBD == null) {
		alert("请录入表底...");
		return false;
	}
	var syje = $("#syje").val();
	if(isNaN(syje)) {
		alert("请输入数字...");
		$("#syje").select().focus();
		return false;
	}
	
	return true;
}

function compute(){
	
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	var params = {};
	params.rtu_id = rtu_id;
	params.mp_id  = mp_id;
	
	params.date = rtnBD.date;
	params.time = 0;
	
	for(var i = 0; i < 3; i++){
		eval("params.mp_id"  + i + "=rtnBD.mp_id" + i);
		eval("params.bd_zy"  + i + "=rtnBD.zbd" + i);
		eval("params.bd_zyj" + i + "=rtnBD.jbd" + i);
		eval("params.bd_zyf" + i + "=rtnBD.fbd" + i);
		eval("params.bd_zyp" + i + "=rtnBD.pbd" + i);
		eval("params.bd_zyg" + i + "=rtnBD.gbd" + i);
	}
	
	var json_str = jsonString.json2String(params);
	$("#syje").val("");
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			userData1		: rtu_id,
			mpid			: mp_id,
			gsmflag			: 0,
			dyOperGetRemain	: json_str,
			userData2		: ComntUseropDef.YFF_DYOPER_GETREMAIN
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				var json = jsonString.string2Json(data.dyOperGetRemain);
				$("#syje").val(round(json.remain_val,def.point));
				$("#btnPause").attr("disabled",false);
			}
			else {
				alert("无法获取余额" + (data.operErr ? data.operErr : ''));
			}
		}
	);
}

function Import(type){
	var tmp = importBD($("#rtu_id").val(),$("#mp_id").val());
	if(!tmp)return;
	rtnBD = tmp;
	$("#td_bdinf").html(rtnBD.td_bdinf);
	$("#btnCompute").attr("disabled",false);
	$("#btnPause").attr("disabled",true);
}

function setDisabled(mode){
	if(!mode){
		$("#sfbd_sj").html("");
		$("#sfbd").html("");
		$("#td_bdinf").html("");
		$("#syje").val("");
		rtnBD = null;
	}
	$("#btnImportBD").attr("disabled",mode);
	$("#btnCompute").attr("disabled",mode);
	$("#btnPause").attr("disabled",mode);
	$("#syje").attr("disabled",mode);
}

function printZt() {
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.dymain);
	if(filename == undefined || filename == null){
		return;
	}
	window.top.WebPrint.prt_params.file_name = filename;
//	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.dyje;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}