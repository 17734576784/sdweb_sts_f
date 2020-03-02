
var	menubg_over_l = "url('images/index/menubg-over-l.gif') no-repeat";
var	menubg_over_m = "url('images/index/menubg-over-m.gif') repeat-x";
var	menubg_over_r = "url('images/index/menubg-over-r.gif') no-repeat";

var	menubg_sel_l = "url('images/index/menubg-sel-l.gif') no-repeat";
var	menubg_sel_m = "url('images/index/menubg-sel-m.gif') repeat-x";
var	menubg_sel_r = "url('images/index/menubg-sel-r.gif') no-repeat";

var	opbg_over_l = "url('images/index/opbg-over-l.gif') no-repeat";
var	opbg_over_m = "url('images/index/opbg-over-m.gif') repeat-x";
var	opbg_over_r = "url('images/index/opbg-over-r.gif') no-repeat";

var left_last_selected = -1;

$(document).ready(function() {
	var top_menu_id = cookie.get(cookie.top_menu);
	if(top_menu_id == undefined || top_menu_id === ""){
		top_menu_id = parent.top_menus[0];
	}
	$("#" + top_menu_id).click().mouseover().mouseout();

	var left_menu_id = cookie.get(top_menu_id);
	var tmp = findLeftId(left_menu_id);
	
	if(tmp < 0 || left_menu_id == undefined || left_menu_id === ""){
		left_menu_id = leftmenu.left_id[0];
	}
	
	$("#" + left_menu_id).click();
});

function findLeftId(left_id) {
	for(var i = 0; i < leftmenu.left_id.length; i++) {
		if(leftmenu.left_id[i] == left_id) return i;
	}
	return -1;
}

function mover(tr) {
	if (left_last_selected != tr.id) {
		var tds = $("#" + tr.id).children();
		
		tds[0].style.background = menubg_over_l;
		tds[1].style.background = menubg_over_m;
		tds[2].style.background = menubg_over_m;
		tds[3].style.background = menubg_over_r;
	}
}

function rt_mover(tr) {
	var tds = $("#" + tr.id).children();
		
	tds[0].style.background = opbg_over_l;
	tds[1].style.background = opbg_over_m;
	tds[2].style.background = opbg_over_m;
	tds[3].style.background = opbg_over_r;
}

function mout(tr) {
	if (left_last_selected != tr.id) {
		setNoBg(tr);
	}
}

function rt_mout(tr) {
	setNoBg(tr);
}

function setNoBg(tr) {
	$("#" + tr.id).children().each(function(){
		this.style.background = "";
	});
}

function mclick(tr) {
	if (left_last_selected != tr.id) {
		
		if (left_last_selected != -1) {
			var tmp = findLeftId(left_last_selected);
			setNoBg(document.getElementById(left_last_selected));
			$("#" + leftmenu.id[tmp]).hide();
		}
		
		left_last_selected = tr.id;
				
		var tds = $("#" + tr.id).children();
		
		tds[0].style.background = menubg_sel_l;
		tds[1].style.background = menubg_sel_m;
		tds[2].style.background = menubg_sel_m;
		tds[3].style.background = menubg_sel_r;
		
		var tmp = findLeftId(tr.id);
		$("#" + leftmenu.id[tmp]).show();
		
		//把当前选中菜单记录到cookie
		var top_menu_id = cookie.get(cookie.top_menu);
		if(top_menu_id == undefined || top_menu_id === ""){
			top_menu_id = parent.top_menus[0];
		}
		
		cookie.set(top_menu_id, tr.id);
	}
}