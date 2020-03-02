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
	frmfile.submit();
}

