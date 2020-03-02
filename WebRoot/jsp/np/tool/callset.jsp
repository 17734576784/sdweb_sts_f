<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.WebConfig"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'callset.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<style type="text/css">
	body{
		margin: 0 0 0 0;
		overflow: hidden;
	}
	.tabsty input{
		width: 100px;
	}
	.tabsty select {
		width: 90px;
		font-color: black;
	}
	input,select{
		font-size: 12px;
		height: 20px;
	}
	.seltitlem{
		font-size: 12px;
		border-bottom: 3px solid #40A6A4;
	}
	</style>
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxmenu_dhx_skyblue.css">
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script  src="<%=basePath%>js/dhtmlx/menu/dhtmlxcommon.js"></script>
  <script  src="<%=basePath%>js/dhtmlx/menu/dhtmlxmenu.js"></script>        
  <script  src="<%=basePath%>js/dhtmlx/menu/dhtmlxmenu_ext.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  <jsp:include page="../../../jsp/ydjsdef.jsp"></jsp:include>
  <script type="text/javascript">
    var old_i = -1;
    function changecolor(i){old_i = i;
	 	if(i==1){$("#11").attr('class','titlel');$("#12").attr('class','titlem');$("#13").attr('class','titler');}
		else {$("#11").attr('class','titlel1');$("#12").attr('class','titlem1');$("#13").attr('class','titler1');}
	 	if(i==2){$("#21").attr('class','titlel');$("#22").attr('class','titlem');$("#23").attr('class','titler');}
		else {$("#21").attr('class','titlel1');$("#22").attr('class','titlem1');$("#23").attr('class','titler1');}
		if(i==3){$("#31").attr('class','titlel');$("#32").attr('class','titlem');$("#33").attr('class','titler');}
		else {$("#31").attr('class','titlel1');$("#32").attr('class','titlem1');$("#33").attr('class','titler1');}
	}
  </script>
  </head>
  
  <body>
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
  <td>
  <table width="100%" cellspacing="0" cellpadding="0" border=0>
  <tr>
    <td valign="bottom" style="border-bottom: 3px solid #40A6A4;" width=190px>
  		<table id="tableother" cellpadding="0" cellspacing="0" border="0">
  		<tr>
  			<td id=11 class=titlel></td>
  			<td id=12 class=titlem width=70 onClick="if(old_i == 1) return;tdid='12';$('#rturecord').attr('src','../dialog/callset_fee.jsp');changecolor(1)" style="cursor: pointer;">基础参数</td>
  			<td id=13 class=titler></td>
  			<td width="3"></td>
  			<td id=21 class=titlel1></td>
  			<td id=22 class=titlem1 width=60 onClick="if(old_i == 2) return;tdid='22';$('#rturecord').attr('src','../dialog/blacklist.jsp');changecolor(2)" style="cursor: pointer;">黑名单</td>
  			<td id=23 class=titler1></td>
  			
  			<td width="3"></td>
  			<td id=31 class=titlel1></td>
  			<td id=32 class=titlem1 width=60 onClick="if(old_i == 3) return;tdid='32';$('#rturecord').attr('src','../dialog/whitelist.jsp');changecolor(3)" style="cursor: pointer;">白名单</td>
  			<td id=33 class=titler1></td>
  			<td width="3"></td>  			

  		</tr>
  		</table>
  	</td>
  	
  	<td valign="bottom" class=seltitlem  style="border-bottom: 3px solid #40A6A4;">
  		<table style="font-size: 12px;" cellpadding="0" cellspacing="0" border="0">
			<tr>
			<td>&nbsp;&nbsp;电表:
			<input type="text" id="esam_no" readonly style="background: #eee;width:130"/>
			<input type="button" id="sel_meter" value="选择"/>
			&nbsp;&nbsp;区域号:
			<input type="text" id="area_code" style="width:130px"/>
			&nbsp;&nbsp;密码:
			<input type="text" id="pwd" style="width:70px"/>
			</td>
			</tr>
		</table>
  	</td>
  	<td style="text-align: right;border-bottom: 3px solid #40A6A4;" ><img id=rtuonline_img style="display:none"/>&nbsp;</td>
  	<td style="text-align: right;border-bottom: 3px solid #40A6A4;font-size:12px;width:60px;"><span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
  	<td align="right" valign="bottom" style="border-bottom: 3px solid #40A6A4;width:80px;">
  	<table cellpadding="0" cellspacing="0" height="25"><tr><td>
  	<div id="menu" style="border: none;"></div>
  	</td></tr></table>
  	</td>
	</tr>
	</table>
 
  	<div id="tab" style="width: 100%; height: 100%;">
  	<iframe src="" width="100%" height="100%" frameborder="0" name="rturecord" id="rturecord"></iframe>
  	</div>
  
  </td>
  </tr>
  </table>
  <jsp:include page="../../inc/btn_excel.jsp"></jsp:include>
  <script src="<%=basePath%>js/np/tool/callset.js"></script>
  <script type="text/javascript">
  $("#toexcel").hide();
  $('#rturecord').attr('src','../dialog/callset_fee.jsp');
  $("#rturecord").height($(window).height() - 25);
  var nptool_whitelist_showflag     = <%=WebConfig.nptool_whitelist_showflag%>;
  var nptool_blacklist_showflag     = <%=WebConfig.nptool_blacklist_showflag%>;
  showHideList();
  
  function showHideList(){
  	if(nptool_blacklist_showflag == "1"){
  		$("#21").show();$("#22").show();$("#23").show();
  	}else{
  		$("#21").hide();$("#22").hide();$("#23").hide();
  	}
  	if(nptool_whitelist_showflag == "1"){
  		$("#31").show();$("#32").show();$("#33").show();
  	}else{
  		$("#31").hide();$("#32").hide();$("#33").hide();
  	}
  }
  </script>
  
  <jsp:include page="../../inc/jsdef.jsp" />
  
  </body>
</html>
