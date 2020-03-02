var params = window.dialogArguments;
var param = params.id;

var tmp_id = -1;

$(document).ready(function(){

	setValue();
	initDB();
	
	$("#xiugai").click(function(){
		if(check()){
			if($("#id").val() === ""){
				$("#id").val(tmp_id);
			}
			addOrEdit("edit");
		};
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
				
		}
	});
}

function addOrEdit(){
	
	var tmpvalue = $("#feeBegindate_").val();
	if(tmpvalue != ""){
		$("#feeBegindate").val(tmpvalue.substring(0, 4) + tmpvalue.substring(5, 7) + tmpvalue.substring(8, 10));
	}
	
	var params = $('#addorupdate').serialize();
	$.ajax({
		url      : def.basePath + "ajaxdocs/actAreaPara!edit.action", //后台处理程序
		type     : 'post',					 							//数据发送方式
		data     : params, 												//要传递的数据；就是上面序列化的值
		success  : function(data) {								    	//回传函数
			if(data.result != "fail") {
				window.returnValue = data.result;
				window.close();
			} else {
				alert("修改失败!");
			}
		}
	});
}

function check() {
	if (!ckChar("describe", "名称", 64, true)) {
		return false;
	}
	if (!ckChar("areaCode", "区域号", 64, true)) {
		return false;
	}
	return true;
}

var pre_orgid = -1;
function setValue(){
	
	if(param != undefined){
		var id = param;
		if(id == ""){
			
		}else{
			$("#id").val(id);
			$("#orgId_").val(params.org_desc);
			var field = "id,describe,orgId,feeprojId,yffalarmId,feeBegindate,areaCode,infCode1,infCode2,remark";
			$.post(def.basePath  + "ajaxdocs/actAreaPara!getById.action", {result : id, field : field}, function(data) {
				var json = eval('(' + data.result + ')');
				var rowsplit = field.split(",");
				for(var i = 0; i < rowsplit.length; i ++){
					
					if(rowsplit[i] == "feeBegindate"){//格式化时间
						$("#"+rowsplit[i]).val(json.data[i]);
						dateFormat.date = json.data[i];
						$("#"+rowsplit[i] + "_").val(dateFormat.formatToYMD(0));
						continue;
					}
					$("#"+rowsplit[i]).val(json.data[i]);
				}
				pre_orgid = $("#orgId").val();
			});
		}
	}
}
