package com.kesd.action.docs;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.json.JSONObject;
import org.apache.struts2.json.annotations.JSON;
import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.util.Rd;


public class ActCsStand {
	
	private String   	result;
	private String   	pageNo;			//页号
	private String   	pageSize;  		//每页记录数
	private String   	field;			//所需查询的数据库字段
	
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : execute
	* <p>
	* <p>DESCRIPTION : 获取力调电费参数表记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@JSON(serialize=false)
	public String execute() throws Exception
	{
		StringBuffer 	ret_buf  = new StringBuffer();
		JDBCDao			dao		 = new JDBCDao();
		String sql= new String();
		
		sql  = "select id,describe,use_flag,stand, cs_minval from cs_stand order by id";
		ResultSet rs = null;
		try {
			rs = dao.executeQuery(sql);
			ret_buf.append(SDDef.JSONROWSTITLE);
			int	Idx = 1;
			while(rs.next()) {
				ret_buf.append(SDDef.JSONLBRACES); 
				ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + (rs.getInt("id")) 		+ SDDef.JSONDATA);
				ret_buf.append(SDDef.JSONQUOT + Idx++ + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(rs.getString("describe"))  + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_USEFLAG, rs.getByte("use_flag"))  + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(rs.getInt("stand")) 		+ SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(rs.getInt("cs_minval")) 	+ SDDef.JSONQBRRBCM);
			}
	        if(Idx == 1){
	        	result = "";
	        }else{
	        	ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
	        	ret_buf.append(SDDef.JSONRBRACES);
	        	result = ret_buf.toString();
	        }
	    } catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.closeRs(rs);
		}
		return SDDef.SUCCESS;
	}
	
	@JSON(serialize=false)
	public String getItems() throws SQLException{
		StringBuffer 	ret_buf  = new StringBuffer();
		JDBCDao			dao	 = new JDBCDao();
		String sql= new String();
		//result 不为空
		JSONObject jsonObj=JSONObject.fromObject(result);
		String id = jsonObj.getString("id");
		sql  = "select * from cs_standitem where cos_id= " + id +" order by cos_id";
		ResultSet rs = null;
		try {
			rs = dao.executeQuery(sql);
			ret_buf.append(SDDef.JSONROWSTITLE);
			int	Idx = 1;
			while(rs.next()) {
				ret_buf.append(SDDef.JSONLBRACES); 
				ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + (rs.getInt("cos_id")+"_"+rs.getInt("item_id")) + SDDef.JSONNZDATA);
				ret_buf.append(SDDef.JSONQUOT + Idx++ + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(rs.getInt("realcos")) 		+ SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(rs.getDouble("dfchgarate") / 100) 	+ SDDef.JSONQBRRBCM);
			}
	        if(Idx == 1){
	        	result = "";
	        }else{
	        	ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
	        	ret_buf.append(SDDef.JSONRBRACES);
	        	result = ret_buf.toString();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.closeRs(rs);
		}
		
		return SDDef.SUCCESS;
	}

	public String update(){
		JDBCDao	dao	 = new JDBCDao();
		String sql= null;
		//result 不为空
		JSONObject jsonObj=JSONObject.fromObject(result);
		String describe = jsonObj.getString("describe");
		String useFlag = jsonObj.getString("useFlag");
		String stand = jsonObj.getString("stand"); 
		String csMinval = jsonObj.getString("csMinval");
		String stand_id = jsonObj.getString("stand_id");
		
		sql  = "update cs_stand set describe = '" + describe + "', use_flag = " + useFlag + ", stand=" + stand + ", cs_minval= " + csMinval + " where id=" + stand_id;
		
		boolean flag = dao.executeUpdate(sql);
		if(flag){
			result = SDDef.SUCCESS;
		}else{
			result = "fail";
		}
		return SDDef.SUCCESS;
	}
	
    //批量删除
	public String pldel(){
		JDBCDao dao = new JDBCDao();
		String sql = new String();
		String[] strs = result.split(",");
		for (int i = 0; i < strs.length; i++) {
			sql = "delete from cs_stand where id=";
			String sql1 = "delete from cs_standitem where cos_id=";
			sql += strs[i];
			sql1 += strs[i];
			dao.executeUpdate(sql1);
			dao.executeUpdate(sql);
		}
		result = SDDef.SUCCESS;
		return SDDef.SUCCESS;
	}
	
	//修改cs_standitem 表
	public String updateCosItem(){
		JDBCDao			dao	 = new JDBCDao();
		String sql= new String();
		JSONObject jsonObj=JSONObject.fromObject(result);
		String cosId      = jsonObj.getString("cos_id");
		String ItemId     = jsonObj.getString("item_id");
		String dfchgarate = jsonObj.getString("dfchgarate");
		
		sql  = "update cs_standitem set dfchgarate = " + dfchgarate + " where cos_id= " + cosId + " and item_id= " + ItemId;
		boolean flag = dao.executeUpdate(sql);
		if(flag){
			result="success";
		}else{
			result="fail";
		}
		return SDDef.SUCCESS;
	}
	//增加cs_stand表和cs_standitem表
	public String add() throws SQLException{
		JDBCDao			dao	 = new JDBCDao();
		String sql= new String();
		String sql2=new String();
		//result 不为空
		JSONObject jsonObj=JSONObject.fromObject(result);
		String describe = jsonObj.getString("describe");
		String useFlag =  jsonObj.getString("useFlag");
		String stand = jsonObj.getString("stand");
		String csMinval = jsonObj.getString("csMinval");
		//得到当前表中最大的id;
		int id=getTableId();
		String KONG=null;
		sql  = "insert into cs_stand " + " values("+(id+1)+",'"+describe+"',"+stand+","+useFlag+","+csMinval+","+KONG+","+KONG+")";
		boolean rs = dao.executeUpdate(sql);
		for(int i = 0;i < 101; i++){
			sql2="insert into cs_standitem " + " values(" + (id + 1) + "," + i + "," + (100 - i) + "," + 0 + "," + KONG + "," + KONG + ")";
			boolean flag = dao.executeUpdate(sql2);
			rs = rs && flag;
			if (rs == false) {
				break;
			}
		}
		if (rs == true) {
			result=SDDef.SUCCESS;
		}else{
			result="fail";
		}
		return SDDef.SUCCESS;
	}
	/**
	 * 由于两个表操作插入多条数据,所以当其中某条数据插入失败的时要进行事物的回滚
	 * 
	 */
	//增加cs_stand表和cs_standitem表 有事件回滚  未完待续
