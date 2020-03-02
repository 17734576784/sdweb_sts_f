//高压补卡
var mygrid 		= null;
var rtnValue 	= null;
var lastpayRcd 	= null;
var history_Records = null;
var mygrid          = null;
var old_op_date     = null;
var old_op_time     = null;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	mygrid = new dhtmlXGridObject('gridbox');
	initGrid_repair();

	$("#repair_type").change(function()	{ change_repairtype(this.value)});
	$("#btnSearch"	).click(function()	{ selcons()});
	$("#cardinfo"   ).click(function()	{ window.top.card_comm.cardinfo()});//卡内信息
	$("#repair"   	).click(function()	{ if(check())doRepair();});
	
	$("#btnPrt"	).click(function(){ printPayRec()});
	$("#jfje"	).keyup(function(){ calcu()});
	$("#zbje"	).keyup(function(){ calcu()});
	$("#jsje"	).keyup(function(){ calcu()});
	
	$("#repair_type").attr("disabled",true);
	$("#btnSearch").focus();
	setDisabled(true);
});

function initGrid_repair(){
	var yff_grid_title ='操作日期,操作类型,缴费金额(元),追补金额(元),结算金额(元),总金额(元),断电值,报警值1,报警值2,购电次数,流水号,缴费方式,操作员,&nbsp;';
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("170,80,80,80,80,80,80,80,80,80,150,80,80,*");
	mygrid.setColAlign("center,center,right,right,right,right,right,right,right,right,left,left,left");
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.enableTooltips("false,false,false,false,false,false,false,false,false,false,false,false,false");
	mygrid.init();
	mygrid.setSkin("light");
	mygrid.setColumnHidden(6, true);
}

