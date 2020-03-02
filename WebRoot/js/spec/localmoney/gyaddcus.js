var rtnValue;
var rtnBD = null;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	
	$("#btnSearch").click(function(){selcons()});	
	$("#btnPrt").click(function(){showPrint()});
	$("#btnNew").click(function(){if(check())addNewCus()});
	$("#rtuInfo").click(function(){rtuInfo()});
	$("#btnImportBD").click(function(){Import()});
	
	$("#yffalarm_alarm1").keyup(function(){if(isNaN(this.value))this.value=0;});
	$("#yffalarm_alarm2").keyup(function(){if(isNaN(this.value))this.value=0;});
	
	setTextDisabled(true);
});

function Import(){
	var tmp = importBD($("#rtu_id").val(),$("#zjg_id").val());
	if(!tmp){
		return;
	}
	if(tmp.td_bdinf == ""){
		$("#td_bdinf").html("未录入表底信息");
	}else{
		rtnBD = tmp;
		$("#td_bdinf").html(rtnBD.td_bdinf);
		$("#btnNew").attr("disabled",false);
	
	}
}


function calMoney(){

	var zbje = 0.0, jfje = 0.0, jsje = 0.0;
	
	zbje = $("#zbje").val()==="" ? 0 : $("#zbje").val();
	jfje = $("#jfje").val()==="" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val()==="" ? 0 : $("#jsje").val();
	
	var zje = round(parseFloat(zbje) + parseFloat(jfje) + parseFloat(jsje),def.point);
	
	if(!isNaN(zje)){
		
		setAlarmValue(rtnValue.yffalarm_alarm1, rtnValue.yffalarm_alarm2, zje);
		
		$("#zje").html(zje);
	}
}

function rtuInfo(){		//终端内信息-type bd或者je-
	modalDialog.width = 750;
	modalDialog.height = 190;
	modalDialog.url = "../dialog/callRemain.jsp";
	modalDialog.param = {
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
	modalDialog.show();
}

function selcons(){//检索
	var tmp = doSearch("je",ComntUseropDef.YFF_GYOPER_ADDCUS, rtnValue);
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, zjg_id : rtnValue.zjg_id, busi_no : rtnValue.userno};
	mis_query(param,gloQueryResult);
	
	setConsValue(rtnValue);
	
	getMoneyLimit();		//囤积限值
	getAlarmValue();		//获取报警方式
	
	$("#rtuInfo").attr("disabled",false);
	$("#btnImportBD").attr("disabled",false);
	$("#btnPrt").attr("disabled",true);
	
	setTextDisabled(false);
	$("#buy_times").val(0);

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
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
}

function setTextDisabled(mode){	//设置文本框状态（disabled/enabled）
	if(!mode){
		$("#zbje").val("");
		$("#jfje").val("");
		$("#jsje").val("");
		$("#yffalarm_alarm1").val("");
		$("#yffalarm_alarm2").val("");
		
		$("#td_bdinf").html("");
		
		$("#zje").html("");
	}
	$("#zbje").attr("disabled",mode);
	$("#jfje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#yffalarm_alarm1").attr("disabled",mode);
	$("#yffalarm_alarm2").attr("disabled",mode);
	
}

function addNewCus(){
	if(!rtnBD){
		alert("请录入表底...");
		return;
	}
	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.paytype 			= $("#pay_type").val();
	params.yffalarm_id 		= $("#yffalarm_id").val();
	
	params.buynum 			= 0;
	
	params.pay_money 		= $("#jfje").val()=== "" ? 0 : $("#jfje").val();
	params.othjs_money 		= $("#jsje").val()=== "" ? 0 : $("#jsje").val();
	params.zb_money 		= $("#zbje").val()=== "" ? 0 : $("#zbje").val();
	params.all_money 		= $("#zje").html()=== "" ? 0 : $("#zje").html();
	
	if(parseFloat(params.all_money) > parseFloat(money_limit)){
		alert("总金额【"+params.all_money+"】大于囤积限值【"+money_limit+"】");
		return;
	}
	
	params.alarmmoney		= $("#yffalarm_alarm1").val();
	params.buydl			= 0;
	params.paybmc			= 0;
	params.alarmbd			= 0;
	
	params.shutbd			= 0;
	params.yffflag			= $("#start_yff_flag").attr('checked') ? 1 : 0;
	
	if ($("#dpfs").attr('checked')) {
		params.plustime		= 0;
	}
	else {
		params.plustime		= $("#plus_width").val() === "" ? 0 : $("#plus_width").val();
	}	
	
	
	modalDialog.height = 300;
	modalDialog.width = 280;
	modalDialog.url = def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param = {
		showGSM : true,
		key	: ["客户编号","客户名称", "缴费金额","追补金额","结算金额","总金额"],
		val	: [$("#userno").html(),$("#username").html(), params.pay_money,params.zb_money,params.othjs_money,params.all_money]		
	};
	
	var o = modalDialog.show();
	
	if(!o||!o.flag){
		return;
	}
	
	var gsmFlag = o.gsm;
	
	var json_str = jsonString.json2String(params);
	
	loading.loading();
	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommGy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_GYCOMM_PAY;
	//end
	
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");

	$.post( def.basePath + "ajaxoper/actCommGy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag 	: true,
			userData1		: params.rtu_id,
			zjgid			: params.zjg_id,
			gsmflag 		: gsmFlag,		//界面提示是否发送短信
			gyCommPay 		: json_str,
			userData2 		: ComntUseropDef.YFF_GYCOMM_PAY
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				addDB();
			}else{
				window.top.addStringTaskOpDetail("接收预付费服务任务失败.");
				alert("通讯失败...");
			}
		}
	);
}

