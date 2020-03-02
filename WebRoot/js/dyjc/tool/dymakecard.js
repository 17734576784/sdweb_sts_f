var rtnValue    = null;
var other_type	= 0;
var feilv_type	= 0;
var metertoken,class2Token;
var printdata1,printdata2;
$(document).ready(function() {
	$("#btnSearch").click(function(){	clear();selcons()});//检索
 	$("#maketoken").click(function(){	maketoken()});	 	
 	$("#saveClass2Token").click(function(){	saveC2token()}); 	
 	$("#printC1Token").click(function(){printPayRec(1)});
 	$("#printC2Token").click(function(){printPayRec(2)});
	setDisabled(true);
 
	changeshowvalue();
	changecontrolvalue();
	$("#savetoken").attr("disabled",true);
	$("#printC1Token").attr("disabled",true);
 	$("#printC2Token").attr("disabled",true);
});

function clear(){
	$("#class2token").html("");
	$("#class1token").html("");
	$("#metertoken").html("");	
	$("#printC1Token").attr("disabled",true);
 	$("#printC2Token").attr("disabled",true);
 	$("#savetoken").attr("disabled",true);
 	
 	$("#chkclass2").attr("checked",false); 	
	$("#chkclass1").attr("checked",false);
 	$("#chkmeterkey").attr("checked",false);	 		
}


function printPayRec(printdata){//打印
	
	//基本信息
	var printParam = {};	
	
	printParam.customerID 	= $("#userno").text();
	printParam.meterNo 		= $("#commaddr").text();
	printParam.conName 		= $("#username").text();
	if(class2token == 1){
		printParam.token1		= $("#class1token").text();		
	}else{
		printParam.token1		= $("#class2token").text();
	}


	modalDialog.width 	= 250;
	modalDialog.height 	= 600;
	modalDialog.param	= printParam;
	modalDialog.url 	= basePath + "jsp/dyjc/print/stsToolTemplate.jsp";
		
	modalDialog.show();
	
//	window.top.WebPrint.setTool2params(printdata,window.top.WebPrint.nodeIdx.tool);//打印用的参数
// 	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.tool); 
//	if(filename == undefined || filename == null)return;
//	window.top.WebPrint.prt_params.ext_info = "";	
//	window.top.WebPrint.prt_params.file_name = filename;
//	window.top.WebPrint.prt_params.reprint = 0;
//	window.top.WebPrint.doPrintTool();
}

function saveC2token(){
	var json = {};
	json.rtuId =  rtnValue.rtu_id;
	json.mpId  =  rtnValue.mp_id;
	if(class2Token == undefined)
	{
		alert("The token is not generated, and then save the token.");
		return;
	}
	json.class2Token = class2Token;
 	$.post(def.basePath+"ajaxdyjc/actMakeTokens!saveClass2Token.action",
 			{
 			result:jsonString.json2String(json)
 			},
 			function(data){
 				if(data.result != ""){
					alert("Save successfully.");
  				} 				
 			} 		
 		); 		
}

function savemetertoken(){
	var json = {};
	json.rtuId =  rtnValue.rtu_id;
	json.mpId  =  rtnValue.mp_id;
	json.meterToken = metertoken;
	 
	$.post(def.basePath+"ajaxdyjc/actMakeTokens!saveMeterToken.action",
 			{
 			result:jsonString.json2String(json)
 			},
 			function(data){
 				if(data.result != ""){
 					alert("Save successfully.");
  				} 				
 			} 		
 		); 		
}

function selcons(){//检索
	var rtnValue1 = doSearch("ks",ComntUseropDef.YFF_DYOPER_PAY, rtnValue);
	if(!rtnValue1){
		return;
	}

	setDisabled(false);
	rtnValue = rtnValue1;
	fillInfoValue(rtnValue);
}


