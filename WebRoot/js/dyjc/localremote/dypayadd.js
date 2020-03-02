var rtnValue;
var rtnBD = null;
var openFlag = false;//是否开户
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("150,80,80,80,80,80,80,80,80,120,80,80,*")
	mygrid.setColAlign("center,center,left,right,right,right,right,right,right,right,left,left")
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.init();
	mygrid.setSkin("light");
	mygrid.setColumnHidden(7, true);
	
	$("#btnSearch").click(function(){selcons()});//检索
	$("#metinfo").click(function(){metinfo()});//表内信息
	$("#btnImportBD").click(function(){Import()});
	$("#rewrite").click(function(){if(check())dorepair();});//补写缴费记录
	$("#prt").click(function(){printPayRec()});//打印
	
	$("#zbje").keyup(function(){calcTotal();});
	$("#jsje").keyup(function(){calcTotal();});
	$("#jfje").keyup(function(){calcTotal();});
	
	setDisabled(true);
	$("#btnSearch").focus();
});

function selcons(){//检索
	var tmp = doSearch("yc",-1,rtnValue);
	                        
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	setDisabled(false);
	setConsValue(rtnValue);
	OpenOrPay(rtnValue);
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno};
	mis_query(param, gloQueryResult);
	
  	
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
	

	getYffRecs();
	
	$("#metinfo").attr("disabled",false);
	$("#rewrite").attr("disabled",false);	
}

//确定是补开户记录还是补缴费记录
function OpenOrPay(rtnValue){
	if(rtnValue.cus_state_id == "" || rtnValue.cus_state_id == SDDef.YFF_CUSSTATE_INIT || rtnValue.cus_state_id == SDDef.YFF_CUSSTATE_DESTORY) {
		openFlag = true;//走开户
	    $("#tr_open1").attr("style","display:block;");
		$("#tr_open2").attr("style","display:block;");
		$("#tr_open3").attr("style","display:block;");
		
	} else if(rtnValue.cus_state_id == SDDef.YFF_CUSSTATE_NORMAL){
		openFlag = false;//走缴费
        $("#tr_open1").attr("style","display:none;");
		$("#tr_open2").attr("style","display:none;");
		$("#tr_open3").attr("style","display:none;");
	} else {
		alert("当前用户状态下不能补写记录。");
		return;
	}
}

function Import(){
	var tmp = importBD($("#rtu_id").val(),$("#mp_id").val());
	if(!tmp)return;
	rtnBD = tmp;
    $("#td_bdinf").html(rtnBD.td_bdinf);
}

function calcTotal(){//总金额
	
	var zbje = $("#zbje").val()===""?0:$("#zbje").val();
	var jsje = $("#jsje").val()===""?0:$("#jsje").val();
	var jfje = $("#jfje").val()===""?0:$("#jfje").val();
	
	if(isNaN(jfje) || isNaN(jsje) || isNaN(zbje)){
		return;
	}
	
	var zje = round(parseFloat(zbje) + parseFloat(jsje) + parseFloat(jfje),3);
	$("#zongje").html(zje);
}

function dorepair(){
	
	if(openFlag){	//补开户记录
		newOne();
	}
	else{			//补缴费记录
		payMoney();
	}
}

