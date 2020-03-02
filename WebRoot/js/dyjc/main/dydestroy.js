var rtnValue;
var rtnBD;
$(document).ready(function(){
	initGrid();//setCons.js 中函数 初始化grid
	$("#btnImportBD").click(function(){Import()});
	$("#btnSearch  ").click(function(){selcons()});
	$("#btnDestroy ").click(function(){consDestroy();});
	$("#btnCompute ").click(function(){compute()});
	
	$("#btnPrt").click(function(){printPayRec()});
	
	setDisabled(true);
	$("#btnSearch").focus();
});

function selcons(){//检索
	var rtnValue1 = doSearch("zz",ComntUseropDef.YFF_DYOPER_DESTORY, rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
	setDisabled(false);
	
	setConsValue(rtnValue);
	
	//剩余金额
	$("#syje").val(rtnValue.now_remain);
	getRecord(rtnValue.rtu_id, rtnValue.mp_id);//setCons.js 中函数 查找缴费记录
	
	$("#btnPrt").attr("disabled",true);
	$("#btnCompute").attr("disabled",true);
	$("#btnDestroy").attr("disabled",true);
}
function consDestroy(){//销户
	
	if(!check()) return;
	
	if(!confirm("确定要销户吗？")){
		return;
	}
	
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	
	var params = {};
	params.rtu_id = rtu_id;
	params.mp_id = mp_id;
	params.pay_money = $("#syje").val() === "" ? 0 : ($("#syje").val() * (-1));
	params.othjs_money = 0;
	params.zb_money = 0;
	params.all_money = params.pay_money;
	
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
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			userData1		: rtu_id,
			mpid			: mp_id,
			gsmflag			: 0,
			dyOperDestory	: json_str,
			userData2		: ComntUseropDef.YFF_DYOPER_DESTORY
		},
		function(data) {//回传函数
			loading.loaded();
			if (data.result == "success") {
				alert("销户成功!");
				getRecord(rtnValue.rtu_id, rtnValue.mp_id);//销户成功之后更新以下购电记录
				setDisabled(true);
				$("#btnDestroy").attr("disabled",true);
				$("#btnCompute").attr("disabled",true);
				$("#btnPrt").attr("disabled",false);
				
				window.top.WebPrint.setYffDataOperIdx2params(data.dyOperDestory,window.top.WebPrint.nodeIdx.dymain);//打印用的参数
			}
			else {
				alert("销户失败!" + (data.operErr ? data.operErr : ''));
			}
			loading.loaded();
		}
	);
}

function check() {
	if (rtnBD == null){
		alert("请录入表底!");
		return false;
	}
	if(!isDbl("syje", "剩余金额",-rtnValue.moneyLimit, rtnValue.moneyLimit)){
		return false;
	}
	return true;
}

function compute(){//计算剩余金额
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	var params = {};
	params.rtu_id = rtu_id;
	params.mp_id  = mp_id;
	params.date = rtnBD.date;
	params.time = 0
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
			if (data.result == "success") {
				var json = jsonString.string2Json(data.dyOperGetRemain);
				$("#syje").val(round(json.remain_val,def.point));
				$("#btnDestroy").attr("disabled",false);
			}
			else {
				alert("获取余额失败!" +  (data.operErr ? data.operErr : ''));
			}
			loading.loaded();
		}
	);
}

function Import(){//录入
	var tmp = importBD($("#rtu_id").val(),$("#mp_id").val());
	if(!tmp)return;
	rtnBD = tmp;
	$("#td_bdinf").html(rtnBD.td_bdinf);
	
	$("#btnCompute").attr("disabled" , false);
}

function setDisabled(mode){
	if(!mode){
		$("#syje").val("");
		$("#sfbd_sj").html("");
		$("#sfbd").html("");
		$("#td_bdinf").html("");
		rtnBD = null;
	}
	
	$("#syje").attr("disabled",mode);
	$("#btnImportBD  ").attr("disabled",mode);
}

function printPayRec() {
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.dymain);
	if(filename == undefined || filename == null){
		return;
	}
	window.top.WebPrint.prt_params.file_name = filename;
//	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.dyje;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}