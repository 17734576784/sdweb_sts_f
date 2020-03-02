<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
<%@page import="com.kesd.comntpara.ComntUseropDef"%>
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
			.tabinfo input,select{
				width: 150px;
			}
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/dyjc/main/dyaddcus.js"></script>
		<script  src="<%=basePath%>js/dyjc/mis/mis.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>	
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>
		
	</head>
	<body>
	<table id="tabinfo" class="tabinfo" align="center">
		<jsp:include page="user_info.jsp"></jsp:include>
  	    <tr><td class="td_lable" style="height:25px;"><i18n:message key="baseInfo.bdxx"/></td>
	    	<td colspan=5 style="padding-left:10px; border: 0px;">
	    	<button id="btnImportBD" class="btn"><i18n:message key="btn.luru"/></button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td></tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.bdVal"/></td>
	    	<td colspan="5" id="td_bdinf" ></td>
	    </tr>
	    
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	
    	<tr><td class="td_lable"><i18n:message key="baseInfo.jfxx"/></td></tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.yzje"/><i18n:message key="baseInfo.yuan"/></td>
	    	<td ><select id="yzje" name="yzje">
		    	<%
		    	
				List list = WebConfig.getYzje();
				
				for (int i = 0; i < list.size(); i++) {
					String[] tmp = (String[])list.get(i);			
					out.println("<option value='"+tmp[0]+"'>"+tmp[1]+"</option>");
				}
				%>
				</select></td>		  		        		    				
	    	<td class="tdr">说明:</td><td colspan=3>在电能表开户前预置在电能表内的可使用的用电金额，开户时售电系统自动扣除此部分金额。</td>
	    
	    </tr>
	    <tr>
	    	<td class="tdr">缴费金额(元):</td><td class="tdrn"><input id=jfje /></td>		  		        		    				
	    	<td class="tdr">结算金额(元):</td><td class="tdrn"><input id=jsje /></td>
	    	<td class="tdr">总金额(元):</td><td class="tdrn"><span id="zje""></span></td>
	    </tr>
	    <tr>
	    	<td class="tdr">旧表基础电量:</td><td  class="tdrn" colspan=5><input id="jt_total_zbdl" name=jcdl/></td>
	    
	    </tr>
	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	<td colspan="6" style="text-align: right;border: 0">
	    	<button id="btnNew"  class="btn"  disabled="disabled"><i18n:message key="idxhref.kaihu"/></button> &nbsp;&nbsp;&nbsp;&nbsp; 
	    	<button id="btnPrt"  class="btn" disabled="disabled"><i18n:message key="btn.dayin"/></button>	&nbsp;&nbsp;&nbsp;&nbsp; 
	    </td></tr>
	</table>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="feeproj_id"/>
	<input type="hidden" id="yffalarm_id"/>
	<jsp:include page="../../inc/jsdef.jsp"></jsp:include>
	
	<script type="text/javascript">
	$("#_sfbdxx").hide();
	$("#_title3").hide();
	var ComntUseropDef = {};
	ComntUseropDef.YFF_DYOPER_ADDRES = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_ADDRES%>;
	ComntUseropDef.YFF_DYOPER_MIS_PAY = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_PAY%>;
	ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER%>;
	var SDDef = {};
	SDDef.YFF_OPTYPE_ADDRES = <%=SDDef.YFF_OPTYPE_ADDRES%>;
	SDDef.YFF_FEETYPE_JTFL 	= <%=SDDef.YFF_FEETYPE_JTFL%>;
	SDDef.YFF_FEETYPE_MIXJT = <%=SDDef.YFF_FEETYPE_MIXJT%>;
	
	var sel_user_info = "<i18n:message key="baseInfo.sel_user_info"/>";
	var khcg = "<i18n:message key="baseInfo.khcg"/>";
	var khsb = "<i18n:message key="baseInfo.khsb"/>";
	var yzje = "<i18n:message key="baseInfo.yzje"/>";
	var yzje = "<i18n:message key="baseInfo.yzje"/>";
	var jfje = "<i18n:message key="baseInfo.jfje"/>";
	var zje = "<i18n:message key="baseInfo.zje"/>";
	</script>
	</body>
</html>
