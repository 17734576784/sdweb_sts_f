/**根据金额计算表底*/
function bd_calcu(all_money, dj, bl, zbd, alarm_val, alarm_type, last_shutdown_bd) {

	var point = def.point;	//小数位数
	
	var retVal = {};
	retVal.buy_dl = 0; 			//购电量
	retVal.pay_bmc = 0; 		//表码差
	retVal.shutdown_bd = 0; 	//断电止码
	retVal.alarm_code = 0; 		//报警止码

	var gdl = round(parseFloat(all_money / dj),point);
	retVal.buy_dl = gdl;

	var bmc = round(parseFloat(gdl / bl),point);
	retVal.pay_bmc = bmc;
	
	var sd_bd = round(parseFloat(gdl / bl) + parseFloat(zbd), point);
	if(sd_bd < 0)sd_bd = 0;
	
	retVal.shutdown_bd = sd_bd;
	
	var alarm_code = 0;
	
	if(alarm_type == 0){	//固定值方式
		
		alarm_code = sd_bd - alarm_val;
		
		last_shutdown_bd = parseFloat(last_shutdown_bd);
		
		if(alarm_code <= last_shutdown_bd){
			alarm_code = last_shutdown_bd + bmc * 0.7;
		}
		
	}else{					//比例方式
		var bmc_percent = parseFloat(bmc) * (100 - parseFloat(alarm_val)) / 100;
		alarm_code = bmc_percent + parseFloat(zbd);
		if(alarm_code < 0) alarm_code = 0;
	}
	
	retVal.alarm_code = round(alarm_code, point);
	
	retVal.buy_dl 		= isNaN(retVal.buy_dl) ?  "" : retVal.buy_dl; 			//购电量
	retVal.pay_bmc 		= isNaN(retVal.pay_bmc) ? "" : retVal.pay_bmc; 		//表码差
	retVal.shutdown_bd 	= isNaN(retVal.shutdown_bd) ? "" : retVal.shutdown_bd; 	//断电止码
	retVal.alarm_code 	= isNaN(retVal.alarm_code) ? "" : retVal.alarm_code; 		//报警止码

	return retVal;
}
