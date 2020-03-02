//换表
var rtnValue;
var	mygrid;
var mp_id;
var ptct_info;

var rtnBDNew = null;
var rtnBDOld = null;
var meterNum = null;//记录选择第几块电表

var WRITECARD_TYPE 	= window.top.SDDef.YFF_CARDMTYPE_KE001;	//卡表类型  1-智能表09版     3-6013	6-智能表13版

var ChangedFlag = false; //换表标志
var payFlag = false;
var gsmflag = 0;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	$("#btnSearch").focus();
	
	$("#btnSearch").click(function(){selcons()});
	$("#btnReadCard").click(function(){readcard()});//读卡检索
	$("#cardInfo").click(function(){window.top.card_comm.cardinfo()});//卡内信息
	$("#btnPrt").click(function(){showPrint()});
	
	$("#btnBDNew").click(function(){Import("new")});
	$("#btnBDOld").click(function(){Import("old")});
	
	$("#btnChgmeter").click(function(){if(check())dochgMeter()});
	
	$("#chg_type").change(function(){chg_type(this.value);});
	//$("#chg_meter").change(function(){chg_meter(this.value);});
	
	//ct信息
	$("#ctfz").attr("readonly",true).css("background","#ccc").keyup(function(){if(isNaN(this.value)){this.value = 1;};calcPTCT("ct")});
	$("#ctfm").attr("readonly",true).css("background","#ccc").keyup(function(){if(isNaN(this.value)){this.value = 1;};calcPTCT("ct")});
	
	//pt信息
	$("#ptfz").attr("readonly",true).css("background","#ccc").keyup(function(){if(isNaN(this.value)){this.value = 1;};calcPTCT("pt")});
	$("#ptfm").attr("readonly",true).css("background","#ccc").keyup(function(){if(isNaN(this.value)){this.value = 1;};calcPTCT("pt")});
	
	//$("#syje").keyup(function(){if(isNaN(this.value)){this.value = 0;return};calcu(this.value);});

	$("#zbje").keyup(function(){calcu()});
	$("#jsje").keyup(function(){calcu()});
	$("#jfje").keyup(function(){calcu()});
	
	var date = new Date();
	$("#chg_time").val(date.Format("yyyy年MM月dd日 HH时mm分").toString());

	initGrid();
	chg_type($("#chg_type").val());
	
	setTextDisabled(true);
});

function selcons(){//检索
	setTextDisabled(false);
	//禁用表底录入和换表键
	disableBtn();
	var js_tmp = doSearch("ks",ComntUseropDef.YFF_GYOPER_CHANGEMETER, rtnValue);
	if(!js_tmp){
		return;
	}
	setSearchJson2Html(js_tmp);	
}

function readcard(){//读卡检索。调用readCardSearchGy
	loading.loading();
	setTextDisabled(false);						//清空记录
	//禁用表底录入和换表键
	disableBtn();
	window.top.card_comm.readCardSearchGy(ComntUseropDef.YFF_GYOPER_CHANGEMETER);
}

function setSearchJson2Html(js_tmp){//读卡检索，返回赋值。调用readCardSearch后，readcard中异步执行完成后，执行此函数。
	loading.loaded();
	if(!js_tmp) return;
	if(!window.top.card_comm.checkInfo(js_tmp)) return;
	
	$("#jfje"	).val("");
	$("#jsje"	).val("");
	$("#zbje"	).val("");
	$("#zje"	).html("");
	$("#bd_old"	).html("");
	$("#bd_new"	).html("");
	$("#buy_dl"	).html("");
	$("#pay_bmc").html("");
	rtnBDNew = null;
	rtnBDOld = null;
	
	rtnValue = js_tmp;
	setConsValue(rtnValue);

	getAlarmValue(rtnValue.yffalarm_id);	//获取报警方式
	ChangedFlag = false;
	
	getMoneyLimit(rtnValue.feeproj_id);		//囤积限值

	getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);							//缴费记录	
	
	var tmp_val = rtnValue.cardmeter_type.split("]");
	WRITECARD_TYPE = tmp_val[0].split("[")[1];
