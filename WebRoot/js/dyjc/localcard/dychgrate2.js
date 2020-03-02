var rtnValue;
var jsonFLFA;
var plsz = -1;//批量设置
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };
//var jt_cycle_md = '0';	//阶梯年结算日

$(document).ready(function(){
	$.post(def.basePath + "ajaxdyjc/actChgRate!getRateList.action",{},function(data){
		if(data.result != ""){
			jsonFLFA = eval('(' + data.result + ')');
			for(var i=0;i<jsonFLFA.rows.length;i++){
					$("#xflfa").append("<option value="+jsonFLFA.rows[i].value+">"+jsonFLFA.rows[i].text+"</option>");
			} 
			changeFLFA();
		}
	});
	
	dateFormat.date = dateFormat.getToday("yMd");
	var datestr 	= dateFormat.formatToYMD(0);
	dateFormat.date = dateFormat.getToday("H")+"00";
	datestr 		= datestr + " " + dateFormat.formatToHM(0);
	$("#qhsj").val(datestr);
	$("#xqhsj").val(datestr);
	$("#jt_qhsj").val(datestr);
	$("#jt_xqhsj").val(datestr);

	$("#btnRead").click(function(){read_card()});
	$("#cardinfo").click(function(){window.top.card_comm.cardinfo()});
	
	$("#btnSearch").click(function(){selcons()});
	$("#xflfa").change(function(){changeFLFA()});//新费率方案 下拉框	
	$("#btnReadFee").click(function(){readFee()});//读取费率文件
	$("#btnReadFeelv").click(function(){readFeeMeter()});//读取费率
	
	
	$("#btnSetFee").click(function(){setFee()});//设置费率
	$("#btnReadSwitchTime").click(function(){readSwitchTime()});//读切换时间
	$("#btnSetSwitchTime").click(function(){setSwitchTime()});//设切换时间
	$("#jt_btnReadSwitchTime").click(function(){readJTSwitchTime()});	//读阶梯切换时间
	$("#jt_btnSetSwitchTime").click(function(){setJTSwitchTime()});		//设阶梯切换时间
	
	$("#btnChangeFL").click(function(){changeFL()});//保存新费率
	$("#btnSearch").focus();
	setDisabled(true);
});

function read_card(){		//读卡
	loading.tip = "正在读卡,请稍后...";
	loading.loading();
	setTimeout("window.top.card_comm.readCardSearchDy(" + ComntUseropDef.YFF_DYOPER_CHANGERATE + ")", 10);
}

function setSearchJson2Html(js_tmp) {
	loading.loaded();
	if(!js_tmp)return;
	if(!window.top.card_comm.checkInfo(js_tmp)) return;
	rtnValue = js_tmp;
	$("#writecard_no").html(rtnValue.writecard_no);
	setcons1();
}

function selcons(){//检索
	var tmp = doSearch("ks",ComntUseropDef.YFF_DYOPER_CHANGERATE,rtnValue);
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	$("#writecard_no").html(rtnValue.writecard_no);
	setcons1();
}

function setcons1() {
	//20131021添加
	//判断表类型，若是旧表，提示到旧表的更改费率界面进行操作
	if(!is2013Meter(rtnValue.yffmeter_type)){
		alert('此用户表规约类型为2009版，请到"更改费率2009版"界面进行操作');
		document.location.reload();
		return;
	}
	
	setConsValue(rtnValue);
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno};
	mis_query(param,gloQueryResult);
	setDisabled(false);
}

function changeFL(){//保存新费率
	
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	
	var params = {};
	params.rtu_id = rtu_id;
	params.mp_id0 = mp_id;
	params.mp_id1 = rtnValue.power_rela1
	params.mp_id2 = rtnValue.power_rela2

	params.chgid 	= $("#xflfa").val();
	var qhsj 		= $("#qhsj").val();
	params.chg_date = qhsj.substring(0,4) + qhsj.substring(5,7) + qhsj.substring(8,10);
	params.chg_time = qhsj.substring(12,14) + qhsj.substring(15,17)

	params.date		= params.chg_date;
	params.time 	= params.chg_time;
	
	params.bd_zy0   = 0;
	params.bd_zyj0  = 0;
	params.bd_zyf0  = 0;
	params.bd_zyp0  = 0;
	params.bd_zyg0  = 0;
	
	params.bd_zy1   = 0;
	params.bd_zyj1  = 0;
	params.bd_zyf1  = 0;
	params.bd_zyp1  = 0;
	params.bd_zyg1  = 0;
	
	params.bd_zy2   = 0;
	params.bd_zyj2  = 0;
	params.bd_zyf2  = 0;
	params.bd_zyp2  = 0;
	params.bd_zyg2  = 0;
	
	modalDialog.height = 250;
	modalDialog.width  = 300;
	modalDialog.url    = def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param  = { 
		showGSM : false,
		key		: ["客户编号","用户名称","所属供电所", "更换新费率"], 
		val		: [rtnValue.userno,rtnValue.username,rtnValue.orgname,$("#xflfa").find("option:selected").text()]
	};
	
	var o = modalDialog.show();
	if(!o.flag){
		return;
	}
	var feeproj_id  = $("#feeproj_id").val();
	if(params.chgid == feeproj_id){
		alert("费率方案相同，不需保存！");
		return;
	}
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			userData1		 : rtu_id,
			mpid 			 : mp_id,
			gsmflag 		 : 0,
			dyOperChangeRate : json_str,
			userData2 		 : ComntUseropDef.YFF_DYOPER_CHANGERATE
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				var colors = "<b style='color:#800000'>";
				var colore = "</b>";
				$("#feeproj_id").val(params.chgid);
				var text=$("#xflfa").find("option:selected").text();
				$("#feeproj_desc").html(colors+text+colore);
				$("#feeproj_detail").html(colors+$("#feeproj_detail_new").html()+colore);
				alert("保存新费率成功!");
			}
			else {
				alert("保存新费率失败!" + (data.operErr ? data.operErr : ''));
			}
		}
	);
}

