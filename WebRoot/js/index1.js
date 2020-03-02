var menu;
var over_color = "#aee3e2", sel_color = "#CEEFF8";
var sel_top_menus_index = -1;
var glo_update_mask = 0x0;

$(document).ready(function() {
	
	over_color = "url('images/btnbg.gif')";
	sel_color = "url('images/btnbg_sel.gif')";
	
	for ( var t = 0; t < top_menus.length; t++) {
		$("#" + top_menus[t]).click(function(){
			clickTopMenu(this.id);
		}).mouseover(function() {
			overBG(this);
		}).mouseout(function() {
			outBG(this);
		});
	}
	
	$(".shortcut_tbl td").mouseover(function() {
		this.style.backgroundColor = '#CEE0E0';
	}).mouseout(function() {
		this.style.backgroundColor = '';
	});
	
	$("#glo_ggtz").click(function(){
		if(glo_update_mask != 0x0){
			$("#alarm_ggtz").css("background","url('images/bullet.gif') no-repeat right").removeAttr("title");
			dbUpdate.sendDBUpdateMsg(glo_update_mask);
			glo_update_mask = 0x0;
		}
	});
	
	$("#alarm_ggtz").click(function(){
		$("#glo_ggtz").click();
	});
	
	var top_menu_id = cookie.get(cookie.top_menu);
	if(top_menu_id == undefined || top_menu_id === ""){
		top_menu_id = top_menus[10];
	}
	$("#" + top_menu_id).click().mouseover().mouseout();
});

function clickTopMenu(id){
	close_frame();
	var tmp = -1;
	for(var i = 0; i < top_menus.length; i++){
		if(id == top_menus[i]){
			tmp = i;
			break;
		}
	}
	
	if(sel_top_menus_index == tmp){return;}
	
	if(tmp == -1){
		alert("menu id error");
		return;
	}
	for(var i = 0; i < top_menus.length; i++){
		if(i == tmp){
			$("#" + id).css("background",sel_color);
			continue;
		}
		$("#" + top_menus[i]).css("background","");
	}
	sel_top_menus_index = tmp;
	
	for(var i = 0; i < top_menus.length; i++){
		var id_ = top_menus[i] + "_";
		
		if(i == sel_top_menus_index){
			$("#" + id_).show();
		}else{
			$("#" + id_).hide();
		}
	}
	cookie.set(cookie.top_menu, id);
	
	//eval(id + "();");
}

function overBG(THIS){
	var tmp = -1;
	for(var i = 0; i < top_menus.length; i++){
		if(THIS.id == top_menus[i]){
			tmp = i;
			break;
		}
	}
	
	if(sel_top_menus_index != tmp){
		THIS.style.background = over_color;
	}
}

function outBG(THIS){
	var tmp = -1;
	for(var i = 0; i < top_menus.length; i++){
		if(THIS.id == top_menus[i]){
			tmp = i;
			break;
		}
	}
	if(sel_top_menus_index == tmp){
		THIS.style.background = sel_color;
	}else{
		THIS.style.background = '';
	}
}

function open_frame(iframe_page, title,info_flag){
	close_frame();
	if(!title){
		title = "";
	}
	
	var _md = document.getElementById("main_div");
	if(!_md){
		var div = "<div id='main_div' style='position:absolute;background:white; width:100%;background:white; z-index:0;'>";
		var table = "<table cellpadding='0' cellspacing='0' width='100%' height='100%' border=0>" +
		"<tr class='t_top' ondblclick='close_frame()'><td></td><td id='child_title'></td>" +
		"<td align='right'><img src='images/detail.gif' alt='详细信息' id='dtl_img' onclick='showDetailDialog();'/>&nbsp;&nbsp;<img src='images/close.gif' alt='关闭' onclick='close_frame();'/></td><td></td></tr><tr><td class='t_left_right'></td><td colspan='2'><iframe name='child_page' id='child_page' height='100%' width='100%' src='' frameborder=0 scrolling='no'>当前浏览器不支持框架</iframe></td><td class='t_left_right'></td></tr><tr class='t_bottom'><td></td><td></td><td></td><td></td></tr></table>";	
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
	
	$("#dtl_img").attr("style","display:" + (info_flag ? "blank" : "none"));
	
	//$("#child_title").html("<font size=10px>" + title + "</font>");
	$("#child_title").html(title);
	$("#child_page").attr("src", iframe_page);
}

function close_frame(){
	var _md = document.getElementById("main_div");
	if(_md && _md.style.display != "none"){
		$("#child_page").attr("src","");
		$("#main_div").hide();
	}
	loading.loaded();
}
function showDetailDialog(){
	showOpDetailDialog();
}
function chg_pwd(){
	modalDialog.height = 160;
	modalDialog.width = 300;
	modalDialog.url = "jsp/dialog/chgpwd.jsp";
	modalDialog.show();
}
function logout(){
	
	if(!confirm("确定要注销吗?"))return;
	
	window.location.href='logout.jsp';
}
function global_ggtz(){
	
	if(glo_update_mask > 0x0){
		//$("#alarm_ggtz").css("background","url('images/alarm.gif') no-repeat right").attr("title","更改通知");
	}
}