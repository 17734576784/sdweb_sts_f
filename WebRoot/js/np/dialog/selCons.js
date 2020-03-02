var rowID 				= "";
var type 				= window.dialogArguments.type;
var operType 			= window.dialogArguments.operType;
var first_flag 			= true;
var cok_org				= "cok_org";//存储上次查询的所属供电所
var cok_area			= "cok_area";//存储上次查询的所属联系人
var flag			 	= true; //键盘enter标志位
var searchResult_flag 	= false;//查询结果是否为空的标志位 false 为查询结果为空
var returnWin_flag      = false;//returnWin()正在执行的标志位  false为不执行

var ptctrate			= 1;
var pt_ratio			= 1;
var ct_ratio			= 1;

$(document).ready(function(){
	initOrg();
	initGrid();
	
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
	getArea();
}

function initGrid(){
	GridPage.init();	
	GridPage.gridobj.setHeader("序号,客户ID,客户姓名,所属片区,所属供电所,农排客户编号,卡号,身份证号,自然村名称,用户地址,邮编,电话,移动电话,&nbsp;");
	GridPage.gridobj.setInitWidths("40,90,90,80,90,90,90,90,90,90,90,90,90,*");
	GridPage.gridobj.setColAlign("center,left,left,left,right,left,left,left,left,left,left,left,left")
	GridPage.gridobj.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	GridPage.gridobj.enableTooltips("false,false,true,false,false,false,false,false,false,false,false,false,false");
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

function getArea(){//根据供电所ID查询线路负责人列表，填充$("#areaId")。
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
			//将cookie里面的area回显
			var cok_area_val = cookie.get(cok_area);
			window.setTimeout('$("#areaId").val(' + cok_area_val + ')',10);
		}
	});
}

function search(){//执行查询
	
	GridPage.gridobj.clearAll();
	//将org  和fxman保存到cookie里面
	cookie.set(cok_org,  $("#orgId").val());
	cookie.set(cok_area,$("#areaId").val());
	var jsdata = "";
	if($("#orgId").val()==-1 && $("#areaId").val()==-1 && $("#consName").val()=="" && $("#identityNo").val()=="" && $("#village").val()=="" && $("#cardNo").val()==""){
			
	}else{
		jsdata = "{\"orgId\":\"" + $("#orgId").val() + "\",";			
		jsdata = jsdata + "\"areaId\":\"" + $("#areaId").val() + "\",";			
		jsdata = jsdata + "\"consName\":\"" + $("#consName").val() + "\",";			
		jsdata = jsdata + "\"consNo\":\"" + $("#consNo").val() + "\",";			
		jsdata = jsdata + "\"village\":\"" + $("#village").val() + "\",";
		jsdata = jsdata + "\"cardNo\":\"" + $("#cardNo").val() + "\"}";		
	}
	
	sType = "{\"type\":\"" + type + "\",\"operType\":\"" + operType +"\"}";
	loading.loading();
	$.post(def.basePath + "ajaxnp/actConsPara!getsearchList.action",{result:sType,pageSize:$("#prs").val(),field:jsdata},function(data){
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
	
	var selId = GridPage.gridobj.getSelectedId();   //areaId_famerId_orgId_areaCode
	if(selId == null){
		alert("请选择一条记录");
		return;
	}
	//alert("所选ID：" + selId);
	//return;
	//以下代码待更改
	var selIdx = GridPage.gridobj.getRowIndex(selId);
	var rows = null;
//	
	var jsdata  = eval('(' + GridPage.dataResult + ')');  //这里和低压的内容不同 
	var strtemp = jsdata.rows[selIdx].data;
	var temp 	= strtemp.toString().split(",");
	var starti 	= temp.length - 1;
//	
	if(selId == null){
		alert('请选择一条记录');
	}else{		
		var json_ids = selId.split("_");	//可以用定义
		returnWin_flag = true;//returnWin函数正在执行的标志
		loading.loading();
		$.post( def.basePath + "ajaxoper/actOperNp!taskProc.action", 		//后台处理程序
			{
				firstLastFlag 	: true,
				userData1		: json_ids[0], //area_id
				farmerid 		: json_ids[1],
				gsmflag 		: 0,
				userData2		: ComntUseropDef.YFF_NPOPER_GPARASTATE
			},
			function(data) {			    	//回传函数
				loading.loaded();
				if (data.result == SDDef.SUCCESS) {
					
					var json = eval("(" + data.npOperGParaState + ")");
					
					json.userno 	 = GridPage.gridobj.cells(selId, 5).getValue();
					json.username 	 = GridPage.gridobj.cells(selId, 2).getValue();
					json.identityno	 = GridPage.gridobj.cells(selId, 7).getValue();
					json.village	 = GridPage.gridobj.cells(selId, 8).getValue();
					json.tel 		 = GridPage.gridobj.cells(selId, 12).getValue();
					json.useraddr	 = GridPage.gridobj.cells(selId, 9).getValue();//用户地址
					json.card_no	 = GridPage.gridobj.cells(selId, 6).getValue();//卡号
					json.area	     = GridPage.gridobj.cells(selId, 3).getValue();//所属片区
					
					json.area_code	 =  json_ids[3];//区域号

//					
					//json.onlinetxt	 ="<b style='color:#800000'>终端" + json.cus_state + "</b>";
					//if(json.cus_state == "在线"){
					//	json.onlinesrc	 = def.basePath + "images/rtuzx_online.gif";
					//}else{
					//	json.onlinesrc	 = def.basePath + "images/rtuzx_offline.gif";
					//}
//					
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

