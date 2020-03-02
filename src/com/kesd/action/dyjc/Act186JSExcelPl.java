package com.kesd.action.dyjc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Cell;
import jxl.Sheet;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.WebConfig;
import com.kesd.dbpara.YffManDef;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;

public class Act186JSExcelPl {

	private static final long serialVersionUID = 1L;

	private File 	myFile;			//金额：算费系统余额
	private File 	myFileBZ;		//表底：抄表日表底
	private String 	contentType;	
	private String 	fileName;
	private String 	imageFileName;
	private String 	errMsg;			//错误信息。	
	private boolean errFlag;		//是否发生错误。主要处理：上传文件格式不正确。用户信息0匹配。读取文件异常
	private String  result; 
	private String  params;			//权限参数
	
	private static final byte CONS_OK  			= 0;	//有匹配用户信息,可以结算。
	private static final byte CONS_PARA			= 1;	//没有找到匹配的档案
	private static final byte CONS_PAYSTATE  	= 2;	//有匹配用户信息。费控状态错误（非金额费控、非主站算费、未开户）。
	private static final byte CONS_BD 			= 3;	//有匹配用户信息。无表底信息或信息不全
	private static final byte CONS_YE 			= 4;	//有匹配用户信息。无余额信息
	private static final byte CONS_CBDAY		= 5;    //有匹配用户信息。无抄表日配置
	
	private static final String FONT_OTHER 	= "<font color=#626262 >";//非主站费控终端-灰
	private static final String FONT_NODATA = "<font color=#FF0000 >";//无匹配项-红色
	private static final String FONT 		= "</font>";
	
	public static class ConsInfo {
		public String 		consNo   	= null;				//户号
		public String 		consName 	= null;				//用户名
		public int			mp_num		= 0;
		
		public double 		remain   	= -1;				//余额 (对应文件的 “预收金额”)
		
		// 20121208 zkz  dbremain 新增  下面相关
		public double 		dbremain   	= -1;				//余额 (对应文件的 “电表余额”)
		public byte			dbremainf	= 0;				//电表余额标志
		// end
		
		public short mp_ids[] = {-1, -1, -1};
		public double curbd[] = {-1.0, -1.0, -1.0};
		
		public byte 		matchFlag  	= CONS_PARA;		//是否有匹配:0可以结算，1.其他费控类型，2 无匹配项
		public int 			rtuId		= -1;				//对应的终端ID
		public short 		mpId		= -1;				//主表
		
		public boolean		setparaf	= false;
		public boolean		fkokf		= false;
		
		byte   cacl_type = 0;
		byte   fee_type  = 0;
		byte   pay_type  = 0;
		byte   feectrl_type= 0;
		short  cb_dayhour =0;
		
		public String describe;
		public double zb_money;
		public double othjs_money;
		public double all_money;	
		public int 	  alarm1;
		public int 	  alarm2;
		public int 	  buy_times;
		public double now_remain;
		public short  res_id;
		public double shutdown_val;

		public static ConsInfo makeNewConsInfo(String cons_no, String cons_desc) {
			ConsInfo cons_info = new ConsInfo();
			
			cons_info.consNo   = cons_no;
			cons_info.consName = cons_desc;
			
			return cons_info;
		}
		
		public static ConsInfo makeNewConsInfo(String cons_no, String cons_desc, 
				   int rtu_id, short mp_id, double zyz,
				   short mp_ids[], int mp_num, boolean fkokf, byte cacl_type, byte feectrl_type, byte pay_type,short cb_dayhour,
				   String describe,double zb_money,double othjs_money,double all_money,int alarm1,int alarm2,int buy_times,double now_remain,
				   short res_id,double shutdown_val) {
			
			ConsInfo cons_info = new ConsInfo();
			
			cons_info.consNo   = cons_no;
			cons_info.consName = cons_desc;
			cons_info.rtuId    = rtu_id;
			cons_info.mpId    = mp_id;
			cons_info.mp_num = mp_num;
			for(int i = 0; i < 3; i++) {
				cons_info.mp_ids[i] = mp_ids[i];
			}
			cons_info.curbd[0] = zyz;
			cons_info.setparaf = true;
			cons_info.fkokf	   = fkokf;

			cons_info.cacl_type= cacl_type;
			cons_info.feectrl_type = feectrl_type;
			cons_info.pay_type = pay_type;
			cons_info.cb_dayhour = cb_dayhour;
			
			cons_info.describe     	=  	describe;
			cons_info.zb_money		=	zb_money;
			cons_info.othjs_money	=	othjs_money;
			cons_info.all_money		=	all_money;	
			cons_info.alarm1		=	alarm1;
			cons_info.alarm2		=	alarm2;
			cons_info.buy_times		=	buy_times;
			cons_info.now_remain	=	now_remain;
			cons_info.res_id		=	res_id;
			cons_info.shutdown_val	=	shutdown_val;

			return cons_info;			
		}
	}
	
