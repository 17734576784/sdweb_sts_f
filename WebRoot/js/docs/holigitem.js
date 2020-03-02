var grid;
var param = window.dialogArguments;
var id=param.id;
$(document).ready(function(){	
	initGrid();	
	
	$("#pldel").click(function(){
		gridopt.plDel();
	});
	$("#plxiugai").click(function(){
		pledit();
	});
	$("#xiugai").click(function(){
		if(check()){gridopt.addOrEdit('edit')}
	});
	
});


function initGrid(){
	grid = new dhtmlXGridObject('gridbox');
	gridHeader           = "<img src='" + def.basePath + "images/grid/imgs/item_chk0.gif' onclick='selectAllOrNone(this);' />,序号,节假日描述,节假日组描述,&nbsp;";
	gridColAlign         = "center,center,left,left";
	gridColTypes         = "ch,ro,ro,ro";
	gridWidths           = "40,50,150,150,*";
	gridTooltips         = "false,false,false,false";
//	
	
	grid.setImagePath(def.basePath + "images/grid/imgs/");
	grid.setHeader(gridHeader);
	grid.setInitWidths(gridWidths);
	grid.setColAlign(gridColAlign);
	grid.setColTypes(gridColTypes);
	grid.enableTooltips(gridTooltips);
	grid.init();
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridopt.gridHeader));
	$("#colType").val("int,str,str");
	$("#filename").val(encodeURI("节假日"));
	
//	gridopt.gridGetDataUrl       = def.basePath  + "ajaxdocs/actHoLiDayGroup.loadItem.action";
//	gridopt.gridSelRowUrl        = def.basePath  + "ajaxdocs/actHoLiDayGroup!getHoLiDayGroupById.action";
//	gridopt.gridAddOrEditUrl     = def.basePath  + "ajaxdocs/actHoLiDayGroup!addOrEdit.action";
//	gridopt.gridDelRowUrl        = def.basePath  + "ajaxdocs/actHoLiDayGroup!delHoLiDayGroupById.action";
//	gridopt.redoOnRowSelected    = true;
//	gridopt.selectRow			 = 0;
//	gridopt.filter(10);
	loadData();
}
function loadData(){
	grid.clearAll();
	
	$.post(basePath + "ajaxdocs/actHoLiDayGroup!loadItem.action", {
		result : id
	}, function(data){
		if(data.result != ''){
			var json = eval('(' + data.result +')');
			grid.parse(json,'','json');
			grid.selectRow(0);
		}
		else{
			alert('没有数据!');
		}
	});
}
function pledit(){
	var seledrow = "";
	for ( var i = 0; i < grid.getRowsNum(); i++) {
			if(this.grid.cells2(i, 0).getValue()==1){
				seledrow += "," + this.grid.getRowId(i);
			}
		}
	if(confirm("确定要修改吗")){
		loading.loading();
		seledrow=seledrow.substring(1);
		$.post(basePath + "ajaxdocs/actHoLiDayGroup!plEdit.action", {
			result : id,field:seledrow
		}, function(data){
			loading.loaded();
			if(data.result == 'faile'){
				alert("修改失败");
				return;
			}else{
				alert("修改成功");
			}			
			loadData();
		});
	}
	
}
//json为对选中行重新进行数据库查询返回的数据
function redoOnRowlClicked(json){
	return;
}
function selectAllOrNone(checked) {      //全选&全不选
		var flag = false;
		if (checked.src.indexOf("item_chk0.gif") != -1) {
			flag = true;
			checked.src = def.basePath + 'images/grid/imgs/item_chk1.gif';
		} else if (checked.src.indexOf("item_chk1.gif") != -1) {
			flag = false;
			checked.src = def.basePath +  'images/grid/imgs/item_chk0.gif';
		}
		for ( var i = 0; i < grid.getRowsNum(); i++) {
			grid.cells2(i, 0).setValue(flag)
		}
	}



