<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.Dict"%>
<%@page import="com.kesd.common.YDTable"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><i18n:message key="doc.dbfkcs" /></title>
    <style type="text/css">
	body {
		margin:0 0 0 0;
		overflow : auto;
		background: #eaeeef;
	}
	.tabsty {
		width:98%;
	}
	.tabsty input, .tabsty select {
		width: 110px;
	}
	input, select {
		font-size: 12px;
	}
	</style>
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  <script>
  	var basePath = '<%=basePath%>';
  </script>
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/docs/yffparaDialogpl_np.js"></script>
  <script src="<%=basePath%>js/docs/docper.js"></script>
  <script src="<%=basePath%>js/common/gridopt.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script src="<%=basePath%>js/common/dateFormat.js"></script>
  <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
  <script src="<%=basePath%>js/validate.js"></script>
  <script src="<%=basePath%>js/common/initDate.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  <script src="<%=basePath%>js/common/loading.js"></script>
  <jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
  </head>
  
  <body>
  <form action="" method="post" id="addorupdate"  style='display:inline;'>
  <br/>
  <table class="tabsty" align="center">
  <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;"><i18n:message key="doc.jbcsda" /><input type="checkbox" id="chk0" style="width: 16px;"/></td>
            <td style=" border: 0px; font-size: 12px; padding-left: 20px; color: blue;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  			</td>
          </tr>
        </table></td>
    </tr>
  	<!-- 基本参数档案 -->
    <tr>
      <td><input type="checkbox" id="areaId_" style="width: 16px;"/><label for="areaId_"><i18n:message key="doc.sspq" />: </label></td> 
      <td><input type="text" id="areadesc" name ="areadesc" readonly="readonly" style="background:#eee;border: 1px solid #808080; width:130px" onDblClick="$('#selarea').click();"/>
      <input id="selarea" type="button" value="..." style=" width:28px; height: 19px;"/></td>
      <td><input type="checkbox" id="ctRatio_" style="width: 16px;"/><label for="ctRatio_"><i18n:message key="doc.ctbb" />: </label></td> 
      <td><input type="text" id="textct" readonly="readonly" style="background:#eee;border: 1px solid #808080; width:130px"  value="1/1=1" ondblclick="editPTCT('CT')"/>&nbsp;<input type="button" value="..." onClick="editPTCT('CT')" style=" width:28px; height: 19px;"/></td>
      <td><input type="checkbox" id="commPwd_" style="width: 16px;"/><label for="commPwd_"><i18n:message key="doc.txmm" />: </label></td>
      <td><input type="text" name="meterpara.commPwd" id="commPwd" style=" width:160px"></td>
    </tr>
    
  	<!-- 基本费控参数 -->
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;"><label for="chk1"><i18n:message key="doc.fkcsda" /></label><input type="checkbox" id="chk1" style="width: 16px;"/></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td><input type="checkbox" id="powLimit_" style="width: 16px;"/><label for="powLimit_">限定功率(kw): </label></td>
      <td><input type="text" name ="nppaypara.powLimit" id="powLimit" style=" width:160px"/></td>
      <td><input type="checkbox" id="keyVersion_" style="width: 16px;"/><label for="keyVersion_"><i18n:message key="doc.mybb" />: </label></td><!-- 密钥版本 -->
      <td><input type="text" name="nppaypara.keyVersion" id="keyVersion" style=" width:160px"/></td>
      <td><input type="checkbox" id="nocycutFlag_" style="width: 16px;"/><label for="nocycutFlag_">无采样自动断电功能: </label></td>
      <td><select id="nocycutFlag" name ="nppaypara.nocycutFlag" style=" width:160px"/></select></td>
    </tr>
    
    <tr>
      <td><input type="checkbox" id="powerupProt_" style="width: 16px;"/><label for="powerupProt_">上电保护功能: </label></td>
      <td><select name="nppaypara.powerupProt" id="powerupProt" style=" width:160px"></select></td>
      <td><input type="checkbox" id="cryplinkId_" style="width: 16px;"/><label for="cryplinkId_"><i18n:message key="doc.ssjmj" />: </label></td><!-- 所属加密机-->
      <td><input type="text" name = "nppaypara.cryplinkId" id = "cryplinkId" style=" width:160px"/></td>
      <td><input type="checkbox" id="nocycutMin_" style="width: 16px;"/><label for="nocycutMin_">无采样自动断电时间: </label></td>
      <td><select id="nocycutMin" name ="nppaypara.nocycutMin" style=" width:160px"/></select></td>
    </tr>
    
    <tr>
      <td><input type="checkbox" id="feeBegindate_" style="width: 16px;"/><label for="feeBegindate_">费率启用日期: </label></td>
      <td>
      	<input type="hidden" id="feeBegindate" name ="nppaypara.feeBegindate" style=" width:160px"/>
      	<input type="text" id="feeBegindate_show" readonly style=" width:160px" onFocus="WdatePicker({dateFmt:'<i18n:message key="date.ymd.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});" />
      </td>
      <td></td><td></td>
      <td colspan=2></td>
    </tr>
    
    
    <tr>
      <td><input type="checkbox" id="feeprojId_" style="width: 16px;"/><label for="feeprojId_"><i18n:message key="doc.flfa" />: </label></td><!-- 费率方案 -->
      <td><select name ="nppaypara.feeprojId" id="feeprojId"  style=" width:160px"></select></td>
      <td><i18n:message key="baseInfo.fams" /></td>
    	<td id="feeproj_desc" colspan=3></td>
    </tr>
      
    <tr>
      <td><input type="checkbox" id="yffalarmId_" style="width: 16px;"/><label for="yffalarmId_"><i18n:message key="doc.bjfa" />: </label></td><!-- 报警方案 -->
      <td><select name="nppaypara.yffalarmId" id="yffalarmId"  style=" width:160px"></select></td>
      <td><i18n:message key="baseInfo.fams" /></td>
      <td id="yffalarm_desc" colspan=3></td> 
    </tr>
  </table>
  	<input type="hidden" name="meterextparanp.areaId" id="areaId" value="1"/><!-- 更改所属片区的参数 -->
  	<input type="hidden" name="mppara.ctRatio" id="ctRatio" value="1" />
  	<input type="hidden" name="mppara.ctNumerator" id="ctNumerator" value="1" />  <!-- ct分子 -->
  	<input type="hidden" name="mppara.ctDenominator" id="ctDenominator" value="1"/>  <!-- ct分母 -->
  <center>
    &nbsp;&nbsp;
    <jsp:include page="../inc/btn_xiugai.jsp"></jsp:include>
    &nbsp;&nbsp;
    <jsp:include page="../inc/btn_guanbi.jsp"></jsp:include>
  </center>
  
