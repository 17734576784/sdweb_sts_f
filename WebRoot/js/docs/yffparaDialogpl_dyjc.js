var params 		= window.dialogArguments;
var param 		= params.id.split("_");
var sel_rows 	= params.sel_rows;
//var txml;
var loaded=[false,false];
var chk1 = [ "caclType", "feectrlType", "payType", "feeprojId", "yffmeterType",
		     "yffalarmId", "keyVersion", "cryplinkId", "tzVal", "protSt", "protEd",
		     "ngloprotFlag", "localMaincalcf", "feeBegindateF", "jtCycleMdF"],
	chk2 = ["feeChgf","feeChgdateF","feeChgtimeF","feeChgid"],
    //新增发行电费档案
    chk3 = ["cbCycleType","cbDayhourF","fxdfFlag","fxdfBegindateF"];
$(document).ready(function(){
	
	loading.loading();
	
	initDiction();
	
	$("#xiugai").click(function(){
		if(check()){
			edit();
		};
	});
	
	$("#chk1").click(function(){
		for(var i = 0; i < chk1.length; i++){
			$("#" + chk1[i] + "_").attr("checked", this.checked);
		}
	});
	$("#chk2").click(function(){
		for(var i = 0; i < chk2.length; i++){
			$("#" + chk2[i] + "_").attr("checked", this.checked);
		}
	});
	$("#chk3").click(function(){
		for(var i = 0; i < chk3.length; i++){
			$("#" + chk3[i] + "_").attr("checked", this.checked);
		}
	});
	$("#feectrlType_").click(function(){
		$("#payType_").attr("checked",this.checked);
	});
	$("#payType_").click(function(){
		$("#feectrlType_").attr("checked",this.checked);
	});
});

function initDB(){
	//{tables:[table1:"table1",table2:"table2"]}
	var tables = '{tables:["'+YDTable.TABLECLASS_YFFRATEPARA+'","'+YDTable.TABLECLASS_YFFALARMPARA+'"]}';
	$.post(def.basePath + "ajax/actCommon!getValueAndText.action",{tableName: tables},function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			
			var json0 = eval("json.rows[0]."+YDTable.TABLECLASS_YFFRATEPARA);
			var json1 = eval("json.rows[1]."+YDTable.TABLECLASS_YFFALARMPARA);
			
			for ( var i = 0; i < json0.length; i++) {
				$("#feeprojId").append("<option value="+json0[i].value+">"+json0[i].text+"</option>");
				$("#feeChgid").append("<option value="+json0[i].value+">"+json0[i].text+"</option>");
			}
			for ( var i = 0; i < json1.length; i++) {
				$("#yffalarmId").append("<option value="+json1[i].value+">"+json1[i].text+"</option>");
			}
			
			setValue();	
		}
	});
}

function initDiction(){
	var text = '{item:["' + Dict.DICTITEM_YESFLAG + '","' + Dict.DICTITEM_USEFLAG + '","' + Dict.DICTITEM_CB_CYCLE_TYPE + '","' + Dict.DICTITEM_PREPAYTYPE + '","' + Dict.DICTITEM_FEETYPE + '","' + Dict.DICTITEM_PAYTYPE + '","' + Dict.DICTITEM_CSSTAND + '","' + Dict.DICTITEM_YFFMONEY + '","' + Dict.DICTITEM_YFFMETERTYPE + '"]}';
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : text}, function(data){
		
		initDB();
		
		var json = eval('(' + data.result + ')');
		var iid = 0;
		var dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_YESFLAG);
		for ( var i = 0; i < dict.length; i++) {
			$("#ngloprotFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			$("#feeChgf").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			$("#fxdfFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_USEFLAG);
		for ( var i = 0; i < dict.length; i++) {
			$("#localMaincalcf").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			$("#useGfh").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_CB_CYCLE_TYPE);
		for ( var i = 0; i < dict.length; i++) {
			$("#cbCycleType").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_PREPAYTYPE);
		for ( var i = 0; i < dict.length; i++) {
			$("#feectrlType").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_FEETYPE);
		for ( var i = 0; i < dict.length; i++) {
			$("#caclType").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_PAYTYPE);
		for ( var i = 0; i < dict.length; i++) {
			$("#payType").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_CSSTAND);
		for ( var i = 0; i < dict.length; i++) {
			$("#csStand").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_YFFMONEY);
		for ( var i = 0; i < dict.length; i++) {
			$("#tzVal").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_YFFMETERTYPE);
		for ( var i = 0; i < dict.length; i++) {
			$("#yffmeterType").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		
	});
}

