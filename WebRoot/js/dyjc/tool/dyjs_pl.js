//缴费
var jsonCons   = null;
var matchFlag  = 0;//0：可结算; 其他: 不能结算 
var rtnValue   = null;
var bdyg   	   = [-1, -1, -1];
var mp_id  	   = [-1, -1, -1];
var clpNum 	   = 0;
var jsDate 	   = 0; //抄表日yyyymmdd：excel的yyyymm + zjgpay_para中的cb_dayhour的dd
var jsTime 	   = 0; //抄表日hhmm ：zjgpay_para中的cb_dayhour的hh + "00"
var cbDayYe    = 0; //抄表日余额:excel中的余额。
var dbRemain   = 0;	//电表余额
var dbRemainf  = 0;	//电表余额标志
var khDate 	   = 0;
var mis_jsje = 0, mis_jsflag = false;
var cacl_type, feectrl_type;		//计费方式,费控方式

var last_sel_id = -1;
var gridColumn = {
		"index"			: 1,
		"rtuId" 		: 2,
		"mpId"			: 3,
		"consName"		: 4,
		"consNo"		: 5,
		"remain"		: 6,
		"bdzyz"			: 7,
		"matchDesc" 	: 8,
		"matchFlag" 	: 9,
		"dbremain"		: 10,
		"dbremainf"		: 11,
		"cacl_type"		: 12,
		"feectrl_type" 	: 13,
		"describe"		: 14,
		"zb_money"		: 15,
		"othjs_money"	: 16,
		"all_money"		: 17,
		"alarm1"		: 18,
		"alarm2" 		: 19,
		"buy_times" 	: 20,
		"now_remain"	: 21,
		"cb_dayhour"	: 22,
		"power_rela1"	: 23,
		"power_rela2" 	: 24,
		"pay_type"		: 25,
		"res_id"		: 26,
		"shutdown_val"	: 27,
		"result"		: 28
}

$(document).ready(function(){
	initCbym();
	initGridCons();
	
	$("#upload_bdye").click(function(){showUpload()});
	$("#btnRemain").click(function(){remainSum()});
	
	$("#btnPay").click(function(){
		payMoney();
	});
});

