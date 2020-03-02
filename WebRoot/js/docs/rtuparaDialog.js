var params = window.dialogArguments;
var loaded = [false,false,false];
var img_path= params.img_path;
var tmp_id = -1;
$(document).ready(function(){
	
	if(params.id == ""){//父页面  点击  新增  进入本页面时，修改按钮禁用
		$("#xiugai").attr("disabled",true);
	}else{
		$("#xiugai").attr("disabled",false);
	}
	
	initDiction();

	//选择居民区
	$("#selconsId").click(function(){
		modalDialog.height = 410;
		modalDialog.width = 350;
		modalDialog.url = YDDef.BASEPATH + "jsp/dialog/selectCons.jsp";
		modalDialog.param = "";
		var str = modalDialog.show();
		if(str && str.id && str.desc){
			$("#showconsId").val(str.desc);
			$("#consId").val(str.id);
		}
	});
	
	$("#tianjia").click(function(){
		if(check()){
			setDateValue();
			if($("#id").val() != ""){
				tmp_id = $("#id").val();
			}
			$("#id").val("");
				addOrEdit("add");
		}
	});
	
	$("#xiugai").click(function(){
		if(check()){
			setDateValue();
			if($("#id").val() === ""){
				$("#id").val(tmp_id);
			}
			addOrEdit("edit");
		};
	});	
});
	
function initDiction(){
	var text = '{item:["' + Dict.DICTITEM_USEFLAG + '","' + Dict.DICTITEM_JMRTUPROTTYPE 
		+ '","' + Dict.DICTITEM_RTUFACTORY + '","' + Dict.DICTITEM_RUNSTATUS 
		+ '","' + Dict.DICTITEM_INSTSITE + '","' + Dict.DICTITEM_NETONLINE + '"]}';
	
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : text}, function(data){
		var json = eval('(' + data.result + ')');
		var iid = 0;
		var dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_USEFLAG);
		for ( var i = 0; i < dict.length; i++) {
			$("#useFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_JMRTUPROTTYPE);
		for ( var i = 0; i < dict.length; i++) {
			$("#protType").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_RTUFACTORY);
		for ( var i = 0; i < dict.length; i++) {
			$("#factory").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_RUNSTATUS);
		for ( var i = 0; i < dict.length; i++) {
			$("#runStatus").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_INSTSITE);
		for ( var i = 0; i < dict.length; i++) {
			$("#instSite").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_NETONLINE);
		for ( var i = 0; i < dict.length; i++) {
			$("#onlineType").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		initChanPara();
		initRtuModel();
		initFreeze();
		setValue();
	});
}
	

function initChanPara(){
	$.post(YDDef.BASEPATH + "ajax/actCommon!loadDBDesc.action",{tableName: YDTable.TABLECLASS_CHANPARA},function(data){
		if(data.result != ""){
			var array = eval('(' + data.result + ')');
			for(var i=0;i<array.length;i++){
				$("#chanMain").append("<option value="+array[i].value+">"+array[i].text+"</option>");
				$("#chanBak").append("<option value="+array[i].value+">"+array[i].text+"</option>");
			}
		}
		loaded[0] = true;
	});
}
function initRtuModel(){
	$.post(YDDef.BASEPATH + "ajax/actCommon!loadRtuModel.action",{result:3},function(data){
		if(data.result != ""){
			var array = eval('(' + data.result + ')');
			for(var i=0;i<array.length;i++){
				$("#rtuModel").append("<option value="+array[i].value+">"+array[i].text+"</option>");
			}
		}
		loaded[1] = true;
	});
}
function initFreeze(){
	$.post(YDDef.BASEPATH + "ajax/actCommon!loadDBDesc.action",{tableName: YDTable.TABLECLASS_FZCBTEMPLATE},function(data){
		if(data.result != ""){
			var array = eval('(' + data.result + ')');
			for(var i=0;i<array.length;i++){
				$("#fzcbId").append("<option value="+array[i].value+">"+array[i].text+"</option>");
			}
		}
		loaded[2] = true;		
	});
}

