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
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>		
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/np/localcard/npdestroycus.js"></script>
		<script  src="<%=basePath%>js/np/dialog/search.js"></script>
		<script  src="<%=basePath%>js/np/tool/setCons.js"></script>
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
    	   	<td class="td_lable" style="width: 150px;">缴费信息</td>	  
	    </tr>
	    <tr>
		    <td class="tdr"  width = "10%" >卡内余额(元):</td><td class="tdrn" width="10%" id="now_remain"></td>
	 		<td></td><td></td>
	 		<td></td><td></td>
	    </tr> 
	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	 <tr>
    		<td colspan="6" style="text-align: right;border: 0">
    		<button id="cardinfo"  	 class="btn">卡内信息</button> &nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnDestroy"  class="btn">销户</button> &nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnPrint"  	 class="btn"  disabled>打印</button> &nbsp;&nbsp;&nbsp;&nbsp;
	    </td></tr>
	</table>
	<input type="hidden" id="area_id"/>
	<input type="hidden" id="farmer_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="infoNum"> <!-- 缴费记录条数 -->
	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
 		$("#mis_jsxx").hide();	
		$("#gridbox").height($(window).height()-$("#tabinfo1").height()-$("#tabinfo2").height()-10);		
		
		var ComntUseropDef = {};
		ComntUseropDef.YFF_NPOPER_DESTORY = <%=ComntUseropDef.YFF_NPOPER_DESTORY%>;
		ComntUseropDef.YFF_NPOPER_GPARASTATE = <%=ComntUseropDef.YFF_NPOPER_GPARASTATE%>;
		var SDDef = {};
		SDDef.STR_OR = "<%=SDDef.STR_OR%>";
		SDDef.SUCCESS = "<%=SDDef.SUCCESS%>";
		
		SDDef.NPCARD_OPTYPE_INITPARA	= <%=SDDef.NPCARD_OPTYPE_INITPARA%>;	//不缴费 仅初始化参数
		SDDef.NPCARD_OPTYPE_OPEN		= <%=SDDef.NPCARD_OPTYPE_OPEN%>;		//卡类型 开户
		SDDef.NPCARD_OPTYPE_BUY			= <%=SDDef.NPCARD_OPTYPE_BUY%>;			//买电充值
		SDDef.NPCARD_OPTYPE_REVER		= <%=SDDef.NPCARD_OPTYPE_REVER%>;		//冲正
		
	</script>
	
	</body>
</html>
