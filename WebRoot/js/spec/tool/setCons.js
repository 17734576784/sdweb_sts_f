var cons_para = {
	fee_unit			: "fee_unit",
	username			: "username",
	userno				: "userno",
	useraddr			: "useraddr",
	tel					: "tel",
	factory				: "factory",
	blv					: "blv",
	rtu_model			: "rtu_model",
	switch_type			: "switch_type",
	plus_width			: "plus_width",
	dpfs				: "dpfs",
	mcfs				: "mcfs",
//	plus_width			: "mckd",	//脉冲宽度不使用mckd 应该为plus_width
	rtu_id				: "rtu_id",
	zjg_id				: "zjg_id",
	cacl_type_desc		: "cacl_type_desc",
	feectrl_type		: "feectrl_type",
	pay_type			: "pay_type",
	pay_type_desc		: "pay_type_desc",
	feeproj_id			: "feeproj_id",
	feeproj_detail		: "feeproj_detail",
	feeproj_desc		: "feeproj_desc",
	yffalarm_id			: "yffalarm_id",
	yffalarm_desc		: "yffalarm_desc",
	yffalarm_alarm1		: "yffalarm_alarm1",
	yffalarm_alarm2		: "yffalarm_alarm2",
	yffalarm_detail		: "yffalarm_detail",
	feeproj_reteval		: "feeproj_reteval",
	
	cus_state			: "cus_state",
	buy_times			: "buy_times",
	start_yff_flag		: "start_yff_flag"
};

function setConsValue(json, null_flag){
	if(null_flag){
		$("#" + cons_para.userno).html("");
		$("#" + cons_para.username).html("");
		$("#" + cons_para.tel).html("");
		$("#" + cons_para.useraddr).html("");
		$("#" + cons_para.fee_unit).html("");
		$("#" + cons_para.cus_state).html("");
		$("#" + cons_para.rtu_model).html("");
		$("#" + cons_para.factory).html("");
		$("#" + cons_para.blv).html("");
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
		$("#" + cons_para.zjg_id).val("");
		$("#" + cons_para.yffalarm_alarm1).val("");
		$("#" + cons_para.yffalarm_alarm2).val("");
		
		$("#" + cons_para.plus_width).attr("disabled", true);
		$("#" + cons_para.plus_width).val("");
		$("#" + cons_para.dpfs).attr('checked',false);
		$("#" + cons_para.mcfs).attr('checked',false);
		$("#" + cons_para.start_yff_flag).attr("checked",false);
		
	}else{
		var colors = "<b style='color:#800000'>";
		var colore = "</b>";
		
		$("#" + cons_para.userno).html(json.userno);
		$("#" + cons_para.username).html(json.username);
		$("#" + cons_para.tel).html(json.tel);
		$("#" + cons_para.useraddr).html(json.useraddr);
		$("#" + cons_para.fee_unit).html(json.fee_unit);
		$("#" + cons_para.cus_state).html(json.cus_state);
		$("#" + cons_para.rtu_model).html(json.rtu_model);
		$("#" + cons_para.factory).html(json.factory);
		$("#" + cons_para.blv).html(json.blv);
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
		$("#" + cons_para.buy_times).val(json.buy_times);
		$("#" + cons_para.rtu_id).val(json.rtu_id);
		$("#" + cons_para.zjg_id).val(json.zjg_id);
		$("#" + cons_para.yffalarm_alarm1).val(json.yffalarm_alarm1);
		$("#" + cons_para.yffalarm_alarm2).val(json.yffalarm_alarm2);
		$("#" + cons_para.feeproj_reteval).val(json.feeproj_reteval);
		
		
		if(document.getElementById("sfbd")) {		//算费表底
			$("#sfbd_sj").html(json.calc_bdymd);
			$("#sfbd").html("总:"+ json.calc_zyz.split(",")[0] +
	                " 尖:"+ json.calc_zyj.split(",")[0]+
	                " 峰:"+ json.calc_zyf.split(",")[0]+
	                " 平:"+ json.calc_zyp.split(",")[0]+
                    " 谷:"+ json.calc_zyg.split(",")[0]+
                    " 无功总:"+ json.calc_zwz.split(",")[0]
			       );
		}
		
		if(document.getElementById(cons_para.dpfs)){
			//jack 20120811不管电平方式还是脉冲方式都显示脉冲宽度，仅当设置时电平发送0，脉冲按照实际发送。
			if(json.switch_type == 0){	//电平方式
				$("#" + cons_para.dpfs).attr('checked',true);
				//$("#" + cons_para.plus_width).val("0");
				$("#" + cons_para.plus_width).val(json.plus_time);
				$("#" + cons_para.plus_width).attr("disabled", true);
			}else{						//脉冲方式
				$("#" + cons_para.mcfs).attr('checked',true);
				$("#" + cons_para.plus_width).attr("disabled", false);
				$("#" + cons_para.plus_width).val(json.plus_time);
			}
//			$("#" + cons_para.dpfs).click(function(){$("#" + cons_para.plus_width).attr("disabled",true).val("")});
//			$("#" + cons_para.mcfs).click(function(){$("#" + cons_para.plus_width).attr("disabled",false)});
			$("#" + cons_para.dpfs).click(function(){$("#" + cons_para.plus_width).attr("disabled",true)});
			$("#" + cons_para.mcfs).click(function(){$("#" + cons_para.plus_width).attr("disabled",false)});
			$("#" + cons_para.plus_width).keyup(function(){if(isNaN(this.value))this.value=0;});
			$("#" + cons_para.start_yff_flag).attr("checked",true);
		}
	}
	
}