function setValue(){//根据id,rtuid 取得一条记录。

	var id = param[1], rtuid = param[0];
	$("#rtuId").val(rtuid);
	$("#mpId").val(id);
	//var field = "rtuId,mpId,feectrlType,caclType,payType,feeprojId,yffalarmId,protSt,protEd,ngloprotFlag,powerRela1,powerRela2,powerRelask1,powerRelask2,keyVersion,yffmeterType,cryplinkId,payAdd,tzVal,powerRelaf,feeChgf,feeChgid,feeChgdate,feeChgtime,prepayflag";
	var field =   "rtuId,mpId,feectrlType,caclType,payType,feeprojId,yffalarmId,feeBegindate,protSt,protEd,ngloprotFlag,powerRela1,powerRela2,powerRelask1,powerRelask2,keyVersion,yffmeterType,cryplinkId,payAdd,tzVal,powerRelaf,feeChgf,feeChgid,feeChgdate,feeChgtime,prepayflag,jtCycleMd,cbCycleType,cbDayhour,fxdfFlag,fxdfBegindate,localMaincalcf";
	$.post(def.basePath  + "ajaxdocs/actYffPara!getYffParaById.action", {result : id+","+rtuid, field : field}, function(data) {
		loading.loaded();
		var json = eval('(' + data.result + ')');
		var rowsplit = field.split(SDDef.SPLITCOMA);
		for(var i = 0; i < rowsplit.length; i ++){
			/*
			if (rowsplit[i] == "feectrlType" ) {
				if(json.data[i] == 0){
					$(txml).find("column[type_name='" + Dict.DICTITEM_PAYTYPE + "']>option").each(function(){
						if ($(this).attr("id") != 2) {
							$("#payType").append("<option value="+$(this).attr("id")+">"+$(this).text()+"</option>");	
						}
					});
				}
				else{
					$(txml).find("column[type_name='" + Dict.DICTITEM_PAYTYPE + "']>option").each(function(){
						if ($(this).attr("id") == 2) {
							$("#payType").append("<option value="+$(this).attr("id")+">"+$(this).text()+"</option>");	
						}
					});
				}
			}
			*/
			if(rowsplit[i] == "feeChgdate" || rowsplit[i] == "feeBegindate" || rowsplit[i] == "fxdfBegindate"){
				dateFormat.date = json.data[i];
				$("#"+rowsplit[i]+"F").val(dateFormat.formatToYMD(0));
			}
			if(rowsplit[i] == "feeChgtime"){
				dateFormat.date = json.data[i];
				$("#"+rowsplit[i]+"F").val(dateFormat.formatToHMS(0));
			}
			if(rowsplit[i] == "cbDayhour" && json.data[i] != "" && json.data[i] != "0" ){
				var tmpdt=json.data[i];
				if(tmpdt.length<4){
					tmpdt = "0000" + tmpdt;
					tmpdt=tmpdt.substring(tmpdt.length-4,tmpdt.length);
				} 
				$("#"+rowsplit[i]+"F").val(tmpdt.substring(0,2) + "日" + tmpdt.substring(2,4) + "时");
			}			
			if(rowsplit[i] == "jtCycleMd"  && json.data[i] != ""){
				var tmpdt=json.data[i];
				if(tmpdt.length<4){
					tmpdt = "0000" + tmpdt;
					tmpdt=tmpdt.substring(tmpdt.length-4,tmpdt.length);
				} 
				$("#"+rowsplit[i]+"F").val(tmpdt.substring(0,2) + "月" + tmpdt.substring(2,4) + "日");
			}
			window.setTimeout("setValue_('"+rowsplit[i]+"','"+json.data[i]+"')",1);
			
			//$("#"+rowsplit[i]).val(json.data[i]);
		}
		if($("#powerRelaf").val() != 1){
			$("#powerRela1").attr("disabled",true); $("#powerRela1").val(-1);
			$("#powerRela2").attr("disabled",true);	$("#powerRela2").val(-1);
			$("#powerRela1_text").css("color","#666");
			$("#powerRela2_text").css("color","#666");
		}else{
			$("#powerRela1").attr("disabled",false);
			$("#powerRela2").attr("disabled",false);
			$("#powerRela1_text").css("color","#000");
			$("#powerRela2_text").css("color","#000");
		}
	});
}

function setValue_(id,data){
	$("#"+id).val(data);
}

