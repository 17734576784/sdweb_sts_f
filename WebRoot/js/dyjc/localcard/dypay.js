var rtnValue;
var feerate;
var payToken;
var jyDl	= "0.0";
var finalJyje	= 0;
var meterkey	= "";
var token1,token2,opentoken;
$(document).ready(function() {
	
	initGrid();
	$("#btnSearch").click(function(){clear();selcons()});//检索 
	$("#pay").click(function(){
		loading.loading();
		 
		if(check()){
			payOper();}
		else{
			loading.loaded();
		}
	});

 	$("#zje").keyup(function(){calcTotal();});
	
	setDisabled(true);
	$("#btnSearch").focus();
	$("#prt").click(function(){	
		printPayRec();
		//$("#zhongxin").css("display","block");
		//$("#zhongxin").jqprint();
	}).attr("disabled",true);
});

function selcons(){//检索
	var rtnValue1 = doSearch("ks",ComntUseropDef.YFF_DYOPER_PAY, rtnValue);
	if(!rtnValue1){
		return;
	}
	$("#zdl").html(0);	//电量
	setDisabled(false);
	rtnValue = rtnValue1;	
	fillInfoValue(rtnValue); 
}

function clear(){
	$("#paymenttoken").css("display","none");
	$("#zje").val("");
	$("#prt").attr("disabled",true);
}

function fillInfoValue(obj) {
	if(!obj)  return;
	
	$("#userno").html(obj.userno);
	$("#username").html(obj.username);
	$("#cus_state").html(obj.cus_state);
	$("#tel").html(obj.tel);
	$("#useraddr").html(obj.useraddr);
	$("#commaddr").html(obj.commaddr);
	$("#yffmeter_type_desc").html(obj.yffmeter_type_desc);
	$("#factory").html(obj.factory);
	$("#utilityid").html(obj.utilityid);
	
	$("#cacl_type_desc").html(obj.cacl_type_desc);
	$("#feectrl_type_desc").html(obj.feectrl_type_desc);
	$("#pay_type_desc").html(obj.pay_type_desc);
	$("#feeproj_desc").html(obj.feeproj_desc);
	$("#feeproj_detail").html(obj.feeproj_detail);
	$("#tzval").html(obj.tzval);
	
	$("#rtu_id").val(obj.rtu_id);
	$("#mp_id").val(obj.mp_id);
	$("#cacl_type").val(obj.cacl_type);
	$("#feectrl_type").val(obj.feectrl_type);
	$("#pay_type").val(obj.pay_type);
	$("#feeproj_id").val(obj.feeproj_id);
	
	$("#seqNo").val(obj.seqNo);		//开户seqNo默认为2，以后每次缴费需要增1,到200置1
	$("#keyNo").val(obj.keyNo);		//开户keyNo默认为1,以后每次缴费需要增1,到200置1
	$("#buy_times").html(obj.buy_times);
	$("#res_id").val(obj.res_id);
	
	//添加ct pt jy_money参数
	$("#ctData").html(obj.ct);
	$("#ptData").html(obj.pt);
	$("#jy_money").html(obj.jy_money);
	$("#KEN").val(obj.ken);
	
	feerate = obj.feeproj_detail;
	getRecord(obj.rtu_id, obj.mp_id, "", 10);
	$("#pay").attr("disabled",false);
}

