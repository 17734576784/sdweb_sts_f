<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.WebConfig"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<i18n:bundle baseName="<%=SDDef.BUNDLENAME%>" id="bundle" locale="<%=WebConfig.app_locale%>" scope="application" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'gyjf.jsp' starting page</title>
    <style type="text/css">
		input{
			width: 130px;
		}
		input,select{
			font-size: 12px;
		}
	</style>
	<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
	<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
	<script  src="<%=basePath%>js/common/modalDialog.js"></script>
	<script  src="<%=basePath%>js/common/number.js"></script>
	<script  src="<%=basePath%>js/common/jsonString.js"></script>
	<script  src="<%=basePath%>js/common/def.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script  src="<%=basePath%>js/spec/localcard/card_calcu.js"></script>
	<script  src="<%=basePath%>js/spec/localcard/gypaychgfee.js"></script>
	 	<script  src="<%=basePath%>js/spec/tool/setCons.js"></script>
	<script  src="<%=basePath%>js/spec/dialog/search.js"></script>
	<script  src="<%=basePath%>js/validate.js"></script>
  	<script  src="<%=basePath%>js/spec/mis/mis.js"></script>
  	<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
    <script  src="<%=basePath%>js/common/cookie.js"></script>
	<script  src="<%=basePath%>js/common/dateFormat.js"></script> 
  </head>
  
  <body>
  <div style="overflow: auto;width:100%;" id="div_scroll">
    <table id="tabinfo" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;">基本信息</td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
