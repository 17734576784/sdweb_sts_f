<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.Dict"%>
<%@page import="com.kesd.common.YDTable"%>
<%@page import="com.kesd.util.I18N"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>力调方案</title>
    <style type="text/css">
body {
	margin:0 0 0 0;
	overflow : auto;
	background: #eaeeef;
}
.tabsty {
	width:95%;
}
.tabsty input, .tabsty select {
	width: 140px;
}
input, select {
	font-size: 12px;
}
</style>
<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
  <script src="<%=basePath%>js/validate.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  <script src="<%=basePath%>js/common/loading.js"></script>		
  <jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
  <script  src="<%=basePath%>js/common/jsonString.js"></script>
  <script src="<%=basePath%>js/docs/docper.js"></script>
<style type="text/css">
	body{
		margin: 0 0 0 0;
		overflow: hidden;		
	  	background: #eaeeef;
	}	
	input,select{
		font-size: 12px;
	}
	</style>
<script type="text/javascript">
$(document).ready(function(){
    var params = window.dialogArguments;
    $("#index").val(params.index).css("background","#ccc");
    $("#cos_id").val(params.cos_id);
    $("#item_id").val(params.item_id);
	$("#realcos").val(params.realcos).css("background","#ccc");
	$("#dfchgarate").val(params.dfchgarate);
	$("#xiugai").click(function(){upadteCosItem()});
});
	function upadteCosItem(){
	    var params={};
		params.cos_id=$("#cos_id").val();
		params.item_id=$("#item_id").val();
		params.dfchgarate=Math.round((parseFloat($("#dfchgarate").val())*10000)/100);
		var json_str = jsonString.json2String(params);
		$.post(def.basePath + "/ajaxdocs/actCsStand!updateCosItem.action",{result:json_str},function(data){
			if(data.result == "fail"){
				alert("修改失败");
				return;
			}else{
				window.returnValue = "success";
		        window.close();
			}
	
		});	
	   
    };
</script>
  </head>
  <body>
    <!--  <form action="" method="post" id="addorupdate"  style='display:inline;'>-->
<form id=form1 style="padding-left:8"	>
<table class="tabsty" width="100%" >
	<tr style="text-align:center"><td style="width:40%;text-align:right">序号:</td><td><input id="index" readonly></td></tr>
	<tr style="text-align:center"><td style="width:40%;text-align:right">实际cos(%):</td><td><input id="realcos" readonly></td></tr>
	<tr style="text-align:center"><td style="width:40%;text-align:right">月电费增减(%):</td><td><input id="dfchgarate"></td></tr>
</table>
<input type="hidden" id="cos_id"/>
<input type="hidden" id="item_id"/>
  <center>
    <jsp:include page="../inc/btn_xiugai.jsp"></jsp:include>
    &nbsp;&nbsp;
    <jsp:include page="../inc/btn_guanbi.jsp"></jsp:include>
  </center>
</form>	
  </body>
</html>
