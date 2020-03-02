var rtnValue;
var rtnBD = null;
var rtnzbd = 0.00;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	
	$("#btnSearch").click(function(){selcons()});
	$("#btnNew").click(function(){if(check())newOne_toComm()});
	
	$("#btnImportBD").click(function(){Import()});
	
	$("#rtuInfo").click(function(){rtuInfo()});
	$("#btnPrt").click(function(){showPrint()});
	
	$("#zbje").keyup(function(){calcuZje();});
	$("#jfje").keyup(function(){calcuZje();});
	$("#jsje").keyup(function(){calcuZje();});
	
	setTextDisabled(true);
});

function setTextDisabled(mode){	//设置文本框状态（disabled/enabled）
	if(!mode){
		$("#zbje").val("");
		$("#jfje").val("");
		$("#jsje").val("");
		
		$("#alarm_code").val("");
		$("#buy_dl").html("");
		$("#zje").html("");
		$("#pay_bmc").html("");
		$("#shutdown_bd").html("");
		$("#jsyhm").html("");
		$("#yhfl").html("");
		$("#td_bdinf").html("");
		rtnBD = null;
	}
	$("#rtuInfo").attr("disabled",mode);
	//$("#btnNew").attr("disabled",mode);
	$("#zbje").attr("disabled",mode);
	$("#jfje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#total_yzbdl").attr("disabled",mode);
	$("#total_wzbdl").attr("disabled",mode);
	$("#alarm_code").attr("disabled",mode);
	
}

function selcons(){//检索
	var tmp = doSearch("bd",ComntUseropDef.YFF_GYOPER_ADDCUS,rtnValue);
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	if(rtnValue.feeproj_type != SDDef.YFF_FEETYPE_DFL){
		alert("表底方式只允许单费率方案。如果要使用复费率方案，请使用金额方式开户。");
		return;
	}
	if(rtnValue.pay_type == ComntDef.YD_PROTOCAL_NULL || rtnValue.prot_type != ComntDef.YD_PROTOCAL_KEDYH){	//科林大用户规约 或者 本地费控
		alert("此开户操作只支持科林大用户本地费控方式..");
		return;
	}
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, zjg_id : rtnValue.zjg_id, busi_no : rtnValue.userno};
	mis_query(param, gloQueryResult);
	
	cons_para.plus_width = "plus_width";
	cons_para.yffalarm_alarm1 = "alarm_val1";
	cons_para.yffalarm_alarm2 = "alarm_val2";
	
	setConsValue(rtnValue);
	
	getMoneyLimit(rtnValue.feeproj_id);		//囤积限值
	getAlarmValue(rtnValue.yffalarm_id);	//获取报警方式
	setTextDisabled(false);
	
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
	
	$("#rtuInfo").attr("disabled",false);
	$("#btnImportBD").attr("disabled",false);
	$("#btnPrt").attr("disabled",true);
	$("#buy_times").val(0);
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
	modalDialog.show();
}

function newOne_toComm(){		//开户-通讯
//zkz 20121126 通讯时 不再调节 报警止码 断电止码	
//	calcuZje();

	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.paytype 			= $("#pay_type").val();
	params.yffalarm_id 		= $("#yffalarm_id").val();
	params.buynum 			= 0;
	params.pay_money 		= $("#jfje").val()=== "" ? 0 : $("#jfje").val();
	params.othjs_money 		= $("#jsje").val()=== "" ? 0 : $("#jsje").val();
	params.zb_money 		= $("#zbje").val()=== "" ? 0 : $("#zbje").val();
	params.all_money 		= $("#zje").html()=== "" ? 0 : $("#zje").html();
	
	if(parseFloat(params.all_money) > parseFloat(money_limit)){
		alert("总金额【"+params.all_money+"】大于囤积限值【"+money_limit+"】");
		return;
	}
	
	params.alarmmoney		= 0;
	params.buydl			= $("#buy_dl").html()	=== "" ? 0 : $("#buy_dl").html();
	params.paybmc			= $("#pay_bmc").html()	=== "" ? 0 : $("#pay_bmc").html();
	params.alarmbd			= $("#alarm_code").val()=== "" ? 0 : $("#alarm_code").val();
	
	params.shutbd			= $("#shutdown_bd").html() ==="" ? 0 : $("#shutdown_bd").html();
	params.yffflag			= $("#start_yff_flag").attr('checked') ? 1 : 0;
	
	if ($("#dpfs").attr('checked')) {
		params.plustime		= 0;
	}
	else {
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
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				newOne_toDB(gsmFlag);
			}else{
				window.top.addStringTaskOpDetail("接收预付费服务任务失败.");
				alert("通讯失败..");
			}
		});

}

