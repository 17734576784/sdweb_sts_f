<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String apptype = request.getParameter("apptype");
if(apptype == null)return;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'yffsj.jsp' starting page</title>
    
  </head>
  <style type="text/css">
	body{
	  margin:0 0 0 0;
	  overflow : hidden;
	}
	.tabsty input,.tabsty select {
		width: 90px;
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
  <script src="<%=basePath%>js/docs/yffsj.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  <jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
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
			</td>
			<td align="right">
			<jsp:include page="../inc/btn_excel.jsp"></jsp:include>
			&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>
	</table>
	
  	<DIV id=gridbox style="width: 100%;"></DIV>
  	<table width="100%" border="0" class="page_tbl">
    <tr><td id="pageinfo">&nbsp;</td></tr>
    </table>
    <input type="hidden" id="id" name="rtuPara.id" />
    </td></tr></table>
    <jsp:include page="../inc/jsdef.jsp" />
  	<SCRIPT>
  		var grid_title = "<i18n:message key="page.yffsj.grid_title" />";
  		$("#showmore").height($(".tabsty").height());
		$("#gridbox").height($(window).height()-$("#showmore").height()-53);
		appType = <%=apptype%>;
  	</SCRIPT>
  </body>
</html>
