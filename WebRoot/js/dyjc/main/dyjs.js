//缴费

var jsonCons   = null;
var matchInfo  = ["有匹配用户信息,可以结算。",
				  "没有找到匹配的档案。",
				  "有匹配用户信息。费控状态错误（非金额费控、非主站算费、未开户）。",
				  "有匹配用户信息。无表底信息或信息不全。",
				  "有匹配用户信息。余额信息错误。"];

var matchFlag  = 0;//0:可结算; 其他: 不能结算 
var rtnValue   = null;
var bdyg   	   = [-1, -1, -1];
var mp_id  	   = [-1,-1,-1];
var clpNum 	   = 0;
var jsDate 	   = 0; //抄表日yyyymmdd:excel的yyyymm + zjgpay_para中的cb_dayhour的dd
var jsTime 	   = 0; //抄表日hhmm :zjgpay_para中的cb_dayhour的hh + "00"
var cbDayYe    = 0; //抄表日余额:excel中的余额。
var khDate 	   = 0;
var mis_jsje = 0, mis_jsflag = false;
var cacl_type, feectrl_type;		//计费方式,费控方式

var last_sel_id = -1;

$(document).ready(function(){
	initGridCons();
	initGrid();
	initCbym();
	
	$("#upload_bdye").click(function(){showUpload()});
	$("#btnRemain").click(function(){remainSum()});
	
	$("#btnPay").click(function(){
		if(check()){
		    payMoney();
		}
	});
	
	$("#jsje").keyup(function(){calcu()});
	
});

