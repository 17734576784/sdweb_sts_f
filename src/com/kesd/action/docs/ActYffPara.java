package com.kesd.action.docs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.struts2.json.annotations.JSON;
import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.SDOperlog;
import com.kesd.common.YDTable;
import com.kesd.dao.HibPage;
import com.kesd.dbpara.MeterPara;
import com.kesd.dbpara.MeterStsPara;
import com.kesd.dbpara.MpPara;
import com.kesd.dbpara.MpPayAlmState;
import com.kesd.dbpara.MpPayPara;
import com.kesd.dbpara.MpPayState;
import com.kesd.dbpara.YffManDef;
import com.kesd.service.DBOper;
import com.kesd.util.Rd;
import com.libweb.common.CommBase;
import com.libweb.dao.HibDao;
import com.libweb.dao.JDBCDao;
import com.sts.common.StsFunc;

public class ActYffPara {
	private static final long serialVersionUID = -601568323512959063L;
	private String 		result;
	private String 		pageNo;		//页号
	private MpPayPara 	mppaypara;	//总加组档案
	private MpPara 		mppara;
	private MeterPara 	meterpara;
	private MeterStsPara meterstspara;
	
	private MpPayAlmState mppayalmstate;
	private String 		tableName;
	private String 		value;
	private String 		text;
	private String 		ids;
	private String 		yffState;
	private String 		pl;
	private String	 	field;
	private String   	pageSize;
	
