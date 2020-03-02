var rtnValue;

$(document).ready(function(){
	
	$("#btnRead").click(function(){read_card()});
	$("#cardinfo").click(function(){cardinfo()});
	$("#btnRead").focus();
});

function read_card(){		//读卡
	clearTable();
	loading.tip = "正在读卡,请稍后...";
	loading.loading();
	setTimeout("window.top.card_comm.readCardSearchDy(" + ComntUseropDef.YFF_DYOPER_PAY + ")", 10);
}

function setSearchJson2Html(js_tmp) {
	loading.loaded();
	if(!js_tmp)return;
	if(!window.top.card_comm.checkInfo(js_tmp)) return;
	
	rtnValue = js_tmp;

	//显示基本信息和费控信息
	setConsValue(rtnValue);
	
	//20131021添加，当查询结果中的预付费表类型
	var yffmeter_type = rtnValue.yffmeter_type;
	//对不同卡表界面卡内信息和反写信息显示作做出调整
	if(is2013Meter(yffmeter_type)){
		$("#type1").css("display","none");
		$("#type2").css("display","block");
		setCardCons2();
	}
	else{
		$("#type1").css("display","block");
		$("#type2").css("display","none");
		setCardCons();
	}	
}

function cardinfo(){	//卡内信息
	modalDialog.width = 500;
	modalDialog.height = 600;
	modalDialog.url = "../../dialog/cardinfo.jsp";
	modalDialog.show();
}

function clearTable(){
	setConsValue(rtnValue,true);
}

//填写卡内信息相关项目
function setCardCons(){

	var cardStr = window.top.card_comm.strInfo;	//卡内的字符串，如"KE_001|0|1|34|0.53……"
	if(this.strInfo == ""){
		return;
	}
	var rtn_json = {};
	var cardInfo = cardStr.split("|");
	var meterType = cardInfo[0];	//卡表类型
	if(meterType == window.top.SDDef.YFF_METER_TYPE_ZNK){
		rtn_json.type = meterType;
		rtn_json.meterType = "科林自管户2009版";
		if(cardInfo[1]=="1"){
			rtn_json.feeType = "单费率";
			rtn_json.fee_1	 = "总:"+cardInfo[14];
			rtn_json.fee_2	 = "总:"+cardInfo[19];
		}else if(cardInfo[1]=="2"){
			rtn_json.feeType = "复费率";
			rtn_json.fee_1	 = "尖:"+cardInfo[15]+",峰:"+cardInfo[16]+",平:"+cardInfo[17]+",谷:"+cardInfo[18];
			rtn_json.fee_2	 = "尖:"+cardInfo[20]+",峰:"+cardInfo[21]+",平:"+cardInfo[22]+",谷:"+cardInfo[23];
		}
		rtn_json.alarm1 = cardInfo[5];
		rtn_json.alarm2 = cardInfo[6];
		rtn_json.ct     = cardInfo[7];
		rtn_json.pt		= cardInfo[8];
		rtn_json.esamNo	= cardInfo[9];
		rtn_json.userNo = cardInfo[10];
		if(cardInfo[11] == "1"){
			rtn_json.cardType = "开户卡";
		}else if(cardInfo[11] == "2"){
			rtn_json.cardType = "购电卡";
		}
		rtn_json.lastPayMoney = parseFloat(cardInfo[12])/100;		
		rtn_json.buy_num 	  = cardInfo[13];		
		
		
		//返写信息
		if(cardInfo[24] == "1"){
			rtn_json.feeType_back = "单费率";
		}
		else if(cardInfo[24] == "2"){
			rtn_json.feeType_back = "复费率";
		}
		rtn_json.ct_back  	 	   = cardInfo[25];
		rtn_json.pt_back  		   = cardInfo[26];
		rtn_json.esamNo_back       = cardInfo[27];
		rtn_json.userNo_back       = cardInfo[28];
		rtn_json.remainMoney_back  = parseFloat(cardInfo[29])/100;
		rtn_json.buy_num_back      = cardInfo[30];
		rtn_json.tzMoney_back	   = cardInfo[31];
		rtn_json.pwdState_back	   = cardInfo[32];
		rtn_json.pwdVersion_back   = cardInfo[35];
		rtn_json.ffck_num_back	   = cardInfo[36];
		
		rtn_json.fxDate_back = formatDT(cardInfo[37],'YMD');
		rtn_json.fxTime_back = formatDT(cardInfo[38],'HMS');
		
		if(rtn_json.userNo_back == "000000000000") {
			rtn_json.write_back_flag = "否";
		}
		else {
			rtn_json.write_back_flag = "是";
		}
	}else {
		alert("卡类型错误！");
		return ;
	}
	
	if(rtn_json.type == window.top.SDDef.YFF_METER_TYPE_ZNK){
		$("#userno_01").html(rtn_json.userNo);
		$("#metertype_01").html(rtn_json.meterType);
		$("#esamno_01").html(rtn_json.esamNo);
		$("#feetype_01").html(rtn_json.feeType);
		$("#scjfje_01").html(rtn_json.lastPayMoney);
		$("#card_type_01").html(rtn_json.cardType);
		$("#alarm1_01").html(rtn_json.alarm1);
		$("#alarm2_01").html(rtn_json.alarm2);
		$("#buynum_01").html(rtn_json.buy_num);
		$("#pt_01").html(rtn_json.pt);
		$("#ct_01").html(rtn_json.ct);
		$("#fee1_01").html(rtn_json.fee_1);
		$("#fee2_01").html(rtn_json.fee_2);
		
		//返写信息
		if(rtn_json.write_back_flag == "否"){
			$("#write_back_flag_01").html("否");
			$("#userno_01_back"    ).html("");
			$("#esamno_01_back"    ).html("");
			$("#feetype_01_back"   ).html("");
			$("#syje_01_back"      ).html("");
			$("#tzje_01_back"      ).html("");
			$("#buynum_01_back"    ).html("");
			$("#pt_01_back"        ).html("");
			$("#ct_01_back"        ).html("");
			$("#mybb_01_back"      ).html("");
			$("#myzt_01_back"      ).html("");
			$("#ffckcs_01_back"    ).html("");
			$("#fxdata_01_back"    ).html("");
			$("#fxtime_01_back"    ).html("");
		}else{
			$("#write_back_flag_01").html("是");
			$("#userno_01_back"    ).html(rtn_json.userNo_back);
			$("#esamno_01_back"    ).html(rtn_json.esamNo_back);
			$("#feetype_01_back"   ).html(rtn_json.feeType_back);
			$("#syje_01_back"  	   ).html(rtn_json.remainMoney_back);
			$("#tzje_01_back"      ).html(rtn_json.tzMoney_back);
			$("#buynum_01_back"    ).html(rtn_json.buy_num_back);
			$("#pt_01_back"        ).html(rtn_json.pt_back);
			$("#ct_01_back"        ).html(rtn_json.ct_back);
			$("#mybb_01_back"      ).html(rtn_json.pwdVersion_back);
			$("#myzt_01_back"      ).html(rtn_json.pwdState_back);
			$("#ffckcs_01_back"    ).html(rtn_json.ffck_num_back);
			$("#fxdata_01_back"    ).html(rtn_json.fxDate_back);
			$("#fxtime_01_back"    ).html(rtn_json.fxTime_back);
		}
	}	
}