function fillInfoValue(obj){
	if( !obj) return;
	$("#userno").html(obj.userno);
	$("#username").html(obj.username);
	$("#cus_state").html(obj.cus_state);
	$("#tel").html(obj.tel);
	$("#useraddr").html(obj.useraddr);
	$("#commaddr").html(obj.commaddr);
	$("#yffmeter_type_desc").html(obj.yffmeter_type_desc);
	$("#factory").html(obj.factory);
	$("#utilityid").html(obj.utilityid);
	
	$("#feeproj_desc").html(obj.feeproj_desc);
	$("#feeproj_detail").html(obj.feeproj_detail);
	$("#friend_s").html(obj.friend_s);
	$("#friend_e").html(obj.friend_e);
 	$("#money_limit").html(obj.money_limit);
	$("#tzval").html(obj.tzval);
	$("#fmaximum_power").html(obj.fmaximum_power);
	$("#gmaximum_power").html(obj.gmaximum_power);
	$("#power_start").html(obj.power_start);
	$("#power_end").html(obj.power_end);
	$("#power_date").html(formatMDH(obj.power_date,"YMD_FULL"));		////最大功率激活日期
	
 	$("#DRN").val(obj.commaddr);
	$("#KRN").val(obj.key_version);
	$("#TI").val(obj.rateid);	
	$("#newMeterNo").val(obj.commaddr);
	
	$("#seqNo").val(obj.seqNo);		//开户seqNo默认为2，以后每次缴费需要增1,到200置1
	$("#keyNo").val(obj.keyNo);		//开户keyNo默认为1,以后每次缴费需要增1,到200置1
	$("#friendDate").val(obj.friendDate);
	$("#SGC").val(obj.r_org_no);	
	
	$("#newSGC").val(obj.r_org_no);
	$("#newKRN").val(obj.key_version);
	$("#newKEN").val(obj.ken);
	$("#newTI").val(obj.rateid);
}
 
function maketoken() {
	$("#class1token").html("");
	$("#class2token").html("");
	$("#metertoken").html("");
	$("#savetoken").attr("disabled",true);
	
	//$("#saveClass2Token").attr("disabled",true);
	
	var json={};
	json.tokentype=$("#tokentype").val();
	json.showvalue=$("#class2value").val();
	json.RND=$("#RND").val();
	json.TID=$("#TID").html();
	json.subclass=$("#subclass").html();
	json.class2=$("#class2").html();		
	
	json.newmeterkey=$("#newmeterkey").val();
	json.newSGC=$("#newSGC").val();
	json.newKEN=$("#newKEN").val();
	
	json.newKRN=$("#newKRN").val();
	json.newMeterNo=$("#newMeterNo").val();
	json.newKT=$("#newKT").val();
	json.newTI=$("#newTI").val();
	
	if($("#ro").attr("checked")==true){
		json.ro=1;
	}else{
		json.ro=0;
	}		
	if($("#res").attr("checked")==true){
		json.res=1;
	}else{
		json.res=0;
	}
		
	json.selecttest=$("#selecttest").val();
	json.controlvalue=$("#controlvalue").val();
	json.subclass1=$("#subclass1").val();
	json.class1=$("#class1").html();
	json.mfrcode=$("#mfrcode").val();   		
	
	json.KT  = $("#keytype").val();
 	json.SGC = $("#SGC").val();
 	json.TI  = $("#TI").val(); 		
	json.KRN = $("#KRN").val();			
	json.IIN = $("#IIN").html();	
	json.DRN = $("#DRN").val();	
 	json.DKGA= $("#DKGA").val(); 
 		
 	json.rtuId =  rtnValue.rtu_id;
	json.mpId  =  rtnValue.mp_id;
	json.resNo =  rtnValue.userno;
	json.resDesc = rtnValue.username;
	
 	json.vk1   =  $("#VK1").text();
 		
 	json.meterAddr = $("#commaddr").html();	//表地址 
 	var num = 0;
 	if($("#chkclass2").attr("checked")){ 	
		if(num == 0){
			makeClass2Token(json);
		}
		num++;
 	}	
 	
	if($("#chkclass1").attr("checked")){ 
		if(num == 0){
			 makeClass1Token(json) 	
		 }
		num++;
	} 
	
   	if($("#chkmeterkey").attr("checked")){ 		 		
   		if(num == 0){
   			makeMeterToken(json);
   		}
   		num++;
 	} 
   	
   	if (num == 0) {
		alert("Select the item to generate Token.");
	}   	
}


