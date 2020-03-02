$(document).ready(function(){
	
	gridopt.gridHeader           = "<img src='"+def.basePath + "images/grid/imgs/item_chk0.gif' onclick='gridopt.selectAllOrNone(this);' />,"+grid_title+",&nbsp;";
	gridopt.gridColAlign         = "center,center,left,left,left,left,left,left,left,left,center,center,center";
	gridopt.gridColTypes         = "ch,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "40,40,145,70,70,70,120,80,80,90,80,80,80,*";
	gridopt.gridTooltips         = "false,false,false,false,false,false,false,false,false,false";
	
	gridopt.gridGetDataUrl       = def.basePath  + "ajaxdocs/actYffParaZ.action?pl=pl";
	gridopt.gridTitleDetailParas = "rtuId,zjgId,caclType,feectrlType,payType,yffalarmId,protSt,protEd,mainsaveFlag,payAdd1,payAdd2,payAdd3,useGfh,hfhTime,hfhShutdown,plusTime,csStand";
	gridopt.selectRow            = 0;	//默认选中第一行
	gridopt.beforeOnRowSelect 	 = true;
	gridopt.click                = false;
	gridopt.dblclick             = true;
	gridopt.redoOnRowSelected	 = true;
	gridopt.gridSrndFlag		 = true;
	getDataByidFromTree();

	appType = SDDef.APPTYPE_ZB;
});

function beforeOnRowSelect(id)
{
	var rid = window.top.rightFrame.rtuid;
	gridopt.selectids = "{\"id\" : \"" + id + "\",\"fatherid\":\"" + rid + "\"}";
}
function redoOnRowSelected(id){
	$("#id").val(id);
}

function search() {
	
	getDataByidFromTree();
	
}
function openModalDialog(id){
	
	var sel_rows = "";
	for(var i = 0; i < gridopt.grid.getRowsNum(); i++) {
		if(gridopt.grid.cells2(i, 0).getValue() == 1) {
			sel_rows += "_" + gridopt.grid.getRowId(i);
		}
	}
	if(sel_rows != SDDef.EMPTY){
		sel_rows = sel_rows.substring(1);	
	}
	if(sel_rows == SDDef.EMPTY){
		alert(update_cond);
		return;
	}
	
	modalDialog.height = 530;
	modalDialog.width = 900;
	modalDialog.url = "yffparaDialogpl_spec.jsp";
	
	modalDialog.param = {id : id, pl : sel_rows, lang : window.top._lang, doc_per : window.top._doc_per};
	var rtnValue = modalDialog.show();
	
	if(rtnValue == undefined || rtnValue == null){
		return;
	}
	
	loading.loading();
	window.setTimeout("loading.loaded()", 500);
	for(var i = 0; i < gridopt.grid.getRowsNum(); i++){
		if(gridopt.grid.cells2(i, 0).getValue() == 1){
			for(var j = 0; j < rtnValue.length; j++){
				if(rtnValue[j] == null) continue;
				gridopt.grid.cells2(i, 3 + j).setValue(rtnValue[j]);
			}
		}
	}
	
	window.top.global_ggtz(window.top.dbUpdate.YD_DBUPDATE_TABLE_ZJGPAY);
}
function redoOnRowDblClicked(id){//id电表ID，
	if(!id)return;
	openModalDialog(id);
}

