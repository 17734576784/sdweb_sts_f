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
	width:130px;
	}
	</style>
	<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
	<script  src="<%=basePath%>js/spec/query/yhye.js"></script>
	<script  src="<%=basePath%>js/spec/tool/comm.js"></script>
	<script  src="<%=basePath%>js/common/gridopt.js"></script>
	<script  src="<%=basePath%>js/common/jsonString.js"></script>
	<script  src="<%=basePath%>js/common/def.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script type="text/javascript">
		var spec_autoshow = <%=WebConfig.autoShow.get("spec_show")%>;		//获取专变自动显示参数配置数组	
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
	    	<td class="td_lable" style="height:22px;" id="td2">筛选条件</td>
	    </tr>
	    <tr>
	     	<td class="tdr">营业点:</td><td><select id="org"></select></td>
	     	<td class="tdr">终端名称:</td><td><select id="rtu"><option value="-1">所有</option></select></td>
	     	<td class="tdr">营业户号:</td><td><input type="text" id="yyhh"/></td>
	    </tr><tr>
	     	<td class="tdr">客户名称:</td><td><input type="text" id="khmc"/></td>
	     	<td class="tdr">联系电话:</td><td><input type="text" id="lxdh"/></td>
	     	<td class="tdr">费控单元:</td><td><input type="text" id="fkdy"/></td>
	    </tr>
	    <tr>
	    <td class="tdr" colspan="6">
		    <button class="btn" id="search">查询</button>
		    <form style="display: inline;" method="post" action="<%=basePath%>excel/dataExcel.action" name="toxls">
				<input type="hidden" id="excPara" name="excPara" />
				<input type="hidden" id="colType"  name="colType" />
				<input type="hidden" id="header"  name="header" />
				<input type="hidden" id="attachheader"  name="attachheader" />
				<input type="hidden" id="vfreeze" name="vfreeze" />
				<input type="hidden" id="hfreeze" name="hfreeze" />
				<input type="hidden" id="filename" name="filename" />
				<button class="btn" id="toexcel" onclick='dcExcel();'>导出</button>
			</form>
	    </td>
	    </tr>
	</table>
	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
	$("#gridbox").height($(window).height() - $("#tabinfo").height() - 28);
	</script>
  </body>
</html>
