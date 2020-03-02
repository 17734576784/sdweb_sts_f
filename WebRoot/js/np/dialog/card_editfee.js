$(document).ready(function(){
	
	$("#write_card").click(function(){write_card()});
	initArea();
});

function initArea() {
	
	$.post(def.basePath + "ajaxnp/actConsPara!getAreaCode.action",{},function(data){
		if(data.result != ""){
			var json0 = eval('(' + data.result + ')');

			for ( var i = 0; i < json0.length; i++) {
				$("#area").append("<option value="+json0[i].value+">"+json0[i].text+"["+json0[i].value+"]</option>");
			}
		}
	});
}

function write_card() {
	var card_no = "1111111111111111";
	var fee = $("#fee").val();
	if(isNaN(fee) || fee == "") {
		alert("电价请输入数字！");
		return;
	}
	var card_str = [];
	var idx = 0;
	card_str[idx++] = card_no;
	card_str[idx++] = "";
	card_str[idx++] = $("#area").val();
	card_str[idx++] = $("#area").val();
	card_str[idx++] = "";
	card_str[idx++] = "";
	card_str[idx++] = "";
	card_str[idx++] = "";
	card_str[idx++] = "";
	card_str[idx++] = fee;
	card_str[idx++] = "";
	card_str[idx++] = NPCARD_OPTYPE_FEE;
	
	var str = YFF_METER_TYPE_NP;
	for(var i = 0; i < card_str.length; i++) {
		str += "|" + card_str[i];
	}
	
	var jobj_out = window.top.card_comm.tool_writecard(str);
	if(jobj_out.errno == 0) {
		alert("写卡成功!");
	}
	else {
		alert(jobj_out.errstr + "\n写卡失败..");
	}
}