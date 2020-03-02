var rtnValue;
var rtnBD = null;
var openFlag = false;//是否开户
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	
	initGrid();
	
	$("#btnSearch").click(function(){selcons()});//检索
	$("#btnRead").click(function(){read_card()});//读卡
	$("#btnImportBD").click(function(){Import()});
	$("#cardinfo").click(function(){window.top.card_comm.extcardinfo()});//卡内信息
	//$("#metinfo").click(function(){metinfo()});//表内信息
	
	$("#rewrite").click(function(){if(check())dorepair();});//补写缴费记录
	$("#prt").click(function(){printPayRec()});//打印
	
	$("#zbje").keyup(function(){calcTotal();});
	$("#jsje").keyup(function(){calcTotal();});
	$("#jfje").keyup(function(){calcTotal();});
	
	$("#btnSearch").focus();
	/**
	 * 20140729新增 写卡户号一样，户号不一样
	 */
	$("#btnReadSearch").click(function(){readCard_search()});//读卡检索
	
	
	setDisabled(true);
});

function selcons(){//检索
	setDisabled(false);
	var rtnValue1 = doSearch("kzext", -1, rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
	setcons1();
}

function read_card(){	//读卡
	setDisabled(false);
	loading.tip = "正在读卡,请稍后...";
	loading.loading();
	setTimeout("window.top.card_comm.readExtCardSearchDy(" + -1 + ")", 10);
}

//20140729新增,关闭列表窗口时，取消loading
function closeloading(){
	loading.loaded();
}

function readCard_search(){
	setDisabled(false);
	loading.tip = "正在读卡,请稍后...";
	loading.loading();
	//没有了延时，测试哪里会出问题
	//如果该写卡户号只被一个用户使用，直接调用setSearchJson2
	//如果为多个用户使用，则从模态窗口中选择。
	window.top.card_comm.readExtCardSameWrtNo(-1);
}

function setSearchJson2Html(js_tmp) {
	loading.loaded();
	if(!js_tmp)return;
	if(!window.top.card_comm.checkExtInfo(js_tmp)) return;
	
	rtnValue = js_tmp;
	setcons1();
}

function setcons1() {
	if (rtnValue.cacl_type == SDDef.YFF_CACL_TYPE_DL) {//从库中查出的计费方式 是否为电量控
		if ((rtnValue.feeType != SDDef.YFF_FEETYPE_DFL)&& (rtnValue.feeType != SDDef.YFF_FEETYPE_MIX)&&(rtnValue.feeType != SDDef.YFF_FEETYPE_JTFL)) {
			alert("电量方式请选择单费率、混合、阶梯费率方案！");
			return;
		}
	}
		
	setConsValue(rtnValue);
	$("#buy_times").html(rtnValue.buy_times);
	$("#writecard_no").html(rtnValue.writecard_no);
	$("#buy_dl").html(0);
	$("#pay_bmc").html(0);
	
	getRecord(rtnValue.rtu_id, rtnValue.mp_id);
	if(rtnValue.cus_state_id == "" || rtnValue.cus_state_id == 0 || rtnValue.cus_state_id == 50) {
		openFlag = true;
		$("#btnImportBD").attr("disabled", false);
	} else if(rtnValue.cus_state_id == 1){
		openFlag = false;
		$("#btnImportBD").attr("disabled",true);
	} else {
		alert("当前用户状态下不能补写记录。");
		return;
	}
	
	//阶梯费率 开户 时
	if(rtnValue.feeType == 3 && openFlag){
	   $("#jt_total_zbdl").attr("disabled",false).css("background","#fff");
	}
	//其他
	else{
	   $("#jt_total_zbdl").attr("disabled",true).css("background","#ccc");
	}
	
	$("#rewrite").attr("disabled", false);
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno};
	mis_query(param, gloQueryResult);
}

function Import(){
	var tmp = importBD($("#rtu_id").val(),$("#mp_id").val());
	if(!tmp)return;
	rtnBD = tmp;
    $("#td_bdinf").html(rtnBD.td_bdinf);
}

