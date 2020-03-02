//开户
var rtnValue 	= null;	//检索用户信息
var rtnBD	 	= null;	//表底信息
var rtnzbd 		= 0.00;
//var CARD_TYPE	= "01";	//电卡类型 开卡01
var WRITECARD_TYPE= window.top.SDDef.YFF_CARDMTYPE_KE001;//卡表类型
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	$("#btnSearch").click(function()	{selcons()});					//查找
	$("#btnNew").click(function()		{if(check())wrtCard_open()});	//开户
//	$("#rtuInfo").click(function()		{rtuInfo()});
	$("#btnImportBD").click(function()	{ImportBd()});					//录入表底
	$("#btnPrt").click(function(){showPrint()});//打印
	
	$("#cardInfo").click(function()		{window.top.card_comm.cardinfo()});
	
	$("#yffalarm_alarm1").keyup(function(){if(isNaN(this.value))this.value=0;});
	$("#yffalarm_alarm2").keyup(function(){if(isNaN(this.value))this.value=0;});

	$("#jfje").keyup(function(){calcu();});
	$("#zbje").keyup(function(){calcu();});
	$("#jsje").keyup(function(){calcu();});

	setDisabled(true);
	$("#btnSearch").focus();
});

function calcu() {
	var zbje = 0.0, jfje = 0.0, jsje = 0.0;
	var rate = 1;
	
	zbje = $("#zbje").val()==="" ? 0 : $("#zbje").val();
	jfje = $("#jfje").val()==="" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val()==="" ? 0 : $("#jsje").val();
	rate = $("#blv").val() ==="" ? 1 : $("#blv").val();
	
	var zje = round(parseFloat(zbje) + parseFloat(jfje) + parseFloat(jsje),2);
		
	// 区分 金额 电量
	if(!isNaN(zje)){

		$("#zje").html(zje);
		var ke003f = (WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE003);	

		if (ke003f) {
			var dj 	= $("#feeproj_reteval").val()===""?1:$("#feeproj_reteval").val();
			var blv = $("#blv").html()===""?1:$("#blv").html();
			var zbd = (rtnzbd==="" || isNaN(rtnzbd)) ? 0 : rtnzbd;
			var alarm_val1 = (rtnValue.yffalarm_alarm1 == "" || rtnValue.yffalarm_alarm1 == undefined ) ? 30 : rtnValue.yffalarm_alarm1;
			var alarm_val2 = (rtnValue.yffalarm_alarm2 == "" || rtnValue.yffalarm_alarm2 == undefined ) ? 5 : rtnValue.yffalarm_alarm2;
			
			var ret_val = cardalarm_calcu(zje, dj, blv, zbd, alarm_val1, alarm_val2, rtnValue.type, rtnzbd);

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

function wrtCard_open(){
	var params =　{};
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money   		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),2);
    
	loading.loading();
	
	modalDialog.height 	= 300;
	modalDialog.width 	= 280;
	modalDialog.url 	= def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param 	= {
		showGSM : true,
		key	: ["客户编号","客户名称", "缴费金额","追补金额","结算金额","总金额"],
		val	: [$("#userno").html(),$("#username").html(), params.pay_money,params.zb_money,params.othjs_money,params.all_money]		
	};
	
	var o = modalDialog.show();
	
	if(!o||!o.flag){
		loading.loaded()
		return;
	}
	params.gsmFlag = o.gsm;
	window.setTimeout("wrtCard_open1(" + o.gsm + ")", 10);
}

function wrtCard_open1(gsm){
	//	calWrtCard();
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
	params.buynum 			= 1;
	
	params.alarm_val1		= $("#yffalarm_alarm1").val();
	params.alarm_val2		= $("#yffalarm_alarm2").val();
	
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money   		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),2);
	
	params.date 			= 0;
	params.time 			= 0;
	params.buy_dl 			= $("#buy_dl").html()==="" ? 0:$("#buy_dl").html();
	params.pay_bmc 			= $("#pay_bmc").html()===""? 0:$("#pay_bmc").html();
    
	params.gsmFlag 			= gsm;
	
	params.pt 				= rtnValue.pt_ratio;
	params.ct 				= rtnValue.ct_ratio;
	params.meter_type 		= WRITECARD_TYPE;
	params.card_type  		= SDDef.CARD_OPTYPE_OPEN;//开户、缴费、补卡
	params.limit_dl 		= money_limit;
	params.feeproj_id 		= $("#feeproj_id").val();
	params.writecard_no		= $("#writecard_no").html();
	params.meterno  		= rtnValue.meter_id;
	params.cardno 			= $("#userno").html();
	params.operType  = ComntUseropDef.YFF_GYOPER_ADDCUS;	//操作类型决定更新标识

	if (CheckCard(params) == false){
		loading.loaded();
		return;
	} 
	$.post( def.basePath + "ajaxoper/actWebCard!getcardWrtInfo.action", 		//后台处理程序
		{
			result	: jsonString.json2String(params)
		},
		function(data) {			    	//回传函数
			loading.loaded();
			var retStr 		 = data.result.toString();
			var ret_json = window.top.card_comm.writecard(retStr);
			if(ret_json.errno  == 0){
				wrtDB_open(params);
			}else{
				loading.loaded();
				alert("写卡失败！\n" + ret_json.errsr);
			}
		}
	);
}