//读取电表费率：分为分时费率和阶梯费率两部分。
//分时费率：ComntProtMsg.YFF_DY_CALL_FEI1
//阶梯费率：ComntProtMsg.YFF_DY_CALL_JTFEI
//paraType
function readFee(){
	//清空原有电表费率内容
	$("#fee_desc").html("");
	
	var rtu_id = $("#rtu_id").val();
	var mp_id  = $("#mp_id").val();
	
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	//定义对象，存储查询出来的电表费率信息。每个元素都为数组
	var feeobj = {};
	
	var params = {};
	if($("#fei1").attr("checked")){
		params.paratype = ComntProtMsg.YFF_DY_CALL_JTFEI1;//读取费率-一套阶梯费率
	}
	else if($("#fei2").attr("checked")){
		params.paratype = ComntProtMsg.YFF_DY_CALL_JTFEI2;//读取费率-二套阶梯费率	
	}
	else{
		alert("请选择第一套或者第二套费率!");
		return ;
	}
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag  : true,
			userData1	   : rtu_id,
			mpid 		   : mp_id,
			dyCommCallPara : json_str,
			userData2      : ComntUseropDef.YFF_DYCOMM_CALLPARA
		},
		function(data) {//回传函数			
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				var json = eval("(" + data.dyCommCallPara + ")");
				var fee_rate = json.fee_rate;	//尖峰平谷
				var jt_step  = json.jt_step;	//梯度
				var jt_rate	 = json.jt_rate;	//阶梯费率
				var jt_jsmdh = json.jt_jsmdh;	//阶梯年结算日
				
				feeobj.jt_step = jt_step;
				feeobj.jt_rate = jt_rate;
				feeobj.jt_jsmdh = jt_jsmdh;
				
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("读取阶梯费率成功!");	//这个改到info中显示。
				}
				else{
					window.top.addStringTaskOpDetail("读取阶梯费率成功!");
				}
				
				//读取费率信息
				if($("#fei1").attr("checked")){
					params.paratype = ComntProtMsg.YFF_DY_CALL_FEI1;//读取费率-一套费率
				}
				else if($("#fei2").attr("checked")){
					params.paratype = ComntProtMsg.YFF_DY_CALL_FEI2;//读取费率-二套费率	
				}
				json_str = jsonString.json2String(params);
				$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
						{
							firstLastFlag  : true,
							userData1	   : rtu_id,
							mpid 		   : mp_id,
							paraType	   : 1,
							dyCommCallPara : json_str,
							userData2      : ComntUseropDef.YFF_DYCOMM_CALLPARA
						},
						function(data){
							loading.loaded();
							window.top.addJsonOpDetail(data.detailInfo);
							if (data.result == "success") {
								var json = eval("(" + data.dyCommCallPara + ")");
								var fee_rate = json.fee_rate;	//尖峰平谷
								//feeobj.push(fee_rate);
								feeobj.fee_rate = fee_rate;
								fillFeeInfo(feeobj);
								if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
									alert("读取费率成功!");	//这个改到info中显示。
								}
								else{
									window.top.addStringTaskOpDetail("读取费率成功!");
								}
							}
							else{
								if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
									alert("读取费率失败!");
								}
								else{
									window.top.addStringTaskOpDetail("读取费率失败!");
								}
							}
						});
			}
			else {
				loading.loaded();
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("读取费率失败!");
				}
				else{
					window.top.addStringTaskOpDetail("读取费率失败!");
				}
			}
		}
	);
}

