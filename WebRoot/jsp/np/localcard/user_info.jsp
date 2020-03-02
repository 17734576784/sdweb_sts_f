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
	    	<td class="tdr">身份证号:</td><td id="identityno">&nbsp;</td>
	    	<td class="tdr">自然村:</td><td id="village">&nbsp;</td>
	    	<td class="tdr">联系电话:</td><td id="tel">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">客户地址:</td><td id="useraddr">&nbsp;</td>
	   		<td class="tdr">结算客户名:</td><td id="jsyhm">&nbsp;</td>
		    <td class="tdr">客户分类:</td><td id="yhfl">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">所属片区:</td><td id="area">&nbsp;</td>
	    	<td class="tdr">区域号:</td><td id="area_no">&nbsp;</td>
	    	<td></td><td></td>    	
	    </tr>
	   	<tr><td colspan="6" style="height:5px; border: 0px;"></td></tr>
    	<tr><td class="td_lable">购电卡信息</td></tr>
	   <tr>
	    	<td class="tdr">购电卡号:</td><td id="card_no">&nbsp;</td>
	    	<td class="tdr">购电卡状态:</td><td id="card_state_desc">&nbsp;</td>
	    	<td class="tdr">购电次数:</td><td id="buy_times">&nbsp;</td> 	
	    </tr>
	 	 <tr><td colspan="6" style="height:5px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>	    
	    <tr id="_title3">
	    <td class="td_lable" style="border-bottom: 0">购电记录</td><td id="ydInfo" style="border : 0; visibility: hidden" onclick="showYDInfo();">&nbsp;&nbsp;&nbsp;&nbsp;<a href='#'>用电信息</a></td>
	    </tr>