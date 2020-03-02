<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Residential Area Select</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/dhtmlx/dhtmlxgrid.css">
	<style type="text/css">
	body{
		width:100%;
		margin: 0 0 0 0;
		overflow: hidden;
	}
	.tabsty input{
		width: 100px;
	}
	.tabsty select {
		width: 90px;
		font-color: black;
	}
	input,select{
		font-size: 12px;
	}
	</style>
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/common/gridopt.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
  </head>
  <body>
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
  <td>
   	<table cellpadding="0" cellspacing="0" height="24" width="100%" class="page_tbl_bg22">
  		<tr>
			<td width=70>&nbsp;Name:</td><td>
			<input type="text" name="search_condition" value="" id="search_condition" style="width: 100px;">
			<input type="button" name="search" value="Inquiry" id="search"/>&nbsp;&nbsp;
			<input type="button" name="confirm" value="Confirm" id="confirm"/>
			</td>
		</tr>
	</table>
	<div id=gridbox style="width: 100%;"></div>
	<input id="consparaId" type="hidden"/>
	<input id="consparaDesc" type="hidden"/>
</td></tr></table>
<SCRIPT>
	var basePath = '<%=basePath%>';
</SCRIPT>
<script src="<%=basePath%>js/dialog/selectCons.js"></script>
  </body>
</html>
