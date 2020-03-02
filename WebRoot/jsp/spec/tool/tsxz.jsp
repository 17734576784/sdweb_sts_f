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
			
			input{
                width:130px
			}			  
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>		
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/spec/tool/tsxz.js"></script>
		<script  src="<%=basePath%>js/spec/tool/comm.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/common/DateFun.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
		<script  src="<%=basePath%>js/common/dateFormat.js"></script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	</head>
	<body>
	
	<table id="tabinfo" class="tabinfo" align="center">
    	<tr>
	    	<td style="padding-left:10px; border: 0px;">营业点:<select id="org" style="width:120px;"></select>&nbsp;&nbsp;&nbsp;&nbsp;
	    	检索条件:<select id="searchType" style="width:80px;">
	    	<option value="1">客户编号</option>
	    	<option value="2">客户名称</option>
	    	<option value="3">联系电话</option>
	    	</select>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<i18n:message key="baseInfo.jsnr"/>:<input id="searchContent" />&nbsp;&nbsp;&nbsp;&nbsp;
	    	更改类型:<select id="updType" style="width:120px;">
	    	<option value="<%=SDDef.YFF_GY_CHGBUYTIME%>"> <i18n:message key="baseInfo.gxgdcs"/>   </option><!-- 0 -->
	    	<option value="<%=SDDef.YFF_GY_FRMESS_ERR%>"> <i18n:message key="baseInfo.1jdxbjsb"/> </option><!-- 1 -->
	    	<option value="<%=SDDef.YFF_GY_FRSOUND_ERR%>"><i18n:message key="baseInfo.1jsybjsb"/> </option><!-- 2 -->
	    	<option value="<%=SDDef.YFF_GY_SECMESS_ERR%>"><i18n:message key="baseInfo.2jdxbjsb"/> </option><!-- 3 -->
	    	<option value="<%=SDDef.YFF_GY_TOTAL_YWZBDL%>">追补累计电量</option><!-- 4 -->
	    	<option value="<%=SDDef.YFF_GY_ZBELE_MONEY%>">追补电度电费</option><!-- 5 -->
	    	<option value="<%=SDDef.YFF_GY_ZBJBF_MONEY%>">追补基本费电费</option><!-- 6 -->
	    	<option value="<%=SDDef.YFF_GY_FXDF_IALL_MONEY%>">发行电费当月缴费总金额</option><!-- 7 -->
	    	<option value="<%=SDDef.YFF_GY_FXDF_REMAIN%>">发行电费后剩余金额</option><!-- 8 -->
	    	<option value="<%=SDDef.YFF_GY_FXDF_YM%>">发行电费数据日期</option><!-- 9 -->
	    	<option value="<%=SDDef.YFF_GY_JSWGBD%>">结算无功表底</option><!-- 10 -->
	    	</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnSearch" class="btn"><i18n:message key="btn.jiansuo"/></button>
	    	&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnUpdate" class="btn"><i18n:message key="btn.gengxin"/></button>
	    	</td>
	    	<td style="border: 0px;" id="rcd_num"></td>
	    </tr>
   	</table>
   	<div style="overflow: auto;width:100%; border-top: 1px solid gray;" id='div_1'>
	<DIV id=gridbox style="width: 100%;"></DIV>
	<table id="tabinfo1" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr>
    	    <td class="td_lable"><i18n:message key="baseInfo.yhxx"/></td>
    	</tr>
   		<tr>
   		    <td class="tdr" style="width:13%">客户编号:     </td> <td style="width:18%" id="yyhh"></td>
	    	<td class="tdr" style="width:15%">终端名称:     </td> <td style="width:18%" id="rtu_desc" ></td>
   		    <td class="tdr" style="width:16%">总家组编号:</td> <td style="width:18%" id="zjg_id"></td>
	    </tr>
   		<tr>
	    	<td class="tdr">计费方式:</td><td id="sflx"></td>
	    	<td class="tdr">费控方式:</td><td id="fkfs"></td>
	    	<td class="tdr">控制类型:</td><td id="yff_kzlx"></td> 	
	    </tr>
   		<tr>
	    	<td class="tdr">购电次数:</td><td class="buy_times"><input id="buy_times" /></td>	       	
	       	<td class="tdr">保电时间:</td><td id="bdsj"></td>
	       	<td class="tdr"><i18n:message key="baseInfo.bjfa"/></td><td id="bjfa"></td>    	
	    </tr>
   		<tr>
   			<td class="tdr">分合闸状态/时间:</td><td id="fhz"></td>
	  		<td class="tdr">分合闸确认状态/时间:</td><td id="fhzqr"></td>
	  		<td></td><td></td>  	
	    </tr>
	    <tr>
	        <td class="tdr"><i18n:message key="baseInfo.1jbjzt"/>:</td><td id="1jbjzt"></td>
	        <td class="tdr">1级短信报警状态/时间:</td><td id="1jdxbj"></td> 
	        <td class="tdr">1级声音报警状态/时间:</td><td id="1jsybj"></td>
	    </tr>  
	    <tr>
	        <td class="tdr"><i18n:message key="baseInfo.2jbjzt"/>:</td><td id="2jbjzt" ></td>
	        <td class="tdr"><i18n:message key="baseInfo.2jdxbj"/>:</td><td id="2jdxbj"></td>
	        <td></td><td></td>
	    </tr>
	</table>
	<!-- 新增阶梯结、算信息 -->
	<table id="tabinfo1" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable">结算信息</td></tr>
   		<tr>
   			<td class="tdr" style="width:13%">电度电费:</td><td style="width:18%" id="ele_money"></td>
   			<td class="tdr" style="width:15%">基本费电费:</td><td style="width:18%"  id="jbf_money"></td>
   		    <td class="tdr" style="width:16%">力调电费:</td><td style="width:18%"  id="powrate_money"></td> 
	    </tr>
   		<tr>
	   		<td class="tdr">累计有功电量:</td><td id="total_ydl"></td>
	   		<td class="tdr">累计无功电量:</td><td id="total_wdl"></td>
	   		<td class="tdr">实际功率因数:</td><td id="real_powrate"></td>
	    </tr>
	    <tr>
		    <td class="tdr">发行电费数据日期:</td><td id="fxdf_data_ymd"></td>
		    <td class="tdr">发行电费算费时间:</td><td id="fxdf_calc_mdhmi"></td>
		    <td class="tdr">发行电费年月:</td><td id = "fxdf_ym_tmp"> </td>	 	         	
	    </tr>
   		<tr>
			<td class="tdr">追补累计有功电量:</td><td><input id="total_yzbdl" ></td>
			<td class="tdr" >追补累计无功电量:</td><td  ><input id=total_wzbdl /></td>
   		    <td class="tdr">追补电度电费:</td><td><input id="zbele_money"></td> 		
	      
	    </tr>
	    <tr>
	        <td class="tdr">追补基本费电费:</td><td><input  id="zbjbf_money"></td>
	        <td class="tdr">发行电费后剩余金额:</td><td><input id="fxdf_remain"></td>
	        <td class="tdr">发行电费当月缴费总金额:</td><td><input id="fxdf_iall_money"></td>
	    </tr>
	    <tr>
	        <td class="tdr">结算无功表底1:</td><td><input id="jswgbd1"></td>
	        <td class="tdr">结算无功表底2:</td><td><input id="jswgbd2"></td>
	        <td class="tdr">结算无功表底3:</td><td><input id="jswgbd3"></td>
	    </tr>
   	
	    <tr><td colspan="8" style="height:10px; border: 0px;"></td></tr>
	</table>
	</div>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="zjg_id"/>
	<input type="hidden" id="clp_num"/>
	<jsp:include page="../../inc/jsdef.jsp" />
	<script>
	$("#gridbox").height(250);
	var	mygrid = new dhtmlXGridObject('gridbox');	
	var ComntUseropDef = {};
	ComntUseropDef.YFF_GYOPER_UDPATESTATE= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_UDPATESTATE%>;
	ComntUseropDef.YFF_GYOPER_GPARASTATE = <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_GPARASTATE%>;
	var SDDef = {};
	SDDef.YFF_DY_CHGBUYTIME  =  <%=SDDef.YFF_GY_CHGBUYTIME%>;
	SDDef.YFF_DY_FRMESS_ERR  =  <%=SDDef.YFF_GY_FRMESS_ERR%>;
	SDDef.YFF_DY_FRSOUND_ERR =  <%=SDDef.YFF_GY_FRSOUND_ERR%>;
	SDDef.YFF_DY_SECMESS_ERR =  <%=SDDef.YFF_GY_SECMESS_ERR%>;
	SDDef.SUCCESS            =  '<%=SDDef.SUCCESS%>';
	$("#div_1").height($(window).height() - 30);
	</script>
	
	</body>
</html>
