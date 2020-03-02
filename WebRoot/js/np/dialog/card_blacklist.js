var retValue = null;
var card_no = "1111111111111111"
$(document).ready(function(){
	initPage();
	setDisabled(true);
	$("#sel_meter").click(function(){		sel_meter();	});//电表选择
	
	$("#write_useflag").click(function(){setUseFlag();});	//设置启停标志
	$("#create_card").click(function(){create_card();});	//制作回抄卡
	$("#black_add").click(function(){write_blackList(SDDef.NPCARD_KEEXPAN_BLACK_ADD);});		//添加黑名单
	$("#black_del").click(function(){write_blackList(SDDef.NPCARD_KEEXPAN_BLACK_DEL);});		//删除黑名单
	$("#black_clear").click(function(){clear_blackList();});	//清空黑名单
	
	$("#read_card").click(function(){read_card();});		//读回抄卡
	
});

function setUseFlag(){//设置启停标志
	var param={};
	param.op_type  = SDDef.NPCARD_KEEXPAN_OTHER;
	param.cardno   = card_no;
	param.areano   = $("#area_code").val();
	param.usedflag = $("#isUsed").val();

	$.post( def.basePath + "ajaxoper/actWebCard!getWriteCardStrNpExt.action", 		//后台处理程序,后台反写内容尚未完善,还需补充!
		{
			result	: jsonString.json2String(param)
		},
		function(data) {			    	//回传函数
			loading.loaded();
			var retStr 	= data.result;
			var ret_json = window.top.card_comm.write_extendcard(retStr);
			if(ret_json.errno  == 0){
				alert("写卡成功！")
			}else{				
				alert("写卡失败！\n" + ret_json.errsr);
			}
		}
	);
}

function create_card(){//制作回抄卡
	var param={};
	param.op_type  = SDDef.NPCARD_KEEXPAN_BLACK_READ;
	param.cardno   = card_no;
	param.areano   = $("#area_code").val();
	param.sub_gno  = $("#group").val();

	$.post( def.basePath + "ajaxoper/actWebCard!getWriteCardStrNpExt.action", 		//后台处理程序,后台反写内容尚未完善,还需补充!
		{
			result	: jsonString.json2String(param)
		},
		function(data) {			    	//回传函数
			loading.loaded();
			var retStr 	= data.result;
//			alert(retStr);
			var ret_json = window.top.card_comm.write_extendcard(retStr);
			if(ret_json.errno  == 0){
				alert("写卡成功！")
			}else{				
				alert("写卡失败！\n" + ret_json.errsr);
			}
		}
	);
	
}

function write_blackList(type){//添加/删除 黑名单
	var param={};
	param.op_type  = type;
	param.cardno   = card_no;
	param.areano   = $("#area_code").val();
	
	var block_no = 0;
	var block_list = "";
	for(var i=0;i< mygrid1.getRowsNum();i++){
		if(mygrid1.cells2(i, 0).getValue() == 1){//选中
			block_no++;
			if(block_no>10){
				alert("一次最多允许设置10个卡,请重新选择。");
				return;
			}
			block_list += "|"+ mygrid1.cells2(i, 7).getValue();
		}	
	}
	for(i=block_no;i<10;i++){
		block_list+="|";
	}
	param.block_no  = block_no; 
	param.blkcardno = block_list.substring(1);
	
		
	$.post( def.basePath + "ajaxoper/actWebCard!getWriteCardStrNpExt.action", 		//后台处理程序,后台反写内容尚未完善,还需补充!
		{
			result	: jsonString.json2String(param)
		},
		function(data) {			    	//回传函数
			loading.loaded();
			var retStr 	= data.result;
			var ret_json = window.top.card_comm.write_extendcard(retStr);
			if(ret_json.errno  == 0){
				alert("写卡成功！")
			}else{				
				alert("写卡失败！\n" + ret_json.errsr);
			}
		}
	);
}

