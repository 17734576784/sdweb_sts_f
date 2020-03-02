<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>subscription code</title>
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  	<script type="text/javascript" src="<%=basePath%>js/regMsg.js"></script>
	<style type="text/css">
	body{
		margin: 0 0 0 0;
		overflow: hidden;
	  	background: #eaeeef;
	}	
	.tabsty{
		width:90%;
	}
	.tabsty input {
		width: 230px;
	}
	input{
		font-size: 12px;
	}
	</style>
  </head>
  
  <body>
	<table class="tabsty" align="center">
		<tr>
			<td colspan="2" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;">
	    		<table cellpadding="0" cellspacing="0"><tr><td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td><td id="tdtitle" style="border: 0px; font-size: 12px;color:red;">Please enter a valid subscription code</td></tr></table>
	    	</td>
	    </tr>
		<tr><td>User Name:</td><td><input type="text" id="userName" /></td></tr>
		<tr><td>Subscription Code:</td><td><input type="text" id="subCode" /></td></tr>
	</table>
	<center>
	<button id="confirmCode">Confirm</button>&nbsp;&nbsp;&nbsp;
	<button id="close">Close</button>
	</center>
  </body>
</html>
