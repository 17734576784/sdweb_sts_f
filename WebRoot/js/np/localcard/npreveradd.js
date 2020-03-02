var rtnValue;
var WRITECARD_TYPE = "5";

$(document).ready(function(){
	initGrid();
	//$("#btnSearch").click(function(){selcons()});
	$("#btnRead").click(function(){read_card()});
	$("#cardinfo").click(function(){window.top.card_comm.cardinfo()});
	//$("#metinfo").click(function(){metinfo()});
	$("#btnRever").click(function(){if(check())wrtCard_cz();});
	$("#btnPrt").click(function(){printPayRec()});
	$("#btnSearch").focus();
	
	$("#pay_money"	).keyup(function(){ calcu()});
	$("#zb_money"	).keyup(function(){ calcu()});
	$("#othjs_money").keyup(function(){ calcu()});
	
	setDisabled(true);
});

function selcons(){//检索
	$("#btnRever").attr("disabled",true);	
	var js_tmp = doSearch("ks",ComntUseropDef.YFF_NPOPER_REVER,rtnValue);
	if(!js_tmp)	return;
	rtnValue = js_tmp;
	selcons1();
}

function calcu(){
	
	var zbje = $("#zb_money").val()		===	""	? 0	: $("#zb_money").val();
	var jsje = $("#othjs_money").val()	===	""	? 0	: $("#othjs_money").val();
	var jfje = $("#pay_money").val()	===	""	? 0	: $("#pay_money").val();
	var last_tt = $("#last_tt").val() 	=== "" 	? 0 : $("#last_tt").val();
	
	var zje = round((parseFloat(jfje) + parseFloat(jsje) + parseFloat(zbje)), def.point);
	if(!isNaN(zje)){
		$("#all_money").html(zje);
	}
}

function read_card(){//读卡
	$("#btnPrt").attr("disabled", true);
	loading.tip = "正在读卡,请稍后...";
	loading.loading();
	window.setTimeout("window.top.card_comm.readCardSearchNp(" + ComntUseropDef.YFF_NPOPER_REVER + ")", 10);
}

function setSearchJson2Html(js_tmp) {

	loading.loaded();
	if(!js_tmp)return;
//	if(!window.top.card_comm.checkInfo(js_tmp)) return;
	rtnValue = js_tmp;
	selcons1();
}

function selcons1() {
	setDisabled(false);
	setConsValue(rtnValue);

	getRecord(rtnValue.areaid,rtnValue.farmerid);		//读取所有购电记录
	lastPayInfo(rtnValue.areaid,rtnValue.farmerid);		//读取最近一次购电记录信息,并将金额显示到页面对应位置
	//mis.js中函数
	//var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno};
	//mis_query(param);
	$("#btnRever").attr("disabled", false);
}
	
function wrtCard_cz1(gsm){
	
	var area_id = $("#area_id").val(), farmer_id = $("#farmer_id").val();
	if(area_id === "" || farmer_id === ""){
		alert(sel_user_info);
		return;
	}
	var params = {};
	params.meter_type 		= WRITECARD_TYPE;
	params.areano			= $("#area_no").html();//区域号
	params.cardno			= $("#card_no").html();
	
	params.card_type  		= SDDef.NPCARD_OPTYPE_REVER;
	
	params.pay_money 		= $("#pay_money").val()===""?0:$("#pay_money").val();
	params.othjs_money 		= $("#othjs_money").val()===""?0:$("#othjs_money").val();
	params.zb_money 		= $("#zb_money").val()===""?0:$("#zb_money").val();
	
	var last_tt = parseFloat($("#last_tt").val());
	var this_tt = parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money);
	var tt = last_tt - this_tt;
	
	//params.all_money   		= round(tt, def.point);
	params.all_money   		    = this_tt;
	
	params.buynum			= parseInt(rtnValue.buy_times);
	params.paytype 		 = $("#pay_type").val();	

	params.area_id			= $("#area_id").val();
	params.farmer_id		= $("#farmer_id").val();
	
	params.lsremain			= 0;	//卡内剩余金额
	params.gsmFlag 			= gsm;
	wrtDB_cz(params);
	
}

