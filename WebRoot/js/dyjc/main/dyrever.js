var rtnValue;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	initGrid();
	$("#btnSearch").click(function(){selcons()});
	$("#btnRever").click(function(){rever()});
	$("#btnPrint").click(function(){printCZ()});
	$("#zbje").keyup(function(){calcTotal();});
	$("#jsje").keyup(function(){calcTotal();});
	$("#jfje").keyup(function(){calcTotal();});
	
	setDisabled(true);
	$("#btnSearch").focus();
});

function rever(){
	
	if(!check()) return;
	
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	
	var params = {};
	params.rtu_id       = rtu_id;
	params.mp_id        = mp_id;
	params.paytype      = $("#pay_type").val();
	params.pay_money    = $("#jfje").val()==="" ? 0 : $("#jfje").val();
	params.othjs_money 	= $("#jsje").val()==="" ? 0 : $("#jsje").val();;
	params.zb_money 	= $("#zbje").val()==="" ? 0 : $("#zbje").val();;
	params.all_money   = round(parseFloat(params.zb_money) + parseFloat(params.pay_money) + parseFloat(params.othjs_money),def.point);
	
	params.old_op_date = rever_info.date;
	params.old_op_time = rever_info.time;
	
	if(round(parseFloat($("#zje").html())+parseFloat(rtnValue.now_remain),3) < 0){
		alert("总金额应大于[-"+rtnValue.now_remain+"]");
		return;
	}
	
	modalDialog.height = 320;
	modalDialog.width = 300;
	modalDialog.url = def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param = {
		showGSM : true,
		key	: ["客户编号","用户名称","所属供电所", "缴费金额","追补金额","结算金额","总金额"],
		val	: [rtnValue.userno,rtnValue.username,rtnValue.orgname, params.pay_money,params.zb_money,params.othjs_money,params.all_money]		
	};
	var o = modalDialog.show();
	if(!o.flag){
		return;
	}
	loading.loading();
	var json_str = jsonString.json2String(params);
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag	: true,
			userData1		: rtu_id,
			mpid			: mp_id,
			gsmflag			: 0,
			dyOperRever		: json_str,
			userData2		: ComntUseropDef.YFF_DYOPER_REVER
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				
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
				setDisabled(true);
				getRecord($("#rtu_id").val(), $("#mp_id").val());
				
				$("#btnRever").attr("disabled", true);
				$("#btnPrint").attr("disabled", false);
				window.top.WebPrint.setYffDataOperIdx2params(data.dyOperRever,window.top.WebPrint.nodeIdx.dymain);//打印用的参数
			}
			else {
				alert("冲正失败!" + (data.operErr ? data.operErr : ''));
			}
		}
	);
}

function check() {
	if(rever_info.date != _today && gloQueryResult.misUseflag == "true") {
		alert("不能冲正非今天的记录...");
		return false;
	}
	
	//如果MIS不通，禁止缴费
	if (gloQueryResult.misUseflag == "true" && (gloQueryResult.misOkflag == "false" || gloQueryResult.misOkflag == undefined)) {
		alert("MIS不能通讯,禁止开户...");
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

	if ((window.top.dynegativemoney.dymain.totflag == 0) &&
		($("#zje").html() < "0")) {
		alert("总金额不能为负数");
		return false;
	}
		
	if(!isDbl_Html("zje" ,  "总金额", -rtnValue.moneyLimit, rtnValue.moneyLimit, false)){//缴费金额应该在0~囤积值之间
		return false;
	}
//
	if (parseFloat($("#zje").html()) + parseFloat($("#dqye").html()) < 0) {
		alert("最新余额不能为负数");
		return false;
	}
	
	if (parseFloat($("#zje").html()) + parseFloat($("#dqye").html()) > rtnValue.moneyLimit) {
		alert("最新余额超出囤积限值");
		return false;
	}
	
    var jfje = $("#jfje").val();
    if(jfje == 0 || jfje == ""){
    	if(!confirm("用户未缴费,确定要冲正吗?"))return false;
    }
    
	return true;
}

function selcons(){//检索
	var rtnValue1 = doSearch("zz",ComntUseropDef.YFF_DYOPER_REVER,rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno};
	mis_query(param,gloQueryResult);
	
	setDisabled(false);
	setConsValue(rtnValue);
	getRecord(rtnValue.rtu_id, rtnValue.mp_id);
	
	$("#buy_times").html(rtnValue.buy_times);
	
	lastPayInfo(rtnValue.rtu_id, rtnValue.mp_id);
	
	$("#btnPrint").attr("disabled",true);
}

function calcTotal(){//总金额
	var zbje = $("#zbje").val()===""?0:$("#zbje").val();
	var jsje = $("#jsje").val()===""?0:$("#jsje").val();
	var jfje = $("#jfje").val()===""?0:$("#jfje").val();
	
	if(isNaN(jfje) || isNaN(jsje) || isNaN(zbje)){
		return;
	}
	
	var zje = round(parseFloat(zbje) + parseFloat(jsje) + parseFloat(jfje),3);
	$("#zje").html(zje);
	
}	

function setDisabled(mode){
	if(!mode){
		$("#buy_times").html("");
		
		$("#last_je").val("");
		$("#last_zbje").val("");
		$("#last_jsje").val("");
		$("#last_tt" ).val("");
		$("#zbje").val("");
		$("#jsje").val("");
		$("#jfje").val("");
		$("#zje").html("");
		
	}
	$("#zbje").attr("disabled",mode);
	$("#jfje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	
	$("#btnRever").attr("disabled",mode);
}

function printCZ() {
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.dymain);
	if(filename == undefined || filename == null){
		return;
	}
	window.top.WebPrint.prt_params.file_name = filename;
//	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.dyje;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}