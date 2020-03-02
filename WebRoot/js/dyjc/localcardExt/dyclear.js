var rtnValue    = null;
var qkkclick = false;

$(document).ready(function(){
	initDB();
	$("#btnRead").click(function()	{ window.top.card_comm.readExtCardSearchDy(-1)});
	
	$("#cardinfo").click(function()	{ window.top.card_comm.extcardinfo()});
	$("#clearcard").click(function(){ qkkclick = true; window.top.card_comm.readExtCardSearchDy(-1)});
});

function initDB(){
	//{tables:[table1:"table1",table2:"table2"]}
	var tables = '{tables:["'+ YDTable.TABLECLASS_OCARDTYPE_PARA + '"]}';
	$.post(def.basePath + "ajax/actCommon!getValueAndTextExtCard.action",{tableName: tables},function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			
			var json0 = eval("json.rows[0]." + YDTable.TABLECLASS_OCARDTYPE_PARA);
			
			for ( var i = 0; i < json0.length; i++) {
				$("#clearcard_type").append("<option value="+json0[i].value+">"+json0[i].text+"</option>");
			}
		}
	});
}


function setSearchJson2Html(rtnValue){//调用readCardSearch后，readcard中异步执行完成后，执行此函数。
	if (rtnValue != undefined || rtuValue != null) {

		$("#userno").html(rtnValue.userno);
		$("#username").html(rtnValue.username);
		$("#tel").html(rtnValue.tel);
		$("#useraddr").html(rtnValue.useraddr);
		$("#writecard_no").html(rtnValue.writecard_no);
		$("#esamno").html(rtnValue.meter_id);
		$("#pt").html(rtnValue.pt);
		$("#ct").html(rtnValue.ct);
		$("#cacl_type").html(rtnValue.cacl_type_desc);
		$("#card_type").html(rtnValue.cardtype);
		$("#xkje").html(rtnValue.paymoney);
		$("#buyp_times").html(rtnValue.buy_times);
		$("#alarm_val").html(rtnValue.yffalarm_detail);
		$("#feeproj_desc").html(rtnValue.feeproj_detail);
	}
	else {
		clearUserInfo();
	}
	
	if(qkkclick){
		var cfmstr = "";
		if ((!rtnValue) ||(rtnValue.userno == undefined)){
			cfmstr = "没有找到用户信息，确认要清空卡？";
			//从下拉框中取得对应的卡类型
			rtnValue.cardtype = $("#clearcard_type").val(); 
			rtnValue.card_pass = $("#card_pass").val();	//密码可以从输入框中输入
		}else{
			cfmstr = "确认要清空客户[" + $("#username").html() + "]、编号["+$("#userno").html()+"]?";
		}

		var con_m = confirm(cfmstr); 
		if( con_m== false ||  con_m== undefined){
			qkkclick = false;
			return;
		}

//		var WRITECARD_TYPE = window.top.SDDef.YFF_CARDMTYPE_KE001;
//		
//		if ((!rtnValue) ||(rtnValue.userno == undefined)){
//			WRITECARD_TYPE = $("#clearcard_type").val(); 
//		}	
//		else {
//			var tmp_val = rtnValue.cardmeter_type.split("]");
//			WRITECARD_TYPE = tmp_val[0].split("[")[1];
//		}
		wrt_zero_card(rtnValue.cardtype, rtnValue.card_pass);
	}	
}

function wrt_zero_card(cardtype, card_pass){//清空卡
//	alert(cardtype + "  " + card_pass);
	
	var params = {};
	params.cardtype 	= cardtype;				//预付费电表类型
	params.card_pass	= card_pass;				//江机使用
//alert(params.cardtype + "  " + params.card_pass);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actWebCard!getExtcardClear.action", 		//后台处理程序
		{
			result	: jsonString.json2String(params)
		},
		function(data) {			    	//回传函数
			var retStr 	 = data.result.toString();
			var ret_json = window.top.card_comm.writeExtcard(retStr);
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
	$("#writecard_no").html("");
	$("#pt").html("");
	$("#ct").html("");
	$("#cacl_type").html("");
	$("#card_type").html("");
	
	$("#xkje").html("");
	$("#buyp_times").html("");
	$("#alarm_val").html("");
	$("#feeproj_desc").html("");
}

function cardinfo(){//卡内信息
	modalDialog.width = 500;
	modalDialog.height = 600;
	modalDialog.url = "../../dialog/cardinfo.jsp";
	modalDialog.show();
}

