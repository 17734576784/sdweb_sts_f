var rtnValue;
var pos = false;
var last_seled = -1;

$(document).ready(function(){
	
	initGrid();
	initInput();
	
	$("#btnSearch").click(function(){selcons()});
	$("#btnUpdate").click(function(){doUpdate()});//更新
	$("#updType").change(function(){chgType()});//更换类型
});

function initGrid() {
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	//length =14
	var yff_grid_title="序号,终端名称,总加组名称,客户编号,购电次数,报警方案,保电时间,1级报警状态/时间,1级短信报警状态/时间,1级声音报警状态/时间,2级报警状态/时间,2级短信报警状态/时间,分合闸状态/时间,分合闸确认状态/时间,算费类型,预付费表类型,费控方式";
	
	//8.28 新增加的字段 共15
	yff_grid_title  = yff_grid_title + ",追补累计有功电量,追补累计无功电量,累计有功电量,累计无功电量,追补电度电费,追补基本费电费,实际功率因数,电度电费,基本费电费,力调电费,发行电费当月缴费总金额,发行电费后剩余金额,发行电费年月,发行电费数据日期,发行电费算费时间,结算无功表底";
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("50,180,150,100,60,120,80,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150")
	mygrid.setColAlign("center,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left")
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.attachEvent("onRowSelect", doOnRowSelected);
	mygrid.enableTooltips("false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false")
	mygrid.init();
	mygrid.setSkin("light");
	mygrid.enableSmartRendering(true);
}

//初始化input的状态
function initInput(){
	$("#buy_times"           ).attr("readonly",false);
	$("#total_yzbdl"    ).attr("readonly",true).css("background","#ccc");
	$("#total_wzbdl"    ).attr("readonly",true).css("background","#ccc");
	$("#zbele_money"    ).attr("readonly",true).css("background","#ccc");
	$("#zbjbf_money"    ).attr("readonly",true).css("background","#ccc");
	$("#fxdf_iall_money").attr("readonly",true).css("background","#ccc");
	$("#fxdf_remain"    ).attr("readonly",true).css("background","#ccc");
    $("#fxdf_ym_tmp").html("<input type=\"text\"  name=\"fxdf_ym\" id=\"fxdf_ym\" readonly style=\"background-color:#ccc;\"/>");
    $("#jswgbd1"    ).attr("readonly",true).css("background","#ccc");
    $("#jswgbd2"    ).attr("readonly",true).css("background","#ccc");
    $("#jswgbd3"    ).attr("readonly",true).css("background","#ccc");
}

function selcons(){//检索
    var flag = true;
	for ( var i = 0; i < load_flag.length; i++) {
		flag = flag && load_flag[i];
	}
	if (!flag) {
		window.setTimeout('selcons()', 100);
		return;
	}
	var params={};
	params.orgId = $("#org").val();
	//params.rtuId = $("#rtu").val();
	params.searchType = $("#searchType").val();
	params.searchContent = $("#searchContent").val();
	params.updType =$("#updType").val();
	var json_str = jsonString.json2String(params);
	mygrid.clearAll();
	loading.loading();
	$.post(def.basePath + "ajaxspec/actUpdateState!getsList.action",{result : json_str},function(data){
		loading.loaded();
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			rtnValue = json;
			mygrid.parse(json,"json");
			mygrid.selectRow(0);
			doOnRowSelected(mygrid.getSelectedId());
			
			$("#rcd_num").html("共" + mygrid.getRowsNum() + "条记录");
		}
		else{
			clear();
		}
	});
}

