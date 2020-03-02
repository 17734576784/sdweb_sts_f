var grid_select_row1 = 0;
var grid_select_row2 = 0;
var gl_dict;
var flag=false;
var flagOver=false;//确定 全选json是否加载完毕
var grid//gridbox1的全局变量

var cur_cs_id = -1;

$(document).ready(function(){
	
	initDropdown();
	initTtList();
	
	$("#tianjia").click(function(){
		add()
	});
	$("#xiugai").click(function(){
		update();
	});	
	$("#pldel").click(function(){
		 pldel();
	});
});

function initDropdown(){
	flagOver = false;
	var text = '{item:["' + Dict.DICTITEM_USEFLAG + '"]}';
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : text}, function(data)
	{
		var json = eval('(' + data.result + ')');
		var iid = 0;
		var dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_USEFLAG);
		gl_dict = dict
		flagOver=true;
		for ( var i = 0; i < dict.length; i++) {
			$("#useFlag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
	});
}

function selectAllOrNone(checked) {      //全选&全不选
	var flag = false;
	if (checked.src.indexOf("item_chk0.gif") != -1) {
		flag = true;
		checked.src = def.basePath + 'images/grid/imgs/item_chk1.gif';
	} else if (checked.src.indexOf("item_chk1.gif") != -1) {
		flag = false;
		checked.src = def.basePath +  'images/grid/imgs/item_chk0.gif';
	}
	for ( var i = 0; i < mygridbox1.getRowsNum(); i++) {
		mygridbox1.cells2(i, 0).setValue(flag);
	}
}

function initTtList(){
	//任务模板列表	
	mygridbox1 = new dhtmlXGridObject('gridbox1');
	mygridbox1.setImagePath(def.basePath + "images/grid/imgs/");
	mygridbox1.setHeader("<img src='"+def.basePath + "images/grid/imgs/item_chk0.gif' onclick='selectAllOrNone(this);' />,序号,名称,使用标志,标准,最小值,&nbsp;");
	mygridbox1.setInitWidths("40,50,120,70,70,70,*");
	mygridbox1.setColAlign("center,center,left,left,left,right");
	mygridbox1.setColTypes("ch,ro,ro,ro,ro,ro");
	mygridbox1.attachEvent("onRowSelect", doOnRowSelected);
	
	mygridbox1.init();
	mygridbox1.enableTooltips("false,false,false,false,false,false");
	$.post(def.basePath + "/ajaxdocs/actCsStand.action" , function(data){
		if(data.result!=""){
			var json = eval('(' + data.result + ')');
			mygridbox1.parse(json, "", "json");
			mygridbox1.selectRow(grid_select_row1, true);
		}
	});
	grid=mygridbox1;
}
function doOnRowSelected(rId){
	//重复点击时直接返回
	if(cur_cs_id == rId) {
		return;
	}
	
	cur_cs_id = rId;
	
	var idx = mygridbox1.getRowIndex(rId);
	grid_select_row1=idx;
	grid_select_row2=0;
	$("#describe").val(mygridbox1.cells(rId,2).getValue());
	useflag(mygridbox1.cells(rId,3).getValue())
	$("#stand").val(mygridbox1.cells(rId,4).getValue());
	$("#csMinval").val(mygridbox1.cells(rId,5).getValue());
	
	initTtListItem(rId);
	//给隐含域的三个变量赋值
	$("#stand_id").val(rId);
}
//根据mygridbox1的value值决定使用标志的值
function useflag(str){
	if(!flagOver){
		 window.setTimeout('useflag(str)', 50); 
		 return; 
	}
	for ( var i = 0; i < gl_dict.length; i++) {
		if(gl_dict[i].text==str){
			$("#useFlag").val(gl_dict[i].value);
		};
	}
}

function initTtListItem(rId){
	//任务模板列表	
	mygridbox2 = new dhtmlXGridObject('gridbox2');
	mygridbox2.setImagePath(def.basePath + "images/grid/imgs/");
	mygridbox2.setHeader("序号,实际cos,月电费增减(%),&nbsp;");
	mygridbox2.setInitWidths("40,160,160,*");
	mygridbox2.setColAlign("center,right,right");
	mygridbox2.setColTypes("ro,ro,ro");
	mygridbox2.enableTooltips("false,false,false");
	mygridbox2.attachEvent("onRowSelect", doOnRowSelected1);
	mygridbox2.attachEvent("onRowDblClicked", doOnRowDblClicked); 
		
	mygridbox2.init();
	var params={};
	params.id=rId;
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post(def.basePath + "/ajaxdocs/actCsStand!getItems.action",{result:json_str},function(data){
		if(data.result!=""){
			loading.loaded();
			var json = eval('(' + data.result + ')');
			mygridbox2.parse(json, "", "json");
			mygridbox2.selectRow(grid_select_row2, true);

		}
	});
}
function doOnRowSelected1(rId1){
	grid_select_row2=mygridbox2.getRowIndex(rId1);
	$("#cos_id").val(rId1.split("_")[0]);
	$("#items_id").val(rId1.split("_")[1]);
} 

