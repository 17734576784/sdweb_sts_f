var glo_opdetail_dlg = null;
var glo_opdetail_str = null;

function showOpDetailDialog(){
	if (glo_opdetail_dlg == null || glo_opdetail_dlg.closed) {
		window.showModelessDialog(def.basePath + "jsp/dialog/opDetailDlg.jsp",window,"scroll:0;status:0;help:1;resizable:1;dialogWidth:740px;dialogHeight:200px");
	}
}

function getWindowOpDetail() {	
	if (glo_opdetail_dlg == null || glo_opdetail_dlg.closed) {
		return null;
	}
	var opdetail = glo_opdetail_dlg.document.getElementById("opdetail");
	if (opdetail == null) return null;
	
	return opdetail;
}

function addPOpDetail(wnd, str, color) {
    var str1 = "";
    str1 += "<p style='color:" + color + "'>" + str + "</p>";
    
    var odiv = glo_opdetail_dlg.document.createElement('div');

    odiv.innerHTML = str1;

    wnd.appendChild(odiv)
}

function addPreOpDetail(wnd, str, color) {
    var str1 = "";
    str1 += "<pre style='color:" + color + "'>" + str + "</pre>";
    
    var odiv = glo_opdetail_dlg.document.createElement('div');

    odiv.innerHTML = str1;

    wnd.appendChild(odiv)
}

function addBrOpDetail(wnd) {
    var str1 = "";
    str1 += "<br>";
    
    var odiv = glo_opdetail_dlg.document.createElement('div');

    odiv.innerHTML = str1;

    wnd.appendChild(odiv)
}

function getCurrentDateTimeDetail() {
	var date_time = new Date();
	
	var str_month = "" + (date_time.getMonth() + 1);
	var str_mday  = "" + date_time.getDate();
	var str_hour  = "" + date_time.getHours();
	var str_min   = "" + date_time.getMinutes();
	var str_sec   = "" + date_time.getSeconds();
	
	if (str_month.length == 1) str_month = "0" + str_month;
	if (str_mday.length  == 1) str_mday  = "0" + str_mday;
	if (str_hour.length  == 1) str_hour  = "0" + str_hour;
	if (str_min.length   == 1) str_min   = "0" + str_min;
	if (str_sec.length   == 1) str_sec   = "0" + str_sec;
	
	return str_month + "/" + str_mday + " " + str_hour + ":" + str_min + ":" + str_sec;
}

function printJsonOpDetail(wnd, item) {
	if (item.TYPE == "0") {  	   //任务执行信息
		addPOpDetail(wnd, "任务执行信息" + "[" + item.TIME + "]:", "black");
		addPOpDetail(wnd, item.DATA, "#008000");
	}
	else if (item.TYPE == "1") {  //接收的报文
		addPOpDetail(wnd, "RX" + "[" + item.TIME + "]:" + "数据长度:[" + item.DATALEN + "] 设备回复", "black");
		addPOpDetail(wnd, item.DATA, "#0000FF");
	}
	else if (item.TYPE == "2") {  //发送的报文
		addPOpDetail(wnd, "TX" + "[" + item.TIME + "]:" + "数据长度:[" + item.DATALEN + "] 主站下行", "black");
		addPOpDetail(wnd, item.DATA, "#FF0000");
	}
	else if (item.TYPE == "3") {  //接收报文的解析
		addPreOpDetail(wnd, "RX报文解析" + "[" + item.TIME + "]:", "black");
		addPreOpDetail(wnd, item.DATA, "#808000");	
	}
	else if (item.TYPE == "4") {  //发送报文的解析
		addPreOpDetail(wnd, "TX报文解析" + "[" + item.TIME + "]:", "black");
		addPreOpDetail(wnd, item.DATA, "#808000");
	}
	else if (item.TYPE == "5") {  	   //数据库事件
		addPOpDetail(wnd, "数据库信息" + "[" + item.TIME + "]:", "black");
		addPOpDetail(wnd, item.DATA, "#008000");
	}
	else if (item.TYPE == "6") {  	   //用户信息
		addPOpDetail(wnd, "用户信息" + "[" + item.TIME + "]:", "black");
		addPOpDetail(wnd, item.DATA, "#008000");
	}
	else if (item.TYPE == "7") {  	   //其他信息
		addPOpDetail(wnd, "其他信息" + "[" + item.TIME + "]:", "black");
		addPOpDetail(wnd, item.DATA, "#008000");
	}else if (item.TYPE == "8") {  	   //加密机报文
		addPOpDetail(wnd, "加密机报文" + "[" + item.TIME + "]:", "black");
		addPOpDetail(wnd, item.DATA, "#008000");
	}
}

