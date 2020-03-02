var rtnValue;
var op_option = ["保电查看","保电设置","解除保电","跳闸","合闸允许","催费告警投入","催费告警解除"];
var op_value  = [0,			1,			2,		 3,		4,			5,			6];

var xd_option = ["永久限电","15分钟","30分钟","1小时","2小时","4小时","8小时","16小时"];
var xd_value = [0,15,30,60,120,240,480,960];

$(document).ready(function(){
	
	$("#btnSearch").click(function(){selcons()});
	$("#operation").click(function(){operation()});
	$("#btnReadCard").click(function(){readcard()});//读卡检索
	$("#cardInfo").click(function(){window.top.card_comm.cardinfo()});//卡内信息
	
		
	initSelect();
	
});

function initSelect(){//初始化 操作类型 下拉框
	var tmp = "";
		for ( var i = 1; i < op_value.length; i++) {
			tmp += ",<option value=" + op_value[i] + ">"+op_option[i]+"</option>";
		}
		$("#oper_type").html(tmp.substring(1));
	
}

function selcons(){//检索
	var js_tmp = doSearch("ks",ComntUseropDef.YFF_GYOPER_PROTECT, rtnValue);
	if(!js_tmp){
		return;
	}	
	setSearchJson2Html(js_tmp)
}

function readcard(){//读卡检索。调用readCardSearchGy
	loading.loading();
	window.setTimeout('window.top.card_comm.readCardSearchGy('+ComntUseropDef.YFF_GYOPER_PROTECT+')', 10);
//	window.top.card_comm.readCardSearchGy(ComntUseropDef.YFF_GYOPER_PROTECT);
}
function setSearchJson2Html(js_tmp){//读卡检索，返回赋值。调用readCardSearch后，readcard中异步执行完成后，执行此函数。
	loading.loaded();

	if(!js_tmp)return;
	
	rtnValue = js_tmp;
	
	setConsValue(rtnValue);
	$("#yffctrl_type").val(rtnValue.yffctrl_type);//控制方式	
	
	var tmp_val = rtnValue.cardmeter_type.split("]");
	WRITECARD_TYPE = tmp_val[0].split("[")[1];
	//调整 20130402	
	//	WRITECARD_TYPE = tmp_val[0].substring(1);

	$("#cardmeter_type").html(tmp_val[1].split('"')[0]);
	$("#writecard_no").html(rtnValue.writecard_no);
	$("#meter_id").html(rtnValue.meter_id);
	
	//显示终端是否在线.
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
	
	if(WRITECARD_TYPE != window.top.SDDef.YFF_CARDMTYPE_KE001 && 
	   WRITECARD_TYPE != window.top.SDDef.YFF_CARDMTYPE_KE006){
		$("#operation").attr("disabled",true);
		alert("非智能表，不支持远程保电。");
		return;
	}else{
		$("#operation").attr("disabled",false);
	}
	
}

function operation(){//操作按钮click
	var value = parseInt($("#oper_type").val());
	var params = {};
	
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	
	var u2 = ComntUseropDef.YFF_GYCOMM_CALLPARA;
	var in_param = "";
	var db_type = 0;
	switch (value) {
	case op_value[1]:		//保电设置
		params.bdbegin 		= 0;
		params.bdend 		= 0;
		params.ctrlflag		= 0;
		if( $("#yffctrl_type").val() == SDDef.YFF_CTRLTYPE_GY_LK ){
			params.paratype = ComntProtMsg.YFF_GY_SET_KEEP;
			u2 = ComntUseropDef.YFF_GYCOMM_SETPARA;
			in_param = "gyCommSetPara";
		}else{
			params.paratype = ComntProtMsg.YFF_GY_CTRL_KEEPON;
			u2 = ComntUseropDef.YFF_GYCOMM_CTRL;
			in_param = "gyCommCtrl";
		}
		
		db_type = ComntProtMsg.YFF_GY_CTRL_KEEPON;
		
		if(!confirm("确定要设置保电吗？"))return;
		
		break;
	case op_value[2]:		//保电解除
		params.bdbegin 		= 0;
		params.bdend 		= 0;
		params.ctrlflag		= 0;
//		params.paratype = ComntProtMsg.YFF_GY_SET_KEEP;
//		u2 = ComntUseropDef.YFF_GYCOMM_SETPARA;
//		in_param = "gyCommSetPara";
		params.paratype = ComntProtMsg.YFF_GY_CTRL_KEEPOFF;
		u2 = ComntUseropDef.YFF_GYCOMM_CTRL;
		in_param = "gyCommCtrl";
		
		db_type = ComntProtMsg.YFF_GY_CTRL_KEEPOFF;
		
		if(!confirm("确定要解除保电吗？"))return;
		break;
	case op_value[3]:		//跳闸
		
		if(!confirm("确定要跳闸吗？"))return;
	
		params.ctrltype = 0;
		params.ctrlroll = 0;
		params.xdsc 	= 0;
		params.plustime = 0;
		params.delaytime = 0;	
		params.paratype = ComntProtMsg.YFF_GY_CTRL_CUT;
		u2 = ComntUseropDef.YFF_GYCOMM_CTRL;
		in_param = "gyCommCtrl";
		
		db_type = ComntProtMsg.YFF_GY_CTRL_CUT;
		
		break;
		
	case op_value[4]:		//合闸允许
	
		if(!confirm("确定要合闸吗？"))return;
	
		params.ctrlroll = 0;
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
	default :return;		
	}
	
	var json_str = jsonString.json2String(params);
	
	var param = {
			firstLastFlag 	: "true",
			userData1		: params.rtu_id,
			zjgid 			: params.zjg_id,
			userData2 		: u2
		};
	eval("param."+in_param+"='"+json_str+"'");
	
	
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	loading.loading();
	$.post( def.basePath + "ajaxoper/actCommGy!taskProc.action", 		//后台处理程序
		param,
		function(data) {			    	//回传函数
			window.top.addJsonOpDetail(data.detailInfo);
			loading.loaded();
			if (data.result == "success") {
				window.top.addStringTaskOpDetail("接收预付费服务任务成功...");
				switch (value) {
				case op_value[1]:	//保电设置
					protect(db_type)
					break;
				case op_value[2]:	//保电解除
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
				default :return;	
				}
				
			}
			else {
				alert("操作失败!");
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
	params.m_prot_st 		= 0;
	params.m_prot_ed 		= 0;
	
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

