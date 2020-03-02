<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.sts.common.STSDef"%>
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
		
		<script>
			var basePath = '<%=basePath%>';
		</script>
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/common/jquery.jqprint-0.3.js"></script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/dyjc/localcard/dypay.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>
		<script  src="<%=basePath%>js/dyjc/mis/mis.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>

		<script type="text/javascript">
			var SDDef = {
				STR_OR	 : 	"<%=SDDef.STR_OR%>",
				SUCCESS	 : 	"<%=SDDef.SUCCESS%>"
			};
			var STSDef = {
				STS_TOKEN_EXPIRED : "<%=STSDef.STS_TOKEN_EXPIRED%>",
				STS_TOKEN_ERROR   : "<%=STSDef.STS_TOKEN_ERROR%>",
				STS_KEYCHANGE     : "<%=STSDef.STS_KEYCHANGE%>",
				STS_METERKEY_ERROR: "<%=STSDef.STS_METERKEY_ERROR%>"
			};
		</script>
	</head>
	<body>
	<div id="dy_pay" style="overflow: auto;">
	<table id="tabinfo" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;">Base Information</td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
	    	<button id="btnSearch" class="btn" >Retrieval</button>&nbsp;&nbsp;&nbsp;&nbsp;
 	    	</td>
	    	<td style="border:0px; text-align: right;" ><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    </tr>
   		<tr>
	    	<td class="tdr" width="12%">Consumer ID:</td><td id="userno" width="15%">&nbsp;</td>
	    	<td class="tdr" width="12%">Consumer Name:</td><td id="username" width="20%">&nbsp;</td>
	     	<td class="tdr" width="12%">Consumer State:</td><td id="cus_state">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">Telephone:</td><td id="tel">&nbsp;</td>
	   		<td class="tdr">Address:</td><td colspan=3 id="useraddr">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">Meter ID:</td><td id="commaddr">&nbsp;</td>
	    	<td class="tdr">Prepayment Type:</td><td id="yffmeter_type_desc">&nbsp;</td>
	    	<td class="tdr">Utility ID:</td><td id="utilityid">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">Manufacturers:</td><td id="factory">&nbsp;</td>
	    	<td></td><td></td>
	    	<td></td><td></td>
	    </tr>
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable">Cost Control Information</td></tr>
	   <tr>
	    	<td class="tdr">Billing Mode:</td><td id="cacl_type_desc">&nbsp;</td>
	    	<td class="tdr">Cost Control Mode:</td><td id="feectrl_type_desc">&nbsp;</td>
	    	<td class="tdr">Payment Mode:</td><td id="pay_type_desc">&nbsp;</td>
	    </tr>
	   	<tr>
	    	<td class="tdr">Tariff Project:</td><td id="feeproj_desc">&nbsp;</td>
	    	<td class="tdr">Tariff Description:</td><td colspan=3 id="feeproj_detail">&nbsp;</td>
	   	</tr>
	   	<tr>
	   		<td class="tdr">Overdraw Amount:</td><td id="tzval">&nbsp;</td>
	   		<td class="tdr">CT:</td><td id="ctData"></td>
	   		<td class="tdr">PT:</td><td id="ptData"></td>
	    </tr>
	 	<tr><td colspan="6" style="height:5px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>	    
	    <tr id="_title3">
	    <td class="td_lable" style="border-bottom: 0">Purchasing Record</td>
	    </tr>
	</table>
	<table class="gridbox" align="center">
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
	</table>
	<table id="tabinfo3" class="tabinfo" align="center">
		<tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable">Billing Information</td>
	    </tr>
	    <tr>
		    <td class="tdr" style="width: 10%">Times of Purchasing:</td>
		    <td class="tdrn"><span id="buy_times"></span></td>
	    	<td class="tdr" style="width: 150px"><!-- Current Balance(TAKA):隐藏当前余额 --></td>
		    <td class="buy_times" style="width: 17%" id="dqye" colspan=5 ></td>
	    </tr>
	    <tr>
		    <td class="tdr" style="width: 10%">Balance:</td>
		    <td class="tdrn"><span id="jy_money"></span></td>
		    <td class="tdr" style="width: 150px"></td>
		    <td class="buy_times" style="width: 17%" colspan=5 ></td>
	    </tr>	    
	   	<tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable">Recharge Information</td>
    	   	<td class="tdr" style="width: 100px">KEN:</td>	
    	   	<td class="tdr" style="width: 100px"><input type="text" id="KEN" value="255"/></td>	  
	    </tr>
    	<tr>
    	   	<td class="tdr" id="firstLabel"  style="width: 8%;"></td><td class="tdrn" style="width: 16%;" id="paymenttoken1" style="text-align: left;dispaly:none"></td>
    	   	<td class="tdr" id="secondLabel" style="width: 8%;"></td><td class="tdrn" style="width: 16%;" id="paymenttoken2" style="text-align: left;dispaly:none"></td>
    	   	<td class="tdr" id="thirdLabel"  style="width: 8%;"></td><td class="tdrn" style="width: 16%;" id="paymenttoken3" style="text-align: left;dispaly:none"></td>
	    </tr>	    
	    <tr>
	        <td class="tdr" style="width: 12%;">Payment amount:</td><td class="tdrn" style="width: 12%;"><input type="text" id=zje /></td>
	    	<td class="tdr" style="width: 12%;">Transfer Credit(kWh):</td><td class="tdrn" style="width: 12%;"><span id="zdl"></span></td>	
	    	<td colspan="2" ></td>
 	    </tr>
	    <tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>	   
	</table>
	<table id="tabbutton" class="tabinfo" align="center">
		<tr>
			<td style="text-align: right;border: 0; width:57%"></td>
			<td style="text-align: right;border: 0; width:10%">
				<!-- <button id="chgMbphone" class="btn"><i18n:message key="btn.ghsj"/></button> --> 
			</td>
			<td style="text-align: right;border: 0; width:3%"></td>
    		<td style="text-align: right;border: 0; width:30%">
 	    	<button id="pay"  class="btn"  disabled="disabled">Payment</button> &nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="prt"  class="btn"  disabled="disabled">Print</button> &nbsp;&nbsp;&nbsp;&nbsp;  	 
	    	</td>
	    </tr>	 
	</table>
	
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="feeproj_id"/>
	<input type="hidden" id="feectrl_type"/>
	<input type="hidden" id="cacl_type"/>
	<input type="hidden" id="seqNo"/>
	<input type="hidden" id="keyNo"/>
	
	<input type="hidden" id="friend_s"/>
	<input type="hidden" id="friend_e"/>
	<input type="hidden" id="friendDate"/>
	<input type="hidden" id="res_id"/>
	
	<jsp:include page="../../inc/jsdef.jsp" />
	<script  src="<%=basePath%>js/common/def.js"></script>		
	
	<script type="text/javascript">
 		$("#dy_pay" ).height($(window).height());
 		$("#gridbox").height($(window).height()-$("#tabinfo").height()-$("#tabinfo3").height()- $("#tabbutton").height()-30);
 		var ComntUseropDef = {};
		ComntUseropDef.YFF_DYOPER_PAY = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_PAY%>;
		ComntUseropDef.YFF_DYOPER_GPARASTATE = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_GPARASTATE%>;
		ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER%>;
		ComntUseropDef.YFF_DYOPER_MIS_PAY = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_PAY%>;
		ComntUseropDef.YFF_DYOPER_RESETDOC      = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_RESETDOC%>;
		var SDDef = {};
		SDDef.YFF_OPTYPE_PAY    = <%=SDDef.YFF_OPTYPE_PAY%>;
		SDDef.YFF_OPTYPE_ADDRES	= <%=SDDef.YFF_OPTYPE_ADDRES%>;
		SDDef.CARD_OPTYPE_BUY   = <%=SDDef.CARD_OPTYPE_BUY%>;
		SDDef.YFF_FEETYPE_JTFL  = <%=SDDef.YFF_FEETYPE_JTFL%>; //阶梯费率             3
		SDDef.YFF_FEETYPE_MIXJT = <%=SDDef.YFF_FEETYPE_MIXJT%>;//混合阶梯费率 4
		SDDef.YFF_CUSSTATE_NORMAL = <%=SDDef.YFF_CUSSTATE_NORMAL%>;
		
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
