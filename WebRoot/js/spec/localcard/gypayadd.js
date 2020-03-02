var rtnValue = null;
var openFlag = false;//是否开户
var bjlFlag  = false;//补记录操作是否已经完成。
var rtnBD	 = null;	//表底信息
var rtnzbd 	 = 0.00;

var WRITECARD_TYPE	= window.top.SDDef.YFF_CARDMTYPE_KE001;//卡表类型
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	initGrid();
		
	$("#btnSearch"  ).click(function(){selcons()});
	$("#btnReadCard").click(function(){readcard()});
	$("#cardInfo"   ).click(function(){window.top.card_comm.cardinfo()});//卡内信息
	$("#btnReWrite"	).click(function(){if(check())reWrite()});
	$("#btnImportBD").click(function(){Import()});
	$("#btnPrt"		).click(function(){showPrint()});
	
	$("#zbje").keyup(function(){calcu()});
	$("#jsje").keyup(function(){calcu()});
	$("#jfje").keyup(function(){calcu()});

	setDisabled(true);
});

function readcard(){//读卡检索
	loading.loading();
	window.top.card_comm.readCardSearchGy(-1);
}

function setSearchJson2Html(js_tmp){//调用readCardSearch后，readcard中异步执行完成后，执行此函数。
	loading.loaded();

	if(!js_tmp)return;
	if(!window.top.card_comm.checkInfo(js_tmp)) return;

	rtnValue = js_tmp;

	setConsValue(rtnValue);	
	if (setCardExt(rtnValue) == false)  return;

	getMoneyLimit(rtnValue.feeproj_id);			//囤积限值
	getAlarmValue(rtnValue.yffalarm_id);		//获取报警方式

	getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);							//读取缴费记录

	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, zjg_id : rtnValue.zjg_id, busi_no : rtnValue.userno};
	mis_query(param,gloQueryResult);
	
	setDisabled(false);
}

function selcons(){		//检索
	var js_tmp = doSearch("ks", -1, rtnValue);	//不限制用户状态
	if(!js_tmp)return;
	if(!window.top.card_comm.checkInfo(js_tmp)) return;

	rtnValue = js_tmp;

	setConsValue(rtnValue);
	if (setCardExt(rtnValue) == false)  return;

	getMoneyLimit(rtnValue.feeproj_id);			//囤积限值
	getAlarmValue(rtnValue.yffalarm_id);		//获取报警方式

	getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, zjg_id : rtnValue.zjg_id, busi_no : rtnValue.userno};
	mis_query(param,gloQueryResult);
	
	setDisabled(false);
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
		var zdl=0.0;
		zdl = zdl + parseFloat(isNaN(rtnBD.zbd0) ? 0 : rtnBD.zbd0);
		zdl = zdl + parseFloat(isNaN(rtnBD.zbd1) ? 0 : rtnBD.zbd1);
		zdl = zdl + parseFloat(isNaN(rtnBD.zbd2) ? 0 : rtnBD.zbd2);
		rtnzbd = round(zdl,def.point);
		$("#td_bdinf").html(rtnBD.td_bdinf);
	}
}

function reWrite(){//补记录 onclick
	
	if(openFlag){//补开户记录
		newOne();		
	}
	else{//补缴费记录
		payMoney();
	}
}

function setDisabled(mode){	//设置文本框状态（disabled/enabled）
	if(!mode){
		$("#zbje").val("");
		$("#jfje").val("");
		$("#jsje").val("");
		$("#yffalarm_alarm1").val("");
		$("#yffalarm_alarm2").val("");
		$("#zje").html("");
		$("#dqye").html("");
		rtnBD		= null;
	}
	
	$("#zbje").attr("disabled",mode);
	$("#jfje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#yffalarm_alarm1").attr("disabled",mode);
	$("#yffalarm_alarm2").attr("disabled",mode);

	$("#btnImportBD").attr("disabled",mode);
	//$("#cardInfo").attr("disabled",mode);

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
			var rtnbd = 0;
			
			if(openFlag){
				rtnbd = rtnzbd;
			}
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

//function initGrid(){
//	var yff_grid_title ='操作日期,操作类型,缴费金额(元),追补金额(元),结算金额(元),总金额(元),断电值,报警值1,报警值2,购电次数,流水号,缴费方式,操作员,&nbsp;';
//	
//	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
//	mygrid.setHeader(yff_grid_title);
//	mygrid.setInitWidths("150,80,80,80,80,80,80,80,80,80,150,80,80,*");
//	mygrid.setColAlign("center,center,right,right,right,right,right,right,right,right,left,left,left");
//	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
//	mygrid.init();
//	mygrid.setSkin("light");
//	mygrid.setColumnHidden(6, true);
//}
//
//function getGyYffRecs(){//缴费记录
//	mygrid.clearAll();
//	var param = "{\"rtu_id\":\"" + $("#rtu_id").val() + "\",\"zjg_id\":\"" +$("#zjg_id").val() + "\",\"top_num\":\"5\"}";
//	$.post( def.basePath + "ajax/actCommon!getGyYFFRecs.action",{result:param},	function(data) {			    	//回传函数
//		if (data.result != "") {
//			jsdata = eval('(' + data.result + ')');
//			mygrid.parse(jsdata,"json");
//		}
//	});
//}

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

	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
	}

