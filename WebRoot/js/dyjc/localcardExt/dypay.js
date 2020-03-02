var rtnValue;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	$.post(def.basePath + "ajaxdyjc/actChgRate!getRateList.action",{},function(data){
	 if(data.result != ""){
		jsonFLFA = eval('(' + data.result + ')');
		for(var i=0;i<jsonFLFA.rows.length;i++){
			$("#xflfa").append("<option value="+jsonFLFA.rows[i].value+">"+jsonFLFA.rows[i].text+"</option>");
		} 
	}
	});
	initGrid();
	$("#btnSearch").click(function(){selcons()});//检索
	$("#btnRead").click(function(){read_card()});//读卡
	
	/**
	 * 20140729新增 写卡户号一样，户号不一样
	 */
	$("#btnReadSearch").click(function(){readCard_search()});//读卡检索
	
	$("#cardinfo").click(function(){window.top.card_comm.extcardinfo()});
//	$("#metinfo").click(function(){metinfo()});
	$("#chgMbphone").click(function(){changeMbphone()}); // 更换手机
	$("#pay").click(function(){if(check())wrtCard_pay();});
	$("#prt").click(function(){printPayRec()}).attr("disabled",true);
 	$("#zbje").keyup(function(){calcTotal();});
	$("#jsje").keyup(function(){calcTotal();});
	$("#jfje").keyup(function(){calcTotal();});
	setDisabled(true);
	$("#btnSearch").focus();
	
});


function selcons(){//检索
	
	var rtnValue1 = doSearch("kzext",ComntUseropDef.YFF_DYOPER_PAY, rtnValue);
	if(!rtnValue1){
		return;
	}
	setDisabled(false);
	rtnValue = rtnValue1;

	setcons1();
}

function read_card(){		//读卡
	setDisabled(false);
	loading.tip = "正在读卡,请稍后...";
	loading.loading();
	setTimeout("window.top.card_comm.readExtCardSearchDy(" + ComntUseropDef.YFF_DYOPER_PAY + ")", 10);
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
	window.top.card_comm.readExtCardSameWrtNo(ComntUseropDef.YFF_DYOPER_PAY);
}


//读卡检索完毕后
function setSearchJson2Html(js_tmp) {
	loading.loaded();
	if(!js_tmp)return;
	
	//将卡内信息与查询出来的档案信息进行比较，如有错误，进行相应提示处理
	if(!window.top.card_comm.checkExtInfo(js_tmp)) return;
	rtnValue = js_tmp;
	
	setcons1();
}

//向界面内填充信息
function setcons1() {
	//重新检索，禁用打印按钮
	$("#prt").attr("disabled",true);
	
//20131021添加，当查询结果中的预付费表类型 为新增表时将WRITECARD_TYPE赋为6
//	var yffmeter_type = rtnValue.yffmeter_type;
//	WRITECARD_TYPE = getCardByMeter(yffmeter_type);
	
	if (rtnValue.cacl_type == SDDef.YFF_CACL_TYPE_DL) {//从库中查出的计费方式 是否为电量控
		if ((rtnValue.feeType != SDDef.YFF_FEETYPE_DFL)&& (rtnValue.feeType != SDDef.YFF_FEETYPE_MIX)&&(rtnValue.feeType != SDDef.YFF_FEETYPE_JTFL)) {
			alert("电量方式请选择单费率、混合、阶梯费率方案！");
			return;
		}
	}
	
	//填充界面基本信息内容
	setConsValue(rtnValue);
	isJTfee(rtnValue);
	
	$("#buy_times").html(rtnValue.buy_times);
	$("#dqye").html(rtnValue.now_remain);
	$("#writecard_no").html(rtnValue.writecard_no)
	
	$("#buy_dl").html(0);
	$("#pay_bmc").html(0);
	
	getRecord(rtnValue.rtu_id,rtnValue.mp_id);
	
	$("#write").attr("disabled",false);
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno,provinceMisFlag : provinceMisFlag};
	mis_query(param,gloQueryResult);
}

