<%@ page language="java" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<form style="display: inline;" method="post" action="<%=basePath%>excel/dataExcel.action" name="_toxls">
	<input type="hidden" id="excPara" name="excPara" />
	<input type="hidden" id="colType" name="colType" />
	<input type="hidden" id="header" name="header" />
	<input type="hidden" id="attachheader" name="attachheader" />
	<input type="hidden" id="vfreeze" name="vfreeze" />
	<input type="hidden" id="hfreeze" name="hfreeze" />
	<input type="hidden" id="filename" name="filename" />
	
	<input type="button" value="Sortie Excel" id="toexcel" onclick='dcExcel();' />
</form>
<script src="<%=basePath%>js/common/jsonString.js"></script>
<script type="text/javascript">
<!--
function setExcValue(){
	
	if(gridopt.grid.getRowsNum() == 0)return;
	//$("#excPara").val("");
	$("#excPara").val(encodeURI(jsonString.json2String(gridopt.jsondata.page[0])));
}
function dcExcel(){
	var len = document.getElementById("excPara").value.length;
	
	if(len > 1280000){
		alert("Data exceed normal, fail to output excel");
		return;
	}
	
	_toxls.submit();
}
//-->
</script>