//20131021新增卡表类型填写卡内信息和反写信息
function setCardCons2(){
	var cardStr = window.top.card_comm.strInfo;	//卡内的字符串，如"KE_001|0|1|34|0.53……"
	if(this.strInfo == ""){
		return;
	}
	var rtn_json = {};
	var cardInfo = cardStr.split("|");
	var meterType = cardInfo[0];	//卡表类型
	if(meterType == window.top.SDDef.YFF_METER_TYPE_ZNK2){
		rtn_json.type = meterType;
		
		rtn_json.meterType = "科林自管户2013版";
		
		//组装成信息字符串
		var fee1_desc = "尖:{"+ cardInfo[13]/10000 + "} 峰:{" + cardInfo[14]/10000  + "} 平:{" + cardInfo[15]/10000  +"} 谷:{" + cardInfo[16]/10000  + "}";
	
		fee1_desc = fee1_desc + "梯度1:{" + cardInfo[25]/10000  + "}[" + cardInfo[18]/100 +"],梯度2:{" + cardInfo[26]/10000  + "}[" + cardInfo[19]/100 + "],梯度3:{" + cardInfo[27]/10000  + "}[" + cardInfo[20]/100  +"],梯度4:{"+ cardInfo[28]/10000  + "}";
		fee1_desc += "阶梯年结算日[" + formatDT(cardInfo[33],'MDH') + "],[" + formatDT(cardInfo[34],'MDH') + "],[" + formatDT(cardInfo[35],'MDH') + "],[" + formatDT(cardInfo[36],'MDH') + "]"; 
		
		var fee2_desc = "尖:{"+ cardInfo[40]/10000  + "} 峰:{" + cardInfo[41]/10000  + "} 平:{" + cardInfo[42]/10000  +"} 谷:{" + cardInfo[43]/10000  + "}";
		fee2_desc += "梯度1:{" + cardInfo[52]/10000  + "}[" + cardInfo[45]/100  +"],梯度2:{" + cardInfo[53]/10000  + "}[" + cardInfo[46]/100 + "],梯度3:{" + cardInfo[54]/10000  + "}[" + cardInfo[47]/100  +"],梯度4:{"+ cardInfo[55]/10000  + "}";
		fee2_desc += "阶梯年结算日[" + formatDT(cardInfo[60],'MDH')+ "],[" + formatDT(cardInfo[61],'MDH') + "],[" + formatDT(cardInfo[62],'MDH') + "],[" + formatDT(cardInfo[63],'MDH') + "]"; 
		
		rtn_json.fee_1 = fee1_desc;
		rtn_json.fee_2 = fee2_desc;
	
		rtn_json.alarm1 = cardInfo[4];
		rtn_json.alarm2 = cardInfo[5];
		rtn_json.ct     = cardInfo[6];
		rtn_json.pt		= cardInfo[7];
		rtn_json.esamNo	= cardInfo[8];
		rtn_json.userNo = cardInfo[9];
		if(cardInfo[10] == "1"){
			rtn_json.cardType = "开户卡";
		}else if(cardInfo[10] == "2"){
			rtn_json.cardType = "购电卡";
		}
		rtn_json.lastPayMoney = parseFloat(cardInfo[11])/100;		
		rtn_json.buy_num 	  = cardInfo[12];		
		
		//返写信息
		rtn_json.ct_back  	 	   = cardInfo[67];
		rtn_json.pt_back  		   = cardInfo[68];
		rtn_json.esamNo_back       = cardInfo[69];
		rtn_json.userNo_back       = cardInfo[70];
		rtn_json.remainMoney_back  = parseFloat(cardInfo[71])/100;
		rtn_json.buy_num_back      = cardInfo[72];
		rtn_json.tzMoney_back	   = cardInfo[73];
		rtn_json.ffck_num_back	   = cardInfo[74];
	
		rtn_json.fxDate_back = formatDT(cardInfo[75],'YMD');
		rtn_json.fxTime_back = formatDT(cardInfo[76],'HMS');
		
		if(rtn_json.userNo_back == "000000000000") {
			rtn_json.write_back_flag = "否";
		}
		else {
			rtn_json.write_back_flag = "是";
		}
	}else {
		alert("卡类型错误！");
		return ;
	}
	
	if(rtn_json.type == window.top.SDDef.YFF_METER_TYPE_ZNK2){
		$("#userno_02").html(rtn_json.userNo);
		$("#metertype_02").html(rtn_json.meterType);
		$("#esamno_02").html(rtn_json.esamNo);
		$("#feetype_02").html(rtn_json.feeType);
		$("#scjfje_02").html(rtn_json.lastPayMoney);
		$("#card_type_02").html(rtn_json.cardType);
		$("#alarm1_02").html(rtn_json.alarm1);
		$("#alarm2_02").html(rtn_json.alarm2);
		$("#buynum_02").html(rtn_json.buy_num);
		$("#pt_02").html(rtn_json.pt);
		$("#ct_02").html(rtn_json.ct);
		$("#fee1_02").html(rtn_json.fee_1);
		$("#fee2_02").html(rtn_json.fee_2);
		
		//返写信息
		if(rtn_json.write_back_flag == "否"){
			$("#write_back_flag_02").html("否");
			$("#userno_02_back"    ).html("");
			$("#esamno_02_back"    ).html("");
			$("#feetype_02_back"   ).html("");
			$("#syje_02_back"      ).html("");
			$("#tzje_02_back"      ).html("");
			$("#buynum_02_back"    ).html("");
			$("#pt_02_back"        ).html("");
			$("#ct_02_back"        ).html("");
			$("#ffckcs_02_back"    ).html("");
			$("#fxdata_02_back"    ).html("");
			$("#fxtime_02_back"    ).html("");
		}else{
			$("#write_back_flag_02").html("是");
			$("#userno_02_back"    ).html(rtn_json.userNo_back);
			$("#esamno_02_back"    ).html(rtn_json.esamNo_back);
			$("#feetype_02_back"   ).html(rtn_json.feeType_back);
			$("#syje_02_back"  	   ).html(rtn_json.remainMoney_back);
			$("#tzje_02_back"      ).html(rtn_json.tzMoney_back);
			$("#buynum_02_back"    ).html(rtn_json.buy_num_back);
			$("#pt_02_back"        ).html(rtn_json.pt_back);
			$("#ct_02_back"        ).html(rtn_json.ct_back);
			$("#ffckcs_02_back"    ).html(rtn_json.ffck_num_back);
			$("#fxdata_02_back"    ).html(rtn_json.fxDate_back);
			$("#fxtime_02_back"    ).html(rtn_json.fxTime_back);
		}
	}	
}

//格式化日期、时间
function formatDT(data,type){
	if(data == 0){
		return data;
	}
	
	var para1, para2, para3;
	
	if(data.length <= 4){
		data = parseInt(data);
		para1 = parseInt(data/100);
		para2 = parseInt(data%100);
		para3 = 0;
	}
	else{
		data = parseInt(data);
		para1 = parseInt(data/10000);
		para2 = parseInt((data%10000)/100);
		para3 = parseInt((data%10000)%100);
	}
	switch(type){
		case 'MDH' :
			return  para1 + '月' + para2 + '日' +  para3 + '时';
			break;
		case 'YMD' :
			return  '20' + para1 + '年' + para2 + '月' +  para3 + '日';
		case 'HMS'  :
			return 	para1 + '时' + para2 + '分' + para3 + '秒';
	}
}





