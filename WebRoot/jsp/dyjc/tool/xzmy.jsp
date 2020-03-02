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
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/xzmy.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>	
		<script  src="<%=basePath%>js/common/loading.js"></script>
		
	</head>
	<body>
	<table id="tabinfo" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;"><i18n:message key="baseInfo.jbxx"/></td>
	    	<td colspan=5 style="padding-left:10px; border: 0px;">&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnSearch" class="btn" ><i18n:message key="btn.jiansuo"/></button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>
	    </tr>
      	<tr>
	    	<td class="tdr" ><i18n:message key="baseInfo.yhbh"/></td><td id="userno" ></td>
	    	<td class="tdr" ><i18n:message key="baseInfo.yhmc"/></td><td colspan=3 id="username" ></td>
	     		    	
	    </tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.lxdh"/></td><td id="tel"></td>
	   		<td class="tdr"><i18n:message key="baseInfo.yhdz"/></td><td colspan=3 id="useraddr"></td>
	    </tr>
	    
	    <tr><td colspan="6" style="height:10p`x; border: 0px;"></td>	</tr>
    	<tr><td class="td_lable"><i18n:message key="baseInfo.dbxx"/></td></tr>
    	<tr>
	    	<td class="tdr" style="width: 10%"><i18n:message key="baseInfo.bh"/></td><td id="esamno" style="width: 10%"></td>
	    	<td class="tdr" style="width: 10%"><i18n:message key="baseInfo.ptbb"/></td><td id="pt_ratio" style="width: 10%"></td>
	    	<td class="tdr" style="width: 10%"><i18n:message key="baseInfo.ctbb"/></td><td id="ct_ratio" style="width: 10%"></td>
	    </tr>
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
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
	    <tr id="type1">
	    	<td class="tdr"><i18n:message key="baseInfo.mybb"/></td><td class="tdrn"><input id="key_version"></td>
	    	<td class="tdr"><i18n:message key="baseInfo.mylx"/></td>
	    	<td><select style="width: 100px;" id="keyType">
	    	<option value=<%=com.kesd.comnt.ComntEsam.YFF_DY_KEY_AUTHEN%> >身份认证</option>
	    	<option value=<%=com.kesd.comnt.ComntEsam.YFF_DY_KEY_PARA%> >参数更新</option>
	    	<option value=<%=com.kesd.comnt.ComntEsam.YFF_DY_KEY_REMOTE%> >远程控制</option></select></td>
	    	<td colspan=2><input type="checkbox" id="chkcsh" style="width: 25px;"/><label for="chkcsh">初始化</label> </td>		  		        		    				
	    </tr>
	    <!-- 20131021新增规约表下装密钥    begin-->
	    <tr id="type2" style="display:none">
	    	<td class="tdr">密钥版本:</td>
	    	<td class="tdrn" id="keybb">
	    		<input id="key_version2">
	    	</td>	
	    	<td class="tdr">密钥条数:</td>
	    	<td>
	    		<select style="width: 100px;" id="keynum">
	    			<option value= "0">编号01~04</option>
	    			<option value= "4">编号05~08</option>
	    			<option value= "8">编号09~12</option>
	    			<option value= "12">编号13~16</option>
	    			<option value= "16">编号17~20</option>
	    		</select>
	    	</td>
	    	<td colspan=2></td>
	   	</tr>
	   	<!-- end -->
	   	<tr>
	    	<td class="tdr" colspan=6 style="border: 0px;">
	    		<button id="btnInit" class="btn" disabled disabled style='display:none'>钱包初始化</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<button id="btnCall" class="btn" disabled><i18n:message key="btn.ckmy"/></button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<button id="btnSet" class="btn" disabled><i18n:message key="btn.xzmy"/></button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<button id="btnClear" class="btn" disabled><i18n:message key="btn.qkmy"/></button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<button id="btnReverKey" class="btn" disabled style='display:none'>恢复密钥</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<button id="btnSaveKey" class="btn" disabled><i18n:message key="btn.baocun"/></button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>		  		        		    				
	    </tr>
	</table>
	</body>
<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	
	<script type="text/javascript">
	var ComntUseropDef = {};
	ComntUseropDef.YFF_DYCOMM_CALLPARA 		= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYCOMM_CALLPARA%>;
	ComntUseropDef.YFF_DYCOMM_CHGKEY 		= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYCOMM_CHGKEY%>;
	ComntUseropDef.YFF_DYOPER_UDPATESTATE   = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_UDPATESTATE%>;
	ComntUseropDef.YFF_DYCOMM_CHGKEY2 		= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYCOMM_CHGKEY2%>;
	ComntUseropDef.YFF_DYCOMM_ININT			= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYCOMM_ININT%>;
	
	var ComntProtMsg = {};
	ComntProtMsg.YFF_DY_CALL_STATE = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_CALL_STATE%>;
	ComntProtMsg.YFF_DY_SET_USERID = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_SET_USERID%>;
	ComntProtMsg.YFF_DY_SET_CT = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_SET_CT%>;
	ComntProtMsg.YFF_DY_SET_PT = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_SET_PT%>;
	ComntProtMsg.YFF_DY_SET_ALARM1 = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_SET_ALARM1%>;
	ComntProtMsg.YFF_DY_SET_ALARM2 = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_SET_ALARM2%>;
	var ComntKeyType = {};
	ComntKeyType.YFF_DY_KEY_REMOTE = <%= com.kesd.comnt.ComntEsam.YFF_DY_KEY_REMOTE%>;
	
	var SDDef = {};
	SDDef.YFF_DY_UPDATE_KEYVER	   = <%=SDDef.YFF_DY_UPDATE_KEYVER%>; 
	
	</script>
	
</html>
