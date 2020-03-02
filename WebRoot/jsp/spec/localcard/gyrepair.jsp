<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef,com.kesd.common.WebConfig"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>高压补卡</title>
    <style type="text/css">
		input{
			width: 130px;
		}
		input,select{
			font-size: 12px;
		}
	</style>
	<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
	<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
	<script  src="<%=basePath%>js/common/modalDialog.js"></script>
	<script  src="<%=basePath%>js/common/number.js"></script>
	<script  src="<%=basePath%>js/common/jsonString.js"></script>
	<script  src="<%=basePath%>js/common/def.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script  src="<%=basePath%>js/spec/localcard/gyrepair.js"></script>
	<script  src="<%=basePath%>js/common/modalDialog.js"></script>
	<script  src="<%=basePath%>js/spec/localcard/card_calcu.js"></script>
	<script  src="<%=basePath%>js/spec/tool/setCons.js"></script>
	<script  src="<%=basePath%>js/spec/mis/mis.js"></script>
	<script  src="<%=basePath%>js/spec/dialog/search.js"></script>
	<script  src="<%=basePath%>js/validate.js"></script>
  </head>
  
  <body>
    <table id="tabinfo" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;">基本信息</td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
	    		<select id="repair_type" style="width: 200px;">
		    		<option value=1>上次购电后购电卡未插入电表</option>
		    		<option value=0>上次购电后购电卡已插入电表</option>
		    	<!-- <option value=2>从未使用购电卡缴费</option> -->		    		
		    	</select>&nbsp;&nbsp;&nbsp;&nbsp;
	    		<button id="btnSearch" class="btn">检索</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>
	    	<td style="border:0px; text-align: right;" ><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    </tr>
   		<tr>
	    	<td class="tdr" width="12%">客户编号:</td><td id="userno"  width="17%">&nbsp;</td>
	    	<td class="tdr" width="12%">客户名称:</td><td id="username" width="20%">&nbsp;</td>
	     	<td class="tdr" width="12%">客户状态:</td><td id="cus_state">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">联系电话:</td><td id="tel">&nbsp;</td>
	   		<td class="tdr">客户地址:</td><td colspan=3 id="useraddr">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">费控单元:</td><td id="fee_unit">&nbsp;</td>
	    	<td class="tdr">结算客户名:</td><td id="jsyhm">&nbsp;</td>
	    	<td class="tdr" id="khfl">客户分类:</td><td id="yhfl">&nbsp;</td>
	    </tr>
   		<tr>
	    	<td class="tdr">终端型号:</td><td id="rtu_model">&nbsp;</td>
	    	<td class="tdr">生产厂家:</td><td id="factory">&nbsp;</td>
	     	<td class="tdr">倍率:</td><td id="blv">&nbsp;</td>
	    </tr>
	   <tr>
	    	<td class="tdr">计费方式:</td><td id="cacl_type_desc">&nbsp;</td>
	    	<td class="tdr">费控方式:</td><td id="feectrl_type">&nbsp;</td>
	    	<td class="tdr">缴费方式:</td><td id="pay_type_desc">&nbsp;</td>
	    </tr>
	   	<tr>
	    	<td class="tdr">费率方案:</td><td id="feeproj_desc">&nbsp;</td>
	    	<td class="tdr">方案描述:</td><td colspan=3 id="feeproj_detail">&nbsp;</td>
	   	</tr>
	   	<tr>
	   		<td class="tdr">报警方案:</td><td id="yffalarm_desc">&nbsp;</td>
	   		<td class="tdr">方案描述:</td><td colspan=3 id="yffalarm_detail">&nbsp;</td>
	    </tr>
	    <tr>
			<td class="tdr">卡表类型: </td><td id="cardmeter_type">&nbsp;</td>
	   		<td class="tdr">写卡户号: </td><td id="writecard_no">&nbsp;</td>
	   		<td class="tdr">ESAM表号:</td><td id="meter_id">&nbsp;</td>
		</tr>
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    		    
	   	<tr><td class="td_lable"  style="border-bottom: 0">购电记录</td></tr>
	    </table>
	<table class="gridbox" align="center">
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
	</table>
	
		<table id="tabinfo2" class="tabinfo" align="center">
		<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable" style="width: 120px;">缴费信息</td>	  
	    </tr>
	    <tr>	    
		    <td class="tdr" width="12%">缴费金额(元):</td><td class="tdrn" width="13%"><input type="text" id="jfje" /></td>
		    <td class="tdr" width="12%">追补金额(元):</td><td class="tdrn" width="13%"><input type="text" id="zbje" /></td>
	    	<td class="tdr" width="12%">结算金额(元):</td><td class="tdrn" width="13%"><input type="text" id="jsje" /></td>
	    	<td class="tdr" width="12%">总金额(元):    </td><td class="tdrn" width="12%"><span  id="zje" style="width: 100%"></span></td>
	    </tr>
	    <tr>
	    <!-- <td class="tdr">上次缴费金额(元):</td><td class="tdrn"><span id="last_jfje">&nbsp;</span></td> -->
		    <td class="tdr">报警值1:</td><td class="tdrn"><input type="text" id="yffalarm_alarm1" /></td>
	    	<td class="tdr">报警值2:</td><td class="tdrn"><input type="text" id="yffalarm_alarm2" /></td>
		    <td class="tdr">购电次数:</td><td class="buy_times"><span id="buy_times"  ></span></td>
	    	<td></td> <td></td>
	    </tr>

	    <tr id = gdlctrl>
	    	<td class="tdr">购电量:</td><td class="tdrn" id="buy_dl" /></td>
	     	<td class="tdr">表码差:</td><td class="tdrn" id="pay_bmc"/></td>
	     	<td></td> <td></td><td></td> <td></td>
	    </tr>

    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
	</table>
	
	<table id="tabinfo3" align="right">
		<tr><td>
			<button id="cardinfo"  class="btn" >卡内信息</button> &nbsp;&nbsp;&nbsp;&nbsp; 
