//缴费
var rtnValue = null;	//检索用户信息
var	mygrid   = null
var jsonFLFA = null;
var jsonGHDB = null;
var jsonZDJBF= null;//终端基本费
var WRITECARD_TYPE 	= window.top.SDDef.YFF_CARDMTYPE_KE003;	//卡表类型  1-智能表09版     3-6013 6-智能表13版
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	 $.post(def.basePath + "ajaxdyjc/actChgRate!getRateList.action",{},function(data){
		if(data.result != ""){
			jsonFLFA = eval('(' + data.result + ')');
			for (var i = 0; i < jsonFLFA.rows.length; i++) {
                 if ((jsonFLFA.rows[i].feeType != SDDef.YFF_FEETYPE_DFL) &&(jsonFLFA.rows[i].feeType != SDDef.YFF_FEETYPE_FFL))
                	 continue;	//只要单费率
				$("#flfa").append("<option value=" + jsonFLFA.rows[i].value + ">" + jsonFLFA.rows[i].text + "</option>");
			}
			changeFLFA();
		}
	});
	
	initGrid();
	$("#btnReadCard").focus();
	
	$("#btnReadCard").click(function(){readcard()});//读卡检索
	$("#cardInfo").click(function(){window.top.card_comm.cardinfo()});//卡内信息
	//$("#rtuInfo").click(function(){rtuInfo()});//终端信息
    $("#btnPay").click(function(){if(check())wrtCard_pay();});
	$("#btnPrt").click(function(){showPrint()});//打印
	$("#chgMbphone").click(function(){changeMbphone()}); // 更换手机
	
	$("#zbje").keyup(function(){calcu()});
	$("#jsje").keyup(function(){calcu()});
	$("#jfje").keyup(function(){calcu()});
	//20131130 
	$("#flfa").change(function(){changeFLFA()});	//费率方案 	
	setDisabled(true);
	getChfDate();
	
});

function readcard(){
	setDisabled(false);	//清空记录	
	loading.loading();
	window.setTimeout('window.top.card_comm.readCardSearchGy('+ComntUseropDef.YFF_GYOPER_PAY+')', 10);
	
}
//20131130
//费率方案 下拉框 onchange
function changeFLFA(){
	var val = $("#flfa").val();
	for (var i = 0; i < jsonFLFA.rows.length; i++) {
		if (jsonFLFA.rows[i].value == val) {
			$("#fams_n").html(jsonFLFA.rows[i].desc);	
			return;
		}		
	}	
}
function saveFLFA(){//保存(费率方案) 
	var params = {};
	params.rtu_id = $("#rtu_id").val();
	params.zjgid  = $("#zjg_id").val();	
	var ghdb 	  = $("#ghdb").val();

//卡式预付费一般为一块电表， 且 6103 可能对应多个电表 但是费率一样 故 
	params.chgid0 = $("#flfa").val();
	params.chgid1 = $("#flfa").val();
	params.chgid2 = $("#flfa").val();
	
//如果非要一次更换一块 则 要从jsonGHDB.rows[i].feeproj 取值 其他从界面

	var qhsj = $("#qhsj").val();	
	params.chg_date = qhsj.substring(0,4) 	+ qhsj.substring(5,7)	+ qhsj.substring(8,10);
	params.chg_time = qhsj.substring(12,14) + qhsj.substring(15,17) + "00";
	//调用获取表底的函数
	getBDPara(params)
	//loading.loading()
 	var json_str=jsonString.json2String(params);
	$.post(def.basePath + "ajaxoper/actOperGy!taskProc.action",
		{
	        gsm_flag  	     : '0',    //是否发送短信
	        userData1	     : params.rtu_id,
	        userData2	     : ComntUseropDef.YFF_GYOPER_CHANGERATE,//高压操作-换电价
	        zjgid	 	     : params.zjgid,
	        gyOperChangeRate : json_str
		},
		function(data){
			if(data.result == "success"){
				var colors = "<b style='color:#800000'>";
				var colore = "</b>";
				$("#feeproj_id").val($("#flfa").val());
				//rtnValue.feeproj_id = $("#flfa").val();
				$("#feeproj_desc").html(colors+$("#flfa").find("option:selected").text()+colore);
				$("#feeproj_detail").html(colors+$("#fams_n").html()+colore);
				alert("更改费率方案成功");
				loading.loaded();
				
			}else{
				alert("更改费率方案失败" + (data.operErr ? data.operErr : ''));
				loading.loaded();
			}
			
		}	
	);
	}

