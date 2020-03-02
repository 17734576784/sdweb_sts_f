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
	<script  src="<%=basePath%>js/common/gridopt.js"></script>
	<script  src="<%=basePath%>js/dyjc/query/yhzt.js"></script>
	<script  src="<%=basePath%>js/dyjc/tool/comm.js"></script>
	<script  src="<%=basePath%>js/common/jsonString.js"></script>
	<script  src="<%=basePath%>js/common/modalDialog.js"></script>
	<script  src="<%=basePath%>js/common/def.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script src="<%=basePath%>js/common/cookie.js"></script>
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
	     	<td class="tdr">POS:</td><td><select id="org" style="width: 150px"></select></td>
	     	<td class="tdr">Concentrator:</td><td><select id="rtu" style="width: 150px"><option value="-1">All</option></select></td>
	     	<td class="tdr">Customer No:</td><td><input type="text" id="yhhh" style="width: 150px"/></td>
	     	<td class="tdr">Customer Name:</td><td><input type="text" id="yhmc" style="width: 150px"/></td>
	     	<td class="tdr">Meter No:</td><td><input type="text" id="bh" style="width: 150px"/></td>
	    </tr>
	    <tr>
	    <td class="tdr">Customer State:</td>
	    <td><select id="yhzt" class="is_width" style="width: 150px">
	    <option value="0">All</option>
	    <option value="1">Normal account</option>
	    <option value="2">Abnormal account</option>
	    <option value="3">More than 1.0 of the balance</option>
	    <option value="4">Less than 1.0 of the balance</option>
	    </select></td>
	   <!--20121114 add except begin-->
	   <td class= "tdr">Alarm State</td>
	   <td><select id="alarm_type" class="is_width" style="width: 150px"> 
	    <option value="0">All</option>
	    <option value="1">1st Short Message</option>
	    <option value="2">1st Sound</option>
	    <option value="3">2nd Short Message</option>
	    <option value="4">Alarm</option>
	    <option value="5">1st Short Msg Succ</option>
	    <option value="6">1st Sound Succ</option>
	    <option value="7">2nd Short Msg Succ</option>
	    <option value="8">Alarm Succ</option>
        </select></td>
        
	    <td class= "tdr">Control State</td>
	   <td><select id="ctrl_type" class="is_width" style="width: 150px"> 
	    <option value="0">All</option>
	    <option value="1">Switch off Error</option>
	    <option value="2">Switch on Error</option>
	    <option value="3">Switch on/off Error</option>
	    <option value="4">Switch off OK</option>
	    <option value="5">Switch on OK</option>
	    <option value="6">Switch on/off OK</option>
        </select></td>
        <!--20121114 add except 下面colspan 有调整 end-->
	    <td class="tdr" colspan="4">
	        <input type="hidden" type="button" value="Select Item" class="btn" id="selLookItem" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
	$("#gridbox").height($(window).height() - $("#tabinfo").height() - 48);
	</script>
  </body>
</html>
