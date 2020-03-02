<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Residents Files</title>
    
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
  	<script src="<%=basePath%>js/docs/rtuparaResidentpara.js"></script>
  	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
  	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
  	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
  	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
  	<script src="<%=basePath%>js/common/modalDialog.js"></script>
  	<jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
	<jsp:include page="../inc/jsdef.jsp"></jsp:include>  	
  </head>
  
  <body>
     	<script type="text/javascript" src="<%=basePath%>js/css.js"></script>
  	<table cellpadding="0" cellspacing="0" height="24" width="100%" class="page_tbl_bg22">
		<tr>
			<td width=50>&nbsp;Name:</td>
			<td>
			<input type="text" name="search_condition" value="" id="search_condition" style="width: 110px;">
			<input type="button" name="search" value="Inquiry" id="search" onclick='search();' />
			<jsp:include page="../inc/btn_pldel.jsp"></jsp:include>
			
			<jsp:include page="../inc/btn_tianjia.jsp"></jsp:include>
			&nbsp;&nbsp;
			Terminal:&nbsp;<select id="rtuId"><option value="-1">---Select---</option></select>
			</td>
<!-- 			<td align="right"> -->
<!--				<form style="display: inline;" method="post" action="<%=basePath%>/excel/docExcel.action" name="toxls">-->
<!--					<input type="button" value="excel" class="btnsty" onclick='gridopt.toExcel(1, 0);' />-->
<!--					<input type="hidden" id="excPara" name="excPara" />-->
<!--					<input type="hidden" id="header" name="header" />-->
<!--					<input type="hidden" id="vfreeze" name="vfreeze" />-->
<!--					<input type="hidden" id="hfreeze" name="hfreeze" />-->
<!--				</form>-->
<!--</td> -->
		</tr>
	</table>
	
  	<DIV id=gridbox style="width: 100%;"></DIV>
  	<table width="100%" border="0" class="page_tbl">
    <tr><td id="pageinfo">&nbsp;</td><td align="right" id="soh"></td><td width="15"></td></tr>
    </table>
  	<SCRIPT>
  		$("#showmore").height($(".tabsty").height()+50);
		$("#gridbox").height($(window).height()-$("#showmore").height()-50);
  	</SCRIPT>
  </body>
</html>
