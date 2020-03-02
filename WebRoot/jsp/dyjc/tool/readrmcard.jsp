<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef,com.kesd.common.YDTable"%>
<%@page import="com.kesd.common.WebConfig"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>

<i18n:bundle baseName="<%=SDDef.BUNDLENAME%>" id="bundle" locale="<%=WebConfig.app_locale%>" scope="application" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<style type="text/css">
			.tabinfo input{
				
			}
			input,select{
				font-size: 12px;
			}			  
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>	
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/common/DateFun.js"></script>
		<script  src="<%=basePath%>js/common/dateFormat.js"></script>
		<script  src="<%=basePath%>js/common/cookie.js"></script>  		
<!--	<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>-->
	
		
		
	</head>
	<body>
	
	<table class="gridbox" align="center">
	
		 <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
	</table>
	<table id="tabinfo" class="tabinfo" align="center">
<!--    	<tr><td colspan="4" style="height:10px; border: 0px;"></td></tr>-->
    
   		<tr>
			<td class="tdr">营业点:</td><td ><select id="org" style="width: 100px;"></select></td>
 			<td class="tdr">所属联系人:</td><td><select id="fzman" name="fzman" style="width: 100px;"></select></td>
	     	<td class="tdr">集中器:</td><td><select id="rtu" style="width: 120px;"><option value="-1">所有</option></select></td>
	     	<td class="tdr">抄表日期:</td><td><input id=idate name=idate class="roinput" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',maxDate:'%y-%M-%ld',isShowClear:'false'});"/></td>
</tr>
<tr>
			<td></td><td></td><td></td><td></td>
	     	<td></td>

	    	<td><button id="readrmcard"  class="btn">读抄表卡</button> </td>
	    	<td><button id="importrmcard"  class="btn">导入数据</button>
	    	<td>
	    		 <form style="display: inline;" method="post" action="<%=basePath%>excel/dataExcel.action" name="toxls">
					<input type="hidden" id="excPara" name="excPara" />
					<input type="hidden" id="colType"  name="colType" />
					<input type="hidden" id="header"  name="header" />
					<input type="hidden" id="attachheader"  name="attachheader" />
					<input type="hidden" id="vfreeze" name="vfreeze" />
					<input type="hidden" id="hfreeze" name="hfreeze" />
					<input type="hidden" id="hidecols" name="hidecols" />
					<input type="hidden" id="filename" name="filename" />
					<button class="btn" id="toexcel" onclick='dcExcel();'>导出</button>
				</form>
			</td>
			
	    </tr>
	    	
	</table>

	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
	
		$("#gridbox").height($(window).height()-55);			
		
									  		
		var SDDef = {};
		SDDef.STR_OR  = "<%=SDDef.STR_OR%>";
		SDDef.SUCCESS = "<%=SDDef.SUCCESS%>";
		var YDTable = {};
		YDTable.TABLECLASS_OCARDTYPE_PARA = '<%=YDTable.TABLECLASS_OCARDTYPE_PARA%>';
		var basePath  = "<%=basePath%>";
	</script>

	<script  src="<%=basePath%>js/dyjc/tool/dyreadrmcard.js"></script>
<!--	<script  src="<%=basePath%>js/dyjc/tool/comm.js"></script>-->
	</body>
</html>
