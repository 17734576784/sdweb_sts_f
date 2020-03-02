package com.kesd.action.np;

import java.util.Iterator;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.libweb.dao.HibDao;

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
	
}
