var consPara = "";

function doSearch(type,operType,rtnValue){//打开检索页
	//type:   页面类型：卡式-ks/远程-yc/主站-zz ;
	//operType:定义任务操作中各个不同操作的ID :ComntUseropDef.YFF_DYOPER_(ADDRES|...)；
//				对于没有定义ComntUseropDef.YFF_DYOPER_xxx的页面,operType给传个ComntUseropDef之外的任意整数值即可。
	modalDialog.height = 450;
	modalDialog.width = 650;
	modalDialog.url = def.basePath + "jsp/np/dialog/selCons.jsp";
	modalDialog.param = {"type":type,"operType":operType,",consInfo":rtnValue};
	var o = modalDialog.show();
	if(o!=undefined || o!=null){
		return o;
	}else{
		return null;
	}
}
function importBD(rtuId,mpId){//录入表底
	modalDialog.height = 320;
	modalDialog.width = 500;
	modalDialog.param = {"rtuId":rtuId,"mpId":mpId};
	modalDialog.url = def.basePath + "jsp/np/dialog/importBD.jsp";
	var o = modalDialog.show();
	if(o!=undefined || o!=null){
		return o;		
	}else{
		return null;
	}
}
function checkBDZ(str){//str，多个录入时，ID 后缀
//	var reg = new RegExp("^[0-9]\d*\.\d*|[0-9]\d*$");
//	 if($("#zbd"+str).val()!="" && !reg.test($("#zbd"+str).val())){
//		 alert("总表底[" + $("#zbd"+str).val() + "]不是合法的表底数值！");
//		 $("#zbd"+str).focus().select();
//		 return false;
//	 }
//	 if($("#jbd"+str).val()!="" && !reg.test($("#jbd"+str).val())){
//		 alert("尖表底[" + $("#jbd"+str).val() + "]不是合法的表底数值！");
//		 $("#jbd"+str).focus().select();
//		 return false;
//	 }
//	 if($("#fbd"+str).val()!="" && !reg.test($("#fbd"+str).val())){
//		 alert("峰表底[" + $("#fbd"+str).val() + "]不是合法的表底数值！");
//		 $("#fbd"+str).focus().select();
//		 return false;
//	 }
//	 if($("#pbd"+str).val()!="" && !reg.test($("#pbd"+str).val())){
//		 alert("平表底[" + $("#pbd"+str).val() + "]不是合法的表底数值！");
//		 $("#pbd"+str).focus().select();
//		 return false;
//	 }
//	 if($("#gbd"+str).val()!="" && !reg.test($("#gbd"+str).val())){
//		 alert("谷表底[" + $("#gbd"+str).val() + "]不是合法的表底数值！");
//		 $("#gbd"+str).focus().select();
//	 	return false;
//	 }
	 if(rtnBD == null){
		alert("未录入表底!");
		return false;
	 }
	 return true;
	
}

function getYffRecs(){//缴费记录
	mygrid.clearAll();
	var param = "{\"rtu_id\":\"" + $("#rtu_id").val() + "\",\"mp_id\":\"" +$("#mp_id").val() + "\",\"top_num\":\"10\"}"; 
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
	modalDialog.url = def.basePath + "jsp/np/dialog/payPrint.jsp";
	modalDialog.param = prtRows;
	modalDialog.show();	
}
