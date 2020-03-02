//恢复费控
var rtnValue;
var rtnBD = null;//全局变量表底
$(document).ready(function(){
	$("#btnSearch" ).click(function(){selcons()});
	$("#btnRecover").click(function(){
		if(check()){
		    reStart();
		}
	});
	$("#btnImportBD").click(function(){Import()});
	$("#btnPrt").click(function(){showPrint()});
	
	$("#zbje"       ).keyup(function(){calcu()});
	$("#jsje"       ).keyup(function(){calcu()});
	$("#jfje"       ).keyup(function(){calcu()});
	
	initGrid();
	IsDisabled();
});

function Import(){
	var tmp = importBD($("#rtu_id").val(),$("#zjg_id").val());
    if(!tmp)return;
    rtnBD = tmp;
    $("#td_bdinf").html(rtnBD.td_bdinf);
    //录入表底之后,恢复按钮可用
    $("#btnRecover"     ).attr("disabled",false);
}

function calcu(){
	var zbje = 0.0, jfje = 0.0, jsje = 0.0;
	
	zbje = $("#zbje").val()==="" ? 0 : $("#zbje").val();
	jfje = $("#jfje").val()==="" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val()==="" ? 0 : $("#jsje").val();
	
	var zje = round(parseFloat(zbje) + parseFloat(jfje) + parseFloat(jsje),3);
	
	if(!isNaN(zje)){
		setAlarmValue(rtnValue.yffalarm_alarm1, rtnValue.yffalarm_alarm2, zje)
		$("#zje").val(zje);
	}
}

function selcons(){//检索
	rtnValue = doSearch("zz",ComntUseropDef.YFF_GYOPER_RESTART, rtnValue);
	if(!rtnValue){
		return;
	}
	
	setConsValue(rtnValue);
	//购电次数 单独赋值
	$("#buy_times").html(rtnValue.buy_times);
	
	getGyYffRecs();
	
	getAlarmValue(rtnValue.yffalarm_id);//获取报警方式
	
	getMoneyLimit(rtnValue.feeproj_id);//获取囤积值
	
	UnDisabled()
	$("#btnPrt").attr("disabled",true);
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
}

function initGrid(){
	var yff_grid_title ='操作日期,操作类型,缴费金额(元),追补金额(元),结算金额(元),总金额(元),断电值,报警值1,报警值2,购电次数,流水号,缴费方式,操作员,&nbsp;';
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("150,80,80,80,80,80,80,80,80,80,150,80,80,*");
	mygrid.setColAlign("center,center,right,right,right,right,right,right,right,right,left,left,left");
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.enableTooltips("false,false,false,false,false,false,false,false,false,false,false,false,false");
	mygrid.init();
	mygrid.setSkin("light");
	mygrid.setColumnHidden(6, true);
}

function getGyYffRecs(){//缴费记录
	mygrid.clearAll();
	var param = "{\"rtu_id\":\"" + $("#rtu_id").val() + "\",\"zjg_id\":\"" +$("#zjg_id").val() + "\",\"top_num\":\"5\"}";
	$.post( def.basePath + "ajax/actCommon!getGyYFFRecs.action",{result:param},	function(data) {			    	//回传函数
		if (data.result != "") {
			jsdata = eval('(' + data.result + ')');
			mygrid.parse(jsdata,"json");
		}
	});
}

function check(){
	if(rtnBD ==null){
		alert("未录入表底..");
		return false;
	}
	
	var jfje = 0, zbje = 0, jsje = 0;
	zbje = $("#zbje").val()==="" ? 0 : $("#zbje").val();
	jfje = $("#jfje").val()==="" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val()==="" ? 0 : $("#jsje").val();

	if(isNaN(zbje)){
		alert("请输入数字!");
		$("#zbje").focus().select();
		return false;
	}
	
	if(isNaN(jfje)){
		alert("请输入数字!");
		$("#jfje").focus().select();
		return false;
	}

	if(isNaN(jsje)){
		alert("请输入数字!");
		$("#jsje").focus().select();
		return false;
	}

	if(!isDbl("jfje",  "缴费金额" ,0 , money_limit, false)){
		return false;
	}

	if(!isDbl("zbje",  "追补金额" ,-money_limit , money_limit, false)){
		return false;
	}
	if(!isDbl("jsje",  "结算金额" ,-money_limit , money_limit, false)){
		return false;
	}

	if ((window.top.gynegativemoney.gymain.totflag == 0) &&
		($("#zje").html() < "0")) {
		alert("总金额不能为负数");
		return false;
	}
		
	if(!isDbl("zje" ,  "总金额", -money_limit, money_limit, false)){//缴费金额应该在0~囤积值之间
		return false;
	}

	var zje=$("#zje").val();
	var jfje=$("#jfje").val();
	
	if(($("#zje").val()=="0"||$("#zje").val()=="")){
		return(confirm("客户未缴费,继续操作吗?"))
	}
	
	return true;
}

