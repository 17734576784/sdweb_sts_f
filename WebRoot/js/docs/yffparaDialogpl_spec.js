var params = window.dialogArguments;
var param = params.id.split(",");
var sel_rows = params.pl;

var chk1 = ["caclType","feectrlType","payType","yffalarmId","yffctrlType","tzVal","feeprojId","feeproj1Id","feeproj2Id","protSt","protEd","ngloprotFlag","keyVersion","cryplinkId","localMaincalcf","feeBegindateF","feeBegindate","cardmeterType"], 
	chk2 = ["payAdd1","payAdd2","payAdd3"],
	chk3 = ["feeChgf","feeChgdateF","feeChgtimeF","feeChgid","feeChgid1","feeChgid2"],
	chk4 = ["jbfChgf","jbfChgdateF","jbfChgtimeF","jbfChgval"],
    //新增发行电费档案
    chk5 = ["cbCycleType","cbDayhourF","cbjsFlag","fxdfFlag","fxdfBegindateF"],
    //力调
    chk6 = ["powrateFlag","prizeFlag","csStand"],
    //报停
    chk7 = ["stopFlag","stopBegdateF","stopEnddateF"];

$(document).ready(function(){
	
	loading.loading();
	initDiction();
	
	$("#xiugai").click(function(){
		if(check()){
			edit();
		};
	});

	$("#ck1").click(function(){
		for(var i = 0; i < chk1.length; i++){
			$("#" + chk1[i] + "_").attr("checked", this.checked);
		}
	});
	$("#ck2").click(function(){
		for(var i = 0; i < chk2.length; i++){
			$("#" + chk2[i] + "_").attr("checked", this.checked);
		}
	});
	$("#ck3").click(function(){
		for(var i = 0; i < chk3.length; i++){
			$("#" + chk3[i] + "_").attr("checked", this.checked);
		}
	});
	$("#ck4").click(function(){
		for(var i = 0; i < chk4.length; i++){
			$("#" + chk4[i] + "_").attr("checked", this.checked);
		}
	});
	//新增
	$("#ck5").click(function(){
		for(var i = 0; i < chk5.length; i++){
			$("#" + chk5[i] + "_").attr("checked", this.checked);
		}
	});
	
	$("#ck6").click(function(){
		for(var i = 0; i < chk6.length; i++){
			$("#" + chk6[i] + "_").attr("checked", this.checked);
		}
	});

	$("#ck7").click(function(){
		for(var i = 0; i < chk7.length; i++){
			$("#" + chk7[i] + "_").attr("checked", this.checked);
		}
	});
	
	$("#feectrlType_").click(function(){
		$("#payType_").attr("checked",this.checked);
	});
	$("#payType_").click(function(){
		$("#feectrlType_").attr("checked",this.checked);
	});
	
	label_change();
	
});

function label_change(){
	$("#jbcs").click(function(){
		$("#d_jbcs").css("display","block");
		$("#d_lt").css("display","none");
		$("#d_qtcs").css("display","none");
		
		$("#jbcs_l").attr("class","titlel");
		$("#jbcs").attr("class","titlem");
		$("#jbcs_r").attr("class","titler");
		
		$("#qtcs_l").attr("class","titlel1");
		$("#qtcs").attr("class","titlem1");
		$("#qtcs_r").attr("class","titler1");
	});
	
	$("#qtcs").click(function(){
		$("#d_jbcs").css("display","none");
		$("#d_lt").css("display","none");
		$("#d_qtcs").css("display","block");
		
		$("#jbcs_l").attr("class","titlel1");
		$("#jbcs").attr("class","titlem1");
		$("#jbcs_r").attr("class","titler1");
		
		$("#qtcs_l").attr("class","titlel");
		$("#qtcs").attr("class","titlem");
		$("#qtcs_r").attr("class","titler");
	});
}

function initDB(){
	//{tables:[table1:"table1",table2:"table2"]}
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
				
				$("#feeChgid").append("<option value="+table[i].value+">"+table[i].text+"</option>");
				$("#feeChgid1").append("<option value="+table[i].value+">"+table[i].text+"</option>");
				$("#feeChgid2").append("<option value="+table[i].value+">"+table[i].text+"</option>");
			}
			table = eval("json.rows["+(iid++)+"]."+YDTable.TABLECLASS_YFFALARMPARA);
			for ( var i = 0; i < table.length; i++) {
				$("#yffalarmId").append("<option value="+table[i].value+">"+table[i].text+"</option>");
			}
			table = eval("json.rows["+(iid++)+"].cs_stand");
			for ( var i = 0; i < table.length; i++) {
				$("#csStand").append("<option value="+table[i].value+">"+table[i].text+"</option>");
			}
			
			setValue();	
		}
	});
}

