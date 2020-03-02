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
		
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
		
		<style type="text/css">
			input,select{
				font-size: 12px;
				width:100px;	
				height: 20px;			
			}	
					
		</style>
		
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>			
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/dateFormat.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/np/localcard/npchgmeter.js"></script>
		<script  src="<%=basePath%>js/np/dialog/search.js"></script>
		<script  src="<%=basePath%>js/np/dialog/sg186.js"></script>
		<script  src="<%=basePath%>js/np/tool/setCons.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/validate.js"></script>
		<script  src="<%=basePath%>js/np/mis/mis.js"></script>
	
	</head>
	<body>
	<div style="overflow: auto;width:100%;" id="div_dyhb">
	
	<table id="tabinfo1" class="tabinfo" align="center">
    	<jsp:include page="user_info.jsp"></jsp:include>
	</table>
	<table class="gridbox" align="center">
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%;"></div></td></tr>
	</table>
	<div id="divjf">
	<table  id="tabinfo2" class="tabinfo" align="center">
		<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" style="height:25px;">换表信息</td>
	    	<td colspan=5 style="padding-left:10px; border: 0px;">
	    	<button id="btnBDOld" class="btn"  disabled="disabled">旧表录入</button>&nbsp;&nbsp;
	    	<button id="btnBDNew" class="btn"  disabled="disabled">新表录入</button>
	    	</td></tr>
	    <tr>
	    	<td class="tdr" style="height: 25px;">旧表信息</td>
	    	<td id="bd_old" colspan="5"></td>
	    </tr>
	    <tr>
	    	<td class="tdr" style="height: 25px;">新表信息</td>
	    	<td id="bd_new" colspan="5"></td>
	    </tr>
	  	<tr>
	 	    <td class="tdr" width="10%">更换时间:</td><td width="10%" colspan='5' ><input type="text" style="width: 160px;" id=ghsj onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日 HH时mm分',maxDate:'%y-%M-%d %H:%m',isShowClear:'false'});"/></td>
<!--		<td class="tdr" width="10%">更换类型:</td><td width="10%"><select id="ghlx" style="width: 100%"><option value=0>更换电表</option><option value=1>更换CT</option></select></td>
	    	<td class="tdr" width="10%">更换电表:</td><td width="10%"><select id="ghdb" style="width: 100%"></select></td>-->

	    </tr>
	    <!-- 
	   	<tr id="tr_ct" style="display: none">
	   		<td class="tdr" id="ctfz_desc" style="height: 16px;">新CT分子:</td><td><input type="text" id="ctfz" style="width: 160px;" value=1 onkeyup="changeCT()"/></td>
	    	<td class="tdr" id="ctfm_desc">新CT分母:</td><td><input type="text" id="ctfm" value=1 onkeyup="changeCT()" style="width: 100%"/></td>
	  		<td class="tdr" id="ctbb_desc">新CT变比:</td><td class="tdr" id="ctbb">1.00</td>
	    </tr>
	     -->
	  	<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr><td class="td_lable">缴费信息</td></tr>
	    <tr>
		    <td class="tdr" width="10%" nowrap="nowrap">购电次数:</td><td class="tdrn" width="10%"><span id="buy_times"></span></td>
			<td class="tdr" width="10%" nowrap="nowrap">上次缴费金额(元):</td><td width="10%" class="tdrn"><span id="last_jfje" style="width: 100%;">&nbsp;</span></td>
		    <td class="tdr" width="10%" nowrap="nowrap">&nbsp;</td><td class="tdr" width="15%">&nbsp;</td>
	    </tr>
	    <tr>
		    <td class="tdr" width="10%" nowrap="nowrap">剩余金额(元):</td><td width="10%" class="tdrn"><input type="text" id=zbje_val style="width: 100%;" onkeyup="calWrtCard()" /></td>
	    	<td class="tdr" width="10%" nowrap="nowrap">缴费金额(元):</td><td width="10%" class="tdrn"><input type="text" id=jfje_val style="width: 100%;" onkeyup="calWrtCard()" /></td>
	    	<td class="tdr" width="10%" nowrap="nowrap">总金额(元):</td><td  class="tdrn"><span id="zje_val" style="width:95%"></span>&nbsp;</td>
	    </tr>
	  	<tr><td colspan="6" style="height:3px; border: 0px;"></td></tr>
		<tr>
    		<td colspan="6" style="text-align: right;border: 0">
    		<button id="cardinfo"  class="btn" >卡内信息</button> &nbsp;&nbsp;&nbsp;&nbsp; 
	    	<button id="metinfo"  class="btn" >表内信息</button> &nbsp;&nbsp;&nbsp;&nbsp;  
	    	<button id="btnChange"  class="btn"  disabled="disabled">更换</button> &nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnPrt"  class="btn"  disabled="disabled">打印</button> &nbsp;&nbsp;&nbsp;&nbsp;
	    </td></tr>
	</table>
	</div> 
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="feeproj_id"/>
	<input type="hidden" id="yffalarm_id"/>
	
	
	</div>
	</body>
	<jsp:include page="../../inc/jsdef.jsp" />
	<script type="text/javascript">
		$("#div_dyhb").height($(window).height());
 		$("#gridbox").height(150);
		var ComntUseropDef = {};
		ComntUseropDef.YFF_DYOPER_PAY = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_PAY%>;
		ComntUseropDef.YFF_DYOPER_GPARASTATE = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_GPARASTATE%>;
		ComntUseropDef.YFF_DYOPER_REVER = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_REVER%>;
		ComntUseropDef.YFF_DYOPER_CHANGEMETER = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_CHANGEMETER%>;
		
		var SDDef = {};
		SDDef.STR_OR	 = 	"<%=SDDef.STR_OR%>";
		SDDef.SUCCESS	 = 	"<%=SDDef.SUCCESS%>";
		
		SDDef.CARD_OPTYPE_OPEN 	= <%=SDDef.CARD_OPTYPE_OPEN%>;
		SDDef.YFF_OPTYPE_PAY	= <%=SDDef.YFF_OPTYPE_PAY%>;
		
		SDDef.NPCARD_OPTYPE_INITPARA	= <%=SDDef.NPCARD_OPTYPE_INITPARA%>;	//不缴费 仅初始化参数
		SDDef.NPCARD_OPTYPE_OPEN		= <%=SDDef.NPCARD_OPTYPE_OPEN%>;		//卡类型 开户
		SDDef.NPCARD_OPTYPE_BUY			= <%=SDDef.NPCARD_OPTYPE_BUY%>;			//买电充值
		SDDef.NPCARD_OPTYPE_REVER		= <%=SDDef.NPCARD_OPTYPE_REVER%>;		//冲正
		
		var yff_grid_title ='<i18n:message key="baseInfo.yff.grid_title"/>';
		var sel_user_info = '<i18n:message key="baseInfo.sel_user_info"/>';
		var khcg = "<i18n:message key="baseInfo.khcg"/>";
		var khsb = "<i18n:message key="baseInfo.khsb"/>";
		
		var zbje = "<i18n:message key="baseInfo.zbje"/>";
		var yzje = "<i18n:message key="baseInfo.yzje"/>";
		var jsje = "<i18n:message key="baseInfo.jsje"/>";
		var jfje = "<i18n:message key="baseInfo.jfje"/>";
		var zje = "<i18n:message key="baseInfo.zje"/>";
	</script>
</html>
