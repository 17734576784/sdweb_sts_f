$(document).ready(function(){
	
	var text = '{item:["' + Dict.DICTITEM_YFFAPPTYPE + '","' + Dict.DICTITEM_USEFLAG + '"]}';
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : text},function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			
			var dict = eval("json.rows[0]."+Dict.DICTITEM_YFFAPPTYPE);
			for ( var i = 0; i < dict.length; i++) {
				$("#appType1").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			}
			
			var dict = eval("json.rows[1]."+Dict.DICTITEM_USEFLAG);
			for ( var i = 0; i < dict.length; i++) {
				$("#useFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			}
		}
	});
	
	gridopt.gridHeader           = "<img src='" + def.basePath + "images/grid/imgs/item_chk0.gif' onclick='gridopt.selectAllOrNone(this);' />,"+grid_title+",&nbsp;";
	gridopt.gridColAlign         = "center,center,left,left,left,left,left,left";
	gridopt.gridColTypes         = "ch,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "40,50,100,100,150,150,150,150,*";
	gridopt.gridTooltips         = "false,false,true,false,false,false,false,false";
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridopt.gridHeader));
	$("#colType").val("int,int,str,str,str,str,str,str");
	$("#filename").val(encodeURI("节假日保电"));
	
	gridopt.gridGetDataUrl       = def.basePath  + "ajaxdocs/actGloProtect.action";
	gridopt.gridSelRowUrl        = def.basePath  + "ajaxdocs/actGloProtect!getGloProtectById.action";
	gridopt.gridAddOrEditUrl     = def.basePath  + "ajaxdocs/actGloProtect!addOrEdit.action";
	gridopt.selectRow            = 0;
	gridopt.ggtz				 = window.top.dbUpdate.YD_DBUPDATE_TABLE_GLOPROT;
	gridopt.gridTitleDetailParas = "id,appType,useFlag,bgDate,bgTime,edDate,edTime";
	gridopt.gridTitleSimpleParas = "id,appType,useFlag,bgDate,bgTime,edDate,edTime";
	gridopt.filter(100);
	gridopt.redoOnRowSelected    = true;
	
	$("#xiugai").click(function(){
		if(check()){
			setDateValue();
			setTimeValue();
			gridopt.addOrEdit('edit')
		}
	});
});


function redoOnRowSelected(json){
	var rowsplit = gridopt.gridTitleDetailParas.split(SDDef.SPLITCOMA);

	for(var i = 0; i < rowsplit.length; i ++){
		if(rowsplit[i] == "bgDate" || rowsplit[i] == "edDate"){
			dateFormat.date = json.data[i];
			$("#"+rowsplit[i]+"F").val(dateFormat.formatToYMD(0));
		}
		else if(rowsplit[i] == "bgTime" || rowsplit[i] == "edTime"){
			dateFormat.date = json.data[i];
			$("#"+rowsplit[i]+"F").val(dateFormat.formatToHMS(0));
		}
		if(rowsplit[i] == "appType"){
			$("#appType1").val(json.data[i]);
		}
		
		$("#"+rowsplit[i]).val(json.data[i]);
	}
}

function setDateValue(){
	tmp = ["bgDate","edDate"];
	for(var i=0;i<tmp.length;i++){
		var tmpvalue=$("#"+tmp[i]+"F").val();
		if(tmpvalue==""){
			$("#"+tmp[i]).val("");
		}else{
			$("#"+tmp[i]).val(getDate_Time(tmpvalue));
		}
	}
}

function setTimeValue() {
	tmp = ["bgTime","edTime"];
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
	//判断开始时间和结束时间
	var sDate = initDate.getdateyyyymmdd("bgDateF");
	var sTimeStr =  $("#bgTimeF").val();
	var sTime = sTimeStr.substring(0,2) + sTimeStr.substring(3,5) + sTimeStr.substring(6,8);
	var starttime = sDate + sTime;
	
	var eDate = initDate.getdateyyyymmdd("edDateF");
	var eTimeStr =  $("#edTimeF").val();
	var eTime = eTimeStr.substring(0,2) + eTimeStr.substring(3,5) + eTimeStr.substring(6,8);
	var endtime = eDate + eTime;
	
	if (endtime < starttime) {
		alert(date_con + "!");
		return false;
	}

	return true;
}