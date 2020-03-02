<%@ page language="java" import="com.kesd.util.I18N" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'yffratepara.jsp' starting page</title>
    
  </head>
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  <style type="text/css">
	body{
	  margin:0 0 0 0;
	  overflow:hidden;
	}
	.tabsty input,.tabsty select {
		width: 95px;
	}
	input,select{
		font-size: 12px;
	}
  </style>
  <script>
	var basePath = '<%=basePath%>';
  </script>
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/common/gridopt.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script src="<%=basePath%>js/common/dateFormat.js"></script>
  <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
  <script src="<%=basePath%>js/docs/yffratepara.js"></script>
  <script src="<%=basePath%>js/docs/pub_docper.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxcommon.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgrid.js"></script>
  <script src="<%=basePath%>js/dhtmlx/grid/dhtmlxgridcell.js"></script>
  <script src="<%=basePath%>js/validate.js"></script>
  <jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
  <body>
  <DIV id="gridbox" style="width: 100%;" ></DIV>
  	<table width="100%" border="0" class="page_tbl" >
    <tr><td id="pageinfo">&nbsp;</td><td align="right" id="soh">Hide detail</td><td width="15"><img src="<%=basePath%>images/mmd.gif" alt="hide" id=imghide onClick="gridopt.showOrHide(this);" style="cursor: pointer;"/></td></tr>
    </table>
    <div id="showmore">
    <form action="" method="post" id="addorupdate"  style='display:inline;'>
    <br/>
    <table class="tabsty" align="center">
    	<tr>
	    	<td><font color=red>*</font>nom : </td><td colspan="3"><input type="text" name="yffratepara.describe" id="describe" style=" width:280px;" /></td>
	    	<td style="display:none"> Indice tarifaire (TI) :  </td><td style="display:none"><input type="text" name="yffratepara.rateId" value="1" id="rateId"/></td>
	    	<td>Date d'activation :  </td><td><input type="text" id="activdateF" readonly onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn',isShowClear:'false',readOnly:'true',position:{top:'above'}});"/>
	    	<input type="hidden" name="yffratepara.activdate" id="activdate"/>	    	
	    	</td>	    	
	    	<td>Type de tarif : </td><td><select name="yffratepara.feeType" id="feeType" onChange="changefeeType(this.value)" style=" width:150px;"></select>  </td>
            <td>Limite de crédit maximale : </td><td><input type="text" name="yffratepara.moneyLimit" id="moneyLimit" /></td>	    	
	    </tr>
	  
	    <tr id="dan">
	    	<td>Tarif total : </td><td><input type="text" name="yffratepara.ratedZ" id="ratedZ"/></td>
	    	<td colspan="10"></td>
	    		
	   	</tr>
	   	<tr id="fu" style='display:none;'>
 	   	   	<td>Tarif de pointe : </td><td><input type="text" name="yffratepara.ratefF" id="ratefF"/></td>	
	    	<td>Hors tarif : </td><td><input type="text" name="yffratepara.ratefG" id="ratefG"/></td>
	  	 	<td colspan="8"></td>
	   	</tr>
	   	<tr id="hh1"  style='display:none;'>
	   		<td>Tarif mixte1: </td><td><input type="text" name="yffratepara.rateh1" id="rateh1"/></td>
        	<td>Tarif mixte2: </td><td><input type="text" name="yffratepara.rateh2" id="rateh2"/></td>	
	    	<td>Tarif mixte3: </td><td><input type="text" name="yffratepara.rateh3" id="rateh3"/></td>
	    	<td>Tarif mixte4: </td><td><input type="text" name="yffratepara.rateh4" id="rateh4"/></td>
	   	</tr>
	   	<tr id="hh2"  style='display:none;'> 
	   		<td>Ratio mixte1: </td><td><input type="text" name="yffratepara.ratehBl1" id="ratehBl1"/></td>
        	<td>Ratio mixte2: </td><td><input type="text" name="yffratepara.ratehBl2" id="ratehBl2"/></td>	
	    	<td>Ratio mixte3: </td><td><input type="text" name="yffratepara.ratehBl3" id="ratehBl3"/></td>
	    	<td>Ratio mixte4: </td><td><input type="text" name="yffratepara.ratehBl4" id="ratehBl4"/></td>
	   	</tr>	  
	   	<tr id="jt1"  style='display:none;'>
			<td>Numéro de tarif de l'étape :    </td><td>
											<select name="yffratepara.ratejNum" id="ratejNum">
												<option value="2">2</option>
												<option value="3">3</option>
												<option value="4">4</option>
												<option value="5">5</option>
												<option value="6">6</option>
												<option value="7">7</option>
											</select>
										</td>
	   		<td>Tarif par étapes1: </td><td><input type="text" name="yffratepara.ratejR1" id="ratejR1"/></td>
        	<td>Tarif par étapes2: </td><td><input type="text" name="yffratepara.ratejR2" id="ratejR2"/></td>	
	    	<td>Tarif par étapes3: </td><td><input type="text" name="yffratepara.ratejR3" id="ratejR3"/></td>
	    	<td>Tarif par étapes4: </td><td><input type="text" name="yffratepara.ratejR4" id="ratejR4"/></td>
	   		<td>Tarif par étapes5: </td><td><input type="text" name="yffratepara.ratejR5" id="ratejR5"/></td>
	   	</tr>
	   	<tr id="jt2"  style='display:none;'>
        	<td>Tarif par étapes6: </td><td><input type="text" name="yffratepara.ratejR6" id="ratejR6"/></td>
	   		<td>Tarif par étapes7: </td><td><input type="text" name="yffratepara.ratejR7" id="ratejR7"/></td>
	    	<td>Gradient de pas1(kWh): </td><td><input type="text" name="yffratepara.ratejTd1" id="ratejTd1"/></td>
        	<td>Gradient de pas2(kWh): </td><td><input type="text" name="yffratepara.ratejTd2" id="ratejTd2"/></td>	
	    	<td>Gradient de pas3(kWh): </td><td><input type="text" name="yffratepara.ratejTd3" id="ratejTd3"/></td>
	    	<td>Gradient de pas4(kWh): </td><td><input type="text" name="yffratepara.ratejTd4" id="ratejTd4"/></td>
	   	</tr>	  
	   	<tr id="jt3"  style='display:none;'>
	  		<td>Gradient de pas5(kWh): </td><td><input type="text" name="yffratepara.ratejTd5" id="ratejTd5"/></td>
        	<td>Gradient de pas6(kWh): </td><td><input type="text" name="yffratepara.ratejTd6" id="ratejTd6"/></td>	
	    	<td colspan="9"></td>
	   	</tr>
        <tr id="hjt1" style="display: none;">        	 
	    	
	    	<td>Étape Type de prix:</td><td><select id="ratehjType" name="yffratepara.ratehjType" ></select> </td>	
	    	<td>Numéro de l'étape:</td><td><select id="ratehjNum" name="yffratepara.ratehjNum" >
	    	<option value=2 >2</option>
	    	<option value=3 selected="selected">3</option>
	    	<option value=4 >4</option>
	    	</select></td>	
	    	<td>Type de tarif:</td><td><select id="meterfeehjType" name="yffratepara.meterfeehjType" > </select></td>	
	    	<td>Prix au mètre:</td><td><input type="text" name="yffratepara.meterfeehjR" id="meterfeehjR"/></td>
	   	</tr>
	   	<tr id="hjt2" style="display: none;">
	   		<td>Taux de pas1:</td><td><input type="text" name="yffratepara.ratehjHr1R1" id="ratehjHr1R1"/></td>
	   		<td>Gradient de pas1(kWh):</td><td><input type="text" name="yffratepara.ratehjHr1Td1" id="ratehjHr1Td1"/></td>
	    	<td>Taux de pas4:</td><td><input type="text" name="yffratepara.ratehjHr1R4" id="ratehjHr1R4"/></td>
	    	<td>Proportion mixte1(%):</td><td><input type="text" name="yffratepara.ratehjBl1" id="ratehjBl1"/></td>	    	
       	</tr>
	   	<tr id="hjt3" style="display: none;">
        	<td>Taux de pas2:</td><td><input type="text" name="yffratepara.ratehjHr1R2" id="ratehjHr1R2"/></td>	
        	<td>Gradient de pas2(kWh):</td><td><input type="text" name="yffratepara.ratehjHr1Td2" id="ratehjHr1Td2"/></td>	
	    	<td>pente2 Prix:</td><td><input type="text" name="yffratepara.ratehjHr2" id="ratehjHr2"/></td>
	    	<td>Proportion mixte2(%):</td><td><input type="text" name="yffratepara.ratehjBl2" id="ratehjBl2"/></td>	
	   	</tr>
	   	<tr id="hjt4" style="display: none;">
	    	<td>Taux de pas3:</td><td><input type="text" name="yffratepara.ratehjHr1R3" id="ratehjHr1R3"/></td>
	    	<td>Gradient de pas3(kWh):</td><td><input type="text" name="yffratepara.ratehjHr1Td3" id="ratehjHr1Td3"/></td>
	   		<td>pente3 Prix:</td><td><input type="text" name="yffratepara.ratehjHr3" id="ratehjHr3"/></td>
	    	<td>Proportion mixte3(%):</td><td><input type="text" name="yffratepara.ratehjBl3" id="ratehjBl3"/></td>	    	
	   	</tr>        
	   	<tr>
        	<td>Code d'interface 1: </td><td><input type="text" name="yffratepara.infCode1" id="infCode1"/></td>	
	    	<td>Code d'interface 2: </td><td><input type="text" name="yffratepara.infCode2" id="infCode2"/>
	    	
	    	<input type="hidden" id="id" name="yffratepara.id" />
	    	<input type="hidden" id="ratejType" name="yffratepara.ratejType" />	  
	    	<input type="hidden" id="meterfeeType" name="yffratepara.meterfeeType" />	    
	    	<input type="hidden" id="meterfeeR" name="yffratepara.meterfeeR" />
	    	<input type="hidden" id="ratefJ" name="yffratepara.ratefJ" /> 	    	 		    	 	
	    	 </td>
	    	<td colspan="8"></td>
	   	</tr>
    </table>
    </form>
    <center>
    <jsp:include page="../inc/btn_tianjia.jsp"></jsp:include>
    &nbsp;&nbsp;
    <jsp:include page="../inc/btn_xiugai.jsp"></jsp:include>
    &nbsp;&nbsp;
    <jsp:include page="../inc/btn_pldel.jsp"></jsp:include>
    </center>   
    </div>
    <jsp:include page="../inc/jsdef.jsp" />
    
     <script type="text/javascript">
	    var grid_title = "<i18n:message key="page.flfa.grid_title" />";
	    var name = "<i18n:message key="doc.name" />";
	    var zfl = "<i18n:message key="doc.zfl" />";
	    var jfl = "<i18n:message key="doc.jfl" />";
	    var ffl = "<i18n:message key="doc.ffl" />";
	    var pfl = "<i18n:message key="doc.pfl" />";
	    var gfl = "<i18n:message key="doc.gfl" />";
    </script>
  </body>
</html>
