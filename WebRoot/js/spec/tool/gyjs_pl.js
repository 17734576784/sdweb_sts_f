var jsonCons   = null;

var cols={//所在列
	cbDayYe		: 4,  //预收金额
	bdyg		: 5,  //有功表底
	bdwg		: 6,  //无功表底
	matchflag	: 7,  //档案匹配
	consdesc	: 8,  //用电系统中的客户名称
	othjsmoney 	: 10, //结算金额--主站、金额时显示
	alarmcode	: 11, //报警止码
	allmoney 	: 12, //总金额	
	alarmval1  	: 13, //yffalarmpara报警值1所在列--主站、金额时显示
	alarmval2  	: 14, //yffalarmpara报警值2所在列--主站、金额时显示
	buynum		: 15, //购电次数
	nowremain	: 16, //当前余额
	buydl		: 17, //购电量
	paybmc		: 18, //表码差
	shutdownval : 19, //断电止码
	ddbd		: 20, //断电表底
	dqbd		: 21, //当前表底
	opresult	: 22, //操作结果
	cacltype 	: 23, //计费方式
	feectrltype : 24, //费控方式
	yffalarmid 	: 25, //报警方案
	cbdayhour	: 26, //抄表日
	cusstate   	: 27, //用户状态
	paytype		: 28, //
	consid		: 29, //conspara.id
	lastshutval	: 30, //上次断电表底，zjgstate.shutdownval
	blv			: 31, //倍率
	jbf			: 32, //基本费
	plustime	: 33, //脉冲宽度
	alarmvalp1  : 34, //yffalarmpara报警值1所在列
	alarmvalp2  : 35, //yffalarmpara报警值2所在列
	alarmtype	: 36, //yffalarmpara报警方式所在列
	feeprojid   : 37,
	feeprojtype : 38,
	moneyLimit	: 39,
	feeprojreteval: 40,
	rtuid		: 41, //
	zjgid		: 42, 
	mpnum		: 43, //
	mpids		: 44,
	zfflag		: 45,
	
	nocol:1//没用的
};

var matchInfo  = ["有匹配用户信息,可以结算。",
				  "没有找到匹配的档案。",
				  "有匹配用户信息。费控状态错误或未开户。",
				  "有匹配，无表底信息或信息不全。",
				  "有匹配，余额信息错误。",
				  "有匹配，费控状态不是本页面对应的类型。",
				  "有匹配，没有配置抄表日，请先配置抄表日再结算。",
				  "有匹配，用户状态不是正常态，不能结算。"
				  ];
var bdyg   	   = [0, 0, 0], bdwg = [0, 0, 0], 
	mis_bdj	   = [0, 0, 0], mis_bdf = [0, 0, 0],
	mis_bdp    = [0, 0, 0], mis_bdg = [0, 0, 0];
var mp_id;
var jsDate 	   = 0; //抄表日yyyymmdd：excel的yyyymm + zjgpay_para中的cb_dayhour的dd
var jsTime 	   = 0; //抄表日hhmm ：zjgpay_para中的cb_dayhour的hh + "00"
var cbDayYe    = 0; //抄表日余额:excel中的余额。
var zfFlag	   = "";

var mis_jsflag = false;	 //计算出的与实际用的是否相同的标志
var mis_jsje = 0, mis_jsbmc = 0, mis_gdl = 0;					//实际用的
var mis_calcu_val = 0, mis_calcu_gdl = 0, mis_calcu_bmc = 0;	//计算出的

var jsje_bd	   = 0;//结算金额-表底方式	

var cacl_type, feectrl_type;		//计费方式,费控方式
var consIdx = null; 

var curbd_info = new Array();


$(document).ready(function(){
	initCbym();
	initGridCons();
	
	$("#upload_bdye").click(function(){showUpload()});
	$("#btnRemain").click(function(){remainSum()});
	$("#btnPay").click(function(){payMoney();});
	
});


function changepage(pagetype){//
	showhideColumns();
	$("#btnRemain").attr("disabled",true);		
	mygridCons.clearAll();
	parsegrid_spec();
	
}

function initCbym() {
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	if(month < 10) month = "0" + month;
	$("#cb_ym").val(year + "年" + month + "月");
}

