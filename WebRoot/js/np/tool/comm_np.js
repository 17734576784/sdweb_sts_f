var load_flag = [false, false];

$(document).ready(function(){
	
	initOrg();
	
	$("#org").change(function(){
		getAreaByOrg(this.value);
	});
	
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
			window.setTimeout('getAreaByOrg('+$("#org").val()+')', 20);
		}
		
	});
}

function getAreaByOrg(org_id){
	$("#area").html("<option value=-1>所有</option>");
	if(org_id == -1){
		load_flag[1] = true;
		return;
	}
	$.post(def.basePath + "ajax/actCommon!getAreaByOrg.action",{value : org_id},function(data){
		load_flag[1] = true;
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			for(var i=0;i<json.length;i++){
				$("#area").append("<option value="+json[i].value+">"+json[i].text+"</option>");
			}
		}
	});
}

function mergeRow(json, mr_json) {
	
	var len = json.rows.length;
	var last_value = "", cur_value = "", last_id = "";
	var eq_num = 1;
	var key_flag = false;
	
	for(var j = 0; j < mr_json.cols.length; j++) {
		
		key_flag = false;
		
		var merge_row = mr_json.cols[j].col;
		
		var ki = null;
		if(mr_json.cols[j].key_idx) {
			ki = mr_json.cols[j].key_idx;
		}
		key_flag = false;
		if(ki != null && ki.length > 0) key_flag = true;
		
		if(key_flag) {
			var id = json.rows[0].id;
			var ids = id.split("_");
			last_value = "";
			for(var t = 0; t < ki.length; t++) {
				last_value += ids[ki[t]];
			}
		}
		else {
			last_value = json.rows[0].data[merge_row];
		}
		
		last_id = json.rows[0].id;
		
		eq_num = 1;
		
		for(var i = 1; i < len; i++) {
			cur_value = "";
			if(key_flag) {
				var id = json.rows[i].id;
				var ids = id.split("_");
				for(var t = 0; t < ki.length; t++) {
					cur_value += ids[ki[t]];
				}
			}
			else {
				cur_value = json.rows[i].data[merge_row];
			}
			
			if(last_value != cur_value) {
				mygrid.setRowspan(last_id, mr_json.cols[j].col, eq_num);
				eq_num = 0;
				last_value = cur_value;
				
				last_id = json.rows[i].id;
			}
			else if(i == len - 1) {
				mygrid.setRowspan(last_id, mr_json.cols[j].col, eq_num + 1);
			}
			
			eq_num++;
			
		}
	}
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