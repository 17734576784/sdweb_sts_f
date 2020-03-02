var rtnValue;
var jsonFLFA;
var jsonGHDB;
var jsonZDJBF;//终端基本费

$(document).ready(function(){
	
	$("#qhsj").val(new Date().Format("yyyy年MM月dd日 HH时mm分").toString());
	
	$("#btnSearch").click(function(){selcons()});	//检索
	$("#flfa").change(function(){changeFLFA()});	//费率方案 
	$("#gglx").change(function(){chageType()});		//'更改类型 
	$("#ghdb").change(function(){changeGHDB()});	//'更换电表 
	$("#btnZC").click(function(){callpara();});		//召测 
	$("#btnGG").click(function(){doChange();});			//更改
    $("#btnSave").click(function(){doSave();});		//保存
    
    //初始化新费率方案
	initFLFA();
	
    IsDisabled()
    //$("#btnSave").attr("disabled",false);
});

function selcons(){//检索  click 

	UnDisabled()
	
	var rtnValue1 = doSearch("je",ComntUseropDef.YFF_GYOPER_CHANGERATE,rtnValue);
	if(!rtnValue1){
		return;
	}
	
	rtnValue = rtnValue1;
	
	setConsValue(rtnValue);
	$("#pay_add1").html(rtnValue.pay_add1);//基本费，附加值2，附加值3赋值
	$("#pay_add2").html(rtnValue.pay_add2);
	$("#pay_add3").html(rtnValue.pay_add3);
	
	$("#xjbf").val(rtnValue.pay_add1);//新基本费

	$("#ghdb").val(rtnValue.fee_unit);
	
	
	//初始化更换电表  
	initGHDB();
	//在更换电表之前将费控单元的值  赋值给更换电表

	$("#btnZC").attr("disabled",false);
	$("#btnGG").attr("disabled",false);
	
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
}


function callpara(){//召测
	$("#zdfl").html();
	$("#zdjbf").html();
	
	var params 		= {};
	params.rtu_id 	= $("#rtu_id").val();
	params.zjgid 	= $("#zjg_id").val();
	
	var gglx = $("#gglx").val();
	if (rtnValue.prot_type == ComntDef.YD_PROTOCAL_KEDYH || gglx == 'flcs') {
		params.paratype = ComntProtMsg.YFF_GY_CALL_FEE;	//召费率	
	}
	else {
		params.paratype = ComntProtMsg.YFF_GY_CALL_APPEND;	//召附加费
	}
	
	
	var json_str = jsonString.json2String(params);
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
			firstLastFlag  : true,
			userData1      : params.rtu_id,
			zjgid          : params.zjgid,
			gyCommCallPara : json_str,
			userData2 	   : ComntUseropDef.YFF_GYCOMM_CALLPARA////高压通讯-召参数
		},
		function(data) {			    	//回传函数
			window.top.addJsonOpDetail(data.detailInfo);	
			if (data.result == "success") {		
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					//alert("接收预付费服务任务成功...");
				}else{
					window.top.addStringTaskOpDetail("接收预付费服务任务成功.");
				}
				jsonZDJBF = eval("(" + data.gyCommCallPara + ")");
				
				if (rtnValue.prot_type == ComntDef.YD_PROTOCAL_KEDYH) {
					$("#zdjbf").html(jsonZDJBF.pay_add1);
					 rated_z = jsonZDJBF.rated_z;
					 ratef_j = jsonZDJBF.ratef_j;
					 ratef_f = jsonZDJBF.ratef_f;
					 ratef_p = jsonZDJBF.ratef_p;
					 ratef_g = jsonZDJBF.ratef_g;
					$("#zdfl").html("总:" + rated_z + " 尖:" + ratef_j + " 峰:" + ratef_f + " 平:" + ratef_p + " 谷:" + ratef_g);//已经拼接完成，不需要自己手动修改
				}
				else {
					if (gglx == 'flcs') {
						rated_z = jsonZDJBF.rated_z;
					 	ratef_j = jsonZDJBF.ratef_j;
					 	ratef_f = jsonZDJBF.ratef_f;
					 	ratef_p = jsonZDJBF.ratef_p;
					 	ratef_g = jsonZDJBF.ratef_g;
						$("#zdfl").html("总:" + rated_z + " 尖:" + ratef_j + " 峰:" + ratef_f + " 平:" + ratef_p + " 谷:" + ratef_g);//已经拼接完成，不需要自己手动修改
					}
					else {
						$("#zdjbf").html(jsonZDJBF.pay_add1);
					}
				}
			}
			else {
				if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
					alert("接收预付费服务任务失败...");
				}
				else {
					window.top.addStringTaskOpDetail("接收预付费服务任务失败.");
				}
			}
			loading.loaded();
		}
	);
}


