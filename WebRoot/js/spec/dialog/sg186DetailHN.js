var param = window.dialogArguments;

$(document).ready(function(){
	
	$("#btnSearch").click(function(){searchData();});	
	$("#btnClose").click(function(){window.close();});
	
	fillData(param);
});

function fillData(param) {
 $("#userName").html(param.yhmc);
 $("#userAddr").html(param.yddz);
 $("#deptName").html(param.hsdwname);
 $("#money").html(param.hjje);
 $("#preReceive").html(param.hjys);
 $("#TotalpayTimes").html(param.jezbs);
 $("#msgInfo").html(param.tsxx);
 //20130427更改--------------------
 var date = "";
 var dfje = 0.0;
 var wyjje = 0.0;
 if(param.jezbs > 0){
  
  //添加时间，取数组第一个
  if(param.detail[0].dfny){
   var ymd  = param.detail[0].dfny.toString();
   date = ymd.substring(0,4) + "年" + ymd.substring(4,6) + "月";
  }
  //添加电费金额和违约金,遍历数组累加
  for(var i=0; i<param.detail.length; i++){
   dfje += parseFloat(param.detail[i].dfje,2);
   wyjje += parseFloat(param.detail[i].wyjje);
  } 
 }
 $("#preymd").html(date);
 $("#premoney").html(round(dfje,2));
 $("#wymoney").html(round(wyjje,2));
}
function clearInfo(){
	$("#userName").html("");
	$("#userAddr").html("");
	$("#deptName").html("");
	$("#money").html("");
	$("#preReceive").html("");
	$("#TotalpayTimes").html("");
	$("#preymd").html("");
	$("#premoney").html("");
	$("#wymoney").html("");
	$("#msgInfo").html("");
}

function searchData(){//读取MIS里的用户信息
	
	clearInfo();
//	var gyOperQueryPower = {rtu_id : param.rtu_id, mp_id : param.mp_id, yhbh : param.yhbh}
	var gyOperQueryPower = {rtu_id : param.rtu_id, zjg_id : param.zjg_id, busi_no : param.yhbh}
	loading.loading();
	$.post(def.basePath + "ajaxoper/actMisGy!taskProc.action",
		{
			 userData1  : param.rtu_id,
   			 gyOperStr  : jsonString.json2String(gyOperQueryPower),
   			 userData2   : ComntUseropDef.YFF_GYOPER_MIS_QUERYPOWER
		},
		function(data){
			loading.loaded();
			//使用MIS接口
			if(data.misUseflag == "true"){
				if(data.misOkflag == "true"){	//成功
					var json = eval("(" + data.gyOperStr + ")");
					json.tsxx = "操作成功!";
					fillData(json);
				}
				else{	//失败
					alert(data.misResult);
				}
			}
			//不使用MIS接口
			else{
				alert("【未使用MIS接口】");
			}
	});
	
}