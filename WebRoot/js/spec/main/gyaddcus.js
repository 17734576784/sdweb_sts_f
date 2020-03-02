//开户
var rtnValue;
//定义全局变量 isDbl 
var min = 0;
var rtnBD = null;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	
	$("#btnSearch"  ).focus().click(function(){selcons()});
	$("#btnNew"     ).click(function(){if(check())addNewCus()});
	$("#btnImportBD").click(function(){Import()});
	$("#btnPrt").click(function(){showPrint()});
	IsDisabled();
	
});


function Import(){
	var tmp = importBD($("#rtu_id").val(),$("#zjg_id").val());
	if(!tmp){
		return;
	}
	rtnBD = tmp;
	$("#td_bdinf").html(rtnBD.td_bdinf);
	$("#jfje").focus();
	$("#btnNew").attr("disabled",false);
	
}

function calMoney(){

	var zbje = 0.0, jfje = 0.0, jsje = 0.0;
	
	zbje = $("#zbje").val()==="" ? 0 : $("#zbje").val();
	jfje = $("#jfje").val()==="" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val()==="" ? 0 : $("#jsje").val();
	
	var zje = round(parseFloat(zbje) + parseFloat(jfje) + parseFloat(jsje),2);
	
	if(!isNaN(zje)){
		setAlarmValue(rtnValue.yffalarm_alarm1, rtnValue.yffalarm_alarm2, zje)
		$("#zje").val(zje);
	}
}

function selcons(){//检索
	$("#btnNew").attr("disabled",true);
	var tmp = doSearch("zz",ComntUseropDef.YFF_GYOPER_ADDCUS, rtnValue);
	if(!tmp){
		return;
	}
    rtnValue = tmp;

	setConsValue(rtnValue);
	getAlarmValue(rtnValue.yffalarm_id);//获取报警方式
	getMoneyLimit(rtnValue.feeproj_id);
	UnDisabled();
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, zjg_id : rtnValue.zjg_id, busi_no : rtnValue.userno};
	mis_query(param, gloQueryResult);
	
	$("#btnImportBD").focus();
	$("#btnPrt").attr("disabled",true);
	
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
}

function addNewCus(){
	
	if(rtnBD == null)
	{
		alert("未录入表底!");
		return;
	}

	var params = {};
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	params.myffalarmid 		= $("#yffalarm_id").val();
	params.paytype 			= $("#pay_type").val();
	params.buynum 			= 1;
	
	params.alarm_val1		= $("#yffalarm_alarm1").val();
	params.alarm_val2		= $("#yffalarm_alarm2").val();
	
	params.pay_money 		= $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money 		= $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money 		= $("#zbje").val()===""?0:$("#zbje").val();
	params.all_money   = round(parseFloat(params.pay_money) + parseFloat(params.zb_money) + parseFloat(params.othjs_money),2);
	
	params.date 	= 0;
	params.time 	= 0;
	params.buy_dl 	= 0;
	params.pay_bmc 	= 0;
	
	params.shutdown_bd = 0;
	params.total_yzbdl = $("#total_yzbdl").val() == "" ? 0 : $("#total_yzbdl").val();
	params.total_wzbdl = $("#total_wzbdl").val() == "" ? 0 : $("#total_wzbdl").val();
	
	params.date =rtnBD.date;
	params.time =0;
	
	for(var i = 0; i < 3; i++){
		eval("params.mp_id"  + i + "=rtnBD.mp_id" + i);
		
		if(rtnBD.zf_flag.substring(i, i + 1) == "0"){	//正向
			eval("params.bd_zy"  + i + "=rtnBD.zbd" + i);
			eval("params.bd_zyj" + i + "=rtnBD.jbd" + i);
			eval("params.bd_zyf" + i + "=rtnBD.fbd" + i);
			eval("params.bd_zyp" + i + "=rtnBD.pbd" + i);
			eval("params.bd_zyg" + i + "=rtnBD.gbd" + i);
			eval("params.bd_zw"  + i + "=rtnBD.wbd" + i);
			eval("params.bd_fy"  + i + "=0");
			eval("params.bd_fyj" + i + "=0");
			eval("params.bd_fyf" + i + "=0");
			eval("params.bd_fyp" + i + "=0");
			eval("params.bd_fyg" + i + "=0");
			eval("params.bd_fw"  + i + "=0");
		}
		else{
			eval("params.bd_zy"  + i + "= 0");
			eval("params.bd_zyj" + i + "= 0");
			eval("params.bd_zyf" + i + "= 0");
			eval("params.bd_zyp" + i + "= 0");
			eval("params.bd_zyg" + i + "= 0");
			eval("params.bd_zw"  + i + "= 0");
			eval("params.bd_fy"  + i + "= rtnBD.zbd" + i);
			eval("params.bd_fyj" + i + "= rtnBD.jbd" + i);
			eval("params.bd_fyf" + i + "= rtnBD.fbd" + i);
			eval("params.bd_fyp" + i + "= rtnBD.pbd" + i);
			eval("params.bd_fyg" + i + "= rtnBD.gbd" + i);
			eval("params.bd_fw"  + i + "= rtnBD.wbd" + i);
		}
	}
	
	modalDialog.height = 300;
	modalDialog.width = 280;
	modalDialog.url = def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param = {
		showGSM : true,
		key	: ["客户编号","客户名称", "缴费金额","追补金额","结算金额","总金额"],
		val	: [$("#userno").html(),$("#username").html(), params.pay_money,params.zb_money,params.othjs_money,params.all_money]		
	};
	
	var o = modalDialog.show();
	
	if(!o||!o.flag){
		return;
	}
	
	params.gsmFlag = o.gsm;//params 最后一个参数是否发送短信  赋值  进行action请求
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
		{
			userData1		: params.rtu_id,
			zjgid 			: params.zjg_id,
			gsmflag 		: params.gsmFlag,
			gyOperAddCus 	: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_ADDCUS
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				
				if(parseFloat(params.pay_money) > 0 && gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
					var ret_json = eval("(" + data.gyOperAddCus + ")");
					var mis_para 	= {};
					mis_para.rtu_id = ret_json.rtu_id;           
					mis_para.zjg_id = params.zjg_id;             
					mis_para.op_type= SDDef.YFF_OPTYPE_ADDRES;   
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
				
				$("#buy_times").val(1);
				$("#btnPrt").attr("disabled",false);
    			window.top.WebPrint.setYffDataOperIdx2params(data.gyOperAddCus,window.top.WebPrint.nodeIdx.gymain);//打印用的参数

				alert("开户成功!");
				loading.loaded();
				IsDisabled();//点击开户之后 各个input框 disabled
			}
			else {
				alert("向主站发送开户命令失败!"  + (data.operErr ? data.operErr : ''));
			}
		}
	);
}

