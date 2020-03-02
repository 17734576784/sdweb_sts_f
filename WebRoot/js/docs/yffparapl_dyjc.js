var json_data = null;
$(document).ready(function(){

	gridopt.gridHeader           = "<img src='"+def.basePath + "images/grid/imgs/item_chk0.gif' onclick='gridopt.selectAllOrNone(this);' />,"+grid_title+",&nbsp;";
	gridopt.gridColAlign         = "center,center,left,left,left,left,left,left,left,left,right,left,left";
	gridopt.gridColTypes         = "ch,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "50,50,100,150,100,80,80,80,120,120,80,90,*";
	gridopt.gridTooltips         = "false,false,false,false,false,false,false,false,false,false,false,false,false";

	gridopt.gridGetDataUrl       = def.basePath  + "ajaxdocs/actYffPara!getyffparapl.action";
	gridopt.gridTitleDetailParas = "rtuId,mpId,yffmeterType,feectrlType,caclType,feeprojId,yffalarmId,protSt,protEd,keyVersion,ngloprotFlag";
	gridopt.selectRow            = 0;	//默认选中第一行
	gridopt.beforeOnRowSelect 	 = true;
	gridopt.click                = false;
	gridopt.dblclick             = true;
	gridopt.redoOnRowSelected	 = true;
	
	getDataByidFromTree();
	
	appType = SDDef.APPTYPE_JC;
	
	$("#search_condition").keypress(function(){
		if(window.event.keyCode == 13)search();
	});
});
function redoOnRowSelected(id){
	$("#id").val(id);
}
function beforeOnRowSelect(id)
{
	var rid = window.top.rightFrame.rtuid;
	gridopt.selectids = "{\"id\" : \"" + id + "\",\"fatherid\":\"" + rid + "\"}";
}

function search() {

	getDataByidFromTree();

}

function openModalDialog(id){//id格式rtuId_mpId
	var sel_rows = "";
	var selId=gridopt.grid.getSelectedId();//光标是否选中这一行
	var selTemp
	for(var i = 0; i < gridopt.grid.getRowsNum(); i++){
		if(gridopt.grid.cells2(i, 0).getValue() == 1){
			sel_rows += "," + gridopt.grid.getRowId(i);
		}
	}
	if(sel_rows != SDDef.EMPTY){
		sel_rows = sel_rows.substring(1);	
	}
	if(sel_rows == SDDef.EMPTY){
		alert(update_cond);
		return;
	}
	modalDialog.height = 410;
	modalDialog.width = 830;
	modalDialog.url = "yffparaDialogpl_dyjc.jsp";
	modalDialog.param = {id : id, sel_rows : sel_rows, lang : window.top._lang, doc_per : window.top._doc_per};
	var rtnValue = modalDialog.show();
	if(rtnValue == undefined || rtnValue == null){
		return;
	}
	
	for(var i = 0; i < gridopt.grid.getRowsNum(); i++){
		if(gridopt.grid.cells2(i, 0).getValue() == 1){
			for(var j = 0; j < rtnValue.length; j++){
				if(rtnValue[j] == null) continue;
				gridopt.grid.cells2(i,4+j).setValue(rtnValue[j]);
			}
		}
	}
	
	window.top.global_ggtz(window.top.dbUpdate.YD_DBUPDATE_TABLE_MPPAY);
	
}
function redoOnRowDblClicked(id){//id格式rtuId_mpId  id是grid自动获取的
	
		openModalDialog(id);
}
