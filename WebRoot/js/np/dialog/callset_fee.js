var callset_item = [];
var retValue = null;
var glo_optype = null;
var glo_finishflag = false;
$(document).ready(function(){
	initCleargq();
	for(var i = 0; i < 8; i++) {
		callset_item[i] = false;
		$("#val" + i).attr("disabled", true);
		$("#val" + i).css("background", "#eee");
	}
	
	if(window.top.child_page.retValue){
		retValue = window.top.child_page.retValue;//父页面的参数
		initInput(retValue);
	}
});

function initInput(obj){
	if(obj){
		var value = [Math.floor(obj.feeproj_id * 10000), obj.area_code, obj.ptct, Math.floor(obj.yffalarm_id * 100), Math.floor(obj.pow_limit * 10000), Math.floor(obj.nocycut_min * 60)];
		for(var i = 0; i < 6; i++) {
			$("#val" + i).val(value[i]);
		}
	}
}

function initCleargq() {
	for(var i = 0; i < 20; i++) {
		$("#val7").append("<option value='"+i+"'>第"+(i+1)+"条</option>")
	}
}

function checkItem(chk) {
	var id = chk.id;
	idx = id.substring(id.length - 1);
	callset_item[idx] = chk.checked;
	
	$("#val" + idx).attr("disabled", !chk.checked);
	if(chk.checked) {
		$("#val" + idx).css("background", "white");
	}
	else {
		$("#val" + idx).css("background", "#eee");
	}
}

var op_type = [
	ComntProtMsg.YFF_CALL_DL645_CALL_FEE,
	ComntProtMsg.YFF_CALL_DL645_CALL_AREA,
	ComntProtMsg.YFF_CALL_DL645_CALL_RATE,
	ComntProtMsg.YFF_CALL_DL645_CALL_ALARM,
	ComntProtMsg.YFF_CALL_DL645_CALL_POWLINIT,
	ComntProtMsg.YFF_CALL_DL645_CALL_NOCYCUT,
	ComntProtMsg.YFF_CALL_DL645_CALL_LOCK
	];

function taskCall(ii) {
	if(!ii){
		if(!retValue) {
			alert("请选择电表！");
			return;
		}
		setDisabled(true);
		ii = 0;
		window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	}
	if(ii >= 7 || glo_finishflag) {
		glo_optype = null;
		glo_finishflag = false;
		
		setDisabled(false);
		return;
	}
	
	if(!callset_item[ii]) {
		taskCall(ii + 1);
		return;
	}

	$("#val" + ii).val("");
	
	glo_optype = ComntUseropDef.YFF_NPOPER_CALLBASE;
	
	$("#loading_item" + ii).html("<img src='../../../images/indicator.gif' />");
	$.post(def.basePath + "ajaxoper/actCommNp!taskProc.action", {
		userData1 		: retValue.rtu_id,
		userData2 		: ComntUseropDef.YFF_NPOPER_CALLBASE,
		mpid 			: retValue.mp_id,
		npCommCallBase 	: '{data_idx : ' + ii + ',datatype:' + op_type[ii] + '}'
	}, function(data){
		window.top.addJsonOpDetail(data.detailInfo);
		if(data.result == "success") {
			$("#loading_item" + ii).html("<img src='../../../images/tick.gif' />");
			var json = eval("(" + data.npCommCallBase + ")");
			$("#val" + ii).val(json.val);
		}
		else {
			$("#loading_item" + ii).html("<img src='../../../images/cross.gif' />");
		}
		taskCall(ii + 1);
	});
}

function getTaskSetPara(idx) {
	if (idx > 7) return;
	var ret_val = "";
	
	//var area_code = retValue.area_code;
	var area_code = parent.$("#area_code").val();
	var pwd       = parent.$("#pwd").val();
	
	//电价
	if (idx == 0) {
		var fee = $("#val0").val();
		ret_val="{data_idx : " + idx + ", meter_area : '" + area_code + "', meter_pwd : '" + pwd + "', fee : " + fee + "}";
	}
	//区域号
	else if (idx == 1) {
		var new_area = $("#val1").val();
		ret_val = "{data_idx : " + idx + ", meter_area : '" + area_code + "', new_area : '" + new_area + "', meter_pwd : '" + pwd + "'}";
	}
	//变比
	else if (idx == 2) {
		var rate = $("#val2").val();
		ret_val="{data_idx : " + idx + ", meter_area : '" + area_code + "', meter_pwd : '" + pwd + "', rate : " + rate + "}";
	}
	//报警金额
	else if (idx == 3) {
		var val = $("#val3").val();
		ret_val="{data_idx : " + idx + ", meter_area : '" + area_code + "', meter_pwd : '" + pwd + "', val : " + val + "}";
	}
	//限定功率
	else if (idx == 4) {
		var val = $("#val4").val();
		ret_val="{data_idx : " + idx + ", meter_area : '" + area_code + "', meter_pwd : '" + pwd + "', val : " + val + "}";
	}
	//无采样自动断电时间
	else if (idx == 5) {
		var val = $("#val5").val();
		ret_val="{data_idx : " + idx + ", meter_area : '" + area_code + "', meter_pwd : '" + pwd + "', val : " + val + "}";
	}
	//电表锁定状态
	else if (idx == 6) {
		var val = $("#val6").val();
		ret_val="{data_idx : " + idx + ", meter_area : '" + area_code + "', meter_pwd : '" + pwd + "', val : " + val + "}";
	}
	//清除挂起记录
	else if (idx == 7) {
		var val = $("#val7").val();
		ret_val="{data_idx : " + idx + ", meter_area : '" + area_code + "', meter_pwd : '" + pwd + "', val : " + val + "}";
	}
	
	return ret_val;
}

function taskSet(ii) {
	if(!ii){
		if($("#esam_no").val() == "") {
			alert("请选择电表！");
			return;
		}
		setDisabled(true);
		ii = 0;
		window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	}
	if(ii > 7 || glo_finishflag) {
		glo_optype = null;		
		glo_finishflag = false;
		setDisabled(false);
		return;
	}
	
	if(!callset_item[ii]) {
		taskSet(ii + 1);
		return;
	}

	glo_optype = ComntUseropDef.YFF_NPOPER_SETBASE;
	
	var str_param = getTaskSetPara(ii);
	$("#loading_item" + ii).html("<img src='../../../images/indicator.gif' />");
	$.post(def.basePath + "ajaxoper/actCommNp!taskProc.action", {
		userData1 		: retValue.rtu_id,
		userData2 		: ComntUseropDef.YFF_NPOPER_SETBASE,
		mpid 			: retValue.mp_id,
		npCommSetBase 	: str_param
	}, function(data){
		if(data.result == "success") {
			window.top.addJsonOpDetail(data.detailInfo);
			$("#loading_item" + ii).html("<img src='../../../images/tick.gif' />");
		}
		else {
			$("#loading_item" + ii).html("<img src='../../../images/cross.gif' />");
		}
		taskSet(ii + 1);
	});
}

function setDisabled(flag) {
	for(var i = 0; i < 8; i++) {
		$("#item" + i).attr("disabled", flag);
	}
}

function taskCancel() {
	if (glo_optype == null) return;
	
	glo_finishflag = true;
	
	$.post(def.basePath + "ajaxoper/actCommNp!cancelTask.action", {
		userData1 : retValue.rtu_id,
		userData2 : glo_optype
	});
}