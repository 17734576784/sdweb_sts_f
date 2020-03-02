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
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/dbzt.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>	
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>
	</head>
	<body>
	<table id="tabinfo1" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;"><i18n:message key="baseInfo.jbxx"/></td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
	    	<button id="btnSearch" class="btn" ><i18n:message key="btn.jiansuo"/></button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnCallState" class="btn" disabled><i18n:message key="btn.cxzt"/></button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<td style="border:0px; text-align: right;" ><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    	</td>
	    </tr>
   		<tr>
	   		<td class="tdr" style="width: 10%"><i18n:message key="baseInfo.yhbh"/></td><td id="userno" style="width: 10%">&nbsp;</td>
	    	<td class="tdr" style="width: 10%"><i18n:message key="baseInfo.yhmc"/></td><td id="username" style="width: 15%">&nbsp;</td>
	     	<td class="tdr" style="width: 10%"><i18n:message key="baseInfo.yhzt"/></td><td id="cus_state" style="width: 10%">&nbsp;</td>
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
    	<tr><td class="td_lable"><i18n:message key="baseInfo.dbztz"/></td></tr>
    	 <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.ytzbj"/></td><td id="ytzbj"> </td>
	    	<td class="tdr"><i18n:message key="baseInfo.gdfs"/></td><td id="gdfs"> </td>
	    	<td class="tdr"><i18n:message key="baseInfo.yxsd"/></td><td id="yxsd">  </td>
	    </tr>
	 	<tr>
	    	<td class="tdr"><i18n:message key="baseInfo.jdqml"/></td><td id="jdqml"> </td>
	    	<td class="tdr"><i18n:message key="baseInfo.dqjt"/></td><td id="dqjt"> </td>
	    	<td class="tdr"><i18n:message key="baseInfo.bcyx"/></td><td id="bcyx"> </td>
	    </tr>
	 	<tr>
	    	<td class="tdr"><i18n:message key="baseInfo.jdqzt"/></td><td id="jdqzt"> </td>
	    	<td class="tdr"><i18n:message key="baseInfo.fldj"/></td><td id="fldj"> </td>
	    	<td class="tdr"><i18n:message key="baseInfo.yxsq"/></td><td id="yxsq"> </td>
	    </tr>
	 	<tr>
	    	<td class="tdr"><i18n:message key="baseInfo.dnblx"/></td><td id="dnblx"> </td>
	    	<td class="tdr"><i18n:message key="baseInfo.bdzt"/></td><td id="bdzt"> </td>
	    	<td class="tdr"><i18n:message key="baseInfo.myzt"/></td><td id="myzt"></td>
	    </tr>
		<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    </table>
	</body>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	
	<script type="text/javascript">
	var ComntUseropDef = {};
	ComntUseropDef.YFF_READDATA = <%=com.kesd.comntpara.ComntUseropDef.YFF_READDATA%>;
	var ComntProtMsg = {};
	ComntProtMsg.YFF_CALL_DB_STATE3 = <%=com.kesd.comnt.ComntProtMsg.YFF_CALL_DB_STATE3%>;
	</script>
</html>
