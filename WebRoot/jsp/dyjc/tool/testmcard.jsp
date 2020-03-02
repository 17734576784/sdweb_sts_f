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
		
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setConsExt.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/testmcard.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>		
		<script  src="<%=basePath%>js/common/dateFormat.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
		
	</head>
	<body>
	<table id="tabinfo" class="tabinfo" align="center">
    	<tr><td colspan="4" style="height:10px; border: 0px;"></td></tr>
    	<tr>
	    	<td  class="td_lable" style="height:25px;">Type</td>	    		
	    </tr>
   		<tr>
   			<td></td>
   			<td width = "55">Type:</td>
   			<td><select name="testtype" id="testtype" >
   				<option value="0">[0]test all the contents</option>
   				<option value="1">[1]test relay</option>
   				<option value="2">[2]test LCD display</option>
   				<option value="3">[3]test total energy</option>
   				<option value="4">[4]test max power limit</option>
   				<option value="5">[5]display current meter status</option>
   				<option value="6">[6]display current power</option>
   				<option value="7">[7]display meter version number </option>
   				<option value="8">[8]display current tariff unit price</option>
   				<option value="9">[9]display overload current limit</option>
   				<option value="10">[10]display credit numbers</option>
   				<option value="11">[11]display serial number of meter</option>
   				<option value="12">[12]display off numbers of relay</option>
   				<option value="13">[13]enter into accuracy test mode</option>
   			</select>
   			</td>	    
	    </tr>
	    <tr><td colspan="4" style="height:15px; border: 0px;"></td></tr>
	    <tr>
	    	<td colspan=4 style="padding-left:10px; border: 0px;" align = "right"><button id="cardinfo" class="btn" >Card Information</button> &nbsp;&nbsp;&nbsp;&nbsp;<button id="writecard"  class="btn">Make Test Card</button>&nbsp;&nbsp;</td>
	    </tr>
	</table>
	
	<jsp:include page="../../inc/jsdef.jsp"></jsp:include>
	
	<script type="text/javascript">
		var SDDef = {};
		SDDef.STR_OR  = "<%=SDDef.STR_OR%>";
		SDDef.SUCCESS = "<%=SDDef.SUCCESS%>";
		var YDTable = {};
		YDTable.TABLECLASS_OCARDTYPE_PARA = '<%=YDTable.TABLECLASS_OCARDTYPE_PARA%>';
		
	</script>
	</body>
</html>
