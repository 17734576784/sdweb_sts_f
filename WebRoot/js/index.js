var menu;
var over_color = "#aee3e2", sel_color = "#CEEFF8";
var sel_top_menus_index = -1;
var sel_left_menus_id = -1;
var glo_update_mask_byte = new Array(32);
for (var i = 0; i < glo_update_mask_byte.length; i ++) {
	glo_update_mask_byte[i] = 0;
}

$(document).ready(function() {
	
	over_color = "url('images/btnbg.gif')";
	sel_color = "url('images/btnbg_sel.gif')";
	leftmenu_over_color = "url('images/index/leftmenu.gif')";
	leftmenu_sel_color = "url('images/index/leftmenu_sel.gif')";
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

		var glo_update_mask_str = "";
		
		for (var i = 0; i < glo_update_mask_byte.length; i++) {
			glo_update_mask_str += ",";
			glo_update_mask_str += glo_update_mask_byte[i];
		}
		glo_update_mask_str = glo_update_mask_str.substring(1);
		
//		$("#alarm_ggtz").css("background","url('images/bullet.gif') no-repeat right").removeAttr("title");
		dbUpdate.sendDBUpdateMsg(glo_update_mask_str);
		
		for (var i = 0; i < glo_update_mask_byte.length; i++) {
			glo_update_mask_byte[i] = 0;
		}
	});
	
//	$("#alarm_ggtz").click(function(){
//		$("#glo_ggtz").click();
//	});
	
	var top_menu_id = cookie.get(cookie.top_menu);
	if(top_menu_id == undefined || top_menu_id === ""){
		top_menu_id = top_menus[0];
	}
	var tmp = findTopId(top_menu_id);
	if(tmp < 0) {
		top_menu_id = top_menus[0];
	}
	
	$("#" + top_menu_id).click().mouseover().mouseout();
	
	if(_login_single == 1) {
		window.setInterval("checkUser()", 1000 * 10);
	}
	
});

function findTopId(top_id) {
	for(var i = 0; i < top_menus.length; i++) {
		if(top_id == top_menus[i]) {
			return i;
		}
	}
	return -1;
}

function checkUser() {
	$.post("ajax/actLogin!checkUser.action", {}, function(data){
		if(data.result == undefined) {
			window.location.href = "login.jsp";
		}
		else if(data.result != "success") {
			alert(data.result);
			logout(true);
			//window.location.href = "login.jsp";
		}
		else {
			$("#operman").attr("title", data.opername);
			$("#operman").html(data.operdesc);
			
			//alert (data.operdesc);
			
		}
	});
}

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
	var tmpid = id.split("_")[1];
	open_frame2('jsp/menu/menu-' + tmpid + '.jsp',menuguide[tmp]);
	
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
function open_frame2(iframe_page, title,info_flag){
	close_frame();
	if(!title){
		title = "";
	}
	var _md = document.getElementById("main_div2");
	if(!_md){
		var div = "<div id='main_div2' style='position:absolute;width:100%;background:white; z-index:0;'>";
		var table = "<table cellpadding='0' cellspacing='0' width='100%' height='100%' border=0>" +
		"<tr class='t_top'><td></td><td id='child_title2'></td>" +
		"<td align='right'></td><td></td></tr><tr><td class='t_left_right'></td><td colspan='2'><iframe name='child_page2' id='child_page2' height='100%' width='100%' src='' frameborder=0 scrolling='no'>当前浏览器不支持框架</iframe></td><td class='t_left_right'></td></tr><tr class='t_bottom'><td></td><td></td><td></td><td></td></tr></table>";	
		div += table + "</div>";
		$(document.body).append(div);
		$("#main_div2").css("left",0);
		$("#main_div2").css("top",$("#top_nav").height());
		$("#main_div2").height($(window).height() - $("#top_nav").height());
	}else{
		if(_md.style.display == "none"){
			$("#main_div2").show();
		}
	}
	
	$("#child_title2").html(title);
	$("#child_page2").attr("src", iframe_page);
}
function open_frame(iframe_page, title,info_flag){
	close_frame();
	if(!title){
		title = "";
	}
	
	var _md = document.getElementById("main_div");
	
	//20131130添加取消操作图标id='cancel_img'
	if(!_md){
		var div = "<div id='main_div' style='position:absolute;background:white; width:100%;background:white; z-index:0;'>";
		var table = "<table cellpadding='0' cellspacing='0' width='100%' height='100%' border=0>" +
		"<tr class='t_top' ondblclick='/*close_frame()*/'><td></td><td id='child_title'></td>" +
		"<td align='right'><img src='images/menu/imgs/cancel_task.gif' alt='取消操作' id='cancel_img' onclick='taskCancel();'/>&nbsp;&nbsp;&nbsp;&nbsp;<img src='images/detail.gif' alt='Detail Information' id='dtl_img' onclick='showDetailDialog();'/>&nbsp;&nbsp;&nbsp;&nbsp;<img src='images/close.gif' alt='Close' onclick='close_frame();'/></td><td></td></tr><tr><td class='t_left_right'></td><td colspan='2'><iframe name='child_page' id='child_page' height='100%' width='100%' src='' frameborder=0 scrolling='no'>当前浏览器不支持框架</iframe></td><td class='t_left_right'></td></tr><tr class='t_bottom'><td></td><td></td><td></td><td></td></tr></table>";	
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
	
	//20131130添加了取消操作的控制
	$("#cancel_img").attr("style","display:" + (info_flag ? "blank" : "none"));
	
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
	modalDialog.width = 330;
	modalDialog.url = "jsp/dialog/chgpwd.jsp";
	modalDialog.show();
}

function logout(flag){
	if(!flag && !confirm("Are you sure to cancel account?"))return;
	
	$.post("ajax/actLogin!logout.action", {result : flag}, function(data){
		if(data.result == "success") {
			window.location.href='login.jsp';
		}
	});
}

function global_ggtz(update_mask){
	var arrayidx = Math.floor(update_mask / 8);
	var maskidx  = update_mask % 8;
	
	if ((glo_update_mask_byte[arrayidx] & Math.pow(2, maskidx)) != Math.pow(2, maskidx))	{
		glo_update_mask_byte[arrayidx] += Math.pow(2, maskidx);
	}
	
//	$("#alarm_ggtz").css("background","url('images/alarm.gif') no-repeat right").attr("title","更改通知");
}

function clickLeftMenu(id){
	var tmp = id.substring(id.length-1,id.length);
	for(var i = 0; i < left_menu_num; i++){
		if(i == tmp){
			$("#" + id).css("background",leftmenu_sel_color);
			continue;
		}
		$("#leftmenu" + i).css("background","");
	}
	sel_left_menus_id = id;
	
	for(var i = 0; i < left_menu_num; i++){
		var id_ = top_menus[i] + "_";
		
		if(i == sel_top_menus_index){
			$("#" + id_).show();
		}else{
			$("#" + id_).hide();
		}
	}
	cookie.set(cookie.top_menu, id);
}

//取消任务操作
function taskCancel(){
	taskCancelObj.cancelDetailTask();
}