<!--    	<button id="rtuinfo"  class="btn" disabled="disabled">终端内信息</button> &nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp; --> 
			<button id="repair"  class="btn" disabled="disabled">补卡</button>&nbsp;&nbsp;&nbsp;&nbsp; 
			<button id="btnPrt"  class="btn" disabled="disabled">打印</button>&nbsp;&nbsp; 
		</td></tr>
	</table>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="zjg_id"/>
	<input type="hidden" id="feeproj_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="yffalarm_id"/>
	<input type="hidden" id="feeproj_reteval"/>
	<jsp:include page="../../inc/jsdef.jsp"></jsp:include>
	<script type="text/javascript">
	
	$("#gridbox").height($(window).height()-$("#tabinfo").height()-$("#tabinfo2").height()-$("#tabinfo3").height()-5);		
	var ComntUseropDef = {};
	ComntUseropDef.YFF_GYOPER_ADDCUS		= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_ADDCUS%>;
	ComntUseropDef.YFF_GYOPER_REPAIR		= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_REPAIR%>;
	ComntUseropDef.YFF_GYOPER_PAY			= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_PAY%>;
	ComntUseropDef.YFF_GYOPER_MIS_QUERYPOWER= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_MIS_QUERYPOWER%>;
	ComntUseropDef.YFF_GYOPER_MIS_PAY 		= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_MIS_PAY%>;
	
	var SDDef = {};
	SDDef.YFF_CACL_TYPE_MONEY 	= <%=SDDef.YFF_CACL_TYPE_MONEY%>;
	SDDef.YFF_CACL_TYPE_BD 		= <%=SDDef.YFF_CACL_TYPE_BD%>;

	SDDef.YFF_OPTYPE_PAY 		= <%=SDDef.YFF_OPTYPE_PAY%>;

	SDDef.CARD_OPTYPE_REPAIR	= <%=SDDef.CARD_OPTYPE_REPAIR%>;
	SDDef.CARD_OPTYPE_OPEN		= <%=SDDef.CARD_OPTYPE_OPEN%>
	
	//使用两种补卡方式的标志位 0只是用一种   1使用两种
	var gycard_repairpay_flag     = <%=WebConfig.gycard_repairpay_flag%>
	function change(type){
/*			if(type!="0"){
				$("#tabinfo2").attr("style","display:none");
				$("#gridbox").height($(window).height()-$("#tabinfo").height()-$("#tabinfo3").height()-5);
			}else{
				$("#tabinfo2").attr("style","display:blank");
				$("#gridbox").height($(window).height()-$("#tabinfo").height()-$("#tabinfo2").height()-$("#tabinfo3").height()-5);
			}
			clearUserInf();*/
		}
	
	</script>
  </body>
</html>
