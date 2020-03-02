
var params = window.dialogArguments;
var res_id = params.id.split(",")[0];
var rtu_id = params.id.split(",")[1];
$(document).ready(function(){
	if(params.id.split(",")[0] == ""){//父页面点击“新增”时
		$("#xiugai").attr("disabled",true);
	}
	$("#rtuId").val(rtu_id);
	initRtu();
	if(res_id != "")setValue();
	$("#tianjia").click(function(){
		if(check()){
			$("#id").val("");
			addOrEdit("add");
		};
	});
	
	$("#xiugai").click(function(){
		if(check()){
			addOrEdit("edit");
		};
	});
	$("#tianjia").show();
	$("#xiugai").show();
	$("#guanbi").show();
});
function addOrEdit(flag){
	var ae_params = $('#addorupdate').serialize();
	$.ajax({
		url      : def.basePath + "ajaxdocs/actResidentPara!addOrEdit.action", //后台处理程序
		type     : 'post',					 							//数据发送方式
		data     : ae_params, 												//要传递的数据；就是上面序列化的值
		success  : function(data) {								    	//回传函数
			if (data.result == "fail") {
				if(flag == 'add'){
					alert("Échec de l'ajout de！");
				}else{
					alert("Impossible de modifier modify");
				}
			}else if(data.result == "exist"){
				alert("L'identifiant du consommateur existe！");
			}else{
				if(flag == 'add'){
					window.returnValue = "1" + data.result + "," + $("#describe").val();
				}else{
					window.returnValue = "0" + data.result.split(",")[1] + "," + $("#id").val() + "," + ($("#preconsId").val() == $("#consId").val()?1:0)+","+$("#describe").val()+","+$("#consNo").val();
				}
				window.close();
			}
		}
	});
}
function check() {
	if (!ckChar("describe", "Name", 64, true)) {
		return false;
	}
//	if (!isTelephone("phone", "固定电话", false)) {
//		return false;
//	}
//	if (!isTelephone("fax", "传真", false)) {
//		return false;
//	}
//	if (!isMobilePhone("mobile", "移动电话", false)) {
//		return false;
//	}
//	if (!isZipcode("post", "邮政编码",false)) {
//		return false;
//	}
//	if (!isEmail("mail", "电子邮件", false)) {
//		return false;
//	}
	
	return true;
}
function setValue(){
	if(params != undefined){
		var id = params.id;
		if(id == ""){
			
		}else{
			var field = "id,describe,consNo,address,post,phone,mobile,fax,mail,infCode1,rtuId";
			$.post(def.basePath + "ajaxdocs/actResidentPara!getResidentParaById.action", {result : id, field : field}, function(data) {
				var json = eval('(' + data.result + ')');
				var rowsplit = field.split(SDDef.SPLITCOMA);
				for(var i = 0; i < rowsplit.length; i ++){
						$("#"+rowsplit[i]).val(json.data[i]);
					}
			});
		}
	}
}

function initRtu() {
	$.post(def.basePath + "ajaxdocs/actResidentPara!getResidentDescId.action", {result:rtu_id}, function(data) {
		var json = eval('(' + data.result + ')');
		$("#rtu_describe").val(json.data[0]);
	});
}