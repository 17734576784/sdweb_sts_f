var rtnValue;
var	mygrid;
var feeproj_data;
//var jsonFLFA;
var rtnBD = null;
var rtnzbd = "";

$(document).ready(function(){
	
	$("#btnSearch").click(function(){selcons()});
	$("#chgfl").click(function(){chg_fl()});
	$("#savefl").click(function(){save_fl()});
	$("#rtuInfo").click(function(){rtuInfo()});
	$("#btnImportBD").click(function(){Import()});
	
	$("#new_feeproj").change(function(){chg_feeproj(this.value);})
	
	$("#last_shutdown_bd").keyup(function(){if(isNaN(this.value))this.value = 0;calcu_bd_byid();});
	
	$("#alarm_bd").keyup(function(){if(isNaN(this.value))this.value = 0;});
	
	initChgTime();
	initGrid();
	init_feeproj();
	
	setTextDisabled(true);
});

function setTextDisabled(mode){	//设置文本框状态（disabled/enabled）
	if(!mode){
		$("#alarm_bd").val("");
		
		$("#syje").html("");
		$("#buy_dl").html("");
		$("#bmc").html("");
		$("#shutdown_bd").html("");
	}
	$("#last_shutdown_bd").attr("disabled",mode);
	$("#alarm_bd").attr("disabled",mode);
}

function initChgTime(){
	var date = new Date();
	$("#chg_time").val(date.Format("yyyy年MM月dd日 HH时mm分").toString());
}

function init_feeproj(){
	//这是原来的获取费率方案的函数
	$.post(def.basePath + "ajaxspec/actConsPara!retRate.action",{},function(data){
		if(data.result != ""){
			//alert(data.result);//要删除
			feeproj_data = eval('(' + data.result + ')');
			var option = "";
			for ( var i = 0; i < feeproj_data.rows.length; i++) {
				if(feeproj_data.rows[i].fee_type == 0){
					option += "<option value="+feeproj_data.rows[i].id+">"+feeproj_data.rows[i].desc+"</option>";
				}
			}
		//	$("#new_feeproj").html(option.substring(1));
		//	setTimeout('chg_feeproj($("#new_feeproj").val())',10);
			$("#new_feeproj").html(option); //.substring(1)
			
			if (feeproj_data.rows.length > 0) {
				$("#new_feeproj_detail").html(feeproj_data.rows[0].detail);
			}
		}
	});
	
//	$.post(def.basePath + "ajaxdyjc/actChgRate!getRateList.action",{},function(data){
//		if(data.result != ""){
//			//alert(data.result);
//			jsonFLFA = eval('(' + data.result + ')');
//			for ( var i = 0; i < jsonFLFA.rows.length; i++) {
//				if(jsonFLFA.rows[i].feeType == 0){
//					$("#new_feeproj").append("<option value="+jsonFLFA.rows[i].value+">"+jsonFLFA.rows[i].text+"</option>");
//				}
//			}
//			setTimeout('chg_feeproj($("#new_feeproj").val())',10);
//		}
//	});
}

function chg_feeproj(value){
	
	for ( var i = 0; i < feeproj_data.rows.length; i++) {
		if(value == feeproj_data.rows[i].id){
			$("#new_feeproj_detail").html(feeproj_data.rows[i].detail);
			calcu_bd(feeproj_data.rows[i].rate);
			break;
		}
	}
//	for ( var i = 0; i < jsonFLFA.rows.length; i++) {
//		if(value == jsonFLFA.rows[i].value){
//			$("#new_feeproj_detail").html(jsonFLFA.rows[i].desc);
//			calcu_bd(jsonFLFA.rows[i].val_rateZ);
//			break;
//		}
//	}
}

