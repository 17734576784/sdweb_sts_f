var printParam = window.dialogArguments;
$(document).ready(function() {
 	$("#conNo").text(printParam.customerID);
	$("#meterAddr").text(printParam.meterNo);
	$("#payDate").text(printParam.payDate);
	$("#conName").text(printParam.conName);
	$("#payMoney").text(printParam.payMoney);
	$("#payDL").text(printParam.payDL+"(kwh)");
	$("#sdadmin").text(printParam.sdadmin);
	$("#token1").text(printParam.token1);
	$("#waterNo").text(printParam.wasteno);
	$("#orgAddr").text(printParam.orgAddr);
	$("#telno").text(printParam.telno);
	$("#orgDesc").text(printParam.orgDesc);
	$("#jyMoney").text(printParam.finalJyje);
	
	if(printParam.opType == "pay"){
		$("#token2").parent().remove();
		$("#token3").parent().remove();
	}
	if(printParam.opType == "open"){
		$("#token2").text(printParam.token2);
		$("#token3").text(printParam.token3);
	}
	if(printParam.opType == "close"){
		$("#token2").parent().remove();
		$("#token3").parent().remove();
	}
	
});

function printTemplate(){
	
	var bdhtml;
	var sprnstr;
	var eprnstr;
	var prnhtml;	
	bdhtml = window.document.body.innerHTML;
	sprnstr = "<!-- startprint1 -->";
	eprnstr = "<!-- endprint1 -->";
	prnhtml = bdhtml.substring(bdhtml.indexOf(sprnstr)+20);
	prnhtml = prnhtml.substring(0,prnhtml.indexOf(eprnstr));
	
	window.document.body.innerHTML = prnhtml;
	
	window.print();
	//$("#printTemplateTable").jqprint();
}