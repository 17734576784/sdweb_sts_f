var param = window.dialogArguments;

$(document).ready(function(){
	
	$("#btnSearch").click(function(){searchData();});	
	$("#btnClose").click(function(){window.close();});
	
	fillData(param);
});

function fillData(param) {
	$("#userType").html(param.yhfl);
	$("#userName").html(param.yhmc);
	$("#userAddr").html(param.yddz);
	$("#TotalpayTimes").html(param.jezbs);
	$("#charge").html(param.hjdf);
	$("#wyj").html(param.hjwyj);
	$("#money").html(param.hjje);
	$("#preReceive").html(param.hjys);
	$("#msgInfo").html(param.tsxx);
}

function clearInfo(){
	$("#userType").html("");
	$("#userName").html("");
	$("#userAddr").html("");
	$("#TotalpayTimes").html("");
	$("#charge").html("");
	$("#wyj").html("");
	$("#money").html("");
	$("#preReceive").html("");
	$("#msgInfo").html("");
}

function searchData(){//读取MIS里的用户信息
	clearInfo();
	var dyOperQueryPower = {rtu_id : param.rtu_id, mp_id : param.mp_id, yhbh : param.yhbh}
	loading.loading();
	$.post(def.basePath + "ajaxoper/actMisDy!taskProc.action",
		{
			userData1		: param.rtu_id,
			dyOperStr		: jsonString.json2String(dyOperQueryPower),
			userData2 		: ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER
		},
		function(data){
			loading.loaded();
			//使用MIS接口
			
			if(data.misUseflag == "true"){
				if(data.misOkflag == "true"){	//成功
					var json = eval("(" + data.dyOperStr + ")");
					json.tsxx = "操作成功!";
					fillData(json);
				}
				else{	//失败
					alert(data.misResult);
				}
			}
			//不使用MIS接口
			else{
				alert("【未使用MIS接口】");
			}
	});
	
}