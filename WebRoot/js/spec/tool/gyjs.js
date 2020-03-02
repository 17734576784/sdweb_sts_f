//缴费
var jsonCons   = null;
var matchInfo  = ["有匹配用户信息,可以结算。",
				  "没有找到匹配的档案。",
				  "有匹配用户信息。费控状态错误或未开户。",
				  "有匹配用户信息。无表底信息或信息不全。",
				  "有匹配用户信息。余额信息错误。"];

var matchFlag  = 0;//0：可结算; 其他: 不能结算
var rtnValue   = null;
var bdyg   	   = [0, 0, 0], bdwg = [0, 0, 0], 
	mis_bdj	   = [0, 0, 0], mis_bdf = [0, 0, 0],
	mis_bdp    = [0, 0, 0], mis_bdg = [0, 0, 0];
var mp_id;
var clpNum 	   = 0;
var jsDate 	   = 0; //抄表日yyyymmdd：excel的yyyymm + zjgpay_para中的cb_dayhour的dd
var jsTime 	   = 0; //抄表日hhmm ：zjgpay_para中的cb_dayhour的hh + "00"
var cbDayYe    = 0; //抄表日余额:excel中的余额。
var zfFlag	   = "";
var curbd_info = new Array();	//终端召测当前表底
var remain_info = null;			//终端召测余额信息


var mis_jsflag = false;											//计算出的与实际用的是否相同的标志
var mis_jsje = 0, mis_jsbmc = 0, mis_gdl = 0;					//实际用的
var mis_calcu_val = 0, mis_calcu_gdl = 0, mis_calcu_bmc = 0;	//计算出的

var cacl_type, fee_type;		//计费方式,费控方式

var last_sel_id = -1;

$(document).ready(function(){

	initGridCons();
	initCbym();
	initGrid();
	
	$("#upload_bdye").click(function(){showUpload()});
	$("#btnRemain").click(function(){remainSum()});
	$("#btnRemain_bd").click(function(){remainSum()});
	$("#btnPay").click(function(){
		if(check("je")){
		    payMoney();
		}
	});
	$("#btnPay_bd").click(function(){
		if(check("bd")){
		    payMoney();
		}
	});

	$("#jsje").keyup(function(){calcu()});
	$("#jsje_bd").keyup(function(){calcu_bd()});
});

function initCbym() {
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	if(month < 10) month = "0" + month;
	$("#cb_ym").val(year + "年" + month + "月");
}

function getFK() {
	if(cacl_type == 1 && fee_type == 1) {			//主站费控
		return "zz";
	} else if(cacl_type == 1 && fee_type == 0) {	//金额
		return "je";
	} else if(cacl_type == 2 && fee_type == 0) {	//表底
		return "bd";
	}
}

function initGridCons(){
	var yff_grid_title ='用&nbsp;户&nbsp;列&nbsp;表&nbsp;&nbsp;';
	mygridCons.setImagePath(def.basePath + "images/grid/imgs/");
	mygridCons.setHeader(yff_grid_title);
	mygridCons.setInitWidths("198");
	mygridCons.setColAlign("left");
	mygridCons.setColTypes("ro");
	mygridCons.enableTooltips("false");
	mygridCons.enableSmartRendering(true);
	mygridCons.init();
	mygridCons.attachEvent("onRowSelect", selOneCons);
	mygridCons.setSkin("light");
}

function parsegrid_spec(resultdata){
	jsonCons = eval('(' + resultdata + ')');
	mygridCons.clearAll();
	mygridCons.parse(jsonCons, "", "json");
	mygridCons.selectRow(0);
	selOneCons(0);
}

//用户列表单击一行
function selOneCons(id){
	var row_num = mygridCons.getRowsNum();
	if (last_sel_id == id && parseInt(row_num) > 1) return;
	
	last_sel_id = id;
	
	$("#jsje").val("");
	$("#jsje_bd").val("");
	$("#alarm_code").val("");
	$("#buy_times").html("");
	$("#buy_times_bd").html("");
	$("#buy_dl").html("");
	$("#pay_bmc").html("");
	$("#shutdown_bd").html("");
	$("#now_remain").html("");
	$("#ddbd").html("");
	$("#sfbd_sj").html("");
	$("#sfbd").html("");
	$("#zje").val("");
	$("#zje_bd").html("");
	$("#yffalarm_alarm1").val("");
	$("#yffalarm_alarm2").val("");
	$("#rtuonline_img").hide();
	$("#rtuonline_sp").html("");
	
	mygridYff.clearAll();
	
	$("#btnPay").attr("disabled",true);
	$("#btnPay_bd").attr("disabled",true);
	
	$("#cons_no").val(jsonCons.rows[id].data[1]);		//用户编号
	$("#rtu_id").val(jsonCons.rows[id].data[2]);		//终端
	$("#zjg_id").val(jsonCons.rows[id].data[3]);		//总加组
	
	bdyg  	  = jsonCons.rows[id].data[4].split("_");	//mis有功总表底
	bdwg   	  = jsonCons.rows[id].data[5].split("_");	//mis无功总表底
	cbDayYe   = jsonCons.rows[id].data[6];				//mis余额
	matchFlag = parseInt(jsonCons.rows[id].data[7]);	//匹配标志
	clpNum    = jsonCons.rows[id].data[8];				//测量点数
	var tmp_mp= jsonCons.rows[id].data[9];
	mp_id     = tmp_mp.split("_");						//测点号
	zfFlag    = jsonCons.rows[id].data[10];				//正反相
	cacl_type = jsonCons.rows[id].data[11];
	fee_type  = jsonCons.rows[id].data[12];
	
	var spanCons = "";
	spanCons += "用户名：" 	 + jsonCons.rows[id].data[0]; 
	spanCons += "，户号：" 	 + jsonCons.rows[id].data[1];
	spanCons += "，预收金额：" + jsonCons.rows[id].data[6] + "元";
	spanCons += "，<br/>有功(总)：" + bdyg[0];
	
	if(parseFloat(bdyg[1]) > 0)spanCons += "、" + bdyg[1];
	if(parseFloat(bdyg[2]) > 0)spanCons += "、" + bdyg[2];
	spanCons += "，无功(总)：" + bdwg[0];
	if(parseFloat(bdwg[1]) > 0)spanCons += "、" + bdwg[1];
	if(parseFloat(bdwg[2]) > 0)spanCons += "、" + bdwg[2];
	spanCons += "。" + matchInfo[matchFlag];
	$("#spanConsInfo").html(spanCons);
	
	var fk = getFK();
	switch(fk) {
	case "zz":
	case "je":
		$("#tabinfo_je").show();
		$("#tabinfo_bd").hide();
		break;
	case "bd":
		$("#tabinfo_je").hide();
		$("#tabinfo_bd").show();
		break;
	default :
		break;
	}
	
	search();
}