function doChange(){//更改
	var gglx = $("#gglx").val();
	if (gglx == 'flcs') {
		var o_fee	= $("#feeproj_id").val();
		var n_fee	= $("#flfa").val();
		if(o_fee == n_fee){
			alert("费率方案相同不需更改,请重新选择新费率方案。");
			return;
		}
	}

	if(!confirm("确定要修改吗?")){
		return ;
	}
	
	var params = {};
	params.rtu_id 		= $("#rtu_id").val();
	params.zjgid		= $("#zjg_id").val();
	params.feeproj_id	= $("#flfa").val();
	params.pay_add1		= $("#xjbf").val();
	params.pay_add2		= $("#pay_add2").html();
	params.pay_add3		= $("#pay_add3").html();	
	
	var gglx = $("#gglx").val();
	if (rtnValue.prot_type == ComntDef.YD_PROTOCAL_KEDYH || gglx == 'flcs') {
		params.paratype = ComntProtMsg.YFF_GY_SET_FEI;	//设费率	
	}
	else {
		params.paratype = ComntProtMsg.YFF_GY_SET_APPEND;	//设附加费
	}

	var json_str=jsonString.json2String(params);

	loading.loading();
	
	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommGy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_GYCOMM_SETPARA;
	//end
	
	window.top.addStringTaskOpDetail('正在向预付费服务发送信息...');
	$.post(def.basePath + "ajaxoper/actCommGy!taskProc.action",
			{
		        firstLastFlag  :  true,
		        userData1      :  params.rtu_id,
	            userData2      :  ComntUseropDef.YFF_GYCOMM_SETPARA,//高压通讯-设参数
	            gyCommSetPara  :  json_str,
	            zjgid	       :  params.zjgid
			},
			function(data){
				window.top.addJsonOpDetail(data.detailInfo);
				if(data.result=="success"){
					$("#btnSave").attr("disabled",false);
					window.top.addStringTaskOpDetail("接收预付费服务任务成功.");
					alert("更改成功");
				}else{
					window.top.addStringTaskOpDetail("接收预付费服务任务失败.");
				    alert("更改失败");
				}
				loading.loaded();
			}
	
	);
}


/////保存--开始
function doSave(){//保存(onclick)
	var gglx = $("#gglx").val();
	if (gglx == 'flcs') {
		if($("#feeproj_id").val() == $("#flfa").val()){
			alert("费率方案相同不需更改,请重新选择新费率方案。");
			return;
		}
		saveFLFA();
	}
	else if (gglx == "jbf") {
		
		var o_fee	= $("#pay_add1").html();
		var n_fee	= $("#xjbf").val();
		
		if(parseFloat(o_fee) == parseFloat(n_fee)){
			alert("新旧基本费相同不需保存,请重新输入新基本费。");
			return;
		}
		
		saveJBF();
	}
	else{
		alert("系统错误");
	}
}
 
