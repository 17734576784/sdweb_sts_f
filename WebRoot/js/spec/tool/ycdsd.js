var rtnValue;
var params = {};
var gloQueryResult = { };
$(document).ready(function(){
	lastPayInfoByType();
	initGrid();
	$("#btnSearch").click(function(){selcons()});
	
	$("#readRtuInfo").click(function() {readRtuInfo()});
//	$("#detail_info").click(function() {showOpDetailDialog()});
	
	isDisabled();
	
});

function readRtuInfo(){
	params.type = sel_type;
	params.rtuid = $("#rtu_id").val();
	params.zjgid = $("#zjg_id").val(); 
	params.userno = $("#userno").html(); 
	params.username = $("#username").html();
	params.buytimes = $("#buy_times").html();
	params.blv = $("#blv").html();
	params.dj = $("#feeproj_reteval").html();
	params.cacl_type_desc = $("#cacl_type_desc").html(); 
	if(rtnValue.prot_type == undefined) params.prot_type = ComntDef.YD_PROTOCAL_KEDYH;
	params.prot_type = rtnValue.prot_type;
	//请求一部分数据
	var param = {};
	param.rtu_id 			= params.rtuid;
	param.zjg_id 			= params.zjgid;
	param.paratype			= ComntProtMsg.YFF_GY_CALL_PARAREMAIN;
	
	var json_str = jsonString.json2String(param);
	loading.loading();
	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommGy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_GYCOMM_CALLPARA;
	//end
	
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommGy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag 	: true,
			userData1		: param.rtu_id,
			zjgid			: param.zjg_id,
			gyCommCallPara	: json_str,
			userData2 		: ComntUseropDef.YFF_GYCOMM_CALLPARA
		},
		function(data) {			    	
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			
			//20131130
			//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
			window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
			window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommGy!cancelTask.action"
			window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_READDATA;
			//end
			
			if (data.result == "success") {
				window.top.addStringTaskOpDetail("接收预付费服务任务成功...");
				var json = eval('(' + data.gyCommCallPara + ')');
				setValue(json);
				
				if (params.prot_type == ComntDef.YD_PROTOCAL_FKGD05 || params.prot_type == ComntDef.YD_PROTOCAL_GD376
						|| params.prot_type == ComntDef.YD_PROTOCAL_GD376_2013) {
					
					param.mpid 		= 1;
					param.ymd 		= 0;
					param.datatype	= ComntProtMsg.YFF_CALL_GY_REMAIN;
					
					json_str = jsonString.json2String(param);
					loading.loading();
					window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
					$.post( def.basePath + "ajaxoper/actCommGy!taskProc.action", 		//后台处理程序
						{
							firstLastFlag 	: true,
							userData1		: param.rtu_id,
							zjgid			: param.zjg_id,
							gyCommReadData	: json_str,
							userData2 		: ComntUseropDef.YFF_READDATA
						},
						function(data) {
							loading.loaded();
							window.top.addJsonOpDetail(data.detailInfo);
							if (data.result == "success") {
								window.top.addStringTaskOpDetail("接收预付费服务任务成功...");
								var json = eval('(' + data.gyCommReadData + ')');
								$("#nowval").html(json.cur_val);
							}
					});
				}
			}else{
				window.top.addStringTaskOpDetail("通讯失败.");
				alert("通讯失败..");
			}
		});
}

function setValue(json){
	
	$("#nowval").html(json.nowval);              //当前 表底
	$("#totval").html(json.totval);              //断电止码
	$("#alarm_val1").html(json.alarm_val1);      //报警止码
	
	$("#start_yff_flag").attr('checked', json.yff_flag == 1 ? true : false);//启动预付费
	
	setRemainDL_JE(json);
}

//读取
function setRemainDL_JE(json){
	if(params.type == "bd"){
		var bmc		 = Number(json.totval) - Number(json.nowval); 
		var remainDl = bmc*params.blv;
		var remainDl_format = Math.round(remainDl*Math.pow(10, 2))/Math.pow(10, 2);
		$("#remain_dl").html(remainDl_format);   //剩余电量
		
		var remain_Je = remainDl*params.dj;      //剩余金额
		var remainJe_format = Math.round(remain_Je*Math.pow(10, 2))/Math.pow(10, 2);
		$("#remain_je").html(remainJe_format);
	}
}

function selcons(){//检索
	
	unDisabled();
	var tmp = doSearch(sel_type, ComntUseropDef.YFF_GYCOMM_PAY, rtnValue);
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	if (sel_type == "bd" && (rtnValue.pay_type == 0 || rtnValue.prot_type != ComntDef.YD_PROTOCAL_KEDYH)) {	//科林大用户规约 或者 本地费控
		alert("用户选择的终端不能远程读预付费！");
		return;
	}
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, zjg_id : rtnValue.zjg_id, busi_no : rtnValue.userno};
	mis_query(param,gloQueryResult);
	
	cons_para.plus_width = "plus_width";
	
	//科林大用户规约显示是否启用预付费，其他规约隐藏
	if(rtnValue.prot_type && rtnValue.prot_type != ComntDef.YD_PROTOCAL_KEDYH){
		$("#yff_start1").hide();
		$("#start_yff_flag").hide();
	}
	
	setConsValue(rtnValue);
	$("#buy_times").html(rtnValue.buy_times);
	
	getMoneyLimit(rtnValue.feeproj_id);		//囤积限值
	getAlarmValue(rtnValue.yffalarm_id);	//获取报警方式
	
	getGyYffRecs(rtnValue.rtu_id, rtnValue.zjg_id);
	
	var tmp_str = "";
	if((rtnValue.prot_st == 0) && (rtnValue.prot_ed == 0)) 
		tmp_str = "正常用电";
	else if(rtnValue.prot_st == rtnValue.prot_ed)
		tmp_str = "全天保电";
	else 
		tmp_str = "时段保电";
	$("#prot_state").html(tmp_str);
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
	
	unDisabled();
}

function initGrid(){
	mygrid = new dhtmlXGridObject('gridbox');
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("150,80,80,80,80,80,80,80,80,80,150,80,80,*");
	mygrid.setColAlign("center,center,right,right,right,right,right,right,right,right,left,left,left");
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.setSkin("light");
	mygrid.init();
	
}

function unDisabled(){
    $("#detail_info").attr("disabled",false);
    $("#readRtuInfo").attr("disabled",false);   
}

function isDisabled(){
    $("#detail_info").attr("disabled",true);
    $("#readRtuInfo").attr("disabled",true);
}

function lastPayInfoByType(){
	if(sel_type == "je"){
		$("#nowval_text").html("当前金额：");
		$("#totval_text").html("总金额：");
		$("#alarm_val1_text").html("报警金额：");
		$("#remain_info").hide();
	}
}