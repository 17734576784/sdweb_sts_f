<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>文件未找到</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
    *{margin : 0 auto;}
    .err_404 {
    	width : 98%;
    }
    </style>
  </head>
  
  <body>
    <h1>404错误</h1>
    <table class="err_404" align="center">
    <tr><td>文件未找到!</td></tr>
    <tr><td>返回<a href="<%=basePath%>login.jsp">登录</a>页</td></tr>
    </table>
  </body>
</html>
