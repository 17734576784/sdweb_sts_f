<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
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
		input{
			width: 120px;
		}
		input,select{
			font-size: 12px;				
		}
		body{
		    overflow: auto;
		}	
		</style>
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>		
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/dyjc/localcardExt/dlcalc.js"></script>
		<script  src="<%=basePath%>js/dyjc/localcardExt/dyrever.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/sg186.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setConsExt.js"></script>
		<script  src="<%=basePath%>js/dyjc/mis/mis.js"></script>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
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
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
	</table> 
	<table id="tabinfo2" class="tabinfo" align="center">
		<tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable">缴费信息</td>	  
	    </tr>
	    <tr>
	    	<td class="tdr">购电次数:</td><td class="tdrn" colspan=7><span id="buy_times" ></span></td>
	    </tr>
	    <tr>
	    	<td class="tdr" width="150">上次缴费金额(元):</td><td class="tdrn"><input id="last_je" readonly style="background: #ccc;" /></td>
	    	<td class="tdr" width="120">上次追补金额(元):</td><td class="tdrn"><input id="last_zbje" readonly style="background: #ccc;" /></td>
	    	<td class="tdr" width="120">上次结算金额(元):</td><td class="tdrn"><input id="last_jsje" readonly style="background: #ccc;" /></td>
	    	<td class="tdr" width="100">上次总金额(元):</td><td class="tdrn"><input id="last_tt" readonly style="background: #ccc;"/></td>		  		        		    				
	    </tr>
	    <tr>
			<td class="tdr">实际缴费金额(元):</td><td class="tdrn" ><input id="jfje" name="jfje" value=""/></td>
			<td class="tdr">实际追补金额(元):</td><td class="tdrn" ><input id="zbje" name="zbje" value=""/></td>
			<td class="tdr">实际结算金额(元):</td><td class="tdrn" ><input id="jsje" name="jsje" value=""/></td>
			<td class="tdr">实际总金额(元):     </td><td class="tdrn" style="width: 100px;"><span id="zje" >&nbsp;</span></td>
	    </tr>
	    <tr>
	    	<td class="tdr">缴费电量:</td><td class="tdrn" style="width: 12%;"><span id=buy_dl></span></td>			
	        <td class="tdr">表码差:</td><td class="tdrn" style="width: 12%;"><span id=pay_bmc></span></td>
	    	<td></td><td></td><td></td><td></td>
	    
	    </tr>
		<tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>
    	<tr>    	
			<td class="tdr" colspan=8 style="border: 0px;">		
    		<button id="cardinfo"  class="btn" >卡内信息</button> &nbsp;&nbsp;&nbsp;&nbsp; 
			<button id="btnRever" class="btn" disabled="disabled">冲正</button> &nbsp;&nbsp;&nbsp;&nbsp; 
			<button id="btnPrt" class="btn" disabled>打印</button>
			</td>
		</tr>
	</table>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="feeproj_id"/>
	<input type="hidden" id="yffalarm_id"/>
	<input type="hidden" id="feeproj_reteval"/>
		
	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
 		$("#gridbox").height($(window).height()-$("#tabinfo1").height()-$("#tabinfo2").height()-10);
		var ComntUseropDef = {};
		ComntUseropDef.YFF_DYOPER_PAY 				= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_PAY%>;
		ComntUseropDef.YFF_DYOPER_GPARASTATE 		= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_GPARASTATE%>;
		ComntUseropDef.YFF_DYOPER_REVER 			= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_REVER%>;
		ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER    = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER%>;
		ComntUseropDef.YFF_DYOPER_MIS_REVER			= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_REVER%>;
		
		var SDDef = {};
		SDDef.STR_OR	 = 	"<%=SDDef.STR_OR%>";
		SDDef.SUCCESS	 = 	"<%=SDDef.SUCCESS%>";
		SDDef.CARD_OPTYPE_BUY   = <%=SDDef.CARD_OPTYPE_BUY%>;
		SDDef.CARD_OPTYPE_OPEN	= <%=SDDef.CARD_OPTYPE_OPEN%>;
		SDDef.CARD_OPTYPE_REPAIR= <%=SDDef.CARD_OPTYPE_REPAIR%>;
		
		SDDef.YFF_OPTYPE_REVER  = <%=SDDef.YFF_OPTYPE_REVER%>;

		SDDef.YFF_CACL_TYPE_MONEY=<%=SDDef.YFF_CACL_TYPE_MONEY%>
		SDDef.YFF_CACL_TYPE_DL		= <%=SDDef.YFF_CACL_TYPE_DL%>;
		
		SDDef.YFF_FEETYPE_DFL	= <%=SDDef.YFF_FEETYPE_DFL%>;
		SDDef.YFF_FEETYPE_MIX   = <%=SDDef.YFF_FEETYPE_MIX%>;
		SDDef.YFF_FEETYPE_JTFL  = <%=SDDef.YFF_FEETYPE_JTFL%> //阶梯费率             3
		SDDef.YFF_FEETYPE_MIXJT = <%=SDDef.YFF_FEETYPE_MIXJT%>//混合阶梯费率 4
						
		var yff_grid_title ='<i18n:message key="baseInfo.yff.grid_title"/>';
		var sel_user_info = "<i18n:message key="baseInfo.sel_user_info"/>";
		var khcg = "<i18n:message key="baseInfo.khcg"/>";
		var khsb = "<i18n:message key="baseInfo.khsb"/>";
		var zbje = "<i18n:message key="baseInfo.zbje"/>";
		var jsje = "<i18n:message key="baseInfo.jsje"/>";
		var jfje = "<i18n:message key="baseInfo.jfje"/>";
		var zje  = "<i18n:message key="baseInfo.zje"/>";
		<%
		Date date = new Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
		out.println("var _today = " + sdf.format(date));
		%>
	</script>
	</body>
</html>
