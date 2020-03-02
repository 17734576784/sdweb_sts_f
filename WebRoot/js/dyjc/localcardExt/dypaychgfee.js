var rtnValue;
var jsonFLFA;
var WRITECARD_TYPE = window.top.SDDef.YFF_CARDMTYPE_KE001;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
    $.post(def.basePath + "ajaxdyjc/actChgRate!getRateList.action",{},function(data){
		if(data.result != ""){
			jsonFLFA = eval('(' + data.result + ')');
			for(var i=0;i<jsonFLFA.rows.length;i++){
					$("#xflfa").append("<option value="+jsonFLFA.rows[i].value+">"+jsonFLFA.rows[i].text+"</option>");
			} 
			changeFLFA();
		}
	});
    
	initGrid();
	$("#btnSearch").click(function(){selcons()});//检索
	$("#btnRead").click(function(){read_card();});
	$("#cardinfo").click(function(){window.top.card_comm.extcardinfo()});
	$("#metinfo").click(function(){metinfo()});
	$("#chgMbphone").click(function(){changeMbphone()}); // 更换手机
	$("#xflfa").change(function(){changeFLFA()});//新费率方案 下拉框	
	$("#write").click(function(){if(check())wrtCard_pay();});
	$("#prt").click(function(){printPayRec();}).attr("disabled",true);
 	$("#zbje").keyup(function(){calcTotal();});
	$("#jsje").keyup(function(){calcTotal();});
	$("#jfje").keyup(function(){calcTotal();});
	setDisabled(true);
	$("#btnSearch").focus();
	getChfDate();
});

function changeFLFA(){//费率方案 下拉框 onchange
	var val=$("#xflfa").val();
	
	var text=$("#xflfa").find("option:selected").text();
	for (var i = 0; i < jsonFLFA.rows.length; i++) {
		if (jsonFLFA.rows[i].value == val) {
			$("#feeproj_detail_new").html(jsonFLFA.rows[i].desc);	
			return;
		}		
	}
	
	
}
//20131118新增初始化费率描述
function initFLFA(){
	var val=$("#feeproj_id").val();
    $("#xflfa").val(val);
    for (var i = 0; i < jsonFLFA.rows.length; i++) {
		if (jsonFLFA.rows[i].value == val) {
			$("#feeproj_detail_new").html(jsonFLFA.rows[i].desc);	
			return;
		}		
	}	
}

function selcons(){//检索
	var rtnValue1 = doSearch("kzext",ComntUseropDef.YFF_DYOPER_PAY, rtnValue);
	if(!rtnValue1){
		return;
	}
	setDisabled(false);
	rtnValue = rtnValue1;
	setcons1();
	isJTfee(rtnValue);
}

function read_card(){		//读卡
	setDisabled(false);
	loading.tip = "正在读卡,请稍后...";
	loading.loading();
	setTimeout("window.top.card_comm.readCardSearchDy(" + ComntUseropDef.YFF_DYOPER_PAY + ")", 10);	 
	
}

function setSearchJson2Html(js_tmp) {
	loading.loaded();
	if(!js_tmp)return;
	if(!window.top.card_comm.checkInfo(js_tmp)) return;
	rtnValue = js_tmp;
	setcons1();
}

function setcons1() {
	//20131021添加，当查询结果中的预付费表类型 为新增表时将WRITECARD_TYPE赋为6
	var yffmeter_type = rtnValue.yffmeter_type;
	if(is2013Meter(yffmeter_type)){
		WRITECARD_TYPE = window.top.SDDef.YFF_CARDMTYPE_KE006;
	}
	else{
		alert('此用户表规约类型为2009版,不支持此项操作!');
		document.location.reload();
		return;
	};
	
	setConsValue(rtnValue);
	isJTfee(rtnValue);
	$("#buy_times").html(rtnValue.buy_times);
	$("#dqye").html(rtnValue.now_remain);
	$("#writecard_no").html(rtnValue.writecard_no)
	getRecord(rtnValue.rtu_id,rtnValue.mp_id);
	
	$("#pay").attr("disabled",false);	
	initFLFA();
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno,provinceMisFlag : provinceMisFlag};
	mis_query(param,gloQueryResult);
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
}

