var rtnValue;
var	mygrid;
var rtnBD = null;

$(document).ready(function(){
	
	$("#btnSearch").click(function(){selcons()});
	
	$("#btnDestory").click(function(){if(check())logoff()});
	
	$("#rtuInfo").click(function(){rtuInfo()});
	
	$("#btnPrt").click(function(){showPrint()});
	
	$("#btnImportBD").click(function(){Import()});
	$("#now_remain").attr("disabled",true);
	initGrid();
});

function Import(){

	var tmp = importBD($("#rtu_id").val(),$("#zjg_id").val());
	if(!tmp){
		return;
	}
	
	if(tmp.td_bdinf == ""){
		$("#td_bdinf").html("未录入表底信息");
	}else{
		rtnBD = tmp;
		$("#td_bdinf").html(rtnBD.td_bdinf);
	}
}

function selcons(){//检索
	$("#btnDestory").attr("disabled",true);
	$("#td_bdinf").html("");
	rtnBD = null;
	var rtnValue1 = doSearch("je",ComntUseropDef.YFF_GYOPER_DESTORY,rtnValue);
	if(!rtnValue1){
		return;
	}
	
	rtnValue = rtnValue1;
	
	setConsValue(rtnValue);
	getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id); 

	$("#btnImportBD").attr("disabled",false);
	$("#rtuInfo").attr("disabled",false);
	$("#btnPrt").attr("disabled",true);
	
	$("#now_remain").attr("disabled",false);
	$("#now_remain").val("");
	
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
}

function logoff(){
	
	if(!rtnBD)
	{
		alert("未录入表底!");
		return;
	}
	
	var now_remain = $("#now_remain").val() === "" ? 0 : $("#now_remain").val();
	if(isNaN(now_remain)){
		alert("当前金额应该为数字!");
		$("#now_remain").focus().select();
		return;
	}

	
	var params = {};
	
	params.rtu_id 			= $("#rtu_id").val();
	params.mp_id 			= $("#mp_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.feeproj_id 		= $("#feeproj_id").val();
	params.myffalarmid 		= $("#yffalarm_id").val();
	params.pay_money 		= -parseFloat(now_remain);
	params.othjs_money 		= 0;
	params.zb_money 		= 0;
	params.all_money 		= params.pay_money;
	
	params.rtu_id0 = $("#rtu_id").val();
	params.rtu_id1 = $("#rtu_id").val();
	params.rtu_id2 = $("#rtu_id").val();
	
	params.mp_id0 = $("#zjg_id").val();
	params.mp_id1 = $("#zjg_id").val();
	params.mp_id2 = $("#zjg_id").val();
	
	params.pay_money = -parseFloat(now_remain);
	params.rtu_id 	 = $("#rtu_id").val();
	
	params.date = rtnBD.date;
	params.time = 0;
		
	for(var i = 0; i < 3; i++){
		eval("params.mp_id" + i + "=rtnBD.mp_id" + i);
		
		if(rtnBD.zf_flag.substring(i, i + 1) == "0"){	//正向
			eval("params.bd_zy"  + i + "=rtnBD.zbd" + i);
			eval("params.bd_zyj" + i + "=rtnBD.jbd" + i);
			eval("params.bd_zyf" + i + "=rtnBD.fbd" + i);
			eval("params.bd_zyp" + i + "=rtnBD.pbd" + i);
			eval("params.bd_zyg" + i + "=rtnBD.gbd" + i);
			eval("params.bd_zw"  + i + "=rtnBD.wbd" + i);
			eval("params.bd_fy"  + i + "=0");
			eval("params.bd_fyj" + i + "=0");
			eval("params.bd_fyf" + i + "=0");
			eval("params.bd_fyp" + i + "=0");
			eval("params.bd_fyg" + i + "=0");
			eval("params.bd_fw"  + i + "=0");
		}else{
			eval("params.bd_zy"  + i + "= 0");
			eval("params.bd_zyj" + i + "= 0");
			eval("params.bd_zyf" + i + "= 0");
			eval("params.bd_zyp" + i + "= 0");
			eval("params.bd_zyg" + i + "= 0");
			eval("params.bd_zw"  + i + "= 0");
			eval("params.bd_fy"  + i + "= rtnBD.zbd" + i);
			eval("params.bd_fyj" + i + "= rtnBD.jbd" + i);
			eval("params.bd_fyf" + i + "= rtnBD.fbd" + i);
			eval("params.bd_fyp" + i + "= rtnBD.pbd" + i);
			eval("params.bd_fyg" + i + "= rtnBD.gbd" + i);
			eval("params.bd_fw"  + i + "= rtnBD.wbd" + i);
		}
	}
	
	if(!confirm("确定要销户吗？")){
		return;
	}
		
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
		{
			userData1		: params.rtu_id,
			zjgid 			: params.zjg_id,
			gsmflag 		: 0,
			gyOperDestory	: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_DESTORY
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id); 
				$("#btnPrt").attr("disabled",false);
				$("#btnDestory").attr("disabled",true);
				$("#btnImportBD").attr("disabled",true);
				window.setTimeout('alert("销户成功!")',50);
				$("#now_remain").attr("disabled",false);
				$("#btnPrt").attr("disabled",false);
    			window.top.WebPrint.setYffDataOperIdx2params(data.gyOperDestory,window.top.WebPrint.nodeIdx.gyycmoney);//打印用的参数

			}
			else {
				alert("销户失败");
			}
		}
	);
}

function rtuInfo(){		//终端内信息-type bd或者je-
	
	modalDialog.width = 750;
	modalDialog.height = 190;
	modalDialog.url = "../dialog/callRemain.jsp";
	modalDialog.param = {
						 type 			: "je", 
						 rtuid 			: $("#rtu_id").val(), 
						 zjgid 			: $("#zjg_id").val(), 
						 userno 		: $("#userno").html(), 
						 username 		: $("#username").html(),
						 buytimes 		: $("#buy_times").val(),
						 blv			: $("#blv").html(),
						 dj				: $("#feeproj_reteval").val(),
						 cacl_type_desc : $("#cacl_type_desc").html(),
						 prot_type		: rtnValue.prot_type
						 };
	var o = modalDialog.show();
	if(!o)return;
	
	$("#btnDestory").attr("disabled",false);
	$("#now_remain").val(o.nowval);
}

function check(){
	
	var now_remain = $("#now_remain").val();
	if(isNaN(now_remain)){
		alert("当前金额输入错误!");
		$("#now_remain").focus().select();
		return false;
	}
	
	return true;
}

function showPrint(){//打印发票
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.gyycmoney);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintGy();
}