<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef,com.kesd.common.YDTable"%>
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
		<script  src="<%=basePath%>js/common/dateFormat.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setConsExt.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/dymakecard.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>		
		<script  src="<%=basePath%>js/validate.js"></script>
		
	</head>
	<body>
	<table id="tabinfo" class="tabinfo" align="center">
	   	<tr>
	    	<td class="td_lable" style="height:25px;">Informations de base</td>
	    	<td colspan=6 style="padding-left:10px; border: 0px;">
	    		<button id="btnSearch" class="btn" >Récupération</button>&nbsp;&nbsp;&nbsp;&nbsp;
 	    	</td>
	    </tr>
   		<tr>
	    	<td class="tdr" width="12%">ID consommateur:</td><td id="userno" width="22%">&nbsp;</td>
	    	<td class="tdr" width="15%">Nom du consommateur:</td><td id="username" width="22%">&nbsp;</td>
	     	<td class="tdr" width="15%">État consommateur:</td><td id="cus_state" width="22%">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">Téléphone:</td><td id="tel">&nbsp;</td>
	   		<td class="tdr">Adresse:</td><td colspan=3 id="useraddr">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">ID du compteur:</td><td id="commaddr">&nbsp;</td>
	    	<td class="tdr">Prepayment Mode:</td><td id="yffmeter_type_desc">&nbsp;</td>
	    	<td class="tdr">ID utilitaire:</td><td id="utilityid">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">Constructeur:</td><td id="factory">&nbsp;</td>
	    	<td></td><td></td>
	    	<td></td><td></td>
	    </tr>	    
	    
	</table>
	
	<table id="tabinfo3" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;"><input id = "chkclass2" name="chkclass2" type="checkbox" style="width:16px; align: left;" /><label for="chkclass2">Class2</label></td>
	   		<td colspan="3" style="height:25px;" id ="class2token" style="text-align: left;"></td>
	   		<td colspan="2" style="height:25px;" align = "right">&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;  <button id="saveClass2Token">Save Token</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button id="printC2Token">Print Token</button> </td>
	    </tr>	    
	    <tr>
	    	<td class="tdr" width="12%">Token Type:</td><td width="22%"><select id="tokentype" onChange="changeshowvalue();"><option value="0">SetMaximumPowerLimit</option><option value="1">ClearCredit</option><option value="3">Set1stSectionDecoderKey</option><option value="4">Set2ndSectionDecoderKey</option><option value="5">ClearTamperCondition</option><option value="6">SetMaximumPhasePowerUnbalanceLimit</option><option value="11">Reserved for Proprietary Use</option></select></td>
	    	<td class="tdr" width="15%"><label id="showvalue"></label></td><td width="22%"><input type="text" id="class2value" style="display: none" style="width: 160px;"/></td>
   			<td class="tdr" width="15%">RND:</td><td width="22%"><select id="RND" style="width: 130px"><option value="0">0</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option></select></td>   			
	    </tr>
	    <tr>
	    	<td class="tdr">TID:</td><td id="TID">&nbsp;</td>
   			<td class="tdr">Sub Class:</td><td id="subclass">&nbsp;</td>
   			<td class="tdr">Class:</td><td id="class2">2</td>
	    </tr>
	    <tr id ="tr1" style="display: none">
	        <td class="tdr">New Register NO:</td><td><input type="text" id="newmeterkey"/></td>
	    	<td class="tdr">New SGC:</td><td><input type="text" id="newSGC"/></td>
   			<td class="tdr">New KEN:</td><td><input type="text" id="newKEN"/></td>   			
	    </tr>
	    <tr id ="tr2" style="display: none">
	    	<td class="tdr">New Meter Number:</td><td><input type="text" id="newMeterNo"/></td>
   			<td class="tdr">New Key Type:</td><td><select id="newKT" style="width: 130px"><option value="0">0</option><option value="1">1</option><option value="2" selected>2</option><option value="3">3</option></select></td>
   			<td class="tdr">New Tariff Index:</td><td><input type="text" id="newTI"/></td>
	    </tr>
	    <tr id ="tr3" style="display: none">
	        <td class="tdr">New KRN:</td><td><select id="newKRN" style="width: 130px;"><option value="0">0</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option></select></td>
	      	<td class="tdr">Rollover Key Change(RO):</td><td><input type="checkbox" id="ro"/></td>
   			<td class="tdr">Reserved(RES):</td><td><input type="checkbox" id="res"/></td>
	    </tr>	    
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
	    <tr>
	    	<td class="td_lable" style="height:25px;"><input id = "chkclass1" name="chkclass1" type="checkbox" style="width:16px; text-align: left;" /><label for="chkclass1">Class1</label></td>
	   		<td colspan="3" style="height:25px;" id ="class1token" style="text-align: left;"></td>
	   		<td colspan="2" style="height:25px;" align = "right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button id="printC1Token">Print Token</button> </td>
	    </tr>
	    <tr>
	    	<td class="tdr">Select Test:</td><td><select id="selecttest" onChange="changecontrolvalue();"><option value="0">Test 0</option><option value="1">Load Switch</option><option value="2">Display</option><option value="3"> Cumulative kWh</option><option value="4">KRN</option><option value="5">TI</option><option value="7">Max Power Limit</option><option value="8">Tamper Status</option><option value="9">Power Consumption</option><option value="10">Software Version</option></select></td>
	    	<td class="tdr">Control(Hex):</td><td ><input type="text" id="controlvalue" style="width: 160px;"/></td>	
	    	<td class="tdr">Sub Class:</td><td><input type="text" id="subclass1"/></td>   			
	    </tr>
	    <tr>
	    	<td class="tdr">Class:</td><td id="class1" >1</td>
	    	<td class="tdr">Mfr Code:</td><td><input type="text" id="mfrcode" style="width: 160px;"/></td>
	    	<td></td><td></td>	    
	    </tr>
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
	    <tr>
	    	<td class="td_lable" style="height:25px;"><input id = "chkmeterkey" name="chkmeterkey" type="checkbox" style="width:16px; text-align: left;" /><label for="chkmeterkey">Meter Keys</label></td>
	    	<td colspan="3" style="height:25px;" id ="metertoken" style="text-align: left;"></td>
	    	<td colspan="2" style="height:25px;" align = "right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button id="savetoken"  class="btn" onClick="savemetertoken()">Save Meter Token</button></td>
	    </tr>
	    <tr>
	    	<td class="tdr">Key Type:</td><td><select id="keytype" style="width: 160px;"><option value="0">0</option><option value="1">1</option><option value="2" selected>2</option><option value="3">3</option></select></td>
	    	<td class="tdr">TI:</td><td><input type="text" id ="TI" style="width: 160px;"/></td>
	    	<td class="tdr">SGC:</td><td><input type="text" id="SGC" style="width: 160px;"/></td>
	    </tr>
	    <tr>	    	
	    	<td class="tdr">KRN:</td><td><select id="KRN" style="width: 160px;"><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option></select></td>
	    	<td class="tdr">DKGA:</td><td><select id ="DKGA" style="width: 160px;"><option value="01">01</option><option value="02" selected>02</option><option value="03">03</option><option value="04">04</option></select></td>
	    	<td class="tdr">DRN:</td><td><input type="text" id="DRN" style="width: 160px;"/></td>	   
	    </tr>
	    <tr>	    	
	    	<td class="tdr">IIN:</td><td id="IIN" style="width: 160px;">600727</td>
	    	<td class="tdr">VK1:</td><td id ="VK1" style="width: 160px;">abababababababab</td>
	    	<td class="tdr"></td><td></td>	   
	    </tr> 	    
 	    <tr>
	    	<td colspan="8"style="padding-left:10px; border: 0px;" align = "right"><button id="maketoken"  class="btn">Make Token</button>&nbsp;&nbsp;
	    </tr>
	</table>		
	
	<jsp:include page="../../inc/jsdef.jsp"></jsp:include>	
	<script type="text/javascript">
		var ComntUseropDef = {};
		ComntUseropDef.YFF_DYOPER_PAY = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_PAY%>;
		var SDDef = {};
		SDDef.STR_OR  = "<%=SDDef.STR_OR%>";
		SDDef.SUCCESS = "<%=SDDef.SUCCESS%>";
		var YDTable = {};
		YDTable.TABLECLASS_OCARDTYPE_PARA = '<%=YDTable.TABLECLASS_OCARDTYPE_PARA%>';
		YDTable.TABLECLASS_YFFRATEPARA   = "<%=YDTable.TABLECLASS_YFFRATEPARA%>";		
	</script>
	</body>
</html>
