<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
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
		<style type="text/css">
			input,select{
				font-size: 12px;
				width:120px;
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
		<script  src="<%=basePath%>js/dyjc/main/dyrecover.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>	
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>
	</head>
	<body>
	<table id="tabinfo" class="tabinfo" id=tabinfo align="center">
    	<jsp:include page="user_info.jsp"></jsp:include>
	</table>
	<table class="gridbox" align="center">
		<tr><td style="padding: 0 0 0 0;"><div id=gridbox style="height:120px;border:0px;"></div></td></tr>
	</table> 
	<table class="tabinfo" align="center" id="tabinfo1">
		<tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>
    	 
    	<tr><td class="td_lable" style="height:25px;"><i18n:message key="baseInfo.bdxx"/></td>
	    	<td colspan=7 style="padding-left:10px; border: 0px;">
	    	<button id="btnImportBD" class="btn"><i18n:message key="btn.luru"/></button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td></tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.bdVal"/></td>
	    	<td colspan="7" id="td_bdinf" ></td>
	    </tr>
	    
	   <tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable" style="width: 140px"><i18n:message key="baseInfo.jfxx"/></td>	  
	    </tr>
	    <tr>
	    	<td class="tdr" style="width: 140px">缴费金额(元):</td><td class="tdrn" style="width: 12%;"><input type="text" id=jfje /></td>
	        <td class="tdr">追补金额(元):</td><td class="tdrn" style="width: 12%;"><input type="text" id=zbje /></td>
			<td class="tdr">结算金额(元):</td><td class="tdrn" style="width: 12%;"><input type="text" id=jsje /></td>
	    	<td class="tdr">总金额(元):</td><td class="tdrn" style="width: 12%;"><span id=zongje></span></td>			
	    </tr>
	    <tr>
	    	<td class="tdr">旧表基础电量:</td><td  class="tdrn"><input id="jt_total_zbdl" name=jcdl /></td>
	    	<td></td><td></td><td></td><td></td><td></td><td></td>
	    </tr>
	   <tr><td colspan="8" style="height:3px; border: 0px;"></td></tr>
	   <tr>
			<td class="tdr" colspan=8 style="border: 0px;">
		    	<button id="restart" class="btn"  disabled="disabled"><i18n:message key="idxhref.huifu"/></button>&nbsp;&nbsp;&nbsp;&nbsp;
		    	<button id="btnPrint"   class="btn"   disabled = "disabled">打印</button>
	    	</td>
	    </tr>
	</table>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="feeproj_id"/>
	<input type="hidden" id="yffalarm_id"/>
	<jsp:include page="../../inc/jsdef.jsp"></jsp:include>
	<script type="text/javascript">
 		$("#_sfbdxx").hide();
 		$("#mis_jsxx").hide();
		var ComntUseropDef = {};
		ComntUseropDef.YFF_DYOPER_RESTART = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_RESTART%>;
		var SDDef = {};
		SDDef.YFF_FEETYPE_JTFL 	= <%=SDDef.YFF_FEETYPE_JTFL%>;
		SDDef.YFF_FEETYPE_MIXJT = <%=SDDef.YFF_FEETYPE_MIXJT%>;
	
		$("#gridbox").height($(window).height() - $("#tabinfo").height()-$("#tabinfo1").height() - 20 )
	</script>
	
	</body>
