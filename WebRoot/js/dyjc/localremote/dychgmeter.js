var rtnValue;
var yffjsdata=null;
var mp_info = null;   //电表信息 
var rtnBDNew = null;
var rtnBDOld = null;
var gloQueryResult = { };

$(document).ready(function(){
	dateFormat.date = dateFormat.getToday("yMd");
	var datestr = dateFormat.formatToYMD(0);
	dateFormat.date = dateFormat.getToday("H")+"00";
	datestr = datestr + " " + dateFormat.formatToHM(0);
	$("#ghsj").val(datestr);
	
	initGrid();	
	changeGHLX();
	setDisabled(true);
	
	$("#btnSearch").click(function(){selcons()});	
	$("#btnChange").click(function(){doChange()});	
	$("#rtnBDOld").click(function(){Import("old")});
	$("#rtnBDNew").click(function(){Import("new")});
	$("#btnPrt").click(function(){showPrint()});
	$("#btnReadMeter").click(function(){metinfo()});
		
	$("#ghlx").change(function(){changeGHLX()});
	$("#ctfz").keyup(function(){changeCT()});
	$("#ctfm").keyup(function(){changeCT()});
	
	$("#zbje").keyup(function(){
		if(checkNum($("#zbje").val(),false,"追补金额")){calcTotal()}
		else this.value = "";
	});
	$("#jfje").keyup(function(){
		if(checkNum($("#jfje").val(),true,"缴费金额")){calcTotal()}
		else this.value = "";
	});
		
});
function initGrid(){
	mygrid = new dhtmlXGridObject('gridbox');
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("150,80,80,80,80,80,80,80,80,120,80,80,*")
	mygrid.setColAlign("center,center,left,right,right,right,right,right,right,right,left,left")
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.init();
	mygrid.setSkin("light");
	if(yffjsdata!=null){
		mygrid.parse(yffjsdata,"json");	
	}
}

function selcons(){//检索
	var tmp = doSearch("yc",ComntUseropDef.YFF_DYOPER_CHANGEMETER,rtnValue);
	if(!tmp){
		return;
	}
	
	rtnValue = tmp;
	setConsValue(rtnValue);
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno};
	mis_query(param, gloQueryResult);
	
	getYffRecs();
	$("#ghlx").val("0");
	changeGHLX();
	getMPList();
	
	$("#zbje").val("");
	$("#jfje").val("");
	$("#zongje").html("");
	$("#ghdb").val(rtnValue.mp_id);
	setDisabled(false);
	
	$("#btnPrt").attr("disabled",true);
	$("#btnChange").attr("disabled",true);
}

function getMPList(){//填充[更换电表]的下拉框
	var datas = {};
	datas.rtu_id = $("#rtu_id").val();
	datas.mpid0 = $("#mp_id").val();
	if(rtnValue.power_relaf!=0){
		datas.mpid1 = -1;
		datas.mpid2 = -1;
	}else{
		datas.mpid1 = -1;
		datas.mpid2 = -1;
	}
	var json_str = jsonString.json2String(datas);
	$.post( def.basePath + "ajaxdyjc/actChgMeter!getMpList.action",{result:json_str},function(data) {	//回传函数
			if (data.result != "") {
				var json = eval('(' + data.result + ')');
				mp_info = json;
			}
			getOldCT();
		}
	);
} 

function doChange(){
	if(!confirm("确认要换表/换倍率操作吗？")){
		return;
	}
	if($("#ghlx").val()==0){ //更换电表
		doChangeMeter();
	}else {  //更换CT
		doChangeCT();
	}
}
	
