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
			input,select{
				font-size: 12px;
			}
			.tabinfo input{
				width: 99%;
			}
		</style>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/zh/cont.css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/spec/tool/gdkxx.js"></script>
		<script  src="<%=basePath%>js/spec/tool/setCons.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/spec/dialog/search.js"></script>
	</head>
	<body>
		<div style="overflow: auto;width:100%;" id="div_dkxx">
		<table id="tabinfo1" class="tabinfo" align="center">
     	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;">基本信息</td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
	    	<button id="btnReadCard" class="btn">读卡</button>
	    	</td>
	    	<td style="border:0px; text-align: right;" ><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    </tr>
   		<tr>
	    	<td class="tdr" width="140">客户编号:</td><td id="userno"  width="23%">&nbsp;</td>
	    	<td class="tdr" width="12%">客户名称:</td><td id="username" width="12%">&nbsp;</td>
	     	<td class="tdr" width="12%">客户状态:</td><td id="cus_state">&nbsp;</td>
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
			<td class="tdr">卡表类型: </td><td id="cardmeter_type">&nbsp;</td>
	   		<td class="tdr">写卡户号: </td><td id="writecard_no">&nbsp;</td>
	   		<td class="tdr">ESAM表号:</td><td id="meter_id">&nbsp;</td>
		</tr>
		<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
		<tr><td class="td_lable">卡内用户信息</td></tr>
	   <table id="tabinfo2" class="tabinfo" align="center" >
		   <tr>
		    	<td class="tdr" width="140">客户编号:</td><td id="userno_01" width="23%">&nbsp;</td>
		    	<td class="tdr" width="12%">电表类型:</td><td id="metertype_01" width="12%">&nbsp;</td>
		    	<td class="tdr" width="12%">ESAM表号:</td><td id="esamno_01">&nbsp;</td>
		    </tr>
		   	<tr>
		    	<td class="tdr">费率类型:</td><td id="feetype_01">&nbsp;</td>
		    	<td class="tdr">上次缴费金额(元):</td><td colspan="3" id="scjfje_01">&nbsp;</td>
		   	</tr>
		   	<tr>
		    	<td class="tdr">电卡类型:</td><td id="card_type_01">&nbsp;</td>
		    	<td class="tdr">报警金额1(元):</td><td id="alarm1_01">&nbsp;</td>
		    	<td class="tdr">报警金额2(元):</td><td id="alarm2_01">&nbsp;</td>
		   	</tr>
		   	<tr>
		   		<td class="tdr">购电次数:</td><td id="buynum_01">&nbsp;</td>
		   		<td class="tdr">PT:</td><td id="pt_01">&nbsp;</td>
		   		<td class="tdr">CT: </td><td id="ct_01">&nbsp;</td>
		    </tr>
		    <tr>
		    	<td class="tdr">第一套费率:</td><td id="fee1_01">&nbsp;</td>
		    	<td class="tdr">第二套费率:</td><td colspan="3" id="fee2_01">&nbsp;</td>
		   	</tr>
		</table>
		<table id="tabinfo3" class="tabinfo" align="center">
		    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
		    <tr><td class="td_lable">卡内返写信息</td></tr>
		    <tr>
		    	<td class="tdr" width="140">返写标志:</td><td id="write_back_flag_01" width="23%">&nbsp;</td>
		    	<td class="tdr" width="12%">客户编号:</td><td id="userno_01_back" width="12%">&nbsp;</td>
		    	<td class="tdr" width="12%">ESAM表号:</td><td id="esamno_01_back">&nbsp;</td>
		    </tr>
		   	<tr>
		    	<td class="tdr">费率类型:</td><td id="feetype_01_back">&nbsp;</td>
		    	<td class="tdr">剩余金额(元):</td><td id="syje_01_back">&nbsp;</td>
		    	<td class="tdr">透支金额:</td><td id="tzje_01_back">&nbsp;</td>
		   	</tr>
		   	<tr>
		   		<td class="tdr">购电次数:</td><td id="buynum_01_back">&nbsp;</td>
		   		<td class="tdr">PT:</td><td id="pt_01_back">&nbsp;</td>
		   		<td class="tdr">CT: </td><td id="ct_01_back">&nbsp;</td>
		    </tr>
		    <tr>
		   		<td class="tdr">密钥版本:</td><td id="mybb_01_back">&nbsp;</td>
		   		<td class="tdr">密钥状态:</td><td id="myzt_01_back">&nbsp;</td>
		   		<td class="tdr">非法插卡次数: </td><td id="ffckcs_01_back">&nbsp;</td>
		    </tr>
		    <tr>
		    	<td class="tdr">返写日期:</td><td id="fxdata_01_back">&nbsp;</td>
		    	<td class="tdr">返写时间:</td><td colspan="3" id="fxtime_01_back">&nbsp;</td>
		   	</tr>
		</table>
		<table id="tabinfo4" class="tabinfo" align="center"  style="display: none"><!-- -->
		   	<tr>
		   		<td class="tdr" width="140">客户编号:</td><td id="userno_03" width="23%">&nbsp;</td>
		   		<td class="tdr" width="12%">电表类型:</td><td id="metertype_03" width="12%">&nbsp;</td>
		    	<td class="tdr" width="12%">倍率:</td><td id="bl_03">&nbsp;</td>
		   	</tr>
		   	<tr>
		    	<td class="tdr">电卡类型:</td><td id="card_type_03">&nbsp;</td>
		   		<td class="tdr">报警表码差1:</td><td id="alarm_1_03">&nbsp;</td>
		   		<td class="tdr">报警表码差2:</td><td id="alarm_2_03">&nbsp;</td>
		    </tr>
		    <tr>
		    	<td class="tdr">购电次数:</td><td id="buy_num_03">&nbsp;</td>
		   		<td class="tdr">购电表码差:</td><td id="buy_dl_03">&nbsp;</td>
		   		<td class="tdr">囤积限值:</td><td id="limit_val_03">&nbsp;</td>
		    </tr>
		</table>
		<table id="tabinfo5" class="tabinfo" align="center"  style="display: none">
		    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
		    <tr><td class="td_lable">卡内返写信息</td></tr>
		    <tr>
		    	<td class="tdr" width="140">返写标志:</td><td id="write_back_flag_03" width="23%">&nbsp;</td>
		    	<td class="tdr" width="12%">客户编号:</td><td id="userno_03_back" width="12%">&nbsp;</td>
		    	<td class="tdr" width="12%">倍率变比:</td><td id="bl_03_back">&nbsp;</td>
		    </tr>
		   	<tr>
		    	<td class="tdr">费率类型:</td><td id="feetype_03_back">&nbsp;</td>
		    	<td class="tdr">剩余量:</td><td id="sydl_03_back">&nbsp;</td>
		    	<td class="tdr">本次购电值:</td><td id="bcgdl_03_back">&nbsp;</td>
		   	</tr>
		   	<tr>
		    	<td class="tdr">脉冲常数:</td><td id="imp_03_back">&nbsp;</td>
		    	<td class="tdr">累计购电值:</td><td colspan="3" id="ljgdl_03_back">&nbsp;</td>
		   	</tr>
		   	<tr>
		   		<td class="tdr">购电次数:</td><td id="buynum_03_back">&nbsp;</td>
		   		<td class="tdr">报警表码差1:</td><td id="alarm1_03_back">&nbsp;</td>
		   		<td class="tdr">报警表码差2: </td><td id="alarm2_03_back">&nbsp;</td>
		    </tr>
		    <tr>
		    	<td class="tdr">返写日期:</td><td id="fxdata_03_back">&nbsp;</td>
		    	<td class="tdr">返写时间:</td><td colspan="3" id="fxtime_03_back">&nbsp;</td>
		   	</tr>
		</table>
	</table>
	
	<!-- 新增2013版智能卡 -->
	<table id="tabinfo6" class="tabinfo" align="center" style="display: none">
	   <tr>
	    	<td class="tdr" style="width: 140">客户编号:</td><td id="userno_02" style="width: 23%">&nbsp;</td>
	    	<td class="tdr" style="width: 12%">电表类型:</td><td id="metertype_02" style="width: 12%">&nbsp;</td>
	    	<td class="tdr" style="width: 12%">ESAM表号:</td><td id="esamno_02">&nbsp;</td>
	    </tr>
	   	<tr>
	    	<td class="tdr">电卡类型:</td><td id="card_type_02">&nbsp;</td>
	    	<td class="tdr">上次缴费金额(元):</td><td id="scjfje_02">&nbsp;</td>
	    	<td class="tdr">购电次数:</td><td id="buynum_02">&nbsp;</td>
	   	</tr>
	   	<tr>
	    	<td class="tdr">报警金额1(元):</td><td id="alarm1_02">&nbsp;</td>
	    	<td class="tdr">报警金额2(元):</td><td id="alarm2_02">&nbsp;</td>
	    	<td></td><td></td>
	   	</tr>
	   	<tr>
	   		<td class="tdr">PT:</td><td id="pt_02">&nbsp;</td>
	   		<td class="tdr">CT: </td><td id="ct_02">&nbsp;</td>
	   		<td></td><td></td>
	    </tr>
	    <tr>
	    	<td class="tdr">第一套费率:</td><td colspan=4 id="fee1_02">&nbsp;</td>
	    	<td></td>
	   	</tr>
	   	<tr>
	   		<td class="tdr">第二套费率:</td><td colspan=4 id="fee2_02">&nbsp;</td>
	   		<td></td>
	   	</tr>
	</table>
	<table id="tabinfo7" class="tabinfo" align="center" style="display: none"> 
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
	    <tr><td class="td_lable">卡内返写信息</td></tr>
	    <tr>
	    	<td class="tdr" style="width: 140">返写标志:</td><td id="write_back_flag_02" style="width: 23%">&nbsp;</td>
	    	<td class="tdr" style="width: 12%">客户编号:</td><td id="userno_02_back" style="width: 12%">&nbsp;</td>
	    	<td class="tdr" style="width: 12%">ESAM表号:</td><td id="esamno_02_back">&nbsp;</td>
	    </tr>
	   	<tr>
	   		<td class="tdr">购电次数:</td><td id="buynum_02_back">&nbsp;</td>
	    	<td class="tdr">剩余金额(元):</td><td id="syje_02_back">&nbsp;</td>
	    	<td class="tdr">透支金额:</td><td id="tzje_02_back">&nbsp;</td>
	   	</tr>
	   	<tr>
	   		<td class="tdr">PT:</td><td id="pt_02_back">&nbsp;</td>
	   		<td class="tdr">CT: </td><td id="ct_02_back">&nbsp;</td>
	    	<td class="tdr">非法插卡次数: </td><td id="ffckcs_02_back">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">返写日期:</td><td id="fxdata_02_back">&nbsp;</td>
	    	<td class="tdr">返写时间:</td><td id="fxtime_02_back">&nbsp;</td>
	    	<td></td><td></td>
	   	</tr>
	</table>
	<!-- 2013智能卡 结束 -->

	<table id="tabinfo8" class="tabinfo" align="center">
		<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>	    
		<tr>
    		<td colspan="6" style="text-align: right;border: 0">
    		<button id="cardInfo" class="btn" >卡内信息</button>
	    </td></tr>
	</table>
	</div>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="zjg_id"/>
	<input type="hidden" value="1" id="mp_id" />
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="feeproj_id"/>
	<input type="hidden" id="yffalarm_id"/>
	<input type="hidden" id="buy_times"/>
	<input type="hidden" id="feeproj_reteval"/>
	<script type="text/javascript">
		var ComntUseropDef = {};
		ComntUseropDef.YFF_GYOPER_PAY = <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_PAY%>;
		ComntUseropDef.YFF_GYOPER_GPARASTATE = <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_GPARASTATE%>;
		$("#div_dkxx").height($(window).height());
	</script>
	</body>
</html>