function wrtCard_cz() {
	
	var pay_money 		= $("#pay_money").val()===""?0:$("#pay_money").val();
	var othjs_money 		= $("#othjs_money").val()===""?0:$("#othjs_money").val();
	var zb_money 		= $("#zb_money").val()===""?0:$("#zb_money").val();
	
	var last_tt = parseFloat($("#last_tt").val());
	var this_tt = parseFloat(pay_money) + parseFloat(zb_money) + parseFloat(othjs_money);
	var tt = last_tt - this_tt;
	if(tt < 0) {
		alert("实际总金额不能大于上次总金额！");
		return;
	}
	
	var params = {};
	
	params.pay_money 		= $("#pay_money").val()	  ===	""?0:$("#pay_money").val();
	params.othjs_money 		= $("#othjs_money").val() ===	""?0:$("#othjs_money").val();
	params.zb_money 		= $("#zb_money").val()	  ===	""?0:$("#zb_money").val();
	params.all_money	 	= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),3);
	
	loading.loading();
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
	
	window.setTimeout("wrtCard_cz1(" + o.gsm + ")", 10);
}

function wrtDB_cz(params){		//冲正
	params.oldopdate	= rever_info.date;  //查询上次缴费记录时取得值
	params.oldoptime 	= rever_info.time;
	params.op_result	= 100;
	var json_str   = jsonString.json2String(params);
	
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperNp!taskProc.action", 		//后台处理程序
			{
		        userData1		: params.area_id,
				farmerid 		: params.farmer_id,
				gsmflag 		: params.gsmFlag,
				npOperRever		: json_str,
				userData2 		: ComntUseropDef.YFF_NPOPER_REVER
			},
			function(data) {			    	//回传函数
				loading.loaded();
				if (data.result == "success") {
					$("#btnPrt").attr("disabled", false);
					getRecord(rtnValue.areaid,rtnValue.farmerid);
					var ret_json = eval("(" + data.npOperRever + ")");
					
//-----------------------------------------mis系统冲正尚未改动----------------------------------------
//					if(misUseflag == "true" && misOkflag == "true") {
//						//MIS冲正, 不能隔天冲正, 请手工在MIS中进行冲正操作...
//						if(_today == params.old_op_date && _today == parseInt(ret_json.op_date)){
//							//mis.js函数
//							var rev_para = {};
//							rev_para.rtu_id 	= params.rtu_id;
//							rev_para.mp_id   	= params.mp_id;
//							rev_para.op_type	= SDDef.YFF_OPTYPE_REVER;
//							rev_para.date		= ret_json.op_date;
//							rev_para.time		= ret_json.op_time;
//							rev_para.last_date 	= params.old_op_date;
//							rev_para.last_time 	= params.old_op_time;
//							rev_para.updateflag = 0;
//							rev_para.czjylsh 	= ret_json.wasteno;
//							rev_para.yjylsh 	= rever_info.last_wastno;
//							rev_para.yhzwrq 	= ret_json.op_date;
//							rev_para.pay_money 	= params.pay_money;
//							rev_para.busi_no 	= rtnValue.userno;
//							rev_para.yhbh = rtnValue.userno;
//						
//							mis_rever(rev_para);
//						}
//						else {
//							alert("MIS冲正, 不能隔天冲正, 请手工在MIS中进行冲正操作...");
//						}
//					}
//-----------------------------------------------------------------------------------------------------
					
					alert("冲正成功!");
					$("#btnRever").attr("disabled",true);
					if(data.dyOperRever == ""){
						alert("获取返回的操作信息失败。请到[补打发票]界面补打。")
						$("#btnPrt").attr("disabled",true);
					}else{
						$("#btnPrt").attr("disabled",false);
						window.top.WebPrint.setYffDataOperIdx2params(data.npOperRever,window.top.WebPrint.nodeIdx.npcard);//打印用的参数
					}
					setDisabled(true);
				}
				else {
					alert("向主站发送冲正命令失败" + (data.operErr ? data.operErr : ''));
				}
			}
		);
}