function wrtCard_pay(){

	var params = {};
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money   		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money), def.point);
	
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
		loading.loaded();
		return;
	}
	
	window.setTimeout("wrtCard_pay1(" + o.gsm + ")", 10);
}


//先缴费还是先保存先写卡
function wrtCard_pay1(gsm) {
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert(sel_user_info);
		return;
	}	
	var params = {};
	params.rtu_id 			= rtu_id;
	params.mp_id 			= mp_id;
	params.myffalarmid 		= $("#yffalarm_id").val();
	params.paytype 			= $("#pay_type").val();
	
	params.alarm_val1		= rtnValue.yffalarm_alarm1;
	params.alarm_val2		= rtnValue.yffalarm_alarm2;
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money   		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money), def.point);
	
	params.gsmFlag 		= gsm;
	params.pt 			= rtnValue.pt;
	params.ct 			= rtnValue.ct;
	params.meter_type 	= WRITECARD_TYPE;
	params.card_type  	= SDDef.CARD_OPTYPE_BUY;	//缴费
	params.limit_dl 	= rtnValue.moneyLimit;
	params.feeproj_id 	= $("#xflfa").val();
	params.writecard_no	= rtnValue.writecard_no;
	params.meterno  	= rtnValue.esamno;
	params.cardno 		= rtnValue.userno;
	params.buynum		= parseInt(rtnValue.buy_times) + 1;
	
	var qhsj = $("#xqhsj").val();
	if(qhsj!=""){
		params.chg_date   = qhsj.substring(2,4)   + qhsj.substring(5,7)   + qhsj.substring(8,10);
	    params.chg_time   = qhsj.substring(12,14) + qhsj.substring(15,17) +"00"
	}

	var jt_qhsj = $("#jt_xqhsj").val();
	if(jt_qhsj!=""){			
		params.jt_chgymd   = jt_qhsj.substring(2,4)   + jt_qhsj.substring(5,7)   + jt_qhsj.substring(8,10);
		params.jt_cghm     = jt_qhsj.substring(12,14) + jt_qhsj.substring(15,17);
	}

	params.operType  = ComntUseropDef.YFF_DYOPER_CHANGERATE;	//操作类型决定更新标识
	params.jt_cycle_md = rtnValue.jt_cycle_md;				//低压阶梯年结算日
	
	$.post( def.basePath + "ajaxoper/actWebCard!getcardWrtInfo.action", 		//后台处理程序
		{
			result	: jsonString.json2String(params)
		},
		function(data) {			    	//回传函数
			loading.loaded();
			var retStr 		 = data.result;
			var ret_json = window.top.card_comm.writecard(retStr);
			if(ret_json.errno  == 0){
				cookie.set("new_fsChgTime", qhsj);
				cookie.set("new_jtChgTime", jt_qhsj);
				wrtDB_pay(params);
			}else{
				alert("写卡失败！\n" + ret_json.errsr);
			}
		}
	);
	
}

function changeFL(){//保存新费率
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	var params = {};
	params.rtu_id = rtu_id;
	params.mp_id0 = mp_id;
	params.mp_id1 = rtnValue.power_rela1
	params.mp_id2 = rtnValue.power_rela2
	params.chgid 	= $("#xflfa").val();
	
	var qhsj 		= $("#xqhsj").val();
	params.chg_date = qhsj.substring(0,4) + qhsj.substring(5,7) + qhsj.substring(8,10);
	params.chg_time = qhsj.substring(12,14) + qhsj.substring(15,17)


	params.date		= params.chg_date;
	params.time 	= params.chg_time;
	
	params.bd_zy0   = 0;
	params.bd_zyj0  = 0;
	params.bd_zyf0  = 0;
	params.bd_zyp0  = 0;
	params.bd_zyg0  = 0;
	
	params.bd_zy1   = 0;
	params.bd_zyj1  = 0;
	params.bd_zyf1  = 0;
	params.bd_zyp1  = 0;
	params.bd_zyg1  = 0;
	
	params.bd_zy2   = 0;
	params.bd_zyj2  = 0;
	params.bd_zyf2  = 0;
	params.bd_zyp2  = 0;
	params.bd_zyg2  = 0;
	

	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			userData1		 : rtu_id,
			mpid 			 : mp_id,
			gsmflag 		 : 0,
			dyOperChangeRate : json_str,
			userData2 		 : ComntUseropDef.YFF_DYOPER_CHANGERATE
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				 alert("保存新费率成功!");				
			}
			else {
				alert("保存新费率失败!" + (data.operErr ? data.operErr : ''));
			}
		}
	);
	}


