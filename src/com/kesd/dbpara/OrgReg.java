package com.kesd.dbpara;

public class OrgReg {
	private Short  orgId;
	private Short  regType;
	private String regUser;
	private String regCode;
	private Short  useFlag;
	
	public void setOrgId(Short orgId){
		this.orgId = orgId;
	}
	public Short getOrgId(){
		return orgId;
	}
	
	public void setRegType(Short regType){
		this.regType = regType;
	}
	public Short getRegType(){
		return regType;
	}			
	
	public void setRegUser(String regUser){
		this.regUser = regUser;
	}
	public String getRegUser(){
		return regUser;
	}
	
	public void setRegCode(String regCode){
		this.regCode = regCode;
	}
	public String getRegCode(){
		return regCode;
	}			

	public void setUseFlag(Short useFlag){
		this.useFlag = useFlag;
	}
	public Short getUseFlag(){
		return useFlag;
	}
	
}
