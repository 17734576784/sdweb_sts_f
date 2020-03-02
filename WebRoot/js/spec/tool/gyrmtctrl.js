var rtnValue;
var op_option = ["保电查看","保电设置","解除保电","跳闸","合闸允许","催费告警投入","催费告警解除"];
var op_value  = [0,			1,			2,		 3,		4,			5,			6];

var xd_option = ["永久限电","15分钟","30分钟","1小时","2小时","4小时","8小时","16小时"];
var xd_value = [0,15,30,60,120,240,480,960];

$(document).ready(function(){
	
	$("#btnSearch").click(function(){selcons()});
	$("#operation").click(function(){operation()});
	$("#oper_type").change(function(){chg_type(this.value)});
	
	$("#ycsj").keyup(function(){if(isNaN(this.value))this.value=0;});
	$("#bdks").keyup(function(){if(isNaN(this.value))this.value=0;});
	$("#bdjs").keyup(function(){if(isNaN(this.value))this.value=0;});
	
	$("#detail_info").click(function() {showOpDetailDialog()});
});

function initSelect(){
	var tmp = "";
	if(rtnValue.prot_type == ComntDef.YD_PROTOCAL_KEDYH){	//科林大用户规约
		for ( var i = 0; i < 5; i++) {
			tmp += ",<option value=" + op_value[i] + ">"+op_option[i]+"</option>";
		}
		$("#oper_type").html(tmp.substring(1));
	}else{
		for ( var i = 0; i < op_value.length; i++) {
			tmp += ",<option value=" + op_value[i] + ">"+op_option[i]+"</option>";
		}
		$("#oper_type").html(tmp.substring(1));
	}
	
	tmp = "";
	for ( var i = 0; i < xd_option.length; i++) {
		tmp += ",<option value=" + xd_value[i] + ">"+xd_option[i]+"</option>";
	}
	$("#xdsc").html(tmp.substring(1));
	
	tmp = '<option value="1">一轮</option><option value="2">二轮</option><option value="3">三轮</option><option value="4">四轮</option>';
	$("#kglc").html(tmp);
	
	chg_type($("#oper_type").val());
}

function chg_type(value){
	var disable_color = "#ccc";
	value = parseInt(value);
	
	switch (value) {
	case op_value[0]:
	case op_value[2]:
		$("#kglc").attr("disabled", true);
		$("#xdsc").attr("disabled", true);
		$("#bdks").attr("readonly", true).css("background", disable_color);
		$("#bdjs").attr("readonly", true).css("background", disable_color);
		$("#ycsj").attr("readonly", true).css("background", disable_color);
		break;
	case op_value[1]:
		$("#kglc").attr("disabled", true);
		$("#xdsc").attr("disabled", true);
		$("#bdks").attr("readonly", false).css("background", "white");
		$("#bdjs").attr("readonly", false).css("background", "white");
		$("#ycsj").attr("readonly", true).css("background", disable_color);		
		break;
	case op_value[3]:
		$("#kglc").attr("disabled", false);
		$("#xdsc").attr("disabled", false);
		$("#bdks").attr("readonly", true).css("background", disable_color);
		$("#bdjs").attr("readonly", true).css("background", disable_color);

		if(rtnValue.prot_type == ComntDef.YD_PROTOCAL_KEDYH) {
			$("#ycsj").attr("readonly", true).css("background", disable_color);	
		}
		else {
			$("#ycsj").attr("readonly", false).css("background", "white");
		}
		break;
	case op_value[4]:
		$("#kglc").attr("disabled", false);
		$("#xdsc").attr("disabled", true);
		$("#bdks").attr("readonly", true).css("background", disable_color);
		$("#bdjs").attr("readonly", true).css("background", disable_color);
		$("#ycsj").attr("readonly", true).css("background", disable_color);
		break;
	}
}

