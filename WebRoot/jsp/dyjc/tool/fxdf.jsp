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
			input,select{
				font-size: 12px;	
			}			  
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>		
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/fxdf.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/comm.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		
	</head>
	<body>
	<table id="tabinfo" class="tabinfo" align="center">
	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr>
	    	<td colspan=6 style="padding-left:10px; border: 0px;">
	    	<i18n:message key="baseInfo.yyd"/><select id="org" style="width:120px;"></select>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<i18n:message key="baseInfo.jzq"/><select id="rtu" style="width:120px;"><option value=-1>所有</option></select>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<i18n:message key="baseInfo.jstj"/><select id="searchType" style="width:80px;">
	    	<option value="1"><i18n:message key="baseInfo.zdmc"/></option>
	    	<option value="2"><i18n:message key="baseInfo.yhhh"/></option>
	    	<option value="3">客户名称</option>
	    	<option value="4">联系电话</option>
	    	<option value="5" selected>ESAM表号</option>
	    	</select>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<i18n:message key="baseInfo.jsnr"/><input id="searchContent" />&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnSearch" class="btn"><i18n:message key="btn.jiansuo"/></button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>
	    </tr>
   	</table>
	<DIV id=gridbox style="width: 100%;"></DIV>
	<table id="tabinfo1" class="tabinfo" align="center">
    	 <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable"><i18n:message key="baseInfo.yhxx"/></td></tr>
	   
   		<tr>
	    	<td class="tdr" style="width: 12%;">客户名称:</td><td style="width: 20%;" id="yhmc" ></td>
	       	<td class="tdr" style="width: 12%;">阶梯周期切换:</td><td style="width: 13%;" id="jtzqqh"></td>
	     	<td class="tdr" style="width: 12%;">阶梯切换日期:</td><td style="width: 13%;" id="jtqhrq"></td>
	    </tr>
	   
   		<tr>
	    	<td class="tdr">居民户号:</td><td id="jmhh"></td>
	       	<td class="tdr">发行起始日期:</td><td id="fxqsrq"></td>
	       	<td class="tdr">阶梯切换执行时间:</td><td style="width: 13%;" id="jtqhzxsh" ></td>	    	
	    </tr>
	    
   		<tr>
	    	<td class="tdr">抄表日:</td><td id="cbr"></td>
	     	<td class="tdr">发行电费年月:</td><td id="fxdfny"></td>	    	
	    	<td class="tdr">结算补差日期:</td><td id="jsbcrq"></td>	       	
	    </tr>
		<tr>
	       	<td class="tdr">主站算费标志:</td><td id="zzsfbz"></td>
	       	<td class="tdr">发行数据日期:</td><td id="fxsjrq"></td>
	       	<td class="tdr"></td><td></td>	
	  	</tr>    
   		<tr>
   			<td class="tdr">发行电费标志:</td><td id="fxdf_flag"></td>
	        <td class="tdr">发行算费时间:</td><td id="fxsfsj"></td>	    	
			<td class="tdr"></td><td></td>	    	
	    </tr>
	    <tr><td colspan="8" style="height:5px;border: 0"></td></tr>
	    <tr>
	       	<td colspan="8" style="text-align: right;border: 0">
	       	<i18n:message key="baseInfo.gglx"/>&nbsp;<select id="opType" style="width:120px;">
	    	<option value=1 >重新发行电费</option>
	    	<option value=2 >重新阶梯切换</option>
	    	</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	       	<button id="btnOpt" class="btn">操作</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>	     		    	
	    </tr>
	    <tr>
	    	     		    	
	    </tr>
	    
	</table>
	
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	
	<script>
	$("#gridbox").height($(window).height()-$("#tabinfo").height()-$("#tabinfo1").height()-10);
	var	mygrid = new dhtmlXGridObject('gridbox');	
	//var yff_grid_title ='<i18n:message key="baseInfo.tsxz.grid_title"/>';
	var yff_grid_title ="序号,终端名称,客户名称,抄表日,主站算费标志,发行电费标志,阶梯周期切换,发行电费起始日期,发行电费年月,发行电费数据日期,发行电费算费时间,阶梯切换日期,阶梯切换执行时间,结算补差日期";
	var ComntUseropDef = {};
	ComntUseropDef.YFF_DYOPER_UDPATESTATE= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_UDPATESTATE%>;
	ComntUseropDef.YFF_DYOPER_GPARASTATE = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_GPARASTATE%>;
	var SDDef = {};
	SDDef.YFF_DY_CHGBUYTIME 	= <%=SDDef.YFF_DY_CHGBUYTIME%>;
	SDDef.YFF_DY_FRMESS_ERR 	= <%=SDDef.YFF_DY_FRMESS_ERR%>;
	SDDef.YFF_DY_FRSOUND_ERR 	= <%=SDDef.YFF_DY_FRSOUND_ERR%>;
	SDDef.YFF_DY_SECMESS_ERR 	= <%=SDDef.YFF_DY_SECMESS_ERR%>;
	SDDef.YFF_CUSSTATE_NORMAL	= <%=SDDef.YFF_CUSSTATE_NORMAL%>;
	SDDef.SUCCESS			 	= "<%=SDDef.SUCCESS%>";
	SDDef.YFF_FEETYPE_JTFL		= <%=SDDef.YFF_FEETYPE_JTFL%>;
	SDDef.YFF_FEETYPE_MIXJT		= <%=SDDef.YFF_FEETYPE_MIXJT%>;
	
	</script>
	</body>
</html>