/*	if (!isZfDbl("zbje", "追补金额")) {
		return false;
	}
	if (!isZfDbl("jsje", "结算金额")) {
		return false;
	}
	if(!isDbl("jfje","缴费金额")) {
		return false;
	}
	
	var jfje = 0, zbje = 0, jsje = 0;
	zbje = $("#zbje").val()==="" ? 0 : $("#zbje").val();
	jfje = $("#jfje").val()==="" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val()==="" ? 0 : $("#jsje").val();
	

	
	if($("#zje").html() === "" || $("#zje").html() === "0"){
		alert("总金额错误,请输入追补金额/缴费金额/结算金额!");
		$("#jfje").focus().select();
		return false;
	}

	if(jfje < 0){
		alert("缴费金额 不能为负, 请重填 缴费金额!");
		$("#jfje").focus().select();
		return false;
	}
	
	if($("#zje").html() < 0){
		alert("总金额 不能为负!");
		$("#jfje").focus().select();
		return false;
	}
*/	
	if(openFlag){
		var total_yzbdl = $("#total_yzbdl").val();
		var total_wzbdl = $("#total_wzbdl").val();
		if(isNaN(total_yzbdl)){
			alert("有功追补电量应该为数字!");
			$("#total_yzbdl").select().focus();
			return false;
		}
		if(isNaN(total_wzbdl)){
			alert("无功追补电量应该为数字!");
			$("#total_wzbdl").select().focus();
			return false;
		}
	}
	return true;
}

function payMoney(){//补缴费记录
	
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
	
	modalDialog.height = 300;
	modalDialog.width = 280;
	modalDialog.url = def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param = {
		showGSM : true,
		key	: ["客户编号","客户名称", "缴费金额","追补金额","结算金额","总金额"],
		val	: [$("#userno").html(),$("#username").html(), params.pay_money,params.zb_money,params.othjs_money,params.all_money]		
	};
	
	var o = modalDialog.show();
	
	if(!o||!o.flag){
		return;
	}
	var gsmFlag = o.gsm;
	
	params.date = 0;
	params.time = 0;
	params.type = 'localcard';//本地卡式
		
	params.buy_dl 			= $("#buy_dl").html()==="" ? 0:$("#buy_dl").html();
	params.pay_bmc 			= $("#pay_bmc").html()===""? 0:$("#pay_bmc").html();
	params.shutdown_bd 		= 0;
	
	params.mp_id0 = $("#zjg_id").val();
	params.mp_id1 = $("#zjg_id").val();
	params.mp_id2 = $("#zjg_id").val();
	
	params.date0 = 0;
	params.time0 = 0;
	params.date1 = 0;
	params.time1 = 0;
	params.date2 = 0;
	params.time2 = 0;
	
	params.bd_zy0  = 0;//$("#zbd").val()===""?0:$("#zbd").val();
	params.bd_zyj0 = 0;//$("#jbd").val()===""?0:$("#jbd").val();
	params.bd_zyf0 = 0;//$("#fbd").val()===""?0:$("#fbd").val();
	params.bd_zyp0 = 0;//$("#pbp").val()===""?0:$("#pbd").val();
	params.bd_zyg0 = 0;//$("#gbg").val()===""?0:$("#gbd").val();
	
	params.bd_zy1  = params.bd_zy0;
	params.bd_zyj1 = params.bd_zyj0;
	params.bd_zyf1 = params.bd_zyf0;
	params.bd_zyp1 = params.bd_zyp0;
	params.bd_zyg1 = params.bd_zyg0;
	
	params.bd_zy2  = params.bd_zy0; 
	params.bd_zyj2 = params.bd_zyj0;
	params.bd_zyf2 = params.bd_zyf0;
	params.bd_zyp2 = params.bd_zyp0;
	params.bd_zyg2 = params.bd_zyg0;
	
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
				bjlFlag = true;				
				getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
				window.top.WebPrint.setYffDataOperIdx2params(data.gyOperPay);//打印用的参数
				
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
				alert("补缴费记录成功!");				
				$("#btnReWrite").attr("disabled", true);
				$("#buy_times").val(parseInt($("#buy_times").val())+1);
				$("#buy_times").html($("#buy_times").val());
				
			}
			else {
				alert("补缴费记录失败!" + (data.operErr ? data.operErr : ''));
			}
		}
	);
}


