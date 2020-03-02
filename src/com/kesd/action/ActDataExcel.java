package com.kesd.action;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.libweb.common.CommBase;
import com.opensymphony.xwork2.ActionSupport;

public class ActDataExcel extends ActionSupport {

	private static final long serialVersionUID = 2173536510754629929L;
	private InputStream excelStream;
	private String 		excPara;		//当前页参数
	private String 		colType;		//列数据类型
	private String 		header;			//列名
	private String 		footer;
	private String 		footerType;
	private String 		attachheader;	//
	private String 		hidecols;		//隐藏列：显示列号4,5,6|隐藏列号1,2,3	。不为空时，认为需要做隐藏列处理。复选框后那一列记为0列，开始取值
	private String 		mergeRows;		//合并行：值为需要合并行的列号
	
	private int 		vfreeze = 1;	//冻结行数
	private int 		hfreeze;		//冻结列数
	
	
	private String		filename = null;
	
	private int  		currentrow = 0; //当前行
	private boolean		chk_flag = true;
	
	public String execute() throws Exception {
		if(header == null || header.isEmpty()){
			return "fail";
		}
		if(excPara == null){	//去掉了为空条件 直接在浏览器输入act地址时 excPara == null
			return "fail";
		}
		
		Number number;
		Label label;
		
		WritableWorkbook workbook;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workbook = Workbook.createWorkbook(os);
		WritableSheet sheet = workbook.createSheet("Sheet1", 0);
		
		int[] strlen = setExcelTitle(sheet);
		int length = strlen.length;
		
		String col_type[] = colType.split(",");
		
		if(excPara.isEmpty()){
			sheet.getSettings().setVerticalFreeze(vfreeze);
			sheet.getSettings().setHorizontalFreeze(hfreeze);
			
			workbook.write();
			workbook.close();
			
			excelStream = new ByteArrayInputStream(os.toByteArray());
			os.close();
			
			return "excel";
		}else{
			excPara = URLDecoder.decode(excPara,"utf-8");
		}
		
		JSONObject jsonObj = JSONObject.fromObject(excPara);
		JSONArray rows    = jsonObj.getJSONArray("rows");
		
		int row = currentrow, col = 0;
		
		jxl.write.NumberFormat nf[] = new jxl.write.NumberFormat[length];
		WritableCellFormat wcf[] = new WritableCellFormat[length];
		
		for (int i = 0; i < length; i++) {
			if (col_type[i].startsWith("0.0")) {
				nf[i] = new jxl.write.NumberFormat(col_type[i]);
				wcf[i] = new WritableCellFormat(nf[i]);
				wcf[i].setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			}
		}
		
		jxl.write.WritableCellFormat wcsB = new  jxl.write.WritableCellFormat();
		wcsB.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
		wcsB.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
		
		Pattern pattern = Pattern.compile("<a.*?/a>");
		
		for(int j = 0; j < rows.size();j++){
			col = 0;
			jsonObj = JSONObject.fromObject(rows.get(j));
			JSONArray jrows = jsonObj.getJSONArray("data");
			
			int i = 0;
			if(chk_flag){
				i = 1;
			}
			
			for (; i < length; i++) {
				
				String datai = jrows.get(i).toString();
				
				//含有超链接代码时，去掉代码
				Matcher m = pattern.matcher(datai);
				if(m.find()){
					datai = datai.replaceAll("<a.*?>|</a>", "");
				}
				
				if(strlen[i] < datai.getBytes().length){
					strlen[i]=datai.getBytes().length;
				}
				
				if(col_type[i].equals("int")){
					if(datai.isEmpty()){
						sheet.addCell(new Label(col++, row, "",wcsB));
					}else{
						number = new Number(col++,row,Integer.parseInt(datai),wcsB);
						sheet.addCell(number);
					}
				}
				else if(col_type[i].startsWith("0.0")){
					if(datai.isEmpty()){
						sheet.addCell(new Label(col++, row, "",wcsB));
					}else{
						number = new Number(col++,row, Double.parseDouble(datai), wcf[i]); 
						sheet.addCell(number);
					}
				}
				else if(col_type[i].equals("str")){
					datai = datai.replaceAll("&nbsp;", " ");
					label = new Label(col++, row, datai,wcsB);
					sheet.addCell(label);
				}
				else {
					label = new Label(col++, row, datai,wcsB);
					sheet.addCell(label);
				}
			}
			row++;
		}
		
		//设置列宽
		jxl.CellView cv = new jxl.CellView();
		int tmp_col = 0;
		for (int i = (chk_flag ? 1 : 0); i < strlen.length; i++) {
			int len = 256 * strlen[i] + 256;
			cv.setSize(len);
			sheet.setColumnView(tmp_col++, cv);
		}
		
		if(footer != null && !footer.isEmpty()){
			//{rows:[{data:["a,0-2","b","c","d","e","f","g","h"]}]}  0-2表示合并第1-3列
			jsonObj = JSONObject.fromObject(footer);
			rows    = jsonObj.getJSONArray("rows");
			
			String[] ft = footerType.split(",");
			nf = new jxl.write.NumberFormat[ft.length];
			wcf = new WritableCellFormat[ft.length];
			
			Color color = Color.decode("#ffffbf");//从“#FFFFFF” 到java.awt.Color
			Colour colour = getNearestColour(color);
			for (int i = 0; i < ft.length; i++) {
				if (ft[i].startsWith("0.0")) {
					nf[i] = new jxl.write.NumberFormat(ft[i]);
					wcf[i] = new WritableCellFormat(nf[i]);
					wcf[i].setBackground(colour);
					wcf[i].setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
				}
			}
			
			jxl.write.WritableCellFormat wcsB1 = new  jxl.write.WritableCellFormat();
			wcsB1.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			wcsB1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			wcsB1.setBackground(colour);
			
			String tmp;
			
			for (int j = 0; j < rows.size(); j++) {
				jsonObj = JSONObject.fromObject(rows.get(j));
				JSONArray jrows = jsonObj.getJSONArray("data");
				col = 0;
				for (int i = 0; i < jrows.size(); i++) {
					
					tmp = jrows.getString(i);
					
					if(tmp.indexOf(",") >= 0 && tmp.indexOf("-") >= 0){	//需要合并
						
						String value = tmp.split(",")[0];
						String hb = tmp.split(",")[1];
						
						int col1 = Integer.parseInt(hb.split("-")[0]);
						int col2 = Integer.parseInt(hb.split("-")[1]);
						
						sheet.mergeCells(col1, row, col2, row);
						
						if(ft[i].equals("int")){
							if(value.isEmpty()){
								
								sheet.addCell(new Label(col++, row, "",wcsB1));
							}else{
								number = new Number(col++,row,Integer.parseInt(value),wcsB1);
								sheet.addCell(number);
							}
						}
						else if(ft[i].startsWith("0.0")){
							if(value.isEmpty()){
								sheet.addCell(new Label(col++, row, "",wcsB1));
							}else{
								number = new Number(col++,row, Double.parseDouble(value), wcf[i]); 
								sheet.addCell(number);
							}
						}
						else if(ft[i].equals("str")){
							value = value.replaceAll("&nbsp;", " ");
							label = new Label(col++, row, value, wcsB1);
							sheet.addCell(label);
						}
						
						col += (col2-col1);
					}else{
						
						String value = tmp;
						if(ft[i].equals("int")){
							if(value.isEmpty()){
								sheet.addCell(new Label(col++, row, "",wcsB1));
							}else{
								number = new Number(col++,row,Integer.parseInt(value),wcsB1);
								sheet.addCell(number);
							}
						}
						else if(ft[i].startsWith("0.0")){
							if(value.isEmpty()){
								sheet.addCell(new Label(col++, row, "",wcsB1));
							}else{
								number = new Number(col++,row, Double.parseDouble(value), wcf[i]); 
								sheet.addCell(number);
							}
						}
						else if(ft[i].equals("str")){
							value = value.replaceAll("&nbsp;", " ");
							label = new Label(col++, row, value, wcsB1);
							sheet.addCell(label);
						}
						
					}
					
				}
				
				row++;
			}
		}
		sheet.getSettings().setVerticalFreeze(vfreeze);
		sheet.getSettings().setHorizontalFreeze(hfreeze);
		
		//设置隐藏列。
		if(hidecols != null && !hidecols.isEmpty()){
			String colids[] = hidecols.substring(hidecols.indexOf("|")+1).split(",");
			for(int i = 0; i < colids.length ; i++){
				if(colids[i].isEmpty())continue;
				sheet.setColumnView(Integer.parseInt(colids[i]),0);//把隐藏列的宽度设置为0，看上去隐藏了。
			}
		}
		
		/*合并行*/
		if(mergeRows != null && !mergeRows.trim().isEmpty()) {
			mergeRows(sheet);
		}
		
		workbook.write();
		workbook.close();
		
		excelStream = new ByteArrayInputStream(os.toByteArray());
		os.close();
		
		return "excel";
	}
	