//缴费操作 
function payOper(){
	if ($("#zdl").text() < 0) {
		loading.loaded();
		alert("kWh n'est pas valide!");
		return;
	}
	
	var params = {};
	params.meterNo = $("#commaddr").html();
	params.customerID = $("#userno").html();
	params.utilityID = $("#utilityid").html();
	params.seqNo = $("#seqNo").val();	
	params.keyNo = $("#keyNo").val(); 
	
	//开户信息 start
	params.DRN = rtnValue.commaddr;
	//params.SGC = rtnValue.r_org_no;
	params.SGC = rtnValue.sgc;
	params.KRN = rtnValue.key_version;
	params.TI = rtnValue.ti;
 
	params.KT  =  rtnValue.key_type;
 	params.IIN = "600727";	 
  	params.DKGA=  rtnValue.dkga;
  	params.rtuId=  rtnValue.rtu_id;
  	params.mpId=  rtnValue.mp_id;
  	params.alldl=  parseFloat($("#zdl").html())*10;
  	params.KEN=  	$("#KEN").val();
 	params.vk = rtnValue.vk1;
 	params.buy_times = 1;						//购电次数为1
 	params.regno = rtnValue.regno;
 	//开户信息 end
 	
	if($("#zje").val() == ""){
		loading.loaded();
		alert("Le montant du paiement ne peut pas être vide.");
		return
	}
	
	params.all_money = $("#zje").val();		 
	params.all_dl = $("#zdl").html()*10;	
	params.rnd = $("#buy_times").html();	
	params.rtuId = $("#rtu_id").val();
	params.mpId = 	$("#mp_id").val();
	params.ct		= $("#ctData").html();
	var operType = SDDef.YFF_OPTYPE_PAY;//操作类型：缴费
	
	//发向后台
	$.post(def.basePath + "ajaxoper/actOperDyBengal!operDY.action", 		//后台处理程序
		{
			params : jsonString.json2String(params),
			operType : operType
		},
		function(data){
			//$("#paymenttoken1").css("display","block");
			$("#paymenttoken1").css("font","bold 18px red");
			$("#paymenttoken2").css("font","bold 18px red");
			$("#paymenttoken3").css("font","bold 18px red");
 	
   			payToken = data.result;
   			//alert(payToken);
   			if(payToken.indexOf("keychange")>-1){
   				if(payToken.substring(9) == "0"){
	   				payToken = payToken.substring(10);
	    			var json = eval("("+ payToken +")");
	
	   				meterkey 	= json.meterKey;
	   				token1   	= json.token1;
	   				token2   	= json.token2;
	   				opentoken 	= json.opentoken;
	   				
	   				$("#firstLabel").html("1stKCT:");
	   				$("#secondLabel").html("2ndKCT:");
	   				$("#thirdLabel").html("Open Account Token:");
	   				$("#paymenttoken1").html(json.token1);
	   				$("#paymenttoken2").html(json.token2);
	   				$("#paymenttoken3").html(json.opentoken);
   				}else{
   					payToken = payToken.substring(10);
	    			var tokens = payToken.split(",");
	    			$("#firstLabel").html("1stKCT:");
	    			$("#secondLabel").html("2ndKCT:");
	    			$("#thirdLabel").html("Payment Token:");
	    			
	    			$("#paymenttoken1").html(tokens[0]);
	   				$("#paymenttoken2").html(tokens[1]);
	   				$("#paymenttoken3").html(tokens[2]);
	   				
	   				token1   	= tokens[0];
	   				token2   	= tokens[1];
	   				opentoken 	= tokens[2];
   				}
   			}else{
   				$("#firstLabel").html("Payment Token :");
    			$("#paymenttoken1").html(payToken);
   			}		
   			if (payToken == STSDef.STS_TOKEN_EXPIRED || payToken == STSDef.STS_TOKEN_ERROR || payToken == STSDef.STS_METERKEY_ERROR) {
   				loading.loaded();
   				alert(payToken);
   			}
   			else if (payToken!="" && payToken != STSDef.STS_KEYCHANGE) {
   				if($("#paymenttoken2").text() == ""){
   					saveDataToDB();   					
   				}else{
   					saveDataToDBByAdd();
   				}

			}else{
				loading.loaded();
				alert("Échec de paiement");
			}
		}
	);	
}

