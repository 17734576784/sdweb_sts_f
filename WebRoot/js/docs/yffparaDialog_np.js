var params = window.dialogArguments;
var param = params.id.split(",");//param[0]:mpid, param[1]:rtuid,param[3]:describe

$(document).ready(function(){
	$("#meterdesc").val(param[2]);
	$("#rtuId").val(param[1]);
	$("#mpId").val(param[0]);
	
	loading.loading();
	initDiction();
	initDB();
	
	$("#xiugai").click(function(){
		if(check()){
			edit("edit");
		};
	});
	
	//更换所属片区
	$("#selarea").click(function(){
		modalDialog.height = 410;
		modalDialog.width = 250;
		modalDialog.url = "selArea.jsp";
		
		modalDialog.param = {aid : $("#areaId").val(), img_path_dialog : params.img_path};
		
		var rtn = modalDialog.show();
		if(!rtn)return;
		$("#areaId").val(rtn.id);
		$("#areadesc").val(rtn.desc);
	});
	
	//实时改变费率方案描述。
	$("#feeprojId").change(function(){
		var feeprojId = $("#feeprojId").find("option:selected").val();
		$.post(def.basePath + "ajaxdocs/actYffParaNP!getFeeprojDesc.action",{result:feeprojId},function(data){
			if(data.result !=""){
				$("#feeproj_desc").html(data.result);
			}
		});
	});
	
	$("#yffalarmId").change(function(){
		var yffalarmId = $("#yffalarmId").find("option:selected").val();
		$.post(def.basePath + "ajaxdocs/actYffParaNP!getAlarmDesc.action",{result:yffalarmId},function(data){
			if(data.result !=""){
				$("#yffalarm_desc").html(data.result);
			}
		});
	});
});


function initDiction(){
	//var text = '{item:["' + Dict.DICTITEM_YESFLAG + '","' + Dict.DICTITEM_NOCYCUT_MIN + '","' + Dict.DICTITEM_CB_CYCLE_TYPE + '","' + Dict.DICTITEM_PREPAYTYPE + '","' + Dict.DICTITEM_FEETYPE + '","' + Dict.DICTITEM_PAYTYPE + '","' + Dict.DICTITEM_CSSTAND + '","' + Dict.DICTITEM_YFFMONEY + '","' + Dict.DICTITEM_YFFMETERTYPE + '"]}';
	var text = '{item:["' + Dict.DICTITEM_YESFLAG + '","' + Dict.DICTITEM_NOCYCUT_MIN + '","' + Dict.DICTITEM_NPMETER_TYPE + '"]}';
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : text}, function(data){
		
		var json = eval('(' + data.result + ')');
		var iid = 0;
		var dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_YESFLAG);
		for ( var i = 0; i < dict.length; i++) {
			$("#nocycutFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			$("#powerupProt").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_NOCYCUT_MIN);
		for ( var i = 0; i < dict.length; i++) {
			$("#nocycutMin").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		
		
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_NPMETER_TYPE);
		for ( var i = 0; i < dict.length; i++) {
			$("#yffmeterType").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		
		
		setValue();	
	});
}

function initDB(){
	var tables = '{tables:["'+YDTable.TABLECLASS_YFFRATEPARA+'","'+YDTable.TABLECLASS_YFFALARMPARA+'"]}';
	$.post(def.basePath + "ajax/actCommon!getValueAndText.action",{tableName: tables},function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			
			var json0 = eval("json.rows[0]."+YDTable.TABLECLASS_YFFRATEPARA);
			var json1 = eval("json.rows[1]."+YDTable.TABLECLASS_YFFALARMPARA);
			
			for ( var i = 0; i < json0.length; i++) {
				$("#feeprojId").append("<option value="+json0[i].value+">"+json0[i].text+"</option>");
			}
			for ( var i = 0; i < json1.length; i++) {
				$("#yffalarmId").append("<option value="+json1[i].value+">"+json1[i].text+"</option>");
			}
				
		}
	});
}

