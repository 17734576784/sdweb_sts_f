<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<i18n:bundle baseName="<%=SDDef.BUNDLENAME%>" id="bundle" locale="<%=WebConfig.app_locale%>" scope="application" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>My JSP 'gyjf.jsp' starting page</title>
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
	<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
	<script  src="<%=basePath%>js/common/modalDialog.js"></script>
	<script  src="<%=basePath%>js/common/number.js"></script>
	<script  src="<%=basePath%>js/common/jsonString.js"></script>
	<script  src="<%=basePath%>js/common/def.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script  src="<%=basePath%>js/spec/main/gyrestore.js"></script>
	<script  src="<%=basePath%>js/spec/tool/setCons.js"></script>
	<script  src="<%=basePath%>js/spec/dialog/search.js"></script>
	<script  src="<%=basePath%>js/validate.js"></script>
	
  </head>
  
  <body>
  <table id="tabinfo" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;">基本信息</td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
	    	<button id="btnSearch" class="btn">检索</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>
	    	<td style="border:0px; text-align: right;" ><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    	
	    </tr>
        <tr>
	    	<td class="tdr" width="140">客户编号:</td><td id="userno"  width="17%">&nbsp;</td>
	    	<td class="tdr" width="12%">客户名称:</td><td id="username" width="20%">&nbsp;</td>
	     	<td class="tdr" width="12%">客户状态:</td><td id="cus_state">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">联系电话:</td><td id="tel">&nbsp;</td>
	   		<td class="tdr">客户地址:</td><td colspan=3 id="useraddr">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">费控单元:</td><td id="fee_unit">&nbsp;</td>
	    	<td class="tdr"></td><td>&nbsp;</td>
	    	<td class="tdr" width="12%"></td><td>&nbsp;</td>
	    </tr>
	    
<!--    <tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr><td class="td_lable">终端信息</td></tr>
 -->	    
   		<tr>
	    	<td class="tdr">终端型号:</td><td id="rtu_model">&nbsp;</td>
	    	<td class="tdr">生产厂家:</td><td id="factory">&nbsp;</td>
	     	<td class="tdr">倍率:</td><td id="blv">&nbsp;</td>
	    </tr>
<!--	    
	   <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
       <tr><td class="td_lable">费控信息</td></tr>
 -->	    
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
	    <tr><td colspan="6" style="height:8px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>	    
	   	<tr><td class="td_lable"  style="border-bottom: 0"><i18n:message key="baseInfo.gdjl"/></td></tr>
	    </table>
	<table class="gridbox" align="center">
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
	</table> 
	
	<table id="tabinfo2" class="tabinfo" align="center">
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" style="height:25px;"><i18n:message key="baseInfo.bdxx"/></td>
    	<td style="padding-left:10px; border: 0px;" colspan='5'>
    	<button id="btnImportBD" class="btn"  ><i18n:message key="btn.luru"/></button>
    	</td></tr>
	    <tr>
	    	<td style="width:140px" class="tdr"><i18n:message key="baseInfo.bdVal"/></td>
	    	<td colspan="5" id="td_bdinf" ></td>
	    </tr>
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable">缴费信息</td></tr>
	    <tr>
	    	<td class="tdr" width="12%">缴费金额(元):</td><td class="tdrn" width="20%"><input id="jfje" name="jfje"/></td>	    
	    	<td class="tdr" width="140">追补金额(元):</td><td class="tdrn" width="17%"><input id="zbje" name="zbje"/></td>		  		        		    				
	    	<td class="tdr">结算金额(元):</td><td class="tdrn"><input id="jsje" name=jsje/></td>
    	</tr>
	    <tr>
	    	<td class="tdr">总金额(元):    </td><td class="tdrn"><input id="zje" name="zje" readonly/></td>
	    	<td class="tdr">报警金额1(元):</td><td class="tdrn"><input id="yffalarm_alarm1"/></td>	    	
	    	<td class="tdr">报警金额2(元):</td><td class="tdrn"><input id="yffalarm_alarm2"/></td>
	    </tr>
	    <tr>
	     	<td></td><td></td>
	     	<td class="tdr">有功追补电量:</td><td class="tdrn"><input id="total_yzbdl"/></td>
	    	<td class="tdr">无功追补电量:</td><td class="tdrn"><input id="total_wzbdl"/></td>
	    	
	    </tr>	    
	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	<td colspan="6" style="text-align: right;border: 0">
	    	<button id="btnRecover"  	class="btn"  disabled="disabled">恢复</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnPrt"  	class="btn" disabled="disabled">打印</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    </td></tr>
	</table>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="zjg_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="feeproj_id"/>
	<input type="hidden" id="yffalarm_id"/>
	<input type="hidden" id="buy_times"/>
	<jsp:include page="../../inc/jsdef.jsp"></jsp:include>
	<script type="text/javascript">
	$("#gridbox").height($(window).height()-$("#tabinfo").height()-$("#tabinfo2").height()-5);		
	var	mygrid = new dhtmlXGridObject('gridbox');
	
	var ComntUseropDef = {};
	ComntUseropDef.YFF_GYOPER_RESTART			= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_RESTART%>;
	
	</script>
  </body>
</html>