function initDiction(){
	var text = '{item:["' + Dict.DICTITEM_YESFLAG + '","' + Dict.DICTITEM_USEFLAG + '","' + Dict.DICTITEM_CB_CYCLE_TYPE + '","' + Dict.DICTITEM_PREPAYTYPE + '","' + Dict.DICTITEM_FEETYPE + '","' + Dict.DICTITEM_PAYTYPE + '","' + Dict.DICTITEM_YFFMONEY + '","' + Dict.DICTITEM_YFFSPECCTRLTYPE  + '","' + Dict.DICTITEM_EXECFLAG + '","' + Dict.DICTITEM_PRIZEFLAG + '","' + Dict.DICTITEM_STOPFLAG + '","' + Dict.DICTITEM_CARDMETER_TYPE + '"]}';
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : text}, function(data){
		
		initDB();
		
		var json = eval('(' + data.result + ')');
		var iid = 0;
		var dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_YESFLAG);
		for ( var i = 0; i < dict.length; i++) {
			$("#ngloprotFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			$("#feeChgf").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			$("#jbfChgf").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			$("#fxdfFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			$("#cbjsFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_USEFLAG);
		for ( var i = 0; i < dict.length; i++) {
			$("#useGfh").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			$("#localMaincalcf").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
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
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_STOPFLAG);
		for ( var i = 0; i < dict.length; i++) {
			$("#stopFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_CARDMETER_TYPE);
		for ( var i = 0; i < dict.length; i++) {
			$("#cardmeterType").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
	});
}

function setValue(){//根据Zjgid,rtuid 取得一条记录。
	
	if(param != undefined){
		var id = param[1], rtuid = param[0];
		$("#rtuId").val(rtuid);
		if(id == ""){//父页面点击“新增”时
		
		}else{
			$("#zjgId").val(id);
			
			var field ="rtuId,zjgId,caclType,feectrlType,payType,yffalarmId,feeBegindate,protSt,protEd,ngloprotFlag,keyVersion,cryplinkId,tzVal,feeprojId,feeproj1Id,feeproj2Id,useGfh,hfhTime,hfhShutdown,plusTime,csStand,powrateFlag,prizeFlag,stopFlag,stopBegdate,stopEnddate,payAdd1,payAdd2,payAdd3,yffctrlType,feeChgf,feeChgid,feeChgid1,feeChgid2,feeChgdate,feeChgtime,jbfChgf,jbfChgval,jbfChgdate,jbfChgtime,cbCycleType,cbDayhour,jsDay,fxdfFlag,fxdfBegindate,localMaincalcf,writecardNo,cbjsFlag,cardmeterType";
			$.post(def.basePath  + "ajaxdocs/actYffParaZ!getYffParaById.action", {result : rtuid+","+ id, field : field}, function(data) {
				loading.loaded();
				if(data.result != ""){
				var json = eval('(' + data.result + ')');
				var rowsplit = field.split(SDDef.SPLITCOMA);
				
				for(var i = 0; i < rowsplit.length; i ++){
					//useGfh,hfhTime,hfhShutdown,plusTime,writecardNo
					if(rowsplit[i] == "useGfh" || rowsplit[i] == "hfhTime" || rowsplit[i] == "hfhShutdown" || rowsplit[i] == "plusTime" || rowsplit[i] == "writecardNo")continue;
					
					if(rowsplit[i] == "feeChgdate" || rowsplit[i] == "jbfChgdate" || rowsplit[i] == "feeBegindate" || rowsplit[i] == "fxdfBegindate" || rowsplit[i] == "stopBegdate" || rowsplit[i] == "stopEnddate"){
						dateFormat.date = json.data[i];
						$("#"+rowsplit[i]+"F").val(dateFormat.formatToYMD(0));
					}
					if(rowsplit[i] == "feeChgtime" || rowsplit[i] == "jbfChgtime"){
						dateFormat.date = json.data[i];
						$("#"+rowsplit[i]+"F").val(dateFormat.formatToHMS(0));
					}
					if(rowsplit[i] == "cbDayhour" && json.data[i] != ""  && json.data[i] != "0" ){
						var tmpdt=json.data[i];
						if(tmpdt.length<4){
							tmpdt = "0000" + tmpdt;
							tmpdt=tmpdt.substring(tmpdt.length-4,tmpdt.length);
						}
						$("#"+rowsplit[i]+"F").val(tmpdt.substring(0,2) + "日" + tmpdt.substring(2,4) + "时");
					}
					window.setTimeout("setValue_('"+rowsplit[i]+"','"+json.data[i]+"')",1);
//					$("#"+rowsplit[i]).val(json.data[i]); 
				}
			}
			});
		}
	}
}

function setValue_(id,data){
	$("#"+id).val(data);
}

function setDateValue() {
	
	var tmp = ["feeChgdate","jbfChgdate","fxdfBegindate","feeBegindate","stopBegdate","stopEnddate"];
	
	for ( var i = 0; i < tmp.length; i++) {
		var tmpvalue = $("#" + tmp[i] + "F").val();
		if (tmpvalue != "") {
			$("#" + tmp[i]).val(tmpvalue.substring(0, 4) + tmpvalue.substring(5, 7) + tmpvalue.substring(8, 10));
		}
	}
	//因为存到数据库中的时间是int的型,所以如果第一位是0则要去掉
	tmp = ["feeChgtime","jbfChgtime"];
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
	tmp = ["cbDayhour"];
	for ( var i = 0; i < tmp.length; i++) {
		var tmpvalue = $("#" + tmp[i] + "F").val();
		if (tmpvalue != "") {
			var time = tmpvalue.substring(0, 2) + tmpvalue.substring(3, 5)
			if(time.substring(0,1) ==0){
				time = time.substring(1);
			}
			$("#" + tmp[i]).val(time);
		}else{
			$("#" + tmp[i]).val(0);
		}
	}
}

function edit(flag){//添加、修改
	
	setDateValue();	//格式化日期为数字
	
	var result = "";
	var rtn = [];
	var rtn_i = 0;
	
	for(var i = 0; i < chk1.length; i++){
		
		if(chk1[i] == "caclType"){
			if($("#" + chk1[i] + "_").attr("checked")){
				rtn[rtn_i++] = $("#" + chk1[i]).find("option:selected").text();
			}else{
				rtn[rtn_i++] = null;
			}
		}else if(chk1[i] == "feectrlType"){
			if($("#" + chk1[i] + "_").attr("checked")){
				rtn[rtn_i++] = $("#" + chk1[i]).find("option:selected").text();
			}else{
				rtn[rtn_i++] = null;
			}
		}else if(chk1[i] == "payType"){
			if($("#" + chk1[i] + "_").attr("checked")){
				rtn[rtn_i++] = $("#" + chk1[i]).find("option:selected").text();
			}else{
				rtn[rtn_i++] = null;
			}
		}else if(chk1[i] == "yffalarmId"){
			if($("#" + chk1[i] + "_").attr("checked")){
				rtn[rtn_i++] = $("#" + chk1[i]).find("option:selected").text();
			}else{
				rtn[rtn_i++] = null;
			}
		}else if(chk1[i] == "protSt"){
			if($("#" + chk1[i] + "_").attr("checked")){
				rtn[rtn_i++] = $("#" + chk1[i]).val() == "" ? "" : $("#" + chk1[i]).val() + "点";
			}else{
				rtn[rtn_i++] = null;
			}
		}else if(chk1[i] == "protEd"){
			if($("#" + chk1[i] + "_").attr("checked")){
				rtn[rtn_i++] = $("#" + chk1[i]).val() == "" ? "" : $("#" + chk1[i]).val() + "点";
			}else{
				rtn[rtn_i++] = null;
			}
		}else if(chk1[i] == "ngloprotFlag"){
			if($("#" + chk1[i] + "_").attr("checked")){
				rtn[rtn_i++] = $("#" + chk1[i]).find("option:selected").text();
			}else{
				rtn[rtn_i++] = null;
			}
		}
		
		//先判断是否被选中,然后判断选中的地方是否有F
		if($("#" + chk1[i] + "_").attr("checked")){
		  if(chk1[i] == "feeBegindateF"){
				var tmp = chk1[i].substring(0, chk1[i].length - 1);
				result += "," + tmp + "=" + ($("#" + tmp).val() === "" ? "null" : $("#" + tmp).val());
				
		 }else {
			result += "," + chk1[i] + "=" + ($("#" + chk1[i]).val() == "" ? "null" : $("#" + chk1[i]).val());
		 }
	    }
	}
	for(var i = 0; i < chk2.length; i++){
		if(chk2[i] == "payAdd1"){
			if($("#" + chk2[i] + "_").attr("checked")){
				rtn[rtn_i++] = $("#" + chk2[i]).val();
			}else{
				rtn[rtn_i++] = null;
			}
		}else if(chk2[i] == "payAdd2"){
			if($("#" + chk2[i] + "_").attr("checked")){
				rtn[rtn_i++] = $("#" + chk2[i]).val();
			}else{
				rtn[rtn_i++] = null;
			}
		}else if(chk2[i] == "payAdd3"){
			if($("#" + chk2[i] + "_").attr("checked")){
				rtn[rtn_i++] = $("#" + chk2[i]).val();
			}else{
				rtn[rtn_i++] = null;
			}
		}
		
		if($("#" + chk2[i] + "_").attr("checked")){
			result += "," + chk2[i] + "=" + ($("#" + chk2[i]).val() == "" ? "null" : $("#" + chk2[i]).val());
		}
	}
	
	for(var i = 0; i < chk3.length; i++){
		if($("#" + chk3[i] + "_").attr("checked")){
			if(chk3[i] == "feeChgdateF" || chk3[i] == "feeChgtimeF"){
				var tmp = chk3[i].substring(0, chk3[i].length - 1);
				result += "," + tmp + "=" + ($("#" + tmp).val() == "" ? "null" : $("#" + tmp).val());
			}else{
				result += "," + chk3[i] + "=" + ($("#" + chk3[i]).val() == "" ? "null" : $("#" + chk3[i]).val());
			}
		}
	}
	for(var i = 0; i < chk4.length; i++){
		if($("#" + chk4[i] + "_").attr("checked")){
			if(chk4[i] == "jbfChgdateF" || chk4[i] == "jbfChgtimeF"){
				var tmp = chk4[i].substring(0, chk4[i].length - 1);
				result += "," + tmp + "=" + ($("#" + tmp).val() == "" ? "null" : $("#" + tmp).val());
			}else{
				result += "," + chk4[i] + "=" + ($("#" + chk4[i]).val() == "" ? "null" : $("#" + chk4[i]).val());
			}
		}
	}
	for(var i = 0; i < chk5.length; i++){
		if($("#" + chk5[i] + "_").attr("checked")){
			if(chk5[i] == "fxdfBegindateF" || chk5[i] == "cbDayhourF"){
				var tmp = chk5[i].substring(0, chk5[i].length - 1);
				result += "," + tmp + "=" + ($("#" + tmp).val() == "" ? "null" : $("#" + tmp).val());
			}else{
				result += "," + chk5[i] + "=" + ($("#" + chk5[i]).val() == "" ? "null" : $("#" + chk5[i]).val())
			}
		}
	}
	
	for(var i = 0; i < chk6.length; i++){
		if($("#" + chk6[i] + "_").attr("checked")){
			result += "," + chk6[i] + "=" + ($("#" + chk6[i]).val() == "" ? "null" : $("#" + chk6[i]).val())
		}
	}
	
	for(var i = 0; i < chk7.length; i++){
		if($("#" + chk7[i] + "_").attr("checked")){
			if(chk7[i] == "stopBegdateF" || chk7[i] == "stopEnddateF"){
				var tmp = chk7[i].substring(0, chk7[i].length - 1);
				result += "," + tmp + "=" + ($("#" + tmp).val() == "" ? "null" : $("#" + tmp).val());
			}else{
				result += "," + chk7[i] + "=" + ($("#" + chk7[i]).val() == "" ? "null" : $("#" + chk7[i]).val())
			}
		}
	}
	
	if(result == ""){
		alert(sel_one);
		return;
	}else{
		result = result.substring(1);
	}

	$.post(def.basePath + "ajaxdocs/actYffParaZ!plEdit.action",{pl : sel_rows, result : result},function(data){
		if(data.result == "fail"){
			alert(update_fail);
			return;
		}
		
		window.returnValue = rtn;
		window.close();
	});
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