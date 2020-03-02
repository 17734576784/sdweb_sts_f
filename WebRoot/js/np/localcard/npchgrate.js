var rtnValue;
var jsonFLFA;
var plsz = -1;//批量设置
var resultInfo = "设置";//批量设置返回的提示信息
var WRITECARD_TYPE = 1;
$(document).ready(function(){
	$.post(def.basePath + "ajaxdyjc/actChgRate!getRateList.action",{},function(data){
		if(data.result != ""){
			jsonFLFA = eval('(' + data.result + ')');
			for(var i=0;i<jsonFLFA.rows.length;i++){
				if(jsonFLFA.rows[i].feeType == 0||jsonFLFA.rows[i].feeType == 1){
					$("#xflfa").append("<option value="+jsonFLFA.rows[i].value+">"+jsonFLFA.rows[i].text+"</option>");
				}
			} 
			changeFLFA();
		}
	});
	
	dateFormat.date = dateFormat.getToday("yMd");
	var datestr 	= dateFormat.formatToYMD(0);
	dateFormat.date = dateFormat.getToday("H")+"00";
	datestr 		= datestr + " " + dateFormat.formatToHM(0);
	$("#qhsj").val(datestr);
	$("#xqhsj").val(datestr);
	
	$("#feeType").html("");

	$("#btnRead").click(function(){read_card()});
	$("#cardinfo").click(function(){window.top.card_comm.cardinfo()});
	
	$("#btnSearch").click(function(){selcons()});
	$("#xflfa").change(function(){changeFLFA()});//新费率方案 下拉框	
	$("#btnReadFee").click(function(){readFee()});//读取费率
	$("#btnSetFee").click(function(){setFee()});//设置费率
	$("#btnReadSwitchTime").click(function(){readSwitchTime()});//读切换时间
	$("#btnSetSwitchTime").click(function(){setSwitchTime()});//设切换时间
	$("#btnChangeFL").click(function(){changeFL()});//保存新费率
	$("#btnSearch").focus();
	setDisabled(true);
});

function read_card(){		//读卡
	loading.tip = "正在读卡,请稍后...";
	loading.loading();
	setTimeout("window.top.card_comm.readCardSearchDy(" + ComntUseropDef.YFF_DYOPER_CHANGERATE + ")", 10);
}

function setSearchJson2Html(js_tmp) {
	loading.loaded();
	if(!js_tmp)return;
	if(!window.top.card_comm.checkInfo(js_tmp)) return;
	rtnValue = js_tmp;
	setcons1();
}

function setcons1() {
	setConsValue(rtnValue);
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno};
	mis_query(param);
	setDisabled(false);
	resultInfo = "设置";
}

function selcons(){//检索
	var tmp = doSearch("ks",ComntUseropDef.YFF_DYOPER_CHANGERATE,rtnValue);
	if(!tmp){
		return;
	}
	rtnValue = tmp;

	setConsValue(rtnValue);
	//mis中函数
	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno};
	mis_query(param);
	setDisabled(false);
	resultInfo = "设置";
}

