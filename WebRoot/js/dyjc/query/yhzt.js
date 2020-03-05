var pos = false;

$(document).ready(function(){
	
	initgrid();
	
	$("#search").click(function(){
		search();
	});
	
	$("#selLookItem").click(function(){
		sellookitem();
	})
	
});

function initgrid(){
	//算上id共89个字段
//	var header = "序号," +
//				 "终端名称,客户编号,测量点名称,ESAM表号,客户名称,计费方式,费控方式,缴费方式,预付费表类型,透支值," +
//				 "费率方案号,报警方案,保电时间,不全局保电,密钥版本,加密机ID,费率启用日期,动力关联,抄表日,抄表周期," +
//				 "主站算费标志,是否发行电费,发行起始日期,阶梯切换月日,费率更改标志,费率更改内容,客户状态,操作类型,操作时间," +
//				 "缴费金额,断电金额,断电金额2,购电次数,当前剩余,当前剩余2,报警门限,跳闸门限,累计购电值,开户日期," +
//				 "结算总表底,结算尖表底,结算峰表底,结算平表底,结算谷表底,结算时间,算费时间,算费表底时间,算费总表底,算费尖表底," +
//				 "算费峰表底,算费平表底,算费谷表底,阶梯追补电量,阶梯累计用电量,换表时间,发行电费当月缴费,发行电费当月缴费2,阶梯切换日期,发行电费剩余金额," +
//				 "发行电费剩余金额2,阶梯切换执行时间,发行电费年月,发行电费数据日期,发行电费算费时间,报警1状态,报警2状态,分合闸状态,报警1改变时间,报警2改变时间," +
//				 "分合闸改变时间,异常标志1,异常标志2,分闸时总表底,报警1短信,报警1声音,分合闸确认,报警2短信,报警2声音,分闸次数," +
//				 "报警1短信时间,报警1声音时间,分合闸发送时间,报警2短信时间,报警2声音时间,成功分合闸时间,报警1短信UUID,报警2短信UUID,信息输出";
	var header = "ID,Terminal,Customer ID,Meter Desc,Meter No,Nom du client,FeeType,FeectrlType,Type de transaction,Tariff Type,KRN,Customer State,Type d'opération,Date d'opération,Montant du paiement,Transfer Credit,Nombre d'achats,Opening Date,Info "
	var datatype = "int,str,str,str,str,str,str,str,str,str,int,str,str,str,0.00,0.00,int,str,str";
//	var datatype = "int";
//	for ( var i = 1; i < 19; i++) {
//		datatype += ",str";
//	}
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(header));
	$("#colType").val(datatype);
	$("#filename").val(encodeURI("Requête d'état"));
	
	gridopt.gridHeader           = header;
	gridopt.gridColAlign         =   "center,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left";
	
	gridopt.gridColTypes         =  "ro," +
									"ro,ro,ro,ro,ro,ro,ro,ro,ro,ro," +
									"ro,ro,ro,ro,ro,ro,ro,ro";
	
	gridopt.gridWidths           =  "50," +
									"150,80,120,100,80,80,120,100,100,80," +
									"120,120,100,100,100,100,100,100";
	gridopt.gridSrndFlag		 = true;
	gridopt.click				 = false;
	gridopt.dblclick             = true;//增加双击事件
	
	gridopt.filter(1);
	
	gridopt.grid.setSkin("light");
	
	//如果 WebConfig配置文件中autoshow="1"，则自动加载查询
	if(dy_autoshow[0] == 1){
		search();
	}
}

//双击事件
function redoOnRowDblClicked(id){
	//zxp   openModalDialog(id);
}
function openModalDialog(id){
	modalDialog.height = 620;
	modalDialog.width  = 860;
	modalDialog.url = def.basePath+"jsp/dyjc/dialog/yhztDialog.jsp";
	//将json数据相应的id的data数据传到Dialog
	modalDialog.param = {
		rtu:$("#rtu").val(),yhhh:$.trim($("#yhhh").val()),yhmc:$.trim($("#yhmc").val()),org:$.trim($("#org").val()),bh:$.trim($("#bh").val()),yhzt:$.trim($("#yhzt").val()),
		id : id , lang : window.top._lang, doc_per : window.top._doc_per
    };
	var rtnValue = modalDialog.show();
	if(rtnValue == undefined || rtnValue == null){
		return;
	}
	gridopt.showgrid(false, $("#gopage").val(), $("#prs").val());
	editSelectRow(rtnValue);
	
	window.top.global_ggtz(window.top.dbUpdate.YD_DBUPDATE_TABLE_ZJGPAY);
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
	
	delColFlag = true;
	
	var param = '{rtu:"'+$("#rtu").val()+
				'",yhhh:"'+$.trim($("#yhhh").val())+
				'",yhmc:"'+$.trim($("#yhmc").val())+
				'",org:"'+$.trim($("#org").val())+
				'",yhzt:"'+$.trim($("#yhzt").val())+
				'",bh:"'+$.trim($("#bh").val())+
				'",alarm_type:"'+$.trim($("#alarm_type").val())+
				'",ctrl_type:"'+$.trim($("#ctrl_type").val())+
				'"}';
	
	gridopt.gridGetDataUrl      = def.basePath  + "ajaxdyjc/actSearch!yhztSearch.action";
	gridopt.gridSearch			= param;
	var prs = $("#prs").val();
	gridopt.filter(prs ? prs : 20);
	
	//隐藏不需要显示的列
	var c_lookitem = cookie.get(cookie.ZBlookitem);
	
	c_lookitem = c_lookitem.split("|")[1];
	if(c_lookitem == null || c_lookitem == undefined || c_lookitem == ""){
		return; 
	}
	
	if(c_lookitem != ""){
		c_lookitem = c_lookitem.split(",");
		for(var i = 0; i < c_lookitem.length; i++){
			gridopt.grid.setColumnHidden((c_lookitem[i]), true);
		}
	}
}

function delJsonData() {
	
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
	for ( var i = 1; i < 89; i++) {
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

//选择显示的列
function sellookitem(){
	modalDialog.height =  600;
	modalDialog.width  =  600;
	modalDialog.url = def.basePath + "jsp/dyjc/dialog/ZBlookItemDialog.jsp";
	var c_look = cookie.get(cookie.ZBlookitem);
	var c_look_tmp = cookie.get(cookie.ZBlookitem_tmp);
	modalDialog.param = {lookitem : c_look,lookitem_tmp : c_look_tmp};
	var rtnValues = modalDialog.show();
	
	if(rtnValues == undefined || rtnValues == null){
		return;
	}
	
	var equalFlag = rtnValues[0].substring(0,1);
	var rtnValue1     = rtnValues[0].substring(1);
	var rtnValue1_tmp = rtnValues[1]
	
	cookie.set(cookie.ZBlookitem, rtnValue1);
	cookie.set(cookie.ZBlookitem_tmp, rtnValue1_tmp);
	
	if(equalFlag != 0){
		window.location.reload();
	}
}
