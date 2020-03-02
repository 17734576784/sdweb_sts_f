<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
<%@page import="com.kesd.common.CommFunc"%>
<%@page import="com.kesd.dbpara.YffManDef"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		
		<script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script src="<%=basePath%>js/common/loading.js"></script>
		<script src="<%=basePath%>js/common/def.js"></script>
		<script src="<%=basePath%>js/common/cookie.js"></script>
		<script src="<%=basePath%>js/dialog/selPrtTemplate.js"></script>
		<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
		<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_data.js"></script>
		<script src="<%=basePath%>js/validate.js"></script>
 	
 		 <style type="text/css">
			
			.mytbl{
				margin-top:8px;
				border-top: 1px solid #ccc;
				border-left: 1px solid #ccc;
				border-right: 1px solid #ccc;
			}	
			.gridbox{
				border: 1px solid #ccc; 
				width: 98%;
				height:250px;
			}
		</style>
		
		
	</head>
	<body>	
   	
    	<table class="mytbl" cellpadding="0" cellspacing="0" align="center" style="width:98%;font-size: 12px;height:22px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><tr>
       		<td style="width:30px; "><img src="<%=basePath%>images/doctitle.png" style="margin-top: 3px;margin-left: 5px;"/></td>
          	<td style="width:100px;padding-top: 5px; font-size: 12px;" >Template List</td>
          	<td style="text-align: right">
          		<input type="checkbox" id=chksetcookie /><label for="chksetcookie">Remember choice </label>&nbsp;&nbsp;&nbsp;&nbsp;
				<button id="btnOK" class=btn>OK</button>&nbsp;&nbsp;&nbsp;&nbsp;
			</td></tr>
       	</table>
       	 	
 		<center><DIV id=gridbox1 class="gridbox"></DIV></center>
	 
   
    
	
	
	
	<script type="text/javascript">
	
	$("#div_pg").height(300);
	var ComntUseropDef = {};
	ComntUseropDef.YFF_GET_PAUSEALARM = <%=com.kesd.comntpara.ComntUseropDef.YFF_GET_PAUSEALARM%>;
	ComntUseropDef.YFF_SET_PAUSEALARM = <%=com.kesd.comntpara.ComntUseropDef.YFF_SET_PAUSEALARM%>;
	
	var mygrid = new dhtmlXGridObject('gridbox1');
//	var mygrid2 = new dhtmlXGridObject('gridbox2');
//	var mygrid3 = new dhtmlXGridObject('gridbox3');
//	var mygrid4 = new dhtmlXGridObject('gridbox4');
//	var mygrid5 = new dhtmlXGridObject('gridbox5');
//	var mygrid6 = new dhtmlXGridObject('gridbox6');
//	var mygrid7 = new dhtmlXGridObject('gridbox7');
//	var mygrid8 = new dhtmlXGridObject('gridbox8');
	

	
	</script>
	</body>
</html>
