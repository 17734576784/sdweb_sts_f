var rtnValue 	    = null;
var WRITECARD_TYPE  = window.top.SDDef.YFF_CARDMTYPE_KE005;   //卡表类型
var lastpayRcd 	    = null;//上次购电记录

var history_Records = null;
var mygrid          = null;
var old_op_date     = null;
var old_op_time     = null;

$(document).ready(function(){
	initGrid();
	$("#btnSearch").click(function(){selcons()});
	$("#cardinfo ").click(function(){cardinfo()});
	$("#btnRead").click(function(){read_card()});
	//$("#metinfo  ").click(function(){metinfo()});
	$("#repair").click(function(){
	   if(check())doRepair();
	});
	$("#btnPrt").click(function(){printPayRec()});
	//$("#repair_type").change(function()	{ change_repairtype(this.value)});
	
	$("#pay_money"	).keyup(function(){ calcu()});
	$("#zb_money"	).keyup(function(){ calcu()});
	$("#othjs_money").keyup(function(){ calcu()});
	
	$("#btnSearch").focus();
	setDisabled(true);
	$("#repair").attr("disabled",true);
	//$("#repair_type").attr("disabled",true);
});


//上次缴费信息   not delete
function GetlastpayRcd_repair(area_id, farmer_id){
	$.post(def.basePath + "ajax/actCommon!lastPayInfoNP.action", 
	{result : "{area_id : " + area_id + ", farmer_id : " + farmer_id + "}"},
	function(data){
		if(data.result == ""){
		    alert("找不到上次购电记录，不能补卡！");
		    return;
		}else{
			var json = eval('(' + data.result + ')');
			lastpayRcd = json;
			$("#lastpay_money").html(lastpayRcd.pay_money);//上次缴费记录
		}
	});
}

function read_card(){
	var json_out = {};
	json_out = window.top.card_comm.readcard();
	if(json_out == undefined || json_out ==""){
		alert("读卡错误!");
	}
	//判断卡号是否被别人占用
	var meterType = json_out.meter_type;//卡表类型
	if(meterType != window.top.SDDef.YFF_METER_TYPE_NP){
		alert("未知卡类型");
	}
	
	//此处还缺少购电卡状态
	var card_no = json_out.cardno;    //卡号
	var area_id = $("#area_id").val();
	var farmer_id = $("#farmer_id").val();
	var cardMsg = area_id + "," + farmer_id + "," + card_no;
	
	$("#card_no").html(card_no);
	$("#card_state_desc").html(json_out.card_state_desc);
	
	$.post(def.basePath + "ajaxnp/actConsPara!uniqueCardCode.action",{result : cardMsg},function(data){
		if(data.result == "fail"){
			loading.loaded();
			alert("此卡号已被别的用户使用,不能补卡!");
			$("#repair").attr("disabled",true);
		}else{
			$("#repair").attr("disabled",false);
			$("#pay_money").attr("disabled",false);
			$("#zb_money").attr("disabled",false);
			$("#othjs_money").attr("disabled",false);
		}
	});
}

function selcons(){//检索   
	setcons1('',true);
	setDisabled(false);
	mygrid.clearAll();
	var js_tmp = doSearch("ks",ComntUseropDef.YFF_NPOPER_REPAIR,rtnValue);
	if(!js_tmp)	return;
	rtnValue = js_tmp;	
	//setConsValue(rtnValue);
	setcons1(rtnValue);
	
	getRecord(rtnValue.areaid,rtnValue.farmerid);
	GetlastpayRcd_repair(rtnValue.areaid,rtnValue.farmerid);//从历史库得到上次缴费记录,给界面赋值
	$("#ydInfo").css("visibility","visible");
	
	
	//mis.js中函数
//	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno};
//	mis_query(param);
//	$("#repair").attr("disabled",false);
	
	//使用两种补卡方式的标志位    0只是用一种   1使用两种
	//$("#repair_type").attr("disabled",false);
	//dycard_repairpay_flag == "1" ? $("#repair_type").attr("disabled",false):"";
	$("#btnPrt").attr("disabled",true);
	$("#btnRead").attr("disabled",false);
}

//计算缴费信息
function calcu(){
	var jfje = 0.0, jsje = 0, zbje = 0;
	jfje = $("#pay_money").val() === "" ? 0 : $("#pay_money").val();
	zbje = $("#zb_money").val() === "" ? 0 : $("#zb_money").val();
	jsje = $("#othjs_money").val() === "" ? 0 : $("#othjs_money").val();
	var zje = round((parseFloat(jfje) + parseFloat(jsje) + parseFloat(zbje)), 3);
	if(!isNaN(zje)){
		$("#all_money").html(zje);
	}
}

