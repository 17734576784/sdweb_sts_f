<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.comnt.ComntProtMsg"%>
<%@page import="com.kesd.comntpara.ComntUseropDef"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'callset_fee.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  <style type="text/css">
	body{
		margin: 0 0 0 0;
	}
	td{font-size:12px;}
	
	select, input {
		width: 140px;
	}
	</style>
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  <script  src="<%=basePath%>js/common/loading.js"></script>
  <jsp:include page="../../../jsp/ydjsdef.jsp"></jsp:include>
  </head>
  
  <body>

<div id="content" style="overflow: auto;">
<table align="center" style="margin:0 0 0 0; padding: 0 0 0 0; width: 90%; text-align: center; font-size: 12px;">
  <tr>
    <td style="margin:0 0 0 0; padding: 0 0 0 0; width: 70%;"><fieldset style="text-align: center;">
        <legend style="color:#069;">
        <input id="item0" class="chk" type="checkbox" style="width:18px; " onclick='checkItem(this)'"/><label for="item0">[召测/设置]&nbsp;费率电价<span class="mpname"></span><span id="loading_item0" class="load"></span></label></legend>
       <table class="tabsty2" width="100%" cellspacing="0" cellpadding="0" style="font-size:12px; line-height:35px;">
          <tr>
            <td style="text-align:left;width:140px;">费率电价(0.01分):</td>
            <td style="text-align:left;"><input type="text" id="val0">&nbsp;&nbsp;修改费率电价后请及时更改电表费率方案(档案管理→农排费控档案→电表控制参数)并发送更改通知！</td>
          </tr>
        </table>
      </fieldset></td>
  </tr>
  <tr>
    <td style="margin:0 0 0 0; padding: 0 0 0 0; width: 70%;"><fieldset style="text-align: center;">
        <legend style="color:#069;">
        <input id="item1" class="chk" type="checkbox" style="width:18px; " onclick='checkItem(this)'"/><label for="item1">[召测/设置]&nbsp;区域号<span class="mpname"></span><span id="loading_item1" class="load"></span></label></legend>
       <table class="tabsty2" width="100%" cellspacing="0" cellpadding="0" style="font-size:12px; line-height:35px;">
          <tr>
            <td style="text-align:left;width:140px;">区域号(16位):</td>
            <td style="text-align:left;"><input type="text" id="val1">&nbsp;&nbsp;修改区域号后请及时更改电表所属片区(档案管理→农排费控档案→电表控制参数)并发送更改通知！</td>
          </tr>
        </table>
      </fieldset></td>
  </tr>
  <tr>
  <td style="margin:0 0 0 0; padding: 0 0 0 0; width: 70%;"><fieldset style="text-align: center;">
        <legend style="color:#069;">
        <input id="item2" class="chk" type="checkbox" style="width:18px; " onclick='checkItem(this)'"/><label for="item2">[召测/设置]&nbsp;变比<span class="mpname"></span><span id="loading_item2" class="load"></span></label></legend>
       <table class="tabsty2" width="100%" cellspacing="0" cellpadding="0" style="font-size:12px; line-height:35px;">
          <tr>
            <td style="text-align:left;width:140px;">变比:</td>
            <td style="text-align:left;"><input type="text" id="val2"></td>
          </tr>
        </table>
      </fieldset></td>
  </tr>

  <tr>
    <td style="margin:0 0 0 0; padding: 0 0 0 0; width: 70%;"><fieldset style="text-align: center;">
        <legend style="color:#069;">
        <input id="item3" class="chk" type="checkbox" style="width:18px; " onclick='checkItem(this)'"/><label for="item3">[召测/设置]&nbsp;报警金额<span class="mpname"></span><span id="loading_item3" class="load"></span></label></legend>
       <table class="tabsty2" width="100%" cellspacing="0" cellpadding="0" style="font-size:12px; line-height:35px;">
          <tr>
            <td style="text-align:left;width:140px;">报警金额(分):</td>
            <td style="text-align:left;"><input type="text" id="val3"></td>
          </tr>
        </table>
      </fieldset></td>
  </tr>

  <tr>
    <td style="margin:0 0 0 0; padding: 0 0 0 0; width: 70%;"><fieldset style="text-align: center;">
        <legend style="color:#069;">
        <input id="item4" class="chk" type="checkbox" style="width:18px; " onclick='checkItem(this)'"/><label for="item4">[召测/设置]&nbsp;限定功率<span class="mpname"></span><span id="loading_item4" class="load"></span></label></legend>
       <table class="tabsty2" width="100%" cellspacing="0" cellpadding="0" style="font-size:12px; line-height:35px;">
          <tr>
            <td style="text-align:left;width:140px;">限定功率(0.1W):</td>
            <td style="text-align:left;"><input type="text" id="val4"></td>
          </tr>
        </table>
      </fieldset></td>
  </tr>

  <tr>
    <td style="margin:0 0 0 0; padding: 0 0 0 0; width: 70%;"><fieldset style="text-align: center;">
        <legend style="color:#069;">
        <input id="item5" class="chk" type="checkbox" style="width:18px; " onclick='checkItem(this)'"/><label for="item5">[召测/设置]&nbsp;无采样自动断电时间<span class="mpname"></span><span id="loading_item5" class="load"></span></label></legend>
       <table class="tabsty2" width="100%" cellspacing="0" cellpadding="0" style="font-size:12px; line-height:35px;">
          <tr>
            <td style="text-align:left;width:140px;">无采样自动断电时间(秒):</td>
            <td style="text-align:left;"><input type="text" id="val5"></td>
          </tr>
        </table>
      </fieldset></td>
  </tr>

  <tr>
    <td style="margin:0 0 0 0; padding: 0 0 0 0; width: 70%;"><fieldset style="text-align: center;">
        <legend style="color:#069;">
        <input id="item6" class="chk" type="checkbox" style="width:18px; " onclick='checkItem(this)'"/><label for="item6">[召测/设置]&nbsp;电表锁定状态<span class="mpname"></span><span id="loading_item6" class="load"></span></label></legend>
       <table class="tabsty2" width="100%" cellspacing="0" cellpadding="0" style="font-size:12px; line-height:35px;">
          <tr>
            <td style="text-align:left;width:140px;">电表锁定状态:</td>
            <td style="text-align:left;">
            <select id="val6">
            <option value="0">否</option>
            <option value="1">是</option>
            </select>
            </td>
          </tr>
        </table>
      </fieldset></td>
  </tr>
