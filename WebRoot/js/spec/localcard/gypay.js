//缴费
var rtnValue = null;	//检索用户信息
var	mygrid   = null
//var rtnBD    = null;
//var CARD_TYPE		= "02";	//电卡类型 缴费02
var WRITECARD_TYPE 	= window.top.SDDef.YFF_CARDMTYPE_KE003;	//卡表类型  1-智能表09版     3-6013 6-智能表13版
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	initGrid();
	$("#btnReadCard").focus();
	
	$("#btnReadCard").click(function(){readcard()});//读卡检索
	$("#cardInfo").click(function(){window.top.card_comm.cardinfo()});//卡内信息
	//$("#rtuInfo").click(function(){rtuInfo()});//终端信息
    $("#btnPay").click(function(){if(check())wrtCard_pay()});
	$("#btnPrt").click(function(){showPrint()});//打印
	$("#chgMbphone").click(function(){changeMbphone()}); // 更换手机
	
	$("#zbje").keyup(function(){calcu()});
	$("#jsje").keyup(function(){calcu()});
	$("#jfje").keyup(function(){calcu()});
	
	setDisabled(true);
});

function readcard(){
	setDisabled(false);	//清空记录	
	loading.loading();
	window.setTimeout('window.top.card_comm.readCardSearchGy('+ComntUseropDef.YFF_GYOPER_PAY+')', 10);
	//window.top.card_comm.readCardSearchGy(ComntUseropDef.YFF_GYOPER_PAY);
	
}

function setSearchJson2Html(js_tmp){	//调用readCardSearch后，readcard中异步执行完成后，执行此函数。
	loading.loaded();
	if(!js_tmp)return;
	if(!window.top.card_comm.checkInfo(js_tmp)) return;
		
	rtnValue = js_tmp;
	
	setConsValue(rtnValue);
	setCardExt(rtnValue);

	getAlarmValue(rtnValue.yffalarm_id);	//获取报警方式
	getMoneyLimit(rtnValue.feeproj_id);		//囤积限值

	getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);							//缴费记录	

	//mis.js中函数   miss查询函数
	var param = {rtu_id : rtnValue.rtu_id, zjg_id : rtnValue.zjg_id, busi_no : rtnValue.userno};
	mis_query(param,gloQueryResult);

	$("#btnPay").attr("disabled", false);	
}

function wrtCard_pay(){//缴费
	var params = {};	
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money	 	= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),3);
	
	loading.loading()
	
	modalDialog.height 		= 300;
	modalDialog.width 		= 300;
	modalDialog.url 		= def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param 		= {
		showGSM : true,
		key	: ["客户编号","客户名称", "缴费金额","追补金额","结算金额","总金额"],
		val	: [$("#userno").html(),$("#username").html(), params.pay_money,params.zb_money,params.othjs_money,params.all_money]		
	};
	
	var o = modalDialog.show();
	if(!o||!o.flag){
		loading.loaded();
		return;
	}
	
	params.gsmFlag = o.gsm;//params 最后一个参数是否发送短信  赋值  进行action请求
	window.setTimeout("wrtCard_pay1(" + o.gsm + ")", 10);

}

function wrtCard_pay1(gsm){//缴费
	var rtu_id = $("#rtu_id").val(), zjg_id = $("#zjg_id").val();
	if(rtu_id === "" || zjg_id === ""){
		alert(sel_user_info);
		return;
	}
	
	var params = {};

	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.myffalarmid 		= $("#yffalarm_id").val();
	params.paytype 			= $("#pay_type").val();
	params.buynum 			= Number($("#buy_times").val()) + 1;

	params.alarm_val1		= $("#yffalarm_alarm1").val();
	params.alarm_val2		= $("#yffalarm_alarm2").val();
	
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money	 	= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),3);
	
	params.date = 0;
	params.time = 0;