//调整 20130402	
//	WRITECARD_TYPE = tmp_val[0].substring(1);;

	$("#cardmeter_type").html(tmp_val[1].split('"')[0]);
	$("#writecard_no").html(rtnValue.writecard_no);
	$("#meter_id").html(rtnValue.meter_id);

	var ke003f = (WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE003);

	if (ke003f) {
		$("#buy_dl").show();
		$("#gdldesc").show();
		$("#pay_bmc").show();
		$("#pay_bmcdesc").show();
		$("#alarm1").html("报警表码差1：");
		$("#alarm2").html("报警表码差2：");
	}
	else {
		$("#buy_dl").hide();
		$("#gdldesc").hide();
		$("#pay_bmc").hide();
		$("#pay_bmcdesc").hide();
		$("#alarm1").html("报警金额1(元)：");
		$("#alarm2").html("报警金额2(元)：");
	}
	$("#buy_times").html($("#buy_times").val());
	
	if(!window.top.card_comm.checkInfo(js_tmp)){
		$("#btnBDOld").attr("disabled",true);
		return;
	}else{
		$("#btnBDOld").attr("disabled",false);
	}
	
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
	
	initChgInfo();
		
	//mis.js中函数   miss查询函数
	var param = {rtu_id : rtnValue.rtu_id, zjg_id : rtnValue.zjg_id, busi_no : rtnValue.userno};
	mis_query(param,gloQueryResult);
	$("#btnBDOld").focus();
}

function dochgMeter(){//点击更换按钮。
	if($("#chg_type").val() == SDDef.YFF_CHGMETER_MT){//换表：写卡开户，存库换表。
		wrtCard_chgMeter();
	}
	else{//存库换表换ct
		wrtDB_chgMeter();
	}
	
}

function wrtCard_chgMeter(){//换表写卡
	
	var params = {};
	
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money   		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),2);
	
	loading.loading();
	
	modalDialog.height 	= 300;
	modalDialog.width 	= 280;
	modalDialog.url 	= def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param 	= {
		showGSM : true,
		key	: ["客户编号","客户名称", "缴费金额","追补金额","结算金额","总金额"],
		val	: [$("#userno").html(),$("#username").html(), params.pay_money,params.zb_money,params.othjs_money,params.all_money]		
	};
	
	var o = modalDialog.show();
	
	if(!o||!o.flag){
		loading.loaded();
		return;
	}
	gsmflag = o.gsm;//记录给GYOper换表时使用的。
	params.gsmFlag = o.gsm;
	window.setTimeout("wrtCard_chgMeter1(" + o.gsm + ")", 10);
}

function wrtCard_chgMeter1(gsm){//换表写卡
	if(rtnBDNew == null)
	{
		alert("未录入新表表底!");
		return;
	}
	if(rtnBDOld == null)
	{
		alert("未录入旧表表底!");
		return;
	}
	
//	calWrtCard();
	
	var rtu_id = $("#rtu_id").val(), zjg_id = $("#zjg_id").val();
	if(rtu_id === "" || zjg_id === ""){
		alert(sel_user_info);
		return;
	}
	
	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.myffalarmid 		= $("#yffalarm_id").val();
	params.paytype 			= $("#pay_type").val();
	params.buynum 			= 1;
	
	params.alarm_val1		= $("#yffalarm_alarm1").val();
	params.alarm_val2		= $("#yffalarm_alarm2").val();
	
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money   		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),2);
	
	params.date 			= 0;
	params.time 			= 0;
	params.buy_dl 			= $("#buy_dl").html()==="" ? 0:$("#buy_dl").html();
	params.pay_bmc 			= $("#pay_bmc").html()===""? 0:$("#pay_bmc").html();
	
	params.gsmFlag = gsm;
	
	params.pt 			= rtnValue.pt_ratio;
	params.ct 			= rtnValue.ct_ratio;
	params.meter_type 	= WRITECARD_TYPE;
	params.card_type  	= SDDef.CARD_OPTYPE_OPEN;//开户、缴费、补卡
	params.limit_dl 	= money_limit;
	params.feeproj_id 	= $("#feeproj_id").val();
	params.writecard_no	= $("#writecard_no").html();
	params.meterno  	= rtnValue.meter_id;
	params.cardno 		= $("#userno").html();
	
	params.operType  = ComntUseropDef.YFF_GYOPER_CHANGEMETER;	//操作类型决定更新标识
	
	if (CheckCard(params) == false) {
	    loading.loaded();	
		return;
	};
	loading.loading();
	$.post( def.basePath + "ajaxoper/actWebCard!getcardWrtInfo.action", 		//后台处理程序
		{
			result	: jsonString.json2String(params)
		},
		function(data) {//回传函数
			var retStr 	 = data.result.toString();
			var ret_json = window.top.card_comm.writecard(retStr);
			if(ret_json.errno  == 0){
				alert("写卡成功");
				wrtDB_chgMeter();
			}else{
				loading.loaded();
				alert("写卡失败！\n" + ret_json.errsr);
			}
		}
	);
	
}

