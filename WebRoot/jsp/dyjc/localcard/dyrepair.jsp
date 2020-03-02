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
	    <title>低压补卡</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<style type="text/css">
		    <style type="text/css">
			input{
				width: 130px;
			}
			input,select{
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
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/dyjc/localcard/dyrepair.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>
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
	    	<td class="tdr" width="12%">ID consommateur:</td><td id="userno" width="15%">&nbsp;</td>
	    	<td class="tdr" width="12%">Nom du consommateur:</td><td id="username" width="20%">&nbsp;</td>
	     	<td class="tdr" width="12%">État consommateur:</td><td id="cus_state">&nbsp;</td>
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
	    	<td></td><td></td>
	    	<td></td><td></td>
	    </tr>
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable">Informations sur le contrôle des coûts</td></tr>
	   <tr>
	    	<td class="tdr">Mode de facturation:</td><td id="cacl_type_desc">&nbsp;</td>
	    	<td class="tdr">Mode de contrôle des coûts:</td><td id="feectrl_type_desc">&nbsp;</td>
	    	<td class="tdr">Mode de paiement:</td><td id="pay_type_desc">&nbsp;</td>
	    </tr>
	   	<tr>
	    	<td class="tdr">Projet tarifaire:</td><td id="feeproj_desc">&nbsp;</td>
	    	<td class="tdr">Description tarifaire:</td><td colspan=3 id="feeproj_detail">&nbsp;</td>
	   	</tr>
	   	<tr>
	   		<td class="tdr">Montant à découvert:</td><td id="tzval">&nbsp;</td>
	   		<td></td><td></td>
	   		<td></td><td></td>
	    </tr>
	 	<tr><td colspan="6" style="height:5px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>	    
	    <tr id="_title3">
	    <td class="td_lable" style="border-bottom: 0">Dossier d'achat</td>
	    </tr>	
	</table>
	<table class="gridbox" align="center">
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
	</table>
	<table id="tabinfo2" class="tabinfo" align="center">
		<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable">Recharge Information</td>	  
	    </tr>
	    <tr>
		    <td class="tdr" width="15%">Times of Purchasing:</td><td class="tdrn" width="17%"><span id="buy_times"></span></td>
		    <td class="tdr" width="15%">Last Recharge Amount(TAKA):</td><td class="tdrn"><input type="text" id="jfje" readonly="readonly" style="background-color: #ccc;"/></td>			
		    <td class="tdr" width="15%">Last Sanctioned Amount(TAKA):</td><td class="tdrn" width="20%"><input type="text" id="zbje" readonly="readonly" style="background-color: #ccc;"/></td>    	
	    </tr>
	    <tr>
	    	<td class="tdr">Last Total Amount(TAKA):</td><td class="tdrn"><span id="zje" style="width: 100%"></span></td>
	    	<td class="tdr"></td><td class="tdrn" ></td>
	  		<td class="tdr"></td><td class="tdrn"></td>
	    </tr>
	</table>
	<table id="tabinfo3" align="right">
		<tr><td>
			<button id="cardinfo"   class="btn" >Card Information</button> &nbsp;&nbsp;&nbsp;&nbsp; 
			<button id="repair"  	class="btn" >Reissued Card</button>&nbsp;&nbsp;&nbsp;&nbsp; 
			<!-- <button id="btnPrt"     class="btn" disabled="disabled">打印</button>&nbsp;&nbsp; --> 
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
	
	<input type="hidden" id="friend_s"/>
	<input type="hidden" id="friend_e"/>
	<input type="hidden" id="friendDate"/>
	<input type="hidden" id="res_id"/>	
	
	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
 		$("#gridbox").height($(window).height()-$("#tabinfo").height()-$("#tabinfo2").height()-$("#tabinfo3").height()-5);
		var ComntUseropDef = {};
		ComntUseropDef.YFF_DYOPER_PAY        = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_PAY%>;
		ComntUseropDef.YFF_DYOPER_GPARASTATE = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_GPARASTATE%>;
		ComntUseropDef.YFF_DYOPER_REPAIR     = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_REPAIR%>
	
		var SDDef = {};
		SDDef.STR_OR	 			= "<%=SDDef.STR_OR%>";
		SDDef.SUCCESS	 			= "<%=SDDef.SUCCESS%>";
		SDDef.YFF_CUSSTATE_NORMAL	= <%=SDDef.YFF_CUSSTATE_NORMAL%>;	 
		SDDef.YFF_OPTYPE_REPAIR		= <%=SDDef.YFF_OPTYPE_REPAIR%>;
		SDDef.CARD_OPTYPE_REPAIR	= <%=SDDef.CARD_OPTYPE_REPAIR%>;
		SDDef.CARD_OPTYPE_OPEN		= <%=SDDef.CARD_OPTYPE_OPEN%>
		
		var khcg = "<i18n:message key="baseInfo.khcg"/>";
		var khsb = "<i18n:message key="baseInfo.khsb"/>";
		var zbje = "<i18n:message key="baseInfo.zbje"/>";
		var jsje = "<i18n:message key="baseInfo.jsje"/>";
		var jfje = "<i18n:message key="baseInfo.jfje"/>";
		var zje =  "<i18n:message key="baseInfo.zje"/>";
	</script>
	</body>
</html>
