var rtnValue =null;
var rtnBD;
//var //WRITECARD_TYPE = window.top.SDDef.YFF_CARDMTYPE_KE001;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	
	$("#btnSearch").click(function(){selcons()});
	
	$("#cardinfo").click(function(){window.top.card_comm.extcardinfo()});
//	$("#metinfo").click(function(){metinfo()});
	$("#btnImportBD").click(function(){Import()});
	
	$("#btnNew").click(function(){if(check())wrtCard_open()});
	$("#btnPrt").click(function(){printPayRec()});
	
	$("#yzje").change(function(){calcTotal();});
	$("#jfje").keyup(function(){calcTotal();});
	$("#jsje").keyup(function(){calcTotal();});
	
	setDisabled(true);
	$("#btnSearch").focus();
});

function Import(){
	var tmp = importBD($("#rtu_id").val(),$("#mp_id").val());
	if(!tmp)return;
	rtnBD = tmp;
    $("#td_bdinf").html(rtnBD.td_bdinf);
	$("#btnNew").attr("disabled",false);
}

function selcons(){//检索
	//每次检索都要禁用开户按钮
	$("#btnNew").attr("disabled","disabled");
	$("#btnPrt").attr("disabled","disabled");
	
	var rtnValue1 = doSearch("kzext",ComntUseropDef.YFF_DYOPER_ADDRES,rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;

	if (rtnValue.cacl_type == SDDef.YFF_CACL_TYPE_DL) {//从库中查出的计费方式 是否为电量控
		if ((rtnValue.feeType != SDDef.YFF_FEETYPE_DFL)&& (rtnValue.feeType != SDDef.YFF_FEETYPE_MIX)&&(rtnValue.feeType != SDDef.YFF_FEETYPE_JTFL)) {
			alert("电量方式请选择单费率、混合、阶梯费率方案！");
			return;
		}
	}
		
	setDisabled(false);
	setConsValue(rtnValue);
	check_JT_total_zbdl();
	$("#writecard_no").html(rtnValue.writecard_no);
	$("#buy_dl").html(0);
	$("#pay_bmc").html(0);
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno,provinceMisFlag : provinceMisFlag};
	mis_query(param,gloQueryResult);
	
	$("#btnImportBD").focus();
}

//function metinfo(){//表内信息
//	modalDialog.width = 800;
//	modalDialog.height = 600;
//	modalDialog.url = def.basePath + "jsp/dyjc/dialog/meterInfo.jsp";
//	modalDialog.param = {"rtuId":$("#rtu_id").val() ,"mpId":$("#mp_id").val()};
//	modalDialog.show();
//}

function wrtDB_open(params){		//开户
	
	params.jt_total_zbdl = $("#jt_total_zbdl").val() == "" ? 0 : $("#jt_total_zbdl").val();
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
	
	loading.loading();
	var json_str = jsonString.json2String(params);
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			userData1		: params.rtu_id,
			mpid 			: params.mp_id,
			gsmflag 		: params.gsmFlag,
			dyOperAddres 	: json_str,
			userData2 		: ComntUseropDef.YFF_DYOPER_ADDRES
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				
				if(parseFloat(params.pay_money) > 0 && gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
					var ret_json = eval("(" + data.dyOperAddres + ")");
					var mis_para 	= {};
					
					//河北南网
					if (provinceMisFlag == "HB") {
						mis_para.rtu_id = params.rtu_id;
						mis_para.mp_id  = params.mp_id;
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
						mis_para.op_type= SDDef.YFF_OPTYPE_ADDRES;
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
						mis_para.op_type= SDDef.YFF_OPTYPE_ADDRES;
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
				window.top.WebPrint.setYffDataOperIdx2params(data.dyOperAddres,window.top.WebPrint.nodeIdx.dycarddl);//打印用的参数
				$("#btnNew").attr("disabled",true);
				$("#btnPrt").attr("disabled",false);
			}
			else {
				alert("向主站发送开户命令失败,请补写开户记录!" + (data.operErr ? data.operErr : ''));
			}
		}
	);
}


function calcTotal(){//总金额
	
	var yzje = $("#yzje").val();
	var jfje = $("#jfje").val() === "" ? 0 : $("#jfje").val();
	var jsje = $("#jsje").val() === "" ? 0 : $("#jsje").val();
	
	if(isNaN(jfje) || isNaN(jsje)){
		return;
	}
	
	var zje = round(parseFloat(jfje) + parseFloat(jsje) - parseFloat(yzje),3);
	$("#zje").html(zje);
	
	var dj_str = $("#feeproj_reteval").val() === "" ? 1 : $("#feeproj_reteval").val();
	var dj_temp = dj_str.split(",");
	var dj = dj_temp[0];
	
	var tmp_dl = 0.0, tmp_bmc = 0.0;

	if(rtnValue.cacl_type == SDDef.YFF_CACL_TYPE_MONEY) {
		$("#buy_dl").html("0.0");
		$("#pay_bmc").html("0.0");		
	}
	else {
		var retcalc = CalcBuyDl(rtnValue.feeType, zje, dj_str, rtnValue.jt_total_dl);
		if (retcalc.retf == -1) {
			tmp_dl  = Number(zje) / Number(dj);
		}
		else {
			tmp_dl  = retcalc.buydl; 
		}
		tmp_bmc = Number(tmp_dl) / Number(rtnValue.pt) / Number(rtnValue.ct);
	
		$("#buy_dl").html(round(tmp_dl, 2));
		$("#pay_bmc").html(round(tmp_bmc, 2));
	}	
	
}

