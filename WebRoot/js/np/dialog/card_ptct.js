
$(document).ready(function(){
	
	$("#sel_meter").click(function(){sel_meter()});
	$("#write_card").click(function(){write_card()});
});

function sel_meter() {
	modalDialog.height = 450;
	modalDialog.width = 650;
	modalDialog.url = def.basePath + "jsp/np/dialog/card_selectmeter.jsp";
	modalDialog.param = {meter_id : ""};
	var ret = modalDialog.show();
	if(!ret) return;
	
	$("#esam_no").val(ret.meter_id);
	$("#area_no").val(ret.area_code);
	$("#ptct").val(ret.ptct);
}

function write_card() {
	var card_no = "1111111111111111";
	var meter_id = $("#esam_no").val();
	if(meter_id == "") {
		alert("请选择表号!");
		return;
	}
	var card_str = [];
	var idx = 0;
	card_str[idx++] = card_no;
	card_str[idx++] = meter_id;
	card_str[idx++] = $("#area_no").val();
	card_str[idx++] = $("#area_no").val();
	card_str[idx++] = "";
	
	card_str[idx++] = "";
	card_str[idx++] = "";
	card_str[idx++] = $("#ptct").val();
	card_str[idx++] = "";
	card_str[idx++] = "";
	card_str[idx++] = "";
	card_str[idx++] = NPCARD_OPTYPE_PTCT;
	
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