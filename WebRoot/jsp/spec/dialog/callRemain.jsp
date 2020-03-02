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
		<title>终端内信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<style type="text/css">
			body{
				overflow: auto;
			}
			input,select{
				font-size: 12px;
			}
		</style>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/zh/cont.css" id="cont" />
		<script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script src="<%=basePath%>js/common/number.js"></script>
		<script src="<%=basePath%>js/common/jsonString.js"></script>
		<script src="<%=basePath%>js/spec/dialog/callRemain.js"></script>
		<script src="<%=basePath%>js/common/loading.js"></script>
		<script src="<%=basePath%>js/common/def.js"></script>
		<script src="<%=basePath%>js/dialog/opdetail.js"></script>
		<script src="<%=basePath%>js/validate.js"></script>
	</head>
	<body>
	<table class="tabinfo" align="center" style="width:95%; display: none;">
    	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable">基本信息</td>
	    	<td colspan=5 style="padding-left:10px; border: 0px; text-align: right;">&nbsp;&nbsp;&nbsp;&nbsp;
	    		<button id="detail_info" class="btn" >详细信息</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<button id="readRtuInfo" class="btn" >读取</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<button id="btnCancel" class="btn">取消任务</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<button id="cls_window" class="btn" onclick="window.close()">关闭</button>
	    	</td>
	    </tr>
   		<tr>
	    	<td class="tdr" width="15%" style="height: 22px;">客户编号:</td><td id="userno"  width="15%">&nbsp;</td>
	    	<td class="tdr" width="15%">客户名称:</td><td id="username" width="20%">&nbsp;</td>
	    	<td class="tdr" width="15%">算费类型:</td><td id="cacl_type_desc" width="20%">&nbsp;</td>
	    </tr>
		<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable">上次缴费信息</td></tr>
	    <tr>
	    	<td class="tdr"	width="15%" id="nowval_text" style="height: 22px;">当前表底:</td><td width="15%"><span id="nowval"></span></td>
	    	<td class="tdr"	width="15%" id="totval_text">断电止码:</td><td width="20%"><span id="totval"></span></td>
	    	<td class="tdr"	width="15%" id="alarm_val1_text">报警止码:</td><td width="20%"><span id="alarm_val1"></span></td>
	    </tr>
	     <tr>
	     	<td class="tdr">缴费次数:</td><td id="buy_times"></td>
	     	<td class="tdr" style="height: 22px;"><label for="start_yff_flag" id="yff_start1">启用预付费:</label></td>
	     	<td class="tdr" style="text-align: left;"><input type="checkbox" id="start_yff_flag" style="width: 16px;" /></td>
	 		<td></td><td></td>
	    </tr>
	    <tr id="remain_info">
	    	<td class="tdr" style="height: 22px;">剩余电量:</td><td><span id="remain_dl"></span></td>
	    	<td class="tdr">剩余金额:</td><td><span id="remain_je"></span></td>
	    	<td></td><td></td>
	    </tr>
	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
	</table>
	
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="zjg_id"/>
	<input type="hidden" id="prot_type"/>
	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
	
	var ComntUseropDef = {};
	ComntUseropDef.YFF_GYCOMM_PAY 			= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYCOMM_PAY%>;
	ComntUseropDef.YFF_GYOPER_GPARASTATE 	= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_GPARASTATE%>;
	ComntUseropDef.YFF_GYCOMM_CALLPARA 		= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYCOMM_CALLPARA%>;
	ComntUseropDef.YFF_READDATA		 		= <%=com.kesd.comntpara.ComntUseropDef.YFF_READDATA%>;
	
	var ComntProtMsg = {};
	ComntProtMsg.YFF_GY_CALL_PARAREMAIN 	= <%=com.kesd.comnt.ComntProtMsg.YFF_GY_CALL_PARAREMAIN%>;
	ComntProtMsg.YFF_CALL_GY_REMAIN 		= <%=com.kesd.comnt.ComntProtMsg.YFF_CALL_GY_REMAIN%>;
	
	var ComntDef = {};
	ComntDef.YD_PROTOCAL_FKGD05				= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_FKGD05%>;
	ComntDef.YD_PROTOCAL_GD376				= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_GD376%>;
	ComntDef.YD_PROTOCAL_GD376_2013			= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_GD376_2013%>;
	ComntDef.YD_PROTOCAL_KEDYH				= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_KEDYH%>;

	var SDDef		   = {};
	SDDef.STR_AND 						= "<%=SDDef.STR_AND%>";
	SDDef.STR_OR 						= "<%=SDDef.STR_OR%>";
	SDDef.SUCCESS	 					= "<%=SDDef.SUCCESS%>";
	
	var yff_grid_title ='<i18n:message key="baseInfo.yffspec.grid_title"/>';
	var sel_user_info = "<i18n:message key="baseInfo.sel_user_info"/>";
	var khcg = "<i18n:message key="baseInfo.khcg"/>";
	var khsb = "<i18n:message key="baseInfo.khsb"/>";
	var yzje = "<i18n:message key="baseInfo.yzje"/>";
	var yzje = "<i18n:message key="baseInfo.yzje"/>";
	var jfje = "<i18n:message key="baseInfo.jfje"/>";
	var zje = "<i18n:message key="baseInfo.zje"/>";
	</script>
	</body>
</html>
