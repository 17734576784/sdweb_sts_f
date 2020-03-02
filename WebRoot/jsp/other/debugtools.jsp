<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'appgg.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<style type="text/css">
	body{
	  margin:0 0 0 0;
	  overflow:auto;
	  background: #eaeeef;
	}
	.tabsty{
		width:95%;
		font-size:12px;
	}
	a{
	color: blue;
	}
	</style>
	<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
	<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#table tr:gt(1)").mouseover(function(){//为id为table的表格   第二行以后增加事件
				this.style.backgroundColor='#f1f8fb';
			}).mouseout(function(){
				this.style.backgroundColor='';
			});
	    });
	</script>
	</head>
  <body>
      <%
       int i = 1;//作为id用
      %>
		<table id="table" width="auto" height="auto" align="center" class="tabsty">
			<tr>
				<td colspan="4" class="page_tbl_bg22">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;" /></td>
							<td width="100%" style="border: 0px; font-size: 12px;">Téléchargement de l'outil de débogage</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<%=i++%>
				</td>
				<td width="30%">
					Assistant de débogage Serialport V2.2
				</td>
				<td>
					Assistant de débogage Serialport V2.2
				</td>
				<td width="20%">
					<a href="<%=basePath%>download.jsp?fileName=V2.2.rar" target="_blank"	>Cliquez sur télécharger</a>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<%=i++%>
				</td>
				<td>
					Outil de test TCP UDP
				</td>
				<td>
					Outil de test TCP UDP
				</td>
				<td width="20%">
					<a href="<%=basePath%>download.jsp?fileName=TCPUDP.rar" target="_blank">Cliquez sur télécharger</a>
				</td>
			</tr>
		</table>
	<script type="text/javascript" language="javascript">
    </script>
</body>
</html>
