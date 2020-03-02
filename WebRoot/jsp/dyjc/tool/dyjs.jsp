<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
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
	
	<script src="<%=basePath%>js/dyjc/tool/dyjs.js"></script>
	<script src="<%=basePath%>js/dyjc/tool/setCons.js"></script>
	
	<script  src="<%=basePath%>js/common/jsonString.js"></script>
	<script  src="<%=basePath%>js/common/def.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script  src="<%=basePath%>js/common/number.js"></script>
	
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
		width:99%;
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

			<fieldset id="fdset_cons" style="padding-top:2px;" align="center">
			<legend>导入信息</legend>
			<span id="spanConsInfo" >&nbsp;<br/>&nbsp;</span>
			</fieldset>
			<div>			
			 <table id="tabinfo" class="tabinfo" align="center">
		    	<jsp:include page="user_info.jsp"></jsp:include>
			    </table>
			<table class="gridbox" align="center">
				  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
			</table> 
			<table id="tabinfo2" class="tabinfo" align="center">
			    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
		    	<tr><td class="td_lable">缴费信息</td></tr>
			    <tr>
			    	<td class="tdr" width="8%">缴费金额(元):</td><td class="tdrn"  width="12%"><input id="jfje" name="jfje" readonly /></td>
			    	<td class="tdr" width="12%">追补金额(元):</td><td class="tdrn"  width="12%"><input id="zbje" name="zbje" /></td>		  		        		    				
			    	<td class="tdr" width="12%">结算金额(元):</td><td class="tdrn"  width="12%"><input id="jsje" name="jsje" /></td>
			    </tr>
			    <tr>
			    	<td class="tdr">总金额(元):    </td><td class="tdrn"><input id="zje" name="zje" readonly /></td>
			    	<!--<td class="tdr">购电次数:</td><td class="tdrn"><span id="buy_times"></span></td>-->
			    	<td class="tdr">电表余额:</td><td class="tdrn"><span id="now_remain2"></span></td>
			    	<td class="tdr">当前余额:</td><td class="tdrn"><span id="now_remain"></span></td>
			    </tr>
			    <tr>
			    	<td colspan="6" style="text-align: right;">
			    	<button id="btnRemain" 	class="btn"  disabled="disabled">计算结算金额</button>&nbsp;&nbsp;&nbsp;&nbsp;
			    	<button id="btnPay"  	class="btn"  disabled="disabled">结算</button>
			    </td></tr>
			</table>
			</div>
		  </div>
		</td>
	  </tr>
  </table>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="cons_no"/>
	<input type="hidden" id="cons_name"/>
	<input type="hidden" id="pay_type"/>
</body>
<jsp:include page="../../inc/jsdef.jsp"></jsp:include>
<script type="text/javascript">
	$("#btnSearch").hide();
	$("#mis_jsxx").hide();
	$("#tdright").width($(window).width()-200-10);
	$("#div_rightmenu").height($(window).height());
	var contH = $(window).height()-25;
	
	$("#gridbox_cons").height($(window).height()-80);			
	var	mygridCons = new dhtmlXGridObject('gridbox_cons');
	
	$("#gridbox").height($(window).height()-$("#tabinfo").height()-$("#tabinfo2").height()- 60 - $("fdset_cons").height());		
	var	mygridYff = new dhtmlXGridObject('gridbox');
	
	var ComntUseropDef = {};
	ComntUseropDef.YFF_DYOPER_JSBC 		 		= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_JSBC%>;
	ComntUseropDef.YFF_DYOPER_GETREMAIN  	= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_GETREMAIN%>;
	ComntUseropDef.YFF_DYOPER_GPARASTATE 		= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_GPARASTATE%>;
	
	var SDDef = {};
	SDDef.SUCCESS 				= '<%=SDDef.SUCCESS%>';
	SDDef.YFF_FEECTRL_TYPE_RTU  = '<%=SDDef.YFF_FEECTRL_TYPE_RTU%>';
	
	SDDef.YFF_CACL_TYPE_MONEY   = '<%=SDDef.YFF_CACL_TYPE_MONEY%>';
	
	
</script>
</html>
  