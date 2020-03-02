//
var mygridbox = new dhtmlXGridObject('gridbox');
var pos = false;
//var load_flag = [false, false, false];
var cookieName = "cok_selectDate";
$(document).ready(function(){
	
	var idate = new Date();
	//qjl add start 20150212 添加抄表日期存入cookie的功能 
	var cookieDate = cookie.get(cookieName);
	if(cookieDate != ""){
		var iCookieDate = cookieDate.substring(0,4) + "年" + cookieDate.substring(4,6) + "月" + cookieDate.substring(6,8) + "日";
		$("#idate").val(iCookieDate);
	}else{
		$("#idate").val(idate.Format("yyyy年MM月dd日").toString());
	}
	//qjl add end 20150212 添加抄表日期存入cookie的功能

	
	$("#org").change(function(){
		getLineFzMan(this.value);
	});
	
	$("#fzman").change(function(){
		getRtuByFzman(this.value);
	});
	
	initOrg();
		
	initGrid();

	$("#readrmcard").click(function(){ReadRmCard();});
	$("#importrmcard").click(function(){ImportRmCard();});
});


function initGrid(){
	var header = "<img src='" + basePath + "images/grid/imgs/item_chk0.gif' onclick='selectAllOrNone(this);' id='headchk'/>,序号,居民名称,户号,卡内户号,计费方式,购电值,购电次数,囤积值,报警值,透支限值,PT,CT,表号,抄表时间,费率电价,阶梯电价,非法次数,跳闸次数,状态字1,状态字2,组合正有,正有示值,正有尖,正有峰,正有平,正有谷,反有总,反有尖,反有峰,反有平,反有谷,剩余值,透支值,累计购电值,上月正有总,上上月正有总,RTU_ID,MP_ID,RES_ID";		
	mygridbox.setImagePath(basePath + "images/grid/imgs/");
	mygridbox.setHeader(header);
	mygridbox.setInitWidths("50,50,120,120,80,80,80,60,60,60,60,60,60,120,60,60,60,60,60,60,60,80,80,60,60,60,60,60,60,60,60,60,80,60,100,100,100,0,0,0,*");
	mygridbox.setColAlign("center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center");
	mygridbox.setColTypes("ch,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygridbox.enableTooltips("false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false");//共39列
	mygridbox.init();
	mygridbox.setSkin("light");
	//mygridbox.enableSmartRendering(true);
	
	var datatype   = "int,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str";
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(header));
	$("#colType").val(datatype);
	$("#filename").val(encodeURI("抄表卡查询"));
	$("#hidecols").val("|35,36,37");//复选框后那一列记为0列(居民名称)，开始取值
}

//
//function initDB(){
//	var tables = '{tables:["'+ YDTable.TABLECLASS_OCARDTYPE_PARA + '"]}';
//	$.post(def.basePath + "ajax/actCommon!getValueAndTextExtCard.action",{tableName: tables},function(data){
//		if(data.result != ""){
//			var json = eval('(' + data.result + ')');
//			
//			var json0 = eval("json.rows[0]." + YDTable.TABLECLASS_OCARDTYPE_PARA);
//			
//			for ( var i = 0; i < json0.length; i++) {
//				$("#card_type").append("<option value="+json0[i].value+">"+json0[i].text+"</option>");
//			}
//		}
//	});
//}


