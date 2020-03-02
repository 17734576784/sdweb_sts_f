<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
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
	tr{
	    /*
		event:expression(
		onmouseover = function(){this.style.backgroundColor='#f1f8fb'}, 
		onmouseout = function(){this.style.backgroundColor=''}
		)
		*/
	}
	a{
	color: blue;
	}
	</style>
    <link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
	<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#table tr:gt(2)").mouseover(function(){//为id为table的表格   第二行以后增加事件
				this.style.backgroundColor='#f1f8fb';
			}).mouseout(function(){
				this.style.backgroundColor='';
			});
	    });
	</script>
	</head>
  <body>
      <!-- 不要改变前两行tr的位置 jquery筛选的时候用到了 -->
      <%
       int i = 1;//作为id用
      %>
		<table id = "table"  width="auto" height="auto" align="center" class="tabsty">
			<tr id =first>
				<td colspan="4" class="page_tbl_bg22">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;" /></td>
							<td width="100%" style="border: 0px; font-size: 12px;">Téléchargement des outils système</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width="5%" nowrap style="text-align: center;"><b>Numéro de série.</b><img src="<%=basePath%>images/no_xz.gif" /></td>
				<td width="30%"><b>Nom de l'outil</b><img src="<%=basePath%>images/name_xz.gif" /></td>
				<td><b>Informations détaillées</b><img src="<%=basePath%>images/ditial_affare.gif" /></td>
				<td width="20%"><b>Télécharger</b><img src="<%=basePath%>images/down_load_xz.gif" /></td>
			</tr>
			<tr id="<%=i%>">
				<td width="5%" style="text-align: center;"><%=i++%></td>
				<td width="30%">IE8-WindowsXP</td>
				<td>Système Windows XP version IE8.0</td>
				<td width="20%"><a href="<%=basePath%>download.jsp?fileName=IE8-WindowsXP.rar" target="_blank">Cliquez sur télécharger</a></td>
			</tr>
			<tr id="<%=i%>">
				<td width="5%" style="text-align: center;"><%=i++%></td>
				<td width="30%">Plug-in prépayé</td>
				<td>Plug-in prépayé version 2</td>
				<td width="20%"><a href="<%=basePath%>download.jsp?fileName=ke_yffwebctrl.rar" target="_blank">Cliquez sur télécharger</a></td>
			</tr><!--
			<tr id="<%=i%>">
				<td width="5%" style="text-align: center;"><%=i++%></td>
				<td width="30%">科林预付费使用说明</td>
				<td>科林预付费使用说明</td>
				<td width="20%"><a href="<%=basePath%>download.jsp?fileName=ke_ygyff_sysm.rar" target="_blank">Click download</a></td>
			</tr>			
			 <tr id="<%=i%>">
				<td width="5%" style="text-align: center;"><%=i++%></td>
				<td width="30%">高压预付费功能建议配置</td>
				<td>高压预付费功能建议配置</td>
				<td width="20%"><a href="<%=basePath%>download.jsp?fileName=ke_gyyff_jypz.rar" target="_blank">Click download</a></td>
			</tr> 						
			<tr id="<%=i%>">
				<td width="5%" style="text-align: center;"><%=i++%></td>
				<td width="30%">结算xls文件</td>
				<td>结算xls文件</td>
				<td width="20%"><a href="<%=basePath%>download.jsp?fileName=js-file.rar" target="_blank">Click download</a></td>
			</tr>
			<tr id="<%=i%>">
				<td width="5%" style="text-align: center;"><%=i++%></td>
				<td width="30%">pdf文件阅读器</td>
				<td>pdf文件阅读器</td>
				<td width="20%"><a href="<%=basePath%>download.jsp?fileName=pdf_setup.rar" target="_blank">Click download</a></td>
			</tr>
			-->
		</table>
</body>
</html>
