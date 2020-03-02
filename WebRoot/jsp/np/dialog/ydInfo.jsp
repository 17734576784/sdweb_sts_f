<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<i18n:bundle baseName="<%=SDDef.BUNDLENAME%>" id="bundle" locale="<%=WebConfig.app_locale%>" scope="application" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>用电信息
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<style type="text/css">
			input, select {
				font-size: 12px;
				width: 120px;
			}
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
		<script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>		
  		<script src="<%=basePath%>js/common/gridopt.js"></script>
		<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>		
		<script src="<%=basePath%>js/common/dhtmlxgrid_page.js"></script>		
		<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>		
		<script src="<%=basePath%>js/common/def.js"></script>
		<script src="<%=basePath%>js/common/loading.js"></script>
		<script src="<%=basePath%>js/common/cookie.js"></script>
		<script src="<%=basePath%>js/np/dialog/ydInfo.js"></script>		
	</head>
	<body>
    <table class="tabinfo" align="center">
		<tr>
			<td class="td_lable" style="width:120px;">客户详细用电信息</td>
			<td style="border: 0px;text-align: right">
	    	</td></tr>
	</table>
	<div id=gridbox style="height:320px;width:640px; border:0px;"></div>
	
	<!-- 	
	<table width="100%" border="0" class="page_tbl">
    <tr><td id="pageinfo">&nbsp;</td></tr>
    </table>
	 -->
	 
	<jsp:include page="../../../jsp/ydjsdef.jsp"></jsp:include>
		<script type="text/javascript">
			var ComntUseropDef = {};
			ComntUseropDef.YFF_NPOPER_PAY = <%=com.kesd.comntpara.ComntUseropDef.YFF_NPOPER_PAY%>;
			ComntUseropDef.YFF_NPOPER_GPARASTATE = <%=com.kesd.comntpara.ComntUseropDef.YFF_NPOPER_GPARASTATE%>;
		</script>	
	</body>
</html>
	