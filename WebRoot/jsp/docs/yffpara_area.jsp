<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'yffpara.jsp' starting page</title>
    <style type="text/css">
	body{
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
  <script src="<%=basePath%>js/docs/yffpara_area.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  <jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
  </head>
  
  <body>
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
  <td valign="top" width="206">
  <jsp:include page="../tree.jsp"></jsp:include>
  </td>
  <td>
    <table cellpadding="0" cellspacing="0" height="24" width="100%" class="page_tbl_bg22">
  <tr>
  <td>&nbsp;&nbsp;区域名称: 
  <input type="text" name="search_condition" value="" id="search_condition" style="width: 110px;">
      <input type="button" name="search" value="<i18n:message key="doc.search" />" id="search" onclick='search();' /></td>
       <td style="font-size: 12px;" >
       &nbsp;&nbsp;<i18n:message key="doc.yffbz" />: 
 			<select id="yffState">
			<option value=0 selected><i18n:message key="doc.all" /></option>
			<option value=1><i18n:message key="doc.sy" /></option>
			<option value=2><i18n:message key="doc.bsy" /></option>
 			</select>
		&nbsp;&nbsp;&nbsp;<input type="button" value="<i18n:message key="refresh" />" id="freshid" onclick="onFresh();" />  						
		</td>
		<td align="right">
	 <jsp:include page="../inc/btn_excel.jsp"></jsp:include>
	 &nbsp;&nbsp;&nbsp;&nbsp;</td>
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
</td></tr></table>
<jsp:include page="../inc/jsdef.jsp" />
<SCRIPT>
 	$("#showmore").height($(".tabsty").height());
	$("#gridbox").height($(window).height()-$("#showmore").height()-53);
</SCRIPT>
  </body>
</html>
