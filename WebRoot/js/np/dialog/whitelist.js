var retValue = null;
var glo_optype = null;
var glo_finishflag 			= false;
var userop_cancel_flag  	= false;//取消任务标志
var userop_use_flag 		= false;
var userop_whitelist_flag 	= false;
var userop_addordel_flag  	= false;
var userop_remove_flag    	= false;
var mygrid = null;
var farmer_inf = null;
var mygrid1 = null;


var isused_data_idx = 0;	//召测\设置白名单起停标志
var whitelist = 1;			//召测白名单
var addordel = 1;			//设置添加\删除白名单
var whiteremove = 2;		//设置删除白名单

var meter_area = null;
var meter_pwd = '000000';   //定死了

$(document).ready(function(){
	if(window.top.child_page.retValue){
		retValue = window.top.child_page.retValue;//父页面的参数
		meter_area = retValue.area_code;
		getFarmInfo();
	}
	initPage();
});

function initPage(){
	initgrid();
	initgrid2();
	initDiction();
}

function initgrid(){
	mygrid = new dhtmlXGridObject('gridbox1');
	var gridHeader           = "序号,客户名称,卡号,设置时间";
	var gridColAlign         = "center,left,left,left";
	var gridColTypes         = "ro,ro,ro,ro";
	mygrid.setImagePath(def.basePath + "images/grid/imgs/");
	mygrid.setHeader(gridHeader);
	mygrid.setInitWidthsP("12,26,26,*");
	mygrid.setColAlign(gridColAlign);
	mygrid.setColTypes(gridColTypes);
	mygrid.init();
}

function initgrid2(){
	mygrid1 = new dhtmlXGridObject('gridbox2');
	var gridHeader           = "序号,所属片区,客户编号,客户名称,卡号";
	var gridColAlign         = "center,left,left,left,left";
	var gridColTypes         = "ro,ro,ro,ro,ro";
	mygrid1.setImagePath(def.basePath + "images/grid/imgs/");
	mygrid1.setHeader(gridHeader);
	mygrid1.setInitWidthsP("5,15,15,15,*");
	mygrid1.setColAlign(gridColAlign);
	mygrid1.setColTypes(gridColTypes);
	mygrid1.attachEvent("onRowSelect",fillCardNo);
	mygrid1.init();
	if(retValue){
	var param = retValue.rtu_id + "," + retValue.mp_id;
	$.post(def.basePath + "ajaxnp/actConsPara!getWhiteList.action",{result : param}, function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			mygrid1.parse(json, "", "json");
			mygrid1.selectRow(0,true);//默认选中第一行
		}
	})
	}
}

function initDiction(){
	var text = '{item:["' + Dict.DICTITEM_STARTUPFLAG + '"]}';
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : text}, function(data){
		var json = eval('(' + data.result + ')');
		var iid = 0;
		var dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_STARTUPFLAG);
		for ( var i = 0; i < dict.length; i++) {
			$("#isUsed").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
	});
}

//召测白名单使用标志    callFlag暂时赋值，还需要定义
function taskCallwhite_use_flag(data_idx){
	enableAll(true);
	userop_use_flag = true;
	window.top.addStringTaskOpDetail("任务执行开始:召测白名单启停标志");
	$("#loading_useflag").html("<img src='../../../images/indicator.gif' />");
	$.post(def.basePath + "ajaxoper/actCommNp!taskProc.action", {
		userData1 			: retValue.rtu_id,
		userData2 			: ComntUseropDef.YFF_NPOPER_WL_PARA,
		callFlag	    	: 'true',
		mpid 				: retValue.mp_id,
		npCommCallWlPara 	: '{data_idx : ' + data_idx + ',datatype:' + ComntProtMsg.YFF_CALL_DL645_CALL_WL_FLAG + '}'
	}, function(data){
		if(data.result == "success") {
			$("#loading_useflag").html("<img src='../../../images/tick.gif' />");
			var json = eval("(" + data.npCommCallWlPara + ")");
			$("#isUsed").val(json.useflag);
		}
		else {
			$("#loading_useflag").html("<img src='../../../images/cross.gif' />");
		}
		userop_use_flag = false;
		window.top.addJsonOpDetail(data.detailInfo);
		enableAll(false);		
		if (!userop_cancel_flag) {	
			if($("#chkrecord").attr("checked")){
			    taskCallwhite_list(whitelist,0);
			}
		}
		//loading.loaded();
	});
}


