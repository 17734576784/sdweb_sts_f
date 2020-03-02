<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
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
				width: 120px;
			}
		</style>
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>		
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/dyjc/main/dyrever.js"></script>
		<script  src="<%=basePath%>js/dyjc/mis/mis.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>	
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
		
	</head>
	<body>
	<table id="tabinfo" class="tabinfo" align="center">
    	<jsp:include page="user_info.jsp"></jsp:include>
	</table>
	<table class="gridbox" align="center">
		<tr><td style="padding: 0 0 0 0;"><div id=gridbox style="height:160px;border:0px;"></div></td></tr>
	</table> 
	<table class="tabinfo" align="center" id="tabinfo1">
		<tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable">缴费信息</td>
	    </tr>
	    <tr>
	    	<td class="tdr" style="width: 140px">购电次数:</td><td class="tdrn" colspan='7'><span id="buy_times" ></span></td>
	    </tr>
	    <tr>
   		    <td class="tdr" width="140">上次缴费金额(元):</td><td class="tdrn"><input id="last_je" class="readonly_bg" readonly /></td>
	    	<td class="tdr">上次追补金额(元):</td><td class="tdrn"><input id="last_zbje" class="readonly_bg" readonly /></td>
	    	<td class="tdr">上次结算金额(元):</td><td class="tdrn"><input id="last_jsje" class="readonly_bg" readonly /></td>
	    	<td class="tdr">上次总金额(元):</td><td class="tdrn"><input id="last_tt" class="readonly_bg" readonly/></td>
	    </tr>
	    <tr>
			<td class="tdr" >实际缴费金额(元):</td><td class="tdrn"><input id="jfje" /></td>
			<td class="tdr" >实际追补金额(元):</td><td class="tdrn"><input id="zbje" /></td>
			<td class="tdr" >实际结算金额(元):</td><td class="tdrn"><input id="jsje" /></td>
			<td class="tdr" >实际总金额(元):     </td><td class="tdrn"> <span id="zje">&nbsp;</span></td>
	    </tr>
		<tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>
		<tr><td class="tdr" colspan=8 style="border: 0px;">
		<button id="btnRever" class="btn" disabled>冲正</button>&nbsp;&nbsp;&nbsp;&nbsp;
		<button id="btnPrint" class="btn" disabled>打印</button>
		</td>
		</tr>
	</table>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="feeproj_id"/>
	<input type="hidden" id="yffalarm_id"/>
	<input type="hidden" id="lastdate"/>
	<input type="hidden" id="lasttime"/>
	
	<jsp:include page="../../inc/jsdef.jsp"></jsp:include>
	<script type="text/javascript">
 		$("#gridbox").height($(window).height()-$("#tabinfo1").height()-$("#tabinfo").height()-15);
 		var ComntUseropDef = {};
		ComntUseropDef.YFF_DYOPER_REVER = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_REVER%>;
		ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER%>;
		ComntUseropDef.YFF_DYOPER_MIS_REVER = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_REVER%>;
		
		var SDDef = {};
		SDDef.YFF_OPTYPE_REVER = <%=SDDef.YFF_OPTYPE_REVER%>;
		<%
		Date date = new Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
		out.println("var _today = " + sdf.format(date));
		%>
	</script>
	</body>
</html>