function initGridCons(){ 
	var yff_grid_title = "<img src='"+def.basePath + "images/grid/imgs/item_chk0.gif' onclick='selectAllOrNone(this);' />,序号,SG186参数,#cspan,#cspan,#cspan,#cspan,匹配档案,售电子系统参数,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,操作结果";
	var yffatachheader="#rspan,#rspan,用户名称,户号,预收金额,有功(总),无功(总),#rspan,用户名称,用户状态,结算金额,报警止码,总金额,报警金额1(元),报警金额2(元),购电次数,当前余额,购电量,表码差,断电止码,断电表底,当前表底,#rspan";
	mygridCons.setImagePath(def.basePath + "images/grid/imgs/");
	mygridCons.setHeader(yff_grid_title);
	mygridCons.attachHeader(yffatachheader);	
	mygridCons.setInitWidths("30,40,180,120,80,80,80,80,180,80,80,80,80,100,100,80,80,80,80,80,80,80,380");
	mygridCons.setColAlign("center,center,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left");
	mygridCons.setColTypes("ch,ro,ro,ro,ro,ro,ro,coro,ro,ro,ed,ed,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygridCons.enableTooltips("false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false");
	mygridCons.init();
	mygridCons.setSkin("light");	
	for(var i = 0; i < matchInfo.length;i++){
		mygridCons.getCombo(7).put(i,matchInfo[i]);
	} 
	showhideColumns();
}

function showhideColumns(){
	if(old_i==3){//表底
		mygridCons.setColumnHidden(cols.othjsmoney,		true);	
		mygridCons.setColumnHidden(cols.alarmval1,		true);	
		mygridCons.setColumnHidden(cols.alarmval2,		true);	
		mygridCons.setColumnHidden(cols.nowremain,		true);	
		mygridCons.setColumnHidden(cols.alarmcode,		false);
		mygridCons.setColumnHidden(cols.buydl,			false);
		mygridCons.setColumnHidden(cols.paybmc,			false);
		mygridCons.setColumnHidden(cols.ddbd,			false);
		mygridCons.setColumnHidden(cols.dqbd,			false);
		mygridCons.setColumnHidden(cols.shutdownval,	false);
		
	}else{
		mygridCons.setColumnHidden(cols.othjsmoney,		false);	
		mygridCons.setColumnHidden(cols.alarmval1,		false);	
		mygridCons.setColumnHidden(cols.alarmval2,		false);	
		mygridCons.setColumnHidden(cols.nowremain,		false);
		mygridCons.setColumnHidden(cols.alarmcode,		true);
		mygridCons.setColumnHidden(cols.buydl,			true);
		mygridCons.setColumnHidden(cols.paybmc,			true);
		mygridCons.setColumnHidden(cols.ddbd,			true);
		mygridCons.setColumnHidden(cols.dqbd,			true);
		mygridCons.setColumnHidden(cols.shutdownval,	true);
	}
	
	
	
}  

function parsegrid_spec(){
	mygridCons.clearAll();
	mygridCons.parse(jsonCons, "", "json");
	//mygridCons.selectRow(0);
	for(var i=0;i<mygridCons.getRowsNum(); i++){
		curbd_info[i]= new Array();
	}
	setChkDisabled();
}

//显示弹出窗口--上传页面
function showUpload(){
	modalDialog.height = 300;
	modalDialog.width  = 400;
	modalDialog.param  = old_i;//1:主站;2:金额;3:表底。	
	modalDialog.url    = def.basePath + "jsp/spec/dialog/uploadSG186BdYe.jsp";
	var o = modalDialog.show();
	
	if(!o && o!= ""){
		return;
	}	
	jsonCons = eval('(' + o + ')');
	parsegrid_spec();			
}


function selectAllOrNone(checked) {      //全选&全不选
	var flag = false;
		if (checked.src.indexOf("item_chk0.gif") != -1) {
			flag = true;
			checked.src = def.basePath + 'images/grid/imgs/item_chk1.gif';
		} else if (checked.src.indexOf("item_chk1.gif") != -1) {
			flag = false;
			checked.src = def.basePath +  'images/grid/imgs/item_chk0.gif';
		}
		for ( var i = 0; i < mygridCons.getRowsNum(); i++) {
			if(mygridCons.cells2(i, cols.matchflag).getValue() == 0){//可以结算的行
				mygridCons.cells2(i, 0).setValue(flag);
			}
		}
}

function setChkDisabled(){
	//showhideColumns()
	switch(old_i){
		case 1:		//主站费控
			cacl_type = 1 ;
			feectrl_type = 1;
			break;
		case 2://金额
			cacl_type = 1 ;
			feectrl_type = 0;
			break;
		case 3://表底
			cacl_type = 2 ;
			feectrl_type = 0;
			break;
	}	
	var jsflag=false;
	for ( var i = 0; i < mygridCons.getRowsNum(); i++) {//alert(mygridCons.cells2(i, cols.matchflag).getValue())
		if(mygridCons.cells2(i, cols.matchflag).getValue() != 0){//不能结算的禁用了。
			mygridCons.cells2(i, 0).setDisabled(true);	
		}else{
//			alert(jsonCons.rows[i].data[cols.cacltype]+","+ cacl_type +","+jsonCons.rows[i].data[cols.feectrltype] +","+ feectrl_type)
			if(jsonCons.rows[i].data[cols.cacltype] != cacl_type || jsonCons.rows[i].data[cols.feectrltype] != feectrl_type){
				mygridCons.cells2(i, 0).setDisabled(true);	
				mygridCons.cells2(i, cols.matchflag).setValue(5);
			}else if(jsonCons.rows[i].data[cols.cbdayhour] == "" || jsonCons.rows[i].data[cols.cbdayhour] == "null"){
				mygridCons.cells2(i, 0).setDisabled(true);	
				mygridCons.cells2(i, cols.matchflag).setValue(6);
			}else if(jsonCons.rows[i].data[cols.cusstate] != 1 ){
				mygridCons.cells2(i, 0).setDisabled(true);	
				mygridCons.cells2(i, cols.matchflag).setValue(7);
			}
			else{
				mygridCons.cells2(i, 0).setDisabled(false);	jsflag=true;
			}
		}		
	}
	
	if(jsflag)$("#btnRemain").attr("disabled",false);			
}

//向数据库中缴费
function pay(i,consIdx) {
	if(i>consIdx.length) return;
	var idx=consIdx[i];

	var params = {};	
	params.rtu_id 		= jsonCons.rows[idx].data[cols.rtuid];
	params.zjg_id 		= jsonCons.rows[idx].data[cols.zjgid];
	params.myffalarmid 	= jsonCons.rows[idx].data[cols.yffalarmid];
	params.paytype 		= jsonCons.rows[idx].data[cols.paytype];
	params.buynum 		= jsonCons.rows[idx].data[cols.buynum];
	
	params.alarm_val1	= mygridCons.cells2(idx,cols.alarmval1).getValue();
	params.alarm_val2	= mygridCons.cells2(idx,cols.alarmval2).getValue();;
		
	if(old_i == 1 && !check_jsye(idx)) {
		return;
	}
		
	params.pay_money 	= 0;
	params.othjs_money 	=  mygridCons.cells2(idx,cols.othjsmoney).getValue();
	if(mis_jsflag) {
		params.othjs_money = mis_jsje;
		params.misjs_money = mis_calcu_val;
	}
	
	params.zb_money 	= 0;
	params.othjs_money  = round(params.othjs_money, def.point);
	params.all_money    = params.othjs_money;
	
	var showmis_jsje    = round(mis_jsje, def.point);

	params.date = 0;
	params.time = 0;

	params.buy_dl = 0;
	params.pay_bmc = 0;
	params.shutdown_bd = 0;

	var t_str = $("#cb_ym").val();
	var str_cb_ym = t_str.substring(0,4) + t_str.substring(5,7); 
	var dt = ("000" + jsonCons.rows[idx].data[cols.cbdayhour]);
	dt = dt.substring(dt.length - 4,dt.length);
	var jsDate1 = dt.substring(0,2); 
	var jsTime1 = dt.substring(2,4);
	var cb_ymd    = str_cb_ym + jsDate1;
		 
	params.fxdf_ym = str_cb_ym;
	params.cons_id = jsonCons.rows[idx].data[cols.consid];
	params.cons_desc = jsonCons.rows[idx].data[cols.consdesc];
	params.pay_type = jsonCons.rows[idx].data[cols.paytype];
		
	params.jsflag = 0;
	if(mis_jsflag) {
		params.jsflag = 1;
		if(old_i == 3){//按表底
			params.buy_dl   = mis_gdl;
			params.pay_bmc  = mis_jsbmc;
			params.misjs_bmc= mis_calcu_bmc;
			params.cur_bd   = mygridCons.cells2(idx,cols.dqbd).getValue();//$("#dqbd").html();
		} else {
			params.pay_bmc = 0;
			params.misjs_bmc = 0;
			params.cur_bd = 0;
		}
	} else {
		if(old_i == 3){//按表底
			params.buy_dl = mygridCons.cells2(idx,cols.buydl).getValue();//$("#buy_dl").html();
			params.pay_bmc = mygridCons.cells2(idx,cols.paybmc).getValue();//$("#pay_bmc").html();
			params.misjs_bmc = mygridCons.cells2(idx,cols.paybmc).getValue();//$("#pay_bmc").html();
			params.cur_bd = mygridCons.cells2(idx,cols.dqbd).getValue();//$("#dqbd").html();
		} else {
			params.pay_bmc = 0;
			params.misjs_bmc = 0;
			params.cur_bd = 0;
		}
	}	 
	 
	params.lastala_val1 = jsonCons.rows[idx].data[cols.alarmval1];
	params.lastala_val2 = jsonCons.rows[idx].data[cols.alarmval2];
	params.lastshut_val = jsonCons.rows[idx].data[cols.lastshutval];
	 
	if(old_i == 3) {//按表底
		params.newala_val1 = mygridCons.cells2(idx,cols.alarmcode).getValue();//$("#alarm_code").val();
		params.newala_val2 = params.newala_val1;
		params.newshut_val = mygridCons.cells2(idx,cols.shutdownval).getValue();//$("#shutdown_bd").html();
	} else {
		params.newala_val1 = mygridCons.cells2(idx,cols.alarmval1).getValue();
		params.newala_val2 = mygridCons.cells2(idx,cols.alarmval2).getValue();
		params.newshut_val = parseFloat(params.lastshut_val) + parseFloat(params.all_money);
	}
	 
	params.date = cb_ymd;
	params.time = jsTime1;
	 
	bdyg 	= jsonCons.rows[idx].data[cols.bdyg].split("_");	//mis有功总表底
	bdwg 	= jsonCons.rows[idx].data[cols.bdyg].split("_");	//mis无功总表底
	mp_id	= jsonCons.rows[idx].data[cols.mpids].split("_");
	zfFlag 	= jsonCons.rows[idx].data[cols.zfflag];
	 
	for(var i = 0; i < 3; i++){ 
		eval("params.mp_id" + i + "=mp_id[" + i + "]");
		 
		if(zfFlag.substring(i, i + 1) == "0"){	//正向
			eval("params.bd_zy"  + i + "=bdyg[" + i + "]");
			eval("params.bd_zyj" + i + "=0");
			eval("params.bd_zyf" + i + "=0");
			eval("params.bd_zyp" + i + "=0");
			eval("params.bd_zyg" + i + "=0");
			eval("params.bd_zw"  + i + "=bdwg[" + i + "]");
			eval("params.bd_fy"  + i + "=0");
			eval("params.bd_fyj" + i + "=0");
			eval("params.bd_fyf" + i + "=0");
			eval("params.bd_fyp" + i + "=0");
			eval("params.bd_fyg" + i + "=0");
			eval("params.bd_fw"  + i + "=0");
		}else{
			eval("params.bd_zy"  + i + "=0");
			eval("params.bd_zyj" + i + "=0");
			eval("params.bd_zyf" + i + "=0");
			eval("params.bd_zyp" + i + "=0");
			eval("params.bd_zyg" + i + "=0");
			eval("params.bd_zw"  + i + "=0");
			eval("params.bd_fy"  + i + "=bdyg[" + i + "]");
			eval("params.bd_fyj" + i + "=0");
			eval("params.bd_fyf" + i + "=0");
			eval("params.bd_fyp" + i + "=0");
			eval("params.bd_fyg" + i + "=0");
			eval("params.bd_fw"  + i + "=bdwg[" + i + "]");
		}
	} 
	 
	var json_str = jsonString.json2String(params); 
	loading.loading();
	
	$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
		{
			userData1		: params.rtu_id,
			zjgid 			: params.zjg_id,
			gsmflag 		: 0,
			gyOperRejs 		: json_str,
			userData2 		: ComntUseropDef.YFF_GYOPER_REJS
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				mygridCons.cells2(idx,cols.opresult).setValue("结算缴费成功");
				mygridCons.cells2(idx,0).setValue(0);
				mygridCons.cells2(idx, 0).setDisabled(true);	
				pay(++i,consIdx);
			}
			else {
				mygridCons.cells2(idx,cols.opresult).setValue(data.err_strinfo);
			}
		}
	);
}

