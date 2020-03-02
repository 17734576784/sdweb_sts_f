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

$(document).ready(function(){
	//将光标定位在客户名称输入框内
	$("#consName").focus();
	//检测Enter事件
	$(document.body).keyup(function(){
		if(window.event.keyCode == 13){	//回车
			//除第一次之外的任何一次
			if(flag == false){
				if(searchResult_flag == true){					
					if(!returnWin_flag){
					  returnWin();
					}
				}
				if(searchResult_flag == false){
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
	$("#btnOK").click(function(){returnWin();});
	
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
			window.setTimeout('setOrgVal(' + cok_org_val + ')', 100);
		}
	});
}

function setOrgVal(org_id) {
	if(!(org_id == null || org_id == "")){
		$("#orgId").val(org_id);
	}
	getLineFzMan();
}

function initGrid(){
	GridPage.init();
	GridPage.gridobj.setImagePath(SDDef.BASEPATH + "images/grid/imgs/");
	GridPage.gridobj.setHeader("序号,终端名称,电表名称,用户名称,客户编号,电表地址,移动电话,所属供电所,线路负责人,资产编号,用户地址,生产厂家,倍率,接线方式,电表类型,预付费类型,&nbsp;");
	GridPage.gridobj.setInitWidths("40,90,90,90,80,90,90,90,90,90,90,90,90,90,90,90,*");
	GridPage.gridobj.setColAlign("center,left,left,left,left,right,left,left,left,left,left,left,left,left,left,left")
	GridPage.gridobj.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	GridPage.gridobj.enableTooltips("false,false,false,true,false,false,false,false,false,false,false,false,false,false,false,false");
	
	colSorting = "na,na,str,str,na,na,na,na,na,na,na,na,na,na,na,na";
	GridPage.gridobj.setColSorting(colSorting);//列排序
	GridPage.gridobj.init();
	GridPage.gridobj.setSkin("light");
	GridPage.gridobj.attachEvent("onRowDblClicked",onRowDblClicked);
}

function onRowDblClicked(rId,cInd){
	if(rId!=undefined){
		GridPage.gridobj.selectRowById(rId);
		returnWin();
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
			window.setTimeout('$("#fzmanId").val(' + cok_fzman_val + ')',1);
		}
	});
}

function search(){//执行查询
	
	GridPage.gridobj.clearAll();
	//将org  和fxman保存到cookie里面
	cookie.set(cok_org,  $("#orgId").val());
	cookie.set(cok_fzman,$("#fzmanId").val());
	var jsdata = "";
	if($("#orgId").val()==-1 && $("#fzmanId").val()==-1 && $("#rtuName").val()=="" && $("#consName").val()=="" && $("#residentId").val()=="" && $("#assetNo").val()=="" && $("#telNo1").val()==""){
			
	}else{
		jsdata = "{\"orgId\":\"" + $("#orgId").val() + "\",";			
		jsdata = jsdata + "\"fzmanId\":\"" + $("#fzmanId").val() + "\",";
		jsdata = jsdata + "\"rtuName\":\"" + $("#rtuName").val() + "\",";
		jsdata = jsdata + "\"consName\":\"" + $("#consName").val() + "\",";			
		jsdata = jsdata + "\"residentId\":\"" + $("#residentId").val() + "\",";			
		jsdata = jsdata + "\"comm_addr\":\"" + $("#comm_addr").val() + "\",";
		jsdata = jsdata + "\"mobile\":\"" + $("#telNo1").val() + "\"}";
		
	}
	sType = "{\"type\":\"" + type + "\",\"operType\":\"" + operType +"\"}";
	loading.loading();
	$.post(def.basePath + "ajaxdyjc/actConsPara!getsearchListExt.action",{result:sType,pageSize:$("#prs").val(),field:jsdata},function(data){
		loading.loaded();
		if(data.result != ""){
			GridPage.dataResult = data.result;
			GridPage.currentPage = 1;
			GridPage.showgrid();
			
			GridPage.gridobj.selectRow(0,true);//默认选中第一行
			//查询结果标志
			searchResult_flag = true;
		}
		else{
			searchResult_flag = false;
		}
	});  
}

function returnWin(){//关闭、返回
	
	var selId = GridPage.gridobj.getSelectedId();
	if(selId == null){
		alert("请选择一条记录");
		return;
	}
	
	var selIdx = GridPage.gridobj.getRowIndex(selId);
	selIdx = selIdx + GridPage.pgSize * (GridPage.currentPage-1);
	var rows = null;
	//GridPage.dataResult为ActConsPara中getsearchList返回的记录的所有值。界面显示的只是一部分。
	var jsdata  = eval('(' + GridPage.dataResult + ')');
	//根据当前页的行序号找到对应的值(jsdata.rows[i].data)。
	var strtemp = jsdata.rows[selIdx].data;
	var temp 	= strtemp.toString().split(",");
	var starti 	= temp.length - 1;
	
	if(selId == null){
		alert('请选择一条记录');
	}else{		
		var json_ids = selId.split("_");	//可以用定义
		returnWin_flag = true;//returnWin函数正在执行的标志
		loading.loading();
		$.post( def.basePath + "ajaxoper/actOperDy!taskProc.action", 		//后台处理程序
			{
				firstLastFlag 	: true,
				userData1		: json_ids[0],
				mpid 			: json_ids[1],
				gsmflag 		: 0,
				userData2		: ComntUseropDef.YFF_DYOPER_GPARASTATE
			},
			function(data) {			    	//回传函数
				loading.loaded();
				if (data.result == SDDef.SUCCESS) {
					
					var json = eval("(" + data.dyOperGParaState + ")");
					
					//data.dyOperGParaState返回的主要是预付费参数及状态
					//主要：cacl_type, cacl_type_desc, 
					
					
					json.username 	 = GridPage.gridobj.cells(selId, 3).getValue();
					json.userno 	 = GridPage.gridobj.cells(selId, 4).getValue();
					json.tel 		 = GridPage.gridobj.cells(selId, 6).getValue();
					json.orgname	 = GridPage.gridobj.cells(selId, 7).getValue();
					json.useraddr	 = GridPage.gridobj.cells(selId, 10).getValue();
					json.factory 	 = GridPage.gridobj.cells(selId, 11).getValue();
					json.blv		 = GridPage.gridobj.cells(selId, 12).getValue();
					json.wiring_mode = GridPage.gridobj.cells(selId, 13).getValue();
					json.cardtype 	= GridPage.gridobj.cells(selId, 14).getValue();//预付费电表类型:KL_001 KL_002 JJ_001...
			
				//	json.feectrl_type 	= temp[starti - 10];	//预付费类型:FK,LK
					json.card_area 		= temp[starti - 13];	//区域码
					json.card_pass 		= temp[starti - 12];	//密码
					json.card_rand 		= temp[starti - 11];	//随机数
					json.cacl_type 		= temp[starti - 10];	//算费类型
					json.writecard_no 	= temp[starti - 9];		//写卡户号
					json.esamno		 = temp[starti - 8];		//esam表号
					json.key_version = temp[starti -1];			//秘钥版本
					json.ct	 		 = temp[starti - 2];
					json.pt	 		 = temp[starti - 5];
					json.jt_cycle_md = temp[starti];
					
					json.resident_id = json_ids[2];//增加客户id,用于更换手机号时的查询
					
					json.onlinetxt	 ="<b style='color:#800000'>终端" + json.onlineflag + "</b>";
					if(json.onlineflag == "在线"){
						json.onlinesrc	 = def.basePath + "images/rtuzx_online.gif";
					}else{
						json.onlinesrc	 = def.basePath + "images/rtuzx_offline.gif";
					}
					
					window.returnValue = json;
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

