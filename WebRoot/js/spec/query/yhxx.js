var pos = false;

$(document).ready(function(){
	
	initgrid();
	
	$("#search").click(function(){
		search();
	});
	
});

function initgrid(){
	var gridHeader = "序号,供电所,客户编号,客户名称,客户地址,联系电话,费控单元,&nbsp;";
	var datatype = "int,str,str,str,str,str,str";
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val(datatype);
	$("#filename").val(encodeURI("高压用户信息查询"));
	
	gridopt.gridHeader           = gridHeader;
	gridopt.gridColAlign         = "center,left,left,left,left,left,left";
	gridopt.gridColTypes         = "ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "50,120,120,100,130,130,100,*";
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
	
	var param = '{org:"'+$("#org").val()+'",rtu:"'+$("#rtu").val()+'",yyhh:"'+$.trim($("#yyhh").val())+'",khmc:"'+$.trim($("#khmc").val())+'",lxdh:"'+$.trim($("#lxdh").val())+'",fkdy:"'+$.trim($("#fkdy").val())+'"}';
	
	gridopt.gridGetDataUrl      = def.basePath  + "ajaxspec/actSearch!yhxxSearch.action";
	gridopt.gridSearch			= param;
	
	var prs = $("#prs").val();
	gridopt.filter(prs?prs:20);
	
}