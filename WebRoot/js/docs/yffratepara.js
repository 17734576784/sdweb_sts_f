$(document).ready(function(){
	
	initDiction();
 	initGrid();
	
	$("#pldel").click(function(){
		gridopt.plDel();
	});
	$("#tianjia").click(function(){
		setDateValue();
		if(check()){gridopt.addOrEdit('add')};
	});
	$("#xiugai").click(function(){
		setDateValue();
		if(check()){gridopt.addOrEdit('edit')}
	});
	
//	$("#ratejNum").change(function(){ratejNum();});//阶梯电价数
});

function ratejNum(){
	var ratejNum = $("#ratejNum").val();	//阶梯电价数
	var ratejR = ["ratejR1","ratejR2","ratejR3","ratejR4","ratejR5","ratejR6","ratejR7"];
	var ratejTd = ["ratejTd1","ratejTd2","ratejTd3","ratejTd4","ratejTd5","ratejTd6"];
	//把阶梯电价文本框全部只读
	for(var i=0; i<7; i++){
		$("#"+ratejR[i]).attr("readonly",true);
		$("#"+ratejR[i]).css("background-color","DCDCDC");
	}
	//根据阶梯电价数设置阶梯电价文本框可读
	for	(var i=0; i<ratejNum; i++){
		$("#"+ratejR[i]).attr("readonly",false);
		$("#"+ratejR[i]).css("background-color","");
	}	
	//把阶梯电量文本框全部只读
	for(var i=0; i<6; i++){
		$("#"+ratejTd[i]).attr("readonly",true);
		$("#"+ratejTd[i]).css("background-color","DCDCDC");
	}
	for	(var i=0; i<(ratejNum-1); i++){
		$("#"+ratejTd[i]).attr("readonly",false);
		$("#"+ratejTd[i]).css("background-color","");
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
	gridopt.gridHeader           = "<img src='"+ def.basePath + "images/grid/imgs/item_chk0.gif' onclick='gridopt.selectAllOrNone(this);' />,Numéro de série.,Nom,Numéro tarifaire,Date d'activation,Type de tarif,Limite de crédit maximale,Tarif total,Tarif d'épaule,Tarif de pointe,Hors tarif,Type de prix d'échelle,Nombre d'étape,Type de débitmètre,Prix d'exécution du compteur d'électricité,Taux progressif1(RMB),Taux progressif2(RMB),Taux progressif3(RMB),Taux progressif4(RMB),Taux progressif5(RMB),Taux progressif6(RMB),Taux progressif7(RMB),Gradient de pas1,Gradient de pas2,Gradient de pas3,Gradient de pas4,Gradient de pas5,Gradient de pas6,Encodage d'interface1,Encodage d'interface2,&nbsp;";
	gridopt.gridColAlign         = "center,center,left,left,left,left,left,left,left,left,left,left,left,left,left,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right";
	gridopt.gridColTypes         = "ch,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "40,50,200,120,100,200,150,100,100,100,150,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,*";
	gridopt.gridTooltips         = "false,false,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false";
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridopt.gridHeader));
	$("#colType").val("int,int,str,str,str,str,0.00,0.00,0.00,0.00,0.00,str,int,str,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,int,int");
	$("#filename").val(encodeURI("Tariff Project"));
	
	gridopt.gridGetDataUrl       = def.basePath  + "ajaxdocs/actYffRatePara.action";
	gridopt.gridSelRowUrl        = def.basePath  + "ajaxdocs/actYffRatePara!getYffRateParaById.action";
	gridopt.gridAddOrEditUrl     = def.basePath  + "ajaxdocs/actYffRatePara!addOrEdit.action";
	gridopt.gridDelRowUrl        = def.basePath  + "ajaxdocs/actYffRatePara!delYffRateParaById.action";
	gridopt.selectRow            = 0;
	gridopt.ggtz				 = window.top.dbUpdate.YD_DBUPDATE_TABLE_FEERATE;
	gridopt.gridTitleSimpleParas = "id,describe,rateId,activdate,feeType,moneyLimit,ratedZ,ratefF,ratefP,ratefG,rateh1,rateh2,rateh3,rateh4,ratehBl1,ratehBl2,ratehBl3,ratehBl4,ratejType,ratejNum,meterfeeType,meterfeeR,ratejR1,ratejR2,ratejR3,ratejR4,ratejR5,ratejR6,ratejR7,ratejTd1,ratejTd2,ratejTd3,ratejTd4,ratejTd5,ratejTd6,ratehjType,ratehjNum,meterfeehjType,meterfeehjR,ratehjHr1R1,ratehjHr1R2,ratehjHr1R3,ratehjHr1R4,ratehjHr1Td1,ratehjHr1Td2,ratehjHr1Td3,ratehjHr2,ratehjHr3,ratehjBl1,ratehjBl2,ratehjBl3";
	gridopt.gridTitleDetailParas = "id,describe,rateId,activdate,feeType,moneyLimit,ratedZ,ratefF,ratefP,ratefG,rateh1,rateh2,rateh3,rateh4,ratehBl1,ratehBl2,ratehBl3,ratehBl4,ratejType,ratejNum,meterfeeType,meterfeeR,ratejR1,ratejR2,ratejR3,ratejR4,ratejR5,ratejR6,ratejR7,ratejTd1,ratejTd2,ratejTd3,ratejTd4,ratejTd5,ratejTd6,ratehjType,ratehjNum,meterfeehjType,meterfeehjR,ratehjHr1R1,ratehjHr1R2,ratehjHr1R3,ratehjHr1R4,ratehjHr1Td1,ratehjHr1Td2,ratehjHr1Td3,ratehjHr2,ratehjHr3,ratehjBl1,ratehjBl2,ratehjBl3,infCode1,infCode2";
	gridopt.filter(100);
	gridopt.redoOnRowSelected    = true;
	for(var i=7;i<30;i++){
//		if(i==12) continue;
		gridopt.grid.setColumnHidden(i,true);
	}
	SelectRowNum();
}

