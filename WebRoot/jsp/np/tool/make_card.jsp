<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'make_card.jsp' starting page</title>
    
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
	</style>
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
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
		if(i==5){$("#51").attr('class','titlel');$("#52").attr('class','titlem');$("#53").attr('class','titler');}
		else {$("#51").attr('class','titlel1');$("#52").attr('class','titlem1');$("#53").attr('class','titler1');}
		if(i==6){$("#61").attr('class','titlel');$("#62").attr('class','titlem');$("#63").attr('class','titler');}
		else {$("#61").attr('class','titlel1');$("#62").attr('class','titlem1');$("#63").attr('class','titler1');}
	}
  </script>
  </head>
  
  <body>
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
  <td>
  <table width="100%" cellspacing="0" cellpadding="0" border=0>
  <tr>
    <td valign="bottom" style="border-bottom: 3px solid #40A6A4;">
    	<table id="tableother" cellpadding="0" cellspacing="0" border="0"><tr>
  			<td id=11 class=titlel></td>
  			<td id=12 class=titlem onClick="if(old_i == 1) return;tdid='12';$('#makecard').attr('src','../dialog/card_setpara.jsp');changecolor(1)" style="cursor: pointer;">参数修改卡</td>
  			<td id=13 class=titler></td>
  			<td width="3"></td>
  			<td id=21 class=titlel1></td>
  			<td id=22 class=titlem1 onClick="if(old_i == 2) return;tdid='22';$('#makecard').attr('src','../dialog/card_ptct.jsp');changecolor(2)" style="cursor: pointer;">变比卡</td>
  			<td id=23 class=titler1></td>
  			<td width="3"></td>
  			<td id=31 class=titlel1></td>
  			<td id=32 class=titlem1 onClick="if(old_i == 3) return;tdid='32';$('#makecard').attr('src','../dialog/card_editarea.jsp');changecolor(3)" style="cursor: pointer;">区域修改卡</td>
  			<td id=33 class=titler1></td>
  			<td width="3"></td>
  			<td id=41 class=titlel1></td>
  			<td id=42 class=titlem1 onClick="if(old_i == 4) return;tdid='42';$('#makecard').attr('src','../dialog/card_editfee.jsp');changecolor(4)" style="cursor: pointer;">电价修改卡</td>
  			<td id=43 class=titler1></td>
  			<td width="3"></td>
  			<td id=51 class=titlel1></td>
  			<td id=52 class=titlem1 onClick="if(old_i == 5) return;tdid='52';$('#makecard').attr('src','../dialog/card_read.jsp');changecolor(5)" style="cursor: pointer;">读工具卡</td>
  			<td id=53 class=titler1></td>
  			<td width="3"></td>
  			<td id=61 class=titlel1></td>
  			<td id=62 class=titlem1 onClick="if(old_i == 6) return;tdid='62';$('#makecard').attr('src','../dialog/card_blacklist.jsp');changecolor(6)" style="cursor: pointer;">黑名单</td>
  			<td id=63 class=titler1></td>
  		</tr></table>
  		</td>
	  </tr>
	</table>
 
  	<div id="tab" style="width: 100%; height: 100%;">
  	<iframe src="" width="100%" height="100%" frameborder="0" name="makecard" id="makecard"></iframe>
  	</div>
  
  </td>
  </tr>
  </table>
  <script type="text/javascript">
  $('#makecard').attr('src','../dialog/card_setpara.jsp');
  appType = <%=SDDef.APPTYPE_NP%>;
  $("#makecard").height($(window).height() - 25);

  </script>
  <jsp:include page="../../inc/jsdef.jsp" />
  </body>
</html>
