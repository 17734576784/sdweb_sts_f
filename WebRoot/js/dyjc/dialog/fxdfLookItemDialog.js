var params     = window.dialogArguments;
var c_lookitem = params.lookitem;

//jsp中的id与列中的列号对应
 var num = [[3],//基本信息
	 [4,5,6,7,8,9,10,11,12,13,46,47,48],//费控参数
	 [14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29],//上次结算
	 [30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45]//算费表底
 	];
 var ids = ["jbxx_selAll","fkcs_selAll","scjs_selAll","sfbd_selAll"];
	

$(document).ready(function(){
	
	//btn设置click事件
	$("#set").click(function(){
		setCo();
	})
	
	//btn切换事件
	label_change();
	
    //基本信息-全选
	$("#jbxx_selAll").click(function(){
		selAll(this.checked,num[0]);
	})
	//费控参数-全选
	$("#fkcs_selAll").click(function(){
		selAll(this.checked,num[1]);
	})
	//上次结算-全选
	$("#scjs_selAll").click(function(){
		selAll(this.checked,num[2]);
	})
	//算费表底-全选
	$("#sfbd_selAll").click(function(){
		selAll(this.checked,num[3]);
	})

	//第一次进入页面(即cookies是否为空 )选中所有的全选按钮
	if(c_lookitem.split("|")[0] == "" || 
	   c_lookitem.split("|")[0] == null || 
	   c_lookitem.split("|")[0] == undefined){
	   $("#jbxx_selAll").attr("checked",true);	
	   $("#fkcs_selAll").attr("checked",true);	
	   $("#scjs_selAll").attr("checked",true);
	   $("#sfbd_selAll").attr("checked",true);
	   
	   for(var i = 1; i < 49; i++ ){		
			$("#"+i).attr("checked",true);	
	   }
	   return;								
	}
	
	//不是第一次进入页面,即cookie不为空,根据上次cookie记录来进行选择
	c_lookitem = c_lookitem.split("|")[0].split(",");
	if(c_lookitem == "")return;
	for(var i = 0; i < c_lookitem.length; i++ ){
		$("#"+c_lookitem[i]).attr("checked",true);
	}

	//判断各个全选按钮是否应该被选中
	setSelAll()

});

function setCo(){//记录选中项id并返回
	var unHiddenCol = "";
	var hiddenCol = "";
	
	for( var i = 1; i <50; i++ ){
		if($("#" + i).val() != undefined){
			if($("#" + i).attr("checked")){
				unHiddenCol += "," + i;
			}else{
				hiddenCol += "," + i;
			}
		}
	}
	unHiddenCol = unHiddenCol.substring(1);//第一个是"," 去掉,
	hiddenCol = hiddenCol.substring(1);
	if(unHiddenCol == ""){
		alert("请至少选择一项！");
		return;
	}
	
	var alltypeCol = unHiddenCol+"|"+hiddenCol;

	var item = window.dialogArguments.lookitem;
	if(item == alltypeCol){
		window.returnValue = "0" + alltypeCol;	//选择项和cookie记录的没有变化
	}else{
		window.returnValue = "1" + alltypeCol;	//选择项和cookie记录的不同
	}
	window.close();
}


function selAll(chk,num_x){//全选/全不选
	for(var i = 0; i < num_x.length; i++){
		$("#" + num_x[i]).attr("checked",chk);
	}
}

function setSelAll(){//设置全选按钮状态是否选中
	for(var j = 0 ; j < ids.length ; j++ ){
		$("#" + ids[j]).attr("checked",true);
		var num_x = num[j];
	    for(var i = 0; i < num_x.length; i++){
			if(!$("#" + num_x[i]).attr("checked")){
				$("#" + ids[j]).attr("checked",false);
				break;
			}
		}
	}	
}

//按钮切换
function label_change(){
	$("#yffcs").click(function(){
		$("#d_yffcs").css("display","block");
		$("#d_yffzt").css("display","none");
		$("#d_bjkzcs").css("display","none");
		
		$("#yffcs_l").attr("class","titlel");
		$("#yffcs").attr("class","titlem");
		$("#yffcs_r").attr("class","titler");
		
		$("#yffzt_l").attr("class","titlel1");
		$("#yffzt").attr("class","titlem1");
		$("#yffzt_r").attr("class","titler1");
		
		$("#bjkzcs_l").attr("class","titlel1");
		$("#bjkzcs").attr("class","titlem1");
		$("#bjkzcs_r").attr("class","titler1");
	});
	
	$("#yffzt").click(function(){
		$("#d_yffcs").css("display", "none");
		$("#d_yffzt").css("display", "block");
		$("#d_bjkzcs").css("display","none");
		
		$("#yffcs_l").attr("class","titlel1");
		$("#yffcs").attr("class","titlem1");
		$("#yffcs_r").attr("class","titler1");
		
		$("#yffzt_l").attr("class","titlel");
		$("#yffzt").attr("class","titlem");
		$("#yffzt_r").attr("class","titler");
		
		$("#bjkzcs_l").attr("class","titlel1");
		$("#bjkzcs").attr("class","titlem1");
		$("#bjkzcs_r").attr("class","titler1");
	});
	
   
}
