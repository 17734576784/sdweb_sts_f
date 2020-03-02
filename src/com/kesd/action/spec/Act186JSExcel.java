package com.kesd.action.spec;

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

import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.WebConfig;
import com.kesd.util.OnlineRtu;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
import com.libweb.comnt.ComntFunc;
import com.libweb.dao.JDBCDao;

public class Act186JSExcel {

	private static final long serialVersionUID = 1L;

	private File 	myFile;			//金额：算费系统余额
	private File 	myFileBZ;		//表底：抄表日表底
	private String 	fileName;
	private String 	errMsg;			//错误信息。
	private boolean errFlag;		//是否发生错误。主要处理：上传文件格式不正确。用户信息0匹配。读取文件异常
	private String  result;
	private String  params;			//权限参数
	
	private static final byte CONS_OK  			= 0;	//有匹配用户信息,可以结算。
	private static final byte CONS_PARA			= 1;	//没有找到匹配的档案
	private static final byte CONS_PAYSTATE  	= 2;	//有匹配用户信息。费控状态错误（非金额费控、非主站算费、未开户）。
	private static final byte CONS_BD 			= 3;	//有匹配用户信息。无表底信息或信息不全
	private static final byte CONS_YE 			= 4;	//有匹配用户信息。无余额信息
	
	private static final String S_FONT_OTHER 	= "<font color=#626262 >";//非主站费控终端-灰
	private static final String S_FONT_NODATA 	= "<font color=#FF0000 >";//红色
	private static final String E_FONT 			= "</font>";
	
	public static class ConsInfo {
		String 		cons_no   	= null;				//户号
		String 		cons_name 	= null;				//用户名
		
		double 		remain   	= -1;				//余额               （对应文件的 “预收金额”）
		double[] 	bd_zyz   	= {-1.0,-1.0,-1.0};	//表底正有总（对应文件的 “有功（总）-上次示数”）
		double[] 	bd_zyj   	= {-1.0,-1.0,-1.0};	//表底正有尖（对应文件的 “有功（尖）-上次示数”）
		double[] 	bd_zyf  	= {-1.0,-1.0,-1.0};	//表底正有峰（对应文件的 “有功（峰）-上次示数”）
		double[] 	bd_zyp   	= {-1.0,-1.0,-1.0};	//表底正有平（对应文件的 “有功（平）-上次示数”）
		double[] 	bd_zyg   	= {-1.0,-1.0,-1.0};	//表底正有谷（对应文件的 “有功（谷）-上次示数”）
		
		double[] 	bd_zwz   	= {-1.0,-1.0,-1.0};	//表底正无总（对应文件的 “无功（总）-上次示数”）
		
		byte 		matchFlag  	= CONS_PARA;		//是否有匹配:0可以结算，1.其他费控类型，2 无匹配项
		
		int 		rtu_id		= -1;				//对应的终端ID
		short 		zjg_id		= -1;				//对应的总加组ID
		
		int			mp_num		= 0;
		short		mp_ids[]	= {-1, -1, -1};
		byte[]		zf_flag		= {0, 0, 0};		//正反相标志 0:正,1:反
		boolean		setparaf	= false;
		boolean		fkokf		= false;
		
		byte   cacl_type = 0;
		byte   fee_type = 0;
		byte   pay_type = 0;
		
		public void setMpInfo(short mp_ids1[], byte zf_flags1[], int mp_num1) {
			if (mp_num1 > mp_ids.length) mp_num1 = mp_ids.length;
			
			for (int i = 0; i < mp_num1; i++) {
				mp_ids[i]  = mp_ids1[i];
				zf_flag[i] = zf_flags1[i]; 
			}
			
			mp_num = mp_num1;
		}
		
		public static ConsInfo makeNewConsInfo(String cons_no, String cons_desc) {
			ConsInfo cons_info = new ConsInfo();
			
			cons_info.cons_no   = cons_no;
			cons_info.cons_name = cons_desc;
			
			return cons_info;
		}
		
		public static ConsInfo makeNewConsInfo(String cons_no, String cons_desc, 
											   int rtu_id, short zjg_id, 
											   short mp_ids[], byte zf_flags[], int mp_num, 
											   boolean fkokf, byte cacl_type, byte fee_type, byte pay_type) {
			ConsInfo cons_info = new ConsInfo();
			
			cons_info.cons_no   = cons_no;
			cons_info.cons_name = cons_desc;
			cons_info.rtu_id    = rtu_id;
			cons_info.zjg_id    = zjg_id;
			
			cons_info.setMpInfo(mp_ids, zf_flags, mp_num);
			
			cons_info.setparaf = true;
			cons_info.fkokf	   = fkokf;
			
			cons_info.cacl_type = cacl_type;
			cons_info.fee_type = fee_type;
			cons_info.pay_type = pay_type;
			return cons_info;			
		}
	}
	
