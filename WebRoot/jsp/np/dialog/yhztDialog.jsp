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
    <title>总加组费控参数详细信息</title>
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
.d_cl{
	border-top: 3px solid #40a6a4;
}
</style>
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/dhtmlx/dhtmlxgrid.css">
  <link rel="STYLESHEET" type="text/css" href="<%=basePath%>css/zh/cont.css">
  <script src="<%=basePath%>js/jquery-1.4.2.min.js"></script>
  <script src="<%=basePath%>js/dyjc/dialog/yhztDialog.js"></script>
  <script src="<%=basePath%>js/common/gridopt.js"></script>
  <script src="<%=basePath%>js/common/def.js"></script>
  <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
  <script src="<%=basePath%>js/validate.js"></script>
  <script src="<%=basePath%>js/common/modalDialog.js"></script>
  <script src="<%=basePath%>js/common/loading.js"></script>
  <script src="<%=basePath%>js/common/jsonString.js"></script>
  </head>
  <body>
  <br/>
  <table style="font-size: 12px; width:95%" cellpadding="0" cellspacing="0" align="center">
  <tr>
  <td>
  	<table cellpadding="0" cellspacing="0" width="100%">
  	<tr>
	  	<td class="titlel" id="yffcs_l"></td>
	  	<td class="titlem" style="width: 100px;" id="yffcs">预付费参数</td>
	  	<td class="titler" id="yffcs_r"></td>
	  	<td width="5"></td>
	  	<td class="titlel1" id="yffzt_l"></td>
	  	<td class="titlem1" style="width: 100px;" id="yffzt">预付费状态</td>
	  	<td class="titler1" id="yffzt_r"></td>
	  	<td width="5"></td>
	  	<td class="titlel1" id="bjkzcs_l"></td>
	  	<td class="titlem1" style="width: 100px;" id="bjkzcs">报警及控制参数</td>
	  	<td class="titler1" id="bjkzcs_r"></td>
	  	<td colspan=5 style="text-align:right; border: 0px;">
	    	<button id="reflesh" class="btn" >刷新</button>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<button id="exit" class="btn" onclick="window.close();">关闭</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    </td>
  	</tr>
  	</table>
  </td>
  </tr>
  </table>
  <div id="d_yffcs" class="d_cl">
  <table class="tabsty" align="center">
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">基本信息</td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td width="12%"><font color=red>*</font>终端名称:</td><td width="20%" id=describe></td>
      <td width="12%">客户编号:      </td><td  width="18%"  id=cons_no></td> 
      <td width="12%">测量点名称: </td><td width="18%"  id=mp_id></td>
    </tr>
    <tr>
      <td>ESAM表号:          </td><td id=meter_id ></td>
      <td>客户名称:</td><td id=cons_desc ></td>
      <td></td><td></td>
    </tr>
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">费控参数</td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td>计费方式: </td><td id=cacl_type></td>
      <td>费控方式: </td><td id=feectrl_type></td>
      <td>缴费方式: </td><td id=pay_type></td>
    </tr>
    <tr>
      <td>预付费表类型: </td><td id=yffmeter_type></td>
      <td>透支值: </td><td id=tz_val></td>
      <td>费率方案号: </td><td id=feeproj_id></td>
    </tr>
    <tr>
      <td>报警方案: </td><td id=yffalarm_id></td>
      <td>保电时间: </td><td id=prot_ed></td>
      <td>不全局保电:</td><td id=ngloprot_flag></td>
    </tr>
    
    <tr>
      <td>密钥版本: 	  </td><td id=key_version></td>
      <td>加密机ID:         </td><td id=cryplink_id></td>
      <td>费率启用日期:</td><td id=fee_begindate></td>
    </tr>
    <tr>
      <td>动力关联</td><td id=power_rela></td>
      <td></td><td></td>
      <td></td><td></td>
    </tr>
    
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>    
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">算费信息</td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td>抄表日:      </td><td id=cb_dayhour></td>
      <td>抄表周期: </td><td id=cb_cycle_type></td>
      <td>主站算费标志: </td><td id=local_maincalcf></td>
    </tr>
    <tr>
      <td>是否发行电费: </td><td id=fxdf_flag></td> 
      <td>发行起始日期: </td><td id=fxdf_begindate></td>
      <td>阶梯切换月日:   </td><td id=jt_cycle_md></td>
      
    </tr>
    <tr>
      <td>费率更改标志:   </td><td id=fee_chgf></td>
      <td>费率更改内容:   </td><td id=fee_chgContent></td>
      <td></td><td></td>
    </tr>
    </table>
  </div>
  <div id="d_yffzt" style="display: none;"  class="d_cl">
  <table class="tabsty" align="center">   
   <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">基本状态</td>
          </tr>
        </table></td>
   </tr>
   <tr>
      <td width="13%">客户状态: </td><td id=cus_state width="18%"></td>
      <td width="14%">操作类型: </td><td id=op_type width="14%"></td>
      <td width="16%">操作时间: </td><td id=op_time width="16%"></td>
    </tr>
    <tr>
      <td>缴费金额: </td><td id=pay_money></td>
      <td>断电金额: </td><td id=shutdown_val></td>
      <td>断电金额2: </td><td id=shutdown_val2></td>
    </tr>
    <tr>
      <td>购电次数: </td><td id=buy_times></td>
      <td>当前剩余: </td><td id=now_remain></td>
      <td>当前剩余2: </td><td id=now_remain2></td>
    </tr>
    <tr>
      <td>报警门限: </td><td id=bj_bd></td>
      <td>跳闸门限: </td><td id=tz_bd></td>
      <td>累计购电值: </td><td id=total_gdz></td>
    </tr>
    <tr>
      <td>开户日期: </td><td id=kh_date></td>
      <td></td><td></td>
      <td></td><td></td>
    </tr>
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>    
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">表底信息</td>
          </tr>
        </table></td>
    </tr>
    
    <tr>
      <td>结算总表底: </td><td id=jsbd_zyz></td>
      <td>结算尖表底: </td><td id=jsbd_zyj></td>
      <td>结算峰表底: </td><td id=jsbd_zyf></td>
    </tr>    
    <tr>
      <td>结算平表底: </td><td id=jsbd_zyp></td>
      <td>结算谷表底: </td><td id=jsbd_zyg></td>
      <td></td><td></td>
    </tr>    
    <tr>
      <td>结算时间:           </td><td id=jsbd_ymd></td>
      <td>算费时间:           </td><td id=calc_mdhmi></td>
      <td>算费表底时间: </td><td id=calc_bdymd></td>
    </tr>    
    <tr>
      <td>算费总表底: </td><td id=calc_zyz></td>
      <td>算费尖表底: </td><td id=calc_zyj></td>
      <td>算费峰表底: </td><td id=calc_zyf></td>
    </tr>
    <tr>
      <td>算费平表底: </td><td id=calc_zyp></td>
      <td>算费谷表底: </td><td id=calc_zyg></td>
      <td></td><td></td>
    </tr> 
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>    
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">电费信息</td>
          </tr>
        </table></td>
    </tr>
    
    <tr>
      <td>阶梯追补电量:      </td><td id=jt_total_zbdl></td>
      <td>阶梯累计用电量: </td><td id=jt_total_dl></td>
      <td>换表时间:                </td><td id=hb_time></td> 
    </tr>  
    <tr>
	  <td>发行电费当月缴费:    </td><td id=fxdf_iall_money></td>      
      <td>发行电费当月缴费2: </td><td id=fxdf_iall_money2></td>
      <td>阶梯上次自动切换日期:              </td><td id=jt_reset_ymd></td>
    </tr>
    <tr>
      <td>发行电费剩余金额:    </td><td id=fxdf_remain></td>
      <td>发行电费剩余金额2: </td><td id=fxdf_remain2></td>
      <td>阶梯切换执行时间:    </td><td id=jt_reset_mdhmi></td>
    </tr> 
    <tr>
      <td>发行电费年月: </td><td id=fxdf_ym></td>
      <td>发行电费数据日期: </td><td id=fxdf_data_ymd></td>
      <td>发行电费算费时间: </td><td id=fxdf_calc_mdhmi></td>
    </tr>
    <tr>
      <td colspan="6" style="border-left: 0px;border-right: 0px;border-bottom: 0px;"></td>
    </tr>    
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">控制状态</td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td>报警1状态: </td><td id=cs_al1_state></td>
      <td>报警2状态: </td><td id=cs_al2_state></td>
      <td>分合闸状态: </td><td id=cs_fhz_state></td>
    </tr> 
    <tr>
    
      <td>报警1改变时间: </td><td id=al1_mdhmi></td>
      <td>报警2改变时间: </td><td id=al2_mdhmi></td>
      <td>分合闸改变时间: </td><td id=fhz_mdhmi></td>
    </tr> 
    <tr>
      <td>异常标志1:</td><td id=yc_flag1></td>
      <td>异常标志2: </td><td id=yc_flag2></td>
      <td>分闸时总表底: </td><td id=fz_zyz></td>
    </tr>              
  </table>
  </div>
  <div id="d_bjkzcs" style="display: none;"  class="d_cl">
  <table class="tabsty" align="center">
    <tr>
      <td colspan="6" style="font-size: 12px;background: url('<%=basePath%>images/bg22.gif') repeat-x;"><table cellpadding="0" cellspacing="0">
          <tr>
            <td style="border: 0px;"><img src="<%=basePath%>images/doctitle.png" style="padding-top: 3px;"/></td>
            <td style="border: 0px; font-size: 12px;">报警及控制参数</td>
          </tr>
        </table></td>
    </tr>     
    <tr>
      <td width="10%">报警1短信: </td><td  width="16%" id=qr_al1_1_state></td>
      <td width="10%">报警1声音: </td><td  width="16%" id=qr_al1_2_state></td>
      <td width="12%">分合闸确认: </td><td  width="16%" id=qr_fhz_state></td>
      
    </tr>  
    <tr>
      <td>报警2短信: </td><td id=qr_al2_1_state></td>
      <td>报警2声音: </td><td id=qr_al2_2_state></td>
      <td>分闸次数: </td><td id=qr_fz_times></td>
    </tr>          

    <tr>
      <td>报警1短信时间: </td><td id=qr_al1_1_mdhmi></td>
      <td>报警1声音时间: </td><td id=qr_al1_2_mdhmi></td>
      <td>分合闸发送时间: </td><td id=qr_fhz_mdhmi></td>
    </tr>  
    
    <tr>
      <td>报警2短信时间: </td><td id=qr_al2_1_mdhmi></td>
      <td>报警2声音时间: </td><td id=qr_al2_2_mdhmi></td>
      <td>成功分合闸时间: </td><td id=cg_fhz_mdhmi></td>
    </tr>  
    
    <tr>
      <td>报警1短信UUID: </td><td id=qr_al1_1_uuid></td>
      <td>报警2短信UUID: </td><td id=qr_al2_1_uuid></td>
      <td>信息输出: </td><td id=out_info></td>
    </tr>  
  </table>
  </div>
  </body>
</html>