function wrtCard_open(){
	var params = {};
	
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#yzje").val();
	params.all_money   		= round(parseFloat(params.pay_money) - parseFloat(params.zb_money) + parseFloat(params.othjs_money),2);
	
	loading.loading();
	modalDialog.height 	= 300;
	modalDialog.width 	= 280;
	modalDialog.url 	= def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param 	= {
		showGSM : true,
		key	: ["客户编号","客户名称", "缴费金额","预置金额","结算金额","总金额"],
		val	: [$("#userno").html(),$("#username").html(), params.pay_money,params.zb_money,params.othjs_money,params.all_money]		
	};
	
	var o = modalDialog.show();
	
	if(!o||!o.flag){
		loading.loaded();
		return;
	}
	
	params.gsmFlag = o.gsm;
	
	window.setTimeout("wrtCard_open1(" + o.gsm + ")", 10);
}
function wrtCard_open1(gsm) {
	
	if (!CheckCard()) {
		loading.loaded();
		return false;
	}
	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.mp_id 			= $("#mp_id").val();
	params.myffalarmid 		= $("#yffalarm_id").val();
	params.paytype 			= $("#pay_type").val();
	
	params.alarm_val1		= rtnValue.yffalarm_alarm1;
	params.alarm_val2		= rtnValue.yffalarm_alarm2;
	
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= -$("#yzje").val();
	params.all_money   		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),2);
	params.gsmFlag 			= gsm;
	
	params.pt 				= rtnValue.pt;
	params.ct 				= rtnValue.ct;
//	params.meter_type 		= //WRITECARD_TYPE;
	params.card_type  		= SDDef.CARD_OPTYPE_OPEN;//开户、缴费、补卡
	params.limit_dl 		= rtnValue.moneyLimit;
	params.feeproj_id 		= $("#feeproj_id").val();
	params.writecard_no		= rtnValue.writecard_no;
	params.meterno  		= rtnValue.esamno;
	params.cardno 			= rtnValue.userno;
	params.buynum			= 1;
	
	params.tz_val		= rtnValue.tz_val				//赊欠门限值
	params.card_rand 	= rtnValue.card_rand;			//随机数
	params.card_area 	= rtnValue.card_area;			//区域码
		
	params.cardtype 	= rtnValue.cardtype;			//预付费电表类型
	
	if ((params.cardtype == "JJ_003") || (params.cardtype == "JJ_004")) {					//JJ_003 处理  与后台联动
		params.card_pass	= ""
	}
	else {
		params.card_pass	= rtnValue.card_pass;
	}
	
	params.cacl_type 	= rtnValue.cacl_type;			//金额 表底 量控
	params.buy_dl		= $("#buy_dl").html();
	params.pay_bmc		= $("#pay_bmc").html();
	
	params.operType  = ComntUseropDef.YFF_DYOPER_ADDRES;	//操作类型决定更新标识
	params.jt_cycle_md = rtnValue.jt_cycle_md;				//低压阶梯年结算日
	
	$.post( def.basePath + "ajaxoper/actWebCard!getWriteCardExt.action", 		//后台处理程序
		{
			result	: jsonString.json2String(params)
		},
		function(data) {			    	//回传函数
			loading.loaded();
			var retStr 		 = data.result;
			var ret_json = window.top.card_comm.writeExtcard(retStr);
			if(ret_json.errno  == 0){
				if ((params.cardtype == "JJ_003")|| (params.cardtype == "JJ_004")){
					params.update_flag = 0x02;
					params.card_pass = ret_json.strinfo;			//传到后台 数据库中存储  作为密码
				}
				else {
					params.update_flag = 0x00;
				}
				
				wrtDB_open(params);
			}else{
				loading.loaded();
				alert("写卡失败！\n" + ret_json.errsr);
			}
		}
	);
}

