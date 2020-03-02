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
		<script src="<%=basePath%>js/np/dialog/selCons.js"></script>		
	</head>
	<body>
	<table class="tabinfo" align="center">
		<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable">筛选条件</td>	
       </tr>
	    <tr>
	    	<td class="tdr">所属供电所:</td><td><select id="orgId" name="orgId" onchange="getArea()" ></select></td>
			<td class="tdr">所属片区:</td><td><select id="areaId" name="areaId" ></select></td>
	    	<td class="tdr">客户名称:</td><td><input name="consName" id="consName" /></td>
	    </tr>	  
	    <tr>
		    <td class="tdr">客户编号:</td><td><input name="consNo" id="consNo" /></td>
		    <td class="tdr">自然村:</td><td><input name="village" id="village" /></td>
		    <td class="tdr">卡号:</td><td><input name="cardNo" id="cardNo" /></td>
		</tr>
	   	<tr><td colspan="6" style="height:3px; border: 0px;"></td></tr>
    	 
	</table>
    <table class="tabinfo" align="center">
		<tr>
			<td class="td_lable" style="width:80px;">客户列表</td>
			<td style="border: 0px;text-align: right">
			<button name="submit"  class="btn" onclick="search()">查询</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 	
	    	<button  name="btnRead"  class="btn" onclick="returnWin()">确定</button>
	    	</td></tr>
	</table>
	<div id=gridbox style="height:300px;width:640px; border:0px;"></div>
		
	<table width="100%" border="0" class="page_tbl">
    <tr><td id="pageinfo">&nbsp;</td></tr>
    </table>

	<jsp:include page="../../../jsp/ydjsdef.jsp"></jsp:include>
		<script type="text/javascript">
			var ComntUseropDef = {};
			ComntUseropDef.YFF_NPOPER_PAY = <%=com.kesd.comntpara.ComntUseropDef.YFF_NPOPER_PAY%>;
			ComntUseropDef.YFF_NPOPER_GPARASTATE = <%=com.kesd.comntpara.ComntUseropDef.YFF_NPOPER_GPARASTATE%>;
		</script>	
	</body>
</html>
	