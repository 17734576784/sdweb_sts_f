var yffmanId = window.dialogArguments.yffmanId;
$(document).ready(function(){	
	initGrid();	

	$("#search").click(function(){
		search();
	});
	$("#confirm").click(function(){
		confirm();
	});
});

function initGrid(){
	
	gridopt.gridHeader           = "<img src='" + def.basePath + "images/grid/imgs/item_chk0.gif' onclick='gridopt.selectAllOrNone(this);' />,Serial No.,org,Name,&nbsp;";
	gridopt.gridColAlign         = "center,center,center,center,center";
	gridopt.gridColTypes         = "ch,ro,ro,ro,ro";
	gridopt.gridWidths           = "40,120,120,275,*";
	gridopt.gridTooltips         = "false,false,false,false,false";
	
	gridopt.gridGetDataUrl       = def.basePath  + "ajaxdocs/actRtuPara!loadAllConsparaPerssion.action";
	gridopt.redoOnRowSelected    = true;
	gridopt.selectRow			 = 0;
	gridopt.gridSearch			 = "{\"describe\":\""+$("#search_condition").val()+"\","+"\"yffId\":"+yffmanId+"}";
	gridopt.filter(100);
}

function search(){
	initGrid();
}

function confirm(){
	var meter_gird = gridopt.grid;
	var seledrow = "";
	
	for ( var i = 0; i < meter_gird.getRowsNum(); i++) {
		if(meter_gird.cells2(i, 0).getValue()==1){
			seledrow += "|" + meter_gird.getRowId(i);
		}
	}
	if(seledrow != ""){
		seledrow = seledrow.substring(1);
	}
	//添加到数据库中	需要传操作员id
	$.post(def.basePath  + "ajaxdocs/actRtuPara!insertConsparaPerssion.action",{result:"{\"consIds\":\""+seledrow+"\","+"\"yffmanId\":"+yffmanId+"}"},function(data)
	{
		if(data.result == "success"){
			alert("Définir avec succès.");
			window.returnValue = data.result;
			window.close();
		}else{
			alert("Erreur.");
		}
	});
}
