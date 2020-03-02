var rtnValue 	    = null;
var lastpayRcd 	    = null;//上次购电记录

var history_Records = null;
var mygridtable     = null;
var old_op_date     = null;
var old_op_time     = null;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	initGrid_repair();
	$("#btnSearch").click(function(){selcons()});
	$("#cardinfo ").click(function(){cardinfo()});
	$("#repair   ").click(function(){
	   if(check())doRepair();
	});
	$("#btnPrt").click(function(){printPayRec()});
	$("#repair_type").change(function()	{ change_repairtype(this.value)});
	$("#jfje"	).keyup(function(){ calcu()});
	$("#zbje"	).keyup(function(){ calcu()});
	$("#jsje"	).keyup(function(){ calcu()});
	$("#btnSearch").focus();
	setDisabled(true);
	$("#repair").attr("disabled",true);
	$("#repair_type").attr("disabled",true);
});

//缴费记录
function initGrid_repair(div_id) {
	if(div_id == undefined) div_id = "gridbox";
	mygridtable = new dhtmlXGridObject(div_id);
	var yff_grid_title = "操作日期,操作类型,缴费金额(元),结算金额(元),追补金额(元),总金额(元),报警值1,断电金额(元),购电次数,流水号,缴费方式,操作员,&nbsp;";
	mygridtable.setImagePath(def.basePath +"images/grid/imgs/");
	mygridtable.setHeader(yff_grid_title);
	mygridtable.setInitWidths("150,80,80,80,80,80,80,80,80,120,80,80,*")
	mygridtable.setColAlign("center,center,left,right,right,right,right,right,right,right,left,left")
	mygridtable.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygridtable.init();
	mygridtable.setSkin("light");
	mygridtable.setColumnHidden(7, true);//隐藏断电金额
}

function getRecord_repair(rtu_id, mp_id, div_id, num , type) {
	if(div_id == undefined) div_id = "gridbox";
	if(num == undefined) {
		var div_height = $("#" + div_id).height();
		num = 20 ;
	}
	mygridtable.clearAll();
	var param = "{rtu_id:" + rtu_id + ",mp_id:" + mp_id + ",top_num: " + num + "}"; 
	$.post( def.basePath + "ajax/actCommon!getDyYFFRecs.action",{result : param}, function(data) {
		if (data.result != "") {
			var jsdata = eval('(' + data.result + ')');
			history_Records = jsdata;
			mygridtable.parse(jsdata,"json");
			//使用两种补卡方式的标志位 0只是用一种   1使用两种
			if(dycard_repairpay_flag == "1"){//允许从历史记录里面补卡
		        mygridtable.attachEvent("onRowSelect", doOnRowSelected);
		        mygridtable.selectRow(0, true);
			}else{
				//不允许从历史记录里面补卡  则默认选中最近的历史记录 禁用 onRowSelect事件
				//从历史记录里面找到最近的:"开户" , "销户" , "缴费" , "换表" , "结算"
				var list = mygridtable.getAllRowIds();//返回的是id拼成的字符串
				var array = list.split(",");
				var id = null;
				for(var rowId = 1 ;rowId <= array.length ; rowId++){
					    var oper_name = mygridtable.cells(rowId,1).getValue();
						if(oper_name  == "开户" || oper_name == "销户" || oper_name == "缴费" || oper_name == "换表" || oper_name == "结算"){
							mygridtable.selectRowById(rowId);
							flag = true;
							id = rowId;
							break;
						}
				}
				if(flag){
					 doOnRowSelected(id);
				}else{
					alert("用户当前状态下 不能进行补卡操作");
					return;
				}
			}
		}
	});
}

//上次缴费信息   not delete
function GetlastpayRcd_repair(rtu_id, mp_id){
	$.post(def.basePath + "ajax/actCommon!lastPayInfoDY.action", 
	{result : "{rtu_id : " + rtu_id + ", mp_id : " + mp_id + "}"},
	function(data){
		if(data.result == ""){
		    alert("找不到上次购电记录，不能补卡！");
		    return;
		}else{
			var json = eval('(' + data.result + ')');
			lastpayRcd = json;
			
			//$("#last_zje").html(lastpayRcd.all_money);
		}
	});
}