function CheckCard()
{	
	var json_out = {};
	json_out = window.top.card_comm.readcard();
	if(json_out == undefined || json_out ==""){
		alert("读卡错误!");
		return  false;				
	}
	
	var meterType = json_out.meter_type;//卡表类型

	var areaNo = json_out.areano;    //区域号
	var buynum = json_out.pay_num;	 //购电次数
	var cardNo = json_out.cardno;    //卡号

	if(meterType == window.top.SDDef.YFF_METER_TYPE_NP){
	
		var tmp = "0000000000000000"
		if (tmp.replace(/(^0*)/g, "") == cardNo.replace(/(^0*)/g, ""))	{
			alert("用户卡号为空，不能冲正！")
			return false;
		}
		else if (rtnValue.card_no.replace(/(^0*)/g, "") != cardNo.replace(/(^0*)/g, "")) {
			alert("用户卡号与系统不一致！")
			return false;
		}
		else if (rtnValue.buy_times != buynum ) {
			alert("用户卡购电次数与售电系统不一致,不能冲正！")
			return false;
		}
		else if	(rtnValue.area_code.replace(/(^0*)/g, "") != areaNo.replace(/(^0*)/g, "")){
			alert("用户所属区域号与系统不一致！");
			return false;
		}
	} else {
		alert("未知卡类型")
		return false;
	}
	return true;
}

//显示用户用电信息
function showYDInfo(){
	_showYDInfo(rtnValue.areaid, rtnValue.farmerid);
}

function check(){
	
	if(rever_info.date != _today && misUseflag == "true") {
		alert("不能冲正非今天的记录...");
		return false;
	}
	
	//如果MIS不通，禁止缴费
	if (misUseflag == "true" && (misOkflag == "false" || misOkflag == undefined)) {
		alert("MIS不能通讯,禁止开户...");
		return false;
	}
	var jfje = 0, zbje = 0, jsje = 0;
	zbje = $("#zb_money").val()==="" ? 0 : $("#zb_money").val();
	jfje = $("#pay_money").val()==="" ? 0 : $("#pay_money").val();
	jsje = $("#othjs_money").val()==="" ? 0 : $("#othjs_money").val();
	
	if(isNaN(zbje)){
		alert("请输入数字!");
		$("#zb_money").focus().select();
		return false;
	}
	
	if(isNaN(jfje)){
		alert("请输入数字!");
		$("#pay_money").focus().select();
		return false;
	}

	if(isNaN(jsje)){
		alert("请输入数字!");
		$("#othjs_money").focus().select();
		return false;
	}

	if(!isDbl("zb_money" ,"追补金额",-rtnValue.moneyLimit, rtnValue.moneyLimit)){
		return false;
	}
	
	if(!isDbl("othjs_money" ,"结算金额",-rtnValue.moneyLimit, rtnValue.moneyLimit)){
		return false;
	}
	
	if(!isDbl("pay_money" ,"缴费金额",0,rtnValue.moneyLimit)){
		return false;
	}
	
	if(!isDbl_Html("all_money","总金额",0,rtnValue.moneyLimit)){
		return false;
	}

	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
	}
	
//	if(($("#jfje").val() == "0" || $("#jfje").val() == "")){
//		return(confirm("客户未缴费,继续操作吗?"))
//}
//
	
	
//	if(("#last_je").val() - ("#pay_money").val() > ("#now_remain").html()){
//		alert("卡内剩余金额不足，不能冲正!");
//	}
	
	
	return true;
}

function printPayRec() {
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.npcard);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}

function setDisabled(mode){	//设置状态
	if(!mode){
		$("#othjs_money").val("");
		$("#pay_money").val("");
		$("#zb_money").val("");
		$("#all_money").html("");
	}
	
	$("#pay_money").attr("disabled",mode);
	$("#othjs_money").attr("disabled",mode);
	$("#zb_money").attr("disabled",mode);
	
}