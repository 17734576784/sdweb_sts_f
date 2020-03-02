<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'cosstand.jsp' starting page</title>
 <style type="text/css">
body {
	margin: 0 0 0 0;
	overflow: hidden;
}
.tabsty1 select {
	width: 115px;
}
input{
	height : 21px;
	font-size: 12px;
}
select {
	font-size: 12px;
}
.table1 select, .table1 input {
	width:150px
}
.table1 td {
	font-size:12px;
	border:none;
	text-align:right;
}
.table2 select, .table2 input {
	width:70px
}
.table2 td {
	font-size:12px;
	border:none;
	text-align:right;
}
</style>
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/docs/cosstand.js"></script>
  <script src="<%=basePath%>js/docs/pub_docper.js"></script>
  <script src="<%=basePath%>js/common/gridopt.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
  <script src="<%=basePath%>js/validate.js"></script>
  <script  src="<%=basePath%>js/common/jsonString.js"></script>
  <script  src="<%=basePath%>js/common/loading.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  <jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
  </head>
  
  <body>
<script type="text/javascript" src="../../js/css.js"></script>
<table id="zzz" width="100%">
  <tr>
    <td width="45%" align="center" valign="top"><div>
        <table id="gridbox"  cellpadding="0" cellspacing="0" width="100%" class="page_tbl_bg22">
          <tr height=24px>
            <td align="left">&nbsp;
            <jsp:include page="../inc/btn_pldel.jsp"></jsp:include>
            </td>
          </tr>
          <tr>
            <td ><DIV id=gridbox1></DIV>
              <input type=hidden value=0 id=prs /></td>
          </tr>
        </table>
      </div></td>
    <td style="width:7px;background: url('../../images/cont_3_center1.gif') repeat-y; margin:0 0 0 0; margin-top:-5px; padding:0 0 0 0;"></td>
    <td width="55%"  valign="top"><div style="margin:0 0 0 0;padding:0 0 0 0;">
        <table id="table1" cellpadding="0" cellspacing="0" width="100%" class="page_tbl_bg22">
          <tr height=24px>
            <td align="left" style="padding-left:5px;">
		    <jsp:include page="../inc/btn_tianjia.jsp"></jsp:include>
		    &nbsp;&nbsp;
		    <jsp:include page="../inc/btn_xiugai.jsp"></jsp:include>
		    &nbsp;&nbsp;
            </td><td align="right" style="padding-right: 5px;">
            <span id="ttname"></span></td>
          </tr>
          <tr>
            <td colspan="2"><div>
                <form action="" method="post" id="addorupdate"  style='display:inline;'>
                  <table class="tabsty1" align="left" width="100%" style="border:none;">
                    <tr style="border:none;">
                      <td style="border:none; text-align:center;" ><fieldset style=" width:100%; text-align:center; padding-top:5px;padding-bottom:5px; line-height:25px;">
                          <table class="table1" width="99%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td><font color=red>*</font>名称:</td>
                              <td style="text-align:left;"><input type="text"  name="csStand.describe" id="describe" /></td>
                              <td>使用标志:</td>
                              <td style="text-align:left;"><select name="csStand.useFlag" id="useFlag">
                                </select></td>
                            </tr>
                            <tr>
                              <td>执行标准:</td>
                              <td style="text-align:left;"><input type="text" name="csStand.stand" id="stand" /></td>
                              
                               <td>最小值:</td>
                              <td style="text-align:left;"><input type="text" name="csStand.csminval" id="csMinval" /></td>
                            </tr>
                          </table>
                        </fieldset></td>
                    </tr>
                  </table>
                </form>
              </div></td>
          </tr>
          <tr>
            <td colspan="2"><DIV id=gridbox2></DIV></td>
          </tr>
          <tr>
            <td><input type="hidden" id="protIIdi" name="protIIdi"/></td>
          </tr>
          <tr style="display:none;">
            <td>&nbsp;信息类:
              <select name="protIId" id="protIId" style="width:450px;" >
              </select></td>
          </tr>
        </table>
      </div></td>
  </tr>
</table>
<input type="hidden" id="stand_id"/>
<input type="hidden" id="cos_id"/>
<input type="hidden" id="item_id"/>
<script type="text/javascript">
  		$("#gridbox1").height($(window).height()-60);
		$("#gridbox2").height($(window).height()-130 + 20);
</script>
</body>
</html>