function SelectRowNum(){

	if(!gridopt.loaded){
		window.setTimeout("SelectRowNum()",30);
		return;
	}
	if(gridopt.selectRow==0){
		changefeeType("3");
//		ratejNum();
	}

}

function changefeeType(feetype){	
	switch(feetype){
		case "1"://复费率
			document.getElementById("dan").style.display  = "none";
			document.getElementById("fu").style.display  = "block";
			document.getElementById("hh1").style.display  = "none";
			document.getElementById("hh2").style.display  = "none";
			document.getElementById("jt1").style.display  = "none";
			document.getElementById("jt2").style.display  = "none";
			document.getElementById("jt3").style.display  = "none";
			document.getElementById("hjt1").style.display  = "none";
			document.getElementById("hjt2").style.display  = "none";
			document.getElementById("hjt3").style.display  = "none";
			document.getElementById("hjt4").style.display  = "none";
			break;
		case "2"://阶梯费率
			document.getElementById("dan").style.display  = "none";
			document.getElementById("fu").style.display  = "none";
			document.getElementById("hh1").style.display  = "none";
			document.getElementById("hh2").style.display  = "none";
			document.getElementById("jt1").style.display  = "block";
			document.getElementById("jt2").style.display  = "block";
			document.getElementById("jt3").style.display  = "block";
			document.getElementById("hjt1").style.display  = "none";
			document.getElementById("hjt2").style.display  = "none";
			document.getElementById("hjt3").style.display  = "none";
			document.getElementById("hjt4").style.display  = "none";
			break;
		case "3"://单费率
			document.getElementById("dan").style.display  = "block";
			document.getElementById("fu").style.display  = "none";
			document.getElementById("hh1").style.display  = "none";
			document.getElementById("hh2").style.display  = "none";
			document.getElementById("jt1").style.display  = "none";
			document.getElementById("jt2").style.display  = "none";
			document.getElementById("jt3").style.display  = "none";
			document.getElementById("hjt1").style.display  = "none";
			document.getElementById("hjt2").style.display  = "none";
			document.getElementById("hjt3").style.display  = "none";
			document.getElementById("hjt4").style.display  = "none";
			break;
		case "4"://混合阶梯
			document.getElementById("dan").style.display  = "none";
			document.getElementById("fu").style.display  = "none";
			document.getElementById("hh1").style.display  = "none";
			document.getElementById("hh2").style.display  = "none";
			document.getElementById("jt1").style.display  = "none";
			document.getElementById("jt2").style.display  = "none";
			document.getElementById("jt3").style.display  = "none";
			document.getElementById("hjt1").style.display  = "block";
			document.getElementById("hjt2").style.display  = "block";
			document.getElementById("hjt3").style.display  = "block";
			document.getElementById("hjt4").style.display  = "block";
			break;
		}
		showhide()
}
function showhide(){	
	$("#gridbox").height($(window).height()-$("#showmore").height()- 115);	
	var rid = gridopt.grid.getSelectedId();
	gridopt.showgrid(true);
	gridopt.grid.selectRow(gridopt.grid.getRowIndex(rid))
}
function redoOnRowSelected(json){
	var rowsplit = gridopt.gridTitleDetailParas.split(SDDef.SPLITCOMA);
	
	for(var i = 0; i < rowsplit.length; i ++){
		if (rowsplit[i] == "feeType"){
			changefeeType(json.data[i]);
 			$("#gridbox").css("visibility", "visible");
			$(".tabsty").show();
			$(".page_tbl").css("visibility", "visible");
		}		
		if(rowsplit[i] == "activdate"){
			$("#activdate").val(json.data[i]);
			$("#activdateF").val(formatMDH(json.data[i],"YMD_FULL"));
		}	

		$("#"+rowsplit[i]).val(json.data[i]);
	}
//	ratejNum();
}

function setDateValue(){
	tmp = ["activdate"];
	for(var i=0;i<tmp.length;i++){
		var tmpvalue=$("#"+tmp[i]+"F").val();		
		if(tmpvalue==""){
			$("#"+tmp[i]).val("");
		}else{
			$("#"+tmp[i]).val(getDate_Time(tmpvalue));
		}
	}
}

function check() {
	if (!ckChar("describe", name, 64, true)) {
		return false;
	}
	if(!isNumber("rateId", "Tariff Index(TI)",1,9999,true)){
		return false;
	}
	if(!isDbl("ratefF", ffl)){
		return false;
	}
	if(!isDbl("ratefP", pfl)){
		return false;
	}
	if(!isDbl("ratefG", gfl)){
		return false;
	}
	
	return true;
}
