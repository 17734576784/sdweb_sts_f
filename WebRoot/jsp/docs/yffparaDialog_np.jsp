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
    <title><i18n:message key="doc.dbkzcs" /></title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
    <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
<style type="text/css">
body {
	margin:0 0 0 0;
	overflow:auto;
	background: #eaeeef;
}
.tabsty {
	width:95%;
}
.tabsty input, .tabsty select {
	width: 110px;
}
input, select {
	font-size: 12px;
}
.disable{
	color : #666;
	disabled:expression(this.disabled=true);
}
.enable{
	color : #000;
	disabled:expression(this.disabled=false);
}
</style>
<script>
	var basePath = '<%=basePath%>';
</script>
<script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
<script src="<%=basePath%>js/docs/yffparaDialog_np.js"></script>
<script src="<%=basePath%>js/docs/docper.js"></script>
<script src="<%=basePath%>js/common/modalDialog.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script src="<%=basePath%>js/validate.js"></script>
<script src="<%=basePath%>js/common/dateFormat.js"></script>
<script src="<%=basePath%>js/common/def.js"></script>
<script src="<%=basePath%>js/common/loading.js"></script>
<jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
<jsp:include page="../../jsp/inc/jsdef.jsp"></jsp:include>
  </head>
  
  <body>
    <form action="" method="post" id="addorupdate"  style='display:inline;'>
  <br/>
  <table class="tabsty" align="center">
  	<tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;"><i18n:message key="doc.jbcsda" /></td>
            <td style=" border: 0px; font-size: 12px; padding-left: 20px; color: blue;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  			</td>
          </tr>
        </table></td>
    </tr>
    <!-- 基本参数档案 -->
    <tr>
      <td><i18n:message key="doc.fkdb" />: </td> <td><input type="text" id="meterdesc" name ="meterdesc" readonly style="background:#eee;border: 1px solid #808080;" style=" width:160px"/></td>
      <td><font color=red>*</font><i18n:message key="doc.sspq" />: </td> 
      <td><input type="text" id="areadesc" name ="areadesc" readonly="readonly" style="background:#eee;border: 1px solid #808080; width:130px" onDblClick="$('#selarea').click();"/>
      <input id="selarea" type="button" value="..." style=" width:28px; height: 19px;"/></td>
      <td><i18n:message key="doc.ctbb" />: </td> 
      <td><input type="text" id="textct" readonly="readonly" style="background:#eee;border: 1px solid #808080; width:130px"  value="1/1=1" ondblclick="editPTCT('CT')"/>&nbsp;<input type="button" value="..." onClick="editPTCT('CT')" style=" width:28px; height: 19px;"/></td>
    </tr>
     <tr>
      <td><font color=red>*</font><i18n:message key="doc.dbdz" />: </td>
      <td><input type="text" name="meterpara.commAddr" id="commAddr" style=" width:160px"></td>
      <td><font color=red>*</font><i18n:message key="doc.ESAM" />: </td>
      <td><input type="text" name = "meterpara.meterId" id="meterId" style=" width:160px"></td>
      <td><i18n:message key="doc.txmm" />: </td>
      <td><input type="text" name="meterpara.commPwd" id="commPwd" style=" width:160px"></td>
    </tr>

   <!-- 费控参数 档案表-->
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;"><i18n:message key="doc.fkcsda" /></td>
            <td style=" border: 0px; font-size: 12px; padding-left: 20px; color: blue;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  			</td>
          </tr>
        </table></td>
    </tr>
    <tr>
     <td>限定功率(kw):</td>
     <td><input type="text" id="powLimit" name ="nppaypara.powLimit" style=" width:160px"/></td>
     <td>无采样自动断电功能:</td>
      <td><select name="nppaypara.nocycutFlag" id="nocycutFlag" style=" width:160px"></select></td>
      <td>无采样自动断电时间: </td>
      <td><select id="nocycutMin" name ="nppaypara.nocycutMin" style=" width:160px"/></select></td>     
    </tr>
    <tr>
      
      <td>上电保护功能:</td>
      <td><select name="nppaypara.powerupProt" id="powerupProt" style=" width:160px"></select></td>
      <td><i18n:message key="doc.mybb" />: </td>
      <td><input type="text" id="keyVersion" name ="nppaypara.keyVersion" style=" width:160px"/></td>
     <td><i18n:message key="doc.ssjmj" />: </td>
      <td><input type="text" name = "nppaypara.cryplinkId" id = "cryplinkId" style=" width:160px"/></td>
    </tr>
    <tr>
      <td>费率启用日期:</td>
      <td>
    	<input type="hidden" name="nppaypara.feeBegindate" id="feeBegindate" />
	    <input type="text" id="feeBegindate_" readonly style=" width:160px" onFocus="WdatePicker({dateFmt:'<i18n:message key="date.ymd.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});" />
      </td>
      <td></td>
      <td></td>
       <td>农排表类型 :</td>
      <td><select name="nppaypara.yffmeterType" id="yffmeterType" style=" width:160px"></select></td>	
    </tr>
    <tr>
    	<td><i18n:message key="doc.flfa" />: </td>
    	<td>
    		<select name="nppaypara.feeprojId" id="feeprojId" style=" width:160px">
    			<option value="0">---未选择---</option>
    		</select>
    	</td>
    	<td><i18n:message key="baseInfo.fams" /></td>
    	<td id="feeproj_desc" colspan=3></td>
    </tr>
    <tr>
    	<td><i18n:message key="doc.bjfa" />: </td>
    	<td>
    		<select name="nppaypara.yffalarmId" id="yffalarmId" style=" width:160px">
    			<option value="0">---未选择---</option>
    		</select>
    	</td>
    	<td><i18n:message key="baseInfo.fams" /></td>
    	<td id="yffalarm_desc" colspan=3></td>
    </tr>   
  </table>
  <center>
    &nbsp;&nbsp;
    <jsp:include page="../inc/btn_xiugai.jsp"></jsp:include>
    &nbsp;&nbsp;
    <jsp:include page="../inc/btn_guanbi.jsp"></jsp:include>
  </center>
  <input type="hidden" name="nppaypara.rtuId" id="rtuId"/>
  <input type="hidden" name="nppaypara.mpId"  id="mpId"/>
  <input type="hidden" name="nppaypara.useFlag"  id="useFlag" value="1"/>
  <input type="hidden" name="meterextparanp.areaId" id="areaId"/><!-- 更改所属片区的参数 -->
  <input type="hidden" name="mppara.ctRatio" id="ctRatio" value="1" />
  <input type="hidden" name="mppara.ctNumerator" id="ctNumerator" value="1" />  <!-- ct分子 -->
  <input type="hidden" name="mppara.ctDenominator" id="ctDenominator" value="1"/>  <!-- ct分母 -->
