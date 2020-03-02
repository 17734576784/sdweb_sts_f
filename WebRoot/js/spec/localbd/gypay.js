var rtnValue;
var rtnBD;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	
	initGrid();
	
	$("#btnSearch").click(function(){selcons()});
	$("#rtuInfo").click(function(){rtuInfo()});
	$("#btnBuy").click(function(){if(check())buy_dl();});
	$("#btnPrt").click(function(){showPrint()});
	$("#chgMbphone").click(function(){changeMbphone()}); // 更换手机

	$("#zbje").keyup(function(){calcu()});
	$("#jsje").keyup(function(){calcu()});
	$("#jfje").keyup(function(){calcu()});
	
	setTextDisabled(true);
});

function setTextDisabled(mode){	//设置文本框状态（disabled/enabled）
	if(!mode){
		$("#zbje").val("");
		$("#jfje").val("");
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
	$("#chgMbphone").attr("disabled",mode);
}

function selcons(){//检索
	
	var tmp = doSearch("bd", ComntUseropDef.YFF_GYCOMM_PAY, rtnValue);
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	if(rtnValue.feeproj_type != SDDef.YFF_FEETYPE_DFL){
		alert("表底方式只允许单费率方案。如果要使用复费率方案，请使用金额方式缴费。");
		return;
	}
	if(rtnValue.pay_type == ComntDef.YD_PROTOCAL_NULL || rtnValue.prot_type != ComntDef.YD_PROTOCAL_KEDYH){	//科林大用户规约 或者 本地费控
		alert("此开户操作只支持科林大用户本地费控方式..");
		return;
	}
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, zjg_id : rtnValue.zjg_id, busi_no : rtnValue.userno};
	mis_query(param,gloQueryResult);
	
	cons_para.plus_width = "plus_width";
	setConsValue(rtnValue);
	$("#buy_times").html(rtnValue.buy_times);
	
	getMoneyLimit(rtnValue.feeproj_id);		//囤积限值
	getAlarmValue(rtnValue.yffalarm_id);	//获取报警方式
	
	getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
	
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
	$("#btnPrt").attr("disabled",true);
	
	setTextDisabled(false);
	
	calcu();
	
	$("#btnBuy").attr("disabled", true);
}

function calcu(){	//总金额报警止码及断电止码
	
	var jfje = 0.0, zbje = 0.0, jsje = 0.0, zje = 0.0;
	zbje = $("#zbje").val() === "" ? 0 : $("#zbje").val();
	jfje = $("#jfje").val() === "" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val() === "" ? 0 : $("#jsje").val();
	
	zje = round(parseFloat(zbje) + parseFloat(jfje) + parseFloat(jsje),def.point);
	
	if(isNaN(zje)){
		return;
	}
	$("#zje").html(zje);
	
	var dj 	= $("#feeproj_reteval").val() === "" ? 1 : $("#feeproj_reteval").val();
	var blv = $("#blv").html() === "" ? 1 : $("#blv").html();
	var zbd = $("#zbd").html() === "" ? 0 : $("#zbd").html();
	var alarm_val = $("#yffalarm_alarm1").val() === "" ? 1 : $("#yffalarm_alarm1").val();
	
	var ret_val = bd_calcu(zje, dj, blv, zbd, alarm_val, alarm_type, zbd);
	
	$("#buy_dl").html(ret_val.buy_dl);
	$("#pay_bmc").html(ret_val.pay_bmc);
	$("#shutdown_bd").html(ret_val.shutdown_bd);
	$("#alarm_code").val(ret_val.alarm_code);

}

function rtuInfo() {	//读取上次缴费信息
	modalDialog.height = 190;
	modalDialog.width = 750;
	modalDialog.url = def.basePath + "jsp/spec/dialog/callRemain.jsp";
	
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
	
	$("#btnBuy").attr("disabled",false);
	$("#jfje").focus();
	
	calcu();
}

