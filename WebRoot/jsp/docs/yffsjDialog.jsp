<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.Dict"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><i18n:message key="doc.yffsjda" /></title>
    <style type="text/css">
	body{
	  margin:0 0 0 0;
	  overflow : auto;
	  background: #eaeeef;
	}
	.tabsty{
		width:95%;
	}
	.tabsty input,.tabsty select {
		width: 160px;
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
  <script src="<%=basePath%>js/docs/yffsjDialog.js"></script>
  <script src="<%=basePath%>js/docs/docper.js"></script>
  <script src="<%=basePath%>js/common/gridopt.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script src="<%=basePath%>js/common/dateFormat.js"></script>
  <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
  <script src="<%=basePath%>js/validate.js"></script>
  <script src="<%=basePath%>js/common/initDate.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  </head>
  
  <body>
  <form action="" method="post" id="addorupdate"  style='display:inline;'>
    <br/>
    <table class="tabsty" align="center">
    	<tr><td colspan="4" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;">
    	<table cellpadding="0" cellspacing="0"><tr><td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td><td style="border: 0px; font-size: 12px;"><i18n:message key="doc.jbda" /></td></tr></table>
    	</td></tr>
    	<tr>
	    	<td nowrap><font color=red>*</font><i18n:message key="doc.name" />: </td><td><input type="text" name="rtuPara.describe" id="describe" readonly style="background-color:#DCDCDC;border: 1px solid #808080;" /></td>
	    	<td nowrap><i18n:message key="doc.sybz" />: </td><td><select name="rtuPayCtrl.useFlag" id="useFlag" ></select></td>
	    </tr>
	    <tr>
	    	<td><i18n:message key="doc.yffqyrq" />: </td>
	    	<td>
	    		<input type="text" id="yffbgDateF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.ymd.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});" />
	    		<input type="hidden" name="rtuPayCtrl.yffbgDate" id="yffbgDate" />
	    	</td>
	    	<td><i18n:message key="doc.yffqysj" />: </td>
	    	<td>
	    		<input type="text" id="yffbgTimeF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.hms.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});" />
	    		<input type="hidden" name="rtuPayCtrl.yffbgTime" id="yffbgTime" />
	    	</td>
	    </tr>
    	<tr>
	    	<td><i18n:message key="doc.dzksrq" />: </td>
	    	<td>
	    		<input type="text" id="sg186bgDateF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.ymd.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});"/>
	    		<input type="hidden" name="rtuPayCtrl.sg186bgDate" id="sg186bgDate"/>
	    	</td>
	    	<td><i18n:message key="doc.dzkssj" />: </td>
	   		<td>
	   			<input type="text" id="sg186bgTimeF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.hms.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});" />
	   			<input type="hidden" name="rtuPayCtrl.sg186bgTime" id="sg186bgTime"/>
		    	<input type="hidden" id="rtuId" name="rtuPayCtrl.rtuId" />
		   	</td>
	     </tr>
	     
	     <tr>
	    	<td><i18n:message key="doc.bydate1"/>: </td>
	    	<td>
	    		<input type="text" id="reserve1DateF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.ymd.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});"/>
	    		<input type="hidden" name="rtuPayCtrl.reserve1Date" id="reserve1Date"/>
	    	</td>
	    	<td><i18n:message key="doc.bytime1"/>: </td>
	   		<td>
	   			<input type="text" id="reserve1TimeF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.hms.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});" />
	   			<input type="hidden" name="rtuPayCtrl.reserve1Time" id="reserve1Time"/>
		   	</td>
	      </tr>
	      
	      <tr>
	    	<td><i18n:message key="doc.bydate2"/>: </td>
	    	<td>
	    		<input type="text" id="reserve2DateF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.ymd.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});"/>
	    		<input type="hidden" name="rtuPayCtrl.reserve2Date" id="reserve2Date"/>
	    	</td>
	    	<td><i18n:message key="doc.bytime2"/>: </td>
	   		<td>
	   			<input type="text" id="reserve2TimeF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.hms.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});" />
	   			<input type="hidden" name="rtuPayCtrl.reserve2Time" id="reserve2Time"/>
		   	</td>
	      </tr>
	      
	      <tr>
	    	<td><i18n:message key="doc.bydate3"/>: </td>
	    	<td>
	    		<input type="text" id="reserve3DateF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.ymd.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});"/>
	    		<input type="hidden" name="rtuPayCtrl.reserve3Date" id="reserve3Date"/>
	    	</td>
	    	<td><i18n:message key="doc.bytime3"/>: </td>
	   		<td>
	   			<input type="text" id="reserve3TimeF" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.hms.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});" />
	   			<input type="hidden" name="rtuPayCtrl.reserve3Time" id="reserve3Time"/>
		   	</td>
	      </tr>
    </table>
    <center>
    <jsp:include page="../inc/btn_xiugai.jsp"></jsp:include>
    &nbsp;&nbsp;
    <jsp:include page="../inc/btn_guanbi.jsp"></jsp:include>
    </center>
    </form>
    <input type="hidden" id="preconsId" />
    <script type="text/javascript">
    var update_fail = "<i18n:message key="comm.gridopt.update_fail" />";
 	var SDDef={
	SUCCESS : '<%=SDDef.SUCCESS%>',
	FAIL : '<%=SDDef.FAIL%>',
	TRUE : '<%=SDDef.TRUE%>',
	FALSE : '<%=SDDef.FALSE%>',
	EMPTY : '<%=SDDef.EMPTY%>',
	SPLITCOMA: '<%=SDDef.SPLITCOMA%>',
	BASEPATH : '<%=basePath%>'
	};
    var Dict = {};
    var YDTable = {};
    SDDef.SPLITCOMA = "<%=SDDef.SPLITCOMA%>";
    SDDef.APPTYPE_ZB = "<%=SDDef.APPTYPE_ZB%>";
    Dict.DICTITEM_USEFLAG = "<%=Dict.DICTITEM_USEFLAG%>";
    </script>
  </body>
</html>
