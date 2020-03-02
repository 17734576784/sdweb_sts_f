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
			.tabinfo input{
				width: 90%;
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
		<script  src="<%=basePath%>js/spec/localbd/gyaddcus.js"></script>
		<script  src="<%=basePath%>js/spec/localbd/bdcalcu.js"></script>
		<script  src="<%=basePath%>js/spec/dialog/search.js"></script>
		<script  src="<%=basePath%>js/spec/tool/setCons.js"></script>
		<script  src="<%=basePath%>js/spec/mis/mis.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
	</head>
	<body>
	<div style="overflow: auto;width:100%;" id="div_scroll">
	<table id="tabinfo" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;">基本信息</td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
	    	<button id="btnSearch" class="btn" >检索</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
	    
    	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr><td class="td_lable">终端信息</td></tr>
   		<tr>
	    	<td class="tdr" width="12%">终端型号:</td><td id="rtu_model" width="17%">&nbsp;</td>
	    	<td class="tdr" width="12%">生产厂家:</td><td id="factory" width="20%">&nbsp;</td>
	     	<td class="tdr" width="12%">倍率:</td><td id="blv"></td>
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
    	<tr><td class="td_lable">控制参数</td><td colspan=2 style="border-top: 0px;border-right: 0px;"></td></tr>
	   <tr>
	    	<td class="tdr" colspan="3" style="text-align: center;">
	    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="x" id="dpfs" style="width: 16px;" /><label for="dpfs">电平方式</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="x" id="mcfs" style="width: 16px;" /><label for="mcfs">脉冲方式</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>
	    	<td class="tdr">脉冲宽度:</td><td><input type="text" id="plus_width" style="width: 80%" /></td>
	    	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="start_yff_flag" style="width: 16px;" />&nbsp;<label for="start_yff_flag">启用预付费</label></td>
	    </tr>
	    
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" style="height:25px;"><i18n:message key="baseInfo.bdxx"/></td>
	    	<td colspan=5 style="padding-left:10px; border: 0px;">
	    	<button id="btnImportBD" class="btn"  disabled="disabled"><i18n:message key="btn.luru"/></button>
	    	</td></tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.bdVal"/></td>
	    	<td style="width:17%;height:25px;" colspan="5" id="td_bdinf" ></td>
	    </tr>
	    
		<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" style="width: 120px;">缴费信息</td></tr>
	    <tr>
	    	<td class="tdr">缴费金额(元):</td><td class="tdrn"><input id="jfje" /></td>
	    	<td class="tdr">追补金额(元):</td><td class="tdrn"><input id="zbje" /></td>
	    	<td class="tdr">结算金额(元):</td><td class="tdrn"><input id="jsje" /></td>
	    </tr>
	    <tr>
	     	<td class="tdr">报警止码:</td><td class="tdrn"><input id="alarm_code" name="alarm_code" /></td>
	     	<td class="tdr">有功追补电量:</td><td class="tdrn"><input id="total_yzbdl" /></td>
	    	<td class="tdr">无功追补电量:</td><td class="tdrn"><input id="total_wzbdl" /></td>
	    </tr>
	    <tr>
	    	<td class="tdr">总金额(元):</td><td class="tdrn"><span id="zje"></span></td>
	    	<td class="tdr">购电量:</td><td class="tdrn"><span id="buy_dl"></span></td>
	    	<td class="tdr">表码差:</td><td class="tdrn"><span id="pay_bmc"></span></td>
	    </tr>
	    <tr>
	    	<td class="tdr">断电止码:</td><td class="tdrn"><span id="shutdown_bd"></span></td>
	    	<td colspan="4" align="right">
	    	<button id="rtuInfo"  	class="btn" disabled="disabled">终端内信息</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnNew"  	class="btn"  disabled="disabled">开户</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnPrt"  	class="btn" disabled="disabled">打印</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>
	    </tr>
	</table>
	</div>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="zjg_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="feeproj_id"/>
	<input type="hidden" id="yffalarm_id"/>
	<input type="hidden" id="buy_times"/>
	<input type="hidden" id="alarm_val1"/>
	<input type="hidden" id="alarm_val2"/>
	<input type="hidden" id="feeproj_reteval"/>
	
	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
	var ComntUseropDef = {};
	ComntUseropDef.YFF_GYOPER_ADDCUS 	 = <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_ADDCUS%>;
	ComntUseropDef.YFF_GYOPER_GPARASTATE = <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_GPARASTATE%>;
	ComntUseropDef.YFF_GYCOMM_PAY 		 = <%=com.kesd.comntpara.ComntUseropDef.YFF_GYCOMM_PAY%>;
	ComntUseropDef.YFF_GYOPER_MIS_QUERYPOWER= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_MIS_QUERYPOWER%>;
	ComntUseropDef.YFF_GYOPER_MIS_PAY 		= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_MIS_PAY%>;
	
	var ComntDef = {};
	ComntDef.YD_PROTOCAL_NULL			= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_NULL%>;
	ComntDef.YD_PROTOCAL_FKGD05			= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_FKGD05%>;
	ComntDef.YD_PROTOCAL_GD376			= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_GD376%>;
	ComntDef.YD_PROTOCAL_GD376_2013		= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_GD376_2013%>;
	ComntDef.YD_PROTOCAL_KEDYH			= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_KEDYH%>;
		
	var SDDef		   		= {};
	SDDef.STR_AND 			= "<%=SDDef.STR_AND%>";
	SDDef.STR_OR 			= "<%=SDDef.STR_OR%>";
	SDDef.YFF_FEETYPE_DFL	= "<%=SDDef.YFF_FEETYPE_DFL%>";
	SDDef.YFF_OPTYPE_ADDRES = <%=SDDef.YFF_OPTYPE_ADDRES%>;
	
	$("#div_scroll").height($(window).height());
	var sel_user_info = "<i18n:message key="baseInfo.sel_user_info"/>";
	var khcg = "<i18n:message key="baseInfo.khcg"/>";
	var khsb = "<i18n:message key="baseInfo.khsb"/>";
	var yzje = "<i18n:message key="baseInfo.yzje"/>";
	var yzje = "<i18n:message key="baseInfo.yzje"/>";
	var jfje = "<i18n:message key="baseInfo.jfje"/>";
	var zje = "<i18n:message key="baseInfo.zje"/>";

	</script>
	</body>
</html>
