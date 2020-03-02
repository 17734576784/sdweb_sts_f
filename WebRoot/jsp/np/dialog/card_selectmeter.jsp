<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>电表信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<style type="text/css">
	body {
		overflow: hidden;		
	}
		input, select {
			font-size: 12px;
			width: 120px;
		}
	</style>
	<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
	<script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>		
 	<script src="<%=basePath%>js/common/gridopt.js"></script>
	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>		
	<script src="<%=basePath%>js/common/dhtmlxgrid_page.js"></script>		
	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>		
	<script src="<%=basePath%>js/common/def.js"></script>
	<script src="<%=basePath%>js/common/loading.js"></script>
	<script src="<%=basePath%>js/np/dialog/card_selectmeter.js"></script>
	<script src="<%=basePath%>js/common/cookie.js"></script>
  </head>
  
  <body>
  <table class="tabinfo" style="width:99%;" align="center">
		<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	   	<td class="td_lable" style="width:80px;">筛选条件</td>	
        </tr>
	    <tr>
	    	<td class="tdr">所属供电所:</td><td><select id="orgId" name="orgId" onchange="setOrgVal()" ></select></td>
	    	<td class="tdr">所属联系人:</td><td><select id="fzmanId" name="fzmanId"></select></td>
	    	<td class="tdr">终端名称:</td><td><input name="rtuName" id="rtuName" /></td>			
	    </tr>
	    <tr>
	    	<td class="tdr">所属片区:</td>
	    	<td><select id="areaId" name="areaId"></select></td>
	    	<td class="tdr">区域号:</td><td><input id="areaNo" /></td>
	    	<td class="tdr">电表名称:</td><td><input name="meterName" id="meterName" /></td>
	    </tr>
	    <tr>
	    	<td class="tdr">电表地址:</td><td><input name="meterAddr" id="meterAddr" /></td>
	    	<td colspan="4"></td>
	    </tr>
	   	<tr><td colspan="6" style="height:3px; border: 0px;"></td></tr>
	</table>
    <table class="tabinfo" align="center">
		<tr>
			<td class="td_lable" style="width:80px;">电表信息</td>
			<td style="border: 0px;text-align: right">
			<button name="submit"  class="btn" onclick="search()">查询</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 	
	    	<button  name="btnRead"  class="btn" onclick="returnWin()">确定</button>
	    	</td></tr>
	</table>
	<div id=gridbox style="height:270px;border:0px;width:99%;margin-left:3px;"></div>
	<table border="0" class="page_tbl" style="width:99%;"  align="center">
    <tr><td id="pageinfo">&nbsp;</td></tr>
    </table>
	<jsp:include page="../../../jsp/ydjsdef.jsp"></jsp:include>
	<script type="text/javascript">
	</script>
  </body>
</html>
