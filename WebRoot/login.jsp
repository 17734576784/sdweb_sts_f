<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.io.*,com.kesd.common.WebConfig"%>
<%@page import="com.kesd.common.SDDef"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>Electricity Sale Sub-system</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<style type="text/css">
	<!--
	body {
		font-family:"Arial";
		margin: 0 0 0 0;
		background: #ffffff;
		overflow: auto;
	}
	.id{
		height:24px;
		width:150px;
		border:1px solid #045650;
		filter: Alpha(Opacity=60);
		background: #e7f6fa;
	}
	input:hover{
		background-color:#A0D1FF;
	}
	-->
	</style>
	<link rel="Shortcut Icon" href="images/addrbar.ico" />
	<link rel="Bookmark" href="images/addrbar.ico"/>
	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="js/common/cookie.js"></script>
	<script src="js/common/modalDialog.js"></script>
	<script type="text/javascript">
		var pressed = false;
		$(document).ready(function() {
			var tmpname = cookie.get(cookie.login_cookie);
			if(tmpname != ""){
				$("#opername").val(tmpname);
				$("#pwd").focus();
			}else{
				$("#opername").focus();
			}
		});
	
		function login(){
			if(pressed){
				return;
			}
			var opername = $("#opername").val();
			pressed = true;
			if($.trim(opername) === ""){
				alert("Please input user name！");
				$("#opername").focus();
				pressed = false;
				return;
			}
			if($.trim($("#pwd").val()) === ""){
				alert("Please input password！");
				$("#pwd").focus();
				pressed = false;
				return;
			}
			//校验账号
			checkLoginAccount(opername);
			
		}
		
		function checkLoginAccount(opername){
			try {
					$.post("ajax/actLogin.action",{
						opername:opername,
						pwd:$("#pwd").val()
					},function(data){
						if(data.result == "success"){

							cookie.set(cookie.login_cookie,opername);
							
							window.opener=null;window.open('','_self');
							window.close();
							window.open('index.jsp','', 'toolbar=0,menubar=0,location=0,status=0,resizable=1,left=0,top=0,height='+(screen.availHeight-30) +',width='+(screen.availWidth-10),true);
						}else if(data.result == "fail"){
							pressed = false;
							alert("User name or password error,fail to log in！");
			//					$("#pwd").select();
						}else{
							pressed = false;
							alert(data.result);
							var o = regMsg();
							if(o != "1"){
								return;
							}
							checkLoginAccount();
						}
					});
				}catch (e) {
					alert("fail");
					pressed = false;
				}
		}
		
		//填写注册码信息弹框
		function regMsg(){
			modalDialog.height = 160;
			modalDialog.width = 500;
			modalDialog.url = "regMsg.jsp";
			var o = modalDialog.show();
			return o;
		}
	</script>
  </head>
  
  <body>
<form method="post" onKeyPress="if(event.keyCode=='13'){login();}">
<div style="position:absolute; right:1px; height:20px; text-align: right; font-size: 12px;">
<a onClick="AddFavorite(window.location,document.title)" id="favorite" style="cursor: pointer; color:#033;" onmousemove="chgCol(this.id,'#2a7066')" onmouseout="chgCol(this.id,'#033')">Added to Favorites</a>&nbsp;&nbsp;|
&nbsp;<a onClick="SetHome(this,window.location)" id="homePage" style="cursor: pointer; color:#033;" onmousemove="chgCol(this.id,'#2a7066')" onmouseout="chgCol(this.id,'#033')">Set as Homepage</a>&nbsp;
</div>
<br/><br/><br/>
<table width="649" align="center" border="0">
<tr><td background="images/loginbg.jpg" height="388" valign="bottom">
	<table align="center" border="0" style="color: #045650;">
		<tr><td align="left" valign="bottom" style="padding-left: 90px; font-size: 40px; color: white;" colspan="3" height="40">Utility</td></tr>
		<tr><td align="center" valign="top" style="padding-left: 30px; font-size: 40px; color: white;" colspan="3">Electricity Sale</td></tr>
		<tr><td align="center" valign="top" style="padding-left: 190px; font-size: 40px; color: white;" colspan="3">Sub-system</td></tr>
		<tr><td  colspan="3">&nbsp;</td></tr>
		<tr><td  colspan="3">&nbsp;</td></tr>
		<tr><td  colspan="3">&nbsp;</td></tr>
		<tr><td  colspan="3">&nbsp;</td></tr>
		<tr>
			<td width="180" height="28" align="right" style="font-size: 13px;">User Name:</td><td><input id="opername" type="text" class="id" /></td>
            <td width="169" rowspan="2">
            &nbsp;<img src="images/dlan.gif" onClick="login();" style="cursor: pointer;"/></td>
        </tr>
          <tr>
            <td width="180" height="29" align="right" style="font-size: 13px;">Password:</td><td><input id="pwd" type="password" class="id" /></td>
          </tr>
  	</table>
<br/><br/><br/><br/>
</td>
</table>
<table width="649" align="center" border="0">
<tr><td>
<div style="font-size: 12px; height:20px; text-align: center;">Notice:The system need IE7 version and 1024*768 resolution or above.</div>
<%
Date date = new Date();
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy", Locale.SIMPLIFIED_CHINESE);
%>
  <div style="font-size: 12px; height:65px; text-align: center; background: url('images/logo_ke.gif') center bottom no-repeat;">SHIJIAZHUANG KELIN ELECTTIC CO.,LTD Copyright &copy;2010-<%=sdf.format(date)%></div>
</td></tr>
</table>
</form>
<div style="text-align: right;height:20px; font-size:13px; position:absolute; width:100%;" id="tips">
Please add this website to reliable list.
<a href="download.jsp?fileName=ke_yffwebctrl.rar" id="down" style="cursor: pointer; color:#033;" onmousemove="chgCol(this.id,'#2a7066')" onmouseout="chgCol(this.id,'#033')">Prepayment control download</a>
</div>
<script type="text/javascript">
	tips.style.posTop = $(window).height() - 20;
	function AddFavorite(sURL, sTitle){
	    try
	    {
	        window.external.addFavorite(sURL, sTitle);
	    }
	    catch (e)
	    {
	        try
	        {
	            window.sidebar.addPanel(sTitle, sURL, "");
	        }
	        catch (e)
	        {
	            alert("Fail to added to favorites,please use 'Ctrl+D'");
	        }
	    }
	}

	function SetHome(obj,vrl){
	        try{
	                obj.style.behavior='url(#default#homepage)';
	                obj.setHomePage(vrl);
	        }
	        catch(e){
	                if(window.netscape) {
	                        try {
	                                netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
	                        }
	                        catch (e) {
	                                alert("This operation is rejected by browser！\nPlease input“about:config”to address bar and press Enter\nThen set [signed.applets.codebase_principal_support] value as 'true',double click。");
	                        }
	                        var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
	                        prefs.setCharPref('browser.startup.homepage',vrl);
	                 }
	        }
	}
	function chgCol(id,color){
		$("#"+id).css("color",color);
	}
</script>
</body>
</html>
