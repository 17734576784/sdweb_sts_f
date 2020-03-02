//冲正
var rtnValue;
//var CARD_TYPE		= "02";	//电卡类型 缴费02
var WRITECARD_TYPE 	= window.top.SDDef.YFF_CARDMTYPE_KE001;	//卡表类型  1-智能表     3-6013
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
    $("#btnSearch"  ).click(function(){selcons()});
	$("#btnReadCard").click(function(){readcard()});
    $("#cardInfo"   ).click(function(){window.top.card_comm.cardinfo()});//卡内信息
	$("#btnRever"   ).click(function(){if(check())wrtCard_rever()});
//	$("#rtuInfo"    ).click(function(){rtuInfo()});
	$("#btnPrt"     ).click(function(){showPrint()});
	
	$("#jfje"       ).keyup(function(){calcu()});
	$("#zbje"       ).keyup(function(){calcu()});
	$("#jsje"       ).keyup(function(){calcu()});
	
	initGrid();
    setDisabled(true);
	$("#btnRever"   ).attr("disabled",true);
	$("#btnReadCard").focus();
});


function readcard(){
	loading.loading();
	setDisabled(false);
	$("#btnRever").attr("disabled",false);
	window.top.card_comm.readCardSearchGy(ComntUseropDef.YFF_GYOPER_REVER);
}

function setSearchJson2Html(js_tmp){//调用readCardSearch后，readcard中异步执行完成后，执行此函数。
	loading.loaded();

	if(!js_tmp)return;
	if(!window.top.card_comm.checkInfo(js_tmp)) return;

	rtnValue = js_tmp;
	
	setConsValue(rtnValue);	
	setCardExt(rtnValue);
	
	getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);							//读取缴费记录
	
	getMoneyLimit(rtnValue.feeproj_id);		//囤积限值
	getAlarmValue(rtnValue.yffalarm_id);//获取报警方式
	
	lastPayInfo();
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, zjg_id : rtnValue.zjg_id, busi_no : rtnValue.userno};
	mis_query(param, gloQueryResult);
	
	$("#btnRever"   ).attr("disabled",false);
	$("#btnPrt"   ).attr("disabled",true);
}

function selcons(){//检索
//	setDisabled(false);
//	$("#btnRever"   ).attr("disabled",true);
		
	var js_tmp = doSearch("ks",ComntUseropDef.YFF_GYOPER_REVER, rtnValue);
	if(!js_tmp)	return;
	if(!window.top.card_comm.checkInfo(js_tmp)) return;
	
	rtnValue = js_tmp;
	
	setDisabled(false);
	
	setConsValue(rtnValue);
	setCardExt(rtnValue);
	
	getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);

	getMoneyLimit(rtnValue.feeproj_id);		//囤积限值
	getAlarmValue(rtnValue.yffalarm_id);	//获取报警方式
	
	lastPayInfo();		//读取最近一次购电记录信息

	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, zjg_id : rtnValue.zjg_id, busi_no : rtnValue.userno};
	mis_query(param, gloQueryResult);
	
	$("#btnRever"   ).attr("disabled",false);	
	$("#btnPrt"     ).attr("disabled",true);	
}