//保存--结束
function getBDPara(params){//传入参数赋值。
	params.mp_id0 = 0;
	params.mp_id1 = 0;
	params.mp_id2 = 0;

	if (jsonGHDB.rows.length > 0) {
		params.mp_id0 = jsonGHDB.rows[0].value;
	}

	if (jsonGHDB.rows.length > 1) {
		params.mp_id1 = jsonGHDB.rows[1].value;
	}

	if (jsonGHDB.rows.length > 2) {
		params.mp_id2 = jsonGHDB.rows[2].value;
	}

	params.date		= '0';
	params.time		= '0';
	params.bd_zy0	= '0';
	params.bd_zyj0	= '0';
	params.bd_zyf0	= '0';
	params.bd_zyp0	= '0';
	params.bd_zyg0	= '0';
	params.bd_fy0	= '0';
	params.bd_fyj0	= '0';
	params.bd_fyf0	= '0';
	params.bd_fyp0	= '0';
	params.bd_fyg0	= '0';
	
	params.bd_zy1	= '0';
	params.bd_zyj1	= '0';
	params.bd_zyf1	= '0';
	params.bd_zyp1	= '0';
	params.bd_zyg1	= '0';
	params.bd_fy1	= '0';
	params.bd_fyj1	= '0';
	params.bd_fyf1	= '0';
	params.bd_fyp1	= '0';
	params.bd_fyg1	= '0';
	                  
	params.bd_zy2	= '0';
	params.bd_zyj2	= '0';
	params.bd_zyf2	= '0';
	params.bd_zyp2	= '0';
	params.bd_zyg2	= '0';
	params.bd_fy2	= '0';
	params.bd_fyj2	= '0';
	params.bd_fyf2	= '0';
	params.bd_fyp2	= '0';
	params.bd_fyg2	= '0';
	
	params.bd_zw0 	= '0';
	params.bd_fw0 	= '0';
	params.bd_zw1 	= '0';
	params.bd_fw1 	= '0';
	params.bd_zw2 	= '0';
	params.bd_fw2 	= '0';
}

//'更换电表'的init()方法:需要传送rtu_id
function initChgMeter(){
	$("#ghdb option").remove();
	var param = '{rtu_id:"' + $("#rtu_id").val() + '",zjg_id:"'+$("#zjg_id").val()+'"}';	
	$.post(def.basePath + "ajaxspec/actConsPara!GetChgRateInfo.action",{result:param},function(data){
		if (data.result != ""){
			jsonGHDB = eval('(' + data.result + ')');
			for (var i = 0; i < jsonGHDB.rows.length; i++) {
				$("#ghdb").append("<option value=" + jsonGHDB.rows[i].value + ">" + jsonGHDB.rows[i].text + "</option>");
				//保存option  value值
			}
			changeGHDB();
		}
	});
	$("#ghdb").attr("disabled",true);	
}

function changeGHDB(){	//更换电表 onchange:  更新新费率方案的内容为所选电表的费率方案。
	var flfa;
	var ghdb = $("#ghdb").val();

	for (var i = 0; i < jsonGHDB.rows.length; i++) {
		if (ghdb == jsonGHDB.rows[i].value) {
			flfa = jsonGHDB.rows[i].feeproj;
			break;
		}
	}
	if (i < jsonGHDB.rows.length) {
		$("#flfa").val(flfa);
		changeFLFA();
	}	
}

