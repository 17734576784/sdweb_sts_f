<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'rtuparaBase.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/dhtmlx/dhtmlxgrid.css">
	<style type="text/css">
	body{
	  margin:0 0 0 0;
	  overflow:hidden;
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
  <script>
	var basePath = '<%=basePath%>';
  </script>
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/common/gridopt.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script src="<%=basePath%>js/docs/rtuparaBase.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  <jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
  <jsp:include page="../inc/jsdef.jsp"/>
  </head>
  
  <body>
<script type="text/javascript" src="<%=basePath%>js/css.js"></script>
  	<table cellpadding="0" cellspacing="0" height="24" width="100%" class="page_tbl_bg22">
		<tr>
			<td width=50>&nbsp;Name:</td><td>
			<input type="text" name="search_condition" value="" id="search_condition" style="width: 110px;">
			<input type="button" name="search" value="Inquiry" id="search" onclick='search();' />
			<input type="button" name="pldel" value="Batch Remove" id="pldel"/>&nbsp;&nbsp;
			<input type="button" name="tianjia" value="Add" id="tianjia"/>					
			</td>
		</tr>
	</table>
	
  	<DIV id=gridbox style="width: 100%;"></DIV>
  	<table width="100%" border="0" class="page_tbl">
    <tr><td id="pageinfo">&nbsp;</td></tr>
    </table>
    <input type="hidden" id="id" name="rtuPara.id" />
  	<script type="text/javascript">
  		$("#showmore").height($(".tabsty").height()+50);
		$("#gridbox").height($(window).height()-$("#showmore").height()-50);
		var SDDef = {};
		SDDef.APPTYPE_JC = "<%=SDDef.APPTYPE_JC%>";
		SDDef.SUCCESS = "<%=SDDef.SUCCESS%>";
		SDDef.FAIL = "<%=SDDef.FAIL%>";
  	</script>
  </body>
</html>