var alarm_type = -1;		//报警方式：固定0，比例1
var money_limit = -1;		//囤积限值

function getAlarmValue(value){
	alarm_type = rtnValue.type;
}

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

function getMoneyLimit(value){
	money_limit = parseFloat(rtnValue.moneyLimit);
}

var date_time = null;//冲正时查询上次缴费信息
var last_jf_id = ["last_je", "last_zbje", "last_jsje", "last_tt"];

function lastPayInfo(){
	$.post(def.basePath + "ajax/actCommon!lastPayInfoGY.action", 
		{result : "{rtu_id : " + rtnValue.rtu_id + ", zjg_id : " + rtnValue.zjg_id + "}"},
	function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			lastpayRcd = json;
			//为上次缴费信息赋值
			$("#" + last_jf_id[0]).val(json.pay_money);
			$("#" + last_jf_id[1]).val(json.zb_money);
			$("#" + last_jf_id[2]).val(json.othjs_money);
			$("#" + last_jf_id[3]).val(json.all_money);
			
			date_time = {};
			date_time.date = json.op_date;
			date_time.time = json.op_time;
			date_time.last_wastno = json.last_wastno;	//原交易流水号
		} else {
			alert("无可冲正的记录!");
			
		}
	});
}

//缴费记录 (效仿低压)
var mygrid = null;
function initGrid(div_id) {
	if(div_id == undefined) div_id = "gridbox";
	mygrid = new dhtmlXGridObject(div_id);
	var yff_grid_title ='操作日期,操作类型,缴费金额(元),追补金额(元),结算金额(元),总金额(元),断电值,报警值1,报警值2,购电次数,流水号,缴费方式,操作员,&nbsp;';
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("150,80,80,80,80,80,80,80,80,80,150,80,80,*");
	mygrid.setColAlign("center,center,right,right,right,right,right,right,right,right,left,left,left");
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.init();
	mygrid.setSkin("light");
	mygrid.setColumnHidden(6, true);//隐藏断电金额
}

function getGyYffRecs(rtu_id, zjg_id, div_id, num){//缴费记录
	if(div_id == undefined) div_id = "gridbox";
	if(num == undefined) {
		var div_height = $("#" + div_id).height();
		var tmp_num = Math.floor((div_height / 23));
		if(tmp_num < 5) num = 5;
		else num = tmp_num;
	}
	mygrid.clearAll();
	var param = "{rtu_id:" + rtu_id + ",zjg_id:" + zjg_id + ",top_num: " + num + "}"; 
	$.post( def.basePath + "ajax/actCommon!getGyYFFRecs.action",{result:param},	function(data) {			    	//回传函数
		if (data.result != "") {
			jsdata = eval('(' + data.result + ')');
			mygrid.parse(jsdata,"json");
		}
	});
}
