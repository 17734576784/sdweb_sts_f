var initDate = {
	datetype	: "daypoint",		//日期类型：daypoint-日时间点；dayarea-日时间段；monthpoint-月时间点；montharea-月时间段
	lang		: window.top._lang ? window.top._lang : params.lang,
	initdate : function(){//初始化日期框
		var mydate=new Date();
		var myM=mydate.getMonth()>8?(mydate.getMonth()+1).toString():'0' + (mydate.getMonth()+1);
		var myD=mydate.getDate()>9?mydate.getDate().toString():'0' + mydate.getDate();
		var myH=mydate.getHours()>9?mydate.getHours().toString():'0' + mydate.getHours();
		
		var yesterday=mydate.DateAdd('d',-1);
		var myMyest=yesterday.getMonth()>8?(yesterday.getMonth()+1).toString():'0' + (yesterday.getMonth()+1);//上个月	
		var myDyest=yesterday.getDate()>9?yesterday.getDate().toString():'0' + yesterday.getDate();
		
		var lastMonth=mydate.DateAdd('m',-1);
		var myMlast=lastMonth.getMonth()>8?(lastMonth.getMonth()+1).toString():'0' + (lastMonth.getMonth()+1);
		var year = "", month = "", day = "", hour = "", min = "";
		if(lang == "cn"){
			year = "年"; month = "月", day = "日"; hour = "时"; min = "分";
		}else{
			year = "-"; month = "-", day = " "; hour = ":"; min = ""; second = "";
		}
		switch(this.datetype.toLowerCase()){
			case "minpoint":
				$("#date").val(mydate.getFullYear()+year+myM+month+myD+day+myH+"00"+min);
				break;
			case "minarea":
				$("#sdate").val(mydate.getFullYear()+year+myM+month+myDyest+day+"00"+hour+"00"+min);
				$("#edate").val(mydate.getFullYear()+year+myM+month+myD+day+"00"+hour+"00"+min);
				break;
			case "daypoint":
				$("#date").val(mydate.getFullYear()+year+myMyest+month+myDyest+day);
				break;
			case "dayarea":
				$("#sdate").val(mydate.getFullYear()+year+myM+month+"01");
				$("#edate").val(mydate.getFullYear()+year+myM+month+myD+day);
				break;			
			case "monthpoint":
				$("#date").val(mydate.getFullYear()+year+myMlast+month);
				break;
			case "montharea":
				$("#sdate").val(mydate.getFullYear()+year+"01"+month);
				$("#edate").val(mydate.getFullYear()+year+myM+month);
				break;		
		}
	},
	checkDate : function(){//输入校验
		var type=this.datetype.toLowerCase()
		type=type.substring(type.length-5,type.length);
		
		if(type=="point"){			
			var date = $("#date").val();
			if(date==""){
				alert("Please choose a date.");return false;
			}
		}else{
			var sdate = $("#sdate").val();
			var edate = $("#edate").val();
			if(sdate==""){
				alert("Please choose the start date.");	return false;
			}
			if(edate==""){
				alert("Please choose the end date.");	return false;
			}
			
			if(sdate.length>11){//日期类型为年月日时分
				if(this.getdateyyyymmdd("sdate")>this.getdateyyyymmdd("edate")){//(this.getdateyyyymmdd("sdate")==this.getdateyyyymmdd("edate") && this.gettimehhmm("sdate")>=gettimehhmm("edate"))){
					alert("The start date cannot be greater than the end date.");return false;
				}else if(this.getdateyyyymmdd("sdate")==this.getdateyyyymmdd("edate")){
					var ss = this.gettimehhmm("sdate");
					var ee = this.gettimehhmm("edate");
					if(ss == "0000"){
						ss = 0;
					}
					if(ee == "0000"){
						ee = 0;
					}
					if(ss > ee){
						alert("The start date cannot be greater than the end date.");return false;
					}
				}
			}else{
				if(this.getdateyyyymmdd("sdate")>this.getdateyyyymmdd("edate")){
					alert("The start date cannot be greater than the end date.");return false;
				}
			}			
		}
		return true;
	},
	getdateyyyymm : function(dtid){//返回yyymm的数据格式
		var date = $("#"+dtid).val();
		return date.substring(0,4)+date.substring(5,7)+"01";
	},
	getdateyyyymmdd : function(dtid){//返回yyyymmdd的数据格式
		var date = $("#"+dtid).val();
		return date.substring(0,4)+date.substring(5,7)+date.substring(8,10);
	},
	gettimehhmm : function(dtid){//返回hhmm的数据格式
		var date = $("#"+dtid).val();
		return date.substring(11,13)+date.substring(14,16);
	},
	getDiffmonths : function(){//返回起始时间之间的月份数
		var sdate=this.getdateyyyymm("sdate");
		var edate=this.getdateyyyymm("edate");
		var tdate1 = new Date();
		var tdate2 = new Date();
		tdate1.setFullYear(sdate.substring(0,4),sdate.substring(4,6)-1,"01");
		tdate2.setFullYear(edate.substring(0,4),edate.substring(4,6)-1,"01");
		return tdate1.dateDiff('m',tdate2) + 1;
	},
	getDiffdays : function(){//返回起始时间之间的天数
		var sdate=this.getdateyyyymmdd("sdate");
		var edate=this.getdateyyyymmdd("edate");
		var tdate1 = new Date();
		var tdate2 = new Date();
		tdate1.setFullYear(sdate.substring(0,4),sdate.substring(4,6)-1,sdate.substring(6,8));
		tdate2.setFullYear(edate.substring(0,4),edate.substring(4,6)-1,edate.substring(6,8));
		return tdate1.dateDiff('d',tdate2) + 1;
	},
	getDiffhours : function(){//返回起始时间之间的小时数
		var sdate = $("#sdate").val();
		var edate = $("#edate").val();
		var tdate1 = new Date();
		var tdate2 = new Date();
		tdate1.setFullYear(sdate.substring(0,4),sdate.substring(5,7)-1,sdate.substring(8,10));
		tdate2.setFullYear(edate.substring(0,4),edate.substring(5,7)-1,edate.substring(8,10));
		tdate1.setHours(sdate.substring(11,13),sdate.substring(14,16),0,0)
		tdate2.setHours(edate.substring(11,13),edate.substring(14,16),0,0)
		return tdate1.dateDiff('h',tdate2);
	},
	changev1v2 : function (flag){
		$("#chkv2").attr("checked",false);
		$("#chkv1").attr("checked",false);
		$("#chkv1").attr("disabled",flag);
		$("#chkv2").attr("disabled",flag);
		if(!flag){
			//选中默认的一次值二次值
			if(YDDef.DEFAULT_VALUE1OR2==0){
				$("#chkv2").attr("checked",true);
			}else{
				$("#chkv1").attr("checked",true);
			}
		}	
	}
}