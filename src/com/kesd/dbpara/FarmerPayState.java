package com.kesd.dbpara;

import java.io.Serializable;

public class FarmerPayState implements Serializable{

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
	
	private Integer 	areaId;			/*所属片区*/	
	private Integer		farmerId;		/*用户编号*/	
	private Byte		cusState;		/*用户状态 0 初始态, 1 正常态, 10 销户态*/	                                                         
	private Byte		opType;			/*本次操作类型 初始态, 开户, 缴费, 补卡, 补写卡, 冲正, 销户 定义与原来一致 */	                       
	private Integer		opDate;			/*本次操作日期*/                                                                                     
	private	Integer		opTime;			/*本次操作时间*/                                                                                     
	                                                                                                                                     
	private Double 		payMoney;		/*缴费金额*/                                                                                         
	private Double 		othjsMoney;		/*结算金额(其它系统)*/                                                                               
	private Double 		zbMoney;		/*追补金额*/                                                                                         
	private Double 		allMoney;		/*总金额*/                                                                                           
		                                                                                                                                 
	private Double		lsRemain;		/*上次剩余金额*/	                                                                                   
	private Double		curRemain;		/*当前剩余金额*/                                                                                     
	private Double		alarm1;			/*报警值1*/		/*备用*/                                                                                                     
	private Double		alarm2;			/*报警值2*/		/*备用*/                                                                                     
	                                                                                                                                     
	private	Integer		khDate;			/*开户日期-YYYYMMDD*/                                                                                         
	private	Integer		xhDate;			/*销户日期-YYYYMMDD*/                                                                                     
                                                                                                                                         
	private Double 		totalGdz;		/*累计购电金额*/                                                                               
	private Integer		buyTimes;		/*购电次数*/                                                                               
	private Integer		totbuyTimes;	/*累计购电次数*/                                                                                                     
	private	Integer		jsBcYmd;	 	/*结算补差日期YYYYMMDD*/   /*备用*/                                                                               
		                                                                                                            
	private Integer		reserve1;                                                                                                        
                                                                                                      
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
	public Integer getJsBcYmd() {
		return jsBcYmd;
	}
	public void setJsBcYmd(Integer jsBcYmd) {
		this.jsBcYmd = jsBcYmd;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public Integer getFarmerId() {
		return farmerId;
	}
	public void setFarmerId(Integer farmerId) {
		this.farmerId = farmerId;
	}
	public Double getLsRemain() {
		return lsRemain;
	}
	public void setLsRemain(Double lsRemain) {
		this.lsRemain = lsRemain;
	}
	public Double getCurRemain() {
		return curRemain;
	}
	public void setCurRemain(Double curRemain) {
		this.curRemain = curRemain;
	}
	public Double getAlarm1() {
		return alarm1;
	}
	public void setAlarm1(Double alarm1) {
		this.alarm1 = alarm1;
	}
	public Double getAlarm2() {
		return alarm2;
	}
	public void setAlarm2(Double alarm2) {
		this.alarm2 = alarm2;
	}
	public Integer getTotbuyTimes() {
		return totbuyTimes;
	}
	public void setTotbuyTimes(Integer totbuyTimes) {
		this.totbuyTimes = totbuyTimes;
	}	
}
