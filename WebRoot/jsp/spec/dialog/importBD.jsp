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
    <title>电表表底录入</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
			input,select{
				font-size: 12px;
				width: 100px;
			}
			body{
				overflow: hidden;
			}
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script src="<%=basePath%>js/common/def.js"></script>
		<script src="<%=basePath%>js/common/loading.js"></script>
		<script src="<%=basePath%>js/common/number.js"></script>
		<script src="<%=basePath%>js/common/jsonString.js"></script>
		<script src="<%=basePath%>js/spec/dialog/importBD.js"></script>
		<script src="<%=basePath%>js/dialog/opdetail.js"></script>
  </head>
  
  <body>
   <table class="tabinfo" align="center" style="width: 90%">
      <tr><td colspan="7" style="height:20px; border: 0px;"></td></tr>
	    <tr>
	    	<td class="tdr" width="70px;">数据来源:</td>
	    	<td colspan="4" width="56%">
	    	<select id=dataSrc>
	    		<option value=0>系统历史数据</option>
	    		<option value=1>召测电表数据</option>
	    	</select></td>
	    	<td colspan="2" width="28%"><button id="btnRead" class="btn">读取</button>
	    	<button id="btnCall" class="btn" style="display:none">召测</button>
	    	</td>
	  	</tr><tr>
	    	<td class="tdr" width="70px;"><span id="spandataType">数据类型:</span><span id="spandataDate" style="display:none">数据日期:</span></td>
	    	<td colspan="4" width="56%">
	    	<select id=dataType style="display:none;">
	    		<option value=0>实时数据</option>
	    		<option value=1>日冻结数据</option>
	    	</select>
	    	<input id=dateTime name=dateTime onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',maxDate:'%y-%M-%d',isShowClear:'false'});" readonly />
	    	</td>
	    	<td colspan="2" width="28%"><button id="btnCallInfo"  class="btn" style="display:none">详细信息</button></td>		
	  	</tr>
	 </table>
	 <table class="tabinfo" align="center" style="width: 90%">
	    <tr><td colspan="7" style="height:20px; border: 0px;"></td></tr>
	    <tr>
	    	<td class="tdr"  width="70px;">&nbsp;</td>
	    	<td colspan=2 id="mp_name0" width="28%">测量点1</td>
	    	<td colspan=2 id="mp_name1" width="28%">测量点2(无效)</td>
	    	<td colspan=2 id="mp_name2" width="28%">测量点3(无效)</td>
	    </tr>
	    <tr>
	    	<td class="tdr">总表底:</td>
	    	<td colspan=2><input id=zbd0 style="width:95%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    	<td colspan=2><input id=zbd1 style="width:95%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    	<td colspan=2><input id=zbd2 style="width:95%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    </tr>
	    <tr>
	    	<td class="tdr">尖表底:</td>
			<td colspan=2><input id=jbd0 style="width:95%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
			<td colspan=2><input id=jbd1 style="width:95%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
			<td colspan=2><input id=jbd2 style="width:95%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
		</tr>
	    <tr>
	    	<td class="tdr">峰表底:</td>
	    	<td colspan=2><input id=fbd0 style="width:95%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    	<td colspan=2><input id=fbd1 style="width:95%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    	<td colspan=2><input id=fbd2 style="width:95%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    </tr>	
	    <tr>
	    	<td class="tdr">平表底:</td>
	    	<td colspan=2><input id=pbd0 style="width:95%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    	<td colspan=2><input id=pbd1 style="width:95%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    	<td colspan=2><input id=pbd2 style="width:95%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    </tr>
	    <tr>
	    	<td class="tdr">谷表底:</td>
	    	<td colspan=2><input id=gbd0 style="width:95%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    	<td colspan=2><input id=gbd1 style="width:95%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    	<td colspan=2><input id=gbd2 style="width:95%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    </tr>
	    <tr>
	    	<td class="tdr">无功表底:</td>
	    	<td colspan=2><input id=zwbd0 style="width:95%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    	<td colspan=2><input id=zwbd1 style="width:95%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    	<td colspan=2><input id=zwbd2 style="width:95%" onkeyup="if(checkBD()){calBdInfo()}" /></td>
	    </tr>
	    <tr>
	    	<td class="tdr">表底信息:</td><td colspan=6 id="bdInfo"></td>
	    	</tr>
	    </table>
	    <table class="tabinfo" align="center" style="width: 90%">
	    <tr><td style="border: 0px; padding-top: 10px;">
		    <button id="btnClear"  class="btn">清空</button></td>
		 <td colspan="2" style="border: 0px; text-align: right;padding-top: 10px;">
		    <button id="btnOK" class="btn" >确定</button>&nbsp;&nbsp;
		    <button id="btnCancel" class="btn" onclick="window.close()">取消</button>
    		<input type="hidden" id="zbd" value="0" />
    		<input type="hidden" id="jbd" value="0" />
    		<input type="hidden" id="fbd" value="0" />
    		<input type="hidden" id="pbd" value="0" />
    		<input type="hidden" id="gbd" value="0" />
    		<input type="hidden" id="wbd" value="0" />
	    </td></tr>
	    </table>
	    <script type="text/javascript">
	    var ComntUseropDef = {};
	    var ComntProtMsg = {};
	    ComntUseropDef.YFF_READDATA = <%=com.kesd.comntpara.ComntUseropDef.YFF_READDATA%>;
	    ComntProtMsg.YFF_CALL_REAL_ZBD = <%=com.kesd.comnt.ComntProtMsg.YFF_CALL_REAL_ZBD%>;
	    ComntProtMsg.YFF_CALL_REAL_FBD = <%=com.kesd.comnt.ComntProtMsg.YFF_CALL_REAL_FBD%>;
	    ComntProtMsg.YFF_CALL_DAY_ZBD = <%=com.kesd.comnt.ComntProtMsg.YFF_CALL_DAY_ZBD%>;
	    ComntProtMsg.YFF_CALL_DAY_FBD = <%=com.kesd.comnt.ComntProtMsg.YFF_CALL_DAY_FBD%>;
	    </script>
  </body>
</html>
