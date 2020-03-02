<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
    <tr><td colspan="6" style="height:10px; border:0px;"></td></tr>
    	<tr>
	    	<td class="td_lable" style="height:25px;">Base Information</td>
	    	<td colspan=4 style="padding-left:10px; border: 0px;">
	    	<button id="btnSearch" class="btn" >Retrieval</button>
 	    	</td>
	    	<td style="border:0px; text-align: right;" ><img id=rtuonline_img style="display:none"/>&nbsp;<span id=rtuonline_sp></span>&nbsp;&nbsp;</td>
	    </tr>
   		<tr>
	    	<td class="tdr" width="12%">Consumer ID:</td><td id="userno" width="15%">&nbsp;</td>
	    	<td class="tdr" width="12%">Consumer Name:</td><td id="username" width="20%">&nbsp;</td>
	     	<td class="tdr" width="12%">Consumer State:</td><td id="cus_state">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">Telephone:</td><td id="tel">&nbsp;</td>
	   		<td class="tdr">Address:</td><td colspan=3 id="useraddr">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">Meter ID:</td><td id="commaddr">&nbsp;</td>
	    	<td class="tdr">Prepayment Type:</td><td id="yffmeter_type_desc">&nbsp;</td>
	    	<td class="tdr">Utility ID:</td><td id="utilityid">&nbsp;</td>
	    </tr>
	    <tr>
	    	<td class="tdr">Manufacturers:</td><td id="factory">&nbsp;</td>
	    	<td></td><td></td>
	    	<td></td><td></td>
	    </tr>
	    <tr><td colspan="6" style="height:10px; border: 0px;"></td></tr>
    	<tr><td class="td_lable">Cost Control Information</td></tr>
	   <tr>
	    	<td class="tdr">Billing Mode:</td><td id="cacl_type_desc">&nbsp;</td>
	    	<td class="tdr">Cost Control Mode:</td><td id="feectrl_type_desc">&nbsp;</td>
	    	<td class="tdr">Payment Mode:</td><td id="pay_type_desc">&nbsp;</td>
	    </tr>
	   	<tr>
	    	<td class="tdr">Tariff Project:</td><td id="feeproj_desc">&nbsp;</td>
	    	<td class="tdr">Tariff Description:</td><td colspan=3 id="feeproj_detail">&nbsp;</td>
	   	</tr>
	   	<tr>
	   		<td class="tdr">Overdraw Amount:</td><td id="tzval">&nbsp;</td>
	   		<td class="tdr">CT:</td><td id="ctData"></td>
	   		<td class="tdr">PT:</td><td id="ptData"></td>
	    </tr>
	 	<tr><td colspan="6" style="height:5px; border-left: 0px;border-right: 0px;border-bottom: 0px;"></td></tr>	    
	    <tr id="_title3">
	    <td class="td_lable" style="border-bottom: 0">Purchasing Record</td>
	    </tr>