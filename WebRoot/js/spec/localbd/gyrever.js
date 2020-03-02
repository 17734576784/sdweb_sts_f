var rtnValue;
var last_shutdownbd = 0;
var czFlag = false;//是否执行过冲正操作。
var rtnBD = null;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	
	initGrid();
	
	$("#btnSearch").click(function(){selcons()});
	$("#rtuInfo").click(function(){rtuInfo()});
	$("#btnCZ").click(function(){if(check())rever();});
	
	$("#jfje").keyup(function(){calcu()});
	$("#zbje").keyup(function(){calcu()});
	$("#jsje").keyup(function(){calcu()});
	
	$("#plus_width").keyup(function(){if(isNaN(this.value))this.value=0;});
	$("#btnPrt").click(function(){showPrint()});//显示打印界面
	
	setTextDisabled(true);
	
});

function setTextDisabled(mode){	//设置文本框状态（disabled/enabled）
	if(!mode){
		$("#jfje").val("");
		$("#zbje").val("");
		$("#jsje").val("");
		$("#alarm_code").val("");
		
		$("#zbd").html("");
		$("#buy_dl").html("");
		$("#zje").html("");
		$("#pay_bmc").html("");
		$("#shutdown_bd").html("");
		$("#jsyhm").html("");
		$("#yhfl").html("");
	}
	
	$("#zbje").attr("disabled",mode);
	$("#jfje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#alarm_code").attr("disabled",mode);
}

function selcons(){//检索
	var tmp = doSearch("bd", ComntUseropDef.YFF_GYOPER_REVER, rtnValue);
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	if(rtnValue.pay_type == ComntDef.YD_PROTOCAL_NULL || rtnValue.prot_type != ComntDef.YD_PROTOCAL_KEDYH){	//科林大用户规约 或者 本地费控
		alert("此开户操作只支持科林大用户本地费控方式..");
		return;
	}
	
	//读取最近一次购电记录信息
	lastPayInfo();
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, zjg_id : rtnValue.zjg_id, busi_no : rtnValue.userno};
	mis_query(param, gloQueryResult);
	
	cons_para.plus_width = "plus_width";
	setConsValue(rtnValue);
	$("#buy_times").html(rtnValue.buy_times);
	
	//$("#last_jfje").val(rtnValue.pay_money);
	//$("#last_zje").val(rtnValue.all_money);
	
	getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
	
	getMoneyLimit(rtnValue.feeproj_id);		//囤积限值
	getAlarmValue(rtnValue.yffalarm_id);	//获取报警方式
	
	var tmp_str = "";
	if((rtnValue.prot_st == 0) && (rtnValue.prot_ed == 0)) 
		tmp_str = "正常用电";
	else if(rtnValue.prot_st == rtnValue.prot_ed)
		tmp_str = "全天保电";
	else 
		tmp_str = "时段保电";
	$("#prot_state").html(tmp_str);
	
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
	
	$("#btnImportBD").attr("disabled",false);
	$("#rtuInfo").attr("disabled",false);
	$("#btnCZ").attr("disabled",true);
	$("#btnPrt").attr("disabled",true);//设置打印按钮不可用
	setTextDisabled(false);
}

function calcu(){	//总金额报警止码及断电止码
	
	var jfje = 0.0, jsje = 0, zbje = 0, zje = 0, last_tt = 0;
	
	jfje = parseFloat($("#jfje").val() === "" ? 0 : $("#jfje").val());
	zbje = parseFloat($("#zbje").val() === "" ? 0 : $("#zbje").val());
	jsje = parseFloat($("#jsje").val() === "" ? 0 : $("#jsje").val());
	zje = round((parseFloat(jfje) + parseFloat(jsje) + parseFloat(zbje)), 3);
	
	if(isNaN(zje)){
		return;
	}
	$("#zje").html(zje);

	
	var dj 	= parseFloat($("#feeproj_reteval").val() === "" ? 1 : $("#feeproj_reteval").val());
	var blv = parseFloat($("#blv").html() === "" ? 1 : $("#blv").html());
	var shutdown_bd = last_shutdownbd;
	var alarm_val = parseFloat($("#yffalarm_alarm1").val()=== "" ? 1 : $("#yffalarm_alarm1").val());
	
	var buy_dl = round(zje / dj, def.point);
	var bmc = round(buy_dl / blv, def.point);
	var new_sd_bd = bmc + shutdown_bd;
	if(new_sd_bd < 0) new_sd_bd = 0;
	
	var alarm_code = 0;
	
	if(alarm_type == 0){	//固定值方式
		alarm_code = round(new_sd_bd - alarm_val, def.point);
	}
	else{					//比例方式
		alarm_code = round(new_sd_bd - zje * alarm_val * 0.01 / (dj * blv), def.point);
	}
	new_sd_bd = round(new_sd_bd, def.point);
	
	$("#alarm_code").val(alarm_code);
	$("#shutdown_bd").html(new_sd_bd);
	
	$("#buy_dl").html(buy_dl);
	$("#pay_bmc").html(bmc);
}

