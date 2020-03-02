
var WebPrint = {
//	fileName : {//模板文件名.各个打印页面调用时使用。变量名不能修改。值可以改。可以增加变量。
//		dyje : "yffmodel_dyje.loc",
//		gybd : "yffmodel_gybd.loc",
//		gyje : "yffmodel_gyje2.loc",
//		gyks : ["","yffmodel_gyje2.loc","","yffmodel_gykecard.loc"]	//0 无  ，1  科林自管户1，  2  科林自管户2，3   科林6103
//	},
//	nodeName : ["","dy-card", "dy-addcus","dy-remote","dy-main","gy-card1","gy-card3","gy-ycmoney","gy-ycbd","gy-main","np-card","dy-carddl"],//其他-系统控制-打印模板  页面中，各种应用类型对应的top节点名
//	cookieName : ["","dycard", "dyaddcus","dyremote","dymain","gycard1","gycard3","gyycmoney","gyycbd","gymain","npcard", "dy-carddl"],//cookie名
	
	
	nodeName : ["","dy-card", "dy-addcus","tool"],//其他-系统控制-打印模板  页面中，各种应用类型对应的top节点名
	cookieName : ["","dycard", "dyaddcus","tool"],//cookie名
	
	nodeIdx :{
		dycard		: 1,
		dyaddcus	: 2,		//zxp 打印开户token
		tool		: 3
//		dyremote	: 3,
//		dymain 		: 4,	
//		gycard1     : 5,
//		gycard3     : 6,
//		gyycmoney   : 7,
//		gyycbd      : 8,
//		gymain      : 9,
//		npcard      : 10,
//		dycarddl    : 11
	},
	
	//打印程序修要修改的地方
	
	//打印模板定义，主要分为两类  居民和专变  每类中按照模式定义  
	//具体的每个模板可以分为多个实例以区分不同的打印机，
	//每个模板定义为一个实例数组
	//使用那个实例可以从cook中读取，每个实例的名称从配置文件中读取。

	
	loadflag 	: 0,
	inputparam 	: "",	
	err_msg 	: "",
	
	prt_params : {
		rtu_id		: -1,
		sub_id		: -1,	//高压-zjgID,低压-mpId
		op_type		: -1,  //默认缴费操作
		op_date		: "0",
		op_time		: "0",
		file_name	: "",
		reprint		: "0",
		wasteno		: "",
		page_type	: "0",	 //页面类型	对应传入nodeIdx的1-8的值。表示是哪种类型的打印参数。
		ext_info	: ""		//额外信息 zxp 传递开户token
//		scdd_bd		: "0"		//上次断电表底，只有表底计费时使用。
	},
	
	setYffDataOperIdx2params : function(yffDataOperIdx,page_type){
		if(yffDataOperIdx == "")return;	
		var json = eval('(' + yffDataOperIdx + ')');	
		this.prt_params.rtu_id = json.rtu_id;
		this.prt_params.sub_id = json.id;
		this.prt_params.op_date = json.op_date;
		this.prt_params.op_type = json.op_type;
		this.prt_params.op_time = json.op_time;
		this.prt_params.wasteno = json.wasteno;
		this.prt_params.page_type = page_type;
		
		this.prt_params.scdd_bd = "0";
	},
	
	setTool2params : function(tool,page_type){
		if(tool == "")return;	
		var json = eval('(' + tool + ')');	
		this.prt_params.rtu_id = json.rtu_id;
		this.prt_params.sub_id = json.id;
		this.prt_params.op_date = json.op_date;
		this.prt_params.op_time = json.op_time;
		this.prt_params.wasteno = json.wasteno;
		this.prt_params.page_type = page_type;		
	},
	
	//zkz  201407 setYffDataOperIdx2params 在缴费按钮中赋值，page_type 打印时重新赋值 在外接卡表中， 后面把上面 函数中的page_type 去掉 较好
//	setYffExtPageCardtype : function(page_type){
//		this.prt_params.page_type = page_type;
//	},
	
	doPrintGy: function(){//外面各个js调本函数
		
		this.loadflag = 0;
		this.inputparam = "";
		var strjson = jsonString.json2String(this.prt_params);
		if(!this.checkparam()){
			return;			
		}
		child_page.loading.loading();
		$.post(def.basePath + "ajax/actPrint!getPrintPara.action",{result:strjson },function(data){
			child_page.loading.loaded();
			if(data.result != ""){
				WebPrint.loadflag = 1;
				WebPrint.inputparam = data.result;
				WebPrint.showDialog();
			}else{
				WebPrint.loadflag = 2;
				WebPrint.err_msg = data.errMsg;
				//web界面提示错误信息
				alert(WebPrint.err_msg);				
			}
		});
		
	},
	
	doPrintDy: function(){//外面各个js调本函数
		
		this.loadflag = 0;
		this.inputparam = "";
		var strjson = jsonString.json2String(this.prt_params);
 		if(!this.checkparam()){
			return;
		}
		child_page.loading.loading();
		$.post(def.basePath + "ajax/actPrint!getPrintPara.action",{result:strjson },function(data){
			child_page.loading.loaded();
			if(data.result != ""){
				WebPrint.loadflag = 1;
				WebPrint.inputparam = data.result;
 				WebPrint.showDialog();
			}else{
				WebPrint.loadflag = 2;
				WebPrint.err_msg = data.errMsg;
				//web界面提示错误信息
				alert(WebPrint.err_msg);
			}
		});
		
	},
	
	doPrintTool: function(){//外面各个js调本函数
		
		this.loadflag = 0;
		this.inputparam = "";
		var strjson = jsonString.json2String(this.prt_params);		
// 		if(!this.checkparam()){
//			return;
//		}
		
		
		child_page.loading.loading();
		$.post(def.basePath + "ajax/actPrint!getPrintPara.action",{result:strjson },function(data){
			child_page.loading.loaded();
			if(data.result != ""){
				WebPrint.loadflag = 1;
				WebPrint.inputparam = data.result;
 				WebPrint.showDialog();
			}else{
				WebPrint.loadflag = 2;
				WebPrint.err_msg = data.errMsg;
				//web界面提示错误信息
				alert(WebPrint.err_msg);
			}
		});
		
	},
	
	showDialog: function(){//显示打印页面

		var json_instr = "{\"dllname\":\"yffprint/libyffwebprint.dll\", \"procname\":\"YffPrint\", \"inputparam\":\"" + this.inputparam + "\"}";	
		var json_outstr = WebPrint.getEmptyString(4096);
  		document.YFFWEBCTRL.CallProc(json_instr, json_instr.length, json_outstr, json_outstr.length);
		child_page.loading.loaded();
		for (var i = 0; i < json_outstr.length; i++) if (json_outstr.charCodeAt(i) == 0) json_outstr = json_outstr.substr(0, i);

		var jobj_out = eval("(" + json_outstr + ")");
		if (jobj_out.errno != 0) {
			alert(jobj_out.errstr + "\nCallProc Failed..");
		}
		
	},
	
	getEmptyString : function (str_len) {
		var ret_str = "";
		
		var count = (str_len + 127) / 128;
		for (var i = 0; i < count; i++) {
			ret_str += "                                                                                                                               ";
		}
		
		return ret_str;
	},
	
	checkparam : function(){
		
		if(this.prt_params.rtu_id ==-1){
			alert("Print parameter is incorrect.[Terminal ID]=-1.");
			return false;
		}
		if(this.prt_params.sub_id ==-1){
			alert("Print parameter is incorrect.[Measuring point/Total plus group ID]=-1.");
			return false;
		}
		if(this.prt_params.wasteno == ""){
			alert("Print parameter is incorrect.[Serial number] is empty.");
			return false;
		}
		if(this.prt_params.op_type ==-1){
			alert("Print parameter is incorrect.[Operation type]=-1.");
			return false;
		}
		if(this.prt_params.file_name = "" || this.prt_params.file_name == null){
			alert("Print parameter is incorrect.[Print template] is empty.");
			return false;
		}		
		return true;
	},
	
	selTemplate : function(type){//选择模板
		modalDialog.width 	= 400;
		modalDialog.height 	= 300;
		modalDialog.param	= type;
		modalDialog.url 	= def.basePath + "jsp/dialog/selPrtTemplate.jsp";
		
		var rtnVal = modalDialog.show();
	
		if(rtnVal == undefined || rtnVal == null){
			return null ;
		}else{
			if(rtnVal.saveflag) this.saveCookie(type,rtnVal.filename)
			return rtnVal.filename;
		}
		 
	},
	
	getTemplateFileNm : function(type){//取得模板文件名。
 		if( type < this.nodeIdx.dycard || type > this.nodeIdx.tool ) {			
			return null;	
		}
		var filename = cookie.get(this.cookieName[type]);//检查是否有cookie里是否记录了默认选择的模板
		
		if( filename == undefined || filename == null || filename == "") {//没有cookie，弹出选择模板窗口
			filename = this.selTemplate(type);
		}
		
		return filename;
	},
	
	saveCookie : function(type,filename){
	
		var cookiename = this.cookieName[type];
		cookie.set(cookiename,filename);
	}
	
	
}