function initGridCons(){//用户列表 --qb add
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

function initCbym() {
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	if(month < 10) month = "0" + month;
	$("#cb_ym").val(year + "年" + month + "月");
}

function parsegrid_spec(resultdata){//必须先打开文件，提交from时走的
	if(resultdata != "" && resultdata != "null"){
		jsonCons = eval('(' + resultdata + ')');
		mygridCons.clearAll();
		mygridCons.parse(jsonCons, "", "json");
		mygridCons.selectRow(0);
		selOneCons(0);
	}
}

function selOneCons(id){//用户列表单击一行
	var row_num = mygridCons.getRowsNum();	
	if (last_sel_id == id && parseInt(row_num) > 1) return;
	
	last_sel_id = id;
	
	$("#jsje").val("");
	$("#buy_times").html("");
	$("#now_remain").html("");
	$("#zje").val("");
	$("#rtuonline_img").hide();
	$("#rtuonline_sp").html("");
	mygridYff.clearAll();
	
	$("#btnPay").attr("disabled",true);
	$("#cons_name").val(jsonCons.rows[id].data[0]);
	$("#cons_no").val(jsonCons.rows[id].data[1]);
	$("#rtu_id").val(jsonCons.rows[id].data[2]);
	$("#mp_id").val(jsonCons.rows[id].data[3]);
	
	bdyg  	= jsonCons.rows[id].data[4].split("_");
	cbDayYe = jsonCons.rows[id].data[5];
	
	cacl_type = jsonCons.rows[id].data[9];
	feectrl_type= jsonCons.rows[id].data[10];
	
	matchFlag = parseInt(jsonCons.rows[id].data[6]);
	
	var spanCons = "";
	spanCons += "用户名:" 	 + jsonCons.rows[id].data[0]; 
	spanCons += "，户号:" 	 + $("#cons_no").val();
	spanCons += "，预收金额:" + cbDayYe + "元";
	spanCons += "，<br/>有功(总):" + bdyg[0];
	
	if(parseFloat(bdyg[1]) > 0)spanCons += "、" + bdyg[1];
	if(parseFloat(bdyg[2]) > 0)spanCons += "、" + bdyg[2];
	spanCons +=  " " + matchInfo[matchFlag];
	
	$("#spanConsInfo").html(spanCons);
	
	search();
}

function showUpload(){//显示弹出窗口--上传页面
	modalDialog.height = 300;
	modalDialog.width  = 400;
	modalDialog.url    = def.basePath + "jsp/dyjc/dialog/uploadSG186BdYe.jsp";
	var o = modalDialog.show();
	
	if(!o && o!= ""){
		return;
	}
	
	parsegrid_spec(o);	
}


function setValue2Jsp(){//根据终端所选终端ID和总加组ID，初始化缴费部分。
	setConsValue(rtnValue);
	//购电次数 单独赋值
	$("#buy_times").html(rtnValue.buy_times);
	$("#now_remain").html(round(rtnValue.now_remain, def.point));
	getRecord(rtnValue.rtu_id, rtnValue.mp_id);
}

function isConsValid()
{
	if (matchFlag != 0) return false;	
	if ($("#rtu_id").val() == undefined || 
		$("#mp_id").val() == undefined || 
		$("#rtu_id").val() == "" || 
		$("#mp_id").val() == "" ) {
		
		return false;
		}
	return true;
}

function search(){//检索用户信息
	$("#btnRemain").attr("disabled",true);
	
	if (!isConsValid()) {
		setConsValue("",true);
		$("#sfbd_sj").html("");
		$("#sfbd").html("");
		mygrid.clearAll();
		return;
	}
	
	//检索用户信息---dyOper-预付费状态
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", //后台处理程序
		{
			userData1		: $("#rtu_id").val(),
			mpid 			: $("#mp_id").val(),
			gsmflag 		: 0,
			userData2		: ComntUseropDef.YFF_DYOPER_GPARASTATE
		},
		function(data) {			    	//回传函数			
			loading.loaded();
			if (data.result == SDDef.SUCCESS) {
				rtnValue = eval("(" + data.dyOperGParaState + ")");
				rtnValue.username = $("#cons_name").val();
				rtnValue.userno = $("#cons_no").val();
				
				khDate  = rtnValue.kh_date;
				
				//检索用户信息--查数据库
				var params = {};
				params.rtu_id 	= $("#rtu_id").val();
				params.mp_id 	= $("#mp_id").val();
				var jsonstr = jsonString.json2String(params);
				$.post( def.basePath + "ajaxdyjc/actSG186JSExcel!getOneCons.action",{result : jsonstr},	function(data) {
					if (data.result != "") {
						
						var json = eval('('+data.result+')');
						rtnValue.res_id		 = json.res_id;
						rtnValue.useraddr	 = json.useraddr;
						rtnValue.tel 		 = json.tel;
						rtnValue.factory	 = json.factory;
						rtnValue.blv 		 = json.blv;
						rtnValue.esamno 	 = json.meter_id;
						rtnValue.rtu_model	 = json.rtu_model;
						rtnValue.prot_type	 = json.prot_type;
						rtnValue.wiring_mode = json.jxfs;
						mp_id[0] = $("#mp_id").val();
						mp_id[1] = json.mp_id1;
						mp_id[2] = json.mp_id2;
						if(json.cb_dayhour == "" || json.cb_dayhour == "null"){
							alert("没有配置抄表日，请先配置抄表日再结算。");
						}else{
							var dt = ("000" + json.cb_dayhour);
							dt = dt.substring(dt.length - 4,dt.length);
							jsDate = dt.substring(0,2); 
							jsTime = dt.substring(2,4);
							if(matchFlag == 0 && rtnValue.cus_state_id ==1)
							{
								$("#btnRemain").attr("disabled",false);
							}
						}
						var online = rtnValue.onlineflag;
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
				alert("查找电表信息错误.." + (data.operErr ? data.operErr : ''));
				setConsValue("",true);
			}
		}
	);
}

function check(){
	if($("#btnPay").attr("disabled"))return false;

	var zje = $("#zje").val();
	
	if(zje == 0 ){//结算金额为0，不需要结算。
		return(confirm("结算金额为0,不需要结算。是否继续"));
	}
	
	return true;
}

function check_jsye() {
	mis_jsflag = false;
	
	var othjs_money = $("#jsje").val()=== "" ? 0 : $("#jsje").val();
	
	//结算金额为负时，其绝对值需小于(当前金额-报警值)
	if(parseFloat(othjs_money) < 0) {
		var alarm_val1 = rtnValue.yffalarm_alarm1
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
	}
	
	return true;
}


function payMoney(){//结算--其实就是走的缴费。结算金额为补差值。其余金额都置0。
	
	if(!check_jsye()) {
		return;
	}
	
	var params = {};
	
	params.rtu_id 		= $("#rtu_id").val();
	params.mp_id 		= $("#mp_id").val();
	params.paytype 		= $("#pay_type").val();
	//params.buynum 		= rtnValue.buy_times;
	params.buynum 		= rtnValue.buy_times + 1;
	
	params.alarm_val1	= rtnValue.yffalarm_alarm1;
	params.alarm_val2	= rtnValue.yffalarm_alarm2;
	
	params.pay_money 	= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 	= $("#jsje").val()===""?0:$("#jsje").val();
	params.jsflag = 0;
	if(mis_jsflag) {
		params.othjs_money 	= mis_jsje;
		params.misjs_money = $("#jsje").val()===""?0:$("#jsje").val();
		params.jsflag = 1;
	}
	params.zb_money 	= $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money    = round(parseFloat(params.zb_money) + parseFloat(params.pay_money) + parseFloat(params.othjs_money),def.point);
	
	modalDialog.height = 300;
	modalDialog.width = 280;
	modalDialog.url = def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param = {
		showGSM : false,
		key	: ["客户编号","用户名称", "缴费金额","追补金额","结算金额","总金额"],
		val	: [rtnValue.userno,rtnValue.username,params.pay_money,params.zb_money,$("#jsje").val()+(mis_jsflag?"("+mis_jsje+")":""),params.all_money]		
	};
	
	var o = modalDialog.show();
	
	if(!o||!o.flag){
		return;
	}
	
	var t_str = $("#cb_ym").val();
	var str_cb_ym = t_str.substring(0,4) + t_str.substring(5,7); 
	var cb_ymd    = str_cb_ym + jsDate;
	
	params.fxdf_ym = str_cb_ym;
	params.res_id = rtnValue.res_id;
	params.res_desc = $("#username").html();
	params.pay_type = rtnValue.pay_type;
	
	params.lastala_val1 = rtnValue.yffalarm_alarm1;
	params.lastala_val2 = rtnValue.yffalarm_alarm2;
	params.lastshut_val = rtnValue.shutdown_val;
	
	params.newala_val1 = rtnValue.yffalarm_alarm1;
	params.newala_val2 = rtnValue.yffalarm_alarm2;
	params.newshut_val = parseFloat(rtnValue.shutdown_val) + parseFloat(params.all_money);
	
	params.date = cb_ymd;
	params.time = jsTime;
	
	for(var i = 0; i < 3; i++){
		eval("params.mp_id" + i + "=mp_id[" + i + "]");
		
		eval("params.bd_zy"  + i + "=bdyg[" + i + "]");
		eval("params.bd_zyj" + i + "=0");
		eval("params.bd_zyf" + i + "=0");
		eval("params.bd_zyp" + i + "=0");
		eval("params.bd_zyg" + i + "=0");
	}
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			userData1		: params.rtu_id,
			mpid 			: params.mp_id,
			gsmflag 		: 0,
			dyOperJsbc 		: json_str,
			userData2 		: ComntUseropDef.YFF_DYOPER_JSBC
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				
				alert("结算缴费成功!");
				
				getRecord(rtnValue.rtu_id, rtnValue.mp_id);
				
				$("#buy_times").html(parseInt($("#buy_times").html())+1);
				$("#btnRemain").attr("disabled",true);
				$("#btnPay").attr("disabled",true);
			}
			else {
				alert("向主站发送命令失败");
			}
		}
	);
}


