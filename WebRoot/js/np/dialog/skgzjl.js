var json_data = null;
var farmer_inf = null;
var mygrid = null;
var cancel_flag = false;
var retValue = null;

$(document).ready(function(){
	initGrid();
	//getDataByidFromTree();
	if(window.top.child_page.retValue){
		retValue = window.top.child_page.retValue;//父页面的参数
		getFarmInfo();
	}
});


function initGrid() {
	mygrid = new dhtmlXGridObject('gridbox');
	
	var gridHeader           = "序号,客户名称,卡号,故障时间,故障原因,加锁余额,解锁金额,交易金额,表内剩余金额";
	var gridColAlign         = "center,left,left,left,left,left,left,left,left";
	var gridColTypes         = "ro,ro,ro,ro,ro,ro,ro,ro,ro";
	var gridWidths           = "50,120,120,180,240,120,120,120,120";
	
	mygrid.setImagePath(def.basePath + "images/grid/imgs/");
	mygrid.setHeader(gridHeader);
	mygrid.setInitWidths(gridWidths);
	mygrid.setColAlign(gridColAlign);
	mygrid.setColTypes(gridColTypes);
	
	mygrid.init();
	
	parent.$("#vfreeze").val(1);
	parent.$("#hfreeze").val(0);
	parent.$("#header").val(encodeURI(gridHeader));
	parent.$("#colType").val("int,str,str,str,str,0.000,0.000,0.000,0.000");
	parent.$("#filename").val(encodeURI("刷卡故障记录"));
	
}
//
//function initGrid() {
//	mygrid = new dhtmlXGridObject('gridbox');
//	
//	var gridHeader           = "序号,客户名称,卡号,故障时间,故障原因";
//	var gridColAlign         = "center,left,left,left,left";
//	var gridColTypes         = "ro,ro,ro,ro,ro";
//	var gridWidths           = "50,120,120,180,240";
//	
//	mygrid.setImagePath(def.basePath + "images/grid/imgs/");
//	mygrid.setHeader(gridHeader);
//	mygrid.setInitWidths(gridWidths);
//	mygrid.setColAlign(gridColAlign);
//	mygrid.setColTypes(gridColTypes);
//	
//	mygrid.init();
//	
//	parent.$("#vfreeze").val(1);
//	parent.$("#hfreeze").val(0);
//	parent.$("#header").val(encodeURI(gridHeader));
//	parent.$("#colType").val("int,str,str,str,str,0.000,str");
//	parent.$("#filename").val(encodeURI("刷卡故障记录"));
//	
//}

function writeDB(){
	alert("不支持存库功能！");
}
var glo_sel_group = new Array();
var glo_grid_id = 0;

function taskCall() {
	glo_grid_id = 0;
	if (retValue == null) return;				//zkz
//	alert(retValue.yffmeter_type );
	if (retValue.yffmeter_type  == SDDef.YFF_NP_CARDTYPE_HNTY) {		//20 new  zkz temp
		for(var i = 0; i < 3; i++) {
				glo_sel_group[i] = ComntProtMsg.YFF_CALL_HNQF_GZJL_FIR + i;
		}
	}
	else {
		for(var i = 0; i < 20; i++) {
			glo_sel_group[i] = ComntProtMsg.YFF_CALL_DL645_GZJL_FIR + i;
		}
	}
	
	mygrid.clearAll();
	cancel_flag = false;
	
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	call_skgzjl(0);
}