function setDisabled(mode){	//设置状态
	if(!mode){
		$("#jfje").val("");
		$("#zbje").val("");
		$("#jsje").val("");
		$("#zje").val("");
		$("#yffalarm_alarm1").val("");
		$("#yffalarm_alarm2").val("");
	//	$("#dqye").html("");
		$("#jsyhm").html("");
		$("#yhfl").html("");
	}
	
	$("#jfje").attr("disabled",mode);
	$("#zbje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#yffalarm_alarm1").attr("disabled",mode);
	$("#yffalarm_alarm2").attr("disabled",mode);
	
}
//last_tt 无用 delete
function calcu(){
	
	var jfje = 0.0, jsje = 0, zbje = 0, last_tt = 0;
	
	jfje = $("#jfje").val() === "" ? 0 : $("#jfje").val();
	zbje = $("#zbje").val() === "" ? 0 : $("#zbje").val();
	jsje = $("#jsje").val() === "" ? 0 : $("#jsje").val();
	last_tt = $("#last_tt").val() === "" ? 0 : $("#last_tt").val();
	
	var zje = round((parseFloat(jfje) + parseFloat(jsje) + parseFloat(zbje)), 3);
	if(!isNaN(zje)){		
		$("#zje").html(zje);
		var ke003f = (WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE003);	

		if (ke003f) {
			var dj 	= $("#feeproj_reteval").val()===""?1:$("#feeproj_reteval").val();
			var blv = $("#blv").html()===""?1:$("#blv").html();
			var zbd = 0;//(rtnzbd==="" || isNaN(rtnzbd)) ? 0 : rtnzbd;
			var alarm_val1 = (rtnValue.yffalarm_alarm1 == "" || rtnValue.yffalarm_alarm1 == undefined) ? 30 : rtnValue.yffalarm_alarm1;
			var alarm_val2 = (rtnValue.yffalarm_alarm2 == "" || rtnValue.yffalarm_alarm2 == undefined) ? 5 : rtnValue.yffalarm_alarm2;
			
			var ret_val = cardalarm_calcu(zje, dj, blv, zbd, alarm_val1, alarm_val2, rtnValue.type, zbd);

			$("#buy_dl").html(ret_val.buy_dl);
			$("#pay_bmc").html(ret_val.pay_bmc);
			//$("#shutdown_bd").html(ret_val.shutdown_bd);
			$("#yffalarm_alarm1").val(ret_val.alarm_code1);
			$("#yffalarm_alarm2").val(ret_val.alarm_code2);
		}
		else {
			setAlarmValue(rtnValue.yffalarm_alarm1, rtnValue.yffalarm_alarm2, zje)
		}
	}
}

function check(){
	
	if(date_time.date != _today && gloQueryResult.misUseflag == "true") {
		alert("不能冲正非今天的记录...");
		return false;
	}
	//如果MIS不通，禁止冲正
	if (gloQueryResult.misUseflag == "true" && (gloQueryResult.misOkflag == "false" || gloQueryResult.misOkflag == undefined)) {
		alert("MIS不能通讯,禁止冲正...");	
		return false;
	}
	
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

	if(parseFloat(jfje) == 0 || jfje == ""){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
	}
	
    if(!isDbl("yffalarm_alarm1",  "报警金额1" ,0 , money_limit, false)){
		return false;
	}
    
    if(!isDbl("yffalarm_alarm2",  "报警金额2" ,0 , money_limit, false)){
		return false;
	}
    
//    var jfje = $("#jfje").val();
//    if(jfje == 0 || jfje == ""){
//    	if(!confirm("用户未缴费,确定要冲正吗?"))return false;
//    }
    
	return true;
}

function wrtCard_rever(){//冲正-写卡
	var params ={};
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money	 	= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),3);
	
	loading.loading();
	
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
	params.gsmFlag = o.gsm;//是否发送短信 
	window.setTimeout("wrtCard_rever1(" + o.gsm + ")", 10);
}