	public List<ConsInfo> consList = new ArrayList<ConsInfo>();	
	
	private int findConsByConsNo(String cons_no) {
		for (int cli = 0; cli < consList.size(); cli++){
			if (consList.get(cli).consNo.equalsIgnoreCase(cons_no)){
				return cli;
			}
		}
		return -1;
	}
	
	//结算测点参数
	static class JSRtuMpPara
	{
		public String  cons_no  = "";
		public int     rtu_id   = -1;
		public short   mp_id   = -1;
		public short[] mp_ids = {-1, -1, -1};
		public byte    mp_num  = 0;
		public byte    cacl_type = 0;
		public byte    feectrl_type = 0;
		public byte	   cus_state = 0;
		public byte	   pay_type  = 0;
		public short   cb_dayhour = 0;//新添属性
		public String describe	= "";
		public double zb_money	= 0.0;
		public double othjs_money = 0.0;
		public double all_money = 0.0;	
		public int 	  alarm1 = 0;
		public int 	  alarm2 = 0;
		public int 	  buy_times = 0;
		public double now_remain = 0.0;
		public short  res_id = 0;
		public double shutdown_val = 0.0;
		
		boolean checkFkOk() {
			if (mp_num <= 0)   return false;
			if (cacl_type != 1) return false;		//暂时只支持金额方式
		//	if (feectrl_type != 1) return false;	//暂时只支持主站费控
			if (cus_state != 1) return false;		//用户状态-正常态
			return true;
		}
	};
	
	//副表
	static class BakMp {
		public String cons_no;
		public String made_no;
		public int rtu_id;
		public short mp_id;
		public double cur_bd;
	}
	
	private JSRtuMpPara[] jsRtuMpParas = null;
	private BakMp[] bakMp = null;
	
	//在副表在中查找
	private int findBakMp(String cons_no, String made_no) {
		if (bakMp == null || bakMp.length <= 0) return -1;
		
		for (int i = 0; i < bakMp.length; i++) {
			
			if(bakMp[i] != null && bakMp[i].cons_no != null && bakMp[i].made_no != null) {
				if(bakMp[i].cons_no.equalsIgnoreCase(cons_no) && bakMp[i].made_no.equalsIgnoreCase(made_no)) {
					return i;
				}
			}	
		}
		return -1;
	}
	
	private BakMp findBakMp(int rtu_id, short mp_id) {
		if (bakMp == null || bakMp.length <= 0) return null;
		
		for (int i = 0; i < bakMp.length; i++) {
			if(bakMp[i].rtu_id == rtu_id && bakMp[i].mp_id == mp_id) {
				return bakMp[i];
			}
		}
		
		return null;
	}
	
	//在主表中查找
	private int findJsPara(String cons_no) {
		if (jsRtuMpParas == null || jsRtuMpParas.length <= 0 ||
			cons_no.length() <= 0) return -1;
		
		for (int i = 0; i < jsRtuMpParas.length; i++) {
			if (jsRtuMpParas[i].cons_no != null &&jsRtuMpParas[i].cons_no.equalsIgnoreCase(cons_no)) {
				return i;
			}
		}
		
		return -1;
	}
	
	private boolean setConsInfo(String cons_no, String cons_desc, String data_type, 
			String cur_data, String factoryno) {
		
		if ((data_type.indexOf("有功") < 0) || (data_type.indexOf("总") < 0)) {
			return false;
		}
		
		ConsInfo t_info = null;
		
		//在副表中查找
		int ret_idx = findBakMp(cons_no, factoryno);
		if(ret_idx >= 0) {
			bakMp[ret_idx].cur_bd = CommBase.strtof(cur_data);
			return false;
		}
		
		
		//在主表中查找cons_no
		int db_idx = findJsPara(cons_no);
		//未找到
		if (db_idx < 0) {
			
			t_info = ConsInfo.makeNewConsInfo(cons_no, cons_desc);
			consList.add(t_info);
			
			return false;
		}
		
		JSRtuMpPara t_mppara = null;
		
		//找到
		t_mppara = jsRtuMpParas[db_idx];
		t_info = ConsInfo.makeNewConsInfo(cons_no, cons_desc, t_mppara.rtu_id, t_mppara.mp_id, CommBase.strtof(cur_data),
				t_mppara.mp_ids, t_mppara.mp_num, t_mppara.checkFkOk(), t_mppara.cacl_type, t_mppara.feectrl_type, t_mppara.pay_type, t_mppara.cb_dayhour,
				t_mppara.describe,t_mppara.zb_money,t_mppara.othjs_money,t_mppara.all_money,t_mppara.alarm1,t_mppara.alarm2,t_mppara.buy_times,t_mppara.now_remain,
				t_mppara.res_id,t_mppara.shutdown_val);
		consList.add(t_info);
		
		return true;
	}

