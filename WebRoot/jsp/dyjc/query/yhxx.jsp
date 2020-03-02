<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page	import="com.kesd.common.WebConfig"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'yhxx.jsp' starting page</title>
   	<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />		
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
	<style type="text/css">
	input,select{
	width:90px;
	}
	</style>
	<script>
		var basePath = '<%=basePath%>';
	</script>	
	<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
	<script  src="<%=basePath%>js/dyjc/query/yhxx.js"></script>
	<script  src="<%=basePath%>js/dyjc/tool/comm.js"></script>
	<script  src="<%=basePath%>js/common/gridopt.js"></script>
	<script  src="<%=basePath%>js/common/jsonString.js"></script>
	<script  src="<%=basePath%>js/common/def.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script type="text/javascript">
		var dy_autoshow = <%=WebConfig.autoShow.get("dy_show")%>;		//获取低压自动显示参数配置数组	
	</script>
  </head>
  
  <body>
    <table width="100%" cellpadding="0" cellspacing="0">
    	<tr><td align="center">
    	<div id=gridbox style="height:420px;border:0px;width:99%;"></div>
    	</td></tr>
    </table>
    <table width="100%" border="0" class="page_tbl">
    <tr><td id="pageinfo">&nbsp;</td></tr>
    </table>
    <table id="tabinfo" class="tabinfo" align="center">
    	<tr>
	    	<td class="td_lable" style="height:22px;" id="td2">Conditions</td>
	    </tr>
	    <tr>
	     	<td class="tdr">POS:</td><td><select id="org" style="width: 230px"></select></td>
	     	<td class="tdr">Concentrator:</td><td><select id="rtu" style="width: 150px"><option value="-1">所有</option></select></td>
	     	<td class="tdr">Customer No:</td><td><input type="text" id="yhbh" style="width: 100px"/></td>
	     	<td class="tdr">Meter No:</td><td><input type="text" id="bh" style="width: 100px"/></td>
	     	<td class="tdr">Customer Name:</td><td><input type="text" id="yhmc" style="width: 100px"/></td>
	     	<td class="tdr">Phone Number:</td><td><input type="text" id="lxdh" style="width: 100px"/></td>
	    </tr>
	    <tr>
	    <td class="tdr" colspan="12">
		    <button class="btn" id="search">Query</button>
		    <form style="display: inline;" method="post" action="<%=basePath%>excel/dataExcel.action" name="toxls">
				<input type="hidden" id="excPara" name="excPara" />
				<input type="hidden" id="colType"  name="colType" />
				<input type="hidden" id="header"  name="header" />
				<input type="hidden" id="attachheader"  name="attachheader" />
				<input type="hidden" id="vfreeze" name="vfreeze" />
				<input type="hidden" id="hfreeze" name="hfreeze" />
				<input type="hidden" id="filename" name="filename" />
				<button class="btn" id="toexcel" onclick='dcExcel();'>Export</button>
			</form>
	    </td>
	    </tr>
	</table>
	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
	$("#gridbox").height($(window).height() - $("#tabinfo").height() - 38);
	</script>
  </body>
</html>
