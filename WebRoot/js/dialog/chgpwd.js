
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
		alert("Les mots de passe ne peuvent pas être vides！");
		return;
	}

	if($("#new_pwd").val() != $("#new_pwd_q").val()){
		alert("Confirmez que le mot de passe est incompatible avec le nouveau mot de passe, veuillez ressaisir!");
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
			alert("Ancien mot de passe est incorrect!");
		}
	});
}