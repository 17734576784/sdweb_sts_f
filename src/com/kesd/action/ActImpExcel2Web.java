package com.kesd.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import jxl.Cell;
import jxl.Sheet;
import jxl.read.biff.BiffException;
import org.apache.struts2.ServletActionContext;
import com.kesd.common.SDDef;
import com.opensymphony.xwork2.ActionSupport;

public class ActImpExcel2Web extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private File myFile;
	private String imageFileName;

	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String resultdata = show(myFile);
		request.setAttribute("resultdata", resultdata);
		return SUCCESS;
	}

	private String show(File myfile) {// 从*.xls中读取数据返回json字符串。
		StringBuffer sbf = new StringBuffer();
		String result = null;
		String temp = null;
		jxl.Workbook rwb = null;
		InputStream is = null;
		try {
			is = new FileInputStream(myfile);
			rwb = jxl.Workbook.getWorkbook(is);
			Sheet rs = rwb.getSheet(0);
			int rsColumns = rs.getColumns();// 获取Sheet表中所包含的总列数
			int rsRows = rs.getRows();// 获取Sheet表中所包含的总行数
			sbf.append(SDDef.JSONROWSTITLE);
			for (int i = 2; i < rsRows-1; i++) {// 从第2行开始取得数据内容。（跳过第一行标题）
				
				if(isEmpty(rs, i)) break;
				
				sbf.append(SDDef.JSONLBRACES);
				sbf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + i + SDDef.JSONDATA1);
				
				for (int j = 0; j < rsColumns; j++) {
					Cell cell = rs.getCell(j, i);// 获取指定单元格的对象引用
					temp = cell.getContents().toString().replaceAll("\r", "").replaceAll("\n", "").replaceAll("\"","”");
					temp = temp.replace("(", "-").replace(")", "");
					sbf.append(SDDef.JSONQUOT + temp + SDDef.JSONCCM);
				}
				sbf.setCharAt(sbf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
				sbf.append(SDDef.JSONRBCM);
			}
			sbf.setCharAt(sbf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			sbf.append(SDDef.JSONRBRACES);
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		result = sbf.toString();
		return result;
	}

	private boolean isEmpty(Sheet rs, int row) {
		int rsColumns = rs.getColumns();
		boolean ret = true;
		for (int j = 0; j < rsColumns; j++) {
			Cell cell = rs.getCell(j, row);// 获取指定单元格的对象引用
			if(cell.getContents() != null && !cell.getContents().trim().isEmpty()) {
				ret = false;
				break;
			}
		}
		return ret;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public String getImageFileName() {
		return imageFileName;
	}
}
