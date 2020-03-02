<%@ page language="java" import="com.kesd.util.I18N" pageEncoding="utf-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<!DOCTYPE HTML  PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>Conspara Doc</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
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
	.page_tbl_bg22 tr td {
		height:20px !important;
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
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  <script type="text/javascript" src="<%=basePath%>js/common/gridopt.js"></script>
  <script type="text/javascript" src="<%=basePath%>js/common/jsonString.js"></script>
  <script type="text/javascript" src="<%=basePath%>js/docs/importMeterArchive.js"></script>
  <script type="text/javascript" src="<%=basePath%>js/common/loading.js"></script>
  
  <jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
  </head>
  
  <body>
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
  <td>
   	<table cellpadding="0" cellspacing="0" height="24" width="100%" class="page_tbl_bg22">
  		<tr>
  			<td width=40></td>
            <td style="float: left">
            <form id="formMeterData" method="post" action="<%=basePath%>ajax/actImportMeterData.action" name ="excels" enctype="multipart/form-data">
				<input type="file" id="fileName" style="float: left" name="fileName"  onchange = "loadPathName();"/>
				<input id="name"   name="name" style="float: left" type="hidden"/>
			</form>
            </td>
           	<td>			
			<input type="button" name="search" value="Aperçu" id="search"'/>&nbsp;&nbsp;
			</td>
  			<td width=80 align="left" nowrap>Nom de l'organisation:</td>
            <td width="120" align="left" nowrap>
			<select id="orgname" style="width: 110px;" onchange = "initConsname();"><option value="-1">Tous</option></select>
            </td>
            <td width="140" align="left" nowrap>Zone résidentielle:</td>
            <td width="120" align="left" nowrap>
			<select id="consname" style="width: 110px;" onchange="initResidentname()"><option value="-1">Tous</option></select> 
            </td>
            <td width="120" align="left" nowrap>Nom résident:</td>
            <td width="120" align="left" nowrap>
			<select id="residentname" style="width: 110px;"><option value="-1">Tous</option></select> 
            </td>
            <td width="120" align="left" nowrap>Projet tarifaire:</td>
            <td width="130" align="left" nowrap>
			<select id="tariffProject" style="width: 110px;"><option value="-1">Tous</option></select> 
            </td>
           	<td align="right" >			
			<input type="button" value="Importer des bibliothèques" id="importToDB" onclick="importGridDataToDB();"/>&nbsp;&nbsp;
			</td>
		</tr>
	</table>
<DIV id=gridbox style="width: 100%;"></DIV>

<input type="hidden" id="id"/>
<!--<input type="file" id="file"  name="file" />-->
<!--onchange = "loadPathName();"-->
</td></tr></table>
<jsp:include page="../inc/jsdef.jsp" />
<SCRIPT>
	$("#gridbox").height($(window).height()-53);
    var resultdata = '<%=(String)request.getAttribute("resultdata")%>';
	var SDDef = {};
	SDDef.APPTYPE_JC = "<%=SDDef.APPTYPE_JC%>";
	SDDef.SUCCESS = "<%=SDDef.SUCCESS%>";
	SDDef.FAIL = "<%=SDDef.FAIL%>";
</SCRIPT>
  </body>
</html>