function call_skgzjl(idx) {
	
	if(idx >= glo_sel_group.length) {
		loading.loaded();
		return;
	}
	//var rtu_id = parent.tree_id.split("_")[1];
	var rtu_id = retValue.rtu_id;
	var mpid   = retValue.mp_id;
	
	var npCommReadData = '{ymd:'+_ymd+', datatype : '+glo_sel_group[idx]+'}';
	
	$.post(def.basePath + "ajaxoper/actCommNp!taskProc.action", 
		{
			//firstLastFlag 	: true,
			userData1 		: rtu_id,
			userData2 		: ComntUseropDef.YFF_READDATA,
			mpid 			: mpid,
			npCommReadData 	: npCommReadData
		},
		function(data) {
			window.top.addJsonOpDetail(data.detailInfo);
			if(data.result == "success") {
				var json = eval("(" + data.npCommReadData + ")");
				if (retValue.yffmeter_type  == SDDef.YFF_NP_CARDTYPE_HNTY) {		//20 new  zkz temp	
					var data_array = json.data;
					var nums = data_array.length;
					var data_per = '';
					for(var i=0; i<nums; i++){
						data_per = data_array[i];

						var griddata = "," + getDescByNo(data_per.kh) + "," + data_per.kh + "," + data_per.jyrq + ",--," + data_per.jsye + "," + data_per.jsje + "," + data_per.jyje + "," + data_per.syje;
						mygrid.addRow(glo_grid_id++, (glo_grid_id) + griddata);
					}
				}
				else {
					var griddata = "," + getDescByNo(json.kh) + "," + json.kh + "," + json.bgtime + "," + json.cause;
					mygrid.addRow(glo_grid_id++, (glo_grid_id) + griddata);
				}
				window.setTimeout("call_skgzjl(" + (idx + 1)+ ")", 500);
			}
			else {
				if (retValue.yffmeter_type  == SDDef.YFF_NP_CARDTYPE_HNTY) {		//20 new  zkz temp
					for (var i = 0; i < 10; i++) {
						mygrid.addRow(glo_grid_id++, (glo_grid_id) + ",--,--,--,--,0.00,0.00,0.00,0.00");
					}
				}
				else {
					mygrid.addRow(glo_grid_id++, (glo_grid_id) + ",--,--,--,--,0.00,0.00,0.00,0.00");
				}
				window.setTimeout("call_skgzjl(" + (idx + 1)+ ")", 3000);
			}
		}
	);
}

function getDescByNo(card_no) {
	for(var i = 0; i < farmer_inf.rows.length; i++) {
		if(card_no == farmer_inf.rows[i].card_no) {
			return farmer_inf.rows[i].farmer_desc;
		}
	}
	return "--";
}

function taskCancel() {
	cancel_flag = true;
	loading.loaded();
	$.post(def.basePath + "ajaxoper/actCommNp!cancelTask.action", {
		//userData1 : parent.tree_id.split("_")[1],
		userData1 : retValue.rtu_id,
		userData2 : ComntUseropDef.YFF_READDATA
	});
}

/*function getDataByidFromTree(id) {
	
	if(!parent.tree_loaded) {
		window.setTimeout("getDataByidFromTree('"+id+"')", 100);
		return;
	}
	if(!id) {
		if(parent.tree_id == "") {
			id = SDDef.GLOBAL_KE2;
		}
		id = parent.tree_id;;
	}
	
	if(id.indexOf(YDTable.TABLECLASS_RTUPARA) >=0 ) {
		id = id.split("_")[1];
		getFarmInfo(id);
		return;
	}
	if(id == undefined || id == "undefined") {
		id = SDDef.GLOBAL_KE2;
	}
	var selid = 0;
	var subItems = parent.tree.getAllSubItems(id);
	var children = subItems.split(",");
	var i = 0;
	for(; i < children.length; i++) {
		if(children[i].indexOf(YDTable.TABLECLASS_RTUPARA) >=0 ) {
			selid = children[i];
			parent.tree.selectItem(selid);
			parent.tree_id = selid;
			selid = selid.split("_")[1];
			
			break;
		}
	}
	
}*/

function getFarmInfo(rtu_id) {
	var rtu_id = retValue.rtu_id;
	if(!rtu_id) return;
	$.post(def.basePath + "ajaxoper/actCommNp!retFarmInfo.action", {result : rtu_id}, function(data) {
		if(data.result != "") {
			farmer_inf = eval("("+data.result+")");
		}
	});
}