package com.kesd.dbpara;

public class OrgPara {
	private Short  id;					
	private String describe;			
	private String rorgNo ;				
	private String addr;				
	private String postalCode;			
	private String mail;				
	private String fax;					
	private String linkMan;				
	private String telNo;				
	
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
	
	public String getRorgNo() {
		return rorgNo;
	}
	public void setRorgNo(String rorgNo) {
		this.rorgNo = rorgNo;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	/*
	public Set<LineFZMan> getLineFZMan() {
		return lineFZMan;
	}
	public void setLineFZMan(Set<LineFZMan> lineFZMan) {
		this.lineFZMan = lineFZMan;
	}
	public Set<ConsPara> getConsPara() {
		return consPara;
	}
	public void setConsPara(Set<ConsPara> consPara) {
		this.consPara = consPara;
	}
	*/
}
