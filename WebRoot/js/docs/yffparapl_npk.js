var totalnums = 0;
var mygridbox = new dhtmlXGridObject('gridbox');
$(document).ready(function(){
	 initGrid();
	//预览
	$("#preview_btn").click(function(){
 		doPreview(); 
	});
	//导入数据库
	$("#impData").click(function(){
		doImport();
	});
});

function doImport(){
		if(mygridbox.getRowsNum() == 0) 
	{
		alert("没有要存库的内容！"); return
	}
 
 	var param = "{\"rows\":[";	
	 	for(var i =0; i< mygridbox.getRowsNum(); i++){
	 		if(mygridbox.cells2(i, 0).getValue() == 1){
		 	param += "{\"cardno\":\"" +mygridbox.cells2(i, 5).getValue()+"\",";
			param += "\"totbuy_times\":\"" +mygridbox.cells2(i,6).getValue()+"\",";
			param += "\"total_gdz\":\"" +mygridbox.cells2(i,7).getValue()+"\",";
			param += "\"cur_remain\":\"" +mygridbox.cells2(i,8).getValue()+"\",";
			param += "\"pay_money\":\"" +mygridbox.cells2(i,10).getValue()+"\",";
			param += "\"op_date\":\"" +mygridbox.cells2(i,11).getValue()+"\",";
			param += "\"op_time\":\"" +mygridbox.cells2(i,11).getValue()+"\"},";		
		}
 	}
	param = param.substr(0,param.length - 1);
	param += "]}";
  	$.post(basePath + "ajaxnp/actImportBD!impGDRec.action", 
		{
			result 		: param
		
		},
		function(data) {
 			if(data.result != ""){
 				var retval = data.result.toString().split(",");
 					for(var i =0; i< mygridbox.getRowsNum(); i++){
 						if(retval[i] == 1){
 							mygridbox.cells2(i,16).setValue("<font color=red>√</font>");	
 						}else{
 							mygridbox.cells2(i,16).setValue("<font color=red>X(请核对该条记录农排档案)</font>");	
 						}
 						
 					}
 					alert("操作完成，请查看导入结果！");
			}
 			else
 			{
 				alert("存库失败，请检查是否是有效数据！" );
 			}
				
		}
	);
}

function doPreview(){
	var xlsName = $("#myFile").val();
	if(xlsName == ""){
		alert("请正确选择要导入的excel文件!");
		return;
	}
	loading.loading();
	frmfile.submit();
	initGrid();
}

function initGrid(){
//	var header = "<img src='" + basePath + "images/grid/imgs/item_chk1.gif' onclick='selectAllOrNone(this);'/>," +
//			"供电单位,供电区域,IC卡号,用户号,用户名,最近一次用电时间,最近剩余金额,最近一次购电时间,最近购电金额,卡状态";
	var header = "<img src='" + basePath + "images/grid/imgs/item_chk1.gif' onclick='selectAllOrNone(this);'/>," +
 			"供电单位,供电区域,用户号,用户名,IC卡号,累计购电次数,累计购电金额,剩余金额,最后用电时间,最后购电金额,最后购电时间,开卡日期,开卡押金,销卡日期,卡状态,导入结果";
	mygridbox.setImagePath(basePath + "images/grid/imgs/");
	mygridbox.setHeader(header);
	mygridbox.setInitWidths("50,120,120,120,120,120,100,100,100,120,100,120,120,120,100,100,150,*");
	mygridbox.setColAlign("center,left,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center");
	mygridbox.setColTypes("ch,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygridbox.enableTooltips("false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false");
	mygridbox.init();
	mygridbox.enableSmartRendering(true);
	loadData();
}

function selectAllOrNone(ck) {
	var flag = false;
	if (ck.src.indexOf("item_chk0.gif")!=-1) {
		flag   = true;
		ck.src = basePath + 'images/grid/imgs/item_chk1.gif';
	}
	else if (ck.src.indexOf("item_chk1.gif")!=-1) {
		flag = false;
		ck.src = basePath + 'images/grid/imgs/item_chk0.gif';
	}
	for( var i = 0; i < totalnums; i++) {
		mygridbox.cells2(i, 0).setValue(flag);
	}
}

function loadData(){	
	 
	if(resultdata != "" && resultdata != "null"){
		var json = eval('(' + resultdata + ')');
	 	mygridbox.parse(json, "", "json");
		totalnums = mygridbox.getRowsNum();	//给全局变量数据总条数赋值
		$("#headchk").attr('src',basePath + 'images/grid/imgs/item_chk1.gif');  
	}
}