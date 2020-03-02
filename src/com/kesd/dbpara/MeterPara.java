package com.kesd.dbpara;

import java.io.Serializable;

public class MeterPara implements Serializable{
	
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
	private static final long serialVersionUID = 7456635211917442688L;
//	private Short 	id; 			
	private Integer rtuId; 			
	private Short 	mpId; 			
	private String 	describe;		
	private Short 	residentId;		
	private Short 	metermodelId;	
	private String 	commAddr;		
	private String 	meterId;		
	private String 	madeNo;			
	private String 	assetNo;		
	private Byte 	factory;		
	private Byte 	status;			
	private Byte 	useFlag;		
	private Byte 	equipIdx;		
	private Integer commSpeed;		
	private Byte 	commPort;		
	private Byte 	commProt;		
	private String 	commPwd;		
	private Byte 	flNum;			
	private Byte 	dndataInt;		
	private Byte 	dndataDec;		
	private Byte 	dataBit;		
	private Byte 	stopBit;		
	private Byte 	checkBit;		
	private Short 	pOffset;		
	private Short 	qOffset;		
	private Short 	substId;		
	private Short 	lineId;			
	private String 	barCode;		
	private String 	jsCode;			
	private Byte 	prepayflag;		
	private String 	infCode1;		
	private String 	infCode2;		
	private String 	infCode3;		
	private String 	reserve1;		
	
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
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public Short getResidentId() {
		return residentId;
	}
	public void setResidentId(Short residentId) {
		this.residentId = residentId;
	}
	public Short getMetermodelId() {
		return metermodelId;
	}
	public void setMetermodelId(Short metermodelId) {
		this.metermodelId = metermodelId;
	}
	public String getCommAddr() {
		return commAddr;
	}
	public void setCommAddr(String commAddr) {
		this.commAddr = commAddr;
	}
	public String getMeterId() {
		return meterId;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public String getMadeNo() {
		return madeNo;
	}
	public void setMadeNo(String madeNo) {
		this.madeNo = madeNo;
	}
	public String getAssetNo() {
		return assetNo;
	}
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}
	public Byte getFactory() {
		return factory;
	}
	public void setFactory(Byte factory) {
		this.factory = factory;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public Byte getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(Byte useFlag) {
		this.useFlag = useFlag;
	}
	public Byte getEquipIdx() {
		return equipIdx;
	}
	public void setEquipIdx(Byte equipIdx) {
		this.equipIdx = equipIdx;
	}
	public Integer getCommSpeed() {
		return commSpeed;
	}
	public void setCommSpeed(Integer commSpeed) {
		this.commSpeed = commSpeed;
	}
	public Byte getCommPort() {
		return commPort;
	}
	public void setCommPort(Byte commPort) {
		this.commPort = commPort;
	}
	public Byte getCommProt() {
		return commProt;
	}
	public void setCommProt(Byte commProt) {
		this.commProt = commProt;
	}
	public String getCommPwd() {
		return commPwd;
	}
	public void setCommPwd(String commPwd) {
		this.commPwd = commPwd;
	}
	public Byte getFlNum() {
		return flNum;
	}
	public void setFlNum(Byte flNum) {
		this.flNum = flNum;
	}
	public Byte getDndataInt() {
		return dndataInt;
	}
	public void setDndataInt(Byte dndataInt) {
		this.dndataInt = dndataInt;
	}
	public Byte getDndataDec() {
		return dndataDec;
	}
	public void setDndataDec(Byte dndataDec) {
		this.dndataDec = dndataDec;
	}
	public Byte getDataBit() {
		return dataBit;
	}
	public void setDataBit(Byte dataBit) {
		this.dataBit = dataBit;
	}
	public Byte getStopBit() {
		return stopBit;
	}
	public void setStopBit(Byte stopBit) {
		this.stopBit = stopBit;
	}
	public Byte getCheckBit() {
		return checkBit;
	}
	public void setCheckBit(Byte checkBit) {
		this.checkBit = checkBit;
	}
	public Short getpOffset() {
		return pOffset;
	}
	public void setpOffset(Short pOffset) {
		this.pOffset = pOffset;
	}
	public Short getqOffset() {
		return qOffset;
	}
	public void setqOffset(Short qOffset) {
		this.qOffset = qOffset;
	}
	public Short getSubstId() {
		return substId;
	}
	public void setSubstId(Short substId) {
		this.substId = substId;
	}
	public Short getLineId() {
		return lineId;
	}
	public void setLineId(Short lineId) {
		this.lineId = lineId;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getJsCode() {
		return jsCode;
	}
	public void setJsCode(String jsCode) {
		this.jsCode = jsCode;
	}
	public Byte getPrepayflag() {
		return prepayflag;
	}
	public void setPrepayflag(Byte prepayflag) {
		this.prepayflag = prepayflag;
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
}
