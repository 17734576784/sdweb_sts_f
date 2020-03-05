var rtnValue 	    = null;
//var WRITECARD_TYPE  = window.top.SDDef.YFF_CARDMTYPE_KE001;
var lastpayRcd 	    = null;//上次购电记录

//var history_Records = null;
var mygrid          = null;
var old_op_date     = null;
var old_op_time     = null;
//var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	initGrid();
	$("#btnSearch").click(function(){selcons()});
	$("#cardinfo").click(function(){window.top.card_comm.cardinfoBengal()});	
	$("#repair").click(function(){
		repairOper();
	});

	$("#jfje").keyup(function(){ calcu()});
	$("#zbje").keyup(function(){ calcu()});
	$("#btnSearch").focus();
	$("#repair").attr("disabled",true);
	
//$("#metinfo  ").click(function(){metinfo()});	
//setDisabled(true);
//$("#btnPrt").click(function(){printPayRec()});
//$("#repair_type").change(function()	{ change_repairtype(this.value)});	
///	$("#repair_type").attr("disabled",true);
	
	
});

function selcons(){//检索   
	var rtnValue1  = doSearch("ks",ComntUseropDef.YFF_DYOPER_REPAIR,rtnValue);
	if(!rtnValue1)	return;
	
	rtnValue = rtnValue1;
	fillInfoValue(rtnValue);
	
	$("#btnPrt").attr("disabled",true);
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
	
	//只在此处加参数SDDef.YFF_OPTYPE_REPAIR，其他地方不用
	getRecord(obj.rtu_id, obj.mp_id, "", 10, SDDef.YFF_OPTYPE_REPAIR);
}

//补卡操作 
function repairOper(){
	loading.loading();
	var params = {};
	params.meterNo = $("#commaddr").html();
	params.customerID = $("#userno").html();
	params.utilityID = $("#utilityid").html();
	params.seqNo = $("#seqNo").val();	
	params.keyNo = $("#keyNo").val();
	params.all_money = $("#zje").html();		//总金额
	    
	var operType = SDDef.YFF_OPTYPE_REPAIR;//操作类型：补卡
	
	//发向后台
	$.post(def.basePath + "ajaxoper/actOperDyBengal!operDY.action", 		//后台处理程序
		{
			params : jsonString.json2String(params),
			operType : operType
		},
		//将字符串写卡
		function(data){
			var retStr = data.result;
			var errState = data.errState;				
			//如果返回有错误，则显示错误内容
			if(errState == 1){
				loading.loaded();
			}
			else{
				writeCard(retStr);
			}
		}
	);	
}

//写卡
function writeCard(retStr){
	var ret_json = window.top.card_comm.writeCardBengal(retStr);	//
	//写卡成功，数据存库
	if(ret_json.errno  == 0){
		saveDataToDB();
	}
	else{
		alert("Échec de la carte d'écriture！\n" + ret_json.errsr);
		loading.loaded();
	}
}

//数据库中存储缴费记录,更改用户状态
function saveDataToDB(){
	var param = {};
	param.rtuId = $("#rtu_id").val();
	param.mpId = $("#mp_id").val();
	param.cus_state = SDDef.YFF_CUSSTATE_NORMAL;//客户状态
	
	param.op_type = SDDef.YFF_OPTYPE_REPAIR;	//操作类型--补卡
	
	param.pay_money = $("#jfje").val();			//缴费金额
	
	//界面没有
	param.js_money = 0;	//结算金额
	
	param.zb_money = $("#zbje").val() ? $("#zbje").val() : 0; 		//追补金额，没有写死为0
	
	param.all_money = $("#zje").html();
	param.buy_times = parseInt($("#buy_times").html());//补卡操作时，购电次数不需要加一

	param.res_id = $("#res_id").val();			//客户编号,存储的是residentpara表中的id
	param.pay_type = $("#pay_type").val();		//缴费方式
	param.seqNo = $("#seqNo").val();
	param.keyNo = $("#keyNo").val();
	var operType = SDDef.YFF_OPTYPE_REPAIR;//操作类型：补卡

	$.post(def.basePath + "ajaxoper/actSaveDBBengal!operDYSaveDB.action", 		//后台处理程序
	{
		params : jsonString.json2String(param),
		operType : operType
	},
	function(data){
		loading.loaded();
		if(data.result = "success"){
			alert("Success！");
			//刷新缴费记录
			getRecord($("#rtu_id").val(), $("#mp_id").val(), "", 10);
		}
		else{
			alert("Impossible de stocker la base de données！");
		}
		$("#repair").attr("disabled",true);
	});
}

