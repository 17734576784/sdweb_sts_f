//param={"rtuId":30000003,"mpId":1};
var mygrid 	= null;
var rtuId 	= window.dialogArguments.rtuId;
var mpId  	= window.dialogArguments.mpId;
var opType  = window.dialogArguments.opType;
var cbDay   = window.dialogArguments.cbDay;
var khDate  = window.dialogArguments.khDate;
//var rtuId 	= 30000002;
//var mpId  	= 2;
//var opType  = 2;
//var cbDay	= 1;
var nodata  = "0";//无数据用0表示
var clpNum  = 1;
var mpIds 	= [-1,-1,-1];


$(document).ready(function(){
	var emptyString = "";

	if(opType == 1 ){
		document.title = "居民发行电费" + emptyString;
		$("#btnUpdate").val("发行");
		initFxdfGrid();
	}else if(opType == 2){
		document.title = "重新阶梯清零" + emptyString;
		$("#btnUpdate").val("清零");
		initJtqlGrid();
	}
	//return;
	
	$("#zbd0").focus();
	
	initDate();
	readMp();
	
	$("#btnRead").click(function(){readBD();});
	$("#btnUpdate").click(function(){if(checkBD())doOperate();});	

	

	
});

function initDate() {
	var mydate=new Date();
	var myM=mydate.getMonth()>8?(mydate.getMonth()+1).toString():'0' + (mydate.getMonth()+1);
	var myD=mydate.getDate()>9?mydate.getDate().toString():'0' + mydate.getDate();
	$("#dateTime").val(mydate.getFullYear()+"年"+myM+"月"+myD+"日");
}

function readMp() {
	var params = '{"rtuId":' + rtuId + ',"mpId":' + mpId + '}';
	$.post(def.basePath + "ajaxdyjc/actImportBD!readMpPara.action",{result : params},function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			clpNum = json.clp_num;	//测量点个数
			$("#mp_name0").html(json.mp_desc0);
			$("#mp_name1").html(json.mp_desc1);
			$("#mp_name2").html(json.mp_desc2);
			mpIds = [json.mp_id0,json.mp_id1,json.mp_id2];
			for(var i=2;i>=clpNum;i--){
				$("#zbd" + i).attr('disabled',true).css('background','#e0e0e0');
				$("#jbd" + i).attr('disabled',true).css('background','#e0e0e0');
				$("#fbd" + i).attr('disabled',true).css('background','#e0e0e0');
				$("#pbd" + i).attr('disabled',true).css('background','#e0e0e0');
				$("#gbd" + i).attr('disabled',true).css('background','#e0e0e0');
			}
		}else{
			alert("读取电表信息错误..");
			return;
		}
	});
}

function readBD(){//从数据库中查询表底
	if(rtuId == "" || mpId == ""){
		alert("请先选择要录入表底的电表。");
		return;
	}
	var dtTm   = $("#dateTime").val();
	var tDate  = dtTm.substring(0,4)+dtTm.substring(5,7)+dtTm.substring(8,10);
	var params = '{"rtuId":' + rtuId + ',"mpId0":' + mpIds[0] + ',"mpId1":' + mpIds[1] + ',"mpId2":' + mpIds[2] + ',"tDate":' + tDate + '}';
	$.post(def.basePath + "ajaxdyjc/actImportBD!readBD.action",{result:params},function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			for(var i=0;i<clpNum;i++){
				$("#zbd" + i).val(json.zbd[i]);
				$("#jbd" + i).val(json.jbd[i]);
				$("#fbd" + i).val(json.fbd[i]);
				$("#pbd" + i).val(json.pbd[i]);
				$("#gbd" + i).val(json.gbd[i]);
			}
		}else{
			for(var i=0;i<clpNum;i++){
				$("#zbd" + i).val(nodata);
				$("#jbd" + i).val(nodata);
				$("#fbd" + i).val(nodata);
				$("#pbd" + i).val(nodata);
				$("#gbd" + i).val(nodata);
			}
		}
		calBdInfo();
	});
	
}
function checkBD(){
	 var reg = new RegExp("^[0-9]\d*\.\d*|[0-9]\d*$");
	 for(var i = 0; i < clpNum; i++){
		 if($("#zbd"+i).val()!="" && !reg.test($("#zbd"+i).val())){
			 alert("总表底[" + $("#zbd"+i).val() + "]不是合法的表底数值！");
			 $("#zbd"+i).focus().select();
			 return false;
		 }
		 if($("#jbd"+i).val()!="" && !reg.test($("#jbd"+i).val())){
			 alert("尖表底[" + $("#jbd"+i).val() + "]不是合法的表底数值！");
			 $("#jbd"+i).focus().select();
			 return false;
		 }
		 if($("#fbd"+i).val()!="" && !reg.test($("#fbd"+i).val())){
			 alert("峰表底[" + $("#fbd"+i).val() + "]不是合法的表底数值！");
			 $("#fbd"+i).focus().select();
			 return false;
		 }
		 if($("#pbd"+i).val()!="" && !reg.test($("#pbd"+i).val())){
			 alert("平表底[" + $("#pbd"+i).val() + "]不是合法的表底数值！");
			 $("#pbd"+i).focus().select();
			 return false;
		 }
		 if($("#gbd"+i).val()!="" && !reg.test($("#gbd"+i).val())){
			 alert("谷表底[" + $("#gbd"+i).val() + "]不是合法的表底数值！");
			 $("#gbd"+i).focus().select();
		 	return false;
		 }
	 }
	 
	 return true;
}

