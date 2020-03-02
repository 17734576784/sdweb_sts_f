var rtnValue;
//var rtnBD;
var WRITECARD_TYPE = 1;

$(document).ready(function(){
	initGrid();
	
	//标准检索
	$("#btnSearch").click(function(){selcons()});
	//获取余额
//	$("#getYE").click(function(){getYE()});
	//销户
	$("#btnDestroy").click(function(){consDestroy()});
	//读卡检索
	$("#btnRead").click(function(){read_card()});
	//卡内信息
	$("#cardinfo").click(function(){window.top.card_comm.cardinfo()});
	//表内信息
	//$("#metinfo").click(function(){metinfo()});
	//打印
	$("#btnPrint").click(function(){printPayRec()});
	
	$("#btnSearch").focus();

	setDisabled(true);
	$("#btnDestroy").attr("disabled",true);
});
function selcons(){//检索
	setDisabled(false);
	$("#btnPrint").attr("disabled",true);
	var rtnValue1 = doSearch("ks",ComntUseropDef.YFF_NPOPER_DESTORY,rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
	setConsValue(rtnValue);
	//$("#now_remain").val(rtnValue.now_remain);
	getRecord(rtnValue.areaid,rtnValue.farmerid);
	//$("#btnImportBD").focus();
	$("#btnDestroy").attr("disabled", false);
}

function setDisabled(mode){
	if(!mode){
		setConsValue(rtnValue,true);
		mygrid.clearAll();
		//$("#td_bdinf").html("");
		//$("#now_remain").html("");
		//$("#sfbd_sj").html("");
		//$("#sfbd").html("");
		
	    //计算客户名称  结算客户分类
		$("#jsyhm").html("");
		$("#yhfl").html("");
		//rtnBD = null;
	}
	//$("#now_remain").attr("disabled",mode);
	//$("#btnImportBD").attr("disabled",mode);
}

function consDestroy(){	//销户
	if(!check()) return;
	
	if(!confirm("确定要销户吗？")){
		return;
	}
	
	var area_id = $("#area_id").val(), farmer_id = $("#farmer_id").val();
	if(area_id === "" || farmer_id === ""){
		alert("请选择用户信息!");
		return;
	}
	var params = {};
//	params.rtu_id = rtu_id;
//	params.mp_id = mp_id;
//	
//	params.pay_money = $("#now_remain").val() === "" ? 0 : ($("#now_remain").val() * (-1));
//	params.othjs_money = 0;
//	params.zb_money = 0;
//	params.all_money = params.pay_money;
	
	params.pay_money = $("#now_remain").html() === "" ? 0 : ($("#now_remain").html() * (-1));
	params.othjs_money = 0;
	params.zb_money = 0;
	params.all_money = params.pay_money;
	
	params.area_id		= $("#area_id").val();
	params.farmer_id		= $("#farmer_id").val();
	
	params.lsremain			= $("#now_remain").html();		//还需要处理
	
//	params.date = rtnBD.date;
//	params.time = 0;
//	for(var i = 0; i < 3; i++){
//		eval("params.mp_id"  + i + "=rtnBD.mp_id" + i);
//		eval("params.bd_zy"  + i + "=rtnBD.zbd" + i);
//		eval("params.bd_zyj" + i + "=rtnBD.jbd" + i);
//		eval("params.bd_zyf" + i + "=rtnBD.fbd" + i);
//		eval("params.bd_zyp" + i + "=rtnBD.pbd" + i);
//		eval("params.bd_zyg" + i + "=rtnBD.gbd" + i);
//	}
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperNp!taskProc.action", 		//后台处理程序
		{
			userData1		: params.area_id,
			farmerid		: params.farmer_id,
			gsmflag			: 0,
			npOperDestory	: json_str,
			userData2		: ComntUseropDef.YFF_NPOPER_DESTORY
		},
		function(data) {			    	//回传函数
			if (data.result == "success") {
				alert("销户成功,请手工清空购电卡..");
				setDisabled(true);
				$("#btnDestroy").attr("disabled",true);
				$("#btnPrint").attr("disabled",false);
				window.top.WebPrint.setYffDataOperIdx2params(data.npOperDestory,window.top.WebPrint.nodeIdx.npcard);//打印用的参数
			}
			else {
				alert("销户失败..." + (data.operErr ? data.operErr : ''));
			}
		loading.loaded();
		}
	);
}

function check() {
return true;	
//	if(rtnBD == null) {
//		alert("请输入表底");
//		return false;
//	}
//	if(!isDbl("now_remain", "当前余额", 0, rtnValue.moneyLimit)){
//		return false;
//	}
//	return true;
}

function read_card(){//读卡
	setDisabled(false);
	loading.tip = "正在读卡,请稍后...";
	loading.loading();
	setTimeout("window.top.card_comm.readCardSearchNp(" + ComntUseropDef.YFF_NPOPER_DESTORY + ")", 10);
}

function setSearchJson2Html(js_tmp) {
	loading.loaded();
	if(!js_tmp)return;
	if(!window.top.card_comm.checkInfo(js_tmp)) return;
	
	rtnValue = js_tmp;
	setConsValue(rtnValue);
	getRecord(rtnValue.areaid,rtnValue.farmerid);
//	$("#btnImportBD").focus();
	$("#btnDestroy").attr("disabled", false);
}


//显示用户用电信息
function showYDInfo(){
	_showYDInfo(rtnValue.areaid, rtnValue.farmerid);
}

function getCardTp(code){
	var ret_str = "购电卡";
	switch(code){
		case "FF":
			ret_str = "原始卡";
			break;
		case "00":
			ret_str = "空白卡";
			break;
		case "01":
			ret_str = "开户卡";
			break;
		case "02":
			ret_str = "购电卡";
			break;
		case "03":
			ret_str = "补卡";
			break;
		case "04":
			ret_str = "费率设置卡";
			break;
		case "05":
			ret_str = "检查卡";
			break;
		default :
			break;
	}
	return ret_str;
}


function printPayRec(){//打印缴费记录
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.npcard);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}
