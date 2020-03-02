<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.YDTable"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>区片档案</title>
    
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
		width: 120px;
	}
	input,select{
		font-size: 12px;
	}
	</style>
	
	<script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script src="<%=basePath%>js/docs/areaparaDialog.js"></script>
	<script src="<%=basePath%>js/docs/docper.js"></script>
	<script src="<%=basePath%>js/common/modalDialog.js"></script>
	<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=basePath%>js/validate.js"></script>
	<script src="<%=basePath%>js/common/def.js"></script>
	<script src="<%=basePath%>js/common/loading.js"></script>
	<script src="<%=basePath%>js/common/dateFormat.js"></script>
	<jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
	<jsp:include page="../../jsp/inc/jsdef.jsp"></jsp:include>

  </head>
  
  <body>
    <form action="" method="post" id="addorupdate"  style='display:inline;'>
    <br/>
    <table class="tabsty" align="center">
    	<tr><td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;">
    	<table cellpadding="0" cellspacing="0"><tr><td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td><td style="border: 0px; font-size: 12px;">基本档案</td></tr></table>
    	</td></tr>
    	<tr>
	    	<td><font color=red>*</font>名称:</td><td><input type="text" name="ap.describe" id="describe" readonly style="background:#eee;border: 1px solid #808080;" /></td>
	    	<td>所属供电所:</td><td>
	    	<input type="text" id="orgId_" readonly style="background:#eee;border: 1px solid #808080;width: 160px;" />
	    	<input type="hidden" name="ap.orgId" id="orgId" />
	    	</td>
	    	<td><font color=red>*</font>区域号:</td>
	    	<td><input type="text" name="ap.areaCode" id="areaCode" /></td>
    	</tr>
    	<tr>
	      <td>费率方案:</td>
	      <td>
	      <select name="ap.feeprojId" id="feeprojId" ></select>
	      </td>
	    	<td>报警方案:</td>
	    	<td>
	    	<select name = "ap.yffalarmId" id= "yffalarmId" style="width: 160px;"></select>
	    	</td>
	    	<td>费率启用日期:</td><td>
	    	<input type="hidden" name="ap.feeBegindate" id="feeBegindate" />
	    	<input type="text" id="feeBegindate_" readonly onFocus="WdatePicker({dateFmt:'<i18n:message key="date.ymd.fmt" />',isShowClear:'false',readOnly:'true',lang:'<i18n:message key="date.lang" />',position:{button:'above'}});" />
	    	</td>
    	</tr>
        <tr>
	      <td>接口编码1:</td>
	      <td><input type="text" name="ap.infCode1" id="infCode1" /></td>
	    	<td>接口编码2:</td>
	    	<td><input type="text" name="ap.infCode2" id="infCode2" style="width: 160px;" /></td>
	    	<td></td><td></td>
    	</tr>
    	<tr><td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>
    	<tr>
	    	<td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;">
	    	<table cellpadding="0" cellspacing="0"><tr><td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td><td style="border: 0px; font-size: 12px;">备注</td></tr></table>
	    	</td>
	    </tr>
    	<tr>
    	<td colspan=6 align="center">
    	<textarea rows="4" style="width:100%;" name="ap.remark" id="remark"></textarea>
    	</td>
    	</tr>
    </table>
    <center>
    <jsp:include page="../inc/btn_xiugai.jsp"></jsp:include>
    &nbsp;&nbsp;
    <jsp:include page="../inc/btn_guanbi.jsp"></jsp:include>
    </center>
    <input type="hidden" name="ap.id" id="id" />
    </form>
    <input type="hidden" id="preconsId" />
    <script type="text/javascript">
    var SDDef = {};
    var YDTable = {};
    SDDef.BASEPATH = "<%=basePath%>";
    SDDef.SPLITCOMA = "<%=SDDef.SPLITCOMA%>";
    SDDef.APPTYPE_NP = "<%=SDDef.APPTYPE_NP%>";
    YDTable.TABLECLASS_ORGPARA = "<%=YDTable.TABLECLASS_ORGPARA%>";
    YDTable.TABLECLASS_YFFRATEPARA = "<%=YDTable.TABLECLASS_YFFRATEPARA%>";
    YDTable.TABLECLASS_YFFALARMPARA = "<%=YDTable.TABLECLASS_YFFALARMPARA%>";
    </script>
  </body>
</html>