//显示弹出窗口--上传页面
function showUpload(){
	modalDialog.height = 300;
	modalDialog.width  = 400;
	modalDialog.url    = def.basePath + "jsp/spec/dialog/uploadSG186BdYe.jsp";
	var o = modalDialog.show();
	
	if(!o && o!= ""){
		return;
	}
	
	parsegrid_spec(o);
}

//显示用户信息
function setValue2Jsp(){
	setConsValue(rtnValue);
	$("#buy_times").html(rtnValue.buy_times);
	$("#buy_times_bd").html(rtnValue.buy_times);
	$("#now_remain").html(round(rtnValue.now_remain, def.point));

	getGyYffRecs($("#rtu_id").val(), $("#zjg_id").val());
	
	getAlarmValue(rtnValue.yffalarm_id);
	getMoneyLimit(rtnValue.feeproj_id);
	
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
}


function isConsValid()
{
	if (matchFlag != 0) return false;	
	if ($("#rtu_id").val() == undefined || 
		$("#zjg_id").val() == undefined || 
		$("#rtu_id").val() == "" || 
		$("#zjg_id").val() == "" ) return false;
	return true;
}

//检索用户信息
function search(){
	$("#btnRemain").attr("disabled",true);
	$("#btnRemain_bd").attr("disabled",true);
	
	if (!isConsValid()) {
		setConsValue("",true);
		mygrid.clearAll();
		return;
	}

	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", //后台处理程序
		{
			userData1		: $("#rtu_id").val(),
			zjgid 			: $("#zjg_id").val(),
			gsmflag 		: 0,
			userData2		: ComntUseropDef.YFF_GYOPER_GPARASTATE
		},
		function(data) {			    	//回传函数			
			loading.loaded();
			if (data.result == SDDef.SUCCESS) {
				rtnValue = eval("(" + data.gyOperGParaState + ")");
				
				//检索用户信息--查数据库                                               
				var params = {};	
				params.rtu_id 	= $("#rtu_id").val();
				params.zjg_id 	= $("#zjg_id").val();
				var jsonstr = jsonString.json2String(params);
				$.post( def.basePath + "ajaxspec/actSG186JSExcel!getOneCons.action",{result:jsonstr}, function(data) {			    	//回传函数
					if (data.result != "") {
						var json = eval('('+data.result+')');
						
						rtnValue.cons_id 	 = json.cons_id;
						rtnValue.fee_unit 	 = json.fee_unit;
						rtnValue.username 	 = json.username;
						rtnValue.userno 	 = json.userno;
						rtnValue.useraddr	 = json.useraddr;
						rtnValue.tel 		 = json.tel;
						rtnValue.factory	 = json.factory;
						rtnValue.blv 		 = json.blv;
						rtnValue.rtu_model	 = json.rtu_model;
						rtnValue.prot_type	 = json.prot_type;
						rtnValue.switch_type = json.switch_type;
						rtnValue.plus_width	 = json.plus_width;
						rtnValue.jbf		 = json.jbf;
						rtnValue.plus_time	 = json.plus_time;
						if(json.cb_dayhour == "" || json.cb_dayhour == "null"){
							alert("没有配置抄表日，请先配置抄表日再结算。");
						}else{
							var dt = ("000" + json.cb_dayhour);
							dt = dt.substring(dt.length - 4,dt.length);
							jsDate = dt.substring(0,2); 
							jsTime = dt.substring(2,4);
							if(matchFlag == 0 && json.cus_state ==1)
							{
								$("#btnRemain").attr("disabled",false);
								$("#btnRemain_bd").attr("disabled",false);
							}
						}
						var online = json.online;
						rtnValue.onlinetxt	 ="<b style='color:#800000'>终端" + online + "</b>";
						if(online =="在线"){
							rtnValue.onlinesrc	 = def.basePath + "images/rtuzx_online.gif";
						}else{
							rtnValue.onlinesrc	 = def.basePath + "images/rtuzx_offline.gif";
						}
						setValue2Jsp();
					}
				});	
			}
			else {
				alert("查找电表信息错误..");
				setConsValue("",true);
			}
		}
	);
}

