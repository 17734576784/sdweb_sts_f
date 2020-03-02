
function CalcBuyDl(feeType, zje, dj_vals, jt_total_dl) {//检索
	var calcret = {};
	calcret.retf		= 	 1;
	calcret.buydl 		=    0;
	var dj_array = dj_vals.split(",");
	var dj  	= 0;				//电价
	if (feeType == SDDef.YFF_FEETYPE_DFL){
		dj  			= dj_array[0];
		calcret.buydl 	= parseFloat(zje) / parseFloat(dj);    
	}
	else if (feeType == SDDef.YFF_FEETYPE_MIX) {
		if (parseInt(dj_array[1]) == 100) {//yffrate.getRatedZ()
			dj  = dj_array[0];
		}
		else if (parseInt(dj_array[1]) + parseInt(dj_array[3]) == 100) {//yffrate.getRateh1()+","+yffrate.getRatehBl1()+","+yffrate.getRateh2()+","+yffrate.getRatehBl2()+","+yffrate.getRateh3()+","+yffrate.getRatehBl3()+","+yffrate.getRateh4()+","+yffrate.getRatehBl4();
			dj  = (parseFloat(dj_array[0]) * parseInt(dj_array[1]) + parseFloat(dj_array[2]) * parseInt(dj_array[3]))/100.0;
		}
		else if (parseInt(dj_array[1]) + parseInt(dj_array[3]) + parseInt(dj_array[5]) == 100) {
			dj  = (parseFloat(dj_array[0]) * parseInt(dj_array[1]) + parseFloat(dj_array[2]) * parseInt(dj_array[3])+ parseFloat(dj_array[4]) * parseInt(dj_array[5]))/100.0;
		}
		else if (parseInt(dj_array[1]) + parseInt(dj_array[3]) + (dj_array[5]) + parseInt(dj_array[7]) == 100) {
			dj  = (parseFloat(dj_array[0]) * parseInt(dj_array[1]) + parseFloat(dj_array[2]) * parseInt(dj_array[3])+ parseFloat(dj_array[4]) * parseInt(dj_array[5]) + parseFloat(dj_array[6]) * parseInt(dj_array[7]))/100.0;
		}
		else {
			calcret.retf		= 	 -1;
		}
	
		if (calcret.retf != -1) {
			calcret.buydl 	= parseFloat(zje) / parseFloat(dj);
		}
	}
	else if (feeType == SDDef.YFF_FEETYPE_JTFL) {	//不再判断月 还是年 统一按年处理
		//yffrate.getRatejR1()+","+yffrate.getRatejTd1()+","+yffrate.getRatejR2()+","+yffrate.getRatejTd2()+","+yffrate.getRatejR3()+","+yffrate.getRatejTd3()+","+yffrate.getRatejR4();
		var jtfeinum = 3;
		if ((parseFloat(dj_array[5]) > parseFloat(dj_array[3])) && (parseFloat(dj_array[3]) > parseFloat(dj_array[1]))) {	//3个阶梯 4个电价
			jtfeinum = 4; 
		}
		else if (parseFloat(dj_array[3]) > parseFloat(dj_array[1])) {	//2个阶梯 3个电价 常用
			jtfeinum = 3;
		}
		else { 	//1个阶梯 2个电价
			jtfeinum = 2;
		}

		var i = 0, end_flag = 0;
		var beginval = 0.0, endval = 0.0;
		var remain_money = zje;
		var detaildl = new Array();
		detaildl = [0,0,0,0];
		for (i = 0; i < jtfeinum; i++) { 
			if (i < jtfeinum - 1) {
				if (jt_total_dl > parseFloat(dj_array[i*2+1]) * 12) 	continue;
			}

			if (i == 0){
				beginval = jt_total_dl;
			}
			else {
				if (jt_total_dl < parseFloat(dj_array[i*2-1]) * 12){
					beginval = parseFloat(dj_array[i*2-1]) * 12;
				}
				else {
					beginval = jt_total_dl;
				}
			}
	
			if (i == jtfeinum - 1){
				end_flag	 = 1;
			}
			else {
				if (parseFloat(remain_money / parseFloat(dj_array[i*2]) + parseFloat(beginval)) <= parseFloat((parseFloat(dj_array[i*2+1])*12))) {
					end_flag = 1;
				}
				else {
					calcret.buydl 	+=  parseFloat((parseFloat(dj_array[i*2+1]) *12) - parseFloat(beginval));
					detaildl[i] = parseFloat((parseFloat(dj_array[i*2+1]) *12) - parseFloat(beginval));
					remain_money 	-=  ((parseFloat(dj_array[i*2+1]) *12) - parseFloat(beginval)) * parseFloat(dj_array[i*2]);
				}
			}

			if (end_flag) {
				calcret.buydl 	+= parseFloat(remain_money) / parseFloat(dj_array[i*2]);
				detaildl[i] = round(parseFloat(remain_money) / parseFloat(dj_array[i*2]), 2);
				calcret.dtdl = detaildl;
				break;
			}
		}
	}
	else {
		calcret.retf		= 	 -1;			
	}

	return calcret;
}