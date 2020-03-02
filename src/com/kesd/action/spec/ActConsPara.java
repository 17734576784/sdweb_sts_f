package com.kesd.action.spec;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.YDTable;
import com.kesd.comnt.ComntDef;
import com.kesd.comntpara.ComntUseropDef;
import com.kesd.dbpara.YffManDef;
import com.kesd.dbpara.YffRatePara;
import com.kesd.util.OnlineRtu;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
import com.libweb.comnt.ComntFunc;
import com.libweb.dao.HibDao;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;


public class ActConsPara extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6268085290910467823L;

	private String 		result;
	private String 		pageNo;		//页号
	private String 		pageSize;	//每页记录数
	private String		field;		//所需查询的数据库字段
	private String 		rtuId;
	private String		zjgId;
	private String		sghz_flag;	//是否只查询376终端标识	20130906

	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>NAME        : getsearchList	
	* <p>DESCRIPTION : 分页获取电表档案记录
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	*-----------------------------------------------------------*/
	@JSON(serialize = false)
	public String getsearchList(){
		List<Map<String, Object>>	list		= null;
		StringBuffer 	ret_buf		= new StringBuffer();

		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}

		JDBCDao j_dao = new JDBCDao();
		String 			sql			= getSql();
		list = j_dao.result(sql);
		if(list.size()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
	
		
		ret_buf.append(SDDef.JSONROWSTITLE);
		for(int index =0;index<list.size();index++){
			Map<String,Object> map=list.get(index);
			
			ret_buf.append(SDDef.JSONLBRACES);
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + map.get("rtu_id") + "_" + map.get("zjg_id") + "_" + map.get("resident_id") + SDDef.JSONNZDATA);
			ret_buf.append(SDDef.JSONQUOT + (index+1) + SDDef.JSONCCM);//序号
			
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("rtu_desc")) + SDDef.JSONCCM);//终端名称
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("cons_desc")) + SDDef.JSONCCM);//客户名称
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("busi_no")) + SDDef.JSONCCM);//营业户号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("cons_addr")) + SDDef.JSONCCM);//客户地址
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("cons_telno")) + SDDef.JSONCCM);//联系电话-tel2 移动手机号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("org_desc")) + SDDef.JSONCCM);//所属供电所
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("fzman_desc")) + SDDef.JSONCCM);//所属线路负责人
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("rtu_addr")) + SDDef.JSONCCM);//终端地址
			
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_METERFACTORY, map.get("rtu_factory"))  + SDDef.JSONCCM);//生产厂家
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("zjg_desc")) + SDDef.JSONCCM);//总加组名称-费控单元
			ret_buf.append(SDDef.JSONQUOT + "[" +CommBase.CheckNum(map.get("cardmeter_type")) + "]" +Rd.getDict(Dict.DICTITEM_CARDMETER_TYPE, map.get("cardmeter_type")) + SDDef.JSONCCM);//写卡类型描述
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckNum(map.get("writecard_no")) + SDDef.JSONCCM);//写卡户号
			
			ret_buf.append(SDDef.JSONQUOT + map.get("prot_type")  + SDDef.JSONCCM);		//通讯规约
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("rm_desc")) + SDDef.JSONCCM);//终端型号

			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);
	
		}

		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();

		return SDDef.SUCCESS;
	}


	private String getSql(){
		StringBuffer 	sbfSql 	= new StringBuffer();
		
//		sbfSql.append("select a.id as rtu_id,a.asset_no as asset_no,z.zjg_id as zjg_id,z.describe as zjg_desc,z.clp_num as clp_num,");
//		sbfSql.append("z.clp_ids as clp_ids, z.zf_flag as zf_flag, z.as_flag as as_flag, m.describe as rm_desc,");
//		sbfSql.append("a.factory as rtu_factory, a.made_no as rtu_madeno, a.prot_type as prot_type,c.id as cons_id,");
//		sbfSql.append("c.busi_no as busi_no,c.describe as cons_desc, c.addr as cons_addr, c.tel_no2 as cons_telno, o.describe as org_desc, f.describe as fzman_desc, ");
//		sbfSql.append("a.rtu_model as rtu_model, p.switchtype as switchtype, p.pulsetime as pulsetime, mp.pt_ratio, mp.ct_ratio");
//		sbfSql.append(" from rtupara as a,conspara as c,dbo.zjgpay_para as e,zjgpara as z,orgpara as o,line_fzman f,zjgpay_state as s,rtupoll_para as p,mppara as mp,rtumodel m ");
//		sbfSql.append(" where a.app_type = " + SDDef.APPTYPE_ZB + " and a.use_flag = 1 and a.id = z.rtu_id and a.cons_id = c.id and c.org_id = o.id and c.app_type = "+ SDDef.APPTYPE_ZB +" and z.use_flag = 1 ");
//		sbfSql.append(" and z.yff_flag = 1 and a.id = s.rtu_id and z.zjg_id = s.zjg_id and e.rtu_id = a.id and e.zjg_id = z.zjg_id and mp.rtu_id = a.id and mp.id = 1");
//		sbfSql.append(" and p.rtu_id = a.id and p.id=1 and f.org_id=o.id and c.line_fzman_id=f.id and a.rtu_model=m.id ");
//		sbfSql.append("select a.id as rtu_id,a.asset_no as asset_no,z.zjg_id as zjg_id,z.describe as zjg_desc,z.clp_num as clp_num,");
//		sbfSql.append("z.clp_ids as clp_ids, z.zf_flag as zf_flag, z.as_flag as as_flag, m.describe as rm_desc,");		
//		sbfSql.append("a.factory as rtu_factory, a.made_no as rtu_madeno, a.prot_type as prot_type,c.id as cons_id,");		
		
		sbfSql.append("select a.describe as rtu_desc,a.id as rtu_id, a.rtu_addr as rtu_addr, z.zjg_id as zjg_id, z.describe as zjg_desc,");
		sbfSql.append("m.describe as rm_desc,");
		sbfSql.append("a.factory as rtu_factory, a.made_no as rtu_madeno, a.prot_type as prot_type,");
		sbfSql.append("c.busi_no as busi_no, c.describe as cons_desc, c.addr as cons_addr, c.tel_no2 as cons_telno, o.describe as org_desc, f.describe as fzman_desc, a.rtu_model as rtu_model, e.cardmeter_type, e.writecard_no ");
		sbfSql.append("from rtupara as a,conspara as c,dbo.zjgpay_para as e,zjgpara as z,orgpara as o,line_fzman f, zjgpay_state as s, rtumodel m ");
		sbfSql.append("where a.app_type = " + SDDef.APPTYPE_ZB + " and a.use_flag = 1 and a.id = z.rtu_id and a.cons_id = c.id and c.org_id = o.id and c.app_type = "+ SDDef.APPTYPE_ZB +" and z.use_flag = 1 ");
		sbfSql.append("and z.yff_flag = 1 and a.id = s.rtu_id and z.zjg_id = s.zjg_id and e.rtu_id = a.id and e.zjg_id = z.zjg_id and ");
		sbfSql.append("f.org_id=o.id and c.line_fzman_id=f.id and a.rtu_model=m.id ");


		JSONObject jsonObj  = JSONObject.fromObject(result);
		String type 	= jsonObj.getString("type");
		String operType 	= jsonObj.getString("operType");

		if(type.equals("bd")){			//表底计费YFF_PREPAYTYPE_REMOTE
			sbfSql.append(" and e.feectrl_type = " + SDDef.YFF_FEECTRL_TYPE_RTU + " and e.cacl_type = " + SDDef.YFF_CACL_TYPE_BD + " and a.prot_type =" + ComntDef.YD_PROTOCAL_KEDYH + " and e.pay_type = " + SDDef.YFF_PREPAYTYPE_REMOTE);
		}else if(type.equals("je")){	//金额计费
			sbfSql.append(" and e.feectrl_type = " + SDDef.YFF_FEECTRL_TYPE_RTU + " and e.cacl_type = " + SDDef.YFF_CACL_TYPE_MONEY +" and e.pay_type = " + SDDef.YFF_PREPAYTYPE_REMOTE);	// + " and a.prot_type =" + ComntDef.YD_PROTOCAL_KEDYH);
		}else if(type.equals("zz")){	//主站费控
			sbfSql.append(" and e.feectrl_type = " + SDDef.YFF_FEECTRL_TYPE_MASTER + " and e.pay_type = " + SDDef.YFF_PREPAYTYPE_MASTER);
		}else if(type.equals("ks")){	//卡式
			sbfSql.append(" and e.pay_type = " + SDDef.YFF_PREPAYTYPE_CARD);
		}
		
		switch(Integer.parseInt(operType)){
		case ComntUseropDef.YFF_GYOPER_ADDCUS:		//高压操作-开户

			sbfSql.append(" and (s.cus_state is null  or s.cus_state = " + SDDef.YFF_CUSSTATE_INIT + " or s.cus_state = " + SDDef.YFF_CUSSTATE_DESTORY + " )");
			break;

		case ComntUseropDef.YFF_GYOPER_RESTART:		//高压操作-恢复
			sbfSql.append(" and  s.cus_state = " + SDDef.YFF_CUSSTATE_PAUSE);
			break;
		case -1: break;	//补写记录，不限制操作类型。
//		case ComntUseropDef.YFF_GYOPER_PAY:			//高压操作-缴费
//		case ComntUseropDef.YFF_GYOPER_REVER:		//高压操作-冲正
//		case ComntUseropDef.YFF_GYOPER_PAUSE:		//高压操作-暂停
//		case ComntUseropDef.YFF_GYOPER_DESTORY:		//高压操作-销户
//		case ComntUseropDef.YFF_GYOPER_CHANGEMETER:	//高压操作-换表换倍率
//		case ComntUseropDef.YFF_GYOPER_CHANGERATE:	//高压操作-更改费率
//		
//		case ComntUseropDef.YFF_GYCOMM_PAY:			//高压通讯-缴费
		default:			
			sbfSql.append(" and  s.cus_state = " + SDDef.YFF_CUSSTATE_NORMAL);
			break;
		 	
//		case ComntUseropDef.YFF_GYOPER_CHANGERATE:	//高压操作-更改费率
//		case ComntUseropDef.YFF_GYOPER_PROTECT:		//高压操作-保电
//		case ComntUseropDef.YFF_GYOPER_UDPATESTATE:	//高压操作-强制状态更新
//		case ComntUseropDef.YFF_GYOPER_RECALC:		//高压操作-重新计算剩余金额
//		case ComntUseropDef.YFF_GYOPER_REWRITE:		//高压操作-补写缴费记录
//		case ComntUseropDef.YFF_GYOPER_GETREMAIN:	//高压操作-返回余额
//		case ComntUseropDef.YFF_GYOPER_GPARASTATE:	//高压操作-获得预付费参数及状态			
			
//		default:
//			break;
		}
		
		if(field != null && !field.isEmpty()){
			JSONObject jsonObj1  = JSONObject.fromObject(field);
			String orgId 	= jsonObj1.getString("orgId").trim();
			String fzmanId 	= jsonObj1.getString("fzmanId").trim();
			String rtuName  = jsonObj1.getString("rtuName").trim();
			String consName	= jsonObj1.getString("consName").trim();
			String busiNo = jsonObj1.getString("busiNo").trim();
			String rtu_addr	= jsonObj1.getString("rtu_addr").trim();
			String mobile	= jsonObj1.getString("mobile").trim();

			if(orgId != null && !orgId.equals("-1")){
				sbfSql.append(" and c.org_id=" + orgId);
			}
			if(fzmanId != null && !fzmanId.equals("-1")){
				sbfSql.append(" and c.line_fzman_id=" + fzmanId);
			}
			if(rtuName != null && !rtuName.isEmpty()){
				sbfSql.append(" and a.describe like '%" + rtuName + "%'");
			}
			if(consName != null && !consName.isEmpty()){
				sbfSql.append(" and c.describe like '%" + consName + "%'");
			}
			if(busiNo != null && !busiNo.isEmpty()){
				sbfSql.append(" and c.busi_no like '%" +  busiNo + "%'");
			}
			if(rtu_addr != null && !rtu_addr.isEmpty()){
				sbfSql.append(" and a.rtu_addr like '%" + rtu_addr + "%'");
			}
			if(mobile != null && !mobile.isEmpty()){
				sbfSql.append(" and c.tel_no2 like '%" + mobile + "%'");
			}
		}
		
		//20130906增加376终端查询条件
		if("yes".equals(sghz_flag)){
			sbfSql.append(" and (a.prot_type = " +  ComntDef.YD_PROTOCAL_GD376 + " or a.prot_type = " + ComntDef.YD_PROTOCAL_GD376_2013 + ")");
		}

		sbfSql.append(" order by a.id, e.zjg_id");
		return sbfSql.toString();
	}

	//add
	private String getGySearchCardSql(JSONObject j_res){
		
		String 	   consNo 		= j_res.getString("consNo");
		String 	   meterType 	= j_res.getString("meterType");
		String 	   operType	 	= j_res.getString("optype");
		

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
	
		YffManDef yffman = (YffManDef)session.getAttribute(SDDef.SESSION_USERNAME);
	    if ((yffman == null) || (yffman.getApptype() & SDDef.YFF_APPTYPE_GYQX) == 0 ) {
			return SDDef.EMPTY;
	    }
	    
	    if ((Integer.parseInt(operType) ==  ComntUseropDef.YFF_GYOPER_ADDCUS) ||
    		(Integer.parseInt(operType) ==  ComntUseropDef.YFF_GYOPER_DESTORY)) {
    	   if (yffman.getOpenflag() != 1) {
    		   return SDDef.EMPTY;
    	   }
		}
		else {
			if (yffman.getPayflag() != 1) {
	    	   return SDDef.EMPTY;
	    	}
		}

		StringBuffer 	sbfSql 	= new StringBuffer();

		sbfSql.append("select a.id as rtu_id, a.asset_no as asset_no, z.zjg_id as zjg_id, z.describe as zjg_desc,");
		sbfSql.append("m.describe as rm_desc, a.factory as rtu_factory, a.made_no as rtu_madeno, a.prot_type as prot_type,");
		sbfSql.append("c.busi_no as busi_no, c.describe as cons_desc, c.addr as cons_addr, c.tel_no2 as cons_telno, ");
		sbfSql.append("o.describe as org_desc, f.describe as fzman_desc, a.rtu_model as rtu_model, e.cardmeter_type, e.writecard_no ");
		sbfSql.append("from rtupara as a,conspara as c,dbo.zjgpay_para as e,zjgpara as z,orgpara as o,line_fzman f, zjgpay_state as s, rtumodel m ");
		sbfSql.append("where a.app_type = " + SDDef.APPTYPE_ZB + " and a.use_flag = 1 and a.id = z.rtu_id and a.cons_id = c.id and c.org_id = o.id and c.app_type = "+ SDDef.APPTYPE_ZB +" and z.use_flag = 1 ");
		sbfSql.append("and z.yff_flag = 1 and a.id = s.rtu_id and z.zjg_id = s.zjg_id and e.rtu_id = a.id and e.zjg_id = z.zjg_id and ");
		sbfSql.append("f.org_id=o.id and c.line_fzman_id=f.id and a.rtu_model=m.id ");
		
		switch(Integer.parseInt(operType)){
		case ComntUseropDef.YFF_GYOPER_ADDCUS:		//高压操作-开户
			sbfSql.append(" and (s.cus_state is null  or s.cus_state = " + SDDef.YFF_CUSSTATE_INIT + " or s.cus_state = " + SDDef.YFF_CUSSTATE_DESTORY + " )");
			break;
		case ComntUseropDef.YFF_GYOPER_RESTART:		//高压操作-恢复
			sbfSql.append(" and  s.cus_state = " + SDDef.YFF_CUSSTATE_PAUSE);
			break;
		case -1: break;//清空卡页面，不限制用户状态	
		default:			
			sbfSql.append(" and  s.cus_state = " + SDDef.YFF_CUSSTATE_NORMAL);
			break;
		}

		if (yffman.getRank() == SDDef.YFF_RANK_ORG) {
			sbfSql.append(" and c.org_id=" + yffman.getOrgId());	//判断 -1？
		}
		else if (yffman.getRank() == SDDef.YFF_RANK_FZMAN) {
			sbfSql.append(" and c.org_id=" + yffman.getOrgId() + " and c.line_fzman_id=" + yffman.getFzmanId());
		}
		
//		if (yffman.getRank() == SDDef.YFF_RANK_ORG) {
//			sbfSql.append(" and c.org_id=" + yffman.getOrgId());	//判断 -1？
//			if (yffman.getFzmanId() != -1) {
//				sbfSql.append(" and c.line_fzman_id=" + yffman.getFzmanId());
//			}
//		}
	
		if(meterType.equals(SDDef.YFF_METER_TYPE_ZNK)){
			//sbfSql.append(" and e.cardmeter_type = " + SDDef.YFF_CARDMTYPE_KE001 + " and (c.busi_no = '" + consNo  + "' or c.busi_no = '" + consNo.replaceFirst("0*", "")  + "')");
			sbfSql.append(" and e.cardmeter_type = " + SDDef.YFF_CARDMTYPE_KE001 + " and c.busi_no like '%" + consNo.replaceFirst("0*", "")  + "'");
		}else if(meterType.equals(SDDef.YFF_METER_TYPE_6103)){
			//sbfSql.append(" and e.cardmeter_type = " + SDDef.YFF_CARDMTYPE_KE003 + " and (e.writecard_no = '" + consNo  + "' or e.writecard_no = '" +  consNo.replaceFirst("0*", "")  + "')");
			sbfSql.append(" and e.cardmeter_type = " + SDDef.YFF_CARDMTYPE_KE003 + " and e.writecard_no like '%" +  consNo.replaceFirst("0*", "")  + "'");
		}
		
		return sbfSql.toString();
	}
	
	int getZjgMpids(int clp_num, String clp_ids, short mp_ids[]) {
		
		int ret_num = 0;
		
		for (int i = 0; i < clp_ids.length(); i++) {
			
			if (clp_ids.charAt(i) == '0') continue;
			else {
				mp_ids[ret_num] = (short)i;
			}
			
			ret_num++;
			
			if (ret_num >= mp_ids.length ||
				ret_num >= clp_num) break;
		}		
		return ret_num;
	}
	
	String getZjgRate(int rtu_id, short zjg_id) {
		JDBCDao j_dao 	= new JDBCDao();
		ResultSet rs 	= null;	
		
		String sql_cmd1 = "select clp_num, clp_ids from zjgpara where rtu_id=" + rtu_id + " and zjg_id=" + zjg_id; 
		
		int clp_num = 0;
		String clp_ids = null;
		
		boolean validf = false;
		double pt_ratio = 0, ct_ratio = 0;
		String meter_id = "";
		try {
			rs = j_dao.executeQuery(sql_cmd1);
			if (rs.next()) {
				clp_num  = rs.getByte("clp_num");
				clp_ids  = rs.getString("clp_ids");
				
				validf = true;
			}
			
			if (validf) {
				short mp_ids[] = new short[64];	
				ComntFunc.arraySet(mp_ids, (short)0);
				int r_clp_num = getZjgMpids(clp_num, clp_ids, mp_ids);
				if (r_clp_num > 0) {
					String sql_cmd2 = "select mp.pt_ratio, mp.ct_ratio, m.meter_id from mppara as mp, meterpara as m where mp.rtu_id = m.rtu_id and mp.id = m.mp_id and mp.rtu_id=" + rtu_id + " and mp.id=" + mp_ids[0];
					
					rs = j_dao.executeQuery(sql_cmd2);
					
					if (rs.next()) {
						pt_ratio  = rs.getDouble("pt_ratio");
						ct_ratio  = rs.getDouble("ct_ratio");
						meter_id  = CommBase.CheckNum(rs.getString("meter_id"));
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			j_dao.closeRs(rs);
		}
		
		JSONObject j_obj =  new JSONObject(); 
		j_obj.put("pt_ratio", 	pt_ratio);
		j_obj.put("ct_ratio", 	ct_ratio);
		j_obj.put("ratio", 		pt_ratio * ct_ratio);
		j_obj.put("meter_id",   meter_id);
		
		return j_obj.toString();
	}
	
	@JSON(serialize = false)
	public String getConsPara2() {
//		if(result == null || result.isEmpty()){
//			result = SDDef.EMPTY;
//			return SDDef.SUCCESS;
//		}

		int   rtu_id = CommBase.strtoi(rtuId);
		short zjg_id = (short)CommBase.strtoi(zjgId);
		
		JSONObject j_obj = JSONObject.fromObject(getZjgRate(rtu_id, zjg_id));
		
		
		boolean onlineflag = false;
		OnlineRtu.RTUSTATE_ITEM rtustate_item = OnlineRtu.getOneRtuState(rtu_id);
		if ((rtustate_item != null) && (rtustate_item.comm_state == 1)) onlineflag = true;

		String t_str = onlineflag ? "在线" : "离线";
		j_obj.put("onlineflag", t_str);
		
//		result = "{ratio:" + ratio + ", onlineflag:\"" + t_str + "\"}";
		result = j_obj.toString();
		
		return SDDef.SUCCESS;
	}
	
	/**
	 * 返回换表信息
	 */
	
	static class ChgMeterInfo {
		short id;
		String desc;
		double ptfm;
		double ptfz;
		double pt;
		double ctfm;
		double ctfz;
		double ct;
		
	}
	
	public String retChgMeterInfo(){
		
		JDBCDao j_dao = new JDBCDao();
		String 	sql	= "";
		ResultSet rs = null;
		StringBuffer ret = new StringBuffer();
		
		try{
			String sql_mp ="select id,describe,pt_denominator ptfm,pt_numerator ptfz,ct_denominator ctfm,ct_numerator ctfz,pt_ratio pt,ct_ratio ct from ydparaben.dbo.mppara where rtu_id=" + result +" order by id";
		
			rs = j_dao.executeQuery(sql_mp);
			rs.last();
			int num = rs.getRow();
			
			ChgMeterInfo cmi[] = new ChgMeterInfo[num];
			
			int j = 0;
			rs.beforeFirst();
			while(rs.next()){
				cmi[j] = new ChgMeterInfo();
				cmi[j].id 	= rs.getShort("id");
				cmi[j].desc	= rs.getString("describe");
				cmi[j].ptfm = rs.getDouble("ptfm");
				cmi[j].ptfz = rs.getDouble("ptfz");
				cmi[j].pt = rs.getDouble("pt");
				cmi[j].ctfm = rs.getDouble("ctfm");
				cmi[j].ctfz = rs.getDouble("ctfz");
				cmi[j].ct = rs.getDouble("ct");
				
				j++;
			}
			j_dao.closeRs(rs);

			sql = "select clp_num,clp_ids from zjgpara where rtu_id=" + result + " and zjg_id=" + zjgId;
			rs = j_dao.executeQuery(sql);
			ret.append(SDDef.JSONROWSTITLE);
			result = "";
			
			
			String clp_ids = null;
			if(rs.next()){
				num 	= rs.getInt("clp_num");
				clp_ids = rs.getString("clp_ids");
			}
			
			int mp_num = 0;
			for(int i = 0; i < clp_ids.length() - 1; i++){
				if(mp_num  >= num) break;
				if(clp_ids.charAt(i + 1) == '0') continue;
				
				int idx = findIndex((i + 1), cmi);
				if(idx < 0) continue;
				ret.append(SDDef.JSONLBRACES);
				ret.append(SDDef.JSONQUOT + "value" + SDDef.JSONQACQ + cmi[idx].id + SDDef.JSONCCM);
				ret.append(SDDef.JSONQUOT + "text" 	+ SDDef.JSONQACQ + cmi[idx].desc + SDDef.JSONCCM);
				ret.append(SDDef.JSONQUOT + "ptfz" 	+ SDDef.JSONQACQ + cmi[idx].ptfz + SDDef.JSONCCM);
				ret.append(SDDef.JSONQUOT + "ptfm" 	+ SDDef.JSONQACQ + cmi[idx].ptfm + SDDef.JSONCCM);
				ret.append(SDDef.JSONQUOT + "pt" 	+ SDDef.JSONQACQ + cmi[idx].pt + SDDef.JSONCCM);
				ret.append(SDDef.JSONQUOT + "ctfz" 	+ SDDef.JSONQACQ + cmi[idx].ctfz + SDDef.JSONCCM);
				ret.append(SDDef.JSONQUOT + "ctfm" 	+ SDDef.JSONQACQ + cmi[idx].ctfm + SDDef.JSONCCM);
				ret.append(SDDef.JSONQUOT + "ct" 	+ SDDef.JSONQACQ + cmi[idx].ct + SDDef.JSONQRBCM);
				
				mp_num++;
			}
			ret.setCharAt(ret.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret.append(SDDef.JSONRBRACES);
			
			result=ret.toString();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			j_dao.closeRs(rs);
		}
		
		return SDDef.SUCCESS;
	}
	
	private int findIndex(int mp_id, ChgMeterInfo mp_ids[]) {
		if(mp_ids == null || mp_ids.length <= 0) return -1;
		
		for(int i = 0; i < mp_ids.length; i++) {
			if(mp_id == mp_ids[i].id) return i;
		}
		return -1;
	}
	
	/**
	 * 6.5返回更改类型 初始化信息 gyjeggfl.jsp
	 * @chao
	 */
	public String retChgRateInfo(){
		
		if(result == null || result.isEmpty()) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JDBCDao j_dao = new JDBCDao();
		
		ResultSet rs = null;
		StringBuffer ret = new StringBuffer();
		
		try{
			JSONObject j_obj = JSONObject.fromObject(result);
			int rtu_id = CommBase.strtoi(j_obj.getString("rtu_id"));
			int zjg_id = CommBase.strtoi(j_obj.getString("zjg_id"));
			
			String sql ="select id,describe from mppara where rtu_id=" + rtu_id +" order by id";
			
			rs = j_dao.executeQuery(sql);
			rs.last();
			int num = rs.getRow();
			
			short mpId[]	= new short [num];
			String mpName[]	= new String[num];
			
			int j = 0;
			rs.beforeFirst();
			while(rs.next()){
				mpId[j] 	= rs.getShort("id");
				mpName[j]	= rs.getString("describe");
				
				j++;
			}
			j_dao.closeRs(rs);
			
			sql = "select a.clp_num,a.clp_ids,c.feeproj_id feeproj,c.feeproj1_id feeproj1,c.feeproj2_id feeproj2 from zjgpara a,zjgpay_para c " +
				  "where a.rtu_id=c.rtu_id and a.zjg_id=c.zjg_id and a.rtu_id=" + rtu_id + " and a.zjg_id=" + zjg_id;
			
			int lookTimes = 0;
			rs = j_dao.executeQuery(sql);
			String clp_ids = null;
			int fee1 = 0, fee2 = 0, fee3 = 0;
			if(rs.next()) {
				lookTimes = rs.getInt("clp_num");
				clp_ids = rs.getString("clp_ids");
				fee1 = rs.getInt("feeproj");
				fee2 = rs.getInt("feeproj1");
				fee3 = rs.getInt("feeproj2");
			}
			if(lookTimes > 3) lookTimes = 3;
			
			num = 0;
			ret.append(SDDef.JSONROWSTITLE);
			for(int i = 0; i < clp_ids.length() - 1; i++){
				
				if(num >= lookTimes) break;
				if(clp_ids.charAt(i + 1) == '0') continue;
				
				int idx = findIndex((i + 1), mpId);
				if(idx < 0) continue;
				
				ret.append(SDDef.JSONLBRACES);
				ret.append(SDDef.JSONQUOT + "value" + SDDef.JSONQACQ + mpId[idx] + SDDef.JSONCCM);
				ret.append(SDDef.JSONQUOT + "text" 	+ SDDef.JSONQACQ + mpName[idx] + SDDef.JSONCCM);
				ret.append(SDDef.JSONQUOT + "feeproj" 	+ SDDef.JSONQACQ + fee1 + SDDef.JSONCCM);
				ret.append(SDDef.JSONQUOT + "feeproj1" 	+ SDDef.JSONQACQ + fee2 + SDDef.JSONCCM);
				ret.append(SDDef.JSONQUOT + "feeproj2" 	+ SDDef.JSONQACQ + fee3 + SDDef.JSONQRBCM);
				
				num ++;
			}
			
			ret.setCharAt(ret.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret.append(SDDef.JSONRBRACES);
			result = ret.toString();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			j_dao.closeRs(rs);
		}
		
		return SDDef.SUCCESS;
	}
	
	/**
	 * 6.6返回更改类型 初始化信息 gyjeggfl.jsp
	 * @chao
	 */
	public String GetChgRateInfo(){
		
		if(result == null || result.isEmpty()) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JDBCDao j_dao = new JDBCDao();
		
		ResultSet rs = null;
		StringBuffer ret = new StringBuffer();
		
		try{
			JSONObject j_obj = JSONObject.fromObject(result);
			int rtu_id = CommBase.strtoi(j_obj.getString("rtu_id"));
			int zjg_id = CommBase.strtoi(j_obj.getString("zjg_id"));
			
			String sql ="select id,describe from mppara where rtu_id=" + rtu_id +" order by id";
			
			rs = j_dao.executeQuery(sql);
			rs.last();
			int num = rs.getRow();
			
			short mpId[]	= new short [num];
			String mpName[]	= new String[num];
			
			int j = 0;
			rs.beforeFirst();
			while(rs.next()){
				mpId[j] 	= rs.getShort("id");
				mpName[j]	= rs.getString("describe");
				
				j++;
			}
			j_dao.closeRs(rs);
			
			sql = "select a.clp_num,a.clp_ids,c.feeproj_id feeproj,c.feeproj1_id feeproj1,c.feeproj2_id feeproj2 from zjgpara a,zjgpay_para c " +
				  "where a.rtu_id=c.rtu_id and a.zjg_id=c.zjg_id and a.rtu_id=" + rtu_id + " and a.zjg_id=" + zjg_id;
			
			int lookTimes = 0;
			rs = j_dao.executeQuery(sql);
			String clp_ids = null;
			int[] feeId = {0,0,0};
			if(rs.next()) {
				lookTimes = rs.getInt("clp_num");
				clp_ids = rs.getString("clp_ids");
				feeId[0] = rs.getInt("feeproj");
				feeId[1] = rs.getInt("feeproj1");
				feeId[2] = rs.getInt("feeproj2");
			}
			if(lookTimes > 3) lookTimes = 3;
			
			num = 0;
			ret.append(SDDef.JSONROWSTITLE);
			for(int i = 0; i < clp_ids.length() - 1; i++){
				
				if(num >= lookTimes) break;
				if(clp_ids.charAt(i + 1) == '0') continue;
				
				int idx = findIndex((i + 1), mpId);
				if(idx < 0) continue;
				
				ret.append(SDDef.JSONLBRACES);
				ret.append(SDDef.JSONQUOT + "value" 	+ SDDef.JSONQACQ + mpId[idx] + SDDef.JSONCCM);
				ret.append(SDDef.JSONQUOT + "text" 		+ SDDef.JSONQACQ + mpName[idx] + SDDef.JSONCCM);
				ret.append(SDDef.JSONQUOT + "feeproj" 	+ SDDef.JSONQACQ + feeId[num] + SDDef.JSONQRBCM);

				num ++;
			}
			
			ret.setCharAt(ret.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret.append(SDDef.JSONRBRACES);
			result = ret.toString();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			j_dao.closeRs(rs);
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
	
	@SuppressWarnings("unchecked")
	public String retRate(){
		
		HibDao hib_dao = new HibDao();
		String hql = "from " + YDTable.TABLECLASS_YFFRATEPARA + " as a";
		List obj_list = hib_dao.loadAll(hql);
		
		if(obj_list.size() == 0){
			result = "";
			return SDDef.SUCCESS;
		}
		
		StringBuffer ret = new StringBuffer();
		ret.append("{rows:[");
		for (int i = 0; i < obj_list.size(); i++) {
			YffRatePara obj = (YffRatePara)obj_list.get(i);
			short id = obj.getId();
			
			String desc = obj.getDescribe().trim();
			String detail = Rd.getYffRateDesc(obj);
			ret.append("{id:" + id + ",rate:\""+obj.getRatedZ()+"\",desc:\"" + desc + "\",detail:\""+detail+"\",fee_type:"+obj.getFeeType()+"},");
			
		}
		ret.deleteCharAt(ret.length() - 1);
		ret.append("]}");
		
		result = ret.toString();
		
		return SDDef.SUCCESS;
	}
	
	
	/**
	 * 查询根据户号查询用户信息。
	 * （读卡检索用）查询终端ID、总加组ID及预付费状态之外的页面参数。
	 * @return
	 */
	@JSON(serialize = false)
	public String getInfobyConsId(){

		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		List<Map<String, Object>>	list		= null;
		JDBCDao j_dao = new JDBCDao();
		JSONObject j_res  		= JSONObject.fromObject(result);
		String sql = getGySearchCardSql(j_res);

		if ((sql == SDDef.EMPTY) || (sql.length() <= 0)) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}

		JSONObject i_rows = new JSONObject();

		list = j_dao.result(sql);
		if(list.size()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		//可能查出多条 需进一步判断
		int i = 0;
		if(list.size() > 1) {
			
			String consNocard = j_res.getString("consNo");
			consNocard = consNocard.replaceFirst("0*", "");
			
			String meterType = j_res.getString("meterType");
			String consNodb = "";
			Map<String,Object> tmp = null;
			//20131127dubr 
			for(i = 0; i < list.size(); i++) {
				tmp = list.get(i);
				if(meterType.equals(SDDef.YFF_METER_TYPE_ZNK) || meterType.equals(SDDef.YFF_METER_TYPE_ZNK2)){
					consNodb = CommBase.CheckString(tmp.get("busi_no"));
				}else if(meterType.equals(SDDef.YFF_METER_TYPE_6103)){
					consNodb = CommBase.CheckString(tmp.get("writecard_no"));
				}
				consNodb = consNodb.replaceFirst("0*", "");
				//将endswith匹配改成equals匹配   [endswith23匹配3]  
				if(consNodb.equals(consNocard)) {
					break;
				}
			}
		}

		Map<String,Object> map=list.get(i);
		
		int   rtu_id = Integer.parseInt(map.get("rtu_id").toString());
		short zjg_id = Short.parseShort(map.get("zjg_id").toString());
		
		i_rows.put("rtu_id", 	CommBase.CheckString(map.get("rtu_id"))); 		//终端ID
		i_rows.put("zjg_id", 	CommBase.CheckString(map.get("zjg_id"))); 		//总加组ID
		i_rows.put("fee_unit",  CommBase.CheckString(map.get("zjg_desc"))); 	//总加组名称-费控单元     
		i_rows.put("username",  CommBase.CheckString(map.get("cons_desc"))); 	//用户名称
		i_rows.put("userno",	CommBase.CheckString(map.get("busi_no"))); 		//户号
		i_rows.put("useraddr",	CommBase.CheckString(map.get("cons_addr"))); 	//用户地址
		i_rows.put("tel", 		CommBase.CheckString(map.get("cons_telno"))); 	//电话
		i_rows.put("factory", 	Rd.getDict(Dict.DICTITEM_METERFACTORY, map.get("rtu_factory"))); //生产厂家
		
	//	i_rows.put("cardmeter_typeid", CommBase.CheckString(map.get("cardmeter_type")));  //写卡类型id
	//	i_rows.put("cardmeter_type","[" + CommBase.CheckNum(map.get("cardmeter_type"))+"]" + Rd.getDict(Dict.DICTITEM_CARDMETER_TYPE, map.get("cardmeter_type"))); //写卡类型 
		i_rows.put("cardmeter_type","\"[" + CommBase.CheckNum(map.get("cardmeter_type"))+"]" + Rd.getDict(Dict.DICTITEM_CARDMETER_TYPE, map.get("cardmeter_type")) +"\""); //写卡类型
		i_rows.put("writecard_no", CommBase.CheckNum(map.get("writecard_no"))); 		//写卡户号
		
		JSONObject j_o  = JSONObject.fromObject(getZjgRate(rtu_id, zjg_id));
		i_rows.put("pt_ratio", 	j_o.get("pt_ratio"));	//PT
		i_rows.put("ct_ratio", 	j_o.get("ct_ratio"));	//CT
		i_rows.put("blv", 		j_o.get("ratio")); 		//倍率
		i_rows.put("meter_id", 	j_o.get("meter_id"));	//esam表号

		i_rows.put("rtu_model", CommBase.CheckString(map.get("rm_desc"))); 						//终端型号	
		i_rows.put("prot_type", CommBase.CheckString(map.get("prot_type"))); 					//通讯规约
		
		OnlineRtu.RTUSTATE_ITEM rtustate_item = OnlineRtu.getOneRtuState(rtu_id);
		
		if (rtustate_item != null) {
			i_rows.put("online", 	( rtustate_item.comm_state == 1 ? "在线" : "离线" )  ); 		//在线状态
		}
		else {
			i_rows.put("online", 	"离线" ); 													//在线状态
		}
		result = i_rows.toString();
		return SDDef.SUCCESS;
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
	public String getRtuId() {
		return rtuId;
	}
	public void setRtuId(String rtuId) {
		this.rtuId = rtuId;
	}
	public String getZjgId() {
		return zjgId;
	}
	public void setZjgId(String zjgId) {
		this.zjgId = zjgId;
	}


	public String getSghz_flag() {
		return sghz_flag;
	}


	public void setSghz_flag(String sghzFlag) {
		sghz_flag = sghzFlag;
	}
	
}

