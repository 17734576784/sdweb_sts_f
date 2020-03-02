var	mygrid = null;
var pos = false;
var load_flag = [false, false];

$(document).ready(function(){ 
	initOrg();
	initDate();
	initgrid();
	$("#search").click(function(){
		search();
	});
	$("#toprint").click(function(){
		rePrint();
	});
	$("#org").change(function(){
		getAreaByOrg(this.value);
	});
});
function initOrg(){
	$.post(def.basePath + "ajax/actCommon!getOrg.action",{},function(data){
		load_flag[0] = true;
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
			window.setTimeout('getAreaByOrg('+$("#org").val()+')', 20);
		}		
	});
}

function getAreaByOrg(org_id){	
	$("#area").html("<option value=-1>所有</option>");
	if(org_id == -1){
		load_flag[1] = true;
		return;
	}
	$.post(def.basePath + "ajax/actCommon!getAreaByOrg.action",{value : org_id, appType : 5},function(data){
		load_flag[1] = true;
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			for(var i=0;i<json.length;i++){
				$("#area").append("<option value="+json[i].value+">"+json[i].text+"</option>");
			}
		}else{
			
		}
	});	
}

//给开始和结束时间初始化赋值
function initDate(){
	var date = new Date();
	$("#sdate").val(date.Format("yyyy年MM月01日").toString());
	$("#edate").val(date.Format("yyyy年MM月dd日").toString());
}
/**通用不传param时的grid初始化*/
function initgrid(){
	var gridHeader = "序号,流水号,所属片区,客户编号,客户名称,客户地址,卡号,操作类型,操作日期,操作员,缴费方式,缴费金额(元),结算金额(元),追补金额(元),总金额(元),报警值1,报警值2,累计缴费金额(元),购电次数";
	var datatype = "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val(datatype);
	$("#filename").val(encodeURI("补打发票"));
	gridopt.gridHeader           = gridHeader;
	gridopt.gridColAlign         = "center,left,left,left,left,left,left,center,left,left,left,right,right,right,right,right,right,right,left";
	gridopt.gridColTypes         = "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "50,120,120,80,100,120,120,80,170,100,100,100,100,100,100,100,100,120,100";
	//gridopt.gridTooltips         = "center,left,left,right,center,left,center,center,left,left,left,right,right,right,right,right,right,right,left";
	gridopt.gridSrndFlag		 = true;
	gridopt.click				 = false;
	gridopt.filter(1);
	gridopt.grid.setSkin("light");
	search(); 
};
function search(){
	var flag = true;
	for ( var i = 0; i < load_flag.length; i++) {
		flag = flag && load_flag[i];
	}
	if (!flag) {
		window.setTimeout('search()', 100);//??
		return;
	}
	
	var sdate = getDate_Time($("#sdate").val());
	var edate = getDate_Time($("#edate").val());
	
	if(sdate > edate){
		edate = sdate;
		$("#edate").val($("#sdate").val());
	}
	
	if(sdate.substring(0, 4) != edate.substring(0, 4)){
		alert("不能跨年选择日期");
		return;
	}
	//var param = '{sdate:"'+sdate+'",edate:"'+edate+'",khbh:"'+$("#khbh").val()+'",czy:"'+$("#czy").val()+'",khmc:"'+$("#khmc").val()+'",org:"'+$("#org").val()+'",rtu:"'+$("#rtu").val()+'"}';
	var params={};
	params.sdate = sdate;
	params.edate = edate;
	params.khbh  = $("#khbh").val();
	params.czy   = $("#czy").val();
	params.khmc  = $("#khmc").val();
	params.org   = $("#org").val();
	params.area  = $("#area").val();
	var json_str = jsonString.json2String(params);
	gridopt.gridGetDataUrl=def.basePath  + "ajaxnp/actSupplyBill!billSearch.action";
	gridopt.gridSearch    = json_str;  
	var prs = $("#prs").val();
	gridopt.filter(prs?prs:20);
}

function rePrint(){//补打发票
	//得到选中的id
	var mygrid=gridopt.grid;
	var selectedId=mygrid.getSelectedRowId();
	
	if(null==selectedId){
		alert("请您选择要打印的内容");
		return;
	}
	
	var ids = selectedId.split("_");
	
	window.top.WebPrint.prt_params.rtu_id  = ids[0];
 	window.top.WebPrint.prt_params.sub_id  = ids[1];
	window.top.WebPrint.prt_params.op_date = ids[3];
	window.top.WebPrint.prt_params.op_time = ids[4];
  	window.top.WebPrint.prt_params.op_type = ids[2]; 
 	window.top.WebPrint.prt_params.wasteno = mygrid.cells(selectedId, 1).getValue();;    
	window.top.WebPrint.prt_params.page_type = ids[5];
	window.top.WebPrint.prt_params.reprint = 1;
	
	var filename =  window.top.WebPrint.getTemplateFileNm(ids[5]);
	if(filename == undefined || filename == null){
		return;
	}
	window.top.WebPrint.prt_params.file_name = filename;
	
//	window.top.WebPrint.prt_params.file_name = window.top.WebPrint.fileName.dyje;

	window.top.WebPrint.doPrintDy();
	
}