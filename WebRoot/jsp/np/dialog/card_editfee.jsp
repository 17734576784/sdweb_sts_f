<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'card_editfee.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
	<style type="text/css">
	body{
		margin: 0 0 0 0;
		overflow: hidden;
	}

	input,select{
		font-size: 12px;
		width: 240px;
		font-color: black;
	}
	</style>
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script src="<%=basePath%>js/np/dialog/card_editfee.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  <script  src="<%=basePath%>js/common/loading.js"></script>
  <jsp:include page="../../../jsp/ydjsdef.jsp"></jsp:include>
  </head>
  
  <body>
  <table cellpadding="0" cellspacing="0" width="75%" class="tabsty" align="center">
  <tr>
  <td>区域:</td><td>
  <select id="area"></select>
  </td>
  <td>电价(0.01分/度):</td><td>
  <input type="text" id="fee" />
  </td>
  </tr>
  </table>
  <center><button id="write_card">写卡</button></center>
  <script type="text/javascript">
  var NPCARD_OPTYPE_FEE = '<%=SDDef.NPCARD_OPTYPE_FEE%>';
  var YFF_METER_TYPE_NP = '<%=SDDef.YFF_METER_TYPE_NP%>';
  </script>
  </body>
</html>