function selcons(){//检索
	var tmp = doSearch(sel_type,ComntUseropDef.YFF_GYCOMM_PAY, rtnValue);
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	
	setConsValue(rtnValue);
	
	$("#bdks").val(rtnValue.prot_st);
	$("#bdjs").val(rtnValue.prot_ed);
	$("#ycsj").val("");
	initSelect();

	if (rtnValue.prot_type == ComntDef.YD_PROTOCAL_KEDYH) {
		$("#ctrlpara_title").attr("style","display:block;");
		$("#ctrlpara_content").attr("style","display:block;");
		$("#ctrlpara_butt").attr("style","display:block;");
	}
	else {
		$("#ctrlpara_title").attr("style","display:none;");
		$("#ctrlpara_content").attr("style","display:none;");
		$("#ctrlpara_butt").attr("style","display:none;");
		
	}
	
	$("#operation").attr("disabled",false);
	
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
}

function operation(){
	
	var value = parseInt($("#oper_type").val());
	var params = {};
	
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	
	var u2 = ComntUseropDef.YFF_GYCOMM_CALLPARA;
	var in_param = "";
	var db_type = 0;
	switch (value) {
	case op_value[0]:		//保电查看
		
		$("#bdks").val("");
		$("#bdjs").val("");
		
		params.paratype = ComntProtMsg.YFF_GY_CALL_KEEP;
		u2 = ComntUseropDef.YFF_GYCOMM_CALLPARA;
		in_param = "gyCommCallPara";
		
		break;
	case op_value[1]:		//保电设置
		var bdks = $("#bdks").val();
		var bdjs = $("#bdjs").val();
		if(isNaN(bdks) || isNaN(bdjs) || bdks === "" || bdjs === ""){
			alert("请输入数字");
			return;
		}
		if(bdks < 0|| bdks >= 24 || bdjs < 0 || bdjs >= 24) {
			alert("保电时间错误");
			return;
		}
		params.bdbegin 		= bdks;
		params.bdend 		= bdjs;
		params.ctrlflag		= 0;
		params.paratype = ComntProtMsg.YFF_GY_SET_KEEP;
		u2 = ComntUseropDef.YFF_GYCOMM_SETPARA;
		in_param = "gyCommSetPara";
		
		db_type = ComntProtMsg.YFF_GY_CTRL_KEEPON;
		
		if(!confirm("确定要设置保电吗？"))return;
		
		break;
	case op_value[2]:		//保电解除
		params.bdbegin 		= 0;
		params.bdend 		= 0;
		params.ctrlflag		= 0;
		params.paratype = ComntProtMsg.YFF_GY_SET_KEEP;
		u2 = ComntUseropDef.YFF_GYCOMM_SETPARA;
		in_param = "gyCommSetPara";
		
		db_type = ComntProtMsg.YFF_GY_CTRL_KEEPOFF;
		
		if(!confirm("确定要解除保电吗？"))return;
		break;
	case op_value[3]:		//跳闸
		
		if(!confirm("确定要跳闸吗？"))return;
	
		params.ctrltype = 0;
		params.ctrlroll = $("#kglc").val();
		params.xdsc 	= $("#xdsc").val();
		if($("#mcfs").attr("checked")){
			
			var plus_width = $("#plus_width").val();
			if(plus_width === "" || isNaN(plus_width)){
				plus_width = 0;
			}
			
			params.plustime = plus_width;
			
		}else{
			params.plustime = 0;
		}
		if(rtnValue.prot_type == ComntDef.YD_PROTOCAL_KEDYH){
			params.delaytime = 0;	
		}
		else {
			if (isNaN($("#ycsj").val())) $("#ycsj").val(0); 
			params.delaytime = $("#ycsj").val();
		}
				
		params.paratype = ComntProtMsg.YFF_GY_CTRL_CUT;
		u2 = ComntUseropDef.YFF_GYCOMM_CTRL;
		in_param = "gyCommCtrl";
		
		db_type = ComntProtMsg.YFF_GY_CTRL_CUT;
		
		break;
		
	case op_value[4]:		//合闸允许
	
		if(!confirm("确定要合闸吗？"))return;
	
		params.ctrlroll = $("#kglc").val();
		params.paratype = ComntProtMsg.YFF_GY_CTRL_ON;
		u2 = ComntUseropDef.YFF_GYCOMM_CTRL;
		in_param = "gyCommCtrl";
		
		db_type = ComntProtMsg.YFF_GY_CTRL_ON;
		
		break;
	case op_value[5]:		//催费告警投入
		if(!confirm("确定要投入催费告警吗？"))return;
	
		params.paratype = ComntProtMsg.YFF_GY_CTRL_ALARMON;
		u2 = ComntUseropDef.YFF_GYCOMM_CTRL;
		in_param = "gyCommCtrl";
		
		db_type = ComntProtMsg.YFF_GY_CTRL_ALARMON;
		break;
	case op_value[6]:		//催费告警解除
		if(!confirm("确定要解除催费告警吗？"))return;
	
		params.paratype = ComntProtMsg.YFF_GY_CTRL_ALARMOFF;
		u2 = ComntUseropDef.YFF_GYCOMM_CTRL;
		in_param = "gyCommCtrl";
		
		db_type = ComntProtMsg.YFF_GY_CTRL_ALARMOFF;
		break;
	}
	
	var json_str = jsonString.json2String(params);
	
	var param = {
			firstLastFlag 	: "true",
			userData1		: params.rtu_id,
			zjgid 			: params.zjg_id,
			userData2 		: u2
		};
	eval("param."+in_param+"='"+json_str+"'");
	
	loading.loading();
	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommGy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = u2;
	//end
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommGy!taskProc.action", 		//后台处理程序
		param,
		function(data) {			    	//回传函数
			window.top.addJsonOpDetail(data.detailInfo);
			loading.loaded();
			if (data.result == "success") {
				window.top.addStringTaskOpDetail("接收预付费服务任务成功...");
				switch (value) {
				case op_value[0]:	//保电查看
					var json = eval("("+data.gyCommCallPara+")");
					$("#bdks").val(json.bdbegin);
					$("#bdjs").val(json.bdend);
					alert("操作成功!");
					break;
				case op_value[1]:	//保电设置
					protect(db_type)
					break;
				case op_value[2]:	//保电解除
					$("#bdks").val(0);
					$("#bdjs").val(0);
					addBD(db_type);
					break;
				case op_value[3]:	//跳闸
					addBD(db_type);
					break;
				case op_value[4]:	//合闸允许
					addBD(db_type);	
					break;
				case op_value[5]:	//催费告警
					addBD(db_type);	
					break;
				case op_value[6]:	//催费告警解除
					addBD(db_type);	
					break;
				}
				
			}
			else {
				alert("操作失败...");
			}
		}
	);
}

