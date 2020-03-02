var rtnValue;
var	mygrid;
var mp_id;
var ptct_info;

var rtnBDNew = null;
var rtnBDOld = null;
var meterNum = null;//记录选择第几块电表

var ChangedFlag = false; //换表标志

$(document).ready(function(){
	
	$("#btnSearch").click(function(){selcons()});
	$("#btnChgmeter").click(function(){chgMeter()});
	
	$("#rtnBDNew").click(function(){Import("new")});
	$("#rtnBDOld").click(function(){Import("old")});
	
	
	$("#rtuInfo").click(function(){rtuInfo()});
	
	$("#chg_type").change(function(){chg_type(this.value);});
	$("#chg_meter").change(function(){chg_meter(this.value);});
	
	//ct信息
	$("#ctfz").attr("readonly",true).css("background","#ccc").keyup(function(){if(isNaN(this.value)){this.value = 1;};calcCT(this.value)});
	$("#ctfm").attr("readonly",true).css("background","#ccc").keyup(function(){if(isNaN(this.value)){this.value = 1;};calcCT(this.value)});
	
	//pt信息
	$("#ptfz").attr("readonly",true).css("background","#ccc").keyup(function(){if(isNaN(this.value)){this.value = 1;};calcPT(this.value)});
	$("#ptfm").attr("readonly",true).css("background","#ccc").keyup(function(){if(isNaN(this.value)){this.value = 1;};calcPT(this.value)});
	
	$("#syje").keyup(function(){if(isNaN(this.value)){this.value = 0;return};calcu(this.value);});
	
	initChgTime();
	initChgType();
	initGrid();
	
	setTextDisabled(true);
});


function initChgType(){
	var option = "<option value="+SDDef.YFF_CHGMETER_MT+">更换电表</option><option value="+SDDef.YFF_CHGMETER_CT+">更换CT</option><option value="+SDDef.YFF_CHGMETER_PT+">更换PT</option>"
	$("#chg_type").html(option);
}

function calcCT(value){
	if(isNaN(value)){
		return;
	}
	if($("#ctfm").val() === "" || $("#ctfm").val() === "0"){
		$("#ct").html("");
		return;
	}
	
	$("#ct").html(round(parseFloat($("#ctfz").val())/parseFloat($("#ctfm").val()),def.point));
}

function calcPT(value){
	if(isNaN(value)){
		return;
	}
	if($("#ptfm").val() === "" || $("#ptfm").val() === "0"){
		$("#pt").html("");
		return;
	}
	
	$("#pt").html(round(parseFloat($("#ptfz").val())/parseFloat($("#ptfm").val()),def.point));
}

function chg_type(value) {
	switch (parseInt(value)) {
		case SDDef.YFF_CHGMETER_MT:
			$("#ctfz").attr("readonly", true).css("background","#ccc");
			$("#ctfm").attr("readonly", true).css("background","#ccc");
	
			$("#ptfz").attr("readonly", true).css("background","#ccc");
			$("#ptfm").attr("readonly", true).css("background","#ccc");
			break;
		case SDDef.YFF_CHGMETER_CT:
			$("#ctfz").attr("readonly", false).css("background","white");
			$("#ctfm").attr("readonly", false).css("background","white");
	
			$("#ptfz").attr("readonly", true).css("background","#ccc");
			$("#ptfm").attr("readonly", true).css("background","#ccc");
			break;
		case SDDef.YFF_CHGMETER_PT:
			$("#ctfz").attr("readonly", true).css("background","#ccc");
			$("#ctfm").attr("readonly", true).css("background","#ccc");
	
			$("#ptfz").attr("readonly", false).css("background","white");
			$("#ptfm").attr("readonly", false).css("background","white");
			break;
		default:
			break;
	}
}

function chg_meter(value){
	for ( var i = 0; i < ptct_info.rows.length; i++) {
		if(ptct_info.rows[i].value == value){
			
			$("#ctfz").val(ptct_info.rows[i].ctfz);
			$("#ctfm").val(ptct_info.rows[i].ctfm);
			$("#ct").html(ptct_info.rows[i].ct);
		
			$("#ptfz").val(ptct_info.rows[i].ptfz);
			$("#ptfm").val(ptct_info.rows[i].ptfm);
			$("#pt").html(ptct_info.rows[i].pt);
			
			break;
		}
	}
}

function initChgTime(){
	var date = new Date();
	$("#chg_time").val(date.Format("yyyy年MM月dd日 HH时mm分").toString());
}

