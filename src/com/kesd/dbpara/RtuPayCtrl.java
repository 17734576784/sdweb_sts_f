package com.kesd.dbpara;

import java.io.Serializable;

public class RtuPayCtrl implements Serializable{
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
	private static final long serialVersionUID = -6049649149126208925L;
	private Integer    	rtuId;        	
	private Byte		useFlag;		
	
	private Integer		yffbgDate;		
	private Integer		yffbgTime;		
	
	private Integer		sg186bgDate;	
	private	Integer		sg186bgTime;		
	
	private Integer		reserve1Date;	
	private Integer		reserve1Time;	
	
	private	Integer		reserve2Date;	
	private	Integer		reserve2Time;	
	
	private Integer		reserve3Date;	
	private Integer		reserve3Time;			
	
	public Integer getRtuId() {
		return rtuId;
	}
	public void setRtuId(Integer rtuId) {
		this.rtuId = rtuId;
	}
	public Byte getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(Byte useFlag) {
		this.useFlag = useFlag;
	}
	public Integer getYffbgDate() {
		return yffbgDate;
	}
	public void setYffbgDate(Integer yffbgDate) {
		this.yffbgDate = yffbgDate;
	}
	public Integer getYffbgTime() {
		return yffbgTime;
	}
	public void setYffbgTime(Integer yffbgTime) {
		this.yffbgTime = yffbgTime;
	}
	public Integer getSg186bgDate() {
		return sg186bgDate;
	}
	public void setSg186bgDate(Integer sg186bgDate) {
		this.sg186bgDate = sg186bgDate;
	}
	public Integer getSg186bgTime() {
		return sg186bgTime;
	}
	public void setSg186bgTime(Integer sg186bgTime) {
		this.sg186bgTime = sg186bgTime;
	}
	public Integer getReserve1Date() {
		return reserve1Date;
	}
	public void setReserve1Date(Integer reserve1Date) {
		this.reserve1Date = reserve1Date;
	}
	public Integer getReserve1Time() {
		return reserve1Time;
	}
	public void setReserve1Time(Integer reserve1Time) {
		this.reserve1Time = reserve1Time;
	}
	public Integer getReserve2Date() {
		return reserve2Date;
	}
	public void setReserve2Date(Integer reserve2Date) {
		this.reserve2Date = reserve2Date;
	}
	public Integer getReserve2Time() {
		return reserve2Time;
	}
	public void setReserve2Time(Integer reserve2Time) {
		this.reserve2Time = reserve2Time;
	}
	public Integer getReserve3Date() {
		return reserve3Date;
	}
	public void setReserve3Date(Integer reserve3Date) {
		this.reserve3Date = reserve3Date;
	}
	public Integer getReserve3Time() {
		return reserve3Time;
	}
	public void setReserve3Time(Integer reserve3Time) {
		this.reserve3Time = reserve3Time;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
