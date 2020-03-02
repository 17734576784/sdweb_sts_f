var pos = false;

$(document).ready(function(){
	
	initgrid();
	
	$("#search").click(function(){
		search();
	});
	
});

function initgrid(){
	
	var gridHeader = "序号,所属片区,客户名称,客户编号,卡号,移动电话,固定电话,身份证号码,自然村名称,客户地址,邮编";
	var datatype = "int,str,str,str,str,str,str,str,str,str,str";
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val(datatype);
	$("#filename").val(encodeURI("农排客户信息查询"));
	
	gridopt.gridHeader           = gridHeader;
	gridopt.gridColAlign         = "center,left,left,left,left,left,left,left,left,left,left";
	gridopt.gridColTypes         = "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "50,120,120,120,130,130,120,120,120,180,100,*";
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
	
	var param = '{org:"'+$("#org").val()+'",area:"'+$("#area").val()+'",yhbh:"'+$.trim($("#yhbh").val())+'",yhmc:"'+$.trim($("#yhmc").val())+'",lxdh:"'+$.trim($("#lxdh").val())+'",cardno:"'+$.trim($("#cardno").val())+'"}';
	
	gridopt.gridGetDataUrl      = def.basePath  + "ajaxnp/actSearch!yhxxSearch.action";
	gridopt.gridSearch			= param;
	var prs = $("#prs").val();
	gridopt.filter(prs?prs:20);
}
