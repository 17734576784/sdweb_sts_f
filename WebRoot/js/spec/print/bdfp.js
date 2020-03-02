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
	$("#sdate").val(date.Format("yyyy年MM月01日").toString());
	$("#edate").val(date.Format("yyyy年MM月dd日").toString());
}
/**通用不传param时的grid初始化*/
function initgrid(){
	var gridHeader = "序号,流水号,客户编号,客户名称,客户地址,操作类型,操作日期,操作员,缴费方式,缴费金额(元),结算金额(元),追补金额(元),总金额(元),报警值1,报警值2,购电次数,电价";
	var datatype = "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val(datatype);
	$("#filename").val(encodeURI("补打发票"));
	gridopt.gridHeader           = gridHeader;
	gridopt.gridColAlign         = "center,left,left,left,left,left,center,left,left,right,right,right,right,right,right,right,left";
	gridopt.gridColTypes         = "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "50,120,80,100,80,80,100,100,100,100,100,100,100,100,100,100,280";
	gridopt.gridTooltips         = "center,left,right,center,left,center,center,left,left,right,right,right,right,right,right,right,left";
	gridopt.gridSrndFlag		 = true;
	gridopt.click				 = false;
	gridopt.filter(1);
	gridopt.grid.setSkin("light");
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
	var param = '{sdate:"'+sdate+'",edate:"'+edate+'",khbh:"'+$("#khbh").val()+'",czy:"'+$("#czy").val()+'",khmc:"'+$("#khmc").val()+'",org:"'+$("#org").val()+'",rtu:"'+$("#rtu").val()+'"}';
	
	gridopt.gridGetDataUrl=def.basePath  + "ajaxspec/actSupplyBill!billSearch.action";
	gridopt.gridSearch    = param;  
	var prs = $("#prs").val();
	gridopt.filter(prs?prs:20);
}
function rePrint(){//补打发票
	//得到选中的id
	var mygrid=gridopt.grid;
	var selectedId=mygrid.getSelectedRowId();
	
	if(null==selectedId){
		alert("请您选择要打印的内容");
		return;
	}
	
	var ids = selectedId.split("_");
	
	window.top.WebPrint.prt_params.rtu_id  = ids[0];
 	window.top.WebPrint.prt_params.sub_id  = ids[1];
	window.top.WebPrint.prt_params.op_date = ids[3];
	window.top.WebPrint.prt_params.op_time = ids[4];
  	window.top.WebPrint.prt_params.op_type = ids[2]; 
 	window.top.WebPrint.prt_params.wasteno = mygrid.cells(selectedId, 1).getValue();;
 	window.top.WebPrint.prt_params.page_type = ids[5];
	window.top.WebPrint.prt_params.reprint = 1;
	
	var filename =  window.top.WebPrint.getTemplateFileNm(ids[5]);
	if(filename == undefined || filename == null){
		return;
	}
	window.top.WebPrint.prt_params.file_name = filename;
//	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.gyje;
	window.top.WebPrint.doPrintGy();
	
}