//	params.buy_dl 			= $("#buy_dl").html()==="" ? 0:$("#buy_dl").html();
//	params.pay_bmc 			= $("#pay_bmc").html()===""? 0:$("#pay_bmc").html();
	
	params.gsmFlag = gsm;//params 最后一个参数是否发送短信  赋值  进行action请求
	
	params.pt 			= rtnValue.pt_ratio;
	params.ct 			= rtnValue.ct_ratio;
	params.meter_type 	= WRITECARD_TYPE;
	params.card_type  	= SDDef.CARD_OPTYPE_BUY;//开户、缴费、补卡

	params.feeproj_id 	= $("#feeproj_id").val();
	params.limit_dl 	= money_limit;

	params.meterno  	= rtnValue.meter_id;
	params.cardno 		= $("#userno").html();
	params.writecard_no	= $("#writecard_no").html();
	params.pay_bmc 		= $("#pay_bmc").html();	
	params.buy_dl 		= $("#buy_dl").html();	
	
	params.operType  = ComntUseropDef.YFF_GYOPER_PAY;	//操作类型决定更新标识
	
	if (CheckCard(params) == false){
		loading.loaded();
		return;
	}

	$.post( def.basePath + "ajaxoper/actWebCard!getcardWrtInfo.action", 		//后台处理程序
		{
			result	: jsonString.json2String(params)
		},
		function(data) {			    	//回传函数
			var retStr 	 = data.result.toString();
			var ret_json = window.top.card_comm.writecard(retStr);

			if(ret_json.errno == 0){
				$("#btnPay").attr("disabled", true);
			//	alert("写卡成功!");
				wrtDB_pay(params);
			}else{
				loading.loaded();
				alert("写卡失败！\n" + ret_json.errsr);
			}	
		}
	);
}

function wrtDB_pay(params){
	params.shutdown_bd 		= 0;
	params.buynum = params.buynum - 1;
	params.type = 'localcard';//本地卡式
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
		{
			userData1		: params.rtu_id,
			zjgid 			: params.zjg_id,
			gsmflag 		: params.gsmFlag,
			gyOperPay 		: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_PAY
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {		
				
				getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
				$("#buy_times").val(parseInt($("#buy_times").val())+1);
//				IsDisabled();
				
				//向MIS系统缴费
				if(parseFloat(params.pay_money) > 0 && gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
					var ret_json = eval("(" + data.gyOperPay + ")");
					var mis_para 	= {};

					mis_para.rtu_id = ret_json.rtu_id;           
					mis_para.zjg_id = params.zjg_id;             
					mis_para.op_type= SDDef.YFF_OPTYPE_PAY;   
					mis_para.date 	= ret_json.op_date;          
					mis_para.time 	= ret_json.op_time;          
					mis_para.updateflag = 0;                     
					mis_para.jylsh 	= ret_json.wasteno;          
					mis_para.yhbh 	= rtnValue.userno;           
					mis_para.jfje 	= params.pay_money;          
					mis_para.yhzwrq = ret_json.op_date;          
					mis_para.jfbs 	= gloQueryResult.misJezbs; 
					
					var all_pay = 0.0;
					
					if(provinceMisFlag == "HB"){
						for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
							eval("mis_para.yhbh" + i + "=gloQueryResult.misDetailsvect["+i+"].yhbh");
							eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");
						}                
					}
					else if(provinceMisFlag == "HN"){
						mis_para.batch_no	= gloQueryResult.misBatchNo;	
						mis_para.hsdwbh		= gloQueryResult.misHsdwbh;
						for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
							eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");

							eval("mis_para.dfny" + i + "=gloQueryResult.misDetailsvect["+i+"].dfny");
							eval("mis_para.dfje" + i + "=gloQueryResult.misDetailsvect["+i+"].dfje");
							eval("mis_para.wyjje" + i + "=gloQueryResult.misDetailsvect["+i+"].wyjje");
							var bcssje= Math.min(parseFloat(gloQueryResult.misDetailsvect[i].dfje) + parseFloat(gloQueryResult.misDetailsvect[i].wyjje), parseFloat(mis_para.jfje) - all_pay);
							all_pay += bcssje;
							eval("mis_para.bcssje" + i + "=" + bcssje);
						}
					}
					else if (provinceMisFlag == "GS"){
						mis_para.jfbs 	= gloQueryResult.misJezbs;
						mis_para.dzpc   = gloQueryResult.misDzpc;
						for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
							eval("mis_para.yhbh" + i + "=gloQueryResult.misDetailsvect["+i+"].yhbh");
							eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");

							eval("mis_para.jfje" + i + "=gloQueryResult.misDetailsvect["+i+"].yjje");
							eval("mis_para.dfje" + i + "=gloQueryResult.misDetailsvect["+i+"].dfje");
							eval("mis_para.wyjje" + i + "=gloQueryResult.misDetailsvect["+i+"].wyjje");
							
							eval("mis_para.sctw" + i + "=gloQueryResult.misDetailsvect["+i+"].sctw");
							eval("mis_para.bctw" + i + "=gloQueryResult.misDetailsvect["+i+"].bctw");
						}
					}
					mis_para.vlength = gloQueryResult.misDetailsvect.length;
					
					mis_pay(mis_para);
				}
				alert("缴费成功!");
				if(data.gyOperPay == ""){
					alert("获取返回的操作信息失败。请到[补打发票]界面补打。")
					$("#btnPrt").attr("disabled",true);
				}else{
					$("#btnPrt").attr("disabled",false);
					window.top.WebPrint.setYffDataOperIdx2params(data.gyOperPay);//打印用的参数
				}
			}
			else {
				alert("向主站发送命令失败" + (data.operErr ? data.operErr : ''));
			}
		}
	);
}

