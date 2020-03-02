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
          	<td class="tdm" >Crédit de transfert pour résident</td>
          	<td style="text-align: right;"><button class="btn" id="preview1">Aperçu</button>&nbsp;&nbsp;<button class="btn" id="default1">Définir par défaut</button>&nbsp;&nbsp;</td>      		          	
          	<td>&nbsp;</td></tr>
       	</table> 	
 		<center><DIV id=gridbox1 class="gridbox"></DIV></center>
	 	</div>
	
    	<div style="display:<%=WebConfig.getShowFlag("dy_leftmenu1")%>">
    	<table class="mytbl" cellpadding="0" cellspacing="0" align="center" style="width:98%;font-size: 12px;height:22px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><tr>
       		<td style="width:30px; "><img src="<%=basePath%>images/doctitle.png" style="margin-top: 3px;margin-left: 5px;"/></td>
          	<td class="tdm" >Contrôle des frais des résidents locaux (à distance)</td>
          	<td style="text-align: right;"><button class="btn" id="preview10">Aperçu</button>&nbsp;&nbsp;<button class="btn" id="default10">Définir par défaut</button>&nbsp;&nbsp;</td>      		          	
          	<td>&nbsp;</td></tr>
       	</table> 	
 		<center><DIV id=gridbox10 class="gridbox"></DIV></center>
 		</div>
    	
    	<div style="display:<%=WebConfig.getShowFlag("dy_leftmenu1")%>">
    	<table class="mytbl" cellpadding="0" cellspacing="0" align="center" style="width:98%;font-size: 12px;height:22px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><tr>
       		<td style="width:30px; "><img src="<%=basePath%>images/doctitle.png" style="margin-top: 3px;margin-left: 5px;"/></td>
          	<td class="tdm" >Contrôle des frais de la gare résidente principale</td>
          	<td style="text-align: right;"><button class="btn" id="preview3">Aperçu</button>&nbsp;&nbsp;<button class="btn" id="default3">Définir par défaut</button>&nbsp;&nbsp;</td>      		          	
          	<td>&nbsp;</td></tr>
       	</table> 	
 		<center><DIV id=gridbox3 class="gridbox"></DIV></center>
		</div>
    	
    	<div style="display:none">
    	<table class="mytbl" cellpadding="0" cellspacing="0" align="center" style="width:98%;font-size: 12px;height:22px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><tr>
       		<td style="width:30px; "><img src="<%=basePath%>images/doctitle.png" style="margin-top: 3px;margin-left: 5px;"/></td>
          	<td class="tdm" >Changement dédié - contrôle des frais locaux (type de carte) - montre intelligente</td>
          	<td style="text-align: right;"><button class="btn" id="preview4">Aperçu</button>&nbsp;&nbsp;<button class="btn" id="default4">Définir par défaut</button>&nbsp;&nbsp;</td>      		          	
          	<td>&nbsp;</td></tr>
       	</table> 	
 		<center><DIV id=gridbox4 class="gridbox"></DIV></center>
	  	</div>
    	
    	<div style="display:none">
    	<table class="mytbl" cellpadding="0" cellspacing="0" align="center" style="width:98%;font-size: 12px;height:22px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><tr>
       		<td style="width:30px; "><img src="<%=basePath%>images/doctitle.png" style="margin-top: 3px;margin-left: 5px;"/></td>
          	<td class="tdm" >Contrôle spécial des frais de changement local (type de carte) -6103</td>
          	<td style="text-align: right;"><button class="btn" id="preview5">Aperçu</button>&nbsp;&nbsp;<button class="btn" id="default5">Définir par défaut</button>&nbsp;&nbsp;</td>      		          	
          	<td>&nbsp;</td></tr>
       	</table> 	
 		<center><DIV id=gridbox5 class="gridbox"></DIV></center>
	  	</div>
    	
    	<div style="display:none">
    	<table class="mytbl" cellpadding="0" cellspacing="0" align="center" style="width:98%;font-size: 12px;height:22px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><tr>
       		<td style="width:30px; "><img src="<%=basePath%>images/doctitle.png" style="margin-top: 3px;margin-left: 5px;"/></td>
          	<td class="tdm" >Contrôle exclusif des frais de changement local (bas du tableau distant)</td>
          	<td style="text-align: right;"><button class="btn" id="preview7">Aperçu</button>&nbsp;&nbsp;<button class="btn" id="default7">Définir par défaut</button>&nbsp;&nbsp;</td>      		          	
          	<td>&nbsp;</td></tr>
       	</table> 	
 		<center><DIV id=gridbox7 class="gridbox"></DIV></center>
	  	</div>
    	
    	<div style="display:none">
	  	<table class="mytbl" cellpadding="0" cellspacing="0" align="center" style="width:98%;font-size: 12px;height:22px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><tr>
       		<td style="width:30px; "><img src="<%=basePath%>images/doctitle.png" style="margin-top: 3px;margin-left: 5px;"/></td>
          	<td class="tdm" >Contrôle exclusif des frais de changement local (montant à distance)</td>
          	<td style="text-align: right;"><button class="btn" id="preview6">Aperçu</button>&nbsp;&nbsp;<button class="btn" id="default6">Définir par défaut</button>&nbsp;&nbsp;</td>      		          	
          	<td>&nbsp;</td></tr>
       	</table> 	
 		<center><DIV id=gridbox6 class="gridbox"></DIV></center>
	  	</div>
    	
    	<div style="display:none">
	 	<table class="mytbl" cellpadding="0" cellspacing="0" align="center" style="width:98%;font-size: 12px;height:22px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><tr>
       		<td style="width:30px; "><img src="<%=basePath%>images/doctitle.png" style="margin-top: 3px;margin-left: 5px;"/></td>
          	<td class="tdm" >Contrôle spécial des frais de la station principale</td>
          	<td style="text-align: right;"><button class="btn" id="preview8">Aperçu</button>&nbsp;&nbsp;<button class="btn" id="default8">Définir par défaut</button>&nbsp;&nbsp;</td>      		          	
          	<td>&nbsp;</td></tr>
       	</table> 	
 		<center><DIV id=gridbox8 class="gridbox"></DIV></center>
	  	</div>
	  	
    	<div style="display:none">
	 	<table class="mytbl" cellpadding="0" cellspacing="0" align="center" style="width:98%;font-size: 12px;height:22px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><tr>
       		<td style="width:30px; "><img src="<%=basePath%>images/doctitle.png" style="margin-top: 3px;margin-left: 5px;"/></td>
          	<td class="tdm" >Contrôle des frais de ligne locale de ferme (type de carte)</td>
          	<td style="text-align: right;"><button class="btn" id="preview9">Aperçu</button>&nbsp;&nbsp;<button class="btn" id="default9">Définir par défaut</button>&nbsp;&nbsp;</td>      		          	
          	<td>&nbsp;</td></tr>
       	</table> 	
 		<center><DIV id=gridbox9 class="gridbox"></DIV></center>
	  	</div>
	  	
	  		 	
    	<div style="display:<%=WebConfig.getShowFlag("dy_leftmenu8")%>">
    	<table class="mytbl" cellpadding="0" cellspacing="0" align="center" style="width:98%;font-size: 12px;height:22px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><tr>
       		<td style="width:30px; "><img src="<%=basePath%>images/doctitle.png" style="margin-top: 3px;margin-left: 5px;"/></td>
          	<td class="tdm" >Ouverture d'un compte par un résident</td>
          	<td style="text-align: right;"><button class="btn" id="preview2">Aperçu</button>&nbsp;&nbsp;<button class="btn" id="default2">Définir par défaut</button>&nbsp;&nbsp;</td>      		          	
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