function wrtCard_rever1(gsm){//冲正-写卡
	var rtu_id = $("#rtu_id").val(), zjg_id = $("#zjg_id").val();
	if(rtu_id === "" || zjg_id === ""){
		loading.loaded();
		alert(sel_user_info);
		return;
	}

	var params = {};

	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.myffalarmid 		= $("#yffalarm_id").val();
	params.paytype 			= $("#pay_type").val();
	params.buynum 			= $("#buy_times").val();

	params.alarm_val1		= $("#yffalarm_alarm1").val();
	params.alarm_val2		= $("#yffalarm_alarm2").val();
	
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	
	params.all_money	 	= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),3);
	
	params.date 			= 0;
	params.time 			= 0;
	params.buy_dl 			= $("#buy_dl").html()==="" ? 0:$("#buy_dl").html();
	params.pay_bmc 			= $("#pay_bmc").html()===""? 0:$("#pay_bmc").html();

	params.shutdown_bd 		= 0;
	
	params.gsmFlag = gsm;//是否发送短信 

	params.pt 			= rtnValue.pt_ratio;
	params.ct 			= rtnValue.ct_ratio;
	params.meter_type 	= WRITECARD_TYPE;
	
	if (params.buynum   == 1) {	//开户
		params.card_type  = SDDef.CARD_OPTYPE_OPEN;//开户、缴费、补卡
	}
	else {						//缴费
		params.card_type  = SDDef.CARD_OPTYPE_BUY;//开户、缴费、补卡
	}
	
	params.feeproj_id   = $("#feeproj_id").val();
	params.limit_dl 	= money_limit;
	
	params.meterno   	= rtnValue.meter_id;
	params.cardno    	= $("#userno").html();
	params.writecard_no	= $("#writecard_no").html();
	params.pay_bmc 		= $("#pay_bmc").html();	
	params.buy_dl 		= $("#buy_dl").html();

	params.operType  = ComntUseropDef.YFF_GYOPER_REVER;	//操作类型决定更新标识

	if (CheckCard(params) == false){
		loading.loaded();
		return;
	} 
	loading.loading();
	$.post( def.basePath + "ajaxoper/actWebCard!getcardWrtInfo.action", 		//后台处理程序
		{
			result	: jsonString.json2String(params)
		},
		function(data) {			    	//回传函数
			var retStr 	 = data.result.toString();
			var ret_json = window.top.card_comm.writecard(retStr);
			
			if(ret_json.errno == 0){
				//alert("写卡成功");
				$("#btnRever").attr("disabled", true);
				WrtDB_rever(params);
			}else{
				loading.loaded();
				alert("写卡失败！\n" + ret_json.errsr);
			}
	
		}
	);
}

function WrtDB_rever(params){//冲正-存库
	
	params.oldopdate 	= date_time.date;
	params.oldoptime 	= date_time.time;
	params.misUseflag = gloQueryResult.misUseflag;	

	var json_str   = jsonString.json2String(params);

	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
			{
				userData1		: params.rtu_id,
				zjgid 			: params.zjg_id,
				gsmflag 		: params.gsmFlag,
				gyOperRever		: json_str,
				userData2 		: ComntUseropDef.YFF_GYOPER_REVER
			},
			function(data) {			    	//回传函数
				loading.loaded();
				if (data.result == "success") {
					$("#btnPrt").attr("disabled", false);
					getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
					var ret_json = eval("(" + data.gyOperRever + ")");
					if(gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
						//MIS冲正, 不能隔天冲正, 请手工在MIS中进行冲正操作...
						if(_today == params.oldopdate && _today == parseInt(ret_json.op_date)){
							var rev_para = {};
							rev_para.rtu_id = params.rtu_id;
							rev_para.zjg_id = params.zjg_id;
							rev_para.op_type= SDDef.YFF_OPTYPE_REVER;
							rev_para.date	= ret_json.op_date;
							rev_para.time	= ret_json.op_time;
							rev_para.last_date = params.oldopdate;
							rev_para.last_time = params.oldoptime;
							rev_para.updateflag = 0;
							rev_para.czjylsh = ret_json.wasteno;
							rev_para.yjylsh = date_time.last_wastno;
							rev_para.yhzwrq = ret_json.op_date;
							rev_para.pay_money = params.pay_money;
							rev_para.busi_no = rtnValue.userno;
							
							if(provinceMisFlag == "HB"){
								
							}
							else if(provinceMisFlag == "HN"){
								rev_para.batch_no	= gloQueryResult.misBatchNo;	
								rev_para.hsdwbh		= gloQueryResult.misHsdwbh;
							}
							else if(provinceMisFlag == "GS"){
								rev_para.yhbh = rtnValue.userno;
								rev_para.dzpc = gloQueryResult.misDzpc;
							}
							mis_rever(rev_para);
						}
						else {
							alert("MIS冲正, 不能隔天冲正, 请手工在MIS中进行冲正操作..." );
						}
					}
					alert("冲正成功!");
					if(data.gyOperPay == ""){
						alert("获取返回的操作信息失败。请到[补打发票]界面补打。")
						$("#btnPrt").attr("disabled",true);
					}else{
						$("#btnPrt").attr("disabled",false);
						window.top.WebPrint.setYffDataOperIdx2params(data.gyOperRever);//打印用的参数
					}
				}
				else {
					alert("向主站发送冲正命令失败" + (data.operErr ? data.operErr : ''));
				}
			}
		);
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

