<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
<%@page import="com.kesd.comnt.ComntProtMsg"%>
<%@page import="com.kesd.comnt.ComntDef"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<i18n:bundle baseName="<%=SDDef.BUNDLENAME%>" id="bundle" locale="<%=WebConfig.app_locale%>" scope="application" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>主站结算补差</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="cache-control" content="no-cache">
	
	<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="index_theme" />
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
	
	<script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
	<script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid_srnd.js"></script>
	<script src="<%=basePath%>js/common/modalDialog.js"></script>
	<script src="<%=basePath%>js/common/def.js"></script>
	<script src="<%=basePath%>js/common/cookie.js"></script>
	<script src="<%=basePath%>js/spec/tool/gyjs.js"></script>
	<script src="<%=basePath%>js/spec/localbd/bdcalcu.js"></script>
	<script src="<%=basePath%>js/spec/tool/setCons.js"></script>
	<script  src="<%=basePath%>js/common/jsonString.js"></script>
	<script  src="<%=basePath%>js/validate.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script  src="<%=basePath%>js/common/number.js"></script>
	<script  src="<%=basePath%>js/common/DateFun.js"></script>
	
	<style type="text/css">
	
	fieldset{
		width:99%;
	}
	fieldset legend{
		font-size:12px; 
	}
	fieldset span{
		font-size:12px;
	}
	
	input {
		width:120px;
	}
	</style>
  </head>
  
  <body>
   <table cellpadding="0" cellspacing="0"  style="border: 0px solid blue;">
	  <tr>
		<td id="tdleft"  style="width:200px; text-align:center; font-size: 12px;">
		<div style="padding-top: 5px;">
		<button id="upload_bdye">上传SG186表底余额</button>
		</div>
		<div style="padding-top: 5px; padding-bottom: 5px;">
		抄表年月:
		<input type="text" id="cb_ym" readonly style="width:120px" onFocus="WdatePicker({dateFmt:'yyyy年MM月',maxDate:'%y-%M',isShowClear:'false'});" />
		</div>
		<div id=gridbox_cons style="border-bottom: 1px solid #93AFBA;width:100%;"></div>
		</td>
		<td style="background: url('<%=basePath%>/images/index/cont_3.gif') repeat-y;" width="10px;"></td>
		
		<td id="tdright">
		  <div id="div_rightmenu" style="overflow: auto;border: 0px solid red;">

			<fieldset id="fdset_cons" style="padding-top:2px;">
			<legend>导入信息</legend>
			<span id="spanConsInfo" >&nbsp;<br/>&nbsp;</span>
			</fieldset>
			<div>			
			 <table id="tabinfo" class="tabinfo" align="center">
		    	<tr><td colspan="6" style="height:5px; border:0px;"></td></tr>
		    	<tr>
			    	<td class="td_lable">基本信息</td>
			    	<td colspan=5 style="text-align: right;" ><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
			    </tr>
		        <tr>
			    	<td class="tdr" width="8%">客户编号:</td><td id="userno"  width="16%">&nbsp;</td>
			    	<td class="tdr" width="8%">客户名称:</td><td id="username" width="16%">&nbsp;</td>
			     	<td class="tdr" width="12%">客户状态:</td><td id="cus_state" width="12%">&nbsp;</td>
			    </tr>
			    <tr>
			    	<td class="tdr">联系电话:</td><td id="tel">&nbsp;</td>
			   		<td class="tdr">客户地址:</td><td colspan=3 id="useraddr">&nbsp;</td>
			    </tr>
			    <tr>
			    	<td class="tdr">费控单元:</td><td id="fee_unit">&nbsp;</td>
			    	<td class="tdr"></td><td>&nbsp;</td>
			    	<td class="tdr" width="12%"></td><td>&nbsp;</td>
			    </tr>
		   		<tr>
			    	<td class="tdr">终端型号:</td><td id="rtu_model">&nbsp;</td>
			    	<td class="tdr">生产厂家:</td><td id="factory">&nbsp;</td>
			     	<td class="tdr">倍率:</td><td id="blv">&nbsp;</td>
			    </tr>
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
			   		<td class="tdr">算费表底时间:</td><td id="sfbd_sj">&nbsp;</td>
			   		<td class="tdr">算费表底:</td><td colspan=3 id="sfbd">&nbsp;</td>
			    </tr>
			    <tr><td colspan="6" style="height:5px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>	    
			   	<tr><td class="td_lable"  style="border-bottom: 0"><i18n:message key="baseInfo.gdjl"/></td></tr>
			    </table>
			<table class="gridbox" align="center">
				  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
			</table>
		 
		<table id="tabinfo_je" class="tabinfo" align="center">
		    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
		   	<tr><td class="td_lable">缴费信息</td></tr>
		    <tr>
		    	<td class="tdr" width="8%">缴费金额(元):</td><td class="tdrn"  width="12%"><input id="jfje" readonly /></td>
		    	<td class="tdr" width="12%">追补金额(元):</td><td class="tdrn"  width="12%"><input id="zbje" readonly /></td>		  		        		    				
		    	<td class="tdr" width="12%">结算金额(元):</td><td class="tdrn"  width="12%"><input id="jsje" /></td>
		    </tr>
		    <tr>
		    	<td class="tdr">总金额(元):    </td><td class="tdrn"><input id="zje" name="zje" readonly /></td>
		    	<td class="tdr">报警金额1(元):</td><td class="tdrn"><input id="yffalarm_alarm1" /></td>
		    	<td class="tdr">报警金额2(元):</td><td class="tdrn"><input id="yffalarm_alarm2" /></td>
		    </tr>
		    <tr>
		    	<td class="tdr">购电次数:</td><td class="tdrn"><span id="buy_times"></span></td>
		    	<td class="tdr">当前余额:</td><td class="tdrn"><span id="now_remain"></span></td>
		    	<td colspan="2" style="text-align: right;">
		    	<button id="btnRemain" 	class="btn"  disabled="disabled">计算结算金额</button>&nbsp;&nbsp;&nbsp;&nbsp;
		    	<button id="btnPay"  	class="btn"  disabled="disabled">结算</button>
		    </td></tr>
		</table>
		
		<table id="tabinfo_bd" class="tabinfo" align="center" style="display: none;">
		<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" style="width: 120px;">缴费信息</td></tr>
	    <tr>
	    	<td class="tdr" width="12%">缴费金额(元):</td><td class="tdrn"><input id="jfje_bd" disabled /></td>
	    	<td class="tdr" width="12%">追补金额(元):</td><td class="tdrn"><input id="zbje_bd" disabled /></td>		  		        		    				
	    	<td class="tdr" width="12%">结算金额(元):</td><td class="tdrn"><input id="jsje_bd" /></td>
	    </tr>
	    <tr>
	    	<td class="tdr">报警止码:</td><td class="tdrn"><input id="alarm_code" /></td>
	    	<td class="tdr">总金额(元):</td><td class="tdrn"><span id="zje_bd"></span></td>
	    	<td class="tdr">购电次数:</td><td class="tdrn"><span id="buy_times_bd"></span></td>
	    </tr>
	    <tr>
	    	<td class="tdr">购电量:</td><td class="tdrn"><span id="buy_dl"></span></td>
	    	<td class="tdr">表码差:</td><td class="tdrn"><span id="pay_bmc"></span></td>
	    	<td class="tdr">断电止码:</td><td class="tdrn"><span id="shutdown_bd"></span></td>
	    </tr>
    	<tr>
    	<td class="tdr">断电表底:</td><td class="tdrn"><span id="ddbd"></span></td>
    	<td class="tdr">当前表底:</td><td class="tdrn"><span id="dqbd"></span></td>
    	<td colspan="3" style="text-align: right;">
		<button id="btnRemain_bd" 	class="btn"  disabled="disabled">计算结算金额</button>&nbsp;&nbsp;&nbsp;&nbsp;
		<button id="btnPay_bd"  	class="btn"  disabled="disabled">结算</button>
		</td></tr>
		</table>
			<input type="hidden" id="rtu_id"/>
			<input type="hidden" id="zjg_id"/>
			<input type="hidden" id="cons_no"/>
			<input type="hidden" id="pay_type"/>
			<input type="hidden" id="feeproj_id"/>
			<input type="hidden" id="yffalarm_id"/>
			<input type="hidden" id="buy_times"/>
			
			<jsp:include page="../../inc/jsdef.jsp"></jsp:include>
			
			</div>
			
		  </div>
		</td>
	  </tr>
  </table>
	
