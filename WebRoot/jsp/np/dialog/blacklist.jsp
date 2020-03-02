<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.kesd.comnt.ComntProtMsg"%>
<%@page import="com.kesd.comntpara.ComntUseropDef"%>
<%@page import="com.kesd.common.Dict"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>My JSP 'rtuconfigBase.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/dhtmlx/dhtmlxgrid.css">
<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/cont.css" id="css_theme">
<style type="text/css">
body {
	margin:0 0 0 0;
	overflow:auto;
}
.tabsty2 td {
	font-size:12px;
	text-align:left;
	
}
.tabsty input, .tabsty select {
	width: 90px;
}
input, select {
	font-size: 12px;
	width:125px;
}
</style>
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  <script type="text/javascript" src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
  <script type="text/javascript" src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
  <script type="text/javascript" src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  <script src="<%=basePath%>js/common/loading.js"></script>
  <script src="<%=basePath%>js/np/dialog/blacklist.js"></script>
  <jsp:include page="../../../jsp/ydjsdef.jsp"></jsp:include>
  <script type="text/javascript" src="<%=basePath%>js/css.js"></script>
</head>
<body>
<form id="blacklistConfigBase" name="blacklistConfigBase">
  <table id=table1 width="90%" align="center" border="0" style="height: 20px; font-size: 12px; margin-top:5px;">
    <tr>
     <td align="center" valign="top" style="font-size:12px;">
      <fieldset style="text-align: center; vertical-align: top; margin-top:5px; ">
        <legend style="color:#069;">
        <input id="chkuseflag" type="checkbox" style="width:18px;" class="chk" onclick='checkUsed(this.checked)'/>
        <label for="chkuseflag">[召测/设置]&nbsp;黑名单启停标志<span id="loading_useflag"></span></label>
        </legend>
        <table class="tabsty2" width="100%" cellspacing="0" cellpadding="0" style="font-size:12px; line-height:35px;">
          <tr>
            <td width="10%" nowrap style="text-align:right;">&nbsp;  </td>
            <td width="20%" nowrap style="text-align:right;"> 电表黑名单启用标志:</td>
            <td width="20%" nowrap style="text-align:left;"><select id="isUsed" disabled="disabled" style="width:140px; background: #eee;"></select></td>
            <td width="12%" nowrap style="text-align:left;">&nbsp;&nbsp;</td>
            <td width="25%" nowrap style="text-align:left;">&nbsp;</td>
          </tr>
        </table>
       </fieldset>
      </td>
    </tr>
  </table>
<table id=table2 width="90%" align="center" border="0">
  <tr>
    <td align="center" valign="top" style="font-size:12px;">
    <fieldset style="text-align: center; vertical-align: top; margin-top:5px; ">
        <legend style="color:#069;">
        <input id="chkrecord" type="checkbox" style="width:18px;" class="chk"/>
        <label for="chkrecord">[仅可召测]&nbsp;当前电表黑名单记录</label>
        <select id="group">
	        <option value=-1>全部</option>
	        <option value=0 selected>第一组(01-10条)</option>
	        <option value=1>第二组(11-20条)</option>
	        <option value=2>第三组(21-30条)</option>
	        <option value=3>第四组(31-40条)</option>
	        <option value=4>第五组(41-50条)</option>
        </select>
		<span id="loading_record"></span>        
        </legend>
        <DIV id=gridbox1 style="width: 100%; border: 1px solid #ccc;"></DIV>
    </fieldset>
    </td>
  </tr>
</table>

<!--
<table id=table3 width="90%" align="center" border="0">
  <tr>
    <td align="center" valign="top" style="font-size:12px;">
    <fieldset style="text-align: center; vertical-align: top; margin-top:5px; ">
        <legend style="color:#069;">
        <span id="all_blacklist"></span>
        <label for="all_blacklist">电表所属片区内所有黑名单记录</label>
        </legend>
        <DIV id=gridbox2 style="width: 100%; border: 1px solid #ccc;"></DIV>
    </fieldset>
    </td>
  </tr>
</table>
-->