//分时费率：ComntProtMsg.YFF_DY_CALL_FEI1
//阶梯费率：ComntProtMsg.YFF_DY_CALL_JTFEI
//paraType
function readFeeMeter(){
	//清空原有电表费率内容
	$("#feelv_desc").html("");
	
	var rtu_id = $("#rtu_id").val();
	var mp_id  = $("#mp_id").val();
	
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	//定义对象，存储查询出来的电表费率信息。每个元素都为数组
	var feeobj = {};
	
	var params = {};
	if($("#feilv1").attr("checked")){
		params.datatype = ComntProtMsg.YFF_CALL_JTFEI1;//读取费率-一套阶梯费率
	}
	else if($("#feilv2").attr("checked")){
		params.datatype = ComntProtMsg.YFF_CALL_JTFEI2;//读取费率-二套阶梯费率	
	}
	else{
		alert("请选择第一套或者第二套费率!");
		return ;
	}
	params.ymd = 120403;
	var json_str = jsonString.json2String(params);
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag  : true,
			userData1	   : rtu_id,
			mpid 		   : mp_id,
			dyCommReadData : json_str,
			userData2      : ComntUseropDef.YFF_READDATA
		},
		function(data) {//回传函数	
			window.top.addJsonOpDetail(data.detailInfo);
		   if (data.result == "success") {
				var json = eval("(" + data.dyCommReadData + ")");
				var fee_rate = json.fee_rate;	//尖峰平谷
				var jt_step  = json.jt_step;	//梯度
				var jt_rate	 = json.jt_rate;	//阶梯费率
				var jt_jsmdh = json.jt_jsmdh;	//阶梯年结算日
				
				feeobj.jt_step = jt_step;
				feeobj.jt_rate = jt_rate;
				feeobj.jt_jsmdh = jt_jsmdh;
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("读取阶梯费率成功!");	//这个改到info中显示。
				}
				else{
					window.top.addStringTaskOpDetail("读取阶梯费率成功!");
				}
				
				//读取费率信息
				if($("#feilv1").attr("checked")){
					params.datatype = ComntProtMsg.YFF_CALL_FEI1;//读取费率-一套费率
				}
				else if($("#feilv2").attr("checked")){
					params.datatype = ComntProtMsg.YFF_CALL_FEI2;//读取费率-二套费率	
				}
				params.ymd = 120403;
				json_str = jsonString.json2String(params);
				$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
						{
							firstLastFlag  : true,
							userData1	   : rtu_id,
							mpid 		   : mp_id,
							dyCommReadData : json_str,
							userData2      : ComntUseropDef.YFF_READDATA
						},
						function(data){
							loading.loaded();
							window.top.addJsonOpDetail(data.detailInfo);
							if (data.result == "success") {
								var json = eval("(" + data.dyCommReadData + ")");
								var fee_rate = json.fee_rate;	//尖峰平谷
								//feeobj.push(fee_rate);
								feeobj.fee_rate = fee_rate;
								fillDbFeeInfo(feeobj);
								if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
									alert("读取费率成功!");	//这个改到info中显示。
								}
								else{
									window.top.addStringTaskOpDetail("读取费率成功!");
								}
							}
							else{
								if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
									alert("读取费率失败!");
								}
								else{
									window.top.addStringTaskOpDetail("读取费率失败!");
								}
							}
						});
			}
			else {
				loading.loaded();
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("读取费率失败!");
				}
				else{
					window.top.addStringTaskOpDetail("读取费率失败!");
				}
			}
		}
	);
}

//根据通讯信息，显示电表费率内容
function fillFeeInfo(feeObj){
	if(!feeObj)	$("#fee_desc").html("");
	else{
		//组装成信息字符串
		var fee_desc = "尖:{"+ feeObj.fee_rate[0]/10000 + "} 峰:{" + feeObj.fee_rate[1]/10000  + "} 平:{" + feeObj.fee_rate[2]/10000  +"} 谷:{" + feeObj.fee_rate[3]/10000  + "}";
		fee_desc += " 阶梯电价：梯度1:{" + feeObj.jt_rate[0]/10000  + "}[" + feeObj.jt_step[0]/100 +"],梯度2:{" + feeObj.jt_rate[1]/10000  + "}[" + feeObj.jt_step[1]/100+ "],梯度3:{" + feeObj.jt_rate[2]/10000  + "}[" + feeObj.jt_step[2]/100 +"],梯度4:{"+ feeObj.jt_rate[3]/10000  + "}";
		fee_desc += "阶梯年结算日[" + formatMDH(feeObj.jt_jsmdh[0],'MDH') + "],[" + formatMDH(feeObj.jt_jsmdh[1],'MDH') + "],[" + formatMDH(feeObj.jt_jsmdh[2],'MDH') + "],[" + formatMDH(feeObj.jt_jsmdh[3],'MDH') + "]"; 
		$("#fee_desc").html(fee_desc);
	}
	$("#btnSetFee").attr("disabled",false);
}

//根据通讯信息，显示电表费率内容
function fillDbFeeInfo(feeObj){
	if(!feeObj)	$("#feelv_desc").html("");
	else{
		//组装成信息字符串
		var fee_desc = "尖:{"+ feeObj.fee_rate[0]/10000 + "} 峰:{" + feeObj.fee_rate[1]/10000  + "} 平:{" + feeObj.fee_rate[2]/10000  +"} 谷:{" + feeObj.fee_rate[3]/10000  + "}";
		fee_desc += " 阶梯电价：梯度1:{" + feeObj.jt_rate[0]/10000  + "}[" + feeObj.jt_step[0]/100 +"],梯度2:{" + feeObj.jt_rate[1]/10000  + "}[" + feeObj.jt_step[1]/100+ "],梯度3:{" + feeObj.jt_rate[2]/10000  + "}[" + feeObj.jt_step[2]/100 +"],梯度4:{"+ feeObj.jt_rate[3]/10000  + "}";
		fee_desc += "阶梯年结算日[" + formatMDH(feeObj.jt_jsmdh[0],'MDH') + "],[" + formatMDH(feeObj.jt_jsmdh[1],'MDH') + "],[" + formatMDH(feeObj.jt_jsmdh[2],'MDH') + "],[" + formatMDH(feeObj.jt_jsmdh[3],'MDH') + "]"; 
		$("#feelv_desc").html(fee_desc);
	}
}