function initGridCons(){
	gridHeader  = "<img src='" + def.basePath + "images/grid/imgs/item_chk0.gif' onclick='selectAllOrNone(this);' />,"
	gridHeader += "序号,rtuId,mpId,SG186系统参数,#cspan,#cspan,#cspan,匹配档案,是否匹配,电表余额,副表余额,计费方式,费控方式,售电子系统参数,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,抄表日,mpId1,mpId2,pay_type,res_id,shutdown_val,操作结果";
	attachheader = "#rspan,#rspan,#rspan,#rspan,用户名称,户号,预收金额,有功(总),#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,用户名称,追补金额&nbsp;<img src='"+ def.basePath + "images/grid/imgs/dhtmlx_grid_edit.gif'>,结算金额&nbsp;<img src='"+ def.basePath + "images/grid/imgs/dhtmlx_grid_edit.gif'>,总金额,报警金额1(元),报警金额2(元),购电次数,当前余额,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan";
	
	mygridCons.setImagePath(def.basePath + "images/grid/imgs/");
	mygridCons.setHeader(gridHeader);
	mygridCons.attachHeader(attachheader);
	mygridCons.setColumnHidden(gridColumn.rtuId,		true);	//rtuId
	mygridCons.setColumnHidden(gridColumn.mpId,			true);	//mpId
	mygridCons.setColumnHidden(gridColumn.matchFlag,	true);	//是否禁用选择框标志
	mygridCons.setColumnHidden(gridColumn.dbremain,		true);	//电表余额
	mygridCons.setColumnHidden(gridColumn.dbremainf,	true);	//副表余额
	mygridCons.setColumnHidden(gridColumn.cacl_type,	true);	//计费方式
	mygridCons.setColumnHidden(gridColumn.feectrl_type,	true);	//费控方式
	mygridCons.setColumnHidden(gridColumn.cb_dayhour,	true);	//抄表日时间
	mygridCons.setColumnHidden(gridColumn.power_rela1,	true);	//mpId1
	mygridCons.setColumnHidden(gridColumn.power_rela2,	true);	//mpId2
	mygridCons.setColumnHidden(gridColumn.pay_type,		true);	//pay_type
	mygridCons.setColumnHidden(gridColumn.res_id,		true);	//res_id
	mygridCons.setColumnHidden(gridColumn.shutdown_val,	true);	//shutdown_val

	mygridCons.setInitWidths("40,40,40,50,180,120,100,100,100,100,100,100,100,100,180,100,100,100,100,100,100,100,100,100,100,100,100,100,260,*");
	mygridCons.setColAlign("center,center,center,center,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left");
	mygridCons.setColTypes("ch,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ed,ed,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygridCons.enableTooltips("false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false");
	mygridCons.init();
	mygridCons.setSkin("light");
	
	//编辑事件
	mygridCons.attachEvent("onEditCell",doOnEditCell);
	
}

function doOnEditCell(stage,rId,cInd,nValue,oVCalue){//后台赋值的行id与行索引一致
	var zbje = 0.0, jfje = 0.0, jsje = 0.0;
	if(stage == 2){
		zjeCompute(rId);
	}
	return true;
}

//计算算总金额
function zjeCompute(index){
	var zbje = 0.0, jfje = 0.0, jsje = 0.0;
	zbje = mygridCons.cells2(index, gridColumn.zb_money).getValue() === "" ? 0 : mygridCons.cells2(index, gridColumn.zb_money).getValue();
	jsje = mygridCons.cells2(index, gridColumn.othjs_money).getValue() === "" ? 0 : mygridCons.cells2(index, gridColumn.othjs_money).getValue();
	zje  = round(parseFloat(zbje) + parseFloat(jsje),def.point);
	if(!isNaN(zje)){
		mygridCons.cells2(index, gridColumn.all_money).setValue(zje);
	}
}

//获取时间
function initCbym() {
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	if(month < 10) month = "0" + month;
	$("#cb_ym").val(year + "年" + month + "月");
}

function parsegrid_spec(resultdata){//必须先打开文件，提交from时走的	
	if(resultdata != "" && resultdata != "null"){
		jsonCons = eval('(' + resultdata + ')');
		mygridCons.clearAll();
		mygridCons.parse(jsonCons, "", "json");
		setRowDisable();
		mygridCons.selectRow(0);
		$("#btnRemain").attr("disabled",false);
	}
}

function showUpload(){//显示弹出窗口--上传页面
	$("#btnPay").attr("disabled",true);
	
	modalDialog.height = 300;
	modalDialog.width  = 400;
	modalDialog.param  = "pl";
	modalDialog.url    = def.basePath + "jsp/dyjc/dialog/uploadSG186BdYe.jsp";
	var o = modalDialog.show();
	
	if(!o && o!= ""){
		return;
	}
	
	parsegrid_spec(o);	
}

function check_jsye(rowIndex) {
	mis_jsflag = false;
	
	var othjs_money = mygridCons.cells2(rowIndex, gridColumn.othjs_money).getValue() === "" ? 0 : mygridCons.cells2(rowIndex, gridColumn.othjs_money).getValue();
	//结算金额为负时，其绝对值需小于(当前金额-报警值)
	if(parseFloat(othjs_money) < 0) {
		var alarm_val1 = mygridCons.cells2(rowIndex, gridColumn.alarm1).getValue();
		var now_remain = mygridCons.cells2(rowIndex, gridColumn.now_remain).getValue();
		if(now_remain == "") now_remain = 0;
		now_remain = parseFloat(now_remain);
		if(now_remain < alarm_val1) {
			//alert("当前金额小于报警值1,不能结算");
			mygridCons.cells2(rowIndex, gridColumn.result).setValue("<font color='red'>当前金额小于报警值1,不能结算</font>");
			setRowDisableByIndex(rowIndex);//新增加
			return false;
		}
		if(now_remain + parseFloat(othjs_money) < alarm_val1) {
			othjs_money = -(now_remain - parseFloat(alarm_val1));
			mis_jsje 	= othjs_money;
			mis_jsflag 	= true;
		}
	}	
	return true;
}


function payMoney(){//结算--其实就是走的缴费。结算金额为补差值。其余金额都置0。
	var rowparam = getSDparams();
	var rowparams = new Array();
	//没选择行，提示
	if(!rowparam){
		alert("请选择结算对象！");
	}
	//只选择了一行
	else if(rowparam.length == 1){
		rowparams[0] = rowparam;
	}
	//选择了多行记录
	else{
		rowparams = rowparam.split(",");//rowIndex
	}

	var new_rowparams = new Array();//复制rowparams中可以通过check_jsye()中的项
	//逐条判断check_jsye()时出现了异步,先排除置灰项
	for(var i=0;i<rowparams.length;i++){
		if(check_jsye(rowparams[i])) {
			new_rowparams.push(rowparams[i]);
		}
	}
	
	loading.loading();
	payMoney_child(0,new_rowparams);
}


function remainSum(){//获取结算金额

	var rowparam = getSDparams();
	var rowparams = new Array();
	//没选择行，提示
	if(!rowparam){
		alert("请选择结算对象！");
	}
	//只选择了一行
	else if(rowparam.length == 1){
		rowparams[0] = rowparam;
	}
	//选择了多行记录
	else{
		rowparams = rowparam.split(",");//rowIndex
	}
	
	loading.loading();
	remainSum_child(0,rowparams);
	$("#btnPay").attr("disabled",false);
}

//全选&全不选
function selectAllOrNone(checked) {      
	if(checked.src.indexOf("_dis.gif")!=-1){
		return;
	}
	var flag = false;
	if (checked.src.indexOf("item_chk0.gif") != -1) {
		flag = true;
		checked.src = def.basePath + 'images/grid/imgs/item_chk1.gif';
	} else if (checked.src.indexOf("item_chk1.gif") != -1) {
		flag = false;
		checked.src = def.basePath +  'images/grid/imgs/item_chk0.gif';
	}

	for ( var i = 0; i < mygridCons.getRowsNum(); i++) {
		if(mygridCons.cells2(i, gridColumn.matchFlag).getValue() == "0"){
			mygridCons.cells2(i, 0).setValue(flag);
		}
	}	
}

//禁用行选择
function setRowDisable(){
	for(var i=0; i<mygridCons.getRowsNum(); i++){
		mygridCons.cells2(i, gridColumn.zb_money).setDisabled(true);  //禁用zbje编辑框
		if(mygridCons.cells2(i, gridColumn.matchFlag).getValue() != "0") {
			mygridCons.cells2(i, 0).setValue(false);
			mygridCons.cells2(i, 0).setDisabled(true);
		}
	}
}

//根据行索引禁用行选择
function setRowDisableByIndex(index){
	mygridCons.cells2(index, gridColumn.matchFlag).setValue("-1");
	mygridCons.cells2(index, 0).setValue(false);
	mygridCons.cells2(index, 0).setDisabled(true);
}

//获取选中行索引，格式：rowIndex1,rowIndex2,...rowIndexi
function getSDparams(){
	var params = "";
	for(var i=0; i<mygridCons.getRowsNum(); i++){
		if(mygridCons.cells2(i, gridColumn.matchFlag).getValue() == "0" && mygridCons.cells2(i, 0).getValue() == 1){
			params += i + ",";
		}
	}
	params = params.substring(0, params.length-1);
	return params;
}


/*闭包，但发生了异步
 * function checkBDInfo(index,rowparams){
	if(index == rowparams.length) return index;
	
	cb_dayhour = mygridCons.cells2(rowparams[index], gridColumn.cb_dayhour).getValue();  //抄表日
	if(cb_dayhour == ""){
		mygridCons.cells2(rowparams[index], gridColumn.result).setValue("<font color='red'>没有配置抄表日，请先配置抄表日再结算</font>");
		setRowDisableByIndex(rowparams[index]);
		checkBDInfo(++index,rowparams);
	}
	return index;
}*/

//计算结算金额，异步处理
function remainSum_child(index,rowparams){
	if(index >= rowparams.length){
		loading.loaded();
		return;
	}

	var cb_dayhour = mygridCons.cells2(rowparams[index], gridColumn.cb_dayhour).getValue();  //抄表日

	bdyg = mygridCons.cells2(rowparams[index], gridColumn.bdzyz).getValue().split("_");
	
	var dt = ("000" + cb_dayhour);
	dt = dt.substring(dt.length - 4,dt.length);
	jsDate = dt.substring(0,2); 
	jsTime = dt.substring(2,4);
	
	var t_str = $("#cb_ym").val();
	var str_cb_ym = t_str.substring(0,4) + t_str.substring(5,7); 
	var cb_ymd    = str_cb_ym + jsDate;
	
	var params = {};
	params.rtu_id = mygridCons.cells2(rowparams[index], gridColumn.rtuId).getValue();
	params.mp_id  = mygridCons.cells2(rowparams[index], gridColumn.mpId).getValue();
	mp_id[0] = params.mp_id;
	mp_id[1] = mygridCons.cells2(rowparams[index], gridColumn.power_rela1).getValue();
	mp_id[2] = mygridCons.cells2(rowparams[index], gridColumn.power_rela2).getValue();
	
	params.date = cb_ymd;
	params.time = jsTime;
	
	for(var j = 0; j < 3; j++){
		eval("params.mp_id" + j + "=mp_id[" + j + "]");
		eval("params.bd_zy"  + j + "=bdyg[" + j + "]");
		eval("params.bd_zyj" + j + "=0");
		eval("params.bd_zyf" + j + "=0");
		eval("params.bd_zyp" + j + "=0");
		eval("params.bd_zyg" + j + "=0");
	}
	
	var json_str=jsonString.json2String(params);
	$.post(
		def.basePath + "ajaxoper/actOperDy!taskProc.action", 
		{
			userData1        :  params.rtu_id,
			mpid 			 :  params.mp_id,
			userData2        :  ComntUseropDef.YFF_DYOPER_GETREMAIN,
			dyOperGetRemain  :  json_str
		},
		function(data){
			if(data.dyOperGetRemain == ""){
				mygridCons.cells2(rowparams[index], gridColumn.result).setValue("<font color='red'>无数据</font>");
				setRowDisableByIndex(rowparams[index++]);
				remainSum_child(index,rowparams);
			}
			var json = eval("(" + data.dyOperGetRemain + ")");
			if(json.remain_val == ""){
				mygridCons.cells2(rowparams[index], gridColumn.result).setValue("<font color='red'>数据出错</font>");
			}
			else{
				var dqje 		= round(json.remain_val, def.point);
				var dbremain	= round(json.reserve1, def.point);
				cbDayYe 		= mygridCons.cells2(rowparams[index], gridColumn.remain).getValue();
				dbRemain		= mygridCons.cells2(rowparams[index], gridColumn.dbremain).getValue();
				dbRemainf		= mygridCons.cells2(rowparams[index], gridColumn.dbremainf).getValue();
				feectrl_type 	= mygridCons.cells2(rowparams[index], gridColumn.feectrl_type).getValue();
				mygridCons.cells2(rowparams[index], gridColumn.othjs_money).setValue(round(parseFloat(cbDayYe) - dqje, def.point));//结算金额				
				//alert(feectrl_type  + "   "  + SDDef.YFF_FEECTRL_TYPE_RTU + "  " + dbRemainf);
				if ((feectrl_type == SDDef.YFF_FEECTRL_TYPE_RTU) && (dbRemainf == 1)) {  //终端费控
					mygridCons.cells2(rowparams[index], gridColumn.zb_money).setValue(round(parseFloat(dbRemain) - dbremain, def.point));//追补金额赋值
					mygridCons.cells2(rowparams[index], gridColumn.zb_money).setDisabled(false);	//追补金额单元格置为可编辑状态
				}
				zjeCompute(rowparams[index]);
				mygridCons.cells2(rowparams[index], gridColumn.result).setValue("计算结算金额成功！");
			}
			remainSum_child(index + 1,rowparams);
		}
	);
}

//结算，异步处理
function payMoney_child(index,rowparams){
	
	if(index >= rowparams.length){
		loading.loaded();
		return;
	}

	var params = {};
	
	params.rtu_id 	= mygridCons.cells2(rowparams[index], gridColumn.rtuId).getValue();
	params.mp_id  	= mygridCons.cells2(rowparams[index], gridColumn.mpId).getValue();
	params.paytype 	= mygridCons.cells2(rowparams[index], gridColumn.pay_type).getValue();
	
	mp_id[0] = params.mp_id;
	mp_id[1] = mygridCons.cells2(rowparams[index], gridColumn.power_rela1).getValue();
	mp_id[2] = mygridCons.cells2(rowparams[index], gridColumn.power_rela2).getValue();

	//20121128
	if (feectrl_type == SDDef.YFF_FEECTRL_TYPE_RTU) {
		params.buynum 	= mygridCons.cells2(rowparams[index], gridColumn.buy_times).getValue();
	}
	else {
		params.buynum 	= parseInt(mygridCons.cells2(rowparams[index], gridColumn.buy_times).getValue()) + 1;
	}
	
	params.alarm_val1	= mygridCons.cells2(rowparams[index], gridColumn.alarm1).getValue();
	params.alarm_val2	= mygridCons.cells2(rowparams[index], gridColumn.alarm2).getValue();
	
	params.pay_money 	= 0;//不能缴费，这里定为0
	params.othjs_money 	= mygridCons.cells2(rowparams[index], gridColumn.othjs_money).getValue() === "" ? 0 : mygridCons.cells2(rowparams[index], gridColumn.othjs_money).getValue();
	params.jsflag = 0;
	
	if(mis_jsflag) {
		params.othjs_money = mis_jsje;
		params.misjs_money = mygridCons.cells2(rowparams[index], gridColumn.othjs_money).getValue() === "" ? 0 : mygridCons.cells2(rowparams[index], gridColumn.othjs_money).getValue();
		params.jsflag = 1;
	}
	
	params.zb_money 	= mygridCons.cells2(rowparams[index], gridColumn.zb_money).getValue() === "" ? 0 : mygridCons.cells2(rowparams[index], gridColumn.zb_money).getValue();
	params.all_money    = round(parseFloat(params.zb_money) + parseFloat(params.pay_money) + parseFloat(params.othjs_money),def.point);
	
	var t_str 		= $("#cb_ym").val();
	var str_cb_ym 	= t_str.substring(0,4) + t_str.substring(5,7); 
	var cb_ymd    	= str_cb_ym + jsDate;
	
	params.fxdf_ym 	= str_cb_ym;
	params.res_id 	= mygridCons.cells2(rowparams[index], gridColumn.res_id).getValue();
	params.res_desc = mygridCons.cells2(rowparams[index], gridColumn.consName).getValue();
	params.pay_type = mygridCons.cells2(rowparams[index], gridColumn.pay_type).getValue();
	
	params.lastala_val1 = mygridCons.cells2(rowparams[index], gridColumn.alarm1).getValue();
	params.lastala_val2 = mygridCons.cells2(rowparams[index], gridColumn.alarm2).getValue();
	params.lastshut_val = mygridCons.cells2(rowparams[index], gridColumn.shutdown_val).getValue();
	
	params.newala_val1 = mygridCons.cells2(rowparams[index], gridColumn.alarm1).getValue();
	params.newala_val2 = mygridCons.cells2(rowparams[index], gridColumn.alarm2).getValue();
	params.newshut_val = parseFloat(mygridCons.cells2(rowparams[index], gridColumn.shutdown_val).getValue()) + parseFloat(params.all_money);
	
	params.date = cb_ymd;
	params.time = jsTime;

	for(var j = 0; j < 3; j++){
		eval("params.mp_id" + j + "=mp_id[" + j + "]");
		eval("params.bd_zy"  + j + "=bdyg[" + j + "]");
		eval("params.bd_zyj" + j + "=0");
		eval("params.bd_zyf" + j + "=0");
		eval("params.bd_zyp" + j + "=0");
		eval("params.bd_zyg" + j + "=0");
	}
	var json_str = jsonString.json2String(params);
	
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			userData1		: params.rtu_id,
			mpid 			: params.mp_id,
			dyOperJsbc 		: json_str,
			userData2 		: ComntUseropDef.YFF_DYOPER_JSBC
		},
		function(data) {			    			//回传函数
			//var index = parseInt(data.gsmflag);     //行索引号
			if (data.result == "success") {
				mygridCons.cells2(rowparams[index], gridColumn.result).setValue("结算缴费成功!");//给结果赋值
				setRowDisableByIndex(rowparams[index]);		//把结算过的灰掉,剩余可结算但没选择的项
			}
			else {
				mygridCons.cells2(rowparams[index], gridColumn.result).setValue("<font color='red'>向主站发送命令失败!</font>");//给结果赋值
			}
			payMoney_child(index+1,rowparams)
		}
	);
}