//如果非要修改，  理解  慎重 
/**function change_repairtype(chgtype)
{	
	if (rtnValue) {
		if (chgtype == 0) {			//已经插入电表
			$("#pay_money").attr("disabled",false);
			$("#zb_money").attr("disabled",false);
			$("#othjs_money").attr("disabled",false)
			$("#pay_money").val("");
			$("#othjs_money").val("");
			$("#zb_money").val("");
			$("#all_money").html("");
			$("#buy_times").html(rtnValue.buy_times);
			$("#lastpay_money").html(rtnValue.money);
		}
		else if (chgtype == 1) {	//未插入电表  (默认的)
			mygrid.selectRow(0);
			$("#pay_money").attr("disabled",true);
			$("#zb_money").attr("disabled",true);
			$("#othjs_money").attr("disabled",true);
			$("#othjs_money").val(rtnValue.zb_money);
			$("#zb_money").val(rtnValue.othjs_money);
			$("#all_money" ).html(rtnValue.all_money);
			$("#pay_money").val(rtnValue.pay_money);
		}
	}
}*/

//显示用户用电信息
function showYDInfo(){
	_showYDInfo(rtnValue.areaid, rtnValue.farmerid);
}

function check(){
	//如果MIS不通，禁止缴费
	if (misUseflag == "true" && (misOkflag == "false" || misOkflag == undefined)) {
		alert("MIS不能通讯,禁止开户...");
		return false;
	}
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
	if(!isDbl("pay_money" ,"缴费金额",0,rtnValue.moneyLimit)){
		return false;
	}
	if(!isDbl_Html("all_money","总金额",0,rtnValue.moneyLimit)){
		return false;
	}
	
//	//对于未插插卡,db操作为补卡时,进行条件判断
//	if($("#repair_type").val()==1 && dycard_repairpay_flag == 1){
//		var id   = mygrid.getSelectedId();
//	    var type = mygrid.cells(id,1).getValue();
//	    if(!(type == "开户" || type == "恢复" || type == "缴费"|| type == "换表"|| type == "结算")){
//	    	alert("不能对 "+ type + "进行补卡操作");
//	        return false;
//	    }
//	}
	
	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
	}

//	if(($("#jfje").val() == "0" || $("#jfje").val() == "")){
//		return(confirm("客户未缴费,继续操作吗?"))
//	}
	
	return true;
}

function CheckCard()
{
	var json_out = {};
	json_out = window.top.card_comm.readcard();
	if(json_out == undefined || json_out ==""){
		return  false;				
	}

	var areano = "", pay_num = 0, pay_money = 0;				//区域号，购电次数
	var meterType = json_out.meter_type;		//卡表类型

	if(meterType == window.top.SDDef.YFF_METER_TYPE_NP){
		areano  = json_out.areano;
		pay_num = json_out.pay_num;
		pay_money = json_out.pay_money;
	}
	else {
		areano = "";
		pay_num = 0;
		pay_money = 0;
	}
	
	var tmp = "0000000000000000"
	if ((tmp.replace(/(^0*)/g, "") != areano.replace(/(^0*)/g, "")) ||
			(parseInt(pay_num) != 0)|| (parseFloat(pay_money) > 0.1 )) {
	
		alert("用户卡非空， 补卡前请先清空卡！")
		return false;
	}

	return true;
}

function doRepair(){//补卡

	var params = {};
	
	params.pay_money 		= $("#pay_money").val()	  ===	""?0:$("#pay_money").val();
	params.othjs_money 		= $("#othjs_money").val() ===	""?0:$("#othjs_money").val();
	params.zb_money 		= $("#zb_money").val()	  ===	""?0:$("#zb_money").val();
	
	params.all_money	 	= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),3);
	
	loading.loading();
	
	modalDialog.height 		= 300;
	modalDialog.width 		= 300;
	modalDialog.url 		= def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param 		= {
		showGSM : true,
		key	: ["客户编号","客户名称", "缴费金额","追补金额","结算金额","总金额"],
		val	: [$("#userno").html(),$("#username").html(), params.pay_money,params.zb_money,params.othjs_money,params.all_money]		
	};
	
	var o = modalDialog.show();
	if(!o||!o.flag){
		loading.loaded();
		return;
	}
	params.gsmFlag = o.gsm;//是否发送短信 
	
	window.setTimeout("doRepair1(" + o.gsm + ")", 10);
}

