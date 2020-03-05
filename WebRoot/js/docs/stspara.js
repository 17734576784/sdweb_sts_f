$(document).ready(function(){
	
	initDiction();
 	initGrid();
	
	$("#pldel").click(function(){
		gridopt.plDel();
	});
	$("#tianjia").click(function(){
		//if(confirm("Modification of 'SGC' or 'Key Type' or 'Key Regno' or 'Encrypt Type' parameters will affect the payment operation.")){
			if(check()){ 
				clearKFM();
				gridopt.addOrEdit('add')
			};		
		//}
	});
	$("#xiugai").click(function(){
		if(confirm("Modification of 'SGC' or 'Key Type' or 'Key Regno' or 'Encrypt Type' parameter will affect the payment operation.")){
			if(check()){ 
				clearKFM();
				gridopt.addOrEdit('edit')
			}
		}
	});	
	$("#ET").click(function(){
		isDisplayKr();
	});
 });

//添加或修改时，根据Encrypt Type选项，清空KR和KMF
function clearKFM(){
	if($("#ET").val()=="0"){
		$("#KR").val("");
		$("#KMF").val("");
	}
}

function initDiction(){
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : '{item:["'+Dict.DICTITEM_FEELVTYPE+'"]}'},function(data){
		
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			
			var dict = eval("json.rows[0]."+Dict.DICTITEM_FEELVTYPE);
			for ( var i = 0; i < dict.length; i++) {
				$("#feeType").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			}	
		}
	});
}
function initGrid(){
	gridopt.gridHeader           = "<img src='"+ def.basePath + "images/grid/imgs/item_chk0.gif' onclick='gridopt.selectAllOrNone(this);' />,Numéro de série.,Nom de l'utilitaire,Code de groupe d'approvisionnement (SGC),Adresse,code postal,speaker,numéro de téléphone,Type de clé (KT),Algorithme de génération de clé de décodeur (DKGA),Clé de vente1(VK1),Clé de vente2(VK2),Clé de vente3(VK3),Clé de vente4(VK4),&nbsp;";
	gridopt.gridColAlign         = "center,center,left,left,left,left,left,left,left,left,left,left,left,left";
	gridopt.gridColTypes         = "ch,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "40,50,200,150,150,150,100,100,100,150,100,100,100,100,*";
	gridopt.gridTooltips         = "false,false,true,false,false,false,false,false,false,false,false,false,false,false";
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridopt.gridHeader));
	$("#colType").val("int,int,str,str,str,str,0.00,0.00,0.00,0.00,0.00,str,int,str,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,int,int");
	$("#filename").val(encodeURI("Projet tarifaire"));
	
	gridopt.gridGetDataUrl       = def.basePath  + "ajaxdocs/actOrgStsPara.action";
	gridopt.gridSelRowUrl        = def.basePath  + "ajaxdocs/actOrgStsPara!getorgstsparaById.action";
	gridopt.gridAddOrEditUrl     = def.basePath  + "ajaxdocs/actOrgStsPara!addOrEdit.action";
	gridopt.gridDelRowUrl        = def.basePath  + "ajaxdocs/actOrgStsPara!delorgstsparaById.action";
	gridopt.selectRow            = 0;
	gridopt.ggtz				 = window.top.dbUpdate.YD_DBUPDATE_TABLE_FEERATE;
	gridopt.gridTitleSimpleParas = "Utility Name,Supply Group Code(SGC),Address,postalCode,linkMan,telNo,id,SGC,KT,DKGA,VK1,VK2,VK3,VK4";
	gridopt.gridTitleDetailParas = "id,describe,rorgNo,addr,postalCode,linkMan,telNo,KT,DKGA,ET,VK1,VK2,VK3,VK4,KR,KMF";
	gridopt.filter(100);
	gridopt.redoOnRowSelected    = true;

	showhide();
}

function isDisplayKr(){
	if($("#ET").val()=="0"){
		$("#KR").css("display","none");
		$("#KMF").hide();
		$("#KRDesc").text("");
	}else{
		$("#KR").css("display","block");
		$("#KMF").show();
		$("#KRDesc").text("Key Regno: ");
	}

}

function showhide(){	
	$("#gridbox").height($(window).height()-$("#showmore").height()- 35);	
	var rid = gridopt.grid.getSelectedId();
	gridopt.showgrid(true);
	gridopt.grid.selectRow(gridopt.grid.getRowIndex(rid))
}
function redoOnRowSelected(json){
	var rowsplit = gridopt.gridTitleDetailParas.split(SDDef.SPLITCOMA);	
	for(var i = 0; i < rowsplit.length; i ++){
 		$("#"+rowsplit[i]).val(json.data[i]);
	}
	isDisplayKr();
}

function check() {
	if (!ckChar("describe", name, 64, true)) {
		return false;
	}
	if (!isNumber("rorgNo", "Supply Group Code(SGC)", 1,999999, true)) {
		return false;
	}
	
	if(!isDbl("KT", zfl)){
		return false;
	}
	if(!isDbl("DKGA", ffl)){
		return false;
	}
	if(!ckChar("VK1", pfl)){
		return false;
	}
	if(!ckChar("VK2", gfl)){
		return false;
	}
	if(!ckChar("VK3", gfl)){
		return false;
	}
	if(!ckChar("VK4", gfl)){
		return false;
	}
	
	return true;
}
