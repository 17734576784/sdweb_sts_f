<%@ page language="java" import="com.kesd.util.I18N" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title></title>    
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
  <script src="<%=basePath%>js/docs/stspara.js"></script>
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
    		<td width="120px">Utility Name: </td><td><input type="text" name="orgpara.describe" id="describe" style="width: 200px"/></td>	
	    	<td  width="220px"><font color=red>*</font>Supply Group Code(SGC): </td><td><input type="text" name="orgpara.rorgNo" id="rorgNo"  style="width: 200px" /></td>
	    	<td width="120px"> Address: </td><td><input type="text" name="orgpara.addr" id="addr"  style="width: 200px" /></td>
	    </tr>
	    <tr>
	    	<td width="120px"> postalCode: </td><td><input type="text" name="orgpara.postalCode" id="postalCode"   style="width: 200px"/></td>
	    	<td width="120px"> linkMan: </td><td><input type="text" name="orgpara.linkMan" id="linkMan"  style="width: 200px" /></td>
	    	<td width="120px"> telNo: </td><td><input type="text" name="orgpara.telNo" id="telNo"   style="width: 200px"/><input type="hidden" name="orgpara.id" id="id" />	</td>	    	
 	    </tr>
	  
	    <tr>
	    <td width="120px">Key Type: </td><td><select name="orgstspara.KT" id="KT" style="width: 200px"><option value="0">0 - Initialization</option><option value="1">1 - Default</option><option value="2">2 - Unique</option><option value="3">3 - Common</option></select></td>	        	
    	<td width="220px">Decoder Key Generation Algorithm: </td><td><select name="orgstspara.DKGA" id="DKGA" style="width: 200px"><option value="1">DKGA01</option><option value="2">DKGA02</option><option value="3">DKGA03</option><option value="4">DKGA04</option></select> </td>
        <td width="120px">Encrypt Type: </td><td><select name="orgstspara.ET" id="ET" style="width: 200px" onChange="isDisplayKr();"><option value="0">Soft Encryption</option><option value="1">Hardware Encryption</option></select></td>
        </tr>
        <tr>
        <td width="120px">Vending Key1: </td><td><input type="text" name="orgstspara.VK1" id="VK1" style="width: 200px"/></td>	   
    	<td width="120px">Vending Key2: </td><td><input type="text" name="orgstspara.VK2" id="VK2" style="width: 200px"/></td>
	   	<td width="120px" id="KRDesc">Key Regno: </td><td><input name="orgstspara.KR" id="KR" style="width: 200px;display:none" value="1" /></td> 
	   	</tr>	   	
	  	<tr>
	  	<td width="120px">Vending Key3: </td><td><input type="text" name="orgstspara.VK3" id="VK3" style="width: 200px"/></td>	    
    	<td width="120px">Vending Key4: </td><td><input type="text" name="orgstspara.VK4" id="VK4" style="width: 200px"/></td>
    	<td width="120px">Register Flag: </td><td><select name="orgReg.useFlag" id="useFlag" style="width: 150px">
    												<option value="0">Not Limit</option>
    												<option value="1">Limit</option>
    											  </select>
    										  </td>
	  	</tr>
	  	<tr>
	  	<td width="120px">KMF: </td>
	  	<td colspan="5"><input type="text" name="orgstspara.KMF" id="KMF" style="width: 700px"/></td>	
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
