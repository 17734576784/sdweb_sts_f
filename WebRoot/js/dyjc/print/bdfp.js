var	mygrid = null;
var pos = false;
$(document).ready(function(){
	initDate();
	initgrid();
	$("#search").click(function(){
		search();
	});
	$("#toprint").click(function(){
		rePrint();
	});
});
//给开始和结束时间初始化赋值
function initDate(){
	var date = new Date();
	$("#sdate").val(date.Format("yyyy-MM-01").toString());
	$("#edate").val(date.Format("yyyy-MM-dd").toString());
}
/**通用不传param时的grid初始化*/
function initgrid(){
	var gridHeader = "ID,Serial No,Terminal,Customer ID,Customer Name,Addr,OperType,OperDate,Operator,ChargeType,Payment amount,Total amount,Balance,Transfer Credit(kWh),Purchase Count,Unit Price";
	var datatype = "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val(datatype);
	$("#filename").val(encodeURI("Print Tickets"));
	gridopt.gridHeader           = gridHeader;
	gridopt.gridColAlign         = "center,left,left,left,left,left,left,center,left,left,right,right,right,right,right,left";
	gridopt.gridColTypes         = "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "50,120,120,80,100,80,120,120,100,100,100,100,100,100,100,150";
	gridopt.gridTooltips         = "center,left,left,right,center,left,center,center,left,left,right,right,right,right,right,left";
	gridopt.gridSrndFlag		 = true;
	gridopt.click				 = false;
	gridopt.filter(1);
	gridopt.grid.setSkin("light");
	gridopt.selectRow = 0;
	search();
};
function search(){
	var flag = true;
	for ( var i = 0; i < load_flag.length; i++) {
		flag = flag && load_flag[i];
	}
	if (!flag) {
		window.setTimeout('search()', 100);//??
		return;
	}
	
	var sdate = getDate_Time($("#sdate").val());
	var edate = getDate_Time($("#edate").val());
	
	if(sdate > edate){
		edate = sdate;
		$("#edate").val($("#sdate").val());
	}
	
	if(sdate.substring(0, 4) != edate.substring(0, 4)){
		alert("不能跨年选择日期");
		return;
	}
	//var param = '{sdate:"'+sdate+'",edate:"'+edate+'",khbh:"'+$("#khbh").val()+'",czy:"'+$("#czy").val()+'",khmc:"'+$("#khmc").val()+'",org:"'+$("#org").val()+'",rtu:"'+$("#rtu").val()+'"}';
	var params={};
	params.sdate = sdate;
	params.edate = edate;
	params.khbh = $("#khbh").val();
	params.czy  = $("#czy").val();
	params.khmc = $("#khmc").val();
	params.org  = $("#org").val();
	params.rtu  = $("#rtu").val();
	var json_str = jsonString.json2String(params);
	gridopt.gridGetDataUrl=def.basePath  + "ajaxdyjc/actSupplyBill!billSearch.action";
	gridopt.gridSearch    = json_str;  
	var prs = $("#prs").val();
	gridopt.filter(prs?prs:20);
}

function rePrint(){//补打发票
	//得到选中的id
	var selectedId=gridopt.grid.getSelectedRowId();
	
	if(null==selectedId){
		alert("Please Select Print Content.");
		return;
	}
	
	var ids = selectedId.split("|");
	var printParam = {};
	if(ids[2] == "1"){
		printParam.opType = "open";
		printParam.token1 = ids[6];
		printParam.token2 = ids[7];
		printParam.token3 = ids[11];
	}else{
		printParam.opType = "pay";
		printParam.token1 = ids[11];
	}

	printParam.meterNo = ids[12];
	printParam.customerID = gridopt.grid.cells(selectedId,3).getValue();
	printParam.conName = gridopt.grid.cells(selectedId,4).getValue();
	

	printParam.payDate = gridopt.grid.cells(selectedId,7).getValue();
	printParam.payMoney = gridopt.grid.cells(selectedId,10).getValue(); 
	printParam.payDL = gridopt.grid.cells(selectedId,13).getValue(); 
	printParam.finalJyje = gridopt.grid.cells(selectedId,12).getValue(); 
	printParam.sdadmin = gridopt.grid.cells(selectedId,8).getValue();
	printParam.wasteno = gridopt.grid.cells(selectedId,1).getValue(); 
	printParam.orgDesc 	= ids[8];
	printParam.orgAddr 	= ids[9];
	printParam.telno 	= ids[10];
	
	
	modalDialog.width 	= 250;
	modalDialog.height 	= 600;
	modalDialog.param	= printParam;
	modalDialog.url 	= basePath + "jsp/dyjc/print/printTemplate.jsp";
		
	modalDialog.show();
	
	
//	var ids = selectedId.split("_"); 
// 	window.top.WebPrint.prt_params.rtu_id  = ids[0];
// 	window.top.WebPrint.prt_params.sub_id  = ids[1];
//	window.top.WebPrint.prt_params.op_date = ids[3];
//	window.top.WebPrint.prt_params.op_time = ids[4];
//  	window.top.WebPrint.prt_params.op_type = ids[2]; 
// 	window.top.WebPrint.prt_params.wasteno = mygrid.cells(selectedId, 1).getValue();;    
//	window.top.WebPrint.prt_params.page_type = ids[5];
//	window.top.WebPrint.prt_params.reprint = 1;
	
	
	
	
//  	if(ids[5] == 2 ){
//		window.top.WebPrint.prt_params.ext_info = ids[6]+"---"+ids[7];
//	}else{
//		window.top.WebPrint.prt_params.ext_info = "";
//	}
// 	
//	var filename =  window.top.WebPrint.getTemplateFileNm(ids[5]);
//	if(filename == undefined || filename == null){
//		return;
//	}
//	window.top.WebPrint.prt_params.file_name = filename;
//	window.top.WebPrint.doPrintDy();
	
}