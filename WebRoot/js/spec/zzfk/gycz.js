//冲正
var rtnValue;
var gloQueryResult = { };
$(document).ready(function(){
	
	$("#btnSearch").click(function(){selcons()});
	
	$("#btnRever").click(function(){if(check())rever()});
	
	$("#btnPrt").click(function(){showPrint()});
	 
	$("#jfje").keyup(function(){calcu()});
	$("#jsje").keyup(function(){calcu()});
	$("#zbje").keyup(function(){calcu()});
	
	initGrid();
	
	IsDisabled();
});

function calcu(){
	
	var jfje   = 0.0, jsje = 0, zbje = 0, last_tt = 0;
	
	jfje = $("#jfje").val() === "" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val() === "" ? 0 : $("#jsje").val();
	zbje = $("#zbje").val() === "" ? 0 : $("#zbje").val();
	last_tt = $("#last_tt").val() === "" ? 0 : $("#last_tt").val();
	
	var zje = round((parseFloat(jfje) + parseFloat(jsje) + parseFloat(zbje)), 3)
	if(!isNaN(zje)){
		$("#zje").val(zje);
		setAlarmValue(rtnValue.yffalarm_alarm1, rtnValue.yffalarm_alarm2, zje);
		//当前余额=当前剩余金额+实际缴费金额-上一次缴费金额
		var dqsyje=0.0;
		dqsyje=$("#dqsyje").val()=== "" ? 0: $("#dqsyje").val();
		$("#dqye").html(round((parseFloat(dqsyje)+parseFloat(zje)-parseFloat(last_tt)),3));
	}
}

function selcons(){//检索

	var tmp = doSearch("zz",ComntUseropDef.YFF_GYOPER_REVER, rtnValue);
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, zjg_id : rtnValue.zjg_id, busi_no : rtnValue.userno};
	mis_query(param,gloQueryResult);
	
	//读取最近一次购电记录信息
	lastPayInfo();
	setConsValue(rtnValue);
	//当前剩余金额赋值
	$("#dqsyje" ).val(round(rtnValue.now_remain,3));
	$("#dqye"   ).html(round(rtnValue.now_remain,3));
	getGyYffRecs();
	getAlarmValue(rtnValue.yffalarm_id);//获取报警方式
	
	$("#buy_times").html($("#buy_times").val());

	getMoneyLimit(rtnValue.feeproj_id);

	UnDisabled();
	$("#btnPrt").attr("disabled",true);
	
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
}