function check_jsye(idx) {
	
	mis_jsflag = false;
	
	var othjs_money = mygridCons.cells2(idx,cols.othjsmoney).getValue();
	var alarm_val1 = mygridCons.cells2(idx,cols.alarmval1).getValue();
	
	//需要加一个判断，结算金额在囤积限值之间.-rtnValue.moneyLimit, rtnValue.moneyLimit
	
	//结算金额为负时，其绝对值需小于(当前金额-报警值)
	if(parseFloat(othjs_money) < 0) {
		var now_remain = jsonCons.rows[idx].data[cols.nowremain];
		if(now_remain == "") now_remain = 0;
		now_remain = parseFloat(now_remain);
		if(now_remain < alarm_val1) {
			mygridCons.cells2(idx,cols.opresult).setValue("当前金额小于报警值1,不能结算");
			return false;
		}
		if(now_remain + parseFloat(othjs_money) < alarm_val1) {
			othjs_money = -(now_remain - parseFloat(alarm_val1));
			mis_jsje = othjs_money;
			mis_jsflag = true;
		}
	} else {
		var jsje = round(othjs_money, def.point);
		if(mis_calcu_val != jsje) {
			mis_jsje = jsje;
			mis_jsflag = true;
		}
	}
	
	return true;
}

//结算
function payMoney(){
	
	switch(old_i){
		case 1:		//主站费控
			var idxstr = "";   
			for (var idx = 0; idx < mygridCons.getRowsNum();idx++){
				if (mygridCons.cells2(idx,0).getValue()==1){
					idxstr += "," + idx;
					mygridCons.cells2(idx,cols.opresult).setValue("");
				}			  
			} 
			if(idxstr.length>0){
				idxstr=idxstr.substring(1);
				idxs=idxstr.split(",");
				pay(0,idxs);
			}else{
				alert("请选择要结算的记录");
			}
			break;
		case 2://金额
		case 3://表底
			pay_je_bd(fk,0,idx);
			break;
	}	
	
	
}