function wrtDB_pay(params){		//缴费
	params.buynum = rtnValue.buy_times;	//卡与通讯的购电次数不一致
	
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
			    changeFL();									
				getRecord($("#rtu_id").val(), $("#mp_id").val());
				setDisabled(true);
				window.top.WebPrint.setYffDataOperIdx2params(data.dyOperPay,window.top.WebPrint.nodeIdx.dycard);//打印用的参数
			}
			else {
				alert("向主站发送缴费命令失败,请补写缴费记录!" + (data.operErr ? data.operErr : ''));
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

function CheckCard() {
	var json_out = {};
	json_out = window.top.card_comm.readcard();
	if(json_out == undefined || json_out ==""){
		alert("读卡错误!");
		return  false;				
	}

	var meterType = json_out.meter_type;//卡表类型
	var meterNo,consNo,buyTime;			//表号，户号,购电次数
	if(meterType == window.top.SDDef.YFF_METER_TYPE_ZNK || meterType == window.top.SDDef.YFF_METER_TYPE_ZNK2){
		
		if(WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE001 && meterType != window.top.SDDef.YFF_METER_TYPE_ZNK ){
			alert("用户电表为2009版规约，请使用09版购电卡!");
			return false;
		}
		
		if( WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE006 && meterType != window.top.SDDef.YFF_METER_TYPE_ZNK2 ){
			alert("用户电表为2013版规约，请使用13版购电卡!");
			return false;
		}
		
		meterNo 	= json_out.meterno; 		//表号
		consNo  	= json_out.consno;			//户号
	 	buyTime		= json_out.back_pay_num;	//返写购电次数

		var tmp = "000000000000"
		if (tmp == meterNo)	{
			alert("用户表号为空，不能缴费！")
			return false;
		}
		else if (tmp == consNo)	{
			alert("用户户号为空，不能缴费！")
			return false;
		}
		else if (parseInt(buyTime) <=0) {
			alert("卡内无反写信息，不能购电！")
			return false;
		}
//		else if (rtnValue.userno.replace(/(^0*)/g, "") != consNo.replace(/(^0*)/g, "")) {
		else if (rtnValue.writecard_no.replace(/(^0*)/g, "") != consNo.replace(/(^0*)/g, "")) {
			alert("用户卡户号与系统查询结果不一致！")
			return false;
		}
		else if (rtnValue.esamno.replace(/(^0*)/g, "") != meterNo.replace(/(^0*)/g, "")) {
			alert("用户卡表号与系统查询结果不一致， 请到换表流程！")
			return false;
		}
		else if (rtnValue.buy_times != buyTime ) {
			alert("用户卡购电次数与售电系统不一致,不能购电！")
			return false;
		}
	} else {
		alert("未知卡类型")
		return false;
	}
	return true;
}

function check() {
	//20131211 新增 提示用户新切换时间不能为空
	if ($("#feeproj_id").val() == $("#xflfa").val())
	{
		alert("新旧费率相同,不许缴费！");
		return;
		
	}
	var qhsj = $("#xqhsj").val();
	if(qhsj==""){		
		alert("请输入新分时费率切换时间");
		return;
		
	}
	var jt_qhsj = $("#jt_xqhsj").val();
	if(jt_qhsj==""){
	    alert("请输入新阶费率梯切换时间");
		return;
		
	}

	//20131211新增判断卡是否满足缴费条件，当不满足条件时，不让改费率
	if(!CheckCard()){		
		return;
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
	
	if(!isDbl("jfje" ,"缴费金额", 0,  rtnValue.moneyLimit)){
		return false;
	}
	if(!isDbl_Html("zongje" ,  "总金额"  , 0, rtnValue.moneyLimit, false)){//总金额应该在0~囤积值之间
		return false;
	}
	
	if(parseFloat($("#zongje").html()) == 0){
		if(!confirm("用户总金额为0,要继续缴费吗?"))return false;
	}

	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
	}
	
//	if($("#zongje").html() > rtnValue.moneyLimit||$("#zongje").html() < 0){
//		alert("总金额应在0与" + rtnValue.moneyLimit + "之间");
//		return false;
//	}
	
	return true;
}

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
					alert("向主站发送更改手机命令失败" + (data.operErr ? data.operErr : ''));
				}
			}
	);
}