function doChangeMeter() {  //换表-通讯操作
	if(!check()) return;
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	if($("#ghlx").val() == "0" && $("#zongje").html() > rtnValue.moneyLimit){
		alert("总金额不能大于囤积限值[" + rtnValue.moneyLimit + "]");
		return;
	}
	
	var params = {};
	params.rtu_id 		= rtu_id;
	params.mp_id  		= mp_id;
	params.myffalarmid	= rtnValue.yffalarm_id;
	params.pay_money 	= $("#jfje").val() == "" ? 0 : $("#jfje").val();
	params.othjs_money 	= 0;
	params.zb_money		= $("#zbje").val() == "" ? 0:$("#zbje").val();
	params.all_money    = parseFloat(params.zb_money) + parseFloat(params.pay_money);
	
	json_str = jsonString.json2String(params);
	
	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommDy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_DYCOMM_ADDRES;
	//end
	
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
			{
				firstLastFlag : true,
				userData1	  : rtu_id,
				mpid          : $("#ghdb").val(),//mp_id,
				gsmflag       : 0,
				dyCommAddres  : json_str,
				userData2     : ComntUseropDef.YFF_DYCOMM_ADDRES
			},
			function(data) { //回传函数
				loading.loaded();
				window.top.addJsonOpDetail(data.detailInfo);
				if (data.result == "success") {
					window.top.addStringTaskOpDetail("远程通讯成功!正在保存到数据库...");
					doOper();  //换表换倍率低压操作，保存数据库
				}else {
					if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
						alert("换表失败!");
					}else{
						window.top.addStringTaskOpDetail("换表失败!");
					}
				}
			}
		);
}


function doChangeCT(){//换CT-通讯操作
	if(!check()) return;
	
	var rtu_id = $("#rtu_id").val();
	var mp_id  = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	var params = {};
	params.rtu_id = rtu_id;
	params.mp_id  = mp_id;
	
	//***************以下为换CT需要的通讯参数****************************//
	params.paratype  	= ComntProtMsg.YFF_DY_SET_CT;  
	params.user_type 	= 0;  //费率类型
	params.bit_update 	= 0;  //参数更新标志位
	var ghsj 			= $("#ghsj").val();
	params.chg_date 	= ghsj.substring(0,4) + ghsj.substring(5,7) + ghsj.substring(8,10);
	params.chg_time 	= ghsj.substring(12,14) + ghsj.substring(15,17) +"00";
	params.alarm_val1   = 0;
	params.alarm_val2   = 0;
	params.ct			= $("#ctbb").html() == "" ? 1 : $("#ctbb").html();
	var ghdb 			= $("#ghdb").val();
	var mp   			= findMpInfo(ghdb);
	params.pt			= mp.pt;
	params.meterno		= $("#esamno").html();
	params.userno		= $("#userno").html();
	
	var json_str = jsonString.json2String(params);
	
	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommDy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_DYCOMM_SETPARA;
	//end
	
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
			{
				firstLastFlag : true,
				userData1	  : rtu_id,
				mpid          : $("#ghdb").val(),//mp_id,
				gsmflag       : 0,
				dyCommSetPara : json_str,
				userData2     : ComntUseropDef.YFF_DYCOMM_SETPARA
			},
			function(data) { //回传函数
				loading.loaded();
				window.top.addJsonOpDetail(data.detailInfo);
				if(data.result=="success"){
					window.top.addStringTaskOpDetail("远程通讯成功!正在保存到数据库...");
					doOper();  //换表换倍率低压操作，保存数据库
				}else {
					if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
						alert("换CT失败!");
					}else{
						window.top.addStringTaskOpDetail("换CT失败!");
					}
				//	$("#btnChange").attr("disabled",true);
				}
			}
	);
}