function wrtDB_open(params){
	params.shutdown_bd 		= 0;
	params.total_yzbdl 		= $("#total_yzbdl").val() == "" ? 0 : $("#total_yzbdl").val();
	params.total_wzbdl		= $("#total_wzbdl").val() == "" ? 0 : $("#total_wzbdl").val();
	params.date 			= rtnBD.date;
	params.time				= 0;
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
		}
		else{
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
	//params 最后一个参数是否发送短信  赋值  进行action请求
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
		{
			userData1		: params.rtu_id,
			zjgid 			: params.zjg_id,
			gsmflag 		: params.gsmFlag,
			gyOperAddCus 	: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_ADDCUS
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
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
				
				$("#buy_times").val(1);
				$("#btnNew").attr("disabled",true);
				$("#btnPrt").attr("disabled",false);
    			window.top.WebPrint.setYffDataOperIdx2params(data.gyOperAddCus);//打印用的参数

				alert("开户成功!");
				setDisabled(true);
				$("#btnNew").attr("disabled",true);
			}
			else {
				alert("写卡成功，向主站发送开户命令失败。请补写开户记录。" + (data.operErr ? data.operErr : ''));
			}
		}
	);
}

function selcons(){//检索
	//每次检索都要禁用开户键
	$("#btnNew").attr("disabled",true);
	
	var js_tmp = doSearch("ks",ComntUseropDef.YFF_GYOPER_ADDCUS, rtnValue);
	if(!js_tmp)	return;
	if(!window.top.card_comm.checkInfo(js_tmp)) return;

	rtnValue = js_tmp;

	setConsValue(rtnValue);			//setCons.js中函数
	setCardExt(rtnValue);

	getAlarmValue(rtnValue.yffalarm_id);//获取报警方式
	getMoneyLimit(rtnValue.feeproj_id);

    //mis.js中函数   miss查询函数
	var param = {rtu_id : rtnValue.rtu_id, zjg_id : rtnValue.zjg_id, busi_no : rtnValue.userno};
	mis_query(param, gloQueryResult);

	setDisabled(false);
	
	$("#btnImportBD").focus();
}

function ImportBd(){
	var tmp = importBD(rtnValue.rtu_id, rtnValue.zjg_id);
	if(!tmp){
		return;
	}
	if(tmp.td_bdinf == ""){
		$("#td_bdinf").html("未录入表底信息");
	}else{
		rtnBD = tmp;

		var zdl=0.0;
		zdl = zdl + parseFloat(isNaN(rtnBD.zbd0) ? 0 : rtnBD.zbd0);
		zdl = zdl + parseFloat(isNaN(rtnBD.zbd1) ? 0 : rtnBD.zbd1);
		zdl = zdl + parseFloat(isNaN(rtnBD.zbd2) ? 0 : rtnBD.zbd2);
		rtnzbd = round(zdl,def.point);
		$("#td_bdinf").html(rtnBD.td_bdinf);
	}
	$("#btnNew").attr("disabled",false);
}

function check() {
	//如果MIS不通，禁止缴费
	if (gloQueryResult.misUseflag == "true" && (gloQueryResult.misOkflag == "false" || gloQueryResult.misOkflag == undefined)) {
		alert("MIS不能通讯,禁止开户...");
		return false;
	}
	//20140116 dubr 写卡户号为空或者0 禁止开户
	if(rtnValue.writecard_no == "" || rtnValue.writecard_no == 0){
		alert("请先配置写卡户号!");
		return false;
	}
	//end
	if(rtnBD == null){
		alert("未录入表底!");
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

	//20130402
	var zje = $("#zje").html();
	if(!isDbl_Html("zje" ,  "总金额"  , 0, money_limit, false)){//总金额应该在0~囤积值之间
		return false;
	}

	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
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
	
	return true;
}

function setDisabled(mode){
	if(!mode){
		$("#zbje").val("");
		$("#jfje").val("");
		$("#jsje").val("");
		$("#zje").html("");
		$("#yffalarm_alarm1").val("");
		$("#yffalarm_alarm2").val("");
		$("#td_bdinf").html("");
		$("#total_yzbdl").val("");
		$("#total_wzbdl").val("");
		rtnBD = null;
	}
	
	$("#zbje").attr("disabled",mode);
	$("#jfje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#yffalarm_alarm1").attr("disabled",mode);
	$("#yffalarm_alarm2").attr("disabled",mode);
	$("#total_yzbdl").attr("disabled",mode);
	$("#total_wzbdl").attr("disabled",mode);
	
	$("#btnImportBD").attr("disabled",mode);
//	$("#cardInfo").attr("disabled",mode);
//	$("#rtuInfo").attr("disabled",mode);
//	$("#btnNew").attr("disabled",mode);
}

//根据卡表类型不同设置不同的界面显示及赋值

//此处要求传入变量， 以后可以提到一个公共函数
function setCardExt(rtnValue)
{
	//add ext
	var tmp_val = rtnValue.cardmeter_type.split("]");
	WRITECARD_TYPE = tmp_val[0].split("[")[1];
//调整 20130402	
//	WRITECARD_TYPE = tmp_val[0].substring(1);
	
	//给卡表类型赋值
	$("#cardmeter_type").html(tmp_val[1]);
	$("#writecard_no").html(rtnValue.writecard_no);
	$("#meter_id").html(rtnValue.meter_id);
	
	var ke003f = (WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE003);

	if (ke003f) {
		$("#gdlctrl").show();
	}
	else {
		$("#gdlctrl").hide();
	}

	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
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
	
	var meterNo 	= json_out.meterno; 	//表号
	var consNo  	= json_out.consno;		//户号
	var buyTime		= json_out.pay_num;		//购电次数	

	//不太严谨 		需要调整
	if (meterType == window.top.SDDef.YFF_METER_TYPE_6103)	{
		var tmp = "00000000000000000000";

		if ((tmp != consNo) || (parseInt(buyTime) != 0)) {
			alert("开户前请先清空卡！")
			return false;
		} 
	}
	else {
		var tmp = "000000000000"
		if ((tmp != meterNo) || (tmp != consNo))		{
			alert("开户前请先清空卡，不能缴费！")
			return false;
		}
	}
	return true;
}