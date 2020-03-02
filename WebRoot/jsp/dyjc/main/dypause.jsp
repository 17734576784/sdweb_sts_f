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
				width:80%
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
		<script  src="<%=basePath%>js/dyjc/main/dypause.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>	
	</head>
	<body>
	<table id="tabinfo1" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:5px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;"><i18n:message key="baseInfo.jbxx"/></td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
	    	<button id="btnSearch" class="btn" ><i18n:message key="btn.jiansuo"/></button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>
	    	<td style="border:0px; text-align: right;" ><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    </tr>
   		<tr>
	    	<td class="tdr" style="width: 140px"><i18n:message key="baseInfo.yhbh"/></td><td id="userno" style="width: 17%">&nbsp;</td>
	    	<td class="tdr" style="width: 12%"><i18n:message key="baseInfo.yhmc"/></td><td id="username" style="width: 20%">&nbsp;</td>
	     	<td class="tdr" style="width: 12%"><i18n:message key="baseInfo.yhzt"/></td><td id="cus_state">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.lxdh"/></td><td id="tel">&nbsp;</td>
	   		<td class="tdr"><i18n:message key="baseInfo.yhdz"/></td><td colspan=3 id="useraddr">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.bh"/></td><td id="esamno">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.yffblx"/></td><td id="yffmeter_type_desc">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.sccj"/></td><td id="factory">&nbsp;</td>
	    </tr>
	    <tr>
	  	    <td class="tdr"><i18n:message key="baseInfo.bl"/></td><td id="blv">&nbsp;</td>
	        <td class="tdr"><i18n:message key="baseInfo.jxfs"/></td><td id="wiring_mode">&nbsp;</td>
	    	<td>&nbsp;</td><td>&nbsp;</td>
	     </tr>
	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr><td class="td_lable"><i18n:message key="baseInfo.fkxx"/></td></tr>
	   <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.jiffs"/></td><td id="cacl_type_desc">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.fkfs"/></td><td id="feectrl_type">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.jffs"/></td><td id="pay_type_desc">&nbsp;</td>    	
	    </tr>
	   	<tr>
	    	<td class="tdr"><i18n:message key="baseInfo.flfa"/></td><td id="feeproj_desc">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.fams"/></td><td colspan=3 id="feeproj_detail">&nbsp;</td>
	   	</tr>
	   	<tr>
	   		<td class="tdr"><i18n:message key="baseInfo.bjfa"/></td><td id="yffalarm_desc">&nbsp;</td>
	   		<td class="tdr"><i18n:message key="baseInfo.fams"/></td><td colspan=3 id="yffalarm_detail">&nbsp;</td>
	    </tr>
		
		<tr><td colspan="6" style="height:8px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>	    
	   	<tr><td class="td_lable"  style="border-bottom: 0"><i18n:message key="baseInfo.gdjl"/></td></tr>	
	</table>
	
	<table class="gridbox" align="center">
		<tr><td style="padding: 0 0 0 0;"><div id=gridbox style="border:0px;"></div></td></tr>
	</table> 
	<table class="tabinfo" align="center" id="tabinfo">
    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" style="height:25px; width:140px;"><i18n:message key="baseInfo.bdxx"/></td>
	    	<td colspan=5 style="padding-left:10px; border: 0px;">
	    	<button id="btnImportBD" class="btn" disabled = "disabled"><i18n:message key="btn.luru"/></button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td></tr>
	    <tr>
	    	<td class="tdr" style="width:140px"><i18n:message key="baseInfo.bdVal" /></td>
	    	<td colspan="5" id="td_bdinf"  ></td>
	    </tr>
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
	    <tr>
    	   	<td class="td_lable" style="100px;"><i18n:message key="baseInfo.jfxx"/></td>	  
	    </tr>
	    
	   <tr>
			<td class="tdr" width = "140px"><i18n:message key="baseInfo.syje"/><i18n:message key="baseInfo.yuan"/></td><td class="tdrn" width = "17%"><input id=syje readonly="readonly" style="{background:#ccc}"/></td>			
	        <td class="tdr" width = "10%">算费表底时间:</td><td id="sfbd_sj"  width = "10%">&nbsp;</td>
	   		<td class="tdr" width = "10%">算费表底:          </td><td id="sfbd" >&nbsp;</td>
	   </tr>
	    <tr>
			<td colspan=6 style="text-align: right;border: 0">
		       	<button id="btnCompute" class="btn"><i18n:message key="btn.jsye"/></button>&nbsp;&nbsp;&nbsp;&nbsp;
		    	<button id="btnPause"   class="btn"><i18n:message key="idxhref.zanting"/></button>&nbsp;&nbsp;&nbsp;&nbsp;
		    	<button id="btnPrint"   class="btn"   disabled = "disabled">打印</button>
	    	</td>
	    </tr>
	</table>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	<script type="text/javascript">
		$("#gridbox").height($(window).height() - $("#tabinfo").height()-$("#tabinfo1").height() - 20 )
		var ComntUseropDef = {};
		ComntUseropDef.YFF_DYOPER_PAUSE = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_PAUSE%>;
		ComntUseropDef.YFF_DYOPER_GETREMAIN = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_GETREMAIN%>;
	</script>
	
	</body>
</html>