//获取结算金额
function remainSum(){
	var idxstr = "";   
	for (var idx = 0; idx < mygridCons.getRowsNum();idx++){
		if (mygridCons.cells2(idx,0).getValue()==1){
			idxstr += "," + idx;
			mygridCons.cells2(idx,cols.opresult).setValue("");
		}			  
	} 
	if(idxstr.length>0){
		idxstr=idxstr.substring(1);
		idxs=idxstr.split(",");		
		switch(old_i) {
		case 1://主站
			remain_zz(0,idxs);
			break;
		case 2://金额
			callRtuRemain("je",0,idxs);
			break;
		case 3://表底
			callRtuRemain("bd",0,idxs);
			break;
		default :
			break;
		}
	}else{
		alert("请选择要结算的记录");
	}

}

//主站方式获取余额
function remain_zz(i,consIdx) {//idx：consIdx的 index；consIdx：选中的行index。
	if(i>consIdx.length) return;
	var idx=consIdx[i];
	if(jsonCons.rows[idx].data[cols.matchflag] != 0 || jsonCons.rows[idx].data[cols.cusstate] != 1  || jsonCons.rows[idx].data[cols.cbdayhour] == "" ) return;
	var t_str = $("#cb_ym").val();
	var str_cb_ym = t_str.substring(0,4) + t_str.substring(5,7); 
	
	var dt = ("000" + jsonCons.rows[idx].data[cols.cbdayhour]);
	dt = dt.substring(dt.length - 4,dt.length);
	var jsDate1 = dt.substring(0,2); 
	var jsTime1 = dt.substring(2,4);
	var cb_ymd    = str_cb_ym + jsDate1;
	var params = {};
	params.userop_id = ComntUseropDef.YFF_GYOPER_GETJSBCREMAIN;
	params.rtu_id = jsonCons.rows[idx].data[cols.rtuid];
	params.zjg_id = jsonCons.rows[idx].data[cols.zjgid];
	params.date = cb_ymd;
	params.time = jsTime1;
	cbDayYe = jsonCons.rows[idx].data[cols.cbDayYe];
	bdyg 	= jsonCons.rows[idx].data[cols.bdyg].split("_");	//mis有功总表底
	bdwg 	= jsonCons.rows[idx].data[cols.bdyg].split("_");	//mis无功总表底
	mp_id	= jsonCons.rows[idx].data[cols.mpids].split("_");
	zfFlag 	= jsonCons.rows[idx].data[cols.zfflag];
	for(var i = 0; i < 3; i++){
		eval("params.mp_id" + i + "=mp_id[" + i + "]");
		
		if(zfFlag.substring(i, i + 1) == "0"){	//正向
			eval("params.bd_zy"  + i + "=bdyg[" + i + "]");
			eval("params.bd_zyj" + i + "=0");
			eval("params.bd_zyf" + i + "=0");
			eval("params.bd_zyp" + i + "=0");
			eval("params.bd_zyg" + i + "=0");
			eval("params.bd_zw"  + i + "=bdwg[" + i + "]");
			eval("params.bd_fy"  + i + "=0");
			eval("params.bd_fyj" + i + "=0");
			eval("params.bd_fyf" + i + "=0");
			eval("params.bd_fyp" + i + "=0");
			eval("params.bd_fyg" + i + "=0");
			eval("params.bd_fw"  + i + "=0");
		}else{
			eval("params.bd_zy"  + i + "=0");
			eval("params.bd_zyj" + i + "=0");
			eval("params.bd_zyf" + i + "=0");
			eval("params.bd_zyp" + i + "=0");
			eval("params.bd_zyg" + i + "=0");
			eval("params.bd_zw"  + i + "=0");
			eval("params.bd_fy"  + i + "=bdyg[" + i + "]");
			eval("params.bd_fyj" + i + "=0");
			eval("params.bd_fyf" + i + "=0");
			eval("params.bd_fyp" + i + "=0");
			eval("params.bd_fyg" + i + "=0");
			eval("params.bd_fw"  + i + "=bdwg[" + i + "]");
		}
	}
	var json_str=jsonString.json2String(params);
	loading.loading();
	$.post(
		def.basePath + "ajaxoper/actOperGy!taskProc.action", 
		{
			userData1        :  params.rtu_id,
			zjgid 			 :  params.zjg_id,
			userData2        :  params.userop_id,
			gyOperGetJSBCRemain  :  json_str,
			gsmflag          :  0
		},
		function(data){
			loading.loaded();
			if(data.gyOperGetJSBCRemain == ""){
				mygridCons.cells2(idx,cols.opresult).setValue(data.err_strinfo);
				return;
			}
			var json = eval("(" + data.gyOperGetJSBCRemain + ")");
			if(json.remain_val == ""){
				mygridCons.cells2(idx,cols.opresult).setValue("数据出错");
			}else{
				var dqje = round(json.remain_val,def.point);
				mis_calcu_val = round(parseFloat(jsonCons.rows[idx].data[cols.cbDayYe]) - dqje,def.point);
				mygridCons.cells2(idx,cols.othjsmoney).setValue(mis_calcu_val);
				mygridCons.cells2(idx,cols.allmoney).setValue(mis_calcu_val);
				mygridCons.cells2(idx,cols.opresult).setValue("操作成功");
				$("#btnPay").attr("disabled",false);
				params.yffalarm_alarm1 = jsonCons.rows[idx].data[cols.alarmvalp1];
				params.yffalarm_alarm2 = jsonCons.rows[idx].data[cols.alarmvalp2];
				setAlarmValue2Cell(params.yffalarm_alarm1, params.yffalarm_alarm2, mis_calcu_val,idx)
	
			}
			remain_zz(++i,consIdx);
		}
	);
}

function setAlarmValue2Cell(v1, v2, zje,idx){//alert(v1+","+ v2+","+ zje+","+cols.alarmtype)
	 var alarmtype=jsonCons.rows[idx].data[cols.alarmtype];
	if(alarmtype == 0){	//固定值方式
		mygridCons.cells2(idx,cols.alarmval1).setValue(v1);
		mygridCons.cells2(idx,cols.alarmval2).setValue(v2);		
	}if(alarmtype == 1){	//比例方式
		if(v1 == 0){
			v1 = 30;
		}
		if(v2 == 0){
			v2 = 5;
		}
		mygridCons.cells2(idx,cols.alarmval1).setValue(round(v1 * zje /100, 3));
		mygridCons.cells2(idx,cols.alarmval2).setValue(round(v2 * zje /100, 3));
		
	}
}

