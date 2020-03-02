var cok_fzman			= "cok_cons";//存储上次查询的所属联系人
$(document).ready(function(){
	initOrg();
	initGrid();
	
});

	//电表名称
	//表号
	//所属片区
	//片区号	
	//所属供电所
	//费率方案
	//报警方案
	//功率限值
	//无脉冲断电时间
	//断电保护

function initGrid() {
	GridPage.init();
	GridPage.gridobj.setHeader("序号,电表名称,表号,所属片区,区域号,供电所,费率方案,报警方案,功率限值,无脉冲断电时间,断电保护,pt,ct,表类型;");
	GridPage.gridobj.setInitWidths("50,120,100,120,90,100,80,80,80,80,80,80,80,80");
	GridPage.gridobj.setColAlign("center,left,left,left,left,left,left,left,left,left,left,left,left,left")
	GridPage.gridobj.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	GridPage.gridobj.enableTooltips("false,false,false,false,false,false,false,false,false,false,false,false,false,false");
	
	GridPage.gridobj.init();
	GridPage.gridobj.setColumnHidden(6,true);
	GridPage.gridobj.setColumnHidden(7,true);
	GridPage.gridobj.setColumnHidden(8,true);
	GridPage.gridobj.setColumnHidden(9,true);
	GridPage.gridobj.setColumnHidden(10,true);
	GridPage.gridobj.setColumnHidden(11,true);
	GridPage.gridobj.setColumnHidden(12,true);
	GridPage.gridobj.setColumnHidden(13,true);
	GridPage.gridobj.setSkin("light");
	GridPage.gridobj.attachEvent("onRowDblClicked",onRowDblClicked);
}

function initOrg(){
	$.post(def.basePath + "ajax/actCommon!getOrg.action",{},function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			if(data.flag != "1"){
				$("#orgId").append("<option value=-1>所有</option>");
			}
			
			for(var i=0;i<json.length;i++){
				$("#orgId").append("<option value="+json[i].value+">"+json[i].text+"</option>");
			}

			window.setTimeout('setOrgVal(' + $("#orgId").val() + ')', 100);
		}
	});
}

function setOrgVal(org_id) {
	if(!(org_id == null || org_id == "")){
		$("#orgId").val(org_id);
	}
	getArea();
	getLineFzMan();
}

function getArea(){//根据供电所ID查询所属片区列表，填充$("#areaId")。
	var orgid=$("#orgId").val();
	if(orgid == undefined || orgid == "-1"){
		$("#areaId").html("<option value=-1>所有</option>");
		return;
	}
	$.post(def.basePath + "ajax/actCommon!getAreaByOrg.action",{value:orgid},function(data){
		if(data.result == ""){
			$("#areaId").html("<option value=-1>所有</option>");
		}else{
			var json = eval('(' + data.result + ')');
			var option="<option value=-1>所有</option>";
			for(var i=0;i<json.length;i++){
				option += "<option value=" + json[i].value + ">" + json[i].text + "</option>";
			}
			$("#areaId").html(option);		
		}
	});
}

function getLineFzMan(){//根据供电所ID查询线路负责人列表，填充$("#fzmanId")。
	var orgid=$("#orgId").val();
	if(orgid == undefined || orgid == "-1"){
		$("#fzmanId").html("<option value=-1>所有</option>");
		return;
	}
	$.post(def.basePath + "ajax/actCommon!getLineFzByOrg.action",{value:orgid},function(data){
		if(data.result == ""){
			$("#fzmanId").html("<option value=-1>所有</option>");
		}else{
			var json = eval('(' + data.result + ')');
			var option="<option value=-1>所有</option>";
			for(var i=0;i<json.length;i++){
				option += "<option value=" + json[i].value + ">" + json[i].text + "</option>";
			}
			$("#fzmanId").html(option);		
			//将cookie里面的fzman回显
			var cok_fzman_val = cookie.get(cok_fzman);
			window.setTimeout('$("#fzmanId").val(' + cok_fzman_val + ')',1);
		}
	});
}

function search(){//执行查询
	
	GridPage.gridobj.clearAll();
	
	var jsdata = "";
	if($("#orgId").val()==-1 && $("#fzmanId").val()==-1 && $("#meterName").val()=="" && $("#areaId").val()=="" && $("#rtuName").val()=="" && $("#areaNo").val()=="" && $("#meterAddr").val()==""){
			
	}else{
		jsdata = "{\"orgId\":\"" + $("#orgId").val() + "\",";
		jsdata = jsdata + "\"fzmanId\":\"" + $("#fzmanId").val() + "\",";
		jsdata = jsdata + "\"rtuName\":\"" + $("#rtuName").val() + "\",";
		jsdata = jsdata + "\"areaId\":\"" + $("#areaId").val() + "\",";
		jsdata = jsdata + "\"areaNo\":\"" + $("#areaNo").val() + "\",";
		jsdata = jsdata + "\"meterName\":\"" + $("#meterName").val() + "\",";
		jsdata = jsdata + "\"meterAddr\":\"" + $("#meterAddr").val() + "\"}";		
	}
	
	loading.loading();
	$.post(def.basePath + "ajaxnp/actConsPara!getMeterList.action",{pageSize:$("#prs").val(), result : jsdata},function(data){
		loading.loaded();
		if(data.result != ""){
			GridPage.dataResult = data.result;
			GridPage.currentPage = 1;
			GridPage.showgrid();
			
			GridPage.gridobj.selectRow(0,true);//默认选中第一行
		}
	});  
}

function returnWin() {
	var id = GridPage.gridobj.getSelectedId();
	if(id == null){
		alert("请选择一条记录");
		return;
	}
	onRowDblClicked(id);
}

function onRowDblClicked(id) {
	if(!id) id = GridPage.gridobj.getSelectedId();
	var rtu_id = id.split("_")[0];
	var mp_id = id.split("_")[1];
	var meter_desc = GridPage.gridobj.cells(id, 1).getValue();
	var meter_id = GridPage.gridobj.cells(id, 2).getValue();
	var area_code = GridPage.gridobj.cells(id, 4).getValue();
	var feeproj_id = GridPage.gridobj.cells(id, 6).getValue();
	var yffalarm_id = GridPage.gridobj.cells(id, 7).getValue();
	var pow_limit = GridPage.gridobj.cells(id, 8).getValue();
	var nocycut_min = GridPage.gridobj.cells(id, 9).getValue();
	var powerup_prot = GridPage.gridobj.cells(id, 10).getValue();
	var pt = GridPage.gridobj.cells(id, 11).getValue();
	var ct = GridPage.gridobj.cells(id, 12).getValue();
	var yffmeter_type = GridPage.gridobj.cells(id, 13).getValue();
	if(pt == "") pt = 1;
	if(ct == "") ct = 1;
	var ptct = pt * ct;
	
	var ret = {rtu_id : rtu_id, mp_id : mp_id, meter_desc : meter_desc, meter_id : meter_id, area_code : area_code, feeproj_id : feeproj_id, yffalarm_id : yffalarm_id, pow_limit : pow_limit, nocycut_min : nocycut_min, powerup_prot : powerup_prot, ptct : ptct, yffmeter_type : yffmeter_type};
	
	var result = feeproj_id + "," + yffalarm_id;
	$.post(def.basePath + "ajaxnp/actConsPara!getFeeAndAlarm.action", {result : result}, function(data) {
		if(data.result != "") {
			var retval = data.result.split(",");
			ret.feeproj_id = retval[0];
			ret.yffalarm_id = retval[1];
		}
		window.returnValue = ret;
		window.close();
	});
	
	
}