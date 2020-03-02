//换表
var rtnValue;
var	mygrid;
var mp_id;
var rtnBDNew = null;
var rtnBDOld = null;

$(document).ready(function(){

	$("#btnSearch").click(function(){selcons()});
	$("#btnChgmeter").click(function(){chgMeter()});

	$("#rtnBDNew").click(function(){Import("new")});
	$("#rtnBDOld").click(function(){Import("old")});
	
	$("#chg_type").change(function(){chg_type(this.value);})
	$("#chg_meter").change(function(){chg_meter(this.value);})
	
	//ct信息
	$("#ctfz").attr("readonly",true).css("background","#ccc").keyup(function(){if(isNaN(this.value)){this.value = 1;};calcCT(this.value)});
	$("#ctfm").attr("readonly",true).css("background","#ccc").keyup(function(){if(isNaN(this.value)){this.value = 1;};calcCT(this.value)});
	
	//pt信息
	$("#ptfz").attr("readonly",true).css("background","#ccc").keyup(function(){if(isNaN(this.value)){this.value = 1;};calcPT(this.value)});
	$("#ptfm").attr("readonly",true).css("background","#ccc").keyup(function(){if(isNaN(this.value)){this.value = 1;};calcPT(this.value)});
	
	$("#mckd").keyup(function(){if(isNaN(this.value))this.value = 0;});
	
	initChgTime();
	initChgType();
	initGrid();
	
	IsDisabled()
	
});

function initChgType(){
	var option = "<option value="+SDDef.YFF_CHGMETER_MT+">更换电表</option><option value="+SDDef.YFF_CHGMETER_CT+">更换CT</option><option value="+SDDef.YFF_CHGMETER_PT+">更换PT</option>"
	$("#chg_type").html(option);
}

function calcCT(value){
	if(isNaN(value)) {
		return;
	}
	if($("#ctfm").val() === "" || $("#ctfm").val() === "0"){
		$("#ct").html("");
		return;
	}
	
	$("#ct").html(round(parseFloat($("#ctfz").val())/parseFloat($("#ctfm").val()),2));
}

