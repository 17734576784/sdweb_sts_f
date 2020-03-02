var rtnValue;
var rtnBDNew = null;
var rtnBDOld = null;
var mp_info = null;   //电表信息 
var WRITECARD_TYPE = window.top.SDDef.YFF_CARDMTYPE_KE001;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	initGrid();	
	dateFormat.date = dateFormat.getToday("yMd");
	var datestr = dateFormat.formatToYMD(0);
	dateFormat.date = dateFormat.getToday("H")+"00";
	datestr = datestr + " " + dateFormat.formatToHM(0);
	$("#ghsj").val(datestr);
	
	$("#btnSearch"	).click(function(){selcons()});
	$("#cardinfo"	).click(function(){window.top.card_comm.cardinfo()});
	$("#metinfo"	).click(function(){metinfo()});
	$("#btnRead").click(function(){readcard()});//读卡检索
		
	$("#btnBDOld").click(function(){Import("o")});
	$("#btnBDNew").click(function(){Import("n")});
	$("#btnChange").click(function(){if(check())wrtCard_chgmeter()});
	
	$("#btnPrt").click(function(){printRec()});
	
	setDisabled(true);
	$("#btnSearch").focus();
});

function getMPList(){//填充[更换电表]的下拉框
	var datas = {};
	datas.rtu_id = $("#rtu_id").val();
	datas.mpid0  = $("#mp_id").val();
	datas.mpid1  = "-1";
	datas.mpid2  = "-1";	
	var json_str = jsonString.json2String(datas);
	$.post( def.basePath + "ajaxdyjc/actChgMeter!getMpList.action",{result:json_str},function(data) {  //回传函数
		if (data.result != "") {
			//每次进来先要清空原有信息
			var json = eval('(' + data.result + ')');
			mp_info = json;	
		}
	});
} 

function selcons(){		//检索
	//20131119禁用按钮
	disableButton();
	var rtnValue1 = doSearch("ks",ComntUseropDef.YFF_DYOPER_CHANGEMETER,rtnValue);
	if(!rtnValue1){
		return;
	}
	
	//20131021添加，当查询结果中的预付费表类型 为新增表时将WRITECARD_TYPE赋为6
	var yffmeter_type = rtnValue1.yffmeter_type;
	WRITECARD_TYPE = getCardByMeter(yffmeter_type);
	
	setSearchJson2Html(rtnValue1);
}

function readcard(){//读卡检索。调用readCardSearchGy
	loading.loading();
	window.top.card_comm.readCardSearchDy(ComntUseropDef.YFF_DYOPER_CHANGEMETER);
}

function setSearchJson2Html(js_tmp){//读卡检索，返回赋值。调用readCardSearch后，readcard中异步执行完成后，执行此函数。
	//20131119禁用按钮
	disableButton();
	loading.loaded();
	if(!js_tmp)return;
	
	//20131021添加，当查询结果中的预付费表类型 为新增表时将WRITECARD_TYPE赋为6
	rtnValue = js_tmp;
	var yffmeter_type = rtnValue.yffmeter_type;
	WRITECARD_TYPE = getCardByMeter(yffmeter_type);

	setDisabled(false);

	$("#buyp_times").html(rtnValue.buy_times);
	$("#last_jfje").html(rtnValue.pay_money);
	$("#writecard_no").html(rtnValue.writecard_no);
	setConsValue(rtnValue);
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno};
	mis_query(param,gloQueryResult);
	
	$("#btnBDOld").focus();
	
	getRecord(rtnValue.rtu_id,rtnValue.mp_id);
	getMPList();
	
	$("#zbje_val").val("");
	$("#jfje_val").val("");
	$("#zje_val").html("");
}

function setDisabled(mode){
	if(!mode){
		$("buy_times").html("");
		$("#last_jfje").html("");
		$("#zbje_val").val("");
		$("#jfje_val").val("");
		$("#zje").html("");
		$("#writecard_no").html("");
		$("#td_bdinf").html("");
		$("#bd_old").html("");
		$("#bd_new").html("");
		var rtnBDNew = null;
		var rtnBDOld = null;
	}

	$("#btnBDOld").attr("disabled",mode);
	$("#zbje_val").attr("disabled",mode);
	$("#jfje_val").attr("disabled",mode);
	$("#ghsj").attr("disabled",mode);
	
}

