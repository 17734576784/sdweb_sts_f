var params = window.dialogArguments;
var id     = params.id;
$(document).ready(function(){
	$("#reflesh").click(function(){
		reflesh();
	})
	reflesh()
	label_change();
});

//刷新触发事件
function reflesh(){
	var param = {};
	param.rtu_id = id.split("_")[0]
	param.zjg_id = id.split("_")[1];
	param.rtu  =  params.rtu;
	param.yyhh =  params.yyhh;
	param.yhmc =  params.yhmc;
	param.org  =  params.org;
	param.lxdh =  params.lxdh;
	var json_str = jsonString.json2String(param);
	loading.loading();
	$.post(def.basePath  + "ajaxspec/actSearch!yhztSearch.action",
		   {result : json_str},
		   function(data){
			   if(data.result==""){
				   alert("数据不存在");
				   return;
			   }
			   if (data.result != "" ) {
				   loading.loaded()
				   var json = eval('(' + data.result + ')');
				   var data = json.page[0].rows[0].data;
				   initTable(data);
			   }
		   }
	)
}

//初始化页面
function initTable(data){
	
	//字体颜色加粗变红
	var colors = "<b style='color:#800000'>";
	var colore = "</b>";
	
	
	var i=1;
	
	$("#describe").val(data[i++])
	$("#busi_no").val(data[i++]);//营业户号
	$("#rtu_id").val(data[i++] )
	
	$("#cacl_type").html(colors + data[i++] +colore);//计费方式
	$("#feectrl_type").html(colors + data[i++] +colore);//费控方式
	$("#pay_type").html(colors +data[i++] +colore);//缴费方式
	
	$("#pay_add1").html(colors + data[i++] + colore);//缴费附加值1
	$("#yffctrl_type").html(data[i++]);//预付费控制类型
	$("#tz_val").html(data[i++]);//透支值
	
	$("#feeproj1").html(colors + data[i++] + colore);//费率方案号1
	$("#feeproj2").html(data[i++]);//费率方案号2
	$("#feeproj3").html(data[i++]);//费率方案号3
	
	$("#yffalarm_id").html(colors + data[i++] + colore);//报警方案
	$("#prot_ed").html(data[i++]);//保电时间
	$("#ngloprot_flag").html(data[i++]);//不全局保电
	//新增字段
	$("#key_version").html(data[i++]);//密钥版本
	$("#cryplink_id").html(data[i++]);//所属加密机
	$("#writecard_no").html(data[i++]);//写卡户号
	$("#fee_begindate").html(data[i++]);//费率启用日期
	$("#cardmeter_type").html(data[i++]);//卡表类型
	
	$("#plus_time").html(data[i++]);//脉冲闭合时间
	$("#use_gfh").html(data[i++]);//启用高负荷控制
	$("#hfh_time").html(data[i++]);//连续高负荷时间
	$("#hfh_shutdown").html(data[i++]);//高负荷断电值
	
	
	$("#cs_stand").html(data[i++]);//功率因数标准
	$("#powrate_flag").html(data[i++]);//力调算费标志
	$("#prize_flag").html(data[i++]);//奖罚标志
	
	
	$("#stop_flag").html(data[i++]);//报停标志
	$("#stop_begdate").html(data[i++]);//报停开始日期
	$("#stop_enddate").html(data[i++]);//报停结束日期
	
	$("#cb_cycle_type").html(data[i++]);//抄表周期
	$("#cb_dayhour").html(data[i++]);//抄表日
	$("#cbjs_flag").html(data[i++]);
	$("#fxdf_flag").html(data[i++]);//是否发行电费
	
	$("#fxdf_begindate").html(data[i++]);//发行起始日期
	$("#local_maincalcf").html(data[i++]);//主站算费标志
	$("#fee_chgf").html(data[i++]);//费率更改标志

	$("#fee_chgContent").html(data[i++]);//费率更改内容
	$("#jbf_chgf").html(data[i++]);//基本费更改标志
	$("#jbf_chgContent").html(data[i++]);//基本率更改内容

	
	//预付参数结束,预付费状态开始
	
	
	$("#cus_state").html(colors + data[i++] + colore);//用户状态
	$("#op_type").html(colors + data[i++] + colore);//操作类型
	$("#op_time").html(data[i++]);//操作时间
	
	$("#pay_money").html(colors + data[i++] + colore);//缴费金额
	$("#pay_bmc").html(colors +data[i++] + colore);//购电量-表码差
	$("#alarm_val").html(colors + data[i++] + colore);//报警值1&2
	
	$("#shutdown_val").html(colors + data[i++] + colore);//断电金额
	$("#buy_times").html(colors + data[i++] + colore);//购电次数
	$("#now_remain").html(colors + data[i++] +colore);//当前剩余
	
	$("#total_gdz").html(colors + data[i++] + colore);//累计购电值
	$("#bj_bd").html(colors + data[i++] + colore);//报警门限
	$("#tz_bd").html(colors + data[i++] + colore);//跳闸门限
	
	$("#hb_time").html(colors + data[i++] +colore);//换表时间
	$("#kh_date").html(colors + data[i++] +colore);//开户日期
	$("#xh_date").html(colors + data[i++] +colore);//销户日期
	
	$("#jsbd_zyz").html(data[i++]);//结算总表底
	$("#jsbd_zyj").html(data[i++]);//结算尖表底
	$("#jsbd_zyf").html(data[i++]);//结算峰表底
	$("#jsbd_zyp").html(data[i++]);//结算平表底
	$("#jsbd_zyg").html(data[i++]);//结算谷表底
	$("#jsbd_zwz").html(data[i++]);//结算无功表底
	
	$("#jsbd_ymd").html(data[i++]);//结算时间
	$("#calc_mdhmi").html(data[i++]);//算费时间
	$("#calc_bdymd").html(data[i++]);//算费表底时间
	
	$("#calc_zyz").html(data[i++]);//算费总表底
	$("#calc_zyj").html(data[i++]);//算费尖表底
	$("#calc_zyf").html(data[i++]);//算费峰表底
	$("#calc_zyp").html(data[i++]);//算费平表底
	$("#calc_zyg").html(data[i++]);//算费谷表底
	$("#calc_zwz").html(data[i++]);//算费无功表底
	
	
	
	$("#total_money").html(colors + data[i++] + colore);//总电费
	$("#ele_money").html(colors + data[i++] + colore);//电度电费
	$("#jbf_money").html(colors + data[i++] + colore);//基本费电费
	$("#powrate_money").html(colors + data[i++] + colore);//力调电费
	$("#other_money").html(data[i++]);//其他电费
	
	$("#total_yzbdl").html(data[i++]);//有功追补电量
	$("#total_wzbdl").html(data[i++]);//无功追补电量
	$("#real_powrate").html(data[i++]);//实际功率因数
	
	$("#total_ydl").html(data[i++]);//有功累计电量
	$("#total_wdl").html(data[i++]);//无功累计电量
	$("#fxdf_iall_money").html(data[i++]);//发行电费当月缴费
	
	$("#zbele_money").html(data[i++]);//追补电度电费
	$("#zbjbf_money").html(data[i++]);//追补基本费
	$("#fxdf_remain").html(data[i++]);//发行电费剩余金额
	
	$("#fxdf_ym").html(data[i++]);//发行电费年月
	$("#fxdf_data_ymd").html(data[i++]);//发行电费数据日期
	$("#fxdf_calc_mdhmi").html(data[i++]);//发行电费算费时间
	
	$("#cs_al1_state").html(colors + data[i++] + colore);//报警1状态
	$("#cs_al2_state").html(colors + data[i++] + colore);//报警2状态
	$("#cs_fhz_state").html(colors + data[i++] + colore);//分合闸状态
		
	$("#al1_mdhmi").html(data[i++]);//报警1改变时间
	$("#al2_mdhmi").html(data[i++]);//报警2改变时间
	$("#fhz_mdhmi").html(data[i++]);//分合闸改变时间
	
	$("#yc_flag1").html(data[i++]);//异常标志1
	$("#yc_flag2").html(data[i++]);//异常标志2
	$("#fz_zyz").html(data[i++]);//分闸时总表底

	//预付费状态结束  报警及控制开始
	
	$("#qr_al1_1_state").html(data[i++]);//报警1短信
	$("#qr_al1_2_state").html(data[i++]);//报警1声音
	$("#qr_fhz_state").html(data[i++]);//分合闸确认
	
	$("#qr_al2_1_state").html(data[i++]);//报警2短信
	$("#qr_al2_2_state").html(data[i++]);//报警2声音
	$("#qr_fz_times").html(data[i++]);//分闸次数
	
	$("#qr_al1_1_mdhmi").html(data[i++]);//报警1短信时间
	$("#qr_al1_2_mdhmi").html(data[i++]);//报警1声音时间
	$("#qr_fhz_mdhmi").html(data[i++]);//分合闸发送时间
	
	$("#qr_al2_1_mdhmi").html(data[i++]);//报警2短信时间
	$("#qr_al2_2_mdhmi").html(data[i++]);//报警2声音时间
	$("#cg_fhz_mdhmi").html(data[i++]);//成功分合闸时间
	
	$("#qr_al1_1_uuid").html(data[i++]);//报警1短信UUID
	$("#qr_al2_1_uuid").html(data[i++]);//报警2短信UUID
	$("#out_info").html(data[i++]);//信息输出:
	
}

