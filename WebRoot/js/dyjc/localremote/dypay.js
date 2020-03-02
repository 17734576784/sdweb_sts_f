var rtnValue;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("150,80,80,80,80,80,80,80,80,120,80,80,*")
	mygrid.setColAlign("center,center,left,right,right,right,right,right,right,right,left,left")
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.init();
	mygrid.setSkin("light");
	mygrid.setColumnHidden(7, true);
	
	$("#btnSearch").click(function(){selcons()});
	$("#btnReadMeter").click(function(){metinfo()});
	$("#btnPay").click(function(){doPay()});
	$("#btnPrt").click(function(){showPrint()});
	$("#chgMbphone").click(function(){changeMbphone()}); // 更换手机
	
	$("#zbje").keyup(function(){calcTotal()});
	$("#jsje").keyup(function(){calcTotal()});
	$("#jfje").keyup(function(){calcTotal()});	
	$("#btnSearch").focus();
	setDisabled(true);
});
	
function selcons(){//检索
	tmp = doSearch("yc",ComntUseropDef.YFF_DYOPER_PAY,rtnValue);
	if(!tmp){
		return;
	}
	setDisabled(false);
	rtnValue = tmp;
	setConsValue(rtnValue);
    //20131213 dubr 总金额显示为0
	calcTotal();
	isJTfee(rtnValue);
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno};
	mis_query(param, gloQueryResult);
	getYffRecs();	
	
	$("#btnReadMeter").attr("disabled",false);
	$("#btnPay").attr("disabled",false);	
	$("#btnPrt").attr("disabled",true);	
	$("#jfje").focus();
}

function doPay(){//缴费
	
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
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
	
	if(!isDbl("jfje" ,"缴费金额",0, rtnValue.moneyLimit)){
		return false;
	}
	
	if(!isDbl_Html("zongje" ,  "总金额"  , 0, rtnValue.moneyLimit, false)){//总金额应该在0~囤积值之间
		return false;
	}

	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费, 要继续缴费吗?")) return false;
	}

//	if($("#zongje").html() > rtnValue.moneyLimit){
//		alert("总金额不能大于囤积限值[" + rtnValue.moneyLimit + "]");
//		return;
//	}
//	if($("#zongje").html() <= 0){
//		alert("总金额必须大于0");
//		return;
//	}
//	if($("#zongje").html() == "NaN"){
//		alert("缴费金额必须为数字");
//		return;
//	}

	var params = {};
	params.rtu_id = rtu_id;
	params.mp_id = mp_id;
	params.paytype = $("#pay_type").val();
	params.buynum = $("#buy_times").html();
	params.pay_money = $("#jfje").val()===""?0:$("#jfje").val();
	if(params.pay_money < 0){
		alert("缴费金额不能为负数");
		return;
	}
	params.othjs_money = $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money = $("#zbje").val()===""?0:$("#zbje").val();
	
	params.all_money = round(parseFloat(params.zb_money) + parseFloat(params.pay_money) + parseFloat(params.othjs_money),3);
	params.rtu_id = rtu_id;
	params.mp_id = mp_id;
	
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
	
	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommDy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_DYCOMM_PAY;
	//end
	
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	var json_str = jsonString.json2String(params);
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag : true,
			userData1 	  : rtu_id,
			mpid 	  	  : mp_id,
			gsmflag   	  : o.gsm,
			dyCommPay 	  : json_str,
			userData2 	  : ComntUseropDef.YFF_DYCOMM_PAY
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				window.top.addStringTaskOpDetail("远程通讯成功!正在保存到数据库...");
				loading.loading();
				$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
					{
						userData1 : rtu_id,
						mpid 	  : mp_id,
						gsmflag   : 0,
						dyOperPay : json_str,
						userData2 : ComntUseropDef.YFF_DYOPER_PAY
					},
					function(data) {			    	//回传函数
						loading.loaded();
						if (data.result == "success") {
							$("#buy_times").val(parseInt($("#buy_times").val())+1);
							getYffRecs();
							setDisabled(true);
							//向MIS系统缴费
							if(parseFloat(params.pay_money) > 0 && gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
								var ret_json = eval("(" + data.dyOperPay + ")");
								var mis_para 	= {};
								
								//河北南网
								if (provinceMisFlag == "HB") {
									mis_para.rtu_id = params.rtu_id;
									mis_para.mp_id  = params.mp_id;
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
									for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
										eval("mis_para.yhbh" + i + "=gloQueryResult.misDetailsvect["+i+"].yhbh");
										eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");
									}
									mis_para.vlength = gloQueryResult.misDetailsvect.length;
								}
								//河南
								else if (provinceMisFlag == "HN") {
									mis_para.rtu_id = params.rtu_id;
									mis_para.mp_id = params.mp_id;
									mis_para.op_type= SDDef.YFF_OPTYPE_PAY;
									mis_para.date 	= ret_json.op_date;
									mis_para.time 	= ret_json.op_time;
									mis_para.updateflag = 0;
									mis_para.jylsh 	= ret_json.wasteno;
									mis_para.yhbh 	= rtnValue.userno;
									mis_para.jfje 	= params.pay_money;
									mis_para.yhzwrq = ret_json.op_date;
									mis_para.jfbs 	= gloQueryResult.misJezbs;

									mis_para.batch_no	= gloQueryResult.misBatchNo;	
									mis_para.hsdwbh		= gloQueryResult.misHsdwbh;	
									
									var all_pay = 0.0;
									for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
										eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");

										eval("mis_para.dfny" + i + "=gloQueryResult.misDetailsvect["+i+"].dfny");
										eval("mis_para.dfje" + i + "=gloQueryResult.misDetailsvect["+i+"].dfje");
										eval("mis_para.wyjje" + i + "=gloQueryResult.misDetailsvect["+i+"].wyjje");
										var bcssje= Math.min(parseFloat(gloQueryResult.misDetailsvect[i].dfje) + parseFloat(gloQueryResult.misDetailsvect[i].wyjje), parseFloat(mis_para.jfje) - all_pay);
										all_pay += bcssje;
										eval("mis_para.bcssje" + i + "=" + bcssje);
									}
									mis_para.vlength = gloQueryResult.misDetailsvect.length;
								}
								//甘肃
								else if (provinceMisFlag == "GS") {
									mis_para.rtu_id = params.rtu_id;
									mis_para.mp_id = params.mp_id;
									mis_para.op_type= SDDef.YFF_OPTYPE_PAY;
									mis_para.date 	= ret_json.op_date;
									mis_para.time 	= ret_json.op_time;
									mis_para.updateflag = 0;
									mis_para.jylsh 	= ret_json.wasteno;
									mis_para.yhbh 	= rtnValue.userno;
									mis_para.jfje 	= params.pay_money;
									mis_para.yhzwrq = ret_json.op_date;
									mis_para.jfbs 	= gloQueryResult.misJezbs;
									mis_para.dzpc   = gloQueryResult.misDzpc;
									
									var all_pay = 0.0;
									for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
										eval("mis_para.yhbh" + i + "=gloQueryResult.misDetailsvect["+i+"].yhbh");
										eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");

										eval("mis_para.jfje" + i + "=gloQueryResult.misDetailsvect["+i+"].yjje");
										eval("mis_para.dfje" + i + "=gloQueryResult.misDetailsvect["+i+"].dfje");
										eval("mis_para.wyjje" + i + "=gloQueryResult.misDetailsvect["+i+"].wyjje");
										
										eval("mis_para.sctw" + i + "=gloQueryResult.misDetailsvect["+i+"].sctw");
										eval("mis_para.bctw" + i + "=gloQueryResult.misDetailsvect["+i+"].bctw");
									}
									mis_para.vlength = gloQueryResult.misDetailsvect.length;
								}
								
								mis_pay(mis_para);
							}
							if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
								alert("缴费成功!");
							}else{
								window.top.addStringTaskOpDetail("缴费成功!");
							}
							$("#btnPrt").attr("disabled",false);
							$("#btnPay").attr("disabled",true);
							window.top.WebPrint.setYffDataOperIdx2params(data.dyOperPay,window.top.WebPrint.nodeIdx.dyremote);//打印用的参数
						}
						else {
							if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
								alert("缴费成功，但保存缴费记录到数据库失败!");
							}else{
								window.top.addStringTaskOpDetail("缴费成功，保存缴费记录到数据库失败!");
							}
						}
					}
				);
			}
			else {
				alert("缴费失败!");
			}
		}
	);
}

