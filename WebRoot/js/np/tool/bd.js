var rtnValue;
$(document).ready(function(){
	$("#feeType").html("");
	$("#btnSearch").click(function(){selcons()});
	$("#btnCZ").click(function(){doCtrl()});//操作
	
	$("#btnSearch").focus();
	$("#op_type").change(function(){
		if(rtnValue.yffmeter_type == SDDef.YFF_METERTYPE_KEZG09 && ($("#op_type").val()== ComntProtMsg.YFF_DY_CTRL_CUT)){//科林自管只能表 = 1 && 控制为跳闸选项的时候 此checkbox可用
			$("#chkzdhz").attr("disabled",false);
	    }else{
	    	$("#chkzdhz").attr("disabled",true);
	    }
	})
	$("#op_type").attr("disabled",true);
});

function selcons(){//检索
//	var rtnValue1 = doSearch("yc",ComntUseropDef.YFF_DYCOMM_CTRL,rtnValue);
	var rtnValue1 = doSearch("",ComntUseropDef.YFF_DYCOMM_CTRL,rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
	setConsValue(rtnValue);

	if(rtnValue.yffmeter_type == SDDef.YFF_METERTYPE_KEZG09 && ($("#op_type").val()== ComntProtMsg.YFF_DY_CTRL_CUT)){//科林自管只能表 = 1 && 控制为跳闸选项的时候 此checkbox可用
		$("#chkzdhz").attr("disabled",false);
	}
	$("#key_version").html(rtnValue.key_version);
	$("#btnCZ").attr("disabled",false);
	
    $("#op_type").attr("disabled",false);

    //显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
}

function type_change(){
	if(rtnValue.yffmeter_type == SDDef.YFF_METERTYPE_KEZG09 && ($("#op_type").val()== ComntProtMsg.YFF_DY_CTRL_CUT)){//科林自管只能表 = 1 && 控制为跳闸选项的时候 此checkbox可用
		$("#chkzdhz").attr("disabled",false);
    }
}
function doCtrl(){//操作
	var value;
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}

	var params = {};
	params.rtu_id = rtu_id;
	params.mp_id = mp_id;
	params.paratype = $("#op_type").val();
	value = params.paratype; 
	
	params.ctrlver =  $("#chkzdhz").attr("checked")?1:0;
	switch(value*1){
		case ComntProtMsg.YFF_DY_CTRL_CUT://跳闸
			if(!confirm("确定要跳闸吗？"))return;
			break;
		case ComntProtMsg.YFF_DY_CTRL_ON ://合闸
			if(!confirm("确定要合闸吗？"))return;
			break;
		case ComntProtMsg.YFF_DY_CTRL_KEEPON://保电
			if(!confirm("确定要保电吗？"))return;	
			break;
		case ComntProtMsg.YFF_DY_CTRL_KEEPOFF://取消保电
			if(!confirm("确定要取消保电吗？"))return;
			break;
		case ComntProtMsg.YFF_DY_CTRL_ALARMON://报警
			if(!confirm("确定要报警吗？"))return;
		    break;
	    case ComntProtMsg.YFF_DY_CTRL_ALARMOFF://解除报警
		    if(!confirm("确定解除报警吗？"))return;
		    break;
	}
	var json_str = jsonString.json2String(params);
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag : true,
			userData1     : rtu_id,
			mpid          : mp_id,
			gsmflag    	  : 0,
			dyCommCtrl    : json_str,
			userData2     : ComntUseropDef.YFF_DYCOMM_CTRL
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);	
			if (data.result == "success") {
				window.top.addStringTaskOpDetail("接收预付费服务任务成功...");
				switch(value*1){
					case ComntProtMsg.YFF_DY_CTRL_CUT://跳闸
						addBD(value);
						break;
					case ComntProtMsg.YFF_DY_CTRL_ON ://合闸
						addBD(value);
						break;
					case ComntProtMsg.YFF_DY_CTRL_KEEPON://保电
						addBD(value);	
						break;
					case ComntProtMsg.YFF_DY_CTRL_KEEPOFF://取消保电
						addBD(value);
						break;
					case ComntProtMsg.YFF_DY_CTRL_ALARMON://报警
					    alert("操作成功")
				    	break;
				    case ComntProtMsg.YFF_DY_CTRL_ALARMOFF://解除报警
				        alert("操作成功")
				    	break;
				}
			}
			else {
				alert("操作失败");
			}
	});
}

//往历史数据库里写数据
function addBD(value){		//添加数据库
	var params = {};
	params.rtu_id	= $("#rtu_id").val();
	params.mp_id	= $("#mp_id").val();
	params.op_type	= value;
	var json_str 	= jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!dbCtrlOper.action", 		//后台处理程序
		{
			result : json_str
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				if(value == 3 || value ==4){
					protect(value);
				}else{
					alert("操作成功!")
				}
			}
			else {
				alert("操作失败!");
			}
		}
	);
}
function protect(value) {//保电-DB
    var params = {};
	params.rtu_id	= $("#rtu_id").val();
	params.mp_id	= $("#mp_id").val();
	params.op_type	= value;
	if(value == 3){//如果是保电           则保电时间设置成 1~1
		params.m_prot_st = 1;
		params.m_prot_ed = 1;
	}
	if(value == 4){//如果是解除保电       则时间设置成0~0
		params.m_prot_st = 0;
		params.m_prot_ed = 0;
	}
	if(value !=3 || value != 4){
		return;
	}
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action",//后台处理程序
		{
			firstLastFlag 	: true,
			userData1		: params.rtu_id,
			mpid 			: params.mp_id,
			gsmflag 		: 0,			//gsmFlag
			dyOperProtect 	: json_str,
			userData2 		: ComntUseropDef.YFF_DYOPER_PROTECT
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				alert("操作成功!");
			}
			else {
				alert("操作失败!");
			}
		}
	);	
}