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
	overflow : false;
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
  <script>
	var basePath = '<%=basePath%>';
  </script>
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/docs/yffparaDialog_spec.js"></script>
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
  	<td style="font-size: 12px; padding-left: 20px; color: blue;">
  	客户名称:<input type="text" id="huming" style="border: none; color:blue;" readonly />&nbsp;&nbsp;
  	客户编号:<input type="text" id="huhao" style="border: none; color:blue;" readonly />&nbsp;&nbsp;
  	变压器容量(kVA):<input type="text" id="cont_cap" style="border: none; color:blue;" readonly />
  	</td>
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
            <td style="border: 0px; font-size: 12px;"><i18n:message key="doc.yffcs" /></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td><font color=red>*</font>计费方式:</td>
      <td><select name="zjgpaypara.caclType" id="caclType" style="width:160px;">
        </select></td>
       <td><i18n:message key="doc.fkfs" />: </td>
      <td><select name ="zjgpaypara.feectrlType" id="feectrlType" style="width:140px;">
        </select></td> 
      <td id="payType_text">缴费方式: </td>
      <td><select name ="zjgpaypara.payType" id="payType" style="width:140px;">
        </select></td> 
    </tr>
    <tr>
      <td><i18n:message key="doc.bjfa" />: </td>
      <td><select name = "zjgpaypara.yffalarmId" id= "yffalarmId" style="width:160px;">
        </select></td>
      <td><i18n:message key="doc.kzfs" />: </td>
      <td><select name="zjgpaypara.yffctrlType" id="yffctrlType" style="width:140px;">
        </select></td>
      <td><i18n:message key="doc.tzje" />: </td>
      <td><select name="zjgpaypara.tzVal" id="tzVal" style="width:140px;">
        </select></td>
      
    </tr>
    <tr>
      <td><%=I18N.getText("doc.cldfl",1) %>: </td>
      <td><select name="zjgpaypara.feeprojId" id="feeprojId" style="width:160px;">
        </select></td>
      <td><%=I18N.getText("doc.cldfl",2) %>: </td>
      <td><select name="zjgpaypara.feeproj1Id" id="feeproj1Id" style="width:140px;">
        </select></td>
      <td><%=I18N.getText("doc.cldfl",3) %>: </td>
      <td><select name="zjgpaypara.feeproj2Id" id="feeproj2Id" style="width:140px;">
        </select></td>
    </tr>
    <tr>
      <td><i18n:message key="doc.zdbdks" />: </td>
      <td><input type="text" id="protSt" name ="zjgpaypara.protSt" style="width:160px;"/></td>
      <td><i18n:message key="doc.zdbdjs" />: </td>
      <td><input type="text" name = "zjgpaypara.protEd" id = "protEd" style="width:140px;" /></td>
      <td><i18n:message key="doc.bcyqjbd" />: </td>
      <td><select name="zjgpaypara.ngloprotFlag" id="ngloprotFlag" style="width:140px;">
        </select></td>
      </tr>
      
      <!-- 新增字段 -->
    <tr>
      <td>密钥版本:</td>
      <td><input type="text" id="keyVersion" name="zjgpaypara.keyVersion" style="width:160px;"/></td>
      <td>所属加密机:</td>
      <td><input type="text" name="zjgpaypara.cryplinkId" id="cryplinkId" style="width:140px;" /></td>
      <td>写卡户号:</td>
      <td><input type="text" name="zjgpaypara.writecardNo" id="writecardNo" style="width:140px;" /></td>
    </tr>
    <tr>
      <td>主站算费:</td>
      <td><select id="localMaincalcf" name ="zjgpaypara.localMaincalcf" style="width:160px;"></select> </td>
    	<td>费率启用日期:</td><td>	<input type="text" id="feeBegindateF" readonly onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:'false',readOnly:'true',position:{button:'above'}});" />
	  	<input type="hidden" name="zjgpaypara.feeBegindate" id="feeBegindate" />
	  	</td><td>卡表类型:</td><td><select name="zjgpaypara.cardmeterType" id="cardmeterType" style="width:140px;" /></select></td>
    </tr>
    
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">发行电费档案</td>
          </tr>
        </table></td>
    </tr> 
    <tr>
      <td>抄表周期:</td>
      <td><select id=cbCycleType name="zjgpaypara.cbCycleType" style="width:160px;"></select></td>
      <td>抄表日:</td><td>
      		<input type="text" id="cbDayhourF" readonly onFocus="WdatePicker({dateFmt:'dd日HH时',isShowClear:'false',readOnly:'true',position:{button:'above'}});"/>
	 		<input type="hidden" name="zjgpaypara.cbDayhour" id="cbDayhour" />
	 	</td>
	  <td>抄表结算标志:</td>
      <td><select id=cbjsFlag name="zjgpaypara.cbjsFlag" style="width:140px;"></select></td>
    </tr>
  <tr>
      <td>是否发行电费:</td>
      <td><select name = "zjgpaypara.fxdfFlag" id = "fxdfFlag" style="width:160px;" ></select> </td>
      <td>发行电费起始日期:</td><td>	<input type="text" id="fxdfBegindateF" readonly onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:'false',readOnly:'true',position:{button:'above'}});" />
	  	<input type="hidden" name="zjgpaypara.fxdfBegindate" id="fxdfBegindate" />
	  </td><td></td><td></td>
    </tr> 
   
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;"><i18n:message key="doc.jbsz" /></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td><i18n:message key="doc.gfhk" />: </td>
      <td><select id="useGfh" name="zjgpaypara.useGfh" style="width:160px;">
        </select></td>
      <td><i18n:message key="doc.gfhsj" />: </td>
      <td><input type = "text" name = "zjgpaypara.hfhTime" id= "hfhTime" style="width:140px;"/></td>
      <td><i18n:message key="doc.gfhddz" />: </td>
      <td><input type="text" name="zjgpaypara.hfhShutdown" id="hfhShutdown" /></td>
    </tr>
    <tr>
      <td><i18n:message key="doc.fhmcbh" />: </td>
      <td><input type = "text" name = "zjgpaypara.plusTime" id= "plusTime" style="width:160px;"/></td>
      <td></td><td></td>
 	  <td></td><td></td>
    </tr>
    <tr>
      <td><i18n:message key="doc.jbf" />: </td>
      <td><input type = "text" name = "zjgpaypara.payAdd1" id= "payAdd1" style="width:160px;"/></td>
      <td><%=I18N.getText("doc.fjz",2)%>: </td>
      <td><input type = "text" name = "zjgpaypara.payAdd2" id= "payAdd2" style="width:140px;" /></td>
      <td><%=I18N.getText("doc.fjz",3)%>: </td>
      <td><input type = "text" name = "zjgpaypara.payAdd3" id= "payAdd3" style="width:140px;"/>
      <input type="hidden" id="rtuId" name="zjgpaypara.rtuId" />
      <input type="hidden" id="zjgId" name="zjgpaypara.zjgId" /></td>
    </tr>
    
    <!-- 增加力调 -->
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">力调</td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td>力调算费标志: </td>
      <td><select id="powrateFlag" name="zjgpaypara.powrateFlag" style="width:160px;"></select></td>
      <td>奖罚标志: </td>
      <td><select id="prizeFlag" name="zjgpaypara.prizeFlag" style="width:140px;"></select></td>
      <td>力调方案: </td>
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
            <td style="border: 0px; font-size: 12px;"><i18n:message key="doc.flggda" /></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td><i18n:message key="doc.flggbz" />: </td>
      <td><select id="feeChgf" name="zjgpaypara.feeChgf" style="width:160px;">
        </select></td>
      <td><i18n:message key="doc.xflqyrq" />: </td>
	  <td>
	  	<input type="text" id="feeChgdateF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.ymd.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});" />
	  	<input type="hidden" name="zjgpaypara.feeChgdate" id="feeChgdate" />
	  </td>
      <td><i18n:message key="doc.xflqysj" />: </td>
	 	<td>
	 		<input type="text" id="feeChgtimeF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.hms.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});"/>
	 		<input type="hidden" name="zjgpaypara.feeChgtime" id="feeChgtime" />
	 	</td>
    </tr>
    <tr>
    	<td><%=I18N.getText("doc.cldxfl",1)%>: </td>
      <td><select id="feeChgid" name="zjgpaypara.feeChgid" style="width:160px;">
        </select></td>
      <td><%=I18N.getText("doc.cldxfl",2)%>: </td>
      <td><select id="feeChgid1" name="zjgpaypara.feeChgid1" style="width:140px;">
        </select></td>
      <td><%=I18N.getText("doc.cldxfl",3)%>: </td>
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
            <td style="border: 0px; font-size: 12px;"><i18n:message key="doc.jbfggda" /></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td><i18n:message key="doc.jbfggbz" />: </td>
      <td><select id="jbfChgf" name="zjgpaypara.jbfChgf" style="width:160px;">
        </select></td>
	 <td><i18n:message key="doc.xjbfqyrq" />: </td>
	  <td>
	  	<input type="text" id="jbfChgdateF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.ymd.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{top:'above'}});" />
	  	<input type="hidden" name="zjgpaypara.jbfChgdate" id="jbfChgdate" />
	  </td>
      <td><i18n:message key="doc.xflqysj" />: </td>
	 	<td>
	 		<input type="text" id="jbfChgtimeF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.hms.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{top:'above'}});" />
	 		<input type="hidden" name="zjgpaypara.jbfChgtime" id="jbfChgtime" />
	 	</td>
    </tr>
    <tr>
      <td><i18n:message key="doc.xjbf" />: </td>
      <td><input type = "text" name = "zjgpaypara.jbfChgval" id= "jbfChgval" style="width:160px;"/></td>
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
            <td style="border: 0px; font-size: 12px;">报停</td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td>报停标志: </td>
      <td><select id="stopFlag" name="zjgpaypara.stopFlag" style="width:160px;"></select></td>
      <td>报停开始日期: <input type="hidden" id="stopBegdate" name="zjgpaypara.stopBegdate" /></td>
      <td><input type="text" id="stopBegdateF" readonly onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:'false',readOnly:'true',position:{button:'above'}});" /></td>
      <td>报停结束日期: <input type="hidden" id="stopEnddate" name="zjgpaypara.stopEnddate" /></td>
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
<jsp:include page="../../jsp/inc/jsdef.jsp"></jsp:include>
<script type="text/javascript">
	var update_fail = "<i18n:message key="comm.gridopt.update_fail" />";
	var fjz 		= "<%=I18N.getText("doc.fjz","")%>";
	var gfhddz 		= "<i18n:message key="doc.gfhddz" />";
	var zdbdks 		= "<i18n:message key="doc.zdbdks" />";
	var zdbdjs 		= "<i18n:message key="doc.zdbdjs" />";
	var gfhsj 		= "<i18n:message key="doc.gfhsj" />";
	var fhmcbh 		= "<i18n:message key="doc.fhmcbh" />";
	
	
    var YDTable = {};
    SDDef.BASEPATH 					= "<%=basePath%>";
    SDDef.SPLITCOMA 				= "<%=SDDef.SPLITCOMA%>";
    Dict.DICTITEM_YESFLAG 			= "<%=Dict.DICTITEM_YESFLAG%>";
    Dict.DICTITEM_USEFLAG 			= "<%=Dict.DICTITEM_USEFLAG%>";
    Dict.DICTITEM_WIRINGMODE 		= "<%=Dict.DICTITEM_WIRINGMODE%>";
    Dict.DICTITEM_RTUPOLLID	 		= "<%=Dict.DICTITEM_RTUPOLLID%>";
	Dict.DICTITEM_PREPAYTYPE	 	= "<%=Dict.DICTITEM_PREPAYTYPE%>";
	Dict.DICTITEM_YFFSPECCTRLTYPE	= "<%=Dict.DICTITEM_YFFSPECCTRLTYPE%>";
	Dict.DICTITEM_FEETYPE 		 	= "<%=Dict.DICTITEM_FEETYPE%>";
	Dict.DICTITEM_CSSTAND	 	 	= "<%=Dict.DICTITEM_CSSTAND%>";
	Dict.DICTITEM_PAYTYPE	 	 	= "<%=Dict.DICTITEM_PAYTYPE%>";
	Dict.DICTITEM_YFFMONEY	 	 	= "<%=Dict.DICTITEM_YFFMONEY%>";
	
	Dict.DICTITEM_EXECFLAG	 	 	= "<%=Dict.DICTITEM_EXECFLAG%>";
	Dict.DICTITEM_PRIZEFLAG	 	 	= "<%=Dict.DICTITEM_PRIZEFLAG%>";
	Dict.DICTITEM_STOPFLAG	 	 	= "<%=Dict.DICTITEM_STOPFLAG%>";
	Dict.DICTITEM_CARDMETER_TYPE 	= "<%=Dict.DICTITEM_CARDMETER_TYPE%>";
	//增加抄表周期类型
	Dict.DICTITEM_CB_CYCLE_TYPE  	= "<%=Dict.DICTITEM_CB_CYCLE_TYPE%>";
	YDTable.TABLECLASS_YFFALARMPARA = "<%=YDTable.TABLECLASS_YFFALARMPARA%>";
	YDTable.TABLECLASS_YFFRATEPARA  = "<%=YDTable.TABLECLASS_YFFRATEPARA%>";
    </script>
  </body>
</html>