function calcu(){
	if(!rtnValue)return;

	var jfje = 0.0, zbje = 0.0, jsje = 0.0, zje = 0.0;
	zbje = $("#zbje").val() === "" ? 0 : $("#zbje").val();
	jfje = $("#jfje").val() === "" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val() === "" ? 0 : $("#jsje").val();
	
	zje = round(parseFloat(zbje) + parseFloat(jfje) + parseFloat(jsje),def.point);
	
	if(!isNaN(zje)){
		$("#zje").html(zje);
		var ke003f = (WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE003);	

		if (ke003f) {
			var dj 	= $("#feeproj_reteval").val()===""?1:$("#feeproj_reteval").val();
			var blv = $("#blv").html()===""?1:$("#blv").html();
			var zbd = 0;
			var alarm_val1 = (rtnValue.yffalarm_alarm1 == "" || rtnValue.yffalarm_alarm1 == undefined ) ? 30 : rtnValue.yffalarm_alarm1;
			var alarm_val2 = (rtnValue.yffalarm_alarm2 == "" || rtnValue.yffalarm_alarm2 == undefined ) ? 5 : rtnValue.yffalarm_alarm2;

			var ret_val = cardalarm_calcu(zje, dj, blv, zbd, alarm_val1, alarm_val2, rtnValue.type, zbd);

			$("#buy_dl").html(ret_val.buy_dl);
			$("#pay_bmc").html(ret_val.pay_bmc);
			//$("#shutdown_bd").html(ret_val.shutdown_bd);
			$("#yffalarm_alarm1").val(ret_val.alarm_code1);
			$("#yffalarm_alarm2").val(ret_val.alarm_code2);
		}
		else 
		{
			setAlarmValue(rtnValue.yffalarm_alarm1, rtnValue.yffalarm_alarm2, zje);
		}
	}else{ 
		return;
	}
}	