function check(type){
	
	if(!isDbl("jsje",  "结算金额" ,-rtnValue.moneyLimit, rtnValue.moneyLimit, false)){
		return false;
	}
	var zje = $("#zje").val();
	if(zje == 0 ){
		return(confirm("结算金额为0,不需要结算。是否继续"));
	}
	
	return true;
}

//向数据库中缴费
function pay(type) {
	var params = {};
	
	params.rtu_id 		= $("#rtu_id").val();
	params.zjg_id 		= $("#zjg_id").val();
	params.myffalarmid 	= $("#yffalarm_id").val();
	params.paytype 		= $("#pay_type").val();
	params.buynum 		= $("#buy_times").val();
	
	params.alarm_val1	= parseFloat($("#yffalarm_alarm1").val());
	params.alarm_val2	= parseFloat($("#yffalarm_alarm2").val());
	
	if(type == "zz" && !check_jsye(params.alarm_val1)) {
		return;
	}
	
	params.pay_money 	= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 	= $("#jsje").val()===""?0:$("#jsje").val();
	if(mis_jsflag) {
		params.othjs_money = mis_jsje;
		params.misjs_money = mis_calcu_val;
	}
	
	params.zb_money 	= $("#zbje").val()===""?0:$("#zbje").val();
	
	params.pay_money    = round(params.pay_money, def.point);
	params.othjs_money  = round(params.othjs_money, def.point);
	params.zb_money 	= round(params.zb_money, def.point);	

	params.all_money    = round(parseFloat(params.zb_money) + parseFloat(params.pay_money) + parseFloat(params.othjs_money),def.point);

	var showmis_jsje    = round(mis_jsje, def.point);

	params.date = 0;
	params.time = 0;

	params.buy_dl = 0;
	params.pay_bmc = 0;
	params.shutdown_bd = 0;

	if(type == "zz") {
		modalDialog.height = 300;
		modalDialog.width = 280;
		modalDialog.url = def.basePath + "jsp/dialog/info.jsp";
		modalDialog.param = {
			showGSM : false,
			key	: ["客户编号","用户名称", "缴费金额","追补金额","结算金额","总金额"],
			val	: [rtnValue.userno,rtnValue.username,params.pay_money,params.zb_money, mis_calcu_val+(showmis_jsje?"("+showmis_jsje+")":""),params.all_money]		
		};
		var o = modalDialog.show();
		if(!o||!o.flag){
			return;
		}
	}

	var t_str = $("#cb_ym").val();
	var str_cb_ym = t_str.substring(0,4) + t_str.substring(5,7); 
	var cb_ymd    = str_cb_ym + jsDate;
	
	params.fxdf_ym = str_cb_ym;
	params.cons_id = rtnValue.cons_id;
	params.cons_desc = $("#username").html();
	params.pay_type = rtnValue.pay_type;
	
	params.jsflag = 0;
	if(mis_jsflag) {
		params.jsflag = 1;
		if(type == "bd"){
			params.buy_dl   = mis_gdl;
			params.pay_bmc  = mis_jsbmc;
			params.misjs_bmc= mis_calcu_bmc;
			params.cur_bd   = $("#dqbd").html();
		} else {
			params.pay_bmc = 0;
			params.misjs_bmc = 0;
			params.cur_bd = 0;
		}
	} else {
		if(type == "bd") {
			params.buy_dl = $("#buy_dl").html();
			params.pay_bmc = $("#pay_bmc").html();
			params.misjs_bmc = $("#pay_bmc").html();
			params.cur_bd = $("#dqbd").html();
		} else {
			params.pay_bmc = 0;
			params.misjs_bmc = 0;
			params.cur_bd = 0;
		}
	}

	params.lastala_val1 = rtnValue.yffalarm_alarm1;
	params.lastala_val2 = rtnValue.yffalarm_alarm2;
	params.lastshut_val = rtnValue.shutdown_val;
	
	if(type == "bd") {
		params.newala_val1 = $("#alarm_code").val();
		params.newala_val2 = params.newala_val1;
		params.newshut_val = $("#shutdown_bd").html();
	} else {
		params.newala_val1 = rtnValue.yffalarm_alarm1;
		params.newala_val2 = rtnValue.yffalarm_alarm2;
		params.newshut_val = parseFloat(rtnValue.shutdown_val) + parseFloat(params.all_money);
	}
	
	params.date = cb_ymd;
	params.time = jsTime;
	
	for(var i = 0; i < 3; i++){
		eval("params.mp_id" + i + "=mp_id[" + i + "]");
		
		if(zfFlag.substring(i, i + 1) == "0"){	//正向
			eval("params.bd_zy"  + i + "=bdyg[" + i + "]");
			eval("params.bd_zyj" + i + "=0");
			eval("params.bd_zyf" + i + "=0");
			eval("params.bd_zyp" + i + "=0");
			eval("params.bd_zyg" + i + "=0");
			eval("params.bd_zw"  + i + "=bdwg[" + i + "]");
			eval("params.bd_fy"  + i + "=0");
			eval("params.bd_fyj" + i + "=0");
			eval("params.bd_fyf" + i + "=0");
			eval("params.bd_fyp" + i + "=0");
			eval("params.bd_fyg" + i + "=0");
			eval("params.bd_fw"  + i + "=0");
		}else{
			eval("params.bd_zy"  + i + "=0");
			eval("params.bd_zyj" + i + "=0");
			eval("params.bd_zyf" + i + "=0");
			eval("params.bd_zyp" + i + "=0");
			eval("params.bd_zyg" + i + "=0");
			eval("params.bd_zw"  + i + "=0");
			eval("params.bd_fy"  + i + "=bdyg[" + i + "]");
			eval("params.bd_fyj" + i + "=0");
			eval("params.bd_fyf" + i + "=0");
			eval("params.bd_fyp" + i + "=0");
			eval("params.bd_fyg" + i + "=0");
			eval("params.bd_fw"  + i + "=bdwg[" + i + "]");
		}
	}
	
	//params.type = type;
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
		{
			userData1		: params.rtu_id,
			zjgid 			: params.zjg_id,
			gsmflag 		: 0,
			gyOperRejs 		: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_REJS
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				alert("结算缴费成功!");
				getGyYffRecs($("#rtu_id").val(), $("#zjg_id").val());
				$("#buy_times").val(parseInt($("#buy_times").val())+1);
				$("#btnRemain").attr("disabled",true);
				$("#btnPay").attr("disabled",true);
				$("#btnPay_bd").attr("disabled",true);
			}
			else {
				alert(data.err_strinfo);
			}
		}
	);
}

