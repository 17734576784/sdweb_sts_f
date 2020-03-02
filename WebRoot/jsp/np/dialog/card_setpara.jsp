<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.YDTable"%>
<%@page import="com.kesd.common.SDDef"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'card_setpara.jsp' starting page</title>
    
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
  <script src="<%=basePath%>js/np/dialog/card_setpara.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  <script  src="<%=basePath%>js/common/loading.js"></script>
  <jsp:include page="../../../jsp/ydjsdef.jsp"></jsp:include>
  </head>
  
  <body>
  <table cellpadding="0" cellspacing="0" width="100%" class="tabsty">
  <tr>
  <td>ESAM表号:</td><td><input type="text" id="esam_no" readonly style="background: #eee;" /><button id="sel_meter">...</button></td>
  <td>区域号:</td><td><input type="text" id="area_no" /></td>
  <td>限定功率(0.1W):</td><td><input type="text" id="pow_limit" /></td>
  <td>上电功能保护:</td><td><select id="powerup_prot"></select></td>
  
  </tr>
  <tr>
  <td>无脉冲断电时间(秒):</td><td><input type="text" id="nocycut_min" /></td>
  <td>变比:</td><td><input type="text" id="ptct" /></td>
  <td>电价(0.01分/度):</td><td><input type="text" id="feeprojId" /></td>
  <td>报警金额(分):</td><td><input type="text" id="yffalarmId" /></td>
  </tr>
  <!-- 
  <tr>
  <td>费率方案:</td>
  <td>
  <select id="feeprojId"></select>
  </td>
  <td colspan=8 id="feeproj_desc"></td>
  </tr>
  <tr>
  <td>报警方案:</td>
  <td>
  <select id="yffalarmId"></select>
  </td>
  <td colspan=8 id="yffalarm_desc"></td>
  </tr>
   -->
  </table>
  <center><button id="write_card">写卡</button></center>
  <script type="text/javascript">
  YDTable.TABLECLASS_YFFRATEPARA = '<%=YDTable.TABLECLASS_YFFRATEPARA%>';
  YDTable.TABLECLASS_YFFALARMPARA = '<%=YDTable.TABLECLASS_YFFALARMPARA%>';
  var NPCARD_OPTYPE_SETPARA = "<%=SDDef.NPCARD_OPTYPE_SETPARA%>";
  var YFF_METER_TYPE_NP = "<%=SDDef.YFF_METER_TYPE_NP%>";
  </script>
  </body>
</html>
