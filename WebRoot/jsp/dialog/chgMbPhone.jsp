<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%@page import="com.kesd.common.SDDef"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>更改手机号</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/dialog/chgMbPhone.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/def.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/validate.js"></script>
	<jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
	<jsp:include page="../../jsp/inc/jsdef.jsp"></jsp:include>
	<script type="text/javascript">
  		var ghsj = "<i18n:message key="doc.ghsj" />";
  		SDDef.YFF_DY_CHGPARATYPE_MOBILE = <%= SDDef.YFF_DY_CHGPARATYPE_MOBILE%>;
  		SDDef.YFF_GY_CHGPARATYPE_MOBILE = <%= SDDef.YFF_GY_CHGPARATYPE_MOBILE%>;
  		SDDef.YFF_NP_CHGPARATYPE_OTHER  = <%= SDDef.YFF_NP_CHGPARATYPE_OTHER%>;
  		SDDef.YFF_NP_CHGPARATYPE_MOBILE = <%= SDDef.YFF_NP_CHGPARATYPE_MOBILE%>
  	</script>
	<style type="text/css">
	body{
		margin: 0 0 0 0;
		overflow: hidden;
	  	background: #eaeeef;
	}	
	.tabsty{
		width:90%;
	}
	.tabsty input {
		width: 130px;
	}
	input{
		font-size: 12px;
	}
	</style>
  </head>
  
  <body>
	<table class="tabsty" align="center">
		<tr>
			<td colspan="2" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;">
	    		<table cellpadding="0" cellspacing="0"><tr><td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td><td id="tdtitle" style="border: 0px; font-size: 12px;">更改手机号</td></tr></table>
	    	</td>
	    </tr>
		<tr><td>客户名称:</td><td><input type="text" id="username" readonly style="background:#eee;border: 1px solid #808080; width:150px"/></td></tr>
		<tr><td>原手机号:</td><td><input type="text" id="old_mbphone" readonly style="background:#eee;border: 1px solid #808080; width:150px"/></td></tr>
		<tr><td>新手机号:</td><td><input type="text" id="new_mbphone" style="border: 1px solid #808080; width:150px"/></td></tr>
	</table>
	<center>
	<button id="xiugai">确定</button>&nbsp;
	<button id="close">关闭</button>
	</center>
  </body>
</html>
