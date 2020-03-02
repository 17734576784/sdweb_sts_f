var pos = false;

$(document).ready(function(){
	
	initgrid();
	
	$("#search").click(function(){
		search();
	});
	
});

function initgrid(){
	var gridHeader = "ID,Terminal,Numéro client,Meter No,Nom du client,Adresse,Meter Addr,Tel,Mobile,Rapport de transformateur,Fabriqué Non,Modèle de compteur,Mode de connexion,Constructeur,&nbsp;";
	var datatype = "int,str,str,str,str,str,str,str,str,0.00,str,str,str,str";
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val(datatype);
	$("#filename").val(encodeURI("CustomerQuery"));
	
	gridopt.gridHeader           = gridHeader;
	gridopt.gridColAlign         = "center,left,left,left,left,left,left,left,left,right,left,left,left,left";
	gridopt.gridColTypes         = "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "50,120,120,100,130,130,130,100,100,100,100,100,100,100,*";
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
	
	var param = '{org:"'+$("#org").val()+'",rtu:"'+$("#rtu").val()+'",yhbh:"'+$.trim($("#yhbh").val())+'",yhmc:"'+$.trim($("#yhmc").val())+'",lxdh:"'+$.trim($("#lxdh").val())+'",bh:"'+$.trim($("#bh").val())+'"}';
	
	gridopt.gridGetDataUrl      = def.basePath  + "ajaxdyjc/actSearch!yhxxSearch.action";
	gridopt.gridSearch			= param;
	var prs = $("#prs").val();
	gridopt.filter(prs?prs:20);
}