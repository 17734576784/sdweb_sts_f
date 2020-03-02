var cookie = {
	login_cookie: "sd_keyhm",
	top_menu 	: "sd_top_menu",
	gsm_flag 	: "sd_gsm_flag",
	ZBlookitem	    : "sd_hide_unhide",
	ZBlookitem_tmp	: "sd_hide_unhide_tmp",
	
	
	set : function(cookie_name,value,time){
		if (value == "")return;
		var Then = new Date();
		
		if(time == undefined){
			time = Then.getTime() + 15 * 24 * 3600000;
		}else{
			time = Then.getTime() + time;
		}
		
		Then.setTime(Then.getTime() + time);
		document.cookie = cookie_name + "=" + value + ";expires="+ Then.toGMTString();
	},
	get : function(cookie_name){
		var cookieString = new String(document.cookie);
		var cookieHeader = cookie_name + "=";
		var beginPosition = cookieString.indexOf(cookieHeader);
		if (beginPosition != -1){
			cookieString = cookieString.substring(beginPosition + cookieHeader.length);
			return cookieString.split(";")[0];
		}else{
			return "";
		}
	},
	del : function(cookie_name){
		document.cookie = cookie_name + "=;expires=Fri, 02-Jan-1970 00:00:00 GMT";
	}
};