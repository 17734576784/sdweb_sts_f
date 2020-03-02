var provinceMisFlag = window.top.provinceMisFlag;  //MIS所属省份标识

$(document).ready(function(){
	mygrid = new dhtmlXGridObject('gridbox');
	initDate();
	initGrid();
	
	$("#search").click(function(){search();});
	$("#check").click(function(){misCheck();});
});

function initDate(){
	var today = new Date();
	var date = new Date();
	date.setDate(today.getDate() - 1);
	$("#sdate").val(date.Format("yyyy年MM月dd日").toString());
}

//获取对账日期最大值(昨天)
function getMaxDate(){
	var date = new Date();
	var year = date.getYear();
	var month = date.getMonth()+1;
	var day = date.getDate()-1;
	return year + '-' + month + '-' + day;
}

function initGrid(){
	var gridHeader = "序号,客户编号,客户名称,操作日期,错误号,缴费金额(元),出厂编号,代收单位编号,操作员编号,交易流水号,缴费笔数,银行账务日期,应收标识1,应收标识2,应收标识3,备注"; 
	var width = "50,120,140,165,80,100,100,100,100,100,100,140,100,100,100,100";
	var colAlign = "center,left,left,left,left,right,left,left,left,left,left,left,left,left,left,left"; 
	var colType = "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	
	if(provinceMisFlag == 'GS'){
		gridHeader = "序号,客户编号,客户名称,操作日期,错误号,本次实缴总金额(元),本次实缴欠费金额(元),本次实缴预收金额(元),出厂编号,对账批次,代收单位编号,操作员编号,交易流水号,缴费笔数,银行账务日期,缴费明细1,缴费明细2,缴费明细3,备注";
		width = "50,120,140,165,80,150,150,150,100,100,100,100,100,100,140,100,100,100,100";;
		colAlign = "center,left,left,left,left,right,right,right,left,left,left,left,left,left,left,left,left,left,left";
		colType = "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	}
	
	mygrid.setImagePath(def.basePath + "images/grid/imgs/");
	mygrid.setHeader(gridHeader);
	mygrid.setInitWidths(width);
	mygrid.setColAlign(colAlign);
	mygrid.setColTypes(colType);
	mygrid.init();
	mygrid.enableSmartRendering(true);
	mygrid.setSkin("light");
	
	var text = '{item:["' + Dict.DICTITEM_YFFOPTYPE + '"]}';
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : text},function(data){
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			
			var dict = eval("json.rows[0]."+Dict.DICTITEM_YFFOPTYPE);
			for ( var i = 0; i < dict.length; i++) {
				mygrid.getCombo(3).put(dict[i].value, dict[i].text);
			}
		}
	});
}

function search(){
	if(!dateLimit()) return;
	
	var sdate =  getDate_Time($("#sdate").val());
	loading.loading();
	mygrid.clearAll();
	$.post(def.basePath + "ajaxoper/actMisCheck.action",
			{
			 datepara 	: sdate,
			 misType 	: provinceMisFlag
			},
			function(data){
				loading.loaded();
				if(data.result != ""){
					var json = eval('(' + data.result + ')');
					mygrid.parse(json,"json");
				}else{
					alert("没有可对账的记录!");
				}
				rtu_id = -1;
				$("#total").html("共" + mygrid.getRowsNum() + "条记录");
			});
}

//与mis对账
function misCheck(){
	if(mygrid.getRowsNum() < 1){
		alert("没有可对账记录!");
		return;
	} 
	
	if(!dateLimit()) return;
	if(check_error_no()){
		if(!confirm("对账信息中含有未上传的记录，建议先到手工上传页面上传记录，是否继续对账?")){
			return;
		}
	}
	
	loading.tip = "正在与MIS系统对账...";
	loading.loading();
	
	window.top.addStringTaskOpDetail("正在向预付费服务发送信息...");
	$.post(def.basePath + "ajaxoper/actMisCheck!taskProc.action",
			{
				userData1 	: -1,
				userData2 	: ComntUseropDef.YFF_GDYOPER_MIS_CHECKPAY,
				misOperStr 	: getDate_Time($("#sdate").val())
			},
			function(data){
				window.top.addJsonOpDetail(data.detailInfo);
				loading.loaded();
				//返回提示信息
				if(data.misResult != ""){
					alert(data.misResult);
					//刷新界面
					search();
				}
				else{
					alert("和MIS对账失败!");
				}
			});
}

function dateLimit(){
	var date = getDate_Time($("#sdate").val());
	if(date >= _today){
		alert("只能对账今天以前的记录!");
		return false;
	}else{
		return true;
	}
}

//判断查询结果中是否含有非1001的错误码,有：true
function check_error_no(){
	var flag = false;
	for(var i=0; i<mygrid.getRowsNum(); i++){
		if(mygrid.cells2(i,4).getValue() != '1001'){
			flag = true;
			break;
		}
	}
	return flag;
}

