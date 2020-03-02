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
		<script  src="<%=basePath%>js/dyjc/localcardExt/dlcalc.js"></script>
		<script  src="<%=basePath%>js/dyjc/localcardExt/dyaddcus.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>
		<script  src="<%=basePath%>js/dyjc/mis/mis.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setConsExt.js"></script>
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
	    	<td class="tdr" width="12%">客户编号:</td><td id="userno" width="15%">&nbsp;</td>
	    	<td class="tdr" width="12%">客户名称:</td><td id="username" width="20%">&nbsp;</td>
	     	<td class="tdr" width="12%">客户状态:</td><td id="cus_state">&nbsp;</td>
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
	    <tr>
	        <td class="tdr">结算客户名:</td><td id="jsyhm">&nbsp;</td>
		    <td class="tdr" id="khfl">客户分类:</td><td id="yhfl" colspan=3>&nbsp;</td>
	     </tr>
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
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
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" style="height:25px;"><i18n:message key="baseInfo.bdxx"/></td>
	    	<td colspan=5 style="padding-left:10px; border: 0px;">
	    	<button id="btnImportBD" class="btn"><i18n:message key="btn.luru"/></button>&nbsp;&nbsp;&nbsp;&nbsp;
	    </td></tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.bdVal"/></td>
	    	<td colspan="5" id="td_bdinf" ></td>
	    </tr>
		<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" style="width: 120px;">缴费信息</td></tr>
	    <tr>
	    	<td class="tdr">预置金额(元):</td><td>
		    	<select id="yzje" name="yzje">
			    	<%
			    	List list = WebConfig.getYzje();
					for (int i = 0; i < list.size(); i++) {
						String[] tmp = (String[])list.get(i);			
						out.println("<option value='"+tmp[0]+"'>"+tmp[1]+"</option>");
					}
					%>
				</select>
	    	</td>		  		        		    				
	    	<td class="tdr">说明:</td><td colspan=3>在电能表开户前预置在电能表内的可使用的用电金额，开户时售电系统自动扣除此部分金额。</td>
	    </tr>
	    <tr>
	    	<td class="tdr">缴费金额(元):</td><td class="tdrn"><input id=jfje /></td>		  		        		    				
	    	<td class="tdr">结算金额(元):</td><td class="tdrn"><input id=jsje /></td>
	    	<td class="tdr">写卡金额(元):</td><td class="tdrn"><span id="zje""></span></td>
	    </tr>
	    <tr>
	    	<td class="tdr">旧表基础电量:</td><td class="tdrn" ><input id="jt_total_zbdl" name=jcdl/></td>
	    	<td class="tdr">缴费电量:</td><td class="tdrn" style="width: 12%;"><span id=buy_dl></span></td>			
	        <td class="tdr">表码差:</td><td class="tdrn" style="width: 12%;"><span id=pay_bmc></span></td>
	    </tr>
	    
	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	<td colspan="6" style="text-align: right;border: 0">
    		<button id="cardinfo"  	class="btn" >卡内信息</button>&nbsp;&nbsp;&nbsp;&nbsp; 
<!--	    	<button id="metinfo"  	class="btn" >表内信息</button> &nbsp;&nbsp;&nbsp;&nbsp;-->
	    	<button id="btnNew"  	class="btn" disabled="disabled">开户</button> &nbsp;&nbsp;&nbsp;&nbsp; 
	    	<button id="btnPrt"  	class="btn" disabled="disabled">打印</button>&nbsp;&nbsp;&nbsp;&nbsp; 
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
	var ComntUseropDef = {};
	ComntUseropDef.YFF_DYOPER_ADDRES 	= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_ADDRES%>
	ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER%>;
	ComntUseropDef.YFF_DYOPER_MIS_PAY 		 = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_PAY%>;
	
	var SDDef		   = {};
	SDDef.STR_AND 				= "<%=SDDef.STR_AND%>";
	SDDef.STR_OR 				= "<%=SDDef.STR_OR%>";
	SDDef.YFF_OPTYPE_ADDRES		= <%=SDDef.YFF_OPTYPE_ADDRES%>;
	SDDef.CARD_OPTYPE_OPEN		= <%=SDDef.CARD_OPTYPE_OPEN%>;
	SDDef.YFF_OPTYPE_ADDRES 	= <%=SDDef.YFF_OPTYPE_ADDRES%>;
	
	SDDef.YFF_FEETYPE_DFL	= <%=SDDef.YFF_FEETYPE_DFL%>;
	SDDef.YFF_FEETYPE_MIX   = <%=SDDef.YFF_FEETYPE_MIX%>;
	SDDef.YFF_FEETYPE_JTFL  = <%=SDDef.YFF_FEETYPE_JTFL%> //阶梯费率             3
	SDDef.YFF_FEETYPE_MIXJT = <%=SDDef.YFF_FEETYPE_MIXJT%>//混合阶梯费率 4

	SDDef.YFF_CACL_TYPE_MONEY   = <%=SDDef.YFF_CACL_TYPE_MONEY%>;
	SDDef.YFF_CACL_TYPE_DL		= <%=SDDef.YFF_CACL_TYPE_DL%>;	

	
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
