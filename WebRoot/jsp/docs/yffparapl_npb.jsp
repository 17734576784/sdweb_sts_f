<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>农排表excel批量导入</title>
    <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
    <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  	<script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  	<script src="<%=basePath%>js/common/def.js"></script>
  	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
  	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
  	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
  	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
  	<script src="<%=basePath%>js/common/loading.js"></script>
  </head>
  
  <body>
    <form id="frmfile" name="frmfile" style="display: inline" action ="<%=basePath%>excel/fileUploadNPB.action" method ="POST" ENCTYPE="multipart/form-data">
	    <table cellpadding="0" cellspacing="0" height="24" width="100%" class="page_tbl_bg22">
			<tr>
				<td width=500>&nbsp;&nbsp;
					<label for="myFile" style="font-size: 14px">导入excel文件:</label>
				   	<input type="file" name ="myFile" id="myFile" style="width: 240px; height:22px;"/>&nbsp;&nbsp;
				   	<input type="button" id="preview_btn" value="预览" style="height:22px"/>
				</td>
				<td align="right">		
					<input type="button" value="导入数据库" id="impData" style="width:80px; height:22px"/>
					<br>
				</td>
			</tr>
		</table>
	  </form>
	<div id=gridbox style="width: 100%;"></div>
  </body>
  <script type="text/javascript">
  	$("#gridbox").height($(window).height()-25);
  	basePath = '<%=basePath%>';
	var resultdata = '<%=(String)request.getAttribute("resultdata")%>';//上传excel文件中的数据
  </script>
  <script src="<%=basePath%>js/docs/yffparapl_npb.js"></script>
</html>
