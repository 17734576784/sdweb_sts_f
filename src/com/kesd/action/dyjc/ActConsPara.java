package com.kesd.action.dyjc;

import java.util.Formatter;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.json.annotations.JSON;

import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.YDTable;
import com.kesd.comntpara.ComntUseropDef;
import com.kesd.dbpara.YffManDef;
import com.kesd.dbpara.YffRatePara;
import com.kesd.util.OnlineRtu;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;

public class ActConsPara extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6268085290910467823L;
	
	private String 		result;
	private String		field;		//所需查询的数据库字段
	private String 		rtuId;
	private int 		consNum;	//具有相同卡号的用户数目
	
	Formatter fmt = new Formatter();
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>NAME        : getsearchList	
	* <p>DESCRIPTION : 获取电表档案记录
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	*-----------------------------------------------------------*/
	@JSON(serialize = false)
	public String getsearchList(){
		List<Map<String, Object>>	list	= null;
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
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + map.get("rtu_id") + "_" + map.get("mp_id") + "_" + map.get("resident_id") + SDDef.JSONNZDATA);
			ret_buf.append(SDDef.JSONQUOT + (index+1) + SDDef.JSONCCM);//序号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("rtu_desc")) + SDDef.JSONCCM);//终端名称
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("m_desc")) + SDDef.JSONCCM);//电表名称
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("re_desc")) + SDDef.JSONCCM);//用户名称
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("cons_no")) + SDDef.JSONCCM);//客户编号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("comm_addr")) + SDDef.JSONCCM);//电表地址
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("mobile")) + SDDef.JSONCCM);//移动电话
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("org_desc")) + SDDef.JSONCCM);//所属供电所
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("fzman_desc")) + SDDef.JSONCCM);//所属线路负责人
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("asset_no")) + SDDef.JSONCCM);//资产编号			
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("address")) + SDDef.JSONCCM);//用户地址
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_METERFACTORY, map.get("factory"))  + SDDef.JSONCCM);//生产厂家
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("bl")) + SDDef.JSONCCM);//倍率
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_JXFS, map.get("wiring_mode"))  + SDDef.JSONCCM);//接线方式

			//不显示在检索界面，但返回给json
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("writecard_no")) + SDDef.JSONCCM);//写卡户号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("meter_id")) + SDDef.JSONCCM);//ESAM表号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("pt_numerator")) + SDDef.JSONCCM);//PT分子
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("pt_denominator")) + SDDef.JSONCCM);//PT分母
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("pt_ratio")) + SDDef.JSONCCM);//PT变比
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("ct_numerator")) + SDDef.JSONCCM);//CT分子
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("ct_denominator")) + SDDef.JSONCCM);//CT分母
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("ct_ratio")) + SDDef.JSONCCM);//CT变比
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("key_version")) + SDDef.JSONCCM);//密钥版本
			//低压卡式增加写卡阶梯费率的阶梯年结算日
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("jt_cycle_md")) + SDDef.JSONCCM);//阶梯年结算日MMDD
			
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
		
		sbfSql.append("select a.describe as rtu_desc,b.rtu_id as rtu_id,b.id as mp_id,d.describe as m_desc,d.comm_addr,d.meter_id,d.asset_no,");
		sbfSql.append("d.factory,b.pt_ratio * b.ct_ratio as bl,b.wiring_mode,c.id as resident_id, q.id as cons_id,e.writecard_no,");
		sbfSql.append("c.describe as re_desc,c.cons_no, c.mobile,c.address,o.describe org_desc,");
		sbfSql.append("b.pt_numerator,b.pt_denominator, b.pt_ratio, b.ct_numerator,b.ct_denominator, b.ct_ratio, e.key_version,e.feeproj_id, isnull(e.jt_cycle_md,0) jt_cycle_md ");
		sbfSql.append(" from  ydparaben.dbo.conspara as q, ydparaben.dbo.rtupara as a, ydparaben.dbo.mppara as b, ydparaben.dbo.residentpara as c, ydparaben.dbo.meterpara as d, ydparaben.dbo.mppay_para as e, ydparaben.dbo.mppay_state as s,orgpara o  where q.id = a.cons_id "); 
		sbfSql.append("and a.app_type = " + SDDef.APPTYPE_JC + " and a.id = b.rtu_id and a.id = c.rtu_id and a.id = d.rtu_id and a.id = e.rtu_id and b.id = d.mp_id and b.id = e.mp_id and c.id = d.resident_id and a.id = s.rtu_id and b.id = s.mp_id and o.id=q.org_id  ");  
		
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String type 	= jsonObj.getString("type");
		String operType 	= jsonObj.getString("operType");
			
		if(type.equals("zz")){		//主站费控
			sbfSql.append(" and e.pay_type = " + Byte.toString(SDDef.YFF_PREPAYTYPE_MASTER));								
		}else if(type.equals("yc")){//终端费控-远程
			sbfSql.append(" and e.pay_type = " + Byte.toString(SDDef.YFF_PREPAYTYPE_REMOTE));		
		}
		//20140606新增，与外接卡式区分条件
		else if(type.equals("ks")){//终端费控-卡式
			sbfSql.append(" and e.pay_type = " + Byte.toString(SDDef.YFF_PREPAYTYPE_CARD) + " and e.yffmeter_type < 100 ");		
		}
