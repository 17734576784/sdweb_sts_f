var WEBPRINTDATA = {	//打印参数结构： 传入参数，里面各个变量名不能修改。
   	GdDh 		: "0",	//购电单号
	UserCode	: "0",	//用户编号
	UserName	: "0",	//用户描述
	MeterNo 	: "0",	//表号
	JFJE		: "0",	//缴费金额
 	JSJE		: "0",	//结算金额
 	ZBJE		: "0",	//追补金额
 	GMDL		: "0",	//本次购买电量
 	SCDD_BD		: "0",	//上次断电表底
 	BCDD_BD		: "0",	//本次断电表底
 	SCSY_JE		: "0",	//上次剩余金额
 	BCDD_JE		: "0",	//断电总金额
 	SK_DX		: "0",	//收款缴费金额(大写)
 	DJ			: "0",	//电价	//复费率 阶梯等改为描述形式
 	BL			: "1",	//倍率
 	DJLX		: "0",	//电价类型
 	GDS			: "0",	//供电所
 	BDZXL		: "0",	//变电站线路
 	GDDZ		: "0",	//购电地址
 	FWDH		: "0",	//服务电话
 	CurTm		: "0",	//缴费日期
 	GDR			: "0",	//购电人
 	SDR			: "0",	//售电人
 	SQR			: "0",	//授权人
 	SGNUM		: "0",	//SG186流水号
 	SGCZNUM		: "0",	//SG被冲正流水号
 	Remark		: "",	//备注
 	OPERTYPE	: "0",	//操作类型 开户 缴费 等
 	REPRINT		: "0",	//补打标志
 	TRANCAP		: "0",	//变压器容量
 	
 	regetWEBPRINTDATA_flag	:	false	//需要给WEBPRINTDATA的某些项重新赋值时，本值设为true，在自己的js里写regetWEBPRINTDATA()函数。
};
var webp = ""; 
var filepara ={//模板文件 loc  。传入参数，里面各个变量名不能修改。 
	file_name 	: "",
	file_len 	: "",
	file_data	: "" 
};
var fileName = {//模板文件名.各个打印页面调用时使用。变量名不能修改。值可以改。可以增加变量。
	dyje 	: "yffmodel_bd_s.loc", //"yffmodel_dyje.loc",
	gybd 	: "yffmodel_gybd.loc",
	gyje 	: "yffmodel_gyje.loc"
	
}
	

var loadflag = {//0：正在加载；1：读取成功；2：读取失败
	docs : 0,
	file : 0
};

var IDs = {//
	rtu_id	: -1,
	mp_id	: -1,
	wasteno	: ""
}

function doPrint1(){
//	getTemplateBytes();
//	return;
	modalDialog.width =1000;
	modalDialog.height = 400;
	modalDialog.url = def.basePath + "jsp/dyjc/dialog/printBill.jsp";
	//得到选中的id
	var mygrid=gridopt.grid;
	var selectedId=mygrid.getSelectedRowId();
	if(null==selectedId){
		alert("请您选择要打印的内容");
		return;
	}
	//购电单号=流水号
	var gddh=mygrid.cells(selectedId, 1).getValue();
	//用户名称
	var yhmc=mygrid.cells(selectedId, 4).getValue();
	var yhbh=mygrid.cells(selectedId, 3).getValue();
	var zddj=mygrid.cells(selectedId,18).getValue();// 暂定电价
	var jfje=mygrid.cells(selectedId,10).getValue();
	var jfdx=MoneyNum2Char(jfje,'2');
	var zje= mygrid.cells(selectedId, 13).getValue();
	//var zje="0.0";
	var gdsj=mygrid.cells(selectedId, 7).getValue();
	var gddz=mygrid.cells(selectedId, 5).getValue();
	var dh="";//用户电话
	var sdr=mygrid.cells(selectedId, 8).getValue();	
    modalDialog.param ={"gddh":gddh,"yhmc":yhmc,"yhbh":yhbh,"zddj":zddj,"jfje":jfje,"jfdx":jfdx,"zje":zje,"gdsj":gdsj,"gddz":gddz,"dh":dh,"sdr":sdr};
	modalDialog.show();
}
function doPrint(){
	getTemplateBytes();
	getWEBPRINTDATA();
	show();

}