function setDisabled(mode){
	if(!mode){
		//清空界面的信息,用于开户后再次查询,而无查询结果的情况
		setConsValue(rtnValue,true);
		mygrid.clearAll();
		$("#jsje").val("");
		$("#jfje").val("");
		$("#zbje").val("");
		$("#dqye").html("");
		$("#buy_times").html("");
		$("#zongje").html("");
		$("#writecard_no").html("");
		//隐含域也要清空
		$("#now_remain").html("");
		$("#now_remain2").html("");
		$("#jt_total_dl").html("");
		//计算客户名称  结算客户分类
		$("#jsyhm").html("");
		$("#yhfl").html("");
	}
	$("#dqye").attr("disabled",mode);
	$("#pay").attr("disabled",mode);
	$("#jfje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#zbje").attr("disabled",mode);
	$("#chgMbphone").attr("disabled",mode);
	$("#write").attr("disabled",mode);
	$("#xqhsj").attr("disabled",mode);
	$("#jt_xqhsj").attr("disabled",mode);
	$("#xflfa").attr("disabled",mode);
	
}

function printPayRec(){//打印
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.dycard);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
//	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.dyje;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}

//如果是 阶梯费率 或者 阶梯混合费率 则显示阶梯信息
function isJTfee(rtnValue){
	 
	//var feeproj_id  = rtnValue.feeproj_id;
	var feeType		= rtnValue.feeType;
	var now_remain  = (rtnValue.now_remain  =="" || rtnValue.now_remain  == undefined) ? 0 : rtnValue.now_remain;
	var now_remain2 = (rtnValue.now_remain2 =="" || rtnValue.now_remain2 == undefined) ? 0 : rtnValue.now_remain2;
	if(feeType == "" || feeType == null ){
		return;
	}
//	alert(now_remain + "  " + now_remain2 + "  " + feeType + "   "  +SDDef.YFF_FEETYPE_JTFL);
	
	if(feeType == SDDef.YFF_FEETYPE_JTFL || feeType == SDDef.YFF_FEETYPE_MIXJT){
		$("#jt_info").attr("style","display:blank");
		$("#jt_info1").attr("style","display:blank");
		$("#jt_info2").attr("style","display:blank");
		
		var jsje = round((parseFloat(now_remain)-parseFloat(now_remain2)), def.point);
		$("#jsje").val(jsje >= 0 ? 0 : jsje);
		$("#now_remain").html(now_remain);
		$("#now_remain2").html(now_remain2);
		$("#jt_total_dl").html(rtnValue.jt_total_dl);
		calcTotal();
	}
}

//初始化更改日期
function getChfDate(){
	initChgDate("xqhsj","new_fsChgTime");
	initChgDate("jt_xqhsj","new_jtChgTime")
}

function initChgDate(id,cookieName){
	var new_chgTime = cookie.get(cookieName);//2013年01月01日 11时10分
	if(new_chgTime == undefined || new_chgTime == ""){
		var datestr = getFormatDate();
		$('#'+ id).val(datestr);
	}else{
		$('#'+ id).val(new_chgTime);
	}
}

function getFormatDate(){
	dateFormat.date = dateFormat.getToday("yMd");
	var datestr 	= dateFormat.formatToYMD(0);
	dateFormat.date = dateFormat.getToday("H")+"00";
	datestr 		= datestr + " " + dateFormat.formatToHM(0);
	return datestr;
}



