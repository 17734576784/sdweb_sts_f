$(document).ready(function(){
	//初始化供电所
	$.post(def.basePath + "ajax/actCommon!getOrg.action",{},function(data){
		var json = eval('(' + data.result + ')');
		
		$("#orgname").html("");
		for ( var i = 0; i < json.length; i++) {
			$("#orgname").append("<option value="+json[i].value+">"+json[i].text+"</option>");
		}
		initConsname();
	});
	
	initHeader();
	
	$("#search").click(function(){
		loadExcelMeterData();
	});
});

//初始化居民区
function initConsname(){
	$.post(def.basePath + "ajax/actCommon!getConsByOrg.action",{value : $("#orgname").val()},function(data){
		var json = eval('(' + data.result + ')');
		
		$("#consname").html("");
		for ( var i = 0; i < json.length; i++) {
			$("#consname").append("<option value="+json[i].value+">"+json[i].text+"</option>");
		}
		initResidentname();
	});
	
}

//初始化居民
function initResidentname(){
	
	$.post(def.basePath + "ajax/actCommon!getResidentByConsid.action",{value : $("#consname").val()},function(data){
		var json = eval('(' + data.result + ')');
		
		$("#residentname").html("");
		for ( var i = 0; i < json.length; i++) {
			$("#residentname").append("<option value="+json[i].value+">"+json[i].text+"</option>");
		}
		initTariffProject();
	});	
}

//初始化费率
function initTariffProject(){
	$.post(def.basePath + "ajax/actCommon!getTariffProject.action",{},function(data){
		var json = eval('(' + data.result + ')');
		
		$("#tariffProject").html("");
		for ( var i = 0; i < json.length; i++) {
			$("#tariffProject").append("<option value="+json[i].value+">"+json[i].text+"</option>");
		}
	});
}


function loadPathName(){
	var path = $("#fileName").val();
	$("#pathName").val(path.substring(path.lastIndexOf("\\")+1));
	
	$("#name").val(path.substring(path.lastIndexOf("\\")+1));
}

//实现excel预览
function loadExcelMeterData(){
	if($("#fileName").val()=="" || $("#fileName").val()=="null"){
		alert("Veuillez dossier de navigateur.");
		return;
	}
	excels.submit();
}

//导入数据到库中
function importGridDataToDB(){
	if(resultdata=="" || resultdata=="null"){
		alert("Aucune donnée, veuillez dossier du navigateur.");
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
	params.orgId = $("#orgname").val();
	params.consId = $("#consname").val();
	params.residentId = $("#residentname").val();  //rtuId_id
	params.tariffProject = $("#tariffProject").val();
	
//	loading.loading();
	$.post(def.basePath + "ajaxoper/actSaveDBBengal!plAddNewOper.action",
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
		}else{
			alert("Importation réussie!");
		}

	});
}

//初始化表格
function initHeader(){
	
	mygrid = new dhtmlXGridObject("gridbox");
	mygrid.setImagePath(def.basePath + "images/grid/imgs/");
	mygrid.setHeader("<img src='"+ def.basePath + "images/grid/imgs/item_chk1.gif' onclick='selectAllOrNone(this);' />,Mètre Non,Tous les numéros d'instruments,Mètre Non,JETON1,JETON2,METERKEY,Type de compteur,PT,CT,&nbsp;");
	mygrid.setInitWidths("40,100,250,250,250,250,250,100,100,100,*");
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
