package com.kesd.dbpara;

public class JSPara {
	public static class ZJSRecordPara {
		public int rtu_id;
		public short zjg_id;
		public int fxdf_ym;
		public int js_times;

		public int op_date;
		public int op_time;
			
		public short cons_id;
		public String cons_desc;
		
		public String op_man;
		
		public byte pay_type;
		public String wasteno;

		public double pay_money;
		public double othjs_money;
		public double zb_money;
		public double all_money;

		public double buy_dl;
		public double pay_bmc;

		public int buy_times;

		//上面为缴费记录 下面新添加用于结算补差
		public double misjs_money;
		public double totjs_money;

		public double misjs_bmc;
		public double totjs_bmc;

		public double cur_bd;

		public double lastala_val1;
		public double lastala_val2;
		public double lastshut_val;
		
		public double newala_val1;
		public double newala_val2;
		public double newshut_val;

		public double reserve1;
		public double reserve2;
		public String reserve3;
		public String reserve4;
	}
	
	public static class JJSRecordPara {
		public int rtu_id;
		public short mp_id;
		public int fxdf_ym;
		public int js_times;

		public int op_date;
		public int op_time;
			
		public short res_id;
		public String res_desc;
		
		public String op_man;
		
		public byte pay_type;
		public String wasteno;

		public double pay_money;
		public double othjs_money;
		public double zb_money;
		public double all_money;

		public int buy_times;

		//上面为缴费记录 下面新添加用于结算补差
		public double misjs_money;
		public double totjs_money;

		public double lastala_val1;
		public double lastala_val2;
		public double lastshut_val;
		
		public double newala_val1;
		public double newala_val2;
		public double newshut_val;

		public double reserve1;
		public double reserve2;
		public String reserve3;
		public String reserve4;
	}
}
