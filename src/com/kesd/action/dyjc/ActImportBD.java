package com.kesd.action.dyjc;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