function calcu_call(){	//总金额报警止码及断电止码
	
	var zje = 0, last_tt = 0;

	last_tt = parseFloat($("#last_tt").val() === "" ? 0 : $("#last_tt").val());
	var dj 	= parseFloat($("#feeproj_reteval").val() === "" ? 1 : $("#feeproj_reteval").val());
	var blv = parseFloat($("#blv").html() === "" ? 1 : $("#blv").html());
	var zbd = parseFloat($("#zbd").html() === "" ? 0 :$("#zbd").html());
	var alarm_val = parseFloat($("#yffalarm_alarm1").val()=== "" ? 1 : $("#yffalarm_alarm1").val());
	
	var buy_dl = round((zje - last_tt) / dj, def.point);
	var bmc = round(buy_dl / blv, def.point);
	var new_sd_bd = bmc + zbd;
	if(new_sd_bd < 0) new_sd_bd = 0;
	
	//var tt_num = jfje + last_zje - last_jfje;
	
	var alarm_code = 0;
	
	if(alarm_type == 0){	//固定值方式
		alarm_code = round(new_sd_bd - alarm_val, def.point);
	}
	else{					//比例方式
		alarm_code = round(new_sd_bd - zje * alarm_val * 0.01 / (dj * blv), def.point);
	}
	new_sd_bd = round(new_sd_bd, def.point);
	
	$("#alarm_code").val(alarm_code);
	$("#shutdown_bd").html(new_sd_bd);
	last_shutdownbd = new_sd_bd;
}

function rtuInfo() {	//读取上次缴费信息
	modalDialog.height = 190;
	modalDialog.width  = 750;
	modalDialog.url    = def.basePath + "jsp/spec/dialog/callRemain.jsp";
	
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
	rtnBD = o;
	if(!o)return;
	
	$("#zbd").html(o.totval);
	if(!czFlag){
		$("#btnCZ").attr("disabled",false);	
	}	
	calcu_call();
}

function rever(){		//缴费

	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.paytype 			= $("#pay_type").val();
	params.yffalarm_id 		= $("#yffalarm_id").val();
	params.buynum 			= $("#buy_times").val();
	
	params.pay_money 		= $("#jfje").val()=== "" ? 0 : $("#jfje").val();
	params.othjs_money 		= $("#jsje").val()=== "" ? 0 : $("#jsje").val();
	params.zb_money 		= $("#zbje").val()=== "" ? 0 : $("#zbje").val();
	params.all_money 		= $("#zje").html()=== "" ? 0 : $("#zje").html();
	
	if(parseFloat(params.pay_money) > parseFloat(money_limit)){
		alert("冲正金额【"+params.all_money+"】大于囤积限值【"+money_limit+"】");
		return;
	}
	
	params.alarmmoney		= 0;
	params.buydl			= $("#buy_dl").html()	=== "" ? 0 : $("#buy_dl").html();
	params.paybmc			= $("#pay_bmc").html()	=== "" ? 0 : $("#pay_bmc").html();
	params.alarmbd			= $("#alarm_code").val()=== "" ? 0 : $("#alarm_code").val();
	
	params.shutbd			= $("#shutdown_bd").html() ==="" ? 0 : $("#shutdown_bd").html();
	params.yffflag			= $("#start_yff_flag").attr('checked') ? 1 : 0;
	params.misUseflag = gloQueryResult.misUseflag;
	params.op_type = ComntUseropDef.YFF_GYOPER_REVER;
	
	if ($("#dpfs").attr('checked')) {
		params.plustime		= 0;
	}else {
		params.plustime		= $("#plus_width").val() === "" ? 0 : $("#plus_width").val();
	}
	
	modalDialog.height = 300;
	modalDialog.width = 280;
	modalDialog.url = def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param = {
		showGSM : true,
		key	: ["客户编号","客户名称", "缴费金额","追补金额","结算金额","总金额"],
		val	: [$("#userno").html(),$("#username").html(), params.pay_money,params.zb_money,params.othjs_money,params.all_money]		
	};
	
	var o = modalDialog.show();
	if(!o.flag){
		return;
	}
	var gsmFlag = o.gsm;
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
		window.top.addJsonOpDetail(data.detailInfo);
		loading.loaded();
		if (data.result == "success") {
			addDB(gsmFlag);
		}else{
			window.top.addStringTaskOpDetail("通讯失败.");
			alert("通讯失败..");
		}
	});
}

