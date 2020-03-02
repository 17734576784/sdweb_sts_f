package com.kesd.dbpara;

public class CsStanditem {
	private Short  		cosId;			/*力调参数ID*/
	private Short		itemId;			/*分项ID*/
	private Short		realcos;		/*实际cos*/
	private Short		dfchgarate;		/*月电费增减百分比*/
	private Short      	reserve1;
	private Integer     reserve2;
	
	public Short getCosId() {
		return cosId;
	}
	public void setCosId(Short cosId) {
		this.cosId = cosId;
	}
	public Short getItemId() {
		return itemId;
	}
	public void setItemId(Short itemId) {
		this.itemId = itemId;
	}
	public Short getRealcos() {
		return realcos;
	}
	public void setRealcos(Short realcos) {
		this.realcos = realcos;
	}
	public Short getDfchgarate() {
		return dfchgarate;
	}
	public void setDfchgarate(Short dfchgarate) {
		this.dfchgarate = dfchgarate;
	}
	public Short getReserve1() {
		return reserve1;
	}
	public void setReserve1(Short reserve1) {
		this.reserve1 = reserve1;
	}
	public Integer getReserve2() {
		return reserve2;
	}
	public void setReserve2(Integer reserve2) {
		this.reserve2 = reserve2;
	}
	
}
	