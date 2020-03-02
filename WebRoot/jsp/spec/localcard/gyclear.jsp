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
			.tabinfo input{
				
			}
			input,select{
				font-size: 12px;
			}			  
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/spec/tool/setCons.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/spec/localcard/gyclear.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		
		
	</head>
	<body>
	<table id="tabinfo" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;">基本信息</td>
	    	<td colspan=5 style="padding-left:10px; border: 0px;">
	    	<button id="btnRead" class="btn">读卡</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>
	    </tr>
   		<tr>
	    	<td class="tdr" width="12%">客户编号:</td><td id="userno" width="20%">&nbsp;</td>
	    	<td class="tdr" width="12%">客户名称:</td><td colspan=3 id="username">&nbsp;</td>	     		    	
	    </tr>
	    <tr>
	    	<td class="tdr">联系电话:</td><td id="tel">&nbsp;</td>
	   		<td class="tdr">客户地址:</td><td colspan=3 id="useraddr">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">ESAM表号:</td><td id="esamno">&nbsp;</td>
	    	<td class="tdr" width="12%">PT变比:</td><td id="pt" width="12%">&nbsp;</td>
	    	<td class="tdr" width="12%">CT变比:</td><td id="ct" width="12%">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr" width="12%">客户状态:</td><td id="cus_state">&nbsp;</td>
	    	<td></td><td colspan = 3></td>
	    </tr>
	     <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" style="height:25px;">费控信息</td></tr>
	   <tr>
	    	<td class="tdr">计费方式:</td><td id="cacl_type">&nbsp;</td>
	    	<td class="tdr">卡类型:</td><td id="card_type">&nbsp;</td>	    	
	    	<td class="tdr">写卡金额(元):</td><td id="xkje">&nbsp;</td>
	    </tr>
	   	<tr>
	   		<td class="tdr">购电次数:</td><td id="buyp_times">&nbsp;</td>
	   		<td class="tdr">报警值:</td><td colspan=3 id="alarm_val">&nbsp;</td>
	   	</tr>
	   	<tr>
	   		<td class="tdr">费率方案(元):</td><td colspan=5 id="feeproj_desc">&nbsp;</td>
	   	</tr>
	   	
		<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
<!--    	<tr><td class="td_lable">返写信息</td></tr>-->
<!--	    <tr>-->
<!--	    	<td class="tdr">客户编号:</td><td ><input id=yhbh name=yhbh class="roinput" value="000000000000" style="text-align: right"/></td>		  		        		    				-->
<!--	    	<td class="tdr">PT变比:</td><td colspan=1>0</td>-->
<!--	    	<td class="tdr">CT变比:</td><td colspan=1>0</td>-->
<!--	    </tr>-->
<!--	    <tr>-->
<!--	    	<td class="tdr">表号:</td><td ><input id=jfje name=bh class="roinput" value="000000000000" style="text-align: right"/></td>		  		        		    				-->
<!--	    	<td class="tdr">计费方式:</td><td colspan=1>&nbsp;</td>-->
<!--	    	<td class="tdr">购电次数:</td><td colspan=1>0</td>-->
<!--	    </tr>-->
<!--	     <tr>-->
<!--	    	<td class="tdr">剩余金额(元):</td><td ><input id=jfje name=syje class="roinput" value="0.00" style="text-align: right"/></td>		  		        		    				-->
<!--	    	<td class="tdr">返写日期:</td><td>00年01月01日 00时00分</td>-->
<!--	    	<td class="tdr">透支金额(元):</td><td ><input id=jfje name=tzje class="roinput" value="0.00" style="text-align: right"/></td>		  		        		    				-->
<!--	    </tr>-->
	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
<!--    	<td colspan="6" style="text-align: right;border-top: 1px solid #B8C0BD; border-left: 0;border-bottom: 0;border-right: 0;">-->
    	<td colspan="6" style="text-align: right;border: 0">
    		    <select id="clearcard_type" style="width: 200px;">
		    	<option value=<%=SDDef.YFF_CARDMTYPE_KE001%>>智能电表卡2009版</option>
		    	<option value=<%=SDDef.YFF_CARDMTYPE_KE006%>>智能电表卡2013版</option>
		    	<option value=<%=SDDef.YFF_CARDMTYPE_KE003%>>Ke6103卡</option>
		    	</select>&nbsp;&nbsp;&nbsp;&nbsp;
		    	
    		<button id="cardinfo"  class="btn">卡内信息</button> &nbsp;&nbsp;&nbsp;&nbsp;	  
    		<button id="clearcard"  class="btn" >清空卡</button> &nbsp;&nbsp;&nbsp;&nbsp;	    	 
	    </td></tr>
	</table>
	<script type="text/javascript">
		var SDDef = {};
		SDDef.STR_OR  = "<%=SDDef.STR_OR%>";
		SDDef.SUCCESS = "<%=SDDef.SUCCESS%>";
		var ComntUseropDef = {};
		ComntUseropDef.YFF_GYOPER_GPARASTATE = <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_GPARASTATE%>;
	</script>
	</body>
</html>
