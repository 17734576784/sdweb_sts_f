var retValue;

$(document).ready(function(){
	$("#sel_meter").click(function(){
		sel_meter();
	});
})

/*function getDataByidFromTree(id){
	var onlineflag	= "离线";
	
	rturecord.getDataByidFromTree(id);
	if(id.indexOf(YDTable.TABLECLASS_RTUPARA) >=0 ) {
		id = id.split("_")[1];
		
		$.post(def.basePath + "ajaxnp/actConsPara!getRtuOnlineFlag.action",
			{rtuId:	id},
			function(data){
				if(data.result != ""){
					var json = eval("(" + data.result + ")");
					onlineflag = json.onlineflag;
					if(onlineflag == "在线"){
						$("#rtuonline_img").attr("src",def.basePath + "images/rtuzx_online.gif");
					}
					else{
						$("#rtuonline_img").attr("src",def.basePath + "images/rtuzx_offline.gif");
					}
					
					$("#rtuonline_img").attr("style","display:blank");
					$("#rtuonline_sp").html("<b style='color:#800000'>终端" + onlineflag + "</b>");
				}
		});  
	}
}*/

function getonlineflag(id){
	var onlineflag	= "离线";
		
	$.post(def.basePath + "ajaxnp/actConsPara!getRtuOnlineFlag.action",
		{rtuId:	id},
		function(data){
			if(data.result != ""){
				var json = eval("(" + data.result + ")");
				onlineflag = json.onlineflag;
				if(onlineflag == "在线"){
					$("#rtuonline_img").attr("src",def.basePath + "images/rtuzx_online.gif");
				}
				else{
					$("#rtuonline_img").attr("src",def.basePath + "images/rtuzx_offline.gif");
				}
				
				$("#rtuonline_img").attr("style","display:blank");
				$("#rtuonline_sp").html("<b style='color:#800000'>终端" + onlineflag + "</b>");
			}
	});  
}


var menu = new dhtmlXMenuObject("menu", "dhx_skyblue");
var xml = "<?xml version='1.0' encoding='gbk'?><menu><item id='menu' text='操作菜单'><item id='zhaoce' text='在线召测' img='top_menu_sjzc.gif'><href><![CDATA[javascript:exeMenu('taskCall');]]></href></item><item id='quxiao' text='操作取消' img='cancel_task.gif'><href><![CDATA[javascript:javascript:exeMenu('taskCancel');]]></href></item><item id='writeDB' text='存库' img='top_menu_sjzc.gif'><href><![CDATA[javascript:exeMenu('writeDB');]]></href></item><item id='dcxls' text='导出EXECL' img='to_Xls.gif'><href><![CDATA[javascript:javascript:dcExcel1();]]></href></item><item id='sep' type='separator'/><item id='xxxx' text='详细信息' img='detail.gif'><href><![CDATA[javascript:window.top.showOpDetailDialog();;]]></href></item></item></menu>";
menu.setIconsPath(def.basePath+"images/menu/imgs/");
menu.loadXMLString(xml);
menu.setAlign('right');

function exeMenu(type){
	eval("rturecord."+type+"();");
}
function dcExcel1() {
	var json = {rows:[]};
	var len = rturecord.mygrid.getRowsNum()
	var col = rturecord.mygrid.getColumnsNum();
	for(var i = 0; i < len; i++) {
		json.rows[i] = {};
		json.rows[i].id = i;
		json.rows[i].data = [];
		for(var j = 0; j < col; j++) {
			json.rows[i].data[j] = rturecord.mygrid.cells2(i, j).getValue();
		}
	}
	$("#excPara").val(encodeURI(jsonString.json2String(json)));
	
	dcExcel();
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
	getonlineflag(ret.rtu_id);
	$("#esam_no").val(ret.meter_desc);
	$("#area_code").val(ret.area_code);
	$("#pwd").val("000000");
	window.frames[0].location.reload();//检索完毕刷新frames中页面
}

//清空界面
function clearInput(){
	$("#esam_no").val("");
	$("#area_code").val("");
	$("#pwd").val("");
}