//var matchFlag  = 0;//0：可结算; 其他: 不能结算 
//var rtnValue   = null;
//var bdyg   	   = [-1, -1, -1];
//var mp_id  	   = [-1, -1, -1];
//var clpNum 	   = 0;
//var jsDate 	   = 0; //抄表日yyyymmdd：excel的yyyymm + zjgpay_para中的cb_dayhour的dd
//var jsTime 	   = 0; //抄表日hhmm ：zjgpay_para中的cb_dayhour的hh + "00"
//var cbDayYe    = 0; //抄表日余额:excel中的余额。
//var dbRemain   = 0;	//电表余额
//var dbRemainf  = 0;	//电表余额标志
//var khDate 	   = 0;
//var mis_jsje = 0, mis_jsflag = false;
//var cacl_type, feectrl_type;		//计费方式,费控方式
//var last_sel_id = -1;

var gridColumn = {
		"index"			: 0,
		"consName"		: 1,
		"consNo"		: 2,
		"totUseDl"		: 3,
		"matchDesc" 	: 4,
		"mdesc"			: 5,
		"feedesc"		: 6,
		"totBuyDl"		: 7,
		"jsMoney"		: 8,
		"jtchgYmd"		: 9,
		"matchFlag" 	: 10,
		"rtuId" 		: 11,
		"mpId"			: 12,
		"jsresult"		: 13
}

$(document).ready(function(){
	initJtResetymd();
	initGridCons();
	
	$("#upload_totdl").click(function(){showUpload()});
	$("#btnjtjs").click(function(){	JtJcProc();	});
});

