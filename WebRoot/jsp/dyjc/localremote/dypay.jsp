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
			body{
			  margin:0 0 0 0;
			  overflow:hidden;
			}	
			input,select{
				font-size: 12px;				
			}	
			.tabinfo input{
				width: 99%;
			}	
		</style>
	
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
  		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
 
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	  	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
  		<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
  		<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>		
		<script  src="<%=basePath%>js/validate.js"></script>
		<script  src="<%=basePath%>js/dyjc/localremote/dypay.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>	
		<script  src="<%=basePath%>js/dyjc/dialog/sg186.js"></script>	
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>	
		<script  src="<%=basePath%>js/dyjc/mis/mis.js"></script>

	</head>
	<body>
	<div id="dy_pay" style="overflow: auto">
	<table id="tabinfo1" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:5px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;"><i18n:message key="baseInfo.jbxx"/></td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
	    	<button id="btnSearch" class="btn" ><i18n:message key="btn.jiansuo"/></button>&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
	    	<td class="tdr">结算客户名:</td><td id="jsyhm">&nbsp;</td>
	    	<td class="tdr" id="khfl">客户分类:</td><td id="yhfl">&nbsp;</td>
	    	<td class="tdr"></td><td>&nbsp;</td>
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
    	<tr><td class="td_lable"><i18n:message key="baseInfo.fkxx"/></td></tr>
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
	    
	    <tr id="jt_info"  style="display: none"><td colspan="6" style="height:5px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>
	    <tr id="jt_info1" style="display: none"><td class="td_lable" >阶梯信息</td></tr>
	    <tr id="jt_info2" style="display : none">
	    	<td class="tdr">阶梯剩余金额:   </td><td id="now_remain1">&nbsp;</td>
	    	<td class="tdr">电表剩余金额:</td><td id="now_remain2">&nbsp;</td>
	    	<td class="tdr">阶梯累计用电量:</td><td id="jt_total_dl">&nbsp;</td>    	
	    </tr>
		
		<tr><td colspan="6" style="height:5px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>	    
	   	<tr><td class="td_lable"  style="border-bottom: 0"><i18n:message key="baseInfo.gdjl"/></td></tr>	
	</table>
	<table class="gridbox" align="center">
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
	</table> 
	<table  id="tabinfo2" class="tabinfo" align="center">
		<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable" ><i18n:message key="baseInfo.jfxx"/></td>	  
	    </tr>
	    <tr>
		    <td class="tdr" style="width: 15%"><i18n:message key="baseInfo.gdcs"/></td><td class="tdrn" style="width: 11%;"><span id=buy_times ></span></td>
		 	<td class="tdr" style="width: 15%"><i18n:message key="baseInfo.ljgdje"/><i18n:message key="baseInfo.yuan"/></td><td class="tdrn" style="width: 11%"><span id="total_gdz"></span></td>
	    	<td class="tdr" style="width: 15%"><i18n:message key="baseInfo.scjfje"/><i18n:message key="baseInfo.yuan"/></td><td class="tdrn" style="width: 11%;"><span id="pay_money"></span></td>
	  	  	<td class="tdr" style="width: 11%"><i18n:message key="baseInfo.syje"/><i18n:message key="baseInfo.yuan"/></td><td class="tdrn" style="width: 11%;"><span id="now_remain"></span></td>
	  	</tr>	  
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.jfje"/><i18n:message key="baseInfo.yuan"/></td><td class="tdrn"><input type="text" id=jfje /></td>
	        <td class="tdr"><i18n:message key="baseInfo.zbje"/><i18n:message key="baseInfo.yuan"/></td><td class="tdrn" ><input type="text" id=zbje /></td>
			<td class="tdr"><i18n:message key="baseInfo.jsje"/><i18n:message key="baseInfo.yuan"/></td><td class="tdrn"><input type="text" id=jsje /></td>			
	    	<td class="tdr"><i18n:message key="baseInfo.zongje"/><i18n:message key="baseInfo.yuan"/></td><td class="tdrn"><span id=zongje style="width: 100%"></span></td>	
	    </tr>
	    <!-- 
		<tr><td colspan=8 style="text-align: right;border: 0">
			<input type="checkbox" name="chkRemote" style="width: 25px;" checked disabled/><i18n:message key="baseInfo.chk.yccz"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 	<button id="btnReadMeter"  class="btn" disabled="disabled"><i18n:message key="btn.ycdb"/></button> &nbsp;&nbsp;&nbsp;&nbsp;
			<button id="chgMbphone" class="btn"><i18n:message key="btn.ghsj"/></button> &nbsp;&nbsp;&nbsp;&nbsp; 
			<button  id="btnPay"  class="btn" disabled="disabled"><i18n:message key="btn.jiaofei"/></button> &nbsp;&nbsp;&nbsp;&nbsp; 
		 	<button  id="btnPrt"  class="btn" disabled="disabled"><i18n:message key="btn.dayin"/></button> 
		</td></tr>
		 -->    
	</table>
	<table id="tabbutton" class="tabinfo" align="center">
		<tr>
			<td style="text-align: right;border: 0; width:57%"></td>
			<td style="text-align: right;border: 0; width:10%">
				<button id="chgMbphone" class="btn"><i18n:message key="btn.ghsj"/></button> &nbsp;&nbsp;&nbsp;&nbsp; 
			</td>
			<td style="text-align: right;border: 0; width:3%"></td>
    		<td style="text-align: right;border: 0; width:30%">
    		<input type="checkbox" name="chkRemote" style="width: 25px;" checked disabled/><i18n:message key="baseInfo.chk.yccz"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 	<button id="btnReadMeter"  class="btn" disabled="disabled"><i18n:message key="btn.ycdb"/></button> &nbsp;&nbsp;&nbsp;&nbsp;
			<button  id="btnPay"  class="btn" disabled="disabled"><i18n:message key="btn.jiaofei"/></button> &nbsp;&nbsp;&nbsp;&nbsp; 
		 	<button  id="btnPrt"  class="btn" disabled="disabled"><i18n:message key="btn.dayin"/></button>     	 
	    	</td>
	    </tr>	 
	</table>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	
	<script type="text/javascript">
 		$("#gridbox").height($(window).height()-$("#tabinfo1").height()-$("#tabinfo2").height()-$("#tabbutton").height()-50);
 		var	mygrid = new dhtmlXGridObject('gridbox');		
	    $("#dy_pay").height($(window).height());
	</script>
	</div>
	</body>
	
	
	<script type="text/javascript">
	var ComntUseropDef = {};
	ComntUseropDef.YFF_DYOPER_PAY = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_PAY%>;
	ComntUseropDef.YFF_DYCOMM_PAY = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYCOMM_PAY%>;
	ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER%>;
	ComntUseropDef.YFF_DYOPER_MIS_PAY 		 = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_PAY%>;
	ComntUseropDef.YFF_DYOPER_RESETDOC      = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_RESETDOC%>;
	var yff_grid_title ='<i18n:message key="baseInfo.yff.grid_title"/>';
	
	var SDDef = {};
	SDDef.YFF_OPTYPE_PAY    = <%=SDDef.YFF_OPTYPE_PAY%>;
	SDDef.YFF_FEETYPE_JTFL  = <%=SDDef.YFF_FEETYPE_JTFL%> //阶梯费率             3
	SDDef.YFF_FEETYPE_MIXJT = <%=SDDef.YFF_FEETYPE_MIXJT%>//混合阶梯费率 4
	</script>
</html>