function addJsonOpDetail(str) {
	if (str == null) return;
		
	glo_opdetail_str = str;
	
	var opdetail = getWindowOpDetail();
	if (opdetail == null) return;	
	
	var json = eval('(' + str + ')');
		
	var i = 0;
	
	for (i = 0; i < json.OPDETAIL.length; i++) {
		printJsonOpDetail(opdetail, json.OPDETAIL[i]);
		addBrOpDetail(opdetail);		
	}
	
	opdetail.scrollTop = opdetail.scrollHeight;
	opdetail.scrollTop = opdetail.scrollHeight;	
}

function addStringTaskOpDetail(str) {
	if (str == null) str = " ";

	var opdetail = getWindowOpDetail();
	if (opdetail == null) return;

	if (str == "\n") {
		addBrOpDetail(opdetail);
	}
	else {
		addPOpDetail(opdetail, "任务执行信息" + "[" + getCurrentDateTimeDetail() + "]:", "black");
		addPOpDetail(opdetail, str, "#008000");
		addBrOpDetail(opdetail);
	}
	
	opdetail.scrollTop = opdetail.scrollHeight;
	opdetail.scrollTop = opdetail.scrollHeight;	
}

function addStringDbOpDetail(str) {
	if (str == null) str = " ";

	var opdetail = getWindowOpDetail();
	if (opdetail == null) return;

	if (str == "\n") {
		addBrOpDetail(opdetail);
	}
	else {
		addPOpDetail(opdetail, "数据库信息" + "[" + getCurrentDateTimeDetail() + "]:", "black");
		addPOpDetail(opdetail, str, "#008000");
		addBrOpDetail(opdetail);
	}
	
	opdetail.scrollTop = opdetail.scrollHeight;
	opdetail.scrollTop = opdetail.scrollHeight;	
}

function addStringUserOpDetail(str) {
	if (str == null) str = " ";

	var opdetail = getWindowOpDetail();
	if (opdetail == null) return;

	if (str == "\n") {
		addBrOpDetail(opdetail);
	}
	else {
		addPOpDetail(opdetail, "用户信息" + "[" + getCurrentDateTimeDetail() + "]:", "black");
		addPOpDetail(opdetail, str, "#008000");
		addBrOpDetail(opdetail);
	}
	
	opdetail.scrollTop = opdetail.scrollHeight;
	opdetail.scrollTop = opdetail.scrollHeight;	
}

function addStringOtherOpDetail(str) {
	if (str == null) str = " ";

	var opdetail = getWindowOpDetail();
	if (opdetail == null) return;

	if (str == "\n") {
		addBrOpDetail(opdetail);
	}
	else {
		addPOpDetail(opdetail, "其他信息" + "[" + getCurrentDateTimeDetail() + "]:", "black");
		addPOpDetail(opdetail, str, "#008000");
		addBrOpDetail(opdetail);
	}
	
	opdetail.scrollTop = opdetail.scrollHeight;
	opdetail.scrollTop = opdetail.scrollHeight;	
}

//20131130 zhp 取消任务操作
var taskCancelObj = {
	cancel_rtu  :  null,	//终端id
	cancel_src  :  null,	//操作action链接
	cancel_oper :  null,	//操作类型

	cancelDetailTask :function(){
	
	if(this.cancel_rtu == null || this.cancel_src == null || this.cancel_oper == null) return;
	else{
		$.post(this.cancel_src, {
			userData1 : this.cancel_rtu,
			userData2 : this.cancel_oper
		});
	}
  }
}

