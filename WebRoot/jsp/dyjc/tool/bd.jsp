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
			input{
				font-size: 12px;
				width: 100px;
				text-align:left;
			}	
			.tdrn{
				font-size: 14px;
				text-align:right;
				font-family: sans-serif;
			}
			.tdrn input{
				font-size: 14px;
				font-weight: bold;
				text-align:right;
				/*font-family: sans-serif;*/
			}		
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>					
		<script  src="<%=basePath%>js/dyjc/tool/bd.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
	</head>
	<body>
	<table id="tabinfo1" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:5px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;"><i18n:message key="baseInfo.jbxx"/></td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
	    	<button id="btnSearch" class="btn" ><i18n:message key="btn.jiansuo"/></button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>
	    	<td style="border:0px; text-align: right;" ><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    </tr>
   		<tr>
	    	<td class="tdr" style="width: 10%"><i18n:message key="baseInfo.yhbh"/></td><td id="userno" style="width: 10%">&nbsp;</td>
	    	<td class="tdr" style="width: 10%"><i18n:message key="baseInfo.yhmc"/></td><td id="username" style="width: 15%">&nbsp;</td>
	     	<td class="tdr" style="width: 10%"><i18n:message key="baseInfo.yhzt"/></td><td id="cus_state" style="width: 10%">&nbsp;</td>
	    </tr>
	   <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.lxdh"/></td><td id="tel">&nbsp;</td>
	   		<td class="tdr"><i18n:message key="baseInfo.yhdz"/></td><td colspan=3 id="useraddr">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.bh"/></td><td id="esamno">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.yffblx"/></td><td id="yffmeter_type_desc">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.sccj"/></td><td id="factory">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.bl"/></td><td id="blv">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.jxfs"/></td><td id="wiring_mode">&nbsp;</td>
	    	<td>&nbsp;</td><td>&nbsp;</td>
	     </tr>
	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr><td class="td_lable"><i18n:message key="baseInfo.fkxx"/></td></tr>
	   <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.jiffs"/></td><td id="cacl_type_desc">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.fkfs"/></td><td id="feectrl_type">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.jffs"/></td><td id="pay_type_desc">&nbsp;</td>    	
	    </tr>
	   	<tr>
	    	<td class="tdr"><i18n:message key="baseInfo.flfa"/></td><td id="feeproj_desc">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.fams"/></td><td colspan=3 id="feeproj_detail">&nbsp;</td>
	   	</tr>
	   	<tr>
	   		<td class="tdr"><i18n:message key="baseInfo.bjfa"/></td><td id="yffalarm_desc">&nbsp;</td>
	   		<td class="tdr"><i18n:message key="baseInfo.fams"/></td><td colspan=3 id="yffalarm_detail">&nbsp;</td>
	    </tr>
		<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable"><i18n:message key="baseInfo.kzxx"/></td></tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.czlx"/></td>
	    	<td><select style="width: 100px;" id="op_type">
	    	<option value=<%=com.kesd.comnt.ComntProtMsg.YFF_DY_CTRL_KEEPON%> ><i18n:message key="baseInfo.baodian"/></option>
	    	<option value=<%=com.kesd.comnt.ComntProtMsg.YFF_DY_CTRL_KEEPOFF%> ><i18n:message key="baseInfo.baodian"/><i18n:message key="baseInfo.jiechu"/></option>
	    	<option value=<%=com.kesd.comnt.ComntProtMsg.YFF_DY_CTRL_ALARMON%> ><i18n:message key="baseInfo.baojing"/></option>
	    	<option value=<%=com.kesd.comnt.ComntProtMsg.YFF_DY_CTRL_ALARMOFF%> ><i18n:message key="baseInfo.baojing"/><i18n:message key="baseInfo.jiechu"/></option>
	    	<option value=<%=com.kesd.comnt.ComntProtMsg.YFF_DY_CTRL_CUT%> ><i18n:message key="baseInfo.tiaozha"/></option>
	    	<option id = "hzyx" value=<%=com.kesd.comnt.ComntProtMsg.YFF_DY_CTRL_ON%> ><i18n:message key="baseInfo.hezha"/></option>
	    	</select></td>
	    	<td class="tdr"><i18n:message key="baseInfo.mybb"/></td>
	        <td id="key_version"></td>
	        <td align = "right"><input type="checkbox" id="chkzdhz" style="width: 25px;" disabled /><label for="chkzdhz"><i18n:message key="baseInfo.zdhz"/></label></td> 		        		    				
	        <td></td>
	    </tr>
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
	   <tr>
	   <td  colspan=6  style="text-align = right;border: 0px;" >
	   <button id="btnLJHZ" class="btn"  style="display : none;"><i18n:message key="btn.zjhz"/></button>&nbsp;&nbsp;&nbsp;
	   <button id="btnCZ" class="btn" disabled><i18n:message key="btn.caozuo"/></button>&nbsp;&nbsp;&nbsp;
	   </td>
	   </tr>
	   			  		        		    				
	   
	</table>
	
	</body>
	<input type="hidden" id="rtu_id" />
	<input type="hidden" id="mp_id" />
	<input type="hidden" id="pay_type" />
      

	<script type="text/javascript">
	var hezha="<i18n:message key='baseInfo.hezha'/>"
	var hzyx="<i18n:message key='baseInfo.hzyx'/>"
	var ComntUseropDef = {};
	ComntUseropDef.YFF_DYCOMM_CTRL    = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYCOMM_CTRL%>;
	ComntUseropDef.YFF_DYCOMM_PAY     = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYCOMM_PAY%>;//低压通信缴费
	ComntUseropDef.YFF_DYOPER_PROTECT = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_PROTECT%>;//低压操作保电
	var ComntProtMsg = {};
	ComntProtMsg.YFF_DY_CTRL_CUT	  = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_CTRL_CUT%>;
	ComntProtMsg.YFF_DY_CTRL_ON       = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_CTRL_ON%>;
	ComntProtMsg.YFF_DY_CTRL_KEEPON   = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_CTRL_KEEPON%>;
	ComntProtMsg.YFF_DY_CTRL_KEEPOFF  = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_CTRL_KEEPOFF%>;
	ComntProtMsg.YFF_DY_CTRL_ALARMON  = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_CTRL_ALARMON%>;
	ComntProtMsg.YFF_DY_CTRL_ALARMOFF = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_CTRL_ALARMOFF%>;
	ComntProtMsg.YFF_DY_CTRL_ONDIRECT = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_CTRL_ONDIRECT%>;	
	</script>
</html>