//end

function setSearchJson2Html(js_tmp){	//调用readCardSearch后，readcard中异步执行完成后，执行此函数。
	loading.loaded();
	if(!js_tmp)return;
	if(!window.top.card_comm.checkInfo(js_tmp)) return;
		
	rtnValue = js_tmp;
	//20131202新加验证卡类型	
	var reg = /\[(.*?)\]/;
    var cardmeter_type = rtnValue.cardmeter_type.match(reg);	
	if(cardmeter_type[1] < 6){
		alert('此用户表规约类型为2009版，请到"更改费率2009版"界面进行操作');
		document.location.reload();
		return;
	};
	
	setConsValue(rtnValue);
	setCardExt(rtnValue);

	getAlarmValue(rtnValue.yffalarm_id);	//获取报警方式
	getMoneyLimit(rtnValue.feeproj_id);		//囤积限值

	getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);							//缴费记录	

	//mis.js中函数   miss查询函数
	var param = {rtu_id : rtnValue.rtu_id, zjg_id : rtnValue.zjg_id, busi_no : rtnValue.userno};
	mis_query(param,gloQueryResult);

	$("#btnPay").attr("disabled", false);	
	initFLFA();
	initChgMeter();
}
//20131217新增初始化费率描述
function initFLFA(){
	var val=$("#feeproj_id").val();
    $("#flfa").val(val);
    for (var i = 0; i < jsonFLFA.rows.length; i++) {
		if (jsonFLFA.rows[i].value == val) {
			$("#fams_n").html(jsonFLFA.rows[i].desc);	
			return;
		}		
	}	
}

function wrtCard_pay(){//缴费
	var params = {};	
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money	 	= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),3);
	
	loading.loading()
	
	modalDialog.height 		= 300;
	modalDialog.width 		= 300;
	modalDialog.url 		= def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param 		= {
		showGSM : true,
		key	: ["客户编号","客户名称", "缴费金额","追补金额","结算金额","总金额"],
		val	: [$("#userno").html(),$("#username").html(), params.pay_money,params.zb_money,params.othjs_money,params.all_money]		
	};
	
	var o = modalDialog.show();
	if(!o||!o.flag){
		loading.loaded();
		return;
	}
	
	params.gsmFlag = o.gsm;//params 最后一个参数是否发送短信  赋值  进行action请求
	window.setTimeout("wrtCard_pay1(" + o.gsm + ")", 10);

}

function wrtCard_pay1(gsm){//缴费
	var rtu_id = $("#rtu_id").val(), zjg_id = $("#zjg_id").val();
	if(rtu_id === "" || zjg_id === ""){
		alert(sel_user_info);
		return;
	}
	
	var params = {};

	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.myffalarmid 		= $("#yffalarm_id").val();
	params.paytype 			= $("#pay_type").val();
	params.buynum 			= Number($("#buy_times").val()) + 1;

	params.alarm_val1		= $("#yffalarm_alarm1").val();
	params.alarm_val2		= $("#yffalarm_alarm2").val();
	
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money	 	= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),3);
	
	params.date = 0;
	params.time = 0;
