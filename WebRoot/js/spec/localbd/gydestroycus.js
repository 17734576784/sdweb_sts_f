var rtnValue;
var	mygrid = null;
var rtnBD = null;

$(document).ready(function(){
	
	initGrid();
	
	$("#btnSearch").click(function(){selcons()});
	
	$("#cardinfo").click(function(){cardinfo()});
	$("#btnDestory").click(function(){if(check())logoff()});
	
	$("#rtuInfo").click(function(){rtuInfo()});
	
	$("#btnImportBD").click(function(){Import()});
	$("#btnPrt").click(function(){showPrint()});

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
		$("#rtuInfo").attr("disabled",false);
	}
}

function selcons(){//检索
	
	var rtnValue1 = doSearch("bd",ComntUseropDef.YFF_GYOPER_DESTORY,rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
	
	setConsValue(rtnValue);
	
	getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
	
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
	
	$("#btnPrt").attr("disabled",true);
	$("#btnImportBD").attr("disabled",false);
	$("#td_bdinf").html("");
}

function logoff(){		//销户
	
	if(!rtnBD)
	{
		alert("未录入表底!");
		return;
	}
	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.mp_id 			= $("#mp_id").val();
	params.zjg_id			= $("#zjg_id").val();
	params.paytype 			= $("#pay_type").val();
	params.feeproj_id 		= $("#feeproj_id").val();
	params.myffalarmid 		= $("#yffalarm_id").val();
	params.pay_money 		= -parseFloat($("#now_remain").val()===""?0:$("#now_remain").val());
	params.othjs_money 		= 0;
	params.zb_money 		= 0;
	
	params.all_money 		= params.pay_money;
	
	params.rtu_id = $("#rtu_id").val();
	params.date = rtnBD.date;
	params.time = 0;
	
	for(var i = 0; i < 3; i++){
		eval("params.mp_id"  + i + "=rtnBD.mp_id" + i);
		
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
			mpid 			: params.mp_id,
			zjgid			: params.zjg_id,
			gsmflag 		: 0,
			gyOperDestory 	: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_DESTORY
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
				$("#btnDestory").attr("disabled",true);
				$("#btnImportBD").attr("disabled",true);
				$("#btnPrt").attr("disabled",false);
    			window.top.WebPrint.setYffDataOperIdx2params(data.gyOperDestory,window.top.WebPrint.nodeIdx.gyycbd);//打印用的参数
				window.setTimeout('alert("销户成功!")',50);
				
			}
			else {
				alert("销户失败!");
			}
		}
	);
}

function rtuInfo(){		//终端内信息-type bd或者je-
	modalDialog.width = 750;
	modalDialog.height = 190;
	modalDialog.url = "../dialog/callRemain.jsp";
	modalDialog.param = {
						 type 			: "bd", 
						 rtuid 			: $("#rtu_id").val(), 
						 zjgid 			: $("#zjg_id").val(), 
						 userno 		: $("#userno").html(), 
						 username 		: $("#username").html(),
						 buytimes 		: $("#buy_times").val(),
						 blv			: $("#blv").html(),
						 dj				: $("#feeproj_reteval").val(),
						 cacl_type_desc : $("#cacl_type_desc").html()
						 };
	var o = modalDialog.show();
	if(!o)return;
	
	$("#btnDestory").attr("disabled",false);
	$("#now_remain").val(o.remain_je);
}

function check(){
	
	var dqje = $("#now_remain").val();
	if(isNaN(dqje)){
		alert("当前金额输入错误!");
		$("#now_remain").focus().select();
		return false;
	}
	
	return true;
}

function showPrint(){//打印发票
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.gyycbd);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintGy();
}

