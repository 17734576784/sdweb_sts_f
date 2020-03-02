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
	var gridHeader = "序号,客户编号,客户名称,客户地址,控制类型,操作日期,操作员,&nbsp;";
	var datatype = "int,str,str,str,str,str,str";
	
	/*
	mygrid.setImagePath(def.basePath + "images/grid/imgs/");
	mygrid.setHeader(gridHeader);
	mygrid.setInitWidths("50,120,150,150,80,170,100,*");
	mygrid.setColAlign("center,left,left,left,left,left");
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro");
	mygrid.enableSmartRendering(true);
	mygrid.init();
	mygrid.setSkin("light");
	*/
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val(datatype);
	$("#filename").val(encodeURI("低压控制信息查询"));
	
	gridopt.gridHeader           = gridHeader;
	gridopt.gridColAlign         = "center,left,left,left,left,left";
	gridopt.gridColTypes         = "ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "50,120,150,150,80,170,100,*";
	gridopt.gridSrndFlag		 = true;
	gridopt.click				 = false;
	
	gridopt.filter(1);
	
	gridopt.grid.setSkin("light");
	
	//如果 WebConfig配置文件中autoshow="1"，则自动加载查询
	if(dy_autoshow[0] == 1){
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
	
	var param = '{sdate:"'+sdate+'",edate:"'+edate+'",org:"'+$("#org").val()+'",rtu:"'+$("#rtu").val()+'",yhbh:"'+$("#yhbh").val()+'",yhmc:"'+$("#yhmc").val()+'",czy:"'+$("#czy").val()+'"}';
	
	gridopt.gridGetDataUrl      = def.basePath  + "ajaxdyjc/actSearch!kzxxSearch.action";
	gridopt.gridSearch			= param;
	var prs = $("#prs").val();
	gridopt.filter(prs?prs:20);
	
}
