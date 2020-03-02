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
				width:120px;
				font-size: 12px;
			}
	        body{
	            overflow:true;
	        }
			
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
		
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>		
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/dyjc/localremote/dypayadd.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>
		<script  src="<%=basePath%>js/dyjc/mis/mis.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/sg186.js"></script>
	</head>
	<body>
	<div style="overflow: auto;width:100%;" id="div_dybjl">
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
		
		<tr><td colspan="6" style="height:5px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>	    
	   	<tr><td class="td_lable"  style="border-bottom: 0"><i18n:message key="baseInfo.gdjl"/></td></tr>	
	</table>
	<table class="gridbox" align="center">
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
	</table> 
	<table id="tabinfo2"  class="tabinfo" align="center">
		<tr id="tr_open1" style="display: none"><td colspan="4" style="height:5px; border: 0px;"></td></tr>
    	<tr id="tr_open2" style="display: none"><td class="td_lable" style="height:25px;"><i18n:message key="baseInfo.bdxx"/></td>
	    	<td colspan=3 style="padding-left:10px; border: 0px;">
	    	   <button id="btnImportBD" class="btn"><i18n:message key="btn.luru"/></button>
	    	</td>
	    </tr>
	    <tr id="tr_open3" style="display: none">
	    	<td class="tdr"><i18n:message key="baseInfo.bdVal"/></td>
	    	<td colspan="7" id="td_bdinf" ></td>
	    </tr>
		<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable" style="width: 120px;">缴费信息</td>  
	    </tr>
	    <tr>
			<td class="tdr">缴费金额(元):</td><td class="tdrn" style="width: 12%;"><input type="text" id=jfje /></td>
			<td class="tdr">追补金额(元):</td><td class="tdrn" style="width: 12%;"><input type="text" id=zbje /></td>			
	        <td class="tdr">结算金额(元):</td><td class="tdrn" style="width: 12%;"><input type="text" id=jsje /></td>
	    	<td class="tdr">总金额(元):</td><td class="tdrn" style="width: 12%;"><span id=zongje></span></td>	
	    </tr>
	    <tr>
		    <td class="tdr" style="width: 15%">购电次数:</td><td class="tdrn" style="width: 11%"><span id="buy_times" ></span></td>
		    <td class="tdr" style="width: 11%">追补电量:</td><td class="tdrn"  colspan="5"><input id="jt_total_zbdl" type="text" /></td>	        	
	  	</tr>
	      <tr><td colspan="8" style="height:5px; border: 0px;"></td></tr>	    
		<tr>
    		<td colspan="8" style="text-align: right;border: 0">
	    	<button id="metinfo"  class="btn">表内信息</button> &nbsp;&nbsp;&nbsp;&nbsp; 
	    	<button id="rewrite"  class="btn"  disabled="disabled">补写缴费</button> &nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="prt"  	  class="btn"  disabled="disabled">打印</button>&nbsp;&nbsp;	    	 
	    </td></tr>	  
	</table>
	
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="feeproj_id"/>
	<input type="hidden" id="yffalarm_id"/>
	
	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
	    $("#div_dybjl").height($(window).height());
 		$("#gridbox").height($(window).height()-$("#tabinfo1").height()-$("#tabinfo2").height()-40);
 		
		var ComntUseropDef = {};	
		ComntUseropDef.YFF_DYOPER_PAY    		 = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_PAY%>;
		ComntUseropDef.YFF_DYOPER_ADDRES    	 = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_ADDRES%>;
		ComntUseropDef.YFF_DYOPER_GPARASTATE	 = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_GPARASTATE%>;
		ComntUseropDef.YFF_DYOPER_REWRITE 		 = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_REWRITE%>;
		ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER%>;
		ComntUseropDef.YFF_DYOPER_MIS_PAY 		 = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_PAY%>;
		
		
		var SDDef = {};
		SDDef.STR_OR				= "<%=SDDef.STR_OR%>";
		SDDef.SUCCESS	 			= "<%=SDDef.SUCCESS%>";
		SDDef.STR_AND 				= "<%=SDDef.STR_AND%>";
		SDDef.STR_OR 				= "<%=SDDef.STR_OR%>";
		SDDef.YFF_CUSSTATE_INIT		= "<%=SDDef.YFF_CUSSTATE_INIT%>";   //初始态    0
		SDDef.YFF_CUSSTATE_PAUSE    = "<%=SDDef.YFF_CUSSTATE_PAUSE%>"   //暂停态    49
	   	SDDef.YFF_CUSSTATE_DESTORY	= "<%=SDDef.YFF_CUSSTATE_DESTORY%>";//销户态    50
		SDDef.YFF_CUSSTATE_NORMAL	= "<%=SDDef.YFF_CUSSTATE_NORMAL%>"; //正常态    1
		
		SDDef.YFF_OPTYPE_ADDRES    	= <%=SDDef.YFF_OPTYPE_ADDRES%>;
		SDDef.YFF_OPTYPE_PAY   	 	= <%=SDDef.YFF_OPTYPE_PAY%>;
 	
		
		var sel_user_info = "<i18n:message key="baseInfo.sel_user_info"/>";
		var khcg = "<i18n:message key="baseInfo.khcg"/>";
		var khsb = "<i18n:message key="baseInfo.khsb"/>";
		var zbje = "<i18n:message key="baseInfo.zbje"/>";
		var jsje = "<i18n:message key="baseInfo.jsje"/>";
		var jfje = "<i18n:message key="baseInfo.jfje"/>";
		var zje = "<i18n:message key="baseInfo.zje"/>";
		
		var	mygrid = new dhtmlXGridObject('gridbox');
		var yff_grid_title ='<i18n:message key="baseInfo.yff.grid_title"/>';
	</script>
	</div>
	</body>
	
</html>