</form>
<jsp:include page="../../jsp/inc/jsdef.jsp"></jsp:include>
<script type="text/javascript">
	var update_con = "<i18n:message  key="comm.gridopt.update_cond" />";
    var update_fail = "<i18n:message key="comm.gridopt.update_fail" />";
    var ssjmj = "<i18n:message key="doc.ssjmj" />";
    var wcyzdddsj = "<i18n:message key="doc.wcyzdddsj" />";
    var xdgl = "<i18n:message key="doc.xdgl" />";
    var mybb = "<i18n:message key="doc.mybb" />";
    
    var YDTable = {};
    Dict.DICTITEM_YESFLAG		 = "<%=Dict.DICTITEM_YESFLAG%>";
    Dict.DICTITEM_NOCYCUT_MIN = "<%=Dict.DICTITEM_NOCYCUT_MIN%>";
    
    Dict.DICTITEM_USEFLAG        = "<%=Dict.DICTITEM_USEFLAG%>";
    Dict.DICTITEM_YFFMONEY		 = "<%=Dict.DICTITEM_YFFMONEY%>";
    Dict.DICTITEM_WIRINGMODE	 = "<%=Dict.DICTITEM_WIRINGMODE%>";
    Dict.DICTITEM_RTUPOLLID	 	 = "<%=Dict.DICTITEM_RTUPOLLID%>";
    Dict.DICTITEM_YFFMETERTYPE   = "<%=Dict.DICTITEM_YFFMETERTYPE%>";
	Dict.DICTITEM_PREPAYTYPE	 = "<%=Dict.DICTITEM_PREPAYTYPE%>";
	Dict.DICTITEM_FEETYPE 		 = "<%=Dict.DICTITEM_FEETYPE%>";
	Dict.DICTITEM_CSSTAND	 	 = "<%=Dict.DICTITEM_CSSTAND%>";
	Dict.DICTITEM_PAYTYPE	 	 = "<%=Dict.DICTITEM_PAYTYPE%>";
	YDTable.TABLECLASS_YFFALARMPARA  = "<%=YDTable.TABLECLASS_YFFALARMPARA%>";
	YDTable.TABLECLASS_YFFRATEPARA   = "<%=YDTable.TABLECLASS_YFFRATEPARA%>";
	YDTable.TABLECLASS_MPPARA    = "<%=YDTable.TABLECLASS_MPPARA%>";
	
	Dict.DICTITEM_CB_CYCLE_TYPE  = "<%=Dict.DICTITEM_CB_CYCLE_TYPE%>";
	
    </script>
  </body>
</html>
