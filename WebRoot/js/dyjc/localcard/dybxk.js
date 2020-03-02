var rtnValue 	= null;
var jsdata 		= null;
var CARD_NO 	= null;	//电卡编号
var METER_ID 	= null;

var CARD_TYPE 	= "03";	//补卡
var GDZ			= 0;
var LJGDZ		= 0;
var WRITECARD_TYPE = window.top.SDDef.YFF_CARDMTYPE_KE001;

$(document).ready(function(){
	
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("150,80,80,80,80,80,80,80,80,120,80,80,*")
	mygrid.setColAlign("center,center,left,right,right,right,right,right,right,right,left,left")
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.init();
	mygrid.setSkin("light");
	
	$("#btnSearch").click(function(){selcons()});
	$("#cardinfo").click(function(){cardinfo()});
	
	$("#metinfo").click(function(){meterinfo()});
	$("#repair").click(function(){if(check())wrtCard_repair();});
	$("#prt").click(function(){printPayRec()});
	
});
function selcons(){//检索
	
	var rtnValue1 = doSearch("ks",ComntUseropDef.YFF_DYOPER_REPAIR,rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
	
	//20131021添加，当查询结果中的预付费表类型 为新增表时将WRITECARD_TYPE赋为6
	var yffmeter_type = rtnValue1.yffmeter_type;
	WRITECARD_TYPE = getCardByMeter(yffmeter_type);
	
	$("#rtu_id").val(rtnValue.json.rtu_id);
	$("#mp_id").val(rtnValue.json.mp_id);
	getYffRecs();
	jfInfo();
	
	CARD_NO = rtnValue.rows.userno;
	METER_ID = rtnValue.rows.esamno;
	
	$("#userno").html(rtnValue.rows.userno);
	$("#username").html(rtnValue.rows.username);
	$("#tel").html(rtnValue.rows.tel);
	$("#useraddr").html(rtnValue.rows.useraddr);
	
	$("#esamno").html(rtnValue.rows.esamno);
	$("#factory").html(rtnValue.rows.factory);
	$("#ct").html(rtnValue.rows.ct);
	$("#wire_type").html(rtnValue.rows.jxfs);
	
	$("#yffmeter_type").html(rtnValue.json.yffmeter_type_desc);
	$("#cus_state").html(rtnValue.json.cus_state);
	$("#meter_type").html(rtnValue.json.yffmeter_type);
	//$("#tmp_prot_flag").html(rtnValue.json.tmp_prot_flag);
	$("#cacl_type").html(rtnValue.json.cacl_type);
	
	$("#feectrl_type").html(rtnValue.json.feectrl_type);
	$("#pay_type").val(rtnValue.json.pay_type);
	$("#pay_type_desc").html(rtnValue.json.pay_type_desc);
	$("#feeproj_id").val(rtnValue.json.feeproj_id);
	$("#feeproj_desc").html(rtnValue.json.feeproj_desc);
	
	$("#feeproj_detail").html(rtnValue.json.feeproj_detail);
	$("#yffalarm_id").val(rtnValue.json.yffalarm_id);
	$("#yffalarm_desc").html(rtnValue.json.yffalarm_desc);
	$("#yffalarm_detail").html(rtnValue.json.yffalarm_detail);
	$("#pay_type_desc").html(rtnValue.json.pay_type_desc);
	
	$("#repair").attr("disabled",false);
}

function jfInfo(){
	
	var repair_type = $("#repair_type").val();
	if(repair_type == 0){
		
		if(rtnValue != null){

			$("#buyp_times").html(rtnValue.json.buy_times);
			$("#last_jfje").html(rtnValue.json.pay_money);	
		}
	}else{
	
	}
}

function calWrtCard(){//写卡金额
	
	var zbje_val = 0.0, jsje_val = 0.0, jfje_val = 0.0, total_val = 0.0;
	zbje_val = $.trim($("#zbje_val").val())==="" ? 0 : $.trim($("#zbje_val").val());
	jsje_val = $.trim($("#jsje_val").val())==="" ? 0 : $.trim($("#jsje_val").val());
	jfje_val = $.trim($("#jfje_val").val())==="" ? 0 : $.trim($("#jfje_val").val());
	
	total_val  = round(parseFloat(zbje_val) + parseFloat(jsje_val),3);
	total_val  = round(total_val + parseFloat(jfje_val),3);
	GDZ = LJGDZ = total_val;
	
	if(!isNaN(total_val)){
		$("#zje").html(total_val);
	}
}

function check(){
	if($("#repair_type").val() != 0)	return true;
	
	if (!isZfDbl("zbje_val", "追补金额", false)) {
		return false;
	}
	if(!isZfDbl("jsje_val", "结算金额", false)) {
		return false;
	}
	if(!isDbl("jfje_val","缴费金额", 0, 1000000, false)) {
		return false;
	}

	return true;
}

function wrtCard_repair(){
	
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert(sel_user_info);
		return;
	}
	
	var zb_money 		= rtnValue.json.zb_money;
	var js_money		= rtnValue.json.othjs_money;
	var pay_money 		= rtnValue.json.pay_money;
	var all_money 		= rtnValue.json.all_money;

	var con_m = confirm("确认补卡吗?");
	if( con_m== false ||  con_m== undefined)	return;
	
	var retStr = null, params = null, CARD_TYPE = "03";
	var yffalarm_id = rtnValue.json.yffalarm_id,	feeproj_id = rtnValue.json.feeproj_id;
	var buy_tms = rtnValue.json.buy_times;
	GDZ = LJGDZ = rtnValue.json.all_money;

	params = "{rtu_id:\""  		+ rtu_id 	  + "\",mp_id:\"" 		+ mp_id 	+ 
			"\",cardno:\"" 		+ CARD_NO 	  + "\",meterno:\"" 	+ METER_ID 	+
			"\",card_type:\"" 	+ CARD_TYPE   + "\",buyp_val:\"" 	+ GDZ 		+
			"\",ljbuyp_val:\"" 	+ LJGDZ 	  + "\",buy_times:\"" 	+ buy_tms 	+
			"\",alarm_type:\"" 	+ yffalarm_id + "\",feeproj_id:\"" 	+ feeproj_id+ "\"}";
	
	loading.loading();
	$.post( def.basePath + "ajaxdyjc/actCardWrtInfo!getcardWrtInfo.action", 		//后台处理程序
		{
			result	: params
		},
		function(data) {			    	//回传函数
				
			retStr 		 = data.result.toString();
			WRITE_INFO 	 = retStr;
//			METER_MODEL  = data.metertype.toString();
			METER_MODEL  = "1";
			var ret_flag = window.top.writecard_bk(WRITE_INFO, CARD_NO, METER_MODEL, "KL");
			if(ret_flag  == true){
				
				wrtDB_repair();
			}
			loading.loaded();
		}
	);
}

