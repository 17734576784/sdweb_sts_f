package com.kesd.action.np;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.kesd.common.SDDef;
import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;

public class ActImportBD extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7360430504387313113L;

	private String 		result;

	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>NAME        : readBD	
	* <p>DESCRIPTION : 从数据库中读取表底
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	*-----------------------------------------------------------*/
	public String readBD(){
		StringBuffer 	ret_buf		= new StringBuffer();
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		Boolean hasdata = false;
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String rtuId 	= jsonObj.getString("rtuId");
		String mpId0 	= jsonObj.getString("mpId0");
		String mpId1 	= jsonObj.getString("mpId1");
		String mpId2 	= jsonObj.getString("mpId2");
		String tDate	= jsonObj.getString("tDate");
			
		JDBCDao j_dao = new JDBCDao();
		HashMap<String, Object> hmap = new HashMap<String, Object>();
		//select * from jDaybddl201105 where rtu_id=30000000 and mp_id=0 and date=20110521 
		String sql ="select bd_zy,bd_zy_fl1,bd_zy_fl2,bd_zy_fl3,bd_zy_fl4 from yddataben.dbo.jDaybddl" +tDate.substring(0, 6) + " where rtu_id=" + rtuId + "  and mp_id=" + mpId0 + " and date=" + tDate; 
		List<Map<String,Object>> list = j_dao.result(sql);
		String zbd = "zbd:[";
		String jbd = "jbd:[";
		String fbd = "fbd:[";
		String pbd = "pbd:[";
		String gbd = "gbd:[";
		
		if(list == null || list.size()==0){
			zbd += "0";
			jbd += "0";
			fbd += "0";
			pbd += "0";
			gbd += "0";
		}else{
			hmap =(HashMap<String, Object>)list.get(0);
			zbd += hmap.get("bd_zy");
			jbd += hmap.get("bd_zy_fl1");
			fbd += hmap.get("bd_zy_fl2");
			pbd += hmap.get("bd_zy_fl3");
			gbd += hmap.get("bd_zy_fl4");
			hasdata = true;
		}
		if(!mpId1.equals("-1")){
			sql ="select bd_zy,bd_zy_fl1,bd_zy_fl2,bd_zy_fl3,bd_zy_fl4 from yddataben.dbo.jDaybddl" +tDate.substring(0, 6) + " where rtu_id=" + rtuId + "  and mp_id=" + mpId1 + " and date=" + tDate;
			list = j_dao.result(sql);
			if(list == null || list.size()==0){
				zbd += ",0";
				jbd += ",0";
				fbd += ",0";
				pbd += ",0";
				gbd += ",0";
			}else{
				hmap=(HashMap<String, Object>)list.get(0);
				zbd += "," + hmap.get("bd_zy");
				jbd += "," + hmap.get("bd_zy_fl1");
				fbd += "," + hmap.get("bd_zy_fl2");
				pbd += "," + hmap.get("bd_zy_fl3");
				gbd += "," + hmap.get("bd_zy_fl4");
				hasdata = true;
			}			
		}
		if(!mpId2.equals("-1")){
			sql ="select bd_zy,bd_zy_fl1,bd_zy_fl2,bd_zy_fl3,bd_zy_fl4 from yddataben.dbo.jDaybddl" +tDate.substring(0, 6) + " where rtu_id=" + rtuId + "  and mp_id=" + mpId2 + " and date=" + tDate;
			list = j_dao.result(sql);
			if(list == null || list.size()==0){
				zbd += ",0";
				jbd += ",0";
				fbd += ",0";
				pbd += ",0";
				gbd += ",0";
			}else{
				hmap=(HashMap<String, Object>)list.get(0);
				zbd += "," + hmap.get("bd_zy");
				jbd += "," + hmap.get("bd_zy_fl1");
				fbd += "," + hmap.get("bd_zy_fl2");
				pbd += "," + hmap.get("bd_zy_fl3");
				gbd += "," + hmap.get("bd_zy_fl4");
				hasdata = true;
			}			
		}
		zbd += "]";
		jbd += "]";
		fbd += "]";
		pbd += "]";
		gbd += "]";
		
		ret_buf.append(SDDef.JSONLBRACES);
		ret_buf.append(zbd + "," + jbd + "," + fbd + "," + pbd + "," + gbd);
		ret_buf.append(SDDef.JSONRBRACES);
		if(hasdata){
			result = ret_buf.toString();
		}else{
			result = SDDef.EMPTY;
		}
		return SDDef.SUCCESS;
	}
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>NAME        : readMpPara	
	* <p>DESCRIPTION : 根据终端ID和电表ID查询 电表及动力关联表的mp_id,mp_desc
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	*-----------------------------------------------------------*/
	
	@SuppressWarnings("unchecked")
	public String readMpPara(){
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String rtuId 	= jsonObj.getString("rtuId");
		String mpId 	= jsonObj.getString("mpId");
		
		String[][] mp = null;
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		try{
			String sql = "select id,describe from mppara where rtu_id="+rtuId;
			rs = j_dao.executeQuery(sql);
			rs.last();
			mp = new String[rs.getRow()][2];
			rs.beforeFirst();
			int i = 0;
			while(rs.next()){
				mp[i][0] = rs.getString("id");
				mp[i][1] = rs.getString("describe");
				i++;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			j_dao.closeRs(rs);
		}
		
		
		String 		 sql 	 = "select mp_id as mp_id0,power_relaf,power_rela1 as mp_id1,power_rela2 as mp_id2 " +
						 	" from mppay_para where rtu_id=" + rtuId + " and mp_id=" + mpId;

		List list 	  = j_dao.result(sql);
		
		if(list == null || list.size()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		HashMap<String, String> hmap=(HashMap<String, String>)list.get(0);
		JSONObject j_obj = new JSONObject();
		int mpNum  = 1;
		j_obj.put("mp_id0",String.valueOf(hmap.get("mp_id0")));
		for (int i = 0; i < mp.length; i++) {
			String obj = String.valueOf(hmap.get("mp_id0"));
			String mpid = mp[i][0];
			
			if(mpid.equals(obj)){
				j_obj.put("mp_desc0",mp[i][1]);
				break;
			}
		}
		j_obj.put("power_relaf",String.valueOf(hmap.get("power_relaf")));
		String mp_id = CommBase.CheckString(hmap.get("mp_id1"));
		int imp_id = CommBase.strtoi(mp_id);
		if(imp_id <= 0){
			j_obj.put("mp_id1","-1");
			j_obj.put("mp_desc1","动力关联1(无效)");
		}else{
			j_obj.put("mp_id1",String.valueOf(hmap.get("mp_id1")));
			for (int i = 0; i < mp.length; i++) {
				String obj = String.valueOf(hmap.get("mp_id1"));
				String mpid = mp[i][0];
				
				if(mpid.equals(obj)){
					j_obj.put("mp_desc1",mp[i][1]);
					break;
				}
			}
			mpNum++;
		}
		
		mp_id = CommBase.CheckString(hmap.get("mp_id2"));
		imp_id = CommBase.strtoi(mp_id);
		if(imp_id <= 0){
			j_obj.put("mp_id2","-1");
			j_obj.put("mp_desc2","动力关联2(无效)");
		}else{
			j_obj.put("mp_id2",String.valueOf(hmap.get("mp_id2")));
			for (int i = 0; i < mp.length; i++) {
				String obj = String.valueOf(hmap.get("mp_id2"));
				String mpid = mp[i][0];
				
				if(mpid.equals(obj)){
					j_obj.put("mp_desc2",mp[i][1]);
					break;
				}
			}
			mpNum++;
		}
		j_obj.put("clp_num",mpNum);
		
		result = j_obj.toString();
		return SDDef.SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String impGDRec() throws SQLException{
		
		boolean val = false;
		String retVal = "";
		JSONObject json = JSONObject.fromObject(result);
		JSONArray  j_array = json.getJSONArray("rows");
		JSONObject j_obj2  = null;	 	
		for (int j = 0; j < j_array.size(); j++) {
			j_obj2 = j_array.getJSONObject(j);
			
			String cardno = j_obj2.getString("cardno");
			String totbuy_times = j_obj2.getString("totbuy_times");
			String total_gdz = j_obj2.getString("total_gdz");
			String cur_remain = j_obj2.getString("cur_remain");
			String pay_money = j_obj2.getString("pay_money");			
			String op_date = j_obj2.getString("op_date").substring(0,10).replace("-", "");
			String op_time = j_obj2.getString("op_time").substring(11).replace(":", "");
		
		

			JDBCDao dao = new JDBCDao();
			String sql = "";
			String farmsql ="select area_id,id,describe,farmer_no from farmerpara where card_no = '"+ cardno + "'";
			ResultSet rs =dao.executeQuery(farmsql);
			String areaId="",farmId="",farnNo="",farmDesc="";
			if(rs.next()){
				areaId = String.valueOf(rs.getInt(1));
				farmId = String.valueOf(rs.getShort(2));
				farmDesc = rs.getString(3);
				farnNo = rs.getString(4);
			}else{
				retVal +=",0";
				continue;
			}
			sql = "select * from yddataben.dbo.NYff"+op_date.substring(0,4) +" where area_id = "+areaId+" and farmer_id = "+farmId+" and op_date = "+op_date+" and op_time = "+op_time+" and op_type = 2";
			val = dao.executeQuery(sql).next();
			if(!val){
				sql = "insert into yddataben.dbo.sysnyfrcd"+op_date.substring(0,4)+"(area_id,farmer_id,farmer_desc,farmer_no,card_no,op_type,op_date,op_time,pay_money,cur_remain,total_gdz,totbuy_times,buy_times,op_man,pay_type,othjs_money,zb_money,all_money,ls_remain) values( "+
				areaId + " , " + farmId + " , '"  + farmDesc + "' , '" + farnNo + "' ,'" + cardno + "' , " + 2 + " , " + op_date + " , "+ op_time + " , " + pay_money + " , "+ cur_remain + " , "+ total_gdz + " , "+ totbuy_times + " , "+ totbuy_times + " , '农排导入',0,0,0,0,0)";
				val = dao.executeUpdate(sql);	

				
				sql = "insert into yddataben.dbo.NYff"+op_date.substring(0,4)+"(area_id,farmer_id,farmer_desc,farmer_no,card_no,op_type,op_date,op_time,pay_money,cur_remain,total_gdz,totbuy_times,buy_times,op_man,pay_type,othjs_money,zb_money,all_money,ls_remain) values( "+
				areaId + " , " + farmId + " , '"  + farmDesc + "' , '" + farnNo + "' ,'" + cardno + "' , " + 2 + " , " + op_date + " , "+ op_time + " , " + pay_money + " , "+ cur_remain + " , "+ total_gdz + " , "+ totbuy_times + " , "+ totbuy_times + " , '农排导入',0,0,0,0,0)";
				val = dao.executeUpdate(sql);	

			}else{
				sql = "update yddataben.dbo.NYff"+op_date.substring(0,4) +" set farmer_desc = '"+farmDesc +"',farmer_no = '"+farnNo+"',card_no = '"+cardno +"', op_type = 2, pay_money="+pay_money+",cur_remain = "
				+cur_remain+ ",total_gdz = "+total_gdz+",totbuy_times= "+totbuy_times+",op_man = '农排导入',pay_type = 0, othjs_money = 0 ,zb_money = 0,all_money = 0,ls_remain = 0,buy_times= " +totbuy_times+
				" where area_id = " +areaId +"  and  farmer_id = "+farmId +" and op_date = "+op_date +"  and op_time = "+op_time;
				val = dao.executeUpdate(sql);
				
				sql = "update yddataben.dbo.sysnyfrcd"+op_date.substring(0,4) +" set farmer_desc = '"+farmDesc +"',farmer_no = '"+farnNo+"',card_no = '"+cardno +"', op_type = 2, pay_money="+pay_money+",cur_remain = "
				+cur_remain+ ",total_gdz = "+total_gdz+",totbuy_times= "+totbuy_times+",op_man = '农排导入',pay_type = 0, othjs_money = 0 ,zb_money = 0,all_money = 0,ls_remain = 0,buy_times= " +totbuy_times+
				" where area_id = " +areaId +"  and  farmer_id = "+farmId +" and op_date = "+op_date +"  and op_time = "+op_time;
				val = dao.executeUpdate(sql);	
			}
				
			 
			retVal +=",1";
		}
		
		if(!val){
			result = retVal.substring(1);
			return SDDef.SUCCESS;
		}
		
		result = retVal.substring(1);
		return SDDef.SUCCESS;
	}
	
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
