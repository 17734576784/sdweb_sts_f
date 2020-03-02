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
	
	var gridHeader           = "序号,客户名称,卡号,开始用电时间,挂起时间,费率,用电金额,剩余金额,用电电量,过零电量,用电状态";
	var gridColAlign         = "center,left,left,left,left,right,right,right,right,right,left";
	var gridColTypes         = "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	var gridWidths           = "50,120,120,180,180,120,120,120,120,120,120";
	
	mygrid.setImagePath(def.basePath + "images/grid/imgs/");
	mygrid.setHeader(gridHeader);
	mygrid.setInitWidths(gridWidths);
	mygrid.setColAlign(gridColAlign);
	mygrid.setColTypes(gridColTypes);
	
	mygrid.init();
	
	parent.$("#vfreeze").val(1);
	parent.$("#hfreeze").val(0);
	parent.$("#header").val(encodeURI(gridHeader));
	parent.$("#colType").val("int,str,str,str,str,0.000,0.000,0.000,0.000,0.000,str");
	parent.$("#filename").val(encodeURI("挂起用户记录"));
	
}

var glo_sel_group = new Array();
var glo_grid_id = 0;

function taskCall() {
	if (retValue == null || retValue == undefined) {
		alert("请选择电表...");
		return;
	}
	
	glo_grid_id = 0;
	
	if (retValue.yffmeter_type  == SDDef.YFF_NP_CARDTYPE_HNTY) {		//20 new  zkz temp
		for(var i = 0; i < 3; i++) {
				glo_sel_group[i] = ComntProtMsg.YFF_CALL_HNQF_GQJL_FIR + i;
		}
	}
	else {
		for(var i = 0; i < 20; i++) {
			glo_sel_group[i] = ComntProtMsg.YFF_CALL_DL645_GQJL_FIR + i;
		}
	}
	
	mygrid.clearAll();
	cancel_flag = false;
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
    call_gqjl(0);
}
function writeDB(){
	if(mygrid.getRowsNum() == 0) 
	{
		alert("没有要存库的内容！"); return
	}
	var rtu_id 	= retValue.rtu_id;
	var mpid 	= retValue.mp_id;
	//alert("rtu_id  "+ rtu_id +" mp_id   "+mpid);
	var param = "{\"rows\":[";
	for(var i =0; i< mygrid.getRowsNum(); i++){
		if(mygrid.cells2(i, 2).getValue() == "--" || mygrid.cells2(i, 3).getValue() == "--" || mygrid.cells2(i, 4).getValue() == "--") continue;

		param += "{\"cardno\":\"" +mygrid.cells2(i, 2).getValue()+"\",";
		param += "\"begendate\":\"" +mygrid.cells2(i,3).getValue()+"\",";
		param += "\"enddate\":\"" +mygrid.cells2(i,4).getValue()+"\",";
		param += "\"fee\":\"" +mygrid.cells2(i,5).getValue()+"\",";
		param += "\"usemoney\":\"" +mygrid.cells2(i,6).getValue()+"\",";
		param += "\"remainmoney\":\"" +mygrid.cells2(i,7).getValue()+"\",";
		param += "\"usedl\":\"" +mygrid.cells2(i,8).getValue()+"\",";
		param += "\"zerodl\":\"" +mygrid.cells2(i,9).getValue()+"\",";
		param += "\"state\":\"" +mygrid.cells2(i,10).getValue()+"\"},";

	}
	param = param.substr(0,param.length - 1);
	param += "]}";
	//alert("param :  "+param );
 	$.post(def.basePath + "ajaxoper/actCommNp!taskProc.action", 
		{
			userData1 		: rtu_id,
			userData2 		: ComntUseropDef.YFF_NPGQOPER_WriteDB,
			mpid 	  		: mpid,
			npCommSetBase 	: param
		},
		function(data) {
 			if(data.result == "success"){
				alert("存库成功！");
		
			}else{
				alert("存库失败，请检查是否是有效数据！" );			
			}
 		
		}
	);
	
}

function call_gqjl(idx) {
	if(cancel_flag) return;
	if(idx >= glo_sel_group.length) {
		loading.loaded();
		return;
	}
	var rtu_id = retValue.rtu_id;
	var mpid   = retValue.mp_id;
	
	var npCommReadData = '{ymd:'+_ymd+', datatype : '+glo_sel_group[idx]+'}';
	
	$.post(def.basePath + "ajaxoper/actCommNp!taskProc.action", 
		{
			//firstLastFlag : true,
			userData1 : rtu_id,
			userData2 : ComntUseropDef.YFF_READDATA,
			mpid : mpid,
			npCommReadData : npCommReadData
		},
		function(data) {
			window.top.addJsonOpDetail(data.detailInfo);
			if(data.result == "success") {
				//alert(data.npCommReadData);
				var json = eval("(" + data.npCommReadData + ")");
				var data_array = json.data;
				var nums = data_array.length;
				
				var data_per = '';
				for(var i=0; i<nums; i++){
					data_per = data_array[i];
					
					var griddata = "," + getDescByNo(data_per.kh) + "," + data_per.kh + "," + data_per.bgtime + "," + data_per.endtime + "," + data_per.fee + "," + data_per.ydje + "," + data_per.syje + "," + data_per.yddl + "," + data_per.gldl + "," + data_per.state;
					
					mygrid.addRow(glo_grid_id++, (glo_grid_id) + griddata);
				}
				window.setTimeout("call_gqjl(" + (idx + 1)+ ")", 500);
			}
			else {
				if (retValue.yffmeter_type  == SDDef.YFF_NP_CARDTYPE_HNTY) {		//20 new  zkz temp
					for (var i = 0; i < 10; i++) {
						mygrid.addRow(glo_grid_id++, (glo_grid_id) + ",--,--,--,--,0,0,0,0,0,--");
					}
				}
				else {
						mygrid.addRow(glo_grid_id++, (glo_grid_id) + ",--,--,--,--,0,0,0,0,0,--");
				}
				
				window.setTimeout("call_gqjl(" + (idx + 1)+ ")", 3000);
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
	loading.loaded();
	cancel_flag = true;
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

function getFarmInfo() {
	var rtu_id = retValue.rtu_id;
	if(!rtu_id) return;
	$.post(def.basePath + "ajaxoper/actCommNp!retFarmInfo.action", {result : rtu_id}, function(data) {
		if(data.result != "") {
			farmer_inf = eval("("+data.result+")");
		}
		else {
			alert("该终端下没有农排客户档案，请先建立农排客户档案！");
			return;
		}
	});
}