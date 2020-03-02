<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.Dict"%>
<%@page import="com.kesd.common.YDTable"%>
<%@page import="com.kesd.util.I18N"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title><i18n:message key="doc.zjzfkcs" /></title>
    <style type="text/css">
body {
	margin:0 0 0 0;
	overflow : false;
	background: #eaeeef;
}
.tabsty {
	width:95%;
}
.tabsty input, .tabsty select {
	width: 140px;
}
input, select {
	font-size: 12px;
}
.d_cl{
	border-top: 3px solid #40a6a4;
}
</style>
<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/dyjc/dialog/fxdfLookItemDialog.js"></script>
  <script src="<%=basePath%>js/common/gridopt.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
  <script src="<%=basePath%>js/validate.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  <script src="<%=basePath%>js/common/loading.js"></script>
  <script src="<%=basePath%>js/common/jsonString.js"></script>
  <script src="<%=basePath%>js/common/cookie.js"></script>
  </head>
  <body>
  <br/>
  <table style="font-size: 12px; width:95%" cellpadding="0" cellspacing="0" align="center">
  <tr>
  <td>
  	<table cellpadding="0" cellspacing="0" width="100%">
  	<tr>
	  	<td class="titlel" id="yffcs_l"></td>
	  	<td class="titlem" style="width: 100px;" id="yffcs">基本信息</td>
	  	<td class="titler" id="yffcs_r"></td>
	  	<td width="5"></td>
	  	<td class="titlel1" id="yffzt_l"></td>
	  	<td class="titlem1" style="width: 100px;" id="yffzt">表底信息</td>
	  	<td class="titler1" id="yffzt_r"></td>
	  	<td colspan=5 style="text-align:right; border: 0px;">
	    	<button id="set" class="btn" >设置</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="exit" class="btn" onclick="window.close();">关闭</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    </td>
  	</tr>
  	</table>
  </td>
  </tr>
  </table>
  <div id="d_yffcs" class="d_cl">
  <table class="tabsty" align="center">
    <tr>
      <td colspan="6" style="border:0px; height:5px;"></td>
    </tr>    
  	<tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">基本信息<label for="jbxx_selAll">******全选</label>&nbsp;<input type="checkbox" id="jbxx_selAll"  style="width: 16px;"/></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td style="text-align: left; border-right: none;" width="131px"><label for="1"><b style="color:#800000">所属集中器</b></label></td>
      <td style="border-left: none;"><input checked="checked" disabled="disabled" type="checkbox" id="1" style="width: 16px; color: red;"/></td>
      
      <td style="text-align: left; border-right: none;" width="131px"><label for="2"><b style="color:#800000">客户编号</b></label></td> 
      <td style="border-left: none;"><input checked="checked" disabled="disabled" type="checkbox" id="2" style="width: 16px; color: red;"/></td>
      
      <td style="text-align: left; border-right: none;" width="131px"><label for="3">客户名称</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="3" style="width: 16px;"/></td>
    </tr>
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">费控参数<label for="fkcs_selAll">******全选</label>&nbsp;<input type="checkbox" id="fkcs_selAll" style="width: 16px;"/></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td style="text-align: left; border-right: none;"><label for="4"><b style="color:#800000">发行电费年月</b></label></td>
      <td style="border-left: none;"><input type="checkbox" id="4" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="5"><b style="color:#800000">发行电费算费时间</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="5" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="6">上次电费年月</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="6" style="width: 16px;"/></td>
    </tr>    
    
    <tr>
      <td style="text-align: left; border-right: none;"><label for="7"><b style="color:#800000">本月缴费总金额</b></label></td>
      <td style="border-left: none;"><input type="checkbox" id="7" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="8"><b style="color:#800000">上次剩余</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="8" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="9"><b style="color:#800000">当前剩余</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="9" style="width: 16px;"/></td>
    </tr>
    
     
    <tr>
      <td style="text-align: left; border-right: none;"><label for="10"><b style="color:#800000">电费金额</b></label></td>
      <td style="border-left: none;"><input type="checkbox" id="10" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="11">阶梯追补累计用电量</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="11" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="12"><b style="color:#800000">阶梯累计用电量</b> </label></td> 
      <td style="border-left: none;"><input type="checkbox" id="12" style="width: 16px;"/></td>
    </tr>      
  
    <tr>
      <td style="text-align: left; border-right: none;"><label for="13">断电值/断电金额 </label></td> 
      <td style="border-left: none;"><input type="checkbox" id="13" style="width: 16px;"/></td>
      <td colspan=4></td>
    </tr>  
    <tr>
      <td style="text-align: left; border-right: none;"><label for="46">更新次数</label></td>
      <td style="border-left: none;"><input type="checkbox" id="46" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="47">整月标志 </label></td> 
      <td style="border-left: none;"><input type="checkbox" id="47" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="48">操作员</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="48" style="width: 16px;"/></td>
    </tr>  