//先查询20条
var glo_sel_group = new Array();
var glo_grid_id = 0;
//召测当前白名单记录
function taskCallwhite_list(data_idx,idx){
	//如果超过记录条数，跳出
	if(idx >= glo_sel_group.length) {
		loading.loaded();
		return;
	}
	enableAll(true);
	userop_whitelist_flag = true;
	window.top.addStringTaskOpDetail("任务执行开始:召测当前白名单记录");
	loading.loading();
	$("#loading_record").html("<img src='../../../images/indicator.gif' />");
	$.post(def.basePath + "ajaxoper/actCommNp!taskProc.action", {
		userData1 			: retValue.rtu_id,
		userData2 			: ComntUseropDef.YFF_NPOPER_WL_PARA,
		callFlag	    	: 'true',
		mpid 				: retValue.mp_id,
		npCommCallWlPara 	: '{data_idx : ' + data_idx + ',datatype:' + glo_sel_group[idx] + '}'
	}, function(data){
		if(data.result == "success") {
			$("#loading_record").html("<img src='../../../images/tick.gif' />");
			var json = eval("(" + data.npCommCallWlPara + ")");
			var griddata = "," + getDescByNo(json.kh) + "," + json.kh + "," + (json.opymd + json.ophms);
			mygrid.addRow(glo_grid_id++, (glo_grid_id) + griddata);
			if(!glo_finishflag){
			window.setTimeout("taskCallwhite_list(" + data_idx + "," + (idx + 1)+ ")", 500);
			}else{
				glo_grid_id = 0;
				loading.loaded();
			}
		}
		else {
			$("#loading_record").html("<img src='../../../images/cross.gif' />");
			mygrid.addRow(glo_grid_id++, (glo_grid_id) + ",--,--,--");
			//中途取消召测
			if(!glo_finishflag){
				window.setTimeout("taskCallwhite_list(" + data_idx + "," + (idx + 1)+ ")", 1000);
			}else{
				glo_grid_id = 0;
				loading.loaded();
			}
		}
		enableAll(false);
		if(idx >= glo_sel_group.length) {
			loading.loaded();
		}
		window.top.addJsonOpDetail(data.detailInfo);
		userop_whitelist_flag = false;
	});
}


//设置白名单使用标志
function taskSetwhite_use_flag(data_idx){
	enableAll(true);
	var userop_use_flag = true;
	window.top.addStringTaskOpDetail("任务执行开始:设置白名单启停标志");
	$("#loading_useflag").html("<img src='../../../images/indicator.gif' />");
	$.post(def.basePath + "ajaxoper/actCommNp!taskProc.action", {
		userData1 			: retValue.rtu_id,
		userData2 			: ComntUseropDef.YFF_NPOPER_WL_PARA,
		callFlag	    	: 'false',
		mpid 				: retValue.mp_id,
		npCommSetWlPara 	: '{data_idx : "' + data_idx + '",useflag:"' + $("#isUsed").val() + '", meter_area : "' + meter_area + '", meter_pwd : "' + meter_pwd + '"}'
	}, function(data){
		if(data.result == "success") {
			$("#loading_useflag").html("<img src='../../../images/tick.gif' />");
			var json = eval("(" + data.npCommCallBase + ")");
		}
		else {
			$("#loading_useflag").html("<img src='../../../images/cross.gif' />");
		}
		enableAll(false);
		window.top.addJsonOpDetail(data.detailInfo);
		userop_whitelist_flag = false;
		
		if (!userop_cancel_flag) {			
			if($("#chkaddordel").attr("checked")){
				taskSetwhite_addordel(addordel);
			}else if($("#chkremove").attr("checked")){
				 taskSetwhite_remove(whiteremove);
			}
		}
	});
}

//设置添加或删除白名单
function taskSetwhite_addordel(data_idx){
	enableAll(true);
	var userop_addordel_flag = true;
	window.top.addStringTaskOpDetail("任务执行开始:设置白名单添加或删除");
	$("#loading_addordel").html("<img src='../../../images/indicator.gif' />");
	$.post(def.basePath + "ajaxoper/actCommNp!taskProc.action", {
		userData1 			: retValue.rtu_id,
		userData2 			: ComntUseropDef.YFF_NPOPER_WL_PARA,
		callFlag	    	: 'false',
		mpid 				: retValue.mp_id,
		npCommSetWlPara 	: '{data_idx : "' + data_idx + '",card_no:"' + $("#cardNo").val() + '", meter_area : "' + meter_area + '", meter_pwd : "' + meter_pwd + '",addflag :"' + $("#addordel").val() + '"}'
	}, function(data){
		if(data.result == "success") {
			$("#loading_addordel").html("<img src='../../../images/tick.gif' />");
			var json = eval("(" + data.npCommCallBase + ")");
		}
		else {
			$("#loading_addordel").html("<img src='../../../images/cross.gif' />");
		}
		enableAll(false);
		window.top.addJsonOpDetail(data.detailInfo);
		userop_addordel_flag = false;
		
		if (!userop_cancel_flag) {			
			if($("#chkremove").attr("checked")){
				 taskSetwhite_remove(whiteremove);
			}
		}
	});
	
}