function calcTotal(){//总金额
	
	var zbje = $("#zbje").val()==="" ? 0 : $("#zbje").val();
	var jsje = $("#jsje").val()==="" ? 0 : $("#jsje").val();
	var jfje = $("#jfje").val()==="" ? 0 : $("#jfje").val();
	
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
		$("#pay_xxdl").html("");
		
		if (rtnValue.feeType == SDDef.YFF_FEETYPE_JTFL) {
			for(var i = 0;i < retcalc.dtdl.length;i++){
				if(retcalc.dtdl[i] != 0){
					$("#pay_xxdl").append((i+1)+"档:"+retcalc.dtdl[i]+"; ");
				}
		//			html("一档："+retcalc.dtdl[0]+"；二档："+retcalc.dtdl[1]+"；三档："+retcalc.dtdl[2]);
			}
		}
	}	
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

function wrtCard_pay1(gsm) {

	if(!CheckCard()){
		loading.loaded();
		return;
	}

	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert(sel_user_info);
		return;
	}
//	UpdatejszbRecrd(rtu_id, mp_id);
//	loading.loaded();
//	return;
	
	var params = {};
	params.rtu_id 			= rtu_id;
	params.mp_id 			= mp_id;
	params.myffalarmid 		= $("#yffalarm_id").val();
	params.paytype 			= $("#pay_type").val();			//缴费方式
	
	params.alarm_val1		= rtnValue.yffalarm_alarm1;
	params.alarm_val2		= rtnValue.yffalarm_alarm2;
	
	//各项缴费金额，写卡时目前只用到了all_money
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money   		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money), def.point);
	
	params.gsmFlag 		= gsm;
	params.pt 			= rtnValue.pt;
	params.ct 			= rtnValue.ct;
