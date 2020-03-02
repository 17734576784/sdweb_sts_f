/**
 *  dbr 2016-11-10上午08:45:17
 */
package com.sts;

import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.util.Log4jUtil;
import com.libweb.dao.JDBCDao;

/**
 * @author dbr
 *
 */
public class ReadRechargeInfo {
	
	
	public void readRechargeRecord(){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select sts.key_type,sts.dkga,sts.vk1,org.r_org_no,msts.ken,");
		sql.append(" res.cons_no,res.id as res_id, res.describe, res.phone, res.address,");
		sql.append(" mpstate.cus_state, meter.comm_addr, meter.factory, mppay.yffmeter_type,"); 
		sql.append(" cons.tel_no3,mppay.cacl_type, mppay.feectrl_type, mppay.pay_type, ");
		sql.append(" mppay.feeproj_id,mppay.key_version, mppay.tz_val, mpstate.reserve1 as seqNo,");
		sql.append(" mpstate.reserve2 as keyNo, mpstate.buy_times, mppay.prot_st as friend_s,");
		sql.append(" mppay.prot_ed as friend_e, mp.reserve1 as friendDate,mp.rp as fmaximum_power,");
		sql.append(" mp.mi as gmaximum_power,mp.bd_factor as power_start,mp.v_factor as power_end,mp.ct_denominator as power_date,");
		sql.append(" rtu.id as rtuId, mp.id mpId ");
		sql.append(" from ydparaben.dbo.orgpara as org,ydparaben.dbo.conspara as cons, ydparaben.dbo.rtupara as rtu, ");
		sql.append(" ydparaben.dbo.mppara as mp, ydparaben.dbo.residentpara as res, ydparaben.dbo.meterpara as meter,");
		sql.append(" ydparaben.dbo.mppay_para as mppay, ydparaben.dbo.mppay_state as mpstate , ");
		sql.append(" ydparaben.dbo.org_stspara as sts, ydparaben.dbo.meter_stspara as msts  ");
		sql.append(" where  sts.org_id = org.id  and  cons.org_id = org.id and  cons.id = rtu.cons_id"); 
		sql.append(" and rtu.app_type = 3 and rtu.id = mp.rtu_id and rtu.id = res.rtu_id and rtu.id = meter.rtu_id"); 
		sql.append(" and  msts.rtu_id = rtu.id and msts.mp_id = mp.id and  rtu.id = mppay.rtu_id and mp.id = meter.mp_id");
		sql.append(" and mp.id = mppay.mp_id and res.id = meter.resident_id and rtu.id = mpstate.rtu_id and mp.id = mpstate.mp_id");
//		sql.append(" and rtu.id = 30000001 and mp.id = 9");
		
		try {
			JDBCDao jdbcDao = new JDBCDao();
			List<Map<String, Object>> list = jdbcDao.result(sql.toString());
			if (list != null) {
				for (Map<String, Object> map : list) {
					
					JSONObject json = new JSONObject();
					json.put("KT", CommFunc.CheckString(map.get("key_type")));
					json.put("SGC", CommFunc.CheckString(map.get("r_org_no")));
					json.put("TI", 1);
					json.put("KRN", CommFunc.objectToString(map.get("key_version")));
					json.put("IIN", "600727");
					
					String commAddr=map.get("comm_addr").toString();
					int length =map.get("comm_addr").toString().length(); 
					switch(length){
						case 13:
							commAddr = commAddr.substring(0,length-1);
							break;
						case 15:
							commAddr = commAddr.substring(2,14);
							break;
						default:
							commAddr = length>12?commAddr.substring(length-12):commAddr;
						break;
					}	
					json.put("DRN", CommFunc.CheckString(commAddr));
					json.put("DKGA", CommFunc.CheckString(map.get("dkga")));
					String rtuId = CommFunc.CheckString(map.get("rtuId"));
					String mpId = CommFunc.CheckString(map.get("mpId"));
					
					json.put("rtuId", rtuId);
					json.put("mpId", mpId);
					json.put("KEN", CommFunc.CheckString(map.get("ken")));
					json.put("all_money", 100); // 写死
					json.put("alldl", 100); // 写死 付费率 算电量

					json.put("res_id", CommFunc.CheckString(map.get("res_id")));
					json.put("keyNo", CommFunc.CheckString(map.get("keyNo")));
					json.put("seqNo", CommFunc.CheckString(map.get("seqNo")));
					json.put("cus_state", CommFunc.CheckString(map.get("cus_state")));
					json.put("pay_type", CommFunc.CheckString(map.get("pay_type")));
					json.put("buy_times", CommFunc.CheckString(map.get("buy_times")));
					json.put("meterId", CommFunc.CheckString(mpId));
					json.put("vk1", CommFunc.CheckString(map.get("vk1")));
					
					//销户
					json.put("showvalue", 0);
					json.put("rnd", CommFunc.CheckString(map.get("buy_times")));
					
					int operType = 11;
					GenerateToken generateToken = new GenerateToken();
					switch(operType){
						case SDDef.YFF_OPTYPE_ADDRES:
							generateToken.GenerateOpenAccountToken(json);
							break;
						case SDDef.YFF_OPTYPE_PAY: 
							generateToken.GeneratePayToken(json);
							break;
						case SDDef.YFF_OPTYPE_DESTORY:
							generateToken.GenerateDestoryAccountToken(json);
							break;
					}
				}
			}
		} catch (Exception e) {
			Log4jUtil.getLogger().logger.info("读取缴费记录异常");
		}
	}
}
