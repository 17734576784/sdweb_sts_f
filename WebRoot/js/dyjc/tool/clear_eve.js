var rtnValue;
//所选事项id组成的数组
var events = [];
//所选择事项总数
var eve_num = 0;
var checks = ["img_event1","img_event2","img_event3"];

$(document).ready(function() {
	$("#btnSearch").click(function(){selcons()});
	$("#clearEvent").click(function(){
		//清空结果信息
		clearResult();
		//获取选择事项，赋值给events
		getSelValue();
		eve_num = events.length;
		//当事项为多个时，从0开始循环清空
		clear(0);
	});
	
	$("#clearXL").click(function(){
		clearXLDL(0);
	});
	
	$("#clearDL").click(function(){
		clearXLDL(1);
	});
	
	initgrid_DY();
	initgrid_DL();
	initgrid_Other();
	
	//页面刚加载时禁用所有选择框，点击查询后启用
	disableAllchk(checks,true);
});

function selcons(){//检索
	clearPage();
	var rtnValue1 = doSearch("",ComntUseropDef.YFF_DYCOMM_CTRL,rtnValue);
	if(!rtnValue1){
		return;
	}	
	rtnValue = rtnValue1;
	
	var meterType = rtnValue.yffmeter_type;
	   
	//根据电表类型判断是否支持该规约
	if(!is2013Meter(meterType)){
		alert("此电表不支持清空电量、需量、事项操作！");
		return;
	}
	
	setConsValue(rtnValue);

    //显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);

	setDisabled(false);
	
	//启用所有复选框
	disableAllchk(checks,false);
}

//清空需量flag=0、电量flag=1
function clearXLDL(flag){
	var params = {};
	params.ctrlver = 0;					//控制版本 	现在传固定值0 
	params.clear_eve_type = 0;			//清除数据的数据类型,电量需量时为0 
	
	if(flag == 0){
		params.paratype = ComntProtMsg.YFF_DY_CLEAR_XL;	//参数类型:清需量
	}else{
		params.paratype = ComntProtMsg.YFF_DY_CLEAR_DL;	//参数类型:清电量
	}
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	
	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommDy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_DYCOMM_CLEAR;
	//end
	
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
			{
				firstLastFlag : true,
				userData1     : rtnValue.rtu_id,
				mpid          : rtnValue.mp_id,
				dyCommClear   : json_str,
				userData2     : ComntUseropDef.YFF_DYCOMM_CLEAR
			},
			function(data){
				loading.loaded();
				window.top.addJsonOpDetail(data.detailInfo);
				if (data.result == "success") {
					window.top.addStringTaskOpDetail("接收预付费服务任务成功...");
					//向历史库写数据
					addBD(params.paratype*1);
				}else{
					alert("操作失败!");
				}
			});
}


//清空事项操作
function clear(events_num){
	if(eve_num == 0){
		alert("请选择要清空的项！");
		return;
	}
	
	if(events_num >= eve_num){
		loading.loaded();
		//清空全局变量
		events = [];
		return;
	}

	var params = {};
	params.ctrlver = 0;									//控制版本 	现在传固定值0
	params.paratype = ComntProtMsg.YFF_DY_CLEAR_EVENT;	//参数类型:清事项 
	params.clear_eve_type = events[events_num];			//清除数据的数据类型
	
	var json_str = jsonString.json2String(params);
	
	loading.loading();
	
	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommDy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_DYCOMM_CLEAR;
	//end
	
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
		
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
			{
				firstLastFlag : true,
				userData1     : rtnValue.rtu_id,
				mpid          : rtnValue.mp_id,
				dyCommClear   : json_str,
				userData2     : ComntUseropDef.YFF_DYCOMM_CLEAR
			},
			function(data){
				window.top.addJsonOpDetail(data.detailInfo);
				if (data.result == "success") {
					window.top.addStringTaskOpDetail("接收预付费服务任务成功...");
					//向页面返回操作是否成功标识
					showResult(events[events_num],4,"√");
					//向历史库写数据
					addBD(params.paratype*1,events[events_num],4);
				}else{
					showResult(events[events_num],4,"X");
				}
				loading.loaded();
				clear(++events_num);
			}
		);
}

