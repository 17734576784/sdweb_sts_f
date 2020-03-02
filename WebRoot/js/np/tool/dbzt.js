var rtnValue;

$(document).ready(function(){
	$("#btnSearch").click(function(){selcons()});
	$("#btnCallState").click(function(){callState()});
});


function selcons(){//检索
	var rtnValue1 = doSearch("other",ComntUseropDef.YFF_READDATA,rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
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
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");	
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag  : true,
			userData1	   : rtu_id,
			mpid 		   : mp_id,
			gsmflag 	   : 0,
			dyCommReadData : json_str,
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
				
				$("#ytzbj").html(json.dbyff_ytzbjzt_desc);
				$("#gdfs").html(json.dbyff_gdfs_desc);
				$("#yxsd").html(json.dbyff_dqyxsd_desc);
				$("#jdqml").html(json.dbyff_jdqmlzt_desc);
				$("#dqjt").html(json.dbyff_dqjt_desc);
				$("#bcyx").html(json.dbyff_bcyx_desc);
				$("#jdqzt").html(json.dbyff_jdqzt_desc);
				$("#fldj").html(json.dbyff_dqyxfldj_desc);
				$("#yxsq").html(json.dbyff_dqyxsq_desc);
				$("#dnblx").html(json.dbyff_dnblx_desc);
				$("#bdzt").html(json.dbyff_protect_desc);
				$("#myzt").html(json.dbyff_keytype_desc);
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

