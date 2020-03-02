<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>手工合闸(仅376终端)</title>
		<style type="text/css">
		.tabinfo input, select{
			width: 160px;
			font-size: 12px;				
		}
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/spec/tool/gysghz376.js"></script>
		<script  src="<%=basePath%>js/spec/tool/setCons.js"></script>
		<script  src="<%=basePath%>js/spec/dialog/search.js"></script>
		<script src="<%=basePath%>js/dialog/opdetail.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
	</head>

	<body>
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
	    
    	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr><td class="td_lable">终端信息</td></tr>
   		<tr>
	    	<td class="tdr" width="12%">终端型号:</td><td id="rtu_model" width="17%">&nbsp;</td>
	    	<td class="tdr" width="12%">生产厂家:</td><td id="factory" width="20%">&nbsp;</td>
	     	<td class="tdr" width="12%">倍率:</td><td id="blv">&nbsp;</td>
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
	    
	    <tr>
    	<td colspan="6" style="text-align: right;border: 0px;height:40px;">
	    	<button id="operation" class="btn" disabled="disabled">手工合闸</button>
	    </td></tr>
	    </table>
	    <input type="hidden" id="rtu_id"/>
	    <input type="hidden" id="zjg_id"/>
	<script type="text/javascript">
	
	var sel_type = "<%=request.getParameter("type")%>";
	
	var ComntUseropDef = {};
	ComntUseropDef.YFF_GYCOMM_PAY			= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYCOMM_PAY%>;
	ComntUseropDef.YFF_GYCOMM_CALLPARA		= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYCOMM_CALLPARA%>;
	ComntUseropDef.YFF_GYCOMM_SETPARA		= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYCOMM_SETPARA%>;
	ComntUseropDef.YFF_GYCOMM_CTRL			= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYCOMM_CTRL%>;
	ComntUseropDef.YFF_GYOPER_PROTECT		= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_PROTECT%>;
	
	var ComntProtMsg = {};
	ComntProtMsg.YFF_GY_CALL_KEEP			= <%=com.kesd.comnt.ComntProtMsg.YFF_GY_CALL_KEEP%>;
	ComntProtMsg.YFF_GY_SET_KEEP			= <%=com.kesd.comnt.ComntProtMsg.YFF_GY_SET_KEEP%>;
	ComntProtMsg.YFF_GY_CTRL_KEEPON			= <%=com.kesd.comnt.ComntProtMsg.YFF_GY_CTRL_KEEPON%>;
	ComntProtMsg.YFF_GY_CTRL_KEEPOFF		= <%=com.kesd.comnt.ComntProtMsg.YFF_GY_CTRL_KEEPOFF%>;
	ComntProtMsg.YFF_GY_CTRL_CUT 			= <%=com.kesd.comnt.ComntProtMsg.YFF_GY_CTRL_CUT%>;
	ComntProtMsg.YFF_GY_CTRL_ON 			= <%=com.kesd.comnt.ComntProtMsg.YFF_GY_CTRL_ON%>;
	ComntProtMsg.YFF_GY_CTRL_ALARMON		= <%=com.kesd.comnt.ComntProtMsg.YFF_GY_CTRL_ALARMON%>;
	ComntProtMsg.YFF_GY_CTRL_ALARMOFF		= <%=com.kesd.comnt.ComntProtMsg.YFF_GY_CTRL_ALARMOFF%>;
	
	var ComntDef = {};
	ComntDef.YD_PROTOCAL_FKGD05				= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_FKGD05%>;
	ComntDef.YD_PROTOCAL_GD376				= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_GD376%>;
	ComntDef.YD_PROTOCAL_GD376_2013				= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_GD376_2013%>;
	ComntDef.YD_PROTOCAL_KEDYH				= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_KEDYH%>;
	
	</script>
	</body>
</html>