function getGyYffRecs_repair(){//缴费记录
	mygrid.clearAll();
	var param = "{\"rtu_id\":\"" + $("#rtu_id").val() + "\",\"zjg_id\":\"" +$("#zjg_id").val() + "\",\"top_num\":\"20\"}";
	$.post( def.basePath + "ajax/actCommon!getGyYFFRecs.action",{result:param},	function(data) {			    	//回传函数
		if (data.result != "") {
			jsdata = eval('(' + data.result + ')');
			history_Records = jsdata;
			mygrid.parse(jsdata,"json");
			var flag = false;//找到正确的补卡标志   
			//使用两种补卡方式的标志位 0只是用一种   1使用两种
			if(gycard_repairpay_flag == "1"){//允许从历史记录里面补卡
		        mygrid.attachEvent("onRowSelect", doOnRowSelected);
		        mygrid.selectRow(0, true);
			}else{
		        //不允许从历史记录里面补卡  则默认选中最近的历史记录 禁用 onRowSelect事件
				//从历史记录里面找到最近的:"开户" , "销户" , "缴费" , "换表" , "结算"
				var list = mygrid.getAllRowIds();//返回的是id拼成的字符串
				var array = list.split(",");
				var id = null;
				for(var rowId = 1 ;rowId <= array.length ; rowId++){
				    var oper_name = mygrid.cells(rowId,1).getValue();
					if(oper_name == "开户" || oper_name == "销户" || oper_name == "缴费" || oper_name == "换表" || oper_name == "结算"){
						mygrid.selectRowById(rowId);
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

function lastPayInfo_repair(){
	$.post(def.basePath + "ajax/actCommon!lastPayInfoGY.action", 
		{result : "{rtu_id : " + rtnValue.rtu_id + ", zjg_id : " + rtnValue.zjg_id + "}"},
	function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			lastpayRcd = json;
		} else {
			alert("找不到上次购电记录，不能补卡！");
		}
	});
}

//grid select事件
function doOnRowSelected(rId){
	if($("#repair_type").val()==1){
		setDisabled(true);
		$("#jfje").val(mygrid.cells(rId,2).getValue());
		$("#jsje").val(mygrid.cells(rId,3).getValue());
		$("#zbje").val(mygrid.cells(rId,4).getValue());
		$("#zje" ).html(mygrid.cells(rId,5).getValue());
		$("#last_zje").html(mygrid.cells(rId,5).getValue());
		$("#yffalarm_alarm1").val(mygrid.cells(rId,7).getValue());
		$("#yffalarm_alarm2").val(mygrid.cells(rId,8).getValue());
		$("#buy_times").html(mygrid.cells(rId,9).getValue());//grid隐藏了断电金额列.注意!!
		//var dateTime = history_Records.rows[rId-1].data[0];
		var dateTime = mygrid.cells(rId,0).getValue();
		if(dateTime){
			old_op_date = dateTime.substring(0,4)   +dateTime.substring(5,7)+dateTime.substring(8,10);
			old_op_time = dateTime.substring(12,14) +dateTime.substring(15,17)+dateTime.substring(18,20);
		}
	}else{
		return;
	}
}

//如果非要修改，  理解  慎重 
function change_repairtype(chgtype)
{	
	if (rtnValue) {
		if (chgtype == 0) {//已经插入电表
			setDisabled(false);
			$("#buy_times").html(rtnValue.buy_times);
		}
		else if (chgtype == 1) {	//未插入电表
			$("#jfje").val(rtnValue.pay_money);
			$("#zbje").val(rtnValue.zb_money);
			$("#jsje").val(rtnValue.othjs_money);
			$("#zje").html(rtnValue.all_money);
			$("#buy_times").html(rtnValue.buy_times);
			calcu();
			setDisabled(true);
		}
	}
}

//检索
function selcons(){
	var rtnValue1 = doSearch("ks",ComntUseropDef.YFF_GYOPER_REPAIR,rtnValue);
	if(!rtnValue1){
		return;
	}
	if(!window.top.card_comm.checkInfo(rtnValue1)) return;
	
	rtnValue = rtnValue1;
	setDisabled(false);
	$("#repair").attr("disabled",false);
	
	//使用两种补卡方式的标志位    0只是用一种   1使用两种
	//$("#repair_type").attr("disabled",false);
	gycard_repairpay_flag == "1" ? $("#repair_type").attr("disabled",false):"";

	setConsValue(rtnValue);					//填写基本信息
	setCardExt(rtnValue);

	getGyYffRecs_repair();					//查询缴费记录
	
	lastPayInfo_repair()                    //查询上次缴费记录

	getMoneyLimit(rtnValue.feeproj_id);		//囤积限值
	getAlarmValue(rtnValue.yffalarm_id);	//获取报警方式

//	GetGylastPay(rtnValue);					//如果从记录中读取， 不能解决未插卡， 但说插卡之情况	
	var sel_idx = $("#repair_type").val();
	change_repairtype (sel_idx);	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, zjg_id : rtnValue.zjg_id, busi_no : rtnValue.userno};
	mis_query(param, gloQueryResult);
}

//如果非要修改，  理解  慎重 
function change_repairtype(chgtype)
{	
	if (rtnValue) {
		if (chgtype ==	0) {			//已经插入电表
			setDisabled(false);
			$("#buy_times").html(rtnValue.buy_times);
			calcu();
		}
		else if (chgtype == 1) {	//未插入电表
			setDisabled(true);
			$("#jfje").val(rtnValue.pay_money);
			$("#zbje").val(rtnValue.zb_money);
			$("#jsje").val(rtnValue.othjs_money);
			$("#zje").html(rtnValue.all_money);
			$("#buy_times").html(rtnValue.buy_times);
			calcu();
		}
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
		var ke003f = (WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE003);	

		if (ke003f) {

			var dj 	= $("#feeproj_reteval").val()===""?1:$("#feeproj_reteval").val();
			var blv = $("#blv").html()===""?1:$("#blv").html();
			var zbd = 0; //(rtnzbd==="" || isNaN(rtnzbd)) ? 0 : rtnzbd;
			var alarm_val1 = (rtnValue.yffalarm_alarm1 == "" || rtnValue.yffalarm_alarm1 == undefined ) ? 30 : rtnValue.yffalarm_alarm1;

			var alarm_val2 = ((rtnValue.yffalarm_alarm2 == 0) || (rtnValue.yffalarm_alarm2 == "") || (rtnValue.yffalarm_alarm2 == undefined)) ? 5 : rtnValue.yffalarm_alarm2;

			var ret_val = cardalarm_calcu(zje, dj, blv, zbd, alarm_val1, alarm_val2, rtnValue.type, zbd);

			$("#buy_dl").html(ret_val.buy_dl);
			$("#pay_bmc").html(ret_val.pay_bmc);
			$("#yffalarm_alarm1").val(ret_val.alarm_code1);
			$("#yffalarm_alarm2").val(ret_val.alarm_code2);
			
		}
		else {
			setAlarmValue(rtnValue.yffalarm_alarm1, rtnValue.yffalarm_alarm2, zje)
		}
	}
}

//验证
function check(){

	//如果MIS不通，禁止缴费
	if (gloQueryResult.misUseflag == "true" && (gloQueryResult.misOkflag == "false" || gloQueryResult.misOkflag == undefined)) {
		alert("MIS不能通讯,禁止缴费...");
		return false;
	}
	
	if($("#repair").attr("disabled"))return false;
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

	if(!isDbl("zbje" , "追补金额" ,-money_limit,money_limit, false)){//追补金额应该在0~囤积值之间
		return false;
	}
	
	if(!isDbl("jsje",  "结算金额" ,-money_limit, money_limit, false)){
		return false;
	}
	
	if(!isDbl("jfje",  "缴费金额" ,0, money_limit, false)){
		return false;
	}

	//20130402
	var zje = $("#zje").html();
	if(!isDbl_Html("zje" ,  "总金额"  , 0, money_limit, false)){//总金额应该在0~囤积值之间
		return false;
	}

	//对于未插插卡,db操作为补卡时,进行条件判断
	if($("#repair_type").val()==1 && gycard_repairpay_flag == 1){
		var id   = mygrid.getSelectedId();
	    var type = mygrid.cells(id,1).getValue();
	    if(!(type == "开户" || type == "恢复" || type == "缴费"|| type == "换表"|| type == "结算")){
	    	alert("不能对 "+ type + "进行补卡操作");
	        return false;
	    }
	}
	
	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
	}

	var zje = $("#zje").val();
	var jfje = $("#jfje").val();
//20131213 dubr 补卡未交费提醒两次 取消这次提醒
//	if(jfje == 0 || jfje == ""){	//总金额>0但是 缴费金额为0或为""(空)
//		return(confirm("用户未交费,是否继续"));
//	}
	
	return true;
}

//补卡
function doRepair(){
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
		return;
	}
	params.gsmFlag = o.gsm;//是否发送短信 
	
	window.setTimeout("doRepair1(" + o.gsm + ")", 10);
}

function doRepair1(gsm){

	var rtu_id = $("#rtu_id").val(), zjg_id = $("#zjg_id").val();
	if(rtu_id === "" || zjg_id === ""){
		loading.loaded();
		alert(sel_user_info);
		return;
	}
	if (lastpayRcd == "") {
		alert("找不到上次购电记录，不能补卡！");
	}

	
	var params = {};

	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.myffalarmid 		= $("#yffalarm_id").val();
	params.paytype 			= $("#pay_type").val();

	params.alarm_val1		= $("#yffalarm_alarm1").val();
	params.alarm_val2		= $("#yffalarm_alarm2").val();
	
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();

	params.all_money	 	= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),3);

	params.date 			= 0;
	params.time 			= 0;
	params.buy_dl 			= $("#buy_dl").html()==="" ? 0:$("#buy_dl").html();
	params.pay_bmc 			= $("#pay_bmc").html()===""? 0:$("#pay_bmc").html();

	params.shutdown_bd 		= 0;

	params.gsmFlag 			= gsm;//是否发送短信 

	params.pt 				= rtnValue.pt_ratio;
	params.ct 				= rtnValue.ct_ratio;
	params.meter_type 		= WRITECARD_TYPE;

	//判断已经插卡 未插卡
	var sel_idx = $("#repair_type").val();
	if (sel_idx == 0) {
		params.card_type  = SDDef.CARD_OPTYPE_REPAIR;		//补卡
		params.buynum 	  = parseInt($("#buy_times").html()) + 1;	
	}
	else { //if (sel_idx == 1){
		params.buynum 	  = parseInt($("#buy_times").html());
		if (parseInt(params.buynum) == 1) {		//开户
			params.card_type= SDDef.CARD_OPTYPE_OPEN;		//开户
		}
		else {						//缴费
			params.card_type= SDDef.CARD_OPTYPE_REPAIR;		//补卡
		}
	}
	
	params.feeproj_id 	= $("#feeproj_id").val();
	params.limit_dl 	= money_limit;

	params.meterno   	= rtnValue.meter_id;
	params.cardno    	= $("#userno").html();
	params.writecard_no	= $("#writecard_no").html();
	params.pay_bmc 		= $("#pay_bmc").html();	
	params.buy_dl 		= $("#buy_dl").html();	

	params.operType  = ComntUseropDef.YFF_GYOPER_REPAIR;	//操作类型决定更新标识

	if (CheckCard(params, sel_idx) == false){
		loading.loaded();
		return;
	} 

	
	$.post( def.basePath + "ajaxoper/actWebCard!getcardWrtInfo.action", 		//后台处理程序
		{
			result	: jsonString.json2String(params)
		},
		function(data) {			    	//回传函数	
			var retStr 	 = data.result.toString();
			var ret_json = window.top.card_comm.writecard(retStr);
			if(ret_json.errno == 0){
				$("#repair").attr("disabled", true);
				alert("写卡成功")
				WrtDB_Repair(params, sel_idx);
			}else{
				loading.loaded();
				alert("写卡失败！\n" + ret_json.errsr);
			}
	
		}
	);
}

