<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'card_read.jsp' starting page</title>
    
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
		width: 150px;
		border: 1px;
		background: #eaeeef;
	}
	
	</style>
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script src="<%=basePath%>js/np/dialog/card_read.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  <script  src="<%=basePath%>js/common/loading.js"></script>
  <jsp:include page="../../../jsp/ydjsdef.jsp"></jsp:include>
  </head>
  
  <body>
  <%
  int i = 1;
  %>
  <table cellpadding="0" cellspacing="0" width="100%" class="tabsty">
  <tr>
  <td>ESAM表号:</td><td><input type="text" id="cf<%=i++%>" /></td>
  <td>旧区域号:</td><td><input type="text" id="cf<%=i++%>" /></td>
  <td>新区域号:</td><td><input type="text" id="cf<%=i++%>" /></td>
  </tr>
  <tr>
  <td>指令信息中卡号:</td><td><input type="text" id="cf<%=i++%>" /></td>
  <td>限定功率(0.1W):</td><td><input type="text" id="cf<%=i++%>" /></td>
  <td>报警金额(分):</td><td><input type="text" id="cf<%=i++%>" /></td>
  </tr>
  <tr>
  <td>变比:</td><td><input type="text" id="cf<%=i++%>" /></td>
  <td>无脉冲断电时间(秒):</td><td><input type="text" id="cf<%=i++%>" /></td>
  <td>电价(0.01分/度):</td><td><input type="text" id="cf<%=i++%>" /></td>
  </tr>
  <tr>
  <td>上电功能保护:</td><td><input id="cf<%=i++%>" /></td>
  <td>卡类型:</td><td><input type="text" id="cf<%=i++%>" /></td>
  <td></td><td></td>
  </tr>
  </table>
  <center><button id="read_card">读卡</button></center>
  <script type="text/javascript">
  var NPCARD_OPTYPE_SETPARA = "<%=SDDef.NPCARD_OPTYPE_SETPARA%>";
  var NPCARD_OPTYPE_PTCT = "<%=SDDef.NPCARD_OPTYPE_PTCT%>";
  var NPCARD_OPTYPE_AREA = "<%=SDDef.NPCARD_OPTYPE_AREA%>";
  var NPCARD_OPTYPE_FEE = "<%=SDDef.NPCARD_OPTYPE_FEE%>";
  
  var NPCARD_OPTYPE_READCARDTYPE = "<%=SDDef.NPCARD_OPTYPE_READCARDTYPE%>";
  </script>
  </body>
</html>
