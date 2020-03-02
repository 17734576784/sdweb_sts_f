var rtnValue;
var meterType = 0;
$(document).ready(function(){
	$("#btnSearch").click(function(){
		selcons()
	});

	$("#btnCall").click(function(){
		if(meterType == 0)	callPara();
		else				callPara2();
	});
	
	//下装密钥0
	$("#btnSet").click(function(){
		if(meterType == 0)	setPara(0);
		else				setPara2(0);	
	});
	
	$("#btnClear" ).click(function(){ 
		setPara(1);
	});
	
	$("#btnSaveKey").click(function(){ 
		Update_keyVerSion();
	});
	
	//恢复密钥1--只有新规约中有
	$("#btnReverKey").click(function(){
		setPara2(1);
	});
	
	//钱包初始化
	$("#btnInit").click(function(){
		init();
	})

	setDisabled(true);
});

function selcons(){//检索
	var tmp = doSearch("",-1,rtnValue);
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	
	//取出表类型，新增规约表页面有所不同
	var yffmeter_type = rtnValue.yffmeter_type;
	//新规约表
	if(is2013Meter(yffmeter_type)){
		$("#type1").css("display","none");
		$("#type2").css("display","block");
		//新表密钥清空及密钥下装(密钥状态为0),故隐藏清空按钮
		$("#btnClear").hide();
		//显示恢复密钥按钮
		$("#btnReverKey").show();
		$("#btnInit").show();
		$("#key_version2").val(rtnValue.key_version);
		meterType = 1;
	}
	//旧规约表
	else{
		$("#type1").css("display","block");
		$("#type2").css("display","none");
		$("#btnClear").show();
		$("#btnReverKey").hide();
		$("#btnInit").hide();
		$("#key_version").val(rtnValue.key_version);
		meterType = 0;
	}
	
	setConsValue(rtnValue);
	$("#pt_ratio").html(rtnValue.pt);
	$("#ct_ratio").html(rtnValue.ct);

	setDisabled(false);
}

function setDisabled(mode)
{
	$("#btnCall").attr("disabled",mode);
	$("#btnSet").attr("disabled",mode);
	$("#btnClear").attr("disabled",mode);
	$("#btnSaveKey").attr("disabled",mode);
	$("#btnReverKey").attr("disabled",mode);
	$("#btnInit").attr("disabled",mode);
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
	
	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommDy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_DYCOMM_CHGKEY;
	//end
	
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag : true,
			userData1	  : rtu_id,
			mpid 		  : mp_id,
			dyCommChgKey  : json_str,
			userData2     : ComntUseropDef.YFF_DYCOMM_CHGKEY
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

//新版本下装密钥
function setPara2(flag)	{	//keyflag:0-下装密钥;1-恢复密钥
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	
	var params = {};
	params.rtu_id  = rtu_id;
	params.mp_id   = mp_id;
	params.keysum  = 20;					//密钥总条数固定为20
	params.key_id  = $('#keynum').val();	//密钥编号
	params.keynum  = 4;						//密钥条数固定为4
	params.keyflag = flag;					//密钥状态

	var json_str = jsonString.json2String(params);
	loading.loading();
	
	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommDy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_DYCOMM_CHGKEY2;
	//end
	
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag : true,
			userData1	  : rtu_id,
			mpid 		  : mp_id,
			dyCommChgKey  : json_str,
			userData2     : ComntUseropDef.YFF_DYCOMM_CHGKEY2
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);

			if (data.result == "success") {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					if(flag == 0)	alert("下装密钥成功!");
					else			alert("恢复密钥成功!");
				}else{
					if(flag == 0)	window.top.addStringTaskOpDetail("下装密钥成功!");
					else			window.top.addStringTaskOpDetail("恢复密钥成功!");
				}
			}
			else {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					if(flag == 0)	alert("下装密钥失败!");
					else			alert("恢复密钥失败!");
				}else{
					if(flag == 0)	window.top.addStringTaskOpDetail("下装密钥失败!");
					else			window.top.addStringTaskOpDetail("恢复密钥失败!");
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
	
	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommDy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_DYCOMM_CALLPARA;
	//end
	
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag  : true,
			userData1	   : rtu_id,
			mpid 		   : mp_id,
			//添加判断标识
			paraType	  	: meterType,
			dyCommCallPara : json_str,
			userData2 	   : ComntUseropDef.YFF_DYCOMM_CALLPARA
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

//新版本查看密钥
function callPara2(){
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	
	var params = {};
	params.paratype = ComntProtMsg.YFF_DY_CALL_STATE;
	var json_str = jsonString.json2String(params);
	loading.loading();
	
	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommDy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_DYCOMM_CALLPARA;
	//end
	
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag 	: true,
			userData1	  	: rtu_id,
			mpid 		  	: mp_id,
			//添加判断标识
			paraType	  	: meterType,
			dyCommCallPara 	: json_str,
			userData2 		: ComntUseropDef.YFF_DYCOMM_CALLPARA
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);	
			if (data.result == "success") {
				var json = eval("(" + data.dyCommCallPara + ")");
				if(json.key_state == 0){
					$("#key_version2").val(0);
				}
				else{
					$("#key_version2").val(1);
				}
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
    
	//20131021添加
	//旧规约发送密钥版本，新规约发送密钥状态
	if(meterType == 0){	
    	params.key_version = $("#key_version").val();
	}
	else{
		params.key_version = $("#key_version2").val();
	}

	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
		
	var json_str = jsonString.json2String(params);

  	//强制更新-开始
	loading.loading();
	$.post(def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//执行强制更新
		{
			firstLastFlag 		: 	true,
			userData1			: 	rtu_id,
			mpid 				: 	mp_id,
			gsmflag 			: 	0,
			userData2			: 	ComntUseropDef.YFF_DYOPER_UDPATESTATE,
			dyOperUpdateState	:	json_str						
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				//如果是旧版本0，向密钥版本输入框中填写信息
				if(meterType == 0){
					$("#key_version").val(params.key_version);
				}
				//如果是新版本1,向密钥版本输入框中填写信息
				else{
					$("#key_version2").val(params.key_version);
				}
				window.top.global_ggtz(window.top.dbUpdate.YD_DBUPDATE_TABLE_MPPAY);
				alert("保存密钥成功...");
			}
			else {
	  			alert("保存密钥失败...");
			}
		}
	);
	//强制更新-结束	
}

//钱包初始化
function init(){
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	
	var params = {};
	params.buynum 		= 0;
	params.pay_money 	= 0;
	params.othjs_money	= 0;
	params.zb_money		= 0;
	params.all_money	= 0;
	loading.loading();
	var json_str = jsonString.json2String(params);
	
	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommDy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_DYCOMM_ININT;
	//end
	
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
			{
				firstLastFlag 	: true,
				userData1	  	: rtu_id,
				mpid 		  	: mp_id,
				dyCommInit 		: json_str,
				userData2 		: ComntUseropDef.YFF_DYCOMM_ININT
			},
			function(data){
				loading.loaded();
				window.top.addJsonOpDetail(data.detailInfo);
				if(data.result == "success"){
					if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
						alert("钱包初始化成功!");
					}else{
						window.top.addStringTaskOpDetail("钱包初始化成功!");
					}
				}
				else{
					if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
						alert("钱包初始化失败!");
					}else{
						window.top.addStringTaskOpDetail("钱包初始化失败!");
					}
				}
			}
		);
}