//	params.buy_dl 			= $("#buy_dl").html()==="" ? 0:$("#buy_dl").html();
//	params.pay_bmc 			= $("#pay_bmc").html()===""? 0:$("#pay_bmc").html();
	
	params.gsmFlag = gsm;//params 最后一个参数是否发送短信  赋值  进行action请求
	
	params.pt 			= rtnValue.pt_ratio;
	params.ct 			= rtnValue.ct_ratio;
	params.meter_type 	= WRITECARD_TYPE;
	params.card_type  	= SDDef.CARD_OPTYPE_BUY;//开户、缴费、补卡

	params.feeproj_id 	= $("#flfa").val();
	params.limit_dl 	= money_limit;

	params.meterno  	= rtnValue.meter_id;
	params.cardno 		= $("#userno").html();
	params.writecard_no	= $("#writecard_no").html();
	params.pay_bmc 		= $("#pay_bmc").html();	
	params.buy_dl 		= $("#buy_dl").html();	
	
	var qhsj = $("#qhsj").val();
	if(qhsj!=""){
		params.chg_date = qhsj.substring(2,4)   + qhsj.substring(5,7)   + qhsj.substring(8,10);
	    params.chg_time  = qhsj.substring(12,14) + qhsj.substring(15,17) +"00"
	}else{
		params.chg_date = "20990102";
		params.chg_time= "112300";	
	}
	
	params.operType  = ComntUseropDef.YFF_GYOPER_CHANGERATE;	//操作类型决定更新标识

	if (CheckCard(params) == false){
		loading.loaded();
		return;
	}

	$.post( def.basePath + "ajaxoper/actWebCard!getcardWrtInfo.action", 		//后台处理程序
		{
			result	: jsonString.json2String(params)
		},
		function(data) {			    	//回传函数
			var retStr 	 = data.result.toString();
			var ret_json = window.top.card_comm.writecard(retStr);

			if(ret_json.errno == 0){
				$("#btnPay").attr("disabled", true);
				alert("写卡成功!");
				cookie.set("new_fsChgTime", qhsj);
				wrtDB_pay(params);
			}else{
				loading.loaded();
				alert("写卡失败！\n" + ret_json.errsr);
			}	
		}
	);
}

function wrtDB_pay(params){
	params.shutdown_bd 		= 0;
	params.buynum = params.buynum - 1;
	params.type = 'localcard';//本地卡式
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
		{
			userData1		: params.rtu_id,
			zjgid 			: params.zjg_id,
			gsmflag 		: params.gsmFlag,
			gyOperPay 		: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_PAY
		},
		function(data) {			    	//回传函数
			//loading.loaded();
			if (data.result == "success") {		
				
				getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
				$("#buy_times").val(parseInt($("#buy_times").val())+1);
//				IsDisabled();
				
				//向MIS系统缴费
				if(parseFloat(params.pay_money) > 0 && gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
					var ret_json = eval("(" + data.gyOperPay + ")");
					var mis_para 	= {};

					mis_para.rtu_id = ret_json.rtu_id;           
					mis_para.zjg_id = params.zjg_id;             
					mis_para.op_type= SDDef.YFF_OPTYPE_PAY;   
					mis_para.date 	= ret_json.op_date;          
					mis_para.time 	= ret_json.op_time;          
					mis_para.updateflag = 0;                     
					mis_para.jylsh 	= ret_json.wasteno;          
					mis_para.yhbh 	= rtnValue.userno;           
					mis_para.jfje 	= params.pay_money;          
					mis_para.yhzwrq = ret_json.op_date;          
					mis_para.jfbs 	= gloQueryResult.misJezbs; 
					
					var all_pay = 0.0;
					
					if(provinceMisFlag == "HB"){
						for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
							eval("mis_para.yhbh" + i + "=gloQueryResult.misDetailsvect["+i+"].yhbh");
							eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");
						}                
					}
					else if(provinceMisFlag == "HN"){
						mis_para.batch_no	= gloQueryResult.misBatchNo;	
						mis_para.hsdwbh		= gloQueryResult.misHsdwbh;
						for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
							eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");

							eval("mis_para.dfny" + i + "=gloQueryResult.misDetailsvect["+i+"].dfny");
							eval("mis_para.dfje" + i + "=gloQueryResult.misDetailsvect["+i+"].dfje");
							eval("mis_para.wyjje" + i + "=gloQueryResult.misDetailsvect["+i+"].wyjje");
							var bcssje= Math.min(parseFloat(gloQueryResult.misDetailsvect[i].dfje) + parseFloat(gloQueryResult.misDetailsvect[i].wyjje), parseFloat(mis_para.jfje) - all_pay);
							all_pay += bcssje;
							eval("mis_para.bcssje" + i + "=" + bcssje);
						}
					}
					else if (provinceMisFlag == "GS"){
						mis_para.jfbs 	= gloQueryResult.misJezbs;
						mis_para.dzpc   = gloQueryResult.misDzpc;
						for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
							eval("mis_para.yhbh" + i + "=gloQueryResult.misDetailsvect["+i+"].yhbh");
							eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");

							eval("mis_para.jfje" + i + "=gloQueryResult.misDetailsvect["+i+"].yjje");
							eval("mis_para.dfje" + i + "=gloQueryResult.misDetailsvect["+i+"].dfje");
							eval("mis_para.wyjje" + i + "=gloQueryResult.misDetailsvect["+i+"].wyjje");
							
							eval("mis_para.sctw" + i + "=gloQueryResult.misDetailsvect["+i+"].sctw");
							eval("mis_para.bctw" + i + "=gloQueryResult.misDetailsvect["+i+"].bctw");
						}
					}
					mis_para.vlength = gloQueryResult.misDetailsvect.length;
					
					mis_pay(mis_para);
				}
				alert("缴费成功!");	
				saveFLFA();						
				
				if(data.gyOperPay == ""){
					alert("获取返回的操作信息失败。请到[补打发票]界面补打。")
					$("#btnPrt").attr("disabled",true);
				}else{
					$("#btnPrt").attr("disabled",false);
					window.top.WebPrint.setYffDataOperIdx2params(data.gyOperPay);//打印用的参数
				}
			}
			else {
				alert("向主站发送命令失败" + (data.operErr ? data.operErr : ''));
			}
		}
	);
}

