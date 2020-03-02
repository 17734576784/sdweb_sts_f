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
    <title>Meter & Cost Control Archive</title>
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
<script src="<%=basePath%>js/docs/yffparaDialog_dyjc.js"></script>
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
<!--电表档案start-->
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;">
        <table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">Basic Archive</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
   		<td nowrap><font color=red>*</font>Record ID:</td><td><input type="text" name="meterpara.mpId" id="id_mp" readonly="readonly" style="background-color:#DCDCDC;border: 1px solid #808080; width: 160px;"/></td>
    	<td nowrap><font color=red>*</font>Meter Name:</td><td><input type="text" name="mppara.describe" id="describe_mpp" /></td>
    	<td nowrap>Terminal Name:</td><td><input type="text" name="rtu_describe" id="rtu_describe" readonly="readonly" style="background-color:#DCDCDC;border: 1px solid #808080; width: 160px;"/></td>
    </tr>
    <tr>	
   		<td nowrap><font color=red>*</font>Resident Name:</td><td><select name="meterpara.residentId" id="residentId" style="width: 160px;"></select></td>
		<td nowrap><font color=red>*</font>Meter ID:</td><td><input type="text" name="meterpara.commAddr" id="commAddr" /></td>
   		<td nowrap>Manufacturer:</td><td><select name="meterpara.factory" id="factory" style="width: 160px;"></select></td>
   	</tr>
   	<tr>
    	<td nowrap>Made Number:</td><td><input type="text" name="meterpara.madeNo" id="madeNo" style="width: 160px;background:#DCDCDC" disabled/></td>   		
   		<td nowrap>Communication Password:</td><td><input type="text" name="meterpara.commPwd" id="commPwd" /></td>
   		<td nowrap>Asset Code:</td><td><input type="text" name="meterpara.assetNo" id="assetNo" style="width: 160px;"/></td>
   	</tr>

<!--电表档案end-->
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>
<!--费控档案start-->
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;">
      	<table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">Cost Control Archive</td>
          </tr>
        </table>
       </td>
    </tr>
    <tr>
	    <td>Meter Type: </td><td><select name = "mppaypara.yffmeterType" id= "yffmeterType" style="width: 160px;"></select></td>
    	<td>Mode de facturation: </td><td><select  name="mppaypara.caclType" id="caclType"></select></td>
    	<td>Cost Control Mode: </td><td><select name ="mppaypara.feectrlType" id="feectrlType" style="width: 160px;"></select></td>
    </tr>
    <tr>
      <td>Overdraw Amount: </td><td><select name = "mppaypara.tzVal" id= "tzVal" style="width: 160px;"></select></td>
	  <td><font color=red>*</font>Key Revision Number(KRN): </td><td><input type="text" id="keyVersion" name ="mppaypara.keyVersion" value="1"/></td>
      <td>Tariff Project: </td><td><select name="mppaypara.feeprojId" id="feeprojId" style="width: 160px;"></select></td>
    </tr>
    <tr>
      <td>Payment Mode:</td><td><select name ="mppaypara.payType" id="payType" style="width: 160px;"></select></td>
      <td>Friendly Mode:</td><td><select name="weekday" id="weekday"></select></td>
      <td><font color=red>*</font>Tariff Index:</td><td><input type="text" id="tariffIndex" name ="meterstspara.ti" value="1"/></td>
      	  
    </tr> 
    <tr>
      <td><font color=red>*</font>KeyExpiryNumber:</td><td><input type="text" id="keyExpiryNumber" name ="meterstspara.ken" value="255"/></td>
      <td><font color=red>*</font>PT:</td><td><input type="text" id="ptRatio" name ="mppara.ptRatio" value="1.00"/>
<!--      <button id="ptBtn" style="width:30px;height:25px" onclick="onClickDiag('pt')">...</button>-->
      </td>
      <td><font color=red>*</font>CT:</td><td><input type="text" id="ctRatio" name ="mppara.ctRatio" value="1.00"/>
