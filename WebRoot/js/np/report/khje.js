var	mygrid = null;
var pos = true;

$(document).ready(function(){
	
	mygrid = new dhtmlXGridObject('gridbox');
	
	initgrid();
	initDate();
	
	$("#search").click(function(){
		search();
	});
	$("#area").change(function(){getMeter();})
});

function getMeter() {
	
	$("#meterdesc").html("<option value=-1>所有</option>");
	
	var aid = $("#area").val();
	if(aid == -1) {
		return;
	}
	
	$.post(def.basePath + "ajax/actCommon!getMeterByArea.action",{value : aid},function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			for(var i=0;i<json.length;i++){
				$("#meterdesc").append("<option value="+json[i].value+">"+json[i].text+"</option>");
			}
		}
	});
}

function initDate(){
	var date = new Date();
	
	$("#sdate").val(date.Format("yyyy年MM月01日").toString());
	$("#edate").val(date.Format("yyyy年MM月dd日").toString());
}

function initgrid() {
	var gridHeader = "供电所,片区,客户编号,客户名称,卡号,电表,开始时间,结束时间,费率,用电金额,剩余金额,用电电量,过零电量,用电状态";
	
	var datatype = "str,str,str,str,str,str,str,str,0.000,0.000,0.000,0.000,0.000,str";
	
	mygrid.setImagePath(def.basePath + "images/grid/imgs/");
	mygrid.setHeader(gridHeader);
	mygrid.setInitWidths("120,120,120,120,120,120,180,180,100,100,100,100,100,100");
	mygrid.setColAlign("left,left,left,left,left,left,left,left,right,right,right,right,right,left");
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	//mygrid.enableSmartRendering(true);
	
	mygrid.init();
	mygrid.setSkin("modern");
	
	mygrid.setColumnHidden(12, true);
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val(datatype);
	$("#filename").val(encodeURI("农排客户金额明细"));
	
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
	
	if(sdate == "") {
		alert("请填写查询起始时间!");
		return;
	}
	if(sdate > edate){
		edate = sdate;
		$("#edate").val($("#sdate").val());
	}
	
	var param = '{sdate:"'+sdate+'",edate:"'+edate+'",org:"'+$("#org").val()+'",area:"'+$("#area").val()+'",khbh:"'+$("#khbh").val()+'",khmc:"'+$("#khmc").val()+'",cardno:"'+$("#cardno").val()+'",meterdesc:"'+$("#meterdesc").val()+'",meteraddr:"'+$("#meteraddr").val()+'"}';
	
	loading.loading();
	$.post(def.basePath + "ajaxnp/actReport!khjeReport.action",{result : param},function(data) {
		loading.loaded();
		if(data.result != "") {
			var json = eval('(' + data.result + ')');
			mygrid.parse(json, "json");
			
			var mr = '{cols:[{col:0,key_idx:[0]},{col:1,key_idx:[1]},{col:2},{col:3,key_idx:[1,2]},{col:4}]}';
			
			$("#mergeRows").val(mr);
			
			var mr_json = eval('(' + mr + ')');
			mergeRow(json, mr_json);
			
			$("#excPara").val(encodeURI(jsonString.json2String(json)));
			
		}else{
			
		}
	});
	
}