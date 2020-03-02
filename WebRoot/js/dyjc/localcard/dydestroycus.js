var rtnValue;
var cancelToken;
var getPrintParam = {};
var printParamAddr = "";
var printParamTelno = "";
$(document).ready(function(){
	initGrid();

  	$("#btnDestroy").attr("disabled",true);
	//标准检索
	$("#btnSearch").click(function(){clear();selcons()});
	//销户
	$("#btnDestroy").click(function(){consDestroy()});
 	$("#btnSearch").focus();
	$("#prt").click(function(){printPayRec()}).attr("disabled",true);

	setDisabled(true);
});


function clear(){	
	$("#canceltoken").css("display","none");
	$("#dqye").val("");
	$("#prt").attr("disabled",true);
}

function selcons(){//检索
	//每次检索禁用销户按钮
	$("#btnDestroy").attr("disabled",true);
 	var rtnValue1 = doSearch("ks",ComntUseropDef.YFF_DYOPER_DESTORY,rtnValue);
	if(!rtnValue1){
		return;
	}
	setDisabled(false);	
	rtnValue = rtnValue1;	
 	fillInfoValue(rtnValue);
}

function printPayRec(){//打印
	
	var printParam = {};	
	
	printParam.opType = "close";
	printParam.meterNo = $("#commaddr").text();
	printParam.customerID = $("#userno").text();
	printParam.conName = $("#username").text();
	
	var token1 = $("#canceltoken").text();
	token1 = token1.split(":")[1];
	printParam.token1 = token1;
	
	printParam.payDate 	= getPrintParam.optime;
	printParam.payMoney = getPrintParam.pay_money; 
	printParam.payDL 	= getPrintParam.buy_dl; 
	printParam.sdadmin 	= getPrintParam.op_man;
	printParam.wasteno 	= getPrintParam.wasteno; 
	printParam.orgAddr 	= printParamAddr; 
	printParam.telno 	= printParamTelno;
	
	printParam.finalJyje 	= $("jy_money").text();
	
	modalDialog.width 	= 250;
	modalDialog.height 	= 600;
	modalDialog.param	= printParam;
	modalDialog.url 	= basePath + "jsp/dyjc/print/printTemplate.jsp";
		
	modalDialog.show();
	
	
	
//	window.top.WebPrint.prt_params.ext_info = "";
//	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.dycard);
//	if(filename == undefined || filename == null)return;
//	window.top.WebPrint.prt_params.file_name = filename;
//	window.top.WebPrint.prt_params.reprint = 0;
//	window.top.WebPrint.doPrintDy();
}

function fillInfoValue(obj){
	if(obj){
		$("#userno").html(obj.userno);
		$("#username").html(obj.username);
		$("#cus_state").html(obj.cus_state);
		$("#tel").html(obj.tel);
		$("#useraddr").html(obj.useraddr);
		$("#commaddr").html(obj.commaddr);
		$("#yffmeter_type_desc").html(obj.yffmeter_type_desc);
		$("#factory").html(obj.factory);
		$("#utilityid").html(obj.utilityid);
		
		$("#cacl_type_desc").html(obj.cacl_type_desc);
		$("#feectrl_type_desc").html(obj.feectrl_type_desc);
		$("#pay_type_desc").html(obj.pay_type_desc);
		$("#feeproj_desc").html(obj.feeproj_desc);
		$("#feeproj_detail").html(obj.feeproj_detail);
		$("#tzval").html(obj.tzval);
		
		$("#rtu_id").val(obj.rtu_id);
		$("#mp_id").val(obj.mp_id);
		$("#cacl_type").val(obj.cacl_type);
		$("#feectrl_type").val(obj.feectrl_type);
		$("#pay_type").val(obj.pay_type);
		$("#feeproj_id").val(obj.feeproj_id);
		
		$("#seqNo").val(obj.seqNo);
		$("#keyNo").val(obj.keyNo);
		
		$("#buy_times").val(obj.buy_times);
		$("#res_id").val(obj.res_id);

		printParamAddr = obj.addr;
		printParamTelno = obj.telno;
		
		getRecord(obj.rtu_id, obj.mp_id, "", 10);
		$("#btnDestroy").attr("disabled",false);
 		
		//添加ct pt jy_money参数
		$("#ctData").html(obj.ct);
		$("#ptData").html(obj.pt);
		$("#jy_money").html(obj.jy_money);
		
	}
}
 