	private void mergeRows(WritableSheet sheet) throws Exception {
		
		JSONObject jsonObj = JSONObject.fromObject(excPara);
		JSONArray rows    = jsonObj.getJSONArray("rows");
		
		int len = rows.size();
		if(len <= 0) return;
		
		String last_value = "", cur_value = "";
		
		int eq_num = 1;
		int start_row = 2;
		if(attachheader == null || attachheader.isEmpty()) start_row = 1;
		
		int step = 0;
		
		//mergeRows格式 : {cols:[{col:1},{col:2,key_idx:[0,1]},{col:3}]}
		//key_idx表示每行id以“_”隔开的索引
		JSONObject mr_obj = JSONObject.fromObject(mergeRows);
		JSONArray cols    = mr_obj.getJSONArray("cols");
		
		boolean key_flag = false;
		
		for(int j = 0; j < cols.size(); j++) {
			
			jsonObj = JSONObject.fromObject(rows.get(0));
			JSONArray jrows = jsonObj.getJSONArray("data");
			
			key_flag = false;
			mr_obj = JSONObject.fromObject(cols.get(j));
			int merge_row = mr_obj.getInt("col");
			
			JSONArray ki = null;
			if(mr_obj.has("key_idx")) {
				ki = mr_obj.getJSONArray("key_idx");
			}
			
			if(ki != null && ki.size() > 0) key_flag = true;
			
			if(key_flag) {
				String id = jsonObj.getString("id");
				String ids[] = id.split("_");
				last_value = "";
				for(int t = 0; t < ki.size(); t++) {
					int tt = CommBase.strtoi(CommFunc.CheckString(ki.get(t)));
					last_value += ids[tt];
				}
			} else {
				last_value = jrows.getString(merge_row);
			}
			
			eq_num = 1;
			step = 0;
			
			for(int i = 1; i < len; i++) {
				jsonObj = JSONObject.fromObject(rows.get(i));
				JSONArray jrows1 = jsonObj.getJSONArray("data");
				cur_value = "";
				if(key_flag) {
					String id = jsonObj.getString("id");
					String ids[] = id.split("_");
					for(int t = 0; t < ki.size(); t++) {
						int tt = CommBase.strtoi(CommFunc.CheckString(ki.get(t)));
						cur_value += ids[tt];
					}
				} else {
					cur_value = jrows1.getString(merge_row);
				}
				
				if(!last_value.equals(cur_value)) {
					
					sheet.mergeCells(merge_row, start_row + step, merge_row, start_row + step + eq_num - 1);
					step += eq_num;
					
					eq_num = 0;
					last_value = cur_value;
				} else {
					if(i == len - 1) {
						sheet.mergeCells(merge_row, start_row + step, merge_row, start_row + step + eq_num);
					}
				}
				
				eq_num++;
			}
		}
		
	}
	
