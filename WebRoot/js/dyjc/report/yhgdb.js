var	mygrid = null;
var pos = true;

$(document).ready(function(){
	mygrid = new dhtmlXGridObject('gridbox');
	
	initgrid();
	initDate();
	
	$("#search").click(function(){
		search();
	});
	
});
function initDate(){
	var date = new Date();
	
	$("#sdate").val(date.Format("yyyy-MM-01").toString());
	$("#edate").val(date.Format("yyyy-MM-dd").toString());
}

function initgrid(){
	mygrid.setImagePath(def.basePath + "images/grid/imgs/");
	var gridHeader = "ID,Customer No,Customer Name,Meter Addr,Purchase Count,Elec-charge,#cspan,Transfer Credit,Average Price,&nbsp;";
	var attachheader = "#rspan,#rspan,#rspan,#rspan,#rspan,Payment amount,Total amount,#rspan,#rspan,#rspan";
	var datatype = "int,str,str,str,int,0.000,0.000,0.000,0.000,0.000";
	mygrid.setHeader(gridHeader);
	mygrid.attachHeader(attachheader);
	mygrid.setInitWidths("50,150,200,120,120,120,120,120,120,*");
	mygrid.setColAlign("center,left,left,left,right,right,right,right,right,right");
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.enableSmartRendering(true);
	mygrid.init();
	mygrid.setSkin("modern");
	
	var empty = "&nbsp;";
	var footer = "<div id='hj'>Total：</div>,#cspan,#cspan,<div id='jfcs'>"+empty+"</div>,<div id='jfje'>"+empty+"</div>,<div id='zbje'>"+empty+"</div>,<div id='jsje'>"+empty+"</div>,&nbsp;,#cspan,#cspan,#cspan";
	var foot_align = ["text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;"];
	mygrid.attachFooter(footer, foot_align);
	
	
	$("#vfreeze").val(2);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val(datatype);
	$("#attachheader").val(encodeURI(attachheader));
	$("#filename").val(encodeURI("PurchaseSummaryOfCustomers"));
	
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
	
	mygrid.clearAll();
	
	var empty = "&nbsp;";
	$("#jfcs").html(empty);
	$("#jfje").html(empty);
	$("#zbje").html(empty);
	$("#jsje").html(empty);
	$("#zje").html(empty);
	
	var sdate = getDate_Time($("#sdate").val());
	var edate = getDate_Time($("#edate").val());
	
	if(sdate > edate){
		edate = sdate;
		$("#edate").val($("#sdate").val());
	}
	
	var param = '{sdate:"'+sdate+'",edate:"'+edate+'",yhbh:"'+$("#yhbh").val()+'",org:"'+$("#org").val()+'",rtu:"'+$("#rtu").val()+'"}';
	
	loading.loading();
	
	$.post(def.basePath + "ajaxdyjc/actReport!yhgdReport.action",{result : param},function(data){
		loading.loaded();
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			mygrid.parse(json,"json");
			
			var hz = eval('(' + data.hz + ')');
			$("#jfcs").html(hz.jfcs);
			$("#jfje").html(hz.jfje);
			$("#zbje").html(hz.zbje);
			$("#jsje").html(hz.jsje);
			$("#zje").html(hz.zje);
			
			var footer = '{rows:[{data:["'+$("#hj").html()+',0-2","'+$("#jfcs").html()+'","'+$("#jfje").html()+'","'+$("#jfje").html()+'","'+$("#jsje").html()+'","",""]}]}';
			var footerType = "str,int,0.000,0.000,0.000,0.000,str";
			$("#footer").val(footer);
			$("#footerType").val(footerType);
			$("#excPara").val(encodeURI(jsonString.json2String(json)));
			
		}else{
			
		}
	});
	
}