function setFee(){
	if($("#xflfa").val() == $("#feeproj_id").val()){
		alert("费率方案相同，不允许修改！");
		return;
		
	}		
	
	if(!confirm("确定要设置费率吗？")){
		return;
	} 
	doSetFee(ComntProtMsg.YFF_DY_FEE_Z,false);
}

//设置费率要进行两次设置：
//设置分时费率：params.paratype = ComntProtMsg.YFF_DY_FEE_BLOCK;
//设置阶梯费率：params.paratype = ComntProtMsg.YFF_DY_JTFEE_BLOCK;
function doSetFee(){
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	
	//分时费率传的参数
	var params = {
		paratype  :  ComntProtMsg.YFF_DY_FEE_BLOCK,
		fee_rate  :  '0,0,0,0,0',
		jt_step   :	 '0,0,0,0,0',     
		jt_rate   :  '0,0,0,0,0,0,0', 
		jt_jsmdh  :  '999999,999999,999999,999999',
		feeno  	  :  2,         	
	    jt_chgymd :  '990101',
	    jt_cghm   :  '0000'	
	};
	 
	
	//阶梯费率传的参数/
	var params_jt = {
		paratype  :  ComntProtMsg.YFF_DY_JTFEE_BLOCK,	
		fee_rate  :  '0,0,0,0,0',
		jt_step   :	 '0,0,0,0,0',     
		jt_rate   :  '0,0,0,0,0,0,0', 
		jt_jsmdh  :  '999999,999999,999999,999999',
		feeno     :  2,         	
		jt_chgymd :  '990101',
		jt_cghm   :  '0000'
	};
	
	//fee_rate到后台转化为数组[5],最后一个元素保留赋0
	//jt_step到后台转化为数组[7],最后四个元素保留赋0
	//jt_rate到后台转化为数组[8],最后四个元素保留赋0
	//jt_jsmdh到后台转化为数组[4],所有元素赋值一样
	var fee_rate = '',jt_step = '',jt_rate = '', jt_jsmdh = '';
	var new_feerate = getChgRate();	//获取下拉框中新选择的费率项的详细信息

	if(!new_feerate.feeType){
		alert("新费率配置有误");
		return;
	}
    /**
     *根据选择的费率类型,计算传入后台的数据
     * 如果为单费率0、复费率1、混合费率2	jt_step,jt_rate,jt_jsmdh全部为0
     */
	//单费率
	if(new_feerate.feeType == 0){
		fee_rate = new_feerate.val_rateZ + ',' + new_feerate.val_rateZ + ','
	   					+ new_feerate.val_rateZ + ',' + new_feerate.val_rateZ + ',0'; 
		params.fee_rate = fee_rate;	
	}
	//复费率
	else if(new_feerate.feeType == 1){
		fee_rate = new_feerate.val_rateJ + ',' + new_feerate.val_rateF + ','
					   	+ new_feerate.val_rateP + ',' + new_feerate.val_rateG + ',0';
		params.fee_rate = fee_rate;		
	}
	//混合费率
	else if(new_feerate.feeType == 2){
		var fee1 = '',fee2 = '', fee3 = '', fee4 = '';
		fee1 = new_feerate.rateh_1*new_feerate.rateh_bl1/100;
		fee2 = new_feerate.rateh_2*new_feerate.rateh_bl2/100;
		fee3 = new_feerate.rateh_3*new_feerate.rateh_bl3/100;
		fee4 = new_feerate.rateh_4*new_feerate.rateh_bl4/100;
		fee_rate_Z = fee1 + fee2 + fee3+ fee4;
		fee_rate = fee_rate_Z + ',' +fee_rate_Z + ','
	   					+ fee_rate_Z+ ',' +fee_rate_Z + ',0'; 
		params.fee_rate = fee_rate;		
	}
	//如果为阶梯费率3，混合阶梯费率4,fee_rate为0
	else if(new_feerate.feeType == 3){
		//dubr 20140117 阶梯年结算日   年度方案   赋值jt_cycle_md字段     月度方案 设为999999  表示无效
		if(new_feerate.meterfeeType == 0) {
			fee_rate = new_feerate.val_rateZ + ',' + new_feerate.val_rateZ + ','
	   					+ new_feerate.val_rateZ + ',' + new_feerate.val_rateZ + ',0'; 		
			params.fee_rate = fee_rate;
		}
		else if(new_feerate.meterfeeType == 1) {
			fee_rate = new_feerate.val_rateJ + ',' + new_feerate.val_rateF + ','
					   	+ new_feerate.val_rateP + ',' + new_feerate.val_rateG + ',0';
			params.fee_rate = fee_rate;	
		}
		else {
			if(new_feerate.ratejType == 0){
				params_jt.jt_jsmdh = rtnValue.jt_cycle_md + ',' + rtnValue.jt_cycle_md + ','+ rtnValue.jt_cycle_md + ',' + rtnValue.jt_cycle_md;	
			}	
			//阶梯电价		
			params_jt.jt_step = new_feerate.ratej_td1 + ',' + new_feerate.ratej_td2 + ',' 
								+ new_feerate.ratej_td3 + ','+ '0,0,0,0';
			params_jt.jt_rate = new_feerate.ratej_r1 + ',' + new_feerate.ratej_r2 + ','
								+ new_feerate.ratej_r3 + ',' + new_feerate.ratej_r4 + ',0,0,0,0';
		}
	}
	//混合阶梯,对阶梯费率进行计算
	else if(new_feerate.feeType == 4){
		if(new_feerate.meterfeehjType == 0) {
			fee_rate = new_feerate.val_rateZ + ',' + new_feerate.val_rateZ + ','
	   					+ new_feerate.val_rateZ + ',' + new_feerate.val_rateZ + ',0'; 		
			params.fee_rate = fee_rate;
		}
		else if(new_feerate.meterfeehjType == 1) {
			fee_rate = new_feerate.val_rateJ + ',' + new_feerate.val_rateF + ','
					   	+ new_feerate.val_rateP + ',' + new_feerate.val_rateG + ',0';
			params.fee_rate = fee_rate;	
		}
		else {
			if(new_feerate.ratehjType == 0){
				params_jt.jt_jsmdh = rtnValue.jt_cycle_md + ',' + rtnValue.jt_cycle_md + ','+ rtnValue.jt_cycle_md + ',' + rtnValue.jt_cycle_md;	
			}
			
			params_jt.jt_step = new_feerate.ratehj_hr1_td1 + ',' + new_feerate.ratehj_hr1_td2 + ',' 
								+ new_feerate.ratehj_hr1_td3 + ','+ '0,0,0,0';
			
			var jt_rate1='',jt_rate2='',jt_rate3='',jt_rate4='';
			
			//第二、三比例电价值固定
			var jt_ratebl2 = new_feerate.ratehj_hr2 * new_feerate.ratehj_bl2/100;
			var jt_ratebl3 = new_feerate.ratehj_hr3 * new_feerate.ratehj_bl3/100;
			
			//计算每一阶梯总电价:第一比例+第二比例+第三比例
			jt_rate1 = new_feerate.ratehj_hr1_r1 * new_feerate.ratehj_bl1/100 + jt_ratebl2 + jt_ratebl3;
			jt_rate2 = new_feerate.ratehj_hr1_r2 * new_feerate.ratehj_bl1/100 + jt_ratebl2 + jt_ratebl3;
			jt_rate3 = new_feerate.ratehj_hr1_r3 * new_feerate.ratehj_bl1/100 + jt_ratebl2 + jt_ratebl3;
			jt_rate4 = new_feerate.ratehj_hr1_r4 * new_feerate.ratehj_bl1/100 + jt_ratebl2 + jt_ratebl3;
			
			params_jt.jt_rate =  jt_rate1 + ',' + jt_rate2 + ',' + jt_rate3 + ',' + jt_rate4 + ',0,0,0,0';
	}	
	
}
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	
	//设置阶梯费率
	var json_str = jsonString.json2String(params_jt);
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag : true,
			userData1	  : rtu_id,
			mpid          : mp_id,
			dyCommSetPara : json_str,
			userData2     : ComntUseropDef.YFF_DYCOMM_SETPARA
		},
		function(data) {			    	//回传函数
			window.top.addJsonOpDetail(data.detailInfo);
			//设置费率成功
			if (data.result == "success") {	
				//如果详细信息小窗口是关闭状态，则弹出提示框进行提示
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					 alert("设置阶梯费率成功!");
				}
				else { //如果详细信息窗口是打开装态，则将提示信息填写到小窗口中
					window.top.addStringTaskOpDetail("设置阶梯费率成功!");
				}
				
				//设置分时费率
				json_str = jsonString.json2String(params);
				$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
						{
							firstLastFlag : true,
							userData1	  : rtu_id,
							mpid          : mp_id,
							dyCommSetPara : json_str,
							userData2     : ComntUseropDef.YFF_DYCOMM_SETPARA
						},
						function(data) {
							if (data.result == "success") {	
								loading.loaded();
								//如果详细信息小窗口是关闭状态，则弹出提示框进行提示
								if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
									 alert("设置费率成功!");
								}
								else { //如果详细信息窗口是打开装态，则将提示信息填写到小窗口中
									window.top.addStringTaskOpDetail("设置费率成功!");
								}
							}
						});
			}	
			else {//设置费率失败
				loading.loaded();
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("设置费率失败!");
				}
				else{
					window.top.addStringTaskOpDetail("设置费率失败!");
				}
			}
		}
	);
}

