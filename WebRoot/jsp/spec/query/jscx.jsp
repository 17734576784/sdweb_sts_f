<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page	import="com.kesd.common.WebConfig"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'jscx.jsp' starting page</title>
    <link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />		
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
	<style type="text/css">
	input,select{
	width:120px;
	}
	</style>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
	<script  src="<%=basePath%>js/spec/query/jscx.js"></script>
	<script  src="<%=basePath%>js/spec/tool/comm.js"></script>
	<script  src="<%=basePath%>js/common/gridopt.js"></script>
	<script  src="<%=basePath%>js/common/def.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script  src="<%=basePath%>js/common/jsonString.js"></script>
	<script  src="<%=basePath%>js/common/DateFun.js"></script>
	<script  src="<%=basePath%>js/common/dateFormat.js"></script>
	<script type="text/javascript">
		var spec_autoshow = <%=WebConfig.autoShow.get("spec_show")%>;		//获取专变自动显示参数配置数组	
	</script>
  </head>
  
  <body>
  <table width="100%" cellpadding="0" cellspacing="0">
    	<tr><td align="center">
    	<div id=gridbox style="height:420px;width:99%;"></div>
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
	    	<td class="tdr">起始时间:</td>
	     	<td><input id=sdate name=sdate class="roinput" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',maxDate:'%y-%M-%d %H:%m',isShowClear:'false'});"/></td>
	     	<td class="tdr">结束时间:</td>
	     	<td><input id=edate name=edate class="roinput" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',maxDate:'%y-%M-%d %H:%m',isShowClear:'false'});"/></td>
	    	<td class="tdr" width="120px;">客户编号:</td><td><input type="text" id="yhbh"/></td>
	    	<td class="tdr">客户名称:</td><td><input type="text" id="yhmc"/></td>
	    </tr>
	    <tr>
	    	<td class="tdr">营业点:</td><td><select id="org"></select></td>
	     	<td class="tdr">终端名称:</td><td><select id="rtu"><option value=-1>所有</option></select></td>
	     	<td class="tdr">操作员:</td><td><input type="text" id="czy"/></td>
	     	<td></td><td></td>
	    </tr>
	    <tr>
	    <td class="tdr" colspan="8">
		    <button id="search" class="btn">查询</button>
		    <form style="display: inline;" method="post" action="<%=basePath%>excel/dataExcel.action" name="toxls">
				<input type="hidden" id="excPara" name="excPara" />
				<input type="hidden" id="colType"  name="colType" />
				<input type="hidden" id="header"  name="header" />
				<input type="hidden" id="attachheader"  name="attachheader" />
				<input type="hidden" id="vfreeze" name="vfreeze" />
				<input type="hidden" id="hfreeze" name="hfreeze" />
				<input type="hidden" id="filename" name="filename" />
				<button id="toexcel" class="btn" onclick='dcExcel();'>导出</button>
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
