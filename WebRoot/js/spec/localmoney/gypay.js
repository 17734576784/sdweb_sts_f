var rtnValue;
var payflag = false;//记录本页面是否执行完缴费操作。
var gsmFlag = 0;
var rtnBD = null;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };
	
$(document).ready(function(){
	
	$("#btnSearch").click(function(){selcons()});
	$("#chgMbphone").click(function(){changeMbphone()}); // 更换手机
	$("#btnPay").click(function(){if(check())payMoney()});
	
	$("#btnPrt").click(function(){showPrint()});
	
	$("#rtuInfo").click(function(){rtuInfo()});
	$("#btnImportBD").click(function(){Import()});//录入表底信息
	
	$("#zbje").keyup(function(){calcu()});
	$("#jsje").keyup(function(){calcu()});
	$("#jfje").keyup(function(){calcu()});
	
	initGrid();
	setTextDisabled(true);
});

function setTextDisabled(mode){	//设置文本框状态（disabled/enabled）
	if(!mode){
		$("#zbje").val("");
		$("#jfje").val("");
		$("#jsje").val("");
		$("#yffalarm_alarm1").val("");
		$("#yffalarm_alarm2").val("");
		$("#td_bdinf").html("");
		$("#zje").html("");
		$("#dqye").html("");
	}
	$("#zbje").attr("disabled",mode);
	$("#jfje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#yffalarm_alarm1").attr("disabled",mode);
	$("#yffalarm_alarm2").attr("disabled",mode);
	$("#chgMbphone").attr("disabled",mode);
}

function calcu(){
	if(!rtnValue)return;
	var zbje = 0.0, jfje = 0.0, jsje = 0.0;
	
	zbje = $("#zbje").val()==="" ? 0 : $("#zbje").val();
	jfje = $("#jfje").val()==="" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val()==="" ? 0 : $("#jsje").val();
	
	var zje = round(parseFloat(zbje) + parseFloat(jfje) + parseFloat(jsje),def.point);
	
	if(!isNaN(zje)){
		
		setAlarmValue(rtnValue.yffalarm_alarm1, rtnValue.yffalarm_alarm2, zje);
		
		$("#zje").html(zje);
	}
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
						buytimes 		: $("#buy_times").html(),
						blv				: $("#blv").html(),
						dj				: $("#feeproj_reteval").html(),
						cacl_type_desc	: $("#cacl_type_desc").html(),
						prot_type		: rtnValue.prot_type
						};
	var o = modalDialog.show();
	
	if(!o)return;
	$("#dqye").html(round(o.nowval, def.point));
	$("#btnPay").attr("disabled",false);
	if(!payflag)$("#btnPay").attr("disabled",false);//如果缴费成功，点击[终端内信息]按钮后。[缴费]按钮不启用。

}

function selcons(){//检索
	setTextDisabled(true);
	$("#btnPay").attr("disabled",true);
	$("#rtuInfo").attr("disabled",true);
	$("#btnImportBD").attr("disabled",true);
	var tmp = doSearch("je", ComntUseropDef.YFF_GYCOMM_PAY, rtnValue);
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, zjg_id : rtnValue.zjg_id, busi_no : rtnValue.userno};
	mis_query(param,gloQueryResult);
	
	setConsValue(rtnValue);
	
	getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id); 
	
	getMoneyLimit(rtnValue.feeproj_id);		//囤积限值
	getAlarmValue(rtnValue.yffalarm_id);	//获取报警方式
	
	//$("#rtuInfo").attr("disabled",false);
	$("#btnImportBD").attr("disabled",false);
	$("#btnPrt").attr("disabled",true);
	$("#buy_times").html($("#buy_times").val());
	
	payflag = false;
	
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

	if(!isDbl("jsje", "结算金额", -money_limit, money_limit, false)) {
		return false;
	}

	if ((window.top.gynegativemoney.gylocmny.totflag == 0) &&
		($("#zje").html() < "0")) {
		alert("总金额不能为负数");
		return false;
	}

	if(!isDbl("zje" ,  "总金额", -money_limit, money_limit, false)){//总金额应该在0~囤积值之间
		return false;
	}

	if (parseFloat($("#zje").html()) + parseFloat($("#dqye").html()) < 0) {
		alert("最新余额不能为负数");
		return false;
	}
	
	if (parseFloat($("#zje").html()) + parseFloat($("#dqye").html()) > money_limit) {
		alert("最新余额超出囤积限值");
		return false;
	}
	
	//20121220
	if (parseFloat($("#yffalarm_alarm1").val()) < 0) {
		alert("报警金额1不能为负数");
		return false;
	}
	
	if (parseFloat($("#yffalarm_alarm2").val()) < 0) {
		alert("报警金额2不能为负数");
		return false;
	}
	//end
	
	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?")) return false;
	}
	
	if(!rtnBD)
	{
		alert("未录入表底!");
		return;
	}

	return true;
}

