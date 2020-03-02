var rowID 				= "";
var type 				= window.dialogArguments.type;
var operType 			= window.dialogArguments.operType;
var first_flag 			= true;
var cok_org				= "cok_org";//存储上次查询的所属供电所
var cok_fzman			= "cok_cons";//存储上次查询的所属联系人
var flag			 	= true; //键盘enter标志位
var searchResult_flag 	= false;//查询结果是否为空的标志位 false 为查询结果为空
var returnWin_flag      = false;//returnWin()正在执行的标志位  false为不执行

var ptctrate			= 1;
var pt_ratio			= 1;
var ct_ratio			= 1;
var meter_id			= 0;


var onlineflag			= "离线";

//20130906,376手工合闸标识，sghz_flag = 1则仅查询376规约终端，为undefined则查询所有终端
var sghz_flag	= window.dialogArguments.sghz_flag;

$(document).ready(function(){
	//将光标定位在客户名称输入框内
	$("#consName").focus();
	$(document.body).keyup(function(){
		if(window.event.keyCode == 13){	//回车
			//除第一次之外的任何一次
			if(flag == false){
				if(searchResult_flag==true){					
					if(!returnWin_flag){
					  returnWin2();
					}
				}
				if(searchResult_flag==false){
					search();
				}
			}
			//第一次
			if(flag == true){
				search();
				flag=false;
			}
		}
	});
	$("#search").click(function(){search();});
	$("#btnOK").click(function(){returnWin2();});

	initOrg();

	initGrid();
});

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

			var cok_org_val = cookie.get(cok_org);
			if(cok_org_val == undefined || cok_org_val == null || cok_org_val == "") {
				getLineFzMan();
			}
			else {
				window.setTimeout('setOrgVal(' + cok_org_val + ')', 10);
			}
			
		}
	});
}

function setOrgVal(org_id) {
	if(!(org_id == null || org_id == "")){
		$("#orgId").val(org_id);
	}
	$("#orgId").val(org_id);
	getLineFzMan();
}

function initGrid(){
	GridPage.init();
	GridPage.gridobj.setImagePath(SDDef.BASEPATH + "images/grid/imgs/");
//	GridPage.gridobj.setHeader("序号,客户名称,客户编号,客户地址,联系电话,所属供电所,线路负责人,资产编号,生产厂家,倍率,费控单元,&nbsp;");
	GridPage.gridobj.setHeader("序号,终端名称,客户名称,客户编号,客户地址,移动电话,所属供电所,线路负责人,终端地址,生产厂家,费控单元,卡表类型, 写卡户号, &nbsp;");	
	GridPage.gridobj.setInitWidths("40,150,150,90,150,90,90,90,90,90,90,90,120,*");
	GridPage.gridobj.setColAlign("center,left,left,left,left,left,left,left,left,left,left,left,left");
	GridPage.gridobj.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	GridPage.gridobj.enableTooltips("false,false,false,false,false,false,false,false,false,false,false,false,false,false");
	GridPage.gridobj.enableSmartRendering(true);
	
	colSorting = "na,na,str,str,na,na,na,na,na,na,na,na,na";
	GridPage.gridobj.setColSorting(colSorting);//列排序
	GridPage.gridobj.init();
	GridPage.gridobj.setSkin("light");
	GridPage.gridobj.attachEvent("onRowDblClicked",onRowDblClicked);
}

function onRowDblClicked(rId,cInd){
	if(rId!=undefined){
		GridPage.gridobj.selectRowById(rId);
		returnWin2();
	}
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
			if(cok_fzman_val == undefined || cok_fzman_val == null || cok_fzman_val == "") {
				
			}
			else {
				window.setTimeout('$("#fzmanId").val(' + cok_fzman_val + ')',1);
			}
		}
	});
}

function search(){//执行查询

	GridPage.gridobj.clearAll();
	//将org  和fxman保存到cookie里面
	cookie.set(cok_org,  $("#orgId").val());
	cookie.set(cok_fzman,$("#fzmanId").val());
	var jsdata = "";
	if($("#orgId").val()==-1 && $("#fzmanId").val()==-1 && $("#rtuName").val()=="" && $("#consName").val()=="" && $("#busiNo").val()=="" && $("#assetNo").val()=="" && $("#telNo1").val()==""){
			
	}else{
		jsdata = "{\"orgId\":\"" + $("#orgId").val() + "\",";			
		jsdata = jsdata + "\"fzmanId\":\"" + $("#fzmanId").val() + "\",";
		jsdata = jsdata + "\"rtuName\":\"" + $("#rtuName").val() + "\",";
		jsdata = jsdata + "\"consName\":\"" + $("#consName").val() + "\",";			
		jsdata = jsdata + "\"busiNo\":\"" + $("#busiNo").val() + "\",";			
		jsdata = jsdata + "\"rtu_addr\":\"" + $("#rtu_addr").val() + "\",";
		jsdata = jsdata + "\"mobile\":\"" + $("#telNo1").val() + "\"}";
		
	}
	sType = "{\"type\":\"" + type + "\",\"operType\":\"" + operType +"\"}";
	
	//20130906  向后台增加一个参数sghz_flag = 1则仅查询376规约终端，为undefined --> "no"则查询所有终端
	if(sghz_flag == undefined || sghz_flag == null)	sghz_flag = "no";
	
	loading.loading();
	$.post(def.basePath + "ajaxspec/actConsPara!getsearchList.action",
				{
					result		:	sType,
					pageSize	:	$("#prs").val(),
					field		:	jsdata,
					sghz_flag	:	sghz_flag
				},
		   function(data){
				loading.loaded();
				if(data.result != ""){
					GridPage.dataResult = data.result;
					GridPage.currentPage = 1;
					GridPage.showgrid();
					GridPage.gridobj.selectRow(0,true);
		
					//查询结果标志
					searchResult_flag = true;
				}else{
					searchResult_flag = false;
				}
	});  
}

