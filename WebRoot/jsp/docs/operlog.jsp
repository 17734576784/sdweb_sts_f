<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>Conspara Doc</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/dhtmlx/dhtmlxgrid.css">
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
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  <script>
  	var basePath = '<%=basePath%>';
  </script>
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/common/gridopt.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>  
  <script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>  
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  <script type="text/javascript" src="<%=basePath%>js/common/gridopt.js"></script>
  <script type="text/javascript" src="<%=basePath%>js/common/jsonString.js"></script>
  <script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="<%=basePath%>js/docs/log.js"></script>
  <script type="text/javascript" src="<%=basePath%>js/common/DateFun.js"></script>
  <script type="text/javascript" src="<%=basePath%>js/common/initDate.js"></script>
  <script type="text/javascript" src="<%=basePath%>js/common/loading.js"></script>
  <jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
  </head>
  
  <body>
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
  <td>
   	<table cellpadding="0" cellspacing="0" height="24" width="100%" class="page_tbl_bg22">
  		<tr>
  		<td width=120 align="left" nowrap>&nbsp;Operator Name：</td>
            <td width="120" align="left" nowrap>
			<select name="opername" id="opername" style="width: 110px;"><option value="-1">All</option></select>
            </td>
            <td width="100" align="left" nowrap>Operator Type：</td>
            <td width="120" align="left" nowrap>
			<select id="opertype" style="width: 110px;">
            <option value="-1">All</option>
            <option value="0">LOG_LOGIN</option>
            <option value="1">LOG_LOGOUT</option>
            <option value="2">LOG_ADD</option>
            <option value="3">LOG_UPDATE</option>
            <option value="4">LOG_DELETE</option>
            <option value="5">LOG_SET</option>
            </select> 
  			<td width="70" align="left" nowrap>Start Time：</td> <td width="150" align="left" nowrap><input type="text" name="sdate" id="sdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'%y-%M-%d %H:%m',isShowClear:'false'});" style="width:140px;"/></td>
            <td width="70" align="left" nowrap>End Time：</td> <td width="150" align="left" nowrap><input type="text" name="edate" id="edate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'%y-%M-%d %H:%m',isShowClear:'false'});" style="width:140px;"/></td>
           	<td>			
			<input type="button" name="search" value="Inquiry" id="search"'/>&nbsp;&nbsp;
			</td>
			<td align="right" style="display: none;">
	 			<jsp:include page="../inc/btn_excel.jsp"></jsp:include>
	 			&nbsp;&nbsp;&nbsp;&nbsp;
	 		</td>
		</tr>
	</table>
<DIV id=gridbox style="width: 100%;"></DIV>
<table width="100%" border="0" class="page_tbl">
  <tr>
    <td id="pageinfo">&nbsp;</td>
    <td align="right" id="soh"></td>
    <td width="15"></td>
  </tr>
</table>
<input type="hidden" id="id"/>
</td></tr></table>
<jsp:include page="../inc/jsdef.jsp" />
<SCRIPT>
 	$("#showmore").height($(".tabsty").height());
	$("#gridbox").height($(window).height()-$("#showmore").height()-53);
	var SDDef = {};
	SDDef.APPTYPE_JC = "<%=SDDef.APPTYPE_JC%>";
	SDDef.SUCCESS = "<%=SDDef.SUCCESS%>";
	SDDef.FAIL = "<%=SDDef.FAIL%>";
</SCRIPT>
  </body>
</html>
