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
function writeDB(){
	
	alert("不支持存库功能！");
}

function initGrid() {
	mygrid = new dhtmlXGridObject('gridbox');
	
	var gridHeader           = "序号,时间,参数类别,修改前,修改后";
	var gridColAlign         = "center,left,left,left,left";
	var gridColTypes         = "ro,ro,ro,ro,ro";
	var gridWidths           = "50,120,120,180,240";
	
	mygrid.setImagePath(def.basePath + "images/grid/imgs/");
	mygrid.setHeader(gridHeader);
	mygrid.setInitWidths(gridWidths);
	mygrid.setColAlign(gridColAlign);
	mygrid.setColTypes(gridColTypes);
	
	mygrid.init();
	
	parent.$("#vfreeze").val(1);
	parent.$("#hfreeze").val(0);
	parent.$("#header").val(encodeURI(gridHeader));
	parent.$("#colType").val("int,str,str,str,str,0.000,str");
	parent.$("#filename").val(encodeURI("参数设置记录"));
	
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

var glo_sel_group = new Array();
var glo_grid_id = 0;

function taskCall() {
	glo_grid_id = 0;
	
	if (retValue == null || retValue == undefined) {
		alert("请选择电表...");
		return;
	}
	
		
	if (retValue.yffmeter_type  != SDDef.YFF_NP_CARDTYPE_HNTY) {		//20 new  zkz temp
		return;
	}
	
	for(var i = 0; i < 5; i++) {				//temp
		glo_sel_group[i] = ComntProtMsg.YFF_CALL_HNQF_CSJL_FIR + i;
	}
		
	mygrid.clearAll();
	cancel_flag = false;
	
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	call_csjl(0);
}

function call_csjl(idx) {
	
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
					
					var data_array = json.data;
					var nums = data_array.length;
					var data_per = '';
					for(var i=0; i<nums; i++){
						data_per = data_array[i];

				
					var griddata = "," + data_per.bgtime + "," + data_per.di + "," + data_per.val1 + "," + data_per.val2;
					mygrid.addRow(glo_grid_id++, (glo_grid_id) + griddata);
					}
				window.setTimeout("call_csjl(" + (idx + 1)+ ")", 500);
			}
			else {
				
				for (var i = 0; i < 10; i++) {
					mygrid.addRow(glo_grid_id++, (glo_grid_id) + ",--,--,--,--,--,--,--,--,--");
				}
				window.setTimeout("call_csjl(" + (idx + 1)+ ")", 3000);
			}
		}
	);
}
//
//function getDescByNo(card_no) {
//	for(var i = 0; i < farmer_inf.rows.length; i++) {
//		if(card_no == farmer_inf.rows[i].card_no) {
//			return farmer_inf.rows[i].farmer_desc;
//		}
//	}
//	return "--";
//}

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