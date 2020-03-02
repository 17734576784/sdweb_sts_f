var DialogDiv={
	over_color : "#aee3e2", 
	sel_color  : "#CEEFF8",
	over_color : "url('images/btnbg.gif')",
	sel_color  : "url('images/btnbg_sel.gif')",
	
	//showDialog()

}
function (iframe_page, title){
	
	close_child();
	
	var _md = document.getElementById("main_div");
	if(!_md){
		var div = "<div id='main_div' style='position:absolute;background:white; width:100%;background:white; z-index:0;'>";
		var table = "<table cellpadding='0' cellspacing='0' width='100%' height='100%' border=0><tr class='t_top'><td></td><td id='child_title'>title</td><td align='right'><img src='images/close.gif' onclick='close_child();'/></td><td></td></tr><tr><td class='t_left_right'></td><td colspan='2'><iframe name='child_page' id='child_page' height='100%' width='100%' src='' frameborder=0 scrolling='auto'>当前浏览器不支持框架</iframe></td><td class='t_left_right'></td></tr><tr class='t_bottom'><td></td><td></td><td></td><td></td></tr></table>";
		div += table + "</div>";
		$(document.body).append(div);
		$("#main_div").css("left",0);
		$("#main_div").css("top",$("#top_nav").height());
		$("#main_div").height($(window).height() - $("#top_nav").height());
	}else{
		if(_md.style.display == "none"){
			$("#main_div").show();
		}
	}
	//$("#child_title").html("<font color=white>" + title + "</font>");
	$("#child_title").html(title);
	$("#child_page").attr("src", iframe_page);
}

function close_child(){
	var _md = document.getElementById("main_div");
	if(_md && _md.style.display != "none"){
		$("#child_page").attr("src","");
		$("#main_div").hide();
	}
}