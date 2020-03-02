package com.kesd.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxl.read.biff.BiffException;

import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.libweb.dao.JDBCDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class ActImportMeterData extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 112730390137951106L;
	
	private String name;
//	private String consname1;
//	private String orgname1;
	private File fileName;
	
	
	public String execute() throws Exception{
		
		Workbook workbook = getWorkBook(fileName); 
		
		List<String[]> list = new ArrayList<String[]>();
		
		if(workbook != null){	
			for(int sheetNum =0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
				
				Sheet sheet = workbook.getSheetAt(sheetNum);
				if(sheet == null){
					continue;
				}
				
				int firstRowNum = sheet.getFirstRowNum();
				
				int lastRowNum = sheet.getLastRowNum();
				
				for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){
					Row row = sheet.getRow(rowNum);
					if(row == null){
						continue;
					}
					int firstCellNum = row.getFirstCellNum();
					
					int lastCellNum =  row.getLastCellNum();//.getPhysicalNumberOfCells();
					
					String[] cells = new String[row.getLastCellNum()];
					
					for(int cellNum = firstCellNum;cellNum < lastCellNum;cellNum++){
						Cell cell = row.getCell(cellNum);
						
						cells[cellNum] = getCellValue(cell);
					}
					list.add(cells);
				}
			}
			workbook.close();
		}

		String  resultdata =setGridData(list);
		
		
		HttpServletRequest request = ServletActionContext.getRequest();

		request.setAttribute("resultdata", resultdata);
		return "success";
	}	

	public String getCellValue(Cell cell){
		String cellValue = "";
		if(cell == null){
			return cellValue;
		}
		
		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
		
		switch(cell.getCellType()){
			case Cell.CELL_TYPE_NUMERIC:
				cellValue = String.valueOf(cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				cellValue = String.valueOf(cell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				cellValue = String.valueOf(cell.getCellFormula());
				break;
			case Cell.CELL_TYPE_BLANK:
				cellValue = "";
				break;
			case Cell.CELL_TYPE_ERROR:
				cellValue = "非法字符";
				break;
			default :
				cellValue = "未知类型";
				break;
		}
		return cellValue;
		
	}
	
	public String setGridData(List<String[]> list) {
		
		String result = "";
		JSONObject rowJson   = new JSONObject();
		JSONObject dataJson  = new JSONObject();
		JSONArray  dataArray = new JSONArray();
		JSONArray  rowArray  = new JSONArray();
		
		for(int i = 0;i<list.size();i++){
			dataJson.put("id", i+1);
			
			dataArray.add("");			//默认选中
//			dataArray.add(SDDef.EMPTY);	//导入结果，默认为空
			
			for (int j = 0; j < list.get(i).length; j++) {
				dataArray.add(CommFunc.objectToString(list.get(i)[j].toString().trim().replace("\"", "").replace("\'", "")));
			}
			dataJson.put("data", dataArray);
			rowArray.add(dataJson);
			dataArray.clear();
			dataJson.clear();
			
			
		}
		
		rowJson.put("rows", rowArray);
		result = rowJson.toString();
		
		return result; 
	}
	
	public Workbook getWorkBook(File file) {
		
		Workbook workbook = null;
		
		try{
			
			InputStream is=	new FileInputStream(file);
			
			if(name.endsWith("xls")){
				workbook = new HSSFWorkbook(is); 
			}else if(name.endsWith("xlsx")){
				workbook = new XSSFWorkbook(is); 
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return workbook; 
	}
	
	public File getFileName() {
		return fileName;
	}
	public void setFileName(File fileName) {
		this.fileName = fileName;
	}

//	public String getConsname1() {
//		return consname1;
//	}
//
//	public void setConsname1(String consname) {
//		this.consname1 = consname;
//	}	
//	
//	public String getOrgname1() {
//		return orgname1;
//	}
//
//	public void setOrgname1(String orgname) {
//		this.orgname1 = orgname;
//	}	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}		
}
