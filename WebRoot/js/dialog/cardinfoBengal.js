
$(document).ready(function(){
	readCardBengal();
});

function readCardBengal() {
	loading.loading();
	var strinfo = card_comm.readCardBengal();

	if(strinfo != "") {
		$.post(def.basePath + "ajaxoper/actOperDyBengal!decryptDataToCard.action", 		//后台处理程序
				{
					result : strinfo
				},
				function(data){
					loading.loaded();
					if(data.result != ""){
						json = eval("(" + data.result + ")");
						setValue(json);
					}
					else{
						alert("Impossible de lire la carte！");
					}
				}
		);
	}
}

function setValue (json) {
//	var id = "block1_answerToReset,block1_version,block1_meterID,block1_customerID,block1_utilityID,block1_sanctionedLoad,block1_meterType,block1_lastRechargeAmount,block1_lastRechargeDate,block1_lastTransactionID,block2_cardType,block2_cardUsedFlag,block2_tokenTotalNumber,block3_token,block4_meterID,block4_consumerID,block4_utilityID,block6_tokenReturnCode";//,block7_LastMonth1,block7_LastMonth2,block7_LastMonth3,block7_LastMonth4,block7_LastMonth5,block7_LastMonth6,block8_returnToken";
//	var ids = id.split(',');
//	for(var i=0; i<ids.length; i++) {
//		$("#other"+ (i+1)).html(json.(eval(ids[i])));		
//	}
	
	if ((json.block1_answerToReset == "") && (json.block1_version == "")) {
		alert("Disparité des clés secrètes！");
		return;
	}

	var str = jsonString.json2String(json);
	var jsons = str.split(',');
	var indexId = 1;
	
	$("#other"+ (indexId++)).html(json.block1_answerToReset);//parseInt(json.block1_answerToReset,10).toString(16));

	for(var i=1; i<13; i++) {
		var key = jsons[i].split(':')[0];
		var val = jsons[i].split(':')[1].split('"')[1].split('"')[0];
		$("#other"+ (indexId++)).html(val);
	}
	
	for(var i=0; i<json.block3_token.length; i++) {
		var block3Token = json.block3_token.split(',');
		$("#other"+ (indexId++)).html(block3Token[i]);
	}
	
	indexId = 39;
	$("#other"+(indexId++)).html(json.block4_meterID);
	$("#other"+(indexId++)).html(json.block4_consumerID);
	$("#other"+(indexId++)).html(json.block4_utilityID);
	
	for(var i=0; i<json.block6_tokenReturnCode.length; i++) {
		var block6Token = json.block6_tokenReturnCode.split(',');
		$("#other"+ (indexId++)).html(block6Token[i]);
	}

	indexId = 67;
	$("#other"+(indexId++)).html(json.block6_RechargeDateTime);
	$("#other"+(indexId++)).html(json.block6_RechargeAmount);

	for(var i=0; i<6; i++){
		$("#other"+ (indexId++)).html(eval("json.block7_LastMonth"+(i+1)+"").block7_billingDate);
		$("#other"+ (indexId++)).html(eval("json.block7_LastMonth"+(i+1)+"").block7_activeEnergyImport);
		$("#other"+ (indexId++)).html(eval("json.block7_LastMonth"+(i+1)+"").block7_takaRecharged);
		$("#other"+ (indexId++)).html(eval("json.block7_LastMonth"+(i+1)+"").block7_takaUsed);
		$("#other"+ (indexId++)).html(eval("json.block7_LastMonth"+(i+1)+"").block7_activeMaximumPower);
		$("#other"+ (indexId++)).html(eval("json.block7_LastMonth"+(i+1)+"").reactiveMaximumPower);
		$("#other"+ (indexId++)).html(eval("json.block7_LastMonth"+(i+1)+"").block7_activeEnergyImportT1);
		$("#other"+ (indexId++)).html(eval("json.block7_LastMonth"+(i+1)+"").block7_activeEnergyImportT2);
		$("#other"+ (indexId++)).html(eval("json.block7_LastMonth"+(i+1)+"").block7_reactiveEnergyImportT1);
		$("#other"+ (indexId++)).html(eval("json.block7_LastMonth"+(i+1)+"").reactiveEnergyImportT2);
		$("#other"+ (indexId++)).html(eval("json.block7_LastMonth"+(i+1)+"").block7_totalChargeT1);
		$("#other"+ (indexId++)).html(eval("json.block7_LastMonth"+(i+1)+"").block7_totalChargeT2);
		$("#other"+ (indexId++)).html(eval("json.block7_LastMonth"+(i+1)+"").block7_numberOfPowerFailures);
		$("#other"+ (indexId++)).html(eval("json.block7_LastMonth"+(i+1)+"").numberOfSanctionedLoadExceeded);
		$("#other"+ (indexId++)).html(eval("json.block7_LastMonth"+(i+1)+"").block7_monthAveragePowerFactor);
	}
	$("#other"+(indexId++)).html(json.block8_returnToken);
	$("#other"+(indexId++)).html(json.block8_updateFlag);
	$("#other"+(indexId++)).html(json.block8_keyNo);
	$("#other"+(indexId++)).html(json.block8_seqNo);
}

function getEmptyString (str_len) {
	var ret_str = "";
	
	var count = (str_len + 127) / 128;
	for (var i = 0; i < count; i++) {
		ret_str += "                                                                                                                               ";
	}
		
	return ret_str;
}