var grid = new dhtmlXGridObject("gridbox");
$(function(){
	$("#gridbox").height($(window).height()-26).focus();
	initGrid();
	loadData();
	$("#search").click(function(){
		loadData();
	});
	
	$("#confirm").click(function(){
		confirm();
	})
});

function initGrid(){
	var gridHeader	 = "Serial No.,Name,&nbsp";
	var gridwidth	 = "10,75,*";
	var gridcolalign = "center,center,center";
	var gridcoltype	 = "ro,ro,ro";
	var enabletip	 = "false,false,false";
	
	//grid.setImagePath(basePath + "images/imgs/");
	grid.setHeader(gridHeader);
	grid.setInitWidthsP(gridwidth);
	grid.setColAlign(gridcolalign);
	grid.setColTypes(gridcoltype);
	grid.init();
	
	grid.enableSmartRendering(true);
	
	grid.attachEvent("onRowSelect",doOnRowSelect);
	grid.attachEvent("onRowDblClicked",doOnRowDbClicked);
	
}

function loadData(){
	grid.clearAll();
	var param = $("#search_condition").val();

	$.post(basePath + "ajaxdocs/actRtuPara!loadAllConspara.action", 
		   {
				result : param
			}, 
		   function(data){
			   	if(data.result){
			   		var json = eval('(' + data.result + ')');
			   		grid.parse(json,'','json');
			   		grid.selectRow(0);
			   		initHiddenData();
			   	}
	});
}

function confirm(){
	var backData = {};
	backData.id = $("#consparaId").val();
	backData.desc = $("#consparaDesc").val();
	window.returnValue = backData;
	window.close();
}

function doOnRowSelect(rid){
	var desc = grid.cells(rid,1).getValue();
	$("#consparaId").val(rid);
	$("#consparaDesc").val(desc);
}

function initHiddenData(){
	var rid = grid.getRowId(0);
	var desc = grid.cells2(0,1).getValue();
	$("#consparaId").val(rid);
	$("#consparaDesc").val(desc);
}

function doOnRowDbClicked(rid){
	var desc = grid.cells(rid,1).getValue();
	var backData = {};
	backData.id = rid;
	backData.desc = desc;
	window.returnValue = backData;
	window.close();
}




