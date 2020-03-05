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
	
	$("#sdate").val(date.Format("yyyy-MM-01").toString());
	$("#edate").val(date.Format("yyyy-MM-dd").toString());
}


function initgrid(){
	
	//mygrid.setImagePath(def.basePath + "images/grid/imgs/");
	var gridHeader = "ID,Terminal,Numéro de série,Numéro client,Nom du client,Client Addr,Meter Addr,Type d'opération,Date d'opération,Opératrice,Type de transaction,Montant du paiement,Montant total,Crédit de transfert (kWh),Nombre d'achats";
	var datatype = "int,str,str,str,str,str,str,str,str,str,str,0.00,0.00,0.00,,int";
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val(datatype);
	$("#filename").val(encodeURI("Requêtecomplète"));
	
	
	gridopt.gridHeader           = gridHeader;
	gridopt.gridColAlign         = "center,left,left,left,left,left,left,left,center,left,left,right,right,right,right";
	gridopt.gridColTypes         = "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "50,120,120,80,130,130,130,80,150,100,100,100,100,100,100";
	gridopt.gridTooltips         = "center,left,left,left,left,left,left,left,center,left,left,right,right,right,right";
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
	
	var y1 = sdate.substring(0, 4), y2 = edate.substring(0, 4);
	if(Math.abs(parseInt(y1) - parseInt(y2)) > 3) {
		alert("Veuillez sélectionner les données dans les 4 ans.");
		return;
	}
	
	var param = '{sdate:"'+sdate+'",edate:"'+edate+'",yhbh:"'+$("#yhbh").val()+'",yhmc:"'+$("#yhmc").val()+'",czy:"'+$("#czy").val()+'",org:"'+$("#org").val()+'",rtu:"'+$("#rtu").val()+'",oper_type:"'+$("#oper_type").val()+'"}';
	
	gridopt.gridGetDataUrl      = def.basePath  + "ajaxdyjc/actSearch!zhSearch.action";
	gridopt.gridSearch			= param;
	var prs = $("#prs").val();
	gridopt.filter(prs?prs:20);
	
}
