var rtnValue;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = {};

$(document).ready(function(){
	initGrid();
	
	$("#btnSearch").click(function(){selcons()});
	$("#btnReadMeter").click(function(){metinfo()});
	$("#btnRever").click(function(){rever()});
	$("#btnPrint").click(function(){printCZ()});		 //打印
	
	$("#zbje").keyup(function(){calcTotal()});
	$("#jsje").keyup(function(){calcTotal()});
	$("#jfje").keyup(function(){calcTotal()});	
	$("#btnSearch").focus();
})

function selcons(){//检索
	tmp = doSearch("yc",ComntUseropDef.YFF_DYOPER_REVER,rtnValue);
	if(!tmp){
		return;
	}
	
	rtnValue = tmp;
	
	//当为旧版本表时，提示不支持
	if(!is2013Meter(rtnValue.yffmeter_type )){
		alert("此电表不支持远程冲正操作！");
		document.location.reload();
		return;
	}
	
	setDisabled(false);
	setConsValue(rtnValue);
	
	$("#buy_times").html(rtnValue.buy_times);
	
	//获取上回缴费信息
	lastPayInfo(rtnValue.rtu_id, rtnValue.mp_id);
	
	isJTfee(rtnValue);
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno};
	//进行mis查询
	mis_query(param, gloQueryResult);
	
	//获取缴费记录
	getRecord(rtnValue.rtu_id, rtnValue.mp_id);
	
	$("#btnReadMeter").attr("disabled",false);
	$("#btnPay").attr("disabled",false);	
	$("#btnPrint").attr("disabled",true);	
	$("#jfje").focus();
}

//表内信息--远程读表
function metinfo(){
	modalDialog.width = 800;
	modalDialog.height = 600;
	modalDialog.url = def.basePath + "jsp/dyjc/dialog/meterInfo.jsp";
	modalDialog.param = {"rtuId":$("#rtu_id").val() ,"mpId":$("#mp_id").val()};
	modalDialog.show();
}

//远程冲正操作
function rever(){
	if(!check()) return;
	
	var last_paymoney=  $("#last_je").val()=== ""?0:$("#last_je").val();
	var pay_money = $("#jfje").val()==="" ? 0:$("#jfje").val();
	
	var params = {};
	params.rtu_id = rtnValue.rtu_id;
	params.mp_id = rtnValue.mp_id;
	params.paytype = $("#pay_type").val();
	params.buynum = $("#buy_times").html();	//此处不加1，在java后台中进行操作
	params.pay_money = pay_money;
	if(params.pay_money < 0){
		alert("缴费金额不能为负数");
		return;
	}
	params.othjs_money = $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money = $("#zbje").val()===""?0:$("#zbje").val();
	
	//通讯传到java后台的all_money钱数为：上次与本次之差
	params.all_money = round(parseFloat(params.zb_money) + parseFloat(last_paymoney)- parseFloat(params.pay_money) + parseFloat(params.othjs_money),3);
	
	params.old_op_date = rever_info.date;
	params.old_op_time = rever_info.time;
	

	modalDialog.height = 320;
	modalDialog.width = 300;
	modalDialog.url = def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param = {
		showGSM : true,
		key	: ["客户编号","用户名称","所属供电所", "缴费金额(元)","追补金额(元)","结算金额(元)","总金额(元)"],
		val	: [rtnValue.userno,rtnValue.username,rtnValue.orgname, params.pay_money,params.zb_money,params.othjs_money,params.all_money]
	};
	
	var o = modalDialog.show();
	if(!o.flag){
		return;
	}
	
	loading.loading();
	
	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommDy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_DYCOMM_REVER;
	//end
	
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");

	var json_str = jsonString.json2String(params);
	
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
			{
				firstLastFlag : true,
				userData1 	  : rtnValue.rtu_id,
				mpid 	  	  : rtnValue.mp_id,
				dyCommRever   : json_str,
				userData2 	  : ComntUseropDef.YFF_DYCOMM_REVER
			},
			function(data){
				window.top.addJsonOpDetail(data.detailInfo);
				if(data.result == "success"){
					window.top.addStringTaskOpDetail("远程通讯成功!正在保存到数据库...");
					//冲正操作保持不变
					params.all_money = $('#zje').val()=== "" ? 0:$("#zje").val();
					json_str = jsonString.json2String(params);
					$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
						{
						firstLastFlag	: true,
							userData1 	: rtnValue.rtu_id,
							mpid 	  	: rtnValue.mp_id,
							gsmflag   	: 0,
							dyOperRever : json_str,
							userData2 	: ComntUseropDef.YFF_DYOPER_REVER
						},
						function(data){
							loading.loaded();
							if (data.result == "success") {
								
								var ret_json = eval("(" + data.dyOperRever + ")");
									
								//MIS操作开始
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
								//MIS操作结束
								
								if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
									alert("冲正成功!");
								}else{
									window.top.addStringTaskOpDetail("冲正成功!");
								}
								loading.loaded();
								setDisabled(true);
								getRecord($("#rtu_id").val(), $("#mp_id").val());
								$("#btnPrint").attr("disabled",false);
								$("#btnRever").attr("disabled",true);
								window.top.WebPrint.setYffDataOperIdx2params(data.dyOperRever,window.top.WebPrint.nodeIdx.dyremote);//打印用的参数
							}
							else{
								loading.loaded();
								if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
									alert("冲正成功，但保存冲正记录到数据库失败!");
								}else{
									window.top.addStringTaskOpDetail("冲正成功，保存冲正记录到数据库失败!");
								}
							}
						});
				}
				else {
					loading.loaded();
					alert("冲正失败!");
				}
			});
		}