//换表换CT数据库操作,换表和换CT通用
function doOper(){
	
	if(!check()) return;
	var rtu_id = $("#rtu_id").val();
	var mp_id  = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	
	var params ={};
	params.rtu_id = rtu_id;
	
	if($("#ghlx").val() == 0) {	//换表
		params.pay_money 	= $("#jfje").val()=="" ? 0 : $("#jfje").val();  
		params.othjs_money	= 0;  
		params.zb_money		= $("#zbje").val()=="" ? 0 : $("#zbje").val();  
		params.all_money    = parseFloat(params.zb_money) + parseFloat(params.pay_money);
		params.buynum 		= 1;
	} else {					//换CT
		params.pay_money 	= 0;  
		params.othjs_money	= 0;  
		params.zb_money		= 0;  
		params.all_money	= 0;
		params.buynum 		= rtnValue.buy_times;
	}
	
	params.o_date = rtnBDOld.date;
	params.o_time = 0;
	params.n_date = rtnBDNew.date;
	params.n_time = 0;
	
	var rtnBD ;
	var ghdb = $("#ghdb").val();
	for(var i = 0; i < 3; i++){
		rtnBD = rtnBDOld;
		eval("params.mp_id"    + i + "=rtnBD.mp_id" + i);
		
		eval("params.bd_zy_o"  + i + "=rtnBD.zbd" + i);
		eval("params.bd_zyj_o" + i + "=rtnBD.jbd" + i);
		eval("params.bd_zyf_o" + i + "=rtnBD.fbd" + i);
		eval("params.bd_zyp_o" + i + "=rtnBD.pbd" + i);
		eval("params.bd_zyg_o" + i + "=rtnBD.gbd" + i);
			
		//确定选择的第几块电表
		if(eval("ghdb==rtnBD.mp_id" + i)){
			eval("params.bd_zy_o  = rtnBD.zbd" + i);
			eval("params.bd_zyj_o = rtnBD.jbd" + i);
			eval("params.bd_zyf_o = rtnBD.fbd" + i);
			eval("params.bd_zyp_o = rtnBD.pbd" + i);
			eval("params.bd_zyg_o = rtnBD.gbd" + i);
		}
		
		rtnBD = rtnBDNew;
		eval("params.bd_zy_n"  + i + "=rtnBD.zbd" + i);
		eval("params.bd_zyj_n" + i + "=rtnBD.jbd" + i);
		eval("params.bd_zyf_n" + i + "=rtnBD.fbd" + i);
		eval("params.bd_zyp_n" + i + "=rtnBD.pbd" + i);
		eval("params.bd_zyg_n" + i + "=rtnBD.gbd" + i);
        //确定选择的第几块电表
		if(eval("ghdb==rtnBD.mp_id" + i)){
			eval("params.bd_zy_n  = rtnBD.zbd" + i);
			eval("params.bd_zyj_n = rtnBD.jbd" + i);
			eval("params.bd_zyf_n = rtnBD.fbd" + i);
			eval("params.bd_zyp_n = rtnBD.pbd" + i);
			eval("params.bd_zyg_n = rtnBD.gbd" + i);
		}
	}
	params.chg_mpid 	= $("#ghdb").val();
	var ghsj 			= $("#ghsj").val();
	params.chg_date 	= ghsj.substring(0,4) + ghsj.substring(5,7) + ghsj.substring(8,10);
	params.chg_time 	= ghsj.substring(12,14) + ghsj.substring(15,17) +"00";
	params.chg_type 	= $("#ghlx").val();	
	
	var mp   = findMpInfo(ghdb);
	if(mp == null) return;
	
	params.oldpt_n = mp.ptfz; 
	params.oldpt_d = mp.ptfm;
	params.oldpt_r = mp.pt;
 
	params.newpt_n = mp.ptfz;
	params.newpt_d = mp.ptfm;
	params.newpt_r = mp.pt;
	
	params.oldct_n = mp.ctfz; 
	params.oldct_d = mp.ctfm;
	params.oldct_r = mp.ct;
	
	var ghlx = $("#ghlx").val();
	if(ghlx==0){//换表
		params.newct_n = mp.ctfz;
		params.newct_d = mp.ctfm;
		params.newct_r = mp.ct;
	}else {		//换CT
		params.newct_n = $("#ctfz").val()  ==  0  ? 1 :$("#ctfz").val();
		params.newct_d = $("#ctfm").val()  ==  0  ? 1 :$("#ctfm").val();
		params.newct_r = $("#ctbb").html() ==  0  ? 1 :$("#ctbb").html();
	}
		
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
			{
				userData1         : rtu_id,
				mpid              : mp_id,
				gsmflag           : 0,
				dyOperChangeMeter : json_str,
				userData2         : ComntUseropDef.YFF_DYOPER_CHANGEMETER
			},
			function(data) {			    	//回传函数
				loading.loaded();
				if (data.result == "success") {
					if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
						if(ghlx == 0){
							alert("换表成功!");
							$("#btnPrt").attr("disabled",false);
						}else {
							alert("换CT成功!");
						}
					}else{
						if(ghlx == 0){
							window.top.addStringTaskOpDetail("换表成功!");
							$("#btnPrt").attr("disabled",false);
						}else {
							window.top.addStringTaskOpDetail("换CT成功!");
						}
					}
					getYffRecs();
					getMPList();
					$("#btnChange").attr("disabled",true);
					window.top.WebPrint.setYffDataOperIdx2params(data.dyOperChangeMeter,window.top.WebPrint.nodeIdx.dyremote);//打印用的参数
				}else {
					if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
						alert("保存到数据库失败!");
					}else{
						window.top.addStringTaskOpDetail("保存到数据库失败!");
					}
				}
			}
		 );

}