function changeFL(){//保存新费率
	
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	
	var params = {};
	params.rtu_id = rtu_id;
	params.mp_id0 = mp_id;
	params.mp_id1 = rtnValue.power_rela1
	params.mp_id2 = rtnValue.power_rela2

	params.chgid 	= $("#xflfa").val();
	var qhsj 		= $("#qhsj").val();
	params.chg_date = qhsj.substring(0,4) + qhsj.substring(5,7) + qhsj.substring(8,10);
	params.chg_time = qhsj.substring(12,14) + qhsj.substring(15,17)

	params.date		= params.chg_date;
	params.time 	= params.chg_time;
	
	params.bd_zy0   = 0;
	params.bd_zyj0  = 0;
	params.bd_zyf0  = 0;
	params.bd_zyp0  = 0;
	params.bd_zyg0  = 0;
	
	params.bd_zy1   = 0;
	params.bd_zyj1  = 0;
	params.bd_zyf1  = 0;
	params.bd_zyp1  = 0;
	params.bd_zyg1  = 0;
	
	params.bd_zy2   = 0;
	params.bd_zyj2  = 0;
	params.bd_zyf2  = 0;
	params.bd_zyp2  = 0;
	params.bd_zyg2  = 0;
	
	modalDialog.height = 250;
	modalDialog.width  = 300;
	modalDialog.url    = def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param  = { 
		showGSM : false,
		key		: ["客户编号","用户名称","所属供电所", "更换新费率"], 
		val		: [rtnValue.userno,rtnValue.username,rtnValue.orgname,$("#xflfa").find("option:selected").text()]
	};
	
	var o = modalDialog.show();
	if(!o.flag){
		return;
	}
	var feeproj_id  = $("#feeproj_id").val();
	if(params.chgid == feeproj_id){
		alert("费率方案相同，不需保存！");
		return;
	}
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			userData1		 : rtu_id,
			mpid 			 : mp_id,
			gsmflag 		 : 0,
			dyOperChangeRate : json_str,
			userData2 		 : ComntUseropDef.YFF_DYOPER_CHANGERATE
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				var colors = "<b style='color:#800000'>";
				var colore = "</b>";
				$("#feeproj_id").val(params.chgid);
				var text=$("#xflfa").find("option:selected").text();
				$("#feeproj_desc").html(colors+text+colore);
				$("#feeproj_detail").html(colors+$("#feeproj_detail_new").html()+colore);
				alert("保存新费率成功!");
			}
			else {
				alert("保存新费率失败!" + (data.operErr ? data.operErr : ''));
			}
		}
	);
}

function readFee(){//读取费率
	var rtu_id = $("#rtu_id").val();
	var mp_id  = $("#mp_id").val();
	
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}	
	
	var params = {};
	if($("#fei1").attr("checked")){
		params.paratype = ComntProtMsg.YFF_DY_CALL_FEI1;//读取费率-一套费率
	}
	else if($("#fei2").attr("checked")){
		params.paratype = ComntProtMsg.YFF_DY_CALL_FEI2;//读取费率-二套费率	
	}
	else{
		alert("请选择第一套或者第二套费率!");
		return ;
	}
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag  : true,
			userData1	   : rtu_id,
			mpid 		   : mp_id,
			gsmflag        : 0,
			dyCommCallPara : json_str,
			userData2      : ComntUseropDef.YFF_DYCOMM_CALLPARA
		},
		function(data) {//回传函数			
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				var json = eval("(" + data.dyCommCallPara + ")");
				$("#fee_desc").html(json.rate_desc);
				$("#fee_no").val(json.feeno);
				$("#btnSetFee").attr("disabled",false);
				
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("读取费率成功!");	//这个改到info中显示。
				}
				else{
					window.top.addStringTaskOpDetail("读取费率成功!");
				}
			}
			else {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("读取费率失败!");
				}
				else{
					window.top.addStringTaskOpDetail("读取费率失败!");
				}
			}
		}
	);
	
}

function setFee(){
	resultInfo = "设置";//设置费率前先清空以下提示信息
	var feeType=parseInt($("#feeType").val());
	switch(feeType) {
		case ComntProtMsg.YFF_DY_FEE_Z : 
			if(!confirm("确定要设置费率吗？")){return;} 
			doSetFee(ComntProtMsg.YFF_DY_FEE_Z,false);
			break;
		case ComntProtMsg.YFF_DY_FEE_J : 
			if(!confirm("确定要设置尖费率吗？")){return;} 
			doSetFee(ComntProtMsg.YFF_DY_FEE_J,false);
			break;
		case ComntProtMsg.YFF_DY_FEE_F : 
			if(!confirm("确定要设置峰费率吗？")){return;} 
			doSetFee(ComntProtMsg.YFF_DY_FEE_F,false);
			break;
		case ComntProtMsg.YFF_DY_FEE_P : 
			if(!confirm("确定要设置平费率吗？")){return;} 
			doSetFee(ComntProtMsg.YFF_DY_FEE_P,false);
			break;
		case ComntProtMsg.YFF_DY_FEE_G : 
			if(!confirm("确定要设置谷费率吗？")){return;} 
			 doSetFee(ComntProtMsg.YFF_DY_FEE_G,false);
			 break;
		case plsz : 
			if(!confirm("确定要批量设置费率吗？")){return;}
			doSetFee(ComntProtMsg.YFF_DY_FEE_J,true);
			break;
	}
}

