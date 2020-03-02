<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
<%@page import="com.kesd.common.CommFunc"%>
<%@page import="com.kesd.dbpara.YffManDef"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		
		<script type="text/javascript">
			var basePath = '<%=basePath%>';
		</script>
		
		<script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script src="<%=basePath%>js/common/loading.js"></script>
		<script src="<%=basePath%>js/common/def.js"></script>
		<script src="<%=basePath%>js/other/print.js"></script>
		<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
		<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_data.js"></script>
		<script src="<%=basePath%>js/validate.js"></script>
 	
 		 <style type="text/css">
			.mytbl{
				margin-top:8px;
				border-top: 1px solid #ccc;
				border-left: 1px solid #ccc;
				border-right: 1px solid #ccc;
			}	
			.gridbox{
				border: 1px solid #ccc; 
				width: 98%;
				height:100px;
			}
			.tdm{
				width:150px;
				padding-top: 5px; 
				font-size: 12px;
			}
		</style>
		
		
	</head>
	<body>	
   	<div id="div_pg" style="overflow: auto;padding-bottom:10px;">
    	<div style="display:<%=WebConfig.getShowFlag("dy_leftmenu0")%>">
    	<table class="mytbl" cellpadding="0" cellspacing="0" align="center" style="width:98%;font-size: 12px;height:22px;background: url('<%=basePath%>images/bg22.gif') repeat-x; " ><tr>
       		<td style="width:30px; "><img src="<%=basePath%>images/doctitle.png" style="margin-top: 3px;margin-left: 5px;"/></td>
          	<td class="tdm" >Resident-Transfer Credit</td>
          	<td style="text-align: right;"><button class="btn" id="preview1">Preview</button>&nbsp;&nbsp;<button class="btn" id="default1">Set to default</button>&nbsp;&nbsp;</td>      		          	
          	<td>&nbsp;</td></tr>
       	</table> 	
 		<center><DIV id=gridbox1 class="gridbox"></DIV></center>
	 	</div>
	
    	<div style="display:<%=WebConfig.getShowFlag("dy_leftmenu1")%>">
    	<table class="mytbl" cellpadding="0" cellspacing="0" align="center" style="width:98%;font-size: 12px;height:22px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><tr>
       		<td style="width:30px; "><img src="<%=basePath%>images/doctitle.png" style="margin-top: 3px;margin-left: 5px;"/></td>
          	<td class="tdm" >居民-本地费控(远程)</td>
          	<td style="text-align: right;"><button class="btn" id="preview10">预览</button>&nbsp;&nbsp;<button class="btn" id="default10">设为默认</button>&nbsp;&nbsp;</td>      		          	
          	<td>&nbsp;</td></tr>
       	</table> 	
 		<center><DIV id=gridbox10 class="gridbox"></DIV></center>
 		</div>
    	
    	<div style="display:<%=WebConfig.getShowFlag("dy_leftmenu1")%>">
    	<table class="mytbl" cellpadding="0" cellspacing="0" align="center" style="width:98%;font-size: 12px;height:22px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><tr>
       		<td style="width:30px; "><img src="<%=basePath%>images/doctitle.png" style="margin-top: 3px;margin-left: 5px;"/></td>
          	<td class="tdm" >居民-主站费控</td>
          	<td style="text-align: right;"><button class="btn" id="preview3">预览</button>&nbsp;&nbsp;<button class="btn" id="default3">设为默认</button>&nbsp;&nbsp;</td>      		          	
          	<td>&nbsp;</td></tr>
       	</table> 	
 		<center><DIV id=gridbox3 class="gridbox"></DIV></center>
		</div>
    	
    	<div style="display:none">
    	<table class="mytbl" cellpadding="0" cellspacing="0" align="center" style="width:98%;font-size: 12px;height:22px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><tr>
       		<td style="width:30px; "><img src="<%=basePath%>images/doctitle.png" style="margin-top: 3px;margin-left: 5px;"/></td>
          	<td class="tdm" >专变-本地费控(卡式)-智能表</td>
          	<td style="text-align: right;"><button class="btn" id="preview4">预览</button>&nbsp;&nbsp;<button class="btn" id="default4">设为默认</button>&nbsp;&nbsp;</td>      		          	
          	<td>&nbsp;</td></tr>
       	</table> 	
 		<center><DIV id=gridbox4 class="gridbox"></DIV></center>
	  	</div>
    	
    	<div style="display:none">
    	<table class="mytbl" cellpadding="0" cellspacing="0" align="center" style="width:98%;font-size: 12px;height:22px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><tr>
       		<td style="width:30px; "><img src="<%=basePath%>images/doctitle.png" style="margin-top: 3px;margin-left: 5px;"/></td>
          	<td class="tdm" >专变-本地费控(卡式)-6103</td>
          	<td style="text-align: right;"><button class="btn" id="preview5">预览</button>&nbsp;&nbsp;<button class="btn" id="default5">设为默认</button>&nbsp;&nbsp;</td>      		          	
          	<td>&nbsp;</td></tr>
       	</table> 	
 		<center><DIV id=gridbox5 class="gridbox"></DIV></center>
	  	</div>
    	
    	<div style="display:none">
    	<table class="mytbl" cellpadding="0" cellspacing="0" align="center" style="width:98%;font-size: 12px;height:22px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><tr>
       		<td style="width:30px; "><img src="<%=basePath%>images/doctitle.png" style="margin-top: 3px;margin-left: 5px;"/></td>
          	<td class="tdm" >专变-本地费控(远程表底)</td>
          	<td style="text-align: right;"><button class="btn" id="preview7">预览</button>&nbsp;&nbsp;<button class="btn" id="default7">设为默认</button>&nbsp;&nbsp;</td>      		          	
          	<td>&nbsp;</td></tr>
       	</table> 	
 		<center><DIV id=gridbox7 class="gridbox"></DIV></center>
	  	</div>
    	
    	<div style="display:none">
	  	<table class="mytbl" cellpadding="0" cellspacing="0" align="center" style="width:98%;font-size: 12px;height:22px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><tr>
       		<td style="width:30px; "><img src="<%=basePath%>images/doctitle.png" style="margin-top: 3px;margin-left: 5px;"/></td>
          	<td class="tdm" >专变-本地费控(远程金额)</td>
          	<td style="text-align: right;"><button class="btn" id="preview6">预览</button>&nbsp;&nbsp;<button class="btn" id="default6">设为默认</button>&nbsp;&nbsp;</td>      		          	
          	<td>&nbsp;</td></tr>
       	</table> 	
 		<center><DIV id=gridbox6 class="gridbox"></DIV></center>
	  	</div>
    	
    	<div style="display:none">
	 	<table class="mytbl" cellpadding="0" cellspacing="0" align="center" style="width:98%;font-size: 12px;height:22px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><tr>
       		<td style="width:30px; "><img src="<%=basePath%>images/doctitle.png" style="margin-top: 3px;margin-left: 5px;"/></td>
          	<td class="tdm" >专变-主站费控</td>
          	<td style="text-align: right;"><button class="btn" id="preview8">预览</button>&nbsp;&nbsp;<button class="btn" id="default8">设为默认</button>&nbsp;&nbsp;</td>      		          	
          	<td>&nbsp;</td></tr>
       	</table> 	
 		<center><DIV id=gridbox8 class="gridbox"></DIV></center>
	  	</div>
	  	
    	<div style="display:none">
	 	<table class="mytbl" cellpadding="0" cellspacing="0" align="center" style="width:98%;font-size: 12px;height:22px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><tr>
       		<td style="width:30px; "><img src="<%=basePath%>images/doctitle.png" style="margin-top: 3px;margin-left: 5px;"/></td>
          	<td class="tdm" >农排-本地费控(卡式)</td>
          	<td style="text-align: right;"><button class="btn" id="preview9">预览</button>&nbsp;&nbsp;<button class="btn" id="default9">设为默认</button>&nbsp;&nbsp;</td>      		          	
          	<td>&nbsp;</td></tr>
       	</table> 	
 		<center><DIV id=gridbox9 class="gridbox"></DIV></center>
	  	</div>
	  	
	  		 	
    	<div style="display:<%=WebConfig.getShowFlag("dy_leftmenu8")%>">
    	<table class="mytbl" cellpadding="0" cellspacing="0" align="center" style="width:98%;font-size: 12px;height:22px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><tr>
       		<td style="width:30px; "><img src="<%=basePath%>images/doctitle.png" style="margin-top: 3px;margin-left: 5px;"/></td>
          	<td class="tdm" >Resident-Opening an Account</td>
          	<td style="text-align: right;"><button class="btn" id="preview2">Preview</button>&nbsp;&nbsp;<button class="btn" id="default2">Set to default</button>&nbsp;&nbsp;</td>      		          	
          	<td>&nbsp;</td></tr>
       	</table> 	
 		<center><DIV id=gridbox2 class="gridbox"></DIV></center>
 		</div>
 	
 	</div>
   
    
	
	
	
	<script type="text/javascript">
	
	$("#div_pg").height($(window).height());
	var ComntUseropDef = {};
	ComntUseropDef.YFF_GET_PAUSEALARM = <%=com.kesd.comntpara.ComntUseropDef.YFF_GET_PAUSEALARM%>;
	ComntUseropDef.YFF_SET_PAUSEALARM = <%=com.kesd.comntpara.ComntUseropDef.YFF_SET_PAUSEALARM%>;
	
	//需要与web_cfg.xml中的menu项中的菜单id关联
	var mygridlist=[null,new dhtmlXGridObject('gridbox1'),new dhtmlXGridObject('gridbox2'),new dhtmlXGridObject('gridbox3'),new dhtmlXGridObject('gridbox4'),new dhtmlXGridObject('gridbox5'),new dhtmlXGridObject('gridbox6'),new dhtmlXGridObject('gridbox7'),new dhtmlXGridObject('gridbox8'),new dhtmlXGridObject('gridbox9'),new dhtmlXGridObject('gridbox10')];
	var showlist =['','<%=WebConfig.getShowFlag("dy_leftmenu0")%>','<%=WebConfig.getShowFlag("dy_leftmenu8")%>','<%=WebConfig.getShowFlag("dy_leftmenu2")%>','<%=WebConfig.getShowFlag("gy_leftmenu0")%>','<%=WebConfig.getShowFlag("gy_leftmenu0")%>','<%=WebConfig.getShowFlag("gy_leftmenu1")%>','<%=WebConfig.getShowFlag("gy_leftmenu2")%>','<%=WebConfig.getShowFlag("gy_leftmenu3")%>','<%=WebConfig.getShowFlag("gy_leftmenu3")%>','<%=WebConfig.getShowFlag("gy_leftmenu1")%>']
	
	</script>
	</body>
</html>
