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
			.tabinfo input{
				width: 99%;
			}
			input,select{
				font-size: 12px;				
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
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/dbsyje.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>	
		<script  src="<%=basePath%>js/dyjc/mis/mis.js"></script>
	</head>
	<body>
	<table id="tabinfo1" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;"><i18n:message key="baseInfo.jbxx"/></td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnSearch" class="btn" ><i18n:message key="btn.jiansuo"/></button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	<select style="width:120px;" id="dtType">
	    	<option value="dbye"><i18n:message key="baseInfo.dbye"/></option>
	    	<option value="dbyetc"><i18n:message key="baseInfo.dbyetc"/></option>
	    	<option value="esamye"><i18n:message key="baseInfo.ESAMye"/></option>
	    	<option value="sqjetc"><i18n:message key="baseInfo.sqjetc"/></option>
	    	</select>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnRead" class="btn" disabled><i18n:message key="baseInfo.duqu"/></button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	<td style="border:0px; text-align: right;" ><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    	</td>
	    </tr>
      	<tr>
	    	<td class="tdr" ><i18n:message key="baseInfo.yhbh"/></td><td id="userno" ></td>
	    	<td class="tdr" ><i18n:message key="baseInfo.yhmc"/></td><td colspan=3 id="username" ></td>
	     		    	
	    </tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.lxdh"/></td><td id="tel"></td>
	   		<td class="tdr"><i18n:message key="baseInfo.yhdz"/></td><td colspan=3 id="useraddr"></td>
	    </tr>
	    
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td>	</tr>
    	<tr><td class="td_lable"><i18n:message key="baseInfo.dbxx"/></td></tr>
    	<tr>
	    	<td class="tdr" style="width: 10%"><i18n:message key="baseInfo.bh"/></td><td id="esamno" style="width: 10%"></td>
	    	<td class="tdr" style="width: 10%"><i18n:message key="baseInfo.ptbb"/></td><td id="pt_ratio" style="width: 10%"></td>
	    	<td class="tdr" style="width: 10%"><i18n:message key="baseInfo.ctbb"/></td><td id="ct_ratio" style="width: 10%"></td>
	    </tr>
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
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
	
		<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable"><i18n:message key="baseInfo.dqxx"/></td></tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.gdcs"/></td><td class="buy_times" style="width: 10%;"><span id="buy_times" ></span></td>
	    	<td class="tdr"><i18n:message key="baseInfo.syje"/><i18n:message key="baseInfo.yuan"/></td><td class="tdrn" style="width: 100px;"><span  id="syje"></span></td>
	    	<td></td><td></td>			
	    </tr>
	   	<tr>
	    	<td class="tdr"><i18n:message key="baseInfo.ljgdje"/><i18n:message key="baseInfo.yuan"/></td><td class="tdrn" style="width: 10%;"><span id=ljgdje ></span></td>
	    	<td class="tdr"><i18n:message key="baseInfo.sqje"/><i18n:message key="baseInfo.yuan"/></td><td class="tdrn" style="width: 100px;"><span id="sqje"></span></td>
	    	<td></td><td></td>			
	    </tr>
	   	<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
   	   	<tr><td class="td_lable"  style="border-bottom: 0"><i18n:message key="baseInfo.gdjl"/></td></tr>	
	</table>
	<table class="gridbox" align="center">
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
	</table> 

	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>	
	<input type="hidden" id="pay_type"/>
<%--	<input type="hidden" id="buy_times"/>--%>
	<script>
		$("#gridbox").height($(window).height()-$("#tabinfo1").height()-10);
		var	mygrid = new dhtmlXGridObject('gridbox');
		
	</script>
	</body>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	<script type="text/javascript">
	var ComntUseropDef = {};
	ComntUseropDef.YFF_READDATA = <%=com.kesd.comntpara.ComntUseropDef.YFF_READDATA%>;
	ComntUseropDef.YFF_DYCOMM_CALLPARA = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYCOMM_CALLPARA%>;
	var ComntProtMsg = {};
	ComntProtMsg.YFF_CALL_DB_TCREMAIN = <%=com.kesd.comnt.ComntProtMsg.YFF_CALL_DB_TCREMAIN%>
	ComntProtMsg.YFF_CALL_DB_TCOVER = <%=com.kesd.comnt.ComntProtMsg.YFF_CALL_DB_TCOVER%>;
	ComntProtMsg.YFF_DY_CALL_STATE = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_CALL_STATE%>;
	ComntProtMsg.YFF_CALL_DY_REMAIN = <%=com.kesd.comnt.ComntProtMsg.YFF_CALL_DY_REMAIN%>;
	
	var yff_grid_title ='<i18n:message key="baseInfo.yff.grid_title"/>';
	</script>
</html>
