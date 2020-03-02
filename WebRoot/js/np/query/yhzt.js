var pos = false;

$(document).ready(function(){
	
	initgrid();
	
	$("#search").click(function(){
		search();
	});
	
});

function initgrid(){
	
	var gridHeader = "序号,所属片区,客户名称,客户编号,卡号,移动电话,用户状态,操作类型,操作日期,缴费金额,结算金额,追补金额,总金额,上次剩余金额,当前剩余金额,累计购电金额,购电次数,开户日期,销户日期";
	var datatype = "int,str,str,str,str,str,str,str,str,0.00,0.00,0.00,0.00,0.00,0.00,0.00,int,str,str";
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val(datatype);
	$("#filename").val(encodeURI("农排客户状态查询"));
	
	gridopt.gridHeader           = gridHeader;
	gridopt.gridColAlign         = "center,left,left,left,left,left,left,left,left,right,right,right,right,right,right,right,right,left,left";
	gridopt.gridColTypes         = "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "50,120,120,120,120,120,120,120,150,120,120,120,120,120,120,120,120,120,120";
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
	
	gridopt.gridGetDataUrl      = def.basePath  + "ajaxnp/actSearch!yhztSearch.action";
	gridopt.gridSearch			= param;
	var prs = $("#prs").val();
	gridopt.filter(prs?prs:20);
	
}
