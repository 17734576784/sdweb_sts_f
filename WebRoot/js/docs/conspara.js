$(document).ready(function(){
	appType = SDDef.APPTYPE_JC;
	initGrid();	
	$("#pldel").click(function(){
		gridopt.plDel(false);
	});
	$("#tianjia").click(function(){
		redoOnRowDblClicked('');
	});
});

function initGrid(){
	gridopt.gridHeader           = "<img src='" + def.basePath + "images/grid/imgs/item_chk0.gif' onclick='gridopt.selectAllOrNone(this);'/>," +
	"								Numéro de série.,nom,Contacts,Numéro de téléphone,Téléphone portable,Type d'alimentation," +
	"								Classe de tension,Utilitaire,Superviseur de ligne,&nbsp;";
	gridopt.gridColAlign         = "center,center,left,left,left,left,center,center,left,center";
	gridopt.gridColTypes         = "ch,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "40,60,200,100,100,100,130,100,100,100,*";
	gridopt.gridTooltips         = "false,false,true,false,false,false,false,false,false,false";

	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridopt.gridHeader));
	$("#colType").val("int,str,str,str,str,str,str,str,str");
	$("#filename").val(encodeURI("Community Files"));
	
	gridopt.gridGetDataUrl       = def.basePath  + "ajaxdocs/actConsPara.action";
	gridopt.gridDelRowUrl        = def.basePath  + "ajaxdocs/actConsPara!delConsParaById.action";
	gridopt.gridTitleDetailParas = "id,describe,addr,postalCode,fzMan,telNo1,telNo2,telNo3,powerType,voltGrade,orgId,lineFzManId,trModel,appType";
	
	gridopt.redoOnRowSelected    = true;
	gridopt.treeRefresh          = true;
	gridopt.click                = false;
	gridopt.selectRow			 = 0;
	gridopt.dblclick             = true;
	gridopt.gridSrndFlag		 = true;
	
	gridopt.filter(0);
}

function redoOnRowDblClicked(id){
	modalDialog.height = 230;
	modalDialog.width = 860;
	modalDialog.url = def.basePath + "jsp/docs/consparaDialog.jsp";
	modalDialog.param = {id : id, lang : window.top._lang, doc_per : window.top._doc_per};
	var rtnValue = modalDialog.show();
	if(rtnValue == undefined || rtnValue == null){
		return;
	}
	else{
		if(rtnValue == 1) {//娣诲姞
			gridopt.selectRow = -1;
			var cp = parseInt($("#gopage").val()); //褰撳墠椤�
			var npp = parseInt($("#prs").val()); //姣忛〉璁板綍鏁�
			var tn = gridopt.grid.getRowsNum();  //褰撳墠椤佃褰曟暟
			if(npp==tn){
			 cp++;
			}
			gridopt.showgrid(false, cp, npp);
			addSelectRow();
		}
		else {//淇敼
			gridopt.showgrid(false, $("#gopage").val(), $("#prs").val());
   			editSelectRow(id);
		}
//		gridopt.filter(0);//淇敼鎴愬姛鍒锋柊 
	}
}
function redoOnRowSelected(json){
	$("#id").val(json);
}
function openModalDialog(id){
	redoOnRowDblClicked(id);
}

function search() {
	gridopt.gridSearch = "{\"describe\" : \"" + $("#search_condition").val() + "\"}";
	gridopt.filter();
}