function WrtDB_Repair(params, sel_idx)
{
	var type    = null;
	if (sel_idx == 0) {//已经插过电卡  走缴费
		type   = ComntUseropDef.YFF_GYOPER_PAY;
		params.old_op_date	= lastpayRcd.op_date;
		params.old_op_time 	= lastpayRcd.op_time;
	}
	else { //没有插过卡 走补卡
		type   = ComntUseropDef.YFF_GYOPER_REPAIR;
		params.old_op_date	= old_op_date;
		params.old_op_time 	= old_op_time;
	}
	//20130402
	params.type = 'localcard';//本地卡式
	params.shutdown_bd 	= 0;
	params.buynum 		= $("#buy_times").html();//购电次数减一
	//params.buynum 		= params.buynum - 1;
	var json_str = jsonString.json2String(params);
	$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
		{
			userData1		: params.rtu_id,
			zjgid 			: params.zjg_id,
			gsmflag 		: params.gsmFlag,
			gyOperPay 		: json_str,
			gyOperRepair    : json_str,//因为不知道是pay||repair 所以都传到后台,根据userData2来区分
			userData2 		: type
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {		
				getGyYffRecs_repair();
				$("#buy_times").html(parseInt($("#buy_times").html())+1);
				if(sel_idx == 0){//sel_idx = 0 已经插过卡, buynum+1 走miss 其余不走miss			
					//向MIS系统缴费
					var ret_json = eval("(" + data.gyOperPay + ")");
					if(parseFloat(params.pay_money) > 0 && gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
						var ret_json = eval("(" + data.gyOperPay + ")");
						var mis_para 	= {};
								
						mis_para.rtu_id = ret_json.rtu_id;           
						mis_para.zjg_id = params.zjg_id;             
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
					
				}
				alert("补卡成功!");
				if(data.gyOperPay == "" && data.gyOperRepair ==""){
					alert("获取返回的操作信息失败。请到[补打发票]界面补打。")
				}else{
					$("#btnPrt").attr("disabled",false);
				    $("#repair").attr("disabled",true);
				    if(type  == ComntUseropDef.YFF_GYOPER_PAY){
						window.top.WebPrint.setYffDataOperIdx2params(data.gyOperPay);//打印用的参数
				    }
					else{
				    	window.top.WebPrint.setYffDataOperIdx2params(data.gyOperRepair);//打印用的参数
				    }
				}
			}
			else{
				alert("向主站发送命令失败" + (data.operErr ? data.operErr : ''));
			}
		}
	);
}