function makeClass2Token(json){		
	if(($("#tokentype").val() == 3  || $("#tokentype").val() == 4) ){
		if(!ckChar("newmeterkey","New Meter Key",20,true)){
			return;
		}	
	}
 	$.post(def.basePath+"ajaxdyjc/actMakeTokens!GenerateClass2Token.action",
 		{
 			result:jsonString.json2String(json)
 		},
 		function(data){
 			if(data.result != ""){
				$("#class2token").css("font","bold 18px red");
   				$("#class2token").html("Token1 :"+data.result);
   				class2Token = data.result;
   				printdata2 = data.params;
  				$("#printC2Token").attr("disabled",false);
 			} 				
 		} 		
 	); 		
	
	if($("#chkclass1").attr("checked")){ 		
		 makeClass1Token(json) 		 
	}
	
   	if($("#chkmeterkey").attr("checked")){ 		 		
   		makeMeterToken(json);
 	} 
}

function makeClass1Token(json){	
	 
//	if(!isNumber("subclass1","Sub class",0,15,true)){
//		return;
//	}	
//
//	if(!isNumber("mfrcode","Mfrcode",0,9999,true)){
//		return;
//	}	 
	
	$.post(def.basePath+"ajaxdyjc/actMakeTokens!GenerateClass1Token.action",
 			{
 			result:jsonString.json2String(json)
 			},
 			function(data){
 				if(data.result != ""){
 					$("#class1token").css("font","bold 18px red");
   					$("#class1token").html("Token :"+data.result);
   					printdata1 = data.params;
//   				::	window.top.WebPrint.setTool2params(data.params,window.top.WebPrint.nodeIdx.tool);//打印用的参数
					$("#printC1Token").attr("disabled",false);
 				} 				
 			} 		
 		); 	
	if($("#chkmeterkey").attr("checked")){ 		 		
   		makeMeterToken(json);
   		return
 	} 
}

function makeMeterToken(json){
		$.post(def.basePath+"ajaxdyjc/actMakeTokens!GenerateDES.action",
 			{
 			result:jsonString.json2String(json)
 			},
 			function(data){
 				if(data.result != ""){
 					$("#metertoken").css("display","block");
					$("#metertoken").css("font","bold 18px red");
   					$("#metertoken").html("Meter Key :"+data.result);
   					metertoken = data.result;
   					$("#newmeterkey").val(data.result);
   					$("#savetoken").attr("disabled",false);
 				} 			 				
 			} 		
 		); 	
}


function gettempArray() {
	
	var temp = new Array();	
	var json = {};
	
	json.meterNo = $("#commaddr").html();
 	json.customerID = $("#userno").html();
	json.utilityID = $("#utilityid").html();
	json.seqNo = $("#seqNo").val();
	json.keyNo = $("#keyNo").val();
	json.feeprojId = $("#feeproj_id").val();
	
	if($("#max_power").attr("checked")){
		temp.push(1);
		json.fmaximum_power=$("#fmaximum_power").html();
		json.gmaximum_power=$("#gmaximum_power").html();
		json.power_start=$("#power_start").html();
		json.power_end=$("#power_end").html();
		json.power_date=rtnValue.power_date;
	}else{
		temp.push(0);
	}
	if($("#friend_mode").attr("checked")){
		temp.push(2);
		json.friend_s=$("#friend_s").html()
		json.friend_e=$("#friend_e").html()
		json.friendDate=$("#friendDate").val()
	}else{
		temp.push(0);
	}
	if($("#credit_amount").attr("checked")){
		temp.push(3);
		json.money_limit=$("#money_limit").html()
	}else{
		temp.push(0);
	}
	if($("#overdraw_amount").attr("checked")){
		temp.push(4);
		json.tzval=$("#tzval").html()
	}else{
		temp.push(0);
	}
	if($("#clear_event").attr("checked")){
		temp.push(5);
	}else{
		temp.push(0);
	}
	if($("#tariff").attr("checked")){
		temp.push(6);
	}else{
		temp.push(0);
	}
//	temp.push(json);
	var result = temp.toString() + "-" + jsonString.json2String(json);
	
	return result;
}

