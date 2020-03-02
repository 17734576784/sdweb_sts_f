var rtnValue;
var WRITECARD_TYPE = window.top.SDDef.YFF_CARDMTYPE_KE001;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	initGrid();
	$("#btnSearch").click(function(){selcons()});
	$("#btnRead").click(function(){read_card()});
	$("#cardinfo").click(function(){window.top.card_comm.cardinfo()});
	$("#metinfo").click(function(){metinfo()});
	$("#btnRever").click(function(){if(check())wrtCard_cz();});
	$("#btnPrt").click(function(){printPayRec()});
	$("#btnSearch").focus();
	
	$("#jfje").keyup(function(){calcu()});
	$("#zbje").keyup(function(){calcu()});
	$("#jsje").keyup(function(){calcu()});
	
	setDisabled(true);
});

function selcons(){//检索
	$("#btnRever").attr("disabled",true);	
	var js_tmp = doSearch("ks",ComntUseropDef.YFF_DYOPER_REVER,rtnValue);
	if(!js_tmp)	return;
	rtnValue = js_tmp;
	
	//20131021添加，当查询结果中的预付费表类型 为新增表时将WRITECARD_TYPE赋为6
	var yffmeter_type = rtnValue.yffmeter_type;
	WRITECARD_TYPE = getCardByMeter(yffmeter_type);
	
	selcons1();
}

function calcu(){
	var jfje   = 0.0, jsje = 0, zbje = 0, last_tt = 0;
	jfje = $("#jfje").val() === "" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val() === "" ? 0 : $("#jsje").val();
	zbje = $("#zbje").val() === "" ? 0 : $("#zbje").val();
	last_tt = $("#last_tt").val() === "" ? 0 : $("#last_tt").val();
	
	var zje = round((parseFloat(jfje) + parseFloat(jsje) + parseFloat(zbje)), def.point);
	if(!isNaN(zje)){
		$("#zje").html(zje);
	}
}

function read_card(){//读卡
	loading.tip = "正在读卡,请稍后...";
	loading.loading();
	window.setTimeout("window.top.card_comm.readCardSearchDy(" + ComntUseropDef.YFF_DYOPER_REVER + ")", 10);
}

function setSearchJson2Html(js_tmp) {
	loading.loaded();
	if(!js_tmp)return;
	if(!window.top.card_comm.checkInfo(js_tmp)) return;
	rtnValue = js_tmp;
	
	//20131021添加，当查询结果中的预付费表类型 为新增表时将WRITECARD_TYPE赋为6
	var yffmeter_type = rtnValue.yffmeter_type;
	WRITECARD_TYPE = getCardByMeter(yffmeter_type);
	selcons1();
}

function selcons1() {
	setDisabled(false);
	setConsValue(rtnValue);
	$("#writecard_no").html(rtnValue.writecard_no)
	getRecord(rtnValue.rtu_id, rtnValue.mp_id);
	lastPayInfo(rtnValue.rtu_id, rtnValue.mp_id);		//读取最近一次购电记录信息
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno};
	mis_query(param, gloQueryResult);

	$("#btnRever").attr("disabled", false);
}
	
function wrtCard_cz1(gsm){
	
	if (!CheckCard()){
		loading.loaded();
		return;
	}
	
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert(sel_user_info);
		return;
	}
	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.mp_id 			= $("#mp_id").val();
	params.paytype 			= $("#pay_type").val();
	params.buynum 			= $("#buy_times").html();
    
	params.alarm_val1		= rtnValue.yffalarm_alarm1;
	params.alarm_val2		= rtnValue.yffalarm_alarm2;
	
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money	 	= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),3);
	
	params.gsmFlag = gsm;//是否发送短信 
	
	params.pt 			= rtnValue.pt;
	params.ct 			= rtnValue.ct;
	params.meter_type 	= WRITECARD_TYPE;
	if (params.buynum == 1) {	//开户
		params.card_type  	= SDDef.CARD_OPTYPE_OPEN;
	}
	else {						//缴费
		params.card_type  	= SDDef.CARD_OPTYPE_BUY;
	}
	params.limit_dl 	= rtnValue.moneyLimit;
	params.feeproj_id 	= $("#feeproj_id").val();
	params.writecard_no	= rtnValue.writecard_no;
	params.meterno  	= rtnValue.esamno;
	params.cardno 		= rtnValue.userno;
	
	params.operType  = ComntUseropDef.YFF_DYOPER_REVER;	//操作类型决定更新标识
	params.jt_cycle_md = rtnValue.jt_cycle_md;				//低压阶梯年结算日
	
	$.post( def.basePath + "ajaxoper/actWebCard!getcardWrtInfo.action", 		//后台处理程序
		{
			result	: jsonString.json2String(params)
		},
		function(data) {			    	//回传函数
			var retStr 	 = data.result;
			var ret_json = window.top.card_comm.writecard(retStr);
			if(ret_json.errno  == 0){
				wrtDB_cz(params);
			}else{
				alert("写卡失败！\n" + ret_json.errsr);
				loading.loaded();
			}
		}
	);
}