function readSwitchTime(){//读切换时间
	var rtu_id = $("#rtu_id").val();
	var mp_id  = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}	
	var params = {};
	params.paratype = ComntProtMsg.YFF_DY_CALL_PARA;//读切换时间	
	var json_str 	= jsonString.json2String(params);
	
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag  : true,
			userData1	   : rtu_id,
			mpid 		   : mp_id,
			dyCommCallPara : json_str,
			userData2      : ComntUseropDef.YFF_DYCOMM_CALLPARA
		},
		function(data) {//回传函数			
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				var json 		= eval("(" + data.dyCommCallPara + ")");
				var strDtTm 	="";
				
				dateFormat.date = "20" + json.chg_date;
				strDtTm 		= dateFormat.formatToYMD(0);
				strDtTm = strDtTm + " " +  formatMDH(json.chg_time+'','HM');
			
				$("#qhsj").val(strDtTm);
			
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("读分时费率切换时间成功!");
				}else{
					window.top.addStringTaskOpDetail("读分时费率切换时间成功!");
				}
			}
			else {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("读分时费率切换时间失败!");
				}else{
					window.top.addStringTaskOpDetail("读分时费率切换时间失败!");
				}
			}
		}
	);
}

//设置分时费率切换时间
function setSwitchTime(){//设切换时间
	var rtu_id = $("#rtu_id").val()
	var mp_id  = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}

	var params = {};
	params.rtu_id = rtu_id;
	params.mp_id  = mp_id;
	params.paratype   = ComntProtMsg.YFF_DY_SET_CHGTIME;
	params.user_type  = $("#user_type").val();
	params.bit_update = $("#bit_update").val();
	
	var qhsj = $("#xqhsj").val();
	params.chg_date   = qhsj.substring(2,4)   + qhsj.substring(5,7)   + qhsj.substring(8,10);
	params.chg_time   = qhsj.substring(12,14) + qhsj.substring(15,17) +"00"
	params.alarm_val1 = $("#alarm_val1").val();
	params.alarm_val2 = $("#alarm_val2").val();
	params.pt = $("#pt").val();
	params.ct = $("#pct").val();
	params.userno  = rtnValue.userno;
	params.meterno = rtnValue.esamno;
	
	var json_str   = jsonString.json2String(params);
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag : true,
			userData1	  : rtu_id,
			mpid 		  : mp_id,
			dyCommSetPara : json_str,
			userData2 	  : ComntUseropDef.YFF_DYCOMM_SETPARA
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("设置分时费率切换时间成功!");
				}else{
					window.top.addStringTaskOpDetail("设置分时费率切换时间成功!");
				}
			}
			else {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("设置分时费率切换时间失败!");
				}else{
					window.top.addStringTaskOpDetail("设置分时费率切换时间失败!");
				}
			}
		}
	);
	
}

