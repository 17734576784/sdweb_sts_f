<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.Dict"%>
<%@page import="com.kesd.common.YDTable"%>
<%@page import="com.kesd.util.I18N"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><i18n:message key="doc.zjzfkcs" /></title>
    <style type="text/css">
body {
	margin:0 0 0 0;
	overflow : auto;
	background: #eaeeef;
}
.tabsty {
	width:95%;
}
.tabsty input, .tabsty select {
	width: 140px;
}
input, select {
	font-size: 12px;
}
.d_cl{
	border-top: 3px solid #40a6a4;
}
</style>
<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/docs/yffparaDialogpl_spec.js"></script>
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
  <table style="font-size: 12px; width:95%" cellpadding="0" cellspacing="0" align="center">
  <tr>
  <td>
  	<table cellpadding="0" cellspacing="0"><tr>
  	<td class="titlel" id="jbcs_l"></td>
  	<td class="titlem" style="width: 70px;" id="jbcs">基本参数</td>
  	<td class="titler" id="jbcs_r"></td>
  	<td width="5"></td>
  	<td class="titlel1" id="qtcs_l"></td>
  	<td class="titlem1" style="width: 70px;" id="qtcs">其他参数</td>
  	<td class="titler1" id="qtcs_r"></td>
  	</tr></table>
  </td>
  </tr>
  </table>
  <div id="d_jbcs" class="d_cl">
  <table class="tabsty" align="center">
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;"><label for="ck1"><i18n:message key="doc.yffcs" /></label><input type="checkbox" id="ck1" style="width:20px;" /></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td><input type="checkbox" id="caclType_" style="width:20px;" /><label for="caclType_">计费方式: </label></td>
      <td><select name="zjgpaypara.caclType" id="caclType" style="width:160px;">
        </select></td>
       <td><input type="checkbox" id="feectrlType_" style="width:20px;" /><label for="feectrlType_"><i18n:message key="doc.fkfs" />: </label></td>
      <td><select name ="zjgpaypara.feectrlType" id="feectrlType" style="width:140px;">
        </select></td> 
      <td id="payType_text"><input type="checkbox" id="payType_" style="width:20px;" /><label for="payType_">缴费方式: </label></td>
      <td><select name ="zjgpaypara.payType" id="payType" style="width:140px;">
        </select></td> 
    </tr>
    <tr>
      <td><input type="checkbox" id="yffalarmId_" style="width:20px;" /><label for="yffalarmId_"><i18n:message key="doc.bjfa" />: </label></td>
      <td><select name = "zjgpaypara.yffalarmId" id= "yffalarmId" style="width:160px;">
        </select></td>
      <td><input type="checkbox" id="yffctrlType_" style="width:20px;" /><label for="yffctrlType_"><i18n:message key="doc.kzfs" />: </label></td>
      <td><select name="zjgpaypara.yffctrlType" id="yffctrlType" style="width:140px;">
        </select></td>
      <td><input type="checkbox" id="tzVal_" style="width:20px;" /><label for="tzVal_"><i18n:message key="doc.tzje" />: </label></td>
      <td><select name="zjgpaypara.tzVal" id="tzVal" style="width:140px;">
        </select></td>
      
    </tr>
    <tr>
      <td><input type="checkbox" id="feeprojId_" style="width:20px;" /><label for="feeprojId_"><%=I18N.getText("doc.cldfl",1) %>: </label></td>
      <td><select name="zjgpaypara.feeprojId" id="feeprojId" style="width:160px;">
        </select></td>
      <td><input type="checkbox" id="feeproj1Id_" style="width:20px;" /><label for="feeproj1Id_"><%=I18N.getText("doc.cldfl",2) %>: </label></td>
      <td><select name="zjgpaypara.feeproj1Id" id="feeproj1Id" style="width:140px;">
        </select></td>
      <td><input type="checkbox" id="feeproj2Id_" style="width:20px;" /><label for="feeproj2Id_"><%=I18N.getText("doc.cldfl",3) %>: </label></td>
      <td><select name="zjgpaypara.feeproj2Id" id="feeproj2Id" style="width:140px;">
        </select></td>
    </tr>
    <tr>
      <td><input type="checkbox" id="protSt_" style="width:20px;" /><label for="protSt_"><i18n:message key="doc.zdbdks" />: </label></td>
      <td><input type="text" id="protSt" name ="zjgpaypara.protSt" style="width:160px;"/></td>
      <td><input type="checkbox" id="protEd_" style="width:20px;" /><label for="protEd_"><i18n:message key="doc.zdbdjs" />: </label></td>
      <td><input type="text" name="zjgpaypara.protEd" id = "protEd" style="width:140px;" /></td>
      <td><input type="checkbox" id="ngloprotFlag_" style="width:20px;" /><label for="ngloprotFlag_"><i18n:message key="doc.bcyqjbd" />: </label></td>
      <td><select name="zjgpaypara.ngloprotFlag" id="ngloprotFlag" style="width:140px;">
        </select></td>
    </tr>
    <!-- 新增 -->
    <tr>
      <td><input type="checkbox" id="keyVersion_" style="width:20px;" /><label for="keyVersion_">密钥版本:</label></td>
      <td><input type="text" id="keyVersion" name ="zjgpaypara.keyVersion" style="width:160px;"/></td>
      <td><input type="checkbox" id="cryplinkId_" style="width:20px;" /><label for="cryplinkId_">所属加密机:</label></td>      
      <td><input type="text" name="zjgpaypara.cryplinkId" id = "cryplinkId" style="width:140px;" /></td>
      <td><input type="checkbox" id="cardmeterType_" style="width:20px;" /><label for="cardmeterType_">卡表类型:</label></td>
      <td><select name="zjgpaypara.cardmeterType" id="cardmeterType" style="width:140px;" /></select></td>
    </tr>
    <tr>
      <td><input type="checkbox" id="localMaincalcf_" style="width:20px;" /><label for="localMaincalcf_">主站算费:</label></td>
      <td><select id="localMaincalcf" name ="zjgpaypara.localMaincalcf" style="width:160px;"></select> </td>
     
      <td><input type="checkbox" id="feeBegindateF_" style="width:20px;" /><label for="feeBegindateF_">费率启用日期:</label></td>      
   	  <td><input type="text" id="feeBegindateF" readonly style="width:140px;"  onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:'false',readOnly:'true',position:{button:'above'}});" />
	  			 <input type="hidden" name="zjgpaypara.feeBegindate" id="feeBegindate" />
	  </td><td></td><td></td>
    </tr>
      
      <!-- 新增发行电表档案  start-->
      
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>
	<tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;"><label for="ck5">发行电费档案</label><input type="checkbox" id="ck5" style="width: 16px;"/></td>
          </tr>
        </table></td>
    </tr>
  	<tr>
       	<td><input type="checkbox" id="cbCycleType_" style="width:20px;" /><label for="cbCycleType_">抄表周期:</label></td>      
      	<td><select id=cbCycleType name ="zjgpaypara.cbCycleType" style="width:160px;"></select></td>
      	<td><input type="checkbox" id="cbDayhourF_" style="width:20px;" /><label for="cbDayhourF_">抄表日:</label></td>      
	    <td><input type="text" id="cbDayhourF" readonly  style="width:140px;" onFocus="WdatePicker({dateFmt:'dd日HH时',isShowClear:'false',readOnly:'true',position:{button:'above'}});"/>
	 			   <input type="hidden" name="zjgpaypara.cbDayhour" id="cbDayhour" />
	  	</td>
    	<td><input type="checkbox" id="cbjsFlag_" style="width:20px;" /><label for="cbjsFlag_">抄表结算标志:</label></td>
        <td><select id=cbjsFlag name="zjgpaypara.cbjsFlag" style="width:140px;"></select></td>
    </tr>
  	<tr>
      <td><input type="checkbox" id="fxdfFlag_" style="width:20px;" /><label for="fxdfFlag_">是否发行电费:</label></td>
      <td><select name = "zjgpaypara.fxdfFlag" id = "fxdfFlag"  style="width:160px;" ></select> </td>
      <td><input type="checkbox" id="fxdfBegindateF_" style="width:20px;" /><label for="fxdfBegindateF_">发行电费起始日期:</label></td>
      <td><input type="text" id="fxdfBegindateF" readonly  style="width:140px;" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:'false',readOnly:'true',position:{button:'above'}});" />
	  	         <input type="hidden" name="zjgpaypara.fxdfBegindate" id="fxdfBegindate" />
	  </td><td></td><td></td>
    </tr>
      
    <!-- 新增发行电表档案  end-->
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;"><label for="ck2"><i18n:message key="doc.jbsz" /></label><input type="checkbox" id="ck2" style="width:20px;" /></td>
          </tr>
        </table></td>
    </tr>
    
    <tr>
      <td><input type="checkbox" id="payAdd1_" style="width:20px;" /><label for="payAdd1_"><i18n:message key="doc.jbf" />: </label></td>
      <td><input type = "text" name = "zjgpaypara.payAdd1" id= "payAdd1" style="width:160px;"/></td>
      <td><input type="checkbox" id="payAdd2_" style="width:20px;" /><label for="payAdd2_"><%=I18N.getText("doc.fjz",2)%>: </label></td>
      <td><input type = "text" name = "zjgpaypara.payAdd2" id= "payAdd2" style="width:140px;" /></td>
      <td><input type="checkbox" id="payAdd3_" style="width:20px;" /><label for="payAdd3_"><%=I18N.getText("doc.fjz",3)%>: </label></td>
      <td><input type = "text" name = "zjgpaypara.payAdd3" id= "payAdd3" style="width:140px;"/>
      <input type="hidden" id="rtuId" name="zjgpaypara.rtuId" />
      <input type="hidden" id="zjgId" name="zjgpaypara.zjgId" /></td>
    </tr>
    
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;">
      <table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;"><label for="ck6">力调</label><input type="checkbox" id="ck6" style="width:20px;" /></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td><input type="checkbox" id="powrateFlag_" style="width:20px;" /><label for="powrateFlag_">力调算费标志:</label> </td>
      <td><select id="powrateFlag" name="zjgpaypara.powrateFlag" style="width:160px;"></select></td>
      <td><input type="checkbox" id="prizeFlag_" style="width:20px;" /><label for="prizeFlag_">奖罚标志: </label></td>
      <td><select id="prizeFlag" name="zjgpaypara.prizeFlag" style="width:140px;"></select></td>
      <td><input type="checkbox" id="csStand_" style="width:20px;" /><label for="csStand_">力调方案: </label></td>
      <td><select name= "zjgpaypara.csStand" id="csStand" style="width:140px;"></select></td>
    </tr>
  </table>
  </div>
  <div id="d_qtcs" style="display: none;"  class="d_cl">
  <table class="tabsty" align="center">
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;"><label for="ck3"><i18n:message key="doc.flggda" /></label><input type="checkbox" id="ck3" style="width:20px;" /></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td><input type="checkbox" id="feeChgf_" style="width:20px;" /><label for="feeChgf_"><i18n:message key="doc.flggbz" />: </label></td>
      <td><select id="feeChgf" name="zjgpaypara.feeChgf" style="width:160px;">
        </select></td>
      <td><input type="checkbox" id="feeChgdateF_" style="width:20px;" /><label for="feeChgdateF_"><i18n:message key="doc.xflqyrq" />: </label></td>
	  <td>
	  	<input type="text" id="feeChgdateF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.ymd.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});" />
	  	<input type="hidden" name="zjgpaypara.feeChgdate" id="feeChgdate" />
	  </td>
      <td><input type="checkbox" id="feeChgtimeF_" style="width:20px;" /><label for="feeChgtimeF_"><i18n:message key="doc.xflqysj" />: </label></td>
	 	<td>
	 		<input type="text" id="feeChgtimeF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.hms.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});"/>
	 		<input type="hidden" name="zjgpaypara.feeChgtime" id="feeChgtime" />
	 	</td>
    </tr>
    <tr>
    	<td><input type="checkbox" id="feeChgid_" style="width:20px;" /><label for="feeChgid_"><%=I18N.getText("doc.cldxfl",1)%>: </label></td>
      <td><select id="feeChgid" name="zjgpaypara.feeChgid" style="width:160px;">
        </select></td>
      <td><input type="checkbox" id="feeChgid1_" style="width:20px;" /><label for="feeChgid1_"><%=I18N.getText("doc.cldxfl",2)%>: </label></td>
      <td><select id="feeChgid1" name="zjgpaypara.feeChgid1" style="width:140px;">
        </select></td>
      <td><input type="checkbox" id="feeChgid2_" style="width:20px;" /><label for="feeChgid2_"><%=I18N.getText("doc.cldxfl",3)%>: </label></td>
      <td><select id="feeChgid2" name="zjgpaypara.feeChgid2" style="width:140px;">
        </select></td>
      
    </tr>    
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;"><label for="ck4"><i18n:message key="doc.jbfggda" /></label><input type="checkbox" id="ck4" style="width:20px;" /></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td><input type="checkbox" id="jbfChgf_" style="width:20px;" /><label for="jbfChgf_"><i18n:message key="doc.jbfggbz" />: </label></td>
      <td><select id="jbfChgf" name="zjgpaypara.jbfChgf" style="width:160px;">
        </select></td>
	 <td><input type="checkbox" id="jbfChgdateF_" style="width:20px;" /><label for="jbfChgdateF_"><i18n:message key="doc.xjbfqyrq" />: </label></td>
	  <td>
	  	<input type="text" id="jbfChgdateF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.ymd.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});" />
	  	<input type="hidden" name="zjgpaypara.jbfChgdate" id="jbfChgdate" />
	  </td>
      <td><input type="checkbox" id="jbfChgtimeF_" style="width:20px;" /><label for="jbfChgtimeF_"><i18n:message key="doc.xflqysj" />: </label></td>
	 	<td>
	 		<input type="text" id="jbfChgtimeF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.hms.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});" />
	 		<input type="hidden" name="zjgpaypara.jbfChgtime" id="jbfChgtime" />
	 	</td>
    </tr>
    <tr>
      <td><input type="checkbox" id="jbfChgval_" style="width:20px;" /><label for="jbfChgval_"><i18n:message key="doc.xjbf" />: </label></td>
      <td><input type="text" name="zjgpaypara.jbfChgval" id= "jbfChgval" style="width:160px;"/></td>
      <td></td>
      <td></td>
      <td></td>
	  <td></td>
    </tr>
    
     <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;"><label for="ck7">报停</label><input type="checkbox" id="ck7" style="width:20px;" /></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td><input type="checkbox" id="stopFlag_" style="width:20px;" /><label for="stopFlag_">报停标志: </label></td>
      <td><select id="stopFlag" name="zjgpaypara.stopFlag" style="width:160px;"></select></td>
      <td><input type="checkbox" id="stopBegdateF_" style="width:20px;" /><label for="stopBegdateF_">报停开始日期: </label><input type="hidden" id="stopBegdate" name="zjgpaypara.stopBegdate" /></td>
      <td><input type="text" id="stopBegdateF" readonly onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:'false',readOnly:'true',position:{button:'above'}});" /></td>
      <td><input type="checkbox" id="stopEnddateF_" style="width:20px;" /><label for="stopEnddateF_">报停结束日期: </label><input type="hidden" id="stopEnddate" name="zjgpaypara.stopEnddate" /></td>
      <td><input type="text" id="stopEnddateF" readonly onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:'false',readOnly:'true',position:{button:'above'}});" /></td>
    </tr>
  </table>
  </div>
  <center>
    <jsp:include page="../inc/btn_xiugai.jsp"></jsp:include>
    &nbsp;&nbsp;
    <jsp:include page="../inc/btn_guanbi.jsp"></jsp:include>
  </center>
