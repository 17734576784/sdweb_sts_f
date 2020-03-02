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
		<title>My JSP 'ggfl.jsp' starting page</title>
		<style type="text/css">
			input,select{
				font-size: 12px;
				width: 100px;;				
			}			  
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/spec/localbd/gychgrate.js"></script>
		<script  src="<%=basePath%>js/spec/localbd/bdcalcu.js"></script>
		<script  src="<%=basePath%>js/spec/tool/setCons.js"></script>
		<script  src="<%=basePath%>js/spec/dialog/search.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
		<script  src="<%=basePath%>js/common/DateFun.js"></script>
		
	</head>

	<body>
	<div style="overflow: auto;width:100%;" id="div_ggfl">
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
	    	<td class="tdr">&nbsp;</td><td>&nbsp;</td>
	    	<td class="tdr">&nbsp;</td><td>&nbsp;</td>
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
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable">控制参数</td><td colspan=2 style="border-top: 0px;border-right: 0px;"></td></tr>
	   <tr>
	    	<td class="tdr" colspan="3" style="text-align: center;">
	    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="x" id="dpfs" style="width: 16px;"/><label for="dpfs">电平方式</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="x" id="mcfs" style="width: 16px;"/><label for="mcfs">脉冲方式</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>
	    	<td class="tdr">脉冲宽度:</td><td><input type="text" id="plus_width" style="width: 80%" /></td>
	    	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="start_yff_flag" style="width: 16px;" />&nbsp;<label for="start_yff_flag">启用预付费</label></td>
	    </tr>
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" style="height:25px;">表底信息</td>
	    	<td colspan=3 style="padding-left:10px; border: 0px;">
	    	<button id="btnImportBD" class="btn"  disabled="disabled">表底录入</button>
	    	</td>
	    	</tr>
	    <tr>
	    	<td class="tdr">表底信息:</td><td id="bd" colspan="5"></td>
	    </tr>
	    <tr><td colspan="6" style="height:8px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>	    
	   	<tr><td class="td_lable"  style="border-bottom: 0"><i18n:message key="baseInfo.gdjl"/></td></tr>
	    </table>
	    
	<table class="gridbox" align="center">
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%;height:135px;"></div></td></tr>
	</table>
	<table id="tabinfo2" class="tabinfo" align="center">
		<tr><td colspan="10" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" style="width: 140px;">缴费信息</td></tr>
<!--    	<tr><td colspan=6 style="border: 0px;">-->
<!--    	<table class="tabinfo" style="width: 100%;" cellpadding="0" cellspacing="0">-->
    	<tr><td class="tdr">断电表底:</td><td class="tdrn" colspan="2"><input type="text" id="last_shutdown_bd" style="width:100%"/></td><td colspan="7"></td></tr>
    	<tr>
	    	<td class="tdr" width="10%">剩余金额(元):</td><td width="10%" class="tdrn"><span id="syje"></span></td>
	    	<td class="tdr" width="10%">购电量:</td><td width="10%" class="tdrn"><span id="buy_dl"></span></td>
	    	<td class="tdr" width="10%">表码差:</td><td width="10%" class="tdrn"><span id="bmc"></span></td>
	    	<td class="tdr" width="10%">断电止码:</td><td width="10%" class="tdrn"><span id="shutdown_bd"></span></td>
	    	<td class="tdr" width="10%">报警止码:</td><td class="tdrn"><input id="alarm_bd" /></td>
	    </tr>
<!--    	</table>-->
<!--    	</td></tr>-->
	    <tr><td colspan="10" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" style="width: 120px;">更换信息</td></tr>
    	<tr>
	    	<td class="tdr"  >更换时间:</td><td colspan=2>
	    	<input id="chg_time" class="roinput" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日 HH时mm分',maxDate:'%y-%M-%d %H:%m',isShowClear:'false'});" style="width:100%;"/>
	    	</td>
	    	<td class="tdr"  >新费率方案:</td><td class="tdrn" colspan=2 >
	    	<select id="new_feeproj" style="width:100%;"></select>
	    	</td>
	    	<td class="tdr" >方案描述:</td><td id="new_feeproj_detail" colspan=3></td>
	    </tr>
	    
	    <tr><td colspan="10" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	<td colspan="10" style="text-align: right;border: 0">
	    	<button id="rtuInfo" class="btn" disabled="disabled">终端内信息</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="chgfl"  class="btn"  disabled="disabled">更换</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="savefl"  class="btn"  disabled="disabled">保存</button>
	    </td></tr>
	</table>
	</div>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="zjg_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="feeproj_id"/>
	<input type="hidden" id="yffalarm_id"/>
	<input type="hidden" id="buy_times"/>
	<input type="hidden" id="feeproj_reteval"/>
	<input type="hidden" id="zbd"/><input type="hidden" id="jbd"/><input type="hidden" id="fbd"/><input type="hidden" id="pbd"/><input type="hidden" id="gbd"/>
	
	<jsp:include page="../../inc/jsdef.jsp"></jsp:include>
	<script type="text/javascript">
	var ComntUseropDef = {};
	ComntUseropDef.YFF_GYCOMM_PAY			= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYCOMM_PAY%>;
	ComntUseropDef.YFF_GYOPER_CHANGERATE	= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_CHANGERATE%>;
	ComntUseropDef.YFF_GYOPER_PAY			= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_PAY%>;
	var SDDef		   = {};
	SDDef.STR_AND 						= "<%=SDDef.STR_AND%>";
	SDDef.STR_OR 						= "<%=SDDef.STR_OR%>";
	$("#div_ggfl").height($(window).height());
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
