var rtnValue;
var rtuId = window.dialogArguments.rtuId;
var mpId  = window.dialogArguments.mpId;
var oper_type = 0;//用来控制检索按钮的启用。0：进入页面前没有检索终端， 1: 进入页面前已经检索了终端

$(document).ready(function(){
	$("#rtu_id").val(rtuId);
	$("#mp_id").val(mpId);
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("150,80,80,80,80,80,80,80,80,120,80,80,*")
	mygrid.setColAlign("center,center,left,right,right,right,right,right,right,right,left,left");
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.init();
	mygrid.setSkin("light");
	
	$("#btnSearch").click(function(){selcons()});
	$("#btnRead").click(function(){readData()});
	$("#btnDetail").click(function(){showOpDetailDialog()});
	//20131130增加取消任务操作
	$("#btnCancel").click(function(){cancelTask()});
	
	if(rtuId != "" && rtuId != undefined){
		$("#btnSearch").attr("disabled",true);
		defaultSearch();
		oper_type = 1;
	}
	
	
});
function selcons(){//检索
	
	var rtnValue1 = doSearch("other",ComntUseropDef.YFF_READDATA,rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
	$("#userno").html(rtnValue.userno);
	$("#username").html(rtnValue.username);
	$("#tel").html(rtnValue.tel);
	$("#useraddr").html(rtnValue.useraddr);
	$("#esamno").html(rtnValue.esamno);
	$("#pt_ratio").html(rtnValue.pt_r);
	$("#ct_ratio").html(rtnValue.ct_r);
	$("#cacl_type").html(rtnValue.cacl_type);
	$("#feectrl_type").html(rtnValue.feectrl_type);
	$("#pay_type_desc").html(rtnValue.pay_type_desc);
	$("#pay_type").val(rtnValue.pay_type);
	
	$("#rtu_id").val(rtnValue.rtu_id);
	$("#mp_id").val(rtnValue.mp_id);
	getYffRecs();
	$("#feeproj_id").val(rtnValue.feeproj_id);
	$("#feeproj_desc").html(rtnValue.feeproj_desc);
	$("#feeproj_detail").html(rtnValue.feeproj_detail);
	
	$("#yffalarm_id").val(rtnValue.yffalarm_id);
	$("#yffalarm_desc").html(rtnValue.yffalarm_desc);
	$("#yffalarm_detail").html(rtnValue.yffalarm_detail);

	$("#buy_times").html("");
	$("#syje").html("");
	$("#ljgdje").html("");
	$("#sqje").html("");
	
	$("#btnRead").attr("disabled",false);
	$("#btnDetail").attr("style","display:blank");
	$("#btnCancel").attr("style","display:blank");
	
}

function readData(){//读取余额
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	var strTdtype = $("#dtType").find("option:selected").text();
	if($("#dtType").val() == "esamye"){//状态查询，过esam
		var params = {};
		params.paratype = ComntProtMsg.YFF_DY_CALL_STATE;
		var json_str = jsonString.json2String(params);
		
		//20131130修改，新加取消操作按钮，加载时loading大小做调整，禁用检索和读取两个按钮
		loading.loading_dialog();
		$("#btnRead").attr("disabled",true);
		$("#btnSearch").attr("disabled",true);
		
		addStringTaskOpDetail("正在向预付费服务发送信息...");
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
				$("#btnRead").attr("disabled",false);
				//如果进入页面前没有查询终端，恢复检索
				if(oper_type == 0){
					$("#btnSearch").attr("disabled",false);
				}
				
				addJsonOpDetail(data.detailInfo);	
				if (data.result == "success") {
					var json = eval("(" + data.dyCommCallPara + ")");
					$("#buy_times").html(json.buy_num);
					$("#syje").html(json.remain_money / 100);
					if (glo_opdetail_dlg == null || glo_opdetail_dlg.closed) {
						alert("读取" + strTdtype + "成功!");
					}else{
						addStringTaskOpDetail("读取" + strTdtype + "成功!");
					}
				}
				else {
					if (glo_opdetail_dlg == null || glo_opdetail_dlg.closed) {
						alert("读取" + strTdtype + "失败!");
					}else{
						addStringTaskOpDetail("读取" + strTdtype + "失败!");
					}
				}
			}
		);
		
	}else{//
		var params = {};
		if($("#dtType").val() == "dbye"){
			params.datatype = ComntProtMsg.YFF_CALL_DY_REMAIN;
		}else if ($("#dtType").val() == "dbyetc"){
			params.datatype = ComntProtMsg.YFF_CALL_DB_TCREMAIN;
		}else if($("#dtType").val() == "sqjetc"){
			params.datatype = ComntProtMsg.YFF_CALL_DB_TCOVER;
		}else{
			return;
		}		
		params.ymd = 120403;
		var json_str = jsonString.json2String(params);
		
		//20131130修改，新加取消操作按钮，加载时loading大小做调整，禁用检索和读取两个按钮
		loading.loading_dialog();
		$("#btnRead").attr("disabled",true);
		$("#btnSearch").attr("disabled",true);
		
		addStringTaskOpDetail("正在向预付费服务发送信息...");
		$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
			{
				firstLastFlag : true,
				userData1: rtu_id,
				mpid : mp_id,
				gsmflag : 0,
				dyCommReadData : json_str,
				userData2 : ComntUseropDef.YFF_READDATA
			},
			function(data) {			    	//回传函数
				loading.loaded();
				$("#btnRead").attr("disabled",false);
				//如果进入页面前没有查询终端，恢复检索
				if(oper_type == 0){
					$("#btnSearch").attr("disabled",false);
				}
				
				addJsonOpDetail(data.detailInfo);
				if (data.result == "success") {
					if (glo_opdetail_dlg == null || glo_opdetail_dlg.closed) {
						alert("读取" + strTdtype + "成功!");
					}else{
						addStringTaskOpDetail("读取" + strTdtype + "成功!");
					}
					var json = eval("(" + data.dyCommReadData + ")");				
					if($("#dtType").val() == "dbye"){
						$("#buy_times").html(json.pay_num);
						$("#syje").html(json.remain_money);
						$("#ljgdje").html(json.total_money);
					}else if ($("#dtType").val() == "dbyetc"){
						$("#syje").html(json.cur_val / 100);
					}else if($("#dtType").val() == "sqjetc"){
						$("#sqje").html(json.cur_val / 100);
					}else{
						return;
					}	
				}
				else {
					if (glo_opdetail_dlg == null || glo_opdetail_dlg.closed) {
						alert("读取" + strTdtype + "失败!");
					}else{
						addStringTaskOpDetail("读取" + strTdtype + "失败!");
					}
				}
			}
		);
	}
	
	
}

