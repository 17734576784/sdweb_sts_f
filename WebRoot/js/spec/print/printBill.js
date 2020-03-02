var param = window.dialogArguments;
$(document).ready(function(){
	setValue();
	
	$("#pbtn").click(function(){
		printbill();
	});
});


function setValue(){
	$("#gddh").html(param.gddh);
	$("#yhmc").html(param.yhmc);
	$("#yhbh").html(param.yhbh);
	$("#zddj").html(param.zddj);
	$("#jfje").html(param.jfje);
	$("#jfdx").html(param.jfdx);
	$("#zje").html(param.zje);
	$("#gdsj").html(param.gdsj);
	$("#gddz").html(param.gddz);
	$("#dh").html(param.dh);
	$("#sdr").html(param.sdr);
    
	//给副联表赋值
    	
	$("#gddh1").html(param.gddh);
	$("#yhmc1").html(param.yhmc);
	$("#yhbh1").html(param.yhbh);
	$("#zddj1").html(param.zddj);
	$("#jfje1").html(param.jfje);
	$("#jfdx1").html(param.jfdx);
	$("#zje1").html(param.zje);
	$("#gdsj1").html(param.gdsj);
	$("#gddz1").html(param.gddz);
	$("#dh1").html(param.dh);
	$("#sdr1").html(param.sdr);
};
function printbill(){
	$("#pbtn").hide();
	window.print();
}