function calcu(){
	if(!rtnValue)return;

	var jfje = 0.0, zbje = 0.0, jsje = 0.0, zje = 0.0;
	zbje = $("#zbje").val() === "" ? 0 : $("#zbje").val();
	jfje = $("#jfje").val() === "" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val() === "" ? 0 : $("#jsje").val();
	
	zje = round(parseFloat(zbje) + parseFloat(jfje) + parseFloat(jsje),def.point);
	
	if(!isNaN(zje)){
		$("#zje").html(zje);
		var ke003f = (WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE003);	

		if (ke003f) {
			var dj 	= $("#feeproj_reteval").val()===""?1:$("#feeproj_reteval").val();
			var blv = $("#blv").html()===""?1:$("#blv").html();
			var zbd = 0;
			var alarm_val1 = (rtnValue.yffalarm_alarm1 == "" || rtnValue.yffalarm_alarm1 == undefined ) ? 30 : rtnValue.yffalarm_alarm1;
			var alarm_val2 = (rtnValue.yffalarm_alarm2 == "" || rtnValue.yffalarm_alarm2 == undefined ) ? 5 : rtnValue.yffalarm_alarm2;

			var ret_val = cardalarm_calcu(zje, dj, blv, zbd, alarm_val1, alarm_val2, rtnValue.type, zbd);

			$("#buy_dl").html(ret_val.buy_dl);
			$("#pay_bmc").html(ret_val.pay_bmc);
			//$("#shutdown_bd").html(ret_val.shutdown_bd);
			$("#yffalarm_alarm1").val(ret_val.alarm_code1);
			$("#yffalarm_alarm2").val(ret_val.alarm_code2);
		}
		else 
		{
			setAlarmValue(rtnValue.yffalarm_alarm1, rtnValue.yffalarm_alarm2, zje);
		}
	}else{ 
		return;
	}
}	

