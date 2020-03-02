package com.kesd.dbpara;

import java.io.Serializable;

public class MpPayState implements Serializable{

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	private static final long serialVersionUID = -6857111136358220117L;
	private Integer 	rtuId;			/*终端编号*/
	private Short		mpId;			/*总加组编号0-7，对应规约1-8*/
	private Byte		cusState;		/*用户状态 0 初始态, 1 正常态, 10 销户态*/	
	private Byte		opType;			/*本次操作类型 0 初始态, 1 开户, 2 缴费, 3 补卡, 4 补写卡, 5 冲正, 6 换表, 7 换费率 10 销户 */	
	private Integer		opDate;			/*本次操作日期*/	
	private	Integer		opTime;			/*本次操作时间*/
	
	private Double 		payMoney;		/*缴费金额*/	
	private Double 		othjsMoney;		/*结算金额(其它系统)*/	
	private Double 		zbMoney;		/*追补金额*/	
	private Double 		allMoney;		/*总金额*/
	
	private Double 		shutdownVal;	/*断电值 金额计费时为:断电金额 */
	private Double 		shutdownVal2;	/*断电值 金额计费时为:断电金额  本地模拟电表 */
	
	private Double 		jsbdZyz;		/*结算总表底*/
	private Double 		jsbdZyj;		/*结算尖表底*/
	private Double 		jsbdZyf;		/*结算峰表底*/
	private Double 		jsbdZyp;		/*结算平表底*/
	private Double 		jsbdZyg;		/*结算谷表底*/

	private Double 		jsbd1Zyz;		/*动力关联1-结算总表底*/
	private Double 		jsbd1Zyj;		/*动力关联1-结算尖表底*/
	private Double 		jsbd1Zyf;		/*动力关联1-结算峰表底*/
	private Double 		jsbd1Zyp;		/*动力关联1-结算平表底*/
	private Double 		jsbd1Zyg;		/*动力关联1-结算谷表底*/

	private Double 		jsbd2Zyz;		/*动力关联2-结算总表底*/
	private Double 		jsbd2Zyj;		/*动力关联2-结算尖表底*/
	private Double 		jsbd2Zyf;		/*动力关联2-结算峰表底*/
	private Double 		jsbd2Zyp;		/*动力关联2-结算平表底*/
	private Double 		jsbd2Zyg;		/*动力关联2-结算谷表底*/

	private Integer		jsbdYmd;		/*结算时间*/
	private Integer		calcMdhmi;		/*算费时间-MMDDHHMI*/
	private Integer		calcBdymd;		/*算费表底时间-YYYYMMDD*/	
	
	private Double 		calcZyz;		/*算费时总表底*/
	private Double 		calcZyj;		/*算费时尖表底*/
	private Double 		calcZyf;		/*算费时峰表底*/
	private Double 		calcZyp;		/*算费时平表底*/
	private Double 		calcZyg;		/*算费时谷表底*/
	
	private Double 		calc1Zyz;		/*动力关联1-算费时总表底*/
	private Double 		calc1Zyj;		/*动力关联1-算费时尖表底*/
	private Double 		calc1Zyf;		/*动力关联1-算费时峰表底*/
	private Double 		calc1Zyp;		/*动力关联1-算费时平表底*/
	private Double 		calc1Zyg;		/*动力关联1-算费时谷表底*/
	
	private Double 		calc2Zyz;		/*动力关联2-算费时总表底*/
	private Double 		calc2Zyj;		/*动力关联2-算费时尖表底*/
	private Double 		calc2Zyf;		/*动力关联2-算费时峰表底*/
	private Double 		calc2Zyp;		/*动力关联2-算费时平表底*/
	private Double 		calc2Zyg;		/*动力关联2-算费时谷表底*/		
	
	private Double 		nowRemain;		/*当前剩余*/
	private Double 		nowRemain2;		/*当前剩余 本地模拟电表*/
	
	private Double 		bjBd;			/*报警门限*/
	private Double 		tzBd;			/*跳闸门限*/

	private Byte		csAl1State;		/*报警1状态  0:正常状态 1:报警状态*/
	private Byte		csAl2State;		/*报警2状态  0:正常状态 1:报警状态*/
	private Byte		csFhzState;		/*分合闸状态 0:分闸状态 1:合闸状态*/

