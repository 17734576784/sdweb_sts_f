<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'zdjl.jsp' starting page</title>
    
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
		if(i==4){$("#41").attr('class','titlel');$("#42").attr('class','titlem');$("#43").attr('class','titler');}
		else {$("#41").attr('class','titlel1');$("#42").attr('class','titlem1');$("#43").attr('class','titler1');}
	}
  </script>
  </head>
  
  <body>
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
	<td>
  <table width="100%" cellspacing="0" cellpadding="0" border=0>
  <tr style="margin-left: 10px">
    <td valign="bottom" style="border-bottom: 3px solid #40A6A4;width: 450px">
    	<table id="tableother" cellpadding="0" cellspacing="0" border="0"><tr>
  			<td id=11 class=titlel></td>
  			<td id=12 class=titlem width=80 onClick="if(old_i == 1) return;tdid='12';$('#rturecord').attr('src','../dialog/ydjl.jsp');changecolor(1)" style="cursor: pointer;">用电记录</td>
  			<td id=13 class=titler></td>
  			<td width="3"></td>
  			<td id=21 class=titlel1></td>
  			<td id=22 class=titlem1 width=80 onClick="if(old_i == 2) return;tdid='22';$('#rturecord').attr('src','../dialog/gqyhjl.jsp');changecolor(2)" style="cursor: pointer;">挂起用户记录</td>
  			<td id=23 class=titler1></td>
  			<td width="3"></td>
  			<td id=31 class=titlel1></td>
  			<td id=32 class=titlem1 width=80 onClick="if(old_i == 3) return;tdid='32';$('#rturecord').attr('src','../dialog/skgzjl.jsp');changecolor(3)" style="cursor: pointer;">刷卡故障记录</td>
  			<td id=33 class=titler1></td>
  			<td width="3"></td>
  			<td width="3"></td>
  			<td id=41 class=titlel1></td>
  			<td id=42 class=titlem1 width=80 onClick="if(old_i == 4) return;tdid='42';$('#rturecord').attr('src','../dialog/csjl.jsp');changecolor(4)" style="cursor: pointer;">参数设置记录</td>
  			<td id=43 class=titler1></td>
  			<td width="3"></td>
  		</tr></table>
  		</td>
  		
		<td valign="bottom" class=seltitlem  style="border-bottom: 3px solid #40A6A4;" width="54%">
		<table style="font-size: 12px;">
			<tr>
			<td width=50 >&nbsp;&nbsp;电表:</td>
			<td>
			<input type="text" id="esam_no" readonly style="background: #eee; margin-left:1px ；width:120px"/>
		</td>
		<td width="50">
			<button id="sel_meter" style="height: 23px">选择</button>
		</td>
		<td width=65 style="visibility: hidden">&nbsp;区域号:</td>
		<td style="visibility: hidden">
			<input type="text" id="area_code" style="width:120px"/>
		</td>
		<td width=50 style="visibility: hidden">&nbsp;密码:</td>
		<td width=70 style="visibility: hidden">
			<input type="text" id="pwd" style="width:70px"/>
		</td>
		<td></td>
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
  <script src="<%=basePath%>js/np/tool/zdjl.js"></script>
  <script type="text/javascript">
  $("#toexcel").hide();
  $('#rturecord').attr('src','../dialog/ydjl.jsp');
  appType = <%=SDDef.APPTYPE_NP%>;
  $("#rturecord").height($(window).height() - 25);
 	
  </script>
  <jsp:include page="../../inc/jsdef.jsp" />
  </body>
</html>
