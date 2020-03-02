$(document).ready(function(){
	
	$("#confirmCode").click(function(){
		var opername = $("#userName").val();
		if($.trim(opername) === ""){
				alert("Please input user name！");
				$("#userName").focus();
				return;
		}
		if($.trim($("#subCode").val()) === ""){
				alert("Please input subscription code！");
				$("#subCode").focus();
				return;
		}
		
		$.post("ajax/actLogin!regMsg.action",{
			opername:$("#userName").val(),
			subCode:$("#subCode").val()
		},
		function(data){
			if(data.result == "success"){
				window.returnValue = "1";
				window.close();
			}else{
				alert(data.result);
			}
			
		});
	});
	
	$("#close").click(function(){
		window.close();
	});
});


