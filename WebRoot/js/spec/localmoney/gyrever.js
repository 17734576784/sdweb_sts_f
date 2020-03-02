var rtnValue;
var czflag  = false;
var gsmFlag = 0;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	
	$("#btnSearch").click(function(){selcons()});
	$("#btnRever").click(function(){if(check())rever()});
	$("#rtuInfo").click(function(){rtuInfo()});
	$("#btnPrt").click(function(){showPrint()});
	
	$("#jfje").keyup(function(){calcu()});
	$("#zbje").keyup(function(){calcu()});
	$("#jsje").keyup(function(){calcu()});
	
	initGrid();
	setTextDisabled(true);
});

function setTextDisabled(mode){	//设置状态
	if(!mode){
		$("#jfje").val("");
		$("#zbje").val("");
		$("#jsje").val("");
		$("#zje").val("");
		$("#yffalarm_alarm1").val("");
		$("#yffalarm_alarm2").val("");
		$("#dqye").html("");
		$("#jsyhm").html("");
		$("#yhfl").html("");
	}
	
	$("#jfje").attr("disabled",mode);
	$("#zbje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#yffalarm_alarm1").attr("disabled",mode);
	$("#yffalarm_alarm2").attr("disabled",mode);
	
}

function calcu(){
	
	var jfje = 0.0, jsje = 0, zbje = 0, last_tt = 0;
	
	jfje = $("#jfje").val() === "" ? 0 : $("#jfje").val();
	zbje = $("#zbje").val() === "" ? 0 : $("#zbje").val();
	jsje = $("#jsje").val() === "" ? 0 : $("#jsje").val();
	last_tt = $("#last_tt").val() === "" ? 0 : $("#last_tt").val();
	
	var zje = round((parseFloat(jfje) + parseFloat(jsje) + parseFloat(zbje)), 3);
	if(!isNaN(zje)){		
		setAlarmValue(rtnValue.yffalarm_alarm1, rtnValue.yffalarm_alarm2, zje);
		$("#zje").val(zje);
	}
	
	/**
	var jfje = 0.0, last_jfje = 0, last_zje = 0;
	jfje = $("#jfje").val()==="" ? 0 : $("#jfje").val();
	last_jfje = $("#last_je").val()==="" ? 0 : $("#last_je").val();
	last_zje = $("#last_tt").val()==="" ? 0 : $("#last_tt").val();
	if(!isNaN(jfje)){
		var zje = parseFloat(jfje) + parseFloat(last_zje) - parseFloat(last_jfje);
		
		setAlarmValue(rtnValue.yffalarm_alarm1, rtnValue.yffalarm_alarm2, zje);
		
		$("#zje").val(zje);
	}
	*/
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
	var ret = modalDialog.show();
	
	if(!ret)return;
	
	$("#dqye").html(round(ret.nowval, def.point));
	
	if(!czflag)$("#btnRever").attr("disabled",false);
}

function selcons(){//检索
	var tmp = doSearch("je",ComntUseropDef.YFF_GYCOMM_PAY, rtnValue);
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	
	//读取最近一次购电记录信息
	lastPayInfo();
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, zjg_id : rtnValue.zjg_id, busi_no : rtnValue.userno};
	mis_query(param, gloQueryResult);
	
	setConsValue(rtnValue);
	
	getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id); 
	
	getMoneyLimit(rtnValue.feeproj_id);		//囤积限值
	getAlarmValue(rtnValue.yffalarm_id);	//获取报警方式
	
	$("#buy_times").html($("#buy_times").val());
	
	$("#rtuInfo").attr("disabled",false);
	$("#btnRever").attr("disabled",true);
	$("#btnPrt").attr("disabled",true);
	
	czflag =  false;
	
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
	
	setTextDisabled(false);
	
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
	
}

