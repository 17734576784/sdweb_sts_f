var rtnValue;
$(document).ready(function(){
	$("#btnSearch").click(function(){ selcons()});

	$("#btnCall"  ).click(function(){ callPara()});
	$("#btnSet"	  ).click(function(){ setPara(0)});
	$("#btnClear" ).click(function(){ setPara(1)});
	$("#btnSaveKey").click(function(){ Update_keyVerSion()});

	setDisabled(true);
});

function selcons(){//检索
	//20121203 zkz
//	var tmp = doSearch("ks",-1,rtnValue);
	var tmp = doSearch("",-1,rtnValue);
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	//setDisabled(false);
	
//	if(rtnValue.feeType != 3){//阶梯费率 则 旧表基础电量可用
//	   $("#jt_total_zbdl").attr("disabled",true).css("background","#ccc");
//	}else{
//	   $("#jt_total_zbdl").attr("disabled",false).css("background","#fff");
//	}
	
	setConsValue(rtnValue);
	$("#pt_ratio").html(rtnValue.pt);
	$("#ct_ratio").html(rtnValue.ct);
	$("#key_version").val(rtnValue.key_version);
	
	setDisabled(false);
/*
	$("#userno").html(rtnValue.rows.userno);
	$("#username").html(rtnValue.rows.username);
	$("#tel").html(rtnValue.rows.tel);
	$("#useraddr").html(rtnValue.rows.useraddr);
	$("#esamno").html(rtnValue.rows.esamno);
	$("#pt_ratio").html(rtnValue.rows.pt_r);
	$("#ct_ratio").html(rtnValue.rows.ct_r);
	$("#cacl_type").html(rtnValue.json.cacl_type);
	$("#feectrl_type").html(rtnValue.json.feectrl_type);
	$("#pay_type_desc").html(rtnValue.json.pay_type_desc);
	$("#key_version").val(rtnValue.json.key_version);
	
	$("#rtu_id").val(rtnValue.json.rtu_id);
	$("#mp_id").val(rtnValue.json.mp_id);
	
	$("#feeproj_id").val(rtnValue.json.feeproj_id);
	$("#feeproj_desc").html(rtnValue.json.feeproj_desc);
	$("#feeproj_detail").html(rtnValue.json.feeproj_detail);
	
	$("#yffalarm_id").val(rtnValue.json.yffalarm_id);
	$("#yffalarm_desc").html(rtnValue.json.yffalarm_desc);
	$("#yffalarm_detail").html(rtnValue.json.yffalarm_detail);
*/
}

function setDisabled(mode)
{
	$("#btnCall").attr("disabled",mode);
	$("#btnSet").attr("disabled",mode);
	$("#btnClear").attr("disabled",mode);
	$("#btnSaveKey").attr("disabled",mode);
}

function setPara(flag)	{	//initflag:0-下装密钥;1-清空密钥
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	
	var params = {};
	params.rtu_id = rtu_id;
	params.mp_id = mp_id;
	params.keytype = $("#keyType").val();
	if($("#chkcsh").attr("checked")){
		params.initflag = 1;	
	}else{
		params.initflag = 0;
	}	
	params.keyver = $("#key_version").val();
	params.clearkey = flag;
	if (params.clearkey) {
		params.keytype = ComntKeyType.YFF_DY_KEY_REMOTE;
	}

	var json_str = jsonString.json2String(params);
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag : true,
			userData1: rtu_id,
			mpid : mp_id,
			gsmflag : 0,
			dyCommChgKey : json_str,
			userData2 : ComntUseropDef.YFF_DYCOMM_CHGKEY
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);

			if (data.result == "success") {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert((flag==0? "下装":"清空") + "密钥成功!");
				}else{
					window.top.addStringTaskOpDetail((flag==0? "下装":"清空") + "密钥成功!");
				}

				if (flag==1) {
					$("#key_version").val(1);
				}
				else {
					$("#key_version").val(parseInt($("#key_version").val())+ 1);
				}
			}
			else {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert((flag==0? "下装":"清空") + "密钥失败!");
				}else{
					window.top.addStringTaskOpDetail((flag==0? "下装":"清空") + "密钥失败!");
				}
			}
		}
	);
	
}

function callPara(){//查看密钥
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	
	var params = {};
	params.paratype = ComntProtMsg.YFF_DY_CALL_STATE;
	var json_str = jsonString.json2String(params);
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag : true,
			userData1: rtu_id,
			mpid : mp_id,
			gsmflag : 0,
			dyCommCallPara : json_str,
			userData2 : ComntUseropDef.YFF_DYCOMM_CALLPARA
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);	
			if (data.result == "success") {
				var json = eval("(" + data.dyCommCallPara + ")");
				$("#key_version").val(json.key_ver);				
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("查看密钥成功!");
				}else{
					window.top.addStringTaskOpDetail("查看密钥成功!");
				}
			}
			else {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("查看密钥失败!");
				}else{
					window.top.addStringTaskOpDetail("查看密钥失败!");
				}
			}
		}
	);
}

function Update_keyVerSion(){
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	
	var params 		= {};
	params.rtuId 	= rtu_id;
	params.mpId  	= mp_id;
	params.updType  = SDDef.YFF_DY_UPDATE_KEYVER;
    	
	params.key_version = $("#key_version").val();

	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
		
	var json_str = jsonString.json2String(params);

  	//强制更新-开始
	loading.loading();
	$.post(def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//执行强制更新
		{
			firstLastFlag : true,
			userData1: rtu_id,
			mpid : mp_id,
			gsmflag : 0,
			userData2: ComntUseropDef.YFF_DYOPER_UDPATESTATE,
			dyOperUpdateState:json_str						
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				$("#key_version").val(params.key_version);
				alert("保存密钥成功...");
			}
			else {
	  			alert("保存密钥失败...");
			}
		}
	);
	//强制更新-结束	
}