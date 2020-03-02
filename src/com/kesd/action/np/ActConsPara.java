package com.kesd.action.np;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.json.annotations.JSON;

import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.comntpara.ComntUseropDef;
import com.kesd.dbpara.YffManDef;
import com.kesd.service.DBOper;
import com.kesd.util.OnlineRtu;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 农排用户档案 处理类。
 * 其他应用查询的conspara，农排应用查询farmerpara。
 * @author Administrator
 *
 */
public class ActConsPara extends ActionSupport{

	private static final long serialVersionUID = 6268085290910467823L;
	
	private String 		result;
	private String		field;		//所需查询的数据库字段
	private String 		rtuId;
	
	//Formatter fmt = new Formatter();
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>NAME        : getsearchList	
	* <p>DESCRIPTION : 获取农排用户档案记录
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
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + map.get("area_id") + "_" + map.get("id")+ "_" + map.get("org_id") + "_" + map.get("area_code") + SDDef.JSONNZDATA);
			ret_buf.append(SDDef.JSONQUOT + (index+1) + SDDef.JSONCCM);//序号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("id")) 			+ SDDef.JSONCCM);		//客户ID
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("describe")) 		+ SDDef.JSONCCM); 		//客户姓名
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("area_desc")) 		+ SDDef.JSONCCM);		//所属片区
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("org_desc")) 		+ SDDef.JSONCCM);		//所属供电所
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("farmer_no")) 		+ SDDef.JSONCCM);		//农排客户编号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("card_no"))	 	+ SDDef.JSONCCM);		//卡号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("identity_no")) 	+ SDDef.JSONCCM);		//身份证号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("village")) 		+ SDDef.JSONCCM);		//自然村名称
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("address")) 		+ SDDef.JSONCCM);		//用户地址
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("post")) 			+ SDDef.JSONCCM);		//邮编
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("phone")) 			+ SDDef.JSONCCM);		//电话
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("mobile")) 		+ SDDef.JSONCCM);		//移动电话
			
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);		
			
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();
		
		return SDDef.SUCCESS;
	}
	
	@JSON(serialize = false)
	public String getFeeAndAlarm() {
		List<Map<String, Object>>	list		= null;
		
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		}

		JDBCDao j_dao = new JDBCDao();
		String ids[] = result.split(",");
		if(ids.length <= 1 || ids[0].isEmpty() || ids[1].isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		}
		String sql = "select yr.rated_z, ya.alarm1 from yffratepara yr, yffalarmpara ya where yr.id="+ids[0]+" and ya.id=" + ids[1];
		list = j_dao.result(sql);
		
		if(list.size()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		}
		
		Map<String,Object> map=list.get(0);
		result = map.get("rated_z") + "," + map.get("alarm1");
		
		return SDDef.SUCCESS;
	}
	
	@JSON(serialize = false)
	public String getAreaCode() {
		String sql = "select area_code,describe from areapara";
		result = DBOper.getValueText(sql);
		return SDDef.SUCCESS;
	}
	
	@JSON(serialize = false)
	public String getMeterList() {
		List<Map<String, Object>>	list		= null;
		StringBuffer 	ret_buf		= new StringBuffer();
		
		JDBCDao j_dao = new JDBCDao();
		String 			sql			= getMeterSql();
		list = j_dao.result(sql);
		if(list.size()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		} 
		
		ret_buf.append(SDDef.JSONROWSTITLE);
		for(int index =0;index<list.size();index++){
			Map<String,Object> map=list.get(index);
			ret_buf.append(SDDef.JSONLBRACES);
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + map.get("rtu_id") + "_" + map.get("mp_id")+ SDDef.JSONNZDATA);
			ret_buf.append(SDDef.JSONQUOT + (index+1) + SDDef.JSONCCM);//序号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("meter_desc")) 	+ SDDef.JSONCCM); 		//电表名称
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("esam_meter_id")) 	+ SDDef.JSONCCM); 		//表号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("area_desc")) 		+ SDDef.JSONCCM);		//所属片区
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("area_code")) 		+ SDDef.JSONCCM);		//片区号	
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("org_desc")) 		+ SDDef.JSONCCM);		//所属供电所
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("feeproj_id")) 	+ SDDef.JSONCCM);		//费率方案
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("yffalarm_id")) 	+ SDDef.JSONCCM);		//报警方案
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("pow_limit")) 		+ SDDef.JSONCCM);		//功率限值
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("nocycut_min")) 	+ SDDef.JSONCCM);		//无脉冲断电时间
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("powerup_prot")) 	+ SDDef.JSONCCM);		//断电保护
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("pt_ratio")) 		+ SDDef.JSONCCM);		//pt变比
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("ct_ratio")) 		+ SDDef.JSONCCM);		//ct变比
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("yffmeter_type")) 		+ SDDef.JSONCCM);		//ct变比
			
			
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);		
			
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();
		return SDDef.SUCCESS;
	}
	
	private String getMeterSql(){
		StringBuffer 	sbfSql 	= new StringBuffer(); 
		sbfSql.append("select o.id as org_id, o.describe as org_desc, ap.id as area_id, ap.describe as area_desc, ap.area_code as area_code,"); 
		sbfSql.append("mt.rtu_id as rtu_id, mt.mp_id as mp_id, mt.describe as meter_desc, mt.meter_id as esam_meter_id, ");
		sbfSql.append("np.feeproj_id as feeproj_id, np.yffalarm_id as yffalarm_id, np.pow_limit as pow_limit, ");
		sbfSql.append("np.nocycut_min as nocycut_min, np.powerup_prot as powerup_prot,mp.pt_ratio as pt_ratio,mp.ct_ratio as ct_ratio, np.yffmeter_type ");
		sbfSql.append("from orgpara o,areapara ap, meterpara mt, meter_extparanp me, nppay_para np,mppara mp, conspara c, line_fzman lfz,rtupara r ");
		sbfSql.append("where ap.org_id = o.id and me.area_id = ap.id and me.rtu_id=mt.rtu_id and me.mp_id=mt.mp_id and me.rtu_id=np.rtu_id ");
		//sbfSql.append("and me.mp_id=np.mp_id and mp.id=mt.mp_id and mp.rtu_id=mt.rtu_id and me.rtu_id >= 50000000 and c.id = ap.id and c.line_fzman_id = lfz.id and r.id = mt.rtu_id");
		sbfSql.append("and me.mp_id=np.mp_id and mp.id=mt.mp_id and mp.rtu_id=mt.rtu_id and me.rtu_id >= 50000000 and c.id = r.cons_id and c.line_fzman_id = lfz.id and r.id = mt.rtu_id");
		if(result != null && !result.isEmpty()){
			JSONObject jsonObj1  = JSONObject.fromObject(result);
			String orgId 	= jsonObj1.getString("orgId").trim();
			String fzmanId 	= jsonObj1.getString("fzmanId").trim();
			String rtuName 	= jsonObj1.getString("rtuName").trim();
			String areaId 	= jsonObj1.getString("areaId").trim();
			String areaNo 	= jsonObj1.getString("areaNo").trim();
			String meterName= jsonObj1.getString("meterName").trim();
			String meterAddr 	= jsonObj1.getString("meterAddr").trim();
			
			if(orgId != null && !orgId.equals("-1") && !orgId.equals("null")){
				sbfSql.append(" and o.id=" + orgId);
			}
			if(areaId != null && !areaId.equals("-1") && !areaId.equals("null")){
				sbfSql.append(" and ap.id=" + areaId);
			}
			if(meterName != null && !meterName.isEmpty() && !meterName.equals("null")){
				sbfSql.append(" and mt.describe like '%" + meterName + "%'");						
			}
			if(fzmanId != null && !fzmanId.equals("-1") && !fzmanId.equals("null")){
				sbfSql.append(" and lfz.id=" + fzmanId);						
			}
			if(rtuName != null && !rtuName.isEmpty() && !rtuName.equals("null")){
				sbfSql.append(" and r.describe like '%" + rtuName + "%'");						
			}	
			if(areaNo != null && !areaNo.isEmpty() && !areaNo.equals("null")){
				sbfSql.append(" and ap.area_code like '%" + areaNo + "%'");						
			}	
			if(meterAddr != null && !meterAddr.isEmpty() && !meterAddr.equals("null")){
				sbfSql.append(" and mt.comm_addr=" + meterAddr);						
			}	
		}
		
		return sbfSql.toString();
	}
	
	private String getSql(){
		StringBuffer 	sbfSql 	= new StringBuffer(); 
		
		JSONObject jsonObj  = JSONObject.fromObject(result);
		String operType 	= jsonObj.getString("operType");
		
		sbfSql.append("select o.describe as org_desc, a.describe as area_desc,a.org_id,a.area_code as area_code,f.* from farmerpara f,areapara a,orgpara o,farmerpay_state s ");
		sbfSql.append(" where o.id=a.org_id and a.id = f.area_id and s.area_id=f.area_id and s.farmer_id = f.id ");
		
		if(field != null && !field.isEmpty()){
			JSONObject jsonObj1  = JSONObject.fromObject(field);
			String orgId 	= jsonObj1.getString("orgId").trim();
			String areaId 	= jsonObj1.getString("areaId").trim();
			String consName	= jsonObj1.getString("consName").trim();
			String farmer_no = jsonObj1.getString("consNo").trim();
			String village	= jsonObj1.getString("village").trim();
			String cardNo	= jsonObj1.getString("cardNo").trim();
			
			if(orgId != null && !orgId.equals("-1") && !orgId.equals("null")){
				sbfSql.append(" and a.org_id=" + orgId);
			}
			if(areaId != null && !areaId.equals("-1") && !areaId.equals("null")){
				sbfSql.append(" and a.id=" + areaId);
			}
			if(consName != null && !consName.isEmpty() && !consName.equals("null")){
				sbfSql.append(" and f.describe like '%" + consName + "%'");						
			}
			if(farmer_no != null && !farmer_no.isEmpty() && !farmer_no.equals("null")){
				sbfSql.append(" and f.farmer_no like '%" +  farmer_no + "%'");						
			}
			if(village != null && !village.isEmpty() && !village.equals("null")){
				sbfSql.append(" and f.village like '%" + village + "%'");				
			}
			if(cardNo != null && !cardNo.isEmpty() && !cardNo.equals("null")){
				sbfSql.append(" and f.card_no like '%" + cardNo + "%'");					
			}				
		}

		switch(Integer.parseInt(operType)){
		case ComntUseropDef.YFF_NPOPER_ADDRES:	//农排操作-开户
			sbfSql.append(" and (s.cus_state is null  or s.cus_state = " + SDDef.YFF_CUSSTATE_INIT + " or s.cus_state = " + SDDef.YFF_CUSSTATE_DESTORY + " )");
			break;
		case ComntUseropDef.YFF_NPOPER_PAY:  	//农排操作-缴费
		case ComntUseropDef.YFF_NPOPER_REVER:	//农排操作-冲正                         
//		case ComntUseropDef.YFF_NPOPER_PAUSE:	//农排操作-暂停
		case ComntUseropDef.YFF_NPOPER_REWRITE:  //农排补写记录
		case ComntUseropDef.YFF_NPOPER_DESTORY:	//农排操作-销户                   
		case ComntUseropDef.YFF_NPOPER_REPAIR:	//农排操作-补卡                       
//		case ComntUseropDef.YFF_NPOPER_CHANGEMETER:	//农排操作-换表换倍率    
//		case ComntUseropDef.YFF_NPOPER_CHANGERATE:	//农排操作-更改费率                       
			sbfSql.append("  and s.cus_state = "+ SDDef.YFF_CUSSTATE_NORMAL); 
			break;
		case ComntUseropDef.YFF_DYOPER_RESTART:		//农排操作-恢复                       
			sbfSql.append("  and s.cus_state = " + SDDef.YFF_CUSSTATE_PAUSE);
			break;
		default:
			break;
		}		
		
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
	
	private String getNpSearchCardSql(JSONObject j_res){
		
		String cardno 	= j_res.getString("cardNo");
		String operType	= j_res.getString("optype");
		
		int op_type = CommBase.strtoi(operType);
		
		//权限相关
		YffManDef yffman = CommFunc.getYffMan();
	    if ((yffman == null) || (yffman.getApptype() & SDDef.YFF_APPTYPE_NPQX )==0 ) {
			return SDDef.EMPTY;
	    }
	    
	    if ((op_type ==  ComntUseropDef.YFF_NPOPER_ADDRES) ||
	    		(op_type ==  ComntUseropDef.YFF_NPOPER_DESTORY)) {
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
		
		sbfSql.append("select o.describe as org_desc, a.describe as area_desc,a.org_id,a.area_code as area_code,f.* from farmerpara f,areapara a,orgpara o, farmerpay_state s ");
		sbfSql.append(" where o.id=a.org_id and a.id = f.area_id and s.area_id=f.area_id and s.farmer_id = f.id and f.card_no= \'" + cardno + "\'");	
		
		switch(op_type){
		case ComntUseropDef.YFF_NPOPER_ADDRES:		//农排操作-开户
			sbfSql.append(" and (s.cus_state is null  or s.cus_state = " + SDDef.YFF_CUSSTATE_INIT + " or s.cus_state = " + SDDef.YFF_CUSSTATE_DESTORY + " )");
			break;
		case -1: break;//清空卡页面，不限制用户状态
		default:
			sbfSql.append(" and  s.cus_state = " + SDDef.YFF_CUSSTATE_NORMAL);
			break;
		}		
		
		if (yffman.getRank() == SDDef.YFF_RANK_ORG) {	//用户所在供电所
			sbfSql.append(" and o.id=" + yffman.getOrgId());
		}
	
		return sbfSql.toString();
		
	}
	
	@JSON(serialize = false)
	public String getInfobyCardNo() {
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		List<Map<String, Object>>	list		= null;
		JDBCDao j_dao = new JDBCDao();
		JSONObject j_res  		= JSONObject.fromObject(result);
		
		String sql = getNpSearchCardSql(j_res);
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
		int i = 0;
		//暂时封掉
//		if(list.size() > 1) {
//			
//			String consNocard = j_res.getString("consNo");
//			consNocard = consNocard.replaceFirst("0*", "");
//			
//			String meterType = j_res.getString("meterType");
//			String consNodb = "";
//			Map<String,Object> tmp = null;
//			
//			for(i = 0; i < list.size(); i++) {
//				tmp = list.get(i);
//				if(meterType.equals(SDDef.YFF_METER_TYPE_ZNK)){
//					consNodb = CommBase.CheckString(tmp.get("cons_no"));
//				}
//				consNodb = consNodb.replaceFirst("0*", "");
//				if(consNodb.endsWith(consNocard)) {
//					break;
//				}
//			}
//		}

		Map<String,Object> map=list.get(i);

		i_rows.put("area_id", 		CommBase.CheckString(map.get("area_id")));		//所属片区ID
		i_rows.put("id", 			CommBase.CheckString(map.get("id")));			//客户ID
		i_rows.put("describe", 		CommBase.CheckString(map.get("describe"))); 	//客户姓名
		i_rows.put("area_desc", 	CommBase.CheckString(map.get("area_desc")));	//所属片区
		i_rows.put("area_code", 	CommBase.CheckString(map.get("area_code")));	//片区码
		i_rows.put("org_desc", 		CommBase.CheckString(map.get("org_desc")));		//所属供电所		
		i_rows.put("farmer_no", 	CommBase.CheckString(map.get("farmer_no")));	//农排客户编号
		i_rows.put("card_no", 		CommBase.CheckString(map.get("card_no")));		//卡号
		i_rows.put("identity_no", 	CommBase.CheckString(map.get("identity_no")));	//身份证号
		i_rows.put("village", 		CommBase.CheckString(map.get("village")));		//自然村名称
		i_rows.put("address", 		CommBase.CheckString(map.get("address")));		//用户地址
		i_rows.put("post", 			CommBase.CheckString(map.get("post")));			//邮编
		i_rows.put("phone", 		CommBase.CheckString(map.get("phone")));		//电话
		i_rows.put("mobile", 		CommBase.CheckString(map.get("mobile")));		//移动电话
		i_rows.put("cardmeter_type","\"["+SDDef.YFF_CARDMTYPE_KE005+"]" + Rd.getDict(Dict.DICTITEM_CARDMETER_TYPE, SDDef.YFF_CARDMTYPE_KE005) + "\""); //写卡类型
		
		result = i_rows.toString();
		return SDDef.SUCCESS;
	}
	
	//开户时输入卡号，卡号唯一判定
	@JSON(serialize = false)
	public String uniqueCardCode(){
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		String[] params = result.split(",");  //area_id + "," + farmer_id + "," + card_no
		String area_id = params[0];
		String farmer_id = params[1];
		String card_no = params[2];
	
		//String sql = "select count(*) from (select f.card_no from farmerpara f ,farmerpay_state s where s.area_id=f.area_id and s.farmer_id = f.id and (s.cus_state is null  or s.cus_state = " + SDDef.YFF_CUSSTATE_INIT + " or s.cus_state = " + SDDef.YFF_CUSSTATE_DESTORY + " )) as a where a.card_no = " + result;
		//String sql = "select count(*) from farmerpara where card_no = \'" + result + "\'";
		//String sql = "select count(*) from (select f.card_no from farmerpara f left join farmerpay_state s on s.area_id=f.area_id and s.farmer_id = f.id where f.area_id !=" + area_id + " or f.id != " + farmer_id + " and s.cus_state = " + SDDef.YFF_CUSSTATE_NORMAL + ") as t where t.card_no = \'" + card_no + "\'";
		//temp chg  if 日后 发现问题 及时 改进 20130311
		String sql = "select count(*) from farmerpara f, farmerpay_state s where  s.area_id = f.area_id and s.farmer_id = f.id and (f.area_id !=" + area_id + " or f.id != " + farmer_id + ") and s.cus_state = " + SDDef.YFF_CUSSTATE_NORMAL + " and f.card_no = \'" + card_no + "\'";

		JDBCDao j_dao = new JDBCDao();
		ResultSet rst = j_dao.executeQuery(sql);
		try {
			if(rst.next()){
				int num = rst.getInt(1);
				if(num == 0){
					result = SDDef.SUCCESS;
				}else{
					result = SDDef.FAIL;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			j_dao.closeRs(rst);
		}
		return SDDef.SUCCESS;
	}
	
	@JSON(serialize = false)
	public String getRtuOnlineFlag() {

		int   rtu_id = CommBase.strtoi(rtuId);		
		JSONObject j_obj = new JSONObject();
		
		boolean onlineflag = false;
		OnlineRtu.RTUSTATE_ITEM rtustate_item = OnlineRtu.getOneRtuState(rtu_id);
		if ((rtustate_item != null) && (rtustate_item.comm_state == 1)) onlineflag = true;

		String t_str = onlineflag ? "在线" : "离线";
		j_obj.put("onlineflag", t_str);
		
		result = j_obj.toString();
		
		return SDDef.SUCCESS;
	}
	
	//查询黑名单列表-工具-远程操作-黑名单
	@JSON(serialize = false)
	public String getBlackList(){
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		List<Map<String, Object>>	list		= null;
		String[] params = result.split(",");  //rtu_id,mp_id
		String rtu_id = params[0];
		String mp_id  = params[1];
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id area_id, a.describe area_desc, f.id farmer_id, f.farmer_no, f.describe farmer_desc, chg.chg_date, chg.chg_time,chg.card_no_old,chg.card_no_new ");
		sql.append("from changecardnp chg, areapara a, meter_extparanp me,farmerpara f ");
		sql.append("where me.area_id = a.id and chg.area_id = a.id and chg.area_id = f.area_id and chg.farmer_id = f.id and chg.card_no_old != chg.card_no_new and me.rtu_id = " + rtu_id + " and me.mp_id = " + mp_id);
		sql.append(" order by chg.chg_date desc");

		JDBCDao j_dao = new JDBCDao();
		list = j_dao.result(sql.toString());
		if(list.size()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		}
		StringBuffer ret_buf = new StringBuffer();
		ret_buf.append(SDDef.JSONROWSTITLE);
		for(int index =0;index<list.size();index++){
			Map<String,Object> map=list.get(index);
			ret_buf.append(SDDef.JSONLBRACES);
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + map.get("area_id") + "_" + map.get("farmer_id") + index + SDDef.JSONNZDATA);
			ret_buf.append(SDDef.JSONQUOT + (index+1) + SDDef.JSONCCM);//序号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("area_desc")) 		+ SDDef.JSONCCM); 		//所属片区
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("farmer_no")) 		+ SDDef.JSONCCM); 		//客户编号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("farmer_desc")) 	+ SDDef.JSONCCM);		//客户名称
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(CommBase.CheckString(map.get("chg_date"))) 		+ SDDef.JSONCCM);		//更换日期
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToHMS(CommBase.CheckString(map.get("chg_time")),1) 		+ SDDef.JSONCCM);		//更换时间
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("card_no_old")) 	+ SDDef.JSONCCM);		//旧卡号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("card_no_new")) 	+ SDDef.JSONCCM);		//新卡号
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);			
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();
		return SDDef.SUCCESS;
	}
	
	//查询黑名单列表-工具-工具卡-黑名单和远程的一样，就是列首多了个checkbox
	@JSON(serialize = false)
	public String getBlackList0(){
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		List<Map<String, Object>>	list		= null;
		String[] params = result.split(",");  //rtu_id,mp_id
		String rtu_id = params[0];
		String mp_id  = params[1];
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id area_id, a.describe area_desc, f.id farmer_id, f.farmer_no, f.describe farmer_desc, chg.chg_date, chg.chg_time,chg.card_no_old,chg.card_no_new ");
		sql.append("from changecardnp chg, areapara a, meter_extparanp me,farmerpara f ");
		sql.append("where me.area_id = a.id and chg.area_id = a.id and chg.area_id = f.area_id and chg.farmer_id = f.id and chg.card_no_old != chg.card_no_new and me.rtu_id = " + rtu_id + " and me.mp_id = " + mp_id);
		sql.append(" order by chg.chg_date desc");

		JDBCDao j_dao = new JDBCDao();
		list = j_dao.result(sql.toString());
		if(list.size()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		}
		StringBuffer ret_buf = new StringBuffer();
		ret_buf.append(SDDef.JSONROWSTITLE);
		for(int index =0;index<list.size();index++){
			Map<String,Object> map=list.get(index);
			ret_buf.append(SDDef.JSONLBRACES);
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + map.get("area_id") + "_" + map.get("farmer_id") + index + SDDef.JSONDATA);
			ret_buf.append(SDDef.JSONQUOT + (index+1) + SDDef.JSONCCM);//序号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("area_desc")) 		+ SDDef.JSONCCM); 		//所属片区
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("farmer_no")) 		+ SDDef.JSONCCM); 		//客户编号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("farmer_desc")) 	+ SDDef.JSONCCM);		//客户名称
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToYMD(CommBase.CheckString(map.get("chg_date"))) 		+ SDDef.JSONCCM);		//更换日期
			ret_buf.append(SDDef.JSONQUOT + CommFunc.FormatToHMS(CommBase.CheckString(map.get("chg_time")),1) 		+ SDDef.JSONCCM);		//更换时间
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("card_no_old")) 	+ SDDef.JSONCCM);		//旧卡号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("card_no_new")) 	+ SDDef.JSONCCM);		//新卡号
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);			
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();
		return SDDef.SUCCESS;
	}
	
	//查询白名单
	@JSON(serialize = false)
	public String getWhiteList(){
		if(result == null || result.isEmpty()){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		List<Map<String, Object>>	list		= null;
		String[] params = result.split(",");  //rtu_id,mp_id
		String rtu_id = params[0];
		String mp_id  = params[1];
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id area_id, a.describe area_desc, f.id farmer_id, f.farmer_no, f.describe farmer_desc, f.card_no ");
		sql.append("from areapara a, farmerpara f, meter_extparanp me ");
		sql.append("where me.area_id = a.id and a.id = f.area_id and me.rtu_id = " + rtu_id + "and me.mp_id = " + mp_id);
		
		JDBCDao j_dao = new JDBCDao();
		list = j_dao.result(sql.toString());
		if(list.size()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS; 
		}
		StringBuffer ret_buf = new StringBuffer();
		ret_buf.append(SDDef.JSONROWSTITLE);
		for(int index =0;index<list.size();index++){
			Map<String,Object> map=list.get(index);
			ret_buf.append(SDDef.JSONLBRACES);
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + map.get("area_id") + "_" + map.get("farmer_id")+ index + SDDef.JSONNZDATA);
			ret_buf.append(SDDef.JSONQUOT + (index+1) + SDDef.JSONCCM);//序号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("area_desc")) 		+ SDDef.JSONCCM); 		//所属片区
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("farmer_no")) 		+ SDDef.JSONCCM); 		//客户编号
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("farmer_desc")) 	+ SDDef.JSONCCM);		//客户名称
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("card_no")) 	+ SDDef.JSONCCM);		//卡号
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);			
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
	public String getRtuId() {
		return rtuId;
	}
	public void setRtuId(String rtuId) {
		this.rtuId = rtuId;
	}
	
}