function check(){
	//20131211 新增 提示用户新切换时间不能为空
	var qhsj = $("#qhsj").val();
	if(qhsj==""){		
		alert("请输入新分时费率切换时间");
		return;
		
	}
	if ($("#feeproj_id").val() == $("#flfa").val())
	{
		alert("新旧费率相同,不许缴费！");	
		return;
	}
	//20131211新增判断卡是否满足缴费条件，当不满足条件时，不让改费率
	if(!CheckCard()){		
		return;
	}
	//如果MIS不通，禁止缴费
	if (gloQueryResult.misUseflag == "true" && (gloQueryResult.misOkflag == "false" || gloQueryResult.misOkflag == undefined)) {
		alert("MIS不能通讯,禁止缴费...");
		return false;
	}
	
	if($("#btnPay").attr("disabled"))  return false;

	var jfje = 0, zbje = 0, jsje = 0;
	zbje = $("#zbje").val()==="" ? 0 : $("#zbje").val();
	jfje = $("#jfje").val()==="" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val()==="" ? 0 : $("#jsje").val();

	if(isNaN(zbje)){
		alert("请输入数字!");
		$("#zbje").focus().select();
		return false;
	}
	
	if(isNaN(jfje)){
		alert("请输入数字!");
		$("#jfje").focus().select();
		return false;
	}

	if(isNaN(jsje)){
		alert("请输入数字!");
		$("#jsje").focus().select();
		return false;
	}

	if(!isDbl("zbje" , "追补金额" ,-money_limit,money_limit, false)){//追补金额应该在0~囤积值之间
		return false;
	}

	if(!isDbl("jsje",  "结算金额" ,-money_limit, money_limit, false)){
		return false;
	}

	if(!isDbl("jfje",  "缴费金额" ,0, money_limit, false)){
		return false;
	}
	//20130402
	var zje = $("#zje").html();
	if(!isDbl_Html("zje" ,  "总金额"  , 0, money_limit, false)){//总金额应该在0~囤积值之间
		return false;
	}

	if(parseFloat(zje) == 0){
		if(!confirm("用户缴费总金额为0,要继续缴费吗?"))return false;
	}
	
	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
	}
	
	if(jfje == 0 || jfje == ""){	//总金额>0但是 缴费金额为0或为""(空)
		return(confirm("用户未交费,是否继续"));
	}
	
	return true;
}

function showPrint(){//打印发票
	var filename = null; 
	
	if (WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE003){
		filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.gycard3);
		window.top.WebPrint.prt_params.page_type = window.top.WebPrint.nodeIdx.gycard3;
	}else{
		filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.gycard1);
		window.top.WebPrint.prt_params.page_type = window.top.WebPrint.nodeIdx.gycard1;
	}
	if(filename == undefined || filename == null) return;
	window.top.WebPrint.prt_params.file_name = filename;
//	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.gyks[WRITECARD_TYPE];
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintGy();
}

function setCardExt(rtnValue)
{
	$("#buy_times").html($("#buy_times").val());
	
	var tmp_val = rtnValue.cardmeter_type.split("]");
	WRITECARD_TYPE = tmp_val[0].split("[")[1];
	
//调整 20130402	
//	WRITECARD_TYPE = tmp_val[0].substring(1);
	$("#cardmeter_type").html(tmp_val[1].split('"')[0]);
	$("#writecard_no").html(rtnValue.writecard_no);
	$("#meter_id").html(rtnValue.meter_id);

	var ke003f = (WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE003);

	if (ke003f) {
		$("#gdl").show();
		$("#gdldesc").show();
		$("#pay_bmc").show();
		$("#pay_bmcdesc").show();
		$("#alarm1").html("报警表码差1：");
		$("#alarm2").html("报警表码差2：");
	}
	else {
		$("#gdl").hide();
		$("#gdldesc").hide();
		$("#pay_bmc").hide();
		$("#pay_bmcdesc").hide();
		$("#alarm1").html("报警金额1(元)：");
		$("#alarm2").html("报警金额2(元)：");
	}
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
}

