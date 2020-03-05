var params = window.dialogArguments;
var param = params.id.split(",");//mpid,rtuid
var weekday = new Array(8);//直接定义并初始化
var addcheck=false;
$(document).ready(function(){
//	loading.loading();
	initMeterpara();
	initDiction();

	$("#tianjia").click(function(){
		if(check()){addOrEdit('add')};
	});
	
	$("#xiugai").click(function(){
		if(check()){
			addOrEdit("edit");
		};
	});
	
	if($("#weekday").val() == 1){
		enableCheck();
	}else{
		disableCheck();
	}
	
	$("#weekday").change(function(){
		if($("#weekday").val() == 1){
			enableCheck();
		}else{
			disableCheck();
		}
		checkWeekday();		
	});
});

function onClickDiag(diagType){
	//diagType	"pt"/"ct"
	modalDialog.height = 200;
	modalDialog.width = 300;
	modalDialog.url = "../dialog/ctDialog.jsp";
	
	modalDialog.param = {diagType : diagType, lang : window.top._lang, doc_per : window.top._doc_per};
	var rtnValue = modalDialog.show();
	alert(rtnValue);
	if(rtnValue == undefined || rtnValue == null){
		return;
	}
	
	
}

function checkWeekday() {
	//初始化数组
	for(var i = 0; i < weekday.length; i++) {
		weekday[i] = '0';
	}
	//是否使用
	if($("#weekday").val() == 1) weekday[7] = '1';
	//星期日
	if($("#sunday").attr("checked") == true) weekday[6] = '1';
	//星期一
	if($("#monday").attr("checked")==true) 	weekday[5] = '1';
	//星期二
	if($("#tuesday").attr("checked")==true) weekday[4] = '1';
	//星期三
	if($("#wednesday").attr("checked")==true) weekday[3] = '1';
	//星期四
	if($("#thursday").attr("checked")==true) weekday[2] = '1';
	//星期五
	if($("#friday").attr("checked")==true) weekday[1] = '1';
	//星期六
	if($("#saturday").attr("checked")==true) weekday[0] = '1';

	$("#reserve1").val(weekday.join(""));
}

function enableCheck(){
	$("#monday").attr("disabled",false);
	$("#tuesday").attr("disabled",false);
	$("#wednesday").attr("disabled",false);
	$("#thursday").attr("disabled",false);
	$("#friday").attr("disabled",false);
	$("#saturday").attr("disabled",false);
	$("#sunday").attr("disabled",false);
	$("#protSt").attr("readonly",false);
	$("#protEd").attr("readonly",false);
	$("#protSt").css("background-color","");
	$("#protEd").css("background-color","");
}

function disableCheck(){
	$("#monday").attr("disabled",true);
	$("#tuesday").attr("disabled",true);
	$("#wednesday").attr("disabled",true);
	$("#thursday").attr("disabled",true);
	$("#friday").attr("disabled",true);
	$("#saturday").attr("disabled",true);
	$("#sunday").attr("disabled",true);
	$("#protSt").attr("readonly",true);
	$("#protEd").attr("readonly",true);
	$("#protSt").css("background-color","#DCDCDC");
	$("#protEd").css("background-color","#DCDCDC");

}

function initMeterpara() {
	var text = '{item:["' + Dict.DICTITEM_FACTORY 
		+ '","' + Dict.DICTITEM_USEFLAG + '"]}';
	
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : text}, function(data){
		var json = eval('(' + data.result + ')');
		var iid = 0;
		//生产厂家
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_FACTORY);
		for ( var i = 0; i < dict.length; i++) {
			$("#factory").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		//周末日使用标志
		dict = eval("json.rows[" + (iid) + "]."+Dict.DICTITEM_USEFLAG);		
		for ( var i = 0; i < dict.length; i++) {
			$("#weekday").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		
		setMeterValue();	
	});
}

