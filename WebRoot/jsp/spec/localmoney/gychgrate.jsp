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
				width: 100%;
			}
			input,select{
				font-size: 12px;
			}
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/spec/dialog/search.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
		<script  src="<%=basePath%>js/spec/tool/setCons.js"></script>
		<script  src="<%=basePath%>js/spec/localmoney/gychgrate.js"></script>
		<script  src="<%=basePath%>js/common/DateFun.js"></script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	</head>
	<body>
	<div style="overflow: auto;width:100%;" id="div_ggfl">
	<table id="tabinfo" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;">基本信息</td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
	    	<button id="btnSearch" class="btn" >检索</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>
	    	<td style="border:0px; text-align: right;" ><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    	
	    </tr>
    	<tr>
	    	<td class="tdr" width="140">客户编号:</td><td id="userno"  width="17%">&nbsp;</td>
	    	<td class="tdr" width="12%">客户名称:</td><td id="username" width="20%">&nbsp;</td>
	     	<td class="tdr" width="12%">客户状态:</td><td id="cus_state">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">联系电话:</td><td id="tel">&nbsp;</td>
	   		<td class="tdr">客户地址:</td><td colspan=3 id="useraddr">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">费控单元:</td><td id="fee_unit">&nbsp;</td>
	    	<td class="tdr">&nbsp;</td><td>&nbsp;</td>
	    	<td class="tdr">&nbsp;</td><td>&nbsp;</td>
	    </tr>
	    
    	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr><td class="td_lable">终端信息</td></tr>
   		<tr>
	    	<td class="tdr" >终端型号:</td><td id="rtu_model" >&nbsp;</td>
	    	<td class="tdr" >生产厂家:</td><td id="factory"   >&nbsp;</td>
	     	<td class="tdr" >倍率:</td>   <td id="blv"      >&nbsp;</td>
	    </tr>
	    
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable">费控信息</td></tr>
	   <tr>
	    	<td class="tdr">计费方式:</td><td id="cacl_type_desc">&nbsp;</td>
	    	<td class="tdr">费控方式:</td><td id="feectrl_type">&nbsp;</td>
	    	<td class="tdr">缴费方式:</td><td id="pay_type_desc">&nbsp;</td>
	    </tr>
	   	<tr>
	    	<td class="tdr">费率方案:</td><td id="feeproj_desc">&nbsp;</td>
	    	<td class="tdr">方案描述:</td><td colspan=3 id="feeproj_detail">&nbsp;</td>
	   	</tr>
	   	<tr>
	   		<td class="tdr">报警方案:</td><td id="yffalarm_desc">&nbsp;</td>
	   		<td class="tdr">方案描述:</td><td colspan=3 id="yffalarm_detail">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">原基本费:</td><td id="pay_add1">&nbsp;</td>
	    	<td class="tdr">附加值2:</td><td id="pay_add2">&nbsp;</td>
	    	<td class="tdr">附加值3:</td><td id="pay_add3">&nbsp;</td>
	    </tr>
		
	    <!--终端费率/基本费-->
		<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" >终端费率/基本费</td></tr>
	    <tr>	    	  		        		    				
	    	<td class="tdr">终端基本费:</td><td id="zdjbf" /></td><td class="tdr">终端费率:</td><td  colspan="3" id="zdfl" ></td>		
	    </tr> 
		<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
        <tr><td class="td_lable" >新费率/基本费</td></tr>
        <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.qhsj"/></td><td ><input id=qhsj style="width:160px;" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日 HH时mm分',maxDate:'%y-%M-%d %H:%m',isShowClear:'false'});"/></td>		  		        		    				
	    	<td class="tdr">更改类型:</td>
	    	<td colspan=3 >
	    	   <select id="gglx" style="width:160px;">
			       <option value="flcs">费率参数</option>
			       <option value="jbf">基本费</option>
	    	   </select> 
	    	</td>	
	    </tr>
		<tr>
	    	<td class="tdr">新费率方案:</td><td ><select id="flfa" style="width:160px;"></select> </td>		  		        		    				
	    	<td class="tdr"><i18n:message key="baseInfo.fams"/></td><td colspan=3 id=fams_n></td>
	    	
	    </tr>
	    <tr>
	    	<td class="tdr">更换电表:</td >
	    	<td>
	    	     <select id="ghdb" style="width:160px;"  >
	    	     </select> 
	    	</td>	
	    	<td class="tdr">新基本费:</td ><td colspan=3 ><input id="xjbf" name="xjbf"  style="text-align: left; width: 95%;" disabled="disabled"/></td>	  		        		    				
	    </tr>
	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    	<td colspan="6" style="text-align: right;border: 0">
	    	<button id="btnZC"  	class="btn" disabled="disabled">召测</button> &nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnGG"  	class="btn"  disabled="disabled">更改</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnSave" class="btn"  disabled="disabled">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    </td></tr>
	</table>
	</div>
	<input type="hidden" id="rtu_id" />
	<input type="hidden" id="zjg_id" />
	<input type="hidden" value="1" id="mp_id" />
	<input type="hidden" id="pay_type" />
	<input type="hidden" id="feeproj_id" />
	<input type="hidden" id="yffalarm_id" />
	<input type="hidden" id="buy_dl"/>
	<input type="hidden" id="pay_bmc"/>
	<input type="hidden" id="buy_times"/>
	<input type="hidden" id="shutdown_bd" />
	<input type="hidden" id="feeproj_reteval"/>
	<input type="hidden" id="zbd" /><input type="hidden" id="jbd" />
	<input type="hidden" id="fbd" /><input type="hidden" id="pbd" /><input type="hidden" id="gbd" />
	
	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
	
	$("#gridbox").height($(window).height()-$("#tabinfo").height()-$("#tabinfo2").height() - 10);		
	
	var ComntUseropDef = {};
	ComntUseropDef.YFF_GYCOMM_CALLPARA  = <%=com.kesd.comntpara.ComntUseropDef.YFF_GYCOMM_CALLPARA%>;
	ComntUseropDef.YFF_GYCOMM_SETPARA   = <%=com.kesd.comntpara.ComntUseropDef.YFF_GYCOMM_SETPARA %>;//高压通讯设参数
	ComntUseropDef.YFF_GYOPER_CHANGPAYADD=<%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_CHANGPAYADD%>//高压操作-换基本费
	ComntUseropDef.YFF_GYOPER_CHANGERATE= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_CHANGERATE%>//高压  操作-换电价
		
	var ComntProtMsg = {};
	//376/05召测设置费率    大用户召测设置费率及附加费
	ComntProtMsg.YFF_GY_CALL_FEE = <%=com.kesd.comnt.ComntProtMsg.YFF_GY_CALL_FEE%>
	ComntProtMsg.YFF_GY_SET_FEI  = <%=com.kesd.comnt.ComntProtMsg.YFF_GY_SET_FEI %>//设置费率
	
	//376/05召测设置附加费
	ComntProtMsg.YFF_GY_CALL_APPEND  = <%=com.kesd.comnt.ComntProtMsg.YFF_GY_CALL_APPEND %>//召测基本费
	ComntProtMsg.YFF_GY_SET_APPEND  = <%=com.kesd.comnt.ComntProtMsg.YFF_GY_SET_APPEND %>//召测基本费
	
	var ComntDef = {};
	ComntDef.YD_PROTOCAL_FKGD05				= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_FKGD05%>;
	ComntDef.YD_PROTOCAL_GD376				= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_GD376%>;
	ComntDef.YD_PROTOCAL_GD376_2013			= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_GD376_2013%>;
	ComntDef.YD_PROTOCAL_KEDYH				= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_KEDYH%>;
	
	
	var SDDef		 = {};
	SDDef.STR_AND 						= "<%=SDDef.STR_AND%>";
	SDDef.STR_OR 						= "<%=SDDef.STR_OR%>";
	SDDef.YFF_FEETYPE_DFL				=  <%=SDDef.YFF_FEETYPE_DFL%>;
	SDDef.YFF_FEETYPE_FFL				=  <%=SDDef.YFF_FEETYPE_FFL%>;
	
	var sel_user_info = "<i18n:message key="baseInfo.sel_user_info"/>";
	var khcg = "<i18n:message key="baseInfo.khcg"/>";
	var khsb = "<i18n:message key="baseInfo.khsb"/>";
	var yzje = "<i18n:message key="baseInfo.yzje"/>";
	var yzje = "<i18n:message key="baseInfo.yzje"/>";
	var jfje = "<i18n:message key="baseInfo.jfje"/>";
	var zje  = "<i18n:message key="baseInfo.zje"/>";
	var yff_grid_title ='操作日期,操作员,缴费金额(元),结算金额(元),追补金额(元),总金额(元),报警值1,断电金额(元),购电次数,流水号,缴费方式,操作类型,&nbsp;';
	</script>
	</body>
</html>
