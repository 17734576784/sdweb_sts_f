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
		input{
			width: 120px;
		}
		input,select{
			font-size: 12px;				
		}
		body{
		    overflow: auto;
		}	
		</style>
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>		
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/dyjc/localcard/dyrever.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/sg186.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>
		<script  src="<%=basePath%>js/dyjc/mis/mis.js"></script>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
	</head>
	<body>
	<table id="tabinfo1" class="tabinfo" align="center">
    	<jsp:include page="user_info.jsp"></jsp:include>
	</table>
	<table class="gridbox" align="center">
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
	</table> 
	<table id="tabinfo2" class="tabinfo" align="center">
		<tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable">缴费信息</td>	  
	    </tr>
	    <tr>
	    	<td class="tdr">购电次数:</td><td class="tdrn" colspan=7><span id="buy_times" ></span></td>
	    </tr>
	    <tr>
	    	<td class="tdr" width="150">上次缴费金额(元):</td><td class="tdrn"><input id="last_je" readonly style="background: #ccc;" /></td>
	    	<td class="tdr" width="120">上次追补金额(元):</td><td class="tdrn"><input id="last_zbje" readonly style="background: #ccc;" /></td>
	    	<td class="tdr" width="120">上次结算金额(元):</td><td class="tdrn"><input id="last_jsje" readonly style="background: #ccc;" /></td>
	    	<td class="tdr" width="100">上次总金额(元):</td><td class="tdrn"><input id="last_tt" readonly style="background: #ccc;"/></td>		  		        		    				
	    </tr>
	    <tr>
			<td class="tdr">实际缴费金额(元):</td><td class="tdrn" ><input id="jfje" name="jfje" value=""/></td>
			<td class="tdr">实际追补金额(元):</td><td class="tdrn" ><input id="zbje" name="zbje" value=""/></td>
			<td class="tdr">实际结算金额(元):</td><td class="tdrn" ><input id="jsje" name="jsje" value=""/></td>
			<td class="tdr">实际总金额(元):     </td><td class="tdrn" style="width: 100px;"><span id="zje" >&nbsp;</span></td>
	    </tr>
		<tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>
    	<tr>    	
			<td class="tdr" colspan=8 style="border: 0px;">		
    		<button id="cardinfo"  class="btn" >卡内信息</button> &nbsp;&nbsp;&nbsp;&nbsp; 
			<button id="metinfo"   class="btn" >表内信息</button> &nbsp;&nbsp;&nbsp;&nbsp;  
			<button id="btnRever" class="btn" disabled="disabled">冲正</button> &nbsp;&nbsp;&nbsp;&nbsp; 
			<button id="btnPrt" class="btn" disabled>打印</button>
			</td>
		</tr>
	</table>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="feeproj_id"/>
	<input type="hidden" id="yffalarm_id"/>
	
	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
 		$("#gridbox").height($(window).height()-$("#tabinfo1").height()-$("#tabinfo2").height()-10);
		var ComntUseropDef = {};
		ComntUseropDef.YFF_DYOPER_PAY 				= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_PAY%>;
		ComntUseropDef.YFF_DYOPER_GPARASTATE 		= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_GPARASTATE%>;
		ComntUseropDef.YFF_DYOPER_REVER 			= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_REVER%>;
		ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER    = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER%>;
		ComntUseropDef.YFF_DYOPER_MIS_REVER			= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_REVER%>;
		
		var SDDef = {};
		SDDef.STR_OR	 = 	"<%=SDDef.STR_OR%>";
		SDDef.SUCCESS	 = 	"<%=SDDef.SUCCESS%>";
		SDDef.CARD_OPTYPE_BUY   = <%=SDDef.CARD_OPTYPE_BUY%>;
		SDDef.CARD_OPTYPE_OPEN	= <%=SDDef.CARD_OPTYPE_OPEN%>;
		SDDef.YFF_OPTYPE_REVER  = <%=SDDef.YFF_OPTYPE_REVER%>;
		
		var yff_grid_title ='<i18n:message key="baseInfo.yff.grid_title"/>';
		var sel_user_info = "<i18n:message key="baseInfo.sel_user_info"/>";
		var khcg = "<i18n:message key="baseInfo.khcg"/>";
		var khsb = "<i18n:message key="baseInfo.khsb"/>";
		var zbje = "<i18n:message key="baseInfo.zbje"/>";
		var jsje = "<i18n:message key="baseInfo.jsje"/>";
		var jfje = "<i18n:message key="baseInfo.jfje"/>";
		var zje  = "<i18n:message key="baseInfo.zje"/>";
		<%
		Date date = new Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
		out.println("var _today = " + sdf.format(date));
		%>
	</script>
	</body>
</html>
