var rtuId = window.dialogArguments.rtuId;
var zjgId  = window.dialogArguments.zjgId;
var nodata="0";
var clpNum  = 1, zfFlag = "", asFlag = "";
var mp_id = [-1, -1, -1];
//param={"rtuId":30000003,"zjgId":1};
$(document).ready(function(){
	
	$("#zbd0").focus();
	
	$(document.body).keyup(function(){
		if(window.event.keyCode == 13){	//Enter
			closeWindow();
		}
	});

	//读取总加组信息
	readZjgpara();
	
});
function readZjgpara(){
	var params = '{"rtuId":' + rtuId + ',"zjgId":' + zjgId + '}';
	$.post(def.basePath + "ajaxspec/actImportBD!readZjgPara.action",{result:params},function(data){
		
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			clpNum = json.clp_num;	//测量点个数
			if(clpNum == 1){
				$("#mp_name0").html(json.mpName0);
				mp_id[0] = json.mpId0;
			}else if(clpNum == 2){
				$("#mp_name0").html(json.mpName0);
				mp_id[0] = json.mpId0;
				$("#mp_name1").html(json.mpName1);
				mp_id[1] = json.mpId1;
			}else if(clpNum == 3){
				$("#mp_name0").html(json.mpName0);
				mp_id[0] = json.mpId0;
				$("#mp_name1").html(json.mpName1);
				mp_id[1] = json.mpId1;
				$("#mp_name2").html(json.mpName2);
				mp_id[2] = json.mpId2;
			}
			
			zfFlag = json.zf_flag;
			asFlag = json.as_flag;
			InitMp(clpNum);
			
		}else{
			alert("读取总加组信息错误..");
			return;
		}
		chgDataSrc();
		
		var mydate=new Date();
		var myM=mydate.getMonth()>8?(mydate.getMonth()+1).toString():'0' + (mydate.getMonth()+1);
		var myD=mydate.getDate()>9?mydate.getDate().toString():'0' + mydate.getDate();
		$("#dateTime").val(mydate.getFullYear()+"年"+myM+"月"+myD+"日");
		
		$("#dataSrc").change(function(){chgDataSrc()});
		$("#dataType").change(function(){if(this.value==0){$("#dateTime").attr("style","display:none");}else{$("#dateTime").attr("style","display:blank");}});
		$("#btnRead").click(function(){readBD();});
		$("#btnCall").click(function(){callData();});
		$("#btnCallInfo").click(function(){showOpDetailDialog();});
		$("#btnOK").click(function(){calBdInfo();closeWindow();});
		$("#btnClear").click(function(){
			for(var i = 0; i < 3; i++){
				$("#zbd"+i).val("");
				$("#jbd"+i).val("");
				$("#fbd"+i).val("");
				$("#pbd"+i).val("");
				$("#gbd"+i).val("");
				$("#zwbd"+i).val("");
			}
			$("#bdInfo").html("");
		});
	});
}

