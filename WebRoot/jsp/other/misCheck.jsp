<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.Dict"%>
<%@page import="com.kesd.comntpara.ComntUseropDef"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'misCheck.jsp' starting page</title>
    <style type="text/css">
	input,select{
		width:110px;
	}
	</style>
    <link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
	<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>		
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
	<script  src="<%=basePath%>js/common/def.js"></script>
	<script  src="<%=basePath%>js/other/misCheck.js"></script>
	<script  src="<%=basePath%>js/common/jsonString.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script  src="<%=basePath%>js/common/DateFun.js"></script>
	<script  src="<%=basePath%>js/common/dateFormat.js"></script>
  </head>
  
  <body>
  	 <div id="top" style="height:22px; font-size: 12px; margin-left:1%" align="left">
  	 	对账日期:
  	 	<input id="sdate" style="font-size : 12px"  onFocus="var date = getMaxDate(); WdatePicker({dateFmt:'yyyy年MM月dd日',maxDate : date,isShowClear:'false'});" readonly/>
  	 	&nbsp;&nbsp;&nbsp;&nbsp;
  	 	<button style="font-size : 12px; height:20px" id="search">查询</button>
  	 	&nbsp;&nbsp;
  	 	<button style="font-size : 12px; height:20px" id="check">对账</button>
  	 </div>
  	 <div id=gridbox style="width: 100%;"></div>
    
  	<script type="text/javascript">
		$("#gridbox").height($(window).height()- $("#top").height());
		
		var ComntUseropDef = {};
		ComntUseropDef.YFF_GDYOPER_MIS_CHECKPAY = <%=ComntUseropDef.YFF_GDYOPER_MIS_CHECKPAY%>;
		
		var Dict = {};
		Dict.DICTITEM_YFFOPTYPE = "<%=Dict.DICTITEM_YFFOPTYPE%>";
		<%
		Date date = new Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
		out.println("var _today = " + sdf.format(date));
		%>
  	</script> 
  </body>
</html>
