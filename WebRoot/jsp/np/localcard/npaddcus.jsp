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
		.tabinfo input,select{
			width: 150px;
		}
		input,select{
			font-size: 12px;				
		}
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/np/localcard/npaddcus.js"></script>
		<script  src="<%=basePath%>js/np/dialog/search.js"></script>
		<script  src="<%=basePath%>js/np/mis/mis.js"></script>
		<script  src="<%=basePath%>js/np/tool/setCons.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
	</head>
	<body>
	<table id="tabinfo" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;">基本信息</td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
	    	<button id="btnSearch" class="btn" >检索</button>
	    	</td>
	    	<td style="border:0px; text-align: right;" ><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    </tr>
   		<tr>
	    	<td class="tdr" width="10%">客户编号:</td><td id="userno" width="10%">&nbsp;</td>
	    	<td class="tdr" width="10%">客户名称:</td><td id="username" width="15%">&nbsp;</td>
	     	<td class="tdr" width="10%">客户状态:</td><td id="cus_state" width="10%">&nbsp;</td>
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
	    <tr>
	       
	     </tr>
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>

    	<tr>
    		<td class="td_lable">购电卡信息</td>
    		
    		<td colspan=4 style="padding-left:10px; border: 0px;">
	    	<button id="btnRead"  class="btn" disabled="disabled">读卡</button>
	    	<span id="card_reading"></span>
	    	</td>
	    	<td style="border:0px; text-align: right;" ><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
    	</tr>
	   <tr>
	    	<td class="tdr">购电卡号:</td><td ><input type="text" id="card_no" readonly style="background:#eee;border: 1px solid #808080;"/>
	    	</td> 
	    	<td class="tdr">购电卡状态:</td><td id="card_state">&nbsp;</td>
	    	<td></td><td></td>
	    </tr>
	   	
	    
		<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" style="width: 120px;">缴费信息</td></tr>
	    
	    <tr>
	    	<td class="tdr">缴费金额(元):</td><td class="tdrn"><input id="jfje" /></td>		  		        		    				
	    	<td class="tdr">结算金额(元):</td><td class="tdrn"><input id="jsje" /></td>
	    	<td class="tdr">写卡金额(元):</td><td class="tdrn"><span id="zje"></span></td>
	    </tr>

	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	<td colspan="6" style="text-align: right;border: 0">
    		<button id="cardinfo"  	class="btn" >卡内信息</button>&nbsp;&nbsp;&nbsp;&nbsp; 
	    	<button id="btnNew"  	class="btn" disabled="disabled">开户</button> &nbsp;&nbsp;&nbsp;&nbsp; 
	    	<button id="btnPrt"  	class="btn" disabled="disabled">打印</button>&nbsp;&nbsp;&nbsp;&nbsp; 
	    </td></tr>
	</table>
	<input type="hidden" id="area_id"/>
	<input type="hidden" id="farmer_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="lsremain"/>
	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
	var ComntUseropDef = {};
	ComntUseropDef.YFF_NPOPER_ADDRES 	= <%=com.kesd.comntpara.ComntUseropDef.YFF_NPOPER_ADDRES%>
	//ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER%>;
	//ComntUseropDef.YFF_DYOPER_MIS_PAY 		 = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_PAY%>;
	
	var SDDef		   = {};
	SDDef.STR_AND 						= "<%=SDDef.STR_AND%>";
	SDDef.STR_OR 						= "<%=SDDef.STR_OR%>";
	SDDef.YFF_OPTYPE_ADDRES				= <%=SDDef.YFF_OPTYPE_ADDRES%>;
	SDDef.CARD_OPTYPE_OPEN				= <%=SDDef.CARD_OPTYPE_OPEN%>;
	
	SDDef.YFF_OPTYPE_ADDRES = <%=SDDef.YFF_OPTYPE_ADDRES%>;
	
	SDDef.YFF_FEETYPE_JTFL  = <%=SDDef.YFF_FEETYPE_JTFL%> //阶梯费率             3
	SDDef.YFF_FEETYPE_MIXJT = <%=SDDef.YFF_FEETYPE_MIXJT%>//混合阶梯费率 4
	
	SDDef.NPCARD_OPTYPE_INITPARA	= <%=SDDef.NPCARD_OPTYPE_INITPARA%>;	//不缴费 仅初始化参数
	SDDef.NPCARD_OPTYPE_OPEN		= <%=SDDef.NPCARD_OPTYPE_OPEN%>;		//卡类型 开户
	SDDef.NPCARD_OPTYPE_BUY			= <%=SDDef.NPCARD_OPTYPE_BUY%>;			//买电充值
	SDDef.NPCARD_OPTYPE_REVER		= <%=SDDef.NPCARD_OPTYPE_REVER%>;		//冲正
	
	var sel_user_info = "<i18n:message key="baseInfo.sel_user_info"/>";
	var khcg = "<i18n:message key="baseInfo.khcg"/>";
	var khsb = "<i18n:message key="baseInfo.khsb"/>";
	var yzje = "<i18n:message key="baseInfo.yzje"/>";
	var yzje = "<i18n:message key="baseInfo.yzje"/>";
	var jfje = "<i18n:message key="baseInfo.jfje"/>";
	var zje =  "<i18n:message key="baseInfo.zje"/>";
	

	</script>
	</body>
</html>
