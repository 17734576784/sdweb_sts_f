<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>" target="_self">
    
    <title>上传模板数据</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript">
	var basePath = '<%=basePath%>';
		function importDB(){
				var myFile = $("#myFile").val();
				if(myFile == ""){
					alert("请选择要导入的文件!");
					return;
				}
				document.forms[0].action = basePath + "impExp/import!getFileText.action";
				//$("#frmfile").attr("action",url);
				frmfile.submit();
		}
	</script>
  </head>
  
  <body>
    <div style="font-size: 15px; margin-top:10px">
    	<span style="margin-left:50px;">请选择导入的数据文件:</span><br/>
    	<form id="frmfile" name="frmfile" action=" " method ="POST" ENCTYPE="multipart/form-data">
    	<input type="file" name ="myFile" id="myFile" style="height:22px;margin-left:50px;margin-top:5px" />
    	<div style="margin-left:80px;margin-top:15px">
    		<input type="button" id="update" value="导入" onclick="importDB();"/>
    		&nbsp;&nbsp;&nbsp;&nbsp;
    		<input type="button" id="cancel" value="取消" onClick="window.close();"/>
    	</div>
    	</form>
    </div>
  </body>
  <script>
  var msg = '<%=(String)request.getAttribute("msg")%>';
  if(msg!="" && msg!="null"){
	window.returnValue = msg;
	window.close();
}			
  </script>
  </html>
