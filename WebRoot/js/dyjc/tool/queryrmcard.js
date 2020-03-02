var pos = false;

$(document).ready(function(){
	
	initDate();
	initgrid();
	
	$("#search").click(function(){
		search();
	});
	
	$("#selLookItem").click(function(){
		sellookitem();
	})
});

function initDate(){
	var date = new Date();
	
	$("#sdate").val(date.Format("yyyy年MM月01日").toString());
	$("#edate").val(date.Format("yyyy年MM月dd日").toString());
}


function initgrid(){
	
	//mygrid.setImagePath(def.basePath + "images/grid/imgs/");
	var gridHeader = "序号,所属变压器,客户户号,电表表号,客户名称,客户地址,卡内户号,操作员,导入日期,抄表时间,购电值,购电次数,计费方式,囤积值,报警值,透支限值,PT,CT,费率电价,阶梯电价,非法次数,跳闸次数,状态字1,状态字2,剩余值,透支值,累计购电值,组合正有,正有总,正有尖,正有峰,正有平,正有谷,反有总,反有尖,反有峰,反有平,反有谷,上月正有总,上上月正有总";
	//var datatype = "int,str,str,str,str,str,str,str,str,0.00,int,0.00,0.00,0.00,0.00,0.00,str,int,0.00,0.00,int,int,int,int,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00";
	var datatype = "int,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str";

	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridHeader));
	$("#colType").val(datatype);
	$("#filename").val(encodeURI("集抄抄表数据查询"));
	
	
	gridopt.gridHeader           = gridHeader;
	gridopt.gridColAlign         = "center,left,left,left,left,left,left,left,left,left,right,center,left,right,right,right,right,right,right,right,center,center,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right";
	gridopt.gridColTypes         = "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "50,120,120,120,180,100,80,80,100,80,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,60,90,60,60,60,60,60,60,60,60,60,60,60,80,80";
	gridopt.gridTooltips         = "center,left,left,left,left,center,left,left,left,left,center,right,right,right,right,right,left,center,right,right,center,center,center,center,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right";
	gridopt.gridSrndFlag		 = true;
	gridopt.click				 = false;
	
	
	gridopt.filter(1);
	
	gridopt.grid.setSkin("light");
	
	//如果 WebConfig配置文件中autoshow="1"，则自动加载查询
	
	if(dy_autoshow[0] == 1){
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
	
	var sdate = getDate_Time($("#sdate").val());
	var edate = getDate_Time($("#edate").val());
	
	if(sdate > edate){
		edate = sdate;
		$("#edate").val($("#sdate").val());
	}
	
	var y1 = sdate.substring(0, 4), y2 = edate.substring(0, 4);
	if(Math.abs(parseInt(y1) - parseInt(y2)) > 3) {
		alert("请选择4年内的数据");
		return;
	}

	delColFlag = true;		//关联到   comm.js setExcValue		gridopt.js if(typeof setExcValue == "function"){setExcValue(); 本页的 delJsonData 不是很好的方法
	
	var param = '{sdate:"'+sdate+'",edate:"'+edate+'",yhbh:"'+$("#yhbh").val()+'",yhmc:"'+$("#yhmc").val()+'",czy:"'+$("#czy").val()+'",org:"'+$("#org").val()+'",rtu:"'+$("#rtu").val()+'",oper_type:"'+$("#oper_type").val()+'"}';

	gridopt.gridGetDataUrl      = def.basePath  + "ajaxdyjc/actReadMeter!SearchRmRcd.action";
	gridopt.gridSearch			= param;
	var prs = $("#prs").val();
	gridopt.filter(prs?prs:20);
	
	//隐藏不需要显示的列
	var c_lookitem = cookie.get(cookie.ZBlookitem);

	c_lookitem = c_lookitem.split("|")[1];
	if(c_lookitem == null || c_lookitem == undefined || c_lookitem == ""){
		return; 
	}
	//alert(c_lookitem)
	
	if(c_lookitem != ""){
		c_lookitem = c_lookitem.split(",");
		for(var i = 0; i < c_lookitem.length; i++){
			gridopt.grid.setColumnHidden((c_lookitem[i]), true);
		}
	}
}

//选择显示的列
function sellookitem(){
	modalDialog.height =  550;
	modalDialog.width  =  600;
	modalDialog.url = def.basePath + "jsp/dyjc/dialog/RmlookItemDialog.jsp";
	var c_look = cookie.get(cookie.ZBlookitem);
	var c_look_tmp = cookie.get(cookie.ZBlookitem_tmp);//alert(c_look + "ssss" + c_look_tmp )
	modalDialog.param = {lookitem : c_look,lookitem_tmp : c_look_tmp};
	var rtnValues = modalDialog.show();
	
	if(rtnValues == undefined || rtnValues == null){
		return;
	}
	
	var equalFlag = rtnValues[0].substring(0,1);
	var rtnValue1     = rtnValues[0].substring(1);
	var rtnValue1_tmp = rtnValues[1]
//	alert(rtnValue1 + "dddd" + rtnValue1_tmp)
	cookie.set(cookie.ZBlookitem, rtnValue1);
	cookie.set(cookie.ZBlookitem_tmp, rtnValue1_tmp);
	
	if(equalFlag != 0){
		window.location.reload();
	}
}


function delJsonData() {
//	alert(1)
	if(!gridopt.jsondata) return;
	
	//删除数据中隐藏的列
	var c_lookitem = cookie.get(cookie.ZBlookitem);
	c_lookitem = c_lookitem.split("|")[1];
	if(c_lookitem == null || c_lookitem == undefined || c_lookitem == ""){
		return;
	}
	var del_flag = "~null";
	c_lookitem = c_lookitem.split(",");
	
	var header = gridopt.gridHeader.split(",");
	var datatype = [];
	datatype[0] = "int";
	for ( var i = 1; i < 39; i++) {
		datatype[i] = "str";
	}
	
	for(var j = 0; j < gridopt.jsondata.page[0].rows.length; j++){
		for(var i = 0; i < c_lookitem.length; i++){
			gridopt.jsondata.page[0].rows[j].data[c_lookitem[i]] = del_flag;
		}
	}
	
	var tmp_json = gridopt.jsondata;
	var xlsheader = [], xlsdatatype = [];
	
	gridopt.jsondata = {total : tmp_json.total, page : [{rows : []}]};
	
	for(var j = 0; j < tmp_json.page[0].rows.length; j++){
		gridopt.jsondata.page[0].rows[j] = {};
		gridopt.jsondata.page[0].rows[j].id = tmp_json.page[0].rows[j].id;
		gridopt.jsondata.page[0].rows[j].data = [];
		var dataidx = 0;
		for(var i = 0; i < tmp_json.page[0].rows[j].data.length; i++) {
			var tmp = tmp_json.page[0].rows[j].data[i];
			if(tmp == del_flag) continue;
			gridopt.jsondata.page[0].rows[j].data[dataidx] = tmp;
			
			if(j == 0) {
				xlsheader[dataidx] = header[i];
				xlsdatatype[dataidx] = datatype[i];
			}
			dataidx++;
		}
	}
	
	header = "";
	datatype = "";
	for(var  i = 0; i < xlsheader.length; i++) {
		header += "," + xlsheader[i];
		datatype += "," + xlsdatatype[i];
	}
	
	$("#header").val(encodeURI(header.substring(1)));
	$("#colType").val(datatype.substring(1));
}