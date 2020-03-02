var cons_para = {
	username			: "username",		//客户名称
	userno				: "userno",			//客户编号
	useraddr			: "useraddr",		//客户地址
	tel					: "tel",			//联系电话
	identityno			: "identityno",		//身份证号
	cus_state			: "cus_state",		//客户状态
	village				: "village",		//自然村
	jsyhm				: "jsyhm",			//结算客户名
	yhfl			    : "yhfl",			//客户分类
	
	card_no				: "card_no",		//购电卡号
	card_state			: "card_state",		//购电卡状态代码
	card_state_desc		: "card_state_desc",//购电卡状态描述
	area				: "area",			//所属片区
	area_no				: "area_no",		//区域号

	
	now_remain			: "now_remain",		//当前余额
	buy_times			: "buy_times",		//购电次数
	pay_money			: "pay_money",		//缴费金额
	othjs_money			: "othjs_money",	//结算金额
	zb_money			: "zb_money",		//追补金额
	all_money			: "all_money",		//总金额

	rtuonline_img		: "rtuonline_img",	//终端在线/离线图片
	rtuonline_sp		: "rtuonline_sp",	//终端在线/离线说明

	//隐藏域
	area_id				: "area_id",		//区域ID
	farmer_id			: "farmer_id"		//农排ID
};

function setConsValue(json, null_flag){
	if(null_flag){
		$("#" + cons_para.userno).html("");
		$("#" + cons_para.username).html("");
		$("#" + cons_para.tel).html("");
		$("#" + cons_para.useraddr).html("");
		$("#" + cons_para.cus_state).html("");
		$("#" + cons_para.identityno).html("");
		$("#" + cons_para.village).html("");
		$("#" + cons_para.jsyhm).html("");
		$("#" + cons_para.yhfl).html("");
		
		$("#" + cons_para.card_no).html("");
		$("#" + cons_para.card_state_desc).html("");
		$("#" + cons_para.area).html("");	
		$("#" + cons_para.area_no).html("");
		$("#" + cons_para.rtuonline_img).html("");
		$("#" + cons_para.rtuonline_sp).html("");
		
		$("#" + cons_para.now_remain).html("");
		$("#" + cons_para.buy_times).html("");
		$("#" + cons_para.pay_money).val("");
		$("#" + cons_para.othjs_money).val("");
		$("#" + cons_para.zb_money).val("");
		$("#" + cons_para.all_money).html("");
		
		//隐藏域赋值
		$("#" + cons_para.area_id).val("");
		$("#" + cons_para.farmer_id).val("");
	}
	else{
		var colors = "<b style='color:#800000'>";
		var colore = "</b>";

		$("#" + cons_para.userno).html(json.userno);
		$("#" + cons_para.username).html(json.username);
		$("#" + cons_para.tel).html(json.tel);
		$("#" + cons_para.useraddr).html(json.useraddr);
		$("#" + cons_para.cus_state).html(json.cus_state);
		$("#" + cons_para.identityno).html(json.identityno);
		$("#" + cons_para.village).html(json.village);
		$("#" + cons_para.jsyhm).html(json.jsyhm);
		$("#" + cons_para.yhfl).html(json.yhfl);
		
		$("#" + cons_para.card_no).html(json.card_no);
		$("#" + cons_para.card_state_desc).html(json.card_state_desc);
		$("#" + cons_para.area).html(json.area);
		$("#" + cons_para.area_no).html(json.area_code);
		
		$("#" + cons_para.now_remain).html(json.now_remain);
		$("#" + cons_para.buy_times).html(json.buy_times);
//		$("#" + cons_para.pay_money).val(json.pay_money);
//		$("#" + cons_para.othjs_money).val(json.othjs_money);
//		$("#" + cons_para.zb_money).val(json.zb_money);
//		$("#" + cons_para.all_money).html(json.all_money);

		//隐藏域赋值
		$("#" + cons_para.area_id).val(json.areaid);
		$("#" + cons_para.farmer_id).val(json.farmerid);		
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
	var yff_grid_title = "操作日期,操作类型,缴费金额(元),结算金额(元),追补金额(元),总金额(元),购电次数,流水号,缴费方式,操作员,&nbsp;";
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("150,80,80,80,80,80,80,120,80,80,*")
	mygrid.setColAlign("center,center,left,right,right,right,right,right,left,left")
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.init();
	mygrid.setSkin("light");
	//mygrid.setColumnHidden(7, true);//不用 断电金额
}

function getRecord(area_id, farmer_id, div_id, num) {
	if(div_id == undefined) div_id = "gridbox";
	if(num == undefined) {
		var div_height = $("#" + div_id).height();
		var tmp_num = Math.floor((div_height / 23));
		if(tmp_num < 5) num = 5;
		else num = tmp_num;
	}
	
	mygrid.clearAll();
	var param = "{area_id:" + area_id + ",farmer_id:" + farmer_id + ",top_num: " + num + "}"; 
	$.post( def.basePath + "ajax/actCommon!getNpYFFRecs.action",{result : param}, function(data) {
		if (data.result != "") {
			var jsdata = eval('(' + data.result + ')');
			mygrid.parse(jsdata,"json");
			$("#infoNum").val(data.ydInfoNo);//查询缴费记录的条数，把它设置为用电信息的查询条数
			$("#ydInfo").css("visibility","visible");
		}
	});
}

function _showYDInfo(area_id, farmer_id, infoNum){
	if(!infoNum) {
		infoNum = 20;
	}
	
	modalDialog.height 	= 350;
	modalDialog.width 	= 650;
	modalDialog.url 	= def.basePath + "jsp/np/dialog/ydInfo.jsp";
	modalDialog.param 	= {
		area_id : area_id,
		farmer_id : farmer_id,
		infoNum : infoNum
	};
	var o = modalDialog.show();
}


//冲正时查询上次缴费信息
var rever_info = null;
var last_jf_id = ["last_je", "last_zbje", "last_jsje", "last_tt"];
function lastPayInfo(area_id, farmer_id){
	$.post(def.basePath + "ajax/actCommon!lastPayInfoNP.action", 
		{result : "{area_id : " + area_id + ", farmer_id : " + farmer_id + "}"},
	function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			
			//为上次缴费信息赋值
			$("#" + last_jf_id[0]).val(json.pay_money);
			$("#" + last_jf_id[1]).val(json.zb_money);
			$("#" + last_jf_id[2]).val(json.othjs_money);
			$("#" + last_jf_id[3]).val(json.all_money);
			
			rever_info = {};
			rever_info.date = json.op_date;
			rever_info.time = json.op_time;
			rever_info.last_wastno = json.last_wastno;	//原交易流水号
			
		} else {
			alert("无可冲正的记录!");
		}
	});
}