function setDisabled(mode){
	//清空原有数据: 最后要提出个单独函数	
	if(!mode){
		$("#zbje").val("");
		$("#jfje").val("");
		$("#jsje").val("");
		$("#zje").html("");
		$("#yffalarm_alarm1").val("");
		$("#yffalarm_alarm2").val("");
		$("#td_bdinf").html("");
		$("#buy_times").html("");
		$("#buy_dl").html("");
		$("#pay_bmc").html("");
		
	//	rtnBD = null;
	}

	$("#ghdb").attr("disabled",mode);
	$("#gglx").attr("disabled",mode);
	$("#qhsj").attr("disabled",mode);
	$("#flfa").attr("disabled",mode);
	$("#zbje").attr("disabled",mode);
	$("#jfje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#yffalarm_alarm1").attr("disabled",mode);
	$("#yffalarm_alarm2").attr("disabled",mode);
	$("#chgMbphone").attr("disabled",mode);
    
//	$("#btnImportBD").attr("disabled",mode);
	//$("#cardInfo").attr("disabled",mode);
}

//判断写卡  数组太脆弱， 调用后台action 改进
function CheckCard(writeparams)
{
	var json_out = {};
	json_out = window.top.card_comm.readcard();
	if(json_out == undefined || json_out ==""){
		alert("读卡错误!");
		return  false;				
	}
	
	var meterType 	= json_out.meter_type;	//卡表类型	
	var meterNo 	= json_out.meterno; 	//表号
	var consNo  	= json_out.consno;		//户号
	var buyTime		= json_out.back_pay_num;//返写的购电次数	
	
	//20131127新增判断
	if((WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE001) && (meterType != window.top.SDDef.YFF_METER_TYPE_ZNK)){
		alert("用户电表为2009版规约，请使用09版购电卡!");
		return false;
	}
	
	if((WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE006) && (meterType != window.top.SDDef.YFF_METER_TYPE_ZNK2)){
		alert("用户电表为2013版规约，请使用13版购电卡!");
		return false;
	}
	//end
	
	//不太严谨 		需要调整
	if (meterType == window.top.SDDef.YFF_METER_TYPE_6103)	{
		var tmp = "00000000000000000000"
		if (tmp == consNo)	{
			alert("用户户号为空，不能缴费！")
			return false;
		}
		else if ((parseInt(buyTime) <=0) || (parseInt(buyTime) >=1000000)) {
			alert("卡内无反写信息，不能购电！")
			return false;
		} 
		else if (writeparams.writecard_no.replace(/(^0*)/g, "") != consNo.replace(/(^0*)/g, "")) {
			alert("用户卡户号与系统不一致！")
			return false;
		}
		else if (writeparams.buynum != parseInt(buyTime) +1) {
			alert("用户卡购电次数与售电系统不一致,不能购电！")
			return false;
		} 
	}
	else {
		var tmp = "000000000000"
		if (tmp == meterNo)	{
			alert("用户表号为空，不能缴费！")
			return false;
		}
		else if (tmp == consNo)	{
			alert("用户户号为空，不能缴费！")
			return false;
		}
		else if (parseInt(buyTime) <=0) {
			alert("卡内无反写信息，不能购电！")
			return false;
		} 
		else if (writeparams.cardno.replace(/(^0*)/g, "") != consNo.replace(/(^0*)/g, "")) {
			alert("用户卡户号与系统不一致！")
			return false;
		}
		else if (writeparams.meterno.replace(/(^0*)/g, "") != meterNo.replace(/(^0*)/g, "")) {
			alert("用户卡表号与系统不一致， 请到换表流程！")
			return false;
		}
		else if (writeparams.buynum != parseInt(buyTime) +1) {
			alert("用户卡购电次数与售电系统不一致,不能购电！")
			return false;
		} 
	}
	return true;
}