function setMeterValue() {
	var flag = true;
	//flag = flag && loaded;
	if (!flag) {
		window.setTimeout('setMeterValue()', 50);
		return;
	}	
	if(param != undefined){
		
		var meterid = param[0], rtuid = param[1];

		if(meterid == ""){//父页面点击“增加”时
			$("#xiugai").attr("disabled",true);
			//$("#id_mp").attr("disabled",true);
			
			$.post(def.basePath + "ajaxdocs/actYffPara!getResValueText.action",{result:rtuid},function(data1){
				if(data1.result != ""){
					var json1 = eval('(' + data1.result + ')');
					for ( var j = 0; j < json1.length; j++) {										
						document.getElementById("residentId").options.add(new Option(json1[j].text,json1[j].value));										
					}
				}								
			});
			$("#rtu_describe").val(param[2]);
		 addcheck=true;
		}
		else{//点超链接初始化
			$("#id_mp").attr("style","width:160px;background-color:#DCDCDC;border: 1px solid #808080;");
			$("#xiugai").attr("disabled",false);
			$("#id").val(meterid);
			$("#id_mp").val(meterid);
			
			var field = "tariffIndex,ptRatio,ctRatio,id_mp,describe_mpp,mpType,commAddr,commPwd,madeNo,assetNo,factory,prepayflag,residentId,reserve1,rtu_describe,ctDenominator,rp,mi,bdFactor,vfactor";
			$.post(def.basePath + "ajaxdocs/actYffPara!getMeterParaById.action", {
				result : meterid+","+rtuid, field : field
				}, function(data) {
				var json = eval('(' + data.result + ')');
				var rowsplit = field.split(SDDef.SPLITCOMA);
				
				for(var i = 0; i < rowsplit.length; i ++){
					if(rowsplit[i] == "residentId"){
						if(json.data[i]!=""){
							var resId = json.data[i];
							$.post(def.basePath + "ajaxdocs/actYffPara!getResValueText.action",{result:rtuid},function(data1){
								if(data1.result != ""){
									var json1 = eval('(' + data1.result + ')');
									for ( var j = 0; j < json1.length; j++) {
										if(json1[j].value!=resId) {
											document.getElementById("residentId").options.add(new Option(json1[j].text,json1[j].value));
										}
										else{
											document.getElementById("residentId").options.add(new Option(json1[j].text,json1[j].value));
											document.getElementById("residentId").value=json1[j].value;
										}
									}
								}								
							});
						}
					}else if(rowsplit[i] == "reserve1"){
						var reserve1 = json.data[i];
						initReserve(reserve1);
					}else if(rowsplit[i] == "ctDenominator"){
						dateFormat.date = json.data[i];
						$("#"+rowsplit[i]+"F").val(dateFormat.formatToYMD(1));
					}
					else{$("#"+rowsplit[i]).val(json.data[i]);}
				}
			});
		}
	}
}

function initReserve(reserve){
	
	for(var i=0; i<8; i++) {
		var tmpval = reserve.substring(i, i+1);
		
		if(tmpval == '1') {
			switch(i) {
				case 0: //星期六
					$("#saturday").attr("checked",true);
					break;
				case 1: //星期五
					$("#friday").attr("checked",true);
				    break;
				case 2:
					$("#thursday").attr("checked",true);
					break;
				case 3:
					$("#wednesday").attr("checked",true);
					break;
				case 4:
					$("#tuesday").attr("checked",true);
					break;
				case 5: //星期一
					$("#monday").attr("checked",true);
					break;
				case 6: //星期日
					$("#sunday").attr("checked",true);	
					break;
			}
		}
		
		if(i == 7) { // 使用标志
			if( (tmpval) == '1'){
				$("#weekday").val(1);
				enableCheck();
			}else{
				$("#weekday").val(0);
				disableCheck();
			}
		}
		
	}
}

function initDB(){
	var tables = '{"tables":["' + YDTable.TABLECLASS_YFFRATEPARA +'"]}';
	$.post(def.basePath + "ajax/actCommon!getValueAndText.action",{tableName: tables},function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			var json0 = eval("json.rows[0]."+YDTable.TABLECLASS_YFFRATEPARA);			
			for ( var i = 0; i < json0.length; i++) {
				$("#feeprojId").append("<option value="+json0[i].value+">"+json0[i].text+"</option>");
			}
			setValue();
		}
	});
}

function initDiction(){
	var text = '{item:["'
		+ Dict.DICTITEM_PREPAYTYPE + '","' 
		+ Dict.DICTITEM_FEETYPE + '","' 
		+ Dict.DICTITEM_PAYTYPE + '","'  
		+ Dict.DICTITEM_YFFMONEY + '","' 
		+ Dict.DICTITEM_YFFMETERTYPE + '"]}';
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : text}, function(data){
		
		initDB();
		
		var json = eval('(' + data.result + ')');
		var iid = 0;

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
		dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_YFFMETERTYPE);
		for ( var i = 0; i < dict.length; i++) {
			$("#yffmeterType").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		
	});
}

function setValue(){//根据id,rtuid 取得一条记录。	
	if(param != undefined){
		var id = param[0], rtuid = param[1];
		$("#rtuId").val(rtuid);
		if(id == ""){//父页面点击“新增”时

		}else{
			$("#xiugai").attr("disabled",false);
			$("#mpId").val(id);
			//field:界面标签的id组合
			var field = "feectrlType,caclType,payType,feeprojId,protSt,protEd," +
					"keyVersion,tariffIndex,keyExpiryNumber,yffmeterType,tzVal," +
					"prepayflag";		
			$.post(def.basePath + "ajaxdocs/actYffPara!getYffParaById.action", {result : id+","+rtuid, field : field}, function(data) {
				loading.loaded();
				var json = eval('(' + data.result + ')');
				var rowsplit = field.split(SDDef.SPLITCOMA);
				
				//20140606 zp 存储计费方式，卡表类型，外接卡表类型，重新查库，加载并选择对应的外接卡表类型下拉列表
				var caclType, yffmeterType, ocardproj_id;
//				20150307
				for(var i = 0; i < rowsplit.length; i ++){
					if(rowsplit[i] == "prepayflag" && json.data[i] == 0 ){
						$("#caclType").html("");
						$("#caclType").append("<option value='0'>无</option>");
						$("#caclType").val(0);
					}
					if (rowsplit[i] == "caclType" ) {
						caclType =  json.data[i];
					}
					else if (rowsplit[i] == "yffmeterType" ) {
						yffmeterType =  json.data[i];
					}
					window.setTimeout("setValue_('"+rowsplit[i]+"','"+json.data[i]+"')",1);
				}
			});
		}
	}
}