function buy_dl(){		//缴费

	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.paytype 			= $("#pay_type").val();
	params.yffalarm_id 		= $("#yffalarm_id").val();
	params.buynum 			= $("#buy_times").val() === "" ? 1 : $("#buy_times").val();
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
	}else {
		params.plustime		= $("#plus_width").val() === "" ? 0 : $("#plus_width").val();
	}
	
	modalDialog.height = 300;
	modalDialog.width  = 280;
	modalDialog.url    = def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param  = {
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
	params.mp_id 			= 1;
	params.paytype 			= $("#pay_type").val();
	params.feeproj_id 		= $("#feeproj_id").val();
	params.myffalarmid 		= $("#yffalarm_id").val();
	params.buynum 			= $("#buy_times").val();
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();;
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	
	params.all_money		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),def.point);
	
	params.alarm_val1		= $("#alarm_code").val()===""?0:$("#alarm_code").val();
	params.alarm_val2		= $("#yffalarm_alarm2").val()===""?0:$("#yffalarm_alarm2").val();
	params.buy_dl			= $("#buy_dl").html();
	params.pay_bmc			= $("#pay_bmc").html();
	params.shutdown_bd		= $("#shutdown_bd").html();
	
	var mydate=new Date();
	var myM=mydate.getMonth()>8?(mydate.getMonth()+1).toString():'0' + (mydate.getMonth()+1);
	var myD=mydate.getDate()>9?mydate.getDate().toString():'0' + mydate.getDate();
	var date = mydate.getFullYear()+myM+myD;
	//增加表底信息
	for(var i = 0; i < 3; i++){
		eval("params.mp_id"  + i + "=-1");
		eval("params.date"  + i + "=" + date);
		eval("params.time"  + i + "=0");
		eval("params.bd_zy"  + i + "=0");
		eval("params.bd_zyj" + i + "=0");
		eval("params.bd_zyf" + i + "=0");
		eval("params.bd_zyp" + i + "=0");
		eval("params.bd_zyg" + i + "=0");
		eval("params.bd_zw"  + i + "=0");
		eval("params.bd_fy"  + i + "=0");
		eval("params.bd_fyj" + i + "=0");
		eval("params.bd_fyf" + i + "=0");
		eval("params.bd_fyp" + i + "=0");
		eval("params.bd_fyg" + i + "=0");
		eval("params.bd_fw"  + i + "=0");
	}
	params.mp_id0 = params.mp_id;
	if(!rtnBD.nowval) return;
	params.bd_zy0 = rtnBD.nowval;//将表底值传入正向测点1的总表底
	params.type = 'localbd';//本地表底
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
		{
			userData1		: params.rtu_id,
			zjgid			: params.zjg_id,
			mpid 			: params.mp_id,
			gsmflag 		: gsmFlag,
			gyOperPay	 	: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_PAY
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				
				window.top.addStringTaskOpDetail("接收预付费服务任务成功...");
				
				
				$("#btnBuy").attr("disabled", true);
				$("#buy_times").val(parseInt($("#buy_times").val())+1);
				$("#buy_times").html($("#buy_times").val());
				
				getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
				setTextDisabled(true);
							
				//向MIS系统缴费
				if(parseFloat(params.pay_money) > 0 && gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
					var ret_json = eval("(" + data.gyOperPay + ")");
					var mis_para 	= {};
					
//					mis_para.rtu_id = params.rtu_id;
//					mis_para.zjg_id = params.zjg_id;
//					mis_para.op_type= SDDef.YFF_OPTYPE_PAY;
//					mis_para.date 	= ret_json.op_date;
//					mis_para.time 	= ret_json.op_time;
//					mis_para.updateflag = 0;
//					mis_para.jylsh 	= ret_json.wasteno;
//					mis_para.yhbh 	= $("#userno").html();
//					mis_para.jfje 	= params.pay_money;
//					mis_para.yhzwrq = ret_json.op_date;
//					mis_para.jfbs 	= misJezbs;
//					
//					if(provinceMisFlag == "HN"){
//						mis_para.batch_no	= misBatchNo;	
//						mis_para.hsdwbh		= misHsdwbh;
//					}
//					var all_pay = 0.0;
//					for(var i = 0; i < misDetailsvect.length; i++) {
//						if(provinceMisFlag == "HB")		eval("mis_para.yhbh" + i + "=misDetailsvect["+i+"].yhbh");
//						eval("mis_para.ysbs" + i + "=misDetailsvect["+i+"].ysbs");
//						
//						if(provinceMisFlag == "HN"){
//							eval("mis_para.dfny" + i + "=misDetailsvect["+i+"].dfny");
//							eval("mis_para.dfje" + i + "=misDetailsvect["+i+"].dfje");
//							eval("mis_para.wyjje" + i + "=misDetailsvect["+i+"].wyjje");
//							var bcssje= Math.min(parseFloat(misDetailsvect[i].dfje) + parseFloat(misDetailsvect[i].wyjje), parseFloat(mis_para.jfje) - all_pay);
//							all_pay += bcssje;
//							eval("mis_para.bcssje" + i + "=" + bcssje);
//						}
//					}
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
				}
				$("#btnPrt").attr("disabled", false);
				window.top.WebPrint.setYffDataOperIdx2params(data.gyOperPay,window.top.WebPrint.nodeIdx.gyycbd);//打印用的参数
				alert("缴费成功!");
				rtnValue = null;
				
			}else{
				window.top.addStringTaskOpDetail("缴费成功,数据库存缴费记录失败.");
				alert("缴费失败.");
			}
		}
	);
}

