package com.kesd.dbpara;

public class YffRatePara {
	private Short   id;				//编号
	private String  describe;		//名称
	private	Short	rateId;			/*费率编号*/
	private Integer	activdate;		/*费率激活日期 月日*/
	
	private Byte	feeType;		/*费率*/ /*0 种类A:居民区 3 类型D:非住宅（照明&用电） 4 类型E:商业&办公室*/
	private Double	ratedZ;			/*总费率*/
	
	/*复费率*/
	private Double	ratefJ;			/*尖费率*/
	private Double	ratefF;			/*峰费率*/	
	private Double	ratefP;			/*平费率*/
	private Double	ratefG;			/*谷费率*/
	
	/*混合费率*/
	private Double	rateh1;			/*混合费率1*/
	private Double	rateh2;			/*混合费率2*/	
	private Double	rateh3;			/*混合费率3*/
	private Double	rateh4;			/*混合费率4*/	
	
	private Byte	ratehBl1;		/*混合比例1*/	
	private Byte	ratehBl2;		/*混合比例2*/		
	private Byte	ratehBl3;		/*混合比例3*/		
	private Byte	ratehBl4;		/*混合比例4*/
	
	/*阶梯电价*/
	private Byte	ratejType;		/*阶梯电价类型*/ 	/*0 年度方案, 1月度方案	2月度峰谷/阶梯混合*/
	private Byte	ratejNum;		/*阶梯电价数*/
	private Byte	meterfeeType;	/*电表费率类型  0 单费率 1 复费率   3阶梯费率  20120704*/
	private Double 	meterfeeR;		/*电表执行电价--新增20120705*/
	
	private Double	ratejR1;			/*阶梯费率1*/
	private Double	ratejR2;			/*阶梯费率2*/	
	private Double	ratejR3;			/*阶梯费率3*/
	private Double	ratejR4;			/*阶梯费率4*/
	
	private Double	ratejR5;			/*阶梯费率5*/	
	private Double	ratejR6;			/*阶梯费率6*/
	private Double	ratejR7;			/*阶梯费率7*/
		
	private Double	ratejTd1;		/*阶梯梯度值1*/
	private Double	ratejTd2;		/*阶梯梯度值2*/
	private Double	ratejTd3;		/*阶梯梯度值3*/
	private Double	ratejTd4;		/*阶梯梯度值4*/
	
	private Double	ratejTd5;		/*阶梯梯度值5*/
	private Double	ratejTd6;		/*阶梯梯度值6*/
	
	public Short getRateId() {
		return rateId;
	}
	public void setRateId(Short rateId) {
		this.rateId = rateId;
	}
	public Integer getActivdate() {
		return activdate;
	}
	public void setActivdate(Integer activdate) {
		this.activdate = activdate;
	}
	public Double getRatejR5() {
		return ratejR5;
	}
	public void setRatejR5(Double ratejR5) {
		this.ratejR5 = ratejR5;
	}
	public Double getRatejR6() {
		return ratejR6;
	}
	public void setRatejR6(Double ratejR6) {
		this.ratejR6 = ratejR6;
	}
	public Double getRatejR7() {
		return ratejR7;
	}
	public void setRatejR7(Double ratejR7) {
		this.ratejR7 = ratejR7;
	}
	public Double getRatejTd4() {
		return ratejTd4;
	}
	public void setRatejTd4(Double ratejTd4) {
		this.ratejTd4 = ratejTd4;
	}
	public Double getRatejTd5() {
		return ratejTd5;
	}
	public void setRatejTd5(Double ratejTd5) {
		this.ratejTd5 = ratejTd5;
	}
	public Double getRatejTd6() {
		return ratejTd6;
	}
	public void setRatejTd6(Double ratejTd6) {
		this.ratejTd6 = ratejTd6;
	}
	/*混合阶梯电价--新增20120705*/
	private Byte	ratehjType;		/*阶梯电价类型*/ 	/*0 年度方案, 1月度方案*/
	private Byte	ratehjNum;		/*阶梯电价数*/
	
	private Byte	meterfeehjType;	/*电表费率类型  0单费率 1复费率  3阶梯费率 20120704*/
	private Double	meterfeehjR;	/*电表执行电价*/
	
	private Double	ratehjHr1R1;	/*第1比例电价-阶梯费率1*/
	private Double	ratehjHr1R2;	/*第1比例电价-阶梯费率2*/
	private Double	ratehjHr1R3;	/*第1比例电价-阶梯费率3*/
	private Double	ratehjHr1R4;	/*第1比例电价-阶梯费率4*/
		