function selcons(){//检索   
	var js_tmp = doSearch("kzext",ComntUseropDef.YFF_DYOPER_REPAIR,rtnValue);
	if(!js_tmp)	return;
	rtnValue = js_tmp;
	
	if (rtnValue.cacl_type == SDDef.YFF_CACL_TYPE_DL) {//从库中查出的计费方式 是否为电量控
		if ((rtnValue.feeType != SDDef.YFF_FEETYPE_DFL)&& (rtnValue.feeType != SDDef.YFF_FEETYPE_MIX)&&(rtnValue.feeType != SDDef.YFF_FEETYPE_JTFL)) {
			alert("电量方式请选择单费率、混合、阶梯费率方案！");
			return;
		}
	}
		
	//20131021添加，当查询结果中的预付费表类型 为新增表时将WRITECARD_TYPE赋为6
//	var yffmeter_type = rtnValue.yffmeter_type;
//	WRITECARD_TYPE = getCardByMeter(yffmeter_type);
	
	setDisabled(false);
	setConsValue(rtnValue);
	$("#writecard_no").html(rtnValue.writecard_no);
	$("#buy_dl").html(0);
	$("#pay_bmc").html(0);
	
	getRecord_repair(rtnValue.rtu_id,rtnValue.mp_id);
	GetlastpayRcd_repair(rtnValue.rtu_id,rtnValue.mp_id);//从历史库得到上次缴费记录,给界面赋值
	change_repairtype($("#repair_type").val());
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno};
	mis_query(param, gloQueryResult);
	$("#repair").attr("disabled",false);
	
	//使用两种补卡方式的标志位    0只是用一种   1使用两种
	//$("#repair_type").attr("disabled",false);
	dycard_repairpay_flag == "1" ? $("#repair_type").attr("disabled",false):"";
	
	$("#btnPrt").attr("disabled",true);
}

//grid select事件
function doOnRowSelected(rId){
	if($("#repair_type").val()==1){
		setDisabled(false);
		$("#jfje").val(mygridtable.cells(rId,2).getValue());
		$("#jsje").val(mygridtable.cells(rId,3).getValue());
		$("#zbje").val(mygridtable.cells(rId,4).getValue());
		$("#zje" ).html(mygridtable.cells(rId,5).getValue());
		$("#last_zje").html(mygridtable.cells(rId,5).getValue());
		$("#buy_times").html(mygridtable.cells(rId,8).getValue());//grid隐藏了断电金额列.注意!!
		var dateTime = mygridtable.cells(rId,0).getValue();
		if(dateTime){
			old_op_date = dateTime.substring(0,4)   +dateTime.substring(5,7)+dateTime.substring(8,10);
			old_op_time = dateTime.substring(12,14) +dateTime.substring(15,17)+dateTime.substring(18,20);
		}
		$("#jfje").attr("disabled",true);
		$("#zbje").attr("disabled",true);
		$("#jsje").attr("disabled",true);
		calcu();		//计算电量	
	}else{
		return;
	}
}