function addOrEdit(flag){
	
	var tmp_str = $("#rtuAddr").val() + "," + $("#chanMain").val() + "," + $("#areaCode").val() + "," + $("#id").val() + "," + flag;
	
	if($("#describe").val() != "" && $("#rtuAddr").val() != ""){
		$.post(YDDef.BASEPATH  + "ajaxdocs/actRtuPara!checkRtuAddrAreaCode.action", {field : tmp_str}, function(data) {
			if( data.field != ""){
				alert("The terminal address and administration code has been used by  " + data.field+ "！");
				$("#rtuAddr").select();
				return false;
			}
			else {
					var ae_params = $('#addorupdate').serialize();
					$.ajax({
						url      : YDDef.BASEPATH + "ajaxdocs/actRtuPara!addOrEdit.action", //后台处理程序
						type     : 'post',					 							//数据发送方式
						data     : ae_params, 												//要传递的数据；就是上面序列化的值
						success  : function(data) {								    	//回传函数
							if (data.result.indexOf("fail") >= 0) {
								if(data.result == "fail"){
									if(flag == 'add'){
										alert("Failed to add！");
									}else{
										alert("Failed to modify！");
									}
								}else{
									alert(data.result.substring(4));
								}
							}
							else{
								if(flag == 'add') {
									alert("Successfully Add！");
									window.returnValue = 1;
								}
								else {
									alert("Successfully Modify！");
									window.returnValue = 0;
								}
								window.close();
							}
						}
					});	
			}
		});
	}
}

function check() {
	if (!ckChar("describe", "Name", 64, true)) {
		return false;
	}
	if (!isNumber("rtuAddr", "Terminal Address", 0, 65535, true)) {
		return false;
	}	
	if(!ckChar("showconsId","Residential Area",64,true)){
		return false;
	}
	if (!isNumber("authCode", "Authentication Code", 0, 65535, true)) {
		return false;
	}
	if(!ckChar("assetNo","Asset No.",32)){
		return false;
	}
	if(!ckChar("barCode","Bar Code",32)){
		return false;
	}
	//验证"认证码"的长度
	if(!ckNumChar($("#authCode").val(),32)){
		alert("Authentication code length should be within 0-32 bytes, and only be number or character. ");
		return false;
	}
	if(!isMobilePhone("showSimcard","SIM card number")){
		return false;
	}
	return true;
}

function setDateValue(){
	tmp = ["instDate","stopDate","runDate"];
	for(var i=0;i<tmp.length;i++){
		var tmpvalue=$("#"+tmp[i]+"F").val();
		if(tmpvalue==""){
			$("#"+tmp[i]).val("");
		}else{
			$("#"+tmp[i]).val(tmpvalue.substring(0,4)+tmpvalue.substring(5,7)+tmpvalue.substring(8,10));
		}
	}
}
function setValue(){
	var flag = true;
	for ( var i = 0; i < loaded.length; i++) {
		flag = flag && loaded[i];
	}
	if (!flag) {
		window.setTimeout('setValue()', 50);
		return;
	}
	
	if(params.id != undefined){
		var id = params.id;
		if(id == ""){
			
		}else{
			$("#id").val(id);
			var field = "id,describe,useFlag,rtuModel,protType,rtuAddr,chanMain,chanBak,simcardId,residentNum,jlpNum,consId,areaCode,factory,runStatus,fzcbId,instSite,instMan,instDate,stopDate,runDate,madeNo,assetNo,infCode1,infCode2,barCode,rtuIpaddr,rtuIpport,onlineType,authCode,authCodelen";
			$.post(YDDef.BASEPATH  + "ajaxdocs/actRtuPara!getRtuParaCommParaById.action", {result : id, field : field}, function(data) {
				var json = eval('(' + data.result + ')');
				var rowsplit = field.split(YDDef.SPLITCOMA);
				for(var i = 0; i < rowsplit.length; i ++){
					if(rowsplit[i] == "instDate"||rowsplit[i] == "stopDate"||rowsplit[i] == "runDate"){
						dateFormat.date = json.data[i];
						$("#"+rowsplit[i]+"F").val(dateFormat.formatToYMD(1));
					}
					else{
						if(rowsplit[i] == "consId"){
							$.post(YDDef.BASEPATH + "ajax/actCommon!loadDBDesc.action",{tableName : YDTable.TABLECLASS_CONSPARA, result : json.data[i]},function(data2){
								if(data2.result!=""){
									var array = eval('(' + data2.result + ')');
									if(array && array[0]){
										$("#showconsId").val(array[0].text);
									}
								}
							});
						}
						$("#"+rowsplit[i]).val(json.data[i]);
					}
				}
				$("#preconsId").val($("#consId").val());
			});
		}
	}
}