	public List<ConsInfo> consList = new ArrayList<ConsInfo>();	
	
	private int findConsByConsNo(String cons_no) {
		for (int cli = 0; cli < consList.size(); cli++){
			if (consList.get(cli).cons_no.equalsIgnoreCase(cons_no)){
				return cli;
			}
		}
		return -1;		
	}
	
	private int findConsById(int rtu_id, short zjg_id) {
		
		for (int cli = 0; cli < consList.size(); cli++){
			if (consList.get(cli).rtu_id == rtu_id && consList.get(cli).zjg_id == zjg_id){				
				return cli;
			}
		}
		return -1;
	}	
	
	static int gGetMpids(int clp_num, String clp_ids1, String zf_flag1, short mp_ids[], byte zf_flags[]) {
		if (mp_ids == null || mp_ids.length <= 0) return 0;
		if (zf_flags == null || zf_flags.length <= 0) return 0;
		
		int ret_num = 0;
		
		for (int i = 0; i < clp_ids1.length(); i++) {
			if (i >= zf_flag1.length()) break;
			
			if (clp_ids1.charAt(i) == '0') continue;
			else {
				mp_ids[ret_num] = (short)i;
				zf_flags[ret_num] = (byte)((zf_flag1.charAt(i) == '0') ? 0 : 1);
			}
			
			ret_num++;
			
			if (ret_num >= mp_ids.length ||
				ret_num >= zf_flags.length ||
				ret_num >= clp_num) break;
		}
		
		return ret_num;
	}
	
	//结算总加组参数
	static class JSRtuZjgPara
	{
		public String cons_no  = "";
		public int    rtu_id   = -1;
		public short  zjg_id   = -1;
		public byte   clp_num  = 0;
		public short  mp_ids[] = null;
		public byte   zf_flag[] = null;	//0正相 1反相
		public byte   cacl_type = 0;
		public byte   feectrl_type = 0;
		public byte   pay_type = 0;
		public byte	  cus_state = 0;
		
		boolean checkFkOk() {
			if (clp_num <= 0)   return false;
			
			if((cacl_type == 1 && feectrl_type == 1) || (cacl_type == 1 && feectrl_type == 0) || cacl_type == 2 && feectrl_type == 0) {
				
			} else {
				return false;
			}
			if(pay_type == 0) return false;		//暂时不支持卡式
			
			if (cus_state != 1) return false;	//用户状态-正常态
			return true;
		}
	}
	
	//结算电表参数
	static class JSMeterPara
	{
		public int	  rtu_id  = -1;
		public short  mp_id   = -1;
		public double zyz = -1;
		public double zyj = -1;
		public double zyf = -1;
		public double zyp = -1;
		public double zyg = -1;
		
		public double zwz = -1;
		public String cons_no = "";
		public String made_no = "";
	};
	
	private JSRtuZjgPara[] jsRtuZjgParas = null;
	private JSMeterPara[]  jsMterParas   = null;
	
	private int findMeter(int[] idx, String cons_no, String made_no) {
		if(jsMterParas == null || jsMterParas.length <= 0) return -1;
		
		for(int i = 0; i < idx.length; i++) {
			if(jsMterParas[idx[i]].cons_no.equalsIgnoreCase(cons_no) && jsMterParas[idx[i]].made_no.equalsIgnoreCase(made_no)) {
				return idx[i];
			}
		}
		return -1;
	}
	
	private int[] findMeter(String cons_no) {
		if(jsMterParas == null || jsMterParas.length <= 0) return null;
		
		List<Integer> list = new ArrayList<Integer>();
		
		for(int i = 0; i < jsMterParas.length; i++) {
			if(jsMterParas[i].cons_no.equalsIgnoreCase(cons_no)) {
				list.add(i);
			}
			//if(list.size() >= 3) break;
		}
		
		if(list.size() > 0) {
			int[] ret = new int[list.size()];
			for(int i = 0; i < ret.length; i++) {
				ret[i] = list.get(i);
			}
			return ret;
		}
		return null;
	}
	
