//查询缴费
function mis_query(params, out_param) {
	//河北南网
	if(window.top.provinceMisFlag == "HB"){
		mis_query_hb(params, out_param);
	}
	//河南
	else if (window.top.provinceMisFlag == "HN"){
		mis_query_hn(params, out_param);
	}
	//甘肃
	else if (window.top.provinceMisFlag == "GS"){
		mis_query_gs(params, out_param);
	}
}

//河北南网-查询缴费
function mis_query_hb(params, out_param) {
	//初始化变量	
	out_param.misUseflag 	 = "false";			//是否使用MIS
	out_param.misOkflag		 = "false";			//MIS返回是否成功
	out_param.misJezbs 		 = 0;				//金额总笔数
	//out_param.misHsdwbh	 = null;			//--新增--电力资金结算单位编号
	//out_param.misBatchNo	 = null;			//--新增--电力资金编号（终端批次号）
	out_param.misDetailsvect = null;
	
	loading.tip = "正在查询MIS系统...";
	loading.loading();
	$.post(def.basePath + "ajaxoper/actMisDy!taskProc.action", 
		{
			userData1		: params.rtu_id,
			dyOperStr		: jsonString.json2String(params),
			userData2 		: ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER
		}, function(data){
			loading.loaded();
			
			out_param.misUseflag = data.misUseflag;
			out_param.misOkflag  = data.misOkflag;
			
			//使用MIS接口
			if(data.misUseflag == "true"){
				if(data.misOkflag == "true"){	//成功
					var json = eval("(" + data.dyOperStr + ")");
					$("#jsyhm").html("<a href='javascript:;' title='详细信息' onclick='mis_detaiInfo("+data.dyOperStr+",true)'>" + json.yhmc + "</a>");
					out_param.misJezbs		 = json.jezbs;
					out_param.misDetailsvect = json.detail;
					
					//客户分类
					$("#yhfl").html("<font color=blue>" + json.yhfl + "</font>");
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

//河南-查询缴费
function mis_query_hn(params, out_param) {
	//初始化变量	
	out_param.misUseflag 	 = "false";			//是否使用MIS
	out_param.misOkflag		 = "false";			//MIS返回是否成功
	out_param.misJezbs 		 = 0;				//金额总笔数
	out_param.misHsdwbh	 	 = null;			//--新增--电力资金结算单位编号
	out_param.misBatchNo	 = null;			//--新增--电力资金编号（终端批次号）
	out_param.misDetailsvect = null;
	
	loading.tip = "正在查询MIS系统...";
	loading.loading();
	$.post(def.basePath + "ajaxoper/actMisDy!taskProc.action", 
		{
			userData1		: params.rtu_id,
			dyOperStr		: jsonString.json2String(params),
			userData2 		: ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER
		}, function(data){
			loading.loaded();
			
			out_param.misUseflag = data.misUseflag;
			out_param.misOkflag = data.misOkflag;
			
			//使用MIS接口
			if(data.misUseflag == "true"){
				if(data.misOkflag == "true"){	//成功
					var json = eval("(" + data.dyOperStr + ")");
					$("#jsyhm").html("<a href='javascript:;' title='详细信息' onclick='mis_detaiInfo("+data.dyOperStr+",true)'>" + json.yhmc + "</a>");
					out_param.misJezbs	= json.jezbs;
					out_param.misDetailsvect = json.detail;
					
					//河南MIS没有客户分类
					$("#khfl").css("visibility","hidden");
					out_param.misHsdwbh	= json.hsdwbh;			//河南新增
					out_param.misBatchNo  = json.batch_no;		//河南新增
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


//甘肃-查询缴费
function mis_query_gs(params, out_param) {
	//初始化变量	
	out_param.misUseflag 	 = "false";			//是否使用MIS
	out_param.misOkflag		 = "false";			//MIS返回是否成功
	out_param.misJezbs 		 = 0;				//金额总笔数
	out_param.misDzpc	 	 = "";				//--新增--对账批次
	out_param.misDetailsvect = null;
	
	loading.tip = "正在查询MIS系统...";
	loading.loading();
	$.post(def.basePath + "ajaxoper/actMisDyGS!taskProc.action", 
		{
			userData1		: params.rtu_id,
			dyOperStr		: jsonString.json2String(params),
			userData2 		: ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER
		}, function(data){
			loading.loaded();
			
			out_param.misUseflag = data.misUseflag;
			out_param.misOkflag = data.misOkflag;
			
			//使用MIS接口
			if(data.misUseflag == "true"){
				if(data.misOkflag == "true"){	//成功
					var json = eval("(" + data.dyOperStr + ")");
					$("#jsyhm").html("<a href='javascript:;' title='详细信息' onclick='mis_detaiInfo("+data.dyOperStr+",true)'>" + json.yhmc + "</a>");
					out_param.misJezbs	= json.jezbs;
					out_param.misDetailsvect = json.detail;
					
					//甘肃MIS没有客户分类
					$("#khfl").css("visibility","hidden");
					out_param.misDzpc	= json.dzpc;			//对账批次
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
	provinceMisFlag = window.top.provinceMisFlag;
	detail.rtu_id = rtnValue.rtu_id;
	detail.mp_id = rtnValue.mp_id;
	detail.yhbh   = rtnValue.userno;
	
	if(flag)detail.tsxx = "操作成功!";
	else detail.tsxx = "操作失败!";

	modalDialog.height = 260;
	modalDialog.width = 350;
	modalDialog.param = detail;
	if("HB" == provinceMisFlag){
		modalDialog.url = def.basePath + "jsp/dyjc/dialog/sg186Detail.jsp";
	}
	else if("HN" == provinceMisFlag){
		modalDialog.url = def.basePath + "jsp/dyjc/dialog/sg186DetailHN.jsp";
	}
	else if("GS" == provinceMisFlag){
		modalDialog.height = 330;
		modalDialog.width = 350;
		modalDialog.url = def.basePath + "jsp/dyjc/dialog/sg186DetailGS.jsp";
	}
	else{
		alert("不支持该省份MIS查询！");
		return;
	}
	modalDialog.show();
}

function mis_pay(params){	//缴费
	loading.tip = "正在向MIS系统缴费，请稍后...";
	loading.loading();
	var url = "";
	if(window.top.provinceMisFlag == "GS"){
		url = def.basePath + "ajaxoper/actMisDyGS!taskProc.action";
	}
	else{
		url = def.basePath + "ajaxoper/actMisDy!taskProc.action";
	}
	
	$.post( url, 
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
	var url = "";
	if(window.top.provinceMisFlag == "GS"){
		url = def.basePath + "ajaxoper/actMisDyGS!taskProc.action";
	}
	else{
		url = def.basePath + "ajaxoper/actMisDy!taskProc.action";
	}
	$.post(url, 
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