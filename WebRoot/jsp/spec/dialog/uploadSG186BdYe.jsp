<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>" target="_self">
    
    <title>上传186表底余额</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="cache-control" content="no-cache">
	
	<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="index_theme" />
	
	<script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script  src="<%=basePath%>js/validate.js"></script>
	<script src="<%=basePath%>js/common/cookie.js"></script>
	<script  src="<%=basePath%>js/common/def.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script  src="<%=basePath%>js/spec/dialog/uploadSG186BdYe.js"></script>

	<style type="text/css">
	.mytable td{
		height: 30px;
		font-size: 12px;
	}
	input, select {
		font-size: 12px;
	}
	</style>
  </head>
  
  <body>

	<form id="frmfile" name="frmfile" action ="" style="display:inline" method ="POST" ENCTYPE="multipart/form-data"> 
		<table cellpadding="0" cellspacing="0" align="center" class="mytable" >
			<tr><td>所属供电所:<select id="orgId" style="height:22px; width:130px" onchange="getLineFzMan()"></select></td></tr>
			<tr><td>所属联系人:<select id="fzmanId" style="height:22px; width:130px"></select></td></tr>
			<tr><td>抄表日表底:</td></tr>
			<tr><td><input type="file" name ="myFileBZ" id="myFileBZ" style="height:22px;"/></td></tr>
			<tr><td>算费系统余额:</td></tr>
			<tr><td><input type="file" name ="myFile" id="myFile" style="height:22px;"/></td></tr>
			<tr><td style="text-align: right;"><input type="button" id="upload_ye" onClick="doPreview()" style="height:22px;" value="上传文件"/></td></tr>
			<tr><td></td></tr>
		</table>  
		
		<table width="100%">
			<tr><td style="font-size: 12px; color:red; text-align: center">如果导入文件错误，请手工另存为"EXCEL97-EXCEL2003"文件类型</td></tr>
		</table>
		
	</form>
			
	
</body>
<script type="text/javascript">

var resultdata = '<%=(String)request.getAttribute("resultdata")%>';
var errMsg = '<%=(String)request.getAttribute("errMsg")%>';
var basePath = '<%=basePath%>';
if(errMsg!="" && errMsg!="null"){
	alert(errMsg);
}else{
	if(resultdata!="" && resultdata!="null"){
		window.returnValue = resultdata;
		window.close();
	}
}

</script>
</html>
  