function setValue_(id, data){
	$("#"+id).val(data);
}

function setDateValue(){
	var tmpvalue = $("#ctDenominatorF").val();
	if(tmpvalue  == ""){
		$("#ctDenominator").val("");
	}else{
		$("#ctDenominator").val(tmpvalue.substring(0,4)+tmpvalue.substring(5,7)+tmpvalue.substring(8,10));
	}
}

function addOrEdit(flag){//修改	
	setDateValue();
	checkWeekday();	
	if(flag == 'add'){
		$("#rtuid_mp").val("");
		if(addcheck){//id_mmp不为空表示  需要校验mpID是否存在
			$("#id_mpp").val($("#id_mp").val());
		}
		else{ //id_mmp为空表示  查询maxid+1
			$("#id_mpp").val("");
		}
	}
	else{
		$("#id_mpp").val($("#id_mp").val());
		$("#rtuid_mp").val(param[1]);
	}
	
	formatMeterAddr();
	var params = $('#addorupdate').serialize();

	$.ajax({
		url      : def.basePath + "ajaxdocs/actYffPara!addOrEdit.action", 	//后台处理程序
		type     : 'post',					 								//数据发送方式
		data     : params, 													//要传递的数据；就是上面序列化的值
		success  : function(data) {								    		//回传函数
			if (data.result == "existID"){
				alert("Échec de l'ajout, l'ID d'enregistrement existe.！");
				$("#id_mp").select();
			}
			else if (data.result == "maxID"){
				alert(" Échec de l'ajout, l'ID d'enregistrement a atteint max 1024.！");
				$("#id_mp").select();
			}
			else if(data.result == "INVALID METER ADDRESS"){
				alert("Erreur de chiffre Luhn dans le numéro DRN");
			}
			else if (data.result == "existAddr"){
				var sjson = eval('(' + data.field + ')');
				var msg   = "Fail to add" + (flag == 'add'? "add":"modify") + "，the meter ID is existed！\n\n";
				msg = msg + "terminal:[" + sjson.rtuId + "]" + sjson.rtuDesc + "\n"; 
				msg = msg + "meter:[" + sjson.mpId + "]" + sjson.mpDesc + "\n";
				msg = msg + "residents:[" + sjson.resId + "]" + sjson.resDesc + "\n\n";
				alert(msg);
			}
			else if (data.result == "fail") {
				if(flag == 'add'){
					alert("défaut d'ajouter！");
				}
				else{
					alert("défaut de modification！");
				}
			}
			else{
				if(flag == 'add'){
					window.returnValue = 1;
				}
				else{
					window.returnValue = 0;
				}
				window.close();
			}
		}
	});
}

//电表地址长度小于10位的，前边补零
function formatMeterAddr(){
	var commAddr = $("#commAddr").val();
	while(commAddr.length < 10){
		commAddr = '0' + commAddr;
	}
	$("#commAddr").val(commAddr);
}

function check() {
	
	if (!isDbl("ctRatio", "ctRatio", 0,64, false)) {
		return false;
	}
	if (!isDbl("ptRatio", "ptRatio", 0,64, false)) {
		return false;
	}
	if (!isNumber("protSt", "Friendly Hour Start",0,24, false)) {
		return false;
	}
	if (!isNumber("protEd", "Friendly Hour End",0,24, false)) {
		return false;
	}
	if (!isNumber("keyVersion", "Key Version",0,127, false)) {
		return false;
	}
	if (!isNumber("tariffIndex", "Key Version",0,127, false)) {
		return false;
	}
	//meterpara start
//	if (!isNumber("id_mp", "Record ID",1,1024,true)) {
//		return false;
//	}	
	if (!ckChar("describe_mpp", "Meter Name", 64, true)) {
		return false;
	}
	if($("#residentId").val() == null){
		alert("Veuillez ajouter un fichier résident!");
		return false;
	}
	if (!ckChar("commAddr", "Meter ID",13, true)) {
		return false;
	}
	
	if (!isNumber("keyVersion", "Key Revision Number",1,9,true)) {
		return false;
	}
	
	//meterpara end
	
	return true;
}