var rtnValue;
var WRITECARD_TYPE = window.top.SDDef.YFF_CARDMTYPE_KE005;   //卡表类型

$(document).ready(function(){
	initGrid();
	$("#btnRead").click(function(){read_card()});
	$("#cardinfo").click(function(){window.top.card_comm.cardinfo()});
	
	$("#pay").click(function(){if(check())wrtCard_pay();});
	$("#prt").click(function(){printPayRec()}).attr("disabled",true);//打印
	$("#chgMbphone").click(function(){changeMbphone()}); // 更换手机
	
	$("#zb_money").keyup(function(){calcTotal();});
	$("#othjs_money").keyup(function(){calcTotal();});
	$("#pay_money").keyup(function(){calcTotal();});
	setDisabled(true);
	$("#btnSearch").focus();
});

function read_card(){		//读卡
	setDisabled(false);
	$("#prt").attr("disabled",true);
	loading.tip = "正在读卡,请稍后...";
	loading.loading();
	setTimeout("window.top.card_comm.readCardSearchNp(" + ComntUseropDef.YFF_NPOPER_PAY + ")", 10);
}

function setSearchJson2Html(js_tmp) {
	loading.loaded();
	if(!js_tmp)return;
	
	if(!window.top.card_comm.checkInfo(js_tmp)) return;
	rtnValue = js_tmp;	
	setcons1();
}

function setcons1() {
	setConsValue(rtnValue);

	getRecord(rtnValue.areaid,rtnValue.farmerid);
	
	if(rtnValue.card_state != "0") {
		alert("购电卡处于灰锁状态，请解扣后再购电！");
		$("#pay").attr("disabled", true);
		return;
	}
	
	$("#pay").attr("disabled",false);
}

function calcTotal(){//总金额
	
	var zbje = $("#zb_money").val()		===	""?0:$("#zb_money").val();
	var jsje = $("#othjs_money").val()	===	""?0:$("#othjs_money").val();
	var jfje = $("#pay_money").val()	===	""?0:$("#pay_money").val();
	
	if(isNaN(jfje) || isNaN(jsje) || isNaN(zbje)){
		return;
	}
	
	var zje = round(parseFloat(zbje) + parseFloat(jsje) + parseFloat(jfje),3);
	$("#all_money").html(zje);
}

function wrtCard_pay(){
	var params = {};
	params.pay_money 		= $("#pay_money").val()		===""?0:$("#pay_money").val();
	params.othjs_money 		= $("#othjs_money").val()	===""?0:$("#othjs_money").val();
	params.zb_money 		= $("#zb_money").val()		===""?0:$("#zb_money").val();
	params.all_money   		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money), def.point);
	
	loading.loading();
	
	modalDialog.height 	= 300;
	modalDialog.width 	= 280;
	modalDialog.url 	= def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param 	= {
		showGSM : true,
		key	: ["客户编号","客户名称", "缴费金额","追补金额","结算金额","总金额"],
		val	: [$("#userno").html(),$("#username").html(), params.pay_money,params.zb_money,params.othjs_money,params.all_money]		
	};
	
	var o = modalDialog.show();
	
	if(!o||!o.flag){
		loading.loaded();
		return;
	}
	
	window.setTimeout("wrtCard_pay1(" + o.gsm + ")", 10);
}

function wrtCard_pay1(gsm) {

	if(!CheckCard()){
		loading.loaded();
		return;
	}
	
	var area_id = $("#area_id").val(), farmer_id = $("#farmer_id").val();
	if(area_id === "" || farmer_id === ""){
		alert(sel_user_info);
		return;
	}
	
	var params = {};
	
	params.meter_type 		= WRITECARD_TYPE;
	params.areano			= $("#area_no").html();//区域号
	params.cardno			= $("#card_no").html();
	params.card_type		= SDDef.NPCARD_OPTYPE_BUY;//开户、缴费、补卡
	params.pay_money 		= $("#pay_money").val()===""?0:$("#pay_money").val();
	params.othjs_money 		= $("#othjs_money").val()===""?0:$("#othjs_money").val();
	params.zb_money 		= $("#zb_money").val()===""?0:$("#zb_money").val();
	params.all_money   		= round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money), def.point);
	params.buynum			= parseInt(rtnValue.buy_times) + 1;
	params.paytype 			= "0";

	params.area_id			= $("#area_id").val();
	params.farmer_id		= $("#farmer_id").val();
		
	params.lsremain			= $("#now_remain").html();		//还需要处理
	params.gsmFlag 			= gsm;
	
	$.post( def.basePath + "ajaxoper/actWebCard!getcardWrtInfo.action", 		//后台处理程序,后台反写内容尚未完善,还需补充!
		{
			result	: jsonString.json2String(params)
		},
		function(data) {			    	//回传函数
			var money = (params.all_money + parseFloat(params.lsremain))*100;
			if (window.top.card_comm.ReWriteCard(data.result, money)) {
				wrtDB_pay(params);
			}
			else {
				alert("写卡失败-电卡异常！\n");	
			}
			loading.loaded();
		}	
	);
}

