<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<i18n:bundle baseName="<%=SDDef.BUNDLENAME%>" id="bundle" locale="<%=WebConfig.app_locale%>" scope="application" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>主站结算补差</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="cache-control" content="no-cache">
	
	<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="index_theme" />
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
	
	<script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
	<script src="<%=basePath%>js/common/modalDialog.js"></script>
	<script src="<%=basePath%>js/common/def.js"></script>
	<script src="<%=basePath%>js/common/cookie.js"></script>
	
	<script src="<%=basePath%>js/dyjc/tool/dyjs_pl.js"></script>
	<script src="<%=basePath%>js/dyjc/tool/setCons.js"></script>
	
	<script  src="<%=basePath%>js/common/jsonString.js"></script>
	<script  src="<%=basePath%>js/common/def.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script  src="<%=basePath%>js/common/number.js"></script>

	<style type="text/css">
	
	fieldset{
		width:99%;
	}
	fieldset legend{
		font-size:12px; 
	}
	fieldset span{
		font-size:12px;
	}
	
	input {
		width:120px;
	}
	</style>
  </head>
 <body>
 	<table class="gridbox" align="center">
		 <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
	</table>
	<table class="gridbox" align="center" style="margin-top:5px;border 0;">
		<tr>
		<td><button id="upload_bdye">上传SG186表底余额</button></td>
		<td style="text-align:right" >抄表年月:&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="text" id="cb_ym" readonly style="width:120px" onFocus="WdatePicker({dateFmt:'yyyy年MM月',maxDate:'%y-%M',isShowClear:'false'});" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button id="btnRemain" 	class="btn"  disabled="disabled">计算结算金额</button>&nbsp;&nbsp;&nbsp;&nbsp;
				<button id="btnPay"  	class="btn"  disabled="disabled">结算所选记录</button>&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
		</tr>
	</table>  
 </body>
 <script type="text/javascript">
	
	$("#gridbox").height($(window).height()-40);			
	var	mygridCons = new dhtmlXGridObject('gridbox');
	
	var ComntUseropDef = {};
	ComntUseropDef.YFF_DYOPER_JSBC 		 		= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_JSBC%>;
	ComntUseropDef.YFF_DYOPER_GETREMAIN  	= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_GETREMAIN%>;
	ComntUseropDef.YFF_DYOPER_GPARASTATE 		= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_GPARASTATE%>;
	
	var SDDef = {};
	SDDef.SUCCESS 				= '<%=SDDef.SUCCESS%>';
	SDDef.YFF_FEECTRL_TYPE_RTU  = '<%=SDDef.YFF_FEECTRL_TYPE_RTU%>';
	
	SDDef.YFF_CACL_TYPE_MONEY   = '<%=SDDef.YFF_CACL_TYPE_MONEY%>';
	
	
</script>
</html>