function getTemplateBytes(){//取得模板文件的内容
 	if(filepara.name == ""){
		alert("没有指定模板文件！");
		return;
	}
	
	loadflag.file = 0;
	$.post(def.basePath + "ajax/actPrint!getBytesbyTmpNm.action",{result:filepara.file_name},function(data){
		if(data.result = "success" && data.bytesize>0){
			filepara.file_len =   data.bytesize ;
			filepara.file_data = data.field;
			loadflag.file=1;
		}else{
			loadflag.file=2;
		}		
	});
	
}

function getWEBPRINTDATA(){//取得档案参数
	loadflag.docs = 0;
	$.post(def.basePath + "ajax/actPrint!getPrintParabyRtu.action",{result:jsonString.json2String(IDs)},function(data){
		if(data.result = "success"){
			webp = data.field;
			var jsondt = eval("(" + data.field + ")"); 
			//**************以下测试*********************
			WEBPRINTDATA.GdDh 		=  IDs.wasteno;	//购电单号
			WEBPRINTDATA.UserCode	=  jsondt.UserCode;	//用户编号
			WEBPRINTDATA.UserName	=  jsondt.UserName;	//用户描述
			WEBPRINTDATA.MeterNo 	=  jsondt.MeterNo;	//表号
			WEBPRINTDATA.JFJE		=  100;	//缴费金额
		 	WEBPRINTDATA.JSJE		=  0;	//结算金额
		 	WEBPRINTDATA.ZBJE		=  1;	//追补金额
		 	WEBPRINTDATA.GMDL		=  jsondt.GMDL;	//本次购买电量
		 	WEBPRINTDATA.SCDD_BD	=  jsondt.SCDD_BD;	//上次断电表底
		 	WEBPRINTDATA.BCDD_BD	=  jsondt.BCDD_BD;	//本次断电表底
		 	WEBPRINTDATA.SCSY_JE	=  jsondt.SCSY_JE;	//上次剩余金额
		 	WEBPRINTDATA.BCDD_JE	=  jsondt.BCDD_JE;	//断电总金额
		 	WEBPRINTDATA.SK_DX		=  jsondt.SK_DX;	//收款缴费金额(大写)
		 	WEBPRINTDATA.DJ			=  jsondt.DJ;	//电价	//复费率 阶梯等改为描述形式
		 	WEBPRINTDATA.BL			=  jsondt.BD;	//倍率
		 	WEBPRINTDATA.DJLX		=  jsondt.DJLX;	//电价类型
		 	WEBPRINTDATA.GDS		=  jsondt.GDS;	//供电所
		 	WEBPRINTDATA.BDZXL		=  jsondt.BDZXL;	//变电站线路
		 	WEBPRINTDATA.GDDZ		=  jsondt.GDDZ;	//购电地址
		 	WEBPRINTDATA.FWDH		=  jsondt.FWDH;	//服务电话
		 	WEBPRINTDATA.CurTm		=  jsondt.CurTm;	//缴费日期
		 	WEBPRINTDATA.GDR		=  jsondt.GDR;	//购电人
		 	WEBPRINTDATA.SDR		=  jsondt.SDR;	//售电人
		 	WEBPRINTDATA.SQR		=  jsondt.SQR;	//授权人
		 	WEBPRINTDATA.SGNUM		=  jsondt.SGNUM;	//SG186流水号
		 	WEBPRINTDATA.SGCZNUM	=  jsondt.SGCZNUM;	//SG被冲正流水号
		 	WEBPRINTDATA.Remark		=  jsondt.Remark;	//备注
		 	WEBPRINTDATA.OPERTYPE	=  jsondt.OPERTYPE;	//操作类型 开户 缴费 等
		 	WEBPRINTDATA.REPRINT	=  jsondt.REPRINT;	//补打标志
		 	WEBPRINTDATA.TRANCAP	=  jsondt.TRANCAP;	//变压器容量
			///////*****************************以上测试***********************
//			WEBPRINTDATA.GdDh 		=  IDs.wasteno;	//购电单号
//			WEBPRINTDATA.UserCode	=  jsondt.UserCode;	//用户编号
//			WEBPRINTDATA.UserName	=  jsondt.UserName;	//用户描述
//			WEBPRINTDATA.MeterNo 	=  jsondt.MeterNo;	//表号
//			WEBPRINTDATA.JFJE		=  jsondt.JFJE;	//缴费金额
//		 	WEBPRINTDATA.JSJE		=  jsondt.JSJE;	//结算金额
//		 	WEBPRINTDATA.ZBJE		=  jsondt.ZBJE;	//追补金额
//		 	WEBPRINTDATA.GMDL		=  jsondt.GMDL;	//本次购买电量
//		 	WEBPRINTDATA.SCDD_BD	=  jsondt.SCDD_BD;	//上次断电表底
//		 	WEBPRINTDATA.BCDD_BD	=  jsondt.BCDD_BD;	//本次断电表底
//		 	WEBPRINTDATA.SCSY_JE	=  jsondt.SCSY_JE;	//上次剩余金额
//		 	WEBPRINTDATA.BCDD_JE	=  jsondt.BCDD_JE;	//断电总金额
//		 	WEBPRINTDATA.SK_DX		=  jsondt.SK_DX;	//收款缴费金额(大写)
//		 	WEBPRINTDATA.DJ			=  jsondt.DJ;	//电价	//复费率 阶梯等改为描述形式
//		 	WEBPRINTDATA.BL			=  jsondt.BD;	//倍率
//		 	WEBPRINTDATA.DJLX		=  jsondt.DJLX;	//电价类型
//		 	WEBPRINTDATA.GDS		=  jsondt.GDS;	//供电所
//		 	WEBPRINTDATA.BDZXL		=  jsondt.BDZXL;	//变电站线路
//		 	WEBPRINTDATA.GDDZ		=  jsondt.GDDZ;	//购电地址
//		 	WEBPRINTDATA.FWDH		=  jsondt.FWDH;	//服务电话
//		 	WEBPRINTDATA.CurTm		=  jsondt.CurTm;	//缴费日期
//		 	WEBPRINTDATA.GDR		=  jsondt.GDR;	//购电人
//		 	WEBPRINTDATA.SDR		=  jsondt.SDR;	//售电人
//		 	WEBPRINTDATA.SQR		=  jsondt.SQR;	//授权人
//		 	WEBPRINTDATA.SGNUM		=  jsondt.SGNUM;	//SG186流水号
//		 	WEBPRINTDATA.SGCZNUM	=  jsondt.SGCZNUM;	//SG被冲正流水号
//		 	WEBPRINTDATA.Remark		=  jsondt.Remark;	//备注
//		 	WEBPRINTDATA.OPERTYPE	=  jsondt.OPERTYPE;	//操作类型 开户 缴费 等
//		 	WEBPRINTDATA.REPRINT	=  jsondt.REPRINT;	//补打标志
//		 	WEBPRINTDATA.TRANCAP	=  jsondt.TRANCAP;	//变压器容量

			loadflag.docs = 1;
		 	//			if(regetWEBPRINTDATA_flag){//
//					regetWEBPRINTDATA()
//			}else{
//				loadflag.docs = 1;
//			}		 	
		}else{
			loadflag.docs = 2;
		}
		
	});	
}

