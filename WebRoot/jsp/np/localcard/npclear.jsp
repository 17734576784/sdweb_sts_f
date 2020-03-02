<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
<%@page import="com.kesd.comntpara.ComntUseropDef"%>
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
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/np/tool/setCons.js"></script>
		<script  src="<%=basePath%>js/np/localcard/npclear.js"></script>
		<script  src="<%=basePath%>js/np/dialog/search.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		
		
	</head>
	<body>
	<table id="tabinfo1" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:5px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height: 25px;">基本信息</td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
		    	<button id="btnRead"  class="btn">读卡</button>			    		    
	    	</td>
	    	<td style="border:0px; text-align: right;" ><img id=rtuonline_img style="display:none"/>&nbsp;
       		<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    </tr>
   		<tr>
	    	<td class="tdr" style="width: 10%"><i18n:message key="baseInfo.yhbh"/></td><td id="userno" style="width: 10%">&nbsp;</td>
	    	<td class="tdr" style="width: 10%"><i18n:message key="baseInfo.yhmc"/></td><td id="username" style="width: 15%">&nbsp;</td>
	     	<td class="tdr" style="width: 10%"><i18n:message key="baseInfo.yhzt"/></td><td id="cus_state" style="width: 10%">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">身份证号:</td><td id="identityno">&nbsp;</td>
	    	<td class="tdr">自然村:</td><td id="village">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.lxdh"/></td><td id="tel">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.yhdz"/></td><td id="useraddr">&nbsp;</td>
	        <td class="tdr">结算客户名:</td><td id="jsyhm">&nbsp;</td>
		    <td class="tdr">客户分类:</td><td id="yhfl">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">所属片区:</td><td id="area">&nbsp;</td>
	    	<td class="tdr">区域号:</td><td id="area_no">&nbsp;</td>
	    	<td></td><td></td>
	    </tr>
	   	<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr>
    		<td class="td_lable">购电卡信息</td>
    		<td colspan=4 style="padding-left:10px; border: 0px;">
	    	
	    	</td>
	    	<td style="border:0px; text-align: right;" ><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
    	</tr>
	    <tr>
	    	<td class="tdr">购电卡号:</td><td id="card_no">&nbsp;</td>
	    	<td class="tdr">购电卡状态:</td><td id="card_state_desc">&nbsp;</td>
	    	<td class="tdr">购电次数:</td><td id="buy_times">&nbsp;</td> 	
	    </tr>
	    <tr>
	    	<td class="tdr">卡内余额(元):</td><td id="now_remain">&nbsp;</td>
	    	<td></td><td></td><td></td><td></td> 	
	    </tr>
	
	    <tr><td colspan="6" style="height:8px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>	    		
	</table>
	<table id="tabinfo2" class="tabinfo" align="center"> 	
		<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
		<tr>
	    	<td colspan="6" style="text-align: right;border: 0">
	    		<button id="cardinfo"  class="btn">卡内信息</button> &nbsp;&nbsp;&nbsp;&nbsp;	  
	    		<button id="clearcard"  class="btn">清空卡</button> &nbsp;&nbsp;&nbsp;&nbsp;	    	 
		    </td>
		</tr>
	</table>
	<input type="hidden" id="area_id"/>
	<input type="hidden" id="farmer_id"/>
	<script type="text/javascript">
		var SDDef = {};
		SDDef.STR_OR  = "<%=SDDef.STR_OR%>";
		SDDef.SUCCESS = "<%=SDDef.SUCCESS%>";
		
		SDDef.NPCARD_OPTYPE_INITPARA	= <%=SDDef.NPCARD_OPTYPE_INITPARA%>;	//不缴费 仅初始化参数
		SDDef.NPCARD_OPTYPE_OPEN		= <%=SDDef.NPCARD_OPTYPE_OPEN%>;		//卡类型 开户
		SDDef.NPCARD_OPTYPE_BUY			= <%=SDDef.NPCARD_OPTYPE_BUY%>;			//买电充值
		SDDef.NPCARD_OPTYPE_REVER		= <%=SDDef.NPCARD_OPTYPE_REVER%>;		//冲正
		
		$("#_title3").hide();
		SDDef.YFF_CARDMTYPE_KE005	= <%=SDDef.YFF_CARDMTYPE_KE005%>;
		var ComntUseropDef = {};
		ComntUseropDef.YFF_NPOPER_GPARASTATE = <%=ComntUseropDef.YFF_NPOPER_GPARASTATE%>;
	</script>
	</body>
</html>