</form>


<script type="text/javascript">
    var update_fail = "<i18n:message key="comm.gridopt.update_fail" />";
    var ssjmj = "<i18n:message key="doc.ssjmj" />";
    var wcyzdddsj = "<i18n:message key="doc.wcyzdddsj" />";
    var xdgl = "<i18n:message key="doc.xdgl" />";
    var mybb = "<i18n:message key="doc.mybb" />";
    var sspq = "<i18n:message key="doc.sspq" />";
    var dbdz = "<i18n:message key="doc.dbdz" />";
    var ESAM = "<i18n:message key="doc.ESAM" />";
    var ctbb = "<i18n:message key="doc.ctbb" />";
    var txmm = "<i18n:message key="doc.txmm" />";
   
    var YDTable = {};
    var YDDef = {};
    var Dict = {};

    Dict.DICTITEM_YESFLAG = "<%=Dict.DICTITEM_YESFLAG%>";
    Dict.DICTITEM_NOCYCUT_MIN = "<%=Dict.DICTITEM_NOCYCUT_MIN%>";
    Dict.DICTITEM_NPMETER_TYPE= "<%=Dict.DICTITEM_NPMETER_TYPE%>";
    
    Dict.DICTITEM_USEFLAG = "<%=Dict.DICTITEM_USEFLAG%>";
    Dict.DICTITEM_YFFMONEY = "<%=Dict.DICTITEM_YFFMONEY%>";
    Dict.DICTITEM_YFFMETERTYPE = "<%=Dict.DICTITEM_YFFMETERTYPE%>";
    Dict.DICTITEM_WIRINGMODE = "<%=Dict.DICTITEM_WIRINGMODE%>";
    Dict.DICTITEM_RTUPOLLID	 = "<%=Dict.DICTITEM_RTUPOLLID%>";
	Dict.DICTITEM_PREPAYTYPE	 = "<%=Dict.DICTITEM_PREPAYTYPE%>";
	Dict.DICTITEM_FEETYPE 		 = "<%=Dict.DICTITEM_FEETYPE%>";
	Dict.DICTITEM_CSSTAND	 	 = "<%=Dict.DICTITEM_CSSTAND%>";
	Dict.DICTITEM_PAYTYPE	 	 = "<%=Dict.DICTITEM_PAYTYPE%>";
	Dict.DICTITEM_CB_CYCLE_TYPE  = "<%=Dict.DICTITEM_CB_CYCLE_TYPE%>";
	
	YDTable.TABLECLASS_YFFALARMPARA  = "<%=YDTable.TABLECLASS_YFFALARMPARA%>";
	YDTable.TABLECLASS_YFFRATEPARA   = "<%=YDTable.TABLECLASS_YFFRATEPARA%>";
	YDTable.TABLECLASS_MPPARA   = "<%=YDTable.TABLECLASS_MPPARA%>";
    </script>
  </body>
</html>
