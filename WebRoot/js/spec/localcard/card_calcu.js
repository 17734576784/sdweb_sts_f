/**根据金额计算表底*/
function cardalarm_calcu(all_money, dj, bl, zbd, alarm_val1, alarm_val2, alarm_type, last_shutdown_bd) {
	var point = def.point;	//小数位数
	
	var retVal = {};
	retVal.buy_dl = 0; 			//购电量
	retVal.pay_bmc = 0; 		//表码差
	retVal.shutdown_bd = 0; 	//断电止码
	retVal.alarm_code1 = 0; 	//报警1
	retVal.alarm_code2 = 0; 	//报警2

	var gdl = round(parseFloat(all_money / dj),point);
	retVal.buy_dl = gdl;

	var bmc = round(parseFloat(gdl / bl),point);
	retVal.pay_bmc = bmc;
	
	var sd_bd = round(parseFloat(gdl / bl) + parseFloat(zbd), point);
	if(sd_bd < 0)sd_bd = 0;
	
	retVal.shutdown_bd = sd_bd;
	
	var alarm_code1 = 0, alarm_code2 = 0;
	
	if(alarm_type == 0){	//固定值方式
		
		alarm_code1 = alarm_val1;
		alarm_code2 = alarm_val2;
//不在判断 		
//		if(alarm_code >= retVal.pay_bmc){
//			alarm_code = retVal.pay_bmc * 0.7;
//		}
		
	}else{					//比例方式
	//	var bmc_percent = parseFloat(bmc) * (100 - parseFloat(alarm_val)) / 100;
		alarm_code1 = parseFloat(retVal.pay_bmc) * parseFloat(alarm_val1) / 100;
		alarm_code2 = parseFloat(retVal.pay_bmc) * parseFloat(alarm_val2) / 100;
		
	}

	retVal.alarm_code1 = round(alarm_code1, point);
	retVal.alarm_code2 = round(alarm_code2, point);
	
	retVal.buy_dl 		= isNaN(retVal.buy_dl) ?  "" : retVal.buy_dl; 			//购电量
	retVal.pay_bmc 		= isNaN(retVal.pay_bmc) ? "" : retVal.pay_bmc; 			//表码差
	retVal.shutdown_bd 	= isNaN(retVal.shutdown_bd) ? "" : retVal.shutdown_bd; 	//断电止码
	retVal.alarm_code1 	= isNaN(retVal.alarm_code1) ? "" : retVal.alarm_code1; 	//报警1
	retVal.alarm_code2 	= isNaN(retVal.alarm_code2) ? "" : retVal.alarm_code2; 	//报警2

	return retVal;
}
