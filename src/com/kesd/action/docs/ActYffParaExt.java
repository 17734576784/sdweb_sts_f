package com.kesd.action.docs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.json.annotations.JSON;

import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.YDTable;
import com.kesd.dao.SqlPage;
import com.kesd.dbpara.LinePara;
import com.kesd.dbpara.ZjgPaypara;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
import com.libweb.dao.HibDao;
import com.libweb.dao.JDBCDao;


public class ActYffParaExt {
	
	private String 		result;
	private String 		pageNo;		//页号
	private ZjgPaypara 	zjgpaypara;	//总加组档案

	private String	 	field;
	private String   	pageSize;
	private String   	pl;
	private int         count=0;

	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : addOrEdit
	* <p>
	* <p>DESCRIPTION : 添加或修改测点档案记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	public String addOrEdit()
	{
		String tmp_str = result;
		
		JSONObject json_obj = JSONObject.fromObject(tmp_str); 
		
		String sql = "update zjg_extpara set month_dl=" + CommBase.strtof(json_obj.getString("ext_month_dl")) + ", " +
					 " month_money=" + CommBase.strtof(json_obj.getString("ext_month_money")) + ", " +
					 " use_type=" + CommBase.strtoi(json_obj.getString("ext_use_type")) + ", " +
					 " moneydl_per=" + CommBase.strtoi(json_obj.getString("ext_moneydl_per")) + ", " +
					 " CTANo='" + CommBase.CheckString(json_obj.getString("ext_ctano")) + "', " +
					 " CTBNo='" + CommBase.CheckString(json_obj.getString("ext_ctbno")) + "', " +
					 " CTCNo='" + CommBase.CheckString(json_obj.getString("ext_ctcno")) + "', " +
					 " meter_accuracy=" + CommBase.strtoi(json_obj.getString("ext_meter_accuracy")) + ", " +
					 " moneyman='" + CommBase.CheckString(json_obj.getString("ext_moneyman")) + "', " +
					 " moneyman_telno1='" + CommBase.CheckString(json_obj.getString("ext_moneyman_telno1")) + "', " +
					 " seal_flag=" + CommBase.strtoi(json_obj.getString("ext_seal_flag")) + ", " +
					 " ctrlline_addr='" + CommBase.CheckString(json_obj.getString("ext_ctrlline_addr")) + "', " +
					 " ctrlfh_explain='" + CommBase.CheckString(json_obj.getString("ext_ctrlfh_explain")) + "' " +
					 " where rtu_id=" + CommBase.strtoi(json_obj.getString("rtu_id")) + " and " +
					 " zjg_id=" + CommBase.strtoi(json_obj.getString("zjg_id"));

		JDBCDao			dao	  = new JDBCDao();
		
		if (dao.executeUpdate(sql)) {
			result = SDDef.SUCCESS;
		}
		else {
			result = SDDef.FAIL;
		}
		return SDDef.SUCCESS;
		
/*		
		
		String sql = "update e.rtu_id as ext_rtu_id, e.zjg_id as ext_zjg_id, c.describe as cons_desc, c.busi_no as cons_busi_no, c.subst_id as cons_subst_id, c.line_id as cons_line_id, a.simcard_id as rtu_simcard_id, " +
		 "e.month_dl as ext_month_dl, e.month_money as ext_month_money, d.feeproj_id as zjgpay_feeproj, e.use_type as ext_use_type, e.moneydl_per as ext_moneydl_per, e.CTANo as ext_ctano, e.CTBNo as ext_ctbno, " + 
		 "e.CTCNo as ext_ctcno, e.meter_accuracy as ext_meter_accuracy, a.inst_date as rtu_inst_date, a.asset_no as rtu_asset_no, e.seal_flag as ext_seal_flag, e.ctrlline_addr as ext_ctrlline_addr, " + 
		 "e.ctrlfh_explain as ext_ctrlfh_explain, c.fz_man as cons_fz_man, c.tel_no1 as cons_tel_no1, e.moneyman as ext_moneyman, e.moneyman_telno1 as ext_moneyman_telno1 " +
		 "from zjg_extpara as e, rtupara as a, conspara as c, zjgpay_para as d where a.cons_id=c.id and a.id=e.rtu_id and a.id=d.rtu_id and e.zjg_id = d.zjg_id and e.rtu_id=" + result.split(",")[0] + " and e.zjg_id =" + result.split(",")[1];

		ResultSet rs = dao.executeQuery(sql);		
*/		
//		System.out.print(tmp_str);
/*		
		HibDao  hib_dao = new HibDao();
		try {
			if(zjgpaypara.getRtuId() == null || zjgpaypara.getRtuId() == -1) {
				result = SDDef.FAIL;
				return SDDef.SUCCESS;
			}
			
			if(zjgpaypara.getZjgId() == null) {
				result = SDDef.FAIL;
				return SDDef.SUCCESS;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		if (hib_dao.saveOrUpdate(zjgpaypara)) {
			
			//YDOperlog.operlog(CommFunc.getUser().getUserpara().getId(), SDDef.LOG_UPDATE, "修改终端["+ zjgpaypara.getRtuId() +"]下总加组["+zjgpaypara.getZjgId()+"]的费控参数档案");
			
			result = SDDef.SUCCESS;
		}else{
			result = SDDef.FAIL;
		}

		return SDDef.SUCCESS;
*/				
	}	
	
