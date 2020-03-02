package com.kesd.dbpara;

import java.io.Serializable;

public class MpPara implements Serializable{
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
	
	private static final long serialVersionUID = -39995634817970066L;
	private			Integer				rtuId;		    
	private			Short 				id;				
	private			String				describe;		
	private			Byte				useFlag;		
	private			Byte				mpType;			
	private 		Byte				consType;		
	private			Integer				ptNumerator;  	
	private			Integer				ptDenominator; 	
	private			Double				ptRatio;		
	private			Integer				ctNumerator;   	
	private			Integer				ctDenominator;  
	private			Double				ctRatio;	    
	private			Double				rv;				
	private			Double				ri;					
	private			Double				rp;				
	private			Double				mi;					
	private			Byte				wiringMode;		
	private			Byte	 			bakFlag;	    
	private			Short       		mainId;		 	
	private			Short       		bdFactor;		
	private			Short       		vfactor;	 	
	private			Short       		ifactor;	 	
	private			Short       		pfactor;	 	
	private			Short       		csFactory;	 	
	private			Short  				clFa; 			
	private			String				infCode1;  			
	private			String				infCode2;  			
	private			String				infCode3;  			
	private			String  			reserve1;  		
	
	public Integer getRtuId() {
		return rtuId;
	}
	public void setRtuId(Integer rtuId) {
		this.rtuId = rtuId;
	}
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
	public Byte getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(Byte useFlag) {
		this.useFlag = useFlag;
	}
	public Byte getMpType() {
		return mpType;
	}
	public void setMpType(Byte mpType) {
		this.mpType = mpType;
	}

	public Integer getPtNumerator() {
		return ptNumerator;
	}
	public void setPtNumerator(Integer ptNumerator) {
		this.ptNumerator = ptNumerator;
	}
	public Integer getPtDenominator() {
		return ptDenominator;
	}
	public void setPtDenominator(Integer ptDenominator) {
		this.ptDenominator = ptDenominator;
	}
	public Double getPtRatio() {
		return ptRatio;
	}
	public void setPtRatio(Double ptRatio) {
		this.ptRatio = ptRatio;
	}
	public Integer getCtNumerator() {
		return ctNumerator;
	}
	public void setCtNumerator(Integer ctNumerator) {
		this.ctNumerator = ctNumerator;
	}
	public Integer getCtDenominator() {
		return ctDenominator;
	}
	public void setCtDenominator(Integer ctDenominator) {
		this.ctDenominator = ctDenominator;
	}
	public Double getCtRatio() {
		return ctRatio;
	}
	public void setCtRatio(Double ctRatio) {
		this.ctRatio = ctRatio;
	}
	public Double getRv() {
		return rv;
	}
	public void setRv(Double rv) {
		this.rv = rv;
	}
	public Double getRi() {
		return ri;
	}
	public void setRi(Double ri) {
		this.ri = ri;
	}
	public Double getRp() {
		return rp;
	}
	public void setRp(Double rp) {
		this.rp = rp;
	}
	public Double getMi() {
		return mi;
	}
	public void setMi(Double mi) {
		this.mi = mi;
	}
	public Byte getWiringMode() {
		return wiringMode;
	}
	public void setWiringMode(Byte wiringMode) {
		this.wiringMode = wiringMode;
	}
	public Byte getBakFlag() {
		return bakFlag;
	}
	public void setBakFlag(Byte bakFlag) {
		this.bakFlag = bakFlag;
	}
	public Short getMainId() {
		return mainId;
	}
	public void setMainId(Short mainId) {
		this.mainId = mainId;
	}
	public Short getBdFactor() {
		return bdFactor;
	}
	public void setBdFactor(Short bdFactor) {
		this.bdFactor = bdFactor;
	}
	public Short getVfactor() {
		return vfactor;
	}
	public void setVfactor(Short vfactor) {
		this.vfactor = vfactor;
	}
	public Short getIfactor() {
		return ifactor;
	}
	public void setIfactor(Short ifactor) {
		this.ifactor = ifactor;
	}
	public Short getPfactor() {
		return pfactor;
	}
	public void setPfactor(Short pfactor) {
		this.pfactor = pfactor;
	}
	public Short getCsFactory() {
		return csFactory;
	}
	public void setCsFactory(Short csFactory) {
		this.csFactory = csFactory;
	}
	public Short getClFa() {
		return clFa;
	}
	public void setClFa(Short clFa) {
		this.clFa = clFa;
	}
	public String getInfCode1() {
		return infCode1;
	}
	public void setInfCode1(String infCode1) {
		this.infCode1 = infCode1;
	}
	public String getInfCode2() {
		return infCode2;
	}
	public void setInfCode2(String infCode2) {
		this.infCode2 = infCode2;
	}
	public String getInfCode3() {
		return infCode3;
	}
	public void setInfCode3(String infCode3) {
		this.infCode3 = infCode3;
	}
	public String getReserve1() {
		return reserve1;
	}
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	public void setConsType(Byte consType) {
		this.consType = consType;
	}
	public Byte getConsType() {
		return consType;
	}
	       	
	

	
	
	
	
	
	
}