function initGrid(){
	var yff_grid_title = '操作日期,操作类型,缴费金额(元),追补金额(元),结算金额(元),总金额(元),断电值,报警值1,报警值2,购电次数,流水号,缴费方式,操作员,&nbsp;';
	
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("150,80,80,80,80,80,80,80,80,80,150,80,80,*");
	mygrid.setColAlign("center,center,right,right,right,right,right,right,right,right,left,left,left");
	mygrid.enableTooltips("false,false,false,false,false,false,false,false,false,false,false,false,false");
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
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
	
	if(date_time.date != _today && gloQueryResult.misUseflag == "true") {
		alert("不能冲正非今天的记录...");
		return false;
	}
	
	//如果MIS不通，禁止冲正
	if (gloQueryResult.misUseflag == "true" && (gloQueryResult.misOkflag == "false" || gloQueryResult.misOkflag == undefined)) {
		alert("MIS不能通讯,禁止冲正...");	
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
//
	if (parseFloat($("#zje").html()) + parseFloat($("#dqye").html()) < 0) {
		alert("最新余额不能为负数");
		return false;
	}
	
	if (parseFloat($("#zje").html()) + parseFloat($("#dqye").html()) > money_limit) {
		alert("最新余额超出囤积限值");
		return false;
	}

    if(!isDbl("yffalarm_alarm1",  "报警金额1" ,0 , money_limit, false)){
		return false;
	}
    
    if(!isDbl("yffalarm_alarm2",  "报警金额2" ,0 , money_limit, false)){
		return false;
	}
    
    var jfje = $("#jfje").val();
    if(jfje == 0 || jfje == ""){
    	if(!confirm("用户未缴费,确定要冲正吗?"))return false;
    }
    
	return true;
}

function rever(){
	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.myffalarmid 		= $("#yffalarm_id").val();
	params.paytype 			= $("#pay_type").val();
	
	params.alarm_val1		= $("#yffalarm_alarm1").val();
	params.alarm_val2		= $("#yffalarm_alarm2").val();
	
	params.pay_money 		= $("#jfje").val() === "" ? 0 : $("#jfje").val();
	params.othjs_money 		= $("#jsje").val() === "" ? 0 : $("#jsje").val();
	params.zb_money 		= $("#zbje").val() === "" ? 0 : $("#zbje").val();
	
	params.all_money        = parseFloat(params.othjs_money)+parseFloat(params.zb_money) +parseFloat(params.pay_money);
	
	params.buy_dl 		= 0;
	params.pay_bmc	 	= 0;
	params.shutdown_bd 	= 0;
	
	params.oldopdate 	= date_time.date;
	params.oldoptime 	= date_time.time;	
	
	modalDialog.height 	= 300;
	modalDialog.width 	= 280;
	modalDialog.url 	= def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param 	= {
		showGSM : true,
		key	: ["客户编号","客户名称", "缴费金额","追补金额","结算金额","总金额"],
		val	: [$("#userno").html(),$("#username").html(), params.pay_money,params.zb_money,params.othjs_money,params.all_money]		
	};
	
	var o = modalDialog.show();
	
	if(!o || !o.flag) {
		return;
	}
	params.gsmFlag = o.gsm;//params 最后一个参数是否发送短信  赋值  进行action请求
	params.misUseflag = gloQueryResult.misUseflag;
	var json_str   = jsonString.json2String(params);
	
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
			{
				userData1		: params.rtu_id,
				zjgid 			: params.zjg_id,
				gsmflag 		: params.gsmFlag,
				gyOperRever		: json_str,
				userData2 		: ComntUseropDef.YFF_GYOPER_REVER
			},
			function(data) {			    	//回传函数
				loading.loaded();
				if (data.result == "success") {
					
					$("#btnPrt").attr("disabled", false);
					
					getGyYffRecs();
					IsDisabled();
					
					var ret_json = eval("(" + data.gyOperRever + ")");
					
					if(gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
						//MIS冲正, 不能隔天冲正, 请手工在MIS中进行冲正操作...
						if(_today == params.oldopdate && _today == parseInt(ret_json.op_date)){
							//mis.js函数
//							var rev_para = {};
//							rev_para.rtu_id = params.rtu_id;
//							rev_para.zjg_id = params.zjg_id;
//							rev_para.op_type= SDDef.YFF_OPTYPE_REVER;
//							rev_para.date	= ret_json.op_date;
//							rev_para.time	= ret_json.op_time;
//							rev_para.last_date = params.oldopdate;
//							rev_para.last_time = params.oldoptime;
//							rev_para.updateflag = 0;
//							rev_para.czjylsh = ret_json.wasteno;
//							rev_para.yjylsh = date_time.last_wastno;
//							rev_para.yhzwrq = ret_json.op_date;
//							rev_para.pay_money = params.pay_money;
//							rev_para.busi_no = rtnValue.userno;
							var rev_para = {};
							rev_para.rtu_id = params.rtu_id;
							rev_para.zjg_id = params.zjg_id;
							rev_para.op_type= SDDef.YFF_OPTYPE_REVER;
							rev_para.date	= ret_json.op_date;
							rev_para.time	= ret_json.op_time;
							rev_para.last_date = params.oldopdate;
							rev_para.last_time = params.oldoptime;
							rev_para.updateflag = 0;
							rev_para.czjylsh = ret_json.wasteno;
							rev_para.yjylsh = date_time.last_wastno;
							rev_para.yhzwrq = ret_json.op_date;
							rev_para.pay_money = params.pay_money;
							rev_para.busi_no = rtnValue.userno;
							
							if(provinceMisFlag == "HB"){
								
							}
							else if(provinceMisFlag == "HN"){
								rev_para.batch_no	= gloQueryResult.misBatchNo;	
								rev_para.hsdwbh		= gloQueryResult.misHsdwbh;
							}
							else if(provinceMisFlag == "GS"){
								rev_para.yhbh = rtnValue.userno;
								rev_para.dzpc = gloQueryResult.misDzpc;
							}
							
							mis_rever(rev_para);
						}
						else {
							alert("MIS冲正, 不能隔天冲正, 请手工在MIS中进行冲正操作...");
						}
					}
					alert("冲正成功!");
					$("#btnPrt").attr("disabled",false);
    				window.top.WebPrint.setYffDataOperIdx2params(data.gyOperRever);//打印用的参数

				}
				else {
					alert("向主站发送开户命令失败");
				}
			}
		);
}

//刚刚进入界面  各个input默认disabled
function IsDisabled(){
	$("#jfje").attr("disabled",true);
	$("#jsje").attr("disabled",true);
	$("#zbje").attr("disabled",true);
	$("#yffalarm_alarm1").attr("disabled",true);
	$("#yffalarm_alarm2").attr("disabled",true);
	
	$("#btnRever").attr("disabled",true);
}

//selcons 各个input属性disabled更改为 false
function UnDisabled(){
	$("#btnRever").attr("disabled", false);
	date_time = null;
	$("#jfje").attr("disabled",false);
	$("#jsje").attr("disabled",false);
	$("#zbje").attr("disabled",false);
	$("#yffalarm_alarm1").attr("disabled",false);
	$("#yffalarm_alarm2").attr("disabled",false);
	
	$("#jfje").attr("value","");
	$("#jsje").attr("value","");
	$("#zbje").attr("value","");
	$("#zje").attr("value","");
	$("#jsyhm").html("");
	$("#yhfl").html("");
}

function showPrint(){//打印发票
	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.gyje;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintGy();
}
