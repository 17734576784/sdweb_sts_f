<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'clear_eve.jsp' starting page</title>

	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
	<style type="text/css">
	input{
		font-size: 12px;
		width:120px;
	}
	</style>
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/modalDialog.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/jsonString.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/dyjc/dialog/search.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/dyjc/tool/setCons.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/dyjc/tool/clear_eve.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/loading.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/def.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
	<script type="text/javascript">
  		var ComntUseropDef = {};
		ComntUseropDef.YFF_DYCOMM_CLEAR = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYCOMM_CLEAR%>;//低压清空电量、需量、事项
		ComntUseropDef.YFF_DYCOMM_CTRL	= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYCOMM_CTRL%>;//控制
		var ComntProtMsg = {}; 
		ComntProtMsg.YFF_DY_CLEAR_EVENT = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_CLEAR_EVENT%>;//清事项标识
		ComntProtMsg.YFF_DY_CLEAR_DL 	= <%=com.kesd.comnt.ComntProtMsg.YFF_DY_CLEAR_DL%>;//清电量	
		ComntProtMsg.YFF_DY_CLEAR_XL 	= <%=com.kesd.comnt.ComntProtMsg.YFF_DY_CLEAR_XL%>;//清需量		
  	</script>
  </head>
   
  <body style="overflow:hidden";>
  <div id='dy_clear' style="overflow: auto;">
  	<table id="tabinfo1" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:5px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;"><i18n:message key="baseInfo.jbxx"/></td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
	    	<button id="btnSearch" class="btn" ><i18n:message key="btn.jiansuo"/></button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>
	    	<td style="border:0px; text-align: right;" ><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    </tr>
   		<tr>
	    	<td class="tdr" style="width: 10%"><i18n:message key="baseInfo.yhbh"/></td><td id="userno" style="width: 10%">&nbsp;</td>
	    	<td class="tdr" style="width: 10%"><i18n:message key="baseInfo.yhmc"/></td><td id="username" style="width: 15%">&nbsp;</td>
	     	<td class="tdr" style="width: 10%"><i18n:message key="baseInfo.yhzt"/></td><td id="cus_state" style="width: 10%">&nbsp;</td>
	    </tr>
	   <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.lxdh"/></td><td id="tel">&nbsp;</td>
	   		<td class="tdr"><i18n:message key="baseInfo.yhdz"/></td><td colspan=3 id="useraddr">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.bh"/></td><td id="esamno">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.yffblx"/></td><td id="yffmeter_type_desc">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.sccj"/></td><td id="factory">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.bl"/></td><td id="blv">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.jxfs"/></td><td id="wiring_mode">&nbsp;</td>
	    	<td>&nbsp;</td><td>&nbsp;</td>
	     </tr>
	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" ><i18n:message key="baseInfo.fkxx"/></td></tr>
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
		<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
		<tr><td class="td_lable" style="height:25px;">清空事项列表</td></tr>
	</table>
	
	<table id="tabinfo2"  class="gridbox" align="center">
		<tr>
		  <td style="padding: 0 3px 0 0;" >
		  	<div id=gridbox1 style="border:0px;width:100%"></div>
		  </td> 
   		  <td>
   		 	<div id=gridbox2 style="width: 100%;"></div>
   		  </td>
   		  <td>
   		 	<div id=gridbox3 style="width: 100%; "></div>
   		  </td>
   		 </tr>
	</table>
    <div id="btn" class="btn" style="text-align: right; margin-right: 1%; margin-top: 10px">
    	<button id="clearXL" class="btn" disabled>清空需量</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	<button id="clearDL" class="btn" disabled>清空电量</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	<button id="clearEvent" class="btn" disabled>清空事项</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </div>
    <br>
   </div>
   <script type="text/javascript">
   	$("#dy_clear").height($(window).height());
   	$("#gridbox1").height(($(window).height()-$("#tabinfo1").height()-$("#btn").height()) - 30);
   	$("#gridbox2").height(($(window).height()-$("#tabinfo1").height()-$("#btn").height()) - 30);
   	$("#gridbox3").height(($(window).height()-$("#tabinfo1").height()-$("#btn").height()) - 30);
   	
   	var mygridbox1 = new dhtmlXGridObject('gridbox1');
   	var mygridbox2 = new dhtmlXGridObject('gridbox2');
   	var mygridbox3 = new dhtmlXGridObject('gridbox3');
   	
   </script>
  </body>
</html>