function newOne() {
	
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert(sel_user_info);
		return;
	}
	var params = {};
	params.rtu_id        = rtu_id;
	params.mp_id         = mp_id;
	params.paytype       = $("#pay_type").val();
	params.feeproj_id    = $("#feeproj_id").val();
	params.myffalarmid   = $("#yffalarm_id").val();
	params.pay_money     = $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money   = $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money      = $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money     = round(parseFloat(params.pay_money)+ parseFloat(params.zb_money) + params.othjs_money, def.point);
	params.jt_total_zbdl = $("#jt_total_zbdl").val() == "" ? 0 :$("#jt_total_zbdl").val();
	
	params.date = rtnBD.date;
	params.time = 0;
	for(var i = 0; i < 3; i++){
		eval("params.mp_id"  + i + "=rtnBD.mp_id" + i);
		eval("params.bd_zy"  + i + "=rtnBD.zbd" + i);
		eval("params.bd_zyj" + i + "=rtnBD.jbd" + i);
		eval("params.bd_zyf" + i + "=rtnBD.fbd" + i);
		eval("params.bd_zyp" + i + "=rtnBD.pbd" + i);
		eval("params.bd_zyg" + i + "=rtnBD.gbd" + i);
	}
	
	modalDialog.height = 320;
	modalDialog.width  = 300;
	modalDialog.url    = def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param  = {
		showGSM : true,
		key	: ["客户编号","用户名称","所供电所", "缴费金额(元)","结算金额(元)","追补金额(元)","总金额(元)"],
		val	: [rtnValue.userno,rtnValue.username,rtnValue.orgname, params.pay_money,params.othjs_money,params.zb_money,params.all_money]		
	};
	
	var o = modalDialog.show();
	if(!o.flag){
		return;
	}
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			userData1      : rtu_id,
			mpid           : mp_id,
			gsmflag        : o.gsm,
			dyOperAddres   : json_str,
			userData2      : ComntUseropDef.YFF_DYOPER_ADDRES
		},
		function(data) {//回传函数
			loading.loaded();
			if (data.result == "success") {
				//mis缴费
				if(parseFloat(params.pay_money) > 0 && gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
					var ret_json = eval("(" + data.dyOperAddres + ")");
					var mis_para 	= {};
					
					//河北南网
					if (provinceMisFlag == "HB") {
						mis_para.rtu_id = params.rtu_id;
						mis_para.mp_id  = params.mp_id;
						mis_para.op_type= SDDef.YFF_DYOPER_ADDRES;
						mis_para.date 	= ret_json.op_date;
						mis_para.time 	= ret_json.op_time;
						mis_para.updateflag = 0;
						mis_para.jylsh 	= ret_json.wasteno;
						mis_para.yhbh 	= rtnValue.userno;
						mis_para.jfje 	= params.pay_money;
						mis_para.yhzwrq = ret_json.op_date;
						mis_para.jfbs 	= gloQueryResult.misJezbs;
						
						var all_pay = 0.0;
						for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
							eval("mis_para.yhbh" + i + "=gloQueryResult.misDetailsvect["+i+"].yhbh");
							eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");
						}
						mis_para.vlength = gloQueryResult.misDetailsvect.length;
					}
					//河南
					else if (provinceMisFlag == "HN") {
						mis_para.rtu_id = params.rtu_id;
						mis_para.mp_id = params.mp_id;
						mis_para.op_type= SDDef.YFF_DYOPER_ADDRES;
						mis_para.date 	= ret_json.op_date;
						mis_para.time 	= ret_json.op_time;
						mis_para.updateflag = 0;
						mis_para.jylsh 	= ret_json.wasteno;
						mis_para.yhbh 	= rtnValue.userno;
						mis_para.jfje 	= params.pay_money;
						mis_para.yhzwrq = ret_json.op_date;
						mis_para.jfbs 	= gloQueryResult.misJezbs;

						mis_para.batch_no	= gloQueryResult.misBatchNo;	
						mis_para.hsdwbh		= gloQueryResult.misHsdwbh;	
						
						var all_pay = 0.0;
						for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
							eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");

							eval("mis_para.dfny" + i + "=gloQueryResult.misDetailsvect["+i+"].dfny");
							eval("mis_para.dfje" + i + "=gloQueryResult.misDetailsvect["+i+"].dfje");
							eval("mis_para.wyjje" + i + "=gloQueryResult.misDetailsvect["+i+"].wyjje");
							var bcssje= Math.min(parseFloat(gloQueryResult.misDetailsvect[i].dfje) + parseFloat(gloQueryResult.misDetailsvect[i].wyjje), parseFloat(mis_para.jfje) - all_pay);
							all_pay += bcssje;
							eval("mis_para.bcssje" + i + "=" + bcssje);
						}
						mis_para.vlength = gloQueryResult.misDetailsvect.length;
					}
					//甘肃
					else if (provinceMisFlag == "GS") {
						mis_para.rtu_id = params.rtu_id;
						mis_para.mp_id = params.mp_id;
						mis_para.op_type= SDDef.YFF_DYOPER_ADDRES;
						mis_para.date 	= ret_json.op_date;
						mis_para.time 	= ret_json.op_time;
						mis_para.updateflag = 0;
						mis_para.jylsh 	= ret_json.wasteno;
						mis_para.yhbh 	= rtnValue.userno;
						mis_para.jfje 	= params.pay_money;
						mis_para.yhzwrq = ret_json.op_date;
						mis_para.jfbs 	= gloQueryResult.misJezbs;
						mis_para.dzpc   = gloQueryResult.misDzpc;
						
						var all_pay = 0.0;
						for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
							eval("mis_para.yhbh" + i + "=gloQueryResult.misDetailsvect["+i+"].yhbh");
							eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");

							eval("mis_para.jfje" + i + "=gloQueryResult.misDetailsvect["+i+"].yjje");
							eval("mis_para.dfje" + i + "=gloQueryResult.misDetailsvect["+i+"].dfje");
							eval("mis_para.wyjje" + i + "=gloQueryResult.misDetailsvect["+i+"].wyjje");
							
							eval("mis_para.sctw" + i + "=gloQueryResult.misDetailsvect["+i+"].sctw");
							eval("mis_para.bctw" + i + "=gloQueryResult.misDetailsvect["+i+"].bctw");
						}
						mis_para.vlength = gloQueryResult.misDetailsvect.length;
					}
					
					mis_pay(mis_para);
				}
				alert("开户成功!");
				setDisabled(true);
				getRecord(rtnValue.rtu_id, rtnValue.mp_id);
				$("#buy_times").html("1");
				window.top.WebPrint.setYffDataOperIdx2params(data.dyOperAddres,window.top.WebPrint.nodeIdx.dyremote);//打印用的参数
				$("#prt").attr("disabled",false);
			}
			else {
				alert("开户失败!");
			}
		}
	);
}

