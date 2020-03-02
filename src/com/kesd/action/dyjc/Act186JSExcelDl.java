package com.kesd.action.dyjc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kesd.util.Rd;
import javax.servlet.http.HttpServletRequest;

import jxl.Cell;
import jxl.Sheet;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.common.WebConfig;
import com.kesd.common.YFFDef;
import com.kesd.dbpara.YffRatePara;
import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;

public class Act186JSExcelDl {

	private static final long serialVersionUID = 1L;

	private File 	myFileDL;		//电量
	
	
	private String 	contentType;
	private String 	fileName;
	private String 	errMsg;			//错误信息。	
	private boolean errFlag;		//是否发生错误。主要处理：上传文件格式不正确。用户信息0匹配。读取文件异常
	private String  result; 
	private String  params;			//权限参数
	
	private static final byte MATCH_OK  			= 0;	//有匹配用户信息,可以结算。
	private static final byte MATCH_PARA			= 1;	//没有找到匹配的档案
	private static final byte MATCH_NOFIND  		= 2;	//无有效费率
	private static final byte MATCH_NOJT  			= 3;	//费率
	private static final byte MATCH_USEDL 			= 4;	//用电量 售电量不匹配
	
	private static final String FONT_OTHER 	= "<font color=#626262 >";//非电量费控-灰
	private static final String FONT_NODATA = "<font color=#FF0000 >";//无匹配项-红色
	private static final String FONT 		= "</font>";
	
	public static class ConsInfo {
		public String 		consNo   	= null;				//户号
		public String 		consName 	= null;				//用户名
		public double 		totUseDl   	= 0.0;				//
		
		public double 		totBuyDl   	= 0.0;				//
		public int 			rtuId		= -1;				//对应的终端ID
		public short 		mpId		= -1;				//主表
		public String 		mdesc		= null;
		public String 		feedesc		= null;
		public int	 		feeid		= -1;
		public int			jtchgYmd	= -1;				//
		public double 		jsMoney   	= 0.0;				//
		
		public byte 		matchFlag  	= MATCH_PARA;		//是否有匹配:0可以结算，1.其他费控类型，2 无匹配项
				
		public static ConsInfo makeNewConsInfo(String cons_no, String cons_desc, double totUseDl) {
			ConsInfo cons_info = new ConsInfo();

			cons_info.consNo   = cons_no;
			cons_info.consName = cons_desc;
			cons_info.totUseDl = totUseDl;

			return cons_info;
		}
	}
	
	class JtResetval{
		public int 			rtuId		= -1;				//对应的终端ID
		public short 		mpId		= -1;				//主表
		public String 		mdesc   	= null;				//
		public int			jtchgYmd	= -1;				//		
		public String 		consNo   	= null;				//户号
		public double 		totBuyDl   	= -1;				//
		public short		feeproj_id	= -1;
	}
	
	public List<ConsInfo> consList = new ArrayList<ConsInfo>();	
	Map<String, JtResetval> jtbuydl_map = new HashMap<String, JtResetval>();

	public String execute() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		if(myFileDL == null ){
			request.setAttribute("errMsg", "上传文件不能为空");
			return SDDef.SUCCESS;
		}
		
		if ((params == null) || (params.isEmpty())) {
			request.setAttribute("errMsg", "上传文件参数错误");
			return SDDef.SUCCESS;
		}
		
		String qx_params[] = params.split(SDDef.JSONCOMA);
		if (qx_params.length < 3) {
			request.setAttribute("errMsg", "上传文件参数个数错误");
			return SDDef.SUCCESS;
		}	
		
		errFlag = false;
		errMsg  = "";
		result  = "";
		
		consList = new ArrayList<ConsInfo>();		

		//读取表底信息文件
		getFileDl(myFileDL);
		if(errFlag){
			request.setAttribute("errMsg", errMsg);
			return SDDef.SUCCESS;
		}
		
		
		loadJtChgRcd();
		if (errFlag) {
			request.setAttribute("errMsg", errMsg);
			return SDDef.SUCCESS;
		}
		
		MatchRecord();
		
		//检查设置参数信息状态1
		calc_check_Valid1();
		if (errFlag) {
			request.setAttribute("errMsg", errMsg);
			return SDDef.SUCCESS;
		}

		toConsJsonStr();

		if(errFlag){
			request.setAttribute("errMsg", errMsg);
			return SDDef.SUCCESS;
		}		
		
		request.setAttribute("errMsg", "");
		request.setAttribute("resultdata", result);
		
