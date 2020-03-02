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
  <script src="<%=basePath%>js/docs/yffparaDialogpl_dyjc.js"></script>
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
            <td style="border: 0px; font-size: 12px;"><label for="chk1"><i18n:message key="doc.fkcsda" /></label><input type="checkbox" id="chk1" style="width: 16px;"/></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td><input type="checkbox" id="feectrlType_" style="width: 16px;"/><label for="feectrlType_"><i18n:message key="doc.fkfs" />: </label></td><!-- 费控方式 -->
      <td><select name ="mppaypara.feectrlType" id="feectrlType"></select></td>
      <td><input type="checkbox" id="caclType_" style="width: 16px;"/><label for="caclType_"><i18n:message key="doc.jffs" />: </label></td><!-- 计费方式 -->
      <td><select name="mppaypara.caclType" id="caclType"></select></td>
      <td><input type="checkbox" id="feeprojId_" style="width: 16px;"/><label for="feeprojId_"><i18n:message key="doc.flfa" />: </label></td><!-- 费率方案 -->
      <td><select name="mppaypara.feeprojId" id="feeprojId" style=" width:160px">
      </select></td>
    </tr>
    <tr>
      <td id="payType_text"><input type="checkbox" id="payType_" style="width: 16px;"/><label for="payType_"><i18n:message key="doc.jiaofeifs" />: </label></td><!-- 缴费方式 -->
      <td><select name ="mppaypara.payType" id="payType">
      </select></td>
      <td><input type="checkbox" id="yffmeterType_" style="width: 16px;"/><label for="yffmeterType_"><i18n:message key="doc.yffblx" />: </label></td><!--预付费表类型-->
      <td><select name = "mppaypara.yffmeterType" id="yffmeterType">
      </select>
      </td>
      <td><input type="checkbox" id="yffalarmId_" style="width: 16px;"/><label for="yffalarmId_"><i18n:message key="doc.bjfa" />: </label></td><!-- 报警方案 -->
      <td><select name = "mppaypara.yffalarmId" id= "yffalarmId" style=" width:160px">
      </select></td>
    </tr>
    <tr>
      <td><input type="checkbox" id="keyVersion_" style="width: 16px;"/><label for="keyVersion_"><i18n:message key="doc.mybb" />: </label></td>
      <td><input type="text" id="keyVersion" name ="mppaypara.keyVersion"/></td>
      <td><input type="checkbox" id="cryplinkId_" style="width: 16px;"/><label for="cryplinkId_"><i18n:message key="doc.ssjmj" />: </label></td>
      <td><input type="text" name = "mppaypara.cryplinkId" id = "cryplinkId" /></td>
      <td><input type="checkbox" id="tzVal_" style="width: 16px;"/><label for="tzVal_"><i18n:message key="doc.tzje" />: </label></td>
      <td><select name = "mppaypara.tzVal" id= "tzVal" style=" width:160px"></select></td>
    </tr>
    <tr>
      <td><input type="checkbox" id="protSt_" style="width: 16px;"/><label for="protSt_"><i18n:message key="doc.bdks" />: </label></td>
      <td><input type="text" id="protSt" name ="mppaypara.protSt" /></td>
      <td><input type="checkbox" id="protEd_" style="width: 16px;"/><label for="protEd_"><i18n:message key="doc.bdjs" />: </label></td>
      <td><input type="text" name = "mppaypara.protEd" id = "protEd" /></td>
      <td><input type="checkbox" id="ngloprotFlag_" style="width: 16px;"/><label for="ngloprotFlag_"><i18n:message key="doc.bcyqjbd" />: </label></td>
      <td><select name="mppaypara.ngloprotFlag" id="ngloprotFlag" style=" width:160px" >
      </select>
      <input type="hidden" id="rtuId" name="mppaypara.rtuId" /><input type="hidden" id="mpId" name="mppaypara.mpId" /></td>
    </tr>
    
    <tr>
   	  <td><input type="checkbox" id="localMaincalcf_" style="width: 16px;"/><label for="localMaincalcf_">终端费控主站算费:</label></td>
   	  <td nowrap><select id="localMaincalcf" name ="mppaypara.localMaincalcf" ></select> </td>
   	  <td><input type="checkbox" id="feeBegindateF_" style="width: 16px;"/><label for="feeBegindateF_">费率启用日期:</label></td>
   	  <td><input type="text"     id="feeBegindateF" readonly  onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:'false',readOnly:'true',position:{button:'above'}});" />
		  <input type="hidden" name="mppaypara.feeBegindate" id="feeBegindate" />
	  </td>
   	  <td><input type="checkbox" id="jtCycleMdF_" style="width: 16px;"/><label for="jtCycleMdF_">阶梯周期切换时间:</label></td><td nowrap>
      	  <input type="text" id="jtCycleMdF" readonly onFocus="WdatePicker({dateFmt:'MM月dd日',isShowClear:'false',readOnly:'true',position:{button:'above'}});" style="width: 160px;"/>
	 	  <input type="hidden" name="mppaypara.jtCycleMd" id="jtCycleMd" />
	  </td>
    </tr>    
    
        <tr><td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>
	<tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;"><label for="chk3">发行电费档案</label><input type="checkbox" id="chk3"  style="width: 16px;"/></td>
          </tr>
        </table></td>
    </tr>
 	<tr>
      	<td><input type="checkbox" id="cbCycleType_" style="width: 16px;"/><label for="cbCycleType_">抄表周期:</label></td>
      	<td nowrap><select id=cbCycleType name ="mppaypara.cbCycleType"></select></td>
      	<td><input type="checkbox" id="cbDayhourF_" style="width: 16px;"/><label for="cbDayhourF_">抄表日:</label></td><td nowrap>
      		<input type="text" id="cbDayhourF" readonly onFocus="WdatePicker({dateFmt:'dd日HH时',isShowClear:'false',readOnly:'true',position:{button:'above'}});" />
	 		<input type="hidden" name="mppaypara.cbDayhour" id="cbDayhour" />
	 	</td>
