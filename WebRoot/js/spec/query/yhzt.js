var pos = false;
var rtnValue=null;
var rtnTmp=null;
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
	
	var header =   "序号,终端名称,客户编号,总加组名称,计费方式,费控方式,缴费方式,基本费,预付费控制类型,透支值,费率方案1,费率方案2,费率方案3,报警方案,保电时间,不全局保电,密钥版本,所属加密机,写卡户号,费率启用日期,卡表类型,脉冲闭合时间,启用高负荷控制,连续高负荷时间,高负荷断电值,功率因数标准,力调算费标志,奖罚标志,报停标志,报停开始日期,报停结束日期,抄表周期,抄表日,抄表日结算标志,是否发行电费,发行起始日期,主站算费标志,费率更改标志,费率更改内容,基本费更改标志,基本费更改内容,客户状态,操作类型,操作时间,缴费金额,购电量-表码差,报警值1&2,断电金额,购电次数,当前剩余,累计购电值,报警门限,跳闸门限,换表时间,开户日期,销户日期,结算总表底,结算尖表底,结算峰表底,结算平表底,结算谷表底,结算无功表底,结算时间,算费时间,算费表底时间,算费总表底,算费尖表底,算费峰表底,算费平表底,算费谷表底,算费无功表底,总电费,电度电费,基本费电费,力调电费,其他电费,追补有功电量,追补无功电量,实际功率因数,累计有功电量,累计无功电量,发行电费当月缴费,追补电度电费,追补基本费,发行电费剩余金额,发行电费年月,发行电费数据日期,发行电费算费时间,报警1状态,报警2状态,分合闸状态,报警1改变时间,报警2改变时间,分合闸改变时间,异常标志1,异常标志2,分闸时总表底,报警1短信,报警1声音,分合闸确认,报警2短信,报警2声音,分闸次数,报警1短信时间,报警1声音时间,分合闸发送时间,报警2短信时间,报警2声音时间,成功分合闸时间,报警1短信UUID,报警2短信UUID,信息输出";
	var datatype = "int";
	var toolTips="false"
	for ( var i = 0; i < 111; i++) {
		datatype += ",str";
		toolTips += ",false"
	}
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(header));
	$("#colType").val(datatype);
	$("#filename").val(encodeURI("高压客户信息查询"));
	
	gridopt.gridHeader           = header;
	gridopt.gridColAlign         = "center,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left,left";
	gridopt.gridColTypes         = "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";

	gridopt.gridWidths           = "50,180,80,150,100,80,80,100,120,120,120,120,120,120,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,80,100,100,100,150,100,150,100,100,150,180,120,120,120,100,100,100,100,100,150,100,100,150,150,150,150,150,150,100,120,100,180,180,180,180,180,180,100,100,100,100,100,100,100,100,100,100,110,100,100,110,100,110,110,110,110,100,110,110,110,100,100,100,100,100,110,100,100,100,100,100,110,100,100,100,100,100,100";

	gridopt.gridSrndFlag		 = true;
	gridopt.click				 = false;
	gridopt.dblclick             = true;//增加双击事件
	gridopt.filter(1);
	
	gridopt.grid.setSkin("light");
	
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
	
	delColFlag = true;
	
	var param = '{rtu:"'+$("#rtu").val()+'",' +
				 'yyhh:"'+$.trim($("#yyhh").val())+'",' +
				 'yhmc:"'+$.trim($("#yhmc").val())+'",' +
				 'org:"'+$.trim($("#org").val())+'",' +
				 'lxdh:"'+$.trim($("#lxdh").val())+'",' +
				 'yhzt:"'+$.trim($("#yhzt").val())+'",' +
				 'alarm_type:"'+$.trim($("#alarm_type").val())+'",' +
				 'ctrl_type:"'+$.trim($("#ctrl_type").val())+'",' +
				 
				 'zjg_id:"'+'-1",' +
				 'rtu_id:"'+'-1"}';
	
	gridopt.gridGetDataUrl      = def.basePath  + "ajaxspec/actSearch!yhztSearch.action";
	gridopt.gridSearch			= param;
	
	var prs = $("#prs").val();
	gridopt.filter(prs?prs:20);
	
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
	
	//删除隐藏的列
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
	
	for ( var i = 1; i < 112; i++) {
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

//双击事件
function redoOnRowDblClicked(id){
	//alert(id);
	openModalDialog(id);
}

function openModalDialog(id){
	modalDialog.height = 650;
	modalDialog.width = 900;
	modalDialog.url = "../dialog/yhztDialog.jsp";
	//将json数据相应的id的data数据传到Dialog
	modalDialog.param = {
		rtu:$("#rtu").val(),yyhh:$.trim($("#yyhh").val()),yhmc:$.trim($("#yhmc").val()),org:$.trim($("#org").val()),lxdh:$.trim($("#lxdh").val()),
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

//选择显示的列
function sellookitem(){
	modalDialog.height =  660;
	modalDialog.width  =  600;
	modalDialog.url = "../dialog/ZBlookItemDialog.jsp";
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
	$("#hidecols").val(rtnValue1);
	if(equalFlag != 0){
		window.location.reload();
	}
}