	private int[] setExcelTitle(WritableSheet sheet) {
		Label    label = null,label1 = null;
		
		String[] heads = null;
		String[] attachheads = null;
		try{
			heads = URLDecoder.decode(header,"utf-8").split(SDDef.SPLITCOMA);
			attachheads = URLDecoder.decode(attachheader,"utf-8").split(SDDef.SPLITCOMA);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		int      len    = heads.length-1;
		if(heads[len].equals("&nbsp;")){
			len--;
		}
		int[]    strlen = new int[len+1];
		for(int i=1;i<strlen.length;i++){
			strlen[i]=0;			
		}
		int      i     = len;
		
		//表头第一列为图片时 去掉第一列
		if (heads[0].indexOf("img") >= 0) {
			chk_flag = true;
			len--;
		} else {
			chk_flag = false;
		}
		
		int unitcolNum=0;
		boolean unitcolflag=false;
		int t = len;
		try{
			if(attachheads.length == heads.length){//两层表头
				for(; i >=0 ; i--) {
					WritableCellFormat wcf=new WritableCellFormat();
					wcf.setAlignment(jxl.format.Alignment.CENTRE);//单元格中的内容水平方向居中
					wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格中的内容垂直方向居中
					wcf.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
					
					if(attachheads[i].equals("#rspan")){//合并行
						strlen[i]=heads[i].getBytes().length;	
						sheet.mergeCells(t,0,t,1); 
						label = new Label(t,0,heads[i],wcf);
						sheet.addCell(label);
					}
					else {
						label1 = new Label(t, 1, attachheads[i],wcf);
						strlen[i]=attachheads[i].getBytes().length;
						sheet.addCell(label1);
						
						if (heads[i].equals("#cspan")) {
							if(unitcolflag==false){//第一个需要合并的列。
								unitcolNum = 1;	
								unitcolflag=true;
							}
							else {
								unitcolNum=unitcolNum+1;
							}						
						}
						else {
							if (unitcolflag==false){//不需要合并列
								label = new Label(t, 0, heads[i],wcf);
								sheet.addCell(label);	
							}
							else {//和并列
								sheet.mergeCells(t,0,t+unitcolNum,0); 
								label = new Label(t, 0, heads[i],wcf);
								sheet.addCell(label);
								unitcolflag=false;
							}
						}
					}
					t--;
				}
				currentrow = 2;
			}
			else {
				int tmp_len = 0;
				if(chk_flag)tmp_len = 1;
				
				for (; i >= tmp_len; i--) {
					strlen[i] = heads[i].getBytes().length;
					WritableCellFormat wcf=new WritableCellFormat();
					wcf.setAlignment(jxl.format.Alignment.CENTRE);	//单元格中的内容水平方向居中
					wcf.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
					label = new Label(t--, 0, heads[i],wcf);
					sheet.addCell(label);
				}
				currentrow = 1;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return strlen;
	}
	
	protected static Colour getNearestColour(Color awtColor) {
		Colour color = null;

		Colour[] colors = Colour.getAllColours();
		if ((colors != null) && (colors.length > 0)) {
			Colour crtColor = null;
			int[] rgb = null;
			int diff = 0;
			int minDiff = 999;

			for (int i = 0; i < colors.length; i++) {
				crtColor = colors[i];
				rgb = new int[3];
				rgb[0] = crtColor.getDefaultRGB().getRed();
				rgb[1] = crtColor.getDefaultRGB().getGreen();
				rgb[2] = crtColor.getDefaultRGB().getBlue();

				diff = Math.abs(rgb[0] - awtColor.getRed())
						+ Math.abs(rgb[1] - awtColor.getGreen())
						+ Math.abs(rgb[2] - awtColor.getBlue());

				if (diff < minDiff) {
					minDiff = diff;
					color = crtColor;
				}
			}
		}
		if (color == null)
			color = Colour.BLACK;
		return color;
	}
	
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public String getExcPara() {
		return excPara;
	}
	public void setExcPara(String excPara) {
		this.excPara = excPara;
	}
	public String getColType() {
		return colType;
	}
	public void setColType(String colType) {
		this.colType = colType;
	}

	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getAttachheader() {
		return attachheader;
	}
	public void setAttachheader(String attachheader) {
		this.attachheader = attachheader;
	}
	public int getVfreeze() {
		return vfreeze;
	}
	public void setVfreeze(int vfreeze) {
		this.vfreeze = vfreeze;
	}
	public int getHfreeze() {
		return hfreeze;
	}
	public void setHfreeze(int hfreeze) {
		this.hfreeze = hfreeze;
	}

	public String getFilename() {
		DateFormat f = null;
		Date date = new Date();
		
		if(filename == null || filename.isEmpty()){
			f = new SimpleDateFormat("yyyyMMddHHmmss");
			filename = f.format(date) + ".xls";
		}else{
			if(filename.indexOf(".xls") < 0) {
				filename += ".xls";
			}
			/*
			f = new SimpleDateFormat("yyyyMMdd");
			filename += "-" + f.format(date) + ".xls";
			*/
		}
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setFooterType(String footerType) {
		this.footerType = footerType;
	}

	public String getFooterType() {
		return footerType;
	}

	
	public String getHidecols() {
		return hidecols;
	}

	
	public void setHidecols(String hidecols) {
		this.hidecols = hidecols;
	}

	public void setMergeRows(String mergeRows) {
		this.mergeRows = mergeRows;
	}

	public String getMergeRows() {
		return mergeRows;
	}

	

}
