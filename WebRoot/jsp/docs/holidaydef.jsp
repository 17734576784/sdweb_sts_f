<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'holidaydef.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	 <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
	 <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
	 <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	 <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
	 <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
	 <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
	 <script src="<%=basePath%>js/common/gridopt.js"></script>
	 <script src="<%=basePath%>js/common/def.js"></script>
	 <script src="<%=basePath%>js/common/dateFormat.js"></script>
	 <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	 <script src="<%=basePath%>js/docs/holidaydef.js"></script>
	 <script src="<%=basePath%>js/docs/pub_docper.js"></script>
	 <script src="<%=basePath%>js/validate.js"></script>
	 <jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
  </head>
  
  <body>
  	<DIV id=gridbox style="width: 100%;"></DIV>
  	<table width="100%" border="0" class="page_tbl">
    	<tr><td id="pageinfo">&nbsp;</td><td align="right" id="soh"><i18n:message key="doc.hide_detail" /></td><td width="15"><img src="<%=basePath%>images/mmd.gif" alt="hide" onClick="gridopt.showOrHide(this);" style="cursor: pointer;"/></td></tr>
    </table>
  	<div id="showmore">
  	 <form action="" method="post" id="addorupdate"  style='display:inline;'>
  	 	<table class="tabsty" align="center">
  	 		<tr>
  	 		<td>描述</td>
  	 		<td><input type="text" name="holidaydef.describe" id="describe"/></td>
  	 		<td>日期</td>
  	 		<td><input type="text" name="holidaydef.sdate" id="sdate"/></td>
  	 		</tr>
  	 	</table>
  	 	<input type="hidden" id="id" 			name="holidaydef.id" />
  	 </form>
  	 <center>
	    <jsp:include page="../inc/btn_tianjia.jsp"></jsp:include>
	    &nbsp;&nbsp;
	    <jsp:include page="../inc/btn_xiugai.jsp"></jsp:include>
	    &nbsp;&nbsp;
	    <jsp:include page="../inc/btn_pldel.jsp"></jsp:include>
	    <br/>
    </center>
   </div>
    <jsp:include page="../inc/jsdef.jsp" />
    <SCRIPT>
    	basePath = '<%=basePath%>';
    	$("#showmore").height($(".tabsty").height());
		$("#gridbox").height($(window).height()-$("#showmore").height()-34);
  	</SCRIPT>
  </body>
</html>
