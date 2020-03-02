//卡式 公共函数
var card_comm = {
		
	strInfo  : "",  //记录卡内字符串
	type	 : "",	//页面类型（开户、销户、缴费……）
	caclType :{	//计费方式
		je : 1,	//金额
		bd : 2	//表底
	},

	//根据缴费方式不同设置不同的界面显示及赋值
	setBDJE : function(cacl_type){
	
		if(cacl_type == SDDef.YFF_CACL_TYPE_MONEY){//金额方式
			$("#gdl").attr("disabled", true).attr("class", "readonly_bg");
			switch(card_comm.type){
			case "gykh": //开户
				
				break;
			}
		}
		else if(cacl_type == SDDef.YFF_CACL_TYPE_BD){//表底方式
			$("#gdl").attr("disabled", false).attr("class", "");
			switch (card_comm.type) {
			case "gykh": //开户
				
				break;
			case "gyxh":
				
				break;
			case "gyjf":
				
				break;
			}
		}
	},
	

	//设置报警金额
	setBDJEAlarmValue : function(zje){
		
		if(rtnValue.cacl_type == SDDef.YFF_CACL_TYPE_MONEY){//金额方式
			setAlarmValue(rtnValue.yffalarm_alarm1, rtnValue.yffalarm_alarm2, zje);
		}
		else if(rtnValue.cacl_type == SDDef.YFF_CACL_TYPE_BD){//表底方式
			
		}
	},

	//读卡.返回卡内信息 {"errno":"0", "strinfo":"read string", "errstr":"some err string"}
	readcard : function(){
		
		var json_instr = "{\"dllname\":\"kecard/libyffwebcard.dll\", \"procname\":\"CardRead\", \"inputparam\":\" \"}"
		var json_outstr = this.getEmptyString(4096);
	    document.YFFWEBCTRL.CallProc(json_instr, json_instr.length, json_outstr, json_outstr.length);
		for (var i = 0; i < json_outstr.length; i++) if (json_outstr.charCodeAt(i) == 0) json_outstr = json_outstr.substr(0, i);
		loading.loaded();
		var jobj_out = eval("(" + json_outstr + ")")
		if (jobj_out.errno == 0) {
			var o_json = this.readString2Json(jobj_out);
			this.strInfo = o_json.strinfo;
			return o_json; 
		}
		else {
			alert(jobj_out.errstr + "\nCallProc Operation failed..");
			return "";
		}
	},
	
	
	
	//工具卡读卡
	tool_readcard : function(type){
		
		var json_instr = "{\"dllname\":\"kecard/libyffwebcard.dll\", \"procname\":\"ToolCardRead\", \"inputparam\":\"" + type + "\"}";
		var json_outstr = this.getEmptyString(4096);
		document.YFFWEBCTRL.CallProc(json_instr, json_instr.length, json_outstr, json_outstr.length);
		for (var i = 0; i < json_outstr.length; i++) if (json_outstr.charCodeAt(i) == 0) json_outstr = json_outstr.substr(0, i);
		
		var jobj_out = eval("(" + json_outstr + ")");
		return jobj_out;
	},
	
	//扩展卡读卡
	read_extendcard : function(){
		//var str="KE_005|81|4|0|3|0311201303160001|1111111111111111||0311201303160001|0311201303160002|0311201303160003|0000000000000000|0000000000000000|0000000000000000|0000000000000000|0000000000000000|0000000000000000|0000000000000000|129|4|0|10||1111111111111111||0311201303160001|130408195018|0311201303160002|130408195018|0311201303160003|130408195018|0311201303160004|130408195018|0311201303160005|130408195018|0311201303160006|130408195018|0311201303160007|130408195018|0311201303160008|130408195018|0311201303160009|130408195018|0311201303160010|130408195018|"
		var json_instr = "{\"dllname\":\"kecard/libyffwebcard.dll\", \"procname\":\"ExpansCardRead\", \"inputparam\":\"81\"}"
		var json_outstr = this.getEmptyString(4096);
		document.YFFWEBCTRL.CallProc(json_instr, json_instr.length, json_outstr, json_outstr.length);
		for (var i = 0; i < json_outstr.length; i++) if (json_outstr.charCodeAt(i) == 0) json_outstr = json_outstr.substr(0, i);

		
		var jobj_out = eval("(" + json_outstr + ")")
		
		if (jobj_out.errno == 0) {
			return jobj_out;		
		}else {
			alert(jobj_out.errstr + "\nCallProc Operation failed..");
			return jobj_out
		}
	},
	
	//写卡
	writecard : function(WRITE_INFO) {
		// "KE_001|2|131|20991231|235959|20|10|1|1|0011|13931860731|2|10000|3|0.52|0.52|0.52|0.52|0.52|0.52|0.52|0.52|0.52|0.52";
		var json_instr = "{\"dllname\":\"kecard/libyffwebcard.dll\", \"procname\":\"CardWrite\", \"inputparam\":\"" + WRITE_INFO + "\"}"
		
		if (WRITE_INFO == "") {
			alert("Write information error..");
			return false;
		}
	
		var json_outstr = this.getEmptyString(4096);
	    document.YFFWEBCTRL.CallProc(json_instr, json_instr.length, json_outstr, json_outstr.length);
		
	    for (var i = 0; i < json_outstr.length; i++) if (json_outstr.charCodeAt(i) == 0) json_outstr = json_outstr.substr(0, i);
		
	    var jobj_out = eval("(" + json_outstr + ")");

	    return jobj_out;
		
	},
	
	//20140608 新增 外接卡表写卡---zhp-----------------------------------------------------------------------------------start----
	writeExtcard : function(WRITE_INFO) {
		//usb|KL|000587128378|111111111111|JJ_003|9999|LK|02|83|||10|10|1|2|NNGGHF|120822155023|2|5|10|1200|9999|0|40|20|5000|0|0000|0000|0000|0000|0000000000|00000000|00000000||||||||||||||||||||||||||||||0.52|0.52|0.52|0.52||||||||||||||0.52|0.52|0.52|0.52||||
		var json_instr = "{\"dllname\":\"PurpCard/libyffpurpcard.dll\", \"procname\":\"CardWrite\", \"inputparam\":\"" + WRITE_INFO + "\"}";		
		
		if (WRITE_INFO == "") {
			alert("Write information error..");
			return false;
		}
	
		var json_outstr = this.getEmptyString(4096);
	    document.YFFWEBCTRL.CallProc(json_instr, json_instr.length, json_outstr, json_outstr.length);
		
	    for (var i = 0; i < json_outstr.length; i++) if (json_outstr.charCodeAt(i) == 0) json_outstr = json_outstr.substr(0, i);
		
	    var jobj_out = eval("(" + json_outstr + ")");

	    return jobj_out;
		
	},
	
	//20150308 孟加拉写卡  writeCard for Bengal
	writeCardBengal : function(WRITE_INFO) {
		var json_instr = "{\"dllname\":\"YffCtrlMjl/libyffcardmjl.dll\", \"procname\":\"CardWrite\", \"inputparam\":\"" +WRITE_INFO + "\"}"

		if (WRITE_INFO == "") {
			alert("Write information error..");
			return false;
		}
		var json_outstr = this.getEmptyString(4096);
		document.YFFWEBCTRL.CallProc(json_instr, json_instr.length, json_outstr, json_outstr.length);
		for (var i = 0; i < json_outstr.length; i++) if (json_outstr.charCodeAt(i) == 0) json_outstr = json_outstr.substr(0, i);
		var jobj_out = eval("(" + json_outstr + ")");
		return jobj_out;
	},

	//孟加拉读卡,返回加密的串
	readCardBengal : function(){
		var json_instr = "{\"dllname\":\"YffCtrlMjl/libyffcardmjl.dll\", \"procname\":\"CardRead\", \"inputparam\":\" \"}"
		var json_outstr = this.getEmptyString(4096);
	    document.YFFWEBCTRL.CallProc(json_instr, json_instr.length, json_outstr, json_outstr.length);
		for (var i = 0; i < json_outstr.length; i++) if (json_outstr.charCodeAt(i) == 0) json_outstr = json_outstr.substr(0, i);
		loading.loaded();
		var jobj_out = eval("(" + json_outstr + ")")
		
		if (jobj_out.errno == 0) {
			return jobj_out.strinfo; //返回的是解密后的xml文件
		}
		else {
			alert(jobj_out.errstr + "\nCallProc Operation failed..");
			return "";
		}
	},
	
	//读卡.返回卡内信息 {"errno":"0", "strinfo":"read string", "errstr":"some err string"}
	readExtcard : function(){
		
		var json_instr = "{\"dllname\":\"PurpCard/libyffpurpcard.dll\", \"procname\":\"CardRead\", \"inputparam\":\" \"}"
		var json_outstr = this.getEmptyString(4096);
	    document.YFFWEBCTRL.CallProc(json_instr, json_instr.length, json_outstr, json_outstr.length);
		for (var i = 0; i < json_outstr.length; i++) if (json_outstr.charCodeAt(i) == 0) json_outstr = json_outstr.substr(0, i);
		loading.loaded();
	
		var jobj_out = eval("(" + json_outstr + ")")
		if (jobj_out.errno == 0) {
			var o_json = this.readExtString2Json(jobj_out);
			this.strInfo = o_json.strinfo;

			return o_json; 
		}
		else {
			alert(jobj_out.errstr + "\nCallProc Operation failed..");
			return "";
		}
	},
	//20140608 新增 外接卡表写卡---zhp-----------------------------------------------------------------------------------end----
	
	//201400802	抄表卡 zkz begin -------------------------------------------------------------------------
	MakeRMcard : function(WRITE_INFO) {
		//JJ_003|40|0
		var json_instr = "{\"dllname\":\"PurpCard/libyffpurpcard.dll\", \"procname\":\"RMeterCardWrite\", \"inputparam\":\"" + WRITE_INFO + "\"}";		
		
		if (WRITE_INFO == "") {
			alert("Write information error..");
			return false;
		}
	
		var json_outstr = this.getEmptyString(4096);
	    document.YFFWEBCTRL.CallProc(json_instr, json_instr.length, json_outstr, json_outstr.length);
		
	    for (var i = 0; i < json_outstr.length; i++) if (json_outstr.charCodeAt(i) == 0) json_outstr = json_outstr.substr(0, i);
		
	    var jobj_out = eval("(" + json_outstr + ")");

	    return jobj_out;
		
	},
	
	//读卡.返回卡内信息 {"errno":"0", "strinfo":"read string", "errstr":"some err string"}
	readRMcard : function(){
		
		var json_instr = "{\"dllname\":\"PurpCard/libyffpurpcard.dll\", \"procname\":\"RMeterCardRead\", \"inputparam\":\" \"}"
		var json_outstr = this.getEmptyString(4096);
	    document.YFFWEBCTRL.CallProc(json_instr, json_instr.length, json_outstr, json_outstr.length);
		for (var i = 0; i < json_outstr.length; i++) if (json_outstr.charCodeAt(i) == 0) json_outstr = json_outstr.substr(0, i);
		loading.loaded();
	
		var jobj_out = eval("(" + json_outstr + ")")
		if (jobj_out.errno == 0) {
			var o_json = this.readExtString2Json(jobj_out);
			this.strInfo = o_json.strinfo;
			return o_json; 
		}
		else {
			alert(jobj_out.errstr + "\nCallProc Operation failed..");
			return "";
		}
	},
	
	//201400802	抄表卡 zkz end -------------------------------------------------------------------------
	
	//201401011	工具卡 zkz begin -------------------------------------------------------------------------
	MakeToolCard : function(WRITE_INFO) {
		//JJ_003|40|0
		var json_instr = "{\"dllname\":\"PurpCard/libyffpurpcard.dll\", \"procname\":\"ToolCardWrite\", \"inputparam\":\"" + WRITE_INFO + "\"}";		
		
		if (WRITE_INFO == "") {
			alert("Write information error..");
			return false;
		}
	
		var json_outstr = this.getEmptyString(4096);
	    document.YFFWEBCTRL.CallProc(json_instr, json_instr.length, json_outstr, json_outstr.length);
		
	    for (var i = 0; i < json_outstr.length; i++) if (json_outstr.charCodeAt(i) == 0) json_outstr = json_outstr.substr(0, i);
		
	    var jobj_out = eval("(" + json_outstr + ")");

	    return jobj_out;
		
	},
	//201401011	工具卡 zkz end -------------------------------------------------------------------------
	
	//工具卡写卡
	tool_writecard : function(WRITE_INFO) {
		
		// "KE_001|2|131|20991231|235959|20|10|1|1|0011|13931860731|2|10000|3|0.52|0.52|0.52|0.52|0.52|0.52|0.52|0.52|0.52|0.52";
		var json_instr = "{\"dllname\":\"kecard/libyffwebcard.dll\", \"procname\":\"ToolCardWrite\", \"inputparam\":\"" + WRITE_INFO + "\"}"
		
		if (WRITE_INFO == "") {
			alert("Write information error..");
			return false;
		}
	
		var json_outstr = this.getEmptyString(4096);
	    document.YFFWEBCTRL.CallProc(json_instr, json_instr.length, json_outstr, json_outstr.length);
		
	    for (var i = 0; i < json_outstr.length; i++) if (json_outstr.charCodeAt(i) == 0) json_outstr = json_outstr.substr(0, i);
		
	    var jobj_out = eval("(" + json_outstr + ")");

	    return jobj_out;
		
	},
	
	//扩展卡写卡：
	write_extendcard : function(WRITE_INFO){		
		var json_instr = "{\"dllname\":\"kecard/libyffwebcard.dll\", \"procname\":\"ExpansCardWrite\", \"inputparam\":\"" +WRITE_INFO + "\"}";
		var json_outstr = this.getEmptyString(4096);
		document.YFFWEBCTRL.CallProc(json_instr, json_instr.length, json_outstr, json_outstr.length);
		for (var i = 0; i < json_outstr.length; i++) if (json_outstr.charCodeAt(i) == 0) json_outstr = json_outstr.substr(0, i);
		var jobj_out = eval("(" + json_outstr + ")")
		
		return jobj_out;		
	},
	
	//生成指定长度的空字符串。
	getEmptyString : function(str_len) {
		var ret_str = "";
		
		var count = (str_len + 127) / 128;
		for (var i = 0; i < count; i++) {
			ret_str += "                                                                                                                               ";
			}
			
		return ret_str;
	},
	
	//卡类型
	getCardTp : function(code){ 
		var ret_str = "购电卡";
		switch(code){
			case "FF":
				ret_str = "原始卡";
				break;
			case "00":
				ret_str = "空白卡";
				break;
			case "01":
				ret_str = "开户卡";
				break;
			case "02":
				ret_str = "购电卡";
				break;
			case "03":
				ret_str = "补卡";
				break;
			case "04":
				ret_str = "费率设置卡";
				break;
			case "05":
				ret_str = "检查卡";
				break;
			default :
				break;
		}
		return ret_str;
	},

	getZnkCardTp : function(code){ 
		var ret_str = "购电卡";
		switch(code){
			case 1:
				ret_str = "开户卡";
				break;
			case 2:
				ret_str = "购电卡";
				break;
			case 3:
				ret_str = "补卡";
				break;
			default :
				ret_str = "未知卡类型";
				break;
		}
		
		return ret_str;
	},
	//获取外接卡类型描述
	getExtCardTp : function(code){ 
		var ret_str = "购电卡";
		switch(code){
			case 0:
				ret_str = "空白卡";
				break;
			case 1:
				ret_str = "开户卡";
				break;
			case 2:
				ret_str = "购电卡";
				break;
			case 3:
				ret_str = "补卡";
				break;
			default :
				ret_str = "原始卡";
				break;
		}
		return ret_str;
	},
	
	
	//卡类型
	get6103CardTp : function(code){ 
		var ret_str = "购电卡";
		switch(code){
			case 0:
				ret_str = "开户卡";
				break;
			case 1:
				ret_str = "购电卡";
				break;
			case 2:
				ret_str = "未知卡类型";
				break;
			default :
				break;
		}
		return ret_str;
	},
	
	//高压读卡检索：与其他类型的“检索”按钮返回同样的json。optype：检索时传的操作类型
	readCardSearchGy : function(optype){
		
		//读卡：取得户号
		var json = {};
		
		var json_out = this.readcard();

		if(json_out == undefined || json_out ==""){
			window.top.child_page.setSearchJson2Html(json);
			return ;				
		}
		this.strInfo = json_out.strinfo;
		var cardInfo = json_out.strinfo.split("|");
		var meterType = cardInfo[0];//卡表类型
	//	var cardType = cardInfo[11];//卡类型
		
	//	if(!checkcardType(cardType,optype))
	//	return;
		var meterNo,consNo,cardtype, paymoney;	//表号，户号, 写卡户号
		switch(meterType){
		case SDDef.YFF_METER_TYPE_ZNK :		//"KE_001"
			meterNo 	= cardInfo[9];
			consNo  	= cardInfo[10];
			cardtype	= cardInfo[11];
			paymoney	= cardInfo[12];
			break;
			
		case SDDef.YFF_METER_TYPE_6103 :		//"KE_003"
			cardtype	= cardInfo[0];
			paymoney	= cardInfo[5];
			consNo 		= cardInfo[2];
			meterNo 	= "";	
			break;
		
		case SDDef.YFF_METER_TYPE_ZNK2 :		//"KE_006"
			meterNo 	= cardInfo[8];
			consNo  	= cardInfo[9];
			cardtype	= cardInfo[10];
			paymoney	= cardInfo[11];
			break;
		}

		if(Number(consNo) == 0 || isNaN(consNo)){
			alert("卡内户号[" + consNo + "]，不能检索用户信息。")
			json.IDDATA = cardInfo;
			if (meterType == SDDef.YFF_METER_TYPE_ZNK || meterType == SDDef.YFF_METER_TYPE_ZNK2) {
				json.cardtype	=    this.getZnkCardTp(parseInt(cardtype));
				json.paymoney 	=    parseFloat(paymoney) /100;
			}
			else {
				json.cardtype	=    this.get6103CardTp(parseInt(cardtype));
				json.paymoney 	=    parseFloat(paymoney) /100;
			}
			window.top.child_page.setSearchJson2Html(json);
			return;
		}
		
		//查询数据库：根据户号取得用户信息。
		var params = {};
		params.optype		= optype;			//开户 销户 缴费
		params.meterType 	= meterType;		//001 003
		params.consNo 		= consNo;
		params.meterNo 		= meterNo;

		//loading.loading();
		$.post(def.basePath + "ajaxspec/actConsPara!getInfobyConsId.action",{result : jsonString.json2String(params)},function(data){

			if(data.result != ""){
				var j_rows =eval("(" +  data.result + ")");

				//查询预付费状态
				$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
				{
					firstLastFlag : true,
					userData1: j_rows.rtu_id,
					zjgid : j_rows.zjg_id,
					gsmflag : 0,
					userData2: ComntUseropDef.YFF_GYOPER_GPARASTATE
				},
				function(data) {			    	//回传函数			

					if (data.result == SDDef.SUCCESS) {
						
						json = eval("(" + data.gyOperGParaState + ")");

						json = card_comm.setResJsonValue(json,j_rows, cardInfo);

						if (json.plus_time == null || json.plus_time == undefined || json.plus_time == '0') {
							json.switch_type = 0;
							json.plus_width	 = 0;
						}
						else {
							json.switch_type = 1;
							json.plus_width	 = json.plus_time;
						}
					}
					else {
						json = card_comm.setResJsonValue(json,j_rows, cardInfo);
						alert("查找电表信息错误..");
					}
					if (meterType == SDDef.YFF_METER_TYPE_ZNK || meterType == SDDef.YFF_METER_TYPE_ZNK2) {
						json.cardtype	=    card_comm.getZnkCardTp(parseInt(cardtype));
						json.paymoney 	=    parseFloat(paymoney) /100;
					}
					else {
						json.cardtype	=    card_comm.get6103CardTp(parseInt(cardtype));
						json.paymoney 	=    parseFloat(paymoney) /100;
					}
					window.top.child_page.setSearchJson2Html(json);	
					return;
				});
			}else{
				json.IDDATA = cardInfo;
				if (meterType == SDDef.YFF_METER_TYPE_ZNK || meterType == SDDef.YFF_METER_TYPE_ZNK2) {
					json.cardtype	=    card_comm.getZnkCardTp(parseInt(cardtype));
					json.paymoney 	=    parseFloat(paymoney) /100;
				}
				else {
					json.cardtype	=    card_comm.get6103CardTp(parseInt(cardtype));
					json.paymoney 	=    parseFloat(paymoney) /100;
				}
				alert("读取用户信息失败！");
				window.top.child_page.setSearchJson2Html(json);
				return;
			}
		});  
	},
	
	//低压读卡检索：与其他类型的“检索”按钮返回同样的json。optype：检索时传的操作类型
	readCardSearchDy : function(optype){
		
		//读卡：取得户号
		var json = {};
		
		var json_out = this.readcard();
		if(json_out == undefined || json_out ==""){
			return ;				
		}
		this.strInfo = json_out.strinfo;//记录卡内字符串部分内容-----
		var cardInfo = json_out.strinfo.split("|");
		var meterType = cardInfo[0];//卡表类型
		var meterNo,consNo,cardtype, paymoney;	//表号，户号, 写卡户号
		switch(meterType){
		case SDDef.YFF_METER_TYPE_ZNK :		//"KE_001"
			meterNo 	= cardInfo[9];
			consNo  	= cardInfo[10];
			cardtype	= cardInfo[11];
			paymoney	= cardInfo[12];
			break;
	
		//保留
		case SDDef.YFF_METER_TYPE_6103 :	//"KE_003"
			cardtype	= cardInfo[0];
			paymoney	= cardInfo[5];
			consNo 		= cardInfo[2];
			meterNo 	= "";	
			break;
			
		//20131021新添加	KE_006
		case SDDef.YFF_METER_TYPE_ZNK2 :	//"KE_006"
			meterNo 	= cardInfo[8];
			consNo  	= cardInfo[9];
			cardtype	= cardInfo[10];
			paymoney	= cardInfo[11];
			break;
		}
		if(Number(consNo) == 0 || isNaN(consNo)){
			json.IDDATA = cardInfo;
			//20131021新添加	KE_006
			if (meterType == SDDef.YFF_METER_TYPE_ZNK || meterType == SDDef.YFF_METER_TYPE_ZNK2) {
				json.cardtype	=    this.getZnkCardTp(parseInt(cardtype));
				json.paymoney 	=    parseFloat(paymoney) /100;
			}
			else {
				json.cardtype	=    this.get6103CardTp(parseInt(cardtype));
				json.paymoney 	=    parseFloat(paymoney) /100;
			}
			window.top.child_page.setSearchJson2Html(json);
			return;
		}
		

		//查询数据库：根据户号取得用户信息。
		var params = {};
		params.optype		= optype;			//开户 销户 缴费
		params.meterType 	= meterType;		//001
		params.consNo 		= consNo;
		params.meterNo 		= meterNo;

		$.post(def.basePath + "ajaxdyjc/actConsPara!getInfobyConsId.action",{result : jsonString.json2String(params)},function(data){

			if(data.result != ""){
				var j_rows =eval("(" +  data.result + ")");

				//查询预付费状态
				$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
				{
					userData1 : j_rows.rtu_id,
					mpid 	  : j_rows.mp_id,
					gsmflag   : 0,
					userData2 : ComntUseropDef.YFF_DYOPER_GPARASTATE
				},
				function(data) {			    	//回传函数			

					if (data.result == SDDef.SUCCESS) {
						json = eval("(" + data.dyOperGParaState + ")");
						json = card_comm.setResJsonValue(json, j_rows, cardInfo);
						json.resident_id 	= j_rows.resident_id;//更改手机读卡时需要传进客户编号 20130326  zhp
					}
					else {
						json = card_comm.setResJsonValue(json, j_rows, cardInfo);
						json.resident_id 	= j_rows.resident_id;//更改手机读卡时需要传进客户编号 20130326  zhp
						alert("查找电表信息错误..");
					}
					//20131021 添加ke006
					if (meterType == SDDef.YFF_METER_TYPE_ZNK || meterType == SDDef.YFF_METER_TYPE_ZNK2) {
						json.cardtype	=    card_comm.getZnkCardTp(parseInt(cardtype));
						json.paymoney 	=    parseFloat(paymoney) /100;
					}
					else {
						json.cardtype	=   card_comm.get6103CardTp(parseInt(cardtype));
						json.paymoney 	=    parseFloat(paymoney) /100;
					}

					json.orgname = j_rows.orgname;
					window.top.child_page.setSearchJson2Html(json);	
					return;
				});
			}else{
				json.IDDATA = cardInfo;
				//20131021 添加ke006
				if (meterType == SDDef.YFF_METER_TYPE_ZNK || meterType == SDDef.YFF_METER_TYPE_ZNK2) {
					json.cardtype	=    card_comm.getZnkCardTp(parseInt(cardtype));
					json.paymoney 	=    parseFloat(paymoney) /100;
				}
				else {
					json.cardtype	=    card_comm.get6103CardTp(parseInt(cardtype));
					json.paymoney 	=    parseFloat(paymoney) /100;
				}
				alert("读取用户信息失败！");
				window.top.child_page.setSearchJson2Html(json);
				return;
			}
		});  
	},
	//201406zkz
	//外接表低压读卡检索：与其他类型的“检索”按钮返回同样的json。optype：检索时传的操作类型
	readExtCardSearchDy : function(optype){
		
		//读卡：取得户号
		var json = {};
		var json_out = this.readExtcard();
		if(json_out == undefined || json_out ==""){
			return ;				
		}
		this.strInfo = json_out.strinfo;//记录卡内字符串部分内容-----
		var cardInfo = json_out.strinfo.split("|");
//		var meterType = cardInfo[0];//卡表类型
		var meterNo,consNo,cardtype, paymoney;	//表号，户号, 写卡户号
		
		consNo  	= cardInfo[0];
		meterNo 	= cardInfo[5];
		paymoney	= cardInfo[7];

		if(Number(consNo) == 0 || isNaN(consNo)){
			json.IDDATA = cardInfo;
			window.top.child_page.setSearchJson2Html(json);
			return;
		}
		

		//查询数据库：根据户号取得用户信息。
		var params = {};
		params.optype		= optype;			//开户 销户 缴费
		params.consNo 		= consNo;
		params.meterNo 		= meterNo;

		$.post(def.basePath + "ajaxdyjc/actConsPara!getInfobyConsIdExt.action",{result : jsonString.json2String(params)},function(data){

			if(data.result != ""){
				var j_rows =eval("(" +  data.result + ")");

				//查询预付费状态
				$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
				{
					userData1 : j_rows.rtu_id,
					mpid 	  : j_rows.mp_id,
					gsmflag   : 0,
					userData2 : ComntUseropDef.YFF_DYOPER_GPARASTATE
				},
				function(data) {			    	//回传函数			

					if (data.result == SDDef.SUCCESS) {
						json = eval("(" + data.dyOperGParaState + ")");
						json = card_comm.setResExtJsonValue(json, j_rows, cardInfo);
						json.resident_id 	= j_rows.resident_id;//更改手机读卡时需要传进客户编号 20130326  zhp
					}
					else {
						json = card_comm.setResExtJsonValue(json, j_rows, cardInfo);
						json.resident_id 	= j_rows.resident_id;//更改手机读卡时需要传进客户编号 20130326  zhp
						alert("查找电表信息错误..");
					}
					json.orgname = j_rows.orgname;
					window.top.child_page.setSearchJson2Html(json);	
					return;
				});
			}else{
				json.IDDATA = cardInfo;
				alert("读取用户信息失败！");
				window.top.child_page.setSearchJson2Html(json);
				return;
			}
		});  
	},
	//end
	
	//201407zhp
	//外接表低压读卡检索(不同用户具有相同写卡户号的情况)：与其他类型的“检索”按钮返回同样的json。optype：检索时传的操作类型
	readExtCardSameWrtNo : function(optype){
		
		//读卡：取得户号
		var json = {};
		var json_out = this.readExtcard();
		if(json_out == undefined || json_out ==""){
			return ;				
		}
		this.strInfo = json_out.strinfo;//记录卡内字符串部分内容-----
		var cardInfo = json_out.strinfo.split("|");

		var meterNo,consNo,cardtype, paymoney;	//表号，户号, 写卡户号
		
		consNo  	= cardInfo[0];
		meterNo 	= cardInfo[5];
		paymoney	= cardInfo[7];

		if(Number(consNo) == 0 || isNaN(consNo)){
			json.IDDATA = cardInfo;
			window.top.child_page.setSearchJson2Html(json);
			return;
		}
		
		//查询数据库：根据户号取得用户信息。
		var params = {};
		params.optype		= optype;			//开户 销户 缴费
		params.consNo 		= consNo;
		params.meterNo 		= meterNo;
		
		//查询数据库，如果改写卡户号只有一户，则按读取该用户信息。
		//如果为多个用户所有，则弹出窗口，列出用户列表进行选择。
		$.post(def.basePath + "ajaxdyjc/actConsPara!getInfobyWrtCard.action",
				{
					result : jsonString.json2String(params)
				},
				function(data){
					//只有一个用户使用该户号				
					if(data.consNum == 1){
						$.post(def.basePath + "ajaxdyjc/actConsPara!getInfobyConsIdExt.action",{result : jsonString.json2String(params)},function(data){

							if(data.result != ""){
								var j_rows =eval("(" +  data.result + ")");

								//查询预付费状态
								$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
								{
									userData1 : j_rows.rtu_id,
									mpid 	  : j_rows.mp_id,
									gsmflag   : 0,
									userData2 : ComntUseropDef.YFF_DYOPER_GPARASTATE
								},
								function(data) {			    	//回传函数			

									if (data.result == SDDef.SUCCESS) {
										json = eval("(" + data.dyOperGParaState + ")");
										json = card_comm.setResExtJsonValue(json, j_rows, cardInfo);
										json.resident_id 	= j_rows.resident_id;//更改手机读卡时需要传进客户编号 20130326  zhp
									}
									else {
										json = card_comm.setResExtJsonValue(json, j_rows, cardInfo);
										json.resident_id 	= j_rows.resident_id;//更改手机读卡时需要传进客户编号 20130326  zhp
										alert("查找电表信息错误..");
									}
									json.orgname = j_rows.orgname;
									window.top.child_page.setSearchJson2Html(json);	
									return;
								});
							}else{
								json.IDDATA = cardInfo;
								alert("读取用户信息失败！");
								window.top.child_page.setSearchJson2Html(json);
								return;
							}
						});  
					}
					//有多个用户使用该户号，弹出模态窗口，进行选择
					else if(data.consNum > 1){
						var j_rows =eval("(" +  data.result + ")");
						modalDialog.height = 330;
						modalDialog.width = 650;
						modalDialog.url = def.basePath + "jsp/dyjc/dialog/readCardSearch.jsp";
						modalDialog.param = j_rows;
						//返回值，具体某一行数据
						var backData = modalDialog.show();
						if(backData!=undefined || backData!=null){
							//调用父页面方法，进行数据展示
							window.top.child_page.rtnValue = backData;
							window.top.child_page.setcons1();
						}
						else{
							//不进行操作关闭窗口，取消loading
							window.top.child_page.closeloading();
						}
					}
					else {
						json.IDDATA = cardInfo;
						alert("读取用户信息失败！");
						window.top.child_page.setSearchJson2Html(json);
						return;
					}
				});
	},
	//end
	
	//农排读卡检索：与其他类型的“检索”按钮返回同样的json。optype：检索时传的操作类型
	readCardSearchNp : function(optype){
		
		//读卡：取得户号
		var json = {};
		
		var json_out = this.readcard();
		if(json_out == undefined || json_out ==""){
			return ;				
		}
		this.strInfo = json_out.strinfo;//记录卡内字符串部分内容-----
		var cardInfo = json_out.strinfo.split("|");
		var meterType = cardInfo[0];//卡表类型
		var cardNo, consNo, cardtype, remainMoney,card_state;	//卡号，户号, 卡类型, 剩余金额
		switch(meterType){
		case SDDef.YFF_METER_TYPE_NP :			//"KE_005"
			cardNo 		= cardInfo[2];
			consNo  	= cardInfo[8];
			cardtype	= cardInfo[9];
			remainMoney	= cardInfo[3];
			card_state  = cardInfo[11];
			
			break;
		}

		if(Number(cardNo) == 0 || isNaN(cardNo)){
			alert("卡号[" + cardNo + "]，不能检索用户信息。")
			json.IDDATA = cardInfo;
			if (meterType == SDDef.YFF_METER_TYPE_NP) {
				json.cardtype	=   this.getZnkCardTp(parseInt(cardtype));
				json.paymoney 	=   "0";//parseFloat(paymoney) /100;
				json.now_remain =   parseFloat(remainMoney) / 100;
			}
			json.card_no = 0;
			window.top.child_page.setSearchJson2Html(json);
			return;
		}
		//查询数据库：根据户号取得用户信息。
		var params = {};
		params.optype		= optype;			//开户 销户 缴费
		params.meterType 	= meterType;		//001
		params.consNo 		= consNo;
		params.meterNo 		= "";
		params.cardNo 		= cardNo;
		
		$.post(def.basePath + "ajaxnp/actConsPara!getInfobyCardNo.action",{result : jsonString.json2String(params)},function(data){
			if(data.result != ""){
				var j_rows =eval("(" +  data.result + ")");
				//查询预付费状态
				$.post( def.basePath + "ajaxoper/actOperNp!taskProc.action", 		//后台处理程序
				{
					userData1: j_rows.area_id,
					farmerid : j_rows.id,
					gsmflag : 0,
					userData2: ComntUseropDef.YFF_NPOPER_GPARASTATE //获取农排预付费参数
				},
				function(data) {			    	//回传函数			

					if (data.result == SDDef.SUCCESS) {					
						json = eval("(" + data.npOperGParaState + ")");//获取农排预付费参数所组成的对象
						json = card_comm.setNpJsonValue(json, j_rows, cardInfo);
					}
					else {
						json = card_comm.setNpJsonValue(json, j_rows, cardInfo);
						alert("查找电表信息错误..");
					}
					
					if (meterType == SDDef.YFF_METER_TYPE_NP) {
						json.cardtype	=    card_comm.getZnkCardTp(parseInt(cardtype));			
						json.paymoney 	=    "0";//parseFloat(paymoney) /100;
						json.now_remain=   parseFloat(remainMoney) / 100;
						json.card_state = card_state;
						if(card_state == 0) {
							json.card_state_desc = "正常";
						}
						else {
							json.card_state_desc = "灰锁";
						}
					}
//					else {
//						json.cardtype	=   card_comm.get6103CardTp(parseInt(cardtype));
//						json.paymoney 	=    parseFloat(paymoney) /100;
//					}
					window.top.child_page.setSearchJson2Html(json);
					return;
				});
			}else{
				json.IDDATA  = cardInfo;
				json.card_no = cardNo;
				if (meterType == SDDef.YFF_METER_TYPE_NP) {
					json.cardtype	=    card_comm.getZnkCardTp(parseInt(cardtype));
					json.paymoney 	=    "";
					json.now_remain=   parseFloat(remainMoney) / 100;
				}
//				else {
//					json.cardtype	=    card_comm.get6103CardTp(parseInt(cardtype));
//					json.paymoney 	=    parseFloat(paymoney) /100;
//				}
				alert("读取用户信息失败！");
				window.top.child_page.setSearchJson2Html(json);
				return;
			}
		});  
	},
	
	//20131210	zhp	begin
	//农排根据写卡金额是否成功写入判断是否重新写卡
	//本次缴费金额成功写入，则进行oper操作
	//到达写入限制次数且未成功则提示异常
	ReWriteCard : function(writestr, checkmoney) {
		for (var i = 0; i < window.top.rewrite_maxnum_np; i++) {
			var ret_json = {};
			ret_json = this.writecard(writestr);
			if(ret_json.errno  != 0){
				return false;	
			}
			
			if(this.isMoneyExist_NP(checkmoney)){
				break;
			}
		}
		return i < window.top.rewrite_maxnum_np;
	},
	
	//农排再次读卡，看缴费金额是否成功写入
	isMoneyExist_NP : function(checkmoney)
	{
		var json_out = {};
		for (var i = 0; i < window.top.reread_maxnum_np; i++) {
			json_out = this.readcard();
		
			if ((json_out) && (json_out !="")) {
				break;		
			}
		}
		
		//再次读卡失败返回 true
		if (i >= window.top.reread_maxnum_np) return true;		

		var pay_num = 0, pay_money = 0;				//购电次数
		var meterType = json_out.meter_type;		//卡表类型
		
		if(meterType == window.top.SDDef.YFF_METER_TYPE_NP){
			cardnum = json_out.pay_num;
			cardmoney = json_out.pay_money; 
			if(checkmoney == cardmoney){
				return true;
			}	
		}
		return false;
	},
	//20131210 end
	
	setResJsonValue : function(json, j_rows, cardInfo){
		
		json.fee_unit	 	= j_rows.fee_unit;
		json.username   	= j_rows.username;
		json.userno 	 	= j_rows.userno;
		json.useraddr    	= j_rows.useraddr;
		json.tel 		 	= j_rows.tel;
		json.factory     	= j_rows.factory;
		json.blv 		 	= j_rows.blv;
		json.pt_ratio 	 	= j_rows.pt_ratio;
		json.ct_ratio  		= j_rows.ct_ratio;
		json.pt 	 		= j_rows.pt_ratio;
		json.ct  			= j_rows.ct_ratio;
		json.meter_id 	 	= j_rows.meter_id;
		json.esamno 	 	= j_rows.meter_id;
		json.wiring_mode	= j_rows.wiring_mode;
		json.writecard_no 	= j_rows.writecard_no;
		json.cardmeter_type	= j_rows.cardmeter_type;
		//20131216新增加阶梯年结算日
		json.jt_cycle_md	= j_rows.jt_cycle_md;
	
		var online = j_rows.online;
		json.onlinetxt	 ="<b style='color:#800000'>终端" + online + "</b>";
		if(online =="在线"){
			json.onlinesrc	 = def.basePath + "images/rtuzx_online.gif";
		}else{
			json.onlinesrc	 = def.basePath + "images/rtuzx_offline.gif";
		}
	
		json.rtu_model	 = j_rows.rtu_model;
		json.prot_type	 = j_rows.prot_type;
		
		json.IDDATA 	 = cardInfo;

		return json;
	},
	
	//20140611
	setResExtJsonValue : function(json, j_rows, cardInfo){
		
		json.fee_unit	 	= j_rows.fee_unit;
		json.username   	= j_rows.username;
		json.userno 	 	= j_rows.userno;
		json.useraddr    	= j_rows.useraddr;
		json.tel 		 	= j_rows.tel;
		json.factory     	= j_rows.factory;
		json.blv 		 	= j_rows.blv;
		json.pt_ratio 	 	= j_rows.pt_ratio;
		json.ct_ratio  		= j_rows.ct_ratio;
		json.pt 	 		= j_rows.pt_ratio;
		json.ct  			= j_rows.ct_ratio;
		json.meter_id 	 	= j_rows.meter_id;
		json.esamno 	 	= j_rows.meter_id;
		json.wiring_mode	= j_rows.wiring_mode;
		json.writecard_no 	= j_rows.writecard_no;
		json.cardmeter_type	= j_rows.cardmeter_type;
		//20131216新增加阶梯年结算日
		json.jt_cycle_md	= j_rows.jt_cycle_md;
	
		var online = j_rows.online;
		json.onlinetxt	 ="<b style='color:#800000'>终端" + online + "</b>";
		if(online =="在线"){
			json.onlinesrc	 = def.basePath + "images/rtuzx_online.gif";
		}else{
			json.onlinesrc	 = def.basePath + "images/rtuzx_offline.gif";
		}
	
		json.rtu_model	 = j_rows.rtu_model;
		json.prot_type	 = j_rows.prot_type;
		
		json.cardtype	 = j_rows.cardtype;
		json.card_pass	 = j_rows.card_pass;
		json.card_area	 = j_rows.card_area;
		json.card_rand	 = j_rows.card_rand;
		
		
		json.IDDATA 	 = cardInfo;

		return json;
	},
	
	setNpJsonValue : function(json, j_rows, cardInfo){

		json.area_id	= j_rows.area_id;
		json.id			= j_rows.id;
		json.username	= j_rows.describe;
		json.area		= j_rows.area_desc;
		json.area_code	= j_rows.area_code;
		json.org_desc	= j_rows.org_desc;
		json.userno		= j_rows.farmer_no;
		json.card_no	= j_rows.card_no;
		
		json.identityno = j_rows.identity_no;
		json.village	= j_rows.village;
		json.useraddr	= j_rows.address;
		json.post		= j_rows.post;
		json.tel		= j_rows.mobile;	//联系电话为移动电话
		json.mobile		= j_rows.mobile;
		//json.card_no 	= j_rows.card_no;
		json.cardmeter_type	= j_rows.cardmeter_type;
		
		json.IDDATA 	 = cardInfo;

		return json;
	},
	
	cardinfo : function(){//卡内信息
		modalDialog.width = 500;
		modalDialog.height = 600;
		modalDialog.url = def.basePath + "jsp/dialog/cardinfo.jsp";
		modalDialog.show();
	},
	
	//qjl add 20150310 start 孟加拉 读取卡内信息
	cardinfoBengal : function(){//卡内信息
		modalDialog.width = 600;
		modalDialog.height = 600;
		modalDialog.url = def.basePath + "jsp/dialog/cardinfoBengal.jsp";
		modalDialog.show();
	},
	//qjl add 20150310 end 

	cardinfomjl : function(){//卡内信息
		modalDialog.width = 500;
		modalDialog.height = 600;
		modalDialog.url = def.basePath + "jsp/dialog/cardinfomjl.jsp";
		modalDialog.show();
	},
	
	extcardinfo : function(){//卡内信息
		modalDialog.width = 500;
		modalDialog.height = 600;
		modalDialog.url = def.basePath + "jsp/dialog/extcardinfo.jsp";
		modalDialog.show();
	},

	checkInfo : function(json){//检查  卡表类型、写卡户号、表号。
		var tmp_val = json.cardmeter_type.split("]");
		writecard_type = tmp_val[0].split("[")[1];
		//调整 20130402	
		//	var writecard_type = tmp_val[0].substring(1);
		
		var caclType = json.cacl_type;//计费方式 

		if(writecard_type == ""){
			alert("卡表类型未配置。请到【档案管理-费控参数管理】配置卡表类型。")
			return false;
		}
		if(writecard_type == SDDef.YFF_CARDMTYPE_KE003){
			if(json.writecard_no =="" || json.writecard_no == undefined){
				alert("写卡户号未配置。请到【档案管理-费控参数管理】配置卡表类型。")
				return false;
			}	
			if(caclType != this.caclType.bd){
				alert("6103表请选用表底计费方式。");
				return false;
			}
		}else if(writecard_type == SDDef.YFF_CARDMTYPE_KE001){
			if(json.meter_id =="" || json.meter_id == undefined){
				alert("表号未配置。请到用电系统电表档案页面中配置表号信息。")
				return false;
			}
			if(caclType != this.caclType.je){
				alert("智能表请使用金额计费方式。")
				return false;
			}
		}
		//20131021添加	新表卡KE006
		else if(writecard_type == SDDef.YFF_CARDMTYPE_KE006){
			if(json.meter_id =="" || json.meter_id == undefined){
				alert("表号未配置。请到用电系统电表档案页面中配置表号信息。")
				return false;
			}
			if(caclType != this.caclType.je){
				alert("智能表请使用金额计费方式。")
				return false;
			}
		}
		else if(writecard_type == SDDef.YFF_CARDMTYPE_KE005){
			if(json.card_no =="" || json.card_no == undefined){
				alert("卡号未配置。请到用电系统电表档案页面中配置卡号信息。")
				return false;
			}
		}
		return true;
	},
	
	checkExtInfo : function(json){//检查  卡表类型、写卡户号、表号。
		var consNo, cacl_type, feeType, cardtype;//户号，算费类型(电量、金额、表底),费率方案(单、复、阶梯),表类型(KL_001..JJ_001..)
		
		consNo = json.userno;
		cacl_type = json.cacl_type;
	 	feeType = json.feeType;
	 	cardtype = json.cardtype;
	 	
	 	if(consNo.replace(/(^0*)/g, "") == '')	{
			alert("用户户号为空，不能缴费！")
			return false;
		}
	 	else if(feeType == 1 && cacl_type == 3){
			alert("电量计费方式不能为复费率！");
			return false;
		}
		else if(!this.checkExtDoc(cacl_type,cardtype)){
			alert("计费方式与表类型不一致！");
			return false;
		}
		
		
//		var tmp_val = json.cardmeter_type.split("]");
//		writecard_type = tmp_val[0].split("[")[1];
//		//调整 20130402	
//
//		var caclType = json.cacl_type;//计费方式 
//
//		if(writecard_type == ""){
//			alert("卡表类型未配置。请到【档案管理-费控参数管理】配置卡表类型。")
//			return false;
//		}
//		if(writecard_type == SDDef.YFF_CARDMTYPE_KE003){
//			if(json.writecard_no =="" || json.writecard_no == undefined){
//				alert("写卡户号未配置。请到【档案管理-费控参数管理】配置卡表类型。")
//				return false;
//			}	
//			if(caclType != this.caclType.bd){
//				alert("6103表请选用表底计费方式。");
//				return false;
//			}
//		}else if(writecard_type == SDDef.YFF_CARDMTYPE_KE001){
//			if(json.meter_id =="" || json.meter_id == undefined){
//				alert("表号未配置。请到用电系统电表档案页面中配置表号信息。")
//				return false;
//			}
//			if(caclType != this.caclType.je){
//				alert("智能表请使用金额计费方式。")
//				return false;
//			}
//		}
//		//20131021添加	新表卡KE006
//		else if(writecard_type == SDDef.YFF_CARDMTYPE_KE006){
//			if(json.meter_id =="" || json.meter_id == undefined){
//				alert("表号未配置。请到用电系统电表档案页面中配置表号信息。")
//				return false;
//			}
//			if(caclType != this.caclType.je){
//				alert("智能表请使用金额计费方式。")
//				return false;
//			}
//		}
//		else if(writecard_type == SDDef.YFF_CARDMTYPE_KE005){
//			if(json.card_no =="" || json.card_no == undefined){
//				alert("卡号未配置。请到用电系统电表档案页面中配置卡号信息。")
//				return false;
//			}
//		}
		return true;
	},
	
	//判断外接表计费方式与表类型是否一致
	//cacl_type:计费方式    	cardtype: 表类型
	checkExtDoc : function(cacl_type,cardtype){
		var flag = true;
		switch(cardtype){
		case 'KL_001':
		case 'KL_002':
		case 'KL_003':
		case 'KL_004':
		case 'JJ_001':
		case 'JJ_002':
			if(cacl_type != 1){
				flag = false;
			}
		break;
		case 'KL_005':
		case 'JJ_003':
		case 'RM_001':
			if(cacl_type != 3){
				flag = false;
			}
		}
		return flag;
	},
	
	//读卡字符串转成json格式。保留j_out原有内容
	readString2Json : function (j_out){
		var jobj_out = {};
		jobj_out.errno = j_out.errno;
		jobj_out.strinfo = j_out.strinfo;
		jobj_out.errstr = j_out.errstr;
		
		var cardInfo = j_out.strinfo.split("|");
		var meterType = cardInfo[0];	//卡表类型
		
		jobj_out.meter_type = meterType;	//卡表类型 
		
		switch(meterType){
		case SDDef.YFF_METER_TYPE_ZNK :	//"KE_001"
			jobj_out.user_type	= cardInfo[1];//01 单费率 02 复费率
			jobj_out.bit_update	= cardInfo[2];//参数更新标志位
			jobj_out.chg_date	= cardInfo[3];//分时日期
			jobj_out.chg_time	= cardInfo[4];//分时时间
			jobj_out.alarm_val1	= cardInfo[5];//报警值1
			jobj_out.alarm_val2	= cardInfo[6];//报警值2
			jobj_out.ct			= cardInfo[7];//CT
			jobj_out.pt			= cardInfo[8];//PT
			jobj_out.meterno 	= cardInfo[9];//表号
			jobj_out.consno  	= cardInfo[10];//客户编号
			jobj_out.card_type	= cardInfo[11];//1 开户 2 购电 3 补卡 
			jobj_out.pay_money	= cardInfo[12];//上次缴费金额(分)
			jobj_out.pay_num	= cardInfo[13];//购电次数
			
			jobj_out.fee1_rated_z	= cardInfo[14];//费率1总费率
			jobj_out.fee1_ratef_j	= cardInfo[15];//费率1尖费率
			jobj_out.fee1_ratef_f	= cardInfo[16];//费率1峰费率
			jobj_out.fee1_ratef_p	= cardInfo[17];//费率1平费率
			jobj_out.fee1_ratef_g	= cardInfo[18];//费率1谷费率
			jobj_out.fee2_rated_z	= cardInfo[19];//费率2总费率
			jobj_out.fee2_ratef_j	= cardInfo[20];//费率2尖费率
			jobj_out.fee2_ratef_f	= cardInfo[21];//费率2峰费率
			jobj_out.fee2_ratef_p	= cardInfo[22];//费率2平费率
			jobj_out.fee2_ratef_g	= cardInfo[23];//费率2谷费率
			
			///////////返写信息
			jobj_out.back_user_type	= cardInfo[24];//01 单费率 02 复费率 
			jobj_out.back_ct		= cardInfo[25];//CT
			jobj_out.back_pt		= cardInfo[26];//PT
			jobj_out.back_meterno	= cardInfo[27];//表号
			jobj_out.back_consno	= cardInfo[28];//客户编号
			jobj_out.back_remain_money	= cardInfo[29];//剩余金额 单位为分
			jobj_out.back_pay_num		= cardInfo[30];//购电次数
			jobj_out.back_overd_money	= cardInfo[31];//透支金额
			jobj_out.back_key_state		= cardInfo[32];//密钥状态
			jobj_out.back_key_updatetype= cardInfo[33];//更新方式
			jobj_out.back_key_identify	= cardInfo[34];//标识
			jobj_out.back_key_ver		= cardInfo[35];//密钥版本
			jobj_out.back_errin_num		= cardInfo[36];//非法插卡次数
			jobj_out.back_reback_date	= cardInfo[37];//返写日期	YY-MM-DD
			jobj_out.back_reback_time	= cardInfo[38];//返写时间         HH-MI-00			
			break;			
		//20131021添加新智能卡
		case SDDef.YFF_METER_TYPE_ZNK2:	//"KE_006"
			jobj_out.bit_update	    = cardInfo[1];//参数更新标志位
			jobj_out.chg_date	    = cardInfo[2];//分时日期
			jobj_out.chg_time	    = cardInfo[3];//分时时间
			jobj_out.alarm_val1	    = cardInfo[4];//报警值1
			jobj_out.alarm_val2	    = cardInfo[5];//报警值2
			jobj_out.ct			    = cardInfo[6];//CT
			jobj_out.pt			    = cardInfo[7];//PT
			jobj_out.meterno 	    = cardInfo[8];//表号
			jobj_out.consno  	    = cardInfo[9];//客户编号
			jobj_out.card_type	    = cardInfo[10];//1 开户 2 购电 3 补卡 
			jobj_out.pay_money	    = cardInfo[11];//上次缴费金额(分)
			jobj_out.pay_num	    = cardInfo[12];//购电次数
			                                 
			jobj_out.fee1_ratef_j	= cardInfo[13];//费率1总费率
			jobj_out.fee1_ratef_f	= cardInfo[14];//费率1尖费率
			jobj_out.fee1_ratef_p	= cardInfo[15];//费率1峰费率
			jobj_out.fee1_ratef_g	= cardInfo[16];//费率1平费率
			jobj_out.fee1_rated_z	= cardInfo[17];//费率1保留
			
			jobj_out.fee1_jt_step1  = cardInfo[18];//阶梯梯度值1     
			jobj_out.fee1_jt_step2  = cardInfo[19];//阶梯梯度值2     
			jobj_out.fee1_jt_step3  = cardInfo[20];//阶梯梯度值3     
			jobj_out.fee1_jt_step4  = cardInfo[21];//阶梯梯度值4     
			jobj_out.fee1_jt_step5  = cardInfo[22];//阶梯梯度值5     
			jobj_out.fee1_jt_step6	= cardInfo[23];//阶梯梯度值6     
			jobj_out.fee1_jt_step7	= cardInfo[24];//阶梯梯度值7     
			jobj_out.fee1_jt_rate1  = cardInfo[25];//阶梯费率1    
			jobj_out.fee1_jt_rate2  = cardInfo[26];//阶梯费率2    
			jobj_out.fee1_jt_rate3  = cardInfo[27];//阶梯费率3    
			jobj_out.fee1_jt_rate4  = cardInfo[28];//阶梯费率4    
			jobj_out.fee1_jt_rate5  = cardInfo[29];//阶梯费率5    
			jobj_out.fee1_jt_rate6  = cardInfo[30];//阶梯费率6    
			jobj_out.fee1_jt_rate7  = cardInfo[31];//阶梯费率7    
			jobj_out.fee1_jt_rate8  = cardInfo[32];//阶梯费率8    
			jobj_out.fee1_jt_jsmdh  = cardInfo[33];//阶梯年结算日1  
			jobj_out.fee1_jt_jsmdh  = cardInfo[34];//阶梯年结算日2  
			jobj_out.fee1_jt_jsmdh  = cardInfo[35];//阶梯年结算日3  
			jobj_out.fee1_jt_jsmdh  = cardInfo[36];//阶梯年结算日4  
			jobj_out.fee1_jt_jsmdh	= cardInfo[37];//阶梯年结算日5  
			jobj_out.fee1_jt_chgymd	= cardInfo[38];//阶梯切换YMD  
			jobj_out.fee1_jt_cghm	= cardInfo[39];//阶梯切换HM   
			
			jobj_out.fee2_ratef_j	= cardInfo[40];//费率2尖费率
			jobj_out.fee2_ratef_f	= cardInfo[41];//费率2峰费率
			jobj_out.fee2_ratef_p	= cardInfo[42];//费率2平费率
			jobj_out.fee2_ratef_g	= cardInfo[43];//费率2谷费率
			jobj_out.fee2_rated_z	= cardInfo[44];//费率2保留
			jobj_out.fee2_jt_step1  = cardInfo[45];//阶梯梯度值1     
			jobj_out.fee2_jt_step2  = cardInfo[46];//阶梯梯度值2     
			jobj_out.fee2_jt_step3  = cardInfo[47];//阶梯梯度值3     
			jobj_out.fee2_jt_step4  = cardInfo[48];//阶梯梯度值4     
			jobj_out.fee2_jt_step5  = cardInfo[49];//阶梯梯度值5     
			jobj_out.fee2_jt_step6	= cardInfo[50];//阶梯梯度值6     
			jobj_out.fee2_jt_step7	= cardInfo[51];//阶梯梯度值7     
			jobj_out.fee2_jt_rate1  = cardInfo[52];//阶梯费率1    
			jobj_out.fee2_jt_rate2  = cardInfo[53];//阶梯费率2    
			jobj_out.fee2_jt_rate3  = cardInfo[54];//阶梯费率3    
			jobj_out.fee2_jt_rate4  = cardInfo[55];//阶梯费率4    
			jobj_out.fee2_jt_rate5  = cardInfo[56];//阶梯费率5    
			jobj_out.fee2_jt_rate6  = cardInfo[57];//阶梯费率6    
			jobj_out.fee2_jt_rate7  = cardInfo[58];//阶梯费率7    
			jobj_out.fee2_jt_rate8  = cardInfo[59];//阶梯费率8    
			jobj_out.fee2_jt_jsmdh  = cardInfo[60];//阶梯年结算日1  
			jobj_out.fee2_jt_jsmdh  = cardInfo[61];//阶梯年结算日2  
			jobj_out.fee2_jt_jsmdh  = cardInfo[62];//阶梯年结算日3  
			jobj_out.fee2_jt_jsmdh  = cardInfo[63];//阶梯年结算日4  
			jobj_out.fee2_jt_jsmdh	= cardInfo[64];//阶梯年结算日5  
			jobj_out.fee2_jt_chgymd	= cardInfo[65];//阶梯切换YMD  
			jobj_out.fee2_jt_cghm	= cardInfo[66];//阶梯切换HM 
			                                   
			///////////返写信息 
			jobj_out.back_ct			= cardInfo[67];//CT
			jobj_out.back_pt			= cardInfo[68];//PT
			jobj_out.back_meterno		= cardInfo[69];//表号
			jobj_out.back_consno		= cardInfo[70];//客户编号
			jobj_out.back_remain_money	= cardInfo[71];//剩余金额 单位为分
			jobj_out.back_pay_num		= cardInfo[72];//购电次数
			jobj_out.back_overd_money	= cardInfo[73];//透支金额
			jobj_out.back_errin_num		= cardInfo[74];//非法插卡次数
			jobj_out.back_reback_date	= cardInfo[75];//返写日期	YY-MM-DD
			jobj_out.back_reback_time	= cardInfo[76];//返写时间         HH-MI-00			
			break;	

		case SDDef.YFF_METER_TYPE_6103 : //"KE_003"
			jobj_out.meterno 	= "";//6103没有表号。跟智能卡保持一致，返回一个空的meterno
			jobj_out.back_meterno 	= "";
			
			jobj_out.card_type	= cardInfo[1];	//0 开户 1 购电 2补卡	6单费率检查卡 
			jobj_out.consno		= cardInfo[2];	//客户编号   
			jobj_out.alarm_val1 = cardInfo[3];	//报警电量1  0.01kwh
			jobj_out.alarm_val2 = cardInfo[4];	//报警电量2  0.01kwh
			jobj_out.buy_dl 	= cardInfo[5];	//购电量	0.01kwh
			jobj_out.pay_num 	= cardInfo[6];	//购电次数
			jobj_out.limit_dl  	= cardInfo[7];	//囤积电量  0.01kwh
			jobj_out.ptct 		= cardInfo[8];	//倍率 
			jobj_out.reserve1  	= cardInfo[9];	//备用1
			jobj_out.reserve2  	= cardInfo[10];	//备用2
			jobj_out.reserve3 	= cardInfo[11];	//备用3
			
			//返写信息
			jobj_out.back_meter_type 	= cardInfo[12];	//00 单相单费率 30单相复费率 80 三相单费率 b0 三相复费率 d0三相多功能
			jobj_out.back_consno 		= cardInfo[13];	//客户编号 
			jobj_out.back_totuse_dl  	= cardInfo[14]; //累计用电量	0.01kwh
			jobj_out.back_remain_dl  	= cardInfo[15]; //剩余电量		0.01kwh
			jobj_out.back_totbuy_dl 	= cardInfo[16]; //累计购电量	0.01kwh
			jobj_out.back_thisbuy_dl 	= cardInfo[17]; //本次购电量	0.01kwh
			jobj_out.back_pay_num 		= cardInfo[18]; //购电次数
			jobj_out.back_overzero_dl	= cardInfo[19]; //过零电量  0.01kwh
			jobj_out.back_errin_num 	= cardInfo[20]; //非法插卡次数
			jobj_out.back_meterstate 	= cardInfo[21]; //电卡表状态字
			jobj_out.back_kvalue 		= cardInfo[22]; //脉冲常数
			jobj_out.back_alarm_val1 	= cardInfo[23]; //报警值1
			jobj_out.back_alarm_val2 	= cardInfo[24]; //报警值2			
			jobj_out.back_reback_date	= cardInfo[25]; //返写日期	YY-MM-DD
			jobj_out.back_reback_time	= cardInfo[26]; //返写时间         HH-MI-00
			jobj_out.back_pay_type 		= cardInfo[27]; //付费方式，    00-普通多功能，01-预付费，02-后付费
			jobj_out.back_feirate 		= cardInfo[28]; //倍率 变比
			break;

		case SDDef.YFF_METER_TYPE_NP :	//"KE_005"
			var idx = 1;
			jobj_out.areano				= cardInfo[idx++];	//区域号
			jobj_out.cardno				= cardInfo[idx++];	//卡号
			jobj_out.pay_money			= cardInfo[idx++];	//分
			jobj_out.pay_num			= cardInfo[idx++];	//购电次数
			jobj_out.oper_date			= cardInfo[idx++];	//日期
			jobj_out.oper_time			= cardInfo[idx++];	//时间
			jobj_out.meterno			= cardInfo[idx++];	//表号
			jobj_out.userno				= cardInfo[idx++];	//客户编号
			jobj_out.para_flag			= cardInfo[idx++];	//参数标志
			jobj_out.oper_type			= cardInfo[idx++];	//操作类型
			jobj_out.card_state			= cardInfo[idx++];	//锁卡标示文件(购电卡状态)
			
			if(jobj_out.card_state == "0") {
				jobj_out.card_state_desc = "正常";
			}
			else {
				jobj_out.card_state_desc = "灰锁";
			}
			
			//反写信息
			jobj_out.back_meterno		= cardInfo[idx++];	//反写表号
			jobj_out.back_cardno		= cardInfo[idx++];	//反写卡号
			jobj_out.back_randno1		= cardInfo[idx++];	//随机数1
			jobj_out.comno				= cardInfo[idx++];  //脱机交易序号
			jobj_out.remain_money		= cardInfo[idx++];	//钱包余额
			jobj_out.back_randno2		= cardInfo[idx++];  //随机数2
			jobj_out.comm_date			= cardInfo[idx++];  //交易日期
			jobj_out.comm_time			= cardInfo[idx++];  //交易时间
			jobj_out.card_remain_money  = cardInfo[idx++];  //卡号表内剩余金额
			break;
		}

		return jobj_out;
	},
	
	//读卡字符串转成json格式。保留j_out原有内容
	readExtString2Json : function (j_out){
		var jobj_out = {};
		jobj_out.errno = j_out.errno;
		jobj_out.strinfo = j_out.strinfo;
		jobj_out.errstr = j_out.errstr;

		var cardInfo = j_out.strinfo.split("|");
	//	var meterType = cardInfo[0];	//卡表类型
		
//		jobj_out.meter_type = meterType;	//卡表类型 
		
//		switch(meterType){
//		case SDDef.YFF_METER_TYPE_ZNK :	//"KE_001"
		
		jobj_out.consno  	= cardInfo[0];//客户编号
		jobj_out.card_type	= cardInfo[1];//1 开户 2 购电 3 补卡
		jobj_out.meter_type	= cardInfo[2];//KL_001, JJ_003	
		jobj_out.yfflb		= cardInfo[3];//预付费类别
		jobj_out.areacode	= cardInfo[4];//区域码
		jobj_out.meterno 	= cardInfo[5];//表号
		jobj_out.pay_money	= cardInfo[6];//上次缴费金额元
		jobj_out.pay_num	= cardInfo[7];//购电次数
		jobj_out.ct			= cardInfo[8];//CT
		jobj_out.pt			= cardInfo[9];//PT
		jobj_out.operman	= cardInfo[10];//操作员
		
		jobj_out.paydate	= cardInfo[11];//售电时间
		jobj_out.back_flag	= cardInfo[12];//返写标志
		jobj_out.back_totval= cardInfo[13];//累计购电值
		jobj_out.back_usevval= cardInfo[14];//累计用电值
		jobj_out.back_pay_num= cardInfo[15];//返写次数
		jobj_out.back_remain_money= cardInfo[16];//剩余金额/电量
		jobj_out.back_overd_money = cardInfo[17];//透支金额
		
		jobj_out.back_key_state		= cardInfo[18];//密钥状态
		jobj_out.back_key_updatetype= cardInfo[19];//更新方式
		jobj_out.back_key_ver		= cardInfo[21];//密钥版本
		jobj_out.back_errin_num		= cardInfo[23];//非法插卡次数
			
		jobj_out.fee1_rated_z	= cardInfo[52];//费率1总费率
		jobj_out.fee1_ratef_j	= cardInfo[52];//费率1尖费率
		jobj_out.fee1_ratef_f	= cardInfo[53];//费率1峰费率
		jobj_out.fee1_ratef_p	= cardInfo[54];//费率1平费率
		jobj_out.fee1_ratef_g	= cardInfo[55];//费率1谷费率
		jobj_out.fee2_rated_z	= cardInfo[69];//费率2总费率
		jobj_out.fee2_ratef_j	= cardInfo[70];//费率2尖费率
		jobj_out.fee2_ratef_f	= cardInfo[71];//费率2峰费率
		jobj_out.fee2_ratef_p	= cardInfo[72];//费率2平费率
		jobj_out.fee2_ratef_g	= cardInfo[73];//费率2谷费率
		
		return jobj_out;
	}
};