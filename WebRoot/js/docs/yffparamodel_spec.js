var apptype = "";
var loadflag = false;
$(document).ready(function(){
	apptype = SDDef.APPTYPE_ZB;
	initDiction();
	initModelList();		//加载模板列表
	$("#backmsg").html("");
	$("#model_list").change(function(){
		if(loadflag){
			getModelByMid();
		}
	});
	
	$("#add").click(function(){
		if(check())	addModelTempalte();
	});
	$("#edit").click(function(){
		if(check())	editModel();
	});
	$("#del").click(function(){
		if(confirm("确定使用该模板数据?")){
			delModelById();
		}
	});
	
	$("#import").click(function(){
		importData();
	});
	
	$("#export").click(function(){
		exportData();
	});
	
});

//初始化数据字典，填充对应下拉框
function initDiction(){
	var text = '{item:["' + Dict.DICTITEM_YESFLAG + '","' + Dict.DICTITEM_USEFLAG + '","' + Dict.DICTITEM_CB_CYCLE_TYPE + '","' + Dict.DICTITEM_PREPAYTYPE + '","' + Dict.DICTITEM_FEETYPE + '","' + Dict.DICTITEM_PAYTYPE + '","' + Dict.DICTITEM_YFFMONEY + '","' + Dict.DICTITEM_YFFSPECCTRLTYPE + '","' + Dict.DICTITEM_EXECFLAG + '","' + Dict.DICTITEM_PRIZEFLAG  + '","' + Dict.DICTITEM_CARDMETER_TYPE + '"]}';
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : text}, function(data){
		initDB();
		var json = eval('(' + data.result + ')');
		var iid = 0;
		var dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_YESFLAG);
		for ( var i = 0; i < dict.length; i++) {
			$("#ngloprotFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			//$("#feeChgf").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			//$("#jbfChgf").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			$("#fxdfFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			$("#cbjsFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_USEFLAG);
		for ( var i = 0; i < dict.length; i++) {
			$("#useGfh").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			$("#localMaincalcf").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_CB_CYCLE_TYPE);
		for ( var i = 0; i < dict.length; i++) {
			$("#cbCycleType").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_PREPAYTYPE);
		for ( var i = 0; i < dict.length; i++) {
			$("#feectrlType").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_FEETYPE);
		for ( var i = 0; i < dict.length; i++) {
			$("#caclType").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_PAYTYPE);
		for ( var i = 0; i < dict.length; i++) {
			$("#payType").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_YFFMONEY);
		for ( var i = 0; i < dict.length; i++) {
			$("#tzVal").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_YFFSPECCTRLTYPE);
		for ( var i = 0; i < dict.length; i++) {
			$("#yffctrlType").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_EXECFLAG);
		for ( var i = 0; i < dict.length; i++) {
			$("#powrateFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_PRIZEFLAG);
		for ( var i = 0; i < dict.length; i++) {
			$("#prizeFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
//		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_STOPFLAG);
//		for ( var i = 0; i < dict.length; i++) {
//			$("#stopFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
//		}
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_CARDMETER_TYPE);
		for ( var i = 0; i < dict.length; i++) {
			$("#cardmeterType").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
	});
}

function initDB(){
	var tables = '{tables:["' + YDTable.TABLECLASS_YFFRATEPARA + '","' + YDTable.TABLECLASS_YFFALARMPARA + '","cs_stand"]}';
	$.post(def.basePath + "ajax/actCommon!getValueAndText.action",{tableName: tables},function(data){
		
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			var iid = 0;
			var table = eval("json.rows["+(iid++)+"]."+YDTable.TABLECLASS_YFFRATEPARA);
			for ( var i = 0; i < table.length; i++) {
				
				$("#feeprojId").append("<option value="+table[i].value+">"+table[i].text+"</option>");
				$("#feeproj1Id").append("<option value="+table[i].value+">"+table[i].text+"</option>");
				$("#feeproj2Id").append("<option value="+table[i].value+">"+table[i].text+"</option>");
				
//				$("#feeChgid").append("<option value="+table[i].value+">"+table[i].text+"</option>");
//				$("#feeChgid1").append("<option value="+table[i].value+">"+table[i].text+"</option>");
//				$("#feeChgid2").append("<option value="+table[i].value+">"+table[i].text+"</option>");
			}
			table = eval("json.rows["+(iid++)+"]."+YDTable.TABLECLASS_YFFALARMPARA);
			for ( var i = 0; i < table.length; i++) {
				$("#yffalarmId").append("<option value="+table[i].value+">"+table[i].text+"</option>");
			}
			table = eval("json.rows["+(iid++)+"].cs_stand");
			for ( var i = 0; i < table.length; i++) {
				$("#csStand").append("<option value="+table[i].value+">"+table[i].text+"</option>");
			}
		}
	});
}

//添加模板
function addModelTempalte(){
	var modelFiled = getModelFiled();
	//模板名称
	var model_desc = $("#model_name").val();
	if(model_desc == ""){
		alert("模板名称不能为空!");
		return;
	}
	loading.loading();
	$.post(
			def.basePath + "ajaxdocs/actYffDocModel!add.action", 
			{
				flag		:   "",
				filed 		: 	modelFiled,
				describe 	: 	model_desc,
				apptype 	: 	apptype
			}, 
			function(data){
				loading.loaded();
				if(data.flag == SDDef.SUCCESS){
					alert("添加模板成功！");
					initModelList(data.mid);
				}
				else if(data.flag == "samename"){
					alert("此模板名称已经存在！");
				}
				else{
					alert("添加模板失败！");
				}
			}
	);
}

//获取模板详细内容,组成json格式
function getModelFiled(){
	var filed = "";
	var selectobj = $("form select");
	var inputobj  = $("form input");
	var key = "";
	var value = "";
	for(var i=0; i<selectobj.length; i++){
		key = selectobj[i].id;
		value = selectobj[i].value;
		filed += '"' + key + '":"' + value + '",';
	}
	for(var i=0; i<inputobj.length; i++){
		key = inputobj[i].id;
		if(key == "import" || key == "export") continue;
		value = inputobj[i].value;
		filed += '"' + key + '":"' + value + '",';
	}
	filed = filed.substring(0,filed.length-1);
	filed = "{" + filed;
	filed += "}"; 
	return filed;
}

//显示当前模板内容
function fillPage(json){
	var selectobj = $("form select");
	var inputobj  = $("form input");
	
	//遍历元素,对应赋值
	for(var key in json){
		for(var i=0; i<selectobj.length; i++){
			if(key == selectobj[i].id){
				$("#" + key).val(json[key]);
				break;
			} 
		}
		
		for(var i=0; i<inputobj.length; i++){
			if(key == inputobj[i].id){
				$("#" + key).val(json[key]);
				break;
			} 
		}
	}
}

//根据mid获取模板内容
function getModelByMid(mid){
	if(!mid) mid = $("#model_list").val();
	if(mid == "-1") return;
	loading.loading();
	$.post(
			def.basePath + "ajaxdocs/actYffDocModel!getModelById.action", 
			{
				flag		:   "",
				filed 		: 	"",
				mid			:	mid,
				describe	:	""
			}, 
			function(data){
				loading.loaded();
				if(data.flag == SDDef.SUCCESS){
					$("#model_name").val(data.describe);
					var json = eval('(' + data.filed + ')');
					fillPage(json);
				}
			}
	);
}

//加载模板列表
function initModelList(mid){
	if(!apptype) return;
	$.post(
			def.basePath + "ajaxdocs/actYffDocModel!getModelIdList.action", 
			{
				filed 		: 	"",
				apptype		:	apptype
			}, 
			function(data){
				var option = "";
				if(data.filed != ""){
					var json = eval('(' + data.filed + ')');
					for(var i=0; i<json.rows.length; i++){
						option += "<option value=" + json.rows[i].value + ">" + json.rows[i].text + "</option>";
					}
					$("#model_list").html(option);
					//如果存在mid则将其设为选择项
					if(mid) $("#model_list").val(mid);
					loadflag = true;
					//加载模板内容
					getModelByMid(mid);
				}
				else{
					option += "<option value=-1>----请添加模板----</option>";
					$("#model_list").html(option);
				}
			}
	);
}

//修改模板内容
function editModel(){
	
	var modelFiled = getModelFiled();
	var modelId = $("#model_list").val();
	if(modelId == "-1"){
		alert("请先添加模板!");
		return;
	} 
	loading.loading();
	//模板名称
	var model_desc = $("#model_name").val();
	if(model_desc == ""){
		alert("模板名称不能为空!");
		return;
	}

	$.post(
			def.basePath + "ajaxdocs/actYffDocModel!editById.action", 
			{
				flag      	:   "",
				mid			:   modelId,
				filed 		: 	modelFiled,
				describe 	: 	model_desc
			}, 
			function(data){
				loading.loaded();
				if(data.flag == SDDef.SUCCESS){
					alert("修改模板成功！");
					initModelList(data.mid);
				}
				else if(data.flag == "samename"){
					alert("此模板名称已经存在！");
				}
				else{
					alert("修改模板失败！");
				}
			}
	);
}

//删除选定模板
function delModelById(){
	var modelId = $("#model_list").val();
	if(modelId == "-1"){
		alert("没有可删除的模板!");
		return;
	}
	loading.loading();
	$.post(
			def.basePath + "ajaxdocs/actYffDocModel!delById.action", 
			{
				flag : "",
				mid	:	modelId
			},
			function(data){
				loading.loaded();
				if(data.flag == 'success'){
					alert("删除模板成功！");
					initModelList();	//重新加载模板列表和模板内容
				}
				else{
					alert("删除模板失败！");
				}
			}
	);
}

function check() {
	if (!isDbl("payAdd1", fjz+"1",false)) {
		return false;
	}
	if (!isDbl("payAdd2", fjz+"2",false)) {
		return false;
	}
	if (!isDbl("payAdd3", fjz+"3",false)) {
		return false;
	}
	if (!isNumber("hfhShutdown", gfhddz, false)) {
		return false;
	}
	
	if (!isNumber("protSt", zdbdks,0,25, false)) { // id, describe, min, max, required
		return false;
	}
	if (!isNumber("protEd", zdbdjs,0,25, false)) {
		return false;
	}
	if (!isNumber("hfhTime", gfhsj,0,32767, false)) {
		return false;
	}
	
	if (!isNumber("plusTime", fhmcbh,0,32767, false)) {
		return false;
	}
	
	return true;
}

//导入数据
function importData(){
	modalDialog.height 	= 120;
	modalDialog.width 	= 300;
	modalDialog.url = "impModel.jsp";
	var o = modalDialog.show();
	if(o && o!= ""){
		window.location.reload();
		alert(o);
	}
}

//导出数据
function exportData(){
	var list_id = $("#model_list").val();
	if(list_id < 0) return ;
	window.location.href= def.basePath + "impExp/export.action?filed=" + list_id;
}
