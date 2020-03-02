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
    <title>居民抄表卡显示配置</title>
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
  <script src="<%=basePath%>js/dyjc/dialog/RmlookItemDialog.js"></script>
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
      
      <td style="text-align: left; border-right: none;" width="131px"><label for="<%=i++%>"><b style="color:#800000">电表表号</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>
      
    <tr>
      
      <td style="text-align: left; border-right: none;" width="131px"><label for="<%=i++%>"><b style="color:#800000">客户名称</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>

      <td style="text-align: left; border-right: none;" width="131px"><label for="<%=i++%>"><b style="color:#800000">客户地址</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>

      <td style="text-align: left; border-right: none;" width="131px"><label for="<%=i++%>"><b style="color:#800000">卡内户号</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>
    
    <tr>

      
      <td style="text-align: left; border-right: none;" width="131px"><label for="<%=i++%>"><b style="color:#800000">操作员</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;" width="131px"><label for="<%=i%>"><b style="color:#800000">导入日期</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"></td> 
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
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">抄表时间</b></label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">购电值</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">购电次数</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>    
    	
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">计费方式</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">囤积值</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">报警值</b></label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>    
    
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">透支限值</b></label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">电压变比</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">电流变比</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>  
       
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">费率电价</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">阶梯电价</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">非法次数</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>     
    
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">跳闸次数</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">状态字1</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">状态字2</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      

    </tr> 
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">剩余值</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>

      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">透支值</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>

      <td style="text-align: left; border-right: none;"><label for="<%=i%>">累计购电值</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j%>" style="width: 16px;"/></td>
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
            <td style="border: 0px; font-size: 12px;">表底信息<label for="yffcs_sfxx_selAll">******全选</label>&nbsp;<input type="checkbox" id="yffcs_sfxx_selAll"  style="width: 16px;"/></td>
          </tr>
        </table></td>
    </tr>      
     
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>"><b style="color:#800000">组合正有</b></label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">正有总</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">正有尖</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    </tr>    
	
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">正有峰</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>       
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">正有平</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">正有谷</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    
    </tr> 
    
    <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">反有总</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">反有尖</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">反有峰</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      
      </tr>
      <tr>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">反有平</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">反有谷</label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>       
      
      <td style="text-align: left; border-right: none;"><label for="<%=i++%>">上月正有总</label></td>
      <td style="border-left: none;"><input type="checkbox" id="<%=j++%>" style="width: 16px;"/></td>
    
    </tr> 
	<tr>
	
      <td style="text-align: left; border-right: none;"><label for="<%=i%>">上上月正有总 </label></td> 
      <td style="border-left: none;"><input type="checkbox" id="<%=j%>" style="width: 16px;"/></td>
	  <td style="text-align: left; border-right: none;"></td> 
      <td style="border-left: none;"></td>
       <td style="text-align: left; border-right: none;"></td> 
      <td style="border-left: none;"></td>
	
	</tr>        
    <%
      int part1_3 = i;
      i=401;
      j=401;
    %>    
    </table>
 	<table>
 	<tr>
 	<td style="text-align: left; border-right: none;" width="131px"></td>
 	<td style="border-left: none;" width="80px"></td>
 		<td>&nbsp;&nbsp;&nbsp;&nbsp;<button id="set" class="btn" >设置</button>&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
  	  <button id="exit" class="btn" onclick="window.close();">关闭</button></td></td>
  	  </tr>
  	  </table>
  </body>
  <script type="text/javascript">
      var gl_part = new Array(3)//记录 3页共九个小标题
      gl_part[0] = <%= part1_1 %>
      gl_part[1] = <%= part1_2 %>
      gl_part[2] = <%= part1_3 %>
      
  </script>
</html>
