var printParam = window.dialogArguments;
$(document).ready(function() {
	
	$("#conNo").text(printParam.customerID);
	$("#meterAddr").text(printParam.meterNo);
	$("#conName").text(printParam.conName);
	//$("#sdadmin").text(printParam.sdadmin);
	$("#token1").text(printParam.token1);
	
	
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