function check(){	
	if(date_time.date != _today && misUseflag == "true") {
		alert("不能冲正非今天的记录...");
		return false;
	}
	
	//如果MIS不通，禁止缴费
	if (gloQueryResult.misUseflag == "true" && (gloQueryResult.misOkflag == "false" || gloQueryResult.misOkflag == undefined)) {
		alert("MIS不能通讯,禁止冲正...");
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

	if ((window.top.gynegativemoney.gylocmny.totflag == 0) &&
		($("#zje").val() < "0")) {
		alert("总金额不能为负数");
		return false;
	}
		
	if(!isDbl("zje" ,  "总金额", -money_limit, money_limit, false)){//缴费金额应该在0~囤积值之间
		return false;
	}

	if (parseFloat($("#zje").val()) + parseFloat($("#dqye").html()) < 0) {
		alert("最新余额不能为负数");
		return false;
	}
	
	if (parseFloat($("#zje").val()) + parseFloat($("#dqye").html()) > money_limit) {
		alert("最新余额超出囤积限值");
		return false;
	}
	
    if(!isDbl("yffalarm_alarm1",  "报警金额1" ,0 , money_limit, false)){
		return false;
	}
   
    if(!isDbl("yffalarm_alarm2",  "报警金额2" ,0 , money_limit, false)){
		return false;
	}
    
    if(jfje === "" || jfje === "0"){
		return confirm("用户未缴费,要继续冲正吗?");
	}
	
	return true;
}

function rever(){
	
	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.paytype 			= $("#pay_type").val();
	params.yffalarm_id 		= $("#yffalarm_id").val();
	params.buynum 			= $("#buy_times").val();
	
	params.pay_money 		= $("#jfje").val()=== "" ? 0 : $("#jfje").val();
	params.othjs_money 		= $("#jsje").val()=== "" ? 0 : $("#jsje").val();
	params.zb_money 		= $("#zbje").val()=== "" ? 0 : $("#zbje").val();
	params.all_money 		= $("#zje").val()=== "" ?  0 : $("#zje").val();
	params.all_money 		= parseFloat(params.all_money) + parseFloat($("#dqye").html() - parseFloat($("#last_tt").val()));
	
	if(parseFloat(params.all_money) > parseFloat(money_limit)){
		alert("冲正金额【"+params.all_money+"】大于囤积限值【"+money_limit+"】");
		return;
	}
	
	params.alarmmoney		= $("#yffalarm_alarm1").val();
	params.buydl			= 0;
	params.paybmc			= 0;
	params.alarmbd			= 0;
	
	params.shutbd			= 0;
	params.yffflag			= $("#start_yff_flag").attr('checked') ? 1 : 0;
	
	if ($("#dpfs").attr('checked')) {
		params.plustime		= 0;
	}
	else {
		params.plustime		= $("#plus_width").val() === "" ? 0 : $("#plus_width").val();
	}
	
	var zje1 = $("#zje").val()=== "" ? 0 : $("#zje").val();
	modalDialog.height = 300;
	modalDialog.width = 280;
	modalDialog.url = def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param = {
		showGSM : true,
		key	: ["客户编号","客户名称", "缴费金额","追补金额","结算金额","总金额"],
		val	: [$("#userno").html(),$("#username").html(), params.pay_money,params.zb_money,params.othjs_money,zje1]		
	};
	
	var o = modalDialog.show();
	
	if(!o||!o.flag){
		return;
	}
	gsmFlag = o.gsm;
	params.misUseflag = gloQueryResult.misUseflag;
	var json_str = jsonString.json2String(params);
	
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
			loading.loaded();
			
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				addDB();
			}else{
				window.top.addStringTaskOpDetail("接收预付费服务任务失败.");
				alert("通讯失败...");
			}
		}
	);
}

function addDB(){
	
	var params = {};
	
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.myffalarmid 		= $("#yffalarm_id").val();
	params.paytype 			= $("#pay_type").val();
	
	params.alarm_val1		= $("#yffalarm_alarm1").val();
	params.alarm_val2		= $("#yffalarm_alarm2").val();
	
	params.pay_money 		= $("#jfje").val()=== "" ? 0 : $("#jfje").val();
	params.othjs_money 		= $("#jsje").val()=== "" ? 0 : $("#jsje").val();
	params.zb_money 		= $("#zbje").val()=== "" ? 0 : $("#zbje").val();
	params.all_money 		= $("#zje").val()=== "" ? 0 : $("#zje").val();
	
	var lastje	=	$("#last_je").val()=== "" ? 0 : $("#last_je").val();
	
	params.buy_dl = 0;
	params.pay_bmc = 0;
	params.shutdown_bd = parseFloat(params.all_money) + parseFloat($("#dqye").html()) - parseFloat(lastje);
	
	params.oldopdate = date_time.date;
	params.oldoptime = date_time.time;
	params.misUseflag = gloQueryResult.misUseflag;
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
		{
			userData1		: params.rtu_id,
			zjgid 			: params.zjg_id,
			gsmflag 		: gsmFlag,
			gyOperRever		: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_REVER
		},
		function(data) {			    	//回传函数
			loading.loaded();
			
			if (data.result == "success") {
				
				window.top.addStringTaskOpDetail("接收预付费服务任务成功...");
				
				$("#btnPrt").attr("disabled",false);
				$("#btnRever").attr("disabled", true);
				czflag = true;
				getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id); 
				setTextDisabled(true);
				
				if(gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
					//MIS冲正, 不能隔天冲正, 请手工在MIS中进行冲正操作...
					var ret_json = eval("(" + data.gyOperRever + ")");
					if(_today == params.oldopdate && _today == parseInt(ret_json.op_date)){
						//mis.js函数
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
    			window.top.WebPrint.setYffDataOperIdx2params(data.gyOperRever,window.top.WebPrint.nodeIdx.gyycmoney);//打印用的参数

			}
			else {
				window.top.addStringTaskOpDetail("冲正成功,数据库存冲正记录失败.");
				
				alert("冲正失败!");
			}
		}
	);
}

function showPrint(){//打印发票
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.gyycmoney);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
//	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.gyje;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintGy();
}