</table>
<table align="center" style="margin:0 0 0 0; padding: 0 0 0 0; width: 90%; text-align: center; font-size: 12px;">
  <tr>
    <td style="margin:0 0 0 0; padding: 0 0 0 0; width: 70%;"><fieldset style="text-align: center;">
        <legend style="color:#069;">
        <input id="item7" class="chk" type="checkbox" style="width:18px; " onclick='checkItem(this)'"/><label for="item7">[设置]&nbsp;清除挂起记录<span class="mpname"></span><span id="loading_item7" class="load"></span></label></legend>
       <table class="tabsty2" width="100%" cellspacing="0" cellpadding="0" style="font-size:12px; line-height:35px;">
          <tr>
            <td style="text-align:left;width:140px;">挂起记录编号:</td>
            <td style="text-align:left;">
            <select id="val7">
            </select>
          </tr>
        </table>
      </fieldset></td>
  </tr>
</table>
</div>
  <script type="text/javascript">
  $("#content").height($(window).height() - 30);
  var ComntProtMsg = {};
  ComntProtMsg.YFF_CALL_DL645_CALL_FEE = <%=ComntProtMsg.YFF_CALL_DL645_CALL_FEE%>;
  ComntProtMsg.YFF_CALL_DL645_CALL_AREA = <%=ComntProtMsg.YFF_CALL_DL645_CALL_AREA%>;
  ComntProtMsg.YFF_CALL_DL645_CALL_RATE = <%=ComntProtMsg.YFF_CALL_DL645_CALL_RATE%>;
  ComntProtMsg.YFF_CALL_DL645_CALL_ALARM = <%=ComntProtMsg.YFF_CALL_DL645_CALL_ALARM%>;
  ComntProtMsg.YFF_CALL_DL645_CALL_POWLINIT = <%=ComntProtMsg.YFF_CALL_DL645_CALL_POWLINIT%>;
  ComntProtMsg.YFF_CALL_DL645_CALL_NOCYCUT = <%=ComntProtMsg.YFF_CALL_DL645_CALL_NOCYCUT%>;
  ComntProtMsg.YFF_CALL_DL645_CALL_LOCK = <%=ComntProtMsg.YFF_CALL_DL645_CALL_LOCK%>;
  
  var ComntUseropDef = {};
  ComntUseropDef.YFF_NPOPER_CALLBASE = <%=ComntUseropDef.YFF_NPOPER_CALLBASE%>;
  ComntUseropDef.YFF_NPOPER_SETBASE = <%=ComntUseropDef.YFF_NPOPER_SETBASE%>;
  </script>
    <script src="<%=basePath%>js/np/dialog/callset_fee.js"></script>
  </body>
</html>
