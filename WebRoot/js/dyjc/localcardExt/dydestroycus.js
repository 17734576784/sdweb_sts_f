var rtnValue;
var rtnBD;

$(document).ready(function(){
	initGrid();
	
	//录入表底
	$("#btnImportBD").click(function(){Import()});
	//标准检索
	$("#btnSearch").click(function(){selcons()});
	//销户
	$("#btnDestroy").click(function(){consDestroy()});
	//读卡检索
	$("#btnRead").click(function(){read_card()});
	//卡内信息
	$("#cardinfo").click(function(){window.top.card_comm.extcardinfo()});
	//20140729新增 写卡户号一样，户号不一样
	$("#btnReadSearch").click(function(){readCard_search()});//读卡检索
	//打印
	$("#btnPrint").click(function(){printPayRec()});
	
	$("#dqye").keyup(function(){calcTotal();});
	
	$("#btnSearch").focus();

	setDisabled(true);
	$("#btnDestroy").attr("disabled",true);
});


function calcTotal(){//总金额
	
	var dqye = $("#dqye").val()==="" ? 0 : $("#dqye").val();

	if(isNaN(dqye))		return;
	
	var dj_str = $("#feeproj_reteval").val() === "" ? 1 : $("#feeproj_reteval").val();
	var dj_temp = dj_str.split(",");
	var dj = dj_temp[0];
	
	var tmp_dl, tmp_bmc;

	if(rtnValue.cacl_type == SDDef.YFF_CACL_TYPE_MONEY) {
		$("#buy_dl").html("0.0");
		$("#pay_bmc").html("0.0");		
	}
	else {
		tmp_dl  = dqye / dj;
		tmp_bmc = tmp_dl / rtnValue.pt / rtnValue.ct;
		
		$("#buy_dl").html(round(tmp_dl, 2))
		$("#pay_bmc").html(round(tmp_bmc, 2));
	}	
}
function selcons(){//检索
	var rtnValue1 = doSearch("kzext",ComntUseropDef.YFF_DYOPER_DESTORY,rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
	setcons1();
}

function setcons1(){	
	setDisabled(false);
	//每次检索禁用销户按钮
	$("#btnDestroy").attr("disabled","disabled");
	$("#btnPrint").attr("disabled","disabled");
	setConsValue(rtnValue);
	$("#writecard_no").html(rtnValue.writecard_no);
	//$("#dqye").val(rtnValue.now_remain);
	getRecord(rtnValue.rtu_id, rtnValue.mp_id);
	$("#btnImportBD").focus();
	loading.loaded();
}

function setDisabled(mode){
	if(!mode){
		setConsValue(rtnValue,true);
		mygrid.clearAll();
		$("#td_bdinf").html("");
		$("#dqye").val("");
//		$("#sfbd_sj").html("");
//		$("#sfbd").html("");
		$("#writecard_no").html("");
	    //计算客户名称  结算客户分类
		$("#jsyhm").html("");
		$("#yhfl").html("");
		rtnBD = null;
	}
	$("#dqye").attr("disabled",mode);
	$("#btnImportBD").attr("disabled",mode);
}

function consDestroy(){	//销户
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
	
	params.pay_money = $("#dqye").val() === "" ? 0 : ($("#dqye").val() * (-1));
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

	params.card_rand 	= rtnValue.card_rand;			//随机数
	params.card_area 	= rtnValue.card_area;			//区域码
	params.cardtype 	= rtnValue.cardtype;			//预付费电表类型
	params.card_pass	= rtnValue.card_pass;
	params.cacl_type 	= rtnValue.cacl_type;			//金额 表底 量控
	params.buy_dl		= $("#buy_dl").html();
	params.pay_bmc		= 	$("#pay_bmc").html();
	params.update_flag  = 0x00;

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
		function(data) {			    	//回传函数
			if (data.result == "success") {
				alert("销户成功,请手工清空购电卡..");
				setDisabled(true);
				$("#btnDestroy").attr("disabled",true);
				$("#btnPrint").attr("disabled",false);
				getRecord(rtu_id, mp_id);
				window.top.WebPrint.setYffDataOperIdx2params(data.dyOperDestory,window.top.WebPrint.nodeIdx.dycard);//打印用的参数
			}
			else {
				alert("销户失败..." + (data.operErr ? data.operErr : ''));
			}
		loading.loaded();
		}
	);
}

function check() {
	if(rtnBD == null) {
		alert("请输入表底");
		return false;
	}
	if(!isDbl("dqye", "当前余额", -rtnValue.moneyLimit, rtnValue.moneyLimit)){
		return false;
	}
	return true;
}

function read_card(){//读卡
	loading.tip = "正在读卡,请稍后...";
	loading.loading();
	setTimeout("window.top.card_comm.readExtCardSearchDy(" + ComntUseropDef.YFF_DYOPER_DESTORY + ")", 10);
}

//20140729新增,关闭列表窗口时，取消loading
function closeloading(){
	loading.loaded();
}