//往历史数据库里写数据
function addBD(value,tableRow_index,column_index){		//添加数据库
	var params = {};
	params.rtu_id	= rtnValue.rtu_id;
	params.mp_id	= rtnValue.mp_id;
	params.op_type	= value;
	var json_str 	= jsonString.json2String(params);
	$.post( def.basePath + "ajaxoper/actOperDy!dbCtrlOper.action", 		//后台处理程序
		{
			result : json_str
		},
		function(data) {			    	//回传函数
			if (data.result == "success") {
				if(value == ComntProtMsg.YFF_DY_CLEAR_XL || value == ComntProtMsg.YFF_DY_CLEAR_DL){
					alert("操作成功!");
				}
				else if(value == ComntProtMsg.YFF_DY_CLEAR_EVENT){
					//向页面返回操作是否成功标识
					showResult(tableRow_index,5,"√");
				}else{
					return;
				}	                    	
			}
			else {
				if(value == ComntProtMsg.YFF_DY_CLEAR_XL || value == ComntProtMsg.YFF_DY_CLEAR_DL){
					alert("操作失败!");
				}
				else if(value == ComntProtMsg.YFF_DY_CLEAR_EVENT){
					showResult(tableRow_index,5,"X");
				}
				else{
					return;
				}
			}
		}
	);
}

//确定设置信息的位置，用来填充返回信息
//返回值'3_val':3代表数据来源表格。val为所选事项的id,(val-*)为id所在行的行号 。
//三个表中事项编号要对应正确。1\24\42分别为起始id
function getGridRowId(val){
	if(val >= 42){
		return '3_' + (val - 42);
	}else if(val >= 24){
		return '2_' + (val - 24);
	}else if(val >=1){
		return '1_' + (val-1);
	}
}

//向结果、存库单元格中填充信息
function showResult(val,column,flag){
	var temp = getGridRowId(val);
	
	//获取返回信息所要填充的行号
	var row_index = temp.split('_')[1];
	//获取返回信息所要填充的表格号
	var table_index = temp.split('_')[0];
	
	if(table_index == 1){
		mygridbox1.cells2(row_index,column).setValue("<font color=red>"+ flag + "</font>");
	}
	else if(table_index == 2){
		mygridbox2.cells2(row_index,column).setValue("<font color=red>"+ flag + "</font>");
	}
	else if(table_index == 3){
		mygridbox3.cells2(row_index,column).setValue("<font color=red>"+ flag + "</font>");
	}
	else{
		return;
	}
}

//获取选中事项的id值
//数据格式:'1,2,3,4'
function getSelValue(){
	getCellValue(mygridbox1);
	getCellValue(mygridbox2);
	getCellValue(mygridbox3);
}

//获取每个表格中选中事项的id,添加到全局数组变量events中
function getCellValue(grid){
	var num = grid.getRowsNum();
	if(num < 0) return;
	for(var i=0; i<num; i++){
		var cell = grid.cells2(i,0).getValue();
		if( cell == 0)	continue;
		events.push(grid.cells2(i,3).getValue());	
	}
}

