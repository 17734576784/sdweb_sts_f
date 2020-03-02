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
	    <title>低压补卡</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<style type="text/css">
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
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
		
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>		
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setConsExt.js"></script>
		<script  src="<%=basePath%>js/dyjc/mis/mis.js"></script>
		<script type="text/javascript">
			var SDDef = {
				STR_OR	 : 	"<%=SDDef.STR_OR%>",
				SUCCESS	 : 	"<%=SDDef.SUCCESS%>"
			};
		</script>
	</head>
	<body>
	<table id="tabinfo1" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:5px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height: 25px;">基本信息</td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
	    		<select id="repair_type" style="width: 200px; disabled:'disabled'">
	    		    <option value=1>上次购电后购电卡未插入电表</option>
		    		<!-- 
		    		<option value=0>上次购电后购电卡已插入电表</option>
		    		 -->
		    	</select>&nbsp;&nbsp;&nbsp;&nbsp;
		    	<button id="btnSearch"  class="btn" >检索</button>&nbsp;&nbsp;&nbsp;&nbsp;		    		    
	    	</td>
	    	<td style="border:0px; text-align: right;" ><img id=rtuonline_img style="display:none"/>&nbsp;
       		<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    </tr>
   		<tr>
	    	<td class="tdr" style="width: 12%"><i18n:message key="baseInfo.yhbh"/></td><td id="userno" style="width: 17%">&nbsp;</td>
	    	<td class="tdr" style="width: 12%"><i18n:message key="baseInfo.yhmc"/></td><td id="username" style="width: 20%">&nbsp;</td>
	     	<td class="tdr" style="width: 12%"><i18n:message key="baseInfo.yhzt"/></td><td id="cus_state" >&nbsp;</td>
	    </tr>
	     <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.lxdh"/></td><td id="tel">&nbsp;</td>
	   		<td class="tdr"><i18n:message key="baseInfo.yhdz"/></td><td id="useraddr">&nbsp;</td>
	   		<td class="tdr">电表类型:</td><td id="meterType">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.bh"/></td><td id="esamno">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.yffblx"/></td><td id="yffmeter_type_desc">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.sccj"/></td><td id="factory">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.bl"/></td><td id="blv">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.jxfs"/></td><td id="wiring_mode">&nbsp;</td>
   		    <td class="tdr">写卡户号: </td><td id="writecard_no">&nbsp;</td>
	    </tr>
	    <tr>
	        <td class="tdr">结算客户名:</td><td id="jsyhm">&nbsp;</td>
		    <td class="tdr" id="khfl">客户分类:</td><td id="yhfl" colspan=3>&nbsp;</td>
	    </tr>
	   	<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable"><i18n:message key="baseInfo.fkxx"/></td></tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.jiffs"/></td><td id="cacl_type_desc">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.fkfs"/></td><td id="feectrl_type">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.jffs"/></td><td id="pay_type_desc">&nbsp;</td>    	
	    </tr>
	   	<tr>
	    	<td class="tdr"><i18n:message key="baseInfo.flfa"/></td><td id="feeproj_desc">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.fams"/></td><td colspan=3 id="feeproj_detail">&nbsp;</td>
	   	</tr>
	   	<tr>
	   		<td class="tdr"><i18n:message key="baseInfo.bjfa"/></td><td id="yffalarm_desc">&nbsp;</td>
	   		<td class="tdr"><i18n:message key="baseInfo.fams"/></td><td colspan=3 id="yffalarm_detail">&nbsp;</td>
	    </tr>
	
	
	    <tr><td colspan="6" style="height:8px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>	    
	    <tr>
	    <td class="td_lable" style="border-bottom: 0">购电记录</td>
	    </tr>		
	</table>
	<table class="gridbox" align="center">
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
	</table>
	<table id="tabinfo2" class="tabinfo" align="center">
		<tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>
    	
	    <tr>
	    	<td class="tdr" >缴费金额(元):</td><td class="tdrn" style="width: 12%;"><input type="text" id="jfje"/></td>			
		    <td class="tdr" >追补金额(元):</td><td class="tdrn" style="width: 12%;"><input type="text" id="zbje"/></td>
		    <td class="tdr">结算金额(元):</td><td class="tdrn" style="width: 12%;"><input type="text" id="jsje"  /></td>
	    	<td class="tdr">总金额(元):</td><td class="tdrn" style="width: 12%;"><span  id="zje" ></span></td>
	    </tr>
	    <tr>
	    	<td class="tdr" >购电次数:</td><td class="tdrn" style="width: 12%;"><span id="buy_times"></span></td>
	    	<td class="tdr" >上次缴费(元):</td><td class="tdrn" style="width: 12%;"><span id="all_money">&nbsp;</span></td>
	  		<td class="tdr">缴费电量:</td><td class="tdrn" style="width: 12%;"><span id=buy_dl></span></td>			
	        <td class="tdr">表码差:</td><td class="tdrn" style="width: 12%;"><span id=pay_bmc></span></td>
	    </tr>
	</table>
	<table id="tabinfo3" align="right">
		<tr><td>
			<button id="cardinfo"   class="btn" >卡内信息</button> &nbsp;&nbsp;&nbsp;&nbsp; 
