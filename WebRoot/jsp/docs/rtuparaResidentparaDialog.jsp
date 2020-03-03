<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>Resident Archive</title>
    
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
		width: 110px;
	}
	input,select{
		font-size: 12px;
	}
	</style>
  	<script>
		var basePath = '<%=basePath%>';
  	</script>	
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/docs/rtuparaResidentparaDialog.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/modalDialog.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/validate.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/def.js"></script>
  </head>
  
  <body>
    <form action="" method="post" id="addorupdate"  style='display:inline;'>
    <br/>
    <table class="tabsty" align="center">
    	<tr><td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;">
    	<table cellpadding="0" cellspacing="0"><tr><td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td><td style="border: 0px; font-size: 12px;">Archives des résidents</td></tr></table>
    	</td></tr>
    	<tr>
	    	<td><font color=red>*</font>Nom:</td><td><input type="text" name="residentpara.describe" id="describe" /></td>
	    	<td><font color=red>*</font>ID consommateur:</td><td><input type="text" name="residentpara.consNo" id="consNo"/></td>
			<td>Nom du terminal:</td><td><input type="text" name="rtu_describe" id="rtu_describe" readonly="readonly" style="background-color:#DCDCDC;border: 1px solid #808080;"/></td>
	    </tr>	
	    <tr>	
	    	<td>Adresse:</td><td><input type="text" name="residentpara.address" id="address"/></td>
	    	<td>Code postal:</td><td><input type="text" name="residentpara.post" id="post"/></td>
	    	<td>Téléphone portable:</td><td><input type="text" name="residentpara.mobile" id="mobile"/></td>
	    </tr>
	    <tr>
			<td>Téléphone:</td><td><input type="text" name="residentpara.phone" id="phone"/></td>	    
	    	<td>Fax:</td><td><input type="text" name="residentpara.fax" id="fax"/></td>
	    	<td>Email:</td><td><input type="text" name="residentpara.mail" id="mail" /></td>
	    </tr>
	    <tr>
		    <td>Code d'interface:</td>
	    	<td>
	    		<input type="text" name="residentpara.infCode1" id="infCode1" />
	    		<input type="hidden" id="id" name="residentpara.id" />
	    		<input type="hidden" id="rtuId" name="residentpara.rtuId" />
	    	</td>
	    	<td colspan="4"></td>
	    </tr>
    </table>
    <center>
        <jsp:include page="../inc/btn_tianjia.jsp"></jsp:include>
        &nbsp;&nbsp;
        <jsp:include page="../inc/btn_xiugai.jsp"></jsp:include>
        &nbsp;&nbsp;
        <jsp:include page="../inc/btn_guanbi.jsp"></jsp:include>
    </center>
    </form>
	<jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
	<jsp:include page="../../jsp/inc/jsdef.jsp"></jsp:include>	
  </body>
</html>