//召测余额信息
function callRtuRemain(type,i,consIdx){//type:"je" 金额方式；"bd"表底方式
	if(i>consIdx.length) return;
	var idx=consIdx[i];
	if(jsonCons.rows[idx].data[cols.matchflag] != 0 || jsonCons.rows[idx].data[cols.cusstate] != 1  || jsonCons.rows[idx].data[cols.cbdayhour] == "" ) return;
	
	var param = {};
	param.rtu_id 	= jsonCons.rows[idx].data[cols.rtuid];
	param.zjg_id 	= jsonCons.rows[idx].data[cols.zjgid];
	param.paratype	= ComntProtMsg.YFF_GY_CALL_PARAREMAIN;
	
	var json_str = jsonString.json2String(param);
	loading.tip = "正在召测当前余额...";
	loading.loading();
	$.post( def.basePath + "ajaxoper/actCommGy!taskProc.action", 		//后台处理程序
	{
		firstLastFlag 	: true,
		userData1		: param.rtu_id,
		zjgid			: param.zjg_id,
		gyCommCallPara	: json_str,
		userData2 		: ComntUseropDef.YFF_GYCOMM_CALLPARA
	},
	function(data) {//回传函数
		loading.loaded();
		if (data.result != "success") {
			mygridCons.cells2(idx,cols.opresult).setValue("召参数通讯失败..");
			callRtuRemain(type,++i,consIdx);
		}else{
			remain_info = eval('(' + data.gyCommCallPara + ')');
			
			if (rtnValue.prot_type == ComntDef.YD_PROTOCAL_FKGD05 || rtnValue.prot_type == ComntDef.YD_PROTOCAL_GD376
					|| rtnValue.prot_type == ComntDef.YD_PROTOCAL_GD376_2013) {
				param.mpid 		= 1;
				param.ymd 		= 0;
				param.datatype	= ComntProtMsg.YFF_CALL_GY_REMAIN;
				
				json_str = jsonString.json2String(param);
				loading.loading();
				$.post( def.basePath + "ajaxoper/actCommGy!taskProc.action", 		//后台处理程序
					{
						firstLastFlag 	: true,
						userData1		: param.rtu_id,
						zjgid			: param.zjg_id,
						gyCommReadData	: json_str,
						userData2 		: ComntUseropDef.YFF_READDATA
					},
				function(data) {//回传函数
					loading.loaded();
					if (data.result == "success") {
						var tmp = eval('(' + data.gyCommReadData + ')');
						remain_info.nowval = tmp.cur_val;
						
						if(type == "je"){
							mygridCons.cells2(idx,cols.nowremain).setValue(remain_info.nowval);//$("#now_remain").html(remain_info.nowval);
							callRealBD(idx);
						}//下面 else 不存在
						else if(type == "bd"){							
							mygridCons.cells2(idx,cols.nowremain).setValue(remain_info.totval);//$("#shutdown_bd").html(remain_info.totval);
							mygridCons.cells2(idx,cols.ddbd).setValue(remain_info.totval);//$("#ddbd").html(remain_info.totval);
							mygridCons.cells2(idx,cols.dqbd).setValue(remain_info.nowval);//$("#dqbd").html(remain_info.nowval);
							mygridCons.cells2(idx,cols.alarmcode).setValue(remain_info.alarm_val1);//$("#alarm_code").val(remain_info.alarm_val1);//报警值1
							cacl_jsye_bd(idx);
						}
					}else{
						mygridCons.cells2(idx,cols.opresult).setValue("读取余额通讯失败.");
					}
					callRtuRemain(type,++i,consIdx);
				});
			} else {//GD05和GD376以外
				if(type == "je"){
					mygridCons.cells2(idx,cols.nowremain).setValue(remain_info.nowval);//$("#now_remain").html(remain_info.nowval);
					callRealBD(idx);
				}
				else if(type == "bd"){
					mygridCons.cells2(idx,cols.nowremain).setValue(remain_info.totval);//$("#shutdown_bd").html(remain_info.totval);
					mygridCons.cells2(idx,cols.ddbd).setValue(remain_info.totval);//$("#ddbd").html(remain_info.totval);
					mygridCons.cells2(idx,cols.dqbd).setValue(remain_info.nowval);//$("#dqbd").html(remain_info.nowval);
					mygridCons.cells2(idx,cols.alarmcode).setValue(remain_info.alarm_val1);//$("#alarm_code").val(remain_info.alarm_val1);//报警值1
					cacl_jsye_bd(idx);
				}
				callRtuRemain(type,++i,consIdx);
			}
		}
		
	});
	
	
}

//召测实时表底
function callRealBD(ii,idx){//gridCons的idx
	
	if(!ii)ii = 0;
	if(ii >= jsonCons.rows[idx].data[cols.mpnum]){
		cacl_jsye_je(idx);
		return;
	}
	
	var param = {};
	param.rtu_id = jsonCons.rows[idx].data[cols.rtuid];
	param.zjg_id = jsonCons.rows[idx].data[cols.zjgid];
	
	if(zfFlag.substring(ii, ii + 1) == "0"){	//正相
		param.datatype = ComntProtMsg.YFF_CALL_REAL_ZBD;
	}else{									//反相
		param.datatype = ComntProtMsg.YFF_CALL_REAL_FBD;
	}
	
	//mygridCons.cells2(idx,cols.opresult).setValue("正在召测当前表底");
	var json_str = jsonString.json2String(param);
	$.post( def.basePath + "ajaxoper/actCommGy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag 	: true,
			userData1		: param.rtu_id,
			zjgid			: param.zjg_id,
			gyCommReadData	: json_str,
			userData2 		: ComntUseropDef.YFF_READDATA
		},
	function(data) {			    	//回传函数
		if (data.result == "success") {
			curbd_info[idx][ii] = eval('(' + data.gyCommReadData + ')');
			callRealBD(ii + 1,idx);
		}else{
			mygridCons.cells2(idx,cols.opresult).setValue("召测表底通讯失败..");
		}
	});
}

