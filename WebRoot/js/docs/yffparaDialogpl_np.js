var params 		= window.dialogArguments;
var param 		= params.id.split("_");	//rtuId_mpId
var sel_rows 	= params.sel_rows;	//所有选中行的rtuId_mpId

var loaded=[false,false];
var chk0 = ["areaId","ctRatio","commPwd"];
var chk1 = [ "feeprojId", "yffalarmId","feeBegindate","powLimit","keyVersion","nocycutFlag","powerupProt","cryplinkId","nocycutMin"];
$(document).ready(function(){
	loading.loading();	
	initDiction();
	
	$("#xiugai").click(function(){
		if(check()){
			edit();
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
	
	//全选、全不选
	$("#chk0").click(function(){
		for(var i = 0; i < chk0.length; i++){
			$("#" + chk0[i] + "_").attr("checked", this.checked);
		}
	});
	
	$("#chk1").click(function(){
		for(var i = 0; i < chk1.length; i++){
			$("#" + chk1[i] + "_").attr("checked", this.checked);
		}
	});
});

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
			setValue();	
		}
	});
}

function initDiction(){
	var text = '{item:["' + Dict.DICTITEM_YESFLAG + '","' + Dict.DICTITEM_NOCYCUT_MIN + '"]}';
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : text}, function(data){
		
		initDB();
		
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
	});
}

function setValue(){//根据id,rtuid 取得一条记录。
  if(param != undefined){
	var id = param[1], rtuid = param[0];
	//$("#rtuId").val(rtuid);
	//$("#mpId").val(id);
	var field = "areaId,areadesc,ctRatio,ctNumerator,ctDenominator,commPwd,feeprojId,yffalarmId,feeBegindate_show,powLimit,keyVersion,nocycutFlag,powerupProt,cryplinkId,feeproj_desc,yffalarm_desc,nocycutMin";
	$.post(def.basePath  + "ajaxdocs/actYffParaNP!getYffParaByPlId.action", {result : id+","+rtuid}, function(data) {
		loading.loaded();
		var json = eval('(' + data.result + ')');
		var rowsplit = field.split(SDDef.SPLITCOMA);
		var i=0;
		while(i<rowsplit.length){
			if(rowsplit[i] == "feeBegindate_show"){//格式化时间
				dateFormat.date = json.data[i];
				$("#"+rowsplit[i]).val(dateFormat.formatToYMD(0));
				i++;
				continue;
			}
			if(rowsplit[i] == "feeproj_desc"){
				$("#feeproj_desc").html(json.data[i]);
			}
			if(rowsplit[i] == "yffalarm_desc"){
				$("#yffalarm_desc").html(json.data[i]);
			}
		$("#"+rowsplit[i]).val(json.data[i]);//向对应文本框填充值
		//window.setTimeout("setValue_('"+rowsplit[i]+"','"+json.data[i]+"')",1);
		i++;
		}
		//CT变比框赋值
		if($("#ctNumerator").val()>0&&$("#ctDenominator").val()>0&&$("#ctRatio").val()>0){
			$("#textct").val($("#ctNumerator").val()+"/"+$("#ctDenominator").val()+"="+$("#ctRatio").val());
		}
	});
  }
}

function setValue_(id,data){
	$("#"+id).val(data);
}

function setDateValue() {
	var tmpvalue = $("#feeBegindate_show").val();
	if(tmpvalue != ""){
		$("#feeBegindate").val(tmpvalue.substring(0, 4) + tmpvalue.substring(5, 7) + tmpvalue.substring(8, 10));
	}
}

//修改	
function edit(){
	setDateValue();
	var result = "";
	var basepara = "";
	var rtn = [];
	var rtn_i = 0;
	var choose_flag = 0;
	for(var i = 0; i < chk1.length; i++){
		//result:向后台传递更改数据	
		if($("#" + chk1[i] + "_").attr("checked")){
		    result += "," + chk1[i] + "=" + ($("#" + chk1[i]).val() == "" ? "null" : $("#" + chk1[i]).val());
		}
	}
	
	//获取基本参数选中项的值，未选中为空,在后台中不予更新
	for(var i = 0; i < chk0.length; i++){
		if($("#" + chk0[i] + "_").attr("checked")){
			if(chk0[i] == "ctRatio"){
				basepara += "," + ($("#ctNumerator").val() == "" ? "null" : $("#ctNumerator").val());  //ct分子
				basepara += "," + ($("#ctDenominator").val() == "" ? "null" : $("#ctDenominator").val()); //ct分母
			}
			basepara += "," + ($("#" + chk0[i]).val() == "" ? "null" : $("#" + chk0[i]).val());
			choose_flag++;//是否标识选中
		}else{
			if(chk0[i] == "ctRatio"){
				basepara += "," + ($("#ctNumerator").val() == "" ? "null" : $("#ctNumerator").val());  //ct分子
				basepara += "," + ($("#ctDenominator").val() == "" ? "null" : $("#ctDenominator").val()); //ct分母
			}
			basepara += "," + ($("#" + chk0[i]).val() == "" ? "null" : $("#" + chk0[i]).val());
		}
	}
	basepara = basepara.substring(1);
	if(result == ""&& choose_flag == 0){
		alert(update_con);
		return;
	}else{
		if(result != "") result = result.substring(1);
	}
	$.post(def.basePath + "ajaxdocs/actYffParaNP!plEdit.action",{pl : sel_rows, result : result, params: basepara},function(data){
		if(data.result == "fail"){
			alert(update_fail);
			return;
		}
		//window.returnValue = rtn;
		window.returnValue = params.id;
		window.close();
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