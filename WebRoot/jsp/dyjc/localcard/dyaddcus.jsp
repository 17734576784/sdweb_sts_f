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
		.tabinfo input,select{
			width: 150px;
		}
		input,select{
			font-size: 12px;				
		}
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<script>
			var basePath = '<%=basePath%>';
		</script>
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/dyjc/localcard/dyaddcus.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>
		<script  src="<%=basePath%>js/dyjc/mis/mis.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
	</head>
	<body>
	<table id="tabinfo" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;">Informations de base</td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
	    	<button id="btnSearch" class="btn" >Récupération</button>
	    	</td>
	    	<td style="border:0px; text-align: right;" ><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    </tr>
   		<tr>
	    	<td class="tdr" width="12%">ID consommateur:</td><td id="userno" width="20%">&nbsp;</td>
	    	<td class="tdr" width="12%">Nom du consommateur:</td><td id="username" width="20%">&nbsp;</td>
	     	<td class="tdr" width="12%">État consommateur:</td><td id="cus_state" width="20%">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">Téléphone:</td><td id="tel">&nbsp;</td>
	   		<td class="tdr">Adresse:</td><td colspan=3 id="useraddr">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">ID du compteur:</td><td id="commaddr">&nbsp;</td>
	    	<td class="tdr">Type de prépaiement:</td><td id="yffmeter_type_desc">&nbsp;</td>
	    	<td class="tdr">ID utilitaire:</td><td id="utilityid">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">Constructeur:</td><td id="factory">&nbsp;</td>
	   		<td class="tdr">CT:</td><td id="ctData"></td>
	   		<td class="tdr">PT:</td><td id="ptData"></td>
	    </tr>
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable">Informations sur le contrôle des coûts</td></tr>
	   <tr>
	    	<td class="tdr">Mode de facturation:</td><td id="cacl_type_desc">&nbsp;</td>
	    	<td class="tdr">Cost Control Mode:</td><td id="feectrl_type_desc">&nbsp;</td>
	    	<td class="tdr">Payment Mode:</td><td id="pay_type_desc">&nbsp;</td>
	    </tr>
	   	<tr>
	    	<td class="tdr">Tariff Project:</td><td id="feeproj_desc">&nbsp;</td>
	    	<td class="tdr">Tariff Description:</td><td colspan=3 id="feeproj_detail">&nbsp;</td>
	   	</tr>
	   	<tr>
	   		<td class="tdr">Overdraw Amount:</td><td id="tzval">&nbsp;</td>
	   		<td class="tdr">KEN:</td><td><input type="text" id="KEN" value="255"/></td>
	   		<td></td><td></td>
	    </tr>
		<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" style="width: 120px;">Recharge Information</td></tr>
	    <tr>
	    	<td class="tdr">Payment amount:</td><td class="tdrn"><input id=zje /></td>		  		        		    				
	    	<td class="tdr">Transfer Credit:</td><td class="tdrn" id="zdl"> </td>
	    	<td></td><td></td>
	    </tr>
	    
	    <tr id="tokens" style="display: none">
	   		<td class="tdr">1stKCT:</td><td class="tdrn" id="token1">&nbsp;</td>
	   		<td class="tdr">2ndKCT:</td><td class="tdrn" id="token2"></td>
	   		<td class="tdr">Open Account Token:</td><td id="opentoken"></td>
	    </tr>
	    
	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	<td colspan="6" style="text-align: right;border: 0">
    		<button id="btnNew"  	class="btn" disabled="disabled">Open an account</button> &nbsp;&nbsp;&nbsp;&nbsp;  
    		<button id="prt"  class="btn"  disabled="disabled">Print</button> &nbsp;&nbsp;&nbsp;&nbsp;  
	    </td></tr>
	</table>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="feeproj_id"/>
	<input type="hidden" id="feectrl_type"/>
	<input type="hidden" id="cacl_type"/>
	<input type="hidden" id="seqNo"/>
	<input type="hidden" id="keyNo"/>
	<input type="hidden" id="fmaximum_power">
	<input type="hidden" id="gmaximum_power">
	<input type="hidden" id="power_start">
	<input type="hidden" id="power_end">
	<input type="hidden" id="friend_s"/>
	<input type="hidden" id="friend_e"/>
	<input type="hidden" id="friendDate"/>
	<input type="hidden" id="res_id"/>
	<input type="hidden" id="power_date"/>
	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
	
	
	var ComntUseropDef = {};
	ComntUseropDef.YFF_DYOPER_ADDRES 	= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_ADDRES%>
	ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER%>;
	ComntUseropDef.YFF_DYOPER_MIS_PAY 		 = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_PAY%>;
	
	var SDDef		   = {};
	SDDef.STR_AND 				= "<%=SDDef.STR_AND%>";
	SDDef.STR_OR 				= "<%=SDDef.STR_OR%>";
	SDDef.YFF_OPTYPE_ADDRES		= <%=SDDef.YFF_OPTYPE_ADDRES%>;
	SDDef.YFF_CUSSTATE_NORMAL	= <%=SDDef.YFF_CUSSTATE_NORMAL%>;	
	SDDef.CARD_OPTYPE_OPEN		= <%=SDDef.CARD_OPTYPE_OPEN%>;
	SDDef.YFF_FEETYPE_JTFL  	= <%=SDDef.YFF_FEETYPE_JTFL%> //阶梯费率             3
	SDDef.YFF_FEETYPE_MIXJT 	= <%=SDDef.YFF_FEETYPE_MIXJT%>//混合阶梯费率 4
	
	var sel_user_info = "<i18n:message key="baseInfo.sel_user_info"/>";
	var khcg = "<i18n:message key="baseInfo.khcg"/>";
	var khsb = "<i18n:message key="baseInfo.khsb"/>";
	var yzje = "<i18n:message key="baseInfo.yzje"/>";
	var yzje = "<i18n:message key="baseInfo.yzje"/>";
	var jfje = "<i18n:message key="baseInfo.jfje"/>";
	var zje =  "<i18n:message key="baseInfo.zje"/>";
	

	</script>
	</body>
</html>