	private	Integer		al1Mdhmi;		/*报警1状态改变时间-MMDDHHMI*/
	private	Integer		al2Mdhmi;		/*报警2状态改变时间-MMDDHHMI*/	
	private	Integer		fhzMdhmi;		/*分合闸状态改变时间-MMDDHHMI*/

	private Double 		fzZyz;			/*分闸时总表底*/
	private Double 		fz1Zyz;			/*动力关联1-分闸时总表底*/
	private Double 		fz2Zyz;			/*动力关联2-分闸时总表底*/

	private	Integer		ycFlag1;		/*异常标志1, 参数错误 */
	private	Integer		ycFlag2;		/*异常标志2(按位标志), 数据错误 0位:分闸后表字继续走 1位:表底飞走 2位:表底倒转 3位:长时间无数据 4位:长时间不缴费*/
	private	Integer		ycFlag3;		/*异常标志3, 备用 */

	private	Integer		hbDate;			/*上次换表日期*/
	private	Integer		hbTime;			/*上次换表时间*/	
	
	private	Integer		khDate;			/*开户日期-YYYYMMDD*/
	private	Integer		xhDate;			/*销户日期-YYYYMMDD*/

	private Double 		totalGdz;		/*累计购电值*/
	private Integer		buyTimes;		/*购电次数*/
	
	private Double 		jtTotalZbdl;	/*阶梯追补累计用电量*/
	private Double 		jtTotalDl;	/*阶梯累计用电量*/
	private	Integer		jtResetYmd;	/*阶梯上次自动切换日期*/
	private Double 		fxdfIallMoney;/*发行电费当月缴费总金额*/
	private Double 		fxdfRemain;	/*发行电费后剩余金额  		fxdf_after_remain*/
	private Double 		fxdfRemain2;	/*发行电费后剩余金额  		fxdf_after_remain 本地模拟电表*/
	private	Integer		fxdfYm;		/*发行电费数据日期YYYYMM*/
	private	Integer		fxdfDataYmd;	/*发行电费数据日期-YYYYMMDD*/
	private	Integer		fxdfCalcMdhmi;/*发行电费算费时间-MMDDHHMI*/
	private	Integer		jsBcYmd;		/*结算补差日期YYYYMMDD*/
	
	private Integer		reserve1;
		
	public Integer getRtuId() {
		return rtuId;
	}
	public void setRtuId(Integer rtuId) {
		this.rtuId = rtuId;
	}
	public Short getMpId() {
		return mpId;
	}
	public void setMpId(Short mpId) {
		this.mpId = mpId;
	}

	public Byte getCusState() {
		return cusState;
	}
	public void setCusState(Byte cusState) {
		this.cusState = cusState;
	}
	