//更换手机
function changeMbphone(){
	modalDialog.height = 160;
	modalDialog.width  = 200;
	modalDialog.url = def.basePath + "jsp/dialog/chgMbPhone.jsp";
	
	var rtn = rtnValue;
	rtn.flag = 'gy';
	modalDialog.param = rtn;
	var params = modalDialog.show();
	if(!params) return;
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post(def.basePath + "ajaxoper/actOperGy!taskProc.action", 
			{
				userData1		: params.rtu_id,
				zjgid 			: params.zjg_id,
				gsmflag 		: params.gsmFlag,
				gyOperResetDoc 	: json_str,
				userData2 		: ComntUseropDef.YFF_GYOPER_RESETDOC
			},
			function(data){
				loading.loaded();
				if (data.result == "success") {
					$("#tel").html(params.filed1);
				}
				else {
					alert("向主站发送更改手机命令失败" + (data.operErr ? data.operErr : ''));
				}
			}
	);
}


function CheckCard() {
	var json_out = {};
	json_out = window.top.card_comm.readcard();
	if(json_out == undefined || json_out ==""){
		alert("读卡错误!");
		return  false;				
	}

	var meterType = json_out.meter_type;//卡表类型
	var meterNo,consNo,buyTime;			//表号，户号,购电次数
	if(meterType == window.top.SDDef.YFF_METER_TYPE_ZNK || meterType == window.top.SDDef.YFF_METER_TYPE_ZNK2){
		
		if((WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE001) && (meterType != window.top.SDDef.YFF_METER_TYPE_ZNK)){
			alert("用户电表为2009版规约，请使用09版购电卡!");
			return false;
		}
		
		if((WRITECARD_TYPE == window.top.SDDef.YFF_CARDMTYPE_KE006) && (meterType != window.top.SDDef.YFF_METER_TYPE_ZNK2)){
			alert("用户电表为2013版规约，请使用13版购电卡!");
			return false;
		}
		
		meterNo 	= json_out.meterno; 		//表号
		consNo  	= json_out.consno;			//户号
	 	buyTime		= json_out.back_pay_num;	//返写购电次数

		var tmp = "000000000000"
		if (tmp == meterNo)	{
			alert("用户表号为空，不能缴费！")
			return false;
		}
		else if (tmp == consNo)	{
			alert("用户户号为空，不能缴费！")
			return false;
		}
		else if (parseInt(buyTime) <=0) {
			alert("卡内无反写信息，不能购电！")
			return false;
		}
//		else if (rtnValue.userno.replace(/(^0*)/g, "") != consNo.replace(/(^0*)/g, "")) {
		else if (rtnValue.writecard_no.replace(/(^0*)/g, "") != consNo.replace(/(^0*)/g, "")) {
			alert("用户卡户号与系统查询结果不一致！")
			return false;
		}
		else if (rtnValue.esamno.replace(/(^0*)/g, "") != meterNo.replace(/(^0*)/g, "")) {
			alert("用户卡表号与系统查询结果不一致， 请到换表流程！")
			return false;
		}
		else if (rtnValue.buy_times != buyTime ) {
			alert("用户卡购电次数与售电系统不一致,不能购电！")
			return false;
		}
	} else {
		alert("未知卡类型")
		return false;
	}
	return true;
}
//初始化更改日期
function getChfDate(){
	initChgDate("qhsj","fsChgTime");
	
}

function initChgDate(id,cookieName){
	var new_chgTime = cookie.get(cookieName);//2013年01月01日 11时10分
	if(new_chgTime == undefined || new_chgTime == ""){
		var datestr = getFormatDate();
		$('#'+ id).val(datestr);
	}else{
		$('#'+ id).val(new_chgTime);
	}
}

function getFormatDate(){
	dateFormat.date = dateFormat.getToday("yMd");
	var datestr 	= dateFormat.formatToYMD(0);
	dateFormat.date = dateFormat.getToday("H")+"00";
	datestr 		= datestr + " " + dateFormat.formatToHM(0);
	return datestr;
}

