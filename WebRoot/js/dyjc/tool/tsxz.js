var rtnValue;
var pos=false;
var selIndex = 0;//回显  上次选中的行数
$(document).ready(function(){
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	//length =11
	var yff_grid_title1=",阶梯追补累计用电量,阶梯累计用电量,阶梯上次切换日期,阶梯上次切换执行时间,发行电费当月缴费总金额,发行电费当月剩余金额,发行电费当月剩余金额2,发行电费年月,发行电费数据日期,发行电费算费时间,计算补差日期"
	yff_grid_title=yff_grid_title+yff_grid_title1;
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("50,150,150,60,150,60,150,145,145,120,145,120,140,140,140,140,140,160,140,140,140,140,140,140,*")
	mygrid.setColAlign("center,left,left,right,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left")
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.attachEvent("onRowSelect", doOnRowSelected);
	mygrid.init();
	mygrid.setSkin("light");
	
	mygrid.enableSmartRendering(true);
	
	initInput()
	
	$("#btnSearch").click(function(){selcons()});
	$("#btnUpdate").click(function(){doUpdate()});//更新
	$("#updType").change(function(){chgType()});//更换类型
	
});

//初始化input的状态
function initInput(){
	$("#buy_times"       	    ).attr("readonly",false);
	$("#jt_total_zbdl"      ).attr("readonly",true).css("background","#ccc");
	$("#fxdf_iall_money"    ).attr("readonly",true).css("background","#ccc");
	$("#jt_total_dl"        ).attr("readonly",true).css("background","#ccc");
	$("#fxdf_iall_money2"   ).attr("readonly",true).css("background","#ccc");
	$("#fxdf_data_ymd"      ).attr("readonly",true).css("background","#ccc");
    $("#fxdf_remain"    	).attr("readonly",true).css("background","#ccc");
    $("#fxdf_calc_mdhmi"    ).attr("readonly",true).css("background","#ccc");
    $("#jt_reset_mdhmi"    	).attr("readonly",true).css("background","#ccc");
    $("#fxdf_remain2"    	).attr("readonly",true).css("background","#ccc");
    
    $("#fxdf_ym"    		).attr("readonly",true).css("background","#ccc");
	$("#fxdf_ym_tmp").html("<input type=\"text\"  name=\"fxdf_ym\" id=\"fxdf_ym\" readonly style=\"background-color:#ccc;\"/>");
	
	$("#jt_reset_ymd"    	).attr("readonly",true).css("background","#ccc");
    $("#jt_reset_ymd_tmp").html("<input type=\"text\"  name=\"jt_reset_ymd\" id=\"jt_reset_ymd\" readonly style=\"background-color:#ccc;\"/>");
}

