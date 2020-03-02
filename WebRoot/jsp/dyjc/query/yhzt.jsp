<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page	import="com.kesd.common.WebConfig"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'yhzt.jsp' starting page</title>
    <link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />		
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
	<style type="text/css">
	input,select{
	width:90px;
	}
	</style>
	<script>
		var basePath = '<%=basePath%>';
	</script>
	<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
	<script  src="<%=basePath%>js/common/gridopt.js"></script>
	<script  src="<%=basePath%>js/dyjc/query/yhzt.js"></script>
	<script  src="<%=basePath%>js/dyjc/tool/comm.js"></script>
	<script  src="<%=basePath%>js/common/jsonString.js"></script>
	<script  src="<%=basePath%>js/common/modalDialog.js"></script>
	<script  src="<%=basePath%>js/common/def.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script src="<%=basePath%>js/common/cookie.js"></script>
	<script type="text/javascript">
		var dy_autoshow = <%=WebConfig.autoShow.get("dy_show")%>;		//获取低压自动显示参数配置数组	
	</script>
  </head>
  
  <body>
  <table width="100%" cellpadding="0" cellspacing="0">
    	<tr><td align="center">
    	<div id=gridbox style="height:420px;border:0px;width:99%;"></div>
    	</td></tr>
    </table>
    <table width="100%" border="0" class="page_tbl">
    <tr><td id="pageinfo">&nbsp;</td></tr>
    </table>
    <table id="tabinfo" class="tabinfo" align="center">
    	<tr>
	    	<td class="td_lable" style="height:22px;" id="td2">Conditions</td>
	    </tr>
	    <tr>
	     	<td class="tdr">PDV:</td><td><select id="org" style="width: 150px"></select></td>
	     	<td class="tdr">Concentrateur:</td><td><select id="rtu" style="width: 150px"><option value="-1">Tous</option></select></td>
	     	<td class="tdr">Numéro client:</td><td><input type="text" id="yhhh" style="width: 150px"/></td>
	     	<td class="tdr">Nom du client:</td><td><input type="text" id="yhmc" style="width: 150px"/></td>
	     	<td class="tdr">Meter No:</td><td><input type="text" id="bh" style="width: 150px"/></td>
	    </tr>
	    <tr>
	    <td class="tdr">État du client:</td>
	    <td><select id="yhzt" class="is_width" style="width: 150px">
	    <option value="0">Tous</option>
	    <option value="1">Compte normal</option>
	    <option value="2">Compte anormal</option>
	    <option value="3">Plus de 1,0 du solde</option>
	    <option value="4">Moins de 1,0 du solde</option>
	    </select></td>
	   <!--20121114 add except begin-->
	   <td class= "tdr">État d'alarme</td>
	   <td><select id="alarm_type" class="is_width" style="width: 150px"> 
	    <option value="0">Tous</option>
	    <option value="1">1er court message</option>
	    <option value="2">1er son</option>
	    <option value="3">2e message court</option>
	    <option value="4">Alarme</option>
	    <option value="5">1st Short Msg Succ</option>
	    <option value="6">1st Sound Succ</option>
	    <option value="7">2nd Short Msg Succ</option>
	    <option value="8">Réussite de l'alarme</option>
        </select></td>
        
	    <td class= "tdr">État de contrôle</td>
	   <td><select id="ctrl_type" class="is_width" style="width: 150px"> 
	    <option value="0">Tous</option>
	    <option value="1">Désactiver l'erreur</option>
	    <option value="2">Erreur d'activation</option>
	    <option value="3">Erreur d'activation / désactivation</option>
	    <option value="4">Désactiver OK</option>
	    <option value="5">Allumez OK</option>
	    <option value="6">Allumer / éteindre OK</option>
        </select></td>
        <!--20121114 add except 下面colspan 有调整 end-->
	    <td class="tdr" colspan="4">
	        <input type="hidden" type="button" value="Select Item" class="btn" id="selLookItem" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    <button class="btn" id="search">Requete</button>
		    <form style="display: inline;" method="post" action="<%=basePath%>excel/dataExcel.action" name="toxls">
				<input type="hidden" id="excPara" name="excPara" />
				<input type="hidden" id="colType"  name="colType" />
				<input type="hidden" id="header"  name="header" />
				<input type="hidden" id="attachheader"  name="attachheader" />
				<input type="hidden" id="vfreeze" name="vfreeze" />
				<input type="hidden" id="hfreeze" name="hfreeze" />
				<input type="hidden" id="filename" name="filename" />
				<button class="btn" id="toexcel" onclick='dcExcel();'>Exportation</button>
			</form>
	    </td>
	    </tr>
	</table>
	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
	$("#gridbox").height($(window).height() - $("#tabinfo").height() - 48);
	</script>
  </body>
</html>
