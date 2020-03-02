//档案权限：0 无；1 读；2 读写
var doc_per = window.top._doc_per;

$(document).ready(function(){
	if(doc_per == undefined){
		doc_per = window.dialogArguments.doc_per;
	}
	switch(doc_per){
	case 0:
		$("body").hide();
	break;
	case 1:
		
	break;
	case 2:
		if(typeof rese2_flag != "undefined"){
			if(rese2_flag == 0)return;
		}
		$("#tianjia").show();
		$("#xiugai").show();
		$("#guanbi").show();
		$("#pldel").show();
	break;
	}
});