function check_jsye(alarm_val1) {
	mis_jsflag = false;
	
	var othjs_money = $("#jsje").val()=== "" ? 0 : $("#jsje").val();
	
	//结算金额为负时，其绝对值需小于(当前金额-报警值)
	if(parseFloat(othjs_money) < 0) {
		var now_remain = $("#now_remain").html();
		if(now_remain == "") now_remain = 0;
		now_remain = parseFloat(now_remain);
		if(now_remain < alarm_val1) {
			alert("当前金额小于报警值1,不能结算");
			return false;
		}
		if(now_remain + parseFloat(othjs_money) < alarm_val1) {
			othjs_money = -(now_remain - parseFloat(alarm_val1));
			mis_jsje = othjs_money;
			mis_jsflag = true;
		}
	} else {
		var jsje = $("#jsje").val() == "" ? 0 : $("#jsje").val();
		jsje = round(jsje, def.point);
		if(mis_calcu_val != jsje) {
			mis_jsje = jsje;
			mis_jsflag = true;
		}
	}
	
	return true;
}

function check_jsye_bd(alarm_val1) {
	mis_jsflag = false;
	
	var othjs_money = $("#jsje").val()=== "" ? 0 : $("#jsje").val();

	if(parseFloat(othjs_money) < 0) {

		var cur_bd = parseFloat(remain_info.nowval);
		var bj_bd = parseFloat(remain_info.alarm_val1);
		if(cur_bd > bj_bd) {
			alert("当前表底大于报警表底,不能结算");
			return false;
		}
		
		var zbje = 0.0, jfje = 0.0;
	
		zbje = $("#zbje_bd").val() === "" ? 0 : $("#zbje_bd").val();
		jfje = $("#jfje_bd").val() === "" ? 0 : $("#jfje_bd").val();
		
		var zje = round(parseFloat(zbje) + parseFloat(jfje) + parseFloat(mis_calcu_val), def.point);
		var dj 	= rtnValue.feeproj_reteval;
		var blv = $("#blv").html() === "" ? 1 : $("#blv").html();
		var zbd = $("#ddbd").html() === "" ? 0 : $("#ddbd").html();
		var alarm_val = rtnValue.yffalarm_alarm1;
		var ret_val = bd_calcu(zje, dj, blv, zbd, alarm_val, alarm_type, zbd);

		if (ret_val.shutdown_bd < parseFloat($("#shutdown_bd").html())) {
			var jsje    = mis_calcu_val - (ret_val.shutdown_bd - parseFloat($("#shutdown_bd").html())) * parseFloat(rtnValue.blv) * parseFloat(rtnValue.feeproj_reteval);
			var bmc  	= jsje / parseFloat(rtnValue.blv) * parseFloat(rtnValue.feeproj_reteval);
			mis_jsje    = jsje;
			mis_jsbmc   = bmc;
			mis_gdl     = bmc * parseFloat(rtnValue.blv);
			mis_jsflag  = true;
		}
		/*if(ddzm == inter) {
			var bmc = ddzm - parseFloat(remain_info.totval);
			var jsje = -(bmc + cur_bd) * parseFloat(rtnValue.blv) * parseFloat(rtnValue.feeproj_reteval);
			jsje = -Math.floor(jsje);
			
			mis_jsje = jsje;
			mis_jsbmc = bmc;
			mis_gdl = bmc * parseFloat(rtnValue.blv);
			mis_jsflag = true;
		}*/
	} else {//正数时 不限制 结算金额
		var jsje = $("#jsje").val() == "" ? 0 : $("#jsje").val();
		jsje = round(jsje, def.point);
		
		if(mis_calcu_val != jsje) {
			
			mis_jsje = jsje;
			mis_jsbmc = jsje / (parseFloat(rtnValue.blv) * parseFloat(rtnValue.feeproj_reteval));
			mis_gdl = jsje / parseFloat(rtnValue.feeproj_reteval);
			
			mis_jsflag = true;
		}
	}
	
	return true;
}

