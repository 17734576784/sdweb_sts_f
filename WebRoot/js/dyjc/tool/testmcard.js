var rtnValue    = null;
//var qkkclick = false;
var other_type			= 0;
var feilv_type			= 0;

$(document).ready(function(){
	$("#writecard").click(function(){maketools();});
	$("#cardinfo").click(function(){window.top.card_comm.cardinfoBengal()});
});

function maketools(){
	var test = $("#testtype").val();
	$.post(def.basePath + "ajaxdyjc/actMakeTools!typeCard.action",{result: test},function(data){
		if(data.result != ""){
		//	alert("dfdf");
			var retStr = data.result;
				var errState = data.errState;				
				//如果返回有错误，则显示错误内容
				if(errState == 1){
					loading.loaded();
				}
				else{
					//接收数据返回写卡字符串
					var ret_json = window.top.card_comm.writeCardBengal(retStr);	//

					//写卡成功
					if(ret_json.errno  == 0){
						alert("Writing card success！\n");
					}
					else{
						loading.loaded();
						alert("Writing card failure！\n" + ret_json.errsr);
					}
				}
		}
	});
}