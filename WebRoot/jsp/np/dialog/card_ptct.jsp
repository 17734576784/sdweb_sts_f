<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'card_ptct.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  <style type="text/css">
	body{
		margin: 0 0 0 0;
		overflow: hidden;
	}
	.tabsty input{
		width: 130px;
	}
	.tabsty select {
		width: 130px;
		font-color: black;
	}
	input,select{
		font-size: 12px;
	}
	</style>
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script src="<%=basePath%>js/np/dialog/card_ptct.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  <script  src="<%=basePath%>js/common/loading.js"></script>
  <jsp:include page="../../../jsp/ydjsdef.jsp"></jsp:include>
  </head>
  
  <body>
  <table cellpadding="0" cellspacing="0" width="70%" class="tabsty" align="center">
  <tr>
  <td>ESAM表号:</td><td><input type="text" id="esam_no" readonly style="background: #eee;" /><button id="sel_meter">...</button></td>
  <td>区域号:</td><td><input type="text" id="area_no" readonly style="background: #eee;" /></td>
  <td>变比:</td><td><input type="text" id="ptct" /></td>
  </tr>
  </table>
  <center><button id="write_card">写卡</button></center>
  <script type="text/javascript">
  var NPCARD_OPTYPE_PTCT = "<%=SDDef.NPCARD_OPTYPE_PTCT%>";
  var YFF_METER_TYPE_NP = '<%=SDDef.YFF_METER_TYPE_NP%>';
  </script>
  </body>
</html>
