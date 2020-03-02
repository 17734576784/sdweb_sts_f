var modalDialog = {
	width : 0,
	height : 0,
	url : "",
	param : "",
	
	show : function(){
		var ua = navigator.userAgent.toLowerCase();
		var ieVersion = "";
		if(ua.match(/msie ([\d.]+)/)){
			ieVersion = ua.match(/msie ([\d.]+)/)[1];
		}
		if(ieVersion=="6.0"){
			this.height += 47;
			this.width += 8;
		}else if(ieVersion=="7.0"){
			
		}else if(ieVersion=="8.0"){
			
		}
		return window.showModalDialog(this.url,this.param,"dialogHeight:"+this.height+"px;dialogWidth:"+this.width+"px;help:no;status:no;");
	}
}