<table id=table4 width="90%" align="center" border="0" style="height: 20px; font-size: 12px; margin-top:5px;">
	<tr>
		<td align="center" valign="top" style="font-size:12px;">
		 <fieldset style="text-align: center; vertical-align: top; margin-top:5px; ">
        	<legend style="color:#069;">
       		<input id="chkaddordel" type="checkbox" style="width:18px;" class="chk" onclick='checkAddorDel(this.checked)'/>
       		<label for="chkaddordel">[仅可设置]&nbsp;黑名单添加删除<span id="loading_addordel"></span></label>
        	</legend>
<DIV id=gridbox2 style="width: 100%; border: 1px solid #ccc;"></DIV>        	
       		<table class="tabsty3" width="100%" cellspacing="0" cellpadding="0" style="font-size:12px; line-height:35px;">
          	<tr>
            	<td width="10%" nowrap style="text-align:right;">&nbsp;&nbsp;</td>
            	<td width="20%" nowrap style="text-align:right;">选择操作类型:</td>
            	<td width="20%" nowrap style="text-align:left;">
            		<select id="addordel" disabled="disabled" style="width:140px; background: #eee;">
            		  <option value="1">添加</option>
            		  <option value="0">删除</option>
            		</select>
            	</td>
            	<td width="12%" nowrap style="text-align:right;">&nbsp;&nbsp;选择农排用户卡号:</td>
            	<td width="25%" nowrap style="text-align:left;"><input id="cardNo" disabled="disabled" style="width:140px; background: #eee;"/></td>
          	</tr>
        	</table>
       	 </fieldset>
		</td>
	</tr>
	<tr>
		<td align="center" valign="top" style="font-size:12px;">
		<fieldset>
	        <legend style="color:#069;">
	        <input id="chkremove" type="checkbox" style="width:18px;" class="chk"/>
	        <label for="chkremove">[仅可设置]&nbsp;移除黑名单<span id="loading_remove"></span></label></legend>
	        <table class="tabsty2" width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px; line-height:30px;">
	          <tr>
	            <td width="24%" nowrap style="text-align:center;"></td>
	            <td nowrap style="text-align:left;">此命令将删除电表内所有黑名单的记录。</td>
	            </tr>
	          </table>
	    </fieldset>
		</td>
	</tr>
</table>
</form>
<br>
<script type="text/javascript">
	$("#gridbox1").height($(window).height()/4 + 120);
	$("#gridbox2").height($(window).height()/4 + 70);
	var Dict = {};
	Dict.DICTITEM_STARTUPFLAG = '<%= Dict.DICTITEM_STARTUPFLAG%>';
	
	var ComntProtMsg = {};
	ComntProtMsg.YFF_CALL_DL645_CALL_BL_FLAG = <%=ComntProtMsg.YFF_CALL_DL645_CALL_BL_FLAG%>; //召测黑名单使用标志
	
	ComntProtMsg.YFF_CALL_DL645_CALL_BL_FIR  = <%=ComntProtMsg.YFF_CALL_DL645_CALL_BL_FIR%>;  //召测黑名单
	ComntProtMsg.YFF_CALL_DL645_CALL_BL_FIN  = <%=ComntProtMsg.YFF_CALL_DL645_CALL_BL_FIN%>;  //召测黑名单
	
	ComntProtMsg.YFF_CALL_DL645_SET_BL_FLAG  = <%=ComntProtMsg.YFF_CALL_DL645_SET_BL_FLAG%>;  //设置黑名单使用标志
	ComntProtMsg.YFF_CALL_DL645_SET_BL_ITEM  = <%=ComntProtMsg.YFF_CALL_DL645_SET_BL_ITEM%>;  //添加、删除黑名单
	ComntProtMsg.YFF_CALL_DL645_CLR_BL 		 = <%=ComntProtMsg.YFF_CALL_DL645_CLR_BL%>;		  //清除黑名单
	
	
	var ComntUseropDef = {};
  	ComntUseropDef.YFF_NPOPER_BL_PARA = <%=ComntUseropDef.YFF_NPOPER_BL_PARA%>;
</script>
  	
</body>
</html>