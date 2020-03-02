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
    
    <title>假日组分项</title>
    
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
	 <script src="<%=basePath%>js/common/modalDialog.js"></script>
	 <script src="<%=basePath%>js/common/gridopt.js"></script>
	 <script src="<%=basePath%>js/common/def.js"></script>
	 <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	 <script src="<%=basePath%>js/docs/holigitem.js"></script>
	 <script src="<%=basePath%>js/docs/pub_docper.js"></script>
	 <script src="<%=basePath%>js/validate.js"></script> 
	 <script src="<%=basePath%>js/common/loading.js"></script>
	 <jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
  </head>
  
  <body>
  <div id="showmore">
  	 <form action="" method="post" id="addorupdate"  style='display:inline;'>
  	 	&nbsp;&nbsp;<input type="button" id="plxiugai" value = "批量修改"/>
  	 	&nbsp;&nbsp;<input type="button" id="refresh" onclick="loadData()" value = "刷新"/>
  	 	&nbsp;&nbsp;<input type="button" id="close" onclick ="window.close();"value = "关闭"/>
  	 	
<!--  	 	<table class="tabsty" align="center">-->
<!--  	 		<tr>-->
<!--  	 		<td>描述</td>-->
<!--  	 		<td><input type="text" name="holidaygroup.describe" id="describe"/></td>-->
<!--  	 		<td>类型</td>-->
<!--  	 		<td><input type="text" name="holidaygroup.grouptype" id="grouptype"/></td>-->
<!--  	 		</tr>-->
<!--  	 	</table>-->
  	 	<input type="hidden" id="groupid" 			name="holigitem.groupid" />
  	 	<input type="hidden" id="holidayid" 			name="holigitem.holidayid" />
  	 </form>
  	</div>
  	 <DIV id=gridbox style="width: 100%;"></DIV>
    <jsp:include page="../inc/jsdef.jsp" />
    <SCRIPT>
    	basePath = '<%=basePath%>';
		$("#gridbox").height($(window).height()-$("#showmore").height());
  	</SCRIPT>
  </body>
</html>