function check(){
	//如果MIS不通，禁止缴费
	if (gloQueryResult.misUseflag == "true" && (gloQueryResult.misOkflag == "false" || gloQueryResult.misOkflag == undefined)) {
		alert("MIS不能通讯,禁止缴费...");
		return false;
	}
	
	if($("#btnPay").attr("disabled"))  return false;

	var jfje = 0, zbje = 0, jsje = 0;
	zbje = $("#zbje").val()==="" ? 0 : $("#zbje").val();
	jfje = $("#jfje").val()==="" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val()==="" ? 0 : $("#jsje").val();

	if(isNaN(zbje)){
		alert("请输入数字!");
		$("#zbje").focus().select();
		return false;
	}
	
	if(isNaN(jfje)){
		alert("请输入数字!");
		$("#jfje").focus().select();
		return false;
	}

	if(isNaN(jsje)){
		alert("请输入数字!");
		$("#jsje").focus().select();
		return false;
	}

	if(!isDbl("zbje" , "追补金额" ,-money_limit,money_limit, false)){//追补金额应该在0~囤积值之间
		return false;
	}

	if(!isDbl("jsje",  "结算金额" ,-money_limit, money_limit, false)){
		return false;
	}

	if(!isDbl("jfje",  "缴费金额" ,0, money_limit, false)){
		return false;
	}
	//20130402
	var zje = $("#zje").html();
	if(!isDbl_Html("zje" ,  "总金额"  , 0, money_limit, false)){//总金额应该在0~囤积值之间
		return false;
	}

	if(parseFloat(zje) == 0){
		if(!confirm("用户缴费总金额为0,要继续缴费吗?"))return false;
	}
	
	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
	}
	
	if(jfje == 0 || jfje == ""){	//总金额>0但是 缴费金额为0或为""(空)
		return(confirm("用户未交费,是否继续"));
	}
	
	return true;
}

function showPrint(){//打印发票
	var filename = null; 
	
	if (WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE003){
		filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.gycard3);
		window.top.WebPrint.prt_params.page_type = window.top.WebPrint.nodeIdx.gycard3;
	}else{
		filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.gycard1);
		window.top.WebPrint.prt_params.page_type = window.top.WebPrint.nodeIdx.gycard1;
	}
	if(filename == undefined || filename == null) return;
	window.top.WebPrint.prt_params.file_name = filename;
//	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.gyks[WRITECARD_TYPE];
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintGy();
}

function setCardExt(rtnValue)
{
	$("#buy_times").html($("#buy_times").val());
	
	var tmp_val = rtnValue.cardmeter_type.split("]");
	WRITECARD_TYPE = tmp_val[0].split("[")[1];
	
//调整 20130402	
//	WRITECARD_TYPE = tmp_val[0].substring(1);
	$("#cardmeter_type").html(tmp_val[1].split('"')[0]);
	$("#writecard_no").html(rtnValue.writecard_no);
	$("#meter_id").html(rtnValue.meter_id);

	var ke003f = (WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE003);

	if (ke003f) {
		$("#gdl").show();
		$("#gdldesc").show();
		$("#pay_bmc").show();
		$("#pay_bmcdesc").show();
		$("#alarm1").html("报警表码差1：");
		$("#alarm2").html("报警表码差2：");
	}
	else {
		$("#gdl").hide();
		$("#gdldesc").hide();
		$("#pay_bmc").hide();
		$("#pay_bmcdesc").hide();
		$("#alarm1").html("报警金额1(元)：");
		$("#alarm2").html("报警金额2(元)：");
	}
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
}

function setDisabled(mode){
	//清空原有数据: 最后要提出个单独函数	
	if(!mode){
		$("#zbje").val("");
		$("#jfje").val("");
		$("#jsje").val("");
		$("#zje").html("");
		$("#yffalarm_alarm1").val("");
		$("#yffalarm_alarm2").val("");
		$("#td_bdinf").html("");
		$("#buy_times").html("");
		$("#buy_dl").html("");
		$("#pay_bmc").html("");
		
	//	rtnBD = null;
	}
	
	$("#zbje").attr("disabled",mode);
	$("#jfje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#yffalarm_alarm1").attr("disabled",mode);
	$("#yffalarm_alarm2").attr("disabled",mode);
	$("#chgMbphone").attr("disabled",mode);
    
//	$("#btnImportBD").attr("disabled",mode);
//	$("#cardInfo").attr("disabled",mode);
}