function  wrtDB_chgMeter() {//换表 存库。
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

	
	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	
	
	//params.yffalarm_id 		= $("#yffalarm_id").val();
	
	params.alarm_val1		= $("#yffalarm_alarm1").val();
	params.alarm_val2		= $("#yffalarm_alarm2").val();
	
	//params.pay_money 		= $("#syje").val()===""? 0 : $("#syje").val();//
	if($("#chg_type").val() == SDDef.YFF_CHGMETER_MT){//换表时。
		params.buynum 		= 1;
		params.pay_money 	= $("#jfje").val()===""?0:$("#jfje").val();//不为0时有payFlag=true，给mis缴费。否则mis不缴费。
		params.othjs_money 	= $("#jsje").val()===""?0:$("#jsje").val();
		params.zb_money 	= $("#zbje").val()===""?0:$("#zbje").val();
		params.all_money   	= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),2);
		params.buydl 		= $("#buy_dl").html()==="" ? 0:$("#buy_dl").html();
		params.paybmc 		= $("#pay_bmc").html()===""? 0:$("#pay_bmc").html();

	}else{
		params.buynum 		= $("#buy_times").val();
		params.pay_money    = 0;
		params.othjs_money 	= 0;
		params.zb_money 	= 0;
		params.all_money 	= 0;
		params.buydl  		= 0;
		params.paybmc  		= 0;
	}
	
	params.alarmmoney  		= 0;
	params.shutbd  			= 0;
	params.shutdown_bd  	= 0;
	params.alarmbd  		= 0;
	
	var chg_meter	= mp_id;
	var chg_time 	= $("#chg_time").val();
	params.chg_date = chg_time.substring(0,  4) + chg_time.substring(5,  7) + chg_time.substring(8,10);
	params.chg_time = chg_time.substring(12,14) + chg_time.substring(15,17) + "00";
	params.o_date 	= chg_time.substring(0,  4) + chg_time.substring(5,  7) + chg_time.substring(8,10);
	params.o_time 	= chg_time.substring(12,14) + chg_time.substring(15,17) + "00";
	params.n_date	= chg_time.substring(0,  4) + chg_time.substring(5,  7) + chg_time.substring(8,10);
	params.n_time 	= chg_time.substring(12,14) + chg_time.substring(15,17) + "00";
	
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
	
	params.chg_mpid			= mp_id;
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
		
		var m_id = mp_id;
		
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
		
		var m_id = mp_id;
		
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
			gsmflag				: gsmflag,
			gyOperChangeMeter	: json_str,
			userData2			: ComntUseropDef.YFF_GYOPER_CHANGEMETER
		},
		function(data) {			    	//回传函数
			loading.loaded();
				if (data.result == "success") {
				//向MIS系统缴费
				if(payFlag && gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
					var ret_json = eval("(" + data.gyOperChangeMeter + ")");
					var mis_para 	= {};
				
					mis_para.rtu_id = ret_json.rtu_id;           
					mis_para.zjg_id = params.zjg_id;             
					mis_para.op_type= SDDef.YFF_OPTYPE_PAY;   
					mis_para.date 	= ret_json.op_date;          
					mis_para.time 	= ret_json.op_time;          
					mis_para.updateflag = 0;                     
					mis_para.jylsh 	= ret_json.wasteno;          
					mis_para.yhbh 	= rtnValue.userno;           
					mis_para.jfje 	= params.pay_money;          
					mis_para.yhzwrq = ret_json.op_date;          
					mis_para.jfbs 	= gloQueryResult.misJezbs; 
					
					var all_pay = 0.0;
					
					if(provinceMisFlag == "HB"){
						for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
							eval("mis_para.yhbh" + i + "=gloQueryResult.misDetailsvect["+i+"].yhbh");
							eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");
						}                
					}
					else if(provinceMisFlag == "HN"){
						mis_para.batch_no	= gloQueryResult.misBatchNo;	
						mis_para.hsdwbh		= gloQueryResult.misHsdwbh;
						for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
							eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");

							eval("mis_para.dfny" + i + "=gloQueryResult.misDetailsvect["+i+"].dfny");
							eval("mis_para.dfje" + i + "=gloQueryResult.misDetailsvect["+i+"].dfje");
							eval("mis_para.wyjje" + i + "=gloQueryResult.misDetailsvect["+i+"].wyjje");
							var bcssje= Math.min(parseFloat(gloQueryResult.misDetailsvect[i].dfje) + parseFloat(gloQueryResult.misDetailsvect[i].wyjje), parseFloat(mis_para.jfje) - all_pay);
							all_pay += bcssje;
							eval("mis_para.bcssje" + i + "=" + bcssje);
						}
					}
					else if (provinceMisFlag == "GS"){
						mis_para.jfbs 	= gloQueryResult.misJezbs;
						mis_para.dzpc   = gloQueryResult.misDzpc;
						for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
							eval("mis_para.yhbh" + i + "=gloQueryResult.misDetailsvect["+i+"].yhbh");
							eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");

							eval("mis_para.jfje" + i + "=gloQueryResult.misDetailsvect["+i+"].yjje");
							eval("mis_para.dfje" + i + "=gloQueryResult.misDetailsvect["+i+"].dfje");
							eval("mis_para.wyjje" + i + "=gloQueryResult.misDetailsvect["+i+"].wyjje");
							
							eval("mis_para.sctw" + i + "=gloQueryResult.misDetailsvect["+i+"].sctw");
							eval("mis_para.bctw" + i + "=gloQueryResult.misDetailsvect["+i+"].bctw");
						}
					}
					mis_para.vlength = gloQueryResult.misDetailsvect.length;	
					mis_pay(mis_para);
					alert("换表缴费成功!");
				}
				else{
					alert("操作成功...");	
				}
			
				if(data.gyOperChangeMeter == ""){
					alert("获取返回的操作信息失败。请到[补打发票]界面补打。")
					$("#btnPrt").attr("disabled",true);
				}else{
					$("#btnPrt").attr("disabled",false);
					window.top.WebPrint.setYffDataOperIdx2params(data.gyOperChangeMeter);//打印用的参数
				}
			
				getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
				
				disableBtn();
			}
			else {
				alert("操作失败..." + (data.operErr ? data.operErr : ''));
			}
		});
}