function callData(ii){
	
	if(!ii)ii = 0;
	if(ii >= clpNum)return;
	
	var param = {};
	param.rtu_id = rtuId;
	param.zjg_id = mp_id[ii];
	
	if($("#dataType").val() == 0){
		if(zfFlag.substring(ii,ii+1) == "0"){
			param.datatype = ComntProtMsg.YFF_CALL_REAL_ZBD;
		}else{
			param.datatype = ComntProtMsg.YFF_CALL_REAL_FBD;
		}
	}else{
		if(zfFlag.substring(ii,ii+1) == "0"){
			param.datatype = ComntProtMsg.YFF_CALL_DAY_ZBD;
		}else{
			param.datatype = ComntProtMsg.YFF_CALL_DAY_FBD;
		}
		var dtTm   = $("#dateTime").val();
		var tDate  = dtTm.substring(0,4 )+ dtTm.substring(5,7) + dtTm.substring(8,10);
		param.ymd = tDate;
	}
	loading.loading();
	addStringTaskOpDetail("正在向预付费服务发送信息...");
	
	var json_str = jsonString.json2String(param);
	
	$.post( def.basePath + "ajaxoper/actCommGy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag 	: true,
			userData1		: param.rtu_id,
			zjgid			: param.zjg_id,
			gyCommReadData	: json_str,
			userData2 		: ComntUseropDef.YFF_READDATA
		},
		function(data) {			    	//回传函数
			loading.loaded();
			addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				addStringTaskOpDetail("接收预付费服务任务成功...");
				var json = eval('(' + data.gyCommReadData + ')');
				
				if(zfFlag.substring(ii,ii+1) == "0"){
					$("#zbd"+ii).val(round(json.cur_zyz,def.point));
					$("#jbd"+ii).val(round(json.cur_zyj,def.point));
					$("#fbd"+ii).val(round(json.cur_zyf,def.point));
					$("#pbd"+ii).val(round(json.cur_zyp,def.point));
					$("#gbd"+ii).val(round(json.cur_zyg,def.point));
					$("#zwbd"+ii).val(round(json.cur_zwz,def.point));
				}else{
					$("#zbd"+ii).val(round(json.cur_fyz,def.point));
					$("#jbd"+ii).val(round(json.cur_fyj,def.point));
					$("#fbd"+ii).val(round(json.cur_fyf,def.point));
					$("#pbd"+ii).val(round(json.cur_fyp,def.point));
					$("#gbd"+ii).val(round(json.cur_fyg,def.point));
					$("#zwbd"+ii).val(round(json.cur_fwz,def.point));
				}
				
				calBdInfo();
				
				callData(ii + 1);
				
			}else{
				addStringTaskOpDetail("接收预付费服务任务失败...");
				alert("通讯失败..");
			}
		});
}