	public String plEdit(){
		if(pl == null || pl.isEmpty()){
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		HibDao  hib_dao = new HibDao();
		String rtu_zjg[] = pl.split("_");
		String con = "";
		boolean flag = true;
		for (int i = 0; i < rtu_zjg.length; i++) {
			con = " rtuId=" + rtu_zjg[i].split(",")[0] + " and zjgId=" + rtu_zjg[i].split(",")[1];
			String hql = "update " + YDTable.TABLECLASS_ZJGPAYPARA + " set " + result + " where " + con;
			if (!hib_dao.updateByHql(hql)) {
				flag = false;
				break;
			}
		}
		
		if(flag){
			result = SDDef.SUCCESS;
		}else{
			result = SDDef.FAIL;
		}
		
		return SDDef.SUCCESS;
	}
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : delZjgParaById
	* <p>
	* <p>DESCRIPTION : 按mp_id从大到小删除多个数量的计量点档案记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>备注 此处删除N个最大计量点，并且删除与之相关联的计量点 
	* <p>zkz 20110120 2230
	*-----------------------------------------------------------*/
	public String delZjgParaById()
	{
		HibDao  hib_dao = new HibDao();
		if(result==null){
			result=SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		int len = 2;
		String[] tmp = result.split(SDDef.SPLITCOMA);
		String[] id = tmp[0].split(SDDef.JSONSPLIT);
		String[] hql = new String[len*id.length];
		for(int i=0;i<id.length;i++){
			hql[i*len] = "delete from "+YDTable.TABLECLASS_ZJGPAYPARA +" as a where a.zjgId="+id[i]+" and a.rtuId="+tmp[1];
			hql[i*len+1] = "update "+YDTable.TABLECLASS_RTUPARA+" set zjgNum=zjgNum-1 where id=" + tmp[1];
		}
		if(hib_dao.updateByHql(hql)){
			result = SDDef.SUCCESS;
		}else{
			result = SDDef.FAIL;
		}
		return SDDef.SUCCESS;
	}
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : getZjgParaById
	* <p>
	* <p>DESCRIPTION : 通过ID获取总加组档案记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@JSON(serialize = false)
	public String getExtParaById() throws Exception
	{
		StringBuffer ret_buf  = new StringBuffer();
		JDBCDao			dao	  = new JDBCDao();

		String sql = "select e.rtu_id as ext_rtu_id, e.zjg_id as ext_zjg_id, c.describe as cons_desc, c.busi_no as cons_busi_no, c.subst_id as cons_subst_id, c.line_id as cons_line_id, a.simcard_id as rtu_simcard_id, " +
					 "e.month_dl as ext_month_dl, e.month_money as ext_month_money, d.feeproj_id as zjgpay_feeproj, e.use_type as ext_use_type, e.moneydl_per as ext_moneydl_per, e.CTANo as ext_ctano, e.CTBNo as ext_ctbno, " + 
					 "e.CTCNo as ext_ctcno, e.meter_accuracy as ext_meter_accuracy, a.inst_date as rtu_inst_date, a.asset_no as rtu_asset_no, e.seal_flag as ext_seal_flag, e.ctrlline_addr as ext_ctrlline_addr, " + 
					 "e.ctrlfh_explain as ext_ctrlfh_explain, c.fz_man as cons_fz_man, c.tel_no1 as cons_tel_no1, e.moneyman as ext_moneyman, e.moneyman_telno1 as ext_moneyman_telno1 " +
					 "from zjg_extpara as e, rtupara as a, conspara as c, zjgpay_para as d where a.cons_id=c.id and a.id=e.rtu_id and a.id=d.rtu_id and e.zjg_id = d.zjg_id and e.rtu_id=" + result.split(",")[0] + " and e.zjg_id =" + result.split(",")[1];
		
		ResultSet rs = null;
		try {
			rs = dao.executeQuery(sql);
			int	count = 1;
			while(rs.next()) {
				ret_buf.append(SDDef.JSONLBRACES); 
				ret_buf.append(SDDef.JSONQUOT + "ext_rtu_id" + SDDef.JSONQACQ + (rs.getInt("ext_rtu_id")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "ext_zjg_id" + SDDef.JSONQACQ + (rs.getShort("ext_zjg_id")) + SDDef.JSONCCM);
				
				ret_buf.append(SDDef.JSONQUOT + "cons_desc"    + SDDef.JSONQACQ + CommBase.CheckString(rs.getString("cons_desc")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "cons_busi_no" + SDDef.JSONQACQ + CommBase.CheckString(rs.getString("cons_busi_no")) + SDDef.JSONCCM);
				
				ret_buf.append(SDDef.JSONQUOT + "cons_subst_id" + SDDef.JSONQACQ + (rs.getShort("cons_subst_id")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "cons_line_id" + SDDef.JSONQACQ + (rs.getShort("cons_line_id")) + SDDef.JSONCCM);
				
				ret_buf.append(LoadLineJString(rs.getShort("cons_line_id")));
				
				ret_buf.append(SDDef.JSONQUOT + "rtu_simcard_id" + SDDef.JSONQACQ + (rs.getShort("rtu_simcard_id")) + SDDef.JSONCCM);
				ret_buf.append(LoadSimCardJString(rs.getShort("rtu_simcard_id")));
				
				ret_buf.append(SDDef.JSONQUOT + "ext_month_dl" + SDDef.JSONQACQ + (rs.getDouble("ext_month_dl")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "ext_month_money" + SDDef.JSONQACQ + (rs.getDouble("ext_month_money")) + SDDef.JSONCCM);
				
				ret_buf.append(SDDef.JSONQUOT + "zjgpay_feeproj" + SDDef.JSONQACQ + (rs.getShort("zjgpay_feeproj")) + SDDef.JSONCCM);
				ret_buf.append(LoadYffRateJString(rs.getShort("zjgpay_feeproj")));
				
				ret_buf.append(SDDef.JSONQUOT + "ext_use_type" + SDDef.JSONQACQ + (rs.getInt("ext_use_type")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "ext_moneydl_per" + SDDef.JSONQACQ + (rs.getInt("ext_moneydl_per")) + SDDef.JSONCCM);
				
				ret_buf.append(SDDef.JSONQUOT + "ext_ctano" + SDDef.JSONQACQ + CommBase.CheckString(rs.getString("ext_ctano")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "ext_ctbno" + SDDef.JSONQACQ + CommBase.CheckString(rs.getString("ext_ctbno")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "ext_ctcno" + SDDef.JSONQACQ + CommBase.CheckString(rs.getString("ext_ctcno")) + SDDef.JSONCCM);
				
				ret_buf.append(SDDef.JSONQUOT + "ext_meter_accuracy" + SDDef.JSONQACQ + (rs.getInt("ext_meter_accuracy")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "rtu_inst_date" + SDDef.JSONQACQ + (rs.getInt("rtu_inst_date")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "rtu_asset_no" + SDDef.JSONQACQ + CommBase.CheckString(rs.getString("rtu_asset_no")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "ext_seal_flag" + SDDef.JSONQACQ + (rs.getInt("ext_seal_flag")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "ext_ctrlline_addr" + SDDef.JSONQACQ + CommBase.CheckString(rs.getString("ext_ctrlline_addr")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "ext_ctrlfh_explain" + SDDef.JSONQACQ + CommBase.CheckString(rs.getString("ext_ctrlfh_explain")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "cons_fz_man" + SDDef.JSONQACQ + CommBase.CheckString(rs.getString("cons_fz_man")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "cons_tel_no1" + SDDef.JSONQACQ + CommBase.CheckString(rs.getString("cons_tel_no1")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "ext_moneyman" + SDDef.JSONQACQ + CommBase.CheckString(rs.getString("ext_moneyman")) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "ext_moneyman_telno1" + SDDef.JSONQACQ + CommBase.CheckString(rs.getString("ext_moneyman_telno1")) + SDDef.JSONCCM);
				
				ret_buf.append(LoadFkJString(rs.getInt("ext_rtu_id")));
				ret_buf.append(LoadMpJString(rs.getInt("ext_rtu_id")));
				
				ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACES.charAt(0));			
				count++;
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dao.closeRs(rs);
		}
		
		if (result == null) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}

		result = ret_buf.toString();			
		
		return SDDef.SUCCESS;
	}

	public String LoadSimCardJString(short simcard_id)
	{
		JDBCDao	 	 dao      = new JDBCDao();		
		String   	 sql      = "select tel_no from simcard where id = " + simcard_id;
		
		StringBuffer ret_buf  = new StringBuffer();
		
		try {
			ResultSet rs = dao.executeQuery(sql);
			while(rs.next()) {
				ret_buf.append(SDDef.JSONQUOT + "rtu_simcard_str" + SDDef.JSONQACQ + CommBase.CheckString(rs.getString("tel_no")) + SDDef.JSONCCM);
				break;				
			}
		}
		catch (SQLException e) {
		}
		
		if (ret_buf.length() <= 0) {
			ret_buf.append(SDDef.JSONQUOT + "rtu_simcard_str" + SDDef.JSONQACQ + "" + SDDef.JSONCCM);
		}
		
		return ret_buf.toString();
	}
	
	public String LoadYffRateJString(short feeproj_id)
	{
		JDBCDao	 	 dao      = new JDBCDao();
		String   	 sql      = "select describe from yffratepara where id = " + feeproj_id;
		
		StringBuffer ret_buf  = new StringBuffer();
		ResultSet rs = null;
		try {
			rs = dao.executeQuery(sql);
			while(rs.next()) {
				ret_buf.append(SDDef.JSONQUOT + "zjgpay_feeproj_str" + SDDef.JSONQACQ + CommBase.CheckString(rs.getString("describe")) + SDDef.JSONCCM);
				break;
			}
		}
		catch (SQLException e) {
			
		}finally {
			dao.closeRs(rs);
		}
		
		if (ret_buf.length() <= 0) {
			ret_buf.append(SDDef.JSONQUOT + "zjgpay_feeproj_str" + SDDef.JSONQACQ + "" + SDDef.JSONCCM);
		}
		
		return ret_buf.toString();
	}
	
	public String LoadFkJString(int rtu_id)
	{
		JDBCDao	 	 dao      = new JDBCDao();
		String   	 sql      = "select top 1 id, switchname from rtupoll_para where rtu_id = " + rtu_id + " order by id";
		
		StringBuffer ret_buf  = new StringBuffer();
		ResultSet rs = null;
		try {
			rs = dao.executeQuery(sql);
			while(rs.next()) {
				ret_buf.append(SDDef.JSONQUOT + "rtupoll_id" + SDDef.JSONQACQ + rs.getInt("id") + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "rtupoll_str" + SDDef.JSONQACQ +  CommBase.CheckString(rs.getString("switchname")) + SDDef.JSONCCM);
				break;				
			}
		}
		catch (SQLException e) {
		} finally {
			dao.closeRs(rs);
		}
		
		if (ret_buf.length() <= 0) {
			ret_buf.append(SDDef.JSONQUOT + "rtupoll_id" + SDDef.JSONQACQ + "-1" + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + "rtupoll_str" + SDDef.JSONQACQ + "" + SDDef.JSONCCM);
		}
		
		return ret_buf.toString();
	}
	
	public String LoadMpJString(int rtu_id)
	{
		JDBCDao	 	 dao      = new JDBCDao();
		String       sql      = " select top 1 c.id as mp_id, c.pt_numerator as pt_numerator, c.pt_denominator as pt_denominator, c.pt_ratio as pt_ratio, " + 
								" c.ct_numerator as ct_numerator, c.ct_denominator as ct_denominator, c.ct_ratio as ct_ratio, b.factory as factory, b.asset_no as asset_no " + 
								" from meterpara as b, mppara as c where b.rtu_id = c.rtu_id and b.mp_id = c.id and b.rtu_id=" + rtu_id + 
								" order by c.id";
		
		StringBuffer ret_buf  = new StringBuffer();
		ResultSet rs = null;
		try {
			rs = dao.executeQuery(sql);
			while(rs.next()) {
				ret_buf.append(SDDef.JSONQUOT + "meter_mpid"    	    + SDDef.JSONQACQ + rs.getInt("mp_id") 			+ SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "meter_ptnumerator"     + SDDef.JSONQACQ + rs.getInt("pt_numerator") 	+ SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "meter_ptdenominator"   + SDDef.JSONQACQ + rs.getInt("pt_denominator") 	+ SDDef.JSONCCM);				
				ret_buf.append(SDDef.JSONQUOT + "meter_ptratio" 		+ SDDef.JSONQACQ + rs.getDouble("pt_ratio") 	+ SDDef.JSONCCM);				
				ret_buf.append(SDDef.JSONQUOT + "meter_ctnumerator" 	+ SDDef.JSONQACQ + rs.getDouble("ct_numerator") + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "meter_ctdenominator" 	+ SDDef.JSONQACQ + rs.getDouble("ct_denominator")+ SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "meter_ctratio" 		+ SDDef.JSONQACQ + rs.getDouble("ct_ratio") 	+ SDDef.JSONCCM);				
				ret_buf.append(SDDef.JSONQUOT + "meter_factory"         + SDDef.JSONQACQ + rs.getInt("factory") 		+ SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "meter_assetno"         + SDDef.JSONQACQ + CommBase.CheckString(rs.getString("asset_no")) + SDDef.JSONCCM);				
				break;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dao.closeRs(rs);
		}
		
		if (ret_buf.length() <= 0) {
			ret_buf.append(SDDef.JSONQUOT + "meter_mpid"    		+ SDDef.JSONQACQ + -1  + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + "meter_ptnumerator"     + SDDef.JSONQACQ + "0" + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + "meter_ptdenominator"   + SDDef.JSONQACQ + "1" + SDDef.JSONCCM);				
			ret_buf.append(SDDef.JSONQUOT + "meter_ptratio" 		+ SDDef.JSONQACQ + "0" + SDDef.JSONCCM);				
			ret_buf.append(SDDef.JSONQUOT + "meter_ctnumerator" 	+ SDDef.JSONQACQ + "0" + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + "meter_ctdenominator" 	+ SDDef.JSONQACQ + "1" + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + "meter_ctratio" 		+ SDDef.JSONQACQ + "0" + SDDef.JSONCCM);				
			ret_buf.append(SDDef.JSONQUOT + "meter_factory"         + SDDef.JSONQACQ + "0" + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + "meter_assetno"         + SDDef.JSONQACQ + ""  + SDDef.JSONCCM);
		}
		
		return ret_buf.toString();
	}

	public String LoadLineJString(short line_id)
	{
		JDBCDao	 	 dao      = new JDBCDao();
		String   	 sql      = "select describe from linepara where id = " + line_id;
		
		StringBuffer ret_buf  = new StringBuffer();
		ResultSet rs = null;
		try {
			rs = dao.executeQuery(sql);
			while(rs.next()) {
				ret_buf.append(SDDef.JSONQUOT + "cons_line_str" + SDDef.JSONQACQ +  CommBase.CheckString(rs.getString("describe")) + SDDef.JSONCCM);
				break;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dao.closeRs(rs);
		}
		
		if (ret_buf.length() <= 0) {
			ret_buf.append(SDDef.JSONQUOT + "cons_line_str" + SDDef.JSONQACQ +  "" + SDDef.JSONCCM);
		}
		
		return ret_buf.toString();
	}
	
	public List loadAllLinePara()
	{
		HibDao 		 hib_dao = new HibDao();		
		String 		 hql	 = "from LinePara as a";

		return hib_dao.loadAll(hql);
	}
	
	public LinePara findLinePara(short line_id, List list)
	{
		if (list == null || list.size() <= 0) return null;
		
		int i = 0;
		LinePara line_para;
		
		for (i = 0; i < list.size(); i++) {
			line_para = (LinePara)list.get(i);
			if (line_para.getId() == line_id) return line_para;
		}
		
		return null;
	}
	
	public LinePara findLinePara(Object line_id, List list) 
	{
		if (line_id == null) return null;
		
		int val;
		try{
			val = Integer.parseInt(line_id.toString());
		}catch (Exception e) {
			return null;
		}
		return findLinePara((short)val, list);
	}
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : execute
	* <p>
	* <p>DESCRIPTION : 分页获取总加组档案记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		List         list      = null;
		StringBuffer ret_buf   = new StringBuffer();
		int 		 page	   = Integer.parseInt(pageNo == null? "1" : pageNo);
		int 		 pagesize  = Integer.parseInt(pageSize == null? "10" : pageSize);
		
		String describe   = null;
		String idFromTree = null;
		String param2     = null;
		String sub_sql    = null;

		LinePara    line_para = null;
		List		line_list = loadAllLinePara();
		
		StringBuffer getRtuSql  = new StringBuffer();
		String sql=new String();
		
		getRtuSql.append("e.rtu_id as ext_rtu_id, e.zjg_id as ext_zjg_id, c.describe as cons_desc, c.busi_no as cons_busi_no, c.subst_id as cons_subst_id, c.line_id as cons_line_id, ").
				  append("e.month_dl as ext_month_dl, e.month_money as ext_month_money, e.use_type as ext_use_type, e.moneydl_per as ext_moneydl_per, e.meter_accuracy as ext_meter_accuracy, "). 
				  append("a.asset_no as rtu_asset_no, c.fz_man as cons_fz_man, c.tel_no1 as cons_tel_no1, e.moneyman as ext_moneyman, e.moneyman_telno1 as ext_moneyman_telno1 ").
				  append("from zjg_extpara as e, rtupara as a, conspara as c, zjgpara as d where a.cons_id=c.id and a.id=e.rtu_id and a.id=d.rtu_id and e.zjg_id = d.zjg_id ");
		
		StringBuffer countSql   = new StringBuffer();
		
		countSql.append("select ").
        		 append("count(e.zjg_id) ").
        		 append("from zjg_extpara as e, rtupara as a, conspara as c, zjgpara as d where a.cons_id=c.id and a.id=e.rtu_id and a.id=d.rtu_id and e.zjg_id = d.zjg_id ");
		
		if(result != null && !result.isEmpty()) {
			JSONObject jsonObj  = JSONObject.fromObject(result);
			describe   = jsonObj.getString("describe");
			param2     = jsonObj.getString("id");
			int pos = param2.indexOf("_");
			idFromTree = param2.substring(pos + 1);
			
			sub_sql = " and a.app_type="+SDDef.APPTYPE_ZB;
			
			if (param2.equals(SDDef.GLOBAL_KE2)) {
				
			} else if (param2.startsWith(YDTable.TABLECLASS_ORGPARA)) {
				sub_sql = " and c.org_id=" + idFromTree;				
			} else if (param2.startsWith(YDTable.TABLECLASS_LINEFZMAN)) {
				sub_sql = " and c.line_fzman_id=" + idFromTree;				
			} else if (param2.startsWith(YDTable.TABLECLASS_CONSPARA)){
				sub_sql = " and c.id=" + idFromTree;
			} else {
				sub_sql = " and a.id=" + idFromTree;
			}
			
			getRtuSql.append(sub_sql);
			countSql.append(sub_sql);
		}
		
		if(describe != null && !describe.isEmpty()){
			sub_sql = " and c.describe like '%" + describe + "%'";
			getRtuSql.append(sub_sql);
			countSql.append(sub_sql);
		}
		getRtuSql.append(" order by e.rtu_id, e.zjg_id desc ) x order by x.ext_rtu_id, x.ext_zjg_id " );		
		
		SqlPage sqlPage = new SqlPage(page, pagesize);
		sqlPage.setTotalrecords(countSql.toString());
		
		count = sqlPage.getTotalrecords();
		sql=getRtuSql.toString();
		
		if(pagesize == 0){
			 sql="select top "+count+" * from (select top "+ count +" " + sql;
		}else{
			if(pagesize > count){
				sql = "select top "+pagesize+" * from (select top "+ count +" " + sql;
			}else{
				sql = "select top "+pagesize+" * from (select top "+(count - pagesize*(page-1)) + " " + sql;
			}
		}
		if(sqlPage.getTotalrecords()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		if (pagesize == 0) {
			pagesize = sqlPage.getTotalrecords();
			sqlPage.setPagesize(pagesize);
		}
		ret_buf.append(SDDef.JSONTOTAL + sqlPage.getTotalrecords() + SDDef.JSONPAGEROWS);
		
		list = sqlPage.getRecord(sql);
		int no = pagesize*(sqlPage.getCurrentpage()-1)+1;
		
		for (int i = 0; i < list.size(); i++) {
			ret_buf.append(SDDef.JSONLBRACES);
			Map<String, Object> map =  (Map<String, Object>) list.get(i);	
			if(pl == null){
				//ID
				ret_buf.append(SDDef.JSONQUOT + "id" +   SDDef.JSONQACQ +map.get("ext_rtu_id") + "," + map.get("ext_zjg_id") +  SDDef.JSONNZDATA); 	//i++;i=2
				//序号
				ret_buf.append(SDDef.JSONQUOT + (no++) +  SDDef.JSONCCM);
				//户名
				ret_buf.append(SDDef.JSONQUOT +  "<a href='javascript:redoOnRowDblClicked(&quot;"+ map.get("ext_rtu_id")+","+map.get("ext_zjg_id")+"," +CommBase.CheckString(map.get("cons_desc")) +"&quot;);'>" + CommBase.CheckString(map.get("cons_desc")) + "</a>" + SDDef.JSONCCM);
			}else{
				//ID
				ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + map.get("ext_rtu_id") + "," +  map.get("ext_zjg_id") +  SDDef.JSONDATA);//i++;i=2
				//序号
				ret_buf.append(SDDef.JSONQUOT + (no++) +  SDDef.JSONCCM);
				//户名
				ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("cons_desc")) + SDDef.JSONCCM);
			}

			//户号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("cons_busi_no")) + SDDef.JSONCCM);			
			
			line_para = findLinePara(map.get("cons_line_id"), line_list);
			
			//供电电源(线路名称)
			ret_buf.append(SDDef.JSONQUOT + (line_para == null ? "未知" : CommBase.CheckString(line_para.getDescribe())) + SDDef.JSONCCM);						

			//月均电量(万kWh)
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("ext_month_dl")) + SDDef.JSONCCM);
			
			//月均电费(万元)
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("ext_month_money")) + SDDef.JSONCCM);			
			
			//用电类别
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_POWER_USETYPE,    map.get("ext_use_type"))  + SDDef.JSONCCM);			
			
			//电量比例(%)
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("ext_moneydl_per")) + SDDef.JSONCCM);			

			//电能表误差
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_METERACCURACY,    map.get("ext_meter_accuracy"))  + SDDef.JSONCCM);
			
			//终端编号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("rtu_asset_no")) + SDDef.JSONCCM);
			
			//用电联系人
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("cons_fz_man")) + SDDef.JSONCCM);
			
			//用电联系电话
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("cons_tel_no1")) + SDDef.JSONCCM);
			
			//财务联系人
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("ext_moneyman")) + SDDef.JSONCCM);
			
			//财务联系电话
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("ext_moneyman_telno1")) + SDDef.JSONCCM);
			
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);
		}	
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRBRBR);
		result = ret_buf.toString();		
		
		return SDDef.SUCCESS;
	}
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
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

	public ZjgPaypara getZjgpaypara() {
		return zjgpaypara;
	}

	public void setZjgpaypara(ZjgPaypara zjgpaypara) {
		this.zjgpaypara = zjgpaypara;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public void setPl(String pl) {
		this.pl = pl;
	}

	public String getPl() {
		return pl;
	}
	
}

