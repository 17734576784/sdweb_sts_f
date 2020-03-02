var params         = window.dialogArguments;
var c_lookitem     = params.lookitem;
var c_lookitem_tmp = params.lookitem_tmp
$(document).ready(function(){
	//加载所必须的函数
	
	//btn设置click事件
	$("#set").click(function(){
		setCo(gl_part);
	})
	
	//btn切换事件
	label_change();
	
    //预付费参数全选函数
	$("#yffcs_jbxx_selAll").click(function(){
		yffcs_jbxx_selAll(this.checked);
	})
	
	$("#yffcs_fkcs_selAll").click(function(){
		yffcs_fkcs_selAll(this.checked);
	})
	
	$("#yffcs_kzxx_selAll").click(function(){
		yffcs_kzxx_selAll(this.checked);
	})
	
	$("#yffcs_sfxx_selAll").click(function(){
		yffcs_sfxx_selAll(this.checked);
	})
	
	//预付费状态 全选 函数
    $("#yffzt_jbzt_selAll").click(function(){
		yffzt_jbzt_selAll(this.checked);
	})
	
    $("#yffzt_bdxx_selAll").click(function(){
		yffzt_bdxx_selAll(this.checked);
	})
	
    $("#yffzt_dfxx_selAll").click(function(){
		yffzt_dfxx_selAll(this.checked);
	})
	
    $("#yffzt_kzzt_selAll").click(function(){
		yffzt_kzzt_selAll(this.checked);
	})

	//报警状态 全选 函数
	$("#alarm").click(function(){
		alarm(this.checked);
	})
	
	
	//第一次进入页面(即cookies是否为空 )选中所有的全选按钮
	
	if(c_lookitem.split("|")[0] == "" || 
	   c_lookitem.split("|")[0] == null || 
	   c_lookitem.split("|")[0] == undefined){
	   $("#yffcs_jbxx_selAll").attr("checked",true);	
	   $("#yffcs_fkcs_selAll").attr("checked",true);	
	   $("#yffcs_kzxx_selAll").attr("checked",true);
	   $("#yffcs_sfxx_selAll").attr("checked",true);
	   $("#yffzt_jbzt_selAll").attr("checked",true);
	   $("#yffzt_bdxx_selAll").attr("checked",true);
	   $("#yffzt_dfxx_selAll").attr("checked",true);
	   $("#yffzt_kzzt_selAll").attr("checked",true);
	   $("#alarm").attr("checked",true);
	   
	   //初始化全选中  分别选中9个部分
	   for(var i =1; i<=gl_part.length ; i++){
		  for(var j = (i*100+1); j <= gl_part[i-1]; j++ ){		
			$("#"+j).attr("checked",true);
		  }	
	   }
	   
	   //判断各个全选按钮是否应该被选中
       all_check_flag()
	   return;								
	}
	
	//不是第一次进入页面,即cookie不为空,根据上次cookie记录来进行选择
	c_lookitem = c_lookitem.split("|")[0].split(",");
	c_lookitem_tmp = c_lookitem_tmp.split("|")[0].split(",");
	if(c_lookitem_tmp == "")return;
	for(var i = 0; i < c_lookitem_tmp.length; i++ ){
		$("#"+c_lookitem_tmp[i]).attr("checked",true);
	}
    //判断各个全选按钮是否应该被选中
    all_check_flag()
});

//三位数字123 取第一位的方法  没有用到
function extract_First(num){
    var tmp = gl_part[0].toString()
	tmp = parseInt(tmp.substring(0,1));//目的是从01开始计数  第一个数比较特殊 前两个为必选项目
}

