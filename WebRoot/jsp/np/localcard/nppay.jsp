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
				width: 120px;
			}
		</style>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/zh/cont.css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/np/localcard/nppay.js"></script>
		<script  src="<%=basePath%>js/np/dialog/search.js"></script>
		<script  src="<%=basePath%>js/np/tool/setCons.js"></script>
		<script  src="<%=basePath%>js/np/mis/mis.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
		<script type="text/javascript">
			var SDDef = {
				STR_OR	 : 	"<%=SDDef.STR_OR%>",
				SUCCESS	 : 	"<%=SDDef.SUCCESS%>"
			};
		</script>
	</head>
	<body>
	<div id="dy_pay" style="overflow: auto">
		<table id="tabinfo1" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:5px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:22px;"  id="td2">基本信息</td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
		    	<button id="btnRead"  class="btn">读卡</button>	    	
	    	</td>
	    	<td style="border:0px; text-align: right;"><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    </tr>
   		<tr>
	    	<td class="tdr" style="width: 10%">客户编号:</td><td id="userno" style="width: 10%">&nbsp;</td>
	    	<td class="tdr" style="width: 10%">客户名称:</td><td id="username" style="width: 15%">&nbsp;</td>
	     	<td class="tdr" style="width: 10%">客户状态:</td><td id="cus_state" style="width: 10%">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">身份证号:</td><td id="identityno">&nbsp;</td>
	    	<td class="tdr">自然村:</td><td id="village">&nbsp;</td>
	    	<td class="tdr">联系电话:</td><td id="tel">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">客户地址:</td><td id="useraddr">&nbsp;</td>
	   		<td class="tdr">结算客户名:</td><td id="jsyhm">&nbsp;</td>
		    <td class="tdr">客户分类:</td><td id="yhfl">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">所属片区:</td><td id="area">&nbsp;</td>
	    	<td class="tdr">区域号:</td><td id="area_no">&nbsp;</td>
	    	<td></td><td></td>    	
	    </tr>

	 	 <tr><td colspan="6" style="height:5px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>	    
	    <tr id="_title3">
	    <td class="td_lable" style="border-bottom: 0">购电记录</td><td id="ydInfo" style="border : 0; visibility: hidden" onclick="showYDInfo();">&nbsp;&nbsp;&nbsp;&nbsp;<a href='#'>用电信息</a></td>
	    </tr>
	</table>
	<table class="gridbox" align="center">
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
	</table>
	<table id="tabinfo3" class="tabinfo" align="center">
		<tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable">购电卡信息</td>
	    </tr>
	    <tr>
	    	<td class="tdr">购电卡号:</td><td id="card_no">&nbsp;</td>
	    	<td class="tdr">购电卡状态:</td><td id="card_state_desc">&nbsp;</td>
		    <td class="tdr" style="width: 150px">卡内余额(元):</td>
		    <td class="tdrn" style="width: 17%" id="now_remain">
		    </td>
		    <td class="tdr" style="width: 10%">购电次数:</td><td colspan=5 class="tdrn"><span id="buy_times"></span></td>
	    </tr>
	   	<tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable">缴费信息</td>	  
	    </tr>
	    <tr>
	    	<td class="tdr">缴费金额(元):</td><td class="tdrn" style="width: 12%;"><input type="text" id="pay_money" /></td>			
	        <td class="tdr">追补金额(元):</td><td class="tdrn" style="width: 12%;"><input type="text" id="zb_money" /></td>
		    <td class="tdr">结算金额(元):</td><td class="tdrn" style="width: 12%;"><input type="text" id="othjs_money" /></td>
	    	<td class="tdr">总金额(元):</td><td colspan=5 class="tdrn" style="width: 12%;"><span id="all_money"></span></td>	
	    </tr>
	    <tr><td colspan="8" style="height:5px; border: 0px;">&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>	
	    <!--
		<tr>
    		<td colspan="8" style="text-align: right;border: 0">
    		<button id="cardinfo" class="btn" >卡内信息</button> &nbsp;&nbsp;&nbsp;&nbsp;
    		<button id="chgMbphone" class="btn"><i18n:message key="btn.ghsj"/></button> &nbsp;&nbsp;&nbsp;&nbsp;  
	    	<button id="pay"  class="btn"  disabled="disabled">缴费</button> &nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="prt"  class="btn"  disabled="disabled">打印</button> &nbsp;&nbsp;&nbsp;&nbsp;	    	 
	    </td></tr>
	      -->  	   
	</table>
	 
	<table id="tabbutton" class="tabinfo" align="center">
		<tr  valign="top">
			<td style="text-align: right;border: 0; width:55%"></td>
			<td style="text-align: right;border: 0; width:10%">
				<button id="chgMbphone" class="btn"><i18n:message key="btn.ghsj"/></button> &nbsp;&nbsp;&nbsp;&nbsp; 
			</td>
			<td style="text-align: right;border: 0; width:5%"></td>
    		<td style="text-align: right;border: 0; width:30%">
    		<button id="cardinfo" class="btn" >卡内信息</button> &nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="pay"  class="btn"  disabled="disabled">缴费</button> &nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="prt"  class="btn"  disabled="disabled">打印</button> &nbsp;&nbsp;&nbsp;&nbsp;	    	 
	    	</td>
	    </tr>	 
	</table>
	
	<input type="hidden" id="area_id"/>
	<input type="hidden" id="farmer_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="infoNum"> <!-- 缴费记录条数 -->
	
	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
 		$("#gridbox").height($(window).height()-$("#tabinfo1").height()-$("#tabinfo3").height()-30);
 		$("#dy_pay" ).height($(window).height());
 		var ComntUseropDef = {};
		ComntUseropDef.YFF_NPOPER_PAY = <%=ComntUseropDef.YFF_NPOPER_PAY%>;
		ComntUseropDef.YFF_NPOPER_GPARASTATE = <%=ComntUseropDef.YFF_NPOPER_GPARASTATE%>;
		ComntUseropDef.YFF_NPOPER_RESETDOC = <%=ComntUseropDef.YFF_NPOPER_RESETDOC%>;
		//ComntUseropDef.YFF_NPOPER_MIS_QUERYPOWER = <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_RESETDOC%>;
		//ComntUseropDef.YFF_NPOPER_MIS_PAY = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_PAY%>;
		var SDDef = {};
		SDDef.YFF_OPTYPE_PAY    = <%=SDDef.YFF_OPTYPE_PAY%>;
		SDDef.CARD_OPTYPE_BUY   = <%=SDDef.CARD_OPTYPE_BUY%>;
		SDDef.YFF_FEETYPE_JTFL  = <%=SDDef.YFF_FEETYPE_JTFL%>; //阶梯费率             3
		SDDef.YFF_FEETYPE_MIXJT = <%=SDDef.YFF_FEETYPE_MIXJT%>;//混合阶梯费率 4
		
		SDDef.NPCARD_OPTYPE_INITPARA	= <%=SDDef.NPCARD_OPTYPE_INITPARA%>;	//不缴费 仅初始化参数
		SDDef.NPCARD_OPTYPE_OPEN		= <%=SDDef.NPCARD_OPTYPE_OPEN%>;		//卡类型 开户
		SDDef.NPCARD_OPTYPE_BUY			= <%=SDDef.NPCARD_OPTYPE_BUY%>;			//买电充值
		SDDef.NPCARD_OPTYPE_REVER		= <%=SDDef.NPCARD_OPTYPE_REVER%>;		//冲正
		
		var sel_user_info = "<i18n:message key="baseInfo.sel_user_info"/>";
		var khcg = "<i18n:message key="baseInfo.khcg"/>";
		var khsb = "<i18n:message key="baseInfo.khsb"/>";
		var zbje = "<i18n:message key="baseInfo.zbje"/>";
		var jsje = "<i18n:message key="baseInfo.jsje"/>";
		var jfje = "<i18n:message key="baseInfo.jfje"/>";
		var zje  = "<i18n:message key="baseInfo.zje"/>";
	</script>
	</div>
	</body>
</html>