//向终端缴费
function pay_je_bd(type) {
	
	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.paytype 			= $("#pay_type").val();
	params.yffalarm_id 		= $("#yffalarm_id").val();
	params.buynum 			= $("#buy_times").val();
	
	if(type == "je") {
		params.alarmmoney	= $("#yffalarm_alarm1").val();
		if(!check_jsye(params.alarmmoney)) {
			return;
		}
		params.buydl		= 0;
		params.paybmc		= 0;
		params.alarmbd		= 0;
		params.shutbd		= 0;
		params.yffflag		= 1;
		params.plustime		= 0;
	} else {
		if(!check_jsye_bd(params.alarmmoney)) {
			return;
		}
		params.alarmmoney	= 0;
		params.buydl		= $("#buy_dl").html()	=== "" ? 0 : $("#buy_dl").html();
		params.paybmc		= $("#pay_bmc").html()	=== "" ? 0 : $("#pay_bmc").html();
		params.alarmbd		= $("#alarm_code").val()=== "" ? 0 : $("#alarm_code").val();
		params.shutbd		= $("#shutdown_bd").html() ==="" ? 0 : $("#shutdown_bd").html();
		params.yffflag		= 1;
		params.plustime		= rtnValue.plus_time;
		if(mis_jsflag) {
			params.buydl = mis_gdl;
			params.paybmc = mis_jsbmc;
		}
	}
	
	params.pay_money 		= $("#jfje").val()=== "" ? 0 : $("#jfje").val();
	params.othjs_money 		= $("#jsje").val()=== "" ? 0 : $("#jsje").val();
	if(mis_jsflag) {
		params.othjs_money 	= mis_jsje;
	}
	params.zb_money 		= $("#zbje").val()=== "" ? 0 : $("#zbje").val();
	
	
	params.pay_money    = round(parseFloat(params.pay_money), def.point);
	params.othjs_money  = round(parseFloat(params.othjs_money), def.point);
	params.zb_money 	= round(parseFloat(params.zb_money), def.point);	
	params.all_money    = round(parseFloat(params.zb_money) + parseFloat(params.pay_money) + parseFloat(params.othjs_money),def.point);

	
	var showmis_jsje    = round(mis_jsje, def.point);
	
	modalDialog.height 	= 300;
	modalDialog.width 	= 280;
	modalDialog.url 	= def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param 	= {
		showGSM : false,
		key	: ["客户编号","客户名称", "缴费金额","追补金额","结算金额","总金额"],
		val	: [$("#userno").html(),$("#username").html(), params.pay_money,params.zb_money,mis_calcu_val + (showmis_jsje?"("+showmis_jsje+")":""),params.all_money]		
	};
	
	var o = modalDialog.show();
	
	if(!o||!o.flag){
		return;
	}
	
	params.all_money    = round(parseFloat(params.all_money) + parseFloat(remain_info.nowval), def.point);
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actCommGy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag 	: true,
			userData1		: params.rtu_id,
			zjgid			: params.zjg_id,
			gsmflag 		: 0,		//界面提示是否发送短信
			gyCommPay 		: json_str,
			userData2 		: ComntUseropDef.YFF_GYCOMM_PAY
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				pay(type);
			}else{
				alert("通讯失败...");
			}
		}
	);
}

//结算
function payMoney(){
	
	var fk = getFK();
	
	switch(fk) {
	case "zz":
		pay(fk);
		break;
	case "je":
	case "bd":
		pay_je_bd(fk);
		break;
	default :
		
		break;
	}
}


//召测实时表底
function callRealBD(ii){
	
	if(!ii)ii = 0;
	if(ii >= clpNum){
		cacl_jsye_je();
		return;
	}
	
	var param = {};
	param.rtu_id = $("#rtu_id").val();
	param.zjg_id = $("#zjg_id").val();
	
	if(zfFlag.substring(ii, ii + 1) == "0"){	//正相
		param.datatype = ComntProtMsg.YFF_CALL_REAL_ZBD;
	}else{									//反相
		param.datatype = ComntProtMsg.YFF_CALL_REAL_FBD;
	}
	
	loading.tip = "正在召测当前表底...";
	loading.loading();
	var json_str = jsonString.json2String(param);
	$.post( def.basePath + "ajaxoper/actCommGy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag 	: true,
			userData1		: param.rtu_id,
			zjgid			: param.zjg_id,
			gyCommReadData	: json_str,
			userData2 		: ComntUseropDef.YFF_READDATA
		},
	function(data) {			    	//回传函数
		loading.loaded();
		if (data.result == "success") {
			curbd_info[ii] = eval('(' + data.gyCommReadData + ')');
			callRealBD(ii + 1);
		}else{
			alert("通讯失败..");
		}
	});
}


