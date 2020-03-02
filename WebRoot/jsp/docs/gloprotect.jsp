<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'gloprotect.jsp' starting page</title>
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
  <script type="text/javascript">
  	var basePath = '<%=basePath%>';
  </script>
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/docs/gloprotect.js"></script>
  <script src="<%=basePath%>js/docs/pub_docper.js"></script>
  <script src="<%=basePath%>js/common/gridopt.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script src="<%=basePath%>js/common/dateFormat.js"></script>
  <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
  <script src="<%=basePath%>js/validate.js"></script>
  <script src="<%=basePath%>js/common/initDate.js"></script>
  <jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
  </head>
  
  <body>
    <DIV id=gridbox style="width: 100%;"></DIV>
    <table width="100%" border="0" class="page_tbl">
    <tr><td id="pageinfo">&nbsp;</td><td align="right" id="soh"><i18n:message key="doc.hide_detail" /></td><td width="15"><img src="../../images/mmd.gif" alt="hide" onClick="gridopt.showOrHide(this);" style="cursor: pointer;"/></td></tr>
    </table>
    <div id="showmore">
    <form action="" method="post" id="addorupdate"  style='display:inline;'>
    <br/>
    <table class="tabsty" align="center">
    	<tr>
		   	<td><i18n:message key="doc.yylx" />: </td><td>
		   	<select id="appType1" disabled></select>
		   	<input type="hidden" name="gloprotect.appType" id="appType"/>
		   	</td>
		   	<td><i18n:message key="doc.bdksrq" />: </td>
	    	<td>
	    		<input type="text" id="bgDateF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.ymd.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />'});"/>
	    		<input type="hidden" name="gloprotect.bgDate" id="bgDate"/>
	    	</td>
	    	<td><i18n:message key="doc.bdkssj" />: </td>
	    	<td>
	    		<input type="text" id="bgTimeF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.hms.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />'});"/>
	    		<input type="hidden" name="gloprotect.bgTime" id="bgTime"/>
	    	</td>
    	</tr>
    	
    	<tr>
    		<td><i18n:message key="doc.sybz" />: </td><td><select name="gloprotect.useFlag" id="useFlag"></select></td>
		   	<td><i18n:message key="doc.bdjsrq" />: </td>
	    	<td>
	    		<input type="text" id="edDateF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.ymd.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />'});"/>
	    		<input type="hidden" name="gloprotect.edDate" id="edDate"/>
	    	</td>
	    	<td><i18n:message key="doc.bdjssj" />: </td>
	    	<td>
	    		<input type="text" id="edTimeF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.hms.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />'});"/>
	    		<input type="hidden" name="gloprotect.edTime" id="edTime"/>
	    		<input type="hidden" id="id" name="gloprotect.id" />
	    	</td>
    	</tr>
    </table>
    </form>
    <center>
    <jsp:include page="../inc/btn_xiugai.jsp" />
    <jsp:include page="../inc/btn_excel.jsp"></jsp:include>
    </center>
    
    </div>
    <jsp:include page="../inc/jsdef.jsp" />
    <SCRIPT>
		$("#showmore").height($(".tabsty").height());
		$("#gridbox").height($(window).height()-$("#showmore").height()-55);
		var grid_title = "<i18n:message key="page.jjrbd" />";
		var date_con = "<i18n:message key="doc.date_cond" />"
	</SCRIPT>
  </body>
</html>
