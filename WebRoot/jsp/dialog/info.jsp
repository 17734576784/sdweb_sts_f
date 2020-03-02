<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int i=1;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title><i18n:message key="page.info.query"/></title>
    <style type="text/css">
    body{
    	background: #E2ECF8;
    	overflow: hidden;
    }
    .tabinfo{
    	border-collapse: collapse;
    	margin:10 10 10 10px;
    	width:99%;
    	overflow:hidden;
    	vertical-align:middle;    	
    	font-size: 14px;
    }
    .tabinfo td{
    	border: 1px solid #B8C0BD;
    	height: 30px;
    	
    }
    .tdnum{
    	font-weight:bold; 
		font-family: sans-serif;
    }
   </style>
    <script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
    <script  src="<%=basePath%>js/common/def.js"></script>
    <script  src="<%=basePath%>js/common/jsonString.js"></script>
    <script  src="<%=basePath%>js/common/cookie.js"></script>
  </head>
  
  <body>
  
  <script type="text/javascript">
  	var params = window.dialogArguments;
  	var rtnVal = {};
	rtnVal.flag = false;
  	window.returnValue = rtnVal;
  $(document).ready(function(){
  	
    var tmpname = cookie.get(cookie.gsm_flag);
    
    if(tmpname == "1"){
    	tmpname = "checked";
    }else{
    	tmpname = "";
    }
  	var div="<div>";
  	var tbl="<table class='tabinfo'  align=center >";
  	
	for(var i=0;i<params.key.length;i++){
  		if(params.key[i].indexOf("金额")>-1){
	  		tbl += "<tr><td width=40% align=right >" + params.key[i] + ":</td><td  width=60% class='tdnum'>" + params.val[i] + "</td></tr>";
  		}else{
  			tbl += "<tr><td width=40% align=right >" + params.key[i] + ":</td><td  width=60%>" + params.val[i] + "</td></tr>";
  		}
  	}
  	if(params.showGSM){
  		tbl += "<tr><td width=40% align=right >短信通知:</td><td  width=60%><input type=checkbox id='chkgsm' "+tmpname+"/></td></tr>";
  	}
  	tbl += "<tr><td colspan='2' align='center' style='border:none'></td></tr>";
  	tbl += "<tr><td colspan='2' align='center' style='border:none'><input type='submit' id='queding' value='<i18n:message key='page.query'/>'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type='button' id='quxiao' value='<i18n:message key='page.cancel'/>'/> </td></tr></table>";
  	div += tbl + "</div>";
  	
  	$(document.body).append(div);
  	
  	$("#queding").click(function(){ret(true)})
  	$("#quxiao").click(function(){ret(false)})
  });

  function ret(flag){
	rtnVal.flag=flag;
	if($("#chkgsm").attr("checked")){
		rtnVal.gsm = "1";
	}else{
		rtnVal.gsm = "0";
	}
	cookie.set(cookie.gsm_flag, rtnVal.gsm);
	window.returnValue = rtnVal;
  	window.close();
  }
  </script>
  </body>
</html>
