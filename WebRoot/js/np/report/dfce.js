var	mygrid = null;
var pos = true;
var load_flag = [false, false];
$(document).ready(function(){
	initOrg();
	mygrid = new dhtmlXGridObject('gridbox');
	
	initgrid();
	initDate();
	
	$("#search").click(function(){
		search();
	});
	$("#org").change(function(){
		getConsByOrg(this.value);
	});
});

function initDate(){
	var date = new Date();
	$("#sdate").val(date.Format("yyyy年MM月01日").toString());
	$("#edate").val(date.Format("yyyy年MM月dd日").toString());
}

function initgrid() {
	var gridHeader = "供电所,台区,电表,表地址,起数,止数,倍率,电量,电价,应收电费,合计售电电费,差额电费,电费平衡%";//表内合计电量,差额电量,电量差额百分比,
	
	var datatype = "str,str,str,str,0.000,0.000,0.00,0.000,str,0.000,0.000,0.000,str";//0.000,0.000,str,
	
	mygrid.setImagePath(def.basePath + "images/grid/imgs/");
	mygrid.setHeader(gridHeader);
	mygrid.setInitWidths("100,120,160,90,80,80,60,90,60,90,90,90,80");//120,120,120,
	mygrid.setColAlign("left,left,left,left,right,right,right,right,right,right,right,right,right");//right,right,right,
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");//ro,ro,ro,
//	mygrid.setColSorting();
	mygrid.init();
//	mygrid.enableSmartRendering(true); 不能用否则数据多的时候会出现乱的现象
	mygrid.setSkin("modern");
	
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val(datatype);
	$("#filename").val(encodeURI("电量电费对照表"));
	
	//如果 WebConfig配置文件中autoshow="1"，则自动加载查询
	if(np_autoshow[0] == 1){
		search();
	}
}

function search() {
	var flag = true;
	for ( var i = 0; i < load_flag.length; i++) {
		flag = flag && load_flag[i];
	}
	if (!flag) {
		window.setTimeout('search()', 100);
		return;
	}
	
	mygrid.clearAll();
	
	var sdate = getDate_Time($("#sdate").val());
	var edate = getDate_Time($("#edate").val());
	
	if ((sdate == "") || (edate == "")) {
		alert("请填写完整的查询时间!");
		return;
	}

	if(sdate >= edate){
		alert("结束日期应大于开始日期");
		return;
	}
	
//	if(sdate == edate){
//		alert("开始日期不能等于结束日期");
//		return;
//	}
	var param = {};
	param.sdate = sdate;
	param.edate = edate;
	param.org	= $("#org").val();
	param.cons	= $("#cons").val();	
	loading.loading();
	$.post(def.basePath + "ajaxnp/actReport!dfceReport.action",{result : jsonString.json2String(param)},function(data) {
		loading.loaded();
		if(data.result != "") {
			var json = eval('(' + data.result + ')');
			mygrid.parse(json, "json");
			
			var mr = '{cols:[{col:0},{col:1}]}';//合并 供电所、台区列
			
			$("#mergeRows").val(mr);
			
			var mr_json = eval('(' + mr + ')');
			mergeRow(json, mr_json);
			var len = json.rows.length;
			for(var i=1; i<len; i++){
				if(json.rows[i].id.indexOf("_t")>=0){
					mygrid.setRowColor(json.rows[i].id,"#ffffbf");
//					mygrid.enableColSpan(true);						//允许合并列
//					mygrid.setColspan(json.rows[i].id,2,5);			//合并第3~7列
				}
			}			

			$("#excPara").val(encodeURI(jsonString.json2String(json)));
			
		}
	});	
}

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
			window.setTimeout('getConsByOrg('+$("#org").val()+')', 20);
		}
		
	});
}

function getConsByOrg(org_id){
	$("#cons").html("<option value=-1>所有</option>");
	if(org_id == -1){
		load_flag[1] = true;
		return;
	}
	$.post(def.basePath + "ajax/actCommon!getConsByOrg.action",{value : org_id},function(data){
		load_flag[1] = true;
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			for(var i=0;i<json.length;i++){
				$("#cons").append("<option value="+json[i].value+">"+json[i].text+"</option>");
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

function dcExcel(){
	var len = $("#excPara").val().length;
	
	if(len > 1280000){
		alert("数据量大，不能导出excel");
		return;
	}
	
	toxls.submit();
}