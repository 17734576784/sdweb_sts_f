<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<% String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%>
<html>
<head>
<title></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/cont.css">
<script type="text/javascript" src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/validate.js"></script>
<style type="text/css">
	body{
		margin: 0 0 0 0;
		overflow: hidden;
	  	background: #eaeeef;
	}	
	.tabsty{
		width:90%;
	}
	.tabsty input {
		width: 110px;
	}
	input{
		font-size: 12px;
	}
	</style>
<script type="text/javascript">
$(document).ready(function(){
	var parm=window.dialogArguments.split(',');
	$("#tdfz").html("&nbsp;"+parm[0]+"分子:");
	$("#tdfm").html("&nbsp;"+parm[0]+"分母:");
	$("#tdt").html("&nbsp;"+parm[0]+"变比:");
	$("#tdtitle").html(parm[0]+"设置");
	$("#Numerator").val(parm[1]);
	$("#Denominator").val(parm[2]);
	if(parm[2]!="" && parm[1]!=""){
		if(parm[2]!=0 && parm[1]!=0){
			$("#Ratio").val(parm[1]/parm[2]);
		}
	}
	if(parm[3]!=""){document.title=parm[3];}		
});
function computePTCT(){
		if($("#Numerator").val()!="" && $("#Denominator").val()!=""){
		$("#Ratio").val(0);
		var reg = new RegExp("^-?\\d+$");
		if($("#Numerator").val()!=0 && $("#Denominator").val()!=0 && reg.test($("#Numerator").val()) && reg.test($("#Denominator").val())){
			$("#Ratio").val($("#Numerator").val()/$("#Denominator").val());
		}
	}
}

window.returnValue="";
function returnWin(){
var strValue=$("#Numerator").val()+","+$("#Denominator").val()+","+$("#Ratio").val();
window.returnValue=strValue;
window.close();
}
</script>
</head><body>
<form id=form1  style='display:inline;'>
	<table class="tabsty"  align="center">
		<tr>
			<td colspan="2" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;">
	    		<table cellpadding="0" cellspacing="0"><tr><td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td><td id="tdtitle" style="border: 0px; font-size: 12px;"></td></tr></table>
	    	</td>
	    </tr>
	    <tr><td id="tdfz" width="35%" style="font-size: 12px;"></td><td><input type=text name="Numerator" id="Numerator" onblur="if(isNumber('Numerator','分子',false)){computePTCT();}"/></td></tr>
		<tr><td id="tdfm" width="35%" style="font-size: 12px;"></td><td><input type=text name="Denominator" id="Denominator" onblur="if(isNumber('Denominator','分母',false)){computePTCT();}"/></td></tr>
		<tr><td id="tdt" width="35%" style="font-size: 12px;"></td><td><input type=text name="meterpara.Ratio" id="Ratio" readonly style="background-color:#DCDCDC;border: 1px solid #808080;" /></td></tr>
		<tr><td colspan=2><center><button id="set" onclick="returnWin()">设置</button>&nbsp;&nbsp;<button id="close" onclick="window.close();">关闭</button></center></td></tr>
    </table>	
</form></body></html>	