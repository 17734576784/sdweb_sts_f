var params = window.dialogArguments;
var param = params.id.split(",");
//var loaded=[false,false,false];
//var txml;
$(document).ready(function(){
	loading.loading();
	initDiction();
	
	$("#xiugai").show();
	
	$("#xiugai").click(function(){
		if(check()){
			addOrEdit("edit");
		};
	});
});

function initDiction(){
	var text = '{item:["' + Dict.DICTITEM_YESFLAG + '","' + Dict.DICTITEM_METERACCURACY + '","' + Dict.DICTITEM_POWER_USETYPE + '"]}';
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : text}, function(data){

		var json = eval('(' + data.result + ')');
		var iid = 0;
		
		//设备加封标志
		var dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_YESFLAG);
		for ( var i = 0; i < dict.length; i++) {
			$("#ext_seal_flag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		
		//电表精度
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_METERACCURACY);
		for ( var i = 0; i < dict.length; i++) {
			$("#ext_meter_accuracy").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		
		//用电类别
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_POWER_USETYPE);
		for ( var i = 0; i < dict.length; i++) {
			$("#ext_use_type").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		
		setValue();			
	});
}

function setValue(){//根据Zjgid,rtuid 取得一条记录。	
	if (param == undefined) return;
	
	var rtuid = param[0],  zjgid = param[1];
	if (rtuid == "" || zjgid == "") return;
	
	$("#rtuId").val(rtuid);
	$("#zjgId").val(zjgid);	
	$("#xiugai").attr("disabled",false);

	var field = "";
	$.post(def.basePath  + "ajaxdocs/actYffParaExt!getExtParaById.action", {result : rtuid+","+zjgid, field : field}, function(data) {
		loading.loaded();
		
		if(data.result != ""){
		var json = eval('(' + data.result + ')');
		
		$("#cons_desc").val(json.cons_desc);
		$("#cons_busi_no").val(json.cons_busi_no);
		$("#rtupoll_str").val(json.rtupoll_str);
		$("#cons_line_str").val(json.cons_line_str);
		$("#rtu_simcard_str").val(json.rtu_simcard_str);
		$("#rtu_simcard_id").val(json.rtu_simcard_id);
		$("#ext_month_dl").val(json.ext_month_dl);
		$("#ext_month_money").val(json.ext_month_money);
		$("#zjgpay_feeproj_str").val(json.zjgpay_feeproj_str);
		$("#ext_use_type").val(json.ext_use_type);
		$("#ext_moneydl_per").val(json.ext_moneydl_per);
		
		//倍率
		$("#beilu").val(json.meter_ptratio*json.meter_ctratio);
		
		$("#ext_ctano").val(json.ext_ctano);
		$("#ext_ctbno").val(json.ext_ctbno);
		$("#ext_ctcno").val(json.ext_ctcno);
		
		$("#meter_factory").val(json.meter_factory);
		$("#meter_assetno").val(json.meter_assetno);
		$("#ext_meter_accuracy").val(json.ext_meter_accuracy);
		
		$("#rtu_inst_date").val(json.rtu_inst_date);
		dateFormat.date = json.rtu_inst_date;
		$("#rtu_inst_dateF").val(dateFormat.formatToYMD(0))
		
		$("#rtu_asset_no").val(json.rtu_asset_no);
		$("#ext_seal_flag").val(json.ext_seal_flag);
		
		$("#meter_ptnumerator").val(json.meter_ptnumerator);
		$("#meter_ptdenominator").val(json.meter_ptdenominator);
		$("#meter_ptratio").val(json.meter_ptratio);
		$("#textpt").val(json.meter_ptnumerator + "/" + json.meter_ptdenominator + "=" + json.meter_ptratio);
		
		$("#meter_ctnumerator").val(json.meter_ctnumerator);
		$("#meter_ctdenominator").val(json.meter_ctdenominator);
		$("#meter_ctratio").val(json.meter_ctratio);
		$("#textct").val(json.meter_ctnumerator + "/" + json.meter_ctdenominator + "=" + json.meter_ctratio);
		
		$("#ext_ctrlline_addr").val(json.ext_ctrlline_addr);
		$("#ext_ctrlfh_explain").val(json.ext_ctrlfh_explain);
		
		$("#cons_fz_man").val(json.cons_fz_man);
		$("#cons_tel_no1").val(json.cons_tel_no1);
		$("#ext_moneyman").val(json.ext_moneyman);
		$("#ext_moneyman_telno1").val(json.ext_moneyman_telno1);	
		
		$("#mpId").val(json.meter_mpid);
		$("#pollId").val(json.rtupoll_id);
	}
	});
}

//增加setValue_()方法
function setValue_(id,data){
	$("#"+id).val(data);
}