//判断写卡  数组太脆弱， 调用后台action 改进
function CheckCard(writeparams)
{
	var json_out = {};
	json_out = window.top.card_comm.readcard();
	if(json_out == undefined || json_out ==""){
		alert("读卡错误!");
		return  false;				
	}
	
	var meterType 	= json_out.meter_type;	//卡表类型	
	var meterNo 	= json_out.meterno; 	//表号
	var consNo  	= json_out.consno;		//户号
	var buyTime		= json_out.back_pay_num;//返写的购电次数	
	
	//20131127新增判断
	if((WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE001) && (meterType != window.top.SDDef.YFF_METER_TYPE_ZNK)){
		alert("用户电表为2009版规约，请使用09版购电卡!");
		return false;
	}
	
	if((WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE006) && (meterType != window.top.SDDef.YFF_METER_TYPE_ZNK2)){
		alert("用户电表为2013版规约，请使用13版购电卡!");
		return false;
	}
	//end
	
	//不太严谨 		需要调整
	if (meterType == window.top.SDDef.YFF_METER_TYPE_6103)	{
		var tmp = "00000000000000000000"
		if (tmp == consNo)	{
			alert("用户户号为空，不能缴费！")
			return false;
		}
		else if ((parseInt(buyTime) <=0) || (parseInt(buyTime) >=1000000)) {
			alert("卡内无反写信息，不能购电！")
			return false;
		} 
		else if (writeparams.writecard_no.replace(/(^0*)/g, "") != consNo.replace(/(^0*)/g, "")) {
			alert("用户卡户号与系统不一致！")
			return false;
		}
		else if (writeparams.buynum != parseInt(buyTime) +1) {
			alert("用户卡购电次数与售电系统不一致,不能购电！")
			return false;
		} 
	}
	else {
		var tmp = "000000000000"
		if (tmp == meterNo)	{
			alert("用户表号为空，不能缴费！")
			return false;
		}
		else if (tmp == consNo)	{
			alert("用户户号为空，不能缴费！")
			return false;
		}
		else if (parseInt(buyTime) <=0) {
			alert("卡内无反写信息，不能购电！")
			return false;
		} 
		else if (writeparams.cardno.replace(/(^0*)/g, "") != consNo.replace(/(^0*)/g, "")) {
			alert("用户卡户号与系统不一致！")
			return false;
		}
		else if (writeparams.meterno.replace(/(^0*)/g, "") != meterNo.replace(/(^0*)/g, "")) {
			alert("用户卡表号与系统不一致， 请到换表流程！")
			return false;
		}
		else if (writeparams.buynum != parseInt(buyTime) +1) {
			alert("用户卡购电次数与售电系统不一致,不能购电！")
			return false;
		} 
	}
	return true;
}

//更换手机
function changeMbphone(){
	modalDialog.height = 160;
	modalDialog.width  = 200;
	modalDialog.url = def.basePath + "jsp/dialog/chgMbPhone.jsp";
	
	var rtn = rtnValue;
	rtn.flag = 'gy';
	modalDialog.param = rtn;
	var params = modalDialog.show();
	if(!params) return;
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post(def.basePath + "ajaxoper/actOperGy!taskProc.action", 
			{
				userData1		: params.rtu_id,
				zjgid 			: params.zjg_id,
				gsmflag 		: params.gsmFlag,
				gyOperResetDoc 	: json_str,
				userData2 		: ComntUseropDef.YFF_GYOPER_RESETDOC
			},
			function(data){
				loading.loaded();
				if (data.result == "success") {
					$("#tel").html(params.filed1);
				}
				else {
					alert("向主站发送更改手机命令失败" + (data.operErr ? data.operErr : ''));
				}
			}
	);
}
