<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>补打发票</title>
    <link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />		
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
	<style type="text/css">
	input,select{
	width:120px;
	}
	</style>
	<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script  src="<%=basePath%>js/common/modalDialog.js"></script>
	<script  src="<%=basePath%>js/common/def.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
	<script  src="<%=basePath%>js/np/print/bdfp.js"></script>
	<!-- 新加入的路径 -->
	<script  src="<%=basePath%>js/common/dateFormat.js"></script>
	<script  src="<%=basePath%>js/common/jsonString.js"></script>
	<script  src="<%=basePath%>js/common/DateFun.js"></script>
	<script  src="<%=basePath%>js/common/gridopt.js"></script>
    <script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
    
    <script  src="<%=basePath%>js/np/print/print.js"></script>
    <!--加入将数字转换成大写字母的js-->
    <script  src="<%=basePath%>js/common/NumtoChar.js"></script>
	<jsp:include page="../../inc/jsdef.jsp" />
  </head>
  
  <body>
  <table width="100%" cellpadding="0" cellspacing="0">
    	<tr><td align="center">
    	<div id=gridbox style="height:420px;border:0px;width:99%;"></div>
    	</td></tr>
    </table>
    <table width="100%" border="0" class="page_tbl">
    <tr><td id="pageinfo">&nbsp;</td></tr>
    </table>
    
    <table id="tabinfo" class="tabinfo" align="center">
    	<tr>
	    	<td class="td_lable" style="height:22px;" id="td2">筛选条件</td>
	    </tr>
   		<tr>
	    	<td class="tdr">起始时间:</td>
	     	<td><input id=sdate name=sdate class="roinput" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',maxDate:'%y-%M-%d %H:%m',isShowClear:'false'});"/></td>
	     	<td class="tdr">结束时间:</td>
	     	<td><input id=edate name=edate class="roinput" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',maxDate:'%y-%M-%d %H:%m',isShowClear:'false'});"/></td>
	    	<td class="tdr" width="120px;">客户编号:</td><td><input type="text" id="khbh"/></td>
	    	<td class="tdr">客户名称:</td><td><input type="text" id="khmc"/></td>
	    </tr>
	    <tr>
	    	<td class="tdr">操作员:</td><td><input type="text" id="czy"/></td>
	     	<td class="tdr">营业点:</td><td><select id="org"></select></td>
	     	<td class="tdr">片区:</td><td><select id="area"><option value=-1>所有</option></select></td>
	     	<td class="tdr"></td>
	     	<td></td>
	    </tr>
	    <tr>
	    <td class="tdr" colspan="8">
		    <button class="btn" id="search">查询</button>&nbsp;&nbsp;&nbsp;&nbsp;
		    <button class="btn" id="toprint">补打</button>
	    </td>
	    </tr>
	</table>
	<script type="text/javascript">
	$("#gridbox").height($(window).height() - $("#tabinfo").height() - 28);
	</script>
  </body>
</html>
