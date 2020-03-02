//更改费率

var rtnValue;
var jsonFLFA;	//费率方案
var jsonGHDB;	//电表信息

var flfa;//公共变量费率方案  保存时判断 原费率方案 与新费率方案是否相同
var rtnBD = null;

$(document).ready(function(){
	//初始化时间
	$("#qhsj").val(new Date().Format("yyyy年MM月dd日 HH时mm分").toString());
	
	//'更改类型'change事件
	$("#gglx").change(function(){chageType()});	

	//'检索'  click事件 
	$("#btnSearch").click(function(){selcons()});
	
	//费率方案change事件,
	$("#flfa").change(function(){changeFLFA()});

	//'更换电表'chage事件
	$("#ghdb").change(function(){changeGHDB()});
	
    //录入按钮  click事件
	$("#btnImportBD").click(function(){Import()});
	
	//保存按钮click事件
    $("#btnSave").click(function(){save();});
    
    IsDisabled();
});

function selcons(){//检索  click 
	var tmp = doSearch("zz",ComntUseropDef.YFF_GYOPER_CHANGERATE,rtnValue);
	if(!tmp)return;
	rtnValue = tmp;
	setConsValue(rtnValue);

	//初始化新费率方案
	initFLFA();
	
	//初始化更换电表  
	initGHDB();
	
	//在更换电表之前将费控单元的值  赋值给更换电表
	$("#ghdb").val(rtnValue.fee_unit);
	$("#yffalarm_id").val(rtnValue.yffalarm_id);
	$("#buy_times").val(rtnValue.buy_times);
	
	//基本费，附加值2，附加值3赋值
	$("#pay_add1").html(rtnValue.pay_add1);
	
	//初始化终端费率
	$("#zdfl").html(rtnValue.pay_add1);
	
	//初始化新基本费 的值
	$("#xjbf").val(rtnValue.pay_add1);
	$("#pay_add2").html(rtnValue.pay_add2);
	$("#pay_add3").html(rtnValue.pay_add3);
	
	//显示终端是否在线
	$("#rtuonline_img").attr("src",rtnValue.onlinesrc);
	$("#rtuonline_img").attr("style","display:blank");
	$("#rtuonline_sp").html(rtnValue.onlinetxt);
	
	UnDisabled();
}

//btn保存(基本费) click事件
function saveJBF(){
	if(rtnBD == null){
		alert("请录入表底信息");
		return ;
	}
	if(!confirm("确定要修改基本费吗?")){
		return ;
	}
	var params = {};
	//rtu_id 和 zjg_id  $.post要用
	params.rtu_id = $("#rtu_id").val();
	params.zjgid = $("#zjg_id").val();
	
	params.jbf_chgval = $("#xjbf").val();
	
	var qhsj = $("#qhsj").val();
	params.chg_date = qhsj.substring(0,4)+qhsj.substring(5,7)+qhsj.substring(8,10)
	params.chg_time = qhsj.substring(12,14)+qhsj.substring(15,17)+"00";
	//调用获取表底的函数
	getBDPara(params);
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post(def.basePath+"ajaxoper/actOperGy!taskProc.action",
			{
		        gsm_flag  : '0',    //是否发送短信
		        userData1 : params.rtu_id,
		        userData2 : ComntUseropDef.YFF_GYOPER_CHANGPAYADD,//高压操作-换基本费
		        zjgid	  : params.zjgid,
		        gyOperChanePayAdd : json_str
			},
			function(data){
				loading.loaded();
				if(data.result == "success"){
					//点击开户之后 各个input框 disabled
					IsDisabled();
					alert("更改基本费成功!");					
				}else{
				    alert("更改基本费失败!" + (data.operErr ? data.operErr : ''));
				}
			}	
	);
}

