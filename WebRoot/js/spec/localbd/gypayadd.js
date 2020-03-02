var rtnValue;
var openFlag = false;//是否开户
var bjlFlag  = false;//补记录操作是否已经完成。
var rtnBD = null;
var rtu_BD = null;
var rtnzbd = 0.00;
var compareFlag = false;//查询终端后,则进行比较 ,如果没有查询终端则不进行比较
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	
	initGrid();
	
	$("#btnSearch").click(function(){selcons()});
	 $("#btnPrt").click(function(){showPrint()});
	
	$("#rtuInfo").click(function(){rtuInfo()});
	$("#btnImportBD").click(function(){Import()});
	$("#btnBuy").click(function(){if(check())reWrite();});

	$("#zbje").keyup(function(){openFlag ? calcuOpen() : calcuPay() });
	$("#jsje").keyup(function(){openFlag ? calcuOpen() : calcuPay() });
	$("#jfje").keyup(function(){openFlag ? calcuOpen() : calcuPay() });

	setTextDisabled(true);
});

function reWrite(){//补记录 onclick
	
	if(openFlag){//补开户记录
		newOne();		
	}
	else{//补缴费记录
		payMoney();
	}
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
		$("#alarm_code").html(rtnzbd);
		$("#shutdown_bd").html(rtnzbd);
		$("#jfje").focus();
		$("#btnBuy").attr("disabled",false);
	
	}
}

function setTextDisabled(mode){	//设置文本框状态（disabled/enabled）
	if(!mode){
		$("#zbje").val("");
		$("#jfje").val("");
		$("#jsje").val("");
		$("#alarm_code").html("");
		//$("#compare_detail").html("");
		//$("#rtu_alarm_code").html("");
		//$("#rtu_shutdown_bd").html("");
		$("#zbd").val("");
		$("#buy_dl").html("");
		$("#zje").html("");
		$("#pay_bmc").html("");
		$("#shutdown_bd").html("");
	}
	
	$("#zbje").attr("disabled",mode);
	$("#jfje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
}

function selcons(){//检索
	$("#btnBuy").attr("disabled",true);
	var tmp = doSearch("bd", -1, rtnValue);
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	
	if( rtnValue.cus_state_val == "" ||rtnValue.cus_state_val == SDDef.YFF_CUSSTATE_INIT || rtnValue.cus_state_val == SDDef.YFF_CUSSTATE_DESTORY){
		openFlag = true;
		rtnValue.buy_times = 0;
	}else if(rtnValue.cus_state_val == SDDef.YFF_CUSSTATE_NORMAL){
		openFlag = false;
	}else{
		alert("当前用户状态下不能补写记录。");
		return;
	}
	
	if(rtnValue.feeproj_type != SDDef.YFF_FEETYPE_DFL){
		alert("表底方式只允许单费率方案。如果要使用复费率方案，请使用金额方式缴费。");
		return;
	}
	if(rtnValue.pay_type == ComntDef.YD_PROTOCAL_NULL || rtnValue.prot_type != ComntDef.YD_PROTOCAL_KEDYH){	//科林大用户规约 或者 本地费控
		alert("此操作只支持科林大用户本地费控方式..");
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
		
	setTextDisabled(false);
	set_tr_show();
	   
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
	
	$("#btnPrt").attr("disabled",true);
	
	if(openFlag){
		$("#btnImportBD").attr("disabled",false);
		$("#rtuInfo").attr("disabled",false);
		calcuOpen();
	}else{
		$("#rtuInfo").attr("disabled",false);
		$("#zbd").val(rtnValue.shutdown_val);
//		calcuPay();
	}

}

function set_tr_show(){// 显示/隐藏:表底信息/上次缴费信息。
	var showopen = "none";
	var showpay  = "block";
	
	if(openFlag){
		showopen = "block";
		showpay  = "none";
	}
	$("#tr_open1").attr("style","display:" + showopen + ";");
	$("#tr_open2").attr("style","display:" + showopen + ";");
	$("#tr_open3").attr("style","display:" + showopen + ";");
	
	$("#tr_pay1").attr("style","display:" + showpay + ";");
	$("#tr_pay2").attr("style","display:" + showpay + ";");
}

function calcuPay(){	//缴费-计算总金额报警止码及断电止码
	
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
	var zbd = $("#zbd").val()===""?0:$("#zbd").val();
	var alarm_val = $("#yffalarm_alarm1").val()==="" ? 1 : $("#yffalarm_alarm1").val();
	
	var ret_val = bd_calcu(zje, dj, blv, zbd, alarm_val, alarm_type, zbd);
	$("#buy_dl").html(ret_val.buy_dl);
	$("#pay_bmc").html(ret_val.pay_bmc);

	$("#shutdown_bd").html(ret_val.shutdown_bd);
	$("#alarm_code").html(ret_val.alarm_code);
	
	compare()
}

function calcuOpen(){//开户-计算总金额报警止码及断电止码
	
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
	var alarm_val = $("#yffalarm_alarm1").val() === "" ? 30 : $("#yffalarm_alarm1").val();
	
	var ret_val = bd_calcu(zje, dj, blv, zbd, alarm_val, alarm_type, rtnzbd);
	$("#buy_dl").html(ret_val.buy_dl);
	$("#pay_bmc").html(ret_val.pay_bmc);
	$("#shutdown_bd").html(ret_val.shutdown_bd);
	$("#alarm_code").html(ret_val.alarm_code);
	
	compare()
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
	rtu_BD = o;
	if(!o)return;
	//将终端查询标志位设置为true
	compareFlag = true
	if(!bjlFlag && !openFlag){
		$("#btnBuy").attr("disabled",false);	
	}
	
	$("#btnBuy").attr("disabled",false);
	//上次终端信息赋值
	$("#rtu_alarm_code").html(o.alarm_val1);//报警值
	$("#rtu_shutdown_bd").html(o.totval);//断电码

	compare();
	
//	if (!openFlag){//补缴费记录时，计算金额。开户的终端内信息只供查看，不用返回值。
//		$("#zbd").val(o.totval);
//		$("#jfje").focus();
//		calcuPay();	
//	}
}

function payMoney(gsmFlag){//补写缴费记录。
	
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
	
	params.alarm_val1		= $("#alarm_code").html()===""?0:$("#alarm_code").html();
	params.alarm_val2		= $("#yffalarm_alarm2").val()===""?0:$("#yffalarm_alarm2").val();
	params.buy_dl			= $("#buy_dl").html();
	params.pay_bmc			= $("#pay_bmc").html();
	params.shutdown_bd		= $("#shutdown_bd").html();
	
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
	if(!rtu_BD.nowval) return;
	params.bd_zy0 = rtu_BD.nowval;//将表底值传入正向测点1的总表底
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
				
				$("#btnPrt").attr("disabled", false);
				$("#btnBuy").attr("disabled", true);
				window.top.WebPrint.setYffDataOperIdx2params(data.gyOperPay,window.top.WebPrint.nodeIdx.gyycbd);//打印用的参数
			
				$("#buy_times").val(parseInt($("#buy_times").val())+1);
				$("#buy_times").html($("#buy_times").val());
				bjlFlag = true;
				getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);

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
				
				setTextDisabled(true);
				
				
			}else{
				alert("补缴费记录失败.");
			}
		}
	);
}

