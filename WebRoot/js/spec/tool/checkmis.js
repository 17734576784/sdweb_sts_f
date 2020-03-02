var pos = false;
var mygrid = null;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识

var rtu_id = -1, zjg_id = -1, op_type = -1, bczlsh = "";

$(document).ready(function() {
	mygrid = new dhtmlXGridObject('gridbox');
	initDate();
	initGrid();
	
	$("#search").click(function(){search();});
	$("#upload").click(function(){upload();});
});

function initDate() {
	var date = new Date();
	
	$("#sdate").val(date.Format("yyyy年MM月dd日").toString());
	$("#edate").val(date.Format("yyyy年MM月dd日").toString());
}

function initGrid() {
	var gridHeader = "序号,客户编号,客户名称,操作类型,操作日期,缴费金额(元),结算金额(元),追补金额(元),总金额(元),购电次数,流水号,被冲正流水号,操作员,缴费方式,客户地址,上传标志,成功标志";
	mygrid.setImagePath(def.basePath + "images/grid/imgs/");
	mygrid.setHeader(gridHeader);
	mygrid.setInitWidths("50,120,140,80,165,100,100,100,100,100,110,110,100,80,130,60,60");
	mygrid.setColAlign("center,left,left,left,left,right,right,right,right,right,left,left,left,left,left,left,left");
	mygrid.setColTypes("ro,ro,ro,coro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.attachEvent("onRowSelect", doOnRowSelected);
	//设置列的排序
	//mygrid.setColSorting("na,na,na,str,str");
	mygrid.init();
	mygrid.enableSmartRendering(true);
	mygrid.setSkin("light");
	
	var text = '{item:["' + Dict.DICTITEM_YFFOPTYPE + '"]}';
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : text},function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			
			var dict = eval("json.rows[0]."+Dict.DICTITEM_YFFOPTYPE);
			for ( var i = 0; i < dict.length; i++) {
				mygrid.getCombo(3).put(dict[i].value, dict[i].text);
			}
			
		}
	});
	
	
	//search();
}

function doOnRowSelected(id) {
	rtu_id = id.split("_")[0];
	zjg_id = id.split("_")[1];
	op_type = mygrid.cells(id, 3).getValue();
	bczlsh = mygrid.cells(id, 11).getValue();
	
	$("#khbh").val(mygrid.cells(id, 1).getValue());
	$("#khmc").val(mygrid.cells(id, 2).getValue());
	$("#khdz").val(mygrid.cells(id, 14).getValue());
	$("#czrq").val(mygrid.cells(id, 4).getValue());
	$("#jfje").val(mygrid.cells(id, 5).getValue());
	$("#lsh").val(mygrid.cells(id, 10).getValue());
	$("#scbz").val(mygrid.cells(id, 15).getValue());
	$("#cgbz").val(mygrid.cells(id, 16).getValue());
}

function clearInfo(){
	$("#khbh").val("");
	$("#khmc").val("");
	$("#khdz").val("");
	$("#czrq").val("");
	$("#jfje").val("");
	$("#lsh").val("");
	$("#scbz").val("");
	$("#cgbz").val("");
}

function search() {
	var sdate = getDate_Time($("#sdate").val());
	var edate = getDate_Time($("#edate").val());
	
	if(sdate > edate){
		edate = sdate;
		$("#edate").val($("#sdate").val());
	}
	
	var param = '{sdate:"'+sdate+'",edate:"'+edate+'",czy:"'+$("#czy").val()+'",org:"'+$("#org").val()+'",err_type:"'+$("#err_type").val()+'"}';
	loading.loading();
	$("#total").html("");
	mygrid.clearAll();
	$.post(def.basePath + "ajaxoper/actMisGy!misErrSearch.action",{result : param},function(data){
		loading.loaded();
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			mygrid.parse(json,"json");
			
			for(var i = 0; i < mygrid.getRowsNum(); i++)
				mygrid.cells2(i, 3).setDisabled(true);
			
		}
		rtu_id = -1;
		$("#total").html("共" + mygrid.getRowsNum() + "条记录");
	});
}

function upload() {
	if(rtu_id == -1)return;
	
	var param = {};
	param.rtu_id = rtu_id;
	param.zjg_id = zjg_id;
	var date = $("#czrq").val();
	if(date == ""){
		alert("日期不能为空!");
		return;
	}
	param.op_date = date.substring(0, 4) + date.substring(5,7) + date.substring(8, 10);
	if(_today != param.op_date){
		alert("不能操作非今天的记录!");
		return;
	}
	param.op_time = date.substring(11, 13) + date.substring(14, 16) + date.substring(17, 19);
	param.op_type = op_type;
	param.yhbh = $("#khbh").val();
	param.pay_money = $("#jfje").val() == "" ? 0 : $("#jfje").val();
	param.lsh = $("#lsh").val();
	param.bczlsh = bczlsh;
	
	var userData2 = 0;
	if(param.op_type == SDDef.YFF_OPTYPE_REVER) {
		userData2 = ComntUseropDef.YFF_GYOPER_MIS_CHECKREVER;
	}
	else {
		userData2 = ComntUseropDef.YFF_GYOPER_MIS_CHECKPAY;
	}

	var str_url = "ajaxoper/actMisGy!taskProc.action";
	if (provinceMisFlag == "GS") {
		str_url = "ajaxoper/actMisGyGS!taskProc.action";
	}
	
	loading.loading();
	//$.post(def.basePath + "ajaxoper/actMisGy!taskProc.action",
	$.post(def.basePath + str_url,
		{
			gyOperStr : jsonString.json2String(param),
			userData1 : rtu_id,
			userData2 : userData2
		},
	function(data){
		loading.loaded();
		alert(data.misResult);
		
		if(data.result == "success"){
			search();
			clearInfo();
		}
	});
}