function calcPTCT(type){//计算PT、CT
	if($("#" + type + "fm").val() === "" || $("#" + type + "fm").val() === "0"){
		$("#" + type).html("");
		return;
	}
	
	$("#" + type).html(round(parseFloat($("#" + type + "fz").val())/parseFloat($("#" + type + "fm").val()),def.point));
}

function chg_type(value) {//根据更换类型，启用/禁用PT、CT文本框,显示/隐藏缴费信息。
	
	switch (parseInt(value)) {
		case SDDef.YFF_CHGMETER_MT:
			$("#ctfz").attr("readonly", true).css("background","#ccc");
			$("#ctfm").attr("readonly", true).css("background","#ccc");
	
			$("#ptfz").attr("readonly", true).css("background","#ccc");
			$("#ptfm").attr("readonly", true).css("background","#ccc");
			
			$("#tabinfo2").show();
			break;
		case SDDef.YFF_CHGMETER_CT:
			$("#ctfz").attr("readonly", false).css("background","white");
			$("#ctfm").attr("readonly", false).css("background","white");
	
			$("#ptfz").attr("readonly", true).css("background","#ccc");
			$("#ptfm").attr("readonly", true).css("background","#ccc");
			
			$("#tabinfo2").hide();
			break;
		case SDDef.YFF_CHGMETER_PT:
			$("#ctfz").attr("readonly", true).css("background","#ccc");
			$("#ctfm").attr("readonly", true).css("background","#ccc");
	
			$("#ptfz").attr("readonly", false).css("background","white");
			$("#ptfm").attr("readonly", false).css("background","white");
			
			$("#tabinfo2").hide();
			break;
		default:
			break;
	}
}