//设置移除白名单
function taskSetwhite_remove(data_idx){
	enableAll(true);
	var userop_remove_flag = true;
	window.top.addStringTaskOpDetail("任务执行开始:设置移除白名单");	
	$("#loading_remove").html("<img src='../../../images/indicator.gif' />");
	$.post(def.basePath + "ajaxoper/actCommNp!taskProc.action", {
		userData1 			: retValue.rtu_id,
		userData2 			: ComntUseropDef.YFF_NPOPER_WL_PARA,
		callFlag	    	: 'false',
		mpid 				: retValue.mp_id,
		npCommSetWlPara 	: '{data_idx : "' + data_idx + '", meter_area : "' + meter_area + '", meter_pwd : "' + meter_pwd + '"}'
	}, function(data){
		if(data.result == "success") {
			$("#loading_remove").html("<img src='../../../images/tick.gif' />");
			var json = eval("(" + data.npCommCallBase + ")");
		}
		else {
			$("#loading_remove").html("<img src='../../../images/cross.gif' />");
		}
		//loading.loaded();
		enableAll(false);
		window.top.addJsonOpDetail(data.detailInfo);
		userop_remove_flag = false;
	});
}


//在线召测
function taskCall(){

	if(!retValue){
		alert("请选择电表!");
		return;
	}
	
	if (isUserOping()) {
		alert("正在操作,请耐心等待...");
		return;		
	}
	
	userop_cancel_flag = false;	
	glo_finishflag = false;
	glo_optype = ComntUseropDef.YFF_NPOPER_WL_PARA;
	glo_grid_id = 0;
	var call_num = 0;
	if($("#chkuseflag").attr("checked")){
		call_num++;
		taskCallwhite_use_flag(isused_data_idx);
	}
	
	if($("#chkrecord").attr("checked")){
		if(mygrid) mygrid.clearAll();
		//设置查询条数---------------------临时定死
		for(var i = 0; i < 3; i++) {
			glo_sel_group[i] = ComntProtMsg.YFF_CALL_DL645_CALL_WL_FIR + i;
		}
		if(call_num == 0){
		 taskCallwhite_list(whitelist,0);
		}
		call_num++;
	}
	if(call_num == 0){
		alert("请选择要召测的项！");
	}
}

//在线设置
function taskSet(){
	if(!retValue){
		alert("请选择电表!");
		return;
	}
	
	if (isUserOping()) {
		alert("正在操作,请耐心等待...");
		return;		
	}
	
	userop_cancel_flag = false;	
	glo_finishflag = false;
	glo_optype = ComntUseropDef.YFF_NPOPER_WL_PARA;
	var call_num = 0;
	if($("#chkuseflag").attr("checked")){
		call_num++;
		taskSetwhite_use_flag(isused_data_idx);
	}
	
	if($("#chkaddordel").attr("checked")){
		if(call_num == 0){
		 taskSetwhite_addordel(addordel);
		}
		call_num++;
	}
	
	if($("#chkremove").attr("checked")){
		if(call_num == 0){
		 taskSetwhite_remove(whiteremove);
		}
		call_num++;
	}
	
	if(call_num == 0){
		alert("请选择要设置的项！");
	}
}


function taskCancel() {
	if (glo_optype == null) return;
	userop_cancel_flag = true;
	glo_finishflag = true;
	$.post(def.basePath + "ajaxoper/actCommNp!cancelTask.action", {
		userData1 : retValue.rtu_id,
		userData2 : glo_optype
	});
}

function isUserOping() {
	if (userop_cancel_flag) return false;
	
	if (userop_use_flag || userop_whitelist_flag || userop_addordel_flag ||
			userop_remove_flag) return true;
	else return false;
}


//点击选择框时，对应样式改变
function enableAll(flag){
	$("#chkuseflag").attr('disabled',flag);
	$("#chkrecord").attr('disabled',flag);
	$("#chkaddordel").attr('disabled',flag);
	$("#chkremove").attr('disabled',flag);
}

function checkUsed(flag){
	if(flag){
		$("#isUsed").attr('disabled',!flag);
		$("#isUsed").css('background','');
	}
	else{
		$("#isUsed").attr('disabled',!flag);
		$("#isUsed").css('background','#eee');
	}
}

function checkAddorDel(flag){
	if(flag){
		$("#addordel").attr('disabled',!flag);
		$("#addordel").css('background','');
		$("#cardNo").attr('disabled',!flag);
		$("#cardNo").css('background-color','');
	}
	else{
		$("#addordel").attr('disabled',!flag);
		$("#addordel").css('background','#eee');
		$("#cardNo").attr('disabled',!flag);
		$("#cardNo").css('background-color','#eee');
	}
}

function getDescByNo(card_no) {
	for(var i = 0; i < farmer_inf.rows.length; i++) {
		if(card_no == farmer_inf.rows[i].card_no) {
			return farmer_inf.rows[i].farmer_desc;
		}
	}
	return "--";
}

function getFarmInfo() {
	var rtu_id = retValue.rtu_id;
	if(!rtu_id) return;
	$.post(def.basePath + "ajaxoper/actCommNp!retFarmInfo.action", {result : rtu_id}, function(data) {
		if(data.result != "") {
			farmer_inf = eval("("+data.result+")");
		}
	});
}
function fillCardNo(){
	var i = mygrid1.getRowIndex(mygrid1.getSelectedRowId());
	var cardNo = mygrid1.cells2(i, 4).getValue();
	if(cardNo){
		$("#cardNo").val(cardNo);
	}
}