function wrtCard_cz() {
	
	var params = {};
	
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
	
	window.setTimeout("wrtCard_cz1(" + o.gsm + ")", 10);
}

function wrtDB_cz(params){		//冲正
	params.old_op_date	= rever_info.date;
	params.old_op_time 	= rever_info.time;
	
	var json_str   = jsonString.json2String(params);
	
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
			{
		        userData1		: params.rtu_id,
				mpid 			: params.mp_id,
				gsmflag 		: params.gsmFlag,
				dyOperRever		: json_str,
				userData2 		: ComntUseropDef.YFF_DYOPER_REVER
			},
			function(data) {			    	//回传函数
				loading.loaded();
				if (data.result == "success") {
					$("#btnPrt").attr("disabled", false);
					getRecord(rtnValue.rtu_id, rtnValue.mp_id);
					var ret_json = eval("(" + data.dyOperRever + ")");
					if(gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
						//MIS冲正, 不能隔天冲正, 请手工在MIS中进行冲正操作...
						if(_today == params.old_op_date && _today == parseInt(ret_json.op_date)){
							//mis.js函数
							var rev_para = {};
							if(provinceMisFlag == "HB"){
								rev_para.rtu_id = params.rtu_id;
								rev_para.mp_id = params.mp_id;
								rev_para.op_type= SDDef.YFF_OPTYPE_REVER;
								rev_para.date	= ret_json.op_date;
								rev_para.time	= ret_json.op_time;
								rev_para.last_date = params.old_op_date;
								rev_para.last_time = params.old_op_time;
								rev_para.updateflag = 0;
								rev_para.czjylsh = ret_json.wasteno;
								rev_para.yjylsh = rever_info.last_wastno;
								rev_para.yhzwrq = ret_json.op_date;
								rev_para.pay_money = params.pay_money;
								rev_para.busi_no = rtnValue.userno;
								rev_para.yhbh = rtnValue.userno;
							}
							else if(provinceMisFlag == "HN"){
								rev_para.rtu_id = params.rtu_id;
								rev_para.mp_id = params.mp_id;
								rev_para.op_type= SDDef.YFF_OPTYPE_REVER;
								rev_para.date	= ret_json.op_date;
								rev_para.time	= ret_json.op_time;
								rev_para.last_date = params.old_op_date;
								rev_para.last_time = params.old_op_time;
								rev_para.updateflag = 0;
								rev_para.czjylsh = ret_json.wasteno;
								rev_para.yjylsh = rever_info.last_wastno;
								rev_para.yhzwrq = ret_json.op_date;
								rev_para.pay_money = params.pay_money;
								rev_para.busi_no = rtnValue.userno;
								rev_para.yhbh = rtnValue.userno;
								
								rev_para.batch_no	= gloQueryResult.misBatchNo;	
								rev_para.hsdwbh		= gloQueryResult.misHsdwbh;
							}
							else if(provinceMisFlag == "GS"){
								rev_para.rtu_id = params.rtu_id;
								rev_para.mp_id = params.mp_id;
								rev_para.op_type= SDDef.YFF_OPTYPE_REVER;
								rev_para.date	= ret_json.op_date;
								rev_para.time	= ret_json.op_time;
								rev_para.last_date = params.old_op_date;
								rev_para.last_time = params.old_op_time;
								rev_para.updateflag = 0;
								rev_para.czjylsh = ret_json.wasteno;
								rev_para.yjylsh = rever_info.last_wastno;
								rev_para.yhzwrq = ret_json.op_date;
								rev_para.pay_money = params.pay_money;
								rev_para.busi_no = rtnValue.userno;
								rev_para.yhbh = rtnValue.userno;
								rev_para.dzpc = gloQueryResult.misDzpc;
							}
							mis_rever(rev_para);
						}
						else {
							alert("MIS冲正, 不能隔天冲正, 请手工在MIS中进行冲正操作...");
						}
					}
					alert("冲正成功!");
					$("#btnRever").attr("disabled",true);
					if(data.dyOperRever == ""){
						alert("获取返回的操作信息失败。请到[补打发票]界面补打。")
						$("#btnPrt").attr("disabled",true);
					}else{
						$("#btnPrt").attr("disabled",false);
						window.top.WebPrint.setYffDataOperIdx2params(data.dyOperRever,window.top.WebPrint.nodeIdx.dycard);//打印用的参数
					}
					setDisabled(true);
				}
				else {
					alert("向主站发送冲正命令失败" + (data.operErr ? data.operErr : ''));
				}
			}
		);
}

