<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
<%@page import="com.kesd.comntpara.ComntUseropDef"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<i18n:bundle baseName="<%=SDDef.BUNDLENAME%>" id="bundle" locale="<%=WebConfig.app_locale%>" scope="application" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<style type="text/css">
			input,select{
				font-size: 12px;				
			}	
			.tabinfo input{
				width: 140px;
			}
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />		
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>		
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/dyjc/localcardExt/dydestroycus.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setConsExt.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>			
	</head>
	<body>
	<table id="tabinfo1" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:5px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:22px;"  id="td2">基本信息</td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
		    	<button id="btnSearch"  class="btn">检索</button>&nbsp;&nbsp;&nbsp;&nbsp;
		    	<button id="btnRead"  class="btn">读卡</button>&nbsp;&nbsp;&nbsp;&nbsp;
		    	<button id="btnReadSearch"  class="btn">读卡检索</button>	    	
	    	</td>
	    	<td style="border:0px; text-align: right;"><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    </tr>
   		<tr>
	    	<td class="tdr" style="width: 10%">客户编号:</td><td id="userno" style="width: 10%">&nbsp;</td>
	    	<td class="tdr" style="width: 10%">客户名称:</td><td id="username" style="width: 15%">&nbsp;</td>
	     	<td class="tdr" style="width: 10%">客户状态:</td><td id="cus_state" style="width: 10%">&nbsp;</td>
	    </tr>
	     <tr>
	    	<td class="tdr">联系电话:</td><td id="tel">&nbsp;</td>
	   		<td class="tdr">客户地址:</td><td id="useraddr">&nbsp;</td>
	   		<td class="tdr">电表类型:</td><td id="meterType">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">ESAM表号:</td><td id="esamno">&nbsp;</td>
	    	<td class="tdr">预付费表类型:</td><td id="yffmeter_type_desc">&nbsp;</td>
	    	<td class="tdr">生产厂家:</td><td id="factory">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">倍率:</td><td id="blv">&nbsp;</td>
	    	<td class="tdr">接线方式:</td><td id="wiring_mode">&nbsp;</td>
    		<td class="tdr">写卡户号: </td><td id="writecard_no">&nbsp;</td>
	    </tr>
	    <tr id="mis_jsxx">
	        <td class="tdr">结算客户名:</td><td id="jsyhm">&nbsp;</td>
		    <td class="tdr" id="khfl">客户分类:</td><td id="yhfl" colspan=3>&nbsp;</td>
	     </tr>
	   	  <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr><td class="td_lable">费控信息</td></tr>
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
	 	 <tr><td colspan="6" style="height:5px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>	    
	    <tr id="_title3">
	    <td class="td_lable" style="border-bottom: 0">购电记录</td>
	    </tr>
	</table>
	
	<table class="gridbox" align="center">
		<tr><td style="padding: 0 0 0 0;"><div id=gridbox style="height:160px;border:0px;"></div></td></tr>
	</table>
	
	<table id="tabinfo2" class="tabinfo" align="center">
		<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" style="height:25px;">
    	  		<i18n:message key="baseInfo.bdxx"/>
    	  	</td>
	        <td colspan=5 style="padding-left:10px; border: 0px;">
	    		<button id="btnImportBD" class="btn"><i18n:message key="btn.luru"/></button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>
	    </tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.bdVal"/></td>
	    	<td colspan="5" id="td_bdinf" ></td>
	    </tr>
		<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable" style="width: 150px;">缴费信息</td>	  
	    </tr>
	    <tr>
		    <td class="tdr"  width = "10%" >当前余额:          </td><td class="tdrn" width="10%"><input type="text" id="dqye" /></td>
<!--	 		<td class="tdr"  width = "10%"  >算费表底时间:</td><td id="sfbd_sj" width = "10%">&nbsp;</td>-->
<!--	   		<td class="tdr"  width = "10%"  >算费表底:          </td><td id="sfbd" width="20%">&nbsp;</td>-->
			<td class="tdr">剩余电量:</td><td class="tdrn" style="width: 12%;"><span id=buy_dl></span></td>			
	        <td class="tdr">表码差:</td><td class="tdrn" style="width: 12%;"><span id=pay_bmc></span></td>

	    </tr> 
	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	 <tr>
    		<td colspan="6" style="text-align: right;border: 0">
    		<button id="cardinfo"  	 class="btn">卡内信息</button> &nbsp;&nbsp;&nbsp;&nbsp;
<!--	    	<button id="metinfo"  	 class="btn">表内信息</button> &nbsp;&nbsp;&nbsp;&nbsp;-->
	    <!--<button id="getYE"  	 class="btn">获取余额</button> &nbsp;&nbsp;&nbsp;&nbsp; -->
	    	<button id="btnDestroy"  class="btn">销户</button> &nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnPrint"  	 class="btn"  disabled>打印</button> &nbsp;&nbsp;&nbsp;&nbsp;
	    </td></tr>
	</table>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="feeproj_reteval"/>
	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
 		$("#mis_jsxx").hide();	
		$("#gridbox").height($(window).height()-$("#tabinfo1").height()-$("#tabinfo2").height()-10);		
		
		var ComntUseropDef = {};
		ComntUseropDef.YFF_DYOPER_DESTORY = <%=ComntUseropDef.YFF_DYOPER_DESTORY%>;
		ComntUseropDef.YFF_DYOPER_GETREMAIN = <%=ComntUseropDef.YFF_DYOPER_GETREMAIN%>;
		ComntUseropDef.YFF_DYOPER_GPARASTATE = <%=ComntUseropDef.YFF_DYOPER_GPARASTATE%>;
		var SDDef = {};
		SDDef.STR_OR = "<%=SDDef.STR_OR%>";
		SDDef.SUCCESS = "<%=SDDef.SUCCESS%>";
		SDDef.YFF_CACL_TYPE_MONEY = "<%=SDDef.YFF_CACL_TYPE_MONEY%>"
		SDDef.YFF_CACL_TYPE_DL		= <%=SDDef.YFF_CACL_TYPE_DL%>;
	</script>
	
	</body>
</html>