<!--      <button id="ctBtn" style="width:30px;height:25px" onclick="onClickDiag('ct')">...</button>-->
      </td>
    </tr> 
     <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>
    <tr style="display:none">
	  <td>Friendly Hour Start(hh): </td><td><input type="text" id="protSt" name ="mppaypara.protSt" style="width: 160px;"/></td>
      <td>Friendly Hour End(hh): </td><td><input type="text" name = "mppaypara.protEd" id = "protEd" /></td>
      <td></td><td></td>	  
    </tr> 
	<tr style="display:none">
   		<td  colspan="4">
   			<input type="checkbox" id="monday" style="width: 16px;" onclick="checkWeekday();"/><label for="monday">Monday</label>
			<input type="checkbox" id="tuesday" style="width: 16px;" onclick="checkWeekday();"/><label for="tuesday">Tuesday</label>
			<input type="checkbox" id="wednesday" style="width: 16px;" onclick="checkWeekday();"/><label for="wednesday">Wednesday</label>
			<input type="checkbox" id="thursday" style="width: 16px;" onclick="checkWeekday();"/><label for="thursday">Thursday</label>
			<input type="checkbox" id="friday" style="width: 16px;" onclick="checkWeekday();"/><label for="friday">Friday</label>
			<input type="checkbox" id="saturday" style="width: 16px;" onclick="checkWeekday();"/><label for="saturday">Saturday</label>
			<input type="checkbox" id="sunday" style="width: 16px;" onclick="checkWeekday();"/><label for="sunday">Sunday</label>
			<input type="hidden"   name="mppara.reserve1" id="reserve1" onclick="checkWeekday();"/>
   		</td>
   		<td></td><td></td>
   	</tr>
   	<tr style="display:none">
   		<td>Maximum Power Active Date:</td>
	    <td>
	    	<input type="text" id="ctDenominatorF" readonly onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn',isShowClear:'false',readOnly:'true',position:{top:'above'}});" style="width: 160px;"/>
	    	<input type="hidden" name="mppara.ctDenominator" id="ctDenominator" />
	    </td>
	    
	    <td>Maximum Power(Peak):</td><td><input type="text" id="rp" name ="mppara.rp"/></td>
	    <td>Maximun Power(Offpeak):</td><td><input type="text" id="mi" name ="mppara.mi" style="width: 160px;"/></td>	    
   	</tr>
   	<tr style="display:none">
	  	<td>Maximum Power Start(hh): </td><td><input type="text" id="bdFactor" name ="mppara.bdFactor" style="width: 160px;"/></td>
      	<td>Maximum Power End(hh): </td><td><input type="text"   id="vfactor" name ="mppara.vfactor" /></td>
      	<td colspan="2"></td>   		
   	</tr>
<!--费控档案end-->
  </table>
<!--隐藏内容-->
  <table style="display: none;">
	    <tr>
<!--	   		<td nowrap>PT变比:</td><td><input type="text"  name="mppara.ptRatio" id="ptRatio" value="1"/></td>-->
<!--			<td nowrap>CT变比:</td><td><input type="text"  name="mppara.ctRatio" id="ctRatio" value="1"/></td>-->
			<td nowrap>测点类型:</td><td><input type="text" name="mppara.mpType" id="mpType" value="0"/></td>
	    	<td nowrap>预付费标志:</td><td><input type="text"  name="meterpara.prepayflag" id="prepayflag" value="1"/></td>
	    	<td nowrap>使用标志:</td><td><input type="text"  name="meterpara.useFlag" id="meteruseflag" value="1"/></td>
	    	<td><input type="text"  name="mppara.useFlag" id="useflag" value="1"/></td>
			<td><input type="hidden" id="rtuId" name="meterpara.rtuId" /></td>
			<td><input type="hidden" id="rtuid_mp" name="mppara.rtuId" /></td>
			<td><input type="hidden" id="id_mpp" name="mppara.id" /></td>
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


<script type="text/javascript">

    var YDTable = {};
    var SDDef = {};
    var Dict = {};

    Dict.DICTITEM_YESFLAG = "<%=Dict.DICTITEM_YESFLAG%>";
    Dict.DICTITEM_USEFLAG = "<%=Dict.DICTITEM_USEFLAG%>";
    Dict.DICTITEM_YFFMONEY = "<%=Dict.DICTITEM_YFFMONEY%>";
    Dict.DICTITEM_YFFMETERTYPE = "<%=Dict.DICTITEM_YFFMETERTYPE%>";
    Dict.DICTITEM_RTUPOLLID	 = "<%=Dict.DICTITEM_RTUPOLLID%>";
	Dict.DICTITEM_PREPAYTYPE	 = "<%=Dict.DICTITEM_PREPAYTYPE%>";
	Dict.DICTITEM_FEETYPE 		 = "<%=Dict.DICTITEM_FEETYPE%>";
	Dict.DICTITEM_PAYTYPE	 	 = "<%=Dict.DICTITEM_PAYTYPE%>";
	Dict.DICTITEM_CB_CYCLE_TYPE  = "<%=Dict.DICTITEM_CB_CYCLE_TYPE%>";
	Dict.DICTITEM_MAINMETFLAG	 = "<%=Dict.DICTITEM_MAINMETFLAG%>";
	Dict.DICTITEM_RESIDENTUSERTYPE = "<%=Dict.DICTITEM_RESIDENTUSERTYPE%>";
	Dict.DICTITEM_FACTORY = "<%=Dict.DICTITEM_FACTORY%>";
	YDTable.TABLECLASS_YFFALARMPARA  = "<%=YDTable.TABLECLASS_YFFALARMPARA%>";
	YDTable.TABLECLASS_YFFRATEPARA   = "<%=YDTable.TABLECLASS_YFFRATEPARA%>";
	YDTable.TABLECLASS_MPPARA   = "<%=YDTable.TABLECLASS_MPPARA%>";
	SDDef.SPLITCOMA = "<%=SDDef.SPLITCOMA%>";
	
    </script>
  </body>
</html>
