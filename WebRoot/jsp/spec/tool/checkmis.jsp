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
    <title>My JSP 'checkmis.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<style type="text/css">
	input,select{
		width:110px;
	}
	#dzsc input{
		background: #ccc;
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
	<script  src="<%=basePath%>js/spec/tool/comm.js"></script>
	<script  src="<%=basePath%>js/spec/tool/checkmis.js"></script>
	<script  src="<%=basePath%>js/common/jsonString.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script  src="<%=basePath%>js/common/DateFun.js"></script>
	<script  src="<%=basePath%>js/common/dateFormat.js"></script>
  </head>
  
  <body>
    <div id=gridbox style="width: 100%;"></div>
    <table class="tabinfo" align="center" width="99%">
    <tr><td class="td_lable" style="height:22px;text-align: left" colspan="11" id="total"></td></tr>
    <tr><td class="td_lable" style="height:22px; width: 66px;" align="center">筛选条件</td></tr>
    <tr>
    	<td class="tdr">起始时间:</td>
     	<td><input id=sdate onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',maxDate:'%y-%M-%d %H:%m',isShowClear:'false'});" readonly/></td>
     	<td class="tdr">结束时间:</td>
     	<td><input id=edate onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',maxDate:'%y-%M-%d %H:%m',isShowClear:'false'});" readonly/></td>
     	<td class="tdr">营业点:</td><td><select id="org"></select></td>
	    <td class="tdr">操作员:</td><td><input type="text" id="czy"></td>
	    <td class="tdr">异常类型:</td>
	    <td><select id="err_type"><option value="0">所有失败记录</option><option value="1">上传失败记录</option><option value="2">冲正失败记录</option></select></td>
	    <td><button id="search">查询</button></td>
	</tr>
	<tr><td style="height: 5px;border: 0px;"></td></tr>
	<tr><td class="td_lable" style="height:22px; width: 66px;" align="center">对账上传</td></tr>
	</table>
	<table class="tabinfo" align="center" width="99%" id="dzsc">
    <tr>
		<td class="tdr">客户编号:</td><td><input type="text" id="khbh" readonly/></td>
		<td class="tdr">客户名称:</td><td><input type="text" id="khmc" style="width:140px;" readonly/></td>
		<td class="tdr">客户地址:</td><td><input type="text" id="khdz" readonly /></td>
		<td class="tdr">操作日期:</td><td><input type="text" id="czrq" style="width:180px;" readonly/></td>
	</tr>
	<tr>
		<td class="tdr">缴费金额:</td><td><input type="text" id="jfje" readonly/></td>
		<td class="tdr">流水号:</td><td><input type="text" id="lsh" style="width:140px;" readonly/></td>
		<td class="tdr">上传标志:</td><td><input type="text" id="scbz" readonly/></td>
		<td class="tdr">成功标志:</td><td><input type="text" id="cgbz" style="width:180px;" readonly/></td>
	</tr>
	<tr><td style="border: 0px;text-align: right;" colspan="8"><button id="upload">上传</button></td></tr>
    </table>
    <jsp:include page="../../inc/jsdef.jsp" />
  	<SCRIPT>
		$("#gridbox").height($(window).height()-$(".tabsty").height()-180);
		var SDDef = {};
		SDDef.YFF_OPTYPE_REVER = <%=SDDef.YFF_OPTYPE_REVER%>;
		
		var ComntUseropDef = {};
		ComntUseropDef.YFF_GYOPER_MIS_CHECKPAY 	= <%=ComntUseropDef.YFF_GYOPER_MIS_CHECKPAY%>;
		ComntUseropDef.YFF_GYOPER_MIS_CHECKREVER = <%=ComntUseropDef.YFF_GYOPER_MIS_CHECKREVER%>;
		
		var Dict = {};
		Dict.DICTITEM_YFFOPTYPE = "<%=Dict.DICTITEM_YFFOPTYPE%>";
		
		<%
		Date date = new Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
		out.println("var _today = " + sdf.format(date));
		%>
  	</SCRIPT>
  </body>
</html>
