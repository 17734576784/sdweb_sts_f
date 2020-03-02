var json_data = null;
$(document).ready(function(){
	getYffState();
	appType = -1;
	$("#search_condition").keypress(function(){
		if(window.event.keyCode == 13)search();
	});
});

function redoOnRowSelected(id){
	$("#id").val(id);
}
function beforeOnRowSelect(id)
{
	gridopt.selectids = "{\"id\" : \"" + id + "\"}";
}

function search() {
	//tree.js中函数
	getDataByidFromTree();
}

function openModalDialog(id){
	modalDialog.height = 300;
	modalDialog.width = 840;
	modalDialog.url = "areaparaDialog.jsp";
	
	modalDialog.param = {id : id, lang : window.top._lang, doc_per : window.top._doc_per, org_desc : gridopt.grid.cells(id, 2).getValue()};
	var rtnValue = modalDialog.show();
	if(rtnValue == undefined || rtnValue == null){
		return;
	}
	
	gridopt.showgrid(false, $("#gopage").val(), $("#prs").val());
	editSelectRow(rtnValue);
}

function redoOnRowDblClicked(id){
	openModalDialog(id);
}

function onFresh() {
	getYffState();
}

function getYffState(){
	gridopt.gridHeader           = "序号,名称,所属供电所,费率方案,报警方案,费率启用日期,区域号,接口编码1,接口编码2,&nbsp;"
	gridopt.gridColAlign         = "center,left,left,left,left,left,left,left,left";
	gridopt.gridColTypes         = "ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "50,150,150,130,120,120,120,120,120,*";
	gridopt.gridTooltips         = "false,false,false,false,false,false";
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridopt.gridHeader));
	$("#colType").val("int,str,str,str,str,str,str,str,str");
	$("#filename").val(encodeURI("片区费控参数"));
	
	gridopt.gridGetDataUrl       = def.basePath  + "ajaxdocs/actAreaPara.action";
	gridopt.redoOnRowSelected    = true;
	gridopt.selectRow            = 0;	//默认选中第一行
	gridopt.treeRefresh          = true;
	gridopt.click                = false;
	gridopt.dblclick             = true;
	gridopt.ggtz				 = window.top.dbUpdate.YD_DBUPDATE_TABLE_AREAPARA;
	getDataByidFromTree();
}
