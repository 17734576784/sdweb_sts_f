<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.kesd.comnt.ComntProtMsg"%>
<%@page import="com.kesd.comntpara.ComntUseropDef"%>
<%@page import="com.kesd.common.Dict"%>
<%@page import="com.kesd.common.SDDef"%>
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
  <script src="<%=basePath%>js/common/jsonString.js"></script>
  <script src="<%=basePath%>js/np/dialog/card_blacklist.js"></script>
  <jsp:include page="../../../jsp/ydjsdef.jsp"></jsp:include>
  <script type="text/javascript" src="<%=basePath%>js/css.js"></script>
</head>
<body>
	<form style="display: inline;" method="post" action="<%=basePath%>excel/dataExcel.action" name="_toxls">
		<input type="hidden" id="excPara" name="excPara" />
		<input type="hidden" id="colType" name="colType" />
		<input type="hidden" id="header" name="header" />
		<input type="hidden" id="attachheader" name="attachheader" />
		<input type="hidden" id="vfreeze" name="vfreeze" />
		<input type="hidden" id="hfreeze" name="hfreeze" />
		<input type="hidden" id="filename" name="filename" />
	</form>
	<form style="display: inline;" method="post" action="<%=basePath%>excel/dataExcel.action" name="_toxls1">
		<input type="hidden" id="excPara1" name="excPara" />
		<input type="hidden" id="colType1" name="colType" />
		<input type="hidden" id="header1" name="header" />
		<input type="hidden" id="attachheader1" name="attachheader" />
		<input type="hidden" id="vfreeze1" name="vfreeze" />
		<input type="hidden" id="hfreeze1" name="hfreeze" />
		<input type="hidden" id="filename1" name="filename" />
	</form>
		<table style="font-size: 12px; margin-top:10px;" align="center" width="90%">
			<tr>
			<td width=50 >&nbsp;&nbsp;电表:</td>
			<td width="130">
			<input type="text" id="esam_no" readonly style="background: #eee;"/>
			</td>
			<td width="50">
			<button id="sel_meter">选择</button>
			</td>
			<td width=50 >区域号:</td>
			<td width="130">
			<input type="text" id="area_code"/>
			</td>
			<td></td>
			</tr>
		</table>

  <table id=table1 width="90%" align="center" border="0" style="height: 20px; font-size: 12px;">
    <tr>
     <td align="center" valign="top" style="font-size:12px;">
      <fieldset style="text-align: center; vertical-align: top; margin-top:5px; ">
        <legend style="color:#069;">
<!--        <input id="chkuseflag" type="checkbox" style="width:18px;" class="chk" onclick='checkUsed(this.checked)'/>-->
        <label >&nbsp;黑名单启停标志<span id="loading_useflag"></span></label>
        </legend>
        <table class="tabsty2" width="100%" cellspacing="0" cellpadding="0" style="font-size:12px; line-height:35px;">
          <tr>
            <td width="10%" nowrap style="text-align:right;">&nbsp;  </td>
            <td width="20%" nowrap style="text-align:right;"> 电表黑名单启用标志:</td>
            <td width="20%" nowrap style="text-align:left;"><select id="isUsed" ></select></td>
            <td width="12%" nowrap style="text-align:left;"><input type="button" id="write_useflag" value="写卡" style="width:40px;"/></td>
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
        <label >&nbsp;读取当前电卡黑名单记录</label> &nbsp;&nbsp;
		<span id="loading_record"></span>        
        </legend>
        <div style="text-align:left;">
         &nbsp;&nbsp;<select id="group">
	        <option value=0 selected>第一组(01-10条)</option>
	        <option value=1>第二组(11-20条)</option>
	        <option value=2>第三组(21-30条)</option>
	        <option value=3>第四组(31-40条)</option>
	        <option value=4>第五组(41-50条)</option>
        </select>&nbsp;&nbsp;
        <input type="button" id="create_card" value="制作回抄卡" style="width:80px;"/>&nbsp;&nbsp;
        <input type="button" id="read_card" value="读回抄卡" style="width:80px;"/>&nbsp;&nbsp; 
	 &nbsp;&nbsp;
			<input type="button" value="导出excel" id="toexcel" style="width:80px;" onclick='dcExcel();' />
        </div>
        <DIV id=gridbox1 style="width: 100%; border: 1px solid #ccc;margin-top:2"></DIV>
    </fieldset>
    </td>
  </tr>
</table>

<table id=table4 width="90%" align="center" border="0" style="height: 20px; font-size: 12px; margin-top:5px;">
	<tr>
		<td align="center" valign="top" style="font-size:12px;">
		 <fieldset style="text-align: center; vertical-align: top; margin-top:5px; ">
        	<legend style="color:#069;">
       		<label >&nbsp;黑名单添加删除<span id="loading_addordel"></span></label>
       		</legend>
			 <div style="text-align:left;">&nbsp;&nbsp;
			<input type="button" id="black_add" value="添加" style="width:40px;"/>&nbsp;&nbsp;
        	<input type="button" id="black_del" value="删除" style="width:40px;"/>&nbsp;&nbsp;
        	<input type="button" value="导出excel" id="toexcel1" style="width:80px;" onclick='dcExcel1();' />
			 </div>
			<DIV id=gridbox2 style="width: 100%; border: 1px solid #ccc;margin-top:2"></DIV>
       	 </fieldset>
		</td>
	</tr>
	<tr>
		<td align="center" valign="top" style="font-size:12px;">
		<fieldset>
	        <legend style="color:#069;">
	        <label >&nbsp;移除黑名单<span id="loading_remove"></span></label></legend>
	        <table class="tabsty2" width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:12px; line-height:30px;">
	          <tr>
	            <td width="24%" nowrap style="text-align:center;"></td>
	            <td nowrap width="30%" style="text-align:left;">此命令将删除电卡内所有黑名单的记录。</td>
	            <td nowrap style="text-align:left;"><input type="button" id="black_clear" value="写卡" style="width:40px;"/>&nbsp;&nbsp;</td>
	            </tr>
	          </table>
	    </fieldset>
		</td>
	</tr>
</table>


<script type="text/javascript">
	$("#gridbox1").height($(window).height()/4 + 120);
	$("#gridbox2").height($(window).height()/4 + 120);
	var Dict = {};
	Dict.DICTITEM_STARTUPFLAG = '<%= Dict.DICTITEM_STARTUPFLAG%>';
	
	var SDDef = {};
	SDDef.NPCARD_KEEXPAN_BLACK_ADD 		= <%=SDDef.NPCARD_KEEXPAN_BLACK_ADD%>;	//增加黑名单
	SDDef.NPCARD_KEEXPAN_BLACK_DEL 		= <%=SDDef.NPCARD_KEEXPAN_BLACK_DEL%>;	//删除黑名单
	SDDef.NPCARD_KEEXPAN_BLACK_CLEAR 	= <%=SDDef.NPCARD_KEEXPAN_BLACK_CLEAR%>;//清空黑名单
	SDDef.NPCARD_KEEXPAN_BLACK_READ 	= <%=SDDef.NPCARD_KEEXPAN_BLACK_READ%>; //抄收黑名单
	SDDef.NPCARD_KEEXPAN_OTHER 			= <%=SDDef.NPCARD_KEEXPAN_OTHER%>;		//其他卡类型
	
	
</script>
  	
</body>
</html>