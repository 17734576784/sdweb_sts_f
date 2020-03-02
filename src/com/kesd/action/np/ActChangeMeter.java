package com.kesd.action.np;

import java.sql.ResultSet;

import net.sf.json.JSONObject;

import org.apache.struts2.json.annotations.JSON;

import com.kesd.common.SDDef;
import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;

public class ActChangeMeter extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6268085290910467823L;
	
	private String 		result;
	private String		field;		//所需查询的数据库字段
	
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>NAME        : getMpList	
	* <p>DESCRIPTION : 取得更换电表的下拉列表
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	*-----------------------------------------------------------*/
	@JSON(serialize = false)
	public String getMpList(){
		StringBuffer 	ret_buf		= new StringBuffer();
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		}
		short[] mpid ={0, 0, 0};
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String rtu_id = jsonObj.getString("rtu_id");
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		String[][] mp = null;
		try {
			String sql = "select id,describe,ct_numerator ctfz,ct_denominator ctfm,ct_ratio ct,pt_numerator ptfz,pt_denominator ptfm,pt_ratio pt from mppara where rtu_id=" + rtu_id;
			rs = j_dao.executeQuery(sql);
			rs.last();
			mp = new String[rs.getRow()][8];
			rs.beforeFirst();
			int i = 0;
			while(rs.next()){
				int idx = 0;
				mp[i][idx++] = rs.getString("id");
				mp[i][idx++] = rs.getString("describe");
				mp[i][idx++] = rs.getString("ctfz");
				mp[i][idx++] = rs.getString("ctfm");
				mp[i][idx++] = rs.getString("ct");
				mp[i][idx++] = rs.getString("ptfz");
				mp[i][idx++] = rs.getString("ptfm");
				mp[i][idx++] = rs.getString("pt");
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			j_dao.closeRs(rs);
		}
		mpid[0] = (short)jsonObj.getInt("mpid0");
		mpid[1]	= (short)jsonObj.getInt("mpid1");
		mpid[2]	= (short)jsonObj.getInt("mpid2");
		ret_buf.append(SDDef.JSONROWSTITLE);
		
		for (int i = 0; i < 3; i++) {
			if(mpid[i] > 0){
				ret_buf.append(SDDef.JSONLBRACES);
				ret_buf.append(SDDef.JSONQUOT + "value" + SDDef.JSONQACQ + mpid[i] + SDDef.JSONCCM); 
				for (int j = 0; j < mp.length; j++) {
					if(mpid[i] == CommBase.strtoi(mp[j][0])){
						ret_buf.append(SDDef.JSONQUOT + "text" 	+ SDDef.JSONQACQ + mp[j][1] + SDDef.JSONCCM);
						ret_buf.append(SDDef.JSONQUOT + "ctfz" 	+ SDDef.JSONQACQ + mp[j][2] + SDDef.JSONCCM);
						ret_buf.append(SDDef.JSONQUOT + "ctfm" 	+ SDDef.JSONQACQ + mp[j][3] + SDDef.JSONCCM);
						ret_buf.append(SDDef.JSONQUOT + "ct" 	+ SDDef.JSONQACQ + mp[j][4] + SDDef.JSONCCM);
						ret_buf.append(SDDef.JSONQUOT + "ptfz" 	+ SDDef.JSONQACQ + mp[j][5] + SDDef.JSONCCM);
						ret_buf.append(SDDef.JSONQUOT + "ptfm" 	+ SDDef.JSONQACQ + mp[j][6] + SDDef.JSONCCM);
						ret_buf.append(SDDef.JSONQUOT + "pt" 	+ SDDef.JSONQACQ + mp[j][7] + SDDef.JSONQRBCM);
						break;
					}
				}
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
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	
}
