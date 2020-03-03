
$(document).ready(function(){	
	$("#pldel").click(function(){
		gridopt.plDel(false);
	});
	$("#tianjia").click(function(){
		redoOnRowDblClicked('');
	});	
	
	initGrid();
});

function initGrid(){
	gridopt.gridHeader           = "<img src='" + def.basePath + "images/grid/imgs/item_chk0.gif' onclick='gridopt.selectAllOrNone(this);' />," +
	"							Numéro de série.,Nom,Utiliser le drapeau,Type de terminal,Protocole,Code d'adresse,Canal principal,Montant pour résident,Montant du compteur," +
	"							Zone résidentielle,&nbsp;";
	gridopt.gridColAlign         = "center,center,left,left,left,left,right,left,right,right,left";
	gridopt.gridColTypes         = "ch,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "40,60,200,80,150,120,100,100,130,100,100,*";
	gridopt.gridTooltips         = "false,false,false,false,false,false,false,false,false,false,false";
	
	gridopt.gridGetDataUrl       = def.basePath  + "ajaxdocs/actRtuPara.action";
	gridopt.gridDelRowUrl        = def.basePath  + "ajaxdocs/actRtuPara!delRtuParaById.action";
	gridopt.gridTitleDetailParas = "id,describe,useFlag,appType,rtuModel,protType,rtuAddr,chanMain,chanBak,simcardId,residentNum,jlpNum,consId,areaCode,factory,runStatus,instSite,instMan,instDate,stopDate,runDate,madeNo,assetNo,fzcbId";
	
	gridopt.redoOnRowSelected    = true;
	gridopt.selectRow            = 0;	
	gridopt.treeRefresh          = true;
	gridopt.click                = false;
	gridopt.dblclick             = true;
	gridopt.filter(0);
}

function search() {
	gridopt.gridSearch = "{\"describe\" : \"" + $("#search_condition").val() + "\"}";
	gridopt.filter(0);
}
function redoOnRowDblClicked(id){
	modalDialog.height = 220;
	modalDialog.width = 960;
	modalDialog.url = def.basePath + "jsp/docs/rtuparaDialog.jsp";
	modalDialog.param = {id : id, lang : window.top._lang, doc_per : window.top._doc_per};
	var rtnValue = modalDialog.show();
	
	if(rtnValue == undefined || rtnValue == null){
		return;
	}
	else{
		if(rtnValue == 1) {//���
			gridopt.selectRow = -1;
			var cp = parseInt($("#gopage").val());//��ǰҳ
			var pr = parseInt($("#prs").val());//ÿҳ��¼��
			var cpr = gridopt.grid.getRowsNum();//��ǰҳ��¼��
			if(pr == cpr){
				cp++;
			}
			gridopt.showgrid(false,cp,pr);
			addSelectRow();
		}
		else {//�޸�
			gridopt.showgrid(false,$("#gopage").val(),$("#prs").val());
			editSelectRow(id);
		}
//		gridopt.filter(0); 
	}
	
}
function redoOnRowSelected(json){
	$("#id").val(json);
}