function payMoney(){//缴费
	
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
	params.all_money 		= parseFloat(params.all_money) + parseFloat($("#dqye").html());

	if(parseFloat(params.all_money) > parseFloat(money_limit)){
		alert("缴费后总金额【"+params.all_money+"】大于囤积限值【"+money_limit+"】，请重新输入缴费金额。");
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
	
	var zje1 = $("#zje").html()=== "" ? 0 : $("#zje").html()
	
	modalDialog.height 	= 300;
	modalDialog.width 	= 280;
	modalDialog.url 	= def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param 	= {
		showGSM : true,
		key	: ["客户编号","客户名称", "缴费金额","追补金额","结算金额","总金额"],
		val	: [$("#userno").html(),$("#username").html(), params.pay_money,params.zb_money,params.othjs_money,zje1]		
	};
	
	var o = modalDialog.show();
	
	if(!o||!o.flag){
		return;
	}
	gsmFlag = o.gsm;
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
			gsmflag 		: gsmFlag,		//界面提示是否发送短信
			gyCommPay 		: json_str,
			userData2 		: ComntUseropDef.YFF_GYCOMM_PAY
		},
		function(data) {			    	//回传函数
			loading.loaded();			
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				addDB();
			}else{
				window.top.addStringTaskOpDetail("通讯失败.");
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
	params.buynum 			= $("#buy_times").val();
	
	params.alarm_val1		= $("#yffalarm_alarm1").val();
	params.alarm_val2		= $("#yffalarm_alarm2").val();
	
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money = round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),def.point);
	params.type 			= 'localmoney';//远程金额
	
	params.buy_dl = 0;
	params.pay_bmc = 0;
	params.shutdown_bd = parseFloat(params.all_money) + parseFloat($("#dqye").html());
	
	for(var i = 0; i < 3; i++){
		eval("params.mp_id"  + i + "=rtnBD.mp_id" + i);
		eval("params.date"  + i + "=rtnBD.date");
		eval("params.time"  + i + "=0");
		
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
			zjgid 			: params.zjg_id,
			gsmflag 		: gsmFlag,
			gyOperPay 		: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_PAY
		},
		function(data) {			    	//回传函数
			loading.loaded();
			
			if (data.result == "success") {
				
				window.top.addStringTaskOpDetail("接收预付费服务任务成功...");
				
				$("#btnPrt").attr("disabled",false);
				
				payflag = true;
				
				getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id); 
				
				$("#btnPay").attr("disabled", true);
				$("#buy_times").val(parseInt($("#buy_times").val())+1);
				$("#buy_times").html($("#buy_times").val());
				setTextDisabled(true);
	
				//向MIS系统缴费
				if(parseFloat(params.pay_money) > 0 && gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
					var ret_json = eval("(" + data.gyOperPay + ")");
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
				}
				alert("缴费成功!");
				$("#btnPrt").attr("disabled",false);
    			window.top.WebPrint.setYffDataOperIdx2params(data.gyOperPay,window.top.WebPrint.nodeIdx.gyycmoney);//打印用的参数

			}
			else {
				window.top.addStringTaskOpDetail("缴费成功,数据库存缴费记录失败.");
				alert("缴费失败.");
			}
		}
	);
}

function Import(){
	var tmp = importBD($("#rtu_id").val(),$("#zjg_id").val());
	if(!tmp){
		return;
	}
	if(tmp.td_bdinf == ""){
		$("#td_bdinf").html("未录入表底信息");
	}else{
		rtnBD = tmp;
		$("#td_bdinf").html(rtnBD.td_bdinf);
		$("#rtuInfo").attr("disabled",false);
	}
}

function showPrint(){//打印发票
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.gyycmoney);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintGy();
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
