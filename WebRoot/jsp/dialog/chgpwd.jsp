<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Change Password</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/dialog/chgpwd.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/def.js"></script>
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
		width: 130px;
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
	    		<table cellpadding="0" cellspacing="0"><tr><td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td><td id="tdtitle" style="border: 0px; font-size: 12px;">Change Password</td></tr></table>
	    	</td>
	    </tr>
		<tr><td>Old Password:</td><td><input type="password" id="old_pwd" /></td></tr>
		<tr><td>New Password:</td><td><input type="password" id="new_pwd" /></td></tr>
		<tr><td>Confirm new Password:</td><td><input type="password" id="new_pwd_q" /></td></tr>
	</table>
	<center>
	<button id="xiugai">Confirm</button>&nbsp;&nbsp;&nbsp;
	<button id="close">Close</button>
	</center>
  </body>
</html>
