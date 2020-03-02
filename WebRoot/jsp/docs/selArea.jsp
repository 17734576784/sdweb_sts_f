<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="com.kesd.common.YDTable"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>片区选择</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<link rel="STYLESHEET" type="text/css" href="css/zh/dhtmlx/dhtmlxtree.css">
	<style type="text/css">
	body{
	  margin:0 0 0 0;
	  overflow:auto;
	}
	.tabsty input,.tabsty select {
		width: 90px;
	}
	input,select{
		font-size: 12px;
	}
	</style>
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css" id="css_theme">
	<jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>js/dhtmlx/tree/dhtmlxcommontree.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/dhtmlx/tree/dhtmlxtree.js"></script>	
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	

  </head>
  
  <body>
  <table style="font-size: 12px; width: 100%;" cellpadding="0" cellspacing="0" border="0">
  <tr>
  <td>
  	<table cellpadding="0" cellspacing="0"><tr>
  	<td class="titlel1" id="mtree_l"></td>
  	<td class="titlem1" style="width: 70px;" id="mtree">管理结构</td>
  	<td class="titler1" id="mtree_r"></td>
  	<td>&nbsp;</td>
  	</tr></table>
  </td>
  </tr>
  <tr><td>
   <div id="treeM" style="width:100%; height:355px;padding-left:2px; overflow:auto; border:1px solid gray;"></div>
  </td></tr>
   </table>
   <center style="padding-top: 5px;">
    <input type="button" value="选 择" id="select" />&nbsp;&nbsp;<input type="button" value="关 闭" id="close" />
    </center>
  </body>
  <script type="text/javascript">
  var param = window.dialogArguments;
  var img_path_dialog = param.img_path_dialog;
  YDTable.TABLECLASS_AREAPARA = "<%=YDTable.TABLECLASS_AREAPARA%>";
  </script>
  <script type="text/javascript" src="<%=basePath%>js/css.js"></script>
  
  <script type="text/javascript">
  var treeM = new dhtmlXTreeObject("treeM", "100%", "100%", 0);
	treeM.setImagePath("<%=basePath%>images/tree/imgs/");
	treeM.attachEvent("onDblClick", function(id){select(treeM);});
	
	var url = "<%=basePath%>ajax/actTree!selArea.action";
	$.post(url,{},function(data){
		if(data.result!=""){
			treeM.loadXMLString(data.result, function(){
				if(param.aid != ""){
					treeM.selectItem(YDTable.TABLECLASS_AREAPARA + "_" + param.aid);
				}
			});
		}
	});
	
	$("#select").click(function(){
		select(treeM);
	});
	
	$("#close").click(function(){
		window.close();
	});
	
	function select(tree){
		var nodeid = tree.getSelectedItemId();
		if(nodeid.indexOf(YDTable.TABLECLASS_AREAPARA) >= 0){
			txt = tree.getItemText(nodeid);
			id = nodeid.split("_")[1];
			
			window.returnValue = {id : id, desc : txt};
			window.close();
		}
	}
  </script>
</html>
