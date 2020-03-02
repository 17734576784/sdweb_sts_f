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
		<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
		<script src="<%=basePath%>js/common/dhtmlxgrid_page.js"></script>
		<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script src="<%=basePath%>js/common/def.js"></script>
		<script src="<%=basePath%>js/common/loading.js"></script>
		<script src="<%=basePath%>js/common/cookie.js"></script>
		<script src="<%=basePath%>js/spec/dialog/selCons.js"></script>
	</head>
	<body>
	<table class="tabinfo" align="center">
		<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable">筛选条件</td>
       </tr>
	    <tr>
			<td class="tdr">所属供电所:</td><td><select id="orgId" name="orgId" onchange="getLineFzMan()"></select></td>
	    	<td class="tdr">所属联系人:</td><td><select id="fzmanId" name="fzmanId"></select></td>
	    	<td class="tdr">终端名称:</td><td><input name="rtuName" id="rtuName" /></td>
	    </tr>	  
	    <tr>
	    	<td class="tdr">客户名称:</td><td><input name="consName" id="consName" /></td>
		    <td class="tdr">客户编号:</td><td><input name="busiNo" id="busiNo" /></td>
		    <td class="tdr">终端地址:</td><td><input name="rtu_addr" id="rtu_addr" /></td>
		</tr>
		<tr>
			<td class="tdr">移动电话:</td><td><input name="telNo1" id="telNo1" /></td>
			<td colspan="4"></td>
		</tr>
	   	<tr><td colspan="6" style="height:3px; border: 0px;"></td></tr>
    	 
	</table>
	
		<table class="tabinfo" align="center">
			<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
	  		<tr>
	    	   	<td class="td_lable" style="width:80px;">用户列表</td>
	    	   	<td style="border: 0px;text-align: right">
				<button id="search"  class="btn">查询</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 	
		    	<button  id="btnOK"  class="btn">确定</button>
		    	</td>
	       </tr>
       </table>
       <div id=gridbox style="height:270px;width:100%;margin-left:3px;margin-right:3px;"></div>
       
	
		
	<table width="99%" border="0" class="page_tbl" align="center">
    <tr><td id="pageinfo">&nbsp;</td></tr>
    </table>
   
	<jsp:include page="../../../jsp/ydjsdef.jsp"></jsp:include>
	</body>
</html>
	