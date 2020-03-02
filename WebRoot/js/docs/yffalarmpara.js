$(document).ready(function(){
	
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : '{item:["'+Dict.DICTITEM_ALARMTYPE+'"]}'},function(data){
		
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			var dict = eval("json.rows[0]."+Dict.DICTITEM_ALARMTYPE);
			for ( var i = 0; i < dict.length; i++) {
				$("#type").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			}
		}
	});
	
	gridopt.gridHeader           = "<img src='" + def.basePath + "images/grid/imgs/item_chk0.gif' onclick='gridopt.selectAllOrNone(this);' />," + grid_title + ",&nbsp;";
	gridopt.gridColAlign         = "center,center,left,left,right,right,left,left,left,left,left,left,left";
	gridopt.gridColTypes         = "ch,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "40,50,150,100,100,100,100,100,100,100,100,100,100,*";
	gridopt.gridTooltips         = "false,false,true,false,false,false,false,false,false,false,false,false,false";
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridopt.gridHeader));
	$("#colType").val("int,int,str,str,0.000,0.000,str,str,str,str,str,str,str");
	$("#filename").val(encodeURI("报警方案"));
	
	gridopt.gridGetDataUrl       = def.basePath  + "ajaxdocs/actYffAlarmPara.action";
	gridopt.gridSelRowUrl        = def.basePath  + "ajaxdocs/actYffAlarmPara!getYffAlarmParaById.action";
	gridopt.gridAddOrEditUrl     = def.basePath  + "ajaxdocs/actYffAlarmPara!addOrEdit.action";
	gridopt.gridDelRowUrl        = def.basePath  + "ajaxdocs/actYffAlarmPara!delYffAlarmParaById.action";
	gridopt.selectRow            = 0;
	gridopt.redoOnRowSelected    = true;             //选择行时重新定义
	gridopt.ggtz				 = window.top.dbUpdate.YD_DBUPDATE_TABLE_YFFALARM;
	gridopt.gridTitleSimpleParas = "id,describe,type,alarm1,alarm2,payalmFlag,hzalmFlag,tzalmFlag,dxalmFlag,syalmFlag,dxalmcgkFlag,syalmcgkFlag";
	gridopt.gridTitleDetailParas = "id,describe,type,alarm1,alarm2,payalmFlag,hzalmFlag,tzalmFlag,dxalmFlag,syalmFlag,dxalmcgkFlag,syalmcgkFlag";
	gridopt.filter(100);
	
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

function redoOnRowSelected(json){
	var rowsplit = gridopt.gridTitleDetailParas.split(",");
	for(var i = 0; i < rowsplit.length; i ++){
		if(rowsplit[i] == "payalmFlag"){
			if(json.data[i] == 0){
				$("#payalmFlag_chk").attr("checked",false);
			}else if(json.data[i] == 1){
				$("#payalmFlag_chk").attr("checked",true);
			}
		}
		else if(rowsplit[i] == "hzalmFlag"){
			if(json.data[i] == 0){
				$("#hzalmFlag_chk").attr("checked",false);
			}else if(json.data[i] == 1){
				$("#hzalmFlag_chk").attr("checked",true);
			}
		}
		else if(rowsplit[i] == "tzalmFlag"){
			if(json.data[i] == 0){
				$("#tzalmFlag_chk").attr("checked",false);
			}else if(json.data[i] == 1){
				$("#tzalmFlag_chk").attr("checked",true);
			}
		}
		else if(rowsplit[i] == "dxalmFlag"){
			if(json.data[i] == 0){
				$("#dxalmFlag_chk").attr("checked",false);
			}else if(json.data[i] == 1){
				$("#dxalmFlag_chk").attr("checked",true);
			}
		}
		else if(rowsplit[i] == "syalmFlag"){
			if(json.data[i] == 0){
				$("#syalmFlag_chk").attr("checked",false);
			}else if(json.data[i] == 1){
				$("#syalmFlag_chk").attr("checked",true);
			}
		}
		else if(rowsplit[i] == "dxalmcgkFlag"){
			if(json.data[i] == 0){
				$("#dxalmcgkFlag_chk").attr("checked",false);
			}else if(json.data[i] == 1){
				$("#dxalmcgkFlag_chk").attr("checked",true);
			}
		}
		else if(rowsplit[i] == "syalmcgkFlag"){
			if(json.data[i] == 0){
				$("#syalmcgkFlag_chk").attr("checked",false);
			}else if(json.data[i] == 1){
				$("#syalmcgkFlag_chk").attr("checked",true);
			}
		}

		$("#"+rowsplit[i]).val(json.data[i]);
	}
}									

function check() {
	if($("#payalmFlag_chk").attr("checked")){
		$("#payalmFlag").val(1)
	}else if(!($("#payalmFlag_chk").attr("checked"))){
		$("#payalmFlag").val(0)
	}
	if($("#hzalmFlag_chk").attr("checked")){
		$("#hzalmFlag").val(1)
	}else if(!($("#hzalmFlag_chk").attr("checked"))){
		$("#hzalmFlag").val(0)
	}
	if($("#tzalmFlag_chk").attr("checked")){
		$("#tzalmFlag").val(1)
	}else if(!($("#tzalmFlag_chk").attr("checked"))){
		$("#tzalmFlag").val(0)
	}
	if($("#dxalmFlag_chk").attr("checked")){
		$("#dxalmFlag").val(1)
	}else if(!($("#dxalmFlag_chk").attr("checked"))){
		$("#dxalmFlag").val(0)
	}
	if($("#syalmFlag_chk").attr("checked")){
		$("#syalmFlag").val(1)
	}else if(!($("#syalmFlag_chk").attr("checked"))){
		$("#syalmFlag").val(0)
	}
	if($("#dxalmcgkFlag_chk").attr("checked")){
		$("#dxalmcgkFlag").val(1)
	}else if(!($("#dxalmcgkFlag_chk").attr("checked"))){
		$("#dxalmcgkFlag").val(0)
	}
	if($("#syalmcgkFlag_chk").attr("checked")){
		$("#syalmcgkFlag").val(1)
	}else if(!($("#syalmcgkFlag_chk").attr("checked"))){
		$("#syalmcgkFlag").val(0)
	}
	
	if (!ckChar("describe", doc_name, 64, true)) {
		return false;
	}
	if(!isNumber("alarm1",bjz+"1")){
		return false;
	}
	if(!isNumber("alarm2",bjz+"2")){
		return false;
	}

	return true;
}
