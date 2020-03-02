var pos = false;

$(document).ready(function(){
	
	initDate();
	initgrid();
	
	$("#search").click(function(){search();});
	
});

function initDate(){
	var date = new Date();
	
	$("#sdate").val(date.Format("yyyy年MM月01日").toString());
	$("#edate").val(date.Format("yyyy年MM月dd日").toString());
}


function initgrid(){
	
	var gridHeader	= "序号,所属片区,客户名称,客户编号,卡号,区域号,操作员,操作类型,操作日期,缴费方式,流水号,被冲正流水号,缴费金额,结算金额,追补金额,总金额,上次剩余金额,当前剩余金额,累计购电金额,购电次数";
	var datatype 	= "int,str,str,str,str,str,str,str,str,str,,str,str,0.00,0.00,0.00,0.00,0.00,0.00,0.00,int";
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val(datatype);
	$("#filename").val(encodeURI("农排综合查询"));
	
	
	gridopt.gridHeader           = gridHeader;
	gridopt.gridColAlign         = "center,left,left,left,left,left,left,center,left,left,left,left,right,right,right,right,right,right,right,right";
	gridopt.gridColTypes         = "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "50,120,120,120,130,130,80,100,150,100,100,100,100,100,100,100,100,100,100,100";
	gridopt.gridTooltips         = "center,left,left,left,left,left,left,center,left,left,left,left,right,right,right,right,right,right,right,right";
	gridopt.gridSrndFlag		 = true;
	gridopt.click				 = false;
	
	gridopt.filter(1);
	
	gridopt.grid.setSkin("light");
	
	//如果 WebConfig配置文件中autoshow="1"，则自动加载查询
	if(np_autoshow[0] == 1){
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
	
	var y1 = sdate.substring(0, 4), y2 = edate.substring(0, 4);
	if(Math.abs(parseInt(y1) - parseInt(y2)) > 3) {
		alert("请选择4年内的数据");
		return;
	}
	
	var param = '{sdate:"'+sdate+'",edate:"'+edate+'",yhbh:"'+$("#yhbh").val()+'",cardno:"'+$("#cardno").val()+'",yhmc:"'+$("#yhmc").val()+'",czy:"'+$("#czy").val()+'",org:"'+$("#org").val()+'",oper_type:"'+$("#oper_type").val()+'"}';
	
	gridopt.gridGetDataUrl      = def.basePath + "ajaxnp/actSearch!zhSearch.action";
	gridopt.gridSearch			= param;
	var prs = $("#prs").val();
	gridopt.filter(prs?prs:20);
	
}


