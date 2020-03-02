var returnWin_flag      = false;//returnWin()正在执行的标志位  false为不执行

$(document).ready(function(){
	initGrid();
	$("#btnOK").click(function(){returnWin();});
});

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
	
	var json = window.dialogArguments;

	GridPage.dataJson = json;
	GridPage.parseJson();
}

function onRowDblClicked(rId,cInd){
	if(rId!=undefined){
		GridPage.gridobj.selectRowById(rId);
		returnWin();
	}
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
	var jsdata  = window.dialogArguments;
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

