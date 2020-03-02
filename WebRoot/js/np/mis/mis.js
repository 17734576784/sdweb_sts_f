//MIS相关操作
var misUseflag 	= "false", 	//是否使用MIS
	misOkflag	= "false",	//MIS返回是否成功
	misJezbs 	= 0,		//金额总笔数
	misDetailsvect = null;

function mis_query(params){	//查询缴费
	/////////初始化全局变量	
	misUseflag 	= "false";
	misOkflag	= "false";
	misJezbs 	= 0;
	misDetailsvect = null;
	/////////
	
	loading.tip = "正在查询MIS系统...";
	loading.loading();
	$.post(def.basePath + "ajaxoper/actMisDy!taskProc.action", 
		{
			userData1		: params.rtu_id,
			dyOperStr		: jsonString.json2String(params),
			userData2 		: ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER
		}, function(data){
			loading.loaded();
			
			misUseflag = data.misUseflag;
			misOkflag = data.misOkflag;
			
			//使用MIS接口
			if(data.misUseflag == "true"){
				if(data.misOkflag == "true"){	//成功
					var json = eval("(" + data.dyOperStr + ")");
					$("#jsyhm").html("<a href='javascript:;' title='详细信息' onclick='mis_detaiInfo("+data.dyOperStr+",true)'>" + json.yhmc + "</a>");
					$("#yhfl").html("<font color=blue>" + json.yhfl + "</font>");
					misJezbs	= json.jezbs;
					misDetailsvect = json.detail;
				}
				else{							//失败
					alert(data.misResult);
				}
			}
			//不使用MIS接口
			else{
				$("#jsyhm").html("【未使用MIS接口】");
				$("#yhfl").html("无");
			}
	});
}

function mis_detaiInfo(detail, flag){
	detail.rtu_id = rtnValue.rtu_id;
	detail.mp_id = rtnValue.mp_id;
	detail.yhbh   = rtnValue.userno;
	
	if(flag)detail.tsxx = "操作成功!";
	else detail.tsxx = "操作失败!";
	
	modalDialog.height = 260;
	modalDialog.width = 350;
	modalDialog.param = detail;
	modalDialog.url = def.basePath + "jsp/dyjc/dialog/sg186Detail.jsp";
	modalDialog.show();
}

function mis_pay(params){	//缴费
	loading.tip = "正在向MIS系统缴费，请稍后...";
	loading.loading();
	$.post(def.basePath + "ajaxoper/actMisDy!taskProc.action", 
		{
			userData1		: params.rtu_id,
			dyOperStr		: jsonString.json2String(params),
			userData2 		: ComntUseropDef.YFF_DYOPER_MIS_PAY
		}, function(data){
			loading.loaded();
			alert(data.misResult);
	});
}

function mis_rever(params){	//冲正
	
	loading.tip = "正在向MIS系统冲正，请稍后...";
	loading.loading();
	$.post(def.basePath + "ajaxoper/actMisDy!taskProc.action", 
		{
			userData1		: params.rtu_id,
			dyOperStr		: jsonString.json2String(params),
			userData2 		: ComntUseropDef.YFF_DYOPER_MIS_REVER
		},
	function(data){
		loading.loaded();
		alert(data.misResult);
	});
}