function remainSum(){//获取结算金额
	
	if(bdyg < 0){
		alert("没有表底示数,不能计算余额。");
		return;
	}
	
	var t_str = $("#cb_ym").val();
	var str_cb_ym = t_str.substring(0,4) + t_str.substring(5,7); 
	var cb_ymd    = str_cb_ym + jsDate;
	
	var params = {};
	params.rtu_id = $("#rtu_id").val();
	params.mp_id = $("#mp_id").val();
	
	params.date = cb_ymd;
	params.time = jsTime;
	
	for(var i = 0; i < 3; i++){
		eval("params.mp_id" + i + "=mp_id[" + i + "]");
		eval("params.bd_zy"  + i + "=bdyg[" + i + "]");
		eval("params.bd_zyj" + i + "=0");
		eval("params.bd_zyf" + i + "=0");
		eval("params.bd_zyp" + i + "=0");
		eval("params.bd_zyg" + i + "=0");
	}
	
	var json_str=jsonString.json2String(params);
	
	loading.loading();
	$.post(
		def.basePath + "ajaxoper/actOperDy!taskProc.action", 
		{
			userData1        :  params.rtu_id,
			mpid 			 :  params.mp_id,
			userData2        :  ComntUseropDef.YFF_DYOPER_GETREMAIN,
			dyOperGetRemain  :  json_str,
			gsmflag          :  0
		},
		function(data){
			loading.loaded();
			if(data.dyOperGetRemain == ""){
				alert(data.err_strinfo);
				return;
			}
			var json = eval("(" + data.dyOperGetRemain + ")");
			if(json.remain_val == ""){
				alert("数据出错");
			}else{
				var dqje = round(json.remain_val, def.point);
				
				$("#jsje").val(round(parseFloat(cbDayYe) - dqje, def.point));
				calcu();
				$("#btnPay").attr("disabled",false);
			}
		}
	);
}

function calcu(){
	var zbje = 0.0, jfje = 0.0, jsje = 0.0;
	
	zbje = $("#zbje").val() === "" ? 0 : $("#zbje").val();
	jfje = $("#jfje").val() === "" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val() === "" ? 0 : $("#jsje").val();
	
	var zje = round(parseFloat(zbje) + parseFloat(jfje) + parseFloat(jsje),def.point);
	
	if(!isNaN(zje)){
		$("#zje").val(zje);
	}
}

//刚刚进入界面  各个input默认disabled
function IsDisabled(){
	$("#zbje"           ).attr("disabled",true);
	$("#jfje"           ).attr("disabled",true);
	$("#jsje"           ).attr("disabled",true);
	
	$("#btnPay").attr("disabled",true);
}