function defaultSearch(){//检索，传入终端ID和电表ID
		
	var param = {};
	param.rtu_id = $("#rtu_id").val(); 
	param.mp_id  = $("#mp_id").val(); 	
	$.post(def.basePath + "ajaxdyjc/actConsPara!getOneRec.action",{result:jsonString.json2String(param)},	function(data) {			    	//回传函数
		if (data.result != "") {
			var jsdata = eval('(' + data.result + ')');
			$("#userno").html(jsdata.userno);
			$("#username").html(jsdata.username);
			$("#tel").html(jsdata.tel);
			$("#useraddr").html(jsdata.useraddr);
			$("#esamno").html(jsdata.esamno);
			$("#pt_ratio").html(jsdata.pt_r);
			$("#ct_ratio").html(jsdata.ct_r);
		}
	});
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag : true,
			userData1: rtuId,
			mpid : mpId,
			gsmflag : 0,
			userData2: ComntUseropDef.YFF_DYOPER_GPARASTATE
		},
		function(data) {			    	//回传函数
			if (data.result == SDDef.SUCCESS) {
				var json = eval("(" + data.dyOperGParaState + ")");
				$("#cacl_type_desc").html(json.cacl_type_desc);
				$("#feectrl_type").html(json.feectrl_type);
				$("#pay_type_desc").html(json.pay_type_desc);
				$("#pay_type").val(json.pay_type);
				
				$("#feeproj_id").val(json.feeproj_id);
				$("#feeproj_desc").html(json.feeproj_desc);
				$("#feeproj_detail").html(json.feeproj_detail);
				
				$("#yffalarm_id").val(json.yffalarm_id);
				$("#yffalarm_desc").html(json.yffalarm_desc);
				$("#yffalarm_detail").html(json.yffalarm_detail);
			}
			else {
				alert("查找电表信息错误!");
			}
		}
	);
	getYffRecs();
		
	$("#btnRead").attr("disabled",false);
	$("#btnDetail").attr("style","display:blank");
	$("#btnCancel").attr("style","display:blank");
}

function cancelTask(){
	var cancel_oper = null;
	if($("#dtType").val() == "esamye"){
		cancel_oper = ComntUseropDef.YFF_DYCOMM_CALLPARA;
	}else{
		cancel_oper = ComntUseropDef.YFF_READDATA;
	}
	
	$.post(def.basePath + "ajaxoper/actCommDy!cancelTask.action", {
		userData1 : rtuId,
		userData2 : cancel_oper
	});
}