	@JSON(serialize=false)
	@SuppressWarnings("unchecked")
	public String getValueAndText(){
		String hql = "select a.id,a.describe from " + tableName.split(",")[0] + " as a where a.bakFlag = 1 and a.rtuId ="+ tableName.split(",")[1];
		if(result!=null&&value!=null){
			hql+= " and a." + result + "="+ value;
		}
		
		HibDao hib_dao = new HibDao();
		StringBuffer ret_buf = new StringBuffer();
		ret_buf.append(SDDef.JSONROWSTITLE);
		List list = hib_dao.loadAll(hql);
		if(list.size() == 0){
			result = "";
			return SDDef.SUCCESS;
		}
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Object[] obj = (Object[])it.next();
			ret_buf.append(SDDef.JSONLBRACES);
			ret_buf.append(SDDef.JSONQUOT + "value" + SDDef.JSONQACQ + obj[0]                       + SDDef.JSONCCM); 
			ret_buf.append(SDDef.JSONQUOT + "text" 	+ SDDef.JSONQACQ + CommBase.CheckString(obj[1]) + SDDef.JSONQRBCM);
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		
		result=ret_buf.toString();
		
		return SDDef.SUCCESS;
	}

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
	
//	@SuppressWarnings("unchecked")
	public String addOrEdit()
	{
		JDBCDao jdbcDao = new JDBCDao();
		HibDao  hib_dao = new HibDao();
		Short   mpId   = 0;		//查找最大的mpid
		boolean add     = false;
		
		int 	keyType  = 0;
		String  keyRegno = "";
		String  rOrgNo   = "";
		
		MpPayState	  mppaystate 	  = new MpPayState();
		MpPayAlmState mppayalmstate   = new MpPayAlmState();
		//MeterStsPara  meterstspara    = new MeterStsPara();
		
		String meterAddr = meterpara.getCommAddr();
		int addrSize = meterAddr.length();
		String meterAddrFront = meterAddr.substring(0, meterAddr.length() - 1);
		String meterAddrCheck = meterAddr.substring(meterAddr.length()-1, meterAddr.length());
		
		//表地址校验
		if(addrSize == 11 || addrSize == 13){
			int check = StsFunc.getLuhnMod(StsFunc.getMeterAddrByte(meterAddrFront), meterAddrFront.length());
			if(check != CommFunc.objectToInt(meterAddrCheck)){
				result = "INVALID METER ADDRESS";
				return SDDef.SUCCESS;
			}
		}
		
		//查询此电表是否是 硬加密供电所 管辖的电表 
		String encryptTypeSql = "select o.key_type,o.encrypt_type,o.key_regno,org.r_org_no from orgpara as org,org_stspara as o,rtupara as rtu,conspara as cons " +
									"where org.id = o.org_id and rtu.cons_id = cons.id and cons.org_id = o.org_id and rtu.id = " + meterpara.getRtuId();
		boolean encryptTypeFlag = false;	
		List<Map<String,Object>> encryptTypeList = jdbcDao.result(encryptTypeSql);
		
		keyType		= 	CommFunc.objectToInt(encryptTypeList.get(0).get("key_type"));
		keyRegno	=	CommFunc.objectToString(encryptTypeList.get(0).get("key_regno"));
		rOrgNo		=	CommFunc.objectToString(encryptTypeList.get(0).get("r_org_no"));
		if(CommFunc.objectToString(encryptTypeList.get(0).get("encrypt_type")).equals("1")){
			encryptTypeFlag = true;
		}
		
		try {
			if(mppara.getRtuId()== null||mppara.getRtuId() == -1){
				add = true;//查找meterpara的最大的rtuid mpid
				
				
				if(mppara.getId() == null){
					
					String hql = "select max(mpId) from "+YDTable.TABLECLASS_METERPARA+" as a where a.rtuId="+meterpara.getRtuId();
					List list = hib_dao.loadAll(hql);
					if(list.get(0) != null) {
						mpId = (Short)list.get(0);
						mpId ++;
					}
					//if(mpId>1024){
					if(mpId>9999){	
						result = "maxID";
						return SDDef.SUCCESS;
					}
					meterpara.setMpId(mpId);
					mppara.setId(mpId);
					mppaypara.setMpId(mpId);
					mppayalmstate.setMpId(mpId);
					
					meterpara.setRtuId(meterpara.getRtuId());
					mppara.setRtuId(meterpara.getRtuId());
					mppaypara.setRtuId(meterpara.getRtuId());
					mppayalmstate.setRtuId(meterpara.getRtuId());
					
					mppaystate.setMpId(mpId);
					mppaystate.setRtuId(mppara.getRtuId());
					//查询是否是硬加密
					if(encryptTypeFlag){
						mppaystate.setCusState(Byte.parseByte("1"));
						mppaystate.setKhDate(CommFunc.objectToInt(CommFunc.nowDateYmd()));
						mppaystate.setXhDate(CommFunc.objectToInt(CommFunc.nowDateYmd()));
						mppaystate.setBuyTimes(0);
					}
					
					meterstspara.setRtuId(mppara.getRtuId());
					meterstspara.setMpId(mpId);
					meterstspara.setMeterKey("0124000000000001");
					meterstspara.setOldmtKey("0124000000000001");
					
					meterstspara.setSgc(rOrgNo);
					meterstspara.setDrn(meterpara.getCommAddr());
					meterstspara.setRegno(CommFunc.objectToByte(keyRegno));
					meterstspara.setKrn(mppaypara.getKeyVersion());
					meterstspara.setOldDrn(meterpara.getCommAddr());
					meterstspara.setOldKen(255);
					meterstspara.setOldKrn((byte)1);
					meterstspara.setKt((byte)keyType);
					meterstspara.setOldKt((byte)keyType);
					meterstspara.setOldSgc(rOrgNo);
					meterstspara.setOldTi((byte)1);
				}else{
					String hql = "select a.id from "+YDTable.TABLECLASS_MPPARA+" as a where a.rtuId="+meterpara.getRtuId()+" and a.id="+mppara.getId();
					List list = hib_dao.loadAll(hql);
					if(list.size() > 0) {
						result = "existID";
						return SDDef.SUCCESS;
					}
					meterpara.setMpId(mppara.getId());
					mppara.setId(mppara.getId());
					mppaypara.setMpId(mppara.getId());
					mppaystate.setMpId(mppara.getId());
					mppayalmstate.setMpId(mppara.getId());
					
					//查询是否是硬加密
					if(encryptTypeFlag){
						mppaystate.setCusState(Byte.parseByte("1"));
						mppaystate.setKhDate(CommFunc.objectToInt(CommFunc.nowDateYmd()));
						mppaystate.setXhDate(CommFunc.objectToInt(CommFunc.nowDateYmd()));
						mppaystate.setBuyTimes(0);
					}
					
					meterpara.setRtuId(meterpara.getRtuId());
					mppara.setRtuId(meterpara.getRtuId());
					mppaypara.setRtuId(meterpara.getRtuId());
					mppayalmstate.setRtuId(meterpara.getRtuId());
					mppaystate.setRtuId(meterpara.getRtuId());
					
					meterstspara.setRtuId(mppara.getRtuId());
					meterstspara.setMpId(mppara.getId());
					meterstspara.setMeterKey("0124000000000001");
					meterstspara.setOldmtKey("0124000000000001");
					
					meterstspara.setSgc(rOrgNo);
					meterstspara.setDrn(meterpara.getCommAddr());
					meterstspara.setRegno(CommFunc.objectToByte(keyRegno));					
					meterstspara.setKrn(mppaypara.getKeyVersion());
					meterstspara.setOldDrn(meterpara.getCommAddr());
					meterstspara.setOldKen(255);
					meterstspara.setOldKrn((byte)1);
					meterstspara.setOldKt((byte)1);
					meterstspara.setOldSgc(rOrgNo);
					meterstspara.setOldTi((byte)1);
				}
			}
			else {
				//修改 
				meterpara.setMpId(meterpara.getMpId());
				mppara.setId(meterpara.getMpId());
				mppaypara.setMpId(meterpara.getMpId());
				mppayalmstate.setMpId(meterpara.getMpId());
				meterstspara.setMpId(meterpara.getMpId());
				
				meterpara.setRtuId(meterpara.getRtuId());
				mppara.setRtuId(meterpara.getRtuId());
				mppaypara.setRtuId(meterpara.getRtuId());
				mppayalmstate.setRtuId(meterpara.getRtuId());
				meterstspara.setRtuId(meterpara.getRtuId());
				meterstspara.setKrn(mppaypara.getKeyVersion());
				meterstspara.setSgc(rOrgNo);
				meterstspara.setRegno(CommFunc.objectToByte(keyRegno));
			}
			
			mppara.setBakFlag((byte)0);	//主附表标志，这里定死为0，主表
			meterpara.setDescribe(mppara.getDescribe());
			//检测电表地址是否存在
			String addr = meterpara.getCommAddr();
			String hql = " from "+YDTable.TABLECLASS_METERPARA+" as a where a.rtuId=" + meterpara.getRtuId() + " and a.mpId<>" + meterpara.getMpId() + " and a.commAddr='" + addr + "'";
			List list = hib_dao.loadAll(hql);
			if(list.size() > 0) {
				meterpara=(MeterPara)list.iterator().next();
				StringBuffer ret_buf= new StringBuffer(); 
				ret_buf.append(SDDef.JSONLBRACES);
				ret_buf.append(SDDef.JSONQUOT  + "rtuId"  + SDDef.JSONQACQ + meterpara.getRtuId() 	+ SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "rtuDesc"  	+ SDDef.JSONQACQ + DBOper.getDescribeById(YDTable.TABLECLASS_RTUPARA, meterpara.getRtuId()) + SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "mpId" 		+ SDDef.JSONQACQ + meterpara.getMpId()		+ SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "mpDesc" 	+ SDDef.JSONQACQ + meterpara.getDescribe()	+ SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "resId" 	+ SDDef.JSONQACQ + meterpara.getResidentId()+ SDDef.JSONCCM);
				ret_buf.append(SDDef.JSONQUOT + "resDesc"	+ SDDef.JSONQACQ + DBOper.getDescribeById(YDTable.TABLECLASS_RESIDENTPARA, meterpara.getResidentId()) + SDDef.JSONQUOT + SDDef.JSONRBRACES);
				field = ret_buf.toString();
				result = "existAddr";
				return SDDef.SUCCESS;
			}			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		
		//添加或修改处理
		Object[] obj = {meterstspara,meterpara,mppara,mppaypara,mppaystate,mppayalmstate};
		
		if(add){
			try{
				if (hib_dao.saveOrUpdate(obj)) {
					String hql = "update "+YDTable.TABLECLASS_RTUPARA+" set jlpNum=jlpNum+1 where id=" + meterpara.getRtuId();
					if(!hib_dao.updateByHql(hql)) {
						result = SDDef.FAIL;
					}
					else {
						SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_ADD, "Add Meter Archive["+ meterpara.getMpId() +"]");

						result = SDDef.SUCCESS;					
					}
				}else{
					result = SDDef.FAIL;
				}
			}catch(Exception e){
				e.printStackTrace();
			};
		}else{
			//修改密钥参数时 判断
			String sql = "select ti,ken,krn,drn,regno,sgc from meter_stspara where rtu_id = " + meterstspara.getRtuId() + " and mp_id = " + meterstspara.getMpId();			
			List<Map<String,Object>> list = jdbcDao.result(sql);
			String 		ti 				= CommFunc.objectToString(list.get(0).get("ti"));
			String 		ken 			= CommFunc.objectToString(list.get(0).get("ken"));
			String 		krn 			= CommFunc.objectToString(list.get(0).get("krn"));
			String 		drn 			= CommFunc.objectToString(list.get(0).get("drn"));
			String		regno			= CommFunc.objectToString(list.get(0).get("regno"));
			String		sgc			= CommFunc.objectToString(list.get(0).get("sgc"));
			
			if(meterstspara.getTi() != CommFunc.objectToInt(ti)){
				sql = "";
				if(ti == null || ti == ""){
					sql = "update meter_stspara set old_ti = " + meterstspara.getTi() + ",ti = " + meterstspara.getTi() + 
					" where rtu_id = "+ meterstspara.getRtuId() +" and mp_id = " + meterstspara.getMpId();
				}else{
					sql = "update meter_stspara set keychange = 1, ti = " + meterstspara.getTi() + 
					" where rtu_id = "+ meterstspara.getRtuId() +" and mp_id = " + meterstspara.getMpId();
				}
				
				
				if(!jdbcDao.executeUpdate(sql)){
					result = SDDef.FAIL;
					return SDDef.SUCCESS;
				}else{
					SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_UPDATE, "update meter_stspara ti parameter " + ti + " to " + meterstspara.getTi() + " successfully.");
				}
			}
			if(meterstspara.getKen() != CommFunc.objectToInt(ken)){
				sql = "";
				if(ken == null || ken == ""){
					sql = "update meter_stspara set old_ken = " + meterstspara.getKen() +",ken = " + meterstspara.getKen() + 
					" where rtu_id = "+ meterstspara.getRtuId() +" and mp_id = " + meterstspara.getMpId();				
				}else{
					sql = "update meter_stspara set keychange = 1, ken = " + meterstspara.getKen() + 
					" where rtu_id = "+ meterstspara.getRtuId() +" and mp_id = " + meterstspara.getMpId();				
				}
				
				
				
				if(!jdbcDao.executeUpdate(sql)){
					result = SDDef.FAIL;
					return SDDef.SUCCESS;					
				}else{
					SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_UPDATE, "update meter_stspara ken parameter " + ken + " to" + meterstspara.getKen() + " successfully.");
				}
			}
			if(meterstspara.getKrn() != CommFunc.objectToInt(krn)){
				sql = "";
				if(krn == null || krn == ""){
					sql = "update meter_stspara set old_krn = " +  meterstspara.getKrn() + ",krn = " +  meterstspara.getKrn() + 
					" where rtu_id = "+ meterstspara.getRtuId() +" and mp_id = " + meterstspara.getMpId();
				}else{
					sql = "update meter_stspara set keychange = 1, krn = " +  meterstspara.getKrn() + 
					" where rtu_id = "+ meterstspara.getRtuId() +" and mp_id = " + meterstspara.getMpId();	
				}
							
				if(!jdbcDao.executeUpdate(sql)){
					result = SDDef.FAIL;
					return SDDef.SUCCESS;				
				}else{
					SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_UPDATE, "update meter_stspara krn parameter " + krn + " to " + meterstspara.getKrn() + " successfully.");
				}
			}	
			if (meterstspara.getRegno() != CommFunc.objectToInt(regno)) {
				sql = "";
				if(regno == null || regno == ""){
					sql = "update meter_stspara set old_regno = " +  meterstspara.getRegno() + ",regno = " +  meterstspara.getRegno() + 
					" where rtu_id = "+ meterstspara.getRtuId() +" and mp_id = " + meterstspara.getMpId();
				}else{
					sql = "update meter_stspara set keychange = 1, regno = " +  meterstspara.getRegno() + 
					" where rtu_id = "+ meterstspara.getRtuId() +" and mp_id = " + meterstspara.getMpId();	
				}
							
				if(!jdbcDao.executeUpdate(sql)){
					result = SDDef.FAIL;
					return SDDef.SUCCESS;				
				}else{
					SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_UPDATE, "update meter_stspara sts-module register parameter '" + regno + "' to '" + meterstspara.getRegno() + "' successfully.");
				}
			}
			
			if(!meterpara.getCommAddr().equals(drn)){
				sql = "";
				if(drn == null || drn == ""){
					sql = "update meter_stspara set old_drn = '" +  meterpara.getCommAddr()  + "' ,drn = '" +  meterpara.getCommAddr()  + 
					"' where rtu_id = "+ meterstspara.getRtuId() +" and mp_id = " + meterstspara.getMpId();	
				}else{
					sql = "update meter_stspara set keychange = 1, drn = '" +  meterpara.getCommAddr()  + "' "  + 
					" where rtu_id = "+ meterstspara.getRtuId() +" and mp_id = " + meterstspara.getMpId();	
				}
							
				if(!jdbcDao.executeUpdate(sql)){
					result = SDDef.FAIL;
					return SDDef.SUCCESS;				
				}else{
					SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_UPDATE, "update meter_stspara drn parameter '" + drn + "' to '" + meterpara.getCommAddr() + "' successfully.");
				}
			}
			
			String[] hql = new String[4];
				hql[0]=" update " + YDTable.TABLECLASS_MPPARA + " set " +
						" describe='" + mppara.getDescribe() +
						"', reserve1='" + mppara.getReserve1() +
						"',mpType="  + mppara.getMpType() +
						",ctDenominator=" + mppara.getCtDenominator() +
						",rp=" + mppara.getRp() +
						",mi=" + mppara.getMi() +
						",bdFactor=" + mppara.getBdFactor() +
						",vfactor=" + mppara.getVfactor();
						if(mppara.getPtRatio()!=null){
							hql[0]+=",pt_ratio=" + mppara.getPtRatio();
						}
						if(mppara.getCtRatio()!=null){
							hql[0]+=",ct_ratio=" + mppara.getCtRatio();
						}
						hql[0]+=" where rtuId=" + mppara.getRtuId() + " and id="+mppara.getId();
				hql[1]=" update " + YDTable.TABLECLASS_METERPARA + " set " +
						"commAddr='" +	meterpara.getCommAddr() +
						"',describe='" +	meterpara.getDescribe() + 
						"',assetNo='"  +	CommFunc.CheckString(meterpara.getAssetNo())  + 
						"',factory="    + meterpara.getFactory()+
						",madeNo='"   + meterpara.getCommAddr()  +
						"',commPwd='"   + CommFunc.CheckString(meterpara.getCommPwd())  +
						"',prepayflag='"   + CommFunc.CheckString(meterpara.getPrepayflag())+
						"',residentId='"  + CommFunc.CheckString(meterpara.getResidentId())  +
						"' where rtuId=" + meterpara.getRtuId() + " and mpId=" + meterpara.getMpId();
				hql[2] = " update " + YDTable.TABLECLASS_MPPAYPARA  + " set " +

						"caclType='" +	CommFunc.CheckString(mppaypara.getCaclType()) + 
						"',feectrlType='"  +	CommFunc.CheckString(mppaypara.getFeectrlType())  + 
						"',payType="    + CommFunc.CheckString(mppaypara.getPayType())+
						",feeprojId='"   + CommFunc.CheckString(mppaypara.getFeeprojId())  +
						"',tzVal='"   + CommFunc.CheckString(mppaypara.getTzVal())+
						"',yffmeterType='"  + CommFunc.CheckString(mppaypara.getYffmeterType())  +				
						"',protSt='"  + CommFunc.CheckString(mppaypara.getProtSt())  +
						"',protEd='"  + CommFunc.CheckString(mppaypara.getProtEd())  +
						"',keyVersion='"  + CommFunc.CheckString(mppaypara.getKeyVersion())  +
						//"',tariffIndex='"  + CommFunc.CheckString(mppaypara.getTariffIndex())  +
						"' where rtuId=" + mppaypara.getRtuId() + " and mpId=" + mppaypara.getMpId();
				hql[3] = " update " + YDTable.TABLECLASS_METERSTSPARA + " set " +
						"reserve1='" 		+ CommFunc.CheckString(meterstspara.getReserve1()) + "'" +
						
						//"krn="  		+ meterstspara.getKrn() + "," +
						 
						
						//"oldDrn=" 		+ CommFunc.CheckString(meterstspara.getOldDrn()) + "," +
						//"oldKen=" 		+ meterstspara.getOldKen() + "," +
						//"krn="  		+ meterstspara.getKrn() + "," +
						//"oldKt="   		+ meterstspara.getOldKt() + "," +
						//"oldmtKey="   	+ CommFunc.CheckString(meterstspara.getOldmtKey()) + "," +
						//"oldSgc="   	+ CommFunc.CheckString(meterstspara.getOldSgc()) + "," +
						//"ti="   		+ meterstspara.getTi() + "," +
						//"oldTi="   		+ meterstspara.getOldTi() +
						" where rtuId=" + meterstspara.getRtuId() + " and mpId=" + meterstspara.getMpId();

			if (hib_dao.updateByHql(hql)) {
				SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_UPDATE, "Modify Meter Archive["+ meterpara.getMpId() +"]");

				result = SDDef.SUCCESS;
			}else{
				result = SDDef.FAIL;
			}
		}
		
		return SDDef.SUCCESS;		
	}	

	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : delResidentParaById
	* <p>
	* <p>DESCRIPTION : 根据id、rtuId删除居民用户档案记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	public String delYffParaById()
	{
		HibDao  hib_dao = new HibDao();
		String id[]	      = result.split(SDDef.JSONSPLIT);
		byte   flag	   	  = 1;
		String currentRes = null;
		for (int i = 0; i < id.length; i++) {
			String mpId = id[i].split(",")[0];
			String rtuId = id[i].split(",")[1];
			String[] hql = new String[5];
			hql[0] = "delete from "+YDTable.TABLECLASS_METERPARA+" as a where a.mpId=" + mpId +" and a.rtuId="+rtuId;
			hql[1] = "delete from "+YDTable.TABLECLASS_MPPARA+" as a where a.id=" + mpId +" and a.rtuId="+rtuId;
			hql[2] = "delete from "+YDTable.TABLECLASS_MPPAYPARA+" as a where a.mpId=" + mpId +" and a.rtuId="+rtuId;
			hql[3] = "delete from MeterStsPara as a where a.mpId=" + mpId +" and a.rtuId="+rtuId;
			hql[4] = "delete from MpPayState as a where a.mpId=" + mpId + " and a.rtuId="+rtuId;

			if (hib_dao.updateByHql(hql)) {
				SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_DELETE, "Delete Meter Archive["+ mpId +"]");

				
				String hqlNum = "update "+YDTable.TABLECLASS_RTUPARA+" set jlpNum=jlpNum-1 where jlpNum>0 and id=" + rtuId;
				if(!hib_dao.updateByHql(hqlNum)) {
					result = SDDef.FAIL;
				}				
				result = SDDef.SUCCESS;
			}else{
				result = SDDef.FAIL;
			}			
		}
		return SDDef.SUCCESS;
	}	
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : getMpParaById
	* <p>
	* <p>DESCRIPTION : 通过ID获取档案记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@JSON(serialize = false)
	@SuppressWarnings("unchecked")
	public String getYffParaById() throws Exception
	{
		StringBuffer ret_buf  = new StringBuffer();
		HibDao       hib_dao  = new HibDao();
//		String hql = "select p.rtuId,p.mpId,p.feectrlType,p.caclType,p.payType,p.feeprojId,p.yffalarmId,p.feeBegindate,p.protSt,p.protEd,p.ngloprotFlag,p.powerRela1,p.powerRela2,p.powerRelask1,p.powerRelask2,p.keyVersion,p.yffmeterType,p.cryplinkId,p.payAdd,p.tzVal,p.powerRelaf,p.feeChgf,p.feeChgid,p.feeChgdate,p.feeChgtime,m.prepayflag,p.jtCycleMd,p.cbCycleType,p.cbDayhour,p.fxdfFlag,p.fxdfBegindate,p.localMaincalcf from " 
//					+ YDTable.TABLECLASS_MPPAYPARA + " as p, " + YDTable.TABLECLASS_METERPARA + " as m where p.rtuId=m.rtuId and p.mpId=m.mpId and p.mpId=" + result.split(",")[0] +" and p.rtuId = "+result.split(",")[1];
		String hql = "select p.feectrlType,p.caclType,p.payType,p.feeprojId,p.protSt,p.protEd,p.keyVersion,mt.ti, mt.ken, p.yffmeterType,p.tzVal,m.prepayflag from " 
			        + YDTable.TABLECLASS_MPPAYPARA + " as p, " + YDTable.TABLECLASS_METERPARA + " as m, " + YDTable.TABLECLASS_METERSTSPARA + " as mt where p.rtuId=m.rtuId and p.mpId=m.mpId and mt.rtuId=p.rtuId and mt.mpId=p.mpId and p.mpId=" + result.split(",")[0] +" and p.rtuId = "+result.split(",")[1];
		List   list = hib_dao.loadAll(hql);
		Object[] object = (Object[])list.get(0);
		ret_buf.append(SDDef.JSONSELDATA);
		for(int i = 0; i<object.length; i ++) {
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(object[i]) + SDDef.JSONQUOT  + SDDef.JSONCOMA);
		}

		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		
		if (result == null) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		result = ret_buf.toString();	
		
		return SDDef.SUCCESS;
	}
	
	
	@JSON(serialize = false)
	@SuppressWarnings("unchecked")
	public String getbakFlag() throws Exception
	{
		StringBuffer ret_buf  = new StringBuffer();
		HibDao       hib_dao  = new HibDao();
		String hql = "select bakFlag from " + YDTable.TABLECLASS_MPPARA + " where id=" + result.split(",")[0] +" and rtuId = "+result.split(",")[1];
		List   list = hib_dao.loadAll(hql);
		Object object = (Object)list.get(0);
			ret_buf.append(CommBase.CheckString(object));
		
		result = ret_buf.toString();
		
		if (result == null) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		
		result = ret_buf.toString();	
		
		return SDDef.SUCCESS;
	}
	
	private String equalRtuAndYff(Object obj,ArrayList<String[]> list){
		String[] temp=new String[2];
		for(int i=0;i<list.size();i++){
			temp=list.get(i);
			if(obj==null){
				return SDDef.EMPTY;
			}
			if(obj.toString().equals(temp[0])){
				return CommBase.CheckString(temp[1]);
			}
		}
		return "";
	}
	private String equalMp(Object obj,Object obj2 ,ArrayList<String[]> list){
		String[] temp=new String[3];
		for(int i=0;i<list.size();i++){
			temp=list.get(i);
			if(obj==null){
				return SDDef.EMPTY;
			}
			if(temp[0].toString().equals(obj.toString()) && temp[1].toString().equals(obj2.toString())){
				return CommBase.CheckString(temp[2]);
			}
		}
		return "";
	}
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : execute
	* <p>
	* <p>DESCRIPTION : 分页获取测量点费控记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		/**
		 * 将表yffratepara的id和describe两个字段放到ArrayList<String[]>
		 * 将表mppara的                 id和rtu_id和describte放到ArrayList<String[]>
		 * 增加比较是否相等的方法    equalId();
		 */
		String describe = null;
		String rtu_id = "-1";
		String yffSql="select id,describe from yffratepara ";
		String yffAPSql="select id, describe from yffalarmpara";
		String mpSql ="select id , rtu_id , describe from mppara where rtu_id >=" + SDDef.JMRTUID;
		JDBCDao dao=new JDBCDao();
		
		ResultSet rs=dao.executeQuery(yffSql);
		ResultSet rsYffAP=dao.executeQuery(yffAPSql);
		ResultSet rsMp=dao.executeQuery(mpSql);
		
		ArrayList<String[]> yffList=new ArrayList<String[]>();
		ArrayList<String[]> yffAPList=new ArrayList<String[]>();
		ArrayList<String[]> mpList =new ArrayList<String[]>();
		
		
		String[] temp=new String[2];
		try {
			while(rs.next()){
			    temp=new String[2];
				temp[0]=rs.getString("id");
				temp[1]=rs.getString("describe");
				yffList.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			dao.closeRs(rs);
		}
		
		String[] tempYffAP=new String[2];
		try {
			while(rsYffAP.next()){
				tempYffAP=new String[2];
				tempYffAP[0]=rsYffAP.getString("id");
				tempYffAP[1]=rsYffAP.getString("describe");
				yffAPList.add(tempYffAP);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			dao.closeRs(rsYffAP);
		}
		
		String[] MpTemp=new String[3];
		try {
			while(rsMp.next()){
			    MpTemp=new String[3];
				MpTemp[0]=rsMp.getString("id");
				MpTemp[1]=rsMp.getString("rtu_id");
				MpTemp[2]=rsMp.getString("describe");
				mpList.add(MpTemp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			dao.closeRs(rsMp);
		}
		YffManDef user = CommFunc.getYffMan();
		
		String getRtuhql = "";
		String counthql  = "";
		
		if(user.getOrgId() == null){
			getRtuhql  = "select d.describe,m.residentId,m.commAddr,m.factory,m.madeNo,e.yffmeterType,e.caclType,e.feectrlType,e.tzVal,e.rtuId,e.mpId,mps.ti " +
					" from " + YDTable.TABLECLASS_MPPAYPARA + " as e,"  + YDTable.TABLECLASS_MPPARA + " as d," 
					+ YDTable.TABLECLASS_RTUPARA + " as a," + YDTable.TABLECLASS_METERPARA  +" as m ," + YDTable.TABLECLASS_CONSPARA +" as c ,"
					+ YDTable.TABLECLASS_METERSTSPARA + " as mps";
			counthql   = "select count(e.mpId) from " + YDTable.TABLECLASS_MPPAYPARA + " as e," 
			+ YDTable.TABLECLASS_MPPARA + " as d," + YDTable.TABLECLASS_RTUPARA + " as a," + YDTable.TABLECLASS_METERPARA  +" as m, " + YDTable.TABLECLASS_CONSPARA +" as c ," 
			+ YDTable.TABLECLASS_METERSTSPARA + " as mps";			
		}else{
			getRtuhql  = "select d.describe,m.residentId,m.commAddr,m.factory,m.madeNo,e.yffmeterType,e.caclType,e.feectrlType,e.tzVal,e.rtuId,e.mpId,mps.ti " +
							" from " + YDTable.TABLECLASS_MPPAYPARA + " as e,"  + YDTable.TABLECLASS_MPPARA + " as d," 
							+ YDTable.TABLECLASS_RTUPARA + " as a," + YDTable.TABLECLASS_METERPARA  +" as m ," + YDTable.TABLECLASS_CONSPARA +" as c ,"
							+ YDTable.TABLECLASS_METERSTSPARA + " as mps," + YDTable.TABLECLASS_Userrankbound +" as u ";
			counthql   = "select count(e.mpId) from " + YDTable.TABLECLASS_MPPAYPARA + " as e," 
							+ YDTable.TABLECLASS_MPPARA + " as d," + YDTable.TABLECLASS_RTUPARA + " as a," + YDTable.TABLECLASS_METERPARA  +" as m, " + YDTable.TABLECLASS_CONSPARA +" as c ," 
							+ YDTable.TABLECLASS_METERSTSPARA + " as mps," + YDTable.TABLECLASS_Userrankbound +" as u";				
		}

		
		List         list      = null;
		StringBuffer ret_buf   = new StringBuffer();
		int 		 page	   = Integer.parseInt(pageNo == null? "1" : pageNo);
		int 		 pagesize  = Integer.parseInt(pageSize == null? "10" : pageSize);
		
		if (CommBase.strtoi(yffState) == 0) {
			getRtuhql += " where m.useFlag = 1 and d.useFlag = 1 and  e.rtuId = d.rtuId and e.rtuId = m.rtuId and e.rtuId = a.id and e.mpId = m.mpId  and e.mpId = d.id and e.rtuId = mps.rtuId and e.mpId = mps.mpId";
			counthql  += " where m.useFlag = 1 and d.useFlag = 1 and  e.rtuId = d.rtuId and e.rtuId = m.rtuId and e.rtuId = a.id and e.mpId = m.mpId  and e.mpId = d.id and e.rtuId = mps.rtuId and e.mpId = mps.mpId";
		}else if(CommBase.strtoi(yffState) == 1) {
			getRtuhql += " where m.prepayflag = 1 and m.useFlag = 1 and d.useFlag = 1 and  e.rtuId = d.rtuId and e.rtuId = m.rtuId and e.rtuId = a.id and e.mpId = m.mpId  and e.mpId = d.id and e.rtuId = mps.rtuId and e.mpId = mps.mpId";
			counthql  += " where m.prepayflag = 1 and m.useFlag = 1 and d.useFlag = 1 and  e.rtuId = d.rtuId and e.rtuId = m.rtuId and e.rtuId = a.id and e.mpId = m.mpId  and e.mpId = d.id and e.rtuId = mps.rtuId and e.mpId = mps.mpId";
		}else if(CommBase.strtoi(yffState) == 2) {
			getRtuhql += " where m.prepayflag = 0 and m.useFlag = 1 and d.useFlag = 1 and  e.rtuId = d.rtuId and e.rtuId = m.rtuId and e.rtuId = a.id and e.mpId = m.mpId  and e.mpId = d.id and e.rtuId = mps.rtuId and e.mpId = mps.mpId";
			counthql  += " where m.prepayflag = 0 and m.useFlag = 1 and d.useFlag = 1 and  e.rtuId = d.rtuId and e.rtuId = m.rtuId and e.rtuId = a.id and e.mpId = m.mpId  and e.mpId = d.id and e.rtuId = mps.rtuId and e.mpId = mps.mpId";
		}
		
		getRtuhql += "  and a.consId = c.id ";
		counthql  += "  and a.consId = c.id ";
		
		if(user.getOrgId() != null){
			getRtuhql += " and u.consId = c.id and u.userId = " + user.getId();
			counthql  += " and u.consId = c.id and u.userId = " + user.getId();
		}
	
		if(result != null && !result.isEmpty()){
			describe = result.split(SDDef.SPLITCOMA)[0];
			rtu_id 	= result.split(SDDef.SPLITCOMA)[1];		
		}
		if(describe != null && !describe.isEmpty()){
			getRtuhql += " and d.describe like '%" + describe + "%'";
			counthql  += " and d.describe like '%" + describe + "%'";
		}
		if(!rtu_id.equals("-1")){
			getRtuhql += " and e.rtuId =" + rtu_id ;
			counthql  += " and e.rtuId =" + rtu_id ;
		}

		if(user.getRank() != 0){
			getRtuhql += " and c.orgId =" + user.getOrgId();
			counthql  += " and c.orgId =" + user.getOrgId();
 		}
		
		HibPage hib_page = new HibPage(page, pagesize);
		hib_page.setTotalrecords(counthql);
		
		if (pagesize == 0) {
			pagesize = hib_page.getTotalrecords();
			hib_page.setPagesize(pagesize);
		}
		if(hib_page.getTotalrecords()== 0) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		ret_buf.append(SDDef.JSONTOTAL + hib_page.getTotalrecords() + SDDef.JSONPAGEROWS);
		getRtuhql += " order by a.id, d.id";
		list = hib_page.getRecord(getRtuhql);
		
		int no = pagesize*(hib_page.getCurrentpage()-1)+1;
		Iterator it = list.iterator();
		
		Map<Integer, String> meter_type = Rd.getDict(Dict.DICTITEM_YFFMETERTYPE);
		Map<Integer, String> cacl_type = Rd.getDict(Dict.DICTITEM_FEETYPE);
		Map<Integer, String> feectrl_type = Rd.getDict(Dict.DICTITEM_PREPAYTYPE);
		Map<Integer, String> factory = Rd.getDict(Dict.DICTITEM_FACTORY);
		
		while(it.hasNext()){
			
			int i = 0;
			Object[] obj = (Object[])it.next();
			
			ret_buf.append(SDDef.JSONLBRACES);
			
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + (obj[10]+","+obj[9]) +  SDDef.JSONDATA); 	//i+1  +"," + CommBase.CheckString(obj[0])
			ret_buf.append(SDDef.JSONQUOT + (no++)										+  SDDef.JSONCCM); 		//i+1

			ret_buf.append(SDDef.JSONQUOT + "<a href='javascript:redoOnRowDblClicked(&quot;"+ obj[10]+","+obj[9] +"&quot;);'>" + obj[i++] + "</a>" + SDDef.JSONCCM);	//电表名称
			
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckString(DBOper.getDescribeById("rtupara", obj[9])) + SDDef.JSONCCM);  //所属终端
			
			ret_buf.append(SDDef.JSONQUOT +   CommBase.CheckString(getResDescribeById(obj[9], obj[i++])) +  SDDef.JSONCCM);//所属居民
			
			ret_buf.append(SDDef.JSONQUOT +  obj[i++] + SDDef.JSONCCM);  //电表地址
			
			String tmp = CommBase.CheckString(obj[i++]);	
			int itmp = CommBase.strtoi(tmp);
			ret_buf.append(SDDef.JSONQUOT + factory.get(itmp)  + SDDef.JSONCCM);//生产厂家
//			ret_buf.append(SDDef.JSONQUOT +  obj[i++] + SDDef.JSONCCM);  
			
			ret_buf.append(SDDef.JSONQUOT +  obj[i++] + SDDef.JSONCCM);	 //出厂编号
			
			tmp = CommBase.CheckString(obj[i++]);	//预付费类型4
			itmp = CommBase.strtoi(tmp);
			ret_buf.append(SDDef.JSONQUOT + meter_type.get(itmp)  + SDDef.JSONCCM);
			
			tmp = CommBase.CheckString(obj[i++]);		//计费方式5
			itmp = CommBase.strtoi(tmp);
			ret_buf.append(SDDef.JSONQUOT + cacl_type.get(itmp) + SDDef.JSONCCM);
			
			tmp = CommBase.CheckString(obj[i++]);		//费控方式6
			itmp = CommBase.strtoi(tmp);
			ret_buf.append(SDDef.JSONQUOT + feectrl_type.get(itmp)  + SDDef.JSONCCM);
			
			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckString(obj[i++]) + SDDef.JSONCCM);	 //透支金额

			ret_buf.append(SDDef.JSONQUOT +  CommBase.CheckString(obj[11]) + SDDef.JSONCCM);	 //Tariff Index
//			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(obj[i++])+ SDDef.JSONCCM);//写卡户号

			
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);
		}
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRBRBR);
		result = ret_buf.toString();
		return SDDef.SUCCESS;
	}
	
	//查找居民描述
	public String getResDescribeById(Object rtuId, Object ResId){
		
		String sql = "select describe from residentpara where rtu_id= " + rtuId 
						+ " and id = " + ResId;
		String desc = "";
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		try {
			//执行sql语句
			rs = j_dao.executeQuery(sql);
			//生成json字符串
			while(rs.next()){
				desc = rs.getString(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return SDDef.EMPTY;
		}finally{
			j_dao.closeRs(rs);
		} 
		return desc;
	}	
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : getyffparapl
	* <p>
	* <p>DESCRIPTION : 分页获取测量点费控记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@JSON(serialize = false)
	@SuppressWarnings("unchecked")
	public String getyffparapl() {
		/**
		 * 将表yffratepara的id和describe两个字段放到ArrayList<String[]>
		 * 将表mppara的                 id和rtu_id和describte放到ArrayList<String[]>
		 * 增加比较是否相等的方法    equalId();
		 */
		
		String yffSql  ="select id, describe from yffratepara ";
		String yffAPSql="select id, describe from yffalarmpara";
		JDBCDao dao=new JDBCDao();
		
		
		ResultSet rs=dao.executeQuery(yffSql);
		ResultSet rsYffAP=dao.executeQuery(yffAPSql);
		
		
		ArrayList<String[]> yffList=new ArrayList<String[]>();
		ArrayList<String[]> yffAPList=new ArrayList<String[]>();
		
		String[] temp=new String[2];
		try {
			while(rs.next()){
			    temp=new String[2];
				temp[0]=rs.getString("id");
				temp[1]=rs.getString("describe");
				yffList.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			dao.closeRs(rs);
		}
		
		String[] tempYffAP=new String[2];
		try {
			while(rsYffAP.next()){
				tempYffAP=new String[2];
				tempYffAP[0]=rsYffAP.getString("id");
				tempYffAP[1]=rsYffAP.getString("describe");
				yffAPList.add(tempYffAP);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			dao.closeRs(rsYffAP);
		}
		
		
		String getRtuhql  = "select e.rtuId,e.mpId,d.describe,a.describe as rtu_desc,e.yffmeterType,e.caclType,e.feectrlType, e.payType,e.feeprojId ,e.yffalarmId, e.keyVersion,e.ngloprotFlag from " + YDTable.TABLECLASS_MPPAYPARA + " as e,"  + YDTable.TABLECLASS_MPPARA + " as d," + YDTable.TABLECLASS_RTUPARA + " as a," + YDTable.TABLECLASS_METERPARA  +" as m" ;
		String counthql   = "select count(e.mpId) from " + YDTable.TABLECLASS_MPPAYPARA + " as e," + YDTable.TABLECLASS_MPPARA + " as d," + YDTable.TABLECLASS_RTUPARA + " as a," + YDTable.TABLECLASS_METERPARA  +" as m" ;
		
		List         list      = null;
		StringBuffer ret_buf   = new StringBuffer();
		int 		 page	   = Integer.parseInt(pageNo == null? "1" : pageNo);
		int 		 pagesize  = Integer.parseInt(pageSize == null? "10" : pageSize);
		
		String describe   = null;
		String idFromTree = null;
		String param2     = null;
		
		
		if(result != null && !result.isEmpty()) {
			JSONObject jsonObj  = JSONObject.fromObject(result);
			describe   = jsonObj.getString("describe");
			param2     = jsonObj.getString("id");
			int pos = param2.indexOf("_");
			idFromTree = param2.substring(pos + 1);
			
			if (param2.equals(SDDef.GLOBAL_KE2)) {
				getRtuhql += "," + YDTable.TABLECLASS_CONSPARA + " as b, " + YDTable.TABLECLASS_ORGPARA + " as c where a.consId=b.id and b.appType="+SDDef.APPTYPE_JC+" and b.orgId=c.id " ;
				counthql  += "," + YDTable.TABLECLASS_CONSPARA + " as b, " + YDTable.TABLECLASS_ORGPARA + " as c where a.consId=b.id and b.appType="+SDDef.APPTYPE_JC+" and b.orgId=c.id " ;
				
			} else if (param2.startsWith(YDTable.TABLECLASS_ORGPARA)) {
				
				getRtuhql += "," + YDTable.TABLECLASS_CONSPARA + " as b, " + YDTable.TABLECLASS_ORGPARA + " as c where a.consId=b.id and b.appType="+SDDef.APPTYPE_JC+" and b.orgId=c.id and c.id=" + idFromTree ;
				counthql  += "," + YDTable.TABLECLASS_CONSPARA + " as b, " + YDTable.TABLECLASS_ORGPARA + " as c where a.consId=b.id and b.appType="+SDDef.APPTYPE_JC+" and b.orgId=c.id and c.id=" + idFromTree ;
				
			} else if (param2.startsWith(YDTable.TABLECLASS_LINEFZMAN)) {
				
				getRtuhql += "," + YDTable.TABLECLASS_CONSPARA + " as b where a.consId=b.id and b.appType="+SDDef.APPTYPE_JC+" and  b.lineFzManId=" + idFromTree ;
				counthql  += "," + YDTable.TABLECLASS_CONSPARA + " as b where a.consId=b.id and b.appType="+SDDef.APPTYPE_JC+" and  b.lineFzManId=" + idFromTree ;
				
			} else if (param2.startsWith(YDTable.TABLECLASS_CONSPARA)){
				
				getRtuhql += "," + YDTable.TABLECLASS_CONSPARA + " as b where a.consId=b.id and b.id=" + idFromTree ;
				counthql  += "," + YDTable.TABLECLASS_CONSPARA + " as b where a.consId=b.id and b.id=" + idFromTree ;
				
			} 
			else {
				getRtuhql += " where a.id=" + idFromTree ;
				counthql  += " where a.id=" + idFromTree ;
			}
		}
		
		getRtuhql += " and m.prepayflag = 1 and m.useFlag = 1 and d.useFlag = 1 and  e.rtuId = d.rtuId and e.rtuId = m.rtuId and e.rtuId = a.id and e.mpId = m.mpId  and e.mpId = d.id ";
		counthql  += " and m.prepayflag = 1 and m.useFlag = 1 and d.useFlag = 1 and  e.rtuId = d.rtuId and e.rtuId = m.rtuId and e.rtuId = a.id and e.mpId = m.mpId  and e.mpId = d.id ";
	
		if(describe != null && !describe.isEmpty()){
			getRtuhql += " and d.describe like '%" + describe + "%'";
			counthql  += " and d.describe like '%" + describe + "%'";
		}
		
		HibPage hib_page = new HibPage(page, pagesize);
		hib_page.setTotalrecords(counthql);
		
		if (pagesize == 0) {
			pagesize = hib_page.getTotalrecords();
			hib_page.setPagesize(pagesize);
		}
		if(hib_page.getTotalrecords()== 0) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		ret_buf.append(SDDef.JSONTOTAL + hib_page.getTotalrecords() + SDDef.JSONPAGEROWS);
		getRtuhql += " order by a.id";
		list = hib_page.getRecord(getRtuhql);
		
		int no = pagesize*(hib_page.getCurrentpage()-1)+1;
		Iterator it = list.iterator();
		
		Map<Integer, String> meter_type = Rd.getDict(Dict.DICTITEM_YFFMETERTYPE);
		Map<Integer, String> cacl_type = Rd.getDict(Dict.DICTITEM_FEETYPE);
		Map<Integer, String> feectrl_type = Rd.getDict(Dict.DICTITEM_PREPAYTYPE);
		Map<Integer, String> pay_type = Rd.getDict(Dict.DICTITEM_PAYTYPE);
		Map<Integer, String> ngloprot_flag = Rd.getDict(Dict.DICTITEM_YESFLAG);
		
		while(it.hasNext()){
			
			int i = 0;
			Object[] obj = (Object[])it.next();
			
			ret_buf.append(SDDef.JSONLBRACES);
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + (obj[0]+"_"+obj[1]) + SDDef.JSONDATA); 
			ret_buf.append(SDDef.JSONQUOT + (no++)          							+ SDDef.JSONCCM);

			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(obj[3]) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(obj[2]) 				+ SDDef.JSONCCM);
			i = 4;
			
			String tmp = CommBase.CheckString(obj[i++]);
			int itmp = CommBase.strtoi(tmp);
			ret_buf.append(SDDef.JSONQUOT + meter_type.get(itmp)  + SDDef.JSONCCM);
			
			tmp = CommBase.CheckString(obj[i++]);
			itmp = CommBase.strtoi(tmp);
			ret_buf.append(SDDef.JSONQUOT + cacl_type.get(itmp) + SDDef.JSONCCM);
			
			tmp = CommBase.CheckString(obj[i++]);
			itmp = CommBase.strtoi(tmp);
			ret_buf.append(SDDef.JSONQUOT + feectrl_type.get(itmp)  + SDDef.JSONCCM);
			
			tmp = CommBase.CheckString(obj[i++]);
			itmp = CommBase.strtoi(tmp);
			ret_buf.append(SDDef.JSONQUOT + pay_type.get(itmp)  + SDDef.JSONCCM);
					
			ret_buf.append(SDDef.JSONQUOT +  equalRtuAndYff(obj[i++],yffList  ) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT +  equalRtuAndYff(obj[i++],yffAPList) + SDDef.JSONCCM);
			
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(obj[i++])              + SDDef.JSONCCM);
			
			tmp = CommBase.CheckString(obj[i++]);
			itmp = CommBase.strtoi(tmp);
			ret_buf.append(SDDef.JSONQUOT + ngloprot_flag.get(itmp) + SDDef.JSONCCM);
			
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);
		}
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRBRBR);
		result = ret_buf.toString();
		return SDDef.SUCCESS;
	}
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : plAddOrEdit
	* <p>
	* <p>DESCRIPTION : 批量添加或修改费控参数记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	
	public String plAddOrEdit(){
		if(pl == null || pl.isEmpty()){
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		HibDao  hib_dao = new HibDao();
		String rtu_mp[] = pl.split(",");
		String con = "";
		boolean flag = true;
		for (int i = 0; i < rtu_mp.length; i++) {
			con = " rtuId=" + rtu_mp[i].split("_")[0] + " and mpId=" + rtu_mp[i].split("_")[1];
			String hql = "update " + YDTable.TABLECLASS_MPPAYPARA + " set " + result + " where " + con;
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
	* <p>NAME        : getMeterParaById
	* <p>
	* <p>DESCRIPTION : 通过ID获取电表档案记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@JSON(serialize = false)
	@SuppressWarnings("unchecked")
	public String getMeterParaById() throws Exception
	{
		if(result == null) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		
		StringBuffer ret_buf  = new StringBuffer();
		HibDao       hib_dao  = new HibDao();//--------------------------------------------------------
//		String hql = "select a.id,b.mpId,a.describe,a.useFlag,a.mpType,a.consType,b.commAddr,b.metermodelId,a.wiringMode,b.madeNo,b.assetNo,b.factory,b.status,b.meterId,b.residentId from " + YDTable.TABLECLASS_MPPARA + " as a ,"+ YDTable.TABLECLASS_METERPARA + " as b where a.id=b.mpId and a.id=" + result.split(SDDef.SPLITCOMA)[0] +" and a.rtuId=b.rtuId and a.rtuId="+result.split(SDDef.SPLITCOMA)[1];
		String hql = "select mps.ti,a.ptRatio,a.ctRatio,b.mpId,a.describe,a.mpType,b.commAddr,b.commPwd,b.madeNo,b.assetNo,b.factory,b.prepayflag,b.residentId,a.reserve1,c.describe,a.ctDenominator,a.rp,a.mi,a.bdFactor,a.vfactor from " + YDTable.TABLECLASS_MPPARA + " as a ," + YDTable.TABLECLASS_METERPARA + " as b, " +YDTable.TABLECLASS_METERSTSPARA + " as mps," +YDTable.TABLECLASS_RTUPARA + " as c where a.id=b.mpId and a.id=" + result.split(SDDef.SPLITCOMA)[0] +" and a.rtuId=b.rtuId  and a.rtuId= mps.rtuId and a.id=mps.mpId and c.id = a.rtuId and a.rtuId="+result.split(SDDef.SPLITCOMA)[1];
		List   list = hib_dao.loadAll(hql);
		Object[] object = (Object[])list.get(0);
		ret_buf.append(SDDef.JSONSELDATA);
		for(int i = 0; i<object.length; i ++) {
			ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(object[i]) + SDDef.JSONQUOT  + SDDef.JSONCOMA);
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();
		
		return SDDef.SUCCESS;
	}
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : getResValueText
	* <p>
	* <p>DESCRIPTION : 获得居民的名称和id
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/	
	@JSON(serialize = false)
	public String getResValueText(){
		String empty = "\"\"";
		String sql = "select id,describe from ydparaben.dbo.residentpara where rtu_id = " + result;
		
		StringBuffer ret_buf = new StringBuffer();
		ret_buf.append("[");
		
		JDBCDao j_dao = new JDBCDao();
		ResultSet rs = null;
		boolean num_flag = false;
		try {
			//执行sql语句
			rs = j_dao.executeQuery(sql);
			//生成json字符串
			while(rs.next()){
				if(!num_flag)num_flag = true;
				ret_buf.append(SDDef.JSONLBRACES);
				ret_buf.append(SDDef.JSONQUOT + "value" + SDDef.JSONQACQ + rs.getString(1)+ SDDef.JSONCCM); 
				ret_buf.append(SDDef.JSONQUOT + "text" 	+ SDDef.JSONQACQ + CommBase.CheckString(rs.getString(2))+ SDDef.JSONQRBCM);
			}
			ret_buf.deleteCharAt(ret_buf.length() - 1);
			ret_buf.append("]");
		} catch (Exception e) {
			e.printStackTrace();
			return empty;
		}finally{
			j_dao.closeRs(rs);
		}
		if(!num_flag)return empty;
		
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

	public MpPayPara getMppaypara() {
		return mppaypara;
	}

	public void setMppaypara(MpPayPara mppaypara) {
		this.mppaypara = mppaypara;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getYffState() {
		return yffState;
	}

	public void setYffState(String yffState) {
		this.yffState = yffState;
	}

	public void setPl(String pl) {
		this.pl = pl;
	}

	public String getPl() {
		return pl;
	}

	public MpPara getMppara() {
		return mppara;
	}

	public void setMppara(MpPara mppara) {
		this.mppara = mppara;
	}

	public MeterPara getMeterpara() {
		return meterpara;
	}

	public void setMeterpara(MeterPara meterpara) {
		this.meterpara = meterpara;
	}

	public MpPayAlmState getMppayalmstate() {
		return mppayalmstate;
	}

	public void setMppayalmstate(MpPayAlmState mppayalmstate) {
		this.mppayalmstate = mppayalmstate;
	}
	
	public MeterStsPara getMeterstspara() {
		return meterstspara;
	}

	public void setMeterstspara(MeterStsPara meterstspara) {
		this.meterstspara = meterstspara;
	}
}
