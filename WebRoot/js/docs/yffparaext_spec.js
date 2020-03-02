
$(document).ready(function(){
	gridopt.gridHeader           = grid_title + ",&nbsp;";
	//序号,户名,户号,供电电源,月均电量(万kWh),月均电费(万元),用电类别,电量比例(%),电能表误差,终端编号,用电联系人,用电联系电话,财务联系人,财务联系电话
	gridopt.gridColAlign         = "center,left,left,left,right,right,left,right,left,left,left,left,left,left";
	
	gridopt.gridColTypes         = "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "40,200,80,80,100,100,80,80,90,80,80,90,80,90,*";
	gridopt.gridTooltips         = "false,false,false,false,false,false,false,false,false,false,false";
	
	gridopt.gridGetDataUrl       = def.basePath  + "ajaxdocs/actYffParaExt.action";
	//gridopt.gridTitleDetailParas = "";
	gridopt.selectRow            = 0;	//默认选中第一行
	gridopt.beforeOnRowSelect 	 = true;
	gridopt.click                = false;
	gridopt.dblclick             = true;
	gridopt.redoOnRowSelected	 = true;
	
	getDataByidFromTree();
	
	appType = SDDef.APPTYPE_ZB;
});

function beforeOnRowSelect(id)
{
	var rid = window.top.rightFrame.rtuid;
	gridopt.selectids = "{\"id\" : \"" + id + "\",\"fatherid\":\"" + rid + "\"}";
}
function redoOnRowSelected(json){
	$("#id").val(json);
}

function search() {
	
	getDataByidFromTree();
	
}
function openModalDialog(id){
	
	modalDialog.height = 530;
	modalDialog.width = 900;
	modalDialog.url = "yffparaextDialog_spec.jsp";
	
	modalDialog.param = {id : id, lang : window.top._lang, doc_per : window.top._doc_per};
	var rtnValue = modalDialog.show();
	
	if(rtnValue == undefined || rtnValue == null){
		return;
	}
	gridopt.showgrid(false, $("#gopage").val(), $("#prs").val());
	editSelectRow(rtnValue);
	
	window.top.global_ggtz(window.top.dbUpdate.YD_DBUPDATE_TABLE_ZJGPAY);
	
}
function redoOnRowDblClicked(id){//id电表ID，
	var tmp = id.split(",");
	var len = tmp.length;
	
	if(len > 2){
		openModalDialog(id);
	}else{
		var tmp = id.split(",");
		var name = gridopt.grid.cells(id,1).getValue();
		name = name.split(">")[1].split("<")[0];
		openModalDialog(tmp[0] + "," + tmp[1] + "," + name);
	}
}