//		else if(type.equals("kzext")){//终端费控-卡式外接
//			sbfSql.append(" and e.pay_type = " + Byte.toString(SDDef.YFF_PREPAYTYPE_CARD) + " and e.yffmeter_type > 100 ");		
//		}
		
		sbfSql.append(" and  b.bak_flag = 0 and d.prepayflag = 1 " );		
		
		switch(Integer.parseInt(operType)){
		case ComntUseropDef.YFF_DYOPER_ADDRES:	//低压操作-开户
		//case ComntUseropDef.YFF_DYCOMM_ADDRES:
			sbfSql.append(" and (s.cus_state is null  or s.cus_state = 0 or s.cus_state = 50 )");
			break;
		case ComntUseropDef.YFF_DYOPER_PAY:  //低压操作-缴费
		case ComntUseropDef.YFF_DYOPER_REVER:	//低压操作-冲正                         
		case ComntUseropDef.YFF_DYOPER_PAUSE://低压操作-暂停                           
		case ComntUseropDef.YFF_DYOPER_DESTORY:	//低压操作-销户                   
		case ComntUseropDef.YFF_DYOPER_REPAIR:	//低压操作-补卡                       
		case ComntUseropDef.YFF_DYOPER_CHANGEMETER:	//低压操作-换表换倍率    
		case ComntUseropDef.YFF_DYOPER_CHANGERATE:	//低压操作-更改费率                       
			sbfSql.append("  and s.cus_state = 1 "); 
			break;
		case ComntUseropDef.YFF_DYOPER_RESTART:		//低压操作-恢复                       
			sbfSql.append("  and s.cus_state = 49 ");
			break;
//		case ComntUseropDef.YFF_DYOPER_CHANGERATE:	//低压操作-更改费率               
//		case ComntUseropDef.YFF_DYOPER_PROTECT:		//低压操作-保电                       
//		case ComntUseropDef.YFF_DYOPER_UDPATESTATE:	//低压操作-强制状态更新           
//		case ComntUseropDef.YFF_DYOPER_RECALC:		//低压操作-重新计算剩余金额         
//		case ComntUseropDef.YFF_DYOPER_REWRITE:		//低压操作-补写缴费记录                
//		case ComntUseropDef.YFF_DYOPER_GETREMAIN:	//低压操作-返回余额                 
//		case ComntUseropDef.YFF_DYOPER_GPARASTATE:	//低压操作-获得预付费参数及状态   
		default:
			break;
		}
		
		if(field != null && !field.isEmpty()){
			JSONObject jsonObj1  = JSONObject.fromObject(field);
			String orgId 	= jsonObj1.getString("orgId").trim();
//			String fzmanId 	= jsonObj1.getString("fzmanId").trim();
			String rtuName  = jsonObj1.getString("rtuName").trim();
			String consName	= jsonObj1.getString("consName").trim();
			String residentId = jsonObj1.getString("residentId").trim();
			String comm_addr	= jsonObj1.getString("comm_addr").trim();
			String mobile	= jsonObj1.getString("mobile").trim();
			
			if(orgId != null && !orgId.equals("-1") && !orgId.equals("null")){
				sbfSql.append(" and q.org_id=" + orgId);
			}
//			if(fzmanId != null && !fzmanId.equals("-1") && !fzmanId.equals("null")){
//				sbfSql.append(" and q.line_fzman_id=" + fzmanId);
//			}
			if(rtuName != null && !rtuName.isEmpty() && !rtuName.equals("null")){
				sbfSql.append(" and a.describe like '%"  + rtuName + "%'");
			}
			if(consName != null && !consName.isEmpty() && !consName.equals("null")){
				sbfSql.append(" and c.describe like '%" + consName + "%'");						
			}
			if(residentId != null && !residentId.isEmpty() && !residentId.equals("null")){
				sbfSql.append(" and c.cons_no like '%" +  residentId + "%'");						
			}
			if(comm_addr != null && !comm_addr.isEmpty() && !comm_addr.equals("null")){
				sbfSql.append(" and d.comm_addr like '%" + comm_addr + "%'");				
			}
			if(mobile != null && !mobile.isEmpty() && !mobile.equals("null")){
				sbfSql.append(" and c.mobile like '%" + mobile + "%'");					
			}				
		}
		
		sbfSql.append(" order by b.rtu_id , b.id");
		
		return sbfSql.toString();
	}
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>NAME        : getOneRec	
	* <p>DESCRIPTION : 获取一条记录。表内信息 页面--用
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	*-----------------------------------------------------------*/
	@JSON(serialize = false)
	public String getOneRec(){
		List<Map<String, Object>>	list		= null;
		
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		}
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String rtu_id 	= jsonObj.getString("rtu_id");
		String mp_id 	= jsonObj.getString("mp_id");
		
		JDBCDao j_dao = new JDBCDao();
		String 	sql	  = "select b.rtu_id as rtu_id,b.id as mp_id,d.describe as m_desc,d.meter_id,d.asset_no,d.factory,b.pt_ratio * b.ct_ratio as bl," +
				" b.wiring_mode,c.id as resident_id, q.id as cons_id,c.describe as re_desc,c.cons_no, c.mobile,c.address,q.org_id,q.line_fzman_id ," +
				" b.pt_numerator,b.pt_denominator, b.pt_ratio, b.ct_numerator,b.ct_denominator, b.ct_ratio  " +
				" from  ydparaben.dbo.conspara as q, ydparaben.dbo.rtupara as a, ydparaben.dbo.mppara as b, ydparaben.dbo.residentpara as c, " +
				" ydparaben.dbo.meterpara as d, ydparaben.dbo.mppay_para as e, ydparaben.dbo.mppay_state as s " +
				" where q.id = a.cons_id and a.app_type = 3 and a.id = b.rtu_id and a.id = c.rtu_id and a.id = d.rtu_id and a.id = e.rtu_id " +
				" and b.id = d.mp_id and b.id = e.mp_id and c.id = d.resident_id and a.id = s.rtu_id and b.id = s.mp_id  " +
				" and b.rtu_id = " + rtu_id + " and  b.id = " + mp_id ;
		
		list = j_dao.result(sql);
		if(list.size()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		}
		
		JSONObject j_obj = new JSONObject();
		Map<String,Object> map=list.get(0);
		j_obj.put("userno", 	CommBase.CheckString(map.get("cons_no")));//户号
		j_obj.put("username", 	CommBase.CheckString(map.get("re_desc")));//用户名称
		j_obj.put("tel", 		CommBase.CheckString(map.get("mobile")));//移动电话
		j_obj.put("useraddr", 	CommBase.CheckString(map.get("address")));//用户地址
		j_obj.put("esamno", 	CommBase.CheckString(map.get("meter_id")));//表号
		j_obj.put("pt_r", 		CommBase.CheckString(map.get("pt_ratio")));//PT
		j_obj.put("ct_r", 		CommBase.CheckString(map.get("ct_ratio")));//CT
		
		result = j_obj.toString();
		
		return SDDef.SUCCESS;
	}
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>NAME        : getOneRec2	
	* <p>DESCRIPTION : 获取一条记录。卡式 读卡检索时，从数据库中补充查询 生产厂家和接线方式两个字段
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	*-----------------------------------------------------------*/
	@JSON(serialize = false)
	public String getOneRec2(){
		List<Map<String, Object>>	list		= null;
		
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		}
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String rtu_id 	= jsonObj.getString("rtu_id");
		String mp_id 	= jsonObj.getString("mp_id");
		
		JDBCDao j_dao = new JDBCDao();
		String 	sql	  = "select a.wiring_mode,b.factory from mppara a,meterpara b where a.id=b.mp_id and a.rtu_id=b.rtu_id " +
						" and a.rtu_id=" + rtu_id + " and a.id = " + mp_id ;
		
		list = j_dao.result(sql);
		if(list.size()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		} 
		
		JSONObject j_obj = new JSONObject();
		Map<String,Object> map=list.get(0);
		j_obj.put("wiring_mode", 	Rd.getDict(Dict.DICTITEM_JXFS, map.get("wiring_mode")));//接线方式
		j_obj.put("factory", 	Rd.getDict(Dict.DICTITEM_METERFACTORY, map.get("factory")));//生产厂家
		
		result = j_obj.toString();
		
		return SDDef.SUCCESS;
	}

	/**
	 * 读卡检索的查询sql
	 * @param j_res
	 * @return
	 */
	private String getDySearchCardSql(JSONObject j_res){
		
		String 	   consNo 		= j_res.getString("consNo");
		String 	   meterType 	= j_res.getString("meterType");
		String 	   operType	 	= j_res.getString("optype");
		
		//权限相关
		YffManDef yffman = CommFunc.getYffMan();
	    if ((yffman == null) || (yffman.getApptype() & SDDef.YFF_APPTYPE_DYQX )==0 ) {
			return SDDef.EMPTY;
	    }
	    int op_type = CommBase.strtoi(operType);
	    
	    if ((op_type ==  ComntUseropDef.YFF_DYOPER_ADDRES) ||
	    		(op_type ==  ComntUseropDef.YFF_DYOPER_DESTORY)) {
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

		sbfSql.append("select o.describe org_desc,r.id rtu_id,mp.id mp_id,mp.describe mp_desc,m.describe rm_desc,c.describe cons_desc,res.id resident_id,r.factory,r.made_no,r.prot_type,mt.meter_id,mp.wiring_mode,mp.pt_ratio,mp.ct_ratio,mp.pt_ratio*mp.ct_ratio ratio,res.cons_no,res.mobile cons_telno,res.address cons_addr,o.describe org_desc,r.rtu_model,p.writecard_no, isnull(p.jt_cycle_md,0) jt_cycle_md ");
		sbfSql.append("from orgpara o,line_fzman l,rtupara r,conspara c,mppara mp,meterpara mt,residentpara res,mppay_para p,mppay_state s,rtumodel m ");
		sbfSql.append("where c.app_type="+SDDef.APPTYPE_JC+" and c.org_id=o.id and c.line_fzman_id=l.id and r.cons_id=c.id and r.id=mp.rtu_id and r.id=mt.rtu_id and r.id=res.rtu_id ");
		sbfSql.append("and r.id=p.rtu_id and r.id=s.rtu_id and mp.id=mt.mp_id and mp.id=p.mp_id and mp.id=s.mp_id and r.rtu_model=m.id ");
		sbfSql.append("and mt.resident_id=res.id and mt.use_flag=1 and mp.use_flag=1 and mt.prepayflag=1 ");
		
		switch(op_type){
		case ComntUseropDef.YFF_GYOPER_ADDCUS:		//低压操作-开户
			sbfSql.append(" and (s.cus_state is null  or s.cus_state = " + SDDef.YFF_CUSSTATE_INIT + " or s.cus_state = " + SDDef.YFF_CUSSTATE_DESTORY + " )");
			break;
		case ComntUseropDef.YFF_GYOPER_RESTART:		//低压操作-恢复
			sbfSql.append(" and  s.cus_state = " + SDDef.YFF_CUSSTATE_PAUSE);
			break;
		case -1: break;//清空卡页面，不限制用户状态
		default:
			sbfSql.append(" and  s.cus_state = " + SDDef.YFF_CUSSTATE_NORMAL);
			break;
		}

		if (yffman.getRank() == SDDef.YFF_RANK_ORG) {	//用户所在供电所
			sbfSql.append(" and c.org_id=" + yffman.getOrgId());
		}
	
		//20131021修改：添加智能卡2:meterType.equals(SDDef.YFF_METER_TYPE_ZNK2)
		if(meterType.equals(SDDef.YFF_METER_TYPE_ZNK) || meterType.equals(SDDef.YFF_METER_TYPE_ZNK2)){
			sbfSql.append(" and p.writecard_no like '%" + consNo.replaceFirst("0*", "")  + "'");
		}
		//end
		
		//低压无此条件
		//else if(meterType.equals(SDDef.YFF_METER_TYPE_6103)){
		//	sbfSql.append(" and e.cardmeter_type = " + SDDef.YFF_CARDMTYPE_KE003 + " and e.writecard_no like '" +  consNo.replaceFirst("0*", "")  + "'");
		//}
		
		return sbfSql.toString();
	}
	
	@JSON(serialize = false)
	public String getInfobyConsId() {
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		List<Map<String, Object>>	list		= null;
		JDBCDao j_dao = new JDBCDao();
		JSONObject j_res  		= JSONObject.fromObject(result);
		
		String sql = getDySearchCardSql(j_res);
		if(sql.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JSONObject i_rows = new JSONObject();

		list = j_dao.result(sql);
		if(list == null || list.size()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		//可能查出多条 需进一步判断
		//20131021修改,判断条件中增加新规约表
		String meterType = j_res.getString("meterType");
		//根据表类型确定写卡类型
		byte card_meter = 0;
		if(meterType.equals(SDDef.YFF_METER_TYPE_ZNK)){
			card_meter = SDDef.YFF_CARDMTYPE_KE001;
		}
		else if(meterType.equals(SDDef.YFF_METER_TYPE_ZNK2)){
			card_meter = SDDef.YFF_CARDMTYPE_KE006;
		}
		
		
		int i = 0;
		if(list.size() > 1) {
			
			String consNocard = j_res.getString("consNo");
			consNocard = consNocard.replaceFirst("0*", "");
			
			
			String consNodb = "";
			Map<String,Object> tmp = null;
			//20131127dubr 
			for(i = 0; i < list.size(); i++) {
				tmp = list.get(i);
				//20131127dubr 将获取参数从consNodb改成writecard_no
				//20131021修改
				//if(meterType.equals(SDDef.YFF_METER_TYPE_ZNK) || meterType.equals(SDDef.YFF_METER_TYPE_ZNK2)){
					consNodb = CommBase.CheckString(tmp.get("writecard_no"));
				//	}
				consNodb = consNodb.replaceFirst("0*", "");
				//将endswith匹配改成equals匹配   [endswith 23匹配3]  
				if(consNodb.equals(consNocard)) {
					break;
				}
			}
		}

		Map<String,Object> map=list.get(i);
		
		int   rtu_id = CommBase.strtoi(map.get("rtu_id").toString());
		
		i_rows.put("rtu_id", 	rtu_id); 		//终端ID
		i_rows.put("mp_id", 	CommBase.CheckString(map.get("mp_id")));     
		i_rows.put("username",  CommBase.CheckString(map.get("mp_desc"))); 	//用户名称
		i_rows.put("userno",	CommBase.CheckString(map.get("cons_no"))); 	//户号
		i_rows.put("useraddr",	CommBase.CheckString(map.get("cons_addr"))); 	//用户地址
		i_rows.put("tel", 		CommBase.CheckString(map.get("cons_telno"))); 	//电话
		i_rows.put("factory", 	Rd.getDict(Dict.DICTITEM_METERFACTORY, map.get("factory"))); //生产厂家
		i_rows.put("pt_ratio", 	map.get("pt_ratio"));	//PT
		i_rows.put("ct_ratio", 	map.get("ct_ratio"));	//CT
		i_rows.put("resident_id", 	map.get("resident_id"));	//resident_id
		i_rows.put("blv", 		map.get("ratio")); 		//倍率
		i_rows.put("meter_id", 	map.get("meter_id"));	//esam表号
		i_rows.put("tel", 		CommBase.CheckString(map.get("cons_telno"))); 	//电话
		i_rows.put("useraddr",	CommBase.CheckString(map.get("cons_addr"))); 	//用户地址
		i_rows.put("orgname",  CommBase.CheckString(map.get("org_desc"))); 	//所属供电所
		i_rows.put("wiring_mode", Rd.getDict(Dict.DICTITEM_JXFS, map.get("wiring_mode")));
		OnlineRtu.RTUSTATE_ITEM rtustate_item = OnlineRtu.getOneRtuState(rtu_id);
		i_rows.put("online", 	((rtustate_item != null) && (rtustate_item.comm_state == 1)) ? "在线" : "离线" ); 		//在线状态
		i_rows.put("rtu_model", CommBase.CheckString(map.get("rm_desc"))); 			//终端型号
		i_rows.put("prot_type", CommBase.CheckString(map.get("prot_type"))); 		//通讯规约
		i_rows.put("writecard_no",	CommBase.CheckString(map.get("writecard_no"))); 	//写卡户号
		i_rows.put("jt_cycle_md",	CommBase.CheckString(map.get("jt_cycle_md"))); 	//低压阶梯年结算日
		//20131021增加新规约表，增加写卡类型
		i_rows.put("cardmeter_type","\"["+ card_meter +"]" + Rd.getDict(Dict.DICTITEM_CARDMETER_TYPE, card_meter) + "\""); //写卡类型	
		result = i_rows.toString();
		return SDDef.SUCCESS;
	}

	/**
	 * 20140729  zhp
	 * 根据写卡户号，检索用户信息
	 */
	@JSON(serialize=false)
	public String getInfobyWrtCard(){
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		String sql = getSqlExtByCard();
		
		List<Map<String, Object>>	list		= null;
		StringBuffer 	ret_buf		= new StringBuffer();
		JDBCDao j_dao = new JDBCDao();
		
		consNum = 0;				//初始化为0
		
		list = j_dao.result(sql);
		if(list.size()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		}
		
		//consNum = list.size();	//具有相同卡号的用户数目。返回到js
		
		String consNodb = "";
		
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String consNocard = jsonObj.getString("consNo");	//写卡户号，从卡中读取
		consNocard = consNocard.replaceFirst("0*", "");
		
		ret_buf.append(SDDef.JSONROWSTITLE);
		
		for(int index =0;index<list.size();index++){

			Map<String,Object> map=list.get(index);

			map = list.get(index);

			consNodb = CommBase.CheckString(map.get("writecard_no"));
			consNodb = consNodb.replaceFirst("0*", "");

			if(!consNodb.equals(consNocard)) {
				continue;
			}
			
			
			ret_buf.append(SDDef.JSONLBRACES); 
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + map.get("rtu_id") + "_" + map.get("mp_id") + "_" + map.get("resident_id") + SDDef.JSONNZDATA);
			ret_buf.append(SDDef.JSONQUOT + (index+1) + SDDef.JSONCCM);//序号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("rtu_desc")) + SDDef.JSONCCM);//终端名称
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("m_desc")) + SDDef.JSONCCM);//电表名称
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("re_desc")) + SDDef.JSONCCM);//用户名称
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("cons_no")) + SDDef.JSONCCM);//客户编号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("comm_addr")) + SDDef.JSONCCM);//电表地址
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("mobile")) + SDDef.JSONCCM);//移动电话
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("org_desc")) + SDDef.JSONCCM);//所属供电所
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("fzman_desc")) + SDDef.JSONCCM);//所属线路负责人
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("asset_no")) + SDDef.JSONCCM);//资产编号			
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("address")) + SDDef.JSONCCM);//用户地址
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_METERFACTORY, map.get("factory"))  + SDDef.JSONCCM);//生产厂家
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("bl")) + SDDef.JSONCCM);//倍率
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_JXFS, map.get("wiring_mode"))  + SDDef.JSONCCM);//接线方式
		
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("cardtype")) 					+ SDDef.JSONCCM);//预付费电表类型
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_FEETYPE, map.get("cacl_type"))  	+ SDDef.JSONCCM);//预付费类型描述

			//不显示在检索界面，但返回给json
			
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("card_area")) 					+ SDDef.JSONCCM);//区域码
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("card_pass")) 					+ SDDef.JSONCCM);//密码			
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("card_rand")) 					+ SDDef.JSONCCM);//随机数
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("cacl_type"))  + SDDef.JSONCCM);//预付费类型数据库值
			
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("writecard_no")) + SDDef.JSONCCM);//写卡户号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("meter_id")) + SDDef.JSONCCM);//ESAM表号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("pt_numerator")) + SDDef.JSONCCM);//PT分子
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("pt_denominator")) + SDDef.JSONCCM);//PT分母
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("pt_ratio")) + SDDef.JSONCCM);//PT变比
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("ct_numerator")) + SDDef.JSONCCM);//CT分子
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("ct_denominator")) + SDDef.JSONCCM);//CT分母
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("ct_ratio")) + SDDef.JSONCCM);//CT变比
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("key_version")) + SDDef.JSONCCM);//密钥版本
			//低压卡式增加写卡阶梯费率的阶梯年结算日
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("jt_cycle_md")) + SDDef.JSONCCM);//阶梯年结算日MMDD
			
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);	
			consNum ++;							//找到一个完全匹配的
		}	

		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();

		return SDDef.SUCCESS;
	}
	
	//20140729 zhp 根据写卡户号，获取外接表的信息
	private String getSqlExtByCard(){
		
		//权限相关
		YffManDef yffman = CommFunc.getYffMan();
	    if ((yffman == null) || (yffman.getApptype() & SDDef.YFF_APPTYPE_DYQX )==0 ) {
			return SDDef.EMPTY;
	    }
	    
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String operType 	= jsonObj.getString("optype");	//操作类型
		String writecard_no = jsonObj.getString("consNo");	//写卡户号，从卡中读取
		
		StringBuffer 	sbfSql 	= new StringBuffer(); 
		
		sbfSql.append("select a.describe as rtu_desc,b.rtu_id as rtu_id,b.id as mp_id,d.describe as m_desc,d.comm_addr,d.meter_id,d.asset_no,");
		sbfSql.append("d.factory,b.pt_ratio * b.ct_ratio as bl,b.wiring_mode,c.id as resident_id, q.id as cons_id,e.writecard_no,");
		sbfSql.append("c.describe as re_desc,c.cons_no, c.mobile,c.address,o.describe org_desc,f.describe fzman_desc,");
		sbfSql.append("b.pt_numerator,b.pt_denominator, b.pt_ratio, b.ct_numerator,b.ct_denominator, b.ct_ratio, e.key_version, isnull(e.jt_cycle_md,0) jt_cycle_md, ");
		sbfSql.append("e.cacl_type, extType.cardtype, e.card_rand, e.card_pass, e.card_area ");
		sbfSql.append(" from  ydparaben.dbo.conspara as q, ydparaben.dbo.rtupara as a, ydparaben.dbo.mppara as b, ydparaben.dbo.residentpara as c, ydparaben.dbo.meterpara as d, ydparaben.dbo.mppay_para as e, ydparaben.dbo.mppay_state as s,orgpara o,ydparaben.dbo.line_fzman f, ydparaben.dbo.ocardtype_para as extType where q.id = a.cons_id "); 
		sbfSql.append("and a.app_type = " + SDDef.APPTYPE_JC + " and a.id = b.rtu_id and a.id = c.rtu_id and a.id = d.rtu_id and a.id = e.rtu_id and b.id = d.mp_id and b.id = e.mp_id and c.id = d.resident_id and a.id = s.rtu_id and b.id = s.mp_id and o.id=q.org_id and f.id=q.line_fzman_id and o.id=f.org_id and extType.id = e.ocardproj_id ");  
	//	sbfSql.append("and e.writecard_no = " + writecard_no);
				
		//外接表查询条件e.yffmeter_type >100
		sbfSql.append(" and e.pay_type = " + Byte.toString(SDDef.YFF_PREPAYTYPE_CARD) + " and e.yffmeter_type > 100 and  b.bak_flag = 0 and d.prepayflag = 1 " );		
		
		switch(Integer.parseInt(operType)){
		case ComntUseropDef.YFF_DYOPER_ADDRES:	//低压操作-开户
		//	sbfSql.append(" and (s.cus_state is null  or s.cus_state = 0 or s.cus_state = 50 )");
			sbfSql.append(" and (s.cus_state is null  or s.cus_state = " + SDDef.YFF_CUSSTATE_INIT + " or s.cus_state = " + SDDef.YFF_CUSSTATE_DESTORY + " )");
			break;
		case ComntUseropDef.YFF_DYOPER_PAY:  //低压操作-缴费
		case ComntUseropDef.YFF_DYOPER_REVER:	//低压操作-冲正                         
		case ComntUseropDef.YFF_DYOPER_PAUSE://低压操作-暂停                           
		case ComntUseropDef.YFF_DYOPER_DESTORY:	//低压操作-销户                   
		case ComntUseropDef.YFF_DYOPER_REPAIR:	//低压操作-补卡                       
		case ComntUseropDef.YFF_DYOPER_CHANGEMETER:	//低压操作-换表换倍率    
		case ComntUseropDef.YFF_DYOPER_CHANGERATE:	//低压操作-更改费率                       
		//	sbfSql.append("  and s.cus_state = 1 ");
			sbfSql.append(" and  s.cus_state = " + SDDef.YFF_CUSSTATE_NORMAL);
			break;
		case ComntUseropDef.YFF_DYOPER_RESTART:		//低压操作-恢复                       
		//	sbfSql.append("  and s.cus_state = 49 ");
			sbfSql.append(" and  s.cus_state = " + SDDef.YFF_CUSSTATE_PAUSE);
			
			break;
		default:
			break;
		}
		
		if (yffman.getRank() == SDDef.YFF_RANK_ORG) {							//用户所在供电所
			sbfSql.append(" and q.org_id=" + yffman.getOrgId());
		}
		else if (yffman.getRank() == SDDef.YFF_RANK_FZMAN) {					//用户所在线路负责人
			sbfSql.append(" and q.line_fzman_id=" + yffman.getFzmanId());		//== l.id ??为什么要用l
		}
		
		sbfSql.append(" and e.writecard_no like '%" + writecard_no.replaceFirst("0*", "")  + "'");
		
	//	sbfSql.append(" order by b.rtu_id , b.id");
		
		return sbfSql.toString();
	}
	

	/**
	 * 读卡检索的查询sql
	 * @param j_res
	 * @return
	 */
	private String getDySearchExtCardSql(JSONObject j_res){
		
		String 	   consNo 		= j_res.getString("consNo");
		String 	   operType	 	= j_res.getString("optype");
		
		//权限相关
		YffManDef yffman = CommFunc.getYffMan();
	    if ((yffman == null) || (yffman.getApptype() & SDDef.YFF_APPTYPE_DYQX )==0 ) {
			return SDDef.EMPTY;
	    }
	    int op_type = CommBase.strtoi(operType);
	    
	    if ((op_type ==  ComntUseropDef.YFF_DYOPER_ADDRES) ||
	    		(op_type ==  ComntUseropDef.YFF_DYOPER_DESTORY)) {
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
		
		sbfSql.append("select o.describe org_desc,r.id rtu_id,mp.id mp_id,mp.describe mp_desc,m.describe rm_desc,c.describe cons_desc,res.id resident_id,r.factory,r.made_no,r.prot_type,mt.meter_id,mp.wiring_mode,mp.pt_ratio,mp.ct_ratio,mp.pt_ratio*mp.ct_ratio ratio,res.cons_no,res.mobile cons_telno,res.address cons_addr,o.describe org_desc,r.rtu_model,p.writecard_no, isnull(p.jt_cycle_md,0) jt_cycle_md,p.card_rand, p.card_pass, p.card_area,p.feeproj_id,ocp.cardtype ");
		sbfSql.append("from orgpara o,line_fzman l,rtupara r,conspara c,mppara mp,meterpara mt,residentpara res,mppay_para p,mppay_state s,rtumodel m , ydparaben.dbo.ocardtype_para as ocp ");
		sbfSql.append("where c.app_type="+SDDef.APPTYPE_JC+" and c.org_id=o.id and c.line_fzman_id=l.id and r.cons_id=c.id and r.id=mp.rtu_id and r.id=mt.rtu_id and r.id=res.rtu_id ");
		sbfSql.append("and r.id=p.rtu_id and r.id=s.rtu_id and mp.id=mt.mp_id and mp.id=p.mp_id and mp.id=s.mp_id and r.rtu_model=m.id ");
		sbfSql.append("and mt.resident_id=res.id and mt.use_flag=1 and mp.use_flag=1 and mt.prepayflag=1 and ocp.id = p.ocardproj_id ");
		
		switch(op_type){
		case ComntUseropDef.YFF_GYOPER_ADDCUS:		//低压操作-开户
			sbfSql.append(" and (s.cus_state is null  or s.cus_state = " + SDDef.YFF_CUSSTATE_INIT + " or s.cus_state = " + SDDef.YFF_CUSSTATE_DESTORY + " )");
			break;
		case ComntUseropDef.YFF_GYOPER_RESTART:		//低压操作-恢复
			sbfSql.append(" and  s.cus_state = " + SDDef.YFF_CUSSTATE_PAUSE);
			break;
		case -1: break;//清空卡页面，不限制用户状态
		default:
			sbfSql.append(" and  s.cus_state = " + SDDef.YFF_CUSSTATE_NORMAL);
			break;
		}

		if (yffman.getRank() == SDDef.YFF_RANK_ORG) {		//用户所在供电所
			sbfSql.append(" and c.org_id=" + yffman.getOrgId());
		}//201407zkz
		else if (yffman.getRank() == SDDef.YFF_RANK_FZMAN) {	//用户所在线路负责人
			sbfSql.append(" and c.line_fzman_id=" + yffman.getFzmanId());		//== l.id ??为什么要用l
		}
	
		sbfSql.append(" and p.writecard_no like '%" + consNo.replaceFirst("0*", "")  + "'");
			
		return sbfSql.toString();
	}
	
	@JSON(serialize = false)
	public String getInfobyConsIdExt() {
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		List<Map<String, Object>>	list		= null;
		JDBCDao j_dao = new JDBCDao();
		JSONObject j_res  		= JSONObject.fromObject(result);
		
		String sql = getDySearchExtCardSql(j_res);
		if(sql.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		JSONObject i_rows = new JSONObject();

		list = j_dao.result(sql);
		if(list == null || list.size()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		//可能查出多条 需进一步判断
		//20131021修改,判断条件中增加新规约表
//		String meterType = j_res.getString("meterType");
//		//根据表类型确定写卡类型
//		byte card_meter = 0;
//		if(meterType.equals(SDDef.YFF_METER_TYPE_ZNK)){
//			card_meter = SDDef.YFF_CARDMTYPE_KE001;
//		}
//		else if(meterType.equals(SDDef.YFF_METER_TYPE_ZNK2)){
//			card_meter = SDDef.YFF_CARDMTYPE_KE006;
//		}
		
		
		int i = 0;
		if(list.size() > 1) {
			
			String consNocard = j_res.getString("consNo");
			consNocard = consNocard.replaceFirst("0*", "");
			
			
			String consNodb = "";
			Map<String,Object> tmp = null;
			//20131127dubr 
			for(i = 0; i < list.size(); i++) {
				tmp = list.get(i);
				//20131127dubr 将获取参数从consNodb改成writecard_no
				//20131021修改
				//if(meterType.equals(SDDef.YFF_METER_TYPE_ZNK) || meterType.equals(SDDef.YFF_METER_TYPE_ZNK2)){
					consNodb = CommBase.CheckString(tmp.get("writecard_no"));
				//	}
				consNodb = consNodb.replaceFirst("0*", "");
				//将endswith匹配改成equals匹配   [endswith 23匹配3]  
				if(consNodb.equals(consNocard)) {
					break;
				}
			}
		}

		Map<String,Object> map=list.get(i);
		
		int   rtu_id = CommBase.strtoi(map.get("rtu_id").toString());
		
		i_rows.put("rtu_id", 	rtu_id); 		//终端ID
		i_rows.put("mp_id", 	CommBase.CheckString(map.get("mp_id")));     
		i_rows.put("username",  CommBase.CheckString(map.get("mp_desc"))); 	//用户名称
		i_rows.put("userno",	CommBase.CheckString(map.get("cons_no"))); 	//户号
		i_rows.put("useraddr",	CommBase.CheckString(map.get("cons_addr"))); 	//用户地址
		i_rows.put("tel", 		CommBase.CheckString(map.get("cons_telno"))); 	//电话
		i_rows.put("factory", 	Rd.getDict(Dict.DICTITEM_METERFACTORY, map.get("factory"))); //生产厂家
		i_rows.put("pt_ratio", 	map.get("pt_ratio"));	//PT
		i_rows.put("ct_ratio", 	map.get("ct_ratio"));	//CT
		i_rows.put("resident_id", 	map.get("resident_id"));	//resident_id
		i_rows.put("blv", 		map.get("ratio")); 		//倍率
		i_rows.put("meter_id", 	map.get("meter_id"));	//esam表号
		i_rows.put("tel", 		CommBase.CheckString(map.get("cons_telno"))); 	//电话
		i_rows.put("useraddr",	CommBase.CheckString(map.get("cons_addr"))); 	//用户地址
		i_rows.put("orgname",  CommBase.CheckString(map.get("org_desc"))); 	//所属供电所
		i_rows.put("wiring_mode", Rd.getDict(Dict.DICTITEM_JXFS, map.get("wiring_mode")));
		OnlineRtu.RTUSTATE_ITEM rtustate_item = OnlineRtu.getOneRtuState(rtu_id);
		i_rows.put("online", 	((rtustate_item != null) && (rtustate_item.comm_state == 1) ? "在线" : "离线" )  ); 		//在线状态
		i_rows.put("rtu_model", CommBase.CheckString(map.get("rm_desc"))); 			//终端型号
		i_rows.put("prot_type", CommBase.CheckString(map.get("prot_type"))); 		//通讯规约
		i_rows.put("writecard_no",	CommBase.CheckString(map.get("writecard_no"))); 	//写卡户号
		i_rows.put("jt_cycle_md",	CommBase.CheckString(map.get("jt_cycle_md"))); 	//低压阶梯年结算日
		
		i_rows.put("cardtype",	CommBase.CheckString(map.get("cardtype"))); 	//表类型KL_001,JJ_001...
		i_rows.put("card_area",	CommBase.CheckString(map.get("card_area"))); 	//区域码
		i_rows.put("cacl_type",	CommBase.CheckString(map.get("cacl_type"))); 	//计费方式
		i_rows.put("card_rand",	CommBase.CheckString(map.get("card_rand"))); 	//随机数
		i_rows.put("card_pass",	CommBase.CheckString(map.get("card_pass"))); 	//密码
		
		//获取费率方案
		YffRatePara yff_rate_para = (YffRatePara)Rd.getRecord(YDTable.TABLECLASS_YFFRATEPARA,(Short)map.get("feeproj_id"));
		i_rows.put("feeType", yff_rate_para.getFeeType()); 	//费率类型(单、复、阶梯)
		
		result = i_rows.toString();
		return SDDef.SUCCESS;
	}
	/**
	 * 外接表的查询检索
	 * */
	@JSON(serialize = false)
	public String getsearchListExt(){
		List<Map<String, Object>>	list		= null;
		StringBuffer 	ret_buf		= new StringBuffer();
		
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		}

		JDBCDao j_dao = new JDBCDao();
		String 			sql			= getSqlExt();
		list = j_dao.result(sql);
		if(list.size()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		} 
		
		ret_buf.append(SDDef.JSONROWSTITLE);
		for(int index =0;index<list.size();index++){
			Map<String,Object> map=list.get(index);
		
			ret_buf.append(SDDef.JSONLBRACES); 
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + map.get("rtu_id") + "_" + map.get("mp_id") + "_" + map.get("resident_id") + SDDef.JSONNZDATA);
			ret_buf.append(SDDef.JSONQUOT + (index+1) + SDDef.JSONCCM);//序号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("rtu_desc")) + SDDef.JSONCCM);//终端名称
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("m_desc")) + SDDef.JSONCCM);//电表名称
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("re_desc")) + SDDef.JSONCCM);//用户名称
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("cons_no")) + SDDef.JSONCCM);//客户编号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("comm_addr")) + SDDef.JSONCCM);//电表地址
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("mobile")) + SDDef.JSONCCM);//移动电话
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("org_desc")) + SDDef.JSONCCM);//所属供电所
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("fzman_desc")) + SDDef.JSONCCM);//所属线路负责人
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("asset_no")) + SDDef.JSONCCM);//资产编号			
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("address")) + SDDef.JSONCCM);//用户地址
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_METERFACTORY, map.get("factory"))  + SDDef.JSONCCM);//生产厂家
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("bl")) + SDDef.JSONCCM);//倍率
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_JXFS, map.get("wiring_mode"))  + SDDef.JSONCCM);//接线方式
			
			//外接表写卡需要字段
			//ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_YFFMETERTYPE, map.get("e.yffmeter_type"))  + SDDef.JSONCCM);//
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("cardtype")) 					+ SDDef.JSONCCM);//预付费电表类型
			ret_buf.append(SDDef.JSONQUOT + Rd.getDict(Dict.DICTITEM_FEETYPE, map.get("cacl_type"))  	+ SDDef.JSONCCM);//预付费类型描述
			

			
			//不显示在检索界面，但返回给json
			
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("card_area")) 					+ SDDef.JSONCCM);//区域码
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("card_pass")) 					+ SDDef.JSONCCM);//密码			
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("card_rand")) 					+ SDDef.JSONCCM);//随机数
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("cacl_type"))  + SDDef.JSONCCM);//预付费类型数据库值
			
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("writecard_no")) + SDDef.JSONCCM);//写卡户号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("meter_id")) + SDDef.JSONCCM);//ESAM表号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("pt_numerator")) + SDDef.JSONCCM);//PT分子
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("pt_denominator")) + SDDef.JSONCCM);//PT分母
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("pt_ratio")) + SDDef.JSONCCM);//PT变比
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("ct_numerator")) + SDDef.JSONCCM);//CT分子
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("ct_denominator")) + SDDef.JSONCCM);//CT分母
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("ct_ratio")) + SDDef.JSONCCM);//CT变比
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("key_version")) + SDDef.JSONCCM);//密钥版本
			//低压卡式增加写卡阶梯费率的阶梯年结算日
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("jt_cycle_md")) + SDDef.JSONCCM);//阶梯年结算日MMDD
			
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);		
			
		}		
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();
		
		return SDDef.SUCCESS;
	}
	
	//获取外接表的信息
	private String getSqlExt(){
		StringBuffer 	sbfSql 	= new StringBuffer(); 
		
		sbfSql.append("select a.describe as rtu_desc,b.rtu_id as rtu_id,b.id as mp_id,d.describe as m_desc,d.comm_addr,d.meter_id,d.asset_no,");
		sbfSql.append("d.factory,b.pt_ratio * b.ct_ratio as bl,b.wiring_mode,c.id as resident_id, q.id as cons_id,e.writecard_no,");
		sbfSql.append("c.describe as re_desc,c.cons_no, c.mobile,c.address,o.describe org_desc,f.describe fzman_desc,");
		sbfSql.append("b.pt_numerator,b.pt_denominator, b.pt_ratio, b.ct_numerator,b.ct_denominator, b.ct_ratio, e.key_version, isnull(e.jt_cycle_md,0) jt_cycle_md, ");
		sbfSql.append("e.cacl_type, extType.cardtype, e.card_rand, e.card_pass, e.card_area ");
		sbfSql.append(" from  ydparaben.dbo.conspara as q, ydparaben.dbo.rtupara as a, ydparaben.dbo.mppara as b, ydparaben.dbo.residentpara as c, ydparaben.dbo.meterpara as d, ydparaben.dbo.mppay_para as e, ydparaben.dbo.mppay_state as s,orgpara o,ydparaben.dbo.line_fzman f, ydparaben.dbo.ocardtype_para as extType where q.id = a.cons_id "); 
		sbfSql.append("and a.app_type = " + SDDef.APPTYPE_JC + " and a.id = b.rtu_id and a.id = c.rtu_id and a.id = d.rtu_id and a.id = e.rtu_id and b.id = d.mp_id and b.id = e.mp_id and c.id = d.resident_id and a.id = s.rtu_id and b.id = s.mp_id and o.id=q.org_id and f.id=q.line_fzman_id and o.id=f.org_id and extType.id = e.ocardproj_id ");  
		
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String operType 	= jsonObj.getString("operType");
		
		//外接表查询条件e.yffmeter_type >100
		sbfSql.append(" and e.pay_type = " + Byte.toString(SDDef.YFF_PREPAYTYPE_CARD) + " and e.yffmeter_type > 100 and  b.bak_flag = 0 and d.prepayflag = 1 " );		
		
		switch(Integer.parseInt(operType)){
		case ComntUseropDef.YFF_DYOPER_ADDRES:	//低压操作-开户
			sbfSql.append(" and (s.cus_state is null  or s.cus_state = 0 or s.cus_state = 50 )");
			break;
		case ComntUseropDef.YFF_DYOPER_PAY:  //低压操作-缴费
		case ComntUseropDef.YFF_DYOPER_REVER:	//低压操作-冲正                         
		case ComntUseropDef.YFF_DYOPER_PAUSE://低压操作-暂停                           
		case ComntUseropDef.YFF_DYOPER_DESTORY:	//低压操作-销户                   
		case ComntUseropDef.YFF_DYOPER_REPAIR:	//低压操作-补卡                       
		case ComntUseropDef.YFF_DYOPER_CHANGEMETER:	//低压操作-换表换倍率    
		case ComntUseropDef.YFF_DYOPER_CHANGERATE:	//低压操作-更改费率                       
			sbfSql.append("  and s.cus_state = 1 "); 
			break;
		case ComntUseropDef.YFF_DYOPER_RESTART:		//低压操作-恢复                       
			sbfSql.append("  and s.cus_state = 49 ");
			break;
		default:
			break;
		}
		
		if(field != null && !field.isEmpty()){
			JSONObject jsonObj1  = JSONObject.fromObject(field);
			String orgId 	= jsonObj1.getString("orgId").trim();
			String fzmanId 	= jsonObj1.getString("fzmanId").trim();
			String rtuName  = jsonObj1.getString("rtuName").trim();
			String consName	= jsonObj1.getString("consName").trim();
			String residentId = jsonObj1.getString("residentId").trim();
			String comm_addr	= jsonObj1.getString("comm_addr").trim();
			String mobile	= jsonObj1.getString("mobile").trim();
			
			if(orgId != null && !orgId.equals("-1") && !orgId.equals("null")){
				sbfSql.append(" and q.org_id=" + orgId);
			}
			if(fzmanId != null && !fzmanId.equals("-1") && !fzmanId.equals("null")){
				sbfSql.append(" and q.line_fzman_id=" + fzmanId);
			}
			if(rtuName != null && !rtuName.isEmpty() && !rtuName.equals("null")){
				sbfSql.append(" and a.describe like '%"  + rtuName + "%'");
			}
			if(consName != null && !consName.isEmpty() && !consName.equals("null")){
				sbfSql.append(" and c.describe like '%" + consName + "%'");						
			}
			if(residentId != null && !residentId.isEmpty() && !residentId.equals("null")){
				sbfSql.append(" and c.cons_no like '%" +  residentId + "%'");						
			}
			if(comm_addr != null && !comm_addr.isEmpty() && !comm_addr.equals("null")){
				sbfSql.append(" and d.comm_addr like '%" + comm_addr + "%'");				
			}
			if(mobile != null && !mobile.isEmpty() && !mobile.equals("null")){
				sbfSql.append(" and c.mobile like '%" + mobile + "%'");					
			}				
		}
		
		sbfSql.append(" order by b.rtu_id , b.id");
		
		return sbfSql.toString();
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
	public String getRtuId() {
		return rtuId;
	}
	public void setRtuId(String rtuId) {
		this.rtuId = rtuId;
	}
	public int getConsNum() {
		return consNum;
	}
	public void setConsNum(int consNum) {
		this.consNum = consNum;
	}
}