	private void checkConsInfoValid1() {
		ConsInfo t_info = null;
		for (int cli = 0; cli < consList.size(); cli++){
			t_info = consList.get(cli);
			
			//没有找到匹配的档案
			if (t_info.setparaf == false) {
				t_info.matchFlag = CONS_PARA;		
				continue;
			}
			
			//有匹配用户信息。费控状态错误（非金额费控、非主站算费、未开户）。
			if (t_info.fkokf == false) {
				t_info.matchFlag = CONS_PAYSTATE;
				continue;
			}
			
			if(t_info.cb_dayhour == 0){
				t_info.matchFlag = CONS_CBDAY;
				continue;
			}
			
			//有匹配用户信息。无表底信息或信息不全
			
			int kk = 0;
			for (kk = 0; kk < t_info.mp_num; kk++) {
				if (t_info.curbd[kk] <= 0) break;
			}
			if (kk < t_info.mp_num) {
				t_info.matchFlag = CONS_BD;	
				continue;				
			}
			
			t_info.matchFlag = CONS_OK;
		}
	}
	
	private void checkConsInfoValid2() {
		ConsInfo t_info = null;
		for (int cli = 0; cli < consList.size(); cli++){
			t_info = consList.get(cli);
			
			if (t_info.matchFlag != CONS_OK) continue;
			
			//有匹配用户信息。无余额信息
			if (t_info.remain < 0) {
				t_info.matchFlag = CONS_YE;
				continue;
			}			
		}
	}
	