//修改
function update(){
	if("" == $("#describe").val()){
		alert("名称不能为空");
		return;
	}
    var params={};
	params.describe = $("#describe").val();
	params.useFlag =  $("#useFlag").val();
	params.stand =    $("#stand").val();
	params.csMinval=  $("#csMinval").val();
	
	if(isNaN(params.stand) || params.stand < 60 || params.stand > 90) {
		alert("执行标准必需在60-90之间!");
		$("#stand").focus().select();
		return;
	}
	
	if(isNaN(params.csMinval) || parseInt(params.csMinval) > parseInt(params.stand)) {
		alert("最小值必需比执行标准小!");
		$("#csMinval").focus().select();
		return;
	}
	
	if(params.csMinval < 0) {
		$("#csMinval").focus().select();
		alert("最小值必需正数!");
		return;
	}
	
	params.stand_id = $("#stand_id").val();
	var json_str = jsonString.json2String(params);
	$.post(def.basePath + "/ajaxdocs/actCsStand!update.action",{result:json_str},function(data){
		if(data.result!=""){
			if(data.result == "success"){
				alert("修改成功");
				initTtList();
				ggtz();
			}else{
				alert("修改失败");
			}
		}
	});
}

//批量删除
function pldel(){
	
	var seledrow = "";
	for ( var i = 0; i < this.grid.getRowsNum(); i++) {
		if(mygridbox1.cells2(i, 0).getValue()==1){
			seledrow += "," + this.grid.getRowId(i);
		}
	}
	
	if(seledrow == ""){
		alert("请选择记录后删除!");
	    return;
	}
	
	if(!confirm("确定要删除吗?"))return;
	
	seledrow = seledrow.substring(1);
	
	$.post(def.basePath + "ajaxdocs/actCsStand!pldel.action",{result:seledrow},function(data){
		if(data.result == SDDef.SUCCESS){
		    alert("批量删除成功");
		    $("#describe").val("");
		    $("#stand").val("");
		    $("csMinval").val("");
		    $("#useFlag").val(0);
		    initTtList();
		    initTtListItem($("#stand_id").val());//防止最后一个数据被删除的时候,initTtListItem()方法不执行
		    grid_select_row1 = 0;//将选行标记赋值为0
		    grid_select_row2 = 0;
		    ggtz();
		}else if(data.result == SDDef.FAIL){
			alert(comm_i18n.gridopt_del_fail+"!");
		}else {
			alert(data.result);
		}
	});
}
function doOnRowDblClicked(rId){
	openModalDialog(rId);
}
//给gridbox2增加双击事件
function openModalDialog(rId){
	var cos_id =rId.split("_")[0];
	var item_id=rId.split("_")[1];
	var index  =mygridbox2.cells(rId,0).getValue();
	var realcos=mygridbox2.cells(rId,1).getValue();
	var dfchgarate=mygridbox2.cells(rId,2).getValue();
	modalDialog.height = 130;
	modalDialog.width  = 330;
	modalDialog.url = "cosstandDialog.jsp";
	
	modalDialog.param = {index:index,cos_id : cos_id,item_id:item_id,realcos:realcos,dfchgarate:dfchgarate,lang : window.top._lang, doc_per : window.top._doc_per};
	var rtnValue = modalDialog.show();
	
	if(rtnValue == undefined || rtnValue == null){
		return;
	}
	initTtListItem($("#stand_id").val());
	mygridbox2.selectRow(rId);
	ggtz();
}
//新增事件
function add(){
	if(""==$("#describe").val()){
		alert("名称不能为空");
		return;
	}
    var params={};
	params.describe = $("#describe").val();
	params.useFlag =  $("#useFlag").val();
	params.stand =    $("#stand").val();
	params.csMinval = $("#csMinval").val();
	
	if(isNaN(params.stand) || params.stand < 60 || params.stand > 90) {
		alert("执行标准必需在60-90之间!");
		$("#stand").focus().select();
		return;
	}
	
	if(isNaN(params.csMinval) || parseInt(params.csMinval) > parseInt(params.stand)) {
		alert("最小值必需比执行标准小!");
		$("#csMinval").focus().select();
		return;
	}
	
	if(params.csMinval < 0) {
		$("#csMinval").focus().select();
		alert("最小值必需正数!");
		return;
	}
	
	var json_str = jsonString.json2String(params);
	
	$.post(def.basePath + "/ajaxdocs/actCsStand!add.action",{result:json_str},function(data){
		if(data.result!=""){
			if(data.result="success"){
				alert("增加成功");
				initTtList();
				ggtz();
			}else{
				alert("增加失败");
			}
		}
	});
}

function ggtz() {
	window.top.global_ggtz(window.top.dbUpdate.YD_DBUPDATE_TABLE_CSSTAND);
}