function Import(bdflag){
	var tmp = importBD($("#rtu_id").val(),$("#mp_id").val());

	if (!tmp) {
		return;
	}
	else{	
		if (bdflag == "o") {
		    rtnBDOld = tmp;
		    $("#btnBDNew").attr("disabled",false);
		    $("#bd_old").html(rtnBDOld.td_bdinf);
		    $("#btnBDNew").focus();
		}
		if (bdflag == "n") {
			rtnBDNew = tmp;
			$("#bd_new").html(rtnBDNew.td_bdinf);
			$("#metinfo").attr("disabled",false);
			$("#btnChange").attr("disabled",false);
		}
	}
}

function wrtCard_chgmeter(){//换表写卡(写开户卡)
	var params = {};
	params.pay_money 		= $("#jfje_val").val()===""?0:$("#jfje_val").val();
	params.othjs_money 		= 0;
	params.zb_money 		= $("#zbje_val").val()===""?0:$("#zbje_val").val();
	params.all_money   		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money),2);
	
	loading.loading();
	
	modalDialog.height 	= 300;
	modalDialog.width 	= 280;
	modalDialog.url 	= def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param 	= {
		showGSM : true,
		key	: ["客户编号","客户名称", "缴费金额","追补金额","总金额"],
		val	: [$("#userno").html(),$("#username").html(), params.pay_money,params.zb_money,params.all_money]		
	};
	
	var o = modalDialog.show();
	
	if(!o||!o.flag){
		loading.loaded();
		return;
	}
	
	window.setTimeout("wrtCard_chgmeter1(" + o.gsm + ")", 10);
}
	
function wrtCard_chgmeter1(gsm){
	if(!CheckCard()) {
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
	params.myffalarmid 		= $("#yffalarm_id").val();
	params.paytype 			= $("#pay_type").val();
	
	params.alarm_val1		= rtnValue.yffalarm_alarm1;
	params.alarm_val2		= rtnValue.yffalarm_alarm2;
	
	params.pay_money 		= $("#jfje_val").val()===""?0:$("#jfje_val").val();
	params.othjs_money 		= 0;
	params.zb_money 		= $("#zbje_val").val()===""?0:$("#zbje_val").val();
	params.all_money   		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money),2);
	
	params.gsmFlag = gsm;
	 
	params.pt 			= rtnValue.pt;
	params.ct 			= rtnValue.ct;
	params.meter_type 	= WRITECARD_TYPE;
	params.card_type  	= SDDef.CARD_OPTYPE_OPEN;//开户
	params.limit_dl 	= rtnValue.moneyLimit;
	params.feeproj_id 	= $("#feeproj_id").val();
	params.writecard_no	= rtnValue.writecard_no;
	params.meterno  	= rtnValue.esamno;
	params.cardno 		= rtnValue.userno;
	params.buynum		= 1;
	
	params.operType  = ComntUseropDef.YFF_DYOPER_CHANGEMETER;
	params.jt_cycle_md = rtnValue.jt_cycle_md;				//低压阶梯年结算日
	
	$.post( def.basePath + "ajaxoper/actWebCard!getcardWrtInfo.action", 		//后台处理程序
		{
			result	: jsonString.json2String(params)
		},
		function(data) {			    	//回传函数
			loading.loaded();
			var retStr 	 = data.result;
			var ret_json = window.top.card_comm.writecard(retStr);
			if(ret_json.errno  == 0){
				wrtDB_chgmeter(gsm);
			}else{
				alert("写卡失败！\n" + ret_json.errsr);
			}
		}
	);
}

