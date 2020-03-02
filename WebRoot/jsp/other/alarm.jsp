<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
<%@page import="com.kesd.common.CommFunc"%>
<%@page import="com.kesd.dbpara.YffManDef"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
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
			input,select{
				font-size: 14px;
				width: 100%
			}
			.mtable{
				width: 300px;
				font-size:16px;
				margin-right: 100px;
			}
			.mtable td{
				height:40px;
			}
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script src="<%=basePath%>js/common/loading.js"></script>
		<script src="<%=basePath%>js/common/def.js"></script>
		<script src="<%=basePath%>js/other/alarm.js"></script>
	</head>
	<body>
	<table id="tabinfo" class="mtable" align="center" style="width: 300px;font-size:14px;">
   	<tr><td colspan="2" style="height:50px; border: 0px;"></td></tr>
   	<tr><td colspan="2" style="text-align: center;font-size:16px;font-weight: bolder;letter-spacing:2px;">报警控制</td></tr>
   	<tr>
    	<td class="tdr" width="40%">应用类型:</td>
    	<td ><select id="appType">
    	<%
		YffManDef yffman = (YffManDef)session.getAttribute(SDDef.SESSION_USERNAME);
		if(yffman != null) {
			/*
			switch(yffman.getApptype()) {
			case 7 :
				out.println("<option value=0>居民应用</option>");
				out.println("<option value=1>高压应用</option>");
				break;
			case 0 :
				out.println("<option value=0>居民应用</option>");
				break;
			case 1 :
				out.println("<option value=1>高压应用</option>");
				break;
			}
			*/
			byte app_type = yffman.getApptype();
	    	if((app_type & 0x1) != 0) {
  				out.println("<option value=0>居民应用</option>");
  			}
  			if((app_type & 0x2) != 0) {
  				out.println("<option value=1>高压应用</option>");
  			}
		}
		%>
    	</select></td>
    </tr>
	  <tr><td class="tdr">暂停(min):</td><td><input id="pause_period"/></td></tr>
	    <tr><td colspan="2" style="height:20px; border: 0px;"></td></tr>
    	<tr>
    		<td colspan="2" style="text-align: right;border: 0">
    		<%
    		out.println("<button class='btn' onclick='show();'>查看</button>&nbsp;&nbsp;&nbsp;&nbsp;");
    		if(yffman.getRank() == 0){
    			if (yffman.getCtrlFlag() == 1) {
    				out.println("<button class='btn' onclick='set();'>设置</button>&nbsp;&nbsp;&nbsp;&nbsp;");
    			}
    		}
    		%>
	    </td>
	    </tr>
	</table>
	<script type="text/javascript">
	var ComntUseropDef = {};
	ComntUseropDef.YFF_GET_PAUSEALARM = <%=com.kesd.comntpara.ComntUseropDef.YFF_GET_PAUSEALARM%>;
	ComntUseropDef.YFF_SET_PAUSEALARM = <%=com.kesd.comntpara.ComntUseropDef.YFF_SET_PAUSEALARM%>;
	</script>
	</body>
</html>
