var cons_para = {
	username			: "username",		//客户名称
	userno				: "userno",			//客户编号
	useraddr			: "useraddr",		//客户地址
	tel					: "tel",			//联系电话
	factory				: "factory",		//生产厂家
	blv					: "blv",			//倍率
	esamno				: "esamno",			//表号
	yffmeter_type		: "yffmeter_type",	//预付费表类型
	yffmeter_type_desc	: "yffmeter_type_desc",
	wiring_mode			: "wiring_mode",	//接线方式
	rtu_id				: "rtu_id",			//终端编号
	mp_id				: "mp_id",			//测点编号
	cacl_type_desc		: "cacl_type_desc",	//计费方式
	feectrl_type		: "feectrl_type",	//费控方式
	pay_type			: "pay_type",		//缴费方式
	pay_type_desc		: "pay_type_desc",
	feeproj_id			: "feeproj_id",		//费率方案
	feeproj_detail		: "feeproj_detail",
	feeproj_desc		: "feeproj_desc",
	yffalarm_id			: "yffalarm_id",	//报警方案
	yffalarm_desc		: "yffalarm_desc",
	yffalarm_alarm1		: "yffalarm_alarm1",
	yffalarm_alarm2		: "yffalarm_alarm2",
	yffalarm_detail		: "yffalarm_detail",
	feeproj_reteval		: "feeproj_reteval",
	
	cus_state			: "cus_state",		//客户状态
	buy_times			: "buy_times",		//购电次数
	pay_money			: "pay_money",		//缴费金额
	othjs_money			: "othjs_money",	//结算金额(其它系统)
	zb_money			: "zb_money",		//追补金额
	all_money			: "all_money",		//总金额
	now_remain			: "now_remain",		//当前剩余
	now_remain2			: "now_remain2",	//当前剩余(本地模拟电表)
	total_gdz			: "total_gdz",		//累计购电值
	moneyLimit			: "moneyLimit",		//购电限值
	
	jt_total_dl         : "jt_total_dl",    //阶梯累计用电量

	shutdown_val		: "shutdown_val",	//开始预付费标志
	power_relaf			: "power_relaf",	//动力关联标志
	power_rela1			: "power_rela1",	//动力关联1
	power_rela2			: "power_rela2",	//动力关联2

	rtuonline_img		: "rtuonline_img",	//终端在线/离线图片
	rtuonline_sp		: "rtuonline_sp",	//终端在线/离线说明
		
	meterType			: "meterType"		//电表类型		//20140614页面基本信息新增电表类型

};

