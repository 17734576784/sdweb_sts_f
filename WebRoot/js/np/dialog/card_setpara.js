
$(document).ready(function(){

	initDict();

	$("#sel_meter").click(function(){sel_meter()});
	$("#write_card").click(function(){write_card()});
});

function initDB(){
	var tables = '{tables:[{table:"'+YDTable.TABLECLASS_YFFRATEPARA+'",field:["fee_type"],value:[0]},{table:"'+YDTable.TABLECLASS_YFFALARMPARA+'",field:["type"],value:[0]}]}';
	$.post(def.basePath + "ajax/actCommon!getValueAndText.action",{tableName: tables},function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			
			var json0 = eval("json.rows[0]." + YDTable.TABLECLASS_YFFRATEPARA);
			var json1 = eval("json.rows[1]." + YDTable.TABLECLASS_YFFALARMPARA);
			
			for ( var i = 0; i < json0.length; i++) {
				$("#feeprojId").append("<option value="+json0[i].value+">"+json0[i].text+"</option>");
			}
			for ( var i = 0; i < json1.length; i++) {
				$("#yffalarmId").append("<option value="+json1[i].value+">"+json1[i].text+"</option>");
			}
			$("#feeprojId").change();
			$("#yffalarmId").change();
		}
	});
}

function initDict(){
	var text = '{item:["' + Dict.DICTITEM_YESFLAG + '"]}';
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : text}, function(data){
		var json = eval('(' + data.result + ')');
		var iid = 0;
		var dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_YESFLAG);
		for ( var i = 0; i < dict.length; i++) {
			$("#powerup_prot").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
	});
}

function sel_meter() {
	modalDialog.height = 450;
	modalDialog.width = 650;
	modalDialog.url = def.basePath + "jsp/np/dialog/card_selectmeter.jsp";
	modalDialog.param = {meter_id : ""};
	var ret = modalDialog.show();
	if(!ret) return;
	
	$("#esam_no").val(ret.meter_id);
	$("#area_no").val(ret.area_code);
	$("#pow_limit").val(Math.floor(ret.pow_limit * 10000));
	$("#powerup_prot").val(ret.powerup_prot);
	$("#feeprojId").val(Math.floor(ret.feeproj_id * 10000));
	$("#yffalarmId").val(Math.floor(ret.yffalarm_id * 100));
	$("#nocycut_min").val(Math.floor(ret.nocycut_min * 60));
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
	card_str[idx++] = $("#pow_limit").val();
	card_str[idx++] = $("#yffalarmId").val();
	card_str[idx++] = $("#ptct").val();
	card_str[idx++] = $("#nocycut_min").val();
	card_str[idx++] = $("#feeprojId").val();
	card_str[idx++] = $("#powerup_prot").val();
	card_str[idx++] = NPCARD_OPTYPE_SETPARA;
	
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