//初始化电压事项列表
function initgrid_DY(){
	mygridbox1.setImagePath(def.basePath + "images/grid/imgs/");
	mygridbox1.setHeader("<img src='"+ def.basePath + "images/grid/imgs/item_chk0_dis.gif' id='img_event1' iid='1' onclick='selectAllOrNone(this);'/>,序号,电压事项,id,结果,存库");
	mygridbox1.setInitWidthsP("7,10,60,0,13,*");
	mygridbox1.setColAlign("center,center,center,center,center,center");
	mygridbox1.setColTypes("ch,ro,ro,ro,ro,ro");
	mygridbox1.enableTooltips("false,false,false,false,false,false");
	mygridbox1.init();
	mygridbox1.setColumnHidden(3,true);

	//事项描述
	var events_dy = ['失压总清','A相失压记录清零','B相失压记录清零','C相失压记录清零',
	                 '欠压总清','A相欠压记录清零','B相欠压记录清零','C相欠压记录清零',
	                 '过压总清','A相过压记录清零','B相过压记录清零','C相过压记录清零',
	                 '断相总清','A相断相记录清零','B相断相记录清零','C相断相记录清零',
	                 '总电压合格率记录清零','A相电压合格率记录清零','B相电压合格率记录清零','C相电压合格率记录清零',
	                 '电压逆相序记录清零','电压不平衡记录清零'];
	//事项对应id,从1开始
	var events_id=[];
	var num = events_dy.length;
	for(var i=1; i<=num; i++){
		events_id.push(i);
	}
	
	//将事项内容填充至表格
	for(var i=0; i<num; i++){
		mygridbox1.addRow(('event'+i) ,"");
		mygridbox1.cells2(i,2).setValue(events_dy[i]);
		mygridbox1.cells2(i,3).setValue(events_id[i]);
	}
	
	mygridbox1.selectRow(0,true);	//默认选中第一行
}

//初始化电流事项列表
function initgrid_DL(){
	mygridbox2.setImagePath(def.basePath + "images/grid/imgs/");
	mygridbox2.setHeader("<img src='"+ def.basePath + "images/grid/imgs/item_chk0_dis.gif' id='img_event2' iid='2' onclick='selectAllOrNone(this);'/>,序号,电流事项,id,结果,存库");
	mygridbox2.setInitWidthsP("7,10,60,0,13,*");
	mygridbox2.setColAlign("center,center,center,center,center,center");
	mygridbox2.setColTypes("ch,ro,ro,ro,ro,ro");
	mygridbox2.enableTooltips("false,false,false,false,false,false");
	//mygridbox2.setSkin("xp");
	mygridbox2.init();
	mygridbox2.setColumnHidden(3,true);
	
	//事项描述
	var events_dy = ['失流总清','A相失流记录清零','B相失流记录清零','C相失流记录清零',
	                 '过流总清','A相过流记录清零','B相过流记录清零','C相过流记录清零',
	                 '断流总清','A相断流记录清零','B相断流记录清零','C相断流记录清零',
	                 '潮流反向总清','A相潮流反向记录清零','B相潮流反向记录清零','C相潮流反向记录清零',
	                 '电流逆相序记录清零','电流不平衡记录清零'];
	//事项对应id,从24开始,前23项为电压事项
	var events_id = [];
	var num = events_dy.length;
	for(var i=1; i<=num; i++){
		events_id.push(i+23);
	}
	
	//将事项内容填充至表格
	for(var i=0; i<num; i++){
		mygridbox2.addRow(('event'+i) ,"");
		mygridbox2.cells2(i,2).setValue(events_dy[i]);
		mygridbox2.cells2(i,3).setValue(events_id[i]);
	}
	
	mygridbox2.selectRow(0,true);//默认选中第一行
}