function doOnRowSelected(rId){
	if(last_seled == rId) return;
	last_seled = rId;
	
	clear();
	//开始赋值
	$("#rtu_id").val(rId.split("_")[0]);
	$("#zjg_id").val(rId.split("_")[1]);
	$("#clp_num").val(rId.split("_")[2]);
	
	var idx = mygrid.getRowIndex(rId); 
	var tmp = mygrid.cells(rId, 1).getValue();
	$("#rtu_desc").html(tmp.split("]")[1]);
	$("#buy_times").val(mygrid.cells(rId, 4).getValue());//购电次数
	$("#2jbjzt").html(mygrid.cells(rId,11).getValue());
	$("#2jdxbj").html(mygrid.cells(rId, 12).getValue());
	$("#zjg_id").html(mygrid.cells(rId, 2).getValue());
	$("#bjfa").html(mygrid.cells(rId, 5).getValue());
	$("#bdsj").html(mygrid.cells(rId, 6).getValue());
	$("#1jbjzt").html(mygrid.cells(rId, 7).getValue());
	$("#yyhh").html(mygrid.cells(rId, 3).getValue());
	$("#sflx").html(rtnValue.rows[idx].data[14]);//算费类型
	$("#fhz").html(mygrid.cells(rId, 12).getValue());
	$("#fhzqr").html(mygrid.cells(rId, 13).getValue());
	$("#yff_kzlx").html(rtnValue.rows[idx].data[15]);//预付费控制类型
	$("#fkfs").html(rtnValue.rows[idx].data[16]);//费控方式
	
	$("#1jdxbj").html(mygrid.cells(rId, 8).getValue());//1级短信报警状态/时间
	$("#1jsybj").html(mygrid.cells(rId, 9).getValue());//1级声音报警状态/时间
	
	//阶梯、结算信息赋值
	$("#total_yzbdl").val(mygrid.cells(rId,17).getValue());//有功追补累计用电量
	$("#total_wzbdl").val(mygrid.cells(rId,18).getValue());//无功功追补累计用电量
	$("#total_ydl").html(mygrid.cells(rId,19).getValue());//有功累计用电量
	$("#total_wdl").html(mygrid.cells(rId,20).getValue());//无功累计用电量
	$("#zbele_money").val(mygrid.cells(rId,21).getValue());//追补电度电费
	$("#zbjbf_money").val(mygrid.cells(rId,22).getValue());//追补基本费电费
	$("#real_powrate").html(mygrid.cells(rId,23).getValue());//实际功率因数
	$("#ele_money").html(mygrid.cells(rId,24).getValue());//电度电费
	$("#jbf_money").html(mygrid.cells(rId,25).getValue());//基本费电费
	$("#powrate_money").html(mygrid.cells(rId,26).getValue());//力调电费
	$("#fxdf_iall_money").val(mygrid.cells(rId,27).getValue());//发行电费当月缴费总金额
	$("#fxdf_remain").val(mygrid.cells(rId,28).getValue());//发行电费后剩余金额
	$("#fxdf_ym").val(mygrid.cells(rId,29).getValue());//(发行电费年月)发行电费数据日期YYYYMM
	$("#fxdf_data_ymd").html(mygrid.cells(rId,30).getValue());//发行电费数据日期-YYYYMMDD
	$("#fxdf_calc_mdhmi").html(mygrid.cells(rId,31).getValue());//发行电费算费时间-MMDDHHMI
	
	//结算无功表底
	if($("#updType").val() == 10){
		setJdbdState();
	}
	
	var jsbd = mygrid.cells(rId, 32).getValue();
	jsbd = jsbd.split("-");
	$("#jswgbd1").val(jsbd[0]);
	$("#jswgbd2").val(jsbd[1]);
	$("#jswgbd3").val(jsbd[2]);
}

//高压强制状态更新
function doUpdate(){//更新
	
	var rtu_id = $("#rtu_id").val(), zjg_id = $("#zjg_id").val();
	if(rtu_id === "" || zjg_id === ""){
		alert("请选择用户信息!");
		return;
	}
	
	if(!confirm("确定要更新吗?")) return;
	
	var params = {};
	params.rtuId = rtu_id;
	params.mpId = zjg_id;
	params.updType = $("#updType").val();
	
	if(params.updType == '0'){
		params.buy_times=$("#buy_times").val();//更新购电次数
	}
	
	else if(params.updType == '4'){//追补累计用电量
		params.total_yzbdl = $("#total_yzbdl").val() == "" ? '0' :$("#total_yzbdl").val();
		params.total_wzbdl = $("#total_wzbdl").val() == "" ? '0' :$("#total_wzbdl").val();
	}
	
	else if(params.updType == '5'){//追补电度电费
		params.zbele_money = $("#zbele_money").val() == "" ? '0' :$("#zbele_money").val();
	}
	
	else if(params.updType == '6'){//追补基本费电费
		params.zbjbf_money = $("#zbjbf_money").val() == "" ? '0' :$("#zbjbf_money").val();
	}
	
	else if(params.updType == '7'){//发行电费当月缴费总金额
		params.fxdf_iall_money = $("#fxdf_iall_money").val() == "" ? '0' :$("#fxdf_iall_money").val();
	}
	
	else if(params.updType == '8'){//发行电费后剩余金额
	    params.fxdf_remain= $("#fxdf_remain").val() == "" ? '0' :$("#fxdf_remain").val();	
	}
	
	else if(params.updType == '9'){//发行电费数据日期
		var tmp = $("#fxdf_ym").val();
		if(tmp == ""){
		    params.fxdf_ym ='0';
		}
		if(tmp != ""){
			params.fxdf_ym = tmp.substring(0,4) + tmp.substring(5,7);
		}
	}
	else if(params.updType == '10') {
		
		if(!isDbl("jswgbd1","结算无功表底1", 0, 100000000000)){
			return ;
		}
		if(!isDbl("jswgbd2","结算无功表底2", 0, 100000000000)){
			return ;
		}
		if(!isDbl("jswgbd3","结算无功表底3", 0, 100000000000)){
			return ;
		}
		
		params.jswgbd1 = $("#jswgbd1").val() == "" ? "0" : $("#jswgbd1").val();
		params.jswgbd2 = $("#jswgbd2").val() == "" ? "0" : $("#jswgbd2").val();
		params.jswgbd3 = $("#jswgbd3").val() == "" ? "0" : $("#jswgbd3").val();
	}
	
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	var json_str = jsonString.json2String(params);
	
	//强制更新-开始
	loading.next_focus_id = "btnSearch";
	loading.loading();
	$.post(def.basePath + "ajaxoper/actOperGy!taskProc.action",//执行强制更新
		{
			userData1:        rtu_id,
			zjgid :           zjg_id,
			gsmflag :         0,
			userData2:        ComntUseropDef.YFF_GYOPER_UDPATESTATE,
			gyOperUpdateState:json_str						
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == "success") {
				alert("接收预付费服务任务成功...");
				update_grid();
			}
			else alert("接收预付费服务任务失败...");
		}
	);
	//强制更新-结束
}