function calcTotal(){//总金额
	
	var zbje = $("#zbje").val()===""?0:$("#zbje").val();
	var jsje = $("#jsje").val()===""?0:$("#jsje").val();
	var jfje = $("#jfje").val()===""?0:$("#jfje").val();
	
	if(isNaN(jfje) || isNaN(jsje) || isNaN(zbje)){
		return;
	}
	
	var zje = round(parseFloat(zbje) + parseFloat(jsje) + parseFloat(jfje),3);
	$("#zongje").html(zje);
	
	var dj_str = $("#feeproj_reteval").val() === "" ? 1 : $("#feeproj_reteval").val();
	var dj_temp = dj_str.split(",");
	var dj = dj_temp[0];				//电价
	
	var tmp_dl, tmp_bmc;

	if(rtnValue.cacl_type == SDDef.YFF_CACL_TYPE_MONEY) {
		$("#buy_dl").html("0.0");
		$("#pay_bmc").html("0.0");		
	}
	else {
		var retcalc = CalcBuyDl(rtnValue.feeType, zje, dj_str, rtnValue.jt_total_dl);
		if (retcalc.retf == -1) {
			tmp_dl  = zje / dj;
		}
		else {
			tmp_dl  = retcalc.buydl; 
		}

		tmp_bmc = tmp_dl / rtnValue.pt / rtnValue.ct;
		
		$("#buy_dl").html(round(tmp_dl, 2));
		$("#pay_bmc").html(round(tmp_bmc, 2));
	}
}

function dorepair(){
	
	if(openFlag){	//补开户记录
		newOne();
	}
	else{			//补缴费记录
		payMoney();
	}
}

function newOne() {
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert(sel_user_info);
		return;
	}
	var params = {};
	params.rtu_id        = rtu_id;
	params.mp_id         = mp_id;
	params.paytype       = $("#pay_type").val();
	params.feeproj_id    = $("#feeproj_id").val();
	params.myffalarmid   = $("#yffalarm_id").val();
	params.pay_money     = $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money   = $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money      = $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money     = Math.round((parseFloat(params.pay_money)+ parseFloat(params.zb_money) + parseFloat(params.othjs_money)),def.point);
	params.jt_total_zbdl = $("#jt_total_zbdl").val() == "" ? 0 :$("#jt_total_zbdl").val();
	
	params.date = rtnBD.date;
	params.time = 0;
	for(var i = 0; i < 3; i++){
		eval("params.mp_id"  + i + "=rtnBD.mp_id" + i);
		eval("params.bd_zy"  + i + "=rtnBD.zbd" + i);
		eval("params.bd_zyj" + i + "=rtnBD.jbd" + i);
		eval("params.bd_zyf" + i + "=rtnBD.fbd" + i);
		eval("params.bd_zyp" + i + "=rtnBD.pbd" + i);
		eval("params.bd_zyg" + i + "=rtnBD.gbd" + i);
	}
	
	//20140617 zp 添加
	params.card_rand 	= rtnValue.card_rand;			//随机数
	params.card_area 	= rtnValue.card_area;			//区域码
		
	params.tz_val		= rtnValue.tz_val				//赊欠门限值
	params.cardtype 	= rtnValue.cardtype;			//预付费电表类型
	params.card_pass	= rtnValue.card_pass;
	params.cacl_type 	= rtnValue.cacl_type;			//金额 表底 量控
	params.buy_dl		= $("#buy_dl").html();
	params.pay_bmc		= 	$("#pay_bmc").html();

	params.operType  = ComntUseropDef.YFF_DYOPER_PAY;	//操作类型决定更新标识
	params.jt_cycle_md = rtnValue.jt_cycle_md;				//低压阶梯年结算日
	params.update_flag = 0x00;
	
	
	modalDialog.height = 320;
	modalDialog.width  = 300;
	modalDialog.url    = def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param  = {
		showGSM : true,
		key	: ["客户编号","用户名称","所供电所", "缴费金额(元)","结算金额(元)","追补金额(元)","总金额(元)"],
		val	: [rtnValue.userno,rtnValue.username,rtnValue.orgname, params.pay_money,params.othjs_money,params.zb_money,params.all_money]		
	};
	
	var o = modalDialog.show();
	if(!o.flag){
		return;
	}
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			userData1      : rtu_id,
			mpid           : mp_id,
			gsmflag        : o.gsm,
			dyOperAddres   : json_str,
			userData2      : ComntUseropDef.YFF_DYOPER_ADDRES
		},
		function(data) {//回传函数
			loading.loaded();
			if (data.result == "success") {
				//mis缴费
				if(parseFloat(params.pay_money) > 0 && gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
					var ret_json = eval("(" + data.dyOperAddres + ")");
					var mis_para 	= {};
					
					//河北南网
					if (provinceMisFlag == "HB") {
						mis_para.rtu_id = params.rtu_id;
						mis_para.mp_id  = params.mp_id;
						mis_para.op_type= SDDef.YFF_DYOPER_ADDRES;
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
						mis_para.op_type= SDDef.YFF_DYOPER_ADDRES;
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
						mis_para.op_type= SDDef.YFF_DYOPER_ADDRES;
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
				
				alert("开户成功!");
				setDisabled(true);
				getRecord(rtnValue.rtu_id, rtnValue.mp_id);
				
				window.top.WebPrint.setYffDataOperIdx2params(data.dyOperAddres,window.top.WebPrint.nodeIdx.dycard);//打印用的参数
				$("#prt").attr("disabled",false);
			}
			else {
				alert("开户失败!" + (data.operErr ? data.operErr : ''));
			}
		}
	);
}

