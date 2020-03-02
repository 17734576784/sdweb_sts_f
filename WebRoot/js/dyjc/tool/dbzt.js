var rtnValue;
var meter_type = 0;

$(document).ready(function(){
	$("#btnSearch").click(function(){selcons()});
	$("#btnCallState").click(function(){callState()});
	$(".type2").hide();
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
		$(".type1").hide();
		$(".type2").show();
	}
	else{
		meter_type = 0;
		$(".type2").hide();
		$(".type1").show();
	}
	setConsValue(rtnValue);
	
	$("#ytzbj").html("");
	$("#gdfs").html("");
	$("#yxsd").html("");
	$("#jdqml").html("");
	$("#dqjt").html("");
	$("#bcyx").html("");
	$("#jdqzt").html("");
	$("#fldj").html("");
	$("#yxsq").html("");
	$("#dnblx").html("");
	$("#bdzt").html("");
	$("#myzt").html("");
	
	//新规约电表电表字状态
	$("#ytzbj2").html("");
	$("#gdfs2").html("");
	$("#yxsd2").html("");
	$("#jdqml2").html("");
	$("#bcyx2").html("");
	$("#jdqzt2").html("");
	$("#yxsq2").html("");
	$("#dnblx2").html("");
	$("#bdzt2").html("");
	$("#sfrzzt").html("");
	$("#bdkhzt").html("");
	$("#yckhzt").html("");
	
	$("#btnCallState").attr("disabled",false);
}


function callState(){//读表状态
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	
	var params = {};
	params.datatype = ComntProtMsg.YFF_CALL_DB_STATE3;
	params.ymd 		= 120203;
	var json_str 	= jsonString.json2String(params);
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
			//20131021新加电表类型
			paraType	   : meter_type,
			userData2 	   : ComntUseropDef.YFF_READDATA
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("查询电表状态成功!");
				}else{
					window.top.addStringTaskOpDetail("查询电表状态成功!");
				}
				var json = eval("(" + data.dyCommReadData + ")");				
				
				//旧规约表赋值
				if(data.paraType == 0){
					$("#ytzbj").html(json.dbyff_ytzbjzt_desc);
					$("#gdfs") .html(json.dbyff_gdfs_desc);
					$("#yxsd") .html(json.dbyff_dqyxsd_desc);
					$("#jdqml").html(json.dbyff_jdqmlzt_desc);
					$("#dqjt") .html(json.dbyff_dqjt_desc);
					$("#bcyx") .html(json.dbyff_bcyx_desc);
					$("#jdqzt").html(json.dbyff_jdqzt_desc);
					$("#fldj") .html(json.dbyff_dqyxfldj_desc);
					$("#yxsq") .html(json.dbyff_dqyxsq_desc);
					$("#dnblx").html(json.dbyff_dnblx_desc);
					$("#bdzt") .html(json.dbyff_protect_desc);
					$("#myzt") .html(json.dbyff_keytype_desc);
				}
				//新规约表赋值
				else{
					$("#ytzbj2").html(json.dbyff_ytzbjzt_desc); 
					$("#gdfs2").html(json.dbyff_gdfs_desc);    
					$("#yxsd2").html(json.dbyff_dqyxsd_desc);  
					$("#jdqml2").html(json.dbyff_jdqmlzt_desc);     
					$("#bcyx2").html(json.dbyff_bcyx_desc);    
					$("#jdqzt2").html(json.dbyff_jdqzt_desc);   
					$("#yxsq2").html(json.dbyff_dqyxsq_desc);  
					$("#dnblx2").html(json.dbyff_dnblx_desc);   
					$("#bdzt2").html(json.dbyff_protect_desc); 
					$("#sfrzzt").html(json.dbyff_authstate_desc);
					$("#bdkhzt").html(json.dbyff_localkh_desc);
					$("#yckhzt").html(json.dbyff_remotekh_desc);
				}
			}
			else {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("查询电表状态失败!");
				}else{
					window.top.addStringTaskOpDetail("查询电表状态失败!");
				}
			}
		}
	);
}

