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
	var gridHeader = "ID,POS,Opératrice,Nombre d'achats,Elec-charge,#cspan,Crédit de transfert (kWh),&nbsp;";
	var attachheader = "#rspan,#rspan,#rspan,#rspan,Montant du paiement,Montant total,#rspan,#rspan";
	var datatype = "int,str,str,int,0.000,0.000,0.000";
	mygrid.setImagePath(def.basePath + "images/grid/imgs/");
	mygrid.setHeader(gridHeader);
	mygrid.attachHeader(attachheader);
	mygrid.setInitWidths("50,200,150,80,150,150,150,*");
	mygrid.setColAlign("center,center,left,right,right,right,right");
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro");
	mygrid.init();
	mygrid.setSkin("light");
	
	var empty = "&nbsp;";
	var footer = "<div id='hj'>Total：</div>,#cspan,#cspan,<div id='jfcs'>"+empty+"</div>,<div id='jfje'>"+empty+"</div>,<div id='zje'>"+empty+"</div>,<div id='zdl'>"+empty+"</div>,&nbsp;,#cspan,#cspan";
	var foot_align = ["text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;"];
	mygrid.attachFooter(footer, foot_align);
	
	
	$("#vfreeze").val(2);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val(datatype);
	$("#attachheader").val(encodeURI(attachheader));
	$("#filename").val(encodeURI("Résumé d'achat des opérateurs"));
	
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
	$("#jfcs").html("&nbsp;");
	$("#jfje").html("&nbsp;");
	$("#zje").html("&nbsp;");
	$("#zdl").html("&nbsp;");
			
	var sdate = getDate_Time($("#sdate").val());
	var edate = getDate_Time($("#edate").val());
	
	if(sdate > edate){
		edate = sdate;
		$("#edate").val($("#sdate").val());
	}
	
	var param = '{sdate:"'+sdate+'",edate:"'+edate+'",org:"'+$("#org").val()+'"}';
	
	loading.loading();
	
	$.post(def.basePath + "ajaxdyjc/actReport!opmanSalsumReport.action",{result : param},function(data){
		loading.loaded();
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			mygrid.parse(json,"json");

			var mr = '{cols:[{col:1}]}';
			$("#mergeRows").val(mr);
			var mr_json = eval('(' + mr + ')');
			mergeRow(json, mr_json);
			
			var hz = eval('(' + data.hz + ')');
			$("#jfcs").html(hz.jfcs);
			$("#jfje").html(hz.jfje);
			$("#zje").html(hz.zje);
			$("#zdl").html(hz.zdl);
			
			var footer = '{rows:[{data:["'+$("#hj").html()+',0-2","'+$("#jfcs").html()+'","'+$("#jfje").html()+'","'+$("#zje").html()+'","'+$("#zdl").html()+'"]}]}';
			var footerType = "str,int,0.000,0.000,0.000,0.000";
			$("#footer").val(footer);
			$("#footerType").val(footerType);
			$("#excPara").val(encodeURI(jsonString.json2String(json)));
			
		}else{
			
		}
	});
	
}
