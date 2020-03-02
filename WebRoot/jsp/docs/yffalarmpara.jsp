<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
  <title>My JSP 'yffalarmpara.jsp' starting page</title>
  
  </head>
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  <style type="text/css">
	body{
	  margin:0 0 0 0;
	  overflow:hidden;
	}
	.tabsty input,.tabsty select {
		width: 90px;
	}
	input,select{
		font-size: 12px;
	}
  </style>
  <script>
	var basePath = '<%=basePath%>';
  </script>
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/common/gridopt.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script src="<%=basePath%>js/docs/yffalarmpara.js"></script>
  <script src="<%=basePath%>js/docs/pub_docper.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
  <script src="<%=basePath%>js/validate.js"></script>
  <jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
  <body>
  <DIV id=gridbox style="width: 100%;"></DIV>
  	<table width="100%" border="0" class="page_tbl">
    <tr><td id="pageinfo">&nbsp;</td><td align="right" id="soh"><i18n:message key="doc.hide_detail" /></td><td width="15"><img src="<%=basePath%>images/mmd.gif" alt="hide" onClick="gridopt.showOrHide(this);" style="cursor: pointer;"/></td></tr>
    </table>
    <div id="showmore">
    <form action="" method="post" id="addorupdate"  style='display:inline;'>
    <br/>
    <table class="tabsty" align="center">
    	<tr>
	    	<td><font color=red>*</font><i18n:message key="doc.name" />: </td><td><input type="text" name="yffalarmpara.describe" id="describe" style="width:180px;" /></td>
	    	<td><i18n:message key="doc.bjfs" />: </td><td><select name="yffalarmpara.type" id="type" style="width:120px;" ></select></td>
	    	<td><span id="alarm1_text"><i18n:message key="doc.bjz" />1: </span></td><td><input type="text" name="yffalarmpara.alarm1" id="alarm1"/></td>
	    	<td><span id="alarm2_text"><i18n:message key="doc.bjz" />2: </span></td><td><input type="text" name="yffalarmpara.alarm2" id="alarm2"/></td>
	    </tr>
	    <tr>
	    	<td style="border-right:none;"><i18n:message key="doc.jfdxtz" />: </td><td width="18" nowrap style="border-left:none;"><input type="checkbox" id="payalmFlag_chk" style="width:18px;"></td>
	    	<td style="border-right:none;"><i18n:message key="doc.hzdxtz" />: </td><td width="18" nowrap style="border-left:none;"><input type="checkbox" id="hzalmFlag_chk" style="width:18px;"></td>
	    	<td style="border-right:none;"><i18n:message key="doc.tzdxtz" />: </td><td width="18" nowrap style="border-left:none;"><input type="checkbox" id="tzalmFlag_chk" style="width:18px;"></td>
	    	<td colspan=2></td>
	    </tr>
	   	<tr>
	    	<td style="border-right:none;"><i18n:message key="doc.fsgjdx" />:</td><td width="18" nowrap style="border-left:none;"><input type="checkbox" id="dxalmFlag_chk" style="width:18px;"></td>
	    	<td style="border-right:none;"><i18n:message key="doc.qygjsy" />:</td><td width="18" nowrap style="border-left:none;"><input type="checkbox" id="syalmFlag_chk" style="width:18px;"></td>
	    	<td colspan=4></td>
	   	</tr>
	   	<tr>
	    	<td style="border-right:none;"><i18n:message key="doc.dxcghkz" />: </td><td width="18" nowrap style="border-left:none;"><input type="checkbox" id="dxalmcgkFlag_chk" style="width:18px;"></td>
	    	<td style="border-right:none;"><i18n:message key="doc.sycghkz" />: </td><td width="18" nowrap style="border-left:none;"><input type="checkbox" id="syalmcgkFlag_chk" style="width:18px;"></td>
	    	<td colspan=4><input type="hidden" id="id" name="yffalarmpara.id"/>
	    	<input type="hidden" id="payalmFlag" name="yffalarmpara.payalmFlag" />
	    	<input type="hidden" id="hzalmFlag" name="yffalarmpara.hzalmFlag" />
	    	<input type="hidden" id="tzalmFlag" name="yffalarmpara.tzalmFlag" />
	    	<input type="hidden" id="dxalmFlag" name="yffalarmpara.dxalmFlag" />
	    	<input type="hidden" id="syalmFlag" name="yffalarmpara.syalmFlag" />
	    	<input type="hidden" id="dxalmcgkFlag" name="yffalarmpara.dxalmcgkFlag" />
	    	<input type="hidden" id="syalmcgkFlag" name="yffalarmpara.syalmcgkFlag" />
	    	</td>
	   	</tr>
    </table>
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
    	var grid_title = "<i18n:message key="page.bjfa.grid_title" />";
    	var doc_name = "<i18n:message key="doc.name" />";
    	var bjz = "<i18n:message key="doc.bjz" />";
  		$("#showmore").height($(".tabsty").height());
		$("#gridbox").height($(window).height()-$("#showmore").height()-34);
  	</SCRIPT>
  </body>
</html>