function addDB(gsmFlag){
	
	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.paytype 			= $("#pay_type").val();
	params.feeproj_id 		= $("#feeproj_id").val();
	params.myffalarmid 		= $("#yffalarm_id").val();
	params.buynum 			= $("#buy_times").val();
	
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()=== "" ? 0 : $("#jsje").val();
	params.zb_money 		= $("#zbje").val()=== "" ? 0 : $("#zbje").val();
	params.all_money 		= $("#zje").html()=== "" ? 0 : $("#zje").html();
	
	params.alarm_val1		= $("#alarm_code").val()=== "" ? 0 : $("#alarm_code").val();
	params.alarm_val2		= $("#yffalarm_alarm2").val()===""?0:$("#yffalarm_alarm2").val();
	
	params.buy_dl			= $("#buy_dl").html();
	params.pay_bmc			= $("#pay_bmc").html();
	params.shutdown_bd		= $("#shutdown_bd").html();
	
	params.oldopdate = date_time.date;
	params.oldoptime = date_time.time;
	params.misUseflag = gloQueryResult.misUseflag;
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
		{
			userData1		: params.rtu_id,
			zjgid			: params.zjg_id,
			gsmflag 		: gsmFlag,
			gyOperRever	 	: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_REVER
		},
		function(data) {			    	//回传函数
			
			loading.loaded();
			
			if (data.result == "success") {
				
				window.top.addStringTaskOpDetail("接收预付费服务任务成功...");
				
				//$("#btnPrt").attr("disabled",false);
				$("#btnCZ").attr("disabled",true);
				
				getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
				setTextDisabled(true);
				
				if(gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
					//MIS冲正, 不能隔天冲正, 请手工在MIS中进行冲正操作...
					var ret_json = eval("(" + data.gyOperRever + ")");
					if(_today == params.oldopdate && _today == parseInt(ret_json.op_date)){
						
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
				$("#btnPrt").attr("disabled",false);//冲正成功后设置打印按钮可用
				czFlag = true;
    			window.top.WebPrint.setYffDataOperIdx2params(data.gyOperRever,window.top.WebPrint.nodeIdx.gyycbd);//打印用的参数
			}else{
				
				window.top.addStringTaskOpDetail("冲正成功,数据库存冲正记录失败.");
				
				alert("冲正失败!");
			}
		}
	);
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

	if ((window.top.gynegativemoney.gylocbd.totflag == 0) &&
		($("#zje").html() < "0")) {
		alert("总金额不能为负数");		
		return false;
	}

	if(!isDbl_Html("zje" ,  "总金额"  ,-money_limit , money_limit, false)){//总金额应该在0~囤积值之间
		return false;
	}
	
	if (parseFloat($("#zje").html()) + parseFloat(rtnBD.remain_je) > money_limit) {
		alert("最新余额超出囤积限值");
		return false;
	}

	return true;
}
function showPrint(){//打印发票
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.gyycbd);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.prt_params.scdd_bd = $("#zbd").html();			//上次断电表底
	window.top.WebPrint.doPrintGy();
}