function newOne(){//补开户记录
	
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
	
	params.alarm_val1		= $("#alarm_code").html()=== "" ? 0 : $("#alarm_code").html();
	params.alarm_val2		= $("#yffalarm_alarm2").val()==="" ? 0 : $("#yffalarm_alarm2").val();
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
			loading.loaded();
			if (data.result == "success") {
				$("#btnPrt").attr("disabled",false);
				$("#btnBuy").attr("disabled",true);
				$("#btnImportBD").attr("disabled", true);
				getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
				//mis缴费
				if(parseFloat(params.pay_money) > 0 && gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
					var ret_json = eval("(" + data.gyOperAddCus + ")");
					var mis_para 	= {};
					
//					mis_para.rtu_id = ret_json.rtu_id;
//					mis_para.zjg_id = params.zjg_id;
//					mis_para.op_type= SDDef.YFF_OPTYPE_ADDRES;
//					mis_para.date 	= ret_json.op_date;
//					mis_para.time 	= ret_json.op_time;
//					mis_para.updateflag = 0;
//					mis_para.jylsh 	= ret_json.wasteno;
//					mis_para.yhbh 	= rtnValue.userno;
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
				
				alert("补开户记录成功!");
				$("#buy_times").val(1);
				bjlFlag = true;
				setTextDisabled(true);
				$("#btnPrt").attr("disabled",false);
    			window.top.WebPrint.setYffDataOperIdx2params(data.gyOperAddCus,window.top.WebPrint.nodeIdx.gyycbd);//打印用的参数
			}
			else {
				
				alert("补开户记录失败!");
			}
		}
	);
}

function check(){
	
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

	if(!isDbl_Html("zje" ,  "总金额", 0/*-money_limit*/, money_limit, false)){//总金额应该在0~囤积值之间
		return false;
	}
	
	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
	}
	
	//rtnBD.remain_je : 终端内剩余金额  
	if (parseFloat($("#zje").html()) + parseFloat(rtu_BD.remain_je) > money_limit) {
		alert("最新余额超出囤积限值");
		return false;
	}

//	if($("#zje").html() === "" || $("#zje").html() === "0"){
//		alert("总金额错误,请输入追补金额/缴费金额/结算金额!");
//		$("#jfje").focus().select();
//		return false;
//	}

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

function printPayRec(){//打印
//	alert("in printPayRec()");
}

//判断原报警止码 ,断电止码 与 新的相同
function compare(){
	if(compareFlag==true){
		
        var colors = "<b style='color:#800000'>";
		var colore = "</b>";
		var str;
		
		if(parseFloat($("#alarm_code").html())==parseFloat($("#rtu_alarm_code").html())){
			str = "报警止码与终端一致.";
		}
		else if(parseFloat($("#alarm_code").html())!=parseFloat($("#rtu_alarm_code").html())){
			str = "报警止码与终端不一致! ";
		}
		
		str += "&nbsp";
		
		if(parseFloat($("#shutdown_bd").html())==parseFloat($("#rtu_shutdown_bd").html())){
			str += "断电止码与终端一致. ";
		}else if(parseFloat($("#shutdown_bd").html())!=parseFloat($("#rtu_shutdown_bd").html())){
			str += "断电止码与终端不一致! ";
		}
		
		str = colors + str + colore;
		$("#compare_detail").html(str);
		
	}
	else{
		return;
	}
}

function showPrint(){//打印发票
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.gyycbd);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintGy();
}

