var rtnValue;
var rtnBD;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	
	$("#btnImportBD").click(function(){Import()});
	$("#btnSearch").click(function(){selcons()});
	
	$("#btnNew").click(function(){if(checkBDZ("")){newOne()}});
	$("#btnPrt").click(function(){printPayRec()});
	
	$("#yzje").change(function(){calcTotal();});
	$("#jfje").keyup(function(){calcTotal();});
	$("#jsje").keyup(function(){calcTotal();});
	
	setDisabled(true);
    $("#btnSearch").focus();
});

function selcons(){//检索
	var rtnValue1 = doSearch("zz",ComntUseropDef.YFF_DYOPER_ADDRES,rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
	setDisabled(false);
	
	if(rtnValue.feeType != SDDef.YFF_FEETYPE_JTFL && rtnValue.feeType != SDDef.YFF_FEETYPE_MIXJT){//阶梯费率 则 旧表基础电量可用
	   $("#jt_total_zbdl").attr("disabled",true).css("background","#ccc");
	}else{
	   $("#jt_total_zbdl").attr("disabled",false).css("background","#fff");
	}
	
	setConsValue(rtnValue);
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno};
	mis_query(param, gloQueryResult);
	
	$("#btnImportBD").focus();
	$("#btnPrt").attr("disabled",true);
	$("#btnNew").attr("disabled",true);
}

function newOne(){//开户
	
	if(!check()) return;
	
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert(sel_user_info);
		return;
	}
	var params = {};
	params.rtu_id        = rtu_id;
	params.mp_id         = mp_id;
	params.paytype       = $("#pay_type").val();
	params.feeproj_id    = $("#feeproj_id").val();
	params.myffalarmid   = $("#yffalarm_id").val();
	params.pay_money     = $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money   = $("#jsje").val()===""?0:$("#jsje").val();
	params.zb_money      =  - $("#yzje").val();
	params.all_money     = round(parseFloat(params.pay_money)+ parseFloat(params.zb_money) + parseFloat(params.othjs_money),3);
	params.jt_total_zbdl = $("#jt_total_zbdl").val() == "" ? 0 :$("#jt_total_zbdl").val();
	
	params.date = rtnBD.date;
	params.time = 0;
	for(var i = 0; i < 3; i++){
		eval("params.mp_id"  + i + "=rtnBD.mp_id" + i);
		eval("params.bd_zy"  + i + "=rtnBD.zbd" + i);
		eval("params.bd_zyj" + i + "=rtnBD.jbd" + i);
		eval("params.bd_zyf" + i + "=rtnBD.fbd" + i);
		eval("params.bd_zyp" + i + "=rtnBD.pbd" + i);
		eval("params.bd_zyg" + i + "=rtnBD.gbd" + i);
	}

	if(params.all_money < 0){
		alert("总金额应该大于零！");
		return;
	}
	modalDialog.height = 320;
	modalDialog.width  = 300;
	modalDialog.url    = def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param  = {
		showGSM : true,
		key	: ["客户编号","用户名称","所供电所", "缴费金额(元)","结算金额(元)","预置金额(元)","总金额(元)"],
		val	: [rtnValue.userno,rtnValue.username,rtnValue.orgname, params.pay_money,params.othjs_money,$("#yzje").val(),params.all_money]		
	};
	
	var o = modalDialog.show();
	if(!o.flag){
		return;
	}
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			userData1      : rtu_id,
			mpid           : mp_id,
			gsmflag        : o.gsm,//0,
			dyOperAddres   : json_str,
			userData2      : ComntUseropDef.YFF_DYOPER_ADDRES
		},
		function(data) {//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);	
			if (data.result == "success") {
				
				if(parseFloat(params.pay_money) > 0 && gloQueryResult.misUseflag == "true" && gloQueryResult.misOkflag == "true") {
					var ret_json = eval("(" + data.dyOperAddres + ")");
					var mis_para 	= {};
					
					//河北南网
					if (provinceMisFlag == "HB") {
						mis_para.rtu_id = params.rtu_id;
						mis_para.mp_id  = params.mp_id;
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
						mis_para.op_type= SDDef.YFF_OPTYPE_ADDRES;
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
						mis_para.op_type= SDDef.YFF_OPTYPE_ADDRES;
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
				
				alert("开户成功!");
				setDisabled(true);
				window.top.WebPrint.setYffDataOperIdx2params(data.dyOperAddres,window.top.WebPrint.nodeIdx.dymain);//打印用的参数
				
				$("#btnPrt").attr("disabled",false);
				$("#btnNew").attr("disabled",true);
			}
			else {
				alert("开户失败!" + (data.operErr ? data.operErr : ''));
			}
		}
	);
}

function check(){
	
	//如果MIS不通，禁止缴费
	if (gloQueryResult.misUseflag == "true" && (gloQueryResult.misOkflag == "false" || gloQueryResult.misOkflag == undefined)) {
		alert("MIS不能通讯,禁止开户...");
		return false;
	}
	
	var jfje = 0, zbje = 0, jsje = 0;
	jfje = $("#jfje").val()==="" ? 0 : $("#jfje").val();
	jsje = $("#jsje").val()==="" ? 0 : $("#jsje").val();
	
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
	
	if(!isDbl("jsje",  "结算金额", -rtnValue.moneyLimit, rtnValue.moneyLimit, false)){
		return false;
	}
	
	if(!isDbl("jfje",  "缴费金额", 0, rtnValue.moneyLimit, false)){
		return false;
	}

	if(!isDbl_Html("zje" ,  "总金额", 0, rtnValue.moneyLimit, false)){//缴费金额应该在0~囤积值之间
		return false;
	}

	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
	}

	if(!isDbl("jt_total_zbdl" , "旧表基础电量")){
		return false;
	}
	
	if(($("#jfje").val() == "0" || $("#jfje").val() == "")){
		return(confirm("客户未缴费,继续操作吗?"))
	}
	
	
	return true;
}

function Import(){
	var tmp = importBD($("#rtu_id").val(),$("#mp_id").val());
	if(!tmp)return;
	rtnBD = tmp;
    $("#td_bdinf").html(rtnBD.td_bdinf);
	$("#btnNew").attr("disabled",false);
}

function calcTotal(){//总金额
	
	var yzje = $("#yzje").val();
	var jfje = $("#jfje").val() === "" ? 0 : $("#jfje").val();
	var jsje = $("#jsje").val() === "" ? 0 : $("#jsje").val();
	
	if(isNaN(jfje) || isNaN(jsje)){
		return;
	}
	$("#zje").html(round(parseFloat(jfje) - parseFloat(yzje) + parseFloat(jsje), 3));
}

function setDisabled(mode){
	if(!mode){
		$("#jfje").val("");
		$("#jsje").val("");
		$("#zje").html("");
		$("#td_bdinf").html("");
		$("#jt_total_zbdl").val("");
		rtnBD = null;
	}
	$("#jfje").attr("disabled",mode);
	$("#jsje").attr("disabled",mode);
	$("#jt_total_zbdl").attr("disabled",mode);
	$("#btnImportBD").attr("disabled",mode);
}

function printPayRec() {
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.dymain);
	if(filename == undefined || filename == null){
		return;
	}
	window.top.WebPrint.prt_params.file_name = filename;
//	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.dyje;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}