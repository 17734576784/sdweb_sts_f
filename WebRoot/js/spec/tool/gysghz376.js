var rtnValue;

$(document).ready(function(){
	$("#btnSearch").click(function(){selcons()});
	$("#operation").click(function(){operation()});
	$("#detail_info").click(function() {showOpDetailDialog()});
});

function selcons(){//检索
	var sghz_flag = "yes";
	var tmp = doSearch(sel_type,ComntUseropDef.YFF_GYCOMM_PAY, rtnValue,sghz_flag);
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	setConsValue(rtnValue);
	
	//手工合闸按钮解禁 
	$("#operation").attr("disabled",false);
	
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
}

//手工合闸,只对第二轮进行操作
function operation(){
	var params = {};
	
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	
	if(!confirm("确定要跳闸吗？"))	return;
	
	params.ctrltype = 0;
	params.ctrlroll = 2;	//只对第二轮进行控制
	params.xdsc 	= 0;	//限电时长定为0
	params.plustime = 0;	//
	params.delaytime = 0;	//延迟时间定为0
	params.paratype = ComntProtMsg.YFF_GY_CTRL_CUT;
	
	u2 = ComntUseropDef.YFF_GYCOMM_CTRL;
	in_param = "gyCommCtrl";
	
	db_type = ComntProtMsg.YFF_GY_CTRL_CUT;

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
				addBD(db_type);
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
