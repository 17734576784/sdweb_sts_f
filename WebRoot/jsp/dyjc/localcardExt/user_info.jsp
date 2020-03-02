<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<tr><td colspan="6" style="height:5px; border:0px;"></td></tr>
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
	   		<td class="tdr">客户地址:</td><td id="useraddr">&nbsp;</td>
	   		<td class="tdr">电表类型:</td><td id="meterType">&nbsp;</td>
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
	 	 <tr><td colspan="6" style="height:5px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>	    
	    <tr id="_title3">
	    <td class="td_lable" style="border-bottom: 0">购电记录</td>
	    </tr>