function update_grid() {
	var rId = mygrid.getSelectedRowId();
	mygrid.cells(rId, 4).setValue($("#buy_times").val());			//购电次数
	mygrid.cells(rId, 17).setValue($("#total_yzbdl").val());	//有功追补累计用电量
	mygrid.cells(rId, 18).setValue($("#total_wzbdl").val());	//无功功追补累计用电量
	mygrid.cells(rId, 21).setValue($("#zbele_money").val());	//追补电度电费
	mygrid.cells(rId, 22).setValue($("#zbjbf_money").val());	//追补基本费电费
	mygrid.cells(rId, 27).setValue($("#fxdf_iall_money").val());//发行电费当月缴费总金额
	mygrid.cells(rId, 28).setValue($("#fxdf_remain").val());	//发行电费后剩余金额
	mygrid.cells(rId, 29).setValue($("#fxdf_ym").val());	//(发行电费年月)发行电费数据日期YYYYMM
	
	var jswgbd =  $("#jswgbd1").val() + "-" +  $("#jswgbd2").val() + "-" + $("#jswgbd3").val();
	mygrid.cells(rId, 32).setValue(jswgbd);	//结算无功表底
}

function clear(){
	$("#rtu_id").val("");
	$("#mp_id").val("");
	$("#rtu_desc").html("");
	$("#buy_times").val("");
	$("#2jbjzt").html("");;
	$("#2jdxbj").html("");
	$("#zjg_id").html("");
	$("#bjfa").html("");
	$("#bdsj").html("");
	$("#1jbjzt").html("");
	$("#yyhh").html("");
	$("#sflx").html("");//算费类型
	$("#fhz").html("");
	$("#fhzqr").html("");
	$("#yff_kzlx").html("");//预付费控制类型
	$("#fkfs").html("");//费控方式
	$("#1jdxbj").html("");//1级短信报警状态/时间
	$("#1jsybj").html("");//1级声音报警状态/时间
	
	$("#total_yzbdl").val("");//有功追补累计用电量
	$("#total_wzbdl").val("");//无功功追补累计用电量
	$("#total_ydl").val("");//有功累计用电量
	$("#total_wdl").val("");//无功累计用电量
	$("#zbele_money").val("");//追补电度电费
	$("#zbjbf_money").val("");//追补基本费电费
	$("#real_powrate").val("");//实际功率因数
	$("#ele_money").val("");//电度电费
	$("#jbf_money").val("");//基本费电费
	$("#powrate_money").val("");//力调电费
	$("#fxdf_iall_money").val("");//发行电费当月缴费总金额
	$("#fxdf_remain").val("");//发行电费后剩余金额
	$("#fxdf_ym").val("");//(发行电费年月)发行电费数据日期YYYYMM
	$("#fxdf_data_ymd").val("");//发行电费数据日期-YYYYMMDD
	$("#fxdf_calc_mdhmi").val("");//发行电费算费时间-MMDDHHMI
}

