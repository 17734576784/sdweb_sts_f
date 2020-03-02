var mygrid 		= null;
var json_data 	= null;//导出数据
$(document).ready(function(){
  	$.post(def.basePath + "ajax/actCommon!initOperman.action",{tableName: "YffManDef"},function(data){
		var json = eval('(' + data.result + ')');
		$("#opername").html("");
		$("#opername").append("<option value=-1>ALL</option>");
		for ( var i = 0; i < json.rows.length; i++) {
			$("#opername").append("<option value="+json.rows[i].value+">"+json.rows[i].text+"</option>");
		}
	});
	initHeader();
	initDateSel();	
	dosearch();
	$("#search").click(function(){
		 dosearch();
	});
});

function dosearch(){ 
	var operId	= $("#opername").val();
	var opType	= $("#opertype").val();
	initDate.datetype = "minmoment";
	mygrid.clearAll();
	if(!initDate.checkDate()){
		return;
	};	
 	var psdate = initDate.getdateyyyymmdd("sdate");
	var pstime = initDate.gettimehhmm("sdate");
	if(pstime == "0000")pstime = 0;
	var pedate = initDate.getdateyyyymmdd("edate");
	var petime = initDate.gettimehhmm("edate");
	if(petime == "0000")petime = 0;
	loading.loading();
	$.post(def.basePath + "ajax/actLogPara.action",
		{
			result 	: operId, 
			optype 	: opType,
			sdate 	: psdate,
			stime 	: pstime,
			edate 	: pedate,
			etime 	: petime
		},
	function(data){ 
		if(data.result!=""){
			var json = eval('(' + data.result + ')');
			mygrid.parse(json, "", "json");
			json_data = json;
			$("#excPara").val(encodeURI(jsonString.json2String(json_data)));
		}
		$("#pageinfo").html("&nbsp;共"+ mygrid.getRowsNum() +"条记录");
		loading.loaded();
	});
}

function initHeader(){
	mygrid = new dhtmlXGridObject("gridbox");
	mygrid.setImagePath(def.basePath + "images/imgs/");
	mygrid.setHeader("Numéro de série.,Nom de l'opérateur,Date d'opération,Moment de l'opération,Type d'opération,Contenu de l'opération");
	mygrid.setInitWidths("50,150,120,120,120,*");
	mygrid.setColAlign("center,left,left,left,left,left,left");
	mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro");
	mygrid.enableTooltips("false,false,false,false,false,true");
	mygrid.init();
	mygrid.attachHeader("");
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);	
	$("#header").val(encodeURI("Serial No.,Operator Name,Operation  Date,Operation Time,Operation Type,Operation Content,&nbsp;"));
	$("#attachheader").val("");
	$("#colType").val("int,str,str,str,str,str");
	mygrid.enableSmartRendering(true);
}

function initDateSel(){	//日期初始化

	var cDate = new Date();
	var hh = cDate.getHours();
	var mm = cDate.getMinutes();
//	if(mm >= 0 && mm <30){
//		mm = "00";
//	}else if(mm >= 30 && mm < 60){
//		mm = "00";
//	}
	if(mm <= 10) mm = "0"+ mm;
	var shh = hh - 1;
	if(shh < 0) shh = 0;
	if(hh < 10) hh = "0" + hh;
	if(shh < 10) shh = "0" + shh;
	
	hh = shh - 0;
	if(hh < 0){
		hh = "00";
	}else if(hh < 10){
		hh = "0" + hh;
	}

	var smm = mm;
					

	var tmp_date = cDate.DateAdd('d',-1).toString();
	
	var y1 = cDate.getFullYear();
	var m1 = cDate.getMonth()+1;
	
	y1	   = y1.toString();
	m1	   = m1.toString();
	if(y1.length == 1){
		y1 = "0" + d1;
	}
	if(m1.length == 1){
		m1 = "0" + m1;
	}

	var d1 = cDate.getDate().toString();
	if(d1.length == 1){
		d1 = "0" + d1;
	}
//	$("#sdate").val(y1+"年"+m1+"月"+d1+"日"+hh+"时"+mm+"分");
	$("#sdate").val(y1+"-"+m1+"-"+d1+" "+"00:00");
	$("#edate").val(y1+"-"+m1+"-"+d1+" "+shh+":"+smm);
	
}


