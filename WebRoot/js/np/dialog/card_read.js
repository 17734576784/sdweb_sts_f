$(document).ready(function(){

	$("#read_card").click(function(){read_card()});
	$("input").attr("readonly", true);
});

function read_card() {
	var ret = window.top.card_comm.tool_readcard(NPCARD_OPTYPE_READCARDTYPE);
	if(ret.errno != 0) {
		alert(ret.errstr + "\nCallProc读卡失败..");
		return;
	}
	var str = ret.strinfo;
	str = str.split("|");
	var card_info = [];
	
	var type = str[str.length - 2];
	ret = window.top.card_comm.tool_readcard(type);
	if(ret.errno != 0) {
		alert(ret.errstr + "\nCallProc读卡失败..");
		return;
	}
	var str = ret.strinfo.split("|");
	for(var i = 2; i < str.length - 3; i++) {
		$("#cf" + (i - 1)).val(str[i]);
	}
	
	if(str[str.length - 3] == 0) {
		$("#cf10").val("否");
	} else {
		$("#cf10").val("是");
	}
	
	var types = ["参数设置卡","变比卡","区域修改卡","电价卡","未知"];
	if(type == NPCARD_OPTYPE_SETPARA) $("#cf11").val(types[0]);
	else  if(type == NPCARD_OPTYPE_PTCT) $("#cf11").val(types[1]);
	else  if(type == NPCARD_OPTYPE_AREA) $("#cf11").val(types[2]);
	else  if(type == NPCARD_OPTYPE_FEE) $("#cf11").val(types[3]);
	else $("#cf11").val(types[4]);
}