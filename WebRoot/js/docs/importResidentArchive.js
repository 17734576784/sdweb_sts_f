$(document).ready(function(){
	loadAllDYRtu();
	initHeader();
	
	$("#search").click(function(){
		loadExcelResidentData();
	});
});

//加载所有低压终端
function loadAllDYRtu(){
	$.post(def.basePath + "ajax/actCommon!loadRtuDY.action",{},function(data){
		if(data.result != ""){
			var array = eval('(' + data.result + ')');
			for(var i=0;i<array.length;i++){
				$("#rtuId").append("<option value="+array[i].id+">"+array[i].desc+"</option>");
			}
		}
	});
}

function loadPathName(){
	var path = $("#fileName").val();
	$("#pathName").val(path.substring(path.lastIndexOf("\\")+1));
	
	$("#name").val(path.substring(path.lastIndexOf("\\")+1));
}
//实现excel预览
function loadExcelResidentData(){
	if($("#fileName").val()=="" || $("#fileName").val()=="null"){
		alert("Please Browser Folder.");
		return;
	}
	excels.submit();
}

//导入数据到库中
function importGridDataToDB(){
	if(resultdata=="" || resultdata=="null" || mygrid.getRowsNum() == 0){
		alert("No Data,please Browser Folder And preview.");
		return;
	}
	var selectImportData = new Array();
	for(var i=1;i<=mygrid.getRowsNum();i++){
		if(mygrid.cells(i,0).getValue()== 1){
			selectImportData.push(resultdata["rows"][i-1]);
		}
	}
	
	var gridParamFormater = {};
	gridParamFormater.rows = selectImportData;

	var params = {};
	params.rtuId = $("#rtuId").val();
	
//	loading.loading();
	$.post(def.basePath + "ajaxoper/actSaveDBBengal!plAddResident.action",
		{
			result 	: jsonString.json2String(gridParamFormater),
			params  : jsonString.json2String(params)
		},
	function(data){
		
		if(data.result != ""){
			for(var i = 1; i < mygrid.getRowsNum(); i++) {
				mygrid.cells2(i, 0).setValue(false);
			}
			var insertErrorRowId = data.result.split(",");
			for(var i = 0; i < insertErrorRowId.length; i++) {
				mygrid.cells(insertErrorRowId[i],0).setValue(true);
				mygrid.setRowTextStyle(insertErrorRowId[i],"color:red;");
			}
			alert("Some data already exists.");
		}else{
			alert("Import Success!");
		}

	});
}

//初始化表格
function initHeader(){
	
	mygrid = new dhtmlXGridObject("gridbox");
	mygrid.setImagePath(def.basePath + "images/grid/imgs/");
	mygrid.setHeader("<img src='"+ def.basePath + "images/grid/imgs/item_chk1.gif' onclick='selectAllOrNone(this);' />,Cons No,Meter No,Resident Describe,Resident Address,Resident Post,Resident Phone,Resident Mobile,Resident Fax,Resident Mail,&nbsp;");
	mygrid.setInitWidths("40,100,150,150,150,150,150,150,150,150,*");
	mygrid.setColAlign("center,center,left,left,left,left,left,left,left,left,left");
	mygrid.setColTypes("ch,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.enableTooltips("false,false,false,false,false,false,false,false,false,false,false");

	mygrid.init();
	parsegrid();
}
//全选和不选
function selectAllOrNone(checked){
	var flag = false;
	if (checked.src.indexOf("item_chk0.gif") != -1) {
		flag = true;
		checked.src = def.basePath + 'images/grid/imgs/item_chk1.gif';
	} else if (checked.src.indexOf("item_chk1.gif") != -1) {
		flag = false;
		checked.src = def.basePath +  'images/grid/imgs/item_chk0.gif';
	}
	for(var i = 0; i < mygrid.getRowsNum(); i++) {
		mygrid.cells2(i, 0).setValue(flag)
	}
}

function parsegrid(){
	//如果有excel返回数据，则加载到grid中,如果没有，则只进行表格初始化
	if(resultdata!="" && resultdata!="null"){
		mygrid.parse(eval('('+resultdata+')'), "", "json");
		resultdata = eval('('+resultdata+')');
	}
	for(var i = 0; i < mygrid.getRowsNum(); i++) {
		mygrid.cells2(i, 0).setValue(true);
	}
}