//修改check方法  修改总金额判断   6.15
function check(){
	
	//如果MIS不通，禁止缴费
	if (gloQueryResult.misUseflag == "true" && (gloQueryResult.misOkflag == "false" || gloQueryResult.misOkflag == undefined)) {
		alert("MIS不能通讯,禁止开户...");
		return false;
	}
	
	if($("#btnNew").attr("disabled"))return false;
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

	if ((window.top.gynegativemoney.gymain.totflag == 0) &&
		($("#zje").val() < "0")) {
		alert("总金额不能为负数");
		return false;
	}
		
	if(!isDbl("zje" ,  "总金额", -money_limit, money_limit, false)){//缴费金额应该在0~囤积值之间
		return false;
	}
	
	var total_yzbdl = $("#total_yzbdl").val();
	var total_wzbdl = $("#total_wzbdl").val();
	if(isNaN(total_yzbdl)){
		alert("有功追补电量应该为数字!");
		$("#total_yzbdl").select().focus();
		return false;
	}
	if(isNaN(total_wzbdl)){
		alert("无功追补电量应该为数字!");
		$("#total_wzbdl").select().focus();
		return false;
	}
	
	if(($("#jfje").val()=="0"||$("#jfje").val()=="")){
		return(confirm("客户未缴费,继续操作吗?"))
	}
	      
	return true;
}

//刚刚进入界面  各个input默认disabled
function IsDisabled(){
	
	$("#zbje"           ).attr("disabled",true);
	$("#jfje"           ).attr("disabled",true);
	$("#jsje"           ).attr("disabled",true);
	$("#zje"            ).attr("disabled",true);
	$("#yffalarm_alarm1").attr("disabled",true);
	$("#yffalarm_alarm2").attr("disabled",true);
	$("#total_yzbdl").attr("disabled",true);
	$("#total_wzbdl").attr("disabled",true);
	$("#btnNew"         ).attr("disabled",true);
	$("#btnImportBD"    ).attr("disabled",true);
	
}

//selcons 各个input属性disabled更改为 false
function UnDisabled(){
	
	//清空追补金额,结算金额,缴费金额,总金额的值 方便继续开户
	$("#zbje"    ).attr("value","");
	$("#jsje"    ).attr("value","");
	$("#jfje"	 ).attr("value","");
	$("#zje"     ).attr("value","");
	$("#td_bdinf").html("");
	$("#jsyhm").html("");
	$("#yhfl").html("");
	rtnBD = null;//每次查完都要原先存放的表底清空(表底隐含数据)
	
	$("#zbje"           ).attr("disabled",false);
	$("#jfje"           ).attr("disabled",false);
	$("#jsje"           ).attr("disabled",false);
	$("#yffalarm_alarm1").attr("disabled",false);
	$("#yffalarm_alarm2").attr("disabled",false);
	
	$("#total_yzbdl").attr("disabled",false);
	$("#total_wzbdl").attr("disabled",false);
	$("#btnImportBD").attr("disabled",false);
}

function showPrint(){//打印发票
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.gymain);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintGy();
}

