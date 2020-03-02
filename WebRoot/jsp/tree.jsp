<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.dbpara.YffManDef"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page	import="com.kesd.common.WebConfig"%>
<%@page	import="com.kesd.common.YDTable"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String autoshow_type = request.getParameter("autoshow_type");
%>
<script type="text/javascript">
	var autoshow_type	= 	"<%=autoshow_type%>";
	var dy_autoshow 	= 	<%=WebConfig.autoShow.get("dy_show")%>;		//获取低压自动显示参数配置数组
	var spec_autoshow 	= 	<%=WebConfig.autoShow.get("spec_show")%>;	//获取高压自动显示参数配置数组
	var np_autoshow 	= 	<%=WebConfig.autoShow.get("np_show")%>;		//获取农排自动显示参数配置数组
	
	var node_type = {};
	node_type.GLOBAL_KE				=	'global';
	node_type.TABLECLASS_ORGPARA	=	'<%=YDTable.TABLECLASS_ORGPARA%>';
	node_type.TABLECLASS_LINEFZMAN	=	'<%=YDTable.TABLECLASS_LINEFZMAN%>';
	node_type.TABLECLASS_CONSPARA	=	'<%=YDTable.TABLECLASS_CONSPARA%>';
	node_type.TABLECLASS_RTUPARA	=	'<%=YDTable.TABLECLASS_RTUPARA%>';
		
</script>
  
	<!-- 管理结构树 -->
    <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxtree.css">
    <script src="<%=basePath%>js/common/def.js"></script>
    <script src="<%=basePath%>js/tree.js"></script>
    <script src="<%=basePath%>js/dhtmlx/tree/dhtmlxcommon.js"></script>
    <script src="<%=basePath%>js/dhtmlx/tree/dhtmlxtree.js"></script>
  <table cellpadding="0" border=0 style="width:100%; border-right: 5px solid; border-right-color: #98D0D0;" cellspacing="0" id="manage_tb1">
	<tr class="page_tbl_bg22">
	<td>
	<table width="100%"><tr style="font-size:12px;">
	<td width=20><img src="<%=basePath%>images/bullet.gif"/></td><td><i18n:message key="tree.gljg" /></td><td onclick="refreshTree()" align="right"><img src="<%=basePath%>images/reload.gif" title="<i18n:message key="refresh" />" /></td>
	</tr></table>
	</td>
	
	</tr><tr>
	<td>
	<div id="tree" style="overflow: auto; width: 206px; background: white; padding-top: 2px;"></div>
	</td>
	</tr>
  </table>