//设置
function setCo(){
	var unHiddenCol = "";
	var unHiddenCol_tmp = "";//在cookie 在dialog.jsp 初始化用
	
	//首先取出9个模块每个的个数 下一步用到
	var sum = new Array(9);
	for(var i =1; i<=gl_part.length ; i++){
		sum[i-1] = gl_part[i-1]%(i*100)
	}
    
	//然后取出9个模块开始计数的位置 
	sum_start = new Array(9);
	
	for(var i = 0 ;i< sum_start.length ; i++){
		if(i == 0){
			sum_start[0] = 0;
		}else if(i == 1){
			sum_start[1] = sum[0];
		}else{
			sum_start[i] = sum_start[i-1]+sum[i-1]
		}
	}
    
	//需要显示项
	for(var i =1; i<=gl_part.length ; i++){
		for(var j = (i*100+1); j <= gl_part[i-1]; j++ ){		
			if($("#"+j).attr("checked")){
				unHiddenCol += "," + (sum_start[i-1]+j%(i*100));
				unHiddenCol_tmp += "," + (j);
			}
		}	
	}
	
	unHiddenCol = unHiddenCol.substring(1);//第一个是','所以要去掉 下表为0的,
	unHiddenCol_tmp = unHiddenCol_tmp.substring(1);//第一个是','所以要去掉 下表为0的,
	
	if(unHiddenCol == ""){
		alert("请至少选择一项！");
		return;
	}
	
	//需要隐藏项
	var hiddenCol = "";
	var hiddenCol_tmp = "";
	for(var i =1; i<=gl_part.length ; i++){
		for(var j = (i*100+1); j <= gl_part[i-1]; j++ ){		
			if(!$("#"+j).attr("checked")){
				hiddenCol += "," + (sum_start[i-1]+j%(i*100));
				hiddenCol_tmp += "," + (j);
			}
		}	
	}
	hiddenCol = hiddenCol.substring(1);
	hiddenCol_tmp = hiddenCol_tmp.substring(1);
	
	var alltypeCol     = unHiddenCol    +"|"+hiddenCol;
    var alltypeCol_tmp = unHiddenCol_tmp+"|"+hiddenCol_tmp;
	

//	cookie.set(cookie.lookitem, alltypeCol);
	var item     = window.dialogArguments.lookitem;
	var item_tmp = window.dialogArguments.lookitem_tmp;

	var returnValues =new Array(2);
	if(item == alltypeCol){
		 returnValues[0]   = "0" + alltypeCol;	//同
		 returnValues[1]   = alltypeCol_tmp
		 window.returnValue = returnValues ; 
		 
	}else{
		 returnValues[0]   = "1" + alltypeCol;	//同
		 returnValues[1]   = alltypeCol_tmp
		 window.returnValue = returnValues ; 
	}	
	window.close();
}

////预付费参数--基本信息  全选
function yffcs_jbxx_selAll(chk){
	var tmp = gl_part[0].toString()
	tmp = parseInt(tmp.substring(0,1))*100+3;//目的是从01开始计数  第一个数比较特殊 前两个为必选项目
	for(var i=tmp;i<=gl_part[0];i++){
		$("#"+i).attr("checked",chk);
	}
}

//预付费参数--费控参数  全选
function yffcs_fkcs_selAll(chk){
	var tmp = gl_part[1].toString()
	tmp = parseInt(tmp.substring(0,1))*100+1;
	for(var i=tmp;i<=gl_part[1];i++){
		$("#"+i).attr("checked",chk);
	}
}

//预付费参数--控制信息  全选
function yffcs_kzxx_selAll(chk){
	var tmp = gl_part[2].toString()
	tmp = parseInt(tmp.substring(0,1))*100+1;
	for(var i = tmp ;i<=gl_part[2];i++){
		$("#"+i).attr("checked",chk);
	}
}

//预付费参数--算费信息  全选
function yffcs_sfxx_selAll(chk){
	var tmp = gl_part[3].toString()
	tmp = parseInt(tmp.substring(0,1))*100+1;
	for(var i= tmp ;i<=gl_part[3];i++){
		$("#"+i).attr("checked",chk);
	}
}

<!--预付费状态开始--->

//预付费状态--基本状态  全选
function yffzt_jbzt_selAll(chk){
	var tmp = gl_part[4].toString()
	tmp = parseInt(tmp.substring(0,1))*100+1;
	for(var i= tmp ;i<=gl_part[4];i++){
		$("#"+i).attr("checked",chk);
	}
}
//预付费状态--表底信息  全选
function yffzt_bdxx_selAll(chk){
	var tmp = gl_part[5].toString()
	tmp = parseInt(tmp.substring(0,1))*100+1;
	for(var i = tmp ;i<=gl_part[5];i++){
		$("#"+i).attr("checked",chk);
	}
}
//预付费状态--电费信息  全选
function yffzt_dfxx_selAll(chk){
	var tmp = gl_part[6].toString()
	tmp = parseInt(tmp.substring(0,1))*100+1;
	for(var i= tmp ;i<=gl_part[6];i++){
		$("#"+i).attr("checked",chk);
	}
}
//预付费状态--控制状态  全选
function yffzt_kzzt_selAll(chk){
	var tmp = gl_part[7].toString()
	tmp = parseInt(tmp.substring(0,1))*100+1;
	for(var i= tmp ;i<=gl_part[7];i++){
		$("#"+i).attr("checked",chk);
	}
}

