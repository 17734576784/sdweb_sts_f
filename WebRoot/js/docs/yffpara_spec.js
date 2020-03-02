
$(document).ready(function(){

	gridopt.gridHeader           = grid_title + ",&nbsp;";
	gridopt.gridColAlign         = "center,left,left,left,left,left,left,left,left,left,right,right,right";
	gridopt.gridColTypes         = "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "40,145,70,70,70,100,120,80,80,90,80,80,80,*";
	gridopt.gridTooltips         = "false,false,false,false,false,false,false,false,false";
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridopt.gridHeader));
	$("#colType").val("int,str,str,str,str,str,str,str,str,str,0.000,0.000,0.000");
	$("#filename").val(encodeURI("专变费控参数"));
	
	gridopt.gridGetDataUrl       = def.basePath  + "ajaxdocs/actYffParaZ.action";
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
	gridopt.selectids = "{\"id\" : \"" + id + "\"}";
}
function redoOnRowSelected(json){
	$("#id").val(json);
}

function search() {
	
	getDataByidFromTree();
	
}
function openModalDialog(id){

	//如果为快速建档type=='1'，链接至yffparafast_spec.jsp
	if(type=='1'){	
		modalDialog.height = 640;
		modalDialog.width = 900;
		modalDialog.url = "yffparafast_spec.jsp";
	}
	else{
		modalDialog.height = 560;
		modalDialog.width = 900;
		modalDialog.url = "yffparaDialog_spec.jsp";
	}
	modalDialog.param = {id : id, lang : window.top._lang, doc_per : window.top._doc_per};
	var rtnValue = modalDialog.show();
	
	if(rtnValue == undefined || rtnValue == null){
		return;
	}
	gridopt.showgrid(false, $("#gopage").val(), $("#prs").val());
	editSelectRow(id);

	window.top.global_ggtz(window.top.dbUpdate.YD_DBUPDATE_TABLE_ZJGPAY);
}

function redoOnRowDblClicked(id){//id电表ID，
	if(id == undefined) {
		id = gridopt.grid.getSelectedRowId();
	}
	var tmp = id.split(",");
	var len = tmp.length;
	
	openModalDialog(id);
}

