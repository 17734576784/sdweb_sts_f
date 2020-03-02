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
    <title>居民费控参数显示配置</title>
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
  <script src="<%=basePath%>js/dyjc/dialog/ZBlookItemDialog.js"></script>
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
	  	<td class="titlem" style="width: 100px;" id="yffcs">预付费参数</td>
	  	<td class="titler" id="yffcs_r"></td>
	  	<td width="5"></td>
	  	<td class="titlel1" id="yffzt_l"></td>
	  	<td class="titlem1" style="width: 100px;" id="yffzt">预付费状态</td>
	  	<td class="titler1" id="yffzt_r"></td>
	  	<td width="5"></td>
	  	<td class="titlel1" id="bjkzcs_l"></td>
	  	<td class="titlem1" style="width: 100px;" id="bjkzcs">报警及控制参数</td>
	  	<td class="titler1" id="bjkzcs_r"></td>
	  	<td colspan=5 style="text-align:right; border: 0px;">
	    	<button id="set" class="btn" >设置</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="exit" class="btn" onclick="window.close();">关闭</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    </td>
  	</tr>
  	</table>
  </td>
  </tr>
  </table>
  <%int i=101;%>
  <%int j=101;%>
  <div id="d_yffcs" class="d_cl">
  <table class="tabsty" align="center">
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">基本信息<label for="yffcs_jbxx_selAll">******全选</label>&nbsp;<input type="checkbox" id="yffcs_jbxx_selAll"  style="width: 16px;"/></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td style="text-align: left; border-right: none;" width="131px"><label for="<%=i++%>"><b style="color:#800000">终端名称</b></label></td>
      <td style="border-left: none;"><input checked="checked" disabled="disabled" type="checkbox" id="<%=j++%>" style="width: 16px; color: red;"/></td>
      
      <td style="text-align: left; border-right: none;" width="131px"><label for="<%=i++%>"><b style="color:#800000">客户编号</b></label></td> 
      <td style="border-left: none;"><input checked="checked" disabled="disabled" type="checkbox" id="<%=j++%>" style="width: 16px; color: red;"/></td>
      
      <td style="text-align: left; border-right: none;" width="131px"><label for="<%=i++%>"><b style="color:#800000">测量点名称</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>
      
    <tr>
      <td style="text-align: left; border-right: none;" width="131px"><label for="<%=i++%>"><b style="color:#800000">ESAM表号</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;" width="131px"><label for="<%=i%>"><b style="color:#800000">客户名称</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;" width="131px"></td> 
      <td style="border-left: none;"></td>
    </tr>
    <%int part1_1 = i;
      i=201;
      j=201;
     %>
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">费控参数<label for="yffcs_fkcs_selAll">******全选</label>&nbsp;<input type="checkbox" id="yffcs_fkcs_selAll" style="width: 16px;"/></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">计费方式</b></label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">费控方式</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">缴费方式</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>    
    
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">预付费控制类型</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">透支值</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">费率方案号</b></label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>    
    
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">报警方案</b></label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">保电时间</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">不全局保电</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>  
       
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">密钥版本</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">加密机ID</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">费率启用日期</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>     
    
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i%>">动力关联</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"></td> 
      <td style="border-left: none;"></td>
      
      <td style="text-align: left; border-right: none;"></td> 
      <td style="border-left: none;"></td>
    </tr> 
    
    <%int part1_2 = i;
      i=301;
      j=301;
     %>
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>    
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">算费信息<label for="yffcs_sfxx_selAll">******全选</label>&nbsp;<input type="checkbox" id="yffcs_sfxx_selAll"  style="width: 16px;"/></td>
          </tr>
        </table></td>
    </tr>      
     
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">抄表日</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">抄表周期</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">主站算费标志</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>    

    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">是否发行电费</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>       
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">发行起始日期</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">阶梯切换月日</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    
    </tr> 
    
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">费率更改标志</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i%>">费率更改内容</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j%>" style="width: 16px;"/></td>
      
      
      <td style="text-align: left; border-right: none;"></td> 
      <td style="border-left: none;"></td>
    </tr> 
    <%
      int part1_3 = i;
      i=401;
      j=401;
    %>    
    </table>
  </div>
  <div id="d_yffzt" style="display: none;"  class="d_cl">
  <table class="tabsty" align="center">   
   <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">基本状态<label for="yffzt_jbzt_selAll">******全选</label>&nbsp;<input type="checkbox" id="yffzt_jbzt_selAll"  style="width: 16px;"/></td>
          </tr>
        </table></td>
   </tr>


    <tr>
      <td style="text-align: left; border-right: none;" width="131px"><label for="<%=i++%>"><b style="color:#800000">客户状态</b></label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;" width="131px"><label for="<%=i++%>"><b style="color:#800000">操作类型</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;" width="131px"><label for="<%=i++%>">操作时间</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>    


    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">缴费金额</b></label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">断电金额</b></label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">断电金额2</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>    
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">购电次数</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">当前剩余</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">当前剩余2</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr> 
       
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">报警门限</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">跳闸门限</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>

      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">累计购电值</b></label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>    
 
    <tr>    
      <td style="text-align: left; border-right: none;"><label for="<%=i%>"><b>开户日期</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"></td> 
      <td style="border-left: none;"></td>
      
      <td style="text-align: left; border-right: none;"></td> 
      <td style="border-left: none;"></td>
    </tr>
    <%int part2_1 = i;
      i=501;
      j=501;
    %>    
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>    
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">表底信息<label for="yffzt_bdxx_selAll">******全选</label>&nbsp;<input type="checkbox" id="yffzt_bdxx_selAll" style="width: 16px;"/></td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">结算总表底</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">结算尖表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">结算峰表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>       

 
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">结算平表底</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">结算谷表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"></td> 
      <td style="border-left: none;"></td>
    </tr>       

    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">结算时间</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">算费时间</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">算费表底时间</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr> 

    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">算费总表底</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">算费尖表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">算费峰表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>     


    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">算费平表底</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i%>">算费谷表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"></td> 
      <td style="border-left: none;"></td>
    </tr>
    <%int part2_2 = i;
      i=601;
      j=601;
    %>   
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>    
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">电费信息<label for="yffzt_dfxx_selAll">******全选</label>&nbsp;<input type="checkbox" id="yffzt_dfxx_selAll"  style="width: 16px;"/>
          </tr>
        </table></td>
    </tr>

    <tr>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">阶梯追补电量</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">阶梯累计用电量</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>

      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">换表时间</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr> 
        
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">发行电费当月缴费</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">发行电费当月缴费2</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    
            <td style="text-align: left; border-right: none;"><label for="<%=i++%>">阶梯上次自动切换日期</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>
    
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">发行电费剩余金额</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">发行电费剩余金额2</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">阶梯切换执行时间</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr> 
        
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">发行电费年月</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">发行电费数据日期</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i%>">发行电费算费时间</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j%>" style="width: 16px;"/></td>
    </tr>  
    <%int part2_3 = i;
      i=701;
      j=701;
    %>   
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>    
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">控制状态<label for="yffzt_kzzt_selAll">******全选</label>&nbsp;<input type="checkbox" id="yffzt_kzzt_selAll" style="width: 16px;"/></td>
          </tr>
        </table></td>
    </tr>
 
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">报警1状态</b></label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">报警2状态</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">分合闸状态</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>
         

    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">报警1改变时间</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">报警2改变时间</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">分合闸改变时间</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>      


    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">异常标志1</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">异常标志2</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i%>">分闸时总表底</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j%>" style="width: 16px;"/></td>
    </tr>       
  </table>
  </div>
  <%int part2_4 = i;
    i=801;
    j=801;
  %>
  <div id="d_bjkzcs" style="display: none;"  class="d_cl">
  <table class="tabsty" align="center">
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">报警及控制参数<label for="alarm">*****全选</label>&nbsp;<input type="checkbox" id="alarm" style="width: 16px;"/></td>
          </tr>
        </table></td>
    </tr>     

 
    <tr>
      <td style="text-align: left; border-right: none;" width="160px"><label for="<%=i++%>">报警1短信</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;" width="160px"><label for="<%=i++%>">报警1声音</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;" width="160px"><label for="<%=i++%>">分合闸确认</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>     
 
   
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">报警2短信</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>

      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">报警2声音</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">分闸次数</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>            


    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">报警1短信时间</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">报警1声音时间</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">分合闸发送时间</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>      
    
      
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">报警2短信时间</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">报警2声音时间</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">成功分合闸时间</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>     
    
     
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">报警1短信UUID</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">报警2短信UUID</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i%>">信息输出</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j%>" style="width: 16px;"/></td>
    </tr>   
  </table>
  </div>
  <%int part3_1 = i;%>
  </body>
  <script type="text/javascript">
      var gl_part = new Array(9)//记录 3页共九个小标题
      gl_part[0] = <%= part1_1 %>
      gl_part[1] = <%= part1_2 %>
      gl_part[2] = <%= part1_3 %>
      gl_part[3] = <%= part2_1 %>
      gl_part[4] = <%= part2_2 %>
      gl_part[5] = <%= part2_3 %>
      gl_part[6] = <%= part2_4 %>
      gl_part[7] = <%= part3_1 %>
      
  </script>
</html>