function newOne(){						//补开户记录
	
	if(rtnBD == null)
	{
		alert("未录入表底!");
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
	
	params.all_money = round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),def.point);
	
	modalDialog.height = 300;
	modalDialog.width  = 280;
	modalDialog.url = def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param = {
		showGSM : true,
		key	: ["客户编号","客户名称", "缴费金额","追补金额","结算金额","总金额"],
		val	: [$("#userno").html(),$("#username").html(), params.pay_money,params.zb_money,params.othjs_money,params.all_money]		
	};
	var o = modalDialog.show();
	
	if(!o||!o.flag){
		return;
	}
	var gsmFlag = o.gsm;
	
	params.buy_dl 			= $("#buy_dl").html()==="" ? 0:$("#buy_dl").html();
	params.pay_bmc 			= $("#pay_bmc").html()===""? 0:$("#pay_bmc").html();
	params.shutdown_bd   	= 0;
	
	params.total_yzbdl 		= $("#total_yzbdl").val() == "" ? 0 : $("#total_yzbdl").val();
	params.total_wzbdl 		= $("#total_wzbdl").val() == "" ? 0 : $("#total_wzbdl").val();
	
	params.date 			= rtnBD.date;
	params.time				= 0;
	
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
			zjgid 			: params.zjg_id,
			gsmflag 		: 0,
			gyOperAddCus 	: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_ADDCUS
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				$("#btnReWrite").attr("disabled",true);
				$("#btnImportBD").attr("disabled",true);
				$("#btnPrt").attr("disabled",false);
				
				$("#buy_times").val(1);
				
				getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
    			window.top.WebPrint.setYffDataOperIdx2params(data.gyOperAddCus);//打印用的参数
				
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
				alert("补开户成功!");
				bjlFlag = true;		
			}
			else {
				alert("补开户记录失败!" + (data.operErr ? data.operErr : ''));
			}
		}
	);
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
//	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.gyks[WRITECARD_TYPE];
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintGy();
}

function setCardExt(rtnValue)
{
	if( rtnValue.cus_state_val == "" ||rtnValue.cus_state_val == SDDef.YFF_CUSSTATE_INIT || rtnValue.cus_state_val == SDDef.YFF_CUSSTATE_DESTORY){
		openFlag = true;
		$("#tr_kh").attr("style","display:block;");
		$("#kh_bd1").attr("style","display:block;");
		$("#kh_bd2").attr("style","display:block;");
		$("#kh_bd3").attr("style","display:block;");
	}else if(rtnValue.cus_state_val == SDDef.YFF_CUSSTATE_NORMAL){
		openFlag = false;
		$("#tr_kh").attr("style","display:none;");
		$("#kh_bd1").attr("style","display:none;");
		$("#kh_bd2").attr("style","display:none;");
		$("#kh_bd3").attr("style","display:none;");
	}else{
		alert("当前用户状态下不能补写记录。");
		return;
	}
		
	$("#buy_times").html($("#buy_times").val());

	var tmp_val = rtnValue.cardmeter_type.split("]");
	WRITECARD_TYPE = tmp_val[0].split("[")[1];
//调整 20130402	
//	WRITECARD_TYPE = tmp_val[0].substring(1);

	$("#cardmeter_type").html(tmp_val[1].split('"')[0]);
	$("#writecard_no").html(rtnValue.writecard_no);
	$("#meter_id").html(rtnValue.meter_id);

	var ke003f = (WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE003);

	if (ke003f) {
		$("#gdl").show();
		$("#gdldesc").show();
		$("#pay_bmc").show();
		$("#pay_bmcdesc").show();
		$("#alarm1").html("报警表码差1：");
		$("#alarm2").html("报警表码差2：");
	}
	else {
		$("#gdl").hide();
		$("#gdldesc").hide();
		$("#pay_bmc").hide();
		$("#pay_bmcdesc").hide();
		$("#alarm1").html("报警金额1(元)：");
		$("#alarm2").html("报警金额2(元)：");
	}
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
	
	//将不记录权限放开  测试通过后再还愿
	$("#btnReWrite").attr("disabled",false);
	$("#btnImportBD").attr("disabled",false);
	$("#btnPrt").attr("disabled",true);

	if(openFlag)$("#buy_times").val(0);

	bjlFlag = false;
}
