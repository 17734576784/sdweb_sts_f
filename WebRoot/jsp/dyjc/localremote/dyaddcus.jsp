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
			.tabinfo input,select{
				width: 99%;
			}
			input,select{
				font-size: 12px;				
			}			  
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>		
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/common/dateFormat.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/sg186.js"></script>
		<script  src="<%=basePath%>js/dyjc/localremote/dyaddcus.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/dyjc/mis/mis.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>		
		
	</head>
	<body>
	<table id="tabinfo" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
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
	    	<td class="tdr">结算客户名:</td><td id="jsyhm">&nbsp;</td>
	    	<td class="tdr" id="khfl">客户分类:</td><td id="yhfl">&nbsp;</td>
	    	<td class="tdr"></td><td>&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.bh"/></td><td id="esamno">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.yffblx"/></td><td id="yffmeter_type_desc">&nbsp;</td>
	    	<td class="tdr"><i18n:message key="baseInfo.sccj"/></td><td id="factory">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.bl"/></td><td id="blv">&nbsp;</td>
	    	<td class="tdr">接线方式:</td><td id="wiring_mode">&nbsp;</td>
	    	<td>&nbsp;</td><td>&nbsp;</td>
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

    	<tr><td class="td_lable"><i18n:message key="baseInfo.jfxx"/></td></tr>
	    <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.yzje"/><i18n:message key="baseInfo.yuan"/></td>
	    	<td>
	    		<select id="yzje" name="yzje">
			    	<%
					List list = WebConfig.getYzje();
					for (int i = 0; i < list.size(); i++) {
						String[] tmp = (String[])list.get(i);			
						out.println("<option value='"+tmp[0]+"'>"+tmp[1]+"</option>");
					}
					%>
				</select>
	    	</td>		  		        		    				
	    	<td class="tdr">说明:</td><td colspan=3>在电能表开户前预置在电能表内的可使用的用电金额，开户时售电系统自动扣除此部分金额。</td>
	    	
	    </tr>
	     <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.jfje"/><i18n:message key="baseInfo.yuan"/></td><td class="tdrn"><input id=jfje name=jfje disabled="disabled"/></td>		  		        		    				
	    	<td class="tdr">总金额(元):</td><td class="tdrn"><span id="zongje" style="text-align: left;"></span></td>
	    	<td class="tdr">旧表基础电量:</td><td  class="tdrn"><input id="jt_total_zbdl" disabled="disabled"/></td>
	    </tr>
	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	<td colspan="6" style="text-align: right;border: 0">
    		<input type="checkbox" name="chkRemote" style="width: 25px;" checked disabled/><i18n:message key="baseInfo.chk.yccz"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 	<button id="btnReadMeter"  class="btn" disabled="disabled"><i18n:message key="btn.ycdb"/></button> &nbsp;&nbsp;&nbsp;&nbsp; 
	    	<button id="btnNew"  class="btn"  disabled="disabled"><i18n:message key="idxhref.kaihu"/></button> &nbsp;&nbsp;&nbsp;&nbsp; 
	    	<button id="btnPrt"  class="btn" disabled="disabled"><i18n:message key="btn.dayin"/></button>	&nbsp;&nbsp;&nbsp;&nbsp; 
	    </td></tr>
	</table>
	
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="feeproj_id"/>
	<input type="hidden" id="yffalarm_id"/>
	<jsp:include page="../../inc/jsdef.jsp" />
	</body>
	<script type="text/javascript">
	var ComntUseropDef = {};
	ComntUseropDef.YFF_DYCOMM_ADDRES = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYCOMM_ADDRES%>;
	ComntUseropDef.YFF_DYOPER_ADDRES= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_ADDRES%>;
	ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER%>;
	ComntUseropDef.YFF_DYOPER_MIS_PAY 		 = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_PAY%>;
	
	
	var sel_user_info = "<i18n:message key="baseInfo.sel_user_info"/>";
	var khcg = "<i18n:message key="baseInfo.khcg"/>";
	var khsb = "<i18n:message key="baseInfo.khsb"/>";
	var yzje = "<i18n:message key="baseInfo.yzje"/>";
	var yzje = "<i18n:message key="baseInfo.yzje"/>";
	var jfje = "<i18n:message key="baseInfo.jfje"/>";
	var zje = "<i18n:message key="baseInfo.zje"/>";
	
	var SDDef = {};
	SDDef.YFF_OPTYPE_ADDRES = <%=SDDef.YFF_OPTYPE_ADDRES%>;
	SDDef.YFF_FEETYPE_JTFL  = <%=SDDef.YFF_FEETYPE_JTFL%> //阶梯费率             3
	SDDef.YFF_FEETYPE_MIXJT = <%=SDDef.YFF_FEETYPE_MIXJT%>//混合阶梯费率 4
	</script>
</html>
