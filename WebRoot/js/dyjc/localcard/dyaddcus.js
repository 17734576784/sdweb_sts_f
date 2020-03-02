var rtnValue =null;
var gloQueryResult = { };
var meterkey,token1,token2,opentoken;
var feerate;
var getPrintParam = {};
var printParamAddr = "";
var printParamTelno = "";
var jyDl	= "0.0";
var finalJyje	= 0;
$(document).ready(function(){
	
	$("#btnSearch").click(function(){clear();selcons()});	
 	
	$("#btnNew").click(function(){
		loading.loading();
		if(check()){
			openAccount();
		}else{
			loading.loaded();
		}
	});
	
	$("#prt").click(function(){makePrintExtInfo();		printPayRec();});

	$("#zje").keyup(function(){calcTotal();});
	
	$("#btnSearch").focus();
	
 });

//function Import(){
//	var tmp = importBD($("#rtu_id").val(),$("#mp_id").val());
//	if(!tmp)return;
//	rtnBD = tmp;
//    $("#td_bdinf").html(rtnBD.td_bdinf);
//	$("#btnNew").attr("disabled",false);
//}

function clear(){
	$("#tokens").css("display","none");
  	$("#zje").val("");
  	$("#prt").attr("disabled",true);

}


function selcons(){//检索
	//每次检索都要禁用开户按钮
	$("#btnNew").attr("disabled","disabled");
	
	var rtnValue1 = doSearch("ks",ComntUseropDef.YFF_DYOPER_ADDRES,rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
 	fillInfoValue(rtnValue);//查询结果返回到界面	

//	setDisabled(false);	
//setConsValue(rtnValue);//查询返回界面赋值	
//	check_JT_total_zbdl();
//	$("#writecard_no").html(rtnValue.writecard_no);
//	
//	//20131021添加，当查询结果中的预付费表类型
//	var yffmeter_type = rtnValue.yffmeter_type;
//	WRITECARD_TYPE = getCardByMeter(yffmeter_type);
	
//	//mis.js中函数
//	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno,provinceMisFlag : provinceMisFlag};
//	mis_query(param,gloQueryResult);
	
	$("#jfje").focus();
}

function fillInfoValue(obj){
	if(obj){
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
		
		$("#seqNo").val(2);		//开户seqNo默认为2
		$("#keyNo").val(1);		//开户keyNo默认为1
		$("#jfje").val("");
		$("#zje").html("");
		$("#friend_s").val(obj.friend_s);
		$("#friend_e").val(obj.friend_e);
		$("#friendDate").val(obj.friendDate);
		$("#fmaximum_power").val(obj.fmaximum_power);
		$("#gmaximum_power").val(obj.gmaximum_power);
		$("#power_start").val(obj.power_start);
		$("#power_end").val(obj.power_end);
		$("#res_id").val(obj.res_id);
		$("#power_date").val(obj.power_date);
		feerate = obj.feeproj_detail;
		
		printParamAddr = obj.addr;
		printParamTelno = obj.telno;
		
		$("#ctData").html(obj.ct);
		$("#ptData").html(obj.pt);
		
		//启用"开户"按钮
		$("#btnNew").attr("disabled",false);
	}
}

//新写的开户调用方法
function openAccount(){
	
	if ($("#zdl").text() < 0) {
		loading.loaded();
		alert("kwh n'est pas valide!");
		return;
	}
	//loading.loading();
	//组参数串,不同界面需要参数可能不同，具体界面再取
	var params = {};
	params.DRN = rtnValue.commaddr;
	params.SGC = rtnValue.sgc;
	params.KRN = rtnValue.key_version;
	params.TI = rtnValue.ti;
 
	params.KT  =  rtnValue.key_type;
 	params.IIN = "600727";	 
  	params.DKGA=  rtnValue.dkga;
  	params.rtuId=  rtnValue.rtu_id;
  	params.mpId=  rtnValue.mp_id;
  	params.alldl=  $("#zdl").html()*10;
  	params.KEN=  $("#KEN").val();
 	params.vk = rtnValue.vk1;
  	
	$.post(def.basePath+"ajaxdyjc/actMakeTokens!makeOpenAccountToken.action", 			{
 			result:jsonString.json2String(params)
 			},
 			function(data){			
 				if(data.result != ""){ 	 					
 					
 					var json = eval("("+ data.result +")");
 					meterkey = json.meterKey;
 					token1 = json.token1;
 					token2 = json.token2;
 					opentoken = json.opentoken
     				$("#tokens").css("display","block");
     				$("#token1").css("font","bold 18px red");
     				$("#token2").css("font","bold 18px red");
     				$("#opentoken").css("font","bold 18px red");
     				
     				$("#token1").html(token1);
     				$("#token2").html(token2);
     				$("#opentoken").html(opentoken);
     				
     				loading.loaded();
     				if (token1!="" || token2!="") {
     					saveDataToDB();
     				}else {
     					alert("Échec d'ouverture");
     				}
 				} 			
 			} 		
 		); 	
}

//数据库中存储缴费记录,更改用户状态
function saveDataToDB(){
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
	finalJyje = round(parseFloat(jyDl) *  parseFloat($("#ctData").html()) * parseFloat($("#ptData").html()) * feerate,2);
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
					//var json = eval('(' + data.params + ')');
					getPrintParam = eval('(' + data.params + ')');
					//window.top.WebPrint.setYffDataOperIdx2params(data.params,window.top.WebPrint.nodeIdx.dycard);//打印用的参数
					$("#btnNew").attr("disabled",true);
					$("#prt").attr("disabled",false);
					alert("Ouvrez un compte avec succès!");	
				}
				else{
					alert("Échec de stockage de la base de données, veuillez constituer l'enregistrement!");
				}
			});
}

