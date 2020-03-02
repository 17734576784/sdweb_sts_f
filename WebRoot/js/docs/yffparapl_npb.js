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

function doPreview(){
	var xlsName = $("#myFile").val();
	if(xlsName == ""){
		alert("请正确选择要导入的excel文件!");
		return;
	}
	loading.loading();
	frmfile.submit();
}

function initGrid(){
	var header = "<img src='" + basePath + "images/grid/imgs/item_chk1.gif' onclick='selectAllOrNone(this);'/>," +
			"供电单位,用电区域,用电台区,电表号,机井名,生产厂家,电表类型,电表状态,倍率,录入日期";
	mygridbox.setImagePath(basePath + "images/grid/imgs/");
	mygridbox.setHeader(header);
	mygridbox.setInitWidths("50,120,120,120,120,120,120,120,120,120,120,*");
	mygridbox.setColAlign("center,left,center,center,center,center,center,center,center,center,center");
	mygridbox.setColTypes("ch,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygridbox.enableTooltips("false,false,false,false,false,false,false,false,false,false,false");
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