function InitMp(clp_num){
	
	if(clp_num == 0){

		$("#zbd0").attr('disabled',true);	$("#zbd0").css('background','#e0e0e0');
		$("#jbd0").attr('disabled',true);	$("#jbd0").css('background','#e0e0e0');
		$("#fbd0").attr('disabled',true);	$("#fbd0").css('background','#e0e0e0');
		$("#pbd0").attr('disabled',true);	$("#pbd0").css('background','#e0e0e0');
		$("#gbd0").attr('disabled',true);	$("#gbd0").css('background','#e0e0e0');
		$("#zwbd0").attr('disabled',true);	$("#zwbd0").css('background','#e0e0e0');
		
		$("#zbd1").attr('disabled',true);	$("#zbd1").css('background','#e0e0e0');
		$("#jbd1").attr('disabled',true);	$("#jbd1").css('background','#e0e0e0');
		$("#fbd1").attr('disabled',true);	$("#fbd1").css('background','#e0e0e0');
		$("#pbd1").attr('disabled',true);	$("#pbd1").css('background','#e0e0e0');
		$("#gbd1").attr('disabled',true);	$("#gbd1").css('background','#e0e0e0');
		$("#zwbd1").attr('disabled',true);	$("#zwbd1").css('background','#e0e0e0');
		
		$("#zbd2").attr('disabled',true);	$("#zbd2").css('background','#e0e0e0');
		$("#jbd2").attr('disabled',true);	$("#jbd2").css('background','#e0e0e0');
		$("#fbd2").attr('disabled',true);	$("#fbd2").css('background','#e0e0e0');
		$("#pbd2").attr('disabled',true);	$("#pbd2").css('background','#e0e0e0');
		$("#gbd2").attr('disabled',true);	$("#gbd2").css('background','#e0e0e0');
		$("#zwbd2").attr('disabled',true);	$("#zwbd2").css('background','#e0e0e0');
	}else if(clp_num == 1){
		
		$("#zbd1").attr('disabled',true);	$("#zbd1").css('background','#e0e0e0');
		$("#jbd1").attr('disabled',true);	$("#jbd1").css('background','#e0e0e0');
		$("#fbd1").attr('disabled',true);	$("#fbd1").css('background','#e0e0e0');
		$("#pbd1").attr('disabled',true);	$("#pbd1").css('background','#e0e0e0');
		$("#gbd1").attr('disabled',true);	$("#gbd1").css('background','#e0e0e0');
		$("#zwbd1").attr('disabled',true);	$("#zwbd1").css('background','#e0e0e0');
		
		$("#zbd2").attr('disabled',true);	$("#zbd2").css('background','#e0e0e0');
		$("#jbd2").attr('disabled',true);	$("#jbd2").css('background','#e0e0e0');
		$("#fbd2").attr('disabled',true);	$("#fbd2").css('background','#e0e0e0');
		$("#pbd2").attr('disabled',true);	$("#pbd2").css('background','#e0e0e0');
		$("#gbd2").attr('disabled',true);	$("#gbd2").css('background','#e0e0e0');
		$("#zwbd2").attr('disabled',true);	$("#zwbd2").css('background','#e0e0e0');
	}else if(clp_num == 2){
		
		$("#zbd2").attr('disabled',true);	$("#zbd2").css('background','#e0e0e0');
		$("#jbd2").attr('disabled',true);	$("#jbd2").css('background','#e0e0e0');
		$("#fbd2").attr('disabled',true);	$("#fbd2").css('background','#e0e0e0');
		$("#pbd2").attr('disabled',true);	$("#pbd2").css('background','#e0e0e0');
		$("#gbd2").attr('disabled',true);	$("#gbd2").css('background','#e0e0e0');
		$("#zwbd2").attr('disabled',true);	$("#zwbd2").css('background','#e0e0e0');
	}else if(clp_num == 3){
		
		
	}
};

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
		
		$("#dataType").val(0);
		
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
	var rtnValue = {};

	rtnValue.zbd0 = $("#zbd0").val() == "" ? 0 : $("#zbd0").val();
	rtnValue.jbd0 = $("#jbd0").val() == "" ? 0 : $("#jbd0").val();
	rtnValue.fbd0 = $("#fbd0").val() == "" ? 0 : $("#fbd0").val();
	rtnValue.pbd0 = $("#pbd0").val() == "" ? 0 : $("#pbd0").val();
	rtnValue.gbd0 = $("#gbd0").val() == "" ? 0 : $("#gbd0").val();
	rtnValue.wbd0 = $("#zwbd0").val() == "" ? 0 : $("#zwbd0").val();
	rtnValue.zbd1 = $("#zbd1").val() == "" ? 0 : $("#zbd1").val();
	rtnValue.jbd1 = $("#jbd1").val() == "" ? 0 : $("#jbd1").val();
	rtnValue.fbd1 = $("#fbd1").val() == "" ? 0 : $("#fbd1").val();
	rtnValue.pbd1 = $("#pbd1").val() == "" ? 0 : $("#pbd1").val();
	rtnValue.gbd1 = $("#gbd1").val() == "" ? 0 : $("#gbd1").val();
	rtnValue.wbd1 = $("#zwbd1").val() == "" ? 0 : $("#zwbd1").val();
	rtnValue.zbd2 = $("#zbd2").val() == "" ? 0 : $("#zbd2").val();
	rtnValue.jbd2 = $("#jbd2").val() == "" ? 0 : $("#jbd2").val();
	rtnValue.fbd2 = $("#fbd2").val() == "" ? 0 : $("#fbd2").val();
	rtnValue.pbd2 = $("#pbd2").val() == "" ? 0 : $("#pbd2").val();
	rtnValue.gbd2 = $("#gbd2").val() == "" ? 0 : $("#gbd2").val();
	rtnValue.wbd2 = $("#zwbd2").val() == "" ? 0 : $("#zwbd2").val();
	rtnValue.td_bdinf = $("#bdInfo").html();
	
	rtnValue.mp_id0   = mp_id[0];
	rtnValue.mp_id1   = mp_id[1];
	rtnValue.mp_id2   = mp_id[2];
	
	var date = $("#dateTime").val();
	date = date.substring(0, 4) + date.substring(5,7) + date.substring(8,10);
	var src = $("#dataSrc").val();
	if(src == 0){
		rtnValue.date	  = date;
	} else {
		rtnValue.date	  = date;
	}
	
	rtnValue.zf_flag  = zfFlag;
	
	window.returnValue = rtnValue;
	window.close();
}