function setTextDisabled(mode){	//设置文本框状态（disabled/enabled）
	if(!mode){
		$("#syje").val("");
		$("#yffalarm_alarm1").val("");
		$("#yffalarm_alarm2").val("");
		
		$("#zbd1").val("");
		$("#jbd1").val("");
		$("#fbd1").val("");
		$("#pbd1").val("");
		$("#gbd1").val("");
		$("#bd1").html("");
		
		$("#zbd2").val("");
		$("#jbd2").val("");
		$("#fbd2").val("");
		$("#pbd2").val("");
		$("#gbd2").val("");
		$("#bd2").html("");
		$("#bd_old").html("");
		$("#bd_new").html("");
	}
	
	$("#syje").attr("disabled",mode);
	$("#yffalarm_alarm1").attr("disabled",mode);
	$("#yffalarm_alarm2").attr("disabled",mode);
}

function Import(bdflag){
	var tmp = importBD($("#rtu_id").val(),$("#zjg_id").val());
	
	if (!tmp) {
		return;
	}
	else{
		if (bdflag == "old") {
		    rtnBDOld = tmp;
		    $("#rtnBDNew").attr("disabled",false);
		    $("#bd_old").html(rtnBDOld.td_bdinf);
		}
		if (bdflag == "new") {
			rtnBDNew = tmp;
			$("#bd_new").html(rtnBDNew.td_bdinf);
			$("#rtuInfo").attr("disabled",false);
		}
	}
}

function selcons(){//检索
	var tmp = doSearch("je",ComntUseropDef.YFF_GYCOMM_PAY, rtnValue);
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	
	setConsValue(rtnValue);
	
	getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id); 
	
	getAlarmValue(rtnValue.yffalarm_id);//获取报警方式
	
	$("#rtuInfo").attr("disabled",true);
	 $("#rtnBDNew").attr("disabled",true);
	$("#rtnBDOld").attr("disabled",false);
	
	setTextDisabled(false);
	rtnBDNew = null;
	rtnBDOld = null;
	ChangedFlag = false;
	
	if (rtnValue.prot_type == ComntDef.YD_PROTOCAL_KEDYH) {
		$("#ctrlpara_title").attr("style","display:block;");
		$("#ctrlpara_content").attr("style","display:block;");
		$("#ctrlpara_butt").attr("style","display:block;");
	}
	else {
		$("#ctrlpara_title").attr("style","display:none;");
		$("#ctrlpara_content").attr("style","display:none;");
		$("#ctrlpara_butt").attr("style","display:none;");
		
	}
	
	initChgInfo();
	
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
}

function initChgInfo(){
	
	$("#chg_meter").html("");
	
	$.post(def.basePath + "ajaxspec/actConsPara!retChgMeterInfo.action",{result:$("#rtu_id").val(), zjgId : $("#zjg_id").val()},function(data) {			    	//回传函数
		if (data.result != "") {
			var json = eval('(' + data.result + ')');
			ptct_info = json;
			for(var i = 0; i < json.rows.length; i++){
				$("#chg_meter").append("<option value="+json.rows[i].value+">"+json.rows[i].text+"</option>");
				if(i == 0){
					mp_id = json.rows[i].value;
					$("#ctfz").val(json.rows[i].ctfz);
					$("#ctfm").val(json.rows[i].ctfm);
					$("#ct").html(json.rows[i].ct);
					$("#ptfz").val(json.rows[i].ptfz);
					$("#ptfm").val(json.rows[i].ptfm);
					$("#pt").html(json.rows[i].pt);
				}
			}
		}			
	});
}

function rtuInfo(){		//终端内信息-type bd或者je-
	modalDialog.width = 750;
	modalDialog.height = 190;
	modalDialog.url = "../dialog/callRemain.jsp";
	modalDialog.param = {
						type 			: "je", 
						rtuid 			: $("#rtu_id").val(), 
						zjgid 			: $("#zjg_id").val(), 
						userno 			: $("#userno").html(), 
						username 		: $("#username").html(),
						buytimes 		: $("#buy_times").val(),
						blv				: $("#blv").html(),
						dj				: $("#feeproj_reteval").val(),
						cacl_type_desc	: $("#cacl_type_desc").html(),
						prot_type		: rtnValue.prot_type
						};
	var o = modalDialog.show();
	if(!o)return;
	
	if (!ChangedFlag) $("#btnChgmeter").attr("disabled",false);
	$("#syje").val(o.nowval);
	calcu(o.nowval);
}


