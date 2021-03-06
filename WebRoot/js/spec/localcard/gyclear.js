var qkkclick = false;
$(document).ready(function(){
	$("#btnRead").click(function(){ window.top.card_comm.readCardSearchGy(-1)});

	$("#cardinfo").click(function(){window.top.card_comm.cardinfo()});
	$("#clearcard").click(function(){ qkkclick = true; window.top.card_comm.readCardSearchGy(-1)});
});

function setSearchJson2Html(rtnValue){//调用readCardSearch后，readcard中异步执行完成后，执行此函数。

	if (rtnValue != undefined || rtuValue != null) {

	  //setConsValue(rtnValue);

		$("#userno").html(rtnValue.userno);
		$("#username").html(rtnValue.username);
		$("#userno").html(rtnValue.userno);
		$("#username").html(rtnValue.username);
		$("#tel").html(rtnValue.tel);
		$("#useraddr").html(rtnValue.useraddr);
		$("#cus_state").html(rtnValue.cus_state);
		
		$("#esamno").html(rtnValue.meter_id);
		$("#pt").html(rtnValue.pt_ratio);
		$("#ct").html(rtnValue.ct_ratio);
		$("#cacl_type").html(rtnValue.cacl_type_desc);
		$("#feeproj_desc").html(rtnValue.feeproj_detail);
		$("#buyp_times").html(rtnValue.buy_times);
		$("#alarm_val").html(rtnValue.yffalarm_detail);
	
		$("#card_type").html(rtnValue.cardtype);
		$("#xkje").html(rtnValue.paymoney);
	}
	else {
		clearUserInfo(); 
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
			WRITECARD_TYPE = $("#clearcard_type").val();
		}	
		else {
			var tmp_val = rtnValue.cardmeter_type.split("]");
			WRITECARD_TYPE = tmp_val[0].split("[")[1];
		}
		
		wrt_zero_card(WRITECARD_TYPE);
	}	
}

function wrt_zero_card(WRITECARD_TYPE){//清空卡

	var params = {};
	params.meter_type 		= WRITECARD_TYPE;

	loading.loading();
	$.post( def.basePath + "ajaxoper/actWebCard!getcardClear.action", 		//后台处理程序
		{
			result	: jsonString.json2String(params)
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
	$("#esamno").html("");
	
	$("#pt").html("");
	$("#ct").html("");
	$("#cacl_type").html("");
	$("#card_type").html("");
	
	$("#xkje").html("");
	$("#buyp_times").html("");
	$("#alarm_val").html("");
	$("#feeproj_desc").html("");
}

