<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.comnt.ComntProtMsg"%>
<%@page import="com.kesd.comntpara.ComntUseropDef"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'ydjl.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  <style type="text/css">
	body{
		margin: 0 0 0 0;
		overflow: hidden;
	}
	.tabsty input{
		width: 100px;
	}
	.tabsty select {
		width: 90px;
		font-color: black;
	}
	input,select{
		font-size: 12px;
	}
	</style>
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script src="<%=basePath%>js/np/dialog/ydjl.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  <script  src="<%=basePath%>js/common/loading.js"></script>
  <jsp:include page="../../../jsp/ydjsdef.jsp"></jsp:include>
  </head>
  
  <body>
   <table align="center" style="margin:0px 0px 0 0; padding: 0 0 0 0; width: 100%; text-align: center; font-size: 12px;">
  <tr>
    <td style="margin:0 0 0 0; padding: 0 0 0 0;"><fieldset style="text-align: center;">
        <legend style="color:#069;">
        <label for="chkmeterconf">[仅可召测]&nbsp;用电记录</label>
        <select id="group">
	        <option value=-1>全部</option>
	        <option value=0 selected>第一组(01-10条)</option>
	        <option value=1>第二组(11-20条)</option>
	        <option value=2>第三组(21-30条)</option>
	        <option value=3>第四组(31-40条)</option>
	        <option value=4>第五组(41-50条)</option>
        </select>
        </legend>
        <DIV id=gridbox style="width: 100%;"></DIV>
      </fieldset></td>
      </tr></table>

    <jsp:include page="../../inc/jsdef.jsp" />
    <script type="text/javascript">
    $("#showmore").height($(".tabsty").height());
	$("#gridbox").height($(window).height()-$("#showmore").height()-40);
	var ComntProtMsg = {};
	ComntProtMsg.YFF_CALL_DL645_YDJL_FIR = <%=ComntProtMsg.YFF_CALL_DL645_YDJL_FIR%>;
	ComntProtMsg.YFF_CALL_HNQF_YDJL_FIR		= <%=ComntProtMsg.YFF_CALL_HNQF_YDJL_FIR%>;
	
	var ComntUseropDef = {};
	ComntUseropDef.YFF_READDATA = <%=ComntUseropDef.YFF_READDATA%>;
	ComntUseropDef.YFF_NPOPER_WriteDB = <%=ComntUseropDef.YFF_NPOPER_WriteDB%>;
	 
	
	var SDDef = {};
	SDDef.YFF_NP_CARDTYPE_HNTY  = <%=SDDef.YFF_NP_CARDTYPE_HNTY%>;
	
	
	<%
	Date date = new Date();
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
	%>
	var _ymd = <%=sdf.format(date)%>;
    </script>
  </body>
</html>
