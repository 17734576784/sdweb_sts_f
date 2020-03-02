var rtnValue;
//var rtnBD = null;
var openFlag = false;//是否开户

$(document).ready(function(){
	
	initGrid();
	
	$("#btnSearch").click(function(){selcons()});//检索
	$("#btnRead").click(function(){read_card()});//读卡
	//$("#btnImportBD").click(function(){Import()});
	$("#cardinfo").click(function(){window.top.card_comm.cardinfo()});//卡内信息
	//$("#metinfo").click(function(){metinfo()});//表内信息
	
	$("#rewrite").click(function(){if(check())dorepair();});//补写缴费记录
	$("#prt").click(function(){printPayRec()});//打印
	
	$("#zb_money").keyup(function(){calcTotal();});
	$("#othjs_money").keyup(function(){calcTotal();});
	$("#pay_money").keyup(function(){calcTotal();});
	
	$("#btnSearch").focus();
	setDisabled(true);
});

function selcons(){//检索
	setDisabled(false);
	var rtnValue1 = doSearch("ks", ComntUseropDef.YFF_NPOPER_REWRITE , rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
	setcons1();
}

function read_card(){	//读卡
	setDisabled(false);
	$("#prt").attr("disabled",true);//
	loading.tip = "正在读卡,请稍后...";
	loading.loading();
	setTimeout("window.top.card_comm.readCardSearchNp(" + ComntUseropDef.YFF_NPOPER_REWRITE + ")", 10);
}

function setSearchJson2Html(js_tmp) {
	loading.loaded();
	if(!js_tmp)return;
//	if(!window.top.card_comm.checkInfo(js_tmp)) return;
	
	rtnValue = js_tmp;
	setcons1();
}

function setcons1() {
	setConsValue(rtnValue);
	$("#buy_times").html(rtnValue.buy_times);
	getRecord(rtnValue.areaid,rtnValue.farmerid);
	
	if(rtnValue.cus_state_id == "" || rtnValue.cus_state_id == 0 || rtnValue.cus_state_id == 50) {
		openFlag = true;
		//$("#btnImportBD").attr("disabled", false);
	} else if(rtnValue.cus_state_id == 1){
		openFlag = false;
		//$("#btnImportBD").attr("disabled",true);
	} else {
		alert("当前用户状态下不能补写记录。");
		return;
	}
	
//	//阶梯费率 开户 时
//	if(rtnValue.feeType == 3 && openFlag){
//	   $("#jt_total_zbdl").attr("disabled",false).css("background","#fff");
//	}
//	//其他
//	else{
//	   $("#jt_total_zbdl").attr("disabled",true).css("background","#ccc");
//	}
	
	$("#rewrite").attr("disabled", false);
	
	//mis.js中函数
//	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno};
//	mis_query(param);
}

//function Import(){
//	var tmp = importBD($("#rtu_id").val(),$("#mp_id").val());
//	if(!tmp)return;
//	rtnBD = tmp;
//    $("#td_bdinf").html(rtnBD.td_bdinf);
//}

function calcTotal(){//总金额
	
	var zbje = $("#zb_money").val()		===	""?0:$("#zb_money").val();
	var jsje = $("#othjs_money").val()	===	""?0:$("#othjs_money").val();
	var jfje = $("#pay_money").val()	===	""?0:$("#pay_money").val();
	
	if(isNaN(jfje) || isNaN(jsje) || isNaN(zbje)){
		return;
	}
	
	var zje = round(parseFloat(zbje) + parseFloat(jsje) + parseFloat(jfje),3);
	$("#all_money").html(zje);
}

function dorepair(){
	//通讯貌似尚不完善	
	if(openFlag){	//补开户记录
		newOne();
	}
	else{			//补缴费记录
		payMoney();
	}
}

function newOne() {
	var area_id = $("#area_id").val(), farmer_id = $("#farmer_id").val();
	if(area_id === "" || farmer_id === ""){
		alert(sel_user_info);
		return;
	}
	var params = {};
	params.area_id       = area_id;
	params.farmer_id     = farmer_id;
	params.paytype       = $("#pay_type").val();
	params.pay_money     = $("#pay_money").val()===""?0:$("#pay_money").val();
	params.othjs_money   = $("#othjs_money").val()===""?0:$("#othjs_money").val();
	params.zb_money      = $("#zb_money").val()===""?0:$("#zb_money").val();
	params.all_money     = Math.round((parseFloat(params.pay_money)+ parseFloat(params.zb_money) + parseFloat(params.othjs_money)),def.point);
	//params.jt_total_zbdl = $("#jt_total_zbdl").val() == "" ? 0 :$("#jt_total_zbdl").val();
	
	//params.date = rtnBD.date;
	params.time = 0;
//	for(var i = 0; i < 3; i++){
//		eval("params.mp_id"  + i + "=rtnBD.mp_id" + i);
//		eval("params.bd_zy"  + i + "=rtnBD.zbd" + i);
//		eval("params.bd_zyj" + i + "=rtnBD.jbd" + i);
//		eval("params.bd_zyf" + i + "=rtnBD.fbd" + i);
//		eval("params.bd_zyp" + i + "=rtnBD.pbd" + i);
//		eval("params.bd_zyg" + i + "=rtnBD.gbd" + i);
//	}
	
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
	$.post( def.basePath + "ajaxoper/actOperNp!taskProc.action", 		//后台处理程序
		{
			userData1      : area_id,
			farmerid           : farmer_id,
			gsmflag        : o.gsm,
			npOperAddres   : json_str,
			userData2      : ComntUseropDef.YFF_NPOPER_ADDRES
		},
		function(data) {//回传函数
			loading.loaded();
			if (data.result == "success") {
				
				alert("开户成功!");
				setDisabled(true);
				getRecord(rtnValue.areaid,rtnValue.farmerid);
				
				window.top.WebPrint.setYffDataOperIdx2params(data.npOperAddres,window.top.WebPrint.nodeIdx.npcard);//打印用的参数
				$("#prt").attr("disabled",false);
			}
			else {
				alert("开户失败!" + (data.operErr ? data.operErr : ''));
			}
		}
	);
}

function payMoney() {
	
	var params = {};
	params.area_id 			= $("#area_id").val();
	params.farmer_id 		= $("#farmer_id").val();
	params.paytype 			= $("#pay_type").val();
	
	params.pay_money 		= $("#pay_money").val()	  ===	""?0:$("#pay_money").val();
	params.othjs_money 		= $("#othjs_money").val() ===	""?0:$("#othjs_money").val();
	params.zb_money 		= $("#zb_money").val()	  ===	""?0:$("#zb_money").val();
	params.all_money   		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money), def.point);
	params.buynum 			= rtnValue.buy_times;
	params.op_result		= 100;
	params.lsremain			= rtnValue.now_remain;		//还需要处理
	
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

	$.post( def.basePath + "ajaxoper/actOperNp!taskProc.action", 		//后台处理程序
		{
			userData1		: params.area_id,
			farmerid 		: params.farmer_id,
			gsmflag 		: params.gsmFlag,
			npOperPay 		: json_str,
			userData2 		: ComntUseropDef.YFF_NPOPER_PAY
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				$("#prt").attr("disabled",false);
				alert("缴费成功!");
				getRecord(rtnValue.areaid,rtnValue.farmerid);
				setDisabled(true);
				$("#rewrite").attr("disabled",true);
				window.top.WebPrint.setYffDataOperIdx2params(data.npOperPay,window.top.WebPrint.nodeIdx.npcard);//打印用的参数
			}
			else {
				alert("向主站发送缴费命令失败,请补写缴费记录!" + (data.operErr ? data.operErr : ''));
			}
			
		}
	);
}

