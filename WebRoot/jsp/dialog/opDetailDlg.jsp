<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>详细信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  </head>
  
  <body onunload="onclose()">
  <table width="100%" align="center" height="100%" border="0">
  <tr><td>
  <textarea id="opdetail" style="width: 100%; height: 100%;"></textarea>
  </td></tr>
  <tr>
  <td align="right" height="20">
  <input type="button" value="清空" onclick="$('#opdetail').val('');"/>
  &nbsp;
  <input type="button" value="关闭" onclick="window.close();"/>  
  </td>
  </tr>
  </table>
  </body>
  <script type="text/javascript">
  	window.top.parent_window = window.dialogArguments;
	
  	if (window.top.parent_window != null) {
  		window.top.parent_window.glo_opdetail_dlg = window;
  		window.top.parent_window = window;
  	}
//
//	//未完成,当对话框第一次打开时 显示前一条记录
//	$(document).ready(function(){
//	  	if (window.top.parent_window != null) {
//  			if (window.top.parent_window.glo_opdetail_str != null) {
//				var opdetail = window.document.getElementById("opdetail");
//  			}
//  		}
//	});
//  
  function onclose() {
  	if (window.top.parent_window != null) {
  		window.top.parent_window.glo_opdetail_dlg = null
  	}
  }
  </script>
</html>
