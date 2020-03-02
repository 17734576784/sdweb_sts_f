var rtnValue = null;
//电表类型(新老规约)判断标识
var meter_type = 0;
$(document).ready(function(){

	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("150,80,80,80,80,80,80,80,80,120,80,80,*")
	mygrid.setColAlign("center,center,left,right,right,right,right,right,right,right,left,left");
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.init();
	mygrid.setSkin("light");
	
	$("#btnSearch").click(function(){selcons()});
	$("#btnRead").click(function(){readData()});
	
});
function selcons(){//检索
	
	var rtnValue1 = doSearch("other",ComntUseropDef.YFF_READDATA,rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
	
	//20131021添加，当查询结果中的预付费表类型 为新规约表时(>=10),将电表类型赋值为1。老电表为0
	var yffmeter_type = rtnValue.yffmeter_type;

	if(is2013Meter(yffmeter_type)){
		//将表类型置为1，传到后台进行表类型识别
		meter_type = 1;
	}
	else{
		meter_type = 0;
	}
	//end
	
	var rtn_str = jsonString.json2String(rtnValue);
	setConsValue(rtnValue);
	$("#pt_ratio").html(rtnValue.pt);
	$("#ct_ratio").html(rtnValue.ct);
	
	getYffRecs()	//获取缴费记录
	
	$("#buy_times").html("");
	$("#syje").html("");
	$("#ljgdje").html("");
	$("#sqje").html("");
	$("#btnRead").attr("disabled",false);
}



function readData(){//读取余额
	var rtu_id = $("#rtu_id").val();
	var mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	var strTdtype = $("#dtType").find("option:selected").text();
	
	if($("#dtType").val() == "esamye"){//状态查询，过esam
		var params = {};
		params.paratype = ComntProtMsg.YFF_DY_CALL_STATE;
		var json_str    = jsonString.json2String(params);
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
				//添加电表类型判断标识
				paraType	   : meter_type,
				dyCommCallPara : json_str,
				userData2 	   : ComntUseropDef.YFF_DYCOMM_CALLPARA
			},
			function(data) {			    	//回传函数
				loading.loaded();
				window.top.addJsonOpDetail(data.detailInfo);	
				if (data.result == "success") {
					var json = eval("(" + data.dyCommCallPara + ")");
					if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
						alert("读取" + strTdtype + "成功!");
					}else{
						window.top.addStringTaskOpDetail("读取" + strTdtype + "成功!");
					}
					$("#buy_times").html(json.buy_num);
					$("#syje").html(json.remain_money / 100);
				}
				else {
					if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
						alert("读取" + strTdtype + "失败!");
					}else{
						window.top.addStringTaskOpDetail("读取" + strTdtype + "失败!");
					}
					$("#syje").html("");
				}
			}
		);
		
	}else{
		var params = {};
		if($("#dtType").val() == "dbye"){				//电表余额
			params.datatype = ComntProtMsg.YFF_CALL_DY_REMAIN;
		}else if ($("#dtType").val() == "dbyetc"){		//电表余额透传
			params.datatype = ComntProtMsg.YFF_CALL_DB_TCREMAIN;
		}else if($("#dtType").val() == "sqjetc"){		//赊欠余额透传
			params.datatype = ComntProtMsg.YFF_CALL_DB_TCOVER;
		}else{
			return;
		}
		params.ymd = 120403;
		
		var json_str = jsonString.json2String(params);
		loading.loading();
		
		//20131130
		//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
		window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
		window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommDy!cancelTask.action"
		window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_READDATA;
		//end
		
		window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
		$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
			{
				firstLastFlag  : true,
				userData1	   : rtu_id,
				mpid 		   : mp_id,
				dyCommReadData : json_str,
				userData2 	   : ComntUseropDef.YFF_READDATA
			},
			function(data) {			    	//回传函数
				loading.loaded()
				window.top.addJsonOpDetail(data.detailInfo);
				if (data.result == "success") {
					if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
						alert("读取" + strTdtype + "成功!");
					}else{
						window.top.addStringTaskOpDetail("读取" + strTdtype + "成功!");
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
					if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
						alert("读取" + strTdtype + "失败!");
					}else{
						window.top.addStringTaskOpDetail("读取" + strTdtype + "失败!");
					}
				}
			}
		);
		
		
	}
	
	
}




//function findPTCT(){//检索，传入终端ID和电表ID
//	
//	var param = {};
//	param.rtu_id = $("#rtu_id").val(); 
//	param.mp_id  = $("#mp_id").val(); 	
//	$.post(def.basePath + "ajaxdyjc/actConsPara!getOneRec.action",{result:jsonString.json2String(param)},	
//		function(data) {			    	//回传函数
//			alert(data.result);
//			if (data.result != "") {
//				var jsdata = eval('(' + data.result + ')');
//				$("#userno").html(jsdata.userno);
//				$("#username").html(jsdata.username);
//				$("#tel").html(jsdata.tel);
//				$("#useraddr").html(jsdata.useraddr);
//				$("#esamno").html(jsdata.esamno);
//				$("#pt_ratio").html(jsdata.pt_r);
//				$("#ct_ratio").html(jsdata.ct_r);
//			}
//	});
//	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
//		{
//			firstLastFlag : true,
//			userData1: rtuId,
//			mpid : mpId,
//			gsmflag : 0,
//			userData2: ComntUseropDef.YFF_DYOPER_GPARASTATE
//		},
//		function(data) {			    	//回传函数
//			if (data.result == SDDef.SUCCESS) {
//				var json = eval("(" + data.dyOperGParaState + ")");
//				$("#cacl_type_desc").html(json.cacl_type_desc);
//				$("#feectrl_type").html(json.feectrl_type);
//				$("#pay_type_desc").html(json.pay_type_desc);
//				$("#pay_type").val(json.pay_type);
//				
//				$("#feeproj_id").val(json.feeproj_id);
//				$("#feeproj_desc").html(json.feeproj_desc);
//				$("#feeproj_detail").html(json.feeproj_detail);
//				
//				$("#yffalarm_id").val(json.yffalarm_id);
//				$("#yffalarm_desc").html(json.yffalarm_desc);
//				$("#yffalarm_detail").html(json.yffalarm_detail);
//			}
//			else {
//				alert("查找电表信息错误!");
//			}
//		}
//	);
//	getYffRecs();
//		
//	$("#btnRead").attr("disabled",false);
//	$("#btnDetail").attr("style","display:blank");
//}

