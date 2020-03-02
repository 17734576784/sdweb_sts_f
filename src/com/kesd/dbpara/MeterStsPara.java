package com.kesd.dbpara;

import java.io.Serializable;

public class MeterStsPara implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3990942930737593458L;
	private int rtuId;
	private short mpId;
	private byte   keychange;
	private String meterKey;
	private String oldmtKey;
	private String token1;
	private String token2;
	private String token3;
	private String token4;
	private String reserve1;
	private String reserve2;
	
	private int    ken;
	private int    oldKen;
	private byte   krn;
	private byte   oldKrn;
	private byte   kt;
	private byte   oldKt;
	private byte   ti;
	private byte   oldTi;
	private byte   regno;
	private byte   oldRegno;
	private String drn;
	private String oldDrn;
	private String sgc;
	private String oldSgc;
	
	
	
	public int getRtuId() {
		return rtuId;
	}
	public void setRtuId(int rtuId) {
		this.rtuId = rtuId;
	}
	public short getMpId() {
		return mpId;
	}
	public void setMpId(short mpId) {
		this.mpId = mpId;
	}
	
	public byte getKeychange() {
		return keychange;
	}
	public void setKeychange(byte keychange) {
		this.keychange = keychange;
	}	
	
	public String getMeterKey() {
		return meterKey;
	}
	public void setMeterKey(String meterKey) {
		this.meterKey = meterKey;
	}
	public String getOldmtKey() {
		return oldmtKey;
	}
	public void setOldmtKey(String oldmtKey) {
		this.oldmtKey = oldmtKey;
	}
	public String getToken1() {
		return token1;
	}
	public void setToken1(String token1) {
		this.token1 = token1;
	}
	public String getToken2() {
		return token2;
	}
	public void setToken2(String token2) {
		this.token2 = token2;
	}
	public String getToken3() {
		return token3;
	}
	public void setToken3(String token3) {
		this.token3 = token3;
	}
	public String getToken4() {
		return token4;
	}
	public void setToken4(String token4) {
		this.token4 = token4;
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
	
	
	
	public int getKen() {
		return ken;
	}
	public void setKen(int ken) {
		this.ken = ken;
	}
	public int getOldKen() {
		return oldKen;
	}
	public void setOldKen(int oldKen) {
		this.oldKen = oldKen;
	}
	
	public byte getKrn() {
		return krn;
	}
	public void setKrn(byte krn) {
		this.krn = krn;
	}
	public byte getOldKrn() {
		return oldKrn;
	}
	public void setOldKrn(byte oldKrn) {
		this.oldKrn = oldKrn;
	}	
	
	public byte getKt() {
		return kt;
	}
	public void setKt(byte kt) {
		this.kt = kt;
	}
	public byte getOldKt() {
		return oldKt;
	}
	public void setOldKt(byte oldKt) {
		this.oldKt = oldKt;
	}	
	
	public String getDrn() {
		return drn;
	}
	public void setDrn(String drn) {
		this.drn = drn;
	}
	public String getOldDrn() {
		return oldDrn;
	}
	public void setOldDrn(String oldDrn) {
		this.oldDrn = oldDrn;
	}	
	
	public byte getTi() {
		return ti;
	}
	public void setTi(byte ti) {
		this.ti = ti;
	}
	public byte getOldTi() {
		return oldTi;
	}
	public void setOldTi(byte oldTi) {
		this.oldTi = oldTi;
	}	

	
	public byte getRegno() {
		return regno;
	}
	public void setRegno(byte regno) {
		this.regno = regno;
	}
	public byte getOldRegno() {
		return oldRegno;
	}
	public void setOldRegno(byte oldRegno) {
		this.oldRegno = oldRegno;
	}	
	
	
	public String getSgc() {
		return sgc;
	}
	public void setSgc(String sgc) {
		this.sgc = sgc;
	}
	public String getOldSgc() {
		return oldSgc;
	}
	public void setOldSgc(String oldSgc) {
		this.oldSgc = oldSgc;
	}
}
