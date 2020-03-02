var cok_org				= "cok_org";//存储上次查询的所属供电所
var cok_fzman			= "cok_cons";//存储上次查询的所属联系人
var params = window.dialogArguments;//pagetype:""单个结算，"pl"批量结算

$(document).ready(function(){
	initOrg();
});


function doPreview(){
	var xlsName = $("#myFile").val();
	if(xlsName == ""){
		alert("请选择算费系统余额文件!");
		return;
	}
	if(xlsName.substring(xlsName.length-4,xlsName.length) != ".xls"){
		alert("请选择xls格式的余额文件!");
		return;
	}
	
	xlsName = $("#myFileBZ").val();
	if(xlsName == "" ){
		alert("请选择抄表日表底文件!");
		$("#myFileBZ").focus()
		return;
	}
	if(xlsName.substring(xlsName.length-4,xlsName.length) != ".xls"){
		alert("请选择xls格式的表底文件!");
		return;
	}
	loading.loading();
	//增加权限参数
	var qx_params = $("#orgId").val() + "," + $("#fzmanId").val();
	if(params == ""){//结算补差
		document.forms[0].action = basePath + "excel/SG186JSExcelDy.action?params=" + qx_params;
	}else{
		document.forms[0].action = basePath + "excel/SG186JSExcelDyPl.action?params=" + qx_params;
	}
	frmfile.submit();
}

function initOrg(){
	$.post(def.basePath + "ajax/actCommon!getOrg.action",{},function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			if(data.flag != "1"){
				$("#orgId").append("<option value=-1>所有</option>");
			}
			
			for(var i=0;i<json.length;i++){
				$("#orgId").append("<option value="+json[i].value+">"+json[i].text+"</option>");
			}

			var cok_org_val = cookie.get(cok_org);
			window.setTimeout('setOrgVal(' + cok_org_val + ')', 100);
		}
	});
}

function setOrgVal(org_id) {
	if(!(org_id == null || org_id == "")){
		$("#orgId").val(org_id);
	}
	getLineFzMan();
}

function getLineFzMan(){//根据供电所ID查询线路负责人列表，填充$("#fzmanId")。
	var orgid=$("#orgId").val();
	if(orgid == undefined || orgid == "-1"){
		$("#fzmanId").html("<option value=-1>所有</option>");
		return;
	}
	$.post(def.basePath + "ajax/actCommon!getLineFzByOrg.action",{value:orgid},function(data){
		if(data.result == ""){
			$("#fzmanId").html("<option value=-1>所有</option>");
		}else{
			var json = eval('(' + data.result + ')');
			var option="<option value=-1>所有</option>";
			for(var i=0;i<json.length;i++){
				option += "<option value=" + json[i].value + ">" + json[i].text + "</option>";
			}
			$("#fzmanId").html(option);		
			//将cookie里面的fzman回显
			var cok_fzman_val = cookie.get(cok_fzman);
			window.setTimeout('$("#fzmanId").val(' + cok_fzman_val + ')',1);
		}
	});
}