function setConsValue(json, null_flag){
	if(null_flag){
		$("#" + cons_para.userno).html("");
		$("#" + cons_para.username).html("");
		$("#" + cons_para.tel).html("");
		$("#" + cons_para.useraddr).html("");
		$("#" + cons_para.cus_state).html("");
		$("#" + cons_para.factory).html("");
		$("#" + cons_para.blv).html("");
		$("#" + cons_para.esamno).html("");
		$("#" + cons_para.yffmeter_type).html("");
		$("#" + cons_para.yffmeter_type_desc).html("");
		$("#" + cons_para.wiring_mode).html("");	
		$("#" + cons_para.cacl_type_desc).html("");
		$("#" + cons_para.feectrl_type).html("");
		$("#" + cons_para.pay_type).val("");
		$("#" + cons_para.pay_type_desc).html("");
		$("#" + cons_para.feeproj_id).val("");
		$("#" + cons_para.feeproj_desc).html("");
		$("#" + cons_para.feeproj_detail).html("");
		$("#" + cons_para.yffalarm_id).val("");
		$("#" + cons_para.yffalarm_desc).html("");
		$("#" + cons_para.yffalarm_detail).html("");
		$("#" + cons_para.buy_times).val("");
		$("#" + cons_para.rtu_id).val("");
		$("#" + cons_para.mp_id).val("");
		$("#" + cons_para.yffalarm_alarm1).val("");
		$("#" + cons_para.yffalarm_alarm2).val("");
		$("#" + cons_para.pay_money).val("");
		$("#" + cons_para.othjs_money).val("");
		$("#" + cons_para.zb_money).val("");
		$("#" + cons_para.all_money).html("");
		$("#" + cons_para.now_remain).html("");
		$("#" + cons_para.now_remain2).html("");
		$("#" + cons_para.total_gdz).val("");
		$("#" + cons_para.moneyLimit).val("");
		
		$("#" + cons_para.jt_total_dl).html("");//阶梯累计电量
		
		$("#" + cons_para.shutdown_val).val("");
		$("#" + cons_para.power_relaf).val("");
		$("#" + cons_para.power_rela1).val("");
		$("#" + cons_para.power_rela2).val("");
		
		//20140614页面基本信息新增电表类型
		$("#" + cons_para.meterType).html("");
	}
	else{
		var colors = "<b style='color:#800000'>";
		var colore = "</b>";
		
		$("#" + cons_para.userno).html(json.userno);
		$("#" + cons_para.username).html(json.username);
		$("#" + cons_para.tel).html(json.tel);
		$("#" + cons_para.useraddr).html(json.useraddr);
		$("#" + cons_para.cus_state).html(json.cus_state);
		$("#" + cons_para.factory).html(json.factory);
		$("#" + cons_para.blv).html(json.blv);
		$("#" + cons_para.esamno).html(json.esamno);
		$("#" + cons_para.yffmeter_type).html(json.yffmeter_type);
		$("#" + cons_para.yffmeter_type_desc).html(json.yffmeter_type_desc);
		$("#" + cons_para.wiring_mode).html(json.wiring_mode);
		$("#" + cons_para.cacl_type_desc).html(colors + json.cacl_type_desc + colore);
		$("#" + cons_para.feectrl_type).html(colors + json.feectrl_type + colore);
		$("#" + cons_para.pay_type).val(json.pay_type);
		$("#" + cons_para.pay_type_desc).html(colors + json.pay_type_desc + colore);
		$("#" + cons_para.feeproj_id).val(json.feeproj_id);
		$("#" + cons_para.feeproj_desc).html(colors + json.feeproj_desc + colore);
		$("#" + cons_para.feeproj_detail).html(colors + json.feeproj_detail + colore);
		$("#" + cons_para.yffalarm_id).val(json.yffalarm_id);
		$("#" + cons_para.yffalarm_desc).html(colors + json.yffalarm_desc + colore);
		$("#" + cons_para.yffalarm_detail).html(colors + json.yffalarm_detail + colore);
		$("#" + cons_para.buy_times).html(json.buy_times);
		$("#" + cons_para.rtu_id).val(json.rtu_id);
		$("#" + cons_para.mp_id).val(json.mp_id);
		
		$("#" + cons_para.yffalarm_alarm1).val(json.yffalarm_alarm1);
		$("#" + cons_para.yffalarm_alarm2).val(json.yffalarm_alarm2);
		$("#" + cons_para.feeproj_reteval).val(json.feeproj_reteval);

		$("#" + cons_para.pay_money).html(json.pay_money);
		$("#" + cons_para.othjs_money).val(json.othjs_money);
		$("#" + cons_para.zb_money).val(json.zb_money);
		$("#" + cons_para.all_money).html(json.all_money);
//		$("#" + cons_para.now_remain).html(json.now_remain);
//		$("#" + cons_para.now_remain2).html(json.now_remain2);
		$("#" + cons_para.total_gdz).html(json.total_gdz);
		$("#" + cons_para.moneyLimit).val(json.moneyLimit);
		$("#" + cons_para.jt_total_dl).val(json.jt_total_dl);
		
		$("#" + cons_para.shutdown_val).val(json.shutdown_val);
		$("#" + cons_para.power_relaf).val(json.power_relaf);
		$("#" + cons_para.power_rela1).val(json.power_rela1);
		$("#" + cons_para.power_rela2).val(json.power_rela2);
		
		//20140614页面基本信息新增电表类型
		$("#" + cons_para.meterType).html(json.cardtype);
		
		if(document.getElementById("sfbd")) {		//算费表底
			$("#sfbd_sj").html(json.calc_bdymd);
			$("#sfbd").html("总:"+ json.calc_zyz.split(",")[0] +
	                " 尖:"+ json.calc_zyj.split(",")[0]+
	                " 峰:"+ json.calc_zyf.split(",")[0]+
	                " 平:"+ json.calc_zyp.split(",")[0]+
                    " 谷:"+ json.calc_zyg.split(",")[0]
			       );
		}
		
		$("#" + cons_para.rtuonline_img).attr("src",json.onlinesrc).attr("style","display:blank");
		$("#" + cons_para.rtuonline_sp).html(json.onlinetxt);
	}
	
}
/**
function setAlarmValue(v1, v2, zje){
	if(rtnValue.type == 0){	//固定值方式
		$("#"+cons_para.yffalarm_alarm1).val(v1);
		$("#"+cons_para.yffalarm_alarm2).val(v2);
	}if(rtnValue.type == 1){	//比例方式
		if(v1 == 0){
			v1 = 30;
		}
		if(v2 == 0){
			v2 = 5;
		}
		$("#"+cons_para.yffalarm_alarm1).val(round(v1 * zje /100, 3));
		$("#"+cons_para.yffalarm_alarm2).val(round(v2 * zje /100, 3));
	}
}
*/

