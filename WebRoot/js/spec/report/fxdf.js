var	mygrid = null;
var pos = true;
$(document).ready(function(){
	mygrid = new dhtmlXGridObject('gridbox');
	
	initgrid();
	initDate();
	
	$("#search").click(function(){
		search();
	});
	
	$("#selLookItem").click(function(){
		sellookitem();
	})
	
});

function initDate(){
	var date = new Date();

	$("#sdate").val(date.DateAdd("m",-1).Format("yyyy年MM月").toString());
	$("#edate").val(date.Format("yyyy年MM月").toString());
}

function initgrid(){
	var gridHeader ="序号,终端名称,客户编号,总加组名称,发行电费年月,发行电费算费时间,上次电费年月,本月缴费总金额,上次剩余,当前剩余,电费金额,实际功率因数,电度电费,基本费电费,力调电费,其它电费,有功追补累计用电量,无功追补累计用电量,有功累计用电量,无功累计用电量,追补电度电费,追补基本费电费,追补其它电费,断电值/金额,上次结算时间,上次结算表底,#cspan,#cspan,#cspan,#cspan,#cspan,动力关联1上次结算表底,#cspan,#cspan,#cspan,#cspan,#cspan,动力关联2上次结算表底,#cspan,#cspan,#cspan,#cspan,#cspan,算费表底时间,算费时表底,#cspan,#cspan,#cspan,#cspan,#cspan,动力关联1算费时表底,#cspan,#cspan,#cspan,#cspan,#cspan,动力关联2算费时表底,#cspan,#cspan,#cspan,#cspan,#cspan,更新次数,整月标志,操作员";
	var attachHeader = "#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,总,尖,峰,平,谷,无功总,总,尖,峰,平,谷,无功总,总,尖,峰,平,谷,无功总,#rspan,总,尖,峰,平,谷,无功总,总,尖,峰,平,谷,无功总,总,尖,峰,平,谷,无功总,#rspan,#rspan,#rspan";
	var datatype = "int,str,str,str,str,str";
	var toolTips="false,false,false,false,false,false"
	var	colAlign = "center,left,left,left,right,right";
	var	colTypes ="ro,ro,ro,ro,ro,ro";
	var	gridWidths ="50,200,120,120,100,120"
		
	for ( var i = 7; i < 66; i++) {
		datatype += ",str";
		toolTips += ",false"
		colAlign += ",right";
		colTypes += ",ro";
		gridWidths +=",100";
	}
	mygrid.setImagePath(def.basePath + "images/grid/imgs/");
	mygrid.setHeader(gridHeader);
	mygrid.attachHeader(attachHeader);
	mygrid.enableTooltips(toolTips);
	mygrid.setInitWidths(gridWidths);
	mygrid.setColAlign(colAlign);
	mygrid.setColTypes(colTypes);
	mygrid.enableSmartRendering(true);
	mygrid.init();
	mygrid.setSkin("modern");
	
//	var empty = "&nbsp;";
//	var footer = "<div id='hj'>合计：</div>,#cspan,#cspan,<div id='jfcs'>"+empty+"</div>,<div id='jfje'>"+empty+"</div>,<div id='zbje'>"+empty+"</div>,<div id='jsje'>"+empty+"</div>,<div id='zje'>"+empty+"</div>,&nbsp;,&nbsp;,#cspan";
//	var foot_align = ["text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;"];
//	mygrid.attachFooter(footer, foot_align);
	
	
	$("#vfreeze").val(2);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val(datatype);
	$("#attachheader").val(encodeURI(attachHeader));
	$("#filename").val(encodeURI("高压发行电费表"));
	$("#hidecols").val(cookie.get(cookie.FXDFlookitem));
	
	//如果 WebConfig配置文件中autoshow="1"，则自动加载查询
	if(spec_autoshow[0] == 1){
		search();
	}
}

function search(){

	var flag = true;
	for ( var i = 0; i < load_flag.length; i++) {
		flag = flag && load_flag[i];
	}
	if (!flag) {
		window.setTimeout('search()', 100);
		return;
	}
	
	mygrid.clearAll();
	$("#jfcs").html("&nbsp;");
	$("#jfje").html("&nbsp;");
	$("#zbje").html("&nbsp;");
	$("#jsje").html("&nbsp;");
	$("#zje").html("&nbsp;");
	
	var sdate = getDate_Time($("#sdate").val());
	var edate = getDate_Time($("#edate").val());
	
	if(sdate > edate){
		edate = sdate;
		$("#edate").val($("#sdate").val());
	}
	
	var param = '{sdate:"'+sdate+'",edate:"'+edate+'",org:"'+$("#org").val()+'",rtu:"'+$("#rtu").val()+'"}';
	
	loading.loading();
	
	$.post(def.basePath + "ajaxspec/actReport!fxdfReport.action",{result : param},function(data){
		loading.loaded();
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			mygrid.parse(json,"json");
			setShowHide();
			
//			var hz = eval('(' + data.hz + ')');
//			$("#jfcs").html(hz.jfcs);
//			$("#jfje").html(hz.jfje);
//			$("#zbje").html(hz.zbje);
//			$("#jsje").html(hz.jsje);
//			$("#zje").html(hz.zje);
//			
//			var footer = '{rows:[{data:["'+$("#hj").html()+',0-2","'+$("#jfcs").html()+'","'+$("#jfje").html()+'","'+$("#zbje").html()+'","'+$("#jsje").html()+'","'+$("#zje").html()+'"]}]}';
//			var footerType = "str,int,0.000,0.000,0.000,0.000";
//			$("#footer").val(footer);
//			$("#footerType").val(footerType);
			$("#excPara").val(encodeURI(jsonString.json2String(json)));
			
		}
	});
	
}

//选择显示的列
function sellookitem(){
	modalDialog.height =  450;
	modalDialog.width  =  600;
	modalDialog.url = "../dialog/fxdfLookItemDialog.jsp";
	var c_look = cookie.get(cookie.FXDFlookitem);
	modalDialog.param = {lookitem : c_look};
	var rtnValue = modalDialog.show();

	if(rtnValue == undefined || rtnValue == null){
		return;
	}
	
	var equalFlag = rtnValue.substring(0,1);
	rtnValue = rtnValue.substring(1);
	$("#hidecols").val(rtnValue);
	cookie.set(cookie.FXDFlookitem, rtnValue);
	if(equalFlag != 0){
		setShowHide();
	}
}

function setShowHide(){//隐藏不需要显示的列
	
	var cols = new Array(mygrid.getColumnsNum());
	for(var i = 0 ; i < mygrid.getColumnsNum() ; i++ ){//先全都显示
		cols[i] = false;
	}
	
	var c_lookitem = cookie.get(cookie.FXDFlookitem);
	c_lookitem = c_lookitem.split("|")[1];
	
	if(c_lookitem != null && c_lookitem != undefined && c_lookitem != ""){
		if(c_lookitem != ""){
			c_lookitem = c_lookitem.split(",");
			
			for(var i = 0; i < c_lookitem.length; i++){//不显示的设置为true；
				cols[c_lookitem[i]] = true;
			}
		}
	}
	
	for(var i = 0; i < mygrid.getColumnsNum(); i++){
		mygrid.setColumnHidden(i, cols[i]);//cols[i]：true隐藏，false显示
	}

}



