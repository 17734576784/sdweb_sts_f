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
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/dateFormat.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/dyjc/main/dychrate.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>		
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>	
	</head>
	<body>
	<table id="tabinfo" class="tabinfo" align="center">
    	<jsp:include page="user_info.jsp"></jsp:include>
    	<tr><td class="td_lable"><i18n:message key="baseInfo.xflxx"/></td></tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.qhsj"/></td><td ><input id=qhsj style="width:160px;" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日 HH时mm分',isShowClear:'false'});"/></td>		  		        		    				
	    	<td colspan=4></td>
	    </tr>
		<tr>
	    	<td class="tdr"><i18n:message key="baseInfo.flfa"/></td><td ><select id="flfa" style="width:160px;"></select> </td>		  		        		    				
	    	<td class="tdr"><i18n:message key="baseInfo.fams"/></td><td colspan=3 id=fams_n></td>
	    </tr>
	
		<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" style="height:25px;"><i18n:message key="baseInfo.bdxx"/></td>
	    	<td colspan=5 style="padding-left:10px; border: 0px;">
	    	<button id="btnImportBD" class="btn"  disabled="disabled"><i18n:message key="btn.luru"/></button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td></tr>
	    <tr>
	    	<td class="tdr" style="width:140px"><i18n:message key="baseInfo.bdVal" /></td>
	    	<td colspan="5" id="td_bdinf"  ></td>
	    </tr>
	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	<td colspan="6" style="text-align: right;border: 0">
	   		<button id="btnChangeFL"  class="btn"  disabled="disabled"><i18n:message key="idxhref.ggfl"/></button> &nbsp;&nbsp;&nbsp;&nbsp;	    	 
	    </td></tr>
	</table>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<script type="text/javascript">
		$("#mis_jsxx").hide();
		$("#_title3").hide();
 		var ComntUseropDef = {};
		ComntUseropDef.YFF_DYOPER_CHANGERATE = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_CHANGERATE%>;
	</script>
	</body>
</html>
