var rtnValue;
var rtnBD;
$(document).ready(function(){	
	initGrid();
	
	$("#btnImportBD").click(function(){Import()});
	$("#btnSearch").click(function(){selcons()});
	$("#restart").click(function(){if(check()){restart()}});
	$("#btnPrint").click(function(){printHf()});
	
	$("#zbje").keyup(function(){calcTotal();});
	$("#jsje").keyup(function(){calcTotal();});
	$("#jfje").keyup(function(){calcTotal();});
	
	setDisabled(true);
	$("#btnSearch").focus();
});

function selcons(){//检索
	var rtnValue1 = doSearch("zz",ComntUseropDef.YFF_DYOPER_RESTART,rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
	setDisabled(false);
	if(rtnValue.feeType != SDDef.YFF_FEETYPE_JTFL && rtnValue.feeType != SDDef.YFF_FEETYPE_MIXJT){//阶梯费率 则 旧表基础电量可用
	   $("#jt_total_zbdl").attr("disabled",true).css("background","#ccc");
	}else{
	   $("#jt_total_zbdl").attr("disabled",false).css("background","#fff");
	}
	setConsValue(rtnValue);
	getRecord($("#rtu_id").val(), $("#mp_id").val());
	$("#btnPrint").attr("disabled",true);
	$("#restart").attr("disabled",true);
}

function restart(){
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	
	var params = {};
	params.rtu_id = rtu_id;
	params.mp_id = mp_id;
	
	params.myffalarmid = $("#yffalarm_id").val();
	params.paytype     = $("#pay_type").val();
	params.buynum      = rtnValue.buy_times;
	
	if($("#jt_total_zbdl").attr("disabled")) {
		params.jt_total_zbdl = 0;
	} else {
		params.jt_total_zbdl = $("#jt_total_zbdl").val() == "" ? 0 :$("#jt_total_zbdl").val();
	}
	
	params.pay_money   = $("#jfje").val() === "" ? 0 : $("#jfje").val();
	params.othjs_money = $("#jsje").val() === "" ? 0 : $("#jsje").val();
	params.zb_money    = $("#zbje").val() === "" ? 0 : $("#zbje").val();	
	params.all_money   = round(parseFloat(params.zb_money) + parseFloat(params.pay_money) + parseFloat(params.othjs_money),3);
	
	modalDialog.height = 320;
	modalDialog.width = 300;
	modalDialog.url = def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param = {
		showGSM : true,
		key	: ["客户编号","用户名称","所属供电所", "缴费金额","追补金额","结算金额","总金额"],
		val	: [rtnValue.userno,rtnValue.username,rtnValue.orgname, params.pay_money,params.zb_money,params.othjs_money,params.all_money]		
	};
	
	var o = modalDialog.show();
	if(!o.flag){
		return;
	}
	
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
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag	: true,
			userData1		: rtu_id,
			mpid			: mp_id,
			gsmflag			: o.gsm,
			dyOperReStart	: json_str,
			userData2		: ComntUseropDef.YFF_DYOPER_RESTART
		},
		function(data) {			    	//回传函数
			loading.loaded();	
			if (data.result == "success") {
				$("#restart").attr("disabled",true);
				getRecord($("#rtu_id").val(), $("#mp_id").val());
				alert("恢复成功!");
				setDisabled(true);
				
				$("#btnPrint").attr("disabled",false);
				window.top.WebPrint.setYffDataOperIdx2params(data.dyOperReStart,window.top.WebPrint.nodeIdx.dymain);//打印用的参数
			}else {
				alert("恢复失败!" + (data.operErr ? data.operErr : ''));
			}
		}
	);
}

function check() {
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
	
	if(!isDbl("jfje" ,"缴费金额",0,rtnValue.moneyLimit)){
		return false;
	}

	if ((window.top.dynegativemoney.dymain.totflag == 0) &&
		($("#zje").html() < "0")) {
		alert("总金额不能为负数");
		return false;
	}
		
	if(!isDbl_Html("zje" ,  "总金额", -rtnValue.moneyLimit, rtnValue.moneyLimit, false)){//缴费金额应该在0~囤积值之间
		return false;
	}

	if ((window.top.dynegativemoney.dymain.remainflag == 0) &&
		(parseFloat($("#zje").html()) + parseFloat($("#dqye").html()) < 0)) {
		alert("最新余额不能为负数");
		return false;
	}

	if (parseFloat($("#zongje").html()) + parseFloat($("#dqye").html()) > rtnValue.moneyLimit) {
		alert("最新余额超出囤积限值");
		return false;
	}
	
	if(($("#jfje").val() == "0" || $("#jfje").val() == "")){
		return(confirm("客户未缴费,继续操作吗?"))
	}
	
	if(!isDbl("jt_total_zbdl", "旧表基础电量")){
		return false;
	}
	
	if(($("#jfje").val() == "0" || $("#jfje").val() == "")){
		return(confirm("客户未缴费,继续操作吗?"))
	}
	
	return true;
}

function Import(){//录入
	var tmp = importBD($("#rtu_id").val(),$("#mp_id").val());
	if(!tmp)return;
	rtnBD = tmp;
	$("#td_bdinf").html(rtnBD.td_bdinf);
	$("#restart").attr("disabled" , false);		
}

function calcTotal(){//计算总金额
	var zbje = $("#zbje").val()===""?0:$("#zbje").val();
	var jsje = $("#jsje").val()===""?0:$("#jsje").val();
	var jfje = $("#jfje").val()===""?0:$("#jfje").val();
	
	if(isNaN(jfje) || isNaN(jsje) || isNaN(zbje)){
		return;
	}
	
	var zje = round(parseFloat(zbje) + parseFloat(jsje) + parseFloat(jfje),3);
	$("#zongje").html(zje);
}

function setDisabled(mode){
	if(!mode){
		$("#jsje").val("");
		$("#jfje").val("");
		$("#zbje").val("");
		$("#zongje").html("");
		$("#jt_total_zbdl").val("");
		rtnBD = null;
	}
	$("#btnImportBD").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#jfje").attr("disabled",mode);
	$("#zbje").attr("disabled",mode);
	$("#jt_total_zbdl").attr("disabled",mode);
	$("#td_bdinf").html("");
}

function printHf() {
//	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.dyje;
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.dymain);
	if(filename == undefined || filename == null){
		return;
	}
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}