<!--    <tr>-->
<!--      <td style="text-align: left; border-right: none;"><label for="64">结算补差标记</label></td>-->
<!--      <td style="border-left: none;"><input type="checkbox" id="64" style="width: 16px;"/></td>-->
<!--      -->
<!--      <td style="text-align: left; border-right: none;"><label for="65">结算补差日期</label></td> -->
<!--      <td style="border-left: none;"><input type="checkbox" id="65" style="width: 16px;"/></td>-->
<!--      -->
<!--      <td style="text-align: left; border-right: none;"><label for="66">结算补差时间</label></td> -->
<!--      <td style="border-left: none;"><input type="checkbox" id="66" style="width: 16px;"/></td>-->
<!--    </tr>  -->
   
    </table>
  </div>
  <div id="d_yffzt" style="display: none;"  class="d_cl">
  <table class="tabsty" align="center">   
    <tr>
      <td colspan="6"   style="border:0px; height:5px;"></td>
    </tr>    
   
   <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">上次结算时间及表底<label for="scjs_selAll">******全选</label>&nbsp;<input type="checkbox" id="scjs_selAll"  style="width: 16px;"/></td>
          </tr>
        </table></td>
   </tr>
    <tr>
      <td style="text-align: left; border-right: none;" width="131px"><label for="14"><b style="color:#800000">上次结算时间</b></label></td>
      <td style="border-left: none;"><input type="checkbox" id="14" style="width: 16px;"/></td>
      <td colspan=2 ></td>  <td colspan=2 ></td>
    </tr>    
    <tr>
      <td style="text-align: left; border-right: none;"><label for="15"><b style="color:#800000">上次结算总表底</b></label></td>
      <td style="border-left: none;"><input type="checkbox" id="15" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="20">动力关联1上次结算总表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="20" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="25">动力关联2上次结算总表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="25" style="width: 16px;"/></td>
    </tr>    
	<tr>
      <td style="text-align: left; border-right: none;"><label for="16">上次结算尖表底</label></td>
      <td style="border-left: none;"><input type="checkbox" id="16" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="21">动力关联1上次结算尖表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="21" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="26">动力关联2上次结算尖表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="26" style="width: 16px;"/></td>
    </tr>    
	<tr>
      <td style="text-align: left; border-right: none;"><label for="17">上次结算峰表底</label></td>
      <td style="border-left: none;"><input type="checkbox" id="17" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="22">动力关联1上次结算峰表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="22" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="27">动力关联2上次结算峰表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="27" style="width: 16px;"/></td>
    </tr>    
	<tr>
      <td style="text-align: left; border-right: none;"><label for="18">上次结算平表底</label></td>
      <td style="border-left: none;"><input type="checkbox" id="18" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="23">动力关联1上次结算平表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="23" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="28">动力关联2上次结算平表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="28" style="width: 16px;"/></td>
    </tr>    
	<tr>
      <td style="text-align: left; border-right: none;"><label for="19">上次结算谷表底</label></td>
      <td style="border-left: none;"><input type="checkbox" id="19" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="24">动力关联1上次结算谷表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="24" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="29">动力关联2上次结算谷表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="29" style="width: 16px;"/></td>
    </tr>    

    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>    
   <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">算费表底时间及表底<label for="sfbd_selAll">******全选</label>&nbsp;<input type="checkbox" id="sfbd_selAll"  style="width: 16px;"/></td>
          </tr>
        </table></td>
   </tr>
    <tr>
      <td style="text-align: left; border-right: none;" width="131px"><label for="30"><b style="color:#800000">算费表底时间</b></label></td>
      <td style="border-left: none;"><input type="checkbox" id="30" style="width: 16px;"/></td>
      <td colspan=2 ></td>  <td colspan=2 ></td>
    </tr>    
    <tr>
      <td style="text-align: left; border-right: none;"><label for="31"><b style="color:#800000">算费表底总表底</b></label></td>
      <td style="border-left: none;"><input type="checkbox" id="31" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="36">动力关联1算费表底总表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="36" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="41">动力关联2算费表底总表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="41" style="width: 16px;"/></td>
    </tr>    
	<tr>
      <td style="text-align: left; border-right: none;"><label for="32">算费表底尖表底</label></td>
      <td style="border-left: none;"><input type="checkbox" id="32" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="37">动力关联1算费表底尖表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="37" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="42">动力关联2算费表底尖表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="42" style="width: 16px;"/></td>
    </tr>    
	<tr>
      <td style="text-align: left; border-right: none;"><label for="33">算费表底峰表底</label></td>
      <td style="border-left: none;"><input type="checkbox" id="33" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="38">动力关联1算费表底峰表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="38" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="43">动力关联2算费表底峰表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="43" style="width: 16px;"/></td>
    </tr>    
	<tr>
      <td style="text-align: left; border-right: none;"><label for="34">算费表底平表底</label></td>
      <td style="border-left: none;"><input type="checkbox" id="34" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="39">动力关联1算费表底平表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="39" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="44">动力关联2算费表底平表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="44" style="width: 16px;"/></td>
    </tr>    
	<tr>
      <td style="text-align: left; border-right: none;"><label for="35">算费表底谷表底</label></td>
      <td style="border-left: none;"><input type="checkbox" id="35" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="40">动力关联1算费表底谷表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="40" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="45">动力关联2算费表底谷表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="45" style="width: 16px;"/></td>
    </tr>  
  </table>
  </div>
  
  </body>
</html>