//打印发票
function printPayRec(){
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
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintGy();
}

function setDisabled(mode){	//设置状态
//	mode = false;
	if(!mode){
		$("#jfje").val("");
		$("#zbje").val("");
		$("#jsje").val("");
		$("#zje").val("");
		$("#yffalarm_alarm1").val("");
		$("#yffalarm_alarm2").val("");
	}

	$("#jfje").attr("disabled",mode);
	$("#zbje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#yffalarm_alarm1").attr("disabled",mode);
	$("#yffalarm_alarm2").attr("disabled",mode);
	
}	

function setCardExt(rtnValue)
{
	//add ext
	var tmp_val = rtnValue.cardmeter_type.split("]");
	WRITECARD_TYPE = tmp_val[0].split("[")[1];
//调整 20130402	
//	WRITECARD_TYPE = tmp_val[0].substring(1);;
	
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

function GetGylastPay(rtnValue){

	lastpayRcd = ""
	$.post(def.basePath + "ajax/actCommon!lastPayInfoGY.action", 
		{result : "{rtu_id : " + rtnValue.rtu_id + ", zjg_id : " + rtnValue.zjg_id + "}"},
	function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			lastpayRcd = json;
		} 
		else {
			lastpay  = "";
			alert("找不到上次缴费记录 !")
		}

		var sel_idx = $("#repair_type").val();
		change_repairtype (sel_idx);	
	});
}