//计算结算余额-金额方式
function cacl_jsye_je(idx){//jsonCons的idx
	//结算金额 =(当前表底  - mis结算日表底 )* pt* ct * 电价 + 基本费 + 当前余额 - mis余额
	
	var blv = jsonCons.rows[idx].data[cols.blv];//parseFloat($("#blv").html());
	var feeproj_type=jsonCons.rows[idx].data[cols.feeprojtype].split("_");
	var feeproj_reteval=jsonCons.rows[idx].data[cols.feeprojreteval].split("_");
	bdyg 	= jsonCons.rows[idx].data[cols.bdyg].split("_");	//mis有功总表底
	bdwg 	= jsonCons.rows[idx].data[cols.bdyg].split("_");	//mis无功总表底
	mp_id	= jsonCons.rows[idx].data[cols.mpids].split("_");
	zfFlag 	= jsonCons.rows[idx].data[cols.zfflag];
	
//	var feeproj_type=jsonCons.rows[idx].data[cols.feeprojtype].split("_");
//	var feeproj_type=jsonCons.rows[idx].data[cols.feeprojtype].split("_");
	var cacu1 = 0;
	//单费率jsonCons.rows[idx].data[cols.]
	if( feeproj_type[0] == 0) {//rtnValue.feeproj_type
		var feez = [0, 0, 0];
		feez[0] = feeproj_reteval[0];//rtnValue.feeproj_reteval;
		feez[1] = feeproj_reteval[1];//rtnValue.feeproj_reteval1;
		feez[2] = feeproj_reteval[2];//rtnValue.feeproj_reteval2;
		for(var i = 0; i < jsonCons.rows[idx].data[cols.mpnum]; i++) { //curbd_info[idx][i].cur_zyz = 12;	//temp test
			//正相
			if(zfFlag.substring(i, i + 1) == "0") {
				cacu1 += (parseFloat(curbd_info[idx][i].cur_zyz) - parseFloat(bdyg[i])) * blv * parseFloat(feez[i]);
			}
			//反相
			else {
				cacu1 += (parseFloat(curbd_info[idx][i].cur_fyz) - parseFloat(bdyg[i])) * blv * parseFloat(feez[i]);
			}
		}
	}
	//复费率
	else if( feeproj_type[0]  == 1) {
		//尖峰平谷电价
		var feej = [0, 0, 0], feef = [0, 0, 0], feep = [0, 0, 0], feeg = [0, 0, 0];
		
		var tmp = feeproj_reteval[0].split(",");
		feej[0] = tmp[0];
		feef[0] = tmp[1];
		feep[0] = tmp[2];
		feeg[0] = tmp[3];
		
		tmp = feeproj_reteval[1].split(",");
		feej[1] = tmp[0];
		feef[1] = tmp[1];
		feep[1] = tmp[2];
		feeg[1] = tmp[3];
		
		tmp = feeproj_reteval[2].split(",");
		feej[2] = tmp[0];
		feef[2] = tmp[1];
		feep[2] = tmp[2];
		feeg[2] = tmp[3];
		
		for(var i = 0; i < jsonCons.rows[idx].data[cols.mpnum]; i++) {
			//正相
			if(zfFlag.substring(i, i + 1) == "0") {
				cacu1 += (((parseFloat(curbd_info[idx][i].cur_zyj) - parseFloat(mis_bdj[i])) * feej[i]) + ((parseFloat(curbd_info[idx][i].cur_zyf) - parseFloat(mis_bdf[i])) * feef[i]) +
						 ((parseFloat(curbd_info[idx][i].cur_zyp) - parseFloat(mis_bdp[i])) * feep[i]) + ((parseFloat(curbd_info[idx][i].cur_zyg) - parseFloat(mis_bdg[i])) * feeg[i])) * blv;
			}
			//反相
			else {
				cacu1 += (((parseFloat(curbd_info[idx][i].cur_fyj) - parseFloat(mis_bdj[i])) * feej[i]) + ((parseFloat(curbd_info[idx][i].cur_fyf) - parseFloat(mis_bdf[i])) * feef[i]) +
						  ((parseFloat(curbd_info[idx][i].cur_fyp) - parseFloat(mis_bdp[i])) * feep[i]) + ((parseFloat(curbd_info[idx][i].cur_fyg) - parseFloat(mis_bdg[i])) * feeg[i])) * blv;
			}
		}
	}
	
	if(cacu1 < 0) {
		mygridCons.cells2(idx,cols.opresult).setValue("结算补差表底比当前表底大,不能结算");//		return;
	}
	
	//基本费
	var jbf = 0;
	
	var year = _today.substring(0, 4);
	var month = _today.substring(4, 6);
	var day = _today.substring(6, 8);

	month = parseInt(month, 10);
	day = parseInt(day, 10);
	//当前服务器时间
	var cur_date = new Date(year, month - 1, day);
	
	var t_str = $("#cb_ym").val();
	var year1 = t_str.substring(0,4);
	var month1 = t_str.substring(5,7);
	var dt = ("000" + jsonCons.rows[idx].data[cols.cbdayhour]);
	dt = dt.substring(dt.length - 4,dt.length);
	var jsDate1 = dt.substring(0,2); 
	var day1 = jsDate1;

	month1 = parseInt(month1, 10);
	day1 = parseInt(day1, 10);
	//结算日时间
	js_date = new Date(year1, month1 - 1, day1);
	
	if ((year < year1) || (year > year1+1)) {
		mygridCons.cells2(idx,cols.opresult).setValue("结算日期错误");
		return;
	}
    var jbfj = jsonCons.rows[idx].data[cols.jbf];
	//间隔2个月以上 //处理 跨年之情况   下面还要处理跨年？
	if((year - year1) * 12+ month > month1 + 1) {
		var tmp_date = new Date(year1, month1, 1);		 //下月1日  计算日期差 算首月基本费
		var days = parseInt(js_date.MaxDayOfDate());
		var jbf1 = (parseFloat(jbfj) / days) * parseInt(js_date.DateDiff('d', tmp_date));
		
		tmp_date = new Date(year, month - 1, 1);		 //当前月份 
		days = parseInt(cur_date.MaxDayOfDate());
		var jbf2 = (parseFloat(jbfj) / days) * parseInt(tmp_date.DateDiff('d', cur_date));

		var months = parseInt(month) - parseInt(month1) - 1; //中间月份
		var jbf3 = parseFloat(jbfj) * months;
		
		jbf = jbf1 + jbf2 + jbf3;
	}
	else {
		var days = parseInt(js_date.MaxDayOfDate());
		jbf = (parseFloat(jbfj) / days) * parseInt(js_date.DateDiff('d',cur_date));
	}

	var jsje = cacu1 + jbf + parseFloat(remain_info.nowval) - parseFloat(jsonCons.rows[idx].data[cols.cbDayYe]);
	jsje = -round(jsje, def.point);

	//计算出来的结算金额
	mis_calcu_val = jsje;
	mygridCons.cells2(idx,cols.othjsmoney).setValue(jsje);

	calcu();

	$("#btnPay").attr("disabled", false);
}