function wrtDB_pay(params){		//缴费
	params.buynum = rtnValue.buy_times;	//卡与通讯的购电次数不一致
	
	loading.loading();
	var json_str = jsonString.json2String(params);

	$.post( def.basePath + "ajaxoper/actOperNp!taskProc.action", 		//后台处理程序
		{
			userData1		: params.area_id,
			farmerid		: params.farmer_id,
			gsmflag 		: params.gsmFlag,
			npOperPay 		: json_str,
			userData2 		: ComntUseropDef.YFF_NPOPER_PAY
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				
				$("#prt").attr("disabled",false);
				alert("缴费成功!");
				getRecord($("#area_id").val(), $("#farmer_id").val());
				setDisabled(true);
				
				$("#buy_times").val(1 + parseInt($("#buy_times").val()));
				
				window.top.WebPrint.setYffDataOperIdx2params(data.npOperPay,window.top.WebPrint.nodeIdx.npcard);//打印用的参数
			}
			else {
				alert("向主站发送缴费命令失败,请补写缴费记录!" + (data.operErr ? data.operErr : ''));
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

	var areaNo = json_out.areano;    //区域号
	var buynum = json_out.pay_num;	 //购电次数
	var cardNo = json_out.cardno;    //卡号

	if(meterType == window.top.SDDef.YFF_METER_TYPE_NP){
	
		var tmp = "0000000000000000"
		if (tmp.replace(/(^0*)/g, "") == cardNo.replace(/(^0*)/g, ""))	{
			alert("用户卡号为空，不能缴费！")
			return false;
		}
		else if (rtnValue.card_no.replace(/(^0*)/g, "") != cardNo.replace(/(^0*)/g, "")) {
			alert("用户卡号与系统不一致！")
			return false;
		}
		else if (rtnValue.buy_times != buynum ) {
			alert("用户卡购电次数与售电系统不一致,不能购电！")
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

function check() {
	
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
	
	if(!isDbl("pay_money" ,"缴费金额", 0,  rtnValue.moneyLimit)){
		return false;
	}

	if(!isDbl_Html("all_money" ,  "总金额"  , 0, rtnValue.moneyLimit, false)){//总金额应该在0~囤积值之间
		return false;
	}

	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
	}
	
//	if($("#zongje").html() > rtnValue.moneyLimit||$("#zongje").html() < 0){
//		alert("总金额应在0与" + rtnValue.moneyLimit + "之间");
//		return false;
//	}
	
	return true;
}

//显示用户用电信息
function showYDInfo(){
	_showYDInfo(rtnValue.areaid, rtnValue.farmerid);
}


function setDisabled(mode){
	if(!mode){
		//清空界面的信息,用于开户后再次查询,而无查询结果的情况
		setConsValue(rtnValue,true);
		mygrid.clearAll();
		$("#othjs_money").val("");
		$("#pay_money").val("");
		$("#zb_money").val("");
		$("#now_remain").html("");
		$("#buy_times").html("");
		$("#all_money").html("");
		//隐含域也要清空
		$("#area_id").val("");
		$("#farmer_id").val("");
		$("#pay_type").val("");
		
		//计算客户名称  结算客户分类
		$("#jsyhm").html("");
		$("#yhfl").html("");
	}
	$("#now_remain").attr("disabled",mode);
	$("#pay").attr("disabled",mode);
	$("#pay_money").attr("disabled",mode);
	$("#othjs_money").attr("disabled",mode);
	$("#zb_money").attr("disabled",mode);
	$("#chgMbphone").attr("disabled",mode);
}

function printPayRec(){//打印
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.npcard);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}

//更换手机
function changeMbphone(){
	modalDialog.height = 160;
	modalDialog.width  = 200;
	modalDialog.url = def.basePath + "jsp/dialog/chgMbPhone.jsp";
	
	var rtn = rtnValue;
	rtn.flag = "np";
	modalDialog.param = rtn;
	var params = modalDialog.show();
	if(!params) return;
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post(def.basePath + "ajaxoper/actOperNp!taskProc.action", 
			{
				userData1		: params.area_id,
				farmerid 		: params.farmer_id,
				gsmflag 		: params.gsmFlag,
				npOperResetDoc 	: json_str,
				userData2 		: ComntUseropDef.YFF_NPOPER_RESETDOC
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