function wrtDB_chgmeter(gsmflag){		//换表-存库。
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	
	var params = {};
	params.rtu_id      = rtu_id;
	params.mp_id       = mp_id;
	params.paytype     =  rtnValue.pay_type;
	params.buynum      =  1;
	
	var ghdb	= mp_id;
	var mp      = findMpInfo(ghdb);
	if(mp == null) return;
	
	params.oldpt_n = mp.ptfz; 
	params.oldpt_d = mp.ptfm;
	params.oldpt_r = mp.pt;
 
	params.newpt_n = mp.ptfz;
	params.newpt_d = mp.ptfm;
	params.newpt_r = mp.pt;
	
	params.oldct_n = mp.ctfz; 
	params.oldct_d = mp.ctfm;
	params.oldct_r = mp.ct;

	params.newct_n = mp.ctfz;
	params.newct_d = mp.ctfm;
	params.newct_r = mp.ct;
	
	params.pay_money 		= $("#jfje_val").val()===""?0:$("#jfje_val").val();
	params.othjs_money 		= 0;
	params.zb_money 		= $("#zbje_val").val()===""?0:$("#zbje_val").val();
	params.all_money   		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money),2);
	
	params.chg_mpid = mp_id;
	var ghsj = $("#ghsj").val();
	
	params.chg_date 	= ghsj.substring(0,4) + ghsj.substring(5,7) + ghsj.substring(8,10);
	params.chg_time 	= ghsj.substring(12,14) + ghsj.substring(15,17) +"00";
	params.chg_type 	= 0;//$("#ghlx").val();	
	
	var rtnBD ;
	
	params.o_date = rtnBDOld.date;
	params.o_time = 0;
	params.n_date = rtnBDNew.date;
	params.n_time = 0;
	
	for(var i = 0; i < 3; i++){
		rtnBD = rtnBDOld;
		eval("params.mp_id"    + i + "=rtnBD.mp_id" + i);
		eval("params.bd_zy_o"  + i + "=rtnBD.zbd" + i);
		eval("params.bd_zyj_o" + i + "=rtnBD.jbd" + i);
		eval("params.bd_zyf_o" + i + "=rtnBD.fbd" + i);
		eval("params.bd_zyp_o" + i + "=rtnBD.pbd" + i);
		eval("params.bd_zyg_o" + i + "=rtnBD.gbd" + i);
			
		//确定选择的第几块电表
		if(eval("ghdb==rtnBD.mp_id" + i)){
			eval("params.bd_zy_o  = rtnBD.zbd" + i);
			eval("params.bd_zyj_o = rtnBD.jbd" + i);
			eval("params.bd_zyf_o = rtnBD.fbd" + i);
			eval("params.bd_zyp_o = rtnBD.pbd" + i);
			eval("params.bd_zyg_o = rtnBD.gbd" + i);
		}
		
		rtnBD = rtnBDNew;
		eval("params.bd_zy_n"  + i + "=rtnBD.zbd" + i);
		eval("params.bd_zyj_n" + i + "=rtnBD.jbd" + i);
		eval("params.bd_zyf_n" + i + "=rtnBD.fbd" + i);
		eval("params.bd_zyp_n" + i + "=rtnBD.pbd" + i);
		eval("params.bd_zyg_n" + i + "=rtnBD.gbd" + i);
        //确定选择的第几块电表
		if(eval("ghdb==rtnBD.mp_id" + i)){
			eval("params.bd_zy_n  = rtnBD.zbd" + i);
			eval("params.bd_zyj_n = rtnBD.jbd" + i);
			eval("params.bd_zyf_n = rtnBD.fbd" + i);
			eval("params.bd_zyp_n = rtnBD.pbd" + i);
			eval("params.bd_zyg_n = rtnBD.gbd" + i);
		}
	}
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			userData1         : rtu_id,
			mpid              : mp_id,
			gsmflag           : 0,
			dyOperChangeMeter : json_str,
			userData2         : ComntUseropDef.YFF_DYOPER_CHANGEMETER
		},
		function(data) {//回传函数
			loading.loaded();
			if (data.result == "success") {
				//向MIS系统缴费
				if(parseFloat(params.pay_money) > 0 && gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
					var ret_json = eval("(" + data.dyOperChangeMeter + ")");
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
				
				alert("换表成功!");
				setDisabled(true);
				$("#btnChange").attr("disabled",true);
				$("#btnBDNew").attr("disabled",true);
				$("#btnPrt").attr("disabled",false);
				getRecord($("#rtu_id").val(), $("#mp_id").val());//表格初始化
				window.top.WebPrint.setYffDataOperIdx2params(data.dyOperChangeMeter,window.top.WebPrint.nodeIdx.dycard);//打印用的参数
			}
			else {
				alert("换表失败!" + (data.operErr ? data.operErr : ''));
			}	
		}
	);
}

