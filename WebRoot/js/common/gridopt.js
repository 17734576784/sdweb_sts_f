var gridopt = {
	
	grid                  : null,
	gridID            	  : "gridbox",         //表格ID
	gridHeader            : "",                //表格标题
	gridattachHeader	  : "",				   //
	gridsplitAt			  :	"",				   //
	gridWidths            : "",                //各单元格宽度
	gridColAlign          : "",                //对齐方式
	gridColTypes          : "",                //单元格数据类型
	gridTooltips          : "",                //是否显示提示
	gridSrndFlag		  : false,			   //
	gridGetDataUrl        : "",                //获取数据url
	gridSelRowUrl         : "",                //选择行url
	gridAddOrEditUrl      : "",                //添加或修改url
	gridDelRowUrl         : "",                //删除url
	gridTitleDetailParas  : "",                //详细信息参数
	gridTitleSimpleParas  : "",                //简化信息参数
	gridSearch            : "",                //查询条件：字符串或json格式
	gridPageId            : "pageinfo",        //分页信息默认id
	redoOnRowSelected     : false,             //选择行时重新定义
	selectRow             : -1,                //是否默认选中某行
	jsondata              : "",                //存储当前页记录
	beforeOnRowSelect	  : false,			   //选择行时重新赋值
	selectids			  : "",				   //选择行时组合的查询条件
	loaded                : false,             //数据是否加载完毕
	showLoading           : true,              //是否显示正在加载图标
	del_showLoading       : false,             //删除时是否显示正在加载图标
	click                 : true,              //
	dblclick              : false,             //双击
	currentRow			  : -1,				   //
	ggtz				  : 0x0,
	
	selectAllOrNone : function(checked) {      //全选&全不选
		var flag = false;
		if (checked.src.indexOf("item_chk0.gif") != -1) {
			flag = true;
			checked.src = def.basePath + 'images/grid/imgs/item_chk1.gif';
		} else if (checked.src.indexOf("item_chk1.gif") != -1) {
			flag = false;
			checked.src = def.basePath +  'images/grid/imgs/item_chk0.gif';
		}
		for ( var i = 0; i < this.grid.getRowsNum(); i++) {
			this.grid.cells2(i, 0).setValue(flag)
		}
	},
	
	//表格显示数据，flag为true时不请求数据库，为false时在数据库查数据；page：页号；pagesize：每页记录数
	showgrid : function(flag,page,pagesize){
		if(this.grid == null){
			if(gridopt.jsondata != ""){
					gridopt.currentRow=this.grid.getSelectedId();
				}
			this.grid = new dhtmlXGridObject(this.gridID);
			this.grid.setImagePath(def.basePath + "images/grid/imgs/");
			this.grid.setHeader(this.gridHeader);
			if(this.gridattachHeader != ""){
				this.grid.attachHeader(this.gridattachHeader);
			}
			if(this.gridWidths.substring(0,1)=="%"){
				this.grid.setInitWidthsP(this.gridWidths.substring(2));
			}else{
				this.grid.setInitWidths(this.gridWidths);
			}
			this.grid.setColAlign(this.gridColAlign);
			this.grid.setColTypes(this.gridColTypes);
			this.grid.attachEvent("onRowSelect", this.doOnRowSelected);
			this.grid.attachEvent("onRowDblClicked",this.doOnRowDblClicked);
			this.grid.init();
			if(this.gridsplitAt != ""){
				this.grid.splitAt(this.gridsplitAt);
			}
			if(this.gridSrndFlag){
				this.grid.enableSmartRendering(true);
			}
			this.grid.enableTooltips(this.gridTooltips);
			//this.loaddata(flag,page,pagesize == undefined ? 10 : pagesize);

		}
		gridopt.grid.clearAll();
		this.loaddata(flag,page,pagesize == undefined ? 20 : pagesize);
	},
	
	//加载数据
	loaddata : function(flag,page,pagesize){
		if(pagesize=="undefined")pagesize = 20;
		if(flag){
			if(gridopt.jsondata != ""){
				var selId=this.grid.getSelectedId();
				this.grid.parse(gridopt.jsondata.page[0], "", "json");
				if(gridopt.currentRow>=0){
					this.grid.selectRow(this.grid.getRowIndex(gridopt.currentRow));
				}
			}
		}else{
			
			if(this.showLoading){
				var width = $(window).width();
				var height = $(window).height();
				
				$("#loading").remove();
				$("#opt_loading").remove();
				
				var div = "<div id='opt_loading' style='position: absolute; border: 1px solid #A4BED4; height: 50px; width:150px; overflow: hidden; background: white; text-align: center;font-size:12px;'><br><img src='"+def.basePath+"images/indicator.gif'> Loading...</div>";
				$(document.body).append(div);
				
				opt_loading.style.posLeft = width/2-75;
				opt_loading.style.posTop  = height/2-25;
			}
			gridopt.loaded=false;
			if(this.gridGetDataUrl == ""){
				gridopt.loaded=true;
				if(gridopt.showLoading){
					$("#opt_loading").remove();
				}
				return;
			}
			
			$.post(this.gridGetDataUrl, {pageNo:page,pageSize:pagesize,result:gridopt.gridSearch , field : gridopt.gridTitleSimpleParas}, function(data) {
				
				if (data.result != "" ) {
					$("#xiugai").attr("disabled",false);
					var json = eval('(' + data.result + ')');
					gridopt.jsondata = json ;
					var totalrecords = json.total;
					var totalpages = 1;
					
					if($("#prs").val()==0){
						totalpages=1;
					}else{
						totalpages = (totalrecords%pagesize)==0?(totalrecords/pagesize):parseInt(totalrecords/pagesize+1);
					}
					
					if(isNaN(totalpages)){
						totalpages = 1;
					}
					
					page = page>totalpages?totalpages:page;
					page = parseInt(page);
					var makepage="<table height='100%' style='font-size:12px;' border=0 cellspacing=0 cellpadding=0><tr valign=bottom>";
					makepage+="<td><select id='prs' onchange='gridopt.showgrid(false,1,this.value);' style='width:140px;'><option value=10>"+"Display 10 records"+"</option><option value=20>"+"Display 20 records"+"</option><option value=50>"+"Display 50 records"+"</option><option value=100>"+"Display 100 records"+"</option><option value=0>"+"All records"+"</option></select></td>";
					makepage+="<td><img src='"+def.basePath+"images/ar_left_abs.gif' "+(page==1?"style='filter: Alpha(Opacity=50);'":"onclick='gridopt.showgrid(false,1,"+pagesize+")' style='cursor:pointer;'")+"></td>";
					makepage+="<td><img src='"+def.basePath+"images/ar_left.gif' "+(page==1?"style='filter: Alpha(Opacity=50);'":"onclick='gridopt.showgrid(false,"+(page-1)+","+pagesize+")' style='cursor:pointer;'")+"></td>";
					makepage+="<td width=40 align=center><input type=text onkeyup='gridopt.gopage(this,"+totalpages+","+pagesize+")' style='width:25px;height:17px;text-align:center;font-size:11px;' value="+page+" id='gopage'></td>";
					makepage+="<td><img src='"+def.basePath+"images/ar_right.gif' "+(page==totalpages?"style='filter: Alpha(Opacity=50);'":"onclick='gridopt.showgrid(false,"+(page+1)+","+pagesize+")' style='cursor:pointer;'")+"></td>";
					makepage+="<td><img src='"+def.basePath+"images/ar_right_abs.gif' "+(page==totalpages?"style='filter: Alpha(Opacity=50);'":"onclick='gridopt.showgrid(false,"+totalpages+","+pagesize+")' style='cursor:pointer;'")+"></td>";
					makepage+="<td>&nbsp;&nbsp;"+"Current Page: "+page+";  Total Pages: "+totalpages+";  Total Records: "+totalrecords+" "+"</td>";
					makepage+="</tr></table>";
					$("#pageinfo").html(makepage);
					$("#prs").val(pagesize);
					
					gridopt.grid.parse(json.page[0], "", "json");
					
					gridopt.loaded = true;
					
					if(typeof setExcValue == "function"){
						setExcValue();
					}
					
					if(gridopt.selectRow >= 0){
						
						if(gridopt.grid.getRowsNum()-1<gridopt.selectRow){
							gridopt.selectRow = gridopt.grid.getRowsNum()-1;
						}
						gridopt.grid.selectRow(gridopt.selectRow, true);
						
					}
				}
				else{
					//zkz modify 20150318 start
					$("#"+gridopt.gridPageId).html("No data to display！<input type=hidden value=20 id=prs /><input type=hidden value=1 id=gopage />");
				//	$("#"+gridopt.gridPageId).html("No data to display！<input type=hidden value="+$("#prs").val()+" id=prs /><input type=hidden value=1 id=gopage />");
					//zkz modify 20150318 end
					gridopt.loaded = true;
					$("#xiugai").attr("disabled",true);
				}
				
				if(gridopt.showLoading){
					$("#opt_loading").remove();
				}
				$("#id").val("");
			});
		}
	},
	
	//输入页号回车后转到该页
	gopage : function(page,totalpages,pagesize){
		with(window.event){
			if(keyCode==13){
				if($.trim(page.value)==""){
					return;
				}
				if(isNaN(page.value)){
					alert("Please input number！");
					page.value = "";
					page.focus();
					return;
				}else if(parseInt(page.value)>totalpages){
					page.value=totalpages;
				}else if(parseInt(page.value)<1){
					page.value=1;
				}
				this.showgrid(false,page.value,pagesize);
			}
		}
	},
	
	//查询
	filter : function(page) {
		if(page == undefined){
			if($("#prs").val()){
				this.showgrid(false,1,$("#prs").val(),this.gridSearch);
			}
			else{
				this.showgrid(false,1,20,this.gridSearch);
			}
		}else{
			this.showgrid(false,1,page,this.gridSearch);
		}
	},
	
	//选中一行时显示该行详细信息
	doOnRowSelected : function(id) {
		
		if(!gridopt.click){
			if(gridopt.redoOnRowSelected){
				redoOnRowSelected(id);
			}
			return;
		}
		if (id == parseInt($("#id").val())) {
			return;
		}
		
		if(gridopt.beforeOnRowSelect == true){
			beforeOnRowSelect(id);
		}
		else {
			gridopt.selectids = id;
		}
		
		$.post(gridopt.gridSelRowUrl, {result : gridopt.selectids, field : gridopt.gridTitleDetailParas}, function(data) {
			var json = eval('(' + data.result + ')');
			if(gridopt.redoOnRowSelected){
				redoOnRowSelected(json);
				return;
			}
		
			var rowsplit = gridopt.gridTitleDetailParas.split(",");
			
			for(var i = 0; i < rowsplit.length; i ++){
				$("#"+rowsplit[i]).val(json.data[i]);
			}
			}
		);
	},
	doOnRowDblClicked : function(id){
		//双击事件
		if(!gridopt.dblclick){
			return;
		}
		redoOnRowDblClicked(id);
	},
	//显示&隐藏详细信息
	showOrHide : function(img ,height) {
		var h = 27;
		if (height == undefined)
			height = 0;
		if(img.alt=="hide"){
			$("#gridbox").height(($(window).height() - h + height));
			$("#showmore").hide();
			img.src=def.basePath +"images/mmu.gif";
			img.alt="show";
			$("#soh").html("Show detail");
		}else{
			$("#gridbox").height($(window).height()-$("#showmore").height()- h + height);
			$("#showmore").show();
			img.src=def.basePath +"images/mmd.gif";
			img.alt="hide";
			$("#soh").html("Hide detail");
		}
		this.showgrid(true);
	},
	
	//导出excel，vfreeze：冻结几行；hfreeze：冻结几列
	toExcel : function(vfreeze, hfreeze){
		
		var tmp_str = encodeURI(jsonString.json2String(this.jsondata));
		
		if(this.grid.getRowsNum() == 0){
			$("#excPara").val(encodeURI(""));
		}else{
			$("#excPara").val(tmp_str);
		}
		
		$("#header").val(encodeURI(this.gridHeader));
		if(this.gridattachHeader != ""){
			$("#attachheader").val(encodeURI(this.gridattachHeader));			
		}
		$("#vfreeze").val(vfreeze);
		$("#hfreeze").val(hfreeze);
		
		toxls.submit();
	},
	
	//删除
	plDel : function(flag){
		
		var seledrow = "";
		for ( var i = 0; i < this.grid.getRowsNum(); i++) {
			if(this.grid.cells2(i, 0).getValue()==1){
				seledrow += "|" + this.grid.getRowId(i);
			}
		}
		if(seledrow==""){
			alert("Please select record before delete it！");
			return;
		}
		if(flag == true){
			seledrow+=","+window.top.rightFrame.rtuid;
		}
		if(confirm("Confirm to delete?")){
			seledrow=seledrow.substring(1);
			if(this.del_showLoading){
				loading.loading();
			}
			$.post(gridopt.gridDelRowUrl, {result:seledrow}, function(data) {
				if(gridopt.del_showLoading){
					loading.loaded();
				}
				if(data.result == SDDef.SUCCESS){
					gridopt.showgrid(false, $("#gopage").val(), $("#prs").val());
					
					if(gridopt.ggtz >= 0){
						window.top.global_ggtz(gridopt.ggtz);
					}
					
				}else if(data.result == SDDef.FAIL){
					alert("Fail to delete！");
				}else {
					alert(data.result);
				}
			});
		}
	},
	
	//添加或修改记录
	addOrEdit : function(flag) {
		var editid = -1;
		if(flag == 'add'){
			$("#id").val("");
		}
		else if(flag == 'edit') {
			if($("#id").val() == "") {
				alert("Please select one record to modify！");
				return;
			}
			editid = $("#id").val();
			if(editid==undefined){
				editid=$("#id_mpp").val();
			}
		}
		
		var params = $('#addorupdate').serialize();
		$.ajax({
			url      : gridopt.gridAddOrEditUrl, 		//后台处理程序
			type     : 'post', 							//数据发送方式
			data     : params, 							//要传递的数据；就是上面序列化的值
			success  : function(data) {			    	//回传函数
			
				if (data.result == SDDef.FAIL) {
					if(flag == 'add'){
						alert("Failure to add！");
					}else{
						alert("Failure to modify！");
					}
				}else if(data.result == SDDef.SUCCESS){
					if(flag == 'add'){
						gridopt.selectRow = -1;
						var cp = parseInt($("#gopage").val());	//当前页
						var npp = parseInt($("#prs").val());	//每页记录数
						var tn = gridopt.grid.getRowsNum();		//当前页记录数
						if(npp==tn){
							cp++;
						}
						gridopt.showgrid(false, cp, npp);
						addSelectRow();
					}
					else{
						gridopt.selectRow = gridopt.grid.getRowIndex(editid);
						gridopt.showgrid(false, $("#gopage").val(), $("#prs").val());
						editSelectRow(editid);
					}
				
					if(gridopt.ggtz >= 0){
						window.top.global_ggtz(gridopt.ggtz);
					}
					
				}else{
					alert(data.result);
				}
			}
		});
	}
};
function addSelectRow(){//添加后选中grid中最后一条
	if(!gridopt.loaded){
		window.setTimeout("addSelectRow()",30);
		return;
	}
	gridopt.grid.selectRow(gridopt.grid.getRowsNum()-1);
	gridopt.doOnRowSelected(gridopt.grid.getSelectedId());
	
}
function editSelectRow(editid){//修改后根据ID选中记录。id为字符串型
	
	if(!gridopt.loaded){
		window.setTimeout("editSelectRow(\""+editid+"\")",30);
		return;
	}
	gridopt.grid.setSelectedRow(editid);
	gridopt.doOnRowSelected(gridopt.grid.getSelectedId());
}