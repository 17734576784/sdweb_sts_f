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
		<script  src="<%=basePath%>js/dyjc/localcardExt/dywrite.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setConsExt.js"></script>
		<script  src="<%=basePath%>js/dyjc/mis/mis.js"></script>
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
	<div id="dy_pay" style="overflow: auto;">
	<table id="tabinfo1" class="tabinfo" align="center">
    	<tr><td colspan="8" style="height:5px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:22px;"  id="td2">基本信息</td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
		    	<button id="btnSearch"  class="btn">检索</button>&nbsp;&nbsp;&nbsp;&nbsp;
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
	    
	    <tr id="jt_info"  style="display: none"><td colspan="6" style="height:5px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>
	    <tr id="jt_info1" style="display: none"><td class="td_lable" >阶梯信息</td></tr>
	    <tr id="jt_info2" style="display : none">
	    	<td class="tdr">阶梯剩余金额:   </td><td id="now_remain">&nbsp;</td>
	    	<td class="tdr">电表剩余金额:</td><td id="now_remain2">&nbsp;</td>
	    	<td class="tdr">阶梯累计用电量:</td><td id="jt_total_dl">&nbsp;</td>    	
	    </tr>
	 	<tr><td colspan="6" style="height:5px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>	    
	    <tr id="_title3">
	    <td class="td_lable" style="border-bottom: 0">购电记录</td>
	    </tr>
	</table>
	<table class="gridbox" align="center">
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
	</table>
	<table id="tabinfo3" class="tabinfo" align="center">
	   <tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>
       <tr><td class="td_lable">费率信息</td> </tr>
	    <tr>
		    <td class="tdr">新费率方案:</td>
	    	<td ><select id="xflfa" style="width: 150px;"></select></td>
	    	<td class="tdr">方案描述:</td><td colspan=5 id="feeproj_detail_new"></td>
	    </tr>	
	    <tr>
		    <td class="tdr"">新分时费率切换时间:</td>
	    	<td  ><input id=xqhsj name=xqhsj  style="width:180px;text-align:left;"  onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日 HH时mm分',isShowClear:'false'});"/>
	    	</td>  	
	    	<td class="tdr" >新阶费率梯切换时间:</td>
	    	<td>
	    		<input id="jt_xqhsj" name=jt_xqhsj  style="width:180px;text-align:left;"  onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日 HH时mm分',isShowClear:'false'});"/>
	    	</td> 
	    </tr>	
	
		<tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable">计费信息</td>
	    </tr>
	    <tr>
		    <td class="tdr" style="width: 150px">当前余额(元):</td>
		    <td class="buy_times" style="width: 17%" id="dqye">
		    </td>
		    <td class="tdr" style="width: 10%">购电次数:</td><td colspan=5 class="tdrn"><span id="buy_times"></span></td>
	    </tr>
	   	<tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable">缴费信息</td>	  
	    </tr>
	    <tr>
	    	<td class="tdr">缴费金额(元):</td><td class="tdrn" style="width: 12%;"><input type="text" id=jfje /></td>			
	        <td class="tdr">追补金额(元):</td><td class="tdrn" style="width: 12%;"><input type="text" id=zbje /></td>
		    <td class="tdr">结算金额(元):</td><td class="tdrn" style="width: 12%;"><input type="text" id=jsje /></td>
	    	<td class="tdr">总金额(元):</td><td class="tdrn" style="width: 12%;"><span id="zongje"></span></td>	
	    </tr>
	    <tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>	 
	</table>
	<table id="tabbutton" class="tabinfo" align="center">
		<tr>
			<td style="text-align: right;border: 0; width:57%"></td>
			<td style="text-align: right;border: 0; width:10%">
				<button id="chgMbphone" class="btn"><i18n:message key="btn.ghsj"/></button> 
			</td>
			<td style="text-align: right;border: 0; width:3%"></td>
    		<td style="text-align: right;border: 0; width:30%">
    		<button id="cardinfo" class="btn" >卡内信息</button> &nbsp;&nbsp;&nbsp;&nbsp; 
	    	<button id="metinfo" class="btn" >表内信息</button> &nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="write"  class="btn"  >写卡</button> &nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="prt"  class="btn"  disabled="disabled">打印</button> &nbsp;&nbsp;&nbsp;&nbsp;	    	 
	    	</td>
	    </tr>	 
	</table>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="yffalarm_id"/>
	<input type="hidden" id="feeproj_id"/>
	<jsp:include page="../../inc/jsdef.jsp" />
	</div>
	</body>
	<script type="text/javascript">
 		$("#gridbox").height(140);
 		$("#dy_pay" ).height($(window).height());
 		 
 		var ComntUseropDef = {};
 		ComntUseropDef.YFF_DYOPER_CHANGERATE = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_CHANGERATE%>;
 		ComntUseropDef.YFF_DYOPER_PAY = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_PAY%>;
		ComntUseropDef.YFF_DYOPER_GPARASTATE = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_GPARASTATE%>;
		ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER%>;
		ComntUseropDef.YFF_DYOPER_MIS_PAY = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_PAY%>;
		ComntUseropDef.YFF_DYOPER_RESETDOC      = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_RESETDOC%>;
		var SDDef = {};
		SDDef.YFF_OPTYPE_PAY    = <%=SDDef.YFF_OPTYPE_PAY%>;
		SDDef.CARD_OPTYPE_BUY   = <%=SDDef.CARD_OPTYPE_BUY%>;
		SDDef.YFF_FEETYPE_JTFL  = <%=SDDef.YFF_FEETYPE_JTFL%> //阶梯费率             3
		SDDef.YFF_FEETYPE_MIXJT = <%=SDDef.YFF_FEETYPE_MIXJT%>//混合阶梯费率 4
		
		var sel_user_info = "<i18n:message key="baseInfo.sel_user_info"/>";
		var khcg = "<i18n:message key="baseInfo.khcg"/>";
		var khsb = "<i18n:message key="baseInfo.khsb"/>";
		var zbje = "<i18n:message key="baseInfo.zbje"/>";
		var jsje = "<i18n:message key="baseInfo.jsje"/>";
		var jfje = "<i18n:message key="baseInfo.jfje"/>";
		var zje  = "<i18n:message key="baseInfo.zje"/>";
	</script>
</html>