<!--      	<td><input type="checkbox" id="CHKjsDay" style="width: 16px;"/><label for="CHKjsDay">结算日:</label></td><td>	<select name="mppaypara.jsDay" id="jsDay" ></select></td>-->
    	<td></td><td>	</td>
    </tr>
 
  	<tr>
      <td><input type="checkbox" id="fxdfFlag_" style="width: 16px;"/><label for="fxdfFlag_">是否发行电费:</label></td>
      <td nowrap><select name = "mppaypara.fxdfFlag" id = "fxdfFlag" ></select> </td>
      <td><input type="checkbox" id="fxdfBegindateF_" style="width: 16px;"/><label for="fxdfBegindateF_">发行电费起始日期:</label></td>
      <td><input type="text" id="fxdfBegindateF" readonly  onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:'false',readOnly:'true',position:{button:'above'}});" />
	  	  <input type="hidden" name="mppaypara.fxdfBegindate" id="fxdfBegindate"  />
	  </td>
	  <td></td><td></td>
    </tr>
    
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>
	<tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;"><label for="chk2"><i18n:message key="doc.flggda" /></label><input type="checkbox" id="chk2" style="width: 16px;"/></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td><input type="checkbox" id="feeChgf_" style="width: 16px;"/><label for="feeChgf_"><i18n:message key="doc.flggbz" />: </label></td>
      <td><select id = "feeChgf" name = "mppaypara.feeChgf"></select></td>  
      <td><input type="checkbox" id="feeChgdateF_" style="width: 16px;"/><label for="feeChgdateF_"><i18n:message key="doc.xflqyrq" />: </label></td>
	  <td>
	  	<input type="text" id="feeChgdateF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.ymd.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{top:'above'}});"/>
	  	<input type="hidden" name="mppaypara.feeChgdate" id="feeChgdate"/>
	  </td>
      <td><input type="checkbox" id="feeChgtimeF_" style="width: 16px;"/><label for="feeChgtimeF_"><i18n:message key="doc.xflqysj" />: </label></td>
	 	<td>
	 		<input type="text" id="feeChgtimeF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.hms.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{top:'above'}});" style=" width:160px"/>
	 		<input type="hidden" name="mppaypara.feeChgtime" id="feeChgtime" />
	 	</td>
    </tr> 
    <tr>      
      <td><input type="checkbox" id="feeChgid_" style="width: 16px;"/><label for="feeChgid_"><i18n:message key="doc.xflmc" />: </label></td>
      <td><select id = "feeChgid" name = "mppaypara.feeChgid" ></select></td>
      <td></td>
      <td></td>
      <td></td>
      <td></td>
    </tr>
  </table>
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
    var bdks = "<i18n:message key="doc.bdks" />";
    var bdjs = "<i18n:message key="doc.bdjs" />";
    var mybb = "<i18n:message key="doc.mybb" />";

    var YDTable = {};
    Dict.DICTITEM_USEFLAG        = "<%=Dict.DICTITEM_USEFLAG%>";
    Dict.DICTITEM_YESFLAG		 = "<%=Dict.DICTITEM_YESFLAG%>";
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
