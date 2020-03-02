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
    <title>My JSP 'gyhb.jsp' starting page</title>
    <style type="text/css">
		input,select{
			font-size: 12px;
			width:160px;				
		}			  
	</style>
	<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
	<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
	<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
	<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
	<script  src="<%=basePath%>js/common/modalDialog.js"></script>
	<script  src="<%=basePath%>js/common/number.js"></script>
	<script  src="<%=basePath%>js/common/jsonString.js"></script>
	<script  src="<%=basePath%>js/common/def.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script  src="<%=basePath%>js/spec/localcard/gychgmeter.js"></script>
	<script  src="<%=basePath%>js/spec/localcard/card_calcu.js"></script>
	<script  src="<%=basePath%>js/spec/tool/setCons.js"></script>
	<script  src="<%=basePath%>js/spec/dialog/search.js"></script>
	<script  src="<%=basePath%>js/validate.js"></script>
	<script  src="<%=basePath%>js/common/DateFun.js"></script>
	<script  src="<%=basePath%>js/spec/mis/mis.js"></script>
	
  </head>
  
  <body>
    <div style="overflow: auto;width:100%;" id="div_gyhb">
	<table id="tabinfo" class="tabinfo" align="center">
    	<tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;">基本信息</td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
	    	<button id="btnSearch" class="btn" >检索</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
	    <tr>
			<td class="tdr">卡表类型: </td><td id="cardmeter_type">&nbsp;</td>
	   		<td class="tdr">写卡户号: </td><td id="writecard_no">&nbsp;</td>
	   		<td class="tdr">ESAM表号:</td><td id="meter_id">&nbsp;</td>
		</tr>
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
	    <tr><td colspan="6" style="height:8px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>	    
	   	<tr><td class="td_lable"  style="border-bottom: 0">购电记录</td></tr>
	    </table>
	<table class="gridbox" align="center">
		  <tr><td style="padding: 0 0 0 0;" ><div id=gridbox style="border:0px;width:100%;height:135px;"></div></td></tr>
	</table>
	 <table id="tabinfo3" class="tabinfo" align="center">
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" style="width: 120px;">更换信息</td></tr>
    	<tr>
	    	<td class="tdr" width="10%">更换时间:</td><td width="15%">
	    	<input id="chg_time" class="roinput" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日 HH时mm分',maxDate:'%y-%M-%d %H:%m',isShowClear:'false'});" />
	    	</td>
	    	<!-- 
	    	<td class="tdr" width="10%">更换电表:</td><td class="tdrn" width="15%">
	    	<select id="chg_meter" ></select>
	    	</td>
	    	 -->
	    	<td class="tdr" width="10%">更换类型:</td><td class="tdrn" width="15%">
	    	<select id="chg_type">
		    	<option value="<%=SDDef.YFF_CHGMETER_MT%>">更换电表</option>
		    	<option value="<%=SDDef.YFF_CHGMETER_CT%>">更换CT</option>
		    	<option value="<%=SDDef.YFF_CHGMETER_PT%>">更换PT</option>
	    	</select>
	    	</td>
	    	<td width="10%"></td><td width="15%"></td>
	    </tr>
	    <tr>
	    	<td class="tdr">新CT分子:</td><td class="tdrn"><input type="text" id="ctfz"/></td>
	    	<td class="tdr">新CT分母:</td><td class="tdrn"><input type="text" id="ctfm"/></td>
	    	<td class="tdr">新CT变比:</td><td class="tdrn"><span id="ct"></span></td>
	    </tr>
	    <tr>
	    	<td class="tdr">新PT分子:</td><td class="tdrn"><input type="text" id="ptfz"/></td>
	    	<td class="tdr">新PT分母:</td><td class="tdrn"><input type="text" id="ptfm"/></td>
	    	<td class="tdr">新PT变比:</td><td class="tdrn"><span id="pt"></span></td>
	    </tr>
	   
	</table>
	<table id="tabinfo2" class="tabinfo" align="center">
		<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable" style="width: 120px;">缴费信息</td></tr>
	     <tr>
	    	<td class="tdr" width="10%">缴费金额(元):</td><td class="tdrn" width="15%"><input id="jfje" name="jfje"/></td>
	    	<td class="tdr" width="10%">追补金额(元):</td><td class="tdrn" width="15%"><input id="zbje" name="zbje"/></td>		  		        		    				
	    	<td class="tdr" width="10%">结算金额(元):</td><td class="tdrn" width="15%"><input id="jsje" name=jsje/></td>
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
	   </table>
	  
	<table id="tabinfo4" class="tabinfo" align="center">
	 	<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr>
    		<td colspan="6" style="text-align: right;border: 0">
	    	<button id="cardInfo" class="btn" >卡内信息</button>&nbsp;&nbsp;&nbsp;&nbsp;
<!--	    	<button id="rtuInfo" class="btn" disabled="disabled">终端内信息</button>&nbsp;&nbsp;&nbsp;&nbsp;-->
	    	<button id="btnChgmeter"  class="btn"  disabled="disabled">换表</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="btnPrt"  	class="btn" disabled="disabled">打印</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    </td></tr>
		<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    
	</table>
	</div>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="zjg_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="feeproj_id"/>
	<input type="hidden" id="yffalarm_id"/>
	<input type="hidden" id="buy_times"/>
	<input type="hidden" id="feeproj_reteval"/>

	<jsp:include page="../../inc/jsdef.jsp"></jsp:include>
	<script type="text/javascript">
	var ComntUseropDef = {};
	ComntUseropDef.YFF_GYOPER_CHANGEMETER	= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_CHANGEMETER%>;
	ComntUseropDef.YFF_GYOPER_MIS_QUERYPOWER= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_MIS_QUERYPOWER%>;
	ComntUseropDef.YFF_GYOPER_MIS_PAY 		= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_MIS_PAY%>;

	var ComntDef = {};
	ComntDef.YD_PROTOCAL_FKGD05				= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_FKGD05%>;
	ComntDef.YD_PROTOCAL_GD376				= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_GD376%>;
	ComntDef.YD_PROTOCAL_GD376_2013			= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_GD376_2013%>;
	ComntDef.YD_PROTOCAL_KEDYH				= <%=com.kesd.comnt.ComntDef.YD_PROTOCAL_KEDYH%>;
		
	var SDDef		   = {};
	SDDef.STR_AND 						= "<%=SDDef.STR_AND%>";
	SDDef.STR_OR 						= "<%=SDDef.STR_OR%>";	
	SDDef.YFF_CHGMETER_MT				= <%=SDDef.YFF_CHGMETER_MT%>;
	SDDef.YFF_CHGMETER_CT				= <%=SDDef.YFF_CHGMETER_CT%>;
	SDDef.YFF_CHGMETER_PT				= <%=SDDef.YFF_CHGMETER_PT%>;
	SDDef.CARD_OPTYPE_OPEN				= <%=SDDef.CARD_OPTYPE_OPEN%>; 
	SDDef.YFF_OPTYPE_PAY				= <%=SDDef.YFF_OPTYPE_PAY%>;
	
	
	$("#div_gyhb").height($(window).height());
	var sel_user_info = "<i18n:message key="baseInfo.sel_user_info"/>";
	
	</script>
  </body>
</html>