//计算结算余额-表底方式
function cacl_jsye_bd(idx){
	//-((断电表底  - mis结算日表底) * pt* ct * 电价  - mis余额)
	
	var shutdown_bd = parseFloat(mygridCons.cells2(idx,cols.nowremain).getValue() == "" ? 0 : mygridCons.cells2(idx,cols.nowremain).getValue());
	var blv = parseFloat(jsonCons.rows[idx].data[cols.blv]);//parseFloat(rtnValue.blv);
	var dj = parseFloat(jsonCons.rows[idx].data[cols.feeprojreteval].split("_")[0]);//rtnValue.feeproj_reteval
	var zbd = 0;
	for(var i = 0; i < jsonCons.rows[idx].data[cols.mpnum]; i++) {
		zbd += bdyg[i];
	}
	//add check zkz 20121016
	var curbd = parseFloat(mygridCons.cells2(idx,cols.dqbd).getValue());//parseFloat($("#dqbd").html());
	if (curbd < zbd) {
		mygridCons.cells2(idx,cols.opresult).setValue("结算补差表底比当前表底大,不能结算"); return;
	}
	
	var jsye = (shutdown_bd - zbd) * blv * dj - jsonCons.rows[idx].data[cols.cbDayYe];
	jsye = -round(jsye, def.point);	//取负
	calcu_bd();

	//计算出来的结算金额等
	mis_calcu_val = jsye;
	mis_calcu_gdl = jsonCons.rows[idx].data[cols.buydl];//$("#buy_dl").html();
	mis_calcu_bmc = jsonCons.rows[idx].data[cols.paybmc];//$("#pay_bmc").html();
	
	$("#btnPay_bd").attr("disabled", false);
}

function calcu_bd(jsje){
	
	var zje = round(parseFloat(jsje), def.point);
	if(isNaN(jsje)){
		return;
	}
	
	mygridCons.cells2(idx,cols.allmoney).setValue(zje);//$("#zje").val(zje);
	
	var dj 	= rtnValue.feeproj_reteval;
	var blv = jsonCons.rows[idx].data[cols.blv] === "" ? 1 : jsonCons.rows[idx].data[cols.blv];// $("#blv").html() === "" ? 1 : $("#blv").html();
	var zbd = jsonCons.rows[idx].data[cols.ddbd] === "" ? 0 : jsonCons.rows[idx].data[cols.ddbd] ; //$("#ddbd").html() === "" ? 0 : $("#ddbd").html();
	
	var alarm_val = mygridCons.cells2(idx,cols.alarmval1).getValue();
	var ret_val = bd_calcu(zje, dj, blv, zbd, alarm_val, alarm_type, zbd);
	
	mygridCons.cells2(idx,cols.buydl).setValue(ret_val.buy_dl);//$("#buy_dl").html(ret_val.buy_dl);
	mygridCons.cells2(idx,cols.paybmc).setValue(ret_val.pay_bmc);//$("#pay_bmc").html(ret_val.pay_bmc);
	mygridCons.cells2(idx,cols.shutdownval).setValue(ret_val.shutdown_bd);//$("#shutdown_bd").html(ret_val.shutdown_bd);
	
	if(zje >= 0) {
		mygridCons.cells2(idx,cols.alarmcode).setValue(ret_val.alarm_code);//$("#alarm_code").val(ret_val.alarm_code);
	} else {
		var inter = parseFloat(remain_info.totval) - parseFloat(remain_info.alarm_val1);
		var cur_bd = parseFloat(remain_info.nowval);
		var min_shutbd = inter + cur_bd;
		if (parseFloat(ret_val.shutdown_bd) < min_shutbd) {
			if (cur_bd < parseFloat(remain_info.alarm_val1)) {
			mygridCons.cells2(idx,cols.shutdownval).setValue(round(min_shutbd, def.point));//	$("#shutdown_bd").html(round(min_shutbd, def.point));
			}//else 下面结算时处理了 当前 > 报警
			else {//if (cur_bd > parseFloat(ret_val.shutdown_bd)) {
				mygridCons.cells2(idx,cols.shutdownval).setValue(round(zbd, def.point));//$("#shutdown_bd").html(round(zbd, def.point));
			}
		}
		var ac = mygridCons.cells2(idx,cols.shutdownval).getValue() - inter ;//parseFloat($("#shutdown_bd").html()) - inter;
		if(ac < 0) {
			mygridCons.cells2(idx,cols.alarmcode).setValue(0);//$("#alarm_code").val(0);
		} else {
			mygridCons.cells2(idx,cols.alarmcode).setValue(round(ac, def.point));//$("#alarm_code").val(round(ac, def.point));
		}
	}
}


//主站用不着了。金额也许可以不用
function calcu(idx){
	var zbje = 0.0, jfje = 0.0, jsje = 0.0;
	jsje = mygridCons.cells2(idx,cols.othjsmoney).getValue();//$("#jsje").val() === "" ? 0 : $("#jsje").val();
	
	var zje = round(parseFloat(zbje),3);
	
	if(!isNaN(zje)){
		setAlarmValue2Cell(rtnValue.yffalarm_alarm1, rtnValue.yffalarm_alarm2, zje)
		mygridCons.cells2(idx,cols.allmoney).setValue(zje); //$("#zje").val(zje);
	}
}



