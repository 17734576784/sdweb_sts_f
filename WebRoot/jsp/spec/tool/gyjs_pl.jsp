<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
<%@page import="com.kesd.comnt.ComntProtMsg"%>
<%@page import="com.kesd.comnt.ComntDef"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<i18n:bundle baseName="<%=SDDef.BUNDLENAME%>" id="bundle" locale="<%=WebConfig.app_locale%>" scope="application" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>主站结算补差</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="cache-control" content="no-cache">
	
	<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="index_theme" />
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
	
	<script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
	<script src="<%=basePath%>js/common/modalDialog.js"></script>
	<script src="<%=basePath%>js/common/def.js"></script>
	<script src="<%=basePath%>js/common/cookie.js"></script>
	<script src="<%=basePath%>js/spec/tool/gyjs_pl.js"></script>
	<script src="<%=basePath%>js/spec/localbd/bdcalcu.js"></script>
	<script src="<%=basePath%>js/common/jsonString.js"></script>
	<script src="<%=basePath%>js/validate.js"></script>
	<script src="<%=basePath%>js/common/loading.js"></script>
	<script src="<%=basePath%>js/common/number.js"></script>
	<script src="<%=basePath%>js/common/DateFun.js"></script>
	
	<jsp:include page="../../../jsp/ydjsdef.jsp"></jsp:include>
	  <script type="text/javascript">
	    var old_i = 1;//js使用勿改名
	    function changecolor(i){old_i = i;
		 	if(i==1){$("#11").attr('class','titlel');$("#12").attr('class','titlem');$("#13").attr('class','titler');}
			else {$("#11").attr('class','titlel1');$("#12").attr('class','titlem1');$("#13").attr('class','titler1');}
		 	if(i==2){$("#21").attr('class','titlel');$("#22").attr('class','titlem');$("#23").attr('class','titler');}
			else {$("#21").attr('class','titlel1');$("#22").attr('class','titlem1');$("#23").attr('class','titler1');}
			if(i==3){$("#31").attr('class','titlel');$("#32").attr('class','titlem');$("#33").attr('class','titler');}
			else {$("#31").attr('class','titlel1');$("#32").attr('class','titlem1');$("#33").attr('class','titler1');}
			changepage();		
		}
	  </script>
  </head>
 <body>
  	<table width="100%" align="center" cellspacing="0" cellpadding="0" border="0" >
  		<tr>
    		<td valign="bottom" style="border-bottom: 3px solid #40A6A4;">
		    	<table id="tableother" cellpadding="0" cellspacing="0" border="0"><tr>
		    		<td width="3"></td>
		  			<td id=11 class=titlel></td>
		  			<td id=12 class=titlem onClick="if(old_i == 1) return;tdid='12';changecolor(1);" style="cursor: pointer;">主站费控</td>
		  			<td id=13 class=titler></td>
		  			<td width="3"></td>
		  			<td id=21 class=titlel1></td>
		  			<td id=22 class=titlem1 onClick="if(old_i == 2) return;tdid='22';changecolor(2)" style="cursor: pointer;">远程金额</td>
		  			<td id=23 class=titler1></td>
		  			<td width="3"></td>
		  			<td id=31 class=titlel1></td>
		  			<td id=32 class=titlem1 onClick="if(old_i == 3) return;tdid='32';changecolor(3)" style="cursor: pointer;">远程表底</td>
		  			<td id=33 class=titler1></td>
		  			<td width="3"></td>  			
		  		</tr></table>
  			</td>
	  	</tr>
	</table> 
	
 	<table class="gridbox" align="center">
		 <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
	</table>
	
	<table class="gridbox" align="center" style="margin-top:5px;border 0;">
		<tr>
		<td><button id="upload_bdye">上传SG186表底余额</button></td>
		<td style="text-align:right" >抄表年月:&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="text" id="cb_ym" readonly style="width:120px" onFocus="WdatePicker({dateFmt:'yyyy年MM月',maxDate:'%y-%M',isShowClear:'false'});" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button id="btnRemain" 	class="btn"  disabled="disabled">计算结算金额</button>&nbsp;&nbsp;&nbsp;&nbsp;
				<button id="btnPay"  	class="btn"  disabled="disabled">结算所选记录</button>&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
		</tr>
	</table>  
 </body>
 <script type="text/javascript">
	
	$("#gridbox").height($(window).height()-60);
				
	var	mygridCons = new dhtmlXGridObject('gridbox');
		
	var ComntUseropDef = {};
	ComntUseropDef.YFF_GYOPER_REJS 		 		= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_REJS%>;
	ComntUseropDef.YFF_GYOPER_GETJSBCREMAIN  	= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_GETJSBCREMAIN%>;
	ComntUseropDef.YFF_GYOPER_GPARASTATE 		= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_GPARASTATE%>;
	ComntUseropDef.YFF_READDATA					= <%=com.kesd.comntpara.ComntUseropDef.YFF_READDATA%>;
	ComntUseropDef.YFF_GYCOMM_CALLPARA			= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYCOMM_CALLPARA%>;
	ComntUseropDef.YFF_GYCOMM_PAY				= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYCOMM_PAY%>;
	
	var ComntProtMsg = {};
	ComntProtMsg.YFF_CALL_REAL_ZBD 		= <%=ComntProtMsg.YFF_CALL_REAL_ZBD%>;
	ComntProtMsg.YFF_CALL_REAL_FBD		= <%=ComntProtMsg.YFF_CALL_REAL_FBD%>;
	ComntProtMsg.YFF_GY_CALL_PARAREMAIN = <%=ComntProtMsg.YFF_GY_CALL_PARAREMAIN%>;
	ComntProtMsg.YFF_CALL_GY_REMAIN 	= <%=ComntProtMsg.YFF_CALL_GY_REMAIN%>;
	
	var ComntDef = {};
	ComntDef.YD_PROTOCAL_FKGD05 	= <%=ComntDef.YD_PROTOCAL_FKGD05%>;
	ComntDef.YD_PROTOCAL_GD376 		= <%=ComntDef.YD_PROTOCAL_GD376%>;
	ComntDef.YD_PROTOCAL_GD376_2013 = <%=ComntDef.YD_PROTOCAL_GD376_2013%>;
	ComntDef.YD_PROTOCAL_KEDYH 		= <%=ComntDef.YD_PROTOCAL_KEDYH%>;
	
	var SDDef = {};
	SDDef.SUCCESS = '<%=SDDef.SUCCESS%>';
	<%
	Date date = new Date();
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
	out.println("var _today = '" + sdf.format(date) + "';");
	%>
	
</script>
</html>