//计算缴费信息
function calcu(){
	var jfje = 0.0, jsje = 0, zbje = 0;
	jfje = $("#jfje").val() === "" ? 0 : $("#jfje").val();
	zbje = $("#zbje").val() === "" ? 0 : $("#zbje").val();
	jsje = $("#jsje").val() === "" ? 0 : $("#jsje").val();
	var zje = round((parseFloat(jfje) + parseFloat(jsje) + parseFloat(zbje)), 3);
	if(!isNaN(zje)){		
		$("#zje").html(zje);
	}
	
	var dj_str = $("#feeproj_reteval").val() === "" ? 1 : $("#feeproj_reteval").val();
	var dj_temp = dj_str.split(",");
	var dj = dj_temp[0];
	
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

//如果非要修改，  理解  慎重 
function change_repairtype(chgtype)
{	
	if (rtnValue) {
		if (chgtype == 0) {			//已经插入电表
			$("#jfje").attr("disabled",false);
			$("#zbje").attr("disabled",false);
			$("#jsje").attr("disabled",false)
			$("#jfje").val("");
			$("#jsje").val("");
			$("#zbje").val("");
			$("#zje").html("");
			$("#buy_times").html(rtnValue.buy_times);
			$("#all_money").html(rtnValue.money);
		}
		else if (chgtype == 1) {	//未插入电表  (默认的)
			mygridtable.selectRow(0);
			$("#jfje").attr("disabled",true);
			$("#zbje").attr("disabled",true);
			$("#jsje").attr("disabled",true);
			$("#jsje").val(rtnValue.zb_money);
			$("#zbje").val(rtnValue.othjs_money);
			$("#zje" ).html(rtnValue.all_money);
			$("#jfje").val(rtnValue.pay_money);
		}
	}
}

function check(){
	//如果MIS不通，禁止缴费
	if (gloQueryResult.misUseflag == "true" && (gloQueryResult.misOkflag == "false" || gloQueryResult.misOkflag == undefined)) {
		alert("MIS不能通讯,禁止补卡...");
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
	if(!isDbl_Html("zje","总金额",0,rtnValue.moneyLimit)){
		return false;
	}
	
	//对于未插插卡,db操作为补卡时,进行条件判断
	if($("#repair_type").val()==1 && dycard_repairpay_flag == 1){
		var id   = mygridtable.getSelectedId();
	    var type = mygridtable.cells(id,1).getValue();
	    if(!(type == "开户" || type == "恢复" || type == "缴费"|| type == "换表"|| type == "结算")){
	    	alert("不能对 "+ type + "进行补卡操作");
	        return false;
	    }
	}
	
	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
	}

//	if(($("#jfje").val() == "0" || $("#jfje").val() == "")){
//		return(confirm("客户未缴费,继续操作吗?"))
//	}
	
	return true;
}

/**
 * 次方法暂时没有用,测试的时候加上
 *  
 * *  
 * *  
 * *  
 * *  
 * 
 * *  
 * *  
 * 
 */
function CheckCard(writeparams, sel_idx)
{	
	var json_out = {};
	json_out = window.top.card_comm.readExtcard();
	if(json_out == undefined || json_out ==""){
		return  false;				
	}	
	
	if (parseInt(json_out.card_type) != 0) {
		alert("此卡非空， 请先清空卡！")
		return false;
	}
	return true;
	
//	var meterType 	= json_out.meter_type;	//卡表类型
//	var meterNo,consNo,buyTime;	//表号,户号,购电次数
//	if(meterType == window.top.SDDef.YFF_METER_TYPE_ZNK || meterType == window.top.SDDef.YFF_METER_TYPE_ZNK2){
//		
//		if((WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE001) && (meterType != window.top.SDDef.YFF_METER_TYPE_ZNK)){
//			alert("用户电表为2009版规约，请使用09版购电卡!");
//			return false;
//		}
//		
//		if((WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE006) && (meterType != window.top.SDDef.YFF_METER_TYPE_ZNK2)){
//			alert("用户电表为2013版规约，请使用13版购电卡!");
//			return false;
//		}
		
//		meterNo 	= json_out.meterno; 	
//		consNo  	= json_out.consno;
//		buyTime		= json_out.pay_num;
		

		
//		//不太严谨 		需要调整
//		var kk_flag = false;
//		var tmp = "000000000000"
//		if ((tmp == meterNo) &&
//			(tmp == consNo) &&
//			(parseInt(buyTime)) == 0) {
//			kk_flag = true;
//		} 
//		else {
//			if (writeparams.writecard_no.replace(/(^0*)/g, "") != consNo.replace(/(^0*)/g, "")) {
//				alert("用户卡户号与系统查询结果不一致,确信要用此卡补卡请先清空卡！")
//				return false;
//			}
//			else if (writeparams.meterno.replace(/(^0*)/g, "") != meterNo.replace(/(^0*)/g, "")) {
//				alert("用户卡表号与系统查询结果不一致， 请到换表流程或者清空卡！")
//				return false;
//			}
//			else {
//				if (parseInt(buyTime) == 0) {			//无反写
//					if (sel_idx == 0) {					//选择反写	
//						alert("此卡上次购电后没有插卡， 确信要补卡请先清空卡！")
//						return false;
//					}
//				}
//				else {
//					if (sel_idx == 1) {					//选择未反写
//					}
//				}
//			}
//			return true;
//		}
//	}
//	else{
//		alert("未知卡类型")
//		return false;
//	}
//	return true;
}

function doRepair(){//补卡
	
	var params = {};
	
    params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money	 	= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),3);
	
	loading.loading();
	
	modalDialog.height 		= 300;
	modalDialog.width 		= 300;
	modalDialog.url 		= def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param 		= {
		showGSM : true,
		key	: ["客户编号","客户名称", "缴费金额","追补金额","结算金额","总金额"],
		val	: [$("#userno").html(),$("#username").html(), params.pay_money,params.zb_money,params.othjs_money,params.all_money]		
	};
	
	var o = modalDialog.show();
	if(!o||!o.flag){
		loading.loaded();
		return;
	}
	params.gsmFlag = o.gsm;//是否发送短信 
	
	window.setTimeout("doRepair1(" + o.gsm + ")", 10);
}

