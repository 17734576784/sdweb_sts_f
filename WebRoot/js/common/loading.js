var loading = {
	
	tip		: "loading...",
	next_focus_id : "",
	
	loading : function(){
		
		this.loaded();
		
		var div = "<div id='others_Loading' style='position: absolute; cursor: wait; border: 1px solid #A4BED4; height: 48px; width:140px; filter: Alpha(Opacity=70); overflow: hidden; background: white; text-align: center;font-size:12px;'>" +
					"<table width=100% height=100%><tr><td align=center><img src='" + def.basePath + "images/indicator.gif'> "+this.tip+"</td></tr></table>" +
					"</div>";
		$(document.body).append(div);
		var width = $(window).width();
		var height = $(window).height();
		
		$("#others_Loading").width(width);
		$("#others_Loading").height(height);
		
		document.getElementById("others_Loading").style.posLeft = 0;//width/2-70;
		document.getElementById("others_Loading").style.posTop  = 0;//height/2-25;
		
		$("#others_Loading").focus();
		
	},
	
	loaded : function(){
		var tmp = document.getElementById("others_Loading");
		if(tmp){
			$("#others_Loading").remove();
			this.tip = "loading...";
			if(this.next_focus_id != "") {
				$("#" + this.next_focus_id).focus();
			}
			this.next_focus_id = "";
		}
	},
	
	//20131201添加，适用于读取终端信息弹出页面
	loading_dialog : function(){
		
		this.loaded();
		
		var div = "<div id='others_Loading' style='position: absolute; cursor: wait; border: 0px solid #A4BED4; height: 48px; width:140px; filter: Alpha(Opacity=70); overflow: hidden; background: white; text-align: center;font-size:12px;'>" +
					"<table border = 0 width=100% height=100%><tr><td align=center><img src='" + def.basePath + "images/indicator.gif'> "+this.tip+"</td></tr></table>" +
					"</div>";
		$(document.body).append(div);
		var width = $(window).width();
		var height = $(window).height();
		
		$("#others_Loading").width(width);
		$("#others_Loading").height(height - 35);
		
		document.getElementById("others_Loading").style.posLeft = 0;//width/2-70;
		document.getElementById("others_Loading").style.posTop  = 35;//height/2-25;
		
		$("#others_Loading").focus();	
	}
}