function callBD(ii){	//召测表底
	
	if(!ii)ii = 0;
	if(ii >= clpNum)return;
	
	var param = {};
	param.rtu_id = rtuId;
	param.mp_id = mpIds[ii];
	param.datatype = $("#dataType").val();
	
	var dtTm   		= $("#dateTime").val();
	param.ymd  	= dtTm.substring(0,4) + dtTm.substring(5,7) + dtTm.substring(8,10);
	
	loading.loading();
	addStringTaskOpDetail("正在向预付费服务发送信息...");
	
	var json_str = jsonString.json2String(param);
	
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag 	: true,
			userData1		: param.rtu_id,
			mpid			: param.mp_id,
			gsmflag 		: 0,
			dyCommReadData	: json_str,
			userData2 		: ComntUseropDef.YFF_READDATA
		},
		function(data) {			    	//回传函数
			loading.loaded();
			addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				addStringTaskOpDetail("接收预付费服务任务成功...");
				var json = eval('(' + data.dyCommReadData + ')');
				
				$("#zbd" + ii).val(json.cur_zyz);
				$("#jbd" + ii).val(json.cur_zyj);
				$("#fbd" + ii).val(json.cur_zyf);
				$("#pbd" + ii).val(json.cur_zyp);
				$("#gbd" + ii).val(json.cur_zyg);
				
				if (glo_opdetail_dlg == null || glo_opdetail_dlg.closed) {
					alert("召测 " + $("#mp_name" + ii).html() + " 数据成功!");
				}else{
					addStringTaskOpDetail("召测 " + $("#mp_name" + ii).html() + " 数据成功!");
				}
				
				calBdInfo();
				
				callBD(ii + 1);
				
			}else{
				addStringTaskOpDetail("接收预付费服务任务失败...");
				alert("通讯失败..");
			}
		});
}

function calBdInfo(){
	
	var zbd=0,jbd=0,fbd=0,pbd=0,gbd=0;
	for(var i = 0; i < clpNum; i++){
		zbd = round(parseFloat(zbd) + parseFloat($("#zbd"+i).val()==""?0:$("#zbd"+i).val()),3);
		jbd = round(parseFloat(jbd) + parseFloat($("#jbd"+i).val()==""?0:$("#jbd"+i).val()),3);
		fbd = round(parseFloat(fbd) + parseFloat($("#fbd"+i).val()==""?0:$("#fbd"+i).val()),3);
		pbd = round(parseFloat(pbd) + parseFloat($("#pbd"+i).val()==""?0:$("#pbd"+i).val()),3);
		gbd = round(parseFloat(gbd) + parseFloat($("#gbd"+i).val()==""?0:$("#gbd"+i).val()),3);
	}
	$("#bdInfo").html("总表底"+zbd+",尖表底"+jbd+",峰表底"+fbd+",平表底"+pbd+",谷表底"+gbd);
	$("#zbd").val(zbd);$("#jbd").val(jbd);$("#fbd").val(fbd);$("#pbd").val(pbd);$("#gbd").val(gbd);
}

function clearBD() {
	for(var i = 0; i < 3; i++){
		$("#zbd" + i).val("");
		$("#jbd" + i).val("");
		$("#fbd" + i).val("");
		$("#pbd" + i).val("");
		$("#gbd" + i).val("");
	}
}

function initFxdfGrid(){//发行电费  历史记录。
	var yff_grid_title= "序号,电费日期,发行日期,上次电费年月,算费表底日期,算费总表底,算费尖表底,算费峰表底,算费平表底,算费谷表底,电费余额,电费金额,补差标志,整月标志";
	
	mygrid = new dhtmlXGridObject("gridbox");
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("40,80,110,90,100,120,120,120,120,120,80,80,80,80,*");
	mygrid.setColAlign("center,center,center,center,center,right,right,right,right,right,right,right,right,right");
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.init();
	mygrid.setSkin("light");	
	
	mygrid.clearAll();
	
	var param = "{\"rtu_id\":\"" + rtuId + "\",\"mp_id\":\"" +mpId+ "\",\"cbday\":\"" + cbDay + "\",\"khDate\":\"" + khDate + "\"}";
	$.post( def.basePath + "ajaxdyjc/actPublishRate!getFxMoney.action",{result:param},	function(data) {	
		if (data.result != "") {
			var jsdata = eval('(' + data.result + ')');
			mygrid.parse(jsdata,"json");		
		}
	});
}