function setDateValue() {
	
	var tmp = ["feeChgdate","jbfChgdate","fxdfBegindate","feeBegindate"];
	
	for ( var i = 0; i < tmp.length; i++) {
		var tmpvalue = $("#" + tmp[i] + "F").val();
		if (tmpvalue != "") {
			$("#" + tmp[i]).val(tmpvalue.substring(0, 4) + tmpvalue.substring(5, 7) + tmpvalue.substring(8, 10));
		}else{
			$("#" + tmp[i]).val(0);
		}
	}
	tmp = ["feeChgtime","jbfChgtime"];
	for ( var i = 0; i < tmp.length; i++) {
		var tmpvalue = $("#" + tmp[i] + "F").val();
		if (tmpvalue != "") {
			$("#" + tmp[i]).val(tmpvalue.substring(0, 2) + tmpvalue.substring(3, 5) + tmpvalue.substring(6, 8));
		}else{
			$("#" + tmp[i]).val(0);
		}
	}
	tmp = ["cbDayhour"];
	for ( var i = 0; i < tmp.length; i++) {
		var tmpvalue = $("#" + tmp[i] + "F").val();
		
		if (tmpvalue != "") {
			$("#" + tmp[i]).val(tmpvalue.substring(0, 2) + tmpvalue.substring(3, 5));
		}else{
			$("#" + tmp[i]).val(0);
		}
	}
}

function getParams()
{
	var json_obj = { };

	json_obj.rtu_id					= $("#rtuId").val();
	json_obj.zjg_id					= $("#zjgId").val();
	json_obj.mp_id                  = $("#mpId").val();
	json_obj.poll_id				= $("#pollId").val();
	
	json_obj.cons_busi_no			= $("#cons_busi_no").val();
	json_obj.rtupoll_str			= $("#rtupoll_str").val();

	json_obj.rtu_simcard_id			= $("#rtu_simcard_id").val();

	json_obj.ext_month_dl			= $("#ext_month_dl").val();
	json_obj.ext_month_money		= $("#ext_month_money").val();

	json_obj.ext_use_type			= $("#ext_use_type").val();
	json_obj.ext_moneydl_per		= $("#ext_moneydl_per").val();

	json_obj.ext_ctano				= $("#ext_ctano").val();
	json_obj.ext_ctbno				= $("#ext_ctbno").val();
	json_obj.ext_ctcno				= $("#ext_ctcno").val();

	json_obj.meter_factory			= $("#meter_factory").val();
	json_obj.meter_assetno			= $("#meter_assetno").val();
	json_obj.ext_meter_accuracy 	= $("#ext_meter_accuracy").val();

	json_obj.rtu_inst_date			= $("#rtu_inst_date").val();

	json_obj.rtu_asset_no			= $("#rtu_asset_no").val();
	json_obj.ext_seal_flag			= $("#ext_seal_flag").val();

	json_obj.meter_ptnumerator		= $("#meter_ptnumerator").val();
	json_obj.meter_ptdenominator 	= $("#meter_ptdenominator").val();
	json_obj.meter_ptratio			= $("#meter_ptratio").val();

	json_obj.meter_ctnumerator	 	= $("#meter_ctnumerator").val();
	json_obj.meter_ctdenominator 	= $("#meter_ctdenominator").val();
	json_obj.meter_ctratio			= $("#meter_ctratio").val();

	json_obj.ext_ctrlline_addr		= $("#ext_ctrlline_addr").val();
	json_obj.ext_ctrlfh_explain		= $("#ext_ctrlfh_explain").val();

	json_obj.cons_fz_man			= $("#cons_fz_man").val();
	json_obj.cons_tel_no1			= $("#cons_tel_no1").val();
	json_obj.ext_moneyman			= $("#ext_moneyman").val();
	json_obj.ext_moneyman_telno1	= $("#ext_moneyman_telno1").val();
	
	return jsonString.json2String(json_obj);
}

function addOrEdit(flag){//添加、修改	
//	setDateValue();
	var params = getParams();//$('#addorupdate').serialize();
//	alert(params); return;
	
	$.post( def.basePath + "ajaxdocs/actYffParaExt!addOrEdit.action", 		//后台处理程序
		{
			result : params
		},
		function(data) {			    	//回传函数		
			
/*			
			var img_str = 'tick.gif';
			if (data.result == YDDef.SUCCESS) {
				
			}
			else {
				img_str = 'cross.gif';			
			}
			$("#loading_mastip").html(commFunc.getHtmlImageItem(img_str));
			addJsonOpDetail(data.detailInfo);					
			userop_mastip_flag = false;
			onUserOpFinished();
			
			if (!userop_cancel_flag) {			
				if($("#chkrturrset").attr("checked")){
					taskSetRtuReset();
				}
			}
*/			
		}
	);
}
function check() {
	
	if (!isDbl("payAdd1", fjz+"1", false)) {
		return false;
	}
	if (!isDbl("payAdd2", fjz+"2", false)) {
		return false;
	}
	if (!isDbl("payAdd3", fjz+"3", false)) {
		return false;
	}
	if (!isNumber("hfhShutdown", gfhddz, false)) {
		return false;
	}
	
	if (!isNumber("protSt", zdbdks,0,25, false)) { // id, describe, min, max, required
		return false;
	}
	if (!isNumber("protEd", zdbdjs,0,25, false)) {
		return false;
	}
	if (!isNumber("hfhTime", gfhsj,0,32767, false)) {
		return false;
	}
	
	if (!isNumber("plusTime", fhmcbh,0,32767, false)) {
		return false;
	}
	
	return true;
}