//此处要求传入变量， 以后可以提到一个公共函数
function setCardExt(rtnValue)
{
	//add ext
	var tmp_val = rtnValue.cardmeter_type.split("]");
	WRITECARD_TYPE = tmp_val[0].split("[")[1];
	//调整 20130402	
	//	WRITECARD_TYPE = tmp_val[0].substring(1);
	
	$("#cardmeter_type").html(tmp_val[1].split('"')[0]);
	$("#writecard_no").html(rtnValue.writecard_no);
	$("#meter_id").html(rtnValue.meter_id);
	
	var ke003f = (WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE003);

	if (ke003f) {
//		$("#buy_dl").attr("disabled", false).attr("class", "");
		$("#gdlctrl").show();
		$("#alarm1").html("报警表码差1：");
		$("#alarm2").html("报警表码差2：");
	}
	else {
//		$("#buy_dl").attr("disabled", true).attr("class", "readonly_bg");
		$("#gdlctrl").hide();
		$("#alarm1").html("报警金额1(元)：");
		$("#alarm2").html("报警金额2(元)：");
	}

	$("#buy_times").html($("#buy_times").val());

	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
	
	if (rtnValue.prot_type == ComntDef.YD_PROTOCAL_KEDYH) {
		$("#ctrlpara_title").attr("style","display:block;");
		$("#ctrlpara_content").attr("style","display:block;");
		$("#ctrlpara_butt").attr("style","display:block;");
	}
	
	else {
		$("#ctrlpara_title").attr("style","display:none;");
		$("#ctrlpara_content").attr("style","display:none;");
		$("#ctrlpara_butt").attr("style","display:none;");
	}
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
	
	var meterType 	 = json_out.meter_type;	//卡表类型	
	var meterNo 	 = json_out.meterno; 	//表号
	var consNo  	 = json_out.consno;		//户号
	var writebuyTime = json_out.pay_num     //写卡的购电次数
	var backbuyTime	 = json_out.back_pay_num;//返写的购电次数
	
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
			alert("用户户号为空，不能冲正！")
			return false;
		}
		else if (writeparams.writecard_no.replace(/(^0*)/g, "") != consNo.replace(/(^0*)/g, "")) {
			alert("用户卡户号与系统不一致！")
			return false;
		}
		else if (parseInt(backbuyTime) >0 && parseInt(backbuyTime) < 1000000) {
			alert("上次购电后已经插卡，不能冲正！")
			return false;
		}
		else if (writeparams.buynum != parseInt(writebuyTime)) {
			alert("用户卡购电次数与售电系统不一致,不能冲正！")
			return false;
		} 
	}
	else {
		var tmp = "000000000000"
		if (tmp == meterNo)	{
			alert("用户表号为空，不能冲正！")
			return false;
		}
		else if (tmp == consNo)	{
			alert("用户户号为空，不能冲正！")
			return false;
		}
		else if (parseInt(backbuyTime) != 0) {
			alert("上次购电后已经插卡,不能冲正！")
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
		else if (writeparams.buynum != parseInt(writebuyTime)) {
			alert("用户卡购电次数与售电系统不一致,不能冲正！")
			return false;
		} 
	}
	return true;
}

function rtuInfo(){		//终端内信息-type bd或者je-
	modalDialog.width  = 750;
	modalDialog.height = 190;
	modalDialog.url    = "../dialog/callRemain.jsp";
	modalDialog.param  = {
						type 			: "je", 
						rtuid 			: $("#rtu_id").val(), 
						zjgid 			: $("#zjg_id").val(), 
						userno 			: $("#userno").html(), 
						username 		: $("#username").html(),
						buytimes 		: $("#buy_times").val(),
						blv				: $("#blv").html(),
						dj				: $("#feeproj_reteval").val(),
						cacl_type_desc	: $("#cacl_type_desc").html(),
						prot_type		: rtnValue.prot_type
						};
	var ret = modalDialog.show();
	if(!ret)return;
	
//	$("#dqye").html(ret.nowval);
	
	if(!czflag)$("#btnRever").attr("disabled",false);
}


