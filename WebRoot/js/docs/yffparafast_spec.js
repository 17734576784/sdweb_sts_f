var params = window.dialogArguments;
var treeData = params.id;
var appType = "";
$(document).ready(function(){
	appType = SDDef.APPTYPE_ZB;
	initDiction();
	//加载模板列表
	initModelList();		
	//应用模板
	$("#chooseModel").click(function(){getModelByMid();});
	$("#save").click(function(){
		if(check()){
			if(confirm("确定使用该模板数据?")){
				saveDoc();
			}
		}
	});
});

function initDiction(){
	var text = '{item:["' + Dict.DICTITEM_YESFLAG + '","' + Dict.DICTITEM_USEFLAG + '","' + Dict.DICTITEM_CB_CYCLE_TYPE + '","' + Dict.DICTITEM_PREPAYTYPE + '","' + Dict.DICTITEM_FEETYPE + '","' + Dict.DICTITEM_PAYTYPE + '","' + Dict.DICTITEM_YFFMONEY + '","' + Dict.DICTITEM_YFFSPECCTRLTYPE + '","' + Dict.DICTITEM_EXECFLAG + '","' + Dict.DICTITEM_PRIZEFLAG + '","' + Dict.DICTITEM_CARDMETER_TYPE + '"]}';
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : text}, function(data){
		initDB();
		var json = eval('(' + data.result + ')');
		var iid = 0;
		var dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_YESFLAG);
		for ( var i = 0; i < dict.length; i++) {
			$("#ngloprotFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			//$("#feeChgf").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			//$("#jbfChgf").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			$("#fxdfFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			$("#cbjsFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_USEFLAG);
		for ( var i = 0; i < dict.length; i++) {
			$("#useGfh").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			$("#localMaincalcf").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			$("#useFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
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
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_YFFMONEY);
		for ( var i = 0; i < dict.length; i++) {
			$("#tzVal").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_YFFSPECCTRLTYPE);
		for ( var i = 0; i < dict.length; i++) {
			$("#yffctrlType").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_EXECFLAG);
		for ( var i = 0; i < dict.length; i++) {
			$("#powrateFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_PRIZEFLAG);
		for ( var i = 0; i < dict.length; i++) {
			$("#prizeFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_CARDMETER_TYPE);
		for ( var i = 0; i < dict.length; i++) {
			$("#cardmeterType").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
	});
	setValue();
}

function initDB(){
	var tables = '{tables:["' + YDTable.TABLECLASS_YFFRATEPARA + '","' + YDTable.TABLECLASS_YFFALARMPARA + '","cs_stand"]}';
	$.post(def.basePath + "ajax/actCommon!getValueAndText.action",{tableName: tables},function(data){
		
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			var iid = 0;
			var table = eval("json.rows["+(iid++)+"]."+YDTable.TABLECLASS_YFFRATEPARA);
			for ( var i = 0; i < table.length; i++) {
				
				$("#feeprojId").append("<option value="+table[i].value+">"+table[i].text+"</option>");
				$("#feeproj1Id").append("<option value="+table[i].value+">"+table[i].text+"</option>");
				$("#feeproj2Id").append("<option value="+table[i].value+">"+table[i].text+"</option>");
				
				//$("#feeChgid").append("<option value="+table[i].value+">"+table[i].text+"</option>");
				//$("#feeChgid1").append("<option value="+table[i].value+">"+table[i].text+"</option>");
				//$("#feeChgid2").append("<option value="+table[i].value+">"+table[i].text+"</option>");
			}
			table = eval("json.rows["+(iid++)+"]."+YDTable.TABLECLASS_YFFALARMPARA);
			for ( var i = 0; i < table.length; i++) {
				$("#yffalarmId").append("<option value="+table[i].value+">"+table[i].text+"</option>");
			}
			table = eval("json.rows["+(iid++)+"].cs_stand");
			for ( var i = 0; i < table.length; i++) {
				$("#csStand").append("<option value="+table[i].value+">"+table[i].text+"</option>");
			}
		}
	});
}

//获取模板详细内容,组成json格式
function getModelFiled(){
	var filed = "";
	var selectobj = $("form select");
	var inputobj  = $("form input");
	var key = "";
	var value = "";
	for(var i=0; i<selectobj.length; i++){
		key = selectobj[i].id;
		value = selectobj[i].value;
		filed += '"' + key + '":"' + value + '",';
	}
	for(var i=0; i<inputobj.length; i++){
		key = inputobj[i].id;
		value = inputobj[i].value;
		filed += '"' + key + '":"' + value + '",';
	}
	filed = filed.substring(0,filed.length-1);
	return filed;
}

//显示当前模板内容
function fillPage(json){
	var selectobj = $("form select");
	var inputobj  = $("form input");
	
	//遍历元素,对应赋值
	for(var key in json){
		for(var i=0; i<selectobj.length; i++){
			if(key == selectobj[i].id){
				$("#" + key).val(json[key]);
				break;
			} 
		}
		
		for(var i=0; i<inputobj.length; i++){
			if(key == inputobj[i].id){
				$("#" + key).val(json[key]);
				break;
			} 
		}
	}
};

//根据mid获取模板内容
function getModelByMid(){
	var mid = $("#model_list").val();
	if(mid == "-1"){
		return;
	} 
	loading.loading();
	$.post(
			def.basePath + "ajaxdocs/actYffDocModel!getModelById.action", 
			{
				flag		:   "",
				filed 		: 	"",
				mid			:	mid,
				describe	:	""
			}, 
			function(data){
				loading.loaded();
				if(data.flag == SDDef.SUCCESS){
					var json = eval('(' + data.filed + ')');
					fillPage(json);
				}
			}
	);
}

//加载模板列表
function initModelList(){
	if(!appType) return;
	$.post(
			def.basePath + "ajaxdocs/actYffDocModel!getModelIdList.action", 
			{
				filed 		: 	"",
				apptype		:	appType
			}, 
			function(data){
				var option = "<option value=-1>----未选择----</option>";
				if(data.filed != ""){
					var json = eval('(' + data.filed + ')');
					for(var i=0; i<json.rows.length; i++){
						option += "<option value=" + json.rows[i].value + ">" + json.rows[i].text + "</option>";
					}
					$("#model_list").html(option);
					getModelByMid();
				}
			}
	);
}

//填充终端总加组基本信息
function setValue(){//根据Zjgid,rtuid 取得一条记录。	
	if(treeData != undefined){
		var param = treeData.split(",");
		$("#zjg_rtuId").val(param[0]);
		$("#rtuPayCtrl_rtuId").val(param[0]);
		$("#zjgId").val(param[1]);
		$("#cusName").val(param[2]);
		$("#cusNo").val(param[3]);
		$("#cont_cap").val(param[4]);
		
		var zjgid = param[1];
		var rtuid = param[0];
		
		var field = "caclType,feectrlType,payType,yffalarmId,feeBegindate,protSt,protEd,ngloprotFlag,keyVersion,cryplinkId,tzVal,feeprojId,feeproj1Id,feeproj2Id,useGfh,hfhTime,hfhShutdown,plusTime,csStand,powrateFlag,prizeFlag,stopFlag,stopBegdate,stopEnddate,payAdd1,payAdd2,payAdd3,yffctrlType,feeChgf,feeChgid,feeChgid1,feeChgid2,feeChgdate,feeChgtime,jbfChgf,jbfChgval,jbfChgdate,jbfChgtime,cbCycleType,cbDayhour,jsDay,fxdfFlag,fxdfBegindate,localMaincalcf,writecardNo,cbjsFlag,cardmeterType,useFlag,yffbgDate,yffbgTime,sg186bgDate,sg186bgTime";
		$.post(def.basePath  + "ajaxdocs/actYffParaZ!getZjgModelParaById.action", {result : zjgid+","+rtuid, field : field}, function(data) {
			loading.loaded();
			if(data.result != ""){
			var json = eval('(' + data.result + ')');
			var rowsplit = field.split(SDDef.SPLITCOMA);
			
			for(var i = 0; i < rowsplit.length; i ++){
				
				if(rowsplit[i] == "feeChgdate" || rowsplit[i] == "jbfChgdate" || rowsplit[i] == "feeBegindate" || rowsplit[i] == "fxdfBegindate" || rowsplit[i] == "stopBegdate" || rowsplit[i] == "stopEnddate"){
					
					dateFormat.date = json.data[i];
					$("#"+rowsplit[i]+"F").val(dateFormat.formatToYMD(0));
				}
				if(rowsplit[i] == "feeChgtime" || rowsplit[i] == "jbfChgtime"){
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
				
				if(rowsplit[i] == "yffbgDate"||rowsplit[i] == "sg186bgDate"){
					dateFormat.date = json.data[i];
					$("#"+rowsplit[i]+"F").val(dateFormat.formatToYMD(0));
				}
				if(rowsplit[i] == "yffbgTime"||rowsplit[i] == "sg186bgTime"){
					dateFormat.date = json.data[i];
					$("#"+rowsplit[i]+"F").val(dateFormat.formatToHMS(0));
				}
				
				window.setTimeout("setValue_('"+rowsplit[i]+"','"+json.data[i]+"')",1);
			}
		}
		});
	}
}

//增加setValue_()方法
function setValue_(id,data){
	$("#"+id).val(data);
}

//保存档案
function saveDoc(){
	loading.loading();
	setDateValue();
	var params = $('#addorupdate').serialize();
	$.ajax({
		url      : def.basePath + "ajaxdocs/actYffParaZ!fastAddDoc.action", //后台处理程序
		type     : 'post',					 								//数据发送方式
		data     : params, 													//要传递的数据；就是上面序列化的值
		success  : function(data) {								    		//回传函数
			loading.loaded();
			if (data.result == "fail") {
				alert("预付费档案保存失败！");
			}else{
				window.returnValue = true;
				alert("预付费档案保存成功！");
			}
			window.close();
		}
	});
}

function setDateValue() {
	
	var tmp = ["yffbgDate", "sg186bgDate","fxdfBegindate","feeBegindate"];
	
	for ( var i = 0; i < tmp.length; i++) {
		var tmpvalue = $("#" + tmp[i] + "F").val();
		if (tmpvalue != "") {
			$("#" + tmp[i]).val(tmpvalue.substring(0, 4) + tmpvalue.substring(5, 7) + tmpvalue.substring(8, 10));
		}else{
			$("#" + tmp[i]).val(0);
		}
	}
	
	tmp = [ "yffbgTime", "sg186bgTime"];
	for ( var i = 0; i < tmp.length; i++) {
		var tmpvalue = $("#" + tmp[i] + "F").val();
		if (tmpvalue != "") {
			$("#" + tmp[i]).val(tmpvalue.substring(0, 2) + tmpvalue.substring(3, 5) + tmpvalue.substring(6, 8));
		}
	}
	
	tmp = ["cbDayhour"];
	for ( var i = 0; i < tmp.length; i++) {
		var tmpvalue = $("#" + tmp[i] + "F").val();
		
		if (tmpvalue != "") {
			$("#" + tmp[i]).val(tmpvalue.substring(0, 2) + tmpvalue.substring(3, 5));
		}else{
			$("#" + tmp[i]).val(0);
		}
	}
}

function check() {
	if (!isDbl("payAdd1", fjz+"1", false)) {
		return false;
	}
	if (!isDbl("payAdd2", fjz+"2", false)) {
		return false;
	}
	if (!isDbl("payAdd3", fjz+"3", false)) {
		return false;
	}
	if (!isNumber("hfhShutdown", gfhddz, false)) {
		return false;
	}
	
	if (!isNumber("protSt", zdbdks,0,25, false)) { // id, describe, min, max, required
		return false;
	}
	if (!isNumber("protEd", zdbdjs,0,25, false)) {
		return false;
	}
	if (!isNumber("hfhTime", gfhsj,0,32767, false)) {
		return false;
	}
	
	if (!isNumber("plusTime", fhmcbh,0,32767, false)) {
		return false;
	}
	
	return true;
}

