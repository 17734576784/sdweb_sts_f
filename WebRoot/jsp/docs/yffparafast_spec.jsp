<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
    <base href="<%=basePath%>">
    
    <title>专变快速建档页面</title>
    <style type="text/css">
	    body {
			margin:0 0 0 10;
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
		.changeItem{
			color: blue;
		}
    </style>
  	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxtree.css">
  	<script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script src="<%=basePath%>js/common/def.js"></script>
	<!--<script src="<%=basePath%>js/common/dateFormat.js"></script>
	-->
	<script src="<%=basePath%>js/common/gridopt.js"></script>
	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
  	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
  	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
  	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
	<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=basePath%>js/validate.js"></script>
	<script src="<%=basePath%>js/docs/yffparafast_spec.js"></script>
	<script src="<%=basePath%>js/docs/docper.js"></script>
	<script src="<%=basePath%>js/dhtmlx/tree/dhtmlxtree.js"></script>
	<script src="<%=basePath%>js/common/loading.js"></script>
	<script src="<%=basePath%>js/common/dateFormat.js"></script>
  </head>
  
  <body>  		
	<table cellpadding="0" cellspacing="0" height="24" width="100%" class="page_tbl_bg22">
		<tr>
			<td width="1%"></td>
			<td align="left" width="45%">
				选择模板:
				<select name="model_list" id="model_list" style="width:160px">
				</select>&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="chooseModel" type="button" value="应用"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="save" type="button" value="保存"/>
			</td>
			<td width=50%></td>
		</tr>
	</table>
 	<div style="overflow : auto; width : 100%" id="div_scroll" > 
  	<form action="" method="post" id="addorupdate"  style='display:inline;'>
	<div id="d_jbcs" class="d_cl">
  	<table class="tabsty" align="center">
	  	<tr>
	      	<td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;">
	      	<table cellpadding="0" cellspacing="0">
	          	<tr>
	            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
	            <td style="border: 0px; font-size: 12px;">基本档案</td>
	          	</tr>
	        </table>
	        </td>
	    </tr>
	  	 <tr>
	      <td>客户名称:</td>
	      <td><input type="text" name="cusName" id="cusName" class="readonly_bg" readonly/></td>
	       <td>客户编号:</td>
	      <td><input type="text" name="cusNo" id="cusNo" class="readonly_bg" readonly/></td> 
	      <td>变压器容量(kVA):</td>
	      <td>
	      	<input type="text" id="cont_cap" class="readonly_bg" readonly/>
	      	<input type="hidden" id="zjg_rtuId" name="zjgpaypara.rtuId" />
      		<input type="hidden" id="zjgId" name="zjgpaypara.zjgId" />
	      </td> 
	    </tr>
  	 	<tr>
     	 <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px; height:11px"></td>
    	</tr>
  	
    	<tr>
      	<td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;">
      	<table cellpadding="0" cellspacing="0">
          	<tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;"><i18n:message key="doc.yffcs" /></td>
          	</tr>
        </table>
        </td>
    </tr>
    <tr>
      <td><font color=red>*</font>计费方式:</td>
      <td><select name="zjgpaypara.caclType" id="caclType">
        </select></td>
       <td><i18n:message key="doc.fkfs" />: </td>
      <td><select name ="zjgpaypara.feectrlType" id="feectrlType">
        </select></td> 
      <td id="payType_text">缴费方式:</td>
      <td><select name ="zjgpaypara.payType" id="payType">
        </select></td> 
    </tr>
    <tr>
      <td><i18n:message key="doc.bjfa" />: </td>
      <td><select name = "zjgpaypara.yffalarmId" id= "yffalarmId">
        </select></td>
      <td><i18n:message key="doc.kzfs" />:</td>
      <td><select name="zjgpaypara.yffctrlType" id="yffctrlType">
        </select></td>
      <td><i18n:message key="doc.tzje" />:</td>
      <td><select name="zjgpaypara.tzVal" id="tzVal">
        </select></td>
      
    </tr>
    <tr>
      <td><%=I18N.getText("doc.cldfl",1) %>:</td>
      <td><select name="zjgpaypara.feeprojId" id="feeprojId">
        </select></td>
      <td><%=I18N.getText("doc.cldfl",2) %>:</td>
      <td><select name="zjgpaypara.feeproj1Id" id="feeproj1Id">
        </select></td>
      <td><%=I18N.getText("doc.cldfl",3) %>:</td>
      <td><select name="zjgpaypara.feeproj2Id" id="feeproj2Id">
        </select></td>
    </tr>
    <tr>
      <td class="changeItem"><i18n:message key="doc.zdbdks"/>:</td>
      <td><input type="text" id="protSt" name ="zjgpaypara.protSt"/></td>
      <td class="changeItem"><i18n:message key="doc.zdbdjs" />:</td>
      <td><input type="text" name = "zjgpaypara.protEd" id = "protEd"/></td>
      <td><i18n:message key="doc.bcyqjbd" />:</td>
      <td><select name="zjgpaypara.ngloprotFlag" id="ngloprotFlag">
        </select></td>
      </tr>
      
      <!-- 新增字段 -->
    <tr>
      <td>密钥版本:</td>
      <td><input type="text" id="keyVersion" name="zjgpaypara.keyVersion"/></td>
      <td>所属加密机:</td>
      <td><input type="text" name="zjgpaypara.cryplinkId" id="cryplinkId"/></td>
      <td>卡表类型:</td><td><select name="zjgpaypara.cardmeterType" id="cardmeterType"/></select></td>
    </tr>
    <tr>
      <td>主站算费:</td>
      <td><select id="localMaincalcf" name ="zjgpaypara.localMaincalcf"></select> </td>
    	<td>费率启用日期:</td><td>	<input type="text" id="feeBegindateF" readonly onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:'false',readOnly:'true',position:{button:'above'}});" />
	  	<input type="hidden" name="zjgpaypara.feeBegindate" id="feeBegindate" />
	  	</td>
    	<td class="changeItem">写卡户号:</td>
      	<td><input type="text" name="zjgpaypara.writecardNo" id="writecardNo"/></td>
    </tr>
    
    <!-- 发行电费档案 -->
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px; height:11px"></td>
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
      <td><select id=cbCycleType name="zjgpaypara.cbCycleType"></select></td>
      <td>抄表日:</td><td>
      		<input type="text" id="cbDayhourF" readonly onFocus="WdatePicker({dateFmt:'dd日HH时',isShowClear:'false',readOnly:'true',position:{button:'above'}});"/>
	 		<input type="hidden" name="zjgpaypara.cbDayhour" id="cbDayhour" />
	 	</td>
	  <td>抄表结算标志:</td>
      <td><select id=cbjsFlag name="zjgpaypara.cbjsFlag"></select></td>
    </tr>
  <tr>
      <td>是否发行电费:</td>
      <td><select name = "zjgpaypara.fxdfFlag" id = "fxdfFlag"></select> </td>
      <td>发行电费起始日期:</td><td>	<input type="text" id="fxdfBegindateF" readonly onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:'false',readOnly:'true',position:{button:'above'}});" />
	  	<input type="hidden" name="zjgpaypara.fxdfBegindate" id="fxdfBegindate" />
	  </td><td></td><td></td>
    </tr> 
   
   <!-- 基本数值 -->
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px; height:11px"></td>
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
      <td><select id="useGfh" name="zjgpaypara.useGfh">
        </select></td>
      <td><i18n:message key="doc.gfhsj" />: </td>
      <td><input type = "text" name = "zjgpaypara.hfhTime" id= "hfhTime"/></td>
      <td><i18n:message key="doc.gfhddz" />: </td>
      <td><input type="text" name="zjgpaypara.hfhShutdown" id="hfhShutdown" /></td>
    </tr>
    <tr>
      <td><i18n:message key="doc.fhmcbh" />: </td>
      <td><input type = "text" name = "zjgpaypara.plusTime" id= "plusTime"/></td>
      <td></td><td></td>
 	  <td></td><td></td>
    </tr>
    <tr>
      <td class="changeItem"><i18n:message key="doc.jbf" />: </td>
      <td><input type = "text" name = "zjgpaypara.payAdd1" id= "payAdd1"/></td>
      <td class="changeItem"><%=I18N.getText("doc.fjz",2)%>: </td>
      <td><input type = "text" name = "zjgpaypara.payAdd2" id= "payAdd2"/></td>
      <td class="changeItem"><%=I18N.getText("doc.fjz",3)%>: </td>
      <td><input type = "text" name = "zjgpaypara.payAdd3" id= "payAdd3"/></td>
    </tr>
    
    <!-- 增加力调 -->
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px; height:11px"></td>
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
      <td><select id="powrateFlag" name="zjgpaypara.powrateFlag"></select></td>
      <td>奖罚标志:</td>
      <td><select id="prizeFlag" name="zjgpaypara.prizeFlag"></select></td>
      <td>力调方案: </td>
      <td><select name= "zjgpaypara.csStand" id="csStand"></select></td>
    </tr>
  
  <!-- 预付费时间 -->
  	<tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px; height:11px"></td>
    </tr>
   	<tr><td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;">
   	<table cellpadding="0" cellspacing="0"><tr><td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td><td style="border: 0px; font-size: 12px;">预付费时间</td></tr></table>
   	</td></tr>
   	<tr>
    	<!--
    	<td nowrap><font color=red>*</font><i18n:message key="doc.name" />: </td><td><input type="text" name="rtuPara.describe" id="describe" readonly style="background-color:#DCDCDC;border: 1px solid #808080;" /></td>
    	-->
    	<td nowrap class="changeItem"><i18n:message key="doc.sybz" />: </td><td><select name="rtuPayCtrl.useFlag" id="useFlag"></select></td>
    	<td class="changeItem" ><i18n:message key="doc.yffqyrq" />: </td>
    	<td>
    		<input type="text" id="yffbgDateF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.ymd.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});" />
    		<input type="hidden" name="rtuPayCtrl.yffbgDate" id="yffbgDate" />
    	</td>
    	<td class="changeItem"><i18n:message key="doc.yffqysj" />: </td>
    	<td>
    		<input type="text" id="yffbgTimeF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.hms.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});" />
    		<input type="hidden" name="rtuPayCtrl.yffbgTime" id="yffbgTime" />
    	</td>
    </tr>
    <tr>
    	<td class="changeItem"><i18n:message key="doc.dzksrq" />: </td>
    	<td>
    		<input type="text" id="sg186bgDateF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.ymd.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});"/>
    		<input type="hidden" name="rtuPayCtrl.sg186bgDate" id="sg186bgDate"/>
    	</td>
    	<td class="changeItem"><i18n:message key="doc.dzkssj" />: </td>
   		<td>
   			<input type="text" id="sg186bgTimeF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.hms.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});" />
   			<input type="hidden" name="rtuPayCtrl.sg186bgTime" id="sg186bgTime" />
	    	<input type="hidden" id="rtuPayCtrl_rtuId" name="rtuPayCtrl.rtuId" />
	   	</td>
	   	<td colspan=2></td>
     </tr>
    </table>
   </div>
