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
			body{
			  margin:0 0 0 0;
			  overflow:hidden;
			}	
			input,select{
				font-size: 12px;				
			}	
			.tabinfo input{
				width: 99%;
			}	
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>		
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>		
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/dyjc/main/dypay.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>
		<script  src="<%=basePath%>js/dyjc/mis/mis.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>
	</head>
	<body>
	<div id="dy_pay" style="overflow: auto">
	<table id="tabinfo1" class="tabinfo" align="center">
    	<jsp:include page="user_info.jsp"></jsp:include>
	</table>
	<table class="gridbox" align="center">
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
	</table> 
		<table id="tabinfo3" class="tabinfo" align="center">
		<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable" >计费信息</td>	  
	    </tr>
	    <tr>
		    <td class="tdr" style="width: 140px">当前余额(元):</td>
		    <td class="buy_times" style="width: 12%" id="now_remain"></td>
		    <td class="tdr" style="width: 10%">购电次数:</td><td colspan=5 class="tdrn"><span id="buy_times"></span></td>
	    </tr>
	</table>
	<table  id="tabinfo2" class="tabinfo" align="center">
		<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable" style="width: 140px"><i18n:message key="baseInfo.jfxx"/></td>	  
	    </tr>
	    <tr>
			<td class="tdr"><i18n:message key="baseInfo.jfje"/><i18n:message key="baseInfo.yuan"/></td><td class="tdrn" style="width: 12%;"><input type="text" id=jfje /></td>			
	        <td class="tdr" style="width: 140px"><i18n:message key="baseInfo.zbje"/><i18n:message key="baseInfo.yuan"/></td><td class="tdrn" style="width: 12%;"><input type="text" id=zbje /></td>
			<td class="tdr"><i18n:message key="baseInfo.jsje"/><i18n:message key="baseInfo.yuan"/></td><td class="tdrn" style="width: 12%;"><input type="text" id=jsje /></td>
	    	<td class="tdr"><i18n:message key="baseInfo.zongje"/><i18n:message key="baseInfo.yuan"/></td><td class="tdrn" style="width: 12%;"><span id=zongje></span></td>	
	    </tr>
	    <tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>
	   <!-- 
		<tr><td colspan=8 style="text-align: right;border: 0">
			<button id="chgMbphone" class="btn"><i18n:message key="btn.ghsj"/></button> &nbsp;&nbsp;&nbsp;&nbsp; 
		 	<button id="btnPay" class="btn"><i18n:message key="btn.jiaofei"/></button> &nbsp;&nbsp;&nbsp;&nbsp; 
		 	<button id="btnPrt" class="btn" disabled><i18n:message key="btn.dayin"/></button> 
		</td></tr>	
		 -->    
	</table>
	<table id="tabbutton" class="tabinfo" align="center">
		<tr>
			<td style="text-align: right;border: 0; width:67%"></td>
			<td style="text-align: right;border: 0; width:10%">
				<button id="chgMbphone" class="btn"><i18n:message key="btn.ghsj"/></button> &nbsp;&nbsp;&nbsp;&nbsp; 
			</td>
			<td style="text-align: right;border: 0; width:3%"></td>
    		<td style="text-align: right;border: 0; width:20%">
		 	<button id="btnPay" class="btn"><i18n:message key="btn.jiaofei"/></button> &nbsp;&nbsp;&nbsp;&nbsp; 
		 	<button id="btnPrt" class="btn" disabled><i18n:message key="btn.dayin"/></button>  	 
	    	</td>
	    </tr>	 
	</table>
	</div>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	<jsp:include page="../../inc/jsdef.jsp"></jsp:include>
	</body>
	<script type="text/javascript">
	$("#gridbox").height($(window).height()-$("#tabinfo1").height()-$("#tabinfo2").height()-$("#tabinfo3").height()-40);
	$("#dy_pay").height($(window).height());
	var ComntUseropDef = {};
	ComntUseropDef.YFF_DYOPER_PAY = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_PAY%>;
	ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER%>;
	ComntUseropDef.YFF_DYOPER_MIS_PAY = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_PAY%>;
	ComntUseropDef.YFF_DYOPER_RESETDOC = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_RESETDOC%>;
	var SDDef = {};
	SDDef.YFF_OPTYPE_PAY = <%=SDDef.YFF_OPTYPE_PAY%>;
	</script>
	
</html>