	private Double	ratehjHr1Td1;	/*第1比例电价-阶梯梯度值1*/
	private Double	ratehjHr1Td2;	/*第1比例电价-阶梯梯度值2*/
	private Double	ratehjHr1Td3;	/*第1比例电价-阶梯梯度值3*/
	
	private Double	ratehjHr2;		/*第2比例电价*/
	private Double	ratehjHr3;		/*第3比例电价*/
	
	private Byte	ratehjBl1;		/*混合比例1*/	
	private Byte	ratehjBl2;		/*混合比例2*/	
	private Byte	ratehjBl3;		/*混合比例3*/	
	
	
	
	
	private Double     moneyLimit;	//囤积金额限值
	
	private String     infCode1;	//接口编码1
	private String     infCode2;	//接口编码2
	private String     infCode3;	//接口编码3
	private Double     reserve1;	//扩展1
	private Double     reserve2;	//扩展2
	
	public Short getId() {
		return id;
	}
	public void setId(Short id) {
		this.id = id;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	public Byte getFeeType() {
		return feeType;
	}
	public void setFeeType(Byte feeType) {
		this.feeType = feeType;
	}
	public Double getRatedZ() {
		return ratedZ;
	}
	public void setRatedZ(Double ratedZ) {
		this.ratedZ = ratedZ;
	}
	public Double getRatefJ() {
		return ratefJ;
	}
	public void setRatefJ(Double ratefJ) {
		this.ratefJ = ratefJ;
	}
	public Double getRatefF() {
		return ratefF;
	}
	public void setRatefF(Double ratefF) {
		this.ratefF = ratefF;
	}
	public Double getRatefP() {
		return ratefP;
	}
	public void setRatefP(Double ratefP) {
		this.ratefP = ratefP;
	}
	public Double getRatefG() {
		return ratefG;
	}
	public void setRatefG(Double ratefG) {
		this.ratefG = ratefG;
	}
	public Double getRateh1() {
		return rateh1;
	}
	public void setRateh1(Double rateh1) {
		this.rateh1 = rateh1;
	}
	public Double getRateh2() {
		return rateh2;
	}
	public void setRateh2(Double rateh2) {
		this.rateh2 = rateh2;
	}
	public Double getRateh3() {
		return rateh3;
	}
	public void setRateh3(Double rateh3) {
		this.rateh3 = rateh3;
	}
	public Double getRateh4() {
		return rateh4;
	}
	public void setRateh4(Double rateh4) {
		this.rateh4 = rateh4;
	}
	public Byte getRatehBl1() {
		return ratehBl1;
	}
	public void setRatehBl1(Byte ratehBl1) {
		this.ratehBl1 = ratehBl1;
	}
	public Byte getRatehBl2() {
		return ratehBl2;
	}
	public void setRatehBl2(Byte ratehBl2) {
		this.ratehBl2 = ratehBl2;
	}
	public Byte getRatehBl3() {
		return ratehBl3;
	}
	public void setRatehBl3(Byte ratehBl3) {
		this.ratehBl3 = ratehBl3;
	}
	public Byte getRatehBl4() {
		return ratehBl4;
	}
	public void setRatehBl4(Byte ratehBl4) {
		this.ratehBl4 = ratehBl4;
	}
	
	public Byte getRatejType() {
		return ratejType;
	}
	public void setRatejType(Byte ratejType) {
		this.ratejType = ratejType;
	}
	public Byte getRatejNum() {
		return ratejNum;
	}
	public void setRatejNum(Byte ratejNum) {
		this.ratejNum = ratejNum;
	}
	
	public Byte getMeterfeeType() {
		return meterfeeType;
	}
	public void setMeterfeeType(Byte meterfeeType) {
		this.meterfeeType = meterfeeType;
	}
	public Double getMeterfeeR() {
		return meterfeeR;
	}
	public void setMeterfeeR(Double meterfeeR) {
		this.meterfeeR = meterfeeR;
	}
	public Double getRatejR1() {
		return ratejR1;
	}
	public void setRatejR1(Double ratejR1) {
		this.ratejR1 = ratejR1;
	}
	public Double getRatejR2() {
		return ratejR2;
	}
	public void setRatejR2(Double ratejR2) {
		this.ratejR2 = ratejR2;
	}
	public Double getRatejR3() {
		return ratejR3;
	}
	public void setRatejR3(Double ratejR3) {
		this.ratejR3 = ratejR3;
	}
	public Double getRatejR4() {
		return ratejR4;
	}
	public void setRatejR4(Double ratejR4) {
		this.ratejR4 = ratejR4;
	}
	public Double getRatejTd1() {
		return ratejTd1;
	}
	public void setRatejTd1(Double ratejTd1) {
		this.ratejTd1 = ratejTd1;
	}
	public Double getRatejTd2() {
		return ratejTd2;
	}
	public void setRatejTd2(Double ratejTd2) {
		this.ratejTd2 = ratejTd2;
	}
	public Double getRatejTd3() {
		return ratejTd3;
	}
	public void setRatejTd3(Double ratejTd3) {
		this.ratejTd3 = ratejTd3;
	}
	
	public Byte getRatehjType() {
		return ratehjType;
	}
	public void setRatehjType(Byte ratehjType) {
		this.ratehjType = ratehjType;
	}
	public Byte getRatehjNum() {
		return ratehjNum;
	}
	public void setRatehjNum(Byte ratehjNum) {
		this.ratehjNum = ratehjNum;
	}
	public Byte getMeterfeehjType() {
		return meterfeehjType;
	}
	public void setMeterfeehjType(Byte meterfeehjType) {
		this.meterfeehjType = meterfeehjType;
	}
	public Double getMeterfeehjR() {
		return meterfeehjR;
	}
	public void setMeterfeehjR(Double meterfeehjR) {
		this.meterfeehjR = meterfeehjR;
	}
	public Double getRatehjHr1R1() {
		return ratehjHr1R1;
	}
	public void setRatehjHr1R1(Double ratehjHr1R1) {
		this.ratehjHr1R1 = ratehjHr1R1;
	}
	public Double getRatehjHr1R2() {
		return ratehjHr1R2;
	}
	public void setRatehjHr1R2(Double ratehjHr1R2) {
		this.ratehjHr1R2 = ratehjHr1R2;
	}
	public Double getRatehjHr1R3() {
		return ratehjHr1R3;
	}
	public void setRatehjHr1R3(Double ratehjHr1R3) {
		this.ratehjHr1R3 = ratehjHr1R3;
	}
	public Double getRatehjHr1R4() {
		return ratehjHr1R4;
	}
	public void setRatehjHr1R4(Double ratehjHr1R4) {
		this.ratehjHr1R4 = ratehjHr1R4;
	}
	public Double getRatehjHr1Td1() {
		return ratehjHr1Td1;
	}
	public void setRatehjHr1Td1(Double ratehjHr1Td1) {
		this.ratehjHr1Td1 = ratehjHr1Td1;
	}
	public Double getRatehjHr1Td2() {
		return ratehjHr1Td2;
	}
	public void setRatehjHr1Td2(Double ratehjHr1Td2) {
		this.ratehjHr1Td2 = ratehjHr1Td2;
	}
	public Double getRatehjHr1Td3() {
		return ratehjHr1Td3;
	}
	public void setRatehjHr1Td3(Double ratehjHr1Td3) {
		this.ratehjHr1Td3 = ratehjHr1Td3;
	}
	public Double getRatehjHr2() {
		return ratehjHr2;
	}
	public void setRatehjHr2(Double ratehjHr2) {
		this.ratehjHr2 = ratehjHr2;
	}
	public Double getRatehjHr3() {
		return ratehjHr3;
	}
	public void setRatehjHr3(Double ratehjHr3) {
		this.ratehjHr3 = ratehjHr3;
	}
	public Byte getRatehjBl1() {
		return ratehjBl1;
	}
	public void setRatehjBl1(Byte ratehjBl1) {
		this.ratehjBl1 = ratehjBl1;
	}
	public Byte getRatehjBl2() {
		return ratehjBl2;
	}
	public void setRatehjBl2(Byte ratehjBl2) {
		this.ratehjBl2 = ratehjBl2;
	}
	public Byte getRatehjBl3() {
		return ratehjBl3;
	}
	public void setRatehjBl3(Byte ratehjBl3) {
		this.ratehjBl3 = ratehjBl3;
	}
	public String getInfCode1() {
		return infCode1;
	}
	public void setInfCode1(String infCode1) {
		this.infCode1 = infCode1;
	}
	public String getInfCode2() {
		return infCode2;
	}
	public void setInfCode2(String infCode2) {
		this.infCode2 = infCode2;
	}
	public String getInfCode3() {
		return infCode3;
	}
	public void setInfCode3(String infCode3) {
		this.infCode3 = infCode3;
	}
	public Double getReserve1() {
		return reserve1;
	}
	public void setReserve1(Double reserve1) {
		this.reserve1 = reserve1;
	}
	public Double getReserve2() {
		return reserve2;
	}
	public void setReserve2(Double reserve2) {
		this.reserve2 = reserve2;
	}
	public Double getMoneyLimit() {
		return moneyLimit;
	}
	public void setMoneyLimit(Double moneyLimit) {
		this.moneyLimit = moneyLimit;
	}
	
}