function readCard_search(){
	loading.tip = "正在读卡,请稍后...";
	loading.loading();
	//如果该写卡户号只被一个用户使用，直接调用setSearchJson2
	//如果为多个用户使用，则从模态窗口中选择。
	window.top.card_comm.readExtCardSameWrtNo(ComntUseropDef.YFF_DYOPER_DESTORY);
}

function setSearchJson2Html(js_tmp) {
	loading.loaded();
	if(!js_tmp)return;
	if(!window.top.card_comm.checkExtInfo(js_tmp)) return;
	rtnValue = js_tmp;
	setcons1();
}

function getUserInfo(CARD_NO, PURP_FLAG, IDDATA){
	
	$.post( def.basePath + "ajaxdyjc/actCardReadInfo!taskProc.action", 		//后台处理程序
		{
			result		  : CARD_NO,
			firstLastFlag : true,
			gsmflag 	  : 0,
			userData2	  : ComntUseropDef.YFF_DYOPER_GPARASTATE
		},
		function(data) {			    	//回传函数			
			
			if (data.result == SDDef.SUCCESS) {
				var json = eval("(" + data.dyOperGParaState + ")");
				var rows = eval("(" + data.userInfo + ")");
				rtnValue = {json : json, rows : rows};
				
				$("#rtu_id").val(rtnValue.rows.rtu_id);
				$("#mp_id").val(rtnValue.rows.mp_id);
				
				var param = "{\"rtu_id\":\"" + $("#rtu_id").val() + "\",\"mp_id\":\"" +$("#mp_id").val() + "\"}"; 
				$.post( def.basePath + "ajaxdyjc/actConsPara!getOneRec2.action",{result:param},	function(data) {			    	//回传函数
					if (data.result != "") {
						var json = eval('(' + data.result + ')');
						$("#factory").html(json.factory);
						$("#jxfs").html(json.wiring_mode);
					}
				});
				
				$("#userno").html(rtnValue.rows.cons_no);
				$("#username").html(rtnValue.rows.cons_desc);
				$("#tel").html(rtnValue.rows.cons_telno);
				$("#useraddr").html(rtnValue.rows.cons_addr);
				
				$("#cus_state").html(rtnValue.json.cus_state);
				$("#feectrl_type").html(rtnValue.json.feectrl_type);
				$("#feeproj_desc").html(rtnValue.json.feeproj_desc);
				$("#feeproj_detail").html(rtnValue.json.feeproj_detail);
				
				$("#feeproj_id").val(rtnValue.json.feeproj_id);
				$("#yffalarm_id").val(rtnValue.json.yffalarm_id);
				$("#yffalarm_desc").html(rtnValue.json.yffalarm_desc);
				$("#yffalarm_detail").html(rtnValue.json.yffalarm_detail);
				
				$("#pay_type_desc").html(rtnValue.json.pay_type_desc);
				$("#cacl_type").html(rtnValue.json.cacl_type);
				$("#yffmeter_type").html(rtnValue.json.yffmeter_type_desc);
				
				METER_ID = rtnValue.rows.meter_no;
				$("#esamno").html(rtnValue.rows.meter_no);
				$("#pt").html(rtnValue.rows.pt);
				$("#ct").html(rtnValue.rows.ct);				
				$("#cacl_type").html(rtnValue.json.cacl_type);
				
				var card_tp = getCardTp(IDDATA.split("|")[1]);
				$("#card_type").html(card_tp);
				$("#buy_times").html(rtnValue.json.buy_times);
				$("#ljgdje").html(rtnValue.json.total_gdz);
				$("#scjfje").html(rtnValue.json.pay_money);
				
				$("#pay_type").val(rtnValue.json.pay_type);
			}
			else {
				var json = eval("(" + data.dyOperGParaState + ")");
				var rows = eval("(" + data.userInfo + ")");
				rtnValue = {json : json, rows : rows};
				$("#userno").html(rtnValue.rows.cons_no);
				
				var card_tp = getCardTp(IDDATA.split("|")[1]);
				$("#card_type").html(card_tp);
				$("#esamno").html(IDDATA.split("|")[5]);
				$("#buy_times").html(IDDATA.split("|")[7]);
				$("#xkje").html(IDDATA.split("|")[6]);
				
				$("#ct").html(IDDATA.split("|")[8]);
				$("#pt").html(IDDATA.split("|")[9]);
			}
			
			getYffRecs();
			$("#btnDestroy").attr("disabled", false);
			$("#btnImportBD").attr("disabled", false);
		}
	);
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

//function metinfo(){//表内信息
//	modalDialog.width = 800;
//	modalDialog.height = 600;
//	modalDialog.url = def.basePath + "jsp/dyjc/dialog/meterInfo.jsp";
//	modalDialog.param = {"rtuId":$("#rtu_id").val() ,"mpId":$("#mp_id").val()};
//	modalDialog.show();
//}

function printPayRec(){//打印缴费记录
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.dycard);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}

function Import(){//录入
	var tmp = importBD($("#rtu_id").val(),$("#mp_id").val());
	if(!tmp)return;
	rtnBD = tmp;
    $("#td_bdinf").html(rtnBD.td_bdinf);
	$("#btnImportBD").attr("disabled",false);
	$("#btnDestroy").attr("disabled",false);
}