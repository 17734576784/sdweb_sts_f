var param = window.dialogArguments;

$(document).ready(function(){
	
	$("#btnSearch").click(function(){searchData();});	
	$("#btnClose").click(function(){window.close();});

	fillData(param);
});
function fillData(param) {
	//用户名称
 	$("#userName").html(param.yhmc);
 	
 	//用电地址
 	$("#userAddr").html(param.yddz);
 	
 	//核算单位
 	$("#deptName").html(param.hsdw);
 	
 	//对账批次
 	$("#dzpcNum").html(param.dzpc);
 	
 	//应缴电费总金额
 	$("#yjAllMoney").html(param.hjje);
 	
 	//预收余额
 	$("#ysAllMoney").html(param.hjys);
 	
 	//电费笔数
 	$("#TotalpayTimes").html(param.jezbs);
 
 	$("#msgInfo").html(param.tsxx);

 
 	var t_ysbs = "";
 	var t_ym = null;
 	var t_dfym = "";
 	var t_yjmoney = 0.0;
 	var t_dfmoney = 0.0;
 	var t_wymoney = 0.0; 

 	if(param.jezbs  > 0){
  		//应收标识
	 	t_ysbs = param.detail[0].ysbs;
	 
  		//电费年月
		if(param.detail[0].dfny){
			t_ym  = param.detail[0].dfny.toString();
			t_dfym = t_ym.substring(0,4) + "年" + t_ym.substring(4,6) + "月";
		}
	
  		//应缴金额
  		t_yjmoney = param.detail[0].yjje;
  	
  		//电费金额
  		t_dfmoney = param.detail[0].dfje;
  
  		//违约金金额
	  	t_wymoney = param.detail[0].wyjje;
 	}
 	
 	
 	//应收标识
 	$("#ysbsNum").html(t_ysbs);
 	
 	//电费年月
 	$("#dfym").html(t_dfym);
 	
 	//应缴金额
 	$("#yjmoney").html(round(t_yjmoney,2));
 	
 	//电费金额
 	$("#dfmoney").html(round(t_dfmoney,2));
 	
 	//违约金金额
 	 $("#wymoney").html(round(t_wymoney,2));
}

function clearInfo(){
	$("#userName").html("");
	$("#userAddr").html("");
	$("#deptName").html("");
	$("#dzpcNum").html("");
	$("#yjAllMoney").html("");
	$("#ysAllMoney").html("");
	$("#TotalpayTimes").html("");
	$("#ysbsNum").html("");
	$("#dfym").html("");
	$("#yjmoney").html("");
	$("#dfmoney").html("");
	$("#wymoney").html("");
	$("#msgInfo").html("");
}

function searchData(){//读取MIS里的用户信息
	clearInfo();
	var gyOperQueryPower = {rtu_id : param.rtu_id, zjg_id : param.zjg_id, busi_no : param.yhbh}
	loading.loading();
	$.post(def.basePath + "ajaxoper/actMisGyGS!taskProc.action",
		{
			userData1		: param.rtu_id,
			gyOperStr		: jsonString.json2String(gyOperQueryPower),
			userData2 		: ComntUseropDef.YFF_GYOPER_MIS_QUERYPOWER
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