function CheckCardProc(result) {

	var json_out = eval("(" + result + ")");	
	if(json_out == undefined || json_out ==""){
		alert("Échec de la carte de lecture!");
		return  false;				
	}
	
	var meterNo,consNo,keyNo, seqNo;				//表号，户号,购电次数
	meterNo 	= json_out.block4_meterID; 		//表号
	consNo  	= json_out.block4_consumerID;	//户号
	keyNo	 	= json_out.block8_keyNo;
	seqNo	  	= json_out.block8_seqNo;
	if ("" == meterNo.replace(/(^0*)/g, ""))	{
		alert("ID de compteur invalide, échec de paiement!")
		return false;
	}
	else if ("" == consNo.replace(/(^0*)/g, ""))	{
		alert("Numéro de consommateur invalide, échec de paiement!")
		return false;
	}
	else if (json_out.block2_cardUsedFlag != 250) {
		alert("Vous n'avez pas encore inséré après le dernier paiement.")
		return false;
	}
	else if ((parseInt(keyNo) < 0) || (parseInt(seqNo) <=0)||(parseInt(keyNo) >=255) || (parseInt(seqNo) >=65535)) {
		alert("Les données retournées sont manquantes sur la carte, échec d'achat!")
		return false;
	}
	
	else if (rtnValue.userno.replace(/(^0*)/g, "") != consNo.replace(/(^0*)/g, "")) {
		alert("L'identifiant du consommateur est différent des informations système!")
		return false;
	} 
	else if (rtnValue.commaddr.replace(/(^0*)/g, "") != meterNo.replace(/(^0*)/g, "")) {
		alert("L'ID du compteur est différent avec les informations système, veuillez passer à la procédure de changement du compteur!")
		return false;
	}
	else if ((rtnValue.seqNo != seqNo )|| (rtnValue.keyNo != keyNo)) {
		alert("Les heures d'achat sont différentes avec les informations système, échec d'achat!")
		return false;
	}

	return true;
}

function saveDataToDBByAdd(){
	//原来库参数可能有多的，多去少补
	var param = {};
	param.rtuId = $("#rtu_id").val();
	param.mpId = $("#mp_id").val();
	param.cus_state = SDDef.YFF_CUSSTATE_NORMAL;//客户状态
	
	param.op_type = SDDef.YFF_OPTYPE_ADDRES;	//操作类型
	param.pay_money = $("#zje").val();			//缴费金额
	param.js_money = $("#jsje").val() ? $("#jsje").val() : 0;			//结算金额
	param.zb_money = 0; 						//追补金额，没有写死为0
	
 	param.all_money = $("#zje").val();
	param.all_dl = $("#zdl").html();
	param.buy_times = 1;						//购电次数为1
	
	param.token = opentoken;
	
	param.meterkey = meterkey;
	param.token1 = token1;
	param.token2 = token2;

	param.res_id = $("#res_id").val();			//客户编号,存储的是residentpara表中的id
	param.pay_type = $("#pay_type").val();		//缴费方式
	
	param.last_remain = 1;						//结余金额--再议,对应后台解析json，若没有，则后台java中也需去掉，不然报错
	param.remain_money = 1;						//剩余金额--再议,对应后台解析json，若没有，则后台java中也需去掉，不然报错
	
	param.seqNo = $("#seqNo").val();
	param.keyNo = $("#keyNo").val();
	
	param.KEN = $("#KEN").val();

	
	//需将电量转换成金额存库
	var fee = 0;
	fee = feerate.split("|");
	if (fee.length >=2 ) {
		fee = fee[1].split("/");
		if (fee.length >= 2) fee = fee[0];
	}
	else fee = feerate;

	finalJyje = round(parseFloat(jyDl) *  parseFloat($("#ctData").html()) * parseFloat($("#ptData").html()) * fee,2);
	param.jy_money = finalJyje;
	
	var operType = SDDef.YFF_OPTYPE_ADDRES;//操作类型：开户
	$.post(def.basePath + "ajaxoper/actSaveDBBengal!operDYSaveDB.action", 		//后台处理程序
			{
				params : jsonString.json2String(param),
				operType : operType
			},
			function(data){
				loading.loaded();
				if(data.result = "success"){	
					getRecord($("#rtu_id").val(), $("#mp_id").val(), "", 10);
		 			$("#pay").attr("disabled",true);
		 			$("#prt").attr("disabled",false);
		 			alert("success!");	
				}
				else{
					alert("Impossible de stocker la base de données, veuillez constituer l'enregistrement!");
				}
			});
}