function CheckCard()
{
	var json_out = {};
	json_out = window.top.card_comm.readcard();
	if(json_out == undefined || json_out ==""){
		alert("读卡错误!");
		return  false;				
	}

	var meterType 	= json_out.meter_type;	//卡表类型
	var meterNo,consNo,buyTime, wbuyTime;  //表号，户号, 写卡户号

	if(meterType == window.top.SDDef.YFF_METER_TYPE_ZNK || meterType == window.top.SDDef.YFF_METER_TYPE_ZNK2){
		
		if((WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE001) && (meterType != window.top.SDDef.YFF_METER_TYPE_ZNK)){
			alert("用户电表为2009版规约，请使用09版购电卡!");
			return false;
		}
		
		if((WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE006 && meterType != window.top.SDDef.YFF_METER_TYPE_ZNK2)){
			alert("用户电表为2013版规约，请使用13版购电卡!");
			return false;
		}
	
		meterNo 	= json_out.meterno; 	//表号
		consNo  	= json_out.consno;		//户号
		buyTime		= json_out.back_pay_num;//返写购电次数	
		wbuyTime	= json_out.pay_num;		//购电次数

		var tmp = "000000000000"
		if (tmp == meterNo)	{
			alert("用户表号为空，不能冲正！")
			return false;
		}
		else if (tmp == consNo)	{
			alert("用户户号为空，不能冲正！")
			return false;
		}
		else if (parseInt(buyTime) != 0) {
			alert("上次购电后已经插卡,不能冲正！")
			return false;
		} 
		else if (rtnValue.writecard_no.replace(/(^0*)/g, "") != consNo.replace(/(^0*)/g, "")) {
			alert("用户卡户号与系统不一致！")
			return false;
		}
		else if (rtnValue.esamno.replace(/(^0*)/g, "") != meterNo.replace(/(^0*)/g, "")) {
			alert("用户卡表号与系统不一致， 请到换表流程！")
			return false;
		}
		else if ((rtnValue.buy_times !=wbuyTime) ||(parseInt(buyTime) > 0)) {
			alert("用户卡已经插入电表,不能冲正！")
			return false;
		}

	//	else if (rtnValue.buy_times != parseInt(buyTime) + 1) {
	//		alert("用户卡购电次数与售电系统不一致,不能购电！")
	//		return false;
	//	} 
	}else {
		alert("未知卡类型")
		return false;
	}
	return true;
}

function metinfo(){//表内信息
	modalDialog.width = 800;
	modalDialog.height = 600;
	modalDialog.url = def.basePath + "jsp/dyjc/dialog/meterInfo.jsp";
	modalDialog.param = {"rtuId":$("#rtu_id").val() ,"mpId":$("#mp_id").val()};
	modalDialog.show();
}

function check(){

	if(rever_info.date != _today && gloQueryResult.misUseflag == "true") {
		alert("不能冲正非今天的记录...");
		return false;
	}
		
	//如果MIS不通，禁止缴费
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

	if(!isDbl("zbje" ,"追补金额",-rtnValue.moneyLimit, rtnValue.moneyLimit)){
		return false;
	}
	
	if(!isDbl("jsje" ,"结算金额",-rtnValue.moneyLimit, rtnValue.moneyLimit)){
		return false;
	}
	
	if(!isDbl("jfje" ,"缴费金额",0,rtnValue.moneyLimit)){
		return false;
	}
	
	if(!isDbl_Html("zje","总金额",0,rtnValue.moneyLimit)){
		return false;
	}

	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
	}
	
//	if(($("#jfje").val() == "0" || $("#jfje").val() == "")){
//		return(confirm("客户未缴费,继续操作吗?"))
//	}
	
	return true;
}

function printPayRec() {
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.dycard);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
//	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.dyje;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
	$("#btnPrt").attr("disabled",true);
}

function setDisabled(mode){	//设置状态
	if(!mode){
		$("#jfje").val("");
		$("#zbje").val("");
		$("#jsje").val("");
		$("#zje").html("");
		$("#writecard_no").html("");
	}
	
	$("#jfje").attr("disabled",mode);
	$("#zbje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	
}