//显示用户用电信息
function showYDInfo(){
	_showYDInfo(rtnValue.areaid, rtnValue.farmerid);
}

function check(){
	//如果MIS不通，禁止缴费
//	if (misUseflag == "true" && (misOkflag == "false" || misOkflag == undefined)) {
//		alert("MIS不能通讯,禁止开户...");
//		return false;
//	}
	
	var jfje = 0, zbje = 0, jsje = 0;
	zbje = $("#zb_money").val()==="" ? 0 : $("#zb_money").val();
	jfje = $("#pay_money").val()==="" ? 0 : $("#pay_money").val();
	jsje = $("#othjs_money").val()==="" ? 0 : $("#othjs_money").val();
	
	if(isNaN(zbje)){
		alert("请输入数字!");
		$("#zb_money").focus().select();
		return false;
	}
	
	if(isNaN(jfje)){
		alert("请输入数字!");
		$("#pay_money").focus().select();
		return false;
	}

	if(isNaN(jsje)){
		alert("请输入数字!");
		$("#othjs_money").focus().select();
		return false;
	}

	if(!isDbl("zb_money" ,"追补金额",-rtnValue.moneyLimit, rtnValue.moneyLimit)){
		return false;
	}
	
	if(!isDbl("othjs_money" ,"结算金额",-rtnValue.moneyLimit, rtnValue.moneyLimit)){
		return false;
	}
	
	if(!isDbl("pay_money" ,"缴费金额",0, rtnValue.moneyLimit)){
		return false;
	}
	
	if(!isDbl_Html("all_money" ,  "总金额"  , 0, rtnValue.moneyLimit, false)){//总金额应该在0~囤积值之间
		return false;
	}

	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
	}

	if(!isDbl("jt_total_zbdl" ,"追补电量",0, 999999)){
		return false;
	}
	