		return SDDef.SUCCESS;
	}
	
	private void getFileDl(File myfile ){
		
		jxl.Workbook rwb = null;
		InputStream is = null;
		
		String fileNm = "年阶梯用电量";		
		
		try {
			is = new FileInputStream(myfile);
			rwb = jxl.Workbook.getWorkbook(is);
			Sheet rs = rwb.getSheet(0);
			int rsRows = rs.getRows();//总行数
			
			int bd_title_lineno = WebConfig.dyDlMisJsConfig.hndl_sg186.dl_title_lineno;			
			
			Cell[] titles = rs.getRow(bd_title_lineno);
			
			//用户编号
			String dl_cons_no = WebConfig.dyDlMisJsConfig.hndl_sg186.dl_cons_no;
			int bd_cons_no_colno = getTitle(titles, dl_cons_no);
			if (bd_cons_no_colno < 0) {
				setTitleErr(fileNm, dl_cons_no, bd_title_lineno);
				return;
			}
			
			//用户名称
			String dl_cons_desc = WebConfig.dyDlMisJsConfig.hndl_sg186.dl_cons_desc;
			int bd_cons_desc_colno = getTitle(titles, dl_cons_desc);
			if (bd_cons_desc_colno < 0) {
				setTitleErr(fileNm, dl_cons_desc, bd_title_lineno);
				return;
			}

			//本次示数
			String dl_cur_data = WebConfig.dyDlMisJsConfig.hndl_sg186.dl_cur_data;
			int bd_cur_data_colno = getTitle(titles, dl_cur_data);
			if (bd_cur_data_colno < 0) {
				setTitleErr(fileNm, dl_cur_data, bd_title_lineno);
				return;
			}
			
			int startRow = bd_title_lineno + 1;//数据起始行
			
			String t_cons_no   = null;
			String t_cons_desc = null;
			String t_cur_data  = null;
			Cell   t_cell      = null;
			
			//读取excel内容，保存到consList。
			for(int i = startRow; i < rsRows; i++ ){
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
				
				//总用电量
				t_cell = rs.getCell(bd_cur_data_colno, i);	
				if (t_cell == null || t_cell.getContents() == null) {
					t_cur_data = "";
				}
				else {
					t_cur_data = t_cell.getContents();
					t_cur_data = t_cur_data.trim().replaceAll("\r", "").replaceAll("\n", "");
				}
		
				//用户编号，用户名称，总用电量
				ConsInfo t_info = ConsInfo.makeNewConsInfo(t_cons_no, t_cons_desc, CommBase.strtof(t_cur_data));
				consList.add(t_info);
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

	private boolean loadJtChgRcd()
	{
		JDBCDao j_dao 	= new JDBCDao();
		ResultSet rs 	= null;
		String orgId 	= null;
		String fzmanId 	= null;
		int    jtchgymd = 20141215;
		
		String qx_params[] = params.split(SDDef.JSONCOMA);
		orgId 		= qx_params[0];
		fzmanId 	= qx_params[1];
		jtchgymd	= CommBase.strtoi(qx_params[2]);

		StringBuffer sql_cmd = new StringBuffer();
		sql_cmd.append("select b.rtu_id,b.mp_id,m.describe,b.date,b.cons_no,b.jt_total_dl,mp.feeproj_id "); 
		sql_cmd.append("from yddataben.dbo.MpJtReset" + jtchgymd /10000 + " b,ydparaben.dbo.mppay_para as mp, ydparaben.dbo.mppay_state as ms,ydparaben.dbo.rtupara as r, ydparaben.dbo.meterpara m, ydparaben.dbo.conspara  as c "); 
		sql_cmd.append("where b.rtu_id = r.id and b.rtu_id = mp.rtu_id and b.rtu_id = ms.rtu_id and b.rtu_id = m.rtu_id and b.mp_id = mp.mp_id and b.mp_id = ms.mp_id and b.mp_id = m.mp_id and r.cons_id = c.id ");
		sql_cmd.append("and b.date = " + jtchgymd + " and mp.cacl_type =3 and feectrl_type = 0 and ms.cus_state = 1 ");

		if(orgId != null && !orgId.equals("-1") && !orgId.equals("null")){
			sql_cmd.append(" and c.org_id=" + orgId);
		}
		if(fzmanId != null && !fzmanId.equals("-1") && !fzmanId.equals("null")){
			sql_cmd.append(" and c.line_fzman_id=" + fzmanId);
		}
				
		try {
			rs = j_dao.executeQuery(sql_cmd.toString());
		
			jtbuydl_map.clear();
			JtResetval temp = null;
			while(rs.next()){
				temp = new JtResetval();
				temp.rtuId  	= rs.getInt("rtu_id");
				temp.mpId   	= rs.getShort("mp_id");
				temp.mdesc 		= CommFunc.CheckString(rs.getString("describe"));
				temp.jtchgYmd 	= rs.getInt("date");
				temp.consNo 	= CommFunc.CheckString(rs.getString("cons_no"));
				temp.totBuyDl	= rs.getDouble("jt_total_dl");
				temp.feeproj_id	= rs.getShort("feeproj_id");
				
				jtbuydl_map.put(temp.consNo, temp);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			
			errFlag = true;
			errMsg  = "读取阶梯切换记录异常";
			
			return false;
		}
		finally {
			j_dao.closeRs(rs);
		}
		return true;
	}
	
	private boolean MatchRecord()
	{
		ConsInfo t_info = null;
		for (int cli = 0; cli < consList.size(); cli++){
			t_info = consList.get(cli);
			
			
			if(jtbuydl_map.containsKey(t_info.consNo)){
				t_info.rtuId 	= jtbuydl_map.get(t_info.consNo).rtuId;
				t_info.mpId 	= jtbuydl_map.get(t_info.consNo).mpId;
				t_info.mdesc 	= jtbuydl_map.get(t_info.consNo).mdesc;				
				t_info.jtchgYmd	= jtbuydl_map.get(t_info.consNo).jtchgYmd;
				t_info.totBuyDl	= jtbuydl_map.get(t_info.consNo).totBuyDl;
				t_info.feeid	= jtbuydl_map.get(t_info.consNo).feeproj_id;
				t_info.matchFlag = MATCH_OK;
			}
			else {
				t_info.matchFlag = MATCH_PARA;
			}
		}
		return true;
	}
		
	private void calc_check_Valid1() {
		
		List<YffRatePara> yfflist = Rd.getYffRate();
		if (yfflist.size() < 1) {
			errFlag = true;
			errMsg  = "读取费率异常";	
			return;
		}

		ConsInfo t_info = null;
		for (int cli = 0; cli < consList.size(); cli++) {
			t_info = consList.get(cli);
			
			if (t_info.matchFlag != MATCH_OK) continue;
		
			int i = 0;
			for (i = 0; i < yfflist.size(); i++) {
				if (t_info.feeid == yfflist.get(i).getId()) break;
			}
			if (i >= yfflist.size()) {
				t_info.matchFlag = MATCH_NOFIND;
				continue;	
			}
			t_info.feedesc = yfflist.get(i).getDescribe();
			
			if (((yfflist.get(i).getFeeType() != SDDef.YFF_FEETYPE_JTFL) || (yfflist.get(i).getRatejType() != SDDef.YFF_RATETYPE_YEAR)) &&
				(yfflist.get(i).getFeeType() != SDDef.YFF_FEETYPE_MIXJT) || (yfflist.get(i).getRatehjType() != SDDef.YFF_RATETYPE_YEAR)) {
				t_info.matchFlag = MATCH_NOJT;
				continue;	
			}

			Byte feetype = 0;
			int ratej_num = 3;
			double jtfee[] = {-1, -1, -1,-1,-1};
			double jtdlmax[] = {-1, -1, -1,-1}; 
			double  totUseDl = 0, totBuyDl = 0;
			boolean end_flag = false;
			
			if ((yfflist.get(i).getFeeType() == SDDef.YFF_FEETYPE_JTFL) && (yfflist.get(i).getRatejType() == SDDef.YFF_RATETYPE_YEAR)) {
				feetype = SDDef.YFF_FEETYPE_JTFL;
				ratej_num = yfflist.get(i).getRatejNum();
				jtfee[0] = yfflist.get(i).getRatejR1();

				if (ratej_num >= 2) {
					jtfee[1] = yfflist.get(i).getRatejR2();
					jtdlmax[0] = yfflist.get(i).getRatejTd1()* 12;
				}
				if (ratej_num >= 3) {
					jtfee[2] = yfflist.get(i).getRatejR3();
					jtdlmax[1] = yfflist.get(i).getRatejTd2() * 12;
					
				}
				if (ratej_num >= 4) {
					jtfee[3] = yfflist.get(i).getRatejR4();
					jtdlmax[2] = yfflist.get(i).getRatejTd3()* 12;
				}

				totUseDl = t_info.totUseDl;
				totBuyDl = t_info.totBuyDl;
			}
			else if ((yfflist.get(i).getRatehjType() == SDDef.YFF_FEETYPE_MIXJT) && (yfflist.get(i).getRatehjType() == SDDef.YFF_RATETYPE_YEAR)) {
				feetype =  SDDef.YFF_FEETYPE_MIXJT;
				ratej_num = yfflist.get(i).getRatehjNum();
				jtfee[0] = yfflist.get(i).getRatehjHr1R1();
				
				if (ratej_num >= 2) {
					jtfee[1] = yfflist.get(i).getRatehjHr1R2();
					jtdlmax[0] = yfflist.get(i).getRatehjHr1Td1()* 12;
				}
				if (ratej_num >= 3) {
					jtfee[2] = yfflist.get(i).getRatehjHr1R3();
					jtdlmax[1] = yfflist.get(i).getRatehjHr1Td2() * 12;
				}
				if (ratej_num >= 4) {
					jtfee[3] = yfflist.get(i).getRatehjHr1R4();
					jtdlmax[2] = yfflist.get(i).getRatehjHr1Td3()* 12;
				}

				totUseDl = t_info.totUseDl * yfflist.get(i).getRatehjBl1() /100.0;
				totBuyDl = t_info.totBuyDl * yfflist.get(i).getRatehjBl1() /100.0;
			}
			
			
			if (totUseDl > totBuyDl) {
				t_info.matchFlag = MATCH_USEDL;
				continue;	
			}
		
			double beginval = 0.0; double endval	= 0.0;
			t_info.jsMoney = 0;
			for (i = 0; i < ratej_num; i++) {				//阶梯费率个数 - 1 = 阶梯梯度数 
				beginval = 0.0; endval	= 0.0;

				if (i < ratej_num - 1){
					if (totUseDl >= jtdlmax[i])	continue;
				}

				if (i == 0){
					beginval = totUseDl;
				}
				else {
					if (totUseDl < jtdlmax[i-1]){
						beginval = jtdlmax[i-1];
					}
					else {
						beginval = totUseDl;
					}
				}

				if (i == ratej_num - 1){
					endval		 = totBuyDl;
					end_flag	 = true;
				}
				else {
					if (totBuyDl < jtdlmax[i]) {
						endval	 = totBuyDl;
						end_flag = true;
					}
					else {
						endval	 =  jtdlmax[i];
					}
				}

				t_info.jsMoney += 	(endval- beginval) * (jtfee[i] - jtfee[0]);
				if (end_flag) break;
			}

		}
		
	}

	private  boolean toConsJsonStr() throws Exception {
		
		if(consList.size() == 0) return false;
		
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		JSONArray  j_array1 = new JSONArray();
		JSONArray  j_array2 = new JSONArray();
		int index = 1;
				
		for(int i = 0 ; i < consList.size(); i++){
			ConsInfo ci = consList.get(i) ;

			json1.put("id", index);

			j_array1.add(index++);								//序号
			j_array1.add(CommFunc.CheckString(ci.consName));	
			j_array1.add(CommFunc.CheckString(ci.consNo));		
			j_array1.add(CommFunc.CheckString(CommFunc.round(ci.totUseDl,2)));

			switch(ci.matchFlag){
			case MATCH_PARA:
				j_array1.add(CommFunc.CheckString(FONT_NODATA + "不能匹配" + FONT));
				break;
			case MATCH_NOFIND:
				j_array1.add(CommFunc.CheckString(FONT_NODATA + "费率方案不存在" + FONT));
				break;
			case MATCH_NOJT:
				j_array1.add(CommFunc.CheckString(FONT_NODATA + "非年阶梯费率方案" + FONT));
				break;
			case MATCH_USEDL:
				j_array1.add(CommFunc.CheckString(FONT_NODATA + "用电量售电量不匹配" + FONT));
				break;	
			default:
				j_array1.add("匹配");
				break;
			}

			j_array1.add(CommFunc.CheckString(ci.mdesc));
			j_array1.add(CommFunc.CheckString(ci.feedesc));
			j_array1.add(CommFunc.CheckString(CommFunc.round(ci.totBuyDl,2)));
			j_array1.add(CommFunc.CheckString(CommFunc.round(ci.jsMoney,2)));
			j_array1.add(CommFunc.CheckString(CommFunc.FormatToYMD(ci.jtchgYmd)));
			j_array1.add(CommFunc.CheckString(ci.matchFlag));
			j_array1.add(CommFunc.CheckString(ci.rtuId));
			j_array1.add(CommFunc.CheckString(ci.mpId));
			j_array1.add("");										//最后一行空格
					
			json1.put("data", j_array1);
			j_array2.add(json1);
			j_array1.clear();
			json1.clear();
			
		}

		json2.put("rows", j_array2);
		result = json2.toString();

		return true;
	}
	
	private void setTitleErr(String fileNm, String chkText, int title_lineno) {
		errFlag = true;
		errMsg  = fileNm + "文件内容错误: 没有在第" + title_lineno + "行的单元格找到标题[" + chkText + "]。";
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

	public File getMyFileDL() {
		return myFileDL;
	}

	public void setMyFileDL(File myFileDL) {
		this.myFileDL = myFileDL;
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
	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public boolean isErrFlag() {
		return errFlag;
	}

	public void setErrFlag(boolean errFlag) {
		this.errFlag = errFlag;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}	
}