//读阶梯费率切换时间(第2套)
function readJTSwitchTime(){
	var rtu_id = $("#rtu_id").val();
	var mp_id  = $("#mp_id").val();
	
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}	
	
	var params = {};
	params.paratype = ComntProtMsg.YFF_DY_CALL_JTFEI2;//读取费率-二套费率	
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag  : true,
			userData1	   : rtu_id,
			mpid 		   : mp_id,
			dyCommCallPara : json_str,
			userData2      : ComntUseropDef.YFF_DYCOMM_CALLPARA
		},
		function(data) {//回传函数			
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				var json = eval("(" + data.dyCommCallPara + ")");
				var strDtTm 	="";
				dateFormat.date = "20" + json.jt_chgymd;
				strDtTm 		= dateFormat.formatToYMD(0);
				strDtTm = strDtTm + " " +  formatMDH(json.jt_cghm + '','HM');
				
				$("#jt_qhsj").val(strDtTm);
				
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("读阶梯费率切换时间成功!");	//这个改到info中显示。
				}
				else{
					window.top.addStringTaskOpDetail("读阶梯费率切换时间成功!");
				}
			}
			else {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("读阶梯费率切换时间失败!");
				}
				else{
					window.top.addStringTaskOpDetail("读阶梯费率切换时间失败!");
				}
			}
		});
}