//btn保存(费率方案) click事件
function saveFLFA(){
	if(rtnBD == null){
		alert("请录入表底信息");
		return ;
	}
	if($("#flfa").val() == flfa){
		alert("费率方案相同不能保存,请重新输入");
		return ;
	}
	if(!confirm("确定要修改费率吗?")){
		return ;
	}
	var params = {};
	
	params.rtu_id = $("#rtu_id").val();
	params.zjgid = $("#zjg_id").val();
	
	var ghdb = $("#ghdb").val();
	
	if(ghdb == jsonGHDB.rows[0].value){
		params.chgid0 = $("#flfa").val();
		params.chgid1 = jsonGHDB.rows[0].feeproj1;
		params.chgid2 = jsonGHDB.rows[0].feeproj2;
	}
	else if(ghdb == jsonGHDB.rows[1].value){
		params.chgid0 = jsonGHDB.rows[0].feeproj;
		params.chgid1 = $("#flfa").val();
		params.chgid2 = jsonGHDB.rows[0].feeproj2;
	}else if(ghdb == jsonGHDB.rows[2].value){
		params.chgid0 = jsonGHDB.rows[0].feeproj;
		params.chgid1 = jsonGHDB.rows[0].feeproj1;
		params.chgid2 = $("#flfa").val();
	}
	
	var qhsj = $("#qhsj").val();
	params.chg_date = qhsj.substring(0,4)+qhsj.substring(5,7)+qhsj.substring(8,10)
	params.chg_time = qhsj.substring(12,14)+qhsj.substring(15,17)+"00";
	
	//调用获取表底的函数
	getBDPara(params);
	
	var json_str = jsonString.json2String(params);
	loading.loading();
	$.post(def.basePath+"ajaxoper/actOperGy!taskProc.action",
			{
		        gsm_flag      : '0',    //是否发送短信
		        userData1     : params.rtu_id,
		        userData2     : ComntUseropDef.YFF_GYOPER_CHANGERATE,//高压操作-换电价
		        zjgid	      : params.zjgid,
		        gyOperChangeRate : json_str
			},
			function(data){
				loading.loaded();
				if(data.result == "success"){
					//点击开户之后 各个input框 disabled
					IsDisabled();
					alert("更改费率方案成功!");
				}else{
				    alert("更改费率方案失败!" + (data.operErr ? data.operErr : ''));
				}
			}	
	);
}

//btn保存  click事件
function save(){
	var gglx = $("#gglx").val();
	if(gglx == 'flcs'){
		saveFLFA();
	}
	else if(gglx == "jbf"){
		saveJBF();
	}
	else{
		alert("系统错误");
	}
}

//录入 click事件
function Import(){
	var tmp = importBD($("#rtu_id").val(),$("#mp_id").val());
	if(!tmp){
		return;
	}else{
		rtnBD = tmp;
	    $("#bdxx").html(rtnBD.td_bdinf);
	    $("#btnSave").attr("disabled",false);
	}	
}

//费率方案  init()
function initFLFA(){
	$("#flfa option").remove();
	$.post(def.basePath + "ajaxdyjc/actChgRate!getRateList.action",{},function(data){
		if(data.result != ""){
			jsonFLFA = eval('(' + data.result + ')');
			for(var i = 0;i<jsonFLFA.rows.length;i++){
				$("#flfa").append("<option value = "+jsonFLFA.rows[i].value+">"+jsonFLFA.rows[i].text+"</option>");
			}
			changeFLFA();
		}
	});
}

//新费率方案  change事件 联动 显示该费率方案详细信息
function changeFLFA(){
	var val=$("#flfa").val();
	for(var i=0;i<jsonFLFA.rows.length;i++){
		if(jsonFLFA.rows[i].value == val){
			$("#fams_n").html(jsonFLFA.rows[i].desc);	
			return;
		}
	}
}

//加载电表信息
function initGHDB(){
	$("#ghdb option").remove();
	var param = '{rtu_id:"'+$("#rtu_id").val()+'",zjg_id:"'+$("#zjg_id").val()+'"}';
	$.post(def.basePath + "ajaxspec/actConsPara!retChgRateInfo.action",{result:param},function(data){
		if(data.result != ""){
			//电表信息{rows:[value:1,text:'电表1',ptfz:1,ptfm:1,pt:1,ctfz:1,ctfm:1,ct:1,feeproj:1,feeprog1:1,feeprog2:1]}
			jsonGHDB = eval('(' + data.result + ')');
			for(var i=0;i<jsonGHDB.rows.length;i++){
				$("#ghdb").append("<option value="+jsonGHDB.rows[i].value+">"+jsonGHDB.rows[i].text+"</option>");
			}
			changeGHDB();
		}
	});
}