function ReadRmCard()
{
	if (($("#org").val()< 0)&&($("#rtu").val() < 0)){
		alert("请选择要导入的供电所或者台区");	
		return;
	}

	loading.loading();
	var ret_json 	= window.top.card_comm.readRMcard();	//readRMcard
	loading.loaded();

	
	if(ret_json.errno != 0){
		alert("读抄表卡失败！\n" + ret_json.errstr);
		return;
	}

	ret_json.strinfo += + $("#org").val() +"|"+ $("#fzman").val() +"|" + $("#rtu").val();

//	ret_json.strinfo += + $("#org").val() +"|"+ $("#rtu").val();
//	var ret_json = {};
//	ret_json.strinfo = "|RM_001|0|6||0|178.00|15|9999.00|10.00|102.00|1.00|1.00|040224|0|0|0|0|0|0|0|2306.00|2306.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|104.00|0.00|2410.00|0.00|0.00||0|357.00|11|9999.00|10.00|0.00|1.00|1.00|000225|0|0|0|0|0|0|0|2177.00|2177.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|412.00|0.00|2589.00|0.00|0.00||0|344.00|16|9999.00|10.00|0.00|1.00|1.00|000226|0|0|0|0|0|0|0|2278.00|2278.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|348.00|0.00|2626.00|0.00|0.00||0|178.00|6|9999.00|10.00|0.00|1.00|1.00|000686|0|0|0|0|0|0|0|437.00|437.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|141.00|0.00|578.00|0.00|0.00||0|200.00|6|9999.00|10.00|0.00|1.00|1.00|000257|0|0|0|0|0|0|0|724.00|724.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|142.00|0.00|866.00|0.00|0.00||0|200.00|7|9999.00|10.00|0.00|1.00|1.00|000256|0|0|0|0|0|0|0|754.00|754.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|345.00|0.00|1099.00|0.00|0.00|" + $("#org").val() +"|"+ $("#fzman").val() +"|" + $("#rtu").val();	
//	ret_json.str|0|6||0|178.00|15|9999.00|10.00|102.00|1.00|1.00|175661|0|0|0|0|0|0|0|2306.00|2306.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|104.00|0.00|2410.00|0.00|0.00||0|357.00|11|9999.00|10.00|0.00|1.00|1.00|175445|0|0|0|0|0|0|0|2177.00|2177.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|412.00|0.00|2589.00|0.00|0.00||0|344.00|16|9999.00|10.00|0.00|1.00|1.00|000226|0|0|0|0|0|0|0|2278.00|2278.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|348.00|0.00|2626.00|0.00|0.00||0|178.00|6|9999.00|10.00|0.00|1.00|1.00|000686|0|0|0|0|0|0|0|437.00|437.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|141.00|0.00|578.00|0.00|0.00||0|200.00|6|9999.00|10.00|0.00|1.00|1.00|000257|0|0|0|0|0|0|0|724.00|724.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|142.00|0.00|866.00|0.00|0.00||0|200.00|7|9999.00|10.00|0.00|1.00|1.00|000256|0|0|0|0|0|0|0|754.00|754.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|0.00|345.00|0.00|1099.00|0.00|0.00|" + $("#org").val() +"|"+ $("#fzman").val() +"|" + $("#rtu").val();
//	ret_json.strinfo = "|JJ_003info = "|RM_001|40|01|000000456789|1|2|3|4|5|6|7|8|000000456789|9|10|11|12|13|14|15|000016.00|000021.23|17|18|19|20|21|22|23|24|25|000087.50|26|000087.50|27|28|" + $("#rtu").val();
//	alert(ret_json.strinfo);

	mygridbox.clearAll();		//清除上次内容
	loading.loading();
	$.post( def.basePath + "ajaxdyjc/actReadMeter!ReadRMCard.action", 		//后台处理程序
		{
			result	: ret_json.strinfo
		},
		function(data) {			    	//回传函数
			loading.loaded();
			if (data.result == "success") {
			//	var retStr 	 = data.rmback.toString();
				var json = eval('(' + data.rmback + ')');
				
				mygridbox.parse(json, "", "json");
				$("#excPara").val(encodeURI(jsonString.json2String(json)));
				//alert("读抄表卡成功！");
			}
			else {
				alert("解析抄表卡数据失败..." + (data.operErr ? data.operErr : ''));
			}
		}
	);
}

