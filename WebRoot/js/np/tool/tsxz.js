var rtnValue;
var pos = false;
var glo_sel_id = "";
var selIndex = 0;//回显  上次选中的行数

$(document).ready(function(){
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	var yff_grid_title = "序号,片区,客户编号,客户名称,客户状态,购电次数,累计购电次数,操作类型,操作时间,缴费金额,结算金额,追补金额,总金额,上次剩余金额,当前剩余金额,累计购电金额,开户日期,销户日期";
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("50,120,120,150,100,100,100,100,180,100,100,100,100,100,100,100,180,180")
	mygrid.setColAlign("center,left,left,left,left,right,right,left,left,left,left,left,left,left,left,left,left,left")
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.attachEvent("onRowSelect", doOnRowSelected);
	mygrid.init();
	mygrid.setSkin("light");
	
	mygrid.enableSmartRendering(true);

	$("#btnSearch").click(function(){selcons()});
	$("#btnUpdate").click(function(){doUpdate()});//更新
	
});

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
	params.org = $("#org").val();
	params.area = $("#area").val();
	params.searchType = $("#searchType").val();
	params.searchContent = $("#searchContent").val();
	params.updType = $("#updType").val();
	var json_str = jsonString.json2String(params);
	mygrid.clearAll();
	loading.loading();
	$.post(def.basePath + "ajaxnp/actUpdateState!getsList.action",{result:json_str},function(data){
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

function doOnRowSelected(rId) {	//org_id rtu_id mp_id area_id farmer_id
	clear();
	glo_sel_id = rId;
	
	var col_idx = 2;
	
	$("#khbh").html(mygrid.cells(rId, col_idx++).getValue());
	$("#khmc").html(mygrid.cells(rId, col_idx++).getValue());
	$("#khzt").html(mygrid.cells(rId, col_idx++).getValue());
	$("#gdcs").val(mygrid.cells(rId, col_idx++).getValue());
	$("#ljgdcs").html(mygrid.cells(rId, col_idx++).getValue());
	$("#czlx").html(mygrid.cells(rId, col_idx++).getValue());
	$("#czsj").html(mygrid.cells(rId, col_idx++).getValue());
	$("#jfje").html(mygrid.cells(rId, col_idx++).getValue());
	$("#jsje").html(mygrid.cells(rId, col_idx++).getValue());
	$("#zbje").html(mygrid.cells(rId, col_idx++).getValue());
	$("#zje").html(mygrid.cells(rId, col_idx++).getValue());
	$("#scsyje").html(mygrid.cells(rId, col_idx++).getValue());
	$("#dqsyje").html(mygrid.cells(rId, col_idx++).getValue());
	$("#ljgdje").html(mygrid.cells(rId, col_idx++).getValue());
	$("#khrq").html(mygrid.cells(rId, col_idx++).getValue());
	$("#xhrq").html(mygrid.cells(rId, col_idx++).getValue());
}

function clear(){
	$("#khbh").html("");
	$("#khmc").html("");
	$("#khzt").html("");
	$("#gdcs").val("");
	$("#ljgdcs").html("");
	$("#czlx").html("");
	$("#czsj").html("");
	$("#jfje").html("");
	$("#jsje").html("");
	$("#zbje").html("");
	$("#zje").html("");
	$("#scsyje").html("");
	$("#dqsyje").html("");
	$("#ljgdje").html("");
	$("#khrq").html("");
	$("#xhrq").html("");
}

function doUpdate(){//更新
	
	if(!confirm("确定要更新吗?")) return;
	if(glo_sel_id == "") {
		alert("请选择客户信息!");
		return;
	}

	var row_id = glo_sel_id.split("_");
	var area_id = row_id[1], farmer_id = row_id[2];
	
	var params = {};
	params.area_id = area_id;
	params.buy_times = $("#gdcs").val() =="" ? 0 : $("#gdcs").val();
    
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	loading.loading();
	
	var json_str = jsonString.json2String(params);
	
	//强制更新-开始
	loading.loading();
	$.post(def.basePath + "ajaxoper/actOperNp!taskProc.action", 		//执行强制更新
		{
			firstLastFlag : true,
			userData1: area_id,
			farmerid : farmer_id,
			gsmflag : 0,
			userData2: ComntUseropDef.YFF_NPOPER_UDPATESTATE,
			npOperUpdateState : json_str						
		},
		function(data) {			    	//回传函数
			loading.loaded();
			window.top.addJsonOpDetail(data.detailInfo);
			if (data.result == SDDef.SUCCESS) {
				alert("接收预付费服务任务成功...");
				update_grid();
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
	mygrid.cells(rId,  5).setValue($("#gdcs").val());			//购电次数
}