function saveJBF(){//保存(基本费)
		
	var params={};
	
	params.rtu_id		= $("#rtu_id").val();
	params.zjgid		= $("#zjg_id").val();
	params.jbf_chgval	= $("#xjbf").val();
	
	var qhsj 		= $("#qhsj").val();
	params.chg_date = qhsj.substring(0,4)+qhsj.substring(5,7)+qhsj.substring(8,10)
	params.chg_time = qhsj.substring(12,14)+qhsj.substring(15,17)+"00";
	//调用获取表底的函数
	getBDPara(params);
	
	var json_str=jsonString.json2String(params);
	loading.loading();
	$.post(def.basePath + "ajaxoper/actOperGy!taskProc.action",
			{
		        gsm_flag  			:'0',    //是否发送短信
		        userData1			:params.rtu_id,
		        userData2			:ComntUseropDef.YFF_GYOPER_CHANGPAYADD,//高压操作-换基本费
		        zjgid	 			: params.zjgid,
		        gyOperChanePayAdd	:json_str
			},
			function(data){
				if(data.result == "success"){
					$("#pay_add1").html($("#xjbf").val());
					$("#btnSave").attr("disabled",true);
					$("#btnGG").attr("disabled",true);
					alert("更改基本费成功");
				}else{
				    alert("更改基本费失败");
				}
				loading.loaded();
			}	
	);
}

function saveFLFA(){//保存(费率方案) 
	
	//rtu_id 和 zjg_id  $.post要用
	var params = {};
	params.rtu_id = $("#rtu_id").val();
	params.zjgid  = $("#zjg_id").val();	
	var ghdb 	  = $("#ghdb").val();
	
	if (ghdb == jsonGHDB.rows[0].value) {
		params.chgid0 = $("#flfa").val();
		params.chgid1 = jsonGHDB.rows[0].feeproj1;
		params.chgid2 = jsonGHDB.rows[0].feeproj2;
	}
	else if (ghdb == jsonGHDB.rows[1].value) {
		params.chgid0 = jsonGHDB.rows[0].feeproj;
		params.chgid1 = $("#flfa").val();
		params.chgid2 = jsonGHDB.rows[0].feeproj2;
	}
	else if (ghdb == jsonGHDB.rows[2].value) {
		params.chgid0 = jsonGHDB.rows[0].feeproj;
		params.chgid1 = jsonGHDB.rows[0].feeproj1;
		params.chgid2 = $("#flfa").val();
	}
	
	var qhsj = $("#qhsj").val();
	params.chg_date = qhsj.substring(0,4) 	+ qhsj.substring(5,7)	+ qhsj.substring(8,10);
	params.chg_time = qhsj.substring(12,14) + qhsj.substring(15,17) + "00";
	
	//调用获取表底的函数
	getBDPara(params);
	//param.paratype=ComntProMsg.具体参数类型应该不用设置因为别的都没有
	loading.loading()
	var json_str=jsonString.json2String(params);
	$.post(def.basePath + "ajaxoper/actOperGy!taskProc.action",
			{
		        gsm_flag  	: '0',    //是否发送短信
		        userData1	: params.rtu_id,
		        userData2	: ComntUseropDef.YFF_GYOPER_CHANGERATE,//高压操作-换电价
		        zjgid	 	: params.zjgid,
		        gyOperChangeRate:json_str
			},
			function(data){
				if(data.result == "success"){
					var colors = "<b style='color:#800000'>";
					var colore = "</b>";
					$("#feeproj_id").val($("#flfa").val());
					$("#feeproj_desc").html(colors+$("#flfa").find("option:selected").text()+colore);
					$("#feeproj_detail").html(colors+$("#fams_n").html()+colore);
					$("#btnSave").attr("disabled",true);
					$("#btnGG").attr("disabled",true);
					alert("更改费率方案成功");
				}else{
				    alert("更改费率方案失败");
				}
				loading.loaded();
			}	
	);
}

//保存--结束


