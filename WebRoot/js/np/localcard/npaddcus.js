var rtnValue =null;
var card_state = null;//电卡状态20130419
//var rtnBD;
var WRITECARD_TYPE = window.top.SDDef.YFF_CARDMTYPE_KE005;

$(document).ready(function(){
	
	$("#btnSearch").click(function(){selcons()});
	$("#btnRead").click(function(){read_card()});//读卡
	$("#cardinfo").click(function(){window.top.card_comm.cardinfo()});
	
	$("#btnNew").click(function(){
		if(check()) wrtCard_open();
	  }
	);

	$("#btnPrt").click(function(){printPayRec()});
	
	$("#yzje").change(function(){calcTotal();});
	$("#jfje").keyup(function(){calcTotal();});
	$("#jsje").keyup(function(){calcTotal();});
	
	setDisabled(true);
	$("#btnSearch").focus();
});

function selcons(){//检索
	setcons1('',true);
	setDisabled(false);
	$("#btnPrt").attr("disabled",true);
	var rtnValue1 = doSearch("ks",ComntUseropDef.YFF_NPOPER_ADDRES,rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
	//
	setcons1(rtnValue);
	
	$("#pay_type").val("0");	//卡式	
	$("#btnRead").attr("disabled",false);
}

function read_card(){
	
	$("#card_reading").html("正在读卡...");
	
	var json_out = {};
	json_out = window.top.card_comm.readcard();
	if(json_out == undefined || json_out ==""){
		alert("读卡错误!");
		$("#card_reading").html("");
	}
	//判断卡号是否被别人占用
	var meterType = json_out.meter_type;//卡表类型
	if(meterType != window.top.SDDef.YFF_METER_TYPE_NP){
		$("#card_reading").html("");
		alert("未知卡类型");
	}
	
	//此处还缺少购电卡状态
	var card_no = json_out.cardno;    //卡号
	var area_id = $("#area_id").val();
	var farmer_id = $("#farmer_id").val();
	var cardMsg = area_id + "," + farmer_id + "," + card_no;
	$("#card_no").val(card_no);
	$("#card_state").html(json_out.card_state_desc);
	
	//20130419灰锁状态判断
	card_state = json_out.card_state;
	
	///
	
	$.post(def.basePath + "ajaxnp/actConsPara!uniqueCardCode.action",{result : cardMsg},function(data){
		$("#card_reading").html("");
		if(data.result == "fail"){
			loading.loaded();
			alert("此卡号已被别的用户使用,不能开户!");
			$("#btnNew").attr("disabled",true);
		}else{
			$("#btnNew").attr("disabled",false);
			$("#jfje").attr("disabled",false);
			$("#jsje").attr("disabled",false);
		}
	});
	$("#card_reading").html("");
}

function setSearchJson2Html(js_tmp) {
	loading.loaded();
	if(!js_tmp)return;

	if(!window.top.card_comm.checkInfo(js_tmp)) return;
	rtnValue = js_tmp;	
	$("#card_no").val(rtnValue.card_no);
	$("#btnNew").attr("disabled",false);
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
		
		$("#area").html(json.area);
		$("#area_no").html(json.area_code);
		
		$("#area_id").val(json.areaid);
		$("#farmer_id").val(json.farmerid);
	
	}else{
		$("#userno").html("");
		$("#username").html("");
		$("#tel").html("");
		$("#useraddr").html("");
		$("#cus_state").html("");
		$("#identityno").html("");
		$("#village").html("");
		$("#jsyhm").html("");
		$("#yhfl").html("");
		
		$("#area").html("");
		$("#area_no").html("");
		
		$("#area_id").val("");
		$("#farmer_id").val("");
	}
}

function wrtDB_open(params){		//开户
	loading.loading();
	var json_str = jsonString.json2String(params);

	$.post( def.basePath + "ajaxoper/actOperNp!taskProc.action", 		//后台处理程序
		{
			userData1		: params.area_id,
			farmerid 		: params.farmer_id,
			gsmflag 		: params.gsmFlag,
			npOperAddres 	: json_str,
			userData2 		: ComntUseropDef.YFF_NPOPER_ADDRES
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				alert("开户成功!");
				setDisabled(true);
				window.top.WebPrint.setYffDataOperIdx2params(data.npOperAddres,window.top.WebPrint.nodeIdx.npcard);//打印用的参数
				$("#btnPrt").attr("disabled",false);
				$("#btnRead").attr("disabled",true);
			}
			else {
				alert("向主站发送开户命令失败,请清空卡后重新开户!" + (data.operErr ? data.operErr : ''));
			}
			$("#btnNew").attr("disabled",true);
		}
	);
}