function wrtDB_repair(){		//补写卡
	loading.loading();
	var params = rtnValue.json.op_date+","+rtnValue.json.op_time;
	var json_str = params;
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag 	: true,
			userData1		: $("#rtu_id").val(),
			mpid 			: $("#mp_id").val(),
			gsmflag 		: 0,
			dyOperRewrite	: json_str,
			userData2 		: ComntUseropDef.YFF_DYOPER_REWRITE
		},
		function(data) {			    	//回传函数
			
			if (data.result == "success") {
				
				$("#prt").attr("disabled",false);
				alert("补卡成功!");
				refreshdata();
			}
			else {
				
				alert("向主站发送补卡命令失败!" + (data.operErr ? data.operErr : ''));
			}
			loading.loaded();
		}
	);
}

function refreshdata(){
	
	getYffRecs();
	var new_buy_times 	= Number($("#buyp_times").html()) + 1;
	var new_last_jfje	= $("#zje").html()===""?0:$("#zje").html();
	
	$("#buyp_times").html(new_buy_times);
	$("#last_jfje").html(new_last_jfje);
}

function cardinfo(){	//卡内信息
	modalDialog.width = 500;
	modalDialog.height = 600;
	modalDialog.url = "../../dialog/cardinfo.jsp";
	modalDialog.show();
}

function metinfo(){//表内信息
	modalDialog.width = 800;
	modalDialog.height = 600;
	modalDialog.url = def.basePath + "jsp/dyjc/dialog/meterInfo.jsp";
	modalDialog.param = {"rtuId":$("#rtu_id").val() ,"mpId":$("#mp_id").val()};
	modalDialog.show();
}

function printPayRec(){//打印
	alert("in printPayRec()");
}