function payMoney() {
	
	var params = {};
	params.rtu_id 			= rtnValue.rtu_id;
	params.mp_id 			= rtnValue.mp_id;
	params.paytype 			= $("#pay_type").val();
	
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money   		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money), def.point);
	params.buynum 			= rtnValue.buy_times;
	
	//20140617 zp 添加
	params.card_rand 	= rtnValue.card_rand;			//随机数
	params.card_area 	= rtnValue.card_area;			//区域码
		
	params.tz_val		= rtnValue.tz_val				//赊欠门限值
	params.cardtype 	= rtnValue.cardtype;			//预付费电表类型
	params.card_pass	= rtnValue.card_pass;
	params.cacl_type 	= rtnValue.cacl_type;			//金额 表底 量控
	params.buy_dl		= $("#buy_dl").html();
	params.pay_bmc		= 	$("#pay_bmc").html();

	params.operType  = ComntUseropDef.YFF_DYOPER_PAY;	//操作类型决定更新标识
	params.jt_cycle_md = rtnValue.jt_cycle_md;				//低压阶梯年结算日
	params.update_flag = 0x00;
	
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
		loading.loaded();
		return;
	}
	params.gsmFlag 			= o.gsm;
	
	loading.loading();
	var json_str = jsonString.json2String(params);
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			userData1		: params.rtu_id,
			mpid 			: params.mp_id,
			gsmflag 		: params.gsmFlag,
			dyOperPay 		: json_str,
			userData2 		: ComntUseropDef.YFF_DYOPER_PAY
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				
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
				
				$("#prt").attr("disabled",false);
				alert("缴费成功!");
				getRecord(rtnValue.rtu_id, rtnValue.mp_id);
				setDisabled(true);
				$("#rewrite").attr("disabled",true);
				window.top.WebPrint.setYffDataOperIdx2params(data.dyOperPay,window.top.WebPrint.nodeIdx.dycard);//打印用的参数
			}
			else {
				alert("向主站发送缴费命令失败,请补写缴费记录!" + (data.operErr ? data.operErr : ''));
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

	if(!isDbl("zbje" ,"追补金额",-rtnValue.moneyLimit, rtnValue.moneyLimit)){
		return false;
	}
	
	if(!isDbl("jsje" ,"结算金额",-rtnValue.moneyLimit, rtnValue.moneyLimit)){
		return false;
	}
	
	if(!isDbl("jfje" ,"缴费金额",0, rtnValue.moneyLimit)){
		return false;
	}
	
	if(!isDbl_Html("zje" ,  "总金额"  , 0, rtnValue.moneyLimit, false)){//总金额应该在0~囤积值之间
		return false;
	}

	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
	}

	if(!isDbl("jt_total_zbdl" ,"追补电量",0, 999999)){
		return false;
	}
	
	if(openFlag && rtnBD == null) {
		alert("请输入表底");
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

function setDisabled(mode){
	if(!mode){
		//清空界面的信息,用于开户后再次查询,而无查询结果的情况
		setConsValue(rtnValue,true);
		$("#jsje").val("");
		$("#jfje").val("");
		$("#zbje").val("");
		$("#buy_times").html("");
		$("#jt_total_zbdl").val("");
		$("#zongje").html("");
		$("#writecard_no").html("");
		//计算客户名称  结算客户分类
		$("#jsyhm").html("");
		$("#yhfl").html("");
		rtnBD = null;
	}
	$("#jt_total_zbdl").attr("disabled",mode);
	$("#jfje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#zbje").attr("disabled",mode);
}

function check_JT_total_zbdl(){//阶梯费率 则 旧表基础电量可用			
	if(rtnValue.feeType == SDDef.YFF_FEETYPE_JTFL || rtnValue.feeType == SDDef.YFF_FEETYPE_MIXJT){
	   $("#jt_total_zbdl").attr("disabled",false).css("background","#fff");
	}else{
	   $("#jt_total_zbdl").attr("disabled",true).css("background","#ccc");
	}
}

function printPayRec(){//打印
	var cardtype = window.top.WebPrint.nodeIdx.dycard;
	if (rtnValue.cacl_type == SDDef.YFF_CACL_TYPE_DL) {//从库中查出的计费方式 是否为电量控
		cardtype = window.top.WebPrint.nodeIdx.dycarddl;
	}
	
	var filename =  window.top.WebPrint.getTemplateFileNm(cardtype);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.page_type = cardtype;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}