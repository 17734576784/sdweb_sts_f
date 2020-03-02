var optype = window.dialogArguments;//页面应用类型//1：低压卡式，2低压远程，3低压主站，4高压卡式-智能卡,5高压卡式-6103,6高压远程金额，7高压远程表底，8高压主站
//var param = params.id.split(",");//mpid,rtuid
var nodeName = ["","dy-card", "dy-addcus","tool","dy-remote","dy-main","gy-card1","gy-card3","gy-ycmoney","gy-ycbd","gy-main","np-card","dy-carddl"];
var cookieName = ["","dycard", "dyaddcus","tool","dyremote","dymain","gycard1","gycard3","gyycmoney","gyycbd","gymain","npcard","dycarddl"];//配置文件中，各种应用类型对应的top节点名
	
var selfilename = null;
//var optype = 3;

$(document).ready(function(){
	
	var optype_s = 0, optype_e = cookieName.length - 1 ;

	if( optype <= optype_s || optype > optype_e) return;	
	
	mygrid.setImagePath(def.basePath + "images/grid/imgs/");
	mygrid.setHeader("Name,,Description");
   	mygrid.setInitWidths("200,0,*");
   	mygrid.setColAlign("left,left,left");
   	mygrid.setColTypes("ro,ro");
	mygrid.setColumnIds("text,filename,desc");
	mygrid.attachEvent("onRowSelect", doOnRowSelected);
	mygrid.attachEvent("onRowDblClicked",doOnRowDblClicked);  
	
	mygrid.xml.top=nodeName[optype];
	//mygrid.xml.row="./item";
	mygrid.xml.row="./item[@useflag=1]";
	mygrid.init();
	mygrid.setSkin("light");
	mygrid.load(def.basePath + "print-loc/print-config.xml","xmlA");
	
	$("#btnOK").click(function(){
		var rtnParams ={
			filename : selfilename ,
			saveflag : $("#chksetcookie").attr("checked")
		}
		window.returnValue = rtnParams;
		window.close();
	});
});

function doOnRowDblClicked(rId){
	
	selfilename = mygrid.cells(rId,1).getValue();		
	var rtnParams ={
		filename : selfilename ,
		saveflag : $("#chksetcookie").attr("checked")
	}
	
	window.returnValue = rtnParams;
	window.close();	
}
 
function doOnRowSelected(rowID){
    selfilename = mygrid.cells(rowID,1).getValue();	
}