function doSetFee(feeType, flag){//设置费率,feeType为费率类型，flag为批量设置标志
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	var params = {};
	params.paratype = feeType;
	//params.paratype = $("#feeType").val();
	params.rtu_id   = rtu_id;
	params.mp_id    = mp_id;
	params.feeno    = $("#fei1").attr("checked") ? 1 : 2;//第一套1/第二套2
	params.rated_z  = $("#fee_z").val() == "" ? 0 : $("#fee_z").val();
	params.ratef_j  = $("#fee_j").val()	== "" ? 0 : $("#fee_j").val();
	params.ratef_f  = $("#fee_f").val() == "" ? 0 : $("#fee_f").val();
	params.ratef_p  = $("#fee_p").val() == "" ? 0 : $("#fee_p").val();
	params.ratef_g  = $("#fee_g").val() == "" ? 0 : $("#fee_g").val();
	
	var ghsj 		= $("#qhsj").val();
	params.chg_date = ghsj.substring(2,4) + ghsj.substring(5,7) + ghsj.substring(8,10);
	params.chg_time = ghsj.substring(12,14) + ghsj.substring(15,17)+"00"
	
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	var json_str = jsonString.json2String(params);
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag : true,
			userData1	  : rtu_id,
			mpid          : mp_id,
			gsmflag       : 0,
			dyCommSetPara : json_str,
			userData2     : ComntUseropDef.YFF_DYCOMM_SETPARA
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {	//设置费率成功
				//如果详细信息小窗口是关闭状态，则弹出提示框进行提示
				if (!flag && (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed)) {
					switch(feeType) {
					   case ComntProtMsg.YFF_DY_FEE_Z : 
							alert("设置总费率成功!");
							break;
					   case ComntProtMsg.YFF_DY_FEE_J : 
						    alert("设置尖费率成功!");
						    break;
					   case ComntProtMsg.YFF_DY_FEE_F : 
						    alert("设置峰费率成功!");
						    break;
					   case ComntProtMsg.YFF_DY_FEE_P : 
						    alert("设置平费率成功!");
						    break;
					   case ComntProtMsg.YFF_DY_FEE_G : 
						    alert("设置谷费率成功!");
						    break;
					}
				}
				else { //如果详细信息窗口是打开装态，则将提示信息填写到小窗口中
					switch(feeType) {
					   case ComntProtMsg.YFF_DY_FEE_Z : 
						   window.top.addStringTaskOpDetail("设置总费率成功!");
							break;
					   case ComntProtMsg.YFF_DY_FEE_J : 
						   window.top.addStringTaskOpDetail("设置尖费率成功!");
						    break;
					   case ComntProtMsg.YFF_DY_FEE_F : 
						   window.top.addStringTaskOpDetail("设置峰费率成功!");
						    break;
					   case ComntProtMsg.YFF_DY_FEE_P : 
						   window.top.addStringTaskOpDetail("设置平费率成功!");
						    break;
					   case ComntProtMsg.YFF_DY_FEE_G : 
						   window.top.addStringTaskOpDetail("设置谷费率成功!");
						    break;
					}
				}
				if(flag && feeType < ComntProtMsg.YFF_DY_FEE_G) {		//如果批量设置标志为true，则递归调用自己，传递下一种费率类型
					doSetFee(++feeType, true);
				}else {
					feeType = -1;  //-1在这里表示批量设置操作完成，但未必每个都成功。
				}		
			}	
			else {//设置费率失败
				if (!flag && (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed)) {
					switch(feeType) {
					   case ComntProtMsg.YFF_DY_FEE_Z : 
							alert("设置总费率失败!");
							break;
					   case ComntProtMsg.YFF_DY_FEE_J : 
						    alert("设置尖费率失败!");
						    break;
					   case ComntProtMsg.YFF_DY_FEE_F : 
						    alert("设置峰费率失败!");
						    break;
					   case ComntProtMsg.YFF_DY_FEE_P : 
						    alert("设置平费率失败!");
						    break;
					   case ComntProtMsg.YFF_DY_FEE_G : 
						    alert("设置谷费率失败!");
						    break;
					}
				}
				else{
					switch(feeType) {
					   case ComntProtMsg.YFF_DY_FEE_Z : 
						    window.top.addStringTaskOpDetail("设置总费率失败!");
							break;
					   case ComntProtMsg.YFF_DY_FEE_J : 
						    window.top.addStringTaskOpDetail("设置尖费率失败!");
						    resultInfo = resultInfo.concat("尖");
						    break;
					   case ComntProtMsg.YFF_DY_FEE_F : 
						    window.top.addStringTaskOpDetail("设置峰费率失败!");
						    resultInfo = (resultInfo == "设置") ? resultInfo.concat("峰") : resultInfo.concat("，峰");
						    break;
					   case ComntProtMsg.YFF_DY_FEE_P : 
						    window.top.addStringTaskOpDetail("设置平费率失败!");
						    resultInfo = (resultInfo == "设置") ? resultInfo.concat("平") : resultInfo.concat("，平");
						    break;
					   case ComntProtMsg.YFF_DY_FEE_G : 
						    window.top.addStringTaskOpDetail("设置谷费率失败!");
						    resultInfo = (resultInfo == "设置") ? resultInfo.concat("谷") : resultInfo.concat("，谷");
						    break;
					}
				}
				if(flag && feeType < ComntProtMsg.YFF_DY_FEE_G){	//如果批量设置标志为true，则递归调用自己，传递下一种费率类型
					doSetFee(++feeType,true);
				}else {
					feeType = -1;  //-1在这里表示批量设置操作完成，但未必每个都成功。
				}	
			}
			if(flag){
				if((resultInfo === "设置") && feeType == -1){
					alert("批量设置成功！");
				}else if((resultInfo != "设置") && feeType == -1){
					alert(resultInfo.concat("费率失败！"));
				}
			}
		}
	);
}


