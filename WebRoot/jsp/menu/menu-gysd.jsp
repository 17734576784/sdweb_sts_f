<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef,com.kesd.common.WebConfig"%>
<%@page import="com.kesd.dbpara.YffManDef"%>
<%@page import="com.kesd.util.Menu"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>高压售电</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="cache-control" content="no-cache">
	<link href="css/zh/menu.css" rel="stylesheet" type="text/css" />
	<link href="css/zh/cont.css" rel="stylesheet" type="text/css" />
	<script src="js/jquery-1.4.2.min.js"></script>
	<script src="js/common/menu.js"></script>
	<script src="js/common/def.js"></script>
	<script src="js/common/cookie.js"></script>
	<style type="text/css">
	body{
		background: white;
	}
	
	</style>
  </head>
  
  <body>
  <%
	YffManDef yffman = (YffManDef)session.getAttribute(SDDef.SESSION_USERNAME);
	%>
  <table cellpadding="0" cellspacing="0"  border=0>
	  <tr>
		<td id="tdleft" valign="top">
		  <div id="div_leftmenu" >
		  	<table class=menuleft  cellspacing="0" cellpadding="0" width="200px;">
				<tr><td colspan=4 class="mtitle" style="padding-left: 35px;height:30px;">管理栏</td></tr>
				<%=Menu.getGy_menu(yffman)%>
			</table>
		  </div>
		</td>
		<td class=splittd width="10px;"></td>
		<td id="tdright">
		  <div id="div_rightmenu" style="overflow: auto;">
		  <%
		  int i = 0;
		  out.println(Menu.getGy_localcard(yffman, 	i++));
		  out.println(Menu.getGy_localbd(yffman, 	i++));
		  out.println(Menu.getGy_localmoney(yffman, i++));
		  out.println(Menu.getGy_main(yffman,		i++));
		  out.println(Menu.getGy_search(yffman, 	i++));
		  out.println(Menu.getGy_report(yffman, 	i++));
		  out.println(Menu.getGy_tool(yffman, 		i++));
		  out.println(Menu.getGy_print(yffman,		i++));
		  %>
		  </div>
		</td>
	  </tr>
  </table>
	
</body>
<script type="text/javascript">
$("#tdright").width($(window).width()-$("#tdleft").width()-10);
$("#div_rightmenu").height($(window).height());
var contH = $(window).height()-80;

</script>
</html>
  