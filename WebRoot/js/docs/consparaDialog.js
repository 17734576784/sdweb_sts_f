var params = window.dialogArguments;

$(document).ready(function(){
	if(params.id == ""){//父页面  点击  新增  进入本页面时，修改按钮禁用
		$("#xiugai").attr("disabled",true);
	}else{
		$("#xiugai").attr("disabled",false);
	}
	
	initOrgId();
	
	$("#tianjia").click(function(){
		if(check()){
			$("#id").val("");
			$("#appType").val(YDDef.APPTYPE_JC);
			addOrEdit("add");
		};
	});
	
	$("#xiugai").click(function(){
		if(check()){
			$("#appType").val(YDDef.APPTYPE_JC);
			addOrEdit("edit");
		};
	});
});

function initDiction(){
	var text = '{item:["' + Dict.DICTITEM_POWERTYPE + '","' + Dict.DICTITEM_VOLTGRADE + '","' + Dict.DICTITEM_TRMODEL + '"]}';
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : text}, function(data){
		var json = eval('(' + data.result + ')');
		var iid = 0;
		var dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_POWERTYPE);
		for ( var i = 0; i < dict.length; i++) {
			$("#powerType").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_VOLTGRADE);
		for ( var i = 0; i < dict.length; i++) {
			$("#voltGrade").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_TRMODEL);
		for ( var i = 0; i < dict.length; i++) {
			$("#trModel").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		setValue();
	});
}


function initOrgId(){
	$.post(YDDef.BASEPATH + "ajax/actCommon!getOrg.action",{},function(data){
		if(data.result != ""){
			var array = eval('(' + data.result + ')');
			for(var i=0;i<array.length;i++){
				$("#orgId").append("<option value="+array[i].value+">"+array[i].text+"</option>");
			}
			
		}
		initLineFzMan();
	});
}


function initLineFzMan(){
	$.post(YDDef.BASEPATH + "ajax/actCommon!getLineFzByOrg.action",{value: $("#orgId").val()},function(data){
		if(data.result != ""){
			var array = eval('(' + data.result + ')');
			for(var i=0;i<array.length;i++){
				$("#lineFzManId").append("<option value="+array[i].value+">"+array[i].text+"</option>");
			}
		}
	});
	initDiction();
}

function getLineFzMan(value){
	$("#lineFzManId").html("");
	$.post(YDDef.BASEPATH + "ajax/actCommon!getLineFzByOrg.action",{value: value},function(data){
		if(data.result != ""){
			var array = eval('(' + data.result + ')');
			for(var i=0;i<array.length;i++){
				$("#lineFzManId").append("<option value=" + array[i].value+">" + array[i].text + "</option>");
			}
		}
	});
}


function addOrEdit(flag){
	
	var params = $('#addorupdate').serialize();
	$.ajax({
		url      : YDDef.BASEPATH + "ajaxdocs/actConsPara!addOrEdit.action", //后台处理程序
		type     : 'post',					 							//数据发送方式
		data     : params, 												//要传递的数据；就是上面序列化的值
		success  : function(data) {								    	//回传函数
			if (data.result == "fail") {
				if(flag == 'add'){
					alert("Failed to add！");
				}else{
					alert("Failed to modify！");
				}
			}else{
				if(flag == 'add'){
//					alert("Successfully Add!");
					window.returnValue = 1;
				}else{
//					alert("Successfully Modify!");
					window.returnValue = 0;
				}
			}
			window.close();
		}
	});
}
function check() {
	if (!ckChar("describe", "Name", 64, true)) {
		return false;
	}
//	if (!isNumber("telNo3", "Utility ID",0,9999, true)) {
//		return false;
//	}
//	if (!isMobilePhone("telNo1", "Telphone", false)) {
//		return false;
//	}
//	if (!isMobilePhone("telNo2", "Mobile", false)) {
//		return false;
//	}
	if (!ckChar("postalCode", "Post Code", 64, false)) {
		return false;
	}
	
	return true;
}
function setValue(){	
	if(params.id != undefined){
		var id = params.id;
		if(id == ""){
			
		}else{
			var field = "id,describe,orgId,lineFzManId,powerType,voltGrade,trModel,fzMan,telNo1,telNo2,telNo3,postalCode,addr";
			$.post(YDDef.BASEPATH  + "ajaxdocs/actConsPara!getConsParaById.action", {result : id, field : field}, function(data) {
				var json = eval('(' + data.result + ')');
				var rowsplit = field.split(",");
				for(var i = 0; i < rowsplit.length; i ++){
					if(rowsplit[i]=="lineFzManId"){
						getLineFzMan($("#orgId").val());
						$("#lineFzManId").val(json.data[i]);
					}
					else{
						$("#"+rowsplit[i]).val(json.data[i]);
					}
				}
			});
		}
	}
}