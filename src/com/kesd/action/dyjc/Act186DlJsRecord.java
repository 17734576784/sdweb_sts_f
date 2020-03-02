package com.kesd.action.dyjc;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.json.annotations.JSON;

import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.common.YDTable;
import com.libweb.common.CommBase;
import com.libweb.dao.HibDao;
import com.libweb.dao.JDBCDao;

public class Act186DlJsRecord {
	private String 			result;
	private String 			operErr				= null;		//返回错误信息描述,在js弹出框中显示
	
	@JSON(serialize = false)
	public String ImportJsRecord()
	{
		JSONObject json_obj = JSONObject.fromObject(result);//将页面数据转化为对象
		JSONArray  rows    = json_obj.getJSONArray("rows");	
		HibDao     hib_dao  = new HibDao();
		operErr		= "";
	//	RateInfo[] rateInfo = new RateInfo[rows.size()];

		int ret = -1, errnum = 0;
		for(int i=0; i<rows.size(); i++){
			ret = Import2DB(JSONObject.fromObject(rows.get(i)));
			if (ret <= 0) errnum ++;
			operErr +=  "," + ret;
		}
	
		operErr = operErr.substring(1);
		result = SDDef.SUCCESS;
//		if (errnum == 0) {
//			result = SDDef.SUCCESS;
//		}
//		else {
//			result = SDDef.FAIL;
//			operErr = "存储数据 总数["+ rows.size() +"]失败数[" + errnum + "]";
//		}
			
		return SDDef.SUCCESS;
	}

