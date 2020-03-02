var rtnValue;
$(document).ready(function(){

	$("#btnSearch").focus().click(function(){selcons()});
	$("#btnCall").click(function(){callPara()});
	$("#btnSet").click(function(){setPara()});
	//20140114 dubr 当选择ESAME表号时，ESAME表号改成可编辑状态
	$("#op_type").change(function(){setEsameno()});	
	//打开页面时，下拉框不可用
	$("#op_type").attr("disabled","disabled");
	//初始化页面时，下拉框选中项为esam 故esma表号可编辑
	$("#esamno").attr("disabled",false);
	//end
		
});
function selcons(){//检索
	var rtnValue1 = doSearch("",ComntUseropDef.YFF_DYCOMM_SETPARA,rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
	//20140115 当返回查询结果时  下拉框可用
	$("#op_type").attr("disabled",false);
	//20131021添加，获取预付费表类型
	var yffmeter_type = rtnValue.yffmeter_type;
	//新规约表
	if(is2013Meter(yffmeter_type)){
		$("#bnhh").attr("disabled","disabled");
		//20140115 dubr 当表为2013表时，下拉初始化为ESAM表号；（下拉框的第一项 13版表不支持，故初始化选中第二项）
		$("#op_type").val(ComntProtMsg.YFF_DY_SET_METERNO );	
		//end
		$("#biaohao").attr("disabled",false);
	}
	//旧规约表
	else{
		$("#esamno").attr("disabled","disabled");
		$("#bnhh").attr("disabled",false);
		$("#biaohao").attr("disabled","disabled");
	}
	
	$("#db_userno").html(rtnValue.userno);
	$("#username").html(rtnValue.username);
	$("#esamno").val(rtnValue.esamno);
	$("#db_pt_ratio").html(rtnValue.pt);
	$("#db_ct_ratio").html(rtnValue.ct);
	$("#db_alarm1").html(rtnValue.yffalarm_alarm1);
	$("#db_alarm2").html(rtnValue.yffalarm_alarm2);
	$("#db_writecard_no").html(rtnValue.writecard_no);
	
	$("#feeproj_id").val(rtnValue.feeproj_id);
	$("#feeproj_desc").html(rtnValue.feeproj_desc);
	$("#feeproj_detail").html(rtnValue.feeproj_detail);
	
	$("#yffalarm_id").val(rtnValue.yffalarm_id);
	$("#yffalarm_desc").html(rtnValue.yffalarm_desc);
	$("#yffalarm_detail").html(rtnValue.yffalarm_detail);

	$("#rtuonline_img").attr("src",rtnValue.onlinesrc).attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
	
	$("#btnCall").attr("disabled",false);
	$("#btnSet").attr("disabled",false);
}
//当设置ESAME表号时，用户esame表号可编辑。其他情况下，均不可编辑
function setEsameno(){
	if($("#op_type").val() == 7){
		$("#esamno").attr("disabled",false);
	}else {
		$("#esamno").attr("disabled",true);
	}
	}


function setPara(){//设置参数
	
	if(rtnValue == undefined || rtnValue == null)return;
	var params = {};
	params.rtu_id = rtnValue.rtu_id;
	params.mp_id = rtnValue.mp_id;
	params.paratype = $("#op_type").val();
	params.user_type = 1;
	params.bit_update = 0;
	params.chg_date = 0;
	params.chg_time = 0;
	params.alarm_val1 = rtnValue.yffalarm_alarm1*100;
	params.alarm_val2 = rtnValue.yffalarm_alarm2*100;
	params.pt = rtnValue.pt;
	params.ct = rtnValue.ct;
	params.userno = rtnValue.writecard_no;
	//20140114 dubr 设置ESAM表号时，新表号从页面中取得
	params.meterno = $("#esamno").val();	

	
	var json_str = jsonString.json2String(params);
	loading.loading();
	
	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommDy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_DYCOMM_SETPARA;
	//end
	
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag 	: true,
			userData1		: params.rtu_id,
			mpid 			: params.mp_id,
			dyCommSetPara 	: json_str,
			userData2 		: ComntUseropDef.YFF_DYCOMM_SETPARA
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					//20140114 dubr 判断是否是更新ESAM表号，当更新表号时，同时更新数据库
					if($("#op_type").val() == 7){
						UpdateEsaseNo();
					}else{							
						alert("设置参数成功!");
					}
				}else{
					if($("#op_type").val() == 7){
						UpdateEsaseNo();
					}
					window.top.addStringTaskOpDetail("设置参数成功!");					
				}
				//end
			}
			else {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("设置参数失败!");
				}else{
					window.top.addStringTaskOpDetail("设置参数失败!");
				}
			}
		}		
	);
	
}


//20140114 dubr 将新esam表号更新到数据库中
function UpdateEsaseNo(){
    var params = {};
	params.rtu_id = rtnValue.rtu_id;
	params.mp_id = rtnValue.mp_id; 
	params.meterno = $("#esamno").val();
	var json_str = jsonString.json2String(params);
	$.post( def.basePath + "ajaxoper/actOperDy!UpdateEsameNo.action", 		//后台处理程序
		{
			dyUpdateEsameNo		 : json_str	
		},function(data){
			if (data.result == "success"){
				alert("设置参数成功!");
				window.top.global_ggtz(window.top.dbUpdate.YD_DBUPDATE_TABLE_METER);
			}else{
				alert("设置参数成功，本地存库失败!");	
				
			}
		}
	);
}
//end

function callPara(){//读表参数
	
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	var params = {};
	params.paratype = 1;
	var json_str = jsonString.json2String(params);
	
	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommDy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_DYCOMM_CALLPARA;
	//end
	
	loading.loading();
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag 	: true,	
			userData1		: rtnValue.rtu_id,
			mpid 			: rtnValue.mp_id,
			dyCommCallPara  : json_str,
			userData2 		: ComntUseropDef.YFF_DYCOMM_CALLPARA
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);			
			if (data.result == "success") {
				var json = eval("(" + data.dyCommCallPara + ")");
				$("#mp_alarm1").html(parseFloat(json.alarm_val1) /100);
				$("#mp_alarm2").html(parseFloat(json.alarm_val2) /100);
				$("#mp_pt_ratio").html(json.pt);
				$("#mp_ct_ratio").html(json.ct);
				$("#mp_writecard_no").html(json.userno);
				
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("接收预付费服务任务成功.");
				}else{
					window.top.addStringTaskOpDetail("接收预付费服务任务成功...");
				}
			}else {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("接收预付费服务任务失败.");
				}else{
					window.top.addStringTaskOpDetail("接收预付费服务任务失败...");
				}
			}
		}
	);
	
}