function payMoney() {
	
	var params = {};
	params.rtu_id 			= rtnValue.rtu_id;
	params.mp_id 			= rtnValue.mp_id;
	params.paytype 			= $("#pay_type").val();
	
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money   		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money), def.point);
	params.buynum 			= rtnValue.buy_times;
	
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
	params.gsmFlag 			= o.gsm;
	
	loading.loading();
	var json_str = jsonString.json2String(params);
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			userData1		: params.rtu_id,
			mpid 			: params.mp_id,
			gsmflag 		: params.gsmFlag,
			dyOperPay 		: json_str,
			userData2 		: ComntUseropDef.YFF_DYOPER_PAY
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				
				//向MIS系统缴费
				if(parseFloat(params.pay_money) > 0 && gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
					var ret_json = eval("(" + data.dyOperPay + ")");
					var mis_para 	= {};
					
					//河北南网
					if (provinceMisFlag == "HB") {
						mis_para.rtu_id = params.rtu_id;
						mis_para.mp_id  = params.mp_id;
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
						for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
							eval("mis_para.yhbh" + i + "=gloQueryResult.misDetailsvect["+i+"].yhbh");
							eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");
						}
						mis_para.vlength = gloQueryResult.misDetailsvect.length;
					}
					//河南
					else if (provinceMisFlag == "HN") {
						mis_para.rtu_id = params.rtu_id;
						mis_para.mp_id = params.mp_id;
						mis_para.op_type= SDDef.YFF_OPTYPE_PAY;
						mis_para.date 	= ret_json.op_date;
						mis_para.time 	= ret_json.op_time;
						mis_para.updateflag = 0;
						mis_para.jylsh 	= ret_json.wasteno;
						mis_para.yhbh 	= rtnValue.userno;
						mis_para.jfje 	= params.pay_money;
						mis_para.yhzwrq = ret_json.op_date;
						mis_para.jfbs 	= gloQueryResult.misJezbs;

						mis_para.batch_no	= gloQueryResult.misBatchNo;	
						mis_para.hsdwbh		= gloQueryResult.misHsdwbh;	
						
						var all_pay = 0.0;
						for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
							eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");

							eval("mis_para.dfny" + i + "=gloQueryResult.misDetailsvect["+i+"].dfny");
							eval("mis_para.dfje" + i + "=gloQueryResult.misDetailsvect["+i+"].dfje");
							eval("mis_para.wyjje" + i + "=gloQueryResult.misDetailsvect["+i+"].wyjje");
							var bcssje= Math.min(parseFloat(gloQueryResult.misDetailsvect[i].dfje) + parseFloat(gloQueryResult.misDetailsvect[i].wyjje), parseFloat(mis_para.jfje) - all_pay);
							all_pay += bcssje;
							eval("mis_para.bcssje" + i + "=" + bcssje);
						}
						mis_para.vlength = gloQueryResult.misDetailsvect.length;
					}
					//甘肃
					else if (provinceMisFlag == "GS") {
						mis_para.rtu_id = params.rtu_id;
						mis_para.mp_id = params.mp_id;
						mis_para.op_type= SDDef.YFF_OPTYPE_PAY;
						mis_para.date 	= ret_json.op_date;
						mis_para.time 	= ret_json.op_time;
						mis_para.updateflag = 0;
						mis_para.jylsh 	= ret_json.wasteno;
						mis_para.yhbh 	= rtnValue.userno;
						mis_para.jfje 	= params.pay_money;
						mis_para.yhzwrq = ret_json.op_date;
						mis_para.jfbs 	= gloQueryResult.misJezbs;
						mis_para.dzpc   = gloQueryResult.misDzpc;
						
						var all_pay = 0.0;
						for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
							eval("mis_para.yhbh" + i + "=gloQueryResult.misDetailsvect["+i+"].yhbh");
							eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");

							eval("mis_para.jfje" + i + "=gloQueryResult.misDetailsvect["+i+"].yjje");
							eval("mis_para.dfje" + i + "=gloQueryResult.misDetailsvect["+i+"].dfje");
							eval("mis_para.wyjje" + i + "=gloQueryResult.misDetailsvect["+i+"].wyjje");
							
							eval("mis_para.sctw" + i + "=gloQueryResult.misDetailsvect["+i+"].sctw");
							eval("mis_para.bctw" + i + "=gloQueryResult.misDetailsvect["+i+"].bctw");
						}
						mis_para.vlength = gloQueryResult.misDetailsvect.length;
					}
					
					mis_pay(mis_para);
				}
				
				$("#prt").attr("disabled",false);
				alert("缴费成功!");
				setDisabled(true);
				getRecord(rtnValue.rtu_id, rtnValue.mp_id);
				$("#buy_times").html(parseInt(rtnValue.buy_times) + 1);
				$("#prt").attr("disabled",false);
				window.top.WebPrint.setYffDataOperIdx2params(data.dyOperPay,window.top.WebPrint.nodeIdx.dyremote);//打印用的参数
				$("#prt").attr("disabled",false);
			}
			else {
				alert("向主站发送缴费命令失败,请补写缴费记录!");
			}
		}
	);
}

