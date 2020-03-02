var GridPage = {
	gridDivID		:'gridbox',
	pageDivId		:'pageinfo',
	pgSize 		    : 20,//每页记录数
	currentPage		: 1,//当前页
	totalPage 		: 1,//共N页
	gotoPage		: 0,//跳至第几页
	totalRecords	: 0,//总记录数
	jsondata		: null,
	gridobj 		: null,
	dataResult		: "",
	dataJson		: "",
	
	cur_recperpage	: 20,	//每页显示 记录数
	
	init : function(divId){
		this.gridobj = new dhtmlXGridObject('gridbox');
	 	this.makepageinfo();		
	},
	makepageinfo : function(){
		var html='';
		var makepage="<table height='100%' id='pagediv' style='font-size:12px;' border=0 cellspacing=0 cellpadding=0><tr valign=bottom>";
		makepage+="<td><select id='prs' onchange='GridPage.changePageSize(this.value);' style='width:140px;'><option value=10>"+"Display 10 records"+"</option><option value=20>"+"Display 20 records"+"</option><option value=50>"+"Display 50 records"+"</option><option value=100>"+"Display 100 records"+"</option><option value=0>"+"All records"+"</option></select></td>";
		makepage+="<td><img src='"+def.basePath+"images/ar_left_abs.gif' "+(this.currentPage==1?"style='filter: Alpha(Opacity=50);'":"onclick='GridPage.gopage1(1)' style='cursor:pointer;'")+"></td>";
		makepage+="<td><img src='"+def.basePath+"images/ar_left.gif' "+(this.currentPage==1?"style='filter: Alpha(Opacity=50);'":"onclick='GridPage.gopage1(" + Number(this.currentPage-1) + ")' style='cursor:pointer;'")+"></td>";
		makepage+="<td width=40 align=center><input type=text onkeyup='GridPage.gopage(this.value)' style='width:23px;height:17px;text-align:center;' value="+this.currentPage+" id='gopage'></td>";
		makepage+="<td><img src='"+def.basePath+"images/ar_right.gif' "+(this.currentPage==this.totalPage?"style='filter: Alpha(Opacity=50);'":"onclick='GridPage.gopage1(" + Number(this.currentPage+1) + ")' style='cursor:pointer;'")+"></td>";
		makepage+="<td><img src='"+def.basePath+"images/ar_right_abs.gif' "+(this.currentPage==this.totalPage?"style='filter: Alpha(Opacity=50);'":"onclick='GridPage.gopage1(" + this.totalPage + ")' style='cursor:pointer;'")+"></td>";
		makepage+="<td>&nbsp;&nbsp;"+"Current Page: "+this.currentPage+";  Total Pages: "+this.totalPage+";  Total Records: "+this.totalRecords+" "+"</td>";
		makepage+="</tr></table>";
		$("#" + this.pageDivId).html(makepage);
		$("#prs").val(this.pgSize);
		$("#currentPage").html(this.currentPage);
		
		window.setTimeout('$("#prs").val('+this.cur_recperpage+')', 10);
	},
	changePageSize : function(pageSize){
		if(this.gridobj.getRowsNum() == 0) return;
		this.cur_recperpage = $("#prs").val();
		if(pageSize == 0) pageSize = this.totalRecords;
		this.pgSize= pageSize;	
		this.showgrid();
	}, 
	showgrid : function(){
		this.jsondata=eval('('+ this.dataResult + ')');
		this.totalRecords = this.jsondata.rows.length;
		var div=this.totalRecords%this.pgSize;
		this.totalPage = div==0 ? this.totalRecords/this.pgSize : (parseInt(this.totalRecords-div) + parseInt(this.pgSize))/this.pgSize;
		this.currentPage = this.currentPage <= this.totalPage ? this.currentPage : this.totalPage;
		this.parsegrid();
		$("#currentPage").html(this.currentPage);	
		$("#totalPage").html(this.totalPage);
		$("#totalRecords").html(this.totalRecords);
		//this.makepageinfo();
	},
	//输入页号回车后转到该页
	gopage : function(page){
		with(window.event){
			if(keyCode==13){
				if($.trim(page)==""){
					return;
				}
				if(isNaN(page)){
					alert("请输入数字！");
					page = "";
					$("#gopage").val("");
					$("#gopage").focus();
					return;
				}else if(parseInt(page)>this.totalPage){
					$("#gopage").val(this.totalPage);
					page = this.totalPage;
				}else if(parseInt(page)<1){
					$("#gopage").val(1);
					page = 1;
				}
				this.currentPage = page;
				this.parsegrid();		
			}
		}
		
	},
	gopage1 : function(page){//点击箭头前后翻页
		this.currentPage = page;
		this.parsegrid();		
	},
	parsegrid : function(){
		var si = 0 + (this.currentPage-1) * this.pgSize;
		var ei = parseInt(si) + parseInt(this.pgSize);
		ei=(ei < this.jsondata.rows.length ? ei : this.jsondata.rows.length); 
		var tempjson = this.jsondata.rows.slice(si,ei);
		var tempjson = {rows:tempjson}
		this.gridobj.clearAll();
		this.gridobj.parse(tempjson, "", "json");			
		this.makepageinfo();		
	},
	//20140729新增.直接加载json数据。
	parseJson : function(){
		this.gridobj.parse(this.dataJson, "", "json");
		$("#pagediv").hide();
	}
	
	
	
}