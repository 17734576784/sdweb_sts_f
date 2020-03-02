<%@ page language="java" import="java.util.List" pageEncoding="UTF-8"%>
<%@page import="com.kesd.util.OnlineUserState"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>在线用户查看</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
    <link href="css/zh/cont.css" rel="stylesheet" type="text/css" id="index_theme" />
	<style type="text/css">
	body{
	  margin:0 0 0 0;
	  overflow:auto;
	  background: #eaeeef;
	}
	.tabsty{
		width:95%;
	}
	</style>
  </head>
  
  <body>
  	<table class="tabsty" align="center">
    	<tr><td colspan="4" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;">
    	<table cellpadding="0" cellspacing="0"><tr>
    	<td style="border: 0px; font-size: 12px;">在线用户</td>
    	</tr></table>
    	</td></tr>
    	<tr><td>序号</td><td>用户名</td><td>登录时间</td><td>登录IP</td></tr>
    <%
    /*
    List<OnlineUserState.User> user = OnlineUserState.getOnlineUserList();
    if(user != null) {
	    for(int i = 0; i < user.size(); i++) {
	    	OnlineUserState.User tmp = user.get(i);
	    	out.println("<tr>");
	    	out.println("<td>" + (i + 1) + "</td><td>"+tmp.username+ "</td><td>"+tmp.log_time+"</td><td>"+tmp.log_ip+"</td>");
	    	out.println("</tr>");
	    }
    }
    */
    %>
    </table>
    
  </body>
</html>