function initGridCons(){
	var header = "<img src='" + def.basePath + "images/grid/imgs/item_chk0.gif' onclick='selectAllOrNone(this);' id='headchk'/>,营销名称,户号,总用电量,匹配档案,用户名,费率,总售电量,追补金额,切换日期,matchFlag,RTU_ID,MP_ID,保存结果";
	
	mygridCons.setImagePath(def.basePath + "images/grid/imgs/");
	mygridCons.setHeader(header);
	mygridCons.setInitWidths("50,120,80,80,160,120,120,80,80,80,60,60,60,60,60*");
	mygridCons.setColAlign("center,center,center,center,center,center,center,center,center,center,center,center,center,center,center");
	mygridCons.setColTypes("ch,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygridCons.enableTooltips("false,false,false,false,false,false,false,false,false,false,false,false,false,false,false");//共列
	mygridCons.init();
	mygridCons.setSkin("light");
	
	mygridCons.setColumnHidden(gridColumn.matchFlag,	true);	//matchFlag
	mygridCons.setColumnHidden(gridColumn.rtuId,		true);	//rtuId
	mygridCons.setColumnHidden(gridColumn.mpId,			true);	//mpId
}

//获取时间
function initJtResetymd() {
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var day = date.getDate();

	if(month < 10) month = "0" + month;
	if(day < 10)   day = "0" + day;

	$("#resetymd").val(year + "年" + month + "月"+ day+ "日");
}

//全选&全不选
function selectAllOrNone(checked) {      
	if(checked.src.indexOf("_dis.gif")!=-1){
		return;
	}
	var flag = false;
	if (checked.src.indexOf("item_chk0.gif") != -1) {
		flag = true;
		checked.src = def.basePath + 'images/grid/imgs/item_chk1.gif';
		$("#btnjtjs").attr("disabled",false);
	} else if (checked.src.indexOf("item_chk1.gif") != -1) {
		flag = false;
		checked.src = def.basePath +  'images/grid/imgs/item_chk0.gif';
	}

	for ( var i = 0; i < mygridCons.getRowsNum(); i++) {
		if(mygridCons.cells2(i, gridColumn.matchFlag).getValue() == "0"){
			mygridCons.cells2(i, 0).setValue(flag);// alert(i + "  " + flag)
		}
	}	
}

function showUpload(){//显示弹出窗口--上传页面
	$("#btnjtjs").attr("disabled",true);

	modalDialog.height = 300;
	modalDialog.width  = 400;

	var t_str 			= $("#resetymd").val();
	modalDialog.param   = t_str.substring(0,4) + t_str.substring(5,7) + t_str.substring(8,10);
	modalDialog.url    = def.basePath + "jsp/dyjc/dialog/uploadSG186TotDl.jsp";
	var o = modalDialog.show();
	
	if(!o && o!= ""){
		return;
	}
	parsegrid(o);	
}

function parsegrid(resultdata){//必须先打开文件，提交from时走的
	
	if(resultdata != "" && resultdata != "null"){
		var json = eval('(' + resultdata + ')');
		mygridCons.clearAll();
		mygridCons.parse(json, "", "json");
		setRowDisable();
		mygridCons.selectRow(0);
	}
}

//禁用行选择
function setRowDisable(){
	for(var i=0; i<mygridCons.getRowsNum(); i++){
		if(mygridCons.cells2(i, gridColumn.matchFlag).getValue() != "0") {
			mygridCons.cells2(i, 0).setValue(false);
			mygridCons.cells2(i, 0).setDisabled(true);
		}
	}
}

function JtJcProc(){	//结算
	for ( var i = 0; i < mygridCons.getRowsNum(); i++) {
		if(mygridCons.cells2(i, 0).getValue() == 1){
			break;	
		}
	}

	if (i >= mygridCons.getRowsNum()) {
		alert("请至少选中一行导入！");
		return;
	}

	var t_str 		= $("#resetymd").val();
	var date 		= t_str.substring(0,4) + t_str.substring(5,7) + t_str.substring(8,10);
  	var seledrow = "{rows:[";

	for ( var i = 0; i < mygridCons.getRowsNum(); i++) {
		if(mygridCons.cells2(i, 0).getValue() != 0) {
			var rtu_id = parseInt(chkString(mygridCons.cells2(i, 11).getValue()));
			var mp_id = parseInt(chkString(mygridCons.cells2(i, 12).getValue()));
			if ((rtu_id <= 0) || (mp_id <= 0))	continue;

			var j = 1;
			seledrow += "{op_date:\"" 			+ date;
			seledrow += "\",cons_no:\"" 		+ chkString(mygridCons.cells2(i, gridColumn.consNo).getValue());
			seledrow += "\",cons_name:\"" 		+ chkString(mygridCons.cells2(i, gridColumn.consName).getValue());
			seledrow += "\",jsmoney:\"" 		+ chkString(mygridCons.cells2(i, gridColumn.jsMoney).getValue());
			seledrow += "\",rtu_id:\"" 			+ chkString(mygridCons.cells2(i, gridColumn.rtuId).getValue());
			seledrow += "\",mp_id:\"" 			+ chkString(mygridCons.cells2(i, gridColumn.mpId).getValue());
			seledrow += "\"},";
		}
	}

	if(seledrow=="{rows:["){
		alert("请至少选中一行合法数据导入！");
		return;
	}
		
	seledrow = seledrow.substring(0,seledrow.length-1);
	seledrow += "]}";
	eval('('+seledrow+')');

	loading.loading();
	$.post(def.basePath + "ajaxdyjc/actSG186DlJs!ImportJsRecord.action", {result:seledrow}, function(data) {
		loading.loaded();
		if(data.result == "success") {
			var retval = data.operErr.toString().split(",");
			var j = 0;
			for(var i =0; i< mygridCons.getRowsNum(); i++){
				if(mygridCons.cells2(i, 0).getValue() != 1) continue;					
 				if(retval[j++] == 1){
 					mygridCons.cells2(i, gridColumn.jsresult).setValue("<font color=red>√</font>");	
 				}else{
 					mygridCons.cells2(i,gridColumn.jsresult).setValue("<font color=red>X</font>");	
 				}
 			}
			//alert("已成功导入所选！");			
		}
		else {			
			alert("导入数据失败...");
		}
	});
}

function chkString(obj){
	var result = "";
	if(!obj){
		obj = result;
	}
	return obj;
}
////获取选中行索引，格式：rowIndex1,rowIndex2,...rowIndexi
//function getSDparams(){
//	var params = "";
//	for(var i=0; i<mygridCons.getRowsNum(); i++){
//		if(mygridCons.cells2(i, gridColumn.matchFlag).getValue() == "0" && mygridCons.cells2(i, 0).getValue() == 1){
//			params += i + ",";
//		}
//	}
//	params = params.substring(0, params.length-1);
//	return params;
//}

