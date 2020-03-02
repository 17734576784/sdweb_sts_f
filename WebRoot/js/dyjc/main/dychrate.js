var rtnValue;
var jsonFLFA;
var rtnBD;
$(document).ready(function(){
	$.post(def.basePath + "ajaxdyjc/actChgRate!getRateList.action",{},function(data){
		if(data.result != ""){
			jsonFLFA = eval('(' + data.result + ')');
			for(var i=0;i<jsonFLFA.rows.length;i++){
				$("#flfa").append("<option value="+jsonFLFA.rows[i].value+">"+jsonFLFA.rows[i].text+"</option>");
			}
			changeFLFA();
		}
	});
	
	dateFormat.date = dateFormat.getToday("yMd");
	var datestr = dateFormat.formatToYMD(0);
	dateFormat.date = dateFormat.getToday("H")+"00";
	datestr = datestr + " " + dateFormat.formatToHM(0);
	$("#qhsj").val(datestr);
	
	$("#btnImportBD").click(function(){Import()});
	$("#btnSearch").click(function(){selcons()});
	$("#flfa").change(function(){changeFLFA()});
	
	$("#btnChangeFL").click(function(){if(checkBDZ("")){changeFL()}});
	
	setDisabled(true);
	$("#btnSearch").focus();
	
});
function selcons(){//检索
	var rtnValue1 = doSearch("zz",ComntUseropDef.YFF_DYOPER_CHANGERATE,rtnValue);
	if(!rtnValue1){
		return;
	}
	rtnValue = rtnValue1;
	setDisabled(false);
	setConsValue(rtnValue);
	$("#flfa").val(rtnValue.feeproj_id);
	changeFLFA();
	$("#btnImportBD").focus();
	$("#btnChangeFL").attr("disabled",true);
}
function changeFL(){//更改费率
	
	var rtu_id = $("#rtu_id").val(), mp_id = $("#mp_id").val();
	if(rtu_id === "" || mp_id === ""){
		alert("请选择用户信息!");
		return;
	}
	if($("#flfa").val() == rtnValue.feeproj_id){
		alert("费率方案相同,不需更改!");
		return;
	}
	
	var params = {};
	params.rtu_id = rtu_id;
	params.mp_id = mp_id;
	params.date = rtnBD.date;
	params.time = 0;
	params.chgid = $("#flfa").val();
	var ghsj = $("#qhsj").val();
	params.chg_date = ghsj.substring(0,4) + ghsj.substring(5,7) + ghsj.substring(8,10);
	params.chg_time = ghsj.substring(12,14) + ghsj.substring(15,17)+"00";
	
	for(var i = 0; i < 3; i++){
		eval("params.mp_id"  + i + "=rtnBD.mp_id" + i);
		eval("params.bd_zy"  + i + "=rtnBD.zbd" + i);
		eval("params.bd_zyj" + i + "=rtnBD.jbd" + i);
		eval("params.bd_zyf" + i + "=rtnBD.fbd" + i);
		eval("params.bd_zyp" + i + "=rtnBD.pbd" + i);
		eval("params.bd_zyg" + i + "=rtnBD.gbd" + i);
	}
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
		{
			firstLastFlag : true,
			userData1: rtu_id,
			mpid : mp_id,
			gsmflag : 0,
			dyOperChangeRate : json_str,
			userData2 : ComntUseropDef.YFF_DYOPER_CHANGERATE
		},
		function(data) {			    	//回传函数
			loading.loaded();	
			if (data.result == "success") {
				var colors = "<b style='color:#800000'>";
				var colore = "</b>";
				rtnValue.feeproj_id = $("#flfa").val();
				$("#feeproj_desc").html(colors+$("#flfa").find("option:selected").text()+colore);
				$("#feeproj_detail").html(colors+$("#fams_n").html()+colore);
				$("#btnChangeFL").attr("disabled",true);
				//$("#btnImportBD").attr("disabled",true);
				setDisabled(true);
				alert("更改成功!");
			}
			else {
				alert("更改失败!" + (data.operErr ? data.operErr : ''))
			}
		}
	);
}

function Import(){
	var tmp = importBD($("#rtu_id").val(),$("#mp_id").val());
	if(!tmp)return;
	rtnBD = tmp;
	$("#td_bdinf").html(rtnBD.td_bdinf);
	$("#btnChangeFL").attr("disabled" , false);
}

function changeFLFA(){//费率方案 下拉框 onchange
	var val=$("#flfa").val();
	for(var i=0;i<jsonFLFA.rows.length;i++){
		if(jsonFLFA.rows[i].value == val){
			$("#fams_n").html(jsonFLFA.rows[i].desc);	
			return;
		}		
	}	
}

function setDisabled(mode){
	if(!mode){
		$("#td_bdinf").html("");
		rtnBD = null;
	}
	
	$("#qhsj").attr("disabled",mode);
	$("#flfa").attr("disabled",mode);
	$("#btnImportBD  ").attr("disabled",mode);
}