function addBD(op_type){		//添加数据库
	
	var params = {};
	
	params.rtu_id	= $("#rtu_id").val();
	params.zjg_id	= $("#zjg_id").val();
	params.op_type	= op_type;
	
	var json_str 	= jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperGy!dbCtrlOper.action", 		//后台处理程序
		{
			result : json_str
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


function protect(db_type) {		//保电-DB
	
	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.mp_id 			= 1;
	params.m_prot_st 		= $("#bdks").val();
	params.m_prot_ed 		= $("#bdjs").val();
	
	if (params.m_prot_st != rtnValue.prot_st || params.m_prot_ed != rtnValue.prot_ed) {
		var json_str = jsonString.json2String(params);
		loading.loading();
		$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
			{
				firstLastFlag 	: true,
				userData1		: params.rtu_id,
				zjgid			: params.zjg_id,
				mpid 			: params.mp_id,
				gsmflag 		: 0,			//gsmFlag
				gyOperProtect 	: json_str,
				userData2 		: ComntUseropDef.YFF_GYOPER_PROTECT
			},
			function(data) {			    	//回传函数
				loading.loaded();
				if (data.result == "success") {
					addBD(db_type);
				}
				else {
					alert("操作失败!");
				}
			}
		);	
	}
	else {
		addBD(db_type);
	}
}

