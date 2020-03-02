<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page	import="com.kesd.common.WebConfig"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'yhzt.jsp' starting page</title>
    <link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />		
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
	<style type="text/css">
	.is_width{
		width:110px;
	}
	</style>
	<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
	<script  src="<%=basePath%>js/common/gridopt.js"></script>
	<script  src="<%=basePath%>js/spec/query/yhzt.js"></script>
	<script  src="<%=basePath%>js/spec/tool/comm.js"></script>
	<script  src="<%=basePath%>js/common/jsonString.js"></script>
	<script  src="<%=basePath%>js/common/def.js"></script>
	<script  src="<%=basePath%>js/common/modalDialog.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script src="<%=basePath%>js/common/cookie.js"></script>
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
	     	<td class="tdr">营业点:</td><td><select id="org" class="is_width"></select></td>
	     	<td class="tdr">终端名称:</td><td><select id="rtu" class="is_width"><option value="-1">所有</option></select></td>
	     	<td class="tdr">客户编号:</td><td><input type="text" id="yyhh" class="is_width"/></td>
	     	<td class="tdr">客户名称:</td><td><input type="text" id="yhmc" class="is_width"/></td>
	     	<td class="tdr">联系电话:</td><td><input type="text" id="lxdh" class="is_width"/></td>
	    </tr>
	    <tr>
	    <td>客户状态:</td>
	    <td><select id="yhzt" class="is_width">
	    <option value="0">所有</option>
	    <option value="1">正常客户</option>
	    <option value="2">非正常客户</option>
	    <option value="3">余额大于1</option>
	    <option value="4">余额小于1</option>
	    </select></td>
	   <!--20121114 add except begin-->
	   <td class= "tdr">报警状态</td>
	   <td><select id="alarm_type" class="is_width"> 
	    <option value="0">所有</option>
	    <option value="1">首次短信异常</option>
	    <option value="2">首次声音异常</option>
	    <option value="3">二次短信异常</option>
	    <option value="4">报警异常</option>
	    <option value="5">首次短信成功</option>
	    <option value="6">首次声音成功</option>
	    <option value="7">二次短信成功</option>
	    <option value="8">报警成功</option>
        </select></td>
        
	    <td class= "tdr">控制状态</td>
	   <td><select id="ctrl_type" class="is_width"> 
	    <option value="0">所有</option>
	    <option value="1">分闸异常</option>
	    <option value="2">合闸异常</option>
	    <option value="3">分合闸异常</option>
	    <option value="4">分闸成功</option>
	    <option value="5">合闸成功</option>
	    <option value="6">分合闸成功</option>
        </select></td>
        <!--20121114 add except 下面colspan 有调整 end-->
         
	    <td class="tdr" colspan="6">
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
