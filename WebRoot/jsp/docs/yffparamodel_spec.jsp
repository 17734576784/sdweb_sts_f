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
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <base href="<%=basePath%>">
    
    <title>专变快速建档模板</title>
    <style type="text/css">
	    body {
			margin:0 0 0 0;
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
    </style>
 
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
  	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  	<script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
  	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
  	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
  	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
  	<script src="<%=basePath%>js/common/gridopt.js"></script>
	<script src="<%=basePath%>js/common/def.js"></script>
	<script src="<%=basePath%>js/common/loading.js"></script>
	<script src="<%=basePath%>js/common/dateFormat.js"></script>
	<script src="<%=basePath%>js/common/modalDialog.js"></script>
	<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=basePath%>js/validate.js"></script>
	<script src="<%=basePath%>js/docs/yffparamodel_spec.js"></script>
	<script src="<%=basePath%>js/docs/docper.js"></script>
	
  </head>
  
  <body>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
    	<td>
    		<table cellpadding="0" cellspacing="0" height="24" width="100%" class="page_tbl_bg22">
    			<tr>
    				<td width="1%"></td>
    				<td align="left" width="55%">
    					模板列表:<select name="model_list" id="model_list" style="width: 120px"></select>&nbsp;&nbsp;&nbsp;&nbsp;
    					模板名称:<input type="text" name="model_name" id="model_name" style="width: 120px"/>
    					&nbsp;&nbsp;&nbsp;&nbsp;
    					<input type="button" id="add" value="新增">&nbsp;&nbsp;
    					<input type="button" id="edit" value="修改">&nbsp;&nbsp;
    					<input type="button" id="del" value="删除">
    				</td>
    				<td align="right" width=41%>
    				<input type="button" id="import" value="导入"/>&nbsp;&nbsp;
    				<input type="button" id="export" value="导出"/>
    				</td>
    				<td width=2%></td>
    				</tr>
    		</table>
    	</td>
    </tr>
    <tr>
   <td>
    	<form action="" method="post" id="addorupdate"  style='display:inline;'>
  		<div id="d_jbcs" class="d_cl" style="overflow: auto">
  		<table class="tabsty" align="center">
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
      <td>卡表类型:</td><td><select name="zjgpaypara.cardmeterType" id="cardmeterType" style="width:140px;" /></select></td>
    </tr>
    <tr>
      <td>主站算费:</td>
      <td><select id="localMaincalcf" name ="zjgpaypara.localMaincalcf" style="width:160px;"></select> </td>
    	<td>费率启用日期:</td><td>	<input type="text" id="feeBegindateF" readonly onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:'false',readOnly:'true',position:{button:'above'}});" />
	  	<input type="hidden" name="zjgpaypara.feeBegindate" id="feeBegindate" />
	  	</td>
	  	<td><!-- 写卡户号: --></td>
      	<td><!-- <input type="text" name="zjgpaypara.writecardNo" id="writecardNo" style="width:140px;" /> --></td>
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
      <!--
      <input type="hidden" id="rtuId" name="zjgpaypara.rtuId" />
      <input type="hidden" id="zjgId" name="zjgpaypara.zjgId" /></td>
      -->
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
 </form>
</td>
</tr>
</table>
<br/>
<jsp:include page="../../jsp/inc/jsdef.jsp"></jsp:include>
<script type="text/javascript">
	$("#d_jbcs").height($(window).height()-24);
	var update_fail = "<i18n:message key="comm.gridopt.update_fail" />";
	var fjz = "<%=I18N.getText("doc.fjz","")%>";
	var gfhddz = "<i18n:message key="doc.gfhddz" />";
	var zdbdks = "<i18n:message key="doc.zdbdks" />";
	var zdbdjs = "<i18n:message key="doc.zdbdjs" />";
	var gfhsj = "<i18n:message key="doc.gfhsj" />";
	var fhmcbh = "<i18n:message key="doc.fhmcbh" />";
   
    var SDDef = {};
    SDDef.BASEPATH = "<%=basePath%>";
    SDDef.SPLITCOMA = "<%=SDDef.SPLITCOMA%>";
    SDDef.SUCCESS	= "<%=SDDef.SUCCESS%>";
    SDDef.APPTYPE_ZB = "<%=SDDef.APPTYPE_ZB%>";
    
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