	public Double getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}
	
	public Double getZbMoney() {
		return zbMoney;
	}
	public void setZbMoney(Double zbMoney) {
		this.zbMoney = zbMoney;
	}
	
	public Byte getOpType() {
		return opType;
	}
	public void setOpType(Byte opType) {
		this.opType = opType;
	}
	public Integer getOpDate() {
		return opDate;
	}
	public void setOpDate(Integer opDate) {
		this.opDate = opDate;
	}
	public Integer getOpTime() {
		return opTime;
	}
	public void setOpTime(Integer opTime) {
		this.opTime = opTime;
	}
	public Integer getReserve1() {
		return reserve1;
	}
	public void setReserve1(Integer reserve1) {
		this.reserve1 = reserve1;
	}
	public Double getOthjsMoney() {
		return othjsMoney;
	}
	public void setOthjsMoney(Double othjsMoney) {
		this.othjsMoney = othjsMoney;
	}
	public Double getAllMoney() {
		return allMoney;
	}
	public void setAllMoney(Double allMoney) {
		this.allMoney = allMoney;
	}
	public Double getShutdownVal() {
		return shutdownVal;
	}
	public void setShutdownVal(Double shutdownVal) {
		this.shutdownVal = shutdownVal;
	}
	public Double getJsbdZyz() {
		return jsbdZyz;
	}
	public void setJsbdZyz(Double jsbdZyz) {
		this.jsbdZyz = jsbdZyz;
	}
	public Double getJsbdZyj() {
		return jsbdZyj;
	}
	public void setJsbdZyj(Double jsbdZyj) {
		this.jsbdZyj = jsbdZyj;
	}
	public Double getJsbdZyf() {
		return jsbdZyf;
	}
	public void setJsbdZyf(Double jsbdZyf) {
		this.jsbdZyf = jsbdZyf;
	}
	public Double getJsbdZyp() {
		return jsbdZyp;
	}
	public void setJsbdZyp(Double jsbdZyp) {
		this.jsbdZyp = jsbdZyp;
	}
	public Double getJsbdZyg() {
		return jsbdZyg;
	}
	public void setJsbdZyg(Double jsbdZyg) {
		this.jsbdZyg = jsbdZyg;
	}
	public Double getJsbd1Zyz() {
		return jsbd1Zyz;
	}
	public void setJsbd1Zyz(Double jsbd1Zyz) {
		this.jsbd1Zyz = jsbd1Zyz;
	}
	public Double getJsbd1Zyj() {
		return jsbd1Zyj;
	}
	public void setJsbd1Zyj(Double jsbd1Zyj) {
		this.jsbd1Zyj = jsbd1Zyj;
	}
	public Double getJsbd1Zyf() {
		return jsbd1Zyf;
	}
	public void setJsbd1Zyf(Double jsbd1Zyf) {
		this.jsbd1Zyf = jsbd1Zyf;
	}
	public Double getJsbd1Zyp() {
		return jsbd1Zyp;
	}
	public void setJsbd1Zyp(Double jsbd1Zyp) {
		this.jsbd1Zyp = jsbd1Zyp;
	}
	public Double getJsbd1Zyg() {
		return jsbd1Zyg;
	}
	public void setJsbd1Zyg(Double jsbd1Zyg) {
		this.jsbd1Zyg = jsbd1Zyg;
	}
	public Double getJsbd2Zyz() {
		return jsbd2Zyz;
	}
	public void setJsbd2Zyz(Double jsbd2Zyz) {
		this.jsbd2Zyz = jsbd2Zyz;
	}
	public Double getJsbd2Zyj() {
		return jsbd2Zyj;
	}
	public void setJsbd2Zyj(Double jsbd2Zyj) {
		this.jsbd2Zyj = jsbd2Zyj;
	}
	public Double getJsbd2Zyf() {
		return jsbd2Zyf;
	}
	public void setJsbd2Zyf(Double jsbd2Zyf) {
		this.jsbd2Zyf = jsbd2Zyf;
	}
	public Double getJsbd2Zyp() {
		return jsbd2Zyp;
	}
	public void setJsbd2Zyp(Double jsbd2Zyp) {
		this.jsbd2Zyp = jsbd2Zyp;
	}
	public Double getJsbd2Zyg() {
		return jsbd2Zyg;
	}
	public void setJsbd2Zyg(Double jsbd2Zyg) {
		this.jsbd2Zyg = jsbd2Zyg;
	}
	public Integer getJsbdYmd() {
		return jsbdYmd;
	}
	public void setJsbdYmd(Integer jsbdYmd) {
		this.jsbdYmd = jsbdYmd;
	}
	public Integer getCalcMdhmi() {
		return calcMdhmi;
	}
	public void setCalcMdhmi(Integer calcMdhmi) {
		this.calcMdhmi = calcMdhmi;
	}
	public Integer getCalcBdymd() {
		return calcBdymd;
	}
	public void setCalcBdymd(Integer calcBdymd) {
		this.calcBdymd = calcBdymd;
	}
	public Double getCalcZyz() {
		return calcZyz;
	}
	public void setCalcZyz(Double calcZyz) {
		this.calcZyz = calcZyz;
	}
	public Double getCalcZyj() {
		return calcZyj;
	}
	public void setCalcZyj(Double calcZyj) {
		this.calcZyj = calcZyj;
	}
	public Double getCalcZyf() {
		return calcZyf;
	}
	public void setCalcZyf(Double calcZyf) {
		this.calcZyf = calcZyf;
	}
	public Double getCalcZyp() {
		return calcZyp;
	}
	public void setCalcZyp(Double calcZyp) {
		this.calcZyp = calcZyp;
	}
	public Double getCalcZyg() {
		return calcZyg;
	}
	public void setCalcZyg(Double calcZyg) {
		this.calcZyg = calcZyg;
	}
	public Double getCalc1Zyz() {
		return calc1Zyz;
	}
	public void setCalc1Zyz(Double calc1Zyz) {
		this.calc1Zyz = calc1Zyz;
	}
	public Double getCalc1Zyj() {
		return calc1Zyj;
	}
	public void setCalc1Zyj(Double calc1Zyj) {
		this.calc1Zyj = calc1Zyj;
	}
	public Double getCalc1Zyf() {
		return calc1Zyf;
	}
	public void setCalc1Zyf(Double calc1Zyf) {
		this.calc1Zyf = calc1Zyf;
	}
	public Double getCalc1Zyp() {
		return calc1Zyp;
	}
	public void setCalc1Zyp(Double calc1Zyp) {
		this.calc1Zyp = calc1Zyp;
	}
	public Double getCalc1Zyg() {
		return calc1Zyg;
	}
	public void setCalc1Zyg(Double calc1Zyg) {
		this.calc1Zyg = calc1Zyg;
	}
	public Double getCalc2Zyz() {
		return calc2Zyz;
	}
	public void setCalc2Zyz(Double calc2Zyz) {
		this.calc2Zyz = calc2Zyz;
	}
	public Double getCalc2Zyj() {
		return calc2Zyj;
	}
	public void setCalc2Zyj(Double calc2Zyj) {
		this.calc2Zyj = calc2Zyj;
	}
	public Double getCalc2Zyf() {
		return calc2Zyf;
	}
	public void setCalc2Zyf(Double calc2Zyf) {
		this.calc2Zyf = calc2Zyf;
	}
	public Double getCalc2Zyp() {
		return calc2Zyp;
	}
	public void setCalc2Zyp(Double calc2Zyp) {
		this.calc2Zyp = calc2Zyp;
	}
	public Double getCalc2Zyg() {
		return calc2Zyg;
	}
	public void setCalc2Zyg(Double calc2Zyg) {
		this.calc2Zyg = calc2Zyg;
	}
	public Double getNowRemain() {
		return nowRemain;
	}
	public void setNowRemain(Double nowRemain) {
		this.nowRemain = nowRemain;
	}
	public Double getBjBd() {
		return bjBd;
	}
	public void setBjBd(Double bjBd) {
		this.bjBd = bjBd;
	}
	public Double getTzBd() {
		return tzBd;
	}
	public void setTzBd(Double tzBd) {
		this.tzBd = tzBd;
	}
	public Byte getCsAl1State() {
		return csAl1State;
	}
	public void setCsAl1State(Byte csAl1State) {
		this.csAl1State = csAl1State;
	}
	public Byte getCsAl2State() {
		return csAl2State;
	}
	public void setCsAl2State(Byte csAl2State) {
		this.csAl2State = csAl2State;
	}
	public Byte getCsFhzState() {
		return csFhzState;
	}
	public void setCsFhzState(Byte csFhzState) {
		this.csFhzState = csFhzState;
	}
	public Integer getAl1Mdhmi() {
		return al1Mdhmi;
	}
	public void setAl1Mdhmi(Integer al1Mdhmi) {
		this.al1Mdhmi = al1Mdhmi;
	}
	public Integer getAl2Mdhmi() {
		return al2Mdhmi;
	}
	public void setAl2Mdhmi(Integer al2Mdhmi) {
		this.al2Mdhmi = al2Mdhmi;
	}
	public Integer getFhzMdhmi() {
		return fhzMdhmi;
	}
	public void setFhzMdhmi(Integer fhzMdhmi) {
		this.fhzMdhmi = fhzMdhmi;
	}
	public Double getFzZyz() {
		return fzZyz;
	}
	public void setFzZyz(Double fzZyz) {
		this.fzZyz = fzZyz;
	}
	public Double getFz1Zyz() {
		return fz1Zyz;
	}
	public void setFz1Zyz(Double fz1Zyz) {
		this.fz1Zyz = fz1Zyz;
	}
	public Double getFz2Zyz() {
		return fz2Zyz;
	}
	public void setFz2Zyz(Double fz2Zyz) {
		this.fz2Zyz = fz2Zyz;
	}
	public Integer getYcFlag1() {
		return ycFlag1;
	}
	public void setYcFlag1(Integer ycFlag1) {
		this.ycFlag1 = ycFlag1;
	}
	public Integer getYcFlag2() {
		return ycFlag2;
	}
	public void setYcFlag2(Integer ycFlag2) {
		this.ycFlag2 = ycFlag2;
	}
	public Integer getYcFlag3() {
		return ycFlag3;
	}
	public void setYcFlag3(Integer ycFlag3) {
		this.ycFlag3 = ycFlag3;
	}
	public Integer getHbDate() {
		return hbDate;
	}
	public void setHbDate(Integer hbDate) {
		this.hbDate = hbDate;
	}
	public Integer getHbTime() {
		return hbTime;
	}
	public void setHbTime(Integer hbTime) {
		this.hbTime = hbTime;
	}
	public Integer getKhDate() {
		return khDate;
	}
	public void setKhDate(Integer khDate) {
		this.khDate = khDate;
	}
	public Integer getXhDate() {
		return xhDate;
	}
	public void setXhDate(Integer xhDate) {
		this.xhDate = xhDate;
	}
	public Double getTotalGdz() {
		return totalGdz;
	}
	public void setTotalGdz(Double totalGdz) {
		this.totalGdz = totalGdz;
	}
	public Integer getBuyTimes() {
		return buyTimes;
	}
	public void setBuyTimes(Integer buyTimes) {
		this.buyTimes = buyTimes;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Double getShutdownVal2() {
		return shutdownVal2;
	}
	public void setShutdownVal2(Double shutdownVal2) {
		this.shutdownVal2 = shutdownVal2;
	}
	public Double getNowRemain2() {
		return nowRemain2;
	}
	public void setNowRemain2(Double nowRemain2) {
		this.nowRemain2 = nowRemain2;
	}
	public Double getJtTotalZbdl() {
		return jtTotalZbdl;
	}
	public void setJtTotalZbdl(Double jtTotalZbdl) {
		this.jtTotalZbdl = jtTotalZbdl;
	}
	public Double getJtTotalDl() {
		return jtTotalDl;
	}
	public void setJtTotalDl(Double jtTotalDl) {
		this.jtTotalDl = jtTotalDl;
	}
	
	public Integer getJtResetYmd() {
		return jtResetYmd;
	}
	public void setJtResetYmd(Integer jtResetYmd) {
		this.jtResetYmd = jtResetYmd;
	}
	public Double getFxdfIallMoney() {
		return fxdfIallMoney;
	}
	public void setFxdfIallMoney(Double fxdfIallMoney) {
		this.fxdfIallMoney = fxdfIallMoney;
	}
	public Double getFxdfRemain() {
		return fxdfRemain;
	}
	public void setFxdfRemain(Double fxdfRemain) {
		this.fxdfRemain = fxdfRemain;
	}
	public Double getFxdfRemain2() {
		return fxdfRemain2;
	}
	public void setFxdfRemain2(Double fxdfRemain2) {
		this.fxdfRemain2 = fxdfRemain2;
	}
	public Integer getFxdfYm() {
		return fxdfYm;
	}
	public void setFxdfYm(Integer fxdfYm) {
		this.fxdfYm = fxdfYm;
	}
	public Integer getFxdfDataYmd() {
		return fxdfDataYmd;
	}
	public void setFxdfDataYmd(Integer fxdfDataYmd) {
		this.fxdfDataYmd = fxdfDataYmd;
	}
	public Integer getFxdfCalcMdhmi() {
		return fxdfCalcMdhmi;
	}
	public void setFxdfCalcMdhmi(Integer fxdfCalcMdhmi) {
		this.fxdfCalcMdhmi = fxdfCalcMdhmi;
	}
	public Integer getJsBcYmd() {
		return jsBcYmd;
	}
	public void setJsBcYmd(Integer jsBcYmd) {
		this.jsBcYmd = jsBcYmd;
	}
	

	
}