function ImportRmCard()
{
	for ( var i = 0; i < mygridbox.getRowsNum(); i++) {
		if(mygridbox.cells2(i, 0).getValue() == 1){
			break;	
		}
	}

	if (i >= mygridbox.getRowsNum()) {
		alert("请至少选中一行导入！");
		return;
	}
	
	var date = getDate_Time($("#idate").val());
	if(!confirm("您选择的日期为" + date + ",确认导入？")){
		return;	
	}
	
	cookie.set(cookieName,date);	//qjl add 20150212 导入日期存入cookie

  	var seledrow = "{rows:[";
	for ( var i = 0; i < mygridbox.getRowsNum(); i++) {
		
		if(mygridbox.cells2(i, 0).getValue() == 1){
			var rtu_id = parseInt(chkString(mygridbox.cells2(i, 37).getValue()));
			var mp_id = parseInt(chkString(mygridbox.cells2(i, 38).getValue()));
			if ((rtu_id <= 0) || (mp_id <= 0))	continue;
			
			var j = 1;
			seledrow += "{op_date:\"" 			+ date;
			seledrow += "\",row_index:\"" 		+ chkString(mygridbox.cells2(i, j++).getValue());
			seledrow += "\",res_desc:\"" 		+ chkString(mygridbox.cells2(i, j++).getValue());	
			seledrow += "\",cons_no :\"" 		+ chkString(mygridbox.cells2(i, j++).getValue())  + "\",writecard_no:\"" 	+	chkString(mygridbox.cells2(i, j++).getValue());
			seledrow += "\",cacl_type:\"" 		+ chkString(mygridbox.cells2(i, j++).getValue()) 	+ "\",pay_val:\""		+	chkString(mygridbox.cells2(i, j++).getValue());
			seledrow += "\",buy_times:\"" 		+ chkString(mygridbox.cells2(i, j++).getValue()) 	+ "\",limit_val:\""		+	chkString(mygridbox.cells2(i, j++).getValue());
			seledrow += "\",alarm_val:\"" 		+ chkString(mygridbox.cells2(i, j++).getValue()) 	+ "\",ztlimit_val:\"" 	+ 	chkString(mygridbox.cells2(i, j++).getValue());
			seledrow += "\",pt:\"" 				+ chkString(mygridbox.cells2(i, j++).getValue()) 	+ "\",ct:\"" 			+	chkString(mygridbox.cells2(i, j++).getValue());
			seledrow += "\",meter_id:\"" 		+ chkString(mygridbox.cells2(i, j++).getValue()) 	+ "\",rm_date:\"" 		+ 	chkString(mygridbox.cells2(i, j++).getValue());
			seledrow += "\",cur_fei:\"" 		+ chkString(mygridbox.cells2(i, j++).getValue()) 	+ "\",cur_jtfei:\"" 	+ 	chkString(mygridbox.cells2(i, j++).getValue());
			seledrow += "\",errin_num:\"" 		+ chkString(mygridbox.cells2(i, j++).getValue()) 	+ "\",break_num:\"" 	+ 	chkString(mygridbox.cells2(i, j++).getValue());
			seledrow += "\",dbstate1:\"" 		+ chkString(mygridbox.cells2(i, j++).getValue()) 	+ "\",dbstate2:\"" 		+ 	chkString(mygridbox.cells2(i, j++).getValue());
			seledrow += "\",bd_zzyz:\"" 		+ chkString(mygridbox.cells2(i, j++).getValue()) 	+ "\",bd_zyz:\"" 		+ 	chkString(mygridbox.cells2(i, j++).getValue());
			seledrow += "\",bd_zyj:\"" 			+ chkString(mygridbox.cells2(i, j++).getValue()) 	+ "\",bd_zyf:\"" 		+ 	chkString(mygridbox.cells2(i, j++).getValue());
			seledrow += "\",bd_zyp:\"" 			+ chkString(mygridbox.cells2(i, j++).getValue()) 	+ "\",bd_zyg:\"" 		+ 	chkString(mygridbox.cells2(i, j++).getValue());
			seledrow += "\",bd_fyz:\"" 			+ chkString(mygridbox.cells2(i, j++).getValue()) 	+ "\",bd_fyj:\"" 		+ 	chkString(mygridbox.cells2(i, j++).getValue());
			seledrow += "\",bd_fyf:\"" 			+ chkString(mygridbox.cells2(i, j++).getValue()) 	+ "\",bd_fyp:\"" 		+ 	chkString(mygridbox.cells2(i, j++).getValue());
			seledrow += "\",bd_fyg:\"" 			+ chkString(mygridbox.cells2(i, j++).getValue()) 	+ "\",remain_val:\"" 	+ 	chkString(mygridbox.cells2(i, j++).getValue());
			seledrow += "\",tz_val:\"" 			+ chkString(mygridbox.cells2(i, j++).getValue()) 	+ "\",totbuy_val:\"" 	+ 	chkString(mygridbox.cells2(i, j++).getValue());
			seledrow += "\",lsmonzyz:\"" 		+ chkString(mygridbox.cells2(i, j++).getValue()) 	+ "\",lssmonzyz:\"" 	+ 	chkString(mygridbox.cells2(i, j++).getValue());
			seledrow += "\",rtu_id:\"" 			+ chkString(mygridbox.cells2(i, j++).getValue()) 	+ "\",mp_id:\"" 		+ 	chkString(mygridbox.cells2(i, j++).getValue());
			seledrow += "\",res_id:\"" 			+ chkString(mygridbox.cells2(i, j++).getValue()); 	
			seledrow += "\"},";
		}
	}

	if(seledrow=="{rows:["){
		alert("请至少选中一行合法数据导入！");
		return;
	}

	seledrow = seledrow.substring(0,seledrow.length-1);
	seledrow += "]}";	//alert(seledrow);
	eval('('+seledrow+')');

	loading.loading();
	$.post(basePath + "ajaxdyjc/actReadMeter!ImportRMcard.action", {result:seledrow}, function(data) {
		loading.loaded();
		if(data.result == "success"){
			alert("已成功导入所选！");
			selectFail(SDDef.SUCCESS);		//qjl add 20150214 全部导入成功则清空所有复选框
		}
		else {			
			alert("导入抄表卡数据失败..." + (data.operErr ? data.operErr : ''));
			selectFail(data.field);		//qjl add 20150214 导入成功的取消复选框选择，失败的不取消 
		}
	});
}

