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
  <script src="<%=basePath%>js/np/dialog/whitelist.js"></script>
  <jsp:include page="../../../jsp/ydjsdef.jsp"></jsp:include>
  <script type="text/javascript" src="<%=basePath%>js/css.js"></script>
</head>
<body>
<form id="whitelistConfigBase" name="whitelistConfigBase">
  <table id=table1 width="90%" align="center" border="0" style="height: 20px; font-size: 12px; margin-top:5px;">
    <tr>
     <td align="center" valign="top" style="font-size:12px;">
      <fieldset style="text-align: center; vertical-align: top; margin-top:5px; ">
        <legend style="color:#069;">
        <input id="chkuseflag" type="checkbox" style="width:18px;" class="chk" onclick='checkUsed(this.checked)'/>
        <label for="chkuseflag">[召测/设置]&nbsp;白名单启停标志<span id="loading_useflag"></span></label>
        </legend>
        <table class="tabsty2" width="100%" cellspacing="0" cellpadding="0" style="font-size:12px; line-height:35px;">
          <tr>
            <td width="10%" nowrap style="text-align:right;">&nbsp;  </td>
            <td width="20%" nowrap style="text-align:right;"> 电表白名单启用标志:</td>
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
        <label for="chkrecord">[仅可召测]&nbsp;当前电表白名单记录<span id="loading_record"></span></label>
        </legend>
        <DIV id=gridbox1 style="width: 100%; border: 1px solid #ccc;"></DIV>
    </fieldset>
    </td>
  </tr>
</table>

<table id=table3 width="90%" align="center" border="0">
  <tr>
    <td align="center" valign="top" style="font-size:12px;">
    <fieldset style="text-align: center; vertical-align: top; margin-top:5px; ">
        <legend style="color:#069;">
        <span id="all_whitelist"></span>
        <label for="all_whitelist">电表所属片区内所有白名单记录</label>
        </legend>
        <DIV id=gridbox2 style="width: 100%; border: 1px solid #ccc;"></DIV>
    </fieldset>
    </td>
  </tr>
</table>

<table id=table4 width="90%" align="center" border="0" style="height: 20px; font-size: 12px; margin-top:5px;">
	<tr>
		<td align="center" valign="top" style="font-size:12px;">
		 <fieldset style="text-align: center; vertical-align: top; margin-top:5px; ">
        	<legend style="color:#069;">
       		<input id="chkaddordel" type="checkbox" style="width:18px;" class="chk" onclick='checkAddorDel(this.checked)'/>
       		<label for="chkaddordel">[仅可设置]&nbsp;白名单添加删除<span id="loading_addordel"></span></label>
        	</legend>
       		<table class="tabsty3" width="100%" cellspacing="0" cellpadding="0" style="font-size:12px; line-height:35px;">
          	<tr>
            	<td width="10%" nowrap style="text-align:right;">&nbsp;&nbsp;</td>
            	<td width="20%" nowrap style="text-align:right;">选择操作类型:</td>
            	<td width="20%" nowrap style="text-align:left;">
            		<select id="addordel" disabled="disabled" style="width:140px; background: #eee;">
            		  <option value="0">添加</option>
            		  <option value="1">删除</option>
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
	        <label for="chkremove">[仅可设置]&nbsp;移除白名单<span id="loading_remove"></span></label></legend>
	        <table class="tabsty2" width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px; line-height:30px;">
	          <tr>
	            <td width="24%" nowrap style="text-align:center;"></td>
	            <td nowrap style="text-align:left;">此命令将删除电表内所有白名单的记录。</td>
	            </tr>
	          </table>
	    </fieldset>
		</td>
	</tr>
</table>
</form>

<script type="text/javascript">
	$("#gridbox1").height($(window).height()/4);
	$("#gridbox2").height($(window).height()/4);
	var Dict = {};
	Dict.DICTITEM_STARTUPFLAG = '<%= Dict.DICTITEM_STARTUPFLAG%>';
	
	var ComntProtMsg = {};
	ComntProtMsg.YFF_CALL_DL645_CALL_WL_FLAG = <%=ComntProtMsg.YFF_CALL_DL645_CALL_WL_FLAG%>; //召测白名单使用标志
	
	ComntProtMsg.YFF_CALL_DL645_CALL_WL_FIR  = <%=ComntProtMsg.YFF_CALL_DL645_CALL_WL_FIR%>;  //召测白名单
	ComntProtMsg.YFF_CALL_DL645_CALL_WL_FIN  = <%=ComntProtMsg.YFF_CALL_DL645_CALL_WL_FIN%>;  //召测白名单
	
	ComntProtMsg.YFF_CALL_DL645_SET_WL_FLAG  = <%=ComntProtMsg.YFF_CALL_DL645_SET_WL_FLAG%>;  //设置白名单使用标志
	ComntProtMsg.YFF_CALL_DL645_SET_WL_ITEM  = <%=ComntProtMsg.YFF_CALL_DL645_SET_WL_ITEM%>;  //添加、删除白名单
	ComntProtMsg.YFF_CALL_DL645_CLR_WL 		 = <%=ComntProtMsg.YFF_CALL_DL645_CLR_WL%>;		  //清除白名单
	
	
	var ComntUseropDef = {};
  	ComntUseropDef.YFF_NPOPER_WL_PARA = <%=ComntUseropDef.YFF_NPOPER_WL_PARA%>;
</script>
  	
</body>
</html>