function CheckCard()
{
	var json_out = {};
	json_out = window.top.card_comm.readExtcard();
	if(json_out == undefined || json_out ==""){
		alert("读卡错误!");
		return  false;				
	}

	var meterNo,consNo,buyTime;				//表号，户号,购电次数
	meterNo 	= json_out.meterno; 		//表号
	consNo  	= json_out.consno;			//户号
 	buyTime		= json_out.back_pay_num;	//返写购电次数

	var tmp = "000000000000"
	if (Number(json_out.back_flag) > 0) {
		alert("开户前请先清空卡，不能开户！")
		return false;
	}
	else if (consNo.replace(/(^0*)/g, "") != "") {
		alert("开户前请先清空卡，不能开户！")
		return false;
	}
	else if (meterNo.replace(/(^0*)/g, "") != "") {
		alert("开户前请先清空卡，不能开户！")		
		return false;
	}
	
//	var meterType = json_out.meter_type;		//卡表类型
//	var meterNo,consNo;							//表号，户号

//	if((WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE001) && (meterType != window.top.SDDef.YFF_METER_TYPE_ZNK)){
//		alert("用户电表为2009版规约，请使用09版购电卡!");
//		return false;
//	}
//	
//	if((WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE006 && meterType != window.top.SDDef.YFF_METER_TYPE_ZNK2)){
//		alert("用户电表为2013版规约，请使用13版购电卡!");
//		return false;
//	}
	
	//20131021添加SDDef.YFF_METER_TYPE_ZNK2判断
//	if(meterType == window.top.SDDef.YFF_METER_TYPE_ZNK || meterType == window.top.SDDef.YFF_METER_TYPE_ZNK2){
//		meterNo 	= json_out.meterno;
//		consNo  	= json_out.consno;
//	}

//	var tmp = "000000000000"
//	if ((tmp != meterNo) || (tmp != consNo))		{
//		alert("开户前请先清空卡，不能开户！")
//		return false;
//	}
	
	return true;
}

function setDisabled(mode){
	if(!mode){
		//清空界面的信息,用于开户后再次查询,而无查询结果的情况
		setConsValue(rtnValue,true);
		$("#jfje").val("");
		$("#jsje").val("");
		$("#zje").html("");
		$("#td_bdinf").html("");
		$("#jt_total_zbdl").val("");
		$("#writecard_no").html("");
		//结算客户名称  结算客户分类
		$("#jsyhm").html("");
		$("#yhfl").html("");
		rtnBD = null;
	}
	$("#jfje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#jt_total_zbdl").attr("disabled",mode);
	$("#btnImportBD").attr("disabled",mode);
	$("#yzje").attr("disabled",mode)
	$("#jt_total_zbdl").attr("disabled",mode);
}

function check_JT_total_zbdl(){//阶梯费率 则 旧表基础电量可用			
	if(rtnValue.feeType == SDDef.YFF_FEETYPE_JTFL || rtnValue.feeType == SDDef.YFF_FEETYPE_MIXJT){
	   $("#jt_total_zbdl").attr("disabled",false).css("background","#fff");
	}else{
	   $("#jt_total_zbdl").attr("disabled",true).css("background","#ccc");
	}
}

function check(){
	//如果MIS不通，禁止缴费
	if (gloQueryResult.misUseflag == "true" && (gloQueryResult.misOkflag == "false" || gloQueryResult.misOkflag == undefined)) {
		alert("MIS不能通讯,禁止开户...");
		return false;
	}
	//20140116 dubr 写卡户号为空或者0 禁止开户
	if(rtnValue.writecard_no=="" || rtnValue.writecard_no==0){
		alert("请先配置写卡户号!");
		return false;
	}
	//end		
	var jfje = 0, zbje = 0, jsje = 0;
	jfje = $("#jfje").val()==="" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val()==="" ? 0 : $("#jsje").val();
	
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
	
	if(!isDbl("jsje",  "结算金额", -rtnValue.moneyLimit, rtnValue.moneyLimit, false)){
		return false;
	}

	if(!isDbl("jfje",  "缴费金额", 0, rtnValue.moneyLimit, false)){
		return false;
	}

	if(!isDbl_Html("zje" ,  "总金额", 0, rtnValue.moneyLimit, false)){//缴费金额应该在0~囤积值之间
		return false;
	}

	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
	}
	
	if(!isDbl("jt_total_zbdl" , "旧表基础电量")){
		return false;
	}
	if(rtnBD == null) {
		alert("未录入表底!");
		return;
	}

//	if(($("#jfje").val() == "0" || $("#jfje").val() == "")){
//		return(confirm("客户未缴费,继续操作吗?"))
//	}
	
	return true;
}

function printPayRec() {
	var cardtype = window.top.WebPrint.nodeIdx.dycard;
		if (rtnValue.cacl_type == SDDef.YFF_CACL_TYPE_DL) {//从库中查出的计费方式 是否为电量控
		cardtype = window.top.WebPrint.nodeIdx.dycarddl;
	}
		
	var filename =  window.top.WebPrint.getTemplateFileNm(cardtype);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}
