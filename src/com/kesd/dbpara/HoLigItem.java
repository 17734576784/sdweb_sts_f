package com.kesd.dbpara;

import java.io.Serializable;

public class HoLigItem implements Serializable{
	private Short groupId;
	private Short holidayId;
	private String reserve1;
	private String reserve2;
	
	public Short getGroupId() {
		return groupId;
	}
	public void setGroupId(Short groupId) {
		this.groupId = groupId;
	}
	public Short getHolidayId() {
		return holidayId;
	}
	public void setHolidayId(Short holidayId) {
		this.holidayId = holidayId;
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
}