function chgType(){
	 var rId = mygrid.getSelectedRowId();
	 var fxdf_ym = mygrid.cells(rId,20).getValue();
	 var jt_reset_ymd = mygrid.cells(rId,15).getValue();
     if($("#updType").val() == 0){//购电次数
		$("#buy_times"       	    ).attr("readonly",false).css("background","#fff");
		$("#jt_total_zbdl"      ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_iall_money"    ).attr("readonly",true).css("background","#ccc");
		
		$("#jt_total_dl"        ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_iall_money2"   ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_data_ymd"      ).attr("readonly",true).css("background","#ccc");
		
		$("#fxdf_remain"    	).attr("readonly",true).css("background","#ccc");
		$("#fxdf_calc_mdhmi"    ).attr("readonly",true).css("background","#ccc");
		$("#jt_reset_mdhmi"    	).attr("readonly",true).css("background","#ccc");
		$("#fxdf_remain2"    	).attr("readonly",true).css("background","#ccc");
		
	    $("#fxdf_ym_tmp").html("<input type=\"text\"  name=\"fxdf_ym\" id=\"fxdf_ym\" readonly style=\"background-color:#ccc;\" value='"+fxdf_ym+"' />");
		
		$("#jt_reset_ymd_tmp").html("<input type=\"text\"  name=\"jt_reset_ymd\" id=\"jt_reset_ymd\" readonly style=\"background-color:#ccc;\" value='"+jt_reset_ymd+"' />");
		
	}else if($("#updType").val() == 4){//阶梯追补累计用电量
		$("#buy_times"       	    ).attr("readonly",true).css("background","#ccc");
		$("#jt_total_zbdl"      ).attr("readonly",false).css("background","#fff");
		$("#fxdf_iall_money"    ).attr("readonly",true).css("background","#ccc");
		$("#jt_total_dl"        ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_iall_money2"   ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_data_ymd"      ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_remain"    	).attr("readonly",true).css("background","#ccc");
		$("#fxdf_calc_mdhmi"    ).attr("readonly",true).css("background","#ccc");
		$("#jt_reset_mdhmi"    	).attr("readonly",true).css("background","#ccc");
		$("#fxdf_remain2"    	).attr("readonly",true).css("background","#ccc");
		
			
	    $("#fxdf_ym_tmp").html("<input type=\"text\"  name=\"fxdf_ym\" id=\"fxdf_ym\" readonly style=\"background-color:#ccc;\" value='"+fxdf_ym+"' />");
		
		$("#jt_reset_ymd_tmp").html("<input type=\"text\"  name=\"jt_reset_ymd\" id=\"jt_reset_ymd\" readonly style=\"background-color:#ccc;\" value='"+jt_reset_ymd+"' />");
		
	}else if($("#updType").val() == 5){//阶梯上次切换日期
		$("#buy_times"       	    ).attr("readonly",true).css("background","#ccc");
		$("#jt_total_zbdl"      ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_iall_money"    ).attr("readonly",true).css("background","#ccc");
		$("#jt_total_dl"        ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_iall_money2"   ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_data_ymd"      ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_remain"    	).attr("readonly",true).css("background","#ccc");
		$("#fxdf_calc_mdhmi"    ).attr("readonly",true).css("background","#ccc");
		$("#jt_reset_mdhmi"    	).attr("readonly",true).css("background","#ccc");
		$("#fxdf_remain2"    	).attr("readonly",true).css("background","#ccc");
		
		$("#fxdf_ym_tmp").html("<input type=\"text\"  name=\"fxdf_ym\" id=\"fxdf_ym\" readonly style=\"background-color:#ccc;\" value='"+fxdf_ym+"' />");
		
		$("#jt_reset_ymd_tmp").html("<input type=\"text\"  name=\"jt_reset_ymd\" id=\"jt_reset_ymd\" readonly " +
									"tyle = \"background-color:#fff\" onFocus=\"WdatePicker({dateFmt:'yyyy年MM月dd日',maxDate:'%y-%M-%d',isShowClear:'false'});\" value='"+jt_reset_ymd+"' />");
	}else if($("#updType").val() == 6){//发行电费当月缴费总金额
		$("#buy_times"       	    ).attr("readonly",true);
		$("#jt_total_zbdl"      ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_iall_money"    ).attr("readonly",false).css("background","#fff");
		$("#jt_total_dl"        ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_iall_money2"   ).attr("readonly",false).css("background","#fff");
		$("#fxdf_data_ymd"      ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_remain"    	).attr("readonly",false).css("background","#ccc");
		$("#fxdf_calc_mdhmi"    ).attr("readonly",true).css("background","#ccc");
		$("#jt_reset_mdhmi"    	).attr("readonly",true).css("background","#ccc");
		$("#fxdf_remain2"    	).attr("readonly",false).css("background","#ccc");
		
        $("#fxdf_ym_tmp").html("<input type=\"text\"  name=\"fxdf_ym\" id=\"fxdf_ym\" readonly style=\"background-color:#ccc;\" value='"+fxdf_ym+"' />");
		
		$("#jt_reset_ymd_tmp").html("<input type=\"text\"  name=\"jt_reset_ymd\" id=\"jt_reset_ymd\" readonly style=\"background-color:#ccc;\" value='"+jt_reset_ymd+"' />");
		
	}else if($("#updType").val() == 7){//发行电费当月剩余金额
		$("#buy_times"       	    ).attr("readonly",true).css("background","#ccc");
		$("#jt_total_zbdl"      ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_iall_money"    ).attr("readonly",true).css("background","#ccc");
		$("#jt_total_dl"        ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_iall_money2"   ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_data_ymd"      ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_remain"    	).attr("readonly",false).css("background","#fff");
		$("#fxdf_calc_mdhmi"    ).attr("readonly",true).css("background","#ccc");
		$("#jt_reset_mdhmi"    	).attr("readonly",true).css("background","#ccc");
		$("#fxdf_remain2"    	).attr("readonly",false).css("background","#fff");
		
        $("#fxdf_ym_tmp").html("<input type=\"text\"  name=\"fxdf_ym\" id=\"fxdf_ym\" readonly style=\"background-color:#ccc;\" value='"+fxdf_ym+"' />");
		
		$("#jt_reset_ymd_tmp").html("<input type=\"text\"  name=\"jt_reset_ymd\" id=\"jt_reset_ymd\" readonly style=\"background-color:#ccc;\" value='"+jt_reset_ymd+"' />");
		
	}else if($("#updType").val() == 8){//发行电费年月
		$("#buy_times"       	    ).attr("readonly",true).css("background","#ccc");
		$("#jt_total_zbdl"      ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_iall_money"    ).attr("readonly",true).css("background","#ccc");
		$("#jt_total_dl"        ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_iall_money2"   ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_data_ymd"      ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_remain"    	).attr("readonly",true).css("background","#ccc");
		$("#fxdf_calc_mdhmi"    ).attr("readonly",true).css("background","#ccc");
		$("#jt_reset_mdhmi"    	).attr("readonly",true).css("background","#ccc");
		$("#fxdf_remain2"    	).attr("readonly",true).css("background","#ccc");
		
		$("#fxdf_ym_tmp").html("<input type=\"text\"  name=\"fxdf_ym\" id=\"fxdf_ym\" readonly onFocus=\"WdatePicker({dateFmt:'yyyy年MM月',maxDate:'%y-%M',isShowClear:'false'});\" value='"+fxdf_ym+"' />");
		
		$("#jt_reset_ymd_tmp").html("<input type=\"text\"  name=\"jt_reset_ymd\" id=\"jt_reset_ymd\" readonly style=\"background-color:#ccc;\" value='"+jt_reset_ymd+"' />");
		
	}else{
		$("#buy_times"       	    ).attr("readonly",true).css("background","#ccc");
		$("#jt_total_zbdl"      ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_iall_money"    ).attr("readonly",true).css("background","#ccc");
		$("#jt_total_dl"        ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_iall_money2"   ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_data_ymd"      ).attr("readonly",true).css("background","#ccc");
		$("#fxdf_remain"    	).attr("readonly",true).css("background","#ccc");
		$("#fxdf_calc_mdhmi"    ).attr("readonly",true).css("background","#ccc");
		$("#jt_reset_mdhmi"    	).attr("readonly",true).css("background","#ccc");
		$("#fxdf_remain2"    	).attr("readonly",true).css("background","#ccc");
		
  		$("#fxdf_ym_tmp").html("<input type=\"text\"  name=\"fxdf_ym\" id=\"fxdf_ym\" readonly style=\"background-color:#ccc;\" value='"+fxdf_ym+"' />");
		
		$("#jt_reset_ymd_tmp").html("<input type=\"text\"  name=\"jt_reset_ymd\" id=\"jt_reset_ymd\" readonly style=\"background-color:#ccc;\" value='"+jt_reset_ymd+"' />");
	}
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
	params.rtuId = $("#rtu").val();
	params.searchType = $("#searchType").val();
	params.searchContent = $("#searchContent").val();
	params.updType =$("#updType").val();
	var json_str = jsonString.json2String(params);
	mygrid.clearAll();
	loading.loading();
	$.post(def.basePath + "ajaxdyjc/actUpdateState!getsList.action",{result:json_str},function(data){
		loading.loaded();
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			rtnValue = json;
			mygrid.parse(json,"json");
			mygrid.selectRow(selIndex);	
			doOnRowSelected(mygrid.getSelectedId());
		}
		else{
			clear();
		}
	});
}

function doOnRowSelected(rId){
	clear();
	$("#rtu_id").val(rId.split("_")[0]);
	$("#mp_id ").val(rId.split("_")[1]);
	var idx = mygrid.getRowIndex(rId); 
	var tmp = mygrid.cells(rId, 2).getValue();
	$("#yhmc").html(tmp.split("]")[1]);
	$("#bjfa").html(mygrid.cells(rId, 4).getValue());
	$("#buy_times").val(mygrid.cells(rId, 3).getValue());
	$("#bdsj").html(mygrid.cells(rId, 5).getValue());
	$("#1jbjzt").html(mygrid.cells(rId, 6).getValue());
	$("#1jdxbj").html(mygrid.cells(rId, 7).getValue());
	$("#1jsybj").html(mygrid.cells(rId, 8).getValue());
	$("#2jbjzt").html(mygrid.cells(rId, 9).getValue());
	$("#2jdxbj").html(mygrid.cells(rId, 10).getValue());
	$("#fhz").html(mygrid.cells(rId, 11).getValue());
	$("#fhzqr").html(mygrid.cells(rId, 12).getValue());
	$("#jmhh").html(rtnValue.rows[idx].data[24]);
	$("#bh").html(rtnValue.rows[idx].data[25]);
	$("#sflx").html(rtnValue.rows[idx].data[26]);
	$("#fkfs").html(rtnValue.rows[idx].data[27]);
	$("#yffblx").html(rtnValue.rows[idx].data[28]);
	//阶梯、计算信息赋值
	$("#jt_total_zbdl").val(mygrid.cells(rId,13).getValue());
	$("#fxdf_iall_money").val(mygrid.cells(rId,17).getValue());//17
	$("#fxdf_ym").val(mygrid.cells(rId,20).getValue());//20	
	
	$("#jt_total_dl").val(mygrid.cells(rId,14).getValue());//14
	$("#fxdf_iall_money2").val(rtnValue.rows[idx].data[30]);
	$("#fxdf_data_ymd").val(mygrid.cells(rId,21).getValue());//21
	$("#jt_reset_ymd").val(mygrid.cells(rId,15).getValue());//15
	
	$("#fxdf_remain").val(mygrid.cells(rId,18).getValue());//18
	$("#fxdf_calc_mdhmi").val(mygrid.cells(rId,22).getValue());//22
	$("#jt_reset_mdhmi").val(mygrid.cells(rId,16).getValue());//16
	$("#fxdf_remain2").val(mygrid.cells(rId,19).getValue());
	
}

function clear(){
	$("#yhmc").html("");
	$("#bjfa").html("");
	$("#buy_times").val("");
	$("#bdsj").html("");
	$("#1jbjzt").html("");
	$("#1jdxbj").html("");
	$("#1jsybj").html("");
	$("#2jbjzt").html("");
	$("#2jdxbj").html("");
	$("#fhz").html("");
	$("#fhzqr").html("");
	$("#jmhh").html("");
	$("#bh").html("");
	$("#sflx").html("");
	$("#fkfs").html("");
	$("#yffblx").html("");
	//阶梯、计算信息赋值
	$("#jt_total_zbdl").val("");
	$("#fxdf_iall_money").val("");
	$("#fxdf_ym").val("");	
	
	$("#jt_total_dl").val("");
	$("#fxdf_iall_money2").val("");
	$("#fxdf_data_ymd").val("");
	$("#jt_reset_ymd").val("");
	
	$("#fxdf_remain").val("");
	$("#fxdf_calc_mdhmi").val("");
	$("#jt_reset_mdhmi").val("");
	$("#fxdf_remain2").val("");
}


function doUpdate(){//更新
	
	if(!confirm("确定要更新吗?")) return;
	
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择客户信息!");
		return;
	}
	
	var params = {};
	params.rtuId = rtu_id;
	params.mpId = mp_id;
	params.updType = $("#updType").val();
    params.buy_times=$("#buy_times").val() =="" ? 0 : $("#buy_times").val();
	params.jt_total_zbdl = $("#jt_total_zbdl").val() == "" ? '0' :$("#jt_total_zbdl").val();
	
	var tmp = $("#jt_reset_ymd").val();
	if(tmp == ""){
		params.jt_reset_ymd = 0;
	}else{
		params.jt_reset_ymd =  tmp.substring(0,4) + tmp.substring(5,7) + tmp.substring(8,10);
	}
	
	params.fxdf_iall_money  = $("#fxdf_iall_money").val() == "" ? '0' :$("#fxdf_iall_money").val();
	params.fxdf_iall_money2 = $("#fxdf_iall_money2").val() == "" ? '0' :$("#fxdf_iall_money2").val();
	
	params.fxdf_remain  = $("#fxdf_remain").val()  == "" ? '0' :$("#fxdf_remain").val();
	params.fxdf_remain2 = $("#fxdf_remain2").val() == "" ? '0' :$("#fxdf_remain2").val();
	
    tmp = $("#fxdf_ym").val();
	if(tmp == ""){
	    params.fxdf_ym ='0';
	}
	if(tmp != ""){
		params.fxdf_ym = tmp.substring(0,4) + tmp.substring(5,7);
	}	
	
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	loading.loading();
	
	var json_str = jsonString.json2String(params);
	
    var tmp  = mygrid.getSelectedRowId();//得到选中当前行的ID
    selIndex = mygrid.getRowIndex(tmp);
	
	//强制更新-开始
	loading.loading();
	$.post(def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//执行强制更新
		{
			firstLastFlag : true,
			userData1: rtu_id,
			mpid : mp_id,
			gsmflag : 0,
			userData2: ComntUseropDef.YFF_DYOPER_UDPATESTATE,
			dyOperUpdateState:json_str						
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == SDDef.SUCCESS) {
				alert("接收预付费服务任务成功...");
				//selcons(); 不重新查询将grid改变值直接赋值即可
				update_grid()
				
			}
			else {
	  			alert("接收预付费服务任务失败...");
			}
		}
	);
	//强制更新-结束			
}

function update_grid() {
	var rId = mygrid.getSelectedRowId();
	mygrid.cells(rId,  3).setValue($("#buy_times").val());			//购电次数
	mygrid.cells(rId, 13).setValue($("#jt_total_zbdl").val());	//阶梯追补累计用电量
	mygrid.cells(rId, 17).setValue($("#fxdf_iall_money").val());//发行电费当月缴费总金额
	mygrid.cells(rId, 18).setValue($("#fxdf_remain").val());	//发行电费后剩余金额
	mygrid.cells(rId, 19).setValue($("#fxdf_remain2").val());	//发行电费后剩余金额2
	mygrid.cells(rId, 15).setValue($("#jt_reset_ymd").val());   //阶梯切换日期
	mygrid.cells(rId, 20).setValue($("#fxdf_ym").val());        //发行电费年月
}