package com.kesd.action.spec;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

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
	* <p>NAME        : readZjgPara	
	* <p>DESCRIPTION : 从数据库中读取总加组信息
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	*-----------------------------------------------------------*/
	public String readZjgPara(){
		StringBuffer 	ret_buf		= new StringBuffer();
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String rtuId 	= jsonObj.getString("rtuId");
		String zjgId 	= jsonObj.getString("zjgId");
		
		short mpId[]	= null;
		String mpName[]	= null;
		
		JDBCDao j_daomp = new JDBCDao();
		//select * from mppara where rtu_id=30000000
		String sql_mp ="select id,describe from ydparaben.dbo.mppara where rtu_id=" + rtuId +" order by id";
		ResultSet rs = null;
		
		try{
			rs = j_daomp.executeQuery(sql_mp);
			rs.last();
			int num = rs.getRow();
			mpId = new short[num];
			mpName = new String[num];
			
			int j = 0;
			rs.beforeFirst();
			while(rs.next()){
				mpId[j] 	= rs.getShort("id");
				mpName[j]	= rs.getString("describe");
				j++;
			}
		}catch(Exception e){
			
			e.printStackTrace();
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		} finally {
			j_daomp.closeRs(rs);
		}
		
		//select * from ydparaben.dbo.zjgpara where rtu_id=30000000 and zjg_id=0 
		try {
			String sql ="select clp_num,clp_ids,zf_flag,as_flag from zjgpara where rtu_id=" + rtuId + "  and zjg_id=" + zjgId;
			rs = j_daomp.executeQuery(sql);
			if(rs.next()) {
				ret_buf.append(SDDef.JSONLBRACES);
				int clp_num 	= rs.getInt("clp_num");
				String clp_ids  = rs.getString("clp_ids");
				String zf_flag  = rs.getString("zf_flag");
				String zf_flags = "";
				String as_flag  = rs.getString("as_flag");
				String as_flags = "";
				
				int lookTimes	= rs.getInt("clp_num");
				if(lookTimes > 3) lookTimes = 3;
				
				int num = 0;
				for(int i = 1; i < clp_ids.length(); i++){
					if(num >= lookTimes) break;
					if(clp_ids.charAt(i) == '0') continue;
					
					int idx = findIndex(i, mpId);
					if(idx < 0) continue;
					
					zf_flags += zf_flag.charAt(i);
					as_flags += as_flag.charAt(i);
					
					ret_buf.append(SDDef.JSONQUOT + "mpName"+ num + SDDef.JSONQACQ + mpName[idx] + SDDef.JSONCCM);
					ret_buf.append(SDDef.JSONQUOT + "mpId"+ num + SDDef.JSONQACQ + mpId[idx] + SDDef.JSONCCM);
					num ++;
				}
				ret_buf.append(SDDef.JSONQUOT + "clp_num" + SDDef.JSONQACQ + clp_num + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "zf_flag" + SDDef.JSONQACQ + zf_flags + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "as_flag" + SDDef.JSONQACQ + as_flags + SDDef.JSONQRBCM);
				ret_buf.setLength(ret_buf.length()-1);
				
				result=ret_buf.toString();
			} else {
				result = SDDef.EMPTY;
				return SDDef.SUCCESS;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			j_daomp.closeRs(rs);
		}
		
		
		
		return SDDef.SUCCESS;
	}
	
	private int findIndex(int mp_id, short mp_ids[]) {
		if(mp_ids == null || mp_ids.length <= 0) return -1;
		
		for(int i = 0; i < mp_ids.length; i++) {
			if(mp_id == mp_ids[i]) return i;
		}
		return -1;
	}
	
	
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>NAME        : readBD	
	* <p>DESCRIPTION : 从数据库中读取表底
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	*-----------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public String readBD(){
		StringBuffer 	ret_buf		= new StringBuffer();
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String rtuId 	= jsonObj.getString("rtuId");
		String mpids	= jsonObj.getString("mpIds");
		String clpNum 	= jsonObj.getString("clpNum");
		String tDate	= jsonObj.getString("tDate");
		String zf_flag	= jsonObj.getString("zfFlag");
		
		String mp_ids[] = mpids.split(",");
		
		int lookTimes	= CommBase.strtoi(clpNum);
		if(lookTimes > 3) lookTimes = 3;
		
		JDBCDao j_daomp = new JDBCDao();
		//获取总加组配置的测点
		String sql_mp ="select id,describe from ydparaben.dbo.mppara where rtu_id=" + rtuId +" and (id="+mp_ids[0]+" or id="+mp_ids[1]+" or id="+mp_ids[2]+") order by id";
		ResultSet rs = null;
		
		short  mpId[]	= new short[3];
		String mpName[]	= new String[3];
		try{
			rs = j_daomp.executeQuery(sql_mp);
			int j = 0;
			while(rs.next()){
				mpId[j] 	= rs.getShort("id");
				mpName[j]	= rs.getString("describe");
				j++;
			}
		}catch(Exception e){
			e.printStackTrace();
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		} finally {
			j_daomp.closeRs(rs);
		}
		
		ret_buf.append(SDDef.JSONROWSTITLE);
		
		for(int i = 0; i < lookTimes; i++){
			
			JDBCDao j_dao = new JDBCDao();
			String zfFlag = zf_flag.substring(i, i + 1);
			
			//select * from jDaybddl201105 where rtu_id=30000000 and mp_id=1 and date=20110521
			String sql = "";
			if(zfFlag.equals("0")){
				sql ="select bd_zy,bd_zy_fl1,bd_zy_fl2,bd_zy_fl3,bd_zy_fl4,bd_zw from yddataben.dbo.ZDaybddl" +tDate.substring(0, 6) + " where rtu_id=" + rtuId + "  and mp_id=" + mpId[i] + " and date=" + tDate;
			}else{
				sql ="select bd_fy,bd_fy_fl1,bd_fy_fl2,bd_fy_fl3,bd_fy_fl4,bd_fw from yddataben.dbo.ZDaybddl" +tDate.substring(0, 6) + " where rtu_id=" + rtuId + "  and mp_id=" + mpId[i] + " and date=" + tDate;
			}
			List list = j_dao.result(sql);
			
			//库中无数据
			if(list == null || list.size()==0){
				ret_buf.append(SDDef.JSONLBRACES);
				ret_buf.append(SDDef.JSONQUOT + "mpName" + SDDef.JSONQACQ + mpName[i] + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "zbd" + SDDef.JSONQACQ + 0 + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "jbd" + SDDef.JSONQACQ + 0 + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "fbd" + SDDef.JSONQACQ + 0 + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "pbd" + SDDef.JSONQACQ + 0 + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "gbd" + SDDef.JSONQACQ + 0 + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "wbd" + SDDef.JSONQACQ + 0 + SDDef.JSONQRBCM);

				continue;
			}
			HashMap<String, Double> hmap=(HashMap<String, Double>)list.get(0);
			ret_buf.append(SDDef.JSONLBRACES);
			ret_buf.append(SDDef.JSONQUOT + "mpName" + SDDef.JSONQACQ + mpName[i] + SDDef.JSONCCM);
			if(zfFlag.equals("0")){
				ret_buf.append(SDDef.JSONQUOT + "zbd" + SDDef.JSONQACQ + (hmap.get("bd_zy")		== null ? "" : hmap.get("bd_zy")) 	  + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "fbd" + SDDef.JSONQACQ + (hmap.get("bd_zy_fl1") == null ? "" : hmap.get("bd_zy_fl1")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "pbd" + SDDef.JSONQACQ + (hmap.get("bd_zy_fl2") == null ? "" : hmap.get("bd_zy_fl2")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "gbd" + SDDef.JSONQACQ + (hmap.get("bd_zy_fl3") == null ? "" : hmap.get("bd_zy_fl3")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "jbd" + SDDef.JSONQACQ + (hmap.get("bd_zy_fl4") == null ? "" : hmap.get("bd_zy_fl4")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "wbd" + SDDef.JSONQACQ + (hmap.get("bd_zw")		== null ? "" : hmap.get("bd_zw"))	  + SDDef.JSONQRBCM);

			}else{
				ret_buf.append(SDDef.JSONQUOT + "zbd" + SDDef.JSONQACQ + (hmap.get("bd_fy")		== null ? "" : hmap.get("bd_fy")) 	  + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "fbd" + SDDef.JSONQACQ + (hmap.get("bd_fy_fl1")	== null ? "" : hmap.get("bd_fy_fl1")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "pbd" + SDDef.JSONQACQ + (hmap.get("bd_fy_fl2")	== null ? "" : hmap.get("bd_fy_fl2")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "gbd" + SDDef.JSONQACQ + (hmap.get("bd_fy_fl3")	== null ? "" : hmap.get("bd_fy_fl3")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "jbd" + SDDef.JSONQACQ + (hmap.get("bd_fy_fl4")	== null ? "" : hmap.get("bd_fy_fl4")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "wbd" + SDDef.JSONQACQ + (hmap.get("bd_fw")		== null ? "" : hmap.get("bd_fw")) 	  + SDDef.JSONQRBCM);
			}
		}
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();
		
		return SDDef.SUCCESS;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
