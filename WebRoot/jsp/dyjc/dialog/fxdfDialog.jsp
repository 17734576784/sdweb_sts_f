<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<i18n:bundle baseName="<%=SDDef.BUNDLENAME%>" id="bundle" locale="<%=WebConfig.app_locale%>" scope="application" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    <title>重新发行电费
    </title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
			input,select{
				font-size: 12px;
				width: 140px;				
			}	
			body{
			overflow: hidden;
			}			  
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>		
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/fxdfDialog.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/dialog/opdetail.js"></script>
		
  </head>
  
  <body>
  
   <table class="tabinfo" align="center" style="width: 580">
      	<tr><td colspan="4" style="height:10px; border: 0px;"></td></tr>
	    <tr>
	    	<td class="tdr" style="border:0">结算时间:</td>
	    	<td colspan="2" width="56%" style="border:0"><input id=dateTime name=dateTime onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',maxDate:'%y-%M-%d',isShowClear:'false'});" readonly /></td>
	    	<td width="28%" style="border:0"><button id="btnRead" class="btn">读取</button>
	    	</td>		
	  	</tr>
	    <tr><td colspan="4" style="height:8px; border: 0px;"></td></tr>
	    <tr>
	    	<td class="tdr"  width="70px;">&nbsp;</td>
	    	<td id="mp_name0" width="28%">测量点1</td>
	    	<td id="mp_name1" width="28%">测量点2(无效)</td>
	    	<td id="mp_name2" width="28%">测量点3(无效)</td>
	    </tr>
	    <tr>
	    	<td class="tdr">总表底:</td>
	    	<td ><input id=zbd0 style="width:97%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    	<td><input id=zbd1 style="width:97%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    	<td><input id=zbd2 style="width:97%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    </tr>
	    <tr>
	    	<td class="tdr">尖表底:</td>
			<td><input id=jbd0 style="width:97%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
			<td><input id=jbd1 style="width:97%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
			<td><input id=jbd2 style="width:97%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
		</tr>
	    <tr>
	    	<td class="tdr">峰表底:</td>
	    	<td><input id=fbd0 style="width:97%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    	<td><input id=fbd1 style="width:97%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    	<td><input id=fbd2 style="width:97%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    </tr>	
	    <tr>
	    	<td class="tdr">平表底:</td>
	    	<td><input id=pbd0 style="width:97%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    	<td><input id=pbd1 style="width:97%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    	<td><input id=pbd2 style="width:97%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    </tr>
	    <tr>
	    	<td class="tdr">谷表底:</td>
	    	<td><input id=gbd0 style="width:97%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    	<td><input id=gbd1 style="width:97%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    	<td><input id=gbd2 style="width:97%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    </tr>
	    <tr><td class="tdr">表底信息:</td><td colspan=6 id="bdInfo"></td></tr>
	    <tr><td colspan="4" style="height:10px; border: 0px;"></td></tr>
	    </table>
	   <table class="gridbox" align="center" style="width: 580">
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
		</table>
	   <table class="tabinfo" align="center" style="width: 580">
	    <tr>
	    	<td style="border: 0px; padding-top: 10px;"></td>
		 	<td colspan="2" style="border: 0px; text-align: right;padding-top: 10px;">
			    <button id="btnUpdate"  class="btn" ></button>&nbsp;&nbsp;
			    <button id="btnCancel" class="btn" onclick="window.close();">取消</button>
	    		<input type="hidden" id="zbd" value="0" /><input type="hidden" id="jbd" value="0" />
	    		<input type="hidden" id="fbd" value="0" /><input type="hidden" id="pbd" value="0" /><input type="hidden" id="gbd" value="0" />
	    </td></tr>
	    </table>
  </body>

  	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	
	<script type="text/javascript">
	$("#gridbox").height(200);
	
	var ComntUseropDef = {};
	ComntUseropDef.YFF_READDATA = <%=com.kesd.comntpara.ComntUseropDef.YFF_READDATA%>;
	ComntUseropDef.YFF_DYOPER_REFXDF = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_REFXDF%>;
	ComntUseropDef.YFF_DYOPER_REJTREST = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_REJTREST%>;
	
	var ComntProtMsg = {};
	ComntProtMsg.YFF_CALL_REAL_ZBD = <%=com.kesd.comnt.ComntProtMsg.YFF_CALL_REAL_ZBD%>;
	ComntProtMsg.YFF_CALL_DAY_ZBD = <%=com.kesd.comnt.ComntProtMsg.YFF_CALL_DAY_ZBD%>;
	ComntProtMsg.YFF_DY_SET_USERID = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_SET_USERID%>;
	</script>
</html>