//费率方案初始化
function initFLFA(){
	$("#flfa option").remove();
	$.post(def.basePath + "ajaxdyjc/actChgRate!getRateList.action",{},function(data){
		if(data.result != ""){
			jsonFLFA = eval('(' + data.result + ')');
			for (var i = 0; i < jsonFLFA.rows.length; i++) {
				if ((jsonFLFA.rows[i].feeType != SDDef.YFF_FEETYPE_DFL) &&
					(jsonFLFA.rows[i].feeType != SDDef.YFF_FEETYPE_FFL)) continue;	//只要单费率
					
				$("#flfa").append("<option value=" + jsonFLFA.rows[i].value + ">" + jsonFLFA.rows[i].text + "</option>");
			}
		}
	});
}

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

//'更换电表'的init()方法:需要传送rtu_id
function initGHDB(){
	$("#ghdb option").remove();
	var param = '{rtu_id:"' + $("#rtu_id").val() + '",zjg_id:"'+$("#zjg_id").val()+'"}';
	$.post(def.basePath + "ajaxspec/actConsPara!retChgRateInfo.action",{result:param},function(data){
		if (data.result != ""){
			jsonGHDB = eval('(' + data.result + ')');
			for (var i = 0; i < jsonGHDB.rows.length; i++) {
				$("#ghdb").append("<option value=" + jsonGHDB.rows[i].value + ">" + jsonGHDB.rows[i].text + "</option>");
				//保存option  value值 
			}
			changeGHDB();
		}
	});
}


function changeGHDB(){//更换电表 onchange:  更新新费率方案的内容为所选电表的费率方案。
	var flfa;
	var ghdb = $("#ghdb").val();
	
	if (ghdb == '1') {
		flfa = jsonGHDB.rows[0].feeproj;
	}
	else if (ghdb == '2') {
	    flfa = jsonGHDB.rows[0].feeproj1;
	} 
	else if (ghdb == '3') {
		flfa=jsonGHDB.rows[0].feeproj2;
	}
	
	$("#flfa").val(flfa);
	changeFLFA();
}

//更改类型的change事件
function chageType(){
	
	var txt = $("#gglx").val();
	if (txt == 'flcs') {
		$("#flfa").attr("disabled",false).css("background","white");
		$("#ghdb").attr("disabled",false).css("background","white");
		$("#xjbf").attr("disabled",true).css("background","#ccc");
	}
	else if (txt == 'jbf') {
		$("#flfa").attr("disabled",true).css("background","#ccc");
		$("#ghdb").attr("disabled",true).css("background","#ccc");
		$("#xjbf").attr("disabled",false).css("background","white");
	}
}


function getBDPara(params){//传入参数赋值。
	//本地费控（远程金额）的更换费率不用表底。所以跟缴费一样赋默认值或0即可。
	
	params.mp_id0 = $("#ghdb").val();
	params.mp_id1 = $("#ghdb").val();
	params.mp_id2 = $("#ghdb").val();
	
	params.date	= '0';
	params.time	= '0';
	
	params.bd_zy0	= '0';
	params.bd_zyj0	= '0';
	params.bd_zyf0	= '0';
	params.bd_zyp0	= '0';
	params.bd_zyg0	= '0';
	params.date0	= '0';
	params.time0	= '0';
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

//$(document).ready  各个input默认disabled
function IsDisabled(){
	$("#qhsj").attr("disabled",true);
	//$("#gglx").attr("disabled",true);
	$("#flfa").attr("disabled",true).css("background","#ccc");
	$("#ghdb").attr("disabled",true).css("background","#ccc");
	$("#xjbf").attr("disabled",true).css("background","#ccc");
	
	$("#btnSave").attr("disabled",true);
	$("#btnImportBD").attr("disabled",true);
}

//selcons 各个input属性disabled更改为 false
function UnDisabled(){
	$("#qhsj").attr("disabled",false);
	$("#gglx").attr("disabled",false);
	$("#flfa").attr("disabled",false).css("background","white");
	$("#ghdb").attr("disabled",false).css("background","white");
		
	$("#zbd").attr("value","");
	$("#jbd").attr("value","");
	$("#fbd").attr("value","");
	$("#pbd").attr("value","");
	$("#gbd").attr("value","");
	chageType()
}