//按钮切换
function label_change(){
	$("#yffcs").click(function(){
		$("#d_yffcs").css("display","block");
		$("#d_yffzt").css("display","none");
		$("#d_bjkzcs").css("display","none");
		
		$("#yffcs_l").attr("class","titlel");
		$("#yffcs").attr("class","titlem");
		$("#yffcs_r").attr("class","titler");
		
		$("#yffzt_l").attr("class","titlel1");
		$("#yffzt").attr("class","titlem1");
		$("#yffzt_r").attr("class","titler1");
		
		$("#bjkzcs_l").attr("class","titlel1");
		$("#bjkzcs").attr("class","titlem1");
		$("#bjkzcs_r").attr("class","titler1");
	});
	
	$("#yffzt").click(function(){
		$("#d_yffcs").css("display", "none");
		$("#d_yffzt").css("display", "block");
		$("#d_bjkzcs").css("display","none");
		
		$("#yffcs_l").attr("class","titlel1");
		$("#yffcs").attr("class","titlem1");
		$("#yffcs_r").attr("class","titler1");
		
		$("#yffzt_l").attr("class","titlel");
		$("#yffzt").attr("class","titlem");
		$("#yffzt_r").attr("class","titler");
		
		$("#bjkzcs_l").attr("class","titlel1");
		$("#bjkzcs").attr("class","titlem1");
		$("#bjkzcs_r").attr("class","titler1");
	});
	
    $("#bjkzcs").click(function(){
		$("#d_yffcs").css("display", "none");
		$("#d_yffzt").css("display", "none");
		$("#d_bjkzcs").css("display","block");
		
		$("#yffcs_l").attr("class","titlel1");
		$("#yffcs").attr("class","titlem1");
		$("#yffcs_r").attr("class","titler1");
		
		$("#yffzt_l").attr("class","titlel1");
		$("#yffzt").attr("class","titlem1");
		$("#yffzt_r").attr("class","titler1");
		
		$("#bjkzcs_l").attr("class","titlel");
		$("#bjkzcs").attr("class","titlem");
		$("#bjkzcs_r").attr("class","titler");
	});	
}