function calcPT(value){
	if(isNaN(value)) {
		return;
	}
	
	if($("#ptfm").val() === "" || $("#ptfm").val() === "0"){
		$("#pt").html("");
		return;
	}
	
	$("#pt").html(round(parseFloat($("#ptfz").val())/parseFloat($("#ptfm").val()),2));
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

function Import(bdflag){
	var tmp = importBD($("#rtu_id").val(),$("#zjg_id").val());
	
	if (!tmp) {
		return;
	}
	else{
		if (bdflag == "old") {
		    rtnBDOld = tmp;
		    $("#bd_old").html(rtnBDOld.td_bdinf);
		}
		if (bdflag == "new") {
			rtnBDNew = tmp;
			$("#bd_new").html(rtnBDNew.td_bdinf);
			$("#btnChgmeter" ).attr("disabled",false);
		}
	}
}

function selcons(){//检索
	tmp = doSearch("zz",ComntUseropDef.YFF_GYOPER_CHANGEMETER, rtnValue);
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	setConsValue(rtnValue);
	//获取报警方式
	getAlarmValue();
	//剩余金额
	$("#syje").html(round(rtnValue.now_remain,2));
	
	//报警值,上次的报警值,和缴费的报警值(根据总金额计算)不一样
	$("#yffalarm_alarm1").val(rtnValue.alarm_val1);
	$("#yffalarm_alarm2").val(rtnValue.alarm_val2);
	getGyYffRecs();
	
	$("#rtnBDOld").attr("disabled",false);
	$("#rtnBDNew").attr("disabled",false);
	
	initChgInfo();
	UnDisabled();
	
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
}

var ptct_info;
function initChgInfo(){
	
	$("#chg_meter").html("");
	
	$.post(def.basePath + "ajaxspec/actConsPara!retChgMeterInfo.action",{result:$("#rtu_id").val(), zjgId : $("#zjg_id").val()},function(data) {			    	//回传函数
		if (data.result != "") {
			var json = eval('(' + data.result + ')');
			ptct_info = json;
			for(var i=0;i<json.rows.length;i++){
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

function initGrid(){
	mygrid = new dhtmlXGridObject('gridbox');
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

function chgMeter() {
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
	
	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.buynum 			= $("#buy_times").val();
	
	params.alarm_val1		= $("#yffalarm_alarm1").val();
	params.alarm_val2		= $("#yffalarm_alarm2").val();
	
	params.pay_money        = 0;
	params.othjs_money 		= 0;
	params.zb_money 		= 0;
	params.all_money 		= 0;
	
	params.alarmmoney  		= 0;
	params.buydl  			= 0;
	params.paybmc  			= 0;
	params.shutbd  			= 0;
	params.shutdown_bd  	= 0;
	params.alarmbd  		= 0;
	
	var chg_meter	= $("#chg_meter").val();
	var chg_time 	= $("#chg_time").val();
	
	params.chg_date = chg_time.substring(0, 4) + chg_time.substring(5, 7) + chg_time.substring(8,10);
	params.chg_time = chg_time.substring(12,14) + chg_time.substring(15,17) + "00";
	
	params.o_date	= rtnBDOld.date;
	params.o_time	= 0;
	params.n_date	= rtnBDNew.date;
	params.n_time	= 0;
	
	for(var i = 0; i < 3; i++){
		var rtnBD = rtnBDOld;
		eval("params.mp_id"  	+ i + "=rtnBD.mp_id" + i);
		
		if(rtnBD.zf_flag.substring(i, i + 1) == "0"){	//0正向
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
		}else{
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
			if(eval("chg_meter==rtnBD.mp_id" + i)){
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
		
		var rtnBD = rtnBDNew;
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
			if(eval("chg_meter==rtnBD.mp_id" + i)){
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
			if(eval("chg_meter==rtnBD.mp_id" + i)){
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
	
	params.chg_mpid			= $("#chg_meter").val();
	params.chg_type			= $("#chg_type").val();
	
	if($("#chg_type").val() == SDDef.YFF_CHGMETER_MT){	//换表
		//判断剩余金额是否合法 
		if($("#syje").html()<0){
			alert("输入金额中包含了无效字符");
			$("#syje").html("");
			return ;
		}
		
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
		
		var json_str = jsonString.json2String(params);
		chgMeter2(json_str, params.rtu_id, params.zjg_id);
		
	}
	else if($("#chg_type").val() == SDDef.YFF_CHGMETER_CT) {	//换ct
		
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
		
		var json_str = jsonString.json2String(params);
		
		chgMeter2(json_str,params.rtu_id,params.zjg_id);
		
	}
	else if($("#chg_type").val() == SDDef.YFF_CHGMETER_PT) {	//换pt
		
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
		
		var json_str = jsonString.json2String(params);
		
		chgMeter2(json_str,params.rtu_id,params.zjg_id);
	}
}

function chgMeter2(json_str,rtu_id,zjg_id) {
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
				alert("操作成功...");
				IsDisabled();
				getGyYffRecs();
				$("#btnChgmeter").attr("disabled",true);
				
			}
			else {
				alert("操作失败...");
			}
		});
}

//刚刚进入界面  各个input默认disabled
function IsDisabled(){
	$("#chg_type").attr("disabled",true);
	$("#jfje").attr("disabled",true);
	$("#yffalarm_alarm1").attr("disabled",true);
	$("#yffalarm_alarm2").attr("disabled",true);
	$("#btnChgmeter").attr("disabled",true);
}

//selcons 各个input属性disabled更改为 false
function UnDisabled() {
	$("#bd_old").html("");
	$("#bd_new").html("");
	rtnBDOld = null;
	rtnBDNew = null;
	$("#chg_type").attr("disabled",false);
	$("#jfje").attr("disabled",false);
	$("#yffalarm_alarm1").attr("disabled",false);
	$("#yffalarm_alarm2").attr("disabled",false);
	
	//每次让其重新初始化
	initGrid();
}