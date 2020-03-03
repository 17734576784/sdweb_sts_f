<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.Dict"%>
<%@page import="com.kesd.common.YDTable"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>Archives du terminal</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
	<style type="text/css">
	body{
	  margin:0 0 0 0;
	  overflow:hidden;
	  background: #eaeeef;
	}
	.tabsty{
		width:95%;
	}
	.tabsty input,.tabsty select {
		width: 120px;
	}
	input,select{
		font-size: 12px;
	}
	</style>
	<script type="text/javascript">
		var basePath = '<%=basePath%>';
	</script>
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/modalDialog.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/validate.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/def.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/docs/rtuparaDialog.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/dateFormat.js"></script>
  </head>
  
  <body>
    <form action="" method="post" id="addorupdate"  style='display:inline;'>
    <br/>
    <table class="tabsty" align="center">
    	<tr><td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;">
    	<table cellpadding="0" cellspacing="0"><tr><td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td><td style="border: 0px; font-size: 12px;">Archive de base</td></tr></table>
    	</td></tr>
    	<tr>
	    	<td><font color=red>*</font>Nom:</td><td><input type="text" name="rtuPara.describe" id="describe"  style=" width:170px;"/></td>
	    	<td>Use-Flag:</td><td><select name="rtuPara.useFlag" id="useFlag"></select></td>
	    	<td><font color=red>*</font>Protocole:</td><td><select name="rtuPara.protType" id="protType" style=" width:170px;"></select></td>
	    </tr>
	    <tr>
	    	<td><font color=red>*</font>Adresse du terminal:</td><td><input type="text" name="rtuPara.rtuAddr" id="rtuAddr"  style=" width:170px;"/></td>
	    	<td><font color=red>*</font>Indicatif régional:</td><td><input type="text" name="rtuPara.areaCode" id="areaCode" /></td>
	    	<td><font color=red>*</font>Zone résidentielle:</td><td><input type="text" id="showconsId" readonly style="background-color:#DCDCDC;border: 1px solid #808080;width:145px;" onDblClick="$('#selconsId').click();" />&nbsp;<button id="selconsId">...</button></td>
	    </tr>
	    <tr>
	    	<td><font color=red>*</font>Type de terminal:</td><td><select name="rtuPara.rtuModel" id="rtuModel" style=" width:170px;"></select></td>
	    	<td>État de fonctionnement:</td><td><select name="rtuPara.runStatus" id="runStatus"></select></td>
	    	<td nowrap>Projet gelé:</td><td><select name="rtuPara.fzcbId" id="fzcbId" style=" width:170px;" ></select></td>
	    </tr>
        <tr>
            <td><font color=red>*</font>code d'identification:</td>
            <td><input type="text" name="rtuCommPara.authCode"    id="authCode" style=" width:170px;"/></td>
        	<td>Longueur du code d'authentification:</td>
	    	<td><input type="text" name="rtuCommPara.authCodelen" id="authCodelen" /></td>
	    	<td>&nbsp;</td><td>&nbsp;</td>
	    	
	    </tr>
	    <tr><td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr> 
	    
	    
	    
		<tr style="display: none"><td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;">
    	<table cellpadding="0" cellspacing="0"><tr><td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td><td style="border: 0px; font-size: 12px;">Archives de communication</td></tr></table>
    	</td></tr>

	    <tr style="display: none">
	    	<td><font color=red>*</font>Canal principal:</td><td><select name="rtuPara.chanMain" id="chanMain" style=" width:170px;"></select></td>
	    	<td>Canal esclave:</td><td><select name="rtuPara.chanBak" id="chanBak" ></select></td>
	    	<td><font color=red>*</font>Mode en ligne:</td><td><select name="rtuCommPara.onlineType" id="onlineType" style="width:170px;"></select></td>
	    </tr>
	    <tr style="display: none">
	    	<td>IP réseau:</td><td><input type="text" name="rtuCommPara.rtuIpaddr" id="rtuIpaddr" style=" width:170px;"/></td>
	    	<td>Port réseau:</td><td><input type="text" name="rtuCommPara.rtuIpport" id="rtuIpport" /></td>
            <td><!-- SIM卡号: --></td>
	    	<td>
	    		<!-- <input type="text" id="showSimcard" name="rtuCommPara.telno" style="border: 1px solid #808080;"/>&nbsp;<button id="selSimcard">...</button>-->
	    	</td>
	    </tr>
	    <tr style="display: none"><td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>
	    
	    
	    
    	 <tr style="display: none">
	    	<td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;">
	    	<table cellpadding="0" cellspacing="0"><tr><td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td><td style="border: 0px; font-size: 12px;">Archive des ressources</td></tr></table>
	    	</td>
    	</tr>
    	<tr style="display: none">
    		<td>N ° d'actif.:</td><td><input type="text" name="rtuPara.assetNo" id="assetNo" style=" width:170px;"/></td>
	    	<td>Code à barre:</td><td><input type="text" name="rtuPara.barCode" id="barCode" /></td>
	    	<td>Constructeur:</td><td><select name="rtuPara.factory" id="factory"  style=" width:170px;"></select></td>
	   	</tr>
    	<tr style="display: none">
	    	<td>Numéro d'usine:</td><td><input type="text" name="rtuPara.madeNo" id="madeNo" style=" width:170px;"/></td>
	    	<td>Code d'interface1:</td><td><input type="text" name="rtuPara.infCode1" id="infCode1" /></td>
	    	<td>Code d'interface2:</td><td><input type="text" name="rtuPara.infCode2" id="infCode2" style=" width:170px;"/></td>
	    </tr>
	    <tr style="display: none">
	    	<td>Site d'installation:</td><td><select name="rtuPara.instSite" id="instSite" style=" width:170px;"></select></td>
	    	<td>Date d'installation:</td>
	    	<td>
	    		<input type="text" id="instDateF" readonly onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:'false',readOnly:'true',lang:'zh-cn',position:{top:'above'}});" />
	    		<input type="hidden" name="rtuPara.instDate" id="instDate" />
	    	</td>
	    	<td>Date d'exécution:</td>
	    	<td>
	    		<input type="text" id="runDateF" readonly onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:'false',readOnly:'true',lang:'zh-cn',position:{top:'above'}});"  style=" width:170px;"/>
	    		<input type="hidden" name="rtuPara.runDate" id="runDate" />
	    	</td>
	    </tr>
    	<tr style="display: none">
	    	<td>Date d'arrêt:</td>
	   		<td>
	   			<input type="text" id="stopDateF" readonly onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:'false',readOnly:'true',lang:'zh-cn',position:{top:'above'}});" style=" width:170px;"/>
	   			<input type="hidden" name="rtuPara.stopDate" id="stopDate" />
	   		</td>
	   		<td></td><td></td>
	    	<td>
	    	</td>
	    	<td>
	    		<input type="hidden" name="rtuPara.consId" id="consId" />
	    		<input type="hidden" name="rtuPara.simcardId" id="simcardId" />
		   		<input type="hidden" name="rtuPara.residentNum" id="residentNum" /> 
		    	<input type="hidden" name="rtuPara.jlpNum" id="jlpNum" />
		    	<input type="hidden" id="id" name="rtuPara.id" />
		    	<input type="hidden" id="appType" name="rtuPara.appType" value="3" />
		    </td>
	     </tr>
    </table>
    <center>
    <jsp:include page="../inc/btn_tianjia.jsp"></jsp:include>
    &nbsp;&nbsp;
     <jsp:include page="../inc/btn_xiugai.jsp"></jsp:include>
    &nbsp;&nbsp;
    <jsp:include page="../inc/btn_guanbi.jsp"></jsp:include>
    </center>
    </form>
    <input type="hidden" id="preconsId" />
    <script type="text/javascript">
    var YDDef = {};
    var Dict = {};
    var YDTable = {};
    YDDef.BASEPATH = "<%=basePath%>";
    YDDef.SPLITCOMA = "<%=SDDef.SPLITCOMA%>";
    YDDef.APPTYPE_JC = "<%=SDDef.APPTYPE_JC%>";
    Dict.DICTITEM_USEFLAG = "<%=Dict.DICTITEM_USEFLAG%>";
    Dict.DICTITEM_JMRTUPROTTYPE = "<%=Dict.DICTITEM_JMRTUPROTTYPE%>";
    Dict.DICTITEM_RTUFACTORY = "<%=Dict.DICTITEM_RTUFACTORY%>";
    Dict.DICTITEM_RUNSTATUS = "<%=Dict.DICTITEM_RUNSTATUS%>";
    Dict.DICTITEM_INSTSITE = "<%=Dict.DICTITEM_INSTSITE%>";
    Dict.DICTITEM_NETONLINE = "<%=Dict.DICTITEM_NETONLINE%>";
    YDTable.TABLECLASS_CHANPARA = "<%=YDTable.TABLECLASS_CHANPARA%>";
    YDTable.TABLECLASS_RTUMODEL = "<%=YDTable.TABLECLASS_RTUMODEL%>";
    YDTable.TABLECLASS_INSTALLMAN = "<%=YDTable.TABLECLASS_INSTALLMAN%>";
    YDTable.TABLECLASS_SIMCARD = "<%=YDTable.TABLECLASS_SIMCARD%>";
    YDTable.TABLECLASS_CONSPARA = "<%=YDTable.TABLECLASS_CONSPARA%>";
	YDTable.TABLECLASS_FZCBTEMPLATE = "<%=YDTable.TABLECLASS_FZCBTEMPLATE%>";
    </script>
    <script src="<%=basePath%>js/docs/docper.js"></script>
	<jsp:include page="../../jsp/inc/jsdef.jsp"></jsp:include>  
  </body>
</html>