//设置阶梯费率切换时间
function setJTSwitchTime(){
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	
	var params = {};
	params.rtu_id 	 = rtu_id;
	params.mp_id  	 = mp_id;
	params.feeno  	 = 2;		//只设置第二套费率
	params.paratype  = ComntProtMsg.YFF_DY_SET_JTCHGTIME;

	var qhsj = $("#jt_xqhsj").val();
	params.jt_chgymd   = qhsj.substring(2,4)   + qhsj.substring(5,7)   + qhsj.substring(8,10);
	params.jt_cghm     = qhsj.substring(12,14) + qhsj.substring(15,17);
	
	//只设置阶梯切换日期时间时，其他值赋为0
	params.fee_rate	=  '0,0,0,0,0';			//复费率 5
	params.jt_step  =  '0,0,0,0,0,0,0';		//阶梯梯度值7 
	params.jt_rate	=  '0,0,0,0,0,0,0,0';	//阶梯费率8
	params.jt_jsmdh =  '0,0,0,0'			//阶梯年结算日4
	
	var json_str   = jsonString.json2String(params);
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
			{
				firstLastFlag : true,
				userData1	  : rtu_id,
				mpid 		  : mp_id,
				dyCommSetPara : json_str,
				userData2 	  : ComntUseropDef.YFF_DYCOMM_SETPARA
			},
			function(data) {			    	//回传函数
				loading.loaded();
				window.top.addJsonOpDetail(data.detailInfo);
				if (data.result == "success") {
					if(window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
						alert("设置阶梯费率切换时间成功!");
					}else{
						window.top.addStringTaskOpDetail("设置阶梯费率切换时间成功!");
					}
				}
				else {
					if(window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
						alert("设置阶梯费率切换时间失败!");
					}else{
						window.top.addStringTaskOpDetail("设置阶梯费率切换时间失败!");
					}
				}
			}
		);
}

function changeFLFA(){//费率方案 下拉框 onchange
	var val=$("#xflfa").val();
	var text=$("#xflfa").find("option:selected").text();
	
	for (var i = 0; i < jsonFLFA.rows.length; i++) {
		if (jsonFLFA.rows[i].value == val) {
			$("#feeproj_detail_new").html(jsonFLFA.rows[i].desc);	
			return;
		}		
	}
}

function setDisabled(mode){
	$("#qhsj" ).attr("disabled",mode);
	$("#xqhsj").attr("disabled",mode);
	$("#jt_qhsj" ).attr("disabled",mode);
	$("#jt_xqhsj").attr("disabled",mode);
	$("#xflfa").attr("disabled",mode);
	$("#btnReadFee").attr("disabled",mode);
	$("#btnSetFee" ).attr("disabled",mode);
	$("#btnReadSwitchTime").attr("disabled",mode);
	$("#btnSetSwitchTime" ).attr("disabled",mode);
	$("#jt_btnReadSwitchTime").attr("disabled",mode);
	$("#jt_btnSetSwitchTime" ).attr("disabled",mode);
	$("#btnChangeFL").attr("disabled",mode);
	$("#fee_desc").html("");
	$("#feelv_desc").html("");
	$("#btnReadFeelv").attr("disabled",mode);
	
}