function chg_meter(value){//选择不同的电表，根据选择的电表和更换类型，填充该电表的PT,CT。
	for ( var i = 0; i < ptct_info.rows.length; i++) {
		if(ptct_info.rows[i].value == value){
			if($("#chg_type").val() == SDDef.YFF_CHGMETER_CT){				//ct
				$("#ctfz").val(ptct_info.rows[i].ctfz);
				$("#ctfm").val(ptct_info.rows[i].ctfm);
				$("#ct").html(ptct_info.rows[i].ct);
			}
			else if($("#chg_type").val() == SDDef.YFF_CHGMETER_PT){		//pt
				$("#ptfz").val(ptct_info.rows[i].ptfz);
				$("#ptfm").val(ptct_info.rows[i].ptfm);
				$("#pt").html(ptct_info.rows[i].pt);
			}
			break;
		}
	}
}

function calcu(){
	if(!rtnValue)return;

	var jfje = 0.0, zbje = 0.0, jsje = 0.0, zje = 0.0;
	zbje = $("#zbje").val() === "" ? 0 : $("#zbje").val();
	jfje = $("#jfje").val() === "" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val() === "" ? 0 : $("#jsje").val();
	
	zje = round(parseFloat(zbje) + parseFloat(jfje) + parseFloat(jsje),def.point);
	
	if(!isNaN(zje)){
		$("#zje").html(zje);
		var ke003f = (WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE003);	

		if (ke003f) {
			var dj 	= $("#feeproj_reteval").val()===""?1:$("#feeproj_reteval").val();
			var blv = $("#blv").html()===""?1:$("#blv").html();
			var zbd = 0;
			var alarm_val1 = (rtnValue.yffalarm_alarm1 == "" || rtnValue.yffalarm_alarm1 == undefined ) ? 30 : rtnValue.yffalarm_alarm1;
			var alarm_val2 = (rtnValue.yffalarm_alarm2 == "" || rtnValue.yffalarm_alarm2 == undefined ) ? 5 : rtnValue.yffalarm_alarm2;

			var ret_val = cardalarm_calcu(zje, dj, blv, zbd, alarm_val1, alarm_val2, rtnValue.type, zbd);

			$("#buy_dl").html(ret_val.buy_dl);
			$("#pay_bmc").html(ret_val.pay_bmc);
			//$("#shutdown_bd").html(ret_val.shutdown_bd);
			$("#yffalarm_alarm1").val(ret_val.alarm_code1);
			$("#yffalarm_alarm2").val(ret_val.alarm_code2);
		}
		else 
		{
			setAlarmValue(rtnValue.yffalarm_alarm1, rtnValue.yffalarm_alarm2, zje);
		}
	}else{ 
		return;
	}
}	