function initJtqlGrid(){//阶梯清零   历史记录。
	var yff_grid_title = "序号,阶梯清零日期,清零执行时间,上次清零日期,追补累计用电量,累计用电量,断电金额,断电金额2";
	
	mygrid = new dhtmlXGridObject("gridbox");
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("40,90,110,100,100,80,80,80,80,80,80,80,80,80,*");
	mygrid.setColAlign("center,center,center,center,center,right,right,right,right,right,right,right,right,right");
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.init();
	mygrid.setSkin("light");	
	
	mygrid.clearAll();
	
	var param = "{\"rtu_id\":\"" + rtuId + "\",\"mp_id\":\"" +mpId+ "\",\"cbday\":\"" + cbDay + "\"}"; 
	$.post( def.basePath + "ajaxdyjc/actPublishRate!getJtClear.action",{result:param},	function(data) {	
		if (data.result != "") {
			var jsdata = eval('(' + data.result + ')');
			mygrid.parse(jsdata,"json");		
		}
	});  
}


function doOperate(){
	for(var i = 0; i < clpNum; i++){
		if($("#zbd"+i).val() == ""){
			alert("[" + $("#mp_name" +i).html() + "]总表底 不能为空！");
			$("#zbd"+i).focus().select();
			return false;
		}
	}
	if(opType == 1 ){
		doFxdf();
	}else if(opType == 2){
		doJtRest();
	}
} 

function doFxdf(){//重新发行电费
	if(!confirm("确定发行？")){
		return;
	}
		
	var params = {};
	params.rtu_id = rtuId;
	params.mp_id = mpId;	
	var date = $("#dateTime").val();
	params.date = date.substring(0, 4) + date.substring(5,7) + date.substring(8,10);
	params.time = 0;
	for(var i = 0; i < 3; i++){
		eval("params.mp_id"  + i + "=mpIds[" + i + "]");
		eval("params.bd_zy"  + i + "=$(\"#zbd" + i +"\").val()");
		eval("params.bd_zyj" + i + "=$(\"#jbd" + i +"\").val()");
		eval("params.bd_zyf" + i + "=$(\"#fbd" + i +"\").val()");
		eval("params.bd_zyp" + i + "=$(\"#pbd" + i +"\").val()");
		eval("params.bd_zyg" + i + "=$(\"#gbd" + i +"\").val()");
	}
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			userData1		: rtuId,
			mpid			: mpId,
			gsmflag			: 0,
			dyOperReFxdf	: json_str,
			userData2		: ComntUseropDef.YFF_DYOPER_REFXDF
		},
		function(data) {			    	//回传函数
			if (data.result == "success") {
				alert("发行成功...");
				setDisabled(true);				
			}
			else {
				alert("发行失败...");
			}
		loading.loaded();
		}
	);	
	
}

function doJtRest(){//重新阶梯清零
	if(!confirm("确定清零？")){
		return;
	}
		
	var params = {};
	params.rtu_id = rtuId;
	params.mp_id = mpId;	
	var date = $("#dateTime").val();
	params.date = date.substring(0, 4) + date.substring(5,7) + date.substring(8,10);
	params.time = 0;
	for(var i = 0; i < 3; i++){
		eval("params.mp_id"  + i + "=mpIds[" + i + "]");
		eval("params.bd_zy"  + i + "=$(\"#zbd" + i +"\").val()");
		eval("params.bd_zyj" + i + "=$(\"#jbd" + i +"\").val()");
		eval("params.bd_zyf" + i + "=$(\"#fbd" + i +"\").val()");
		eval("params.bd_zyp" + i + "=$(\"#pbd" + i +"\").val()");
		eval("params.bd_zyg" + i + "=$(\"#gbd" + i +"\").val()");
	}
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			userData1		: rtuId,
			mpid			: mpId,
			gsmflag			: 0,
			dyOperReJtRest	: json_str,
			userData2		: ComntUseropDef.YFF_DYOPER_REJTREST
		},
		function(data) {			    	//回传函数
			if (data.result == "success") {
				alert("阶梯清零成功...");
				setDisabled(true);				
			}
			else {
				alert("阶梯清零失败...");
			}
		loading.loaded();
		}
	);	
	
}

