$(document).ready(function(){
	appType = SDDef.APPTYPE_JC;	
	initGrid();	
	$("#tianjia").show();
	$("#xiugai").show();
	$("#pldel").show();
	$("#pldel").click(function(){
		gridopt.plDel(false);
	});
	
	loadAllDYRtu();
	
	$("#tianjia").click(function(){
		var rtuId = $("#rtuId").val();
		if(rtuId == -1){
			alert("Please select the terminal before add！");
			return;
		}
		redoOnRowDblClicked("," + rtuId);//逗号之前为居民id,后边为终端id。
	});
	
});

function initGrid() {

	gridopt.gridHeader           = "<img src='"+def.basePath + "images/grid/imgs/item_chk0.gif' onclick='gridopt.selectAllOrNone(this);' />,Serial No.,Name,Terminal Name,Consumer ID,Address,Post Code,Telephone,Mobile Phone,Fax,E-mail,&nbsp;";
	gridopt.gridColAlign         = "center,center,left,left,left,center,left,left,left,left,left";
	gridopt.gridColTypes         = "ch,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "40,60,120,200,120,100,100,80,100,90,100,*";
	gridopt.gridTooltips         = "false,false,false,false,false,false,false,false,false,false,false";
	gridopt.gridGetDataUrl       = def.basePath  + "ajaxdocs/actResidentPara.action";
	gridopt.gridAddOrEditUrl     = def.basePath  + "ajaxdocs/actResidentPara!addOrEdit.action";
	gridopt.gridDelRowUrl        = def.basePath  + "ajaxdocs/actResidentPara!delResidentParaById.action";
	gridopt.gridTitleDetailParas = "id,describe,consNo,address,post,phone,mobile,fax,mail,rtuId";
	gridopt.treeRefresh          = true;
	gridopt.click                = false;
	gridopt.dblclick             = true;
	search();
}

//加载所有低压终端
function loadAllDYRtu(){
	$.post(def.basePath + "ajax/actCommon!loadRtuDY.action",{},function(data){
		if(data.result != ""){
			var array = eval('(' + data.result + ')');
			for(var i=0;i<array.length;i++){
				$("#rtuId").append("<option value="+array[i].id+">"+array[i].desc+"</option>");
			}
		}
	});
}

function redoOnRowDblClicked(id){
	if(!(id.indexOf(",")>=0)){
		var diapara = gridopt.grid.cells(id,2).getValue();	//获得传给弹出窗口的id
		id = diapara .split('"')[1].split('"')[0];
	}

	$("#id").val(id);
	var ids=id;
	modalDialog.height = 190;
	modalDialog.width = 700;
	modalDialog.url = def.basePath + "jsp/docs/rtuparaResidentparaDialog.jsp";
	modalDialog.param = {id : ids};
	var rtnValue = modalDialog.show();
	if(rtnValue == undefined || rtnValue == null){
		return;
	}
	var flag = rtnValue.substring(0,1);
	rtnValue = rtnValue.substring(1);
	if(flag == "1"){	//添加
		
//		window.top.leftFrame.treeLD(rtnValue,"rtu");	//定位树节点,参数：当前节点id，节点描述，节点名称
		gridopt.selectRow = -1;
		var cp = parseInt($("#gopage").val());	//当前页
		var npp = parseInt($("#prs").val());	//每页记录数
		var tn = gridopt.grid.getRowsNum();		//当前页记录数
		if(npp==tn){
			cp++;
		}
		gridopt.showgrid(false, cp, npp);
		addSelectRow();
	}
	else if(flag == "0"){ 	//修改
		rtnValue = rtnValue.split(",");
//		if(rtnValue[2]=="0"){	//所属居民区改变时树定位到修改的节点同时页面中只显示刚修改过的记录
//			
////			window.top.leftFrame.treeLD(rtnValue[1]+","+rtnValue[0]+","+rtnValue[3],"rtu","edit");
//			
//		}else{					//所属居民区未改变时只刷新页面及改变树节点名称
		gridopt.showgrid(false, $("#gopage").val(), $("#prs").val());
		editSelectRow(id.replace(",","_"));
//		}
	}
}

function search(){
	gridopt.gridSearch = "{\"describe\" : \"" + $("#search_condition").val() + "\", \"rtuid\" : \"" + $("#rtuId").val() + "\"}";
	gridopt.filter(0);
}