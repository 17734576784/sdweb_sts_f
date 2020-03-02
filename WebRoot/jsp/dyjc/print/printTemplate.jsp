<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>"> 
    <title>printTemplate.jsp</title>
    <script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
    <script  src="<%=basePath%>js/common/jquery.jqprint-0.3.js"></script>
	<style type="text/css">
	#printTemplateTable table td{
		font-family:黑体;
	}
	.fontSize{
		font-size:12pt;
	}
	</style>
  </head>
  
  <body style="text-align:center;">
  	<!-- startprint1 -->
	<div id="printTemplateTable" style="font-size:10pt;font-weight:bold;width:58mm;text-align:center;">
		<table width="100%" height="30" border="0" align="center" cellpadding="0" cellspacing="0">
	  		<tr>
	    		<td align="center">Vending System</td>
	  		</tr>
		</table>
		<table width="100%"  height="30"  border="0" cellspacing="0" bgcolor="#FFFFFF">
		  <tr>
		    <td width="30" height="30" align="left">Serial:</td>
		    <td align="left" id="waterNo" style="word-wrap:break-word;word-break:break-all;">流水号</td>
		  </tr>
		  <tr>
		  	<td width="30" height="30" align="left" bgcolor="#FFFFFF">Csr No:</td>
		  	<td align="left" id="conNo" bgcolor="#FFFFFF">客户编号</td>
		  </tr>
		  <tr>
		  	<td width="30" align="left" bgcolor="#FFFFFF">Csr Name:</td>
		  	<td align="left" id="conName" bgcolor="#FFFFFF">客户名称</td>
		  </tr>	
		  <tr>
		  	<td width="30" height="30" align="left"bgcolor="#FFFFFF">Meter No:</td>
		  	<td align="left" id="meterAddr" bgcolor="#FFFFFF">电表地址</td>
		  </tr>	
		  <tr>
		  	<td width="30" height="30" align="left" bgcolor="#FFFFFF">Date:</td>
		  	<td align="left" id="payDate" bgcolor="#FFFFFF">缴费日期</td>
		  </tr>
		  <tr>
		  	<td width="30" height="30" align="left" bgcolor="#FFFFFF">Price:</td>
		  	<td align="center" id="payMoney" bgcolor="#FFFFFF">缴费金额(元)</td>
		  </tr>
		  <tr>
		  	<td width="30" height="30" align="left" bgcolor="#FFFFFF">Balance:</td>
		  	<td align="center" id="jyMoney" bgcolor="#FFFFFF">缴费金额(元)</td>
		  </tr>		  	
		  <tr>
		  	<td height="30" align="left"  bgcolor="#FFFFFF">Amount:</td>
		  	<td align="left" id="payDL" bgcolor="#FFFFFF">购电量  kwh</td>
		  </tr>
		  <tr>
		  	<td height="30" align="left"  bgcolor="#FFFFFF">Sperson:</td>
		  	<td align="center" id="sdadmin" bgcolor="#FFFFFF">售电操作员</td>
		  </tr>
		</table>
	    <table width="100%" height="30" border="0" align="center" cellpadding="0" cellspacing="1">
		  <tr>
		  	<td height="30" align="left" bgcolor="#FFFFFF">TOKEN</td>
		  </tr>
	  	  <tr>
	      	<td align="left">**************************</td>
	  	  </tr>
		</table>
		<table width="100%" height="30" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		  	<td align="left" id="token1" bgcolor="#FFFFFF">Token1</td>
		  </tr>
		  <tr>
		  	<td align="left" id="token2" bgcolor="#FFFFFF">Token2</td>
		  </tr>
		  <tr>
		  	<td align="left" id="token3" bgcolor="#FFFFFF">Token3</td>
		  </tr>		
		</table>
	    <table width="100%" height="30" border="0" align="center" cellpadding="0" cellspacing="1">
	  	  <tr>
	      	<td align="left" width="30%">**************************</td>
	  	  </tr>	  	  
		</table>
		<table width="100%" height="30" border="0"  cellpadding="0" cellspacing="1">
		  <tr>
		  	<td height="30" align="left" bgcolor="#FFFFFF">Service Name:</td>
		  	<td align="left" id="orgDesc" bgcolor="#FFFFFF">服务名称</td>
		  </tr>		  
		  <tr>
		  	<td height="30" align="left" width="30%" class="fontSize" bgcolor="#FFFFFF">Service Call:</td>
		  	<td align="left" id="telno" bgcolor="#FFFFFF">服务电话</td>
		  </tr>
		  <tr>
		  	<td height="30" align="left" class="fontSize" bgcolor="#FFFFFF">Sales Addr:</td>
		  	<td align="left" id="orgAddr" bgcolor="#FFFFFF">购电地址</td>
		  </tr>		
		</table>
		<hr/>
		Please keep the small ticket to prepare the query 
		<br>	 
	</div>
	<!-- endprint1 -->
	<div style="text-align:center;">
		<button class="btn" onclick="printTemplate();">Print</button>
	</div>
	
	<script src="<%=basePath%>js/dyjc/print/printTemplate.js"></script>
  </body>
</html>