//change事件
function chgType(){
	
	var rId = mygrid.getSelectedRowId();
	var fxdf_ym = mygrid.cells(rId,29).getValue();
	if($("#updType").val()==0){//更新购电次数
		
		$("#buy_times"           ).attr("readonly",false).css("background","#FFF");
		$("#zbele_money"    ).attr("readonly",true).css("background","#ccc");
		$("#zbjbf_money"    ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_iall_money").attr("readonly",true).css("background","#ccc");
		$("#fxdf_remain"    ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_ym_tmp").html("<input type=\"text\"  name=\"fxdf_ym\" id=\"fxdf_ym\" readonly style=\"background-color:#ccc;\" value='"+fxdf_ym+"' />");
		$("#jswgbd1"    ).attr("readonly",true).css("background","#ccc");
		$("#jswgbd2"    ).attr("readonly",true).css("background","#ccc");
		$("#jswgbd3"    ).attr("readonly",true).css("background","#ccc");
		
	}else if($("#updType").val()==4){//更新追补累计用电量
		
		$("#buy_times"           ).attr("readonly",true).css("background","#ccc");;
		$("#total_yzbdl"    ).attr("readonly",false).css("background","#fff");
		$("#total_wzbdl"    ).attr("readonly",false).css("background","#fff");
		$("#zbele_money"    ).attr("readonly",true).css("background","#ccc");
		$("#zbjbf_money"    ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_iall_money").attr("readonly",true).css("background","#ccc");
		$("#fxdf_remain"    ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_ym_tmp").html("<input type=\"text\"  name=\"fxdf_ym\" id=\"fxdf_ym\" readonly style=\"background-color:#ccc;\" value='"+fxdf_ym+"' />");
		$("#jswgbd1"    ).attr("readonly",true).css("background","#ccc");
		$("#jswgbd2"    ).attr("readonly",true).css("background","#ccc");
		$("#jswgbd3"    ).attr("readonly",true).css("background","#ccc");
		
	}else if($("#updType").val()==5){//更新追补电度电费
		
		$("#buy_times"           ).attr("readonly",true).css("background","#ccc");;
		$("#total_yzbdl"    ).attr("readonly",true).css("background","#ccc");
		$("#total_wzbdl"    ).attr("readonly",true).css("background","#ccc");
		$("#zbele_money"    ).attr("readonly",false).css("background","#fff");
		$("#zbjbf_money"    ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_iall_money").attr("readonly",true).css("background","#ccc");
		$("#fxdf_remain"    ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_ym_tmp").html("<input type=\"text\"  name=\"fxdf_ym\" id=\"fxdf_ym\" readonly style=\"background-color:#ccc;\" value='"+fxdf_ym+"' />");
		$("#jswgbd1"    ).attr("readonly",true).css("background","#ccc");
		$("#jswgbd2"    ).attr("readonly",true).css("background","#ccc");
		$("#jswgbd3"    ).attr("readonly",true).css("background","#ccc");
	}else if($("#updType").val()==6){//更新追补基本费电费
		
		$("#buy_times"           ).attr("readonly",true).css("background","#ccc");;
		$("#total_yzbdl"    ).attr("readonly",true).css("background","#ccc");
		$("#total_wzbdl"    ).attr("readonly",true).css("background","#ccc");
		$("#zbele_money"    ).attr("readonly",true).css("background","#ccc");
		$("#zbjbf_money"    ).attr("readonly",false).css("background","#fff");
		$("#fxdf_iall_money").attr("readonly",true).css("background","#ccc");
		$("#fxdf_remain"    ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_ym_tmp").html("<input type=\"text\"  name=\"fxdf_ym\" id=\"fxdf_ym\" readonly style=\"background-color:#ccc;\" value='"+fxdf_ym+"' />");
		$("#jswgbd1"    ).attr("readonly",true).css("background","#ccc");
		$("#jswgbd2"    ).attr("readonly",true).css("background","#ccc");
		$("#jswgbd3"    ).attr("readonly",true).css("background","#ccc");
	}else if($("#updType").val()==7){//发行电费当月缴费总金额
		
		$("#buy_times"           ).attr("readonly",true).css("background","#ccc");;
		$("#total_yzbdl"    ).attr("readonly",true).css("background","#ccc");
		$("#total_wzbdl"    ).attr("readonly",true).css("background","#ccc");
		$("#zbele_money"    ).attr("readonly",true).css("background","#ccc");
		$("#zbjbf_money"    ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_iall_money").attr("readonly",false).css("background","#fff");
		$("#fxdf_remain"    ).attr("readonly",true).css("background","#ccc");
	    $("#fxdf_ym_tmp").html("<input type=\"text\"  name=\"fxdf_ym\" id=\"fxdf_ym\" readonly style=\"background-color:#ccc;\" value='"+fxdf_ym+"' />");
		$("#jswgbd1"    ).attr("readonly",true).css("background","#ccc");
		$("#jswgbd2"    ).attr("readonly",true).css("background","#ccc");
		$("#jswgbd3"    ).attr("readonly",true).css("background","#ccc");
	}else if($("#updType").val()==8){//发行电费后剩余金额
		
		$("#buy_times"           ).attr("readonly",true).css("background","#ccc");;
		$("#total_yzbdl"    ).attr("readonly",true).css("background","#ccc");
		$("#total_wzbdl"    ).attr("readonly",true).css("background","#ccc");
		$("#zbele_money"    ).attr("readonly",true).css("background","#ccc");
		$("#zbjbf_money"    ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_iall_money").attr("readonly",true).css("background","#ccc");
		$("#fxdf_remain"    ).attr("readonly",false).css("background","#fff");
		$("#fxdf_ym_tmp").html("<input type=\"text\"  name=\"fxdf_ym\" id=\"fxdf_ym\" readonly style=\"background-color:#ccc;\" value='"+fxdf_ym+"' />");
		$("#jswgbd1"    ).attr("readonly",true).css("background","#ccc");
		$("#jswgbd2"    ).attr("readonly",true).css("background","#ccc");
		$("#jswgbd3"    ).attr("readonly",true).css("background","#ccc");
	}else if($("#updType").val()==9){//发行电费数据日期
		
		$("#buy_times"           ).attr("readonly",true).css("background","#ccc");;
		$("#total_yzbdl"    ).attr("readonly",true).css("background","#ccc");
		$("#total_wzbdl"    ).attr("readonly",true).css("background","#ccc");
		$("#zbele_money"    ).attr("readonly",true).css("background","#ccc");
		$("#zbjbf_money"    ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_iall_money").attr("readonly",true).css("background","#ccc");
		$("#fxdf_remain"    ).attr("readonly",true).css("background","#ccc");	
		$("#fxdf_ym_tmp").html("<input type=\"text\"  name=\"fxdf_ym\" id=\"fxdf_ym\" readonly onFocus=\"WdatePicker({dateFmt:'yyyy年MM月',maxDate:'%y-%M',isShowClear:'false'});\" value='"+fxdf_ym+"' />");
	    $("#jswgbd1"    ).attr("readonly",true).css("background","#ccc");
		$("#jswgbd2"    ).attr("readonly",true).css("background","#ccc");
		$("#jswgbd3"    ).attr("readonly",true).css("background","#ccc");
	}else if($("#updType").val() == 10){
		
		$("#buy_times"           ).attr("readonly",true).css("background","#ccc");;
		$("#total_yzbdl"    ).attr("readonly",true).css("background","#ccc");
		$("#total_wzbdl"    ).attr("readonly",true).css("background","#ccc");
		$("#zbele_money"    ).attr("readonly",true).css("background","#ccc");
		$("#zbjbf_money"    ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_iall_money").attr("readonly",true).css("background","#ccc");
		$("#fxdf_remain"    ).attr("readonly",true).css("background","#ccc");
	    $("#fxdf_ym_tmp").html("<input type=\"text\"  name=\"fxdf_ym\" id=\"fxdf_ym\" readonly style=\"background-color:#ccc;\" value='"+fxdf_ym+"' />");
	    
	    setJdbdState();
	}
}
function setJdbdState() {
	var clp_num = parseInt($("#clp_num").val());
    switch(clp_num) {
    case 2 :
    	$("#jswgbd1").attr("readonly",false).css("background","#fff");
		$("#jswgbd2").attr("readonly",false).css("background","#fff");
		$("#jswgbd3").attr("readonly",true).css("background","#ccc");
		break;
	case 3 :
    	$("#jswgbd1").attr("readonly",false).css("background","#fff");
		$("#jswgbd2").attr("readonly",false).css("background","#fff");
		$("#jswgbd3").attr("readonly",false).css("background","#fff");
		break;
	case 1 :
    default:
    	$("#jswgbd1").attr("readonly",false).css("background","#fff");
		$("#jswgbd2").attr("readonly",true).css("background","#ccc");
		$("#jswgbd3").attr("readonly",true).css("background","#ccc");
		break;
    }
}