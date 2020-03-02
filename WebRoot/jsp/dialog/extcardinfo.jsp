<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>外接卡内信息</title>
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
	<table width="480" id="tbother" style="display:blank" align="center"><!-- 外接表 -->
	<tr bgcolor="#E7F8F2" align="center"><td width="15%">序号</td><td width="40%">数据项</td><td width="45%">数据项值</td></tr>
	<tr><td colspan =3 >用户信息</td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>用户编号</td><td id="other0"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>电卡类型</td><td id="other1"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>电表类型</td><td id="other2"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>预付费类别</td><td id="other3"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>区域码</td><td id="other4"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>表号</td><td id="other5"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>购电值</td><td id="other6"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>购电次数</td><td id="other7"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>CT</td><td id="other8"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>PT</td><td id="other9"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>操作员</td><td id="other10"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>售电时间</td><td id="other11"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>返写标志</td><td id="other12"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>累计购电值</td><td id="other13"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>累计用电量</td><td id="other14"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>返写购电次数</td><td id="other15"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>剩余值</td><td id="other16"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>故障状态</td><td id="other17"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>透支值</td><td id="other18"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>密钥状态</td><td id="other19"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>密钥更新方式</td><td id="other20"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>密钥条数</td><td id="other21"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>密钥版本</td><td id="other22"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>非法卡插入次数</td><td id="other23"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>过负荷跳闸次数</td><td id="other24"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>编程次数</td><td id="other25"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>清零次数</td><td id="other26"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>时钟设置次数</td><td id="other27"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>表尾端子盖打开次数</td><td id="other28"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>最大负荷</td><td id="other29"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>最大负荷发生时间</td><td id="other30"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>返写日期时间</td><td id="other31"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>当前有功累计总用电量</td><td id="other32"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>当前有功尖费率累计用电量</td><td id="other33"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>当前有功峰费率累计用电量</td><td id="other34"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>当前有功平费率累计用电量</td><td id="other35"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>当前有功谷费率累计用电量</td><td id="other36"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>最近一次有功冻结日总</td><td id="other37"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>最近一次有功冻结日尖</td><td id="other38"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>最近一次有功冻结日峰</td><td id="other39"></td></tr>
	
	<tr><td class="td_ali"><%=i++%></td><td>最近一次有功冻结日平</td><td id="other40"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>最近一次有功冻结日谷</td><td id="other41"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>新费率表启动日期</td><td id="other42"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第一套费率阶梯数</td><td id="other43"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第一套费率阶梯1</td><td id="other44"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第一套费率阶梯2</td><td id="other45"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第一套费率阶梯3</td><td id="other46"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第一套费率阶梯4</td><td id="other47"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第一套费率阶梯5</td><td id="other48"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第一套费率阶梯6</td><td id="other49"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第一套费率阶梯7</td><td id="other50"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第一套费率阶梯8</td><td id="other51"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第一套费率费率1</td><td id="other52"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第一套费率费率2</td><td id="other53"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第一套费率费率3</td><td id="other54"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第一套费率费率4</td><td id="other55"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第一套费率费率5</td><td id="other56"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第一套费率费率6</td><td id="other57"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第一套费率费率7</td><td id="other58"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第一套费率费率8</td><td id="other59"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第二套费率阶梯数</td><td id="other60"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第二套费率阶梯1</td><td id="other61"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第二套费率阶梯2</td><td id="other62"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第二套费率阶梯3</td><td id="other63"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第二套费率阶梯4</td><td id="other64"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第二套费率阶梯5</td><td id="other65"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第二套费率阶梯6</td><td id="other66"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第二套费率阶梯7</td><td id="other67"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第二套费率阶梯8</td><td id="other68"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第二套费率费率1</td><td id="other69"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第二套费率费率2</td><td id="other70"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第二套费率费率3</td><td id="other71"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第二套费率费率4</td><td id="other72"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第二套费率费率5</td><td id="other73"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第二套费率费率6</td><td id="other74"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第二套费率费率7</td><td id="other75"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>第二套费率费率8</td><td id="other76"></td></tr>
	<tr><td colspan =3 style="border: 0"></td></tr>
	</table>
	</div>
	</body>
	<script type="text/javascript">
	
	$(document).ready(function(){
		setTimeout("ret_card()",100);
		loading.loading();
	});
	
	
	//对读取的卡中的日期进行格式化
	//data:卡中读取的日期时间值。type:格式化形式
	function timeFormat(data,type){
		if(!data){
			return '';
		}
		
		if(typeof(data) == 'number'){
			data = data.toString();
		}
		
		var data_nums = data.length;
		var nums = type.length;
		var back_data = '';
		var year = '';
		
		//如果卡中所得到的数据与标准格式长度不一致，则在数据前补0
		if(data_nums < nums){
			var diff_num = nums - data_nums;
			var add0 = '';
			for(var j=0; j<diff_num; j++){
				add0 += '0';
			}
			data = add0 + data;
		}
		
		if(type=='YYMMDDHHMISS'){
			year = data.substring(0,2);
			year = (year == '00' ? year : "20" + year);
			back_data = year + '年' + data.substring(2,4) + '月' + data.substring(4,6) + '日' + data.substring(6,8) + '时' + data.substring(8,10) + '分' + data.substring(10,12) + '秒 '; 
		}
		else if(type=='MMDDHHMI'){
			back_data = data.substring(0,2) + '月' + data.substring(2,4) + '日' + data.substring(4,6) + '时' + data.substring(6,8) + '分';
		}
		else if(type=='YYYYMMDDHHMI'){
			back_data = data.substring(0,4) + '年' + data.substring(4,6) + '月' + data.substring(6,8) + '日' + data.substring(8,10) + '时' + data.substring(10,12) + '分';
		}
		else if(type=='YYMMDDHHMI'){
			year = data.substring(0,2);
			year = (year == '00' ? year : "20" + year);
			back_data = data.substring(0,2) + '年' + data.substring(2,4) + '月' + data.substring(4,6) + '日' + data.substring(6,8) + '时' + data.substring(8,10) + '分';
		}
		else{
		
		}
		return back_data;
	}
	
	
	function ret_card(){
		var jobj_out = card_comm.readExtcard();
		loading.loaded();
		if (jobj_out.errno == "0") {

			var tmp = jobj_out.strinfo.split("|");
			var temp_value = "", ymd = '', hms = '';
			
			for(var i = 0; i < tmp.length; i++){
				//电卡类型
				if(i==1){
					var card_type = '';
					//如果为FF则会发生转换错误,按16进制转换-----
					if(tmp[i].substring(1,2) == 'FF')
						card_type = 0xFF;
					card_type = parseInt(tmp[i].substring(1,2));//01 开户 ,02 购电 ,03 补卡
					temp_value = "[" + tmp[i] + "]" + card_comm.getExtCardTp(card_type);
				}
				
				//外接卡日期时间处理，这里不再提出为公共方法
				//售电时间--YYMMDDHHMISS
				else if(i==11){
					temp_value = timeFormat(tmp[i],'YYMMDDHHMISS');
				}
				//最大负荷发生时间--MMDDHHMI
				else if(i==30){
					temp_value = timeFormat(tmp[i],'MMDDHHMI'); 
				}
				//返写日期时间--YYYYMMDDHHMI
				else if(i==31){
					temp_value = timeFormat(tmp[i],'YYYYMMDDHHMI');
				}
				//新费率表启动日期--YYMMDDHHMI
				else if(i==42){
					temp_value = timeFormat(tmp[i],'YYMMDDHHMI');
				}
				else{
					temp_value = tmp[i];
				}
				$("#other"+ i).html(temp_value);
			}
		}
	}

	</script>
</html>
