<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.dbpara.YffManDef"%>
<%@page import="com.kesd.common.SDDef"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'yffmandef.jsp' starting page</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
    <style type="text/css">
	body{
	  margin:0 0 0 0;
	  overflow:hidden;
	}
	.tabsty input, select {
		width: 130px;
	}
	input,select{
		font-size: 12px;
	}
	</style>
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  <script>
  	var basePath = '<%=basePath%>';
  </script>
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/common/gridopt.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script src="<%=basePath%>js/common/dateFormat.js"></script>
  <script  src="<%=basePath%>js/common/modalDialog.js"></script>
  <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
  <script src="<%=basePath%>js/docs/yffmandef.js"></script>
  <script src="<%=basePath%>js/docs/pub_docper.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
  <script src="<%=basePath%>js/validate.js"></script>
  <jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
  </head>
  
  <body>
    <DIV id=gridbox style="width: 100%;"></DIV>
  	<table width="100%" border="0" class="page_tbl">
    <tr><td id="pageinfo">&nbsp;</td><td align="right" id="soh">Hide detail</td><td width="15"><img src="<%=basePath%>images/mmd.gif" alt="hide" onClick="gridopt.showOrHide(this);" style="cursor: pointer;"/></td></tr>
    </table>
    <div id="showmore">
    <form action="" method="post" id="addorupdate"  style='display:inline;'>
    <br/>
    <table class="tabsty" align="center">
    	<tr>
	    	<td><font color=red>*</font>Login Name:</td><td><input type="text" name="yffmandef.name" id="name" /></td>
	    	<td>Description:</td><td><input type="text" name="yffmandef.describe" id="describe" /></td>
	    	<td>Password:</td><td><input type="password" name="yffmandef.passwd" id="passwd"/></td>
	    	<td>Power Supply:</td>
	    	<td><select name="yffmandef.orgId" id="orgId" disabled = "disabled"><option value="">--select--</option></select></td> 
	    </tr>
	    <tr>
	    	<td>Application Type:</td>
	    	<td><input type="checkbox" id="dyqx_chk" style="width:18px;" value="1"/><label for="dyqx_chk">Resident</label></td><!--
	    	<td><input type="checkbox" id="zbqx_chk" style="width:18px;" value="2"/><label for="zbqx_chk">专变</label></td>
	    	<td><input type="checkbox" id="npqx_chk" style="width:18px;" value="4"/><label for="npqx_chk">农排</label></td>	    
	    	-->
	    	<td>Permission Range:</td><td><select name="yffmandef.rank" id="rank"></select></td>	
	    	<td>Fee Control Archive Permissions:</td><td><select name="yffmandef.rese1_flag" id="rese1_flag" style="width: 150px"></select></td>
	    	<td>Public Archive Permissions:</td><td><select name="yffmandef.rese3_flag" id="rese3_flag" style="width: 150px"></select></td>	    	
	     </tr>
	    
	    <tr> 
	    	
	    	<td style="border-right:none;">User Management Permissions:</td><td width="18" style="border-left:none;"><input type="checkbox" id="rese2_flag_chk" style="width:18px;"></td>
	    	<td style="border-right:none;">Report Permission:</td><td width="18px" style="border-left:none;"><input type="checkbox" id="viewFlag_chk" style="width:18px;"></td>
	    	<td style="border-right:none;">Account Permission:</td><td width="18" style="border-left:none;"><input type="checkbox" id="openFlag_chk" style="width:18px;"></td>
	    	<td style="border-right:none;">The purchase of Electricity Permission:</td><td width="18" style="border-left:none;"><input type="checkbox" id="payFlag_chk" style="width:18px;"></td>
	    
	   	</tr>
	    <tr>
	      	<td style="border-right:none;">Modify Permissions:</td><td width="18" style="border-left:none;"><input type="checkbox" id="paraFlag_chk" style="width:18px;"></td>
	       	<td style="border-right:none;">Control Permissions:</td><td width="18" style="border-left:none;"><input type="checkbox" id="ctrlFlag_chk" style="width:18px;"></td>
	    	<td id="residentLabel">Residential Area:</td><td id="residentBtn"><button style="width: 40px;height:25px" id="selectConsDialog">...</button></td>
	    </tr>
    </table>
    	    <input type="hidden" id="id" 			name="yffmandef.id" />
	    	<input type="hidden" id="openflag" 		name="yffmandef.openflag" />
	    	<input type="hidden" id="payflag" 		name="yffmandef.payflag" />
	    	<input type="hidden" id="paraflag" 		name="yffmandef.paraflag" />
	    	<input type="hidden" id="viewflag" 		name="yffmandef.viewflag" />
	    	<input type="hidden" id="ctrlFlag" 		name="yffmandef.ctrlFlag" />
	    	<input type="hidden" id="rese2_flag" 	name="yffmandef.rese2_flag" />
	    	<input type="hidden" id="apptype" 		name="yffmandef.apptype"/>
    </form>
    <center>
    <jsp:include page="../inc/btn_tianjia.jsp"></jsp:include>
    &nbsp;&nbsp;
    <jsp:include page="../inc/btn_xiugai.jsp"></jsp:include>
    &nbsp;&nbsp;
    <jsp:include page="../inc/btn_pldel.jsp"></jsp:include>
    <jsp:include page="../inc/btn_excel.jsp"></jsp:include>
    </center>
    
    </div>
    <jsp:include page="../inc/jsdef.jsp" />
  	<SCRIPT>
  		$("#showmore").height($(".tabsty").height());
		$("#gridbox").height($(window).height()-$("#showmore").height()-104);
		var grid_title = "<i18n:message key="page.yffry.grid_title" />";
		var name = "<i18n:message key="doc.name" />";
		var rese2_flag = <%=((YffManDef)session.getAttribute(SDDef.SESSION_USERNAME)).getRese2_flag()%>;
		SDDef.YFF_APPTYPE_DYQX = <%= SDDef.YFF_APPTYPE_DYQX%>;
		SDDef.YFF_APPTYPE_GYQX = <%= SDDef.YFF_APPTYPE_GYQX%>;
		SDDef.YFF_APPTYPE_NPQX = <%= SDDef.YFF_APPTYPE_NPQX%>;
		SDDef.SPLITCOMA		   = '<%= SDDef.SPLITCOMA%>';
		SDDef.ADMIN			   = '<%= SDDef.ADMIN%>';
  	</SCRIPT>
  </body>
</html>