function readSwitchTime(){//读切换时间
	var rtu_id = $("#rtu_id").val();
	var mp_id  = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}	
	var params = {};
	params.paratype = ComntProtMsg.YFF_DY_CALL_PARA;//读切换时间	
	var json_str 	= jsonString.json2String(params);
	
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag  : true,
			userData1	   : rtu_id,
			mpid 		   : mp_id,
			gsmflag 	   : 0,
			dyCommCallPara : json_str,
			userData2      : ComntUseropDef.YFF_DYCOMM_CALLPARA
		},
		function(data) {//回传函数			
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				var json 		= eval("(" + data.dyCommCallPara + ")");
				var strDtTm 	="";
				dateFormat.date = "20" + json.chg_date;
				strDtTm 		= dateFormat.formatToYMD(0);
				dateFormat.date = json.chg_time.toString().substr(0,4);
				strDtTm 		= strDtTm + " " + dateFormat.formatToHM(0);
				
				$("#qhsj").val(strDtTm);
				$("#user_type").val(json.user_type);
				$("#bit_update").val(json.bit_update);
				$("#chg_date").val(json.chg_date);
				$("#chg_time").val(json.chg_time);
				$("#alarm_val1").val(json.alarm_val1);
				$("#alarm_val2").val(json.alarm_val2);
				$("#ct").val(json.ct);
				$("#pt").val(json.pt);
				$("#meterno").val(json.meterno);
				$("#userno").val(json.userno);
				
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("读切换时间成功!");
				}else{
					window.top.addStringTaskOpDetail("读切换时间成功!");
				}
			}
			else {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("读切换时间失败!");
				}else{
					window.top.addStringTaskOpDetail("读切换时间失败!");
				}
			}
		}
	);
}