function calcu(value){
	if(isNaN(value)){
		return;
	}
	setAlarmValue(rtnValue.yffalarm_alarm1, rtnValue.yffalarm_alarm2, value);
}

function chgMeter(){
	if(rtnBDOld == null) {
		alert("请录入旧表底...");
		return;
	}
	
	if(rtnBDNew == null){
		alert("请录入新表底...");
		return;
	}
	
	if($("#chg_time").val() == ""){
		alert("请填写更换时间...");
		return;
	}
	
	if(!confirm("确认要换表/换倍率操作吗？")){
		return;
	}
	
	ChangedFlag = true;
	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.buynum 			= $("#buy_times").val();
	
	params.yffalarm_id 		= $("#yffalarm_id").val();
	
	params.alarm_val1		= $("#yffalarm_alarm1").val();
	params.alarm_val2		= $("#yffalarm_alarm2").val();
	
	params.pay_money 		= $("#syje").val();
	params.othjs_money 		= 0;
	params.zb_money 		= 0;
	params.all_money 		= params.pay_money;
	
	params.alarmmoney  		= $("#yffalarm_alarm1").val();
	params.buydl  			= 0;
	params.paybmc  			= 0;
	params.shutbd  			= 0;
	params.shutdown_bd  	= params.pay_money;
	params.alarmbd  		= 0;
	params.yffflag			= $("#start_yff_flag").attr('checked') ? 1 : 0;
	
	if ($("#dpfs").attr('checked')) {
		params.plustime		= 0;
	}else {
		params.plustime		= $("#plus_width").val() === "" ? 0 : $("#plus_width").val();
	}
	var chg_meter	= $("#chg_meter").val();
	var chg_time 	= $("#chg_time").val();
	
	params.chg_date = chg_time.substring(0,4) + chg_time.substring(5,7) + chg_time.substring(8,10);
	params.chg_time = chg_time.substring(12,14) + chg_time.substring(15,17) + "00";
	params.o_date	= rtnBDOld.date;
	params.o_time	= 0;
	params.n_date	= rtnBDNew.date;
	params.n_time	= 0;
	
	for(var i = 0; i < 3; i++){
		var rtnBD = rtnBDOld;
		eval("params.mp_id"  	+ i + "=rtnBD.mp_id" + i);
		
		if(rtnBD.zf_flag.substring(i, i + 1) == "0"){	//正向
			eval("params.bd_zy_o"  + i + "=rtnBD.zbd" + i);
			eval("params.bd_zyj_o" + i + "=rtnBD.jbd" + i);
			eval("params.bd_zyf_o" + i + "=rtnBD.fbd" + i);
			eval("params.bd_zyp_o" + i + "=rtnBD.pbd" + i);
			eval("params.bd_zyg_o" + i + "=rtnBD.gbd" + i);
			eval("params.bd_zw_o"  + i + "=rtnBD.wbd" + i);
			
			eval("params.bd_fy_o"  + i + "=0");
			eval("params.bd_fyj_o" + i + "=0");
			eval("params.bd_fyf_o" + i + "=0");
			eval("params.bd_fyp_o" + i + "=0");
			eval("params.bd_fyg_o" + i + "=0");
			eval("params.bd_fw_o"  + i + "=0");
			
			//确定选择的第几块电表

			if(eval("chg_meter==rtnBD.mp_id" + i)){
				eval("params.bd_zy_o =  rtnBD.zbd" + i);
				eval("params.bd_zyj_o = rtnBD.jbd" + i);
				eval("params.bd_zyf_o = rtnBD.fbd" + i);
				eval("params.bd_zyp_o = rtnBD.pbd" + i);
				eval("params.bd_zyg_o = rtnBD.gbd" + i);
				eval("params.bd_zw_o  = rtnBD.wbd" + i);
				
				eval("params.bd_fy_o =  0");
				eval("params.bd_fyj_o = 0");
				eval("params.bd_fyf_o = 0");
				eval("params.bd_fyp_o = 0");
				eval("params.bd_fyg_o = 0");
				eval("params.bd_fw_o  = 0");
			}
		}
		else{
			eval("params.bd_zy_o"  + i + "= 0");
			eval("params.bd_zyj_o" + i + "= 0");
			eval("params.bd_zyf_o" + i + "= 0");
			eval("params.bd_zyp_o" + i + "= 0");
			eval("params.bd_zyg_o" + i + "= 0");
			eval("params.bd_zw_o"  + i + "= 0");
			
			eval("params.bd_fy_o"  + i + "= rtnBD.zbd" + i);
			eval("params.bd_fyj_o" + i + "= rtnBD.jbd" + i);
			eval("params.bd_fyf_o" + i + "= rtnBD.fbd" + i);
			eval("params.bd_fyp_o" + i + "= rtnBD.pbd" + i);
			eval("params.bd_fyg_o" + i + "= rtnBD.gbd" + i);
			eval("params.bd_fw_o"  + i + "= rtnBD.wbd" + i);
			
			//确定选择的第几块电表
			if(eval("chg_meter == rtnBD.mp_id" + i)){
				eval("params.bd_zy_o =  0");
				eval("params.bd_zyj_o = 0");
				eval("params.bd_zyf_o = 0");
				eval("params.bd_zyp_o = 0");
				eval("params.bd_zyg_o = 0");
				eval("params.bd_zw_o  = 0");
				
				eval("params.bd_fy_o =  rtnBD.zbd" + i);
				eval("params.bd_fyj_o = rtnBD.jbd" + i);
				eval("params.bd_fyf_o = rtnBD.fbd" + i);
				eval("params.bd_fyp_o = rtnBD.pbd" + i);
				eval("params.bd_fyg_o = rtnBD.gbd" + i);
				eval("params.bd_fw_o  = rtnBD.wbd" + i);
			}
		}
		
		var rtnBD=rtnBDNew;
		if(rtnBD.zf_flag.substring(i, i + 1) == "0") {	//正向
			eval("params.bd_zy_n"  + i + "=rtnBD.zbd" + i);
			eval("params.bd_zyj_n" + i + "=rtnBD.jbd" + i);
			eval("params.bd_zyf_n" + i + "=rtnBD.fbd" + i);
			eval("params.bd_zyp_n" + i + "=rtnBD.pbd" + i);
			eval("params.bd_zyg_n" + i + "=rtnBD.gbd" + i);
			eval("params.bd_zw_n"  + i + "=rtnBD.wbd" + i);
			
			eval("params.bd_fy_n"  + i + "=0");
			eval("params.bd_fyj_n" + i + "=0");
			eval("params.bd_fyf_n" + i + "=0");
			eval("params.bd_fyp_n" + i + "=0");
			eval("params.bd_fyg_n" + i + "=0");
			eval("params.bd_fw_n"  + i + "=0");
			
			//确定选择的第几块电表
			if(eval("chg_meter == rtnBD.mp_id" + i)){
				eval("params.bd_zy_n =  rtnBD.zbd" + i);
				eval("params.bd_zyj_n = rtnBD.jbd" + i);
				eval("params.bd_zyf_n = rtnBD.fbd" + i);
				eval("params.bd_zyp_n = rtnBD.pbd" + i);
				eval("params.bd_zyg_n = rtnBD.gbd" + i);
				eval("params.bd_zw_n  = rtnBD.wbd" + i);
				
				eval("params.bd_fy_n =  0");
				eval("params.bd_fyj_n = 0");
				eval("params.bd_fyf_n = 0");
				eval("params.bd_fyp_n = 0");
				eval("params.bd_fyg_n = 0");
				eval("params.bd_fw_n  = 0");
			}
		}
		else {
			eval("params.bd_zy_n"  + i + "= 0");
			eval("params.bd_zyj_n" + i + "= 0");
			eval("params.bd_zyf_n" + i + "= 0");
			eval("params.bd_zyp_n" + i + "= 0");
			eval("params.bd_zyg_n" + i + "= 0");
			eval("params.bd_zw_n"  + i + "= 0");
			eval("params.bd_fy_n"  + i + "= rtnBD.zbd" + i);
			eval("params.bd_fyj_n" + i + "= rtnBD.jbd" + i);
			eval("params.bd_fyf_n" + i + "= rtnBD.fbd" + i);
			eval("params.bd_fyp_n" + i + "= rtnBD.pbd" + i);
			eval("params.bd_fyg_n" + i + "= rtnBD.gbd" + i);
			eval("params.bd_fw_n"  + i + "= rtnBD.wbd" + i);
			
			//确定选择的第几块电表
			if(eval("chg_meter == rtnBD.mp_id" + i)){
				eval("params.bd_zy_n =  0");
				eval("params.bd_zyj_n = 0");
				eval("params.bd_zyf_n = 0");
				eval("params.bd_zyp_n = 0");
				eval("params.bd_zyg_n = 0");
				eval("params.bd_zw_n  = 0");
				
				eval("params.bd_fy_n =  rtnBD.zbd" + i);
				eval("params.bd_fyj_n = rtnBD.jbd" + i);
				eval("params.bd_fyf_n = rtnBD.fbd" + i);
				eval("params.bd_fyp_n = rtnBD.pbd" + i);
				eval("params.bd_fyg_n = rtnBD.gbd" + i);
				eval("params.bd_fw_n  = rtnBD.wbd" + i);
			}
		}
	}
	
	params.chg_mpid		= $("#chg_meter").val();
	params.chg_type		= $("#chg_type").val();
	
	if($("#chg_type").val() == SDDef.YFF_CHGMETER_MT){	//换表
		
		params.oldct_n = $("#ctfz").val();
		params.oldct_d = $("#ctfm").val();
		params.oldct_r = $("#ct").html();
		params.newct_n = params.oldct_n;
		params.newct_d = params.oldct_d;
		params.newct_r = params.oldct_r;
		
		params.oldpt_n = $("#ptfz").val();
		params.oldpt_d = $("#ptfm").val();
		params.oldpt_r = $("#pt").html();
		params.newpt_n = params.oldpt_n;
		params.newpt_d = params.oldpt_d;
		params.newpt_r = params.oldpt_r;
	}
	else if($("#chg_type").val() == SDDef.YFF_CHGMETER_CT){	//换ct
		
		var m_id = $("#chg_meter").val();
		
		for ( var i = 0; i < ptct_info.rows.length; i++) {
			
			if(ptct_info.rows[i].value == m_id){
				
				params.oldct_n = ptct_info.rows[i].ctfz;
				params.oldct_d = ptct_info.rows[i].ctfm;
				params.oldct_r = ptct_info.rows[i].ct;
				
				break;
			}
		}
		
		params.newct_n = $("#ctfz").val();
		params.newct_d = $("#ctfm").val();
		params.newct_r = $("#ct").html();  
		
		params.oldpt_n = $("#ptfz").val();
		params.oldpt_d = $("#ptfm").val();
		params.oldpt_r = $("#pt").html();
		params.newpt_n = params.oldpt_n;
		params.newpt_d = params.oldpt_d;
		params.newpt_r = params.oldpt_r;

	}
	else if($("#chg_type").val() == SDDef.YFF_CHGMETER_PT){	//换pt
		
		params.oldct_n = $("#ctfz").val();
		params.oldct_d = $("#ctfm").val();
		params.oldct_r = $("#ct").html();
		params.newct_n = params.oldct_n;
		params.newct_d = params.oldct_d;
		params.newct_r = params.oldct_r;
		
		var m_id = $("#chg_meter").val();
		
		for ( var i = 0; i < ptct_info.rows.length; i++) {
			
			if(ptct_info.rows[i].value == m_id){
				
				params.oldpt_n = ptct_info.rows[i].ptfz;
				params.oldpt_d = ptct_info.rows[i].ptfm;
				params.oldpt_r = ptct_info.rows[i].pt;
				
				break;
			}
		}
		
		params.newpt_n = $("#ptfz").val();
		params.newpt_d = $("#ptfm").val();
		params.newpt_r = $("#pt").html();
	}
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	
	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommGy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_GYCOMM_PAY;
	//end
	
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post(def.basePath + "ajaxoper/actCommGy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag 	: true,
			userData1		: params.rtu_id,
			zjgid			: params.zjg_id,
			gyCommPay 		: json_str,
			userData2 		: ComntUseropDef.YFF_GYCOMM_PAY
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				chgMeter2(json_str,params.rtu_id,params.zjg_id);
				
			}else{
				window.top.addStringTaskOpDetail("接收预付费服务任务失败.");
				alert("通讯失败...");
			}
		}
	);
}

function chgMeter2(json_str,rtu_id,zjg_id){
	loading.loading();
	$.post(def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
		{
			userData1			: rtu_id,
			zjgid				: zjg_id,
			gsmflag				: 0,
			gyOperChangeMeter	: json_str,
			userData2			: ComntUseropDef.YFF_GYOPER_CHANGEMETER
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				window.top.addStringTaskOpDetail("接收预付费服务任务成功.");
				alert("操作成功...");
				getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id); 
				setTextDisabled(true);
				$("#btnChgmeter").attr("disabled",true);
			}
			else {
				window.top.addStringTaskOpDetail("终端通讯成功,但存库操作失败...");
				alert("操作失败...");
			}
		});
}