var pos = false;

$(document).ready(function(){
	
	initDate();
	initgrid();
	
	$("#search").click(function(){
		search();
	});
	
});

function initDate(){
	var date = new Date();
	
	$("#sdate").val(date.Format("yyyy年MM月01日").toString());
	$("#edate").val(date.Format("yyyy年MM月dd日").toString());
}


function initgrid(){
	
	var gridHeader = "序号,流水号,客户编号,客户名称,客户地址,操作类型,操作日期,操作员,缴费方式,缴费金额(元),结算金额(元),追补金额(元),总金额(元),报警值1,报警值2,断电金额(元),购电次数";
	var datatype = "int,str,str,str,str,str,str,str,str,0.00,0.00,0.00,0.00,0.00,0.00,0.00,int";
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val(datatype);
	$("#filename").val(encodeURI("高压综合查询"));
	
	
	gridopt.gridHeader           = gridHeader;
	gridopt.gridColAlign         = "center,left,left,left,left,left,center,left,left,right,right,right,right,right,right,right,right";
	gridopt.gridColTypes         = "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "50,150,80,150,130,80,165,100,100,100,100,100,100,100,100,100,100";
	gridopt.gridTooltips         = "center,left,left,left,left,left,center,left,left,right,right,right,right,right,right,right,right";
	gridopt.gridSrndFlag		 = true;
	gridopt.click				 = false;
	
	gridopt.filter(1);
	
	gridopt.grid.setSkin("light");
	
	//如果 WebConfig配置文件中autoshow="1"，则自动加载查询
	if(spec_autoshow[0] == 1){
		search();
	}
}

function search(){
	var flag = true;
	for ( var i = 0; i < load_flag.length; i++) {
		flag = flag && load_flag[i];
	}
	if (!flag) {
		window.setTimeout('search()', 100);
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
	var param = '{sdate:"'+sdate+'",edate:"'+edate+'",yhbh:"'+$("#yhbh").val()+'",yhmc:"'+$("#yhmc").val()+'",czy:"'+$("#czy").val()+'",org:"'+$("#org").val()+'",rtu:"'+$("#rtu").val()+'",oper_type:"'+$("#oper_type").val()+'"}';
	
	gridopt.gridGetDataUrl      = def.basePath  + "ajaxspec/actSearch!zhSearch.action";
	gridopt.gridSearch			= param;
	var prs = $("#prs").val();
	gridopt.filter(prs?prs:20);
	
	setExcValue();
	
}
