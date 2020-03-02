var rtnValue;
var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识
var gloQueryResult = { };

$(document).ready(function(){
	
	$("#btnSearch").click(function(){selcons()});
	$("#btnReadMeter").click(function(){metinfo()});
	$("#btnNew").click(function(){newOne()});
	$("#btnPrt").click(function(){showPrint()});
	$("#yzje").change(function(){calcTotal();});
	
	$("#jfje").keyup(function(){
		if(checkNum($("#jfje").val(),true,"缴费金额")){calcTotal()}
		else{
			this.value = "";
		}
	});

});

function calcTotal(){//总金额
	
	var yzje = $("#yzje").val()===""?0:$("#yzje").val();
	var jfje = $("#jfje").val()===""?0:$("#jfje").val();
	
	$("#zongje").html(round(parseFloat(jfje) - parseFloat(yzje),3));
}	
	
function checkNum(jeVal,zFlag,desc){
	if(isNaN(jeVal)){
		return false;
	}
	return true;
}

function selcons(){//检索
	var tmp = doSearch("yc",ComntUseropDef.YFF_DYOPER_ADDRES,rtnValue);
	if(!tmp){
		return;
	}
	rtnValue = tmp;
	
	setConsValue(rtnValue);
	
	//mis.js中函数
	var param = {rtu_id : rtnValue.rtu_id, mp_id : rtnValue.mp_id, yhbh : rtnValue.userno};
	mis_query(param, gloQueryResult);
	
	check_JT_total_zbdl()//检测是否为阶梯费率
	
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
	
	$("#btnReadMeter").attr("disabled",false);
	$("#btnNew").attr("disabled",false);
	$("#btnPrt").attr("disabled",true);	
	
	$("#jfje").attr("disabled",false);
	$("#jcdl").attr("disabled",false);
	$("#jfje").focus();
	$("#jfje").val("");	
	$("#jcdl").val("");	
	$("#zongje").html("");	
}

