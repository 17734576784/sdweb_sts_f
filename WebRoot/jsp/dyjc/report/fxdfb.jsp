<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page	import="com.kesd.common.WebConfig"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>发行电费报表</title>
    <link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />		
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
	<style type="text/css">
	input{
		width:80px;
	}
	
	select{
		width:150px;
	}
	
	</style>
	<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
	<script  src="<%=basePath%>js/common/gridopt.js"></script>
	<script  src="<%=basePath%>js/dyjc/report/fxdf.js"></script>
	<script  src="<%=basePath%>js/dyjc/tool/comm.js"></script>
	<script  src="<%=basePath%>js/common/jsonString.js"></script>
	<script  src="<%=basePath%>js/common/def.js"></script>
	<script  src="<%=basePath%>js/common/modalDialog.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script  src="<%=basePath%>js/common/cookie.js"></script>
	<script  src="<%=basePath%>js/common/dateFormat.js"></script>
	<script  src="<%=basePath%>js/common/DateFun.js"></script>
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
	   		<td class="tdr">起始时间:</td>
	     	<td><input id=sdate name=sdate class="roinput" onFocus="WdatePicker({dateFmt:'yyyy年MM月',maxDate:'%y-%M',isShowClear:'false'});"/></td>
	     	<td class="tdr">结束时间:</td>
	     	<td><input id=edate name=edate class="roinput" onFocus="WdatePicker({dateFmt:'yyyy年MM月',maxDate:'%y-%M',isShowClear:'false'});"/></td>
	    
	     	<td class="tdr">营业点:</td><td><select id="org"></select></td>
	     	<td class="tdr">所属集中器:</td><td><select id="rtu" style="width: 200px;"><option value="-1">所有</option></select></td>
	    </tr>
	    <tr>
	    <td class="tdr" colspan="8">
	        <input type="button" value="选择查看项" class="btn" id="selLookItem"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    <input type="button" value="查询" class="btn" id="search"/>
		    <form style="display: inline;" method="post" action="<%=basePath%>excel/dataExcel.action" name="toxls">
				<input type="hidden" id="excPara" name="excPara" />
				<input type="hidden" id="colType"  name="colType" />
				<input type="hidden" id="header"  name="header" />
				<input type="hidden" id="attachheader"  name="attachheader" />
				<input type="hidden" id="vfreeze" name="vfreeze" />
				<input type="hidden" id="hfreeze" name="hfreeze" />
				<input type="hidden" id="filename" name="filename" />
				<input type="hidden" id="hidecols" name="hidecols"/><!-- 页面有隐藏设置时，导出后的excel也隐藏对应的列 -->
	
				<input type="button" value="导出" class="btn" id="toexcel" onclick='dcExcel();'/>
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
