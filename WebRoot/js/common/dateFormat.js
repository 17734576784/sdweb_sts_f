var dateFormat = {
	date : "",
	lang : window.top._lang ? window.top._lang : params.lang,
	
	formatToYMD : function (type){	//20110101--->2011年01月01日&2011-01-01
		if(this.date.length==8){
			if(type==0){
				return (this.date.substring(0,4)+"年"+this.date.substring(4,6)+"月"+this.date.substring(6,8)+"日");
				/*
				if(this.lang == "cn"){
					return (this.date.substring(0,4)+"年"+this.date.substring(4,6)+"月"+this.date.substring(6,8)+"日");
				}else{
					return (this.date.substring(0,4)+"-"+this.date.substring(4,6)+"-"+this.date.substring(6,8));
				}
				*/
			}else if(type==1){
				return (this.date.substring(0,4)+"-"+this.date.substring(4,6)+"-"+this.date.substring(6,8));
			}
		}else{
			return "";
		}
	},
	formatToHM : function(type){   //0830格式化为日期格式08时30分&08:30
		if(this.date.length==4){
			if(type==0){
				return (this.date.substring(0,2)+"时"+this.date.substring(2,4)+"分");
			}else if(type==1){
				return (this.date.substring(0,2)+":"+this.date.substring(2,4));
			}
		}else if(this.date.length==3){
			if(type==0){
				return ("0"+this.date.substring(0,1)+"时"+this.date.substring(1,3)+"分");
			}else if(type==1){
				return ("0"+this.date.substring(0,1)+":"+this.date.substring(1,3));
			}
		}else if(this.date.length==2){
			if(type==0){
				return ("00时"+this.date.substring(0,2)+"分");
			}else if(type==1){
				return ("00:"+this.date.substring(0,2));
			}
		}else if(this.date.length==1){
			if(type==0){
				return ("00时0"+this.date+"分");
			}else if(type==1){
				return ("00:0"+this.date);
			}
		}else{
			return "";
		}
	},
	formatToHMS : function(type){   //000001格式化为日期格式08时30分02秒&08:30:02
		
		while(this.date.length<6){
			this.date = 0 + "" + this.date;
		}
		
		if(this.date.length==6){
			if(type==0){
				if(this.lang == "cn"){
					return (this.date.substring(0,2) + "时" + this.date.substring(2,4) + "分" + this.date.substring(4,6) + "秒");
				}else{
					return (this.date.substring(0,2) + ":" + this.date.substring(2,4) + ":" + this.date.substring(4,6));
				}
			}else if(type==1){
				return (this.date.substring(0,2) + ":" + this.date.substring(2,4) + ":" + this.date.substring(4,6));
			}
		}else{
			return "";
		}
	},
	
	formatToDHM : function (){	//010101--->01日01时01分
		switch(this.date.length){
			case 6:
				return (this.date.substring(0,2)+"日"+this.date.substring(2,4)+"时"+this.date.substring(4,6)+"分");
			case 5:
				return ("0"+this.date.substring(0,1)+"日"+this.date.substring(1,3)+"时"+this.date.substring(3,5)+"分");
			default:
			return "";	
		}
	},
	formatToSHM : function (){
		if(this.date.length <6){
			this.date= "000000"+this.date;
		}
		if(this.date.length >6){
			this.date = this.date.substr(this.date.length-6,6)
		}
		switch(this.date.length){
			case 6:
				return (this.date.substring(0,2)+"时"+this.date.substring(2,4)+"分");
			case 5:
				return ("0"+this.date.substring(0,1)+"时"+this.date.substring(1,3)+"分");
			default:
			return "";	
		}
	},	
	getToday : function(type){
		var datestr = "";
		var mydate=new Date();
		var myM=mydate.getMonth()>8?(mydate.getMonth()+1).toString():'0' + (mydate.getMonth()+1);
		var myD=mydate.getDate()>9?mydate.getDate().toString():'0' + mydate.getDate();
		var myH=mydate.getHours()>9?mydate.getHours().toString():'0' + mydate.getHours();
		var mym=mydate.getMinutes()>9?mydate.getMinutes().toString():'0' + mydate.getMinutes();
		var mys=mydate.getSeconds()>9?mydate.getSeconds().toString():'0' + mydate.getSeconds();
		
		for(var i=0;i<type.length;i++){
			cotype = type.substring(i,i+1);
			switch(cotype){
				case "y": datestr = datestr + mydate.getFullYear();break;
				case "M": datestr = datestr + myM;break;
				case "d": datestr = datestr + myD;break;
				case "H": datestr = datestr + myH;break;
				case "m": datestr = datestr + mym;break;
				case "s": datestr = datestr + mys;break;
			}
		}
		
		return datestr;
	}
};

//dateFormat.lang = window.top._lang ? window.top._lang : params.lang;

/**
 * 2012年01月01日--->20120101
 * 2012-01-01--->20120101
 * 12时20分00秒--->122000
 * 12:20:00--->122000
 */
function getDate_Time(dt_str){
	var dt = "";
	for ( var j = 0; j < dt_str.length; j++) {
		var ch = dt_str.substring(j, j + 1);
		if(!isNaN(ch)){
			dt += ch;
		}
	}
	while(dt.substring(0,1) === "0"){
		dt = dt.substring(1);
	}
	
	if(dt === ""){
		dt = 0;
	}
	
	return dt;
}

/**
 * 3-8位数字的日期时间格式化，数据从库中或日期控件中取得,开头不含0。 
 * @data:要格式化数据,
 * @type:格式化形式	'YMD':121012-2012年10月12日，'YMD_FULL':20120808-2012年08月08日
 */
function formatMDH(data,type){
	if(data == undefined || type == undefined || data == 0){
		return '';
	}
	if(typeof(data) == 'number'){
		data = data.toString();
	}
	
	if(data.length < 3 || data.length > 8){
		return '';
	}
	
	var para1, para2, para3;
	
	if(data.length <= 4){
		data = parseInt(data);
		para1 = parseInt(data/100) < 10 ? ('0' + parseInt(data/100)) : parseInt(data/100);
		para2 = parseInt(data%100) < 10 ? ('0' + parseInt(data%100)) : parseInt(data%100);
		para3 = '00';
	}
	else{
		data = parseInt(data);
		para1 = parseInt(data/10000) < 10 ? ('0' + parseInt(data/10000)) : parseInt(data/10000);
		para2 = parseInt((data%10000)/100) <10 ? ('0' + parseInt((data%10000)/100)) : parseInt((data%10000)/100);
		para3 = parseInt((data%10000)%100) <10 ? ('0' + parseInt((data%10000)%100)) : parseInt((data%10000)%100);
	}
	
	switch(type){
		case 'MDH' :
			return  para1 + '-' + para2 + '-' +  para3;
			break;
		case 'YMD' :
			return  '20' + para1 + '-' + para2 + '-' +  para3;
			break;
		case 'YMD_FULL' :
			return para1 + '-' + para2 + '-' +  para3;
			break;
		case 'HM'  :
			return 	para1 + '-' + para2;
			break;
		case 'HMS' :
			return para1 + '-' + para2 + '-' + para3 ;
			break;
		case 'DH'  :
			return 	para1 + '-' + para2 + '-';
			break;
		case 'DHM' :
			return para1 + '-' + para2 + '-' + para3;
			break;
		case 'MD':
			return para1 + '-' + para2 ;
			break;
		default:
			return '';
			break;
	}
}