function clear_blackList(){//清空黑名单
	var param={};
	param.op_type  = SDDef.NPCARD_KEEXPAN_BLACK_CLEAR;
	param.cardno   = card_no;
	param.areano   = $("#area_code").val();

	$.post( def.basePath + "ajaxoper/actWebCard!getWriteCardStrNpExt.action", 		//后台处理程序,后台反写内容尚未完善,还需补充!
		{
			result	: jsonString.json2String(param)
		},
		function(data) {			    	//回传函数
			loading.loaded();
			var retStr 	= data.result;
//			alert(retStr);
			var ret_json = window.top.card_comm.write_extendcard(retStr);
			if(ret_json.errno  == 0){
				alert("写卡成功！")
			}else{				
				alert("写卡失败！\n" + ret_json.errsr);
			}
		}
	);
}

function read_card(){//读回抄卡
	var ret_json = window.top.card_comm.read_extendcard();
	
	if(ret_json.errno != 0){				
		alert("读卡失败！\n" + ret_json.errsr);
		return;
	}
	//读卡成功，显示插表后卡内的返写用户信息。
	var cardInfo = ret_json.strinfo.split("|");
	if(cardInfo[2] != SDDef.NPCARD_KEEXPAN_BLACK_READ){
		alert("卡类型不是回抄卡！");
		return;		
	}else{
		if(cardInfo[19] != SDDef.NPCARD_KEEXPAN_BLACK_READ){
			alert("返写信息中的卡类型不是回抄卡，可能是未插卡到电表。");
			return;		
		}
	}
	if(cardInfo[21]==0){
		alert("黑名单为空，无记录。")
		return;
	}
	ret_json.strarr = cardInfo;
	
/*	var str = "KE_005|81|4|0|3|0311201303160001|1111111111111111||0311201303160001|0311201303160002|0311201303160003|0000000000000000|0000000000000000|0000000000000000|0000000000000000|0000000000000000|0000000000000000|0000000000000000|129|4|0|10||1111111111111111||0311201303160001|130408195018|0311201303160002|130408195018|0311201303160003|130408195018|0311201303160004|130408195018|0311201303160005|130408195018|0311201303160006|130408195018|0311201303160007|130408195018|0311201303160008|130408195018|0311201303160009|130408195018|0311201303160010|130408195018|";
	var ret_json={};
	ret_json.strarr = str.split("|");
	*/
	
	mygrid.clearAll();
	$.post( def.basePath + "ajaxoper/actWebCard!getNpExtConsInfo.action", 		//后台处理程序,后台反写内容尚未完善,还需补充!
		{
			result	: jsonString.json2String(ret_json)
		},
		function(data) {			    	//回传函数
			//loading.loaded();
			if(data.result != ""){
				var json = eval('(' + data.result + ')');
				mygrid.parse(json, "", "json");
				mygrid.selectRow(0,true);//默认选中第一行
				$("#excPara").val(encodeURI(jsonString.json2String(json)));
			}
		}
	);
}

//查询电表
function sel_meter() {
	modalDialog.height = 450;
	modalDialog.width = 650;
	modalDialog.url = def.basePath + "jsp/np/dialog/card_selectmeter.jsp";
	modalDialog.param = {meter_id : ""};
	var ret = modalDialog.show();
	if(!ret) return;
	
	retValue = ret;
	$("#esam_no").val(ret.meter_desc);
	$("#area_code").val(ret.area_code);
	setDisabled(false);
	searchGrid2();//检索完毕刷新“黑名单添加删除”列表
}

function initPage(){
	initgrid();
	initgrid2();
	initDiction();
}

function initgrid(){
	mygrid = new dhtmlXGridObject('gridbox1');
	var gridHeader           = "序号,客户名称,卡号,设置时间";
	var gridColAlign         = "center,left,left,left";
	var gridColTypes         = "ro,ro,ro,ro";
	mygrid.setImagePath(def.basePath + "images/grid/imgs/");
	mygrid.setHeader(gridHeader);
	mygrid.setInitWidthsP("12,23,24,*");
	mygrid.setColAlign(gridColAlign);
	mygrid.setColTypes(gridColTypes);
	mygrid.init();
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val("int,str,str,str");
	$("#filename").val(encodeURI("回抄电表内黑名单"));
	
}

