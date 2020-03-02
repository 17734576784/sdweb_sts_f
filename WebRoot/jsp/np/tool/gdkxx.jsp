<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
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
				width: 99%;
			}
		</style>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/zh/cont.css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/gdkxx.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
	</head>
	<body>
	<table id="tabinfo1" class="tabinfo" align="center">
		<jsp:include page="../localcard/user_info.jsp"></jsp:include>
	</table>	    
	   <table id="tabinfo2" class="tabinfo" align="center" >
		<tr><td class="td_lable">卡内用户信息</td></tr>
		   <tr>
		    	<td class="tdr" style="width: 10%">客户编号:</td><td id="userno_01" style="width: 15%">&nbsp;</td>
		    	<td class="tdr" style="width: 10%">电表类型:</td><td id="metertype_01" style="width: 10%">&nbsp;</td>
		    	<td class="tdr" style="width: 10%">ESAM表号:</td><td id="esamno_01" style="width: 10%">&nbsp;</td>
		    </tr>
		   	<tr>
		    	<td class="tdr">费率类型:</td><td id="feetype_01">&nbsp;</td>
		    	<td class="tdr">上次缴费金额(元):</td><td colspan="3" id="scjfje_01">&nbsp;</td>
		   	</tr>
		   	<tr>
		    	<td class="tdr">电卡类型:</td><td id="card_type_01">&nbsp;</td>
		    	<td class="tdr">报警金额1(元):</td><td id="alarm1_01">&nbsp;</td>
		    	<td class="tdr">报警金额2(元):</td><td id="alarm2_01">&nbsp;</td>
		   	</tr>
		   	<tr>
		   		<td class="tdr">购电次数:</td><td id="buynum_01">&nbsp;</td>
		   		<td class="tdr">PT:</td><td id="pt_01">&nbsp;</td>
		   		<td class="tdr">CT: </td><td id="ct_01">&nbsp;</td>
		    </tr>
		    <tr>
		    	<td class="tdr">第一套费率:</td><td id="fee1_01">&nbsp;</td>
		    	<td class="tdr">第二套费率:</td><td colspan="3" id="fee2_01">&nbsp;</td>
		   	</tr>
		</table>
		<table id="tabinfo3" class="tabinfo" align="center">
		    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
		    <tr><td class="td_lable">卡内返写信息</td></tr>
		    <tr>
		    	<td class="tdr" style="width: 10%">返写标志:</td><td id="write_back_flag_01" style="width: 15%">&nbsp;</td>
		    	<td class="tdr" style="width: 10%">客户编号:</td><td id="userno_01_back" style="width: 10%">&nbsp;</td>
		    	<td class="tdr" style="width: 10%">ESAM表号:</td><td id="esamno_01_back" style="width: 10%">&nbsp;</td>
		    </tr>
		   	<tr>
		    	<td class="tdr">费率类型:</td><td id="feetype_01_back">&nbsp;</td>
		    	<td class="tdr">剩余金额(元):</td><td id="syje_01_back">&nbsp;</td>
		    	<td class="tdr">透支金额:</td><td id="tzje_01_back">&nbsp;</td>
		   	</tr>
		   	<tr>
		   		<td class="tdr">购电次数:</td><td id="buynum_01_back">&nbsp;</td>
		   		<td class="tdr">PT:</td><td id="pt_01_back">&nbsp;</td>
		   		<td class="tdr">CT: </td><td id="ct_01_back">&nbsp;</td>
		    </tr>
		    <tr>
		   		<td class="tdr">密钥版本:</td><td id="mybb_01_back">&nbsp;</td>
		   		<td class="tdr">密钥状态:</td><td id="myzt_01_back">&nbsp;</td>
		   		<td class="tdr">非法插卡次数: </td><td id="ffckcs_01_back">&nbsp;</td>
		    </tr>
		    <tr>
		    	<td class="tdr">返写日期:</td><td id="fxdata_01_back">&nbsp;</td>
		    	<td class="tdr">返写时间:</td><td colspan="3" id="fxtime_01_back">&nbsp;</td>
		   	</tr>
		</table>
	<table id="tabinfo4" class="tabinfo" align="center">
		<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>	    
		<tr>
    		<td colspan="6" style="text-align: right;border: 0">
    		<button id="cardinfo" class="btn" >卡内信息</button>
	    </td></tr>	   
	</table>
	
	<script type="text/javascript">
	
		$("#_title3").hide();
		$("#btnSearch").hide();
		$("#mis_jsxx").hide();
		var ComntUseropDef = {};
		ComntUseropDef.YFF_DYOPER_PAY = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_PAY%>;
		ComntUseropDef.YFF_DYOPER_GPARASTATE = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_GPARASTATE%>;
	</script>
	</body>
</html>