//报警及控制参数--报警及控制参数  全选
function alarm(chk){
	var tmp = gl_part[8].toString()
	tmp = parseInt(tmp.substring(0,1))*100+1;
	for(var i= tmp ;i<=gl_part[8];i++){
		$("#"+i).attr("checked",chk);
	}
}

//进入此页面,判断各个全选按钮是否应该选中
//进入此页面,判断各个全选按钮是否应该选中
function all_check_flag(){
   for(var i = 1; i<=gl_part.length ; i++){
	   if(i==1){//预付费参数--基本信息   
		   $("#yffcs_jbxx_selAll").attr("checked",true);
		   for(var j = (i*100+1); j <= gl_part[i-1]; j++ ){
			   if(!$("#"+j).attr("checked")){
				   $("#yffcs_jbxx_selAll").attr("checked",false);
				   break;
		       }
	       }
	   }
	   if(i==2){////预付费参数--费控参数 
		   $("#yffcs_fkcs_selAll").attr("checked",true);
		   for(var j = (i*100+1); j <= gl_part[i-1]; j++ ){
			   if(!$("#"+j).attr("checked")){
				   $("#yffcs_fkcs_selAll").attr("checked",false);
				   break;
		       }
   	       }
	   }
	   if(i==3){//预付费参数--控制信息  
		   $("#yffcs_kzxx_selAll").attr("checked",true);
		   for(var j = (i*100+1); j <= gl_part[i-1]; j++ ){
			   if(!$("#"+j).attr("checked")){
				   $("#yffcs_kzxx_selAll").attr("checked",false);
				   break;
		       }
	   	   }
	   }
	   if(i==4){//预付费参数--算费信息   
		   $("#yffcs_sfxx_selAll").attr("checked",true);
		   for(var j = (i*100+1); j <= gl_part[i-1]; j++ ){
			   if(!$("#"+j).attr("checked")){
				   $("#yffcs_sfxx_selAll").attr("checked",false);
				   break;
		       }
	   	   }
	   }
	   if(i==5){//预付费状态--基本状态   
		   $("#yffzt_jbzt_selAll").attr("checked",true);
		   for(var j = (i*100+1); j <= gl_part[i-1]; j++ ){
			   if(!$("#"+j).attr("checked")){
				   $("#yffzt_jbzt_selAll").attr("checked",false);
				   break;
		       }
	   	   }
	   }
	   if(i==6){//预付费状态--表底信息
		   $("#yffzt_bdxx_selAll").attr("checked",true);
		   for(var j = (i*100+1); j <= gl_part[i-1]; j++ ){
			   if(!$("#"+j).attr("checked")){
				   $("#yffzt_bdxx_selAll").attr("checked",false);
				   break;
		       }
	   	   }
	   }
	   if(i==7){//预付费状态--电费信息    
		   $("#yffzt_dfxx_selAll").attr("checked",true);
		   for(var j = (i*100+1); j <= gl_part[i-1]; j++ ){
			   if(!$("#"+j).attr("checked")){
				   $("#yffzt_dfxx_selAll").attr("checked",false);
				   break;
		       }
	   		}
	   }
	   if(i==8){//预付费状态--控制状态   
		   $("#yffzt_kzzt_selAll").attr("checked",true);
		   for(var j = (i*100+1); j <= gl_part[i-1]; j++ ){
			   if(!$("#"+j).attr("checked")){
				   $("#yffzt_kzzt_selAll").attr("checked",false);
				   break;
		       }
	   	   }
	   }
	   if(i==9){//报警及控制参数--报警及控制参数    
		   $("#alarm").attr("checked",true);
		   for(var j = (i*100+1); j <= gl_part[i-1]; j++ ){
			   if(!$("#"+j).attr("checked")){
				   $("#alarm").attr("checked",false);
				   break;
			   }
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
	
    $("#bjkzcs").click(function(){
		$("#d_yffcs").css("display", "none");
		$("#d_yffzt").css("display", "none");
		$("#d_bjkzcs").css("display","block");
		
		$("#yffcs_l").attr("class","titlel1");
		$("#yffcs").attr("class","titlem1");
		$("#yffcs_r").attr("class","titler1");
		
		$("#yffzt_l").attr("class","titlel1");
		$("#yffzt").attr("class","titlem1");
		$("#yffzt_r").attr("class","titler1");
		
		$("#bjkzcs_l").attr("class","titlel");
		$("#bjkzcs").attr("class","titlem");
		$("#bjkzcs_r").attr("class","titler");
	});	
}
