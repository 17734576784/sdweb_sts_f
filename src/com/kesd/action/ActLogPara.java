package com.kesd.action;

import java.sql.ResultSet;
import java.sql.SQLException; 
import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;

public class ActLogPara extends ActionSupport {

	private static final long serialVersionUID = -224733026026518118L;
	
	private String result;
	private String value;
	private String sdate;
	private String edate;
	private String stime;
	private String etime;
	private String optype;
	private String shm;
	private String ehm;
	
	private int no = 0;
	
	public String delOrgParaById()
	{
		return SDDef.SUCCESS;
	}
	
	public String execute() throws Exception{
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		String username = CommFunc.getUser().getName().trim();
		
		StringBuffer ret_buf = new StringBuffer();
		ret_buf.append(SDDef.JSONROWSTITLE);
		
		int syear = Integer.parseInt(sdate.substring(0,4));
		int eyear = Integer.parseInt(edate.substring(0,4));
		
		int dateS = Integer.parseInt(sdate);
		int dateE = Integer.parseInt(edate);
		
		int timeS = Integer.parseInt(stime)*100;
		int timeE = Integer.parseInt(etime)*100 + 59;
		
		String first_time = "0";
		String last_time  = "235959";
		String first_day  = "0101";
		String last_day   = "1231";
		
		int tab_num = 0;
		
		tab_num = eyear - syear + 1;
		String con_sql = "";
		for (int i = 0; i < tab_num; i++) { 	//tab_num 表个数
			
			if(tab_num > 1){		
				if(i == 0){
					dateE = syear*10000  + Integer.parseInt(last_day);
					timeE = Integer.parseInt(last_time);
				}
				else if (i==tab_num-1){
					dateS = syear*10000 + Integer.parseInt(first_day);
					timeS = Integer.parseInt(first_time);
					
					dateE = Integer.parseInt(edate);
					timeE = Integer.parseInt(etime)*100;
				}
				else {
					dateS = syear*10000 + Integer.parseInt(first_day);
					timeS = Integer.parseInt(first_time);
					
					dateE = syear*10000  + Integer.parseInt(last_day);
					timeE = Integer.parseInt(last_time);
				}
			}
			if(dateS == dateE){	//时间条件	
				con_sql = " and o.date=" + dateS + " and o.time>=" + timeS + " and o.time<=" + timeE;
			}else if(dateE - 1 == dateS){
				con_sql = " and ((o.date=" + dateS + " and o.time>=" + timeS + ") or (o.date=" + dateE + " and o.time<=" + timeE + "))";
			}else{
				con_sql = " and ((o.date=" + dateS + " and o.time>=" + timeS + ") or (o.date=" + dateE + " and o.time<=" + timeE + ") or (o.date>"+dateS+" and o.date<"+dateE+"))";
			}
			String table = "operlog" + syear ;
			syear++;
			ResultSet rs = null;
			String sql = "";
			JDBCDao j_dao = new JDBCDao();
			try{	//查询data库里operlog表
				sql = "select count(*) from yddataben.dbo.sysobjects where name='" + table + "'";
				rs = j_dao.executeQuery(sql);
				if(rs.next()){
					if(rs.getInt(1) == 0){
						j_dao.closeRs(rs);
						continue;
					}
					j_dao.closeRs(rs);
				}
				
				table = "yddataben.dbo." + table;
				String con_opername = "", con_opertype = "";
				if(result.equals("-1")){	//操作员姓名下拉框 -1时表示全部
					con_opername = "";
				}else{
					con_opername = " and o.oper_id ="+ result;
				}
				if(optype.equals("-1")){	//操作类型下拉框 -1时表示全部
					con_opertype = "";
				}else{
					con_opertype = " and o.opertype ="+ optype;
				}
				String tmp = "";
				if(!username.equals(SDDef.ADMIN)){
					tmp = " and u.name='"+username+"'";
				}
				sql = "select u.name,u.describe operdesc,o.date,o.time,o.opertype,o.oper_info from " + table + " o,ydparaben.dbo.YffManDef u where o.oper_id=u.id "+ tmp + con_opertype + con_opername + con_sql;
				
				sql += " order by o.date,o.time";
				rs = j_dao.executeQuery(sql);
				while(rs.next()){					
					no++;
					if(no >= 10000)break;					
					ret_buf.append(SDDef.JSONLBRACES);
					ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + no+"_"+rs.getInt("date")+"_" +rs.getInt("time")+ SDDef.JSONNZDATA);
					ret_buf.append(SDDef.JSONQUOT + no                         + SDDef.JSONCCM);
					ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(rs.getString("operdesc")) + "(" + CommFunc.CheckString(rs.getString("name")) + ")" + SDDef.JSONCCM);		//操作员
					ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(rs.getInt("date")) + SDDef.JSONCCM);				//日期
					ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToHM2(rs.getInt("time")/100) + SDDef.JSONCCM);			//时间					
					ret_buf.append(SDDef.JSONQUOT +getOperType(rs.getInt("opertype"))  + SDDef.JSONCCM);				//操作类型
					
					ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(rs.getString("oper_info")) + SDDef.JSONQBRRBCM);	//事项内容
					
				}
				j_dao.closeRs(rs);
				
			}catch(SQLException e){
				j_dao.closeRs(rs);
				e.printStackTrace();
			}
		}
		if (no == 0) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		
		result=ret_buf.toString();
		
		return SDDef.SUCCESS;
	}

	public String getOperType(int type){
		String operType ="";
		switch(type){
		case 0:
			operType = "LOG_LOGIN";
			break;
		case 1 :
			operType = "LOG_LOGOUT";
			break;
		case 2 :
			operType = "LOG_ADD";
			break;
		case 3 :
			operType = "LOG_UPDATE";
			break;
		case 4 :
			operType = "LOG_DELETE";
			break;
		case 5 :
			operType = "LOG_SET";
			break;			
		}
		
		
		return operType;
	}
 	
 
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public String getShm() {
		return shm;
	}

	public void setShm(String shm) {
		this.shm = shm;
	}

	public String getEhm() {
		return ehm;
	}

	public void setEhm(String ehm) {
		this.ehm = ehm;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getOptype() {
		return optype;
	}

	public void setOptype(String optype) {
		this.optype = optype;
	}
}