function check_jsye_bd(alarm_val1) {
	mis_jsflag = false;
	
	var othjs_money = mygridCons.cells2(idx,cols.othjsmoney).getValue();//$("#jsje").val()=== "" ? 0 : $("#jsje").val();

	if(parseFloat(othjs_money) < 0) {

		var cur_bd = parseFloat(remain_info.nowval);
		var bj_bd = parseFloat(remain_info.alarm_val1);
		if(cur_bd > bj_bd) {
			mygridCons.cells2(idx,cols.opresult).setValue("当前表底大于报警表底,不能结算");
			return false;
		}
		
		var zbje = 0.0, jfje = 0.0;
	
//		zbje = $("#zbje_bd").val() === "" ? 0 : $("#zbje_bd").val();
//		jfje = $("#jfje_bd").val() === "" ? 0 : $("#jfje_bd").val();
		
		var zje = round(parseFloat(zbje) + parseFloat(jfje) + parseFloat(mis_calcu_val), def.point);
		var dj 	= rtnValue.feeproj_reteval;
		var blv = jsonCons.rows[idx].data[cols.blv] === "" ? 1 : jsonCons.rows[idx].data[cols.blv];//$("#blv").html() === "" ? 1 : $("#blv").html();
		var zbd = jsonCons.rows[idx].data[cols.ddbd]=== "" ? 1 : jsonCons.rows[idx].data[cols.blv];//$("#ddbd").html() === "" ? 0 : $("#ddbd").html();
		var alarm_val = rtnValue.yffalarm_alarm1;
		var ret_val = bd_calcu(zje, dj, blv, zbd, alarm_val, alarm_type, zbd);

		if (ret_val.shutdown_bd < parseFloat(jsonCons.rows[idx].data[cols.shutdownval]/*$("#shutdown_bd").html()*/)) {
			var jsje    = mis_calcu_val - (ret_val.shutdown_bd - parseFloat(jsonCons.rows[idx].data[cols.shutdownval])) * parseFloat(rtnValue.blv) * parseFloat(rtnValue.feeproj_reteval);
			var bmc  	= jsje / parseFloat(rtnValue.blv) * parseFloat(rtnValue.feeproj_reteval);
			mis_jsje    = jsje;
			mis_jsbmc   = bmc;
			mis_gdl     = bmc * parseFloat(rtnValue.blv);
			mis_jsflag  = true;
		}
		/*if(ddzm == inter) {
			var bmc = ddzm - parseFloat(remain_info.totval);
			var jsje = -(bmc + cur_bd) * parseFloat(rtnValue.blv) * parseFloat(rtnValue.feeproj_reteval);
			jsje = -Math.floor(jsje);
			
			mis_jsje = jsje;
			mis_jsbmc = bmc;
			mis_gdl = bmc * parseFloat(rtnValue.blv);
			mis_jsflag = true;
		}*/
	} else {//正数时 不限制 结算金额
		var jsje = mygridCons.cells2(idx,cols.othjsmoney).getValue();//$("#jsje").val() == "" ? 0 : $("#jsje").val();
		jsje = round(jsje, def.point);
		
		if(mis_calcu_val != jsje) {
			
			mis_jsje = jsje;
			mis_jsbmc = jsje / (parseFloat(rtnValue.blv) * parseFloat(rtnValue.feeproj_reteval));
			mis_gdl = jsje / parseFloat(rtnValue.feeproj_reteval);
			
			mis_jsflag = true;
		}
	}
	
	return true;
}

//向终端缴费
function pay_je_bd(type,i,consIdx) {
	if(i>consIdx.length) return;
	var idx=consIdx[i];
	
	var params = {};
	params.rtu_id 		= jsonCons.rows[idx].data[cols.rtuid];
	params.zjg_id 		= jsonCons.rows[idx].data[cols.zjgid];
	params.paytype 		= jsonCons.rows[idx].data[cols.paytype];
	params.yffalarm_id 	= jsonCons.rows[idx].data[cols.yffalarmid];
	params.buynum 		= jsonCons.rows[idx].data[cols.buynum];

	if(type == "je") {
		params.alarmmoney	= jsonCons.rows[idx].data[cols.yffalarmid];//$("#yffalarm_alarm1").val();
		if(!check_jsye(params.alarmmoney)) {
			return;
		}
		params.buydl		= 0;
		params.paybmc		= 0;
		params.alarmbd		= 0;
		params.shutbd		= 0;
		params.yffflag		= 1;
		params.plustime		= 0;
	} else {
		if(!check_jsye_bd(params.alarmmoney)) {
			return;
		}
		params.alarmmoney	= 0;
		params.buydl		= mygridCons.cells2(idx,cols.buydl).getValue() === "" ? 0 : mygridCons.cells2(idx,cols.buydl).getValue();//$("#buy_dl").html()	=== "" ? 0 : $("#buy_dl").html();
		params.paybmc		= mygridCons.cells2(idx,cols.paybmc).getValue() === "" ? 0 : mygridCons.cells2(idx,cols.paybmc).getValue();//$("#pay_bmc").html()	=== "" ? 0 : $("#pay_bmc").html();
		params.alarmbd		= mygridCons.cells2(idx,cols.alarmcode).getValue() === "" ? 0 : mygridCons.cells2(idx,cols.alarmcode).getValue();//$("#alarm_code").val()=== "" ? 0 : $("#alarm_code").val();
		params.shutbd		= mygridCons.cells2(idx,cols.shutdownval).getValue() === "" ? 0 : mygridCons.cells2(idx,cols.shutdownval).getValue();//$("#shutdown_bd").html() ==="" ? 0 : $("#shutdown_bd").html();
		params.yffflag		= 1;
		params.plustime		= rtnValue.plus_time;
		if(mis_jsflag) {
			params.buydl = mis_gdl;
			params.paybmc = mis_jsbmc;
		}
	}
	
	params.pay_money 		= 0;
	params.othjs_money 		=  mygridCons.cells2(idx,cols.opresult).getValue() === "" ? 0 : mygridCons.cells2(idx,cols.othjsmoney).getValue();//$("#jsje").val()=== "" ? 0 : $("#jsje").val();
	if(mis_jsflag) {
		params.othjs_money 	= mis_jsje;
	}
	params.zb_money 		= 0;
	
	
	params.pay_money    = round(parseFloat(params.pay_money), def.point);
	params.othjs_money  = round(parseFloat(params.othjs_money), def.point);
	params.zb_money 	= round(parseFloat(params.zb_money), def.point);	
	params.all_money    = round(parseFloat(params.zb_money) + parseFloat(params.pay_money) + parseFloat(params.othjs_money),def.point);

	var showmis_jsje    = round(mis_jsje, def.point);
	params.all_money    = round(parseFloat(params.all_money) + parseFloat(remain_info.nowval), def.point);
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actCommGy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag 	: true,
			userData1		: params.rtu_id,
			zjgid			: params.zjg_id,
			gsmflag 		: 0,		//界面提示是否发送短信
			gyCommPay 		: json_str,
			userData2 		: ComntUseropDef.YFF_GYCOMM_PAY
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
				pay(type);
			}else{
				mygridCons.cells2(idx,cols.opresult).setValue("通讯失败...");
			}
		}
	);
}




