function doRepair1(gsm){
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert(sel_user_info);
		loading.loaded();
		return;
	}
	
	//GetlastpayRcd(rtu_id,mp_id);//判断上次缴费信息
	
	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.mp_id 			= $("#mp_id").val();
	params.myffalarmid 		= rtnValue.yffalarm_id;
	params.paytype 			= rtnValue.pay_type;

	params.alarm_val1		= rtnValue.yffalarm_alarm1;
	params.alarm_val2		= rtnValue.yffalarm_alarm2;
	
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();

	params.all_money	 	= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),3);

	params.date 			= 0;
	params.time 			= 0;

	params.gsmFlag = gsm;//是否发送短信 

	//type=0已经插卡  ,type=1未插卡
	var sel_idx = $("#repair_type").val();
	if (sel_idx == 0) {
		params.card_type  = SDDef.CARD_OPTYPE_REPAIR;		//补卡
		params.buynum 	  = parseInt($("#buy_times").html()) + 1;	
	}
	else {//未插卡
		params.buynum 	  = parseInt($("#buy_times").html());
		if (parseInt(params.buynum) == 1) {		//开户
			params.card_type= SDDef.CARD_OPTYPE_OPEN;		//开户
		//	alert("未插卡 开户");
		}
		else {						//缴费
			params.card_type= SDDef.CARD_OPTYPE_REPAIR;		//补卡
		//	alert("未插卡 补卡")
		}
	}
	
	params.pt 			= rtnValue.pt;
	params.ct 			= rtnValue.ct;
//	params.meter_type 	= WRITECARD_TYPE;
	params.limit_dl 	= rtnValue.moneyLimit;
	params.feeproj_id 	= rtnValue.feeproj_id;
	params.writecard_no	= rtnValue.writecard_no;
	params.meterno  	= rtnValue.esamno;
	params.cardno 		= rtnValue.userno;

	params.tz_val		= rtnValue.tz_val				//赊欠门限值
	params.card_rand 	= rtnValue.card_rand;			//随机数
	params.card_area 	= rtnValue.card_area;			//区域码
	params.cardtype 	= rtnValue.cardtype;			//预付费电表类型
	params.card_pass	= rtnValue.card_pass;
	params.cacl_type 	= rtnValue.cacl_type;			//金额 表底 量控
	params.buy_dl		= $("#buy_dl").html();
	params.pay_bmc		= 	$("#pay_bmc").html();

	if ((params.cardtype == "JJ_003") || (params.cardtype == "JJ_004")){
		params.card_type  	= SDDef.CARD_OPTYPE_REPAIR;
	}

	params.operType  = ComntUseropDef.YFF_DYOPER_REPAIR;	//操作类型决定更新标识
	params.jt_cycle_md = rtnValue.jt_cycle_md;				//低压阶梯年结算日
	
	if (CheckCard(params, sel_idx) == false){
		loading.loaded();
		return;
	}
	$.post( def.basePath + "ajaxoper/actWebCard!getWriteCardExt.action", 		//后台处理程序
		{
			result	: jsonString.json2String(params)
		},
		function(data) {			    	//回传函数
			loading.loaded();		
			var retStr 		   = data.result;
			var ret_json       = window.top.card_comm.writeExtcard(retStr);
			if(ret_json.errno  == 0){
				if ((params.cardtype == "JJ_003") || (params.cardtype == "JJ_004")) {
					params.update_flag = 0x02;
					params.card_pass = ret_json.strinfo;			//传到后台 数据库中存储  作为密码
				}
				else {
					params.update_flag = 0x00;
				}
			//	alert("写卡成功")					//注释掉了  成功 后 弹出一次提示框 需要处理 一下
				wrtDB_repair(params, sel_idx);
			}else{
				alert("写卡失败！\n" + ret_json.errsr);
			}
		}
	);
}