function addDB(){		//开户
	
	var params = {};
	
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.myffalarmid 		= $("#yffalarm_id").val();
	params.paytype 			= $("#pay_type").val();
	params.buynum 			= 1;
	
	params.alarm_val1		= $("#yffalarm_alarm1").val();
	params.alarm_val2		= $("#yffalarm_alarm2").val();
	
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	
	params.all_money = round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),def.point);
	params.buy_dl = 0;
	params.pay_bmc = 0;
	params.shutdown_bd = params.all_money;
	params.total_yzbdl = $("#total_yzbdl").val() == "" ? 0 : $("#total_yzbdl").val();
	params.total_wzbdl = $("#total_wzbdl").val() == "" ? 0 : $("#total_wzbdl").val();
	
	params.date = rtnBD.date;
	params.time = 0;
	
	for(var i = 0; i < 3; i++){
		eval("params.mp_id"  + i + "=rtnBD.mp_id" + i);
		
		if(rtnBD.zf_flag.substring(i, i + 1) == "0"){	//正向
			eval("params.bd_zy"  + i + "=rtnBD.zbd" + i);
			eval("params.bd_zyj" + i + "=rtnBD.jbd" + i);
			eval("params.bd_zyf" + i + "=rtnBD.fbd" + i);
			eval("params.bd_zyp" + i + "=rtnBD.pbd" + i);
			eval("params.bd_zyg" + i + "=rtnBD.gbd" + i);
			eval("params.bd_zw"  + i + "=rtnBD.wbd" + i);
			eval("params.bd_fy"  + i + "=0");
			eval("params.bd_fyj" + i + "=0");
			eval("params.bd_fyf" + i + "=0");
			eval("params.bd_fyp" + i + "=0");
			eval("params.bd_fyg" + i + "=0");
			eval("params.bd_fw"  + i + "=0");
		}else{
			eval("params.bd_zy"  + i + "= 0");
			eval("params.bd_zyj" + i + "= 0");
			eval("params.bd_zyf" + i + "= 0");
			eval("params.bd_zyp" + i + "= 0");
			eval("params.bd_zyg" + i + "= 0");
			eval("params.bd_zw"  + i + "= 0");
			eval("params.bd_fy"  + i + "= rtnBD.zbd" + i);
			eval("params.bd_fyj" + i + "= rtnBD.jbd" + i);
			eval("params.bd_fyf" + i + "= rtnBD.fbd" + i);
			eval("params.bd_fyp" + i + "= rtnBD.pbd" + i);
			eval("params.bd_fyg" + i + "= rtnBD.gbd" + i);
			eval("params.bd_fw"  + i + "= rtnBD.wbd" + i);
		}
		
	}

	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
		{
			userData1		: params.rtu_id,
			zjgid 			: params.zjg_id,
			gsmflag 		: 0,
			gyOperAddCus 	: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_ADDCUS
		},
		function(data) {			    	//回传函数
			window.top.addJsonOpDetail(data.detailInfo);
			loading.loaded();
			if (data.result == "success") {
				$("#btnNew").attr("disabled",true);
				$("#btnImportBD").attr("disabled",true);
				$("#btnPrt").attr("disabled",false);
				
				$("#buy_times").val(1);
				
				setTextDisabled(true);
				
				//mis缴费
				if(parseFloat(params.pay_money) > 0 && gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
					var ret_json = eval("(" + data.gyOperAddCus + ")");
					var mis_para 	= {};
					
					mis_para.rtu_id = ret_json.rtu_id;           
					mis_para.zjg_id = params.zjg_id;             
					mis_para.op_type= SDDef.YFF_OPTYPE_ADDRES;   
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
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("开户成功!");
				}else{
					window.top.addStringTaskOpDetail("开户成功!");
				}
				$("#btnPrt").attr("disabled",false);
    			window.top.WebPrint.setYffDataOperIdx2params(data.gyOperAddCus,window.top.WebPrint.nodeIdx.gyycmoney);//打印用的参数

			}
			else {
				if(window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("向主站发送开户命令失败!");
				}else{
					window.top.addStringTaskOpDetail("向主站发送开户命令失败!");
				}
			}
		}
	);
}

function check(){
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
	
	if(!isDbl("zbje" , "追补金额" ,-money_limit, money_limit, false)){//追补金额应该在0~囤积值之间
		return false;
	}
	
	if(!isDbl("jsje",  "结算金额" ,-money_limit , money_limit, false)){
		return false;
	}
	
	if(!isDbl("jfje",  "缴费金额" ,0 , money_limit, false)){
		return false;
	}

	if(!isDbl_Html("zje" ,  "总金额"  ,0 , money_limit, false)){//缴费金额应该在0~囤积值之间
		return false;
	}
	
	var total_yzbdl = $("#total_yzbdl").val();
	var total_wzbdl = $("#total_wzbdl").val();
	if(isNaN(total_yzbdl)){
		alert("有功追补电量应该为数字!");
		$("#total_yzbdl").select().focus();
		return false;
	}
	if(isNaN(total_wzbdl)){
		alert("无功追补电量应该为数字!");
		$("#total_wzbdl").select().focus();
		return false;
	}
	
	if(($("#jfje").val()=="0"||$("#jfje").val()=="")){
		return(confirm("客户未缴费,继续操作吗?"))
	}
	
	return true;
}

function showPrint(){//打印发票
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.gyycmoney);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintGy();
}