	/**  读取金额的excel。保存到consList里。*/
	private void getFileYE(File myfile){
		
		String fileNm = "算费系统余额";			
		
		jxl.Workbook rwb = null;
		InputStream is = null;
		try {
			is = new FileInputStream(myfile);
			rwb = jxl.Workbook.getWorkbook(is);
			Sheet rs = rwb.getSheet(0);
			int rsRows = rs.getRows();//总行数
			
			int ye_title_lineno = WebConfig.dyMisJsConfig.hb_sg186.ye_title_lineno;
			
			//校验
			Cell[] titles = rs.getRow(ye_title_lineno);

			//用户编号
			String ye_cons_no = WebConfig.dyMisJsConfig.hb_sg186.ye_cons_no;
			int ye_cons_no_colno = getTitle(titles, ye_cons_no);
			if (ye_cons_no_colno < 0) {
				setTitleErr(fileNm, ye_cons_no, ye_title_lineno);
				return;
			}
			
			//用户名称
			String ye_cons_desc = WebConfig.dyMisJsConfig.hb_sg186.ye_cons_desc;
			int ye_cons_desc_colno = getTitle(titles, ye_cons_desc);
			if (ye_cons_desc_colno < 0) {
				setTitleErr(fileNm, ye_cons_desc, ye_title_lineno);
				return;
			}
			
			//预收金额
			String ye_remain = WebConfig.dyMisJsConfig.hb_sg186.ye_remain;
			int ye_remain_colno = getTitle(titles, ye_remain);
			if (ye_remain_colno < 0) {
				setTitleErr(fileNm, ye_remain, ye_title_lineno);
				return;
			}

			//电表金额
			String ye_dbremain = WebConfig.dyMisJsConfig.hb_sg186.ye_dbremain;
			int ye_dbremain_colno = getTitle(titles, ye_dbremain);
			if (ye_dbremain_colno < 0) {;
			}

			int startRow = ye_title_lineno + 1;//数据起始行
			
			String t_cons_no   = null;
			String t_cons_desc = null;
			String t_remain    = null;
			String t_dbremain  = null;
			Cell   t_cell      = null;
			byte   t_dbremainf = 0;
			//读取excel内容，保存到consList。
			for(int i = startRow; i < rsRows; i++ ){
				/*
				if (rs.getCell(0,i) == null ||
					rs.getCell(0,i).getContents() == null || 
					rs.getCell(0,i).getContents().isEmpty()) break;
				*/
				
				//用户编号
				t_cell = rs.getCell(ye_cons_no_colno, i);	if (t_cell == null) 	continue;				
				t_cons_no = t_cell.getContents();			if (t_cons_no == null) 	continue;
				t_cons_no = t_cons_no.trim().replaceAll("\r", "").replaceAll("\n", "");
				if (t_cons_no.isEmpty()) continue;
				
				//用户名称
				t_cell = rs.getCell(ye_cons_desc_colno, i);	
				if (t_cell == null || t_cell.getContents() == null) {
					t_cons_desc = "";
				}
				else {
					t_cons_desc = t_cell.getContents();
					t_cons_desc = t_cons_desc.trim().replaceAll("\r", "").replaceAll("\n", "");
				}
				
				//预收金额
				t_cell = rs.getCell(ye_remain_colno, i);	
				if (t_cell == null || t_cell.getContents() == null) {
					t_remain = "";
				}
				else {
					t_remain = t_cell.getContents();
					t_remain = t_remain.trim().replaceAll("\r", "").replaceAll("\n", "");
				}
				
				//电表余额
				if (ye_dbremain_colno < 0) {
					t_dbremain = "";
					t_dbremainf  = 0;
				}
				else {
					t_cell = rs.getCell(ye_dbremain_colno, i);	
					if (t_cell == null || t_cell.getContents() == null) {
						t_dbremain 		= "";
						t_dbremainf  	= 0;
					}
					else {
						t_dbremain 	= t_cell.getContents();
						t_dbremain 	= t_dbremain.trim().replaceAll("\r", "").replaceAll("\n", "");
						t_dbremainf = 1;
					}				
				}
				int cons_idx = findConsByConsNo(t_cons_no);
				if (cons_idx < 0) continue;

				consList.get(cons_idx).remain 		= CommBase.strtof(t_remain);
				consList.get(cons_idx).dbremain		= CommBase.strtof(t_dbremain);
				consList.get(cons_idx).dbremainf	= t_dbremainf;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			errFlag = true;
			errMsg  = fileNm + ",文件读取异常";			
		} finally {
            if ( null != is) {
                try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            } 
		}				
	}
	
	private void setBakMp() {
		ConsInfo tmp = null;
		BakMp bak_mp = null;
		for(int i = 0; i < consList.size(); i++) {
			tmp = consList.get(i);
			if(tmp.mp_num == 2) {
				bak_mp = findBakMp(tmp.rtuId, tmp.mp_ids[1]);
				tmp.curbd[1] = bak_mp.cur_bd;
			}
			else if(tmp.mp_num == 3) {
				bak_mp = findBakMp(tmp.rtuId, tmp.mp_ids[1]);
				tmp.curbd[1] = bak_mp.cur_bd;
				
				bak_mp = findBakMp(tmp.rtuId, tmp.mp_ids[2]);
				tmp.curbd[2] = bak_mp.cur_bd;
			}
		}
	}
	
	/**  读取表底的excel。保存到consList里。*/
	private void getFileBD(File myfile ){
	
		jxl.Workbook rwb = null;
		InputStream is = null;
		
		String fileNm = "抄表日表底";		
		
		try {
			is = new FileInputStream(myfile);
			rwb = jxl.Workbook.getWorkbook(is);
			Sheet rs = rwb.getSheet(0);
			int rsRows = rs.getRows();//总行数
			
			int bd_title_lineno = WebConfig.dyMisJsConfig.hb_sg186.bd_title_lineno;			
			
			Cell[] titles = rs.getRow(bd_title_lineno);
			
			//用户编号
			String bd_cons_no = WebConfig.dyMisJsConfig.hb_sg186.bd_cons_no;
			int bd_cons_no_colno = getTitle(titles, bd_cons_no);
			if (bd_cons_no_colno < 0) {
				setTitleErr(fileNm, bd_cons_no, bd_title_lineno);
				return;
			}
			
			//用户名称
			String bd_cons_desc = WebConfig.dyMisJsConfig.hb_sg186.bd_cons_desc;
			int bd_cons_desc_colno = getTitle(titles, bd_cons_desc);
			if (bd_cons_desc_colno < 0) {
				setTitleErr(fileNm, bd_cons_desc, bd_title_lineno);
				return;
			}
			
			//示数类型
			String bd_data_type = WebConfig.dyMisJsConfig.hb_sg186.bd_data_type;
			int bd_data_type_colno = getTitle(titles, bd_data_type);
			if (bd_data_type_colno < 0) {
				setTitleErr(fileNm, bd_data_type, bd_title_lineno);
				return;
			}
			
			//本次示数
			String bd_cur_data = WebConfig.dyMisJsConfig.hb_sg186.bd_cur_data;
			int bd_cur_data_colno = getTitle(titles, bd_cur_data);
			if (bd_cur_data_colno < 0) {
				setTitleErr(fileNm, bd_cur_data, bd_title_lineno);
				return;
			}
			
			//是否使用出厂编号
			//boolean use_factoryf = WebConfig.dyMisJsConfig.hb_sg186.bd_use_factnof;
			
			//出厂编号
			int bd_factory_no_colno = -1;
			String bd_factory_no = WebConfig.dyMisJsConfig.hb_sg186.bd_factory_no;
			bd_factory_no_colno = getTitle(titles, bd_factory_no);
			if (bd_factory_no_colno < 0) {
				setTitleErr(fileNm, bd_factory_no, bd_title_lineno);
				return;
			}
			
			int startRow = bd_title_lineno + 1;//数据起始行
			
			String t_cons_no   = null;
			String t_cons_desc = null;
			String t_data_type = null;
			String t_cur_data  = null;
			String t_factoryno = null;
			Cell   t_cell      = null;
			
			//读取excel内容，保存到consList。
			for(int i = startRow; i < rsRows; i++ ){
				/*
				if (rs.getCell(0,i) == null ||
					rs.getCell(0,i).getContents() == null || 
					rs.getCell(0,i).getContents().isEmpty()) break;
				*/
				
				//用户编号
				t_cell = rs.getCell(bd_cons_no_colno, i);	if (t_cell == null) 	continue;				
				t_cons_no = t_cell.getContents();			if (t_cons_no == null) 	continue;
				t_cons_no = t_cons_no.trim().replaceAll("\r", "").replaceAll("\n", "");
				if (t_cons_no.isEmpty()) continue;				
				
				//用户名称
				t_cell = rs.getCell(bd_cons_desc_colno, i);	
				if (t_cell == null || t_cell.getContents() == null) {
					t_cons_desc = "";
				}
				else {
					t_cons_desc = t_cell.getContents();
					t_cons_desc = t_cons_desc.trim().replaceAll("\r", "").replaceAll("\n", "");
				}
				
				//示数类型
				t_cell = rs.getCell(bd_data_type_colno, i);	if (t_cell == null) 	continue;				
				t_data_type = t_cell.getContents();			if (t_data_type == null) 	continue;
				t_data_type = t_data_type.trim().replaceAll("\r", "").replaceAll("\n", "");
				if (t_data_type.isEmpty()) continue;
				
				//本次示数
				t_cell = rs.getCell(bd_cur_data_colno, i);	
				if (t_cell == null || t_cell.getContents() == null) {
					t_cur_data = "";
				}
				else {
					t_cur_data = t_cell.getContents();
					t_cur_data = t_cur_data.trim().replaceAll("\r", "").replaceAll("\n", "");
				}
				
				//出厂编号
				t_factoryno="";
				t_cell = rs.getCell(bd_factory_no_colno, i);	
				if (t_cell == null || t_cell.getContents() == null) {
					t_factoryno = "";
				}
				else {
					t_factoryno = t_cell.getContents();
					t_factoryno = t_factoryno.trim().replaceAll("\r", "").replaceAll("\n", "");
				}
				
				//用户编号，用户名称，示数类型，本次示数，出厂编号
				setConsInfo(t_cons_no, t_cons_desc, t_data_type, t_cur_data, t_factoryno);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			errFlag = true;
			errMsg  = fileNm + ",文件读取异常";
		} finally {
            if ( null != is) {
                try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            } 
		}
	
	}
	
	//根据名称查询列号
	private int getTitle(Cell[] titles, String name) {
		if (titles == null) return -1;
		
		String tmp_str = null;
		for (int i = 0; i < titles.length; i++) {
			if (i > 100) break;		//容错
			tmp_str = titles[i].getContents();
			if (tmp_str == null) continue;
			
			if (name.compareToIgnoreCase(tmp_str.trim()) == 0) return i;
		}
		
		return -1;
	}
	
	//列名称错误返回错误信息
	private void setTitleErr(String fileNm, String chkText, int title_lineno) {
		errFlag = true;
		errMsg  = fileNm + "文件内容错误: 没有在第" + title_lineno + "行的单元格找到标题[" + chkText + "]。";
	}
		
	/**上传excel
	 * 
	 */
	public String execute() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		if(myFileBZ == null || myFile == null) {
			request.setAttribute("errMsg", "上传文件不能为空");
			return SDDef.SUCCESS;
		}
		
		errFlag = false;
		errMsg  = "";
		result  = "";
		
		consList = new ArrayList<ConsInfo>();		
		
		loadDBPara();
		
		if (errFlag) {
			request.setAttribute("errMsg", errMsg);
			return SDDef.SUCCESS;
		}
		
		//读取表底信息文件
		getFileBD(myFileBZ);
		if(errFlag){
			request.setAttribute("errMsg", errMsg);
			return SDDef.SUCCESS;
		}
		
		//读取余额文件
		getFileYE(myFile);
		if(errFlag){
			request.setAttribute("errMsg", errMsg);
			return SDDef.SUCCESS;
		}
		setBakMp();
		
		//检查设置参数信息状态1
		checkConsInfoValid1();
		
		//检查设置参数信息状态2:有匹配用户信息。无余额信息
		checkConsInfoValid2();

		String resultdata = toConsJsonStr();
		if(errFlag){
			request.setAttribute("errMsg", errMsg);
			return SDDef.SUCCESS;
		}		
		
		request.setAttribute("errMsg", "");
		request.setAttribute("resultdata", resultdata);
		
		return SDDef.SUCCESS;
	}
	
	/**  把consList转化成json格式返回。
	 * 
	 */
	private  String toConsJsonStr(){
		if(consList.size() == 0) return SDDef.EMPTY;
		
		StringBuffer sbf = new StringBuffer();
		
		sbf.append(SDDef.JSONROWSTITLE);
		for(int i = 0 ; i < consList.size(); i++){
			ConsInfo ci = consList.get(i) ;
			
			sbf.append(SDDef.JSONLBRACES); 
			sbf.append(SDDef.JSONQUOT + "id" 		+ SDDef.JSONQACQ + i + SDDef.JSONDATA);     //id	
			sbf.append(SDDef.JSONQUOT + (i+1) 		+ SDDef.JSONCCM);							//序号				1
			sbf.append(SDDef.JSONQUOT + ci.rtuId  	+ SDDef.JSONCCM);							//rtu_id			2
			sbf.append(SDDef.JSONQUOT + ci.mpId  	+ SDDef.JSONCCM);							//mp_id				3
			switch(ci.matchFlag){
			case CONS_OK:
				sbf.append(SDDef.JSONQUOT + ci.consName + SDDef.JSONCCM);						//用户名称	4
				break;
			case CONS_PARA:
			case CONS_PAYSTATE:
				sbf.append(SDDef.JSONQUOT + FONT_OTHER + ci.consName + FONT + SDDef.JSONCCM);
				break;
			case CONS_BD:
			case CONS_YE:
			case CONS_CBDAY:
				sbf.append(SDDef.JSONQUOT + FONT_NODATA + ci.consName + FONT + SDDef.JSONCCM);
				break;
			}
			sbf.append(SDDef.JSONQUOT + ci.consNo  	+ SDDef.JSONCCM);							//用户编号			5
			sbf.append(SDDef.JSONQUOT + ci.remain  		+ SDDef.JSONCCM);						//预收金额			6
			
			String bdzyz = (ci.curbd[0] < 0 ? 0 : ci.curbd[0]) + "_" + (ci.curbd[1] <0 ? 0 : ci.curbd[1]) + "_" + (ci.curbd[2] < 0 ? 0 : ci.curbd[2]); 
			sbf.append(SDDef.JSONQUOT + bdzyz 			+ SDDef.JSONCCM);					//有功(总)		7
			sbf.append(SDDef.JSONQUOT + getMatchInfo(ci.matchFlag)	+ SDDef.JSONCCM);		//能否结算描述	8
			sbf.append(SDDef.JSONQUOT + ci.matchFlag	+ SDDef.JSONCCM);					//能否结算标志2  9
			sbf.append(SDDef.JSONQUOT + ci.dbremain 	+ SDDef.JSONCCM);					//电表余额		10	
			sbf.append(SDDef.JSONQUOT + ci.dbremainf	+ SDDef.JSONCCM);					//电表余额标志	11	
			sbf.append(SDDef.JSONQUOT + ci.cacl_type 	+ SDDef.JSONCCM);					//计费方式		12	
			sbf.append(SDDef.JSONQUOT + ci.feectrl_type + SDDef.JSONCCM);					//费控方式		13
	
			if(ci.matchFlag != CONS_PARA){
				sbf.append(SDDef.JSONQUOT + ci.describe		+ SDDef.JSONCCM);				//用户名称		14
				sbf.append(SDDef.JSONQUOT + 0				+ SDDef.JSONCCM);				//追补金额		15  //用到时在js页面赋值
				sbf.append(SDDef.JSONQUOT + ci.othjs_money  + SDDef.JSONCCM);				//结算金额		16
				sbf.append(SDDef.JSONQUOT + ci.all_money  	+ SDDef.JSONCCM);				//总金额			17	
				sbf.append(SDDef.JSONQUOT + ci.alarm1  		+ SDDef.JSONCCM);				//报警金额1		18	
				sbf.append(SDDef.JSONQUOT + ci.alarm2  		+ SDDef.JSONCCM);				//报警金额2		19
				sbf.append(SDDef.JSONQUOT + ci.buy_times  	+ SDDef.JSONCCM);				//购电次数		20
				sbf.append(SDDef.JSONQUOT + ci.now_remain  	+ SDDef.JSONCCM);				//当前余额		21
				sbf.append(SDDef.JSONQUOT + ci.cb_dayhour  	+ SDDef.JSONCCM);				//抄表日			22
				sbf.append(SDDef.JSONQUOT + ci.mp_ids[1]  	+ SDDef.JSONCCM);				//mp_id[1]		23
				sbf.append(SDDef.JSONQUOT + ci.mp_ids[2]  	+ SDDef.JSONCCM);				//mp_id[2]		24
				sbf.append(SDDef.JSONQUOT + ci.pay_type  	+ SDDef.JSONCCM);				//pay_type		25
				sbf.append(SDDef.JSONQUOT + ci.res_id  		+ SDDef.JSONCCM);				//res_id		26
				sbf.append(SDDef.JSONQUOT + ci.shutdown_val + SDDef.JSONQBRRBCM);			//shutdown_val	27
			}
			else{
				for(int j=0; j<13; j++){
					sbf.append(SDDef.JSONQUOT + "---" + SDDef.JSONCCM);
				}
				sbf.append(SDDef.JSONQUOT + "---" + SDDef.JSONQBRRBCM);
			}

		}

		sbf.setCharAt(sbf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		sbf.append(SDDef.JSONRBRACES);
	
		
		return sbf.toString();
	}

	private boolean loadJsRtuMpPara()
	{
		JDBCDao j_dao 	= new JDBCDao();
		ResultSet rs 	= null;
		String orgId 	= null;
		String fzmanId 	= null;
		
		if(params != null && !params.isEmpty()){
			String qx_params[] = params.split(SDDef.JSONCOMA);
			orgId 	= qx_params[0];
			fzmanId = qx_params[1];
		}
		StringBuffer sql_cmd = new StringBuffer();
		
		sql_cmd.append("select res.cons_no,mt.made_no, c.rtu_id, c.id mp_id,c.bak_flag,d.power_relaf,d.power_rela1,d.power_rela2,d.cacl_type, d.feectrl_type, d.pay_type, d.cb_dayhour,e.cus_state,");
		sql_cmd.append("res.describe, e.zb_money, e.othjs_money, e.all_money, yfalarm.alarm1, yfalarm.alarm2, e.buy_times, e.now_remain, res.id res_id, e.shutdown_val ");
		sql_cmd.append("from conspara as a, rtupara as b, mppara as c, mppay_para as d, mppay_state as e,residentpara res,meterpara mt,yffalarmpara yfalarm ");
		sql_cmd.append("where b.app_type = 3 and b.cons_id = a.id and c.rtu_id = b.id and d.rtu_id=b.id and e.rtu_id=b.id and e.mp_id=c.id ");
		sql_cmd.append("and yfalarm.id = d.yffalarm_id ");
		sql_cmd.append("and res.rtu_id = b.id and mt.rtu_id=b.id and mt.mp_id=c.id and mt.resident_id=res.id and d.mp_id=c.id and mt.use_flag=1 ");
		sql_cmd.append("and c.use_flag=1 and mt.prepayflag=1 ");
		
		if(orgId != null && !orgId.equals("-1") && !orgId.equals("null")){
			sql_cmd.append(" and a.org_id=" + orgId);
		}
		if(fzmanId != null && !fzmanId.equals("-1") && !fzmanId.equals("null")){
			sql_cmd.append(" and a.line_fzman_id=" + fzmanId);
		}
		sql_cmd.append(" order by c.rtu_id, c.mp_id");
	    
		try {
			rs = j_dao.executeQuery(sql_cmd.toString());
			rs.last();
			int num = rs.getRow();
			JSRtuMpPara[] tmp1 = null;
			BakMp[] tmp2 = null;
			
			if (num > 0) {
				tmp1 = new JSRtuMpPara[num];
				tmp2 = new BakMp[num];
			}
			
			int idx1 = 0, idx2 = 0;
			rs.beforeFirst();
			int bak_num = 0;
			
			while(rs.next()){
				if(rs.getShort("bak_flag") == 1) {	//副表
					bak_num++;
					tmp2[idx2] = new BakMp();
					tmp2[idx2].cons_no  = rs.getString("cons_no");
					tmp2[idx2].made_no  = rs.getString("made_no");
					tmp2[idx2].rtu_id  	= rs.getInt("rtu_id");
					tmp2[idx2].mp_id  	= rs.getShort("mp_id");
					idx2++;	
				} 
				else {	//主表
					tmp1[idx1] = new JSRtuMpPara();
					tmp1[idx1].cons_no   = rs.getString("cons_no");
					tmp1[idx1].rtu_id    = rs.getInt("rtu_id");
					tmp1[idx1].mp_id   	 = rs.getShort("mp_id");
					tmp1[idx1].mp_ids[0] = rs.getShort("power_relaf");	//主表
					tmp1[idx1].mp_ids[1] = rs.getShort("power_rela1");	//动力关联1
					tmp1[idx1].mp_ids[2] = rs.getShort("power_rela2");	//动力关联2
					
					//zkz 20140527  (ids[1]&& [2]) > 0 结果为.mp_num  = 2 错误; 
//					tmp1[idx1].mp_num  = 1;
//					if(tmp1[idx1].mp_ids[1] > 0) {
//						tmp1[idx1].mp_num  = 2;
//					} else if(tmp1[idx1].mp_ids[2] > 0) {
//						tmp1[idx1].mp_num  = 3;
//					}
					if ((tmp1[idx1].mp_ids[1] > 0) && (tmp1[idx1].mp_ids[2] > 0)) {
						tmp1[idx1].mp_num  = 3;
					}
					else if ((tmp1[idx1].mp_ids[1] > 0) || (tmp1[idx1].mp_ids[2] > 0)) {
						tmp1[idx1].mp_num  = 2;
					}
					else {
						tmp1[idx1].mp_num  = 1;
					}
					//zkz end
					
					tmp1[idx1].cacl_type 	= 	rs.getByte("cacl_type");
					tmp1[idx1].feectrl_type = 	rs.getByte("feectrl_type");
					tmp1[idx1].pay_type 	= 	rs.getByte("pay_type");
					tmp1[idx1].cus_state 	= 	rs.getByte("cus_state");
					tmp1[idx1].cb_dayhour 	= 	rs.getShort("cb_dayhour");
					
					tmp1[idx1].describe     =  	rs.getString("describe");
					tmp1[idx1].zb_money		=	rs.getDouble("zb_money");
					tmp1[idx1].othjs_money	=	rs.getDouble("othjs_money");
					tmp1[idx1].all_money	=	rs.getDouble("all_money");	
					tmp1[idx1].alarm1		=	rs.getInt("alarm1");
					tmp1[idx1].alarm2		=	rs.getInt("alarm2");
					tmp1[idx1].buy_times	=	rs.getInt("buy_times");
					tmp1[idx1].now_remain	=	rs.getDouble("now_remain");
					tmp1[idx1].res_id		=	rs.getShort("res_id");
					tmp1[idx1].shutdown_val	=	rs.getDouble("shutdown_val");
					idx1++;
				}
			}
			
			jsRtuMpParas = new JSRtuMpPara[num - bak_num];
			bakMp = new BakMp[bak_num];
			
			for(int i = 0; i < jsRtuMpParas.length; i++) {
				jsRtuMpParas[i] = tmp1[i];
			}
			
			for(int i = 0; i < bak_num; i++) {
				bakMp[i] = tmp2[i];
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			j_dao.closeRs(rs);
		}
		return true;
	}
	
	
	private boolean loadDBPara() {
		
		if (!loadJsRtuMpPara()) {
			
			errFlag = true;
			errMsg  = "读取参数库失败...";			
			
			return false;
		}
		
		return true;
	}
	
	public File getMyFile() {
		return myFile;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public File getMyFileBZ() {
		return myFileBZ;
	}

	public void setMyFileBZ(File myFileBZ) {
		this.myFileBZ = myFileBZ;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}


	public String getMatchInfo(byte param){
		String info = "";
		switch(param){
		case CONS_OK:
			info = "有匹配用户信息,可以结算" + FONT;
			break;
		case CONS_PARA:
			info = FONT_OTHER + "没有找到匹配的档案" + FONT;
			break;
		case CONS_PAYSTATE:
			info = FONT_NODATA + "有匹配用户信息，费控状态错误（非金额费控、非主站算费、未开户）" + FONT;
			break;
		case CONS_BD:
			info = FONT_NODATA + "有匹配用户信息，无表底信息或信息不全" + FONT;
			break;
		case CONS_YE:
			info = FONT_NODATA + "有匹配用户信息，无余额信息" + FONT;
			break;
		case CONS_CBDAY:
			info = FONT_NODATA + "有匹配用户信息，无抄表日配置" + FONT;
		}
		return info;
	}
}

