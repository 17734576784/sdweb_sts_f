<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'key.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script src="<%=basePath%>js/np/tool/key.js"></script>
  </head>
  
  <body>
  <center style="padding-top: 20px;">
    <button id="readcard">读卡</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <button id="suocard">灰锁</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <button id="unsuocard">解扣</button>
  </center>
  <script type="text/javascript">
  var YFF_METER_TYPE_NP = '<%=SDDef.YFF_METER_TYPE_NP%>';
  var NPCARD_OPTYPE_GRAYLOCK = '<%=SDDef.NPCARD_OPTYPE_GRAYLOCK%>';
  var NPCARD_OPTYPE_GRAYUNLOCK = '<%=SDDef.NPCARD_OPTYPE_GRAYUNLOCK%>';
  </script>
  </body>
</html>