function setYFFInfo(){
	$("#zbje").val("");
	$("#jsje").val("");
	$("#jfje").val("");
	$("#zongje").html("");
}


function showPrint(){//打印发票
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.dyremote);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
//	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.dyje;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}

function calcTotal(){//总金额
	
	var zbje = $("#zbje").val()===""?0:$("#zbje").val();
	var jsje = $("#jsje").val()===""?0:$("#jsje").val();
	var jfje = $("#jfje").val()===""?0:$("#jfje").val();
	
	$("#zongje").html(round(parseFloat(zbje) + parseFloat(jsje) + parseFloat(jfje),3));
}	
	
function checkNum(jeVal,zFlag,desc){
	if(isNaN(jeVal)){
		return false;
	}
	return true;
}

function metinfo(){//表内信息--远程读表
	modalDialog.width = 800;
	modalDialog.height = 600;
	modalDialog.url = def.basePath + "jsp/dyjc/dialog/meterInfo.jsp";
	modalDialog.param = {"rtuId":$("#rtu_id").val() ,"mpId":$("#mp_id").val()};
	modalDialog.show();
}

function setDisabled(mode){
	if(!mode){
		$("#jsje").val("");
		$("#jfje").val("");
		$("#zbje").val("");
//		$("#dqye").val("");
		$("#zongje").html("");
	}
//	$("#dqye").attr("disabled",mode);
	$("#pay").attr("disabled",mode);
	$("#jfje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#zbje").attr("disabled",mode);
	$("#chgMbphone").attr("disabled",mode);
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

//更换手机
function changeMbphone(){
	modalDialog.height = 160;
	modalDialog.width  = 200;
	modalDialog.url = def.basePath + "jsp/dialog/chgMbPhone.jsp";
	
	var rtn = rtnValue;
	rtn.flag = 'dy';
	modalDialog.param = rtn;
	var params = modalDialog.show();
	if(!params) return;
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post(def.basePath + "ajaxoper/actOperDy!taskProc.action", 
			{
				userData1		: params.rtu_id,
				mpid 			: params.mp_id,
				gsmflag 		: params.gsmFlag,
				dyOperResetDoc 	: json_str,
				userData2 		: ComntUseropDef.YFF_DYOPER_RESETDOC
			},
			function(data){
				loading.loaded();
				if (data.result == "success") {
					$("#tel").html(params.filed1);
				}
				else {
					alert("向主站发送更改手机命令失败");
				}
			}
	);
}