function newOne_toDB(gsmFlag){		//开户-DB
	
	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.mp_id 			= 1;
	params.paytype 			= $("#pay_type").val();
	params.feeproj_id 		= $("#feeproj_id").val();
	params.myffalarmid 		= $("#yffalarm_id").val();
	params.buynum 			= 1;
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();;
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	
	params.all_money		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),def.point);
	
	params.alarm_val1		= $("#alarm_code").val()=== "" ? 0 : $("#alarm_code").val();
	params.alarm_val2		= $("#alarm_val2").val();
	params.buy_dl			= $("#buy_dl").html();
	params.pay_bmc			= $("#pay_bmc").html();
	params.shutdown_bd		= $("#shutdown_bd").html();
	params.total_yzbdl 		= $("#total_yzbdl").val() == "" ? 0 : $("#total_yzbdl").val();
	params.total_wzbdl 		= $("#total_wzbdl").val() == "" ? 0 : $("#total_wzbdl").val();
	
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
			gsmflag 		: gsmFlag,
			gyOperAddCus 	: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_ADDCUS
		},
		function(data) {			    	//回传函数
			window.top.addJsonOpDetail(data.detailInfo);
			loading.loaded();
			if (data.result == "success") {
				$("#btnPrt").attr("disabled",false);
				$("#btnNew").attr("disabled",true);
				
				$("#buy_times").val(1);
				setTextDisabled(true);
				
				//mis缴费
				if(parseFloat(params.pay_money) > 0 && gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
					var ret_json = eval("(" + data.gyOperAddCus + ")");
					var mis_para 	= {};
				
					mis_para.rtu_id = ret_json.rtu_id;           
					mis_para.zjg_id = params.zjg_id;             
					mis_para.op_type= SDDef.YFF_OPTYPE_ADDRES;   
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
				}
				alert("开户成功!");
				$("#btnPrt").attr("disabled",false);
    			window.top.WebPrint.setYffDataOperIdx2params(data.gyOperAddCus,window.top.WebPrint.nodeIdx.gyycbd);//打印用的参数
			}
			else {
				window.top.addStringTaskOpDetail("向主站发送开户命令失败!");
				alert("开户失败!");
			}
		}
	);
}


function calcuZje(){
	
	var jfje = 0.0, zbje = 0.0, jsje = 0.0, zje = 0.0;
	zbje = $("#zbje").val()==="" ? 0 : $("#zbje").val();
	jfje = $("#jfje").val()==="" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val()==="" ? 0 : $("#jsje").val();
	
	zje = round(parseFloat(zbje) + parseFloat(jfje) + parseFloat(jsje),def.point);
	
	if(isNaN(zje)){
		return;
	}
	$("#zje").html(zje);
	
	var dj 	= $("#feeproj_reteval").val()===""?1:$("#feeproj_reteval").val();
	var blv = $("#blv").html()===""?1:$("#blv").html();
	var zbd = (rtnzbd==="" || isNaN(rtnzbd)) ? 0 : rtnzbd;
	var alarm_val = $("#alarm_val1").val() == "" ? 30 : $("#alarm_val1").val();
	
	var ret_val = bd_calcu(zje, dj, blv, zbd, alarm_val, alarm_type, rtnzbd);
	
	$("#buy_dl").html(ret_val.buy_dl);
	$("#pay_bmc").html(ret_val.pay_bmc);
	$("#shutdown_bd").html(ret_val.shutdown_bd);
	$("#alarm_code").val(ret_val.alarm_code);
	
}

function check(){
	//如果MIS不通，禁止操作
	if (gloQueryResult.misUseflag == "true" && (gloQueryResult.misOkflag == "false" || gloQueryResult.misOkflag == undefined)) {
		alert("MIS不能通讯,禁止开户...");	
		return false;
	}
	
	if(!rtnBD){
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

	if (!isDbl("zbje", "追补金额", -money_limit, money_limit, false)) {
		return false;
	}

	if(!isDbl("jfje","缴费金额", 0, money_limit, false)) {
		return false;
	}

	if(!isDbl("jsje","结算金额", -money_limit, money_limit, false)) {
		return false;
	}

	if(!isDbl_Html("zje" ,  "总金额", 0, money_limit, false)){//总金额应该在0~囤积值之间
		return false;
	}
	
//	if($("#zje").html() == 0){
//		return confirm("用户未缴费,要继续开户吗?");
//	}
	
	return true;
}

function showPrint(){//打印发票
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.gyycbd);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintGy();
}

function Import(){//录入
	var tmp = importBD($("#rtu_id").val(),$("#zjg_id").val());
	if(!tmp){
		return;
	}
	
	if(tmp.td_bdinf == ""){
		$("#td_bdinf").html("未录入表底信息");
	}else{
		rtnBD = tmp;
		$("#td_bdinf").html(rtnBD.td_bdinf);
		
		var zdl=0.0;
		zdl = zdl + parseFloat(isNaN(rtnBD.zbd0) ? 0 : rtnBD.zbd0);
		zdl = zdl + parseFloat(isNaN(rtnBD.zbd1) ? 0 : rtnBD.zbd1);
		zdl = zdl + parseFloat(isNaN(rtnBD.zbd2) ? 0 : rtnBD.zbd2);
		rtnzbd = round(zdl,def.point);
		$("#alarm_code").val(rtnzbd);
		$("#shutdown_bd").html(rtnzbd);
		$("#jfje").focus();
		$("#btnNew").attr("disabled",false);
	}
	//缴费完成后再次修改表底	
	calcuZje();
}