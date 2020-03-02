var load_flag = [false, false];

$(document).ready(function(){
	
	initOrg();
	
	$("#org").change(function(){
		getRtuByOrg(this.value);
	});
	//$("#readcard").click(function(){readcard();});
});
function initOrg(){
	$.post(def.basePath + "ajax/actCommon!getOrg.action",{},function(data){
		load_flag[0] = true;
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			
			if(!pos){
				if(data.flag != "1"){
					$("#org").append("<option value=-1>所有</option>");
				}
			}
			
			for(var i=0;i<json.length;i++){
				$("#org").append("<option value="+json[i].value+">"+json[i].text+"</option>");
			}
			
			if(pos){
				if(data.flag != "1"){
					$("#org").append("<option value=-1>所有</option>");
				}
			}
			window.setTimeout('getRtuByOrg('+$("#org").val()+')', 20);
		}
		
	});
}
function getRtuByOrg(org_id){
	$("#rtu").html("<option value=-1>所有</option>");
	if(org_id == -1){
		load_flag[1] = true;
		return;
	}
	$.post(def.basePath + "ajax/actCommon!getRtuByOrg.action",{value : org_id, appType : 5},function(data){
		load_flag[1] = true;
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			for(var i=0;i<json.length;i++){
				$("#rtu").append("<option value="+json[i].value+">"+json[i].text+"</option>");
			}
		}else{
			
		}
	});
}

function readcard() {
	var card_inf = window.top.card_comm.readcard();
	alert(card_inf)
}

function setExcValue(){
	
	if(gridopt.grid.getRowsNum() == 0)return;
	
	$("#excPara").val(encodeURI(jsonString.json2String(gridopt.jsondata.page[0])));
}
function dcExcel(){
	var len = $("#excPara").val().length;
	
	if(len > 1280000){
		alert("数据量大，不能导出excel");
		return;
	}
	
	toxls.submit();
}