//	if(($("#jfje").val() == "0" || $("#jfje").val() == "")){
//		return(confirm("客户未缴费,继续操作吗?"))
//	}
	
//	if($("#zongje").html() > rtnValue.moneyLimit || $("#zongje").html() < 0){
//		alert("总金额应在0与" + rtnValue.moneyLimit + "之间");
//		return false;
//	}
	
	if(openFlag && rtnBD == null) {
		alert("请输入表底");
		return false;
	}
	
	return true;
}

/*function metinfo(){//表内信息
	modalDialog.width = 800;
	modalDialog.height = 600;
	modalDialog.url = def.basePath + "jsp/dyjc/dialog/meterInfo.jsp";
	modalDialog.param = {"rtuId":$("#rtu_id").val() ,"mpId":$("#mp_id").val()};
	modalDialog.show();
}*/

function setDisabled(mode){
	if(!mode){
		//清空界面的信息,用于开户后再次查询,而无查询结果的情况
		setConsValue(rtnValue,true);
		$("#othjs_money").val("");
		$("#pay_money").val("");
		$("#zb_money").val("");
		$("#buy_times").html("");
		$("#jt_total_zbdl").val("");
		$("#all_money").html("");
	    
		//计算客户名称  结算客户分类
		$("#jsyhm").html("");
		$("#yhfl").html("");
		rtnBD = null;
	}
	$("#jt_total_zbdl").attr("disabled",mode);
	$("#pay_money").attr("disabled",mode);
	$("#othjs_money").attr("disabled",mode);
	$("#zb_money").attr("disabled",mode);
}

/*function check_JT_total_zbdl(){//阶梯费率 则 旧表基础电量可用			
	if(rtnValue.feeType == SDDef.YFF_FEETYPE_JTFL || rtnValue.feeType == SDDef.YFF_FEETYPE_MIXJT){
	   $("#jt_total_zbdl").attr("disabled",false).css("background","#fff");
	}else{
	   $("#jt_total_zbdl").attr("disabled",true).css("background","#ccc");
	}
}*/

function printPayRec(){//打印
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.npcard);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}