function setTextDisabled(mode){	//设置文本框状态（disabled/enabled）
	if(!mode){
		$("#yffalarm_alarm1").val("");
		$("#yffalarm_alarm2").val("");
		
		$("#jfje").val("");
		$("#zbje").val("");
		$("#jsje").val("");
		
		$("#zje").html("");
		$("#buy_times").val("");
		$("#buy_dl").val("");
		$("#pay_bmc").val("");
	}
	
	$("#yffalarm_alarm1").attr("disabled",mode);
	$("#yffalarm_alarm2").attr("disabled",mode);
	$("#jfje").attr("disabled",mode);
	$("#zbje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
}

function Import(bdflag){
	var tmp = importBD($("#rtu_id").val(),$("#zjg_id").val());
	
	if (!tmp) {
		return;
	}
	else{
		if (bdflag == "old") {
		    rtnBDOld = tmp;
		    $("#btnBDNew").attr("disabled",false);
		    $("#bd_old").html(rtnBDOld.td_bdinf);
		    $("#btnBDNew").focus();
		}
		if (bdflag == "new") {
			rtnBDNew = tmp;
			$("#bd_new").html(rtnBDNew.td_bdinf);
			$("#rtuInfo").attr("disabled",false);
			$("#btnChgmeter").attr("disabled",false);
		}
	}
}

function initChgInfo(){//取得总加组的测点ID描述及PTCT
	
	//$("#chg_meter").html("");
	
	$.post(def.basePath + "ajaxspec/actConsPara!retChgMeterInfo.action",{result:$("#rtu_id").val(), zjgId : $("#zjg_id").val()},function(data) {			    	//回传函数
		if (data.result != "") {
			var json = eval('(' + data.result + ')');
			ptct_info = json;
			for(var i = 0; i < json.rows.length; i++){
				//$("#chg_meter").append("<option value="+json.rows[i].value+">"+json.rows[i].text+"</option>");
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
			chg_meter(mp_id);
		}			
	});
}

function check(){
	if(rtnBDNew == null)
	{
		alert("未录入新表表底!");
		return false;
	}
	if(rtnBDOld == null)
	{
		alert("未录入旧表表底!");
		return false;
	}
	
	if(!confirm("确认要换表/换倍率操作吗？")){
		return false;
	}
	//if($("#btnPay").attr("disabled"))  return false;不知道什么情况下，按钮禁用还进check？？。
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
	
	if(!isDbl("zbje" , "追补金额" ,-money_limit,money_limit, false)){//追补金额应该在0~囤积值之间
		return false;
	}
	
	if(!isDbl("jsje",  "结算金额" ,-money_limit, money_limit, false)){
		return false;
	}
	
	if(!isDbl("jfje",  "缴费金额" ,0, money_limit, false)){
		return false;
	}

	//20130402
	var zje = $("#zje").html();
	if(!isDbl_Html("zje" ,  "总金额"  , 0, money_limit, false)){//总金额应该在0~囤积值之间
		return false;
	}
	
	if($("#chg_type").val() == SDDef.YFF_CHGMETER_MT && $("#jfje").val()>0 ){//换表同时缴费时，要求MIS通讯。
		payFlag = true;
		if(!window.top.card_comm.checkInfo(rtnValue)) return;
		if (gloQueryResult.misUseflag == "true" && (gloQueryResult.misOkflag == "false" || gloQueryResult.misOkflag == undefined)) {//如果MIS不通，禁止缴费
			alert("MIS不能通讯,禁止缴费...");
			return false;
		}		
	}else{
		payFlag =  false;
		$("#jfje").val(0);
		$("#zje").html(0);
		$("#jsje").val(0);
		$("#zbje").val(0);
	}
	
	return true;
}

function CheckCard(writeparams){//写卡前校验。跟高压开户相同。
	var json_out = {};
	json_out = window.top.card_comm.readcard();
	if(json_out == undefined || json_out ==""){
		alert("读卡错误!");
		return  false;				
	}

	var meterType 	= json_out.meter_type;	//卡表类型	
	var meterNo 	= json_out.meterno; 	//表号
	var consNo  	= json_out.consno;		//户号
	var buyTime		= json_out.pay_num;		//购电次数	
	
	//20131127新增判断
	if((WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE001) && (meterType != window.top.SDDef.YFF_METER_TYPE_ZNK)){
		alert("用户电表为2009版规约，请使用09版购电卡!");
		return false;
	}
	
	if((WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE006) && (meterType != window.top.SDDef.YFF_METER_TYPE_ZNK2)){
		alert("用户电表为2013版规约，请使用13版购电卡!");
		return false;
	}
	//end

	if (meterType == window.top.SDDef.YFF_METER_TYPE_6103)	{
		var tmp = "00000000000000000000"
		if ((tmp != consNo) || (parseInt(buyTime) != 0)) {
			alert("换表前请先清空卡！")
			return false;
		} 
	}
	else {
		var tmp = "000000000000"
		if ((tmp != meterNo) || (tmp != consNo))		{
			alert("换表前请先清空卡，不能缴费！")
			return false;
		}
	}
	return true;
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
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintGy();
}

function disableBtn(){
	$("#btnBDNew"	).attr("disabled",true);
	$("#btnBDOld"	).attr("disabled",true);
	$("#btnChgmeter").attr("disabled",true);
}
