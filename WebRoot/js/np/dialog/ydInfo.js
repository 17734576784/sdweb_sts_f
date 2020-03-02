var mygrid = null;

var area_id = window.dialogArguments.area_id;
var farmer_id = window.dialogArguments.farmer_id;
var infoNum = window.dialogArguments.infoNum;

$(document).ready(function(){
	initGrid();
	getYDRecord(infoNum);
});

function initGrid(){
	mygrid = new dhtmlXGridObject("gridbox");
	mygrid.setHeader("序号,终端名称,电表名称,卡号,开始日期,开始时间,结束日期,结束时间,费率,本次用电金额(元),剩余金额(元),用电电量(kwh),过零电量(kwh),用电状态,&nbsp;");
	mygrid.setInitWidths("40,90,90,120,100,90,100,90,70,120,120,150,150,80,*");
	mygrid.setColAlign("center,left,left,left,right,left,left,left,left,left,left,left,left,left");
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.enableTooltips("false,false,false,false,false,false,false,false,false,false,false,false,false,false");
	mygrid.init();
	mygrid.setSkin("light");
	//GridPage.gridobj.attachEvent("onRowDblClicked",onRowDblClicked);
}

function getYDRecord(num) {
	mygrid.clearAll();
	var param = "{top_num: " + num + ",area_id:" + area_id + ",farmer_id:" + farmer_id + "}"; 
	$.post(def.basePath + "ajax/actCommon!getYDInfoNP.action",{result : param}, function(data) {
		if (data.result != "") {
			var jsdata = eval('(' + data.result + ')');
			mygrid.parse(jsdata,"json");
		}
	});
}