//召测余额信息
function callRtuRemain(type){
	var param = {};
	param.rtu_id 	= $("#rtu_id").val();
	param.zjg_id 	= $("#zjg_id").val();
	param.paratype	= ComntProtMsg.YFF_GY_CALL_PARAREMAIN;
	
	var json_str = jsonString.json2String(param);
	loading.tip = "正在召测当前余额...";
	loading.loading();
	$.post( def.basePath + "ajaxoper/actCommGy!taskProc.action", 		//后台处理程序
	{
		firstLastFlag 	: true,
		userData1		: param.rtu_id,
		zjgid			: param.zjg_id,
		gyCommCallPara	: json_str,
		userData2 		: ComntUseropDef.YFF_GYCOMM_CALLPARA
	},
	function(data) {//回传函数
		loading.loaded();
		if (data.result == "success") {
			remain_info = eval('(' + data.gyCommCallPara + ')');
			
			if (rtnValue.prot_type == ComntDef.YD_PROTOCAL_FKGD05 || rtnValue.prot_type == ComntDef.YD_PROTOCAL_GD376 
					|| rtnValue.prot_type == ComntDef.YD_PROTOCAL_GD376_2013) {
				param.mpid 		= 1;
				param.ymd 		= 0;
				param.datatype	= ComntProtMsg.YFF_CALL_GY_REMAIN;
				
				json_str = jsonString.json2String(param);
				loading.loading();
				$.post( def.basePath + "ajaxoper/actCommGy!taskProc.action", 		//后台处理程序
					{
						firstLastFlag 	: true,
						userData1		: param.rtu_id,
						zjgid			: param.zjg_id,
						gyCommReadData	: json_str,
						userData2 		: ComntUseropDef.YFF_READDATA
					},
				function(data) {//回传函数
					loading.loaded();
					if (data.result == "success") {
						var tmp = eval('(' + data.gyCommReadData + ')');
						remain_info.nowval = tmp.cur_val;
						
						if(type == "je"){
							$("#now_remain").html(remain_info.nowval);
							callRealBD();
						}//下面 else 不存在
						else if(type == "bd"){
							$("#shutdown_bd").html(remain_info.totval);
							$("#ddbd").html(remain_info.totval);
							$("#dqbd").html(remain_info.nowval);
							$("#alarm_code").val(remain_info.alarm_val1);
							cacl_jsye_bd();
						}
					}else{
						alert("通讯失败..");
					}
				});
			} else {
				if(type == "je"){
					$("#now_remain").html(remain_info.nowval);
					callRealBD();
				}
				else if(type == "bd"){
					$("#shutdown_bd").html(remain_info.totval);
					$("#ddbd").html(remain_info.totval);
					$("#dqbd").html(remain_info.nowval);
					$("#alarm_code").val(remain_info.alarm_val1);
					cacl_jsye_bd();
				}
			}
		}else{
			alert("通讯失败..");
		}
	});
	
	
}


//计算结算余额-金额方式
function cacl_jsye_je(){
	//结算金额 =(当前表底  - mis结算日表底 )* pt* ct * 电价 + 基本费 + 当前余额 - mis余额
	
	var blv = parseFloat($("#blv").html());
	
	var cacu1 = 0;
	//单费率
	if(rtnValue.feeproj_type == 0) {
		var feez = [0, 0, 0];
		feez[0] = rtnValue.feeproj_reteval;
		feez[1] = rtnValue.feeproj_reteval1;
		feez[2] = rtnValue.feeproj_reteval2;
		for(var i = 0; i < clpNum; i++) { curbd_info[i].cur_zyz = 12;	//temp test
			//正相
			if(zfFlag.substring(i, i + 1) == "0") {
				cacu1 += (parseFloat(curbd_info[i].cur_zyz) - parseFloat(bdyg[i])) * blv * parseFloat(feez[i]);
			}
			//反相
			else {
				cacu1 += (parseFloat(curbd_info[i].cur_fyz) - parseFloat(bdyg[i])) * blv * parseFloat(feez[i]);
			}
		}
	}
	//复费率
	else if(rtnValue.feeproj_type == 1) {
		//尖峰平谷电价
		var feej = [0, 0, 0], feef = [0, 0, 0], feep = [0, 0, 0], feeg = [0, 0, 0];
		
		var tmp = rtnValue.feeproj_reteval.split(",");
		feej[0] = tmp[0];
		feef[0] = tmp[1];
		feep[0] = tmp[2];
		feeg[0] = tmp[3];
		
		tmp = rtnValue.feeproj_reteval1.split(",");
		feej[1] = tmp[0];
		feef[1] = tmp[1];
		feep[1] = tmp[2];
		feeg[1] = tmp[3];
		
		tmp = rtnValue.feeproj_reteval2.split(",");
		feej[2] = tmp[0];
		feef[2] = tmp[1];
		feep[2] = tmp[2];
		feeg[2] = tmp[3];
		
		for(var i = 0; i < clpNum; i++) {
			//正相
			if(zfFlag.substring(i, i + 1) == "0") {
				cacu1 += (((parseFloat(curbd_info[i].cur_zyj) - parseFloat(mis_bdj[i])) * feej[i]) + ((parseFloat(curbd_info[i].cur_zyf) - parseFloat(mis_bdf[i])) * feef[i]) +
						 ((parseFloat(curbd_info[i].cur_zyp) - parseFloat(mis_bdp[i])) * feep[i]) + ((parseFloat(curbd_info[i].cur_zyg) - parseFloat(mis_bdg[i])) * feeg[i])) * blv;
			}
			//反相
			else {
				cacu1 += (((parseFloat(curbd_info[i].cur_fyj) - parseFloat(mis_bdj[i])) * feej[i]) + ((parseFloat(curbd_info[i].cur_fyf) - parseFloat(mis_bdf[i])) * feef[i]) +
						  ((parseFloat(curbd_info[i].cur_fyp) - parseFloat(mis_bdp[i])) * feep[i]) + ((parseFloat(curbd_info[i].cur_fyg) - parseFloat(mis_bdg[i])) * feeg[i])) * blv;
			}
		}
	}
	
	if(cacu1 < 0) {
		alert("结算补差表底比当前表底大,不能结算");//		return;
	}
	
	//基本费
	var jbf = 0;
	
	var year = _today.substring(0, 4);
	var month = _today.substring(4, 6);
	var day = _today.substring(6, 8);

	month = parseInt(month, 10);
	day = parseInt(day, 10);
	//当前服务器时间
	var cur_date = new Date(year, month - 1, day);
	
	var t_str = $("#cb_ym").val();
	var year1 = t_str.substring(0,4);
	var month1 = t_str.substring(5,7);
	var day1 = jsDate;

	month1 = parseInt(month1, 10);
	day1 = parseInt(day1, 10);
	//结算日时间
	js_date = new Date(year1, month1 - 1, day1);
	
	if ((year < year1) || (year > year1+1)) {
		alert("结算日期错误");
		return;
	}

	//间隔2个月以上 //处理 跨年之情况   下面还要处理跨年？
	if((year - year1) * 12+ month > month1 + 1) {
		var tmp_date = new Date(year1, month1, 1);		 //下月1日  计算日期差 算首月基本费
		var days = parseInt(js_date.MaxDayOfDate());
		var jbf1 = (parseFloat(rtnValue.jbf) / days) * parseInt(js_date.DateDiff('d', tmp_date));
		
		tmp_date = new Date(year, month - 1, 1);		 //当前月份 
		days = parseInt(cur_date.MaxDayOfDate());
		var jbf2 = (parseFloat(rtnValue.jbf) / days) * parseInt(tmp_date.DateDiff('d', cur_date));

		var months = parseInt(month) - parseInt(month1) - 1; //中间月份
		var jbf3 = parseFloat(rtnValue.jbf) * months;
		
		jbf = jbf1 + jbf2 + jbf3;
	}
	else {
		var days = parseInt(js_date.MaxDayOfDate());
		jbf = (parseFloat(rtnValue.jbf) / days) * parseInt(js_date.DateDiff('d',cur_date));
	}

	var jsje = cacu1 + jbf + parseFloat(remain_info.nowval) - parseFloat(cbDayYe);
	jsje = -round(jsje, def.point);

	//计算出来的结算金额
	mis_calcu_val = jsje;
	$("#jsje").val(jsje);

	calcu();

	$("#btnPay").attr("disabled", false);
}