function calcTotal(){//总金额
		
	var zje = $("#zje").val()==="" ? 0 : $("#zje").val();	//追补金额
 	if(isNaN(zje)){
 		alert("NAN");
		return;
	}
 		
 	var zdl = parseFloat(zje)/feerate;
 	
 	zdl     = zdl/parseFloat($("#ctData").html());
 	
 	zdl     = zdl/parseFloat($("#ptData").html());
 	
 	zdl = zdl - ($("#tzval").text() === "" ? 0 : $("#tzval").text());
 	
 	var zdlStr = "" + zdl;
 	if(zdlStr.indexOf(".")>0){
 		zdlStr2 = zdlStr.split(".")[1];	//小数点部分
 		if(zdlStr2.length > 1){
 			zdlStr = zdlStr.split(".")[0] + "." + zdlStr2.substring(0,1);
 			jyDl   = "0.0" + zdlStr2.substring(1);		//误差部分 电量 需转成金额存库
 		}
 	}
 	$("#zdl").html(zdlStr);	//电量
 	
 	
 	//zdl  = round(zdl,1);
	//$("#zdl").html(zdl);	//总金额
}

function makePrintExtInfo() {	//获取额外打印信息
	var token1 = $("#token1").html()==="" ? '' : $("#token1").html();
	var token2 = $("#token2").html()==="" ? '' : $("#token2").html();
	
	if (token1.length !== 0 && token2.length !== 0){
		window.top.WebPrint.prt_params.ext_info = token1 + "---" + token2;
	}
	else window.top.WebPrint.prt_params.ext_info = "";
}


//function setDisabled(mode){
//	if(!mode){
//		//清空界面的信息,用于开户后再次查询,而无查询结果的情况
////		setConsValue(rtnValue,true);
//		$("#jfje").val("");
//		$("#jsje").val("");
//		$("#zje").html("");
//		$("#td_bdinf").html("");
//		$("#jt_total_zbdl").val("");
//		$("#writecard_no").html("");
//		//结算客户名称  结算客户分类
//		$("#jsyhm").html("");
//		$("#yhfl").html("");
//		rtnBD = null;
//	}
//	$("#jfje").attr("disabled",mode);
//	$("#jsje").attr("disabled",mode);
//	$("#jt_total_zbdl").attr("disabled",mode);
//	$("#btnImportBD").attr("disabled",mode);
//	$("#yzje").attr("disabled",mode)
//	$("#jt_total_zbdl").attr("disabled",mode);
//}

//function check_JT_total_zbdl(){//阶梯费率 则 旧表基础电量可用			
//	if(rtnValue.feeType == SDDef.YFF_FEETYPE_JTFL || rtnValue.feeType == SDDef.YFF_FEETYPE_MIXJT){
//	   $("#jt_total_zbdl").attr("disabled",false).css("background","#fff");
//	}else{
//	   $("#jt_total_zbdl").attr("disabled",true).css("background","#ccc");
//	}
//}

function check(){
	//20140116 dubr 写卡户号为空或者0 禁止开户
//	if(rtnValue.writecard_no=="" || rtnValue.writecard_no==0){
//		alert("请先配置写卡户号!");
//		return false;
//	}
	//end		
	//jsje = $("#jsje").val()==="" ? 0 : $("#jsje").val();
	
//	if(isNaN(jsje)){
//	alert("请输入数字!");
//	$("#jsje").focus().select();
//	return false;
//}

//if(!isDbl("jsje",  "结算金额", -rtnValue.moneyLimit, rtnValue.moneyLimit, false)){
//	return false;
//}
	
//	if(!isDbl("jt_total_zbdl" , "旧表基础电量")){
//		return false;
//	}
//	if(rtnBD == null) {
//		alert("未录入表底!");
//		return;
//	}

//	if(($("#jfje").val() == "0" || $("#jfje").val() == "")){
//		return(confirm("客户未缴费,继续操作吗?"))
//	}
	

	var jfje = 0, zbje = 0, jsje = 0;
	zje = $("#zje").val()==="" ? 0 : $("#zje").val();
 
	if(!isNumber("KEN" ,  "KEN", 1, 255, true)){//缴费金额应该在0~囤积值之间
		return false;
	}

	
	
	if(!isDbl_Html("zje" ,  "Montant total", 0, rtnValue.moneyLimit, false)){//缴费金额应该在0~囤积值之间
		return false;
	}

	if(parseFloat(zje) == 0){
		if(!confirm("Le consommateur n'a pas encore payé, le paiement est-il nécessaire?"))return false;
	}
	
	return true;
}

function printPayRec() {
	
	var printParam = {};	
	
	printParam.opType = "open";
	printParam.meterNo = $("#commaddr").text();
	printParam.customerID = $("#userno").text();
	printParam.conName = $("#username").text();
	
	var token1 = $("#token1").text();
	printParam.token1 = token1;
	var token2 = $("#token2").text();
	printParam.token2 = token2;	
	var token3 = $("#opentoken").text();
	printParam.token3 = token3;
	
	printParam.finalJyje = finalJyje;
	printParam.payDate 	= getPrintParam.optime;
	printParam.payMoney = getPrintParam.pay_money; 
	printParam.payDL 	= getPrintParam.buy_dl; 
	printParam.sdadmin 	= getPrintParam.op_man;
	printParam.wasteno 	= getPrintParam.wasteno; 
	printParam.orgAddr 	= printParamAddr; 
	printParam.telno 	= printParamTelno;
	
	modalDialog.width 	= 250;
	modalDialog.height 	= 600;
	modalDialog.param	= printParam;
	modalDialog.url 	= basePath + "jsp/dyjc/print/printTemplate.jsp";
		
	modalDialog.show();
	
}
