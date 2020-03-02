//更改费率
var rtnValue = null;
var jsonFLFA = null;
var jsonGHDB = null;
var jsonZDJBF= null;//终端基本费

$(document).ready(function(){
	$("#btnSearch").focus();
	$("#qhsj").val(new Date().Format("yyyy年MM月dd日 HH时mm分").toString());

	$("#btnSearch").click(function(){selcons()});	//检索
	$("#btnReadCard").click(function(){readcard()});//读卡检索
	$("#flfa").change(function(){changeFLFA()});	//费率方案 
	$("#ghdb").change(function(){changeGHDB()});	//'更换电表 
    $("#btnSave").click(function(){doSave();});		//保存

    //初始化新费率方案
	initFLFA();
    setDisabled()
});

function readcard(){
	loading.loading();
    UnDisabled();
    window.setTimeout('window.top.card_comm.readCardSearchGy('+ComntUseropDef.YFF_GYOPER_CHANGERATE+')', 10);
}

function setSearchJson2Html(js_tmp){	//调用readCardSearch后，readcard中异步执行完成后，执行此函数。
	loading.loaded();
	if(!js_tmp)return;
	if(!window.top.card_comm.checkInfo(js_tmp)) return;
		
	rtnValue = js_tmp;
//	//20131202新加验证卡类型	  ?????
//	var reg = /\[(.*?)\]/;
//    var cardmeter_type = rtnValue.cardmeter_type.match(reg);	
//	if(cardmeter_type[1] >= 6){
//		alert('此用户表规约类型为2013版，请到"更改费率2013版"界面进行操作');
//		document.location.reload();
//		return;
//	};
	setConsValue(rtnValue);
	setCardExt(rtnValue);

	//初始化更换电表  
	initChgMeter();
	//在更换电表之前将费控单元的值  赋值给更换电表
}
function selcons(){//检索  click 
	var rtnValue1 = doSearch("ks",ComntUseropDef.YFF_GYOPER_CHANGERATE,rtnValue);
	if(!rtnValue1){
		return;
	}
	
	rtnValue = rtnValue1;
//	//20131202新加验证卡类型	 ?????
//	var reg = /\[(.*?)\]/;
//    var cardmeter_type = rtnValue.cardmeter_type.match(reg);	
//	if(cardmeter_type[1] >= 6){
//		alert('此用户表规约类型为2013版，请到"更改费率2013版"界面进行操作');
//		document.location.reload();
//		return;
//	};
	setConsValue(rtnValue);
	setCardExt(rtnValue);

	//初始化更换电表  
	initChgMeter();
	//在更换电表之前将费控单元的值  赋值给更换电表
	
	UnDisabled();
}

//'更换电表'的init()方法:需要传送rtu_id
function initChgMeter(){
	$("#ghdb option").remove();
	var param = '{rtu_id:"' + $("#rtu_id").val() + '",zjg_id:"'+$("#zjg_id").val()+'"}';	
	$.post(def.basePath + "ajaxspec/actConsPara!GetChgRateInfo.action",{result:param},function(data){
		if (data.result != ""){
			jsonGHDB = eval('(' + data.result + ')');
			for (var i = 0; i < jsonGHDB.rows.length; i++) {
				$("#ghdb").append("<option value=" + jsonGHDB.rows[i].value + ">" + jsonGHDB.rows[i].text + "</option>");
				//保存option  value值
			}
			changeGHDB();
		}
	});
	$("#ghdb").attr("disabled",true);	
}

function changeGHDB(){	//更换电表 onchange:  更新新费率方案的内容为所选电表的费率方案。
	var flfa;
	var ghdb = $("#ghdb").val();

	for (var i = 0; i < jsonGHDB.rows.length; i++) {
		if (ghdb == jsonGHDB.rows[i].value) {
			flfa = jsonGHDB.rows[i].feeproj;
			break;
		}
	}
	if (i < jsonGHDB.rows.length) {
		$("#flfa").val(flfa);
		changeFLFA();
	}	
}

//费率方案 下拉框 onchange
function changeFLFA(){
	var val = $("#flfa").val();
	for (var i = 0; i < jsonFLFA.rows.length; i++) {
		if (jsonFLFA.rows[i].value == val) {
			$("#fams_n").html(jsonFLFA.rows[i].desc);	
			return;
		}		
	}	
}

//费率方案初始化
//需要 添加break zkz 
function initFLFA(){
	//$("#flfa option").remove();
	$.post(def.basePath + "ajaxdyjc/actChgRate!getRateList.action",{},function(data){
		if(data.result != ""){
			jsonFLFA = eval('(' + data.result + ')');
			for (var i = 0; i < jsonFLFA.rows.length; i++) {
				if ((jsonFLFA.rows[i].feeType != SDDef.YFF_FEETYPE_DFL) &&
						(jsonFLFA.rows[i].feeType != SDDef.YFF_FEETYPE_FFL)) continue;	//只要单费率
				$("#flfa").append("<option value=" + jsonFLFA.rows[i].value + ">" + jsonFLFA.rows[i].text + "</option>");
			}
		}
	});
}




//保存--开始
function doSave(){//保存(onclick)
	saveFLFA();
}

