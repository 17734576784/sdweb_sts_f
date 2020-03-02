var json_data = null;
$(document).ready(function(){
	$("#pldel").show();
	getYffState();
	appType = SDDef.APPTYPE_JC;

	$("#pldel").click(function(){
		gridopt.plDel(false);
	});
	
	loadAllDYRtu();
	$("#tianjia").click(function(){
		var rtuId = $("#rtuId").val();
		if(rtuId == -1){
			alert("Veuillez sélectionner le terminal avant d'ajouter！");
			return;
		}
		redoOnRowDblClicked("," + rtuId + "," + $("#rtuId").find("option:selected").text());//逗号之前为居民id,后边为终端id,下拉框选择的终端名称
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
	modalDialog.height = 340;
	modalDialog.width = 900;
	modalDialog.url = "yffparaDialog_dyjc.jsp";
	
	modalDialog.param = {id : id, lang : window.top._lang, doc_per : window.top._doc_per};
	var rtnValue = modalDialog.show();
	if(rtnValue == undefined || rtnValue == null){
		return;
	}
	
	if(rtnValue == 1) {//添加
		gridopt.selectRow = -1;
		var cp = parseInt($("#gopage").val());
		var pr = parseInt($("#prs").val());
		var cpr = gridopt.grid.getRowsNum();
		if(pr == cpr){
			cp++
		}
		gridopt.showgrid(false,cp,pr);
		addSelectRow();
	}
	else {//修改
		gridopt.showgrid(false,$("#gopage").val(),$("#prs").val());
		editSelectRow(id);
	}
//	gridopt.showgrid(false, $("#gopage").val(), $("#prs").val());
//	editSelectRow(rtnValue);

	window.top.global_ggtz(window.top.dbUpdate.YD_DBUPDATE_TABLE_MPPAY);
}

function redoOnRowDblClicked(id){//id电表ID，
	if(id.indexOf(",") >= 0){
		openModalDialog(id);
	}else{
		var tmp = id.split("_");
		var name = gridopt.grid.cells(id,1).getValue();
		name = name.split(">")[1].split("<")[0];
		openModalDialog(tmp[1] + "," + tmp[0]);
	}
}

function onFresh() {
	getYffState();
}

function getYffState(){
	gridopt.gridHeader           = "<img src='"+def.basePath + "images/grid/imgs/item_chk0.gif' onclick='gridopt.selectAllOrNone(this);' />," +
	"								Numéro de série.,Nom,Nom du terminal,Nom résident,ID du compteur,Constructeur,Numéro d'usine,Type de compteur,Mode de facturation," +
	"								Mode de contrôle des coûts,Montant à découvert,Index tarifaire,&nbsp;";
	gridopt.gridColAlign         = "center,center,left,left,left,left,left,left,left,left";
	gridopt.gridColTypes         = "ch,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "40,60,100,100,100,100,120,120,200,100,160,100,100,*";
	gridopt.gridTooltips         = "false,false,false,false,false,false,false,false,false,false,false,false,false";
	
//	$("#vfreeze").val(1);
//	$("#hfreeze").val(0);
//	$("#header").val(encodeURI(gridopt.gridHeader));
//	$("#colType").val("int,str,str,str,str,str,str,str,str,str,int,str,str,str");
//	$("#filename").val(encodeURI("居民费控参数"));
	
	gridopt.gridGetDataUrl       = def.basePath  + "ajaxdocs/actYffPara.action?yffState=" + $("#yffState").val();
	gridopt.gridDelRowUrl        = def.basePath  + "ajaxdocs/actYffPara!delYffParaById.action";	
//	gridopt.gridTitleDetailParas = "rtuId,mpId,yffmeterType,feectrlType,caclType,feeprojId,yffalarmId,protSt,protEd,ngloprotFlag,powerRela1,powerRela2,keyVersion";
	gridopt.selectRow            = 0;	//默认选中第一行
	gridopt.beforeOnRowSelect 	 = true;
	gridopt.gridSrndFlag		 = true;
	gridopt.click                = false;
	gridopt.dblclick             = true;
	gridopt.redoOnRowSelected	 = true;
	gridopt.ggtz				 = window.top.dbUpdate.YD_DBUPDATE_TABLE_MPPAY;
	doSearch();
//	gridopt.filter(10);
//	getDataByidFromTree();
}
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
function doSearch(){
	
	gridopt.gridSearch = $("#search_condition").val() + "," + $("#rtuId").val();
	gridopt.filter(20);
}



