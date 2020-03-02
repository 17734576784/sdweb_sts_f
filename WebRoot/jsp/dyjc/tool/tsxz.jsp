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
			input {
				width :130px
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
		<script  src="<%=basePath%>js/dyjc/tool/tsxz.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/comm.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/common/DateFun.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
		<script  src="<%=basePath%>js/common/dateFormat.js"></script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		
	</head>
	<body>
	<table id="tabinfo" class="tabinfo" align="center">
	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr>
	    	<td colspan=6 style="padding-left:10px; border: 0px;">
	    	<i18n:message key="baseInfo.yyd"/>:<select id="org" style="width:120px;"></select>&nbsp;&nbsp;
	    	<i18n:message key="baseInfo.jzq"/>:<select id="rtu" style="width:120px;"><option value=-1>所有</option></select>&nbsp;&nbsp;
	    	<i18n:message key="baseInfo.jstj"/>:<select id="searchType" style="width:80px;">
	    	<option value="1"><i18n:message key="baseInfo.zdmc"/></option>
	    	<option value="2"><i18n:message key="baseInfo.yhhh"/></option>
	    	<option value="3">客户名称</option>
	    	<option value="4">联系电话</option>
	    	<option value="5" selected>ESAM表号</option>
	    	</select>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<i18n:message key="baseInfo.jsnr"/>:<input id="searchContent" />&nbsp;&nbsp;
	    	
	        <i18n:message key="baseInfo.gglx"/>&nbsp;<select id="updType" style="width:140px;">
	    	<option value="<%=SDDef.YFF_DY_CHGBUYTIME%>"><i18n:message key="baseInfo.gxgdcs"/></option>
	    	<option value="<%=SDDef.YFF_DY_FRMESS_ERR%>"><i18n:message key="baseInfo.1jdxbjsb"/></option>
	    	<option value="<%=SDDef.YFF_DY_FRSOUND_ERR%>"><i18n:message key="baseInfo.1jsybjsb"/></option>
	    	<option value="<%=SDDef.YFF_DY_SECMESS_ERR%>"><i18n:message key="baseInfo.2jdxbjsb"/></option>
	    	<option value="<%=SDDef.YFF_DY_JTTOTZBDL%>">阶梯追补累计用电量</option>
	    	<option value="<%=SDDef.YFF_DY_JTRESETYMD%>">阶梯自动上次切换日期</option>
	    	<option value="<%=SDDef.YFF_DY_FXDF_ALLMONEY%>">发行电费当月缴费总金额</option>
	    	<option value="<%=SDDef.YFF_DY_FXDF_REMIAN%>">发行电费当月剩余金额</option>
	    	<option value="<%=SDDef.YFF_DY_FXDF_YM%>">发行电费年月</option>
	    	</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnSearch" class="btn"><i18n:message key="btn.jiansuo"/></button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnUpdate" class="btn"><i18n:message key="btn.gengxin"/></button>
	    	</td>
	    </tr>
   	</table>
	<DIV id=gridbox style="width: 100%;"></DIV>
	<table id="tabinfo1" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable"><i18n:message key="baseInfo.yhxx"/></td></tr>
	    <tr>
	        <td class="tdr" style="width: 13%;"><i18n:message key="baseInfo.yhmc"/></td><td style="width: 18%;" id="yhmc" ></td>
	       	<td class="tdr" style="width: 15%;"><i18n:message key="baseInfo.gdcs"/></td><td class="buy_times" style="width: 18%;"><input id="buy_times" /></td>
	        <td class="tdr" style="width: 16%;"><i18n:message key="baseInfo.2jbjzt"/>:</td><td style="width: 18%;" id="2jbjzt" ></td>
	    </tr>
	    <tr>
	        <td class="tdr">客户编号:</td><td id="jmhh"></td>
	       	<td class="tdr"><i18n:message key="baseInfo.bjfa"/></td><td id="bjfa"></td>
	        <td class="tdr"><i18n:message key="baseInfo.2jdxbj"/>:</td><td id="2jdxbj"></td>
	    </tr>
	    <tr>
	        <td class="tdr"><i18n:message key="baseInfo.bh"/></td><td id="bh"></td>
	        <td class="tdr"><i18n:message key="baseInfo.bdsj"/>:</td><td id="bdsj"></td>
	        <td class="tdr"><i18n:message key="baseInfo.fhz"/>:</td><td id="fhz"></td>
	    </tr>
	    <tr>
	        <td class="tdr"><i18n:message key="baseInfo.sflx"/>:</td><td id="sflx"></td>
	        <td class="tdr"><i18n:message key="baseInfo.1jbjzt"/>:</td><td id="1jbjzt"></td>
	        <td class="tdr"><i18n:message key="baseInfo.fhzqr"/>:</td><td id="fhzqr"></td>
	    </tr>
	    <tr>
	        <td class="tdr"><i18n:message key="baseInfo.fkfs"/></td><td id="fkfs"></td>
            <td class="tdr"><i18n:message key="baseInfo.1jdxbj"/>:</td><td id="1jdxbj"></td>
            <td></td><td></td>	        
	    </tr>
	    <tr>
	        <td class="tdr"><i18n:message key="baseInfo.yffblx"/></td><td id="yffblx"></td>
	     	<td class="tdr"><i18n:message key="baseInfo.1jsybj"/>:</td><td id="1jsybj"></td>
	     	<td></td><td></td>	
	    </tr>
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable">阶梯、计算信息</td></tr>
    	<tr>
    		<td class="tdr">阶梯追补电量:</td><td ><input id=jt_total_zbdl ></td>
    		<td class="tdr">发行电费当月总金额:</td><td class=buy_times ><input id=fxdf_iall_money></td>
    		<td class="tdr">发行电费年月:</td><td id="fxdf_ym_tmp"></td>
    	</tr>
    	<tr>
    		<td class="tdr">阶梯累计用电量:</td><td><input id=jt_total_dl></td>
    		<td class="tdr">发行电费当月总金额2:</td><td><input id=fxdf_iall_money2></td>
    		<td class="tdr">发行电费数据日期:</td><td class=buy_times><input id=fxdf_data_ymd ></td>
    	</tr>
    	<tr>
    		<td class="tdr">阶梯切换日期:</td><td id = "jt_reset_ymd_tmp"></td>
    		<td class="tdr">发行电费当月剩余金额:</td><td><input id=fxdf_remain ></td>
    		<td class="tdr">发行电费算费时间:</td><td><input id=fxdf_calc_mdhmi></td>
    	</tr>
    	<tr>
    		<td class="tdr">阶梯切换执行时间:</td><td class="buy_times"><input id=jt_reset_mdhmi></td>
    		<td class="tdr">发行电费当月剩余金额2:</td><td><input id=fxdf_remain2 ></td>
    		<td></td><td></td>
    	</tr>
	    <tr><td colspan="6" style="height:5px;border: 0"></td></tr>
	</table>
	
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<script>
	$("#gridbox").height($(window).height()-$("#tabinfo").height()-$("#tabinfo1").height()-10);
	var	mygrid = new dhtmlXGridObject('gridbox');	
	var yff_grid_title ='<i18n:message key="baseInfo.tsxz.grid_title"/>';
	var ComntUseropDef = {};
	ComntUseropDef.YFF_DYOPER_UDPATESTATE= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_UDPATESTATE%>;
	ComntUseropDef.YFF_DYOPER_GPARASTATE = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_GPARASTATE%>;
	var SDDef = {};
	
	SDDef.YFF_DY_CHGBUYTIME  	    = <%=SDDef.YFF_DY_CHGBUYTIME%>;  	//更新购电次数
	SDDef.YFF_DY_FRMESS_ERR         = <%=SDDef.YFF_DY_FRMESS_ERR%>;  	//首次短信告警确认失败
	SDDef.YFF_DY_FRSOUND_ERR        = <%=SDDef.YFF_DY_FRSOUND_ERR%>; 	//首次声音告警确认失败
	SDDef.YFF_DY_SECMESS_ERR        = <%=SDDef.YFF_DY_SECMESS_ERR%>; 	//二次短信告警确认失败
	SDDef.YFF_DY_JTTOTZBDL		    = <%=SDDef.YFF_DY_JTTOTZBDL%>;	    //阶梯追补累计用电量
	SDDef.YFF_DY_JTRESETYMD		    = <%=SDDef.YFF_DY_JTRESETYMD%>;	    //阶梯上次自动切换日期	自动切换执行时间
	SDDef.YFF_DY_FXDF_ALLMONEY	    = <%=SDDef.YFF_DY_FXDF_ALLMONEY%>;	//发行电费当月缴费总金额
	SDDef.YFF_DY_FXDF_REMIAN		= <%=SDDef.YFF_DY_FXDF_REMIAN%>;	//发行电费当月剩余金额
	SDDef.YFF_DY_FXDF_YM			= <%=SDDef.YFF_DY_FXDF_YM%>;		//发行电费年月
	SDDef.SUCCESS                   = '<%=SDDef.SUCCESS%>';
	
	
	</script>
	</body>
</html>
