//param={"rtuId":30000003,"mpId":1};

var rtuId 	= window.dialogArguments.rtuId;
var mpId  	= window.dialogArguments.mpId;
var nodata  = "0";//无数据用0表示
var clpNum  = 1;
var mpIds 	= [-1,-1,-1];

$(document).ready(function(){
	
	$("#zbd0").focus();
	
	chgDataSrc();
	
	initDate();
	
	readMp();
	
	$("#dataType").change(function(){
		if(this.value == ComntProtMsg.YFF_CALL_REAL_ZBD){
			$("#dateTime").attr("style","display:none");
		}else{
			$("#dateTime").attr("style","display:blank");
		}
	});
	
	$("#dataSrc").change(function(){chgDataSrc()});
	$("#btnRead").click(function(){readBD();});
	$("#btnCall").click(function(){callBD();});
	$("#btnCallInfo").click(function(){showOpDetailDialog();});
	$("#btnOK").click(function(){closeWindow();});	
	$("#btnClear").click(function(){clearBD();});
	
	$(document.body).keyup(function(){
		if(window.event.keyCode == 13){	//Enter
			closeWindow();
		}
	});
	
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

function chgDataSrc(){//数据来源
	
	var src = $("#dataSrc").val();
	if(src == 0){//读取历史数据
		$("#btnRead").attr("style","display:blank");
		$("#btnCall").attr("style","display:none");
		$("#dateTime").attr("style","display:blank");
		$("#spandataDate").attr("style","display:blank");
		$("#dataType").attr("style","display:none");
		$("#spandataType").attr("style","display:none");
		$("#btnCallInfo").attr("style","display:none");
	}else{//召测电表数据
		
		$("#dataType").val(ComntProtMsg.YFF_CALL_REAL_ZBD);
		
		$("#btnCall").attr("style","display:blank");
		$("#btnRead").attr("style","display:none");
		$("#dataType").attr("style","display:blank");
		$("#spandataType").attr("style","display:blank");
		$("#dateTime").attr("style","display:none");
		$("#spandataDate").attr("style","display:none");
		$("#btnCallInfo").attr("style","display:blank");
	}
}

function closeWindow(){
	
	if(!checkBD()){
		return;
	}
	calBdInfo();
	
	var rtnValue = {};

	rtnValue.zbd0 = $("#zbd0").val() == "" ? 0 : $("#zbd0").val();
	rtnValue.jbd0 = $("#jbd0").val() == "" ? 0 : $("#jbd0").val();
	rtnValue.fbd0 = $("#fbd0").val() == "" ? 0 : $("#fbd0").val();
	rtnValue.pbd0 = $("#pbd0").val() == "" ? 0 : $("#pbd0").val();
	rtnValue.gbd0 = $("#gbd0").val() == "" ? 0 : $("#gbd0").val();
	
	rtnValue.zbd1 = $("#zbd1").val() == "" ? 0 : $("#zbd1").val();
	rtnValue.jbd1 = $("#jbd1").val() == "" ? 0 : $("#jbd1").val();
	rtnValue.fbd1 = $("#fbd1").val() == "" ? 0 : $("#fbd1").val();
	rtnValue.pbd1 = $("#pbd1").val() == "" ? 0 : $("#pbd1").val();
	rtnValue.gbd1 = $("#gbd1").val() == "" ? 0 : $("#gbd1").val();
	
	rtnValue.zbd2 = $("#zbd2").val() == "" ? 0 : $("#zbd2").val();
	rtnValue.jbd2 = $("#jbd2").val() == "" ? 0 : $("#jbd2").val();
	rtnValue.fbd2 = $("#fbd2").val() == "" ? 0 : $("#fbd2").val();
	rtnValue.pbd2 = $("#pbd2").val() == "" ? 0 : $("#pbd2").val();
	rtnValue.gbd2 = $("#gbd2").val() == "" ? 0 : $("#gbd2").val();
	
	rtnValue.td_bdinf = $("#bdInfo").html();
	
	rtnValue.mp_id0   = mpIds[0];
	rtnValue.mp_id1   = mpIds[1];
	rtnValue.mp_id2   = mpIds[2];
	rtnValue.clpNum   = clpNum;
	
	var date = $("#dateTime").val();
	date = date.substring(0, 4) + date.substring(5,7) + date.substring(8,10);
	var src = $("#dataSrc").val();
	
	if(src == 0){
		rtnValue.date	  = date;
	} else {
		if($("#dataType").val() == ComntProtMsg.YFF_CALL_REAL_ZBD) {
			rtnValue.date	  = 0;
		}else{
			rtnValue.date	  = date;
		}
	}
	
	window.returnValue = rtnValue;
	window.close();
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
	param.ymd  	= dtTm.substring(0,4)+dtTm.substring(5,7)+dtTm.substring(8,10);
	
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