function check(){
	//如果MIS不通，禁止缴费
	if (gloQueryResult.misUseflag == "true" && (gloQueryResult.misOkflag == "false" || gloQueryResult.misOkflag == undefined)) {
		alert("MIS不能通讯,禁止开户...");
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
	
	if(!isDbl("zbje" ,"追补金额",-rtnValue.moneyLimit, rtnValue.moneyLimit)){
		return false;
	}
	
	if(!isDbl("jsje" ,"结算金额",-rtnValue.moneyLimit, rtnValue.moneyLimit)){
		return false;
	}
	
	if(!isDbl("jfje" ,"缴费金额", 0, rtnValue.moneyLimit)){
		return false;
	}
	
	if(!isDbl_Html("zje",  "总金额", 0, rtnValue.moneyLimit, false)){//总金额应该在0~囤积值之间
		return false;
	}

	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
	}
	
	if(!isDbl("jt_total_zbdl" ,"追补电量",0, 99999999999)){
		return false;
	}
	
	if(($("#jfje").val() == "0" || $("#jfje").val() == "")){
		return(confirm("客户未缴费,继续操作吗?"))
	}
	
	if($("#zongje").html() > rtnValue.moneyLimit || $("#zongje").html() < 0){
		alert("总金额应在0与" + rtnValue.moneyLimit + "之间");
		return false;
	}
	
	if(openFlag && rtnBD == null) {
		alert("请输入表底");
		return false;
	}
	return true;
}

function metinfo(){//表内信息
	modalDialog.width = 800;
	modalDialog.height = 600;
	modalDialog.url = def.basePath + "jsp/dyjc/dialog/meterInfo.jsp";
	modalDialog.param = {"rtuId":$("#rtu_id").val() ,"mpId":$("#mp_id").val()};
	modalDialog.show();
}

function setDisabled(mode){
	if(!mode){
		$("#jsje").val("");
		$("#jfje").val("");
		$("#zbje").val("");
		$("#buy_times").html("");
		$("#jt_total_zbdl").val("");
		$("#zongje").html("");
		rtnBD = null;
	}
	$("#jt_total_zbdl").attr("disabled",mode);
	$("#jfje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#zbje").attr("disabled",mode);
}

function printPayRec(){//打印
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.dyremote);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
//	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.dyje;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}