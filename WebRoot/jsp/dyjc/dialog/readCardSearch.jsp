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
		<title>用户选择
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<style type="text/css">
		body{
			overflow: hidden;
		}	
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
		<script src="<%=basePath%>js/dyjc/dialog/readCardSearch.js"></script>			
	</head>
	<body>
	<table class="tabinfo" align="center">
    	 <tr>
    	   	<td class="td_lable" style="width:90px">客户列表</td>
    	   	<td style="text-align: right;border: 0px; width:550px">	 	
	    		<button  name="btnRead"  class="btn" onclick="returnWin()">确定</button>
	    	</td>  		
       </tr>
	</table>
    <table align="center" cellpadding="0" cellspacing="0" width="644px;">
		<tr><td><DIV id=gridbox style="width:100%;height:285px;"></DIV> </td></tr>
		<tr><td style="height:20px;font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;" id="pageinfo">&nbsp;</td></tr>
	</table>
	<jsp:include page="../../../jsp/ydjsdef.jsp"></jsp:include>
	</body>
</html>
	