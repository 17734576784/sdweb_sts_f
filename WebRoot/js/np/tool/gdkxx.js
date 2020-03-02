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
	setConsValue(rtnValue);
	setCardCons();
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
		rtn_json.meterType = "科林自管户";
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
		
		
		var year  = parseInt(cardInfo[37]/10000);
		var month = parseInt((cardInfo[37]%10000)/100);
		var day   = parseInt((cardInfo[37]%10000)%100);
		var hour  = parseInt(cardInfo[38]/10000);
		var min   = parseInt((cardInfo[38]%10000)/100);
		var sec   = parseInt((cardInfo[38]%10000)%100);
		
		rtn_json.fxDate_back = "20"+year+"年"+month+"月"+day+"日";
		rtn_json.fxTime_back = hour+"时"+min+"分"+sec+"秒";
		
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