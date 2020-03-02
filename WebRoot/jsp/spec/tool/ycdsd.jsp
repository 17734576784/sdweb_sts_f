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
				<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
		<script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
		<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
	    <script src="<%=basePath%>js/spec/dialog/search.js"></script>
	    <script src="<%=basePath%>js/common/modalDialog.js"></script>
		<script src="<%=basePath%>js/common/number.js"></script>
		<script src="<%=basePath%>js/common/jsonString.js"></script>
		<script src="<%=basePath%>js/spec/tool/ycdsd.js"></script>
		<script src="<%=basePath%>js/common/loading.js"></script>
		<script src="<%=basePath%>js/common/def.js"></script>
		<script src="<%=basePath%>js/spec/tool/setCons.js"></script>
		<script src="<%=basePath%>js/spec/mis/mis.js"></script>
		<script src="<%=basePath%>js/validate.js"></script>
		<script src="<%=basePath%>js/dialog/opdetail.js"></script>
	</head>
	<body>
	<!-- 
	-->
	  <table id="tabinfo1" class="tabinfo" align="center">
      	<tr><td colspan="8" style="height:10px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;">基本信息</td>
	    	<td colspan=6 style="padding-left:10px; border: 0px;">
	    	<button id="btnSearch" class="btn" >检索</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>
	    	<td style="border:0px; text-align: right;" ><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    </tr>
   		<tr>
	    	<td class="tdr">客户编号:</td><td id="userno" width="10%"></td>
	    	<td class="tdr" width="10%">客户名称:</td><td id="username" colspan=3 width="30%">&nbsp;</td>
	     	<td class="tdr" width="10%">客户状态:</td><td id="cus_state" width="10%">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr" width="10%">联系电话:</td><td id="tel">&nbsp;</td>
	   		<td class="tdr">客户地址:</td><td colspan=3 id="useraddr">&nbsp;</td>
	   		<td class="tdr">保电状态:</td><td id="prot_state">&nbsp;</td>
	    </tr>
   		<tr>
   			<td class="tdr">费控单元:</td><td id="fee_unit">&nbsp;</td>
	    	<td class="tdr" width="10%">终端型号:</td><td id="rtu_model" width="10%">&nbsp;</td>
	    	<td class="tdr" width="10%">生产厂家:</td><td id="factory" width="10%">&nbsp;</td>
	     	<td class="tdr" width="10%">倍率:</td><td id="blv">&nbsp;</td>
	    </tr>
	   <tr>
	    	<td class="tdr">计费方式:</td><td id="cacl_type_desc">&nbsp;</td>
	    	<td class="tdr">费控方式:</td><td id="feectrl_type">&nbsp;</td>
	    	<td class="tdr">缴费方式:</td><td id="pay_type_desc">&nbsp;</td>
	    	<td></td><td></td>
	    </tr>
	   	<tr>
	    	<td class="tdr">费率方案:</td><td id="feeproj_desc">&nbsp;</td>
	    	<td class="tdr">方案描述:</td><td colspan=3 id="feeproj_detail">&nbsp;</td>
	    	<td></td><td></td>
	   	</tr>
	   	<tr>
	   		<td class="tdr">报警方案:</td><td id="yffalarm_desc">&nbsp;</td>
	   		<td class="tdr">方案描述:</td><td colspan=3 id="yffalarm_detail">&nbsp;</td>
	   		<td></td>
	   		<td></td>
	    </tr>
	    <tr>
	    <td colspan="8" style="height:8px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>	    
	    <tr>
	    <td class="td_lable" style="border-bottom: 0">购电记录</td>
	    </tr>
	    
	   </table>
	    
	   <table class="gridbox" align="center">
			<tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
	   </table> 
	
	   <table id="tabinfo2"  align="center" class="tabinfo">
		<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable">上次缴费信息</td></tr>
	    <tr>
	    	<td class="tdr"	width="10%" id="nowval_text" style="height: 22px;">当前表底:</td><td width="10%"><span id="nowval"></span></td>
	    	<td class="tdr"	width="10%" id="totval_text">断电止码:</td><td width="30%"><span id="totval"></span></td>
	    	<td class="tdr"	width="10%" id="alarm_val1_text">报警止码:</td><td width="10%"><span id="alarm_val1"></span></td>
	    </tr>
	     <tr>
	     	<td class="tdr">缴费次数:</td><td id="buy_times"></td>
	     	<td class="tdr" style="height: 22px;" ><label for="start_yff_flag" id="yff_start1">启用预付费:</label></td>
	     	<td class="tdr" style="text-align: left;"><input type="checkbox" id="start_yff_flag"  disabled/></td>
	 		<td></td><td></td>
	    </tr>
	    <tr id="remain_info">
	    	<td class="tdr" style="height: 22px;">剩余电量:</td><td><span id="remain_dl"></span></td>
	    	<td class="tdr">剩余金额:</td><td><span id="remain_je"></span></td>
	    	<td></td><td></td>
	    </tr>
	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	<td colspan="6" style="text-align: right;border: 0">
   <!-- <button id="detail_info" class="btn" >详细信息</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
	    	<button id="readRtuInfo" class="btn" >读取</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	<!--
	    	<button id="cls_window" class="btn" onclick="window.close()">关闭</button>
	         -->
	    </td></tr>
	</table>
	
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="zjg_id"/>
	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
	var sel_type = "<%=request.getParameter("type")%>";
	$("#gridbox").height($(window).height()-$("#tabinfo1").height()-$("#tabinfo2").height()-5);
	var ComntUseropDef = {};
	ComntUseropDef.YFF_GYCOMM_PAY 	     = <%=com.kesd.comntpara.ComntUseropDef.YFF_GYCOMM_PAY%>;
	ComntUseropDef.YFF_GYOPER_GPARASTATE = <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_GPARASTATE%>;
	ComntUseropDef.YFF_GYCOMM_CALLPARA   = <%=com.kesd.comntpara.ComntUseropDef.YFF_GYCOMM_CALLPARA%>;
	ComntUseropDef.YFF_READDATA		 		= <%=com.kesd.comntpara.ComntUseropDef.YFF_READDATA%>;
	
	var ComntDef = {};
	ComntDef.YD_PROTOCAL_FKGD05				= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_FKGD05%>;
	ComntDef.YD_PROTOCAL_GD376				= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_GD376%>;
	ComntDef.YD_PROTOCAL_GD376_2013			= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_GD376_2013%>;
	ComntDef.YD_PROTOCAL_KEDYH				= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_KEDYH%>;
	
	var ComntProtMsg = {};
	ComntProtMsg.YFF_GY_CALL_PARAREMAIN = <%=com.kesd.comnt.ComntProtMsg.YFF_GY_CALL_PARAREMAIN%>;
	ComntProtMsg.YFF_CALL_GY_REMAIN 		= <%=com.kesd.comnt.ComntProtMsg.YFF_CALL_GY_REMAIN%>;
	
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
	var zje  = "<i18n:message key="baseInfo.zje"/>";
	</script>
	</body>
</html>
