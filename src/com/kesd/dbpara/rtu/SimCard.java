package com.kesd.dbpara.rtu;

public class SimCard {
	private Short 	id;					//���
	private String  telNo;				//�绰����
	private String  ipAddr;				//��ӦIP
	private String  serialNo;			//���к�
	private Byte 	type;				//���ͣ�GPRS��CDMA��GSM
	private String  appType;			//ҵ������
	private Integer actualFlux;			//ʵ������
	private Integer overrunFlux;		//������
	private Byte 	status;				//����״̬
	private Integer runDate;			//Ͷ������
	private Integer stopDate;			//ͣ������
	private Short orgId;				//��������
		
	public Short getId() {
		return id;
	}
	public void setId(Short id) {
		this.id = id;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public Integer getActualFlux() {
		return actualFlux;
	}
	public void setActualFlux(Integer actualFlux) {
		this.actualFlux = actualFlux;
	}
	public Integer getOverrunFlux() {
		return overrunFlux;
	}
	public void setOverrunFlux(Integer overrunFlux) {
		this.overrunFlux = overrunFlux;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public Integer getRunDate() {
		return runDate;
	}
	public void setRunDate(Integer runDate) {
		this.runDate = runDate;
	}
	public Integer getStopDate() {
		return stopDate;
	}
	public void setStopDate(Integer stopDate) {
		this.stopDate = stopDate;
	}
	public Short getOrgId() {
		return orgId;
	}
	public void setOrgId(Short orgId) {
		this.orgId = orgId;
	}
}
