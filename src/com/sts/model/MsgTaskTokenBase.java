package com.sts.model;

import java.io.Serializable;

public class MsgTaskTokenBase implements Serializable{

	/**
	 * MsgTaskToken操作公共类
	 */
	private static final long serialVersionUID = 6816372738183176240L;
	
	String dispenserPan;		//19位表号
	String keyRegNo;			//厂家售电寄存器，KMF中解析的Key Register NO
	String sgc;					//KMF中(Supply Group Code)	
	int    tariffIndex;			//(费率方案中 tariff_index) 
	String keyRevisionNumber;	//KMF(Key Revision Number)
	String keyExpiryNumber;		//KMF(Key Expiry Number)
	byte   creditType;			//0(Electricity)
	String tokenId;				//StsFunc.getTid(STSDef.STS_BASEDATE_XX)算法获取，STSDef.STS_BASEDATE_XX在配置文件中配置
	
	public MsgTaskTokenBase(){
		
	}
	
	public MsgTaskTokenBase(String dispenserPan, String keyRegNo, String sgc,
			int tariffIndex, String keyRevisionNumber,
			String keyExpiryNumber, byte creditType, String tokenId) {
		super();
		this.dispenserPan = dispenserPan;
		this.keyRegNo = keyRegNo;
		this.sgc = sgc;
		this.tariffIndex = tariffIndex;
		this.keyRevisionNumber = keyRevisionNumber;
		this.keyExpiryNumber = keyExpiryNumber;
		this.creditType = creditType;
		this.tokenId = tokenId;
	}

	public String getDispenserPan() {
		return dispenserPan;
	}
	
	public void setDispenserPan(String dispenserPan) {
		this.dispenserPan = dispenserPan;
	}
	
	public String getKeyRegNo() {
		return keyRegNo;
	}
	
	public void setKeyRegNo(String keyRegNo) {
		this.keyRegNo = keyRegNo;
	}
	
	public String getSgc() {
		return sgc;
	}
	
	public void setSgc(String sgc) {
		this.sgc = sgc;
	}
	
	public int getTariffIndex() {
		return tariffIndex;
	}
	
	public void setTariffIndex(int tariffIndex) {
		this.tariffIndex = tariffIndex;
	}
	
	public String getKeyRevisionNumber() {
		return keyRevisionNumber;
	}
	
	public void setKeyRevisionNumber(String keyRevisionNumber) {
		this.keyRevisionNumber = keyRevisionNumber;
	}
	
	public String getKeyExpiryNumber() {
		return keyExpiryNumber;
	}
	
	public void setKeyExpiryNumber(String keyExpiryNumber) {
		this.keyExpiryNumber = keyExpiryNumber;
	}
	
	public byte getCreditType() {
		return creditType;
	}
	
	public void setCreditType(byte creditType) {
		this.creditType = creditType;
	}
	
	public String getTokenId() {
		return tokenId;
	}
	
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
}