function returnWin2() {
	var selId = GridPage.gridobj.getSelectedId();
	if(selId == null){
		alert("请选择一条记录");
		return;
	}
	
	var selIdx = GridPage.gridobj.getRowIndex(selId);
	selIdx = selIdx + GridPage.pgSize * (GridPage.currentPage-1);
	var rows = null;
	
	var jsdata  = eval('(' + GridPage.dataResult + ')');
	var strtemp = jsdata.rows[selIdx].data;
	var temp 	= strtemp.toString().split(",");
	var starti 	= temp.length-1;
	
	if(selId == null){
		alert('请选择一条记录');
		return;
	}

	ptctrate 	= 1;
	onlineflag	= "离线";
	
	var json_ids = selId.split("_");	//可以用定义	
	$.post(def.basePath + "ajaxspec/actConsPara!getConsPara2.action",{
			rtuId:json_ids[0],
			zjgId:json_ids[1]},
		function(data){

			if(data.result != ""){
				var json = eval("(" + data.result + ")");
				ptctrate = json.ratio;				
				onlineflag = json.onlineflag;
				pt_ratio = json.pt_ratio;
				ct_ratio = json.ct_ratio;
				meter_id = json.meter_id;	
			}
			
			returnWin();
			
	});  

}

function returnWin(){//关闭、返回

	var selId = GridPage.gridobj.getSelectedId();
	if(selId == null){
		alert("请选择一条记录");
		return;
	}
	
	var selIdx = GridPage.gridobj.getRowIndex(selId);
	var rows = null;
	
	var jsdata  = eval('(' + GridPage.dataResult + ')');
	var strtemp = jsdata.rows[selIdx].data;
	var temp 	= strtemp.toString().split(",");
	var starti 	= temp.length-1;
	
	if(selId == null){
		alert('请选择一条记录');
	}else{		
		var json_ids = selId.split("_");	//可以用定义
		returnWin_flag = true;//returnWin函数正在执行的标志
		loading.loading();
		$.post( def.basePath + "ajaxoper/actOperGy!taskProc.action", 		//后台处理程序
			{
				firstLastFlag : true,
				userData1: json_ids[0],
				zjgid : json_ids[1],
				gsmflag : 0,
				userData2: ComntUseropDef.YFF_GYOPER_GPARASTATE
			},
			function(data) {			    	//回传函数
				loading.loaded();
				if (data.result == SDDef.SUCCESS) {
	
					var json = eval("(" + data.gyOperGParaState + ")");
					
					json.fee_unit 	 = GridPage.gridobj.cells(selId, 10).getValue();					
					json.username 	 = GridPage.gridobj.cells(selId, 2).getValue();
					json.userno 	 = GridPage.gridobj.cells(selId, 3).getValue();
					json.useraddr	 = GridPage.gridobj.cells(selId, 4).getValue();
					json.tel 		 = GridPage.gridobj.cells(selId, 5).getValue();
					json.orgname	 = GridPage.gridobj.cells(selId, 6).getValue();//供电所名称
					json.factory 	 = GridPage.gridobj.cells(selId, 9).getValue();
					
					//add gycard
					json.cardmeter_type = GridPage.gridobj.cells(selId, 11).getValue();
					json.writecard_no 	= GridPage.gridobj.cells(selId, 12).getValue();
					
					json.meter_id	 	= meter_id;
//					json.blv 		 = GridPage.gridobj.cells(selId, 9).getValue();
					json.blv		 = ptctrate;
					json.pt_ratio	 = pt_ratio;
					json.ct_ratio	 = ct_ratio;
			
//					var online = GridPage.gridobj.cells(selId, 11).getValue();
					json.resident_id = json_ids[2];//增加客户id,用于更换手机号时的查询
					
					json.onlinetxt	 ="<b style='color:#800000'>终端" + onlineflag + "</b>";
					if(onlineflag == "在线"){
						json.onlinesrc	 = def.basePath + "images/rtuzx_online.gif";
					}else{
						json.onlinesrc	 = def.basePath + "images/rtuzx_offline.gif";
					}
					
					json.rtu_model	 = temp[starti--];
					json.prot_type	 = temp[starti--];
					
					//临时这样用吧...
					if (json.plus_time == null || json.plus_time == undefined || json.plus_time == '0') {
						json.switch_type = 0;
						json.plus_width	 = 0;
					}
					else {
						json.switch_type = 1;
						json.plus_width	 = json.plus_time;
					}
					
//					json.switch_type = temp[starti--];
//					json.plus_width	 = temp[starti--];
					
					window.returnValue = json;
					
					//
					window.close();
				}
				else {
					returnWin_flag = false;
					alert("查找电表信息错误..");
				}
			}
		);
		
		
	}
}