function setDateValue() {//给隐含域赋值
    var tmp = ["feeChgdate","fxdfBegindate","feeBegindate"];
	
	for ( var i = 0; i < tmp.length; i++) {
		var tmpvalue = $("#" + tmp[i] + "F").val();
		if (tmpvalue != "") {
			$("#" + tmp[i]).val(tmpvalue.substring(0, 4) + tmpvalue.substring(5, 7) + tmpvalue.substring(8, 10));
		}
		else{//如果为空 赋值隐含域为零
			$("#" + tmp[i]).val("");
		}
	}
	tmp = ["feeChgtime"];
	for ( var i = 0; i < tmp.length; i++) {
		var tmpvalue = $("#" + tmp[i] + "F").val();
		if (tmpvalue != "") {
			var time = tmpvalue.substring(0, 2) + tmpvalue.substring(3, 5) + tmpvalue.substring(6, 8);
			if(time.substring(0,1) == 0){
				time = time.substring(1);
			}
			
			$("#" + tmp[i]).val(time);
		}
		else{
			$("#" + tmp[i]).val();
		}
	}
	
	//当抄表日判断第一位是否为零 是则去掉
	tmp = ["cbDayhour","jtCycleMd"];
	for ( var i = 0; i < tmp.length; i++) {
		var tmpvalue = $("#" + tmp[i] + "F").val();
		
		if (tmpvalue != "") {
			var time = tmpvalue.substring(0, 2) + tmpvalue.substring(3, 5)
			if(time.substring(0,1) ==0){
				time = time.substring(1);
			}
			$("#" + tmp[i]).val(time);
			//$("#" + tmp[i]).val(tmpvalue.substring(0, 2) + tmpvalue.substring(3, 5));
		}else{
			$("#" + tmp[i]).val("");
		}
	}
}

function edit(){//修改	
	setDateValue();
	var result = "";
	var rtn = [];
	var rtn_i = 0;
	for(var i = 0; i < chk1.length; i++){
		if(chk1[i] == "caclType" || chk1[i] == "feectrlType" || chk1[i] == "payType" || chk1[i] == "feeprojId" || chk1[i] == "yffalarmId" || chk1[i] == "ngloprotFlag"||chk1[i]=="localMaincalcf"){
			if($("#" + chk1[i] + "_").attr("checked")){
				rtn[rtn_i++] = $("#" + chk1[i]).find("option:selected").text();
			}else{
				rtn[rtn_i++] = null;
			}
		}else if(chk1[i] == "keyVersion"){//密钥版本
			if($("#" + chk1[i] + "_").attr("checked")){
				rtn[rtn_i++] = $("#" + chk1[i]).val();
			}else{
				rtn[rtn_i++] = null;
			}
		}
		if($("#" + chk1[i] + "_").attr("checked")){
			if(chk1[i]=="feeBegindateF"||chk1[i]=="jtCycleMdF"){
				var tmp = chk1[i].substring(0, chk1[i].length - 1);
				result += "," + tmp + "=" + ($("#" + tmp).val() === "" ? "null" : $("#" + tmp).val());
		    }else{
		    		result += "," + chk1[i] + "=" + ($("#" + chk1[i]).val() == "" ? "null" : $("#" + chk1[i]).val());
		    } 
		}
	}
	
	for(var i = 0; i < chk2.length; i++){
		if($("#" + chk2[i] + "_").attr("checked")){
			if(chk2[i] == "feeChgdateF" || chk2[i] == "feeChgtimeF"){
				var tmp = chk2[i].substring(0, chk2[i].length - 1);
				//if(tmp.length<6){}
				result += "," + tmp + "=" + ($("#" + tmp).val() === "" ? "null" : $("#" + tmp).val());
		    }else{
		    	result += "," + chk2[i] + "=" + ($("#" + chk2[i]).val() == "" ? "null" : $("#" + chk2[i]).val());
		    }
		}
    }
	
	for(var i = 0; i < chk3.length; i++){
		if($("#" + chk3[i] + "_").attr("checked")){
			if(chk3[i] == "cbDayhourF" || chk3[i] == "fxdfBegindateF"){
				var tmp = chk3[i].substring(0, chk3[i].length - 1);
				result += "," + tmp + "=" + ($("#" + tmp).val() === "" ? "null" : $("#" + tmp).val());
		    }else{
		    	result += "," + chk3[i] + "=" + ($("#" + chk1[i]).val() == "" ? "null" : $("#" + chk3[i]).val());
		    }
		}
    }
	if(result == ""){
		alert(update_con);
		return;
	}else{
		result = result.substring(1);
	}
	$.post(def.basePath + "ajaxdocs/actYffPara!plAddOrEdit.action",{pl : sel_rows, result : result},function(data){
		if(data.result == "fail"){
			alert(update_fail);
			return;
		}
		
		window.returnValue = rtn;
		window.close();
	});
}

function check() {

	if (!isNumber("protSt", bdks,0,24, false)) { // id, describe, min, max, required
		return false;
	}

	if (!isNumber("protEd", bdjs,0,24, false)) {
		return false;
	}
	if (!isNumber("keyVersion", mybb,0,127, false)) {
		return false;
	}
	return true;
}