function newOne(){//开户

	//如果MIS不通，禁止缴费
	if (gloQueryResult.misUseflag == "true" && (gloQueryResult.misOkflag == "false" || gloQueryResult.misOkflag == undefined)) {
		alert("MIS不能通讯,禁止开户...");
		return false;
	}
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert(sel_user_info);
		return;
	}

	var jfje = 0;
	jfje = $("#jfje").val()==="" ? 0 : $("#jfje").val();
	
	if(isNaN(jfje)){
		alert("请输入数字!");
		$("#jfje").focus().select();
		return false;
	}

	if(!isDbl("jfje",  "缴费金额", 0, rtnValue.moneyLimit, false)){
		return false;
	}

	if(!isDbl_Html("zongje", "总金额", 0, rtnValue.moneyLimit, false)){//缴费金额应该在0~囤积值之间
		return false;
	}

	if(parseFloat(jfje) == 0){
		if(!confirm("用户未缴费,要继续缴费吗?"))return false;
	}
	if(!isDbl("jt_total_zbdl", "旧表基础电量")){
		return false;
	}

	var params = {};
	params.rtu_id = rtu_id;
	params.mp_id = mp_id;
	params.paytype = $("#pay_type").val();
	params.feeproj_id = $("#feeproj_id").val();
	params.myffalarmid = $("#yffalarm_id").val();
	params.buynum = "1";
	params.pay_money = $("#jfje").val()===""?0:$("#jfje").val();
	params.othjs_money = 0;
	params.zb_money = $("#yzje").val()===""?0:(0 - $("#yzje").val());
	params.all_money = round(parseFloat(params.pay_money) + parseFloat(params.zb_money),3);
	params.rtu_id = rtu_id;
	params.mp_id0 = mp_id;
	params.mp_id1 = rtnValue.power_rela1;
	params.mp_id2 = rtnValue.power_rela2;
	
	params.bd_zy0 = 0;
	params.bd_zy1 = 0;
	params.bd_zy2 = 0;
	params.bd_zyj0 = 0;
	params.bd_zyj1 = 0;
	params.bd_zyj2 = 0;
	params.bd_zyf0 = 0;
	params.bd_zyf1 = 0;
	params.bd_zyf2 = 0;
	params.bd_zyp0 = 0;
	params.bd_zyp1 = 0;
	params.bd_zyp2 = 0;
	params.bd_zyg0 = 0;
	params.bd_zyg1 = 0;
	params.bd_zyg2 = 0;
	
	params.jt_total_zbdl = $("#jt_total_zbdl").val()==="" ? 0 : $("#jt_total_zbdl").val();
	
	
	params.date = dateFormat.getToday("yMd");
	params.time = dateFormat.getToday("H")+"00";
	modalDialog.height = 300;
	modalDialog.width = 300;
	modalDialog.url = def.basePath + "jsp/dialog/info.jsp";
	modalDialog.param = {
		showGSM : true,
		key	: ["客户编号","用户名称","所属供电所", "缴费金额(元)","预置金额(元)","总金额(元)"],
		val	: [rtnValue.userno,rtnValue.username,rtnValue.orgname, params.pay_money,$("#yzje").val(),params.all_money]		
	};
	
	var o = modalDialog.show();
	if(!o.flag){
		return;
	}
	var json_str = jsonString.json2String(params);
	loading.loading();
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	
	//20131130
	//给取消操作函数参数赋值,taskCancelObj在opdetail.js中定义
	window.top.taskCancelObj.cancel_rtu = rtnValue.rtu_id;
	window.top.taskCancelObj.cancel_src = def.basePath + "ajaxoper/actCommDy!cancelTask.action"
	window.top.taskCancelObj.cancel_oper = ComntUseropDef.YFF_DYCOMM_ADDRES;
	//end
	
	$.post( def.basePath + "ajaxoper/actCommDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag : true,
			userData1	  : rtu_id,
			mpid 		  : mp_id,
			gsmflag 	  : o.gsm,
			dyCommAddres  : json_str,
			userData2 	  : ComntUseropDef.YFF_DYCOMM_ADDRES
		},
		function(data) {			    	//回传函数			
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				window.top.addStringTaskOpDetail("远程通讯成功!正在保存到数据库...");
				loading.loading();
				$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
					{
						userData1	 : rtu_id,
						mpid 		 : mp_id,
						gsmflag 	 : 0,
						dyOperAddres : json_str,
						userData2 	 : ComntUseropDef.YFF_DYOPER_ADDRES
					},
					function(data) {			    	//回传函数				
						loading.loaded();
						window.top.addJsonOpDetail(data.detailInfo);	
						if (data.result == "success") {						
							//向MIS系统缴费
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
							if (window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
								alert("开户成功!");
							}else{
								window.top.addStringTaskOpDetail("开户成功!");
							}
							$("#btnPrt").attr("disabled",false);
							$("#btnNew").attr("disabled",true);
							window.top.WebPrint.setYffDataOperIdx2params(data.dyOperAddres,window.top.WebPrint.nodeIdx.dyremote);//打印用的参数
						}
						else {
							if(window.top.glo_opdetail_dlg == null || window.top.glo_opdetail_dlg.closed) {
								alert("保存到数据库失败!");
							}else{
								window.top.addStringTaskOpDetail("保存到数据库失败!");
							}	
						}
					}
				);				
			}
			else {
				alert("开户失败");
			}
		}
	);
}

function check_JT_total_zbdl(){//阶梯费率 则 旧表基础电量可用			
	if(rtnValue.feeType == SDDef.YFF_FEETYPE_JTFL || rtnValue.feeType == SDDef.YFF_FEETYPE_MIXJT){
	   $("#jt_total_zbdl").attr("disabled",false).css("background","#fff");
	}else{
	   $("#jt_total_zbdl").attr("disabled",true).css("background","#ccc");
	}
}

function showPrint(){//打印发票
	var filename =  window.top.WebPrint.getTemplateFileNm(window.top.WebPrint.nodeIdx.dyremote);
	if(filename == undefined || filename == null)return;
	window.top.WebPrint.prt_params.file_name = filename;
//	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.dyje;
	window.top.WebPrint.prt_params.reprint = 0;
	window.top.WebPrint.doPrintDy();
}

function metinfo(){//表内信息--远程读表
	modalDialog.width = 800;
	modalDialog.height = 600;
	modalDialog.url = def.basePath + "jsp/dyjc/dialog/meterInfo.jsp";
	modalDialog.param = {"rtuId":$("#rtu_id").val() ,"mpId":$("#mp_id").val()};
	modalDialog.show();
}