//如果是 阶梯费率 或者 阶梯混合费率 则显示阶梯信息
function isJTfee(rtnValue){
	var feeType  = rtnValue.feeType;
	var now_remain  = (rtnValue.now_remain  =="" || rtnValue.now_remain  == undefined) ? 0 : rtnValue.now_remain;
	var now_remain2 = (rtnValue.now_remain2 =="" || rtnValue.now_remain2 == undefined) ? 0 : rtnValue.now_remain2;

	if(feeType == "" || feeType == null ){
		return;
	}
	if(feeType == SDDef.YFF_FEETYPE_JTFL || feeType == SDDef.YFF_FEETYPE_MIXJT){
		$("#jt_info" ).attr("style","display:blank");
		$("#jt_info1").attr("style","display:blank");
		$("#jt_info2").attr("style","display:blank");
		
		var jsje = round((parseFloat(now_remain)-parseFloat(now_remain2)), def.point);
		$("#jsje").val(jsje >= 0 ? 0 : jsje);
		
		$("#now_remain1").html(now_remain);
		$("#now_remain2").html(now_remain2);
		$("#jt_total_dl").html(rtnValue.jt_total_dl);
		calcTotal();
	}
}

//界面信息禁用控制
function setDisabled(mode){
	if(!mode){
		$("#jsje").val("");
		$("#jfje").val("");
		$("#zbje").val("");
		$("#zje").val("");
	}
	$("#btnRever").attr("disabled",mode);
	$("#jfje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#zbje").attr("disabled",mode);
	$("#chgMbphone").attr("disabled",mode);
}

//计算总金额
function calcTotal(){//总金额
	var zbje = $("#zbje").val()===""?0:$("#zbje").val();
	var jsje = $("#jsje").val()===""?0:$("#jsje").val();
	var jfje = $("#jfje").val()===""?0:$("#jfje").val();
	
	$("#zje").val(round(parseFloat(zbje) + parseFloat(jsje) + parseFloat(jfje),3));
}

function check() {
	if(rever_info.date != _today && gloQueryResult.misUseflag == "true") {
		alert("不能冲正非今天的记录...");
		return false;
	}
	
	//如果MIS不通，禁止缴费
	if (gloQueryResult.misUseflag == "true" && (gloQueryResult.misOkflag == "false" || gloQueryResult.misOkflag == undefined)) {
		alert("MIS不能通讯,禁止缴费...");
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
		($("#zje").val() < "0")) {
		alert("总金额不能为负数");
		return false;
	}
		
	if(!isDbl("zje" ,  "总金额", -rtnValue.moneyLimit, rtnValue.moneyLimit, false)){//缴费金额应该在0~囤积值之间
		return false;
	}
	
	if (parseFloat($("#zje").val()) + parseFloat($("#syje").html()) < 0) {
		alert("最新余额不能为负数");
		return false;
	}
	
	if (parseFloat($("#zje").val()) + parseFloat($("#syje").html()) > rtnValue.moneyLimit) {
		alert("最新余额超出囤积限值");
		return false;
	}
	
    var jfje = $("#jfje").val();
    if(jfje == 0 || jfje == ""){
    	if(!confirm("用户未缴费,确定要冲正吗?"))return false;
    }
    
    //冲正金额大于上次缴费金额，到缴费界面缴费
    if(parseFloat($("#zje").val()) > parseFloat($("#last_je").val())){
    	alert("冲正金额大于上次缴费金额，请到缴费界面缴纳差额!")
    	return false;
    }
  
	return true;
}

function printCZ() {
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.dyremote);
	if(filename == undefined || filename == null){
		return;
	}
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
	$("#btnPrint").attr("disabled",true);
}
