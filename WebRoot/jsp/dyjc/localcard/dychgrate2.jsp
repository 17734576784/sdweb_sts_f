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
			input{
				font-size: 12px;
				width: 100px;
				text-align:right;
			}	
			.tdrn{
				font-size: 14px;
				text-align:right;
				font-family: sans-serif;
			}
			.tdrn input{
				font-size: 14px;
				font-weight: bold;
				text-align:right;
				/*font-family: sans-serif;*/
			}		
		</style>
		<link href="<%=basePath%>css/zh/cont.css" rel="stylesheet" type="text/css" id="cont" />
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid_skins.css">
		<script  src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>		
		<script  src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
		<script  src="<%=basePath%>js/common/number.js"></script>
		<script  src="<%=basePath%>js/common/dateFormat.js"></script>
		<script  src="<%=basePath%>js/common/modalDialog.js"></script>
		<script  src="<%=basePath%>js/common/jsonString.js"></script>
		<script  src="<%=basePath%>js/common/loading.js"></script>
		<script  src="<%=basePath%>js/common/def.js"></script>
		<script  src="<%=basePath%>js/dyjc/dialog/search.js"></script>		
		<script  src="<%=basePath%>js/dyjc/tool/setCons.js"></script>	
		<script  src="<%=basePath%>js/dyjc/localcard/dychgrate2.js"></script>
		<script  src="<%=basePath%>js/dyjc/mis/mis.js"></script>

	</head>
	<body>
	<table id="tabinfo1" class="tabinfo" align="center">
    <tr><td colspan="8" style="height:5px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:22px;"  id="td2">基本信息</td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
		    	<button id="btnSearch"  class="btn">检索</button>&nbsp;&nbsp;&nbsp;&nbsp;
		    	<button id="btnRead"  class="btn">读卡</button>	    	
	    	</td>
	    	<td style="border:0px; text-align: right;"><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    </tr>
   		<tr>
	    	<td class="tdr" style="width: 10%">客户编号:</td><td id="userno" style="width: 10%">&nbsp;</td>
	    	<td class="tdr" style="width: 10%">客户名称:</td><td id="username" style="width: 15%">&nbsp;</td>
	     	<td class="tdr" style="width: 10%">客户状态:</td><td id="cus_state" style="width: 10%">&nbsp;</td>
	    </tr>
	     <tr>
	    	<td class="tdr">联系电话:</td><td id="tel">&nbsp;</td>
	   		<td class="tdr">客户地址:</td><td colspan=3 id="useraddr">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">ESAM表号:</td><td id="esamno">&nbsp;</td>
	    	<td class="tdr">预付费表类型:</td><td id="yffmeter_type_desc">&nbsp;</td>
	    	<td class="tdr">生产厂家:</td><td id="factory">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">倍率:</td><td id="blv">&nbsp;</td>
	    	<td class="tdr">接线方式:</td><td id="wiring_mode">&nbsp;</td>
	    	<td class="tdr">写卡户号: </td><td id="writecard_no">&nbsp;</td>
	    </tr>
	    <tr id="mis_jsxx">
	        <td class="tdr">结算客户名:</td><td id="jsyhm">&nbsp;</td>
		    <td class="tdr" id="khfl">客户分类:</td><td id="yhfl" colspan=3>&nbsp;</td>
	     </tr>
	   	  <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
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
	    
		<tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable"><i18n:message key="baseInfo.flqhsj"/></td></tr>
	    <!-- 费率切换时间 -->
	    <tr>
	    	<td class="tdr" style="width:10%">分时切换时间:</td>
	    	<td colspan=2>
	    		<input id=qhsj name=qhsj style="width:180px ; text-align:left;" readonly/>&nbsp;&nbsp;&nbsp;&nbsp;
	    		<button id="btnReadSwitchTime"  class="btn"  disabled="disabled"><i18n:message key="btn.dqhsj"/></button>&nbsp;&nbsp;&nbsp;&nbsp; 
	    	</td>
	    	<td class="tdr" style="width:15%">新分时切换时间:</td>
	    	<td colspan=2>
	    		<input id=xqhsj name=xqhsj  style="width:180px;text-align:left;"  onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日 HH时mm分',isShowClear:'false'});"/>&nbsp;&nbsp;&nbsp;&nbsp;
	    		 <button id="btnSetSwitchTime" class="btn" disabled="disabled"><i18n:message key="btn.sqhsj"/></button>&nbsp;&nbsp;&nbsp;&nbsp; 
	    	</td>  		        		    				
	    </tr>
	    <!-- 阶梯费率切换时间 -->
	    <tr>
	    	<td class="tdr" style="width:10%">阶梯切换时间:</td>
	    	<td colspan=2>
	    		<input id="jt_qhsj" name=jt_qhsj style="width:180px ; text-align:left;" readonly/>&nbsp;&nbsp;&nbsp;&nbsp;
	    		<button id="jt_btnReadSwitchTime"  class="btn"  disabled="disabled">读取阶梯切换时间</button>&nbsp;&nbsp;&nbsp;&nbsp; 
	    	</td>
	    	<td class="tdr" style="width:15%">新阶梯切换时间:</td>
	    	<td colspan=2>
	    		<input id="jt_xqhsj" name=jt_xqhsj  style="width:180px;text-align:left;"  onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日 HH时mm分',isShowClear:'false'});"/>&nbsp;&nbsp;&nbsp;&nbsp;
	    		 <button id="jt_btnSetSwitchTime" class="btn" disabled="disabled">设置阶梯切换时间</button>&nbsp;&nbsp;&nbsp;&nbsp; 
	    	</td>  		        		    				
	    </tr>
	    <tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr><td class="td_lable"><i18n:message key="baseInfo.bnflxx"/></td></tr>
	   <!-- 2013111将电表费率改成费率文件，其他没有变 -->
	    <tr>
	    	<td class="tdr">费率文件:</td>
	    	<td colspan=3 id="fee_desc" style="boder-right:none">
	    	</td>
	    	<td style="text-align:right">
	    		<input type="radio" id="fei1" name="fei" style="width: 25px;" checked/>第一套&nbsp;&nbsp;
	    		<input type="radio" id="fei2" name="fei"  style="width: 25px;"/>第二套&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>
	    	<td style="border-left:none;text-align:right">
	    		<button id="btnReadFee"  class="btn"  disabled="disabled">读取文件</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>
	    </tr>
	    <!-- 20131113新增电表费率一行 -->
	     <tr>
	    	<td class="tdr">电表费率:</td>
	    	<td colspan=3 id="feelv_desc" style="boder-right:none">
	    	</td>
	    	<td style="text-align:right">
	    		<input type="radio" id="feilv1" name="feilv" style="width: 25px;" checked/>当前套&nbsp;&nbsp;
	    		<input type="radio" id="feilv2" name="feilv"  style="width: 25px;"/>备用套&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>
	    	<td style="border-left:none;text-align:right">
	    		<button id="btnReadFeelv"  class="btn"  disabled="disabled">读取费率</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	</td>
	    </tr>
	   <!-- end -->    
	    
	   <tr>
	    	<td class="tdr"><i18n:message key="baseInfo.xin"/><i18n:message key="baseInfo.flfa"/></td>
	    	<td ><select id="xflfa" style="width: 150px;"></select></td>
	    	<td class="tdr"><i18n:message key="baseInfo.fams"/></td>
	    	<td colspan=2 id="feeproj_detail_new"></td>
	    	<td style="border-left:none;text-align:right">&nbsp;&nbsp;&nbsp;&nbsp;<button id="btnSetFee"  class="btn"  disabled="disabled"><i18n:message key="baseInfo.shezhi"/><i18n:message key="baseInfo.fl"/></button>&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    </tr>
	   
	   <tr><td colspan=6 style="border:0; text-align: right" >
	   		<button id="cardinfo" 	  class="btn">卡内信息</button> &nbsp;&nbsp;&nbsp;&nbsp;	
    		<button id="btnChangeFL"  class="btn"  disabled="disabled"><i18n:message key="btn.bcxfl"/></button>&nbsp;&nbsp;&nbsp;&nbsp;   	 
	    </td></tr>
	</table>
	</body>
	<input type="hidden" id="rtu_id"/>
	<input type="hidden" id="mp_id"/>
	<input type="hidden" id="pay_type"/>
	<input type="hidden" id="feeproj_id"/>
	
	<input type="hidden" id="fee_z" value=0 />
	<input type="hidden" id="fee_j" value=0 />
	<input type="hidden" id="fee_f" value=0 />
	<input type="hidden" id="fee_p" value=0 />
	<input type="hidden" id="fee_g" value=0 />
	
	<input type="hidden" id="user_type" value=2 />                            
	<input type="hidden" id="bit_update" value=125 />                           
	<input type="hidden" id="chg_date" />                             
	<input type="hidden" id="chg_time" />                             
	<input type="hidden" id="alarm_val1" value=10000 />                           
	<input type="hidden" id="alarm_val2" value=0 />                           
	<input type="hidden" id="pct" value=1 />                               
	<input type="hidden" id="pt" value=1 />                               
	<input type="hidden" id="meterno" />                              
	<input type="hidden" id="userno" />         

	<script type="text/javascript">
	var ComntUseropDef = {};
	ComntUseropDef.YFF_DYOPER_CHANGERATE = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_CHANGERATE%>;
	ComntUseropDef.YFF_DYCOMM_CALLPARA = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYCOMM_CALLPARA%>;
	ComntUseropDef.YFF_DYCOMM_SETPARA = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYCOMM_SETPARA%>;
	var ComntProtMsg = {};
	ComntProtMsg.YFF_DY_CALL_PARA = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_CALL_PARA%>;
	ComntProtMsg.YFF_DY_CALL_FEI1 = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_CALL_FEI1%>;
	ComntProtMsg.YFF_DY_CALL_FEI2 = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_CALL_FEI2%>;
	ComntProtMsg.YFF_DY_CALL_JTFEI1 = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_CALL_JTFEI1%>;
	ComntProtMsg.YFF_DY_CALL_JTFEI2 = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_CALL_JTFEI2%>;
	ComntProtMsg.YFF_DY_CALL_STATE = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_CALL_STATE%>;
	ComntProtMsg.YFF_DY_SET_CHGTIME = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_SET_CHGTIME%>;
	ComntProtMsg.YFF_DY_SET_JTCHGTIME = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_SET_JTCHGTIME%>;
	ComntProtMsg.YFF_DY_FEE_BLOCK = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_FEE_BLOCK%>;
	ComntProtMsg.YFF_DY_JTFEE_BLOCK	= <%=com.kesd.comnt.ComntProtMsg.YFF_DY_JTFEE_BLOCK%>;
	
	ComntProtMsg.YFF_DY_FEE_1 = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_FEE_1%>;
	ComntProtMsg.YFF_DY_FEE_2 = <%=com.kesd.comnt.ComntProtMsg.YFF_DY_FEE_2%>;

	ComntProtMsg.YFF_CALL_FEI1		= <%=com.kesd.comnt.ComntProtMsg.YFF_CALL_FEI1%>;	//一套费率
	ComntProtMsg.YFF_CALL_FEI2      = <%=com.kesd.comnt.ComntProtMsg.YFF_CALL_FEI2%>;	//二套费率
	ComntProtMsg.YFF_CALL_JTFEI1    = <%=com.kesd.comnt.ComntProtMsg.YFF_CALL_JTFEI1%>;	//一套阶梯费率
	ComntProtMsg.YFF_CALL_JTFEI2    = <%=com.kesd.comnt.ComntProtMsg.YFF_CALL_JTFEI2%>;	//二套阶梯费率
    ComntUseropDef.YFF_READDATA = <%=com.kesd.comntpara.ComntUseropDef.YFF_READDATA%>;
	ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_QUERYPOWER%>;
	ComntUseropDef.YFF_DYOPER_MIS_PAY 		 = <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_MIS_PAY%>;
	</script>
</html>
