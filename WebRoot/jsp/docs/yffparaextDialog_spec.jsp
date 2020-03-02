<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%@page import="com.kesd.common.Dict"%>
<%@page import="com.kesd.common.YDTable"%>
<%@page import="com.kesd.util.I18N"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><i18n:message key="doc.zjzfkcs" /></title>
    <style type="text/css">
body {
	margin:0 0 0 0;
	overflow : false;
	background: #eaeeef;
}
.tabsty {
	width:95%;
}
.tabsty input, .tabsty select {
	width: 140px;
}
input, select {
	font-size: 12px;
}
.my_readonly {
	background-color:#DCDCDC;
	border: 1px solid #808080;
}

</style>
<link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/docs/yffparaextDialog_spec.js"></script>  
  <script src="<%=basePath%>js/docs/docper.js"></script>
  <script src="<%=basePath%>js/common/gridopt.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script src="<%=basePath%>js/common/dateFormat.js"></script>
  <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
  <script src="<%=basePath%>js/validate.js"></script>
  <script src="<%=basePath%>js/common/initDate.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  <script src="<%=basePath%>js/common/loading.js"></script>
  <script src="<%=basePath%>js/common/jsonString.js"></script>
    
  <jsp:include page="../../jsp/ydjsdef.jsp"></jsp:include>
  </head>
  
  <body>
    <form action="" method="post" id="addorupdate"  style='display:inline;'>
  <br/>
  <table class="tabsty" align="center">
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">用户信息</td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td>户名:</td>
      <td nowrap><input class="my_readonly" type="text" id="cons_desc" style="width:160px;" readonly/></td>

      <td>户号:</td>
      <td nowrap><input type="text" id="cons_busi_no" style="width:160px;" /></td>

      <td>采集开关:</td>
      <td nowrap><input type="text" id="rtupoll_str" style="width:160px;" /></td>      
    </tr>

    <tr>
      <td>供电电源:</td>
      <td nowrap><input class="my_readonly" type="text" id="cons_line_str" style="width:160px;" readonly/></td>

	  <td>SIM卡号:</td>
   	  <td>
   	  <input type="text" id="rtu_simcard_str" readonly style="background-color:#DCDCDC;border: 1px solid #808080;" onDblClick="$('#selSimcard').click();" />&nbsp;&nbsp;<button id="selSimcard">...</button>
	  <input type="hidden" id="rtu_simcard_id" />
   	  </td>
   	  
    </tr>
    
    <tr>    
      <td>月均电量(万KWH):</td>
      <td nowrap><input type="text" id="ext_month_dl" style="width:160px;" /></td>    
     
      <td>月均电费(万元):</td>
      <td nowrap><input type="text" id="ext_month_money" style="width:160px;" /></td>         
     
      <td>协议电价(元):</td>
      <td nowrap><input class="my_readonly" type="text" id="zjgpay_feeproj_str" style="width:160px;" readonly/></td>              
    </tr>
     
    <tr>
      <td>用电类别:</td>
      <td nowrap><select id="ext_use_type" style="width:160px;"></select></td>

      <td>费控结算比(%):</td>
      <td nowrap><input type="text" id="ext_moneydl_per" style="width:160px;" /></td>              

      <td>倍率:</td>
      <td nowrap><input class="my_readonly" type="text" id="beilu" style="width:160px;" readonly/></td>

	</tr>

    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>

    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">现场设备</td>
          </tr>
        </table></td>
    </tr>

	<tr>
      <td>CT-A相编号:</td>
      <td nowrap><input type="text" id="ext_ctano" style="width:160px;" /></td>

      <td>CT-B相编号:</td>
      <td nowrap><input type="text" id="ext_ctbno" style="width:160px;" /></td>

      <td>CT-C相编号:</td>
      <td nowrap><input type="text" id="ext_ctcno" style="width:160px;" /></td>

	</tr>

	<tr>
      <td>电表厂家:</td>
      <td nowrap><input type="text" id="meter_factory" style="width:160px;" /></td>

      <td>电表编号:</td>
      <td nowrap><input type="text" id="meter_assetno" style="width:160px;" /></td>

      <td>电表精度:</td>
      <td nowrap><select id="ext_meter_accuracy" style="width:160px;"></select></td>
	</tr>
	
	<tr>
      <td>终端安装日期:</td>
      <td>	<input type="text" id="rtu_inst_dateF" style="width:160px;" readonly onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:'false',readOnly:'true',position:{button:'above'}});" />
	  <input type="hidden" id="rtu_inst_date" />
	  </td>
	
      <td>终端编号:</td>
      <td nowrap><input type="text" id="rtu_asset_no" style="width:160px;" /></td>	
	
      <td>设备加封标志:</td>
      <td nowrap><select id="ext_seal_flag" style="width:160px;" ></select> </td>	
	</tr>
	
	<tr>
		<td>PT变比:</td>
		<td>
		<input type="text" id="textpt" readonly="readonly" style="background-color:#DCDCDC;border: 1px solid #808080;"  ondblclick="editPTCT('PT')"/>&nbsp;<input type="button" value="..." onClick="editPTCT('PT')" style=" width:23px; height: 19px;"/>
	  	<input type="hidden" id="meter_ptnumerator" />
	  	<input type="hidden" id="meter_ptdenominator" />
	  	<input type="hidden" id="meter_ptratio" />	  	
		</td>
		
		<td>CT变比:</td>
		<td>
		<input type="text" id="textct" readonly="readonly" style="background-color:#DCDCDC;border: 1px solid #808080;"  ondblclick="editPTCT('CT')"/>&nbsp;<input type="button" value="..." onClick="editPTCT('CT')" style=" width:23px; height: 19px;"/>
	  	<input type="hidden" id="meter_ctnumerator" />
	  	<input type="hidden" id="meter_ctdenominator" />
	  	<input type="hidden" id="meter_ctratio" />
		</td>
	</tr>
	
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>	
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">控制开关</td>
          </tr>
        </table></td>
    </tr>	
	
	<tr>
      <td>控制电缆接引处:</td>
      <td colspan="5" nowrap><input type="text" id="ext_ctrlline_addr" style="width:99%;" /></td>	
	</tr>
	
	<tr>
      <td>控制负荷说明:</td>
      <td colspan="5" nowrap><input type="text" id="ext_ctrlfh_explain" style="width:99%;" /></td>	
	</tr>
	
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>	
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">客户联系方式</td>
          </tr>
        </table></td>
    </tr>
	
	<tr>
      <td>用电联系人姓名:</td>
      <td nowrap><input type="text" id="cons_fz_man" style="width:160px;" /></td>	

      <td>用电联系人电话:</td>
      <td nowrap><input type="text" id="cons_tel_no1" style="width:160px;" /></td>
	</tr>

	<tr>
      <td>财务联系人姓名:</td>
      <td nowrap><input type="text" id="ext_moneyman" style="width:160px;" /></td>	

      <td>财务联系人电话:</td>
      <td nowrap>
      <input type="text" id="ext_moneyman_telno1" style="width:160px;" />
      <!-- 隐藏域 -->
      <input type="hidden" id="rtuId" />
      <input type="hidden" id="zjgId" />
      <input type="hidden" id="mpId" />
      <input type="hidden" id="pollId" />
      </td>      
	</tr>
 </table>
  
  <center>
    <jsp:include page="../inc/btn_xiugai.jsp"></jsp:include>
    &nbsp;&nbsp;
    <jsp:include page="../inc/btn_guanbi.jsp"></jsp:include>
  </center>
