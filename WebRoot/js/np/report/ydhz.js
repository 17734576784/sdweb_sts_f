var	mygrid = null;
var pos = true;

$(document).ready(function(){
	mygrid = new dhtmlXGridObject('gridbox');
	initDate();
	initgrid();
	$("#search").click(function(){
		search();
	});
	
});

function initDate(){
	var date = new Date();
	
	$("#sdate").val(date.Format("yyyy年MM月01日").toString());
	//$("#edate").val(date.Format("yyyy年MM月dd日").toString());
}

function initgrid(){
	var gridHeader = "序号,营业点,片区名称,客户编号,客户名称,卡号,累计购电总金额,最后购电时间,购电金额,剩余金额,最后用电时间,&nbsp;";
	var datatype = "int,str,str,str,str,str,str,str,str,str,str,str";
	mygrid.setImagePath(def.basePath + "images/grid/imgs/");
	mygrid.setHeader(gridHeader);
	mygrid.setInitWidths("50,140,140,120,120,120,120,120,120,120,120,*");
	mygrid.setColAlign("center,center,center,left,right,right,right,right,right,right,right");
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	mygrid.init();
	mygrid.setSkin("modern");
	
	
	var empty = "&nbsp;";
	var footer = "<div id='hj'>合计：</div>,#cspan,#cspan,#cspan,#cspan,#cspan,<div id='gdje_total'>"+empty+"</div>,"+empty+",<div id='gdje'>"+empty+"</div>,<div id='syje'>"+empty+"</div>,"+empty+",&nbsp;";
	var foot_align = ["text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;","text-align:right;"];
	mygrid.attachFooter(footer, foot_align);
	
	
	$("#vfreeze").val(2);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val(datatype);
	$("#attachheader").val('');
	$("#filename").val(encodeURI("区域时间段片区用电汇总表"));
	
	//如果 WebConfig配置文件中autoshow="1"，则自动加载查询
	if(np_autoshow[0] == 1){
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
	$("#gdje_total").html("&nbsp;");
	$("#gdje").html("&nbsp;");
	$("#syje").html("&nbsp;");
			
	var sdate = getDate_Time($("#sdate").val());
	var param = '{sdate:"'+sdate+'", org:"'+$("#org").val() + '",area:"'+ $("#area").val() + '"}';
	
	loading.loading();
	
	$.post(def.basePath + "ajaxnp/actReport!loadYDHZData.action",
			{
				result : param
			},
			function(data){
				loading.loaded();
				if(data.result != ""){
					var json = eval('(' + data.result + ')');
					mygrid.parse(json,"json");
				
					//------------合并部分--------------
					//var mr = '{cols:[{col:1},{col:2,key_idx:[0,1]},{col:3}]}';
					var mr = '{cols:[{col:1},{col:2}]}';
					$("#mergeRows").val(mr);
					var mr_json = eval('(' + mr + ')');
					
					mergeRow(json, mr_json);
					//--------------------------
					
					var hz = eval('(' + data.hz + ')');
					$("#gdje_total").html(hz.gdje_total);
					$("#gdje").html(hz.gdje);
					$("#syje").html(hz.syje);
					
					var empty = "&nbsp;";
					//var footer = '{rows:[{data:["'+$("#hj").html()+',0-5","'+$("#gdje_total").html()+'",empty,"'+$("#gdje").html()+ "'," + $("#syje").html()+'" ]}]}';
					var footer = '{rows:[{data:["'+$("#hj").html()+',0-5","'+$("#gdje_total").html()+'",--,"'+$("#gdje").html()+ '","'+$("#syje").html()+'",--]}]}';
					var footerType = "str,str,str,str,str,str,str";
					$("#footer").val(footer);
					$("#footerType").val(footerType);
		
					$("#excPara").val(encodeURI(jsonString.json2String(json)));
					
				}else{
					
				}
	});
	
}
