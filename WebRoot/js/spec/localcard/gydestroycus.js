//销户
var rtnValue;
var	mygrid = null;
var rtnBD = null;
var readcardFlag;//是否读卡检索。如果是读卡检索，销户的同时清空卡。
var WRITECARD_TYPE = window.top.SDDef.YFF_CARDMTYPE_KE001;


$(document).ready(function(){
	initGrid();
	
	$("#btnSearch"	).focus();
	$("#btnSearch"	).click(function(){selcons()});//检索事件
	$("#btnReadCard").click(function(){readcard()});//读卡检索
	
	$("#btnImportBD").click(function(){Import()});//录入
	$("#cardInfo"	).click(function(){window.top.card_comm.cardinfo()});//卡内信息
	$("#btnDestory"	).click(function(){if(check())logoff()});//销户
	$("#btnPrt"		).click(function(){showPrint()});//打印
	
});

function selcons(){//检索
	var tmp = doSearch("ks",ComntUseropDef.YFF_GYOPER_DESTORY,rtnValue);
	if(!tmp)return;
	if(!window.top.card_comm.checkInfo(tmp)) return;
	
	rtnValue = tmp;
	setConsValue(rtnValue);
	getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
	
	var tmp_val = rtnValue.cardmeter_type.split("]");
	WRITECARD_TYPE = tmp_val[0].split("[")[1];
//调整 20130402	
//	WRITECARD_TYPE = tmp_val[0].substring(1);
	if(WRITECARD_TYPE == ""){
		alert("卡表类型未配置。请到【档案管理-费控参数管理】配置卡表类型。")
		return;
	}
	$("#cardmeter_type").html(tmp_val[1].split('"')[0]);
	$("#writecard_no").html(rtnValue.writecard_no);
	$("#meter_id").html(rtnValue.meter_id);

	
	UnDisabled()
	$("#btnImportBD").focus();
	
    //显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
	//$("#btnDestory"	).attr("disabled",false);
}

function readcard(){
	loading.loading();
	window.setTimeout('window.top.card_comm.readCardSearchGy('+ComntUseropDef.YFF_GYOPER_DESTORY+')', 10);
	//window.top.card_comm.readCardSearchGy(ComntUseropDef.YFF_GYOPER_DESTORY);
}
function setSearchJson2Html(js_tmp){//调用readCardSearch后，readcard中异步执行完成后，执行此函数。
	loading.loaded();
	
	if(!js_tmp)return;
	if(!window.top.card_comm.checkInfo(js_tmp)) return;
	
	rtnValue = js_tmp;
	setConsValue(rtnValue);	
	var tmp_val = rtnValue.cardmeter_type.split("]");
	WRITECARD_TYPE = tmp_val[0].split("[")[1];
	//调整 20130402	
	//	WRITECARD_TYPE = tmp_val[0].substring(1);

	//add ext
	$("#cardmeter_type").html(tmp_val[1].split('"')[0]);
	$("#writecard_no").html(rtnValue.writecard_no);
	$("#meter_id").html(rtnValue.meter_id);
	
	UnDisabled()
	getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
	
	getMoneyLimit(rtnValue.feeproj_id);		//囤积限值
	getAlarmValue(rtnValue.yffalarm_id);//获取报警方式
	
	$("#buy_times").html($("#buy_times").val());
	
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
	
	//$("#btnDestory"	).attr("disabled",false);
	
}

//btn btnImportBD函数
function Import(){
	var tmp = importBD($("#rtu_id").val(),$("#zjg_id").val());
	if(!tmp){
		return;
	}
	rtnBD = tmp;
	$("#td_bdinf" 	).html(rtnBD.td_bdinf);
	$("#btnDestory" ).attr("disabled",false);
	
}

function logoff(){//销户
	
	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	
	params.pay_money 		= -parseFloat($("#now_remain").val()===""?0:round($("#now_remain").val(),2));
	params.othjs_money 		= 0;
	params.zb_money 		= 0;
	params.all_money 		= params.pay_money;
	
	params.date 			= rtnBD.date;
	params.time				= 0;
	
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
	
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag 	: true,
			userData1		: params.rtu_id,
			zjgid 			: params.zjg_id,
			gsmflag 		: 0,
			gyOperDestory	: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_DESTORY
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				alert("销户成功,请手工清空购电卡..");
				IsDisabled();
				getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
				$("#btnPrt").attr("disabled",false);
    			window.top.WebPrint.setYffDataOperIdx2params(data.gyOperDestory);//打印用的参数

			}
			else {
				alert("向主站发送命令失败" + (data.operErr ? data.operErr : ''));
			}
		}
	);
}


function showPrint(){//打印发票
	var filename = null; 
	
	if (WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE003){
		filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.gycard3);
		window.top.WebPrint.prt_params.page_type = window.top.WebPrint.nodeIdx.gycard3;
	}else{
		filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.gycard1);
		window.top.WebPrint.prt_params.page_type = window.top.WebPrint.nodeIdx.gycard1;
	}
	if(filename == undefined || filename == null) return;
	window.top.WebPrint.prt_params.file_name = filename;
//	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.gyks[WRITECARD_TYPE];
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintGy();
}

function check(){
	//判断总表底是否为空
	if (rtnBD == null){
		alert("请录入表底!");
		return false;
	}
	
	if(!confirm("确定要销户吗？")){
		return false;
	}
	return true;
}

//disabled  暂时没用
function IsDisabled(){
	$("#dqje").attr("disabled",true);
	$("#btnDestory ").attr("disabled",true);
	//$("#rtuInfo    ").attr("disabled",true);
	$("#btnImportBD").attr("disabled",true);
}

//undisabled
function UnDisabled(){
	$("#btnDestory").attr("disabled",true);
	$("#td_bdinf").html("");
	$("#btnImportBD").attr("disabled",false);
    rtnBD = null;//旧表底信息清零
}

