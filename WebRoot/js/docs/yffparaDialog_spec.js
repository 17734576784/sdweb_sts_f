var params = window.dialogArguments;
var param = params.id.split(",");

$(document).ready(function(){
	loading.loading();
	initDiction();
	
	$("#xiugai").click(function(){
		if(check()){
			addOrEdit("edit");
		};
	});
	
	label_change();
	
	$("#huming").val(param[2]);
	$("#huhao").val(param[3]);
	$("#cont_cap").val(param[4]);
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
	var text = '{item:["' + Dict.DICTITEM_YESFLAG + '","' + Dict.DICTITEM_USEFLAG + '","' + Dict.DICTITEM_CB_CYCLE_TYPE + '","' + Dict.DICTITEM_PREPAYTYPE + '","' + Dict.DICTITEM_FEETYPE + '","' + Dict.DICTITEM_PAYTYPE + '","' + Dict.DICTITEM_YFFMONEY + '","' + Dict.DICTITEM_YFFSPECCTRLTYPE + '","' + Dict.DICTITEM_EXECFLAG + '","' + Dict.DICTITEM_PRIZEFLAG + '","' + Dict.DICTITEM_STOPFLAG + '","' + Dict.DICTITEM_CARDMETER_TYPE + '"]}';
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
		var id = param[0], rtuid = param[1];
		$("#rtuId").val(rtuid);
		$("#zjgname").val(param[2]);
		if(id == ""){//父页面点击“新增”时
		
		}else{
			//$("#xiugai").attr("disabled",false);
			$("#zjgId").val(id);
			var field = "rtuId,zjgId,caclType,feectrlType,payType,yffalarmId,feeBegindate,protSt,protEd,ngloprotFlag,keyVersion,cryplinkId,tzVal,feeprojId,feeproj1Id,feeproj2Id,useGfh,hfhTime,hfhShutdown,plusTime,csStand,powrateFlag,prizeFlag,stopFlag,stopBegdate,stopEnddate,payAdd1,payAdd2,payAdd3,yffctrlType,feeChgf,feeChgid,feeChgid1,feeChgid2,feeChgdate,feeChgtime,jbfChgf,jbfChgval,jbfChgdate,jbfChgtime,cbCycleType,cbDayhour,jsDay,fxdfFlag,fxdfBegindate,localMaincalcf,writecardNo,cbjsFlag,cardmeterType";
			$.post(def.basePath  + "ajaxdocs/actYffParaZ!getYffParaById.action", {result : id+","+rtuid, field : field}, function(data) {
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
					window.setTimeout("setValue_('"+rowsplit[i]+"','"+json.data[i]+"')",1);
					
					//$("#"+rowsplit[i]).val(json.data[i]);
				}
			}
			});
		}
	}
}

//增加setValue_()方法
function setValue_(id,data){
	$("#"+id).val(data);
}

function setDateValue() {
	
	var tmp = ["feeChgdate","jbfChgdate","fxdfBegindate","feeBegindate","stopBegdate","stopEnddate"];
	
	for ( var i = 0; i < tmp.length; i++) {
		var tmpvalue = $("#" + tmp[i] + "F").val();
		if (tmpvalue != "") {
			$("#" + tmp[i]).val(tmpvalue.substring(0, 4) + tmpvalue.substring(5, 7) + tmpvalue.substring(8, 10));
		}else{
			$("#" + tmp[i]).val(0);
		}
	}
	tmp = ["feeChgtime","jbfChgtime"];
	for ( var i = 0; i < tmp.length; i++) {
		var tmpvalue = $("#" + tmp[i] + "F").val();
		if (tmpvalue != "") {
			$("#" + tmp[i]).val(tmpvalue.substring(0, 2) + tmpvalue.substring(3, 5) + tmpvalue.substring(6, 8));
		}else{
			$("#" + tmp[i]).val(0);
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

function addOrEdit(flag){//添加、修改
	setDateValue();
	var params = $('#addorupdate').serialize();
	$.ajax({
		url      : def.basePath + "ajaxdocs/actYffParaZ!addOrEdit.action", //后台处理程序
		type     : 'post',					 							//数据发送方式
		data     : params, 												//要传递的数据；就是上面序列化的值
		success  : function(data) {								    	//回传函数
			if (data.result == "fail") {
				alert(update_fail+"!");
			}else{
				window.returnValue = true;
				window.close();
			}
		}
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