function metinfo(){//表内信息
	modalDialog.width = 800;
	modalDialog.height = 600;
	modalDialog.url = def.basePath + "jsp/dyjc/dialog/meterInfo.jsp";
	modalDialog.param = {"rtuId":$("#rtu_id").val() ,"mpId":$("#mp_id").val()};
	modalDialog.show();
}

function calWrtCard(){//写卡金额
	
	var zbje_val = 0.0, jfje_val = 0.0, total_val = 0.0;
	zbje_val = $.trim($("#zbje_val").val())==="" ? 0 : $.trim($("#zbje_val").val());
	jfje_val = $.trim($("#jfje_val").val())==="" ? 0 : $.trim($("#jfje_val").val());
	
	total_val  = round(parseFloat(zbje_val) + parseFloat(jfje_val),3);
	GDZ = LJGDZ = total_val;
	
	if(!isNaN(total_val)){
		$("#zje_val").html(total_val);
	}
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
	var meterNo,consNo,buyTime;			//表号，户号, 写卡户号
	if(meterType == window.top.SDDef.YFF_METER_TYPE_ZNK || meterType == window.top.SDDef.YFF_METER_TYPE_ZNK2){
	 	meterNo 	= json_out.meterno; 	//表号
		consNo  	= json_out.consno;		//户号
	 	buyTime		= json_out.pay_num;		//购电次数
	 	
	 	if((WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE001) && (meterType != window.top.SDDef.YFF_METER_TYPE_ZNK)){
			alert("用户电表为2009版规约，请使用09版购电卡!");
			return false;
		}
		
		if((WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE006) && (meterType != window.top.SDDef.YFF_METER_TYPE_ZNK2)){
			alert("用户电表为2013版规约，请使用13版购电卡!");
			return false;
		}
	 	
	}else{
		alert("卡内信息中卡表类型不是智能卡。")
		return false;
	}
	
	var tmp = "000000000000"
	if ((tmp != meterNo) || (tmp != consNo))		{
		alert("换表前请先清空卡，不能缴费！")
		return false;
	}

	return true;
}

function check() {
	if(rtnBDOld == null) {
		alert("请录入旧表底...");
		return false;
	}
	
	if(rtnBDNew == null){
		alert("请录入新表底...");
		return false;
	}
	
	if($("#ghsj").val() == ""){
		alert("请填写更换时间...");
		return false;
	}
	if (!isZfDbl("zbje_val", "剩余金额", false)) {
		return false;
	}
	if(!isDbl("jfje_val","缴费金额", 0, 1000000, false)) {
		return false;
	}
	
	if(!confirm("确认要换表/换倍率操作吗？")){
		return false;
	}
	
	return true;
}

function printRec(){//打印
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.dycard);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}

//返回电表信息
function findMpInfo(mp_id) {
	if(mp_info == null) {
		alert("无电表信息");
		return null;
	}
	for(var i = 0; i < mp_info.rows.length; i++) {
		if(mp_id == mp_info.rows[i].value) {
			return mp_info.rows[i];
		}
	}
	alert("无电表信息");
	return null;
}

//20131119,检索或读卡时，禁用换表键和录入表底键
function disableButton(){
	$("#btnChange").attr("disabled",true);
	$("#btnBDOld").attr("disabled",true);
	$("#btnBDNew").attr("disabled",true);
}