//'更换电表'change事件   用到全局变量jsonGHDB
function changeGHDB(){
	var ghdb = $("#ghdb").val();//得到了value值

	if(ghdb == jsonGHDB.rows[0].value){
		flfa = jsonGHDB.rows[0].feeproj;
	}else if(ghdb == jsonGHDB.rows[1].value){
	    flfa=jsonGHDB.rows[0].feeproj1;
	}else if(ghdb == jsonGHDB.rows[2].value){
		flfa = jsonGHDB.rows[0].feeproj2;
	}
	$("#flfa").val(flfa);
}

//更改类型  change事件
function chageType(){
	
	var txt = $("#gglx").val();
	if(txt == 'flcs'){
		$("#flfa").attr("disabled",false).css("background","white");
		$("#ghdb").attr("disabled",false).css("background","white");
		$("#xjbf").attr("disabled",true).css("background","#ccc");
	}
	if(txt == 'jbf'){
		$("#flfa").attr("disabled",true).css("background","#ccc");
		$("#ghdb").attr("disabled",true).css("background","#ccc");
		$("#xjbf").attr("disabled",false).css("background","white");
	}
}

//btn保存 click事件json_str参数(包含三个表底的参数)
function getBDPara(params){
	params.date = rtnBD.date;
	params.time = 0;
	for(var i = 0; i < 3; i++){
		eval("params.mp_id"  + i + "=rtnBD.mp_id" + i);
		
		if(rtnBD.zf_flag.substring(i, i + 1) == "0"){	//正向
			eval("params.bd_zy"  + i + " = rtnBD.zbd" + i);
			eval("params.bd_zyj" + i + " = rtnBD.jbd" + i);
			eval("params.bd_zyf" + i + " = rtnBD.fbd" + i);
			eval("params.bd_zyp" + i + " = rtnBD.pbd" + i);
			eval("params.bd_zyg" + i + " = rtnBD.gbd" + i);
			eval("params.bd_zw"  + i + " = rtnBD.wbd" + i);
			eval("params.bd_fy"  + i + " = 0");
			eval("params.bd_fyj" + i + " = 0");
			eval("params.bd_fyf" + i + " = 0");
			eval("params.bd_fyp" + i + " = 0");
			eval("params.bd_fyg" + i + " = 0");
			eval("params.bd_fw"  + i + " = 0");
		}
		else{
			eval("params.bd_zy"  + i + " = 0");
			eval("params.bd_zyj" + i + " = 0");
			eval("params.bd_zyf" + i + " = 0");
			eval("params.bd_zyp" + i + " = 0");
			eval("params.bd_zyg" + i + " = 0");
			eval("params.bd_zw"  + i + " = 0");
			eval("params.bd_fy"  + i + " = rtnBD.zbd" + i);
			eval("params.bd_fyj" + i + " = rtnBD.jbd" + i);
			eval("params.bd_fyf" + i + " = rtnBD.fbd" + i);
			eval("params.bd_fyp" + i + " = rtnBD.pbd" + i);
			eval("params.bd_fyg" + i + " = rtnBD.gbd" + i);
			eval("params.bd_fw"  + i + " = rtnBD.wbd" + i);
		}
	}
}

//刚刚进入界面  各个input默认disabled
function IsDisabled(){
	$("#qhsj").attr("disabled",true);
	$("#gglx").attr("disabled",true);
	$("#flfa").attr("disabled",true).css("background","#ccc");
	$("#ghdb").attr("disabled",true).css("background","#ccc");
	$("#xjbf").attr("disabled",true).css("background","#ccc");
	$("#btnSave").attr("disabled",true);
	$("#btnImportBD").attr("disabled",true);
}

//selcons 各个input属性disabled更改为 false
function UnDisabled(){
	$("#qhsj").attr("disabled",false);
	$("#gglx").attr("disabled",false);
	$("#flfa").attr("disabled",false).css("background","white");
	$("#ghdb").attr("disabled",false).css("background","white");
	$("#xjbf").attr("disabled",false);
	$("#btnImportBD").attr("disabled",false);
	$("#bdxx").html("");
	rtnBD = null;	
}
