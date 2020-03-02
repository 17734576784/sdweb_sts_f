$(document).ready(function(){
	
	gridopt.gridHeader           = grid_title + ",&nbsp;";
	gridopt.gridColAlign         = "center,left,left,left,left,left,left";
	gridopt.gridColTypes         = "ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "50,180,80,100,100,140,140,*";
	gridopt.gridTooltips         = "false,false,false,false,false,false,false";
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridopt.gridHeader));
	$("#colType").val("int,str,str,str,str,str,str");
	var filename = "预付费时间";
	if(appType == 1) filename = "专变" + filename;
	else if(appType == 3) filename = "居民" + filename;
	$("#filename").val(encodeURI(filename));
	
	gridopt.gridGetDataUrl       = def.basePath  + "ajaxdocs/actRtuPayCtrlPara!getRtuPayCtrlPara.action?apptype="+appType;
	gridopt.gridTitleDetailParas = "id,describe,useFlag,yffbgDate,yffbgTime,sg186bgDate,sg186bgDate";
	gridopt.selectRow            = 0;	//默认选中第一行
	gridopt.treeRefresh          = true;
	gridopt.click                = false;
	gridopt.dblclick             = true;
	gridopt.gridSrndFlag		 = true;

	getDataByidFromTree();
	
});

function search() {
	getDataByidFromTree();
}

function redoOnRowDblClicked(id){
	$("#id").val(id);
	modalDialog.height = 320;
	modalDialog.width = 700;
	modalDialog.url = "yffsjDialog.jsp";
	modalDialog.param = {id : id, lang : window.top._lang, doc_per : window.top._doc_per};
	var rtnValue = modalDialog.show();
	
	if(rtnValue == undefined || rtnValue == null){
		return;
	}
	
	window.top.global_ggtz(window.top.dbUpdate.YD_DBUPDATE_TABLE_YFFCTRL);
	
	gridopt.showgrid(false, $("#gopage").val(), $("#prs").val());
	editSelectRow(rtnValue);
}
function redoOnRowSelected(json){
	
}
