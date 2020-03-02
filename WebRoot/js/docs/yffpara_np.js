var json_data = null;
$(document).ready(function(){
	getYffState();
	appType = SDDef.APPTYPE_NP;
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
	modalDialog.height = 280;
	modalDialog.width = 900;
	modalDialog.url = "yffparaDialog_np.jsp";
	
	modalDialog.param = {id : id, lang : window.top._lang, doc_per : window.top._doc_per};
	var rtnValue = modalDialog.show();
	if(rtnValue == undefined || rtnValue == null){
		return;
	}
	
	gridopt.showgrid(false, $("#gopage").val(), $("#prs").val());
	editSelectRow(rtnValue);

	window.top.global_ggtz(window.top.dbUpdate.YD_DBUPDATE_TABLE_MPPAY);
	
}

function redoOnRowDblClicked(id){//id电表ID，
	if(id.indexOf(",") >= 0){
		openModalDialog(id);
	}else{
		var tmp = id.split("_");
		var name = gridopt.grid.cells(id,2).getValue();
		name = name.split(">")[1].split("<")[0];
		openModalDialog(tmp[1] + "," + tmp[0] + "," + name);
	}
}

function onFresh() {
	getYffState();
}

function getYffState(){
	//gridopt.gridHeader           = grid_title + ",&nbsp;";
	
	gridopt.gridHeader           = "序号,终端名称,农排表名称,限定功率,无采样自动断电功能,无采样自动断电时间,上电保护功能,密钥版本,所属加密机ID";
	gridopt.gridColAlign         = "center,left,left,left,left,left,left,left,left";
	gridopt.gridColTypes         = "ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "50,150,150,100,150,150,100,100,100,*";
	gridopt.gridTooltips         = "false,false,false,false,false,false,false,false,false";
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridopt.gridHeader));
	$("#colType").val("int,str,str,int,str,str,str,int,int");
	$("#filename").val(encodeURI("农排费控参数"));
	
	gridopt.gridGetDataUrl       = def.basePath  + "ajaxdocs/actYffParaNP.action?yffState=" + $("#yffState").val();
	gridopt.gridTitleDetailParas = "rtuId,mpId,pow_limit,nocycut_flag,nocycut_min,powerup_prot,key_version,cryplink_id";
	gridopt.selectRow            = 0;	//默认选中第一行
	gridopt.beforeOnRowSelect 	 = true;
	gridopt.gridSrndFlag		 = true;
	gridopt.click                = false;
	gridopt.dblclick             = true;
	gridopt.redoOnRowSelected	 = true;
//	gridopt.ggtz				 = dbUpdate.YD_DBUPDATE_TABLE_MPPAY;
	getDataByidFromTree();
}


