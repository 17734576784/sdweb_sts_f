<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
    <script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
    <script  src="<%=basePath%>js/common/jquery.jqprint-0.3.js"></script>
	<style type="text/css">
	#printTemplateTable table td{
		font-family:黑体;
	}
	.fontSize{
		font-size:10pt;
	}
	</style>
	
  </head>
  
  <body style="text-align:center;">
  	<!-- startprint1 -->
	<div id="printTemplateTable" style="font-size:8pt;font-weight:bold;width:58mm;text-align:center;">
		<table width="100%" height="30" border="0" align="center" cellpadding="0" cellspacing="0">
	  		<tr>
	    		<td align="center">Système de vente</td>
	  		</tr>
		</table>
		<table width="100%"  height="30"  border="0" cellspacing="0" bgcolor="#FFFFFF">
		  <tr>
		  	<td width="30" height="30" align="left" bgcolor="#FFFFFF">Numéro de client:</td>
		  	<td align="left" id="conNo" bgcolor="#FFFFFF">客户编号</td>
		  </tr>
		  <tr>
		  	<td width="30" align="left" bgcolor="#FFFFFF">Nom du client:</td>
		  	<td align="left" id="conName" bgcolor="#FFFFFF">客户名称</td>
		  </tr>	
		  <tr>
		  	<td width="30" height="30" align="left"bgcolor="#FFFFFF">Mètre Non:</td>
		  	<td align="left" id="meterAddr" bgcolor="#FFFFFF">电表地址</td>
		  </tr>
		</table>
	    <table width="100%" height="30" border="0" align="center" cellpadding="0" cellspacing="1">
		  <tr>
		  	<td height="30" align="left" bgcolor="#FFFFFF">JETON</td>
		  </tr>
	  	  <tr>
	      	<td align="left">*********************</td>
	  	  </tr>
		</table>
		<table width="100%" height="30" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		  	<td align="left" id="token1" bgcolor="#FFFFFF">JETON1</td>
		  </tr>	
		</table>
	    <table width="100%" height="30" border="0" align="center" cellpadding="0" cellspacing="1">
	  	  <tr>
	      	<td align="left" width="30%">*********************</td>
	  	  </tr>
		  <tr>
		  	<td height="30" align="left" id="orgDesc" bgcolor="#FFFFFF">Kelin Electric Co.,Ltd.</td>
		  </tr>
		</table>
<!--		<table width="100%" height="30" border="0"  cellpadding="0" cellspacing="1">-->
<!--		  <tr>-->
<!--		  	<td height="30" align="left" width="30%" class="fontSize" bgcolor="#FFFFFF">Service Call:</td>-->
<!--		  	<td align="left" id="telno" bgcolor="#FFFFFF">服务电话</td>-->
<!--		  </tr>-->
<!--		  <tr>-->
<!--		  	<td height="30" align="left" class="fontSize" bgcolor="#FFFFFF">Sales Addr:</td>-->
<!--		  	<td align="left" id="orgAddr" bgcolor="#FFFFFF">购电地址</td>-->
<!--		  </tr>		-->
<!--		</table>-->
		<hr/>
		Veuillez conserver le petit ticket pour préparer la requête
		<br>	 
	</div>
	<!-- endprint1 -->
	<div style="text-align:center;">
		<button class="btn" onclick="printTemplate();">Impression</button>
	</div>
	
	<script src="<%=basePath%>js/dyjc/print/stsToolTemplate.js"></script>
  </body>
</html>
