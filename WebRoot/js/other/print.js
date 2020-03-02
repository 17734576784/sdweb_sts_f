
var gridparam = {
	ImagePath	: def.basePath + "images/grid/imgs/",
	Header 		: "ID,Utiliser le drapeau,Nom,Nom du fichier modèle,Décris,Défaut",	// 
	InitWidths	: "80,80,150,200,*,100",//300,
	ColAlign	: "center,center,left,left,left,center", 
	ColTypes	: "ro,coro,ro,ro,ro,ro" 
};
var readyflag = true;
//,new dhtmlXGridObject('gridbox3'),new dhtmlXGridObject('gridbox4'),new dhtmlXGridObject('gridbox5'),new dhtmlXGridObject('gridbox6'),new dhtmlXGridObject('gridbox7'),new dhtmlXGridObject('gridbox8')];

$(document).ready(function(){
	loading.loading();
	var otime = 0;
 	for(var i=1;i< window.top.WebPrint.nodeName.length;i++){
 		if(showlist[i] == "blank"){
			otime = otime + 100;
			$("#preview" + i).click(function(){preview(this.id)});
			$("#default" + i).click(function(){setdefault(this.id)});
 			window.setTimeout("initmyGrid(mygridlist[" + i + "],'" + window.top.WebPrint.nodeName[i] + "')",otime);
		}
	}
	window.setTimeout("loading.loaded()",200);
    window.setTimeout("markdefault()",900);
});

function preview(id){
	var idx = id.substring(id.length-1);	
	var selId = mygridlist[idx].getSelectedId();
	if(selId==null){
		alert("Veuillez sélectionner un enregistrement!");	
		return;
	}
	
	var filename = mygridlist[idx].cells(selId,3).getValue();

	loading.loading();
	$.post(def.basePath + "ajax/actPrint!getTestPrintData.action",{result:filename},function(data){
		loading.loaded();
		if(data.result != ""){
			window.top.WebPrint.loadflag = 1;
			window.top.WebPrint.inputparam = data.result;
			window.top.WebPrint.showDialog();
		}else{
			window.top.WebPrint.loadflag = 2;
			alert(data.errMsg);
		}
	});
}

function setdefault(id){
	var idx = id.substring(id.length-1);	
	var selId = mygridlist[idx].getSelectedId();
	if(selId==null){
		alert("Veuillez sélectionner un enregistrement.");	
		return;
	}
	var filename = mygridlist[idx].cells(selId,3).getValue();
	window.top.WebPrint.saveCookie(idx,filename);
	if(filename == window.top.cookie.get(window.top.WebPrint.cookieName[idx])){
		for(var j = 0; j < mygridlist[idx].getRowsNum(); j++){
			mygridlist[idx].cells2(j,5).setValue("")
		}
		mygridlist[idx].cells(selId,5).setValue("Currently in use.")
		alert("Définir avec succès.");		
	}else{
		alert("L'installation a échoué, veuillez réinitialiser.");		
	}
	
}

function initmyGrid(mygrid,nodename){
 	mygrid.setImagePath(gridparam.ImagePath);
	mygrid.setHeader(gridparam.Header);
   	mygrid.setInitWidths(gridparam.InitWidths);
   	mygrid.setColAlign(gridparam.ColAlign);
   	mygrid.setColTypes(gridparam.ColTypes);
	mygrid.setColumnIds("id,useflag,text,filename,desc,''");
	mygrid.getCombo(1).put(1, "Enabled");
	mygrid.getCombo(1).put(0, "Disabled");
	mygrid.xml.top=nodename;
	mygrid.xml.row="./item";
	//mygrid.xml.row="./item[@useflag=1]";
	mygrid.init();
	mygrid.setSkin("light");
	mygrid.load(def.basePath + "print-loc/print-config.xml",function(){
	for(var i = 0; i < mygrid.getRowsNum(); i++)
		mygrid.cells2(i, 1).setDisabled(true);	
	},"xmlA");
	
}

function markdefault(){
 	for(var i = 1 ; i < window.top.WebPrint.cookieName.length ; i++){
		var filename = window.top.cookie.get(window.top.WebPrint.cookieName[i]);
 		for(var j = 0; j < mygridlist[i].getRowsNum(); j++){
			if(filename == mygridlist[i].cells2(j,3).getValue()){
				//alert(filename)
				mygridlist[i].cells2(j,5).setValue("current");
				break;
			}
		}
	}
	
	
}