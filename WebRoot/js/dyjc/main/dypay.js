var rtnValue;
var gloQueryResult = { };

var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识

$(document).ready(function(){
    initGrid();//初始化grid
	$("#btnSearch").click(function(){selcons()});
	
	$("#chgMbphone").click(function(){changeMbphone()}); // 更换手机
	$("#btnPay").click(function(){doPay()});
	$("#btnPrt").click(function(){printPayRec()});
	
	$("#zbje").keyup(function(){calcTotal();});
	$("#jsje").keyup(function(){calcTotal();});
	$("#jfje").keyup(function(){calcTotal();});
	
	$("#btnSearch").focus();
	setDisabled(true);
});

function selcons(){//检索
	var rtnValue1 = doSearch("zz",ComntUseropDef.YFF_DYOPER_PAY, rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno};
	mis_query(param, gloQueryResult);
	
	setDisabled(false);
	setConsValue(rtnValue);
	$("#now_remain").html(round(rtnValue.now_remain, def.point));
	
	getRecord(rtnValue.rtu_id, rtnValue.mp_id);
	$("#btnPay").attr("disabled",false);	
	$("#btnPrt").attr("disabled",true);	
	
}

function doPay(){//缴费
	if(!check()) return;

	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	
	var params = {};
	params.rtu_id    = rtu_id;
	params.mp_id     = mp_id;
	params.paytype   = $("#pay_type").val();
	params.buynum    = rtnValue.buy_times;
	params.pay_money = $("#jfje").val()===""?0:$("#jfje").val();
	if(params.pay_money < 0){
		alert("缴费金额不能为负数");
		return;
	}
	if(round(parseFloat($("#zongje").html())+parseFloat($("#now_remain").html()),3) < 0){
		alert("总金额应大于[-"+$("#now_remain").html()+"]");
		return false;
	}
	params.othjs_money = $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money    = $("#zbje").val()===""?0:$("#zbje").val();	
	params.all_money   = round(parseFloat(params.zb_money) + parseFloat(params.pay_money) + parseFloat(params.othjs_money),3);

	modalDialog.height = 320;
	modalDialog.width = 300;
	modalDialog.url = def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param = {
		showGSM : true,
		key	: ["客户编号","用户名称","所属供电所", "缴费金额","追补金额","结算金额","总金额"],
		val	: [rtnValue.userno,rtnValue.username,rtnValue.orgname, params.pay_money,params.zb_money,params.othjs_money,params.all_money]		
	};
	
	var o = modalDialog.show();
	if(!o.flag){
		return;
	}
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			userData1 : rtu_id,
			mpid 	  : mp_id,
			gsmflag   : o.gsm,
			dyOperPay : json_str,
			userData2 : ComntUseropDef.YFF_DYOPER_PAY
		},
		function(data) {			    	//回传函数
			if (data.result == "success") {
				loading.loaded();
				//向MIS系统缴费
				if(parseFloat(params.pay_money) > 0 && 
				   gloQueryResult.misUseflag == "true" && 
				   gloQueryResult.misOkflag  == "true") {

					var ret_json = eval("(" + data.dyOperPay + ")");
					var mis_para 	= {};
					
					//河北南网
					if (provinceMisFlag == "HB") {
						mis_para.rtu_id = params.rtu_id;
						mis_para.mp_id  = params.mp_id;
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
						for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
							eval("mis_para.yhbh" + i + "=gloQueryResult.misDetailsvect["+i+"].yhbh");
							eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");
						}
						mis_para.vlength = gloQueryResult.misDetailsvect.length;
					}
					//河南
					else if (provinceMisFlag == "HN") {
						mis_para.rtu_id = params.rtu_id;
						mis_para.mp_id = params.mp_id;
						mis_para.op_type= SDDef.YFF_OPTYPE_PAY;
						mis_para.date 	= ret_json.op_date;
						mis_para.time 	= ret_json.op_time;
						mis_para.updateflag = 0;
						mis_para.jylsh 	= ret_json.wasteno;
						mis_para.yhbh 	= rtnValue.userno;
						mis_para.jfje 	= params.pay_money;
						mis_para.yhzwrq = ret_json.op_date;
						mis_para.jfbs 	= gloQueryResult.misJezbs;

						mis_para.batch_no	= gloQueryResult.misBatchNo;	
						mis_para.hsdwbh		= gloQueryResult.misHsdwbh;	
						
						var all_pay = 0.0;
						for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
							eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");

							eval("mis_para.dfny" + i + "=gloQueryResult.misDetailsvect["+i+"].dfny");
							eval("mis_para.dfje" + i + "=gloQueryResult.misDetailsvect["+i+"].dfje");
							eval("mis_para.wyjje" + i + "=gloQueryResult.misDetailsvect["+i+"].wyjje");
							var bcssje= Math.min(parseFloat(gloQueryResult.misDetailsvect[i].dfje) + parseFloat(gloQueryResult.misDetailsvect[i].wyjje), parseFloat(mis_para.jfje) - all_pay);
							all_pay += bcssje;
							eval("mis_para.bcssje" + i + "=" + bcssje);
						}
						mis_para.vlength = gloQueryResult.misDetailsvect.length;
					}
					//甘肃
					else if (provinceMisFlag == "GS") {
						mis_para.rtu_id = params.rtu_id;
						mis_para.mp_id = params.mp_id;
						mis_para.op_type= SDDef.YFF_OPTYPE_PAY;
						mis_para.date 	= ret_json.op_date;
						mis_para.time 	= ret_json.op_time;
						mis_para.updateflag = 0;
						mis_para.jylsh 	= ret_json.wasteno;
						mis_para.yhbh 	= rtnValue.userno;
						mis_para.jfje 	= params.pay_money;
						mis_para.yhzwrq = ret_json.op_date;
						mis_para.jfbs 	= gloQueryResult.misJezbs;
						mis_para.dzpc   = gloQueryResult.misDzpc;
						
						var all_pay = 0.0;
						for(var i = 0; i < gloQueryResult.misDetailsvect.length; i++) {
							eval("mis_para.yhbh" + i + "=gloQueryResult.misDetailsvect["+i+"].yhbh");
							eval("mis_para.ysbs" + i + "=gloQueryResult.misDetailsvect["+i+"].ysbs");

							eval("mis_para.jfje" + i + "=gloQueryResult.misDetailsvect["+i+"].yjje");
							eval("mis_para.dfje" + i + "=gloQueryResult.misDetailsvect["+i+"].dfje");
							eval("mis_para.wyjje" + i + "=gloQueryResult.misDetailsvect["+i+"].wyjje");
							
							eval("mis_para.sctw" + i + "=gloQueryResult.misDetailsvect["+i+"].sctw");
							eval("mis_para.bctw" + i + "=gloQueryResult.misDetailsvect["+i+"].bctw");
						}
						mis_para.vlength = gloQueryResult.misDetailsvect.length;
					}
					
					mis_pay(mis_para);
				}
				
				alert("缴费成功!");
				setDisabled(true);
				getRecord($("#rtu_id").val(), $("#mp_id").val());
				$("#btnPrt").attr("disabled",false);
				$("#btnPay").attr("disabled",true);
				
				window.top.WebPrint.setYffDataOperIdx2params(data.dyOperPay,window.top.WebPrint.nodeIdx.dymain);//打印用的参数
			}
			else {
				alert("缴费失败!" + (data.operErr ? data.operErr : ''));
			}			
		}
	);
}

