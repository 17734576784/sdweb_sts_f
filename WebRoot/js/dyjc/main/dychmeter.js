var rtnValue;

var rtnBDNew = null;
var rtnBDOld = null;

var mp_info = null;
$(document).ready(function(){
	
	initDate();
	initGrid();	
	changeGHLX();
	
	$("#btnSearch").click(function(){selcons()});
	$("#btnChange").click(function(){doChange()});
	$("#rtnBDOld ").click(function(){Import("old")});
	$("#rtnBDNew ").click(function(){Import("new")});
	//$("#btnPrt").click(function(){printHB()});
	$("#ghlx").change(function(){changeGHLX()});
	$("#ghdb").change(function(){getOldCT()});	
	$("#ctfz").keyup(function(){changeCT()});
	$("#ctfm").keyup(function(){changeCT()});
	
	$("#btnSearch").focus();
	setDisabled(true);
});

function initDate() {
	dateFormat.date = dateFormat.getToday("yMd");
	var datestr = dateFormat.formatToYMD(0);
	dateFormat.date = dateFormat.getToday("H")+"00";
	datestr = datestr + " " + dateFormat.formatToHM(0);
	$("#ghsj").val(datestr);
}

function selcons(){//检索
	var rtnValue1 = doSearch("zz",ComntUseropDef.YFF_DYOPER_CHANGEMETER,rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
	setDisabled(false);
	setConsValue(rtnValue);
	
	getRecord($("#rtu_id").val(), $("#mp_id").val());//表格初始化
	getMPList();
	
	$("#btnChange").attr("disabled",true);
	$("#rtnBDOld").focus();
}


function getMPList(){//填充[更换电表]的下拉框
	$("#ghdb").html("");
	var datas = {};
	datas.rtu_id = $("#rtu_id").val();
	datas.mpid0 = $("#mp_id").val();
	if(rtnValue.power_relaf!=0){
		datas.mpid1 = rtnValue.power_rela1;
		datas.mpid2 = rtnValue.power_rela2;
	}else{
		datas.mpid1 = "0";
		datas.mpid2 = "0";
	}
	var json_str = jsonString.json2String(datas);
	$.post( def.basePath + "ajaxdyjc/actChgMeter!getMpList.action",{result:json_str},function(data) {			    	//回传函数
		if (data.result != "") {
			var json = eval('(' + data.result + ')');
			mp_info  = json;
			for(var i=0;i<json.rows.length;i++){
				$("#ghdb").append("<option value="+json.rows[i].value+">"+json.rows[i].text+"</option>");
			}
		}
		getOldCT();
	});
}

function doChange(){//换表 换ct
    
	if(!check()) return;
	
	if(!confirm("确认要换表/换倍率操作吗？")){
		return;
	}
	
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	
	
	var params = {};
	params.rtu_id = rtu_id;
	params.mp_id  = mp_id;
	params.paytype     =  rtnValue.pay_type;
	params.buynum      =  rtnValue.buy_times;
	
	var ghdb	    = $("#ghdb").val();
	var mp = findMpInfo(ghdb);
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
	
	if($("#ghlx").val() == 0) {	//换表
		params.newct_n = mp.ctfz;
		params.newct_d = mp.ctfm;
		params.newct_r = mp.ct;
		
	} else {
		params.newct_n = $("#ctfz").val()  ==  0  ? 1 :$("#ctfz").val();
		params.newct_d = $("#ctfm").val()  ==  0  ? 1 :$("#ctfm").val();
		params.newct_r = $("#ctbb").html() ==  0  ? 1 :$("#ctbb").html();
	}
	
	params.pay_money = 0;
	params.othjs_money = 0;
	params.zb_money    = 0;	
	params.all_money   = 0;
	
	params.chg_mpid = $("#ghdb").val();
	var ghsj = $("#ghsj").val();
	
	params.chg_date 	= ghsj.substring(0,4) + ghsj.substring(5,7) + ghsj.substring(8,10);
	params.chg_time 	= ghsj.substring(12,14) + ghsj.substring(15,17) +"00";
	params.chg_type 	= $("#ghlx").val();	
	
	var rtnBD ;
	
	params.o_date = rtnBDOld.date;
	params.o_time = 0;
	params.n_date = rtnBDNew.date;
	params.n_time = 0;
	
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
		function(data) {//回传函数
			loading.loaded();
			if (data.result == "success") {
				alert("换表成功!");
				
				setDisabled(true);
				
				$("#btnChange").attr("disabled",true);
				//$("#btnPrt").attr("disabled",false);
				getRecord($("#rtu_id").val(), $("#mp_id").val());//表格初始化
				
				window.top.WebPrint.setYffDataOperIdx2params(data.dyOperChangeMeter,window.top.WebPrint.nodeIdx.dymain);//打印用的参数
			}
			else {
				alert("换表失败!" + (data.operErr ? data.operErr : ''));
			}
			
		}
	);
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
	

	var ghlx = $("#ghlx").val();
	if(ghlx == 1){
		if(!isNumber("ctfz" ,"新CT分子")){
			return false;
		}
		if(!isNumber("ctfm" ,"新CT分母")){
			return false;
		}
	}
	
	return true;
}


function changeGHLX(){	//更换类型
	
	var type = $("#ghlx").val();
	if(type != "0"){	//更换CT
		$("#tr_ct").attr("style","display:blank");
		$("#jfje").attr("disabled", true);
		$("#jsje").attr("disabled", true);
		$("#zbje").attr("disabled", true);
		getOldCT();
	}else{				//更换电表
		$("#jfje").attr("disabled", false);
		$("#jsje").attr("disabled", false);
		$("#zbje").attr("disabled", false);
		$("#ghdb").attr("disabled", false);
		$("#tr_ct").attr("style","display:none");		
	}
}

function changeCT(){
	var ctfz=$("#ctfz").val();
	var ctfm=$("#ctfm").val();
	if(!isNaN(ctfz) && !isNaN(ctfm) && ctfz>0 && ctfm>0){
		$("#ctbb").html(round(ctfz/ctfm, 2));
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

function getOldCT(){	//更换电表选择动力关联的非主表时，查询旧表CT添加到新CT里。
	
	var mp_id = $("#ghdb").val();
	var mp = findMpInfo(mp_id);
	if(mp == null) return;
	
	$("#ctfz").val(mp.ctfz);
	$("#ctfm").val(mp.ctfm);
	$("#ctbb").html(mp.ct);
}

function setDisabled(mode){
	if(!mode){
		
		$("#bd_old").html("");
		$("#bd_new").html("");
		
		rtnBDNew = null;
		rtnBDOld = null;
		mp_info = null;
	}
	$("#ghsj").attr("disabled",mode);
	$("#ghlx").attr("disabled",mode);
	$("#ghdb").attr("disabled",mode);
	$("#ctfz").attr("disabled",mode);
	$("#ctfm").attr("disabled",mode);
	
	$("#rtnBDOld").attr("disabled",mode);
	$("#rtnBDNew").attr("disabled",mode);
}

function printHB() {
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.dymain);
	if(filename == undefined || filename == null){
		return;
	}
	window.top.WebPrint.prt_params.file_name = filename;
//	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.dyje;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}