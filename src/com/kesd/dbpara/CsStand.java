package com.kesd.dbpara;

public class CsStand {
	private Short  		id;				/*编号*/
	private String     	describe;		/*描述*/
	private Short      	stand;			/*执行标准*/
	private Byte      	useflag;		/*使用标志*/
	private Short      	reserve1;
	private Integer     reserve2;
	
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
	public Short getStand() {
		return stand;
	}
	public void setStand(Short stand) {
		this.stand = stand;
	}
	public Byte getUseflag() {
		return useflag;
	}
	public void setUseflag(Byte useflag) {
		this.useflag = useflag;
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