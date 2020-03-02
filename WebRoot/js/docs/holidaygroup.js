$(document).ready(function(){	
	initGrid();	
	
	$("#pldel").click(function(){
		gridopt.plDel();
	});
	$("#tianjia").click(function(){
		if(check()){gridopt.addOrEdit('add')};
	});
	$("#xiugai").click(function(){
		if(check()){gridopt.addOrEdit('edit')}
	});
	
});


function initGrid(){
//	grid = new dhtmlXGridObject('gridbox');
	gridopt.gridHeader           = "<img src='" + def.basePath + "images/grid/imgs/item_chk0.gif' onclick='gridopt.selectAllOrNone(this);' />,序号,描述,类型,&nbsp;";
	gridopt.gridColAlign         = "center,center,left,left";
	gridopt.gridColTypes         = "ch,ro,ro,ro";
	gridopt.gridWidths           = "40,50,150,100,*";
	gridopt.gridTooltips         = "false,false,false,false";
//	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridopt.gridHeader));
	$("#colType").val("int,str,int");
	$("#filename").val(encodeURI("节假日"));
	
	gridopt.gridGetDataUrl       = def.basePath  + "ajaxdocs/actHoLiDayGroup.action";
	gridopt.gridSelRowUrl        = def.basePath  + "ajaxdocs/actHoLiDayGroup!getHoLiDayGroupById.action";
	gridopt.gridAddOrEditUrl     = def.basePath  + "ajaxdocs/actHoLiDayGroup!addOrEdit.action";
	gridopt.gridDelRowUrl        = def.basePath  + "ajaxdocs/actHoLiDayGroup!delHoLiDayGroupById.action";
	gridopt.gridTitleSimpleParas = "id,describe,grouptype";
	gridopt.gridTitleDetailParas = "id,describe,grouptype";
//	gridopt.redoOnRowSelected    = true;
	gridopt.dblclick             =true;
	gridopt.selectRow			 = 0;
	gridopt.filter(10);
//	loadData();
}


//json为对选中行重新进行数据库查询返回的数据
function redoOnRowDblClicked(id){
	modalDialog.height = 650;
	modalDialog.width = 680;
	modalDialog.url = "holigitem.jsp";
	modalDialog.param = {id : id};
	var results = modalDialog.show();
}

function check() {
	if (!ckChar("describe", "描述", 64, true)) {
		return false;
	}
	return true;
}