//	public String add() throws SQLException{
//		JDBCDao			daoa	 = new JDBCDao();
//		List<String> list=new ArrayList<String>();
//		String sql= new String();
//		String sql2=new String();
//		//result 必定不为空
//		JSONObject jsonObj=JSONObject.fromObject(result);
//		String describe = jsonObj.getString("describe");
//		String useFlag =  jsonObj.getString("useFlag");
//		String stand = jsonObj.getString("stand");
//		//得到当前表中最大的id;
//		int id=getTableId();
//		String KONG=null;
//		sql  = "insert into " + YDTable.TABLECLASS_CSSTAND +
//		       " values( '"+(id+1)+"','"+describe+"','"+stand+"','"+useFlag+"',"+KONG+","+KONG+")";
//		list.add(sql);
//		boolean rs = dao.executeUpdate(sql);
//		for(int i=0;i<101;i++){
//			sql2="insert into " + YDTable.TABLECLASS_CSSTANDITEM1 +
//		       " values( '"+(id+1)+"','"+i+"','"+(100-i)+"',"+0+","+KONG+","+KONG+")";
//			list.add(sql2);
//		}
//		boolean flag = false;
//		try {
//		    for(int i=0;i<list.size();i++){
//		    	dao.executeUpdate(list.get(i));
//		    }
//		    flag = true;
//		    con.commit();//提交
//	    }catch (Exception e) {
//		    con.rollback();//回滚
//		    e.printStackTrace();
//		}finally {
////		    this.closeAll();//关闭连接 数据集 语句对象
//			if(flag==true){
//				result=SDDef.SUCCESS;
//			}else{
//				result="fail";
//			}
//			return SDDef.SUCCESS;
//		}
//	}
	
	private int getTableId() throws SQLException{
		String sqlId=new String();
		sqlId="select max(id) id from cs_stand";
		int id = 0;
		JDBCDao	dao	 = new JDBCDao();
		ResultSet rs = null;
		try {
			rs = dao.executeQuery(sqlId);
			if(rs.next()){
				id=rs.getInt("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.closeRs(rs);
		}
		
		return id;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
		
}
