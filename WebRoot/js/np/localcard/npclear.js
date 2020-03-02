var rtnValue    = null;
var qkkclick = false;
var card_no  = "";
var now_remain = 0;

$(document).ready(function(){
	$("#btnRead").click(function()	{ window.top.card_comm.readCardSearchNp(-1)});
	
	$("#cardinfo").click(function()	{ window.top.card_comm.cardinfo()});
	$("#clearcard").click(function(){ qkkclick = true; window.top.card_comm.readCardSearchNp(-1)});
});

function setSearchJson2Html(rtnValue){//调用readCardSearch后，readcard中异步执行完成后，执行此函数。

	if (rtnValue != undefined || rtuValue != null) {
		
		card_no = rtnValue.card_no;
		if (card_no == 0) {
			alert("不可识别的卡号，不能够清卡！");
			return;
		}
		
		if(rtnValue.now_remain > 0) {
			now_remain = rtnValue.now_remain;
		}
		
		
		$("#userno").html(rtnValue.userno);
		$("#username").html(rtnValue.username);
		$("#tel").html(rtnValue.tel);
		$("#useraddr").html(rtnValue.useraddr);
		$("#cus_state").html(rtnValue.cus_state);
		$("#identityno").html(rtnValue.identityno);
		$("#village").html(rtnValue.village);
		$("#jsyhm").html(rtnValue.jsyhm);
		$("#yhfl").html(rtnValue.yhfl);
		
		$("#card_no").html(rtnValue.card_no);
		$("#card_state").html(rtnValue.card_state_desc);
		$("#area").html(rtnValue.area);
		$("#area_no").html(rtnValue.area_code);
	}
	else {
		//应该获取卡号--------
		clearUserInfo();
		return;
	}
	
	if(qkkclick){
		var cfmstr = "";

		if ((!rtnValue) ||(rtnValue.userno == undefined)){
			cfmstr = "没有找到用户信息，确认要清空卡？";
		}else{
			cfmstr = "确认要清空客户[" + $("#username").html() + "]、编号["+$("#userno").html()+"]?";
		}
	
		var con_m = confirm(cfmstr); 
		if( con_m== false ||  con_m== undefined){
			qkkclick = false;
			return;
		}

		var WRITECARD_TYPE = 1;
		if ((!rtnValue) ||(rtnValue.userno == undefined)){
			WRITECARD_TYPE = SDDef.YFF_CARDMTYPE_KE005; 
		}	
		else {
			var tmp_val = rtnValue.cardmeter_type.split("]");
			WRITECARD_TYPE = tmp_val[0].substring(1);
		}

		wrt_zero_card(WRITECARD_TYPE);
	}	
}

function wrt_zero_card(WRITECARD_TYPE){//清空卡

	var params = {};
	params.meter_type = WRITECARD_TYPE;
	params.now_remain = now_remain;
	
	loading.loading();
	$.post( def.basePath + "ajaxoper/actWebCard!getcardClear.action", 		//后台处理程序
		{
			result		: jsonString.json2String(params),
			card_no_np	: card_no
		},
		function(data) {			    	//回传函数
			var retStr 	 = data.result.toString();

			var ret_json = window.top.card_comm.writecard(retStr);
			loading.loaded();
			if(ret_json.errno == 0){
				alert("清空卡成功！");
			}else{
				alert("清空卡失败！\n" + ret_json.errstr);
			}
			qkkclick = false;
		}
	);
}

function clearUserInfo() {
	
	
	$("#userno").html("");
	$("#username").html("");
	$("#tel").html("");
	$("#useraddr").html("");
	$("#cus_state").html("");
	$("#identityno").html("");
	$("#village").html("");
	$("#jsyhm").html("");
	$("#yhfl").html("");
	
	$("#card_no").html("");
	$("#card_state").html("");
	$("#area").html("");
	$("#area_no").html("");
}

function cardinfo(){//卡内信息
	modalDialog.width = 500;
	modalDialog.height = 600;
	modalDialog.url = "../../dialog/cardinfo.jsp";
	modalDialog.show();
}
