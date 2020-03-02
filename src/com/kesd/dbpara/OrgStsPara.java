package com.kesd.dbpara;

import java.io.Serializable;

public class OrgStsPara implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3990942930732563458L;
	private Short OrgId;			/*SGC/所属供电所*/
	private byte KT;				/*密钥类型*/
	private byte DKGA;				/*算法类型*/
	private byte ET;                /*加密类型  0:软加密  1:硬加密*/
	private byte KR;                /*加密寄存器类型 1-N*/
	private String VK1;				/*VK1*/
	private String VK2;
	private String VK3;
	private String VK4;
	private String KMF;				/*厂家加密码*/
	
	private String reserve1;
	private String reserve2;
 
	public Short getOrgId() {
		return OrgId;
	}
	public void setOrgId(Short orgId) {
		OrgId = orgId;
	}
	public byte getKT() {
		return KT;
	}
	public void setKT(byte kT) {
		KT = kT;
	}
	public byte getET() {
		return ET;
	}
	public void setET(byte eT) {
		ET = eT;
	}
	public byte getKR() {
		return KR;
	}
	public void setKR(byte kR) {
		KR = kR;
	}
	public byte getDKGA() {
		return DKGA;
	}
	public void setDKGA(byte dKGA) {
		DKGA = dKGA;
	}
	public String getVK1() {
		return VK1;
	}
	public void setVK1(String vK1) {
		VK1 = vK1;
	}
	public String getVK2() {
		return VK2;
	}
	public void setVK2(String vK2) {
		VK2 = vK2;
	}
	public String getVK3() {
		return VK3;
	}
	public void setVK3(String vK3) {
		VK3 = vK3;
	}
	public String getVK4() {
		return VK4;
	}
	public void setVK4(String vK4) {
		VK4 = vK4;
	}
	
	public String getKMF() {
		return KMF;
	}
	public void setKMF(String kMF) {
		KMF = kMF;
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
 
	
	
}
