<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.Dict"%>
<%@page import="com.kesd.common.YDTable"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>Residents Archive</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
	<style type="text/css">
	body{
	  margin:0 0 0 0;
	  overflow:hidden;
	  background: #eaeeef;
	}
	.tabsty{
		width:95%;
	}
	.tabsty input,.tabsty select {
		width: 110px;
	}
	input,select{
		font-size: 12px;
	}
	</style>
	<script type="text/javascript">
		var basePath = '<%=basePath%>';
	</script>
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/modalDialog.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/def.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/validate.js"></script>
	
  </head>
  <jsp:include page="../inc/jsdef.jsp"></jsp:include>
  <body>
    <form action="" method="post" id="addorupdate"  style='display:inline;'>
    <br/>
    <table class="tabsty" align="center">
    	<tr><td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;">
    	<table cellpadding="0" cellspacing="0"><tr><td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td><td style="border: 0px; font-size: 12px;">Basic Archive</td></tr></table>
    	</td></tr>
    	<tr>
	    	<td><font color=red>*</font>Name:</td><td><input type="text" name="conspara.describe" id="describe" style="width: 150px;"/></td>
	    	<td><font color=red>*</font>Utility:</td><td><select name="conspara.orgId" id="orgId" onChange="getLineFzMan(this.value);"  style="width: 150px;"><option value="">--Select--</option></select></td>
	    	<td>Power Supply Type:</td><td><select name="conspara.powerType" id="powerType" style="width: 150px;"></select></td>
	    	
	    	<!--
	    	<td><font color=red>*</font>Line Supervisor:</td><td><select name="conspara.lineFzManId" id="lineFzManId" style="width: 150px;"><option value=""></option></select><input type="hidden" id="prelineFzManId"/></td>
	    --></tr>
	    <tr>
	    	<td>Voltage Class:</td><td><select name="conspara.voltGrade" id="voltGrade" ></select></td>
	    	<td>Transformer Type:</td><td><select name="conspara.trModel" id="trModel" style="width: 150px;"></select></td>
	    </tr>
	    
	    <tr><td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>
    	<tr>
	    	<td colspan="6" style="font-size: 12px;background: url('../../../images/bg22.gif') repeat-x;">
	    	<table cellpadding="0" cellspacing="0"><tr><td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td><td style="border: 0px; font-size: 12px;">Contact Archive</td></tr></table>
	    	</td>
	    </tr>
    	<tr>
    		<td>Contact:</td><td><input type="text" name="conspara.fzMan" id="fzMan"/></td>
    		<td>Telephone:</td><td><input type="text" name="conspara.telNo1" id="telNo1" style="width: 150px;"/></td>
    		<td></td><td><input type="text" name="conspara.telNo3" id="telNo3" style="width: 150px;"/></td>
    	</tr>
    	<tr>
    		<td>Mobile Phone:</td><td><input type="text" name="conspara.telNo2" id="telNo2" style="width: 150px;"/></td>
    		<td>Post Code:</td><td><input type="text" name="conspara.postalCode" id="postalCode"/></td>
    		<td>Address:</td><td><input type="text" name="conspara.addr" id="addr" style="width: 150px;"/>
    			<input type="hidden" id="id" name="conspara.id" />
    			<input type="hidden" id="appType" name="conspara.appType" /></td>
    	</tr>
    </table>
    <center>
	<jsp:include page="../inc/btn_tianjia.jsp"></jsp:include>
    &nbsp;&nbsp;
     <jsp:include page="../inc/btn_xiugai.jsp"></jsp:include>
    &nbsp;&nbsp;
    <jsp:include page="../inc/btn_guanbi.jsp"></jsp:include>
    </center>
    </form>
    <script type="text/javascript">
    var YDDef = {};
    var Dict = {};
    var YDTable = {};
    YDDef.BASEPATH = "<%=basePath%>";
    YDDef.SPLITCOMA = "<%=SDDef.SPLITCOMA%>";
    YDDef.APPTYPE_JC = "<%=SDDef.APPTYPE_JC%>";
    Dict.DICTITEM_VOLTGRADE = "<%=Dict.DICTITEM_VOLTGRADE%>";
    Dict.DICTITEM_POWERTYPE = "<%=Dict.DICTITEM_POWERTYPE%>";
    Dict.DICTITEM_TRMODEL = "<%=Dict.DICTITEM_TRMODEL%>";

    YDTable.TABLECLASS_ORGPARA = "<%=YDTable.TABLECLASS_ORGPARA%>";
    YDTable.TABLECLASS_LINEFZMAN = "<%=YDTable.TABLECLASS_LINEFZMAN%>";
    </script>
    <script type="text/javascript" src="<%=basePath%>js/docs/consparaDialog.js"></script>
    <script src="<%=basePath%>js/docs/docper.js"></script>
  </body>
</html>