function wrtDB_repair(params, sel_idx){	//补卡
	var type    = null;
	if (sel_idx == 0) {//已经插过电卡  走缴费
		type   = ComntUseropDef.YFF_DYOPER_PAY
	}
	else { //没有插过卡 走补卡
		type   = ComntUseropDef.YFF_DYOPER_REPAIR
	}
	//购电次数减一
	params.buynum = $("#buy_times").html();
	loading.loading();
	if(sel_idx == 1){
		params.old_op_date	= old_op_date;
		params.old_op_time 	= old_op_time;
	}else{
		params.old_op_date	= lastpayRcd.op_date;
		params.old_op_time 	= lastpayRcd.op_time
	}
	params.misUseflag   = gloQueryResult.misUseflag;
	
	var json_str = jsonString.json2String(params);
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag 	: true,
			userData1		: $("#rtu_id").val(),
			mpid 			: $("#mp_id").val(),
			gsmflag 		: 0,
			dyOperPay		: json_str,
			dyOperRepair    : json_str,//因为不知道是pay||repair 所以都传到后台,根据userData2来区分
			userData2 		: type
		},
	    function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				//sel_idx = 0 已经插过卡, buynum+1 走mis 其余不走mis
				if(sel_idx == 0){
					var ret_json = eval("(" + data.dyOperPay + ")");			
				
					//向MIS系统缴费
					if(parseFloat(params.pay_money) > 0 && gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") 
					{
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
				}
				alert("补卡成功!");
				getRecord_repair(rtnValue.rtu_id,rtnValue.mp_id);	//刷新缴费记录
				if(data.dyOperPay == "" && data.dyRepair ==""){
					alert("获取返回的操作信息失败。请到[补打发票]界面补打。")
				}else{
					$("#btnPrt").attr("disabled",false);
					$("#repair").attr("disabled",true);
					setDisabled(true);
					if(sel_idx == 0){
						window.top.WebPrint.setYffDataOperIdx2params(data.dyOperPay,window.top.WebPrint.nodeIdx.dycard);//打印用的参数
					}else{
						window.top.WebPrint.setYffDataOperIdx2params(data.dyOperRepair,window.top.WebPrint.nodeIdx.dycard);//打印用的参数
					}
				}
			}
			else {
				alert("向主站发送补卡命令失败" + (data.operErr ? data.operErr : ''));
			}
		}
	);
}

function cardinfo(){	//卡内信息
	modalDialog.width = 500;
	modalDialog.height = 600;
	modalDialog.url = def.basePath + "jsp/dialog/extcardinfo.jsp";
	modalDialog.show();
}


function printPayRec() {
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
	$("#btnPrt").attr("disabled",true);
}

function setDisabled(mode){	//设置状态
	if(!mode){
		$("#jfje").val("");
		$("#zbje").val("");
		$("#jsje").val("");
		$("#buy_times").html("");
		$("#last_zje").html("");
		$("#zje").html("");
	}

	$("#jfje").attr("disabled",mode);
	$("#zbje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
}