$(document).ready(function(){
	$("#readcard").click(function(){window.top.card_comm.cardinfo()});
	$("#suocard").click(function(){card(NPCARD_OPTYPE_GRAYLOCK)});
	$("#unsuocard").click(function(){card(NPCARD_OPTYPE_GRAYUNLOCK)});
});

function card(type) {
	
	var card_str = [];
	for(var i = 0; i < 11; i++) {
		card_str[i] = "";
	}
	card_str[11] = type;
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
