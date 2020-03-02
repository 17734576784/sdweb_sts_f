<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>补打发票</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style  type="text/css">
	.tbl{
		border-collapse: collapse;
		width: 100%;		
	}
	.tbl td{
	border:1px solid;
	border-color:gray;
	width: 50%;
	}
	</style>
	<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script  src="<%=basePath%>js/common/NumtoChar.js"></script>
	<script  src="<%=basePath%>js/dyjc/print/printBill.js"></script>
  </head>
  
  <body>
  	
   <table class="tbl" align="center">
   <tr><td  style="border: 0; text-align: center;font-size: 18px;letter-spacing: 4px;" colspan=2>购电收据</td></tr>   
   <tr><td style="border:0;border-bottom: 1px solid;" colspan=2>购电单号:</td></tr>
   <tr><td style="border:0;height:10px" colspan=2> </td></tr>   
   <tr><td>用户名称:</td><td><span id="yhmc">11</span></td></tr>   
   <tr><td>用户编号:</td><td><span id="yhbh">22</span></td></tr>
   <tr><td>缴费金额(元):</td><td><span id="jfje">33</span></td></tr>
   <tr><td>大写(元):</td><td><span id="dxje"></span></td></tr>   
   </table>
   <br>
  </body>
</html>
