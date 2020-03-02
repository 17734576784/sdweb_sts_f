package com.kesd.dbpara;
public class LineFZMan {
	
	private Short    		id;			
	private String   		describe;	
	private Short           orgId;      
	private String   		telNo1;			
	private String   		telNo2;		
	private String   		addr;		
	
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
	public Short getOrgId() {
		return orgId;
	}
	public void setOrgId(Short orgId) {
		this.orgId = orgId;
	}
	public String getTelNo1() {
		return telNo1;
	}
	public void setTelNo1(String telNo1) {
		this.telNo1 = telNo1;
	}
	public String getTelNo2() {
		return telNo2;
	}
	public void setTelNo2(String telNo2) {
		this.telNo2 = telNo2;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
}