<!--	    	<button id="btnSearch" class="btn">检索</button>&nbsp;&nbsp;&nbsp;-->
	    	<button id="btnReadCard" class="btn">读卡</button>
	    	</td>
	    	<td style="border:0px; text-align: right;" ><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    </tr>
   		<tr>
	    	<td class="tdr" width="12%">客户编号:</td><td id="userno"  width="17%">&nbsp;</td>
	    	<td class="tdr" width="12%">客户名称:</td><td id="username" width="20%">&nbsp;</td>
	     	<td class="tdr" width="12%">客户状态:</td><td id="cus_state">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">联系电话:</td><td id="tel">&nbsp;</td>
	   		<td class="tdr">客户地址:</td><td colspan=3 id="useraddr">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">费控单元:</td><td id="fee_unit">&nbsp;</td>
	    	<td class="tdr">结算客户名:</td><td id="jsyhm">&nbsp;</td>
	    	<td class="tdr" id="khfl">客户分类:</td><td id="yhfl">&nbsp;</td>
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

		<!--card add 20120907-->
		<tr id = gdlctrl>
			<td class="tdr">卡表类型: </td><td id="cardmeter_type">&nbsp;</td>
	   		<td class="tdr">写卡户号: </td><td id="writecard_no">&nbsp;</td>
	   		<td class="tdr">ESAM表号:</td><td id="meter_id">&nbsp;</td>
		</tr>

	    <tr><td colspan="6" style="height:8px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>	    
	   	<tr><td class="td_lable"  style="border-bottom: 0">购电记录</td></tr>
	    </table>
	<table class="gridbox" align="center">
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%"></div></td></tr>
	</table>
	
	<table id="tabinfo2" class="tabinfo" align="center">
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable">更改费率</td></tr>
	   	<tr>
	    	<td class="tdr">新费率方案:</td><td ><select id="flfa" style="width:160px;"></select> </td>		  		        		    				
	    	<td class="tdr">方案描述:</td><td colspan=3 id=fams_n></td>
	   	</tr>
	   	<tr>
	   	  <td class="tdr">切换时间:</td><td ><input id=qhsj style="width:160px;" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日 HH时mm分',maxDate:'%y-%M-%d %H:%m',isShowClear:'false'});"/></td>		  		        		    				
	    	<td class="tdr">更改类型:</td>
	    	<td >
	    	   <select id="gglx" style="width:160px;">
			       <option value="flcs">费率参数</option>
			   <!--<option value="jbf">基本费</option> -->
	    	   </select> 
	    	</td>	
	    		<td class="tdr">更换电表:</td >
	    	<td>
	    	     <select id="ghdb" style="width:160px;"  >
	    	     </select> 
	    	</td>
	   	</tr> 
	 
	    
	    
	
	   	 
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable">缴费信息</td></tr>
	    <tr>
	    	<td class="tdr">缴费金额(元):</td><td class="tdrn"><input id="jfje" name="jfje"/></td>
	    	<td class="tdr" width="140">追补金额(元):</td><td class="tdrn"><input id="zbje" name="zbje"/></td>		  		        		    				
	    	<td class="tdr">结算金额(元):</td><td class="tdrn"><input id="jsje" name=jsje/></td>
	    </tr>
	     <tr >
	    	<td class="tdr">总金额(元):</td><td class="tdrn"><span id="zje"></span></td>
	    	<td class="tdr" id="alarm1">报警值1:</td><td class="tdrn"><input id="yffalarm_alarm1"/></td>
	    	<td class="tdr" id="alarm2">报警值2:</td><td class="tdrn"><input id="yffalarm_alarm2"/></td>
	    </tr>
    	<tr >
    	<td class="tdr">购电次数:</td><td class="tdrn"><span id="buy_times"></span></td>

    	<td class="tdr" ><span id= "gdldesc" >购电量:</span></td>    	<td class="tdrn" /><span id="buy_dl" ></span></td>
    	<td class="tdr" ><span id= "pay_bmcdesc">表码差:</span></td>	<td class="tdrn" /><span id="pay_bmc" ></span></td>

    	</tr>
	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
	    <!-- 
    	<tr>
    	<td colspan="6" style="text-align: right;border: 0">
	    	<button id="cardInfo"  	class="btn">卡内信息</button>&nbsp;&nbsp;&nbsp;&nbsp;
	   	 	<button id="chgMbphone" class="btn"><i18n:message key="btn.ghsj"/></button> &nbsp;&nbsp;&nbsp;&nbsp; 
	    	<button id="btnPay"  	class="btn" disabled >缴费</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnPrt"  	class="btn" disabled >打印</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    </td></tr>
	     -->
	</table>
	<table id="tabbutton" class="tabinfo" align="center">
		<tr>
			<td style="text-align: right;border: 0; width:57%"></td>
			<td style="text-align: right;border: 0; width:10%">
				<button id="chgMbphone" class="btn"><i18n:message key="btn.ghsj"/></button> 
			</td>
			<td style="text-align: right;border: 0; width:3%"></td>
    		<td style="text-align: right;border: 0; width:30%">
    		<button id="cardInfo"  	class="btn">卡内信息</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnPay"  	class="btn" disabled >缴费</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnPrt"  	class="btn" disabled >打印</button>&nbsp;&nbsp;&nbsp;&nbsp;   	 
	    	</td>
	    </tr>	 
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
	
	<jsp:include page="../../inc/jsdef.jsp"></jsp:include>
	<script type="text/javascript">
	$("#gridbox").height($(window).height()-$("#tabinfo").height()-$("#tabinfo2").height());
	$("#div_scroll").height($(window).height());		
	var yff_grid_title ='操作日期,操作类型,缴费金额(元),追补金额(元),结算金额(元),总金额(元),断电值,报警值1,报警值2,购电次数,流水号,缴费方式,操作员,&nbsp;';
	var ComntUseropDef = {};
	ComntUseropDef.YFF_GYOPER_PAY			= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_PAY%>;
	ComntUseropDef.YFF_GYOPER_MIS_QUERYPOWER= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_MIS_QUERYPOWER%>;
	ComntUseropDef.YFF_GYOPER_MIS_PAY		= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_MIS_PAY%>;
	ComntUseropDef.YFF_GYOPER_RESETDOC      = <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_RESETDOC%>;
	ComntUseropDef.YFF_GYOPER_CHANGERATE      = <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_CHANGERATE %>;
	
	
	
	
	var SDDef = {};
	SDDef.YFF_CACL_TYPE_MONEY 	= <%=SDDef.YFF_CACL_TYPE_MONEY%>;
	SDDef.YFF_CACL_TYPE_BD 		= <%=SDDef.YFF_CACL_TYPE_BD%>;
	SDDef.CARD_OPTYPE_BUY		= "<%=SDDef.CARD_OPTYPE_BUY%>"
	SDDef.YFF_OPTYPE_PAY		= <%=SDDef.YFF_OPTYPE_PAY%>;
	SDDef.YFF_FEETYPE_DFL		=  <%=SDDef.YFF_FEETYPE_DFL%>;	 
	SDDef.YFF_FEETYPE_FFL		=  <%=SDDef.YFF_FEETYPE_FFL%>;

	var sel_user_info = "<i18n:message key="baseInfo.sel_user_info"/>";
	
	</script>
  </body>
</html>