//	params.bl           = parseInt(rtnValue.pt) * parseInt(rtnValue.ct);
	//params.meter_type 	= WRITECARD_TYPE;
	params.card_type  	= SDDef.CARD_OPTYPE_BUY;	//缴费
	params.limit_dl 	= rtnValue.moneyLimit;
	params.feeproj_id 	= $("#feeproj_id").val();
	params.writecard_no	= rtnValue.writecard_no;
	params.meterno  	= rtnValue.esamno;
	params.cardno 		= rtnValue.userno;			
	params.buynum		= parseInt(rtnValue.buy_times) + 1;
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

	$.post( def.basePath + "ajaxoper/actWebCard!getWriteCardExt.action", 		//后台处理程序
		{
			result	: jsonString.json2String(params)
		},
		function(data) {			    	//回传函数
			loading.loaded();
			var retStr 		 = data.result;
			var ret_json = window.top.card_comm.writeExtcard(retStr);

			if(ret_json.errno  == 0){
				if ((params.cardtype == "JJ_003")||(params.cardtype == "JJ_004")){
					params.update_flag = 0x02;
					params.card_pass = ret_json.strinfo;			//传到后台 数据库中存储  作为密码
				}
				else {
					params.update_flag = 0x00;
				}
				wrtDB_pay(params);
			}else{
				alert("写卡失败！\n" + ret_json.errsr);
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
				UpdatejszbRecrd(params.rtu_id, params.mp_id);
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
				getRecord($("#rtu_id").val(), $("#mp_id").val());
				setDisabled(true);
				window.top.WebPrint.setYffDataOperIdx2params(data.dyOperPay,window.top.WebPrint.nodeIdx.dycarddl);//打印用的参数
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

//读卡检索时判断
function CheckCard() {
	var json_out = {};
	json_out = window.top.card_comm.readExtcard();
	if(json_out == undefined || json_out ==""){
		alert("读卡错误!");
		return  false;				
	}
	
	var meterNo,consNo,buyTime, back_buyTime;				//表号，户号,购电次数
	//var cacl_type, feeType,  cardtype;//算费类型(电量、金额、表底),费率方案(单、复、阶梯),表类型(KL_001..JJ_001..)
	
	meterNo 	= json_out.meterno; 		//表号
	consNo  	= json_out.consno;			//户号
 	back_buyTime		= json_out.back_pay_num;	//返写购电次数
 	buyTime		= json_out.pay_num;	//写卡购电次数
 	

	//var tmp = "000000000000"
 	
	if (consNo.replace(/(^0*)/g, "") == '')	{
		alert("用户户号为空，不能缴费！")
		return false;
	}
	else if (Number(json_out.back_flag) <=0) {
		alert("卡内无反写信息，不能购电！")
		return false;
	}
	else if (rtnValue.writecard_no.replace(/(^0*)/g, "") != consNo.replace(/(^0*)/g, "")) {
		alert("用户卡户号与系统查询结果不一致！")
		return false;
	}
	else if (Number(rtnValue.buy_times) != Number(buyTime)) {
		alert("用户卡购电次数与售电系统不一致,不能购电！")
		return false;
	}
	//非人民卡表要进行返写购电次数进行判断
	else if (Number(rtnValue.buy_times) != Number(back_buyTime) && json_out.meter_type != "RM_001") {
		alert("用户卡返写购电次数与售电系统不一致,不能购电！")
		return false;
	}
	else if ((meterNo != "") && (rtnValue.esamno.replace(/(^0*)/g, "") != meterNo.replace(/(^0*)/g, ""))) {
		alert("用户卡表号与系统查询结果不一致， 请到换表流程！")
		return false;
	}
	return true;
}

function check() {
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
	}else if(parseFloat(jfje) == 0){//20131213 dubr 将if改成else if 当总金额为0时，不提示未交费
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
	window.top.WebPrint.prt_params.reprint 	 = 0;

	window.top.WebPrint.doPrintDy();
}

//如果是 阶梯费率 或者 阶梯混合费率 则显示阶梯信息
function isJTfee(rtnValue){
	var feeType		= rtnValue.feeType;
	var now_remain  = (rtnValue.now_remain  =="" || rtnValue.now_remain  == undefined) ? 0 : rtnValue.now_remain;
	var now_remain2 = (rtnValue.now_remain2 =="" || rtnValue.now_remain2 == undefined) ? 0 : rtnValue.now_remain2;
	if(feeType == "" || feeType == null ){
		return;
	}

	if(feeType == SDDef.YFF_FEETYPE_JTFL || feeType == SDDef.YFF_FEETYPE_MIXJT){
		$("#jt_info").attr("style","display:blank");
		$("#jt_info1").attr("style","display:blank");
		$("#jt_info2").attr("style","display:blank");
//		var jsje = round((parseFloat(now_remain)-parseFloat(now_remain2)), def.point);
//		$("#jsje").val(jsje >= 0 ? 0 : jsje);
//		$("#now_remain").html(now_remain);
//		$("#now_remain2").html(now_remain2);
		$("#jt_total_dl").html(round(rtnValue.jt_total_dl, 2));
//		calcTotal();
	}

	getjszbRecrd($("#rtu_id").val() ,$("#mp_id").val());
}

//量控阶梯处理追补 结算金额
function getjszbRecrd(rtu_id, mp_id)
{	
	if (!dylkcard_jtzbjs_flag) return;
	if (rtnValue.cacl_type != SDDef.YFF_CACL_TYPE_DL) return;

	$.post(def.basePath + "ajaxdyjc/actSG186DlJs!ReadJsRecord.action", 
		{result : "{rtu_id : " + rtu_id + ", mp_id : " + mp_id + "}"},
	function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');

			$("#zbje").val(json.zb_money);
			$("#jsje").val(json.othjs_money);
		} else {

		}
	});
}

function UpdatejszbRecrd(rtu_id, mp_id)
{	
	if (!dylkcard_jtzbjs_flag) return;
	if (rtnValue.cacl_type != SDDef.YFF_CACL_TYPE_DL) return;

	$.post(def.basePath + "ajaxdyjc/actSG186DlJs!UpdateJsRecord.action", 
		{result : "{rtu_id : " + rtu_id + ", mp_id : " + mp_id + "}"},
	function(data){
		if(data.result == "success"){
		}else {
			alert("更新阶梯结算追补金额记录失败!");
		}
	});
}