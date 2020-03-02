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
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
		<style type="text/css">
			input,select{
				font-size: 12px;	
			}
			input {
				width :130px
			}
		</style>
		
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>		
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/np/tool/tsxz.js"></script>
		<script  src="<%=basePath%>js/np/tool/comm_np.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/common/DateFun.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
		<script  src="<%=basePath%>js/common/dateFormat.js"></script>
		
	</head>
	<body>
	<table id="tabinfo" class="tabinfo" align="center">
		<tr>
	    	<td colspan=6 style="padding-left:10px; border: 0px;">
	    	<i18n:message key="baseInfo.yyd"/>:<select id="org" style="width:120px;"></select>&nbsp;&nbsp;片区:
	    	<select id="area" style="width:120px;"><option value=-1>所有</option></select>&nbsp;&nbsp;
	    	<i18n:message key="baseInfo.jstj"/>:<select id="searchType" style="width:80px;">
	    	<option value="1"><i18n:message key="baseInfo.yhhh"/></option>
	    	<option value="2" selected>客户名称</option>	    	
	    	</select>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<i18n:message key="baseInfo.jsnr"/>:<input id="searchContent" />&nbsp;&nbsp;
	    	
	        <i18n:message key="baseInfo.gglx"/>&nbsp;<select id="updType" style="width:100px;">
	    	<option value="<%=SDDef.YFF_DY_CHGBUYTIME%>"><i18n:message key="baseInfo.gxgdcs"/></option>
	    	</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnSearch" class="btn"><i18n:message key="btn.jiansuo"/></button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnUpdate" class="btn"><i18n:message key="btn.gengxin"/></button>
	    	</td>
	    </tr>
   	</table>
	<DIV id=gridbox style="width: 100%;"></DIV>
	<table id="tabinfo1" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable"><i18n:message key="baseInfo.yhxx"/></td></tr>
	    <tr>
	        <td class="tdr" style="width: 13%;">客户编号:</td><td style="width: 18%;" id="khbh"></td>
	        <td class="tdr" style="width: 15%;"><i18n:message key="baseInfo.yhmc"/></td><td style="width: 18%;" id="khmc" ></td>
	        <td class="tdr" style="width: 16%;">客户状态:</td><td id="khzt"></td>
	    </tr>
	    <tr>
	    	<td class="tdr">购电次数:</td><td><input type="text" id="gdcs" /></td>
	    	<td class="tdr">累计购电次数:</td><td id="ljgdcs"></td>
<!--	    	<td class="tdr">报警方案:</td><td id="bjfa"></td>-->
			<td class="tdr"></td><td id="bjfa"></td>
	    </tr>
	    <tr>
	    	<td class="tdr">操作类型:</td><td id="czlx"></td>
	    	<td class="tdr">操作时间:</td><td id="czsj"></td>
	    	<td class="tdr">缴费金额:</td><td id="jfje"></td>
	    </tr>
	    <tr>
	    	<td class="tdr">结算金额:</td><td id="jsje"></td>
	    	<td class="tdr">追补金额:</td><td id="zbje"></td>
	    	<td class="tdr">总金额:</td><td id="zje"></td>
	    </tr>
	    <tr>
	    	<td class="tdr">上次剩余金额:</td><td id="scsyje"></td>
	    	<td class="tdr">当前剩余金额:</td><td id="dqsyje"></td>
	    	<td class="tdr">累计购电金额:</td><td id="ljgdje"></td>
	    </tr>
	    <tr>
	    	<td class="tdr">开户日期:</td><td id="khrq"></td>
	    	<td class="tdr">销户日期:</td><td id="xhrq"></td>
	    	<td></td><td></td>
	    </tr>
	</table>
	
	<script>
	$("#gridbox").height($(window).height()-$("#tabinfo").height()-$("#tabinfo1").height()-10);
	var	mygrid = new dhtmlXGridObject('gridbox');	
	var ComntUseropDef = {};
	ComntUseropDef.YFF_NPOPER_UDPATESTATE= <%=com.kesd.comntpara.ComntUseropDef.YFF_NPOPER_UDPATESTATE%>;
	ComntUseropDef.YFF_NPOPER_GPARASTATE = <%=com.kesd.comntpara.ComntUseropDef.YFF_NPOPER_GPARASTATE%>;
	var SDDef = {};
	
	SDDef.YFF_DY_CHGBUYTIME  	    = <%=SDDef.YFF_DY_CHGBUYTIME%>;  	//更新购电次数
	SDDef.SUCCESS                   = '<%=SDDef.SUCCESS%>';
	
	
	</script>
	</body>
</html>
