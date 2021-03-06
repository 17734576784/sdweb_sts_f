//销户
var rtnValue;
var rtnBD = null;
$(document).ready(function(){

	$("#btnSearch").click(function(){selcons()});
	
	$("#btnXH").click(function(){if(check())logoff()});
	
	$("#remainSum").click(function(){remainSum()});
	
	 $("#btnPrt").click(function(){showPrint()});
	
	$("#btnImportBD").click(function(){Import()});
	
	initGrid();
	//input disabled
	IsDisabled()
});

function initGrid(){
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("150,80,80,80,80,80,80,80,80,80,150,80,80,*");
	mygrid.setColAlign("center,center,right,right,right,right,right,right,right,right,left,left,left");
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.enableTooltips("false,false,false,false,false,false,false,false,false,false,false,false,false");
	mygrid.init();
	mygrid.setSkin("light");
	mygrid.setColumnHidden(6, true);
}

function remainSum(){
	
	if(rtnBD == null){
		alert("未录入表底..");
		return;
	}
	
	var params = {};
	params.userop_id = ComntUseropDef.YFF_GYOPER_GETREMAIN;
	params.rtu_id 	 = $("#rtu_id").val();
	params.zjg_id 	 = $("#zjg_id").val();

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
		}else{
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

	$("#dqje").val("");
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post(
		def.basePath + "ajaxoper/actOperGy!taskProc.action", 
		{
			userData1        :  params.rtu_id,
			zjgid 			 :  params.zjg_id,
			userData2        :  params.userop_id,
			gyOperGetRemain  :  json_str,
			gsmflag          :  0
		},
		function(data){
			loading.loaded();
			var json = eval("("+data.gyOperGetRemain+")");
			if(json.remain_val == ""){
				alert("数据出错");
			}else{
				$("#dqje").val(round(json.remain_val,2));
			}
		}
	);

}

function Import(){
	var tmp = importBD($("#rtu_id").val(),$("#zjg_id").val());
	if(!tmp){
		return;
	}
	rtnBD = tmp;
	$("#td_bdinf").html(rtnBD.td_bdinf);
	$("#remainSum").attr("disabled",false);
	$("#btnXH").attr("disabled",false);
}

function selcons(){//检索
	
	var tmp = doSearch("zz",ComntUseropDef.YFF_GYOPER_DESTORY,rtnValue);
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	setConsValue(rtnValue);
	//计费信息 ——当前金额——当前表底赋值
	$("#dqje").val(round(rtnValue.now_remain, 3));//四舍五入去三位小数
    
	getMoneyLimit();
	getGyYffRecs();
	
	//input undisabled	
	UnDisabled();
	$("#btnPrt").attr("disabled",true);
	$("#btnImportBD").focus();
	
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
}
function getGyYffRecs(){//缴费记录
	mygrid.clearAll();
	var param = "{\"rtu_id\":\"" + $("#rtu_id").val() + "\",\"zjg_id\":\"" +$("#zjg_id").val() + "\",\"top_num\":\"5\"}";
	$.post( def.basePath + "ajax/actCommon!getGyYFFRecs.action",{result:param},	function(data) {			    	//回传函数
		if (data.result != "") {
			jsdata = eval('(' + data.result + ')');
			mygrid.parse(jsdata,"json");
		}
	});
}

function logoff(){
	
	var params = {};
	
	params.rtu_id 			= $("#rtu_id").val();
	params.zjg_id 			= $("#zjg_id").val();
	
	params.pay_money 		= -parseFloat($("#dqje").val()===""?0:round($("#dqje").val(),2));
	params.othjs_money 		= 0;
	params.zb_money 		= 0;
	params.all_money 		= params.pay_money;
	params.date				= rtnBD.date;
	params.time				= 0;
		
	for(var i = 0; i < 3; i++){
		eval("params.mp_id" + i + "=rtnBD.mp_id" + i);		
		
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
		}else{
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
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag 	: true,
			userData1		: params.rtu_id,
			zjgid 			: params.zjg_id,
			gsmflag 		: 0,
			gyOperDestory	: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_DESTORY
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				alert("销户成功");
				IsDisabled();
				getGyYffRecs();
				$("#btnPrt").attr("disabled",false);
    			window.top.WebPrint.setYffDataOperIdx2params(data.gyOperDestory);//打印用的参数

			}
			else {
				alert("向主站发送命令失败");
			}
		}
	);
	
}
//btn销户前 check
//修改check方法  修改总金额判断   6.15
function check(){
	//判断总表底是否为空
	if (rtnBD == null){
		alert("请录入表底!");
		return false;
	}
	
	if(!confirm("确定要销户吗？")){
		return false;
	}
	return true;
}

//刚刚进入界面  各个input默认disabled
function IsDisabled(){
	$("#dqje").attr("disabled",true);
	//btn
	$("#remainSum").attr("disabled",true);
	$("#btnXH").attr("disabled",true);
	$("#btnImportBD").attr("disabled",true);
	
}

//selcons 各个input属性disabled更改为 false
function UnDisabled(){
	$("#dqje").attr("disabled",false);
	$("#td_bdinf").html("");
	rtnBD = null;
	$("#zbd").attr("value","");
	//btn
	$("#btnImportBD").attr("disabled",false);
}

function showPrint(){//打印发票
	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.gyje;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintGy();
}