function initgrid2(){	
	mygrid1 = new dhtmlXGridObject('gridbox2');
	var gridHeader           = "<img src='" + def.basePath + "images/grid/imgs/item_chk0.gif' onclick='selectAllOrNone(this);' />,序号,所属片区,客户编号,客户名称,更换日期,更换时间,旧卡号,新卡号";
	var gridColAlign         = "center,center,left,left,left,left,left,left,left";
	var gridColTypes         = "ch,ro,ro,ro,ro,ro,ro,ro,ro";
	mygrid1.setImagePath(def.basePath + "images/grid/imgs/");
	mygrid1.setHeader(gridHeader);
	mygrid1.setInitWidthsP("5,5,10,15,10,15,12,15,*");
	mygrid1.setColAlign(gridColAlign);
	mygrid1.setColTypes(gridColTypes);
	mygrid1.init();	
	$("#vfreeze1").val(1);
	$("#hfreeze1").val(0);
	$("#header1").val(encodeURI(gridHeader));
	$("#colType1").val("ch,int,str,str,str,str,str,str,str");
	$("#filename1").val(encodeURI("添加删除黑名单"));
	
}

function searchGrid2(){//查询数据库填充 “黑名单添加删除”列表
	mygrid1.clearAll();
	if(retValue){
		var param = retValue.rtu_id + "," + retValue.mp_id;
		$.post(def.basePath + "ajaxnp/actConsPara!getBlackList0.action",{result : param}, function(data){
			if(data.result != ""){
				var json = eval('(' + data.result + ')');
				mygrid1.parse(json, "", "json");
				mygrid1.selectRow(0,true);//默认选中第一行
				$("#excPara1").val(encodeURI(jsonString.json2String(json)));
			}
		})
	}
}

function initDiction(){
	var text = '{item:["' + Dict.DICTITEM_STARTUPFLAG + '"]}';
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : text}, function(data){
		var json = eval('(' + data.result + ')');
		var iid = 0;
		var dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_STARTUPFLAG);
		for ( var i = 0; i < dict.length; i++) {
			$("#isUsed").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
	});
}

function selectAllOrNone(checked) {      //全选&全不选
	if(checked.src.indexOf("_dis.gif")!=-1){
		return;
	}
	var flag = false;
	if (checked.src.indexOf("item_chk0.gif") != -1) {
		flag = true;
		checked.src = def.basePath + 'images/grid/imgs/item_chk1.gif';
	} else if (checked.src.indexOf("item_chk1.gif") != -1) {
		flag = false;
		checked.src = def.basePath +  'images/grid/imgs/item_chk0.gif';
	}
	
	for ( var i = 0; i < mygrid1.getRowsNum(); i++) {
		mygrid1.cells2(i, 0).setValue(flag);
	}
		
}

function setDisabled(flag){
	$("#write_useflag").attr('disabled',flag);
	$("#create_card").attr('disabled',flag);
	$("#black_add").attr('disabled',flag);
	$("#black_del").attr('disabled',flag);
	$("#read_card").attr('disabled',flag);
	$("#black_clear").attr('disabled',flag);
	$("#toexcel").attr('disabled',flag);
	$("#toexcel1").attr('disabled',flag);
}

function dcExcel(){
	var len = document.getElementById("excPara").value.length;
	
	if(len > 1280000){
		alert("数据量大，不能导出excel");
		return;
	}
	
	_toxls.submit();
}
function dcExcel1(){
	var len = document.getElementById("excPara1").value.length;
	
	if(len > 1280000){
		alert("数据量大，不能导出excel");
		return;
	}
	
	_toxls1.submit();
}