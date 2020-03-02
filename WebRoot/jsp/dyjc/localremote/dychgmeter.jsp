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
			input{
				font-size: 12px;
				width: 100px;
				/*text-align:right;*/
			}	
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>		
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/dateFormat.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/dyjc/localremote/dychgmeter.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>	
		<script  src="<%=basePath%>js/dyjc/dialog/sg186.js"></script>	
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>
		<script  src="<%=basePath%>js/dyjc/mis/mis.js"></script>	

	</head>
	<body>
	<div id="div_hb" style="overflow: auto;">
	<table id="tabinfo1" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:5px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;"><i18n:message key="baseInfo.jbxx"/></td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;"><button id="btnSearch" class="btn" ><i18n:message key="btn.jiansuo"/></button></td>
	    	<td style="border:0px; text-align: right;"><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    </tr>
   		<tr>
	    	<td class="tdr" style="width: 150px"><i18n:message key="baseInfo.yhbh"/></td><td id="userno" style="width: 17%">&nbsp;</td>
	    	<td class="tdr" width="10%"><i18n:message key="baseInfo.yhmc"/></td><td id="username" style="width: 17%">&nbsp;</td>
	     	<td class="tdr" width="10%"><i18n:message key="baseInfo.yhzt"/></td><td id="cus_state">&nbsp;</td>
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
		
		<tr><td colspan="6" style="height:8px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>	    
	   	<tr><td class="td_lable"  style="border-bottom: 0"><i18n:message key="baseInfo.gdjl"/></td></tr>	
	</table>
	<table class="gridbox" align="center">
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px; width:100%"></div></td></tr>
	</table> 
	<table  id="tabinfo6" class="tabinfo" align="center">
		<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
		<tr>
    	   	<td class="td_lable" style="width: 150px;">表底信息</td>
    	   	<td colspan=5 style="padding-left:10px; border: 0px;">
	    	<button id="rtnBDOld" class="btn"  disabled="disabled">旧表录入</button>&nbsp;&nbsp;
	    	<button id="rtnBDNew" class="btn"  disabled="disabled">新表录入</button>
	    	</td>	  
	    </tr>
	    <tr>
	    	<td class="tdr">旧表信息:</td>
	    	<td id="bd_old" colspan="5"></td>
	    </tr>
	    <tr>
	    	<td class="tdr">新表信息:</td>
	    	<td id="bd_new" colspan="5"></td>
	    </tr>
		<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable">换表信息</td>
	    </tr>
		<tr>
	     	<td class="tdr" style="width: 150px"><i18n:message key="baseInfo.ghsj"/></td><td width="17%"><input id=ghsj style="width: 99%" disabled="disabled" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日 HH时mm分',isShowClear:'false'});"/></td>
		    <td class="tdr" style="width: 150px"><i18n:message key="baseInfo.ghlx"/></td><td colspan="3"><select style="width:200px;" id="ghlx" disabled="disabled"><option value=<%=SDDef.YFF_CHGMETER_MT%>><i18n:message key="baseInfo.ghdb"/></option><option value=<%=SDDef.YFF_CHGMETER_CT%>><i18n:message key="baseInfo.ghct"/></option></select></td>
<%--	    	<td class="tdr" style="width: 150px"><i18n:message key="baseInfo.ghdb"/></td><td><select style="width:50%;" id="ghdb" disabled="disabled"></select></td>			--%>
	    </tr>
	   	<tr id=tabinfo3>
	 		<td class="tdr"><i18n:message key="baseInfo.newCTfz"/></td><td class="tdrn" style="border-top: 0"><input id=ctfz style="width: 99%"/></td>
	    	<td class="tdr"><i18n:message key="baseInfo.newCTfm"/></td><td class="tdrn" style="border-top: 0; width:200px"><input id=ctfm style="width: 99%"/></td>
	  		<td class="tdr" style="width:250px;"><i18n:message key="baseInfo.newCTbb"/></td><td class="tdrn"><span id="ctbb"></span></td>		  
	    </tr>
		<tr id=tabinfo4>
	        <td class="tdr"><i18n:message key="baseInfo.syje"/><i18n:message key="baseInfo.yuan"/></td><td class="tdrn" style="border-top: 0" ><input type="text" id=zbje style="width: 99%"/></td>
			<td class="tdr"><i18n:message key="baseInfo.jfje"/><i18n:message key="baseInfo.yuan"/></td><td class="tdrn" style="border-top: 0; width:200px" ><input type="text" id=jfje style="width: 99%"/></td>			
	    	<td class="tdr" style="width:250px;"><i18n:message key="baseInfo.zongje"/><i18n:message key="baseInfo.yuan"/></td><td class="tdrn" style="border-top: 0"><span id=zongje></span></td>	
	    </tr>
	  	<tr>
	    	<td class="tdr" colspan=6 style="border: 0px;">
	  			<input type="checkbox" name="chkRemote" style="width: 25px;" checked disabled/><i18n:message key="baseInfo.chk.yccz"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  			<button id="btnReadMeter"  class="btn" disabled="disabled"><i18n:message key="btn.ycdb"/></button> &nbsp;&nbsp;&nbsp;&nbsp;
				<button id="btnChange"  class="btn" disabled="disabled"><i18n:message key="btn.genghuan"/></button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  			<button  id="btnPrt"  class="btn" disabled="disabled"><i18n:message key="btn.dayin"/></button>
  			</td>	  				  
	    </tr>    
	
	</table>
	</div>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="ghdb"/>
		
	<script type="text/javascript">
		$("#div_hb").height($(window).height());
 		$("#gridbox").height($(window).height()-$("#tabinfo1").height()-$("#tabinfo6").height()+20);
 		var	mygrid = new dhtmlXGridObject('gridbox');
 		
	 	var ComntUseropDef = {};
		ComntUseropDef.YFF_DYOPER_CHANGEMETER = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_CHANGEMETER%>;
		ComntUseropDef.YFF_DYCOMM_ADDRES = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYCOMM_ADDRES%>;
		ComntUseropDef.YFF_DYCOMM_SETPARA =<%=com.kesd.comntpara.ComntUseropDef.YFF_DYCOMM_SETPARA%>
		var ComntProtMsg = {};
		ComntProtMsg.YFF_DY_SET_CT = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_SET_CT%>;
		var yff_grid_title ='<i18n:message key="baseInfo.yff.grid_title"/>';
	 	
		ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER%>;
		ComntUseropDef.YFF_DYOPER_MIS_PAY 		 = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_PAY%>;
	</script>
	</body>
</html>
