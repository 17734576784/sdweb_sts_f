var tree;
var tree_id = "";
var tree_loaded = false;
var appType = -1;
$(document).ready(function(){
	$("#tree").height($(window).height() - 25);
	$("#manage_tb2").hide();
	
	initTree();
	createTree();
	
});

function initTree(){
	tree = new dhtmlXTreeObject("tree", "100%", "100%", 0);
	tree.setImagePath(def.basePath + "images/tree/imgs/");
	tree.attachEvent("onSelect",function(id){getDataByidFromTree(1,id)});//isFirst->1,是否初次加载页面
	tree.enableSmartXMLParsing(true);
}

function createTree(flag){
	$.post(def.basePath + "ajax/actTree.action", {refresh : flag ? 1 : 0, appType : appType},function(data){
		tree.loadXMLString(data.result, function(){tree_loaded = true;});
	});
	
}

function refreshTree(){
	
	tree_id = "";
	tree_loaded = false;
	$("#tree").html("");
	tree = null;
	
	initTree();
	createTree(true);
}

function getDataByidFromTree(isFirst,id, rows){
	
	if(!tree_loaded){
		window.setTimeout("getDataByidFromTree(" + id + ", " + rows + ");", 100);
		return;
	}
	
	if(!id || id === ""){
		if(tree_id === ""){
			if(window.top._rank != 0){
				var tmp = tree.getSubItems(SDDef.GLOBAL_KE2);
				if(tmp == "")return;
				tmp = tmp.split(",")[0];
				tree.selectItem(tmp);
			}else{
				tree.selectItem(SDDef.GLOBAL_KE2);
			}
			
			return;
		}
	}else{
		if(SDDef.GLOBAL_KE2 == id && window.top._rank != 0){
			var tmp = tree.getSubItems(SDDef.GLOBAL_KE2);
			if(tmp == "")return;
			tmp = tmp.split(",")[0];
			tree.selectItem(tmp);
			return;
		}else{
			tree_id = id;
		}
	}

	gridopt.gridSearch = "{\"describe\" : \"" + $("#search_condition").val() + "\", \"id\" : \"" + tree_id + "\"}";
	
	if(!rows){
		rows = 20;
	}
	
	//点击查询按钮加载
	if(!isFirst){
		gridopt.filter(rows);
	}
	//如果是第一次加载页面或点击树节点加载
	else{
		//低压费控档案
		if(autoshow_type == "dy_doc"){
			if(tree_id.indexOf(node_type.GLOBAL_KE)					>-1 	&& dy_autoshow[1] == 1)	gridopt.filter(rows);
			if(tree_id.indexOf(node_type.TABLECLASS_ORGPARA)		>-1 	&& dy_autoshow[2] == 1)	gridopt.filter(rows);
			if(tree_id.indexOf(node_type.TABLECLASS_LINEFZMAN)		>-1 	&& dy_autoshow[3] == 1)	gridopt.filter(rows);
			if(tree_id.indexOf(node_type.TABLECLASS_CONSPARA)		>-1 	&& dy_autoshow[4] == 1)	gridopt.filter(rows);
			if(tree_id.indexOf(node_type.TABLECLASS_RTUPARA)		>-1 	&& dy_autoshow[5] == 1)	gridopt.filter(rows);
		}
		//专变费控档案
		else if(autoshow_type == "spec_doc"){
			if(tree_id.indexOf(node_type.GLOBAL_KE)					>-1 	&& spec_autoshow[1] == 1)	gridopt.filter(rows);
			if(tree_id.indexOf(node_type.TABLECLASS_ORGPARA)		>-1 	&& spec_autoshow[2] == 1)	gridopt.filter(rows);
			if(tree_id.indexOf(node_type.TABLECLASS_LINEFZMAN)		>-1 	&& spec_autoshow[3] == 1)	gridopt.filter(rows);
			if(tree_id.indexOf(node_type.TABLECLASS_CONSPARA)		>-1 	&& spec_autoshow[4] == 1)	gridopt.filter(rows);
			if(tree_id.indexOf(node_type.TABLECLASS_RTUPARA)		>-1 	&& spec_autoshow[5] == 1)	gridopt.filter(rows);
		}
		//农排费控档案
		else if(autoshow_type == "np_doc"){
			if(tree_id.indexOf(node_type.GLOBAL_KE)					>-1 	&& np_autoshow[1] == 1)	gridopt.filter(rows);
			if(tree_id.indexOf(node_type.TABLECLASS_ORGPARA)		>-1 	&& np_autoshow[2] == 1)	gridopt.filter(rows);
			if(tree_id.indexOf(node_type.TABLECLASS_LINEFZMAN)		>-1 	&& np_autoshow[3] == 1)	gridopt.filter(rows);
			if(tree_id.indexOf(node_type.TABLECLASS_CONSPARA)		>-1 	&& np_autoshow[4] == 1)	gridopt.filter(rows);
			if(tree_id.indexOf(node_type.TABLECLASS_RTUPARA)		>-1  	&& np_autoshow[5] == 1)	gridopt.filter(rows);
		}
	}
}