//计算结算余额-表底方式
function cacl_jsye_bd(){
	//-((断电表底  - mis结算日表底) * pt* ct * 电价  - mis余额)
	
	var shutdown_bd = parseFloat($("#shutdown_bd").html() == "" ? 0 : $("#shutdown_bd").html());
	var blv = parseFloat(rtnValue.blv);
	var dj = parseFloat(rtnValue.feeproj_reteval);
	var zbd = 0;
	for(var i = 0; i < clpNum; i++) {
		zbd += bdyg[i];
	}
	//add check zkz 20121016
	var curbd = parseFloat($("#dqbd").html());
	if (curbd < zbd) {
		alert("结算补差表底比当前表底大,不能结算"); return;
	}
	
	var jsye = (shutdown_bd - zbd) * blv * dj - cbDayYe;
	jsye = -round(jsye, def.point);	//取负
	$("#jsje_bd").val(jsye);
	calcu_bd();

	//计算出来的结算金额等
	mis_calcu_val = jsye;
	mis_calcu_gdl = $("#buy_dl").html();
	mis_calcu_bmc = $("#pay_bmc").html();
	
	$("#btnPay_bd").attr("disabled", false);
}

//主站方式获取余额
function remain_zz() {
	
	var t_str = $("#cb_ym").val();
	var str_cb_ym = t_str.substring(0,4) + t_str.substring(5,7); 
	var cb_ymd    = str_cb_ym + jsDate;
	
	var params = {};
	params.userop_id = ComntUseropDef.YFF_GYOPER_GETJSBCREMAIN;
	params.rtu_id = $("#rtu_id").val();
	params.zjg_id = $("#zjg_id").val();
	
	params.date = cb_ymd;
	params.time = jsTime;
	
	for(var i = 0; i < 3; i++){
		eval("params.mp_id" + i + "=mp_id[" + i + "]");
		
		if(zfFlag.substring(i, i + 1) == "0"){	//正向
			eval("params.bd_zy"  + i + "=bdyg[" + i + "]");
			eval("params.bd_zyj" + i + "=0");
			eval("params.bd_zyf" + i + "=0");
			eval("params.bd_zyp" + i + "=0");
			eval("params.bd_zyg" + i + "=0");
			eval("params.bd_zw"  + i + "=bdwg[" + i + "]");
			eval("params.bd_fy"  + i + "=0");
			eval("params.bd_fyj" + i + "=0");
			eval("params.bd_fyf" + i + "=0");
			eval("params.bd_fyp" + i + "=0");
			eval("params.bd_fyg" + i + "=0");
			eval("params.bd_fw"  + i + "=0");
		}else{
			eval("params.bd_zy"  + i + "=0");
			eval("params.bd_zyj" + i + "=0");
			eval("params.bd_zyf" + i + "=0");
			eval("params.bd_zyp" + i + "=0");
			eval("params.bd_zyg" + i + "=0");
			eval("params.bd_zw"  + i + "=0");
			eval("params.bd_fy"  + i + "=bdyg[" + i + "]");
			eval("params.bd_fyj" + i + "=0");
			eval("params.bd_fyf" + i + "=0");
			eval("params.bd_fyp" + i + "=0");
			eval("params.bd_fyg" + i + "=0");
			eval("params.bd_fw"  + i + "=bdwg[" + i + "]");
		}
	}
	
	var json_str=jsonString.json2String(params);
	
	loading.loading();
	$.post(
		def.basePath + "ajaxoper/actOperGy!taskProc.action", 
		{
			userData1        :  params.rtu_id,
			zjgid 			 :  params.zjg_id,
			userData2        :  params.userop_id,
			gyOperGetJSBCRemain  :  json_str,
			gsmflag          :  0
		},
		function(data){
			loading.loaded();
			if(data.gyOperGetJSBCRemain == ""){
				alert(data.err_strinfo);
				return;
			}
			var json = eval("(" + data.gyOperGetJSBCRemain + ")");
			if(json.remain_val == ""){
				alert("数据出错");
			}else{
				var dqje = round(json.remain_val,def.point);
				mis_calcu_val = round(parseFloat(cbDayYe) - dqje,def.point);
				$("#jsje").val(mis_calcu_val);
				calcu();
				$("#btnPay").attr("disabled",false);
			}
		}
	);
}