function changeGHLX(){//更换类型
 	if($("#ghlx").val() == 0){//更换电表
 		$("#tabinfo4").attr("style","display:blank");
 		$("#tabinfo3").attr("style","display:none");
 	}else{					//更换CT
 		$("#tabinfo4").attr("style","display:none");
 		$("#tabinfo3").attr("style","display:blank");
 		getOldCT();
 	} 
}

function changeCT(){
	var ctfz=$("#ctfz").val();
	var ctfm=$("#ctfm").val();
	if(!isNaN(ctfz) && !isNaN(ctfm) && ctfz>0 && ctfm>0){
		$("#ctbb").html(round(ctfz/ctfm,2));
	}else{
		$("#ctbb").html("");
	}	
}

function Import(bdflag){
	var tmp = importBD($("#rtu_id").val(),$("#mp_id").val());
	if (!tmp) {
		return;
	}
	else{
		if (bdflag == "old") {
		    rtnBDOld = tmp;
		    $("#bd_old").html(rtnBDOld.td_bdinf);
		    $("#rtnBDNew").attr("disabled" , false);
		    $("#rtnBDNew").focus();
		}
		if (bdflag == "new") {
			rtnBDNew = tmp;
			$("#bd_new").html(rtnBDNew.td_bdinf);
			$("#btnChange" ).attr("disabled",false);
		}
	}
}

function setDisabled(mode){
	
	$("#ghsj").attr("disabled",mode);
	$("#ghlx").attr("disabled",mode);
	$("#zbje").attr("disabled",mode);
	$("#jfje").attr("disabled",mode);
	$("#rtnBDOld").attr("disabled",mode);
	$("#rtnBDNew").attr("disabled",mode);

	$("#btnReadMeter").attr("disabled",mode);

	$("#bd_old").html("");
	$("#bd_new").html("");
}

function calcTotal(){//总金额
	
	var zbje = $("#zbje").val()===""?0:$("#zbje").val();
	var jfje = $("#jfje").val()===""?0:$("#jfje").val();
	
	$("#zongje").html(round(parseFloat(zbje) + parseFloat(jfje),3));
}	
	
function checkNum(jeVal,zFlag,desc){
	if(isNaN(jeVal)){
		return false;
	}
	return true;
}
function getOldCT(){//更换电表选择动力关联的非主表时，查询旧表CT添加到新CT里。
	if($("#ghdb").val()===null){
		return;
	}
	
	var mp_id = $("#ghdb").val();
	var mp = findMpInfo(mp_id);
	if(mp == null) return;
	
	$("#ctfz").val(mp.ctfz);
	$("#ctfm").val(mp.ctfm);
	$("#ctbb").html(mp.ct);
}

function check() {
	if(rtnBDOld == null) {
		alert("请录入旧表底...");
		return false;
	}
	
	if(rtnBDNew == null){
		alert("请录入新表底...");
		return false;
	}
	
	if($("#ghsj").val() == ""){
		alert("请填写更换时间...");
		return false;
	}
	
	return true;
}

function showPrint(){//打印发票
	//alert("打印");
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.dyremote);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
//	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.dyje;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}

//返回电表信息
function findMpInfo(mp_id) {
	if(mp_info == null) {
		alert("无电表信息");
		return null;
	}
	for(var i = 0; i < mp_info.rows.length; i++) {
		if(mp_id == mp_info.rows[i].value) {
			return mp_info.rows[i];
		}
	}
	alert("无电表信息");
	return null;
}

function metinfo(){//表内信息--远程读表
	modalDialog.width = 800;
	modalDialog.height = 600;
	modalDialog.url = def.basePath + "jsp/dyjc/dialog/meterInfo.jsp";
	modalDialog.param = {"rtuId":$("#rtu_id").val() ,"mpId":$("#mp_id").val()};
	modalDialog.show();
}