function saveFLFA(){//保存(费率方案) 
	
	//rtu_id 和 zjg_id  $.post要用
	//if (rtnValue.feeproj_id == $("#flfa").val()) {
	if ($("#feeproj_id").val() == $("#flfa").val()) {
		alert("费率方案相同,不需修改！");
		return;
	} 
	
	var params = {};
	params.rtu_id = $("#rtu_id").val();
	params.zjgid  = $("#zjg_id").val();	
	var ghdb 	  = $("#ghdb").val();

//卡式预付费一般为一块电表， 且 6103 可能对应多个电表 但是费率一样 故 
	params.chgid0 = $("#flfa").val();
	params.chgid1 = $("#flfa").val();
	params.chgid2 = $("#flfa").val();
	
//如果非要一次更换一块 则 要从jsonGHDB.rows[i].feeproj 取值 其他从界面

	var qhsj = $("#qhsj").val();
	params.chg_date = qhsj.substring(0,4) 	+ qhsj.substring(5,7)	+ qhsj.substring(8,10);
	params.chg_time = qhsj.substring(12,14) + qhsj.substring(15,17) + "00";
	
	//调用获取表底的函数
	getBDPara(params)
	//param.paratype=ComntProMsg.具体参数类型应该不用设置因为别的都没有
	loading.loading()
	var json_str=jsonString.json2String(params);
	$.post(def.basePath + "ajaxoper/actOperGy!taskProc.action",
		{
	        gsm_flag  	     : '0',    //是否发送短信
	        userData1	     : params.rtu_id,
	        userData2	     : ComntUseropDef.YFF_GYOPER_CHANGERATE,//高压操作-换电价
	        zjgid	 	     : params.zjgid,
	        gyOperChangeRate : json_str
		},
		function(data){
			//window.top.addJsonOpDetail(data.detailInfo);
			if(data.result == "success"){
				var colors = "<b style='color:#800000'>";
				var colore = "</b>";
				$("#feeproj_id").val($("#flfa").val());
				//rtnValue.feeproj_id = $("#flfa").val();
				$("#feeproj_desc").html(colors+$("#flfa").find("option:selected").text()+colore);
				$("#feeproj_detail").html(colors+$("#fams_n").html()+colore);
				setDisabled();
				alert("更改费率方案成功");
			}else{
			    alert("更改费率方案失败" + (data.operErr ? data.operErr : ''));
			}
			loading.loaded();
		}	
	);
}
//保存--结束

function getBDPara(params){//传入参数赋值。
	params.mp_id0 = 0;
	params.mp_id1 = 0;
	params.mp_id2 = 0;

	if (jsonGHDB.rows.length > 0) {
		params.mp_id0 = jsonGHDB.rows[0].value;
	}

	if (jsonGHDB.rows.length > 1) {
		params.mp_id1 = jsonGHDB.rows[1].value;
	}

	if (jsonGHDB.rows.length > 2) {
		params.mp_id2 = jsonGHDB.rows[2].value;
	}

	params.date		= '0';
	params.time		= '0';
	params.bd_zy0	= '0';
	params.bd_zyj0	= '0';
	params.bd_zyf0	= '0';
	params.bd_zyp0	= '0';
	params.bd_zyg0	= '0';
	params.bd_fy0	= '0';
	params.bd_fyj0	= '0';
	params.bd_fyf0	= '0';
	params.bd_fyp0	= '0';
	params.bd_fyg0	= '0';
	
	params.bd_zy1	= '0';
	params.bd_zyj1	= '0';
	params.bd_zyf1	= '0';
	params.bd_zyp1	= '0';
	params.bd_zyg1	= '0';
	params.bd_fy1	= '0';
	params.bd_fyj1	= '0';
	params.bd_fyf1	= '0';
	params.bd_fyp1	= '0';
	params.bd_fyg1	= '0';
	                  
	params.bd_zy2	= '0';
	params.bd_zyj2	= '0';
	params.bd_zyf2	= '0';
	params.bd_zyp2	= '0';
	params.bd_zyg2	= '0';
	params.bd_fy2	= '0';
	params.bd_fyj2	= '0';
	params.bd_fyf2	= '0';
	params.bd_fyp2	= '0';
	params.bd_fyg2	= '0';
	
	params.bd_zw0 	= '0';
	params.bd_fw0 	= '0';
	params.bd_zw1 	= '0';
	params.bd_fw1 	= '0';
	params.bd_zw2 	= '0';
	params.bd_fw2 	= '0';
}

//$(document).ready  各个input默认disabled
function setDisabled(){
	$("#qhsj").attr("disabled",true);
	$("#gglx").attr("disabled",true);
	$("#flfa").attr("disabled",true).css("background","#ccc");
	$("#ghdb").attr("disabled",true).css("background","#ccc");
	//$("#xjbf").attr("disabled",true).css("background","#ccc");

	$("#btnSave").attr("disabled",true);
	
}

//selcons 各个input属性disabled更改为 false
function UnDisabled(){
	$("#btnSave").attr("disabled",false);
	$("#qhsj").attr("disabled",false);
	$("#gglx").attr("disabled",false);
	$("#flfa").attr("disabled",false).css("background","white");
	$("#ghdb").attr("disabled",false).css("background","white");
	
	$("#zbd").attr("value","");
	$("#jbd").attr("value","");
	$("#fbd").attr("value","");
	$("#pbd").attr("value","");
	$("#gbd").attr("value","");
//	chageType();
}


function setCardExt(rtnValue)
{
	var tmp_val = rtnValue.cardmeter_type.split("]");
	WRITECARD_TYPE = tmp_val[0].split("[")[1];
//调整 20130402	
//	WRITECARD_TYPE = tmp_val[0].substring(1);
	
	$("#cardmeter_type").html(tmp_val[1].split('"')[0]);
	$("#writecard_no").html(rtnValue.writecard_no);
	$("#meter_id").html(rtnValue.meter_id);
}

