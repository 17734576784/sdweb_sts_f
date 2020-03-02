<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
<%@page	import="com.kesd.comntpara.ComntUseropDef"%>

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
			input{
				width:120px;
				font-size: 12px;
			}
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
		
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>		
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/np/localcard/nppayadd.js"></script>
		<script  src="<%=basePath%>js/np/dialog/search.js"></script>
		<script  src="<%=basePath%>js/np/tool/setCons.js"></script>
		<script  src="<%=basePath%>js/np/mis/mis.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
	</head>
	<body>
	<table id="tabinfo1" class="tabinfo" align="center">
    <jsp:include page="user_info.jsp"></jsp:include>
	</table>
	<table class="gridbox" align="center">
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
	</table> 
	<table id="tabinfo2"  class="tabinfo" align="center">
		
		<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable" style="width: 120px;">缴费信息</td>  
	    </tr>
	    <tr>
	    	<td class="tdr">缴费金额(元):</td><td class="tdrn" style="width: 12%;"><input type="text" id="pay_money" /></td>
	        <td class="tdr">追补金额(元):</td><td class="tdrn" style="width: 12%;"><input type="text" id="zb_money" /></td>
			<td class="tdr">结算金额(元):</td><td class="tdrn" style="width: 12%;"><input type="text" id="othjs_money"/></td>			
	    	<td class="tdr">总金额(元):</td><td class="tdrn" style="width: 12%;"><span id="all_money"></span></td>	
	    </tr>
	    
	    <tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>	    
		<tr>
    		<td colspan="8" style="text-align: right;border: 0">
    		<button id="cardinfo"  class="btn">卡内信息</button> &nbsp;&nbsp;&nbsp;&nbsp; 
	    	<button id="rewrite"  class="btn" disabled="disabled">补写缴费</button> &nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="prt"  class="btn"  disabled="disabled">打印</button>&nbsp;&nbsp;	    	 
	    </td></tr>	  
	</table>
	<input type="hidden" id="area_id"/>
	<input type="hidden" id="farmer_id"/>
	<input type="hidden" id="pay_type" value="0"/>
	<input type="hidden" id="lsremain">
	<input type="hidden" id="infoNum"> <!-- 缴费记录条数 -->
	
	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
 		$("#gridbox").height($(window).height()-$("#tabinfo1").height()-$("#tabinfo2").height()-$("#tabinfo3").height()-2);
 		$("#btnSearch").hide();
		var ComntUseropDef = {};
		ComntUseropDef.YFF_NPOPER_PAY 			= 	<%=ComntUseropDef.YFF_NPOPER_PAY%>;
		ComntUseropDef.YFF_NPOPER_ADDRES 		= 	<%=ComntUseropDef.YFF_NPOPER_ADDRES%>;
		ComntUseropDef.YFF_NPOPER_GPARASTATE 	= 	<%=ComntUseropDef.YFF_NPOPER_GPARASTATE%>;
		ComntUseropDef.YFF_NPOPER_REWRITE 		= 	<%=ComntUseropDef.YFF_NPOPER_REWRITE%>;
		//ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER = <%//=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER%>;
		//ComntUseropDef.YFF_DYOPER_MIS_PAY = <%//=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_PAY%>;
		
		var SDDef = {};
		SDDef.STR_OR	 = 	"<%=SDDef.STR_OR%>";
		SDDef.SUCCESS	 = 	"<%=SDDef.SUCCESS%>";
		SDDef.YFF_OPTYPE_ADDRES    	= "<%=SDDef.YFF_OPTYPE_ADDRES%>";
		SDDef.YFF_OPTYPE_PAY   	 	= "<%=SDDef.YFF_OPTYPE_PAY%>";
		
		SDDef.NPCARD_OPTYPE_INITPARA	= <%=SDDef.NPCARD_OPTYPE_INITPARA%>;	//不缴费 仅初始化参数
		SDDef.NPCARD_OPTYPE_OPEN		= <%=SDDef.NPCARD_OPTYPE_OPEN%>;		//卡类型 开户
		SDDef.NPCARD_OPTYPE_BUY			= <%=SDDef.NPCARD_OPTYPE_BUY%>;			//买电充值
		SDDef.NPCARD_OPTYPE_REVER		= <%=SDDef.NPCARD_OPTYPE_REVER%>;		//冲正
		
		var sel_user_info = "<i18n:message key="baseInfo.sel_user_info"/>";
		var khcg = "<i18n:message key="baseInfo.khcg"/>";
		var khsb = "<i18n:message key="baseInfo.khsb"/>";
		var zbje = "<i18n:message key="baseInfo.zbje"/>";
		var jsje = "<i18n:message key="baseInfo.jsje"/>";
		var jfje = "<i18n:message key="baseInfo.jfje"/>";
		var zje = "<i18n:message key="baseInfo.zje"/>";
 		
	</script>
	</body>
	
</html>
