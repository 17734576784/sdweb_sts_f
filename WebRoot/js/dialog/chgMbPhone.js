var params = window.dialogArguments;

$(document).ready(function() {
	var username = $("#username").val(params.username);
	var old_tel = $("#old_mbphone").val(params.tel);
	$("#xiugai").click(function() {
		if(check()) {
			chg();
		}
	});

	$("#close").click(function() {
		window.close();
	});
});

function chg() {
	var new_tel = $("#new_mbphone").val();
	var o = {};
	if(params.flag == "gy"){
		o.rtu_id = params.rtu_id;
		o.zjg_id = params.zjg_id;
		o.chgtype = SDDef.YFF_GY_CHGPARATYPE_MOBILE;
	}else if(params.flag == "dy"){
		o.rtu_id = params.rtu_id;
		o.mp_id = params.mp_id;
		o.chgtype = SDDef.YFF_DY_CHGPARATYPE_MOBILE;
		o.resid = params.resident_id;
	}else if(params.flag == "np"){
		o.area_id = params.areaid;
		o.farmer_id = params.farmerid;
		o.chgtype = SDDef.YFF_NP_CHGPARATYPE_MOBILE;
	}
	o.filed1 = new_tel;
	o.filed2 = "";
	o.filed3 = "";
	o.filed4 = "";
	o.gsmFlag = "0";//短信通知不发
	
	window.returnValue = o;
	window.close();
}

	function check() {
		if (!isMobilePhone("new_mbphone",ghsj, false)) {
			return false;
		}
		return true;
	}