function setValue(){//根据id,rtuid 取得一条记录。	
	if(param != undefined){
		var id = param[0], rtuid = param[1];
		$("#rtuId").val(rtuid);
		$("#mpId").val(id);
		$("#xiugai").attr("disabled",false);
		var field = "areaId,areadesc,commAddr,meterId,ctRatio,ctNumerator,ctDenominator,commPwd,powLimit,keyVersion,nocycutFlag,powerupProt,cryplinkId,yffmeterType,nocycutMin,feeprojId,yffalarmId,feeproj_desc,yffalarm_desc,feeBegindate";
		$.post(def.basePath + "ajaxdocs/actYffParaNP!getYffParaById.action", {result : id+","+rtuid}, function(data) {
			loading.loaded();
			var json = eval('(' + data.result + ')');
			var rowsplit = field.split(SDDef.SPLITCOMA);
			for(var i = 0; i < rowsplit.length; i ++){
				
				if(rowsplit[i] == "feeBegindate"){//格式化时间
					$("#"+rowsplit[i]).val(json.data[i]);
					dateFormat.date = json.data[i];
					$("#"+rowsplit[i] + "_").val(dateFormat.formatToYMD(0));
					continue;
				}
				if(rowsplit[i] == "feeproj_desc"){
					$("#feeproj_desc").html(json.data[i]);
				}
				if(rowsplit[i] == "yffalarm_desc"){
					$("#yffalarm_desc").html(json.data[i]);
				}
				$("#"+rowsplit[i]).val(json.data[i]);
			}
			//CT变比框赋值
			if($("#ctNumerator").val()>0&&$("#ctDenominator").val()>0&&$("#ctRatio").val()>0){
				$("#textct").val($("#ctNumerator").val()+"/"+$("#ctDenominator").val()+"="+$("#ctRatio").val());
			}
		});
	}
}


//修改
function edit(flag){
	
	var tmpvalue = $("#feeBegindate_").val();
	if(tmpvalue != ""){
		$("#feeBegindate").val(tmpvalue.substring(0, 4) + tmpvalue.substring(5, 7) + tmpvalue.substring(8, 10));
	}
	
	var params = $('#addorupdate').serialize();
	$.ajax({
		url      : def.basePath + "ajaxdocs/actYffParaNP!edit.action", //后台处理程序
		type     : 'post',					 							//数据发送方式
		data     : params, 												//要传递的数据；就是上面序列化的值
		success  : function(data) {								    	//回传函数
			if (data.result == "fail") {
				alert(update_fail);
			}else{
				window.returnValue = $("#rtuId").val()+"_"+$("#mpId").val();
				window.close();
			}
		}
	});
}

function editPTCT(PTorCT){//弹出窗口，编辑PT、CT.
	var fz,fm;
	var str;
	var cellfz,cellfm,cellRatio;
	fz=$("#"+PTorCT.toLowerCase()+"Numerator").val(); 
	fm=$("#"+PTorCT.toLowerCase()+"Denominator").val();
	modalDialog.height = 160;
	modalDialog.width = 200;
	modalDialog.url = "../dialog/editPTCT.jsp";
	modalDialog.param = PTorCT + "," + fz + "," + fm + ",电表档案";
	var str = modalDialog.show();
	if(str == undefined || str == null || str == ""){
		return;
	}	
 	var stri=str.split(",");
	$("#"+PTorCT.toLowerCase()+"Numerator").attr('value',stri[0]);
	$("#"+PTorCT.toLowerCase()+"Denominator").attr('value',stri[1]);
	$("#"+PTorCT.toLowerCase()+"Ratio").attr('value',stri[2]);
	$("#text"+PTorCT.toLowerCase()).val(stri[0]+"/"+stri[1]+"="+stri[2]);
}

function check() {
	if (!isNumber("powLimit",xdgl)) {
		return false;
	}
	if (!isNumber("nocycutMin",wcyzdddsj)) {
		return false;
	}
	if (!isNumber("cryplinkId",ssjmj)) {
		return false;
	}
	if (!isNumber("keyVersion", mybb,0,127, false)) {
		return false;
	}
	return true;
}