function  show(){
	if(loadflag.file == 2){
		alert("读取模板文件" + filepara.file_name + "失败！");
		return;
	}else if(loadflag.docs == 2){
		alert("读取用户档案失败！");
		return;
	}else if(loadflag.file == 0 || loadflag.docs == 0){
		window.setTimeout("show()",500);
		return;
	}
	
	var json_instr_printdata = webp;
	var json_instr_file = jsonString.json2String(filepara);//"{\"file_name\":\"yffmodel_gyje.loc\", \"file_len\":\"8112\", \"file_data\":\"75,76,68,45,83,50,48,48,50,0,86,101,114,50,46,49,51,0,-51,-51,0,0,0,0,0,4,0,0,0,3,0,0,68,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,50,0,0,0,50,0,0,0,-10,3,0,0,68,1,0,0,50,0,0,0,68,1,0,0,-10,3,0,0,68,1,0,0,50,0,0,0,68,1,0,0,-10,3,0,0,68,1,0,0,50,0,0,0,50,0,0,0,-106,2,0,0,68,1,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,-101,0,0,0,8,1,0,0,51,0,0,0,75,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,16,0,0,0,0,0,0,0,2,0,0,0,-44,-92,-72,-74,-73,-47,-67,-55,-73,-47,-75,-91,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,-62,1,0,0,95,2,0,0,54,0,0,0,73,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,16,0,0,0,0,0,0,0,2,0,0,0,-44,-92,-72,-74,-73,-47,-67,-55,-73,-47,-75,-91,40,-72,-79,-63,-86,41,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,66,0,0,0,66,0,0,0,76,0,0,0,43,1,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,66,0,0,0,79,1,0,0,76,0,0,0,76,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,79,1,0,0,79,1,0,0,76,0,0,0,43,1,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,66,0,0,0,79,1,0,0,42,1,0,0,42,1,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,66,0,0,0,79,1,0,0,98,0,0,0,98,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,67,0,0,0,79,1,0,0,120,0,0,0,120,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,66,0,0,0,79,1,0,0,-113,0,0,0,-113,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,66,0,0,0,79,1,0,0,-91,0,0,0,-91,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,66,0,0,0,79,1,0,0,-69,0,0,0,-69,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,66,0,0,0,79,1,0,0,21,1,0,0,21,1,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,-94,0,0,0,-94,0,0,0,77,0,0,0,44,1,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,70,0,0,0,-112,0,0,0,80,0,0,0,97,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,0,0,0,0,-45,-61,-69,-89,-61,-5,-77,-58,58,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,3,-51,-51,-51,1,0,0,0,0,0,0,0,-91,0,0,0,77,1,0,0,82,0,0,0,95,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,0,0,0,0,0,0,0,0,-45,-61,-69,-89,-61,-5,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,70,0,0,0,-122,0,0,0,101,0,0,0,117,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,0,0,0,0,-67,-55,-73,-47,-67,-16,-74,-18,58,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,69,0,0,0,-105,0,0,0,126,0,0,0,-116,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,0,0,0,0,-79,-66,-76,-50,-71,-70,-62,-14,-75,-25,-63,-65,58,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,69,0,0,0,-105,0,0,0,-109,0,0,0,-94,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,0,0,0,0,-55,-49,-76,-50,-74,-49,-75,-25,-79,-19,-75,-41,58,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,70,0,0,0,-102,0,0,0,-85,0,0,0,-72,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,0,0,0,0,-79,-66,-76,-50,-74,-49,-75,-25,-79,-19,-75,-41,58,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,69,0,0,0,-108,0,0,0,1,1,0,0,17,1,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,0,0,0,0,-67,-55,-73,-47,-56,-53,58,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,69,0,0,0,-121,0,0,0,26,1,0,0,39,1,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,0,0,0,0,-67,-55,-73,-47,-56,-43,-58,-38,58,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,3,-51,-51,-51,1,0,0,0,0,0,0,0,-90,0,0,0,78,1,0,0,124,0,0,0,-115,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,4,0,0,0,1,0,0,0,-79,-66,-76,-50,-71,-70,-62,-14,-75,-25,-63,-65,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,3,-51,-51,-51,1,0,0,0,0,0,0,0,-91,0,0,0,78,1,0,0,-109,0,0,0,-94,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,5,0,0,0,1,0,0,0,-55,-49,-76,-50,-74,-49,-75,-25,-79,-19,-75,-41,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,3,-51,-51,-51,1,0,0,0,0,0,0,0,-91,0,0,0,77,1,0,0,-87,0,0,0,-72,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,6,0,0,0,1,0,0,0,-79,-66,-76,-50,-74,-49,-75,-25,-79,-19,-75,-41,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,3,-51,-51,-51,1,0,0,0,0,0,0,0,-92,0,0,0,77,1,0,0,26,1,0,0,41,1,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,17,0,0,0,0,0,0,0,-67,-55,-73,-47,-56,-43,-58,-38,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,70,0,0,0,-117,0,0,0,-20,0,0,0,-5,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,0,0,0,0,-79,-28,-75,-25,-53,-7,58,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,-120,1,0,0,-106,2,0,0,76,0,0,0,76,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,-119,1,0,0,-119,1,0,0,75,0,0,0,42,1,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,-119,1,0,0,-106,2,0,0,98,0,0,0,98,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,-119,1,0,0,-106,2,0,0,120,0,0,0,120,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,-118,1,0,0,-106,2,0,0,-113,0,0,0,-113,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,-119,1,0,0,-106,2,0,0,-91,0,0,0,-91,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,-119,1,0,0,-106,2,0,0,-69,0,0,0,-69,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,-119,1,0,0,-106,2,0,0,-47,0,0,0,-47,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,-118,1,0,0,-106,2,0,0,-25,0,0,0,-25,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,-106,2,0,0,-106,2,0,0,76,0,0,0,42,1,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,-21,1,0,0,-21,1,0,0,77,0,0,0,44,1,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,-115,1,0,0,-36,1,0,0,82,0,0,0,95,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,0,0,0,0,-45,-61,-69,-89,-61,-5,-77,-58,58,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,-115,1,0,0,-34,1,0,0,102,0,0,0,117,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,0,0,0,0,-67,-55,-73,-47,-67,-16,-74,-18,58,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,-115,1,0,0,-28,1,0,0,125,0,0,0,-115,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,0,0,0,0,-79,-66,-76,-50,-71,-70,-62,-14,-75,-25,-63,-65,58,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,-116,1,0,0,-28,1,0,0,-109,0,0,0,-95,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,0,0,0,0,-55,-49,-76,-50,-74,-49,-75,-25,-79,-19,-75,-41,58,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,-115,1,0,0,-29,1,0,0,-87,0,0,0,-71,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,0,0,0,0,-79,-66,-76,-50,-74,-49,-75,-25,-79,-19,-75,-41,58,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,-116,1,0,0,-33,1,0,0,4,1,0,0,17,1,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,0,0,0,0,-67,-55,-73,-47,-56,-53,58,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,-114,1,0,0,-28,1,0,0,27,1,0,0,40,1,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,0,0,0,0,-67,-55,-73,-47,-56,-43,-58,-38,58,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,3,-51,-51,-51,1,0,0,0,0,0,0,0,-18,1,0,0,-107,2,0,0,80,0,0,0,94,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,2,0,0,0,0,0,0,0,-45,-61,-69,-89,-61,-5,-77,-58,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,3,-51,-51,-51,1,0,0,0,0,0,0,0,-18,1,0,0,-108,2,0,0,123,0,0,0,-115,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,4,0,0,0,1,0,0,0,-79,-66,-76,-50,-71,-70,-62,-14,-75,-25,-63,-65,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,3,-51,-51,-51,1,0,0,0,0,0,0,0,-17,1,0,0,-108,2,0,0,-110,0,0,0,-94,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,5,0,0,0,1,0,0,0,-55,-49,-76,-50,-74,-49,-75,-25,-79,-19,-75,-41,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,3,-51,-51,-51,1,0,0,0,0,0,0,0,-17,1,0,0,-108,2,0,0,-88,0,0,0,-71,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,6,0,0,0,1,0,0,0,-79,-66,-76,-50,-74,-49,-75,-25,-79,-19,-75,-41,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,3,-51,-51,-51,5,0,0,0,0,0,0,0,-18,1,0,0,-107,2,0,0,24,1,0,0,40,1,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,17,0,0,0,0,0,0,0,-67,-55,-73,-47,-56,-43,-58,-38,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,-115,1,0,0,-71,1,0,0,-21,0,0,0,-4,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,0,0,0,0,-79,-28,-75,-25,-53,-7,58,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,3,-51,-51,-51,1,0,0,0,0,0,0,0,-84,0,0,0,76,1,0,0,101,0,0,0,116,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,3,0,0,0,1,0,0,0,-67,-55,-73,-47,-67,-16,-74,-18,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,3,-51,-51,-51,1,0,0,0,0,0,0,0,-15,1,0,0,-112,2,0,0,101,0,0,0,116,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,3,0,0,0,1,0,0,0,-67,-55,-73,-47,-67,-16,-74,-18,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,3,-51,-51,-51,1,0,0,0,0,0,0,0,-91,0,0,0,78,1,0,0,-43,0,0,0,-26,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,-54,-43,-65,-18,40,-76,-13,-48,-76,41,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,3,-51,-51,-51,1,0,0,0,0,0,0,0,-14,0,0,0,75,1,0,0,-64,0,0,0,-51,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,8,0,0,0,1,0,0,0,-79,-74,-62,-54,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,3,-51,-51,-51,1,0,0,0,0,0,0,0,-87,0,0,0,76,1,0,0,-21,0,0,0,-4,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,13,0,0,0,0,0,0,0,-71,-87,-75,-25,-53,-7,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,66,0,0,0,79,1,0,0,-47,0,0,0,-47,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,67,0,0,0,79,1,0,0,-25,0,0,0,-25,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,66,0,0,0,79,1,0,0,-2,0,0,0,-2,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,69,0,0,0,-118,0,0,0,-65,0,0,0,-50,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,0,0,0,0,-79,-74,-62,-54,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,70,0,0,0,-112,0,0,0,-44,0,0,0,-27,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,0,0,0,0,-54,-43,-65,-18,40,-76,-13,-48,-76,41,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,-115,1,0,0,-43,1,0,0,-65,0,0,0,-48,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,0,0,0,0,-79,-74,-62,-54,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,2,-51,-51,-51,1,0,0,0,0,0,0,0,-115,1,0,0,-37,1,0,0,-43,0,0,0,-28,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,0,0,0,0,-54,-43,-65,-18,40,-76,-13,-48,-76,41,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,3,-51,-51,-51,1,0,0,0,0,0,0,0,-17,1,0,0,-110,2,0,0,-43,0,0,0,-27,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,-54,-43,-65,-18,40,-76,-13,-48,-76,41,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,3,-51,-51,-51,1,0,0,0,0,0,0,0,-19,1,0,0,-110,2,0,0,-66,0,0,0,-49,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,8,0,0,0,1,0,0,0,-79,-74,-62,-54,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,-118,1,0,0,-106,2,0,0,-3,0,0,0,-3,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,-119,1,0,0,-106,2,0,0,19,1,0,0,19,1,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,1,-51,-51,-51,1,0,0,0,0,0,0,0,-119,1,0,0,-106,2,0,0,42,1,0,0,42,1,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,3,-51,-51,-51,1,0,0,0,0,0,0,0,-18,1,0,0,-109,2,0,0,-20,0,0,0,-5,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,1,0,0,0,1,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51,13,0,0,0,0,0,0,0,-71,-87,-75,-25,-53,-7,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-53,-50,-52,-27,0,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,-51,12,0,0,0,0,0,0,0,-51,-51,-51,-51,-51,-51,-51,-51\" }";
	var json_outstr = window.top.getEmptyString(4096);
		
	window.top.document.WEBPRINT.Print(json_instr_file, json_instr_file.length, json_instr_printdata, json_instr_printdata.length, json_outstr, json_outstr.length);
	
	for (var i = 0; i < json_outstr.length; i++) if (json_outstr.charCodeAt(i) == 0) json_outstr = json_outstr.substr(0, i);
	
	var j_o=json_outstr.replace(/(^\s*)|(\s*$)/g, "");//trim()
	if(j_o == ""){
		return;
	}
	var jobj_out = eval("(" + json_outstr + ")")
	
	if (jobj_out.errno == 0) {
		alert("Print操作成功..");
	}
	else {
		alert(jobj_out.errstr + "\nPrint操作失败..");
	}
	
} 


