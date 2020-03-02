var rtnValue;
var pos=false;
$(document).ready(function(){
	
	mygrid.setImagePath(def.basePath +"images/grid/imgs/");
	mygrid.setHeader(yff_grid_title);
	mygrid.setInitWidths("50,150,150,150,150,150,150,145,145,120,145,120,140,140,*")
	mygrid.setColAlign("center,left,left,left,left,left,left,left,left,left,left,left,left,left")
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.attachEvent("onRowSelect", doOnRowSelected);
	mygrid.init();
	mygrid.setSkin("light");
	$("#btnSearch").click(function(){selcons()});
	$("#btnOpt").click(function(){doOpt()});//操作
});

function selcons(){//检索
	var params={};
	params.orgId = $("#org").val()==null?"-1":$("#org").val();
	params.rtuId = $("#rtu").val();
	params.searchType = $("#searchType").val();
	params.searchContent = $("#searchContent").val();
	var json_str = jsonString.json2String(params);
	mygrid.clearAll();
	$.post(def.basePath + "ajaxdyjc/actPublishRate!getsList.action",{result:json_str},function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			rtnValue = json;
			mygrid.parse(json,"json");
			mygrid.selectRow(0);	
			doOnRowSelected(mygrid.getSelectedId());
		}
	});
	
}

function doOnRowSelected(rId){
	$("#rtu_id").val(rId.split("_")[0]);
	$("#mp_id").val(rId.split("_")[1]);
	
	var idx = mygrid.getRowIndex(rId); 
	var tmp = mygrid.cells(rId, 2).getValue();
	$("#yhmc").html(tmp.split("]")[1]);
	$("#jtzqqh").html(mygrid.cells(rId, 6).getValue());
	$("#jtqhrq").html(mygrid.cells(rId, 11).getValue());
	$("#jtqhzxsh").html(mygrid.cells(rId, 12).getValue());
	$("#jmhh").html(rtnValue.rows[idx].data[14]);
	$("#fxqsrq").html(mygrid.cells(rId, 7).getValue());
	$("#cbr").html(mygrid.cells(rId, 3).getValue());
	$("#fxdfny").html(mygrid.cells(rId, 8).getValue());
	$("#jsbcrq").html(mygrid.cells(rId, 13).getValue());
	$("#zzsfbz").html(mygrid.cells(rId, 4).getValue());
	$("#fxsjrq").html(mygrid.cells(rId, 9).getValue());
	$("#fxsfsj").html(mygrid.cells(rId, 10).getValue());
	$("#fxdf_flag").html(mygrid.cells(rId, 5).getValue());
}

function doOpt(){//
	var optype = $("#opType").val();
	if(optype != 1 && optype != 2){
		alert("请选择操作类型!");
		return;
	}
	var cbDay = $("#cbr").html();
	if(cbDay == "" || cbDay.substring(0,2) == "00"){
		alert("请先配置抄表日再操作!")
	}
	var params = {};
	params.rtuId 	= $("#rtu_id").val();
	params.mpId 	= $("#mp_id").val();
	params.opType 	= optype;
	params.cbDay 	= cbDay.substring(0,2);
	
	//查询预付费状态取得开户日期和用户状态
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
			{
				firstLastFlag 	: true,
				userData1		: params.rtuId,
				mpid 			: params.mpId,
				gsmflag 		: 0,
				userData2		: ComntUseropDef.YFF_DYOPER_GPARASTATE
			},
			function(data) {			    	//回传函数
				loading.loaded();
				if (data.result == SDDef.SUCCESS) {
					var json = eval("(" + data.dyOperGParaState + ")");
					params.khDate = json.kh_date;//开户日期
					if(json.cus_state_id != SDDef.YFF_CUSSTATE_NORMAL){
						alert("用户状态【" + json.cus_state + "】，只有正常态的用户可以操作。");
					}
					else{
						if(optype == 2 ){
							if(json.feeType == SDDef.YFF_FEETYPE_JTFL || json.feeType == SDDef.YFF_FEETYPE_MIXJT )
							{
								openModalDialog(params);
							}else{
								alert("该用户没有使用阶梯类型费率方案。不需要阶梯切换。")
							}
						}else{
							openModalDialog(params);	
						}
					}
				}
				else {
					alert("查找电表信息错误,请重试。");
				}
			}
		);
	
}
function openModalDialog(params){
	
	modalDialog.width = 600;
	modalDialog.height = 500;
	modalDialog.url = def.basePath + "jsp/dyjc/dialog/fxdfDialog.jsp";
	modalDialog.param = params;
	modalDialog.show();
	
}
