package com.kesd.dbpara;

import java.io.Serializable;

public class MeterExtparaNP implements Serializable{
	
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
	
	private Integer rtuId;			/*终端编号*/
	private Short	mpId;			/*测量点编号*/
	private Integer areaId;			/*所属片区*/
	private String	reserve1;	
	private String	reserve2;
	private String	reserve3;
	 
	
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
	
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getReserve1() {
		return reserve1;
	}
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	public String getReserve2() {
		return reserve2;
	}
	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}
	public String getReserve3() {
		return reserve3;
	}
	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}
	
	public MeterExtparaNP(){
		
	}
}