function chg_fl(){
	
	var zbd = rtnzbd;//$("#zbd").val();
	if(zbd === ""){
		alert("未输入表底!");
		return;
	}
	
	if(!confirm("确定要更换费率吗?"))return;
	
	var o_fee				= $("#feeproj_id").val();
	var n_fee				= $("#new_feeproj").val();
	
	if(o_fee == n_fee){
		//if(!confirm("费率相同，确定要更换吗?"))return;
		alert("费率相同，不需要更换。");return;
	}
	
	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.paytype 			= $("#pay_type").val();
	params.yffalarm_id 		= $("#yffalarm_id").val();
	params.buynum 			= $("#buy_times").val() === "" ? 1 : $("#buy_times").val();
	params.pay_money 		= 0;
	params.othjs_money 		= 0;
	params.zb_money 		= $("#syje").html()=== "" ? 0 : $("#syje").html();
	params.all_money 		= params.pay_money;
	/*
	if(parseFloat(params.all_money) > parseFloat(money_limit)){
		alert("总金额【"+params.all_money+"】大于囤积限值【"+money_limit+"】");
		return;
	}
	*/
	params.alarmmoney		= 0;
	params.buydl			= $("#buy_dl").html()	=== "" ? 0 : $("#buy_dl").html();
	params.paybmc			= $("#bmc").html()	=== "" ? 0 : $("#bmc").html();
	params.alarmbd			= $("#alarm_bd").val()=== "" ? 0 : $("#alarm_bd").val();
	params.shutbd			= $("#shutdown_bd").html() ==="" ? 0 : $("#shutdown_bd").html();
	
	params.yffflag			= $("#start_yff_flag").attr('checked') ? 1 : 0;
	
	if ($("#dpfs").attr('checked')) {
		params.plustime		= 0;
	}else {
		params.plustime		= $("#plus_width").val() === "" ? 0 : $("#plus_width").val();
	}
	
	var json_str = jsonString.json2String(params);
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	loading.loading();

	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommGy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_GYCOMM_PAY;
	//end
	
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommGy!taskProc.action", 		//后台处理程序
	{
		firstLastFlag 	: true,
		userData1		: params.rtu_id,
		zjgid			: params.zjg_id,
		gyCommPay 		: json_str,
		userData2 		: ComntUseropDef.YFF_GYCOMM_PAY
	},
	function(data) {			    	//回传函数
		window.top.addJsonOpDetail(data.detailInfo);
		loading.loaded();
		if (data.result == "success") {
			//addDB();
			window.top.addStringTaskOpDetail("更换费率通讯成功...");
			alert("更换费率通讯成功...");
		}else{
			window.top.addStringTaskOpDetail("接收预付费服务任务失败...");
			alert("通讯失败...");
		}
	});
}

function addDB(){
	
	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.mp_id 			= 1;
	params.paytype 			= $("#pay_type").val();
	params.feeproj_id 		= $("#feeproj_id").val();
	params.myffalarmid 		= $("#yffalarm_id").val();
	params.buynum 			= $("#buy_times").val();
	params.pay_money 		= 0;
	params.othjs_money 		= 0;
	params.zb_money 		= $("#syje").html()===""?0:$("#syje").html();
	params.all_money		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),def.point);
	
	params.alarm_val1		= $("#alarm_bd").val()=== "" ? 0 : $("#alarm_bd").val();
	params.alarm_val2		= 0;
	params.buy_dl			= $("#buy_dl").html();
	params.pay_bmc			= $("#bmc").html();
	params.shutdown_bd		= $("#shutdown_bd").html();
	
	params.rtu_id0 = $("#rtu_id").val();
	params.rtu_id1 = $("#rtu_id").val();
	params.rtu_id2 = $("#rtu_id").val();
	
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
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
		{
			userData1		: params.rtu_id,
			zjgid			: params.zjg_id,
			mpid 			: params.mp_id,
			gsmflag 		: 0,
			gyOperPay	 	: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_PAY
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				
				window.top.addStringTaskOpDetail("接收预付费服务任务成功...");
				
				//$("#btnPrt").attr("disabled",false);
				
				$("#chgfl").attr("disabled",true);
				
				$("#buy_times").val(parseInt($("#buy_times").val())+1);
				
				alert("更换费率成功!");
				
				getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
				
			}else{
				window.top.addStringTaskOpDetail("更换费率成功,但存数据库失败...");
				alert("更换费率失败!");
			}
		}
	);
}

function save_fl(){
	var zbd = rtnzbd;//$("#zbd").val();
	if(zbd === ""){
		alert("未输入表底!");
		return;
	}
	
	var o_fee				= $("#feeproj_id").val();
	var n_fee				= $("#new_feeproj").val();
	
	if(o_fee == n_fee){
		alert("费率相同，不需保存...")
		return;
	}
	
	if(!confirm("确定要保存费率吗?"))return;
	
	var params = {};
	params.rtu_id 		= $("#rtu_id").val();
	params.zjg_id 		= $("#zjg_id").val();
	params.mp_id 		= 1;
	params.chgid0		= $("#new_feeproj").val();
	params.chgid1		= $("#feeproj_id").val();
	params.chgid2		= $("#feeproj_id").val();
	
	var chg_time 		= $("#chg_time").val();
	params.chg_date		= chg_time.substring(0,4)+chg_time.substring(5,7)+chg_time.substring(8,10);
	params.chg_time		= chg_time.substring(12,14)+chg_time.substring(15,17)+"00";
	params.date		= 0;
	params.time		= 0;
	
	params.rtu_id0 = $("#rtu_id").val();
	params.rtu_id1 = $("#rtu_id").val();
	params.rtu_id2 = $("#rtu_id").val();
	
	
	for(var i = 0; i < 3; i++){
		eval("params.mp_id"  + i + "=rtnBD.mp_id" + i);
		eval("params.date"  + i + "=rtnBD.date");
		eval("params.time"  + i + "=0");
		
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
			userData1		: params.rtu_id,
			zjgid			: params.zjg_id,
			mpid 			: params.mp_id,
			gsmflag 		: 0,
			gyOperChangeRate: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_CHANGERATE
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				
				//$("#btnPrt").attr("disabled",false);
				
				alert("保存费率成功!");
				var colors = "<b style='color:#800000'>";
				var colore = "</b>";
				$("#feeproj_id").val($("#new_feeproj").val());
				$("#feeproj_desc").html(colors+$("#new_feeproj").find("option:selected").text()+colore);
				$("#feeproj_detail").html(colors+$("#new_feeproj_detail").html()+colore);
				$("#savefl").attr("disabled",true);
				$("#btnImportBD").attr("disabled",true);
				getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
			}else{
				alert("保存费率失败!");
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
						userno 			: $("#userno").html(), 
						username 		: $("#username").html(),
						buytimes 		: $("#buy_times").val(),
						blv				: $("#blv").html(),
						dj				: $("#feeproj_reteval").val(),
						cacl_type_desc	: $("#cacl_type_desc").html()
						};
	var o = modalDialog.show();
	if(!o)return;
	
	//和开户一样。终端内信息只供查看不使用。
//	$("#syje").html(o.remain_je);
//	$("#bmc").html(o.totval);
//	$("#shutdown_bd").html(o.totval);
//	$("#alarm_bd").val(o.alarm_val1);

//	calcu_bd();
	calcu_bd_byid();
}

