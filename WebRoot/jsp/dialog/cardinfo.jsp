<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>卡内信息</title>
	</head>
	<link href="<%=basePath%>css/zh/page.css" rel="stylesheet" type="text/css" id="cont" />
	<style type="text/css">
	.tbl tr{
		event:expression(
		onmouseover = function(){this.style.backgroundColor='#99FFCC'},
		onmouseout = function(){this.style.backgroundColor=''}
		)
	}
	.td_ali{
		text-align: center;
	}
	</style>
	<script src="<%=basePath%>/js/jquery-1.4.2.min.js"></script>
	<script  src="<%=basePath%>js/common/def.js"></script>
	<script  src="<%=basePath%>js/common/card_comm.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script  src="<%=basePath%>js/common/jsonString.js"></script>
	<body>
	
	<OBJECT id="YFFWEBCTRL" name="YFFWEBCTRL"  width="0" height="0"
	CLASSID="CLSID:9A285C16-B8AB-435C-B05D-693261E7DAAC"
	CODEBASE="libyffwebctrl.dll#version=1,0,0,1">
	</object>

	<%
	int i = 1;
	%>
	<div class="tbl">
	<table width="480" id="tbznb" style="display:none" align="center"><!-- 智能表 -->
	<tr bgcolor="#E7F8F2" align="center"><td width="15%">序号</td><td width="40%">数据项</td><td width="45%">数据项值</td></tr>
	<tr><td colspan =3 >用户信息</td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>电表类型</td><td id="zn0"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率类型</td><td id="zn1"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>参数更新标志位</td><td id="zn2"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>分时日期</td><td id="zn3"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>分时时间</td><td id="zn4"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>报警值1</td><td id="zn5"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>报警值2</td><td id="zn6"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>CT</td><td id="zn7"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>PT</td><td id="zn8"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>ESAM表号</td><td id="zn9"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>客户编号</td><td id="zn10"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>电卡类型</td><td id="zn11"></td></tr>
	<tr><td colspan =3 >钱包信息</td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>上次缴费金额</td><td id="zn12"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>购电次数</td><td id="zn13"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率1总费率</td><td id="zn14"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率1尖费率</td><td id="zn15"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率1峰费率</td><td id="zn16"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率1平费率</td><td id="zn17"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率1谷费率</td><td id="zn18"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率2总费率</td><td id="zn19"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率2尖费率</td><td id="zn20"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率2峰费率</td><td id="zn21"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率2平费率</td><td id="zn22"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率2谷费率</td><td id="zn23"></td></tr>
	<tr><td colspan =3 >返写信息</td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>用户类型(01 单费率 02 复费率 )</td><td id="zn24"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>CT</td><td id="zn25"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>PT</td><td id="zn26"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>ESAM表号</td><td id="zn27"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>客户编号</td><td id="zn28"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>剩余金额(元)</td><td id="zn29"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>购电次数</td><td id="zn30"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>透支金额</td><td id="zn31"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>密钥状态</td><td id="zn32"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>更新方式</td><td id="zn33"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>标识</td><td id="zn34"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>密钥版本</td><td id="zn35"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>非法插卡次数</td><td id="zn36"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>返写日期</td><td id="zn37"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>返写时间</td><td id="zn38"></td></tr>
	<tr><td colspan =3 style="border: 0"></td></tr>
	</table>
	
	<table width="480" id="tb6103" style="display:none" align="center" ><!-- 6103 -->
	<%i=1;%>
	<tr bgcolor="#E7F8F2" align="center"><td width="15%">序号</td><td width="40%">数据项</td><td width="45%">数据项值</td></tr>
	
	<tr><td colspan =3 >参数信息</td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>卡表类型</td>	 <td id="610"></td></tr>   
	<tr><td class="td_ali"><%=i++%></td><td>电卡类型</td> 	 <td id="611"></td></tr>   
	<tr><td class="td_ali"><%=i++%></td><td>客户编号</td>   <td id="612"></td></tr>     
	<tr><td class="td_ali"><%=i++%></td><td>报警值1</td>   <td id="613"></td></tr>     
	<tr><td class="td_ali"><%=i++%></td><td>报警值2</td>   <td id="614"></td></tr>     
	<tr><td class="td_ali"><%=i++%></td><td>购电量</td>  	 <td id="615"></td></tr>   
	<tr><td class="td_ali"><%=i++%></td><td>购电次数</td> 	 <td id="616"></td></tr>   
	<tr><td class="td_ali"><%=i++%></td><td>囤积限值</td>   <td id="617"></td></tr>     
	<tr><td class="td_ali"><%=i++%></td><td>倍率</td>    	 <td id="618"></td></tr>     

	<tr><td class="td_ali"><%=i++%></td><td>备用1</td> 	  <td id="619"></td></tr>   
	<tr><td class="td_ali"><%=i++%></td><td>备用2</td>     <td id="6110"></td></tr>     
	<tr><td class="td_ali"><%=i++%></td><td>备用3</td>     <td id="6111"></td></tr>     

	<tr><td colspan =3 >返写信息</td></tr>

	<tr><td class="td_ali"><%=i++%></td><td>费率类型</td>		<td id="6112"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>客户编号</td>		<td id="6113"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>累计用电量</td>		<td id="6114"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>剩余电量</td>		<td id="6115"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>累计购电量</td>		<td id="6116"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>本次购电量</td>		<td id="6117"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>购电次数</td>		<td id="6118"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>过零电量</td>		<td id="6119"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>非法插卡次数</td>	<td id="6120"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>电卡表状态字</td>	<td id="6121"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>脉冲常数</td>		<td id="6122"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>报警值1</td>			<td id="6123"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>报警值2</td>			<td id="6124"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>返写日期</td>		<td id="6125"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>返写时间</td>		<td id="6126"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>付费方式</td>		<td id="6127"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>倍率变比</td>		<td id="6128"></td></tr>
	<tr><td colspan =3 style="border: 0"></td></tr>
	</table>
	
	
	<table width="480" id="tbnp" style="display:none" align="center" ><!-- 农排 -->
	<%i=1;%>
	<tr bgcolor="#E7F8F2" align="center"><td width="15%">序号</td><td width="40%">数据项</td><td width="45%">数据项值</td></tr>
	
	<tr><td colspan =3 >参数信息</td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>卡表类型</td><td id="np0"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>区域号</td><td id="np1"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>卡号</td><td id="np2"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>余额(元)</td><td id="np3"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>购电次数</td><td id="np4"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>购电卡状态</td><td id="np11"></td></tr>
	<tr><td colspan =3 style="border: 0"></td></tr>
	</table>
	
	
	<table width="480" id="tbznb2" style="display:none" align="center"><!-- 智能表 2-->
	<%i=1;%>
	<tr bgcolor="#E7F8F2" align="center"><td width="15%">序号</td><td width="40%">数据项</td><td width="45%">数据项值</td></tr>
	<tr><td colspan =3 >用户信息</td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>电表类型</td><td id="new_zn0"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>参数更新标志位</td><td id="new_zn1"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>分时日期</td><td id="new_zn2"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>分时时间</td><td id="new_zn3"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>报警值1</td><td id="new_zn4"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>报警值2</td><td id="new_zn5"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>CT</td><td id="new_zn6"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>PT</td><td id="new_zn7"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>ESAM表号</td><td id="new_zn8"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>客户编号</td><td id="new_zn9"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>电卡类型</td><td id="new_zn10"></td></tr>
	<tr><td colspan =3 >钱包信息</td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>上次缴费金额</td><td id="new_zn11"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>购电次数</td><td id="new_zn12"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率1尖费率</td><td id="new_zn13"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率1峰费率</td><td id="new_zn14"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率1平费率</td><td id="new_zn15"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率1谷费率</td><td id="new_zn16"></td></tr>
	<!-- 阶梯信息 -->
	<tr><td class="td_ali"><%=i++%></td><td>费率1阶梯梯度值1</td><td id="new_zn18"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率1阶梯梯度值2</td><td id="new_zn19"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率1阶梯梯度值3</td><td id="new_zn20"></td></tr>
	
	<tr><td class="td_ali"><%=i++%></td><td>费率1阶梯费率1</td><td id="new_zn25"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率1阶梯费率2</td><td id="new_zn26"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率1阶梯费率3</td><td id="new_zn27"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率1阶梯费率4</td><td id="new_zn28"></td></tr>
	
	<tr><td class="td_ali"><%=i++%></td><td>费率1阶梯年结算日1</td><td id="new_zn33"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率1阶梯年结算日2</td><td id="new_zn34"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率1阶梯年结算日3</td><td id="new_zn35"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率1阶梯年结算日4</td><td id="new_zn36"></td></tr>
	
	<tr><td class="td_ali"><%=i++%></td><td>费率2尖费率</td><td id="new_zn40"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率2峰费率</td><td id="new_zn41"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率2平费率</td><td id="new_zn42"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率2谷费率</td><td id="new_zn43"></td></tr>
	<!-- 阶梯信息 -->
	<tr><td class="td_ali"><%=i++%></td><td>费率2阶梯梯度值1</td><td id="new_zn45"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率2阶梯梯度值2</td><td id="new_zn46"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率2阶梯梯度值3</td><td id="new_zn47"></td></tr>
	
	<tr><td class="td_ali"><%=i++%></td><td>费率2阶梯费率1</td><td id="new_zn52"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率2阶梯费率2</td><td id="new_zn53"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率2阶梯费率3</td><td id="new_zn54"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率2阶梯费率4</td><td id="new_zn55"></td></tr>
	
	<tr><td class="td_ali"><%=i++%></td><td>费率2阶梯年结算日1</td><td id="new_zn60"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率2阶梯年结算日2</td><td id="new_zn61"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率2阶梯年结算日3</td><td id="new_zn62"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>费率2阶梯年结算日4</td><td id="new_zn63"></td></tr>
	
	<tr><td class="td_ali"><%=i++%></td><td>阶梯切换日期</td><td id="new_zn65"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>阶梯切换时间</td><td id="new_zn66"></td></tr>
	<tr><td colspan =3 >返写信息</td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>CT</td><td id="new_zn67"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>PT</td><td id="new_zn68"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>ESAM表号</td><td id="new_zn69"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>客户编号</td><td id="new_zn70"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>剩余金额</td><td id="new_zn71"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>购电次数</td><td id="new_zn72"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>透支金额</td><td id="new_zn73"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>非法插卡次数</td><td id="new_zn74"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>返写日期</td><td id="new_zn75"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>返写时间</td><td id="new_zn76"></td></tr>
	<tr><td colspan =3 style="border: 0"></td></tr>
	</table>
	</div>
	</body>
	<script type="text/javascript">
	var SDDef = {};
	SDDef.YFF_METER_TYPE_ZNK	= "<%=SDDef.YFF_METER_TYPE_ZNK%>";
	SDDef.YFF_METER_TYPE_6103	= "<%=SDDef.YFF_METER_TYPE_6103%>";
	SDDef.YFF_METER_TYPE_NP		= "<%=SDDef.YFF_METER_TYPE_NP%>";
	SDDef.YFF_METER_TYPE_ZNK2	= "<%=SDDef.YFF_METER_TYPE_ZNK2%>";
	
	//不赋值dateFormat报错
	var params ={lang : "cn"};
	
	$(document).ready(function(){
		setTimeout("ret_card()",100);
		loading.loading();
	});
	
	var qz = "";//ID前缀
	
	function ret_card(){
		var jobj_out = card_comm.readcard();
		loading.loaded();
		if (jobj_out.errno == "0") {
			switch(jobj_out.meter_type){
			case  SDDef.YFF_METER_TYPE_ZNK: //智能卡
				$("#tbznb").attr("style","display:blank;");
				$("#tb6103").attr("style","display:none;");
				$("#tbnp").attr("style","display:none;");
				$("#tbznb2").attr("style","display:none;");
				qz = "zn";
				break;
			case SDDef.YFF_METER_TYPE_6103 : //6103
				$("#tbznb").attr("style","display:none;");
				$("#tb6103").attr("style","display:blank;");
				$("#tbnp").attr("style","display:none;");
				$("#tbznb2").attr("style","display:none;");
				qz = "61";
				break;
			case SDDef.YFF_METER_TYPE_NP : 	//农排
				$("#tbznb").attr("style","display:none;");
				$("#tb6103").attr("style","display:none;");
				$("#tbnp").attr("style","display:blank;");
				$("#tbznb2").attr("style","display:none;");
				qz = "np";
				break;	
			case SDDef.YFF_METER_TYPE_ZNK2: //智能卡2
				$("#tbznb").attr("style","display:none;");
				$("#tb6103").attr("style","display:none;");
				$("#tbnp").attr("style","display:none;");
				$("#tbznb2").attr("style","display:blank;");
				qz = "new_zn";
				break;			
			}
			var tmp = jobj_out.strinfo.split("|");
			
			for(var i = 0; i < tmp.length; i++){
				//20131021新增ke006
				if(qz=="new_zn"){
					if(i==1){
						$("#"+ qz + i).html(parseInt(tmp[i],10).toString(16) + "H");
					}
					else{
						if(i==17 || i==44 || (i>20&&i<25)|| (i>29&&i<33) || (i>36&&i<40) || (i>47&&i<52) ||(i>55&&i<60) || i==64) continue;
						//卡内购电金额/100为实际购电金额
						//反写金额/100
						if(i==11 || i==71){
							tmp[i] = tmp[i]/100;
						}
						
						//卡中费率/10000得到实际费率
						//数组中元素对应的是显示内容在卡中的位置
						var fees = [13,14,15,16,25,26,27,28,40,41,42,43,52,53,54,55];
						var jt_val = [18,19,20,45,46,47];
						
						for(var j=0, l= fees.length; j<l; j++){
							if(i == fees[j]){
							 tmp[i] = tmp[i]/10000;
							 break;
							}
						}
						for(var j=0, l= jt_val.length; j<l; j++){
							if(i == jt_val[j]){
							 tmp[i] = tmp[i]/100;
							 break;
							}
						}
						
						//卡中读取的日期时间格式化
						var jt_jsday = [33,34,35,36,60,61,62,63];
						for(var j=0, l= jt_jsday.length; j<l; j++){
							if(i == jt_jsday[j]){
							 tmp[i] = formatMDH(tmp[i],'MDH');
							 break;
							}
						}
						
						//110101--->2011年01月01日
						if(i == 65 || i == 75){
							tmp[i] = formatMDH(tmp[i],'YMD');
						}
						//0830格式化为日期格式08时30分
						if(i == 66){
							dateFormat.date = tmp[i];
							tmp[i] = dateFormat.formatToHM(0);
						}
						//11420格式化为1时14分20秒
						if(i == 76){
							dateFormat.date = tmp[i];
							tmp[i] = dateFormat.formatToHMS(0);
						}
						$("#"+ qz + i).html(tmp[i]);
					}
				}
				//智能卡
				else if(qz=="zn" && i==2){
					$("#"+ qz + i).html(parseInt(tmp[i],10).toString(16) + "H");
				}
				//ke001上次缴费金额(元)
				else if(qz=="zn" && i==12){
					$("#"+ qz + i).html(parseInt(tmp[i])/100);
				}
				//ke001剩余金额(元)
				else if(qz=="zn" && i== 29){
					$("#"+ qz + i).html(parseInt(tmp[i])/100);
				}
				//ke005剩余金额(元)
				else if(qz=="np" && i == 3 ){
					$("#"+ qz + i).html(parseInt(tmp[i])/100);
				}
				//其他
				else{
					$("#"+ qz + i).html(tmp[i]);
				}
			}
			if(jobj_out.meter_type == SDDef.YFF_METER_TYPE_NP) {
				var state = tmp[11] == "0" ? "正常" : "灰锁";
				$("#np11").html(state);
			}
		}
	}
	</script>
	<script  src="<%=basePath%>js/common/dateFormat.js"></script>
</html>