function setSwitchTime(){//设切换时间
	var rtu_id = $("#rtu_id").val()
	var mp_id  = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}

	var params = {};
	params.rtu_id = rtu_id;
	params.mp_id  = mp_id;
	params.paratype   = ComntProtMsg.YFF_DY_SET_CHGTIME;
	params.user_type  = $("#user_type").val();
	params.bit_update = $("#bit_update").val();
	
	var qhsj = $("#xqhsj").val();
	params.chg_date   = qhsj.substring(2,4)   + qhsj.substring(5,7)   + qhsj.substring(8,10);
	params.chg_time   = qhsj.substring(12,14) + qhsj.substring(15,17) +"00"
	params.alarm_val1 = $("#alarm_val1").val();
	params.alarm_val2 = $("#alarm_val2").val();
	params.pt = $("#pt").val();
	params.ct = $("#pct").val();
	params.userno  = rtnValue.userno;
	params.meterno = rtnValue.esamno;
	
	var json_str   = jsonString.json2String(params);
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag : true,
			userData1	  : rtu_id,
			mpid 		  : mp_id,
			gsmflag 	  : 0,
			dyCommSetPara : json_str,
			userData2 	  : ComntUseropDef.YFF_DYCOMM_SETPARA
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("设切换时间成功!");
				}else{
					window.top.addStringTaskOpDetail("设切换时间成功!");
				}
			}
			else {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("设切换时间失败!");
				}else{
					window.top.addStringTaskOpDetail("设切换时间失败!");
				}
			}
		}
	);
	
}

function changeFLFA(){//费率方案 下拉框 onchange
	var val=$("#xflfa").val();
	var text=$("#xflfa").find("option:selected").text();
	
	if (text.indexOf("单") > -1) {//单费率
		//$("#feeType").attr("disabled",false);
		$("#feeType").html("");
		$("#feeType").append("<option value=" + ComntProtMsg.YFF_DY_FEE_Z + ">费率总</option>");
	}
	else if (text.indexOf("复") > -1) {//复费率
		//$("#feeType").attr("disabled",false);
		$("#feeType").html("");
		$("#feeType").append("<option value=" + ComntProtMsg.YFF_DY_FEE_J + ">费率尖</option>");
		$("#feeType").append("<option value=" + ComntProtMsg.YFF_DY_FEE_F + ">费率峰</option>");
		$("#feeType").append("<option value=" + ComntProtMsg.YFF_DY_FEE_P + ">费率平</option>");
		$("#feeType").append("<option value=" + ComntProtMsg.YFF_DY_FEE_G + ">费率谷</option>");
		$("#feeType").append("<option value=" + plsz+">批量设置</option>");
	}
	else{
		$("#feeType").html("");
		$("#feeType").attr("disabled",true);
		//$("#user_type").val("02");
	} 
	
	for (var i = 0; i < jsonFLFA.rows.length; i++) {
		if (jsonFLFA.rows[i].value == val) {
			$("#feeproj_detail_new").html(jsonFLFA.rows[i].desc);	
			$("#fee_z").val(jsonFLFA.rows[i].val_rateZ);
			$("#fee_j").val(jsonFLFA.rows[i].val_rateJ);
			$("#fee_f").val(jsonFLFA.rows[i].val_rateF);
			$("#fee_p").val(jsonFLFA.rows[i].val_rateP);
			$("#fee_g").val(jsonFLFA.rows[i].val_rateG);
			return;
		}		
	}
}

function setDisabled(mode){
	$("#qhsj" ).attr("disabled",mode);
	$("#xqhsj").attr("disabled",mode);
	$("#xflfa").attr("disabled",mode);
	$("#feeType").attr("disabled",mode);
	$("#btnReadFee").attr("disabled",mode);
	$("#btnSetFee" ).attr("disabled",mode);
	$("#btnReadSwitchTime").attr("disabled",mode);
	$("#btnSetSwitchTime" ).attr("disabled",mode);
	$("#btnChangeFL").attr("disabled",mode);
	$("#cardinfo").attr("disabled",mode);
	$("#fee_desc").html("");
}

