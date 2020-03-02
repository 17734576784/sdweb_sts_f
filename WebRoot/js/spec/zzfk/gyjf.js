//缴费
var rtnValue;
var gloQueryResult = { };
$(document).ready(function(){
	
	$("#btnSearch").click(function(){selcons()});
	$("#btnPrt").click(function(){showPrint()});
	
	$("#btnPay").click(function(){
		if(check()){
		    payMoney();
		}
	});
	$("#zbje").keyup(function(){calcu()});
	$("#jsje").keyup(function(){calcu()});
	$("#jfje").keyup(function(){calcu()});
	
	initGrid();
	
	IsDisabled()
});

function calcu() {
	var zbje = 0.0, jfje = 0.0, jsje = 0.0;
	
	zbje = $("#zbje").val() === "" ? 0 : $("#zbje").val();
	jfje = $("#jfje").val() === "" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val() === "" ? 0 : $("#jsje").val();
	
	var zje = round(parseFloat(zbje) + parseFloat(jfje) + parseFloat(jsje),3);
	
	if(!isNaN(zje)){
		setAlarmValue(rtnValue.yffalarm_alarm1, rtnValue.yffalarm_alarm2, zje)
		$("#zje").val(zje);
	}
}

function selcons(){//检索
	tmp = doSearch("zz",ComntUseropDef.YFF_GYCOMM_PAY, rtnValue);
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, zjg_id : rtnValue.zjg_id, busi_no : rtnValue.userno};
	mis_query(param,gloQueryResult);
	
	setConsValue(rtnValue);
	//购电次数 单独赋值
	$("#buy_times").html(rtnValue.buy_times);
	$("#dqye").html(round(rtnValue.now_remain, def.point));
	getGyYffRecs();
	
	getAlarmValue(rtnValue.yffalarm_id);
	getMoneyLimit(rtnValue.feeproj_id);
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
	UnDisabled();
}

function initGrid(){
	var yff_grid_title ='操作日期,操作类型,缴费金额(元),追补金额(元),结算金额(元),总金额(元),断电值,报警值1,报警值2,购电次数,流水号,缴费方式,操作员,&nbsp;';
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("150,80,80,80,80,80,80,80,80,80,150,80,80,*");
	mygrid.setColAlign("center,center,right,right,right,right,right,right,right,right,left,left,left");
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.enableTooltips("false,false,false,false,false,false,false,false,false,false,false,false,false");
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
	//如果MIS不通，禁止缴费
	if (gloQueryResult.misUseflag == "true" && (gloQueryResult.misOkflag == "false" || gloQueryResult.misOkflag == undefined)) {
		alert("MIS不能通讯,禁止缴费...");
		return false;
	}
	
	if($("#btnPay").attr("disabled"))return false;
	
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

	if ((window.top.gynegativemoney.gymain.remainflag == 0) &&
		(parseFloat($("#zje").html()) + parseFloat($("#dqye").html()) < 0)) {
		alert("最新余额不能为负数");
		return false;
	}

	if (parseFloat($("#zje").html()) + parseFloat($("#dqye").html()) > money_limit) {
		alert("最新余额超出囤积限值");
		return false;
	}
	
	var zje = $("#zje").val();
	var jfje = $("#jfje").val();
	
	if(jfje == 0 || jfje == ""){	//总金额>0但是 缴费金额为0或为""(空)
		return(confirm("用户未交费,是否继续"));
	}
	
	return true;
}

function payMoney(){
	
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
	
	params.all_money = round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),3);
	
	params.date = 0;
	params.time = 0;
	
	params.buy_dl = 0;
	params.pay_bmc = 0;
	params.shutdown_bd = 0;
	
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
	params.gsmFlag = o.gsm;//params 最后一个参数是否发送短信  赋值  进行action请求
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
		{
			userData1		: params.rtu_id,
			zjgid 			: params.zjg_id,
			gsmflag 		: params.gsmFlag,
			gyOperPay 		: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_PAY
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {		
				
				getGyYffRecs();
				$("#buy_times").val(parseInt($("#buy_times").val())+1);
				IsDisabled();
				
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
//					mis_para.yhbh 	= rtnValue.userno;
//					mis_para.jfje 	= params.pay_money;
//					mis_para.yhzwrq = ret_json.op_date;
//					mis_para.jfbs 	= misJezbs;
//					
//					for(var i = 0; i < misDetailsvect.length; i++) {
//						eval("mis_para.yhbh" + i + "=misDetailsvect["+i+"].yhbh");
//						eval("mis_para.ysbs" + i + "=misDetailsvect["+i+"].ysbs");
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
				alert("缴费成功!");
				if(data.gyOperPay == ""){
					alert("获取返回的操作信息失败。请到[补打发票]界面补打。")
					$("#btnPrt").attr("disabled",true);
				}else{
					//alert(data.gyOperPay)
					$("#btnPrt").attr("disabled",false);
					window.top.WebPrint.setYffDataOperIdx2params(data.gyOperPay);//打印用的参数

				}
				
								
			}
			else {
				alert("向主站发送命令失败");
			}
		}
	);
}

//刚刚进入界面  各个input默认disabled
function IsDisabled(){
	$("#zbje"           ).attr("disabled",true);
	$("#jfje"           ).attr("disabled",true);
	$("#jsje"           ).attr("disabled",true);
	$("#yffalarm_alarm1").attr("disabled",true);
	$("#yffalarm_alarm2").attr("disabled",true);
	
	$("#btnPay").attr("disabled",true);
}

//selcons 各个input属性disabled更改为 false
function UnDisabled(){
	//清空追补金额,结算金额,缴费金额,总金额的值 方便继续开户
	$("#zbje"    ).attr("value","");
	$("#jsje"    ).attr("value","");
	$("#jfje"    ).attr("value","");
	$("#zje"     ).attr("value","");
	$("#td_bdinf").html("");
	$("#jsyhm").html("");
	$("#yhfl").html("");
	
	$("#zbje"           ).attr("disabled",false);
	$("#jfje"           ).attr("disabled",false);
	$("#jsje"           ).attr("disabled",false);
	$("#yffalarm_alarm1").attr("disabled",false);
	$("#yffalarm_alarm2").attr("disabled",false);
	
	$("#btnPay").attr("disabled",false);
	$("#btnPrt").attr("disabled",true);
}

function showPrint(){//打印发票
	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.gyje;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintGy();
	
}