	public int Import2DB(JSONObject json_obj) {
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String sql = null;
		int ret = 0;

		try {
			
			String ymd				= new SimpleDateFormat("yyyyMMdd").format(new Date());
			String hms				= new SimpleDateFormat("HHmmss").format(new Date());

			//int calctye = json_obj.getString("cacl_type")=="金额费控"?SDDef.YFF_CACL_TYPE_MONEY:SDDef.YFF_CACL_TYPE_DL;
			
			sql = "insert ydparaben.dbo.jzbjs_rcd(rtu_id,mp_id,jszb_type,cons_no,cons_desc,org_no,zbjs_ym,zbjs_ymd,zbjs_hms,js_money,zb_money2,zb_money3,zb_money4,op_date,op_time,nowstate,reserve1,reserve2)" +
			"values("+CommBase.strtoi((json_obj.getString("rtu_id")))+","+
					  CommBase.strtoi((json_obj.getString("mp_id")))+",1,'"+
					  (json_obj.getString("cons_no"))+"','"+
					  (json_obj.getString("cons_name"))+"','',"+
					  CommBase.strtoi((json_obj.getString("op_date")))/100+","+
					  ymd +"," +
					  hms +"," +
					  (json_obj.getString("jsmoney"))+",0,0,0,0,0,0,0,0)";

			if(j_dao.executeUpdate(sql)){
				ret = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			j_dao.closeRs(rs);
		}
		
		if (ret != 0) return ret;
			
		try {
			
			String ymd				= new SimpleDateFormat("yyyyMMdd").format(new Date());
			String hms				= new SimpleDateFormat("HHmmss").format(new Date());
		
			sql = "select * from ydparaben.dbo.jzbjs_rcd where jszb_type = 1 and nowstate = 0" +
				  " and rtu_id = " + CommBase.strtoi((json_obj.getString("rtu_id"))) +
				  " and	mp_id = " + CommBase.strtoi((json_obj.getString("mp_id"))) +
				  " and	zbjs_ym = " + CommBase.strtoi((json_obj.getString("op_date")))/100;

			rs = j_dao.executeQuery(sql);
			while(rs.next()){
				sql = "update ydparaben.dbo.jzbjs_rcd set " +
				  "cons_no = '" +json_obj.getString("cons_no")+
				  "',cons_desc = '" +json_obj.getString("cons_name")+
				  "',zbjs_ymd = " + ymd +
				  ",zbjs_hms=" + hms + 
				  ",js_money=" +json_obj.getString("jsmoney")+
				  " where jszb_type = 1 and nowstate = 0" +
				  " and rtu_id = " + CommBase.strtoi((json_obj.getString("rtu_id"))) +
				  " and	mp_id = " + CommBase.strtoi((json_obj.getString("mp_id"))) +
				  " and	zbjs_ym = " + CommBase.strtoi((json_obj.getString("op_date")))/100;
	  
				if(j_dao.executeUpdate(sql)){
					ret = 1;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			j_dao.closeRs(rs);
		}
	
		
		return ret;
	}

	public String ReadJsRecord()
	{
		if(result == null || result.isEmpty()) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}

		JSONObject jsonObj  = JSONObject.fromObject(result);
		String rtu_id	= jsonObj.getString("rtu_id");
		String mp_id	= jsonObj.getString("mp_id");
		
		ResultSet 				rs = null;
		JDBCDao j_dao			= new JDBCDao();
		StringBuffer ret_buf	= new StringBuffer();
		double 		jstot_money = 0.0, zbtot_money = 0.0;
		try {	//sum(js_money) as totjs_money
			String sql = "select * from ydparaben.dbo.jzbjs_rcd where jszb_type = 1 and nowstate = 0" +
			  " and rtu_id = " + rtu_id + " and	mp_id = " + mp_id;
			rs = j_dao.executeQuery(sql);
			while (rs.next()) {
				jstot_money  +=rs.getDouble("js_money");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			j_dao.closeRs(rs);
		}
		
		try {
			String sql = "select * from ydparaben.dbo.jzbjs_rcd where jszb_type = 0 and nowstate = 0" +
			  " and rtu_id = " + rtu_id + " and	mp_id = " + mp_id;
			rs = j_dao.executeQuery(sql);
			while (rs.next()) {
				zbtot_money  +=rs.getDouble("zb_money2");
				zbtot_money  +=rs.getDouble("zb_money3");
				zbtot_money  +=rs.getDouble("zb_money4");
			}
			zbtot_money *= -1.0;
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			j_dao.closeRs(rs);
		}

		ret_buf.append("{rtu_id:" 		+ rtu_id 		+ ",");
		ret_buf.append("mp_id:" 		+ mp_id	 		+ ",");
		ret_buf.append("zb_money:" 		+ zbtot_money 	+ ",");
		ret_buf.append("othjs_money:" 	+ jstot_money 	+  "}");

		result = ret_buf.toString();

		return SDDef.SUCCESS;
	}
	
	
	public String UpdateJsRecord()
	{
		int ret = 0;
		if(result == null || result.isEmpty()) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}

		JSONObject jsonObj  = JSONObject.fromObject(result);
		String rtu_id	= jsonObj.getString("rtu_id");
		String mp_id	= jsonObj.getString("mp_id");
		
		JDBCDao j_dao			= new JDBCDao();
		StringBuffer ret_buf	= new StringBuffer();
		
		try {
			String ymd				= new SimpleDateFormat("yyyyMMdd").format(new Date());
			String hms				= new SimpleDateFormat("HHmmss").format(new Date());
			
			String sql = "update ydparaben.dbo.jzbjs_rcd set  nowstate = 1" +
			  ", op_date = " + ymd +
			  ", op_time =" + hms + 
			  " where nowstate = 0" +
			  " and rtu_id = " +  rtu_id +
			  " and	mp_id = " + mp_id;
			 if (j_dao.executeUpdate(sql)) {
				 ret = 1;
			 }
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}

		if (ret==1){
			result = SDDef.SUCCESS;
		}
		else {
			 result = SDDef.FAIL;
		}
		
		return SDDef.SUCCESS;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOperErr() {
		return operErr;
	}

	public void setOperErr(String operErr) {
		this.operErr = operErr;
	}
	
	
}
