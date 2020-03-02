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
					width:160px;
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
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/dateFormat.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/dyjc/main/dychmeter.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>	
		<script  src="<%=basePath%>js/dyjc/dialog/sg186.js"></script>		
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>
	</head>
	<body>
	<div id="div_hb" style="overflow: auto;">
	<table id="tabinfo1" class="tabinfo" align="center">
    	<jsp:include page="user_info.jsp"></jsp:include>
	</table>
	<table class="gridbox" align="center">
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px; width:100%"></div></td></tr>
	</table> 
	
	<table  id="tabinfo2" class="tabinfo" align="center">
		<tr><td colspan="6" style="height:8px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>
		<tr>
    	   	<td class="td_lable" style="width: 140px">表底信息</td>
    	   	<td colspan=5 style="padding-left:10px; border: 0px;">
	    	<button id="rtnBDOld" class="btn"  disabled="disabled">旧表录入</button>&nbsp;&nbsp;
	    	<button id="rtnBDNew" class="btn"  disabled="disabled">新表录入</button>
	    	</td>	  
	    </tr>
	    <tr>
	    	<td class="tdr" width = 140px>旧表信息:</td>
	    	<td id="bd_old" colspan="7"></td>
	    </tr>
	    <tr>
	    	<td class="tdr">新表信息:</td>
	    	<td id="bd_new" colspan="7"></td>
	    </tr>
		</table>
	    
	<table id=tabinfo3 class="tabinfo" align="center">
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" style="height:25px;">换表信息</td></tr>
	    <tr>
	     	<td class="tdr" style="width: 140px"><i18n:message key="baseInfo.ghsj"/></td><td style="width: 17%"><input id=ghsj onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日 HH时mm分',isShowClear:'false'});"/></td>
		    <td class="tdr" style="width: 12%"><i18n:message key="baseInfo.ghlx"/></td><td style="width: 20%"><select id="ghlx"><option value=<%=SDDef.YFF_CHGMETER_MT%>><i18n:message key="baseInfo.ghdb"/></option><option value=<%=SDDef.YFF_CHGMETER_CT%>><i18n:message key="baseInfo.ghct"/></option></select></td>
	    	<td class="tdr" style="width: 12%"><i18n:message key="baseInfo.ghdb"/></td><td><select id="ghdb"></select></td>
	    </tr>
	   	<tr id="tr_ct">
	 		<td class="tdr"><i18n:message key="baseInfo.newCTfz"/></td><td><input id=ctfz /></td>
	    	<td class="tdr"><i18n:message key="baseInfo.newCTfm"/></td><td ><input id=ctfm /></td>
	  		<td class="tdr"><i18n:message key="baseInfo.newCTbb"/></td><td id=ctbb align="left"></td>
	    </tr>
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>  
	  	<tr>
	    	<td class="tdr" colspan=6 style="border: 0px;">
	  			<button id="btnChange"  class="btn" disabled="disabled"><i18n:message key="btn.genghuan"/></button>
<%--	  			<button id="btnPrt"  class="btn" disabled="disabled"><i18n:message key="btn.dayin"/></button>--%>
  			</td>
	    </tr>
	</table>
	</div>
	
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	<jsp:include page="../../inc/jsdef.jsp"></jsp:include>
	<script type="text/javascript">
		$("#mis_jsxx").hide();
 		$("#gridbox").height($(window).height()-$("#tabinfo1").height()-$("#tabinfo2").height()-$("#tabinfo3").height());
 		if($("#gridbox").height() < 100)$("#gridbox").height(100)
 		$("#div_hb").height($(window).height());
	 	var ComntUseropDef = {};
		ComntUseropDef.YFF_DYOPER_CHANGEMETER = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_CHANGEMETER%>;
	</script>
	</body>
</html>