function Import(){

	var tmp = importBD($("#rtu_id").val(),$("#zjg_id").val());
	if(!tmp){
		return;
	}else{
		if(tmp.td_bdinf == ""){
			$("#bd").html("未录入表底信息");
		}else{
			rtnBD = tmp
//			$("#zbd").val(rtnBD.zbd);
//			$("#jbd").val(rtnBD.jbd);
//			$("#fbd").val(rtnBD.fbd);
//			$("#pbd").val(rtnBD.pbd);
//			$("#gbd").val(rtnBD.gbd);
			var zdl=0.0;
			zdl = zdl + parseFloat(isNaN(rtnBD.zbd0) ? 0 : rtnBD.zbd0);
			zdl = zdl + parseFloat(isNaN(rtnBD.zbd1) ? 0 : rtnBD.zbd1);
			zdl = zdl + parseFloat(isNaN(rtnBD.zbd2) ? 0 : rtnBD.zbd2);
			rtnzbd = round(zdl,def.point);
			
			$("#bd").html(rtnBD.td_bdinf);
			
		//	$("#new_feeproj").val(rtnValue.feeproj_id);
			
			calcu_bd_byid();
			$("#chgfl").attr("disabled",false);
			$("#savefl").attr("disabled",false);
		}
	}
}

var last_sd_bd = $("#shutdown_bd").html() == "" ? 0 : $("#shutdown_bd").html();

function calcu_bd_byid(){

	var value = $("#new_feeproj").val();
	for ( var i = 0; i < feeproj_data.rows.length; i++) {
		if(value == feeproj_data.rows[i].id){
			calcu_bd(feeproj_data.rows[i].rate);
			break;
		}
	}
}

function calcu_bd(dj){
	
	var zbd = rtnzbd;//$("#zbd").val();
	if(zbd === "")return;
	
	var shutdown_bd = $("#last_shutdown_bd").val()===""?0:$("#last_shutdown_bd").val();
	var blv = rtnValue.blv;
	
	$("#syje").html(round((shutdown_bd - zbd) * blv * rtnValue.feeproj_reteval, def.point));
			
//	if(dj == undefined){
//		dj 	= rtnValue.feeproj_reteval;
//		$("#syje").html(round((shutdown_bd - zbd) * blv * dj, def.point));
//	}
	var zje = $("#syje").html();
	var alarm_val = rtnValue.yffalarm_alarm1;
	
	var ret_val = bd_calcu(zje, dj, blv, zbd, alarm_val, alarm_type, last_sd_bd);
	
	var tmp = $("#shutdown_bd").html() == "" ? 0 : $("#shutdown_bd").html();
	
	if(tmp != "" && tmp == last_sd_bd){
		last_sd_bd = $("#shutdown_bd").html() == "" ? 0 : $("#shutdown_bd").html();
	}
	
	$("#buy_dl").html(ret_val.buy_dl);
	$("#bmc").html(ret_val.pay_bmc);
	$("#shutdown_bd").html(ret_val.shutdown_bd);
	$("#alarm_bd").val(ret_val.alarm_code);
}

function selcons(){//检索
	var tmp = doSearch("bd",ComntUseropDef.YFF_GYOPER_CHANGERATE, rtnValue);
	if(!tmp){
		return;
	}
	
	rtnValue = tmp;
	
	setConsValue(rtnValue);
	
	$("#last_shutdown_bd").val(rtnValue.shutdown_val);
	$("#new_feeproj").val(rtnValue.feeproj_id);
	chg_feeproj($("#new_feeproj").val());
	
	getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
	getAlarmValue(rtnValue.yffalarm_id);	//获取报警方式
	
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
	
	$("#rtuInfo").attr("disabled",false);
	$("#btnImportBD").attr("disabled",false);
	$("#chgfl").attr("disabled",true);
	$("#savefl").attr("disabled",true);
	$("#bd").html("");
	setTextDisabled(false);
	
}