</form>
</div>
<jsp:include page="../../jsp/inc/jsdef.jsp"></jsp:include>
<script type="text/javascript">
	$("#div_scroll").height(615);
	//var treeHight = $(window).height();
	var update_fail = "<i18n:message key="comm.gridopt.update_fail" />";
	var fjz = "<%=I18N.getText("doc.fjz","")%>";
	var gfhddz = "<i18n:message key="doc.gfhddz" />";
	var zdbdks = "<i18n:message key="doc.zdbdks" />";
	var zdbdjs = "<i18n:message key="doc.zdbdjs" />";
	var gfhsj = "<i18n:message key="doc.gfhsj" />";
	var fhmcbh = "<i18n:message key="doc.fhmcbh" />";
   
    var SDDef = {};
    SDDef.BASEPATH 		= "<%=basePath%>";
    SDDef.SPLITCOMA 	= "<%=SDDef.SPLITCOMA%>";
    SDDef.SUCCESS		= "<%=SDDef.SUCCESS%>";
    SDDef.APPTYPE_ZB 	= "<%=SDDef.APPTYPE_ZB%>"; 
    
    var Dict = {};
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
	//增加抄表周期类型
	Dict.DICTITEM_CB_CYCLE_TYPE  = "<%=Dict.DICTITEM_CB_CYCLE_TYPE%>";
	
	var YDTable = {};
	YDTable.TABLECLASS_YFFALARMPARA  = "<%=YDTable.TABLECLASS_YFFALARMPARA%>";
	YDTable.TABLECLASS_YFFRATEPARA   = "<%=YDTable.TABLECLASS_YFFRATEPARA%>";
    </script>
</body>
</html>
