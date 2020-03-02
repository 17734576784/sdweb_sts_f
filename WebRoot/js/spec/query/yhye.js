var pos = false;

$(document).ready(function(){
	
	initgrid();
	
	$("#search").click(function(){
		search();
	});
	
});

function initgrid(){
	var gridHeader = "序号,所属供电所,费控用户,用户电话,断电金额/表底,起始日期,	起始总表底,起始尖表底,起始峰表底,起始平表底,起始谷表底,算费数据日期,算费总表底,算费尖表底,算费峰表底,算费平表底,算费谷表底,当前剩余,报警金额/表底,信息输出,&nbsp;";
	var datatype = "int,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str";
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val(datatype);
	$("#filename").val(encodeURI("高压用户余额查询"));
	
	gridopt.gridHeader           = gridHeader;
	gridopt.gridColAlign         = "center,left,left,left,right,left,left,left,left,left,left,left,left,left,left,left,left,right,right,left";
	gridopt.gridColTypes         = "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "50,120,150,100,100,100,120,120,120,120,120,100,120,120,120,120,120,100,100,300,*";
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
	//alert(param)
	gridopt.gridGetDataUrl  = def.basePath  + "ajaxspec/actSearch!yhyeSearch.action";
	gridopt.gridSearch		= param;
	
	var prs = $("#prs").val();
	gridopt.filter(prs?prs:20);
	
}