function reStart(){
	
	var params = {};
	
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.myffalarmid 		= $("#yffalarm_id").val();
	params.paytype 			= $("#pay_type").val();
	params.buynum 			= $("#buy_times").val();
	
	params.alarm_val1		= $("#yffalarm_alarm1").val();
	params.alarm_val2		= $("#yffalarm_alarm2").val();
	
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	
	params.all_money = round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),3);
		
	params.buy_dl  = 0;
	params.pay_bmc = 0;
	params.shutdown_bd = 0;
	
	params.total_yzbdl = $("#total_yzbdl").val() == "" ? 0 : $("#total_yzbdl").val();
	params.total_wzbdl = $("#total_wzbdl").val() == "" ? 0 : $("#total_wzbdl").val();
	
	params.rtu_id0 = $("#rtu_id").val();
	params.rtu_id1 = $("#rtu_id").val();
	params.rtu_id2 = $("#rtu_id").val();
	
	params.date = rtnBD.date;
	params.time =0;
		
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
		}
		else{
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
	
	//确认缴费的info
	var gsmFlag = 0;
	
	modalDialog.height = 300;
	modalDialog.width = 280;
	modalDialog.url = def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param = {
		showGSM : true,
		key	: ["客户编号","客户名称", "缴费金额","追补金额","结算金额","总金额"],
		val	: [$("#userno").html(),$("#username").html(), params.pay_money,params.zb_money,params.othjs_money,params.all_money]		
	};
	
	var o = modalDialog.show();
	
	if(!o||!o.flag){
		return;
	}
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
		{
			userData1		: params.rtu_id,
			zjgid 			: params.zjg_id,
			gsmflag 		: 0,
			gyOperReStart 	: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_RESTART
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {		
				alert("恢复成功!");			
				getGyYffRecs();
				IsDisabled();
				$("#btnPrt").attr("disabled",false);
   	 			window.top.WebPrint.setYffDataOperIdx2params(data.gyOperReStart);//打印用的参数

			}
			else {
				alert("向主站发送开户命令失败");
			}
		}
	);
}

//刚刚进入界面  各个input默认disabled
function IsDisabled(){
	$("#zbje"           ).attr("disabled",true);
	$("#jfje"           ).attr("disabled",true);
	$("#jsje"           ).attr("disabled",true);
	$("#yffalarm_alarm1").attr("disabled",true);
	$("#yffalarm_alarm2").attr("disabled",true);
	$("#btnImportBD"    ).attr("disabled",true);
	$("#btnRecover"     ).attr("disabled",true);
	
	$("#total_yzbdl").attr("disabled",true);
	$("#total_wzbdl").attr("disabled",true);
}

//selcons 各个input属性disabled更改为 false
function UnDisabled(){
	//清空追补金额,结算金额,缴费金额,总金额的值 方便继续开户
	$("#zbje"           ).attr("value","");
	$("#jsje"           ).attr("value","");
	$("#jfje"           ).attr("value","");
	$("#zje"            ).attr("value","");
	$("#td_bdinf"       ).html("");
	rtnBD = null;
	$("#zbje"           ).attr("disabled",false);
	$("#jfje"           ).attr("disabled",false);
	$("#jsje"           ).attr("disabled",false);
	$("#yffalarm_alarm2").attr("disabled",false);
	$("#yffalarm_alarm1").attr("disabled",false);
	
	$("#total_yzbdl").attr("disabled",false);
	$("#total_wzbdl").attr("disabled",false);
	
	$("#btnImportBD").attr("disabled",false);
}

function showPrint(){//打印发票
	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.gyje;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintGy();
}