<!--	    	<button id="metinfo"  	class="btn" >表内信息</button> &nbsp;&nbsp;&nbsp;&nbsp;-->
			<button id="repair"  	class="btn" >补卡</button>&nbsp;&nbsp;&nbsp;&nbsp; 
			<button id="btnPrt"     class="btn" disabled="disabled">打印</button>&nbsp;&nbsp; 
		</td></tr>
	</table>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="feeproj_id"/>
	<input type="hidden" id="yffalarm_id"/>
	<input type="hidden" id="feeproj_reteval"/>

	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
 		$("#gridbox").height($(window).height()-$("#tabinfo1").height()-$("#tabinfo2").height()-$("#tabinfo3").height()-5);
		var ComntUseropDef = {};
		ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER%>;
		ComntUseropDef.YFF_DYOPER_MIS_PAY 		 = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_PAY%>;
		ComntUseropDef.YFF_DYOPER_PAY        = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_PAY%>;
		ComntUseropDef.YFF_DYOPER_GPARASTATE = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_GPARASTATE%>;
		ComntUseropDef.YFF_DYOPER_REPAIR     = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_REPAIR%>
		
		var SDDef = {};
		SDDef.YFF_CACL_TYPE_MONEY 	= <%=SDDef.YFF_CACL_TYPE_MONEY%>;
		SDDef.YFF_CACL_TYPE_BD 		= <%=SDDef.YFF_CACL_TYPE_BD%>;
		SDDef.YFF_CACL_TYPE_DL		= <%=SDDef.YFF_CACL_TYPE_DL%>;

		SDDef.YFF_OPTYPE_PAY 		= <%=SDDef.YFF_OPTYPE_PAY%>;	
		SDDef.CARD_OPTYPE_REPAIR	= <%=SDDef.CARD_OPTYPE_REPAIR%>;
		SDDef.CARD_OPTYPE_OPEN		= <%=SDDef.CARD_OPTYPE_OPEN%>

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
		var zje =  "<i18n:message key="baseInfo.zje"/>";
		
		//使用两种补卡方式的标志位 0只是用一种   1使用两种
		var dycard_repairpay_flag     = <%=WebConfig.dycard_repairpay_flag%>
		<%
		 Date date = new Date();
		 java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
		 out.println("var _today = " + sdf.format(date));
		%>
	</script>
	<script  src="<%=basePath%>js/dyjc/localcardExt/dlcalc.js"></script>
	<script  src="<%=basePath%>js/dyjc/localcardExt/dyrepair.js"></script>
	</body>
</html>