//金额方式获取余额
function remain_je() {
	callRtuRemain("je");
}

//表底方式获取余额
function remain_bd() {
	callRtuRemain("bd");
}

//获取结算金额
function remainSum(){
	var fk = getFK();
	
	switch(fk) {
	case "zz":
		$("#tabinfo_je").show();
		$("#tabinfo_bd").hide();
		remain_zz();
		break;
	case "je":
		$("#tabinfo_je").show();
		$("#tabinfo_bd").hide();
		remain_je();
		break;
	case "bd":
		$("#tabinfo_je").hide();
		$("#tabinfo_bd").show();
		remain_bd();
		break;
	default :
		
		break;
	}
}


function calcu(){
	var zbje = 0.0, jfje = 0.0, jsje = 0.0;
	
	zbje = $("#zbje").val() === "" ? 0 : $("#zbje").val();
	jfje = $("#jfje").val() === "" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val() === "" ? 0 : $("#jsje").val();
	
	var zje = round(parseFloat(zbje) + parseFloat(jfje) + parseFloat(jsje),3);
	
	if(!isNaN(zje)){
		setAlarmValue(rtnValue.yffalarm_alarm1, rtnValue.yffalarm_alarm2, zje)
		$("#zje").val(zje);
	}
}

function calcu_bd(){
	var zbje = 0.0, jfje = 0.0, jsje = 0.0;
	
	zbje = $("#zbje_bd").val() === "" ? 0 : $("#zbje_bd").val();
	jfje = $("#jfje_bd").val() === "" ? 0 : $("#jfje_bd").val();
	jsje = $("#jsje_bd").val() === "" ? 0 : $("#jsje_bd").val();
	
	$("#zbje").val(zbje);
	$("#jfje").val(jfje);
	$("#jsje").val(jsje);
	
	var zje = round(parseFloat(zbje) + parseFloat(jfje) + parseFloat(jsje), def.point);
	if(isNaN(jsje)){
		return;
	}
	
	$("#zje_bd").html(zje);
	$("#zje").val(zje);
	
	var dj 	= rtnValue.feeproj_reteval;
	var blv = $("#blv").html() === "" ? 1 : $("#blv").html();
	var zbd = $("#ddbd").html() === "" ? 0 : $("#ddbd").html();
	
	var alarm_val = rtnValue.yffalarm_alarm1;
	var ret_val = bd_calcu(zje, dj, blv, zbd, alarm_val, alarm_type, zbd);
	
	$("#buy_dl").html(ret_val.buy_dl);
	$("#pay_bmc").html(ret_val.pay_bmc);
	$("#shutdown_bd").html(ret_val.shutdown_bd);
	
	if(zje >= 0) {
		$("#alarm_code").val(ret_val.alarm_code);
	} else {
		var inter = parseFloat(remain_info.totval) - parseFloat(remain_info.alarm_val1);
		var cur_bd = parseFloat(remain_info.nowval);
		var min_shutbd = inter + cur_bd;
		if (parseFloat(ret_val.shutdown_bd) < min_shutbd) {
			if (cur_bd < parseFloat(remain_info.alarm_val1)) {
				$("#shutdown_bd").html(round(min_shutbd, def.point));
			}//else 下面结算时处理了 当前 > 报警
			else {//if (cur_bd > parseFloat(ret_val.shutdown_bd)) {
				$("#shutdown_bd").html(round(zbd, def.point));
			}
		}
		var ac = parseFloat($("#shutdown_bd").html()) - inter;
		if(ac < 0) {
			$("#alarm_code").val(0);
		} else {
			$("#alarm_code").val(round(ac, def.point));
		}
	}
}

//刚刚进入界面  各个input默认disabled
function IsDisabled(){
	$("#zbje"           ).attr("disabled",true);
	$("#jfje"           ).attr("disabled",true);
	$("#jsje"           ).attr("disabled",true);
	$("#yffalarm_alarm1").attr("disabled",true);
	$("#yffalarm_alarm2").attr("disabled",true);
	
	$("#btnPay").attr("disabled",true);
	$("#btnPay_bd").attr("disabled",true);
}
