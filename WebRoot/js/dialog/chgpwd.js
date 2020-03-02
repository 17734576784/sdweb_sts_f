
$(document).ready(function(){
	
	$("#xiugai").click(function(){
		chg();
	});
	
	$("#close").click(function(){
		window.close();
	});
});

function chg(){
	if($.trim($("#old_pwd").val()) == "" || $.trim($("#new_pwd").val()) == "" || $.trim($("#new_pwd_q").val()) == ""){
		alert("Passwords can not be empty！");
		return;
	}

	if($("#new_pwd").val() != $("#new_pwd_q").val()){
		alert("Confirm password is inconsistent with the new password, please re-enter！");
		$("#new_pwd_q").focus().select();
		return;
	}
	
	$.post("../../ajax/actLogin!chgPsw.action",{
		result	: $("#old_pwd").val(), 
		pwd		: $("#new_pwd").val()
	},
	function(data){
		if(data.result == "success"){
			window.close();
		}else if(data.result == "fail"){
			alert("Old password is incorrect！");
		}
	});
}