//清空页面信息
function clearResult(){
	setConsValue(rtnValue,true);
	initGrid();
	$("#buyp_times").html("");
	$("#last_jfje").html("");
	$("#repair").attr("disabled",true);
	$("#rtuinfo").attr("disabled",true);
}

function CheckCard(writeparams, sel_idx)
{	
	var json_out = {};
	json_out = window.top.card_comm.readcard();
	if(json_out == undefined || json_out ==""){
		return  false;				
	}
	
	var meterType 	= json_out.meter_type;	//卡表类型	
	var meterNo 	= json_out.meterno; 	//表号
	var consNo  	= json_out.consno;		//户号
	var buyTime		= json_out.back_pay_num;//返写的购电次数	
	//var buyTime	= json_out.back_pay_num;//正写的购电次数
	
	//20131127新增判断
	if((WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE001) && (meterType != window.top.SDDef.YFF_METER_TYPE_ZNK)){
		alert("用户电表为2009版规约，请使用09版购电卡!");
		return false;
	}
	
	if((WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE006 && meterType != window.top.SDDef.YFF_METER_TYPE_ZNK2)){
		alert("用户电表为2013版规约，请使用13版购电卡!");
		return false;
	}
	//end

	//不太严谨 		需要调整
	var kk_flag = false;
	if (meterType == window.top.SDDef.YFF_METER_TYPE_6103)	{
		var tmp = "00000000000000000000"
		if ((tmp != consNo) || (parseInt(buyTime) <= 0) || (parseInt(buyTime) >= 1000000)) {
			kk_flag  = true;
		}
		else {
			if (writeparams.writecard_no.replace(/(^0*)/g, "") != consNo.replace(/(^0*)/g, "")) {
				alert("用户卡户号与系统不一致,确信要用此卡补卡请先清空卡！")
				return false;
			}
			return true;
		}
	}
	else {
		var tmp = "000000000000"

		if ((tmp == meterNo) &&
			(tmp == consNo) &&
			(parseInt(buyTime)) == 0) {
			kk_flag = true;
		} 
		else {
			if (writeparams.cardno.replace(/(^0*)/g, "") != consNo.replace(/(^0*)/g, "")) {
				alert("用户卡户号与系统不一致,确信要用此卡补卡请先清空卡！")
				return false;
			}
			else if (writeparams.meterno.replace(/(^0*)/g, "") != meterNo.replace(/(^0*)/g, "")) {
				alert("用户卡表号与系统不一致， 请到换表流程或者清空卡！")
				return false;
			}
			else {
				if (parseInt(buyTime) == 0) {			//无反写
					if (sel_idx == 0) {					//选择反写	
						alert("此卡上次购电后没有插卡， 确信要补卡请先清空卡！")
						return false;
					}
				}
				else {
					if (sel_idx == 1) {					//选择未反写
						
					}
				}
			}
			return true;
		}
	}

	return true;
}

function jfInfo(){
	
	var repair_type = $("#repair_type").val();
	if(repair_type == 0){
		
		if(rtnValue != null){

			$("#buyp_times").html(rtnValue.buy_times);
			$("#last_jfje").html(rtnValue.pay_money);	
		}
	}else{
		
	}
}
 
//function change_repairtype(chgtype)
//{	
//	if (chgtype == 0) {			//已经插入电表
//		setDisabled(false);
//		if (lastpayRcd != "") {
//			$("#buy_times").html(lastpayRcd.buy_times);
//		}
//		else {
//			alert(rtnValue.buy_times);
//			$("#buy_times").html(rtnValue.buy_times);
//		}
//	}
//	else if (chgtype == 1) {	//未插入电表
//		if (lastpayRcd != "") {
//			$("#jfje").val(lastpayRcd.pay_money);
//			$("#zbje").val(lastpayRcd.zb_money);
//			$("#jsje").val(lastpayRcd.othjs_money);
//			$("#zje").html(lastpayRcd.all_money);
//			$("#buy_times").html(lastpayRcd.buy_times);
//		}
//		else {
//			$("#jfje").val(rtnValue.pay_money);
//			$("#zbje").val(rtnValue.zb_money);
//			$("#jsje").val(rtnValue.othjs_money);
//			$("#zje").html(rtnValue.all_money);
//			$("#buy_times").html(rtnValue.buy_times);
//
//		}
//		calcu();
//
//		setDisabled(true);
//	}
//}