</form>
<jsp:include page="../../jsp/inc/jsdef.jsp"></jsp:include>
<script type="text/javascript">
	var update_fail = "<i18n:message key="comm.gridopt.update_fail" />";
	var fjz = "<%=I18N.getText("doc.fjz","")%>";
	var gfhddz = "<i18n:message key="doc.gfhddz" />";
	var zdbdks = "<i18n:message key="doc.zdbdks" />";
	var zdbdjs = "<i18n:message key="doc.zdbdjs" />";
	var gfhsj = "<i18n:message key="doc.gfhsj" />";
	var fhmcbh = "<i18n:message key="doc.fhmcbh" />";
		
    var YDTable = {};
    SDDef.BASEPATH = "<%=basePath%>";
    SDDef.SPLITCOMA = "<%=SDDef.SPLITCOMA%>";
    Dict.DICTITEM_YESFLAG = "<%=Dict.DICTITEM_YESFLAG%>";
    Dict.DICTITEM_USEFLAG = "<%=Dict.DICTITEM_USEFLAG%>";
    
    //电表精度
    Dict.DICTITEM_METERACCURACY = "<%=Dict.DICTITEM_METERACCURACY%>";    
    //用电类别
    Dict.DICTITEM_POWER_USETYPE = "<%=Dict.DICTITEM_POWER_USETYPE%>";	
    </script>
  </body>
</html>