function doRepair1(gsm){
	var area_id = $("#area_id").val(), farmer_id = $("#farmer_id").val();
	if(area_id === "" || farmer_id === ""){
		alert(sel_user_info);
		loading.loaded();
		return;
	}
	
	//GetlastpayRcd(rtu_id,mp_id);//判断上次缴费信息
	
	var params = {};
	params.meter_type 		= WRITECARD_TYPE;
	params.areano			= $("#area_no").html();//区域号
	params.cardno			= $("#card_no").html();
	params.card_type  		= SDDef.NPCARD_OPTYPE_OPEN;				//补卡
	params.pay_money 		= $("#pay_money").val()===""?0:$("#pay_money").val();
	params.othjs_money 		= $("#othjs_money").val()===""?0:$("#othjs_money").val();
	params.zb_money 		= $("#zb_money").val()===""?0:$("#zb_money").val();
	params.all_money   		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money), def.point);
	params.buynum			= parseInt(rtnValue.buy_times) + 1;
	params.paytype 			= "0";

	params.area_id			= $("#area_id").val();
	params.farmer_id		= $("#farmer_id").val();
		
	params.lsremain			= 0;		//还需要处理

	params.gsmFlag = gsm;//是否发送短信 

	if (CheckCard() == false){
		loading.loaded();
		return;
	}

	$.post( def.basePath + "ajaxoper/actWebCard!getcardWrtInfo.action", 		//后台处理程序
		{
			result	: jsonString.json2String(params)
		},
		function(data) {			    	//回传函数
			var money = (params.all_money + parseFloat(params.lsremain))*100;
			if (window.top.card_comm.ReWriteCard(data.result, money)) {
				wrtDB_repair(params);
			}
			else {
				alert("写卡失败-电卡异常！\n");	
			}
			loading.loaded();
		}
	);
}

function wrtDB_repair(params){	//补卡
	//params.buynum = $("#buy_times").html();
	loading.loading();
	params.old_op_date	= lastpayRcd.op_date;
	params.old_op_time 	= lastpayRcd.op_time
	
	params.misUseflag   = misUseflag;
	

	var json_str = jsonString.json2String(params);

	$.post( def.basePath + "ajaxoper/actOperNp!taskProc.action", 		//后台处理程序
		{
			userData1		: params.area_id,
			farmerid		: params.farmer_id,
			gsmflag 		: 0,
			npOperRepair    : json_str,
			userData2 		: ComntUseropDef.YFF_NPOPER_REPAIR
		},
	    function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {						
					alert("补卡成功!");
					getRecord(rtnValue.areaid,rtnValue.farmerid);
					$("#btnPrt").attr("disabled",false);
					$("#repair").attr("disabled",true);
					setDisabled(true);
					window.top.WebPrint.setYffDataOperIdx2params(data.npOperRepair,window.top.WebPrint.nodeIdx.npcard);//打印用的参数
			}
			else {
				alert("向主站发送冲正命令失败,请清空卡后重试..." + (data.operErr ? data.operErr : ''));
			}
		}
	);
}

function cardinfo(){	//卡内信息
	modalDialog.width = 500;
	modalDialog.height = 600;
	modalDialog.url = def.basePath + "jsp/dialog/cardinfo.jsp";
	modalDialog.show();
}

function printPayRec() {
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.npcard);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}

function setDisabled(mode){	//设置状态
	if(!mode){
		$("#othjs_money").val("");
		$("#pay_money").val("");
		$("#zb_money").val("");
		$("#buy_times").html("");
		$("#last_zje").html("");
		$("#all_money").html("");
		$("#card_no").html("");
		$("#lastpay_money").html("");
	}

    //$("#repair_type").attr("disabled",mode);
	//$("#cardinfo").attr("disabled",mode);
	//$("#metinfo").attr("disabled",mode);
	$("#pay_money").attr("disabled",mode);
	$("#othjs_money").attr("disabled",mode);
	$("#zb_money").attr("disabled",mode);
}

function setcons1(json,null_flag) {
	if(!null_flag){
		$("#userno").html(json.userno);
		$("#username").html(json.username);
		$("#tel").html(json.tel);
		$("#useraddr").html(json.useraddr);
		$("#cus_state").html(json.cus_state);
		$("#identityno").html(json.identityno);
		$("#village").html(json.village);
		$("#jsyhm").html(json.jsyhm);
		$("#yhfl").html(json.yhfl);
		
		//$("#card_no").val(json.card_no);
		$("#area").html(json.area);
		$("#area_no").html(json.area_code);

		$("#area_id").val(json.areaid);
		$("#farmer_id").val(json.farmerid);

	}
	else{
		$("#userno").html("");
		$("#username").html("");
		$("#tel").html("");
		$("#useraddr").html("");
		$("#cus_state").html("");
		$("#identityno").html("");
		$("#village").html("");
		$("#jsyhm").html("");
		$("#yhfl").html("");
		
		//$("#card_no").val(json.card_no);
		$("#area").html("");
		$("#area_no").html("");
		
		$("#area_id").val("");
		$("#farmer_id").val("");
	}
}