//获取所选中的新费率的费率信息
//页面加载时已经将查询出来的结果赋值给了jsonFLFA.
function getChgRate(){

	var new_feerate = {};
	
	for(var i=0;i<jsonFLFA.rows.length;i++){
		if(jsonFLFA.rows[i].value != $("#xflfa").val()) 		continue;

		new_feerate.feeType = jsonFLFA.rows[i].feeType;						//费率类型

		if (new_feerate.feeType  == 0) {
			new_feerate.val_rateZ = jsonFLFA.rows[i].val_rateZ*10000;			//总
		}
		else if (new_feerate.feeType ==  1) {
			new_feerate.val_rateJ = jsonFLFA.rows[i].val_rateJ*10000;			//尖
			new_feerate.val_rateF = jsonFLFA.rows[i].val_rateF*10000;			//峰
			new_feerate.val_rateP = jsonFLFA.rows[i].val_rateP*10000;			//平
			new_feerate.val_rateG = jsonFLFA.rows[i].val_rateG*10000;			//谷
		}
		else if (new_feerate.feeType == 2) {									//混合费率
			new_feerate.rateh_1 = jsonFLFA.rows[i].rateh_1*10000;				//混合费率1
			new_feerate.rateh_2 = jsonFLFA.rows[i].rateh_2*10000;				//混合费率2
			new_feerate.rateh_3 = jsonFLFA.rows[i].rateh_3*10000;				//混合费率3
			new_feerate.rateh_4 = jsonFLFA.rows[i].rateh_4*10000;				//混合费率4
			
			new_feerate.rateh_bl1 = jsonFLFA.rows[i].rateh_bl1;					//混合比例1
			new_feerate.rateh_bl2 = jsonFLFA.rows[i].rateh_bl2;					//混合比例2
			new_feerate.rateh_bl3 = jsonFLFA.rows[i].rateh_bl3;					//混合比例3
			new_feerate.rateh_bl4 = jsonFLFA.rows[i].rateh_bl4;					//混合比例4
		}																			
		else if (new_feerate.feeType == 3) {		//阶梯费率			
			//dubr 添加 阶梯电价类型  20140117
			new_feerate.meterfeeType = jsonFLFA.rows[i].meterfeeType;        	/*电表费率类型  0 单费率 1 复费率   3阶梯费率 */
			new_feerate.meterfeeR    = jsonFLFA.rows[i].meterfeeR;           	/*电表执行电价*/
			new_feerate.ratejType = jsonFLFA.rows[i].ratejType;             	//阶梯电价类型 

			if(new_feerate.meterfeeType == 0) {
				new_feerate.val_rateZ = jsonFLFA.rows[i].meterfeeR *10000;			//总
			}
			else if(new_feerate.meterfeeType == 1){
				new_feerate.val_rateJ = jsonFLFA.rows[i].meterfeeR *10000;			//总
				new_feerate.val_rateF = jsonFLFA.rows[i].meterfeeR *10000;			//总
				new_feerate.val_rateP = jsonFLFA.rows[i].meterfeeR *10000;			//总
				new_feerate.val_rateG = jsonFLFA.rows[i].meterfeeR *10000;			//总
			}
			else {
				var month = (new_feerate.ratejType == 0) ? 12 :1;					//阶梯电价类型  0 年度方案, 1月度方案  2月度峰谷 阶梯混合
				var blv = $("#blv").html() == 0 ? 1: parseInt($("#blv").html());
				var mul = Math.round(month/blv*100);
				
				new_feerate.ratej_td1 = jsonFLFA.rows[i].ratej_td1*mul;			//阶梯梯度值1
				new_feerate.ratej_td2 = jsonFLFA.rows[i].ratej_td2*mul;			//阶梯梯度值2
				new_feerate.ratej_td3 = jsonFLFA.rows[i].ratej_td3*mul;			//阶梯梯度值3

				new_feerate.ratej_r1 = jsonFLFA.rows[i].ratej_r1*10000;				//阶梯费率1
				new_feerate.ratej_r2 = jsonFLFA.rows[i].ratej_r2*10000;				//阶梯费率2
				new_feerate.ratej_r3 = jsonFLFA.rows[i].ratej_r3*10000;				//阶梯费率3
				new_feerate.ratej_r4 = jsonFLFA.rows[i].ratej_r4*10000;				//阶梯费率4
			}
		}
		else if (new_feerate.feeType == 4) {										//混合阶梯费率 dubr 20140124

			//alert(jsonFLFA.rows[i].ratej_td1 + '*********');
			new_feerate.meterfeehjType 	= jsonFLFA.rows[i].meterfeehjType;    		/*电表费率类型  0单费率 1复费率  3阶梯费率*/
			new_feerate.meterfeehjR    	= jsonFLFA.rows[i].meterfeehjR;       		/*电表执行电价*/
			new_feerate.ratehjType 		= jsonFLFA.rows[i].ratehjType;            	/*阶梯电价类型 0 年度方案, 1月度方案*/
		
			if(new_feerate.meterfeehjType == 0) {
				new_feerate.val_rateZ = jsonFLFA.rows[i].meterfeehjR *10000;			//总
			}
			else if(new_feerate.meterfeehjType == 1) {
				new_feerate.val_rateJ = jsonFLFA.rows[i].meterfeehjR *10000;			//总
				new_feerate.val_rateF = jsonFLFA.rows[i].meterfeehjR *10000;			//总
				new_feerate.val_rateP = jsonFLFA.rows[i].meterfeehjR *10000;			//总
				new_feerate.val_rateG = jsonFLFA.rows[i].meterfeehjR *10000;			//总
			}
			else {
				var month = (new_feerate.ratehjType == 0) ? 12 : 1;
				var blv = $("#blv").html() == 0 ? 1: parseInt($("#blv").html());
				var mul = Math.round(month/blv*100);

		    	new_feerate.ratehj_hr1_td1 = jsonFLFA.rows[i].ratehj_hr1_td1*mul;	//第1比例电价-阶梯梯度值1
		    	new_feerate.ratehj_hr1_td2 = jsonFLFA.rows[i].ratehj_hr1_td2*mul;	//第1比例电价-阶梯梯度值2
				new_feerate.ratehj_hr1_td3 = jsonFLFA.rows[i].ratehj_hr1_td3*mul;	//第1比例电价-阶梯梯度值3
		   
				new_feerate.ratehj_hr1_r1 = jsonFLFA.rows[i].ratehj_hr1_r1*10000;  		//第1比例电价-阶梯费率1 
				new_feerate.ratehj_hr1_r2 = jsonFLFA.rows[i].ratehj_hr1_r2*10000;		//第1比例电价-阶梯费率2 
				new_feerate.ratehj_hr1_r3 = jsonFLFA.rows[i].ratehj_hr1_r3*10000;		//第1比例电价-阶梯费率3 
				new_feerate.ratehj_hr1_r4 = jsonFLFA.rows[i].ratehj_hr1_r4*10000;		//第1比例电价-阶梯费率4 
				
				new_feerate.ratehj_hr2 	  = jsonFLFA.rows[i].ratehj_hr2*10000;			//第2比例电价
				new_feerate.ratehj_hr3    = jsonFLFA.rows[i].ratehj_hr3*10000;			//第3比例电价
				
				new_feerate.ratehj_bl1    = jsonFLFA.rows[i].ratehj_bl1;				//混合比例1
				new_feerate.ratehj_bl2    = jsonFLFA.rows[i].ratehj_bl2;				//混合比例2
				new_feerate.ratehj_bl3    = jsonFLFA.rows[i].ratehj_bl3;				//混合比例3
			}
		break;
		}
	}
	return new_feerate;
}