function chkString(obj){
	var result = "";
	if(!obj){
		obj = result;
	}
	return obj;
}


function selectAllOrNone(checked) {      //全选&全不选
		var flag = false;
		if (checked.src.indexOf("item_chk0.gif") != -1) {
			flag = true;
			checked.src = def.basePath + 'images/grid/imgs/item_chk1.gif';
		} else if (checked.src.indexOf("item_chk1.gif") != -1) {
			flag = false;
			checked.src = def.basePath +  'images/grid/imgs/item_chk0.gif';
		}
		for ( var i = 0; i < this.mygridbox.getRowsNum(); i++) {
			this.mygridbox.cells2(i, 0).setValue(flag)
		}
}

function initOrg() {
	$.post(def.basePath + "ajax/actCommon!getOrg.action",{},function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			
			if(!pos){
				if(data.flag != "1"){
					$("#org").append("<option value=-1>所有</option>");
				}
			}

			for(var i=0;i<json.length;i++){
				$("#org").append("<option value="+json[i].value+">"+json[i].text+"</option>");
			}
			
			if(pos){
				if(data.flag != "1"){
					$("#org").append("<option value=-1>所有</option>");
				}
			}
			getLineFzMan($("#org").val());
		}
		
	});
}

function getLineFzMan(org_id){//根据供电所ID查询线路负责人列表，填充$("#fzman")。
	$("#fzman").html("<option value=-1>所有</option>");
	if(org_id == -1){
		return;
	}

	$.post(def.basePath + "ajax/actCommon!getLineFzByOrg.action",{value:org_id},function(data) {
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			for(var i=0;i<json.length;i++){
				$("#fzman").append("<option value="+json[i].value+">"+json[i].text+"</option>");
			}
			getRtuByFzman($("#fzman").val());
		}	
	});
}

function getRtuByFzman(fzman_id){
	$("#rtu").html("<option value=-1>所有</option>");
	if(fzman_id == -1){
		return;
	}
	$.post(def.basePath + "ajax/actCommon!getRtuByFzman.action",{value : fzman_id, appType : 3},function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			for(var i=0;i<json.length;i++){
				$("#rtu").append("<option value="+json[i].value+">"+json[i].text+"</option>");
			}
		}else{
			
		}
	});
}

function dcExcel(){
	var len = $("#excPara").val().length;	
	if(len > 1280000){
		alert("数据量大，不能导出excel");
		return;
	}
	toxls.submit();	
}

//qjl add 20150212 start 导出成功的清空选择复选框
function selectFail(successId){
	//取消导入成功的复选框选择项
	$("#headchk").attr("src",def.basePath +  'images/grid/imgs/item_chk0.gif');
	if(successId == SDDef.SUCCESS){	
		for ( var i = 0; i < this.mygridbox.getRowsNum(); i++) {
			this.mygridbox.cells2(i, 0).setValue(false);
		}
	}else{
		successId = successId.substring(1);
		var rows = successId.split("_");
		alert(rows);
		for ( var i = 0; i < rows.length; i++) {
			this.mygridbox.cells2(rows[i]-1, 0).setValue(false);
		}
	}
}
//qjl add 20150212 end