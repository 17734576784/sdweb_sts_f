<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.comntpara.ComntUseropDef"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    <title>营销详细信息</title>
    
	<style type="text/css">
		body{
			overflow: hidden;
		}			  
	</style>
	<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
	<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script  src="<%=basePath%>js/common/def.js"></script>
	<script  src="<%=basePath%>js/common/DateFun.js"></script>
	<script  src="<%=basePath%>js/common/jsonString.js"></script>
	<script  src="<%=basePath%>js/dyjc/dialog/sg186DetailHN.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script src="<%=basePath%>js/common/number.js"></script>	
  </head>
  
  <body>
   <table class="tabinfo" align="center" style="width:300px;">
      <tr><td colspan="2" style="height:20px; border: 0px;"></td></tr>
	    <tr><td class="tdr" style="width: 35%">营销用户名称:</td><td id="userName" style="width: 65%"></td></tr>
	 	<tr><td class="tdr" >用户地址:</td><td id="userAddr"></td></tr>
	 	<tr><td class="tdr" >供电单位名称:</td><td id="deptName"></td></tr>
	 	<tr><td class="tdr" >合计金额(元):</td><td id="money">	</td></tr>
	 	<tr><td class="tdr" >合计预收(元):</td><td id="preReceive">	</td></tr>
	 	<tr><td class="tdr" >金额总笔数:</td><td id="TotalpayTimes"></td></tr>
	 	<tr><td class="tdr" >电费年月:</td><td id="preymd">	</td></tr>
	 	<tr><td class="tdr" >电费金额(元):</td><td id="premoney">	</td></tr>
	 	<tr><td class="tdr" >违约金金额(元):</td><td id="wymoney">	</td></tr>
	 	
	 	<tr><td class="tdr" >信息提示:</td><td id="msgInfo"></td></tr>
	 	<tr><td style="border: 0;height:20px;" colspan=2 ></td></tr>
	 	<tr>
	 		<td style="border: 0;height:20px;text-align: right;" colspan=2>
	 	 		<button id="btnSearch"  class="btn">刷新</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 	  		<button id="btnClose"  class="btn" >关闭</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 	 	</td>
	 	</tr>
	  </table>
  </body>
  	<script type="text/javascript">
	var ComntUseropDef = {};
	ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER = <%=ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER%>;
	</script>
</html>