</body>
<script type="text/javascript">

	$("#tdright").width($(window).width()-200-10);
	$("#div_rightmenu").height($(window).height());
	var contH = $(window).height()-25;
	
	$("#gridbox_cons").height($(window).height()-80);			
	var	mygridCons = new dhtmlXGridObject('gridbox_cons');
	
	$("#gridbox").height($(window).height()-$("#tabinfo").height()-$("#tabinfo_je").height()- 90 - $("fdset_cons").height());		
	var	mygridYff = new dhtmlXGridObject('gridbox');
	
	var ComntUseropDef = {};
	ComntUseropDef.YFF_GYOPER_REJS 		 		= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_REJS%>;
	ComntUseropDef.YFF_GYOPER_GETJSBCREMAIN  	= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_GETJSBCREMAIN%>;
	ComntUseropDef.YFF_GYOPER_GPARASTATE 		= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_GPARASTATE%>;
	ComntUseropDef.YFF_READDATA					= <%=com.kesd.comntpara.ComntUseropDef.YFF_READDATA%>;
	ComntUseropDef.YFF_GYCOMM_CALLPARA			= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYCOMM_CALLPARA%>;
	ComntUseropDef.YFF_GYCOMM_PAY				= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYCOMM_PAY%>;
	
	var ComntProtMsg = {};
	ComntProtMsg.YFF_CALL_REAL_ZBD 		= <%=ComntProtMsg.YFF_CALL_REAL_ZBD%>;
	ComntProtMsg.YFF_CALL_REAL_FBD 		= <%=ComntProtMsg.YFF_CALL_REAL_FBD%>;
	ComntProtMsg.YFF_GY_CALL_PARAREMAIN = <%=ComntProtMsg.YFF_GY_CALL_PARAREMAIN%>;
	ComntProtMsg.YFF_CALL_GY_REMAIN 	= <%=ComntProtMsg.YFF_CALL_GY_REMAIN%>;
	
	var ComntDef = {};
	ComntDef.YD_PROTOCAL_FKGD05 	= <%=ComntDef.YD_PROTOCAL_FKGD05%>;
	ComntDef.YD_PROTOCAL_GD376 		= <%=ComntDef.YD_PROTOCAL_GD376%>;
	ComntDef.YD_PROTOCAL_GD376_2013 = <%=ComntDef.YD_PROTOCAL_GD376_2013%>;
	ComntDef.YD_PROTOCAL_KEDYH 		= <%=ComntDef.YD_PROTOCAL_KEDYH%>;
	
	var SDDef = {};
	SDDef.SUCCESS = '<%=SDDef.SUCCESS%>';
	<%
	Date date = new Date();
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
	out.println("var _today = '" + sdf.format(date) + "';");
	%>
	
</script>
</html>
  