//数据库中存储缴费记录,更改用户状态
function saveDataToDB(){
	var param = {};
	param.rtuId = $("#rtu_id").val();
	param.mpId = $("#mp_id").val();
	param.cus_state = SDDef.YFF_CUSSTATE_NORMAL;//客户状态	
	param.op_type = SDDef.YFF_OPTYPE_PAY;		//操作类型--缴费
	
	param.all_money = $("#zje").val();
	param.all_dl = $("#zdl").html();
	param.token = payToken;

	param.buy_times = parseInt($("#buy_times").html()) + 1;				//购电次数为1
	param.res_id = $("#res_id").val();			//客户编号,存储的是residentpara表中的id
	param.pay_type = $("#pay_type").val();		//缴费方式
	
	param.last_remain = 1;						//结余金额--再议,对应后台解析json，若没有，则后台java中也需去掉，不然报错
	param.remain_money = 1;						//剩余金额--再议,对应后台解析json，若没有，则后台java中也需去掉，不然报错	
	param.seqNo = $("#seqNo").val();
	param.keyNo = $("#keyNo").val();
	
	//需将电量转换成金额存库
	var fee = 0;
	fee = feerate.split("|");
	if (fee.length >=2 ) {
		fee = fee[1].split("/");
		if (fee.length >= 2) fee = fee[0];
	}
	else fee = feerate;

	finalJyje = round(parseFloat(jyDl) *  parseFloat($("#ctData").html()) * parseFloat($("#ptData").html()) * fee,2);
	param.jy_money = finalJyje;
	
	var operType = SDDef.YFF_OPTYPE_PAY;//操作类型：缴费
	$.post(def.basePath + "ajaxoper/actSaveDBBengal!operDYSaveDB.action", 		//后台处理程序
	{
		params : jsonString.json2String(param),
		operType : operType
	},
	function(data){
		loading.loaded();
		if(data.result = "success"){
 			getRecord($("#rtu_id").val(), $("#mp_id").val(), "", 10);
 			//window.top.WebPrint.setYffDataOperIdx2params(data.params,window.top.WebPrint.nodeIdx.dycard);//打印用的参数
 			$("#pay").attr("disabled",true);
 			$("#prt").attr("disabled",false);
 			alert("success!");	
		}
		else{
			alert("Impossible de stocker la base de données!");
		}
		
	});
}

function calcTotal(){//总金额
	
	var zje = $("#zje").val()==="" ? 0 : $("#zje").val();	//追补金额
	if(isNaN(zje)){
		return;
	}
	
	var jyje = $("#jy_money").html() == "" ? 0 : $("#jy_money").html();		//结余金额
	zje = parseFloat(zje) + parseFloat(jyje);		//总金额= 追补金额 + 结余金额

	var buy_times = $("#buy_times").text() === "" ? 0 : $("#buy_times").text();
	
	var param = {};
	param.rtuId = $("#rtu_id").val();
	param.mpId = $("#mp_id").val();
	param.feeprojId = $("#feeproj_id").val();
	param.totalMoney = $("#zje").val();
	param.tzdl = $("#tzval").text();
	param.ct = $("#ctData").html();
	param.pt = $("#ptData").html();
 	$.post(def.basePath + "ajaxoper/actSaveDBBengal!loadPayInfo.action", 		//后台处理程序
	{
		params : jsonString.json2String(param)
 	},
	function(data){
		loading.loaded();
		if(data.result = "success"){
 			var zdl = data.calcZdl;
		 	zdl = zdl/parseFloat($("#ctData").html());
		 	zdl	= zdl/parseFloat($("#ptData").html());
		 	
		 	var jyZdl = "";		 	
		 	if (buy_times<=0) zdl = zdl - ($("#tzval").text() === "" ? 0 : $("#tzval").text());
		  	var zdlStr = "" + zdl;
		 	if(zdlStr.indexOf(".")>0){
	 		zdlStr2 = zdlStr.split(".")[1];	//小数点部分
	 		if(zdlStr2.length > 1){
	 			zdlStr = zdlStr.split(".")[0] + "." + zdlStr2.substring(0,1);
	 			jyDl   = "0.0" + zdlStr2.substring(1);		//误差部分 电量 需转成金额存库
	 		}
		 	}
		 	$("#zdl").html(zdlStr);	//电量
		}
		else{
			alert("Impossible de stocker la base de données!");
		}
		
	});
	
 	
 	

	//$("#zdl").html(zdl);	//总金额
}