function calcTotal(){//总金额
	var jfje = $("#jfje").val() === "" ? 0 : $("#jfje").val();
	var jsje = $("#jsje").val() === "" ? 0 : $("#jsje").val();
	
	if(isNaN(jfje) || isNaN(jsje)){
		return;
	}
	$("#zje").html(round(parseFloat(jfje) /*- parseFloat(yzje)*/ + parseFloat(jsje), 3));
}

function wrtCard_open(){
	
	var params = {};
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= 0;
	params.all_money   		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),2);
	
	loading.loading();
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

	var area_id = $("#area_id").val();
	var farmer_id = $("#farmer_id").val();
	var card_no = $("#card_no").val();
	var cardMsg = area_id + "," + farmer_id + "," + card_no;

	params.gsmFlag = o.gsm;

	window.setTimeout("wrtCard_open1(" + o.gsm + ")", 10);
}

function wrtCard_open1(gsm) {
	if (!CheckCard()) {
		loading.loaded();
		return false;
	}
	
	
	var params = {};
	params.meter_type 		= WRITECARD_TYPE;
	params.areano			= $("#area_no").html();
	params.cardno			= $("#card_no").val();
	params.card_type		= SDDef.NPCARD_OPTYPE_OPEN;//开户、缴费、补卡
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= 0;
	params.all_money   		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),2);	
	params.buynum			= 1;
	params.paytype 			= "0";
	
	params.area_id			= $("#area_id").val();
	params.farmer_id		= $("#farmer_id").val();
	
	params.lsremain			= "0";		//还需要处理
	params.gsmFlag 			= gsm;

	$.post( def.basePath + "ajaxoper/actWebCard!getcardWrtInfo.action", 		//后台处理程序,后台反写内容尚未完善,还需补充!
			{
				result	: jsonString.json2String(params)
			},
			function(data) {			    	//回传函数
				var money = (params.all_money + parseFloat(params.lsremain))*100;
				if (window.top.card_comm.ReWriteCard(data.result, money)) {
					wrtDB_open(params);
				}
				else {
					alert("写卡失败-电卡异常！\n");	
				}
				loading.loaded();
			}
		);
}

function CheckCard()
{	
	var json_out = {};
	json_out = window.top.card_comm.readcard();
	if(json_out == undefined || json_out ==""){
		alert("读卡错误!");
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
			(parseInt(pay_num) != 0) || (parseFloat(pay_money) > 0.1 ))		{
	
		alert("开户前请先清空卡，不能开户！")
		return false;
	}
	
	//判断是否灰锁20130419
	if(card_state != "0") {
		alert("此卡处于灰锁状态，请先解扣后再开户！");
		return false;
	}

	return true;
}

function setDisabled(mode){
	if(!mode){
		//清空界面的信息,用于开户后再次查询,而无查询结果的情况
		$("#jfje").val("");
		$("#jsje").val("");
		$("#zje").html("");
		$("#card_no").val("");
		
		//结算客户名称  结算客户分类
		$("#jsyhm").html("");
		$("#yhfl").html("");
	}
	$("#jfje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#yzje").attr("disabled",mode);
}

function check(){
	
	var jfje = 0, zbje = 0, jsje = 0;
	jfje = $("#jfje").val()==="" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val()==="" ? 0 : $("#jsje").val();
	
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
	
	if(!isDbl("jsje",  "结算金额", -rtnValue.moneyLimit, rtnValue.moneyLimit, false)){
		return false;
	}
	
	if(!isDbl("jfje",  "缴费金额", 0, rtnValue.moneyLimit, false)){
		return false;
	}

	if(!isDbl_Html("zje" ,  "总金额", 0, rtnValue.moneyLimit, false)){//缴费金额应该在0~囤积值之间
		return false;
	}

	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
	}
	return true;
}


//卡号格式正则判断
function cardNoCheck(id){
	var value = $(id).val();
		var reg = /^\d{1,16}$/;
		if(!reg.test(value)){
			alert(" 卡号应由不多于16位的数字组成!");
			$(id).focus();
			return false;
		}
	return true;
}

//卡号不足16为，补足0
function cardNoFormat(id){
	var cardNo = $(id).val();
	var tmp = "";
	if(cardNo.length <16){
		for(var i=0; i<16-cardNo.length; i++){
			tmp += "0";
		}
	}
	return tmp.concat(cardNo);
}


function printPayRec() {
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.npcard);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}