//更换手机
function changeMbphone(){
	modalDialog.height = 160;
	modalDialog.width  = 200;
	modalDialog.url = def.basePath + "jsp/dialog/chgMbPhone.jsp";
	
	var rtn = rtnValue;
	rtn.flag = 'gy';
	modalDialog.param = rtn;
	var params = modalDialog.show();
	if(!params) return;
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post(def.basePath + "ajaxoper/actOperGy!taskProc.action", 
			{
				userData1		: params.rtu_id,
				zjgid 			: params.zjg_id,
				gsmflag 		: params.gsmFlag,
				gyOperResetDoc 	: json_str,
				userData2 		: ComntUseropDef.YFF_GYOPER_RESETDOC
			},
			function(data){
				loading.loaded();
				if (data.result == "success") {
					$("#tel").html(params.filed1);
				}
				else {
					alert("向主站发送更改手机命令失败");
				}
			}
	);
}

function check(){
	if(rtnValue == null)return;
	
	//如果MIS不通，禁止缴费
	if (gloQueryResult.misUseflag == "true" && (gloQueryResult.misOkflag == "false" || gloQueryResult.misOkflag == undefined)) {
		alert("MIS不能通讯,禁止缴费...");
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

	if(!isDbl("jfje","缴费金额", 0, money_limit, false)) {
		return false;
	}

	if (!isDbl("zbje", "追补金额", -money_limit, money_limit, false)) {
		return false;
	}

	if(!isDbl("jsje","结算金额", -money_limit, money_limit, false)) {
		return false;
	}

	if ((window.top.gynegativemoney.gylocbd.totflag == 0) &&
		($("#zje").html() < "0")) {
		alert("总金额不能为负数");		
		return false;
	}

	if(!isDbl_Html("zje" ,  "总金额"  , 0, money_limit, false)){//总金额应该在0~囤积值之间
		return false;
	}
	
	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
	}
	
	//rtnBD.remain_je : 终端内剩余金额  
	if (parseFloat($("#zje").html()) + parseFloat(rtnBD.remain_je) > money_limit) {
		alert("最新余额超出囤积限值");
		return false;
	}

//if($("#zje").html() === "" || $("#zje").html() === "0"){
//		alert("总金额错误,请输入追补金额/缴费金额/结算金额!");
//		$("#jfje").focus().select();
//		return false;
//	}

//	if(parseFloat(jfje) == 0){
//		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
//	}
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