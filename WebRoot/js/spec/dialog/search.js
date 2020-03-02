var consPara = "";

//20130906 zhp	376终端手工合闸，只查询376终端。增加参数sghz_flag
function doSearch(type,operType,rtnValue,sghz_flag){//打开检索页

	//type:   页面类型：卡式-ks/远程-yc/主站-zz ;
	//operType:定义任务操作中各个不同操作的ID :ComntUseropDef.YFF_DYOPER_(ADDRES|...)；
//				对于没有定义ComntUseropDef.YFF_DYOPER_xxx的页面,operType给传个ComntUseropDef之外的任意整数值即可。
	//sghz_flag:若不为undefined则后台只查询376终端
	modalDialog.height = 450;
	modalDialog.width = 650;
	modalDialog.url = def.basePath + "jsp/spec/dialog/selCons.jsp";
	modalDialog.param = {"type":type,"operType":operType,",consInfo":rtnValue,"sghz_flag":sghz_flag};
	var o = modalDialog.show();
	if(o!=undefined || o!=null){
		return o;
	}else{
		return null;
	}
}

function importBD(rtuId,zjgId){//录入表底
	
	modalDialog.height = 350;
	modalDialog.width = 600;
	modalDialog.param = {"rtuId":rtuId,"zjgId":zjgId};
	modalDialog.url = def.basePath + "jsp/spec/dialog/importBD.jsp";
	var o = modalDialog.show();
	if(o!=undefined || o!=null){
		return o;		
	}else{
		return null;
	}
}


function getYffRecs(){//缴费记录
	mygrid.clearAll();
	var param = "{\"rtu_id\":\"" + $("#rtu_id").val() + "\",\"mp_id\":\"" +$("#mp_id").val() + "\",\"top_num\":\"5\"}"; 
	$.post( def.basePath + "ajax/actCommon!getDyYFFRecs.action",{result:param},	function(data) {			    	//回传函数
		if (data.result != "") {
			var jsdata = eval('(' + data.result + ')');
			mygrid.parse(jsdata,"json");		
		}
	});   	
}

function doPayPrint(prtRows){
	modalDialog.height = 450;
	modalDialog.width = 650;
	modalDialog.url = def.basePath + "jsp/dyjc/dialog/payPrint.jsp";
	modalDialog.param = prtRows;
	modalDialog.show();	
}