function readBD(){//从数据库中查询表底
	if(rtuId == "" || zjgId == ""){
		alert("请先选择要录入表底的电表。");
		return;
	}
	var dtTm   = $("#dateTime").val();
	var tDate  = dtTm.substring(0,4)+dtTm.substring(5,7)+dtTm.substring(8,10);
	var mpids  = mp_id[0] + "," + mp_id[1] + "," + mp_id[2];
	var params = '{"rtuId":' + rtuId + ',"mpIds":"' + mpids + '","clpNum":' + clpNum + ',"zfFlag":\"' + zfFlag + '\","tDate":' + tDate + '}';
	$.post(def.basePath + "ajaxspec/actImportBD!readBD.action",{result:params},function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			for(var i = 0; i < clpNum; i++){
				$("#zbd"+i).val(round(json.rows[i].zbd,def.point));
				$("#jbd"+i).val(round(json.rows[i].jbd,def.point));
				$("#fbd"+i).val(round(json.rows[i].fbd,def.point));
				$("#pbd"+i).val(round(json.rows[i].pbd,def.point));
				$("#gbd"+i).val(round(json.rows[i].gbd,def.point));
				$("#zwbd"+i).val(round(json.rows[i].wbd,def.point));

			}
		}else{
			for(var i = 0; i < clpNum; i++){
				$("#zbd"+i).val(nodata);
				$("#jbd"+i).val(nodata);
				$("#fbd"+i).val(nodata);
				$("#pbd"+i).val(nodata);
				$("#gbd"+i).val(nodata);
				$("#zwbd"+i).val(nodata);
			}
		}
		calBdInfo();
	});
}

function calBdInfo(){
	
	var zbd = 0, jbd = 0, fbd = 0, pbd = 0, gbd = 0, wbd = 0;

	for(var i = 0; i < clpNum; i++){
		var tmp_flag = asFlag.substring(i, i + 1) 
		if(tmp_flag == 0){
			zbd = round(parseFloat(zbd) + parseFloat($("#zbd"+i).val()==""?0:$("#zbd"+i).val()),def.point);
			jbd = round(parseFloat(jbd) + parseFloat($("#jbd"+i).val()==""?0:$("#jbd"+i).val()),def.point);
			fbd = round(parseFloat(fbd) + parseFloat($("#fbd"+i).val()==""?0:$("#fbd"+i).val()),def.point);
			pbd = round(parseFloat(pbd) + parseFloat($("#pbd"+i).val()==""?0:$("#pbd"+i).val()),def.point);
			gbd = round(parseFloat(gbd) + parseFloat($("#gbd"+i).val()==""?0:$("#gbd"+i).val()),def.point);
			wbd = round(parseFloat(wbd) + parseFloat($("#zwbd"+i).val()==""?0:$("#zwbd"+i).val()),def.point);

		}else{
			zbd = round(parseFloat(zbd) - parseFloat($("#zbd"+i).val()==""?0:$("#zbd"+i).val()),def.point);
			jbd = round(parseFloat(jbd) - parseFloat($("#jbd"+i).val()==""?0:$("#jbd"+i).val()),def.point);
			fbd = round(parseFloat(fbd) - parseFloat($("#fbd"+i).val()==""?0:$("#fbd"+i).val()),def.point);
			pbd = round(parseFloat(pbd) - parseFloat($("#pbd"+i).val()==""?0:$("#pbd"+i).val()),def.point);
			gbd = round(parseFloat(gbd) - parseFloat($("#gbd"+i).val()==""?0:$("#gbd"+i).val()),def.point);
			wbd = round(parseFloat(wbd) - parseFloat($("#zwbd"+i).val()==""?0:$("#zwbd"+i).val()),def.point);
		}
	}
	
	$("#bdInfo").html("总表底" + zbd + ",尖表底" + jbd + ",峰表底" + fbd + ",平表底" + pbd + ",谷表底" + gbd + ",无功表底"+ wbd + ".");
	$("#zbd").val(zbd);
	$("#jbd").val(jbd);
	$("#fbd").val(fbd);
	$("#pbd").val(pbd);
	$("#gbd").val(gbd);
	$("#wbd").val(wbd);

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

		 if($("#zwbd"+i).val()!="" && !reg.test($("#zwbd"+i).val())){
			 alert("无功表底[" + $("#gbd"+i).val() + "]不是合法的表底数值！");
			 $("#zwbd"+i).focus().select();
		 	return false;
		 }
	 }
	 return true;
}