function setDisabled(mode){	
	
	$("#chkclass2").attr("disabled",mode);
	$("#tokentype").attr("disabled",mode);
	$("#class2value").attr("disabled",mode);
	
	$("#newKRN").attr("disabled",mode);
	$("#newMeterNo").attr("disabled",mode);
	$("#newKT").attr("disabled",mode);
	$("#newTI").attr("disabled",mode);
	$("#ro").attr("disabled",mode);
	$("#res").attr("disabled",mode);
	$("#RND").attr("disabled",mode);
	
	
	$("#chkclass1").attr("disabled",mode);
	$("#selecttest").attr("disabled",mode);
	$("#subclass1").attr("disabled",mode);	 
	$("#controlvalue").attr("disabled",mode);
	$("#mfrcode").attr("disabled",mode);
	
	$("#chkmeterkey").attr("disabled",mode);
	$("#keytype").attr("disabled",mode);
	$("#TI").attr("disabled",mode);
	$("#SGC").attr("disabled",mode);
	$("#KRN").attr("disabled",mode);
	$("#DKGA").attr("disabled",mode);
	$("#DRN").attr("disabled",mode);
	$("#SGC").attr("disabled",mode); 
	$("#maketoken").attr("disabled",mode);	
}

function changeshowvalue(){
	var tokentype = $("#tokentype").val();
	switch(tokentype){
		case "0":
			$("#showvalue").html("MPL(Watts):");
			$("#subclass").html("0");			
			$("#class2value").css("display","block");
			$("#tr1").css("display","none");
			$("#tr2").css("display","none");
			$("#tr3").css("display","none");	
			$("#saveClass2Token").attr("disabled",true);
			break;
		case "1":
			$("#showvalue").html("Register:");
			$("#subclass").html("1");
			$("#class2value").css("display","block");
			$("#tr1").css("display","none");
			$("#tr2").css("display","none");
			$("#tr3").css("display","none");
			$("#saveClass2Token").attr("disabled",true);
			break;
		case "3":
			$("#showvalue").html("");
			$("#subclass").html("3");
			$("#class2value").css("display","none");
			$("#tr1").css("display","block");
			$("#tr2").css("display","block");
			$("#tr3").css("display","block");	
			$("#saveClass2Token").attr("disabled",false);
			break;
		case "4":
			$("#showvalue").html("");
			$("#subclass").html("4");
			$("#class2value").css("display","none");
			$("#tr1").css("display","block");
			$("#tr2").css("display","block");
			$("#tr3").css("display","block");	
			$("#saveClass2Token").attr("disabled",true);
			break;
		case "5":
			$("#showvalue").html("Pad:");
			$("#subclass").html("5");
			$("#class2value").css("display","block");
			$("#tr1").css("display","none");
			$("#tr2").css("display","none");
			$("#tr3").css("display","none");
			$("#saveClass2Token").attr("disabled",true);
			break;
		case "6":
			$("#showvalue").html("MPPUL:");
			$("#subclass").html("6");
			$("#class2value").css("display","block");
			$("#tr1").css("display","none");
			$("#tr2").css("display","none");
			$("#tr3").css("display","none");
			$("#saveClass2Token").attr("disabled",true);
			break;
		case "11":
			$("#showvalue").html("PropData:");
			$("#subclass").html("11");
			$("#class2value").css("display","block");
			$("#tr1").css("display","none");
			$("#tr2").css("display","none");
			$("#tr3").css("display","none");
			$("#saveClass2Token").attr("disabled",true);
			break;
	}
}

function changecontrolvalue(){
	var selecttest = $("#selecttest").val();
	switch(selecttest){
		case "0":
			$("#controlvalue").val("ffffffff");
  			break;
		case "1":
			$("#controlvalue").val("1");
  			break;
  		case "2":
			$("#controlvalue").val("2");
 			break;
		case "3":
			$("#controlvalue").val("4");
 			break;
		case "4":
			$("#controlvalue").val("8");
 			break;
		case "5":
			$("#controlvalue").val("10");
 			break;
		case "7":
			$("#controlvalue").val("40");
 			break;
 		case "8":
			$("#controlvalue").val("80");
 			break;
 		case "9":
			$("#controlvalue").val("100");
 			break;
 		case "10":
			$("#controlvalue").val("200");
 			break;
	}
}
