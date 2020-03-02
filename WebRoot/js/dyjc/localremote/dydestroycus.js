var rtnValue;
var gloQueryResult = { };

$(document).ready(function(){
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("150,80,80,80,80,80,80,80,80,120,80,80,*")
	mygrid.setColAlign("center,center,left,right,right,right,right,right,right,right,left,left")
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.init();
	mygrid.setSkin("light");
	mygrid.setColumnHidden(7, true);
	
	$("#btnSearch").click(function(){selcons()});
	$("#btnDestroy").click(function(){consDestroy()});
	$("#btnReadMeter").click(function(){compute()});
	$("#btnPrt").click(function(){showPrint()});
});

function selcons(){//检索
	var tmp = doSearch("yc",ComntUseropDef.YFF_DYOPER_DESTORY,rtnValue);
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	
	setConsValue(rtnValue);
	//查询mis
	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno};
	mis_query(param, gloQueryResult);
	
	getYffRecs();
	$("#btnReadMeter").attr("disabled",false);
	$("#btnPrt").attr("disabled",true);	

}

function consDestroy(){//销户
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	
	var params = {};
	params.date = dateFormat.getToday("yMd");
	params.time = dateFormat.getToday("H")+"00";
	params.pay_money = $("#syje").val()==="" ? 0 : ($("#syje").val() * (-1));
	params.othjs_money = 0;
	params.zb_money = 0;
	
	params.all_money = params.pay_money;
	
	params.rtu_id = rtu_id;
	params.mp_id0 = mp_id;
	params.mp_id1 = rtnValue.power_rela1;
	params.mp_id2 = rtnValue.power_rela2;
	
	params.bd_zy0  = 0
	params.bd_zyj0 = 0
	params.bd_zyf0 = 0
	params.bd_zyp0 = 0
	params.bd_zyg0 = 0
	params.bd_zy1  = 0
	params.bd_zyj1 = 0
	params.bd_zyf1 = 0
	params.bd_zyp1 = 0
	params.bd_zyg1 = 0
	params.bd_zy2  = 0
	params.bd_zyj2 = 0
	params.bd_zyf2 = 0
	params.bd_zyp2 = 0
	params.bd_zyg2 = 0
	
	if(!confirm("确定要销户吗？")){
		return;
	}
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			userData1		: rtu_id,
			mpid			: mp_id,
			gsmflag			: 0,
			dyOperDestory	: json_str,
			userData2		: ComntUseropDef.YFF_DYOPER_DESTORY
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				alert("销户成功!");
				getYffRecs();
				$("#btnPrt").attr("disabled",false);
				$("#btnDestroy").attr("disabled",true);
				window.top.WebPrint.setYffDataOperIdx2params(data.dyOperDestory,window.top.WebPrint.nodeIdx.dyremote);//打印用的参数
			}
			else {
				alert("销户失败!");
			}
		}
	);
}

function showPrint(){//打印发票
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.dyremote);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
//	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.dyje;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}

function compute(){//计算余额。执行的“远程读电表余额操作”，返回当前余额
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	
	var params = {};
	params.datatype = ComntProtMsg.YFF_CALL_DY_REMAIN;
		
	params.ymd = 120403; 
	loading.loading();
	
	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommDy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_READDATA;
	//end
	
	var json_str = jsonString.json2String(params);
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag 	: true,
			userData1		: rtu_id,
			mpid 			: mp_id,
			gsmflag 		: 0,
			dyCommReadData 	: json_str,
			userData2 		: ComntUseropDef.YFF_READDATA
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				var json = eval("(" + data.dyCommReadData + ")");				
				$("#syje").val(json.remain_money);
				$("#btnDestroy").attr("disabled",false);
				alert("远程读取电表余额成功!");
			}
			else {
				alert("远程读取电表余额失败!");
			}
		}
	);
}