	private int findZjgPara(String cons_no) {
		if(jsRtuZjgParas == null || jsRtuZjgParas.length <= 0) return -1;
		
		for(int i = 0; i < jsRtuZjgParas.length; i++) {
			if(jsRtuZjgParas[i].cons_no.equalsIgnoreCase(cons_no)) {
				return i;
			}
		}
		
		return -1;
	}
	
	
	private boolean isValidDataType(String data_type) {
		//有功总
		if (data_type.indexOf("有功") >= 0) {
			return true;
		}
		//无功总
		else if (data_type.indexOf("无功") >= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean setConsInfo(String cons_no, String cons_desc, String data_type, String cur_data, String factoryno, boolean use_factoryf) {
		if (!isValidDataType(data_type)) return false;
		
		ConsInfo t_info = null;
		
		//根据户号查找电表
		int mt_idx[] = findMeter(cons_no);
		//未找到
		if(mt_idx == null) {
			t_info = ConsInfo.makeNewConsInfo(cons_no, cons_desc);
			consList.add(t_info);
			return false;
		}
		//找到
		else {
			double bd = CommBase.strtof(cur_data);
			//有多个电表
			if(mt_idx.length > 1) {
				int m_id = findMeter(mt_idx, cons_no, factoryno);
				if(m_id < 0) {
					return false;
				}
				
				if ((data_type.indexOf("有功") >= 0) && (data_type.indexOf("总") >= 0)) {
					jsMterParas[m_id].zyz = bd;
				}
				//无功总
				else if((data_type.indexOf("无功") >= 0) && (data_type.indexOf("总") >= 0)) {
					jsMterParas[m_id].zwz = bd;
				}
				//有功 尖
				else if((data_type.indexOf("有功") >= 0) && (data_type.indexOf("尖") >= 0)) {
					jsMterParas[m_id].zyj = bd;
				}
				//有功 峰
				else if((data_type.indexOf("有功") >= 0) && (data_type.indexOf("峰") >= 0)) {
					jsMterParas[m_id].zyj = bd;
				}
				//有功 平
				else if((data_type.indexOf("有功") >= 0) && (data_type.indexOf("平") >= 0)) {
					jsMterParas[m_id].zyj = bd;
				}
				//有功 谷
				else if((data_type.indexOf("有功") >= 0) && (data_type.indexOf("谷") >= 0)) {
					jsMterParas[m_id].zyj = bd;
				}
			}
			
			int info_idx = findZjgPara(cons_no);
			
			if(info_idx < 0){
				t_info = ConsInfo.makeNewConsInfo(cons_no, cons_desc);
				consList.add(t_info);
				return false;
			}
			
			JSRtuZjgPara t_zjgpara = jsRtuZjgParas[info_idx];
			info_idx = findConsById(t_zjgpara.rtu_id, t_zjgpara.zjg_id);
			
			//consList无该条记录
			if (info_idx < 0) {
				
				t_info = ConsInfo.makeNewConsInfo(cons_no, cons_desc, t_zjgpara.rtu_id, t_zjgpara.zjg_id,
						t_zjgpara.mp_ids, t_zjgpara.zf_flag, t_zjgpara.clp_num, t_zjgpara.checkFkOk(), t_zjgpara.cacl_type, t_zjgpara.feectrl_type, t_zjgpara.pay_type);
				
				consList.add(t_info);
			}
			//consList存在 且只有一个电表
			else if(mt_idx.length == 1) {
				t_info = consList.get(info_idx);
			} else {
				return true;
			}
			
			//有功总
			if ((data_type.indexOf("有功") >= 0) && (data_type.indexOf("总") >= 0)) {
				t_info.bd_zyz[0] 	 = bd;
			}
			//无功总
			else if ((data_type.indexOf("无功") >= 0) && (data_type.indexOf("总") >= 0)) {
				t_info.bd_zwz[0] 	 = bd;
			}
			//有功 尖
			else if((data_type.indexOf("有功") >= 0) && (data_type.indexOf("尖") >= 0)) {
				t_info.bd_zyj[0] = bd;
			}
			//有功 峰
			else if((data_type.indexOf("有功") >= 0) && (data_type.indexOf("峰") >= 0)) {
				t_info.bd_zyj[0] = bd;
			}
			//有功 平
			else if((data_type.indexOf("有功") >= 0) && (data_type.indexOf("平") >= 0)) {
				t_info.bd_zyj[0] = bd;
			}
			//有功 谷
			else if((data_type.indexOf("有功") >= 0) && (data_type.indexOf("谷") >= 0)) {
				t_info.bd_zyj[0] = bd;
			}
		}
		
		return true;
	}
	
	private int getMeterId(int[] idx, int rtu_id, int mp_id) {
		if(jsMterParas == null || jsMterParas.length == 0) return -1;
		for(int i = 0; i < jsMterParas.length; i++) {
			if(jsMterParas[idx[i]].rtu_id == rtu_id && jsMterParas[idx[i]].mp_id == mp_id) {
				return idx[i];
			}
		}
		return -1;
	}
	
	private void setMulMp() {
		ConsInfo tmp = null;
		int[] mt_id = null;
		for(int i = 0; i < consList.size(); i++) {
			
			tmp = consList.get(i);
			
			if(tmp.mp_num > 1) {
				mt_id = findMeter(tmp.cons_no);
				for(int j = 0; j < tmp.mp_num; j++) {
					int id = getMeterId(mt_id, tmp.rtu_id, tmp.mp_ids[j]);
					tmp.bd_zyz[j] = jsMterParas[id].zyz;
					tmp.bd_zyj[j] = jsMterParas[id].zyj;
					tmp.bd_zyf[j] = jsMterParas[id].zyf;
					tmp.bd_zyp[j] = jsMterParas[id].zyp;
					tmp.bd_zyg[j] = jsMterParas[id].zyg;
					tmp.bd_zwz[j] = jsMterParas[id].zwz;
				}
			}
		}
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
			
			//有匹配用户信息。无表底信息或信息不全
			int kk = 0;
			for (kk = 0; kk < t_info.mp_num; kk++) {
				if (t_info.bd_zyz[kk] < 0 || 
					t_info.bd_zwz[kk] < 0) break;
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
	
	/**  读取金额的excel。保存到consList里。
	 * 
	 */
	private  void getFileYE(File myfile){
		
		String fileNm = "算费系统余额";			
		
		jxl.Workbook rwb = null;
		InputStream is = null;
		try {
			is = new FileInputStream(myfile);
			rwb = jxl.Workbook.getWorkbook(is);
			Sheet rs = rwb.getSheet(0);
			int rsRows = rs.getRows();//总行数
			
			int ye_title_lineno = WebConfig.gyMisJsConfig.hb_sg186.ye_title_lineno;
			
			//校验
			Cell[] titles = rs.getRow(ye_title_lineno);

			//用户编号
			String ye_cons_no = WebConfig.gyMisJsConfig.hb_sg186.ye_cons_no;
			int ye_cons_no_colno = getTitle(titles, ye_cons_no);
			if (ye_cons_no_colno < 0) {
				setTitleErr(fileNm, ye_cons_no, ye_title_lineno);
				return;
			}
			
			//用户名称
			String ye_cons_desc = WebConfig.gyMisJsConfig.hb_sg186.ye_cons_desc;
			int ye_cons_desc_colno = getTitle(titles, ye_cons_desc);
			if (ye_cons_desc_colno < 0) {
				setTitleErr(fileNm, ye_cons_desc, ye_title_lineno);
				return;
			}
			
			//预收金额
			String ye_remain = WebConfig.gyMisJsConfig.hb_sg186.ye_remain;
			int ye_remain_colno = getTitle(titles, ye_remain);
			if (ye_remain_colno < 0) {
				setTitleErr(fileNm, ye_remain, ye_title_lineno);
				return;
			}
			
			int startRow = ye_title_lineno + 1;//数据起始行
			
			String t_cons_no   = null;
			String t_cons_desc = null;
			String t_remain    = null;			
			Cell   t_cell      = null;
			
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
				
				int cons_idx = findConsByConsNo(t_cons_no);
				if (cons_idx < 0) continue;
				
				consList.get(cons_idx).remain = CommBase.strtof(t_remain);
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
			
			int bd_title_lineno = WebConfig.gyMisJsConfig.hb_sg186.bd_title_lineno;			
			
			Cell[] titles = rs.getRow(bd_title_lineno);
			
			//用户编号
			String bd_cons_no = WebConfig.gyMisJsConfig.hb_sg186.bd_cons_no;
			int bd_cons_no_colno = getTitle(titles, bd_cons_no);
			if (bd_cons_no_colno < 0) {
				setTitleErr(fileNm, bd_cons_no, bd_title_lineno);
				return;
			}
			
			//用户名称
			String bd_cons_desc = WebConfig.gyMisJsConfig.hb_sg186.bd_cons_desc;
			int bd_cons_desc_colno = getTitle(titles, bd_cons_desc);
			if (bd_cons_desc_colno < 0) {
				setTitleErr(fileNm, bd_cons_desc, bd_title_lineno);
				return;
			}
			
			//示数类型
			String bd_data_type = WebConfig.gyMisJsConfig.hb_sg186.bd_data_type;
			int bd_data_type_colno = getTitle(titles, bd_data_type);
			if (bd_data_type_colno < 0) {
				setTitleErr(fileNm, bd_data_type, bd_title_lineno);
				return;
			}
			
			//本次示数
			String bd_cur_data = WebConfig.gyMisJsConfig.hb_sg186.bd_cur_data;
			int bd_cur_data_colno = getTitle(titles, bd_cur_data);
			if (bd_cur_data_colno < 0) {
				setTitleErr(fileNm, bd_cur_data, bd_title_lineno);
				return;
			}
			
			//是否使用出厂编号
			boolean use_factoryf = true; //WebConfig.gyMisJsConfig.hb_sg186.bd_use_factnof;
			
			//出厂编号
			int bd_factory_no_colno = -1;			
			if (use_factoryf) {
				String bd_factory_no = WebConfig.gyMisJsConfig.hb_sg186.bd_factory_no;
				bd_factory_no_colno = getTitle(titles, bd_factory_no);
				if (bd_factory_no_colno < 0) {
					setTitleErr(fileNm, bd_factory_no, bd_title_lineno);
					return;
				}
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
				if (use_factoryf) {
				t_cell = rs.getCell(bd_factory_no_colno, i);	
				if (t_cell == null || t_cell.getContents() == null) {
					t_factoryno = "";
				}
				else {
					t_factoryno = t_cell.getContents();
					t_factoryno = t_factoryno.trim().replaceAll("\r", "").replaceAll("\n", "");
				}
				}
				
				setConsInfo(t_cons_no, t_cons_desc, t_data_type, t_cur_data, t_factoryno, use_factoryf);
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
		setMulMp();
		//检查设置参数信息状态1
		checkConsInfoValid1();
		
		//读取余额文件
		getFileYE(myFile);
		if(errFlag){
			request.setAttribute("errMsg", errMsg);
			return SDDef.SUCCESS;
		}
		
		//检查设置参数信息状态1
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
	
	/**把consList转化成json格式返回*/
	private  String toConsJsonStr(){
		if(consList.size() == 0) return SDDef.EMPTY;
		
		StringBuffer sbf = new StringBuffer();
		
		sbf.append(SDDef.JSONROWSTITLE);
		
		for(int i = 0 ; i < consList.size(); i++){
			ConsInfo ci = consList.get(i) ;
			
			sbf.append(SDDef.JSONLBRACES); 
			sbf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + i + SDDef.JSONNZDATA);					
			switch(ci.matchFlag){
			case CONS_OK:
				sbf.append(SDDef.JSONQUOT + ci.cons_name + SDDef.JSONCCM);
				break;
			case CONS_PARA:
			case CONS_PAYSTATE:
				sbf.append(SDDef.JSONQUOT + S_FONT_OTHER + ci.cons_name + E_FONT + SDDef.JSONCCM);
				break;
			case CONS_BD:
			case CONS_YE:
				sbf.append(SDDef.JSONQUOT + S_FONT_NODATA + ci.cons_name + E_FONT + SDDef.JSONCCM);
				break;
			}
			sbf.append(SDDef.JSONQUOT + ci.cons_no  	+ SDDef.JSONCCM);
			sbf.append(SDDef.JSONQUOT + ci.rtu_id  	+ SDDef.JSONCCM);
			sbf.append(SDDef.JSONQUOT + ci.zjg_id  	+ SDDef.JSONCCM);
			
			String bd = ci.bd_zyz[0] + "_" + ci.bd_zyz[1] + "_" + ci.bd_zyz[2]; 
			sbf.append(SDDef.JSONQUOT + bd 			+ SDDef.JSONCCM);
			
			bd = ci.bd_zwz[0] + "_" + ci.bd_zwz[1] + "_" + ci.bd_zwz[2]; 
			sbf.append(SDDef.JSONQUOT + bd  			+ SDDef.JSONCCM);
			
			sbf.append(SDDef.JSONQUOT + ci.remain  		+ SDDef.JSONCCM);
			sbf.append(SDDef.JSONQUOT + ci.matchFlag	+ SDDef.JSONCCM);
			sbf.append(SDDef.JSONQUOT + ci.mp_num + SDDef.JSONCCM);
			sbf.append(SDDef.JSONQUOT + (ci.mp_ids[0] + "_" + ci.mp_ids[1] + "_" + ci.mp_ids[2]) + SDDef.JSONCCM);		//mp_ids
			sbf.append(SDDef.JSONQUOT + (ci.zf_flag[0] + "" + ci.zf_flag[1] + "" + ci.zf_flag[2]) + SDDef.JSONCCM);	//正反相
			sbf.append(SDDef.JSONQUOT + ci.cacl_type + SDDef.JSONCCM);
			sbf.append(SDDef.JSONQUOT + ci.fee_type + SDDef.JSONCCM);
			
			bd = ci.bd_zyj[0] + "_" + ci.bd_zyj[1] + "_" + ci.bd_zyj[2];
			sbf.append(SDDef.JSONQUOT + bd  			+ SDDef.JSONCCM);	//有功尖
			
			bd = ci.bd_zyf[0] + "_" + ci.bd_zyf[1] + "_" + ci.bd_zyf[2];
			sbf.append(SDDef.JSONQUOT + bd  			+ SDDef.JSONCCM);	//有功峰
			
			bd = ci.bd_zyp[0] + "_" + ci.bd_zyp[1] + "_" + ci.bd_zyp[2];
			sbf.append(SDDef.JSONQUOT + bd  			+ SDDef.JSONCCM);	//有功平
			
			bd = ci.bd_zyg[0] + "_" + ci.bd_zyg[1] + "_" + ci.bd_zyg[2];
			sbf.append(SDDef.JSONQUOT + bd  			+ SDDef.JSONQBRRBCM);//有功谷
		}
		
		sbf.setCharAt(sbf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		sbf.append(SDDef.JSONRBRACES);
	
		
		return sbf.toString();
	}

	private JSRtuZjgPara[] loadJsRtuZjgPara()
	{
		JDBCDao j_dao 	= new JDBCDao();
		ResultSet rs 	= null;

		JSRtuZjgPara[] ret_para = null;
		String orgId 	= null;
		String fzmanId 	= null;
		
		if(params != null && !params.isEmpty()){
			String qx_params[] = params.split(SDDef.JSONCOMA);
			orgId 	= qx_params[0];
			fzmanId = qx_params[1];
		}
		StringBuffer sql_cmd = new StringBuffer();
//		String sql_cmd = "select a.busi_no, c.rtu_id, c.zjg_id, c.clp_num, c.clp_ids, c.zf_flag, d.cacl_type, d.feectrl_type,d.pay_type,e.cus_state " + 
//						 "from conspara as a, rtupara as b, zjgpara as c, zjgpay_para as d, zjgpay_state as e " + 
//						 "where b.app_type = 1 and b.cons_id = a.id and c.rtu_id = b.id and d.rtu_id=b.id and e.rtu_id=b.id and c.use_flag=1 and c.yff_flag=1 " + 
//						 "order by c.rtu_id, c.zjg_id";

		sql_cmd.append("select a.busi_no, c.rtu_id, c.zjg_id, c.clp_num, c.clp_ids, c.zf_flag, d.cacl_type, d.feectrl_type,d.pay_type,e.cus_state ");
		sql_cmd.append("from conspara as a, rtupara as b, zjgpara as c, zjgpay_para as d, zjgpay_state as e ");
		sql_cmd.append("where b.app_type = 1 and b.cons_id = a.id and c.rtu_id = b.id and d.rtu_id=b.id and e.rtu_id=b.id and c.use_flag=1 and c.yff_flag=1 ");
		
		if(orgId != null && !orgId.equals("-1") && !orgId.equals("null")){
			sql_cmd.append(" and a.org_id=" + orgId);
		}
		if(fzmanId != null && !fzmanId.equals("-1") && !fzmanId.equals("null")){
			sql_cmd.append(" and a.line_fzman_id=" + fzmanId);
		}
		sql_cmd.append(" order by c.rtu_id, c.zjg_id");
		
		try {
			rs = j_dao.executeQuery(sql_cmd.toString());
			rs.last();
			int num = rs.getRow();
			
			if (num > 0) ret_para = new JSRtuZjgPara[num];
			
			int idx = 0;
			rs.beforeFirst();
			String tmp = null;
			while(rs.next()){
				ret_para[idx] = new JSRtuZjgPara();
				ret_para[idx].cons_no  = rs.getString("busi_no");
				ret_para[idx].rtu_id   = rs.getInt("rtu_id");
				ret_para[idx].zjg_id   = rs.getShort("zjg_id");
				ret_para[idx].clp_num  = rs.getByte("clp_num");
				tmp = rs.getString("clp_ids");
				ret_para[idx].mp_ids = setClp(tmp, ret_para[idx].clp_num);
				ret_para[idx].zf_flag  = setZf(tmp, rs.getString("zf_flag"), ret_para[idx].clp_num);
				ret_para[idx].cacl_type = rs.getByte("cacl_type");
				ret_para[idx].feectrl_type = rs.getByte("feectrl_type");
				ret_para[idx].pay_type = rs.getByte("pay_type");
				ret_para[idx].cus_state = rs.getByte("cus_state");
				
				idx++;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			j_dao.closeRs(rs);
		}
		
		return ret_para;
	}
	
	private short[] setClp(String clp_ids, int clp_num) {
		if(clp_ids == null) return null;
		short[] ret = {-1, -1, -1};
		int ret_id = 0;
		for(int i = 0; i < clp_ids.length(); i++) {
			if(clp_ids.charAt(i) == '1') {
				ret[ret_id++] = (short)i;
			}
			if(ret_id >= clp_num)break;
		}
		
		return ret;
	}
	
	private byte[] setZf(String clp_ids, String zf_flag, int clp_num) {
		if(clp_ids == null || zf_flag == null) return null;
		byte[] ret = {0, 0, 0};
		int ret_id = 0;
		for(int i = 0; i < clp_ids.length(); i++) {
			if(clp_ids.charAt(i) == '1') {
				ret[ret_id++] = zf_flag.charAt(i) == '0' ? (byte)0 : (byte)1;
			}
			if(ret_id >= clp_num)break;
		}
		
		return ret;
	}
	
	private JSMeterPara[] loadJsMeterPara()
	{
		JDBCDao j_dao 	= new JDBCDao();
		ResultSet rs 	= null;

		JSMeterPara[] ret_para = null;
		String orgId 	= null;
		String fzmanId 	= null;
		
		if(params != null && !params.isEmpty()){
			String qx_params[] = params.split(SDDef.JSONCOMA);
			orgId 	= qx_params[0];
			fzmanId = qx_params[1];
		}
		StringBuffer sql_cmd = new StringBuffer();

		sql_cmd.append("select c.busi_no cons_no,b.rtu_id, b.mp_id, b.made_no from rtupara as a, meterpara as b,conspara c "); 
		sql_cmd.append("where a.app_type=1 and a.id=b.rtu_id and a.cons_id=c.id and b.use_flag=1 ");
		
		if(orgId != null && !orgId.equals("-1") && !orgId.equals("null")){
			sql_cmd.append(" and c.org_id=" + orgId);
		}
		if(fzmanId != null && !fzmanId.equals("-1") && !fzmanId.equals("null")){
			sql_cmd.append(" and c.line_fzman_id=" + fzmanId);
		}

		sql_cmd.append(" order by rtu_id, mp_id");
		try {
			rs = j_dao.executeQuery(sql_cmd.toString());
			rs.last();
			int num = rs.getRow();
			
			if (num > 0) ret_para = new JSMeterPara[num];
			
			int idx = 0;
			rs.beforeFirst();
			while(rs.next()){
				ret_para[idx] = new JSMeterPara();
				ret_para[idx].rtu_id   = rs.getInt("rtu_id");
				ret_para[idx].mp_id    = rs.getShort("mp_id");
				ret_para[idx].cons_no  = CommBase.CheckString(rs.getString("cons_no"));
				ret_para[idx].made_no  = CommBase.CheckString(rs.getString("made_no"));
				
				idx++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			j_dao.closeRs(rs);
		}
		
		return ret_para;
	}
	
	private boolean loadDBPara() {
		jsRtuZjgParas   = loadJsRtuZjgPara();
		jsMterParas = loadJsMeterPara();
		
		if (jsRtuZjgParas == null   || jsRtuZjgParas.length <= 0 ||
			jsMterParas == null || jsMterParas.length <= 0) {
			
			errFlag = true;
			errMsg  = "读取参数库失败...";			
			
			return false;
		}
		
		return true;
	}
	
	boolean getZjgMpTPara(int rtu_id, short zjg_id, double rate[], short plus_time[]) {
		JDBCDao j_dao 	= new JDBCDao();
		ResultSet rs 	= null;

		String sql_cmd = "select c.clp_num, c.clp_ids, c.zf_flag, e.plus_time from zjgpara as c, zjgpay_para as e " + 
						 "where c.rtu_id = e.rtu_id and c.zjg_id = e.zjg_id and c.rtu_id=" + rtu_id + " and c.zjg_id=" + zjg_id; 

		int    clp_num    = 0;
		String clp_ids    = "";
		String zf_flag    = "";
		short  plus_time1 = 0;						
		
		boolean validf    = false;
		
		double pt_ratio = 0;
		double ct_ratio = 0;
		
		try {
			rs = j_dao.executeQuery(sql_cmd);
			
			if (rs.next()) {
				clp_num      = rs.getByte("clp_num");
				clp_ids      = rs.getString("clp_ids");
				zf_flag      = rs.getString("zf_flag");
				plus_time1   = rs.getByte("plus_time");		
				
				validf = true;
			}
			
			if (validf) {
				short mp_ids1[] = new short[64];	
				byte  zj_flags1[] = new byte[64];
				ComntFunc.arraySet(mp_ids1, (short)0);
				ComntFunc.arraySet(zj_flags1, (byte)0);
				
				int r_clp_num = gGetMpids(clp_num, clp_ids, zf_flag, mp_ids1, zj_flags1);
				if (r_clp_num > 0) {
					String sql_cmd2 = "select pt_ratio, ct_ratio from mppara where rtu_id=" + rtu_id + " and id=" + mp_ids1[0];
					
					rs = j_dao.executeQuery(sql_cmd2);
					
					if (rs.next()) {
						pt_ratio  = rs.getDouble("pt_ratio");
						ct_ratio  = rs.getDouble("ct_ratio");
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			j_dao.closeRs(rs);
		}
		
		rate[0] = pt_ratio * ct_ratio; 
		plus_time[0] = plus_time1;
		
		return true;
	}
	
	/**根据户号读取用户信息。
	 * 用于单击一行后，在缴费结算界面的详细信息显示。调用GYoper的预付费状态查询后，不足的信息。从数据库查询补足。
	 * 
	 * @return
	 */
	@JSON(serialize = false)
	public String getOneCons(){
		
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		}
		
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String rtuId 	= jsonObj.getString("rtu_id");
		String zjgId	= jsonObj.getString("zjg_id");
		
		double t_rate[] = {0};
		short  plus_time[] = {0};
		
		getZjgMpTPara(CommBase.strtoi(rtuId), (short)CommBase.strtoi(zjgId), t_rate, plus_time);
		
		JDBCDao j_dao = new JDBCDao();
		
		String 	sql   = "select c.id cons_id,e.pay_add1,e.plus_time,a.id as rtu_id, a.asset_no as asset_no, z.zjg_id as zjg_id, z.describe as zjg_desc," +
		 				"m.describe as rm_desc," + 
		 				"a.factory as rtu_factory, a.made_no as rtu_madeno, a.prot_type as prot_type," + 
		 				"c.busi_no as busi_no, c.describe as cons_desc, c.addr as cons_addr, c.tel_no2 as cons_telno, o.describe as org_desc, f.describe as fzman_desc, a.rtu_model as rtu_model , s.cus_state, e.cb_dayhour " + 
		 				"from rtupara as a,conspara as c,dbo.zjgpay_para as e,zjgpara as z,orgpara as o,line_fzman f, zjgpay_state as s, rtumodel m " + 
		 				"where a.app_type = " + SDDef.APPTYPE_ZB + " and a.use_flag = 1 and a.id = z.rtu_id and a.cons_id = c.id and c.org_id = o.id and c.app_type = "+ SDDef.APPTYPE_ZB +" and z.use_flag = 1 " + 
		 				"and z.yff_flag = 1 and a.id = s.rtu_id and z.zjg_id = s.zjg_id and e.rtu_id = a.id and e.zjg_id = z.zjg_id and " + 
		 				"f.org_id=o.id and c.line_fzman_id=f.id and a.rtu_model=m.id and " +
		 				"z.rtu_id=" + rtuId + " and z.zjg_id=" + zjgId; 
		
		List<Map<String, Object>>	list = null;
		
		list = j_dao.result(sql);
		if(list.size()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		}
		
		JSONObject j_obj = new JSONObject();
		Map<String,Object> map=list.get(0);
		j_obj.put("cons_id", 	CommBase.CheckString(map.get("cons_id")));
		j_obj.put("fee_unit", 	CommBase.CheckString(map.get("zjg_desc")));	//总加组名称-费控单元
		j_obj.put("username", 	CommBase.CheckString(map.get("cons_desc")));//客户名称	 	
		j_obj.put("userno", 	CommBase.CheckString(map.get("busi_no")));//营业户号	 		
		j_obj.put("useraddr", 	CommBase.CheckString(map.get("cons_addr")));	//客户地址		
		j_obj.put("tel", 		CommBase.CheckString(map.get("cons_telno")));	//联系电话-tel2 移动手机号 				
		j_obj.put("factory", 	Rd.getDict(Dict.DICTITEM_METERFACTORY, map.get("rtu_factory")));//生产厂家
		
		j_obj.put("blv", 		t_rate[0]);	//默认使用第一块表的倍率
		j_obj.put("rtu_model", 	CommBase.CheckString(map.get("rm_desc")));//终端型号		
		j_obj.put("prot_type", 	CommBase.CheckString(map.get("prot_type")));//通讯规约
		
		j_obj.put("switch_type", plus_time[0] == 0 ? 0 : 1);
		j_obj.put("plus_width",  plus_time[0]);
		
		j_obj.put("cus_state", 	CommBase.CheckString(map.get("cus_state")));	//用户状态
		j_obj.put("cb_dayhour", CommBase.CheckString(map.get("cb_dayhour")));	//抄表日：每月  DD日HH时
		j_obj.put("jbf", 		CommBase.CheckString(map.get("pay_add1")));	//抄表日：每月  DD日HH时
		j_obj.put("plus_time", 	CommBase.CheckNum(map.get("plus_time")));	//脉冲宽度
		OnlineRtu.RTUSTATE_ITEM rtustate_item = OnlineRtu.getOneRtuState(CommBase.strtoi(rtuId));
		boolean onlineflag = false;
		if ((rtustate_item != null) && (rtustate_item.comm_state == 1)) onlineflag = true;
		
		j_obj.put("online", (onlineflag ? "在线" : "离线" ));//终端在线状态
		
		result = j_obj.toString();
		return SDDef.SUCCESS;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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
}