function consDestroy() {	//销户
	if(!check()) return;
	
	if(!confirm("Voulez-vous vraiment annuler le compte？")){
		return;
	}
	
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("Veuillez choisir les informations du consommateur!");
		return;
	}
	
	var param = {};
	param.rtuId 	= $("#rtu_id").val();
	param.mpId 		= $("#mp_id").val();
	param.cus_state = SDDef.YFF_CUSSTATE_DESTORY;	//客户状态
	param.op_type 	= SDDef.YFF_OPTYPE_DESTORY;		//操作类型--缴费

 	if($("#dqye").val() == ""){
		param.pay_money = 0;	//缴费金额
	}else{
		param.pay_money = parseFloat($("#dqye").val()) * -1;	//缴费金额  -- 负数表示结算退款
	}	
	param.js_money = 0;
	param.zb_money = 0;
	param.all_money = param.pay_money;
	param.buy_times = parseInt($("#buy_times").val());	//购电次数
	
	param.res_id = $("#res_id").val();			//客户编号,存储的是residentpara表中的id
	param.pay_type = $("#pay_type").val();		//缴费方式
	
	param.last_remain = 1;						//结余金额--再议,对应后台解析json，若没有，则后台java中也需去掉，不然报错
	param.remain_money = 1;						//剩余金额--再议,对应后台解析json，若没有，则后台java中也需去掉，不然报错
	
	param.seqNo = $("#seqNo").val();
	param.keyNo = $("#keyNo").val();
	
	param.showvalue = 0;
	param.RND = 0;
 	
	param.meterNo  = $("#commaddr").text();		//表地址
	param.userNo   = $("#userno").text();		
	param.userName = $("#username").text();		
	
	var operType = SDDef.YFF_OPTYPE_DESTORY;	//操作类型
	
 	$.post(def.basePath + "ajaxoper/actOperDyBengal!operDY.action", 		//后台处理程序
		{
			params : jsonString.json2String(param),
			operType : operType
		},
		function(data){
			loading.loaded();			
			$("#canceltoken").css("display","block");
			$("#canceltoken").css("font","bold 18px red");
   			$("#canceltoken").html("Cancellation Token :"+data.result);	
   			cancelToken = data.result;
   			param.token = cancelToken; 
			$.post(def.basePath + "ajaxoper/actSaveDBBengal!operDYSaveDB.action", 		//后台处理程序
			{
				params : jsonString.json2String(param),
				operType : operType
			},
			function(data){
				loading.loaded();
				if(data.result != ""){
 					$("#prt").attr("disabled",false);
 					getPrintParam = eval('(' + data.params + ')');
					//window.top.WebPrint.setYffDataOperIdx2params(data.params,window.top.WebPrint.nodeIdx.dycard);//打印用的参数
					$("#btnDestroy").attr("disabled",true);
					alert("Succès de l'annulation du compte！");
				}
				else{
					alert("Échec de l'annulation du compte ...！");
				}
			
			});
			}
	);	
}


function setDisabled(mode){
	if(!mode){
//		setConsValue(rtnValue,true);
		mygrid.clearAll();
	//	$("#td_bdinf").html("");
		$("#dqye").val("");
		$("#sfbd_sj").html("");
		$("#sfbd").html("");
		$("#writecard_no").html("");
	    //计算客户名称  结算客户分类
		$("#jsyhm").html("");
		$("#yhfl").html("");
//		rtnBD = null;
	}
	$("#dqye").attr("disabled",mode);
	$("#btnImportBD").attr("disabled",mode);
}

function check() {
//	if(rtnBD == null) {
//		alert("Please input base information");
//		return false;
//	}
	if(!isDbl("dqye", "Solde actuel", -rtnValue.moneyLimit, rtnValue.moneyLimit)){
		return false;
	}
	return true;
}