var params = window.dialogArguments;

$(document).ready(function(){
	
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : '{item:["' + Dict.DICTITEM_USEFLAG + '"]}'}, function(data){
		var json = eval('(' + data.result + ')');
		var dict = eval("json.rows[0]." + Dict.DICTITEM_USEFLAG);
		for ( var i = 0; i < dict.length; i++) {
			$("#useFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		setValue();
	});
	
	$("#xiugai").click(function(){
		addOrEdit("edit");
	});
});

function setValue(){
	if(params != undefined){
		rtuid = params.id;
		$("#rtuId").val(rtuid);

		$("#xiugai").attr("disabled",false);

		var field = "id,describe,rtuId,useFlag,yffbgDate,yffbgTime,sg186bgDate,sg186bgTime,reserve1Date,reserve1Time,reserve2Date,reserve2Time,reserve3Date,reserve3Time";
		$.post(def.basePath  + "ajaxdocs/actRtuPayCtrlPara!getRtuPayCtrlById.action", {result : rtuid, field : field}, function(data) {
			if(data.result != ""){
			var json = eval('(' + data.result + ')');
			var rowsplit = field.split(SDDef.SPLITCOMA);
			
			for(var i = 0; i < rowsplit.length; i ++){
				if(rowsplit[i] == "yffbgDate"||rowsplit[i] == "sg186bgDate" || rowsplit[i] == "reserve1Date" || rowsplit[i] == "reserve2Date" || rowsplit[i] == "reserve3Date"){
					dateFormat.date = json.data[i];
					$("#"+rowsplit[i]+"F").val(dateFormat.formatToYMD(0));
				}
				if(rowsplit[i] == "yffbgTime"||rowsplit[i] == "sg186bgTime" || rowsplit[i] == "reserve1Time" || rowsplit[i] == "reserve2Time" || rowsplit[i] == "reserve3Time"){
					dateFormat.date = json.data[i];
					$("#"+rowsplit[i]+"F").val(dateFormat.formatToHMS(0));
				}
				$("#"+rowsplit[i]).val(json.data[i]);
			}
		}
		});
	}
}

function setDateValue() {
	
	var tmp = ["yffbgDate", "sg186bgDate","reserve1Date","reserve2Date","reserve3Date"];
	
	for ( var i = 0; i < tmp.length; i++) {
		var tmpvalue = $("#" + tmp[i] + "F").val();
		if (tmpvalue != "") {
			$("#" + tmp[i]).val(tmpvalue.substring(0, 4) + tmpvalue.substring(5, 7) + tmpvalue.substring(8, 10));
		}
	}
	tmp = [ "yffbgTime", "sg186bgTime","reserve1Time","reserve2Time","reserve3Time"];
	for ( var i = 0; i < tmp.length; i++) {
		var tmpvalue = $("#" + tmp[i] + "F").val();
		if (tmpvalue != "") {
			$("#" + tmp[i]).val(tmpvalue.substring(0, 2) + tmpvalue.substring(3, 5) + tmpvalue.substring(6, 8));
		}
	}
	
}
function addOrEdit(flag){//添加、修改
	setDateValue();
	
	var params = $('#addorupdate').serialize();
	$.ajax({
		url      : def.basePath + "ajaxdocs/actRtuPayCtrlPara!addOrEdit.action", //后台处理程序
		type     : 'post',					 							//数据发送方式
		data     : params, 												//要传递的数据；就是上面序列化的值
		success  : function(data) {								    	//回传函数
			if (data.result == "fail") {
				alert(update_fail);
			}else{
				window.returnValue = $("#rtuId").val();
				window.close();
			}
		}
	});
}
