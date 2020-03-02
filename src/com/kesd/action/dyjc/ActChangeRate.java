package com.kesd.action.dyjc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.libweb.dao.HibDao;
import com.libweb.dao.JDBCDao;

import com.kesd.common.SDDef;
import com.kesd.common.YDTable;
import com.kesd.dbpara.YffRatePara;
import com.kesd.util.Rd;
import com.opensymphony.xwork2.ActionSupport;


public class ActChangeRate extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6268085290910467823L;
	
	private String 		result;
	private String		field;		//所需查询的数据库字段
	private String 		rtu_id;
	private String 		mp_id;
	
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>NAME        : getRateList	
	* <p>DESCRIPTION : 取得费率方案的下拉列表
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	*-----------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	@JSON(serialize = false)
	public String getRateList(){
		StringBuffer 	ret_buf		= new StringBuffer();
	
		ret_buf.append(SDDef.JSONROWSTITLE);
		HibDao hib_dao = new HibDao();
		String hql = "from " + YDTable.TABLECLASS_YFFRATEPARA;
		
		List  list = hib_dao.loadAll(hql);
		Iterator it = list.iterator();
		for(int i=0; i<list.size();i++){
			YffRatePara rate = (YffRatePara)it.next();
			ret_buf.append(SDDef.JSONLBRACES);
			ret_buf.append(SDDef.JSONQUOT + "value" + SDDef.JSONQACQ + rate.getId() + SDDef.JSONCCM);//ID
			ret_buf.append(SDDef.JSONQUOT + "text" + SDDef.JSONQACQ + rate.getDescribe() + SDDef.JSONCCM);//描述
			ret_buf.append(SDDef.JSONQUOT + "feeType" + SDDef.JSONQACQ + rate.getFeeType()+ SDDef.JSONCCM); //费率类型
			ret_buf.append(SDDef.JSONQUOT + "val_rateZ" + SDDef.JSONQACQ + rate.getRatedZ() + SDDef.JSONCCM);//费率总
			ret_buf.append(SDDef.JSONQUOT + "val_rateJ" + SDDef.JSONQACQ + rate.getRatefJ() + SDDef.JSONCCM);//费率尖
			ret_buf.append(SDDef.JSONQUOT + "val_rateF" + SDDef.JSONQACQ + rate.getRatefF() + SDDef.JSONCCM);//费率峰
			ret_buf.append(SDDef.JSONQUOT + "val_rateP" + SDDef.JSONQACQ + rate.getRatefP() + SDDef.JSONCCM);//费率平
			ret_buf.append(SDDef.JSONQUOT + "val_rateG" + SDDef.JSONQACQ + rate.getRatefG() + SDDef.JSONCCM);//费率谷
			ret_buf.append(SDDef.JSONQUOT + "val_moneyLimit" + SDDef.JSONQACQ + rate.getMoneyLimit() + SDDef.JSONCCM);//囤积限制
			
			//20131021添加  begin
			//新规约表中可以添加混合费率、阶梯、混合阶梯电价
			//混合费率
			ret_buf.append(SDDef.JSONQUOT + "rateh_1" + SDDef.JSONQACQ + rate.getRateh1() + SDDef.JSONCCM);//混合费率1
			ret_buf.append(SDDef.JSONQUOT + "rateh_2" + SDDef.JSONQACQ + rate.getRateh2() + SDDef.JSONCCM);//混合费率2
			ret_buf.append(SDDef.JSONQUOT + "rateh_3" + SDDef.JSONQACQ + rate.getRateh3() + SDDef.JSONCCM);//混合费率3
			ret_buf.append(SDDef.JSONQUOT + "rateh_4" + SDDef.JSONQACQ + rate.getRateh4() + SDDef.JSONCCM);//混合费率4
			ret_buf.append(SDDef.JSONQUOT + "rateh_bl1" + SDDef.JSONQACQ + rate.getRatehBl1() + SDDef.JSONCCM);//混合比例1
			ret_buf.append(SDDef.JSONQUOT + "rateh_bl2" + SDDef.JSONQACQ + rate.getRatehBl2() + SDDef.JSONCCM);//混合比例2
			ret_buf.append(SDDef.JSONQUOT + "rateh_bl3" + SDDef.JSONQACQ + rate.getRatehBl3() + SDDef.JSONCCM);//混合比例3
			ret_buf.append(SDDef.JSONQUOT + "rateh_bl4" + SDDef.JSONQACQ + rate.getRatehBl4() + SDDef.JSONCCM);//混合比例4
			
			//阶梯电价
			//dubr 添加阶梯电价类型
			ret_buf.append(SDDef.JSONQUOT + "ratejType" + SDDef.JSONQACQ + rate.getRatejType() + SDDef.JSONCCM);//*阶梯电价类型 0 年度方案, 1月度方案  2月度峰谷
			ret_buf.append(SDDef.JSONQUOT + "meterfeeType" + SDDef.JSONQACQ + rate.getMeterfeeType() + SDDef.JSONCCM);/*电表费率类型  0 单费率 1 复费率   3阶梯费率 */
			ret_buf.append(SDDef.JSONQUOT + "meterfeeR" + SDDef.JSONQACQ + rate.getMeterfeeR() + SDDef.JSONCCM);/*电表执行电价*/
			//end
			
			ret_buf.append(SDDef.JSONQUOT + "ratej_r1" + SDDef.JSONQACQ + rate.getRatejR1() + SDDef.JSONCCM);//阶梯费率1
			ret_buf.append(SDDef.JSONQUOT + "ratej_r2" + SDDef.JSONQACQ + rate.getRatejR2() + SDDef.JSONCCM);//阶梯费率2
			ret_buf.append(SDDef.JSONQUOT + "ratej_r3" + SDDef.JSONQACQ + rate.getRatejR3() + SDDef.JSONCCM);//阶梯费率3
			ret_buf.append(SDDef.JSONQUOT + "ratej_r4" + SDDef.JSONQACQ + rate.getRatejR4() + SDDef.JSONCCM);//阶梯费率4
			
			ret_buf.append(SDDef.JSONQUOT + "ratej_td1" + SDDef.JSONQACQ + rate.getRatejTd1() + SDDef.JSONCCM);//阶梯梯度值1
			ret_buf.append(SDDef.JSONQUOT + "ratej_td2" + SDDef.JSONQACQ + rate.getRatejTd2() + SDDef.JSONCCM);//阶梯梯度值2
			ret_buf.append(SDDef.JSONQUOT + "ratej_td3" + SDDef.JSONQACQ + rate.getRatejTd3() + SDDef.JSONCCM);//阶梯梯度值3
			
			//混合阶梯电价
			//dubr 添加/*阶梯电价类型*/ 	/*0 年度方案, 1月度方案*/
			ret_buf.append(SDDef.JSONQUOT + "ratehjType" + SDDef.JSONQACQ + rate.getRatehjType() + SDDef.JSONCCM);/*阶梯电价类型 0 年度方案, 1月度方案*/
			ret_buf.append(SDDef.JSONQUOT + "meterfeehjType" + SDDef.JSONQACQ + rate.getMeterfeehjType() + SDDef.JSONCCM);/*电表费率类型  0单费率 1复费率  3阶梯费率*/
			ret_buf.append(SDDef.JSONQUOT + "meterfeehjR" + SDDef.JSONQACQ + rate.getMeterfeehjR() + SDDef.JSONCCM);/*电表执行电价*/
			//end
			
			ret_buf.append(SDDef.JSONQUOT + "ratehj_hr1_r1" + SDDef.JSONQACQ + rate.getRatehjHr1R1() + SDDef.JSONCCM);//第1比例电价-阶梯费率1
			ret_buf.append(SDDef.JSONQUOT + "ratehj_hr1_r2" + SDDef.JSONQACQ + rate.getRatehjHr1R2() + SDDef.JSONCCM);//第1比例电价-阶梯费率2
			ret_buf.append(SDDef.JSONQUOT + "ratehj_hr1_r3" + SDDef.JSONQACQ + rate.getRatehjHr1R3() + SDDef.JSONCCM);//第1比例电价-阶梯费率3
			ret_buf.append(SDDef.JSONQUOT + "ratehj_hr1_r4" + SDDef.JSONQACQ + rate.getRatehjHr1R4() + SDDef.JSONCCM);//第1比例电价-阶梯费率4
			
			ret_buf.append(SDDef.JSONQUOT + "ratehj_hr1_td1" + SDDef.JSONQACQ + rate.getRatehjHr1Td1() + SDDef.JSONCCM);//第1比例电价-阶梯梯度值1
			ret_buf.append(SDDef.JSONQUOT + "ratehj_hr1_td2" + SDDef.JSONQACQ + rate.getRatehjHr1Td2() + SDDef.JSONCCM);//第1比例电价-阶梯梯度值2
			ret_buf.append(SDDef.JSONQUOT + "ratehj_hr1_td3" + SDDef.JSONQACQ + rate.getRatehjHr1Td3() + SDDef.JSONCCM);//第1比例电价-阶梯梯度值3
			
			ret_buf.append(SDDef.JSONQUOT + "ratehj_hr2" + SDDef.JSONQACQ + rate.getRatehjHr2() + SDDef.JSONCCM);//第2比例电价
			ret_buf.append(SDDef.JSONQUOT + "ratehj_hr3" + SDDef.JSONQACQ + rate.getRatehjHr3() + SDDef.JSONCCM);//第3比例电价
			
			ret_buf.append(SDDef.JSONQUOT + "ratehj_bl1" + SDDef.JSONQACQ + rate.getRatehjBl1() + SDDef.JSONCCM);//混合比例1
			ret_buf.append(SDDef.JSONQUOT + "ratehj_bl2" + SDDef.JSONQACQ + rate.getRatehjBl2() + SDDef.JSONCCM);//混合比例2
			ret_buf.append(SDDef.JSONQUOT + "ratehj_bl3" + SDDef.JSONQACQ + rate.getRatehjBl3() + SDDef.JSONCCM);//混合比例3
			//end
			ret_buf.append(SDDef.JSONQUOT + "desc" 	+ SDDef.JSONQACQ + Rd.getYffRateDesc(rate) + SDDef.JSONQRBCM);//详细信息文字描述
		}
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result=ret_buf.toString();
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

	public String getRtu_id() {
		return rtu_id;
	}

	public void setRtu_id(String rtuId) {
		rtu_id = rtuId;
	}

	public String getMp_id() {
		return mp_id;
	}

	public void setMp_id(String mpId) {
		mp_id = mpId;
	}

}
