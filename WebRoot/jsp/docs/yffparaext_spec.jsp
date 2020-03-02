<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'yffparaext_spec.jsp' starting page</title>
    <style type="text/css">
body {
	margin:0 0 0 0;
	overflow:hidden;
}
.tabsty input, .tabsty select {
	width: 90px;
}
input, select {
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
  <script src="<%=basePath%>js/docs/yffparaext_spec.js"></script>    
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  <jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>

  </head>
  
  <body>
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
  <td valign="top" width="206">
  <jsp:include page="../tree.jsp">
  	<jsp:param value="dy_doc" name="autoshow_type"/>
  </jsp:include>
  </td>
  <td>
	<table cellpadding="0" cellspacing="0" height="24" width="100%" class="page_tbl_bg22">
  <tr>
    <td>&nbsp;<i18n:message key="doc.name" />: 
      <input type="text" name="search_condition" value="" id="search_condition" style="width: 110px;">
      <input type="button" name="search" value="<i18n:message key="doc.search" />" id="search" onclick='search();' />
      &nbsp;&nbsp;
	 </td>
  </tr>
</table>
<DIV id=gridbox style="width: 100%;"></DIV>
<table width="100%" border="0" class="page_tbl">
  <tr>
    <td id="pageinfo">&nbsp;</td>
    <td align="right" id="soh"></td>
    <td width="15"></td>
  </tr>
</table>
<input type="hidden" id="id"/>
</td>
</tr></table>
<jsp:include page="../inc/jsdef.jsp" />
<SCRIPT>
	var grid_title = "<i18n:message key="page.spec.extpara.title" />";
  	$("#showmore").height($(".tabsty").height());
	$("#gridbox").height($(window).height()-$("#showmore").height()-53);
</SCRIPT>
  </body>
</html>
