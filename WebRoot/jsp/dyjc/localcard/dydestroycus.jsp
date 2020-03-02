<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
<%@page import="com.kesd.comntpara.ComntUseropDef"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<i18n:bundle baseName="<%=SDDef.BUNDLENAME%>" id="bundle" locale="<%=WebConfig.app_locale%>" scope="application" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<style type="text/css">
			input,select{
				font-size: 12px;				
			}	
			.tabinfo input{
				width: 140px;
			}
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />		
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
		<script>
			var basePath = '<%=basePath%>';
		</script>		
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>		
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/dyjc/localcard/dydestroycus.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>			
	</head>
	<body>
	<table id="tabinfo1" class="tabinfo" align="center">
    	<jsp:include page="user_info.jsp"></jsp:include>
	</table>
	
	<table class="gridbox" align="center">
		<tr><td style="padding: 0 0 0 0;"><div id=gridbox style="height:160px;border:0px;"></div></td></tr>
	</table>
	
	<table id="tabinfo2" class="tabinfo" align="center">
		<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable" style="width: 150px;">Informations de recharge</td>	  
    	   	<td colspan="7" id="canceltoken" style="text-align: left;display: none"></td>
    	   	
	    </tr>
	    <tr>
		    <td class="tdr"  width = "10%" >Solde actuel:</td><td class="tdrn" width="10%"><input type="text" id="dqye" /></td>
		    <td class="tdr"  width = "10%" ></td><td class="tdr"  width = "10%"></td>
		    <td class="tdr"  width = "10%"></td>
			<td></td>
	    </tr>
	    <tr>
		    <td class="tdr" style="width: 10%">Balance:</td>
		    <td class="tdrn"><span id="jy_money"></span></td>
		    <td class="tdr" style="width: 150px"></td>
		    <td class="buy_times" style="width: 17%" colspan=5 ></td>
	    </tr>	     
	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	 <tr>
    		<td colspan="6" style="text-align: right;border: 0">
  	    	<button id="btnDestroy"  class="btn">Annulation de compte</button> &nbsp;&nbsp;
  	    	<button id="prt"  class="btn"  disabled="disabled">Impression</button> &nbsp;&nbsp;&nbsp;&nbsp;  	 
  	    	
	    </td></tr>
	</table>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="seqNo"/>
	<input type="hidden" id="keyNo"/>
	<input type="hidden" id="buy_times"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="res_id"/>
	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
		var basePath = '<%=basePath%>';
 		$("#mis_jsxx").hide();	
		$("#gridbox").height($(window).height()-$("#tabinfo1").height()-$("#tabinfo2").height()-10);		
		
		var ComntUseropDef = {};
		ComntUseropDef.YFF_DYOPER_DESTORY = <%=ComntUseropDef.YFF_DYOPER_DESTORY%>;
		ComntUseropDef.YFF_DYOPER_GETREMAIN = <%=ComntUseropDef.YFF_DYOPER_GETREMAIN%>;
		ComntUseropDef.YFF_DYOPER_GPARASTATE = <%=ComntUseropDef.YFF_DYOPER_GPARASTATE%>;
		var SDDef = {};
		SDDef.STR_OR 				= "<%=SDDef.STR_OR%>";
		SDDef.SUCCESS 				= "<%=SDDef.SUCCESS%>";
		SDDef.YFF_CUSSTATE_DESTORY	= "<%=SDDef.YFF_CUSSTATE_DESTORY%>"
		SDDef.YFF_OPTYPE_DESTORY	= "<%=SDDef.YFF_OPTYPE_DESTORY%>";
		
	</script>
	
	</body>
</html>