function check() {
	//如果MIS不通，禁止缴费
	var money_limit = rtnValue.moneyLimit;
	if (gloQueryResult.misUseflag == "true" && (gloQueryResult.misOkflag == "false" || gloQueryResult.misOkflag == undefined)) {
		alert("MIS不能通讯,禁止开户...");
		return false;
	}
	
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

	if(!isDbl("jfje",  "缴费金额" ,0 , money_limit, false)){
		return false;
	}

	if(!isDbl("zbje",  "追补金额" ,-money_limit , money_limit, false)){
		return false;
	}
	if(!isDbl("jsje",  "结算金额" ,-money_limit , money_limit, false)){
		return false;
	}

	if ((window.top.dynegativemoney.dymain.totflag == 0) &&
		($("#zongje").html() < "0")) {
		alert("总金额不能为负数");
		return false;
	}
		
	if(!isDbl_Html("zongje" ,  "总金额", -money_limit, money_limit, false)){//缴费金额应该在0~囤积值之间
		return false;
	}

	if ((window.top.dynegativemoney.dymain.remainflag == 0) &&
		(parseFloat($("#zje").html()) + parseFloat($("#now_remain").html()) < 0)) {
		alert("最新余额不能为负数");
		return false;
	}

	if (parseFloat($("#zongje").html()) + parseFloat($("#now_remain").html()) > rtnValue.moneyLimit) {
		alert("最新余额超出囤积限值");
		return false;
	}
	
	if(($("#jfje").val() == "0" || $("#jfje").val() == "")){
		return(confirm("客户未缴费,继续操作吗?"))
	}
	
//	if($("#zongje").html() > rtnValue.moneyLimit||$("#zongje").html() < 0){
//		alert("总金额应在0与" + rtnValue.moneyLimit + "之间");
//		return false;
//	}
	return true;
}

function printPayRec(){//打印
	//window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.dyje;
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.dymain);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}

function calcTotal(){//总金额
	
	var zbje = $("#zbje").val()===""?0:$("#zbje").val();
	var jsje = $("#jsje").val()===""?0:$("#jsje").val();
	var jfje = $("#jfje").val()===""?0:$("#jfje").val();
	
	if(isNaN(jfje) || isNaN(jsje) || isNaN(zbje)){
		return;
	}
	
	var zje = round(parseFloat(zbje) + parseFloat(jsje) + parseFloat(jfje),3);
	$("#zongje").html(zje);
}	

//更换手机
function changeMbphone(){
	modalDialog.height = 160;
	modalDialog.width  = 200;
	modalDialog.url = def.basePath + "jsp/dialog/chgMbPhone.jsp";
	
	var rtn = rtnValue;
	rtn.flag = 'dy';
	modalDialog.param = rtn;
	var params = modalDialog.show();
	if(!params) return;
	var json_str = jsonString.json2String(params);
	loading.loading();
	
	$.post(def.basePath + "ajaxoper/actOperDy!taskProc.action", 
			{
				userData1		: params.rtu_id,
				mpid 			: params.mp_id,
				gsmflag 		: params.gsmFlag,
				dyOperResetDoc 	: json_str,
				userData2 		: ComntUseropDef.YFF_DYOPER_RESETDOC
			},
			function(data){
				loading.loaded();
				if (data.result == "success") {
					$("#tel").html(params.filed1);
				}
				else {
					alert("向主站发送更改手机命令失败");
				}
			}
	);
}

function setDisabled(mode){
	if(!mode){
		$("#now_remain").html("");
		$("#jsje").val("");
		$("#jfje").val("");
		$("#zbje").val("");
		$("#zongje").html("");
	}
	$("#btnPay").attr("disabled",mode);
	$("#jfje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#zbje").attr("disabled",mode);
	$("#chgMbphone").attr("disabled",mode);
}