</form>
<jsp:include page="../inc/jsdef.jsp"></jsp:include>
<script type="text/javascript">

    var update_fail = "<i18n:message key="comm.gridopt.update_fail" />";
	var fjz = "<%=I18N.getText("doc.fjz","")%>";
	var gfhddz = "<i18n:message key="doc.gfhddz" />";
	var zdbdks = "<i18n:message key="doc.zdbdks" />";
	var zdbdjs = "<i18n:message key="doc.zdbdjs" />";
	var gfhsj = "<i18n:message key="doc.gfhsj" />";
	var fhmcbh = "<i18n:message key="doc.fhmcbh" />";
	var sel_one = "<i18n:message key="doc.sel_one" />";
	
    var YDTable = {};
    SDDef.BASEPATH = "<%=basePath%>";
    SDDef.SPLITCOMA = "<%=SDDef.SPLITCOMA%>";
    Dict.DICTITEM_YESFLAG = "<%=Dict.DICTITEM_YESFLAG%>";
    Dict.DICTITEM_USEFLAG = "<%=Dict.DICTITEM_USEFLAG%>";
    Dict.DICTITEM_WIRINGMODE = "<%=Dict.DICTITEM_WIRINGMODE%>";
    Dict.DICTITEM_RTUPOLLID	 = "<%=Dict.DICTITEM_RTUPOLLID%>";
	Dict.DICTITEM_PREPAYTYPE	 = "<%=Dict.DICTITEM_PREPAYTYPE%>";
	Dict.DICTITEM_YFFSPECCTRLTYPE= "<%=Dict.DICTITEM_YFFSPECCTRLTYPE%>";
	Dict.DICTITEM_FEETYPE 		 = "<%=Dict.DICTITEM_FEETYPE%>";
	Dict.DICTITEM_CSSTAND	 	 = "<%=Dict.DICTITEM_CSSTAND%>";
	Dict.DICTITEM_PAYTYPE	 	 = "<%=Dict.DICTITEM_PAYTYPE%>";
	Dict.DICTITEM_YFFMONEY	 	 = "<%=Dict.DICTITEM_YFFMONEY%>";
	
	Dict.DICTITEM_EXECFLAG	 	 = "<%=Dict.DICTITEM_EXECFLAG%>";
	Dict.DICTITEM_PRIZEFLAG	 	 = "<%=Dict.DICTITEM_PRIZEFLAG%>";
	Dict.DICTITEM_STOPFLAG	 	 = "<%=Dict.DICTITEM_STOPFLAG%>";
	Dict.DICTITEM_CARDMETER_TYPE = "<%=Dict.DICTITEM_CARDMETER_TYPE%>";
	
	Dict.DICTITEM_CB_CYCLE_TYPE  = "<%=Dict.DICTITEM_CB_CYCLE_TYPE%>";
	
	YDTable.TABLECLASS_YFFALARMPARA  = "<%=YDTable.TABLECLASS_YFFALARMPARA%>";
	YDTable.TABLECLASS_YFFRATEPARA   = "<%=YDTable.TABLECLASS_YFFRATEPARA%>";
	
    </script>
  </body>
</html>