function setDisabled(mode){
	if(!mode){
		//清空界面的信息,用于开户后再次查询,而无查询结果的情况
//		setConsValue(rtnValue,true);
		mygrid.clearAll();
		$("#jsje").val("");
		$("#jfje").val("");
		$("#zbje").val("");
		$("#dqye").html("");
		$("#buy_times").html("");
		$("#zje").html("");
		$("#writecard_no").html("");
		//隐含域也要清空
		$("#now_remain").html("");
		$("#now_remain2").html("");
		$("#jt_total_dl").html("");
		//计算客户名称  结算客户分类
		$("#jsyhm").html("");
		$("#yhfl").html("");
	}
	$("#dqye").attr("disabled",mode);
	$("#pay").attr("disabled",mode);
	$("#jfje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#zbje").attr("disabled",mode);
	$("#chgMbphone").attr("disabled",mode);
}

function check() {
	var jfje = 0, zbje = 0, jsje = 0;

	if(!isDbl_Html("zje" ,  "Total Amount"  , 0, rtnValue.moneyLimit, false)){//总金额应该在0~囤积值之间
		return false;
	}
	
 	if(parseFloat($("#zje").val()) == 0){
		if(!confirm("Le montant total est de 0, le paiement est-il toujours nécessaire?"))
			return false;
	}
	
	return true;
}

function printPayRec(){//打印	
	
	var printParam = {};	
	if($("#paymenttoken2").text() == ""){
		printParam.opType = "pay";		
	}else{
		printParam.opType = "open";
		printParam.token2 = $("#paymenttoken2").text();
		printParam.token3 = $("#paymenttoken3").text();
	}
	
	printParam.meterNo = $("#commaddr").text();
	printParam.customerID = $("#userno").text();
	printParam.conName = $("#username").text();
	
	printParam.token1 = $("#paymenttoken1").text();
	var selectedId = mygrid.getSelectedId();
	
	printParam.finalJyje = finalJyje;
	printParam.payDate = mygrid.cells(selectedId,0).getValue();
	printParam.payMoney = mygrid.cells(selectedId,2).getValue(); 
	printParam.payDL = mygrid.cells(selectedId,5).getValue(); 
	printParam.sdadmin = mygrid.cells(selectedId,8).getValue();
	printParam.wasteno = mygrid.cells(selectedId,6).getValue(); 
	printParam.orgAddr = mygrid.cells(selectedId,9).getValue(); 
	printParam.telno = mygrid.cells(selectedId,10).getValue(); 
	printParam.orgDesc = mygrid.cells(selectedId,11).getValue(); 
	
	modalDialog.width 	= 250;
	modalDialog.height 	= 600;
	modalDialog.param	= printParam;
	modalDialog.url 	= basePath + "jsp/dyjc/print/printTemplate.jsp";
		
	modalDialog.show();
	
}