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
	
	$("#sdate").val(date.Format("yyyy年MM月01日").toString());
	$("#edate").val(date.Format("yyyy年MM月dd日").toString());
}

function initgrid(){
	
	var gridHeader = "序号,客户编号,客户名称,卡号,电费(元),#cspan,#cspan,#cspan,日期,操作员,&nbsp;";
	var attachheader = "#rspan,#rspan,#rspan,#rspan,缴费金额,追补金额,结算金额,总金额,#rspan,#rspan,#rspan";
	var datatype = "int,str,str,str,0.000,0.000,0.000,0.000,str,str";
	
	mygrid.setImagePath(def.basePath + "images/grid/imgs/");
	mygrid.setHeader(gridHeader);
	mygrid.attachHeader(attachheader);
	mygrid.setInitWidths("50,120,120,120,120,100,100,120,180,100,*");
	mygrid.setColAlign("center,left,left,left,right,right,right,right,left,left");
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	//mygrid.enableSmartRendering(true);
	
	mygrid.init();
	mygrid.setSkin("modern");
	var empty = "&nbsp;";
	var footer = "<div id='hj'>合计：</div>,#cspan,#cspan,#cspan,<div id='jfje'>"+empty+"</div>,<div id='zbje'>"+empty+"</div>,<div id='jsje'>"+empty+"</div>,<div id='zje'>"+empty+"</div>,&nbsp;,#cspan,#cspan,#cspan";
	var foot_align = ["text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;"];
	mygrid.attachFooter(footer, foot_align);
	
	$("#vfreeze").val(2);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val(datatype);
	$("#attachheader").val(encodeURI(attachheader));
	$("#filename").val(encodeURI("农排购电明细表"));
	
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
	
	mygrid.clearAll();
	$("#jfje").html("&nbsp;");
	$("#zbje").html("&nbsp;");
	$("#jsje").html("&nbsp;");
	$("#zje").html("&nbsp;");
			
	var sdate = getDate_Time($("#sdate").val());
	var edate = getDate_Time($("#edate").val());
	
	if(sdate > edate){
		edate = sdate;
		$("#edate").val($("#sdate").val());
	}
	
	var param = '{sdate:"'+sdate+'",edate:"'+edate+'",czy:"'+$("#czy").val()+'",org:"'+$("#org").val()+'",area:"'+$("#area").val()+'"}';
	
	loading.loading();
	
	$.post(def.basePath + "ajaxnp/actReport!gdmxReport.action",{result : param},function(data){
		loading.loaded();
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			mygrid.parse(json, "json");
		
			var mr = '{cols:[{col:1},{col:2,key_idx:[0,1]},{col:3}]}';
			$("#mergeRows").val(mr);
			var mr_json = eval('(' + mr + ')');
			
			mergeRow(json, mr_json);
			
			var hz = eval('(' + data.hz + ')');
			$("#jfje").html(hz.jfje);
			$("#zbje").html(hz.zbje);
			$("#jsje").html(hz.jsje);
			$("#zje").html(hz.zje);
			
			var footer = '{rows:[{data:["'+$("#hj").html()+',0-3","'+$("#jfje").html()+'","'+$("#zbje").html()+'","'+$("#jsje").html()+'","'+$("#zje").html()+'",",8-9"]}]}';
			var footerType = "str,0.000,0.000,0.000,0.000,str";
			$("#footer").val(footer);
			$("#footerType").val(footerType);
			$("#excPara").val(encodeURI(jsonString.json2String(json)));
			
		}else{
			
		}
	});
	
}