//缴费记录
var mygrid = null;
function initGrid(div_id) {
	if(div_id == undefined) div_id = "gridbox";
	
	mygrid = new dhtmlXGridObject(div_id);
	var yff_grid_title = "操作日期,操作类型,缴费金额(元),结算金额(元),追补金额(元),总金额(元),报警值1,断电金额(元),购电次数,购电量, 表码差, 流水号,缴费方式,操作员,&nbsp;";
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("150,80,80,80,80,80,80,80,80,80,80,120,80,80,*")
	mygrid.setColAlign("center,center,left,right,right,right,right,right,right,right,right,right,left,left")
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.init();
	mygrid.setSkin("light");
	mygrid.setColumnHidden(7, true);//隐藏断电金额,添加时要在7列以后添加
}

//查询缴费记录
function getRecord(rtu_id, mp_id, div_id, num) {
	if(div_id == undefined) div_id = "gridbox";
	if(num == undefined) {
		var div_height = $("#" + div_id).height();
		var tmp_num = Math.floor((div_height / 23));
		if(tmp_num < 5) num = 5;
		else num = tmp_num;
	}
	
	mygrid.clearAll();
	var param = "{rtu_id:" + rtu_id + ",mp_id:" + mp_id + ",top_num: " + num + "}"; 
	$.post( def.basePath + "ajax/actCommon!getDyYFFRecsExt.action",{result : param}, function(data) {
		if (data.result != "") {
			var jsdata = eval('(' + data.result + ')');
			mygrid.parse(jsdata,"json");
		}
	});
}


var rever_info = null;//冲正时查询上次缴费信息
var last_jf_id = ["last_je", "last_zbje", "last_jsje", "last_tt"];
function lastPayInfo(rtu_id, mp_id){
	$.post(def.basePath + "ajax/actCommon!lastPayInfoDY.action", 
		{result : "{rtu_id : " + rtu_id + ", mp_id : " + mp_id + "}"},
	function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			
			//为上次缴费信息赋值
			$("#" + last_jf_id[0]).val(json.pay_money);
			$("#" + last_jf_id[1]).val(json.zb_money);
			$("#" + last_jf_id[2]).val(json.othjs_money);
			$("#" + last_jf_id[3]).val(json.all_money);
			
			$("#zbje").val(json.zb_money);
			$("#jsje").val(json.othjs_money);
			
			rever_info = {};
			rever_info.date = json.op_date;
			rever_info.time = json.op_time;
			rever_info.last_wastno = json.last_wastno;	//原交易流水号
			
		} else {
			alert("无可冲正的记录!");
		}
	});
}

//低压根据表类型取出对应卡类型
function getCardByMeter(yffmeter_type){
	if(!yffmeter_type) return window.top.SDDef.YFF_CARDMTYPE_KE001;
	if(yffmeter_type <= window.top.SDDef.YFF_METERTYPE_XCB){
		return window.top.SDDef.YFF_CARDMTYPE_KE001;
	}
	else{
		return window.top.SDDef.YFF_CARDMTYPE_KE006;
	}
}

//低压中表类型是否为13版
function is2013Meter(yffmeter_type){
	if(!yffmeter_type)	return false;
	if(yffmeter_type <= window.top.SDDef.YFF_METERTYPE_XCB)	return false;
	else return true;
}