//初始化其他事项列表
function initgrid_Other(){
	mygridbox3.setImagePath(def.basePath + "images/grid/imgs/");
	mygridbox3.setHeader("<img src='"+ def.basePath + "images/grid/imgs/item_chk0_dis.gif' id='img_event3' iid='3' onclick='selectAllOrNone(this);'/>,序号,其他事项,id,结果,存库");
	mygridbox3.setInitWidthsP("7,10,60,0,13,*");
	mygridbox3.setColAlign("center,center,center,center,center,center");
	mygridbox3.setColTypes("ch,ro,ro,ro,ro,ro");
	mygridbox3.enableTooltips("false,false,false,false,false,false");
	mygridbox3.init();
	mygridbox3.setColumnHidden(3,true);
	
	//事项描述
	var events_dy = ['过载总清','A相过载反向记录清零','B相过载反向记录清零','C相过载反向记录清零',
	                 '跳闸记录清零','合闸记录清零','总功率因数超下限清零','辅助电源失电记录清零',
	                 '掉电记录清零','编程记录清零','需量记录清零','校时记录清零',
	                 '时段表编程记录清零','时区表编程记录清零','周休日编程记录清零','节假日编程记录清零',
	                 '有功组合方式编程记录清零','无功组合方式1编程记录清零','无功组合方式2编程记录清零','结算日编程记录清零',
	                 '开表盖编程记录清零','开端钮盒编程记录清零'];
	//事项对应id,从42开始
	var events_id = [];
	var num = events_dy.length;
	for(var i=1; i<=num; i++){
		events_id.push(i+41);
	}
	
	//将事项内容填充至表格
	for(var i=0; i<num; i++){
		mygridbox3.addRow(('event'+i) ,"");
		mygridbox3.cells2(i,2).setValue(events_dy[i]);
		mygridbox3.cells2(i,3).setValue(events_id[i]);
	}
	
	mygridbox3.selectRow(0,true);	//默认选中第一行
}

//全选和全不选
//此处checked为js中直接取得节点
function selectAllOrNone(checked){
	if(checked.src.indexOf("_dis.gif")>=0)return;
	var flag = false;
	if (checked.src.indexOf("item_chk0.gif") != -1) {
		flag = true;
		checked.src = def.basePath + 'images/grid/imgs/item_chk1.gif';
	} else if (checked.src.indexOf("item_chk1.gif") != -1) {
		flag = false;
		checked.src = def.basePath +  'images/grid/imgs/item_chk0.gif';
	}
	
	if(checked.iid == 1){	
		setChkState(mygridbox1,flag);
	}
	else if (checked.iid == 2) {
		setChkState(mygridbox2,flag);
	}
	else if(checked.iid == 3){
		setChkState(mygridbox3,flag);
	}
}


//禁用/启用选择框
function setDisabled(flag){
	$("#clearEvent").attr("disabled", flag);
	$("#clearXL").attr("disabled", flag);
	$("#clearDL").attr("disabled", flag);
}

//禁用、启用所有选择框
//obj:grid表格
//checkeds:grid表格总选择框
function disableAllchk(checkeds,flag){
	for(var i=0,num = checkeds.length; i<num; i++){
		if(flag){
			$("#" + checkeds[i])[0].src = def.basePath + 'images/grid/imgs/item_chk0_dis.gif';
		}else{
			$("#" + checkeds[i])[0].src = def.basePath + 'images/grid/imgs/item_chk0.gif';
		}
	}
	disableChk(mygridbox1,flag);
	disableChk(mygridbox2,flag);
	disableChk(mygridbox3,flag);
}

//禁用、启用obj表的所有复选框
function disableChk(obj,flag){
	for ( var i = 0; i < obj.getRowsNum(); i++) {
		obj.cells2(i, 0).setDisabled(flag);
	}
}


//清空表格中事项返回结果
function clearResult(){
	clearCellsResult(mygridbox1);
	clearCellsResult(mygridbox2);
	clearCellsResult(mygridbox3);
}

function clearCellsResult(obj){
	for(var i=0,num = obj.getRowsNum(); i<num; i++){
		obj.cells2(i,4).setValue("");
		obj.cells2(i,5).setValue("");
	}
}

//清空所有打钩选项
function clearAllChk(){
	setChkState(mygridbox1, false);
	setChkState(mygridbox2, false);
	setChkState(mygridbox3, false);
}

//选择框打钩或为空
function setChkState(obj, flag){
	for ( var i = 0, nums = obj.getRowsNum(); i < nums; i++) {
		obj.cells2(i, 0).setValue(flag)
	}
}

//清空页面内表格信息
function clearPage(){
	clearResult();
	clearAllChk();
}






