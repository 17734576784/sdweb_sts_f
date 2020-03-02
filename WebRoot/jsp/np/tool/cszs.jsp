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
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<style type="text/css">
			.tabinfo input{
				width: 99%;
			}
			input,select{
				font-size: 12px;				
			}			  
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/cszs.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>	
		<script  src="<%=basePath%>js/common/loading.js"></script>
		
	</head>
	<body>
	<table id="tabinfo" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr>
	    	   	<td class="td_lable" style="height:25px;"><i18n:message key="baseInfo.jbxx"/></td>
	 			<td colspan=4 style="padding-left:10px; border: 0px;">
	    		<button id="btnSearch" class="btn" ><i18n:message key="btn.jiansuo"/></button>
	    		</td>
	    		<td style="border:0px; text-align: right;"><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    </tr>
   		<tr>
	    	<td class="tdr" width="10%"><i18n:message key="baseInfo.bh"/></td><td id="esamno">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.yhmc"/></td><td colspan=3 id="username" style="width: 15%"> </td>
	    </tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.flfa"/></td><td id="feeproj_desc">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.fams"/></td><td colspan=3 id="feeproj_detail">&nbsp;</td>
	   	</tr>
	   	<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable"><i18n:message key="baseInfo.zzcs"/></td></tr>
	   	<tr>
	    	<td class="tdr"><i18n:message key="baseInfo.yhbh"/></td><td id="db_userno"></td>
	    	<td class="tdr"><i18n:message key="baseInfo.ptbb"/></td><td id="db_pt_ratio"></td>
	    	<td class="tdr"><i18n:message key="baseInfo.ctbb"/></td><td id="db_ct_ratio"></td>
	    </tr>
	    <tr>
	   		<td class="tdr"><i18n:message key="baseInfo.bjfa"/></td><td id="yffalarm_desc"></td>
	   		<td class="tdr">报警值1:</td><td id=db_alarm1></td>
	   		<td class="tdr">报警值2:</td><td id=db_alarm2></td>
	    </tr>
		<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable"><i18n:message key="baseInfo.dbcs"/></td></tr>
	   		<tr>
	    	<td class="tdr" width="10%"><i18n:message key="baseInfo.yhbh"/></td><td id="mp_userno" width="10%"></td>
	    	<td class="tdr" width="10%"><i18n:message key="baseInfo.ptbb"/></td><td id="mp_pt_ratio" width="10%"></td>
	    	<td class="tdr" width="10%"><i18n:message key="baseInfo.ctbb"/></td><td id="mp_ct_ratio" width="10%"></td>
	    </tr>
	    <tr>
	   		<td class="tdr"></td><td></td>
	   		<td class="tdr">报警值1:</td><td id=mp_alarm1></td>
	   		<td class="tdr">报警值2:</td><td id=mp_alarm2></td>
	    </tr>
	    <tr>
	    	<td class="tdr" colspan=6 style="border: 0px;">
		 		<i18n:message key="baseInfo.czlx"/>&nbsp;<select style="width: 100px;" id="op_type">
	    		<option value=<%=com.kesd.comnt.ComntProtMsg.YFF_DY_SET_USERID%> ><i18n:message key="baseInfo.jmhh"/></option>
	    		<option value=<%=com.kesd.comnt.ComntProtMsg.YFF_DY_SET_PT%> ><i18n:message key="baseInfo.dbPT"/></option>
	    		<option value=<%=com.kesd.comnt.ComntProtMsg.YFF_DY_SET_CT%> ><i18n:message key="baseInfo.dbCT"/></option>
	    		<option value=<%=com.kesd.comnt.ComntProtMsg.YFF_DY_SET_ALARM1%> >报警值1</option>
	    		<option value=<%=com.kesd.comnt.ComntProtMsg.YFF_DY_SET_ALARM2%> >报警值2</option>
	    		</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<button id="btnCall" class="btn" disabled><i18n:message key="btn.dbcs"/></button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<button id="btnSet" class="btn" disabled><i18n:message key="baseInfo.shezhi"/></button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>		  		        		    				
	    </tr>
	</table>
	</body>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	
	<script type="text/javascript">
	var ComntUseropDef = {};
	ComntUseropDef.YFF_DYCOMM_CALLPARA = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYCOMM_CALLPARA%>;
	ComntUseropDef.YFF_DYCOMM_SETPARA = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYCOMM_SETPARA%>;
	var ComntProtMsg = {};
	ComntProtMsg.YFF_DY_CALL_PARA = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_CALL_PARA%>;
	ComntProtMsg.YFF_DY_SET_USERID = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_SET_USERID%>;
	ComntProtMsg.YFF_DY_SET_CT = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_SET_CT%>;
	ComntProtMsg.YFF_DY_SET_PT = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_SET_PT%>;
	ComntProtMsg.YFF_DY_SET_ALARM1 = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_SET_ALARM1%>;
	ComntProtMsg.YFF_DY_SET_ALARM2 = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_SET_ALARM2%>;
	</script>
	
</html>
