package com.kesd.dbpara;

public class GloProtect {
	private Short	id;			
	private Byte   	appType;	
	private Byte   	useFlag;	
	private Integer bgDate;		
	private Integer bgTime;		
	
	private Integer edDate;		
	private Integer edTime;		
	
	private String  reserve1;	
	private String  reserve2;	
	private String  reserve3;	
	private String  reserve4;	
	
	
	public Short getId() {
		return id;
	}
	public void setId(Short id) {
		this.id = id;
	}
	public Byte getAppType() {
		return appType;
	}
	public void setAppType(Byte appType) {
		this.appType = appType;
	}
	public Byte getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(Byte useFlag) {
		this.useFlag = useFlag;
	}
	
	public Integer getBgDate() {
		return bgDate;
	}
	public void setBgDate(Integer bgDate) {
		this.bgDate = bgDate;
	}
	public Integer getBgTime() {
		return bgTime;
	}
	public void setBgTime(Integer bgTime) {
		this.bgTime = bgTime;
	}
	public Integer getEdDate() {
		return edDate;
	}
	public void setEdDate(Integer edDate) {
		this.edDate = edDate;
	}
	public Integer getEdTime() {
		return edTime;
	}
	public void setEdTime(Integer edTime) {
		this.edTime = edTime;
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
	public String getReserve4() {
		return reserve4;
	}
	public void setReserve4(String reserve4) {
		this.reserve4 = reserve4;
	}	
	
}
