<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.dbpara.YffManDef"%>
<%@page import="com.kesd.common.SDDef"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'yffmandef.jsp' starting page</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
    <style type="text/css">
	body{
	  margin:0 0 0 0;
	  overflow:hidden;
	}
	.tabsty input, select {
		width: 130px;
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
  <script src="<%=basePath%>js/common/dateFormat.js"></script>
  <script  src="<%=basePath%>js/common/modalDialog.js"></script>
  <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
  <script src="<%=basePath%>js/dialog/selectConsPermission.js"></script>
  <script src="<%=basePath%>js/docs/pub_docper.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
  <script src="<%=basePath%>js/validate.js"></script>
  <jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
  </head>
  
  <body>
   	<table cellpadding="0" cellspacing="0" height="24" width="100%" class="page_tbl_bg22">
  		<tr>
			<td width=70>&nbsp;Name:</td><td>
			<input type="text" name="search_condition" value="" id="search_condition" style="width: 100px;">
			<input type="button" name="search" value="Inquiry" id="search"/>&nbsp;&nbsp;
			<input type="button" name="confirm" value="Confirm" id="confirm"/>
			</td>
		</tr>
	</table>  
    <DIV id=gridbox style="width: 100%;"></DIV>
  	<table width="100%" border="0" class="page_tbl">
    <tr><td id="pageinfo">&nbsp;</td><td align="right" id="soh">Hide detail</td><td width="15"><img src="<%=basePath%>images/mmd.gif" alt="hide" onClick="gridopt.showOrHide(this);" style="cursor: pointer;"/></td></tr>
    </table>
    <jsp:include page="../inc/jsdef.jsp" />
  	<SCRIPT>
		$("#gridbox").height(450-104);
		var grid_title = "<i18n:message key="page.yffry.grid_title" />";
		var name = "<i18n:message key="doc.name" />";
		var rese2_flag = <%=((YffManDef)session.getAttribute(SDDef.SESSION_USERNAME)).getRese2_flag()%>;
		SDDef.YFF_APPTYPE_DYQX = <%= SDDef.YFF_APPTYPE_DYQX%>;
		SDDef.YFF_APPTYPE_GYQX = <%= SDDef.YFF_APPTYPE_GYQX%>;
		SDDef.YFF_APPTYPE_NPQX = <%= SDDef.YFF_APPTYPE_NPQX%>;
		SDDef.SPLITCOMA		   = '<%= SDDef.SPLITCOMA%>';
  	</SCRIPT>
  </body>
</html>
